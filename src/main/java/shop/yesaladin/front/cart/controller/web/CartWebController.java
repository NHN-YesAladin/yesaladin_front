package shop.yesaladin.front.cart.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.cart.dto.AddToCartDto;
import shop.yesaladin.front.cart.dto.ViewCartDto;
import shop.yesaladin.front.common.utils.CookieUtils;
import shop.yesaladin.front.config.GatewayConfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 장바구니 추가 관련 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartWebController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private final CookieUtils cookieUtils;

    /**
     * [GET /cart] 장바구니 View 를 반환합니다.
     *
     * @return 장바구니 View
     * @author 이수정
     * @since 1.0
     */
    @GetMapping
    public String cart(
            @CookieValue(value = "CART_NO", required = false) Cookie cookie,
            Model model
    ) throws IOException, URISyntaxException {
        // eBook 상품 = eBook 파일 존재
        List<ViewCartDto> eBookCart = new ArrayList<>();
        // 구독 상품 = eBook 파일 존재 안함 && 구독 가능 상품
        List<ViewCartDto> subscribeCart = new ArrayList<>();
        // 배송 상품 = eBook 파일 존재 안함 && 구독 불가능 상품
        List<ViewCartDto> deliveryCart = new ArrayList<>();

        // CART_NO 쿠키가 존재하지 않는 경우 = 장바구니에 넣은 물건이 없는 경우
        // CART_NO 쿠키가 존재하는 경우 = 장바구니에 넣은 물건이 있는 경우, 회원로그인을 한 경우
        if (Objects.nonNull(cookie)) {
            // 장바구니에 담은 데이터를 Map으로 받아옴
            Map<Object, Object> cart = redisTemplate.opsForHash().entries(cookie.getValue());
            log.info("cart = {}", cart);

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            cart.keySet().forEach(key -> {
                log.info("key = {}", key.toString());
                log.info("value = {}", cart.get(key).toString());
                multiValueMap.add(key.toString(), cart.get(key).toString());
            });

            // 받아온 Map을 바탕으로 상품 개별 정보를 받아옴
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                    .path("/v1/products/cart")
                    .queryParams(multiValueMap)
                    .build();

            ResponseDto<List<ViewCartDto>> response = restTemplate.exchange(
                    uri.toUri(),
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<ResponseDto<List<ViewCartDto>>>() {
                    }
            ).getBody();

            // 상품 종류에 따른 분류
            Objects.requireNonNull(response.getData()).forEach(product -> {
                if (Boolean.TRUE.equals(product.getIsEbook())) {
                    eBookCart.add(product);
                } else if (Boolean.TRUE.equals(product.getIsSubscribeProduct())) {
                    subscribeCart.add(product);
                } else {
                    deliveryCart.add(product);
                }
            });
        }

        model.addAttribute("eBookCart", eBookCart);
        model.addAttribute("subscribeCart", subscribeCart);
        model.addAttribute("deliveryCart", deliveryCart);

        return "main/cart/cart";
    }

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
    public void addToCart(
            @RequestBody AddToCartDto cartDto,
            @CookieValue(value = "CART_NO", required = false) Cookie cookie,
            @CookieValue(value = "YA_AUT", required = false) Cookie member,
            HttpServletResponse response
    ) {
        // 전달받은 상품 아이디, 개수 확인
        log.info("id = {}", cartDto.getId());
        log.info("quantity = {}", cartDto.getQuantity());
        log.info("isEbook = {}", cartDto.getIsEbook());
        log.info("isSubscribe = {}", cartDto.getIsSubscriptionAvailable());

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

        // CART_NO 쿠키의 값을 Redis의 key로 사용함.
        // 만약, 이미 카트에 입력하려는 값이 존재한다면 '+'하여 저장
        // 없는 상품이라면 그대로 저장
        log.info("cookie's name = {}", cookie.getName());
        log.info("cookie's value = {}", cookie.getValue());
        Object preQuantity = redisTemplate.opsForHash().get(cookie.getValue(), cartDto.getId());

        // 이전에 저장되어 있던 상품
        log.info("preQuantity = {}", preQuantity);
        int quantity = cartDto.getQuantity();
        if (Boolean.TRUE.equals(Objects.nonNull(preQuantity) && !cartDto.getIsEbook()) && Boolean.TRUE.equals(!cartDto.getIsSubscriptionAvailable())) {
            quantity += (int) preQuantity;
        }
        log.info("quantity = {}", quantity);
        redisTemplate.opsForHash().put(cookie.getValue(), cartDto.getId(), quantity);

        if (Objects.isNull(member)) {
            // 비회원이라면 만료 3일
            redisTemplate.expire(cookie.getValue(), 3, TimeUnit.DAYS);
        } else {
            // 회원이라면 만료 30일
            redisTemplate.expire(cookie.getValue(), 30, TimeUnit.DAYS);
        }
    }

    /**
     * [DELETE /cart/{productId}] 장바구니에서 특정 상품을 삭제합니다.
     *
     * @param productId 장바구니에서 삭제할 상품의 Id
     * @param cookie    Redis의 key값을 가진 CART_NO 쿠키
     * @author 이수정
     * @since 1.0
     */
    @DeleteMapping("/{productId}")
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
     * [PUT /cart/{productId}] 장바구니 내의 상품의 담은 개수를 변경합니다.
     *
     * @param productId 장바구니에서 개수를 변경할 상품의 Id
     * @param quantity  장바구니 상품의 개수
     * @param cookie    Redis의 key값을 가진 CART_NO 쿠키
     * @author 이수정
     * @since 1.0
     */
    @PutMapping("/{productId}")
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
