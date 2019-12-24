/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrPersonnelLang;
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
public class HrPersonnelLangDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrPersonnelLangDAO.class);

    /**
     * @param entityManager
     */
    public HrPersonnelLangDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPersonnelLangDAO Initializing...");
    }

    public List<HrPersonnelLang> getLangData(long empCode) throws DAOException {
         if (logger.isDebugEnabled()) {
            logger.debug("findEmpByEmpCode()");
        }
        List<HrPersonnelLang> hrPersonnelLangList = new ArrayList<HrPersonnelLang>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrPersonnelLang_BY_EMPCODE);
            q1.setParameter("empCode", empCode);
            hrPersonnelLangList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getLangData() - end - return value=" + hrPersonnelLangList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLangData()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLangData()");
            throw new DAOException(e);
        }
        return hrPersonnelLangList;
    }
}
