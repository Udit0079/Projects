/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.to.HrLeavePostingTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrLeavePosting;
import com.hrms.entity.payroll.HrLeavePostingPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr BIsht
 */
public class HrLeavePostingDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrLeavePostingDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrLeavePostingDAO Initializing...");
    }

    /**
     * Get Hr leave posting for employee between the finanacial year dates
     * @param hrleavePostingPK
     * @return
     * @throws DAOException
     */
    public List<HrLeavePosting> findhrLeavePosting(HrLeavePostingPK hrleavePostingPK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findhrLeavePosting()");
        }
        try {
            List<HrLeavePosting> hrLeavePosting;
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_LEAVE_POSTING_FOR_EMP_CODE);
            q1.setParameter("compCode", hrleavePostingPK.getCompCode());
            q1.setParameter("empCode", hrleavePostingPK.getEmpCode());
            q1.setParameter("dateFrom", hrleavePostingPK.getDateFrom());
            q1.setParameter("dateTo", hrleavePostingPK.getDateTo());
            hrLeavePosting = q1.getResultList();
            return hrLeavePosting;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findhrLeavePosting()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findhrLeavePosting()");
            throw new DAOException(e);
        }



    }

    public List viewofdataLeavePosting(int compCode, String fromDate, String toDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("viewofdataLeavePosting()");
        }
        try {
            List hrLeavePostings;
            Query q1 = entityManager.createQuery(NamedQueryConstant.VIEW_DATA_LEAVE_ALLOCATION);            
            q1.setParameter("compCode", compCode);
            q1.setParameter("dateFrom", fromDate);
            q1.setParameter("dateTo", toDate);
            hrLeavePostings = q1.getResultList();            
            //return hrLeavePostings;            
            return hrLeavePostings;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
    }

    public List<HrLeavePosting> checkDupleacteData(HrLeavePostingTO hr) {
        try {
            Query q = entityManager.createNamedQuery(NamedQueryConstant.FIND_HRLEAVEPOSTING_ALL_DATA);
            q.setParameter("compCode", hr.getHrLeavePostingPK().getCompCode());
            q.setParameter("leaveCode", hr.getHrLeavePostingPK().getLeaveCode());
            q.setParameter("empCode", hr.getHrLeavePostingPK().getEmpCode());
            q.setParameter("dateFrom", hr.getHrLeavePostingPK().getDateFrom());
            q.setParameter("dateTo", hr.getHrLeavePostingPK().getDateTo());
            List<HrLeavePosting> result = q.getResultList();            
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
