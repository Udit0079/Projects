/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.neftrtgs.AccountMaster;
import com.cbs.entity.neftrtgs.TDAccountMaster;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class AccountMasterDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(AccountMasterDAO.class);

    public AccountMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("AccountMasterDAO Initializing...");
    }

    public AccountMaster getEntityByAcno(String acno) throws ApplicationException {

        if (logger.isDebugEnabled()) {
            logger.debug("getEntityByAcno()");
        }
//        String accNature = acno.substring(2, 4);
        AccountMaster obj = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("AccountMaster.findByACNo");
            createNamedQuery.setParameter("aCNo", acno);
            List resulList = createNamedQuery.getResultList();
            if (resulList.size() > 0) {
                obj = (AccountMaster) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findEntityEvent() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }
    public TDAccountMaster getEntityByTDAcno(String acno) throws ApplicationException {

        if (logger.isDebugEnabled()) {
            logger.debug("getEntityByTDAcno()");
        }
        TDAccountMaster obj = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("TDAccountMaster.findByAcno");
            createNamedQuery.setParameter("acno", acno);
            List resulList = createNamedQuery.getResultList();
            if (resulList.size() > 0) {
                obj = (TDAccountMaster) resulList.get(0);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("findEntityEvent() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }
}
