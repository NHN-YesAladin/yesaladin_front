package shop.yesaladin.front.member.exception;

/**
 * 로그인 요청 시 잘못된 입력 값일 경우 발생하는 예외 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
public class InvalidLoginRequestException extends RuntimeException {

    private static final String MESSAGE = "INVALID LOGIN ID, PASSWORD";

    public InvalidLoginRequestException() {
        super(MESSAGE);
    }
}
