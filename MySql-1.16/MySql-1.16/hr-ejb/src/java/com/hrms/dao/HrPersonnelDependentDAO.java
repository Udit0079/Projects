package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrPersonnelDependent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrPersonnelDependentDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrPersonnelDependentDAO.class);

    public HrPersonnelDependentDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPersonnelDependentDAO Initializing...");
    }

    public List<HrPersonnelDependent> findEmpByEmpCode(long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByEmpCode()");
        }
        List<HrPersonnelDependent> hrPersonnelDependentList = new ArrayList<HrPersonnelDependent>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrPersonnelDependent_BY_EMPCODE);
            q1.setParameter("empCode", empCode);
            hrPersonnelDependentList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + hrPersonnelDependentList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEmpByEmpCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEmpByEmpCode()");
            throw new DAOException(e);
        }
        return hrPersonnelDependentList;
    }

    public long getMaxDependentCode() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxableComponent()");
        }
        long max = 0;
        try {
            Query q1 = entityManager.createQuery("SELECT MAX(h.hrPersonnelDependentPK.dependentCode) FROM HrPersonnelDependent h");
            if (q1.getSingleResult() != null) {
                max = (Long) q1.getSingleResult();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getMaxDependentCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxDependentCode()");
            throw new DAOException(e);
        }
        return max;
    }
}
