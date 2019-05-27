/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.run.tool.application;

import java.io.File;
import java.text.ParseException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;

import jp.co.run.tool.abstracts.AbstractReadFile;
import jp.co.run.tool.common.CommonConsts;
import jp.co.run.tool.common.CommonProperties;
import jp.co.run.tool.common.CommonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadFile.
 */
public class ReadFile extends AbstractReadFile<String> {

    /*
     * (non-Javadoc)
     *
     * @see
     * jp.co.run.tool.abstracts.AbstractReadFile#getData(org.apache.poi.ss.usermodel
     * .Workbook)
     */
    @Override
    public String getData(Workbook myWorkBook) throws ParseException {
        // Check Workbook not null.
        String errorResult = "";
        if (myWorkBook != null) {
            // Check active sheet
            int sheetActiveIndex = myWorkBook.getActiveSheetIndex();
            if (CommonProperties.isSheetChecked && sheetActiveIndex != CommonConsts.ZERO) {
                System.out.println("Active sheet isn't exactly. Current active sheet is " + myWorkBook.getSheetName(sheetActiveIndex));
                errorResult += "\nActive sheet isn't exactly. Current active sheet is " + myWorkBook.getSheetName(sheetActiveIndex);
            }
            int numberSheet = myWorkBook.getNumberOfSheets();
            for (int i = 0; i < numberSheet; i++) {
                // Get number sheet from workbook.
                Sheet mySheet = myWorkBook.getSheetAt(i);
                String nameSheet = myWorkBook.getSheetName(i);
                // Check Sheet not null.
                if (mySheet != null) {
                    Iterator<?> rowIterator = mySheet.rowIterator();

                    // Check active cell of current sheet
                    if (CommonProperties.isCellChecked && mySheet.getActiveCell() != CellAddress.A1) {
                        System.out.println("Sheet : " + nameSheet + " Active cell isn't exactly : " + mySheet.getActiveCell());
                        errorResult += "\nSheet : " + nameSheet + " Active cell isn't exactly : " + mySheet.getActiveCell();
                    }
                    while (rowIterator.hasNext()) {
                        Row row = (Row) rowIterator.next();
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();

                            int colIndex = cell.getColumnIndex();
                            int rowIndex = cell.getRowIndex();
                            CellAddress cellAddress = new CellAddress(rowIndex, colIndex);
                            int typeCell = cell.getCellType();

                            // Check content isn't date
                            if (typeCell == Cell.CELL_TYPE_STRING) {
                                // Check character Vietnamese
                                if (CommonProperties.isVietnameseChecked && CommonUtils.isVietnamese(cell.getStringCellValue())) {
                                    System.out.println("Sheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " content contains Vietnamese " + " - Value: " + cell.getStringCellValue());
                                    errorResult += "\nSheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " content contains Vietnamese " + " - Value: " + cell.getStringCellValue();
                                }

                                // Check font name
                                Font font = getFont(cell);
                                if (CommonProperties.isFontChecked && CommonUtils.isFontNotExactly(font.getFontName())) {
                                    System.out.println("Sheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " font isn't exactly"
                                            + " - Font : " + font.getFontName());
                                    errorResult += "\nSheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " font isn't exactly"
                                            + " - Font : " + font.getFontName();
                                }

                                // Check font size
                                if (CommonProperties.isSizeChecked && CommonUtils.isFontSizeNotExactly(font.getFontHeightInPoints())) {
                                    System.out.println("Sheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " font size isn't exactly"
                                            + " - Size : " + font.getFontHeightInPoints());
                                    errorResult += "\nSheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " font size isn't exactly"
                                            + " - Size : " + font.getFontHeightInPoints();
                                }
                            }

                            // Check content is date
                            if (typeCell == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                                if (CommonProperties.isFormatChecked && CommonUtils.isDateFormatNotExactly(cell.getCellStyle().getDataFormatString())) {
                                    System.out.println("Sheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " format date isn't exactly"
                                            + " - Value : " + cell.getNumericCellValue());
                                    errorResult += "\nSheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " format date isn't exactly";
                                }

                                Font font = getFont(cell);
                                // Check font name
                                if (CommonProperties.isFontChecked && CommonUtils.isFontNotExactly(font.getFontName())) {
                                    System.out.println("Sheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " font isn't exactly"
                                            + " - Font : " + font.getFontName());
                                    errorResult += "\nSheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " font isn't exactly"
                                            + " - Font : " + font.getFontName();
                                }

                                // Check font size
                                if (CommonProperties.isSizeChecked && CommonUtils.isFontSizeNotExactly(font.getFontHeightInPoints())) {
                                    System.out.println("Sheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " font size isn't exactly"
                                            + " - Size : " + font.getFontHeightInPoints());
                                    errorResult += "\nSheet : " + nameSheet + " - Cell : " + cellAddress.toString() + " font size isn't exactly"
                                            + " - Size : " + font.getFontHeightInPoints();
                                }
                            }
                        }
                    }
                } else {
                    return errorResult;
                }
            }
        }
        return errorResult;
    }

