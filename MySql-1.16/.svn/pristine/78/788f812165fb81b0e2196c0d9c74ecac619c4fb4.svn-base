/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.to.ClearSlipHdTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.ClearSlipHd;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class ClearSlipHdDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(AdminRoleFormsDAO.class);

    /**
     * @param entityManager
     */
    public ClearSlipHdDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("AdminRoleFormsDAO Initializing...");
    }

    public List viewDataSettelementLetter(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("viewDataSettelementLetter()");
        }
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.VIEW_DATA_SETTLEMENT);
            query.setParameter("compCode", compCode);
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

    public ClearSlipHd findByPrimaryKey(ClearSlipHdTO to) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByPrimaryKey()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_BY_PRIMARY_KEY_CLEARSHLIPHD);
            query.setParameter("compCode", to.getClearSlipHdPK().getCompCode());
            query.setParameter("empCode", to.getClearSlipHdPK().getEmpCode());
            ClearSlipHd clearSlipHd = (ClearSlipHd) query.getSingleResult();
            return clearSlipHd;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
    }
}
