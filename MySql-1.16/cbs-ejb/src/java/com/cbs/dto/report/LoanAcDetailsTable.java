/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class LoanAcDetailsTable {
    private String custId;
    private String accountNumber;
    private String acDesc;
    private String name;
    private float roi;
    private String sanctionDt;
    private String odSanctionChangeDt;
    private BigDecimal amountSanc;
    private String expiryDt;
    private String docExpDt;
    private String disbDt;
    private BigDecimal amtDis;
    private BigDecimal balance;
    private String status;
    private Integer loanDuration;
    private BigDecimal odLimit;
    private BigDecimal installAmt;
    private String sector;
    private String lastCrDt;
    private BigDecimal lastCrAmt;
    private String brnCode;
    private String productCode;
    private String category;
    private BigDecimal npaOir;
    private String acNature;
    private String emiStrtDt;
    private String subSector;
    private String termOfAdvnc;
    private String categroyOpt;
    private Integer jointNo;
    private String jointDetails;

    public String getJointDetails() {
        return jointDetails;
    }

    public void setJointDetails(String jointDetails) {
        this.jointDetails = jointDetails;
    }    
    
    public String getCategroyOpt() {
        return categroyOpt;
    }
    public void setCategroyOpt(String categroyOpt) {
        this.categroyOpt = categroyOpt;
    }
    public String getSubSector() {
        return subSector;
    }
    public void setSubSector(String subSector) {
        this.subSector = subSector;
    }
    public String getTermOfAdvnc() {
        return termOfAdvnc;
    }
    public void setTermOfAdvnc(String termOfAdvnc) {
        this.termOfAdvnc = termOfAdvnc;
    }    
    public String getEmiStrtDt() {
        return emiStrtDt;
    }
    public void setEmiStrtDt(String emiStrtDt) {
        this.emiStrtDt = emiStrtDt;
    }    
    public String getAcNature() {
        return acNature;
    }
    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

   public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getOdSanctionChangeDt() {
        return odSanctionChangeDt;
    }

    public void setOdSanctionChangeDt(String odSanctionChangeDt) {
        this.odSanctionChangeDt = odSanctionChangeDt;
    }

    public BigDecimal getInstallAmt() {
        return installAmt;
    }

    public void setInstallAmt(BigDecimal installAmt) {
        this.installAmt = installAmt;
    }
    
    public String getDisbDt() {
        return disbDt;
    }

    public void setDisbDt(String DisbDt) {
        this.disbDt = DisbDt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmountSanc() {
        return amountSanc;
    }

    public void setAmountSanc(BigDecimal amountSanc) {
        this.amountSanc = amountSanc;
    }

    public BigDecimal getAmtDis() {
        return amtDis;
    }

    public void setAmtDis(BigDecimal amtDis) {
        this.amtDis = amtDis;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(String expiryDt) {
        this.expiryDt = expiryDt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRoi() {
        return roi;
    }

    public void setRoi(float roi) {
        this.roi = roi;
    }

    public String getSanctionDt() {
        return sanctionDt;
    }

    public void setSanctionDt(String sanctionDt) {
        this.sanctionDt = sanctionDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcDesc() {
        return acDesc;
    }

    public void setAcDesc(String acDesc) {
        this.acDesc = acDesc;
    }

    public Integer getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Integer loanDuration) {
        this.loanDuration = loanDuration;
    }

    public BigDecimal getOdLimit() {
        return odLimit;
    }

    public void setOdLimit(BigDecimal odLimit) {
        this.odLimit = odLimit;
    } 

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }    

    public String getLastCrDt() {
        return lastCrDt;
    }

    public void setLastCrDt(String lastCrDt) {
        this.lastCrDt = lastCrDt;
    }

    public BigDecimal getLastCrAmt() {
        return lastCrAmt;
    }

    public void setLastCrAmt(BigDecimal lastCrAmt) {
        this.lastCrAmt = lastCrAmt;
    }

    public String getDocExpDt() {
        return docExpDt;
    }

    public void setDocExpDt(String docExpDt) {
        this.docExpDt = docExpDt;
    }

    public BigDecimal getNpaOir() {
        return npaOir;
    }

    public void setNpaOir(BigDecimal npaOir) {
        this.npaOir = npaOir;
    }

    public Integer getJointNo() {
        return jointNo;
    }

    public void setJointNo(Integer jointNo) {
        this.jointNo = jointNo;
    }
       
}
