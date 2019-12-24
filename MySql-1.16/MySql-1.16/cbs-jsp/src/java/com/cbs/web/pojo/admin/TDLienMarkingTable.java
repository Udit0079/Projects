/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class TDLienMarkingTable implements Serializable{

    private String acNo;
    private float voucherNo;
    private float reciept;
    private float printAmt;
    private String fddt;
    private String matDt;
    private String tdmatDt;
    private String intOpt;
    private float roi;
    private String status;
    private float seqNo;
    private String lien;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getFddt() {
        return fddt;
    }

    public void setFddt(String fddt) {
        this.fddt = fddt;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getMatDt() {
        return matDt;
    }

    public void setMatDt(String matDt) {
        this.matDt = matDt;
    }

    public float getPrintAmt() {
        return printAmt;
    }

    public void setPrintAmt(float printAmt) {
        this.printAmt = printAmt;
    }

    public float getReciept() {
        return reciept;
    }

    public void setReciept(float reciept) {
        this.reciept = reciept;
    }

    public float getRoi() {
        return roi;
    }

    public void setRoi(float roi) {
        this.roi = roi;
    }

    public float getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(float seqNo) {
        this.seqNo = seqNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTdmatDt() {
        return tdmatDt;
    }

    public void setTdmatDt(String tdmatDt) {
        this.tdmatDt = tdmatDt;
    }

    public float getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(float voucherNo) {
        this.voucherNo = voucherNo;
    }

    public TDLienMarkingTable(String acNo, float voucherNo, float reciept, float printAmt, String fddt, String matDt, String tdmatDt, String intOpt, float roi, String status, float seqNo, String lien) {
        this.acNo = acNo;
        this.voucherNo = voucherNo;
        this.reciept = reciept;
        this.printAmt = printAmt;
        this.fddt = fddt;
        this.matDt = matDt;
        this.tdmatDt = tdmatDt;
        this.intOpt = intOpt;
        this.roi = roi;
        this.status = status;
        this.seqNo = seqNo;
        this.lien = lien;
    }




}
