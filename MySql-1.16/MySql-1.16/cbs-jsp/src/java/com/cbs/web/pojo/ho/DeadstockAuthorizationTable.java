/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.ho;

/**
 *
 * @author Ankit Verma
 */
public class DeadstockAuthorizationTable {
    private String acno;
    private int trsno;
    private String custName;
    private float crAmt;
    private float drAmt;
    private String instNo;
    private String enterBy;
    private String auth;
    private String details;
    private float recNo;
    private String dt;
    private String destBrnId;
    private String orgBrnId;
    private int ty;
    private String intFlag;
    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public float getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(float crAmt) {
        this.crAmt = crAmt;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDestBrnId() {
        return destBrnId;
    }

    public void setDestBrnId(String destBrnId) {
        this.destBrnId = destBrnId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(float drAmt) {
        this.drAmt = drAmt;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getOrgBrnId() {
        return orgBrnId;
    }

    public void setOrgBrnId(String orgBrnId) {
        this.orgBrnId = orgBrnId;
    }

    public float getRecNo() {
        return recNo;
    }

    public void setRecNo(float recNo) {
        this.recNo = recNo;
    }

    public int getTrsno() {
        return trsno;
    }

    public void setTrsno(int trsno) {
        this.trsno = trsno;
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
    }

    public String getIntFlag() {
        return intFlag;
    }

    public void setIntFlag(String intFlag) {
        this.intFlag = intFlag;
    }

    

}
