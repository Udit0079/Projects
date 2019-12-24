/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.sms;

import com.cbs.sms.service.SmsType;
import java.io.Serializable;

public class SmsToBatchTo implements Serializable {

    private static final long serialVersionUID = 52369874112534L;
    private String acNo;
    private Double crAmt;
    private Double drAmt;
    private Integer tranType;
    private Integer ty;         //0-Credit, 1- Debit
    private String txnDt;       //In dd/MM/yyyy
    private SmsType template;   //Template Name
    private String details;
    
    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public Double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(Double crAmt) {
        this.crAmt = crAmt;
    }

    public Double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(Double drAmt) {
        this.drAmt = drAmt;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public String getTxnDt() {
        return txnDt;
    }

    public void setTxnDt(String txnDt) {
        this.txnDt = txnDt;
    }

    public SmsType getTemplate() {
        return template;
    }

    public void setTemplate(SmsType template) {
        this.template = template;
    }

    public Integer getTranType() {
        return tranType;
    }

    public void setTranType(Integer tranType) {
        this.tranType = tranType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
}
