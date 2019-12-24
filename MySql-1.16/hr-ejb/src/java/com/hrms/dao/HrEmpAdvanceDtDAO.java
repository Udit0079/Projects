package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrEmpAdvanceDtDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrEmpAdvanceDtDAO.class);

    /**
     * @param entityManager
     */
    public HrEmpAdvanceDtDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrEmpAdvanceDtDAO Initializing...");
    }

    public long getMaxAdvNoFromHrEmpAdvanceDt(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxableComponent()");
        }
        long max = 0;
        try {
            Query q1 = entityManager.createQuery("select MAX(h.hrEmpAdvanceDtPK.empAdvNo) FROM HrEmpAdvanceDt h" +
                    " where h.hrEmpAdvanceDtPK.compCode = :compCode");
            q1.setParameter("compCode", compCode);
            Object O = q1.getSingleResult();
            if (O != null) {
                max = (Long) O;
            } else {
                max = 0;
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return max;
    }
}
