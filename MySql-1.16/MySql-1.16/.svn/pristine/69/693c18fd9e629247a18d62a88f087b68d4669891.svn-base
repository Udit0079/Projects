/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.adaptor;

import com.cbs.dto.master.BnkaddTO;
import com.cbs.dto.master.BranchMasterTO;
import com.cbs.dto.master.CbsChargeDetailTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.dto.master.CbsGlSubHeadSchemeDetailsPKTO;
import com.cbs.dto.master.CbsGlSubHeadSchemeDetailsTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.master.CbsSchemePopUpFormsTO;
import com.cbs.dto.master.CbsSchemeTypeFormMappingTO;
import com.cbs.dto.master.GltableTO;
import com.cbs.dto.master.ParameterinfoReportTO;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.entity.master.Bnkadd;

import com.cbs.entity.master.CbsExceptionMaster;
import com.cbs.entity.master.CbsGlSubHeadSchemeDetails;
import com.cbs.entity.master.CbsGlSubHeadSchemeDetailsPK;
import com.cbs.entity.master.CbsRefRecType;
import com.cbs.entity.master.CbsRefRecTypePK;
import com.cbs.entity.master.CbsSchemePopUpForms;
import com.cbs.entity.master.CbsSchemeTypeFormMapping;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.master.BranchMaster;
import com.cbs.entity.master.CbsChargeDetail;

/**
 *
 * @author sipl
 */
public class ObjectAdaptorMaster {

    public static CbsExceptionMaster adaptToCbsExceptionMasterEntity(CbsExceptionMasterTO cbsExceptionMasterTO) {
        CbsExceptionMaster cbsExceptionMasterE = new CbsExceptionMaster();

        cbsExceptionMasterE.setExceptionCode(cbsExceptionMasterTO.getExceptionCode());
        cbsExceptionMasterE.setExceptionDesc(cbsExceptionMasterTO.getExceptionDesc());
        cbsExceptionMasterE.setExceptionType(cbsExceptionMasterTO.getExceptionType());

        return cbsExceptionMasterE;
    }

    public static CbsExceptionMasterTO adaptToCbsExceptionMasterTO(CbsExceptionMaster cbsExceptionMaster) {
        CbsExceptionMasterTO cbsExceptionMasterTo = new CbsExceptionMasterTO();

        cbsExceptionMasterTo.setExceptionCode(cbsExceptionMaster.getExceptionCode());
        cbsExceptionMasterTo.setExceptionDesc(cbsExceptionMaster.getExceptionDesc());
        cbsExceptionMasterTo.setExceptionType(cbsExceptionMaster.getExceptionType());

        return cbsExceptionMasterTo;
    }

    public static CbsGlSubHeadSchemeDetailsPK adaptToCbsGlSubHeadSchemeDtlsPKEntity(CbsGlSubHeadSchemeDetailsPKTO cbsGlSubHeadSchemeDtlsPKTO) {
        CbsGlSubHeadSchemeDetailsPK cbsGlSubHeadSchDtlsPKE = new CbsGlSubHeadSchemeDetailsPK();

        cbsGlSubHeadSchDtlsPKE.setSchemeCode(cbsGlSubHeadSchemeDtlsPKTO.getSchemeCode());
        cbsGlSubHeadSchDtlsPKE.setGlSubHeadCode(cbsGlSubHeadSchemeDtlsPKTO.getGlSubHeadCode());

        return cbsGlSubHeadSchDtlsPKE;
    }

    public static CbsGlSubHeadSchemeDetailsPKTO adaptToCbsGlSubHeadSchemeDtlsPKTO(CbsGlSubHeadSchemeDetailsPK cbsGlSubHeadSchemeDtlsPK) {
        CbsGlSubHeadSchemeDetailsPKTO cbsGlSubHeadSchDtlsPKTo = new CbsGlSubHeadSchemeDetailsPKTO();

        cbsGlSubHeadSchDtlsPKTo.setSchemeCode(cbsGlSubHeadSchemeDtlsPK.getSchemeCode());
        cbsGlSubHeadSchDtlsPKTo.setGlSubHeadCode(cbsGlSubHeadSchemeDtlsPK.getGlSubHeadCode());

        return cbsGlSubHeadSchDtlsPKTo;
    }

