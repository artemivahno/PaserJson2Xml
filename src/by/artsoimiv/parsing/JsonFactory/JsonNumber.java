package by.artsoimiv.parsing.JsonFactory;

import java.io.PrintWriter;

public class JsonNumber extends JsonValue{

    final private Number value;

    public JsonNumber(Number value) {
        if (value == null) {
            throw new NullPointerException(" Используй JsonNull c null");
        }
        this.value = value;
    }

    @Override
    public Object javaObjectValue() {
        return null;
    }

    @Override
    public void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount) {
        printWriter.append(stringValue());
    }

    @Override
    public JsonNode deepClone() {
        return this;
    }
}
