package by.artsoimiv.simple;

public class XmlFunctions {
    // переменная для записи xml-строки
    private StringBuilder xml;

    public XmlFunctions() {
        xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    }

    public String getXml() {
        return xml.toString();
    }

    // функция добавления открывающегося тега
    public void putStart(String key) {
        xml.append("<").append(key).append(">\n");
    }

    // функция добавления закрывающегося тега
    public void putEnd(String key) {
        xml.append("</").append(key).append(">\n");
    }

    // функция добавления значенияя между тегами
    public void putValue(String value) {
        xml.append(value).append("\n");
    }
}
