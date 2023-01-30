package shop.yesaladin.front.member.adapter;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.LoginRequest;
import shop.yesaladin.front.member.dto.MemberResponse;

/**
 * 회원 관련 로직을 처리하기 위해 Shop, Auth 서버에 RestTemplate으로 요청하기 위한 어댑터 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
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
                gatewayConfig.getAuthUrl() + "/auth/login",
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
    public ResponseEntity<ResponseDto<MemberResponse>> getMemberInfo(
            LoginRequest loginRequest,
            String accessToken
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/login/{loginId}")
                .encode()
                .build()
                .expand(loginRequest.getLoginId())
                .toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
