
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.customer.Customerid;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CustomerIdDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CustomerIdDAO.class);

    public CustomerIdDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CustomerIdDAO Initializing...");
    }

    public List<Customerid> getRowByCustId(long custId) throws ApplicationException {
        List<Customerid> list = new ArrayList<Customerid>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("Customerid.findByCustId");
            createNamedQuery.setParameter("custId", custId);
            list = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }
}
