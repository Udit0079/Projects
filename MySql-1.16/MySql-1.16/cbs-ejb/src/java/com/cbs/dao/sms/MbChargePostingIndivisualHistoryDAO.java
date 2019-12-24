/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.sms.MbChargePostingIndivisualHistory;
import com.cbs.exception.ApplicationException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Ankit Verma
 */
public class MbChargePostingIndivisualHistoryDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(MbChargePostingIndivisualHistoryDAO.class);

    public MbChargePostingIndivisualHistoryDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("MbChargePostingIndivisualHistoryDAO Initializing...");
    }

//    public Date getMaxToDateForChargePostingByAcno(String acno, String messageType) throws ApplicationException {
//        Date date = null;
//        try {
//            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TO_DATE_BY_ACNO);
//            createNamedQuery.setParameter("acno", acno);
//            createNamedQuery.setParameter("messageType", messageType);
//            List resultList = createNamedQuery.getResultList();
//            if (!resultList.isEmpty() && resultList.get(0) != null) {
//                date = (Date) resultList.get(0);
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(date);
//                cal.add(Calendar.DATE, 1);
//                date = cal.getTime();
//            } else {
//                Query query = entityManager.createQuery(NamedQueryConstant.FIND_CREATION_DATE_BY_ACNO);
//                List list = query.setParameter("acno", acno).getResultList();
//                if (!list.isEmpty()) {
//                    date = (Date) list.get(0);
//                }
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return date;
//    }

    public Date getMaxToDateOfSmsPosting(String acno) throws ApplicationException {
        Date date = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TO_DATE_BY_ACNO_MESSAGE_TYPE);
            createNamedQuery.setParameter("acno", acno);
            List resultList = createNamedQuery.getResultList();
            if (!resultList.isEmpty() && resultList.get(0) != null) {
                date = (Date) resultList.get(0);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, 1);
                date = cal.getTime();
            } else {
                Query query = entityManager.createQuery(NamedQueryConstant.FIND_CREATION_DATE_BY_ACNO);
                List list = query.setParameter("acno", acno).getResultList();
                if (!list.isEmpty()) {
                    date = (Date) list.get(0);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return date;
    }

    public long getMaxTxnId() throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getMaxTxnId()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TXN_ID_MB_CHARGE_POSTING_INDIVIDUAL_HISTORY);
            Object result = (Object) createNamedQuery.getSingleResult();
            long obj = 0;
            if (result != null) {
                obj = Long.parseLong(result.toString());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getMaxTxnId() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxTxnId()");
            throw new ApplicationException(e);
        }
    }

    public MbChargePostingIndivisualHistory checkAlreadyPostedAccount() throws ApplicationException {
        MbChargePostingIndivisualHistory obj = new MbChargePostingIndivisualHistory();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CHECK_ALREADY_POSTED_ACCOUNT);
            createNamedQuery.setParameter(null, obj);
            createNamedQuery.setParameter(null, obj);
            obj = (MbChargePostingIndivisualHistory) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return obj;
    }
}
