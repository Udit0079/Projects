
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.master.BranchMaster;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class BranchMasterDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(BranchMasterDAO.class);

    public BranchMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("BranchMasterDAO Initializing...");
    }

    public BranchMaster getEntityByBrnCode(Integer brncode) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityByBrnCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("BranchMaster.findByBrnCode");
            createNamedQuery.setParameter("brnCode", brncode);
            BranchMaster obj = (BranchMaster) createNamedQuery.getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("findEntityEvent() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }

    public BranchMaster getEntityByAlphacode(String alphacode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("BranchMaster.findByAlphaCode");
            createNamedQuery.setParameter("alphaCode", alphacode);
            BranchMaster obj = (BranchMaster) createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
