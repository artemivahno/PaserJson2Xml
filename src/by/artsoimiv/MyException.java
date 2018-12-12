package by.artsoimiv;

public class MyException extends RuntimeException{

    public MyException(final String message) {
        super(message);
    }

    public MyException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }

}
