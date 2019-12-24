/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrDirectRec;
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
public class HrDirectRecDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrDirectRecDAO.class);
    /**
     * @param entityManager
     */
    public HrDirectRecDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrDirectRecDAO Initializing...");
    }

     public List directRecruitmentInterviewerViewDetail(int compcode ,String candidateName) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_INTERVIEW_VIEW_DETAIL);
             query.setParameter("value1", candidateName+"%");
            query.setParameter("value2", compcode);
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
      public List directRecruitmentUpdateDetail(int compcode ,String arNum) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_UPDATE_DETAIL);
            query.setParameter("value1", compcode);
            query.setParameter("value2", arNum);
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

     public List directRecruitmentSaveCheck(int compcode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_SAVE_CHECK);
            query.setParameter("value2", compcode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method directRecruitmentSaveCheck()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method directRecruitmentSaveCheck()");
                throw new DAOException(e);
        }
    }

     public List directRecruitmentSaveValid() throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_SAVE_VALID);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method directRecruitmentSaveValid()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method directRecruitmentSaveValid()");
                throw new DAOException(e);
        }
    }

      public List directRecruitmentSuperCode(int compcode,String superId) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_SAVE_SUPERCODE);
            query.setParameter("value1", compcode);
            query.setParameter("value2", superId);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method directRecruitmentSuperCode()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method directRecruitmentSuperCode()");
                throw new DAOException(e);
        }
    }

       public HrDirectRec directRecruitmentUpdateCheck(int compCode, String arNo) throws DAOException {
        HrDirectRec instance = new HrDirectRec();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_UPDATE_CHECK);
            query.setParameter("value1", compCode);
            query.setParameter("value2", arNo);
            instance = (HrDirectRec)query.getSingleResult();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method directRecruitmentUpdateCheck()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method directRecruitmentUpdateCheck()");
                throw new DAOException(e);
        }
    }

}
