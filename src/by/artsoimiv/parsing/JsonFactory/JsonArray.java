package by.artsoimiv.parsing.JsonFactory;

import by.artsoimiv.parsing.exception.JsonConversionException;
import by.artsoimiv.parsing.exception.JsonValueNotPresentException;

import java.io.PrintWriter;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
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
    public static JsonArray fromNodeStream(Stream<? extends JsonNode> nodes){
        JsonArray jsonNodes = new JsonArray();
        nodes.forEach(jsonNodes::add);
        return jsonNodes;
    }

// Создает JsonArray из Strings
    public static JsonArray fromStrings(String... strings){
        return fromStringList(Arrays.asList(strings));
    }
    public static JsonArray fromStringList(List<String> nodes){
        if (nodes == null) {
            return new JsonArray();
        }
        return new JsonArray(nodes.stream().map(JsonString::new).collect(Collectors.toList()));
    }

//* Собирает поток аргументов в JsonArray со строками
    public static JsonArray fromStringStream(Stream<String> nodes){
        return new JsonArray(nodes.map(JsonString::new).collect(Collectors.toList()));
    }

//* Сопоставляет значения с функцией и возвращает JsonArray с результатами.
    public static <T> JsonArray map(Collection<T> values, Function<T, JsonNode> f){
        return fromNodeStream(values.stream().map(o -> f.apply(o)));
    }

//Возвращает список этого JsonArray JsonObjects, сопоставленных с функцией. Пропускает значения,
// которые не являются JsonObjects.
    public <T> List<T> objects(Function<JsonObject,T> mapFunc) {
        return nodeStream()
                .filter(n -> n instanceof JsonObject)
                .map(n -> (JsonObject) n)
                .map(mapFunc)
                .collect(Collectors.toList());
    }

//Возвращает список всех строковых значений членов этого JsonArray, которые не являются JsonObjects или JsonArrays.
// Пропускает JsonObjects и JsonArrays
    public List<String> strings(){
        return stringStream().collect(Collectors.toList());
    }
