package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


public class JSONUtility {

    public static HashMap<String,Object> getJsonDataInMap(String FilePath) throws IOException {
        //Convert json file to json string
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
        // Extract the JSON content from the response
        String responseJson = response.getBody().asString();
        String expectedJson = new String(Files.readAllBytes(Paths.get(filePath)));

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode responseNode = objectMapper.readTree(responseJson);
        JsonNode expectedNode = objectMapper.readTree(expectedJson);

        // Compare the JSON nodes using the equals() method
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

        // Save the response to a JSON file
        try (FileWriter fileWriter = new FileWriter("dataSpo2.json")) {
            fileWriter.write(responseBody);
            System.out.println("Response saved to response.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
