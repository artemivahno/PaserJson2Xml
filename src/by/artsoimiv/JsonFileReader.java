package by.artsoimiv;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
}
