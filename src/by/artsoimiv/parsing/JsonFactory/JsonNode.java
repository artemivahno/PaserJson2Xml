package by.artsoimiv.parsing.JsonFactory;

import by.artsoimiv.parsing.Exception.JsonValueNotPresentException;

import java.io.PrintWriter;
import java.io.StringWriter;

//Общий супер класс всех элементов Json.
// JsonNode может быть объектом (JsonArray, JsonObject) или значением (string, number, boolean).

public abstract class JsonNode {

    //Значение в виде строки JSON
    public String toJson() {
        StringWriter result = new StringWriter();
        toJson(new PrintWriter(result), "", "");
        return result.toString();
    }

    public String toIndentedJson(String indentationAmount){
        StringWriter res = new StringWriter();
        toJson(new PrintWriter(res),"",indentationAmount);
        return res.toString();
    }

//Записывает эти объекты как JSON для данного writer
    public abstract void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount);

    public void toJson(PrintWriter printWriter){
        toJson(printWriter,"","");
    }

    public String stringValue() throws JsonValueNotPresentException {
        throw new JsonValueNotPresentException(String.format("Не поддерживаемый класс %s", getClass().getSimpleName()));
    }

    public String toString(){
        return toJson();
    }

    public abstract JsonNode deepClone();

//проверяет является ли node объектом
// если да - JsonObject иначе false
    public boolean isObject(){
        return (this instanceof JsonObject);
    }

//проверяет является ли node array
// если да - JsonArray иначе false
    public boolean isArray() {
        return (this instanceof JsonArray);
    }
}
