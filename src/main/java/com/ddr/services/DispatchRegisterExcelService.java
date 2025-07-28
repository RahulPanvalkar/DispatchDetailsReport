package com.ddr.services;

import com.ddr.dao.DispatchReportDAO;
import com.ddr.model.DispatchRegisterDTO;
import com.ddr.model.DispatchReport;
import com.ddr.util.CommonUtil;
import com.ddr.util.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class DispatchRegisterExcelService {

    private final DispatchReportDAO dispatchReportDAO = new DispatchReportDAO();

    private static final Logger logger = LoggerUtil.getLogger(DispatchRegisterExcelService.class);

    private int currentRow = 0;

    private final String[] headers = {"TRANSACTION NO", "DISPATCH DATE", "PARTY", "DESTINATION", "TRANSPORTER", "GOODS VALUE",
            "INVOICE NO", "LR NO", "DRIVER NAME", "LORRY NUMBER", "LR DATE", "DISPATCH DELAY", "NO OF CASES",
            "FORM NUM", "C FORM DATE", "C FORM VALUE", "POD DATE", "POD NUMBER", "REASON"};

    /*public static void main(String[] args) {
        //DownloadService service = new DownloadService();
        String filePath = "D:\\RAHUL\\TASK\\Dispatch details report summary\\DispatchDetailsReport\\module-resource\\Dispatch-Register-Summary.xlsx";
        DispatchRegisterDTO dto = new DispatchRegisterDTO();
        dto.setBranch("10");
        dto.setStockPoint("10");
        dto.setDivision("327");
        dto.setStartDate("01/04/2023");
        dto.setEndDate("31/03/2024");
        dto.setCustomer("0");
        dto.setReportType("Y");
        dto.setFinancialYear("17");

        DispatchRegisterExcelService service = new DispatchRegisterExcelService();
        service.updateFileData(dto);
    }*/

    public String updateFileData(DispatchRegisterDTO registerDTO) {
        logger.debug("updating excel file..");
        String filePath = "";
        if (registerDTO == null) {
            logger.debug("registerDTO is null");
            return filePath;
        }
        try {
            // getting DispatchRegister list
            List<DispatchReport> dispRegList = dispatchReportDAO.callDispatchRegisterProcedure(registerDTO);

            // get Location Name
            String branch = registerDTO.getBranch();
            String locationName = "";
            if ("0".equals(branch)) {
                locationName = "HEALTHCARE LTD-ALL";
            } else {
                locationName = dispatchReportDAO.getLocationNameByLocId(Integer.parseInt(branch));
            }

            // Company name
            String compName = "HEALTHCARE PVT LTD.";    // for company_id=SNK

            String reportType = ("Y".equalsIgnoreCase(registerDTO.getReportType())) ? "Detail" : "Summary";

            String finYearRange = dispatchReportDAO.getFinancialYearByFinYearId(Integer.parseInt(registerDTO.getFinancialYear()));

            String reportDate = registerDTO.getStartDate() + " To " + registerDTO.getEndDate();

            String divisionDesc = dispatchReportDAO.getDivisionNameByDivId(registerDTO.getDivision());

            String currentDateTime = CommonUtil.getCurrentDateTime();

            int size = dispRegList.size();

            // Excel file path
            filePath = "D:\\RAHUL\\TASK\\Dispatch details report summary\\DispatchDetailsReport\\module-resource\\Dispatch-Register-Report.xlsx";

            // add data in file and return inputStream for downloading
            //InputStream inputStream =
            addDispatchDataInFile(filePath, dispRegList, compName, locationName, finYearRange, divisionDesc, reportType);

            // after successful update return file path for downloading
            return filePath;
        } catch (Exception e) {
            logger.error("Exception occurred in updateFileData :: ", e);
        }
        return filePath;
    }

    // Method to create Excel file for Dispatch Register Report Summary
    private void addDispatchDataInFile(String filePath, List<DispatchReport> dispRegList, String compName, String locationName, String finYearRange, String division, String reportType) {
        try {

            logger.debug("dispRegList size >> [{}]", dispRegList.size());
            if (!dispRegList.isEmpty()) logger.debug(dispRegList.get(0));

            logger.debug("creating workbook..");
            XSSFWorkbook workbook = new XSSFWorkbook();
            logger.debug("creating sheet..");
            String sheetName = "Dispatch Register " + reportType + " Report";
            XSSFSheet xssfSheet = workbook.createSheet(sheetName);

            // Create header in row 1
            String[] headers = {"TRANSACTION NO", "DISPATCH DATE", "PARTY", "DESTINATION", "TRANSPORTER", "GOODS VALUE",
                    "INVOICE NO", "LR NO", "DRIVER NAME", "LORRY NUMBER", "LR DATE", "DISPATCH DELAY", "NO OF CASES",
                    "FORM NUM", "C FORM DATE", "C FORM VALUE", "POD DATE", "POD NUMBER", "REASON"};

            int headerLength = headers.length;

            // Create title in row 0 , 1, 2
            if (compName == null || locationName == null) {
                compName = "";
                locationName = "";
            }

            String title = "Dispatch Register " + reportType +" Report From : " + finYearRange;
            createTitleRow(workbook, xssfSheet, compName.toUpperCase(), headerLength, currentRow);  // rowNo = 0
            createTitleRow(workbook, xssfSheet, locationName.toUpperCase(), headerLength, ++currentRow); // rowNo = 1
            createTitleRow(workbook, xssfSheet, title, headerLength, ++currentRow); // rowNo = 2

            // Create Header Row
            createHeaderRow(workbook, xssfSheet, headers, ++currentRow); // rowNo = 3

            // Create custom styled row to show Division
            String divisionDesc = "Division : " + division;
            createStyledRow(workbook, xssfSheet, ++currentRow, divisionDesc, 0, headerLength - 1,
                    (short) 9, "Arial", IndexedColors.BLACK.getIndex(), IndexedColors.LIGHT_CORNFLOWER_BLUE.index,
                    true, false, CellStyle.ALIGN_LEFT);

            xssfSheet.createFreezePane(0, 5);

            // font style for body
            Font cellFont = (XSSFFont) workbook.createFont();
            cellFont.setFontName("Arial");
            cellFont.setFontHeightInPoints((short) 9);

            // date cell style
            CellStyle dateStyle = workbook.createCellStyle();
            short dateFormat = workbook.createDataFormat().getFormat("dd/MM/yyyy");
            dateStyle.setDataFormat(dateFormat);
            dateStyle.setFont(cellFont);
            dateStyle.setAlignment(CellStyle.ALIGN_LEFT);

            dateStyle.setBorderTop(CellStyle.BORDER_THIN);
            dateStyle.setBorderLeft(CellStyle.BORDER_THIN);
            dateStyle.setBorderRight(CellStyle.BORDER_THIN);
            dateStyle.setBorderBottom(CellStyle.BORDER_THIN);

            // Data cell style
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setFont(cellFont);
            dataStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            dataStyle.setBorderTop(CellStyle.BORDER_THIN);
            dataStyle.setBorderLeft(CellStyle.BORDER_THIN);
            dataStyle.setBorderRight(CellStyle.BORDER_THIN);
            dataStyle.setBorderBottom(CellStyle.BORDER_THIN);

            BigDecimal goodsValueTotal = BigDecimal.ZERO;

            // body from row = 4;
            for (DispatchReport dispReg : dispRegList) {
                ++currentRow;

                Row row = xssfSheet.createRow(currentRow);

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
                if (dispReg.getGoodsValue() != null) {
                    cell5.setCellValue(dispReg.getGoodsValue().doubleValue());
                    // to get total value of goods
                    goodsValueTotal = goodsValueTotal.add(dispReg.getGoodsValue());
                }
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

            // get the total value of goods
            //BigDecimal goodsValueTotal = getGoodsValueTotal(dispRegList);

            // Create custom styled row to show Division
            String divisionTotal = "Division Total : ";
            createStyledRowWithCols(workbook, xssfSheet, ++currentRow, divisionTotal, goodsValueTotal, 0, headerLength - 1,
                    (short) 9, "Arial", IndexedColors.BLACK.getIndex(), IndexedColors.LIGHT_CORNFLOWER_BLUE.index,
                    false, false, CellStyle.ALIGN_LEFT);

            // Create custom styled row to show Division
            String grandTotal = "Grand Total : ";
            createStyledRowWithCols(workbook, xssfSheet, ++currentRow, grandTotal, goodsValueTotal, 0, headerLength - 1,
                    (short) 9, "Arial", IndexedColors.BLACK.getIndex(), IndexedColors.LIGHT_CORNFLOWER_BLUE.index,
                    false, false, CellStyle.ALIGN_LEFT);


            // to set column size to auto
            for (int i = 0; i < headers.length; i++) {
                xssfSheet.autoSizeColumn(i);
            }

            // Write to file
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);

//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            workbook.write(bos);
//
//            // Save to disk
//            try (FileOutputStream foss = new FileOutputStream(new File(filePath))) {
//                bos.writeTo(foss);
//            }
//
//            // Return for download
//            return new ByteArrayInputStream(bos.toByteArray());

        } catch (Exception e) {
            logger.error("Exception occurred in addDispatchDataInFile :: ", e);
        }
        //return null;
    }

    private void addDispatchDataInFileForAllDivision(XSSFWorkbook workbook, XSSFSheet xssfSheet, DispatchRegisterDTO registerDTO, String locationName, String finYearRange, String division) {
        try {

            // getting DispatchRegister list
            List<DispatchReport> dispRegList = dispatchReportDAO.callDispatchRegisterProcedure(registerDTO);


            int headerLength = headers.length;

            // Create custom styled row to show Division
            createStyledRow(workbook, xssfSheet, ++currentRow, division, 0, headerLength - 1,
                    (short) 9, "Arial", IndexedColors.BLACK.getIndex(), IndexedColors.LIGHT_CORNFLOWER_BLUE.index,
                    true, false, CellStyle.ALIGN_LEFT);

            xssfSheet.createFreezePane(0, 5);

            // font style for body
            Font cellFont = (XSSFFont) workbook.createFont();
            cellFont.setFontName("Arial");
            cellFont.setFontHeightInPoints((short) 9);

            // date cell style
            CellStyle dateStyle = workbook.createCellStyle();
            short dateFormat = workbook.createDataFormat().getFormat("dd/MM/yyyy");
            dateStyle.setDataFormat(dateFormat);
            dateStyle.setFont(cellFont);
            dateStyle.setAlignment(CellStyle.ALIGN_LEFT);

            dateStyle.setBorderTop(CellStyle.BORDER_THIN);
            dateStyle.setBorderLeft(CellStyle.BORDER_THIN);
            dateStyle.setBorderRight(CellStyle.BORDER_THIN);
            dateStyle.setBorderBottom(CellStyle.BORDER_THIN);

            // Data cell style
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setFont(cellFont);
            dataStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            dataStyle.setBorderTop(CellStyle.BORDER_THIN);
            dataStyle.setBorderLeft(CellStyle.BORDER_THIN);
            dataStyle.setBorderRight(CellStyle.BORDER_THIN);
            dataStyle.setBorderBottom(CellStyle.BORDER_THIN);

            BigDecimal goodsValueTotal = BigDecimal.ZERO;

            // body from row = 4;
            for (DispatchReport dispReg : dispRegList) {
                ++currentRow;

                Row row = xssfSheet.createRow(currentRow);

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
                if (dispReg.getGoodsValue() != null) {
                    cell5.setCellValue(dispReg.getGoodsValue().doubleValue());
                    // to get total value of goods
                    goodsValueTotal = goodsValueTotal.add(dispReg.getGoodsValue());
                }
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


            // Create custom styled row to show Division
            String divisionTotal = "Division Total : ";
            createStyledRowWithCols(workbook, xssfSheet, ++currentRow, divisionTotal, goodsValueTotal, 0, headerLength - 1,
                    (short) 9, "Arial", IndexedColors.BLACK.getIndex(), IndexedColors.LIGHT_CORNFLOWER_BLUE.index,
                    false, false, CellStyle.ALIGN_LEFT);

            // Create custom styled row to show Division
            String grandTotal = "Grand Total : ";
            createStyledRowWithCols(workbook, xssfSheet, ++currentRow, grandTotal, goodsValueTotal, 0, headerLength - 1,
                    (short) 9, "Arial", IndexedColors.BLACK.getIndex(), IndexedColors.LIGHT_CORNFLOWER_BLUE.index,
                    false, false, CellStyle.ALIGN_LEFT);


            // to set column size to auto
            for (int i = 0; i < headers.length; i++) {
                xssfSheet.autoSizeColumn(i);
            }


        } catch (Exception e) {
            logger.error("Exception occurred in addDispatchDataInFile :: ", e);
        }
        //return null;
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

        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);

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

    // Method to create custom styled row
    public static void createStyledRowWithCols(XSSFWorkbook workbook, XSSFSheet sheet,
                                               int rowIndex, String cellName, BigDecimal cellValue, int startCol, int endCol,
                                               short fontSize, String fontName, short fontColor,
                                               short bgColor, boolean isBold, boolean wrapText, short alignment) {

        logger.debug("creating styled row with cols >> rowIndex : [{}]", rowIndex);

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


        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);

        logger.debug("cellName [{}], cellValue : [{}]", cellName, cellValue);
        // Create row and cell
        Row row = sheet.createRow(rowIndex);
        Cell cell1 = row.createCell(startCol);
        cell1.setCellValue(cellName);
        cell1.setCellStyle(style);

        // Merge first 5 columns
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, startCol, startCol + 4));

        // Create the 6th column as a single cell
        Cell cell6 = row.createCell(startCol + 5);
        CellStyle newStyle = workbook.createCellStyle();
        // create new style to set alignment for this cell only
        newStyle.cloneStyleFrom(style);
        newStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        cell6.setCellValue(cellValue.doubleValue());
        cell6.setCellStyle(newStyle);

        // Merge the remaining columns after the 4th column
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, startCol + 6, endCol));  // Merges remaining columns

        // Apply styles to the merged cells
        for (int colIndex = startCol; colIndex <= endCol; colIndex++) {
            if (colIndex != startCol && colIndex != startCol + 5) {  // Skip cell1 and cell6
                Cell cell = row.createCell(colIndex);
                cell.setCellStyle(style);
            }
        }

    }

    private static BigDecimal getGoodsValueTotal(List<DispatchReport> dispRepDataList) {
        logger.debug("Getting total goods value...");

        if (dispRepDataList == null || dispRepDataList.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (DispatchReport dispRepData : dispRepDataList) {
            BigDecimal goodsValue = dispRepData.getGoodsValue();
            if (goodsValue != null) {
                total = total.add(goodsValue);
            }
        }

        logger.debug("Total goods value: [{}]", total);
        return total;
    }


}
