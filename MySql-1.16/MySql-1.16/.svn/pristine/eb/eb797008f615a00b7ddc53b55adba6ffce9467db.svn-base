/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.to.ClearSlipDetTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.ClearSlipDet;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class ClearSlipDetDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(AdminRoleFormsDAO.class);

    /**
     * @param entityManager
     */
    public ClearSlipDetDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("AdminRoleFormsDAO Initializing...");
    }

    public ClearSlipDet findByPrimaryKey(ClearSlipDetTO clearSlipDetTO) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByPrimaryKey()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_BY_PRIMARY_KEY_CLEARSLIPDET);
            query.setParameter("compCode", clearSlipDetTO.getClearSlipDetPK().getCompCode());
            query.setParameter("empCode", clearSlipDetTO.getClearSlipDetPK().getEmpCode());
            query.setParameter("deptCode", clearSlipDetTO.getClearSlipDetPK().getDeptCode());
            ClearSlipDet clearSlipDet = (ClearSlipDet) query.getSingleResult();
            return clearSlipDet;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
    }
}
