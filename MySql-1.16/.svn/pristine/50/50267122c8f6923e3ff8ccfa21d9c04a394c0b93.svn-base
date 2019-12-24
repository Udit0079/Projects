/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrAttendanceDetails;
import com.hrms.entity.payroll.HrAttendanceDetailsPK;
import com.hrms.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * /**
 *
 * @author Sudhir Bisht
 */
public class HrAttendanceDetailsDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrAttendanceDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrAttendanceDetailsDAO Initializing...");
    }

    /**
     * Find Hr attendance details for Month with postflag
     *
     * @param hrAttendancePK
     * @param postFlag
     * @return
     * @throws DAOException
     */
    public List<HrAttendanceDetails> findAttendanceDetailsPostedForMonth(HrAttendanceDetails hrAttendanceDetails) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findAttendanceDetailsPostedForMonth()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.ATTENDANCE_DETAILS_POSTED_OF_MONTH);
            q1.setParameter("compCode", hrAttendanceDetails.getHrAttendanceDetailsPK().getCompCode());
            q1.setParameter("empCode", hrAttendanceDetails.getHrAttendanceDetailsPK().getEmpCode());
            q1.setParameter("attenMonth", hrAttendanceDetails.getHrAttendanceDetailsPK().getAttenMonth());
            q1.setParameter("attenYear", hrAttendanceDetails.getHrAttendanceDetailsPK().getAttenYear());
            q1.setParameter("postFlag", hrAttendanceDetails.getPostFlag());
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findAttendanceDetailsPostedForMonth()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAttendanceDetailsPostedForMonth()");
            throw new DAOException(e);
        }
    }

    /**
     * Get attendance details of all emloyees by company Code and year
     * parameters passed
     *
     * @param compCode
     * @param year
     * @return
     * @throws DAOException
     */
    public List getAttenDataByComCodeAndCurrentYr(int compCode, int year) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAttenDataByComCodeAndCurrentYr()");
        }
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.ATTENDANCE_DETAILS_FOR_CURRENT_YEAR);
            q1.setParameter("compCode", compCode);
            //q1.setParameter("attenYear", year);
            return q1.getResultList();
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAttenDataByComCodeAndCurrentYr()");
            throw new ApplicationException(e);
        }
    }

    /**
     * find entity hrAttendanceDetails by primary composite key and post flag
     *
     * @param hrAttendanceDetailsPK
     * @return
     * @throws DAOException
     */
    public List<HrAttendanceDetails> getByPrimaryCompositeKeyAndPostFlag(HrAttendanceDetailsPK hrAttendanceDetailsPK, char postFlag) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getByPrimaryCompositeKeyAndPostFlag()");
        }
        List<HrAttendanceDetails> hrAttendanceDetailsList = new ArrayList<HrAttendanceDetails>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.ATTENDANCE_DETAILS_BY_PRIMARY_KEY_AND_POSTFLAG);
            q1.setParameter("compCode", hrAttendanceDetailsPK.getCompCode());
            q1.setParameter("empCode", hrAttendanceDetailsPK.getEmpCode());
            q1.setParameter("attenMonth", hrAttendanceDetailsPK.getAttenMonth());
            q1.setParameter("attenYear", hrAttendanceDetailsPK.getAttenYear());
            q1.setParameter("postFlag", postFlag);
            hrAttendanceDetailsList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getByPrimaryCompositeKeyAndPostFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getByPrimaryCompositeKeyAndPostFlag()");
            throw new DAOException(e);
        }
        return hrAttendanceDetailsList;
    }

    /**
     * Get attendance details between processing dates and with post flag status
     *
     * @param hrAttendanceDetail
     * @return
     * @throws DAOException
     */
    public List<HrAttendanceDetails> getAttendanceDetailsWithPostFlag(HrAttendanceDetails hrAttendanceDetail) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAttendanceDetailsWithPostFlag()");
        }
        List<HrAttendanceDetails> hrAttendanceDetailsList = new ArrayList<HrAttendanceDetails>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.ATTENDANCE_DETAILS_BETWEEN_PAGE_DATES_AND_POSTFLAG);
            q1.setParameter("compCode", hrAttendanceDetail.getHrAttendanceDetailsPK().getCompCode());
            q1.setParameter("empCode", hrAttendanceDetail.getHrAttendanceDetailsPK().getEmpCode());
            q1.setParameter("pageDateFrom", hrAttendanceDetail.getProcessDateFrom());
            q1.setParameter("pageDateTo", hrAttendanceDetail.getProcessDateTo());
            q1.setParameter("postFlag", hrAttendanceDetail.getPostFlag());
            hrAttendanceDetailsList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAttendanceDetailsWithPostFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAttendanceDetailsWithPostFlag()");
            throw new DAOException(e);
        }
        return hrAttendanceDetailsList;
    }
}
