/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade;

import com.hrms.common.to.ParametersTO;
import com.hrms.dao.ParametersDAO;
import javax.ejb.Stateless;
import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrMstStructDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstStruct;
import com.hrms.entity.hr.Parameters;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
@Stateless(mappedName = "ParametersFacade")
@Remote({ParametersFacadeRemote.class})
public class ParametersFacade implements ParametersFacadeRemote {

    private static final Logger logger = Logger.getLogger(ParametersFacade.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ParametersTO> getParameterList() throws ApplicationException {
        long begin = System.nanoTime();
        ParametersDAO structMasterDao = new ParametersDAO(em);
        List<ParametersTO> structMasterTOs = new ArrayList<ParametersTO>();
        try {
            List<Parameters> parameterList = structMasterDao.findByStructCode();
            for (Parameters hrMstStruct : parameterList) {
                structMasterTOs.add(ObjectAdaptorHr.adaptToParameterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDirectRecruitmentList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDirectRecruitmentList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDirectRecruitmentList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return structMasterTOs;
    }

    @Override
    public List<HrMstStructTO> parametertDescriptionList(int compCode, String code) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, code);
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDirectRecruitmentList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDirectRecruitmentList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDirectRecruitmentList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return structMasterTOs;
    }

    @Override
    public String parameterSave(HrMstStructTO hrParamObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrParamObj);
        HrMstStruct hrparameter = null;
        try {
            HrMstStructDAO hrMstStructDAO = new HrMstStructDAO(em);
            hrparameter = ObjectAdaptorHr.adaptToMstStructEntity(hrParamObj);
            hrMstStructDAO.save(hrparameter);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method parameterSave()", e);
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
            logger.error("Exception occured while executing method parameterSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for parameterSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String parameterUpdate(HrMstStructTO hrParamObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrParamObj);
        HrMstStruct hrparameter = null;
        try {
            HrMstStructDAO hrMstStructDAO = new HrMstStructDAO(em);
            hrparameter = ObjectAdaptorHr.adaptToMstStructEntity(hrParamObj);
            hrMstStructDAO.update(hrparameter);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method parameterUpdate()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("updateCreationOfDatabank")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method parameterUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for parameterUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    @Override
    public String deleteParameterAction(int compCode, String structcode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStruct hrMstStruct = new HrMstStruct();
        try {
            HrMstStructDAO hrMstStructDAO = new HrMstStructDAO(em);
            hrMstStruct = hrMstStructDAO.deleteParameter(compCode, structcode);
            hrMstStructDAO.delete(hrMstStruct);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteParameterAction()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for deleteParameterAction is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }
}
