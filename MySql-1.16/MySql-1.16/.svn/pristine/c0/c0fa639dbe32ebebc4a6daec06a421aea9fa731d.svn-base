
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.customer.CBSCustDeliveryChannelDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CBSCustDeliveryChannelDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSCustDeliveryChannelDetailsDAO.class);

    public CBSCustDeliveryChannelDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSCustDeliveryChannelDetailsDAO Initializing...");
    }
     public CBSCustDeliveryChannelDetails getCusDeliveryChannelDetailsByCustId(String custId)throws DAOException
    {
          if (logger.isDebugEnabled()) {
            logger.debug("getCusDeliveryChannelDetailsByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CBSCustDeliveryChannelDetails.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            CBSCustDeliveryChannelDetails obj = null;
            if (resulList.size() > 0) {
                obj = (CBSCustDeliveryChannelDetails) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getCusDeliveryChannelDetailsByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getCusDeliveryChannelDetailsByCustId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCusDeliveryChannelDetailsByCustId()");
            throw new DAOException(e);
        }
    }
}

