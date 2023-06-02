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
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    public static List<String> fetchDatesFromJson(String filePath) {
        List<String> dates = new ArrayList<>();
        String jsonString;

        if (filePath.contains("xlsx")) {
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


//    public boolean areEqualIgnoringProductField(String jsonString1, String jsonString2) {
//        JsonNode arrayNode1 = null;
//        try {
//            arrayNode1 = objectMapper.readTree(jsonString1);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        JsonNode arrayNode2 = null;
//        try {
//            arrayNode2 = objectMapper.readTree(jsonString2);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (arrayNode1.size() != arrayNode2.size()) {
//            return false;
//        }
//
//        for (int i = 0; i < arrayNode1.size(); i++) {
//            JsonNode node1 = arrayNode1.get(i);
//            JsonNode node2 = arrayNode2.get(i);
//
//            // Remove the "product" field from the nodes before comparison
//            ((ObjectNode) node1).remove("product");
//            ((ObjectNode) node2).remove("product");
//            System.out.println("node1  " + node1);
//            System.out.println("node2  " + node2);
//            if (!node1.equals(node2)) {
//                return false;
//            }
//        }
//
//        return true;
//    }


    public static boolean compareUsingCommonFields(String jsonString1, String jsonString2) {
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

            for (Iterator<String> fieldNames = node1.fieldNames(); fieldNames.hasNext(); ) {
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
