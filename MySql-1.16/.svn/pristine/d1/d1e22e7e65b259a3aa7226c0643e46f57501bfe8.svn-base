/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.payroll;


import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
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
 * @author root
 */
@Entity
@Table(name = "hr_shift_map")
@NamedQueries({@NamedQuery(name = "HrShiftMap.findAll", query = "SELECT h FROM HrShiftMap h"),
@NamedQuery(name = "HrShiftMap.findByCompCode", query = "SELECT h FROM HrShiftMap h WHERE h.hrShiftMapPK.compCode = :compCode"),
@NamedQuery(name = "HrShiftMap.findByEmpCode", query = "SELECT h FROM HrShiftMap h WHERE h.hrShiftMapPK.empCode = :empCode"),
@NamedQuery(name = "HrShiftMap.findByShiftCode", query = "SELECT h FROM HrShiftMap h WHERE h.hrShiftMapPK.shiftCode = :shiftCode"),
@NamedQuery(name = "HrShiftMap.findByDefaultComp", query = "SELECT h FROM HrShiftMap h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrShiftMap.findByStatFlag", query = "SELECT h FROM HrShiftMap h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrShiftMap.findByStatUpFlag", query = "SELECT h FROM HrShiftMap h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrShiftMap.findByModDate", query = "SELECT h FROM HrShiftMap h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrShiftMap.findByAuthBy", query = "SELECT h FROM HrShiftMap h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrShiftMap.findByEnteredBy", query = "SELECT h FROM HrShiftMap h WHERE h.enteredBy = :enteredBy"),
@NamedQuery(name = "HrShiftMap.findByCompCodeAndEmpCode", query = "select h from HrShiftMap h where h.hrShiftMapPK.compCode = :compCode and h.hrShiftMapPK.empCode = :empCode")
})
public class HrShiftMap extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrShiftMapPK hrShiftMapPK;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
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
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "SHIFT_CODE", referencedColumnName = "SHIFT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstShift hrMstShift;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrShiftMap() {
    }

    public HrShiftMap(HrShiftMapPK hrShiftMapPK) {
        this.hrShiftMapPK = hrShiftMapPK;
    }

    public HrShiftMap(HrShiftMapPK hrShiftMapPK, String statFlag, String statUpFlag, Date modDate, String authBy, String enteredBy) {
        this.hrShiftMapPK = hrShiftMapPK;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrShiftMap(int compCode, long empCode, String shiftCode) {
        this.hrShiftMapPK = new HrShiftMapPK(compCode, empCode, shiftCode);
    }

    public HrShiftMapPK getHrShiftMapPK() {
        return hrShiftMapPK;
    }

    public void setHrShiftMapPK(HrShiftMapPK hrShiftMapPK) {
        this.hrShiftMapPK = hrShiftMapPK;
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

    public HrMstShift getHrMstShift() {
        return hrMstShift;
    }

    public void setHrMstShift(HrMstShift hrMstShift) {
        this.hrMstShift = hrMstShift;
    }

    public HrPersonnelDetails getHrPersonnelDetails() {
        return hrPersonnelDetails;
    }

    public void setHrPersonnelDetails(HrPersonnelDetails hrPersonnelDetails) {
        this.hrPersonnelDetails = hrPersonnelDetails;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrShiftMapPK != null ? hrShiftMapPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrShiftMap)) {
            return false;
        }
        HrShiftMap other = (HrShiftMap) object;
        if ((this.hrShiftMapPK == null && other.hrShiftMapPK != null) || (this.hrShiftMapPK != null && !this.hrShiftMapPK.equals(other.hrShiftMapPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrShiftMap[hrShiftMapPK=" + hrShiftMapPK + "]";
    }

}
