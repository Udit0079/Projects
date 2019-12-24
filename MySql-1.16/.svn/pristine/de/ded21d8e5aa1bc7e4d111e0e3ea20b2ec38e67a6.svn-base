/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

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
@Table(name = "hr_contractor_details")
@NamedQueries({@NamedQuery(name = "HrContractorDetails.findAll", query = "SELECT h FROM HrContractorDetails h"),
@NamedQuery(name = "HrContractorDetails.findByCompCode", query = "SELECT h FROM HrContractorDetails h WHERE h.hrContractorDetailsPK.compCode = :compCode"),
@NamedQuery(name = "HrContractorDetails.findByContCode", query = "SELECT h FROM HrContractorDetails h WHERE h.hrContractorDetailsPK.contCode = :contCode"),
@NamedQuery(name = "HrContractorDetails.findByContFirName", query = "SELECT h FROM HrContractorDetails h WHERE h.contFirName = :contFirName"),
@NamedQuery(name = "HrContractorDetails.findByContMidName", query = "SELECT h FROM HrContractorDetails h WHERE h.contMidName = :contMidName"),
@NamedQuery(name = "HrContractorDetails.findByContLastName", query = "SELECT h FROM HrContractorDetails h WHERE h.contLastName = :contLastName"),
@NamedQuery(name = "HrContractorDetails.findByContAddress", query = "SELECT h FROM HrContractorDetails h WHERE h.contAddress = :contAddress"),
@NamedQuery(name = "HrContractorDetails.findByContCity", query = "SELECT h FROM HrContractorDetails h WHERE h.contCity = :contCity"),
@NamedQuery(name = "HrContractorDetails.findByContPin", query = "SELECT h FROM HrContractorDetails h WHERE h.contPin = :contPin"),
@NamedQuery(name = "HrContractorDetails.findByContState", query = "SELECT h FROM HrContractorDetails h WHERE h.contState = :contState"),
@NamedQuery(name = "HrContractorDetails.findByFaxNumber", query = "SELECT h FROM HrContractorDetails h WHERE h.faxNumber = :faxNumber"),
@NamedQuery(name = "HrContractorDetails.findByMobileNumber", query = "SELECT h FROM HrContractorDetails h WHERE h.mobileNumber = :mobileNumber"),
@NamedQuery(name = "HrContractorDetails.findByTeleNumber", query = "SELECT h FROM HrContractorDetails h WHERE h.teleNumber = :teleNumber"),
@NamedQuery(name = "HrContractorDetails.findByResiNumber", query = "SELECT h FROM HrContractorDetails h WHERE h.resiNumber = :resiNumber"),
@NamedQuery(name = "HrContractorDetails.findByEmailNumber", query = "SELECT h FROM HrContractorDetails h WHERE h.emailNumber = :emailNumber"),
@NamedQuery(name = "HrContractorDetails.findByDefaultComp", query = "SELECT h FROM HrContractorDetails h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrContractorDetails.findByStatFlag", query = "SELECT h FROM HrContractorDetails h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrContractorDetails.findByStatUpFlag", query = "SELECT h FROM HrContractorDetails h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrContractorDetails.findByModDate", query = "SELECT h FROM HrContractorDetails h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrContractorDetails.findByAuthBy", query = "SELECT h FROM HrContractorDetails h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrContractorDetails.findMaxCodeByCompCode", query = "SELECT max(h.hrContractorDetailsPK.contCode) FROM HrContractorDetails h WHERE h.hrContractorDetailsPK.compCode = :compCode"),
@NamedQuery(name = "HrContractorDetails.findByEnteredBy", query = "SELECT h FROM HrContractorDetails h WHERE h.enteredBy = :enteredBy")})
public class HrContractorDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrContractorDetailsPK hrContractorDetailsPK;
    @Basic(optional = false)
    @Column(name = "CONT_FIR_NAME")
    private String contFirName;
    @Column(name = "CONT_MID_NAME")
    private String contMidName;
    @Column(name = "CONT_LAST_NAME")
    private String contLastName;
    @Column(name = "CONT_ADDRESS")
    private String contAddress;
    @Column(name = "CONT_CITY")
    private String contCity;
    @Column(name = "CONT_PIN")
    private String contPin;
    @Column(name = "CONT_STATE")
    private String contState;
    @Column(name = "FAX_NUMBER")
    private String faxNumber;
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @Column(name = "TELE_NUMBER")
    private String teleNumber;
    @Column(name = "RESI_NUMBER")
    private String resiNumber;
    @Column(name = "EMAIL_NUMBER")
    private String emailNumber;
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

    public HrContractorDetails() {
    }

    public HrContractorDetails(HrContractorDetailsPK hrContractorDetailsPK) {
        this.hrContractorDetailsPK = hrContractorDetailsPK;
    }

    public HrContractorDetails(HrContractorDetailsPK hrContractorDetailsPK, String contFirName) {
        this.hrContractorDetailsPK = hrContractorDetailsPK;
        this.contFirName = contFirName;
    }

    public HrContractorDetails(int compCode, String contCode) {
        this.hrContractorDetailsPK = new HrContractorDetailsPK(compCode, contCode);
    }

    public HrContractorDetailsPK getHrContractorDetailsPK() {
        return hrContractorDetailsPK;
    }

    public void setHrContractorDetailsPK(HrContractorDetailsPK hrContractorDetailsPK) {
        this.hrContractorDetailsPK = hrContractorDetailsPK;
    }

    public String getContFirName() {
        return contFirName;
    }

    public void setContFirName(String contFirName) {
        this.contFirName = contFirName;
    }

    public String getContMidName() {
        return contMidName;
    }

    public void setContMidName(String contMidName) {
        this.contMidName = contMidName;
    }

    public String getContLastName() {
        return contLastName;
    }

    public void setContLastName(String contLastName) {
        this.contLastName = contLastName;
    }

    public String getContAddress() {
        return contAddress;
    }

    public void setContAddress(String contAddress) {
        this.contAddress = contAddress;
    }

    public String getContCity() {
        return contCity;
    }

    public void setContCity(String contCity) {
        this.contCity = contCity;
    }

    public String getContPin() {
        return contPin;
    }

    public void setContPin(String contPin) {
        this.contPin = contPin;
    }

    public String getContState() {
        return contState;
    }

    public void setContState(String contState) {
        this.contState = contState;
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

    public String getResiNumber() {
        return resiNumber;
    }

    public void setResiNumber(String resiNumber) {
        this.resiNumber = resiNumber;
    }

    public String getEmailNumber() {
        return emailNumber;
    }

    public void setEmailNumber(String emailNumber) {
        this.emailNumber = emailNumber;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrContractorDetailsPK != null ? hrContractorDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrContractorDetails)) {
            return false;
        }
        HrContractorDetails other = (HrContractorDetails) object;
        if ((this.hrContractorDetailsPK == null && other.hrContractorDetailsPK != null) || (this.hrContractorDetailsPK != null && !this.hrContractorDetailsPK.equals(other.hrContractorDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrContractorDetails[hrContractorDetailsPK=" + hrContractorDetailsPK + "]";
    }

}
