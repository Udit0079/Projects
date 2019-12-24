/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.sms;

import com.cbs.adaptor.ObjectAdaptorAccount;
import com.cbs.adaptor.ObjectAdaptorMaster;
import com.cbs.adaptor.ObjectAdaptorSms;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.NamedQueryConstant;
import com.cbs.constant.SmsServicesEnum;
import com.cbs.dao.master.BnkAddDAO;
import com.cbs.dao.master.BranchMasterDAO;
import com.cbs.dao.master.CbsChargeDetailDAO;
import com.cbs.dao.master.GlTableDAO;
import com.cbs.dao.master.ParameterinfoReportDAO;
import com.cbs.dao.sms.AccountMasterDAO;
import com.cbs.dao.sms.MbChargeMasterDAO;
import com.cbs.dao.sms.MbChargePostingIndivisualHistoryDAO;
import com.cbs.dao.sms.MbPushMsgTabDAO;
import com.cbs.dao.sms.MbServicesDescriptionDAO;
import com.cbs.dao.sms.MbSmsSenderBankDetailDAO;
import com.cbs.dao.sms.MbSubscriberServicesDAO;
import com.cbs.dao.sms.MbSubscriberTabDAO;
import com.cbs.dao.sms.MbSubscriberTabHisDAO;
import com.cbs.dto.AccountMasterTO;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.master.BnkaddTO;
import com.cbs.dto.master.BranchMasterTO;
import com.cbs.dto.master.CbsChargeDetailTO;
import com.cbs.dto.master.GltableTO;
import com.cbs.dto.sms.BulkSmsTo;
import com.cbs.dto.sms.MbChargePostingIndividualHistoryTO;
import com.cbs.dto.sms.MbPushMsgTabTO;
import com.cbs.dto.sms.MbServicesDescriptionTO;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.MbSubscriberServicesPKTO;
import com.cbs.dto.sms.MbSubscriberServicesTO;
import com.cbs.dto.sms.MbSubscriberTabTO;
import com.cbs.dto.sms.ProductWiseSmsDetailsPojo;
import com.cbs.dto.sms.SmsPostingTo;
import com.cbs.dto.sms.SmsRequestTO;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.entity.master.CbsChargeDetail;
import com.cbs.entity.neftrtgs.AccountMaster;
import com.cbs.entity.neftrtgs.TDAccountMaster;
import com.cbs.entity.sms.MbChargeMaster;
import com.cbs.entity.sms.MbPushMsgTab;
import com.cbs.entity.sms.MbServicesDescription;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.entity.sms.MbSubscriberServices;
import com.cbs.entity.sms.MbSubscriberServicesPK;
import com.cbs.entity.sms.MbSubscriberTab;
import com.cbs.entity.sms.MbSubscriberTabHis;
import com.cbs.entity.sms.MbSubscriberTabHisPK;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ExceptionCode;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.sms.service.SmsSender;
import com.cbs.sms.service.SmsService;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.Validator;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;

