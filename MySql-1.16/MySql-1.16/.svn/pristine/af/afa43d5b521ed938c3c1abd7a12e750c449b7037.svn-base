/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrMstDeptSubdept;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class HrMstDeptSubdeptDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMstDeptSubdeptDAO.class);

    /**
     * @param entityManager
     */
    public HrMstDeptSubdeptDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstDeptSubdept Initializing...");
    }

    public List<HrMstDeptSubdept> getAllByCompcode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByCompcode()");
        }
        List<HrMstDeptSubdept> hrMstDeptSubdeptList = null;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrMstDeptSubdept_BY_COMPCODE);
            q1.setParameter("compCode", compCode);
            hrMstDeptSubdeptList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrMstDeptSubdeptList;
    }

    public List<HrMstDeptSubdept> getAllByDeptcodeSubdeptcode(int compCode, String deptCode, String subDeptCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByDeptcodeSubdeptcode()");
        }
        List<HrMstDeptSubdept> hrMstDeptSubdeptList = null;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrMstDeptSubdept_BY_COMPCODE_DEPTCODE_SUBDEPTCODE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("deptCode", deptCode);
            q1.setParameter("subDeptCode", subDeptCode);
            hrMstDeptSubdeptList = q1.getResultList();

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrMstDeptSubdeptList;
    }

    public List<HrMstDeptSubdept> getAllByDeptcode(int compCode, String deptCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByDeptcodeSubdeptcode()");
        }
        List<HrMstDeptSubdept> hrMstDeptSubdeptList = null;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrMstDeptSubdept_BY_COMPCODE_DEPTCODE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("deptCode", deptCode);
            hrMstDeptSubdeptList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrMstDeptSubdeptList;
    }

    public List getSubDepartment(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByDeptcodeSubdeptcode()");
        }
        List<HrMstDeptSubdept> hrMstDeptSubdeptList = null;
        try {
            String query =
                    "SELECT h.hrMstStructPK.structCode, h.description FROM HrMstStruct h" +
                    " WHERE h.hrMstStructPK.compCode = :compCode AND" +
                    " h.hrMstStructPK.structCode like 'SUB%' AND" +
                    " h.hrMstStructPK.structCode NOT IN" +
                    " (SELECT k.hrMstDeptSubdeptPK.subDeptCode FROM HrMstDeptSubdept k" +
                    " WHERE k.hrMstDeptSubdeptPK.compCode = :compCode)";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            hrMstDeptSubdeptList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrMstDeptSubdeptList;
    }

    public List getSubDepartmentsAssigned(int compCode, String deptCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByDeptcodeSubdeptcode()");
        }
        List<HrMstDeptSubdept> hrMstDeptSubdeptList = null;
        try {
            String query =
                    "select distinct A.hrMstDeptSubdeptPK.subDeptCode, B.description from HrMstDeptSubdept A, HrMstStruct B" +
                    " where A.hrMstDeptSubdeptPK.compCode =:compCode AND" +
                    " A.hrMstDeptSubdeptPK.subDeptCode = B.hrMstStructPK.structCode AND" +
                    " A.hrMstDeptSubdeptPK.deptCode = :deptCode";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            q1.setParameter("deptCode", deptCode);
            hrMstDeptSubdeptList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrMstDeptSubdeptList;
    }
}

