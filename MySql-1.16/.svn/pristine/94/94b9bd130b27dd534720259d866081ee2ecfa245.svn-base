/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.adaptor;

import com.cbs.dto.sms.MbPushMsgTabTO;
import com.cbs.dto.sms.MbServicesDescriptionTO;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.MbSubscriberServicesPKTO;
import com.cbs.dto.sms.MbSubscriberServicesTO;
import com.cbs.dto.sms.MbSubscriberTabTO;
import com.cbs.entity.sms.MbPushMsgTab;
import com.cbs.entity.sms.MbServicesDescription;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.entity.sms.MbSubscriberServices;
import com.cbs.entity.sms.MbSubscriberServicesPK;
import com.cbs.entity.sms.MbSubscriberTab;

public class ObjectAdaptorSms {

    /**
     *
     * @param mbSubscriberTabTO
     * @return
     */
    public static MbSubscriberTab adaptToMbSubscriberTabEntity(MbSubscriberTabTO mbSubscriberTabTO) {
        MbSubscriberTab mbSubscriberTab = new MbSubscriberTab();
        mbSubscriberTab.setAcno(mbSubscriberTabTO.getAcno());
        mbSubscriberTab.setAcnoType(mbSubscriberTabTO.getAcnoType());
        mbSubscriberTab.setAuth(mbSubscriberTabTO.getAuth());
        mbSubscriberTab.setAuthBy(mbSubscriberTabTO.getAuthBy());
        
        mbSubscriberTab.setBillDeductionAcno(mbSubscriberTabTO.getBillDeductionAcno());
        mbSubscriberTab.setCashCrLimit(mbSubscriberTabTO.getCashCrLimit());
        mbSubscriberTab.setCashDrLimit(mbSubscriberTabTO.getCashDrLimit());
        mbSubscriberTab.setCreatedDate(mbSubscriberTabTO.getCreatedDate());
        
        mbSubscriberTab.setEnterBy(mbSubscriberTabTO.getEnterBy());
        mbSubscriberTab.setMobileNo(mbSubscriberTabTO.getMobileNo());
        mbSubscriberTab.setPinNo(mbSubscriberTabTO.getPinNo());
        mbSubscriberTab.setStatus(mbSubscriberTabTO.getStatus());
        
        mbSubscriberTab.setTrfCrLimit(mbSubscriberTabTO.getTrfCrLimit());
        mbSubscriberTab.setTrfDrLimit(mbSubscriberTabTO.getTrfDrLimit());
        mbSubscriberTab.setClgCrLimit(mbSubscriberTabTO.getClgCrLimit());
        mbSubscriberTab.setClgDrLimit(mbSubscriberTabTO.getClgDrLimit());
        mbSubscriberTab.setAuthStatus(mbSubscriberTabTO.getAuthStatus());
        mbSubscriberTab.setInterestLimit(mbSubscriberTabTO.getInterestLimit());
        mbSubscriberTab.setChargeLimit(mbSubscriberTabTO.getChargeLimit());
        mbSubscriberTab.setUpdateBy(mbSubscriberTabTO.getUpdateBy());
        mbSubscriberTab.setUpdateDt(mbSubscriberTabTO.getUpdateDt());
        
        return mbSubscriberTab;
    }

    /**
     *
     * @param mbSubscriberTab
     * @return
     */
    public static MbSubscriberTabTO adaptToMbSubscriberTabTO(MbSubscriberTab mbSubscriberTab) {
        MbSubscriberTabTO mbSubscriberTabTO = new MbSubscriberTabTO();
        mbSubscriberTabTO.setAcno(mbSubscriberTab.getAcno());
        mbSubscriberTabTO.setAcnoType(mbSubscriberTab.getAcnoType());
        mbSubscriberTabTO.setAuth(mbSubscriberTab.getAuth());
        
        mbSubscriberTabTO.setAuthBy(mbSubscriberTab.getAuthBy());
        mbSubscriberTabTO.setBillDeductionAcno(mbSubscriberTab.getBillDeductionAcno());
        mbSubscriberTabTO.setCashCrLimit(mbSubscriberTab.getCashCrLimit());
        mbSubscriberTabTO.setCashDrLimit(mbSubscriberTab.getCashDrLimit());
        
        mbSubscriberTabTO.setClgCrLimit(mbSubscriberTab.getClgCrLimit());
        mbSubscriberTabTO.setClgDrLimit(mbSubscriberTab.getClgDrLimit());
        mbSubscriberTabTO.setCreatedDate(mbSubscriberTab.getCreatedDate());
        mbSubscriberTabTO.setEnterBy(mbSubscriberTab.getEnterBy());
        mbSubscriberTabTO.setAuthStatus(mbSubscriberTab.getAuthStatus());
        
        mbSubscriberTabTO.setMobileNo(mbSubscriberTab.getMobileNo());
        mbSubscriberTabTO.setPinNo(mbSubscriberTab.getPinNo());
        mbSubscriberTabTO.setStatus(mbSubscriberTab.getStatus());
        mbSubscriberTabTO.setTrfCrLimit(mbSubscriberTab.getTrfCrLimit());
        mbSubscriberTabTO.setTrfDrLimit(mbSubscriberTab.getTrfDrLimit());
        mbSubscriberTabTO.setInterestLimit(mbSubscriberTab.getInterestLimit());
        mbSubscriberTabTO.setChargeLimit(mbSubscriberTab.getChargeLimit());
        
        return mbSubscriberTabTO;
    }
    
