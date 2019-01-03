package by.artsoimiv.nexttry.utils;

import java.io.*;

public class FileUtil {

    private final static String jsonFileName = "E:\\Java\\PaserJson2Xml\\src\\by\\artsoimiv\\recources\\sample1.json";
    private final static String xmlFileName = "E:\\Java\\PaserJson2Xml\\src\\by\\artsoimiv\\recources\\result1.xml";

    public static void writeXml(String text)
    {
        // write the content in file
        try (FileWriter fileWriter = new FileWriter(xmlFileName))
        {
            fileWriter.write(text);
        }
        catch (IOException e)
        {
            // exception handling
        }
    }

    public static String readJson()
    {   BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(jsonFileName));
            String line = bufferedReader.readLine();
            while(line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // exception handling
        } catch (IOException e) {
            // exception handling
        }
        return stringBuilder.toString();
    }
}
