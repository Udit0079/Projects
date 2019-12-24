/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrMstRoute;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class HrMstRouteDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMstRouteDAO.class);

    /**
     * @param entityManager
     */
    public HrMstRouteDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstRouteDAO Initializing...");
    }

    public List<HrMstRoute> getAllByRouteNo(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByRouteNo()");
        }
        List<HrMstRoute> hrMstRouteList = null;
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HR_MST_ROUTE_BY_COMPCODE);
            q1.setParameter("compCode", compCode);
            hrMstRouteList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
        return hrMstRouteList;
    }
}
