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
 * @author Administrator
 */
@Entity
@Table(name = "hr_mst_dept_subdept")
@NamedQueries({
    @NamedQuery(name = "HrMstDeptSubdept.findAll", query = "SELECT h FROM HrMstDeptSubdept h"),
    @NamedQuery(name = "HrMstDeptSubdept.findByCompCodeDeptCode", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.hrMstDeptSubdeptPK.compCode = :compCode AND h.hrMstDeptSubdeptPK.deptCode = :deptCode"),
    @NamedQuery(name = "HrMstDeptSubdept.findByCompCodeDeptCodeSubDeptCode", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.hrMstDeptSubdeptPK.compCode = :compCode AND h.hrMstDeptSubdeptPK.deptCode = :deptCode AND h.hrMstDeptSubdeptPK.subDeptCode = :subDeptCode"),
    @NamedQuery(name = "HrMstDeptSubdept.findByCompCode", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.hrMstDeptSubdeptPK.compCode = :compCode"),
    @NamedQuery(name = "HrMstDeptSubdept.findByDeptCode", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.hrMstDeptSubdeptPK.deptCode = :deptCode"),
    @NamedQuery(name = "HrMstDeptSubdept.findBySubDeptCode", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.hrMstDeptSubdeptPK.subDeptCode = :subDeptCode"),
    @NamedQuery(name = "HrMstDeptSubdept.findByStatFlag", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrMstDeptSubdept.findByStatUpFlag", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrMstDeptSubdept.findByModDate", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrMstDeptSubdept.findByDefaultComp", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrMstDeptSubdept.findByAuthBy", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrMstDeptSubdept.findByEnteredBy", query = "SELECT h FROM HrMstDeptSubdept h WHERE h.enteredBy = :enteredBy")
})
public class HrMstDeptSubdept extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstDeptSubdeptPK hrMstDeptSubdeptPK;
    @Basic(optional = false)
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Basic(optional = false)
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Basic(optional = false)
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Basic(optional = false)
    @Column(name = "DEFAULT_COMP")
    private int defaultComp;
    @Basic(optional = false)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DEPT_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "SUB_DEPT_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct1;

    public HrMstDeptSubdept() {
    }

    public HrMstDeptSubdept(HrMstDeptSubdeptPK hrMstDeptSubdeptPK) {
        this.hrMstDeptSubdeptPK = hrMstDeptSubdeptPK;
    }

    public HrMstDeptSubdept(HrMstDeptSubdeptPK hrMstDeptSubdeptPK, String statFlag, String statUpFlag, Date modDate, int defaultComp, String authBy, String enteredBy) {
        this.hrMstDeptSubdeptPK = hrMstDeptSubdeptPK;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.defaultComp = defaultComp;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrMstDeptSubdept(int compCode, String deptCode, String subDeptCode) {
        this.hrMstDeptSubdeptPK = new HrMstDeptSubdeptPK(compCode, deptCode, subDeptCode);
    }

    public HrMstDeptSubdeptPK getHrMstDeptSubdeptPK() {
        return hrMstDeptSubdeptPK;
    }

    public void setHrMstDeptSubdeptPK(HrMstDeptSubdeptPK hrMstDeptSubdeptPK) {
        this.hrMstDeptSubdeptPK = hrMstDeptSubdeptPK;
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

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrMstDeptSubdeptPK != null ? hrMstDeptSubdeptPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstDeptSubdept)) {
            return false;
        }
        HrMstDeptSubdept other = (HrMstDeptSubdept) object;
        if ((this.hrMstDeptSubdeptPK == null && other.hrMstDeptSubdeptPK != null) || (this.hrMstDeptSubdeptPK != null && !this.hrMstDeptSubdeptPK.equals(other.hrMstDeptSubdeptPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrMstDeptSubdept[hrMstDeptSubdeptPK=" + hrMstDeptSubdeptPK + "]";
    }
}
