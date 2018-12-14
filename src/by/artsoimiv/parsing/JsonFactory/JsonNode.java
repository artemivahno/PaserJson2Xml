package by.artsoimiv.parsing.JsonFactory;

import by.artsoimiv.parsing.Exception.JsonValueNotPresentException;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class JsonNode {

    /**
     * The value as a JSON string
     */
    public String toJson() {
        StringWriter result = new StringWriter();
        toJson(new PrintWriter(result), "", "");
        return result.toString();
    }

    public abstract void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount);

    public void toJson(PrintWriter printWriter){
        toJson(printWriter,"","");
    }

    public String toString(){
        return toJson();
    }

    public String stringValue() throws JsonValueNotPresentException{
        throw new JsonValueNotPresentException(String.format("Не поддержвается классом %s ",getClass().getSimpleName()));
    }

    public abstract JsonNode deepClone();

    public boolean isObject(){
        return (this instanceof JsonObject);
    }
}