    public static CbsGlSubHeadSchemeDetails adaptToCbsGlSubHeadSchDtlsEntity(CbsGlSubHeadSchemeDetailsTO cbsGlSubHeadSchDtlsTO) {
        CbsGlSubHeadSchemeDetails cbsGlSubHeadSchDtlsE = new CbsGlSubHeadSchemeDetails();

        cbsGlSubHeadSchDtlsE.setCbsGlSubHeadSchemeDetailsPK(ObjectAdaptorMaster.adaptToCbsGlSubHeadSchemeDtlsPKEntity(cbsGlSubHeadSchDtlsTO.getCbsGlSubHeadSchemeDetailsPKTO()));

        cbsGlSubHeadSchDtlsE.setSchemeType(cbsGlSubHeadSchDtlsTO.getSchemeType());
        cbsGlSubHeadSchDtlsE.setDfltFlag(cbsGlSubHeadSchDtlsTO.getDfltFlag());
        cbsGlSubHeadSchDtlsE.setDelFlag(cbsGlSubHeadSchDtlsTO.getDelFlag());

        return cbsGlSubHeadSchDtlsE;
    }

    public static CbsGlSubHeadSchemeDetailsTO adaptToCbsGlSubHeadSchDtlsTO(CbsGlSubHeadSchemeDetails cbsGlSubHeadSchDtls) {
        CbsGlSubHeadSchemeDetailsTO cbsGlSubHeadSchDtlsTo = new CbsGlSubHeadSchemeDetailsTO();

        cbsGlSubHeadSchDtlsTo.setCbsGlSubHeadSchemeDetailsPKTO(ObjectAdaptorMaster.adaptToCbsGlSubHeadSchemeDtlsPKTO(cbsGlSubHeadSchDtls.getCbsGlSubHeadSchemeDetailsPK()));

        cbsGlSubHeadSchDtlsTo.setSchemeType(cbsGlSubHeadSchDtls.getSchemeType());
        cbsGlSubHeadSchDtlsTo.setDfltFlag(cbsGlSubHeadSchDtls.getDfltFlag());
        cbsGlSubHeadSchDtlsTo.setDelFlag(cbsGlSubHeadSchDtls.getDelFlag());

        return cbsGlSubHeadSchDtlsTo;
    }

    public static CbsRefRecTypePK adaptToCbsRefRecTypePKEntity(CbsRefRecTypePKTO cbsRefRecTypePKTO) {
        CbsRefRecTypePK cbsRefRecTypePKE = new CbsRefRecTypePK();

        cbsRefRecTypePKE.setRefRecNo(cbsRefRecTypePKTO.getRefRecNo());
        cbsRefRecTypePKE.setRefCode(cbsRefRecTypePKTO.getRefCode());

        return cbsRefRecTypePKE;
    }

    public static CbsRefRecTypePKTO adaptToCbsRefRecTypePKTO(CbsRefRecTypePK cbsRefRecTypePK) {
        CbsRefRecTypePKTO cbsRefRecTypePKTo = new CbsRefRecTypePKTO();

        cbsRefRecTypePKTo.setRefRecNo(cbsRefRecTypePK.getRefRecNo());
        cbsRefRecTypePKTo.setRefCode(cbsRefRecTypePK.getRefCode());

        return cbsRefRecTypePKTo;
    }

    public static CbsRefRecType adaptToCbsRefRecTypeEntity(CbsRefRecTypeTO cbsRefRecTypeTO) {
        CbsRefRecType cbsRefRecTypeE = new CbsRefRecType();

        cbsRefRecTypeE.setCbsRefRecTypePK(ObjectAdaptorMaster.adaptToCbsRefRecTypePKEntity(cbsRefRecTypeTO.getCbsRefRecTypePKTO()));

        cbsRefRecTypeE.setRefDesc(cbsRefRecTypeTO.getRefDesc());
        cbsRefRecTypeE.setCreatedByUserId(cbsRefRecTypeTO.getCreatedByUserId());
        cbsRefRecTypeE.setCreationDate(cbsRefRecTypeTO.getCreationDate());

        cbsRefRecTypeE.setLastUpdatedByUserId(cbsRefRecTypeTO.getLastUpdatedByUserId());
        cbsRefRecTypeE.setLastUpdatedDate(cbsRefRecTypeTO.getLastUpdatedDate());
        cbsRefRecTypeE.setTsCnt(cbsRefRecTypeTO.getTsCnt());

        return cbsRefRecTypeE;
    }

