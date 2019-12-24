/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrOrgChart;
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
public class HrOrgChartDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrOrgChartDAO.class);

    /**
     * @param entityManager
     */
    public HrOrgChartDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrOrgChartDAO Initializing...");
    }

    public List reportingStructureEditDetail(int compcode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.REPORTING_STRUCTURE_EDIT_DETAIL);
            query.setParameter("value1", compcode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method reportingStructureEditDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method reportingStructureEditDetail()");
            throw new DAOException(e);
        }
    }

    public List reportingStructurePrimrycheck(int compcode, String contCode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.REPORTING_DETAILS_DELETE_QUERY);
            query.setParameter("value1", compcode);
            query.setParameter("value2", contCode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method reportingStructurePrimrycheck()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method reportingStructurePrimrycheck()");
            throw new DAOException(e);
        }
    }

    public HrOrgChart deleteReportingDetails(int compCode, String contCode) throws DAOException {
        HrOrgChart instance = new HrOrgChart();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.REPORTING_DETAILS_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", contCode);
            instance = (HrOrgChart) query.getSingleResult();
            return instance;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method deleteContractoerDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteContractoerDetails()");
            throw new DAOException(e);
        }
    }

     public List viewOrgDetail(int compcode,String dept,String grade) throws DAOException{
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.VIEW_ORG_DETAIL);
            query.setParameter("value1", compcode);
            query.setParameter("value2", dept);
            query.setParameter("value3", grade);
            preList = query.getResultList();
            return preList;
        } 
        catch (PersistenceException e) {
            logger.error("Exception occured while executing method reportingStructureEditDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method reportingStructureEditDetail()");
            throw new DAOException(e);
        }
    }
}
