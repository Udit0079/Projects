/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
 * @author root
 */
@Entity
@Table(name = "hr_mst_institute")
@NamedQueries({@NamedQuery(name = "HrMstInstitute.findAll", query = "SELECT h FROM HrMstInstitute h"), @NamedQuery(name = "HrMstInstitute.findByCompCode", query = "SELECT h FROM HrMstInstitute h WHERE h.hrMstInstitutePK.compCode = :compCode"), @NamedQuery(name = "HrMstInstitute.findByInstCode", query = "SELECT h FROM HrMstInstitute h WHERE h.hrMstInstitutePK.instCode = :instCode"), @NamedQuery(name = "HrMstInstitute.findByInstName", query = "SELECT h FROM HrMstInstitute h WHERE h.instName = :instName"), @NamedQuery(name = "HrMstInstitute.findByInstAdd", query = "SELECT h FROM HrMstInstitute h WHERE h.instAdd = :instAdd"), @NamedQuery(name = "HrMstInstitute.findByContPers", query = "SELECT h FROM HrMstInstitute h WHERE h.contPers = :contPers"), @NamedQuery(name = "HrMstInstitute.findByContNo", query = "SELECT h FROM HrMstInstitute h WHERE h.contNo = :contNo"), @NamedQuery(name = "HrMstInstitute.findByDefaultComp", query = "SELECT h FROM HrMstInstitute h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrMstInstitute.findByStatFlag", query = "SELECT h FROM HrMstInstitute h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrMstInstitute.findByStatUpFlag", query = "SELECT h FROM HrMstInstitute h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrMstInstitute.findByModDate", query = "SELECT h FROM HrMstInstitute h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrMstInstitute.findByAuthBy", query = "SELECT h FROM HrMstInstitute h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrMstInstitute.findByEnteredBy", query = "SELECT h FROM HrMstInstitute h WHERE h.enteredBy = :enteredBy")})
public class HrMstInstitute extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstInstitutePK hrMstInstitutePK;
    @Basic(optional = false)
    @Column(name = "INST_NAME")
    private String instName;
    @Column(name = "INST_ADD")
    private String instAdd;
    @Column(name = "CONT_PERS")
    private String contPers;
    @Column(name = "CONT_NO")
    private String contNo;
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

    public HrMstInstitute() {
    }

    public HrMstInstitute(HrMstInstitutePK hrMstInstitutePK) {
        this.hrMstInstitutePK = hrMstInstitutePK;
    }

    public HrMstInstitute(HrMstInstitutePK hrMstInstitutePK, String instName) {
        this.hrMstInstitutePK = hrMstInstitutePK;
        this.instName = instName;
    }

    public HrMstInstitute(int compCode, String instCode) {
        this.hrMstInstitutePK = new HrMstInstitutePK(compCode, instCode);
    }

    public HrMstInstitutePK getHrMstInstitutePK() {
        return hrMstInstitutePK;
    }

    public void setHrMstInstitutePK(HrMstInstitutePK hrMstInstitutePK) {
        this.hrMstInstitutePK = hrMstInstitutePK;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstAdd() {
        return instAdd;
    }

    public void setInstAdd(String instAdd) {
        this.instAdd = instAdd;
    }

    public String getContPers() {
        return contPers;
    }

    public void setContPers(String contPers) {
        this.contPers = contPers;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
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
        hash += (hrMstInstitutePK != null ? hrMstInstitutePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstInstitute)) {
            return false;
        }
        HrMstInstitute other = (HrMstInstitute) object;
        if ((this.hrMstInstitutePK == null && other.hrMstInstitutePK != null) || (this.hrMstInstitutePK != null && !this.hrMstInstitutePK.equals(other.hrMstInstitutePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrMstInstitute[hrMstInstitutePK=" + hrMstInstitutePK + "]";
    }

}
