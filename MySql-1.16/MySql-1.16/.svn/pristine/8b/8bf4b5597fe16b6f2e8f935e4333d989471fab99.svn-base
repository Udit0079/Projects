package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrEmpAdvanceHd;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrEmpAdvanceHdDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrEmpAdvanceHdDAO.class);

    /**
     * @param entityManager
     */
    public HrEmpAdvanceHdDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrEmpAdvanceHdDAO Initializing...");
    }

    public List getAdvanceTableData(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByCompcode()");
        }
        List list = null;
        try {
            String query = "SELECT A.hrEmpAdvanceHdPK.empAdvNo, A.hrEmpAdvanceHdPK.empCode, A.hrEmpAdvanceHdPK.advance, A.sanctionDate, B.empId, B.empName, C.description, A.sanctionAmt" +
                    " FROM HrEmpAdvanceHd A,HrPersonnelDetails B,HrMstStruct C" +
                    " WHERE A.authBy = '' AND" +
                    " A.hrEmpAdvanceHdPK.compCode = :compCode" +
                    " AND A.hrEmpAdvanceHdPK.empCode = B.hrPersonnelDetailsPK.empCode" +
                    " AND A.hrEmpAdvanceHdPK.compCode = C.hrMstStructPK.compCode" +
                    " AND A.hrEmpAdvanceHdPK.advance = C.hrMstStructPK.structCode" +
                    " order by A.hrEmpAdvanceHdPK.empAdvNo desc";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            list = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAdvanceDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvanceDetails()");
            throw new DAOException(e);
        }
        return list;
    }

    public List<HrEmpAdvanceHd> getAdvanceDetails(int compCode, long empAdvNo) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAdvanceDetails()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_HR_EMP_ADVANCE_HD_BY_COMPCODE_EMP_ADVNO);
            query.setParameter("compCode", compCode);
            query.setParameter("empAdvNo", empAdvNo);
            List<HrEmpAdvanceHd> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getAdvanceDetails() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAdvanceDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvanceDetails()");
            throw new DAOException(e);
        }
    }

    public List getAdvanceTableDataForAuthorization(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByCompcode()");
        }
        List list = null;
        try {
            String query = "SELECT A.hrEmpAdvanceHdPK.empAdvNo, A.hrEmpAdvanceHdPK.empCode, A.hrEmpAdvanceHdPK.advance, A.sanctionDate, B.empId, B.empName, C.description, A.sanctionAmt" +
                    " FROM HrEmpAdvanceHd A,HrPersonnelDetails B,HrMstStruct C" +
                    " WHERE A.authBy = ''" +
                    " AND A.hrEmpAdvanceHdPK.compCode = :compCode" +
                    " AND A.hrEmpAdvanceHdPK.empCode = B.hrPersonnelDetailsPK.empCode" +
                    " AND A.hrEmpAdvanceHdPK.compCode = C.hrMstStructPK.compCode" +
                    " AND A.hrEmpAdvanceHdPK.advance = C.hrMstStructPK.structCode order by A.hrEmpAdvanceHdPK.empAdvNo";

            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            list = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAdvanceDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvanceDetails()");
            throw new DAOException(e);
        }
        return list;
    }

    public HrEmpAdvanceHd findEntity(int compCode, long empAdvNo) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCategorizationBasedEmployees()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HR_EMP_ADVANCE_HD_BY_COMPCODE_EMP_ADVNO);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empAdvNo", empAdvNo);
            List<HrEmpAdvanceHd> hrEmpAdvanceHdList = q1.getResultList();
            if (hrEmpAdvanceHdList.size() == 1) {
                return hrEmpAdvanceHdList.get(0);
            } else {
                return (new HrEmpAdvanceHd());
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAdvanceDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvanceDetails()");
            throw new DAOException(e);
        }
    }
}
