
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CBSTradeFinanceInformation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CBSTradeFinanceInformationDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSTradeFinanceInformationDAO.class);

    public CBSTradeFinanceInformationDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSTradeFinanceInformationDAO Initializing...");
    }

    public CBSTradeFinanceInformation getTDFinanceDetailsByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTDFinanceDetailsByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CBSTradeFinanceInformation.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            CBSTradeFinanceInformation obj = null;
            if (resulList.size() > 0) {
                obj = (CBSTradeFinanceInformation) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getTDFinanceDetailsByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getTDFinanceDetailsByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTDFinanceDetailsByCustId()");
            throw new DAOException(e);
        }
    }
}
