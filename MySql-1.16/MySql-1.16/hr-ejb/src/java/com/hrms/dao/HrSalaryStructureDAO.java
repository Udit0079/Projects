/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrSalaryStructure;
import com.hrms.entity.hr.HrSalaryStructurePK;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class HrSalaryStructureDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrSalaryStructureDAO.class);

    /**
     * @param entityManager
     */
    public HrSalaryStructureDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrSalaryStructureDAO Initializing...");
    }

    /**
     *
     * @param compCode
     * @return
     * @throws DAOException
     */
    public List<HrSalaryStructure> getTaxableComponent(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxableComponent()");
        }
        List<HrSalaryStructure> hrSalaryStructureList = new ArrayList<HrSalaryStructure>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SALARY_STRUCTURE_DESCRIPTION_WITH_TAXABLE);
            q1.setParameter("taxable", 'Y');
            q1.setParameter("compCode", compCode);
            hrSalaryStructureList = q1.getResultList();
        } catch (PersistenceException e) {
            try {
                logger.error("Exception occured while executing method getTaxableComponent()");
                throw ExceptionTranslator.translateException(e);
            } catch (DAOException ex) {
                java.util.logging.Logger.getLogger(HrSalaryStructureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTaxableComponent()");
            throw new DAOException(e);
        }
        return hrSalaryStructureList;
    }

    /**
     *
     * @param compCode
     * @param datFrom
     * @param dateTo
     * @return
     * @throws DAOException
     */
//    public List getHrSalryStructure(int compCode, String datFrom, String dateTo) throws DAOException {
    public List getHrSalryStructure(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getHrSalryStructure()");
        }
        List salaryStructureList = new ArrayList();
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.FIND_HR_SALARY_STRUCTURE);
            q1.setParameter("compCode", compCode);
//            q1.setParameter("datFrom", datFrom);
//            q1.setParameter("dateTo", dateTo);
            salaryStructureList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getHrSalryStructure()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrSalryStructure()");
            throw new DAOException(e);
        }
        return salaryStructureList;
    }

    /**
     *
     * @param hrSalaryStructurePK
     * @return
     * @throws DAOException
     */
    public List<HrSalaryStructure> getHrSalryStructureDescription(HrSalaryStructurePK hrSalaryStructurePK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getHrSalryStructureDescription()");
        }
        List salaryStructureList = new ArrayList();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_ENTITY_FOR_SLAB_DESCRIPTION);
            q1.setParameter("compCode", hrSalaryStructurePK.getCompCode());
            q1.setParameter("purposeCode", hrSalaryStructurePK.getPurposeCode());
            q1.setParameter("nature", hrSalaryStructurePK.getNature());
//            q1.setParameter("dateFrom", hrSalaryStructurePK.getDateFrom());
//            q1.setParameter("dateTo", hrSalaryStructurePK.getDateTo());
            salaryStructureList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getHrSalryStructureDescription()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrSalryStructureDescription()");
            throw new DAOException(e);
        }
        return salaryStructureList;
    }

    /**
     *
     * @param compCode
     * @param fromDate
     * @param toDate
     * @return
     * @throws DAOException
     */
    public List<HrSalaryStructure> hrSalStructByCompAndDates(int compCode, String fromDate, String toDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("hrSalStructByCompAndDates()");
        }
        List<HrSalaryStructure> salaryStructureList = new ArrayList();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SALARYSTRUCTURE_BY_COMCODE_DATEFROM_DATETO);
            q1.setParameter("compCode", compCode);
//            q1.setParameter("dateFrom", fromDate);
//            q1.setParameter("dateTo", toDate);
            salaryStructureList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method hrSalStructByCompAndDates()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method hrSalStructByCompAndDates()");
            throw new DAOException(e);
        }
        return salaryStructureList;
    }
      
    public List<HrSalaryStructure> hrSalStructNotSaved(int compCode, String empCd) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("hrSalStructNotSaved()");
        }
        List<HrSalaryStructure> salaryStructureList = new ArrayList();
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.SALARYSTRUCTURE_NOT_SAVED);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCd", empCd);
            salaryStructureList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method hrSalStructNotSaved()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error("Exception occured while executing method hrSalStructNotSaved()");
            throw new DAOException(e);
        }
        return salaryStructureList;
    }  

    /**
     *
     * @param hrSalaryStructurePK
     * @return
     * @throws DAOException
     */
    public HrSalaryStructure hrSalStructByCompAldescAndDates(HrSalaryStructurePK hrSalaryStructurePK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("hrSalStructByCompPurposeAndDates()");
        }
        HrSalaryStructure salaryStructure = new HrSalaryStructure();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.HR_SALARYSTRUCT_BY_DATES_AND_ALDESC);
            q1.setParameter("compCode", hrSalaryStructurePK.getCompCode());
//            q1.setParameter("dateFrom", hrSalaryStructurePK.getDateFrom());
//            q1.setParameter("dateTo", hrSalaryStructurePK.getDateTo());
            q1.setParameter("alDesc", hrSalaryStructurePK.getAlDesc());
            salaryStructure = (HrSalaryStructure) q1.getSingleResult();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method hrSalStructByCompPurposeAndDates()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method hrSalStructByCompPurposeAndDates()");
            throw new DAOException(e);
        }
        return salaryStructure;
    }

    /**
     * 
     * @param hrSalaryStructurePK
     * @return
     * @throws DAOException
     */
    public List<HrSalaryStructure> getHrSalaryStructByKey(HrSalaryStructurePK hrSalaryStructurePK) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getHrSalaryStructByKey()");
        }
        List salaryStructureList = new ArrayList();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SALARY_STRUCTURE_BY_PRIMARY_KEY);
            q1.setParameter("compCode", hrSalaryStructurePK.getCompCode());
            q1.setParameter("purposeCode", hrSalaryStructurePK.getPurposeCode());
            q1.setParameter("nature", hrSalaryStructurePK.getNature());
            q1.setParameter("alDesc", hrSalaryStructurePK.getAlDesc());
//            q1.setParameter("dateFrom", hrSalaryStructurePK.getDateFrom());
//            q1.setParameter("dateTo", hrSalaryStructurePK.getDateTo());
            salaryStructureList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getHrSalaryStructByKey()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrSalaryStructByKey()");
            throw new DAOException(e);
        }
        return salaryStructureList;
    }
    
    public List maxSalaryStructShortCode(int compCode, String purCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.FIND_MAX_STRUCT_SHORT_CODE);
            query.setParameter("value1", compCode);
            query.setParameter("value2", purCode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method directRecruitmentSaveCheck()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method directRecruitmentSaveCheck()");
                throw new DAOException(e);
        }
    }
}
