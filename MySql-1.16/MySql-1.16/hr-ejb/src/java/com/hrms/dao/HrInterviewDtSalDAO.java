/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrInterviewDtSal;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrInterviewDtSalDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrInterviewDtSalDAO.class);
    /**
     * @param entityManager
     */
    public HrInterviewDtSalDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrInterviewDtSalDAO Initializing...");
    }
  public String updateAppointmentLetter(HrInterviewDtSal entity) throws DAOException {
        String msg = null;
        try {
             Query query = entityManager.createQuery(NamedQueryConstant.UPDATE_APPOINTMENT_LETTER);
             query.setParameter("value1",entity.getHrInterviewDtSalPK().getCompCode());
             query.setParameter("value2",entity.getHrInterviewDtSalPK().getIntCode());
             query.setParameter("value3",entity.getHrInterviewDtSalPK().getAdvtCode());
             query.setParameter("value4",entity.getHrInterviewDtSalPK().getJobCode());
             query.setParameter("value5",entity.getHrInterviewDtSalPK().getCandSrno());
             query.setParameter("compOff", entity.getCompOff());
             query.setParameter("compNegoit", entity.getCompNegoit());
             query.setParameter("compExpect", entity.getCompExpect());
             query.executeUpdate();

            msg = "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return msg;
    }

}

