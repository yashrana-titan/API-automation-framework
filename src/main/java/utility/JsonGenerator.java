package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
public class JsonGenerator {

    public static void main(String[] args) {
        String csvFile = "./src/main/java/putdata/test.csv";
        String templateJsonFile = "./src/main/java/putdata/BPTemplate.json";

        String modifiedJson = replaceValuesInJson(csvFile, templateJsonFile);
        System.out.println(modifiedJson);
    }

    public static String replaceValuesInJson(String csvFilePath, String templateJsonFilePath) {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
            String csvLine;
            String[] csvHeaders = csvReader.readLine().split(",");
            JSONArray jsonArray = new JSONArray();
            while ((csvLine = csvReader.readLine()) != null) {
                String[] csvValues = csvLine.split(",");
                JSONObject jsonObject = new JSONObject();
                String date = csvValues[0];
                jsonObject.put("date", date);
                JSONObject existingObject = getJSONObjectForDate(jsonArray, date);
                JSONObject detailsObject;
                if (existingObject != null) {
                    detailsObject = existingObject.getJSONObject("details");
                } else {
                    detailsObject = new JSONObject();
                    jsonObject.put("details", detailsObject);
                }
                for (int i = 1; i < csvValues.length; i += 4) {
                    String slot = csvValues[i];
                    String[] slotValues = csvValues[i + 1].split("\\|");
                    String[] PValues = csvValues[i + 2].split("\\|");
                    String[] DValues = csvValues[i + 3].split("\\|");

                    JSONObject slotObject = new JSONObject();
                    if (slotValues.length > 1) {
                        JSONArray SArray = new JSONArray();
                        for (String value : slotValues) {
                            SArray.put(value);
                        }
                        slotObject.put("S", SArray);
                    } else {
                        slotObject.put("S", slotValues[0]);
                    }
                    if (PValues.length > 1) {
                        JSONArray PArray = new JSONArray();
                        for (String value : PValues) {
                            PArray.put(value);
                        }
                        slotObject.put("P", PArray);
                    } else {
                        slotObject.put("P", PValues[0]);
                    }

                    if (DValues.length > 1) {
                        JSONArray DArray = new JSONArray();
                        for (String value : DValues) {
                            DArray.put(value);
                        }
                        slotObject.put("D", DArray);
                    } else {
                        slotObject.put("D", DValues[0]);
                    }
                    detailsObject.put(slot, slotObject);
                }

                if (existingObject == null) {
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject getJSONObjectForDate(JSONArray jsonArray, String date) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("date").equals(date)) {
                return jsonObject;
            }
        }
        return null;
    }
}
