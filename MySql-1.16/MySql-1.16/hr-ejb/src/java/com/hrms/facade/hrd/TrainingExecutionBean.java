/*
 * CREATED BY    :   ROHIT KRISHNA GUPTA
 * CREATION DATE :   05 JULY 2011
 */
package com.hrms.facade.hrd;

import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrTrainingExecutionTO;
import com.hrms.common.to.HrTrainingPlanTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrTrainingExecution;
import com.hrms.entity.hr.HrTrainingPlan;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author ROHIT KRISHNA
 */
@Stateless(mappedName = "TrainingExecutionBean")
@Remote({TrainingExecutionRemote.class})
public class TrainingExecutionBean implements TrainingExecutionRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    private static final Logger logger = Logger.getLogger(TrainingExecutionBean.class);

    @Override
    public List trainingPlanGridDetail(int compCode, String trngExec) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            resultLt = hrPersonalDetdaoObj.trainingPlanGetDetailInExecution(compCode, trngExec);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return resultLt;
    }

    @Override
    public String getMaxTrngExecCode() throws ApplicationException {
        try {
            String queryString = "SELECT MAX(A.hrTrainingExecutionPK.trngexecCode) FROM HrTrainingExecution A";
            Query query = em.createQuery(queryString);
            List chk1 = query.getResultList();
            if (chk1.isEmpty()) {
                return "! No Record in Hr_Training_Execution !!!";
            }
            if(chk1.get(0)==null)
            {
                   return "! No Record in Hr_Training_Execution !!!";
            }
            String tempTrngExecCode = chk1.get(0).toString();
            return tempTrngExecCode;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

    }

    @Override
    public String saveTrainingExecutionDetail(HrTrainingExecutionTO hrTrngExec, HrTrainingPlanTO hrTrngPlan) throws ApplicationException {
        try {
            long begin = System.nanoTime();
            Validator.isNull(hrTrngExec);
            Validator.isNull(hrTrngPlan);
            HrTrainingExecution obj = new HrTrainingExecution();
            HrTrainingPlan obj1 = new HrTrainingPlan();
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            obj = ObjectAdaptorHr.adaptToHrTrainingExecutionEntity(hrTrngExec);
            hrPersonalDetdaoObj.save(obj);

            String queryString = "SELECT A.hrTrainingExecutionPK.trngexecCode FROM HrTrainingExecution A WHERE A.hrTrainingExecutionPK.compCode=:value1" +
                    " AND A.hrTrainingExecutionPK.trngexecCode=:value2 AND A.hrTrainingExecutionPK.empCode=:value3";
            Query query = em.createQuery(queryString);
            query.setParameter("value1", hrTrngExec.getHrTrainingExecutionPKTO().getCompCode());
            query.setParameter("value2", hrTrngExec.getHrTrainingExecutionPKTO().getTrngexecCode());
            query.setParameter("value3", hrTrngExec.getHrTrainingExecutionPKTO().getEmpCode());
            List chk1 = query.getResultList();
            if (!chk1.isEmpty()) {
                Query updateQuery = em.createQuery("UPDATE HrTrainingPlan A SET A.trngExec='Y' WHERE A.hrTrainingPlanPK.compCode=:value1 AND " +
                        " A.hrTrainingPlanPK.empCode=:value2 AND A.hrTrainingPlanPK.trngCode=:value3 AND A.hrTrainingPlanPK.progCode=:value4 AND " +
                        " A.hrTrainingPlanPK.dateFrom = :value5 AND A.hrTrainingPlanPK.dateTo=:value6");
                updateQuery.setParameter("value1", hrTrngPlan.getHrTrainingPlanPKTO().getCompCode());
                updateQuery.setParameter("value2", hrTrngPlan.getHrTrainingPlanPKTO().getEmpCode());
                updateQuery.setParameter("value3", hrTrngPlan.getHrTrainingPlanPKTO().getTrngCode());
                updateQuery.setParameter("value4", hrTrngPlan.getHrTrainingPlanPKTO().getProgCode());
                updateQuery.setParameter("value5", hrTrngPlan.getHrTrainingPlanPKTO().getDateFrom());
                updateQuery.setParameter("value6", hrTrngPlan.getHrTrainingPlanPKTO().getDateTo());
                int var = updateQuery.executeUpdate();
                if (var <= 0) {
                    return "false";
                } else {
                    return "true";
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        return "true";
    }
    @Override
    public String updateTrainingExecutionDetail(HrTrainingExecutionTO hrTrngExec) throws ApplicationException {
        try {
            long begin = System.nanoTime();
            Validator.isNull(hrTrngExec);
            HrTrainingExecution obj = new HrTrainingExecution();
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            String queryString = "SELECT A.hrTrainingExecutionPK.trngexecCode FROM HrTrainingExecution A WHERE A.hrTrainingExecutionPK.compCode=:value1" +
                    " AND A.hrTrainingExecutionPK.trngexecCode=:value2 AND A.hrTrainingExecutionPK.empCode=:value3";
            Query query = em.createQuery(queryString);
            query.setParameter("value1", hrTrngExec.getHrTrainingExecutionPKTO().getCompCode());
            query.setParameter("value2", hrTrngExec.getHrTrainingExecutionPKTO().getTrngexecCode());
            query.setParameter("value3", hrTrngExec.getHrTrainingExecutionPKTO().getEmpCode());
            List chk1 = query.getResultList();
            if (!chk1.isEmpty()) {
                obj = ObjectAdaptorHr.adaptToHrTrainingExecutionEntity(hrTrngExec);
                hrPersonalDetdaoObj.update(obj);
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method updateTrainingExecutionDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateTrainingExecutionDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        return "Data has been successfully updated";
    }
    @Override
    public String deleteTrainingExecutionDetail(HrTrainingExecutionTO hrTrngExec, HrTrainingPlanTO hrTrngPlan) throws ApplicationException {
        try {
            long begin = System.nanoTime();
            Validator.isNull(hrTrngExec);
            Validator.isNull(hrTrngPlan);
            //HrTrainingExecution obj = new HrTrainingExecution();
            HrTrainingPlan obj1 = new HrTrainingPlan();
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            String queryString = "SELECT A FROM HrTrainingExecution A WHERE A.hrTrainingExecutionPK.compCode=:value1" +
                    " AND A.hrTrainingExecutionPK.trngexecCode=:value2 AND A.hrTrainingExecutionPK.empCode=:value3";
            Query query = em.createQuery(queryString);
            query.setParameter("value1", hrTrngExec.getHrTrainingExecutionPKTO().getCompCode());
            query.setParameter("value2", hrTrngExec.getHrTrainingExecutionPKTO().getTrngexecCode());
            query.setParameter("value3", hrTrngExec.getHrTrainingExecutionPKTO().getEmpCode());
            List chk1 = query.getResultList();
            if (!chk1.isEmpty()) {
               // obj = ObjectAdaptorHr.adaptToHrTrainingExecutionEntity(hrTrngExec);
                hrPersonalDetdaoObj.delete((HrTrainingExecution)chk1.get(0));

                Query updateQuery = em.createQuery("UPDATE HrTrainingPlan A SET A.trngExec='N' WHERE A.hrTrainingPlanPK.compCode=:value1 AND " +
                        " A.hrTrainingPlanPK.empCode=:value2 AND A.hrTrainingPlanPK.trngCode=:value3 AND A.hrTrainingPlanPK.progCode=:value4 AND " +
                        " A.hrTrainingPlanPK.dateFrom = :value5 AND A.hrTrainingPlanPK.dateTo=:value6");
                updateQuery.setParameter("value1", hrTrngPlan.getHrTrainingPlanPKTO().getCompCode());
                updateQuery.setParameter("value2", hrTrngPlan.getHrTrainingPlanPKTO().getEmpCode());
                updateQuery.setParameter("value3", hrTrngPlan.getHrTrainingPlanPKTO().getTrngCode());
                updateQuery.setParameter("value4", hrTrngPlan.getHrTrainingPlanPKTO().getProgCode());
                updateQuery.setParameter("value5", hrTrngPlan.getHrTrainingPlanPKTO().getDateFrom());
                updateQuery.setParameter("value6", hrTrngPlan.getHrTrainingPlanPKTO().getDateTo());
                int var = updateQuery.executeUpdate();
                if (var <= 0) {
                    return "Data could not be deleted from HrTrainingPlan !!!";
                } else {
                    return "Data deleted Succesfully.";
                }
            } else {
                return "Data has already been deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }

 @Override
    public List trainingExucutionEditist(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrPersonalDetdaoObj.trainingExcutionEditDetail(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method trainingExucutionEditist()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method trainingExucutionEditist()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for trainingExucutionEditist is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }


}
