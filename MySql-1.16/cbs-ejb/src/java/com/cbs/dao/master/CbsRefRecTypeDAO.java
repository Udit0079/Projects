
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.master.CbsRefRecType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsRefRecTypeDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsRefRecTypeDAO.class);

    public CbsRefRecTypeDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsRefRecTypeDAO Initializing...");
    }

    public List<CbsRefRecType> getCurrencyCode(String refRecNo) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCurrencyCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsRefRecType.findByRefRecNo");
            createNamedQuery.setParameter("refRecNo", refRecNo);
            List<CbsRefRecType> cbsRefRecList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getCurrencyCode() - end - return value=" + cbsRefRecList);
            }
            return cbsRefRecList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getCurrencyCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCurrencyCode()");
            throw new DAOException(e);
        }
    }
}
