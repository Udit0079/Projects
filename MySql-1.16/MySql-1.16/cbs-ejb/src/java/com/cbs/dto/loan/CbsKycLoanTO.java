/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class CbsKycLoanTO implements Serializable {
    private static final long serialVersionUID = 1L;
   
    protected CbsKycLoanPKTO cbsKycLoanPKTO;
   
    private String loantype;
    
    private String loanamt;
   
    private String lastChangeUserID;
    
    private Date lastChangeTime;
    
    private String recordCreaterID;
    
    private Date creationTime;
    
    private String tsCnt;

    public CbsKycLoanPKTO getCbsKycLoanPKTO() {
        return cbsKycLoanPKTO;
    }

    public void setCbsKycLoanPKTO(CbsKycLoanPKTO cbsKycLoanPKTO) {
        this.cbsKycLoanPKTO = cbsKycLoanPKTO;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public String getLoanamt() {
        return loanamt;
    }

    public void setLoanamt(String loanamt) {
        this.loanamt = loanamt;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public String getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(String tsCnt) {
        this.tsCnt = tsCnt;
    }
    
    
    
}
