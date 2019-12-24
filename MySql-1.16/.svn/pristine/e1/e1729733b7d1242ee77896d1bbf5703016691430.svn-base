/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class HrExitInterviewDtDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(AdminRoleFormsDAO.class);

    /**
     * @param entityManager
     */
    public HrExitInterviewDtDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrExitInterviewDtDAO Initializing...");
    }

    public List viewDataExitInterview(int compCode, long empCode) throws DAOException {
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.VIEW_DATA_EXIT_INTERVIEW);
            query.setParameter("compCode", compCode);
            query.setParameter("empCode", empCode);
            List result = query.getResultList();
            return result;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
    }
}
