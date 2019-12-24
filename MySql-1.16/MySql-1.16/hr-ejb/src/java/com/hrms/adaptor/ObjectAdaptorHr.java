package com.hrms.adaptor;

import com.hrms.common.to.HrAdvertDetDtPKTO;
import com.hrms.common.to.HrAdvertDetDtTO;
import com.hrms.common.to.HrAdvertDtPKTO;
import com.hrms.common.to.HrAdvertDtTO;
import com.hrms.common.to.HrAdvertHdPKTO;
import com.hrms.common.to.HrAdvertHdTO;
import com.hrms.common.to.HrConsultantPKTO;
import com.hrms.common.to.HrConsultantTO;
import com.hrms.common.to.HrDataPrevCompPKTO;
import com.hrms.common.to.HrDataPrevCompTO;
import com.hrms.common.to.HrDataQualPKTO;
import com.hrms.common.to.HrDataQualTO;
import com.hrms.common.to.HrDataReferencePKTO;
import com.hrms.common.to.HrDataReferenceTO;
import com.hrms.common.to.HrDatabankPKTO;
import com.hrms.common.to.HrDatabankTO;
import com.hrms.common.to.HrDetailsProgramPKTO;
import com.hrms.common.to.HrDetailsProgramTO;
import com.hrms.common.to.HrDirectRecPKTO;
import com.hrms.common.to.HrDirectRecTO;
import com.hrms.common.to.HrInterviewDtPKTO;
import com.hrms.common.to.HrInterviewDtSalPKTO;
import com.hrms.common.to.HrInterviewDtSalTO;
import com.hrms.common.to.HrInterviewDtTO;
import com.hrms.common.to.HrInterviewHdPKTO;
import com.hrms.common.to.HrInterviewHdTO;
import com.hrms.common.to.HrManpowerPKTO;
import com.hrms.common.to.HrManpowerTO;
import com.hrms.common.to.HrMstDesgPKTO;
import com.hrms.common.to.HrMstDesgTO;
import com.hrms.common.to.HrMstInstitutePKTO;
import com.hrms.common.to.HrMstInstituteTO;
import com.hrms.common.to.HrMstProgramPKTO;
import com.hrms.common.to.HrMstProgramTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrMstTrngProgramPKTO;
import com.hrms.common.to.HrMstTrngProgramTO;
import com.hrms.common.to.HrMstZoneLocationPKTO;
import com.hrms.common.to.HrMstZoneLocationTO;
import com.hrms.common.to.HrOrgChartPKTO;
import com.hrms.common.to.HrOrgChartTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrSalaryStructurePKTO;
import com.hrms.common.to.HrSalaryStructureTO;
import com.hrms.common.to.HrTrainingExecutionPKTO;
import com.hrms.common.to.HrTrainingExecutionTO;
import com.hrms.common.to.HrTrainingPlanPKTO;
import com.hrms.common.to.HrTrainingPlanTO;
import com.hrms.common.to.ParametersTO;
import com.hrms.entity.hr.HrAdvertDetDt;
import com.hrms.entity.hr.HrAdvertDetDtPK;
import com.hrms.entity.hr.HrAdvertDt;
import com.hrms.entity.hr.HrAdvertDtPK;
import com.hrms.entity.hr.HrAdvertHd;
import com.hrms.entity.hr.HrAdvertHdPK;
import com.hrms.entity.hr.HrConsultant;
import com.hrms.entity.hr.HrConsultantPK;
import com.hrms.entity.hr.HrDataPrevComp;
import com.hrms.entity.hr.HrDataPrevCompPK;
import com.hrms.entity.hr.HrDataQual;
import com.hrms.entity.hr.HrDataQualPK;
import com.hrms.entity.hr.HrDataReference;
import com.hrms.entity.hr.HrDataReferencePK;
import com.hrms.entity.hr.HrDatabank;
import com.hrms.entity.hr.HrDatabankPK;
import com.hrms.entity.hr.HrDetailsProgram;
import com.hrms.entity.hr.HrDetailsProgramPK;
import com.hrms.entity.hr.HrDirectRec;
import com.hrms.entity.hr.HrDirectRecPK;
import com.hrms.entity.hr.HrInterviewDt;
import com.hrms.entity.hr.HrInterviewDtPK;
import com.hrms.entity.hr.HrInterviewDtSal;
import com.hrms.entity.hr.HrInterviewDtSalPK;
import com.hrms.entity.hr.HrInterviewHd;
import com.hrms.entity.hr.HrInterviewHdPK;
import com.hrms.entity.hr.HrManpower;
import com.hrms.entity.hr.HrManpowerPK;
import com.hrms.entity.hr.HrMstDesg;
import com.hrms.entity.hr.HrMstDesgPK;
import com.hrms.entity.hr.HrMstInstitute;
import com.hrms.entity.hr.HrMstInstitutePK;
import com.hrms.entity.hr.HrMstProgram;
import com.hrms.entity.hr.HrMstProgramPK;
import com.hrms.entity.hr.HrMstStruct;
import com.hrms.entity.hr.HrMstStructPK;
import com.hrms.entity.hr.HrMstTrngProgram;
import com.hrms.entity.hr.HrMstTrngProgramPK;
import com.hrms.entity.hr.HrMstZoneLocation;
import com.hrms.entity.hr.HrMstZoneLocationPK;
import com.hrms.entity.hr.HrOrgChart;
import com.hrms.entity.hr.HrOrgChartPK;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.hr.HrPersonnelDetailsPK;
import com.hrms.entity.hr.HrSalaryStructure;
import com.hrms.entity.hr.HrSalaryStructurePK;
import com.hrms.entity.hr.HrTrainingExecution;
import com.hrms.entity.hr.HrTrainingExecutionPK;
import com.hrms.entity.hr.HrTrainingPlan;
import com.hrms.entity.hr.HrTrainingPlanPK;
import com.hrms.entity.hr.Parameters;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Zeeshan waris
 */
public class ObjectAdaptorHr {
   

    public static HrMstStructPK adaptToMstStructPKEntity(HrMstStructPKTO structMasterPKTO) {
        HrMstStructPK mstStructPK = new HrMstStructPK();
        mstStructPK.setCompCode(structMasterPKTO.getCompCode());
        mstStructPK.setStructCode(structMasterPKTO.getStructCode());
        return mstStructPK;

    }

    public static HrMstStructPKTO adaptToStructMasterPKTO(HrMstStructPK hrMstStructPK) {
        HrMstStructPKTO structMasterPKTO = new HrMstStructPKTO();
        structMasterPKTO.setCompCode(hrMstStructPK.getCompCode());
        structMasterPKTO.setStructCode(hrMstStructPK.getStructCode());
        return structMasterPKTO;

    }

    public static HrMstStruct adaptToMstStructEntity(HrMstStructTO structMasterTO) {
        HrMstStruct hrMstStruct = new HrMstStruct();
        hrMstStruct.setHrMstStructPK(ObjectAdaptorHr.adaptToMstStructPKEntity(structMasterTO.getHrMstStructPKTO()));
        hrMstStruct.setEnteredBy(structMasterTO.getEnteredBy());
        hrMstStruct.setDefaultComp(structMasterTO.getDefaultComp());
        hrMstStruct.setDescription(structMasterTO.getDescription());
        hrMstStruct.setAuthBy(structMasterTO.getAuthBy());
        hrMstStruct.setModDate(structMasterTO.getModDate());
        hrMstStruct.setStatFlag(structMasterTO.getStatFlag());
        hrMstStruct.setStatUpFlag(structMasterTO.getStatUpFlag());
        return hrMstStruct;
    }

    public static HrMstStructTO adaptToStructMasterTO(HrMstStruct hrMstStruct) {
        HrMstStructTO structMasterTo = new HrMstStructTO();
        structMasterTo.setHrMstStructPKTO(ObjectAdaptorHr.adaptToStructMasterPKTO(hrMstStruct.getHrMstStructPK()));
        structMasterTo.setEnteredBy(hrMstStruct.getEnteredBy());
        structMasterTo.setDefaultComp(hrMstStruct.getDefaultComp());

        structMasterTo.setDescription(hrMstStruct.getDescription());
        structMasterTo.setAuthBy(hrMstStruct.getAuthBy());
        structMasterTo.setModDate(hrMstStruct.getModDate());
        structMasterTo.setStatFlag(hrMstStruct.getStatFlag());

        structMasterTo.setStatUpFlag(hrMstStruct.getStatUpFlag());
        return structMasterTo;
    }

    /**
     * ************** Written By Zeeshan ***********************
     */
    public static HrConsultantPK adaptToHrConsultantPKEntity(HrConsultantPKTO consultantPKTO) {
        HrConsultantPK consultantPK = new HrConsultantPK();
        consultantPK.setCompCode(consultantPKTO.getCompCode());
        consultantPK.setConsCode(consultantPKTO.getConsCode());
        return consultantPK;

    }

    public static HrConsultantPKTO adaptToHrConsultantPKTO(HrConsultantPK hrConsultantPK) {
        HrConsultantPKTO hrConsultantPKTO = new HrConsultantPKTO();
        hrConsultantPKTO.setCompCode(hrConsultantPK.getCompCode());
        hrConsultantPKTO.setConsCode(hrConsultantPK.getConsCode());
        return hrConsultantPKTO;

    }

    public static HrConsultant adaptToHrConsultantEntity(HrConsultantTO hrConsultantTO) {
        HrConsultant hrConsultant = new HrConsultant();
        hrConsultant.setHrConsultantPK(ObjectAdaptorHr.adaptToHrConsultantPKEntity(hrConsultantTO.getHrConsultantPKTO()));
        hrConsultant.setConsFirName(hrConsultantTO.getConsFirName());
        hrConsultant.setConsMidName(hrConsultantTO.getConsMidName());
        hrConsultant.setConsLastName(hrConsultantTO.getConsLastName());
        hrConsultant.setConsAdd(hrConsultantTO.getConsAdd());
        hrConsultant.setConsCity(hrConsultantTO.getConsCity());
        hrConsultant.setConsPin(hrConsultantTO.getConsPin());
        hrConsultant.setConsState(hrConsultantTO.getConsState());
        hrConsultant.setFaxNumber(hrConsultantTO.getFaxNumber());
        hrConsultant.setMobileNumber(hrConsultantTO.getMobileNumber());
        hrConsultant.setTeleNumber(hrConsultantTO.getTeleNumber());
        hrConsultant.setContPerson(hrConsultantTO.getContPerson());
        hrConsultant.setEmail(hrConsultantTO.getEmail());
        hrConsultant.setContDesg(hrConsultantTO.getContDesg());
        hrConsultant.setConsFee(hrConsultantTO.getConsFee());
        hrConsultant.setDefaultComp(hrConsultantTO.getDefaultComp());
        hrConsultant.setStatFlag(hrConsultantTO.getStatFlag());
        hrConsultant.setStatUpFlag(hrConsultantTO.getStatUpFlag());
        hrConsultant.setDtModDate(hrConsultantTO.getDtModDate());
        hrConsultant.setAuthBy(hrConsultantTO.getAuthBy());
        hrConsultant.setEnteredBy(hrConsultantTO.getEnteredBy());
        return hrConsultant;
    }

    public static HrConsultantTO adaptToHrConsultantTO(HrConsultant hrConsultant) {
        HrConsultantTO hrConsultantTo = new HrConsultantTO();
        hrConsultantTo.setHrConsultantPKTO(ObjectAdaptorHr.adaptToHrConsultantPKTO(hrConsultant.getHrConsultantPK()));
        hrConsultantTo.setConsFirName(hrConsultant.getConsFirName());
        hrConsultantTo.setConsMidName(hrConsultant.getConsMidName());
        hrConsultantTo.setConsLastName(hrConsultant.getConsLastName());
        hrConsultantTo.setConsAdd(hrConsultant.getConsAdd());
        hrConsultantTo.setConsCity(hrConsultant.getConsCity());
        hrConsultantTo.setConsPin(hrConsultant.getConsPin());
        hrConsultantTo.setConsState(hrConsultant.getConsState());
        hrConsultantTo.setFaxNumber(hrConsultant.getFaxNumber());
        hrConsultantTo.setMobileNumber(hrConsultant.getMobileNumber());
        hrConsultantTo.setTeleNumber(hrConsultant.getTeleNumber());
        hrConsultantTo.setContPerson(hrConsultant.getContPerson());
        hrConsultantTo.setEmail(hrConsultant.getEmail());
        hrConsultantTo.setContDesg(hrConsultant.getContDesg());
        hrConsultantTo.setConsFee(hrConsultant.getConsFee());
        hrConsultantTo.setDefaultComp(hrConsultant.getDefaultComp());
        hrConsultantTo.setStatFlag(hrConsultant.getStatFlag());
        hrConsultantTo.setStatUpFlag(hrConsultant.getStatUpFlag());
        hrConsultantTo.setDtModDate(hrConsultant.getDtModDate());
        hrConsultantTo.setAuthBy(hrConsultant.getAuthBy());
        hrConsultantTo.setEnteredBy(hrConsultant.getEnteredBy());
        return hrConsultantTo;
    }

    public static HrDataPrevCompPK adaptToHrDataPrevCompPKEntity(HrDataPrevCompPKTO hrDataPrevCompTO) {
        HrDataPrevCompPK dataPrevcampPK = new HrDataPrevCompPK();
        dataPrevcampPK.setCompCode(hrDataPrevCompTO.getCompCode());
        dataPrevcampPK.setAdvtCode(hrDataPrevCompTO.getAdvtCode());
        dataPrevcampPK.setJobCode(hrDataPrevCompTO.getJobCode());
        dataPrevcampPK.setCandSrno(hrDataPrevCompTO.getCandSrno());
        dataPrevcampPK.setCompName(hrDataPrevCompTO.getCompName());
        return dataPrevcampPK;
    }

    public static HrDataPrevCompPKTO adaptToHrDataPrevCompPKTO(HrDataPrevCompPK hrDataPrevCompPK) {
        HrDataPrevCompPKTO hrDataPrevCompPKTO = new HrDataPrevCompPKTO();
        hrDataPrevCompPKTO.setCompCode(hrDataPrevCompPK.getCompCode());
        hrDataPrevCompPKTO.setAdvtCode(hrDataPrevCompPK.getAdvtCode());
        hrDataPrevCompPKTO.setJobCode(hrDataPrevCompPK.getJobCode());
        hrDataPrevCompPKTO.setCandSrno(hrDataPrevCompPK.getCandSrno());
        hrDataPrevCompPKTO.setCompName(hrDataPrevCompPK.getCompName());
        return hrDataPrevCompPKTO;
    }

    public static HrDataPrevComp adaptToHrDataPrevCompEntity(HrDataPrevCompTO hrDataPrevCompTO) {
        HrDataPrevComp hrDataPrevComp = new HrDataPrevComp();
        hrDataPrevComp.setHrDataPrevCompPK(ObjectAdaptorHr.adaptToHrDataPrevCompPKEntity(hrDataPrevCompTO.getHrDataPrevCompPKTO()));
        // hrDataPrevComp.setHrDatabank(ObjectAdaptorHr.adaptToHrDatabankEntity(hrDataPrevCompTO.getHrDatabankTO()));
        hrDataPrevComp.setAnnualTurn(hrDataPrevCompTO.getAnnualTurn());
        hrDataPrevComp.setLeaveDate(hrDataPrevCompTO.getLeaveDate());
        hrDataPrevComp.setCompAdd(hrDataPrevCompTO.getCompAdd());
        hrDataPrevComp.setDurFrom(hrDataPrevCompTO.getDurFrom());
        hrDataPrevComp.setDurTo(hrDataPrevCompTO.getDurTo());
        hrDataPrevComp.setDesgJoin(hrDataPrevCompTO.getDesgJoin());
        hrDataPrevComp.setDesgLeave(hrDataPrevCompTO.getDesgLeave());
        hrDataPrevComp.setTotEmp(hrDataPrevCompTO.getTotEmp());
        hrDataPrevComp.setEmpUnder(hrDataPrevCompTO.getEmpUnder());
        hrDataPrevComp.setSalJoin(hrDataPrevCompTO.getSalJoin());
        hrDataPrevComp.setSalLeave(hrDataPrevCompTO.getSalLeave());
        hrDataPrevComp.setReason(hrDataPrevCompTO.getReason());
        hrDataPrevComp.setDefaultCompCode(hrDataPrevCompTO.getDefaultCompCode());
        hrDataPrevComp.setStatFlag(hrDataPrevCompTO.getStatFlag());
        hrDataPrevComp.setStatUpFlag(hrDataPrevCompTO.getStatUpFlag());
        hrDataPrevComp.setModDate(hrDataPrevCompTO.getModDate());
        hrDataPrevComp.setAuthBy(hrDataPrevCompTO.getAuthBy());
        hrDataPrevComp.setEnteredBy(hrDataPrevCompTO.getEnteredBy());
        return hrDataPrevComp;
    }

    public static HrDataPrevCompTO adaptToHrDataPrevCompTO(HrDataPrevComp hrDataPrevComp) {
        HrDataPrevCompTO hrDataPrevCompTO = new HrDataPrevCompTO();
        hrDataPrevCompTO.setHrDataPrevCompPKTO(ObjectAdaptorHr.adaptToHrDataPrevCompPKTO(hrDataPrevComp.getHrDataPrevCompPK()));
        // hrDataPrevCompTO.setHrDatabankTO(ObjectAdaptorHr.adaptToHrDatabankTO(hrDataPrevComp.getHrDatabank()));
        hrDataPrevCompTO.setAnnualTurn(hrDataPrevComp.getAnnualTurn());
        hrDataPrevCompTO.setLeaveDate(hrDataPrevComp.getLeaveDate());
        hrDataPrevCompTO.setCompAdd(hrDataPrevComp.getCompAdd());
        hrDataPrevCompTO.setDurFrom(hrDataPrevComp.getDurFrom());
        hrDataPrevCompTO.setDurTo(hrDataPrevComp.getDurTo());
        hrDataPrevCompTO.setDesgJoin(hrDataPrevComp.getDesgJoin());
        hrDataPrevCompTO.setDesgLeave(hrDataPrevComp.getDesgLeave());
        hrDataPrevCompTO.setTotEmp(hrDataPrevComp.getTotEmp());
        hrDataPrevCompTO.setEmpUnder(hrDataPrevComp.getEmpUnder());
        hrDataPrevCompTO.setSalJoin(hrDataPrevComp.getSalJoin());
        hrDataPrevCompTO.setSalLeave(hrDataPrevComp.getSalLeave());
        hrDataPrevCompTO.setReason(hrDataPrevComp.getReason());
        hrDataPrevCompTO.setDefaultCompCode(hrDataPrevComp.getDefaultCompCode());
        hrDataPrevCompTO.setStatFlag(hrDataPrevComp.getStatFlag());
        hrDataPrevCompTO.setStatUpFlag(hrDataPrevComp.getStatUpFlag());
        hrDataPrevCompTO.setModDate(hrDataPrevComp.getModDate());
        hrDataPrevCompTO.setAuthBy(hrDataPrevComp.getAuthBy());
        hrDataPrevCompTO.setEnteredBy(hrDataPrevComp.getEnteredBy());
        return hrDataPrevCompTO;
    }

    public static HrDatabankPK adaptToHrDatabankPKEntity(HrDatabankPKTO hrDatabankPKTO) {
        HrDatabankPK hrDatabankPK = new HrDatabankPK();
        hrDatabankPK.setCompCode(hrDatabankPKTO.getCompCode());
        hrDatabankPK.setCandId(hrDatabankPKTO.getCandId());
        hrDatabankPK.setAdvtCode(hrDatabankPKTO.getAdvtCode());
        hrDatabankPK.setJobCode(hrDatabankPKTO.getJobCode());
        return hrDatabankPK;
    }

    public static HrDatabankPKTO adaptToHrDatabankPKTO(HrDatabankPK hrDatabankPK) {
        HrDatabankPKTO hrDatabankPKTO = new HrDatabankPKTO();
        hrDatabankPKTO.setCompCode(hrDatabankPK.getCompCode());
        hrDatabankPKTO.setCandId(hrDatabankPK.getCandId());
        hrDatabankPKTO.setAdvtCode(hrDatabankPK.getAdvtCode());
        hrDatabankPKTO.setJobCode(hrDatabankPK.getJobCode());
        return hrDatabankPKTO;
    }

