package by.artsoimiv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


public class JsonParser {
    // переменная для записи xml-строки
    private String xml;

    // функция добавления открывающегося тега
    private void putStart(String key) {
        xml += "<" + key + ">";
    }

    // функция добавления закрывающегося тега
    private void putEnd(String key) {
        xml += "</" + key + ">";
    }

    // функция добавления значенияя между тегами
    private void putValue(String value) {
        xml += value;
    }

    public static void main(String[] args) {
        new JsonParser().run();
    }

    private void run() {
        try {
            // чтение json из файла и запись содержимого файла в переменную
            Scanner scanner = new Scanner(new File("sample.json"));
            String json = "";
            while (scanner.hasNext()) {
                json += scanner.next();
            }

            //  проверка корректности json
            if (checkJson(json)) {
                // удаление пробелов, ковычек, новых строк
                json = json.replaceAll("\"", "");
                json = json.replaceAll("\n", "");
                json = json.replaceAll(" ", "");

                // удаление первого и последнего символа из json
                json = removeFirstChar(json);
                json = removeLastChar(json);

                // добавление шапки xml
                xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

                // начало парсинга json
                parseStart(json);
                System.out.println(xml);

                // запись сформированной xml-строки в файл
                PrintStream out = new PrintStream(new FileOutputStream("result.xml"));
                out.print(xml);
            } else {
                System.out.println("Error! Please check your JSON!");
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    // функция проверки корректности json
    // пояснение: у json в начале и в конце должны быть фигурные скобки
    // плюс количество открывающихся фигурных скобок должно равняться количеству закрывающихся
    private boolean checkJson(String json) {
        if (json.startsWith("{") && json.endsWith("}")) {
            int countOpen = countChar('{', json);
            int countClose = countChar('}', json);
            if (countOpen == countClose) {
                return true;
            }
            return false;
        }
        return false;
    }

    // начальная функция парсинга.
    private void parseStart(String json) {
        System.out.println("START: --->> " + json);
        if (json == null || json.length() <= 0) {
        } else {
            // пояснение: если json начинается с фигурной скобки - это либо начало, либо массив внутри элемента
            // если начинается с : значит идет значение между тегами
            // если начинается с , значит идет название тега (если в начале то тоже идет тег)
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
        System.out.println("\n parseKey: --->> " + json);
        // парсинг тега
        String key = json.substring(0, json.indexOf(':'));
        System.out.println("key: " + key);
        // удаление тега из текущей строки json
        json = removeKey(json);
        // запись начала тега
        putStart(key);
        // после тега идет значение - поэтому парсинг значения
        parseValue(json, key);
        // если предыдущий тег не пустой, то закрываем тег
        if (prevKey != null) {
            putEnd(prevKey);
        }
    }

    // функция удаления скобок в json-строке (фигурных и квадратных)
    private void removeBorders(String json, String key) {
        System.out.println("\nremoveBorders: --->> " + json);
        json = removeFirstChar(json);
        json = removeLastChar(json);
        // парсиниг тега
        parseKey(json, key);
    }

    // функция парсинга значения между тегами с учетом тега - для корректного закрытия тега
    private void parseValue(String json, String key) {
        System.out.println("\nparseValue: --->> " + json);
        json = removeFirstChar(json);
        // пояснение: если значения между тегами начинается со скобок - значит там массив значений
        // иначе просто парсинг значения
        if (json.startsWith("{")) {
            removeBorders(json, key);
        } else if (json.startsWith("[")) {
            json = json.substring(json.indexOf('[') + 1, json.indexOf(']'));
            // парсинг массива значений с учетом тега
            if (key != null) {
                parseArray(json, key);
            } else {
                parseArray(json, null);
            }
        } else {
            String value;
            // проверка на конец json-строки
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
            // запись значения между тегами
            putValue(value);
            if (key != null) {
                putEnd(key);
            }
            // рекурсия первоначальной функции
            parseStart(json);
        }
    }

    // функция парсинга массива значений
    private void parseArray(String json, String key) {
        System.out.println("\nparseArray: --->> " + json);
        // подсчет количества значений внутри массива
        int membersCount = countChar('{', json);
        String[] members = new String[membersCount];
        for (int i = 0; i < membersCount; i++) {
            // запись каждого значения внутри массива для дальнейшего парсинга
            if ((i + 1) == membersCount) {
                members[i] = json.substring(1, json.length() - 1);
            } else {
                members[i] = json.substring(1, json.indexOf('}'));
                json = json.substring(json.indexOf('}') + 2);
            }
        }
        for (int i = 0; i < members.length; i++) {
            // парсинг каждого элемента массива значений
            System.out.println("MEMBER: --->> " + members[i]);
            parseKey(members[i], null);
            if (key != null) {
                putEnd(key);
                if (i + 1 != membersCount) {
                    putStart(key);
                }
            }
        }
    }

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
    private String removeLastChar(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(0, json.length() - 1);
        }
        return json;
    }

    // функция удаления первого элемента строки
    private String removeFirstChar(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(1);
        }
        return json;
    }

    // функция удаления тега
    private String removeKey(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(json.indexOf(':'));
        }
        return json;
    }

    // функция удаления значения между тегами
    private String removeValue(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(json.indexOf(','));
        }
        return json;
    }

    // функция удаления последнего значения - при завершении парсинга
    private String removeLastValue(String json) {
        if (json != null && json.length() > 0) {
            json = json.substring(json.length());
        }
        return json;
    }


}
