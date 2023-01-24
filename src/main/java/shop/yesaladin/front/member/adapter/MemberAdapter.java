package shop.yesaladin.front.member.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.LoginRequest;
import shop.yesaladin.front.member.dto.MemberResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAdapter {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;

    /**
     * 로그인 과정에서 Auth 서버에서 인증된 JWT 형식의 accessToken과 uuid를 받아오는 기능입니다.
     * 해당 정보들은 HTTP Response Header에 담겨 반환 됩니다.
     *
     * @param loginRequest 회원이 로그인 시 입력한 정보를 담은 DTO 입니다.
     * @return Auth 서버에서 발급받은 JWT 형식의 accessToken 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    public ResponseEntity<Void> getAuthInfo(LoginRequest loginRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, headers);

        return restTemplate.exchange(
                gatewayConfig.getAuthUrl() + "/login",
                HttpMethod.POST,
                entity,
                Void.class
        );
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
    public ResponseEntity<MemberResponse> getMemberInfo(
            LoginRequest loginRequest,
            String accessToken
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        return restTemplate.getForEntity(
                gatewayConfig.getShopUrl() + "/v1/members/login/{loginId}",
                MemberResponse.class,
                loginRequest.getLoginId(),
                httpHeaders
        );
    }
}
