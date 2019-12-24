
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.entity.customer.CbsCustNreinfo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CBSCustNREInfoDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CBSCustNREInfoDAO.class);

    public CBSCustNREInfoDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CBSCustNREInfoDAO Initializing...");
    }

    public CbsCustNreinfo getCustNreDetailsByCustId(String custId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCustNreDetailsByCustId()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsCustNreinfo.findByCustomerId");
            createNamedQuery.setParameter("customerId", custId);
            List resulList = createNamedQuery.getResultList();
            CbsCustNreinfo obj = null;
            if (resulList.size() > 0) {
                obj = (CbsCustNreinfo) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getCustNreDetailsByCustId() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getCustNreDetailsByCustId()");
            return null;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCustNreDetailsByCustId()");
            throw new DAOException(e);
        }
    }
}
