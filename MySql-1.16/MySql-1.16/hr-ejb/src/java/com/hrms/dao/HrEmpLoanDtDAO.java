package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrEmpLoanDt;
import com.hrms.entity.personnel.HrEmpLoanDtPK;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrEmpLoanDtDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrEmpLoanDtDAO.class);

    public HrEmpLoanDtDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrEmpLoanDtDAO Initializing...");
    }

    public long getMaxAdvNoFromHrEmpLoanDt(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxableComponent()");
        }
        long max = 0;
        try {
            Query q1 = entityManager.createQuery("select MAX(h.hrEmpLoanDtPK.empLoanNo) FROM HrEmpLoanDt h" +
                    " where h.hrEmpLoanDtPK.compCode = :compCode");
            q1.setParameter("compCode", compCode);
            if (q1.getSingleResult() != null) {
                max = (Long) q1.getSingleResult();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getEmpLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmpLoanDetails()");
            throw new DAOException(e);
        }
        return max;
    }

    public List<HrEmpLoanDt> getEmpLoanDetails(HrEmpLoanDtPK hrEmpLoanDtPK, Date dateFrom, Date toDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEmpLoanDetails()");
        }
        List<HrEmpLoanDt> empLoanDetailsList = new ArrayList<HrEmpLoanDt>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.EMP_LOAN_DETAILS_BY_EMPCODE_EMPLOAN_AND_FINANCIAL_YRS);
            q1.setParameter("compCode", hrEmpLoanDtPK.getCompCode());
            q1.setParameter("empCode", hrEmpLoanDtPK.getEmpCode());
            q1.setParameter("empLoanNo", hrEmpLoanDtPK.getEmpLoanNo());
            q1.setParameter("dateFrom", dateFrom);
            q1.setParameter("dateTo", toDate);
            empLoanDetailsList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getEmpLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmpLoanDetails()");
            throw new DAOException(e);
        }
        return empLoanDetailsList;
    }
}

