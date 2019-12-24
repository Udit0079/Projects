package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrMstCompLoan;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrMstCompLoanDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMstCompLoanDAO.class);

    public HrMstCompLoanDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstCompLoanDAO Initializing...");
    }

    public List<HrMstCompLoan> getAllByCompcode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByCompcode()");
        }
        List<HrMstCompLoan> hrMstCompLoanList = null;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrMstCompLoan_BY_COMPCODE);
            q1.setParameter("compCode", compCode);
            hrMstCompLoanList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrMstCompLoanList;
    }
}
