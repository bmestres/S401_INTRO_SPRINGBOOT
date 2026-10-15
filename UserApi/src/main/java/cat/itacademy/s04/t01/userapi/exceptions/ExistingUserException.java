package cat.itacademy.s04.t01.userapi.exceptions;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException(String message) {
        super(message);
    }
}
