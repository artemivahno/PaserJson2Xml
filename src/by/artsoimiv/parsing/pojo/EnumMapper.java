package by.artsoimiv.parsing.pojo;

import by.artsoimiv.parsing.JsonFactory.JsonNode;
import by.artsoimiv.parsing.JsonFactory.JsonString;
import by.artsoimiv.parsing.exception.CanNotMapException;

//Используется для перечислений Pojo Map. Enum.valueOf используется для отображения String в экземплярe Enum
public class EnumMapper implements PojoMappingRule{

    @Override
    public boolean isApplicableToClass(Class<?> clazz, JsonNode jsonNode) {
        return clazz.isEnum() && (jsonNode instanceof JsonString);
    }

    @Override
    public <T> T mapClass(JsonNode jsonNode, Class<T> clazz, MapitFunction mapitfunc) throws CanNotMapException {
        String value = jsonNode.stringValue();
        Enum anEnum = Enum.valueOf((Class<Enum>) clazz,value);
        return (T) anEnum;
    }
}
