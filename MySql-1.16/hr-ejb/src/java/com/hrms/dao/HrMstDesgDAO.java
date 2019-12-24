/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
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
public class HrMstDesgDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMstDesgDAO.class);

    /**
     * @param entityManager
     */
    public HrMstDesgDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstDesgDAO Initializing...");
    }

    public List prepareOrgnEditDetail(int compcode, String contCode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PREPARE_ORG_VIEW_DETAIL);
            query.setParameter("value1", compcode);
            query.setParameter("value2", contCode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method prepareOrgnEditDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method prepareOrgnEditDetail()");
            throw new DAOException(e);
        }
    }

    public List prepareOrgnizationSaveDetail(int compcode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PREPARE_ORGN_EDIT_DETAIL);
            query.setParameter("value1", compcode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method prepareOrgnizationSaveDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method prepareOrgnizationSaveDetail()");
            throw new DAOException(e);
        }
    }

    public String deletePrepareOrgnDetails(int compCode, String contCode) throws DAOException {
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PREPARE_ORGN_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", contCode);
            query.executeUpdate();
            return "true";
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method deleteContractoerDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteContractoerDetails()");
            throw new DAOException(e);
        }
    }

    public List prepareOrgnUpdateDesgCode(int compcode, String structCode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PREPARE_ORGN_UPDATE_DESGCODE);
            query.setParameter("value1", compcode);
            query.setParameter("value2", structCode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method prepareOrgnUpdateDesgCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method prepareOrgnUpdateDesgCode()");
            throw new DAOException(e);
        }
    }
}
