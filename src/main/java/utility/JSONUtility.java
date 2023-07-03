package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public static void saveResponseInFile(Response response) {
        String responseBody = response.getBody().asString();

        try (FileWriter fileWriter = new FileWriter("dataSpo2.json")) {
            fileWriter.write(responseBody);
            System.out.println("Response saved to response.json");
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


    public static boolean compareJSONArrays(String jsonStr1, String jsonStr2) {
        try {
            JSONArray jsonArray1 = new JSONArray(jsonStr1);
            JSONArray jsonArray2 = new JSONArray(jsonStr2);

            if (jsonArray1.length() != jsonArray2.length()) {
                return false;
            }

            Set<Integer> matchedIndices = new HashSet<>();

            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
                boolean foundMatch = false;

                for (int j = 0; j < jsonArray2.length(); j++) {
                    if (matchedIndices.contains(j)) {
                        continue;
                    }

                    JSONObject jsonObj2 = jsonArray2.getJSONObject(j);

                    if (compareJSONObjects(jsonObj1, jsonObj2)) {
                        foundMatch = true;
                        matchedIndices.add(j);
                        break;
                    }
                }

                if (!foundMatch) {
                    return false;
                }
            }

            return matchedIndices.size() == jsonArray2.length();
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean compareJSONObjects(JSONObject obj1, JSONObject obj2) {
        try {
            // Get the keys from both objects
            Set<String> keys1 = obj1.keySet();
            Set<String> keys2 = obj2.keySet();

            // Get the common keys
            Set<String> commonKeys = new HashSet<>(keys1);
            commonKeys.retainAll(keys2);

            // Compare the values for common keys
            for (String key : commonKeys) {
                Object value1 = obj1.get(key);
                Object value2 = obj2.get(key);

                if (!areEqualJSONValues(value1, value2)) {
                    return false;
                }
            }

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean areEqualJSONValues(Object value1, Object value2) {
        if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
            return compareJSONObjects((JSONObject) value1, (JSONObject) value2);
        } else if (value1 instanceof JSONArray && value2 instanceof JSONArray) {
            return compareJSONArrays((JSONArray) value1, (JSONArray) value2);
        } else {
            String strValue1 = String.valueOf(value1).replaceAll("[\"\\{\\}\\[\\]]", "");
            String strValue2 = String.valueOf(value2).replaceAll("[\"\\{\\}\\[\\]]", "");
            return strValue1.equals(strValue2);
        }
    }

    private static boolean compareJSONArrays(JSONArray arr1, JSONArray arr2) {
        if (arr1.length() != arr2.length()) {
            return false;
        }

        Set<Integer> matchedIndices = new HashSet<>();

        for (int i = 0; i < arr1.length(); i++) {
            JSONObject element1 = arr1.getJSONObject(i);
            boolean foundMatch = false;

            for (int j = 0; j < arr2.length(); j++) {
                if (matchedIndices.contains(j)) {
                    continue;
                }

                JSONObject element2 = arr2.getJSONObject(j);

                if (compareJSONObjects(element1, element2)) {
                    foundMatch = true;
                    matchedIndices.add(j);
                    break;
                }
            }

            if (!foundMatch) {
                return false;
            }
        }

        return matchedIndices.size() == arr2.length();
    }

//    private static boolean compareJSONObjects(JSONObject obj1, JSONObject obj2) {
//        if (obj1.length() != obj2.length()) {
//            return false;
//        }
//
//        Iterator<String> keys = obj1.keys();
//
//        while (keys.hasNext()) {
//            String key = keys.next();
//
//            if (!obj2.has(key)) {
//                return false;
//            }
//
//            Object value1 = obj1.get(key);
//            Object value2 = obj2.get(key);
//
//            if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
//                if (!compareJSONObjects((JSONObject) value1, (JSONObject) value2)) {
//                    return false;
//                }
//            } else if (value1 instanceof JSONArray && value2 instanceof JSONArray) {
//                if (!compareJSONArrays(value1.toString(), value2.toString())) {
//                    return false;
//                }
//            } else {
//                if (!Objects.equals(value1, value2)) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }
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
