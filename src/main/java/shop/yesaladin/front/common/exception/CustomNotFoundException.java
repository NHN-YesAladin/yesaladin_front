package shop.yesaladin.front.common.exception;

/**
 * Back Server에서 발생하는 Not Found 예외를 잡기 위한 Custom Exception 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
public class CustomNotFoundException extends RuntimeException {

    public CustomNotFoundException(String message) {
        super(message);
    }
}
