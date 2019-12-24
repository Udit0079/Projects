package com.cbs.dto.report;

public class InoperativeMarkingPojo implements java.io.Serializable {

    private String ACNO;
    private String DT;
    private String CUSTNAME;
    private String BNKNAME;
    private String BNKADD;
    private double BALANCE;
    private String DAYDIFF;

    public String getACNO() {
        return ACNO;
    }

    public void setACNO(String ACNO) {
        this.ACNO = ACNO;
    }

    public double getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(double BALANCE) {
        this.BALANCE = BALANCE;
    }

    public String getBNKADD() {
        return BNKADD;
    }

    public void setBNKADD(String BNKADD) {
        this.BNKADD = BNKADD;
    }

    public String getBNKNAME() {
        return BNKNAME;
    }

    public void setBNKNAME(String BNKNAME) {
        this.BNKNAME = BNKNAME;
    }

    public String getCUSTNAME() {
        return CUSTNAME;
    }

    public void setCUSTNAME(String CUSTNAME) {
        this.CUSTNAME = CUSTNAME;
    }

    public String getDAYDIFF() {
        return DAYDIFF;
    }

    public void setDAYDIFF(String DAYDIFF) {
        this.DAYDIFF = DAYDIFF;
    }

    public String getDT() {
        return DT;
    }

    public void setDT(String DT) {
        this.DT = DT;
    }
}
