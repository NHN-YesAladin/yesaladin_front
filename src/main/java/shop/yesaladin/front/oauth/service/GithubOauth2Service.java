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
                .scheme("https")
                .host("github.com")
                .path("login/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUrl)
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String tokenProcessingUrl(String code) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("github.com")
                .path("login/oauth/access_token")
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
                .host("api.github.com")
                .path("user")
                .build().toUriString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Oauth2LoginRequestDto createOauth2Dto(Map<String, Object> userInfo) {
        String email = userInfo.get("email").toString();
        if (Objects.isNull(email)) {
            email = UUID.randomUUID() + "@github.com";
            return new Oauth2LoginRequestDto(email);
        }
        return new Oauth2LoginRequestDto(email);
    }
}
