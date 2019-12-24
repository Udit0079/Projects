/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrMstTrngProgram;
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
public class HrMstTrngProgramDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMstTrngProgramDAO.class);

    /**
     * @param entityManager
     */
    public HrMstTrngProgramDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstTrngProgramDAO Initializing...");
    }

    public List trngProgGridLoad(int compCode, String trainingCode) throws DAOException {
        List resultList = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("minSkilSet()");
        }
        try {
            Query query = entityManager.createQuery("SELECT A.hrMstProgramPK.progCode,A.progName," +
                    " B.hrMstTrngProgramPK.trngCode " +
                    " FROM HrMstProgram A,HrMstTrngProgram B " +
                    " WHERE B.hrMstTrngProgramPK.compCode=:value1 " +
                    " AND B.hrMstTrngProgramPK.trngCode=:value2 " +
                    " AND A.hrMstProgramPK.progCode = B.hrMstTrngProgramPK.progCode " +
                    " AND A.hrMstProgramPK.compCode=B.hrMstTrngProgramPK.compCode");
            query.setParameter("value1", compCode);
            query.setParameter("value2", trainingCode);
            resultList = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method trngProgGridLoad()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method trngProgGridLoad()");
            throw new DAOException(e);
        }
        return resultList;
    }

    public List checkAlreadyExistance(int compCode, String trainingCode, String progCode) throws DAOException {
        List resultLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("checkAlreadyExistance()");
        }
        try {
            Query query = entityManager.createQuery("SELECT A.hrMstTrngProgramPK.progCode FROM HrMstTrngProgram A WHERE A.hrMstTrngProgramPK.compCode = :value1 AND A.hrMstTrngProgramPK.progCode=:value2 AND A.hrMstTrngProgramPK.trngCode=:value3");
            query.setParameter("value1", compCode);
            query.setParameter("value2", progCode);
            query.setParameter("value3", trainingCode);
            resultLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method trngProgGridLoad()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method trngProgGridLoad()");
            throw new DAOException(e);
        }
        return resultLt;
    }

    public HrMstTrngProgram findByCompCodeAndProgramCodeAndTrngCode(int compCode, String progCode,String trainingCode) throws DAOException{
        HrMstTrngProgram instance = new HrMstTrngProgram();
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCodeAndProgramCodeAndTrngCode()");
        }
        try {
            final String queryString = "select A from HrMstTrngProgram A where A.hrMstTrngProgramPK.compCode = :value1 " +
                    "and A.hrMstTrngProgramPK.progCode = :value2 AND A.hrMstTrngProgramPK.trngCode=:value3";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            query.setParameter("value2", progCode);
            query.setParameter("value3", trainingCode);
            instance = (HrMstTrngProgram)query.getSingleResult();
            return instance;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method trngProgGridLoad()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method trngProgGridLoad()");
            throw new DAOException(e);
        }
    }
}