    public static HrDatabank adaptToHrDatabankEntity(HrDatabankTO hrDatabankTO) {
        HrDatabank hrDatabank = new HrDatabank();
        hrDatabank.setHrDatabankPK(ObjectAdaptorHr.adaptToHrDatabankPKEntity(hrDatabankTO.getHrDatabankPKTO()));
        hrDatabank.setCandCode(hrDatabankTO.getCandCode());
        hrDatabank.setCandFirName(hrDatabankTO.getCandFirName());
        hrDatabank.setCandMidName(hrDatabankTO.getCandMidName());
        hrDatabank.setCandLastName(hrDatabankTO.getCandLastName());
        hrDatabank.setCandContAdd(hrDatabankTO.getCandContAdd());
        hrDatabank.setCandContPin(hrDatabankTO.getCandContPin());
        hrDatabank.setCandContCity(hrDatabankTO.getCandContCity());
        hrDatabank.setCandContState(hrDatabankTO.getCandContState());
        hrDatabank.setCandPermAdd(hrDatabankTO.getCandPermAdd());
        hrDatabank.setCandPermPin(hrDatabankTO.getCandPermPin());
        hrDatabank.setCandPermCity(hrDatabankTO.getCandPermCity());
        hrDatabank.setCandPermState(hrDatabankTO.getCandPermState());
        hrDatabank.setCandOffTel(hrDatabankTO.getCandOffTel());
        hrDatabank.setCandOffFax(hrDatabankTO.getCandOffFax());
        hrDatabank.setCandOffEmail(hrDatabankTO.getCandOffEmail());
        hrDatabank.setCandResTel(hrDatabankTO.getCandResTel());
        hrDatabank.setCandResFax(hrDatabankTO.getCandResFax());
        hrDatabank.setCandResEmail(hrDatabankTO.getCandResEmail());
        hrDatabank.setDtBirth(hrDatabankTO.getDtBirth());
        hrDatabank.setSex(hrDatabankTO.getSex());
        hrDatabank.setSpecialCode1(hrDatabankTO.getSpecialCode1());
        hrDatabank.setSpecialCode2(hrDatabankTO.getSpecialCode2());
        hrDatabank.setTotExpr(hrDatabankTO.getTotExpr());
        hrDatabank.setCurrExpr(hrDatabankTO.getCurrExpr());
        hrDatabank.setAutoExpr(hrDatabankTO.getAutoExpr());
        hrDatabank.setPostApplied1(hrDatabankTO.getPostApplied1());
        hrDatabank.setPostApplied2(hrDatabankTO.getPostApplied2());
        hrDatabank.setZone(hrDatabankTO.getZone());
        hrDatabank.setLocatCode(hrDatabankTO.getLocatCode());
        hrDatabank.setDesgCode(hrDatabankTO.getDesgCode());
        hrDatabank.setConsCode(hrDatabankTO.getConsCode());
        hrDatabank.setCurrSal(hrDatabankTO.getCurrSal());
        hrDatabank.setCurrDesg(hrDatabankTO.getCurrDesg());
        hrDatabank.setCurrCname(hrDatabankTO.getCurrCname());
        hrDatabank.setCompAdd1(hrDatabankTO.getCompAdd1());
        hrDatabank.setCompAdd2(hrDatabankTO.getCompAdd2());
        hrDatabank.setCompCity(hrDatabankTO.getCompCity());
        hrDatabank.setCompState(hrDatabankTO.getCompState());
        hrDatabank.setCompPin(hrDatabankTO.getCompPin());
        hrDatabank.setExpSal(hrDatabankTO.getExpSal());
        hrDatabank.setJoinSal(hrDatabankTO.getJoinSal());
        hrDatabank.setJoinDesg(hrDatabankTO.getJoinDesg());
        hrDatabank.setServiceLen(hrDatabankTO.getServiceLen());
        hrDatabank.setStatus(hrDatabankTO.getStatus());
        hrDatabank.setOrgType(hrDatabankTO.getOrgType());
        hrDatabank.setSkillCode(hrDatabankTO.getSkillCode());
        hrDatabank.setFreeDay(hrDatabankTO.getFreeDay());
        hrDatabank.setNation(hrDatabankTO.getNation());
        hrDatabank.setReligion(hrDatabankTO.getReligion());
        hrDatabank.setMarital(hrDatabankTO.getMarital());
        hrDatabank.setBloodGrp(hrDatabankTO.getBloodGrp());
        hrDatabank.setJoiningPeriod(hrDatabankTO.getJoiningPeriod());
        hrDatabank.setPassportNo(hrDatabankTO.getPassportNo());
        hrDatabank.setPassportDate(hrDatabankTO.getPassportDate());
        hrDatabank.setVisaDet(hrDatabankTO.getVisaDet());
        hrDatabank.setFathName(hrDatabankTO.getFathName());
        hrDatabank.setFatherOcc(hrDatabankTO.getFatherOcc());
        hrDatabank.setFatherDesig(hrDatabankTO.getFatherDesig());
        hrDatabank.setFatherOrg(hrDatabankTO.getFatherOrg());
        hrDatabank.setFatherPhone(hrDatabankTO.getFatherPhone());
        hrDatabank.setProfMember(hrDatabankTO.getProfMember());
        hrDatabank.setIndivMember(hrDatabankTO.getIndivMember());
        hrDatabank.setCallInt(hrDatabankTO.getCallInt());
        hrDatabank.setPrevEmp(hrDatabankTO.getPrevEmp());
        hrDatabank.setPrevEmpCode(hrDatabankTO.getPrevEmpCode());
        hrDatabank.setDurFrom(hrDatabankTO.getDurFrom());
        hrDatabank.setDurTo(hrDatabankTO.getDurTo());
        hrDatabank.setUnitName(hrDatabankTO.getUnitName());
        hrDatabank.setPrevDeptCode(hrDatabankTO.getPrevDeptCode());
        hrDatabank.setPrevDesgCode(hrDatabankTO.getPrevDesgCode());
        hrDatabank.setSalaryDrawn(hrDatabankTO.getSalaryDrawn());
        hrDatabank.setReasonLeave(hrDatabankTO.getReasonLeave());
        hrDatabank.setEvalFlag(hrDatabankTO.getEvalFlag());
        hrDatabank.setDataTransfer(hrDatabankTO.getDataTransfer());
        hrDatabank.setDefaultComp(hrDatabankTO.getDefaultComp());
        hrDatabank.setStatFlag(hrDatabankTO.getStatFlag());
        hrDatabank.setStatUpFlag(hrDatabankTO.getStatUpFlag());
        hrDatabank.setModDate(hrDatabankTO.getModDate());
        hrDatabank.setAuthBy(hrDatabankTO.getAuthBy());
        hrDatabank.setEnteredBy(hrDatabankTO.getEnteredBy());
        return hrDatabank;
    }

    public static HrDatabankTO adaptToHrDatabankTO(HrDatabank hrDatabank) {
        HrDatabankTO hrDatabankTO = new HrDatabankTO();
        hrDatabankTO.setHrDatabankPKTO(ObjectAdaptorHr.adaptToHrDatabankPKTO(hrDatabank.getHrDatabankPK()));
        hrDatabankTO.setCandCode(hrDatabank.getCandCode());
        hrDatabankTO.setCandFirName(hrDatabank.getCandFirName());
        hrDatabankTO.setCandMidName(hrDatabank.getCandMidName());
        hrDatabankTO.setCandLastName(hrDatabank.getCandLastName());
        hrDatabankTO.setCandContAdd(hrDatabank.getCandContAdd());
        hrDatabankTO.setCandContPin(hrDatabank.getCandContPin());
        hrDatabankTO.setCandContCity(hrDatabank.getCandContCity());
        hrDatabankTO.setCandContState(hrDatabank.getCandContState());
        hrDatabankTO.setCandPermAdd(hrDatabank.getCandPermAdd());
        hrDatabankTO.setCandPermPin(hrDatabank.getCandPermPin());
        hrDatabankTO.setCandPermCity(hrDatabank.getCandPermCity());
        hrDatabankTO.setCandPermState(hrDatabank.getCandPermState());
        hrDatabankTO.setCandOffTel(hrDatabank.getCandOffTel());
        hrDatabankTO.setCandOffFax(hrDatabank.getCandOffFax());
        hrDatabankTO.setCandOffEmail(hrDatabank.getCandOffEmail());
        hrDatabankTO.setCandResTel(hrDatabank.getCandResTel());
        hrDatabankTO.setCandResFax(hrDatabank.getCandResFax());
        hrDatabankTO.setCandResEmail(hrDatabank.getCandResEmail());
        hrDatabankTO.setDtBirth(hrDatabank.getDtBirth());
        hrDatabankTO.setSex(hrDatabank.getSex());
        hrDatabankTO.setSpecialCode1(hrDatabank.getSpecialCode1());
        hrDatabankTO.setSpecialCode2(hrDatabank.getSpecialCode2());
        hrDatabankTO.setTotExpr(hrDatabank.getTotExpr());
        hrDatabankTO.setCurrExpr(hrDatabank.getCurrExpr());
        hrDatabankTO.setAutoExpr(hrDatabank.getAutoExpr());
        hrDatabankTO.setPostApplied1(hrDatabank.getPostApplied1());
        hrDatabankTO.setPostApplied2(hrDatabank.getPostApplied2());
        hrDatabankTO.setZone(hrDatabank.getZone());
        hrDatabankTO.setLocatCode(hrDatabank.getLocatCode());
        hrDatabankTO.setDesgCode(hrDatabank.getDesgCode());
        hrDatabankTO.setConsCode(hrDatabank.getConsCode());
        hrDatabankTO.setCurrSal(hrDatabank.getCurrSal());
        hrDatabankTO.setCurrDesg(hrDatabank.getCurrDesg());
        hrDatabankTO.setCurrCname(hrDatabank.getCurrCname());
        hrDatabankTO.setCompAdd1(hrDatabank.getCompAdd1());
        hrDatabankTO.setCompAdd2(hrDatabank.getCompAdd2());
        hrDatabankTO.setCompCity(hrDatabank.getCompCity());
        hrDatabankTO.setCompState(hrDatabank.getCompState());
        hrDatabankTO.setCompPin(hrDatabank.getCompPin());
        hrDatabankTO.setExpSal(hrDatabank.getExpSal());
        hrDatabankTO.setJoinSal(hrDatabank.getJoinSal());
        hrDatabankTO.setJoinDesg(hrDatabank.getJoinDesg());
        hrDatabankTO.setServiceLen(hrDatabank.getServiceLen());
        hrDatabankTO.setStatus(hrDatabank.getStatus());
        hrDatabankTO.setOrgType(hrDatabank.getOrgType());
        hrDatabankTO.setSkillCode(hrDatabank.getSkillCode());
        hrDatabankTO.setFreeDay(hrDatabank.getFreeDay());
        hrDatabankTO.setNation(hrDatabank.getNation());
        hrDatabankTO.setReligion(hrDatabank.getReligion());
        hrDatabankTO.setMarital(hrDatabank.getMarital());
        hrDatabankTO.setBloodGrp(hrDatabank.getBloodGrp());
        hrDatabankTO.setJoiningPeriod(hrDatabank.getJoiningPeriod());
        hrDatabankTO.setPassportNo(hrDatabank.getPassportNo());
        hrDatabankTO.setPassportDate(hrDatabank.getPassportDate());
        hrDatabankTO.setVisaDet(hrDatabank.getVisaDet());
        hrDatabankTO.setFathName(hrDatabank.getFathName());
        hrDatabankTO.setFatherOcc(hrDatabank.getFatherOcc());
        hrDatabankTO.setFatherDesig(hrDatabank.getFatherDesig());
        hrDatabankTO.setFatherOrg(hrDatabank.getFatherOrg());
        hrDatabankTO.setFatherPhone(hrDatabank.getFatherPhone());
        hrDatabankTO.setProfMember(hrDatabank.getProfMember());
        hrDatabankTO.setIndivMember(hrDatabank.getIndivMember());
        hrDatabankTO.setCallInt(hrDatabank.getCallInt());
        hrDatabankTO.setPrevEmp(hrDatabank.getPrevEmp());
        hrDatabankTO.setPrevEmpCode(hrDatabank.getPrevEmpCode());
        hrDatabankTO.setDurFrom(hrDatabank.getDurFrom());
        hrDatabankTO.setDurTo(hrDatabank.getDurTo());
        hrDatabankTO.setUnitName(hrDatabank.getUnitName());
        hrDatabankTO.setPrevDeptCode(hrDatabank.getPrevDeptCode());
        hrDatabankTO.setPrevDesgCode(hrDatabank.getPrevDesgCode());
        hrDatabankTO.setSalaryDrawn(hrDatabank.getSalaryDrawn());
        hrDatabankTO.setReasonLeave(hrDatabank.getReasonLeave());
        hrDatabankTO.setEvalFlag(hrDatabank.getEvalFlag());
        hrDatabankTO.setDataTransfer(hrDatabank.getDataTransfer());
        hrDatabankTO.setDefaultComp(hrDatabank.getDefaultComp());
        hrDatabankTO.setStatFlag(hrDatabank.getStatFlag());
        hrDatabankTO.setStatUpFlag(hrDatabank.getStatUpFlag());
        hrDatabankTO.setModDate(hrDatabank.getModDate());
        hrDatabankTO.setAuthBy(hrDatabank.getAuthBy());
        hrDatabankTO.setEnteredBy(hrDatabank.getEnteredBy());
        return hrDatabankTO;
    }

    public static HrDataQualPK adaptToHrDataQualPKEntity(HrDataQualPKTO hrDataQualPKTO) {
        HrDataQualPK hrDataQualPK = new HrDataQualPK();
        hrDataQualPK.setCompCode(hrDataQualPKTO.getCompCode());
        hrDataQualPK.setAdvtCode(hrDataQualPKTO.getAdvtCode());
        hrDataQualPK.setJobCode(hrDataQualPKTO.getJobCode());
        hrDataQualPK.setCandSrno(hrDataQualPKTO.getCandSrno());
        hrDataQualPK.setQualCode(hrDataQualPKTO.getQualCode());
        return hrDataQualPK;
    }

    public static HrDataQualPKTO adaptToHrDataQualPKTO(HrDataQualPK hrDataQualPK) {
        HrDataQualPKTO hrDataQualPKTO = new HrDataQualPKTO();
        hrDataQualPKTO.setCompCode(hrDataQualPK.getCompCode());
        hrDataQualPKTO.setAdvtCode(hrDataQualPK.getAdvtCode());
        hrDataQualPKTO.setJobCode(hrDataQualPK.getJobCode());
        hrDataQualPKTO.setCandSrno(hrDataQualPK.getCandSrno());
        hrDataQualPKTO.setQualCode(hrDataQualPK.getQualCode());
        return hrDataQualPKTO;
    }

    public static HrDataQual adaptToHrDataQualEntity(HrDataQualTO hrDataQualTO) {
        HrDataQual hrDataQual = new HrDataQual();
        hrDataQual.setHrDataQualPK(ObjectAdaptorHr.adaptToHrDataQualPKEntity(hrDataQualTO.getHrDataQualPKTO()));
        //hrDataQual.setHrDatabank(ObjectAdaptorHr.adaptToHrDatabankEntity(hrDataQualTO.getHrDatabankTO()));
        //hrDataQual.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrDataQualTO.getHrMstStructTO()));
        hrDataQual.setInstName(hrDataQualTO.getInstName());
        hrDataQual.setYear(hrDataQualTO.getYear());
        hrDataQual.setSubName(hrDataQualTO.getSubName());
        hrDataQual.setSpecial(hrDataQualTO.getSpecial());
        hrDataQual.setPerMarks(hrDataQualTO.getPerMarks());
        hrDataQual.setDiv(hrDataQualTO.getDiv());
        hrDataQual.setDefaultCompCode(hrDataQualTO.getDefaultCompCode());
        hrDataQual.setStatFlag(hrDataQualTO.getStatFlag());
        hrDataQual.setStatUpFlag(hrDataQualTO.getStatUpFlag());
        hrDataQual.setModDate(hrDataQualTO.getModDate());
        hrDataQual.setAuthBy(hrDataQualTO.getAuthBy());
        hrDataQual.setEnteredBy(hrDataQualTO.getEnteredBy());
        return hrDataQual;
    }

    public static HrDataQualTO adaptToHrDataQualTO(HrDataQual hrDataQual) {
        HrDataQualTO hrDataQualTO = new HrDataQualTO();
        hrDataQualTO.setHrDataQualPKTO(ObjectAdaptorHr.adaptToHrDataQualPKTO(hrDataQual.getHrDataQualPK()));
        // hrDataQualTO.setHrDatabankTO(ObjectAdaptorHr.adaptToHrDatabankTO(hrDataQual.getHrDatabank()));
        //  hrDataQualTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrDataQual.getHrMstStruct()));
        hrDataQualTO.setInstName(hrDataQual.getInstName());
        hrDataQualTO.setYear(hrDataQual.getYear());
        hrDataQualTO.setSubName(hrDataQual.getSubName());
        hrDataQualTO.setSpecial(hrDataQual.getSpecial());
        hrDataQualTO.setPerMarks(hrDataQual.getPerMarks());
        hrDataQualTO.setDiv(hrDataQual.getDiv());
        hrDataQualTO.setDefaultCompCode(hrDataQual.getDefaultCompCode());
        hrDataQualTO.setStatFlag(hrDataQual.getStatFlag());
        hrDataQualTO.setStatUpFlag(hrDataQual.getStatUpFlag());
        hrDataQualTO.setModDate(hrDataQual.getModDate());
        hrDataQualTO.setAuthBy(hrDataQual.getAuthBy());
        hrDataQualTO.setEnteredBy(hrDataQual.getEnteredBy());
        return hrDataQualTO;
    }

    public static HrDataReferencePK adaptToHrDataReferencePKEntity(HrDataReferencePKTO hrDataReferencePKTO) {
        HrDataReferencePK hrDataReferencePK = new HrDataReferencePK();
        hrDataReferencePK.setCompCode(hrDataReferencePKTO.getCompCode());
        hrDataReferencePK.setAdvtCode(hrDataReferencePKTO.getAdvtCode());
        hrDataReferencePK.setJobCode(hrDataReferencePKTO.getJobCode());
        hrDataReferencePK.setCandSrno(hrDataReferencePKTO.getCandSrno());
        hrDataReferencePK.setReferName(hrDataReferencePKTO.getReferName());
        return hrDataReferencePK;
    }

    public static HrDataReferencePKTO adaptToHrDataReferencePKTO(HrDataReferencePK hrDataReferencePK) {
        HrDataReferencePKTO hrDataReferencePKTO = new HrDataReferencePKTO();
        hrDataReferencePKTO.setCompCode(hrDataReferencePK.getCompCode());
        hrDataReferencePKTO.setAdvtCode(hrDataReferencePK.getAdvtCode());
        hrDataReferencePKTO.setJobCode(hrDataReferencePK.getJobCode());
        hrDataReferencePKTO.setCandSrno(hrDataReferencePK.getCandSrno());
        hrDataReferencePKTO.setReferName(hrDataReferencePK.getReferName());
        return hrDataReferencePKTO;
    }

    public static HrDataReference adaptToHrDataReferenceEntity(HrDataReferenceTO hrDataReferenceTO) {
        HrDataReference hrDataReference = new HrDataReference();
        hrDataReference.setHrDataReferencePK(ObjectAdaptorHr.adaptToHrDataReferencePKEntity(hrDataReferenceTO.getHrDataReferencePKTO()));
        //hrDataReference.setHrDatabank(ObjectAdaptorHr.adaptToHrDatabankEntity(hrDataReferenceTO.getHrDatabankTO()));
        hrDataReference.setReferAdd(hrDataReferenceTO.getReferAdd());
        hrDataReference.setReferPin(hrDataReferenceTO.getReferPin());
        hrDataReference.setReferState(hrDataReferenceTO.getReferState());
        hrDataReference.setReferCity(hrDataReferenceTO.getReferCity());
        hrDataReference.setReferPhone(hrDataReferenceTO.getReferPhone());
        hrDataReference.setReferOcc(hrDataReferenceTO.getReferOcc());
        hrDataReference.setDefaultCompCode(hrDataReferenceTO.getDefaultCompCode());
        hrDataReference.setStatFlag(hrDataReferenceTO.getStatFlag());
        hrDataReference.setStatUpFlag(hrDataReferenceTO.getStatUpFlag());
        hrDataReference.setModDate(hrDataReferenceTO.getModDate());
        hrDataReference.setAuthBy(hrDataReferenceTO.getAuthBy());
        hrDataReference.setEnteredBy(hrDataReferenceTO.getEnteredBy());
        return hrDataReference;
    }

