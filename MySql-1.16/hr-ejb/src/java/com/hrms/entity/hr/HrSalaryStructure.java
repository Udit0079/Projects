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
 * @author root
 */
@Entity
@Table(name = "hr_salary_structure")
@NamedQueries({@NamedQuery(name = "HrSalaryStructure.findAll", query = "SELECT h FROM HrSalaryStructure h"),
@NamedQuery(name = "HrSalaryStructure.findByCompCode", query = "SELECT h FROM HrSalaryStructure h WHERE h.hrSalaryStructurePK.compCode = :compCode"),
@NamedQuery(name = "HrSalaryStructure.findByPurposeCode", query = "SELECT h FROM HrSalaryStructure h WHERE h.hrSalaryStructurePK.purposeCode = :purposeCode"),
@NamedQuery(name = "HrSalaryStructure.findByNature", query = "SELECT h FROM HrSalaryStructure h WHERE h.hrSalaryStructurePK.nature = :nature"),
@NamedQuery(name = "HrSalaryStructure.findByAlDesc", query = "SELECT h FROM HrSalaryStructure h WHERE h.hrSalaryStructurePK.alDesc = :alDesc"),
@NamedQuery(name = "HrSalaryStructure.findByTaxable", query = "SELECT h FROM HrSalaryStructure h WHERE h.taxable = :taxable"),
@NamedQuery(name = "HrSalaryStructure.findByApplicableDate", query = "SELECT h FROM HrSalaryStructure h WHERE h.applicableDate = :applicableDate"),
//@NamedQuery(name = "HrSalaryStructure.findByDateFrom", query = "SELECT h FROM HrSalaryStructure h WHERE h.hrSalaryStructurePK.dateFrom = :dateFrom"),
////@NamedQuery(name = "HrSalaryStructure.findByDateTo", query = "SELECT h FROM HrSalaryStructure h WHERE h.hrSalaryStructurePK.dateTo = :dateTo"),
@NamedQuery(name = "HrSalaryStructure.findByDefaultComp", query = "SELECT h FROM HrSalaryStructure h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrSalaryStructure.findByStatFlag", query = "SELECT h FROM HrSalaryStructure h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrSalaryStructure.findByStatUpFlag", query = "SELECT h FROM HrSalaryStructure h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrSalaryStructure.findByModDate", query = "SELECT h FROM HrSalaryStructure h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrSalaryStructure.findByAuthBy", query = "SELECT h FROM HrSalaryStructure h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrSalaryStructure.findByEnteredBy", query = "SELECT h FROM HrSalaryStructure h WHERE h.enteredBy = :enteredBy"),
@NamedQuery(name = "HrSalaryStructure.findByGlCode", query = "SELECT h FROM HrSalaryStructure h WHERE h.glCode = :glCode"),
@NamedQuery(name = "HrSalaryStructure.findByDescShCode", query = "SELECT h FROM HrSalaryStructure h WHERE h.descShCode = :descShCode"),
@NamedQuery(name = "HrSalaryStructure.findEntityByTaxableAndCompCode", query = "select h FROM HrSalaryStructure h WHERE h.taxable = :taxable and h.hrSalaryStructurePK.compCode = :compCode"),
//@NamedQuery(name = "HrSalaryStructure.findEntityForSlabDescription", query = "select h FROM HrSalaryStructure h where h.hrSalaryStructurePK.compCode = :compCode" +
//" and h.hrSalaryStructurePK.purposeCode = :purposeCode and h.hrSalaryStructurePK.nature = :nature and h.hrSalaryStructurePK.dateFrom = :dateFrom and h.hrSalaryStructurePK.dateTo = :dateTo"),
@NamedQuery(name = "HrSalaryStructure.findEntityForSlabDescription", query = "select h FROM HrSalaryStructure h where h.hrSalaryStructurePK.compCode = :compCode" +
" and h.hrSalaryStructurePK.purposeCode = :purposeCode and h.hrSalaryStructurePK.nature = :nature"),
//@NamedQuery(name = "HrSalaryStructure.findByCompCodeDateFromDateTo", query = "select h FROM HrSalaryStructure h where h.hrSalaryStructurePK.compCode = :compCode" +
//" and h.hrSalaryStructurePK.dateFrom = :dateFrom and h.hrSalaryStructurePK.dateTo = :dateTo"),
//@NamedQuery(name = "HrSalaryStructure.findByCompDatesAldesc", query = "select h FROM HrSalaryStructure h where h.hrSalaryStructurePK.compCode = :compCode" +
//" and h.hrSalaryStructurePK.dateFrom = :dateFrom and h.hrSalaryStructurePK.dateTo = :dateTo and h.hrSalaryStructurePK.alDesc = :alDesc"),
@NamedQuery(name = "HrSalaryStructure.findByCompDatesAldesc", query = "select h FROM HrSalaryStructure h where h.hrSalaryStructurePK.compCode = :compCode" +
" and h.hrSalaryStructurePK.alDesc = :alDesc"),
//@NamedQuery(name = "HrSalaryStructure.findEntityByPrimaryKey", query = "select h FROM HrSalaryStructure h where h.hrSalaryStructurePK.compCode = :compCode and h.hrSalaryStructurePK.purposeCode = :purposeCode and " +
//"h.hrSalaryStructurePK.nature = :nature and h.hrSalaryStructurePK.alDesc = :alDesc and h.hrSalaryStructurePK.dateFrom = :dateFrom and h.hrSalaryStructurePK.dateTo = :dateTo")
@NamedQuery(name = "HrSalaryStructure.findEntityByPrimaryKey", query = "select h FROM HrSalaryStructure h where h.hrSalaryStructurePK.compCode = :compCode and h.hrSalaryStructurePK.purposeCode = :purposeCode and " +
"h.hrSalaryStructurePK.nature = :nature and h.hrSalaryStructurePK.alDesc = :alDesc")
})
public class HrSalaryStructure extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrSalaryStructurePK hrSalaryStructurePK;
    @Column(name = "TAXABLE")
    private Character taxable;
    @Basic(optional = false)
    @Column(name = "APPLICABLE_DATE")
    private String applicableDate;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Basic(optional = false)
    @Column(name = "STAT_FLAG")
    private char statFlag;
    @Basic(optional = false)
    @Column(name = "STAT_UP_FLAG")
    private char statUpFlag;
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
    @Column(name = "GL_CODE")
    private String glCode;
    @Column(name = "DESC_SHORT_CODE")
    private Integer descShCode;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "PURPOSE_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "NATURE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct1;

    public HrSalaryStructure() {
    }

    public HrSalaryStructure(HrSalaryStructurePK hrSalaryStructurePK) {
        this.hrSalaryStructurePK = hrSalaryStructurePK;
    }

    public HrSalaryStructure(HrSalaryStructurePK hrSalaryStructurePK, String applicableDate, char statFlag, char statUpFlag, Date modDate, String authBy, String enteredBy, String glCode, int descShCode) {
        this.hrSalaryStructurePK = hrSalaryStructurePK;
        this.applicableDate = applicableDate;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
        this.glCode = glCode;
        this.descShCode = descShCode;
    }