    public static MbSubscriberServices adaptToMbSubscriberServicesEntity(MbSubscriberServicesTO mbSubscriberServicesTO) {
        MbSubscriberServices mbSubscriberServices = new MbSubscriberServices();
        mbSubscriberServices.setMbSubscriberServicesPK(adapToMbSubscriberServicesPKEntity(mbSubscriberServicesTO.getMbSubscriberServicesPKTO()));
        mbSubscriberServices.setMbSubscriberTab(mbSubscriberServicesTO.getMbSubscriberTab());
        return mbSubscriberServices;
    }
    
    public static MbSubscriberServicesTO adapToMbSubscriberServicesTO(MbSubscriberServices mbSubscriberServices) {
        MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
        mbSubscriberServicesTO.setMbSubscriberServicesPKTO(adapToMbSubscriberServicesPKTO(mbSubscriberServices.getMbSubscriberServicesPK()));
        mbSubscriberServicesTO.setMbSubscriberTab(mbSubscriberServices.getMbSubscriberTab());
        return mbSubscriberServicesTO;
    }
    
    public static MbSubscriberServicesPK adapToMbSubscriberServicesPKEntity(MbSubscriberServicesPKTO mbSubscriberServicesPKTO) {
        MbSubscriberServicesPK mbSubscriberServicesPK = new MbSubscriberServicesPK();
        
        mbSubscriberServicesPK.setAcno(mbSubscriberServicesPKTO.getAcno());
        mbSubscriberServicesPK.setServices(Short.parseShort(mbSubscriberServicesPKTO.getVar()));
        return mbSubscriberServicesPK;
        
    }
    
    public static MbSubscriberServicesPKTO adapToMbSubscriberServicesPKTO(MbSubscriberServicesPK mbSubscriberServicesPK) {
        MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();
        mbSubscriberServicesPKTO.setAcno(mbSubscriberServicesPK.getAcno());
        mbSubscriberServicesPKTO.setServices(mbSubscriberServicesPK.getServices());
        return mbSubscriberServicesPKTO;
    }
    
    public static MbPushMsgTab adapToMbPushMsgTabEntity(MbPushMsgTabTO mbPushMsgTabTO) {
        MbPushMsgTab mbPushMsgTab = new MbPushMsgTab();
        
        mbPushMsgTab.setMessageId(mbPushMsgTabTO.getMessageId() == null ? "" : mbPushMsgTabTO.getMessageId());
        mbPushMsgTab.setMobile(mbPushMsgTabTO.getMobile());
        mbPushMsgTab.setAcno(mbPushMsgTabTO.getAcno());
        mbPushMsgTab.setMessage(mbPushMsgTabTO.getMessage());
        mbPushMsgTab.setMessageStatus(mbPushMsgTabTO.getMessageStatus());
        mbPushMsgTab.setMessageType(mbPushMsgTabTO.getMessageType());
        mbPushMsgTab.setEnterBy(mbPushMsgTabTO.getEnterBy());
        
        return mbPushMsgTab;
    }
    
    public static MbPushMsgTabTO adapToMbPushMsgTabTO(MbPushMsgTab mbPushMsgTab) {
        MbPushMsgTabTO mbPushMsgTabTO = new MbPushMsgTabTO();
        
        mbPushMsgTabTO.setMessageId(mbPushMsgTab.getMessageId());
        mbPushMsgTabTO.setMobile(mbPushMsgTab.getMobile());
        mbPushMsgTabTO.setAcno(mbPushMsgTab.getAcno());
        mbPushMsgTabTO.setMessage(mbPushMsgTab.getMessage());
        mbPushMsgTabTO.setMessageStatus(mbPushMsgTab.getMessageStatus());
        mbPushMsgTabTO.setMessageType(mbPushMsgTab.getMessageType());
        mbPushMsgTabTO.setDt(mbPushMsgTab.getDt());
        mbPushMsgTabTO.setEnterBy(mbPushMsgTab.getEnterBy());
        
        return mbPushMsgTabTO;
    }
    
