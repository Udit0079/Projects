package com.cbs.dto.report;

public class IssueChequeBookRegisterPojo implements java.io.Serializable, Comparable<IssueChequeBookRegisterPojo> {

    private String bnkName;
    private String bnkAddress;
    private String acNo;
    private String custName;
    private int chBookNo;
    private int chNoFrom;
    private int chNoTo;
    private String issuedBy;
    private String issueDt;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
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

    public int getChBookNo() {
        return chBookNo;
    }

    public void setChBookNo(int chBookNo) {
        this.chBookNo = chBookNo;
    }

    public int getChNoFrom() {
        return chNoFrom;
    }

    public void setChNoFrom(int chNoFrom) {
        this.chNoFrom = chNoFrom;
    }

    public int getChNoTo() {
        return chNoTo;
    }

    public void setChNoTo(int chNoTo) {
        this.chNoTo = chNoTo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }
    public int compareTo(IssueChequeBookRegisterPojo pojo){
      return   this.acNo.compareToIgnoreCase(pojo.getAcNo());
    }
}
