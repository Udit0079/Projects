/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "investment_call_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentCallMaster.findAll", query = "SELECT i FROM InvestmentCallMaster i"),
    @NamedQuery(name = "InvestmentCallMaster.findByCtrlNo", query = "SELECT i FROM InvestmentCallMaster i WHERE i.ctrlNo = :ctrlNo"),
    @NamedQuery(name = "InvestmentCallMaster.findByDealDt", query = "SELECT i FROM InvestmentCallMaster i WHERE i.dealDt = :dealDt"),
    @NamedQuery(name = "InvestmentCallMaster.findByCompletionDt", query = "SELECT i FROM InvestmentCallMaster i WHERE i.completionDt = :completionDt"),
    @NamedQuery(name = "InvestmentCallMaster.findByRoi", query = "SELECT i FROM InvestmentCallMaster i WHERE i.roi = :roi"),
    @NamedQuery(name = "InvestmentCallMaster.findByAmt", query = "SELECT i FROM InvestmentCallMaster i WHERE i.amt = :amt"),
    @NamedQuery(name = "InvestmentCallMaster.findByNoOfDays", query = "SELECT i FROM InvestmentCallMaster i WHERE i.noOfDays = :noOfDays"),
    @NamedQuery(name = "InvestmentCallMaster.findByAuth", query = "SELECT i FROM InvestmentCallMaster i WHERE i.auth = :auth"),
    @NamedQuery(name = "InvestmentCallMaster.findByAuthBy", query = "SELECT i FROM InvestmentCallMaster i WHERE i.authBy = :authBy"),
    @NamedQuery(name = "InvestmentCallMaster.findByEnterBy", query = "SELECT i FROM InvestmentCallMaster i WHERE i.enterBy = :enterBy"),
    @NamedQuery(name = "InvestmentCallMaster.findByLastUpdateBy", query = "SELECT i FROM InvestmentCallMaster i WHERE i.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "InvestmentCallMaster.findByLastUpdateDt", query = "SELECT i FROM InvestmentCallMaster i WHERE i.lastUpdateDt = :lastUpdateDt"),
    @NamedQuery(name = "InvestmentCallMaster.findByDetails", query = "SELECT i FROM InvestmentCallMaster i WHERE i.details = :details"),
    @NamedQuery(name = "InvestmentCallMaster.findByTranTime", query = "SELECT i FROM InvestmentCallMaster i WHERE i.tranTime = :tranTime"),
    @NamedQuery(name = "InvestmentCallMaster.findByStatus", query = "SELECT i FROM InvestmentCallMaster i WHERE i.status = :status"),
    @NamedQuery(name = "InvestmentCallMaster.findByIntAmt", query = "SELECT i FROM InvestmentCallMaster i WHERE i.intAmt = :intAmt")})

public class InvestmentCallMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRL_NO")
    private Long ctrlNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DEAL_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dealDt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPLETION_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROI")
    private double roi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMT")
    private double amt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_OF_DAYS")
    private int noOfDays;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "AUTH")
    private String auth;
    @Size(max = 50)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Size(max = 50)
    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;
    @Column(name = "LAST_UPDATE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDt;
    @Size(max = 250)
    @Column(name = "DETAILS")
    private String details;
    @Column(name = "TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;
    @Size(max = 12)
    @Column(name = "STATUS")
    private String status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INT_AMT")
    private Double intAmt;
    @Size(max = 12)
    @Column(name = "DR_GLHEAD")
    private String drGlHead;

    public InvestmentCallMaster() {
    }

    public InvestmentCallMaster(Long ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public InvestmentCallMaster(Long ctrlNo, Date dealDt, Date completionDt, double roi, double amt, int noOfDays, String auth, String enterBy) {
        this.ctrlNo = ctrlNo;
        this.dealDt = dealDt;
        this.completionDt = completionDt;
        this.roi = roi;
        this.amt = amt;
        this.noOfDays = noOfDays;
        this.auth = auth;
        this.enterBy = enterBy;
    }

    public Long getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(Long ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public Date getDealDt() {
        return dealDt;
    }

    public void setDealDt(Date dealDt) {
        this.dealDt = dealDt;
    }

    public Date getCompletionDt() {
        return completionDt;
    }

    public void setCompletionDt(Date completionDt) {
        this.completionDt = completionDt;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(Date lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDrGlHead() {
        return drGlHead;
    }

    public void setDrGlHead(String drGlHead) {
        this.drGlHead = drGlHead;
    }

    public Double getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(Double intAmt) {
        this.intAmt = intAmt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctrlNo != null ? ctrlNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentCallMaster)) {
            return false;
        }
        InvestmentCallMaster other = (InvestmentCallMaster) object;
        if ((this.ctrlNo == null && other.ctrlNo != null) || (this.ctrlNo != null && !this.ctrlNo.equals(other.ctrlNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentCallMaster[ ctrlNo=" + ctrlNo + " ]";
    }
}
