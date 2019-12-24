
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.loan.CbsSchemeFeeOrChargesDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsSchemeFeeOrChargesDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemeFeeOrChargesDetailsDAO.class);

    public CbsSchemeFeeOrChargesDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemeFeeOrChargesDetailsDAO Initializing...");
    }

    public List<CbsSchemeFeeOrChargesDetails> getEntityBySchemeCode(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemeFeeOrChargesDetails.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List<CbsSchemeFeeOrChargesDetails> entity = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findAllEvent() - end - return value=" + entity);
            }
            return entity;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw new DAOException(e);
        }
    }
}
