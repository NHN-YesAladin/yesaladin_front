package shop.yesaladin.front.oauth.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Oauth2Utils {
    GITHUB("github"),
    KAKAO("kakao"),
    YESALADIN_EMAIL("@yesaladin.com"),
    ACCESS_TOKEN("access_token"),
    KAKAO_ACCOUNT("kakao_account"),
    EMAIL("email"),
    HTTPS("https"),
    GITHUB_HOST("github.com"),
    GITHUB_API_HOST("api.github.com"),
    KAKAO_HOST("kauth.kakao.com"),
    CLIENT_ID("client_id"),
    CLIENT_SECRET("client_secret"),
    REDIRECT_URI("redirect_uri"),
    CODE("code");

    private String value;
}