    public static HrDataReferenceTO adaptToHrDataReferenceTO(HrDataReference hrDataReference) {
        HrDataReferenceTO hrDataReferenceTO = new HrDataReferenceTO();
        hrDataReferenceTO.setHrDataReferencePKTO(ObjectAdaptorHr.adaptToHrDataReferencePKTO(hrDataReference.getHrDataReferencePK()));
        // hrDataReferenceTO.setHrDatabankTO(ObjectAdaptorHr.adaptToHrDatabankTO(hrDataReference.getHrDatabank()));
        hrDataReferenceTO.setReferAdd(hrDataReference.getReferAdd());
        hrDataReferenceTO.setReferPin(hrDataReference.getReferPin());
        hrDataReferenceTO.setReferState(hrDataReference.getReferState());
        hrDataReferenceTO.setReferCity(hrDataReference.getReferCity());
        hrDataReferenceTO.setReferPhone(hrDataReference.getReferPhone());
        hrDataReferenceTO.setReferOcc(hrDataReference.getReferOcc());
        hrDataReferenceTO.setDefaultCompCode(hrDataReference.getDefaultCompCode());
        hrDataReferenceTO.setStatFlag(hrDataReference.getStatFlag());
        hrDataReferenceTO.setStatUpFlag(hrDataReference.getStatUpFlag());
        hrDataReferenceTO.setModDate(hrDataReference.getModDate());
        hrDataReferenceTO.setAuthBy(hrDataReference.getAuthBy());
        hrDataReferenceTO.setEnteredBy(hrDataReference.getEnteredBy());
        return hrDataReferenceTO;
    }

    public static HrMstProgramPK adaptToHrMstProgramPKEntity(HrMstProgramPKTO hrMstProgramPKTO) {
        HrMstProgramPK hrMstProgramPK = new HrMstProgramPK();
        hrMstProgramPK.setCompCode(hrMstProgramPKTO.getCompCode());
        hrMstProgramPK.setProgCode(hrMstProgramPKTO.getProgCode());
        return hrMstProgramPK;
    }

    public static HrMstProgramPKTO adaptToHrMstProgramPKTO(HrMstProgramPK hrMstProgramPK) {
        HrMstProgramPKTO hrMstProgramPKTO = new HrMstProgramPKTO();
        hrMstProgramPKTO.setCompCode(hrMstProgramPK.getCompCode());
        hrMstProgramPKTO.setProgCode(hrMstProgramPK.getProgCode());
        return hrMstProgramPKTO;
    }

    public static HrMstProgram adaptToHrMstProgramEntity(HrMstProgramTO hrMstProgramTO) {
        HrMstProgram hrMstProgram = new HrMstProgram();
        hrMstProgram.setHrMstProgramPK(ObjectAdaptorHr.adaptToHrMstProgramPKEntity(hrMstProgramTO.getHrMstProgramPKTO()));
        hrMstProgram.setProgName(hrMstProgramTO.getProgName());
        hrMstProgram.setInstCode(hrMstProgramTO.getInstCode());
        hrMstProgram.setTrngFrom(hrMstProgramTO.getTrngFrom());
        hrMstProgram.setTrngTo(hrMstProgramTO.getTrngTo());
        hrMstProgram.setInextHouse(hrMstProgramTO.getInextHouse());
        hrMstProgram.setTrngCost(hrMstProgramTO.getTrngCost());
        hrMstProgram.setFacuName(hrMstProgramTO.getFacuName());
        hrMstProgram.setDefaultComp(hrMstProgramTO.getDefaultComp());
        hrMstProgram.setStatFlag(hrMstProgramTO.getStatFlag());
        hrMstProgram.setStatUpFlag(hrMstProgramTO.getStatUpFlag());
        hrMstProgram.setModDate(hrMstProgramTO.getModDate());
        hrMstProgram.setEnteredBy(hrMstProgramTO.getEnteredBy());
        hrMstProgram.setAuthBy(hrMstProgramTO.getAuthBy());
        return hrMstProgram;
    }

    public static HrMstProgramTO adaptToHrMstProgramTO(HrMstProgram hrMstProgram) {
        HrMstProgramTO hrMstProgramTO = new HrMstProgramTO();
        hrMstProgramTO.setHrMstProgramPKTO(ObjectAdaptorHr.adaptToHrMstProgramPKTO(hrMstProgram.getHrMstProgramPK()));
        hrMstProgramTO.setProgName(hrMstProgram.getProgName());
        hrMstProgramTO.setInstCode(hrMstProgram.getInstCode());
        hrMstProgramTO.setTrngFrom(hrMstProgram.getTrngFrom());
        hrMstProgramTO.setTrngTo(hrMstProgram.getTrngTo());
        hrMstProgramTO.setInextHouse(hrMstProgram.getInextHouse());
        hrMstProgramTO.setTrngCost(hrMstProgram.getTrngCost());
        hrMstProgramTO.setFacuName(hrMstProgram.getFacuName());
        hrMstProgramTO.setDefaultComp(hrMstProgram.getDefaultComp());
        hrMstProgramTO.setStatFlag(hrMstProgram.getStatFlag());
        hrMstProgramTO.setStatUpFlag(hrMstProgram.getStatUpFlag());
        hrMstProgramTO.setModDate(hrMstProgram.getModDate());
        hrMstProgramTO.setEnteredBy(hrMstProgram.getEnteredBy());
        hrMstProgramTO.setAuthBy(hrMstProgram.getAuthBy());
        return hrMstProgramTO;
    }

    public static HrDetailsProgramPK adaptToHrDetailsProgramPKEntity(HrDetailsProgramPKTO hrDetailsProgramPKTO) {
        HrDetailsProgramPK hrDetailsProgramPK = new HrDetailsProgramPK();
        hrDetailsProgramPK.setCompCode(hrDetailsProgramPKTO.getCompCode());
        hrDetailsProgramPK.setProgCode(hrDetailsProgramPKTO.getProgCode());
        hrDetailsProgramPK.setSkillCode(hrDetailsProgramPKTO.getSkillCode());
        return hrDetailsProgramPK;
    }

    public static HrDetailsProgramPKTO adaptToHrDetailsProgramPKTO(HrDetailsProgramPK hrDetailsProgramPK) {
        HrDetailsProgramPKTO hrDetailsProgramPKTO = new HrDetailsProgramPKTO();
        hrDetailsProgramPKTO.setCompCode(hrDetailsProgramPK.getCompCode());
        hrDetailsProgramPKTO.setProgCode(hrDetailsProgramPK.getProgCode());
        hrDetailsProgramPKTO.setSkillCode(hrDetailsProgramPK.getSkillCode());
        return hrDetailsProgramPKTO;
    }

    public static HrDetailsProgram adaptToHrDetailsProgramEntity(HrDetailsProgramTO hrDetailsProgramTO) {
        HrDetailsProgram hrDetailsProgram = new HrDetailsProgram();
        hrDetailsProgram.setHrDetailsProgramPK(ObjectAdaptorHr.adaptToHrDetailsProgramPKEntity(hrDetailsProgramTO.getHrDetailsProgramPKTO()));
        // hrDetailsProgram.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrDetailsProgramTO.getHrMstStructTO()));
        //  hrDetailsProgram.setHrMstProgram(ObjectAdaptorHr.adaptToHrMstProgramEntity(hrDetailsProgramTO.getHrMstProgramTO()));
        hrDetailsProgram.setDefaultComp(hrDetailsProgramTO.getDefaultComp());
        hrDetailsProgram.setStatFlag(hrDetailsProgramTO.getStatFlag());
        hrDetailsProgram.setStatUpFlag(hrDetailsProgramTO.getStatUpFlag());
        hrDetailsProgram.setModDate(hrDetailsProgramTO.getModDate());
        hrDetailsProgram.setAuthBy(hrDetailsProgramTO.getAuthBy());
        hrDetailsProgram.setEnteredBy(hrDetailsProgramTO.getEnteredBy());
        return hrDetailsProgram;
    }

    public static HrDetailsProgramTO adaptToHrDetailsProgramTO(HrDetailsProgram hrDetailsProgram) {
        HrDetailsProgramTO hrDetailsProgramTO = new HrDetailsProgramTO();
        hrDetailsProgramTO.setHrDetailsProgramPKTO(ObjectAdaptorHr.adaptToHrDetailsProgramPKTO(hrDetailsProgram.getHrDetailsProgramPK()));
        // hrDetailsProgramTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrDetailsProgram.getHrMstStruct()));
        // hrDetailsProgramTO.setHrMstProgramTO(ObjectAdaptorHr.adaptToHrMstProgramTO(hrDetailsProgram.getHrMstProgram()));
        hrDetailsProgramTO.setDefaultComp(hrDetailsProgram.getDefaultComp());
        hrDetailsProgramTO.setStatFlag(hrDetailsProgram.getStatFlag());
        hrDetailsProgramTO.setStatUpFlag(hrDetailsProgram.getStatUpFlag());
        hrDetailsProgramTO.setModDate(hrDetailsProgram.getModDate());
        hrDetailsProgramTO.setAuthBy(hrDetailsProgram.getAuthBy());
        hrDetailsProgramTO.setEnteredBy(hrDetailsProgram.getEnteredBy());
        return hrDetailsProgramTO;
    }

    public static HrDirectRecPK adaptToHrDirectRecPKEntity(HrDirectRecPKTO hrDirectRecPKTO) {
        HrDirectRecPK hrDirectRecPK = new HrDirectRecPK();
        hrDirectRecPK.setCompCode(hrDirectRecPKTO.getCompCode());
        hrDirectRecPK.setArno(hrDirectRecPKTO.getArno());
        return hrDirectRecPK;
    }

    public static HrDirectRecPKTO adaptToHrDirectRecPKTO(HrDirectRecPK hrDirectRecPK) {
        HrDirectRecPKTO hrDirectRecPKTO = new HrDirectRecPKTO();
        hrDirectRecPKTO.setCompCode(hrDirectRecPK.getCompCode());
        hrDirectRecPKTO.setArno(hrDirectRecPK.getArno());
        return hrDirectRecPKTO;
    }

    public static HrDirectRec adaptToHrDirectRecEntity(HrDirectRecTO hrDirectRecTO) {
        HrDirectRec hrDirectRec = new HrDirectRec();
        hrDirectRec.setHrDirectRecPK(ObjectAdaptorHr.adaptToHrDirectRecPKEntity(hrDirectRecTO.getHrDirectRecPKTO()));
        hrDirectRec.setArdate(hrDirectRecTO.getArdate());
        hrDirectRec.setZoneCode(hrDirectRecTO.getZoneCode());
        hrDirectRec.setDesigCode(hrDirectRecTO.getDesigCode());
        hrDirectRec.setLocationCode(hrDirectRecTO.getLocationCode());
        hrDirectRec.setCandCode(hrDirectRecTO.getCandCode());
        hrDirectRec.setCandName(hrDirectRecTO.getCandName());
        hrDirectRec.setFatherName(hrDirectRecTO.getFatherName());
        hrDirectRec.setAppointmentDate(hrDirectRecTO.getAppointmentDate());
        hrDirectRec.setContactNo(hrDirectRecTO.getContactNo());
        hrDirectRec.setBasicSalary(hrDirectRecTO.getBasicSalary());
        hrDirectRec.setHra(hrDirectRecTO.getHra());
        hrDirectRec.setTa(hrDirectRecTO.getTa());
        hrDirectRec.setMedicalAllw(hrDirectRecTO.getMedicalAllw());
        hrDirectRec.setTotal(hrDirectRecTO.getTotal());
        hrDirectRec.setAddress(hrDirectRecTO.getAddress());
        hrDirectRec.setCity(hrDirectRecTO.getCity());
        hrDirectRec.setState(hrDirectRecTO.getState());
        hrDirectRec.setPin(hrDirectRecTO.getPin());
        hrDirectRec.setEmailId(hrDirectRecTO.getEmailId());
        hrDirectRec.setJobStatus(hrDirectRecTO.getJobStatus());
        hrDirectRec.setRemarks(hrDirectRecTO.getRemarks());
        hrDirectRec.setEffectiveDate(hrDirectRecTO.getEffectiveDate());
        hrDirectRec.setQualCode(hrDirectRecTO.getQualCode());
        hrDirectRec.setSuperId(hrDirectRecTO.getSuperId());
        hrDirectRec.setInitiatorId(hrDirectRecTO.getInitiatorId());
        hrDirectRec.setDeptHeadId(hrDirectRecTO.getDeptHeadId());
        hrDirectRec.setHrdApproval(hrDirectRecTO.getHrdApproval());
        hrDirectRec.setMdApproval(hrDirectRecTO.getMdApproval());
        hrDirectRec.setStatFlag(hrDirectRecTO.getStatFlag());
        hrDirectRec.setStatUpFlag(hrDirectRecTO.getStatUpFlag());
        hrDirectRec.setModDate(hrDirectRecTO.getModDate());
        hrDirectRec.setDefaultComp(hrDirectRecTO.getDefaultComp());
        hrDirectRec.setAuthBy(hrDirectRecTO.getAuthBy());
        hrDirectRec.setEnteredBy(hrDirectRecTO.getEnteredBy());
        return hrDirectRec;
    }

    public static HrDirectRecTO adaptToHrDirectRecTO(HrDirectRec hrDirectRec) {
        HrDirectRecTO hrDirectRecTO = new HrDirectRecTO();
        hrDirectRecTO.setHrDirectRecPKTO(ObjectAdaptorHr.adaptToHrDirectRecPKTO(hrDirectRec.getHrDirectRecPK()));
        hrDirectRecTO.setArdate(hrDirectRec.getArdate());
        hrDirectRecTO.setZoneCode(hrDirectRec.getZoneCode());
        hrDirectRecTO.setDesigCode(hrDirectRec.getDesigCode());
        hrDirectRecTO.setLocationCode(hrDirectRec.getLocationCode());
        hrDirectRecTO.setCandCode(hrDirectRec.getCandCode());
        hrDirectRecTO.setCandName(hrDirectRec.getCandName());
        hrDirectRecTO.setFatherName(hrDirectRec.getFatherName());
        hrDirectRecTO.setAppointmentDate(hrDirectRec.getAppointmentDate());
        hrDirectRecTO.setContactNo(hrDirectRec.getContactNo());
        hrDirectRecTO.setBasicSalary(hrDirectRec.getBasicSalary());
        hrDirectRecTO.setHra(hrDirectRec.getHra());
        hrDirectRecTO.setTa(hrDirectRec.getTa());
        hrDirectRecTO.setMedicalAllw(hrDirectRec.getMedicalAllw());
        hrDirectRecTO.setTotal(hrDirectRec.getTotal());
        hrDirectRecTO.setAddress(hrDirectRec.getAddress());
        hrDirectRecTO.setCity(hrDirectRec.getCity());
        hrDirectRecTO.setState(hrDirectRec.getState());
        hrDirectRecTO.setPin(hrDirectRec.getPin());
        hrDirectRecTO.setEmailId(hrDirectRec.getEmailId());
        hrDirectRecTO.setJobStatus(hrDirectRec.getJobStatus());
        hrDirectRecTO.setRemarks(hrDirectRec.getRemarks());
        hrDirectRecTO.setEffectiveDate(hrDirectRec.getEffectiveDate());
        hrDirectRecTO.setQualCode(hrDirectRec.getQualCode());
        hrDirectRecTO.setSuperId(hrDirectRec.getSuperId());
        hrDirectRecTO.setInitiatorId(hrDirectRec.getInitiatorId());
        hrDirectRecTO.setDeptHeadId(hrDirectRec.getDeptHeadId());
        hrDirectRecTO.setHrdApproval(hrDirectRec.getHrdApproval());
        hrDirectRecTO.setMdApproval(hrDirectRec.getMdApproval());
        hrDirectRecTO.setStatFlag(hrDirectRec.getStatFlag());
        hrDirectRecTO.setStatUpFlag(hrDirectRec.getStatUpFlag());
        hrDirectRecTO.setModDate(hrDirectRec.getModDate());
        hrDirectRecTO.setDefaultComp(hrDirectRec.getDefaultComp());
        hrDirectRecTO.setAuthBy(hrDirectRec.getAuthBy());
        hrDirectRecTO.setEnteredBy(hrDirectRec.getEnteredBy());
        return hrDirectRecTO;
    }

    public static HrPersonnelDetailsPK adaptToHrPeronnelDetailPKEntity(HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO) {
        HrPersonnelDetailsPK hrPersonnelDetailsPK = new HrPersonnelDetailsPK();
        hrPersonnelDetailsPK.setCompCode(hrPersonnelDetailsPKTO.getCompCode());
        hrPersonnelDetailsPK.setEmpCode(BigInteger.valueOf(hrPersonnelDetailsPKTO.getEmpCode()));
        
        return hrPersonnelDetailsPK;
    }

