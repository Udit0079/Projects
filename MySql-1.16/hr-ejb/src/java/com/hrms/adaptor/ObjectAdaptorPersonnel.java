/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.adaptor;

import com.hrms.common.to.ClearSlipDetPKTO;
import com.hrms.common.to.ClearSlipDetTO;
import com.hrms.common.to.ClearSlipHdPKTO;
import com.hrms.common.to.ClearSlipHdTO;
import com.hrms.common.to.HrApprisalDetailsPKTO;
import com.hrms.common.to.HrApprisalDetailsTO;
import com.hrms.common.to.HrContractorDetailsPKTO;
import com.hrms.common.to.HrContractorDetailsTO;
import com.hrms.common.to.HrEmpAdvanceDtPKTO;
import com.hrms.common.to.HrEmpAdvanceDtTO;
import com.hrms.common.to.HrEmpAdvanceHdPKTO;
import com.hrms.common.to.HrEmpAdvanceHdTO;
import com.hrms.common.to.HrEmpLoanDtPKTO;
import com.hrms.common.to.HrEmpLoanDtTO;
import com.hrms.common.to.HrEmpLoanHdPKTO;
import com.hrms.common.to.HrEmpLoanHdTO;
import com.hrms.common.to.HrExitInterviewDtPKTO;
import com.hrms.common.to.HrExitInterviewDtTO;
import com.hrms.common.to.HrExitInterviewHdPKTO;
import com.hrms.common.to.HrExitInterviewHdTO;
import com.hrms.common.to.HrMembershipDetailPKTO;
import com.hrms.common.to.HrMembershipDetailTO;
import com.hrms.common.to.HrOrgChartPKTO;
import com.hrms.common.to.HrOrgChartTO;
import com.hrms.common.to.HrPersonnelDependentPKTO;
import com.hrms.common.to.HrPersonnelDependentTO;
import com.hrms.common.to.HrPersonnelHobbyPKTO;
import com.hrms.common.to.HrPersonnelHobbyTO;
import com.hrms.common.to.HrPersonnelLangPKTO;
import com.hrms.common.to.HrPersonnelLangTO;
import com.hrms.common.to.HrPersonnelPreviousCompanyPKTO;
import com.hrms.common.to.HrPersonnelPreviousCompanyTO;
import com.hrms.common.to.HrPersonnelQualificationPKTO;
import com.hrms.common.to.HrPersonnelQualificationTO;
import com.hrms.common.to.HrPersonnelReferencePKTO;
import com.hrms.common.to.HrPersonnelReferenceTO;
import com.hrms.common.to.HrPersonnelTransportPKTO;
import com.hrms.common.to.HrPersonnelTransportTO;
import com.hrms.common.to.HrPromotionDetailsPKTO;
import com.hrms.common.to.HrPromotionDetailsTO;
import com.hrms.common.to.HrSeparationDetailsPKTO;
import com.hrms.common.to.HrSeparationDetailsTO;
import com.hrms.common.to.HrTempStaffPKTO;
import com.hrms.common.to.HrTempStaffTO;
import com.hrms.common.to.HrTransferDetailsPKTO;
import com.hrms.common.to.HrTransferDetailsTO;
import com.hrms.entity.hr.HrOrgChart;
import com.hrms.entity.hr.HrOrgChartPK;
import com.hrms.entity.personnel.ClearSlipDet;
import com.hrms.entity.personnel.ClearSlipDetPK;
import com.hrms.entity.personnel.ClearSlipHd;
import com.hrms.entity.personnel.ClearSlipHdPK;
import com.hrms.entity.personnel.HrApprisalDetails;
import com.hrms.entity.personnel.HrApprisalDetailsPK;
import com.hrms.entity.personnel.HrContractorDetails;
import com.hrms.entity.personnel.HrContractorDetailsPK;
import com.hrms.entity.personnel.HrEmpAdvanceDt;
import com.hrms.entity.personnel.HrEmpAdvanceDtPK;
import com.hrms.entity.personnel.HrEmpAdvanceHd;
import com.hrms.entity.personnel.HrEmpAdvanceHdPK;
import com.hrms.entity.personnel.HrEmpLoanDt;
import com.hrms.entity.personnel.HrEmpLoanDtPK;
import com.hrms.entity.personnel.HrEmpLoanHd;
import com.hrms.entity.personnel.HrEmpLoanHdPK;
import com.hrms.entity.personnel.HrExitInterviewDt;
import com.hrms.entity.personnel.HrExitInterviewDtPK;
import com.hrms.entity.personnel.HrExitInterviewHd;
import com.hrms.entity.personnel.HrExitInterviewHdPK;
import com.hrms.entity.personnel.HrMembershipDetail;
import com.hrms.entity.personnel.HrMembershipDetailPK;
import com.hrms.entity.personnel.HrPersonnelDependent;
import com.hrms.entity.personnel.HrPersonnelDependentPK;
import com.hrms.entity.personnel.HrPersonnelHobby;
import com.hrms.entity.personnel.HrPersonnelHobbyPK;
import com.hrms.entity.personnel.HrPersonnelLang;
import com.hrms.entity.personnel.HrPersonnelLangPK;
import com.hrms.entity.personnel.HrPersonnelPreviousCompany;
import com.hrms.entity.personnel.HrPersonnelPreviousCompanyPK;
import com.hrms.entity.personnel.HrPersonnelQualification;
import com.hrms.entity.personnel.HrPersonnelQualificationPK;
import com.hrms.entity.personnel.HrPersonnelReference;
import com.hrms.entity.personnel.HrPersonnelReferencePK;
import com.hrms.entity.personnel.HrPersonnelTransport;
import com.hrms.entity.personnel.HrPersonnelTransportPK;
import com.hrms.entity.personnel.HrPromotionDetails;
import com.hrms.entity.personnel.HrPromotionDetailsPK;
import com.hrms.entity.personnel.HrSeparationDetails;
import com.hrms.entity.personnel.HrSeparationDetailsPK;
import com.hrms.entity.personnel.HrTempStaff;
import com.hrms.entity.personnel.HrTempStaffPK;
import com.hrms.entity.personnel.HrTransferDetails;
import com.hrms.entity.personnel.HrTransferDetailsPK;

/**
 *
 * @author Zeeshan Waris
 */
public class ObjectAdaptorPersonnel {

    public static HrTransferDetailsPK adaptToHrTransferDetailsPKEntity(HrTransferDetailsPKTO transferDetailsPKTO) {
        HrTransferDetailsPK hrTransferDetailsPK = new HrTransferDetailsPK();
        hrTransferDetailsPK.setCompCode(transferDetailsPKTO.getCompCode());
        hrTransferDetailsPK.setArNo(transferDetailsPKTO.getArNo());
        return hrTransferDetailsPK;

    }

    public static HrTransferDetailsPKTO adaptToHrTransferDetailsPKTO(HrTransferDetailsPK hrTransferDetailsPK) {
        HrTransferDetailsPKTO hrTransferDetailsPKTO = new HrTransferDetailsPKTO();
        hrTransferDetailsPKTO.setCompCode(hrTransferDetailsPK.getCompCode());
        hrTransferDetailsPKTO.setArNo(hrTransferDetailsPK.getArNo());
        return hrTransferDetailsPKTO;
    }

    public static HrTransferDetails adaptToHrTransferDetailsEntity(HrTransferDetailsTO transferDetailsTO) {
        HrTransferDetails hrTransferDetails = new HrTransferDetails();
        hrTransferDetails.setHrTransferDetailsPK(ObjectAdaptorPersonnel.adaptToHrTransferDetailsPKEntity(transferDetailsTO.getHrTransferDetailsPKTO()));
        hrTransferDetails.setArdate(transferDetailsTO.getArdate());
        hrTransferDetails.setEmpCode(transferDetailsTO.getEmpCode());
        hrTransferDetails.setPresentDesig(transferDetailsTO.getPresentDesig());
        hrTransferDetails.setProposedDesig(transferDetailsTO.getProposedDesig());
        hrTransferDetails.setZoneFrom(transferDetailsTO.getZoneFrom());
        hrTransferDetails.setZoneTo(transferDetailsTO.getZoneTo());
        hrTransferDetails.setBlockFrom(transferDetailsTO.getBlockFrom());
        hrTransferDetails.setBlockTo(transferDetailsTO.getBlockTo());
        hrTransferDetails.setDeptFrom(transferDetailsTO.getDeptFrom());
        hrTransferDetails.setDeptTo(transferDetailsTO.getDeptTo());
        hrTransferDetails.setLocationFrom(transferDetailsTO.getLocationFrom());
        hrTransferDetails.setLocationTo(transferDetailsTO.getLocationTo());
        hrTransferDetails.setEffective(transferDetailsTO.getEffective());
        hrTransferDetails.setTransfer(transferDetailsTO.getTransfer());
        hrTransferDetails.setPf(transferDetailsTO.getPf());
        hrTransferDetails.setEsi(transferDetailsTO.getEsi());
        hrTransferDetails.setSalProcess(transferDetailsTO.getSalProcess());
        hrTransferDetails.setReason(transferDetailsTO.getReason());
        hrTransferDetails.setDeput(transferDetailsTO.getDeput());
        hrTransferDetails.setDefaultComp(transferDetailsTO.getDefaultComp());
        hrTransferDetails.setStatFlag(transferDetailsTO.getStatFlag());
        hrTransferDetails.setStatUpFlag(transferDetailsTO.getStatUpFlag());
        hrTransferDetails.setModDate(transferDetailsTO.getModDate());
        hrTransferDetails.setAuthBy(transferDetailsTO.getAuthBy());
        hrTransferDetails.setEnteredBy(transferDetailsTO.getEnteredBy());
        return hrTransferDetails;
    }

    public static HrTransferDetailsTO adaptToStructMasterTO(HrTransferDetails hrTransferDetails) {
        HrTransferDetailsTO hrTransferDetailsTO = new HrTransferDetailsTO();
        hrTransferDetailsTO.setHrTransferDetailsPKTO(ObjectAdaptorPersonnel.adaptToHrTransferDetailsPKTO(hrTransferDetails.getHrTransferDetailsPK()));
        hrTransferDetailsTO.setArdate(hrTransferDetails.getArdate());
        hrTransferDetailsTO.setEmpCode(hrTransferDetails.getEmpCode());
        hrTransferDetailsTO.setPresentDesig(hrTransferDetails.getPresentDesig());
        hrTransferDetailsTO.setProposedDesig(hrTransferDetails.getProposedDesig());
        hrTransferDetailsTO.setZoneFrom(hrTransferDetails.getZoneFrom());
        hrTransferDetailsTO.setZoneTo(hrTransferDetails.getZoneTo());
        hrTransferDetailsTO.setBlockFrom(hrTransferDetails.getBlockFrom());
        hrTransferDetailsTO.setBlockTo(hrTransferDetails.getBlockTo());
        hrTransferDetailsTO.setDeptFrom(hrTransferDetails.getDeptFrom());
        hrTransferDetailsTO.setDeptTo(hrTransferDetails.getDeptTo());
        hrTransferDetailsTO.setLocationFrom(hrTransferDetails.getLocationFrom());
        hrTransferDetailsTO.setLocationTo(hrTransferDetails.getLocationTo());
        hrTransferDetailsTO.setEffective(hrTransferDetails.getEffective());
        hrTransferDetailsTO.setTransfer(hrTransferDetails.getTransfer());
        hrTransferDetailsTO.setPf(hrTransferDetails.getPf());
        hrTransferDetailsTO.setEsi(hrTransferDetails.getEsi());
        hrTransferDetailsTO.setSalProcess(hrTransferDetails.getSalProcess());
        hrTransferDetailsTO.setReason(hrTransferDetails.getReason());
        hrTransferDetailsTO.setDeput(hrTransferDetails.getDeput());
        hrTransferDetailsTO.setDefaultComp(hrTransferDetails.getDefaultComp());
        hrTransferDetailsTO.setStatFlag(hrTransferDetails.getStatFlag());
        hrTransferDetailsTO.setStatUpFlag(hrTransferDetails.getStatUpFlag());
        hrTransferDetailsTO.setModDate(hrTransferDetails.getModDate());
        hrTransferDetailsTO.setAuthBy(hrTransferDetails.getAuthBy());
        hrTransferDetailsTO.setEnteredBy(hrTransferDetails.getEnteredBy());
        return hrTransferDetailsTO;
    }

