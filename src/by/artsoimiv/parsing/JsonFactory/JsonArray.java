package by.artsoimiv.parsing.JsonFactory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonArray extends JsonNode implements Iterable<JsonNode>{

    private final List<JsonNode> values;

// Создает пустой JsonArray
    public JsonArray() {
        values = new ArrayList<>();
    }

    public JsonArray(List<? extends JsonNode> nodes) {
        this.values = new ArrayList<>(nodes);
    }

// Создает JsonArray c nodes в качестве аргументов листа
    public static JsonArray fromNodeList(List<? extends JsonNode> nodes) {
        return new JsonArray(nodes);
    }

    //Создает JsonArray из потока аргументов
    public static JsonArray fromNodeStrim(Stream<? extends JsonNode> nodes){
        JsonArray jsonNodes = new JsonArray();
        nodes.forEach(jsonNodes::add);
        return jsonNodes;
    }

//Добавляет аргумент в конце JsonArray
    public JsonArray add (Object o){
        values.add(JsonFactory.jsonNode(o));
        return this;
    }

//Добавляет аргумент в конце JsonArray
    public JsonArray addAll (List<String> values){
    this.values.addAll(values.stream().map(JsonFactory::jsonString).collect(Collectors.toList()));
    return this;
}

    @Override
    public void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount) {

    }

    @Override
    public JsonNode deepClone() {
        return null;
    }

    @Override
    public Iterator<JsonNode> iterator() {
        return null;
    }
}