//Возвращает поток всех строковых значений членов этого JsonArray, которые не являются JsonObjects или JsonArrays.
// Пропускает JsonObjects и JsonArrays. отфильтрованные значения, которые являются строками
    public Stream<String> stringStream() {
        return nodeStream()
                .filter(no -> no instanceof JsonValue)
                .map(no -> ((JsonValue) no).stringValue());
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

//Возвращает кол-во элементов в JsonArray
    public int size(){
        return values.size();
    }

//Возвращает значение в позиции аргумента как JsonArray. JsonConversionException если значение позиции не JsonArray.
    public JsonArray requiredArray(int pos) throws JsonConversionException {
        return get(pos, JsonArray.class);
    }

//Возвращает значение в позиции аргумента как JsonObject.
    public JsonObject requiredObject(int pos) throws JsonConversionException {
        return get(pos, JsonObject.class);
    }

//Возвращает значение в позиции аргумента в виде строки.
    public String requiredString(int pos) throws JsonConversionException {
        return get(pos, JsonValue.class).stringValue();
    }

//Возвращает значение в позиции аргумента как long.
    public long requiredLong(int pos) throws JsonConversionException {
        return requiredNumber(pos).longValue();
    }

//Возвращает значение в позиции аргумента как double.
    public double requiredDouble(int pos) throws JsonConversionException {
        return requiredNumber(pos).doubleValue();
    }

//Возвращает значение в позиции аргумента как число.
    public Number requiredNumber(int pos) throws JsonConversionException {
        return asNumber(get(pos));
    }

    private Number asNumber(JsonNode jsonNode) {
        if (jsonNode instanceof JsonNumber) {
            return (Number) ((JsonNumber)jsonNode).javaObjectValue();
        } else if (jsonNode instanceof JsonValue) {
            try {
                return Double.parseDouble(((JsonValue)jsonNode).stringValue());
            } catch (NumberFormatException e) {
                throw new JsonConversionException(jsonNode + " не число");
            }
        } else {
            throw new JsonConversionException(jsonNode + " не число");
        }
    }


//Возвращает значение в позиции аргумента boolean.
public boolean requiredBoolean(int pos) throws JsonConversionException {
    return asBoolean(get(pos));
}

    public boolean asBoolean(JsonNode jsonNode) {
        if (jsonNode instanceof JsonBoolean) {
            return ((JsonBoolean)jsonNode).booleanValue();
        } else if (jsonNode instanceof JsonValue) {
            return Boolean.parseBoolean(((JsonValue)jsonNode).stringValue());
        } else {
            throw new JsonConversionException(jsonNode + " не boolean");
        }
    }

//Возвращает значение в позиции аргумента как указанный тип Enum.
    public <T extends Enum<T>> T requiredEnum(int pos, Class<T> enumType) {
        return Enum.valueOf(enumType, requiredString(pos));
    }

//Возвращает значение в позиции аргумента как instant
    public Instant requiredInstant(int pos) {
        return Instant.parse(requiredString(pos));
    }

//Возвращает значение в позиции аргумента, преобразованное в класс аргумента.
    public <T> T get(int pos, Class<T> jsonClass) throws JsonConversionException, JsonValueNotPresentException {
        JsonNode jsonNode = get(pos);
        if (!jsonClass.isAssignableFrom(jsonNode.getClass())) {
            throw new JsonConversionException(String.format("Объект в array (%s) не является %s",
                    jsonNode.getClass().getName(),jsonClass.getName()));
        }
        return (T) jsonNode;
    }

    private JsonNode get(int pos) throws JsonValueNotPresentException{
        if (pos < 0 || pos >= size()) {
            throw new JsonValueNotPresentException("Json array не имеет значения в возиции "+pos);
        }
        return values.get(pos);
    }

//Возвращает true, если аргумент является JsonArray с тем же значения как этот объект
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonArray)) return false;
        JsonArray jsonArray = (JsonArray)o;
        return Objects.equals(values, jsonArray.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

//Записывает текстовое представление JSON этого JsonArray writer
    @Override
    public void toJson(PrintWriter printWriter, String currentIntentation, String indentationAmount) {
        printWriter.append("[");
        if (!indentationAmount.isEmpty()) printWriter.append("\n");
        for (Iterator<JsonNode> iterator = values.iterator(); iterator.hasNext();) {
            JsonNode node = iterator.next();
            printWriter.write(currentIntentation + indentationAmount);
            node.toJson(printWriter, currentIntentation + indentationAmount, indentationAmount);

            if (iterator.hasNext()) printWriter.append(",");
            if (!indentationAmount.isEmpty()) printWriter.append("\n");
        }
        printWriter.append(currentIntentation + "]");
    }

//Создает копию этого JsonArray со всеми скопированными значениями
    @Override
    public JsonNode deepClone() {
        return new JsonArray(mapNodes(JsonNode::deepClone));
    }

//Возвращает список членов этого JsonArray, сопоставленных с функцией.
    public <T> List<T> mapNodes(Function<JsonNode,T>mapFunc) {
        return nodeStream().map(mapFunc).collect(Collectors.toList());
    }

//Возвращает поток членов этого JsonArray.
    public Stream<JsonNode> nodeStream(){
        return values.stream();
    }

// Возвращает список children, которые являются массивами. Children, не являющиеся JsonNode, пропускаются
    public List<JsonArray> arrays() {
        return arrayStream().collect(Collectors.toList());
    }
    public Stream<JsonArray> arrayStream() {
        return nodeStream()
                .filter(n -> n instanceof JsonArray)
                .map(n -> (JsonArray)n);
    }

    @Override
    public Iterator<JsonNode> iterator() {
        return new ArrayList<>(values).iterator();
    }

    public boolean isEmpty(){
        return values.isEmpty();
    }

//Удаляет значение в указанной позиции. Возвращает значение, которое было удалено.
    public JsonNode remove(int i) {
        return values.remove(i);
    }

//Удаляет все значения в этом JsonArray
    public void clear() {
        values.clear();
    }

//Заменяет значение в указанной позиции.
    public void set(int i, Object o) {
        values.set(i, JsonFactory.jsonNode(o));
    }


}
