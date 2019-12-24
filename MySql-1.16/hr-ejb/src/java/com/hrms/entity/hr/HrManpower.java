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
@Table(name = "hr_manpower")
@NamedQueries({@NamedQuery(name = "HrManpower.findAll", query = "SELECT h FROM HrManpower h"), @NamedQuery(name = "HrManpower.findByCompCode", query = "SELECT h FROM HrManpower h WHERE h.hrManpowerPK.compCode = :compCode"), @NamedQuery(name = "HrManpower.findByYear", query = "SELECT h FROM HrManpower h WHERE h.hrManpowerPK.year = :year"), @NamedQuery(name = "HrManpower.findByMonth", query = "SELECT h FROM HrManpower h WHERE h.hrManpowerPK.month = :month"), @NamedQuery(name = "HrManpower.findByZone", query = "SELECT h FROM HrManpower h WHERE h.hrManpowerPK.zone = :zone"), @NamedQuery(name = "HrManpower.findByDeptCode", query = "SELECT h FROM HrManpower h WHERE h.hrManpowerPK.deptCode = :deptCode"), @NamedQuery(name = "HrManpower.findByMinmExp", query = "SELECT h FROM HrManpower h WHERE h.minmExp = :minmExp"), @NamedQuery(name = "HrManpower.findByPrefExp", query = "SELECT h FROM HrManpower h WHERE h.prefExp = :prefExp"), @NamedQuery(name = "HrManpower.findByRequExp", query = "SELECT h FROM HrManpower h WHERE h.requExp = :requExp"), @NamedQuery(name = "HrManpower.findByAutoExp", query = "SELECT h FROM HrManpower h WHERE h.autoExp = :autoExp"), @NamedQuery(name = "HrManpower.findByPosFill", query = "SELECT h FROM HrManpower h WHERE h.posFill = :posFill"), @NamedQuery(name = "HrManpower.findByPosRequ", query = "SELECT h FROM HrManpower h WHERE h.posRequ = :posRequ"), @NamedQuery(name = "HrManpower.findByPosSanc", query = "SELECT h FROM HrManpower h WHERE h.posSanc = :posSanc"), @NamedQuery(name = "HrManpower.findBySkillCode", query = "SELECT h FROM HrManpower h WHERE h.skillCode = :skillCode"), @NamedQuery(name = "HrManpower.findByDefaultComp", query = "SELECT h FROM HrManpower h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrManpower.findByStatFlag", query = "SELECT h FROM HrManpower h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrManpower.findByStatUpFlag", query = "SELECT h FROM HrManpower h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrManpower.findByModDate", query = "SELECT h FROM HrManpower h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrManpower.findByAuthBy", query = "SELECT h FROM HrManpower h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrManpower.findByEnteredBy", query = "SELECT h FROM HrManpower h WHERE h.enteredBy = :enteredBy")})
public class HrManpower extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrManpowerPK hrManpowerPK;
    @Column(name = "MINM_EXP")
    private Double minmExp;
    @Column(name = "PREF_EXP")
    private Double prefExp;
    @Column(name = "REQU_EXP")
    private Double requExp;
    @Column(name = "AUTO_EXP")
    private Double autoExp;
    @Column(name = "POS_FILL")
    private Integer posFill;
    @Column(name = "POS_REQU")
    private Integer posRequ;
    @Column(name = "POS_SANC")
    private Integer posSanc;
    @Column(name = "SKILL_CODE")
    private String skillCode;
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
//    @Column(name = "ZONE")
//    private String jone;
//    @Column(name = "DEPT_CODE")
//    private String departmentCode;
    @Column(name = "QUAL_CODE1")
    private String qualcode1;
    @Column(name = "QUAL_CODE2")
    private String qualCode2;
    @Column(name = "QUAL_CODE3")
    private String qualCode3;
    @Column(name = "SPECIAL_CODE")
    private String specialCode;
    @Column(name = "DESG_CODE")
    private String desgCode;

