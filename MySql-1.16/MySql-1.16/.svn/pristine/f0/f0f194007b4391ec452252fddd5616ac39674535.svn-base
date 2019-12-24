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
@Table(name = "hr_consultant")
@NamedQueries({@NamedQuery(name = "HrConsultant.findAll", query = "SELECT h FROM HrConsultant h"),
@NamedQuery(name = "HrConsultant.findByCompCode", query = "SELECT h FROM HrConsultant h WHERE h.hrConsultantPK.compCode = :compCode"),
@NamedQuery(name = "HrConsultant.findByConsCode", query = "SELECT h FROM HrConsultant h WHERE h.hrConsultantPK.consCode = :consCode"),
@NamedQuery(name = "HrConsultant.findByConsFirName", query = "SELECT h FROM HrConsultant h WHERE h.consFirName = :consFirName"),
@NamedQuery(name = "HrConsultant.findByConsMidName", query = "SELECT h FROM HrConsultant h WHERE h.consMidName = :consMidName"),
@NamedQuery(name = "HrConsultant.findByConsLastName", query = "SELECT h FROM HrConsultant h WHERE h.consLastName = :consLastName"),
@NamedQuery(name = "HrConsultant.findByConsAdd", query = "SELECT h FROM HrConsultant h WHERE h.consAdd = :consAdd"),
@NamedQuery(name = "HrConsultant.findByConsCity", query = "SELECT h FROM HrConsultant h WHERE h.consCity = :consCity"),
@NamedQuery(name = "HrConsultant.findByConsPin", query = "SELECT h FROM HrConsultant h WHERE h.consPin = :consPin"),
@NamedQuery(name = "HrConsultant.findByConsState", query = "SELECT h FROM HrConsultant h WHERE h.consState = :consState"),
@NamedQuery(name = "HrConsultant.findByFaxNumber", query = "SELECT h FROM HrConsultant h WHERE h.faxNumber = :faxNumber"),
@NamedQuery(name = "HrConsultant.findByMobileNumber", query = "SELECT h FROM HrConsultant h WHERE h.mobileNumber = :mobileNumber"),
@NamedQuery(name = "HrConsultant.findByTeleNumber", query = "SELECT h FROM HrConsultant h WHERE h.teleNumber = :teleNumber"),
@NamedQuery(name = "HrConsultant.findByContPerson", query = "SELECT h FROM HrConsultant h WHERE h.contPerson = :contPerson"),
@NamedQuery(name = "HrConsultant.findByEmail", query = "SELECT h FROM HrConsultant h WHERE h.email = :email"),
@NamedQuery(name = "HrConsultant.findByContDesg", query = "SELECT h FROM HrConsultant h WHERE h.contDesg = :contDesg"),
@NamedQuery(name = "HrConsultant.findByConsFee", query = "SELECT h FROM HrConsultant h WHERE h.consFee = :consFee"),
@NamedQuery(name = "HrConsultant.findByDefaultComp", query = "SELECT h FROM HrConsultant h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrConsultant.findByStatFlag", query = "SELECT h FROM HrConsultant h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrConsultant.findByStatUpFlag", query = "SELECT h FROM HrConsultant h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrConsultant.findByDtModDate", query = "SELECT h FROM HrConsultant h WHERE h.dtModDate = :dtModDate"),
@NamedQuery(name = "HrConsultant.findByAuthBy", query = "SELECT h FROM HrConsultant h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrConsultant.findByConsultantName", query = "SELECT h FROM HrConsultant h WHERE h.hrConsultantPK.compCode = :value1 and h.consFirName like:value2 order by h.consFirName asc"),
@NamedQuery(name = "HrConsultant.findByConsultantDetails", query = "SELECT h FROM HrConsultant h WHERE h.hrConsultantPK.compCode = :value1 and h.hrConsultantPK.consCode = :value2  and h.consFirName = :value3"),
@NamedQuery(name = "HrConsultant.findByEnteredBy", query = "SELECT h FROM HrConsultant h WHERE h.enteredBy = :enteredBy")})
public class HrConsultant extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrConsultantPK hrConsultantPK;
    @Basic(optional = false)
    @Column(name = "CONS_FIR_NAME")
    private String consFirName;
    @Column(name = "CONS_MID_NAME")
    private String consMidName;
    @Column(name = "CONS_LAST_NAME")
    private String consLastName;
    @Column(name = "CONS_ADD")
    private String consAdd;
    @Column(name = "CONS_CITY")
    private String consCity;
    @Column(name = "CONS_PIN")
    private String consPin;
    @Column(name = "CONS_STATE")
    private String consState;
    @Column(name = "FAX_NUMBER")
    private String faxNumber;
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @Column(name = "TELE_NUMBER")
    private String teleNumber;
    @Column(name = "CONT_PERSON")
    private String contPerson;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "CONT_DESG")
    private String contDesg;
    @Column(name = "CONS_FEE")
    private Double consFee;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Column(name = "DT_MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModDate;
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    public HrConsultant() {
    }

    public HrConsultant(HrConsultantPK hrConsultantPK) {
        this.hrConsultantPK = hrConsultantPK;
    }

    public HrConsultant(HrConsultantPK hrConsultantPK, String consFirName) {
        this.hrConsultantPK = hrConsultantPK;
        this.consFirName = consFirName;
    }

    public HrConsultant(int compCode, String consCode) {
        this.hrConsultantPK = new HrConsultantPK(compCode, consCode);
    }

    public HrConsultantPK getHrConsultantPK() {
        return hrConsultantPK;
    }

    public void setHrConsultantPK(HrConsultantPK hrConsultantPK) {
        this.hrConsultantPK = hrConsultantPK;
    }

    public String getConsFirName() {
        return consFirName;
    }

    public void setConsFirName(String consFirName) {
        this.consFirName = consFirName;
    }

    public String getConsMidName() {
        return consMidName;
    }

    public void setConsMidName(String consMidName) {
        this.consMidName = consMidName;
    }

    public String getConsLastName() {
        return consLastName;
    }

    public void setConsLastName(String consLastName) {
        this.consLastName = consLastName;
    }

    public String getConsAdd() {
        return consAdd;
    }

    public void setConsAdd(String consAdd) {
        this.consAdd = consAdd;
    }

    public String getConsCity() {
        return consCity;
    }

    public void setConsCity(String consCity) {
        this.consCity = consCity;
    }

    public String getConsPin() {
        return consPin;
    }

    public void setConsPin(String consPin) {
        this.consPin = consPin;
    }

    public String getConsState() {
        return consState;
    }

    public void setConsState(String consState) {
        this.consState = consState;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    public String getContPerson() {
        return contPerson;
    }

    public void setContPerson(String contPerson) {
        this.contPerson = contPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContDesg() {
        return contDesg;
    }

    public void setContDesg(String contDesg) {
        this.contDesg = contDesg;
    }

    public Double getConsFee() {
        return consFee;
    }

    public void setConsFee(Double consFee) {
        this.consFee = consFee;
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

    public Date getDtModDate() {
        return dtModDate;
    }

    public void setDtModDate(Date dtModDate) {
        this.dtModDate = dtModDate;
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
        hash += (hrConsultantPK != null ? hrConsultantPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrConsultant)) {
            return false;
        }
        HrConsultant other = (HrConsultant) object;
        if ((this.hrConsultantPK == null && other.hrConsultantPK != null) || (this.hrConsultantPK != null && !this.hrConsultantPK.equals(other.hrConsultantPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.HrConsultant[hrConsultantPK=" + hrConsultantPK + "]";
    }
}
