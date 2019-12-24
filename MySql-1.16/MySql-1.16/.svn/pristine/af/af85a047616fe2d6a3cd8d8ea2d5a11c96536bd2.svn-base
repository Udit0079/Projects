/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.neftrtgs;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.neftrtgs.CbsNpciInwardNonAadhaar;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CbsNpciInwardNonAadhaarDAO extends GenericDAO {

    public CbsNpciInwardNonAadhaarDAO(EntityManager em) {
        super(em);
    }

    public List<CbsNpciInwardNonAadhaar> findByRrn(String rrn) throws ApplicationException {
        List<CbsNpciInwardNonAadhaar> returnList = new ArrayList<CbsNpciInwardNonAadhaar>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsNpciInwardNonAadhaar.findByRrn");
            createNamedQuery.setParameter("rrn", rrn);
            returnList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    public List<CbsNpciInwardNonAadhaar> duplicateSeqOnDate(String originatorCode, Date fileComingDt,
            String fileSeqNo) throws ApplicationException {
        List<CbsNpciInwardNonAadhaar> returnList = new ArrayList<CbsNpciInwardNonAadhaar>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.DUPLICATE_DATE_SEQ_FILE);
            createNamedQuery.setParameter("originatorCode", originatorCode);
            createNamedQuery.setParameter("fileComingDt", fileComingDt);
            createNamedQuery.setParameter("fileSeqNo", fileSeqNo);
            returnList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }
}
