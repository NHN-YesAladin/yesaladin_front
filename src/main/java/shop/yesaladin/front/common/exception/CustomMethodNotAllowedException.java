package shop.yesaladin.front.common.exception;

/**
 * Back Server에서 발생하는 Method Not Allowed 예외를 잡기 위한 Custom Exception 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
public class CustomMethodNotAllowedException extends RuntimeException {

    public CustomMethodNotAllowedException(String message) {
        super(message);
    }
}
