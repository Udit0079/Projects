
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class GlTableDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(GlTableDAO.class);

    public GlTableDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("GlTableDAO Initializing...");
    }

    public Gltable findByPK(String acNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("Gltable.findByAcNo");
            createNamedQuery.setParameter("acNo", acNo);
            Gltable entity = (Gltable) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public Gltable findByMsgflag(int msgFlag) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("Gltable.findByMsgflag");
            createNamedQuery.setParameter("msgflag", msgFlag);
            Gltable entity = (Gltable) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
