/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;


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
@Table(name = "hr_personnel_lang")
@NamedQueries({@NamedQuery(name = "HrPersonnelLang.findAll", query = "SELECT h FROM HrPersonnelLang h"), @NamedQuery(name = "HrPersonnelLang.findByCompCode", query = "SELECT h FROM HrPersonnelLang h WHERE h.hrPersonnelLangPK.compCode = :compCode"), @NamedQuery(name = "HrPersonnelLang.findByEmpCode", query = "SELECT h FROM HrPersonnelLang h WHERE h.hrPersonnelLangPK.empCode = :empCode"), @NamedQuery(name = "HrPersonnelLang.findByLang", query = "SELECT h FROM HrPersonnelLang h WHERE h.hrPersonnelLangPK.lang = :lang"), @NamedQuery(name = "HrPersonnelLang.findByLangRead", query = "SELECT h FROM HrPersonnelLang h WHERE h.langRead = :langRead"), @NamedQuery(name = "HrPersonnelLang.findByLangWrite", query = "SELECT h FROM HrPersonnelLang h WHERE h.langWrite = :langWrite"), @NamedQuery(name = "HrPersonnelLang.findByLangSpeak", query = "SELECT h FROM HrPersonnelLang h WHERE h.langSpeak = :langSpeak"), @NamedQuery(name = "HrPersonnelLang.findByDefaultComp", query = "SELECT h FROM HrPersonnelLang h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrPersonnelLang.findByStatFlag", query = "SELECT h FROM HrPersonnelLang h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrPersonnelLang.findByStatUpFlag", query = "SELECT h FROM HrPersonnelLang h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrPersonnelLang.findByModDate", query = "SELECT h FROM HrPersonnelLang h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrPersonnelLang.findByAuthBy", query = "SELECT h FROM HrPersonnelLang h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrPersonnelLang.findByEnteredBy", query = "SELECT h FROM HrPersonnelLang h WHERE h.enteredBy = :enteredBy")})
public class HrPersonnelLang extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPersonnelLangPK hrPersonnelLangPK;
    @Basic(optional = false)
    @Column(name = "LANG_READ")
    private char langRead;
    @Basic(optional = false)
    @Column(name = "LANG_WRITE")
    private char langWrite;
    @Basic(optional = false)
    @Column(name = "LANG_SPEAK")
    private char langSpeak;
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

    public HrPersonnelLang() {
    }

    public HrPersonnelLang(HrPersonnelLangPK hrPersonnelLangPK) {
        this.hrPersonnelLangPK = hrPersonnelLangPK;
    }

    public HrPersonnelLang(HrPersonnelLangPK hrPersonnelLangPK, char langRead, char langWrite, char langSpeak) {
        this.hrPersonnelLangPK = hrPersonnelLangPK;
        this.langRead = langRead;
        this.langWrite = langWrite;
        this.langSpeak = langSpeak;
    }

    public HrPersonnelLang(int compCode, long empCode, String lang) {
        this.hrPersonnelLangPK = new HrPersonnelLangPK(compCode, empCode, lang);
    }

    public HrPersonnelLangPK getHrPersonnelLangPK() {
        return hrPersonnelLangPK;
    }

    public void setHrPersonnelLangPK(HrPersonnelLangPK hrPersonnelLangPK) {
        this.hrPersonnelLangPK = hrPersonnelLangPK;
    }

    public char getLangRead() {
        return langRead;
    }

    public void setLangRead(char langRead) {
        this.langRead = langRead;
    }

    public char getLangWrite() {
        return langWrite;
    }

    public void setLangWrite(char langWrite) {
        this.langWrite = langWrite;
    }

    public char getLangSpeak() {
        return langSpeak;
    }

    public void setLangSpeak(char langSpeak) {
        this.langSpeak = langSpeak;
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
        hash += (hrPersonnelLangPK != null ? hrPersonnelLangPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelLang)) {
            return false;
        }
        HrPersonnelLang other = (HrPersonnelLang) object;
        if ((this.hrPersonnelLangPK == null && other.hrPersonnelLangPK != null) || (this.hrPersonnelLangPK != null && !this.hrPersonnelLangPK.equals(other.hrPersonnelLangPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelLang[hrPersonnelLangPK=" + hrPersonnelLangPK + "]";
    }

}
