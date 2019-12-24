package com.cbs.dto.report;

public class Td15HEntryCertificationPojo {

    private String panNo;
    private String dt;
    private double interest;
    private double totalTaxD;
    private double rate;
    private String fYear;
    private String tdsCircle;
    private String taxAcNo;
    private String panNoDeDu;
    private String custName;
    private String bankNameAdd;
    private String custNameAdd;
    private String frDt;
    private String toDt;
    private String tdsOfficeAdd;

    public String getTdsOfficeAdd() {
        return tdsOfficeAdd;
    }

    public void setTdsOfficeAdd(String tdsOfficeAdd) {
        this.tdsOfficeAdd = tdsOfficeAdd;
    }
    
    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }
    
    public String getBankNameAdd() {
        return bankNameAdd;
    }

    public void setBankNameAdd(String bankNameAdd) {
        this.bankNameAdd = bankNameAdd;
    }

    public String getCustNameAdd() {
        return custNameAdd;
    }

    public void setCustNameAdd(String custNameAdd) {
        this.custNameAdd = custNameAdd;
    }

    
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPanNoDeDu() {
        return panNoDeDu;
    }

    public void setPanNoDeDu(String panNoDeDu) {
        this.panNoDeDu = panNoDeDu;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTaxAcNo() {
        return taxAcNo;
    }

    public void setTaxAcNo(String taxAcNo) {
        this.taxAcNo = taxAcNo;
    }

    public String getTdsCircle() {
        return tdsCircle;
    }

    public void setTdsCircle(String tdsCircle) {
        this.tdsCircle = tdsCircle;
    }

    public double getTotalTaxD() {
        return totalTaxD;
    }

    public void setTotalTaxD(double totalTaxD) {
        this.totalTaxD = totalTaxD;
    }
}
