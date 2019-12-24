/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;


import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
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
@Table(name = "hr_personnel_hobby")
@NamedQueries({@NamedQuery(name = "HrPersonnelHobby.findAll", query = "SELECT h FROM HrPersonnelHobby h"), @NamedQuery(name = "HrPersonnelHobby.findByCompCode", query = "SELECT h FROM HrPersonnelHobby h WHERE h.hrPersonnelHobbyPK.compCode = :compCode"), @NamedQuery(name = "HrPersonnelHobby.findByEmpCode", query = "SELECT h FROM HrPersonnelHobby h WHERE h.hrPersonnelHobbyPK.empCode = :empCode"), @NamedQuery(name = "HrPersonnelHobby.findByHobbyName", query = "SELECT h FROM HrPersonnelHobby h WHERE h.hrPersonnelHobbyPK.hobbyName = :hobbyName"), @NamedQuery(name = "HrPersonnelHobby.findByDefaultComp", query = "SELECT h FROM HrPersonnelHobby h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrPersonnelHobby.findByStatFlag", query = "SELECT h FROM HrPersonnelHobby h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrPersonnelHobby.findByStatUpFlag", query = "SELECT h FROM HrPersonnelHobby h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrPersonnelHobby.findByModDate", query = "SELECT h FROM HrPersonnelHobby h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrPersonnelHobby.findByAuthBy", query = "SELECT h FROM HrPersonnelHobby h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrPersonnelHobby.findByEnteredBy", query = "SELECT h FROM HrPersonnelHobby h WHERE h.enteredBy = :enteredBy")})
public class HrPersonnelHobby extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPersonnelHobbyPK hrPersonnelHobbyPK;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrPersonnelHobby() {
    }

    public HrPersonnelHobby(HrPersonnelHobbyPK hrPersonnelHobbyPK) {
        this.hrPersonnelHobbyPK = hrPersonnelHobbyPK;
    }

    public HrPersonnelHobby(int compCode, long empCode, String hobbyName) {
        this.hrPersonnelHobbyPK = new HrPersonnelHobbyPK(compCode, empCode, hobbyName);
    }

    public HrPersonnelHobbyPK getHrPersonnelHobbyPK() {
        return hrPersonnelHobbyPK;
    }

    public void setHrPersonnelHobbyPK(HrPersonnelHobbyPK hrPersonnelHobbyPK) {
        this.hrPersonnelHobbyPK = hrPersonnelHobbyPK;
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

    public HrPersonnelDetails getHrPersonnelDetails() {
        return hrPersonnelDetails;
    }

    public void setHrPersonnelDetails(HrPersonnelDetails hrPersonnelDetails) {
        this.hrPersonnelDetails = hrPersonnelDetails;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrPersonnelHobbyPK != null ? hrPersonnelHobbyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelHobby)) {
            return false;
        }
        HrPersonnelHobby other = (HrPersonnelHobby) object;
        if ((this.hrPersonnelHobbyPK == null && other.hrPersonnelHobbyPK != null) || (this.hrPersonnelHobbyPK != null && !this.hrPersonnelHobbyPK.equals(other.hrPersonnelHobbyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelHobby[hrPersonnelHobbyPK=" + hrPersonnelHobbyPK + "]";
    }

}
