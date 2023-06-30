package utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JSONPlaceholderReplacer {
    public static void main(String[] args) {
        String csvFile = "./src/main/java/csvdata/BPData.csv";
        String jsonFile = "./src/main/java/jsontemplates/BPTemplate.json";
        System.out.println(CreateJsonFromCSV(csvFile, jsonFile));
    }

    public static List<JSONObject> CreateJsonFromCSV(String CsvFilePath, String JsonFilePath) {
        List<List<String>> data = readCSV(CsvFilePath);
        String jsonTemplate = readJSONTemplate(JsonFilePath);

        List<JSONObject> jsonList = createJSONs(data, jsonTemplate);
        return jsonList;
    }

    public static List<List<String>> readCSV(String csvFile) {
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split(",");
                List<String> row = new ArrayList<>();
                for (String value : rowValues) {
                    if (value.contains("|")) {
                        String[] values = value.split("\\|");
                        row.add(Arrays.asList(values).toString());
                    } else {
                        row.add(value);
                    }
                }
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static String readJSONTemplate(String jsonFile) {
        StringBuilder jsonTemplate = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonTemplate.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonTemplate.toString();
    }

    public static List<JSONObject> createJSONs(List<List<String>> data, String jsonTemplate) {
        List<JSONObject> jsonList = new ArrayList<>();

        if (data.size() < 2) {
            System.out.println("Insufficient data rows to create JSONs.");
            return jsonList;
        }

        List<String> placeholders = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            List<String> values = data.get(i);
            String jsonStr = replacePlaceholders(jsonTemplate, removeAngleBrackets(placeholders), values);
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                jsonList.add(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return combineDetailsForSameDate(jsonList);
    }

    public static String replacePlaceholders(String jsonTemplate, List<String> placeholders, List<String> values) {
        for (int i = 0; i < placeholders.size(); i++) {
            String placeholder = placeholders.get(i);
            String value = (i < values.size()) ? values.get(i) : "";
            jsonTemplate = jsonTemplate.replace("<" + placeholder + ">", value);
        }
        return jsonTemplate;
    }

    public static List<String> removeAngleBrackets(List<String> placeholders) {
        List<String> cleanedPlaceholders = new ArrayList<>();
        for (String placeholder : placeholders) {
            placeholder = placeholder.replaceAll("<", "").replaceAll(">", "");
            cleanedPlaceholders.add(placeholder);
        }
        return cleanedPlaceholders;
    }

    public static List<JSONObject> combineDetailsForSameDate(List<JSONObject> jsonList) {
        List<JSONObject> mergedData = new ArrayList<>();
        for (JSONObject obj : jsonList) {
            String date = obj.getString("date");
            JSONObject details = obj.getJSONObject("details");
            JSONObject existingObject = null;
            for (JSONObject mergedObj : mergedData) {
                if (mergedObj.getString("date").equals(date)) {
                    existingObject = mergedObj;
                    break;
                }
            }
            if (existingObject != null) {
                Iterator<String> keys = details.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    existingObject.getJSONObject("details").put(key, details.get(key));
                }
            } else {
                mergedData.add(new JSONObject(obj.toString()));
            }
        }
        return mergedData;
    }
}
