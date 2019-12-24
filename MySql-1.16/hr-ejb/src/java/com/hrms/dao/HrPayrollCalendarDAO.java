/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author stellar
 */
public class HrPayrollCalendarDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrPayrollCalendarDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("LeaveMasterDAO Initializing...");
    }
}

