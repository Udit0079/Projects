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
 * @author Administrator
 */
@Entity
@Table(name = "hr_mst_struct")
@NamedQueries({
    @NamedQuery(name = "HrMstStruct.findAll", query = "SELECT h FROM HrMstStruct h"),
    @NamedQuery(name = "HrMstStruct.findByCompCode", query = "SELECT h FROM HrMstStruct h WHERE h.hrMstStructPK.compCode = :compCode"),
    @NamedQuery(name = "HrMstStruct.findByStructCode", query = "SELECT h FROM HrMstStruct h WHERE h.hrMstStructPK.structCode = :structCode"),
    @NamedQuery(name = "HrMstStruct.findByDescription", query = "SELECT h FROM HrMstStruct h WHERE h.description = :description"),
    @NamedQuery(name = "HrMstStruct.findByDefaultComp", query = "SELECT h FROM HrMstStruct h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrMstStruct.findByStatFlag", query = "SELECT h FROM HrMstStruct h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrMstStruct.findByStatUpFlag", query = "SELECT h FROM HrMstStruct h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrMstStruct.findByModDate", query = "SELECT h FROM HrMstStruct h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrMstStruct.findByAuthBy", query = "SELECT h FROM HrMstStruct h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrMstStruct.findByEnteredBy", query = "SELECT h FROM HrMstStruct h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrMstStruct.findByStructCodeAndCompCode", query = "SELECT h FROM HrMstStruct h WHERE h.hrMstStructPK.compCode = :compCode "
        + "and h.hrMstStructPK.structCode like :structCode order by h.description")
})
public class HrMstStruct extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstStructPK hrMstStructPK;
    @Basic(optional = false)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Basic(optional = false)
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Basic(optional = false)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;

    public HrMstStruct() {
    }

    public HrMstStruct(HrMstStructPK hrMstStructPK) {
        this.hrMstStructPK = hrMstStructPK;
    }

    public HrMstStruct(HrMstStructPK hrMstStructPK, String description, Date modDate, String authBy, String enteredBy) {
        this.hrMstStructPK = hrMstStructPK;
        this.description = description;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrMstStruct(int compCode, String structCode) {
        this.hrMstStructPK = new HrMstStructPK(compCode, structCode);
    }

    public HrMstStructPK getHrMstStructPK() {
        return hrMstStructPK;
    }

    public void setHrMstStructPK(HrMstStructPK hrMstStructPK) {
        this.hrMstStructPK = hrMstStructPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (hrMstStructPK != null ? hrMstStructPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstStruct)) {
            return false;
        }
        HrMstStruct other = (HrMstStruct) object;
        if ((this.hrMstStructPK == null && other.hrMstStructPK != null) || (this.hrMstStructPK != null && !this.hrMstStructPK.equals(other.hrMstStructPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrMstStruct[hrMstStructPK=" + hrMstStructPK + "]";
    }
}
