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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_personnel_details_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPersonnelDetailsHis.findAll", query = "SELECT h FROM HrPersonnelDetailsHis h"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByAccomdType", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.accomdType = :accomdType"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByArea", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.area = :area"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByAuthBy", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByAutoExpr", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.autoExpr = :autoExpr"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBankAccountCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.bankAccountCode = :bankAccountCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBankCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.bankCode = :bankCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBasicSal", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.basicSal = :basicSal"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBirthCity", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.birthCity = :birthCity"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBirthDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.birthDate = :birthDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBirthState", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.birthState = :birthState"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBlock", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.block = :block"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBloodGrp", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.bloodGrp = :bloodGrp"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCareer", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.career = :career"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCatgCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.catgCode = :catgCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCertAdosDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.certAdosDate = :certAdosDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCertAdosNo", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.certAdosNo = :certAdosNo"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCertRef", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.certRef = :certRef"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCertTokNo", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.certTokNo = :certTokNo"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByChest", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.chest = :chest"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByChildren", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.children = :children"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCompanyLease", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.companyLease = :companyLease"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByConfirmationDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.confirmationDate = :confirmationDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByDefaultComp", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByDeptCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.deptCode = :deptCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByDesgCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.desgCode = :desgCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmailId", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.emailId = :emailId"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpCardNo", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empCardNo = :empCardNo"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpContAdd", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empContAdd = :empContAdd"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpContCity", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empContCity = :empContCity"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpContPin", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empContPin = :empContPin"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpContState", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empContState = :empContState"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpContTel", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empContTel = :empContTel"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpFirName", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empFirName = :empFirName"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpId", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empId = :empId"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpImage", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empImage = :empImage"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpLastName", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empLastName = :empLastName"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpMidName", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empMidName = :empMidName"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpName", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empName = :empName"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpPermAdd", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empPermAdd = :empPermAdd"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpPermCity", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empPermCity = :empPermCity"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpPermPin", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empPermPin = :empPermPin"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpPermState", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empPermState = :empPermState"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpPermTel", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empPermTel = :empPermTel"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpStatus", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empStatus = :empStatus"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpType", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empType = :empType"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEnteredBy", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEsiMember", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.esiMember = :esiMember"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByFathHusName", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.fathHusName = :fathHusName"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByFatherHusDesig", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.fatherHusDesig = :fatherHusDesig"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByFatherHusOcc", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.fatherHusOcc = :fatherHusOcc"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByFatherHusOrg", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.fatherHusOrg = :fatherHusOrg"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByFatherHusPhone", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.fatherHusPhone = :fatherHusPhone"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByFinAccountCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.finAccountCode = :finAccountCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByGradeCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.gradeCode = :gradeCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByGrossSal", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.grossSal = :grossSal"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByHealth", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.health = :health"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByHeight", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.height = :height"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByHouseholdAmt", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.householdAmt = :householdAmt"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByHraAmt", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.hraAmt = :hraAmt"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByIndivMember", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.indivMember = :indivMember"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByInsuranceNo", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.insuranceNo = :insuranceNo"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByJobdesc", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.jobdesc = :jobdesc"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByJoinDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.joinDate = :joinDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByLocatCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.locatCode = :locatCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByMaritalStatus", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.maritalStatus = :maritalStatus"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByMetro", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.metro = :metro"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByModDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByNation", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.nation = :nation"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByNoticePeriod", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.noticePeriod = :noticePeriod"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByOccCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.occCode = :occCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByOtEligibility", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.otEligibility = :otEligibility"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByPassportDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.passportDate = :passportDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByPassportNo", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.passportNo = :passportNo"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByPassword", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.password = :password"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByPayMode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.payMode = :payMode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByPayrollDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.payrollDate = :payrollDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByPfMember", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.pfMember = :pfMember"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByProbPeriod", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.probPeriod = :probPeriod"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByProfMember", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.profMember = :profMember"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByRelay", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.relay = :relay"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByRelayDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.relayDate = :relayDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByReligion", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.religion = :religion"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByRepTo", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.repTo = :repTo"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findBySex", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.sex = :sex"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByShiftCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.shiftCode = :shiftCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findBySkillCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.skillCode = :skillCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findBySpecialCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.specialCode = :specialCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByStatFlag", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByStatUpFlag", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findBySubdeptCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.subdeptCode = :subdeptCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByTimeIn", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.timeIn = :timeIn"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByTimeOut", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.timeOut = :timeOut"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByTotExpr", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.totExpr = :totExpr"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByTown", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.town = :town"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByTravOverseasStatus", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.travOverseasStatus = :travOverseasStatus"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByTrustMember", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.trustMember = :trustMember"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByUnitName", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.unitName = :unitName"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByVisaDet", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.visaDet = :visaDet"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByVpfMember", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.vpfMember = :vpfMember"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByWeddingDate", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.weddingDate = :weddingDate"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByWeight", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.weight = :weight"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByWorkStatus", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.workStatus = :workStatus"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByZones", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.zones = :zones"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCompCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.compCode = :compCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByEmpCode", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.empCode = :empCode"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByCustomerId", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.customerId = :customerId"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByPfAccount", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.pfAccount = :pfAccount"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByBaseBranch", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.baseBranch = :baseBranch"),
    @NamedQuery(name = "HrPersonnelDetailsHis.findByTxnId", query = "SELECT h FROM HrPersonnelDetailsHis h WHERE h.txnId = :txnId")})
