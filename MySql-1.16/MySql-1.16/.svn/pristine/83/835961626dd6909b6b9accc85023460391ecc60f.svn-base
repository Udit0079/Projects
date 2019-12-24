/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrDatabank;
import com.hrms.entity.hr.HrDirectRec;
import com.hrms.entity.hr.HrManpower;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrManpowerDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrManpowerDAO.class);
    /**
     * @param entityManager
     */
    public HrManpowerDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrManpowerDAO Initializing...");
    }

     public List manpowerDetail(int compCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.MANPOWER_DETAIL);
            query.setParameter("value1", compCode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method directRecruitmentInterviewerViewDetail()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method directRecruitmentInterviewerViewDetail()");
                throw new DAOException(e);
        }
     }

      public HrManpower manpowerDeleteAction(int compCode, int year,String month,String zone,String deptCode) throws DAOException {
        HrManpower instance = new HrManpower();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.MANPOWER_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", year);
            query.setParameter("value3", month);
            query.setParameter("value4", zone);
            query.setParameter("value5", deptCode);
            instance = (HrManpower)query.getSingleResult();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method manpowerDeleteAction()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method manpowerDeleteAction()");
                throw new DAOException(e);
        }
    }

       public List manpowerGradeCode(int compCode,String desgCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.MANPOWER_STRUCT_CODE);
            query.setParameter("value1", compCode);
            query.setParameter("value2", desgCode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method manpowerGradeCode()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method manpowerGradeCode()");
                throw new DAOException(e);
        }
     }

      public List manpowerEmployeeNo(int compCode,String zones,String deptCode,String desgCode,String gradeCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.MANPOWER_EMPLOYEE_NO);
            query.setParameter("value1", compCode);
            query.setParameter("value2", zones);
            query.setParameter("value3", deptCode);
            query.setParameter("value4", desgCode);
            query.setParameter("value5", gradeCode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method manpowerEmployeeNo()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method manpowerEmployeeNo()");
                throw new DAOException(e);
        }
     }

    public String manpowerUpdate(HrManpower entity) throws DAOException {
        String msg = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.MANPOWER_UPDATE_ACTION);
            query.setParameter("qualVal1", entity.getQualcode1());
            query.setParameter("qualVal2", entity.getQualCode2());
            query.setParameter("qualVal3", entity.getQualCode3());
            query.setParameter("specialCodeVal", entity.getSpecialCode());
            query.setParameter("desgcodeVal", entity.getDesgCode());
            query.setParameter("minExpVal", entity.getMinmExp());
            query.setParameter("prefExpVal", entity.getPrefExp());
            query.setParameter("reqExpVal", entity.getRequExp());
            query.setParameter("autoExpVal", entity.getAutoExp());
            query.setParameter("posfillVal", entity.getPosFill());
            query.setParameter("posReqVal", entity.getPosRequ());
            query.setParameter("posSancVal", entity.getPosSanc());
            query.setParameter("skillCodeVal", entity.getSkillCode());
            query.setParameter("statFlagVal", entity.getStatFlag());
            query.setParameter("statUpflagVal", entity.getStatUpFlag());
            query.setParameter("modDtVal", entity.getModDate());
            query.setParameter("authbyVal", entity.getAuthBy());
            query.setParameter("enterbyVal", entity.getEnteredBy());
            query.setParameter("compCodeVal", entity.getHrManpowerPK().getCompCode());
            query.setParameter("monthVal", entity.getHrManpowerPK().getMonth());
            query.setParameter("yearVal", entity.getHrManpowerPK().getYear());
            query.setParameter("zoneVal", entity.getHrManpowerPK().getZone());
            query.setParameter("deptcodeVal", entity.getHrManpowerPK().getDeptCode());
            query.executeUpdate();
            msg="true";
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method manpowerUpdate()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method manpowerUpdate()");
                throw new DAOException(e);
        }
        return msg;
    }

}