package com.it.doubledi.cinemamanager._common.util;

import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.util.Objects;

public class ExcelUtils {
    public static final int FONT_SIZE = 12;
    public static final String NAME_CELL = "nameCell";
    public static final String BLANK = "";

    private ExcelUtils() {

    }

    public static String readCellContent(Cell cell) {
        String content;

        switch (cell.getCellType()) {

            case STRING:
                content = StrUtil.getString(cell.getStringCellValue());

                break;
            case NUMERIC:
                content = BigDecimal.valueOf(cell.getNumericCellValue()).toString();

                break;
            case BOOLEAN:
                content = cell.getBooleanCellValue() + BLANK;

                break;
            case FORMULA:
                content = cell.getCellFormula() + BLANK;

                break;
            default:
                content = BLANK;
        }

        return content;
    }

    public static void createCell(Row row, int index, CellStyle style, String message) {
        Cell cell = row.createCell(index, CellType.STRING);
        cell.setCellValue(message);

        cell.setCellStyle(style);

        row.setRowStyle(style);
    }

    public static CellStyle createCellStyle(Workbook workbook, short fontIndex, String type) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();

        font.setColor(fontIndex);

        // set font bold neu la ten cot
        if (Objects.nonNull(type) && type.equals(NAME_CELL)) {
            font.setBold(true);
        } else {
            font.setItalic(true);
        }
        font.setFontHeight((short) (FONT_SIZE * 20));

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        font.setFontName("Times New Roman");
        style.setFont(font);
        style.setWrapText(true);

        return style;
    }

    public static CellStyle createValueCellStyle(Workbook workbook) {
        return createCellStyle(workbook, IndexedColors.BLACK1.getIndex(), null);
    }
}
