package shop.yesaladin.front.common.utils;

import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Objects;

/**
 * Cookie의 생성, 값 조회 등을 편리 하게 사용 하기 위한 Util 클래스입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Component
public class CookieUtils {

    /**
     * Cookie를 생성하기 위한 기능입니다.
     *
     * @param name   생성할 Cookie의 이름
     * @param value  생성할 Cookie에 들어갈 값
     * @param maxAge 생성할 Cookie의 maxAge
     * @return 생성된 Cookie
     * @author 송학현
     * @since 1.0
     */
    public Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        return cookie;
    }

    /**
     * maxage를 지정하지 않고 Cookie를 생성하기 위한 기능입니다.
     *
     * @param name  생성할 Cookie의 이름
     * @param value 생성할 Cookie에 들어갈 값
     * @return 생성된 Cookie
     * @author 송학현
     * @since 1.0
     */
    public Cookie createCookieWithoutMaxAge(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    /**
     * Cookie의 value를 불러오기 위한 기능입니다.
     *
     * @param cookies Cookie의 목록
     * @param key     조회하고자 하는 Cookie의 key
     * @return 조회 대상 Cookie의 value
     * @author 송학현
     * @since 1.0
     */
    public String getValueFromCookie(Cookie[] cookies, String key) {
        if (Objects.isNull(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(key, cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 쿠기에 저장된 장바구니 관련 정보를 지우고, redis의 정보도 삭제하는 메서드
     *
     * @param redisTemplate 레디스 템플릿
     * @param cookie 쿠키
     * @param httpServletResponse 쿠키 삭제를 위한 서블릿 응답 객체
     * @author 배수한
     * @since 1.0
     */
    public void deleteCart(
            RedisTemplate<String, Object> redisTemplate,
            Cookie cookie,
            HttpServletResponse httpServletResponse
    ) {
        if (Objects.nonNull(cookie)) {
            String cartNoCookieValue = cookie.getValue();
            redisTemplate.delete(cartNoCookieValue);

            Cookie cart = this.createCookie("CART_NO", "", 0);
            httpServletResponse.addCookie(cart);
        }
    }
}
