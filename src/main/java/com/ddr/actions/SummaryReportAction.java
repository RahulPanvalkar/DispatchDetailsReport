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
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class SummaryReportAction extends ActionSupport {

    Logger logger = LoggerUtil.getLogger(SummaryReportAction.class);

    private DispatchRegisterDTO dto;
    public Map<String, Object> result = new HashMap<>();
    private String message;
    private boolean success;
    private boolean error;


    public String getDispatchDataFromSession() throws Exception {
        logger.debug("getting dispatch data from session");

        Map<String, Object> session = ActionContext.getContext().getSession();

        // Check if the session contains the "dispatch_data" key
        if (session == null && !session.containsKey("dispatch_dto")) {
            logger.error("session is null or does not contain key");
            result = new HashMap<>();
            return ERROR;
        }

        dto = (DispatchRegisterDTO) session.get("dispatch_dto");

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

        return SUCCESS;
    }

    public String downloadExcel() throws Exception {
        logger.debug("downloadExcel method called..");

        /*if (dto == null) {
            logger.info("DTO value is null..");
            dto = new DispatchRegisterDTO();
            dto.setBranch("10");
            dto.setStockPoint("10");
            dto.setDivision("327");
            dto.setStartDate("01/04/2023");
            dto.setEndDate("31/03/2024");
            dto.setCustomer("0");
            dto.setReportType("Y");
            dto.setFinancialYear("17");
        }*/

        Map<String, Object> session = ActionContext.getContext().getSession();

        // Check if the session contains the "dispatch_data" key
        if (session == null && !session.containsKey("dispatch_dto")) {
            logger.error("session is null or does not contain dto key");
            error = true;
            message = "Download failed, internal server error";

            // Store message in session or request
            ActionContext.getContext().getSession().put("error", true);
            ActionContext.getContext().getSession().put("message", message);
            return ERROR;
        }

        dto = (DispatchRegisterDTO) session.get("dispatch_dto");
        if (dto == null) {
            error = true;
            message = "Download failed, internal server error";
            logger.error("Registration details required >> dto : [{}]", dto);

            // Store message in session or request
            ActionContext.getContext().getSession().put("error", true);
            ActionContext.getContext().getSession().put("message", message);
            return ERROR;
        }

        try {
            DispatchRegisterExcelService service = new DispatchRegisterExcelService();
            String filePath = service.updateFileData(dto);

            File file = new File(filePath);

            if (!file.exists()) {
                error = true;
                message = "Download failed, File not found";
                logger.error("File not found >> filePath : [{}]", filePath);

                // Store message in session or request
                ActionContext.getContext().getSession().put("downloadError", true);
                ActionContext.getContext().getSession().put("message", message);
                return ERROR;
            }

            HttpServletResponse response = ServletActionContext.getResponse();
            try (FileInputStream inputStream = new FileInputStream(file);
                 ServletOutputStream outputStream = response.getOutputStream()) {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=\"Dispatch-Register-Report.xlsx\"");

                inputStream.transferTo(outputStream);
                outputStream.flush();
            }
        } catch (Exception e) {
            logger.error("Error processing file download", e);
            error = true;
            message = "Error occurred, please try again later";
            // Store message in session or request
            ActionContext.getContext().getSession().put("error", true);
            ActionContext.getContext().getSession().put("message", message);
            return ERROR;
        }

        return NONE;
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
