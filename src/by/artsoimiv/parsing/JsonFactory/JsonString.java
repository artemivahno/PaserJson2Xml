package by.artsoimiv.parsing.JsonFactory;

import java.io.PrintWriter;

public class JsonString extends JsonValue{

    private final String value;

    public JsonString(String value) {
        this.value = value;
    }

    @Override
    public Object javaObjectValue() {
        return null;
    }

    @Override
    public void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount) {

    }

    @Override
    public JsonNode deepClone() {
        return null;
    }
}
