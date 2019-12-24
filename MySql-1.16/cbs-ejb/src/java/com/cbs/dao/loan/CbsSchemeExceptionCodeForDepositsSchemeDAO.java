
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsSchemeExceptionCodeForDepositsSchemeDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemeExceptionCodeForDepositsSchemeDAO.class);

    public CbsSchemeExceptionCodeForDepositsSchemeDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemeExceptionCodeForDepositsSchemeDAO Initializing...");
    }
    
    public Object getDepositOverDueIntParameter(String schemeCode) throws DAOException{
        if (logger.isDebugEnabled()) {
            logger.debug("getDepositOverDueIntParameter()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DEPOSIT_OVERDUE_INT_PARAMETER);
            createNamedQuery.setParameter("schemeCode", schemeCode);
            
            Object obj = createNamedQuery.getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("getDepositOverDueIntParameter() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getDepositOverDueIntParameter()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDepositOverDueIntParameter()");
            throw new DAOException(e);
        }
    }
}

