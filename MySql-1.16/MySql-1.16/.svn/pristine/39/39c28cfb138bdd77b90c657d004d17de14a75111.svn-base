/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.common.to.HrDatabankTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
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
public class HrInterviewHdDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrInterviewHdDAO.class);
    /**
     * @param entityManager
     */
    public HrInterviewHdDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrInterviewHdDAO Initializing...");
    }

    public String preinterviewAdvtCode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("preinterviewAdvtCode()");
        }
        String max="1";
        try {
                long maxCode=0;
                Query q1 = entityManager.createNamedQuery("HrInterviewHd.findMaxIntCodeByCompCode");
                q1.setParameter("compCode", compCode);
                if (q1.getSingleResult() != null) {
                maxCode = Long.parseLong(q1.getSingleResult().toString());
               long val = maxCode + 1;
               max = Long.toString(val);
                int length = max.length();
                int addedZero = 7 - length;
                for (int i = 1; i <= addedZero; i++) {
                    max = "0" + max;
                }
            }
             else{
                    max="0000001";
              }    
             max="INT"+max;             
             return max;
     
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method preinterviewAdvtCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method preinterviewAdvtCode()");
            throw new DAOException(e);
        }
 }


    public List preintInterviewerSearch(int compCode,String nameConsultant) throws DAOException {
        List dropDownList1 =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PREINT_INTERVIEWSEARCH);
            query.setParameter("value1", compCode);
            query.setParameter("value2", nameConsultant+"%");
            dropDownList1 = query.getResultList();
            return dropDownList1;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method preintInterviewerSearch()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method preintInterviewerSearch()");
                throw new DAOException(e);
        }
    }

    public List preintInterviewerUpdateView(int compCode,String intPreCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PREINT_INTERVIEW_UPDATE);
            query.setParameter("value1", compCode);
            query.setParameter("value2", intPreCode+"%");
            query.setParameter("value3", 'Y');
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method preintInterviewerUpdateView()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method preintInterviewerUpdateView()");
                throw new DAOException(e);
        }
    }


     public List preintInterviewersCode(int compCode,String intPreCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.PREINT_INTERVIEW_CODE);
            query.setParameter("value1", compCode);
            query.setParameter("value2", intPreCode);
            query.setParameter("value3", 'Y');
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method preintInterviewersCode()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method preintInterviewersCode()");
                throw new DAOException(e);
        }
    }

//commented by Ankit
//      public String preinterviewUpdateDatabank(HrDatabankTO entity) throws DAOException {
//        String msg = null;
//        try {
//            Query query = entityManager.createQuery(NamedQueryConstant.DATABANK_UPDATE);
//            query.setParameter("compCode", entity.getHrDatabankPKTO().getCompCode());
//            query.setParameter("canid", entity.getHrDatabankPKTO().getCandId());
//            query.setParameter("calint", entity.getCallInt());
//            query.executeUpdate();
//            msg="true";
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//                logger.error("Exception occured while executing method preinterviewUpdateDatabank()");
//                throw ExceptionTranslator.translateException(e);
//        } catch (Exception e) {
//                logger.error("Exception occured while executing method preinterviewUpdateDatabank()");
//                throw new DAOException(e);
//        }
//        return msg;
//    }


     public List postintInterviewerDetail(int compCode, String advtCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.POSTINTERVIEW_INTERVIEWER_DETAIL);
            query.setParameter("value1", compCode);
            query.setParameter("value2", advtCode + "%");
            query.setParameter("value3", 'Y');
            query.setParameter("value4", 'N');
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method postintInterviewerDetail()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method postintInterviewerDetail()");
                throw new DAOException(e);
        }
    }

}


