/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.Parameters;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class ParametersDAO extends GenericDAO {
    private static final Logger logger = Logger.getLogger(ParametersDAO.class);
     /**
     * @param entityManager
     */
    public ParametersDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("PayrollCalanderDAO Initializing...");
    }
     public List<Parameters> findByStructCode() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findAll()");
        }
        try {
            String q = "SELECT h FROM Parameters h";
            Query query = entityManager.createQuery(q);
            List<Parameters> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByStructCode() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method findByCompCode()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method findByCompCode()");
                throw new DAOException(e);
        }
    }

}
