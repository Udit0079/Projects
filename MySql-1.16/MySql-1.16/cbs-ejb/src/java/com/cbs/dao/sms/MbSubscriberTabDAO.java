/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.constant.CbsConstant;
import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.dto.sms.SmsPostingTo;
import com.cbs.entity.sms.MbSubscriberTab;
import com.cbs.exception.ApplicationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class MbSubscriberTabDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(MbSubscriberTabDAO.class);

    public MbSubscriberTabDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("MbSubscriberTabDAO Initializing...");
    }

    public MbSubscriberTab getEntityByAcno(String acno) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityByAcno()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbSubscriberTab.findByAcno");
            createNamedQuery.setParameter("acno", acno);
            List resulList = createNamedQuery.getResultList();
            MbSubscriberTab obj = null;
            if (resulList.size() > 0) {
                obj = (MbSubscriberTab) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findEntityEvent() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }

    public List getAllMobileNo(String orgnBrCode) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllMobileNo()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_ALL_MB_NO);
            createNamedQuery.setParameter("status", "1");
            createNamedQuery.setParameter("auth", "Y");
            createNamedQuery.setParameter("authStatus", "V");
            createNamedQuery.setParameter("orgnBrCode", orgnBrCode);
            List resulList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEntityEvent() - end - return value=" + resulList);
            }
            return resulList;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }

    public List<MbSubscriberTab> getAllAccountByMobile(String mobileNo) throws ApplicationException {
        List<MbSubscriberTab> resultList = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.ALL_SUBSCRIBER_BY_MOBILE);
            createNamedQuery.setParameter("mobileNo", "+" + mobileNo);
            createNamedQuery.setParameter("status", "1");
            createNamedQuery.setParameter("auth", "Y");
            createNamedQuery.setParameter("authStatus", "V");
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException("Problem In getAllAccountByMobile() Method " + ex.getMessage());
        }
        return resultList;
    }

    public MbSubscriberTab getPinByMobile(String mobileNo) throws ApplicationException {
        MbSubscriberTab entity = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbSubscriberTab.findByMobileNo");
            createNamedQuery.setParameter("mobileNo", "+" + mobileNo);
            entity = (MbSubscriberTab) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getAllAccountByMobile() Method " + ex.getMessage());
        }
        return entity;
    }

    public MbSubscriberTab getPinByMobileAndAccountCode(String mobileNo, String accountCode) throws ApplicationException {
        MbSubscriberTab entity = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.SUBSCRIBER_BY_MOBILE_AND_ACCOUNT_CODE);
            createNamedQuery.setParameter("mobileNo", "+" + mobileNo);
            createNamedQuery.setParameter("accode", accountCode);
            entity = (MbSubscriberTab) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getAllAccountByMobile() Method " + ex.getMessage());
        }
        return entity;
    }

    public List getAllStaffMobileNo(String orgnBrCode) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllStaffMobileNo()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_ALL_STAFF_MOBILE_NO);
            createNamedQuery.setParameter("type", "STAFF");
            createNamedQuery.setParameter("status", "1");
            createNamedQuery.setParameter("auth", "Y");
            createNamedQuery.setParameter("authStatus", "V");
            createNamedQuery.setParameter("orgnBrCode", orgnBrCode);
            List resulList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEntityEvent() - end - return value=" + resulList);
            }
            return resulList;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }

    public Object getSubscriberByMobilePinAndAccType(String mobileNo, String accountCode, String pin) throws ApplicationException {
        Object obj = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.SUBSCRIBER_ACNO_BY_MOBILE_ACCOUNT_CODE_AND_PIN);
            createNamedQuery.setParameter("mobileNo", "+" + mobileNo);
            createNamedQuery.setParameter("accode", accountCode);
            createNamedQuery.setParameter("pin", pin);
            obj = createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getAllAccountByMobile() Method " + ex.getMessage());
        }
        return obj;
    }

