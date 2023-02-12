package shop.yesaladin.front.common.exception;

/**
 * Back Server에서 발생하는 인증 예외를 잡기 위한 Custom Exception 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
public class CustomUnauthorizedException extends RuntimeException {

    public CustomUnauthorizedException(String message) {
        super(message);
    }
}
