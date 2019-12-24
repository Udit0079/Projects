/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.master.CbsChargeDetail;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class CbsChargeDetailDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(CbsChargeDetailDAO.class);

    public CbsChargeDetailDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("CbsChargeDetailDAO Initializing...");
    }

    public List<CbsChargeDetail> getCbsChargeDetail(String chargeType) throws ApplicationException {
        List<CbsChargeDetail> resultList = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsChargeDetail.findByChargeType");
            createNamedQuery.setParameter("chargeType", chargeType);
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getCbsChargeDetail() Method " + ex.getMessage());
        }
        return resultList;
    }

    public List<CbsChargeDetail> getCbsChargeDetailByTypeNameAndActype(String chargeType, String chargeName, String acType) throws ApplicationException {
        List<CbsChargeDetail> resultList = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CBS_CHARGE_DETAIL_BY_TYPE_NAME_AND_ACTYPE);
            createNamedQuery.setParameter("chargeType", chargeType);
            createNamedQuery.setParameter("chargeName", chargeName);
            createNamedQuery.setParameter("acType", acType);
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getCbsChargeDetail() Method " + ex.getMessage());
        }
        return resultList;
    }
}