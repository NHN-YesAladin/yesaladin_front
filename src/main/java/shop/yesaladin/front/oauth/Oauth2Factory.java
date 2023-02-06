package shop.yesaladin.front.oauth;

import static shop.yesaladin.front.oauth.util.Oauth2Utils.GITHUB;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.KAKAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.yesaladin.front.oauth.exception.Oauth2ServiceNotProvidedException;
import shop.yesaladin.front.oauth.service.GithubOauth2Service;
import shop.yesaladin.front.oauth.service.KakaoOauth2Service;
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
    private final KakaoOauth2Service kakaoOauth2Service;

    /**
     * OAuth2 Service Provider의 종류에 따라 해당 서비스를 반환하기 위한 기능입니다.
     *
     * @param oauthProvider OAuth2 provider의 이름
     * @return 해당 OAuth2Service
     * @author 송학현
     * @since 1.0
     */
    public Oauth2Service getOauth2Service(String oauthProvider) {
        if (oauthProvider.equals(GITHUB.getValue())) {
            return githubOauth2Service;
        } else if (oauthProvider.equals(KAKAO.getValue())) {
            return kakaoOauth2Service;
        } else {
            throw new Oauth2ServiceNotProvidedException();
        }
    }
}
