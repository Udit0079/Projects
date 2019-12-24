/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.sms.MbPullMsgTab;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class MbPullMsgTabDAO extends GenericDAO {

    public MbPullMsgTabDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Long getMaxTxnId() throws ApplicationException {
        Long id = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_MAX_TXN_ID_MB_PULL_MSG_TAB);
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                id = new Long(1);
            } else {
                id = new Long(Long.parseLong(obj.toString())) + new Long(1);
            }
        } catch (Exception ex) {
            System.out.println("Problem In getMaxTxnId() Method " + ex.getMessage());
        }
        return id;
    }

    public MbPullMsgTab getEntityByTxnId(long txnId) throws ApplicationException {
        MbPullMsgTab entity = new MbPullMsgTab();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbPullMsgTab.findByTxnId");
            createNamedQuery.setParameter("txnId", txnId);
            entity = (MbPullMsgTab) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getEntityByTxnId() Method " + ex.getMessage());
        }
        return entity;
    }
}
