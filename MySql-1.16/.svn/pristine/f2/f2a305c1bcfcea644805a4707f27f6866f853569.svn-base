/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.sms.MbPushMsgTab;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class MbPushMsgTabDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(MbSubscriberTabDAO.class);

    public MbPushMsgTabDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("MbSubscriberTabDAO Initializing...");
    }

    public MbPushMsgTab getEntityByAcno(String acno) throws ApplicationException {
        System.out.println("in dao");
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbPushMsgTab.findByAcno");
            createNamedQuery.setParameter("acno", acno);
            List resulList = createNamedQuery.getResultList();
            MbPushMsgTab obj = null;
            if (resulList.size() > 0) {
                obj = (MbPushMsgTab) resulList.get(0);
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

    public List getDataforChargePosting(String messageType, String acno, String orgnBrCode) throws ApplicationException {
        List list = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("getDataforChargePosting()");
        }
        try {
            if (acno.equalsIgnoreCase("ALL")) {
                Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DATA_FOR_CHARGE);
                createNamedQuery.setParameter("type", messageType);
                createNamedQuery.setParameter("orgnBrCode", orgnBrCode);
                list = createNamedQuery.getResultList();
            } else {
                Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DATA_FOR_CHARGE_BY_ACNO);
                createNamedQuery.setParameter("acno", acno);
                list = createNamedQuery.setParameter("type", messageType).getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return list;
    }

    public List<MbPushMsgTab> getEntityToPush(int messageStatus, Date curDt, Date nextDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.ENTITY_TO_PUSH_ON_CURRENT_DATE);
            createNamedQuery.setParameter("messageStatus", messageStatus);
            createNamedQuery.setParameter("curDt", curDt);
            createNamedQuery.setParameter("nextDt", nextDt);
            List resultList = createNamedQuery.getResultList();
            if (resultList != null && !resultList.isEmpty()) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_ALL_PUSHED_ENTITY);
                createNamedQuery.setParameter("messageStatus", messageStatus);
                createNamedQuery.setParameter("curDt", curDt);
                createNamedQuery.setParameter("nextDt", nextDt);
                int n = createNamedQuery.executeUpdate();
                if (n <= 0) {
                    resultList = new ArrayList<MbPushMsgTab>();
                    return resultList;
                }
            }
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void updateMbPushMsgTab(MbPushMsgTab entity) throws ApplicationException {
        try {
            update(entity);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<MbPushMsgTab> getAllSendedTxnId(String acno, String type) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityToPush()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_SENDED_TXN_ID);
            createNamedQuery.setParameter("acno", acno);
            createNamedQuery.setParameter("type", type);
            List resultList = createNamedQuery.getResultList();
            return resultList;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }
    
    public List<MbPushMsgTab> getAllFixedSendedSms(String acno) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityToPush()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_FIXED_SENDED_SMS);
            createNamedQuery.setParameter("acno", acno);
            List resultList = createNamedQuery.getResultList();
            return resultList;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }

    public List<String> getDistinctMessageType(Date frmdt, Date todt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_DISTINCT_MESSAGE_TYPE);
            createNamedQuery.setParameter("fromdt", frmdt);
            createNamedQuery.setParameter("todt", todt);
            List<String> resultList = createNamedQuery.getResultList();
            return resultList;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }

    public Integer getTotalSendMessage(String messageType, Date frmdt, Date todt) throws ApplicationException {
        Integer noOfMsg = 0;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.TOTAL_SEND_MESSAGE);
            createNamedQuery.setParameter("messageType", messageType);
            createNamedQuery.setParameter("fromdt", frmdt);
            createNamedQuery.setParameter("todt", todt);
            List entityList = createNamedQuery.getResultList();
            if (!entityList.isEmpty()) {
                noOfMsg = Integer.parseInt(entityList.get(0).toString());
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
        return noOfMsg;
    }

    public MbPushMsgTab getEntityByMessageId(String messageId) throws ApplicationException {
        MbPushMsgTab obj = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbPushMsgTab.findByMessageId");
            createNamedQuery.setParameter("messageId", messageId);
            try {
                obj = (MbPushMsgTab) createNamedQuery.getSingleResult();
            } catch (Exception ex) {
                System.out.println("No Result Found For Message ID-->" + messageId);
            }
            return obj;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
