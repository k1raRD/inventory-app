package com.k1rard.inventory.inventory.app.utils;

import com.k1rard.inventory.inventory.app.models.Category;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class CategoryExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Category> categories;

    public CategoryExcelExporter(List<Category> categories) {
        this.categories = categories;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Resultados");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Descripcion", style);
    }

    private void createCell(Row row, int columCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columCount);
        Cell cell = row.createCell(columCount);

        if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(Category result: categories) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(result.getId()), style);
            createCell(row, columnCount++, String.valueOf(result.getName()), style);
            createCell(row, columnCount++, String.valueOf(result.getDescription()), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine(); // write header
        writeDataLines(); // write data

        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        workbook.close();

        servletOutputStream.close();
    }
}
