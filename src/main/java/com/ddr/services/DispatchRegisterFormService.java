package com.ddr.services;

import com.ddr.dao.DispatchRegisterFormDAO;
import com.ddr.util.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatchRegisterFormService {

    private final Logger logger = LoggerUtil.getLogger(DispatchRegisterFormService.class);

    private static DispatchRegisterFormDAO dao = new DispatchRegisterFormDAO();


    public Map<String, Object> getDefaultDataList() {
        logger.info("getting default data list..");
        Map<String, Object> result = new HashMap<>();
        try {
            // getting locationData
            List<Map<String, Object>> locationData = dao.getLocationData();

            // getting stockPointData
            List<Map<String, Object>> stockPointData = dao.getStockPointData();

            // getting divisionData
            List<Map<String, Object>> divisionData = dao.getDivisionData();

            // getting financialYearData
            List<Map<String, Object>> financialYearData = dao.getFinancialYearData();

            Map<String, Object> data = new HashMap<>();
            data.put("branches", locationData);
            data.put("stock_points", stockPointData);
            data.put("divisions", divisionData);
            data.put("fin_years", financialYearData);

            result.put("success", true);
            result.put("message", "Request successful");
            result.put("data", data);
            return result;

        } catch (RuntimeException e) {
            logger.error("Exception occurred in getDefaultValues >> ", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

    // Method to get Location Data from db
    public Map<String, Object> getBranchList() {
        Map<String, Object> result = new HashMap<>();
        try {
            // getting locationData
            List<Map<String, Object>> locationData = dao.getLocationData();

            if (locationData == null || locationData.isEmpty()) {
                result.put("success", false);
                result.put("message", "Branch list not found");
                return result;
            }
            result.put("success", true);
            result.put("message", "Request successful");
            result.put("data", locationData);
            return result;

        } catch (RuntimeException e) {
            logger.error("Exception occurred in getBranchList >> ", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

    // Method to get Stock Point Data from db
    public Map<String, Object> getStockPointList() {
        Map<String, Object> result = new HashMap<>();
        try {
            // getting stockPointData
            List<Map<String, Object>> stockPointData = dao.getStockPointData();

            if (stockPointData == null || stockPointData.isEmpty()) {
                result.put("success", false);
                result.put("message", "Stock Point list not found");
                return result;
            }
            result.put("success", true);
            result.put("message", "Request successful");
            result.put("data", stockPointData);
            return result;

        } catch (RuntimeException e) {
            logger.error("Exception occurred in getStockPointList >> ", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

    // Method to get Stock Point Data from db
    public Map<String, Object> getStockPointByLocId(int locId) {
        Map<String, Object> result = new HashMap<>();
        try {
            logger.debug("locId :: [{}]", locId);
            if (locId <= 0) {
                result.put("success", false);
                result.put("message", "Invalid location id");
                return result;
            }

            // getting stockPoint
            Map<String, Object> stockPoint = dao.getStockPointByLocId(locId);

            if (stockPoint == null || stockPoint.isEmpty()) {
                result.put("success", false);
                result.put("message", "Stock Point not found");
                return result;
            }
            result.put("success", true);
            result.put("message", "Request successful");
            result.put("data", stockPoint);
            return result;

        } catch (RuntimeException e) {
            logger.error("Exception occurred in getStockPointByLocId >> ", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

    // Method to get Division Data from db
    public Map<String, Object> getDivisionList() {
        Map<String, Object> result = new HashMap<>();
        try {
            // getting divisionData
            List<Map<String, Object>> divisionData = dao.getDivisionData();

            if (divisionData == null || divisionData.isEmpty()) {
                result.put("success", false);
                result.put("message", "Division list not found");
                return result;
            }
            result.put("success", true);
            result.put("message", "Request successful");
            result.put("data", divisionData);
            return result;

        } catch (RuntimeException e) {
            logger.error("Exception occurred in getDivisionList >> ", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

    // Method to get Customer Data from db based on location
    public Map<String, Object> getCustomerListByLocId(int locId) {
        Map<String, Object> result = new HashMap<>();
        try {
            logger.debug("locId ::: [{}]", locId);
            if (locId < 0) {
                result.put("success", false);
                result.put("message", "Invalid location id");
                return result;
            }
            // getting customerData
            List<Map<String, Object>> customerData = dao.getCustomerDataByLocId(locId);

            if (customerData == null || customerData.isEmpty()) {
                result.put("success", false);
                result.put("message", "Customer list not found");
                return result;
            }
            result.put("success", true);
            result.put("message", "Request successful");
            result.put("data", customerData);
            result.put("cust_count", customerData.size());
            return result;

        } catch (RuntimeException e) {
            logger.error("Exception occurred in getCustomerListByLocId >> ", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

    // Method to get Financial year Data from db
    public Map<String, Object> getFinancialYearList() {
        Map<String, Object> result = new HashMap<>();
        try {
            // getting financialYearData
            List<Map<String, Object>> financialYearData = dao.getFinancialYearData();

            if (financialYearData == null || financialYearData.isEmpty()) {
                result.put("success", false);
                result.put("message", "Financial year list not found");
                return result;
            }
            result.put("success", true);
            result.put("message", "Request successful");
            result.put("data", financialYearData);
            return result;

        } catch (RuntimeException e) {
            logger.error("Exception occurred in getFinancialYearList >> ", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

    public Map<String, Object> getFinancialYearDates(int finYearId) {
        Map<String, Object> result = new HashMap<>();
        try {
            logger.debug("finYearId :: [{}]", finYearId);
            if (finYearId <= 0) {
                result.put("success", false);
                result.put("message", "Invalid financial year id");
                return result;
            }
            // getting financial year details
            Map<String, Object> financialYearDates = dao.getFinancialYearDatesByFinYearId(finYearId);

            if (financialYearDates == null || financialYearDates.isEmpty()) {
                result.put("success", false);
                result.put("message", "Financial year not found");
                return result;
            }
            result.put("success", true);
            result.put("message", "Request successful");
            result.put("data", financialYearDates);
            return result;

        } catch (RuntimeException e) {
            logger.error("Exception occurred in getFinancialYearDates >> ", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

}
