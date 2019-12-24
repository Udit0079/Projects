/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.sms;

import com.cbs.dto.NpciFileDto;
import com.cbs.dto.master.BnkaddTO;
import com.cbs.dto.master.BranchMasterTO;
import com.cbs.dto.master.CbsChargeDetailTO;
import com.cbs.dto.master.GltableTO;
import com.cbs.dto.sms.BulkSmsTo;
import com.cbs.dto.sms.MbChargePostingIndividualHistoryTO;
import com.cbs.dto.sms.MbServicesDescriptionTO;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.MbSubscriberServicesTO;
import com.cbs.dto.sms.MbSubscriberTabTO;
import com.cbs.dto.sms.ProductWiseSmsDetailsPojo;
import com.cbs.dto.sms.SmsPostingTo;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.entity.sms.MbChargeMaster;
import com.cbs.entity.sms.MbSubscriberTab;
import com.cbs.exception.ApplicationException;
import com.cbs.sms.service.SmsType;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface SmsManagementFacadeRemote {

    public String checkSubscriberDetails(String acno) throws ApplicationException;

    public String findNameByAcno(String acno) throws ApplicationException;

    public MbSubscriberTabTO getSubscriberDetails(String acno) throws ApplicationException;

    public List<MbSubscriberServicesTO> getSubsCriberServices(String acno) throws ApplicationException;

    public String saveUpdateSubscriberAndServicesDetails(String acno, MbSubscriberTabTO mbSubscriberTabTO, List<MbSubscriberServicesTO> referenceList, List<MbSubscriberServicesTO> onRequestList, String pin, String mobileNo, String flag) throws ApplicationException;

    public List<MbSubscriberTabTO> getAllMobileNo(String orgnBrCode) throws ApplicationException;

//    public String sendMessageToAll(List<MbPushMsgTabTO> mbPushMsgTabTOs) throws ApplicationException;
//    public String sendMessageToSingleSubscriber(MbPushMsgTabTO mbPushMsgTabTO) throws ApplicationException;
    public java.lang.String saveChargeMasterDetails(com.cbs.entity.sms.MbChargeMaster chargeMaster) throws com.cbs.exception.ApplicationException;

    public java.util.List getDataforChargePosting(java.lang.String messageType, String acno, String orgnBrCode) throws com.cbs.exception.ApplicationException;

    public MbChargeMaster getChargePerMessage() throws com.cbs.exception.ApplicationException;

//    public Date getMaxToDateForChargePostingByAcno(java.lang.String acno, String messageType) throws com.cbs.exception.ApplicationException;
    public Date getMaxToDateOfSmsPosting(java.lang.String acno) throws com.cbs.exception.ApplicationException;

//    public Date getMaxToDateForAllPosting(String brncode) throws ApplicationException;
    public java.lang.String postChargeHistory(int fixedOrPerSms, int chargeBasedOnParam,
            List<MbChargePostingIndividualHistoryTO> entityList, String type, String orgnBrCode,
            String creditedGLHead, String frDt, String toDt, String nature, String acType)
            throws com.cbs.exception.ApplicationException;

//    public java.lang.String postFixedChargeHistory(int fixedOrPerSms, java.util.List<MbChargePostingIndividualHistoryTO> entityList,
//            String flag, String orgnBrCode, String crHead, String frDt, String toDt,
//            String nature, String acType) throws com.cbs.exception.ApplicationException;
    public List<MbSmsSenderBankDetailTO> getBankAndSenderDetail() throws ApplicationException;

    public MbServicesDescriptionTO getEntityByServiceCode(Integer code) throws ApplicationException;

    public BranchMasterTO branchmasterEntityByBrncode(Integer brncode) throws ApplicationException;

    public BnkaddTO bankNameByBnkAdd(String alphacode) throws ApplicationException;

    public List getAllStaffMobileNo(String orgnBrCode) throws ApplicationException;

    public GltableTO getGlTableByAcno(String acno) throws ApplicationException;

    public String isExistMobileAcCodeAndPin(String mobile, String accode, String pin) throws ApplicationException;

    public List<CbsChargeDetailTO> getCbsChargeDetail(String chargeType) throws ApplicationException;

    public List<CbsChargeDetailTO> getCbsChargeDetailByTypeNameAndActype(String chargeType, String chargeName, String acType) throws ApplicationException;

//    public List<SmsPostingTo> getAllAccountOfBarnch(String frDt, String toDt, String brCode, String nature, String acType, String type) throws ApplicationException;
    public List getAllAccountByAuthAndAuthStatus(String auth, String authStatus) throws ApplicationException;

    public List getAllAccountToVerify(String orginBrCode) throws ApplicationException;

    public MbSubscriberTab checkAccountByAuthAndAuthStatus(String auth, String authStatus, String acno) throws ApplicationException;

    public String activateOrDeactivate(String acno, String function, String enterBy) throws ApplicationException;

    public String deleteOrAuthorize(String acno, String function, String authBy) throws ApplicationException;

    public List getSmsChgFrequency(int chargeBasedOnParam, String nature, String acType, String orgnBrCode) throws ApplicationException;

    public String getCodeByReportName(String reportName) throws ApplicationException;

    public boolean isValidAcnoForSms(String msgType, String acno, int tranType, int ty,
            double amt) throws ApplicationException;

    public List getTaxDetails() throws ApplicationException;

    public void sendSmsToBatch(List<SmsToBatchTo> list) throws ApplicationException;

    public void sendTransactionalSms(SmsType template, String acno, Integer tranType,
            Integer ty, Double amount, String txnDt, String details) throws ApplicationException;

    public void sendPromotionalSms(String acno, String promoMsg, String promoMobile,
            String bankName, String userName) throws ApplicationException;

    public void sendSms(TransferSmsRequestTo transferSmsRequestTo) throws ApplicationException;

    public void trfSmsRequestToBatch(List<TransferSmsRequestTo> list) throws ApplicationException;

    public boolean isActiveAccountForSms(String acno) throws ApplicationException;

    public List allAccountMessage(int chgBasedOn, String type, String acno, String nature, String acType,
            String frDt, String toDt, String orgnBrCode) throws ApplicationException;

    public void sendMinimumBalanceSms(List<TransferSmsRequestTo> smsList) throws ApplicationException;

    public void sendAtmSms(List<TransferSmsRequestTo> smsList) throws ApplicationException;

//    public String individualPerSmsBasis(List<MbChargePostingIndividualHistoryTO> entityList, String flag,
//            String messageType, String orgnBrCode) throws ApplicationException;
    public List<SmsPostingTo> getAccountSmsDetail(String acno) throws ApplicationException;

    public List getAllAccountNatureWithoutPO() throws ApplicationException;

    public List getAllAccountCodeByNature(String acNature) throws ApplicationException;

    public List<SmsPostingTo> getAccountDetail(int chgBasedOn, String frDt, String toDt, String brCode, String nature,
            String acType) throws ApplicationException;

    public List<ProductWiseSmsDetailsPojo> getProductWiseSmSDetails(String Branch, String accNature, String accountType,
            String fromdate, String todate) throws ApplicationException;

//    public String sendBulkSmsByGupshup(List<File> files, List<BulkSmsTo> bulkSmsList, String bulkMode, File originalFile, String userName,
//            String orgnCode, String todayDt) throws ApplicationException;
//
//    public String sendBulkSmsByDigialaya(List<String> allXmlString, List<BulkSmsTo> bulkSmsList, String bulkMode,
//            File originalFile, String userName, String orgnCode, String todayDt) throws ApplicationException;
    public String sendBulkSms(String vendorName, List<File> gupShupFiles, List<BulkSmsTo> bulkSmsList, List<String> digialayaAllXmlString,
            String bulkMode, File originalFile, String userName, String orgnCode, String todayDt) throws ApplicationException;

    public List<NpciFileDto> getBulkSmsData(String fromDt, String toDt) throws ApplicationException;
    
    public void sendNeftRtgsTxnSms(SmsType template, String acno, Integer tranType,
            Integer ty, Double amount, String txnDt, String txnType, String beneName) throws ApplicationException;
}
