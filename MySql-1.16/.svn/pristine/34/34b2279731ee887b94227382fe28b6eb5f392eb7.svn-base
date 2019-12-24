
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.loan.CbsSchemeCurrencyDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsSchemeCurrencyDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemeCurrencyDetailsDAO.class);

    public CbsSchemeCurrencyDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemeCurrencyDetailsDAO Initializing...");
    }

    public CbsSchemeCurrencyDetails getEntityBySchemeCode(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemeCurrencyDetails.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List resulList = createNamedQuery.getResultList();
            CbsSchemeCurrencyDetails obj = null;
            if (resulList.size() > 0) {
                obj = (CbsSchemeCurrencyDetails) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findAllEvent() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw new DAOException(e);
        }
    }
}
