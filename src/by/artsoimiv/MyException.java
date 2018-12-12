package by.artsoimiv;

import java.io.FileNotFoundException;

public class MyException extends FileNotFoundException {

    public MyException(final String message) {
        super(message);
    }
}
