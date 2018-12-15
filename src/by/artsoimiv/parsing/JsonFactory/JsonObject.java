package by.artsoimiv.parsing.JsonFactory;

import by.artsoimiv.parsing.Exception.JsonConversionException;
import by.artsoimiv.parsing.Exception.JsonValueNotPresentException;

import java.io.PrintWriter;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//JsonObject - класс представляет словарь значений по строке ключи.  Каждое значение может быть JsonArray, числом, строкой,
// логическое значение или другим объектом. Например: <code>{"foo":"string", "number":123, "really":false]}</code>,
// <code>requiredString("foo")</code> который возвращает 'string' и  <code>requiredLong("number")</code> вернет 123.


public class JsonObject extends JsonNode{

    private final Map<String, JsonNode> values;

//Производит пустой JsonObject
    public JsonObject() {
        this.values = new LinkedHashMap<>();
    }

    public JsonObject(Map<String, JsonNode> values) {
        this.values = values;
    }

//Возвращает значение ключа аргумента в виде строки или пустого значения Optional - если ключ отсутствует. Кидает
// JsonConversionException если значение JsonArray или JsonObject.

    public Optional<String> stringValue(String key) throws JsonConversionException {
        return get(key, JsonValue.class).map(JsonValue::stringValue);
    }

//Возвращает значение ключа в виде String. кидает JsonValueNotPresentException если ключ не получаем или не верный тип
    public String requiredString(String key) throws JsonValueNotPresentException {
        return stringValue(key).orElseThrow(throwKeyNotPresent(key));
    }

    private Supplier<JsonValueNotPresentException> throwKeyNotPresent(String key) {
        return () -> new JsonValueNotPresentException(String.format("Требуемый ключ '%s' не существует",key));
    }

//Возвращает значение аргумента как double или пустой. JsonConversionException если значение не конвертируется в число.
//    public Optional<Double> doubleValue(String key) throws JsonConversionException {
//        return numberValue(key).map(Number::doubleValue);
//    }

// //Возвращает значение аргумента как double
//    public double requiredDouble(String key) throws JsonValueNotPresentException {
//        return doubleValue(key).orElseThrow(throwKeyNotPresent(key));
    //    }

//Возвращает значение аргумента как long или пустое
//JsonConversionException если значение не конвертируется в число
//    public Optional<Long> longValue(String key) throws JsonConversionException{
//        return numberValue(key).map(Number::longValue);
//    }
//
//    //Возвращает значение аргумента как long
//    public double requiredLong(String key) throws JsonValueNotPresentException {
//        return longValue(key).orElseThrow(throwKeyNotPresent(key));
//    }

//Возвращает значение ключа в виде числа или пустого элемента Optional если ключа нет. JsonConversionException если
// значение не конвертируется в число
    public Optional<Object> numberValue(String key) throws JsonConversionException {
        JsonNode node = values.get(key);
        if (node == null || node instanceof JsonNull){
            return Optional.empty();
        }
        if (node instanceof JsonNumber) {
            return Optional.of(((JsonNumber)node).javaObjectValue());
        }
        if (node instanceof JsonValue){
            String stringValue = node.stringValue();
            if (stringValue.isEmpty()){
                return Optional.empty();
            }
            try {
                return Optional.of(Double.parseDouble(stringValue()));
            } catch (NumberFormatException e){
                throw new JsonConversionException(key + " это не число");
            }
        } else {
            throw new JsonConversionException(key + " это не число");
        }
    }
//Возвращает значение аргумента key как boolean. JsonValueNotPresentException - если key не представлен,
// JsonConversionException если key не boolean
    public boolean reqiredBoolean(String key) throws JsonConversionException, JsonValueNotPresentException {
        return booleanValue(key).orElseThrow(throwKeyNotPresent(key));
    }

    public Optional<Boolean> booleanValue(String key) throws JsonConversionException {
        JsonNode node = values.get(key);
        if (node == null || node instanceof JsonNull){
            return Optional.empty();
        }
        if (node instanceof JsonBoolean) {
            return Optional.of(((JsonBoolean)node).booleanValue());
        }
        if (node instanceof JsonValue) {
            return Optional.of(Boolean.parseBoolean(node.stringValue()));
        }else {
            throw new JsonConversionException(key + " этот ключ не boolean");
        }
    }

//Возвращает значение аргумента key как boolean. JsonValueNotPresentException если ключ не прдставлен,
// JsonConversionException key не конвертируется в boolean.
    public boolean requiredBoolean (String key)throws JsonConversionException, JsonValueNotPresentException{
        return booleanValue(key).orElseThrow(throwKeyNotPresent(key));
    }

//Возвращает значение ключа аргумента в виде JsonObject или пустого
//Optional, если ключа нет. JsonConversionException если значение не JsonObject.
    public Optional<JsonObject> objectValue(String key)throws JsonConversionException{
        return get(key, JsonObject.class);
    }

//Возвращает в качестве ключа JsonObject. JsonValueNotPresentException если ключ не JsonObject или ключа нет.
    public JsonObject requiredObject(String key) throws JsonValueNotPresentException {
        return objectValue(key).orElseThrow(throwKeyNotPresent(key));
    }

