/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrConsultant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrConsultantDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrConsultantDAO.class);

    /**
     * @param entityManager
     */
    public HrConsultantDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrConsultantDAO Initializing...");
    }
     public List<HrConsultant> findByConsultantName(int compCode, String consultantName) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByStructCode()");
        }
        try {
            Query query=entityManager.createNamedQuery(NamedQueryConstant.STRUCT_FIND_BY_CONSULTANT_NAME);
            query.setParameter("value1", compCode);
            query.setParameter("value2", consultantName+"%");
            List<HrConsultant> resultList = query.getResultList();
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

      public List<HrConsultant> consultantDetails(int compCode,String consCode,String name) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByStructCode()");
        }
        try {
            Query query=entityManager.createNamedQuery(NamedQueryConstant.STRUCT_FIND_BY_CONSULTANT_DETAILS);
            query.setParameter("value1", compCode);
            query.setParameter("value2", consCode);
            query.setParameter("value3", name);
            List<HrConsultant> resultList = query.getResultList();
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

        public HrConsultant deleteConsultant(int compCode, String consCode) throws DAOException {
        HrConsultant instance = new HrConsultant();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CONSULTANT_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", consCode);
            instance = (HrConsultant)query.getSingleResult();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method deleteConsultant()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method deleteConsultant()");
                throw new DAOException(e);
        }
    }

         public List consultantPrimrycheck(int compCode, String consCode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CONSULTANT_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", consCode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method consultantPrimrycheck()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method consultantPrimrycheck()");
            throw new DAOException(e);
        }
    }

}