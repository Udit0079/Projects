/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.master.CbsSchemePopUpForms;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class CbsSchemePopUpFormsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemePopUpFormsDAO.class);

    public CbsSchemePopUpFormsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemePopUpFormsDAO Initializing...");
    }

    public List<CbsSchemePopUpForms> getForms(String schemeType) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getForms()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_FORMS_FOR_SCHEME_TYPE_SCHEME_MASTER);
            createNamedQuery.setParameter("schemeType", schemeType);
            List<CbsSchemePopUpForms> cbsSchemePopUpFormsList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getForms() - end - return value=" + cbsSchemePopUpFormsList);
            }
            return cbsSchemePopUpFormsList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getForms()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getForms()");
            throw new DAOException(e);
        }
    }

    public CbsSchemePopUpForms findByPK(String formId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByPK()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemePopUpForms.findByFormId");
            createNamedQuery.setParameter("formId", formId);
            CbsSchemePopUpForms cbsSchemePopUpForms = (CbsSchemePopUpForms) createNamedQuery.getSingleResult();

            if (logger.isDebugEnabled()) {
                logger.debug("findAllEvent() - end - return value=" + cbsSchemePopUpForms);
            }
            return cbsSchemePopUpForms;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw new DAOException(e);
        }
    }
}
