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
import java.time.LocalDate;
import java.util.*;

public class JSONUtility {
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

    public static boolean compareJsonFromResponse(Response response1, Response response2) throws IOException {
        String responseJson1 = response1.getBody().asString();
        String responseJson2 = response2.getBody().asString();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode responseNode1 = objectMapper.readTree(responseJson1);
        JsonNode responseNode2 = objectMapper.readTree(responseJson2);

        return responseNode1.equals(responseNode2);
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


    public static boolean compareJsonArrays(String putDataJson,String getDataJson,String productCode)
    {
        JsonArray putData = new Gson().fromJson(putDataJson, JsonArray.class);
        JsonArray getData = new Gson().fromJson(getDataJson, JsonArray.class);

        System.out.println("put data from function "+putData);
        System.out.println("get data from function "+getData);
        // Extract dates from putData
        Set<LocalDate> putDates = extractDates(putData);
        //System.out.println(putDataJson);
        // Filter getData based on the dates and product code
        JsonArray filteredGetData = filterDataByDatesAndProduct(getData, putDates, productCode);
        System.out.println(filteredGetData);
        // Compare the filtered data
        return compareJSONData(putData, filteredGetData);
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

    private static boolean compareJSONData(JsonArray putData, JsonArray getData) {
        // Map to store the compared data
        Map<LocalDate, Map<String, JsonObject>> comparedData = new HashMap<>();

        // Iterate over the putData
        for (JsonElement putElement : putData) {
            JsonObject putObject = putElement.getAsJsonObject();
            LocalDate date = LocalDate.parse(putObject.get("date").getAsString());

            // Get the corresponding getObject from getData
            JsonObject getObject = getObjectByDate(getData, date);

            if (getObject != null) {
                JsonObject putDetails = putObject.getAsJsonObject("details");
                JsonObject getDetails = getObject.getAsJsonObject("details");

                // Compare the slots and add to the comparedData map
                for (Map.Entry<String, JsonElement> entry : putDetails.entrySet()) {
                    String putSlot = entry.getKey();
                    if (getDetails.has(putSlot)) {
                        JsonObject putSlotData = convertToJsonObject(entry.getValue());
                        JsonObject getSlotData = convertToJsonObject(getDetails.get(putSlot));

                        if (areEqual(putSlotData, getSlotData)) {
                            comparedData.computeIfAbsent(date, k -> new HashMap<>()).put(putSlot, putSlotData);
                        }
                    }
                }
            }
        }
        return !comparedData.isEmpty();
    }

    private static JsonObject convertToJsonObject(JsonElement element) {
        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("value", element.getAsString());
            return jsonObject;
        }
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
            return elem1.getAsJsonObject().equals(elem2.getAsJsonObject());
        } else if (elem1.isJsonPrimitive() && elem2.isJsonPrimitive()) {
            JsonPrimitive primitive1 = elem1.getAsJsonPrimitive();
            JsonPrimitive primitive2 = elem2.getAsJsonPrimitive();

            if (primitive1.isString() && primitive2.isString()) {
                String value1 = primitive1.getAsString().replaceAll("\"", "");
                String value2 = primitive2.getAsString().replaceAll("\"", "");
                return value1.equals(value2);
            } else {
                return primitive1.equals(primitive2);
            }
        }
        return false;
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

}
