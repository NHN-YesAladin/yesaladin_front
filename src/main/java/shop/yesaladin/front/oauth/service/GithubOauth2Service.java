package shop.yesaladin.front.oauth.service;

import static shop.yesaladin.front.oauth.util.Oauth2Utils.CLIENT_ID;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.CLIENT_SECRET;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.CODE;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.EMAIL;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.GITHUB_API_HOST;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.GITHUB_HOST;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.HTTPS;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.REDIRECT_URI;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.YESALADIN_EMAIL;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.oauth.adapter.Oauth2Adapter;
import shop.yesaladin.front.oauth.dto.Oauth2LoginRequestDto;

/**
 * Github OAuth2 로그인을 위한 서비스 클래스 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@Service
public class GithubOauth2Service extends Oauth2Service {

    @Value("${oauth.github.clientId}")
    private String clientId;
    @Value("${oauth.github.secret}")
    private String secret;
    @Value("${oauth.github.redirectUrl}")
    private String redirectUrl;

    public GithubOauth2Service(
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
                .host(GITHUB_HOST.getValue())
                .path("login/oauth/authorize")
                .queryParam(CLIENT_ID.getValue(), clientId)
                .queryParam(REDIRECT_URI.getValue(), redirectUrl)
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String tokenProcessingUrl(String code) {
        return UriComponentsBuilder.newInstance()
                .scheme(HTTPS.getValue())
                .host(GITHUB_HOST.getValue())
                .path("login/oauth/access_token")
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
                .host(GITHUB_API_HOST.getValue())
                .path("user")
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Oauth2LoginRequestDto createOauth2Dto(Map<String, Object> userInfo) {
        Object email = userInfo.get(EMAIL.getValue());
        if (!Objects.isNull(email)) {
            return new Oauth2LoginRequestDto(email.toString());
        }
        String loginId = userInfo.get("login").toString();
        return new Oauth2LoginRequestDto(loginId + YESALADIN_EMAIL.getValue());
    }
}
