
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CbsKycLoan;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsKycLoanDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsKycLoanDAO.class);

    public CbsKycLoanDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsKycLoanDAO Initializing...");
    }
      public List<CbsKycLoan> getKycLoanByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getKycLoanByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsKycLoan.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List<CbsKycLoan> objectList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getKycLoanByCustId() - end - return value=" + objectList);
            }
            return objectList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getKycLoanByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getKycLoanByCustId()");
            throw new DAOException(e);
        }
    }
      public int getMaxTxnIdByCustId(String custId)throws DAOException
    {
         if (logger.isDebugEnabled()) {
            logger.debug("getMaxTxnIdByCustId()");
        }
        try {
            //Query createNamedQuery = entityManager.createNamedQuery("CBSCustomerMasterDetail.getMaxCustID");
            Query createNamedQuery=entityManager.createNamedQuery("CbsKycLoan.getMaxTxnIdByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            int obj = 0;
            if (resulList.size() > 0) {
                obj = resulList.get(0)!=null?Integer.parseInt(resulList.get(0).toString()):0;
            }
            else
            {
                return 0;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getMaxTxnIdByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getMaxTxnIdByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getMaxTxnIdByCustId()");
            throw new DAOException(e);
        }
    }  
}

