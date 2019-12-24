/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrPersonnelHobby;
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
public class HrPersonnelHobbyDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrPersonnelHobbyDAO.class);

    /**
     * @param entityManager
     */
    public HrPersonnelHobbyDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPersonnelHobbyDAO Initializing...");
    }

    public List<HrPersonnelHobby> getHobbyData(long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByEmpCode()");
        }
        List<HrPersonnelHobby> hrPersonnelHobbyList = new ArrayList<HrPersonnelHobby>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrPersonnelHobby_BY_EMPCODE);
            q1.setParameter("empCode", empCode);
            hrPersonnelHobbyList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getHobbyData() - end - return value=" + hrPersonnelHobbyList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getHobbyData()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHobbyData()");
            throw new DAOException(e);
        }
        return hrPersonnelHobbyList;
    }
}
