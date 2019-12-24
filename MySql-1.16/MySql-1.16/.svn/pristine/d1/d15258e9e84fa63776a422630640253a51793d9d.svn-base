/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrPayrollCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class PayrollCalendarDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(PayrollCalendarDAO.class);

    /**
     * @param entityManager
     */
    public PayrollCalendarDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("PayrollCalanderDAO Initializing...");
    }

    /**
     *
     * @param compCode
     * @param status
     * @return
     * @throws DAOException
     */
    public List<HrPayrollCalendar> findByStatusFlag(int compCode, String status) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByStatusFlag()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.PAYROLL_CALANDER_FIND_BY_STATUS_FLAG);
            query.setParameter("compCode", compCode);
            query.setParameter("statusFlag", status);
            List<HrPayrollCalendar> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByStatusFlag() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByStatusFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByStatusFlag()");
            throw new DAOException(e);
        }
    }

    /**
     * function to find the existing open payroll calendar
     * @param hrPayrollCalendar
     * @return
     * @throws DAOException
     */
    public List<HrPayrollCalendar> findSavedPayrollCalendar(HrPayrollCalendar hrPayrollCalendar) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findSavedPayrollCalendar()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.PAYROLL_CALENDAR_EXISTING_AND_STATUS_OPEN);
            query.setParameter("compCode", hrPayrollCalendar.getHrPayrollCalendarPK().getCompCode());
            query.setParameter("dateFrom", hrPayrollCalendar.getHrPayrollCalendarPK().getDateFrom());
            query.setParameter("dateTo", hrPayrollCalendar.getHrPayrollCalendarPK().getDateTo());
            query.setParameter("statusFlag", hrPayrollCalendar.getStatusFlag());
            List<HrPayrollCalendar> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByStatusFlag() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findSavedPayrollCalendar()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findSavedPayrollCalendar()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws DAOException
     */
    public List<HrPayrollCalendar> displayPayRollCalendarGrid(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("displayPayRollCalendarGrid()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.PAYROLL_CALENDAR_LIST_BY_COMPANY_CODE);
            q1.setParameter("compCode", compCode);
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method displayPayRollCalendarGrid()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method displayPayRollCalendarGrid()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws DAOException
     */
    public List<HrPayrollCalendar> getOpencalendarList(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getOpencalendarList()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.PAYROLL_CALANDER_FIND_BY_STATUS_FLAG);
            q1.setParameter("compCode", compCode);
            q1.setParameter("statusFlag", "OPEN");
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getOpencalendarList()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getOpencalendarList()");
            throw new DAOException(e);
        }
    }

    /********* himanshu ********/
    public List datacalander(int compCode) throws DAOException {
        List l = null;
        try {
            Query q = entityManager.createNamedQuery(NamedQueryConstant.PAYROLL_CALANDER_FIND_BY_STATUS_FLAG1);
            q.setParameter("compCode", compCode);
            q.setParameter("statusFlag", "OPEN");
            l = q.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getOpencalendarList()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getOpencalendarList()");
            throw new DAOException(e);
        }
        return l;
    }
    /**********************/
}
