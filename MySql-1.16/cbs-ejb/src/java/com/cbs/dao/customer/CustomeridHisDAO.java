
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

public class CustomeridHisDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CustomeridHisDAO.class);

    public CustomeridHisDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CustomeridHisDAO Initializing...");
    }
}

