package by.artsoimiv.parsing.pojo;

import by.artsoimiv.parsing.exception.CanNotMapException;
import by.artsoimiv.parsing.JsonFactory.JsonNode;

//Правило mapping, которое переопределяет PojoMapping любого количества классов. Реализация должна обеспечивать
//преобразование из json в экземпляр класса
public interface PojoMappingRule {

//Проверьте, должно ли это правило использоваться для сопоставления данного класса - clazz Класс, который будет
// отображаться; jsonNode Значение, которое будет отображаться; true, если следует использовать это правило, иначе false
    boolean isApplicableToClass(Class<?> clazz,JsonNode jsonNode);

//Предоставить mapping для данного класса: <T>;  jsonNode - Предоставленный json; clazz - Класс, который должен
// отображаться; mapitfunc Функция обратного вызова для отображения вложенных объектов; Реализация, отображающая
// предоставленный JSON. CanNotMapException Если отображение данного ввода не может быть выполнено
    <T> T mapClass(JsonNode jsonNode, Class<T> clazz, MapitFunction mapitfunc) throws CanNotMapException;

}
