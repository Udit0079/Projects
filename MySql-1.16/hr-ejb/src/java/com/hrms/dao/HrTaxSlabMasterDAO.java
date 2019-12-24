/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;


import com.hrms.common.exception.ApplicationException;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrTaxSlabMaster;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Ankit Verma
 */
public class HrTaxSlabMasterDAO extends GenericDAO{
     private static final Logger logger = Logger.getLogger(HrTaxSlabMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrTaxSlabMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrTaxSlabMasterDAO Initializing...");
    }
   
     public String getMaxTaxSlabCode(int compCode) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getMaxTaxSlabCode()");
        }
        String max = "0";
        try {
            Query q1 = entityManager.createNamedQuery("HrTaxSlabMaster.findMaxSlabCodeByCompCode");
             q1.setParameter("compCode", compCode);
            if (q1.getSingleResult() != null) {
                max = q1.getSingleResult().toString();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getMaxTaxSlabCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxTaxSlabCode()");
            throw new DAOException(e);
        }
        return max;
    }
     public List<HrTaxSlabMaster> getTaxSlabMasterDetails(int compCode) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxSlabMasterDetails()");
        }
        List<HrTaxSlabMaster> slabMastersEntityList=new ArrayList<HrTaxSlabMaster>();
        try {
            Query q1 = entityManager.createNamedQuery("HrTaxSlabMaster.findByCompCode");
            slabMastersEntityList = q1.setParameter("compCode", compCode).getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getTaxSlabMasterDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTaxSlabMasterDetails()");
            throw new DAOException(e);
        }
        return slabMastersEntityList;
    } 
     
     
     
    
}
