package com.ddr.dao;

import com.ddr.model.DispatchRegisterDTO;
import com.ddr.model.DispatchReport;
import com.ddr.util.CommonUtil;
import com.ddr.util.DBConnectionManager;
import com.ddr.util.LoggerUtil;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatchReportDAO {

    private final Logger logger = LoggerUtil.getLogger(DispatchReportDAO.class);

    public List<DispatchReport> callDispatchRegisterProcedure(DispatchRegisterDTO dto) {

        List<DispatchReport> list = new ArrayList<>();

        String query = "{call DISPATCH_REGISTER(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = DBConnectionManager.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            // Register OUT parameter (1st parameter)
            stmt.registerOutParameter(1, OracleTypes.CURSOR);

            // Set IN parameters (2nd to 11th)
            /*stmt.setString(2, "CURRENT"); // FIN_YR_FLAG -- CURRENT/PREVIOUS
            stmt.setInt(3, 17);   // FINYR_ID
            stmt.setString(4, "SNK"); // COMP_CD
            stmt.setString(5, "10");  // LOCID
            stmt.setString(6, "2023-04-01"); // STDT
            stmt.setString(7, "2024-03-31"); // ENDT
            stmt.setString(8, "0"); // DIVID
            stmt.setString(9, "0"); // CUSTID
            stmt.setString(10, "N"); // DETAILED
            stmt.setString(11, "10"); // STOCKPOINTID*/

            String finYearFlag = dto.getStartDate();

            stmt.setString(2, "CURRENT"); // FIN_YR_FLAG -- CURRENT/PREVIOUS
            stmt.setInt(3, Integer.parseInt(dto.getFinancialYear()));   // FINYR_ID
            stmt.setString(4, "SNK"); // COMP_CD
            stmt.setString(5, dto.getBranch());  // LOCID
            stmt.setString(6, dto.getStartDate()); // STDT
            stmt.setString(7, dto.getEndDate()); // ENDT
            stmt.setString(8, dto.getDivision()); // DIVID
            stmt.setString(9, dto.getCustomer()); // CUSTID
            stmt.setString(10, dto.getReportType()); // DETAILED
            stmt.setString(11, dto.getStockPoint()); // STOCKPOINTID

            // Execute the call
            stmt.execute();

            logger.debug("Query : [{}], Param : [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}]", query,
                    OracleTypes.CURSOR, "CURRENT", dto.getFinancialYear(), "SNK", dto.getBranch(), dto.getStartDate(),
                    dto.getEndDate(), dto.getDivision(), dto.getCustomer(), dto.getReportType(), dto.getStockPoint());

            // Get the cursor (result set)
            try (ResultSet rs = (ResultSet) stmt.getObject(1);) {
                // Process the result set
                while (rs.next()) {
                    DispatchReport dispReg = new DispatchReport();

                    dispReg.setDspTrnNo(rs.getString("DSP_TRN_NO"));
                    dispReg.setDspDt(CommonUtil.parseDate(rs.getString("DSP_DT")));
                    dispReg.setCustName(rs.getString("CUST_NAME"));
                    dispReg.setDestination(rs.getString("DESTINATION"));
                    dispReg.setTransporter(rs.getString("TRANSPORTER"));
                    dispReg.setGoodsValue(rs.getBigDecimal("GOODS_VALUE"));
                    dispReg.setInvNo(rs.getString("INV_NO"));
                    dispReg.setLrNum(rs.getString("LR_NUM"));
                    dispReg.setDriverName(rs.getString("DRIVER_NAME"));
                    dispReg.setLorryNo(rs.getString("LORRY_NO"));
                    dispReg.setDelayDays(rs.getInt("DELAY_DAYS"));
                    dispReg.setNoOfCases(rs.getInt("NO_OF_CASES"));
                    dispReg.setFormNum(rs.getString("FORM_NUM"));
                    dispReg.setcFormValue(rs.getInt("CFORM_VALUE"));
                    //dispReg.setPodNum(rs.getInt("POD_NUM"));
                    dispReg.setPodReason(rs.getString("POD_REASON"));

                    dispReg.setLrDate(CommonUtil.parseDate(rs.getString("LR_DATE")));
                    dispReg.setcFormDate(CommonUtil.parseDate(rs.getString("CFORM_DATE")));
                    dispReg.setPodDate(CommonUtil.parseDate(rs.getString("POD_DATE")));

                    list.add(dispReg);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception occurred :: >> ", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    public String getLocationNameByLocId(int locId) {
        String sql = "SELECT LOC_NAME FROM DEPOT_MASTER WHERE LOC_ID = ?";
        int parameterCount = 0;
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locId);

            logger.debug("Query : [{}], Param : [{}]", sql, locId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("LOC_NAME");
                }
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Location Name  >> ", e);
            throw new RuntimeException(e);
        }
        return "";
    }

    public String getCustomerNameByCustId(String custId) {
        String sql = "SELECT CUST_NAME FROM CUSTOMER_MASTER WHERE CUST_ID = ?";
        int parameterCount = 0;
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, custId);

            logger.debug("Query : [{}], Param : [{}]", sql, custId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("CUST_NAME");
                }
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Customer Name >> ", e);
            throw new RuntimeException(e);
        }
        return "";
    }

    public String getDivisionNameByDivId(String divId) {
        String sql = "SELECT PARA_DESCR FROM PARAMETERS WHERE PARA_ID = ?";
        int parameterCount = 0;
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, divId);

            logger.debug("Query : [{}], Param : [{}]", sql, divId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("PARA_DESCR");
                }
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Division >> ", e);
            throw new RuntimeException(e);
        }
        return "";
    }

    public String getFinancialYearByFinYearId(int finYearId) {
        String finYear = "";
        String sql = "SELECT FIN_YEAR_ID, START_DT, END_DT FROM FINANCIAL_YEAR WHERE FIN_YEAR_ID = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, finYearId);

            logger.debug("Query : [{}], Param : [{}]", sql, finYearId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String startDt = CommonUtil.convertToString(rs.getDate("START_DT"));
                    String endDt = CommonUtil.convertToString(rs.getDate("END_DT"));

                    finYear = startDt + " To " + endDt;

                }
                logger.debug("Financial Year dates :: finYear >> {}", finYear);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while getting Financial Year >> ", e);
            throw new RuntimeException(e);
        }
        return finYear;
    }
}
