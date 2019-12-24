/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class MbSmsSenderBankDetailDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(MbSmsSenderBankDetailDAO.class);

    public MbSmsSenderBankDetailDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("MbSubscriberTabDAO Initializing...");
    }

    public List<MbSmsSenderBankDetail> getBankAndSenderDetail() throws ApplicationException {
        List<MbSmsSenderBankDetail> resultList = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbSmsSenderBankDetail.findAll");
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getBankAndSenderDetail() Method " + ex.getMessage());
        }
        return resultList;
    }
}
