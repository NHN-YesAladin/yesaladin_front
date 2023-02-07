package shop.yesaladin.front.auth;

import static shop.yesaladin.front.member.jwt.AuthUtil.JWT_CODE;
import static shop.yesaladin.front.member.jwt.AuthUtil.UUID_CODE;

import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import shop.yesaladin.front.member.adapter.MemberAdapter;
import shop.yesaladin.front.member.exception.InvalidLogoutRequestException;
import shop.yesaladin.front.member.jwt.AuthInfo;

/**
 * 기본적으로 동작하는 LogoutHandler를 custom한 클래스 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MemberAdapter memberAdapter;

    /**
     * logout 시 동작하는 기능입니다.
     * 세션 정보를 없애고, SecurityContext 역시 clear 합니다.
     *
     * @param request HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @param authentication 인증 객체
     * @author : 송학현
     * @since : 1.0
     */
    @Override
    public void logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) {
        HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            log.info("Already Logged out");
            throw new InvalidLogoutRequestException();
        }

        session.invalidate();

        String uuid = getValueFromCookie(request.getCookies(), UUID_CODE.getValue());
        log.info("uuid={}", uuid);
        if (Objects.isNull(uuid)) {
            throw new InvalidLogoutRequestException();
        }

        AuthInfo auth = (AuthInfo) redisTemplate.opsForHash().get(uuid, JWT_CODE.getValue());
        redisTemplate.opsForHash().delete(uuid, JWT_CODE.getValue());
        Cookie cart = new Cookie("CART_NO", "");
        cart.setMaxAge(0);
        response.addCookie(cart);

        Cookie authCookie = new Cookie(UUID_CODE.getValue(), "");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);

        memberAdapter.logout(uuid, auth.getAccessToken());

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
    }

    /**
     * Cookie에 들어있는 value를 반환하기 위한 기능입니다.
     *
     * @param cookies 브라우저에 존재하는 Cookie의 목록입니다.
     * @param key 찾고자 하는 Cookie의 key에 해당합니다.
     * @return 해당 쿠키에 저장된 값을 반환합니다.
     */
    private String getValueFromCookie(Cookie[] cookies, String key) {
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
}
