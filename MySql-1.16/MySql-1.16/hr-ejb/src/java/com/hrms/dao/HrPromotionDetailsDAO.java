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
public class HrPromotionDetailsDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(AdminRoleFormsDAO.class);

    /**
     * @param entityManager
     */
    public HrPromotionDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPromotionDetailsDAO Initializing...");
    }

    public String generateArnCode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("generateArnCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("HrPromotionDetails.findMaxArNoByCompCode");
            List resultList = createNamedQuery.setParameter("compCode", compCode).getResultList();
            return String.valueOf(resultList.get(0)==null?"1":Integer.parseInt(resultList.get(0).toString())+1);
           

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method generateAdvtCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method generateAdvtCode()");
            throw new DAOException(e);
        }

    }
    public List viewDataPromotion(int compCode)throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("viewDataPromotion()");
        }
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PROMOTION_VIEW);
            query.setParameter("compCode", compCode);
            List result = query.getResultList();
            return result;
        }catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }
  }
