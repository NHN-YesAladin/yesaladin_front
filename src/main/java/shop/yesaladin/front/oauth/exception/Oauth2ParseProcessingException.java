package shop.yesaladin.front.oauth.exception;

/**
 * OAuth2에서 사용자 정보를 불러오는 과정 에서 parsing 에러 발생 시 나타나는 예외 클래스
 *
 * @author 송학현
 * @since 1.0
 */
public class Oauth2ParseProcessingException extends RuntimeException {

    private static final String MESSAGE = "OAuth2에서 사용자 정보를 불러오는 과정 에서 parsing 에러 발생";

    public Oauth2ParseProcessingException() {
        super(MESSAGE);
    }
}
