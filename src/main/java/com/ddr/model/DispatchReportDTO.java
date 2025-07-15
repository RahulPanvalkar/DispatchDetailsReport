package com.ddr.model;

import com.ddr.util.CommonUtil;

public class DispatchReportDTO {
    private String dspTrnNo;    // TRANSACTION NO >> 000000000000001
    private String dspDtStr;    // DISPATCH DATE >> 04/04/2023
    private String custName;    // PARTY >> RUBY PHARMA
    private String destination; // DESTINATION >> SOLAPUR
    private String transporter; // TRANSPORTER >> BTI
    private double goodsValue;  // GOODS VALUE >> 12934.00
    private String invNo;   // INVOICE NO >> BWMNI2300001
    private String lrNum;   // LR NO >> 675433
    private String driverName;  // DRIVER NAME
    private String lorryNo;     // LORRY NUMBER
    private String lrDateStr;   // LR DATE >> 04/04/2023
    private int delayDays;      // DISPATCH DELAY >> 1.00
    private int noOfCases;      // NO OF CASES >> 13.00
    private String formNum;     // FORM NUM >> NA
    private String cFormDateStr;    // C FORM DATE >> 04/04/2023
    private int cFormValue;     // C FORM VALUE >> 0.00
    private String podDateStr;  // POD DATE
    private int podNum;     // POD NUMBER
    private String podReason;   // REASON

    public DispatchReportDTO() {
    }

    public DispatchReportDTO(DispatchReport dispatchReport) {

        // Convert Dates
        this.cFormDateStr = CommonUtil.convertToString(dispatchReport.getcFormDate());
        this.podDateStr = CommonUtil.convertToString(dispatchReport.getPodDate());
        this.lrDateStr = CommonUtil.convertToString(dispatchReport.getLrDate());
        this.dspDtStr = CommonUtil.convertToString(dispatchReport.getDspDt());

        // Set remaining values
        this.dspTrnNo = dispatchReport.getDspTrnNo();
        this.custName = dispatchReport.getCustName();
        this.destination = dispatchReport.getDestination();
        this.transporter = dispatchReport.getTransporter();
        this.goodsValue = dispatchReport.getGoodsValue();
        this.invNo = dispatchReport.getInvNo();
        this.lrNum = dispatchReport.getLrNum();
        this.driverName = dispatchReport.getDriverName();
        this.lorryNo = dispatchReport.getLorryNo();
        this.delayDays = dispatchReport.getDelayDays();
        this.noOfCases = dispatchReport.getNoOfCases();
        this.formNum = dispatchReport.getFormNum();
        this.cFormValue = dispatchReport.getcFormValue();
        this.podNum = dispatchReport.getPodNum();
        this.podReason = dispatchReport.getPodReason();
    }


    public String getDspTrnNo() {
        return dspTrnNo;
    }

    public void setDspTrnNo(String dspTrnNo) {
        this.dspTrnNo = dspTrnNo;
    }

    public String getDspDtStr() {
        return dspDtStr;
    }

    public void setDspDtStr(String dspDtStr) {
        this.dspDtStr = dspDtStr;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public double getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(double goodsValue) {
        this.goodsValue = goodsValue;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getLrNum() {
        return lrNum;
    }

    public void setLrNum(String lrNum) {
        this.lrNum = lrNum;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLorryNo() {
        return lorryNo;
    }

    public void setLorryNo(String lorryNo) {
        this.lorryNo = lorryNo;
    }

    public String getLrDateStr() {
        return lrDateStr;
    }

    public void setLrDateStr(String lrDateStr) {
        this.lrDateStr = lrDateStr;
    }

    public int getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(int delayDays) {
        this.delayDays = delayDays;
    }

    public int getNoOfCases() {
        return noOfCases;
    }

    public void setNoOfCases(int noOfCases) {
        this.noOfCases = noOfCases;
    }

    public String getFormNum() {
        return formNum;
    }

    public void setFormNum(String formNum) {
        this.formNum = formNum;
    }

    public String getcFormDateStr() {
        return cFormDateStr;
    }

    public void setcFormDateStr(String cFormDateStr) {
        this.cFormDateStr = cFormDateStr;
    }

    public int getcFormValue() {
        return cFormValue;
    }

    public void setcFormValue(int cFormValue) {
        this.cFormValue = cFormValue;
    }

    public String getPodDateStr() {
        return podDateStr;
    }

    public void setPodDateStr(String podDateStr) {
        this.podDateStr = podDateStr;
    }

    public int getPodNum() {
        return podNum;
    }

    public void setPodNum(int podNum) {
        this.podNum = podNum;
    }

    public String getPodReason() {
        return podReason;
    }

    public void setPodReason(String podReason) {
        this.podReason = podReason;
    }
}
