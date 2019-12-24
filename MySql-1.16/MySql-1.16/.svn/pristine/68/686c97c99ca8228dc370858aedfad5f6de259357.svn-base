/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

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
@Table(name = "hr_temp_staff")
@NamedQueries({@NamedQuery(name = "HrTempStaff.findAll", query = "SELECT h FROM HrTempStaff h"),
@NamedQuery(name = "HrTempStaff.findByCompCode", query = "SELECT h FROM HrTempStaff h WHERE h.hrTempStaffPK.compCode = :compCode"), @NamedQuery(name = "HrTempStaff.findByArNo", query = "SELECT h FROM HrTempStaff h WHERE h.hrTempStaffPK.arNo = :arNo"), @NamedQuery(name = "HrTempStaff.findByContCode", query = "SELECT h FROM HrTempStaff h WHERE h.contCode = :contCode"), @NamedQuery(name = "HrTempStaff.findByEmpName", query = "SELECT h FROM HrTempStaff h WHERE h.empName = :empName"), @NamedQuery(name = "HrTempStaff.findByDesgCode", query = "SELECT h FROM HrTempStaff h WHERE h.desgCode = :desgCode"), @NamedQuery(name = "HrTempStaff.findByZone", query = "SELECT h FROM HrTempStaff h WHERE h.zone = :zone"), @NamedQuery(name = "HrTempStaff.findByLocatCode", query = "SELECT h FROM HrTempStaff h WHERE h.locatCode = :locatCode"), @NamedQuery(name = "HrTempStaff.findByFromDate", query = "SELECT h FROM HrTempStaff h WHERE h.fromDate = :fromDate"), @NamedQuery(name = "HrTempStaff.findByToDate", query = "SELECT h FROM HrTempStaff h WHERE h.toDate = :toDate"), @NamedQuery(name = "HrTempStaff.findByWages", query = "SELECT h FROM HrTempStaff h WHERE h.wages = :wages"), @NamedQuery(name = "HrTempStaff.findByActive", query = "SELECT h FROM HrTempStaff h WHERE h.active = :active"), @NamedQuery(name = "HrTempStaff.findByDefaultComp", query = "SELECT h FROM HrTempStaff h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrTempStaff.findByStatFlag", query = "SELECT h FROM HrTempStaff h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrTempStaff.findByStatUpFlag", query = "SELECT h FROM HrTempStaff h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrTempStaff.findByModDate", query = "SELECT h FROM HrTempStaff h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrTempStaff.findByAuthBy", query = "SELECT h FROM HrTempStaff h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrTempStaff.findByEnteredBy", query = "SELECT h FROM HrTempStaff h WHERE h.enteredBy = :enteredBy")})
public class HrTempStaff extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrTempStaffPK hrTempStaffPK;
    @Basic(optional = false)
    @Column(name = "CONT_CODE")
    private String contCode;
    @Basic(optional = false)
    @Column(name = "EMP_NAME")
    private String empName;
    @Column(name = "DESG_CODE")
    private String desgCode;
    @Column(name = "ZONE")
    private String zone;
    @Column(name = "LOCAT_CODE")
    private String locatCode;
    @Column(name = "FROM_DATE")
    private String fromDate;
    @Column(name = "TO_DATE")
    private String toDate;
    @Column(name = "WAGES")
    private Double wages;
    @Column(name = "ACTIVE")
    private Character active;
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

    public HrTempStaff() {
    }

    public HrTempStaff(HrTempStaffPK hrTempStaffPK) {
        this.hrTempStaffPK = hrTempStaffPK;
    }

    public HrTempStaff(HrTempStaffPK hrTempStaffPK, String contCode, String empName) {
        this.hrTempStaffPK = hrTempStaffPK;
        this.contCode = contCode;
        this.empName = empName;
    }

    public HrTempStaff(int compCode, String arNo) {
        this.hrTempStaffPK = new HrTempStaffPK(compCode, arNo);
    }

    public HrTempStaffPK getHrTempStaffPK() {
        return hrTempStaffPK;
    }

    public void setHrTempStaffPK(HrTempStaffPK hrTempStaffPK) {
        this.hrTempStaffPK = hrTempStaffPK;
    }

    public String getContCode() {
        return contCode;
    }

    public void setContCode(String contCode) {
        this.contCode = contCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getLocatCode() {
        return locatCode;
    }

    public void setLocatCode(String locatCode) {
        this.locatCode = locatCode;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Double getWages() {
        return wages;
    }

    public void setWages(Double wages) {
        this.wages = wages;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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
        hash += (hrTempStaffPK != null ? hrTempStaffPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTempStaff)) {
            return false;
        }
        HrTempStaff other = (HrTempStaff) object;
        if ((this.hrTempStaffPK == null && other.hrTempStaffPK != null) || (this.hrTempStaffPK != null && !this.hrTempStaffPK.equals(other.hrTempStaffPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrTempStaff[hrTempStaffPK=" + hrTempStaffPK + "]";
    }

}
