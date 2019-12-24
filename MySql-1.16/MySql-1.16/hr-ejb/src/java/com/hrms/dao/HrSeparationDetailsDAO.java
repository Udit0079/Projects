/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrSeparationDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
/**
 *
 * @author admin
 */
public class HrSeparationDetailsDAO extends GenericDAO{
private static final Logger logger = Logger.getLogger(HrSeparationDetailsDAO.class);
    /**
     * @param entityManager
     */
    EntityManager em;

    public HrSeparationDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("LeaveMasterDAO Initializing...");
    }
    
    public List setelementSearch(int compCode)throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("setelementSearch()");
        }
        try{
            Query query=entityManager.createQuery(NamedQueryConstant.SETTLEMENT_SEARCH);
            query.setParameter("compCode", compCode);
            query.setParameter("statFlag","N" );
            List result=query.getResultList();
            return result;
        }catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }
    public List empDataSetelement(int compCode,String empId)throws DAOException{
         if (logger.isDebugEnabled()) {
            logger.debug("empDataSetelement()");
        }
        try{
            Query query=entityManager.createQuery(NamedQueryConstant.SELECT_DATA_SETTLEMENT_LETTER);
            query.setParameter("compCode", compCode);
            query.setParameter("empId", empId);
            List result=query.getResultList();
            return result;
        }catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }
    public boolean updateStatFlag(int compCode,long empCode,String statFlag)throws DAOException{
         if (logger.isDebugEnabled()) {
            logger.debug("updateStatFlag()");
        }
        try{
            Query query=entityManager.createNamedQuery(NamedQueryConstant.UPDATE_HR_SEPRATION_STAT_FLAG);
            query.setParameter("compCode",compCode );
            query.setParameter("empCode",empCode);
            query.setParameter("statFlag",statFlag );
            query.executeUpdate();
            return true;
        }catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
         }

    }
    public List<HrSeparationDetails> checkempCode(int compCode)throws DAOException{
         if (logger.isDebugEnabled()) {
            logger.debug("checkempCode()");
        }
        try{
            Query query = entityManager.createQuery(NamedQueryConstant.CHECK_DATA_EXIT_INTERVIEW_ADD);
            query.setParameter("compCode", compCode);
            List<HrSeparationDetails> result=query.getResultList();
            return result;
        }catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }
    public List<HrSeparationDetails> checkempCodeView(int compCode)throws DAOException{
         if (logger.isDebugEnabled()) {
            logger.debug("checkempCodeView()");
        }
        try{
            Query query = entityManager.createQuery(NamedQueryConstant.CHECK_DATA_EXIT_INTERVIEW_VIEW);
            query.setParameter("compCode", compCode);
            List<HrSeparationDetails> result=query.getResultList();
            return result;
        }catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }
    
    public List viewEmployeeForTaxSearch()throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("setelementSearch()");
        }
        try{
            Query query=entityManager.createQuery(NamedQueryConstant.EMP_SEARCH_TAX);
            query.setParameter("empStatus",'Y' );
            List result=query.getResultList();
            return result;
        }catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }
}
