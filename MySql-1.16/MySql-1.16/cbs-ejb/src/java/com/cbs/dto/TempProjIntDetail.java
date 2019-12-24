/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author root
 */
public class TempProjIntDetail {

    private String acno;
    private String custname;
    private double fAmt = 0d;
    private float roi;
    private String matDt;
    private double int1 = 0d;
    private double int2 = 0d;
    private double int3 = 0d;
    private double tds1 = 0d;
    private String td2;
    private double tds3 = 0d;
    private String details;
    private String msg;
    private float cumuPrinamt;
    private int voucherNo;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getfAmt() {
        return fAmt;
    }

    public void setfAmt(double fAmt) {
        this.fAmt = fAmt;
    }

    public double getInt1() {
        return int1;
    }

    public void setInt1(double int1) {
        this.int1 = int1;
    }

    public double getInt2() {
        return int2;
    }

    public void setInt2(double int2) {
        this.int2 = int2;
    }

    public double getInt3() {
        return int3;
    }

    public void setInt3(double int3) {
        this.int3 = int3;
    }

    public String getMatDt() {
        return matDt;
    }

    public void setMatDt(String matDt) {
        this.matDt = matDt;
    }

    public float getRoi() {
        return roi;
    }

    public void setRoi(float roi) {
        this.roi = roi;
    }

    public String getTd2() {
        return td2;
    }

    public void setTd2(String td2) {
        this.td2 = td2;
    }

    public double getTds1() {
        return tds1;
    }

    public void setTds1(double tds1) {
        this.tds1 = tds1;
    }

    public double getTds3() {
        return tds3;
    }

    public void setTds3(double tds3) {
        this.tds3 = tds3;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public float getCumuPrinamt() {
        return cumuPrinamt;
    }

    public void setCumuPrinamt(float cumuPrinamt) {
        this.cumuPrinamt = cumuPrinamt;
    }

    public int getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(int voucherNo) {
        this.voucherNo = voucherNo;
    }
 
}
