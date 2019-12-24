package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrPersonnelReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrPersonnelReferenceDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrPersonnelReferenceDAO.class);

    public HrPersonnelReferenceDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPersonnelReferenceDAO Initializing...");
    }

    public long getMaxRefCode() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxableComponent()");
        }
        long max = 0;
        try {
            Query q1 = entityManager.createQuery("SELECT MAX(h.hrPersonnelReferencePK.refCode) FROM HrPersonnelReference h");
            if (q1.getSingleResult() != null) {
                max = (Long) q1.getSingleResult();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getMaxRefCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxRefCode()");
            throw new DAOException(e);
        }
        return max;
    }

    public List<HrPersonnelReference> findEmpByEmpCode(long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByCompCodeTypeValue()");
        }
        List<HrPersonnelReference> hrPersonnelReferenceList = new ArrayList<HrPersonnelReference>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrPersonnelReference_BY_EMPCODE);
            q1.setParameter("empCode", empCode);
            hrPersonnelReferenceList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + hrPersonnelReferenceList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEmpByEmpCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEmpByEmpCode()");
            throw new DAOException(e);
        }
        return hrPersonnelReferenceList;
    }
}
