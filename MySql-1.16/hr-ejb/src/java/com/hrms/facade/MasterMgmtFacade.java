/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade;

import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.adaptor.PayrollObjectAdaptor;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.LeaveMasterDAO;
import com.hrms.dao.PayrollCalendarDAO;
import com.hrms.dao.HrMstStructDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstStruct;
import com.hrms.entity.payroll.HrLeaveMaster;
import com.hrms.entity.payroll.HrPayrollCalendar;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Dhirendra Singh
 */
@Stateless(mappedName = "MasterMgmtFacade")
@Remote({MasterMgmtFacadeRemote.class})
public class MasterMgmtFacade implements MasterMgmtFacadeRemote {

    private static final Logger logger = Logger.getLogger(MasterMgmtFacade.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<HrMstStructTO> getIntialData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, "LEA%");
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return structMasterTOs;
    }

    @Override
    public List<PayrollCalendarTO> getFinYear(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        PayrollCalendarDAO payrollCalendarDao = new PayrollCalendarDAO(em);
        List<PayrollCalendarTO> payrollCalendarTOs = new ArrayList<PayrollCalendarTO>();
        try {
            List<HrPayrollCalendar> payrollCalendarList = payrollCalendarDao.findByStatusFlag(compCode, "OPEN");
            for (HrPayrollCalendar payrollCalendar : payrollCalendarList) {
                payrollCalendarTOs.add(PayrollObjectAdaptor.adaptToPayrollCalendarTO(payrollCalendar));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getFinYear()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getFinYear()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getFinYear is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return payrollCalendarTOs;
    }

    @Override
    public String saveLeaveDetail(LeaveMasterTO leavmstObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(leavmstObj);

        HrLeaveMaster hrLeaveMaster = null;
        try {
            LeaveMasterDAO leaveMasterDao = new LeaveMasterDAO(em);
            hrLeaveMaster = PayrollObjectAdaptor.adaptToHrLeaveMasterEntity(leavmstObj);
            leaveMasterDao.save(hrLeaveMaster);

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
        return "Data has been successfully saved.";
    }
//    @Override
//    public String saveLeaveDetail(String flag, LeaveMasterTO leavmstdto) {
//        try {
//            //int compCode,Str leaveCode,Str leaveNat,int numDays,Str dtFrm,
//            //Str dtTo,Str ApplicableDt, str desc,char enCash,int defaultComp,char statFlag,
//            //char statUpFlg,date modDt,str authBy,str entrBY
//            HrLeaveMaster hrleavmst = new HrLeaveMaster();
//            HrLeaveMasterPK hrleavmstpk = new HrLeaveMasterPK();
//            if (flag.equalsIgnoreCase("S")) {
//                hrleavmstpk.setCompCode(leavmstdto.getCompCode());
//                hrleavmstpk.setDateFrom(leavmstdto.getDtFrm());
//                hrleavmstpk.setDateTo(leavmstdto.getDtTo());
//                hrleavmstpk.setLeaveCode(leavmstdto.getLeaveCode());
//                hrleavmst.setHrLeaveMasterPK(hrleavmstpk);
//                hrleavmst.setApplicableDate(leavmstdto.getApplicableDt());
//                hrleavmst.setAuthBy(leavmstdto.getAuthBy());
//                hrleavmst.setDefaultComp(leavmstdto.getDefaultComp());
//                hrleavmst.setDescription(leavmstdto.getDesc());
//                hrleavmst.setEncash(leavmstdto.getEnCash());
//                hrleavmst.setEnteredBy(leavmstdto.getEntrBY());
//                hrleavmst.setModDate(leavmstdto.getModDt());
//                hrleavmst.setNumDays(leavmstdto.getNumDays());
//                hrleavmst.setStatFlag(leavmstdto.getStatFlag());
//                hrleavmst.setStatUpFlag(leavmstdto.getStatUpFlg());
//                HrMstStructPK hrMstStructPk = new HrMstStructPK();
//                System.out.println("mayank----" + leavmstdto.getLeaveNat());
//                hrMstStructPk.setStructCode(leavmstdto.getLeaveNat());
//                hrMstStructPk.setCompCode(leavmstdto.getCompCode());
//                hrMstStructPk.getStructCode().toString();
//                HrMstStruct hrMstStruct = new HrMstStruct(hrMstStructPk);
//                hrleavmst.setHrMstStruct(hrMstStruct);
//                System.out.println("hi-----");
//                System.out.println("mayyyyy" + hrleavmst.getHrMstStruct().getHrMstStructPK().getStructCode());
//                String str = leaveDao.save(hrleavmst);
//                if (str.equalsIgnoreCase("true")) {
//                    return "Data is saved successfully.";
//                } else {
//                    return "Problem in data saving.";
//                }
////                } else {
////                    return "You are trying to insert duplicate data.";
////                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return "";
    //}
}
