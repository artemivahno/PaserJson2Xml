package by.artsoimiv.parsing.pojo;

import by.artsoimiv.parsing.JsonFactory.JsonNode;

//Реализуйте этот интерфейс на объекте, который должен иметь JSON сериализацию. PojoMapper вызовет #jsonValue() во время
// сериализации
public interface OverridesJsonGenerator {
    JsonNode jsonValue();
}
