package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class sample {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Method to convert a single sheet of Excel data to JSON format
     *
     * @param excelFile
     * @param sheetIndex
     * @return
     */
    public JsonNode excelSheetToJson(File excelFile, int sheetIndex) {
        ObjectNode jsonData = mapper.createObjectNode();
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            fis = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            String sheetName = sheet.getSheetName();

            List<String> headers = new ArrayList<>();
            ArrayNode sheetData = mapper.createArrayNode();

            // Reading each row of the sheet
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);

                if (j == 0) {
                    // Reading sheet header names
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        if (cell != null) {
                            headers.add(cell.getStringCellValue());
                        } else {
                            headers.add("");
                        }
                    }
                } else {
                    // Reading worksheet data
                    ObjectNode rowData = mapper.createObjectNode();
                    for (int k = 0; k < headers.size(); k++) {
                        Cell cell = row.getCell(k);
                        String headerName = headers.get(k);

                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case FORMULA:
                                    rowData.put(headerName, cell.getCellFormula());
                                    break;
                                case BOOLEAN:
                                    rowData.put(headerName, cell.getBooleanCellValue());
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        rowData.put(headerName, cell.getDateCellValue().toString());
                                    } else {
                                        rowData.put(headerName, cell.getNumericCellValue());
                                    }
                                    break;
                                case BLANK:
                                    rowData.put(headerName, "");
                                    break;
                                default:
                                    rowData.put(headerName, cell.getStringCellValue());
                                    break;
                            }
                        } else {
                            rowData.put(headerName, "");
                        }
                    }
                    sheetData.add(rowData);
                }
            }

            jsonData.set(sheetName, sheetData);

            return jsonData;
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

    /**
     * Main method to test this converter
     *
     * @param args
     */
//    public static void main(String[] args) {
//        // Creating a file object with specific file path
//        File excelFile = new File("path/to/your/excel/file.xlsx");
//        int sheetIndex = 0; // Index of the sheet to convert (0 for the first sheet)
//
//        ExcelToJsonConverter converter = new ExcelToJsonConverter();
//        JsonNode data = converter.excelSheetToJson(excelFile, sheetIndex);
//        System.out.println("Excel sheet data in JSON format:\n" + data);
//    }
}
