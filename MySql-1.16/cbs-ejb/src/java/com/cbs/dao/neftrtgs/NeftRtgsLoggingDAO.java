/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.neftrtgs;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.neftrtgs.NeftRtgsLogging;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class NeftRtgsLoggingDAO extends GenericDAO {

    public NeftRtgsLoggingDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public long getMaxTxnId() throws ApplicationException {
        long id = 0;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TXN_ID_NEFT_RTGS_LOGGING);
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                id = 1;
            } else {
                id = Long.parseLong(obj.toString()) + 1;
            }
            return id;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<NeftRtgsLogging>findByUtrNo(String utrNo) throws ApplicationException{
         List<NeftRtgsLogging> entityList = new ArrayList<NeftRtgsLogging>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("NeftRtgsLogging.findByUtr");
            createNamedQuery.setParameter("utr", utrNo);
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return entityList;
    }
    
    public List<NeftRtgsLogging> getAllLoggingEntity(Date currentDt) throws ApplicationException {
        List<NeftRtgsLogging> entityList = new ArrayList<NeftRtgsLogging>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("NeftRtgsLogging.findByDt");
            createNamedQuery.setParameter("dt", currentDt);
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return entityList;
    }
}
