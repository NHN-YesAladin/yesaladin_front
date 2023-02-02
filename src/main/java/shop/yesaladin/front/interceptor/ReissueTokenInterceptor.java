package shop.yesaladin.front.interceptor;

import static shop.yesaladin.front.member.jwt.AuthUtil.JWT_CODE;
import static shop.yesaladin.front.member.jwt.AuthUtil.UUID_CODE;

import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.yesaladin.front.member.adapter.MemberAdapter;
import shop.yesaladin.front.member.jwt.AuthInfo;

/**
 * JWT Token 재발급을 위한 custom interceptor 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class ReissueTokenInterceptor implements HandlerInterceptor {

    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * JWT Token 재발급을 위한 기능입니다.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  interceptor가 진입하려는 Controller가 담겨있는 handler
     * @return Controller 진입 여부
     * @throws Exception interceptor 작동 시 발생할 수 있는 예외 입니다.
     * @author 송학현
     * @since 1.0
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uuid = getUuidFromCookie(request.getCookies());
        log.info("uuid={}", uuid);
        if (!(authentication instanceof AnonymousAuthenticationToken) && Objects.nonNull(uuid)) {
            log.info("로그인 o");

            if (Objects.isNull(uuid)) {
                log.info("uuid 없음");
                return true;
            }

            AuthInfo authInfo = (AuthInfo) redisTemplate.opsForHash()
                    .get(uuid, JWT_CODE.getValue());
            log.info("redis에 기존에 있던 accessToken={}", authInfo.getAccessToken());
            if (Objects.nonNull(authInfo)) {
                String accessToken = memberAdapter.tokenReissue(uuid);
                log.info("new token={}", accessToken);

                redisTemplate.opsForHash().delete(uuid, JWT_CODE.getValue());
                authInfo.setAccessToken(accessToken);
                redisTemplate.opsForHash().put(uuid, JWT_CODE.getValue(), authInfo);
            }
            return true;
        }

        log.info("로그인 x");
        return true;
    }

    /**
     * cookie에 들어있는 uuid를 반환하기 위한 기능입니다.
     *
     * @param cookies 브라우저에 존재하는 Cookie의 목록입니다.
     * @return login 시 발급 받아 쿠키에 저장한 uuid 값을 반환합니다.
     * @author : 송학현
     * @since : 1.0
     */
    private String getUuidFromCookie(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (Objects.equals(UUID_CODE.getValue(), cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