//    public List<String[]> getAllAccountOfBarnch(String frDt, String toDt, String brCode,
//            Integer staffInclude) throws ApplicationException {
//        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
//        List<String[]> list = new CopyOnWriteArrayList<String[]>();
//        try {
//            List<String> tempList = new ArrayList<String>();
//            if (staffInclude == 0) {
//                Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.ALL_SMS_REGISTERED_ACTIVE_ACCOUNT_WITHOUT_STAFF);
//                createNamedQuery.setParameter("curBrCode", brCode);
//                createNamedQuery.setParameter("toDt", dmy.parse(toDt));
//                createNamedQuery.setParameter("acnoType", "STAFF");
//                tempList = createNamedQuery.getResultList();
//            } else if (staffInclude == 1) {
//                Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.ALL_SMS_REGISTERED_ACTIVE_ACCOUNT);
//                createNamedQuery.setParameter("curBrCode", brCode);
//                createNamedQuery.setParameter("toDt", dmy.parse(toDt));
//                tempList = createNamedQuery.getResultList();
//            }
//
//            if (!tempList.isEmpty()) {
//                for (String v : tempList) {
//                    String[] arr = new String[2];
//                    arr[0] = v;
//                    arr[1] = frDt;
//                    list.add(arr);
//                }
//            }
//
//            System.out.println("Size of final list:-->" + list.size());
//            if (!list.isEmpty()) {
//                for (String[] arr : list) {
//                    Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CHECK_ALREADY_POSTED_ACCOUNT);
//                    createNamedQuery.setParameter("acno", arr[0]);
//                    List resultList = createNamedQuery.getResultList();
//                    if (!resultList.isEmpty() && resultList.get(0) != null) {
//                        Date maxToDt = (Date) resultList.get(0);
//                        if (dmy.parse(frDt).compareTo(maxToDt) <= 0) {
//                            list.remove(arr);
//                        }
//                    } else {
//                        createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_CREATION_DATE_BY_ACNO);
//                        List subList = createNamedQuery.setParameter("acno", arr[0]).getResultList();
//                        String regDt = dmy.format((Date) subList.get(0));
//                        arr[1] = frDt;
//                        if (dmy.parse(regDt).compareTo(dmy.parse(frDt)) > 0) {
//                            arr[1] = regDt;
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            System.out.println("Problem In getAllAccountOfBarnch() Method " + ex.getMessage());
//        }
//        return list;
//    }
    public List getAllAccountByAuthAndAuthStatus(String auth, String authStatus) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_ACCOUNT_TO_DELETE);
            createNamedQuery.setParameter("auth", auth);
            createNamedQuery.setParameter("authStatus", authStatus);
            return createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAllAccountToVerify(String orgnBrCode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_ACCOUNT_TO_VERIFY);
            createNamedQuery.setParameter("auth", "N");
            createNamedQuery.setParameter("orgnBrCode", orgnBrCode);
            return createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public MbSubscriberTab checkAccountByAuthAndAuthStatus(String auth, String authStatus, String acno) throws ApplicationException {
        MbSubscriberTab entity = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CHECK_ACCOUNT_BY_AUTH_AND_AUTHSTATUS);
            createNamedQuery.setParameter("auth", auth);
            createNamedQuery.setParameter("authStatus", authStatus);
            createNamedQuery.setParameter("acno", acno);
            if (!createNamedQuery.getResultList().isEmpty()) {
                entity = (MbSubscriberTab) createNamedQuery.getResultList().get(0);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return entity;
    }

    public MbSubscriberTab checkRegisteredAccount(String auth, String authStatus, String acno, String status) throws ApplicationException {
        MbSubscriberTab entity = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CHECK_ACCOUNT_BY_AUTH_AUTHSTATUS_AND_STATUS);
            createNamedQuery.setParameter("auth", auth);
            createNamedQuery.setParameter("authStatus", authStatus);
            createNamedQuery.setParameter("acno", acno);
            createNamedQuery.setParameter("status", status);
            if (!createNamedQuery.getResultList().isEmpty()) {
                entity = (MbSubscriberTab) createNamedQuery.getResultList().get(0);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return entity;
    }
}
