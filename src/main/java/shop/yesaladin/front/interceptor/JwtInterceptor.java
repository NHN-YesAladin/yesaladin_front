package shop.yesaladin.front.interceptor;

import static shop.yesaladin.front.member.jwt.AuthUtil.JWT_CODE;
import static shop.yesaladin.front.member.jwt.AuthUtil.UUID_CODE;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.yesaladin.front.member.jwt.AuthInfo;

/**
 * RestTemplate 요청 전 Authorization Header에 JWT 토큰을 담기 위한 custom interceptor 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements ClientHttpRequestInterceptor {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * RestTemplate의 실행 전, Request Header에 JWT 토큰을 담기 위한 기능 입니다.
     *
     * @param request HttpRequest 객체 입니다.
     * @param body 요청 body에 해당 합니다.
     * @param execution 실제 실행을 수행 하고 요청을 후속 프로세스 체인으로 전달하기 위한 객체 입니다.
     * @return Client 측의 HTTP 응답을 나타 냅니다. RestTemplate 요청 직전에
     * @throws IOException interceptor 작동 시 발생할 수 있는 예외 입니다.
     *
     * @author : 송학현
     * @since : 1.0
     */
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution
    ) throws IOException {
        log.info("path={}", request.getURI().getPath());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (this.isRequiredAuthorizationHeader(request.getURI().getPath())
                && !Objects.isNull(authentication)) {

            HttpServletRequest servletRequest = Objects.requireNonNull(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                    .getRequest();

            String uuid = getUuidFromCookie(servletRequest.getCookies());
            log.info("uuid={}", uuid);

            AuthInfo auth = (AuthInfo) redisTemplate.opsForHash().get(uuid, JWT_CODE.getValue());
            if (Objects.nonNull(auth)) {
                log.info("accessToken={}", auth.getAccessToken());
                log.info("loginId={}", auth.getLoginId());
                log.info("authorities={}", auth.getAuthorities());
                log.info("nickname={}", auth.getNickname());
                request.getHeaders().setBearerAuth(auth.getAccessToken());
                request.getHeaders().add(UUID_CODE.getValue(), uuid);
            }
        }
        return execution.execute(request, body);
    }

    /**
     * RestTemplate 으로 API 요청 시 Authorization Header 값이 필요한 경로 인지 판별 하기 위한 기능 입니다.
     * categories의 경우, 추 후 GET 요청의 경우만 Gateway filter에서 제외 하도록 하겠습니다.
     *
     * @param uri API 요청 대상 경로 입니다.
     * @return JWT AccessToken이 필요한 경로 인지 판별한 결과 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    public boolean isRequiredAuthorizationHeader(String uri) {
        // TODO: shop api 기준으로 경로들 리스트화 해서 수정할 것
        return !(uri.contains("login") || uri.contains("members") || uri.contains("categories") || uri.contains("check") || uri.contains("coupons"));
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
