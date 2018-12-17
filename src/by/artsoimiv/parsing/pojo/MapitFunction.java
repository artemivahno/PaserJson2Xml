package by.artsoimiv.parsing.pojo;

import by.artsoimiv.parsing.JsonFactory.JsonNode;

public interface MapitFunction {
    Object mapot(JsonNode jsonNode, Class<?> clazz) throws ClassCastException;
}
