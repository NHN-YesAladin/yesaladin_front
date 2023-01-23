package shop.yesaladin.front.auth;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.member.dto.LoginRequest;
import shop.yesaladin.front.member.dto.MemberResponse;

/**
 * AuthenticationManager를 custom한 Manager 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private static final int BEARER_LENGTH = 7;

    @Value("${yesaladin.gateway.base}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    /**
     * Auth 서버에서 발급받은 JWT 토큰을 기반으로 Shop 서버에 유저 정보를 요청 한 뒤, UsernamePasswordAuthenticationToken을 만들어
     * 반환합니다.
     *
     * @param authentication 인증 객체입니다.
     * @return 인증 객체를 반환합니다.
     * @throws AuthenticationException
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
        ResponseEntity<Void> exchange = getAccessToken(
                loginRequest
        );

        String uuid = exchange.getHeaders().get("UUID_HEADER").get(0);
        log.info("uuid={}", uuid);

        // TODO: login시 입력 값이 비었거나, 유저 정보가 없다면 redirect도 안되고 여기서 NullPointerException 발생함.
        String accessToken = extractAuthorizationHeader(exchange);

        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        ResponseEntity<MemberResponse> memberResponse = getMemberInfo(
                loginRequest,
                accessToken
        );

        MemberResponse member = Objects.requireNonNull(memberResponse).getBody();
        log.info("member={}", member);

        // TODO: redis에 토큰 추가

        log.info("accessToken={}", accessToken);

        List<SimpleGrantedAuthority> authorities = member.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        log.info("authorities={}", authorities);

        return new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal().toString(),
                null,
                authorities
        );
    }

    private String extractAuthorizationHeader(ResponseEntity<Void> exchange) {
        String accessToken = exchange.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        if (Objects.isNull(accessToken)) {
            throw new AuthenticationServiceException("AUTHORIZATION Header is empty");
        }
        return accessToken;
    }

    /**
     * 로그인 과정에서 accessToken을 발급 받아 HTTP Header에 추가한 뒤, Shop 서버에 회원의 정보를 요청하는 기능입니다.
     *
     * @param loginRequest 회원이 로그인 시 입력한 정보를 담은 DTO 입니다.
     * @param accessToken  로그인 과정에서 Auth 서버에서 발급받은 JWT 형식의 accessToken 입니다.
     * @return Shop 서버에 요청한 회원의 정보 DTO를 담은 결과 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    private ResponseEntity<MemberResponse> getMemberInfo(
            LoginRequest loginRequest,
            String accessToken
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        return restTemplate.getForEntity(
                gatewayUrl + "shop/v1/members/login/{loginId}",
                MemberResponse.class,
                loginRequest.getLoginId(),
                httpHeaders
        );
    }

    /**
     * 로그인 과정에서 Auth 서버에서 인증된 JWT 형식의 accessToken을 받아오는 기능입니다.
     *
     * @param loginRequest 회원이 로그인 시 입력한 정보를 담은 DTO 입니다.
     * @return Auth 서버에서 발급받은 JWT 형식의 accessToken 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    private ResponseEntity<Void> getAccessToken(
            LoginRequest loginRequest
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, headers);

        return restTemplate.exchange(
                gatewayUrl + "/auth/login",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }
}
