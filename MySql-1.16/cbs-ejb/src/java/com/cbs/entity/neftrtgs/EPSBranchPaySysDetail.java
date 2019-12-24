/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "eps_branchpaysysdetail")
@NamedQueries({
    @NamedQuery(name = "EPSBranchPaySysDetail.findAll", query = "SELECT e FROM EPSBranchPaySysDetail e"),
    @NamedQuery(name = "EPSBranchPaySysDetail.findByBankCode", query = "SELECT e FROM EPSBranchPaySysDetail e WHERE e.bankCode = :bankCode"),
    @NamedQuery(name = "EPSBranchPaySysDetail.findByBranchCode", query = "SELECT e FROM EPSBranchPaySysDetail e WHERE e.branchCode = :branchCode"),
    @NamedQuery(name = "EPSBranchPaySysDetail.findByPaySysId", query = "SELECT e FROM EPSBranchPaySysDetail e WHERE e.paySysId = :paySysId"),
    @NamedQuery(name = "EPSBranchPaySysDetail.findByChargesFlag", query = "SELECT e FROM EPSBranchPaySysDetail e WHERE e.chargesFlag = :chargesFlag"),
    @NamedQuery(name = "EPSBranchPaySysDetail.findByDelStatusFlag", query = "SELECT e FROM EPSBranchPaySysDetail e WHERE e.delStatusFlag = :delStatusFlag"),
    @NamedQuery(name = "EPSBranchPaySysDetail.findByEnableFlag", query = "SELECT e FROM EPSBranchPaySysDetail e WHERE e.enableFlag = :enableFlag")})
public class EPSBranchPaySysDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "BankCode")
    private String bankCode;
    @Basic(optional = false)
    @Column(name = "BranchCode")
    private String branchCode;
    @Id
    @Basic(optional = false)
    @Column(name = "PaySysId")
    private String paySysId;
    @Column(name = "ChargesFlag")
    private String chargesFlag;
    @Column(name = "DelStatusFlag")
    private String delStatusFlag;
    @Basic(optional = false)
    @Column(name = "EnableFlag")
    private String enableFlag;

    public EPSBranchPaySysDetail() {
    }

    public EPSBranchPaySysDetail(String paySysId) {
        this.paySysId = paySysId;
    }

    public EPSBranchPaySysDetail(String paySysId, String bankCode, String branchCode, String enableFlag) {
        this.paySysId = paySysId;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.enableFlag = enableFlag;
    }

    public EPSBranchPaySysDetail(String paySysId, String bankCode, String branchCode, String enableFlag, String chargesFlag, String delStatusFlag) {
        this.paySysId = paySysId;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.enableFlag = enableFlag;
        this.chargesFlag = chargesFlag;
        this.delStatusFlag = delStatusFlag;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getPaySysId() {
        return paySysId;
    }

    public void setPaySysId(String paySysId) {
        this.paySysId = paySysId;
    }

    public String getChargesFlag() {
        return chargesFlag;
    }

    public void setChargesFlag(String chargesFlag) {
        this.chargesFlag = chargesFlag;
    }

    public String getDelStatusFlag() {
        return delStatusFlag;
    }

    public void setDelStatusFlag(String delStatusFlag) {
        this.delStatusFlag = delStatusFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paySysId != null ? paySysId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSBranchPaySysDetail)) {
            return false;
        }
        EPSBranchPaySysDetail other = (EPSBranchPaySysDetail) object;
        if ((this.paySysId == null && other.paySysId != null) || (this.paySysId != null && !this.paySysId.equals(other.paySysId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSBranchPaySysDetail[paySysId=" + paySysId + "]";
    }
}
