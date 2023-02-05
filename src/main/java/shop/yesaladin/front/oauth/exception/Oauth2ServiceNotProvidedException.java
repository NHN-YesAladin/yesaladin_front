package shop.yesaladin.front.oauth.exception;

/**
 * OAuth2 Login Service가 제공되지 않는 경우 발생하는 예외
 *
 * @author 송학현
 * @since 1.0
 */
public class Oauth2ServiceNotProvidedException extends RuntimeException {

    private static final String OAUTH_SERVICE_NOT_PROVIDED = "OAuth Service Not Provided";

    public Oauth2ServiceNotProvidedException() {
        super(OAUTH_SERVICE_NOT_PROVIDED);
    }
}
