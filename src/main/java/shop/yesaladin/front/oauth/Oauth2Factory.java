package shop.yesaladin.front.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.yesaladin.front.oauth.service.GithubOauth2Service;
import shop.yesaladin.front.oauth.service.Oauth2Service;

/**
 * OAuth2 Provider 종류에 따라 Service를 반환하는 factory 클래스 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class Oauth2Factory {

    private final GithubOauth2Service githubOauth2Service;

    /**
     * OAuth2 Service Provider의 종류에 따라 해당 서비스를 반환하기 위한 기능입니다.
     *
     * @param oauthProvider OAuth2 provider의 이름
     * @return 해당 OAuth2Service
     * @author 송학현
     * @since 1.0
     */
    public Oauth2Service getOauth2Service(String oauthProvider) {
        if (oauthProvider.equals("github")) {
            return githubOauth2Service;
        } else if (oauthProvider.equals("naver")) {
            return null;
        } else if (oauthProvider.equals("kakao")) {
            return null;
        } else {
            throw new RuntimeException("OAuth Service not found");
        }
    }
}
