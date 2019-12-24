/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrTransferDetails;
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
public class HrTransferDetailsDAO extends GenericDAO {

 private static final Logger logger = Logger.getLogger(HrTransferDetailsDAO.class);

  public HrTransferDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrTransferDetailsDAO Initializing...");
    }


   public List transferGetArNo(int compcode ,String arNo) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TRANSFER_GET_ARNO);
            query.setParameter("value1", compcode);
            query.setParameter("value2", arNo);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method transferGetArNo()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method transferGetArNo()");
                throw new DAOException(e);
        }
    }

     public List transferEditData(int compcode ,String arNo) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TRANSFER_EDIT_DETAIL);
            query.setParameter("value1", compcode);
            query.setParameter("value2", arNo);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method transferEditData()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method transferEditData()");
                throw new DAOException(e);
        }
    }
      public HrTransferDetails deleteTransfer(int compCode, String arNo) throws DAOException {
         HrTransferDetails instance = new HrTransferDetails();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TRANSFER_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", arNo);
            instance = (HrTransferDetails)query.getSingleResult();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method deleteTransfer()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method deleteTransfer()");
                throw new DAOException(e);
        }
    }

      public List transferPrimrycheck(int compCode, String arNo) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TRANSFER_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", arNo);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method transferPrimrycheck()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method transferPrimrycheck()");
            throw new DAOException(e);
        }
    }




  


}
