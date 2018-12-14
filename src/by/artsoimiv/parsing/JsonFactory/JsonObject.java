package by.artsoimiv.parsing.JsonFactory;

import by.artsoimiv.parsing.Exception.JsonConversionException;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

//JsonObject - класс представляет словарь значений по строке ключи.  Каждое значение может быть JsonArray, числом, строкой,
// логическое значение или другим объектом. Например: <code>{"foo":"string", "number":123, "really":false]}</code>,
// <code>requiredString("foo")</code> который возвращает 'string' и  <code>requiredLong("number")</code> вернет 123.


public class JsonObject extends JsonNode{

    private final Map<String, JsonNode> values;

    public JsonObject() {
        this.values = new LinkedHashMap<>();
    }

    public JsonObject(Map<String, JsonNode> values) {
        this.values = values;
    }

//Возвращает значение ключа аргумента в виде строки или пустого значения Optional - если ключ отсутствует. Кидает
// JsonConversionException если значение JsonArray или JsonObject.

//    public Optional<String> stringValue(String key) throws JsonConversionException {
//        return get(key, JsonValue.class).map(JsonValue::stringValue);
//    }

//Возвращает значение ключа аргумента в качестве типа аргумента или пустого Optional если ключ отсутствует.
// кидает JsonConversionException - если значение неопределенного типа
//    private <T> Optional<T> get(String key, Class<T> t) throws JsonConversionException{
//        Optional<JsonNode> value = value(key);
//
//    }

    @Override
    public void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount) {

    }

    @Override
    public JsonNode deepClone() {
        return null;

    }

}
