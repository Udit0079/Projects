package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrPersonnelPreviousCompany;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrPersonnelPreviousCompanyDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrPersonnelPreviousCompanyDAO.class);

    public HrPersonnelPreviousCompanyDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPersonnelPreviousCompanyDAO Initializing...");
    }

    public List<HrPersonnelPreviousCompany> getExperienceData(long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByCompCodeTypeValue()");
        }
        List<HrPersonnelPreviousCompany> hrPersonnelPreviousCompanyList = new ArrayList<HrPersonnelPreviousCompany>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrPersonnelPreviousCompany_BY_EMPCODE);
            q1.setParameter("empCode", empCode);
            hrPersonnelPreviousCompanyList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + hrPersonnelPreviousCompanyList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getExperienceData()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getExperienceData()");
            throw new DAOException(e);
        }
        return hrPersonnelPreviousCompanyList;
    }

    public long getMaxRefCode() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxableComponent()");
        }
        long max = 0;
        try {
            Query q1 = entityManager.createQuery("SELECT MAX(h.hrPersonnelPreviousCompanyPK.prevCompCode) FROM HrPersonnelPreviousCompany h");
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
}
