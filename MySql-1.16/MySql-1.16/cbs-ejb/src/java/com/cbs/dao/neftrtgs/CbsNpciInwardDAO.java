/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.neftrtgs;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.neftrtgs.CbsNpciInward;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CbsNpciInwardDAO extends GenericDAO {

    public CbsNpciInwardDAO(EntityManager em) {
        super(em);
    }

    public List<CbsNpciInward> findByUserCreditReferenceAndIwType(String usrCrRef, String fileType) throws ApplicationException {
        List<CbsNpciInward> returnList = new ArrayList<CbsNpciInward>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_BY_UCR_AND_IW_TYPE);
            createNamedQuery.setParameter("ucr", usrCrRef);
            createNamedQuery.setParameter("iwType", fileType);
            returnList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }
}
