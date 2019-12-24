/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_loan_repayment_cycle_definition")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeLoanRepaymentCycleDefinition.findAll", query = "SELECT c FROM CbsSchemeLoanRepaymentCycleDefinition c"),
    @NamedQuery(name = "CbsSchemeLoanRepaymentCycleDefinition.findBySchemeCode", query = "SELECT c FROM CbsSchemeLoanRepaymentCycleDefinition c WHERE c.cbsSchemeLoanRepaymentCycleDefinitionPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeLoanRepaymentCycleDefinition.findByAccountOpenFromDate", query = "SELECT c FROM CbsSchemeLoanRepaymentCycleDefinition c WHERE c.cbsSchemeLoanRepaymentCycleDefinitionPK.accountOpenFromDate = :accountOpenFromDate"),
    @NamedQuery(name = "CbsSchemeLoanRepaymentCycleDefinition.findByAccountOpenToDate", query = "SELECT c FROM CbsSchemeLoanRepaymentCycleDefinition c WHERE c.accountOpenToDate = :accountOpenToDate"),
    @NamedQuery(name = "CbsSchemeLoanRepaymentCycleDefinition.findByRepaymentStartDate", query = "SELECT c FROM CbsSchemeLoanRepaymentCycleDefinition c WHERE c.repaymentStartDate = :repaymentStartDate"),
    @NamedQuery(name = "CbsSchemeLoanRepaymentCycleDefinition.findByCurrentOrNextMonthIndicator", query = "SELECT c FROM CbsSchemeLoanRepaymentCycleDefinition c WHERE c.currentOrNextMonthIndicator = :currentOrNextMonthIndicator"),
    @NamedQuery(name = "CbsSchemeLoanRepaymentCycleDefinition.findByDelFlag", query = "SELECT c FROM CbsSchemeLoanRepaymentCycleDefinition c WHERE c.delFlag = :delFlag")})
public class CbsSchemeLoanRepaymentCycleDefinition extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeLoanRepaymentCycleDefinitionPK cbsSchemeLoanRepaymentCycleDefinitionPK;
    @Size(max = 2)
    @Column(name = "ACCOUNT_OPEN_TO_DATE")
    private String accountOpenToDate;
    @Size(max = 2)
    @Column(name = "REPAYMENT_START_DATE")
    private String repaymentStartDate;
    @Size(max = 1)
    @Column(name = "CURRENT_OR_NEXT_MONTH_INDICATOR")
    private String currentOrNextMonthIndicator;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeLoanRepaymentCycleDefinition() {
    }

    public CbsSchemeLoanRepaymentCycleDefinition(CbsSchemeLoanRepaymentCycleDefinitionPK cbsSchemeLoanRepaymentCycleDefinitionPK) {
        this.cbsSchemeLoanRepaymentCycleDefinitionPK = cbsSchemeLoanRepaymentCycleDefinitionPK;
    }

    public CbsSchemeLoanRepaymentCycleDefinition(String schemeCode, String accountOpenFromDate) {
        this.cbsSchemeLoanRepaymentCycleDefinitionPK = new CbsSchemeLoanRepaymentCycleDefinitionPK(schemeCode, accountOpenFromDate);
    }

    public CbsSchemeLoanRepaymentCycleDefinitionPK getCbsSchemeLoanRepaymentCycleDefinitionPK() {
        return cbsSchemeLoanRepaymentCycleDefinitionPK;
    }

    public void setCbsSchemeLoanRepaymentCycleDefinitionPK(CbsSchemeLoanRepaymentCycleDefinitionPK cbsSchemeLoanRepaymentCycleDefinitionPK) {
        this.cbsSchemeLoanRepaymentCycleDefinitionPK = cbsSchemeLoanRepaymentCycleDefinitionPK;
    }

    public String getAccountOpenToDate() {
        return accountOpenToDate;
    }

    public void setAccountOpenToDate(String accountOpenToDate) {
        this.accountOpenToDate = accountOpenToDate;
    }

    public String getRepaymentStartDate() {
        return repaymentStartDate;
    }

    public void setRepaymentStartDate(String repaymentStartDate) {
        this.repaymentStartDate = repaymentStartDate;
    }

    public String getCurrentOrNextMonthIndicator() {
        return currentOrNextMonthIndicator;
    }

    public void setCurrentOrNextMonthIndicator(String currentOrNextMonthIndicator) {
        this.currentOrNextMonthIndicator = currentOrNextMonthIndicator;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsSchemeLoanRepaymentCycleDefinitionPK != null ? cbsSchemeLoanRepaymentCycleDefinitionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeLoanRepaymentCycleDefinition)) {
            return false;
        }
        CbsSchemeLoanRepaymentCycleDefinition other = (CbsSchemeLoanRepaymentCycleDefinition) object;
        if ((this.cbsSchemeLoanRepaymentCycleDefinitionPK == null && other.cbsSchemeLoanRepaymentCycleDefinitionPK != null) || (this.cbsSchemeLoanRepaymentCycleDefinitionPK != null && !this.cbsSchemeLoanRepaymentCycleDefinitionPK.equals(other.cbsSchemeLoanRepaymentCycleDefinitionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLoanRepaymentCycleDefinition[ cbsSchemeLoanRepaymentCycleDefinitionPK=" + cbsSchemeLoanRepaymentCycleDefinitionPK + " ]";
    }
    
}