    public static HrPersonnelDetailsPKTO adaptToHrPeronnelDetailPKTO(HrPersonnelDetailsPK hrPersonnelDetailsPK) {
        HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
        hrPersonnelDetailsPKTO.setCompCode(hrPersonnelDetailsPK.getCompCode());
        hrPersonnelDetailsPKTO.setEmpCode(hrPersonnelDetailsPK.getEmpCode().longValue());
        return hrPersonnelDetailsPKTO;
    }
     static Date date = new  Date();
    public static HrPersonnelDetails adaptTOHrPersonnelDetailsEntity(HrPersonnelDetailsTO hrPersonnelDetailsTO) {
        HrPersonnelDetails hrPersonnelDetails = new HrPersonnelDetails();
        hrPersonnelDetails.setHrPersonnelDetailsPK(ObjectAdaptorHr.adaptToHrPeronnelDetailPKEntity(hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO()));
        hrPersonnelDetails.setEmpId(hrPersonnelDetailsTO.getEmpId());
        hrPersonnelDetails.setEmpCardNo(hrPersonnelDetailsTO.getEmpCardNo());
        hrPersonnelDetails.setEmpName(hrPersonnelDetailsTO.getEmpName());
        hrPersonnelDetails.setBlock(hrPersonnelDetailsTO.getBlock());
        hrPersonnelDetails.setUnitName(hrPersonnelDetailsTO.getUnitName());
        hrPersonnelDetails.setEmpFirName(hrPersonnelDetailsTO.getEmpFirName());
        hrPersonnelDetails.setEmpMidName(hrPersonnelDetailsTO.getEmpMidName());
        hrPersonnelDetails.setEmpLastName(hrPersonnelDetailsTO.getEmpLastName());
        hrPersonnelDetails.setEmpContAdd(hrPersonnelDetailsTO.getEmpContAdd());
        hrPersonnelDetails.setEmpContPin(hrPersonnelDetailsTO.getEmpContPin());
        hrPersonnelDetails.setEmpContCity(hrPersonnelDetailsTO.getEmpContCity());
        hrPersonnelDetails.setEmpContState(hrPersonnelDetailsTO.getEmpContState());
        hrPersonnelDetails.setEmpContTel(hrPersonnelDetailsTO.getEmpContTel());
        hrPersonnelDetails.setEmpPermAdd(hrPersonnelDetailsTO.getEmpPermAdd());
        hrPersonnelDetails.setEmpPermPin(hrPersonnelDetailsTO.getEmpPermPin());
        hrPersonnelDetails.setEmpPermCity(hrPersonnelDetailsTO.getEmpPermCity());
        hrPersonnelDetails.setEmpPermState(hrPersonnelDetailsTO.getEmpPermState());
        hrPersonnelDetails.setEmpPermTel(hrPersonnelDetailsTO.getEmpPermTel());
        hrPersonnelDetails.setBirthDate(hrPersonnelDetailsTO.getBirthDate());
        hrPersonnelDetails.setSex(hrPersonnelDetailsTO.getSex());
        hrPersonnelDetails.setEmpType(hrPersonnelDetailsTO.getEmpType());
        hrPersonnelDetails.setFathHusName(hrPersonnelDetailsTO.getFathHusName());
        hrPersonnelDetails.setFatherHusOcc(hrPersonnelDetailsTO.getFatherHusOcc());
        hrPersonnelDetails.setFatherHusDesig(hrPersonnelDetailsTO.getFatherHusDesig());
        hrPersonnelDetails.setFatherHusOrg(hrPersonnelDetailsTO.getFatherHusOrg());
        hrPersonnelDetails.setFatherHusPhone(hrPersonnelDetailsTO.getFatherHusPhone());
        hrPersonnelDetails.setTotExpr(hrPersonnelDetailsTO.getTotExpr() == null ? null : hrPersonnelDetailsTO.getTotExpr().floatValue());
        hrPersonnelDetails.setAutoExpr(hrPersonnelDetailsTO.getAutoExpr() == null ? null : hrPersonnelDetailsTO.getAutoExpr().floatValue());
        hrPersonnelDetails.setZones(hrPersonnelDetailsTO.getZones());
        hrPersonnelDetails.setLocatCode(hrPersonnelDetailsTO.getLocatCode());
        hrPersonnelDetails.setDeptCode(hrPersonnelDetailsTO.getDeptCode());
        hrPersonnelDetails.setGradeCode(hrPersonnelDetailsTO.getGradeCode());
        hrPersonnelDetails.setDesgCode(hrPersonnelDetailsTO.getDesgCode());
        hrPersonnelDetails.setCatgCode(hrPersonnelDetailsTO.getCatgCode());
        hrPersonnelDetails.setSubdeptCode(hrPersonnelDetailsTO.getSubdeptCode());
        hrPersonnelDetails.setTravOverseasStatus(hrPersonnelDetailsTO.getTravOverseasStatus());
        hrPersonnelDetails.setHeight(hrPersonnelDetailsTO.getHeight());
        hrPersonnelDetails.setNation(hrPersonnelDetailsTO.getNation());
        hrPersonnelDetails.setWeight(hrPersonnelDetailsTO.getWeight());
        hrPersonnelDetails.setChest(hrPersonnelDetailsTO.getChest());
        hrPersonnelDetails.setHealth(hrPersonnelDetailsTO.getHealth());
        hrPersonnelDetails.setReligion(hrPersonnelDetailsTO.getReligion());
        hrPersonnelDetails.setMaritalStatus(hrPersonnelDetailsTO.getMaritalStatus());
        hrPersonnelDetails.setBloodGrp(hrPersonnelDetailsTO.getBloodGrp());
        hrPersonnelDetails.setBirthCity(hrPersonnelDetailsTO.getBirthCity());
        hrPersonnelDetails.setBirthState(hrPersonnelDetailsTO.getBirthState());
        hrPersonnelDetails.setWeddingDate(hrPersonnelDetailsTO.getWeddingDate());
        hrPersonnelDetails.setJoinDate(hrPersonnelDetailsTO.getJoinDate());
        hrPersonnelDetails.setPayrollDate(hrPersonnelDetailsTO.getPayrollDate());
        hrPersonnelDetails.setPayMode(hrPersonnelDetailsTO.getPayMode());
        hrPersonnelDetails.setOtEligibility(hrPersonnelDetailsTO.getOtEligibility());
        hrPersonnelDetails.setSpecialCode(hrPersonnelDetailsTO.getSpecialCode());
        hrPersonnelDetails.setSkillCode(hrPersonnelDetailsTO.getSkillCode());
        hrPersonnelDetails.setProbPeriod(hrPersonnelDetailsTO.getProbPeriod() == null ? null : hrPersonnelDetailsTO.getProbPeriod().floatValue());
        hrPersonnelDetails.setNoticePeriod(hrPersonnelDetailsTO.getNoticePeriod() == null ? null : hrPersonnelDetailsTO.getNoticePeriod().floatValue());
        hrPersonnelDetails.setConfirmationDate(hrPersonnelDetailsTO.getConfirmationDate());
        hrPersonnelDetails.setChildren(hrPersonnelDetailsTO.getChildren());
        hrPersonnelDetails.setEmailId(hrPersonnelDetailsTO.getEmailId());
        hrPersonnelDetails.setBasicSal(hrPersonnelDetailsTO.getBasicSal() == null ? null : hrPersonnelDetailsTO.getBasicSal().floatValue());
        hrPersonnelDetails.setGrossSal(hrPersonnelDetailsTO.getGrossSal() == null ? null : hrPersonnelDetailsTO.getGrossSal().floatValue());
        hrPersonnelDetails.setRepTo(hrPersonnelDetailsTO.getRepTo());
        hrPersonnelDetails.setWorkStatus(hrPersonnelDetailsTO.getWorkStatus());
        hrPersonnelDetails.setPassportNo(hrPersonnelDetailsTO.getPassportNo());
        hrPersonnelDetails.setPassportDate(hrPersonnelDetailsTO.getPassportDate());
        hrPersonnelDetails.setVisaDet(hrPersonnelDetailsTO.getVisaDet());
        hrPersonnelDetails.setProfMember(hrPersonnelDetailsTO.getProfMember());
        hrPersonnelDetails.setIndivMember(hrPersonnelDetailsTO.getIndivMember());
        hrPersonnelDetails.setTimeIn(hrPersonnelDetailsTO.getTimeIn());
        hrPersonnelDetails.setTimeOut(hrPersonnelDetailsTO.getTimeOut());
        hrPersonnelDetails.setShiftCode(hrPersonnelDetailsTO.getShiftCode());
        hrPersonnelDetails.setAccomdType(hrPersonnelDetailsTO.getAccomdType());
        hrPersonnelDetails.setInsuranceNo(hrPersonnelDetailsTO.getInsuranceNo());
        hrPersonnelDetails.setCareer(hrPersonnelDetailsTO.getCareer());
        hrPersonnelDetails.setJobdesc(hrPersonnelDetailsTO.getJobdesc());
        hrPersonnelDetails.setBankCode(hrPersonnelDetailsTO.getBankCode());
        hrPersonnelDetails.setBankAccountCode(hrPersonnelDetailsTO.getBankAccountCode());
        hrPersonnelDetails.setCompanyLease(hrPersonnelDetailsTO.getCompanyLease());
        hrPersonnelDetails.setHraAmt(hrPersonnelDetailsTO.getHraAmt() == null ? null : hrPersonnelDetailsTO.getHraAmt().floatValue());
        hrPersonnelDetails.setEsiMember(hrPersonnelDetailsTO.getEsiMember());
        hrPersonnelDetails.setPfMember(hrPersonnelDetailsTO.getPfMember());
        hrPersonnelDetails.setVpfMember(hrPersonnelDetailsTO.getVpfMember());
        hrPersonnelDetails.setTrustMember(hrPersonnelDetailsTO.getTrustMember());
        hrPersonnelDetails.setHouseholdAmt(hrPersonnelDetailsTO.getHouseholdAmt() == null ? null : hrPersonnelDetailsTO.getHouseholdAmt().floatValue());
        hrPersonnelDetails.setMetro(hrPersonnelDetailsTO.getMetro());
        hrPersonnelDetails.setTown(hrPersonnelDetailsTO.getTown());
        hrPersonnelDetails.setArea(hrPersonnelDetailsTO.getArea());
        hrPersonnelDetails.setOccCode(hrPersonnelDetailsTO.getOccCode());
        hrPersonnelDetails.setEmpImage(hrPersonnelDetailsTO.getEmpImage());
        hrPersonnelDetails.setCertAdosNo(hrPersonnelDetailsTO.getCertAdosNo());
        hrPersonnelDetails.setCertAdosDate(hrPersonnelDetailsTO.getCertAdosDate());
        hrPersonnelDetails.setCertTokNo(hrPersonnelDetailsTO.getCertTokNo());
        hrPersonnelDetails.setCertRef(hrPersonnelDetailsTO.getCertRef());
        hrPersonnelDetails.setRelay(hrPersonnelDetailsTO.getRelay());
        hrPersonnelDetails.setRelayDate(hrPersonnelDetailsTO.getRelayDate());
        hrPersonnelDetails.setDefaultComp(hrPersonnelDetailsTO.getDefaultComp());
        hrPersonnelDetails.setStatFlag(hrPersonnelDetailsTO.getStatFlag());
        hrPersonnelDetails.setStatUpFlag(hrPersonnelDetailsTO.getStatUpFlag());
       
        hrPersonnelDetails.setModDate(date);
        hrPersonnelDetails.setAuthBy(hrPersonnelDetailsTO.getAuthBy());
        hrPersonnelDetails.setFinAccountCode(hrPersonnelDetailsTO.getFinAccountCode());
        hrPersonnelDetails.setEmpStatus(hrPersonnelDetailsTO.getEmpStatus());
        hrPersonnelDetails.setPassword(hrPersonnelDetailsTO.getPassword());
        hrPersonnelDetails.setEnteredBy(hrPersonnelDetailsTO.getEnteredBy());
        hrPersonnelDetails.setCustomerId(hrPersonnelDetailsTO.getCustomerId());
        hrPersonnelDetails.setPfAccount(hrPersonnelDetailsTO.getPfAccount());
        hrPersonnelDetails.setBaseBranch(hrPersonnelDetailsTO.getBaseBranch());
        hrPersonnelDetails.setDesgCode(hrPersonnelDetailsTO.getDesignation());
        hrPersonnelDetails.setRetirementDate(hrPersonnelDetailsTO.getRetirementDate());
        hrPersonnelDetails.setUanNumber(hrPersonnelDetailsTO.getUanNumber());
        return hrPersonnelDetails;
    }

    public static HrPersonnelDetailsTO adaptTOHrPersonnelDetailsTO(HrPersonnelDetails hrPersonnelDetails) {
        HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
        
        hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(ObjectAdaptorHr.adaptToHrPeronnelDetailPKTO(hrPersonnelDetails.getHrPersonnelDetailsPK()));
        hrPersonnelDetailsTO.setEmpId(hrPersonnelDetails.getEmpId());
        hrPersonnelDetailsTO.setEmpCardNo(hrPersonnelDetails.getEmpCardNo());
        hrPersonnelDetailsTO.setEmpName(hrPersonnelDetails.getEmpName());
        hrPersonnelDetailsTO.setBlock(hrPersonnelDetails.getBlock());
        hrPersonnelDetailsTO.setUnitName(hrPersonnelDetails.getUnitName());
        hrPersonnelDetailsTO.setEmpFirName(hrPersonnelDetails.getEmpFirName());
        hrPersonnelDetailsTO.setEmpMidName(hrPersonnelDetails.getEmpMidName());
        hrPersonnelDetailsTO.setEmpLastName(hrPersonnelDetails.getEmpLastName());
        hrPersonnelDetailsTO.setEmpContAdd(hrPersonnelDetails.getEmpContAdd());
        hrPersonnelDetailsTO.setEmpContPin(hrPersonnelDetails.getEmpContPin());
        hrPersonnelDetailsTO.setEmpContCity(hrPersonnelDetails.getEmpContCity());
        hrPersonnelDetailsTO.setEmpContState(hrPersonnelDetails.getEmpContState());
        hrPersonnelDetailsTO.setEmpContTel(hrPersonnelDetails.getEmpContTel());
        hrPersonnelDetailsTO.setEmpPermAdd(hrPersonnelDetails.getEmpPermAdd());
        hrPersonnelDetailsTO.setEmpPermPin(hrPersonnelDetails.getEmpPermPin());
        hrPersonnelDetailsTO.setEmpPermCity(hrPersonnelDetails.getEmpPermCity());
        hrPersonnelDetailsTO.setEmpPermState(hrPersonnelDetails.getEmpPermState());
        hrPersonnelDetailsTO.setEmpPermTel(hrPersonnelDetails.getEmpPermTel());
        hrPersonnelDetailsTO.setBirthDate(hrPersonnelDetails.getBirthDate());
        hrPersonnelDetailsTO.setSex(hrPersonnelDetails.getSex());
        hrPersonnelDetailsTO.setEmpType(hrPersonnelDetails.getEmpType());
        hrPersonnelDetailsTO.setFathHusName(hrPersonnelDetails.getFathHusName());
        hrPersonnelDetailsTO.setFatherHusOcc(hrPersonnelDetails.getFatherHusOcc());
        hrPersonnelDetailsTO.setFatherHusDesig(hrPersonnelDetails.getFatherHusDesig());
        hrPersonnelDetailsTO.setFatherHusOrg(hrPersonnelDetails.getFatherHusOrg());
        hrPersonnelDetailsTO.setFatherHusPhone(hrPersonnelDetails.getFatherHusPhone());
        hrPersonnelDetailsTO.setTotExpr(hrPersonnelDetails.getTotExpr() == null ? null : hrPersonnelDetails.getTotExpr().doubleValue());
        hrPersonnelDetailsTO.setAutoExpr(hrPersonnelDetails.getAutoExpr() == null ? null : hrPersonnelDetails.getAutoExpr().doubleValue());
        hrPersonnelDetailsTO.setZones(hrPersonnelDetails.getZones());
        hrPersonnelDetailsTO.setLocatCode(hrPersonnelDetails.getLocatCode());
        hrPersonnelDetailsTO.setDeptCode(hrPersonnelDetails.getDeptCode());
        hrPersonnelDetailsTO.setGradeCode(hrPersonnelDetails.getGradeCode());
        hrPersonnelDetailsTO.setDesgCode(hrPersonnelDetails.getDesgCode());
        hrPersonnelDetailsTO.setCatgCode(hrPersonnelDetails.getCatgCode());
        hrPersonnelDetailsTO.setSubdeptCode(hrPersonnelDetails.getSubdeptCode());
        hrPersonnelDetailsTO.setTravOverseasStatus(hrPersonnelDetails.getTravOverseasStatus());
        hrPersonnelDetailsTO.setHeight(hrPersonnelDetails.getHeight());
        hrPersonnelDetailsTO.setNation(hrPersonnelDetails.getNation());
        hrPersonnelDetailsTO.setWeight(hrPersonnelDetails.getWeight());
        hrPersonnelDetailsTO.setChest(hrPersonnelDetails.getChest());
        hrPersonnelDetailsTO.setHealth(hrPersonnelDetails.getHealth());
        hrPersonnelDetailsTO.setReligion(hrPersonnelDetails.getReligion());
        hrPersonnelDetailsTO.setMaritalStatus(hrPersonnelDetails.getMaritalStatus());
        hrPersonnelDetailsTO.setBloodGrp(hrPersonnelDetails.getBloodGrp());
        hrPersonnelDetailsTO.setBirthCity(hrPersonnelDetails.getBirthCity());
        hrPersonnelDetailsTO.setBirthState(hrPersonnelDetails.getBirthState());
        hrPersonnelDetailsTO.setWeddingDate(hrPersonnelDetails.getWeddingDate());
        hrPersonnelDetailsTO.setJoinDate(hrPersonnelDetails.getJoinDate());
        hrPersonnelDetailsTO.setPayrollDate(hrPersonnelDetails.getPayrollDate());
        hrPersonnelDetailsTO.setPayMode(hrPersonnelDetails.getPayMode());
        hrPersonnelDetailsTO.setOtEligibility(hrPersonnelDetails.getOtEligibility());
        hrPersonnelDetailsTO.setSpecialCode(hrPersonnelDetails.getSpecialCode());
        hrPersonnelDetailsTO.setSkillCode(hrPersonnelDetails.getSkillCode());
        hrPersonnelDetailsTO.setProbPeriod(hrPersonnelDetails.getProbPeriod() == null ? null : hrPersonnelDetails.getProbPeriod().doubleValue());
        hrPersonnelDetailsTO.setNoticePeriod(hrPersonnelDetails.getNoticePeriod() == null ? null : hrPersonnelDetails.getNoticePeriod().doubleValue());
        hrPersonnelDetailsTO.setConfirmationDate(hrPersonnelDetails.getConfirmationDate());
        hrPersonnelDetailsTO.setChildren(hrPersonnelDetails.getChildren());
        hrPersonnelDetailsTO.setEmailId(hrPersonnelDetails.getEmailId());
        hrPersonnelDetailsTO.setBasicSal(hrPersonnelDetails.getBasicSal() == null ? null : hrPersonnelDetails.getBasicSal().doubleValue());
        hrPersonnelDetailsTO.setGrossSal(hrPersonnelDetails.getGrossSal() == null ? null : hrPersonnelDetails.getGrossSal().doubleValue());
        hrPersonnelDetailsTO.setRepTo(hrPersonnelDetails.getRepTo());
        hrPersonnelDetailsTO.setWorkStatus(hrPersonnelDetails.getWorkStatus());
        hrPersonnelDetailsTO.setPassportNo(hrPersonnelDetails.getPassportNo());
        hrPersonnelDetailsTO.setPassportDate(hrPersonnelDetails.getPassportDate());
        hrPersonnelDetailsTO.setVisaDet(hrPersonnelDetails.getVisaDet());
        hrPersonnelDetailsTO.setProfMember(hrPersonnelDetails.getProfMember());
        hrPersonnelDetailsTO.setIndivMember(hrPersonnelDetails.getIndivMember());
        hrPersonnelDetailsTO.setTimeIn(hrPersonnelDetails.getTimeIn());
        hrPersonnelDetailsTO.setTimeOut(hrPersonnelDetails.getTimeOut());
        hrPersonnelDetailsTO.setShiftCode(hrPersonnelDetails.getShiftCode());
        hrPersonnelDetailsTO.setAccomdType(hrPersonnelDetails.getAccomdType());
        hrPersonnelDetailsTO.setInsuranceNo(hrPersonnelDetails.getInsuranceNo());
        hrPersonnelDetailsTO.setCareer(hrPersonnelDetails.getCareer());
        hrPersonnelDetailsTO.setJobdesc(hrPersonnelDetails.getJobdesc());
        hrPersonnelDetailsTO.setBankCode(hrPersonnelDetails.getBankCode());
        hrPersonnelDetailsTO.setBankAccountCode(hrPersonnelDetails.getBankAccountCode());
        hrPersonnelDetailsTO.setCompanyLease(hrPersonnelDetails.getCompanyLease());
        hrPersonnelDetailsTO.setHraAmt(hrPersonnelDetails.getHraAmt() == null ? null : hrPersonnelDetails.getHraAmt().doubleValue());
        hrPersonnelDetailsTO.setEsiMember(hrPersonnelDetails.getEsiMember());
        hrPersonnelDetailsTO.setPfMember(hrPersonnelDetails.getPfMember());
        hrPersonnelDetailsTO.setVpfMember(hrPersonnelDetails.getVpfMember());
        hrPersonnelDetailsTO.setTrustMember(hrPersonnelDetails.getTrustMember());
        hrPersonnelDetailsTO.setHouseholdAmt(hrPersonnelDetails.getHouseholdAmt() == null ? null : hrPersonnelDetails.getHouseholdAmt().doubleValue());
        hrPersonnelDetailsTO.setMetro(hrPersonnelDetails.getMetro());
        hrPersonnelDetailsTO.setTown(hrPersonnelDetails.getTown());
        hrPersonnelDetailsTO.setArea(hrPersonnelDetails.getArea());
        hrPersonnelDetailsTO.setOccCode(hrPersonnelDetails.getOccCode());
        hrPersonnelDetailsTO.setEmpImage(hrPersonnelDetails.getEmpImage());
        hrPersonnelDetailsTO.setCertAdosNo(hrPersonnelDetails.getCertAdosNo());
        hrPersonnelDetailsTO.setCertAdosDate(hrPersonnelDetails.getCertAdosDate());
        hrPersonnelDetailsTO.setCertTokNo(hrPersonnelDetails.getCertTokNo());
        hrPersonnelDetailsTO.setCertRef(hrPersonnelDetails.getCertRef());
        hrPersonnelDetailsTO.setRelay(hrPersonnelDetails.getRelay());
        hrPersonnelDetailsTO.setRelayDate(hrPersonnelDetails.getRelayDate());
        hrPersonnelDetailsTO.setDefaultComp(hrPersonnelDetails.getDefaultComp());
        hrPersonnelDetailsTO.setStatFlag(hrPersonnelDetails.getStatFlag());
        hrPersonnelDetailsTO.setStatUpFlag(hrPersonnelDetails.getStatUpFlag());
        hrPersonnelDetailsTO.setUanNumber(hrPersonnelDetails.getUanNumber());
       // aditya upadted
        Date date = new Date();
    //    hrPersonnelDetailsTO.setModDate(hrPersonnelDetails.getModDate());
        hrPersonnelDetailsTO.setModDate(date);
        hrPersonnelDetailsTO.setAuthBy(hrPersonnelDetails.getAuthBy());
        hrPersonnelDetailsTO.setFinAccountCode(hrPersonnelDetails.getFinAccountCode());
        hrPersonnelDetailsTO.setEmpStatus(hrPersonnelDetails.getEmpStatus());
        hrPersonnelDetailsTO.setPassword(hrPersonnelDetails.getPassword());
        hrPersonnelDetailsTO.setEnteredBy(hrPersonnelDetails.getEnteredBy());
        hrPersonnelDetailsTO.setCustomerId(hrPersonnelDetails.getCustomerId());
        hrPersonnelDetailsTO.setPfAccount(hrPersonnelDetails.getPfAccount());
        hrPersonnelDetailsTO.setBaseBranch(hrPersonnelDetails.getBaseBranch());
        hrPersonnelDetailsTO.setDesgCode(hrPersonnelDetails.getDesgCode());
        return hrPersonnelDetailsTO;

    }

