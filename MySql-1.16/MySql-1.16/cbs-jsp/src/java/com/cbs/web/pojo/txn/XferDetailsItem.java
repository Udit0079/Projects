/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class XferDetailsItem implements Serializable{

    float  trsNo;

    String  acNo;

    String  custName;

    double  balance;

    double  crAmt;

    double  drAmt;

    String  instNo;

    String  auth;

    String  details;

    String  enterBy;

    float   recNo;

    String  clgReason;

    String  iy;

    String  subtokenno;

    String  checkBy;

    String  tranType;

    String  ty;

    String  payBy;

    String  viewDetails;

    String  instDt;

    String valueDt;
    
    String adviceNo;
    
    String adviceBrCode;
    
    String relatedTo;

    public String getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }
    
    public String getAdviceBrCode() {
        return adviceBrCode;
    }

    public void setAdviceBrCode(String adviceBrCode) {
        this.adviceBrCode = adviceBrCode;
    }

    public String getAdviceNo() {
        return adviceNo;
    }

    public void setAdviceNo(String adviceNo) {
        this.adviceNo = adviceNo;
    }
    
    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getValueDt() {
        return valueDt;
    }

    public void setValueDt(String valueDt) {
        this.valueDt = valueDt;
    }
    
    public String getViewDetails() {
        return viewDetails;
    }

    public void setViewDetails(String viewDetails) {
        this.viewDetails = viewDetails;
    }
    

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public String getClgReason() {
        return clgReason;
    }

    public void setClgReason(String clgReason) {
        this.clgReason = clgReason;
    }

    public double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(double crAmt) {
        this.crAmt = crAmt;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(double drAmt) {
        this.drAmt = drAmt;
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

    public String getIy() {
        return iy;
    }

    public void setIy(String iy) {
        this.iy = iy;
    }

    public String getPayBy() {
        return payBy;
    }

    public void setPayBy(String payBy) {
        this.payBy = payBy;
    }

    public float getRecNo() {
        return recNo;
    }

    public void setRecNo(float recNo) {
        this.recNo = recNo;
    }

    public String getSubtokenno() {
        return subtokenno;
    }

    public void setSubtokenno(String subtokenno) {
        this.subtokenno = subtokenno;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public float getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(float trsNo) {
        this.trsNo = trsNo;
    }

    public String getTy() {
        return ty;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }

    
}
