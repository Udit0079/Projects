
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.master.CbsGlSubHeadSchemeDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsGlSubHeadSchemeDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsGlSubHeadSchemeDetailsDAO.class);

    public CbsGlSubHeadSchemeDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsGlSubHeadSchemeDetailsDAO Initializing...");
    }

    public List<CbsGlSubHeadSchemeDetails> getEntityBySchemeCode(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsGlSubHeadSchemeDetails.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List<CbsGlSubHeadSchemeDetails> entity = createNamedQuery.getResultList();
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
    
    public List<Object[]> getGSHSDDetails(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getGSHSDDetails()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_GL_SUB_HEAD_SCHEME_DETAILS);
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List<Object[]> entityList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findAllEvent() - end - return value=" + entityList);
            }
            return entityList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw new DAOException(e);
        }
    }
}
