package by.artsoimiv.parsing;

import by.artsoimiv.JsonParseException;
import by.artsoimiv.parsing.JsonFactory.JsonNode;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JsonParser {
//Разбор читателя как JsonNode. Вернет JsonArray, JsonValue.
    public static JsonNode parse(Reader reader) throws JsonParseException {
    JsonParser jsonParser = new JsonParser(reader);
    return jsonParser.parseValue();
    }

//Parse String как JsonNode. Вернет JsonArray или JsonValue.
    public static JsonNode parse(String input) throws JsonParseException  {
        return parse(new StringReader(input));
    }

    private Reader reader;
    private char lastRead;
    private boolean finished;

    private JsonParser(Reader reader) {
        this.reader = reader;
        readNext();
    }

    private void readNext()  {
        int read;
        try {
            read = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (read == -1) {
            finished = true;
            return;
        }
        this.lastRead = (char) read;
    }

    private JsonNode parseValue() {
        while (!finished) {
//            switch (lastRead) {
//                case '{':
//                    return parseObject();
//                case '[':
//                    return parseArray();
//                case '"':
//                    return parseStringValue();
//                case 't':
//                case 'f':
//                    return parseBooleanValue();
//                case 'n':
//                    return parseNullValue();
//            }
//            if (lastRead == '-' || Character.isDigit(lastRead)) {
//                return parseNumberValue();
//            }
            if (!(Character.isWhitespace(lastRead))) {
                throw new JsonParseException("Unexpected character '" + lastRead + "'");
            }
            readNext();
        }
        return null;
    }

}
