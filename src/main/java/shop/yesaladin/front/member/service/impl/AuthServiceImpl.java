package shop.yesaladin.front.member.service.impl;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.member.dto.LoginRequest;
import shop.yesaladin.front.member.jwt.JwtPayload;
import shop.yesaladin.front.member.service.inter.AuthService;
import shop.yesaladin.front.member.util.LoginRequestStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${gatewayUrl}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LoginRequestStatus login(LoginRequest request, HttpSession session) throws JsonProcessingException {
        ResponseEntity response = doLogin(request);

        if (Objects.isNull(response.getHeaders().get("Authorization"))) {
            return LoginRequestStatus.WITHDRAW;
        } else if (response.getStatusCode().is5xxServerError()) {
            return LoginRequestStatus.UNAUTHORIZED;
        } else {
            List<String> authorization = response.getHeaders().get("Authorization");
            log.info("response={}", authorization);

            String jwt = authorization.get(0);
            if (jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            }
            // jwt에서 user 정보 빼와서 ContextHolder에 담기
            List<SimpleGrantedAuthority> authorities = parseJwt(jwt).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(toList());

            log.info("authorities={}", authorities);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    request.getLoginId(),
                    null,
                    authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("Jwt", jwt);

            return LoginRequestStatus.LOGIN;
        }
    }

    private List<String> parseJwt(String jwt) throws JsonProcessingException {
        String[] jwtSection = jwt.split("\\.");
        // JWT 는 header, payload, signature 가 "." 으로 연결됨 (header.payload.signature)
        String jwtPayload = jwtSection[1];

        byte[] decode = Base64.getDecoder().decode(jwtPayload);
        JwtPayload payload = new ObjectMapper().readValue(new String(decode, StandardCharsets.UTF_8), JwtPayload.class);

        return payload.getRoles();
    }

    private ResponseEntity doLogin(LoginRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(
                gatewayUrl + "/auth/login",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

    @Override
    public void logout(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)) {
            // TODO: Redis에 들어있는 토큰을 지우기
            redisTemplate.opsForHash().delete(authentication.getName(), "REFRESH_TOKEN");
            session.invalidate();
            SecurityContextHolder.clearContext();
        }
    }
}