    //возвращает значение ключа аргумента в виде {Instant} или пустого Optional, если ключ отсутствует.
    public Optional<Instant> instantValue(String key) {
        return stringValue(key).map(s -> Instant.parse(s));
    }
//возвращает значение ключа аргумента в виде {Instant}
    public Instant requiredInstant (String key){
        return instantValue(key).orElseThrow(throwKeyNotPresent(key));
    }

    //возвращает значение ключа аргумента в виде Enum или пустого Optional, если ключ отсутствует.
    private <T extends Enum<T>> Optional<T> enumValue(String key, Class<T> enumType){
        return stringValue(key).map(s->Enum.valueOf(enumType,s));
    }

    //возвращает значение ключа аргумента в виде Enum
    public <T extends Enum<T>> T requifedEnum(String key, Class<T> enumType){
        return enumValue(key, enumType).orElseThrow(throwKeyNotPresent(key));
    }



    //Возвращает значение ключа аргумента как есть или пустое значение Optional если ключ отсутствует.
// JsonConversionException - если значение неопределенного типа
    public Optional<JsonNode> value(String key) {
        return Optional.ofNullable(values.get(key));
    }

//Возвращает значение ключа аргумента в качестве типа аргумента или пустого Optional если ключ отсутствует.
// кидает JsonConversionException - если значение неопределенного типа
    private <T extends JsonNode> Optional<T> get(String key, Class<T> t) throws JsonConversionException{
        Optional<JsonNode> value = value(key);
        if (value.isPresent() && !t.isAssignableFrom(value.get().getClass())) {
            throw new JsonConversionException("Не могу сконвертировать "+ key + " в "+t);
        }
        return value.map(node -> (T) node);
    }

//Возвращает значение ключа аргумента в виде JsonArray или пустого
    public Optional<JsonArray> arrayVaiue(String key) throws JsonConversionException {
        return get(key, JsonArray.class);
    }

//Возвращает значение ключа аргумента в виде JsonArray.
    public JsonArray requiredArray(String key){
        return arrayVaiue(key).orElseThrow(throwKeyNotPresent(key));
    }

//Записывает текстовое представление JSON этого JsonArray
    @Override
    public void toJson(PrintWriter printWriter, String currentIntentation, String indentatinAmount) {
        printWriter.append("{");
        if (!indentatinAmount.isEmpty()) printWriter.append("\n");
        for (Iterator<Map.Entry<String,JsonNode>> iterator = values.entrySet().iterator(); iterator.hasNext();){
            Map.Entry<String,JsonNode> entry = iterator.next();
            printWriter.append(currentIntentation + indentatinAmount);
            printWriter.append('"');
            printWriter.append(entry.getKey());
            printWriter.append("\":");
            entry.getValue().toJson(printWriter, currentIntentation + indentatinAmount, indentatinAmount);

            if (iterator.hasNext()) {
                printWriter.append(",");
            }

            if (!indentatinAmount.isEmpty()) printWriter.append("\n");
        }
        printWriter.append(currentIntentation + "}");
    }
//Связывает указанное значение с указанным ключом. Если map ранее содержала значение для ключа, старое значение заменяется
    public JsonObject put(String key, Object value) {
        values.put(key, JsonFactory.jsonNode(value));
        return this;
    }

//Возвращает все ключи этого JsonObject.
    public Set<String> keys(){
        return values.keySet();
    }

//Удаляет и возвращает значение, указанное ключом аргумента.
    public Optional<JsonNode> remove(String key){
        if(key == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(values.remove(key));
    }

//Создает копию этого JsonObject со всеми скопированными значениями
    @Override
    public JsonObject deepClone() {
        Map<String, JsonNode> cloned = values.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().deepClone()));
        Map<String, JsonNode> newValues = new HashMap<>();
        newValues.putAll(cloned);
        return new JsonObject(newValues);
    }

//Возвращает true, если аргумент является JsonObject с тем же значением что и этот объект
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonObject)) return false;
        JsonObject that = (JsonObject) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

//Возвращает количество элементов в этом JsonObject
    public int size() {
        return values.size();
    }

// Возвращает true, если этот JsonObject не содержит значений.
    public boolean isEmpty() {
        return values.isEmpty();
    }

// Удаляет все значения в этом JsonObject.
    public void clear() {
        values.clear();
    }
// Возвращает true, если этот JsonObject имеет значение для указанного ключа.
    public boolean containsKey(String key) {
        return values.containsKey(key);
    }

//Поместите все объекты из исходного JsonObject в этот
    public JsonObject putAll(JsonObject source) {
        values.putAll(source.values);
        return this;
    }
}
