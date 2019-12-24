/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.master.AbbParameterInfo;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class AbbParameterInfoDAO extends GenericDAO {

    public AbbParameterInfoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<AbbParameterInfo> getEntityByPurpose(String purpose) throws ApplicationException {
        List<AbbParameterInfo> list = new ArrayList<>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("AbbParameterInfo.findByPurpose");
            createNamedQuery.setParameter("purpose", purpose);
            list = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }
}
