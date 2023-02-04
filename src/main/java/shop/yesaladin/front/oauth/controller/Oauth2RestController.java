package shop.yesaladin.front.oauth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.oauth.Oauth2Factory;
import shop.yesaladin.front.oauth.service.Oauth2Service;

/**
 * Login Page에서 클릭한 OAuth2 로그인 버튼에 따라 해당 OAuth2 로그인을 수행하기 위한 기능 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class Oauth2RestController {

    private final Oauth2Factory oauth2Factory;

    /**
     * OAuth2 Login 수행을 위해 해당 url을 반환하기 위한 기능입니다.
     *
     * @param oauthProvider OAuth2 provider의 종류
     * @param response      HttpServletResponse
     * @return OAuth2 Provider의 종류에 따라 로그인 수행을 위한 url
     * @author 송학현
     * @since 1.0
     */
    @GetMapping("/redirect-url")
    public String getRedirectUrl(String oauthProvider, HttpServletResponse response) {
        log.info("provider={}", oauthProvider);
        Oauth2Service oauth2Service = oauth2Factory.getOauth2Service(oauthProvider);

        setProviderCookie(oauthProvider, response);
        return oauth2Service.getOAuth2ProcessingUrl();
    }

    /**
     * OAuth2 Provider의 종류를 담은 Cookie를 만드는 기능입니다.
     *
     * @param oauthProvider OAuth2 Provider의 종류
     * @param response HttpServletResponse
     * @author 송학현
     * @since 1.0
     */
    private void setProviderCookie(String oauthProvider, HttpServletResponse response) {
        Cookie cookie = new Cookie("provider", oauthProvider);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }
}
