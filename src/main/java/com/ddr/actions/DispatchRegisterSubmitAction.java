package com.ddr.actions;

import com.ddr.model.DispatchRegisterDTO;
import com.ddr.services.DispatchRegisterExcelService;
import com.ddr.util.LoggerUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.Logger;

public class DispatchRegisterSubmitAction extends ActionSupport {

    Logger logger = LoggerUtil.getLogger(DispatchRegisterSubmitAction.class);

    private DispatchRegisterDTO dto;

    public String submit() throws Exception {
        logger.debug("submit method called..");
        logger.debug("DTO : {}", dto);

        DispatchRegisterExcelService excelService = new DispatchRegisterExcelService();
        String filePath = excelService.updateFileData(dto);
        logger.debug("filePath :: [{}]", filePath);

        return NONE;
    }

    public void setDto(DispatchRegisterDTO dto) {
        this.dto = dto;
    }

    public DispatchRegisterDTO getDto() {
        return dto;
    }
}
