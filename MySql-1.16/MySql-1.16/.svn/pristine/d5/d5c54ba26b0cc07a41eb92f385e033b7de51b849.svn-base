/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrAdvertDt;
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
public class HrAdvertDtDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrAdvertDtDAO.class);

    /**
     * @param entityManager
     */
    public HrAdvertDtDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrAdvertDtDAO Initializing...");
    }

       public HrAdvertDt deleteAdvertisementDt(int compCode, String consCode) throws DAOException {
        HrAdvertDt instance = new HrAdvertDt();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.ADV_DELETE_DT);
            query.setParameter("value1", compCode);
            query.setParameter("value2", consCode);
            instance = (HrAdvertDt)query.getSingleResult();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method deleteAdvertisementDt()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method deleteAdvertisementDt()");
                throw new DAOException(e);
        }
    }


        public List deleteAdvertisementDtCheck(int compCode, String advtCode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.ADV_DELETE_DT);
            query.setParameter("value1", compCode);
            query.setParameter("value2", advtCode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method deleteAdvertisementDtCheck()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAdvertisementDtCheck()");
            throw new DAOException(e);
        }
    }

}