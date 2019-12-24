/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.delegate;

import com.cbs.dto.master.BnkaddTO;
import com.cbs.dto.master.BranchMasterTO;
import com.cbs.dto.master.CbsChargeDetailTO;
import com.cbs.dto.master.GltableTO;
import com.cbs.dto.sms.MbChargePostingIndividualHistoryTO;
import com.cbs.dto.sms.MbPushMsgTabTO;
import com.cbs.dto.sms.MbServicesDescriptionTO;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.MbSubscriberServicesTO;
import com.cbs.dto.sms.MbSubscriberTabTO;
import com.cbs.dto.sms.SmsPostingTo;
import com.cbs.entity.sms.MbChargeMaster;
import com.cbs.entity.sms.MbPushMsgTab;
import com.cbs.entity.sms.MbSubscriberTab;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.sms.RequestReceiverFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author ANKIT VERMA
 */
public class SmsManagementDelegate {

    private final String jndiHomeName = "SmsManagementFacade";
    private SmsManagementFacadeRemote beanRemote = null;
    private final String jndiHome = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote remote = null;
    private final String jndiTest = "RequestReceiverFacade";
    private RequestReceiverFacadeRemote testRemote = null;

    /**
     * No argument constructor
     *
     * @throws ServiceLocatorException
     */
    public SmsManagementDelegate() throws ServiceLocatorException {
        beanRemote = (SmsManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
        remote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHome);
        testRemote = (RequestReceiverFacadeRemote) ServiceLocator.getInstance().lookup(jndiTest);
    }

