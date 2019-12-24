/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import org.apache.log4j.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author Zeeshan Waris
 */
public  class  AdminRoleFormsDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(AdminRoleFormsDAO.class);

    /**
     * @param entityManager
     */
    public AdminRoleFormsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("AdminRoleFormsDAO Initializing...");
    }

}
