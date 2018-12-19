package by.artsoimiv;

import by.artsoimiv.parsing.JsonFactory.JsonFactory;
import by.artsoimiv.parsing.JsonFactory.JsonObject;
import by.artsoimiv.parsing.JsonParser;
import by.artsoimiv.parsing.pojo.PojoMapper;

public class Main {

    //Задача: Написать конвертер с Json в Xml без использования доп.библиотек
    //валидация json файла
    //источник - локальный файл
    //сохраняем - в локальный файл
    //


    public static void main(String[] args) {

        JsonFileReader jsonFileReader = new JsonFileReader();

       // JsonObject o = (JsonObject)JsonParser.parse(jsonFileReader);

        System.out.println("Конец " );
    }
}
