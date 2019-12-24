/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  20 JUNE 2011
 */
package com.hrms.facade.hrd;

import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstTrngProgramPKTO;
import com.hrms.common.to.HrMstTrngProgramTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrMstProgramDAO;
import com.hrms.dao.HrMstTrngProgramDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstTrngProgram;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author ROHIT KRISHNA
 */
@Stateless(mappedName = "TrainingProgramMasterBean")
@Remote({TrainingProgramMasterRemote.class})
public class TrainingProgramMasterBean implements TrainingProgramMasterRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    private static final Logger logger = Logger.getLogger(TrainingProgramMasterBean.class);
//    @EJB
//    HrMstProgramLocal daoMstProg;
//    @EJB
//    HrMstTrngProgramLocal daoMstTrngProg;

    @Override
    public List trainingNameDd(int compCode) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrMstProgramDAO hrMstProgDao = new HrMstProgramDAO(em);
            resultLt = hrMstProgDao.trainingNameCombo(compCode);
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
    public List progNameDd(int compCode) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrMstProgramDAO hrMstProgDao = new HrMstProgramDAO(em);
            resultLt = hrMstProgDao.progNameCombo(compCode);
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
    public List trngMstGridLoad(int compCode, String trainingCode) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrMstTrngProgramDAO hrMstTrng = new HrMstTrngProgramDAO(em);
            resultLt = hrMstTrng.trngProgGridLoad(compCode, trainingCode);
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
    public String saveTrainingRecord(HrMstTrngProgramTO trngProgObj) throws ApplicationException {
        String result = "";
        long begin = System.nanoTime();
        Validator.isNull(trngProgObj);
        try {
            HrMstTrngProgram obj = new HrMstTrngProgram();
            List resultLt = new ArrayList();
            HrMstTrngProgramDAO hrMstTrng = new HrMstTrngProgramDAO(em);
            resultLt = hrMstTrng.checkAlreadyExistance(trngProgObj.getHrMstTrngProgramPKTO().getCompCode(), trngProgObj.getHrMstTrngProgramPKTO().getTrngCode(), trngProgObj.getHrMstTrngProgramPKTO().getProgCode());
            if (!resultLt.isEmpty() || resultLt.size() > 0) {
                result = "false1";
                return result;
            }
            obj = ObjectAdaptorHr.adaptToHrMstTrngProgramEntity(trngProgObj);
            hrMstTrng.save(obj);
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
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveLeaveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    @Override
    //public String deleteTrngRecord(int compCode, String progCode, String trainingCode) throws ApplicationException{
    public String deleteTrngRecord(HrMstTrngProgramPKTO trngProgObj) throws ApplicationException {
        Validator.isNull(trngProgObj);
        try {
            HrMstTrngProgram obj = new HrMstTrngProgram();
            HrMstTrngProgramDAO hrMstTrng = new HrMstTrngProgramDAO(em);
            obj = hrMstTrng.findByCompCodeAndProgramCodeAndTrngCode(trngProgObj.getCompCode(), trngProgObj.getProgCode(), trngProgObj.getTrngCode());
            hrMstTrng.delete(obj);
            return "true";
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
}
