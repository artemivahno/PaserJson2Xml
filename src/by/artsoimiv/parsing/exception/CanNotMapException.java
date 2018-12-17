package by.artsoimiv.parsing.exception;

public class CanNotMapException extends RuntimeException{
    public CanNotMapException(String message) {
        super(message);
    }

    public CanNotMapException(Throwable cause) {
        super(cause);
    }
}
