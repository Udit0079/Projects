/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class TdLienMarkingGrid implements Serializable {

    private String acNo;
    private int Sno;
    private String voucherNo;
    private String reciept;
    private String printAmt;
    private String fddt;
    private String matDt;
    private String tdmatDt;
    private String intOpt;
    private String roi;
    private String status;
    private String guarSecStatus;
    private String seqNo;
    private String lien;
    public String margineRoi;
    public String addRoi;
    public String applicableRoi;
    public String intTable;
    public String typeOfSec;//O:Other; F: FD/TD in case of Bank Guarantee
    public String chkLien;
    public String details;
    public String security; //P:PRIMARY C:COLLETRAL   
    public String securityType;//DATED OR NON-DATED    
    public String securityDesc1;
    public String securityDesc2;
    public String securityDesc3;
    public String particulars;
    public String otherAc;
    public String matValue;
    public String matDate;
    public String estimationDt;
    public String lienValue;
    public String margin;
    public String lienAcNo;

    public int getSno() {
        return Sno;
    }

    public void setSno(int Sno) {
        this.Sno = Sno;
    }

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

    public String getPrintAmt() {
        return printAmt;
    }

    public void setPrintAmt(String printAmt) {
        this.printAmt = printAmt;
    }

    public String getReciept() {
        return reciept;
    }

    public void setReciept(String reciept) {
        this.reciept = reciept;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
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

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getMargineRoi() {
        return margineRoi;
    }

    public void setMargineRoi(String margineRoi) {
        this.margineRoi = margineRoi;
    }

    public String getApplicableRoi() {
        return applicableRoi;
    }

    public void setApplicableRoi(String applicableRoi) {
        this.applicableRoi = applicableRoi;
    }

    public String getAddRoi() {
        return addRoi;
    }

    public void setAddRoi(String addRoi) {
        this.addRoi = addRoi;
    }

    public String getIntTable() {
        return intTable;
    }

    public void setIntTable(String intTable) {
        this.intTable = intTable;
    }

    public String getTypeOfSec() {
        return typeOfSec;
    }

    public void setTypeOfSec(String typeOfSec) {
        this.typeOfSec = typeOfSec;
    }

    public String getChkLien() {
        return chkLien;
    }

    public void setChkLien(String chkLien) {
        this.chkLien = chkLien;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityDesc1() {
        return securityDesc1;
    }

    public void setSecurityDesc1(String securityDesc1) {
        this.securityDesc1 = securityDesc1;
    }

    public String getSecurityDesc2() {
        return securityDesc2;
    }

    public void setSecurityDesc2(String securityDesc2) {
        this.securityDesc2 = securityDesc2;
    }

    public String getSecurityDesc3() {
        return securityDesc3;
    }

    public void setSecurityDesc3(String securityDesc3) {
        this.securityDesc3 = securityDesc3;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getOtherAc() {
        return otherAc;
    }

    public void setOtherAc(String otherAc) {
        this.otherAc = otherAc;
    }

    public String getMatValue() {
        return matValue;
    }

    public void setMatValue(String matValue) {
        this.matValue = matValue;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public String getEstimationDt() {
        return estimationDt;
    }

    public void setEstimationDt(String estimationDt) {
        this.estimationDt = estimationDt;
    }

    public String getLienValue() {
        return lienValue;
    }

    public void setLienValue(String lienValue) {
        this.lienValue = lienValue;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getLienAcNo() {
        return lienAcNo;
    }

    public void setLienAcNo(String lienAcNo) {
        this.lienAcNo = lienAcNo;
    }

    public String getGuarSecStatus() {
        return guarSecStatus;
    }

    public void setGuarSecStatus(String guarSecStatus) {
        this.guarSecStatus = guarSecStatus;
    }
}
