
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.master.CbsExceptionMaster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsExceptionMasterDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsExceptionMasterDAO.class);

    public CbsExceptionMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsExceptionMasterDAO Initializing...");
    }

    public List<CbsExceptionMaster> geAllExceptionCode() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("geAllExceptionCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsExceptionMaster.findAll");
            List<CbsExceptionMaster> cbsExceptionMasterList = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("geAllExceptionCode() - end - return value=" + cbsExceptionMasterList);
            }
            return cbsExceptionMasterList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method geAllExceptionCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method geAllExceptionCode()");
            throw new DAOException(e);
        }
    }

    public CbsExceptionMaster findByPK(String exceptionCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByPK()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsExceptionMaster.findByExceptionCode");
            createNamedQuery.setParameter("exceptionCode", exceptionCode);
            CbsExceptionMaster cbsExceptionMaster = (CbsExceptionMaster) createNamedQuery.getSingleResult();

            if (logger.isDebugEnabled()) {
                logger.debug("findByPK() - end - return value=" + cbsExceptionMaster);
            }
            return cbsExceptionMaster;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByPK()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByPK()");
            throw new DAOException(e);
        }
    }
}
