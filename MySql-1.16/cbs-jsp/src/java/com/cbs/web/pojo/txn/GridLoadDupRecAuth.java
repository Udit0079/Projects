/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;
import java.io.Serializable;

/**
 *
 * @author root
 */
public class GridLoadDupRecAuth implements Serializable{
   String acNo;
   float vchNo;
   float roi;
   String tdMadeDt;
   String fdDt;
   String matDt;
   float prinAmt;
   String intOpt;
   float receiptNo;
   String period;
   String ofAcno;
   Float seqNo;
   String status;
   String enterBy;
   String auth;
   Double intAtMat;
   Double totTdAmt;

    public Double getTotTdAmt() {
        return totTdAmt;
    }

    public void setTotTdAmt(Double totTdAmt) {
        this.totTdAmt = totTdAmt;
    }

    public Double getIntAtMat() {
        return intAtMat;
    }

    public void setIntAtMat(Double intAtMat) {
        this.intAtMat = intAtMat;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }
      public float getVchNo() {
        return vchNo;
    }

    public void setVchNo(float vchNo) {
        this.vchNo = vchNo;
    }

      public float getRoi() {
        return roi;
    }

    public void setRoi(float roi) {
        this.roi = roi;
    }

      public String getTdMadeDt() {
        return tdMadeDt;
    }

    public void setTdMadeDt(String tdMadeDt) {
        this.tdMadeDt = tdMadeDt;
    }

       public String getFdDt() {
        return fdDt;
    }

    public void setFdDt(String fdDt) {
        this.fdDt = fdDt;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public String getMatDt() {
        return matDt;
    }

    public void setMatDt(String matDt) {
        this.matDt = matDt;
    }

    public String getOfAcno() {
        return ofAcno;
    }

    public void setOfAcno(String ofAcno) {
        this.ofAcno = ofAcno;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getPrinAmt() {
        return prinAmt;
    }

    public void setPrinAmt(float prinAmt) {
        this.prinAmt = prinAmt;
    }

    public float getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(float receiptNo) {
        this.receiptNo = receiptNo;
    }

  
    public Float getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Float seqNo) {
        this.seqNo = seqNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}