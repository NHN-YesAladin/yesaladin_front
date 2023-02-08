package shop.yesaladin.front.member.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.LoginRequestDto;
import shop.yesaladin.front.member.dto.LogoutRequestDto;
import shop.yesaladin.front.member.dto.MemberResponseDto;

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
     * @param loginRequestDto 회원이 로그인 시 입력한 정보를 담은 DTO 입니다.
     * @return Auth 서버에서 발급받은 JWT 형식의 accessToken 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    public ResponseEntity<Void> getAuthInfo(LoginRequestDto loginRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<LoginRequestDto> entity = new HttpEntity<>(loginRequestDto, headers);

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
     * @param loginRequestDto 회원이 로그인 시 입력한 정보를 담은 DTO 입니다.
     * @param accessToken  로그인 과정에서 Auth 서버에서 발급받은 JWT 형식의 accessToken 입니다.
     * @return Shop 서버에 요청한 회원의 정보 DTO를 담은 결과 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    public ResponseEntity<ResponseDto<MemberResponseDto>> getMemberInfo(
            LoginRequestDto loginRequestDto,
            String accessToken
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/login/{loginId}")
                .encode()
                .build()
                .expand(loginRequestDto.getLoginId())
                .toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );
    }

    /**
     * Auth 서버에 logout 요청을 하기 위한 기능 입니다.
     * Auth 서버는 식별 키를 받아 Redis에 저장된 로그인 된 회원의 정보를 제거 합니다.
     *
     * @param uuid 로그인 한 사용자가 가진 유일한 식별 값
     * @author 송학현
     * @since 1.0
     */
    public void logout(String uuid, String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        LogoutRequestDto logoutRequest = new LogoutRequestDto(uuid);

        log.info("request={}", logoutRequest);
        HttpEntity<LogoutRequestDto> entity = new HttpEntity<>(logoutRequest, httpHeaders);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getAuthUrl())
                .path("/logout")
                .encode()
                .build()
                .toUri();

        restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

    /**
     * Auth 서버에 토큰 재발급 요청을 하기 위한 기능입니다.
     *
     * @param uuid 회원의 고유 식별자에 해당합니다.
     * @return 재발급된 JWT AccessToken을 반환합니다.
     * @author 송학현
     * @since 1.0
     */
    public ResponseEntity<Void> tokenReissue(String uuid) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        httpHeaders.add("UUID", uuid);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getAuthUrl())
                .path("/reissue")
                .encode()
                .build()
                .toUri();

        HttpEntity entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                Void.class
        );
    }
}
