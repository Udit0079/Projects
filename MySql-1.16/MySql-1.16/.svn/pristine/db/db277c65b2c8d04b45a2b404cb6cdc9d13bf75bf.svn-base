
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CBSBuyerSellerLimitDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CBSBuyerSellerLimitDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSBuyerSellerLimitDetailsDAO.class);

    public CBSBuyerSellerLimitDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSBuyerSellerLimitDetailsDAO Initializing...");
    }
    public CBSBuyerSellerLimitDetails getBsLimitByCustId(String custId)throws DAOException
    {
          if (logger.isDebugEnabled()) {
            logger.debug("getBsLimitByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CBSBuyerSellerLimitDetails.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            CBSBuyerSellerLimitDetails obj = null;
            if (resulList.size() > 0) {
                obj = (CBSBuyerSellerLimitDetails) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getBsLimitByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getBsLimitByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getBsLimitByCustId()");
            throw new DAOException(e);
        }
    }
}

