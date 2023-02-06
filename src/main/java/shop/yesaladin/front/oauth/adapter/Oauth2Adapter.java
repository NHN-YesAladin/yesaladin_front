package shop.yesaladin.front.oauth.adapter;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import shop.yesaladin.front.member.dto.MemberProfileExistResponseDto;
import shop.yesaladin.front.member.dto.MemberResponseDto;

/**
 * OAuth2 Login 기능을 수행하기 위한 adapter 클래스 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class Oauth2Adapter {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;

    /**
     * OAuth2에서 accessToken을 발급 받기 위해 요청을 수행하는 기능 입니다.
     *
     * @param url accessToken을 발급 받기 위한 경로 입니다.
     * @return OAuth2에서 인증된 사용자임을 나타내는 accessToken
     * @author 송학현
     * @since 1.0
     */
    public ResponseEntity<String> getAccessToken(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );
    }

    /**
     * OAuth2에 사용자 정보 요청을 수행하는 기능입니다.
     *
     * @param token OAuth2에서 발급 받은 accessToken
     * @param userInfoProcessingUrl OAuth2에서 제공하는 사용자 정보 요청 url
     * @return 사용자 정보
     * @author 송학현
     * @since 1.0
     */
    public ResponseEntity<String> getUser(String token, String userInfoProcessingUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        return restTemplate.exchange(
                userInfoProcessingUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
    }

    /**
     * YesAladin 자사 회원 인지 판별하기 위한 기능 입니다.
     *
     * @param email OAuth2에서 회원 정보 조회 시 가져온 email 입니다.
     * @return 해당 유저가 YesAladin 자사 회원 인지에 대한 결과
     * @author 송학현
     * @since 1.0
     */
    public boolean isAlreadyMember(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkEmail/{email}")
                .encode()
                .build()
                .expand(email)
                .toUri();

        ResponseEntity<ResponseDto<MemberProfileExistResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody().getData().isResult();
    }

    /**
     * OAuth2 로그인 시 email 기준으로 자사 회원에 등록되어 있는 경우 해당 회원 정보를 불러오기 위한 기능입니다.
     *
     * @param email 조회 대상 회원의 email
     * @return YesAladin 회원 정보
     * @author 송학현
     * @since 1.0
     */
    public ResponseEntity<ResponseDto<MemberResponseDto>> getYesaladinMember(
            String email
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/oauth2/login/{email}")
                .encode()
                .build()
                .expand(email)
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
