package by.artsoimiv.parsing.JsonFactory;

import java.io.PrintWriter;

public class JsonBoolean extends JsonValue{
    private final boolean value;

    public JsonBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String stringValue() {
        return Boolean.toString(value);
    }

    @Override
    public Object javaObjectValue() {
        return null;
    }

    @Override
    public void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount) {
        printWriter.append(stringValue());
    }

    public boolean booleanValue(){
        return value;
    }

    @Override
    public JsonNode deepClone() {
        return null;
    }
}
