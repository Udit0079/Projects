/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class MbSubscriberTabHisDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(MbSubscriberTabDAO.class);

    public MbSubscriberTabHisDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("MbSubscriberTabHisDAO Initializing...");
    }

    public Long getMaxTxnId(String accountNo) throws ApplicationException {
        Long id = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_MAX_TXN_ID_MB_SUBSCRIBER_TAB_HIS_FOR_ACCOUNT);
            createNamedQuery.setParameter("acno", accountNo);
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                id = new Long(1);
            } else {
                id = new Long(Long.parseLong(obj.toString())) + new Long(1);
            }
        } catch (Exception ex) {
            System.out.println("Problem In getMaxTxnId() Method " + ex.getMessage());
        }
        return id;
    }
}
