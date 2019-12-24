/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "hr_direct_rec")
@NamedQueries({@NamedQuery(name = "HrDirectRec.findAll", query = "SELECT h FROM HrDirectRec h"), @NamedQuery(name = "HrDirectRec.findByCompCode", query = "SELECT h FROM HrDirectRec h WHERE h.hrDirectRecPK.compCode = :compCode"), @NamedQuery(name = "HrDirectRec.findByArno", query = "SELECT h FROM HrDirectRec h WHERE h.hrDirectRecPK.arno = :arno"), @NamedQuery(name = "HrDirectRec.findByArdate", query = "SELECT h FROM HrDirectRec h WHERE h.ardate = :ardate"), @NamedQuery(name = "HrDirectRec.findByZoneCode", query = "SELECT h FROM HrDirectRec h WHERE h.zoneCode = :zoneCode"), @NamedQuery(name = "HrDirectRec.findByDesigCode", query = "SELECT h FROM HrDirectRec h WHERE h.desigCode = :desigCode"), @NamedQuery(name = "HrDirectRec.findByLocationCode", query = "SELECT h FROM HrDirectRec h WHERE h.locationCode = :locationCode"), @NamedQuery(name = "HrDirectRec.findByCandCode", query = "SELECT h FROM HrDirectRec h WHERE h.candCode = :candCode"), @NamedQuery(name = "HrDirectRec.findByCandName", query = "SELECT h FROM HrDirectRec h WHERE h.candName = :candName"), @NamedQuery(name = "HrDirectRec.findByFatherName", query = "SELECT h FROM HrDirectRec h WHERE h.fatherName = :fatherName"), @NamedQuery(name = "HrDirectRec.findByAppointmentDate", query = "SELECT h FROM HrDirectRec h WHERE h.appointmentDate = :appointmentDate"), @NamedQuery(name = "HrDirectRec.findByContactNo", query = "SELECT h FROM HrDirectRec h WHERE h.contactNo = :contactNo"), @NamedQuery(name = "HrDirectRec.findByBasicSalary", query = "SELECT h FROM HrDirectRec h WHERE h.basicSalary = :basicSalary"), @NamedQuery(name = "HrDirectRec.findByHra", query = "SELECT h FROM HrDirectRec h WHERE h.hra = :hra"), @NamedQuery(name = "HrDirectRec.findByTa", query = "SELECT h FROM HrDirectRec h WHERE h.ta = :ta"), @NamedQuery(name = "HrDirectRec.findByMedicalAllw", query = "SELECT h FROM HrDirectRec h WHERE h.medicalAllw = :medicalAllw"), @NamedQuery(name = "HrDirectRec.findByTotal", query = "SELECT h FROM HrDirectRec h WHERE h.total = :total"), @NamedQuery(name = "HrDirectRec.findByAddress", query = "SELECT h FROM HrDirectRec h WHERE h.address = :address"), @NamedQuery(name = "HrDirectRec.findByCity", query = "SELECT h FROM HrDirectRec h WHERE h.city = :city"), @NamedQuery(name = "HrDirectRec.findByState", query = "SELECT h FROM HrDirectRec h WHERE h.state = :state"), @NamedQuery(name = "HrDirectRec.findByPin", query = "SELECT h FROM HrDirectRec h WHERE h.pin = :pin"), @NamedQuery(name = "HrDirectRec.findByEmailId", query = "SELECT h FROM HrDirectRec h WHERE h.emailId = :emailId"), @NamedQuery(name = "HrDirectRec.findByJobStatus", query = "SELECT h FROM HrDirectRec h WHERE h.jobStatus = :jobStatus"), @NamedQuery(name = "HrDirectRec.findByRemarks", query = "SELECT h FROM HrDirectRec h WHERE h.remarks = :remarks"), @NamedQuery(name = "HrDirectRec.findByEffectiveDate", query = "SELECT h FROM HrDirectRec h WHERE h.effectiveDate = :effectiveDate"), @NamedQuery(name = "HrDirectRec.findByQualCode", query = "SELECT h FROM HrDirectRec h WHERE h.qualCode = :qualCode"), @NamedQuery(name = "HrDirectRec.findBySuperId", query = "SELECT h FROM HrDirectRec h WHERE h.superId = :superId"), @NamedQuery(name = "HrDirectRec.findByInitiatorId", query = "SELECT h FROM HrDirectRec h WHERE h.initiatorId = :initiatorId"), @NamedQuery(name = "HrDirectRec.findByDeptHeadId", query = "SELECT h FROM HrDirectRec h WHERE h.deptHeadId = :deptHeadId"), @NamedQuery(name = "HrDirectRec.findByHrdApproval", query = "SELECT h FROM HrDirectRec h WHERE h.hrdApproval = :hrdApproval"), @NamedQuery(name = "HrDirectRec.findByMdApproval", query = "SELECT h FROM HrDirectRec h WHERE h.mdApproval = :mdApproval"), @NamedQuery(name = "HrDirectRec.findByStatFlag", query = "SELECT h FROM HrDirectRec h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrDirectRec.findByStatUpFlag", query = "SELECT h FROM HrDirectRec h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrDirectRec.findByModDate", query = "SELECT h FROM HrDirectRec h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrDirectRec.findByDefaultComp", query = "SELECT h FROM HrDirectRec h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrDirectRec.findByAuthBy", query = "SELECT h FROM HrDirectRec h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrDirectRec.findByEnteredBy", query = "SELECT h FROM HrDirectRec h WHERE h.enteredBy = :enteredBy")})
public class HrDirectRec extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrDirectRecPK hrDirectRecPK;
    @Basic(optional = false)
    @Column(name = "ARDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ardate;
    @Basic(optional = false)
    @Column(name = "ZONE_CODE")
    private String zoneCode;
    @Basic(optional = false)
    @Column(name = "DESIG_CODE")
    private String desigCode;
    @Basic(optional = false)
    @Column(name = "LOCATION_CODE")
    private String locationCode;
    @Basic(optional = false)
    @Column(name = "CAND_CODE")
    private long candCode;
    @Basic(optional = false)
    @Column(name = "CAND_NAME")
    private String candName;
    @Column(name = "FATHER_NAME")
    private String fatherName;
    @Column(name = "APPOINTMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentDate;
    @Column(name = "CONTACT_NO")
    private String contactNo;
    @Column(name = "BASIC_SALARY")
    private Integer basicSalary;
    @Column(name = "HRA")
    private Integer hra;
    @Column(name = "TA")
    private Integer ta;
    @Column(name = "MEDICAL_ALLW")
    private Integer medicalAllw;
    @Column(name = "TOTAL")
    private Integer total;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "PIN")
    private String pin;
    @Column(name = "EMAIL_ID")
    private String emailId;
    @Column(name = "JOB_STATUS")
    private Character jobStatus;
    @Column(name = "REMARKS")
    private String remarks;
    @Column(name = "EFFECTIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;
    @Column(name = "QUAL_CODE")
    private String qualCode;
    @Column(name = "SUPER_ID")
    private BigInteger superId;
    @Column(name = "INITIATOR_ID")
    private BigInteger initiatorId;
    @Column(name = "DEPT_HEAD_ID")
    private BigInteger deptHeadId;
    @Column(name = "HRD_APPROVAL")
    private Character hrdApproval;
    @Column(name = "MD_APPROVAL")
    private Character mdApproval;
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
    @Column(name = "DEFAULT_COMP")
    private int defaultComp;
    @Basic(optional = false)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;

    public HrDirectRec() {
    }

    public HrDirectRec(HrDirectRecPK hrDirectRecPK) {
        this.hrDirectRecPK = hrDirectRecPK;
    }

    public HrDirectRec(HrDirectRecPK hrDirectRecPK, Date ardate, String zoneCode, String desigCode, String locationCode, long candCode, String candName, String statFlag, String statUpFlag, Date modDate, int defaultComp, String authBy, String enteredBy) {
        this.hrDirectRecPK = hrDirectRecPK;
        this.ardate = ardate;
        this.zoneCode = zoneCode;
        this.desigCode = desigCode;
        this.locationCode = locationCode;
        this.candCode = candCode;
        this.candName = candName;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.defaultComp = defaultComp;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrDirectRec(int compCode, String arno) {
        this.hrDirectRecPK = new HrDirectRecPK(compCode, arno);
    }

    public HrDirectRecPK getHrDirectRecPK() {
        return hrDirectRecPK;
    }

    public void setHrDirectRecPK(HrDirectRecPK hrDirectRecPK) {
        this.hrDirectRecPK = hrDirectRecPK;
    }

    public Date getArdate() {
        return ardate;
    }

    public void setArdate(Date ardate) {
        this.ardate = ardate;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getDesigCode() {
        return desigCode;
    }

    public void setDesigCode(String desigCode) {
        this.desigCode = desigCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public long getCandCode() {
        return candCode;
    }

    public void setCandCode(long candCode) {
        this.candCode = candCode;
    }

    public String getCandName() {
        return candName;
    }

    public void setCandName(String candName) {
        this.candName = candName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Integer getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Integer basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Integer getHra() {
        return hra;
    }

    public void setHra(Integer hra) {
        this.hra = hra;
    }

    public Integer getTa() {
        return ta;
    }

    public void setTa(Integer ta) {
        this.ta = ta;
    }

    public Integer getMedicalAllw() {
        return medicalAllw;
    }

    public void setMedicalAllw(Integer medicalAllw) {
        this.medicalAllw = medicalAllw;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Character getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Character jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getQualCode() {
        return qualCode;
    }

    public void setQualCode(String qualCode) {
        this.qualCode = qualCode;
    }

    public BigInteger getSuperId() {
        return superId;
    }

    public void setSuperId(BigInteger superId) {
        this.superId = superId;
    }

    public BigInteger getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(BigInteger initiatorId) {
        this.initiatorId = initiatorId;
    }

    public BigInteger getDeptHeadId() {
        return deptHeadId;
    }

    public void setDeptHeadId(BigInteger deptHeadId) {
        this.deptHeadId = deptHeadId;
    }

    public Character getHrdApproval() {
        return hrdApproval;
    }

    public void setHrdApproval(Character hrdApproval) {
        this.hrdApproval = hrdApproval;
    }

    public Character getMdApproval() {
        return mdApproval;
    }

    public void setMdApproval(Character mdApproval) {
        this.mdApproval = mdApproval;
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

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
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
        hash += (hrDirectRecPK != null ? hrDirectRecPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDirectRec)) {
            return false;
        }
        HrDirectRec other = (HrDirectRec) object;
        if ((this.hrDirectRecPK == null && other.hrDirectRecPK != null) || (this.hrDirectRecPK != null && !this.hrDirectRecPK.equals(other.hrDirectRecPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrDirectRec[hrDirectRecPK=" + hrDirectRecPK + "]";
    }

}