public class HrPersonnelDetailsHis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "ACCOMD_TYPE")
    private String accomdType;
    @Size(max = 255)
    @Column(name = "AREA")
    private String area;
    @Size(max = 255)
    @Column(name = "AUTH_BY")
    private String authBy;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AUTO_EXPR")
    private Float autoExpr;
    @Size(max = 255)
    @Column(name = "BANK_ACCOUNT_CODE")
    private String bankAccountCode;
    @Size(max = 255)
    @Column(name = "BANK_CODE")
    private String bankCode;
    @Column(name = "BASIC_SAL")
    private Float basicSal;
    @Size(max = 255)
    @Column(name = "BIRTH_CITY")
    private String birthCity;
    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @Size(max = 255)
    @Column(name = "BIRTH_STATE")
    private String birthState;
    @Size(max = 255)
    @Column(name = "BLOCK")
    private String block;
    @Size(max = 255)
    @Column(name = "BLOOD_GRP")
    private String bloodGrp;
    @Size(max = 255)
    @Column(name = "CAREER")
    private String career;
    @Size(max = 255)
    @Column(name = "CATG_CODE")
    private String catgCode;
    @Column(name = "CERT_ADOS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date certAdosDate;
    @Size(max = 255)
    @Column(name = "CERT_ADOS_NO")
    private String certAdosNo;
    @Size(max = 255)
    @Column(name = "CERT_REF")
    private String certRef;
    @Size(max = 255)
    @Column(name = "CERT_TOK_NO")
    private String certTokNo;
    @Size(max = 255)
    @Column(name = "CHEST")
    private String chest;
    @Column(name = "CHILDREN")
    private Integer children;
    @Size(max = 255)
    @Column(name = "COMPANY_LEASE")
    private String companyLease;
    @Column(name = "CONFIRMATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmationDate;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Size(max = 255)
    @Column(name = "DEPT_CODE")
    private String deptCode;
    @Size(max = 255)
    @Column(name = "DESG_CODE")
    private String desgCode;
    @Size(max = 255)
    @Column(name = "EMAIL_ID")
    private String emailId;
    @Size(max = 255)
    @Column(name = "EMP_CARD_NO")
    private String empCardNo;
    @Size(max = 255)
    @Column(name = "EMP_CONT_ADD")
    private String empContAdd;
    @Size(max = 255)
    @Column(name = "EMP_CONT_CITY")
    private String empContCity;
    @Size(max = 255)
    @Column(name = "EMP_CONT_PIN")
    private String empContPin;
    @Size(max = 255)
    @Column(name = "EMP_CONT_STATE")
    private String empContState;
    @Size(max = 255)
    @Column(name = "EMP_CONT_TEL")
    private String empContTel;
    @Size(max = 255)
    @Column(name = "EMP_FIR_NAME")
    private String empFirName;
    @Size(max = 255)
    @Column(name = "EMP_ID")
    private String empId;
    @Column(name = "EMP_IMAGE")
    private Long empImage;
    @Size(max = 255)
    @Column(name = "EMP_LAST_NAME")
    private String empLastName;
    @Size(max = 255)
    @Column(name = "EMP_MID_NAME")
    private String empMidName;
    @Size(max = 255)
    @Column(name = "EMP_NAME")
    private String empName;
    @Size(max = 255)
    @Column(name = "EMP_PERM_ADD")
    private String empPermAdd;
    @Size(max = 255)
    @Column(name = "EMP_PERM_CITY")
    private String empPermCity;
    @Size(max = 255)
    @Column(name = "EMP_PERM_PIN")
    private String empPermPin;
    @Size(max = 255)
    @Column(name = "EMP_PERM_STATE")
    private String empPermState;
    @Size(max = 255)
    @Column(name = "EMP_PERM_TEL")
    private String empPermTel;
    @Column(name = "EMP_STATUS")
    private Character empStatus;
    @Size(max = 255)
    @Column(name = "EMP_TYPE")
    private String empType;
    @Size(max = 255)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @Column(name = "ESI_MEMBER")
    private Character esiMember;
    @Size(max = 255)
    @Column(name = "FATH_HUS_NAME")
    private String fathHusName;
    @Size(max = 255)
    @Column(name = "FATHER_HUS_DESIG")
    private String fatherHusDesig;
    @Size(max = 255)
    @Column(name = "FATHER_HUS_OCC")
    private String fatherHusOcc;
    @Size(max = 255)
    @Column(name = "FATHER_HUS_ORG")
    private String fatherHusOrg;
    @Size(max = 255)
    @Column(name = "FATHER_HUS_PHONE")
    private String fatherHusPhone;
    @Size(max = 255)
    @Column(name = "FIN_ACCOUNT_CODE")
    private String finAccountCode;
    @Size(max = 255)
    @Column(name = "GRADE_CODE")
    private String gradeCode;
    @Column(name = "GROSS_SAL")
    private Float grossSal;
    @Size(max = 255)
    @Column(name = "HEALTH")
    private String health;
    @Size(max = 255)
    @Column(name = "HEIGHT")
    private String height;
    @Column(name = "HOUSEHOLD_AMT")
    private Float householdAmt;
    @Column(name = "HRA_AMT")
    private Float hraAmt;
    @Size(max = 255)
    @Column(name = "INDIV_MEMBER")
    private String indivMember;
    @Size(max = 255)
    @Column(name = "INSURANCE_NO")
    private String insuranceNo;
    @Size(max = 255)
    @Column(name = "JOBDESC")
    private String jobdesc;
    @Column(name = "JOIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;
    @Size(max = 255)
    @Column(name = "LOCAT_CODE")
    private String locatCode;
    @Column(name = "MARITAL_STATUS")
    private Character maritalStatus;
    @Column(name = "METRO")
    private Character metro;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Size(max = 255)
    @Column(name = "NATION")
    private String nation;
    @Column(name = "NOTICE_PERIOD")
    private Float noticePeriod;
    @Size(max = 255)
    @Column(name = "OCC_CODE")
    private String occCode;
    @Column(name = "OT_ELIGIBILITY")
    private Character otEligibility;
    @Column(name = "PASSPORT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passportDate;
    @Size(max = 255)
    @Column(name = "PASSPORT_NO")
    private String passportNo;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "PAY_MODE")
    private String payMode;
    @Column(name = "PAYROLL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payrollDate;
    @Column(name = "PF_MEMBER")
    private Character pfMember;
    @Column(name = "PROB_PERIOD")
    private Float probPeriod;
    @Size(max = 255)
    @Column(name = "PROF_MEMBER")
    private String profMember;
    @Size(max = 255)
    @Column(name = "RELAY")
    private String relay;
    @Column(name = "RELAY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date relayDate;
    @Size(max = 255)
    @Column(name = "RETIREMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date retirementDate;
    @Size(max = 255)
    @Column(name = "RELIGION")
    private String religion;
    @Size(max = 255)
    @Column(name = "REP_TO")
    private String repTo;
    @Column(name = "SEX")
    private Character sex;
    @Size(max = 255)
    @Column(name = "SHIFT_CODE")
    private String shiftCode;
    @Size(max = 255)
    @Column(name = "SKILL_CODE")
    private String skillCode;
    @Size(max = 255)
    @Column(name = "SPECIAL_CODE")
    private String specialCode;
    @Size(max = 255)
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Size(max = 255)
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Size(max = 255)
    @Column(name = "SUBDEPT_CODE")
    private String subdeptCode;
    @Column(name = "TIME_IN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIn;
    @Column(name = "TIME_OUT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOut;
    @Column(name = "TOT_EXPR")
    private Float totExpr;
    @Size(max = 255)
    @Column(name = "TOWN")
    private String town;
    @Column(name = "TRAV_OVERSEAS_STATUS")
    private Character travOverseasStatus;
    @Column(name = "TRUST_MEMBER")
    private Character trustMember;
    @Size(max = 255)
    @Column(name = "UNIT_NAME")
    private String unitName;
    @Size(max = 255)
    @Column(name = "VISA_DET")
    private String visaDet;
    @Column(name = "VPF_MEMBER")
    private Character vpfMember;
    @Column(name = "WEDDING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date weddingDate;
    @Size(max = 255)
    @Column(name = "WEIGHT")
    private String weight;
    @Size(max = 255)
    @Column(name = "WORK_STATUS")
    private String workStatus;
    @Size(max = 255)
    @Column(name = "ZONES")
    private String zones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "PF_ACCOUNT")
    private String pfAccount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "BASE_BRANCH")
    private String baseBranch;
     @NotNull
    @Size(max = 12)
    @Column(name = "UAN_NUMBER")
    private String uanNumber;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXN_ID")
    private Long txnId;

    public HrPersonnelDetailsHis() {
    }

    public HrPersonnelDetailsHis(Long txnId) {
        this.txnId = txnId;
    }

    public HrPersonnelDetailsHis(Long txnId, int compCode, long empCode, String customerId, String pfAccount, String baseBranch) {
        this.txnId = txnId;
        this.compCode = compCode;
        this.empCode = empCode;
        this.customerId = customerId;
        this.pfAccount = pfAccount;
        this.baseBranch = baseBranch;
    }

    public String getAccomdType() {
        return accomdType;
    }

    public void setAccomdType(String accomdType) {
        this.accomdType = accomdType;
    }

    public String getUanNumber() {
        return uanNumber;
    }

    public void setUanNumber(String uanNumber) {
        this.uanNumber = uanNumber;
    }

    
    
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Float getAutoExpr() {
        return autoExpr;
    }

    public void setAutoExpr(Float autoExpr) {
        this.autoExpr = autoExpr;
    }

    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Float getBasicSal() {
        return basicSal;
    }

    public void setBasicSal(Float basicSal) {
        this.basicSal = basicSal;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthState() {
        return birthState;
    }

    public void setBirthState(String birthState) {
        this.birthState = birthState;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public Date getCertAdosDate() {
        return certAdosDate;
    }

    public void setCertAdosDate(Date certAdosDate) {
        this.certAdosDate = certAdosDate;
    }

    public String getCertAdosNo() {
        return certAdosNo;
    }

    public void setCertAdosNo(String certAdosNo) {
        this.certAdosNo = certAdosNo;
    }

    public String getCertRef() {
        return certRef;
    }

    public void setCertRef(String certRef) {
        this.certRef = certRef;
    }

    public String getCertTokNo() {
        return certTokNo;
    }

    public void setCertTokNo(String certTokNo) {
        this.certTokNo = certTokNo;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getCompanyLease() {
        return companyLease;
    }

    public void setCompanyLease(String companyLease) {
        this.companyLease = companyLease;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmpCardNo() {
        return empCardNo;
    }

    public void setEmpCardNo(String empCardNo) {
        this.empCardNo = empCardNo;
    }

    public String getEmpContAdd() {
        return empContAdd;
    }

    public void setEmpContAdd(String empContAdd) {
        this.empContAdd = empContAdd;
    }

    public String getEmpContCity() {
        return empContCity;
    }

    public void setEmpContCity(String empContCity) {
        this.empContCity = empContCity;
    }

    public String getEmpContPin() {
        return empContPin;
    }

    public void setEmpContPin(String empContPin) {
        this.empContPin = empContPin;
    }

    public String getEmpContState() {
        return empContState;
    }

    public void setEmpContState(String empContState) {
        this.empContState = empContState;
    }

    public String getEmpContTel() {
        return empContTel;
    }

    public void setEmpContTel(String empContTel) {
        this.empContTel = empContTel;
    }

    public String getEmpFirName() {
        return empFirName;
    }

    public void setEmpFirName(String empFirName) {
        this.empFirName = empFirName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Long getEmpImage() {
        return empImage;
    }

    public void setEmpImage(Long empImage) {
        this.empImage = empImage;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public String getEmpMidName() {
        return empMidName;
    }

    public void setEmpMidName(String empMidName) {
        this.empMidName = empMidName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPermAdd() {
        return empPermAdd;
    }

    public void setEmpPermAdd(String empPermAdd) {
        this.empPermAdd = empPermAdd;
    }

    public String getEmpPermCity() {
        return empPermCity;
    }

    public void setEmpPermCity(String empPermCity) {
        this.empPermCity = empPermCity;
    }

    public String getEmpPermPin() {
        return empPermPin;
    }

    public void setEmpPermPin(String empPermPin) {
        this.empPermPin = empPermPin;
    }

    public String getEmpPermState() {
        return empPermState;
    }

    public void setEmpPermState(String empPermState) {
        this.empPermState = empPermState;
    }

    public String getEmpPermTel() {
        return empPermTel;
    }

    public void setEmpPermTel(String empPermTel) {
        this.empPermTel = empPermTel;
    }

    public Character getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(Character empStatus) {
        this.empStatus = empStatus;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Character getEsiMember() {
        return esiMember;
    }

    public void setEsiMember(Character esiMember) {
        this.esiMember = esiMember;
    }

    public String getFathHusName() {
        return fathHusName;
    }

    public void setFathHusName(String fathHusName) {
        this.fathHusName = fathHusName;
    }

    public String getFatherHusDesig() {
        return fatherHusDesig;
    }

    public void setFatherHusDesig(String fatherHusDesig) {
        this.fatherHusDesig = fatherHusDesig;
    }

    public String getFatherHusOcc() {
        return fatherHusOcc;
    }

    public void setFatherHusOcc(String fatherHusOcc) {
        this.fatherHusOcc = fatherHusOcc;
    }

    public String getFatherHusOrg() {
        return fatherHusOrg;
    }

    public void setFatherHusOrg(String fatherHusOrg) {
        this.fatherHusOrg = fatherHusOrg;
    }

    public String getFatherHusPhone() {
        return fatherHusPhone;
    }

    public void setFatherHusPhone(String fatherHusPhone) {
        this.fatherHusPhone = fatherHusPhone;
    }

    public String getFinAccountCode() {
        return finAccountCode;
    }

    public void setFinAccountCode(String finAccountCode) {
        this.finAccountCode = finAccountCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Float getGrossSal() {
        return grossSal;
    }

    public void setGrossSal(Float grossSal) {
        this.grossSal = grossSal;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Float getHouseholdAmt() {
        return householdAmt;
    }

    public void setHouseholdAmt(Float householdAmt) {
        this.householdAmt = householdAmt;
    }

    public Float getHraAmt() {
        return hraAmt;
    }

    public void setHraAmt(Float hraAmt) {
        this.hraAmt = hraAmt;
    }

    public String getIndivMember() {
        return indivMember;
    }

    public void setIndivMember(String indivMember) {
        this.indivMember = indivMember;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public Date getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(Date retirementDate) {
        this.retirementDate = retirementDate;
    }
     
    
    
    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getJobdesc() {
        return jobdesc;
    }

    public void setJobdesc(String jobdesc) {
        this.jobdesc = jobdesc;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getLocatCode() {
        return locatCode;
    }

    public void setLocatCode(String locatCode) {
        this.locatCode = locatCode;
    }

    public Character getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Character maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Character getMetro() {
        return metro;
    }

    public void setMetro(Character metro) {
        this.metro = metro;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Float getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(Float noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getOccCode() {
        return occCode;
    }

    public void setOccCode(String occCode) {
        this.occCode = occCode;
    }

    public Character getOtEligibility() {
        return otEligibility;
    }

    public void setOtEligibility(Character otEligibility) {
        this.otEligibility = otEligibility;
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(Date passportDate) {
        this.passportDate = passportDate;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public Date getPayrollDate() {
        return payrollDate;
    }

    public void setPayrollDate(Date payrollDate) {
        this.payrollDate = payrollDate;
    }

    public Character getPfMember() {
        return pfMember;
    }

    public void setPfMember(Character pfMember) {
        this.pfMember = pfMember;
    }

    public Float getProbPeriod() {
        return probPeriod;
    }

    public void setProbPeriod(Float probPeriod) {
        this.probPeriod = probPeriod;
    }

    public String getProfMember() {
        return profMember;
    }

    public void setProfMember(String profMember) {
        this.profMember = profMember;
    }

    public String getRelay() {
        return relay;
    }

    public void setRelay(String relay) {
        this.relay = relay;
    }

    public Date getRelayDate() {
        return relayDate;
    }

    public void setRelayDate(Date relayDate) {
        this.relayDate = relayDate;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRepTo() {
        return repTo;
    }

    public void setRepTo(String repTo) {
        this.repTo = repTo;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
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

    public String getSubdeptCode() {
        return subdeptCode;
    }

    public void setSubdeptCode(String subdeptCode) {
        this.subdeptCode = subdeptCode;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public Float getTotExpr() {
        return totExpr;
    }

    public void setTotExpr(Float totExpr) {
        this.totExpr = totExpr;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Character getTravOverseasStatus() {
        return travOverseasStatus;
    }

    public void setTravOverseasStatus(Character travOverseasStatus) {
        this.travOverseasStatus = travOverseasStatus;
    }

    public Character getTrustMember() {
        return trustMember;
    }

    public void setTrustMember(Character trustMember) {
        this.trustMember = trustMember;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getVisaDet() {
        return visaDet;
    }

    public void setVisaDet(String visaDet) {
        this.visaDet = visaDet;
    }

    public Character getVpfMember() {
        return vpfMember;
    }

    public void setVpfMember(Character vpfMember) {
        this.vpfMember = vpfMember;
    }

    public Date getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(Date weddingDate) {
        this.weddingDate = weddingDate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPfAccount() {
        return pfAccount;
    }

    public void setPfAccount(String pfAccount) {
        this.pfAccount = pfAccount;
    }

    public String getBaseBranch() {
        return baseBranch;
    }

    public void setBaseBranch(String baseBranch) {
        this.baseBranch = baseBranch;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnId != null ? txnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelDetailsHis)) {
            return false;
        }
        HrPersonnelDetailsHis other = (HrPersonnelDetailsHis) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrPersonnelDetailsHis[ txnId=" + txnId + " ]";
    }
}
