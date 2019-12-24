
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CbsKycDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsKycDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsKycDetailsDAO.class);

    public CbsKycDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsKycDetailsDAO Initializing...");
    }
     public CbsKycDetails getKycDetailsByCustId(String custId)throws DAOException
    {
          if (logger.isDebugEnabled()) {
            logger.debug("getKycDetailsByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsKycDetails.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
             List resulList = createNamedQuery.getResultList();
            CbsKycDetails obj = null;
            if (resulList.size() > 0) {
                obj = (CbsKycDetails) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getKycDetailsByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getKycDetailsByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getKycDetailsByCustId()");
            throw new DAOException(e);
        }
    }
}

