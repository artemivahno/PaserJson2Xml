package by.artsoimiv.parsing.pojo;

import by.artsoimiv.parsing.JsonFactory.JsonNode;

//Подключаемый десериализатор JSON для класса. Вы можете переопределить это для управления десериализацией JSON.
// Реализовывать целевой класс OverrideMapper.
public interface JsonPojoBuilder<T> {

    //Реализация для создания объекта из JsonNode
    T build(JsonNode jsonObject);
}
