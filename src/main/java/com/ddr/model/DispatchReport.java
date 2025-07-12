package com.ddr.model;

import java.time.LocalDate;

public class DispatchReport {
    private String dspTrnNo;    // TRANSACTION NO >> 000000000000001
    private LocalDate dspDt;    // DISPATCH DATE >> 04/04/2023
    private String custName;    // PARTY >> RUBY PHARMA
    private String destination; // DESTINATION >> SOLAPUR
    private String transporter; // TRANSPORTER >> BTI
    private double goodsValue;  // GOODS VALUE >> 12934.00
    private String invNo;   // INVOICE NO >> BWMNI2300001
    private String lrNum;   // LR NO >> 675433
    private String driverName;  // DRIVER NAME
    private String lorryNo;     // LORRY NUMBER
    private LocalDate lrDate;   // LR DATE >> 04/04/2023
    private int delayDays;      // DISPATCH DELAY >> 1.00
    private int noOfCases;      // NO OF CASES >> 13.00
    private String formNum;     // FORM NUM >> NA
    private LocalDate cFormDate;    // C FORM DATE >> 04/04/2023
    private int cFormValue;     // C FORM VALUE >> 0.00
    private LocalDate podDate;  // POD DATE
    private int podNum;     // POD NUMBER
    private String podReason;   // REASON


    // CURRENTLY NOT REQUIRED
//    private long rowNum;
//    private long dspId;
//    private long divId;
//    private String division;
//    private String challanNo;
//    private int custId;
//    private float dspValue;
//    private int prodId;
//    private String prodCd;
//    private String prodName;
//    private int batchId;
//    private String batchNo;
//    private long orderNo;
//    private int sold;
//    private int free;
//    private int repl;
//    private float rate;
//    private double tax;
//    private double discount;

    public DispatchReport() {
    }

    public String getDspTrnNo() {
        return dspTrnNo;
    }

    public void setDspTrnNo(String dspTrnNo) {
        this.dspTrnNo = dspTrnNo;
    }

    public LocalDate getDspDt() {
        return dspDt;
    }

    public void setDspDt(LocalDate dspDt) {
        this.dspDt = dspDt;
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

    public LocalDate getLrDate() {
        return lrDate;
    }

    public void setLrDate(LocalDate lrDate) {
        this.lrDate = lrDate;
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

    public LocalDate getcFormDate() {
        return cFormDate;
    }

    public void setcFormDate(LocalDate cFormDate) {
        this.cFormDate = cFormDate;
    }

    public int getcFormValue() {
        return cFormValue;
    }

    public void setcFormValue(int cFormValue) {
        this.cFormValue = cFormValue;
    }

    public LocalDate getPodDate() {
        return podDate;
    }

    public void setPodDate(LocalDate podDate) {
        this.podDate = podDate;
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

    @Override
    public String toString() {
        return String.format(
                "DispatchRegister{dspTrnNo='%s', dspDt=%s, custName='%s', destination='%s', transporter='%s', " +
                        "goodsValue=%.2f, invNo='%s', lrNum='%s', driverName='%s', lorryNo='%s', lrDate=%s, delayDays=%d, " +
                        "noOfCases=%d, formNum='%s', cFormDate=%s, cFormValue=%d, podDate=%s, podNum=%d, podReason='%s'}",
                dspTrnNo, dspDt, custName, destination, transporter, goodsValue, invNo, lrNum, driverName, lorryNo, lrDate,
                delayDays, noOfCases, formNum, cFormDate, cFormValue, podDate, podNum, podReason
        );
    }


}
