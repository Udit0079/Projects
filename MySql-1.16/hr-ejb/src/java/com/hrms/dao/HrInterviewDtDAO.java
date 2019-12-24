/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.common.exception.ApplicationException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrInterviewDt;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
public class HrInterviewDtDAO  extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrInterviewDtDAO.class);
    /**
     * @param entityManager
     */
    public HrInterviewDtDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrInterviewDtDAO Initializing...");
    }

     public String postinterviewUpdate(HrInterviewDt entity) throws DAOException {
        String msg = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.POSTINTERVIEW_UPDATE_ACTION);
            query.setParameter("compCode", entity.getHrInterviewDtPK().getCompCode());
            query.setParameter("intcode", entity.getHrInterviewDtPK().getIntCode());
            query.setParameter("advtcode", entity.getHrInterviewDtPK().getAdvtCode());
            query.setParameter("jobcode", entity.getHrInterviewDtPK().getJobCode());
            query.setParameter("candsrno", entity.getHrInterviewDtPK().getCandSrno());
            query.setParameter("result", entity.getIntResult());
            query.setParameter("exptJoin", entity.getExpectJoin());
            query.setParameter("remarks", entity.getIntRemarks());
            query.executeUpdate();
            msg="true";
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method postinterviewUpdate()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method postinterviewUpdate()");
                throw new DAOException(e);
        }
        return msg;
    }

      public List postintIntView(int compCode,String intPreCode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.POSTINTERVIEW_INTERVIEWER_VIEW_DETAIL);
            query.setParameter("value1", compCode);
            query.setParameter("value2", intPreCode+"%");
            query.setParameter("value3", 'S');
            query.setParameter("value4", 'H');
            query.setParameter("value5", 'R');
            query.setParameter("value6", 'N');
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method postintIntView()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method postintIntView()");
                throw new DAOException(e);
        }
    }

       public List postintIntEdit(int compCode,String intCode,String advtCode,String jobCode,String candSrno) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.POSTINTERVIEW_INTERVIEWER_EDIT_DETAIL);
            query.setParameter("value1", compCode);
            query.setParameter("value2", intCode);
            query.setParameter("value3", advtCode);
            query.setParameter("value4", jobCode);
            query.setParameter("value5", candSrno);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method postintIntEdit()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method postintIntEdit()");
                throw new DAOException(e);
        }
    }




        public String extensionOfAppointmentAdviceSave(HrInterviewDt entity) throws DAOException {
        String msg = null;
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.EXTENSIN_OF_APPOINTMENT_SAVE);
            query.setParameter("compCode", entity.getHrInterviewDtPK().getCompCode());
            query.setParameter("intcode", entity.getHrInterviewDtPK().getIntCode());
            query.setParameter("candsrno", entity.getHrInterviewDtPK().getCandSrno());
            query.setParameter("adviceCancel", entity.getAdviceCancel());
            query.setParameter("expectJoin", entity.getExpectJoin());
            query.setParameter("extension", entity.getExtension());
            query.setParameter("stateFlag", entity.getStatFlag());
            query.setParameter("statupFlag", entity.getStatUpFlag());
            query.setParameter("modDt", entity.getModDate());
            query.setParameter("authBy", entity.getAuthBy());
            query.setParameter("enterBy", entity.getEnteredBy());
            query.executeUpdate();
            msg="true";
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method extensionOfAppointmentAdviceSave()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method extensionOfAppointmentAdviceSave()");
                throw new DAOException(e);
        }
        return msg;
    }

      public String extensionOfAppointmentAdviceUpdate(HrInterviewDt entity) throws DAOException {
        String msg = null;
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.EXTENSIN_OF_APPOINTMENT_UPDATE);
            query.setParameter("compCode", entity.getHrInterviewDtPK().getCompCode());
            query.setParameter("intcode", entity.getHrInterviewDtPK().getIntCode());
            query.setParameter("candsrno", entity.getHrInterviewDtPK().getCandSrno());
            query.setParameter("cancel", entity.getCancel());
            query.setParameter("adviceCancel", entity.getAdviceCancel());
            query.setParameter("expectJoin", entity.getExpectJoin());
            query.setParameter("extension", entity.getExtension());
            query.setParameter("stateFlag", entity.getStatFlag());
            query.setParameter("statupFlag", entity.getStatUpFlag());
            query.setParameter("modDt", entity.getModDate());
            query.setParameter("authBy", entity.getAuthBy());
            query.setParameter("enterBy", entity.getEnteredBy());
            query.executeUpdate();
            msg="true";
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method extensionOfAppointmentAdviceUpdate()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method extensionOfAppointmentAdviceUpdate()");
                throw new DAOException(e);
        }
        return msg;
    }

       public String updateTabForSaveAppLetter(HrInterviewDt entity) throws ApplicationException{
        String msg = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.UPDATE_TABLE_FOR_SAVE_APPOINTMENT_LETTER);
             query.setParameter("compCode", entity.getHrInterviewDtPK().getCompCode());
             query.setParameter("intCode", entity.getHrInterviewDtPK().getIntCode());
             query.setParameter("advtCode", entity.getHrInterviewDtPK().getAdvtCode());
             query.setParameter("jobCode", entity.getHrInterviewDtPK().getJobCode());
             query.setParameter("candsrno", entity.getHrInterviewDtPK().getCandSrno());
             query.setParameter("intResult", entity.getIntResult());
             query.executeUpdate();
            msg = "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return msg;
    }

     public String updateAppointmentCencel(HrInterviewDt entity) throws ApplicationException {
        String msg = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.UPDATE_APPOINTMENT_CENCEL);
            query.setParameter("compCode", entity.getHrInterviewDtPK().getCompCode());
            query.setParameter("intCode", entity.getHrInterviewDtPK().getIntCode());
            query.setParameter("candsrno", entity.getHrInterviewDtPK().getCandSrno());
            query.setParameter("cancel", entity.getCancel());
            query.setParameter("adviceCancel", entity.getAdviceCancel());
            query.setParameter("statFlag", entity.getStatFlag());
            query.setParameter("statUpFlag", entity.getStatUpFlag());
            query.setParameter("modDate", entity.getModDate());
            query.setParameter("authBy", entity.getAuthBy());
            query.setParameter("enteredBy", entity.getEnteredBy());
            query.executeUpdate();
            msg = "true";
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method preinterviewUpdateDatabank()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method preinterviewUpdateDatabank()");
                throw new DAOException(e);
        }
        return msg;
    }

}

