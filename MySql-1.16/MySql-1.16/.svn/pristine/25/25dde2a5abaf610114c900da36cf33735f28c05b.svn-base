
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.loan.CbsLoanInterestCodeMaster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsLoanInterestCodeMasterDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsLoanInterestCodeMasterDAO.class);

    public CbsLoanInterestCodeMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsLoanInterestCodeMasterDAO Initializing...");
    }

    public List<CbsLoanInterestCodeMaster> getDataTodRef() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getDataTodRef()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsLoanInterestCodeMaster.findAll");
            List<CbsLoanInterestCodeMaster> cbsLoanInterestCodeMasters = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getDataTodRef() - end - return value=" + cbsLoanInterestCodeMasters);
            }
            return cbsLoanInterestCodeMasters;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getDataTodRef()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDataTodRef()");
            throw new DAOException(e);
        }
    }
}
