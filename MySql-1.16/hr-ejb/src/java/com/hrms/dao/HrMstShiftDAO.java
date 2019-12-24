/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrMstShift;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class HrMstShiftDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrMstShiftDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("LeaveMasterDAO Initializing...");
    }

    public List<HrMstShift> getShiftMasterByCompCode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getShiftMasterByCompCode()");
        }
        List<HrMstShift> hrMstShiftList = null;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_MST_SHIFT_ENTITY_BY_COMPCODE);
            q1.setParameter("compCode", compCode);
            hrMstShiftList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
        return hrMstShiftList;
    }

    public List<HrMstShift> getShiftListData(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getShiftListData()");
        }
        try {
            List<HrMstShift> hrLeaveRegisterList;
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_MST_SHIFT_ENTITY_BY_COMPCODE);
            q1.setParameter("compCode", compCode);
            hrLeaveRegisterList = q1.getResultList();
            return hrLeaveRegisterList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getShiftListData()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getShiftListData()");
            throw new DAOException(e);
        }
    }

    public List getShifts(int compCode, String shiftCode, long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getShifts()");
        }
        try {
            List<HrMstShift> hrLeaveRegisterList;
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_MST_SHIFT_ENTITY_BY_COMPCODE);
            q1.setParameter("compCode", compCode);
            hrLeaveRegisterList = q1.getResultList();
            return hrLeaveRegisterList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getShifts()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getShifts()");
            throw new DAOException(e);
        }
    }

    /**
     * Get employee shift details by employee code and company code
     * @param compCode
     * @param empCode
     * @return
     * @throws DAOException
     */
    public List getEmployeeShiftsDetails(int compCode, long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEmployeeShiftsDetails()");
        }
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.GET_EMP_SHIFTS_DETAILS);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getEmployeeShiftsDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeShiftsDetails()");
            throw new DAOException(e);
        }
    }

    public HrMstShift getEntityByShiftCodeAndCompCode(Integer compCode, String shiftCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityByShiftCodeAndCompCode()");
        }
        HrMstShift entity = null;
        try {
            Query q1 = entityManager.createNamedQuery("HrMstShift.findByShiftCodeAndCompCode");
            q1.setParameter("compCode", compCode);
            q1.setParameter("shiftCode", shiftCode);
            List resultList = q1.getResultList();
            if (!resultList.isEmpty()) {
                entity = (HrMstShift) resultList.get(0);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getEntityByShiftCodeAndCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEntityByShiftCodeAndCompCode()");
            throw new DAOException(e);
        }
        return entity;
    }
}
