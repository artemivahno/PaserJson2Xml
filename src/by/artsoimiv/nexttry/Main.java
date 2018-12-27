package by.artsoimiv.nexttry;

public class Main {

    //Задача: Написать конвертер с Json в Xml без использования доп.библиотек
    //валидация json файла
    //источник - локальный файл
    //сохраняем - в локальный файл
    //


    public static void main(String[] args) {

        JsonFileReader readJson = new JsonFileReader("by/artsoimiv/recources/sample.json");
        System.out.println(readJson.getFile("by/artsoimiv/recources/sample.json"));


        System.out.println("Конец " );
    }
}
