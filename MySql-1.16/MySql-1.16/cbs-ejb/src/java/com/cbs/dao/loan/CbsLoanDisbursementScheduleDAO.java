
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

public class CbsLoanDisbursementScheduleDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsLoanDisbursementScheduleDAO.class);

    public CbsLoanDisbursementScheduleDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsLoanDisbursementScheduleDAO Initializing...");
    }
}

