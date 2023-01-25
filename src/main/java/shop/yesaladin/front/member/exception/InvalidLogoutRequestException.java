package shop.yesaladin.front.member.exception;

/**
 * 잘못된 로그아웃 요청 시 발생하는 예외입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
public class InvalidLogoutRequestException extends RuntimeException {

    private static final String MESSAGE = "Already logged out";

    public InvalidLogoutRequestException() {
        super(MESSAGE);
    }
}
