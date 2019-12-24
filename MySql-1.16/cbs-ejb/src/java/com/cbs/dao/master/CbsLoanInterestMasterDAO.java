
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

public class CbsLoanInterestMasterDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsLoanInterestMasterDAO.class);

    public CbsLoanInterestMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsLoanInterestMasterDAO Initializing...");
    }
}

