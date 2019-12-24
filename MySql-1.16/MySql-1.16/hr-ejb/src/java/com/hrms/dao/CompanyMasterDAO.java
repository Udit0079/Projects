/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.CompanyMaster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CompanyMasterDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(CompanyMasterDAO.class);

    public CompanyMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CompanyMasterDAO Initializing...");
    }

    public String companyname(int compCode) throws DAOException {
        String s = null;
        try {
            Query q = entityManager.createNamedQuery(NamedQueryConstant.FIND_COMPANY_NAME_BY_COMPCODE);
            q.setParameter("companyCode", compCode);
            s = q.getSingleResult().toString();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByPrimKeyAndPostFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByPrimKeyAndPostFlag()");
            throw new DAOException(e);
        }
        return s;
    }

    public List<CompanyMaster> getCompanyMasterTO(int compCode) throws DAOException {
        try {
            Query q = entityManager.createNamedQuery(NamedQueryConstant.FIND_COMPANY_MASTER_ENTITY_BY_COMPANY_CODE);
            q.setParameter("companyCode", compCode);
            List<CompanyMaster> companyMasters = q.getResultList();
            return companyMasters;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByPrimKeyAndPostFlag()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByPrimKeyAndPostFlag()");
            throw new DAOException(e);
        }
    }
}
