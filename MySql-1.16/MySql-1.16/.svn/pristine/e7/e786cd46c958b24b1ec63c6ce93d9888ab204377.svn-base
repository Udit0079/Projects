/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrMstBus;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class HrMstBusDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMstBusDAO.class);

    /**
     * @param entityManager
     */
    public HrMstBusDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstBusDAO Initializing...");
    }

    public List<HrMstBus> getAllByBusNo(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByBusNo()");
        }
        List<HrMstBus> hrMstBusList = null;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HR_MST_BUS_BY_COMPCODE);
            q1.setParameter("compCode", compCode);
            hrMstBusList = q1.getResultList();

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrMstBusList;
    }
}