@Stateless(mappedName = "SmsManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class SmsManagementFacade implements SmsManagementFacadeRemote {

    private static final Logger logger = Logger.getLogger(SmsManagementFacade.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    RequestReceiverFacadeRemote reqReceivedSms;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private TransactionManagementFacadeRemote txnRemote;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
    @EJB
    SmsSender smsSender;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat smsDtFormat = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmyy = new SimpleDateFormat("dd-MM-yy");
    NumberFormat formatter = new DecimalFormat("#.##");
//    Date curDt = new Date();

    public String saveUpdateSubscriberAndServicesDetails(String acno, MbSubscriberTabTO mbSubscriberTabTO, List<MbSubscriberServicesTO> referenceList, List<MbSubscriberServicesTO> onRequestList, String pin, String mobileNo, String flag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
            MbSubscriberTabHisDAO mbSubscriberTabHisDAO = new MbSubscriberTabHisDAO(entityManager);
            MbSubscriberServicesDAO mbSubscriberServicesDAO = new MbSubscriberServicesDAO(entityManager);
            if (flag.equalsIgnoreCase("SAVE")) {
                MbSubscriberTab singleEntity = ObjectAdaptorSms.adaptToMbSubscriberTabEntity(mbSubscriberTabTO);
                mbSubscriberTabDAO.save(singleEntity);

                for (MbSubscriberServicesTO mbSubscriberServicesTO : referenceList) {
                    MbSubscriberServices services = ObjectAdaptorSms.adaptToMbSubscriberServicesEntity(mbSubscriberServicesTO);
                    mbSubscriberServicesDAO.save(services);
//                        mbSubscriberServicesDAO.merge(services);
                }
            }
            if (flag.equalsIgnoreCase("UPDATE")) {
                MbSubscriberTab oldObj = mbSubscriberTabDAO.getEntityByAcno(acno);
                if (oldObj == null) {
                    throw new ApplicationException("There is no such A/c to update.");
                }
                //Making history of a particular account.
                Long maxTxnId = mbSubscriberTabHisDAO.getMaxTxnId(acno);
                MbSubscriberTabHisPK hisPK = new MbSubscriberTabHisPK();
                hisPK.setTxnId(maxTxnId);
                hisPK.setAcno(acno);
                MbSubscriberTabHis his = new MbSubscriberTabHis();

                his.setMbSubscriberTabHisPK(hisPK);
                his.setAcnoType(oldObj.getAcnoType());
                his.setMobileNo(oldObj.getMobileNo());
                his.setStatus(oldObj.getStatus());
                his.setBillDeductionAcno(oldObj.getBillDeductionAcno());

                his.setCashCrLimit(oldObj.getCashCrLimit());
                his.setCashDrLimit(oldObj.getCashDrLimit());
                his.setTrfCrLimit(oldObj.getTrfCrLimit());
                his.setTrfDrLimit(oldObj.getTrfDrLimit());
                his.setClgCrLimit(oldObj.getClgCrLimit());

                his.setClgDrLimit(oldObj.getClgDrLimit());
                his.setPinNo(oldObj.getPinNo());
                his.setCreatedDate(oldObj.getCreatedDate());
                his.setEnterBy(oldObj.getEnterBy());
                his.setAuth(oldObj.getAuth());
                his.setAuthBy(oldObj.getAuthBy());
                his.setAuthStatus(oldObj.getAuthStatus());
                his.setInterestLimit(oldObj.getInterestLimit());
                his.setChargeLimit(oldObj.getChargeLimit());
                his.setUpdateBy(oldObj.getUpdateBy());
                his.setUpdateDt(oldObj.getUpdateDt());

                mbSubscriberTabHisDAO.save(his);

                mbSubscriberTabTO.setEnterBy(oldObj.getEnterBy());
                mbSubscriberTabTO.setCreatedDate(oldObj.getCreatedDate());

                MbSubscriberTab singleEntity = ObjectAdaptorSms.adaptToMbSubscriberTabEntity(mbSubscriberTabTO);
                mbSubscriberTabDAO.update(singleEntity);

                if (!referenceList.isEmpty()) {
                    String acno1 = referenceList.get(0).getMbSubscriberServicesPKTO().getAcno();
                    List<MbSubscriberServices> list = mbSubscriberServicesDAO.getEntityListByAcno(acno1);
                    for (short i = 1; i <= 20; i++) {
                        boolean flag1 = false;
                        boolean flag2 = false;
                        for (MbSubscriberServicesTO mbSubscriberServicesTO : referenceList) {
                            if (i == Integer.parseInt(mbSubscriberServicesTO.getMbSubscriberServicesPKTO().getVar())) {
                                flag1 = true;
                            }
                        }
                        for (MbSubscriberServices mss : list) {
                            System.out.println(mss.getMbSubscriberServicesPK().getServices());
                            if (i == (int) mss.getMbSubscriberServicesPK().getServices()) {
                                flag2 = true;
                            }
                        }
                        if (flag1 == false && flag2 == true) {
                            MbSubscriberServices services1 = new MbSubscriberServices();
                            MbSubscriberServicesPK msspk = new MbSubscriberServicesPK();
                            msspk.setAcno(acno1);
                            msspk.setServices(i);
                            services1.setMbSubscriberServicesPK(msspk);
                            mbSubscriberServicesDAO.delete(services1, services1.getMbSubscriberServicesPK());
                        } else if (flag1 == true && flag2 == false) {
                            MbSubscriberServices services1 = new MbSubscriberServices();
                            MbSubscriberServicesPK msspk = new MbSubscriberServicesPK();
                            msspk.setAcno(acno1);
                            msspk.setServices(i);
                            services1.setMbSubscriberServicesPK(msspk);
                            mbSubscriberServicesDAO.save(services1);
                        }
                    }
                } else {
                    List<MbSubscriberServices> list = mbSubscriberServicesDAO.getEntityListByAcno(acno);
                    for (MbSubscriberServices mss : list) {
                        for (short i = 1; i <= 19; i++) {
                            if (i == mss.getMbSubscriberServicesPK().getServices()) {
                                MbSubscriberServices services1 = new MbSubscriberServices();
                                MbSubscriberServicesPK msspk = new MbSubscriberServicesPK();
                                msspk.setAcno(acno);
                                msspk.setServices(i);
                                services1.setMbSubscriberServicesPK(msspk);
                                mbSubscriberServicesDAO.delete(services1, services1.getMbSubscriberServicesPK());
                            }
                        }
                    }
                }
            }
            ut.commit();
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public String activateOrDeactivate(String acno, String function, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
            MbSubscriberTabHisDAO mbSubscriberTabHisDAO = new MbSubscriberTabHisDAO(entityManager);

            MbSubscriberTab oldObj = mbSubscriberTabDAO.getEntityByAcno(acno);
            if (oldObj == null) {
                throw new ApplicationException("There is no such A/c to update. " + acno);
            }
            //Making history of a particular account.
            Long maxTxnId = mbSubscriberTabHisDAO.getMaxTxnId(acno);
            MbSubscriberTabHisPK hisPK = new MbSubscriberTabHisPK();
            hisPK.setTxnId(maxTxnId);
            hisPK.setAcno(acno);
            MbSubscriberTabHis his = new MbSubscriberTabHis();

            his.setMbSubscriberTabHisPK(hisPK);
            his.setAcnoType(oldObj.getAcnoType());
            his.setMobileNo(oldObj.getMobileNo());
            his.setStatus(oldObj.getStatus());
            his.setBillDeductionAcno(oldObj.getBillDeductionAcno());

            his.setCashCrLimit(oldObj.getCashCrLimit());
            his.setCashDrLimit(oldObj.getCashDrLimit());
            his.setTrfCrLimit(oldObj.getTrfCrLimit());
            his.setTrfDrLimit(oldObj.getTrfDrLimit());
            his.setClgCrLimit(oldObj.getClgCrLimit());

            his.setClgDrLimit(oldObj.getClgDrLimit());
            his.setPinNo(oldObj.getPinNo());
            his.setCreatedDate(oldObj.getCreatedDate());
            his.setEnterBy(oldObj.getEnterBy());
            his.setAuth(oldObj.getAuth());
            his.setAuthBy(oldObj.getAuthBy());
            his.setAuthStatus(oldObj.getAuthStatus());

            his.setUpdateBy(oldObj.getUpdateBy());
            his.setUpdateDt(oldObj.getUpdateDt());

            mbSubscriberTabHisDAO.save(his);
            //Updating the old entity
            oldObj.setUpdateBy(enterBy);
            oldObj.setUpdateDt(new Date());
            oldObj.setAuth("N");
            oldObj.setAuthBy("");
            oldObj.setAuthStatus(function);

            mbSubscriberTabDAO.update(oldObj);
            ut.commit();
            if (function.equalsIgnoreCase("T")) {
                try {
                    smsRegistration(acno);
                } catch (Exception e) {
                    System.out.println("Error In Sending Registration Message of A/c :: " + acno + " On Date " + new Date());
                }
            }
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public List<MbSubscriberServicesTO> addOnRequestService(List<MbSubscriberServicesTO> onRequestList, String acno) {
        MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
        MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

        mbSubscriberServicesPKTO.setVar("1");
        mbSubscriberServicesPKTO.setAcno(acno);
        mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
        onRequestList.add(mbSubscriberServicesTO);

        MbSubscriberServicesTO servicesTO = new MbSubscriberServicesTO();
        MbSubscriberServicesPKTO pkTo = new MbSubscriberServicesPKTO();

        pkTo.setVar("3");
        pkTo.setAcno(acno);
        servicesTO.setMbSubscriberServicesPKTO(pkTo);
        onRequestList.add(servicesTO);

        return onRequestList;
    }

    public String deleteOrAuthorize(String acno, String function, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);

            MbSubscriberTab oldObj = mbSubscriberTabDAO.getEntityByAcno(acno);
            if (oldObj != null) {
                if (function.equalsIgnoreCase("DELETE")) {
                    mbSubscriberTabDAO.delete(new MbSubscriberTab(), acno);
//                    mbSubscriberTabDAO.remove(oldObj);
                } else if (function.equalsIgnoreCase("AUTHORIZE")) {
                    oldObj.setAuth("Y");
                    oldObj.setAuthBy(authBy);
                    if (oldObj.getAuthStatus().equals("A")
                            || oldObj.getAuthStatus().equals("E")
                            || oldObj.getAuthStatus().equals("T")) {
                        oldObj.setAuthStatus("V");
                    }
                    mbSubscriberTabDAO.update(oldObj);
                }
            } else {
                throw new ApplicationException("There is no such account. " + acno);
            }
            ut.commit();
            if (function.equalsIgnoreCase("AUTHORIZE") && !oldObj.getAuthStatus().equalsIgnoreCase("D")) {
                try {
                    smsRegistration(acno);
                } catch (Exception e) {
                    System.out.println("Error In Sending Registration Message of A/c :: " + acno + " On Date " + new Date());
                }
            }
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public void smsRegistration(String acno) throws ApplicationException {
        try {
            MbSubscriberTabTO subscriberTo = getSubscriberDetails(acno);
            List<MbSmsSenderBankDetailTO> bankTo = getBankAndSenderDetail();
            String templateBankName = bankTo.get(0).getTemplateBankName().trim();

//            String pullCode = " Dear Customer, Congratulations! SMS Alert services has been "
//                    + "activated in your account. For more information contact your branch";
            //Sending SMS
            TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
            tSmsRequestTo.setMsgType("PAT");
            tSmsRequestTo.setTemplate(SmsType.ACCOUNT_REGISTRATION);
            tSmsRequestTo.setAcno(acno);
            tSmsRequestTo.setPromoMobile(subscriberTo.getMobileNo());
            tSmsRequestTo.setBankName(templateBankName);
//            tSmsRequestTo.setPullCode(pullCode);
            sendSms(tSmsRequestTo);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String checkSubscriberDetails(String acno) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
        try {
            MbSubscriberTab mbSubscriberTabEntity = mbSubscriberTabDAO.getEntityByAcno(acno);
            if (mbSubscriberTabEntity != null) {
                return msg = "true";
            } else {
                msg = "false";
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method checkSubscriberDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for checkSubscriberDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;
    }

    @Override
    public String findNameByAcno(String acno) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        AccountMasterDAO accountMasterDAO = new AccountMasterDAO(entityManager);
        try {
            String accNature = fts.getAccountNature(acno);
            if (accNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                TDAccountMaster tdAccountMasterEntity = accountMasterDAO.getEntityByTDAcno(acno);
                if (tdAccountMasterEntity != null) {
                    msg = tdAccountMasterEntity.getCustname();
                } else {
                    msg = "Account Holder name does not exist";
                }
            } else {
                AccountMaster accountMasterEntity = accountMasterDAO.getEntityByAcno(acno);
                if (accountMasterEntity != null) {
                    AccountMasterTO accountMasterTO = ObjectAdaptorAccount.adaptToAccountMasterTO(accountMasterEntity);
                    msg = accountMasterTO.getCustname();
                } else {
                    msg = "Account Holder name does not exist";
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method checkSubscriberDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for checkSubscriberDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;
    }

    public MbSubscriberTabTO getSubscriberDetails(String acno) throws ApplicationException {
        long begin = System.nanoTime();
        MbSubscriberTabTO mbSubscriberTabTO = new MbSubscriberTabTO();
        MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
        try {
            MbSubscriberTab MbSubscriberTabDetails = mbSubscriberTabDAO.getEntityByAcno(acno);
            mbSubscriberTabTO = ObjectAdaptorSms.adaptToMbSubscriberTabTO(MbSubscriberTabDetails);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPopUpForm is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return mbSubscriberTabTO;
    }

    public List<MbSubscriberServicesTO> getSubsCriberServices(String acno) throws ApplicationException {
        long begin = System.nanoTime();
        List<MbSubscriberServicesTO> mbSubscriberServicesTOs = new ArrayList<MbSubscriberServicesTO>();
        MbSubscriberServicesDAO mbSubscriberServicesDAO = new MbSubscriberServicesDAO(entityManager);
        try {
            List<MbSubscriberServices> mbSubscriberServices = mbSubscriberServicesDAO.getEntityListByAcno(acno);
            if (mbSubscriberServices.size() > 0) {
                for (MbSubscriberServices entityList : mbSubscriberServices) {
                    mbSubscriberServicesTOs.add(ObjectAdaptorSms.adapToMbSubscriberServicesTO(entityList));
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return mbSubscriberServicesTOs;
    }

    @Override
    public List getAllMobileNo(String orgnBrCode) throws ApplicationException {
        long begin = System.nanoTime();
        List mbSubscriberTabTOs = new ArrayList();
        MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
        try {
            List mbSubscriberTabs = mbSubscriberTabDAO.getAllMobileNo(orgnBrCode);
            if (!mbSubscriberTabs.isEmpty()) {
                mbSubscriberTabTOs.addAll(mbSubscriberTabs);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED, "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return mbSubscriberTabTOs;
    }

//    public String sendMessageToAll(List<MbPushMsgTabTO> mbPushMsgTabTOs) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        String msg = "";
//        MbPushMsgTabDAO mbPushMsgTabDAO = new MbPushMsgTabDAO(entityManager);
//        try {
//            ut.begin();
//            for (MbPushMsgTabTO mbPushMsgTabTO : mbPushMsgTabTOs) {
//                MbPushMsgTab mbPushMsgTab = ObjectAdaptorSms.adapToMbPushMsgTabEntity(mbPushMsgTabTO);
//                mbPushMsgTabDAO.save(mbPushMsgTab);
//            }
//            ut.commit();
//            msg = "true";
//        } catch (Exception ex) {
//            try {
//                ut.rollback();
//                ex.printStackTrace();
//                throw new ApplicationException(ex);
//            } catch (Exception e) {
//                throw new ApplicationException(e);
//            }
//        }
//        return msg;
//    }
    public String sendMessageToSingleSubscriber(MbPushMsgTabTO mbPushMsgTabTO) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        MbPushMsgTabDAO mbPushMsgTabDAO = new MbPushMsgTabDAO(entityManager);
        try {
            ut.begin();
            MbPushMsgTab mbPushMsgTab = ObjectAdaptorSms.adapToMbPushMsgTabEntity(mbPushMsgTabTO);
            mbPushMsgTabDAO.save(mbPushMsgTab);
            ut.commit();
            msg = "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return msg;

    }

    public String saveChargeMasterDetails(MbChargeMaster chargeMaster) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        MbChargeMasterDAO masterDAO = new MbChargeMasterDAO(entityManager);
        try {
            ut.begin();
            Query namedQuery = entityManager.createNamedQuery("MbChargeMaster.findByMessageType");
            List<MbChargeMaster> resultList = namedQuery.setParameter("messageType", chargeMaster.getMessageType()).getResultList();
            if (!resultList.isEmpty()) {
                for (MbChargeMaster master : resultList) {
                    if (master.getStatus().equalsIgnoreCase("A")) {
                        master.setStatus("D");
                        masterDAO.update(master);
                    }
                }
            }
            chargeMaster.setTxnId(masterDAO.getMaxTxnId() + 1);
            masterDAO.save(chargeMaster);
            ut.commit();
            msg = "Record has been saved successfully.";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return msg;
    }

    public List getDataforChargePosting(String messageType, String acno, String orgnBrCode) throws ApplicationException {
        long begin = System.nanoTime();
        List dataforChargePosting = new ArrayList();
        MbPushMsgTabDAO mbPushMsgTabDAO = new MbPushMsgTabDAO(entityManager);
        try {
            dataforChargePosting = mbPushMsgTabDAO.getDataforChargePosting(messageType, acno, orgnBrCode);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDataforChargePosting()", e);
            throw new ApplicationException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDataforChargePosting is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return dataforChargePosting;
    }

    public MbChargeMaster getChargePerMessage() throws ApplicationException {
        MbChargeMasterDAO masterDAO = new MbChargeMasterDAO(entityManager);
        try {
            return masterDAO.getChargePerMessage();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

//    public Date getMaxToDateForChargePostingByAcno(String acno, String messageType) throws ApplicationException {
//        Date date = null;
//        try {
//            MbChargePostingIndivisualHistoryDAO dao = new MbChargePostingIndivisualHistoryDAO(entityManager);
//            date = dao.getMaxToDateForChargePostingByAcno(acno, messageType.trim());
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return date;
//    }
    public Date getMaxToDateOfSmsPosting(String acno) throws ApplicationException {
        Date date = null;
        try {
            MbChargePostingIndivisualHistoryDAO dao = new MbChargePostingIndivisualHistoryDAO(entityManager);
            date = dao.getMaxToDateOfSmsPosting(acno);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return date;
    }

//    public Date getMaxToDateForAllPosting(String brncode) throws ApplicationException {
//        Date date = null;
//        try {
//            MbChargePostingHistoryDAO dao = new MbChargePostingHistoryDAO(entityManager);
//            date = dao.getMaxToDateForAllPosting(brncode);
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return date;
//    }
//    public String individualPerSmsBasis(List<MbChargePostingIndividualHistoryTO> entityList, String flag, String messageType,
//            String orgnBrCode) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        String msg = "", acNature = "", username = "";
//        int i = 0, taxApplyCode = 0;
//        double totalCharge = 0.0;
//        try {
//            ut.begin();
//
//            String currentDt = fts.daybeginDate(orgnBrCode);
//
//            taxApplyCode = fts.getCodeForReportName("STAXMODULE_ACTIVE");
//            float trsno = fts.getTrsNo().floatValue();
//            for (int k = 0; k < entityList.size(); k++) {
//                MbChargePostingIndividualHistoryTO toEntity = entityList.get(k);
//                acNature = fts.getAccountNature(toEntity.getBillingAcno());
//                username = toEntity.getEnterBy();
//                double partySerCharge = 0.0;
//
//                if (toEntity.getDrAmount() == 0) {
//                    throw new ApplicationException("Total charge amount is zero so it can not be post.");
//                }
//
//                if (taxApplyCode == 1) {
//                    Map<String, Double> map = ibRemote.getTaxComponent(toEntity.getDrAmount(), ymd.format(new Date()));
//                    Set<Entry<String, Double>> set = map.entrySet();
//                    Iterator<Entry<String, Double>> it = set.iterator();
//                    while (it.hasNext()) {
//                        Entry entry = it.next();
//                        partySerCharge = partySerCharge + Double.parseDouble(entry.getValue().toString());
//                    }
//                }
//                double totalDramt = toEntity.getDrAmount() + partySerCharge;
//
//                msg = fts.checkBalance(toEntity.getBillingAcno(), totalDramt, username);
//                if (!msg.equalsIgnoreCase("TRUE")) {
//                    int n = entityManager.createNativeQuery("insert into pendingcharges(acno,dt,amount,details,"
//                            + "instno,ty,trantype,recno,trsno,enterby,auth, authby,updatedt,updateby,charges,"
//                            + "recover,trandesc) values('" + toEntity.getBillingAcno() + "','" + currentDt + "',"
//                            + "" + formatter.format(totalDramt) + ",'Pending SMS Charge','',1,2,0," + trsno + ",'" + username + "',"
//                            + "'Y','" + username + "','" + currentDt + "','" + username + "',Null,'N',7)").executeUpdate();
//                    if (n <= 0) {
//                        throw new ApplicationException("Problem in pending charges maintenance.");
//                    }
//                    ut.commit();
//                    return "Pending charges has been maintained";
//                }
//
//                msg = fts.insertRecons(acNature, toEntity.getBillingAcno(), 1, toEntity.getDrAmount(), currentDt,
//                        currentDt, 2, "SMS Charge Posting", username, trsno, ymdh.format(new Date()),
//                        fts.getRecNo().floatValue(), "Y", username, 35, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
//                        orgnBrCode, orgnBrCode, 0, "", "", "");
//                if (!msg.equalsIgnoreCase("true")) {
//                    throw new ApplicationException(msg);
//                }
//
//                if (partySerCharge != 0 && taxApplyCode == 1) {
//                    msg = fts.insertRecons(acNature, toEntity.getBillingAcno(), 1, partySerCharge, currentDt,
//                            currentDt, 2, "SMS Charge Service Tax", username, trsno, ymdh.format(new Date()),
//                            fts.getRecNo().floatValue(), "Y", username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "",
//                            "", orgnBrCode, orgnBrCode, 0, "", "", "");
//                    if (!msg.equalsIgnoreCase("true")) {
//                        throw new ApplicationException(msg);
//                    }
//                }
//
//                msg = fts.updateBalance(acNature, toEntity.getBillingAcno(), 0.0, Double.parseDouble(formatter.format(totalDramt)), "", "");
//                if (!msg.equalsIgnoreCase("true")) {
//                    throw new ApplicationException(msg);
//                }
//
//                MbChargeMasterDAO chargeMasterDao = new MbChargeMasterDAO(entityManager);
//                MbChargeMaster chargeEntity = chargeMasterDao.getChargePerMessage();
//                String creditedGLHead = chargeEntity.getChargeGlHead();
//                creditedGLHead = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + creditedGLHead + "01";
//                acNature = fts.getAccountNature(creditedGLHead);
//
//                msg = fts.insertRecons(acNature, creditedGLHead, 0, toEntity.getDrAmount(),
//                        currentDt, currentDt, 2, "SMS Charge Posting", username, trsno,
//                        ymdh.format(new Date()), fts.getRecNo().floatValue(), "Y", username, 35, 3, "", "",
//                        0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0, "", "", "");
//                if (!msg.equalsIgnoreCase("true")) {
//                    throw new ApplicationException(msg);
//                }
//
//                msg = fts.updateBalance(acNature, creditedGLHead, toEntity.getDrAmount(), 0.0, "", "");
//                if (!msg.equalsIgnoreCase("true")) {
//                    throw new ApplicationException(msg);
//                }
//
//                if (taxApplyCode == 1) {
//                    Map<String, Double> map = ibRemote.getTaxComponent(toEntity.getDrAmount(), ymd.format(new Date()));
//                    Set<Entry<String, Double>> staxSet = map.entrySet();
//                    Iterator<Entry<String, Double>> staxIt = staxSet.iterator();
//                    while (staxIt.hasNext()) {
//                        Entry entry = staxIt.next();
//                        String[] keyArray = entry.getKey().toString().split(":");
//                        String description = keyArray[0];
//                        String taxHead = orgnBrCode + keyArray[1];
//                        double serAmount = Double.parseDouble(entry.getValue().toString());
//
//                        if (serAmount != 0) {
//                            description = description + " In Sms Charge";
//                            msg = fts.insertRecons("PO", taxHead, 0, serAmount, currentDt, currentDt, 2,
//                                    description, username, trsno, ymdh.format(new Date()), fts.getRecNo().floatValue(), "Y",
//                                    username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0,
//                                    "", "", "");
//                            if (!msg.equalsIgnoreCase("true")) {
//                                throw new ApplicationException(msg);
//                            }
//
//                            msg = fts.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
//                            if (!msg.equalsIgnoreCase("true")) {
//                                throw new ApplicationException(msg);
//                            }
//                        }
//                    }
//                }
//                totalCharge = totalCharge + toEntity.getDrAmount();
//            }
//            
//            MbChargePostingIndivisualHistoryDAO dao = new MbChargePostingIndivisualHistoryDAO(entityManager);
//            long maxTxnId = dao.getMaxTxnId();
//            for (MbChargePostingIndividualHistoryTO entityTO : entityList) {
//                if (entityTO.getFromDate().compareTo(entityTO.getToDate()) > 0) {
//                    i = i + 1;
//                    continue;
//                }
//
//                MbChargePostingIndivisualHistory entity = new MbChargePostingIndivisualHistory();
//                entity.setAcno(entityTO.getAcno());
//                entity.setDrAmount(Double.parseDouble(formatter.format(entityTO.getDrAmount() + entityTO.getSerCharge())));
//                entity.setEnterBy(entityTO.getEnterBy());
//                entity.setFromDate(entityTO.getFromDate());
//                entity.setMessageType(entityTO.getMessageType());
//                entity.setNoOfMessage(entityTO.getNoOfMessage());
//                entity.setToDate(entityTO.getToDate());
//                entity.setTranDt(entityTO.getTranDt());
//                entity.setTxnId(++maxTxnId);
//                entity.setTrsNo(trsno);
//                dao.save(entity);
//                MbPushMsgTabDAO msgTabDAO = new MbPushMsgTabDAO(entityManager);
//
//                List<MbPushMsgTab> dataList = msgTabDAO.getAllSendedTxnId(entityTO.getAcno(), entityTO.getMessageType());
//                if (!dataList.isEmpty()) {
//                    for (int k = 0; k < dataList.size(); k++) {
//                        MbPushMsgTab pushEntity = dataList.get(k);
//                        pushEntity.setMessageStatus(3);
//                        msgTabDAO.update(pushEntity);
//                    }
//                }
//            }
//            if (i != entityList.size()) {
//                if (flag.equalsIgnoreCase("ALL")) {
//                    MbChargePostingHistoryDAO historyDAO = new MbChargePostingHistoryDAO(entityManager);
//                    long maxTxnId1 = historyDAO.getMaxTxnId();
//                    MbChargePostingHistory chargePostingHistory = new MbChargePostingHistory();
//                    chargePostingHistory.setTxnId(++maxTxnId1);
//                    chargePostingHistory.setGlCrAmount(Double.parseDouble(formatter.format(totalCharge)));
//                    chargePostingHistory.setMessageType(entityList.get(0).getMessageType());
//                    chargePostingHistory.setTranDt(entityList.get(0).getTranDt());
//                    chargePostingHistory.setEnterBy(entityList.get(0).getEnterBy());
//                    chargePostingHistory.setTrsNo(trsno);
//                    historyDAO.save(chargePostingHistory);
//                }
//            } else {
//                return "Record[s] has been already posted.";
//            }
//            ut.commit();
//            msg = "Record[s] has been posted successfully.";
//        } catch (Exception e) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(e.getMessage());
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//        return msg;
//    }
    public String postChargeHistory(int fixedOrPerSms, int chargeBasedOnParam,
            List<MbChargePostingIndividualHistoryTO> entityList, String type, String orgnBrCode,
            String creditedGLHead, String frDt, String toDt, String nature, String acType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "", acNature = "", username = "";
        int taxApplyCode = 0, roundUpTo = 0, roundUpToIgst = 0;
        double totalCharge = 0.0, totalStaxCharge = 0, totalRot = 0, totalRotIgst = 0, totalStaxChargeIgst = 0;
        try {
            System.out.println("Start Time-->" + new Date());
            ut.begin();

            String accountType = "";
            if (chargeBasedOnParam == 2) {
                accountType = "A";
            } else if (chargeBasedOnParam == 1) {
                accountType = nature;
            } else if (chargeBasedOnParam == 0) {
                accountType = acType;
            }

            if (fixedOrPerSms != 2) { //Fixed Case
                if (type.equalsIgnoreCase("I")) {
                    MbChargePostingIndividualHistoryTO toEntity = entityList.get(0);
                    Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CHECK_ALREADY_POSTED_ACCOUNT);
                    createNamedQuery.setParameter("acno", toEntity.getAcno());
                    List resultList = createNamedQuery.getResultList();
                    if (!resultList.isEmpty() && resultList.get(0) != null) {
                        Date maxToDt = (Date) resultList.get(0);
                        if (toEntity.getFromDate().compareTo(maxToDt) <= 0) {
                            throw new ApplicationException("Charge has been already posted for this account.");
                        }
                    }
                }
            }

            String currentDt = fts.daybeginDate(orgnBrCode);
            float trsno = fts.getTrsNo().floatValue();
            float recno = fts.getRecNo().floatValue();
            taxApplyCode = fts.getCodeForReportName("STAXMODULE_ACTIVE");
            if (taxApplyCode == 1) {
                //Calculating total stax percentage and round upto.
                Map<String, Double> slabMap = ibRemote.getTaxComponentSlab(ymd.format(new Date()));
                Set<Entry<String, Double>> set = slabMap.entrySet();
                Iterator<Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    roundUpTo = Integer.parseInt(keyArray[3]);
                    totalRot += Double.parseDouble(entry.getValue().toString());
                }
                Map<String, Double> slabMapIgst = ibRemote.getIgstTaxComponentSlab(ymd.format(new Date()));
                Set<Entry<String, Double>> setIgst = slabMapIgst.entrySet();
                Iterator<Entry<String, Double>> itIgst = setIgst.iterator();
                while (itIgst.hasNext()) {
                    Entry entry = itIgst.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    roundUpToIgst = Integer.parseInt(keyArray[3]);
                    totalRotIgst += Double.parseDouble(entry.getValue().toString());
                }
            }
            //Transaction Handling For Individual A/c
            for (int k = 0; k < entityList.size(); k++) {
                MbChargePostingIndividualHistoryTO toEntity = entityList.get(k);
                acNature = toEntity.getBillingAcnoNature();
                username = toEntity.getEnterBy();
                String custState = toEntity.getCustState();
                String branchState = toEntity.getBrState();
                double partySerCharge = 0.0, partySerChargeIgst = 0.0;

                if (custState.equalsIgnoreCase(branchState)) {
                    partySerCharge = CbsUtil.round(((toEntity.getDrAmount() * totalRot) / 100), roundUpTo);
                } else {
                    partySerCharge = CbsUtil.round(((toEntity.getDrAmount() * totalRotIgst) / 100), roundUpToIgst);
                    partySerChargeIgst = partySerCharge;
                }
                //Balance Checking
                double totalDramt = toEntity.getDrAmount() + partySerCharge;
                msg = fts.checkBalance(toEntity.getBillingAcno(), totalDramt, username);
                if (!msg.equalsIgnoreCase("TRUE")) {
                    Query q1 = entityManager.createNativeQuery("insert into pendingcharges(acno,dt,amount,details,"
                            + "instno,ty,trantype,recno,trsno,enterby,auth, authby,updatedt,updateby,charges,"
                            + "recover,trandesc) values('" + toEntity.getBillingAcno() + "','" + currentDt + "',"
                            + "" + toEntity.getDrAmount() + ",'Pending SMS Charge','',1,2,0," + trsno + ",'" + username + "',"
                            + "'Y','" + username + "','" + currentDt + "','" + username + "',Null,'N',116)");

                    q1.executeUpdate();
                    continue;
                }
                //Charge deduction
                String destBrCode = fts.getCurrentBrnCode(toEntity.getBillingAcno());
                if (destBrCode.equalsIgnoreCase(orgnBrCode)) {
                    recno = recno + 1;
                    msg = fts.insertRecons(acNature, toEntity.getBillingAcno(), 1, toEntity.getDrAmount(), currentDt,
                            currentDt, 2, "SMS Charge Posting for "+ toEntity.getAcno(), username, trsno, ymdh.format(new Date()),
                            recno, "Y", "System", 116, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
                            orgnBrCode, orgnBrCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    //If any service tax then
                    if (partySerCharge != 0 && taxApplyCode == 1) {
                        recno = recno + 1;
                        msg = fts.insertRecons(acNature, toEntity.getBillingAcno(), 1, partySerCharge, currentDt,
                                currentDt, 2, "GST for SMS Charge for "+ toEntity.getAcno(), username, trsno, ymdh.format(new Date()),
                                recno, "Y", "System", 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "",
                                "", orgnBrCode, orgnBrCode, 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                    }
                }else{
                    //# interbranch Transaction
                    recno = recno + 1;
                    msg = fts.insertRecons(acNature, toEntity.getBillingAcno(), 1, toEntity.getDrAmount(), currentDt,
                            currentDt, 2, "SMS Charge Posting for " + toEntity.getAcno(), username, trsno, ymdh.format(new Date()),
                            recno, "Y", "System", 116, 3, "", "", 0.0f, "", "", 51, "", 0.0f, "", "",
                            orgnBrCode,destBrCode , 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    //If any service tax then
                    if (partySerCharge != 0 && taxApplyCode == 1) {
                        recno = recno + 1;
                        msg = fts.insertRecons(acNature, toEntity.getBillingAcno(), 1, partySerCharge, currentDt,
                                currentDt, 2, "GST for SMS Charge for " + toEntity.getAcno(), username, trsno, ymdh.format(new Date()),
                                recno, "Y", "System", 71, 3, "", "", 0.0f, "", "", 51, "", 0.0f, "",
                                "", orgnBrCode, destBrCode, 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                    }
                }

                msg = fts.updateBalance(acNature, toEntity.getBillingAcno(), 0.0, totalDramt, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                totalCharge += toEntity.getDrAmount();
                if (custState.equalsIgnoreCase(branchState)) {
                    totalStaxCharge += partySerCharge;
                } else {
                    totalStaxChargeIgst += partySerChargeIgst;
                }
            }
            //Charge head transaction. 
            if (totalCharge == 0.0) {
                throw new ApplicationException("Total charge amount is zero so it can not be post.");
            }

            recno = recno + 1;
            msg = fts.insertRecons("PO", creditedGLHead, 0, Double.parseDouble(formatter.format(totalCharge)), currentDt, currentDt,
                    2, "SMS Charge Posting", username, trsno, ymdh.format(new Date()), recno,
                    "Y", username, 116, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            msg = fts.updateBalance("PO", creditedGLHead, Double.parseDouble(formatter.format(totalCharge)), 0.0, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            //double totalCrAmt = totalCharge + totalStaxCharge + totalStaxChargeIgst;
            //Service head transaction if any. 
            if (taxApplyCode == 1) {
                double totalChgBasedOnTotalServiceTax = CbsUtil.round(((totalStaxCharge * 100) / totalRot), roundUpTo);

                Map<String, Double> map = ibRemote.getTaxComponent(totalChgBasedOnTotalServiceTax, ymd.format(new Date()));
                Set<Entry<String, Double>> staxSet = map.entrySet();
                Iterator<Entry<String, Double>> staxIt = staxSet.iterator();
                double totalGstAmt = 0;
                String taxHead = "";
                while (staxIt.hasNext()) {
                    Entry entry = staxIt.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    String description = keyArray[0];
                    taxHead = orgnBrCode + keyArray[1];
                    double serAmount = Double.parseDouble(entry.getValue().toString());
                    totalGstAmt += serAmount;
                    recno = recno + 1;

                    description = description + " for Sms Charge";
                    msg = fts.insertRecons("PO", taxHead, 0, serAmount, currentDt, currentDt, 2,
                            description, username, trsno, ymdh.format(new Date()), recno, "Y",
                            username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0,
                            "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    msg = fts.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }
                totalStaxCharge = CbsUtil.round(totalStaxCharge, roundUpTo);
                if (totalStaxCharge != totalGstAmt) {
                    double serAmount = totalStaxCharge - totalGstAmt;
                    serAmount = CbsUtil.round(serAmount, roundUpTo);
                    int ty = 0;
                    if (serAmount < 0) {
                        ty = 1;
                        serAmount = Math.abs(serAmount);
                    }
                    recno = recno + 1;
                    msg = fts.insertRecons("PO", taxHead, ty, serAmount, currentDt, currentDt, 2,
                            "Difference amount for Sms Charge batch", username, trsno, ymdh.format(new Date()), recno, "Y",
                            username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0,
                            "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    msg = fts.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }
                if (totalStaxChargeIgst != 0) {
                    double totalChgBasedOnTotalServiceTaxIgst = CbsUtil.round(((totalStaxChargeIgst * 100) / totalRotIgst), roundUpToIgst);

                    Map<String, Double> mapIgst = ibRemote.getIgstTaxComponent(totalChgBasedOnTotalServiceTaxIgst, ymd.format(new Date()));
                    Set<Entry<String, Double>> staxSetIgst = mapIgst.entrySet();
                    Iterator<Entry<String, Double>> staxItIgst = staxSetIgst.iterator();
                    while (staxItIgst.hasNext()) {
                        Entry entry = staxItIgst.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        String description = keyArray[0];
                        taxHead = orgnBrCode + keyArray[1];
                        double serAmount = Double.parseDouble(entry.getValue().toString());
                        recno = recno + 1;

                        description = description + " for Sms Charge";
                        msg = fts.insertRecons("PO", taxHead, 0, serAmount, currentDt, currentDt, 2,
                                description, username, trsno, ymdh.format(new Date()), recno, "Y",
                                username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0,
                                "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }

                        msg = fts.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                    }
                }
            }
            //SMS Table Updation
            String mbChargePostingIndivisualHistoryInsertQuery = "insert into mb_charge_posting_indivisual_history"
                    + "(TRS_NO, ACNO, FROM_DATE, TO_DATE, DR_AMOUNT, NO_OF_MESSAGE, MESSAGE_TYPE, ENTER_BY, TRAN_DT) values";
            String mbChargePostingIndivisualHistoryDataQuery = "";
            String allAcno = "";

            for (MbChargePostingIndividualHistoryTO to : entityList) {
                if (mbChargePostingIndivisualHistoryDataQuery.equals("")) {
                    mbChargePostingIndivisualHistoryDataQuery = "(" + trsno + ",'" + to.getAcno() + "',"
                            + "'" + ymd.format(to.getFromDate()) + "','" + ymd.format(to.getToDate()) + "',"
                            + "" + Double.parseDouble(formatter.format(to.getDrAmount() + to.getSerCharge())) + ","
                            + "" + to.getNoOfMessage() + ",'" + to.getMessageType() + "','" + to.getEnterBy() + "',"
                            + "current_timestamp)";
                } else {
                    mbChargePostingIndivisualHistoryDataQuery = mbChargePostingIndivisualHistoryDataQuery + ","
                            + "(" + trsno + ",'" + to.getAcno() + "','" + ymd.format(to.getFromDate()) + "',"
                            + "'" + ymd.format(to.getToDate()) + "'," + Double.parseDouble(formatter.format(to.getDrAmount()
                                            + to.getSerCharge())) + "," + to.getNoOfMessage() + ",'" + to.getMessageType() + "',"
                            + "'" + to.getEnterBy() + "',current_timestamp)";
                }

                if (allAcno.equals("")) {
                    allAcno = "\'" + to.getAcno() + "\'";
                } else {
                    allAcno = allAcno + ",\'" + to.getAcno() + "\'";
                }
            }

            mbChargePostingIndivisualHistoryInsertQuery = mbChargePostingIndivisualHistoryInsertQuery + mbChargePostingIndivisualHistoryDataQuery;
            int n = entityManager.createNativeQuery(mbChargePostingIndivisualHistoryInsertQuery).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in sms account history insertion");
            }

            System.out.println("All A/c Are-->" + allAcno);

            List countList = entityManager.createNativeQuery("select * from mb_push_msg_tab "
                    + "where acno in(" + allAcno + ") and message_status=2 and date_format(dt,'%Y%m%d') "
                    + "between '" + ymd.format(dmy.parse(frDt)) + "' and '" + ymd.format(dmy.parse(toDt)) + "'").getResultList();
            if (!countList.isEmpty()) {
                n = entityManager.createNativeQuery("update mb_push_msg_tab set message_status=3 where "
                        + "acno in(" + allAcno + ") and message_status=2 and date_format(dt,'%Y%m%d') "
                        + "between '" + ymd.format(dmy.parse(frDt)) + "' and '" + ymd.format(dmy.parse(toDt)) + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in mb_push_msg_tab status updation.");
                }
            }

            if (type.equalsIgnoreCase("A")) {
                n = entityManager.createNativeQuery("insert into mb_charge_posting_history(TRS_NO,GL_CR_AMOUNT,"
                        + "MESSAGE_TYPE,ENTER_BY,TRAN_DT,CR_ACNO,FROM_DATE,TO_DATE,AC_TYPE) values (" + trsno + ", "
                        + "" + Double.parseDouble(formatter.format(totalCharge + totalStaxCharge)) + ", '', "
                        + "'" + entityList.get(0).getEnterBy() + "', '" + ymd.format(entityList.get(0).getTranDt()) + "',"
                        + "'" + creditedGLHead + "','" + ymd.format(dmy.parse(frDt)) + "',"
                        + "'" + ymd.format(dmy.parse(toDt)) + "','" + accountType + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in mb_charge_posting_history insertion.");
                }
                //Updation of cbs_loan_acctype_interest_parameter
//                n = entityManager.createNativeQuery("update cbs_loan_acctype_interest_parameter set post_flag='Y',"
//                        + "post_dt=now() where post_flag='N' and flag='MB' and brncode='" + orgnBrCode + "' and "
//                        + "from_dt='" + ymd.format(dmy.parse(frDt)) + "' and "
//                        + "to_dt='" + ymd.format(dmy.parse(toDt)) + "' and ac_type='" + accountType + "'").executeUpdate();
                n = entityManager.createNativeQuery("update cbs_loan_acctype_interest_parameter set post_flag='Y',"
                        + "post_dt=now() where post_flag='N' and flag='MB' and brncode='" + orgnBrCode + "' and "
                        + "from_dt='" + ymd.format(dmy.parse(frDt)) + "' and ac_type='" + accountType + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Parameter Table Updation.");
                }

                List snoList = entityManager.createNativeQuery("select ifnull(max(sno),0)+1 from "
                        + "cbs_loan_acctype_interest_parameter").getResultList();
                Vector snoVec = (Vector) snoList.get(0);

                String[] dtArr = prepareNextFreqDt(ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)));
                n = entityManager.createNativeQuery("insert into cbs_loan_acctype_interest_parameter (sno, ac_type, from_dt, to_dt, "
                        + "post_flag, dt, post_dt, brncode, enter_by, flag) values (" + Integer.parseInt(snoVec.get(0).toString()) + ", "
                        + "'" + accountType + "', '" + dtArr[0] + "', '" + dtArr[1] + "', 'N', now(), null, '" + orgnBrCode + "', "
                        + "'System', 'MB')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Parameter Table Insertion.");
                }
            }
            //Last Recno Updation
            fts.updateRecNo(recno);
            ut.commit();
            msg = "Record[s] has been posted successfully. Batch No is-->" + trsno;
            System.out.println("End Time-->" + new Date());
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return msg;
    }

    public String[] prepareNextFreqDt(String frDt, String toDt) throws Exception {
        String[] arr = new String[2];
        try {
            String fromDt = CbsUtil.dateAdd(toDt, 1);
            long noOfMonth = CbsUtil.monthDiff(ymd.parse(frDt), ymd.parse(toDt));
            noOfMonth = noOfMonth == 0 ? 1 : (noOfMonth + 1);
            String tempToDt = CbsUtil.monthAdd(toDt, Integer.parseInt(String.valueOf(noOfMonth)));

            arr[0] = fromDt; //Next Fr Dt
            arr[1] = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(tempToDt.substring(4, 6)),
                    Integer.parseInt(tempToDt.substring(0, 4)))); //Next To Dt
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }

//    public String postFixedChargeHistory(int fixedOrPerSms, List<MbChargePostingIndividualHistoryTO> entityList, String type,
//            String orgnBrCode, String creditedGLHead, String frDt, String toDt, String nature, String acType) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        String msg = "", acNature = "", username = "";
//        float recno = 0.0f;
//        int taxApplyCode = 0;
//        double totalCharge = 0.0, totalStaxCharge = 0.0;
//        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            ut.begin();
//            String currentDt = fts.daybeginDate(orgnBrCode);
//            String accountType = acType.equalsIgnoreCase("A") ? nature : acType;
//
//            if (type.equalsIgnoreCase("I")) {
//                MbChargePostingIndividualHistoryTO toEntity = entityList.get(0);
//                Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CHECK_ALREADY_POSTED_ACCOUNT);
//                createNamedQuery.setParameter("acno", toEntity.getAcno());
//                List resultList = createNamedQuery.getResultList();
//                if (!resultList.isEmpty() && resultList.get(0) != null) {
//                    Date maxToDt = (Date) resultList.get(0);
//                    if (toEntity.getFromDate().compareTo(maxToDt) <= 0) {
//                        throw new ApplicationException("Charge has been posted already for this account.");
//                    }
//                }
//            } else if (type.equalsIgnoreCase("A")) {
//                Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TO_DATE_BY_BRANCH_AND_ACTYPE);
//                createNamedQuery.setParameter("brncode", orgnBrCode);
//                createNamedQuery.setParameter("acType", accountType);
//                List resultList = createNamedQuery.getResultList();
//                if (!resultList.isEmpty() && resultList.get(0) != null) {
//                    Date maxToDt = (Date) resultList.get(0);
//                    if (dmy.parse(frDt).compareTo(maxToDt) <= 0) {
//                        throw new ApplicationException("Charge has been already posted for this branch in this duration.");
//                    }
//                }
//            }
//            
//            taxApplyCode = fts.getCodeForReportName("STAXMODULE_ACTIVE");
//            float trsno = fts.getTrsNo().floatValue();
//            for (int k = 0; k < entityList.size(); k++) {
//                MbChargePostingIndividualHistoryTO toEntity = entityList.get(k);
//                acNature = fts.getAccountNature(toEntity.getBillingAcno());
//                username = toEntity.getEnterBy();
//                double partySerCharge = 0.0;
//
//                if (taxApplyCode == 1) {
//                    Map<String, Double> map = ibRemote.getTaxComponent(toEntity.getDrAmount(), ymd.format(new Date()));
//                    Set<Entry<String, Double>> set = map.entrySet();
//                    Iterator<Entry<String, Double>> it = set.iterator();
//                    while (it.hasNext()) {
//                        Entry entry = it.next();
//                        partySerCharge = partySerCharge + Double.parseDouble(entry.getValue().toString());
//                    }
//                }
//
//                
//                double totalDramt = toEntity.getDrAmount() + partySerCharge;
//                msg = fts.checkBalance(toEntity.getBillingAcno(), totalDramt, username);
//                if (!msg.equalsIgnoreCase("TRUE")) {
//                    int n = entityManager.createNativeQuery("Insert into pendingcharges(Acno,Dt,Amount,Details,InstNo,"
//                            + "TY,TranType,RecNo,Trsno,EnterBy,Auth, AuthBy,UpdateDt,UpdateBy,charges,recover,"
//                            + "trandesc) Values('" + toEntity.getBillingAcno() + "','" + currentDt + "',"
//                            + "" + totalDramt + ",'Pending SMS Charge','',1,2,0," + trsno + ","
//                            + "'" + username + "','Y','" + username + "','" + currentDt + "',"
//                            + "'" + username + "',Null,'N',7)").executeUpdate();
//                    if (n <= 0) {
//                        throw new ApplicationException("Problem in Pendingcharges Insertion");
//                    }
//                    continue;
//                }
//                recno = fts.getRecNo().floatValue();
//                msg = fts.insertRecons(acNature, toEntity.getBillingAcno(), 1, toEntity.getDrAmount(), currentDt,
//                        currentDt, 2, "SMS Charge Posting", username, trsno, ymdh.format(new Date()), recno, "Y",
//                        username, 35, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0, "", "", "");
//                if (!msg.equalsIgnoreCase("true")) {
//                    throw new ApplicationException(msg);
//                }
//                
//                if (partySerCharge != 0 && taxApplyCode == 1) {
//                    msg = fts.insertRecons(acNature, toEntity.getBillingAcno(), 1, partySerCharge, currentDt,
//                            currentDt, 2, "SMS Charge Service Tax", username, trsno, ymdh.format(new Date()),
//                            fts.getRecNo().floatValue(), "Y", username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
//                            orgnBrCode, orgnBrCode, 0, "", "", "");
//                    if (!msg.equalsIgnoreCase("true")) {
//                        throw new ApplicationException(msg);
//                    }
//                }
//
//                msg = fts.updateBalance(acNature, toEntity.getBillingAcno(), 0.0, totalDramt, "", "");
//                if (!msg.equalsIgnoreCase("true")) {
//                    throw new ApplicationException(msg);
//                }
//                totalCharge += toEntity.getDrAmount();
//                totalStaxCharge += partySerCharge;
//            }
//            
//            acNature = fts.getAccountNature(creditedGLHead);
//            recno = fts.getRecNo().floatValue();
//            if (totalCharge == 0) {
//                throw new ApplicationException("Cr amount can not be zero.");
//            }
//            msg = fts.insertRecons(acNature, creditedGLHead, 0, totalCharge, currentDt, currentDt,
//                    2, "SMS Charge Posting", username, trsno, ymdh.format(new Date()), recno, "Y", username, 35,
//                    3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0, "", "", "");
//            if (!msg.equalsIgnoreCase("true")) {
//                throw new ApplicationException(msg);
//            }
//            msg = fts.updateBalance(acNature, creditedGLHead, totalCharge, 0.0, "", "");
//            if (!msg.equalsIgnoreCase("true")) {
//                throw new ApplicationException(msg);
//            }
//            
//            if (taxApplyCode == 1) {
//                Map<String, Double> slabMap = ibRemote.getTaxComponentSlab(ymd.format(new Date()));
//                Set<Entry<String, Double>> set = slabMap.entrySet();
//                Iterator<Entry<String, Double>> it = set.iterator();
//                int roundUpTo = 0;
//                double totalRot = 0;
//                while (it.hasNext()) {
//                    Entry entry = it.next();
//                    String[] keyArray = entry.getKey().toString().split(":");
//                    roundUpTo = Integer.parseInt(keyArray[3]);
//                    totalRot += Double.parseDouble(entry.getValue().toString());
//                }
//                double totalChgBasedOnTotalServiceTax = CbsUtil.round(((totalStaxCharge * 100) / totalRot), roundUpTo);
//
//
//                Map<String, Double> map = ibRemote.getTaxComponent(totalChgBasedOnTotalServiceTax, ymd.format(new Date()));
//                Set<Entry<String, Double>> staxSet = map.entrySet();
//                Iterator<Entry<String, Double>> staxIt = staxSet.iterator();
//                while (staxIt.hasNext()) {
//                    Entry entry = staxIt.next();
//                    String[] keyArray = entry.getKey().toString().split(":");
//                    String description = keyArray[0];
//                    String taxHead = orgnBrCode + keyArray[1];
//                    double serAmount = Double.parseDouble(entry.getValue().toString());
//
//                    if (serAmount != 0) {
//                        description = description + " In Sms Charge";
//                        msg = fts.insertRecons("PO", taxHead, 0, serAmount, currentDt, currentDt, 2,
//                                description, username, trsno, ymdh.format(new Date()), fts.getRecNo().floatValue(), "Y",
//                                username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0,
//                                "", "", "");
//                        if (!msg.equalsIgnoreCase("true")) {
//                            throw new ApplicationException(msg);
//                        }
//
//                        msg = fts.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
//                        if (!msg.equalsIgnoreCase("true")) {
//                            throw new ApplicationException(msg);
//                        }
//                    }
//                }
//            }
//            
//            MbChargePostingIndivisualHistoryDAO dao = new MbChargePostingIndivisualHistoryDAO(entityManager);
//            MbPushMsgTabDAO msgTabDAO = new MbPushMsgTabDAO(entityManager);
//            long maxTxnId = dao.getMaxTxnId();
//            for (MbChargePostingIndividualHistoryTO entityTO : entityList) {
//                MbChargePostingIndivisualHistory entity = new MbChargePostingIndivisualHistory();
//                entity.setAcno(entityTO.getAcno());
//                entity.setDrAmount(entityTO.getDrAmount());
//                entity.setEnterBy(entityTO.getEnterBy());
//                entity.setFromDate(entityTO.getFromDate());
//                entity.setMessageType("");
//                entity.setToDate(entityTO.getToDate());
//                entity.setTranDt(new Date());
//                entity.setTxnId(++maxTxnId);
//                entity.setTrsNo(trsno);
//                dao.save(entity);
//
//                List<MbPushMsgTab> dataList = msgTabDAO.getAllFixedSendedSms(entityTO.getAcno());
//                for (int k = 0; k < dataList.size(); k++) {
//                    MbPushMsgTab pushEntity = dataList.get(k);
//                    pushEntity.setMessageStatus(3);
//                    msgTabDAO.update(pushEntity);
//                }
//            }
//            if (type.equalsIgnoreCase("A")) {
//                
//                MbChargePostingHistoryDAO historyDAO = new MbChargePostingHistoryDAO(entityManager);
//                long maxTxnId1 = historyDAO.getMaxTxnId();
//                MbChargePostingHistory chargePostingHistory = new MbChargePostingHistory();
//                chargePostingHistory.setTxnId(++maxTxnId1);
//                chargePostingHistory.setGlCrAmount(totalCharge);
//                chargePostingHistory.setMessageType("");
//                chargePostingHistory.setTranDt(new Date());
//                chargePostingHistory.setEnterBy(entityList.get(0).getEnterBy());
//                chargePostingHistory.setTrsNo(trsno);
//                chargePostingHistory.setCrAcno(creditedGLHead);
//                chargePostingHistory.setFromDate(dmy.parse(frDt));
//                chargePostingHistory.setToDate(dmy.parse(toDt));
//                historyDAO.save(chargePostingHistory);
//                
//                int n = entityManager.createNativeQuery("update cbs_loan_acctype_interest_parameter set post_flag='Y',"
//                        + "post_dt=now() where post_flag='N' and flag='MB' and brncode='" + orgnBrCode + "' and "
//                        + "from_dt='" + ymd.format(dmy.parse(frDt)) + "' and "
//                        + "to_dt='" + ymd.format(dmy.parse(toDt)) + "' and ac_type='" + accountType + "'").executeUpdate();
//                if (n <= 0) {
//                    throw new ApplicationException("Problem In Parameter Table Updation.");
//                }
//            }
//            ut.commit();
//            msg = "Record[s] has been posted successfully. Generated batch no is : " + String.valueOf(trsno);
//        } catch (Exception e) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(e.getMessage());
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//        return msg;
//    }
    public List<MbSmsSenderBankDetailTO> getBankAndSenderDetail() throws ApplicationException {
        long begin = System.nanoTime();
        List<MbSmsSenderBankDetailTO> resultList = new ArrayList<MbSmsSenderBankDetailTO>();
        MbSmsSenderBankDetailDAO mbSmsSenderBankDetailDAO = new MbSmsSenderBankDetailDAO(entityManager);
        try {
            List<MbSmsSenderBankDetail> entityList = mbSmsSenderBankDetailDAO.getBankAndSenderDetail();
            for (MbSmsSenderBankDetail entity : entityList) {
                resultList.add(ObjectAdaptorSms.adaptToMbSmsSenderBankDetailTO(entity));
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPopUpForm is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return resultList;
    }

    public MbServicesDescriptionTO getEntityByServiceCode(Integer code) throws ApplicationException {
        long begin = System.nanoTime();
        MbServicesDescriptionTO to = new MbServicesDescriptionTO();
        MbServicesDescriptionDAO mbServicesDescriptionDAO = new MbServicesDescriptionDAO(entityManager);
        try {
            MbServicesDescription entity = mbServicesDescriptionDAO.getEntityByServiceCode(code);
            to = ObjectAdaptorSms.adaptToMbServicesDescriptionTO(entity);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEntityByServiceCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getEntityByServiceCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return to;
    }

    public BranchMasterTO branchmasterEntityByBrncode(Integer brncode) throws ApplicationException {
        long begin = System.nanoTime();
        BranchMasterTO to = new BranchMasterTO();
        BranchMasterDAO branchMasterDAO = new BranchMasterDAO(entityManager);
        try {
            to = ObjectAdaptorMaster.adaptToBranchMasterTO(branchMasterDAO.getEntityByBrnCode(brncode));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEntityByServiceCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getEntityByServiceCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return to;
    }

    public BnkaddTO bankNameByBnkAdd(String alphacode) throws ApplicationException {
        long begin = System.nanoTime();
        BnkaddTO to = new BnkaddTO();
        BnkAddDAO bnkAddDAO = new BnkAddDAO(entityManager);
        try {
            to = ObjectAdaptorMaster.adaptToBnkaddTO(bnkAddDAO.bankNameByBnkAdd(alphacode));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEntityByServiceCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getEntityByServiceCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return to;
    }

    public List getAllStaffMobileNo(String orgnBrCode) throws ApplicationException {
        long begin = System.nanoTime();
        List mbSubscriberTabTOs = new ArrayList();
        MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
        try {
            List mbSubscriberTabs = mbSubscriberTabDAO.getAllStaffMobileNo(orgnBrCode);
            if (!mbSubscriberTabs.isEmpty()) {
                mbSubscriberTabTOs.addAll(mbSubscriberTabs);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED, "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return mbSubscriberTabTOs;
    }

    public GltableTO getGlTableByAcno(String acno) throws ApplicationException {
        GlTableDAO glTableDAO = new GlTableDAO(entityManager);
        try {
            GltableTO to = ObjectAdaptorMaster.adaptToGltableTO(glTableDAO.findByPK(acno));
            return to;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String isExistMobileAcCodeAndPin(String mobile, String accode, String pin) throws ApplicationException {
        MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
        try {
            Object obj = mbSubscriberTabDAO.getSubscriberByMobilePinAndAccType(mobile, accode, pin);
            if (obj != null) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<CbsChargeDetailTO> getCbsChargeDetail(String chargeType) throws ApplicationException {
        CbsChargeDetailDAO cbsChargeDetailDAO = new CbsChargeDetailDAO(entityManager);
        List<CbsChargeDetailTO> toList = new ArrayList<CbsChargeDetailTO>();
        try {
            List<CbsChargeDetail> list = cbsChargeDetailDAO.getCbsChargeDetail(chargeType);
            for (CbsChargeDetail entity : list) {
                toList.add(ObjectAdaptorMaster.adaptToCbsChargeDetailTO(entity));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return toList;
    }

    public List<CbsChargeDetailTO> getCbsChargeDetailByTypeNameAndActype(String chargeType, String chargeName, String acType) throws ApplicationException {
        CbsChargeDetailDAO cbsChargeDetailDAO = new CbsChargeDetailDAO(entityManager);
        List<CbsChargeDetailTO> toList = new ArrayList<CbsChargeDetailTO>();
        try {
            List<CbsChargeDetail> list = cbsChargeDetailDAO.getCbsChargeDetailByTypeNameAndActype(chargeType, chargeName, acType);
            for (CbsChargeDetail entity : list) {
                toList.add(ObjectAdaptorMaster.adaptToCbsChargeDetailTO(entity));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return toList;
    }

//    public List<String[]> getAllAccountOfBarnch(String frDt, String toDt, String brCode) throws ApplicationException {
//        List<String[]> dataList = new ArrayList<String[]>();
//        MbSubscriberTabDAO dao = new MbSubscriberTabDAO(entityManager);
//        try {
//            Integer staffInclude = fts.getCodeForReportName("SMS-CHG-WAIVE-STAFF");
//            dataList = dao.getAllAccountOfBarnch(frDt, toDt, brCode, staffInclude);
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//        return dataList;
//    }
    public List<SmsPostingTo> getAccountDetail(int chgBasedOn, String frDt, String toDt, String brCode,
            String nature, String acType) throws ApplicationException {
        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<SmsPostingTo> list = new CopyOnWriteArrayList<SmsPostingTo>();
        List dataList = new ArrayList();
        try {
//            String query = ""; //All nature in one go
//            if ((nature != null && !nature.equals("")) && acType == null) {   //Nature Wise
//                query = " and substring(acno,3,2) in(select acctcode from accounttypemaster where acctnature='" + nature + "') ";
//            } else if ((nature != null && !nature.equals("")) && (acType != null && !acType.equals(""))) { //Account Type
//                query = " and substring(acno,3,2) in('" + acType + "') ";
//            }

            String query = ""; //All nature in one go
            if (chgBasedOn == 1) {   //Nature Wise
                query = " and substring(acno,3,2) in(select acctcode from accounttypemaster where acctnature='" + nature + "') ";
            } else if (chgBasedOn == 0) { //Account Type
                query = " and substring(acno,3,2) in('" + acType + "') ";
            }

            Integer staffInclude = fts.getCodeForReportName("SMS-CHG-WAIVE-STAFF");
            if (staffInclude == 0) { //Staff Exclude
                dataList = entityManager.createNativeQuery("select aa.acno,aa.bill_deduction_acno,aa.acctnature, bb.stateCode, bb.brState  from "
                        + " (select mb.acno,mb.bill_deduction_acno,att.acctnature "
                        + " from mb_subscriber_tab mb, accounttypemaster att where mb.status=1 and mb.auth='Y' and "
                        + " mb.auth_status='V' and substring(mb.acno,1,2)='" + brCode + "' and "
                        + " date_format(mb.created_date,'%Y%m%d')<='" + ymd.format(dmy.parse(toDt)) + "' and "
                        + " mb.acno_type<>'STAFF' and substring(mb.bill_deduction_acno,3,2)="
                        + " att.acctcode " + query + ")aa,"
                        + " (select ci.Acno as acno, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState "
                        + " from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + " where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + brCode + "')  bb "
                        + " where aa.acno = bb.acno order by aa.acno").getResultList();
            } else if (staffInclude == 1) { //All staff and non-staff
                dataList = entityManager.createNativeQuery("select aa.acno,aa.bill_deduction_acno,aa.acctnature, bb.stateCode, bb.brState from "
                        + " (select mb.acno,mb.bill_deduction_acno,att.acctnature "
                        + "from mb_subscriber_tab mb, accounttypemaster att where mb.status=1 and mb.auth='Y' and "
                        + "mb.auth_status='V' and substring(mb.acno,1,2)='" + brCode + "' and "
                        + "date_format(mb.created_date,'%Y%m%d')<='" + ymd.format(dmy.parse(toDt)) + "' and "
                        + "substring(mb.bill_deduction_acno,3,2)=att.acctcode " + query + ")aa,"
                        + " (select ci.Acno as acno, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState  "
                        + " from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + " where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + brCode + "')  bb "
                        + " where aa.acno = bb.acno order by aa.acno").getResultList();
            }
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                String billingAccount = ele.get(1).toString();
                String actNature = ele.get(2).toString();

                if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                        || actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    SmsPostingTo to = new SmsPostingTo();
                    to.setAcno(ele.get(0).toString());
                    to.setBillingAcno(billingAccount);
                    to.setFromDt(frDt);
                    to.setBillingAcnoNature(actNature);
                    to.setCustState(ele.get(3).toString());
                    to.setBrState(ele.get(4).toString());

                    list.add(to);
                }
            }

            System.out.println("Size of final list:-->" + list.size());
            //This process will slow down
            for (SmsPostingTo to : list) {
                dataList = entityManager.createNativeQuery("select max(to_date) from mb_charge_posting_indivisual_history "
                        + "where acno='" + to.getAcno() + "'").getResultList();
                Vector ele = (Vector) dataList.get(0);
                if (!dataList.isEmpty() && ele.get(0) != null) {
                    String maxToDt = dmy.format(sdf.parse(ele.get(0).toString()));
                    if (dmy.parse(frDt).compareTo(dmy.parse(maxToDt)) <= 0) {
                        list.remove(to);
                    }
                } else {
                    dataList = entityManager.createNativeQuery("select created_date from mb_subscriber_tab where "
                            + "acno='" + to.getAcno() + "'").getResultList();
                    ele = (Vector) dataList.get(0);
                    String regDt = dmy.format(sdf.parse(ele.get(0).toString()));
                    to.setFromDt(frDt);
                    if (dmy.parse(regDt).compareTo(dmy.parse(frDt)) > 0) {
                        to.setFromDt(regDt);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public List getAllAccountByAuthAndAuthStatus(String auth, String authStatus) throws ApplicationException {
        MbSubscriberTabDAO dao = new MbSubscriberTabDAO(entityManager);
        try {
            return dao.getAllAccountByAuthAndAuthStatus(auth, authStatus);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAllAccountToVerify(String orgnBrCode) throws ApplicationException {
        MbSubscriberTabDAO dao = new MbSubscriberTabDAO(entityManager);
        try {
            return dao.getAllAccountToVerify(orgnBrCode);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public MbSubscriberTab checkAccountByAuthAndAuthStatus(String auth, String authStatus, String acno) throws ApplicationException {
        MbSubscriberTabDAO dao = new MbSubscriberTabDAO(entityManager);
        try {
            return dao.checkAccountByAuthAndAuthStatus(auth, authStatus, acno);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getSmsChgFrequency(int chargeBasedOnParam, String nature, String acType, String orgnBrCode) throws ApplicationException {
        try {
            String accountType = "";
            if (chargeBasedOnParam == 2) {
                accountType = "A";
            } else if (chargeBasedOnParam == 1) {
                accountType = nature;
            } else if (chargeBasedOnParam == 0) {
                accountType = acType;
            }

            return entityManager.createNativeQuery("select DATE_FORMAT(min(from_dt),'%Y%m%d') as fromdt,"
                    + "DATE_FORMAT(min(to_dt),'%Y%m%d') as todt from cbs_loan_acctype_interest_parameter "
                    + "where flag='MB' and post_flag='N' and brncode='" + orgnBrCode + "' and "
                    + "ac_type='" + accountType + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getCodeByReportName(String reportName) throws ApplicationException {
        try {
            List list = entityManager.createNativeQuery("select code from parameterinfo_report "
                    + "where reportname='" + reportName + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define in parameterinfo_report for:" + reportName);
            }
            Vector element = (Vector) list.get(0);
            return element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public boolean isValidAcnoForSms(String msgType, String acno, int tranType, int ty,
            double amt) throws ApplicationException {
        MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
        MbServicesDescriptionDAO mbServiceDAO = new MbServicesDescriptionDAO(entityManager);
        MbSubscriberServicesDAO mbSubscriberServicesDAO = new MbSubscriberServicesDAO(entityManager);
        ParameterinfoReportDAO parameterinfoReportDAO = new ParameterinfoReportDAO(entityManager);
        try {
            ParameterinfoReport entity = parameterinfoReportDAO.getCodeByReportName("SMS");
            if (entity.getCode() != 1) {
                return false;
            }
            if (fts.getAccountNature(acno).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                return false;
            }
            if (msgType.equalsIgnoreCase("T")) {
                MbSubscriberTab mbSubscriberTabEntity = mbSubscriberTabDAO.checkRegisteredAccount("Y", "V", acno, "1");
                if (mbSubscriberTabEntity == null) {
                    return false;
                }

                MbServicesDescription mbServiceDesc = mbServiceDAO.getEntityByServiceName(SmsServicesEnum.getService(tranType + "" + ty));
                MbSubscriberServices mbSubService = mbSubscriberServicesDAO.getEntityByAcnoAndService(acno, mbServiceDesc.getServiceCode());
                if (mbSubService == null) {
                    return false;
                }
                if (tranType == 0 && ty == 0 && amt >= mbSubscriberTabEntity.getCashCrLimit()) {
                    return true;
                } else if (tranType == 0 && ty == 1 && amt >= mbSubscriberTabEntity.getCashDrLimit()) {
                    return true;
                } else if (tranType == 1 && ty == 0 && amt >= mbSubscriberTabEntity.getClgCrLimit()) {
                    return true;
                } else if (tranType == 1 && ty == 1 && amt >= mbSubscriberTabEntity.getClgDrLimit()) {
                    return true;
                } else if (tranType == 2 && ty == 0 && amt >= mbSubscriberTabEntity.getTrfCrLimit()) {
                    return true;
                } else if (tranType == 2 && ty == 1 && amt >= mbSubscriberTabEntity.getTrfDrLimit()) {
                    return true;
                } else if (tranType == 8 && amt >= mbSubscriberTabEntity.getInterestLimit()) { //Interest
                    return true;
                } else if (tranType == 9 && amt >= mbSubscriberTabEntity.getChargeLimit()) { //Charges
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method isValidAcnoForSms()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED, "System exception has occured"));
        }
    }

    @Override
    public void sendSms(TransferSmsRequestTo transferSmsRequestTo) throws ApplicationException {
        try {
            if (isValidAcnoForSms(transferSmsRequestTo.getMsgType(), transferSmsRequestTo.getAcno(),
                    transferSmsRequestTo.getTranType() == null ? 0 : transferSmsRequestTo.getTranType(),
                    transferSmsRequestTo.getTy() == null ? 0 : transferSmsRequestTo.getTy(),
                    transferSmsRequestTo.getAmount() == null ? 0d
                    : Double.parseDouble(formatter.format(transferSmsRequestTo.getAmount())))) {

                transferSmsRequestTo.setBalFlag(transferSmsRequestTo.getBalFlag() == null ? "N"
                        : transferSmsRequestTo.getBalFlag());

                //Pending Balance.
                List pendingList = txnRemote.selectPendingBalance(transferSmsRequestTo.getAcno());
                Vector element = (Vector) pendingList.get(0);
                Double pendingBal = Double.parseDouble(element.get(0).toString());

                SmsRequestTO requestTo = new SmsRequestTO();
                requestTo.setSmsTemplate(transferSmsRequestTo.getTemplate()); //Mandatory
                requestTo.setAcNo("XXXX" + transferSmsRequestTo.getAcno().substring(4, 10) + "XX"); //Mandatory
                requestTo.setAmount(transferSmsRequestTo.getAmount() == null ? new BigDecimal("0")
                        : new BigDecimal(formatter.format(transferSmsRequestTo.getAmount())));

                requestTo.setTranType(transferSmsRequestTo.getTranType() == null ? 0 : transferSmsRequestTo.getTranType());
                requestTo.setTy(transferSmsRequestTo.getTy() == null ? 0 : transferSmsRequestTo.getTy());
                requestTo.setTxnDt(transferSmsRequestTo.getDate() == null ? smsDtFormat.format(dmy.parse(dmy.format(new Date())))
                        : smsDtFormat.format(dmy.parse(transferSmsRequestTo.getDate()))); //Mandatory in case of T. 
                //Format should be dd/MM/YYYY of transferSmsRequestTo.getDate().
                requestTo.setActualAcNo(transferSmsRequestTo.getAcno());

                requestTo.setPromoMsg(transferSmsRequestTo.getPromoMsg() == null ? "" : transferSmsRequestTo.getPromoMsg());
                requestTo.setPin(transferSmsRequestTo.getPin() == null ? "" : transferSmsRequestTo.getPin());
                requestTo.setPullCode(transferSmsRequestTo.getPullCode() == null ? "" : transferSmsRequestTo.getPullCode());
                requestTo.setServices(transferSmsRequestTo.getServices() == null ? "" : transferSmsRequestTo.getServices());

                requestTo.setMiniStatement(transferSmsRequestTo.getMiniStatement() == null ? "" : transferSmsRequestTo.getMiniStatement());
                requestTo.setFirstCheque(transferSmsRequestTo.getFirstCheque() == null ? "" : transferSmsRequestTo.getFirstCheque());
                requestTo.setLastCheque(transferSmsRequestTo.getLastCheque() == null ? "" : transferSmsRequestTo.getLastCheque());
                if (transferSmsRequestTo.getBankName() == null || transferSmsRequestTo.getBankName().equals("")) {
                    List<MbSmsSenderBankDetailTO> bankTo = getBankAndSenderDetail();
                    requestTo.setBankName(bankTo.get(0).getTemplateBankName());
                } else {
                    requestTo.setBankName(transferSmsRequestTo.getBankName());
                }
                requestTo.setModuleName(transferSmsRequestTo.getModuleName() == null ? "" : transferSmsRequestTo.getModuleName());
                requestTo.setUserMsgId("");
                requestTo.setUserName(transferSmsRequestTo.getUserName() == null ? "System" : transferSmsRequestTo.getUserName());
                requestTo.setDetails(transferSmsRequestTo.getDetails() == null ? "" : transferSmsRequestTo.getDetails());
                requestTo.setPendingBalance(transferSmsRequestTo.getPendingBalance() == null ? "0.0" : String.valueOf(pendingBal));

                //transferSmsRequestTo.getMsgType()-->P/T/PAT(Promotinal/Transactional/Promotional As Transactional 
                //Like-Registration,Issue Cheque Book)
                //transferSmsRequestTo.getTemplate()-->Actual VM Name.
                if (transferSmsRequestTo.getMsgType().equalsIgnoreCase("P")
                        || transferSmsRequestTo.getMsgType().equalsIgnoreCase("PAT")) {
                    requestTo.setMobile(transferSmsRequestTo.getPromoMobile().trim().substring(1)); //Mandatory
                    requestTo.setBalance("0");
                    if (transferSmsRequestTo.getBalFlag().equalsIgnoreCase("Y")) {    //If you need then set bal flag to Y. Other wise not.
                        //Pending Balance.
//                        List pendingList = txnRemote.selectPendingBalance(transferSmsRequestTo.getAcno());
//                        Vector element = (Vector) pendingList.get(0);
//                        Double pendingBal = Double.parseDouble(element.get(0).toString());
                        //Actual Ledger Balance.
                        //Double ledgerBal = commonReport.getBalanceOnDate(transferSmsRequestTo.getAcno(),
                         //       ymd.format(dmy.parse(transferSmsRequestTo.getDate())));
                        Double ledgerBal = Double.parseDouble(fts.ftsGetBal(transferSmsRequestTo.getAcno()));
                        requestTo.setBalance(formatter.format(ledgerBal - pendingBal));
                    }
                    requestTo.setMessageType(transferSmsRequestTo.getMsgType().equalsIgnoreCase("P") ? "P" : "T"); //Mandatory
                } else {                                //Transactional Message
                    MbSubscriberTabTO to = getSubscriberDetails(transferSmsRequestTo.getAcno());
                    requestTo.setMobile(to.getMobileNo().substring(1));
                    //Pending Balance.
//                    List pendingList = txnRemote.selectPendingBalance(transferSmsRequestTo.getAcno());
//                    Vector element = (Vector) pendingList.get(0);
//                    Double pendingBal = Double.parseDouble(element.get(0).toString());
                    //Actual Ledger Balance.
                    
                    //Double ledgerBal = commonReport.getBalanceOnDate(transferSmsRequestTo.getAcno(),
//                            ymd.format(dmy.parse(transferSmsRequestTo.getDate())));
                    
                     Double ledgerBal = Double.parseDouble(fts.ftsGetBal(transferSmsRequestTo.getAcno()));
                    requestTo.setBalance(formatter.format(ledgerBal - pendingBal));
                    requestTo.setMessageType(transferSmsRequestTo.getMsgType()); //Mandatory
                }
                new SmsService().postSms(requestTo);

                System.out.println("After Sending SMS In SmsManagementFacade-->A/c is::" + transferSmsRequestTo.getAcno()
                        + " And Amount is::" + transferSmsRequestTo.getAmount());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void sendMinimumBalanceSms(List<TransferSmsRequestTo> smsList)
            throws ApplicationException {
        try {
            for (TransferSmsRequestTo trfSmsTo : smsList) {
                SmsRequestTO smsTo = new SmsRequestTO();

                smsTo.setMessageType("T");
                smsTo.setSmsTemplate(SmsType.MINIMUM_BALANCE);
                smsTo.setAcNo("XXXX" + trfSmsTo.getAcno().trim().substring(4, 10) + "XX");
                smsTo.setActualAcNo(trfSmsTo.getAcno().trim());
                smsTo.setBankName(trfSmsTo.getBankName().trim());
                smsTo.setMobile(trfSmsTo.getPromoMobile().trim().substring(1));
                smsTo.setModuleName("MB"); //For minimum balance
                smsTo.setUserMsgId("");

                new SmsService().postSms(smsTo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void sendTransactionalSms(SmsType template, String acno, Integer tranType,
            Integer ty, Double amount, String txnDt, String details) throws ApplicationException {
        try {
            TransferSmsRequestTo trfSmsRequestTo = new TransferSmsRequestTo();

            trfSmsRequestTo.setMsgType("T");
            trfSmsRequestTo.setTemplate(template);
            trfSmsRequestTo.setAcno(acno);
            trfSmsRequestTo.setTranType(tranType);
            trfSmsRequestTo.setTy(ty);
            trfSmsRequestTo.setAmount(amount);
            if (!new Validator().validateDate_dd_mm_yyyy(txnDt)) {
                throw new ApplicationException("Sending Sms Txn Date Is Wrong.");
            }
            trfSmsRequestTo.setDate(txnDt);
            trfSmsRequestTo.setDetails(details);

            sendSms(trfSmsRequestTo);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    @Override
    public void sendNeftRtgsTxnSms(SmsType template, String acno, Integer tranType,
            Integer ty, Double amount, String txnDt, String txnType, String beneName) throws ApplicationException {
        try {
            TransferSmsRequestTo trfSmsRequestTo = new TransferSmsRequestTo();

            trfSmsRequestTo.setMsgType("T");
            trfSmsRequestTo.setTemplate(template);
            trfSmsRequestTo.setAcno(acno);
            trfSmsRequestTo.setTranType(tranType);
            trfSmsRequestTo.setTy(ty);
            trfSmsRequestTo.setAmount(amount);
            if (!new Validator().validateDate_dd_mm_yyyy(txnDt)) {
                throw new ApplicationException("Sending Sms Txn Date Is Wrong.");
            }
            trfSmsRequestTo.setDate(txnDt);
            trfSmsRequestTo.setServices(txnType);
            trfSmsRequestTo.setDetails(beneName);

            sendSms(trfSmsRequestTo);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public void sendPromotionalSms(String acno, String promoMsg, String promoMobile, String bankName, String userName)
            throws ApplicationException {
        try {
            TransferSmsRequestTo trfSmsRequestTo = new TransferSmsRequestTo();

//            trfSmsRequestTo.setMsgType("P");
            trfSmsRequestTo.setMsgType("PAT");
            trfSmsRequestTo.setTemplate(SmsType.PROMOTIONAL);
            trfSmsRequestTo.setAcno(acno.trim());
            trfSmsRequestTo.setPromoMsg(promoMsg.trim());
            trfSmsRequestTo.setPromoMobile(promoMobile.trim());
            trfSmsRequestTo.setBankName(bankName.trim());
            trfSmsRequestTo.setUserName(userName);

            sendSms(trfSmsRequestTo);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void sendSmsToBatch(List<SmsToBatchTo> list) throws ApplicationException {
        try {
            for (SmsToBatchTo to : list) {
                String acNo = to.getAcNo();
                if (!fts.getAccountNature(acNo).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    try {
                        if (to.getTy() == 0) {
                            sendTransactionalSms(to.getTemplate(), acNo, to.getTranType(), to.getTy(),
                                    to.getCrAmt(), to.getTxnDt(), to.getDetails());
                        } else if (to.getTy() == 1) {
                            sendTransactionalSms(to.getTemplate(), acNo, to.getTranType(), to.getTy(),
                                    to.getDrAmt(), to.getTxnDt(), to.getDetails());
                        }
                    } catch (Exception ex) {
                        System.out.println("Error SMS Sending-->A/c is::" + acNo + " And Cr Amount is::"
                                + to.getCrAmt() + " And Dr Amount is::" + to.getDrAmt());
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void trfSmsRequestToBatch(List<TransferSmsRequestTo> list) throws ApplicationException {
        try {
            for (TransferSmsRequestTo to : list) {
                try {
                    sendSms(to);
                } catch (Exception ex) {
                    System.out.println("Error Sending SMS-->A/c is::"
                            + to.getAcno() + "Amount is-->" + to.getAmount());
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getTaxDetails() throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select rot,glhead from taxmaster where applicableflag='Y'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public boolean isActiveAccountForSms(String acno) throws ApplicationException {
        try {
            List chkList = entityManager.createNativeQuery("select acno from mb_subscriber_tab where "
                    + "status=1 and auth='Y' and auth_status='V' and acno='" + acno + "'").getResultList();
            if (chkList.isEmpty()) {
                throw new ApplicationException("This A/c is not active for SMS");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return true;
    }

    public List<SmsPostingTo> allAccountMessage(int chgBasedOn, String type, String acno, String nature, String acType,
            String frDt, String toDt, String orgnBrCode) throws ApplicationException {
        List<SmsPostingTo> returnList = new ArrayList<SmsPostingTo>();
        List dataList = new ArrayList();
        try {
            if (type.equalsIgnoreCase("A")) {
//                String query = ""; //All nature in one go
//                if ((nature != null && !nature.equals("")) && acType == null) {   //Nature Wise
//                    query = " and substring(m.acno,3,2) in(select acctcode from accounttypemaster where acctnature='" + nature + "') ";
//                } else if ((nature != null && !nature.equals("")) && (acType != null && !acType.equals(""))) { //Account Type
//                    query = " and substring(m.acno,3,2) in('" + acType + "') ";
//                }

                String query = ""; //All nature in one go
                if (chgBasedOn == 1) {   //Nature Wise
                    query = " and substring(m.acno,3,2) in(select acctcode from accounttypemaster where acctnature='" + nature + "') ";
                } else if (chgBasedOn == 0) { //Account Type
                    query = " and substring(m.acno,3,2) in('" + acType + "') ";
                }

                Integer staffInclude = fts.getCodeForReportName("SMS-CHG-WAIVE-STAFF");
                if (staffInclude == 0) { //Staff Exclude
                    dataList = entityManager.createNativeQuery("select aa.acno, aa.message, aa.bill_deduction_acno, aa.acctnature, bb.stateCode, bb.brState from "
                            + " (select p.acno,count(p.message) as message,m.bill_deduction_acno,"
                            + " atm.acctnature from mb_push_msg_tab p,mb_subscriber_tab m,accounttypemaster atm where "
                            + " m.status=1 and m.auth='Y' and m.auth_status='V' and m.acno_type<>'STAFF' and p.acno "
                            + " not like '' and p.message_status=2 and p.acno=m.acno and "
                            + " substring(p.acno,1,2)='" + orgnBrCode + "' and "
                            + " substring(m.bill_deduction_acno,3,2) = atm.acctcode and "
                            + " date_format(p.dt,'%Y%m%d') between '" + ymd.format(dmy.parse(frDt)) + "' "
                            + " and '" + ymd.format(dmy.parse(toDt)) + "' " + query + " group by p.acno) aa,"
                            + " (select ci.Acno as acno, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState "
                            + " from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                            + " where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + orgnBrCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + orgnBrCode + "') bb "
                            + " where aa.acno = bb.acno").getResultList();
                } else if (staffInclude == 1) { //All staff and non-staff
                    dataList = entityManager.createNativeQuery("select aa.acno, aa.message, aa.bill_deduction_acno, aa.acctnature, bb.stateCode, bb.brState from "
                            + " (select p.acno,count(p.message) as message,m.bill_deduction_acno,"
                            + " atm.acctnature from mb_push_msg_tab p,mb_subscriber_tab m,accounttypemaster atm where "
                            + " m.status=1 and m.auth='Y' and m.auth_status='V' and p.acno not like '' and "
                            + " p.message_status=2 and p.acno=m.acno and substring(p.acno,1,2)='" + orgnBrCode + "' and "
                            + " substring(m.bill_deduction_acno,3,2) = atm.acctcode and date_format(p.dt,'%Y%m%d') "
                            + " between '" + ymd.format(dmy.parse(frDt)) + "' and '" + ymd.format(dmy.parse(toDt)) + "' "
                            + query + " group by p.acno) aa,"
                            + " (select ci.Acno as acno, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState  "
                            + " from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                            + " where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + orgnBrCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + orgnBrCode + "')  bb "
                            + " where aa.acno = bb.acno").getResultList();
                }
            } else {
                dataList = entityManager.createNativeQuery("select p.acno,count(p.message),m.bill_deduction_acno,"
                        + "atm.acctnature,ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState "
                        + " from mb_push_msg_tab p,mb_subscriber_tab m,accounttypemaster atm, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + "where p.acno = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast(substring(m.acno,1,2) as unsigned) and "
                        + "p.acno=m.acno and p.acno not like '' and p.message_status=2 and m.status=1 and "
                        + "m.auth='Y' and m.auth_status='V' and m.acno='" + acno + "' and "
                        + "substring(m.bill_deduction_acno,3,2) = atm.acctcode group by p.acno").getResultList();
            }
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                String billingAccount = ele.get(2).toString();
                String actNature = ele.get(3).toString();

                if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                        || actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    SmsPostingTo to = new SmsPostingTo();
                    to.setAcno(ele.get(0).toString());
                    to.setBillingAcno(billingAccount);
                    to.setBillingAcnoNature(actNature);
                    to.setNoOfMessage(new BigInteger(ele.get(1).toString()));
                    to.setCustState(ele.get(4).toString());
                    to.setBrState(ele.get(5).toString());

                    returnList.add(to);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    @Override
    public void sendAtmSms(List<TransferSmsRequestTo> smsList)
            throws ApplicationException {
        try {
            for (TransferSmsRequestTo trfSmsTo : smsList) {
                SmsRequestTO smsTo = new SmsRequestTO();

                smsTo.setMessageType("T");
                if (trfSmsTo.getModuleName().equalsIgnoreCase("ATM")) {
                    if (trfSmsTo.getTxnType().equalsIgnoreCase("W")
                            && (trfSmsTo.getFirstCheque().equalsIgnoreCase("00")
                            || trfSmsTo.getFirstCheque().equalsIgnoreCase("01"))) { //ATM Withdrawal
                        smsTo.setSmsTemplate(SmsType.ATM_WITHDRAWAL);
                    } else if (trfSmsTo.getTxnType().equalsIgnoreCase("R")
                            && (trfSmsTo.getFirstCheque().equalsIgnoreCase("00")
                            || trfSmsTo.getFirstCheque().equalsIgnoreCase("01"))) { //ATM Reversal
                        smsTo.setSmsTemplate(SmsType.ATM_REVERSAL);
                    } else if (trfSmsTo.getTxnType().equalsIgnoreCase("W")
                            && (trfSmsTo.getFirstCheque().equalsIgnoreCase("44")
                            || trfSmsTo.getFirstCheque().equalsIgnoreCase("45")
                            || trfSmsTo.getFirstCheque().equalsIgnoreCase("46"))) { //POS Withdrawal
                        smsTo.setSmsTemplate(SmsType.POS_WITHDRAWAL);
                    } else if (trfSmsTo.getTxnType().equalsIgnoreCase("R")
                            && (trfSmsTo.getFirstCheque().equalsIgnoreCase("44")
                            || trfSmsTo.getFirstCheque().equalsIgnoreCase("45")
                            || trfSmsTo.getFirstCheque().equalsIgnoreCase("46"))) { //POS Reversal
                        smsTo.setSmsTemplate(SmsType.POS_REVERSAL);
                    } else if (trfSmsTo.getTxnType().equalsIgnoreCase("W")
                            && trfSmsTo.getFirstCheque().equalsIgnoreCase("49")) { //E-COM Withdrawal
                        smsTo.setSmsTemplate(SmsType.ECOM_WITHDRAWAL);
                    } else if (trfSmsTo.getTxnType().equalsIgnoreCase("R")
                            && trfSmsTo.getFirstCheque().equalsIgnoreCase("49")) { //E-COM Reversal
                        smsTo.setSmsTemplate(SmsType.ECOM_REVERSAL);
                    }

                    smsTo.setAcNo("XXXX" + trfSmsTo.getAcno().trim().substring(4, 10) + "XX");
                    smsTo.setActualAcNo(trfSmsTo.getAcno().trim());
                    smsTo.setBankName(trfSmsTo.getBankName().trim());
                    smsTo.setMobile(trfSmsTo.getPromoMobile().trim().substring(1));
                    smsTo.setModuleName(trfSmsTo.getModuleName());
                    smsTo.setAmount(BigDecimal.valueOf(trfSmsTo.getAmount()));
                    smsTo.setUserMsgId(trfSmsTo.getUserMsgId());
                    smsTo.setTxnDt(trfSmsTo.getDate() == null ? smsDtFormat.format(dmy.parse(dmy.format(new Date())))
                            : smsDtFormat.format(dmy.parse(trfSmsTo.getDate())));
                    String details = trfSmsTo.getLastCheque().trim();
                    String[] detailsArr = details.split("\\:");
                    smsTo.setFirstCheque(detailsArr[0].trim().substring(0, 2) + "XXX" + detailsArr[0].trim().substring(5)); //Card No
                    String terminalLocation = detailsArr[1].trim();
                    terminalLocation = (terminalLocation.length() >= 10) ? terminalLocation.substring(0, 10).trim() : terminalLocation;
                    smsTo.setLastCheque(terminalLocation.trim()); //Terminal Location
                    smsTo.setPin(detailsArr[2].trim()); //Terminal Id
                    //Balance coming from 
                    smsTo.setBalance(trfSmsTo.getAtmBal());
                } else if (trfSmsTo.getModuleName().equalsIgnoreCase("IMPS")) {
                    String[] detailArr = trfSmsTo.getLastCheque().split("/");
                    if ((trfSmsTo.getFirstCheque().equalsIgnoreCase("43") || trfSmsTo.getFirstCheque().equalsIgnoreCase("41")) && trfSmsTo.getTxnType().equalsIgnoreCase("D")) {
                        smsTo.setSmsTemplate(SmsType.IMPS_INWARD_SUCCESS);
                        smsTo.setAcNo("XXXX" + trfSmsTo.getAcno().trim().substring(4, 10) + "XX");
                        smsTo.setAmount(BigDecimal.valueOf(trfSmsTo.getAmount()));
                        smsTo.setTxnDt(trfSmsTo.getDate() == null ? dmyy.format(dmy.parse(dmy.format(new Date())))
                                : dmyy.format(dmy.parse(trfSmsTo.getDate())));
                        //Balance coming from 
                        smsTo.setBalance(trfSmsTo.getAtmBal());
                        smsTo.setActualAcNo(trfSmsTo.getAcno().trim());
                    } else if ((trfSmsTo.getFirstCheque().equalsIgnoreCase("42") || trfSmsTo.getFirstCheque().equalsIgnoreCase("41")) && trfSmsTo.getTxnType().equalsIgnoreCase("W")) {
                        smsTo.setSmsTemplate(SmsType.IMPS_OUTWARD_SUCCESS);
                        smsTo.setAcNo("XXXX" + trfSmsTo.getAcno().trim().substring(4, 10) + "XX");
                        smsTo.setAmount(BigDecimal.valueOf(trfSmsTo.getAmount()));
                        smsTo.setTxnDt(trfSmsTo.getDate() == null ? dmyy.format(dmy.parse(dmy.format(new Date())))
                                : dmyy.format(dmy.parse(trfSmsTo.getDate())));
                        smsTo.setPin(getImpsOtherBankAccountNo(detailArr[0].trim()));
                        smsTo.setLastCheque(detailArr[1].trim());
                        smsTo.setActualAcNo(trfSmsTo.getAcno().trim());
                    } else if (trfSmsTo.getFirstCheque().equalsIgnoreCase("42") && trfSmsTo.getTxnType().equalsIgnoreCase("R")) {
                        smsTo.setSmsTemplate(SmsType.IMPS_OUTWARD_FAIL);
                        smsTo.setAmount(BigDecimal.valueOf(trfSmsTo.getAmount()));
                        smsTo.setTxnDt(trfSmsTo.getDate() == null ? dmyy.format(dmy.parse(dmy.format(new Date())))
                                : dmyy.format(dmy.parse(trfSmsTo.getDate())));
                        smsTo.setLastCheque(detailArr[1].trim());
                        smsTo.setActualAcNo(trfSmsTo.getAcno().trim());
                    }
                    smsTo.setMobile(trfSmsTo.getPromoMobile().trim().substring(1));
                    smsTo.setModuleName(trfSmsTo.getModuleName());
                    smsTo.setUserMsgId(trfSmsTo.getUserMsgId());
                }
                new SmsService().postSms(smsTo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public static String getImpsOtherBankAccountNo(String othBankAccountNo) throws Exception {
        String returnBankAccountNo = "";
        try {
            if (othBankAccountNo.length() <= 3) {
                returnBankAccountNo = othBankAccountNo;
            } else {
                int desiredXLength = othBankAccountNo.length() - 3;
                returnBankAccountNo = ParseFileUtil.addTrailingDesiredCharacter(othBankAccountNo, desiredXLength, "X");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnBankAccountNo;
    }

    public List<SmsPostingTo> getAccountSmsDetail(String acno) throws ApplicationException {
        List<SmsPostingTo> returnList = new ArrayList<SmsPostingTo>();
        try {
            List dataList = entityManager.createNativeQuery("select mb.acno,mb.bill_deduction_acno,att.acctnature, "
                    + "ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState   "
                    + "from mb_subscriber_tab mb,accounttypemaster att, customerid ci, cbs_customer_master_detail cm, branchmaster br "
                    + " where mb.acno = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast(substring(mb.acno,1,2) as unsigned) and "
                    + " mb.status=1 and mb.auth='Y' and "
                    + " mb.auth_status='V' and mb.acno='" + acno + "' and "
                    + " substring(mb.bill_deduction_acno,3,2)=att.acctcode").getResultList();
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                String billingAccount = ele.get(1).toString();
                String actNature = ele.get(2).toString();
                if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                        || actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    SmsPostingTo to = new SmsPostingTo();
                    to.setAcno(ele.get(0).toString());
                    to.setBillingAcno(billingAccount);
                    to.setBillingAcnoNature(actNature);
                    to.setBrState(ele.get(4).toString());
                    to.setCustState(ele.get(3).toString());

                    returnList.add(to);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    public List getAllAccountNatureWithoutPO() throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select distinct(acctnature) from "
                    + "accounttypemaster where acctnature not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAllAccountCodeByNature(String acNature) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select distinct(acctcode) from "
                    + "accounttypemaster where acctnature in('" + acNature + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List<ProductWiseSmsDetailsPojo> getProductWiseSmSDetails(String Branch, String accNature, String accountType, String fromdate, String todate) throws ApplicationException {
        List<ProductWiseSmsDetailsPojo> dataList = new ArrayList<>();
        try {
            List resultList = entityManager.createNativeQuery("SELECT acno,mobile,message,message_type FROM mb_push_msg_tab  WHERE\n"
                    + " date_format(dt,'%Y%m%d') BETWEEN '" + fromdate + "' and '" + todate + "'").getResultList();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                ProductWiseSmsDetailsPojo pojo = new ProductWiseSmsDetailsPojo();
                pojo.setAcno(ele.get(0).toString());
                pojo.setMobile(ele.get(1).toString());
                pojo.setMessage(ele.get(2).toString());
                pojo.setMessage_Type(ele.get(3).toString());
                dataList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public String sendBulkSms(String vendorName, List<File> gupShupFiles, List<BulkSmsTo> bulkSmsList, List<String> digialayaAllXmlString,
            String bulkMode, File originalFile, String userName, String orgnCode, String todayDt) throws ApplicationException {
        String result = "";
        try {
            Integer code = fts.getCodeForReportName("SMS");
            if (code != 1) {
                throw new ApplicationException("SMS parameter should be ON.");
            }
            if (vendorName.equalsIgnoreCase("digialaya")) {
                result = smsSender.postBulkSmsByDigialaya(digialayaAllXmlString, bulkSmsList, bulkMode, originalFile,
                        userName, orgnCode, todayDt);
            } else if (vendorName.equalsIgnoreCase("gupshup")) {
                result = smsSender.postBulkSmsByGupshup(gupShupFiles, bulkSmsList, bulkMode, originalFile, userName, orgnCode, todayDt);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public List<NpciFileDto> getBulkSmsData(String fromDt, String toDt) throws ApplicationException {
        List<NpciFileDto> resultList = new ArrayList<>();
        try {
            List datalIST = entityManager.createNativeQuery("select FILE_NO,FILE_GEN_DATE,FILE_NAME,FILE_GEN_BY  from cbs_npci_mapper_files "
                    + "where FILE_GEN_TYPE ='BLKSMS' AND FILE_GEN_DATE between '" + fromDt + "'  and '" + toDt + "' order by FILE_GEN_DATE").getResultList();
            if (datalIST.isEmpty()) {
                throw new ApplicationException("There is no data to download.");
            }
            for (int i = 0; i < datalIST.size(); i++) {
                Vector vec = (Vector) datalIST.get(i);
                NpciFileDto pojo = new NpciFileDto();
                pojo.setFileNo(new BigInteger(vec.get(0).toString()));
                pojo.setFileGenDt(vec.get(1).toString());
                String[] detailsArr = vec.get(2).toString().split(":");
                pojo.setOrignalFileName(detailsArr[0].trim());
                pojo.setPartname(detailsArr[1].trim());
                pojo.setFileGenBy(vec.get(3).toString());

                resultList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }
}
