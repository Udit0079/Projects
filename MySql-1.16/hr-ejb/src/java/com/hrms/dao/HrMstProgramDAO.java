/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;

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
public class HrMstProgramDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrMstProgramDAO.class);
    /**
     * @param entityManager
     */
    public HrMstProgramDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstProgramDAO Initializing...");
    }

    public List trainingNameCombo(int compCode)throws DAOException{
        List progNameLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("minSkilSet()");
        }
        try{
            Query query = entityManager.createQuery("SELECT A.hrMstStructPK.structCode,A.description FROM HrMstStruct A WHERE A.hrMstStructPK.compCode = :value1 AND A.hrMstStructPK.structCode LIKE 'TRA%' ");
            query.setParameter("value1", compCode);
            progNameLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return progNameLt;
    }

    public List progNameCombo(int compCode)throws DAOException{
        List progNameLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("minSkilSet()");
        }
        try{
            Query query = entityManager.createQuery("SELECT A.hrMstProgramPK.progCode,A.progName FROM HrMstProgram A WHERE A.hrMstProgramPK.compCode = :value1");
            query.setParameter("value1", compCode);
            progNameLt = query.getResultList();
        }catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return progNameLt;
    }

}