    public static HrInterviewDtPK adaptToHrInterviewDtPKEntity(HrInterviewDtPKTO hrInterviewDtPKTO) {
        HrInterviewDtPK HrInterviewDtPK = new HrInterviewDtPK();
        HrInterviewDtPK.setCompCode(hrInterviewDtPKTO.getCompCode());
        HrInterviewDtPK.setIntCode(hrInterviewDtPKTO.getIntCode());
        HrInterviewDtPK.setAdvtCode(hrInterviewDtPKTO.getAdvtCode());
        HrInterviewDtPK.setJobCode(hrInterviewDtPKTO.getJobCode());
        HrInterviewDtPK.setCandSrno(hrInterviewDtPKTO.getCandSrno());
        return HrInterviewDtPK;
    }

    public static HrInterviewDtPKTO adaptToHrInterviewDtPKTO(HrInterviewDtPK hrInterviewDtPK) {
        HrInterviewDtPKTO hrInterviewDtPKTO = new HrInterviewDtPKTO();
        hrInterviewDtPKTO.setCompCode(hrInterviewDtPK.getCompCode());
        hrInterviewDtPKTO.setIntCode(hrInterviewDtPK.getIntCode());
        hrInterviewDtPKTO.setAdvtCode(hrInterviewDtPK.getAdvtCode());
        hrInterviewDtPKTO.setJobCode(hrInterviewDtPK.getJobCode());
        hrInterviewDtPKTO.setCandSrno(hrInterviewDtPK.getCandSrno());
        return hrInterviewDtPKTO;
    }

    public static HrInterviewDt adaptToHrInterviewDtEntity(HrInterviewDtTO hrInterviewDtTO) {
        HrInterviewDt hrInterviewDt = new HrInterviewDt();
        hrInterviewDt.setHrInterviewDtPK(ObjectAdaptorHr.adaptToHrInterviewDtPKEntity(hrInterviewDtTO.getHrInterviewDtPKTO()));
//        hrInterviewDt.setHrDatabank(ObjectAdaptorHr.adaptToHrDatabankEntity(hrInterviewDtTO.getHrDatabankTO()));
//        hrInterviewDt.setHrInterviewHd(ObjectAdaptorHr.adaptToHrInterviewHdEntity(hrInterviewDtTO.getHrInterviewHdTO()));
        hrInterviewDt.setIntResult(hrInterviewDtTO.getIntResult());
        hrInterviewDt.setTimeIn(hrInterviewDtTO.getTimeIn());
        hrInterviewDt.setCancel(hrInterviewDtTO.getCancel());
        hrInterviewDt.setAdviceCancel(hrInterviewDtTO.getAdviceCancel());
        hrInterviewDt.setExpectJoin(hrInterviewDtTO.getExpectJoin());
        hrInterviewDt.setExtension(hrInterviewDtTO.getExtension());
        hrInterviewDt.setIntRemarks(hrInterviewDtTO.getIntRemarks());
        hrInterviewDt.setDefaultCompCode(hrInterviewDtTO.getDefaultCompCode());
        hrInterviewDt.setStatFlag(hrInterviewDtTO.getStatFlag());
        hrInterviewDt.setStatUpFlag(hrInterviewDtTO.getStatUpFlag());
        hrInterviewDt.setModDate(hrInterviewDtTO.getModDate());
        hrInterviewDt.setAuthBy(hrInterviewDtTO.getAuthBy());
        hrInterviewDt.setEnteredBy(hrInterviewDtTO.getEnteredBy());
        return hrInterviewDt;
    }

    public static HrInterviewDtTO adaptToHrInterviewDtTO(HrInterviewDt hrInterviewDt) {
        HrInterviewDtTO hrInterviewDtTO = new HrInterviewDtTO();
        hrInterviewDtTO.setHrInterviewDtPKTO(ObjectAdaptorHr.adaptToHrInterviewDtPKTO(hrInterviewDt.getHrInterviewDtPK()));
//        hrInterviewDtTO.setHrDatabankTO(ObjectAdaptorHr.adaptToHrDatabankTO(hrInterviewDt.getHrDatabank()));
//        hrInterviewDtTO.setHrInterviewHdTO(ObjectAdaptorHr.adaptToHrInterviewHdTO(hrInterviewDt.getHrInterviewHd()));
        hrInterviewDtTO.setIntResult(hrInterviewDt.getIntResult());
        hrInterviewDtTO.setTimeIn(hrInterviewDt.getTimeIn());
        hrInterviewDtTO.setCancel(hrInterviewDt.getCancel());
        hrInterviewDtTO.setAdviceCancel(hrInterviewDt.getAdviceCancel());
        hrInterviewDtTO.setExpectJoin(hrInterviewDt.getExpectJoin());
        hrInterviewDtTO.setExtension(hrInterviewDt.getExtension());
        hrInterviewDtTO.setIntRemarks(hrInterviewDt.getIntRemarks());
        hrInterviewDtTO.setDefaultCompCode(hrInterviewDt.getDefaultCompCode());
        hrInterviewDtTO.setStatFlag(hrInterviewDt.getStatFlag());
        hrInterviewDtTO.setStatUpFlag(hrInterviewDt.getStatUpFlag());
        hrInterviewDtTO.setModDate(hrInterviewDt.getModDate());
        hrInterviewDtTO.setAuthBy(hrInterviewDt.getAuthBy());
        hrInterviewDtTO.setEnteredBy(hrInterviewDt.getEnteredBy());
        return hrInterviewDtTO;
    }

    public static HrInterviewDtSalPK adaptToHrInterviewDtSalPKEntity(HrInterviewDtSalPKTO hrInterviewDtSalPKTO) {
        HrInterviewDtSalPK hrInterviewDtSalPK = new HrInterviewDtSalPK();
        hrInterviewDtSalPK.setCompCode(hrInterviewDtSalPKTO.getCompCode());
        hrInterviewDtSalPK.setIntCode(hrInterviewDtSalPKTO.getIntCode());
        hrInterviewDtSalPK.setAdvtCode(hrInterviewDtSalPKTO.getAdvtCode());
        hrInterviewDtSalPK.setJobCode(hrInterviewDtSalPKTO.getJobCode());
        hrInterviewDtSalPK.setCandSrno(hrInterviewDtSalPKTO.getCandSrno());
        return hrInterviewDtSalPK;
    }

    public static HrInterviewDtSalPKTO adaptToHrInterviewDtSalPKTO(HrInterviewDtSalPK hrInterviewDtSalPK) {
        HrInterviewDtSalPKTO hrInterviewDtSalPKTO = new HrInterviewDtSalPKTO();
        hrInterviewDtSalPKTO.setCompCode(hrInterviewDtSalPK.getCompCode());
        hrInterviewDtSalPKTO.setIntCode(hrInterviewDtSalPK.getIntCode());
        hrInterviewDtSalPKTO.setAdvtCode(hrInterviewDtSalPK.getAdvtCode());
        hrInterviewDtSalPKTO.setJobCode(hrInterviewDtSalPK.getJobCode());
        hrInterviewDtSalPKTO.setCandSrno(hrInterviewDtSalPK.getCandSrno());
        return hrInterviewDtSalPKTO;
    }

    public static HrInterviewDtSal adaptToHrInterviewDtSalEntity(HrInterviewDtSalTO hrInterviewDtSalTO) {
        HrInterviewDtSal HrInterviewDtSal = new HrInterviewDtSal();
        HrInterviewDtSal.setHrInterviewDtSalPK(ObjectAdaptorHr.adaptToHrInterviewDtSalPKEntity(hrInterviewDtSalTO.getHrInterviewDtSalPKTO()));
        //HrInterviewDtSal.setHrDatabank(ObjectAdaptorHr.adaptToHrDatabankEntity(hrInterviewDtSalTO.getHrDatabankTO()));
        //HrInterviewDtSal.setHrInterviewDt(ObjectAdaptorHr.adaptToHrInterviewDtEntity(hrInterviewDtSalTO.getHrInterviewDtTO()));
        HrInterviewDtSal.setSalCompCode(hrInterviewDtSalTO.getSalCompCode());
        HrInterviewDtSal.setCompOff(hrInterviewDtSalTO.getCompOff());
        HrInterviewDtSal.setCompNegoit(hrInterviewDtSalTO.getCompNegoit());
        HrInterviewDtSal.setCompExpect(hrInterviewDtSalTO.getCompExpect());
        HrInterviewDtSal.setDefaultCompCode(hrInterviewDtSalTO.getDefaultCompCode());
        HrInterviewDtSal.setStatFlag(hrInterviewDtSalTO.getStatFlag());
        HrInterviewDtSal.setStatUpFlag(hrInterviewDtSalTO.getStatUpFlag());
        HrInterviewDtSal.setModDate(hrInterviewDtSalTO.getModDate());
        HrInterviewDtSal.setAuthBy(hrInterviewDtSalTO.getAuthBy());
        HrInterviewDtSal.setEnteredBy(hrInterviewDtSalTO.getEnteredBy());
        return HrInterviewDtSal;
    }

    public static HrInterviewDtSalTO adaptToHrInterviewDtSalTO(HrInterviewDtSal hrInterviewDtSal) {
        HrInterviewDtSalTO hrInterviewDtSalTO = new HrInterviewDtSalTO();
        hrInterviewDtSalTO.setHrInterviewDtSalPKTO(ObjectAdaptorHr.adaptToHrInterviewDtSalPKTO(hrInterviewDtSal.getHrInterviewDtSalPK()));
        // hrInterviewDtSalTO.setHrDatabankTO(ObjectAdaptorHr.adaptToHrDatabankTO(hrInterviewDtSal.getHrDatabank()));
        // hrInterviewDtSalTO.setHrInterviewDtTO(ObjectAdaptorHr.adaptToHrInterviewDtTO(hrInterviewDtSal.getHrInterviewDt()));
        hrInterviewDtSalTO.setSalCompCode(hrInterviewDtSal.getSalCompCode());
        hrInterviewDtSalTO.setCompOff(hrInterviewDtSal.getCompOff());
        hrInterviewDtSalTO.setCompNegoit(hrInterviewDtSal.getCompNegoit());
        hrInterviewDtSalTO.setCompExpect(hrInterviewDtSal.getCompExpect());
        hrInterviewDtSalTO.setDefaultCompCode(hrInterviewDtSal.getDefaultCompCode());
        hrInterviewDtSalTO.setStatFlag(hrInterviewDtSal.getStatFlag());
        hrInterviewDtSalTO.setStatUpFlag(hrInterviewDtSal.getStatUpFlag());
        hrInterviewDtSalTO.setModDate(hrInterviewDtSal.getModDate());
        hrInterviewDtSalTO.setAuthBy(hrInterviewDtSal.getAuthBy());
        hrInterviewDtSalTO.setEnteredBy(hrInterviewDtSal.getEnteredBy());
        return hrInterviewDtSalTO;
    }

    public static HrInterviewHdPK adaptToHrInterviewHdPKEntity(HrInterviewHdPKTO hrInterviewHdPKTO) {
        HrInterviewHdPK hrInterviewHdPK = new HrInterviewHdPK();
        hrInterviewHdPK.setCompCode(hrInterviewHdPKTO.getCompCode());
        hrInterviewHdPK.setIntCode(hrInterviewHdPKTO.getIntCode());
        return hrInterviewHdPK;
    }

    public static HrInterviewHdPKTO adaptToHrInterviewHdPKTO(HrInterviewHdPK hrInterviewHdPK) {
        HrInterviewHdPKTO hrInterviewHdPKTO = new HrInterviewHdPKTO();
        hrInterviewHdPKTO.setCompCode(hrInterviewHdPK.getCompCode());
        hrInterviewHdPKTO.setIntCode(hrInterviewHdPK.getIntCode());
        return hrInterviewHdPKTO;
    }

    public static HrInterviewHd adaptToHrInterviewHdEntity(HrInterviewHdTO hrInterviewHdTO) {
        HrInterviewHd hrInterviewHd = new HrInterviewHd();
        hrInterviewHd.setHrInterviewHdPK(ObjectAdaptorHr.adaptToHrInterviewHdPKEntity(hrInterviewHdTO.getHrInterviewHdPKTO()));
        hrInterviewHd.setDesgCode(hrInterviewHdTO.getDesgCode());
        hrInterviewHd.setIntDate(hrInterviewHdTO.getIntDate());
        hrInterviewHd.setIntTime(hrInterviewHdTO.getIntTime());
        hrInterviewHd.setTimePerCand(hrInterviewHdTO.getTimePerCand());
        hrInterviewHd.setTimeBreak(hrInterviewHdTO.getTimeBreak());
        hrInterviewHd.setFareCatg(hrInterviewHdTO.getFareCatg());
        hrInterviewHd.setIntVenue(hrInterviewHdTO.getIntVenue());
        hrInterviewHd.setEmpCode(hrInterviewHdTO.getEmpCode());
        hrInterviewHd.setEmpCode2(hrInterviewHdTO.getEmpCode2());
        hrInterviewHd.setEmp1Desg(hrInterviewHdTO.getEmp1Desg());
        hrInterviewHd.setEmp2Desg(hrInterviewHdTO.getEmp2Desg());
        hrInterviewHd.setRecType(hrInterviewHdTO.getRecType());
        hrInterviewHd.setDefaultCompcode(hrInterviewHdTO.getDefaultCompcode());
        hrInterviewHd.setStatFlag(hrInterviewHdTO.getStatFlag());
        hrInterviewHd.setStatUpFlag(hrInterviewHdTO.getStatUpFlag());
        hrInterviewHd.setModDate(hrInterviewHdTO.getModDate());
        hrInterviewHd.setAuthBy(hrInterviewHdTO.getAuthBy());
        hrInterviewHd.setEnteredBy(hrInterviewHdTO.getEnteredBy());
        return hrInterviewHd;
    }

    public static HrInterviewHdTO adaptToHrInterviewHdTO(HrInterviewHd hrInterviewHd) {
        HrInterviewHdTO HrInterviewHdTO = new HrInterviewHdTO();
        HrInterviewHdTO.setHrInterviewHdPKTO(ObjectAdaptorHr.adaptToHrInterviewHdPKTO(hrInterviewHd.getHrInterviewHdPK()));
        HrInterviewHdTO.setDesgCode(hrInterviewHd.getDesgCode());
        HrInterviewHdTO.setIntDate(hrInterviewHd.getIntDate());
        HrInterviewHdTO.setIntTime(hrInterviewHd.getIntTime());
        HrInterviewHdTO.setTimePerCand(hrInterviewHd.getTimePerCand());
        HrInterviewHdTO.setTimeBreak(hrInterviewHd.getTimeBreak());
        HrInterviewHdTO.setFareCatg(hrInterviewHd.getFareCatg());
        HrInterviewHdTO.setIntVenue(hrInterviewHd.getIntVenue());
        HrInterviewHdTO.setEmpCode(hrInterviewHd.getEmpCode());
        HrInterviewHdTO.setEmpCode2(hrInterviewHd.getEmpCode2());
        HrInterviewHdTO.setEmp1Desg(hrInterviewHd.getEmp1Desg());
        HrInterviewHdTO.setEmp2Desg(hrInterviewHd.getEmp2Desg());
        HrInterviewHdTO.setRecType(hrInterviewHd.getRecType());
        HrInterviewHdTO.setDefaultCompcode(hrInterviewHd.getDefaultCompcode());
        HrInterviewHdTO.setStatFlag(hrInterviewHd.getStatFlag());
        HrInterviewHdTO.setStatUpFlag(hrInterviewHd.getStatUpFlag());
        HrInterviewHdTO.setModDate(hrInterviewHd.getModDate());
        HrInterviewHdTO.setAuthBy(hrInterviewHd.getAuthBy());
        HrInterviewHdTO.setEnteredBy(hrInterviewHd.getEnteredBy());
        return HrInterviewHdTO;
    }

    public static HrManpowerPK adaptToHrManpowerPKEntity(HrManpowerPKTO hrManpowerPKTO) {
        HrManpowerPK hrManpowerPK = new HrManpowerPK();
        hrManpowerPK.setCompCode(hrManpowerPKTO.getCompCode());
        hrManpowerPK.setYear(hrManpowerPKTO.getYear());
        hrManpowerPK.setMonth(hrManpowerPKTO.getMonth());
        hrManpowerPK.setZone(hrManpowerPKTO.getZone());
        hrManpowerPK.setDeptCode(hrManpowerPKTO.getDeptCode());
        return hrManpowerPK;
    }

    public static HrManpowerPKTO adaptToHrManpowerPKTO(HrManpowerPK hrManpowerPK) {
        HrManpowerPKTO hrManpowerPKTO = new HrManpowerPKTO();
        hrManpowerPKTO.setCompCode(hrManpowerPK.getCompCode());
        hrManpowerPKTO.setYear(hrManpowerPK.getYear());
        hrManpowerPKTO.setMonth(hrManpowerPK.getMonth());
        hrManpowerPKTO.setZone(hrManpowerPK.getZone());
        hrManpowerPKTO.setDeptCode(hrManpowerPK.getDeptCode());
        return hrManpowerPKTO;
    }

    public static HrManpower adaptToHrManpowerEntity(HrManpowerTO hrManpowerTO) {
        HrManpower hrManpower = new HrManpower();
        hrManpower.setHrManpowerPK(ObjectAdaptorHr.adaptToHrManpowerPKEntity(hrManpowerTO.getHrManpowerPKTO()));
//        hrManpower.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrManpowerTO.getHrMstStructTO()));
//        hrManpower.setHrMstStruct1(ObjectAdaptorHr.adaptToMstStructEntity(hrManpowerTO.getHrMstStruct1TO()));
//        hrManpower.setHrMstStruct2(ObjectAdaptorHr.adaptToMstStructEntity(hrManpowerTO.getHrMstStruct2TO()));
//        hrManpower.setHrMstStruct3(ObjectAdaptorHr.adaptToMstStructEntity(hrManpowerTO.getHrMstStruct3TO()));
//        hrManpower.setHrMstStruct4(ObjectAdaptorHr.adaptToMstStructEntity(hrManpowerTO.getHrMstStruct4TO()));
//        hrManpower.setHrMstStruct5(ObjectAdaptorHr.adaptToMstStructEntity(hrManpowerTO.getHrMstStruct5TO()));
//        hrManpower.setHrMstStruct6(ObjectAdaptorHr.adaptToMstStructEntity(hrManpowerTO.getHrMstStruct6TO()));
        hrManpower.setMinmExp(hrManpowerTO.getMinmExp());
        hrManpower.setPrefExp(hrManpowerTO.getPrefExp());
        hrManpower.setRequExp(hrManpowerTO.getRequExp());
        hrManpower.setAutoExp(hrManpowerTO.getAutoExp());
        hrManpower.setPosFill(hrManpowerTO.getPosFill());
        hrManpower.setPosRequ(hrManpowerTO.getPosRequ());
        hrManpower.setPosSanc(hrManpowerTO.getPosSanc());
        hrManpower.setSkillCode(hrManpowerTO.getSkillCode());
        hrManpower.setDefaultComp(hrManpowerTO.getDefaultComp());
        hrManpower.setStatFlag(hrManpowerTO.getStatFlag());
        hrManpower.setStatUpFlag(hrManpowerTO.getStatUpFlag());
        hrManpower.setModDate(hrManpowerTO.getModDate());
        hrManpower.setAuthBy(hrManpowerTO.getAuthBy());
        hrManpower.setEnteredBy(hrManpowerTO.getEnteredBy());
        //hrManpower.setJone(hrManpowerTO.getJone());
        //hrManpower.setDepartmentCode(hrManpowerTO.getDeptCode());
        hrManpower.setQualcode1(hrManpowerTO.getQualcode1());
        hrManpower.setQualCode2(hrManpowerTO.getQualCode2());
        hrManpower.setQualCode3(hrManpowerTO.getQualCode3());
        hrManpower.setSpecialCode(hrManpowerTO.getSpecialCode());
        hrManpower.setDesgCode(hrManpowerTO.getDesgCode());

        return hrManpower;
    }

