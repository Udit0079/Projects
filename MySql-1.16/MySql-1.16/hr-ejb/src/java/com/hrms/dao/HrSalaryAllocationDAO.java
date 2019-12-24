/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.to.SalaryAllocationTOExtra;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrSalaryAllocation;
import com.hrms.entity.payroll.HrSalaryAllocationPK;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Bisht
 */
public class HrSalaryAllocationDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * @param entityManager
     */
    public HrSalaryAllocationDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrSalaryAllocationDAO Initializing...");
    }

    /**
     * Get salary allocate for month
     *
     * @param hrSalaryAllocationPK
     * @return
     * @throws DAOException
     */
    public List<HrSalaryAllocation> getSalaryAllocateForMonth(HrSalaryAllocationPK hrSalaryAllocationPK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getSalaryAllocateForMonth");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SALARY_ALLOCATION_FOR_THE_MONTH);
            q1.setParameter("compCode", hrSalaryAllocationPK.getCompCode());
            q1.setParameter("empCode", hrSalaryAllocationPK.getEmpCode());
//            q1.setParameter("calDateFrom", hrSalaryAllocationPK.getCalDateFrom());
//            q1.setParameter("calDateTo", hrSalaryAllocationPK.getCalDateTo());
//            q1.setParameter("months", hrSalaryAllocationPK.getMonths());
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSalaryAllocateForMonth()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSalaryAllocateForMonth()");
            throw new DAOException(e);
        }
    }

    public List<HrSalaryAllocation> findsalaryAllocateByEmp(SalaryAllocationTOExtra salaryAllocationTOExtra) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findsalaryAllocateByEmp");
        }
        List<HrSalaryAllocation> salaryAllocationList;
        try {
            Query q1 = entityManager.createQuery("SELECT h FROM HrSalaryAllocation h where h.hrSalaryAllocationPK.compCode = :compCode and"
                    + " h.hrSalaryAllocationPK.empCode = :empCode and h.hrSalaryAllocationPK.calDateFrom = :calDateFrom"
                    + " and h.hrSalaryAllocationPK.calDateTo = :calDateTo and h.hrSalaryAllocationPK.months = :months"
                    + " and h.hrSalaryAllocationPK.componentType in(select hs.hrSalaryStructurePK.alDesc FROM HrSalaryStructure hs where hs.hrSalaryStructurePK.compCode = :compCode "
                    + " and hs.hrSalaryStructurePK.purposeCode = :purposeCode and hs.hrSalaryStructurePK.dateFrom = :calDateFrom and hs.hrSalaryStructurePK.dateTo = :calDateTo)");
            q1.setParameter("compCode", salaryAllocationTOExtra.getCompCode());
            q1.setParameter("empCode", salaryAllocationTOExtra.getEmpCode());
            q1.setParameter("calDateFrom", formatter.format(salaryAllocationTOExtra.getCalDateFrom()));
            q1.setParameter("calDateTo", formatter.format(salaryAllocationTOExtra.getCaldateTo()));
            q1.setParameter("months", String.valueOf(salaryAllocationTOExtra.getMonth()));
            q1.setParameter("purposeCode", salaryAllocationTOExtra.getPurposeCode());
            salaryAllocationList = q1.getResultList();

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findsalaryAllocateByEmp()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findsalaryAllocateByEmp()");
            throw new DAOException(e);
        }
        return salaryAllocationList;
    }

    public List<HrSalaryAllocation> findsalAllocByVariousParameters(HrSalaryAllocationPK hrSalaryAllocationPK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findsalaryAllocateByEmp");
        }
        List<HrSalaryAllocation> salaryAllocationList;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.HRSALARYALLOCATION_BY_VARIOUS_PARAMETERS);
            q1.setParameter("compCode", hrSalaryAllocationPK.getCompCode());
            q1.setParameter("empCode", hrSalaryAllocationPK.getEmpCode());
//            q1.setParameter("calDateFrom", hrSalaryAllocationPK.getCalDateFrom());
//            q1.setParameter("calDateTo", hrSalaryAllocationPK.getCalDateTo());
//            q1.setParameter("months", hrSalaryAllocationPK.getMonths());
//            q1.setParameter("componentType", hrSalaryAllocationPK.getComponentType());
            salaryAllocationList = q1.getResultList();

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findsalaryAllocateByEmp()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findsalaryAllocateByEmp()");
            throw new DAOException(e);
        }
        return salaryAllocationList;
    }

    /**
     * get salary allocate by empcode , company code between financial year
     *
     * @param hrSalaryAllocationPK
     * @return
     * @throws DAOException
     */
    public List<HrSalaryAllocation> findsalAllocByEmpCodeAndCompCode(HrSalaryAllocationPK hrSalaryAllocationPK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findsalAllocByEmpCodeAndCompCode");
        }
        List<HrSalaryAllocation> salaryAllocationList;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_SALARY_ALLOCATE_BY_EMPCODE);
            q1.setParameter("compCode", hrSalaryAllocationPK.getCompCode());
            q1.setParameter("empCode", hrSalaryAllocationPK.getEmpCode());
//            q1.setParameter("calDateFrom", hrSalaryAllocationPK.getCalDateFrom());
//            q1.setParameter("calDateTo", hrSalaryAllocationPK.getCalDateTo());
            salaryAllocationList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findsalAllocByEmpCodeAndCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findsalAllocByEmpCodeAndCompCode()");
            throw new DAOException(e);
        }
        return salaryAllocationList;
    }

    public List findTaAmt(HrSalaryAllocationPK hrSalaryAllocationPK, String purposeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findTaAmt");
        }
        List salaryAllocationList;
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.GET_TA_AMT_OF_EMPLOYEE_BETWEEN_FINANCIAL_YEAR);
            q1.setParameter("compCode", hrSalaryAllocationPK.getCompCode());
            q1.setParameter("empCode", hrSalaryAllocationPK.getEmpCode());
////            q1.setParameter("calDateFrom", hrSalaryAllocationPK.getCalDateFrom());
////            q1.setParameter("calDateTo", hrSalaryAllocationPK.getCalDateTo());
////            q1.setParameter("months", hrSalaryAllocationPK.getMonths());
//            q1.setParameter("componentType", hrSalaryAllocationPK.getComponentType());
            q1.setParameter("purposeCode", purposeCode);
            salaryAllocationList = q1.getResultList();

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findTaAmt()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findTaAmt()");
            throw new DAOException(e);
        }
        return salaryAllocationList;
    }

    /**
     * Get allowances for the employee based on company code, employee code,
     * Calendar dates , months and purpose code
     *
     * @param hrSalaryAllocationPK
     * @param puroseCode
     * @return
     * @throws DAOException
     */
    public List getSalaryAmtOfEmpForMonth(HrSalaryAllocationPK hrSalaryAllocationPK, String puroseCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllowancesOfEmpForMonth()");
        }
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.GET_TOTAL_ALLOWANCE_BY_EMPCODE_MONTHS_FINACIAL_YEAR_DATES);
            q1.setParameter("compCode", hrSalaryAllocationPK.getCompCode());
            q1.setParameter("empCode", hrSalaryAllocationPK.getEmpCode());
    //        q1.setParameter("calDateFrom", hrSalaryAllocationPK.getCalDateFrom());
    //        q1.setParameter("calDateTo", hrSalaryAllocationPK.getCalDateTo());
    //        q1.setParameter("months", hrSalaryAllocationPK.getMonths());
            q1.setParameter("purposeCode", puroseCode);
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAllowancesOfEmpForMonth()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAllowancesOfEmpForMonth()");
            throw new DAOException(e);
        }
    }
}
