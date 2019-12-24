
package com.hrms.dao;

/**
 * @author Zeeshan Waris
 *
 */
public class NamedQueryConstant {

    public static final String FIND_COMPANY_MASTER_ENTITY_BY_COMPANY_CODE = "CompanyMaster.findCompanyMasterEntityByCompanyCode";
    public static final String COMPANY_FIND_ALL = "CompanyMaster.findAll";
    public static final String STRUCT_FIND_BY_STRUCT_CODE = "HrMstStruct.findByStructCodeAndCompCode";
    //   public static final String ENTITY_BY_COMPANYCODE_AND_ADVCODE = "StrMstControl.findByCompCodeAndADV";
    public static final String PAYROLL_CALANDER_FIND_BY_STATUS_FLAG = "HrPayrollCalendar.findOpenPayrollCalendar";
    //  public static final String FIND_MAX_ADVT_CODE_BY_COMPCODE = "HrAdvertHd.findByCompCodeAndAdvertCode";	
//  public static final String UPDATE_ADVERTISEMENT_CODE = "StrMstControl.updateAdvCodeByCompCode";
    public static final String FIND_ADVT_CODE_HEADER_DETAILS = "HrAdvertHd.findByCompCodeAndAdvertCode";
    public static final String FIND_ADVERT_CODE = "HrAdvertHd.findAdvtCodeLike";
    public static final String DELETE_BY_ADVTCODE_AND_COMPANY_CODE_DETAILS = "HrAdvertDt.deleteByCompCodeAndAdvertCode";
    public static final String DELETE_BY_COMPANYCODE_AND_ADVERT_CODE_HEADER = "HrAdvertHd.deleteByCompCodeAndAdvertCode";
    public static final String STRUCT_FIND_BY_CONSULTANT_NAME = "HrConsultant.findByConsultantName";
    public static final String STRUCT_FIND_BY_CONSULTANT_DETAILS = "HrConsultant.findByConsultantDetails";
    public static final String CONSULTANT_DELETE_QUERY = "select h from HrConsultant h WHERE h.hrConsultantPK.compCode=:value1 and h.hrConsultantPK.consCode=:value2";
    public static final String DATABANK_FIND_BY_COMPANY_CODE = "HrDatabank.findByCompanyCode";
    public static final String DATABANK_UPDATE = "UPDATE HrDatabank s SET s.callInt=:calint  WHERE s.hrDatabankPK.compCode=:compCode and  s.hrDatabankPK.candId = :canid";
//    public static final String PREINT_ENTITY_BY_COMPANYCODE_AND_ADVCODE = "StrMstControl.preintfindByCompCodeAndADV";
//    public static final String PREINT_FIND_MAX_ENDNUMBER_BY_COMPCODE__AND_ADVCODE = "StrMstControl.preintfindEndNumberByCompCodeAndADV";
//    public static final String PREINT_UPDATE_ADVERTISEMENT_CODE = "StrMstControl.preintupdateAdvCodeByCompCode";
    public static final String PREINT_INTERVIEWSEARCH = "SELECT A.empId,A.empName ,A.desgCode,B.description "
            + "FROM  HrPersonnelDetails A ,HrMstStruct B WHERE A.hrPersonnelDetailsPK.compCode= :value1  "
            + "AND A.empName LIKE :value2 AND A.hrPersonnelDetailsPK.compCode=B.hrMstStructPK.compCode "
            + "AND A.desgCode=B.hrMstStructPK.structCode";
    public static final String PREINT_INTERVIEW_UPDATE =
            "SELECT A.hrInterviewHdPK.intCode,A.intDate,A.desgCode,A.intTime,A.timePerCand,A.timeBreak,A.fareCatg,A.intVenue,A.empCode,B.empName,"
            + "A.empCode2,C.empName,A.emp1Desg,D.description,A.emp2Desg,E.description,A.recType from HrInterviewHd A ,HrPersonnelDetails B,"
            + "HrPersonnelDetails C,HrMstStruct D,HrMstStruct E,HrInterviewDt F WHERE A.hrInterviewHdPK.compCode= :value1  "
            + "AND A.hrInterviewHdPK.intCode LIKE :value2 AND A.hrInterviewHdPK.compCode=B.hrPersonnelDetailsPK.compCode "
            + "AND A.empCode=B.empId AND A.hrInterviewHdPK.compCode=C.hrPersonnelDetailsPK.compCode "
            + "AND A.empCode2=C.empId AND A.hrInterviewHdPK.compCode=D.hrMstStructPK.compCode AND A.emp1Desg=D.hrMstStructPK.structCode "
            + "AND A.hrInterviewHdPK.compCode=E.hrMstStructPK.compCode AND A.emp2Desg=E.hrMstStructPK.structCode "
            + "AND A.hrInterviewHdPK.compCode=F.hrInterviewDtPK.compCode AND A.hrInterviewHdPK.intCode=F.hrInterviewDtPK.intCode "
            + "AND F.intResult=:value3 ";
    public static final String PREINT_INTERVIEW_CODE = "SELECT h.hrInterviewDtPK.candSrno,h.hrInterviewDtPK.advtCode,h.hrInterviewDtPK.jobCode,A.candFirName,A.candMidName,A.candLastName,A.totExpr,A.sex,A.callInt from HrInterviewDt h ,HrDatabank A "
            + "WHERE h.hrInterviewDtPK.compCode= :value1  "
            + "AND h.hrInterviewDtPK.intCode= :value2 "
            + "AND h.hrInterviewDtPK.compCode=A.hrDatabankPK.compCode "
            + "AND h.hrInterviewDtPK.candSrno=A.hrDatabankPK.candId "
            + "AND h.intResult=:value3 ";
    public static final String POSTINTERVIEW_INTERVIEWER_DETAIL = "SELECT A.hrInterviewDtPK.advtCode,A.hrInterviewDtPK.jobCode,A.hrInterviewDtPK.intCode,A.hrInterviewDtPK.candSrno,A.timeIn,B.candFirName,B.candMidName,B.candLastName,C.empCode,C.empCode2,C.intDate,C.intVenue,C.desgCode from HrInterviewDt A ,HrDatabank B,HrInterviewHd C "
            + "WHERE A.hrInterviewDtPK.compCode= :value1  "
            + "AND A.hrInterviewDtPK.advtCode LIKE :value2 "
            + "AND A.hrInterviewDtPK.compCode=B.hrDatabankPK.compCode "
            + "AND A.hrInterviewDtPK.candSrno=B.hrDatabankPK.candId "
            + "AND A.hrInterviewDtPK.compCode=B.hrDatabankPK.compCode "
            + "AND A.hrInterviewDtPK.intCode=C.hrInterviewHdPK.intCode "
            + "AND A.intResult=:value3 "
            + "AND A.adviceCancel=:value4";
    public static final String POSTINTERVIEW_INTERVIEWER_VIEW_DETAIL = "SELECT h.hrInterviewDtPK.intCode,h.hrInterviewDtPK.advtCode,h.hrInterviewDtPK.jobCode,h.hrInterviewDtPK.candSrno,A.candFirName,A.candMidName,A.candLastName from HrInterviewDt h ,HrDatabank A "
            + "WHERE h.hrInterviewDtPK.compCode= :value1  "
            + "AND h.hrInterviewDtPK.intCode LIKE :value2 "
            + "AND h.hrInterviewDtPK.compCode=A.hrDatabankPK.compCode "
            + "AND h.hrInterviewDtPK.candSrno=A.hrDatabankPK.candId "
            + "AND (h.intResult=:value3 OR h.intResult=:value4 OR h.intResult=:value5) "
            + "AND h.adviceCancel=:value6 ";
    public static final String POSTINTERVIEW_INTERVIEWER_EDIT_DETAIL = "SELECT h.intResult,h.timeIn,h.expectJoin,h.extension,h.intRemarks,A.desgCode,A.empCode,A.empCode2,A.intVenue,A.intDate from HrInterviewDt h ,HrInterviewHd A "
            + "WHERE h.hrInterviewDtPK.compCode= :value1  "
            + "AND h.hrInterviewDtPK.intCode=:value2 "
            + "AND h.hrInterviewDtPK.advtCode=:value3 "
            + "AND h.hrInterviewDtPK.jobCode=:value4  "
            + "AND h.hrInterviewDtPK.candSrno=:value5 "
            + "AND h.hrInterviewDtPK.compCode=A.hrInterviewHdPK.compCode "
            + "AND h.hrInterviewDtPK.intCode=A.hrInterviewHdPK.intCode ";
    public static final String POSTINTERVIEW_UPDATE_ACTION = "UPDATE HrInterviewDt s SET s.intResult=:result,s.expectJoin=:exptJoin,s.intRemarks=:remarks  WHERE s.hrInterviewDtPK.compCode=:compCode and  s.hrInterviewDtPK.intCode = :intcode and  s.hrInterviewDtPK.advtCode = :advtcode and  s.hrInterviewDtPK.jobCode = :jobcode and  s.hrInterviewDtPK.candSrno = :candsrno";
    public static final String EXTENSIN_OF_APPOINTMENT_SEARCH = "SELECT A.hrInterviewDtPK.intCode,A.hrInterviewDtPK.candSrno,"
            + "A.expectJoin,A.extension,B.candFirName,B.candMidName,B.candLastName,C.intDate,"
            + "C.intVenue,A.timeIn,C.desgCode"
            + " from HrInterviewDt A ,HrDatabank B,HrInterviewHd C "
            + " WHERE A.hrInterviewDtPK.compCode=:value1"
            + " AND A.hrInterviewDtPK.candSrno LIKE :value2"
            + " AND A.hrInterviewDtPK.compCode=B.hrDatabankPK.compCode"
            + " AND A.hrInterviewDtPK.candSrno=B.hrDatabankPK.candId"
            + " AND A.hrInterviewDtPK.compCode=C.hrInterviewHdPK.compCode"
            + " AND A.hrInterviewDtPK.intCode=C.hrInterviewHdPK.intCode"
            + " AND A.intResult=:value3 "
            + " AND A.adviceCancel=:value4";
    public static final String EXTENSIN_OF_APPOINTMENT_EDIT_DETAIL = "SELECT A.hrInterviewDtPK.intCode,A.hrInterviewDtPK.candSrno,"
            + "B.candFirName,B.candMidName,B.candLastName,C.intDate,"
            + " C.intTime,C.intVenue,C.desgCode,A.expectJoin,A.extension"
            + " from HrInterviewDt A ,HrDatabank B,HrInterviewHd C "
            + " WHERE A.hrInterviewDtPK.compCode=:value1"
            + " AND A.hrInterviewDtPK.intCode LIKE :value2"
            + " AND A.hrInterviewDtPK.compCode=B.hrDatabankPK.compCode"
            + " AND A.hrInterviewDtPK.candSrno=B.hrDatabankPK.candId"
            + " AND A.hrInterviewDtPK.compCode=C.hrInterviewHdPK.compCode"
            + " AND A.hrInterviewDtPK.intCode=C.hrInterviewHdPK.intCode"
            + " AND A.intResult=:value3 "
            + " AND A.adviceCancel=:value4";
    public static final String EXTENSIN_OF_APPOINTMENT_SAVE = "HrInterviewDt.extensionSave";
    public static final String EXTENSIN_OF_APPOINTMENT_UPDATE = "HrInterviewDt.extensionUpdate";
    public static final String DIRECT_RECRUITMENT_ZONE_LIST = "SELECT A.hrMstZoneLocationPK.locationCode,B.description "
            + "FROM  HrMstZoneLocation A ,HrMstStruct B WHERE A.hrMstZoneLocationPK.compCode= :value1  "
            + "AND A.hrMstZoneLocationPK.zoneCode= :value2 "
            + "AND A.hrMstZoneLocationPK.compCode=B.hrMstStructPK.compCode "
            + "AND A.hrMstZoneLocationPK.locationCode=B.hrMstStructPK.structCode";
    public static final String DIRECT_RECRUITMENT_INTERVIEW_SEARCH = "SELECT A.empId,A.empName,A.empContAdd,A.empContTel "
            + "FROM  HrPersonnelDetails A WHERE A.empId LIKE :value1  "
            + "AND A.hrPersonnelDetailsPK.compCode= :value2 "
            + "AND A.empStatus= :value3 ORDER BY A.empName ";
    public static final String DIRECT_RECRUITMENT_INTERVIEW_SEARCH_BY_NAME = "SELECT A.empId,A.empName,A.empContAdd,A.empContTel "
            + "FROM  HrPersonnelDetails A WHERE A.empName LIKE :value1  "
            + "AND A.hrPersonnelDetailsPK.compCode= :value2 "
            + "AND A.empStatus= :value3 ORDER BY A.empName";
    public static final String DIRECT_RECRUITMENT_INTERVIEW_VIEW_DETAIL = "SELECT A.hrDirectRecPK.compCode,A.hrDirectRecPK.arno,A.candName,A.fatherName,A.ardate "
            + "FROM  HrDirectRec A WHERE A.candName LIKE :value1  "
            + "AND A.hrDirectRecPK.compCode= :value2 ";
    public static final String DIRECT_RECRUITMENT_UPDATE_DETAIL = "SELECT A.hrDirectRecPK.compCode,A.hrDirectRecPK.arno,A.ardate,A.candName,A.fatherName,A.contactNo,A.qualCode,A.jobStatus,A.zoneCode,A.desigCode,A.locationCode,A.effectiveDate,A.appointmentDate,A.remarks,A.hrdApproval,A.mdApproval,A.basicSalary,A.hra,A.ta,A.medicalAllw,A.total,A.address,A.city,A.state,A.pin,A.emailId,B.empId,B.empName,C.empId,C.empName,D.empId,D.empName,E.description "
            + "FROM  HrDirectRec A,HrPersonnelDetails B,HrPersonnelDetails C,HrPersonnelDetails D,HrMstStruct E WHERE A.hrDirectRecPK.compCode= :value1 "
            + "AND A.hrDirectRecPK.arno= :value2  "
            + "AND A.hrDirectRecPK.compCode=B.hrPersonnelDetailsPK.compCode "
            + "AND A.superId=B.hrPersonnelDetailsPK.empCode "
            + "AND A.hrDirectRecPK.compCode=C.hrPersonnelDetailsPK.compCode "
            + "AND A.initiatorId=C.hrPersonnelDetailsPK.empCode "
            + "AND A.hrDirectRecPK.compCode=D.hrPersonnelDetailsPK.compCode "
            + "AND A.deptHeadId=D.hrPersonnelDetailsPK.empCode "
            + "AND A.hrDirectRecPK.compCode=E.hrMstStructPK.compCode "
            + "AND A.locationCode=E.hrMstStructPK.structCode";
    public static final String DIRECT_RECRUITMENT_SAVE_CHECK = "SELECT max(A.hrDirectRecPK.arno) "
            + "FROM  HrDirectRec A WHERE A.hrDirectRecPK.compCode= :value2 ";
    public static final String DIRECT_RECRUITMENT_SAVE_VALID = "SELECT A FROM  HrDirectRec A ";
    public static final String DIRECT_RECRUITMENT_SAVE_SUPERCODE = "SELECT A.hrPersonnelDetailsPK.empCode "
            + "FROM  HrPersonnelDetails A WHERE A.hrPersonnelDetailsPK.compCode= :value1 "
            + "AND A.empId=:value2 ";
    public static final String DIRECT_RECRUITMENT_UPDATE_CHECK = "select A from HrDirectRec A where A.hrDirectRecPK.compCode = :value1 "
            + "and A.hrDirectRecPK.arno = :value2";
    public static final String MANPOWER_DETAIL = "SELECT A.hrManpowerPK.year,A.hrManpowerPK.month,C.description,A.hrManpowerPK.zone,B.description,A.hrManpowerPK.deptCode,D.description,A.qualcode1,A.qualCode2,A.qualCode3,A.specialCode,A.desgCode,E.description,A.minmExp,A.prefExp,A.requExp,A.autoExp,A.posFill,A.posRequ,A.posSanc,A.skillCode from HrManpower A ,HrMstStruct B,HrMstStruct C,HrMstStruct D,HrMstStruct E "
            + "WHERE A.hrManpowerPK.compCode= :value1  "
            + "AND A.hrManpowerPK.zone=B.hrMstStructPK.structCode "
            + "AND A.hrManpowerPK.compCode=B.hrMstStructPK.compCode "
            + "AND A.hrManpowerPK.month=C.hrMstStructPK.structCode "
            + "AND A.hrManpowerPK.compCode=C.hrMstStructPK.compCode "
            + "AND A.hrManpowerPK.deptCode=D.hrMstStructPK.structCode "
            + "AND A.hrManpowerPK.compCode=D.hrMstStructPK.compCode "
            + "AND A.desgCode=E.hrMstStructPK.structCode "
            + "AND A.hrManpowerPK.compCode=E.hrMstStructPK.compCode ";
    public static final String MANPOWER_DELETE_QUERY = "select h from HrManpower h WHERE h.hrManpowerPK.compCode=:value1 and h.hrManpowerPK.year=:value2 and h.hrManpowerPK.month=:value3 and h.hrManpowerPK.zone=:value4 and h.hrManpowerPK.deptCode=:value5";
    public static final String MANPOWER_STRUCT_CODE = "select A.gradeCode,B.description,C.jobSpecification FROM HrMstDesg A,HrMstStruct B,HrOrgChart C "
            + "WHERE A.hrMstDesgPK.compCode=:value1 "
            + "AND A.hrMstDesgPK.desgCode=:value2 "
            + "AND A.hrMstDesgPK.compCode=B.hrMstStructPK.compCode "
            + "AND A.gradeCode=B.hrMstStructPK.structCode AND A.hrMstDesgPK.compCode=C.hrOrgChartPK.compCode AND A.hrMstDesgPK.desgCode=C.hrOrgChartPK.desgCode";
    public static final String MANPOWER_EMPLOYEE_NO = "SELECT A FROM  HrPersonnelDetails A  WHERE A.hrPersonnelDetailsPK.empCode=:value1 and A.zones=:value2 and A.deptCode=:value3 and A.desgCode=:value4 and A.gradeCode=:value5";
    public static final String MANPOWER_UPDATE_ACTION = "UPDATE HrManpower s SET s.qualcode1=:qualVal1,s.qualCode2=:qualVal2,s.qualCode3=:qualVal3,s.specialCode=:specialCodeVal,s.desgCode=:desgcodeVal,s.minmExp=:minExpVal,s.prefExp=:prefExpVal,s.requExp=:reqExpVal,s.autoExp=:autoExpVal,s.posFill=:posfillVal,s.posRequ=:posReqVal,s.posSanc=:posSancVal,s.skillCode=:skillCodeVal,s.statFlag=:statFlagVal,s.statUpFlag=:statUpflagVal,s.modDate=:modDtVal,s.authBy=:authbyVal,s.enteredBy=:enterbyVal  WHERE s.hrManpowerPK.compCode=:compCodeVal and  s.hrManpowerPK.month= :monthVal and  s.hrManpowerPK.year = :yearVal and s.hrManpowerPK.zone=:zoneVal and s.hrManpowerPK.deptCode=:deptcodeVal";
    public static final String CREATION_OF_DATABANK_SEARCH = "SELECT h.hrAdvertHdPK.advtCode,h.hrAdvertHdPK.jobCode FROM HrAdvertHd h WHERE h.hrAdvertHdPK.compCode=:value1 and h.hrAdvertHdPK.advtCode like :value2 ";
    public static final String CREATION_OF_DATABANK_VIEW = "HrDatabank.findByCandidateId";
    public static final String CREATION_OF_DATABANK_CONSULTANT_LIST = "select h.hrConsultantPK.consCode,h.consFirName from HrConsultant h where h.hrConsultantPK.compCode=:value1 order by h.consFirName";
    public static final String CREATION_OF_DATABANK_REFERANCE_DETAILS = "select h.hrDataReferencePK.referName,h.referAdd,h.referPin,h.referState,h.referCity,h.referPhone,h.referOcc from HrDataReference h where h.hrDataReferencePK.compCode=:value1 and h.hrDataReferencePK.candSrno=:value2 and h.hrDataReferencePK.advtCode=:value3 and h.hrDataReferencePK.jobCode=:value4";
    public static final String CREATION_OF_DATABANK_QUALIFICATION = "select h.hrDataQualPK.qualCode,f.description,h.instName,h.year,h.subName,h.special,j.description,h.perMarks,h.div from HrDataQual h,HrMstStruct f,HrMstStruct j where h.hrDataQualPK.compCode=:value1 and h.hrDataQualPK.candSrno=:value2 and h.hrDataQualPK.advtCode=:value3 and h.hrDataQualPK.jobCode=:value4 and h.hrDataQualPK.compCode=f.hrMstStructPK.compCode and h.hrDataQualPK.qualCode=f.hrMstStructPK.structCode and h.hrDataQualPK.compCode=j.hrMstStructPK.compCode and h.special=j.hrMstStructPK.structCode";
    public static final String CREATION_OF_DATABANK_PREVIOUS_EMPLOYEMENT = "select A.hrDataPrevCompPK.compName,A.annualTurn,A.leaveDate,A.compAdd,A.durFrom,A.durTo,A.desgJoin,B.description,A.desgLeave,C.description,A.totEmp,A.empUnder,A.salJoin,A.salLeave,A.reason from HrDataPrevComp A,HrMstStruct B,HrMstStruct C where A.hrDataPrevCompPK.compCode=:value1 and A.hrDataPrevCompPK.candSrno=:value2 and A.hrDataPrevCompPK.advtCode=:value3 and A.hrDataPrevCompPK.jobCode=:value4 and A.hrDataPrevCompPK.compCode=B.hrMstStructPK.compCode and A.desgJoin=B.hrMstStructPK.structCode and A.hrDataPrevCompPK.compCode=C.hrMstStructPK.compCode and A.desgLeave=C.hrMstStructPK.structCode";
    public static final String CREATI0N_OF_DATABANK_SAVE_VALID = "SELECT A FROM  HrDatabank A ";
    public static final String CREATI0N_OF_DATABANK_DATA_QUAL_DEL = "select h from HrDataQual h WHERE h.hrDataQualPK.compCode=:value1 and "
            + "h.hrDataQualPK.advtCode=:value2 and h.hrDataQualPK.jobCode=:value3 and h.hrDataQualPK.candSrno=:value4 and h.hrDataQualPK.qualCode=:value5";
    public static final String CREATI0N_OF_DATABANK_DATA_EXP_DEL = "select h from HrDataPrevComp h WHERE h.hrDataPrevCompPK.compCode=:value1 "
            + "and h.hrDataPrevCompPK.advtCode=:value2 and h.hrDataPrevCompPK.jobCode=:value3 and h.hrDataPrevCompPK.candSrno=:value4 and "
            + "h.hrDataPrevCompPK.compName=:value5";
    public static final String CREATI0N_OF_DATABANK_DATA_REF_DEL = "select h from HrDataReference h WHERE h.hrDataReferencePK.compCode=:value1 "
            + "and h.hrDataReferencePK.advtCode=:value2 and h.hrDataReferencePK.jobCode=:value3 and h.hrDataReferencePK.candSrno=:value4 and "
            + "h.hrDataReferencePK.referName=:value5";
    public static final String PARAMETER_DELETE_QUERY = "select h from HrMstStruct h WHERE h.hrMstStructPK.compCode=:value1 and "
            + "h.hrMstStructPK.structCode=:value2";
    public static final String TRANSFER_EMP_DETAIL =
            "SELECT A.hrPersonnelDetailsPK.empCode,A.empId,A.empName,A.block,B.description,A.unitName,"
            + "C.description,A.deptCode,D.description,A.desgCode,E.description,A.gradeCode,F.description,A.zones,G.description,A.locatCode,H.description,"
            + "A.noticePeriod,A.joinDate,A.totExpr,A.repTo "
            + "FROM HrPersonnelDetails A,HrMstStruct B,HrMstStruct C,HrMstStruct D,HrMstStruct E,HrMstStruct F,HrMstStruct H,HrMstStruct G "
            + "WHERE A.empId=:value1 and A.hrPersonnelDetailsPK.compCode= :value2 "
            + "AND A.block=B.hrMstStructPK.structCode "
            + "AND A.hrPersonnelDetailsPK.compCode=B.hrMstStructPK.compCode "
            + "AND A.hrPersonnelDetailsPK.compCode=C.hrMstStructPK.compCode "
            + "AND A.unitName=C.hrMstStructPK.structCode "
            + "AND A.deptCode=D.hrMstStructPK.structCode "
            + "AND A.hrPersonnelDetailsPK.compCode=D.hrMstStructPK.compCode  "
            + "AND A.hrPersonnelDetailsPK.compCode=E.hrMstStructPK.compCode  "
            + "AND A.desgCode=E.hrMstStructPK.structCode "
            + "AND A.hrPersonnelDetailsPK.compCode=F.hrMstStructPK.compCode "
            + "AND A.gradeCode=F.hrMstStructPK.structCode "
            + "AND A.hrPersonnelDetailsPK.compCode=H.hrMstStructPK.compCode "
            + "AND A.locatCode=H.hrMstStructPK.structCode "
            + "AND A.hrPersonnelDetailsPK.compCode=G.hrMstStructPK.compCode "
            + "AND A.zones=G.hrMstStructPK.structCode ";
    public static final String TRANSFER_GET_ARNO =
            "SELECT h.hrTransferDetailsPK.compCode,h.hrTransferDetailsPK.arNo,h.ardate,h.effective FROM HrTransferDetails h where "
            + "h.hrTransferDetailsPK.compCode=:value1 and h.hrTransferDetailsPK.arNo like :value2";
    public static final String TRANSFER_EDIT_DETAIL = "SELECT A.empId,A.empName,A.hrPersonnelDetailsPK.empCode,B.presentDesig,B.proposedDesig,B.zoneFrom,"
            + "B.zoneTo,B.blockFrom,B.blockTo,B.deptFrom,B.deptTo,B.locationFrom,B.locationTo,B.effective,B.transfer,B.pf,B.esi,B.salProcess,"
            + "B.reason,B.deput FROM HrPersonnelDetails A, HrTransferDetails B where B.hrTransferDetailsPK.compCode=:value1 and B.hrTransferDetailsPK.arNo=:value2 and A.hrPersonnelDetailsPK.compCode=B.hrTransferDetailsPK.compCode and A.hrPersonnelDetailsPK.empCode=B.empCode";
    public static final String TRANSFER_DELETE_QUERY = "select h from HrTransferDetails h WHERE h.hrTransferDetailsPK.compCode=:value1 and h.hrTransferDetailsPK.arNo=:value2";
    public static final String TEMPORARY_STAFF_CONTRACTOR_DETAILS = "select h.hrContractorDetailsPK.contCode,h.contFirName from HrContractorDetails h WHERE h.hrContractorDetailsPK.compCode=:value1";
    public static final String TEMPORARY_STAFF_EDIT_DETAIL = "HrTempStaff.findByCompCode";
    public static final String TEMPORARY_STAFF_DELETE_QUERY = "select h from HrTempStaff h WHERE h.hrTempStaffPK.compCode=:value1 and h.hrTempStaffPK.arNo=:value2";
//    public static final String CONTRACTOR_ENTITY_BY_COMPANYCODE_AND_ADVCODE = "StrMstControl.contractorfindByCompCodeAndADV";
//    public static final String CONTRACTOR_FIND_MAX_ENDNUMBER_BY_COMPCODE__AND_ADVCODE = "StrMstControl.contractorfindEndNumberByCompCodeAndADV";
//    public static final String CONTRACTOR_UPDATE_ADVERTISEMENT_CODE = "StrMstControl.contractorupdateAdvCodeByCompCode";
    public static final String CONTRACTOR_EDIT_DETAILS = "HrContractorDetails.findByCompCode";
    public static final String CONTRACTOR_DETAILS_DELETE_QUERY = "select h from HrContractorDetails h WHERE h.hrContractorDetailsPK.compCode=:value1 and h.hrContractorDetailsPK.contCode=:value2";
    public static final String REPORTING_STRUCTURE_EDIT_DETAIL = "SELECT A.hrOrgChartPK.desgCode,B.description,A.desgCodeRp,C.description,A.posts,A.jobSpecification,A.description,A.flag1,B.hrMstStructPK.structCode from HrOrgChart A ,HrMstStruct B,HrMstStruct C "
            + "WHERE A.hrOrgChartPK.compCode= :value1  "
            + "AND A.hrOrgChartPK.compCode=B.hrMstStructPK.compCode "
            + "AND A.hrOrgChartPK.compCode=C.hrMstStructPK.compCode "
            + "AND A.hrOrgChartPK.desgCode=B.hrMstStructPK.structCode "
            + "AND A.desgCodeRp=C.hrMstStructPK.structCode ";
    public static final String REPORTING_DETAILS_DELETE_QUERY = "select h from HrOrgChart h WHERE h.hrOrgChartPK.compCode=:value1 and h.hrOrgChartPK.desgCode=:value2";
    public static final String VIEW_ORG_DETAIL = "SELECT A.empName,A.desgCode,B.description from HrPersonnelDetails A ,HrMstStruct B "
            + "WHERE A.hrPersonnelDetailsPK.compCode= :value1 AND A.deptCode= :value2  "
            + "AND A.gradeCode= :value3 "
            + "AND A.hrPersonnelDetailsPK.compCode=B.hrMstStructPK.compCode "
            + "AND A.desgCode=B.hrMstStructPK.structCode ";
    public static final String PREPARE_ORG_VIEW_DETAIL = "SELECT A.hrMstStructPK.structCode,A.description from HrMstStruct A "
            + "WHERE A.hrMstStructPK.structCode like :value2 AND A.hrMstStructPK.compCode= :value1  "
            + "AND A.hrMstStructPK.structCode NOT IN (SELECT B.hrMstDesgPK.desgCode FROM HrMstDesg B WHERE B.hrMstDesgPK.compCode=:value1) order by A.description";
    public static final String PREPARE_ORGN_EDIT_DETAIL = "SELECT distinct A.gradeCode,B.description from HrMstDesg A,HrMstStruct B "
            + "WHERE A.hrMstDesgPK.compCode=:value1 AND A.hrMstDesgPK.compCode=B.hrMstStructPK.compCode AND A.gradeCode=B.hrMstStructPK.structCode  order by B.description";
    public static final String PREPARE_ORGN_DELETE_QUERY = "delete from HrMstDesg h WHERE h.hrMstDesgPK.compCode=:value1 and h.gradeCode=:value2";
    public static final String PREPARE_ORGN_UPDATE_DESGCODE = "SELECT A.hrMstDesgPK.desgCode,B.description,A.gradeCode,C.description from HrMstDesg A ,HrMstStruct B,HrMstStruct C "
            + "WHERE A.hrMstDesgPK.compCode= :value1 AND A.gradeCode LIKE :value2  "
            + "AND A.hrMstDesgPK.compCode=B.hrMstStructPK.compCode "
            + "AND A.hrMstDesgPK.desgCode=B.hrMstStructPK.structCode "
            + "AND A.hrMstDesgPK.compCode=C.hrMstStructPK.compCode "
            + "AND A.gradeCode=C.hrMstStructPK.structCode";
    //////////////Navneet
//    public static final String FIND_HR_PERSONNEL_DETAILS_ENTITY_BY_COMPCODE_AND_EMPCODE_OR_EMPID = "HrPersonnelDetails.findByCompCodeEmpCodeEmpId";
    //public static final String FIND_HR_PERSONNEL_DETAILS_ENTITY_BY_COMPCODE_AND_EMPCODE_OR_EMPID = QueryConstant.FIND_BY_COMP_CODE_EMP_CODE_EMP_ID;
    public static final String FIND_HR_PERSONNEL_DETAILS_ENTITY_BY_COMPCODE_AND_EMPCODE_OR_EMPID = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND (h.hrPersonnelDetailsPK.empCode = :empCode OR h.empId = :empId)";
    public static final String FIND_HR_EMP_ADVANCE_HD_BY_COMPCODE_EMP_ADVNO = "HrEmpAdvanceHd.findHrEmpAdvanceHdByCompCodeEmpAdvNo";
    public static final String FIND_HR_EMP_LOAN_HD_BY_COMPCODE_EMPLOANNO = "HrEmpLoanHd.findHrEmpLoanHdByCompCodeEmpLoanNo";
    public static final String FIND_HR_LEAVE_MASTER_ENTITY_BY_COMPCODE = "HrLeaveMaster.findByCompCode";
    public static final String FIND_HR_LEAVE_MASTER_ENTITY_BY_DATE_FROM_AND_DATE_TO = "HrLeaveMaster.findByDateFromAndDateTo";
//    public static final String FIND_BY_COMPCODE_AND_LIKE_EMP_ID = "HrPersonnelDetails.findByCompCodeAndLikeEmpId";
//    public static final String FIND_BY_COMPCODE_AND_LIKE_EMP_ID = QueryConstant.FIND_BY_COMP_CODE_AND_LIKE_EMP_ID;
    public static final String FIND_BY_COMPCODE_AND_LIKE_EMP_ID = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode=:compCode AND h.empId like:empId order by h.empId";
//    public static final String FIND_BY_COMPCODE_AND_LIKE_EMP_NAME = "HrPersonnelDetails.findByCompCodeAndLikeEmpName";
    //public static final String FIND_BY_COMPCODE_AND_LIKE_EMP_NAME = QueryConstant.FIND_BY_COMP_CODE_AND_LIKE_EMP_NAME;
    public static final String FIND_BY_COMPCODE_AND_LIKE_EMP_NAME = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empName like :empName order by h.empName";
    public static final String FIND_TOTAL_LEAVE_DAYS = "HrLeaveRegister.findTotalLeaveDays";
    public static final String FIND_LEAVE_REGISTER_ENTITY_BY_COMPCODE = "select max(cast(substring(leav_reg_code,4) as unsigned)) from hr_leave_register where comp_code = compCode";
    public static final String FIND_BY_COMPCODE_AND_EMPCODE = "HrLeaveRegister.findByCompCodeAndEmpCode";
    public static final String FIND_NUM_OF_LEAVE_DAYS_BY_LEAVE_CODE = "HrLeaveMaster.findNumOfLeaveDaysByLeaveCode";
    public static final String FIND_MST_SHIFT_ENTITY_BY_COMPCODE = "HrMstShift.findMstShiftEntityByCompCode";
//    public static final String FIND_CATEGORIZATION_BASED_EMPLOYEES = "HrPersonnelDetails.findCategorizationBasedEmployees";
    //public static final String FIND_CATEGORIZATION_BASED_EMPLOYEES = QueryConstant.FIND_CATEGORIZATION_BASED_EMPLOYEES;
    public static final String FIND_CATEGORIZATION_BASED_EMPLOYEES = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empStatus = 'Y' AND (h.deptCode= :type OR h.gradeCode= :type OR h.unitName= :type OR h.block =:type OR h.desgCode= :type OR h.catgCode= :type OR h.zones= :type OR h.locatCode= :type OR h.empType= :type)";
    public static final String FIND_HrMstCompLoan_BY_COMPCODE = "HrMstCompLoan.findHrMstCompLoanByCompCode";
    public static final String FIND_HrMstDeptSubdept_BY_COMPCODE = "HrMstDeptSubdept.findByCompCode";
    public static final String FIND_HrMstDeptSubdept_BY_COMPCODE_DEPTCODE_SUBDEPTCODE = "HrMstDeptSubdept.findByCompCodeDeptCodeSubDeptCode";
    public static final String FIND_HrMstDeptSubdept_BY_COMPCODE_DEPTCODE = "HrMstDeptSubdept.findByCompCodeDeptCode";
//    public static final String FIND_EMP_BY_COMPCODE_STATUS_Y = "HrPersonnelDetails.findEmpByCompCodeWithStatusY";
//public static final String FIND_EMP_BY_COMPCODE_STATUS_Y = QueryConstant.FIND_EMP_BY_COMP_CODE_WITH_STATUS_Y;

    
    
    
    public static final String FIND_EMP_BY_COMPCODE_STATUS_Y = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode and h.empStatus = :empStatus";
   
    
    
    
    public static final String FIND_HR_MST_BUS_BY_COMPCODE = "HrMstBus.findHrMstBusByCompCode";
    public static final String FIND_HR_MST_ROUTE_BY_COMPCODE = "HrMstRoute.findHrMstRouteByCompCode";
    ///////////End
    public static final String ADD_LIST_EVALUATION_OF_DATABANK = "SELECT h.hrDatabankPK.candId,h.hrDatabankPK.advtCode,"
            + "h.hrDatabankPK.jobCode,h.candFirName,h.candMidName,h.candLastName,h.totExpr,h.specialCode1,B.description,"
            + "h.currSal,h.expSal from HrDatabank h,HrMstStruct B where h.hrDatabankPK.compCode=:value1 and h.orgType=:value2"
            + " and h.postApplied1=:value3 and h.hrDatabankPK.compCode=B.hrMstStructPK.compCode and h.specialCode1=B.hrMstStructPK.structCode and h.evalFlag=:value4";
    public static final String EDIT_EVALUATION_OF_DATABANK = "select A.orgType,B.description,A.postApplied1,C.description"
            + " from HrDatabank A,HrMstStruct B,HrMstStruct C  where A.hrDatabankPK.compCode=:value1 and A.orgType like :value2"
            + " and A.hrDatabankPK.compCode=B.hrMstStructPK.compCode and A.specialCode1=B.hrMstStructPK.structCode "
            + "and A.hrDatabankPK.compCode= C.hrMstStructPK.compCode  and A.postApplied1=C.hrMstStructPK.structCode ";
    public static final String SAVE_EVALUATION_OF_DATABANK = "UPDATE HrDatabank h SET  h.evalFlag = :evalFlag "
            + "WHERE h.hrDatabankPK.compCode = :compCode and  h.hrDatabankPK.candId = :candId "
            + " and h.hrDatabankPK.advtCode = :advtCode and h.hrDatabankPK.jobCode = :jobCode "
            + "and h.orgType = :orgType and h.postApplied1 = :postApplied1";
    public static final String UPDATE_EVALUATION_OF_DATABANK =
            "UPDATE HrDatabank h SET  h.evalFlag = :evalFlag "
            + "WHERE h.hrDatabankPK.compCode = :compCode and h.orgType = :orgType and h.postApplied1 = :postApplied1";
    public static final String SEARCH_LIST_APPOINTMENT_LETTER =
            "SELECT A.hrInterviewDtPK.advtCode,A.hrInterviewDtPK.jobCode,A.hrInterviewDtPK.intCode,"
            + "A.hrInterviewDtPK.candSrno,B.candFirName,B.candMidName,B.candLastName,B.postApplied1,"
            + "B.candContAdd,B.candContCity,B.candContState,B.candContPin,B.expSal"
            + " from HrInterviewDt A ,HrDatabank B "
            + " WHERE A.hrInterviewDtPK.compCode=:value1"
            + " AND A.hrInterviewDtPK.candSrno LIKE :value2"
            + " AND A.hrInterviewDtPK.compCode=B.hrDatabankPK.compCode"
            + " AND A.hrInterviewDtPK.candSrno=B.hrDatabankPK.candId"
            + " AND A.intResult=:value3 ";
    public static final String VIEW_EDIT_LIST_APPOINTMENT_LETTER =
            "SELECT h.hrInterviewDtSalPK.advtCode,h.hrInterviewDtSalPK.jobCode,"
            + "h.hrInterviewDtSalPK.intCode,h.hrInterviewDtSalPK.candSrno,"
            + "h.compOff,h.compNegoit,h.compExpect,B.candFirName,B.candMidName,B.candLastName,B.postApplied1,"
            + "B.candContAdd,B.candContCity,B.candContState,B.candContPin"
            + " from HrInterviewDtSal h ,HrDatabank B,HrInterviewDt C "
            + " WHERE h.hrInterviewDtSalPK.compCode=:value1"
            + " AND h.hrInterviewDtSalPK.candSrno LIKE :value2"
            + " AND h.hrInterviewDtSalPK.compCode=B.hrDatabankPK.compCode"
            + " AND h.hrInterviewDtSalPK.candSrno=B.hrDatabankPK.candId"
            + " AND h.hrInterviewDtSalPK.compCode=C.hrInterviewDtPK.compCode"
            + " AND h.hrInterviewDtSalPK.advtCode=B.hrDatabankPK.advtCode"
            + " AND h.hrInterviewDtSalPK.jobCode=C.hrInterviewDtPK.jobCode"
            + " AND h.hrInterviewDtSalPK.candSrno=C.hrInterviewDtPK.candSrno"
            + " AND C.intResult=:value3 ";
    public static final String VIEW_LIST_CANCELLATION_OF_APPOINTMENT_LETTER =
            "SELECT A.hrInterviewDtPK.intCode,A.hrInterviewDtPK.candSrno,"
            + "A.expectJoin,A.extension,B.candFirName,B.candMidName,B.candLastName,C.intDate,"
            + "C.intVenue,C.intTime,C.desgCode,A.cancel"
            + " from HrInterviewDt A ,HrDatabank B,HrInterviewHd C "
            + " WHERE A.hrInterviewDtPK.compCode=:value1"
            + " AND A.hrInterviewDtPK.intCode LIKE :value2"
            + " AND A.hrInterviewDtPK.compCode=B.hrDatabankPK.compCode"
            + " AND A.hrInterviewDtPK.candSrno=B.hrDatabankPK.candId"
            + " AND A.hrInterviewDtPK.compCode=C.hrInterviewHdPK.compCode"
            + " AND A.hrInterviewDtPK.intCode=C.hrInterviewHdPK.intCode"
            + " AND A.adviceCancel=:value3";
    public static final String SEARCH_LIST_CANCELLATION_OF_APPOINTMENT_LETTER =
            "SELECT A.hrInterviewDtPK.intCode,A.hrInterviewDtPK.candSrno,"
            + "A.expectJoin,A.extension,B.candFirName,B.candMidName,B.candLastName,C.intDate,"
            + "C.intVenue,A.timeIn,C.desgCode"
            + " from HrInterviewDt A ,HrDatabank B,HrInterviewHd C "
            + " WHERE A.hrInterviewDtPK.compCode=:value1"
            + " AND A.hrInterviewDtPK.candSrno LIKE :value2"
            + " AND A.hrInterviewDtPK.compCode=B.hrDatabankPK.compCode"
            + " AND A.hrInterviewDtPK.candSrno=B.hrDatabankPK.candId"
            + " AND A.hrInterviewDtPK.compCode=C.hrInterviewHdPK.compCode"
            + " AND A.hrInterviewDtPK.intCode=C.hrInterviewHdPK.intCode"
            + " AND A.intResult=:value3 "
            + " AND A.adviceCancel=:value4";
    public static final String UPDATE_APPOINTMENT_CENCEL =
            "UPDATE HrInterviewDt h SET h.cancel = :cancel,h.adviceCancel = :adviceCancel,"
            + "h.statFlag = :statFlag,h.statUpFlag = :statUpFlag,h.modDate = :modDate,h.authBy = :authBy,h.enteredBy = :enteredBy  "
            + "WHERE h.hrInterviewDtPK.compCode=:compCode and  h.hrInterviewDtPK.intCode =:intCode and  h.hrInterviewDtPK.candSrno = :candsrno";
    public static final String UPDATE_TABLE_FOR_SAVE_APPOINTMENT_LETTER =
            "UPDATE HrInterviewDt h SET  h.intResult = :intResult "
            + "WHERE h.hrInterviewDtPK.compCode=:compCode and  h.hrInterviewDtPK.intCode =:intCode "
            + " and h.hrInterviewDtPK.advtCode = :advtCode and h.hrInterviewDtPK.jobCode = :jobCode "
            + "and  h.hrInterviewDtPK.candSrno = :candsrno";
    public static final String UPDATE_APPOINTMENT_LETTER =
            "UPDATE HrInterviewDtSal h SET h.compOff = :compOff,h.compNegoit = :compNegoit,h.compExpect = :compExpect WHERE h.hrInterviewDtSalPK.compCode =:value1 and"
            + " h.hrInterviewDtSalPK.intCode =:value2 and  h.hrInterviewDtSalPK.advtCode =:value3 and h.hrInterviewDtSalPK.jobCode =:value4 "
            + "and h.hrInterviewDtSalPK.candSrno =:value5";
    public static final String TRNG_EXC_EDIT_DATA =
            "SELECT A.hrTrainingExecutionPK.trngexecCode,A.hrTrainingExecutionPK.empCode,"
            + "B.empName,B.empId,A.trngCode,C.description,A.progCode,D.progName,"
            + "A.actDura,A.facuName,A.trngCost,A.commence,A.endDate,A.apprDet,A.trainerAss,A.traineeAss,A.datePlanFrom,A.datePlanTo,E.trngDur,D.inextHouse,F.description from HrTrainingExecution A,HrPersonnelDetails B,HrMstStruct C,HrMstProgram D,HrTrainingPlan E,HrMstStruct F "
            + "WHERE A.hrTrainingExecutionPK.compCode=:value1 "
            + "AND A.hrTrainingExecutionPK.compCode=B.hrPersonnelDetailsPK.compCode "
            + "AND A.hrTrainingExecutionPK.empCode=B.hrPersonnelDetailsPK.empCode "
            + "AND C.hrMstStructPK.compCode=A.hrTrainingExecutionPK.compCode "
            + "AND C.hrMstStructPK.structCode=A.trngCode "
            + "AND D.hrMstProgramPK.progCode=A.progCode "
            + "AND D.hrMstProgramPK.compCode=A.hrTrainingExecutionPK.compCode "
            + "AND E.hrTrainingPlanPK.compCode=A.hrTrainingExecutionPK.compCode "
            + "AND E.hrTrainingPlanPK.empCode=A.hrTrainingExecutionPK.empCode "
            + "AND E.hrTrainingPlanPK.trngCode=A.trngCode "
            + "AND E.hrTrainingPlanPK.progCode=A.progCode "
            + "AND E.hrTrainingPlanPK.dateFrom=A.datePlanFrom "
            + "AND E.hrTrainingPlanPK.dateTo=A.datePlanTo "
            + "AND F.hrMstStructPK.compCode=B.hrPersonnelDetailsPK.compCode "
            + "AND F.hrMstStructPK.structCode=B.deptCode";
    public static final String ADV_DELETE_DT = "select h from HrAdvertDt h WHERE h.hrAdvertDtPK.compCode=:value1 and h.hrAdvertDtPK.advtCode=:value2";
    public static final String ADV_DELETE_HD = "select h from HrAdvertHd h WHERE h.hrAdvertHdPK.compCode=:value1 and h.hrAdvertHdPK.advtCode=:value2";
    public static final String FIND_COMPANY_NAME_BY_COMPCODE = "CompanyMaster.findCompanyNamebycmpnycode";
    public static final String PAYROLL_CALANDER_FIND_BY_STATUS_FLAG1 = "HrPayrollCalendar.findPayrollCalendarByStatusFlag1";
    public static final String EMPCODE_FIND_BY_COMPANY_CODE_AND_EMPID = "HrPersonnelDetails.findEmpByCompCodeLoginWithStatusY";
    public static final String FIND_PERSONALDATA_BY_EMP_ID = "select h from HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode  and h.empStatus = :empStatus and h.empId like :empId  order by h.empName";
    public static final String FIND_PERSONALDATA_BY_EMP_NAME = "select h from HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode  and h.empStatus = :empStatus and h.empName like :empName order by h.empName";
    public static final String FIND_EMPCODE_BY_EMP_ID_COMPCODE_FLAG = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode and h.empStatus = :empStatus and h.empId = :empId";
    public static final String FIND_HRLEAVEPOSTING_ALL_DATA = "HrLeavePosting.findhrLeavePostingByCheckAll";
    public static final String FIND_EMPCODE_HRPERSONNAL_CHECK_ALL = "select h from HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode and h.empStatus = :empStatus and ( h.deptCode = :value or h.gradeCode = :value or h.unitName = :value or h.block = :value or h.desgCode = :value or h.catgCode = :value or h.zones = :value or h.locatCode = :value or h.empType = :value)";
    public static final String FIND_HRHOLIDAYMASTER_COMPCODE = "HrHolidayMaster.findByCompCodeAndFrDtAndToDt";
    public static final String FIND_MAX_HOLIDAYCODE = "HrHolidayMaster.findMaxHoliDayCode";
    public static final String FIND_BY_COMPCODE_EMPCODE = "HrSeparationDetails.findBycompCodeempCode";
    public static final String FIND_BY_COMPCOD_EMPID = "SELECT h.hrPersonnelDetailsPK.empCode FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode and h.empId = :empId";
    public static final String FIND_BY_PRIMARY_KEY = "HrApprisalDetails.findByCompCodeEmpCodeAppdt";
    public static final String FIND_BY_PRIMARY_KEY_HR_PROMOTION = "HrPromotionDetails.findAllPrmiarykey";
    public static final String FIND_BY_PRIMARY_KEY_CLEARSHLIPHD = "ClearSlipHd.findbyPrimarykey";
    public static final String FIND_BY_PRIMARY_KEY_CLEARSLIPDET = "ClearSlipDet.findByAllPrimarykey";
    public static final String FIND_BY_EMP_NAME_FOR_EXIT_INTERVIEW = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode  and h.empStatus = :empStatus and h.hrPersonnelDetailsPK.empCode = :empCode order by h.empName";
    public static final String UPDATE_HR_SEPRATION_STAT_FLAG = "HrSeparationDetails.updateflag";
    public static final String UPDATE_HR_PERSONNEL_EMP_STATUS = "HrPersonnelDetails.finadbyupdateempsatus";
    public static final String DATE_IN_FIND_BY_EMP_CODE =
            "select h.timeIn from HrAttendanceMaintenance h where h.hrAttendanceMaintenancePK.compCode = :compCode and h.hrAttendanceMaintenancePK.empCode = :empCode and h.hrAttendanceMaintenancePK.attenDate  "
            + " between (select max(a.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance a where a.hrAttendanceMaintenancePK.compCode = :compCode and a.hrAttendanceMaintenancePK.empCode = :empCode) and CURRENT_TIMESTAMP and h.hrAttendanceMaintenancePK.attenDate in (select max(b.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance b where b.hrAttendanceMaintenancePK.compCode = :compCode and b.hrAttendanceMaintenancePK.empCode = :empCode and b.hrAttendanceMaintenancePK.attenDate between (select max(c.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance c where c.hrAttendanceMaintenancePK.compCode = :compCode and c.hrAttendanceMaintenancePK.empCode = :empCode ) and CURRENT_TIMESTAMP ) ";
    public static final String TIME_OUT_FIND_BY_EMP_CODE =
            "select h.timeOut from HrAttendanceMaintenance h where h.hrAttendanceMaintenancePK.compCode = :compCode and h.hrAttendanceMaintenancePK.empCode = :empCode and h.hrAttendanceMaintenancePK.attenDate  "
            + " between (select max(a.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance a where a.hrAttendanceMaintenancePK.compCode = :compCode and a.hrAttendanceMaintenancePK.empCode = :empCode) and CURRENT_TIMESTAMP and h.hrAttendanceMaintenancePK.attenDate in (select max(b.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance b where b.hrAttendanceMaintenancePK.compCode = :compCode and b.hrAttendanceMaintenancePK.empCode = :empCode and b.hrAttendanceMaintenancePK.attenDate between (select max(c.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance c where c.hrAttendanceMaintenancePK.compCode = :compCode and c.hrAttendanceMaintenancePK.empCode = :empCode ) and CURRENT_TIMESTAMP ) ";
    public static final String UPDATE_TIME_OUT_BY_EMP_CODE =
            "update HrAttendanceMaintenance h set h.timeOut = CURRENT_TIMESTAMP , h.attenStatusFixed='Y',h.modDate = CURRENT_TIMESTAMP where h.hrAttendanceMaintenancePK.compCode = :compCode and h.hrAttendanceMaintenancePK.empCode = :empCode and h.hrAttendanceMaintenancePK.attenDate"
            + " between (select max(a.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance a where a.hrAttendanceMaintenancePK.compCode = :compCode and a.hrAttendanceMaintenancePK.empCode = :empCode) and CURRENT_TIMESTAMP and h.hrAttendanceMaintenancePK.attenDate in (select max(b.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance b where b.hrAttendanceMaintenancePK.compCode = :compCode and b.hrAttendanceMaintenancePK.empCode = :empCode and b.hrAttendanceMaintenancePK.attenDate between (select max(c.hrAttendanceMaintenancePK.attenDate) "
            + "from HrAttendanceMaintenance c where c.hrAttendanceMaintenancePK.compCode = :compCode and c.hrAttendanceMaintenancePK.empCode = :empCode ) and CURRENT_TIMESTAMP ) ";
    public static final String VIEW_DATA_BY_MONTH =
            "select e.timeIn , e.timeOut , e.hrAttendanceMaintenancePK.attenDate from HrAttendanceMaintenance e where e.hrAttendanceMaintenancePK.compCode = :compCode and e.hrAttendanceMaintenancePK.empCode = :empCode and e.hrAttendanceMaintenancePK.attenDate between  :value1 and :value2";
    public static final String VIEW_DATA_LEAVE_ALLOCATION =
            "SELECT a.empId,a.empName,h.hrLeavePostingPK.leaveCode,h.postingDate,h.hrLeavePostingPK.dateFrom,h.hrLeavePostingPK.dateTo FROM "
            + "HrLeavePosting h,HrPersonnelDetails a where h.hrLeavePostingPK.compCode = a.hrPersonnelDetailsPK.compCode and h.hrLeavePostingPK.empCode = a.hrPersonnelDetailsPK.empCode "
            + "and h.hrLeavePostingPK.compCode = :compCode and h.hrLeavePostingPK.dateFrom = :dateFrom and h.hrLeavePostingPK.dateTo = :dateTo order by a.empName ";
    public static final String VIEW_DATA_ACCEPTANCE_LETTER =
            "select a.empId,a.empName,a.empType,a.deptCode,c.description,a.desgCode,d.description,a.gradeCode,e.description,a.joinDate,a.noticePeriod,b.sepaCode,b.resignation,b.reason,b.resigAccept,b.separation from HrPersonnelDetails a ,HrSeparationDetails b ,HrMstStruct c ,HrMstStruct d ,HrMstStruct e where "
            + " a.hrPersonnelDetailsPK.compCode = :compCode and a.hrPersonnelDetailsPK.empCode = b.hrSeparationDetailsPK.empCode and a.deptCode = c.hrMstStructPK.structCode "
            + "and  a.desgCode = d.hrMstStructPK.structCode and a.gradeCode = e.hrMstStructPK.structCode and a.hrPersonnelDetailsPK.compCode = c.hrMstStructPK.compCode and a.hrPersonnelDetailsPK.compCode = d.hrMstStructPK.compCode and a.hrPersonnelDetailsPK.compCode = e.hrMstStructPK.compCode";
    public static final String SELECT_DATA_ACCEPTANCE_LETTER =
            "SELECT A.hrPersonnelDetailsPK.empCode,A.empId,A.empName,A.block,B.description,A.unitName,C.description,A.deptCode,D.description,A.desgCode,E.description,A.gradeCode,F.description,A.zones,G.description,A.locatCode,H.description,A.noticePeriod,A.joinDate,A.totExpr,a.repTo FROM HrPersonnelDetails A,HrMstStruct B,HrMstStruct C,HrMstStruct D,HrMstStruct E,HrMstStruct F,HrMstStruct G,HrMstStruct H WHERE A.empId = :empId and A.hrPersonnelDetailsPK.compCode = :compCode "
            + " and A.block = B.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = B.hrMstStructPK.compCode and A.hrPersonnelDetailsPK.compCode = C.hrMstStructPK.compCode "
            + " and A.unitName = C.hrMstStructPK.structCode and A.deptCode= D.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = D.hrMstStructPK.compCode and A.hrPersonnelDetailsPK.compCode = E.hrMstStructPK.compCode "
            + " and A.desgCode = E.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = F.hrMstStructPK.compCode and A.gradeCode = F.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = H.hrMstStructPK.compCode "
            + " and A.locatCode = h.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = G.hrMstStructPK.compCode and A.zones = G.hrMstStructPK.structCode";
    public static final String APPRIASAL_DATA_VIEW =
            "SELECT A.hrApprisalDetailsPK.empCode,B.empId,B.empName,B.joinDate,A.ratingCode,A.apprResult,A.trngCode,A.recHead,A.recHod,A.recHrd,A.recPersonnel,A.incrAmt,A.hrApprisalDetailsPK.appraisalDt,A.promWef,B.deptCode,C.description,B.desgCode,D.description,B.gradeCode,E.description,B.block,F.description,B.unitName,G.description,B.totExpr  FROM HrApprisalDetails A,HrPersonnelDetails B,HrMstStruct C,HrMstStruct D,HrMstStruct E,HrMstStruct F,HrMstStruct G "
            + " where A.hrApprisalDetailsPK.compCode = :compCode and A.hrApprisalDetailsPK.compCode = B.hrPersonnelDetailsPK.compCode and A.hrApprisalDetailsPK.empCode = B.hrPersonnelDetailsPK.empCode and A.hrApprisalDetailsPK.compCode = C.hrMstStructPK.compCode "
            + " and A.hrApprisalDetailsPK.compCode = D.hrMstStructPK.compCode and A.hrApprisalDetailsPK.compCode = E.hrMstStructPK.compCode and A.hrApprisalDetailsPK.compCode = F.hrMstStructPK.compCode and "
            + " B.deptCode = C.hrMstStructPK.structCode and B.desgCode = D.hrMstStructPK.structCode and B.gradeCode= E.hrMstStructPK.structCode and B.block = F.hrMstStructPK.structCode and A.hrApprisalDetailsPK.compCode = G.hrMstStructPK.compCode and B.unitName = G.hrMstStructPK.structCode "
            + " order by B.empName ";
    public static final String VIEW_DATA_SETTLEMENT =
            "SELECT A.clearSlipHdPK.empCode,B.empId,B.joinDate,A.settlementDt,A.totAmt,A.ddChequeNu, "
            + " A.ddCheque,A.ddAmt,A.bankName,B.empName,B.deptCode,C.description,B.desgCode,D.description,B.gradeCode,E.description,B.block,F.description, "
            + " G.separation,H.dueAmt,H.recoverableAmt,H.netAmt,H.remarks,H.clearSlipDetPK.deptCode FROM ClearSlipHd A,HrPersonnelDetails B,HrMstStruct C,HrMstStruct D,HrMstStruct E,HrMstStruct F,HrSeparationDetails G,ClearSlipDet H,HrMstStruct I where "
            + " A.clearSlipHdPK.compCode = :compCode and A.clearSlipHdPK.compCode = B.hrPersonnelDetailsPK.compCode and A.clearSlipHdPK.empCode = B.hrPersonnelDetailsPK.empCode and "
            + " A.clearSlipHdPK.compCode = C.hrMstStructPK.compCode and A.clearSlipHdPK.compCode = D.hrMstStructPK.compCode and A.clearSlipHdPK.compCode = E.hrMstStructPK.compCode and A.clearSlipHdPK.compCode = F.hrMstStructPK.compCode "
            + " and B.deptCode = C.hrMstStructPK.structCode and B.desgCode = D.hrMstStructPK.structCode and B.gradeCode = E.hrMstStructPK.structCode and "
            + " B.block = F.hrMstStructPK.structCode and A.clearSlipHdPK.compCode = G.hrSeparationDetailsPK.compCode and  A.clearSlipHdPK.empCode = G.hrSeparationDetailsPK.empCode and H.clearSlipDetPK.compCode= A.clearSlipHdPK.compCode "
            + " and H.clearSlipDetPK.empCode= A.clearSlipHdPK.empCode and H.clearSlipDetPK.compCode = I.hrMstStructPK.compCode and H.clearSlipDetPK.deptCode= I.hrMstStructPK.structCode";
    public static String SETTLEMENT_SEARCH =
            "select A.hrSeparationDetailsPK.empCode,B.empName,B.empId FROM HrSeparationDetails A,HrPersonnelDetails B where A.hrSeparationDetailsPK.compCode = :compCode and A.statFlag= :statFlag and "
            + " A.hrSeparationDetailsPK.compCode =B.hrPersonnelDetailsPK.compCode and A.hrSeparationDetailsPK.empCode =B.hrPersonnelDetailsPK.empCode ";
    public static String EMP_SEARCH_TAX =
            "select B.hrPersonnelDetailsPK.empCode,B.empName,B.empId FROM HrPersonnelDetails B where B.empStatus= :empStatus ";
    public static String PROMOTION_VIEW =
            "SELECT A.hrPromotionDetailsPK.compCode,A.empCode,B.empId,A.hrPromotionDetailsPK.arNo,A.ardate, "
            + " A.deptFrom,A.deptTo,A.blockTo,A.zoneFrom,A.zoneTo,A.presLocat,A.proposRepId,A.budgtStatus,A.overRating,"
            + " A.remarks1,A.promWef,A.headApprov,A.hrdApprov,A.mdApprov,B.empName,A.proposDesig,A.presDesig,A.blockFrom,A.proposLocat,A.presRepId,A.modDate FROM HrPromotionDetails A,HrPersonnelDetails B where B.hrPersonnelDetailsPK.compCode = :compCode and B.hrPersonnelDetailsPK.compCode = A.hrPromotionDetailsPK.compCode and B.hrPersonnelDetailsPK.empCode = A.empCode";
    public static String UPDATE_PERSONEL =
            "UPDATE HrPersonnelDetails H SET H.locatCode= :locatCode,H.deptCode= :deptCode,H.block= :block,H.repTo=:repto,H.zones= :zones,H.desgCode= :desgCode where H.hrPersonnelDetailsPK.compCode= :compCode and H.hrPersonnelDetailsPK.empCode= :empCode";
    public static final String SELECT_DATA_SETTLEMENT_LETTER =
            "SELECT A.hrPersonnelDetailsPK.empCode,A.empId,A.empName,A.block,B.description,A.deptCode,D.description,A.desgCode,E.description,A.gradeCode,F.description,A.joinDate,I.separation FROM HrPersonnelDetails A,HrMstStruct B,HrMstStruct C,HrMstStruct D,HrMstStruct E,HrMstStruct F,HrMstStruct G,HrMstStruct H,HrSeparationDetails I WHERE A.empId = :empId and A.hrPersonnelDetailsPK.compCode = :compCode "
            + " and A.block = B.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = B.hrMstStructPK.compCode and A.hrPersonnelDetailsPK.compCode = C.hrMstStructPK.compCode "
            + " and A.unitName = C.hrMstStructPK.structCode and A.deptCode= D.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = D.hrMstStructPK.compCode and A.hrPersonnelDetailsPK.compCode = E.hrMstStructPK.compCode "
            + " and A.desgCode = E.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = F.hrMstStructPK.compCode and A.gradeCode = F.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = H.hrMstStructPK.compCode "
            + " and A.locatCode = h.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = G.hrMstStructPK.compCode and A.zones = G.hrMstStructPK.structCode and A.hrPersonnelDetailsPK.compCode = I.hrSeparationDetailsPK.compCode and A.hrPersonnelDetailsPK.empCode = I.hrSeparationDetailsPK.empCode";
    public static final String CHECK_DATA_EXIT_INTERVIEW_ADD =
            "select A from HrSeparationDetails A,HrExitInterviewHd B where A.hrSeparationDetailsPK.compCode = :compCode and A.hrSeparationDetailsPK.compCode = B.hrExitInterviewHdPK.compCode and "
            + " A.hrSeparationDetailsPK.empCode <> B.hrExitInterviewHdPK.empCode";
    public static final String CHECK_DATA_EXIT_INTERVIEW_VIEW = "select A from HrSeparationDetails A,HrExitInterviewHd B where A.hrSeparationDetailsPK.compCode = :compCode and A.hrSeparationDetailsPK.compCode = B.hrExitInterviewHdPK.compCode and "
            + " A.hrSeparationDetailsPK.empCode = B.hrExitInterviewHdPK.empCode";
    public static final String VIEW_DATA_EXIT_INTERVIEW = "SELECT A.hrExitInterviewDtPK.reasonCode,B.satisAsses,B.disatisAsses FROM HrExitInterviewDt A,"
            + "HrExitInterviewHd B where A.hrExitInterviewDtPK.compCode= :compCode and A.hrExitInterviewDtPK.empCode = :empCode "
            + "and A.hrExitInterviewDtPK.compCode= B.hrExitInterviewHdPK.compCode and A.hrExitInterviewDtPK.empCode = B.hrExitInterviewHdPK.empCode";
//    public static final String APPRAISAL_ENTITY_BY_COMPANYCODE = "StrMstControl.findByCompCodeAndArn";
//    public static final String APPRAISAL_FIND_MAX_ENDNUMBER_BY_COMPCODE = "StrMstControl.findEndNumberByCompCodeAndArn";
//    public static final String APPRAISAL_UPDATE_ADVERTISEMENT = "StrMstControl.updateAdvCodeByCompCodeArn";
    public static final String FIND_HrPersonnelLang_BY_EMPCODE = "HrPersonnelLang.findByEmpCode";
    public static final String FIND_HrPersonnelHobby_BY_EMPCODE = "HrPersonnelHobby.findByEmpCode";
    public static final String FIND_HrMembershipDetail_BY_EMPCODE = "HrMembershipDetail.findByEmpCode";
    public static final String FIND_HrPersonnelTransport_BY_EMPCODE = "HrPersonnelTransport.findByEmpCode";
    public static final String FIND_HrPersonnelPreviousCompany_BY_EMPCODE = "HrPersonnelPreviousCompany.findByEmpCode";
    public static final String FIND_HrPersonnelDependent_BY_EMPCODE = "HrPersonnelDependent.findByEmpCode";
    public static final String FIND_HrPersonnelQualification_BY_EMPCODE = "HrPersonnelQualification.findByEmpCode";
    public static final String FIND_HrPersonnelReference_BY_EMPCODE = "HrPersonnelReference.findByEmpCode";
    public static final String FIND_SALARY_ALLOCATE_BY_EMPCODE = "HrSalaryAllocation.findsalaryAllocByEmpCode";
    /**
     * ***************** PAYROLL QUERIES ****************
     */
    public static final String PAYROLL_CALENDAR_LIST_BY_COMPANY_CODE = "HrPayrollCalendar.findPayrollCalendarListByCompCode";
    public static final String FIND_ENTITY_BY_FROM_TO_DATE = "HrPayrollCloseTrace.findByFromDateAndToDate";
    public static final String FIND_MAX_TRACE_PROBLEM_CODE = "HrPayrollCloseTrace.findTraceProblemCode";
    public static final String FIND_LEAVE_POSTING_FOR_EMP_CODE = "HrLeavePosting.findhrLeavePostingByEmpCode";
    public static final String FIND_ENTITY_BY_COMPANYCODE_AND_EMPCODE = "HrShiftMap.findByCompCodeAndEmpCode";
    public static final String ATTENDANCE_DETAILS_POSTED_OF_MONTH = "HrAttendanceDetails.findPostedAttendanceDetailsOfMonth";
    public static final String ALLOWANCE_PROCESSING_BY_EMPCODE_AND_EMPCODE = "HrAllowanceProcessing.findByCompCodeAndEmpCode";
    public static final String DEDUCTION_PROCESSING_FOR_EMPLOYEE_FOR_MONTH = "HrDeductionProcessing.findByEmpCodeAndCompCode";
    public static final String EMPLOYEE_SALARY_TRANSFER_FOR_MONTH = "HrSalaryProcessing.findEmpSalaryTransferForTheMonth";
    public static final String FIND_TAXABLE_ENTITY = "HrSalaryStructure.findTaxableEntity";
//    public static final String FIND_HR_SALARY_STRUCTURE = "SELECT A.hrSalaryStructurePK.purposeCode,B.description,A.hrSalaryStructurePK.alDesc,"
//            + "A.taxable,A.hrSalaryStructurePK.nature,C.description,A.applicableDate,"
//            + "A.hrSalaryStructurePK.dateFrom,A.hrSalaryStructurePK.dateTo FROM HrSalaryStructure A,"
//            + "HrMstStruct B,HrMstStruct C WHERE A.hrSalaryStructurePK.compCode = :compCode "
//            + "AND A.hrSalaryStructurePK.dateFrom = :datFrom AND "
//            + "A.hrSalaryStructurePK.dateTo = :dateTo AND "
//            + "A.hrSalaryStructurePK.compCode = B.hrMstStructPK.compCode"
//            + " AND B.hrMstStructPK.compCode = C.hrMstStructPK.compCode AND "
//            + "A.hrSalaryStructurePK.purposeCode=B.hrMstStructPK.structCode AND "
//            + "A.hrSalaryStructurePK.nature=C.hrMstStructPK.structCode";
    public static final String FIND_HR_SALARY_STRUCTURE = "SELECT A.hrSalaryStructurePK.purposeCode,B.description,A.hrSalaryStructurePK.alDesc,"
            + "A.taxable,A.hrSalaryStructurePK.nature,C.description,A.applicableDate,A.glCode,A.descShCode"
            + " FROM HrSalaryStructure A,"
            + "HrMstStruct B,HrMstStruct C WHERE A.hrSalaryStructurePK.compCode = :compCode "
            + "AND A.hrSalaryStructurePK.compCode = B.hrMstStructPK.compCode"
            + " AND B.hrMstStructPK.compCode = C.hrMstStructPK.compCode AND "
            + "A.hrSalaryStructurePK.purposeCode=B.hrMstStructPK.structCode AND "
            + "A.hrSalaryStructurePK.nature=C.hrMstStructPK.structCode";
    public static final String FIND_ENTITY_FOR_SLAB_DESCRIPTION = "HrSalaryStructure.findEntityForSlabDescription";
    public static final String FIND_HRPERSONNEL_BY_PARAMETERS = "HrPersonnelDetails.findEmpCompByVariousParameters";
    public static final String SALARYSTRUCTURE_BY_COMCODE_DATEFROM_DATETO = "HrSalaryStructure.findByCompCodeDateFromDateTo";
    public static final String SALARYSTRUCTURE_NOT_SAVED = "SELECT h FROM HrSalaryStructure h"
            + " WHERE h.hrSalaryStructurePK.alDesc NOT IN "
            + " (SELECT distinct k.hrSalaryAllocationPK.componentType FROM HrSalaryAllocation k WHERE k.hrSalaryAllocationPK.compCode=:compCode "
            + " and k.hrSalaryAllocationPK.empCode=(select B.hrPersonnelDetailsPK.empCode from HrPersonnelDetails B "
            + " where B.empId=:empCd and B.hrPersonnelDetailsPK.compCode=:compCode))";   
    public static final String SLABMASTER_BY_VARIOUS_PARAMETERS = "HrSlabMaster.findByVariousParameters";
    public static final String SLABMASTER_BY_VARIOUS_PARAMETERS1 = "HrSlabMaster.findByVariousParametsrs1";
    public static final String HRSALARYALLOCATION_BY_VARIOUS_PARAMETERS = "HrSalaryAllocation.findByEmpMonDatesComponenetType";
    public static final String HR_SALARYSTRUCT_BY_DATES_AND_ALDESC = "HrSalaryStructure.findByCompDatesAldesc";
//    public static final String HR_PERSONNEL_BY_EMPID_EMPSTATUS_COMPCODE = "HrPersonnelDetails.findByCompEmpIdWithStatusY";
    //public static final String HR_PERSONNEL_BY_EMPID_EMPSTATUS_COMPCODE = QueryConstant.FIND_BY_COMP_EMP_ID_WITH_STATUS_Y;
    public static final String HR_PERSONNEL_BY_EMPID_EMPSTATUS_COMPCODE = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode=:compCode AND h.empId like:empId AND h.empStatus=:empStatus order by h.empId";
    public static final String HOLIDAY_MASTER_BY_COMP_CODE_AND_HOLIDAY_DATE = "HrHolidayMaster.findByCompCodeAndHolidayDate";
    public static final String HOLIDAY_MASTER_BY_PRIMARY_KEY = "HrHolidayMaster.findByPrimaryKey";
    public static final String LEAVE_REGISTER_BY_EMPCODE_COMPCODE_AND_DEPARTRECOM = "HrLeaveRegister.findByEmpCodeDepartRecom";
    public static final String ATTENDANCE_MAINTENANCE_BY_EMPCODE_COMPCODE_AND_ATTENDATE = "HrAttendanceMaintenance.findByComCodeEmCodeAndAttenDate";
    public static final String HOLIDAY_MASTER_BY_COMP_CODE_AND_HOLIDAY_DATE_AND_DESC =
            "SELECT h FROM HrHolidayMaster h WHERE "
            + "h.hrHolidayMasterPK.compCode = :compCode and h.holidayDate = :holidayDate and h.holidayDesc =:desc and "
            + "h.hrPayrollCalendar.hrPayrollCalendarPK.dateFrom =:calFromDate and h.hrPayrollCalendar.hrPayrollCalendarPK.dateTo =:calToDate";
    public static final String GET_EMP_SHIFTS_DETAILS =
            "Select A.shiftIn,A.shiftOut,A.graceShiftIn,A.graceShiftOut,A.hrMstShiftPK.shiftCode from HrMstShift A , HrShiftMap B where "
            + "A.hrMstShiftPK.compCode = :compCode and A.hrMstShiftPK.compCode = B.hrShiftMapPK.compCode and A.hrMstShiftPK.shiftCode="
            + "B.hrShiftMapPK.shiftCode and B.hrShiftMapPK.empCode = :empCode";
//    public static final String ATTENDANCE_DETAILS_FOR_CURRENT_YEAR =
//            "select A.hrAttendanceDetailsPK.empCode , A.hrAttendanceDetailsPK.attenMonth ,"
//            + " A.hrAttendanceDetailsPK.attenYear , A.processDateFrom , A.processDateTo , "
//            + "A.workingDays, A.presentDays , A.absentDays , A.overTimePeriod,A.overTimeUnit,"
//            + " A.postFlag , B.empId ,B.empName from HrAttendanceDetails A , HrPersonnelDetails B"
//            + " where A.hrAttendanceDetailsPK.compCode = B.hrPersonnelDetailsPK.compCode and "
//            + "A.hrAttendanceDetailsPK.empCode = B.hrPersonnelDetailsPK.empCode and A.postFlag='N' and "
//            + " A.hrAttendanceDetailsPK.compCode = :compCode and A.hrAttendanceDetailsPK.attenYear = :attenYear";
    public static final String ATTENDANCE_DETAILS_FOR_CURRENT_YEAR =
            "select A.hrAttendanceDetailsPK.empCode , A.hrAttendanceDetailsPK.attenMonth ,"
            + " A.hrAttendanceDetailsPK.attenYear , A.processDateFrom , A.processDateTo , "
            + "A.workingDays, A.presentDays , A.absentDays , A.overTimePeriod,A.overTimeUnit,"
            + " A.postFlag , B.empId ,B.empName,A.paidLeave, A.deductDays from HrAttendanceDetails A , HrPersonnelDetails B"
            + " where A.hrAttendanceDetailsPK.compCode = B.hrPersonnelDetailsPK.compCode and "
            + "A.hrAttendanceDetailsPK.empCode = B.hrPersonnelDetailsPK.empCode and A.postFlag='N' and "
            + " A.hrAttendanceDetailsPK.compCode = :compCode";
    public static final String ATTENDANCE_DETAILS_BY_PRIMARY_KEY_AND_POSTFLAG = "HrAttendanceDetails.findByPrimaryComositeKeyAndPostFlag";
    public static final String ALLOWANCE_PROCESSING_BY_EMPCODE_DATEFROM_AND_DATETO = "HrAllowanceProcessing.findByEmpCodeAndCalDateFromAndCalDateTo";
    public static final String ALLOWANCE_PROCESSING_FOR_MONTH_WITH_POSTING_FLAG = "HrAllowanceProcessing.findByMonthWithPostingFlag";
    public static final String ALLOWANCE_PROCESSING_ALL_MONTH_WITH_POSTFLAG_PARAMETER = "HrAllowanceProcessing.findAllowanceProcessAllMonthWithPostFlag";
//    public static final String GET_TOTAL_ALLOWANCE_BY_EMPCODE_MONTHS_FINACIAL_YEAR_DATES =
//            "select A.componentAmount,A.hrSalaryAllocationPK.componentType FROM HrSalaryAllocation A , HrSalaryStructure B where A.hrSalaryAllocationPK.compCode = :compCode"
//            + " and A.hrSalaryAllocationPK.compCode = B.hrSalaryStructurePK.compCode and A.hrSalaryAllocationPK.empCode = :empCode"
//            + " and A.hrSalaryAllocationPK.componentType = B.hrSalaryStructurePK.alDesc and B.hrSalaryStructurePK.purposeCode = :purposeCode "
//            + "and A.hrSalaryAllocationPK.months = :months and A.hrSalaryAllocationPK.calDateFrom = :calDateFrom and A.hrSalaryAllocationPK.calDateTo = :calDateTo "
//            + "and A.hrSalaryAllocationPK.calDateFrom = B.hrSalaryStructurePK.dateFrom and A.hrSalaryAllocationPK.calDateTo = B.hrSalaryStructurePK.dateTo";
    public static final String GET_TOTAL_ALLOWANCE_BY_EMPCODE_MONTHS_FINACIAL_YEAR_DATES =
            "select A.componentAmount,A.hrSalaryAllocationPK.componentType,B.glCode,B.descShCode FROM HrSalaryAllocation A , HrSalaryStructure B where A.hrSalaryAllocationPK.compCode = :compCode"
            + " and A.hrSalaryAllocationPK.compCode = B.hrSalaryStructurePK.compCode and A.hrSalaryAllocationPK.empCode = :empCode"
            + " and A.hrSalaryAllocationPK.componentType = B.hrSalaryStructurePK.alDesc and B.hrSalaryStructurePK.purposeCode = :purposeCode ";            
    public static final String DEDUCTION_PROCESSING_OF_EMPLOYEE_BETWEEN_FINANCIAL_YEARS_WITH_POST_FLAG = "HrDeductionProcessing.findEmpDeductProcessBetweenFinanacialdates";
    public static final String EMP_LOAN_HEADER_BY_EMPCODE_AND_COMPANY_CODE = "HrEmpLoanHd.findByCompCodeAndEmpCode";
    public static final String EMP_LOAN_DETAILS_BY_EMPCODE_EMPLOAN_AND_FINANCIAL_YRS = "HrEmpLoanDt.findEmpLoanDetailsByEmpcodeEmpLoanNo";
    public static final String DEDUCTION_PROCESSING_EMP_BETWEEN_FISCAL_YEAR = "HrDeductionProcessing.findEmpDeductionBetweenFinanacialdates";
    public static final String GET_TOTAL_DEDUCTION_OF_EMPLOYEE_BETWEEN_FINANCIAL_YEAR =
            "select A.componentAmount FROM HrSalaryAllocation A , HrSalaryStructure B where A.hrSalaryAllocationPK.compCode = :compCode"
            + " and A.hrSalaryAllocationPK.compCode = B.hrSalaryStructurePK.compCode and A.hrSalaryAllocationPK.empCode = :empCode"
            + " and A.hrSalaryAllocationPK.componentType = B.hrSalaryStructurePK.alDesc and B.hrSalaryStructurePK.purposeCode = :purposeCode "
            + "and A.hrSalaryAllocationPK.months = :months and A.hrSalaryAllocationPK.calDateFrom = :calDateFrom and A.hrSalaryAllocationPK.calDateTo = :calDateTo "
            + "and A.hrSalaryAllocationPK.calDateFrom = B.hrSalaryStructurePK.dateFrom and A.hrSalaryAllocationPK.calDateTo = B.hrSalaryStructurePK.dateTo";
    public static final String GET_TA_AMT_OF_EMPLOYEE_BETWEEN_FINANCIAL_YEAR =
            "select A.componentAmount FROM HrSalaryAllocation A , HrSalaryStructure B where A.hrSalaryAllocationPK.compCode = :compCode"
            + " and A.hrSalaryAllocationPK.compCode = B.hrSalaryStructurePK.compCode and A.hrSalaryAllocationPK.empCode = :empCode"
            + " and A.hrSalaryAllocationPK.componentType = B.hrSalaryStructurePK.alDesc and B.hrSalaryStructurePK.purposeCode = :purposeCode "
            + "and A.hrSalaryAllocationPK.months = :months and A.hrSalaryAllocationPK.calDateFrom = :calDateFrom and A.hrSalaryAllocationPK.calDateTo = :calDateTo "
            + "and A.hrSalaryAllocationPK.calDateFrom = B.hrSalaryStructurePK.dateFrom and A.hrSalaryAllocationPK.calDateTo = B.hrSalaryStructurePK.dateTo and A.hrSalaryAllocationPK.componentType = :componentType";
    public static final String BONUS_PROCESSING_FOR_EMP_IN_FINANCIAL_YEAR = "HrBonusProcessing.findBonusProcessingForEmp";
    public static final String BONUS_PROCESSING_FOR_EMP_FOR_MONTH = "HrBonusProcessing.findNotPostedEntriesForMonth";
    public static final String BONUS_PROCESSING_FOR_EMP_FOR_YEAR = "HrBonusProcessing.findNotPostedEntriesForYear";
    public static final String ARREAR_PROCESSING_FOR_EMP_FOR_YEAR = "HrArrearProcessing.findArrearProcessOfEmployee";
    public static final String ARREAR_PROCESSING_FOR_EMP_FOR_MONTH_WITH_POST_FLAG = "HrArrearProcessing.findArrearProcesForMonth";
    public static final String ARREAR_PROCESSING_FOR_EMP_FOR_YEAR_WITH_POST_FLAG = "HrArrearProcessing.findArrearProcesForYear";
    public static final String TAX_PROCESSING_FOR_EMP_FOR_YEAR = "HrTaxProcessing.findTaxProcessingForYear";
    public static final String TAX_PROCESSSING_FOR_EMP_FOR_MONTH_WITH_POSTFLAG = "HrTaxProcessing.findTaxProcessingForEmpMonth";
    public static final String TAX_PROCESSING_FOR_EMP_FOR_YEAR_WITH_POSTFLAG = "HrTaxProcessing.findTaxProcessingForEmpYear";
    public static final String TAX_DETAILS_ENTITY_BASED_ON_GROSS_SALARY = "HrTaxSal.findTaxDetailsBasedOnGrossSalary";
    public static final String SALARY_PROCESSED_FOR_YEAR = "HrSalaryProcessing.findEmpSalaryForyear";
    public static final String SALARY_PROCESSING_FOR_EMP_FOR_YEAR_WITH_POSTFLAG = "HrSalaryProcessing.findEmpSalaryForYearWithPostFlag";
    public static final String SALARY_PROCESSED_FOR_MONTH_WITH_POSTFLAG = "HrSalaryProcessing.findEmpSalaryForTheMonthWithPostFlag";
    public static final String SALARY_ALLOCATION_FOR_THE_MONTH = "HrSalaryAllocation.findSalaryAllocateForMonth";
    public static final String ATTENDANCE_DETAILS_BETWEEN_PAGE_DATES_AND_POSTFLAG = "HrAttendanceDetails.findAttenDetailsWithPostFlag";
    public static final String PAYROLL_CALENDAR_EXISTING_AND_STATUS_OPEN = "HrPayrollCalendar.HrPayrollCalendar.findExistingPayrollCalendar";
    public static final String TAX_DETAILS_BY_GROSS_SALARY_WISE = "HrTaxSal.findDetailsByCompCodeAndDates";
    public static final String TAX_COMPO_WISE_BY_COMPANY_CODE = "HrTaxSalComp.findByCompCode";
    public static final String SALARY_STRUCTURE_DESCRIPTION_WITH_TAXABLE = "HrSalaryStructure.findEntityByTaxableAndCompCode";
    public static final String SALARY_STRUCTURE_BY_PRIMARY_KEY = "HrSalaryStructure.findEntityByPrimaryKey";
    public static final String SLAB_MASTER_BY_PRIMARY_KEY = "HrSlabMaster.findEntityByKey";
    public static final String SLAB_MASTER_WITH_APPLIED_FLAG = "HrSlabMaster.findEntityWithAppFlag";
    public static final String SLAB_MASTER_WITHOUT_APP_FLAG = "HrSlabMaster.findEntityWithoutAppFlag";
//    public static final String PERSONNEL_DETAILS_WITH_EMP_STATUS = "HrPersonnelDetails.findCategorizationBasedEmployeesWithEmpStatus";
    //public static final String PERSONNEL_DETAILS_WITH_EMP_STATUS = QueryConstant.FIND_CATEGORIZATION_BASED_EMPLOYEES_WITH_EMP_STATUS;
    public static final String PERSONNEL_DETAILS_WITH_EMP_STATUS = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empStatus = :empstatus AND (h.deptCode= :type OR h.gradeCode= :type OR h.unitName= :type OR h.block =:type OR h.desgCode= :type OR h.catgCode= :type OR h.zones= :type OR h.locatCode= :type OR h.empType= :type OR h.baseBranch= :type)";
    public static final String FIND_MAX_CATEGORYCODE = "HrTaxInvestmentCategory.findMaxCategoryCode";
    /**
     * ************ END_PAYROLL_QUERIES **********************
     */
    public static final String ROLE_URL_FIND_BY_ROLE_PARENT_CODE = "HrRoleUrlMaster.findByRoleNameAndParentCode";
    public static final String ROLE_URL_FIND_BY_ROLE_AND_DISPLAY_NAME = "HrRoleUrlMaster.findByRoleNameAndDisplayName";
    public static final String USER_FIND_BY_USERID = "Securityinfo.findByUserId";
    public static final String FIND_BY_COMP_CODE_AND_EMP_CODE = "HrTaxInvestmentCategory.findByCompCodeAndEmpCode";
    public static final String FIND_BY_COMP_CODE_AND_TAX_FOR = "HrTaxSlabMaster.findByCompCodeAndTaxFor";
    public static final String VIEW_DATA_TAXINVESTMENTCATEGORY = "HrTaxInvestmentCategory.findByCompCode";
    public static final String SELECT_CATEGORY_CODE = "select A.hrTaxInvestmentCategoryPK.categoryCode FROM HrTaxInvestmentCategory A,HrMstStruct B where A.hrTaxInvestmentCategoryPK.categoryCode=B.hrMstStructPK.structCode  and A.hrTaxInvestmentCategoryPK.compCode=B.hrMstStructPK.compCode";
    public static final String FIND_EMPLOYEE_NAME_BY_EMPCODE = "HrPersonnelDetails.findByEmpCode";
    public static final String FIND_BY_CUSTOMER_ID = "SELECT h FROM HrPersonnelDetails h WHERE h.customerId=:customerId";
    public static final String FIND_ALL_LEAVE_BY_COMP_CODE = "SELECT h FROM HrLeaveRegister h WHERE h.hrLeaveRegisterPK.compCode = :compCode order by h.hrLeaveRegisterPK.leavRegCode";
    
    public static final String FIND_MAX_STRUCT_SHORT_CODE = "SELECT max(A.descShCode) "
            + "FROM  HrSalaryStructure A WHERE A.hrSalaryStructurePK.compCode= :value1 and A.hrSalaryStructurePK.purposeCode= :value2";
    
}
