package com.cbs.dto.report;

public class AccountStatusReportPojo implements java.io.Serializable {

    private String acName;
    private int activeAcc;
    private int closedAcc;
    private String depositLoan;
    private String acNo;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }
    
    public String getDepositLoan() {
        return depositLoan;
    }

    public void setDepositLoan(String depositLoan) {
        this.depositLoan = depositLoan;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public int getActiveAcc() {
        return activeAcc;
    }

    public void setActiveAcc(int activeAcc) {
        this.activeAcc = activeAcc;
    }

    public int getClosedAcc() {
        return closedAcc;
    }

    public void setClosedAcc(int closedAcc) {
        this.closedAcc = closedAcc;
    }
}
