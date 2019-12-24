/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "hr_advert_dt")
@NamedQueries({@NamedQuery(name = "HrAdvertDt.findAll", query = "SELECT h FROM HrAdvertDt h"),
@NamedQuery(name = "HrAdvertDt.findByCompCode", query = "SELECT h FROM HrAdvertDt h WHERE h.hrAdvertDtPK.compCode = :compCode"),
@NamedQuery(name = "HrAdvertDt.findByAdvtCode", query = "SELECT h FROM HrAdvertDt h WHERE h.hrAdvertDtPK.advtCode = :advtCode"),
@NamedQuery(name = "HrAdvertDt.findByJobCode", query = "SELECT h FROM HrAdvertDt h WHERE h.hrAdvertDtPK.jobCode = :jobCode"),
@NamedQuery(name = "HrAdvertDt.findByMediaType", query = "SELECT h FROM HrAdvertDt h WHERE h.hrAdvertDtPK.mediaType = :mediaType"),
@NamedQuery(name = "HrAdvertDt.findByDesgCode", query = "SELECT h FROM HrAdvertDt h WHERE h.hrAdvertDtPK.desgCode = :desgCode"),
@NamedQuery(name = "HrAdvertDt.findByDeptCode", query = "SELECT h FROM HrAdvertDt h WHERE h.hrAdvertDtPK.deptCode = :deptCode"),
@NamedQuery(name = "HrAdvertDt.findByLocatCode", query = "SELECT h FROM HrAdvertDt h WHERE h.hrAdvertDtPK.locatCode = :locatCode"),
@NamedQuery(name = "HrAdvertDt.findByVaccant", query = "SELECT h FROM HrAdvertDt h WHERE h.vaccant = :vaccant"),
@NamedQuery(name = "HrAdvertDt.findByLastDate", query = "SELECT h FROM HrAdvertDt h WHERE h.lastDate = :lastDate"),
@NamedQuery(name = "HrAdvertDt.findByDefaultComp", query = "SELECT h FROM HrAdvertDt h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrAdvertDt.findByStatFlag", query = "SELECT h FROM HrAdvertDt h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrAdvertDt.findByStatUpFlag", query = "SELECT h FROM HrAdvertDt h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrAdvertDt.findByModDate", query = "SELECT h FROM HrAdvertDt h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrAdvertDt.findByAuthBy", query = "SELECT h FROM HrAdvertDt h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrAdvertDt.findMaxAdvtCodeByCompCode", query = "SELECT max(substring(h.hrAdvertDtPK.advtCode,3,10)) FROM HrAdvertDt h WHERE h.hrAdvertDtPK.compCode = :compCode"),
@NamedQuery(name = "HrAdvertDt.findByEnteredBy", query = "SELECT h FROM HrAdvertDt h WHERE h.enteredBy = :enteredBy")})
public class HrAdvertDt extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrAdvertDtPK hrAdvertDtPK;
    @Column(name = "VACCANT")
    private Integer vaccant;
    @Column(name = "LAST_DATE")
    private String lastDate;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DESG_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DEPT_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct1;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "LOCAT_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct2;

    public HrAdvertDt() {
    }

    public HrAdvertDt(HrAdvertDtPK hrAdvertDtPK) {
        this.hrAdvertDtPK = hrAdvertDtPK;
    }

    public HrAdvertDt(int compCode, String advtCode, String jobCode, char mediaType, String desgCode, String deptCode, String locatCode) {
        this.hrAdvertDtPK = new HrAdvertDtPK(compCode, advtCode, jobCode, mediaType, desgCode, deptCode, locatCode);
    }

    public HrAdvertDtPK getHrAdvertDtPK() {
        return hrAdvertDtPK;
    }

    public void setHrAdvertDtPK(HrAdvertDtPK hrAdvertDtPK) {
        this.hrAdvertDtPK = hrAdvertDtPK;
    }

    public Integer getVaccant() {
        return vaccant;
    }

    public void setVaccant(Integer vaccant) {
        this.vaccant = vaccant;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
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

    public HrMstStruct getHrMstStruct() {
        return hrMstStruct;
    }

    public void setHrMstStruct(HrMstStruct hrMstStruct) {
        this.hrMstStruct = hrMstStruct;
    }

    public HrMstStruct getHrMstStruct1() {
        return hrMstStruct1;
    }

    public void setHrMstStruct1(HrMstStruct hrMstStruct1) {
        this.hrMstStruct1 = hrMstStruct1;
    }

    public HrMstStruct getHrMstStruct2() {
        return hrMstStruct2;
    }

    public void setHrMstStruct2(HrMstStruct hrMstStruct2) {
        this.hrMstStruct2 = hrMstStruct2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrAdvertDtPK != null ? hrAdvertDtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAdvertDt)) {
            return false;
        }
        HrAdvertDt other = (HrAdvertDt) object;
        if ((this.hrAdvertDtPK == null && other.hrAdvertDtPK != null) || (this.hrAdvertDtPK != null && !this.hrAdvertDtPK.equals(other.hrAdvertDtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrAdvertDt[hrAdvertDtPK=" + hrAdvertDtPK + "]";
    }

}
