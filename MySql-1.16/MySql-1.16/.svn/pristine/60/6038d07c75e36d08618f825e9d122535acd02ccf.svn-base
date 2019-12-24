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
@Table(name = "hr_data_qual")
@NamedQueries({
    @NamedQuery(name = "HrDataQual.findAll", query = "SELECT h FROM HrDataQual h"),
    @NamedQuery(name = "HrDataQual.findByCompCode", query = "SELECT h FROM HrDataQual h WHERE h.hrDataQualPK.compCode = :compCode"),
    @NamedQuery(name = "HrDataQual.findByAdvtCode", query = "SELECT h FROM HrDataQual h WHERE h.hrDataQualPK.advtCode = :advtCode"),
    @NamedQuery(name = "HrDataQual.findByJobCode", query = "SELECT h FROM HrDataQual h WHERE h.hrDataQualPK.jobCode = :jobCode"),
    @NamedQuery(name = "HrDataQual.findByCandSrno", query = "SELECT h FROM HrDataQual h WHERE h.hrDataQualPK.candSrno = :candSrno"),
    @NamedQuery(name = "HrDataQual.findByQualCode", query = "SELECT h FROM HrDataQual h WHERE h.hrDataQualPK.qualCode = :qualCode"),
    @NamedQuery(name = "HrDataQual.findByInstName", query = "SELECT h FROM HrDataQual h WHERE h.instName = :instName"),
    @NamedQuery(name = "HrDataQual.findByYear", query = "SELECT h FROM HrDataQual h WHERE h.year = :year"),
    @NamedQuery(name = "HrDataQual.findBySubName", query = "SELECT h FROM HrDataQual h WHERE h.subName = :subName"),
    @NamedQuery(name = "HrDataQual.findBySpecial", query = "SELECT h FROM HrDataQual h WHERE h.special = :special"),
    @NamedQuery(name = "HrDataQual.findByPerMarks", query = "SELECT h FROM HrDataQual h WHERE h.perMarks = :perMarks"),
    @NamedQuery(name = "HrDataQual.findByDiv", query = "SELECT h FROM HrDataQual h WHERE h.div = :div"),
    @NamedQuery(name = "HrDataQual.findByDefaultCompCode", query = "SELECT h FROM HrDataQual h WHERE h.defaultCompCode = :defaultCompCode"),
    @NamedQuery(name = "HrDataQual.findByStatFlag", query = "SELECT h FROM HrDataQual h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrDataQual.findByStatUpFlag", query = "SELECT h FROM HrDataQual h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrDataQual.findByModDate", query = "SELECT h FROM HrDataQual h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrDataQual.findByAuthBy", query = "SELECT h FROM HrDataQual h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrDataQual.findByEnteredBy", query = "SELECT h FROM HrDataQual h WHERE h.enteredBy = :enteredBy")})
public class HrDataQual extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrDataQualPK hrDataQualPK;
    @Basic(optional = false)
    @Column(name = "INST_NAME")
    private String instName;
    @Basic(optional = false)
    @Column(name = "YEAR")
    private int year;
    @Column(name = "SUB_NAME")
    private String subName;
    @Column(name = "SPECIAL")
    private String special;
    @Column(name = "PER_MARKS")
    private Double perMarks;
    @Column(name = "DIV")
    private String div;
    @Column(name = "DEFAULT_COMP_CODE")
    private Integer defaultCompCode;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "CAND_SRNO", referencedColumnName = "CAND_ID", insertable = false, updatable = false), @JoinColumn(name = "ADVT_CODE", referencedColumnName = "ADVT_CODE", insertable = false, updatable = false), @JoinColumn(name = "JOB_CODE", referencedColumnName = "JOB_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrDatabank hrDatabank;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "QUAL_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;

    public HrDataQual() {
    }

    public HrDataQual(HrDataQualPK hrDataQualPK) {
        this.hrDataQualPK = hrDataQualPK;
    }

    public HrDataQual(HrDataQualPK hrDataQualPK, String instName, int year) {
        this.hrDataQualPK = hrDataQualPK;
        this.instName = instName;
        this.year = year;
    }

    public HrDataQual(int compCode, String advtCode, String jobCode, String candSrno, String qualCode) {
        this.hrDataQualPK = new HrDataQualPK(compCode, advtCode, jobCode, candSrno, qualCode);
    }

    public HrDataQualPK getHrDataQualPK() {
        return hrDataQualPK;
    }

    public void setHrDataQualPK(HrDataQualPK hrDataQualPK) {
        this.hrDataQualPK = hrDataQualPK;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public Double getPerMarks() {
        return perMarks;
    }

    public void setPerMarks(Double perMarks) {
        this.perMarks = perMarks;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public Integer getDefaultCompCode() {
        return defaultCompCode;
    }

    public void setDefaultCompCode(Integer defaultCompCode) {
        this.defaultCompCode = defaultCompCode;
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

    public HrDatabank getHrDatabank() {
        return hrDatabank;
    }

    public void setHrDatabank(HrDatabank hrDatabank) {
        this.hrDatabank = hrDatabank;
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
        hash += (hrDataQualPK != null ? hrDataQualPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDataQual)) {
            return false;
        }
        HrDataQual other = (HrDataQual) object;
        if ((this.hrDataQualPK == null && other.hrDataQualPK != null) || (this.hrDataQualPK != null && !this.hrDataQualPK.equals(other.hrDataQualPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrDataQual[hrDataQualPK=" + hrDataQualPK + "]";
    }

}
