/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrEmpLoanHd;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author stellar
 */
public class HrEmpLoanHdDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrEmpLoanHdDAO.class);

    /**
     * @param entityManager
     */
    public HrEmpLoanHdDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrEmpLoanHdDAO Initializing...");
    }

    public List getLoanTableData(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByCompcode()");
        }
        List list = null;
        try {
            String query = "SELECT A.hrEmpLoanHdPK.empLoanNo, A.hrEmpLoanHdPK.empCode, A.hrEmpLoanHdPK.loanType, A.sanctionDate, B.empId, B.empName, C.description, A.sanctionAmt,A.instPlan" +
                    " FROM HrEmpLoanHd A,HrPersonnelDetails B,HrMstStruct C" +
                    " WHERE A.authBy = ''" +
                    " AND A.hrEmpLoanHdPK.compCode = :compCode" +
                    " AND A.hrEmpLoanHdPK.empCode = B.hrPersonnelDetailsPK.empCode" +
                    " AND A.hrEmpLoanHdPK.compCode = C.hrMstStructPK.compCode" +
                    " AND A.hrEmpLoanHdPK.loanType = C.hrMstStructPK.structCode" +
                    " order by A.hrEmpLoanHdPK.empLoanNo";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            list = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return list;
    }

    public List<HrEmpLoanHd> getLoanDetails(int compCode, long empLoanNo) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getLoanDetails()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_HR_EMP_LOAN_HD_BY_COMPCODE_EMPLOANNO);
            query.setParameter("compCode", compCode);
            query.setParameter("empLoanNo", empLoanNo);
            List<HrEmpLoanHd> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getLoanDetails() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
    }

    /**
     * Get employee loan header details based on employee code and company code
     * @param compCode
     * @param empCode
     * @return
     * @throws DAOException
     */
    public List<HrEmpLoanHd> findEmpLoanHeader(int compCode, long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpLoanHeader()");
        }
        List<HrEmpLoanHd> empLoanHeaderList = new ArrayList<HrEmpLoanHd>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.EMP_LOAN_HEADER_BY_EMPCODE_AND_COMPANY_CODE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            empLoanHeaderList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEmpLoanHeader()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEmpLoanHeader()");
            throw new DAOException(e);
        }
        return empLoanHeaderList;
    }

    public List getLoanTableDataForAuthorization(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByCompcode()");
        }
        List list = null;
        try {
            String query = "SELECT A.hrEmpLoanHdPK.empLoanNo, A.hrEmpLoanHdPK.empCode, A.hrEmpLoanHdPK.loanType, A.sanctionDate, B.empId, B.empName, C.description, A.sanctionAmt,A.instPlan" +
                    " FROM HrEmpLoanHd A,HrPersonnelDetails B,HrMstStruct C" +
                    " WHERE A.authBy = ''" +
                    " AND A.hrEmpLoanHdPK.compCode = :compCode" +
                    " AND A.hrEmpLoanHdPK.empCode = B.hrPersonnelDetailsPK.empCode" +
                    " AND A.hrEmpLoanHdPK.compCode = C.hrMstStructPK.compCode" +
                    " AND A.hrEmpLoanHdPK.loanType = C.hrMstStructPK.structCode" +
                    " order by A.hrEmpLoanHdPK.empLoanNo";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            list = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return list;
    }

    public HrEmpLoanHd findEntity(int compCode, long empLoanNo) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCategorizationBasedEmployees()");
        }
        HrEmpLoanHd hrEmpLoanHd = new HrEmpLoanHd();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HR_EMP_LOAN_HD_BY_COMPCODE_EMPLOANNO);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empLoanNo", empLoanNo);
            hrEmpLoanHd = (HrEmpLoanHd) q1.getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("findEntity() - end - return value=" + hrEmpLoanHd);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrEmpLoanHd;
    }
}
