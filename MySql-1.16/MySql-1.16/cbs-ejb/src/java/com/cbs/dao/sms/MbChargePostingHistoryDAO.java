/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
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
public class MbChargePostingHistoryDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(MbChargePostingHistoryDAO.class);

    public MbChargePostingHistoryDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("MbChargePostingHistoryDAO Initializing...");
    }

    public long getMaxTxnId() throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getMaxTxnId()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TXN_ID_MB_CHARGE_POSTING_HISTORY);
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

//    public Date getMaxToDateForAllPosting(String brncode) throws ApplicationException {
//        Date date = null;
//        try {
//            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TO_DATE_BY_BRANCH);
//            createNamedQuery.setParameter("brncode", brncode);
//            List resultList = createNamedQuery.getResultList();
//            if (!resultList.isEmpty() && resultList.get(0) != null) {
//                date = (Date) resultList.get(0);
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(date);
//                cal.add(Calendar.DATE, 1);
//                date = cal.getTime();
//            } else {
//                Query query = entityManager.createQuery(NamedQueryConstant.FIND_MIN_CREATED_DATE_FOR_BRANCH);
//                List list = query.setParameter("brncode", brncode).getResultList();
//                if (!list.isEmpty()) {
//                    date = (Date) list.get(0);
//                } else {
//                    Calendar cal = Calendar.getInstance();
//                    date = cal.getTime();
//                }
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return date;
//    }
}
