package by.artsoimiv;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class JsonFileReader {

    private static final String FILENAME = "h:\\Dropbox\\My Files\\_Java\\";

    private void readJson(){
        final String filepath = FILENAME;

        try {
            final FileReader jsonFile = new FileReader(filepath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    String jsonStr = "{\"Customer\": {" +
            "\"address\": {" +
            "\"street\": \"NANTERRE CT\"," +
            "\"postcode\": 77471" +
            "}," +
            "\"name\": \"Mary\"," +
            "\"age\": 37" +
            "}}";

//    JSONObject json = new JSONObject(jsonStr);
//    String xml = XML.toString(json);
//
//System.out.println(xml);
//
//    // 2. Convert Json File -> XML File
//    String jsonFile = System.getProperty("user.dir") + "\\file.json";
//    String xmlFile = System.getProperty("user.dir") + "\\file.xml";
//
//    jsonStr = new String(Files.readAllBytes(Paths.get(jsonFile)));
//    json = new JSONObject(jsonStr);
//
//try (FileWriter fileWriter = new FileWriter(xmlFile)){
//        fileWriter.write(XML.toString(json));
//    }
//
//    jsonStr = new String(Files.readAllBytes(Paths.get(jsonFile)));
//    json = new JSONObject(jsonStr);
//
//		try (FileWriter fileWriter = new FileWriter(xmlFile)){
//        fileWriter.write(XML.toString(json));
//    }



}