    public static HrManpowerTO adaptToHrManpowerTO(HrManpower hrManpower) {
        HrManpowerTO hrManpowerTO = new HrManpowerTO();
        hrManpowerTO.setHrManpowerPKTO(ObjectAdaptorHr.adaptToHrManpowerPKTO(hrManpower.getHrManpowerPK()));
//        hrManpowerTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrManpower.getHrMstStruct()));
//        hrManpowerTO.setHrMstStruct1TO(ObjectAdaptorHr.adaptToStructMasterTO(hrManpower.getHrMstStruct1()));
//        hrManpowerTO.setHrMstStruct2TO(ObjectAdaptorHr.adaptToStructMasterTO(hrManpower.getHrMstStruct2()));
//        hrManpowerTO.setHrMstStruct3TO(ObjectAdaptorHr.adaptToStructMasterTO(hrManpower.getHrMstStruct3()));
//        hrManpowerTO.setHrMstStruct4TO(ObjectAdaptorHr.adaptToStructMasterTO(hrManpower.getHrMstStruct4()));
//        hrManpowerTO.setHrMstStruct5TO(ObjectAdaptorHr.adaptToStructMasterTO(hrManpower.getHrMstStruct5()));
//        hrManpowerTO.setHrMstStruct6TO(ObjectAdaptorHr.adaptToStructMasterTO(hrManpower.getHrMstStruct6()));
        hrManpowerTO.setMinmExp(hrManpower.getMinmExp());
        hrManpowerTO.setPrefExp(hrManpower.getPrefExp());
        hrManpowerTO.setRequExp(hrManpower.getRequExp());
        hrManpowerTO.setAutoExp(hrManpower.getAutoExp());
        hrManpowerTO.setPosFill(hrManpower.getPosFill());
        hrManpowerTO.setPosRequ(hrManpower.getPosRequ());
        hrManpowerTO.setPosSanc(hrManpower.getPosSanc());
        hrManpowerTO.setSkillCode(hrManpower.getSkillCode());
        hrManpowerTO.setDefaultComp(hrManpower.getDefaultComp());
        hrManpowerTO.setStatFlag(hrManpower.getStatFlag());
        hrManpowerTO.setStatUpFlag(hrManpower.getStatUpFlag());
        hrManpowerTO.setModDate(hrManpower.getModDate());
        hrManpowerTO.setAuthBy(hrManpower.getAuthBy());
        hrManpowerTO.setEnteredBy(hrManpower.getEnteredBy());
        // hrManpowerTO.setJone(hrManpower.getJone());
        // hrManpowerTO.setDeptCode(hrManpower.getDepartmentCode());
        hrManpowerTO.setQualcode1(hrManpower.getQualcode1());
        hrManpowerTO.setQualCode2(hrManpower.getQualCode2());
        hrManpowerTO.setQualCode3(hrManpower.getQualCode3());
        hrManpowerTO.setSpecialCode(hrManpower.getSpecialCode());
        hrManpowerTO.setDesgCode(hrManpower.getDesgCode());
        return hrManpowerTO;
    }

    public static HrMstDesgPK adaptToHrMstDesgPKEntity(HrMstDesgPKTO hrMstDesgPKTO) {
        HrMstDesgPK hrMstDesgPK = new HrMstDesgPK();
        hrMstDesgPK.setCompCode(hrMstDesgPKTO.getCompCode());
        hrMstDesgPK.setDesgCode(hrMstDesgPKTO.getDesgCode());
        return hrMstDesgPK;
    }

    public static HrMstDesgPKTO adaptToHrMstDesgPKTO(HrMstDesgPK hrMstDesgPK) {
        HrMstDesgPKTO hrMstDesgPKTO = new HrMstDesgPKTO();
        hrMstDesgPKTO.setCompCode(hrMstDesgPK.getCompCode());
        hrMstDesgPKTO.setDesgCode(hrMstDesgPK.getDesgCode());
        return hrMstDesgPKTO;
    }

    public static HrMstDesg adaptToHrMstDesgEntity(HrMstDesgTO hrMstDesgTO) {
        HrMstDesg hrMstDesg = new HrMstDesg();
        hrMstDesg.setHrMstDesgPK(ObjectAdaptorHr.adaptToHrMstDesgPKEntity(hrMstDesgTO.getHrMstDesgPKTO()));
        //hrMstDesg.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrMstDesgTO.getHrMstStructTO()));
        // hrMstDesg.setHrMstStruct1(ObjectAdaptorHr.adaptToMstStructEntity(hrMstDesgTO.getHrMstStruct1TO()));
        hrMstDesg.setDefaultComp(hrMstDesgTO.getDefaultComp());
        hrMstDesg.setStatFlag(hrMstDesgTO.getStatFlag());
        hrMstDesg.setStatUpFlag(hrMstDesgTO.getStatUpFlag());
        hrMstDesg.setModDate(hrMstDesgTO.getModDate());
        hrMstDesg.setAuthBy(hrMstDesgTO.getAuthBy());
        hrMstDesg.setEnteredBy(hrMstDesgTO.getEnteredBy());
        hrMstDesg.setGradeCode(hrMstDesgTO.getGradeCode());
        return hrMstDesg;
    }

    public static HrMstDesgTO adaptToHrMstDesgTO(HrMstDesg hrMstDesg) {
        HrMstDesgTO hrMstDesgTO = new HrMstDesgTO();
        hrMstDesgTO.setHrMstDesgPKTO(ObjectAdaptorHr.adaptToHrMstDesgPKTO(hrMstDesg.getHrMstDesgPK()));
        //hrMstDesgTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrMstDesg.getHrMstStruct()));
        // hrMstDesgTO.setHrMstStruct1TO(ObjectAdaptorHr.adaptToStructMasterTO(hrMstDesg.getHrMstStruct1()));
        hrMstDesgTO.setDefaultComp(hrMstDesg.getDefaultComp());
        hrMstDesgTO.setStatFlag(hrMstDesg.getStatFlag());
        hrMstDesgTO.setStatUpFlag(hrMstDesg.getStatUpFlag());
        hrMstDesgTO.setModDate(hrMstDesg.getModDate());
        hrMstDesgTO.setAuthBy(hrMstDesg.getAuthBy());
        hrMstDesgTO.setEnteredBy(hrMstDesg.getEnteredBy());
        hrMstDesgTO.setGradeCode(hrMstDesg.getGradeCode());
        return hrMstDesgTO;
    }

//    /***************** written by awesh ***********************/
//    public static AdminRoleFormsPK adaptToAdminRoleFormsPKEntity(AdminRoleFormsPKTO adminRoleFormsPKTO) {
//        AdminRoleFormsPK adminRoleFormsPK = new AdminRoleFormsPK();
//        adminRoleFormsPK.setRoleName(adminRoleFormsPKTO.getRoleName());
//        adminRoleFormsPK.setForms(adminRoleFormsPKTO.getForms());
//        return adminRoleFormsPK;
//    }
//
//    public static AdminRoleFormsPKTO adaptToAdminRoleFormsPKTO(AdminRoleFormsPK adminRoleFormsPK) {
//        AdminRoleFormsPKTO adminRoleFormsPKTO = new AdminRoleFormsPKTO();
//        adminRoleFormsPKTO.setRoleName(adminRoleFormsPK.getRoleName());
//        adminRoleFormsPKTO.setForms(adminRoleFormsPK.getForms());
//        return adminRoleFormsPKTO;
//    }
//
//    public static AdminRoleForms adaptToAdminRoleFormsEntity(AdminRoleFormsTO adminRoleFormsTO) {
//        AdminRoleForms adminRoleForms = new AdminRoleForms();
//        adminRoleForms.setAdminRoleFormsPK(ObjectAdaptorHr.adaptToAdminRoleFormsPKEntity(adminRoleFormsTO.getAdminRoleFormsPKTO()));
//        adminRoleForms.setAddOp(adminRoleFormsTO.getAddOp());
//        adminRoleForms.setEditOp(adminRoleFormsTO.getEditOp());
//        adminRoleForms.setViewOp(adminRoleFormsTO.getViewOp());
//        adminRoleForms.setDeleteOp(adminRoleFormsTO.getDeleteOp());
//        adminRoleForms.setAuthorizeOp(adminRoleFormsTO.getAuthorizeOp());
//        adminRoleForms.setTranTime(adminRoleFormsTO.getTranTime());
//        adminRoleForms.setEffectDate(adminRoleFormsTO.getEffectDate());
//        adminRoleForms.setEnteredBy(adminRoleFormsTO.getEnteredBy());
//        adminRoleForms.setDefaultComp(adminRoleFormsTO.getDefaultComp());
//        adminRoleForms.setAuthBy(adminRoleFormsTO.getAuthBy());
//        adminRoleForms.setStatFlag(adminRoleFormsTO.getStatFlag());
//        adminRoleForms.setStatUpFlag(adminRoleFormsTO.getStatUpFlag());
//        return adminRoleForms;
//    }
//
//    public static AdminRoleFormsTO adaptToAdminRoleFormsTO(AdminRoleForms adminRoleForms) {
//        AdminRoleFormsTO adminRoleFormsTO = new AdminRoleFormsTO();
//        adminRoleFormsTO.setAdminRoleFormsPKTO(ObjectAdaptorHr.adaptToAdminRoleFormsPKTO(adminRoleForms.getAdminRoleFormsPK()));
//        adminRoleFormsTO.setAddOp(adminRoleForms.getAddOp());
//        adminRoleFormsTO.setEditOp(adminRoleForms.getEditOp());
//        adminRoleFormsTO.setViewOp(adminRoleForms.getViewOp());
//        adminRoleFormsTO.setDeleteOp(adminRoleForms.getDeleteOp());
//        adminRoleFormsTO.setAuthorizeOp(adminRoleForms.getAuthorizeOp());
//        adminRoleFormsTO.setTranTime(adminRoleForms.getTranTime());
//        adminRoleFormsTO.setEffectDate(adminRoleForms.getEffectDate());
//        adminRoleFormsTO.setEnteredBy(adminRoleForms.getEnteredBy());
//        adminRoleFormsTO.setDefaultComp(adminRoleForms.getDefaultComp());
//        adminRoleFormsTO.setAuthBy(adminRoleForms.getAuthBy());
//        adminRoleFormsTO.setStatFlag(adminRoleForms.getStatFlag());
//        adminRoleFormsTO.setStatUpFlag(adminRoleForms.getStatUpFlag());
//        return adminRoleFormsTO;
//    }
//
//    public static AdminUserRolePK adaptToAdminUserRolePKEntity(AdminUserRolePKTO adminUserRolePKTO) {
//        AdminUserRolePK adminUserRolePK = new AdminUserRolePK();
//        adminUserRolePK.setUserName(adminUserRolePKTO.getUserName());
//        adminUserRolePK.setTRoleName(adminUserRolePKTO.gettRoleName());
//        adminUserRolePK.setRoleName(adminUserRolePKTO.getRoleName());
//        return adminUserRolePK;
//    }
//
//    public static AdminUserRolePKTO adaptToAdminUserRolePKTO(AdminUserRolePK adminUserRolePK) {
//        AdminUserRolePKTO adminUserRolePKTO = new AdminUserRolePKTO();
//        adminUserRolePKTO.setUserName(adminUserRolePK.getUserName());
//        adminUserRolePKTO.settRoleName(adminUserRolePK.getRoleName());
//        adminUserRolePKTO.setRoleName(adminUserRolePK.getRoleName());
//        return adminUserRolePKTO;
//    }
//
//    public static AdminUserRole adaptToAdminUserRoleEntity(AdminUserRoleTO adminUserRoleTO) {
//        AdminUserRole adminUserRole = new AdminUserRole();
//        adminUserRole.setAdminUserRolePK(ObjectAdaptorHr.adaptToAdminUserRolePKEntity(adminUserRoleTO.getAdminUserRolePKTO()));
//        adminUserRole.setTranTime(adminUserRoleTO.getTranTime());
//        adminUserRole.setEffectDate(adminUserRoleTO.getEffectDate());
//        adminUserRole.setEnteredBy(adminUserRoleTO.getEnteredBy());
//        adminUserRole.setDefaultComp(adminUserRoleTO.getDefaultComp());
//        adminUserRole.setAuthBy(adminUserRoleTO.getAuthBy());
//        adminUserRole.setStatFlag(adminUserRoleTO.getStatFlag());
//        adminUserRole.setStatUpFlag(adminUserRoleTO.getStatUpFlag());
//
//        return adminUserRole;
//    }
//
//    public static AdminUserRoleTO adaptToAdminUserRoleTO(AdminUserRole adminUserRole) {
//        AdminUserRoleTO adminUserRoleTO = new AdminUserRoleTO();
//        adminUserRoleTO.setAdminUserRolePKTO(ObjectAdaptorHr.adaptToAdminUserRolePKTO(adminUserRole.getAdminUserRolePK()));
//        adminUserRoleTO.setTranTime(adminUserRole.getTranTime());
//        adminUserRoleTO.setEffectDate(adminUserRole.getEffectDate());
//        adminUserRoleTO.setEnteredBy(adminUserRole.getEnteredBy());
//        adminUserRoleTO.setDefaultComp(adminUserRole.getDefaultComp());
//        adminUserRoleTO.setAuthBy(adminUserRole.getAuthBy());
//        adminUserRoleTO.setStatFlag(adminUserRole.getStatFlag());
//        adminUserRoleTO.setStatUpFlag(adminUserRole.getStatUpFlag());
//        return adminUserRoleTO;
//    }
    public static HrAdvertDetDtPK adaptToHrAdvertDetDtPKEntity(HrAdvertDetDtPKTO hrAdvertDetDtPKTO) {
        HrAdvertDetDtPK hrAdvertDetDtPK = new HrAdvertDetDtPK();
        hrAdvertDetDtPK.setCompCode(hrAdvertDetDtPKTO.getCompCode());
        hrAdvertDetDtPK.setAdvtCode(hrAdvertDetDtPKTO.getAdvtCode());
        hrAdvertDetDtPK.setMediaType(hrAdvertDetDtPKTO.getMediaType());
        hrAdvertDetDtPK.setJobCode(hrAdvertDetDtPKTO.getJobCode());
        hrAdvertDetDtPK.setCity(hrAdvertDetDtPKTO.getCity());
        hrAdvertDetDtPK.setMediaName(hrAdvertDetDtPKTO.getMediaName());
        return hrAdvertDetDtPK;
    }

    public static HrAdvertDetDtPKTO adaptToHrAdvertDetDtPKTO(HrAdvertDetDtPK hrAdvertDetDtPK) {
        HrAdvertDetDtPKTO hrAdvertDetDtPKTO = new HrAdvertDetDtPKTO();

        hrAdvertDetDtPKTO.setCompCode(hrAdvertDetDtPK.getCompCode());
        hrAdvertDetDtPKTO.setAdvtCode(hrAdvertDetDtPK.getAdvtCode());
        hrAdvertDetDtPKTO.setMediaType(hrAdvertDetDtPK.getMediaType());
        hrAdvertDetDtPKTO.setJobCode(hrAdvertDetDtPK.getJobCode());
        hrAdvertDetDtPKTO.setCity(hrAdvertDetDtPK.getCity());
        hrAdvertDetDtPKTO.setMediaName(hrAdvertDetDtPK.getMediaName());
        return hrAdvertDetDtPKTO;
    }

    public static HrAdvertDetDt adaptToHrAdvertDetDtEntity(HrAdvertDetDtTO hrAdvertDetDtTO) {
        HrAdvertDetDt hrAdvertDetDt = new HrAdvertDetDt();
        hrAdvertDetDt.setHrAdvertDetDtPK(ObjectAdaptorHr.adaptToHrAdvertDetDtPKEntity(hrAdvertDetDtTO.getHrAdvertDetDtPKTO()));
        hrAdvertDetDt.setResponse(hrAdvertDetDtTO.getResponse());
        hrAdvertDetDt.setPageNo(hrAdvertDetDtTO.getPageNo());
        hrAdvertDetDt.setColumnNo(hrAdvertDetDtTO.getColumnNo());
        hrAdvertDetDt.setLanguageIn(hrAdvertDetDtTO.getLanguageIn());
        hrAdvertDetDt.setCost(hrAdvertDetDtTO.getCost());
        hrAdvertDetDt.setTimeIn(hrAdvertDetDtTO.getTimeIn());
        hrAdvertDetDt.setDuration(hrAdvertDetDtTO.getDuration());
        hrAdvertDetDt.setDefaultComp(hrAdvertDetDtTO.getDefaultComp());
        hrAdvertDetDt.setStatFlag(hrAdvertDetDtTO.getStatFlag());
        hrAdvertDetDt.setStatUpFlag(hrAdvertDetDtTO.getStatUpFlag());
        hrAdvertDetDt.setModDate(hrAdvertDetDtTO.getModDate());
        hrAdvertDetDt.setAuthBy(hrAdvertDetDtTO.getAuthBy());
        hrAdvertDetDt.setEnteredBy(hrAdvertDetDtTO.getEnteredBy());
        return hrAdvertDetDt;
    }

    public static HrAdvertDetDtTO adaptToHrAdvertDetDtTO(HrAdvertDetDt hrAdvertDetDt) {
        HrAdvertDetDtTO hrAdvertDetDtTO = new HrAdvertDetDtTO();
        hrAdvertDetDtTO.setHrAdvertDetDtPKTO(ObjectAdaptorHr.adaptToHrAdvertDetDtPKTO(hrAdvertDetDt.getHrAdvertDetDtPK()));
        hrAdvertDetDtTO.setResponse(hrAdvertDetDt.getResponse());
        hrAdvertDetDtTO.setPageNo(hrAdvertDetDt.getPageNo());
        hrAdvertDetDtTO.setColumnNo(hrAdvertDetDt.getColumnNo());
        hrAdvertDetDtTO.setLanguageIn(hrAdvertDetDt.getLanguageIn());
        hrAdvertDetDtTO.setCost(hrAdvertDetDt.getCost());
        hrAdvertDetDtTO.setTimeIn(hrAdvertDetDt.getTimeIn());
        hrAdvertDetDtTO.setDuration(hrAdvertDetDt.getDuration());
        hrAdvertDetDtTO.setDefaultComp(hrAdvertDetDt.getDefaultComp());
        hrAdvertDetDtTO.setStatFlag(hrAdvertDetDt.getStatFlag());
        hrAdvertDetDtTO.setStatUpFlag(hrAdvertDetDt.getStatUpFlag());
        hrAdvertDetDtTO.setModDate(hrAdvertDetDt.getModDate());
        hrAdvertDetDtTO.setAuthBy(hrAdvertDetDt.getAuthBy());
        hrAdvertDetDtTO.setEnteredBy(hrAdvertDetDt.getEnteredBy());
        return hrAdvertDetDtTO;
    }

    public static HrAdvertDtPK adaptToHrAdvertDtPKEntity(HrAdvertDtPKTO hrAdvertDtPKTO) {
        HrAdvertDtPK hrAdvertDtPK = new HrAdvertDtPK();
        hrAdvertDtPK.setCompCode(hrAdvertDtPKTO.getCompCode());
        hrAdvertDtPK.setAdvtCode(hrAdvertDtPKTO.getAdvtCode());
        hrAdvertDtPK.setJobCode(hrAdvertDtPKTO.getJobCode());
        hrAdvertDtPK.setMediaType(hrAdvertDtPKTO.getMediaType());
        hrAdvertDtPK.setDesgCode(hrAdvertDtPKTO.getDesgCode());
        hrAdvertDtPK.setDeptCode(hrAdvertDtPKTO.getDeptCode());
        hrAdvertDtPK.setLocatCode(hrAdvertDtPKTO.getLocatCode());
        return hrAdvertDtPK;
    }

