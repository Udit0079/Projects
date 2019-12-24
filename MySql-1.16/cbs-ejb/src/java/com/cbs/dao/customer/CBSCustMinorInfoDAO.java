
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CBSCustMinorInfo;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CBSCustMinorInfoDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSCustMinorInfoDAO.class);

    public CBSCustMinorInfoDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSCustMinorInfoDAO Initializing...");
    }

    public CBSCustMinorInfo getCustMinorDetailsByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCustMinorDetailsByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CBSCustMinorInfo.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            CBSCustMinorInfo obj = null;
            if (resulList.size() > 0) {
                obj = (CBSCustMinorInfo) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getCustMinorDetailsByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getCustMinorDetailsByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCustMinorDetailsByCustId()");
            throw new DAOException(e);
        }
    }
     public int getMaxTsCnt()throws DAOException
    {
         if (logger.isDebugEnabled()) {
            logger.debug("getMaxTsCnt()");
        }
        try {
            //Query createNamedQuery = entityManager.createNamedQuery("CbsCustomerMasterDetail.getMaxCustID");
            Query createNamedQuery=entityManager.createNativeQuery("select max(cast(tsCnt as int)) from CBS_Cust_MinorInfo");//has to be changed later when 
            List resulList = createNamedQuery.getResultList();
            int obj = 0;
            if (resulList.size() > 0) {
                Vector vec=(Vector)resulList.get(0);
                obj = vec.get(0)!=null?Integer.parseInt(vec.get(0).toString()):0;
            }
            else
            {
                return 0;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getMaxTsCnt() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getMaxTsCnt()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getMaxTsCnt()");
            throw new DAOException(e);
        }
    }
}
