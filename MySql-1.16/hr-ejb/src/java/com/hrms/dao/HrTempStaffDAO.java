/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrTempStaff;
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
public class HrTempStaffDAO extends GenericDAO {
     private static final Logger logger = Logger.getLogger(HrMstStructDAO.class);

    public HrTempStaffDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("PayrollCalanderDAO Initializing...");
    }
      public List<HrTempStaff> findByCompCode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCode()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.TEMPORARY_STAFF_EDIT_DETAIL);
            query.setParameter("compCode", compCode);
            List<HrTempStaff> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByCompCode() - end - return value=" + resultList);
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

     public HrTempStaff deleteTemporaryStaff(int compCode, String arNum) throws DAOException {
        HrTempStaff instance = new HrTempStaff();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TEMPORARY_STAFF_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", arNum);
            instance = (HrTempStaff)query.getSingleResult();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method deleteTemporaryStaff()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method deleteTemporaryStaff()");
                throw new DAOException(e);
        }
    }


      public List temporaryStaffPrimrycheck(int compCode, String arNum) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TEMPORARY_STAFF_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", arNum);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method temporaryStaffPrimrycheck()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method temporaryStaffPrimrycheck()");
            throw new DAOException(e);
        }
    }


}
