package by.artsoimiv.parsing.pojo;

import by.artsoimiv.parsing.JsonFactory.JsonNode;
import by.artsoimiv.parsing.exception.CanNotMapException;

//Правило отображения, которое можно использовать для предоставления отображения из json в интерфейс.
// Bytebuddy (bytebuddy.net) используется для создания экземпляра интерфейса во время выполнения.
// Важно: Использование этого класса требует от вас добавления дополнительной byte-buddy зависимости к вашему пути к классам.
public class DynamicInterfaceMapper implements PojoMappingRule {
    private final boolean mapAllGetters;

    private DynamicInterfaceMapper(boolean mapAllGetters) {
        this.mapAllGetters = mapAllGetters;
    }

    public static DynamicInterfaceMapper mapperThatMapsAllGetters(){
        return new DynamicInterfaceMapper(true);
    }

    public DynamicInterfaceMapper() {
        this(false);
    }

    @Override
    public boolean isApplicableToClass(Class<?> clazz, JsonNode jsonNode) {
        return false;
    }

    @Override
    public <T> T mapClass(JsonNode jsonNode, Class<T> clazz, MapitFunction mapitfunc) throws CanNotMapException {
        return null;
    }
}