    public static HrTempStaffPK adaptToHrTempStaffPKEntity(HrTempStaffPKTO HrTempStaffPKTO) {
        HrTempStaffPK HrTempStaffPK = new HrTempStaffPK();
        HrTempStaffPK.setCompCode(HrTempStaffPKTO.getCompCode());
        HrTempStaffPK.setArNo(HrTempStaffPKTO.getArNo());
        return HrTempStaffPK;

    }

    public static HrTempStaffPKTO adaptToHrTempStaffPKTO(HrTempStaffPK hrTempStaffPK) {
        HrTempStaffPKTO HrTempStaffPKTO = new HrTempStaffPKTO();
        HrTempStaffPKTO.setCompCode(hrTempStaffPK.getCompCode());
        HrTempStaffPKTO.setArNo(hrTempStaffPK.getArNo());
        return HrTempStaffPKTO;
    }

    public static HrTempStaff adaptToHrTempStaffEntity(HrTempStaffTO hrTempStaffTO) {
        HrTempStaff hrTempStaff = new HrTempStaff();
        hrTempStaff.setHrTempStaffPK(ObjectAdaptorPersonnel.adaptToHrTempStaffPKEntity(hrTempStaffTO.getHrTempStaffPKTO()));
        hrTempStaff.setContCode(hrTempStaffTO.getContCode());
        hrTempStaff.setEmpName(hrTempStaffTO.getEmpName());
        hrTempStaff.setDesgCode(hrTempStaffTO.getDesgCode());
        hrTempStaff.setZone(hrTempStaffTO.getZone());
        hrTempStaff.setLocatCode(hrTempStaffTO.getLocatCode());
        hrTempStaff.setFromDate(hrTempStaffTO.getFromDate());
        hrTempStaff.setToDate(hrTempStaffTO.getToDate());
        hrTempStaff.setWages(hrTempStaffTO.getWages());
        hrTempStaff.setActive(hrTempStaffTO.getActive());
        hrTempStaff.setDefaultComp(hrTempStaffTO.getDefaultComp());
        hrTempStaff.setStatFlag(hrTempStaffTO.getStatFlag());
        hrTempStaff.setStatUpFlag(hrTempStaffTO.getStatUpFlag());
        hrTempStaff.setModDate(hrTempStaffTO.getModDate());
        hrTempStaff.setAuthBy(hrTempStaffTO.getAuthBy());
        hrTempStaff.setEnteredBy(hrTempStaffTO.getEnteredBy());
        return hrTempStaff;
    }

    public static HrTempStaffTO adaptToHrTempStaffTO(HrTempStaff hrTempStaff) {
        HrTempStaffTO hrTempStaffTO = new HrTempStaffTO();
        hrTempStaffTO.setHrTempStaffPKTO(ObjectAdaptorPersonnel.adaptToHrTempStaffPKTO(hrTempStaff.getHrTempStaffPK()));
        hrTempStaffTO.setContCode(hrTempStaff.getContCode());
        hrTempStaffTO.setEmpName(hrTempStaff.getEmpName());
        hrTempStaffTO.setDesgCode(hrTempStaff.getDesgCode());
        hrTempStaffTO.setZone(hrTempStaff.getZone());
        hrTempStaffTO.setLocatCode(hrTempStaff.getLocatCode());
        hrTempStaffTO.setFromDate(hrTempStaff.getFromDate());
        hrTempStaffTO.setToDate(hrTempStaff.getToDate());
        hrTempStaffTO.setWages(hrTempStaff.getWages());
        hrTempStaffTO.setActive(hrTempStaff.getActive());
        hrTempStaffTO.setDefaultComp(hrTempStaff.getDefaultComp());
        hrTempStaffTO.setStatFlag(hrTempStaff.getStatFlag());
        hrTempStaffTO.setStatUpFlag(hrTempStaff.getStatUpFlag());
        hrTempStaffTO.setModDate(hrTempStaff.getModDate());
        hrTempStaffTO.setAuthBy(hrTempStaff.getAuthBy());
        hrTempStaffTO.setEnteredBy(hrTempStaff.getEnteredBy());
        return hrTempStaffTO;
    }

    public static HrContractorDetailsPK adaptToHrContractorDetailsPKEntity(HrContractorDetailsPKTO contractorDetailsPKTO) {
        HrContractorDetailsPK hrContractorDetailsPK = new HrContractorDetailsPK();
        hrContractorDetailsPK.setCompCode(contractorDetailsPKTO.getCompCode());
        hrContractorDetailsPK.setContCode(contractorDetailsPKTO.getContCode());
        return hrContractorDetailsPK;

    }

    public static HrContractorDetailsPKTO adaptToHrContractorDetailsPKTO(HrContractorDetailsPK hrContractorDetailsPK) {
        HrContractorDetailsPKTO hrContractorDetailsPKTO = new HrContractorDetailsPKTO();
        hrContractorDetailsPKTO.setCompCode(hrContractorDetailsPK.getCompCode());
        hrContractorDetailsPKTO.setContCode(hrContractorDetailsPK.getContCode());
        return hrContractorDetailsPKTO;
    }

    public static HrContractorDetails adaptToHrContractorDetailsEntity(HrContractorDetailsTO hrContractorDetailsTO) {
        HrContractorDetails hrContractorDetails = new HrContractorDetails();
        hrContractorDetails.setHrContractorDetailsPK(ObjectAdaptorPersonnel.adaptToHrContractorDetailsPKEntity(hrContractorDetailsTO.getHrContractorDetailsPKTO()));
        hrContractorDetails.setContFirName(hrContractorDetailsTO.getContFirName());
        hrContractorDetails.setContMidName(hrContractorDetailsTO.getContMidName());
        hrContractorDetails.setContLastName(hrContractorDetailsTO.getContLastName());
        hrContractorDetails.setContAddress(hrContractorDetailsTO.getContAddress());
        hrContractorDetails.setContCity(hrContractorDetailsTO.getContCity());
        hrContractorDetails.setContPin(hrContractorDetailsTO.getContPin());
        hrContractorDetails.setContState(hrContractorDetailsTO.getContState());
        hrContractorDetails.setFaxNumber(hrContractorDetailsTO.getFaxNumber());
        hrContractorDetails.setMobileNumber(hrContractorDetailsTO.getMobileNumber());
        hrContractorDetails.setTeleNumber(hrContractorDetailsTO.getTeleNumber());
        hrContractorDetails.setResiNumber(hrContractorDetailsTO.getResiNumber());
        hrContractorDetails.setEmailNumber(hrContractorDetailsTO.getEmailNumber());
        hrContractorDetails.setDefaultComp(hrContractorDetailsTO.getDefaultComp());
        hrContractorDetails.setStatFlag(hrContractorDetailsTO.getStatFlag());
        hrContractorDetails.setStatUpFlag(hrContractorDetailsTO.getStatUpFlag());
        hrContractorDetails.setModDate(hrContractorDetailsTO.getModDate());
        hrContractorDetails.setAuthBy(hrContractorDetailsTO.getAuthBy());
        hrContractorDetails.setEnteredBy(hrContractorDetailsTO.getEnteredBy());
        return hrContractorDetails;
    }

