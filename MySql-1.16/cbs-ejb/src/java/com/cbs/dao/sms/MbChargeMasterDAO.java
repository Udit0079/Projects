/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.sms.MbChargeMaster;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class MbChargeMasterDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(MbSubscriberTabDAO.class);

    public MbChargeMasterDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("MbChargeMasterDAO Initializing...");
    }

    public long getMaxTxnId() throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TXN_ID);
            Object result = (Object) createNamedQuery.getSingleResult();
            long obj = 0;
            if (result != null) {
                obj = Long.parseLong(result.toString());
            }

            if (logger.isDebugEnabled()) {
                logger.debug("getMaxTxnId() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxTxnId()");
            throw new ApplicationException(e);
        }
    }

    public MbChargeMaster getChargePerMessage() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_BY_STATUS);
            if (createNamedQuery.getSingleResult() == null) {
                throw new ApplicationException("Please fill data in MB CHARGE MASTER.");
            }
            MbChargeMaster entity = (MbChargeMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