    public static MbServicesDescription adaptToMbServicesDescriptionEntity(MbServicesDescriptionTO mbServicesDescriptionTO) {
        MbServicesDescription mbServicesDescription = new MbServicesDescription();
        mbServicesDescription.setServiceCode(mbServicesDescriptionTO.getServiceCode());
        mbServicesDescription.setServiceName(mbServicesDescriptionTO.getServiceName());
        mbServicesDescription.setServiceType(mbServicesDescriptionTO.getServiceType());
        mbServicesDescription.setServiceStatus(mbServicesDescriptionTO.getServiceStatus());
        mbServicesDescription.setEnterBy(mbServicesDescriptionTO.getEnterby());
        mbServicesDescription.setLastUpdateBy(mbServicesDescriptionTO.getLastUpdateBy());
        mbServicesDescription.setLastUpdateDt(mbServicesDescriptionTO.getLastUpdateDt());
        return mbServicesDescription;
    }
    
    public static MbServicesDescriptionTO adaptToMbServicesDescriptionTO(MbServicesDescription mbServicesDescription) {
        MbServicesDescriptionTO mbServicesDescriptionTO = new MbServicesDescriptionTO();
        mbServicesDescriptionTO.setServiceCode(mbServicesDescription.getServiceCode());
        mbServicesDescriptionTO.setServiceName(mbServicesDescription.getServiceName());
        mbServicesDescriptionTO.setServiceType(mbServicesDescription.getServiceType());
        mbServicesDescriptionTO.setServiceStatus(mbServicesDescription.getServiceStatus());
        mbServicesDescriptionTO.setEnterby(mbServicesDescription.getEnterBy());
        mbServicesDescriptionTO.setLastUpdateBy(mbServicesDescription.getLastUpdateBy());
        mbServicesDescriptionTO.setLastUpdateDt(mbServicesDescription.getLastUpdateDt());
        return mbServicesDescriptionTO;
    }
    
    public static MbSmsSenderBankDetail adaptToMbSmsSenderBankDetailEntity(MbSmsSenderBankDetailTO mbSmsSenderbankDetailTO) {
        MbSmsSenderBankDetail mbSmsSenderBankDetail = new MbSmsSenderBankDetail();
        mbSmsSenderBankDetail.setBankCode(mbSmsSenderbankDetailTO.getBankcode());
        
        mbSmsSenderBankDetail.setSmsShortCode(mbSmsSenderbankDetailTO.getShortcode());
        mbSmsSenderBankDetail.setSmsSender(mbSmsSenderbankDetailTO.getSender());
        mbSmsSenderBankDetail.setSenderId(mbSmsSenderbankDetailTO.getSenderId());
        mbSmsSenderBankDetail.setTemplateBankName(mbSmsSenderbankDetailTO.getTemplateBankName());
        mbSmsSenderBankDetail.setBankShortName(mbSmsSenderbankDetailTO.getBankShortName());
        return mbSmsSenderBankDetail;
    }
    
    public static MbSmsSenderBankDetailTO adaptToMbSmsSenderBankDetailTO(MbSmsSenderBankDetail mbSmsSenderBankDetail) {
        MbSmsSenderBankDetailTO mbSmsSenderbankDetailTO = new MbSmsSenderBankDetailTO();
        mbSmsSenderbankDetailTO.setBankcode(mbSmsSenderBankDetail.getBankCode());
        mbSmsSenderbankDetailTO.setShortcode(mbSmsSenderBankDetail.getSmsShortCode());
        mbSmsSenderbankDetailTO.setSender(mbSmsSenderBankDetail.getSmsSender());
        mbSmsSenderbankDetailTO.setSenderId(mbSmsSenderBankDetail.getSenderId());
        mbSmsSenderbankDetailTO.setTemplateBankName(mbSmsSenderBankDetail.getTemplateBankName());
        mbSmsSenderbankDetailTO.setBankShortName(mbSmsSenderBankDetail.getBankShortName());
        mbSmsSenderbankDetailTO.setSmsVendor(mbSmsSenderBankDetail.getSmsVendor());
        return mbSmsSenderbankDetailTO;
    }
}
