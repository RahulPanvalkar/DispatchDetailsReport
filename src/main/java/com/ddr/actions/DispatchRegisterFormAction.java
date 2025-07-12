package com.ddr.actions;

import com.ddr.services.DispatchRegisterFormService;
import com.ddr.util.LoggerUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatchRegisterFormAction extends ActionSupport {

    private final Logger logger = LoggerUtil.getLogger(DispatchRegisterFormAction.class);

    public Map<String, Object> result = new HashMap<>();

    private String type;

    private int locId;

    private int finYearId;

    private List<String> reportTypeList;

    @Override
    public String execute() {
        logger.debug("execute method called..");
        DispatchRegisterFormService service = new DispatchRegisterFormService();
        logger.debug("type :: [{}]", this.type);
        this.type = (this.type == null || this.type.trim().isEmpty()) ? "" : this.type.trim();

        switch (type) {
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
                this.result.put("message", "Unexpected error occurred");
            }
        }

        logger.debug("result >> {}", this.result);
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
}

