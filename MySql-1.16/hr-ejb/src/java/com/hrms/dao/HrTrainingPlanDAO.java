/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrTrainingPlanDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrTrainingPlanDAO.class);
    /**
     * @param entityManager
     */
    public HrTrainingPlanDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrTrainingPlanDAO Initializing...");
    }

}
