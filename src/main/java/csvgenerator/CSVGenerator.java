package csvgenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CSVGenerator {
    public static void main(String[] args) {
        String templateFilePath = "./src/main/java/org/example/BPTemplateCSV.csv";
        String outputFilePath = "./final_BP_data.csv";

        generateCSVFromTemplate(templateFilePath, outputFilePath);
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

        line = line.replaceAll("<TODAY-1>", getDateOffset(todayDate, -1));

        line = line.replaceAll("<TODAY-2>", getDateOffset(todayDate, -2));

        line = line.replaceAll("<LAST_WEEK>", getDateOffset(todayDate, -7));

        line = line.replaceAll("<LAST_MONTH>", getDateOffset(todayDate, -30));

        // Replace <SLOT> placeholder with slot timestamp
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
        int slotIndex = line.indexOf(slotPlaceholder);
        if (slotIndex != -1) {
            String datePart = line.substring(0, slotIndex);
            String remainingPart = line.substring(slotIndex + slotPlaceholder.length());

            String currentDate = datePart.split(",")[0].trim(); // Extract the current date from the line
            long slot = getSlotTimestamp(currentDate);
            line = datePart + slot + remainingPart;
        }
        return line;
    }


    private static long getSlotTimestamp(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = dateFormat.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.getTimeInMillis() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
