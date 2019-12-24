
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.loan.CbsSchemeLoanSchemeDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsSchemeLoanSchemeDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemeLoanSchemeDetailsDAO.class);

    public CbsSchemeLoanSchemeDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemeLoanSchemeDetailsDAO Initializing...");
    }
    
    public CbsSchemeLoanSchemeDetails getEntityBySchemeCode(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemeLoanSchemeDetails.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List resulList = createNamedQuery.getResultList();
            CbsSchemeLoanSchemeDetails obj = null;
            if (resulList.size() > 0) {
                obj = (CbsSchemeLoanSchemeDetails) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findAllEvent() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method findAllEvent()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method findAllEvent()");
            throw new DAOException(e);
        }
    }
    
    public Object getSchTypeAndCurCodeBySchCode(String schemeCode) throws DAOException{
        if (logger.isDebugEnabled()) {
            logger.debug("getSchTypeAndCurCodeBySchCode()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.SCHEME_TYPE_AND_CURRENCY_CODE_BY_SCHEME_CODE);
            createNamedQuery.setParameter("schemeCode", schemeCode);
            
            Object loanSchemeTypeAndCurrency = createNamedQuery.getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("getSchTypeAndCurCodeBySchCode() - end - return value=" + loanSchemeTypeAndCurrency);
            }
            return loanSchemeTypeAndCurrency;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSchTypeAndCurCodeBySchCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSchTypeAndCurCodeBySchCode()");
            throw new DAOException(e);
        }
    }
}