    public static CbsRefRecTypeTO adaptToCbsRefRecTypeTO(CbsRefRecType cbsRefRecType) {
        CbsRefRecTypeTO cbsRefRecTypeTo = new CbsRefRecTypeTO();

        cbsRefRecTypeTo.setCbsRefRecTypePKTO(ObjectAdaptorMaster.adaptToCbsRefRecTypePKTO(cbsRefRecType.getCbsRefRecTypePK()));

        cbsRefRecTypeTo.setRefDesc(cbsRefRecType.getRefDesc());
        cbsRefRecTypeTo.setCreatedByUserId(cbsRefRecType.getCreatedByUserId());
        cbsRefRecTypeTo.setCreationDate(cbsRefRecType.getCreationDate());

        cbsRefRecTypeTo.setLastUpdatedByUserId(cbsRefRecType.getLastUpdatedByUserId());
        cbsRefRecTypeTo.setLastUpdatedDate(cbsRefRecType.getLastUpdatedDate());
        cbsRefRecTypeTo.setTsCnt(cbsRefRecType.getTsCnt());

        return cbsRefRecTypeTo;
    }

    public static Gltable adaptToGltableEntity(GltableTO gltableTO) {
        Gltable gltableE = new Gltable();

        gltableE.setAcNo(gltableTO.getAcNo());
        gltableE.setAcName(gltableTO.getAcName());
        gltableE.setPostflag(gltableTO.getPostflag());
        gltableE.setMsgflag(gltableTO.getMsgflag());

        return gltableE;
    }

    public static GltableTO adaptToGltableTO(Gltable gltable) {
        GltableTO gltableTo = new GltableTO();

        gltableTo.setAcNo(gltable.getAcNo());
        gltableTo.setAcName(gltable.getAcName());
        gltableTo.setPostflag(gltable.getPostflag());
        gltableTo.setMsgflag(gltable.getMsgflag());

        return gltableTo;
    }

    public static CbsSchemePopUpForms adaptToCBSSchemePopUpFormsEntity(CbsSchemePopUpFormsTO cbsSchemePopUpFormsTO) {
        CbsSchemePopUpForms cbsSchemePopUpForms = new CbsSchemePopUpForms();

        cbsSchemePopUpForms.setFormId(cbsSchemePopUpFormsTO.getFormId());
        cbsSchemePopUpForms.setFormName(cbsSchemePopUpFormsTO.getFormName());

        return cbsSchemePopUpForms;
    }

    public static CbsSchemePopUpFormsTO adaptToCBSSchemePopUpFormsTO(CbsSchemePopUpForms cbsSchemePopUpForms) {
        CbsSchemePopUpFormsTO cbsSchemePopUpFormsTO = new CbsSchemePopUpFormsTO();

        cbsSchemePopUpFormsTO.setFormId(cbsSchemePopUpForms.getFormId());
        cbsSchemePopUpFormsTO.setFormName(cbsSchemePopUpForms.getFormName());

        return cbsSchemePopUpFormsTO;
    }

    public static CbsSchemeTypeFormMapping adaptToCbsSchemeTypeFormMappingEntity(CbsSchemeTypeFormMappingTO cbsSchemeTypeFormMappingTO) {
        CbsSchemeTypeFormMapping cbsSchemeTypeFormMapping = new CbsSchemeTypeFormMapping();

        cbsSchemeTypeFormMapping.setId(cbsSchemeTypeFormMappingTO.getId());
        cbsSchemeTypeFormMapping.setSchemeType(cbsSchemeTypeFormMappingTO.getSchemeType());
        cbsSchemeTypeFormMapping.setFormId(cbsSchemeTypeFormMappingTO.getFormId());

        return cbsSchemeTypeFormMapping;
    }

    public static CbsSchemeTypeFormMappingTO adaptToCbsSchemeTypeFormMappingTO(CbsSchemeTypeFormMapping cbsSchemeTypeFormMapping) {
        CbsSchemeTypeFormMappingTO cbsSchemeTypeFormMappingTO = new CbsSchemeTypeFormMappingTO();

        cbsSchemeTypeFormMappingTO.setId(cbsSchemeTypeFormMapping.getId());
        cbsSchemeTypeFormMappingTO.setSchemeType(cbsSchemeTypeFormMapping.getSchemeType());
        cbsSchemeTypeFormMappingTO.setFormId(cbsSchemeTypeFormMapping.getFormId());

        return cbsSchemeTypeFormMappingTO;
    }

