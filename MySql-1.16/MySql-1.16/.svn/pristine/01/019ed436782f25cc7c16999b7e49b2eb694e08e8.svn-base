/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CbsCustIdentityDetails;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class CBSCustIdentityDetailsDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(CBSCustIdentityDetailsDAO.class);

    public CBSCustIdentityDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSCustIdentityDetailsDAO Initializing...");
    }

    public List<CbsCustIdentityDetails> getCustIdentityDetailsCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCustIdentityDetailsCustId()");
        }
        List<CbsCustIdentityDetails> objList = new ArrayList<CbsCustIdentityDetails>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsCustIdentityDetails.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            if (resulList.size() > 0) {
                for (int i = 0; i < resulList.size(); i++) {
                    objList.add((CbsCustIdentityDetails) resulList.get(i));
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getCustIdentityDetailsCustId() - end - return value=" + objList);
            }
            return objList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getCustIdentityDetailsCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCustIdentityDetailsCustId()");
            throw new DAOException(e);
        }
    }
}
