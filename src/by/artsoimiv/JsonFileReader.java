package by.artsoimiv;


import java.io.FileNotFoundException;
import java.io.FileReader;


public class JsonFileReader {

    private static final String FILENAME = "h:\\Dropbox\\My Files\\_Java\\friends.json";

    public void readJson(){
        final String filepath = FILENAME;

        try {
            final FileReader jsonString = new FileReader(filepath);

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

