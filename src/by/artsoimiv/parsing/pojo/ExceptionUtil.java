package by.artsoimiv.parsing.pojo;

public class ExceptionUtil {

    //Бросает проверенное исключение как исключение без проверки без уведомления компилятора. Это возможно из-за
    // стирания обобщенных типов и того факта, что проверка исключений происходит только во время выполнения.
    static RuntimeException soften(Exception e){
        return softenHelper(e);
    }
    private static <T extends Exception> T softenHelper(Exception e) throws T {
        throw (T)e;
    }
}
