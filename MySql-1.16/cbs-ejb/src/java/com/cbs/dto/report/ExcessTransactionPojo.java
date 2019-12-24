package com.cbs.dto.report;

public class ExcessTransactionPojo implements java.io.Serializable {

    private String bnkName;
    private String bnkAddress;
    private String acNo;
    private String partyName;
    private double crAmount;
    private double drAmount;
    private String enterBy;
    private String authBy;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getBnkAddress() {
        return bnkAddress;
    }

    public void setBnkAddress(String bnkAddress) {
        this.bnkAddress = bnkAddress;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public double getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(double crAmount) {
        this.crAmount = crAmount;
    }

    public double getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(double drAmount) {
        this.drAmount = drAmount;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
