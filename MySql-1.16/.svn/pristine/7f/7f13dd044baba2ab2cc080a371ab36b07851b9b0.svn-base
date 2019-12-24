
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.loan.CbsSchemeDocumentDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsSchemeDocumentDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemeDocumentDetailsDAO.class);

    public CbsSchemeDocumentDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemeDocumentDetailsDAO Initializing...");
    }
    
    public CbsSchemeDocumentDetails getEntityBySchemeCode(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemeDocumentDetails.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            CbsSchemeDocumentDetails entity = (CbsSchemeDocumentDetails) createNamedQuery.getSingleResult();
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
    
    public List<CbsSchemeDocumentDetails> getAllDataBySchemeCode(String schemeCode) throws DAOException{
        if (logger.isDebugEnabled()) {
            logger.debug("getAllDatabySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemeDocumentDetails.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List<CbsSchemeDocumentDetails> cbsSchemeDocumentDetails = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getAllDatabySchemeCode() - end - return value=" + cbsSchemeDocumentDetails);
            }
            return cbsSchemeDocumentDetails;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAllDatabySchemeCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAllDatabySchemeCode()");
            throw new DAOException(e);
        }
    }
}

