/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Athar Reza
 */
public class FdAccountDetailPojo implements Serializable {

    private String acNo;
    private String custName;
    private String fatherName;
    private String perAddr;
    private String panNo;
    private BigDecimal prnAmt;
    private BigDecimal matAmt;
    private BigDecimal roiAmt;
    private BigDecimal intAmt;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }

    public BigDecimal getMatAmt() {
        return matAmt;
    }

    public void setMatAmt(BigDecimal matAmt) {
        this.matAmt = matAmt;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPerAddr() {
        return perAddr;
    }

    public void setPerAddr(String perAddr) {
        this.perAddr = perAddr;
    }

    public BigDecimal getPrnAmt() {
        return prnAmt;
    }

    public void setPrnAmt(BigDecimal prnAmt) {
        this.prnAmt = prnAmt;
    }

    public BigDecimal getRoiAmt() {
        return roiAmt;
    }

    public void setRoiAmt(BigDecimal roiAmt) {
        this.roiAmt = roiAmt;
    }
    
}