    public static HrAdvertDtPKTO adaptToHrAdvertDtPKTO(HrAdvertDtPK hrAdvertDtPK) {
        HrAdvertDtPKTO hrAdvertDtPKTO = new HrAdvertDtPKTO();
        hrAdvertDtPKTO.setCompCode(hrAdvertDtPK.getCompCode());
        hrAdvertDtPKTO.setAdvtCode(hrAdvertDtPK.getAdvtCode());
        hrAdvertDtPKTO.setJobCode(hrAdvertDtPK.getJobCode());
        hrAdvertDtPKTO.setMediaType(hrAdvertDtPK.getMediaType());
        hrAdvertDtPKTO.setDesgCode(hrAdvertDtPK.getDesgCode());
        hrAdvertDtPKTO.setDeptCode(hrAdvertDtPK.getDeptCode());
        hrAdvertDtPKTO.setLocatCode(hrAdvertDtPK.getLocatCode());
        return hrAdvertDtPKTO;
    }

    public static HrAdvertDt adaptToHrAdvertDtEntity(HrAdvertDtTO hrAdvertDtTO) {
        HrAdvertDt hrAdvertDt = new HrAdvertDt();
        hrAdvertDt.setHrAdvertDtPK(ObjectAdaptorHr.adaptToHrAdvertDtPKEntity(hrAdvertDtTO.getHrAdvertDtPKTO()));
        //hrAdvertDt.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrAdvertDtTO.getHrMstStructTO()));
        // hrAdvertDt.setHrMstStruct1(ObjectAdaptorHr.adaptToMstStructEntity(hrAdvertDtTO.getHrMstStructTO1()));
        // hrAdvertDt.setHrMstStruct2(ObjectAdaptorHr.adaptToMstStructEntity(hrAdvertDtTO.getHrMstStructTO2()));
        hrAdvertDt.setVaccant(hrAdvertDtTO.getVaccant());
        hrAdvertDt.setLastDate(hrAdvertDtTO.getLastDate());
        hrAdvertDt.setDefaultComp(hrAdvertDtTO.getDefaultComp());
        hrAdvertDt.setStatFlag(hrAdvertDtTO.getStatFlag());
        hrAdvertDt.setStatUpFlag(hrAdvertDtTO.getStatUpFlag());
        hrAdvertDt.setModDate(hrAdvertDtTO.getModDate());
        hrAdvertDt.setAuthBy(hrAdvertDtTO.getAuthBy());
        hrAdvertDt.setEnteredBy(hrAdvertDtTO.getEnteredBy());
        return hrAdvertDt;
    }

    public static HrAdvertDtTO adaptToHrAdvertDtTO(HrAdvertDt hrAdvertDt) {
        HrAdvertDtTO hrAdvertDtTO = new HrAdvertDtTO();
        hrAdvertDtTO.setHrAdvertDtPKTO(ObjectAdaptorHr.adaptToHrAdvertDtPKTO(hrAdvertDt.getHrAdvertDtPK()));
        // hrAdvertDtTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrAdvertDt.getHrMstStruct()));
        //hrAdvertDtTO.setHrMstStructTO1(ObjectAdaptorHr.adaptToStructMasterTO(hrAdvertDt.getHrMstStruct1()));
        //hrAdvertDtTO.setHrMstStructTO2(ObjectAdaptorHr.adaptToStructMasterTO(hrAdvertDt.getHrMstStruct2()));
        hrAdvertDtTO.setVaccant(hrAdvertDt.getVaccant());
        hrAdvertDtTO.setLastDate(hrAdvertDt.getLastDate());
        hrAdvertDtTO.setDefaultComp(hrAdvertDt.getDefaultComp());
        hrAdvertDtTO.setStatFlag(hrAdvertDt.getStatFlag());
        hrAdvertDtTO.setStatUpFlag(hrAdvertDt.getStatUpFlag());
        hrAdvertDtTO.setModDate(hrAdvertDt.getModDate());
        hrAdvertDtTO.setAuthBy(hrAdvertDt.getAuthBy());
        hrAdvertDtTO.setEnteredBy(hrAdvertDt.getEnteredBy());
        return hrAdvertDtTO;
    }

    public static HrAdvertHdPK adaptToHrAdvertHdPKEntity(HrAdvertHdPKTO hrAdvertHdPKTO) {
        HrAdvertHdPK hrAdvertHdPK = new HrAdvertHdPK();
        hrAdvertHdPK.setCompCode(hrAdvertHdPKTO.getCompCode());
        hrAdvertHdPK.setAdvtCode(hrAdvertHdPKTO.getAdvtCode());
        hrAdvertHdPK.setAdvtDate(hrAdvertHdPKTO.getAdvtDate());
        hrAdvertHdPK.setJobCode(hrAdvertHdPKTO.getJobCode());
        return hrAdvertHdPK;
    }

    public static HrAdvertHdPKTO adaptToHrAdvertHdPKTO(HrAdvertHdPK hrAdvertHdPK) {
        HrAdvertHdPKTO hrAdvertHdPKTO = new HrAdvertHdPKTO();
        hrAdvertHdPKTO.setCompCode(hrAdvertHdPK.getCompCode());
        hrAdvertHdPKTO.setAdvtCode(hrAdvertHdPK.getAdvtCode());
        hrAdvertHdPKTO.setAdvtDate(hrAdvertHdPK.getAdvtDate());
        hrAdvertHdPKTO.setJobCode(hrAdvertHdPK.getJobCode());
        return hrAdvertHdPKTO;
    }

    public static HrAdvertHd adaptToHrAdvertHdEntity(HrAdvertHdTO hrAdvertHdTO) {
        HrAdvertHd hrAdvertHd = new HrAdvertHd();
        hrAdvertHd.setHrAdvertHdPK(ObjectAdaptorHr.adaptToHrAdvertHdPKEntity(hrAdvertHdTO.getHrAdvertHdPKTO()));
        hrAdvertHd.setMediaType(hrAdvertHdTO.getMediaType());
        hrAdvertHd.setDefaultComp(hrAdvertHdTO.getDefaultComp());
        hrAdvertHd.setStatFlag(hrAdvertHdTO.getStatFlag());
        hrAdvertHd.setStatUpFlag(hrAdvertHdTO.getStatUpFlag());
        hrAdvertHd.setModDate(hrAdvertHdTO.getModDate());
        hrAdvertHd.setEnteredBy(hrAdvertHdTO.getEnteredBy());
        hrAdvertHd.setAuthBy(hrAdvertHdTO.getAuthBy());
        return hrAdvertHd;
    }

    public static HrAdvertHdTO adaptToHrAdvertHdTO(HrAdvertHd hrAdvertHd) {
        HrAdvertHdTO hrAdvertHdTO = new HrAdvertHdTO();
        hrAdvertHdTO.setHrAdvertHdPKTO(ObjectAdaptorHr.adaptToHrAdvertHdPKTO(hrAdvertHd.getHrAdvertHdPK()));
        hrAdvertHdTO.setMediaType(hrAdvertHd.getMediaType());
        hrAdvertHdTO.setDefaultComp(hrAdvertHd.getDefaultComp());
        hrAdvertHdTO.setStatFlag(hrAdvertHd.getStatFlag());
        hrAdvertHdTO.setStatUpFlag(hrAdvertHd.getStatUpFlag());
        hrAdvertHdTO.setModDate(hrAdvertHd.getModDate());
        hrAdvertHdTO.setEnteredBy(hrAdvertHd.getEnteredBy());
        hrAdvertHdTO.setAuthBy(hrAdvertHd.getAuthBy());
        return hrAdvertHdTO;
    }

    public static HrTrainingPlanPK adaptToHrTrainingPlanPKEntity(HrTrainingPlanPKTO hrTrainingPlanPKTO) {
        HrTrainingPlanPK hrTrainingPlanPK = new HrTrainingPlanPK();
        hrTrainingPlanPK.setCompCode(hrTrainingPlanPKTO.getCompCode());
        hrTrainingPlanPK.setEmpCode(hrTrainingPlanPKTO.getEmpCode());
        hrTrainingPlanPK.setTrngCode(hrTrainingPlanPKTO.getTrngCode());
        hrTrainingPlanPK.setProgCode(hrTrainingPlanPKTO.getProgCode());
        hrTrainingPlanPK.setDateFrom(hrTrainingPlanPKTO.getDateFrom());
        hrTrainingPlanPK.setDateTo(hrTrainingPlanPKTO.getDateTo());
        return hrTrainingPlanPK;
    }

    public static HrTrainingPlanPKTO adaptToHrAdvertHdPKTO(HrTrainingPlanPK hrTrainingPlanPK) {
        HrTrainingPlanPKTO hrTrainingPlanPKTO = new HrTrainingPlanPKTO();
        hrTrainingPlanPKTO.setCompCode(hrTrainingPlanPK.getCompCode());
        hrTrainingPlanPKTO.setEmpCode(hrTrainingPlanPK.getEmpCode());
        hrTrainingPlanPKTO.setTrngCode(hrTrainingPlanPK.getTrngCode());
        hrTrainingPlanPKTO.setProgCode(hrTrainingPlanPK.getProgCode());
        hrTrainingPlanPKTO.setDateFrom(hrTrainingPlanPK.getDateFrom());
        hrTrainingPlanPKTO.setDateTo(hrTrainingPlanPK.getDateTo());
        return hrTrainingPlanPKTO;
    }

    public static HrTrainingPlan adaptToHrTrainingPlanEntity(HrTrainingPlanTO hrTrainingPlanTO) {
        HrTrainingPlan hrTrainingPlan = new HrTrainingPlan();
        hrTrainingPlan.setHrTrainingPlanPK(ObjectAdaptorHr.adaptToHrTrainingPlanPKEntity(hrTrainingPlanTO.getHrTrainingPlanPKTO()));
        hrTrainingPlan.setTrngDur(hrTrainingPlanTO.getTrngDur());
        hrTrainingPlan.setTrngExec(hrTrainingPlanTO.getTrngExec());
        hrTrainingPlan.setApprDet(hrTrainingPlanTO.getApprDet());
        hrTrainingPlan.setDefaultComp(hrTrainingPlanTO.getDefaultComp());
        hrTrainingPlan.setStatFlag(hrTrainingPlanTO.getStatFlag());
        hrTrainingPlan.setStatUpFlag(hrTrainingPlanTO.getStatUpFlag());
        hrTrainingPlan.setModDate(hrTrainingPlanTO.getModDate());
        hrTrainingPlan.setAuthBy(hrTrainingPlanTO.getAuthBy());
        hrTrainingPlan.setEnteredBy(hrTrainingPlanTO.getEnteredBy());
        return hrTrainingPlan;
    }

