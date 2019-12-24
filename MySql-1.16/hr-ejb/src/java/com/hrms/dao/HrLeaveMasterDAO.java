/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrLeaveMaster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrLeaveMasterDAO extends GenericDAO {

   private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrLeaveMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrLeaveMasterDAO Initializing...");
    }

    /**
     *
     * @param compCode
     * @return
     * @throws DAOException
     */
    public List<HrLeaveMaster> findEntityByCompCode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEntityByCompCode()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_HR_LEAVE_MASTER_ENTITY_BY_COMPCODE);
            query.setParameter("compCode", compCode);
            List<HrLeaveMaster> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEntityByCompCode() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEntityByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityByCompCode()");
            throw new DAOException(e);
        }
    }

     public List<HrLeaveMaster> findSelectedLeaveCodeList(int compCode, String fromDate, String toDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findSelectedLeaveCodeList()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_HR_LEAVE_MASTER_ENTITY_BY_DATE_FROM_AND_DATE_TO);
            query.setParameter("compCode", compCode);
            query.setParameter("dateFrom", fromDate);
            query.setParameter("dateTo", toDate);
            List<HrLeaveMaster> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findSelectedLeaveCodeList() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findSelectedLeaveCodeList()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findSelectedLeaveCodeList()");
            throw new DAOException(e);
        }
    }

    public List<HrLeaveMaster> findNumOfLeaveDays(int compCode, String leaveCode, String fromDate, String toDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findNumOfLeaveDays()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_NUM_OF_LEAVE_DAYS_BY_LEAVE_CODE);
            query.setParameter("compCode", compCode);
            query.setParameter("leaveCode", leaveCode);
            query.setParameter("dateFrom", fromDate);
            query.setParameter("dateTo", toDate);
            List<HrLeaveMaster> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findNumOfLeaveDays() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findNumOfLeaveDays()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findNumOfLeaveDays()");
            throw new DAOException(e);
        }
    }
}
