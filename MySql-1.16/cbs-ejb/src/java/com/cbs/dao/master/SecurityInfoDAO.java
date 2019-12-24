/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.master.Securityinfo;

import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class SecurityInfoDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(SecurityInfoDAO.class);

    public SecurityInfoDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("SecurityInfoDAO Initializing...");
    }

    public Securityinfo findByUserId(String userId) throws ApplicationException {
        Securityinfo entity = null;
        try {
            Query query = entityManager.createNamedQuery("Securityinfo.findByUserId");
            entity = (Securityinfo) query.setParameter("userId", userId).getSingleResult();

        } catch (NoResultException e) {
            throw new ApplicationException("Invalid loged in User");
        }
        return entity;
    }
}
