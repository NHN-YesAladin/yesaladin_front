package shop.yesaladin.front.interceptor;

import static shop.yesaladin.front.member.jwt.AuthUtil.JWT_CODE;
import static shop.yesaladin.front.member.jwt.AuthUtil.UUID_CODE;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.yesaladin.front.common.utils.CookieUtils;
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
    private final CookieUtils cookieUtils;

    private static final String X_EXPIRE_HEADER = "X-Expire";
    private static final long TIME_TO_REISSUE = Duration.ofMinutes(59).toSeconds();

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
        String uuid = cookieUtils.getValueFromCookie(request.getCookies(), UUID_CODE.getValue());
        log.info("uuid={}", uuid);
        if (!(authentication instanceof AnonymousAuthenticationToken) && Objects.nonNull(uuid)) {
            if (Objects.isNull(uuid)) {
                return true;
            }
            tokenReissue(uuid);
        }
        return true;
    }

    /**
     * JWT 토큰의 유효 시간에 따라 재발급을 요청 하기 위한 기능 입니다.
     *
     * @param uuid login 시 발급 받아 쿠키에 저장한 uuid 값
     * @author 송학현
     * @since 1.0
     */
    private void tokenReissue(String uuid) {
        AuthInfo authInfo = (AuthInfo) redisTemplate.opsForHash()
                .get(uuid, JWT_CODE.getValue());
        if (Objects.nonNull(authInfo) && isReissueRequired(authInfo)) {
            ResponseEntity<Void> response = memberAdapter.tokenReissue(uuid);
            String accessToken = response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String expiredTime = response.getHeaders().get(X_EXPIRE_HEADER).get(0);
            authInfo.setAccessToken(accessToken);
            authInfo.setExpiredTime(expiredTime);
            redisTemplate.opsForHash().put(uuid, JWT_CODE.getValue(), authInfo);
        }
    }

    /**
     * AccessToken의 만료 시간과 현재 시간을 비교 하여 토큰 재발급을 처리해야 하는 지 판별하기 위한 기능 입니다.
     *
     * @param authInfo AccessToken과 User 정보를 담은 클래스 입니다.
     * @return AccessToken의 만료 시간이 5분 이하 인지 판단한 결과 입니다.
     * @author 송학현
     * @since 1.0
     */
    private boolean isReissueRequired(AuthInfo authInfo) {
        long expiredTime = Long.parseLong(authInfo.getExpiredTime());
        long now = new Date().getTime();
        return (expiredTime - (now / 1000) > TIME_TO_REISSUE);
    }
}
