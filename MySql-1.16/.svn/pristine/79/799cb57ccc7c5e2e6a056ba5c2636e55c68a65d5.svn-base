/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrAttendanceMaintenance;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class HrAttendanceMaintainDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrAttendanceMaintainDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrAttendanceMaintainDAO Initializing...");
    }

    /**
     * Get emloyee attendance maintenanace by company code , emloyee code and atten Date
     * @param compCode
     * @param empCode
     * @param attenDate
     * @return
     * @throws DAOException
     */
    public HrAttendanceMaintenance getByCompCodeEmpCodeAndAttenDate(int compCode, long empCode, Date attenDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getByCompCodeEmpCodeAndAttenYear()");
        }
        HrAttendanceMaintenance attendanceMaintain = new HrAttendanceMaintenance();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.ATTENDANCE_MAINTENANCE_BY_EMPCODE_COMPCODE_AND_ATTENDATE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            q1.setParameter("attenDate", attenDate);
            List<HrAttendanceMaintenance> attendanceList = q1.getResultList();
            for (HrAttendanceMaintenance hrAttendanceMaintenance : attendanceList) {
                attendanceMaintain = hrAttendanceMaintenance;
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getByCompCodeEmpCodeAndAttenYear()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getByCompCodeEmpCodeAndAttenYear()");
            throw new DAOException(e);
        }
        return attendanceMaintain;
    }

    /************************** himanshu **********/
    public boolean checkattend(int compCode, int empCode) throws DAOException {
        try {
            Query q = entityManager.createQuery(NamedQueryConstant.DATE_IN_FIND_BY_EMP_CODE);
            List l = q.getResultList();
            Iterator itr = l.iterator();
            Date d = (Date) itr.next();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String s = formatter.format(d);
            String s1 = formatter.format(Date.class.newInstance());
            if (s.equals(s1)) {
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
    }

    public boolean checktimeout(int compCode, int empCode) throws DAOException {
        try {
            Query q = entityManager.createQuery(NamedQueryConstant.TIME_OUT_FIND_BY_EMP_CODE);
            q.setParameter("compCode", compCode);
            q.setParameter("empCode", empCode);
            List l = q.getResultList();
            Iterator itr = l.iterator();
            Object s = itr.next();
            Date d = new Date();
            d.setDate(1);
            d.setMonth(0);
            d.setYear(0);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            if (formatter.format(d).equals(formatter.format(s))) {
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
    }

    public boolean updatetimout(int compCode, int empCode) {
        try {
            Query q = entityManager.createQuery(NamedQueryConstant.UPDATE_TIME_OUT_BY_EMP_CODE);
            q.setParameter("compCode", compCode);
            q.setParameter("empCode", empCode);
            int i = q.executeUpdate();
            if (i == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List<HrAttendanceMaintenance> viewdata(int compCode, int empCode, Date d) throws DAOException {
        Date d1 = new java.util.Date(), d2 = new java.util.Date();
        List<HrAttendanceMaintenance> l = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Calendar c = Calendar.getInstance();
            d1.setMonth(d.getMonth() - 1);
            d1.setDate(30);
            d1.setYear(d.getYear());
            d2.setMonth(d.getMonth());
            d2.setDate(31);
            d2.setYear(d.getYear());
            Query q = entityManager.createQuery(NamedQueryConstant.VIEW_DATA_BY_MONTH);
            q.setParameter("compCode", compCode);
            q.setParameter("empCode", empCode);
            q.setParameter("value1", d1);
            q.setParameter("value2", d2);
            l = q.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return l;
    }
    /********************************************/
}