    public static HrContractorDetailsTO adaptToHrContractorDetailsTO(HrContractorDetails hrContractorDetails) {
        HrContractorDetailsTO HrContractorDetailsTO = new HrContractorDetailsTO();
        HrContractorDetailsTO.setHrContractorDetailsPKTO(ObjectAdaptorPersonnel.adaptToHrContractorDetailsPKTO(hrContractorDetails.getHrContractorDetailsPK()));
        HrContractorDetailsTO.setContFirName(hrContractorDetails.getContFirName());
        HrContractorDetailsTO.setContMidName(hrContractorDetails.getContMidName());
        HrContractorDetailsTO.setContLastName(hrContractorDetails.getContLastName());
        HrContractorDetailsTO.setContAddress(hrContractorDetails.getContAddress());
        HrContractorDetailsTO.setContCity(hrContractorDetails.getContCity());
        HrContractorDetailsTO.setContPin(hrContractorDetails.getContPin());
        HrContractorDetailsTO.setContState(hrContractorDetails.getContState());
        HrContractorDetailsTO.setFaxNumber(hrContractorDetails.getFaxNumber());
        HrContractorDetailsTO.setMobileNumber(hrContractorDetails.getMobileNumber());
        HrContractorDetailsTO.setTeleNumber(hrContractorDetails.getTeleNumber());
        HrContractorDetailsTO.setResiNumber(hrContractorDetails.getResiNumber());
        HrContractorDetailsTO.setEmailNumber(hrContractorDetails.getEmailNumber());
        HrContractorDetailsTO.setDefaultComp(hrContractorDetails.getDefaultComp());
        HrContractorDetailsTO.setStatFlag(hrContractorDetails.getStatFlag());
        HrContractorDetailsTO.setStatUpFlag(hrContractorDetails.getStatUpFlag());
        HrContractorDetailsTO.setModDate(hrContractorDetails.getModDate());
        HrContractorDetailsTO.setAuthBy(hrContractorDetails.getAuthBy());
        HrContractorDetailsTO.setEnteredBy(hrContractorDetails.getEnteredBy());
        return HrContractorDetailsTO;
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
        hrOrgChart.setHrOrgChartPK(ObjectAdaptorPersonnel.adaptToHrOrgChartPKEntity(hrOrgChartTO.getHrOrgChartPKTO()));
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
        hrOrgChartTO.setHrOrgChartPKTO(ObjectAdaptorPersonnel.adaptToHrOrgChartPKTO(hrOrgChart.getHrOrgChartPK()));
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

////////////////////////////////////HrEmpAdvance///////////////////////////////////////////////
    public static HrEmpAdvanceDt adaptToHrEmpAdvanceDtEntity(HrEmpAdvanceDtTO hrEmpAdvanceDtTO) {
        HrEmpAdvanceDt hrEmpAdvanceDt = new HrEmpAdvanceDt();
        HrEmpAdvanceDtPK hrEmpAdvanceDtPK = new HrEmpAdvanceDtPK();
        hrEmpAdvanceDtPK.setAdvance(hrEmpAdvanceDtTO.getHrEmpAdvanceDtPKTO().getAdvance());
        hrEmpAdvanceDtPK.setCompCode(hrEmpAdvanceDtTO.getHrEmpAdvanceDtPKTO().getCompCode());
        hrEmpAdvanceDtPK.setDueDate(hrEmpAdvanceDtTO.getHrEmpAdvanceDtPKTO().getDueDate());
        hrEmpAdvanceDtPK.setEmpAdvNo(hrEmpAdvanceDtTO.getHrEmpAdvanceDtPKTO().getEmpAdvNo());
        hrEmpAdvanceDtPK.setEmpCode(hrEmpAdvanceDtTO.getHrEmpAdvanceDtPKTO().getEmpCode());

        hrEmpAdvanceDt.setHrEmpAdvanceDtPK(hrEmpAdvanceDtPK);
        hrEmpAdvanceDt.setAmount(hrEmpAdvanceDtTO.getAmount());
        hrEmpAdvanceDt.setAuthBy(hrEmpAdvanceDtTO.getAuthBy());
        hrEmpAdvanceDt.setDefaultComp(hrEmpAdvanceDtTO.getDefaultComp());
        hrEmpAdvanceDt.setEnteredBy(hrEmpAdvanceDtTO.getEnteredBy());

//        hrEmpAdvanceDt.setHrEmpAdvanceHd(ObjectAdaptorPersonnel.adaptToHrEmpAdvanceHdEntity(hrEmpAdvanceDtTO.getHrEmpAdvanceHdTO()));
        hrEmpAdvanceDt.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrEmpAdvanceDtTO.getHrMstStructTO()));
        hrEmpAdvanceDt.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrEmpAdvanceDtTO.getHrPersonnelDetailsTO()));

        hrEmpAdvanceDt.setModDate(hrEmpAdvanceDtTO.getModDate());
        hrEmpAdvanceDt.setRepayFlag(hrEmpAdvanceDtTO.getRepayFlag());
        hrEmpAdvanceDt.setStatFlag(hrEmpAdvanceDtTO.getStatFlag());
        hrEmpAdvanceDt.setStatUpFlag(hrEmpAdvanceDtTO.getStatUpFlag());
        hrEmpAdvanceDt.setTotInstall(hrEmpAdvanceDtTO.getTotInstall());
        hrEmpAdvanceDt.setVarInstall(hrEmpAdvanceDtTO.getVarInstall());
        return hrEmpAdvanceDt;
    }

    public static HrEmpAdvanceDtTO adaptToHrEmpAdvanceDtTO(HrEmpAdvanceDt hrEmpAdvanceDt) {

        HrEmpAdvanceDtTO hrEmpAdvanceDtTO = new HrEmpAdvanceDtTO();
        HrEmpAdvanceDtPKTO hrEmpAdvanceDtPKTO = new HrEmpAdvanceDtPKTO();
        hrEmpAdvanceDtPKTO.setAdvance(hrEmpAdvanceDt.getHrEmpAdvanceDtPK().getAdvance());
        hrEmpAdvanceDtPKTO.setCompCode(hrEmpAdvanceDt.getHrEmpAdvanceDtPK().getCompCode());
        hrEmpAdvanceDtPKTO.setDueDate(hrEmpAdvanceDt.getHrEmpAdvanceDtPK().getDueDate());
        hrEmpAdvanceDtPKTO.setEmpAdvNo(hrEmpAdvanceDt.getHrEmpAdvanceDtPK().getEmpAdvNo());
        hrEmpAdvanceDtPKTO.setEmpCode(hrEmpAdvanceDt.getHrEmpAdvanceDtPK().getEmpCode());
        hrEmpAdvanceDtTO.setHrEmpAdvanceDtPKTO(hrEmpAdvanceDtPKTO);
        hrEmpAdvanceDtTO.setAmount(hrEmpAdvanceDt.getAmount());
        hrEmpAdvanceDtTO.setAuthBy(hrEmpAdvanceDt.getAuthBy());
        hrEmpAdvanceDtTO.setDefaultComp(hrEmpAdvanceDt.getDefaultComp());
        hrEmpAdvanceDtTO.setEnteredBy(hrEmpAdvanceDt.getEnteredBy());

//        hrEmpAdvanceDtTO.setHrEmpAdvanceHdTO(ObjectAdaptorPersonnel.adaptToHrEmpAdvanceHdTO(hrEmpAdvanceDt.getHrEmpAdvanceHd()));
        hrEmpAdvanceDtTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrEmpAdvanceDt.getHrMstStruct()));
        hrEmpAdvanceDtTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrEmpAdvanceDt.getHrPersonnelDetails()));

        hrEmpAdvanceDtTO.setModDate(hrEmpAdvanceDt.getModDate());
        hrEmpAdvanceDtTO.setRepayFlag(hrEmpAdvanceDt.getRepayFlag());
        hrEmpAdvanceDtTO.setStatFlag(hrEmpAdvanceDt.getStatFlag());
        hrEmpAdvanceDtTO.setStatUpFlag(hrEmpAdvanceDt.getStatUpFlag());
        hrEmpAdvanceDtTO.setTotInstall(hrEmpAdvanceDt.getTotInstall());
        hrEmpAdvanceDtTO.setVarInstall(hrEmpAdvanceDt.getVarInstall());
        return hrEmpAdvanceDtTO;
    }

    public static HrEmpAdvanceHd adaptToHrEmpAdvanceHdEntity(HrEmpAdvanceHdTO hrEmpAdvanceHdTO) {
        HrEmpAdvanceHd hrEmpAdvanceHd = new HrEmpAdvanceHd();
        HrEmpAdvanceHdPK hrEmpAdvanceHdPK = new HrEmpAdvanceHdPK();
        hrEmpAdvanceHdPK.setAdvance(hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getAdvance());
        hrEmpAdvanceHdPK.setCompCode(hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getCompCode());
        hrEmpAdvanceHdPK.setEmpAdvNo(hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getEmpAdvNo());
        hrEmpAdvanceHdPK.setEmpCode(hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getEmpCode());
        hrEmpAdvanceHd.setHrEmpAdvanceHdPK(hrEmpAdvanceHdPK);
        hrEmpAdvanceHd.setAuthBy(hrEmpAdvanceHdTO.getAuthBy());
        hrEmpAdvanceHd.setDefaultComp(hrEmpAdvanceHdTO.getDefaultComp());
        hrEmpAdvanceHd.setEnteredBy(hrEmpAdvanceHdTO.getEnteredBy());
        hrEmpAdvanceHd.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrEmpAdvanceHdTO.getHrMstStructTO()));
        hrEmpAdvanceHd.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrEmpAdvanceHdTO.getHrPersonnelDetailsTO()));
        hrEmpAdvanceHd.setModDate(hrEmpAdvanceHdTO.getModDate());
        hrEmpAdvanceHd.setNoInstall(hrEmpAdvanceHdTO.getNoInstall());
        hrEmpAdvanceHd.setPeriodicity(hrEmpAdvanceHdTO.getPeriodicity());
        hrEmpAdvanceHd.setSanctionAmt(hrEmpAdvanceHdTO.getSanctionAmt());
        hrEmpAdvanceHd.setSanctionDate(hrEmpAdvanceHdTO.getSanctionDate());
        hrEmpAdvanceHd.setStartDate(hrEmpAdvanceHdTO.getStartDate());
        hrEmpAdvanceHd.setRepayFlag(hrEmpAdvanceHdTO.getRepayFlag());
        hrEmpAdvanceHd.setStatFlag(hrEmpAdvanceHdTO.getStatFlag());
        hrEmpAdvanceHd.setStatUpFlag(hrEmpAdvanceHdTO.getStatUpFlag());
        return hrEmpAdvanceHd;
    }

    public static HrEmpAdvanceHdTO adaptToHrEmpAdvanceHdTO(HrEmpAdvanceHd hrEmpAdvanceHd) {

        HrEmpAdvanceHdTO hrEmpAdvanceHdTO = new HrEmpAdvanceHdTO();
        HrEmpAdvanceHdPKTO hrEmpAdvanceHdPKTO = new HrEmpAdvanceHdPKTO();
        hrEmpAdvanceHdPKTO.setAdvance(hrEmpAdvanceHd.getHrEmpAdvanceHdPK().getAdvance());
        hrEmpAdvanceHdPKTO.setCompCode(hrEmpAdvanceHd.getHrEmpAdvanceHdPK().getCompCode());
        hrEmpAdvanceHdPKTO.setEmpAdvNo(hrEmpAdvanceHd.getHrEmpAdvanceHdPK().getEmpAdvNo());
        hrEmpAdvanceHdPKTO.setEmpCode(hrEmpAdvanceHd.getHrEmpAdvanceHdPK().getEmpCode());
        hrEmpAdvanceHdTO.setHrEmpAdvanceHdPKTO(hrEmpAdvanceHdPKTO);
        hrEmpAdvanceHdTO.setAuthBy(hrEmpAdvanceHd.getAuthBy());
        hrEmpAdvanceHdTO.setDefaultComp(hrEmpAdvanceHd.getDefaultComp());
        hrEmpAdvanceHdTO.setEnteredBy(hrEmpAdvanceHd.getEnteredBy());
        hrEmpAdvanceHdTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrEmpAdvanceHd.getHrMstStruct()));
        hrEmpAdvanceHdTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrEmpAdvanceHd.getHrPersonnelDetails()));
        hrEmpAdvanceHdTO.setModDate(hrEmpAdvanceHd.getModDate());
        hrEmpAdvanceHdTO.setNoInstall(hrEmpAdvanceHd.getNoInstall());
        hrEmpAdvanceHdTO.setPeriodicity(hrEmpAdvanceHd.getPeriodicity());
        hrEmpAdvanceHdTO.setSanctionAmt(hrEmpAdvanceHd.getSanctionAmt());
        hrEmpAdvanceHdTO.setSanctionDate(hrEmpAdvanceHd.getSanctionDate());
        hrEmpAdvanceHdTO.setStartDate(hrEmpAdvanceHd.getStartDate());
        hrEmpAdvanceHdTO.setRepayFlag(hrEmpAdvanceHd.getRepayFlag());
        hrEmpAdvanceHdTO.setStatFlag(hrEmpAdvanceHd.getStatFlag());
        hrEmpAdvanceHdTO.setStatUpFlag(hrEmpAdvanceHd.getStatUpFlag());
        return hrEmpAdvanceHdTO;
    }

////////////////////////////////////HrEmpLoan///////////////////////////////////////////////
    public static HrEmpLoanDt adaptToHrEmpLoanDtEntity(HrEmpLoanDtTO hrEmpLoanDtTO) {
        HrEmpLoanDt hrEmpLoanDt = new HrEmpLoanDt();
        HrEmpLoanDtPK hrEmpLoanDtPK = new HrEmpLoanDtPK();
        hrEmpLoanDtPK.setLoanType(hrEmpLoanDtTO.getHrEmpLoanDtPKTO().getLoanType());
        hrEmpLoanDtPK.setCompCode(hrEmpLoanDtTO.getHrEmpLoanDtPKTO().getCompCode());
        hrEmpLoanDtPK.setDueDate(hrEmpLoanDtTO.getHrEmpLoanDtPKTO().getDueDate());
        hrEmpLoanDtPK.setEmpLoanNo(hrEmpLoanDtTO.getHrEmpLoanDtPKTO().getEmpLoanNo());
        hrEmpLoanDtPK.setEmpCode(hrEmpLoanDtTO.getHrEmpLoanDtPKTO().getEmpCode());

        hrEmpLoanDt.setHrEmpLoanDtPK(hrEmpLoanDtPK);
        hrEmpLoanDt.setAuthBy(hrEmpLoanDtTO.getAuthBy());
        hrEmpLoanDt.setDefaultComp(hrEmpLoanDtTO.getDefaultComp());
        hrEmpLoanDt.setEnteredBy(hrEmpLoanDtTO.getEnteredBy());
        hrEmpLoanDt.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrEmpLoanDtTO.getHrMstStructTO()));
        hrEmpLoanDt.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrEmpLoanDtTO.getHrPersonnelDetailsTO()));
        hrEmpLoanDt.setInterest(hrEmpLoanDtTO.getInterest());
        hrEmpLoanDt.setModDate(hrEmpLoanDtTO.getModDate());
        hrEmpLoanDt.setPrincipal(hrEmpLoanDtTO.getPrincipal());
