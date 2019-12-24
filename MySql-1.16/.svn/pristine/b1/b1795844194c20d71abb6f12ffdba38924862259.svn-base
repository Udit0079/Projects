/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrShiftMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class HrShiftMapDAO extends GenericDAO {
     private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);
    /**
     * @param entityManager
     */
    public HrShiftMapDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrShiftMapDAO Initializing...");
    }
    /**
     * Get shift details for the employee
     * @param compCode
     * @param empCode
     * @return
     * @throws DAOException
     */
    public List<HrShiftMap> hrShiftMap(Integer compCode, Long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("hrShiftMap()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_ENTITY_BY_COMPANYCODE_AND_EMPCODE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method hrShiftMap()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method hrShiftMap()");
            throw new DAOException(e);
        }
    }
    
}
