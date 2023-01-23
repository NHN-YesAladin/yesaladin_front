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

    @Value("${yesaladin.gateway}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    /**
     * Auth 서버에서 발급받은 JWT 토큰을 기반으로 UsernamePasswordAuthenticationToken을 만들어 반환합니다.
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginRequest loginRequest = new LoginRequest(
                (String) authentication.getPrincipal(),
                (String) authentication.getCredentials()
        );
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<Void> exchange = restTemplate.exchange(
                gatewayUrl + "/auth/login",
                HttpMethod.POST,
                entity,
                Void.class
        );

        // TODO: login시 입력 값이 비었거나, 유저 정보가 없다면 redirect도 안되고 여기서 NullPointerException 발생함.
        String accessToken = exchange.getHeaders().get("Authorization").get(0);
        if (Objects.isNull(accessToken)) {
            throw new AuthenticationServiceException("");
        }

        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        ResponseEntity<MemberResponse> memberResponse = restTemplate.getForEntity(
                gatewayUrl + "/shop/v1/members/login/{loginId}",
                MemberResponse.class,
                loginRequest.getLoginId(),
                httpHeaders
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
}
