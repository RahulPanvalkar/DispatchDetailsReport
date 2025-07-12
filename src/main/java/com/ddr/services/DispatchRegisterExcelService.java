package com.ddr.services;

import com.ddr.dao.DispatchReportDAO;
import com.ddr.model.DispatchRegisterDTO;
import com.ddr.model.DispatchReport;
import com.ddr.util.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class DispatchRegisterExcelService {

    private static final DispatchReportDAO dispatchReportDAO = new DispatchReportDAO();

    private static final Logger logger = LoggerUtil.getLogger(DispatchRegisterExcelService.class);

    /*public static void main(String[] args) {
        //DownloadService service = new DownloadService();
        String filePath = "D:\\RAHUL\\TASK\\Dispatch details report summary\\DispatchDetailsReport\\module-resource\\Dispatch-Register-Summary.xlsx";
        DispatchRegisterDTO dto = new DispatchRegisterDTO();
        dto.setBranch("10");
        dto.setStockPoint("10");
        dto.setDivision("0");
        dto.setStartDate("01/04/2023");
        dto.setEndDate("31/03/2024");
        dto.setCustomer("0");
        dto.setReportType("N");
        dto.setFinancialYear("17");

        DispatchRegisterExcelService service = new DispatchRegisterExcelService();
        service.updateFileData(dto);
    }*/

    public String updateFileData(DispatchRegisterDTO registerDTO) {
        logger.debug("updating excel file..");
        if (registerDTO == null) {
            logger.debug("registerDTO is null");
            return "";
        }
        try {
            // getting DispatchRegister list
            List<DispatchReport> dispRegList = dispatchReportDAO.callDispatchRegisterProcedure(registerDTO);

            // get Location Name
            String branch = registerDTO.getBranch();
            if (branch == null || branch.trim().isEmpty()) branch = "";
            String locationName = dispatchReportDAO.getLocationNameByLocId(Integer.parseInt(branch));

            // get customer name
            String custId = registerDTO.getBranch();
            if (custId == null || custId.trim().isEmpty()) return "";
            String custName = dispatchReportDAO.getCustomerNameByCustId(custId);

            String finYearRange = registerDTO.getStartDate() + " TO " + registerDTO.getEndDate();

            //Excel file path
            String filePath = "D:\\RAHUL\\TASK\\Dispatch details report summary\\DispatchDetailsReport\\module-resource\\Dispatch-Register-Summary.xlsx";

            addDispatchDataInFile(filePath, dispRegList, custName, locationName, finYearRange);

            // after successful update return file path for downloading
            return filePath;
        } catch (Exception e) {
            logger.error("Exception occurred in updateFileData :: ", e);
        }
        return "";
    }

    //
    private static void addDispatchDataInFile(String filePath, List<DispatchReport> dispRegList, String custName, String locationName, String finYearRange) {
        try {

            logger.debug("dispRegList size >> [{}]", dispRegList.size());
            if (!dispRegList.isEmpty()) logger.debug(dispRegList.get(0));

            logger.debug("creating workbook..");
            XSSFWorkbook workbook = new XSSFWorkbook();
            logger.debug("creating sheet..");
            XSSFSheet xssfSheet = workbook.createSheet("Dispatch Register Summary");

            // Create header in row 1
            String[] headers = {"TRANSACTION NO", "DISPATCH DATE", "PARTY", "DESTINATION", "TRANSPORTER", "GOODS VALUE",
                    "INVOICE NO", "LR NO", "DRIVER NAME", "LORRY NUMBER", "LR DATE", "DISPATCH DELAY", "NO OF CASES",
                    "FORM NUM", "C FORM DATE", "C FORM VALUE", "POD DATE", "POD NUMBER", "REASON"};

            int headerLength = headers.length;

            // Create title in row 0 , 1, 2
            String title = "Dispatch Register Summary Report From : " + finYearRange;
            createTitleRow(workbook, xssfSheet, custName.toUpperCase(), headerLength, 0);
            createTitleRow(workbook, xssfSheet, locationName.toUpperCase(), headerLength, 1);
            createTitleRow(workbook, xssfSheet, title, headerLength, 2);

            // Create Header Row
            createHeaderRow(workbook, xssfSheet, headers, 3);

            // Create custom styled row to show Division
            createStyledRow(workbook, xssfSheet, 4, "Division : MAIN", 0, headerLength - 1,
                    (short) 9, "Arial", IndexedColors.BLACK.getIndex(), IndexedColors.LIGHT_CORNFLOWER_BLUE.index,
                    true, false, CellStyle.ALIGN_LEFT);

            // font style for body
            Font cellFont = (XSSFFont) workbook.createFont();
            cellFont.setFontName("Arial");
            cellFont.setFontHeightInPoints((short) 9);

            // date cell style
            CellStyle dateStyle = workbook.createCellStyle();
            short dateFormat = workbook.createDataFormat().getFormat("dd/MM/yyyy");
            dateStyle.setDataFormat(dateFormat);
            dateStyle.setFont(cellFont);

            // Data cell style
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setFont(cellFont);
            dataStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

            int rowNum = 4;
            for (DispatchReport dispReg : dispRegList) {
                ++rowNum;

                Row row = xssfSheet.createRow(rowNum);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(dispReg.getDspTrnNo());
                cell0.setCellStyle(dataStyle);

                Cell cell1 = row.createCell(1);
                if (dispReg.getDspDt() != null) {
                    cell1.setCellValue(java.sql.Date.valueOf(dispReg.getDspDt()));
                }
                cell1.setCellStyle(dateStyle);

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(dispReg.getCustName());
                cell2.setCellStyle(dataStyle);

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(dispReg.getDestination());
                cell3.setCellStyle(dataStyle);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(dispReg.getTransporter());
                cell4.setCellStyle(dataStyle);

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(dispReg.getGoodsValue());
                cell5.setCellStyle(dataStyle);

                Cell cell6 = row.createCell(6);
                cell6.setCellValue(dispReg.getInvNo());
                cell6.setCellStyle(dataStyle);

                Cell cell7 = row.createCell(7);
                cell7.setCellValue(dispReg.getLrNum());
                cell7.setCellStyle(dataStyle);

                Cell cell8 = row.createCell(8);
                cell8.setCellValue(dispReg.getDriverName());
                cell8.setCellStyle(dataStyle);

                Cell cell9 = row.createCell(9);
                cell9.setCellValue(dispReg.getLorryNo());
                cell9.setCellStyle(dataStyle);

                Cell cell10 = row.createCell(10);
                if (dispReg.getLrDate() != null) {
                    cell10.setCellValue(java.sql.Date.valueOf(dispReg.getLrDate()));
                }
                cell10.setCellStyle(dateStyle);

                Cell cell11 = row.createCell(11);
                cell11.setCellValue(dispReg.getDelayDays());
                cell11.setCellStyle(dataStyle);

                Cell cell12 = row.createCell(12);
                cell12.setCellValue(dispReg.getNoOfCases());
                cell12.setCellStyle(dataStyle);

                Cell cell13 = row.createCell(13);
                cell13.setCellValue(dispReg.getFormNum());
                cell13.setCellStyle(dataStyle);

                Cell cell14 = row.createCell(14);
                if (dispReg.getcFormDate() != null) {
                    cell14.setCellValue(java.sql.Date.valueOf(dispReg.getcFormDate()));
                }
                cell14.setCellStyle(dateStyle);

                Cell cell15 = row.createCell(15);
                cell15.setCellValue(dispReg.getcFormValue());
                cell15.setCellStyle(dataStyle);

                Cell cell16 = row.createCell(16);
                if (dispReg.getPodDate() != null) {
                    cell16.setCellValue(java.sql.Date.valueOf(dispReg.getPodDate()));
                }
                cell16.setCellStyle(dateStyle);

                Cell cell17 = row.createCell(17);
                cell17.setCellValue(dispReg.getPodNum());
                cell17.setCellStyle(dataStyle);

                Cell cell18 = row.createCell(18);
                cell18.setCellValue(dispReg.getPodReason());
                cell18.setCellStyle(dataStyle);

            }

            // to set column size to auto
            for (int i = 0; i < headers.length; i++) {
                xssfSheet.autoSizeColumn(i);
            }

            // Write to file
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (Exception e) {
            logger.error("Exception occurred in addDispatchDataInFile :: ", e);
        }
    }

    // Method to create the title row
    public static void createTitleRow(Workbook workbook, Sheet sheet, String title, int columnSpan, int rowNo) {
        logger.debug("creating title row >> columnSpan : [{}], rowNo : [{}]", columnSpan, rowNo);
        CellStyle titleStyle = workbook.createCellStyle();

        Font titleFont = (XSSFFont) workbook.createFont();
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setColor(IndexedColors.BLACK.getIndex());
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setFontName("Arial");

        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // Background color
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        Row titleRow = sheet.createRow(rowNo);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);

        // Merge columns (0 to columnSpan - 1)
        sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 0, columnSpan - 1));
    }

    // Method to create the header row
    public static void createHeaderRow(Workbook workbook, Sheet sheet, String[] headers, int rowNo) {
        logger.debug("creating header row >> rowNo : [{}]", rowNo);
        CellStyle headerStyle = workbook.createCellStyle();

        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerStyle.setWrapText(true);

        // Background color
        headerStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);

        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        Row headerRow = sheet.createRow(rowNo);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            //logger.debug("header-{} [{}]", i, headers[i]);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    // Method to create custom styled row
    public static void createStyledRow(XSSFWorkbook workbook, XSSFSheet sheet,
                                       int rowIndex, String cellValue, int startCol, int endCol,
                                       short fontSize, String fontName, short fontColor,
                                       short bgColor, boolean isBold, boolean wrapText, short alignment) {

        logger.debug("creating styled row >> rowIndex : [{}]", rowIndex);

        // Create font
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setColor(fontColor);
        font.setFontName(fontName);
        font.setBold(isBold);

        // Create style
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(bgColor);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setWrapText(wrapText);
        style.setAlignment(alignment);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // Create row and cell
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(startCol);
        cell.setCellValue(cellValue);
        cell.setCellStyle(style);

        // Merge columns if needed
        if (endCol > startCol) {
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, startCol, endCol));
        }
    }

}