//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "ZONE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DEPT_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct1;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "QUAL_CODE1", referencedColumnName = "STRUCT_CODE")})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct2;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "QUAL_CODE2", referencedColumnName = "STRUCT_CODE")})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct3;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "QUAL_CODE3", referencedColumnName = "STRUCT_CODE")})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct4;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "SPECIAL_CODE", referencedColumnName = "STRUCT_CODE")})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct5;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DESG_CODE", referencedColumnName = "STRUCT_CODE")})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct6;

    public HrManpower() {
    }

    public HrManpower(HrManpowerPK hrManpowerPK) {
        this.hrManpowerPK = hrManpowerPK;
    }

    public HrManpower(HrManpowerPK hrManpowerPK, int defaultComp, String statFlag, String statUpFlag, Date modDate, String authBy, String enteredBy) {
        this.hrManpowerPK = hrManpowerPK;
        this.defaultComp = defaultComp;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrManpower(int compCode, int year, String month, String zone, String deptCode) {
        this.hrManpowerPK = new HrManpowerPK(compCode, year, month, zone, deptCode);
    }

    public HrManpowerPK getHrManpowerPK() {
        return hrManpowerPK;
    }

    public void setHrManpowerPK(HrManpowerPK hrManpowerPK) {
        this.hrManpowerPK = hrManpowerPK;
    }

    public Double getMinmExp() {
        return minmExp;
    }

    public void setMinmExp(Double minmExp) {
        this.minmExp = minmExp;
    }

    public Double getPrefExp() {
        return prefExp;
    }

    public void setPrefExp(Double prefExp) {
        this.prefExp = prefExp;
    }

    public Double getRequExp() {
        return requExp;
    }

    public void setRequExp(Double requExp) {
        this.requExp = requExp;
    }

    public Double getAutoExp() {
        return autoExp;
    }

    public void setAutoExp(Double autoExp) {
        this.autoExp = autoExp;
    }

    public Integer getPosFill() {
        return posFill;
    }

    public void setPosFill(Integer posFill) {
        this.posFill = posFill;
    }

    public Integer getPosRequ() {
        return posRequ;
    }

    public void setPosRequ(Integer posRequ) {
        this.posRequ = posRequ;
    }

    public Integer getPosSanc() {
        return posSanc;
    }

    public void setPosSanc(Integer posSanc) {
        this.posSanc = posSanc;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
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


    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getQualCode2() {
        return qualCode2;
    }

    public void setQualCode2(String qualCode2) {
        this.qualCode2 = qualCode2;
    }

    public String getQualCode3() {
        return qualCode3;
    }

    public void setQualCode3(String qualCode3) {
        this.qualCode3 = qualCode3;
    }

    public String getQualcode1() {
        return qualcode1;
    }

    public void setQualcode1(String qualcode1) {
        this.qualcode1 = qualcode1;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
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
//
//    public HrMstStruct getHrMstStruct2() {
//        return hrMstStruct2;
//    }
//
//    public void setHrMstStruct2(HrMstStruct hrMstStruct2) {
//        this.hrMstStruct2 = hrMstStruct2;
//    }
//
//    public HrMstStruct getHrMstStruct3() {
//        return hrMstStruct3;
//    }
//
//    public void setHrMstStruct3(HrMstStruct hrMstStruct3) {
//        this.hrMstStruct3 = hrMstStruct3;
//    }
//
//    public HrMstStruct getHrMstStruct4() {
//        return hrMstStruct4;
//    }
//
//    public void setHrMstStruct4(HrMstStruct hrMstStruct4) {
//        this.hrMstStruct4 = hrMstStruct4;
//    }
//
//    public HrMstStruct getHrMstStruct5() {
//        return hrMstStruct5;
//    }
//
//    public void setHrMstStruct5(HrMstStruct hrMstStruct5) {
//        this.hrMstStruct5 = hrMstStruct5;
//    }
//
//    public HrMstStruct getHrMstStruct6() {
//        return hrMstStruct6;
//    }
//
//    public void setHrMstStruct6(HrMstStruct hrMstStruct6) {
//        this.hrMstStruct6 = hrMstStruct6;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrManpowerPK != null ? hrManpowerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrManpower)) {
            return false;
        }
        HrManpower other = (HrManpower) object;
        if ((this.hrManpowerPK == null && other.hrManpowerPK != null) || (this.hrManpowerPK != null && !this.hrManpowerPK.equals(other.hrManpowerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrManpower[hrManpowerPK=" + hrManpowerPK + "]";
    }

}
