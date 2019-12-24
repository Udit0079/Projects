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
@Table(name = "hr_details_program")
@NamedQueries({@NamedQuery(name = "HrDetailsProgram.findAll", query = "SELECT h FROM HrDetailsProgram h"), @NamedQuery(name = "HrDetailsProgram.findByCompCode", query = "SELECT h FROM HrDetailsProgram h WHERE h.hrDetailsProgramPK.compCode = :compCode"), @NamedQuery(name = "HrDetailsProgram.findByProgCode", query = "SELECT h FROM HrDetailsProgram h WHERE h.hrDetailsProgramPK.progCode = :progCode"), @NamedQuery(name = "HrDetailsProgram.findBySkillCode", query = "SELECT h FROM HrDetailsProgram h WHERE h.hrDetailsProgramPK.skillCode = :skillCode"), @NamedQuery(name = "HrDetailsProgram.findByDefaultComp", query = "SELECT h FROM HrDetailsProgram h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrDetailsProgram.findByStatFlag", query = "SELECT h FROM HrDetailsProgram h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrDetailsProgram.findByStatUpFlag", query = "SELECT h FROM HrDetailsProgram h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrDetailsProgram.findByModDate", query = "SELECT h FROM HrDetailsProgram h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrDetailsProgram.findByAuthBy", query = "SELECT h FROM HrDetailsProgram h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrDetailsProgram.findByEnteredBy", query = "SELECT h FROM HrDetailsProgram h WHERE h.enteredBy = :enteredBy")})
public class HrDetailsProgram extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrDetailsProgramPK hrDetailsProgramPK;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "PROG_CODE", referencedColumnName = "PROG_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstProgram hrMstProgram;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "SKILL_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;

    public HrDetailsProgram() {
    }

    public HrDetailsProgram(HrDetailsProgramPK hrDetailsProgramPK) {
        this.hrDetailsProgramPK = hrDetailsProgramPK;
    }

    public HrDetailsProgram(int compCode, String progCode, String skillCode) {
        this.hrDetailsProgramPK = new HrDetailsProgramPK(compCode, progCode, skillCode);
    }

    public HrDetailsProgramPK getHrDetailsProgramPK() {
        return hrDetailsProgramPK;
    }

    public void setHrDetailsProgramPK(HrDetailsProgramPK hrDetailsProgramPK) {
        this.hrDetailsProgramPK = hrDetailsProgramPK;
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

    public HrMstProgram getHrMstProgram() {
        return hrMstProgram;
    }

    public void setHrMstProgram(HrMstProgram hrMstProgram) {
        this.hrMstProgram = hrMstProgram;
    }

    public HrMstStruct getHrMstStruct() {
        return hrMstStruct;
    }

    public void setHrMstStruct(HrMstStruct hrMstStruct) {
        this.hrMstStruct = hrMstStruct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrDetailsProgramPK != null ? hrDetailsProgramPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDetailsProgram)) {
            return false;
        }
        HrDetailsProgram other = (HrDetailsProgram) object;
        if ((this.hrDetailsProgramPK == null && other.hrDetailsProgramPK != null) || (this.hrDetailsProgramPK != null && !this.hrDetailsProgramPK.equals(other.hrDetailsProgramPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrDetailsProgram[hrDetailsProgramPK=" + hrDetailsProgramPK + "]";
    }

}
