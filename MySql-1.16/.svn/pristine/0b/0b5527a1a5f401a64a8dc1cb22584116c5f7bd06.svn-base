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
@Table(name = "eps_nodalbranchdetail")
@NamedQueries({
    @NamedQuery(name = "EPSNodalBranchDetail.findAll", query = "SELECT e FROM EPSNodalBranchDetail e"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByBankCode", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.bankCode = :bankCode"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByBranchCode", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.branchCode = :branchCode"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByPaySysId", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.paySysId = :paySysId"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByOutwardPoolAccId", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.outwardPoolAccId = :outwardPoolAccId"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByInwardAccPlaceHolder", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.inwardAccPlaceHolder = :inwardAccPlaceHolder"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByOutwardAccPlaceHolder", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.outwardAccPlaceHolder = :outwardAccPlaceHolder"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByCommPaidAccPlaceHolder", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.commPaidAccPlaceHolder = :commPaidAccPlaceHolder"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByInwardSundryPlaceHolder", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.inwardSundryPlaceHolder = :inwardSundryPlaceHolder"),
    @NamedQuery(name = "EPSNodalBranchDetail.findByCommRecvAccPlaceHolder", query = "SELECT e FROM EPSNodalBranchDetail e WHERE e.commRecvAccPlaceHolder = :commRecvAccPlaceHolder")})
public class EPSNodalBranchDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "BankCode")
    private String bankCode;
    @Basic(optional = false)
    @Column(name = "BranchCode")
    private String branchCode;
    @Basic(optional = false)
    @Column(name = "PaySysId")
    private String paySysId;
    @Id
    @Basic(optional = false)
    @Column(name = "OutwardPoolAccId")
    private String outwardPoolAccId;
    @Column(name = "InwardAccPlaceHolder")
    private String inwardAccPlaceHolder;
    @Column(name = "OutwardAccPlaceHolder")
    private String outwardAccPlaceHolder;
    @Column(name = "CommPaidAccPlaceHolder")
    private String commPaidAccPlaceHolder;
    @Column(name = "InwardSundryPlaceHolder")
    private String inwardSundryPlaceHolder;
    @Column(name = "CommRecvAccPlaceHolder")
    private String commRecvAccPlaceHolder;

    public EPSNodalBranchDetail() {
    }

    public EPSNodalBranchDetail(String outwardPoolAccId) {
        this.outwardPoolAccId = outwardPoolAccId;
    }

    public EPSNodalBranchDetail(String outwardPoolAccId, String bankCode, String branchCode, String paySysId) {
        this.outwardPoolAccId = outwardPoolAccId;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.paySysId = paySysId;
    }

    public EPSNodalBranchDetail(String outwardPoolAccId, String bankCode, String branchCode, String paySysId, String inwardAccPlaceHolder, String outwardAccPlaceHolder, String commPaidAccPlaceHolder, String inwardSundryPlaceHolder, String commRecvAccPlaceHolder) {
        this.outwardPoolAccId = outwardPoolAccId;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.paySysId = paySysId;
        this.inwardAccPlaceHolder = inwardAccPlaceHolder;
        this.outwardAccPlaceHolder = outwardAccPlaceHolder;
        this.commPaidAccPlaceHolder = commPaidAccPlaceHolder;
        this.inwardSundryPlaceHolder = inwardSundryPlaceHolder;
        this.commRecvAccPlaceHolder = commRecvAccPlaceHolder;
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

    public String getOutwardPoolAccId() {
        return outwardPoolAccId;
    }

    public void setOutwardPoolAccId(String outwardPoolAccId) {
        this.outwardPoolAccId = outwardPoolAccId;
    }

    public String getInwardAccPlaceHolder() {
        return inwardAccPlaceHolder;
    }

    public void setInwardAccPlaceHolder(String inwardAccPlaceHolder) {
        this.inwardAccPlaceHolder = inwardAccPlaceHolder;
    }

    public String getOutwardAccPlaceHolder() {
        return outwardAccPlaceHolder;
    }

    public void setOutwardAccPlaceHolder(String outwardAccPlaceHolder) {
        this.outwardAccPlaceHolder = outwardAccPlaceHolder;
    }

    public String getCommPaidAccPlaceHolder() {
        return commPaidAccPlaceHolder;
    }

    public void setCommPaidAccPlaceHolder(String commPaidAccPlaceHolder) {
        this.commPaidAccPlaceHolder = commPaidAccPlaceHolder;
    }

    public String getInwardSundryPlaceHolder() {
        return inwardSundryPlaceHolder;
    }

    public void setInwardSundryPlaceHolder(String inwardSundryPlaceHolder) {
        this.inwardSundryPlaceHolder = inwardSundryPlaceHolder;
    }

    public String getCommRecvAccPlaceHolder() {
        return commRecvAccPlaceHolder;
    }

    public void setCommRecvAccPlaceHolder(String commRecvAccPlaceHolder) {
        this.commRecvAccPlaceHolder = commRecvAccPlaceHolder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (outwardPoolAccId != null ? outwardPoolAccId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSNodalBranchDetail)) {
            return false;
        }
        EPSNodalBranchDetail other = (EPSNodalBranchDetail) object;
        if ((this.outwardPoolAccId == null && other.outwardPoolAccId != null) || (this.outwardPoolAccId != null && !this.outwardPoolAccId.equals(other.outwardPoolAccId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSNodalBranchDetail[outwardPoolAccId=" + outwardPoolAccId + "]";
    }
}
