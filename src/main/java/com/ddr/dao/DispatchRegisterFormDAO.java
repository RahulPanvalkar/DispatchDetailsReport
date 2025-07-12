package com.ddr.dao;

import com.ddr.util.CommonUtil;
import com.ddr.util.DBConnectionManager;
import com.ddr.util.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatchRegisterFormDAO {

    private final Logger logger = LoggerUtil.getLogger(DispatchRegisterFormDAO.class);

    // Location data for Branch List
    public List<Map<String, Object>> getLocationData() {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = "SELECT LOC_ID, ERP_LOC_NAME, LOC_NAME FROM DEPOT_MASTER ORDER BY LOC_NAME";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            logger.debug("Query : [{}]", sql);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int locId = rs.getInt("LOC_ID");
                    String locName = checkNull(rs.getString("ERP_LOC_NAME"));
                    Map<String, Object> map = new HashMap<>();
                    map.put("loc_id", locId);
                    map.put("loc_name", locName);
                    result.add(map);
                }
                logger.debug("location data :: result >> {}", result);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Location Data >> ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    // Data for Stock Point List
    public List<Map<String, Object>> getStockPointData() {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = "SELECT STOCK_POINT_ID,LOC_NAME FROM STOCKPOINT_MST";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            logger.debug("Query :: [{}]", sql);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int stockPointId = rs.getInt("STOCK_POINT_ID");
                    String locName = checkNull(rs.getString("LOC_NAME"));
                    Map<String, Object> map = new HashMap<>();
                    map.put("stock_point_id", stockPointId);
                    map.put("loc_name", locName);
                    result.add(map);
                }
                logger.debug("stock point data :: result >> {}", result);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Stock Point Data >> ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    // Method to get Stock Point based on location id
    public Map<String, Object> getStockPointByLocId(int locId) {
        Map<String, Object> map = new HashMap<>();
        String sql = "SELECT STOCK_POINT_ID, LOC_NAME FROM STOCKPOINT_MST WHERE LOC_ID = ?";
        int parameterCount = 0;
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locId);

            logger.debug("Query : [{}], Param : [{}]", sql, locId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int stockPointId = rs.getInt("STOCK_POINT_ID");
                    String locName = checkNull(rs.getString("LOC_NAME"));

                    map.put("stock_point_id", stockPointId);
                    map.put("loc_name", locName);
                }
                logger.debug("stock point :: map >> {}", map);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Stock Point >> ", e);
            throw new RuntimeException(e);
        }
        return map;
    }

    // Data for Stock Point List
    public List<Map<String, Object>> getDivisionData() {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = "SELECT PARA_ID, PARA_DESCR FROM PARAMETERS WHERE PARA_TYPE ='DIV'";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            logger.debug("Query :: [{}]", sql);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int paraId = rs.getInt("PARA_ID");
                    String paraDescr = checkNull(rs.getString("PARA_DESCR"));
                    Map<String, Object> map = new HashMap<>();
                    map.put("para_id", paraId);
                    map.put("para_descr", paraDescr);
                    result.add(map);
                }
                logger.debug("division data :: result >> {}", result);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Division Data >> ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    // Data for Customer List
    public List<Map<String, Object>> getCustomerDataByLocId(int locId) {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = "SELECT DISTINCT CM.CUST_ID, CM.CUST_NAME, CM.DESTINATION,CM.CUST_CD, CM.ERP_CUST_CD " +
                "FROM CUSTOMER_MASTER CM,CUSTOMER_ALLOCATION CA " +
                "WHERE CM.CUST_ID=CA.CUST_ID AND CM.LOC_ID = ? " +
                "AND (CM.DELETED ='N' OR CM.DELETED IS NULL) ORDER BY CM.CUST_NAME";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locId);

            logger.debug("Query : [{}], Param : [{}]", sql, locId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int custId = rs.getInt("CUST_ID");
                    String custName = checkNull(rs.getString("CUST_NAME"));
                    Map<String, Object> map = new HashMap<>();
                    map.put("cust_id", custId);
                    map.put("cust_name", custName);
                    result.add(map);
                }
                logger.debug("customer data :: result >> {}", result);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Customer Data >> ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    // Financial Year data for Financial Year List
    public List<Map<String, Object>> getFinancialYearData() {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = "SELECT FIN_YEAR_ID, START_DT, END_DT FROM FINANCIAL_YEAR ORDER BY FIN_YEAR_ID DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            logger.debug("Query : [{}]", sql);
            // Create SimpleDateFormat with desired format
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int finYearId = rs.getInt("FIN_YEAR_ID");
                    String startDt = CommonUtil.convertToString(rs.getDate("START_DT"));
                    String endDt = CommonUtil.convertToString(rs.getDate("END_DT").toLocalDate());
                    Map<String, Object> map = new HashMap<>();
                    map.put("fin_year_id", finYearId);
                    map.put("start_dt", startDt);
                    map.put("end_dt", endDt);
                    result.add(map);
                }
                logger.debug("Financial Year data :: result >> {}", result);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Financial Year Data >> ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    // method to check if string is null or empty
    private String checkNull(String str) {
        return (str == null || str.trim().isEmpty()) ? "" : str.trim();
    }

    public Map<String, Object> getFinancialYearDatesByFinYearId(int finYearId) {
        Map<String, Object> map = new HashMap<>();

        String sql = "SELECT FIN_YEAR_ID, START_DT, END_DT FROM FINANCIAL_YEAR WHERE FIN_YEAR_ID = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, finYearId);

            logger.debug("Query : [{}], Param : [{}]", sql, finYearId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String startDt = CommonUtil.convertToString(rs.getDate("START_DT"));
                    String endDt = CommonUtil.convertToString(rs.getDate("END_DT").toLocalDate());

                    map.put("fin_year_id", finYearId);
                    map.put("start_dt", startDt);
                    map.put("end_dt", endDt);
                }
                logger.debug("Financial Year dates :: map >> {}", map);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Financial Year Dates >> ", e);
            throw new RuntimeException(e);
        }
        return map;
    }
}