//    public HrSalaryStructure(int compCode, String purposeCode, String nature, String alDesc, String dateFrom, String dateTo) {
//        this.hrSalaryStructurePK = new HrSalaryStructurePK(compCode, purposeCode, nature, alDesc, dateFrom, dateTo);
//    }
    public HrSalaryStructure(int compCode, String purposeCode, String nature, String alDesc) {
        this.hrSalaryStructurePK = new HrSalaryStructurePK(compCode, purposeCode, nature, alDesc);
    }

    public HrSalaryStructurePK getHrSalaryStructurePK() {
        return hrSalaryStructurePK;
    }

    public void setHrSalaryStructurePK(HrSalaryStructurePK hrSalaryStructurePK) {
        this.hrSalaryStructurePK = hrSalaryStructurePK;
    }

    public Character getTaxable() {
        return taxable;
    }

    public void setTaxable(Character taxable) {
        this.taxable = taxable;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public char getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(char statFlag) {
        this.statFlag = statFlag;
    }

    public char getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(char statUpFlag) {
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

    public String getGlCode() {
        return glCode;
    }

    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    public int getDescShCode() {
        return descShCode;
    }

    public void setDescShCode(int descShCode) {
        this.descShCode = descShCode;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrSalaryStructurePK != null ? hrSalaryStructurePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryStructure)) {
            return false;
        }
        HrSalaryStructure other = (HrSalaryStructure) object;
        if ((this.hrSalaryStructurePK == null && other.hrSalaryStructurePK != null) || (this.hrSalaryStructurePK != null && !this.hrSalaryStructurePK.equals(other.hrSalaryStructurePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrSalaryStructure[hrSalaryStructurePK=" + hrSalaryStructurePK + "]";
    }

}
