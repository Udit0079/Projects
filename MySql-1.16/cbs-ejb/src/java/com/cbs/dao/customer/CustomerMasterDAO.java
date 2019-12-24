
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.customer.Customermaster;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CustomerMasterDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CustomerMasterDAO.class);

    public CustomerMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CustomerMasterDAO Initializing...");
    }

    public Customermaster getCustomerMaster(String custno, String actype, String agcode, String brncode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CUSTOMER_MASTER_BY_PK);
            createNamedQuery.setParameter("custno", custno);
            createNamedQuery.setParameter("actype", actype);
            createNamedQuery.setParameter("agcode", agcode);
            createNamedQuery.setParameter("brncode", brncode);
            Customermaster customerMaster = (Customermaster) createNamedQuery.getSingleResult();
            return customerMaster;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
