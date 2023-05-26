package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JSONUtility {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static HashMap<String,Object> getJsonDataInMap(String FilePath) throws IOException {
        String JsonString = FileUtils.readFileToString(new File(FilePath), StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,Object> data = mapper.readValue(JsonString, new TypeReference<HashMap<String, Object>>() {});
        return data;
    }

    public static String getJSONString(String FilePath) throws IOException {
        String JsonString = FileUtils.readFileToString(new File(FilePath), StandardCharsets.UTF_8);
        return JsonString;
    }

    public static boolean compareResJsonWithFile(Response response, String filePath) throws IOException {
        String responseJson = response.getBody().asString();
        String expectedJson = new String(Files.readAllBytes(Paths.get(filePath)));

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode responseNode = objectMapper.readTree(responseJson);
        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        return responseNode.equals(expectedNode);
    }
    public static boolean compareJsonFromResponse(Response response1, Response response2) throws IOException {
        String responseJson1 = response1.getBody().asString();
        String responseJson2 = response2.getBody().asString();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode responseNode1 = objectMapper.readTree(responseJson1);
        JsonNode responseNode2 = objectMapper.readTree(responseJson2);

        return responseNode1.equals(responseNode2);
    }
    public static void saveResponseInFile(Response response) {
        String responseBody = response.getBody().asString();

        try (FileWriter fileWriter = new FileWriter("dataSpo2.json")) {
            fileWriter.write(responseBody);
            System.out.println("Response saved to response.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> fetchDatesFromJson(String filePath) {
        List<String> dates = new ArrayList<>();

        try {
            FileReader reader = new FileReader(filePath);

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject entry = (JSONObject) obj;
                String date = (String) entry.get("date");
                dates.add(date);
            }
            reader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return dates;
    }

    public static boolean areEqualIgnoringProductField(String jsonString1, String jsonString2) {
        JsonNode arrayNode1 = null;
        try {
            arrayNode1 = objectMapper.readTree(jsonString1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonNode arrayNode2 = null;
        try {
            arrayNode2 = objectMapper.readTree(jsonString2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (arrayNode1.size() != arrayNode2.size()) {
            return false;
        }

        for (int i = 0; i < arrayNode1.size(); i++) {
            JsonNode node1 = arrayNode1.get(i);
            JsonNode node2 = arrayNode2.get(i);

            // Remove the "product" field from the nodes before comparison
            ((ObjectNode) node1).remove("product");
            ((ObjectNode) node2).remove("product");
            System.out.println("node1  "+ node1);
            System.out.println("node2  "+node2);
            if (!node1.equals(node2)) {
                return false;
            }
        }

        return true;
    }


}