    public static HrTrainingPlanTO adaptToHrTrainingPlanTO(HrTrainingPlan hrTrainingPlan) {
        HrTrainingPlanTO hrTrainingPlanTO = new HrTrainingPlanTO();
        hrTrainingPlanTO.setHrTrainingPlanPKTO(ObjectAdaptorHr.adaptToHrAdvertHdPKTO(hrTrainingPlan.getHrTrainingPlanPK()));
        hrTrainingPlanTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrTrainingPlan.getHrMstStruct()));
        hrTrainingPlanTO.setHrMstProgramTO(ObjectAdaptorHr.adaptToHrMstProgramTO(hrTrainingPlan.getHrMstProgram()));
        hrTrainingPlanTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrTrainingPlan.getHrPersonnelDetails()));
        hrTrainingPlanTO.setTrngDur(hrTrainingPlan.getTrngDur());
        hrTrainingPlanTO.setTrngExec(hrTrainingPlan.getTrngExec());
        hrTrainingPlanTO.setApprDet(hrTrainingPlan.getApprDet());
        hrTrainingPlanTO.setDefaultComp(hrTrainingPlan.getDefaultComp());
        hrTrainingPlanTO.setStatFlag(hrTrainingPlan.getStatFlag());
        hrTrainingPlanTO.setStatUpFlag(hrTrainingPlan.getStatUpFlag());
        hrTrainingPlanTO.setModDate(hrTrainingPlan.getModDate());
        hrTrainingPlanTO.setAuthBy(hrTrainingPlan.getAuthBy());
        hrTrainingPlanTO.setEnteredBy(hrTrainingPlan.getEnteredBy());
        return hrTrainingPlanTO;
    }

    public static HrTrainingExecutionPK adaptToHrTrainingExecutionPKEntity(HrTrainingExecutionPKTO hrTrainingExecutionPKTO) {
        HrTrainingExecutionPK hrTrainingExecutionPK = new HrTrainingExecutionPK();
        hrTrainingExecutionPK.setCompCode(hrTrainingExecutionPKTO.getCompCode());
        hrTrainingExecutionPK.setEmpCode(hrTrainingExecutionPKTO.getEmpCode());
        hrTrainingExecutionPK.setTrngexecCode(hrTrainingExecutionPKTO.getTrngexecCode());
        return hrTrainingExecutionPK;
    }

    public static HrTrainingExecutionPKTO adaptToHrTrainingExecutionPKTO(HrTrainingExecutionPK hrTrainingExecutionPK) {
        HrTrainingExecutionPKTO hrTrainingExecutionPKTO = new HrTrainingExecutionPKTO();
        hrTrainingExecutionPKTO.setCompCode(hrTrainingExecutionPK.getCompCode());
        hrTrainingExecutionPKTO.setEmpCode(hrTrainingExecutionPK.getEmpCode());
        hrTrainingExecutionPKTO.setTrngexecCode(hrTrainingExecutionPK.getTrngexecCode());
        return hrTrainingExecutionPKTO;
    }

    public static HrTrainingExecution adaptToHrTrainingExecutionEntity(HrTrainingExecutionTO hrTrainingExecutionTO) {
        HrTrainingExecution hrTrainingExecution = new HrTrainingExecution();
        hrTrainingExecution.setHrTrainingExecutionPK(ObjectAdaptorHr.adaptToHrTrainingExecutionPKEntity(hrTrainingExecutionTO.getHrTrainingExecutionPKTO()));
        // hrTrainingExecution.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrTrainingExecutionTO.getHrPersonnelDetailsTO()));
        hrTrainingExecution.setTrngCode(hrTrainingExecutionTO.getTrngCode());
        hrTrainingExecution.setProgCode(hrTrainingExecutionTO.getProgCode());
        hrTrainingExecution.setFacuName(hrTrainingExecutionTO.getFacuName());
        hrTrainingExecution.setInextHouse(hrTrainingExecutionTO.getInextHouse());
        hrTrainingExecution.setDatePlanFrom(hrTrainingExecutionTO.getDatePlanFrom());
        hrTrainingExecution.setDatePlanTo(hrTrainingExecutionTO.getDatePlanTo());
        hrTrainingExecution.setCommence(hrTrainingExecutionTO.getCommence());
        hrTrainingExecution.setEndDate(hrTrainingExecutionTO.getEndDate());
        hrTrainingExecution.setActDura(hrTrainingExecutionTO.getActDura());
        hrTrainingExecution.setTrngCost(hrTrainingExecutionTO.getTrngCost());
        hrTrainingExecution.setApprDet(hrTrainingExecutionTO.getApprDet());
        hrTrainingExecution.setTrainerAss(hrTrainingExecutionTO.getTrainerAss());
        hrTrainingExecution.setTraineeAss(hrTrainingExecutionTO.getTraineeAss());
        hrTrainingExecution.setDefaultComp(hrTrainingExecutionTO.getDefaultComp());
        hrTrainingExecution.setStatFlag(hrTrainingExecutionTO.getStatFlag());
        hrTrainingExecution.setStatUpFlag(hrTrainingExecutionTO.getStatUpFlag());
        hrTrainingExecution.setModDate(hrTrainingExecutionTO.getModDate());
        hrTrainingExecution.setEnteredBy(hrTrainingExecutionTO.getEnteredBy());
        hrTrainingExecution.setAuthBy(hrTrainingExecutionTO.getAuthBy());
        return hrTrainingExecution;
    }

    public static HrTrainingExecutionTO adaptToHrTrainingExecutionTO(HrTrainingExecution hrTrainingExecution) {
        HrTrainingExecutionTO hrTrainingExecutionTO = new HrTrainingExecutionTO();
        hrTrainingExecutionTO.setHrTrainingExecutionPKTO(ObjectAdaptorHr.adaptToHrTrainingExecutionPKTO(hrTrainingExecution.getHrTrainingExecutionPK()));
        // hrTrainingExecutionTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrTrainingExecution.getHrPersonnelDetails()));
        hrTrainingExecutionTO.setTrngCode(hrTrainingExecution.getTrngCode());
        hrTrainingExecutionTO.setProgCode(hrTrainingExecution.getProgCode());
        hrTrainingExecutionTO.setFacuName(hrTrainingExecution.getFacuName());
        hrTrainingExecutionTO.setInextHouse(hrTrainingExecution.getInextHouse());
        hrTrainingExecutionTO.setDatePlanFrom(hrTrainingExecution.getDatePlanFrom());
        hrTrainingExecutionTO.setDatePlanTo(hrTrainingExecution.getDatePlanTo());
        hrTrainingExecutionTO.setCommence(hrTrainingExecution.getCommence());
        hrTrainingExecutionTO.setEndDate(hrTrainingExecution.getEndDate());
        hrTrainingExecutionTO.setActDura(hrTrainingExecution.getActDura());
        hrTrainingExecutionTO.setTrngCost(hrTrainingExecution.getTrngCost());
        hrTrainingExecutionTO.setApprDet(hrTrainingExecution.getApprDet());
        hrTrainingExecutionTO.setTrainerAss(hrTrainingExecution.getTrainerAss());
        hrTrainingExecutionTO.setTraineeAss(hrTrainingExecution.getTraineeAss());
        hrTrainingExecutionTO.setDefaultComp(hrTrainingExecution.getDefaultComp());
        hrTrainingExecutionTO.setStatFlag(hrTrainingExecution.getStatFlag());
        hrTrainingExecutionTO.setStatUpFlag(hrTrainingExecution.getStatUpFlag());
        hrTrainingExecutionTO.setModDate(hrTrainingExecution.getModDate());
        hrTrainingExecutionTO.setEnteredBy(hrTrainingExecution.getEnteredBy());
        hrTrainingExecutionTO.setAuthBy(hrTrainingExecution.getAuthBy());
        return hrTrainingExecutionTO;
    }

    public static HrSalaryStructurePK adaptToHrSalaryStructurePKEntity(HrSalaryStructurePKTO hrSalaryStructurePKTO) {
        HrSalaryStructurePK hrSalaryStructurePK = new HrSalaryStructurePK();
        hrSalaryStructurePK.setCompCode(hrSalaryStructurePKTO.getCompCode());
        hrSalaryStructurePK.setPurposeCode(hrSalaryStructurePKTO.getPurposeCode());
        hrSalaryStructurePK.setNature(hrSalaryStructurePKTO.getNature());
        hrSalaryStructurePK.setAlDesc(hrSalaryStructurePKTO.getAlDesc());
//        hrSalaryStructurePK.setDateFrom(hrSalaryStructurePKTO.getDateFrom());
//        hrSalaryStructurePK.setDateTo(hrSalaryStructurePKTO.getDateTo());
        return hrSalaryStructurePK;
    }

    public static HrSalaryStructurePKTO adaptToHrSalaryStructurePKTO(HrSalaryStructurePK hrSalaryStructurePK) {
        HrSalaryStructurePKTO hrSalaryStructurePKTO = new HrSalaryStructurePKTO();
        hrSalaryStructurePKTO.setCompCode(hrSalaryStructurePK.getCompCode());
        hrSalaryStructurePKTO.setPurposeCode(hrSalaryStructurePK.getPurposeCode());
        hrSalaryStructurePKTO.setNature(hrSalaryStructurePK.getNature());
        hrSalaryStructurePKTO.setAlDesc(hrSalaryStructurePK.getAlDesc());
//        hrSalaryStructurePKTO.setDateFrom(hrSalaryStructurePK.getDateFrom());
//        hrSalaryStructurePKTO.setDateTo(hrSalaryStructurePK.getDateTo());
        return hrSalaryStructurePKTO;
    }

    public static HrSalaryStructure adaptToHrSalaryStructureEntity(HrSalaryStructureTO hrSalaryStructureTO) {
        HrSalaryStructure hrSalaryStructure = new HrSalaryStructure();
        hrSalaryStructure.setHrSalaryStructurePK(ObjectAdaptorHr.adaptToHrSalaryStructurePKEntity(hrSalaryStructureTO.getHrSalaryStructurePKTO()));
        hrSalaryStructure.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrSalaryStructureTO.getHrMstStructTO()));
        hrSalaryStructure.setHrMstStruct1(ObjectAdaptorHr.adaptToMstStructEntity(hrSalaryStructureTO.getHrMstStruct1TO()));
        hrSalaryStructure.setTaxable(hrSalaryStructureTO.getTaxable());
        hrSalaryStructure.setApplicableDate(hrSalaryStructureTO.getApplicableDate());
        hrSalaryStructure.setDefaultComp(hrSalaryStructureTO.getDefaultComp());
        hrSalaryStructure.setStatFlag(hrSalaryStructureTO.getStatFlag());
        hrSalaryStructure.setStatUpFlag(hrSalaryStructureTO.getStatUpFlag());
        hrSalaryStructure.setModDate(hrSalaryStructureTO.getModDate());
        hrSalaryStructure.setEnteredBy(hrSalaryStructureTO.getEnteredBy());
        hrSalaryStructure.setAuthBy(hrSalaryStructureTO.getAuthBy());
        hrSalaryStructure.setGlCode(hrSalaryStructureTO.getGlCode());
        hrSalaryStructure.setDescShCode(hrSalaryStructureTO.getDescShCode());
        return hrSalaryStructure;
    }

    public static HrSalaryStructureTO adaptToHrSalaryStructureTO(HrSalaryStructure hrSalaryStructure) {
        HrSalaryStructureTO hrSalaryStructureTO = new HrSalaryStructureTO();
        hrSalaryStructureTO.setHrSalaryStructurePKTO(ObjectAdaptorHr.adaptToHrSalaryStructurePKTO(hrSalaryStructure.getHrSalaryStructurePK()));
        hrSalaryStructureTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrSalaryStructure.getHrMstStruct()));
        hrSalaryStructureTO.setHrMstStruct1TO(ObjectAdaptorHr.adaptToStructMasterTO(hrSalaryStructure.getHrMstStruct1()));
        hrSalaryStructureTO.setTaxable(hrSalaryStructure.getTaxable());
        hrSalaryStructureTO.setApplicableDate(hrSalaryStructure.getApplicableDate());
        hrSalaryStructureTO.setDefaultComp(hrSalaryStructure.getDefaultComp());
        hrSalaryStructureTO.setStatFlag(hrSalaryStructure.getStatFlag());
        hrSalaryStructureTO.setStatUpFlag(hrSalaryStructure.getStatUpFlag());
        hrSalaryStructureTO.setModDate(hrSalaryStructure.getModDate());
        hrSalaryStructureTO.setEnteredBy(hrSalaryStructure.getEnteredBy());
        hrSalaryStructureTO.setAuthBy(hrSalaryStructure.getAuthBy());
        hrSalaryStructureTO.setGlCode(hrSalaryStructure.getGlCode());
        hrSalaryStructureTO.setDescShCode(hrSalaryStructure.getDescShCode());
        return hrSalaryStructureTO;
    }

    public static HrOrgChartPK adaptToHrOrgChartPKEntity(HrOrgChartPKTO hrOrgChartPKTO) {
        HrOrgChartPK hrOrgChartPK = new HrOrgChartPK();
        hrOrgChartPK.setCompCode(hrOrgChartPKTO.getCompCode());
        hrOrgChartPK.setDesgCode(hrOrgChartPKTO.getDesgCode());
        return hrOrgChartPK;
    }

    public static HrOrgChartPKTO adaptToHrOrgChartPKTO(HrOrgChartPK hrOrgChartPK) {
        HrOrgChartPKTO hrOrgChartPKTO = new HrOrgChartPKTO();
        hrOrgChartPKTO.setCompCode(hrOrgChartPK.getCompCode());
        hrOrgChartPKTO.setDesgCode(hrOrgChartPK.getDesgCode());
        return hrOrgChartPKTO;
    }

    public static HrOrgChart adaptToHrOrgChartEntity(HrOrgChartTO hrOrgChartTO) {
        HrOrgChart hrOrgChart = new HrOrgChart();
        hrOrgChart.setHrOrgChartPK(ObjectAdaptorHr.adaptToHrOrgChartPKEntity(hrOrgChartTO.getHrOrgChartPKTO()));
        hrOrgChart.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrOrgChartTO.getHrMstStructTO()));
        hrOrgChart.setDesgCodeRp(hrOrgChartTO.getDesgCodeRp());
        hrOrgChart.setPosts(hrOrgChartTO.getPosts());
        hrOrgChart.setJobSpecification(hrOrgChartTO.getJobSpecification());
        hrOrgChart.setDescription(hrOrgChartTO.getDescription());
        hrOrgChart.setFlag1(hrOrgChartTO.getFlag1());
        hrOrgChart.setDefaultComp(hrOrgChartTO.getDefaultComp());
        hrOrgChart.setStatFlag(hrOrgChartTO.getStatFlag());
        hrOrgChart.setStatUpFlag(hrOrgChartTO.getStatUpFlag());
        hrOrgChart.setModDate(hrOrgChartTO.getModDate());
        hrOrgChart.setAuthBy(hrOrgChartTO.getAuthBy());
        hrOrgChart.setEnteredBy(hrOrgChartTO.getEnteredBy());
        return hrOrgChart;
    }

    public static HrOrgChartTO adaptToHrOrgChartTO(HrOrgChart hrOrgChart) {
        HrOrgChartTO hrOrgChartTO = new HrOrgChartTO();
        hrOrgChartTO.setHrOrgChartPKTO(ObjectAdaptorHr.adaptToHrOrgChartPKTO(hrOrgChart.getHrOrgChartPK()));
        hrOrgChartTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrOrgChart.getHrMstStruct()));
        hrOrgChartTO.setDesgCodeRp(hrOrgChart.getDesgCodeRp());
        hrOrgChartTO.setPosts(hrOrgChart.getPosts());
        hrOrgChartTO.setJobSpecification(hrOrgChart.getJobSpecification());
        hrOrgChartTO.setDescription(hrOrgChart.getDescription());
        hrOrgChartTO.setFlag1(hrOrgChart.getFlag1());
        hrOrgChartTO.setDefaultComp(hrOrgChart.getDefaultComp());
        hrOrgChartTO.setStatFlag(hrOrgChart.getStatFlag());
        hrOrgChartTO.setStatUpFlag(hrOrgChart.getStatUpFlag());
        hrOrgChartTO.setModDate(hrOrgChart.getModDate());
        hrOrgChartTO.setAuthBy(hrOrgChart.getAuthBy());
        hrOrgChartTO.setEnteredBy(hrOrgChart.getEnteredBy());
        return hrOrgChartTO;
    }

    public static HrMstZoneLocationPK adaptHrMstZoneLocationPKEntity(HrMstZoneLocationPKTO hrMstZoneLocationPKTO) {
        HrMstZoneLocationPK hrMstZoneLocationPK = new HrMstZoneLocationPK();
        hrMstZoneLocationPK.setCompCode(hrMstZoneLocationPKTO.getCompCode());
        hrMstZoneLocationPK.setLocationCode(hrMstZoneLocationPKTO.getLocationCode());
        hrMstZoneLocationPK.setZoneCode(hrMstZoneLocationPKTO.getZoneCode());
        return hrMstZoneLocationPK;
    }

    public static HrMstZoneLocationPKTO adaptToHrMstZoneLocationPKTO(HrMstZoneLocationPK hrMstZoneLocationPK) {
        HrMstZoneLocationPKTO hrMstZoneLocationPKTO = new HrMstZoneLocationPKTO();
        hrMstZoneLocationPKTO.setCompCode(hrMstZoneLocationPK.getCompCode());
        hrMstZoneLocationPKTO.setLocationCode(hrMstZoneLocationPK.getLocationCode());
        hrMstZoneLocationPKTO.setZoneCode(hrMstZoneLocationPK.getZoneCode());
        return hrMstZoneLocationPKTO;
    }

    public static HrMstZoneLocation adaptToHrMstZoneLocationEntity(HrMstZoneLocationTO hrMstZoneLocationTO) {
        HrMstZoneLocation hrMstZoneLocation = new HrMstZoneLocation();
        hrMstZoneLocation.setHrMstZoneLocationPK(ObjectAdaptorHr.adaptHrMstZoneLocationPKEntity(hrMstZoneLocationTO.getHrMstZoneLocationPKTO()));
        hrMstZoneLocation.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrMstZoneLocationTO.getHrMstStructTO()));
        hrMstZoneLocation.setHrMstStruct1(ObjectAdaptorHr.adaptToMstStructEntity(hrMstZoneLocationTO.getHrMstStruct1TO()));
        hrMstZoneLocation.setStatFlag(hrMstZoneLocationTO.getStatFlag());
        hrMstZoneLocation.setStatUpFlag(hrMstZoneLocationTO.getStatUpFlag());
        hrMstZoneLocation.setModDate(hrMstZoneLocationTO.getModDate());
        hrMstZoneLocation.setDefaultComp(hrMstZoneLocationTO.getDefaultComp());
        hrMstZoneLocation.setAuthBy(hrMstZoneLocationTO.getAuthBy());
        hrMstZoneLocation.setEnteredBy(hrMstZoneLocationTO.getEnteredBy());
        return hrMstZoneLocation;
    }

    public static HrMstZoneLocationTO adaptToHrMstZoneLocationTO(HrMstZoneLocation hrMstZoneLocation) {
        HrMstZoneLocationTO hrMstZoneLocationTO = new HrMstZoneLocationTO();
        hrMstZoneLocationTO.setHrMstZoneLocationPKTO(ObjectAdaptorHr.adaptToHrMstZoneLocationPKTO(hrMstZoneLocation.getHrMstZoneLocationPK()));
        hrMstZoneLocationTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrMstZoneLocation.getHrMstStruct()));
        hrMstZoneLocationTO.setHrMstStruct1TO(ObjectAdaptorHr.adaptToStructMasterTO(hrMstZoneLocation.getHrMstStruct1()));
        hrMstZoneLocationTO.setStatFlag(hrMstZoneLocation.getStatFlag());
        hrMstZoneLocationTO.setStatUpFlag(hrMstZoneLocation.getStatUpFlag());
        hrMstZoneLocationTO.setModDate(hrMstZoneLocation.getModDate());
        hrMstZoneLocationTO.setDefaultComp(hrMstZoneLocation.getDefaultComp());
        hrMstZoneLocationTO.setAuthBy(hrMstZoneLocation.getAuthBy());
        hrMstZoneLocationTO.setEnteredBy(hrMstZoneLocation.getEnteredBy());
        return hrMstZoneLocationTO;
    }

    public static HrMstTrngProgramPK adaptHrMstTrngProgramPKEntity(HrMstTrngProgramPKTO hrMstTrngProgramPKTO) {
        HrMstTrngProgramPK hrMstTrngProgramPK = new HrMstTrngProgramPK();
        hrMstTrngProgramPK.setCompCode(hrMstTrngProgramPKTO.getCompCode());
        hrMstTrngProgramPK.setProgCode(hrMstTrngProgramPKTO.getProgCode());
        hrMstTrngProgramPK.setTrngCode(hrMstTrngProgramPKTO.getTrngCode());
        return hrMstTrngProgramPK;
    }

    public static HrMstTrngProgramPKTO adaptToHrMstTrngProgramPKTO(HrMstTrngProgramPK hrMstTrngProgramPK) {
        HrMstTrngProgramPKTO hrMstTrngProgramPKTO = new HrMstTrngProgramPKTO();
        hrMstTrngProgramPKTO.setCompCode(hrMstTrngProgramPK.getCompCode());
        hrMstTrngProgramPKTO.setProgCode(hrMstTrngProgramPK.getProgCode());
        hrMstTrngProgramPKTO.setTrngCode(hrMstTrngProgramPK.getTrngCode());
        return hrMstTrngProgramPKTO;
    }

    public static HrMstTrngProgram adaptToHrMstTrngProgramEntity(HrMstTrngProgramTO hrMstTrngProgramTO) {
        HrMstTrngProgram hrMstTrngProgram = new HrMstTrngProgram();
        hrMstTrngProgram.setHrMstTrngProgramPK(ObjectAdaptorHr.adaptHrMstTrngProgramPKEntity(hrMstTrngProgramTO.getHrMstTrngProgramPKTO()));
        hrMstTrngProgram.setDefaultComp(hrMstTrngProgramTO.getDefaultComp());
        hrMstTrngProgram.setStatFlag(hrMstTrngProgramTO.getStatFlag());
        hrMstTrngProgram.setStatUpFlag(hrMstTrngProgramTO.getStatUpFlag());
        hrMstTrngProgram.setModDate(hrMstTrngProgramTO.getModDate());
        hrMstTrngProgram.setAuthBy(hrMstTrngProgramTO.getAuthBy());
        hrMstTrngProgram.setEnteredBy(hrMstTrngProgramTO.getEnteredBy());
        return hrMstTrngProgram;
    }

    public static HrMstTrngProgramTO adaptToHrMstTrngProgramTO(HrMstTrngProgram hrMstTrngProgram) {
        HrMstTrngProgramTO hrMstTrngProgramTO = new HrMstTrngProgramTO();
        hrMstTrngProgramTO.setHrMstTrngProgramPKTO(ObjectAdaptorHr.adaptToHrMstTrngProgramPKTO(hrMstTrngProgram.getHrMstTrngProgramPK()));
        hrMstTrngProgramTO.setHrMstProgramTO(ObjectAdaptorHr.adaptToHrMstProgramTO(hrMstTrngProgram.getHrMstProgram()));
        hrMstTrngProgramTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrMstTrngProgram.getHrMstStruct()));
        hrMstTrngProgramTO.setDefaultComp(hrMstTrngProgram.getDefaultComp());
        hrMstTrngProgramTO.setStatFlag(hrMstTrngProgram.getStatFlag());
        hrMstTrngProgramTO.setStatUpFlag(hrMstTrngProgram.getStatUpFlag());
        hrMstTrngProgramTO.setModDate(hrMstTrngProgram.getModDate());
        hrMstTrngProgramTO.setAuthBy(hrMstTrngProgram.getAuthBy());
        hrMstTrngProgramTO.setEnteredBy(hrMstTrngProgram.getEnteredBy());
        return hrMstTrngProgramTO;
    }

    public static HrMstInstitutePK adaptHrMstInstitutePKEntity(HrMstInstitutePKTO hrMstInstitutePKTO) {
        HrMstInstitutePK hrMstInstitutePK = new HrMstInstitutePK();
        hrMstInstitutePK.setCompCode(hrMstInstitutePKTO.getCompCode());
        hrMstInstitutePK.setInstCode(hrMstInstitutePKTO.getInstCode());
        return hrMstInstitutePK;
    }

    public static HrMstInstitutePKTO adaptToHrMstInstitutePKTO(HrMstInstitutePK hrMstInstitutePK) {
        HrMstInstitutePKTO hrMstInstitutePKTO = new HrMstInstitutePKTO();
        hrMstInstitutePKTO.setCompCode(hrMstInstitutePK.getCompCode());
        hrMstInstitutePKTO.setInstCode(hrMstInstitutePK.getInstCode());
        return hrMstInstitutePKTO;
    }

    public static HrMstInstitute adaptToHrMstInstituteEntity(HrMstInstituteTO hrMstInstituteTO) {
        HrMstInstitute hrMstInstitute = new HrMstInstitute();
        hrMstInstitute.setHrMstInstitutePK(ObjectAdaptorHr.adaptHrMstInstitutePKEntity(hrMstInstituteTO.getHrMstInstitutePKTO()));
        hrMstInstitute.setInstName(hrMstInstituteTO.getInstName());
        hrMstInstitute.setInstAdd(hrMstInstituteTO.getInstAdd());
        hrMstInstitute.setContPers(hrMstInstituteTO.getContPers());
        hrMstInstitute.setContNo(hrMstInstituteTO.getContNo());
        hrMstInstitute.setDefaultComp(hrMstInstituteTO.getDefaultComp());
        hrMstInstitute.setStatFlag(hrMstInstituteTO.getStatFlag());
        hrMstInstitute.setStatUpFlag(hrMstInstituteTO.getStatUpFlag());
        hrMstInstitute.setModDate(hrMstInstituteTO.getModDate());
        hrMstInstitute.setEnteredBy(hrMstInstituteTO.getEnteredBy());
        hrMstInstitute.setAuthBy(hrMstInstituteTO.getAuthBy());
        return hrMstInstitute;
    }

    public static HrMstInstituteTO adaptToHrMstInstituteTO(HrMstInstitute hrMstInstitute) {
        HrMstInstituteTO hrMstInstituteTO = new HrMstInstituteTO();
        hrMstInstituteTO.setHrMstInstitutePKTO(ObjectAdaptorHr.adaptToHrMstInstitutePKTO(hrMstInstitute.getHrMstInstitutePK()));
        hrMstInstituteTO.setInstName(hrMstInstitute.getInstName());
        hrMstInstituteTO.setInstAdd(hrMstInstitute.getInstAdd());
        hrMstInstituteTO.setContPers(hrMstInstitute.getContPers());
        hrMstInstituteTO.setContNo(hrMstInstitute.getContNo());
        hrMstInstituteTO.setDefaultComp(hrMstInstitute.getDefaultComp());
        hrMstInstituteTO.setStatFlag(hrMstInstitute.getStatFlag());
        hrMstInstituteTO.setStatUpFlag(hrMstInstitute.getStatUpFlag());
        hrMstInstituteTO.setModDate(hrMstInstitute.getModDate());
        hrMstInstituteTO.setEnteredBy(hrMstInstitute.getEnteredBy());
        hrMstInstituteTO.setAuthBy(hrMstInstitute.getAuthBy());
        return hrMstInstituteTO;
    }

    public static Parameters adaptToParameterEntity(ParametersTO parametersTO) {
        Parameters parameters = new Parameters();
        parameters.setDescription(parametersTO.getDescription());
        return parameters;
    }

    public static ParametersTO adaptToParameterTO(Parameters parameters) {
        ParametersTO parametersTO = new ParametersTO();
        parametersTO.setDescription(parameters.getDescription());
        return parametersTO;

    }
//    public static HrRoleUrlMaster adaptToHrRoleUrlMasterEntity(HrRoleUrlMasterTO hrRoleUrlMasterTO) {
//        HrRoleUrlMaster hrRoleUrlMasterE = new HrRoleUrlMaster();
//
//        hrRoleUrlMasterE.setIdNo(hrRoleUrlMasterTO.getIdNo());
//        hrRoleUrlMasterE.setRoleName(hrRoleUrlMasterTO.getRoleName());
//        hrRoleUrlMasterE.setDisplayName(hrRoleUrlMasterTO.getDisplayName());
//
//        hrRoleUrlMasterE.setUrl(hrRoleUrlMasterTO.getUrl());
//        hrRoleUrlMasterE.setModuleName(hrRoleUrlMasterTO.getModuleName());
//        hrRoleUrlMasterE.setTxnId(hrRoleUrlMasterTO.getTxnId());
//
//        hrRoleUrlMasterE.setParentcode(hrRoleUrlMasterTO.getParentcode());
//        hrRoleUrlMasterE.setSubparentcode(hrRoleUrlMasterTO.getSubparentcode());
//
//        return hrRoleUrlMasterE;
//    }
//
//    public static HrRoleUrlMasterTO adaptToHrRoleUrlMasterTO(HrRoleUrlMaster hrRoleUrlMaster) {
//        HrRoleUrlMasterTO hrRoleUrlMasterTo = new HrRoleUrlMasterTO();
//
//        hrRoleUrlMasterTo.setIdNo(hrRoleUrlMaster.getIdNo());
//        hrRoleUrlMasterTo.setRoleName(hrRoleUrlMaster.getRoleName());
//        hrRoleUrlMasterTo.setDisplayName(hrRoleUrlMaster.getDisplayName());
//
//        hrRoleUrlMasterTo.setUrl(hrRoleUrlMaster.getUrl());
//        hrRoleUrlMasterTo.setModuleName(hrRoleUrlMaster.getModuleName());
//        hrRoleUrlMasterTo.setTxnId(hrRoleUrlMaster.getTxnId());
//
//        hrRoleUrlMasterTo.setParentcode(hrRoleUrlMaster.getParentcode());
//        hrRoleUrlMasterTo.setSubparentcode(hrRoleUrlMaster.getSubparentcode());
//
//        return hrRoleUrlMasterTo;
//    }
    /**
     * *************** END *********************
     */
}
