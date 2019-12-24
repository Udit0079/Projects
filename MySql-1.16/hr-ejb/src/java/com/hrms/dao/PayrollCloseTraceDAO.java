/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrPayrollCloseTrace;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author sudhir Kr Bisht
 */
public class PayrollCloseTraceDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public PayrollCloseTraceDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("PayrollCloseTraceDAO Initializing...");
    }

    /**
     * get the payroll close trace problems entity between dates parameters
     * @param selectFromDate
     * @param SelectToDate
     * @return
     * @throws DAOException
     */
    public List<HrPayrollCloseTrace> findByFrmDateAndToDate(Date selectFromDate, Date SelectToDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByFrmDateAndToDate()");
        }
        List<HrPayrollCloseTrace> hrPayrollCloseTraceList = new ArrayList<HrPayrollCloseTrace>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_ENTITY_BY_FROM_TO_DATE);
            q1.setParameter("calDateFrom", selectFromDate);
            q1.setParameter("calDateTo", SelectToDate);
            hrPayrollCloseTraceList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByFrmDateAndToDate() - end - return value=" + hrPayrollCloseTraceList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByFrmDateAndToDate()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByFrmDateAndToDate()");
            throw new DAOException(e);
        }
        return hrPayrollCloseTraceList;
    }

    /**
     * 
     * @return
     * @throws DAOException
     */
    public int findTraceProblemCode() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findTraceProblemCode()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_MAX_TRACE_PROBLEM_CODE);
            if(q1.getSingleResult() == null){
                return 1;
            }else{
                return (Integer.parseInt(q1.getSingleResult().toString()))+1;
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findTraceProblemCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findTraceProblemCode()");
            throw new DAOException(e);
        }

    }
}