    public static BranchMaster adaptToBranchMasterEntity(BranchMasterTO branchMasterTO) {
        BranchMaster branchMaster = new BranchMaster();
        branchMaster.setBranchName(branchMasterTO.getBranchName());
        branchMaster.setAddress(branchMasterTO.getAddress());
        branchMaster.setBrnCode(branchMasterTO.getBrnCode());
        branchMaster.setAlphaCode(branchMasterTO.getAlphaCode());
        branchMaster.setCity(branchMasterTO.getCity());
        branchMaster.setState(branchMasterTO.getState());
        branchMaster.setROffice(branchMasterTO.getrOffice());
        branchMaster.setPincode(branchMasterTO.getPincode());
        branchMaster.setEnterBy(branchMasterTO.getEnterBy());
        branchMaster.setExCounter(branchMasterTO.getExCounter());
        branchMaster.setParentBrnCode(branchMasterTO.getParentBrnCode());
        return branchMaster;
    }

    public static BranchMasterTO adaptToBranchMasterTO(BranchMaster branchMaster) {
        BranchMasterTO branchMasterTO = new BranchMasterTO();
        branchMasterTO.setBranchName(branchMaster.getBranchName());
        branchMasterTO.setAddress(branchMaster.getAddress());
        branchMasterTO.setBrnCode(branchMaster.getBrnCode());
        branchMasterTO.setAlphaCode(branchMaster.getAlphaCode());
        branchMasterTO.setCity(branchMaster.getCity());
        branchMasterTO.setState(branchMaster.getState());
        branchMasterTO.setrOffice(branchMaster.getROffice());
        branchMasterTO.setPincode(branchMaster.getPincode());
        branchMasterTO.setEnterBy(branchMaster.getEnterBy());
        branchMasterTO.setExCounter(branchMaster.getExCounter());
        branchMasterTO.setParentBrnCode(branchMaster.getParentBrnCode());
        return branchMasterTO;
    }

    public static Bnkadd adaptToBnkaddEntity(BnkaddTO bnkaddTO) {
        Bnkadd bnkadd = new Bnkadd();
        bnkadd.setBankname(bnkaddTO.getBankname());
        bnkadd.setBankaddress(bnkaddTO.getBankaddress());
        bnkadd.setVersion(bnkaddTO.getVersion());
        bnkadd.setAlphacode(bnkaddTO.getAlphacode());
        bnkadd.setMicr(bnkaddTO.getMicr());
        bnkadd.setMicrcode(bnkaddTO.getMicrcode());
        bnkadd.setBranchcode(bnkaddTO.getBranchcode());
        return bnkadd;
    }

    public static BnkaddTO adaptToBnkaddTO(Bnkadd bnkadd) {
        BnkaddTO bnkaddTO = new BnkaddTO();
        bnkaddTO.setBankname(bnkadd.getBankname());
        bnkaddTO.setBankaddress(bnkadd.getBankaddress());
        bnkaddTO.setVersion(bnkadd.getVersion());
        bnkaddTO.setAlphacode(bnkadd.getAlphacode());
        bnkaddTO.setMicr(bnkadd.getMicr());
        bnkaddTO.setMicrcode(bnkadd.getMicrcode());
        bnkaddTO.setBranchcode(bnkadd.getBranchcode());
        return bnkaddTO;
    }

    public static ParameterinfoReport adaptToParameterinfoReportEntity(ParameterinfoReportTO parameterinfoReportTO) {
        ParameterinfoReport parameterinfoReport = new ParameterinfoReport();

        parameterinfoReport.setReportName(parameterinfoReportTO.getReportName());
        parameterinfoReport.setCode(parameterinfoReportTO.getCode());

        return parameterinfoReport;
    }

    public static ParameterinfoReportTO adaptToParameterinfoReportTO(ParameterinfoReport parameterinfoReport) {
        ParameterinfoReportTO parameterinfoReportTO = new ParameterinfoReportTO();

        parameterinfoReportTO.setReportName(parameterinfoReport.getReportName());
        parameterinfoReportTO.setCode(parameterinfoReport.getCode());

        return parameterinfoReportTO;
    }

    public static CbsChargeDetailTO adaptToCbsChargeDetailTO(CbsChargeDetail cbsChargeDetail) {
        CbsChargeDetailTO cbsChargeDetailTO = new CbsChargeDetailTO();
        cbsChargeDetailTO.setChargeType(cbsChargeDetail.getChargeType());
        cbsChargeDetailTO.setChargeName(cbsChargeDetail.getCbsChargeDetailPK().getChargeName());
        cbsChargeDetailTO.setAmount(cbsChargeDetail.getAmt());
        cbsChargeDetailTO.setCreditHead(cbsChargeDetail.getCrGlHead().trim());
        cbsChargeDetailTO.setFrequency(cbsChargeDetail.getSmsFrequency().trim());
        return cbsChargeDetailTO;
    }
}
