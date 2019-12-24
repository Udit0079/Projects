
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.master.Bnkadd;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class BnkAddDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(BnkAddDAO.class);

    public BnkAddDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("BnkAddDAO Initializing...");
    }

    public Object getBankAddress(String brCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getBankAddress()");
        }
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.BANK_ADDRESS_BY_BR_CODE);
            createNamedQuery.setParameter("brnCode", brCode);

            Object bankAdd = createNamedQuery.getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("getBankAddress() - end - return value=" + bankAdd);
            }
            return bankAdd;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getBankAddress()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getBankAddress()");
            throw new DAOException(e);
        }
    }

    public Bnkadd bankNameByBnkAdd(String alphacode) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("bankNameByBnkAdd()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("Bnkadd.findByAlphacode");
            createNamedQuery.setParameter("alphacode", alphacode);
            Bnkadd obj = (Bnkadd) createNamedQuery.getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("bankNameByBnkAdd() - end - return value=" + obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method bankNameByBnkAdd()");
            throw new ApplicationException(e);
        }
    }
}
