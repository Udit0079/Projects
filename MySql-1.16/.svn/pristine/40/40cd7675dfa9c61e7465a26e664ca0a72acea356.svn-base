
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CbsRelatedPersonsDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CBSRelatedPersonsDetailsDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(CBSRelatedPersonsDetailsDAO.class);

    public CBSRelatedPersonsDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSRelatedPersonsDetailsDAO Initializing...");
    }

    public List<CbsRelatedPersonsDetails> getCustRelatedPersonDetailsByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCustMisDetailsVyCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsRelatedPersonsDetails.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List<CbsRelatedPersonsDetails> objectList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getCustMinorDetailsByCustId() - end - return value=" + objectList);
            }
            return objectList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getCustMinorDetailsByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCustMinorDetailsByCustId()");
            throw new DAOException(e);
        }
    }

    public int getMaxTxnIdByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getMaxTxnIdByCustId()");
        }
        try {
            //Query createNamedQuery = entityManager.createNamedQuery("CBSCustomerMasterDetail.getMaxCustID");
            Query createNamedQuery = entityManager.createNamedQuery("CBSRelatedPersonsDetails.findMaxSnoByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            int obj = 0;
            if (resulList.size() > 0) {
                obj = resulList.get(0) != null ? Integer.parseInt(resulList.get(0).toString()) : 0;
            } else {
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
