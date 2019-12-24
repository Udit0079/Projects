
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CBSCustCurrencyInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CBSCustCurrencyInfoDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSCustCurrencyInfoDAO.class);

    public CBSCustCurrencyInfoDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSCustCurrencyInfoDAO Initializing...");
    }

    public List<CBSCustCurrencyInfo> getCurrencyDetailsByCustomerId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCurrencyDetailsByCustomerId()");
        }
        List<CBSCustCurrencyInfo> resulList=new ArrayList<CBSCustCurrencyInfo>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CBSCustCurrencyInfo.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            resulList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getCurrencyDetailsByCustomerId() - end - return value=" + resulList);
            }
            return resulList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw new DAOException(e);
        }

    }
   
}