    /**
     * List files for folder.
     *
     * @param folder
     *            the folder
     */
    public String listFilesForFolder(final File folder, int typeExcute) {
        String errorResult = "";
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, typeExcute);
            } else {
                // Check file is excel file
                if (CommonUtils.isAcceptFile(fileEntry)) {
                    errorResult += "\n==========================================================\n" + fileEntry.getName();
                    System.out.println(fileEntry.getName());
                    errorResult += readFile(fileEntry.getPath(), typeExcute);
                }
            }
        }
        return errorResult;
    }

    /**
     * Getting current font from cell.
     *
     * @param cell the cell
     * @return the font
     */
    private Font getFont(Cell cell) {
        Workbook wb = cell.getRow().getSheet().getWorkbook();
        CellStyle style = cell.getCellStyle();
        return wb.getFontAt(style.getFontIndex());
    }

    /**
     * Creates the font style data format.
     *
     * @param workbook the workbook
     * @return the cell style
     */
    public CellStyle createFontStyleDataFormat(Workbook workbook, CellStyle cellStyle, Cell cell)
    {
        Font new_font = getFont(cell);
        try {
            // Check repair font
            if (CommonProperties.isFontChecked) {
                new_font.setFontName(CommonProperties.getFontCell());
            }

            // Check repair font size
            if (CommonProperties.isSizeChecked) {
                new_font.setFontHeightInPoints((short)CommonProperties.getFontSizeCell());
            }

            // Check cell format date
            if (CommonProperties.isFormatChecked) {
                short format = workbook.createDataFormat().getFormat(CommonProperties.getFormatCell());
                cellStyle.setDataFormat(format);
            }
            cellStyle.setFont(new_font);
            return cellStyle;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            return cellStyle;
        }
    }

    @Override
    public String repairDocument(Workbook myWorkBook, String nameFile) throws Exception {
     // Check Workbook not null.
        String errorResult = "";
        if (myWorkBook != null) {
            // Check active sheet
            int sheetActiveIndex = myWorkBook.getActiveSheetIndex();
            if (CommonProperties.isSheetChecked && sheetActiveIndex != CommonConsts.ZERO) {
                System.out.println("Active sheet isn't exactly. Current active sheet is " + myWorkBook.getSheetName(sheetActiveIndex));
                errorResult += "\nActive sheet isn't exactly. Current active sheet is " + myWorkBook.getSheetName(sheetActiveIndex);
                myWorkBook.setActiveSheet(0);
            }
            // Get number of sheets
            int numberSheet = myWorkBook.getNumberOfSheets();
            for (int i = 0; i < numberSheet; i++) {
                // Get number sheet from workbook.
                Sheet mySheet = myWorkBook.getSheetAt(i);
                String nameSheet = myWorkBook.getSheetName(i);
                // Check Sheet not null.
                if (mySheet != null) {
                    Iterator<?> rowIterator = mySheet.rowIterator();

                    // Check active cell of current sheet
                    if (CommonProperties.isCellChecked && mySheet.getActiveCell() != CellAddress.A1) {
                        System.out.println("Sheet : " + nameSheet + " Active cell isn't exactly : " + mySheet.getActiveCell());
                        errorResult += "\nSheet : " + nameSheet + " Active cell isn't exactly : " + mySheet.getActiveCell();
                        mySheet.setActiveCell(CellAddress.A1);
                    }
                    while (rowIterator.hasNext()) {
                        Row row = (Row) rowIterator.next();
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            int typeCell = cell.getCellType();
                            CellStyle cellStyle = cell.getCellStyle();

                            // Check content isn't date
                            if (typeCell == Cell.CELL_TYPE_STRING) {
//                                cell.setCellStyle(cellStyleNormal);
                            }

                            // Check content is date
                            if (typeCell == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
//                                cell.setCellStyle(cellStyleFormat);
                            }
                        }
                    }
                } else {
                    return errorResult;
                }
            }
        }
        // Write file and check success
        boolean isWriteFile = WriteFile.writeWorkbook(myWorkBook, nameFile);
        if (!isWriteFile) {
            return CommonConsts.EMPTY;
        }
        return errorResult;
    }
}
