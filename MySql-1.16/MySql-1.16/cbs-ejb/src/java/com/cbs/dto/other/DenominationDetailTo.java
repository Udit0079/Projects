/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.other;

import java.io.Serializable;
import java.math.BigDecimal;

//String acno, float recNo, String dt, String denomination,int denoValue, int ty, String brnCode, String enteryBy
public class DenominationDetailTo implements Serializable {

    private String acno;
    private float recNo;
    private String dt;
    private BigDecimal denomination;
    private int denoValue;
    private int ty;
    private String brnCode;
    private String enteryBy;
    private String newOldFlag;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public float getRecNo() {
        return recNo;
    }

    public void setRecNo(float recNo) {
        this.recNo = recNo;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public BigDecimal getDenomination() {
        return denomination;
    }

    public void setDenomination(BigDecimal denomination) {
        this.denomination = denomination;
    }

    public int getDenoValue() {
        return denoValue;
    }

    public void setDenoValue(int denoValue) {
        this.denoValue = denoValue;
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public String getEnteryBy() {
        return enteryBy;
    }

    public void setEnteryBy(String enteryBy) {
        this.enteryBy = enteryBy;
    }

    public String getNewOldFlag() {
        return newOldFlag;
    }

    public void setNewOldFlag(String newOldFlag) {
        this.newOldFlag = newOldFlag;
    }
}
