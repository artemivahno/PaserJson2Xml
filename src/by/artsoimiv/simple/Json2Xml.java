package by.artsoimiv.simple;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Json2Xml {

    public static void main(String[] args) {
        new Json2Xml().run();
    }

    private void run() {
        try {
            XmlFunctions xmlFunctions = new XmlFunctions();
            JsonFunctions jsonFunctions = new JsonFunctions(xmlFunctions);


            Scanner scanner = new Scanner(new File("test.json"));

            String json = "";
            while (scanner.hasNext()) {
                json += scanner.next();
            }

            if (jsonFunctions.checkJson(json)) {
                json = json.replaceAll("\"", "");
                json = json.replaceAll("\n", "");
                json = json.replaceAll(" ", "");
                json = jsonFunctions.removeFirstChar(json);
                json = jsonFunctions.removeLastChar(json);
                jsonFunctions.parseStart(json);
//вызов функции формирования из мапы
                System.out.println(xmlFunctions.getXml());
                PrintStream out = new PrintStream(new FileOutputStream("result.xml"));
                out.print(xmlFunctions.getXml());
            } else {
                System.out.println("Error! Please check your JSON!");
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
