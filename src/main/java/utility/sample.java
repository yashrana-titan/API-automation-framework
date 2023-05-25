//package utility;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//
//public class sample {
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    public static boolean areEqualIgnoringProductField(String jsonString1, String jsonString2) {
//        JsonNode node1 = null;
//        try {
//            node1 = objectMapper.readTree(jsonString1);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        JsonNode node2 = null;
//        try {
//            node2 = objectMapper.readTree(jsonString2);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Extract the "date" and "details" fields from each JSON string
//        String date1 = node1.get("date").asText();
//        JsonNode details1 = node1.get("details");
//
//        String date2 = node2.get("date").asText();
//        JsonNode details2 = node2.get("details");
//
//        // Compare the "date" fields
//        if (!date1.equals(date2)) {
//            return false;
//        }
//
//        // Compare the "details" fields
//        if (!details1.equals(details2)) {
//            return false;
//        }
//
//        return true;
//    }
//
//    public static void main(String[] args) {
//        String jsonString1 = "{\n" +
//                "    \"date\": \"2023-05-01\",\n" +
//                "    \"product\": \"REFLEX_PLAY\",\n" +
//                "    \"details\": {\n" +
//                "        \"1683606600\": 90,\n" +
//                "        \"1683610200\": 99,\n" +
//                "        \"1683613800\": 96\n" +
//                "    }\n" +
//                "}";
//
//        String jsonString2 = "{\n" +
//                "    \"date\": \"2023-05-01\",\n" +
//                "    \"details\": {\n" +
//                "        \"1683606600\": 90,\n" +
//                "        \"1683600200\": 99,\n" +
//                "        \"1683613800\": 96\n" +
//                "    }\n" +
//                "}";
//
//        boolean areEqual = areEqualIgnoringProductField(jsonString1, jsonString2);
//        System.out.println("The two JSON strings are equal (ignoring 'product' field): " + areEqual);
//    }
//}
