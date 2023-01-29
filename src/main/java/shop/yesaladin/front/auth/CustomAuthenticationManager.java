package shop.yesaladin.front.auth;

import static java.util.stream.Collectors.toList;
import static shop.yesaladin.front.member.jwt.AuthUtil.JWT_CODE;
import static shop.yesaladin.front.member.jwt.AuthUtil.UUID_CODE;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.yesaladin.front.common.exception.InvalidHttpHeaderException;
import shop.yesaladin.front.member.adapter.MemberAdapter;
import shop.yesaladin.front.member.dto.LoginRequest;
import shop.yesaladin.front.member.dto.MemberResponse;
import shop.yesaladin.front.member.jwt.AuthInfo;

/**
 * AuthenticationManager를 custom한 Manager 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private static final String UUID_HEADER = "UUID_HEADER";

    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Auth 서버에서 발급받은 JWT 토큰을 기반으로 Shop 서버에 유저 정보를 요청 한 뒤,
     * UsernamePasswordAuthenticationToken을 만들어 반환합니다.
     *
     * @param authentication 인증 객체입니다.
     * @return 인증 객체를 반환합니다.
     * @throws AuthenticationException 인증 실패 시 발생 하는 예외 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        LoginRequest loginRequest = new LoginRequest(
                (String) authentication.getPrincipal(),
                (String) authentication.getCredentials()
        );
        ResponseEntity<Void> exchange = memberAdapter.getAuthInfo(loginRequest);

        checkValidLoginRequest(exchange);

        String uuid = Objects.requireNonNull(exchange.getHeaders().get(UUID_HEADER).get(0));
        log.info("uuid={}", uuid);

        String accessToken = extractAuthorizationHeader(exchange);

        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        ResponseEntity<MemberResponse> memberResponse = memberAdapter.getMemberInfo(
                loginRequest,
                accessToken
        );

        log.info("accessToken={}", accessToken);

        HttpServletResponse servletResponse = Objects.requireNonNull(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())).getResponse();

        Cookie cookie = new Cookie(UUID_CODE.getValue(), uuid);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        servletResponse.addCookie(cookie);

        List<SimpleGrantedAuthority> authorities = getAuthorities(memberResponse);
        log.info("authorities={}", authorities);

        AuthInfo authInfo = new AuthInfo(memberResponse.getBody(), accessToken, memberResponse.getBody().getRoles());
        log.info("authInfo={}", authInfo);
        redisTemplate.opsForHash().put(uuid, JWT_CODE.getValue(), authInfo);

        return new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal().toString(),
                null,
                authorities
        );
    }

    /**
     * login 요청 시 올바른 결과 인지 판별 하기 위해 Response Header를 검증 하는 기능 입니다.
     * 예외 발생 시 CustomFailureHandler가 동작합니다.
     *
     * @param exchange Auth 서버에 login 요청 시 반환 되는 결과 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    private void checkValidLoginRequest(ResponseEntity<Void> exchange) {
        log.info("Auth server exchange check={}", exchange);
        if (!exchange.getHeaders().containsKey(UUID_HEADER) || !exchange.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new BadCredentialsException("자격 증명 실패");
        }
    }

    /**
     * Shop 서버에서 제공 받은 회원 정보를 바탕으로 권한 정보를 추출하는 기능입니다.
     *
     * @param memberResponse Shop 서버에서 제공받은 회원 정보 결과 입니다.
     * @return token을 만들기 위해 권한 정보를 담은 List<SimpleGrantedAuthority>를 반환합니다.
     * @author : 송학현
     * @since : 1.0
     */
    private List<SimpleGrantedAuthority> getAuthorities(ResponseEntity<MemberResponse> memberResponse) {
        MemberResponse member = Objects.requireNonNull(memberResponse).getBody();
        log.info("member={}", member);

        return member.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    /**
     * 로그인 시 Auth 서버에서 제공 받은 응답 헤더를 추출하는 기능입니다.
     *
     * @param exchange Auth 서버에서 제공받은 응답 헤더입니다.
     * @return Http Response Header의 Authorization에 들어있는 accessToken을 추출하여 반환합니다.
     * @author : 송학현
     * @since : 1.0
     */
    private String extractAuthorizationHeader(ResponseEntity<Void> exchange) {
        String accessToken = Objects.requireNonNull(exchange.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        if (Objects.isNull(accessToken)) {
            throw new InvalidHttpHeaderException("Authorization Header is empty");
        }
        return accessToken;
    }
}
