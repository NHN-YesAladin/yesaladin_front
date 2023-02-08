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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
     * @param request   HttpRequest 객체 입니다.
     * @param body      요청 body에 해당 합니다.
     * @param execution RestTemplate 요청 직전에 실제 실행을 수행 하고 요청을 후속 프로세스 체인으로 전달하기 위한 객체 입니다.
     * @return Client 측의 HTTP 응답을 나타 냅니다.
     * @throws IOException interceptor 작동 시 발생할 수 있는 예외 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution
    ) throws IOException {
        String path = request.getURI().getPath();
        log.info("path={}", path);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            HttpServletRequest servletRequest = Objects.requireNonNull(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                    .getRequest();

            String uuid = getUuidFromCookie(servletRequest.getCookies());
            log.info("uuid={}", uuid);

            if (Objects.isNull(uuid)) {
                return execution.execute(request, body);
            }

            AuthInfo auth = (AuthInfo) redisTemplate.opsForHash().get(uuid, JWT_CODE.getValue());
            if (Objects.nonNull(auth)) {
                log.info("accessToken={}", auth.getAccessToken());
                request.getHeaders().setBearerAuth(auth.getAccessToken());
                request.getHeaders().add(UUID_CODE.getValue(), uuid);
            }
        }
        return execution.execute(request, body);
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
