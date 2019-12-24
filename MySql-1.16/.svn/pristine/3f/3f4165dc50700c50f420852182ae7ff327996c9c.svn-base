
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.loan.CbsSchemeGeneralSchemeParameterMaster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsSchemeGeneralSchemeParameterMasterDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemeGeneralSchemeParameterMasterDAO.class);

    public CbsSchemeGeneralSchemeParameterMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemeGeneralSchemeParameterMasterDAO Initializing...");
    }
    
    public CbsSchemeGeneralSchemeParameterMaster getEntityBySchemeCode(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemeGeneralSchemeParameterMaster.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List resulList = createNamedQuery.getResultList();
            CbsSchemeGeneralSchemeParameterMaster obj = null;
            if (resulList.size() > 0) {
                obj = (CbsSchemeGeneralSchemeParameterMaster) resulList.get(0);
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
}

