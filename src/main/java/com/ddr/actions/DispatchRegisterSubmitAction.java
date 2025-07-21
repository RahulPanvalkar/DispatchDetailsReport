package com.ddr.actions;

import com.ddr.model.DispatchRegisterDTO;
import com.ddr.services.DispatchRegisterExcelService;
import com.ddr.services.DispatchRegisterSubmitService;
import com.ddr.util.LoggerUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DispatchRegisterSubmitAction extends ActionSupport {

    Logger logger = LoggerUtil.getLogger(DispatchRegisterSubmitAction.class);

    private DispatchRegisterDTO dto;
    public Map<String, Object> result = new HashMap<>();
    private String message;
    private boolean success;
    private boolean error;

    public String submit() throws Exception {
        String response = getData();
        logger.debug("response >> {}", response);
        return response;
    }

    public String getDispatchDataFromSession() throws Exception {
        logger.debug("getting dispatch data from session");

        getData();

        Map<String, Object> session = ActionContext.getContext().getSession();

        // Check if the session contains the "dispatch_data" key
        if (session == null && !session.containsKey("dispatch_data")) {
            logger.error("session is null or does not contain key");
            result = new HashMap<>();
            return ERROR;
        }

        result = (Map<String, Object>) session.get("dispatch_data");
        return SUCCESS;
    }

    public String getData() throws Exception {
        logger.debug("getData method called..");
        logger.debug("DTO :: {}", dto);

        if (dto == null) {
            dto = new DispatchRegisterDTO();
            dto.setBranch("10");
            dto.setStockPoint("10");
            dto.setDivision("327");
            dto.setStartDate("01/04/2023");
            dto.setEndDate("31/03/2024");
            dto.setCustomer("0");
            dto.setReportType("Y");
            dto.setFinancialYear("17");
        }

        DispatchRegisterSubmitService service = new DispatchRegisterSubmitService();
        result = service.getDispatchReportData(dto);

        if (result == null || result.get("success") == null) {
            error = true;
            message = "Something went wrong!";
            return ERROR;
        } else if (!(Boolean) result.get("success")) {
            error = true;
            message = (String) result.get("message");
            return ERROR;
        }

        // stored it in session
        ActionContext.getContext().getSession().put("dispatch_data", result);

        //DispatchRegisterExcelService excelService = new DispatchRegisterExcelService();
        //String filePath = excelService.updateFileData(dto);
        //logger.debug("filePath :: [{}]", filePath);

        /*HttpServletResponse response = ServletActionContext.getResponse();
        try (InputStream inputStream = excelService.updateFileData(dto);
             ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"Dispatch-Register-Summary.xlsx\"");

            inputStream.transferTo(outputStream);
            outputStream.flush();
        }*/

        return SUCCESS;
    }

    public void setDto(DispatchRegisterDTO dto) {
        this.dto = dto;
    }

    public DispatchRegisterDTO getDto() {
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
