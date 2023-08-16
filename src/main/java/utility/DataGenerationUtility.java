package utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataGenerationUtility {

    public static void main(String[] args) {
        System.out.println("generated json :"+jsonGenerator("health","pt/logs"));
//        System.out.println(jsonGenerator("users"));
    }
    public static void csvGenerator(String URLName,String URLItem){
        String templateFilePath = "./src/main/java/csvtemplates/"+URLName+"/"+URLItem+"CsvTemplate.csv";
        String outputFilePath = "./src/main/resources/generatedCSVData/"+URLName+"/"+URLItem+"Data.csv";

        generateCSVFromTemplate(templateFilePath,outputFilePath);
    }

    public static void csvGenerator(String URI)
    {
        String templateFilePath = "./src/main/java/csvtemplates/"+URI+"CsvTemplate.csv";
        String outputFilePath = "./src/main/resources/generatedCSVData/"+URI+"Data.csv";
        generateCSVFromTemplate(templateFilePath,outputFilePath);
    }

    private static void generateCSVFromTemplate(String templateFilePath, String outputFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(templateFilePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = replacePlaceholders(line);
                bw.write(line);
                bw.newLine();
            }

            System.out.println("CSV generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String replacePlaceholders(String line) {
        String todayDate = getCurrentDate();
        line = line.replaceAll("<TODAY>", todayDate);

        line = line.replaceAll("<TODAY-1>", Objects.requireNonNull(getDateOffset(todayDate, -1)));

        line = line.replaceAll("<TODAY-2>", Objects.requireNonNull(getDateOffset(todayDate, -2)));

        line = line.replaceAll("<LAST_WEEK>", Objects.requireNonNull(getDateOffset(todayDate, -7)));

        line = line.replaceAll("<LAST_MONTH>", Objects.requireNonNull(getDateOffset(todayDate, -30)));


        line = replaceSlotPlaceholder(line);

        return line;
    }

    private static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    private static String getDateOffset(String currentDate, int offset) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(currentDate));
            calendar.add(Calendar.DAY_OF_MONTH, offset);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String replaceSlotPlaceholder(String line) {
        String slotPlaceholder = "<SLOT>";
        String slot2Placeholder = "<SLOT2>";

        int slotIndex = line.indexOf(slotPlaceholder);
        int slot2Index = line.indexOf(slot2Placeholder);

        if (slotIndex != -1) {
            String datePart = line.substring(0, slotIndex);
            String remainingPart = line.substring(slotIndex + slotPlaceholder.length());

            String currentDate = datePart.split(",")[0].trim();
            long slot = getSlotTimestamp(currentDate, 10, 0); // 10 AM
            line = datePart + slot + remainingPart;
        }

        if (slot2Index != -1) {
            String datePart = line.substring(0, slot2Index);
            String remainingPart = line.substring(slot2Index + slot2Placeholder.length());

            String currentDate = datePart.split(",")[0].trim();
            long slot2 = getSlotTimestamp(currentDate, 18, 0); // 6 PM
            line = datePart + slot2 + remainingPart;
        }

        return line;
    }

    private static long getSlotTimestamp(String date, int hour, int minute) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.getTimeInMillis() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //Methods to generate JSON from CSV file

    public static List<JSONObject> jsonGenerator(String HealthApiItem)
    {
        csvGenerator(HealthApiItem);
        String csvFilePath = "./src/main/resources/generatedCSVData/"+HealthApiItem+"Data.csv";
        String jsonFilePath = "./src/main/java/jsontemplates/"+HealthApiItem+"Template.json";
        return CreateJsonFromCSV(csvFilePath,jsonFilePath);
    }



    public static List<JSONObject> jsonGenerator(String URLName,String URLItem)
    {
        csvGenerator(URLName,URLItem);
        String csvFilePath = "./src/main/resources/generatedCSVData/"+URLName+"/"+URLItem+"Data.csv";
        String jsonFilePath = "./src/main/java/jsontemplates/"+URLName+"/"+URLItem+"Template.json";
        return CreateJsonFromCSV(csvFilePath,jsonFilePath);
    }

    public static List<JSONObject> CreateJsonFromCSV(String CsvFilePath, String JsonFilePath) {
        List<List<String>> data = readCSV(CsvFilePath);
        String jsonTemplate = readJSONTemplate(JsonFilePath);

        List<JSONObject> jsonList = createJSONs(data, jsonTemplate);
        if(jsonTemplate.contains("ARRAY"))
        {
            convertArrayLikeStrings(jsonList);
        }
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

                        row.add(Arrays.toString(values));

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
        System.out.println(placeholders);
        boolean hasDatePlaceholder = placeholders.contains("DATE");

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
        if(hasDatePlaceholder)
            jsonList =  combineDetailsForSameDate(jsonList);
        if(jsonTemplate.contains("ARRAY"))
        {
            return convertArrayLikeStrings(jsonList);
        }
        else return jsonList;
    }

    public static List<JSONObject> createJSONsNoDate(List<List<String>> data, String jsonTemplate) {
        List<JSONObject> jsonList = new ArrayList<>();

        if (data.size() < 2) {
            System.out.println("Insufficient data rows to create JSONs.");
            return jsonList;
        }

        List<String> placeholders = data.get(0);
        List<String> cleanedPlaceholders = removeAngleBrackets(placeholders);

        // Check if the JSON template contains a "date" placeholder
        boolean hasDatePlaceholder = cleanedPlaceholders.contains("date");

        for (int i = 1; i < data.size(); i++) {
            List<String> values = data.get(i);
            String jsonStr = replacePlaceholders(jsonTemplate, cleanedPlaceholders, values);
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                // If the template contains "date" placeholder, combine details for the same date
                if (hasDatePlaceholder) {
                    jsonList = combineDetailsForSameDate(jsonList);
                } else {
                    jsonList.add(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonList;
    }

    public static String replacePlaceholders(String jsonTemplate, List<String> placeholders, List<String> values) {
        Map<String, String> placeholderValues = new LinkedHashMap<>(); // Use LinkedHashMap to preserve insertion order
        for (int i = 0; i < placeholders.size(); i++) {
            String placeholder = placeholders.get(i);
            String value = (i < values.size()) ? values.get(i) : "";
            placeholderValues.put(placeholder, value);
        }

        for (Map.Entry<String, String> entry : placeholderValues.entrySet()) {
            String placeholder = entry.getKey();
            String value = entry.getValue();
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
            JSONObject details = obj.optJSONObject("details");
            JSONObject existingObject = null;

            for (JSONObject mergedObj : mergedData) {
                if (mergedObj.getString("date").equals(date)) {
                    existingObject = mergedObj;
                    break;
                }
            }

            if (existingObject != null) {
                if (details != null) {
                    Iterator<String> keys = details.keys();
                    while (keys.hasNext()) {
                        String slot = keys.next();
                        Object slotValue = details.opt(slot);
                        Object existingSlotValue = existingObject.getJSONObject("details").opt(slot);

                        if (existingSlotValue instanceof JSONObject && slotValue instanceof JSONObject) {
                            mergeJSONObjects((JSONObject) existingSlotValue, (JSONObject) slotValue);
                        } else {
                            if (existingSlotValue != null) {
                                JSONArray jsonArray = new JSONArray();
                                if (existingSlotValue instanceof JSONArray) {
                                    jsonArray = (JSONArray) existingSlotValue;
                                } else {
                                    jsonArray.put(existingSlotValue);
                                }
                                jsonArray.put(slotValue);
                                existingObject.getJSONObject("details").put(slot, jsonArray);
                            } else {
                                existingObject.getJSONObject("details").put(slot, slotValue);
                            }
                        }
                    }
                }
            } else {
                mergedData.add(new JSONObject(obj.toString()));
            }
        }

        return mergedData;
    }

    private static void mergeJSONObjects(JSONObject target, JSONObject source) {
        Iterator<String> keys = source.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = source.get(key);

            if (target.has(key) && target.get(key) instanceof JSONObject && value instanceof JSONObject) {
                mergeJSONObjects(target.getJSONObject(key), (JSONObject) value);
            } else {
                target.put(key, value);
            }
        }
    }

    public static List<JSONObject> convertArrayLikeStrings(List<JSONObject> jsonObjects) throws JSONException {
        List<JSONObject> modifiedJSONObjects = new ArrayList<>();
        for (JSONObject jsonObject : jsonObjects) {
            JSONObject modifiedJSONObject = new JSONObject();

            String[] keys = JSONObject.getNames(jsonObject);
            if (keys != null) {
                for (String key : keys) {
                    String value = jsonObject.optString(key);
                    if (value.startsWith("[") && value.endsWith("]")) {
                        JSONArray array = new JSONArray(value);
                        modifiedJSONObject.put(key, arrayToList(array));
                    } else {
                        modifiedJSONObject.put(key, value);
                    }
                }
            }
            modifiedJSONObjects.add(modifiedJSONObject);
        }
        return modifiedJSONObjects;
    }

    public static List<String> arrayToList(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
        }
        return list;
    }
}
