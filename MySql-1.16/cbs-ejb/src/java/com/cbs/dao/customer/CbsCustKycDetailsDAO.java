/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CbsCustKycDetails;

import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class CbsCustKycDetailsDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(CbsCustKycDetailsDAO.class);

    public CbsCustKycDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsCustKycDetailsDAO Initializing...");
    }

    public CbsCustKycDetails getCbsCustKycDetailsByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCbsCustKycDetailsByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsCustKycDetails.findByTxnId");
            createNamedQuery.setParameter("txnId", getMaxTxnId("cbs_cust_kyc_details", custId));
            List resulList = createNamedQuery.getResultList();
            CbsCustKycDetails obj = null;
            if (resulList.size() > 0) {
                obj = (CbsCustKycDetails) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getCbsCustKycDetailsByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getCbsCustKycDetailsByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCbsCustKycDetailsByCustId()");
            throw new DAOException(e);
        }
    }

    public int getMaxTxnId(String tableName, String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getMaxTxnId()");
        }
        try {
            //Query createNamedQuery = entityManager.createNamedQuery("CbsCustomerMasterDetail.getMaxCustID");
            Query createNamedQuery = entityManager.createNativeQuery("select max(TxnId) from " + tableName + " where customerid=?");//has to be changed later when 
            createNamedQuery.setParameter(1, custId);
            List resulList = createNamedQuery.getResultList();
            int obj = 0;
            if (resulList.size() > 0) {
                Vector vec = (Vector) resulList.get(0);
                obj = vec.get(0) != null ? Integer.parseInt(vec.get(0).toString()) : 0;
            } else {
                return 0;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getMaxTxnId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getMaxTxnId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getMaxTxnId()");
            throw new DAOException(e);
        }
    }
}
