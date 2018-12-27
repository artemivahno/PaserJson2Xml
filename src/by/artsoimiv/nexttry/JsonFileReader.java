package by.artsoimiv.nexttry;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class JsonFileReader {

    private String jsonInput;

    private String jsonStrigFromFile;


    public JsonFileReader(String input) {
        this.jsonInput = input;
    }

    public String getJsonStrigFromFile() {
        return jsonStrigFromFile;
    }

    public void setJsonStrigFromFile(String jsonStrigFromFile) {
        this.jsonStrigFromFile = jsonStrigFromFile;
        jsonInput=(getFile("by/artsoimiv/recources/sample.json"));
    }

    public String getFile(String input) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(input).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }




}

