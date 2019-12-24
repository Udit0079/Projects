/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrSalaryAllocationPK;
import com.hrms.entity.payroll.HrSalaryProcessing;
import com.hrms.entity.payroll.HrSalaryProcessingPK;
import com.hrms.entity.payroll.HrTaxInvestmentCategory;
import com.hrms.entity.payroll.HrTaxSlabMaster;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
/**
 *
 * @author Sudhir Kr Bisht
 */
public class HrSalaryProcessingDAO extends GenericDAO {

   private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrSalaryProcessingDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrSalaryProcessingDAO Initializing...");
    }

    /**
     * Get salary processed for month for employee with post flag
     * @param compCode
     * @param empCode
     * @param fromDate
     * @param toDate
     * @param month
     * @param postFlag
     * @return
     * @throws DAOException
     */
    public List<HrSalaryProcessing> getSalaryForTheMonthWithPostFlag(HrSalaryProcessing hrSalaryProcessing) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getSalaryForTheMonthWithPostFlag()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SALARY_PROCESSED_FOR_MONTH_WITH_POSTFLAG);
            q1.setParameter("compCode",hrSalaryProcessing.getHrSalaryProcessingPK().getCompCode());
            q1.setParameter("empCode", hrSalaryProcessing.getHrPersonnelDetails().getHrPersonnelDetailsPK().getEmpCode());
            q1.setParameter("calDateFrom", hrSalaryProcessing.getHrSalaryProcessingPK().getCalDateFrom());
            q1.setParameter("calDateTo", hrSalaryProcessing.getHrSalaryProcessingPK().getCalDateTo());
            q1.setParameter("months", hrSalaryProcessing.getHrSalaryProcessingPK().getMonths());
            q1.setParameter("postFlag", 'Y');
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSalaryForTheMonthWithPostFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSalaryForTheMonthWithPostFlag()");
            throw new DAOException(e);
        }
    }

    /**
     * Get salary Processed for the year with post Flag
     * @param hrSalaryProcessing
     * @return
     * @throws DAOException
     */
     public List<HrSalaryProcessing> getSalaryForTheYearWithPostFlag(HrSalaryProcessing hrSalaryProcessing) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getSalaryForTheYearWithPostFlag()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SALARY_PROCESSING_FOR_EMP_FOR_YEAR_WITH_POSTFLAG);
            q1.setParameter("compCode",hrSalaryProcessing.getHrSalaryProcessingPK().getCompCode());
            q1.setParameter("empCode", hrSalaryProcessing.getHrSalaryProcessingPK().getEmpCode());
            q1.setParameter("calDateFrom", hrSalaryProcessing.getHrSalaryProcessingPK().getCalDateFrom());
            q1.setParameter("calDateTo", hrSalaryProcessing.getHrSalaryProcessingPK().getCalDateTo());
            q1.setParameter("postFlag", hrSalaryProcessing.getPostFlag());
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSalaryForTheYearWithPostFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSalaryForTheYearWithPostFlag()");
            throw new DAOException(e);
        }
    }

    /**
     * Get salary processed for employee for the year
     * @param hrSalaryProcessingPK
     * @return
     * @throws DAOException
     */
      public List<HrSalaryProcessing> getSalaryProcessedForYear(HrSalaryProcessingPK hrSalaryProcessingPK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getSalaryProcessedForYear()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SALARY_PROCESSED_FOR_YEAR);
            q1.setParameter("compCode", hrSalaryProcessingPK.getCompCode());
            q1.setParameter("empCode", hrSalaryProcessingPK.getEmpCode());
            q1.setParameter("calDateFrom", hrSalaryProcessingPK.getCalDateFrom());
            q1.setParameter("calDateTo", hrSalaryProcessingPK.getCalDateTo());
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSalaryProcessedForYear()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSalaryProcessedForYear()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param compCode
     * @param empCode
     * @param fromDate
     * @param toDate
     * @param month
     * @param postFlag
     * @param receiptFlag
     * @return
     * @throws DAOException
     */
    public List<HrSalaryProcessing> getempSalTransForTheMonth(int compCode, Long empCode, Date fromDate, Date toDate, String month, char postFlag, char receiptFlag) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getempSalTransForTheMonth()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.EMPLOYEE_SALARY_TRANSFER_FOR_MONTH);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            q1.setParameter("calDateFrom", fromDate);
            q1.setParameter("calDateTo", toDate);
            q1.setParameter("receiptFlag", 'Y');
            q1.setParameter("months", month);
            q1.setParameter("postFlag", 'Y');
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getempSalTransForTheMonth()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getempSalTransForTheMonth()");
            throw new DAOException(e);
        }
    }
    
    public List<HrSalaryProcessing> getTaxProcessEmpForYearWithPostFlag(HrSalaryAllocationPK hrSalaryAllocationPK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxProcessEmpForYearWithPostFlag()");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SALARY_PROCESSING_FOR_EMP_FOR_YEAR_WITH_POSTFLAG);
            q1.setParameter("compCode", hrSalaryAllocationPK.getCompCode());
            q1.setParameter("empCode", hrSalaryAllocationPK.getEmpCode());
//            q1.setParameter("calDateFrom", sdf.parse(hrSalaryAllocationPK.getCalDateFrom()));
//            q1.setParameter("calDateTo", sdf.parse(hrSalaryAllocationPK.getCalDateTo()));
            q1.setParameter("postFlag", 'Y');
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getTaxProcessEmpForYearWithPostFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTaxProcessEmpForYearWithPostFlag()");
            throw new DAOException(e);
        }
    }
    
    
    public List<HrTaxInvestmentCategory> findByCompCodeAndEmpCode(int compCode, long empCode) throws DAOException{
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCodeAndEmpCode()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_BY_COMP_CODE_AND_EMP_CODE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCodeAndEmpCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCodeAndEmpCode()");
            throw new DAOException(e);
        }
    }
    
    public List<HrTaxSlabMaster> findByCompCodeAndTaxFor(int compCode, String taxFor)throws DAOException{
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxProcessEmpForYearWithPostFlag()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_BY_COMP_CODE_AND_TAX_FOR);
            q1.setParameter("compCode", compCode);
            q1.setParameter("taxFor", taxFor);
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getTaxProcessEmpForYearWithPostFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTaxProcessEmpForYearWithPostFlag()");
            throw new DAOException(e);
        }
    }
}
