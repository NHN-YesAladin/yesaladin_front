package shop.yesaladin.front.oauth.service;

import static shop.yesaladin.front.oauth.util.Oauth2Utils.CLIENT_ID;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.CLIENT_SECRET;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.CODE;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.HTTPS;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.KAKAO_ACCOUNT;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.KAKAO_HOST;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.REDIRECT_URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
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
                .scheme(HTTPS.getValue())
                .host(KAKAO_HOST.getValue())
                .path("oauth/authorize")
                .queryParam(CLIENT_ID.getValue(), clientId)
                .queryParam(REDIRECT_URI.getValue(), redirectUrl)
                .queryParam("response_type", "code")
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String tokenProcessingUrl(String code) {
        return UriComponentsBuilder.newInstance()
                .scheme(HTTPS.getValue())
                .host(KAKAO_HOST.getValue())
                .path("oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam(CLIENT_ID.getValue(), clientId)
                .queryParam(CLIENT_SECRET.getValue(), secret)
                .queryParam(CODE.getValue(), code)
                .queryParam(REDIRECT_URI.getValue(), redirectUrl)
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserInfoProcessingUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme(HTTPS.getValue())
                .host(KAKAO_HOST.getValue())
                .path("v2/user/me")
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Oauth2LoginRequestDto createOauth2Dto(Map<String, Object> userInfo) {
        Map<String, String> kakaoAccount = (Map) userInfo.get(KAKAO_ACCOUNT.getValue());
        String email = kakaoAccount.get("email");
        return new Oauth2LoginRequestDto(email);
    }
}
