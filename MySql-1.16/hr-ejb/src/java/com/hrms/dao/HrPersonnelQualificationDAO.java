package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrPersonnelQualification;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrPersonnelQualificationDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrPersonnelQualificationDAO.class);

    public HrPersonnelQualificationDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPersonnelQualificationDAO Initializing...");
    }

    public List<HrPersonnelQualification> findEmpByEmpCode(long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByCompCodeTypeValue()");
        }
        List<HrPersonnelQualification> hrPersonnelQualificationList = new ArrayList<HrPersonnelQualification>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrPersonnelQualification_BY_EMPCODE);
            q1.setParameter("empCode", empCode);
            hrPersonnelQualificationList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + hrPersonnelQualificationList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEmpByEmpCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEmpByEmpCode()");
            throw new DAOException(e);
        }
        return hrPersonnelQualificationList;
    }
}
