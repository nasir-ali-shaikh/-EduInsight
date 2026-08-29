package eduinsight;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Minimal, dependency-free JSON parser. Only what EduInsight
 * needs: objects, arrays, strings, numbers, booleans and null.
 * No external library required, so the project builds and runs
 * with nothing but the JDK.
 */
public class JsonUtil {

    private final String json;
    private int pos = 0;

    private JsonUtil(String json) {
        this.json = json;
    }

    public static Object parse(String json) {
        JsonUtil parser = new JsonUtil(json);
        parser.skipWhitespace();
        return parser.parseValue();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseObjectRoot(String json) {
        Object value = parse(json);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        throw new IllegalArgumentException("Expected a JSON object at the root.");
    }

    private void skipWhitespace() {
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
            pos++;
        }
    }

    private char peek() {
        return json.charAt(pos);
    }

    private Object parseValue() {
        skipWhitespace();
        char c = peek();
        switch (c) {
            case '{':
                return parseObject();
            case '[':
                return parseArray();
            case '"':
                return parseString();
            case 't':
            case 'f':
                return parseBoolean();
            case 'n':
                return parseNull();
            default:
                return parseNumber();
        }
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> map = new LinkedHashMap<>();
        pos++; // consume {
        skipWhitespace();

        if (peek() == '}') {
            pos++;
            return map;
        }

        while (true) {
            skipWhitespace();
            String key = parseString();
            skipWhitespace();
            pos++; // consume :
            Object value = parseValue();
            map.put(key, value);
            skipWhitespace();

            char next = peek();
            pos++;

            if (next == '}') {
                break;
            }
            // otherwise next == ',' -> continue loop
        }

        return map;
    }

    private List<Object> parseArray() {
        List<Object> list = new ArrayList<>();
        pos++; // consume [
        skipWhitespace();

        if (peek() == ']') {
            pos++;
            return list;
        }

        while (true) {
            Object value = parseValue();
            list.add(value);
            skipWhitespace();

            char next = peek();
            pos++;

            if (next == ']') {
                break;
            }
            // otherwise next == ',' -> continue loop
        }

        return list;
    }

    private String parseString() {
        StringBuilder sb = new StringBuilder();
        pos++; // consume opening quote

        while (peek() != '"') {
            char c = json.charAt(pos);

            if (c == '\\') {
                pos++;
                char esc = json.charAt(pos);

                switch (esc) {
                    case 'n':
                        sb.append('\n');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case '"':
                        sb.append('"');
                        break;
                    case '\\':
                        sb.append('\\');
                        break;
                    case '/':
                        sb.append('/');
                        break;
                    case 'u':
                        String hex = json.substring(pos + 1, pos + 5);
                        sb.append((char) Integer.parseInt(hex, 16));
                        pos += 4;
                        break;
                    default:
                        sb.append(esc);
                }
            } else {
                sb.append(c);
            }

            pos++;
        }

        pos++; // consume closing quote
        return sb.toString();
    }

    private Boolean parseBoolean() {
        if (json.startsWith("true", pos)) {
            pos += 4;
            return Boolean.TRUE;
        } else {
            pos += 5;
            return Boolean.FALSE;
        }
    }

    private Object parseNull() {
        pos += 4;
        return null;
    }

    private Object parseNumber() {
        int start = pos;

        while (pos < json.length()
                && (Character.isDigit(peek())
                || peek() == '-'
                || peek() == '+'
                || peek() == '.'
                || peek() == 'e'
                || peek() == 'E')) {
            pos++;
        }

        String number = json.substring(start, pos);

        if (number.contains(".") || number.contains("e") || number.contains("E")) {
            return Double.parseDouble(number);
        }

        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return Double.parseDouble(number);
        }
    }
}