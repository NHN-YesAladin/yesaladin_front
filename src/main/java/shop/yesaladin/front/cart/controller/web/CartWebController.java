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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.cart.dto.ViewCartDto;
import shop.yesaladin.front.common.utils.CookieUtils;
import shop.yesaladin.front.config.GatewayConfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 장바구니 추가 관련 페이지 View 반환을 위한 Controller 입니다.
 *
 * @author 이수정
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
            @CookieValue(value = "YA_AUT", required = false) Cookie member,
            Model model,
            HttpServletResponse httpServletResponse
    ) throws IOException, URISyntaxException {
        List<ViewCartDto> eBookCart = new ArrayList<>();
        List<ViewCartDto> deliveryCart = new ArrayList<>();

        // 회원이 쿠키를 악의적으로 삭제 -> 회원일 때 생성
        if (Objects.nonNull(member)) {
            String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
            cookie = cookieUtils.createCookie("CART_NO", loginId, 60 * 60 * 24 * 30);

            httpServletResponse.addCookie(cookie);
        }

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
            Objects.requireNonNull(Objects.requireNonNull(response).getData()).forEach(product -> {
                if (Boolean.FALSE.equals(product.getIsDeleted())) {
                    if (Boolean.TRUE.equals(product.getIsEbook())) {
                        eBookCart.add(product);
                    } else {
                        deliveryCart.add(product);
                    }
                }
            });
        }

        model.addAttribute("eBookCart", eBookCart);
        model.addAttribute("deliveryCart", deliveryCart);

        return "main/cart/cart";
    }
}
