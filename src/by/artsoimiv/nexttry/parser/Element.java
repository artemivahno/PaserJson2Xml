package by.artsoimiv.nexttry.parser;

import java.util.HashMap;
import java.util.Map;

public class Element {
    private Map<Key, Value> key_value_pairs = new HashMap<>();

    public Element() {
    }

    public Element(Key key, Value value) {
        this.addKeyValuePair(key, value);
    }

    public Map<Key, Value> getKeyValuePairs() {
        return key_value_pairs;
    }

    public void addKeyValuePair(Key key, Value value) {
        key_value_pairs.put(key, value);
    }

    public void addElementContents(Element element) {
        for (Map.Entry<Key, Value> map : element.key_value_pairs.entrySet())
            this.addKeyValuePair(map.getKey(), map.getValue());
    }

    public int size() {
        return key_value_pairs.size();
    }

    public String toString() {
        if (key_value_pairs.size() > 0) {
            StringBuilder sb = new StringBuilder().append("{\n");

            for (Map.Entry<Key, Value> map : this.key_value_pairs.entrySet()) {
                Key key = map.getKey();
                Value value = map.getValue();

                if (value.getValue() != null)
                    sb.append("  " + key.getKey() + " : " + value.toString());
                else
                    sb.append("  " + key.getKey() + " : " + value.getElement().toString());
            }

            return sb.append("\n}").toString();
        }

        return "(nothing)";
    }
}
