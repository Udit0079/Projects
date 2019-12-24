
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

public class LoanCodebookDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(LoanCodebookDAO.class);

    public LoanCodebookDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("LoanCodebookDAO Initializing...");
    }
}

