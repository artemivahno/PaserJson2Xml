package by.artsoimiv.simple;

public class JsonFunctions {

    private XmlFunctions xmlFunctions;

    public JsonFunctions(XmlFunctions xmlFunctions) {
        this.xmlFunctions = xmlFunctions;
    }

    // начало парсинга json
    public void parseStart(String json) {
        System.out.println("START: --->> " + json);
        if (json == null || json.length() <= 0) {
        } else {
            if (json.startsWith("{")) {
                removeBorders(json, null);
            } else if (json.startsWith(":")) {
                parseValue(json, null);
            } else if (json.startsWith(",")) {
                json = removeFirstChar(json);
                parseKey(json, null);
            } else {
                parseKey(json, null);
            }
        }
    }

    // функция парсинга тег, с учетом предыдущего тега - чтобы корректно закрыть тег
    private void parseKey(String json, String prevKey) {
        System.out.println("\nparseKey: --->> " + json);
        String key = json.substring(0, json.indexOf(':'));
        System.out.println("key: " + key);
        json = removeKey(json);
        //запомнть key,

        xmlFunctions.putStart(key);
        parseValue(json, key);
        if (prevKey != null) {
            xmlFunctions.putEnd(prevKey);
        }
    }

    // функция удаления скобок в json-строке (фигурных и квадратных)
    private void removeBorders(String json, String key) {
        System.out.println("\nremoveBorders: --->> " + json);
        json = removeFirstChar(json);
        json = removeLastChar(json);
        parseKey(json, key);
    }

    // функция парсинга значения между тегами с учетом тега - для корректного закрытия тега
    private void parseValue(String json, String key) {
        System.out.println("\nparseValue: --->> " + json);
        json = removeFirstChar(json);
        if (json.startsWith("{")) {
            removeBorders(json, key);
        } else if (json.startsWith("[")) {
            json = json.substring(json.indexOf('[') + 1, json.indexOf(']'));
            if (key != null) {
                parseArray(json, key);
            } else {
                parseArray(json, null);
            }
        } else {
            String value;
            if (json.contains(",")) {
                value = json.substring(0, json.indexOf(','));
            } else {
                value = json.substring(0, json.length());
            }
            System.out.println("value: " + value);
            if (json.contains(",")) {
                json = removeValue(json);
            } else {
                json = removeLastValue(json);
            }
            xmlFunctions.putValue(value);
            if (key != null) {
                xmlFunctions.putEnd(key);
            }
            parseStart(json);
        }
    }

    // функция парсинга массива значений
    private void parseArray(String json, String key) {
        System.out.println("\nparseArray: --->> " + json);
        int membersCount = countChar('{', json);
        String[] members = new String[membersCount];
        for (int i = 0; i < membersCount; i++) {
            if ((i + 1) == membersCount) {
                members[i] = json.substring(1, json.length() - 1);
            } else {
                members[i] = json.substring(1, json.indexOf('}'));
                json = json.substring(json.indexOf('}') + 2);
            }
        }
        for (int i = 0; i < members.length; i++) {
            System.out.println("MEMBER: --->> " + members[i]);
            parseKey(members[i], null);
            if (key != null) {
                xmlFunctions.putEnd(key);
                if (i + 1 != membersCount) {
                    xmlFunctions.putStart(key);
                }
            }
        }
    }

    //  проверка корректности json
    private String regex = "\\{\\}"; //что не так в этой регулярке?
    public boolean checkJson(String json) {
        if (json.startsWith("{") && json.endsWith("}")) {
            int countOpen = countChar('{', json);
            int countClose = countChar('}', json);
            int quote = countChar('"', json);
            int semicolon = countChar(';', json);

            //условия валидации
            if (countOpen == countClose && quote %2==0 && semicolon==0 && !matches(regex,json)) {
                return true;
            }
            return false;
        }

        return false;
    }

    static boolean matches(String regex, CharSequence json) {
        return false;
    }
/*
    private boolean matches (String json, String regex){
        boolean b=false;
        String regexp = "[^{}$]";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()){
            b=true;
     return b;
    }*/

    // функция подсчета количества определенных символов в строке
    private int countChar(char c, String json) {
        int n = 0;
        char[] array = json.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                n++;
            }
        }
        return n;
    }



    // функция удаления последнего элемента строки
    public String removeLastChar(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(0, json.length() - 1);
        }
        return json;
    }

    // функция удаления первого элемента строки
    public String removeFirstChar(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(1);
        }
        return json;
    }

    // функция удаления тега
    public String removeKey(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(json.indexOf(':'));
        }
        return json;
    }

    // функция удаления значения между тегами
    public String removeValue(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(json.indexOf(','));
        }
        return json;
    }

    // функция удаления последнего значения - при завершении парсинга
    public String removeLastValue(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(json.length());
        }
        return json;
    }

}
