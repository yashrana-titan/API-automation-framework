package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.*;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;

public class JSONUtility extends BaseClass{
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static HashMap<String, Object> getJsonDataInMap(String FilePath) throws IOException {
        String JsonString = FileUtils.readFileToString(new File(FilePath), StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> data = mapper.readValue(JsonString, new TypeReference<HashMap<String, Object>>() {
        });
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


    public static void saveResponseInFile(Response response, String filePath) {
        String responseBody = response.getBody().asString();

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(responseBody);
            System.out.println("Response saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> fetchDatesfromJSONList(List<JSONObject>list)
    {
        List<String> dates = new ArrayList<>();

        for (JSONObject jsonObject :list) {
            if (jsonObject.has("date")) {
                try {
                    String date = jsonObject.getString("date");
                    dates.add(date);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return dates;
    }


    public static List<String> fetchDatesFromJson(String filePath) {
        List<String> dates = new ArrayList<>();
        String jsonString;

        if (filePath.contains("csv")) {
            jsonString = JSONUtility.excelToJson(filePath);
        } else {
            try {
                jsonString = JSONUtility.getJSONString(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

            for (Object obj : jsonArray) {
                JSONObject entry = (JSONObject) obj;
                String date = (String) entry.get("date");
                dates.add(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dates;
    }


    public static boolean compareJsons(String json1, String json2) {
        JSONObject jsonObj1 = new JSONObject(json1);
        JSONObject jsonObj2 = new JSONObject(json2);

        for (String key : jsonObj1.keySet()) {
            if (jsonObj2.has(key)) {
                Object value1 = jsonObj1.get(key);
                Object value2 = jsonObj2.get(key);

                if (!value1.equals(value2)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }


    public static boolean compareJsonArrays(String putDataJson , String getDataJson , String productToCompare) {
        JsonArray putData = new Gson().fromJson(putDataJson, JsonArray.class);
        JsonArray getData = new Gson().fromJson(getDataJson, JsonArray.class);

        Set<LocalDate> putDates = extractDates(putData);
        System.out.println(putDataJson);
        System.out.println(getDataJson);
        JsonArray filteredGetData;
        if(getDataJson.contains("product"))
        {

            filteredGetData = filterDataByDatesAndProduct(getData, putDates, productToCompare);
        }
        else
        {
            System.out.println("no product exists");
            filteredGetData = filterDataByDates(getData, putDates);

        }
        System.out.println("filtered data "+filteredGetData);
        return compareJSONData(putData,filteredGetData );

    }

    private static Set<LocalDate> extractDates(JsonArray data) {
        Set<LocalDate> dates = new HashSet<>();
        for (JsonElement element : data) {
            JsonObject jsonObject = element.getAsJsonObject();
            if (jsonObject.has("date")) {
                LocalDate date = LocalDate.parse(jsonObject.get("date").getAsString());
                dates.add(date);
            }
        }
        return dates;
    }

    private static JsonArray filterDataByDatesAndProduct(JsonArray data, Set<LocalDate> dates, String productCode) {
        JsonArray filteredData = new JsonArray();
        for (JsonElement element : data) {
            JsonObject jsonObject = element.getAsJsonObject();
            if (jsonObject.has("date") && jsonObject.has("product")) {
                LocalDate date = LocalDate.parse(jsonObject.get("date").getAsString());
                String product = jsonObject.get("product").getAsString();
                if (dates.contains(date) && product.equals(productCode)) {
                    filteredData.add(jsonObject);
                }
            }
        }
        return filteredData;
    }

    private static JsonArray filterDataByDates(JsonArray data, Set<LocalDate> dates) {
        JsonArray filteredData = new JsonArray();
        for (JsonElement element : data) {
            JsonObject jsonObject = element.getAsJsonObject();
            if (jsonObject.has("date")) {
                LocalDate date = LocalDate.parse(jsonObject.get("date").getAsString());
                if (dates.contains(date)) {
                    filteredData.add(jsonObject);
                }
            }
        }
        return filteredData;
    }

    private static boolean compareJSONData(JsonArray putData, JsonArray getData) {
        // Map to store the compared data
        Map<LocalDate, Map<Integer, JsonElement>> comparedData = new HashMap<>();

        // Iterate over the putData
        for (JsonElement putElement : putData) {
            JsonObject putObject = putElement.getAsJsonObject();
            LocalDate date = LocalDate.parse(putObject.get("date").getAsString());

            // Get the corresponding getObject from getData
            JsonObject getObject = getObjectByDate(getData, date);

            if (getObject != null) {
                JsonObject putDetails = putObject.getAsJsonObject("details");
                System.out.println("\n\nComparator");
                System.out.println(putDetails);
                JsonObject getDetails = getObject.getAsJsonObject("details");
                System.out.println(getDetails);

                // Compare the slots and add to the comparedData map
                for (Map.Entry<String, JsonElement> entry : putDetails.entrySet()) {
                    String putSlot = entry.getKey();
                    if (getDetails.has(putSlot)) {
                        int slot = Integer.parseInt(putSlot);
                        JsonElement putSlotData = entry.getValue();
                        JsonElement getSlotData = getDetails.get(putSlot);
                        if (areEqual(putSlotData, getSlotData)) {
                            comparedData.computeIfAbsent(date, k -> new HashMap<>()).put(slot, putSlotData);
                        }
                    }
                }
            }
        }
        return !comparedData.isEmpty();
    }

    private static JsonObject getObjectByDate(JsonArray data, LocalDate date) {
        for (JsonElement element : data) {
            JsonObject jsonObject = element.getAsJsonObject();
            if (jsonObject.has("date") && jsonObject.get("date").getAsString().equals(date.toString())) {
                return jsonObject;
            }
        }
        return null;
    }

    private static boolean areEqual(JsonElement elem1, JsonElement elem2) {
        if (elem1.isJsonObject() && elem2.isJsonObject()) {
            return areEqual(elem1.getAsJsonObject(), elem2.getAsJsonObject());
        } else {
            String value1 = elem1.isJsonPrimitive() ? elem1.getAsString().replaceAll("\"", "") : elem1.toString();
            String value2 = elem2.isJsonPrimitive() ? elem2.getAsString().replaceAll("\"", "") : elem2.toString();
            return value1.equals(value2);
        }
    }

    private static boolean areEqual(JsonObject obj1, JsonObject obj2) {
        if (obj1.size() != obj2.size()) {
            return false;
        }

        for (Map.Entry<String, JsonElement> entry : obj1.entrySet()) {
            String key = entry.getKey();
            JsonElement value1 = entry.getValue();
            JsonElement value2 = obj2.get(key);

            if (!areEqual(value1, value2)) {
                return false;
            }
        }

        return true;
    }


    public static String excelToJson(String excelFilePath) {
        File excelFile = new File(excelFilePath);
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            fis = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            List<String> headers = new ArrayList<>();
            ArrayNode sheetData = objectMapper.createArrayNode();

            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);

                if (j == 0) {
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        if (cell != null) {
                            headers.add(cell.getStringCellValue());
                        } else {
                            headers.add("");
                        }
                    }
                } else {
                    ObjectNode rowData = objectMapper.createObjectNode();
                    for (int k = 0; k < headers.size(); k++) {
                        Cell cell = row.getCell(k);
                        String headerName = headers.get(k);

                        if (cell != null) {
                            if (headerName.contains("_")) {
                                String[] headerParts = headerName.split("_");
                                createNestedJson(rowData, headerParts, cell);
                            } else {
                                rowData.set(headerName, getCellValueAsJsonNode(cell));
                            }
                        } else {
                            rowData.put(headerName, "");
                        }
                    }
                    sheetData.add(rowData);
                }
            }

            return objectMapper.writeValueAsString(sheetData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static void createNestedJson(ObjectNode parentJson, String[] headerParts, Cell cell) {
        ObjectNode nestedJson = parentJson;
        for (int i = 0; i < headerParts.length - 1; i++) {
            String headerPart = headerParts[i];
            if (!nestedJson.has(headerPart)) {
                ObjectNode newJson = objectMapper.createObjectNode();
                nestedJson.set(headerPart, newJson);
                nestedJson = newJson;
            } else {
                nestedJson = (ObjectNode) nestedJson.get(headerPart);
            }
        }
        nestedJson.set(headerParts[headerParts.length - 1], getCellValueAsJsonNode(cell));
    }

    private static JsonNode getCellValueAsJsonNode(Cell cell) {
        DataFormatter dataFormatter = new DataFormatter();
        FormulaEvaluator formulaEvaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();

        CellValue cellValue = formulaEvaluator.evaluate(cell);
        if (cellValue.getCellType() == CellType.STRING) {
            String value = cellValue.getStringValue();
            if (value.contains(",")) {
                String[] values = value.split(",");
                ArrayNode arrayNode = objectMapper.createArrayNode();
                for (String val : values) {
                    arrayNode.add(val.trim());
                }
                return arrayNode;
            } else {
                return objectMapper.valueToTree(value);
            }
        } else {
            return objectMapper.valueToTree(dataFormatter.formatCellValue(cell));
        }
    }

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


    public String extractValueFromResponse(Response response, String keyToFind) {
        // Get the response body as a JSON string
        String responseBody = response.getBody().asString();
        String value;
            ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonNode valueNode = jsonNode.get(keyToFind);

            if (valueNode != null) {
                value = valueNode.asText();
                return value;
            } else {
                System.out.println("Key '" + keyToFind + "' not found in the response.");
                return null;
            }
    }
}
