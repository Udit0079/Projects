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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "investment_security_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentSecurityMaster.findAll", query = "SELECT i FROM InvestmentSecurityMaster i"),
    @NamedQuery(name = "InvestmentSecurityMaster.findBySno", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.sno = :sno"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByDt", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.dt = :dt"),
    @NamedQuery(name = "InvestmentSecurityMaster.findBySecurityName", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.securityName = :securityName"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByMaturityYear", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.maturityYear = :maturityYear"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByIntPayableFirstDate", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.intPayableFirstDate = :intPayableFirstDate"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByIntPayableSecondDate", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.intPayableSecondDate = :intPayableSecondDate"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByRoi", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.roi = :roi"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByDelFlag", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.delFlag = :delFlag"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByEnterBy", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.enterBy = :enterBy"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByAuthBy", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.authBy = :authBy"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByAuth", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.auth = :auth"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByLastUpdateBy", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "InvestmentSecurityMaster.findByTranTime", query = "SELECT i FROM InvestmentSecurityMaster i WHERE i.tranTime = :tranTime")})
public class InvestmentSecurityMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SNO")
    private Long sno;
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SECURITY_NAME")
    private String securityName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "MATURITY_YEAR")
    private String maturityYear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "INT_PAYABLE_FIRST_DATE")
    private String intPayableFirstDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "INT_PAYABLE_SECOND_DATE")
    private String intPayableSecondDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROI")
    private double roi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Size(max = 20)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "AUTH")
    private String auth;
    @Size(max = 20)
    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;
    @Column(name = "TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;

    public InvestmentSecurityMaster() {
    }

    public InvestmentSecurityMaster(Long sno) {
        this.sno = sno;
    }

    public InvestmentSecurityMaster(Long sno, String securityName, String maturityYear, String intPayableFirstDate, String intPayableSecondDate, double roi, String delFlag, String enterBy, String auth) {
        this.sno = sno;
        this.securityName = securityName;
        this.maturityYear = maturityYear;
        this.intPayableFirstDate = intPayableFirstDate;
        this.intPayableSecondDate = intPayableSecondDate;
        this.roi = roi;
        this.delFlag = delFlag;
        this.enterBy = enterBy;
        this.auth = auth;
    }

    public InvestmentSecurityMaster(Date dt, String securityName, String maturityYear, String intPayableFirstDate, 
            String intPayableSecondDate, double roi, String delFlag, String enterBy, String auth, String authBy, 
            String lastUpdateBy, Date trantime) {
        this.dt = dt;
        this.securityName = securityName;
        this.maturityYear = maturityYear;
        this.intPayableFirstDate = intPayableFirstDate;
        this.intPayableSecondDate = intPayableSecondDate;
        this.roi = roi;
        this.delFlag = delFlag;
        this.enterBy = enterBy;
        this.auth = auth;
        this.authBy = authBy;
        this.lastUpdateBy = lastUpdateBy;
        this.tranTime = trantime;
    }
    
    public InvestmentSecurityMaster(Long sno,Date dt, String securityName, String maturityYear, String intPayableFirstDate, 
            String intPayableSecondDate, double roi, String delFlag, String enterBy, String auth, String authBy, 
            String lastUpdateBy, Date trantime) {
        this.sno = sno;
        this.dt = dt;
        this.securityName = securityName;
        this.maturityYear = maturityYear;
        this.intPayableFirstDate = intPayableFirstDate;
        this.intPayableSecondDate = intPayableSecondDate;
        this.roi = roi;
        this.delFlag = delFlag;
        this.enterBy = enterBy;
        this.auth = auth;
        this.authBy = authBy;
        this.lastUpdateBy = lastUpdateBy;
        this.tranTime = trantime;
    }

    public Long getSno() {
        return sno;
    }

    public void setSno(Long sno) {
        this.sno = sno;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getMaturityYear() {
        return maturityYear;
    }

    public void setMaturityYear(String maturityYear) {
        this.maturityYear = maturityYear;
    }

    public String getIntPayableFirstDate() {
        return intPayableFirstDate;
    }

    public void setIntPayableFirstDate(String intPayableFirstDate) {
        this.intPayableFirstDate = intPayableFirstDate;
    }

    public String getIntPayableSecondDate() {
        return intPayableSecondDate;
    }

    public void setIntPayableSecondDate(String intPayableSecondDate) {
        this.intPayableSecondDate = intPayableSecondDate;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sno != null ? sno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentSecurityMaster)) {
            return false;
        }
        InvestmentSecurityMaster other = (InvestmentSecurityMaster) object;
        if ((this.sno == null && other.sno != null) || (this.sno != null && !this.sno.equals(other.sno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentSecurityMaster[ sno=" + sno + " ]";
    }
}
