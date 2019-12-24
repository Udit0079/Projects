/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrMstStruct;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan waris
 */
public class HrMstStructDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMstStructDAO.class);

    /**
     * @param entityManager
     */
    public HrMstStructDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("PayrollCalanderDAO Initializing...");
    }

    public List<HrMstStruct> findByStructCode(int compCode, String structCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByStructCode()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.STRUCT_FIND_BY_STRUCT_CODE);
            query.setParameter("compCode", compCode);
            query.setParameter("structCode", structCode);
            List<HrMstStruct> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByStructCode() - end - return value=" + resultList);
            }
            if (!resultList.isEmpty()) {
                return resultList;
            } else {
                return new ArrayList<HrMstStruct>();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }

    public List directRecruitmentZoneList(int compCode, String zone) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_ZONE_LIST);
            query.setParameter("value1", compCode);
            query.setParameter("value2", zone);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method directRecruitmentZoneList()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentZoneList()");
            throw new DAOException(e);
        }
    }

    public HrMstStruct deleteParameter(int compCode, String structCode) throws DAOException {
        HrMstStruct instance = new HrMstStruct();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PARAMETER_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", structCode);
            instance = (HrMstStruct) query.getSingleResult();
            return instance;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method deleteParameter()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteParameter()");
            throw new DAOException(e);
        }
    }

    public List getGradeAnddesignationList(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByStructCode()");
        }
        try {
            String str = "SELECT distinct A.gradeCode, B.description, A.hrMstDesgPK.desgCode, C.description" +
                    " FROM HrMstDesg A,HrMstStruct B,HrMstStruct C" +
                    " WHERE A.hrMstDesgPK.compCode= :compCode AND" +
                    " A.hrMstDesgPK.compCode=B.hrMstStructPK.compCode AND" +
                    " A.hrMstDesgPK.compCode=C.hrMstStructPK.compCode AND" +
                    " A.gradeCode=B.hrMstStructPK.structCode AND" +
                    " A.hrMstDesgPK.desgCode=C.hrMstStructPK.structCode" +
                    " ORDER BY A.gradeCode";
            Query query = entityManager.createQuery(str);
            query.setParameter("compCode", compCode);
            List resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("getGradeAnddesignationList() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getGradeAnddesignationList()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getGradeAnddesignationList()");
            throw new DAOException(e);
        }
    }
}
