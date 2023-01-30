package shop.yesaladin.front.common.exception;

/**
 * HTTP Header에 들어있는 정보를 파싱하고자 할 때, 유효하지 않은 경우 발생하는 예외 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
public class InvalidHttpHeaderException extends RuntimeException {

    public InvalidHttpHeaderException(String message) {
        super(message);
    }
}
