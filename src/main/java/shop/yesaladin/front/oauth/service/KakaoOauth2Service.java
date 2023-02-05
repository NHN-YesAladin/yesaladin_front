package shop.yesaladin.front.oauth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.oauth.adapter.Oauth2Adapter;
import shop.yesaladin.front.oauth.dto.Oauth2LoginRequestDto;

/**
 * Kakao OAuth2 로그인을 위한 서비스 클래스 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@Service
public class KakaoOauth2Service extends Oauth2Service {

    @Value("${oauth.kakao.clientId}")
    private String clientId;
    @Value("${oauth.kakao.secret}")
    private String secret;
    @Value("${oauth.kakao.redirectUrl}")
    private String redirectUrl;

    public KakaoOauth2Service(
            Oauth2Adapter oauth2Adapter,
            ObjectMapper objectMapper
    ) {
        super(oauth2Adapter, objectMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOAuth2ProcessingUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("kauth.kakao.com")
                .path("oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("response_type", "code")
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String tokenProcessingUrl(String code) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("kauth.kakao.com")
                .path("oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", secret)
                .queryParam("code", code)
                .queryParam("redirect_uri", redirectUrl)
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserInfoProcessingUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("kapi.kakao.com")
                .path("v2/user/me")
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Oauth2LoginRequestDto createOauth2Dto(Map<String, Object> userInfo) {
        Map<String, String> kakaoAccount = (Map) userInfo.get("kakao_account");
        String email = kakaoAccount.get("email");
        if (Objects.isNull(email)) {
            email = UUID.randomUUID() + "@kakao.com";
            return new Oauth2LoginRequestDto(email);
        }
        return new Oauth2LoginRequestDto(email);
    }
}
