
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.loan.CbsSchemeTodExceptionDetailsCurrencyWise;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsSchemeTodExceptionDetailsCurrencyWiseDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemeTodExceptionDetailsCurrencyWiseDAO.class);

    public CbsSchemeTodExceptionDetailsCurrencyWiseDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemeTodExceptionDetailsCurrencyWiseDAO Initializing...");
    }

    public CbsSchemeTodExceptionDetailsCurrencyWise getEntityBySchemeCode(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemeTodExceptionDetailsCurrencyWise.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List resulList = createNamedQuery.getResultList();
            CbsSchemeTodExceptionDetailsCurrencyWise obj = null;
            if (resulList.size() > 0) {
                obj = (CbsSchemeTodExceptionDetailsCurrencyWise) resulList.get(0);
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

    public List<Object[]> getDataGlTableAccToSchemeTodDetails(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getDataGlTableAccToSchemeTodDetails()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DATA_GL_TABLE_ACC_TO_SCHEME_TOD_DETAILS);
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List<Object[]> entityList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findAllEvent() - end - return value=" + entityList);
            }
            return entityList;
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

    public Object getTodSerialNo() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTodSerialNo()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.TOD_SERIAL_NUMBER);

            Object bankAdd = createNamedQuery.getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("getTodSerialNo() - end - return value=" + bankAdd);
            }
            return bankAdd;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getTodSerialNo()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getTodSerialNo()");
            throw new DAOException(e);
        }
    }
}