    public String saveUpdateSubscriberAndServicesDetails(String acno, MbSubscriberTabTO mbSubscriberTabTO, List<MbSubscriberServicesTO> referenceList, List<MbSubscriberServicesTO> onRequestList, String pin, String mobileNo, String flag) throws ApplicationException {
        try {
            return beanRemote.saveUpdateSubscriberAndServicesDetails(acno, mbSubscriberTabTO, referenceList, onRequestList, pin, mobileNo, flag);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String activateOrDeactivate(String acno, String function, String enterBy) throws ApplicationException {
        try {
            return beanRemote.activateOrDeactivate(acno, function, enterBy);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String deleteOrAuthorize(String acno, String function, String authBy) throws ApplicationException {
        try {
            return beanRemote.deleteOrAuthorize(acno, function, authBy);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String checkSubscriberDetails(String acno) throws ApplicationException {
        String msg = "";
        try {
            msg = beanRemote.checkSubscriberDetails(acno);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        } catch (Exception e) {
        }
        return msg;
    }

    public String getNameByAcno(String acno) throws ApplicationException {
        String msg = "";
        try {
            msg = beanRemote.findNameByAcno(acno);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public MbSubscriberTabTO getSubscriberDetails(String acno) throws ApplicationException {
        MbSubscriberTabTO mbSubscriberTabTO = new MbSubscriberTabTO();
        try {
            mbSubscriberTabTO = beanRemote.getSubscriberDetails(acno);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return mbSubscriberTabTO;
    }

    public List<MbSubscriberServicesTO> getSubsCriberServices(String acno) throws ApplicationException {
        List<MbSubscriberServicesTO> mbSubscriberServicesTOs = new ArrayList<MbSubscriberServicesTO>();
        try {
            mbSubscriberServicesTOs = beanRemote.getSubsCriberServices(acno);

        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return mbSubscriberServicesTOs;
    }

    public List getAllMobileNo(String orgnBrCode) throws ApplicationException {
        List mbSubscriberTabTOs = new ArrayList();
        try {
            mbSubscriberTabTOs = beanRemote.getAllMobileNo(orgnBrCode);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return mbSubscriberTabTOs;
    }

//    public String sendMessageToAll(List<MbPushMsgTabTO> mbPushMsgTabTOs) throws ApplicationException {
//        String msg = "";
//        try {
//            msg = beanRemote.sendMessageToAll(mbPushMsgTabTOs);
//        } catch (Exception e) {
//            msg = "Message Not Sent";
//        }
//        return msg;
//    }

//    public long getMaxTxnId() throws ApplicationException {
//        long maxTxnId = 0;
//        try {
//            maxTxnId = beanRemote.getMaxTxnId();
//        } catch (ApplicationException e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return maxTxnId;
//    }
//    public String sendMessageToSingleSubscriber(MbPushMsgTabTO mbPushMsgTabTO) throws ApplicationException {
//        String msg = "";
//        try {
//            msg = beanRemote.sendMessageToSingleSubscriber(mbPushMsgTabTO);
//        } catch (Exception e) {
//            msg = "Message Not Sent";
//        }
//        return msg;
//    }

    public String saveChargeMasterDetails(MbChargeMaster chargeMaster) throws ApplicationException {
        String msg = "";
        try {
            msg = beanRemote.saveChargeMasterDetails(chargeMaster);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public List<MbPushMsgTab> getDataforChargePosting(String messageType, String acno, String orgnBrCode) throws ApplicationException {
        List<MbPushMsgTab> dataforChargePosting = new ArrayList<MbPushMsgTab>();
        try {
            dataforChargePosting = beanRemote.getDataforChargePosting(messageType, acno, orgnBrCode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataforChargePosting;
    }

    public MbChargeMaster getChargePerMessage() throws ApplicationException {
        try {
            return beanRemote.getChargePerMessage();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

//    public Date getMaxToDateForChargePostingByAcno(String acno, String messageType) throws ApplicationException {
//        Date date = null;
//        try {
//            date = beanRemote.getMaxToDateForChargePostingByAcno(acno, messageType);
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return date;
//    }
    public Date getMaxToDateOfSmsPosting(String acno) throws ApplicationException {
        Date date = null;
        try {
            date = beanRemote.getMaxToDateOfSmsPosting(acno);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return date;
    }

//    public Date getMaxToDateForAllPosting(String brncode) throws ApplicationException {
//        Date date = null;
//        try {
//            date = beanRemote.getMaxToDateForAllPosting(brncode);
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return date;
//    }
//    public String postChargeHistory(List<MbChargePostingIndividualHistoryTO> entityList, String flag, String messageType, String orgnBrCode) throws ApplicationException {
//        String msg = "";
//        try {
//            if (flag.equalsIgnoreCase("ALL")) {
//                msg = beanRemote.postChargeHistory(entityList, flag, messageType, orgnBrCode);
//            } else {
//                msg = beanRemote.individualPerSmsBasis(entityList, flag, messageType, orgnBrCode);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApplicationException(e.getMessage());
//        }
//        return msg;
//    }
    public String postChargeHistory(int fixedOrPerSms, int chargeBasedOnParam,
            List<MbChargePostingIndividualHistoryTO> entityList, String type, String orgnBrCode,
            String creditedGLHead, String frDt, String toDt, String nature, String acType) throws ApplicationException {
        String msg = "";
        try {
            msg = beanRemote.postChargeHistory(fixedOrPerSms, chargeBasedOnParam,
                    entityList, type, orgnBrCode, creditedGLHead, frDt, toDt, nature, acType);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

//    public String postFixedChargeHistory(int fixedOrPerSms, List<MbChargePostingIndividualHistoryTO> entityList, String type,
//            String orgnBrCode, String crHead, String frDt, String toDt, String nature,
//            String acType) throws ApplicationException {
//        String msg = "";
//        try {
//            msg = beanRemote.postFixedChargeHistory(fixedOrPerSms, entityList, type, orgnBrCode,
//                    crHead, frDt, toDt, nature, acType);
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return msg;
//    }
    public String getOriginBranch() throws ApplicationException {
        try {
            return (remote.getOriginBranch());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void testPullMessages(String mobileNo, String message) throws ApplicationException {
        try {
            testRemote.processOnRequestMessage(mobileNo, message);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<MbSmsSenderBankDetailTO> getBankAndSenderDetail() throws ApplicationException {
        List<MbSmsSenderBankDetailTO> resultList = new ArrayList<MbSmsSenderBankDetailTO>();
        try {
            resultList = beanRemote.getBankAndSenderDetail();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public MbServicesDescriptionTO getEntityByServiceCode(Integer code) throws ApplicationException {
        MbServicesDescriptionTO to = null;
        try {
            to = beanRemote.getEntityByServiceCode(code);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return to;
    }

    public BranchMasterTO branchmasterEntityByBrncode(Integer brncode) throws ApplicationException {
        BranchMasterTO to = null;
        try {
            to = beanRemote.branchmasterEntityByBrncode(brncode);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return to;
    }

    public BnkaddTO bankNameByBnkAdd(String alphacode) throws ApplicationException {
        BnkaddTO to = null;
        try {
            to = beanRemote.bankNameByBnkAdd(alphacode);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return to;
    }

    public List getAllStaffMobileNo(String orgnBrCode) throws ApplicationException {
        List mbSubscriberTabTOs = new ArrayList();
        try {
            mbSubscriberTabTOs = beanRemote.getAllStaffMobileNo(orgnBrCode);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return mbSubscriberTabTOs;
    }

    public GltableTO getGltableToByAcno(String acno) throws ApplicationException {
        try {
            return beanRemote.getGlTableByAcno(acno);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String isExistMobileAcCodeAndPin(String mobile, String accode, String pin) throws ApplicationException {
        try {
            return beanRemote.isExistMobileAcCodeAndPin(mobile, accode, pin);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<CbsChargeDetailTO> getCbsChargeDetail(String chargeType) throws ApplicationException {
        try {
            return beanRemote.getCbsChargeDetail(chargeType);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<CbsChargeDetailTO> getCbsChargeDetailByTypeNameAndActype(String chargeType, String chargeName, String acType) throws ApplicationException {
        try {
            return beanRemote.getCbsChargeDetailByTypeNameAndActype(chargeType, chargeName, acType);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

//    public List<String[]> getAllAccountOfBarnch(String frDt, String toDt, String brCode) throws ApplicationException {
//        try {
//            return beanRemote.getAllAccountOfBarnch(frDt, toDt, brCode);
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//    }
    public List<SmsPostingTo> getAccountDetail(int chgBasedOn, String frDt, String toDt, String brCode,
            String nature, String acType) throws ApplicationException {
        List<SmsPostingTo> list = new CopyOnWriteArrayList<SmsPostingTo>();
        try {
            list = beanRemote.getAccountDetail(chgBasedOn, frDt, toDt, brCode, nature, acType);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public List getAllAccountByAuthAndAuthStatus(String auth, String authStatus) throws ApplicationException {
        try {
            return beanRemote.getAllAccountByAuthAndAuthStatus(auth, authStatus);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAllAccountToVerify(String orginBrCode) throws ApplicationException {
        try {
            return beanRemote.getAllAccountToVerify(orginBrCode);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public MbSubscriberTab checkAccountByAuthAndAuthStatus(String auth, String authStatus, String acno) throws ApplicationException {
        try {
            return beanRemote.checkAccountByAuthAndAuthStatus(auth, authStatus, acno);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<SmsPostingTo> allAccountMessage(int chgBasedOn, String type, String acno, String nature, String acType,
            String frDt, String toDt, String orgnBrCode) throws ApplicationException {
        List<SmsPostingTo> dataList = new ArrayList<SmsPostingTo>();
        try {
            dataList = beanRemote.allAccountMessage(chgBasedOn, type, acno, nature, acType, frDt, toDt, orgnBrCode);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<SmsPostingTo> getAccountSmsDetail(String acno) throws ApplicationException {
        List<SmsPostingTo> dataList = new ArrayList<SmsPostingTo>();
        try {
            dataList = beanRemote.getAccountSmsDetail(acno);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
}
