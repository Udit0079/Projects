/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.to.HrApprisalDetailsTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrApprisalDetails;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author admin
 */
public class HrApprisalDetailsDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrApprisalDetailsDAO.class);

    /**
     * @param entityManager
     */
    public HrApprisalDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("AdminRoleFormsDAO Initializing...");
    }

    public List viewAppraisalData(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("viewAppraisalData()");
        }
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.APPRIASAL_DATA_VIEW);
            query.setParameter("compCode", compCode);
            List data = query.getResultList();
            return data;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findhrLeavePosting()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findhrLeavePosting()");
            throw new DAOException(e);
        }
    }

    public Object checkData(HrApprisalDetailsTO to) {
        Query query = entityManager.createNamedQuery(NamedQueryConstant.FIND_BY_PRIMARY_KEY);
        query.setParameter("compCode", to.getHrApprisalDetailsPKTO().getCompCode());
        query.setParameter("empCode", to.getHrApprisalDetailsPKTO().getEmpCode());
        query.setParameter("appraisalDt", to.getHrApprisalDetailsPKTO().getAppraisalDt());
        Object result = query.getSingleResult();
        return result;
    }
}
