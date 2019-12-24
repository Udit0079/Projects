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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_mst_desg")
@NamedQueries({@NamedQuery(name = "HrMstDesg.findAll", query = "SELECT h FROM HrMstDesg h"), @NamedQuery(name = "HrMstDesg.findByCompCode", query = "SELECT h FROM HrMstDesg h WHERE h.hrMstDesgPK.compCode = :compCode"), @NamedQuery(name = "HrMstDesg.findByDesgCode", query = "SELECT h FROM HrMstDesg h WHERE h.hrMstDesgPK.desgCode = :desgCode"), @NamedQuery(name = "HrMstDesg.findByDefaultComp", query = "SELECT h FROM HrMstDesg h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrMstDesg.findByStatFlag", query = "SELECT h FROM HrMstDesg h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrMstDesg.findByStatUpFlag", query = "SELECT h FROM HrMstDesg h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrMstDesg.findByModDate", query = "SELECT h FROM HrMstDesg h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrMstDesg.findByAuthBy", query = "SELECT h FROM HrMstDesg h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrMstDesg.findByEnteredBy", query = "SELECT h FROM HrMstDesg h WHERE h.enteredBy = :enteredBy")})
public class HrMstDesg extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstDesgPK hrMstDesgPK;
    @Basic(optional = false)
    @Column(name = "DEFAULT_COMP")
    private int defaultComp;
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
   // @Column(name = "DESG_CODE")
   // private String desigCode;
    @Column(name = "GRADE_CODE")
    private String gradeCode;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DESG_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
//    @OneToOne(optional = false)
//    private HrMstStruct hrMstStruct;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "GRADE_CODE", referencedColumnName = "STRUCT_CODE")})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct1;

    public HrMstDesg() {
    }

    public HrMstDesg(HrMstDesgPK hrMstDesgPK) {
        this.hrMstDesgPK = hrMstDesgPK;
    }

    public HrMstDesg(HrMstDesgPK hrMstDesgPK, int defaultComp, String statFlag, String statUpFlag, Date modDate, String authBy, String enteredBy) {
        this.hrMstDesgPK = hrMstDesgPK;
        this.defaultComp = defaultComp;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrMstDesg(int compCode, String desgCode) {
        this.hrMstDesgPK = new HrMstDesgPK(compCode, desgCode);
    }

    public HrMstDesgPK getHrMstDesgPK() {
        return hrMstDesgPK;
    }

    public void setHrMstDesgPK(HrMstDesgPK hrMstDesgPK) {
        this.hrMstDesgPK = hrMstDesgPK;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
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

//    public String getDesigCode() {
//        return desigCode;
//    }
//
//    public void setDesigCode(String desigCode) {
//        this.desigCode = desigCode;
//    }

   
    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    

//    public HrMstStruct getHrMstStruct() {
//        return hrMstStruct;
//    }
//
//    public void setHrMstStruct(HrMstStruct hrMstStruct) {
//        this.hrMstStruct = hrMstStruct;
//    }
//
//    public HrMstStruct getHrMstStruct1() {
//        return hrMstStruct1;
//    }
//
//    public void setHrMstStruct1(HrMstStruct hrMstStruct1) {
//        this.hrMstStruct1 = hrMstStruct1;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrMstDesgPK != null ? hrMstDesgPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstDesg)) {
            return false;
        }
        HrMstDesg other = (HrMstDesg) object;
        if ((this.hrMstDesgPK == null && other.hrMstDesgPK != null) || (this.hrMstDesgPK != null && !this.hrMstDesgPK.equals(other.hrMstDesgPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrMstDesg[hrMstDesgPK=" + hrMstDesgPK + "]";
    }

}
