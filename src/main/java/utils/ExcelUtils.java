package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;

    /**
     * Opens an Excel file and loads a specific sheet.
     *
     * @param filePath  Path to the Excel file.
     * @param sheetName Name of the sheet to load.
     */
    public static void loadExcel(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the file.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + filePath, e);
        }
    }

    /**
     * Gets the value from a specific cell.
     *
     * @param rowNumber Row number (0-based index).
     * @param colNumber Column number (0-based index).
     * @return Cell value as a String.
     */
    public static String getCellValue(int rowNumber, int colNumber) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            return "";
        }

        Cell cell = row.getCell(colNumber);
        if (cell == null) {
            return "";
        }

        return getCellDataAsString(cell);
    }

    /**
     * Sets a value in a specific cell.
     *
     * @param rowNumber Row number (0-based index).
     * @param colNumber Column number (0-based index).
     * @param value     Value to set.
     */
    public static void setCellValue(int rowNumber, int colNumber, String value) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            row = sheet.createRow(rowNumber);
        }

        Cell cell = row.getCell(colNumber);
        if (cell == null) {
            cell = row.createCell(colNumber);
        }

        cell.setCellValue(value);
    }

    /**
     * Saves the changes to the Excel file.
     *
     * @param filePath Path to save the Excel file.
     */
    public static void saveExcel(String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save Excel file: " + filePath, e);
        }
    }

    /**
     * Closes the workbook.
     */
    public static void closeExcel() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to close Excel workbook", e);
        }
    }

    /**
     * Converts cell data to a string representation.
     *
     * @param cell Cell to convert.
     * @return Cell data as a String.
     */
    private static String getCellDataAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
