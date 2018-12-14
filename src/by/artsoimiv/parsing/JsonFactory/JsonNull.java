package by.artsoimiv.parsing.JsonFactory;

import by.artsoimiv.parsing.Exception.JsonValueNotPresentException;

import java.io.PrintWriter;

public class JsonNull extends JsonValue {
    public JsonNull(){
    }

    @Override
    public String stringValue() throws JsonValueNotPresentException {
        return super.stringValue();
    }

    @Override
    public JsonNode deepClone() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public Object javaObjectValue() {
        return null;
    }

    @Override
    public void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount) {

    }
}
