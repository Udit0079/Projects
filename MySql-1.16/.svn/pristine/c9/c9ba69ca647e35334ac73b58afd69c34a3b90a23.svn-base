
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CBSCustMinorInfoHis;
import com.cbs.entity.customer.CbsCustMisinfoHis;
import com.cbs.entity.customer.CbsCustNreinfoHis;
import com.cbs.entity.customer.CbsCustomerMasterDetail;
import com.cbs.entity.customer.CbsCustomerMasterDetailHis;
import com.cbs.exception.ApplicationException;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CBSCustomerMasterDetailDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSCustomerMasterDetailDAO.class);

    public CBSCustomerMasterDetailDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSCustomerMasterDetailDAO Initializing...");
    }

    public CbsCustomerMasterDetail getCustomerDetailByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCustomerDetailByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsCustomerMasterDetail.findByCustomerid");
            createNamedQuery.setParameter("customerid", custId);
            List resulList = createNamedQuery.getResultList();
            CbsCustomerMasterDetail obj = null;
            if (resulList.size() > 0) {
                obj = (CbsCustomerMasterDetail) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getCustomerDetailByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getCustomerDetailByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getCustomerDetailByCustId()");
            throw new DAOException(e);
        }
    }

    public long getMaxCustId() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getMaxCustId()");
        }
        try {
            //Query createNamedQuery = entityManager.createNamedQuery("CBSCustomerMasterDetail.getMaxCustID");
            Query createNamedQuery = entityManager.createNativeQuery("select max(cast(customerid as unsigned)) from cbs_customer_master_detail");//has to be changed later when 
            List resulList = createNamedQuery.getResultList();
            long obj = 0;
            if (resulList.size() > 0) {
                Vector vec = (Vector) resulList.get(0);
                obj = vec.get(0) != null ? Long.parseLong(vec.get(0).toString()) : 0l;
            } else {
                return 0l;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getMaxCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getMaxCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getMaxCustId()");
            throw new DAOException(e);
        }
    }

    public int getMaxTsCnt() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getMaxTsCnt()");
        }
        try {
            //Query createNamedQuery = entityManager.createNamedQuery("CBSCustomerMasterDetail.getMaxCustID");
            Query createNamedQuery = entityManager.createNativeQuery("select max(cast(tsCnt as int)) from cbs_customer_master_detail");//has to be changed later when 
            List resulList = createNamedQuery.getResultList();
            int obj = 0;
            if (resulList.size() > 0) {
                Vector vec = (Vector) resulList.get(0);
                obj = vec.get(0) != null ? Integer.parseInt(vec.get(0).toString()) : 0;
            } else {
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

    public Object getUnVerifyCustIdsByBrnCode(String brncode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getUnVerifyCustIdsByBrnCode()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_UN_VERIFY_CUST_ID);
            createNamedQuery.setParameter("primaryBrCode", brncode);
            Object obj = createNamedQuery.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getUnVerifyCustIdsByBrnCode() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getUnVerifyCustIdsByBrnCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getUnVerifyCustIdsByBrnCode()");
            throw new DAOException(e);
        }
    }

    public CbsCustomerMasterDetailHis getCustomerLastDetail(String custId, Long txnid) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CUSTOMER_LAST_CHANGE_DETAIL);
            createNamedQuery.setParameter("customerid", custId);
            createNamedQuery.setParameter("txnid", txnid);
            CbsCustomerMasterDetailHis hisObj = (CbsCustomerMasterDetailHis) createNamedQuery.getSingleResult();
            return hisObj;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public CBSCustMinorInfoHis getCustomerLastDetailForMinor(String custId, Long txnid) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CUSTOMER_LAST_CHANGE_DETAIL_FOR_MINOR);
            createNamedQuery.setParameter("customerid", custId);
            createNamedQuery.setParameter("txnid", txnid);
            CBSCustMinorInfoHis hisObj = (CBSCustMinorInfoHis) createNamedQuery.getSingleResult();
            return hisObj;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public CbsCustMisinfoHis getCustomerLastDetailForMis(String custId, Long txnid) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CUSTOMER_LAST_CHANGE_DETAIL_FOR_MIS);
            createNamedQuery.setParameter("customerid", custId);
            createNamedQuery.setParameter("txnid", txnid);
            CbsCustMisinfoHis hisObj = (CbsCustMisinfoHis) createNamedQuery.getSingleResult();
            return hisObj;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public CbsCustNreinfoHis getCustomerLastDetailForNre(String custId, Long txnid) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CUSTOMER_LAST_CHANGE_DETAIL_FOR_NRE);
            createNamedQuery.setParameter("customerid", custId);
            createNamedQuery.setParameter("txnid", txnid);
            CbsCustNreinfoHis hisObj = (CbsCustNreinfoHis) createNamedQuery.getSingleResult();
            return hisObj;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
