package com.ddr.model;

public class DispatchRegisterDTO {
    private String branch;  // locId
    private String stockPoint;  // stockPointId
    private String division;    // divisionId
    private String financialYear;   //finYearId
    private String startDate;
    private String endDate;
    private String customer;    //custId
    private String reportType;  // detailed ? 'Y' OR 'N'

    public DispatchRegisterDTO() {    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getStockPoint() {
        return stockPoint;
    }

    public void setStockPoint(String stockPoint) {
        this.stockPoint = stockPoint;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    @Override
    public String toString() {
        return String.format("DispatchRegisterDTO{ branch=%s, stockPoint=%s, division=%s, finYearId=%s, startDate=%s, endDate=%s, " +
                        "customer=%s, reportType/detailed=%s}%n",
                branch, stockPoint, division, financialYear, startDate, endDate, customer, reportType);
    }
}
