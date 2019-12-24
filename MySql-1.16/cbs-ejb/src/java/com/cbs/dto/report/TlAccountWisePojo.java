package com.cbs.dto.report;

import java.math.BigDecimal;

public class TlAccountWisePojo implements java.io.Serializable, Comparable<TlAccountWisePojo> {

    private String ACCNUM;
    private BigDecimal PRINAMT;
    private BigDecimal ROI;
    private String DISDT;
    private BigDecimal INSTAMT;
    private String INSTDT;
    private String PRD;
    private BigDecimal BUK1;
    private BigDecimal BUK2;
    private BigDecimal BUK3;
    private BigDecimal BUK4;
    private BigDecimal BUK5;
    private BigDecimal BUK6;
    private BigDecimal BUK7;
    private BigDecimal BUK8;
    private Integer LOANMONTH;
    private Long LOANDAYS;
    private String bankname;
    private String branchname;
    private String bankaddress;

    public String getACCNUM() {
        return ACCNUM;
    }

    public void setACCNUM(String ACCNUM) {
        this.ACCNUM = ACCNUM;
    }

    public BigDecimal getBUK1() {
        return BUK1;
    }

    public void setBUK1(BigDecimal BUK1) {
        this.BUK1 = BUK1;
    }

    public BigDecimal getBUK2() {
        return BUK2;
    }

    public void setBUK2(BigDecimal BUK2) {
        this.BUK2 = BUK2;
    }

    public BigDecimal getBUK3() {
        return BUK3;
    }

    public void setBUK3(BigDecimal BUK3) {
        this.BUK3 = BUK3;
    }

    public BigDecimal getBUK4() {
        return BUK4;
    }

    public void setBUK4(BigDecimal BUK4) {
        this.BUK4 = BUK4;
    }

    public BigDecimal getBUK5() {
        return BUK5;
    }

    public void setBUK5(BigDecimal BUK5) {
        this.BUK5 = BUK5;
    }

    public BigDecimal getBUK6() {
        return BUK6;
    }

    public void setBUK6(BigDecimal BUK6) {
        this.BUK6 = BUK6;
    }

    public BigDecimal getBUK7() {
        return BUK7;
    }

    public void setBUK7(BigDecimal BUK7) {
        this.BUK7 = BUK7;
    }

    public BigDecimal getBUK8() {
        return BUK8;
    }

    public void setBUK8(BigDecimal BUK8) {
        this.BUK8 = BUK8;
    }

    public String getDISDT() {
        return DISDT;
    }

    public void setDISDT(String DISDT) {
        this.DISDT = DISDT;
    }

    public BigDecimal getINSTAMT() {
        return INSTAMT;
    }

    public void setINSTAMT(BigDecimal INSTAMT) {
        this.INSTAMT = INSTAMT;
    }

    public String getINSTDT() {
        return INSTDT;
    }

    public void setINSTDT(String INSTDT) {
        this.INSTDT = INSTDT;
    }

    public long getLOANDAYS() {
        return LOANDAYS;
    }

    public void setLOANDAYS(long LOANDAYS) {
        this.LOANDAYS = LOANDAYS;
    }

    public Integer getLOANMONTH() {
        return LOANMONTH;
    }

    public void setLOANMONTH(Integer LOANMONTH) {
        this.LOANMONTH = LOANMONTH;
    }

    public String getPRD() {
        return PRD;
    }

    public void setPRD(String PRD) {
        this.PRD = PRD;
    }

    public BigDecimal getPRINAMT() {
        return PRINAMT;
    }

    public void setPRINAMT(BigDecimal PRINAMT) {
        this.PRINAMT = PRINAMT;
    }

    public BigDecimal getROI() {
        return ROI;
    }

    public void setROI(BigDecimal ROI) {
        this.ROI = ROI;
    }

    public String getBankaddress() {
        return bankaddress;
    }

    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public int compareTo(TlAccountWisePojo pojo) {
        return this.LOANDAYS.compareTo(pojo.getLOANDAYS());
    }
}
