
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

public class CBSCustCurrencyInfoHisDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSCustCurrencyInfoHisDAO.class);

    public CBSCustCurrencyInfoHisDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSCustCurrencyInfoHisDAO Initializing...");
    }
}