//        hrEmpLoanDt.setHrEmpLoanHd(ObjectAdaptorPersonnel.adaptToHrEmpLoanHdEntity(hrEmpLoanDtTO.getHrEmpLoanHdTO()));
        hrEmpLoanDt.setRepayFlag(hrEmpLoanDtTO.getRepayFlag());
        hrEmpLoanDt.setStatFlag(hrEmpLoanDtTO.getStatFlag());
        hrEmpLoanDt.setStatUpFlag(hrEmpLoanDtTO.getStatUpFlag());
        hrEmpLoanDt.setTotInstall(hrEmpLoanDtTO.getTotInstall());
        hrEmpLoanDt.setVarInstall(hrEmpLoanDtTO.getVarInstall());
        return hrEmpLoanDt;
    }

    public static HrEmpLoanDtTO adaptToHrEmpLoanDtTO(HrEmpLoanDt hrEmpLoanDt) {

        HrEmpLoanDtTO hrEmpLoanDtTO = new HrEmpLoanDtTO();
        HrEmpLoanDtPKTO hrEmpLoanDtPKTO = new HrEmpLoanDtPKTO();
        hrEmpLoanDtPKTO.setLoanType(hrEmpLoanDt.getHrEmpLoanDtPK().getLoanType());
        hrEmpLoanDtPKTO.setCompCode(hrEmpLoanDt.getHrEmpLoanDtPK().getCompCode());
        hrEmpLoanDtPKTO.setDueDate(hrEmpLoanDt.getHrEmpLoanDtPK().getDueDate());
        hrEmpLoanDtPKTO.setEmpLoanNo(hrEmpLoanDt.getHrEmpLoanDtPK().getEmpLoanNo());
        hrEmpLoanDtPKTO.setEmpCode(hrEmpLoanDt.getHrEmpLoanDtPK().getEmpCode());
        hrEmpLoanDtTO.setHrEmpLoanDtPKTO(hrEmpLoanDtPKTO);
        hrEmpLoanDtTO.setAuthBy(hrEmpLoanDt.getAuthBy());
        hrEmpLoanDtTO.setDefaultComp(hrEmpLoanDt.getDefaultComp());
        hrEmpLoanDtTO.setEnteredBy(hrEmpLoanDt.getEnteredBy());
//        hrEmpLoanDtTO.setHrEmpLoanHdTO(ObjectAdaptorPersonnel.adaptToHrEmpLoanHdTO(hrEmpLoanDt.getHrEmpLoanHd()));
        hrEmpLoanDtTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrEmpLoanDt.getHrMstStruct()));
        hrEmpLoanDtTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrEmpLoanDt.getHrPersonnelDetails()));
        hrEmpLoanDtTO.setInterest(hrEmpLoanDt.getInterest());
        hrEmpLoanDtTO.setModDate(hrEmpLoanDt.getModDate());
        hrEmpLoanDtTO.setPrincipal(hrEmpLoanDt.getPrincipal());
        hrEmpLoanDtTO.setRepayFlag(hrEmpLoanDt.getRepayFlag());
        hrEmpLoanDtTO.setStatFlag(hrEmpLoanDt.getStatFlag());
        hrEmpLoanDtTO.setStatUpFlag(hrEmpLoanDt.getStatUpFlag());
        hrEmpLoanDtTO.setTotInstall(hrEmpLoanDt.getTotInstall());
        hrEmpLoanDtTO.setVarInstall(hrEmpLoanDt.getVarInstall());
        return hrEmpLoanDtTO;
    }

    public static HrEmpLoanHd adaptToHrEmpLoanHdEntity(HrEmpLoanHdTO hrEmpLoanHdTO) {
        HrEmpLoanHd hrEmpLoanHd = new HrEmpLoanHd();
        HrEmpLoanHdPK hrEmpLoanHdPK = new HrEmpLoanHdPK();
        hrEmpLoanHdPK.setLoanType(hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getLoanType());
        hrEmpLoanHdPK.setCompCode(hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getCompCode());
        hrEmpLoanHdPK.setEmpLoanNo(hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getEmpLoanNo());
        hrEmpLoanHdPK.setEmpCode(hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getEmpCode());

        hrEmpLoanHd.setAuthBy(hrEmpLoanHdTO.getAuthBy());
        hrEmpLoanHd.setDefaultComp(hrEmpLoanHdTO.getDefaultComp());
        hrEmpLoanHd.setEnteredBy(hrEmpLoanHdTO.getEnteredBy());
        hrEmpLoanHd.setHrEmpLoanHdPK(hrEmpLoanHdPK);
        hrEmpLoanHd.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrEmpLoanHdTO.getHrMstStructTO()));
        hrEmpLoanHd.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrEmpLoanHdTO.getHrPersonnelDetailsTO()));
        hrEmpLoanHd.setInstPlan(hrEmpLoanHdTO.getInstPlan());
        hrEmpLoanHd.setModDate(hrEmpLoanHdTO.getModDate());
        hrEmpLoanHd.setNoInst(hrEmpLoanHdTO.getNoInst());
        hrEmpLoanHd.setPeriodicity(hrEmpLoanHdTO.getPeriodicity());
        hrEmpLoanHd.setRepayFlag(hrEmpLoanHdTO.getRepayFlag());
        hrEmpLoanHd.setRoi(hrEmpLoanHdTO.getRoi());
        hrEmpLoanHd.setSanctionAmt(hrEmpLoanHdTO.getSanctionAmt());
        hrEmpLoanHd.setSanctionDate(hrEmpLoanHdTO.getSanctionDate());
        hrEmpLoanHd.setStartDate(hrEmpLoanHdTO.getStartDate());
        hrEmpLoanHd.setStatFlag(hrEmpLoanHdTO.getStatFlag());
        hrEmpLoanHd.setStatUpFlag(hrEmpLoanHdTO.getStatUpFlag());
        return hrEmpLoanHd;
    }

    public static HrEmpLoanHdTO adaptToHrEmpLoanHdTO(HrEmpLoanHd hrEmpLoanHd) {
        HrEmpLoanHdTO hrEmpLoanHdTO = new HrEmpLoanHdTO();
        HrEmpLoanHdPKTO hrEmpLoanHdPKTO = new HrEmpLoanHdPKTO();
        hrEmpLoanHdPKTO.setLoanType(hrEmpLoanHd.getHrEmpLoanHdPK().getLoanType());
        hrEmpLoanHdPKTO.setCompCode(hrEmpLoanHd.getHrEmpLoanHdPK().getCompCode());
        hrEmpLoanHdPKTO.setEmpLoanNo(hrEmpLoanHd.getHrEmpLoanHdPK().getEmpLoanNo());
        hrEmpLoanHdPKTO.setEmpCode(hrEmpLoanHd.getHrEmpLoanHdPK().getEmpCode());
        hrEmpLoanHdTO.setAuthBy(hrEmpLoanHd.getAuthBy());
        hrEmpLoanHdTO.setDefaultComp(hrEmpLoanHd.getDefaultComp());
        hrEmpLoanHdTO.setEnteredBy(hrEmpLoanHd.getEnteredBy());
        hrEmpLoanHdTO.setHrEmpLoanHdPKTO(hrEmpLoanHdPKTO);
        hrEmpLoanHdTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrEmpLoanHd.getHrMstStruct()));
        hrEmpLoanHdTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrEmpLoanHd.getHrPersonnelDetails()));
        hrEmpLoanHdTO.setInstPlan(hrEmpLoanHd.getInstPlan());
        hrEmpLoanHdTO.setModDate(hrEmpLoanHd.getModDate());
        hrEmpLoanHdTO.setNoInst(hrEmpLoanHd.getNoInst());
        hrEmpLoanHdTO.setPeriodicity(hrEmpLoanHd.getPeriodicity());
        hrEmpLoanHdTO.setRepayFlag(hrEmpLoanHd.getRepayFlag());
        hrEmpLoanHdTO.setRoi(hrEmpLoanHd.getRoi());
        hrEmpLoanHdTO.setSanctionAmt(hrEmpLoanHd.getSanctionAmt());
        hrEmpLoanHdTO.setSanctionDate(hrEmpLoanHd.getSanctionDate());
        hrEmpLoanHdTO.setStartDate(hrEmpLoanHd.getStartDate());
        hrEmpLoanHdTO.setStatFlag(hrEmpLoanHd.getStatFlag());
        hrEmpLoanHdTO.setStatUpFlag(hrEmpLoanHd.getStatUpFlag());
        return hrEmpLoanHdTO;
    }

    ////////////////////////
    public static HrSeparationDetails adaptToPersonnelSeprationEntity(HrSeparationDetailsTO hrSeparationDetailsTO) {
        HrSeparationDetails hrSeparationDetails = new HrSeparationDetails();
        hrSeparationDetails.setAuthBy(hrSeparationDetailsTO.getAuthBy());
        hrSeparationDetails.setDefaultComp(hrSeparationDetailsTO.getDefaultComp());
        hrSeparationDetails.setEnteredBy(hrSeparationDetailsTO.getEnteredBy());
        hrSeparationDetails.setLoanFlg(hrSeparationDetailsTO.getLoanFlg());
        hrSeparationDetails.setLoanRecovery(hrSeparationDetailsTO.getLoanRecovery());
        hrSeparationDetails.setModDate(hrSeparationDetailsTO.getModDate());
        hrSeparationDetails.setNetDues(hrSeparationDetailsTO.getNetDues());
        hrSeparationDetails.setNoticeGvn(hrSeparationDetailsTO.getNoticeGvn());
        hrSeparationDetails.setNoticePay(hrSeparationDetailsTO.getNoticePay());
        hrSeparationDetails.setNoticePrd(hrSeparationDetailsTO.getNoticePrd());
        hrSeparationDetails.setPrdRecPay(hrSeparationDetailsTO.getPrdRecPay());
        hrSeparationDetails.setReason(hrSeparationDetailsTO.getReason());
        hrSeparationDetails.setResigAccept(hrSeparationDetailsTO.getResigAccept());
        hrSeparationDetails.setResignation(hrSeparationDetailsTO.getResignation());
        hrSeparationDetails.setSepaCode(hrSeparationDetailsTO.getSepaCode());
        hrSeparationDetails.setSeparation(hrSeparationDetailsTO.getSeparation());
        hrSeparationDetails.setStatFlag(hrSeparationDetailsTO.getStatFlag());
        hrSeparationDetails.setStatUpFlag(hrSeparationDetailsTO.getStatUpFlag());
        hrSeparationDetails.setUpdatedBy(hrSeparationDetailsTO.getUpdatedBy());
        hrSeparationDetails.setUpdatedOn(hrSeparationDetailsTO.getUpdatedOn());
        hrSeparationDetails.setHrSeparationDetailsPK(adaptPKToPersonnelSeprationEntityPk(hrSeparationDetailsTO.getHrSeparationDetailsPK()));
        return hrSeparationDetails;
    }

    public static HrSeparationDetailsPK adaptPKToPersonnelSeprationEntityPk(HrSeparationDetailsPKTO hrSeparationDetailsPKTO) {
        HrSeparationDetailsPK hrSeparationDetailsPK = new HrSeparationDetailsPK();
        hrSeparationDetailsPK.setCompCode(hrSeparationDetailsPKTO.getCompCode());
        hrSeparationDetailsPK.setEmpCode(hrSeparationDetailsPKTO.getEmpCode());
        return hrSeparationDetailsPK;
    }

    public static HrApprisalDetails addaptEntityHrApprisalDetailsTo(HrApprisalDetailsTO hrApprisalDetailsTO) {
        HrApprisalDetails hrApprisalDetails = new HrApprisalDetails();
        hrApprisalDetails.setHrApprisalDetailsPK(addaptEntityPkHrApprisalDetailsPK(hrApprisalDetailsTO.getHrApprisalDetailsPKTO()));
        hrApprisalDetails.setApprResult(hrApprisalDetailsTO.getApprResult());
        hrApprisalDetails.setRatingCode(hrApprisalDetailsTO.getRatingCode());
        hrApprisalDetails.setTrngCode(hrApprisalDetailsTO.getTrngCode());
        hrApprisalDetails.setRecHead(hrApprisalDetailsTO.getRecHead());
        hrApprisalDetails.setRecHod(hrApprisalDetailsTO.getRecHod());
        hrApprisalDetails.setRecHrd(hrApprisalDetailsTO.getRecHrd());
        hrApprisalDetails.setRecPersonnel(hrApprisalDetailsTO.getRecPersonnel());
        hrApprisalDetails.setIncrAmt(hrApprisalDetailsTO.getIncrAmt());
        hrApprisalDetails.setPromWef(hrApprisalDetailsTO.getPromWef());
        hrApprisalDetails.setDefaultComp(hrApprisalDetailsTO.getDefaultComp());
        hrApprisalDetails.setStatFlag(hrApprisalDetailsTO.getStatFlag());
        hrApprisalDetails.setStatUpFlag(hrApprisalDetailsTO.getStatUpFlag());
        hrApprisalDetails.setEnteredBy(hrApprisalDetailsTO.getEnteredBy());
        hrApprisalDetails.setAuthBy(hrApprisalDetailsTO.getAuthBy());
        hrApprisalDetails.setModDate(hrApprisalDetailsTO.getModDate());
        return hrApprisalDetails;
    }

    public static HrApprisalDetailsPK addaptEntityPkHrApprisalDetailsPK(HrApprisalDetailsPKTO hrApprisalDetailsPKTO) {
        HrApprisalDetailsPK hrApprisalDetailsPK = new HrApprisalDetailsPK();
        hrApprisalDetailsPK.setCompCode(hrApprisalDetailsPKTO.getCompCode());
        hrApprisalDetailsPK.setEmpCode(hrApprisalDetailsPKTO.getEmpCode());
        hrApprisalDetailsPK.setAppraisalDt(hrApprisalDetailsPKTO.getAppraisalDt());
        return hrApprisalDetailsPK;
    }

    public static HrPromotionDetails addaptEntitypkHrPromotionDetails(HrPromotionDetailsTO hrPromotionDetailsTO) {
        HrPromotionDetails hrPromotionDetails = new HrPromotionDetails();
        hrPromotionDetails.setHrPromotionDetailsPK(addaptEntityPKHrpromotionDetailsPK(hrPromotionDetailsTO.getHrPromotionDetailsPKTO()));
        hrPromotionDetails.setEmpCode(hrPromotionDetailsTO.getEmpCode());
        hrPromotionDetails.setArdate(hrPromotionDetailsTO.getArdate());
        hrPromotionDetails.setDeptFrom(hrPromotionDetailsTO.getDeptFrom());
        hrPromotionDetails.setDeptTo(hrPromotionDetailsTO.getDeptTo());
        hrPromotionDetails.setBlockFrom(hrPromotionDetailsTO.getBlockFrom());
        hrPromotionDetails.setBlockTo(hrPromotionDetailsTO.getBlockTo());
        hrPromotionDetails.setZoneFrom(hrPromotionDetailsTO.getZoneFrom());
        hrPromotionDetails.setZoneTo(hrPromotionDetailsTO.getZoneTo());
        hrPromotionDetails.setPresLocat(hrPromotionDetailsTO.getPresLocat());
        hrPromotionDetails.setProposLocat(hrPromotionDetailsTO.getProposLocat());
        hrPromotionDetails.setSalaryPres(hrPromotionDetailsTO.getSalaryPres());
        hrPromotionDetails.setSalaryPropos(hrPromotionDetailsTO.getSalaryPropos());
        hrPromotionDetails.setPresDesig(hrPromotionDetailsTO.getPresDesig());
        hrPromotionDetails.setProposDesig(hrPromotionDetailsTO.getProposDesig());
        hrPromotionDetails.setPresRepId(hrPromotionDetailsTO.getPresRepId());
        hrPromotionDetails.setProposRepId(hrPromotionDetailsTO.getProposRepId());
        hrPromotionDetails.setBudgtStatus(hrPromotionDetailsTO.getBudgtStatus());
        hrPromotionDetails.setOverRating(hrPromotionDetailsTO.getOverRating());
        hrPromotionDetails.setRemarks1(hrPromotionDetailsTO.getRemarks1());
        hrPromotionDetails.setPromWef(hrPromotionDetailsTO.getPromWef());
        hrPromotionDetails.setHeadApprov(hrPromotionDetailsTO.getHeadApprov());
        hrPromotionDetails.setHrdApprov(hrPromotionDetailsTO.getHrdApprov());
        hrPromotionDetails.setMdApprov(hrPromotionDetailsTO.getMdApprov());
        hrPromotionDetails.setStatus(hrPromotionDetailsTO.getStatus());
        hrPromotionDetails.setEmpIdOld(hrPromotionDetailsTO.getEmpIdOld());
        hrPromotionDetails.setDefaultComp(hrPromotionDetailsTO.getDefaultComp());
        hrPromotionDetails.setStatFlag(hrPromotionDetailsTO.getStatFlag());
        hrPromotionDetails.setStatUpFlag(hrPromotionDetailsTO.getStatUpFlag());
        hrPromotionDetails.setModDate(hrPromotionDetailsTO.getModDate());
        hrPromotionDetails.setAuthBy(hrPromotionDetailsTO.getAuthBy());
        hrPromotionDetails.setEnteredBy(hrPromotionDetailsTO.getEnteredBy());
        return hrPromotionDetails;

    }

    public static HrPromotionDetailsPK addaptEntityPKHrpromotionDetailsPK(HrPromotionDetailsPKTO hrPromotionDetailsPKTO) {
        HrPromotionDetailsPK hrPromotionDetailsPK = new HrPromotionDetailsPK();
        hrPromotionDetailsPK.setCompCode(hrPromotionDetailsPKTO.getCompCode());
        hrPromotionDetailsPK.setArNo(hrPromotionDetailsPKTO.getArNo());
        return hrPromotionDetailsPK;
    }

    public static ClearSlipHd addaptEntityClearSlipHd(ClearSlipHdTO clearSlipHdTO) {
        ClearSlipHd clearSlipHd = new ClearSlipHd();
        clearSlipHd.setClearSlipHdPK(addaptEntityClearSlipHdPK(clearSlipHdTO.getClearSlipHdPK()));
        clearSlipHd.setSettlementDt(clearSlipHdTO.getSettlementDt());
        clearSlipHd.setTotAmt(clearSlipHdTO.getTotAmt());
        clearSlipHd.setDdChequeNu(clearSlipHdTO.getDdChequeNu());
        clearSlipHd.setDdCheque(clearSlipHdTO.getDdCheque());
        clearSlipHd.setDdAmt(clearSlipHdTO.getDdAmt());
        clearSlipHd.setBankName(clearSlipHdTO.getBankName());
        clearSlipHd.setDefaultComp(clearSlipHdTO.getDefaultComp());
        clearSlipHd.setStatFlag(clearSlipHdTO.getStatFlag());
        clearSlipHd.setStatUpFlag(clearSlipHdTO.getStatUpFlag());
        clearSlipHd.setModDate(clearSlipHdTO.getModDate());
        clearSlipHd.setEnteredBy(clearSlipHdTO.getEnteredBy());
        clearSlipHd.setAuthBy(clearSlipHdTO.getAuthBy());
        return clearSlipHd;
    }

    public static ClearSlipHdPK addaptEntityClearSlipHdPK(ClearSlipHdPKTO clearSlipHdPKTO) {
        ClearSlipHdPK clearSlipHdPK = new ClearSlipHdPK();
        clearSlipHdPK.setCompCode(clearSlipHdPKTO.getCompCode());
        clearSlipHdPK.setEmpCode(clearSlipHdPKTO.getEmpCode());
        return clearSlipHdPK;
    }

    public static ClearSlipDet addaptEntityClearSlipDet(ClearSlipDetTO clearSlipDetTO) {
        ClearSlipDet clearSlipDet = new ClearSlipDet();
        clearSlipDet.setClearSlipDetPK(addaptEntityClearSlipDetPK(clearSlipDetTO.getClearSlipDetPK()));
        clearSlipDet.setDueAmt(clearSlipDetTO.getDueAmt());
        clearSlipDet.setRecoverableAmt(clearSlipDetTO.getRecoverableAmt());
        clearSlipDet.setNetAmt(clearSlipDetTO.getNetAmt());
        clearSlipDet.setRemarks(clearSlipDetTO.getRemarks());
        clearSlipDet.setDefaultComp(clearSlipDetTO.getDefaultComp());
        clearSlipDet.setStatFlag(clearSlipDetTO.getStatFlag());
        clearSlipDet.setStatUpFlag(clearSlipDetTO.getStatUpFlag());
        clearSlipDet.setModDate(clearSlipDetTO.getModDate());
        clearSlipDet.setAuthBy(clearSlipDetTO.getAuthBy());
        clearSlipDet.setEnteredBy(clearSlipDetTO.getEnterdBy());
        return clearSlipDet;
    }

    public static ClearSlipDetPK addaptEntityClearSlipDetPK(ClearSlipDetPKTO clearSlipDetPKTO) {
        ClearSlipDetPK clearSlipDetPK = new ClearSlipDetPK();
        clearSlipDetPK.setCompCode(clearSlipDetPKTO.getCompCode());
        clearSlipDetPK.setEmpCode(clearSlipDetPKTO.getEmpCode());
        clearSlipDetPK.setDeptCode(clearSlipDetPKTO.getDeptCode());
        return clearSlipDetPK;
    }

    public static HrExitInterviewDt addaptEntityHrExitInterviewDt(HrExitInterviewDtTO hrExitInterviewDtTO) {
        HrExitInterviewDt hrExitInterviewDt = new HrExitInterviewDt();
        hrExitInterviewDt.setHrExitInterviewDtPK(addaptEntityHrExitInterviewDtPK(hrExitInterviewDtTO.getHrExitInterviewDtPK()));
        hrExitInterviewDt.setDefaultComp(hrExitInterviewDtTO.getDefaultComp());
        hrExitInterviewDt.setAuthBy(hrExitInterviewDtTO.getAuthBy());
        hrExitInterviewDt.setEnteredBy(hrExitInterviewDtTO.getEnteredBy());
        hrExitInterviewDt.setStatFlag(hrExitInterviewDtTO.getStatFlag());
        hrExitInterviewDt.setStatUpFlag(hrExitInterviewDtTO.getStatUpFlag());
        hrExitInterviewDt.setModDate(hrExitInterviewDtTO.getModDate());
        return hrExitInterviewDt;
    }

    public static HrExitInterviewDtPK addaptEntityHrExitInterviewDtPK(HrExitInterviewDtPKTO exitInterviewDtPKTO) {
        HrExitInterviewDtPK hrExitInterviewDtPK = new HrExitInterviewDtPK();
        hrExitInterviewDtPK.setCompCode(exitInterviewDtPKTO.getCompCode());
        hrExitInterviewDtPK.setEmpCode(exitInterviewDtPKTO.getEmpCode());
        hrExitInterviewDtPK.setReasonCode(exitInterviewDtPKTO.getReasonCode());
        return hrExitInterviewDtPK;
    }

    public static HrExitInterviewHd addaptEntityHrExitInterviewHd(HrExitInterviewHdTO hrExitInterviewHdTO) {
        HrExitInterviewHd hrExitInterviewHd = new HrExitInterviewHd();
        hrExitInterviewHd.setHrExitInterviewHdPK(addaptEntityHrExitInterviewHdPK(hrExitInterviewHdTO.getHrExitInterviewHdPK()));
        hrExitInterviewHd.setSatisAsses(hrExitInterviewHdTO.getSatisAsses());
        hrExitInterviewHd.setDisatisAsses(hrExitInterviewHdTO.getDisatisAsses());
        hrExitInterviewHd.setDefaultComp(hrExitInterviewHdTO.getDefaultComp());
        hrExitInterviewHd.setAuthBy(hrExitInterviewHdTO.getAuthBy());
        hrExitInterviewHd.setEnteredBy(hrExitInterviewHdTO.getEnteredBy());
        hrExitInterviewHd.setStatFlag(hrExitInterviewHdTO.getStatFlag());
        hrExitInterviewHd.setStatUpFlag(hrExitInterviewHdTO.getStatUpFlag());
        hrExitInterviewHd.setModDate(hrExitInterviewHdTO.getModDate());
        return hrExitInterviewHd;
    }

    public static HrExitInterviewHdPK addaptEntityHrExitInterviewHdPK(HrExitInterviewHdPKTO hrExitInterviewHdPKTO) {
        HrExitInterviewHdPK hrExitInterviewHdPK = new HrExitInterviewHdPK();
        hrExitInterviewHdPK.setCompCode(hrExitInterviewHdPKTO.getCompCode());
        hrExitInterviewHdPK.setEmpCode(hrExitInterviewHdPKTO.getEmpCode());
        return hrExitInterviewHdPK;
    }

    public static HrPersonnelReference adaptTOHrPersonnelReferenceEntity(HrPersonnelReferenceTO hrPersonnelReferenceTO) {
        HrPersonnelReference hrPersonnelReference = new HrPersonnelReference();
        HrPersonnelReferencePK hrPersonnelReferencePK = new HrPersonnelReferencePK();
        hrPersonnelReferencePK.setCompCode(hrPersonnelReferenceTO.getHrPersonnelReferencePKTO().getCompCode());
        hrPersonnelReferencePK.setEmpCode(hrPersonnelReferenceTO.getHrPersonnelReferencePKTO().getEmpCode());
        hrPersonnelReferencePK.setRefCode(hrPersonnelReferenceTO.getHrPersonnelReferencePKTO().getRefCode());
        hrPersonnelReference.setAuthBy(hrPersonnelReferenceTO.getAuthBy());
        hrPersonnelReference.setDefaultComp(hrPersonnelReferenceTO.getDefaultComp());
        hrPersonnelReference.setEnteredBy(hrPersonnelReferenceTO.getEnteredBy());
        hrPersonnelReference.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrPersonnelReferenceTO.getHrPersonnelDetailsTO()));
        hrPersonnelReference.setHrPersonnelReferencePK(hrPersonnelReferencePK);
        hrPersonnelReference.setModDate(hrPersonnelReferenceTO.getModDate());
        hrPersonnelReference.setRefAdd(hrPersonnelReferenceTO.getRefAdd());
        hrPersonnelReference.setRefCity(hrPersonnelReferenceTO.getRefCity());
        hrPersonnelReference.setRefName(hrPersonnelReferenceTO.getRefName());
        hrPersonnelReference.setRefOcc(hrPersonnelReferenceTO.getRefOcc());
        hrPersonnelReference.setRefPhone(hrPersonnelReferenceTO.getRefPhone());
        hrPersonnelReference.setRefPin(hrPersonnelReferenceTO.getRefPin());
        hrPersonnelReference.setRefState(hrPersonnelReferenceTO.getRefState());
        hrPersonnelReference.setStatFlag(hrPersonnelReferenceTO.getStatFlag());
        hrPersonnelReference.setStatUpFlag(hrPersonnelReferenceTO.getStatUpFlag());
        return hrPersonnelReference;
    }

    public static HrPersonnelReferenceTO adaptTOHrPersonnelReferenceTO(HrPersonnelReference hrPersonnelReference) {
        HrPersonnelReferenceTO hrPersonnelReferenceTO = new HrPersonnelReferenceTO();
        HrPersonnelReferencePKTO hrPersonnelReferencePKTO = new HrPersonnelReferencePKTO();
        hrPersonnelReferencePKTO.setCompCode(hrPersonnelReference.getHrPersonnelReferencePK().getCompCode());
        hrPersonnelReferencePKTO.setEmpCode(hrPersonnelReference.getHrPersonnelReferencePK().getEmpCode());
        hrPersonnelReferencePKTO.setRefCode(hrPersonnelReference.getHrPersonnelReferencePK().getRefCode());
        hrPersonnelReferenceTO.setAuthBy(hrPersonnelReference.getAuthBy());
        hrPersonnelReferenceTO.setDefaultComp(hrPersonnelReference.getDefaultComp());
        hrPersonnelReferenceTO.setEnteredBy(hrPersonnelReference.getEnteredBy());
        hrPersonnelReferenceTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelReference.getHrPersonnelDetails()));
        hrPersonnelReferenceTO.setHrPersonnelReferencePKTO(hrPersonnelReferencePKTO);
        hrPersonnelReferenceTO.setModDate(hrPersonnelReference.getModDate());
        hrPersonnelReferenceTO.setRefAdd(hrPersonnelReference.getRefAdd());
        hrPersonnelReferenceTO.setRefCity(hrPersonnelReference.getRefCity());
        hrPersonnelReferenceTO.setRefName(hrPersonnelReference.getRefName());
        hrPersonnelReferenceTO.setRefOcc(hrPersonnelReference.getRefOcc());
        hrPersonnelReferenceTO.setRefPhone(hrPersonnelReference.getRefPhone());
        hrPersonnelReferenceTO.setRefPin(hrPersonnelReference.getRefPin());
        hrPersonnelReferenceTO.setRefState(hrPersonnelReference.getRefState());
        hrPersonnelReferenceTO.setStatFlag(hrPersonnelReference.getStatFlag());
        hrPersonnelReferenceTO.setStatUpFlag(hrPersonnelReference.getStatUpFlag());
        return hrPersonnelReferenceTO;
    }

    public static HrPersonnelQualification adaptTOHrPersonnelQualificationEntity(HrPersonnelQualificationTO hrPersonnelQualificationTO) {
        HrPersonnelQualification hrPersonnelQualification = new HrPersonnelQualification();
        HrPersonnelQualificationPK hrPersonnelQualificationPK = new HrPersonnelQualificationPK();
        hrPersonnelQualificationPK.setCompCode(hrPersonnelQualificationTO.getHrPersonnelQualificationPKTO().getCompCode());
        hrPersonnelQualificationPK.setEmpCode(hrPersonnelQualificationTO.getHrPersonnelQualificationPKTO().getEmpCode());
        hrPersonnelQualificationPK.setQualCode(hrPersonnelQualificationTO.getHrPersonnelQualificationPKTO().getQualCode());
        hrPersonnelQualification.setAuthBy(hrPersonnelQualificationTO.getAuthBy());
        hrPersonnelQualification.setDefaultComp(hrPersonnelQualificationTO.getDefaultComp());
        hrPersonnelQualification.setDivision(hrPersonnelQualificationTO.getDivision());
        hrPersonnelQualification.setDuration(hrPersonnelQualificationTO.getDuration());
        hrPersonnelQualification.setEnteredBy(hrPersonnelQualificationTO.getEnteredBy());
        hrPersonnelQualification.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrPersonnelQualificationTO.getHrPersonnelDetailsTO()));
        hrPersonnelQualification.setHrPersonnelQualificationPK(hrPersonnelQualificationPK);
        hrPersonnelQualification.setInstituteName(hrPersonnelQualificationTO.getInstituteName());
        hrPersonnelQualification.setModDate(hrPersonnelQualificationTO.getModDate());
        hrPersonnelQualification.setPercentageMarks(hrPersonnelQualificationTO.getPercentageMarks());
        hrPersonnelQualification.setSpecializationCode(hrPersonnelQualificationTO.getSpecializationCode());
        hrPersonnelQualification.setStatFlag(hrPersonnelQualificationTO.getStatFlag());
        hrPersonnelQualification.setStatUpFlag(hrPersonnelQualificationTO.getStatUpFlag());
        hrPersonnelQualification.setSubName(hrPersonnelQualificationTO.getSubName());
        hrPersonnelQualification.setYear(hrPersonnelQualificationTO.getYear());
        return hrPersonnelQualification;
    }

    public static HrPersonnelQualificationTO adaptTOHrPersonnelQualificationTO(HrPersonnelQualification hrPersonnelQualification) {
        HrPersonnelQualificationTO hrPersonnelQualificationTO = new HrPersonnelQualificationTO();
        HrPersonnelQualificationPKTO hrPersonnelQualificationPKTO = new HrPersonnelQualificationPKTO();
        hrPersonnelQualificationPKTO.setCompCode(hrPersonnelQualification.getHrPersonnelQualificationPK().getCompCode());
        hrPersonnelQualificationPKTO.setEmpCode(hrPersonnelQualification.getHrPersonnelQualificationPK().getEmpCode());
        hrPersonnelQualificationPKTO.setQualCode(hrPersonnelQualification.getHrPersonnelQualificationPK().getQualCode());
        hrPersonnelQualificationTO.setAuthBy(hrPersonnelQualification.getAuthBy());
        hrPersonnelQualificationTO.setDefaultComp(hrPersonnelQualification.getDefaultComp());
        hrPersonnelQualificationTO.setDivision(hrPersonnelQualification.getDivision());
        hrPersonnelQualificationTO.setDuration(hrPersonnelQualification.getDuration());
        hrPersonnelQualificationTO.setEnteredBy(hrPersonnelQualification.getEnteredBy());
        hrPersonnelQualificationTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelQualification.getHrPersonnelDetails()));
        hrPersonnelQualificationTO.setHrPersonnelQualificationPKTO(hrPersonnelQualificationPKTO);
        hrPersonnelQualificationTO.setInstituteName(hrPersonnelQualification.getInstituteName());
        hrPersonnelQualificationTO.setModDate(hrPersonnelQualification.getModDate());
        hrPersonnelQualificationTO.setPercentageMarks(hrPersonnelQualification.getPercentageMarks());
        hrPersonnelQualificationTO.setSpecializationCode(hrPersonnelQualification.getSpecializationCode());
        hrPersonnelQualificationTO.setStatFlag(hrPersonnelQualification.getStatFlag());
        hrPersonnelQualificationTO.setStatUpFlag(hrPersonnelQualification.getStatUpFlag());
        hrPersonnelQualificationTO.setSubName(hrPersonnelQualification.getSubName());
        hrPersonnelQualificationTO.setYear(hrPersonnelQualification.getYear());
        return hrPersonnelQualificationTO;
    }

    public static HrPersonnelDependent adaptTOHrPersonnelDependentEntity(HrPersonnelDependentTO hrPersonnelDependentTO) {
        HrPersonnelDependent hrPersonnelDependent = new HrPersonnelDependent();
        HrPersonnelDependentPK hrPersonnelDependentPK = new HrPersonnelDependentPK();
        hrPersonnelDependentPK.setCompCode(hrPersonnelDependentTO.getHrPersonnelDependentPKTO().getCompCode());
        hrPersonnelDependentPK.setEmpCode(hrPersonnelDependentTO.getHrPersonnelDependentPKTO().getEmpCode());
        hrPersonnelDependentPK.setDependentCode(hrPersonnelDependentTO.getHrPersonnelDependentPKTO().getDependentCode());
        hrPersonnelDependent.setAuthBy(hrPersonnelDependentTO.getAuthBy());
        hrPersonnelDependent.setDefaultComp(hrPersonnelDependentTO.getDefaultComp());
        hrPersonnelDependent.setDepAge(hrPersonnelDependentTO.getDepAge());
        hrPersonnelDependent.setDepName(hrPersonnelDependentTO.getDepName());
        hrPersonnelDependent.setDepRel(hrPersonnelDependentTO.getDepRel());
        hrPersonnelDependent.setEnteredBy(hrPersonnelDependentTO.getEnteredBy());
        hrPersonnelDependent.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrPersonnelDependentTO.getHrPersonnelDetailsTO()));
        hrPersonnelDependent.setHrPersonnelDependentPK(hrPersonnelDependentPK);
        hrPersonnelDependent.setModDate(hrPersonnelDependentTO.getModDate());
        hrPersonnelDependent.setOccupation(hrPersonnelDependentTO.getOccupation());
        hrPersonnelDependent.setPetName(hrPersonnelDependentTO.getPetName());
        hrPersonnelDependent.setStatFlag(hrPersonnelDependentTO.getStatFlag());
        hrPersonnelDependent.setStatUpFlag(hrPersonnelDependentTO.getStatUpFlag());
        return hrPersonnelDependent;
    }

    public static HrPersonnelDependentTO adaptTOHrPersonnelDependentTO(HrPersonnelDependent hrPersonnelDependent) {
        HrPersonnelDependentTO hrPersonnelDependentTO = new HrPersonnelDependentTO();
        HrPersonnelDependentPKTO hrPersonnelDependentPKTO = new HrPersonnelDependentPKTO();
        hrPersonnelDependentPKTO.setCompCode(hrPersonnelDependent.getHrPersonnelDependentPK().getCompCode());
        hrPersonnelDependentPKTO.setEmpCode(hrPersonnelDependent.getHrPersonnelDependentPK().getEmpCode());
        hrPersonnelDependentPKTO.setDependentCode(hrPersonnelDependent.getHrPersonnelDependentPK().getDependentCode());

        hrPersonnelDependentTO.setAuthBy(hrPersonnelDependent.getAuthBy());
        hrPersonnelDependentTO.setDefaultComp(hrPersonnelDependent.getDefaultComp());
        hrPersonnelDependentTO.setDepAge(hrPersonnelDependent.getDepAge());
        hrPersonnelDependentTO.setDepName(hrPersonnelDependent.getDepName());
        hrPersonnelDependentTO.setDepRel(hrPersonnelDependent.getDepRel());
        hrPersonnelDependentTO.setEnteredBy(hrPersonnelDependent.getEnteredBy());
        hrPersonnelDependentTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelDependent.getHrPersonnelDetails()));
        hrPersonnelDependentTO.setHrPersonnelDependentPKTO(hrPersonnelDependentPKTO);
        hrPersonnelDependentTO.setModDate(hrPersonnelDependent.getModDate());
        hrPersonnelDependentTO.setOccupation(hrPersonnelDependent.getOccupation());
        hrPersonnelDependentTO.setPetName(hrPersonnelDependent.getPetName());
        hrPersonnelDependentTO.setStatFlag(hrPersonnelDependentTO.getStatFlag());
        hrPersonnelDependentTO.setStatUpFlag(hrPersonnelDependentTO.getStatUpFlag());
        return hrPersonnelDependentTO;
    }

    public static HrPersonnelPreviousCompany adaptTOHrPersonnelPreviousCompanyEntity(HrPersonnelPreviousCompanyTO hrPersonnelPreviousCompanyTO) {
        HrPersonnelPreviousCompany hrPersonnelPreviousCompany = new HrPersonnelPreviousCompany();
        HrPersonnelPreviousCompanyPK hrPersonnelPreviousCompanyPK = new HrPersonnelPreviousCompanyPK();
        hrPersonnelPreviousCompanyPK.setCompCode(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getCompCode());
        hrPersonnelPreviousCompanyPK.setEmpCode(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getEmpCode());
        hrPersonnelPreviousCompanyPK.setDurFrom(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getDurFrom());
        hrPersonnelPreviousCompanyPK.setDurTo(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getDurTo());
        hrPersonnelPreviousCompanyPK.setPrevCompCode(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getPrevCompCode());

        hrPersonnelPreviousCompany.setAnnualTurn(hrPersonnelPreviousCompanyTO.getAnnualTurn());
        hrPersonnelPreviousCompany.setAuthBy(hrPersonnelPreviousCompanyTO.getAuthBy());
        hrPersonnelPreviousCompany.setCompAdd(hrPersonnelPreviousCompanyTO.getCompAdd());
        hrPersonnelPreviousCompany.setCompName(hrPersonnelPreviousCompanyTO.getCompName());
        hrPersonnelPreviousCompany.setDefaultComp(hrPersonnelPreviousCompanyTO.getDefaultComp());
        hrPersonnelPreviousCompany.setDesgJoin(hrPersonnelPreviousCompanyTO.getDesgJoin());
        hrPersonnelPreviousCompany.setDesgLeave(hrPersonnelPreviousCompanyTO.getDesgLeave());
        hrPersonnelPreviousCompany.setEmpUnder(hrPersonnelPreviousCompanyTO.getEmpUnder());
        hrPersonnelPreviousCompany.setEnteredBy(hrPersonnelPreviousCompanyTO.getEnteredBy());
        hrPersonnelPreviousCompany.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrPersonnelPreviousCompanyTO.getHrPersonnelDetailsTO()));
        hrPersonnelPreviousCompany.setHrPersonnelPreviousCompanyPK(hrPersonnelPreviousCompanyPK);
        hrPersonnelPreviousCompany.setModDate(hrPersonnelPreviousCompanyTO.getModDate());
        hrPersonnelPreviousCompany.setReasonLeaving(hrPersonnelPreviousCompanyTO.getReasonLeaving());
        hrPersonnelPreviousCompany.setSalJoin(hrPersonnelPreviousCompanyTO.getSalJoin());
        hrPersonnelPreviousCompany.setSalLeave(hrPersonnelPreviousCompanyTO.getSalLeave());
        hrPersonnelPreviousCompany.setStatFlag(hrPersonnelPreviousCompanyTO.getStatFlag());
        hrPersonnelPreviousCompany.setStatUpFlag(hrPersonnelPreviousCompanyTO.getStatUpFlag());
        hrPersonnelPreviousCompany.setTotEmp(hrPersonnelPreviousCompanyTO.getTotEmp());

        return hrPersonnelPreviousCompany;
    }

    public static HrPersonnelPreviousCompanyTO adaptTOHrPersonnelPreviousCompanyTO(HrPersonnelPreviousCompany hrPersonnelPreviousCompany) {
        HrPersonnelPreviousCompanyTO hrPersonnelPreviousCompanyTO = new HrPersonnelPreviousCompanyTO();
        HrPersonnelPreviousCompanyPKTO hrPersonnelPreviousCompanyPKTO = new HrPersonnelPreviousCompanyPKTO();
        hrPersonnelPreviousCompanyPKTO.setCompCode(hrPersonnelPreviousCompany.getHrPersonnelPreviousCompanyPK().getCompCode());
        hrPersonnelPreviousCompanyPKTO.setEmpCode(hrPersonnelPreviousCompany.getHrPersonnelPreviousCompanyPK().getEmpCode());
        hrPersonnelPreviousCompanyPKTO.setDurFrom(hrPersonnelPreviousCompany.getHrPersonnelPreviousCompanyPK().getDurFrom());
        hrPersonnelPreviousCompanyPKTO.setDurTo(hrPersonnelPreviousCompany.getHrPersonnelPreviousCompanyPK().getDurTo());
        hrPersonnelPreviousCompanyPKTO.setPrevCompCode(hrPersonnelPreviousCompany.getHrPersonnelPreviousCompanyPK().getPrevCompCode());

        hrPersonnelPreviousCompanyTO.setAnnualTurn(hrPersonnelPreviousCompany.getAnnualTurn());
        hrPersonnelPreviousCompanyTO.setAuthBy(hrPersonnelPreviousCompany.getAuthBy());
        hrPersonnelPreviousCompanyTO.setCompAdd(hrPersonnelPreviousCompany.getCompAdd());
        hrPersonnelPreviousCompanyTO.setCompName(hrPersonnelPreviousCompany.getCompName());
        hrPersonnelPreviousCompanyTO.setDefaultComp(hrPersonnelPreviousCompany.getDefaultComp());
        hrPersonnelPreviousCompanyTO.setDesgJoin(hrPersonnelPreviousCompany.getDesgJoin());
        hrPersonnelPreviousCompanyTO.setDesgLeave(hrPersonnelPreviousCompany.getDesgLeave());
        hrPersonnelPreviousCompanyTO.setEmpUnder(hrPersonnelPreviousCompany.getEmpUnder());
        hrPersonnelPreviousCompanyTO.setEnteredBy(hrPersonnelPreviousCompany.getEnteredBy());
        hrPersonnelPreviousCompanyTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelPreviousCompany.getHrPersonnelDetails()));
        hrPersonnelPreviousCompanyTO.setHrPersonnelPreviousCompanyPKTO(hrPersonnelPreviousCompanyPKTO);
        hrPersonnelPreviousCompanyTO.setModDate(hrPersonnelPreviousCompany.getModDate());
        hrPersonnelPreviousCompanyTO.setReasonLeaving(hrPersonnelPreviousCompany.getReasonLeaving());
        hrPersonnelPreviousCompanyTO.setSalJoin(hrPersonnelPreviousCompany.getSalJoin());
        hrPersonnelPreviousCompanyTO.setSalLeave(hrPersonnelPreviousCompany.getSalLeave());
        hrPersonnelPreviousCompanyTO.setStatFlag(hrPersonnelPreviousCompany.getStatFlag());
        hrPersonnelPreviousCompanyTO.setStatUpFlag(hrPersonnelPreviousCompany.getStatUpFlag());
        hrPersonnelPreviousCompanyTO.setTotEmp(hrPersonnelPreviousCompany.getTotEmp());
        return hrPersonnelPreviousCompanyTO;
    }

    public static HrPersonnelTransport adaptTOHrPersonnelTransportEntity(HrPersonnelTransportTO hrPersonnelTransportTO) {
        HrPersonnelTransport hrPersonnelTransport = new HrPersonnelTransport();
        HrPersonnelTransportPK hrPersonnelTransportPK = new HrPersonnelTransportPK();
        hrPersonnelTransportPK.setCompCode(hrPersonnelTransportTO.getHrPersonnelTransportPKTO().getCompCode());
        hrPersonnelTransportPK.setEmpCode(hrPersonnelTransportTO.getHrPersonnelTransportPKTO().getEmpCode());
        hrPersonnelTransportPK.setBusNo(hrPersonnelTransportTO.getHrPersonnelTransportPKTO().getBusNo());
        hrPersonnelTransportPK.setRouteCode(hrPersonnelTransportTO.getHrPersonnelTransportPKTO().getRouteCode());


        hrPersonnelTransport.setAuthBy(hrPersonnelTransportTO.getAuthBy());
        hrPersonnelTransport.setDefaultComp(hrPersonnelTransportTO.getDefaultComp());
        hrPersonnelTransport.setEnteredBy(hrPersonnelTransportTO.getEnteredBy());
        hrPersonnelTransport.setHrMstBus(ObjectAdaptorDefinitions.adaptToHrMstBusEntity(hrPersonnelTransportTO.getHrMstBusTO()));
        hrPersonnelTransport.setHrMstRoute(ObjectAdaptorDefinitions.adaptToHrMstRouteEntity(hrPersonnelTransportTO.getHrMstRouteTO()));
        hrPersonnelTransport.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrPersonnelTransportTO.getHrPersonnelDetailsTO()));
        hrPersonnelTransport.setHrPersonnelTransportPK(hrPersonnelTransportPK);
        hrPersonnelTransport.setModDate(hrPersonnelTransportTO.getModDate());
        hrPersonnelTransport.setPickPnt(hrPersonnelTransportTO.getPickPnt());
        hrPersonnelTransport.setPickTm(hrPersonnelTransportTO.getPickTm());
        hrPersonnelTransport.setStatFlag(hrPersonnelTransportTO.getStatFlag());
        hrPersonnelTransport.setStatUpFlag(hrPersonnelTransportTO.getStatUpFlag());
        return hrPersonnelTransport;
    }

    public static HrPersonnelTransportTO adaptTOHrPersonnelTransportTO(HrPersonnelTransport hrPersonnelTransport) {
        HrPersonnelTransportTO hrPersonnelTransportTO = new HrPersonnelTransportTO();
        HrPersonnelTransportPKTO hrPersonnelTransportPKTO = new HrPersonnelTransportPKTO();
        hrPersonnelTransportPKTO.setCompCode(hrPersonnelTransport.getHrPersonnelTransportPK().getCompCode());
        hrPersonnelTransportPKTO.setEmpCode(hrPersonnelTransport.getHrPersonnelTransportPK().getEmpCode());
        hrPersonnelTransportPKTO.setBusNo(hrPersonnelTransport.getHrPersonnelTransportPK().getBusNo());
        hrPersonnelTransportPKTO.setRouteCode(hrPersonnelTransport.getHrPersonnelTransportPK().getRouteCode());

        hrPersonnelTransportTO.setAuthBy(hrPersonnelTransport.getAuthBy());
        hrPersonnelTransportTO.setDefaultComp(hrPersonnelTransport.getDefaultComp());
        hrPersonnelTransportTO.setEnteredBy(hrPersonnelTransport.getEnteredBy());
        hrPersonnelTransportTO.setHrMstBusTO(ObjectAdaptorDefinitions.adaptToHrMstBusTO(hrPersonnelTransport.getHrMstBus()));
        hrPersonnelTransportTO.setHrMstRouteTO(ObjectAdaptorDefinitions.adaptToHrMstRouteTO(hrPersonnelTransport.getHrMstRoute()));
        hrPersonnelTransportTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelTransport.getHrPersonnelDetails()));
        hrPersonnelTransportTO.setHrPersonnelTransportPKTO(hrPersonnelTransportPKTO);
        hrPersonnelTransportTO.setModDate(hrPersonnelTransport.getModDate());
        hrPersonnelTransportTO.setPickPnt(hrPersonnelTransport.getPickPnt());
        hrPersonnelTransportTO.setPickTm(hrPersonnelTransport.getPickTm());
        hrPersonnelTransportTO.setStatFlag(hrPersonnelTransport.getStatFlag());
        hrPersonnelTransportTO.setStatUpFlag(hrPersonnelTransport.getStatUpFlag());
        return hrPersonnelTransportTO;
    }

    public static HrMembershipDetail adaptTOHrMembershipDetailEntity(HrMembershipDetailTO hrMembershipDetailTO) {
        HrMembershipDetail hrMembershipDetail = new HrMembershipDetail();
        HrMembershipDetailPK hrMembershipDetailPK = new HrMembershipDetailPK();
        hrMembershipDetailPK.setCompCode(hrMembershipDetailTO.getHrMembershipDetailPKTO().getCompCode());
        hrMembershipDetailPK.setEmpCode(hrMembershipDetailTO.getHrMembershipDetailPKTO().getEmpCode());

        hrMembershipDetailPK.setMemNo(hrMembershipDetailTO.getHrMembershipDetailPKTO().getMemNo());
        hrMembershipDetail.setAccomdType(hrMembershipDetailTO.getAccomdType());
        hrMembershipDetail.setAuthBy(hrMembershipDetailTO.getAuthBy());
        hrMembershipDetail.setDefaultComp(hrMembershipDetailTO.getDefaultComp());
        hrMembershipDetail.setEnteredBy(hrMembershipDetailTO.getEnteredBy());
        hrMembershipDetail.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrMembershipDetailTO.getHrPersonnelDetailsTO()));
        hrMembershipDetail.setHrMembershipDetailPK(hrMembershipDetailPK);
        hrMembershipDetail.setIndivMember(hrMembershipDetailTO.getIndivMember());
        hrMembershipDetail.setInsuranceNo(hrMembershipDetailTO.getInsuranceNo());
        hrMembershipDetail.setModDate(hrMembershipDetailTO.getModDate());
        hrMembershipDetail.setPassportDate(hrMembershipDetailTO.getPassportDate());
        hrMembershipDetail.setPassportNo(hrMembershipDetailTO.getPassportNo());
        hrMembershipDetail.setProfMember(hrMembershipDetailTO.getProfMember());
        hrMembershipDetail.setStatFlag(hrMembershipDetailTO.getStatFlag());
        hrMembershipDetail.setStatUpFlag(hrMembershipDetailTO.getStatUpFlag());
        hrMembershipDetail.setTravelOver(hrMembershipDetailTO.getTravelOver());
        return hrMembershipDetail;
    }

    public static HrMembershipDetailTO adaptTOHrMembershipDetailTO(HrMembershipDetail hrMembershipDetail) {
        HrMembershipDetailTO hrMembershipDetailTO = new HrMembershipDetailTO();
        HrMembershipDetailPKTO hrMembershipDetailPKTO = new HrMembershipDetailPKTO();
        hrMembershipDetailPKTO.setCompCode(hrMembershipDetail.getHrMembershipDetailPK().getCompCode());
        hrMembershipDetailPKTO.setEmpCode(hrMembershipDetail.getHrMembershipDetailPK().getEmpCode());
        hrMembershipDetailPKTO.setMemNo(hrMembershipDetail.getHrMembershipDetailPK().getMemNo());
        hrMembershipDetailTO.setAccomdType(hrMembershipDetail.getAccomdType());
        hrMembershipDetailTO.setAuthBy(hrMembershipDetail.getAuthBy());
        hrMembershipDetailTO.setDefaultComp(hrMembershipDetail.getDefaultComp());
        hrMembershipDetailTO.setEnteredBy(hrMembershipDetail.getEnteredBy());
        hrMembershipDetailTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrMembershipDetail.getHrPersonnelDetails()));
        hrMembershipDetailTO.setHrMembershipDetailPKTO(hrMembershipDetailPKTO);
        hrMembershipDetailTO.setIndivMember(hrMembershipDetail.getIndivMember());
        hrMembershipDetailTO.setInsuranceNo(hrMembershipDetail.getInsuranceNo());
        hrMembershipDetailTO.setModDate(hrMembershipDetail.getModDate());
        hrMembershipDetailTO.setPassportDate(hrMembershipDetail.getPassportDate());
        hrMembershipDetailTO.setPassportNo(hrMembershipDetail.getPassportNo());
        hrMembershipDetailTO.setProfMember(hrMembershipDetail.getProfMember());
        hrMembershipDetailTO.setStatFlag(hrMembershipDetail.getStatFlag());
        hrMembershipDetailTO.setStatUpFlag(hrMembershipDetail.getStatUpFlag());
        hrMembershipDetailTO.setTravelOver(hrMembershipDetail.getTravelOver());
        return hrMembershipDetailTO;
    }

    public static HrPersonnelLangTO adaptTOHrPersonnelLangTO(HrPersonnelLang hrPersonnelLang) {
        HrPersonnelLangTO hrPersonnelLangTO = new HrPersonnelLangTO();
        HrPersonnelLangPKTO hrPersonnelLangPKTO = new HrPersonnelLangPKTO();
        hrPersonnelLangPKTO.setCompCode(hrPersonnelLang.getHrPersonnelLangPK().getCompCode());
        hrPersonnelLangPKTO.setEmpCode(hrPersonnelLang.getHrPersonnelLangPK().getEmpCode());
        hrPersonnelLangPKTO.setLang(hrPersonnelLang.getHrPersonnelLangPK().getLang());

        hrPersonnelLangTO.setAuthBy(hrPersonnelLang.getAuthBy());
        hrPersonnelLangTO.setDefaultComp(hrPersonnelLang.getDefaultComp());
        hrPersonnelLangTO.setEnteredBy(hrPersonnelLang.getEnteredBy());
        hrPersonnelLangTO.setHrPersonnelLangPKTO(hrPersonnelLangPKTO);
        hrPersonnelLangTO.setLangRead(hrPersonnelLang.getLangRead());
        hrPersonnelLangTO.setLangSpeak(hrPersonnelLang.getLangSpeak());
        hrPersonnelLangTO.setLangWrite(hrPersonnelLang.getLangWrite());
        hrPersonnelLangTO.setModDate(hrPersonnelLang.getModDate());
        hrPersonnelLangTO.setStatFlag(hrPersonnelLang.getStatFlag());
        hrPersonnelLangTO.setStatUpFlag(hrPersonnelLang.getStatUpFlag());

        return hrPersonnelLangTO;
    }

    public static HrPersonnelLang adaptTOHrPersonnelLangEntity(HrPersonnelLangTO hrPersonnelLangTO) {
        HrPersonnelLang hrPersonnelLang = new HrPersonnelLang();
        HrPersonnelLangPK hrPersonnelLangPK = new HrPersonnelLangPK();
        hrPersonnelLangPK.setCompCode(hrPersonnelLangTO.getHrPersonnelLangPKTO().getCompCode());
        hrPersonnelLangPK.setEmpCode(hrPersonnelLangTO.getHrPersonnelLangPKTO().getEmpCode());
        hrPersonnelLangPK.setLang(hrPersonnelLangTO.getHrPersonnelLangPKTO().getLang());

        hrPersonnelLang.setAuthBy(hrPersonnelLangTO.getAuthBy());
        hrPersonnelLang.setDefaultComp(hrPersonnelLangTO.getDefaultComp());
        hrPersonnelLang.setEnteredBy(hrPersonnelLangTO.getEnteredBy());
        hrPersonnelLang.setHrPersonnelLangPK(hrPersonnelLangPK);
        hrPersonnelLang.setLangRead(hrPersonnelLangTO.getLangRead());
        hrPersonnelLang.setLangSpeak(hrPersonnelLangTO.getLangSpeak());
        hrPersonnelLang.setLangWrite(hrPersonnelLangTO.getLangWrite());
        hrPersonnelLang.setModDate(hrPersonnelLangTO.getModDate());
        hrPersonnelLang.setStatFlag(hrPersonnelLangTO.getStatFlag());
        hrPersonnelLang.setStatUpFlag(hrPersonnelLangTO.getStatUpFlag());

        return hrPersonnelLang;
    }

    public static HrPersonnelHobbyTO adaptTOHrPersonnelHobbyTO(HrPersonnelHobby hrPersonnelHobby) {
        HrPersonnelHobbyTO hrPersonnelHobbyTO = new HrPersonnelHobbyTO();
        HrPersonnelHobbyPKTO hrPersonnelHobbyPKTO = new HrPersonnelHobbyPKTO();
        hrPersonnelHobbyPKTO.setCompCode(hrPersonnelHobby.getHrPersonnelHobbyPK().getCompCode());
        hrPersonnelHobbyPKTO.setEmpCode(hrPersonnelHobby.getHrPersonnelHobbyPK().getEmpCode());
        hrPersonnelHobbyPKTO.setHobbyName(hrPersonnelHobby.getHrPersonnelHobbyPK().getHobbyName());
        hrPersonnelHobbyTO.setAuthBy(hrPersonnelHobby.getAuthBy());
        hrPersonnelHobbyTO.setDefaultComp(hrPersonnelHobby.getDefaultComp());
        hrPersonnelHobbyTO.setEnteredBy(hrPersonnelHobby.getEnteredBy());
        hrPersonnelHobbyTO.setHrPersonnelHobbyPKTO(hrPersonnelHobbyPKTO);
        hrPersonnelHobbyTO.setModDate(hrPersonnelHobby.getModDate());
        hrPersonnelHobbyTO.setStatFlag(hrPersonnelHobby.getStatFlag());
        hrPersonnelHobbyTO.setStatUpFlag(hrPersonnelHobby.getStatUpFlag());
        return hrPersonnelHobbyTO;
    }

    public static HrPersonnelHobby adaptTOHrPersonnelHobbyEntity(HrPersonnelHobbyTO hrPersonnelHobbyTO) {
        HrPersonnelHobby hrPersonnelHobby = new HrPersonnelHobby();
        HrPersonnelHobbyPK hrPersonnelHobbyPK = new HrPersonnelHobbyPK();
        hrPersonnelHobbyPK.setCompCode(hrPersonnelHobbyTO.getHrPersonnelHobbyPKTO().getCompCode());
        hrPersonnelHobbyPK.setEmpCode(hrPersonnelHobbyTO.getHrPersonnelHobbyPKTO().getEmpCode());
        hrPersonnelHobbyPK.setHobbyName(hrPersonnelHobbyTO.getHrPersonnelHobbyPKTO().getHobbyName());
        hrPersonnelHobby.setAuthBy(hrPersonnelHobbyTO.getAuthBy());
        hrPersonnelHobby.setDefaultComp(hrPersonnelHobbyTO.getDefaultComp());
        hrPersonnelHobby.setEnteredBy(hrPersonnelHobbyTO.getEnteredBy());
        hrPersonnelHobby.setHrPersonnelHobbyPK(hrPersonnelHobbyPK);
        hrPersonnelHobby.setModDate(hrPersonnelHobbyTO.getModDate());
        hrPersonnelHobby.setStatFlag(hrPersonnelHobbyTO.getStatFlag());
        hrPersonnelHobby.setStatUpFlag(hrPersonnelHobbyTO.getStatUpFlag());
        return hrPersonnelHobby;
    }
}
