/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CbsCustAdditionalAddressDetails;
//import com.cbs.entity.customer.CbsCustIdentityDetails;
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
public class CbsCustAdditionalAddressDetailsDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(CbsCustAdditionalAddressDetails.class);

    public CbsCustAdditionalAddressDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsCustAdditionalAddressDetailsDAO Initializing...");
    }

    public List<CbsCustAdditionalAddressDetails> getCustAddressDetailsByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCustAddressDetailsByCustId()");
        }
        List<CbsCustAdditionalAddressDetails> objList = new ArrayList<CbsCustAdditionalAddressDetails>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsCustAdditionalAddressDetails.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            if (resulList.size() > 0) {
                for (int i = 0; i < resulList.size(); i++) {
                    objList.add((CbsCustAdditionalAddressDetails) resulList.get(i));
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getCustAddressDetailsByCustId() - end - return value=" + objList);
            }
            return objList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getCustAddressDetailsByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCustAddressDetailsByCustId()");
            throw new DAOException(e);
        }
    }
}
