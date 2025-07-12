package com.ddr.services;

import com.ddr.util.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class DispatchReportService {

    private final Logger logger = LoggerUtil.getLogger(DispatchReportService.class);

    /*public void createDispatchRegSumReportFile(String[] args) {
        DispatchReportDAO dao = new DispatchReportDAO();

        Map<String, Object> result = new HashMap<>();
        try {
            // getting stockPointData
            List<DispatchRegister> dispRegList = dao.callDispatchRegisterProcedure();
            logger.debug("dispRegList size >> [{}]", dispRegList.size());

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
            logger.error("Exception occurred in getStockPointList >> [{}]", e.getMessage());
            e.printStackTrace();
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }*/
}
