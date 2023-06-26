package utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;

public class JsonComparator {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean compareJsonStrings(String jsonString1, String jsonString2) {
        try {
            JsonNode jsonNode1 = objectMapper.readTree(jsonString1);
            JsonNode jsonNode2 = objectMapper.readTree(jsonString2);

            return compareJsonNodes(jsonNode1, jsonNode2);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON strings", e);
        }
    }

    private static boolean compareJsonNodes(JsonNode node1, JsonNode node2) {
        if (node1.equals(node2)) {
            return true;
        }

        if (node1.isObject() && node2.isObject()) {
            if (node1.size() != node2.size()) {
                return false;
            }

            for (Iterator<String> fieldNames = node1.fieldNames(); fieldNames.hasNext();) {
                String fieldName = fieldNames.next();
                if (!node2.has(fieldName)) {
                    return false;
                }

                JsonNode fieldValue1 = node1.get(fieldName);
                JsonNode fieldValue2 = node2.get(fieldName);

                if (!compareJsonNodes(fieldValue1, fieldValue2)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        String jsonString1 = null;
        try {
            jsonString1 = JSONUtility.getJSONString("./src/main/java/putdata/sample2.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String jsonString2 = null;
        try {
            jsonString2 = JSONUtility.getJSONString("./src/main/java/putdata/samplespo2.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean result = compareJsonStrings(jsonString1, jsonString2);
        System.out.println("Comparison result: " + result);
    }
}
