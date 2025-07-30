package com.ddr.actions;

import com.ddr.model.DispatchRegisterDTO;
import com.ddr.services.DispatchRegisterFormService;
import com.ddr.services.DispatchRegisterSubmitService;
import com.ddr.util.LoggerUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DispatchRegisterFormAction extends ActionSupport implements ModelDriven<DispatchRegisterDTO> {

    private final Logger logger = LoggerUtil.getLogger(DispatchRegisterFormAction.class);

    public Map<String, Object> result = new HashMap<>();

    private String type;

    private int locId;

    private int finYearId;

    private DispatchRegisterDTO dto;
    private String message;
    private boolean success;
    private boolean error;

    public String getData() {
        logger.debug("getData method called >> type [{}]", this.type);
        this.type = (this.type == null || this.type.trim().isEmpty()) ? "" : this.type.trim();

        DispatchRegisterFormService service = new DispatchRegisterFormService();

        switch (type) {
            case "default":
                this.result = service.getDefaultDataList();
                break;
            case "branchList":
                this.result = service.getBranchList();
                break;
            case "stockPointList":
                this.result = service.getStockPointList();
                break;
            case "stockPoint":
                this.result = service.getStockPointByLocId(locId);
                break;
            case "divisionList":
                this.result = service.getDivisionList();
                break;
            case "customerList":
                this.result = service.getCustomerListByLocId(locId);
                break;
            case "financialYearList":
                this.result = service.getFinancialYearList();
                break;
            case "finYearDates":
                this.result = service.getFinancialYearDates(finYearId);
                break;
            default: {
                this.result.put("success", false);
                this.result.put("message", "Data type required");
            }
        }

        return SUCCESS;
    }

    // method to submit the form
    public String submit() throws Exception {
        logger.debug("submit method called..");

        String division = this.dto.getDivision();
        // get dispatch data using stored procedure from service
        DispatchRegisterSubmitService service = new DispatchRegisterSubmitService();
        result = service.getDispatchRegisterSubmit(this.dto);

        if (result == null || result.get("success") == null) {
            ActionContext.getContext().getSession().put("dispatch_dto", this.dto);
            error = true;
            message = "Something went wrong!";
            return ERROR;
        } else if (!(Boolean) result.get("success")) {
            ActionContext.getContext().getSession().put("dispatch_dto", this.dto);
            error = true;
            message = (String) result.get("message");
            return ERROR;
        }

        // stored it in session
        this.dto.setDivision(division);
        ActionContext.getContext().getSession().put("dispatch_dto", this.dto);

        return SUCCESS;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public void setFinYearId(int finYearId) {
        this.finYearId = finYearId;
    }

    public DispatchRegisterDTO getDto() {
        return dto;
    }

    public void setDto(DispatchRegisterDTO dto) {
        this.dto = dto;
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

    @Override
    public DispatchRegisterDTO getModel() {
        return dto;
    }
}

