/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrPersonnelTransport;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class HrPersonnelTransportDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrPersonnelTransportDAO.class);

    /**
     * @param entityManager
     */
    public HrPersonnelTransportDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPersonnelTransportDAO Initializing...");
    }

    public List<HrPersonnelTransport> getTransportData(long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTransportData()");
        }
        List<HrPersonnelTransport> hrPersonnelTransportList = new ArrayList<HrPersonnelTransport>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrPersonnelTransport_BY_EMPCODE);
            q1.setParameter("empCode", empCode);
            hrPersonnelTransportList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getTransportData() - end - return value=" + hrPersonnelTransportList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getTransportData()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTransportData()");
            throw new DAOException(e);
        }
        return hrPersonnelTransportList;
    }
}
