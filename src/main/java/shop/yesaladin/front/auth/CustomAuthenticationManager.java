package shop.yesaladin.front.auth;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
import shop.yesaladin.front.member.jwt.JwtPayload;

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

        // TODO: gatewayUrl이 현재 shop 기준으로 되어 있어 실제 gateway + auth url로 변경해야 함.
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

        // TODO: redis에 토큰 추가

        log.info("accessToken={}", accessToken);

        List<SimpleGrantedAuthority> authorities = parseJwt(accessToken.substring(BEARER_LENGTH)).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        log.info("authorities={}", authorities);

        return new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal().toString(),
                null,
                authorities
        );
    }

    /**
     * JWT를 parsing 하기 위한 기능입니다.
     *
     * @param jwt JWT로 발급된 accessToken 입니다.
     * @return JWT payload에 저장된 권한 정보를 반환합니다.
     * @throws JsonProcessingException
     */
    private List<String> parseJwt(String jwt) throws JsonProcessingException {
        String[] jwtSection = jwt.split("\\.");
        // JWT 는 header, payload, signature 가 "." 으로 연결됨 (header.payload.signature)
        String jwtPayload = jwtSection[1];

        byte[] decode = Base64.getDecoder().decode(jwtPayload);
        JwtPayload payload = new ObjectMapper().readValue(new String(decode, StandardCharsets.UTF_8), JwtPayload.class);

        return payload.getRoles();
    }
}
