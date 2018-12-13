package by.artsoimiv;

import java.io.FileNotFoundException;

public class MyException extends Exception {

    public String toString () {
        return "\nОчередь пуста.";
}

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
