/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;


/**
 *
 * @author root
 */
@Embeddable
public class CbsLoanInterestCodeMasterHistoryPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "INTEREST_CODE")
    private String interestCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTEREST_VERSION_NO")
    private int interestVersionNo;

    public CbsLoanInterestCodeMasterHistoryPK() {
    }

    public CbsLoanInterestCodeMasterHistoryPK(String interestCode, int interestVersionNo) {
        this.interestCode = interestCode;
        this.interestVersionNo = interestVersionNo;
    }

    public String getInterestCode() {
        return interestCode;
    }

    public void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    public int getInterestVersionNo() {
        return interestVersionNo;
    }

    public void setInterestVersionNo(int interestVersionNo) {
        this.interestVersionNo = interestVersionNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (interestCode != null ? interestCode.hashCode() : 0);
        hash += (int) interestVersionNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsLoanInterestCodeMasterHistoryPK)) {
            return false;
        }
        CbsLoanInterestCodeMasterHistoryPK other = (CbsLoanInterestCodeMasterHistoryPK) object;
        if ((this.interestCode == null && other.interestCode != null) || (this.interestCode != null && !this.interestCode.equals(other.interestCode))) {
            return false;
        }
        if (this.interestVersionNo != other.interestVersionNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsLoanInterestCodeMasterHistoryPK[ interestCode=" + interestCode + ", interestVersionNo=" + interestVersionNo + " ]";
    }
    
}
