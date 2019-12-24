/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.entity.personnel.HrExitInterviewHd;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class HrExitInterviewHdDAO extends GenericDAO{
  private static final Logger logger = Logger.getLogger(AdminRoleFormsDAO.class);

    /**
     * @param entityManager
     */
    public HrExitInterviewHdDAO (EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrExitInterviewDtDAO Initializing...");
    }
public void checkData(HrExitInterviewHd hrExitInterviewHd){
    HrExitInterviewHd entity=entityManager.find(HrExitInterviewHd.class,hrExitInterviewHd.getHrExitInterviewHdPK());
    
}

}
