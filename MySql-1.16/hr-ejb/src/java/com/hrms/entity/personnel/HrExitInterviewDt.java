/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "hr_exit_interview_dt")
@NamedQueries({@NamedQuery(name = "HrExitInterviewDt.findAll", query = "SELECT h FROM HrExitInterviewDt h")})
public class HrExitInterviewDt extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrExitInterviewDtPK hrExitInterviewDtPK;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "ENTERED_BY")
    private String enteredBy;

    public HrExitInterviewDt() {
    }

    public HrExitInterviewDt(HrExitInterviewDtPK hrExitInterviewDtPK) {
        this.hrExitInterviewDtPK = hrExitInterviewDtPK;
    }

    public HrExitInterviewDt(int compCode, long empCode, String reasonCode) {
        this.hrExitInterviewDtPK = new HrExitInterviewDtPK(compCode, empCode, reasonCode);
    }

    public HrExitInterviewDtPK getHrExitInterviewDtPK() {
        return hrExitInterviewDtPK;
    }

    public void setHrExitInterviewDtPK(HrExitInterviewDtPK hrExitInterviewDtPK) {
        this.hrExitInterviewDtPK = hrExitInterviewDtPK;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrExitInterviewDtPK != null ? hrExitInterviewDtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrExitInterviewDt)) {
            return false;
        }
        HrExitInterviewDt other = (HrExitInterviewDt) object;
        if ((this.hrExitInterviewDtPK == null && other.hrExitInterviewDtPK != null) || (this.hrExitInterviewDtPK != null && !this.hrExitInterviewDtPK.equals(other.hrExitInterviewDtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.HrExitInterviewDt[hrExitInterviewDtPK=" + hrExitInterviewDtPK + "]";
    }

}
