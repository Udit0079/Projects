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
@Table(name = "cbs_scheme_deposit_flow_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findAll", query = "SELECT c FROM CbsSchemeDepositFlowDetails c"),
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeDepositFlowDetails c WHERE c.cbsSchemeDepositFlowDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeDepositFlowDetails c WHERE c.cbsSchemeDepositFlowDetailsPK.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findByFlowCode", query = "SELECT c FROM CbsSchemeDepositFlowDetails c WHERE c.cbsSchemeDepositFlowDetailsPK.flowCode = :flowCode"),
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findByFlowFreqMonths", query = "SELECT c FROM CbsSchemeDepositFlowDetails c WHERE c.flowFreqMonths = :flowFreqMonths"),
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findByFlowFreqDays", query = "SELECT c FROM CbsSchemeDepositFlowDetails c WHERE c.flowFreqDays = :flowFreqDays"),
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findByFlowPeriodBegin", query = "SELECT c FROM CbsSchemeDepositFlowDetails c WHERE c.flowPeriodBegin = :flowPeriodBegin"),
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findByFlowPeriodEnd", query = "SELECT c FROM CbsSchemeDepositFlowDetails c WHERE c.flowPeriodEnd = :flowPeriodEnd"),
    @NamedQuery(name = "CbsSchemeDepositFlowDetails.findByDelFlag", query = "SELECT c FROM CbsSchemeDepositFlowDetails c WHERE c.delFlag = :delFlag")})
public class CbsSchemeDepositFlowDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeDepositFlowDetailsPK cbsSchemeDepositFlowDetailsPK;
    @Size(max = 3)
    @Column(name = "FLOW_FREQ_MONTHS")
    private String flowFreqMonths;
    @Size(max = 3)
    @Column(name = "FLOW_FREQ_DAYS")
    private String flowFreqDays;
    @Size(max = 1)
    @Column(name = "FLOW_PERIOD_BEGIN")
    private String flowPeriodBegin;
    @Size(max = 1)
    @Column(name = "FLOW_PERIOD_END")
    private String flowPeriodEnd;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeDepositFlowDetails() {
    }

    public CbsSchemeDepositFlowDetails(CbsSchemeDepositFlowDetailsPK cbsSchemeDepositFlowDetailsPK) {
        this.cbsSchemeDepositFlowDetailsPK = cbsSchemeDepositFlowDetailsPK;
    }

    public CbsSchemeDepositFlowDetails(String schemeCode, String schemeType, String flowCode) {
        this.cbsSchemeDepositFlowDetailsPK = new CbsSchemeDepositFlowDetailsPK(schemeCode, schemeType, flowCode);
    }

    public CbsSchemeDepositFlowDetailsPK getCbsSchemeDepositFlowDetailsPK() {
        return cbsSchemeDepositFlowDetailsPK;
    }

    public void setCbsSchemeDepositFlowDetailsPK(CbsSchemeDepositFlowDetailsPK cbsSchemeDepositFlowDetailsPK) {
        this.cbsSchemeDepositFlowDetailsPK = cbsSchemeDepositFlowDetailsPK;
    }

    public String getFlowFreqMonths() {
        return flowFreqMonths;
    }

    public void setFlowFreqMonths(String flowFreqMonths) {
        this.flowFreqMonths = flowFreqMonths;
    }

    public String getFlowFreqDays() {
        return flowFreqDays;
    }

    public void setFlowFreqDays(String flowFreqDays) {
        this.flowFreqDays = flowFreqDays;
    }

    public String getFlowPeriodBegin() {
        return flowPeriodBegin;
    }

    public void setFlowPeriodBegin(String flowPeriodBegin) {
        this.flowPeriodBegin = flowPeriodBegin;
    }

    public String getFlowPeriodEnd() {
        return flowPeriodEnd;
    }

    public void setFlowPeriodEnd(String flowPeriodEnd) {
        this.flowPeriodEnd = flowPeriodEnd;
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
        hash += (cbsSchemeDepositFlowDetailsPK != null ? cbsSchemeDepositFlowDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeDepositFlowDetails)) {
            return false;
        }
        CbsSchemeDepositFlowDetails other = (CbsSchemeDepositFlowDetails) object;
        if ((this.cbsSchemeDepositFlowDetailsPK == null && other.cbsSchemeDepositFlowDetailsPK != null) || (this.cbsSchemeDepositFlowDetailsPK != null && !this.cbsSchemeDepositFlowDetailsPK.equals(other.cbsSchemeDepositFlowDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeDepositFlowDetails[ cbsSchemeDepositFlowDetailsPK=" + cbsSchemeDepositFlowDetailsPK + " ]";
    }
    
}
