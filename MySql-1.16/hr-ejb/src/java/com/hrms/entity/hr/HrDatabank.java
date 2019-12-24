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
@Table(name = "hr_databank")
@NamedQueries({@NamedQuery(name = "HrDatabank.findAll", query = "SELECT h FROM HrDatabank h"),
@NamedQuery(name = "HrDatabank.findByCompCode", query = "SELECT h FROM HrDatabank h WHERE h.hrDatabankPK.compCode = :compCode"),
@NamedQuery(name = "HrDatabank.findByCandCode", query = "SELECT h FROM HrDatabank h WHERE h.candCode = :candCode"),
@NamedQuery(name = "HrDatabank.findByCandId", query = "SELECT h FROM HrDatabank h WHERE h.hrDatabankPK.candId = :candId"),
@NamedQuery(name = "HrDatabank.findByAdvtCode", query = "SELECT h FROM HrDatabank h WHERE h.hrDatabankPK.advtCode = :advtCode"),
@NamedQuery(name = "HrDatabank.findByJobCode", query = "SELECT h FROM HrDatabank h WHERE h.hrDatabankPK.jobCode = :jobCode"),
@NamedQuery(name = "HrDatabank.findByCandFirName", query = "SELECT h FROM HrDatabank h WHERE h.candFirName = :candFirName"),
@NamedQuery(name = "HrDatabank.findByCandMidName", query = "SELECT h FROM HrDatabank h WHERE h.candMidName = :candMidName"),
@NamedQuery(name = "HrDatabank.findByCandLastName", query = "SELECT h FROM HrDatabank h WHERE h.candLastName = :candLastName"),
@NamedQuery(name = "HrDatabank.findByCandContAdd", query = "SELECT h FROM HrDatabank h WHERE h.candContAdd = :candContAdd"),
@NamedQuery(name = "HrDatabank.findByCandContPin", query = "SELECT h FROM HrDatabank h WHERE h.candContPin = :candContPin"),
@NamedQuery(name = "HrDatabank.findByCandContCity", query = "SELECT h FROM HrDatabank h WHERE h.candContCity = :candContCity"),
@NamedQuery(name = "HrDatabank.findByCandContState", query = "SELECT h FROM HrDatabank h WHERE h.candContState = :candContState"),
@NamedQuery(name = "HrDatabank.findByCandPermAdd", query = "SELECT h FROM HrDatabank h WHERE h.candPermAdd = :candPermAdd"),
@NamedQuery(name = "HrDatabank.findByCandPermPin", query = "SELECT h FROM HrDatabank h WHERE h.candPermPin = :candPermPin"),
@NamedQuery(name = "HrDatabank.findByCandPermCity", query = "SELECT h FROM HrDatabank h WHERE h.candPermCity = :candPermCity"),
@NamedQuery(name = "HrDatabank.findByCandPermState", query = "SELECT h FROM HrDatabank h WHERE h.candPermState = :candPermState"),
@NamedQuery(name = "HrDatabank.findByCandOffTel", query = "SELECT h FROM HrDatabank h WHERE h.candOffTel = :candOffTel"),
@NamedQuery(name = "HrDatabank.findByCandOffFax", query = "SELECT h FROM HrDatabank h WHERE h.candOffFax = :candOffFax"),
@NamedQuery(name = "HrDatabank.findByCandOffEmail", query = "SELECT h FROM HrDatabank h WHERE h.candOffEmail = :candOffEmail"),
@NamedQuery(name = "HrDatabank.findByCandResTel", query = "SELECT h FROM HrDatabank h WHERE h.candResTel = :candResTel"),
@NamedQuery(name = "HrDatabank.findByCandResFax", query = "SELECT h FROM HrDatabank h WHERE h.candResFax = :candResFax"),
@NamedQuery(name = "HrDatabank.findByCandResEmail", query = "SELECT h FROM HrDatabank h WHERE h.candResEmail = :candResEmail"),
@NamedQuery(name = "HrDatabank.findByDtBirth", query = "SELECT h FROM HrDatabank h WHERE h.dtBirth = :dtBirth"),
@NamedQuery(name = "HrDatabank.findBySex", query = "SELECT h FROM HrDatabank h WHERE h.sex = :sex"),
@NamedQuery(name = "HrDatabank.findBySpecialCode1", query = "SELECT h FROM HrDatabank h WHERE h.specialCode1 = :specialCode1"),
@NamedQuery(name = "HrDatabank.findBySpecialCode2", query = "SELECT h FROM HrDatabank h WHERE h.specialCode2 = :specialCode2"),
@NamedQuery(name = "HrDatabank.findByTotExpr", query = "SELECT h FROM HrDatabank h WHERE h.totExpr = :totExpr"),
@NamedQuery(name = "HrDatabank.findByCurrExpr", query = "SELECT h FROM HrDatabank h WHERE h.currExpr = :currExpr"),
@NamedQuery(name = "HrDatabank.findByAutoExpr", query = "SELECT h FROM HrDatabank h WHERE h.autoExpr = :autoExpr"),
@NamedQuery(name = "HrDatabank.findByPostApplied1", query = "SELECT h FROM HrDatabank h WHERE h.postApplied1 = :postApplied1"),
@NamedQuery(name = "HrDatabank.findByPostApplied2", query = "SELECT h FROM HrDatabank h WHERE h.postApplied2 = :postApplied2"),
@NamedQuery(name = "HrDatabank.findByZone", query = "SELECT h FROM HrDatabank h WHERE h.zone = :zone"),
@NamedQuery(name = "HrDatabank.findByLocatCode", query = "SELECT h FROM HrDatabank h WHERE h.locatCode = :locatCode"),
@NamedQuery(name = "HrDatabank.findByDesgCode", query = "SELECT h FROM HrDatabank h WHERE h.desgCode = :desgCode"),
@NamedQuery(name = "HrDatabank.findByConsCode", query = "SELECT h FROM HrDatabank h WHERE h.consCode = :consCode"),
@NamedQuery(name = "HrDatabank.findByCurrSal", query = "SELECT h FROM HrDatabank h WHERE h.currSal = :currSal"),
@NamedQuery(name = "HrDatabank.findByCurrDesg", query = "SELECT h FROM HrDatabank h WHERE h.currDesg = :currDesg"),
@NamedQuery(name = "HrDatabank.findByCurrCname", query = "SELECT h FROM HrDatabank h WHERE h.currCname = :currCname"),
@NamedQuery(name = "HrDatabank.findByCompAdd1", query = "SELECT h FROM HrDatabank h WHERE h.compAdd1 = :compAdd1"),
@NamedQuery(name = "HrDatabank.findByCompAdd2", query = "SELECT h FROM HrDatabank h WHERE h.compAdd2 = :compAdd2"),
@NamedQuery(name = "HrDatabank.findByCompCity", query = "SELECT h FROM HrDatabank h WHERE h.compCity = :compCity"),
@NamedQuery(name = "HrDatabank.findByCompState", query = "SELECT h FROM HrDatabank h WHERE h.compState = :compState"),
@NamedQuery(name = "HrDatabank.findByCompPin", query = "SELECT h FROM HrDatabank h WHERE h.compPin = :compPin"),
@NamedQuery(name = "HrDatabank.findByExpSal", query = "SELECT h FROM HrDatabank h WHERE h.expSal = :expSal"),
@NamedQuery(name = "HrDatabank.findByJoinSal", query = "SELECT h FROM HrDatabank h WHERE h.joinSal = :joinSal"),
@NamedQuery(name = "HrDatabank.findByJoinDesg", query = "SELECT h FROM HrDatabank h WHERE h.joinDesg = :joinDesg"),
@NamedQuery(name = "HrDatabank.findByServiceLen", query = "SELECT h FROM HrDatabank h WHERE h.serviceLen = :serviceLen"),
@NamedQuery(name = "HrDatabank.findByStatus", query = "SELECT h FROM HrDatabank h WHERE h.status = :status"),
@NamedQuery(name = "HrDatabank.findByOrgType", query = "SELECT h FROM HrDatabank h WHERE h.orgType = :orgType"),
@NamedQuery(name = "HrDatabank.findBySkillCode", query = "SELECT h FROM HrDatabank h WHERE h.skillCode = :skillCode"),
@NamedQuery(name = "HrDatabank.findByFreeDay", query = "SELECT h FROM HrDatabank h WHERE h.freeDay = :freeDay"),
@NamedQuery(name = "HrDatabank.findByNation", query = "SELECT h FROM HrDatabank h WHERE h.nation = :nation"),
@NamedQuery(name = "HrDatabank.findByReligion", query = "SELECT h FROM HrDatabank h WHERE h.religion = :religion"),
@NamedQuery(name = "HrDatabank.findByMarital", query = "SELECT h FROM HrDatabank h WHERE h.marital = :marital"),
@NamedQuery(name = "HrDatabank.findByBloodGrp", query = "SELECT h FROM HrDatabank h WHERE h.bloodGrp = :bloodGrp"),
@NamedQuery(name = "HrDatabank.findByJoiningPeriod", query = "SELECT h FROM HrDatabank h WHERE h.joiningPeriod = :joiningPeriod"),
@NamedQuery(name = "HrDatabank.findByPassportNo", query = "SELECT h FROM HrDatabank h WHERE h.passportNo = :passportNo"),
@NamedQuery(name = "HrDatabank.findByPassportDate", query = "SELECT h FROM HrDatabank h WHERE h.passportDate = :passportDate"),
@NamedQuery(name = "HrDatabank.findByVisaDet", query = "SELECT h FROM HrDatabank h WHERE h.visaDet = :visaDet"),
@NamedQuery(name = "HrDatabank.findByFathName", query = "SELECT h FROM HrDatabank h WHERE h.fathName = :fathName"),
@NamedQuery(name = "HrDatabank.findByFatherOcc", query = "SELECT h FROM HrDatabank h WHERE h.fatherOcc = :fatherOcc"),
@NamedQuery(name = "HrDatabank.findByFatherDesig", query = "SELECT h FROM HrDatabank h WHERE h.fatherDesig = :fatherDesig"),
@NamedQuery(name = "HrDatabank.findByFatherOrg", query = "SELECT h FROM HrDatabank h WHERE h.fatherOrg = :fatherOrg"),
@NamedQuery(name = "HrDatabank.findByFatherPhone", query = "SELECT h FROM HrDatabank h WHERE h.fatherPhone = :fatherPhone"),
@NamedQuery(name = "HrDatabank.findByProfMember", query = "SELECT h FROM HrDatabank h WHERE h.profMember = :profMember"),
@NamedQuery(name = "HrDatabank.findByIndivMember", query = "SELECT h FROM HrDatabank h WHERE h.indivMember = :indivMember"),
@NamedQuery(name = "HrDatabank.findByCallInt", query = "SELECT h FROM HrDatabank h WHERE h.callInt = :callInt"),
@NamedQuery(name = "HrDatabank.findByPrevEmp", query = "SELECT h FROM HrDatabank h WHERE h.prevEmp = :prevEmp"),
@NamedQuery(name = "HrDatabank.findByPrevEmpCode", query = "SELECT h FROM HrDatabank h WHERE h.prevEmpCode = :prevEmpCode"),
@NamedQuery(name = "HrDatabank.findByDurFrom", query = "SELECT h FROM HrDatabank h WHERE h.durFrom = :durFrom"),
@NamedQuery(name = "HrDatabank.findByDurTo", query = "SELECT h FROM HrDatabank h WHERE h.durTo = :durTo"),
@NamedQuery(name = "HrDatabank.findByUnitName", query = "SELECT h FROM HrDatabank h WHERE h.unitName = :unitName"),
@NamedQuery(name = "HrDatabank.findByPrevDeptCode", query = "SELECT h FROM HrDatabank h WHERE h.prevDeptCode = :prevDeptCode"),
@NamedQuery(name = "HrDatabank.findByPrevDesgCode", query = "SELECT h FROM HrDatabank h WHERE h.prevDesgCode = :prevDesgCode"),
@NamedQuery(name = "HrDatabank.findBySalaryDrawn", query = "SELECT h FROM HrDatabank h WHERE h.salaryDrawn = :salaryDrawn"),
@NamedQuery(name = "HrDatabank.findByReasonLeave", query = "SELECT h FROM HrDatabank h WHERE h.reasonLeave = :reasonLeave"),
@NamedQuery(name = "HrDatabank.findByEvalFlag", query = "SELECT h FROM HrDatabank h WHERE h.evalFlag = :evalFlag"),
@NamedQuery(name = "HrDatabank.findByDataTransfer", query = "SELECT h FROM HrDatabank h WHERE h.dataTransfer = :dataTransfer"),
@NamedQuery(name = "HrDatabank.findByDefaultComp", query = "SELECT h FROM HrDatabank h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrDatabank.findByStatFlag", query = "SELECT h FROM HrDatabank h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrDatabank.findByStatUpFlag", query = "SELECT h FROM HrDatabank h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrDatabank.findByModDate", query = "SELECT h FROM HrDatabank h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrDatabank.findByAuthBy", query = "SELECT h FROM HrDatabank h WHERE h.authBy = :authBy"), 
@NamedQuery(name = "HrDatabank.findByCompanyCode", query = "SELECT h FROM HrDatabank h WHERE h.hrDatabankPK.compCode = :value1 and h.callInt = :value2"),
@NamedQuery(name = "HrDatabank.findByCandidateId", query = "SELECT h FROM HrDatabank h WHERE h.hrDatabankPK.compCode=:value1 and h.hrDatabankPK.candId like:value2"),
@NamedQuery(name = "HrDatabank.findByEnteredBy", query = "SELECT h FROM HrDatabank h WHERE h.enteredBy = :enteredBy")})
public class HrDatabank extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrDatabankPK hrDatabankPK;
    @Basic(optional = false)
    @Column(name = "CAND_CODE")
    private long candCode;
    @Basic(optional = false)
    @Column(name = "CAND_FIR_NAME")
    private String candFirName;
    @Column(name = "CAND_MID_NAME")
    private String candMidName;
    @Column(name = "CAND_LAST_NAME")
    private String candLastName;
    @Column(name = "CAND_CONT_ADD")
    private String candContAdd;
    @Column(name = "CAND_CONT_PIN")
    private String candContPin;
    @Column(name = "CAND_CONT_CITY")
    private String candContCity;
    @Column(name = "CAND_CONT_STATE")
    private String candContState;
    @Column(name = "CAND_PERM_ADD")
    private String candPermAdd;
    @Column(name = "CAND_PERM_PIN")
    private String candPermPin;
    @Column(name = "CAND_PERM_CITY")
    private String candPermCity;
    @Column(name = "CAND_PERM_STATE")
    private String candPermState;
    @Column(name = "CAND_OFF_TEL")
    private String candOffTel;
    @Column(name = "CAND_OFF_FAX")
    private String candOffFax;
    @Column(name = "CAND_OFF_EMAIL")
    private String candOffEmail;
    @Column(name = "CAND_RES_TEL")
    private String candResTel;
    @Column(name = "CAND_RES_FAX")
    private String candResFax;
    @Column(name = "CAND_RES_EMAIL")
    private String candResEmail;
    @Column(name = "DT_BIRTH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtBirth;
    @Basic(optional = false)
    @Column(name = "SEX")
    private char sex;
    @Column(name = "SPECIAL_CODE1")
    private String specialCode1;
    @Column(name = "SPECIAL_CODE2")
    private String specialCode2;
    @Column(name = "TOT_EXPR")
    private Double totExpr;
    @Column(name = "CURR_EXPR")
    private Double currExpr;
    @Column(name = "AUTO_EXPR")
    private Double autoExpr;
    @Basic(optional = false)
    @Column(name = "POST_APPLIED1")
    private String postApplied1;
    @Column(name = "POST_APPLIED2")
    private String postApplied2;
    @Basic(optional = false)
    @Column(name = "ZONE")
    private String zone;
    @Basic(optional = false)
    @Column(name = "LOCAT_CODE")
    private String locatCode;
    @Column(name = "DESG_CODE")
    private String desgCode;
    @Column(name = "CONS_CODE")
    private String consCode;
    @Column(name = "CURR_SAL")
    private Double currSal;
    @Column(name = "CURR_DESG")
    private String currDesg;
    @Column(name = "CURR_CNAME")
    private String currCname;
    @Column(name = "COMP_ADD1")
    private String compAdd1;
    @Column(name = "COMP_ADD2")
    private String compAdd2;
    @Column(name = "COMP_CITY")
    private String compCity;
    @Column(name = "COMP_STATE")
    private String compState;
    @Column(name = "COMP_PIN")
    private String compPin;
    @Column(name = "EXP_SAL")
    private Double expSal;
    @Column(name = "JOIN_SAL")
    private Double joinSal;
    @Column(name = "JOIN_DESG")
    private String joinDesg;
    @Column(name = "SERVICE_LEN")
    private Double serviceLen;
    @Column(name = "STATUS")
    private Character status;
    @Column(name = "ORG_TYPE")
    private String orgType;
    @Column(name = "SKILL_CODE")
    private String skillCode;
    @Column(name = "FREE_DAY")
    private Integer freeDay;
    @Column(name = "NATION")
    private String nation;
    @Column(name = "RELIGION")
    private String religion;
    @Column(name = "MARITAL")
    private Character marital;
    @Column(name = "BLOOD_GRP")
    private String bloodGrp;
    @Column(name = "JOINING_PERIOD")
    private Integer joiningPeriod;
    @Column(name = "PASSPORT_NO")
    private String passportNo;
    @Column(name = "PASSPORT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passportDate;
    @Column(name = "VISA_DET")
    private String visaDet;
    @Column(name = "FATH_NAME")
    private String fathName;
    @Column(name = "FATHER_OCC")
    private String fatherOcc;
    @Column(name = "FATHER_DESIG")
    private String fatherDesig;
    @Column(name = "FATHER_ORG")
    private String fatherOrg;
    @Column(name = "FATHER_PHONE")
    private String fatherPhone;
    @Column(name = "PROF_MEMBER")
    private String profMember;
    @Column(name = "INDIV_MEMBER")
    private String indivMember;
    @Column(name = "CALL_INT")
    private Character callInt;
    @Column(name = "PREV_EMP")
    private Character prevEmp;
    @Column(name = "PREV_EMP_CODE")
    private String prevEmpCode;
    @Column(name = "DUR_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date durFrom;
    @Column(name = "DUR_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date durTo;
    @Column(name = "UNIT_NAME")
    private String unitName;
    @Column(name = "PREV_DEPT_CODE")
    private String prevDeptCode;
    @Column(name = "PREV_DESG_CODE")
    private String prevDesgCode;
    @Column(name = "SALARY_DRAWN")
    private Double salaryDrawn;
    @Column(name = "REASON_LEAVE")
    private String reasonLeave;
    @Column(name = "EVAL_FLAG")
    private Character evalFlag;
    @Column(name = "DATA_TRANSFER")
    private String dataTransfer;
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

    public HrDatabank() {
    }

    public HrDatabank(HrDatabankPK hrDatabankPK) {
        this.hrDatabankPK = hrDatabankPK;
    }

    public HrDatabank(HrDatabankPK hrDatabankPK, long candCode, String candFirName, char sex, String postApplied1, String zone, String locatCode) {
        this.hrDatabankPK = hrDatabankPK;
        this.candCode = candCode;
        this.candFirName = candFirName;
        this.sex = sex;
        this.postApplied1 = postApplied1;
        this.zone = zone;
        this.locatCode = locatCode;
    }

    public HrDatabank(int compCode, String candId, String advtCode, String jobCode) {
        this.hrDatabankPK = new HrDatabankPK(compCode, candId, advtCode, jobCode);
    }

    public HrDatabankPK getHrDatabankPK() {
        return hrDatabankPK;
    }

    public void setHrDatabankPK(HrDatabankPK hrDatabankPK) {
        this.hrDatabankPK = hrDatabankPK;
    }

    public long getCandCode() {
        return candCode;
    }

    public void setCandCode(long candCode) {
        this.candCode = candCode;
    }

    public String getCandFirName() {
        return candFirName;
    }

    public void setCandFirName(String candFirName) {
        this.candFirName = candFirName;
    }

    public String getCandMidName() {
        return candMidName;
    }

    public void setCandMidName(String candMidName) {
        this.candMidName = candMidName;
    }

    public String getCandLastName() {
        return candLastName;
    }

    public void setCandLastName(String candLastName) {
        this.candLastName = candLastName;
    }

    public String getCandContAdd() {
        return candContAdd;
    }

    public void setCandContAdd(String candContAdd) {
        this.candContAdd = candContAdd;
    }

    public String getCandContPin() {
        return candContPin;
    }

    public void setCandContPin(String candContPin) {
        this.candContPin = candContPin;
    }

    public String getCandContCity() {
        return candContCity;
    }

    public void setCandContCity(String candContCity) {
        this.candContCity = candContCity;
    }

    public String getCandContState() {
        return candContState;
    }

    public void setCandContState(String candContState) {
        this.candContState = candContState;
    }

    public String getCandPermAdd() {
        return candPermAdd;
    }

    public void setCandPermAdd(String candPermAdd) {
        this.candPermAdd = candPermAdd;
    }

    public String getCandPermPin() {
        return candPermPin;
    }

    public void setCandPermPin(String candPermPin) {
        this.candPermPin = candPermPin;
    }

    public String getCandPermCity() {
        return candPermCity;
    }

    public void setCandPermCity(String candPermCity) {
        this.candPermCity = candPermCity;
    }

    public String getCandPermState() {
        return candPermState;
    }

    public void setCandPermState(String candPermState) {
        this.candPermState = candPermState;
    }

    public String getCandOffTel() {
        return candOffTel;
    }

    public void setCandOffTel(String candOffTel) {
        this.candOffTel = candOffTel;
    }

    public String getCandOffFax() {
        return candOffFax;
    }

    public void setCandOffFax(String candOffFax) {
        this.candOffFax = candOffFax;
    }

    public String getCandOffEmail() {
        return candOffEmail;
    }

    public void setCandOffEmail(String candOffEmail) {
        this.candOffEmail = candOffEmail;
    }

    public String getCandResTel() {
        return candResTel;
    }

    public void setCandResTel(String candResTel) {
        this.candResTel = candResTel;
    }

    public String getCandResFax() {
        return candResFax;
    }

    public void setCandResFax(String candResFax) {
        this.candResFax = candResFax;
    }

    public String getCandResEmail() {
        return candResEmail;
    }

    public void setCandResEmail(String candResEmail) {
        this.candResEmail = candResEmail;
    }

    public Date getDtBirth() {
        return dtBirth;
    }

    public void setDtBirth(Date dtBirth) {
        this.dtBirth = dtBirth;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getSpecialCode1() {
        return specialCode1;
    }

    public void setSpecialCode1(String specialCode1) {
        this.specialCode1 = specialCode1;
    }

    public String getSpecialCode2() {
        return specialCode2;
    }

    public void setSpecialCode2(String specialCode2) {
        this.specialCode2 = specialCode2;
    }

    public Double getTotExpr() {
        return totExpr;
    }

    public void setTotExpr(Double totExpr) {
        this.totExpr = totExpr;
    }

    public Double getCurrExpr() {
        return currExpr;
    }

    public void setCurrExpr(Double currExpr) {
        this.currExpr = currExpr;
    }

    public Double getAutoExpr() {
        return autoExpr;
    }

    public void setAutoExpr(Double autoExpr) {
        this.autoExpr = autoExpr;
    }

    public String getPostApplied1() {
        return postApplied1;
    }

    public void setPostApplied1(String postApplied1) {
        this.postApplied1 = postApplied1;
    }

    public String getPostApplied2() {
        return postApplied2;
    }

    public void setPostApplied2(String postApplied2) {
        this.postApplied2 = postApplied2;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getLocatCode() {
        return locatCode;
    }

    public void setLocatCode(String locatCode) {
        this.locatCode = locatCode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getConsCode() {
        return consCode;
    }

    public void setConsCode(String consCode) {
        this.consCode = consCode;
    }

    public Double getCurrSal() {
        return currSal;
    }

    public void setCurrSal(Double currSal) {
        this.currSal = currSal;
    }

    public String getCurrDesg() {
        return currDesg;
    }

    public void setCurrDesg(String currDesg) {
        this.currDesg = currDesg;
    }

    public String getCurrCname() {
        return currCname;
    }

    public void setCurrCname(String currCname) {
        this.currCname = currCname;
    }

    public String getCompAdd1() {
        return compAdd1;
    }

    public void setCompAdd1(String compAdd1) {
        this.compAdd1 = compAdd1;
    }

    public String getCompAdd2() {
        return compAdd2;
    }

    public void setCompAdd2(String compAdd2) {
        this.compAdd2 = compAdd2;
    }

    public String getCompCity() {
        return compCity;
    }

    public void setCompCity(String compCity) {
        this.compCity = compCity;
    }

    public String getCompState() {
        return compState;
    }

    public void setCompState(String compState) {
        this.compState = compState;
    }

    public String getCompPin() {
        return compPin;
    }

    public void setCompPin(String compPin) {
        this.compPin = compPin;
    }

    public Double getExpSal() {
        return expSal;
    }

    public void setExpSal(Double expSal) {
        this.expSal = expSal;
    }

    public Double getJoinSal() {
        return joinSal;
    }

    public void setJoinSal(Double joinSal) {
        this.joinSal = joinSal;
    }

    public String getJoinDesg() {
        return joinDesg;
    }

    public void setJoinDesg(String joinDesg) {
        this.joinDesg = joinDesg;
    }

    public Double getServiceLen() {
        return serviceLen;
    }

    public void setServiceLen(Double serviceLen) {
        this.serviceLen = serviceLen;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public Integer getFreeDay() {
        return freeDay;
    }

    public void setFreeDay(Integer freeDay) {
        this.freeDay = freeDay;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Character getMarital() {
        return marital;
    }

    public void setMarital(Character marital) {
        this.marital = marital;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }

    public Integer getJoiningPeriod() {
        return joiningPeriod;
    }

    public void setJoiningPeriod(Integer joiningPeriod) {
        this.joiningPeriod = joiningPeriod;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(Date passportDate) {
        this.passportDate = passportDate;
    }

    public String getVisaDet() {
        return visaDet;
    }

    public void setVisaDet(String visaDet) {
        this.visaDet = visaDet;
    }

    public String getFathName() {
        return fathName;
    }

    public void setFathName(String fathName) {
        this.fathName = fathName;
    }

    public String getFatherOcc() {
        return fatherOcc;
    }

    public void setFatherOcc(String fatherOcc) {
        this.fatherOcc = fatherOcc;
    }

    public String getFatherDesig() {
        return fatherDesig;
    }

    public void setFatherDesig(String fatherDesig) {
        this.fatherDesig = fatherDesig;
    }

    public String getFatherOrg() {
        return fatherOrg;
    }

    public void setFatherOrg(String fatherOrg) {
        this.fatherOrg = fatherOrg;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getProfMember() {
        return profMember;
    }

    public void setProfMember(String profMember) {
        this.profMember = profMember;
    }

    public String getIndivMember() {
        return indivMember;
    }

    public void setIndivMember(String indivMember) {
        this.indivMember = indivMember;
    }

    public Character getCallInt() {
        return callInt;
    }

    public void setCallInt(Character callInt) {
        this.callInt = callInt;
    }

    public Character getPrevEmp() {
        return prevEmp;
    }

    public void setPrevEmp(Character prevEmp) {
        this.prevEmp = prevEmp;
    }

    public String getPrevEmpCode() {
        return prevEmpCode;
    }

    public void setPrevEmpCode(String prevEmpCode) {
        this.prevEmpCode = prevEmpCode;
    }

    public Date getDurFrom() {
        return durFrom;
    }

    public void setDurFrom(Date durFrom) {
        this.durFrom = durFrom;
    }

    public Date getDurTo() {
        return durTo;
    }

    public void setDurTo(Date durTo) {
        this.durTo = durTo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPrevDeptCode() {
        return prevDeptCode;
    }

    public void setPrevDeptCode(String prevDeptCode) {
        this.prevDeptCode = prevDeptCode;
    }

    public String getPrevDesgCode() {
        return prevDesgCode;
    }

    public void setPrevDesgCode(String prevDesgCode) {
        this.prevDesgCode = prevDesgCode;
    }

    public Double getSalaryDrawn() {
        return salaryDrawn;
    }

    public void setSalaryDrawn(Double salaryDrawn) {
        this.salaryDrawn = salaryDrawn;
    }

    public String getReasonLeave() {
        return reasonLeave;
    }

    public void setReasonLeave(String reasonLeave) {
        this.reasonLeave = reasonLeave;
    }

    public Character getEvalFlag() {
        return evalFlag;
    }

    public void setEvalFlag(Character evalFlag) {
        this.evalFlag = evalFlag;
    }

    public String getDataTransfer() {
        return dataTransfer;
    }

    public void setDataTransfer(String dataTransfer) {
        this.dataTransfer = dataTransfer;
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
        hash += (hrDatabankPK != null ? hrDatabankPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDatabank)) {
            return false;
        }
        HrDatabank other = (HrDatabank) object;
        if ((this.hrDatabankPK == null && other.hrDatabankPK != null) || (this.hrDatabankPK != null && !this.hrDatabankPK.equals(other.hrDatabankPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hrPayroll.HrDatabank[hrDatabankPK=" + hrDatabankPK + "]";
    }

}
