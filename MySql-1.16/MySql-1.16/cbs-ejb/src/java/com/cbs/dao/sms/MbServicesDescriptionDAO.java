/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.sms.MbServicesDescription;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class MbServicesDescriptionDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(MbServicesDescriptionDAO.class);

    public MbServicesDescriptionDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("MbSubscriberTabDAO Initializing...");
    }

    public MbServicesDescription getEntityByServiceCode(Integer code) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityByServiceCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbServicesDescription.findByServiceCode");
            createNamedQuery.setParameter("serviceCode", code);
            MbServicesDescription obj = (MbServicesDescription) createNamedQuery.getSingleResult();

            if (logger.isDebugEnabled()) {
                logger.debug("getEntityByServiceCode() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEntityByServiceCode()");
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public MbServicesDescription getEntityByServiceName(String name) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityByServiceCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbServicesDescription.findByServiceName");
            createNamedQuery.setParameter("serviceName", name);
            MbServicesDescription obj = (MbServicesDescription) createNamedQuery.getSingleResult();

            if (logger.isDebugEnabled()) {
                logger.debug("getEntityByServiceCode() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEntityByServiceCode()");
            throw new ApplicationException(e.getMessage());
        }
    }
}
