package shop.yesaladin.front.member.exception;

public class InvalidLoginRequestException extends RuntimeException {

    private static final String MESSAGE = "INVALID LOGIN ID, PASSWORD";

    public InvalidLoginRequestException() {
        super(MESSAGE);
    }
}
