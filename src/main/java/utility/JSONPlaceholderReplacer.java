package utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JSONPlaceholderReplacer {
    public static void main(String[] args) {
        String jsonTemplatePath = "./src/main/java/putdata/BPTemplate.json";
        String csvFilePath = "./src/main/java/putdata/test.csv";
        String result = replacePlaceholders(jsonTemplatePath, csvFilePath);
        System.out.println(result);
    }

    public static String replacePlaceholders(String jsonTemplatePath, String csvFilePath) {
        String jsonTemplate = readFromFile(jsonTemplatePath);
        JSONArray csvData = readCSVFile(csvFilePath);

        JSONArray outputArray = new JSONArray();

        for (int i = 0; i < csvData.length(); i++) {
            JSONObject csvRow = csvData.getJSONObject(i);
            JSONObject outputObject = new JSONObject(jsonTemplate);

            String date = csvRow.getString("Date");
            JSONObject slotData = csvRow.getJSONObject("Slot");

            JSONObject slotDetails = new JSONObject();
            JSONObject slotObject = new JSONObject();
            slotObject.put("p", slotData.getString("P"));
            slotObject.put("a", slotData.getString("A"));
            slotObject.put("s", slotData.getString("S"));
            slotDetails.put("Slot", slotObject);

            outputObject.put("date", date);
            outputObject.put("details", slotDetails);
            outputArray.put(outputObject);
        }

        return outputArray.toString();
    }

    private static String readFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONArray readCSVFile(String filePath) {
        JSONArray jsonArray = new JSONArray();

        try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            String[] headers = null;

            while ((line = csvReader.readLine()) != null) {
                String[] values = line.split(",");

                if (isFirstLine) {
                    headers = values;
                    isFirstLine = false;
                } else {
                    JSONObject rowObject = new JSONObject();

                    for (int i = 0; i < headers.length; i++) {
                        String header = headers[i];
                        String value = values[i];
                        rowObject.put(header, value);
                    }

                    jsonArray.put(rowObject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
