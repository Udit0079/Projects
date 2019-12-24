/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.common.exception.ApplicationException;
import com.hrms.entity.hr.HrDataPrevComp;
import com.hrms.entity.hr.HrDataQual;
import com.hrms.entity.hr.HrDataReference;
import com.hrms.entity.hr.HrDatabank;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrDatabankDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrDatabankDAO.class);
    /**
     * @param entityManager
     */
    public HrDatabankDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrDatabankDAO Initializing...");
    }

     public List<HrDatabank> viewDetails(int compCode,char calint) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByStructCode()");
        }
        try {
            Query query=entityManager.createNamedQuery(NamedQueryConstant.DATABANK_FIND_BY_COMPANY_CODE);
            query.setParameter("value1", compCode);
            query.setParameter("value2", calint);
            List<HrDatabank> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByStructCode() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method findByCompCode()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method findByCompCode()");
                throw new DAOException(e);
        }
    }


       public List extensionOfAppointmentAdviceSearch(int compCode, String candidateCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.EXTENSIN_OF_APPOINTMENT_SEARCH);
            query.setParameter("value1", compCode);
            query.setParameter("value2",candidateCode+"%");
            query.setParameter("value3",'S');
            query.setParameter("value4",'N');
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method extensionOfAppointmentAdviceSearch()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method extensionOfAppointmentAdviceSearch()");
                throw new DAOException(e);
        }
    }

        public List extensionOfAppointmentAdviceEditList(int compCode, String intCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.EXTENSIN_OF_APPOINTMENT_EDIT_DETAIL);
            query.setParameter("value1", compCode);
            query.setParameter("value2",intCode+"%");
            query.setParameter("value3",'S');
            query.setParameter("value4",'N');
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method extensionOfAppointmentAdviceEditList()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method extensionOfAppointmentAdviceEditList()");
                throw new DAOException(e);
        }
    }


       public List<HrDatabank> creationOfDatabankViewDeatails(int compCode,String candId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByStructCode()");
        }
        try {
            Query query=entityManager.createNamedQuery(NamedQueryConstant.CREATION_OF_DATABANK_VIEW);
            query.setParameter("value1", compCode);
            query.setParameter("value2", candId+"%");
            List<HrDatabank> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByStructCode() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method creationOfDatabankViewDeatails()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method creationOfDatabankViewDeatails()");
                throw new DAOException(e);
        }
    }

       public List creationOfDatabankConsultantList(int compCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATION_OF_DATABANK_CONSULTANT_LIST);
            query.setParameter("value1", compCode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method creationOfDatabankConsultantList()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method creationOfDatabankConsultantList()");
                throw new DAOException(e);
        }
    }

       public List extensionOfAppointmentReferanceDetails(int compCode, String candSRno,String advtCode,String jobCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATION_OF_DATABANK_REFERANCE_DETAILS);
            query.setParameter("value1", compCode);
            query.setParameter("value2", candSRno);
            query.setParameter("value3", advtCode);
            query.setParameter("value4", jobCode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method extensionOfAppointmentReferanceDetails()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method extensionOfAppointmentReferanceDetails()");
                throw new DAOException(e);
        }
    }

       public List extensionOfAppointmentQualificationDetails(int compCode, String candSRno,String advtCode,String jobCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATION_OF_DATABANK_QUALIFICATION);
            query.setParameter("value1", compCode);
            query.setParameter("value2", candSRno);
            query.setParameter("value3", advtCode);
            query.setParameter("value4", jobCode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method extensionOfAppointmentQualificationDetails()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method extensionOfAppointmentQualificationDetails()");
                throw new DAOException(e);
        }
    }
       public List extensionOfAppointmentPreviousEmpDetail(int compCode, String candSRno,String advtCode,String jobCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATION_OF_DATABANK_PREVIOUS_EMPLOYEMENT);
            query.setParameter("value1", compCode);
            query.setParameter("value2", candSRno);
            query.setParameter("value3", advtCode);
            query.setParameter("value4", jobCode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method extensionOfAppointmentPreviousEmpDetail()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method extensionOfAppointmentPreviousEmpDetail()");
                throw new DAOException(e);
        }
    }
    public List creationOfDatabankSaveValid() throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATI0N_OF_DATABANK_SAVE_VALID);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method creationOfDatabankSaveValid()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method creationOfDatabankSaveValid()");
                throw new DAOException(e);
        }
    }

     public List<HrDataQual> creationofDatabankHrDataQualDeleteAction(int compCode,String advertisementNo,String jobSpecification,String candidateId,String examinationQualification) throws DAOException {
         try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATI0N_OF_DATABANK_DATA_QUAL_DEL);
            query.setParameter("value1", compCode);
            query.setParameter("value2", advertisementNo);
            query.setParameter("value3", jobSpecification);
            query.setParameter("value4", candidateId);
            query.setParameter("value5", examinationQualification);
            List<HrDataQual> instance = query.getResultList();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method creationofDatabankHrDataQualDeleteAction()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method creationofDatabankHrDataQualDeleteAction()");
                throw new DAOException(e);
        }
    }

       

       public List<HrDataPrevComp> creationofDatabankHrDataPrevCompDeleteAction(int compCode,String advertisementNo,String jobSpecification,String candidateId,String companyNameExperience) throws DAOException {
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATI0N_OF_DATABANK_DATA_EXP_DEL);
            query.setParameter("value1", compCode);
            query.setParameter("value2", advertisementNo);
            query.setParameter("value3", jobSpecification);
            query.setParameter("value4", candidateId);
            query.setParameter("value5", companyNameExperience);
            List<HrDataPrevComp> instance = query.getResultList();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method creationofDatabankHrDataQualDeleteAction()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method creationofDatabankHrDataQualDeleteAction()");
                throw new DAOException(e);
        }
    }

        public  List<HrDataReference> creationofDatabankHrDataReferenceDeleteAction(int compCode,String advertisementNo,String jobSpecification,String candidateId,String referanceNameReferanceDetails) throws DAOException {
         try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATI0N_OF_DATABANK_DATA_REF_DEL);
            query.setParameter("value1", compCode);
            query.setParameter("value2", advertisementNo);
            query.setParameter("value3", jobSpecification);
            query.setParameter("value4", candidateId);
            query.setParameter("value5", referanceNameReferanceDetails);
            List<HrDataReference> instance = query.getResultList();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method creationofDatabankHrDataReferenceDeleteAction()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method creationofDatabankHrDataReferenceDeleteAction()");
                throw new DAOException(e);
        }
    }

     public List<HrDatabank> addListEvaluationOfDatabank(int compCode, String organisation, String designation) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("addListEvaluationOfDatabank()");
        }
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.ADD_LIST_EVALUATION_OF_DATABANK);
            query.setParameter("value1", compCode);
            query.setParameter("value2", organisation);
            query.setParameter("value3", designation);
            query.setParameter("value4", 'N');
            List<HrDatabank> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("viewEditListAppointmentLetter() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method viewEditListAppointmentLetter()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method viewEditListAppointmentLetter()");
            throw new DAOException(e);
        }
    }
     public List<HrDatabank> editEvaluationOfDatabank(int compCode, String organisation) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("editEvaluationOfDatabank()");
        }
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.EDIT_EVALUATION_OF_DATABANK);
            query.setParameter("value1", compCode);
            query.setParameter("value2", organisation + "%");
            List<HrDatabank> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("viewEditListAppointmentLetter() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method viewEditListAppointmentLetter()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method viewEditListAppointmentLetter()");
            throw new DAOException(e);
        }
    }

    public String saveEvaluationOfDatabank(HrDatabank entity) throws ApplicationException {
        String msg = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.SAVE_EVALUATION_OF_DATABANK);
            query.setParameter("compCode", entity.getHrDatabankPK().getCompCode());
            query.setParameter("candId", entity.getHrDatabankPK().getCandId());
            query.setParameter("advtCode", entity.getHrDatabankPK().getAdvtCode());
            query.setParameter("jobCode", entity.getHrDatabankPK().getJobCode());
            query.setParameter("orgType", entity.getOrgType());
            query.setParameter("postApplied1", entity.getPostApplied1());
            query.setParameter("evalFlag", entity.getEvalFlag());
            query.executeUpdate();
            msg = "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return msg;
    }

    public String updateEvaluationOfDatabank(HrDatabank entity) throws ApplicationException {
        String msg = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.UPDATE_EVALUATION_OF_DATABANK);
            query.setParameter("compCode", entity.getHrDatabankPK().getCompCode());
            query.setParameter("orgType", entity.getOrgType());
            query.setParameter("postApplied1", entity.getPostApplied1());
            query.setParameter("evalFlag", entity.getEvalFlag());
            query.executeUpdate();
            msg = "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return msg;
    }

     public List searchListAppointmentLetter(int compCode, String candidateCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("searchListAppointmentLetter()");
        }
        try {
            List searchAppointmentLetterList = new ArrayList();
            Query query = entityManager.createQuery(NamedQueryConstant.SEARCH_LIST_APPOINTMENT_LETTER);
            query.setParameter("value1", compCode);
            query.setParameter("value2", candidateCode + "%");
            query.setParameter("value3", 'S');
            searchAppointmentLetterList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("searchListCanAppAdvice() - end - return value=" + searchAppointmentLetterList);
            }
            return searchAppointmentLetterList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method searchListCanAppAdvice()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method searchListCanAppAdvice()");
            throw new DAOException(e);
        }
    }

      public List viewListCanAppAdvice(int compCode, String intCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("searchListCanAppAdvice()");
        }
        try {
            List viewList = new ArrayList();
            Query query = entityManager.createQuery(NamedQueryConstant.VIEW_LIST_CANCELLATION_OF_APPOINTMENT_LETTER);
            query.setParameter("value1", compCode);
            query.setParameter("value2",intCode+"%");
            query.setParameter("value3",'Y');
            viewList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("searchListCanAppAdvice() - end - return value=" + viewList);
            }
            return viewList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method searchListCanAppAdvice()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method searchListCanAppAdvice()");
            throw new DAOException(e);
        }
    }

       public List searchListCanAppAdvice(int compCode, String candidateCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("searchListCanAppAdvice()");
        }
        try {
            List dropDownSearchList = new ArrayList();
            Query query = entityManager.createQuery(NamedQueryConstant.SEARCH_LIST_CANCELLATION_OF_APPOINTMENT_LETTER);
            query.setParameter("value1", compCode);
            query.setParameter("value2", candidateCode + "%");
            query.setParameter("value3", 'S');
            query.setParameter("value4", 'N');
            dropDownSearchList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("searchListCanAppAdvice() - end - return value=" + dropDownSearchList);
            }
            return dropDownSearchList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method searchListCanAppAdvice()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method searchListCanAppAdvice()");
            throw new DAOException(e);
        }
    }

       

}