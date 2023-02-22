package shop.yesaladin.front.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.cart.dto.AddToCartDto;
import shop.yesaladin.front.common.utils.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 장바구니 추가, 삭제, 수량 변경 관련 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtils cookieUtils;

    /**
     * [POST /cart] 요청을 받아 장바구니에 상품을 추가합니다.
     *
     * @param cartDto  장바구니에 추가할 상품의 Id, 개수, 상품의 종류를 담은 Dto
     * @param cookie   Redis의 key값을 가진 CART_NO 쿠키
     * @param member   회원이라면 YA_AUT 쿠키가 존재, 회원용 장바구니 생성을 위한 쿠키
     * @param response 쿠키 정보를 추가하기 위한 HttpServletResponse
     * @author 이수정
     * @author 송학현
     * @since 1.0
     */
    @PostMapping
    public ResponseDto<String> addToCart(
            @RequestBody AddToCartDto cartDto,
            @CookieValue(value = "CART_NO", required = false) Cookie cookie,
            @CookieValue(value = "YA_AUT", required = false) Cookie member,
            HttpServletResponse response
    ) {
        // 전달받은 상품 아이디, 개수 확인
        log.info("id = {}", cartDto.getId());
        log.info("isEbook = {}", cartDto.getIsEbook());
        log.info("isSubscribe = {}", cartDto.getIsSubscriptionAvailable());
        log.info("quantity = {}", cartDto.getQuantity());

        // 0, 음수인 수량을 담으면 에러
        if (cartDto.getQuantity() <= 0) {
            return ResponseDto.<String>builder()
                    .success(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .data("0 혹은 음수인 수량은 장바구니에 담을 수 없습니다.")
                    .build();
        }

        // 비회원이 구독상품, Ebook 상품을 담으려고 할 때
        if (Objects.isNull(member) && (cartDto.getIsEbook() || cartDto.getIsSubscriptionAvailable())) {
            return ResponseDto.<String>builder()
                    .success(false)
                    .status(HttpStatus.UNAUTHORIZED)
                    .data("E-book 또는 구독상품은 회원만 담을 수 있습니다.")
                    .build();
        }

        // 회원인 경우, 회원의 ID(PK) 존재 [로그인 시 발급][로그아웃 시 삭제]
        // 회원이 쿠키를 악의적으로 삭제 -> 회원일 때 생성
        if (Objects.nonNull(member)) {
            String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
            cookie = cookieUtils.createCookie("CART_NO", loginId, 60 * 60 * 24 * 30);

            response.addCookie(cookie);
        }

        // 비회원인 경우, 비회원용 장바구니 UUID 존재 [장바구니 담기 선택 시 발급][3일 만료]
        // 존재하지 않는 경우, 비회원 + 미발급 상태
        if (Objects.isNull(cookie)) {
            String uuid = String.valueOf(UUID.randomUUID());
            cookie = cookieUtils.createCookie("CART_NO", uuid, 60 * 60 * 24 * 3);

            response.addCookie(cookie);
        }

        // anonymousUser 차단
        if (cookie.getName().equals("anonymousUser")) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseDto.<String>builder()
                    .success(false)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data("다시 한번 시도해주십시오.")
                    .build();
        }

        // CART_NO 쿠키의 값을 Redis의 key로 사용함.
        // 만약, 이미 카트에 입력하려는 값이 존재한다면 '+'하여 저장
        // 없는 상품이라면 그대로 저장
        log.info("cookie's name = {}", cookie.getName());
        log.info("cookie's value = {}", cookie.getValue());
        Object preQuantity = redisTemplate.opsForHash().get(cookie.getValue(), cartDto.getId());

        // 이전에 저장되어 있던 상품
        log.info("preQuantity = {}", preQuantity);
        if (Objects.nonNull(preQuantity) && Integer.parseInt(preQuantity.toString()) == 1
                && (cartDto.getIsEbook() || cartDto.getIsSubscriptionAvailable())) {
            // 구독상품, Ebook이고 이전에 담았던 상품인 경우
            return ResponseDto.<String>builder()
                    .success(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .data("E-book 또는 구독상품은 1개만 담을 수 있습니다.")
                    .build();
        }

        // 이전 수량과 합산
        int quantity = cartDto.getQuantity();
        if (Boolean.TRUE.equals(Objects.nonNull(preQuantity) && !cartDto.getIsEbook()) && Boolean.TRUE.equals(!cartDto.getIsSubscriptionAvailable())) {
            quantity += Integer.parseInt(preQuantity.toString());
        }
        redisTemplate.opsForHash().put(cookie.getValue(), cartDto.getId(), quantity);

        if (Objects.isNull(member)) {
            // 비회원이라면 만료 3일
            redisTemplate.expire(cookie.getValue(), 3, TimeUnit.DAYS);
        } else {
            // 회원이라면 만료 30일
            redisTemplate.expire(cookie.getValue(), 30, TimeUnit.DAYS);
        }

        return ResponseDto.<String>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data("Success")
                .build();
    }


    /**
     * [POST /cart/delete/{productId}] 장바구니에서 특정 상품을 삭제합니다.
     *
     * @param productId 장바구니에서 삭제할 상품의 Id
     * @param cookie    Redis의 key값을 가진 CART_NO 쿠키
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/delete/{productId}")
    @ResponseBody
    public void deleteInCart(
            @PathVariable String productId,
            @CookieValue(value = "CART_NO") Cookie cookie
    ) {
        log.info("productId = {}", productId);

        String key = cookie.getValue();

        redisTemplate.opsForHash().delete(key, productId);
    }

    /**
     * [POST /cart/change-quantity/{productId}] 장바구니 내의 상품의 담은 개수를 변경합니다.
     *
     * @param productId 장바구니에서 개수를 변경할 상품의 Id
     * @param quantity  장바구니 상품의 개수
     * @param cookie    Redis의 key값을 가진 CART_NO 쿠키
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/change-quantity/{productId}")
    @ResponseBody
    public void modifyQuantity(
            @PathVariable String productId,
            @RequestBody Map<String, String> quantity,
            @CookieValue(value = "CART_NO") Cookie cookie
    ) {
        log.info("productId = {}", productId);
        log.info("quantity = {}", quantity);

        String key = cookie.getValue();

        redisTemplate.opsForHash().put(key, productId, quantity.get("quantity"));
    }
}
