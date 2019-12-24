/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.neftrtgs;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.exception.ApplicationException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class NeftFileDetailsDAO extends GenericDAO {

    public NeftFileDetailsDAO(EntityManager em) {
        super(em);
    }

    public Long getMaxSrl(Date curDate) throws ApplicationException {
        Long id = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_H2H_MAX_SR_NO);
            createNamedQuery.setParameter("curDate", curDate);
            Object obj = createNamedQuery.getSingleResult();
            id = (obj == null) ? 1 : Long.parseLong(obj.toString()) + 1;
            return id;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }

    }
}
