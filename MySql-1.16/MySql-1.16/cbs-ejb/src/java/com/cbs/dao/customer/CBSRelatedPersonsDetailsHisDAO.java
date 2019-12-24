
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

public class CBSRelatedPersonsDetailsHisDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSRelatedPersonsDetailsHisDAO.class);

    public CBSRelatedPersonsDetailsHisDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSRelatedPersonsDetailsHisDAO Initializing...");
    }
}

