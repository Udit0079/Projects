/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrLeaveRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr BIsht
 */
public class HrLeaveRegisterDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrLeaveRegisterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrLeaveRegisterDAO Initializing...");
    }

    /**
     *
     * @param compCode
     * @param leaveCode
     * @param empCode
     * @param hrCalendatDateFrom
     * @param hrCalendatDateTo
     * @return
     * @throws DAOException
     */
    public List<HrLeaveRegister> findTotalLeaveDays(int compCode, String leaveCode, long empCode, String hrCalendatDateFrom, String hrCalendatDateTo) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findTotalLeaveDays()");
        }
        try {
            List<HrLeaveRegister> hrLeaveRegisterList;
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_TOTAL_LEAVE_DAYS);
            q1.setParameter("compCode", compCode);
            q1.setParameter("leaveCode", leaveCode);
            q1.setParameter("empCode", empCode);
            q1.setParameter("fromDate", hrCalendatDateFrom);
            q1.setParameter("toDate", hrCalendatDateTo);
            hrLeaveRegisterList = q1.getResultList();
            return hrLeaveRegisterList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findTotalLeaveDays()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findTotalLeaveDays()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param compCode
     * @param empCode
     * @param leaveFromDate
     * @param leaveToDate
     * @return
     * @throws DAOException
     */
    public List<HrLeaveRegister> byCompCodeAndEmpCode(int compCode, long empCode, String leaveRegCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("byCompCodeAndEmpCode()");
        }
        try {
            List<HrLeaveRegister> hrLeaveRegisterList;
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_BY_COMPCODE_AND_EMPCODE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            q1.setParameter("leaveRegCode", leaveRegCode);
            hrLeaveRegisterList = q1.getResultList();
            return hrLeaveRegisterList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method byCompCodeAndEmpCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method byCompCodeAndEmpCode()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws DAOException
     */
    public String findAllByCompCode(int compCode) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("findAllByCompCode()");
        }
        try {
            List list = entityManager.createNativeQuery("select max(cast(substring(leav_reg_code,4) as unsigned)) from "
                    + "hr_leave_register where comp_code = " + compCode + "").getResultList();
            Vector vec = (Vector) list.get(0);
            if (vec == null) {
                return "";
            } else {
                return vec.get(0).toString();
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllByCompCode()");
            throw new Exception(e.getMessage());
        }
    }

    public List<HrLeaveRegister> leaveRegisterData(int compCode) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("leaveRegisterData()");
        }
        List<HrLeaveRegister> dataList = new ArrayList<HrLeaveRegister>();
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.FIND_ALL_LEAVE_BY_COMP_CODE);
            q1.setParameter("compCode", compCode);
            if (!q1.getResultList().isEmpty()) {
                dataList = (List<HrLeaveRegister>) q1.getResultList();
            }
            return dataList;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllByCompCode()");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Find hr leave register by employee code, company code and Depart recom
     *
     * @param compCode
     * @param empCode
     * @param departRecom
     * @return
     * @throws DAOException
     */
    public List<HrLeaveRegister> getLeaveDataByEmpCodeAndDepartRecom(int compCode, long empCode, char departRecom, String date) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getLeaveDataByEmpCodeAndDepartRecom()");
        }
        try {
            List<HrLeaveRegister> hrLeaveRegisterList;
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.LEAVE_REGISTER_BY_EMPCODE_COMPCODE_AND_DEPARTRECOM);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            q1.setParameter("departRecom", departRecom);
            q1.setParameter("date", date);
            hrLeaveRegisterList = q1.getResultList();
            return hrLeaveRegisterList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLeaveDataByEmpCodeAndDepartRecom()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLeaveDataByEmpCodeAndDepartRecom()");
            throw new DAOException(e);
        }
    }
}
