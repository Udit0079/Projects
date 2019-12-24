/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.to.HrTaxInvestmentCategoryTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.payroll.HrTaxInvestmentCategory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Ankit Verma
 */
public class HrTaxInvestmentCategoryDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrTaxInvestmentCategoryDAO.class);

    /**
     * @param entityManager
     */
    public HrTaxInvestmentCategoryDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrTaxInvestmentCategoryDAO Initializing...");
    }

    public String findMaxcategoryCode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findMaxcategoryCode()");
        }
        String max = "0";
        try {
            Query q = entityManager.createNamedQuery("");
            q.setParameter("compCode", compCode);
            if (q.getSingleResult() != null) {
                max = q.getSingleResult().toString();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findMaxCategoryCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findMaxCategoryCode()");
            throw new DAOException(e);
        }
        return max;
    }
    // Find category code to edit data   

    public List selectCategorycode(int categoryCode, int empCode) throws DAOException {

        List result = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.SELECT_CATEGORY_CODE);
            query.setParameter("categoryCode", categoryCode);
            query.setParameter("empCode", empCode);
            result = query.getResultList();


        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method selectCategorycode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectCategorycode()");
            throw new DAOException(e);
        }
        return result;
    }

    public HrPersonnelDetails findEmployeeName(int empCode) throws DAOException {
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_EMPLOYEE_NAME_BY_EMPCODE);
            query.setParameter("empCode", empCode);
            return (HrPersonnelDetails) query.getSingleResult();

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEmployeeName()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEmployeeName()");
            throw new DAOException(e);
        }
    }

    public List<HrTaxInvestmentCategory>  viewDataTaxInvestmentCategory(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("viewDataTaxInvestmentCategory()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.VIEW_DATA_TAXINVESTMENTCATEGORY);
            query.setParameter("compCode", compCode);
            List<HrTaxInvestmentCategory> result = query.getResultList();
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
