package com.ddr.services;

import com.ddr.dao.DispatchRegisterFormDAO;
import com.ddr.dao.DispatchReportDAO;
import com.ddr.model.DispatchRegisterDTO;
import com.ddr.model.DispatchReport;
import com.ddr.model.DispatchReportDTO;
import com.ddr.util.CommonUtil;
import com.ddr.util.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class DispatchRegisterSubmitService {

    private final Logger logger = LoggerUtil.getLogger(DispatchRegisterSubmitService.class);

    private final DispatchReportDAO dispatchReportDAO = new DispatchReportDAO();

    private final DispatchRegisterFormDAO registerFormDAO = new DispatchRegisterFormDAO();

    public Map<String, Object> getDispatchRegisterSubmit(DispatchRegisterDTO registerDTO) {
        Map<String, Object> result = new HashMap<>();

        try {
            // check if user input is valid
            String[] valResult = isAllDataValid(registerDTO);

            if ("false".equals(valResult[0])) {
                result.put("success", false);
                result.put("message", valResult[1]);
                return result;
            }

            // getting DispatchRegister list
            List<DispatchReport> reports = dispatchReportDAO.callDispatchRegisterProcedure(registerDTO);

            if (reports.isEmpty()) {
                logger.debug("Report data not found..");
                result.put("success", false);
                result.put("message", "report data not found");
                return result;
            }

            result.put("success", true);
            result.put("message", "Dispatch data found");

            return result;

        } catch (Exception e) {
            logger.error("Exception occurred in getDispatchReportData :: ", e);
            result.put("success", false);
            result.put("message", "Error: " + e.getMessage());
        }
        return result;
    }

    public Map<String, Object> getDispatchReportData(DispatchRegisterDTO registerDTO) {
        logger.debug("getting Dispatch Report data..");
        Map<String, Object> result = new HashMap<>();

        try {
            // check if user input is valid
            String[] valResult = isAllDataValid(registerDTO);

            if ("false".equals(valResult[0])) {
                result.put("success", false);
                result.put("message", valResult[1]);
                return result;
            }

            logger.info("Division :: [{}]", registerDTO.getDivision());

            // getting DispatchRegister list
            List<DispatchReport> reports = dispatchReportDAO.callDispatchRegisterProcedure(registerDTO);
            int size = reports.size();

            if (reports.isEmpty()) {
                logger.debug("Report data not found");
                result.put("success", false);
                result.put("message", "report data not found");
                return result;
            }

            if ("0".equals(registerDTO.getDivision())) {
                // MAIN division report DTO
                List<DispatchReportDTO> mainDtoReports = reports.stream()
                        .filter(dr -> "MAIN".equals(dr.getDivision()))
                        .map(DispatchReportDTO::new)
                        .toList();
                logger.debug("MAIN division list size >> [{}]", mainDtoReports.size());
                BigDecimal mainDivisionTotal = getTotalGoodsValue(mainDtoReports, DispatchReportDTO::getGoodsValue);

                // ZENKARE division report DTO
                List<DispatchReportDTO> zenDtoReports = reports.stream()
                        .filter(dr -> "ZENKARE".equals(dr.getDivision()))
                        .map(DispatchReportDTO::new)
                        .toList();
                logger.debug("ZENKARE division list size >> [{}]", zenDtoReports.size());
                BigDecimal zenDivisionTotal = getTotalGoodsValue(zenDtoReports, DispatchReportDTO::getGoodsValue);

                if (mainDtoReports.isEmpty() && zenDtoReports.isEmpty()) {
                    logger.debug("Report data not found for MAIN and ZENKARE division");
                    result.put("success", false);
                    result.put("message", "report data not found");
                    return result;
                }

                // create map to store separate reports
                Map<String, List<DispatchReportDTO>> dtoReportForAllDivision = new HashMap<>();
                dtoReportForAllDivision.put("MAIN", mainDtoReports);
                dtoReportForAllDivision.put("ZENKARE", zenDtoReports);

                result.put("main_div_total", mainDivisionTotal);
                result.put("zen_div_total", zenDivisionTotal);
                result.put("data", dtoReportForAllDivision);
                result.put("div_desc", "ALL");

            } else {

                String divisionDesc = dispatchReportDAO.getDivisionNameByDivId(registerDTO.getDivision());

                List<DispatchReportDTO> reportDTOList = reports.stream()
                        .map(DispatchReportDTO::new)
                        .toList();

                logger.debug("dispReg(0) :: {}", reports.get(0));

                result.put("data", reportDTOList);
                result.put("div_desc", divisionDesc);
            }

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

            String currentDateTime = CommonUtil.getCurrentDateTime();

            BigDecimal grandTotal = getTotalGoodsValue(reports, DispatchReport::getGoodsValue);

            result.put("success", true);
            result.put("message", "Dispatch data fetched successfully");
            result.put("total_records", size);
            result.put("loc_name", locationName);
            result.put("comp_name", compName);
            result.put("report_type", reportType);
            result.put("fin_year_range", finYearRange);
            result.put("report_date", reportDate);
            result.put("date_time", currentDateTime);
            result.put("grand_total", grandTotal);
            return result;

        } catch (Exception e) {
            logger.error("Exception occurred in getDispatchReportData :: ", e);
            result.put("success", false);
            result.put("message", "Error: " + e.getMessage());
        }
        return result;
    }

    private <T> BigDecimal getTotalGoodsValue(List<T> list, Function<T, BigDecimal> getter) {
        return list == null ? BigDecimal.ZERO :
                list.stream()
                        .map(getter)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public String[] isAllDataValid(DispatchRegisterDTO registerDTO) {

        String[] valResult = new String[2];

        if (registerDTO == null) {
            valResult[0] = "false";
            valResult[1] = "Registration details required";
            return valResult;
        }

        valResult = validateBranch(registerDTO.getBranch());
        if ("false".equals(valResult[0])) {
            return valResult;
        }

        valResult = validateStockPoint(registerDTO.getStockPoint());
        if ("false".equals(valResult[0])) {
            return valResult;
        }

        valResult = validateDivision(registerDTO.getDivision());
        if ("false".equals(valResult[0])) {
            return valResult;
        }

        valResult = validateReportType(registerDTO.getReportType());
        if ("false".equals(valResult[0])) {
            return valResult;
        }

        valResult = validateStartDtAndEndDt(registerDTO.getFinancialYear(), registerDTO.getStartDate(), registerDTO.getEndDate());
        if ("false".equals(valResult[0])) {
            return valResult;
        }

        return valResult;
    }

    // Validate Branch Id
    public String[] validateBranch(String branchId) {
        logger.debug("validateBranch is called..");
        String[] res = new String[2];
        res[0] = "true";
        res[1] = "";
        // Regex to match only numbers
        String regex = "^[0-9]+$";
        if (branchId == null || branchId.trim().isEmpty()) {
            logger.debug("Branch is required");
            res[0] = "false";
            res[1] = "Branch is required";
            return res;
        } else if (!branchId.matches(regex)) {
            logger.debug("Invalid branch Id");
            res[0] = "false";
            res[1] = "Invalid branch";
            return res;
        }

        return res;
    }

    // Validate StockPoint Id
    public String[] validateStockPoint(String stockPointId) {
        logger.debug("validateStockPoint is called..");

        String[] res = new String[2];
        res[0] = "true";
        res[1] = "";

        // Regex to match only numbers
        String regex = "^[0-9]+$";
        if (stockPointId == null || stockPointId.trim().isEmpty()) {
            logger.debug("Last name is missing");
            res[0] = "false";
            res[1] = "Last name is missing";
            return res;
        } else if (!stockPointId.matches(regex)) {
            logger.debug("Invalid stock point id");
            res[0] = "false";
            res[1] = "Invalid stock point";
            return res;
        }
        return res;
    }

    // Validate Division Id
    public String[] validateDivision(String divisionId) {
        logger.debug("validateDivision is called..");
        String[] res = new String[2];
        res[0] = "true";
        res[1] = "";

        // Regex for valid Division id
        String regex = "^[0-9]+$";
        if (divisionId == null || divisionId.trim().isEmpty()) {
            logger.debug("Division id required");
            res[0] = "false";
            res[1] = "Division required";
            return res;
        } else if (!divisionId.matches(regex)) {
            logger.debug("Invalid division id");
            res[0] = "false";
            res[1] = "Invalid division";
            return res;
        }
        return res;
    }

    // Validate Report type (Detail/Summary)
    public String[] validateReportType(String reportType) {
        logger.debug("validateReportType is called..");
        String[] res = new String[2];
        res[0] = "true";
        res[1] = "";

        if (reportType == null || reportType.trim().isEmpty()) {
            logger.debug("Report type required");
            res[0] = "false";
            res[1] = "Report type required";
            return res;
        } else if (!("N".equalsIgnoreCase(reportType) || "Y".equalsIgnoreCase(reportType))) {
            logger.debug("Invalid report type");
            res[0] = "false";
            res[1] = "Invalid report type";
            return res;
        }
        return res;
    }

    // Validate financial year
    public String[] validateFinancialYear(String finYearId) {
        logger.debug("validateFinancialYear is called..");
        String[] res = new String[2];
        res[0] = "true";
        res[1] = "";

        // Regex for valid finYear id
        String regex = "^[0-9]+$";
        if (finYearId == null || finYearId.trim().isEmpty()) {
            logger.debug("Financial year required");
            res[0] = "false";
            res[1] = "Financial year required";
            return res;
        } else if (!finYearId.matches(regex)) {
            logger.debug("Invalid financial year id");
            res[0] = "false";
            res[1] = "Invalid financial year";
            return res;
        }
        return res;
    }

    // Validate Start date and End date
    public String[] validateStartDtAndEndDt(String finYearId, String startDtStr, String endDtStr) {
        logger.debug("validateStartDtAndEndDt is called..");

        String[] res = new String[2];
        res[0] = "true";
        res[1] = "";

        // validate financial year id
        res = validateFinancialYear(finYearId);
        if ("false".equals(res[0])) {
            return res;
        }

        // validate start date
        if (startDtStr == null || startDtStr.trim().isEmpty()) {
            logger.debug("Start date required");
            res[0] = "false";
            res[1] = "Start date required";
            return res;
        }

        // validate start date
        if (endDtStr == null || endDtStr.trim().isEmpty()) {
            logger.debug("End date required");
            res[0] = "false";
            res[1] = "End date required";
            return res;
        }

        try {
            // get start date and end date for the current financial year
            Map<String, Object> map = registerFormDAO.getFinancialYearDatesByFinYearId(Integer.parseInt(finYearId));

            String startDtFromDb = (String) map.get("start_dt");
            String endDtFromDb = (String) map.get("end_dt");

            LocalDate dbStartDt = CommonUtil.parseDate(startDtFromDb);
            LocalDate dbEndDt = CommonUtil.parseDate(endDtFromDb);

            LocalDate startDt = CommonUtil.parseDate(startDtStr);
            LocalDate endDt = CommonUtil.parseDate(endDtStr);

            if (startDt == null || endDt == null || dbStartDt == null || dbEndDt == null) {
                logger.debug("Entered range extends beyond the financial year.");
                res[0] = "false";
                res[1] = "Entered range extends beyond the financial year.";
                return res;
            }

            // check if start date and end date comes under financial year range
            if (startDt.isBefore(dbStartDt) && endDt.isAfter(dbEndDt)) {
                logger.debug("Entered range extends beyond the financial year.");
                res[0] = "false";
                res[1] = "Entered range extends beyond the financial year.";
                return res;
            }

        } catch (Exception e) {
            logger.error("Exception occurred in validateStartDtAndEndDt >> ", e);
            res[0] = "false";
            res[1] = "Exception occurred, please try again";
        }

        return res;
    }


}
