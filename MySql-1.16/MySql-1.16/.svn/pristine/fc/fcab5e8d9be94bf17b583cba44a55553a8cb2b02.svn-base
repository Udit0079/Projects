/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.customer;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.customer.TdCustomermaster;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class TdCustomermasterDAO extends GenericDAO {

    public TdCustomermasterDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public TdCustomermaster getTDCustomerMaster(String custNo, String actype, String brncode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_TD_CUSTOMER_MASTER_BY_PK);
            createNamedQuery.setParameter("custNo", custNo);
            createNamedQuery.setParameter("actype", actype);
            createNamedQuery.setParameter("brncode", brncode);
            TdCustomermaster tdCustomerMaster = (TdCustomermaster) createNamedQuery.getSingleResult();
            return tdCustomerMaster;
        } catch (Exception ex) {
            System.out.println("Cause is:: " + ex.getMessage());
            if (ex.getMessage().equals("getSingleResult() did not retrieve any entities.")) {
                throw new ApplicationException("There is no data for:: " + brncode + actype + custNo + "01");
            }
            throw new ApplicationException(ex);
        }
    }
}
