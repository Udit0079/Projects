package com.cbs.dto;

public class DenominitionTable {

    private String Acno,
            EnterBy;
    private Double Amount,
            recno;
    private Integer Ty,
            Rs1000,
            Rs500,
            Rs100,
            Rs50,
            Rs20,
            Rs10,
            Rs5,
            Rs2,
            Rs1,
            Rs05,
            Rs025,
            Rs005,
            Rs010,
            Rs020,
            Rs002,
            Rs001,
            rsc001,
            rs00002,
            rs00001;
    private String Dt,
            Trantime;
    /**Code added by Rohit Krishna Gupta on 29/08/2011**/
    private String cashierId;
    private int cashierLevelId;
    private String closingFlag;

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public int getCashierLevelId() {
        return cashierLevelId;
    }

    public void setCashierLevelId(int cashierLevelId) {
        this.cashierLevelId = cashierLevelId;
    }

    public String getClosingFlag() {
        return closingFlag;
    }

    public void setClosingFlag(String closingFlag) {
        this.closingFlag = closingFlag;
    }

    /**End of added code**/
    public String getAcno() {
        return Acno;
    }

    public void setAcno(String Acno) {
        this.Acno = Acno;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double Amount) {
        this.Amount = Amount;
    }

    public String getEnterBy() {
        return EnterBy;
    }

    public void setEnterBy(String EnterBy) {
        this.EnterBy = EnterBy;
    }

    public Integer getRs001() {
        return Rs001;
    }

    public void setRs001(Integer Rs001) {
        this.Rs001 = Rs001;
    }

    public Integer getRs002() {
        return Rs002;
    }

    public void setRs002(Integer Rs002) {
        this.Rs002 = Rs002;
    }

    public Integer getRs005() {
        return Rs005;
    }

    public void setRs005(Integer Rs005) {
        this.Rs005 = Rs005;
    }

    public Integer getRs010() {
        return Rs010;
    }

    public void setRs010(Integer Rs010) {
        this.Rs010 = Rs010;
    }

    public Integer getRs020() {
        return Rs020;
    }

    public void setRs020(Integer Rs020) {
        this.Rs020 = Rs020;
    }

    public Integer getRs025() {
        return Rs025;
    }

    public void setRs025(Integer Rs025) {
        this.Rs025 = Rs025;
    }

    public Integer getRs05() {
        return Rs05;
    }

    public void setRs05(Integer Rs05) {
        this.Rs05 = Rs05;
    }

    public Integer getRs1() {
        return Rs1;
    }

    public void setRs1(Integer Rs1) {
        this.Rs1 = Rs1;
    }

    public Integer getRs10() {
        return Rs10;
    }

    public void setRs10(Integer Rs10) {
        this.Rs10 = Rs10;
    }

    public Integer getRs100() {
        return Rs100;
    }

    public void setRs100(Integer Rs100) {
        this.Rs100 = Rs100;
    }

    public Integer getRs1000() {
        return Rs1000;
    }

    public void setRs1000(Integer Rs1000) {
        this.Rs1000 = Rs1000;
    }

    public Integer getRs2() {
        return Rs2;
    }

    public void setRs2(Integer Rs2) {
        this.Rs2 = Rs2;
    }

    public Integer getRs20() {
        return Rs20;
    }

    public void setRs20(Integer Rs20) {
        this.Rs20 = Rs20;
    }

    public Integer getRs5() {
        return Rs5;
    }

    public void setRs5(Integer Rs5) {
        this.Rs5 = Rs5;
    }

    public Integer getRs50() {
        return Rs50;
    }

    public void setRs50(Integer Rs50) {
        this.Rs50 = Rs50;
    }

    public Integer getRs500() {
        return Rs500;
    }

    public void setRs500(Integer Rs500) {
        this.Rs500 = Rs500;
    }

    public String getDt() {
        return Dt;
    }

    public void setDt(String Dt) {
        this.Dt = Dt;
    }

    public String getTrantime() {
        return Trantime;
    }

    public void setTrantime(String Trantime) {
        this.Trantime = Trantime;
    }

    public Integer getTy() {
        return Ty;
    }

    public void setTy(Integer Ty) {
        this.Ty = Ty;
    }

    public Double getRecno() {
        return recno;
    }

    public void setRecno(Double recno) {
        this.recno = recno;
    }

    public Integer getRs00001() {
        return rs00001;
    }

    public void setRs00001(Integer rs00001) {
        this.rs00001 = rs00001;
    }

    public Integer getRs00002() {
        return rs00002;
    }

    public void setRs00002(Integer rs00002) {
        this.rs00002 = rs00002;
    }

    public Integer getRsc001() {
        return rsc001;
    }

    public void setRsc001(Integer rsc001) {
        this.rsc001 = rsc001;
    }

    @Override
    public String toString() {
        return ("Rs1000-" + Rs1000 + "\nRs500-" + Rs500 + "\nRs100-" + Rs100 + "\nRs50-" + Rs50 + "\nRs20-" + Rs20 + "\nRs10-" + Rs10 + "\nRs5-" + Rs5 + "\nRs2-" + Rs2 + "\nRs1-" + Rs1 + "\nRs0.50-" + Rs05 + "\nTotal-" + Amount);
    }
}
