/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.payroll;

import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.adaptor.PayrollObjectAdaptor;
import com.hrms.common.complexTO.TaxInvestmentCategoryTO;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.EmpSalaryAllocationGridTO;
import com.hrms.common.to.GetSalaryAllocationTO;
import com.hrms.common.to.HrAttendanceMaintenanceTO;
import com.hrms.common.to.HrHolidayMasterTO;
import com.hrms.common.to.HrHolidayMasterTOPK;
import com.hrms.common.to.HrLeavePostingTO;
import com.hrms.common.to.HrLeaveRegisterTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrSalaryStructurePKTO;
import com.hrms.common.to.HrSalaryStructureTO;
import com.hrms.common.to.HrShiftMapTO;
import com.hrms.common.to.HrSlabMasterTO;
import com.hrms.common.to.HrTaxInvestmentCategoryTO;
import com.hrms.common.to.HrTaxSlabMasterTO;
import com.hrms.common.to.SalaryAllocationTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.common.utils.Validator;
import com.hrms.dao.CompanyMasterDAO;
import com.hrms.dao.HrAttendanceDetailsDAO;
import com.hrms.dao.HrAttendanceMaintainDAO;
import com.hrms.dao.HrHolidayMasterDAO;
import com.hrms.dao.HrLeaveMasterDAO;
import com.hrms.dao.HrLeavePostingDAO;
import com.hrms.dao.HrLeaveRegisterDAO;
import com.hrms.dao.HrMstShiftDAO;
import com.hrms.dao.HrMstStructDAO;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.HrSalaryAllocationDAO;
import com.hrms.dao.HrSalaryProcessingDAO;
import com.hrms.dao.HrSalaryStructureDAO;
import com.hrms.dao.HrShiftMapDAO;
import com.hrms.dao.HrSlabMasterDAO;
import com.hrms.dao.HrTaxInvestmentCategoryDAO;
import com.hrms.dao.HrTaxSlabMasterDAO;
import com.hrms.dao.LeaveMasterDAO;
import com.hrms.dao.PayrollCalendarDAO;
import com.hrms.dao.PayrollCloseTraceDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.NullEntityException;
import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrMstStruct;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.hr.HrSalaryStructure;
import com.hrms.entity.hr.HrSalaryStructurePK;
import com.hrms.entity.payroll.CompanyMaster;
import com.hrms.entity.payroll.HrAttendanceDetails;
import com.hrms.entity.payroll.HrAttendanceDetailsPK;
import com.hrms.entity.payroll.HrAttendanceMaintenance;
import com.hrms.entity.payroll.HrHolidayMaster;
import com.hrms.entity.payroll.HrLeaveMaster;
import com.hrms.entity.payroll.HrLeavePosting;
import com.hrms.entity.payroll.HrLeavePostingPK;
import com.hrms.entity.payroll.HrLeaveRegister;
import com.hrms.entity.payroll.HrMstShift;
import com.hrms.entity.payroll.HrPayrollCalendar;
import com.hrms.entity.payroll.HrPayrollCalendarPK;
import com.hrms.entity.payroll.HrPayrollCloseTrace;
import com.hrms.entity.payroll.HrPayrollCloseTracePK;
import com.hrms.entity.payroll.HrSalaryAllocation;
import com.hrms.entity.payroll.HrSalaryAllocationPK;
import com.hrms.entity.payroll.HrSalaryProcessing;
import com.hrms.entity.payroll.HrSalaryProcessingPK;
import com.hrms.entity.payroll.HrShiftMap;
import com.hrms.entity.payroll.HrSlabMaster;
import com.hrms.entity.payroll.HrSlabMasterPK;
import com.hrms.entity.payroll.HrTaxInvestmentCategory;
import com.hrms.entity.payroll.HrTaxSlabMaster;
import com.hrms.to.other.CommonComparator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Bisht
 */
@Stateless(mappedName = "PayrollMasterFacade")
@Remote({PayrollMasterFacadeRemote.class})
public class PayrollMasterFacade implements PayrollMasterFacadeRemote {

    private static final Logger logger = Logger.getLogger(PayrollMasterFacade.class);
    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<HrMstStructTO> getIntialData(int compCode, String structCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, structCode);
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
    public List<LeaveMasterTO> getLeaveMasterData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrLeaveMasterDAO hrLeaveMasterDAO = new HrLeaveMasterDAO(em);
        List<LeaveMasterTO> leaveMasterTOs = new ArrayList<LeaveMasterTO>();
        try {
            List<HrLeaveMaster> hrLeaveMasterList = hrLeaveMasterDAO.findEntityByCompCode(compCode);
            for (HrLeaveMaster hrLeaveMaster : hrLeaveMasterList) {
                leaveMasterTOs.add(PayrollObjectAdaptor.adaptToHrLeaveMasterTO(hrLeaveMaster));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getLeaveMasterData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLeaveMasterData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getLeaveMasterData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return leaveMasterTOs;

    }

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
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

    /**
     *
     * @param leavmstObj
     * @return
     * @throws ApplicationException
     */
    @Override
    public String saveUpdateDeleteLeaveDetail(LeaveMasterTO leavmstObj, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(leavmstObj);
        String msg = "";

        HrLeaveMaster hrLeaveMaster = null;
        try {
            LeaveMasterDAO leaveMasterDao = new LeaveMasterDAO(em);
            hrLeaveMaster = PayrollObjectAdaptor.adaptToHrLeaveMasterEntity(leavmstObj);
            if (mode.equalsIgnoreCase("save")) {
                leaveMasterDao.save(hrLeaveMaster);
                msg = "Data has been successfully saved.";
            }
            if (mode.equalsIgnoreCase("update")) {
                leaveMasterDao.update(hrLeaveMaster);
                msg = "Data has been successfully saved.";
            }
            if (mode.equalsIgnoreCase("delete")) {
                leaveMasterDao.delete(hrLeaveMaster, hrLeaveMaster.getHrLeaveMasterPK());
                msg = "Data has been successfully deleted.";
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
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveLeaveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

    /**
     * ********* Payroll master code **
     */
    /**
     * Function to save the calendar in open mode and delete the Open payroll
     * calendar . Once a calendar has been closed , cannot be reopen again.
     *
     * @param payrollCalendarTO
     * @param mod
     * @return
     * @throws ApplicationException
     */
    @Override
    public String payrollCalendarSaveDelete(PayrollCalendarTO payrollCalendarTO, String mode) throws ApplicationException {
        Validator.isNull(payrollCalendarTO);
        try {
            PayrollCalendarDAO payrollCalendarDAO = new PayrollCalendarDAO(em);
            HrPayrollCalendar hrPayrollCalendar = new HrPayrollCalendar();
            hrPayrollCalendar = PayrollObjectAdaptor.adaptToPayrollCalendarEntity(payrollCalendarTO);
            List<HrPayrollCalendar> payRollcalendarList = payrollCalendarDAO.findSavedPayrollCalendar(hrPayrollCalendar);
            if (mode.equalsIgnoreCase("SAVE")) {
                if (payRollcalendarList.isEmpty()) {
                    payrollCalendarDAO.save(hrPayrollCalendar);
                    return "Payroll calendar saved successfully";
                } else {
                    return "Payroll calendar already exist!!!";
                }
            } else {
                if (!payRollcalendarList.isEmpty()) {
                    payrollCalendarDAO.delete(hrPayrollCalendar, hrPayrollCalendar.getHrPayrollCalendarPK());
                    return "Calendar deleted successfully";
                } else {
                    return "Payroll calendar does not exist or close!!!";
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<PayrollCalendarTO> displayPayrollCalendarGrid(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        PayrollCalendarDAO payrollCalendarDAO = new PayrollCalendarDAO(em);
        List<PayrollCalendarTO> payrollCalendarTOs = new ArrayList<PayrollCalendarTO>();
        try {
            List<HrPayrollCalendar> hrPayrollCalendarList = payrollCalendarDAO.displayPayRollCalendarGrid(compCode);
            for (HrPayrollCalendar hrPayrollCalendar : hrPayrollCalendarList) {
                payrollCalendarTOs.add(PayrollObjectAdaptor.adaptToPayrollCalendarTO(hrPayrollCalendar));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method displayPayRollCalendarGrid()", e);
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
            logger.error("Exception occured while executing method displayPayRollCalendarGrid()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for displayPayRollCalendarGrid is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return payrollCalendarTOs;

    }

    /**
     * Function to close the open payroll calendar.
     *
     * @param closeYearButtonTO
     * @return
     * @throws ApplicationException
     */
    @Override
    public String closePayrollCalendar(HrPayrollCalendar hrPayrollCalendar, String closeMode) throws ApplicationException {
        long begin = System.nanoTime();
        int compCode = hrPayrollCalendar.getHrPayrollCalendarPK().getCompCode();
        Date selectFromDate = hrPayrollCalendar.getHrPayrollCalendarPK().getDateFrom();
        Date selectToDate = hrPayrollCalendar.getHrPayrollCalendarPK().getDateTo();
        String enterBy = hrPayrollCalendar.getAuthBy();
        int count = 0;
        String message = "false";
        try {
            String closeFlag = null;
            Date varFromDate;
            HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
            PayrollCloseTraceDAO payrollCloseTraceDAO = new PayrollCloseTraceDAO(em);

            HrLeavePostingDAO hrLeavePostingDAO = new HrLeavePostingDAO(em);
            HrShiftMapDAO hrShiftMapDAO = new HrShiftMapDAO(em);
            HrSalaryAllocationDAO hrSalaryAllocationDAO = new HrSalaryAllocationDAO(em);

            HrAttendanceDetailsDAO hrAttendanceDetailsDAO = new HrAttendanceDetailsDAO(em);
            HrSalaryProcessingDAO hrSalaryProcessingDAO = new HrSalaryProcessingDAO(em);
            List<HrPersonnelDetails> employeeList = hrPersonnelDetailsDAO.findEmpByCompCodeWithStatusY(compCode);

            if (employeeList.isEmpty()) {
                throw new ApplicationException("No active employees in this company");
            }
            List<HrPayrollCloseTrace> hrPayrollCloseTraceList = payrollCloseTraceDAO.findByFrmDateAndToDate(selectFromDate, selectToDate);
            if (!hrPayrollCloseTraceList.isEmpty()) {
                for (HrPayrollCloseTrace hrPayrollCloseTrace : hrPayrollCloseTraceList) {
                    payrollCloseTraceDAO.delete(hrPayrollCloseTrace, hrPayrollCloseTrace.getHrPayrollCloseTracePK());
                }
            }
            for (HrPersonnelDetails hrPersonnel : employeeList) {
                Long empCode = hrPersonnel.getHrPersonnelDetailsPK().getEmpCode().longValue();
                String empid = hrPersonnel.getEmpId();
                String empName = hrPersonnel.getEmpName();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                String fromDate = sdf.format(selectFromDate);
                String toDate = sdf.format(selectToDate);
                HrLeavePostingPK hrleavePostingPK = new HrLeavePostingPK();

                hrleavePostingPK.setCompCode(compCode);
                hrleavePostingPK.setEmpCode(empCode);
                hrleavePostingPK.setDateFrom(fromDate);
                hrleavePostingPK.setDateTo(toDate);
                List<HrLeavePosting> hrleavePostingList = hrLeavePostingDAO.findhrLeavePosting(hrleavePostingPK);

                if (hrleavePostingList.isEmpty()) {
                    closeFlag = "OPEN";
                    int traceProblemCode = payrollCloseTraceDAO.findTraceProblemCode();
                    HrPayrollCloseTrace closeTrace = new HrPayrollCloseTrace();
                    HrPayrollCloseTracePK closetracePk = new HrPayrollCloseTracePK();

                    closetracePk.setCalDateFrom(selectFromDate);
                    closetracePk.setCalDateTo(selectToDate);
                    closetracePk.setCompCode(compCode);
                    closetracePk.setEmpCode(empCode);

                    closetracePk.setTracedProblemCode(traceProblemCode);
                    closeTrace.setHrPayrollCloseTracePK(closetracePk);
                    closeTrace.setTracedProblem("LEAVE IS NOT ALLOCATED TO" + empid + ":" + empName + " FOR THE YEAR" + selectFromDate + "TO" + selectToDate);
                    payrollCloseTraceDAO.save(closeTrace);
                }
                List<HrShiftMap> shiftMapList = hrShiftMapDAO.hrShiftMap(compCode, empCode);
                if (shiftMapList.isEmpty()) {
                    closeFlag = "OPEN";
                    int traceProblemCode = payrollCloseTraceDAO.findTraceProblemCode();
                    HrPayrollCloseTrace closeTrace = new HrPayrollCloseTrace();
                    HrPayrollCloseTracePK closetracePk = new HrPayrollCloseTracePK();
                    closetracePk.setCalDateFrom(selectFromDate);
                    closetracePk.setCalDateTo(selectToDate);

                    closetracePk.setCompCode(compCode);
                    closetracePk.setEmpCode(empCode);
                    closetracePk.setTracedProblemCode(traceProblemCode);

                    closeTrace.setHrPayrollCloseTracePK(closetracePk);
                    closeTrace.setTracedProblem("SHIFT IS NOT MAPPED FOR" + empid + ":" + empName);
                    payrollCloseTraceDAO.save(closeTrace);
                }
                varFromDate = selectFromDate;
                while (varFromDate.before(selectToDate) || varFromDate.equals(selectToDate)) {
                    SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
                    String varDate = sd.format(varFromDate);
                    int year = Integer.parseInt(varDate.substring(0, 4));
                    String month = varDate.substring(4, 6);
                    HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
                    hrSalaryAllocationPK.setCompCode(compCode);
                    hrSalaryAllocationPK.setEmpCode(empCode);

                    List<HrSalaryAllocation> salaryAllocationForMonth = hrSalaryAllocationDAO.getSalaryAllocateForMonth(hrSalaryAllocationPK);
                    if (salaryAllocationForMonth.isEmpty()) {
                        closeFlag = "OPEN";
                        int traceProblemCode = payrollCloseTraceDAO.findTraceProblemCode();
                        HrPayrollCloseTrace closeTrace = new HrPayrollCloseTrace();
                        HrPayrollCloseTracePK closetracePk = new HrPayrollCloseTracePK();
                        closetracePk.setCalDateFrom(selectFromDate);
                        closetracePk.setCalDateTo(selectToDate);
                        closetracePk.setCompCode(compCode);

                        closetracePk.setEmpCode(empCode);
                        closetracePk.setTracedProblemCode(traceProblemCode);
                        closeTrace.setHrPayrollCloseTracePK(closetracePk);
                        closeTrace.setTracedProblem("SALARY IS NOT ALLOCATED FOR" + empid + ":" + empName + "FOR THE MONTH OF " + month + "IN THE YEAR " + year);
                        payrollCloseTraceDAO.save(closeTrace);
                    }
                    HrAttendanceDetailsPK hrAttendanceDetailsPK = new HrAttendanceDetailsPK();
                    HrAttendanceDetails hrAttendanceDetails = new HrAttendanceDetails();
                    hrAttendanceDetailsPK.setAttenMonth(month);
                    hrAttendanceDetailsPK.setAttenYear(year);
                    hrAttendanceDetailsPK.setCompCode(compCode);
                    hrAttendanceDetailsPK.setEmpCode(empCode);
                    hrAttendanceDetails.setPostFlag('Y');
                    hrAttendanceDetails.setHrAttendanceDetailsPK(hrAttendanceDetailsPK);
                    List<HrAttendanceDetails> attendanceDetailsList = hrAttendanceDetailsDAO.findAttendanceDetailsPostedForMonth(hrAttendanceDetails);
                    if (attendanceDetailsList.isEmpty()) {
                        closeFlag = "OPEN";
                        int traceProblemCode = payrollCloseTraceDAO.findTraceProblemCode();
                        HrPayrollCloseTrace closeTrace = new HrPayrollCloseTrace();
                        HrPayrollCloseTracePK closetracePk = new HrPayrollCloseTracePK();
                        closetracePk.setCalDateFrom(selectFromDate);
                        closetracePk.setCalDateTo(selectToDate);
                        closetracePk.setCompCode(compCode);
                        closetracePk.setEmpCode(empCode);
                        closetracePk.setTracedProblemCode(traceProblemCode);
                        closeTrace.setHrPayrollCloseTracePK(closetracePk);
                        closeTrace.setTracedProblem("ATTENDANCE IS NOT POSTED FOR" + empid + ":" + empName + "FOR THE MONTH OF " + month + "IN THE YEAR " + year);
                        payrollCloseTraceDAO.save(closeTrace);
                    }

                    HrSalaryProcessing hrSalaryProcessing = new HrSalaryProcessing();
                    HrSalaryProcessingPK hrSalaryProcessingPK = new HrSalaryProcessingPK();
                    hrSalaryProcessingPK.setCalDateFrom(selectFromDate);
                    hrSalaryProcessingPK.setCalDateTo(selectToDate);
                    hrSalaryProcessingPK.setCompCode(compCode);
                    hrSalaryProcessingPK.setEmpCode(empCode);
                    hrSalaryProcessingPK.setMonths(month);
                    hrSalaryProcessing.setHrSalaryProcessingPK(hrSalaryProcessingPK);
                    hrSalaryProcessing.setPostFlag('Y');
                    List<HrSalaryProcessing> empSalaryPostedForTheMonth = hrSalaryProcessingDAO.getSalaryForTheMonthWithPostFlag(hrSalaryProcessing);
                    if (empSalaryPostedForTheMonth.isEmpty()) {
                        closeFlag = "OPEN";
                        int traceProblemCode = payrollCloseTraceDAO.findTraceProblemCode();
                        HrPayrollCloseTrace closeTrace = new HrPayrollCloseTrace();
                        HrPayrollCloseTracePK closetracePk = new HrPayrollCloseTracePK();
                        closetracePk.setCalDateFrom(selectFromDate);
                        closetracePk.setCalDateTo(selectToDate);
                        closetracePk.setCompCode(compCode);
                        closetracePk.setEmpCode(empCode);
                        closetracePk.setTracedProblemCode(traceProblemCode);
                        closeTrace.setHrPayrollCloseTracePK(closetracePk);
                        closeTrace.setTracedProblem("SALARY IS NOT POSTED FOR" + empid + ":" + empName + "FOR THE MONTH OF " + month + "IN THE YEAR " + year);
                        payrollCloseTraceDAO.save(closeTrace);
                    }
                    List<HrSalaryProcessing> empsalTranForMonth = hrSalaryProcessingDAO.getempSalTransForTheMonth(compCode, empCode, selectFromDate, selectToDate, month, 'Y', 'Y');
                    if (empsalTranForMonth.isEmpty()) {
                        closeFlag = "OPEN";
                        int traceProblemCode = payrollCloseTraceDAO.findTraceProblemCode();
                        HrPayrollCloseTrace closeTrace = new HrPayrollCloseTrace();
                        HrPayrollCloseTracePK closetracePk = new HrPayrollCloseTracePK();
                        closetracePk.setCalDateFrom(selectFromDate);
                        closetracePk.setCalDateTo(selectToDate);
                        closetracePk.setCompCode(compCode);
                        closetracePk.setEmpCode(empCode);
                        closetracePk.setTracedProblemCode(traceProblemCode);
                        closeTrace.setHrPayrollCloseTracePK(closetracePk);
                        closeTrace.setTracedProblem("SALARY IS NOT TRANSFERRED TO" + empid + ":" + empName + "ACCOUNT FOR THE MONTH OF " + month + "IN THE YEAR " + year);
                        payrollCloseTraceDAO.save(closeTrace);
                    }
                    Calendar c = Calendar.getInstance();
                    c.setTime(varFromDate);
                    c.add(Calendar.MONTH, 1);
                    varFromDate = c.getTime();
                }
            }
            if (closeMode.equalsIgnoreCase("AUTOMATED")) {
                if (closeFlag.equalsIgnoreCase("CLOSE")) {
                    PayrollCalendarDAO payrollDAO = new PayrollCalendarDAO(em);
                    hrPayrollCalendar = new HrPayrollCalendar();
                    HrPayrollCalendarPK hrPayrollCalendarPK = new HrPayrollCalendarPK();
                    hrPayrollCalendarPK.setCompCode(compCode);
                    hrPayrollCalendarPK.setDateFrom(selectFromDate);
                    hrPayrollCalendarPK.setDateTo(selectToDate);
                    hrPayrollCalendar.setStatusFlag("OPEN");
                    hrPayrollCalendar.setHrPayrollCalendarPK(hrPayrollCalendarPK);
                    List<HrPayrollCalendar> payrollCalendarList = payrollDAO.findSavedPayrollCalendar(hrPayrollCalendar);
                    if (!payrollCalendarList.isEmpty()) {
                        for (HrPayrollCalendar hrPayrollCalendarEntity : payrollCalendarList) {
                            hrPayrollCalendarEntity.setStatusFlag("CLOSE");
                            hrPayrollCalendarEntity.setAuthBy(enterBy);
                            hrPayrollCalendarEntity.setEnteredBy(enterBy);
                            payrollDAO.update(hrPayrollCalendarEntity);
                            count++;
                        }
                    } else {
                        return "Calendar does not exist or not open!!!";
                    }
                    if (count != 0) {
                        return "Payroll calendar closed successfully";
                    } else {
                        return "Unable to close!!!";
                    }
                }
                if (closeFlag.equalsIgnoreCase("OPEN")) {
                    return "Selected payroll calendar cannot be closed in automated mode!!!";
                }
            }
            if (closeMode.equalsIgnoreCase("MANUAL")) {
                PayrollCalendarDAO payrollDAO = new PayrollCalendarDAO(em);
                hrPayrollCalendar = new HrPayrollCalendar();
                HrPayrollCalendarPK hrPayrollCalendarPK = new HrPayrollCalendarPK();
                hrPayrollCalendarPK.setCompCode(compCode);
                hrPayrollCalendarPK.setDateFrom(selectFromDate);
                hrPayrollCalendarPK.setDateTo(selectToDate);
                hrPayrollCalendar.setStatusFlag("OPEN");
                hrPayrollCalendar.setHrPayrollCalendarPK(hrPayrollCalendarPK);
                List<HrPayrollCalendar> payrollCalendarList = payrollDAO.findSavedPayrollCalendar(hrPayrollCalendar);
                if (!payrollCalendarList.isEmpty()) {
                    for (HrPayrollCalendar hrPayrollCalendarEntity : payrollCalendarList) {
                        hrPayrollCalendarEntity.setStatusFlag("CLOSE");
                        hrPayrollCalendarEntity.setAuthBy(enterBy);
                        hrPayrollCalendarEntity.setEnteredBy(enterBy);
                        payrollDAO.update(hrPayrollCalendarEntity);
                        count++;
                    }
                } else {
                    return "Calendar does not exist or not open!!!";
                }
                if (count != 0) {
                    return "Payroll calendar closed successfully";
                } else {
                    return "Unable to close!!!";
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return message;
    }

    /**
     * ****************************** code for IncomeTax
     * ****************************************
     */
    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<HrSalaryStructureTO> gettaxableComponent(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrSalaryStructureDAO hrSalaryStructureDAO = new HrSalaryStructureDAO(em);
        List<HrSalaryStructureTO> hrSalaryStructureTO = new ArrayList<HrSalaryStructureTO>();
        try {
            List<HrSalaryStructure> hrSalaryStructureList = hrSalaryStructureDAO.getTaxableComponent(compCode);
            for (HrSalaryStructure hrSalaryStructure : hrSalaryStructureList) {
                hrSalaryStructureTO.add(ObjectAdaptorHr.adaptToHrSalaryStructureTO(hrSalaryStructure));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method gettaxableComponent()", e);
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
            logger.error("Exception occured while executing method gettaxableComponent()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for gettaxableComponent is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrSalaryStructureTO;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<PayrollCalendarTO> getOpenCalendarList(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        PayrollCalendarDAO payrollCalendarDAO = new PayrollCalendarDAO(em);
        List<PayrollCalendarTO> payrollCalendarTOs = new ArrayList<PayrollCalendarTO>();
        try {
            List<HrPayrollCalendar> hrPayrollCalendarList = payrollCalendarDAO.getOpencalendarList(compCode);
            for (HrPayrollCalendar hrPayrollCalendar : hrPayrollCalendarList) {
                payrollCalendarTOs.add(PayrollObjectAdaptor.adaptToPayrollCalendarTO(hrPayrollCalendar));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getOpenCalendarList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return payrollCalendarTOs;
    }

    /**
     * ******************** code for Hr salary structure
     * *************************
     */
    @Override
//    public List getHrSalaryStructure(int compCode, String fromDate, String toDate) throws ApplicationException {
    public List getHrSalaryStructure(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        List hrSalaryStructureList = new ArrayList();
        try {
            HrSalaryStructureDAO hrSalaryStructureDAO = new HrSalaryStructureDAO(em);
//            hrSalaryStructureList = hrSalaryStructureDAO.getHrSalryStructure(compCode, fromDate, toDate);
            hrSalaryStructureList = hrSalaryStructureDAO.getHrSalryStructure(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveHrTaxSalary()", e);
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
            logger.error("Exception occured while executing method saveHrTaxSalary()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveHrTaxSalary is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrSalaryStructureList;
    }

    @Override
    public String saveHrSalaryStructure(HrSalaryStructureTO hrSalaryStructureTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        String msg = "false";
        try {
            HrSlabMasterDAO hrSlabMasterDAO = new HrSlabMasterDAO(em);
            HrSalaryStructurePK hrSalaryStructurePK = new HrSalaryStructurePK();
            hrSalaryStructurePK.setAlDesc(hrSalaryStructureTO.getHrSalaryStructurePKTO().getAlDesc());
            hrSalaryStructurePK.setCompCode(hrSalaryStructureTO.getHrSalaryStructurePKTO().getCompCode());
//            hrSalaryStructurePK.setDateFrom(hrSalaryStructureTO.getHrSalaryStructurePKTO().getDateFrom());
//            hrSalaryStructurePK.setDateTo(hrSalaryStructureTO.getHrSalaryStructurePKTO().getDateTo());
            hrSalaryStructurePK.setNature(hrSalaryStructureTO.getHrSalaryStructurePKTO().getNature());
            hrSalaryStructurePK.setPurposeCode(hrSalaryStructureTO.getHrSalaryStructurePKTO().getPurposeCode());
            HrSalaryStructureDAO hrSalaryStructureDAO = new HrSalaryStructureDAO(em);
            if (mode.equalsIgnoreCase("SAVE")) {
                List<HrSalaryStructure> salaryStructureList = hrSalaryStructureDAO.getHrSalaryStructByKey(hrSalaryStructurePK);
                if (salaryStructureList.isEmpty()) {
                    hrSalaryStructureDAO.save(ObjectAdaptorHr.adaptToHrSalaryStructureEntity(hrSalaryStructureTO));
                    return "Data saved successfully ! Please Add Also to the Required Employee ";
                } else {
                    return "Data already exist!!!";
                }
            }
            if (mode.equalsIgnoreCase("UPDATE")) {
                List<HrSalaryStructure> salaryStructureList = hrSalaryStructureDAO.getHrSalaryStructByKey(hrSalaryStructurePK);
                if (!salaryStructureList.isEmpty()) {
                    hrSalaryStructureDAO.update(ObjectAdaptorHr.adaptToHrSalaryStructureEntity(hrSalaryStructureTO));
                    return "Data updated successfully";
                } else {
                    return "No such data exist for updation";
                }
            }
            if (mode.equalsIgnoreCase("DELETE")) {
                HrSlabMaster hrSlabMaster = new HrSlabMaster();
                HrSlabMasterPK hrSlabMasterPK = new HrSlabMasterPK();
                hrSlabMasterPK.setBaseComponent(hrSalaryStructureTO.getHrSalaryStructurePKTO().getAlDesc());
                hrSlabMasterPK.setDependComponent(hrSalaryStructureTO.getHrSalaryStructurePKTO().getAlDesc());
                hrSlabMasterPK.setCompCode(hrSalaryStructureTO.getHrSalaryStructurePKTO().getCompCode());
                hrSlabMasterPK.setNature(hrSalaryStructureTO.getHrSalaryStructurePKTO().getNature());
                hrSlabMasterPK.setPurposeCode(hrSalaryStructureTO.getHrSalaryStructurePKTO().getPurposeCode());
                hrSlabMaster.setHrSlabMasterPK(hrSlabMasterPK);
                List<HrSlabMaster> hrSlabMasterList = hrSlabMasterDAO.getSlabMasterByParam(hrSlabMaster);
                if (!hrSlabMasterList.isEmpty()) {
                    return "Structure can't be delete because slab is defined";
                }

                List<HrSalaryStructure> salaryStructureList = hrSalaryStructureDAO.getHrSalaryStructByKey(hrSalaryStructurePK);
                if (!salaryStructureList.isEmpty()) {
                    hrSalaryStructureDAO.delete(ObjectAdaptorHr.adaptToHrSalaryStructureEntity(hrSalaryStructureTO), ObjectAdaptorHr.adaptToHrSalaryStructureEntity(hrSalaryStructureTO).getHrSalaryStructurePK());
                    return "Data deleted successfully";
                } else {
                    return "No such data exist for deletion";
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveHrSalaryStructure()", e);
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
            logger.error("Exception occured while executing method saveHrSalaryStructure()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveHrSalaryStructure is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

    @Override
    public List<HrSalaryStructureTO> getSlabMasterInitialData(HrSalaryStructurePKTO hrSalaryStructurePKTO) throws ApplicationException {
        long begin = System.nanoTime();
        HrSalaryStructureDAO hrSalaryStructureDAO = new HrSalaryStructureDAO(em);
        List<HrSalaryStructureTO> hrSalaryStructureTOs = new ArrayList<HrSalaryStructureTO>();
        try {
            List<HrSalaryStructure> hrSalaryStructureList = hrSalaryStructureDAO.getHrSalryStructureDescription(ObjectAdaptorHr.adaptToHrSalaryStructurePKEntity(hrSalaryStructurePKTO));
            for (HrSalaryStructure hrSalaryStructure : hrSalaryStructureList) {
                hrSalaryStructureTOs.add(ObjectAdaptorHr.adaptToHrSalaryStructureTO(hrSalaryStructure));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSlabMasterInitialData()", e);
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
            logger.error("Exception occured while executing method getSlabMasterInitialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSlabMasterInitialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrSalaryStructureTOs;

    }

    @Override
    public List getSlabMasterStructureGrid(int compCode, String purposeCode, Date fromDate, Date toDate) throws ApplicationException {
        long begin = System.nanoTime();
        List hrSlabMasterStructureList = new ArrayList();
        try {
            HrSlabMasterDAO hrSlabMasterDAO = new HrSlabMasterDAO(em);
            hrSlabMasterStructureList = hrSlabMasterDAO.getSlabMasterStructure(compCode, purposeCode, fromDate, toDate);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSlabMasterStructureGrid()", e);
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
            logger.error("Exception occured while executing method getSlabMasterStructureGrid()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSlabMasterStructureGrid is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrSlabMasterStructureList;
    }

    @Override
    public String saveSlabStructure(HrSlabMasterTO hrSlabMasterTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        String msg = "false";
        try {
            HrSlabMaster hrSlabMaster = new HrSlabMaster();
            HrSlabMasterPK hrSlabMasterPK = new HrSlabMasterPK();
            HrSlabMasterDAO hrSlabMasterDAO = new HrSlabMasterDAO(em);
//            if (mode.equalsIgnoreCase("SAVE")) {
//                hrSlabMaster.setAlDesc(hrSlabMasterTO.getHrSlabMasterPK().getAlDesc());
//                hrSlabMasterPK.setCompCode(hrSlabMasterTO.getHrSlabMasterPK().getCompCode());
////                hrSlabMasterPK.setDateFrom(hrSlabMasterTO.getHrSlabMasterPK().getDateFrom());
////                hrSlabMasterPK.setDateTo(hrSlabMasterTO.getHrSlabMasterPK().getDateTo());
//                hrSlabMasterPK.setNature(hrSlabMasterTO.getHrSlabMasterPK().getNature());
//                hrSlabMasterPK.setPurposeCode(hrSlabMasterTO.getHrSlabMasterPK().getPurposeCode());
//                hrSlabMaster.setHrSlabMasterPK(hrSlabMasterPK);
//                hrSlabMaster.setRangeFrom(hrSlabMasterTO.getRangeFrom());
//                hrSlabMaster.setRangeTo(hrSlabMasterTO.getRangeTo());
//                List<HrSlabMaster> slabMasterList = hrSlabMasterDAO.getSlabMasterByKey(hrSlabMaster);
//                if (slabMasterList.isEmpty()) {
//                    String slabCode = hrSlabMasterDAO.calculateSlabCode();
//                    hrSlabMasterTO.getHrSlabMasterPK().setSlabCode(slabCode);
//                    hrSlabMasterDAO.save(PayrollObjectAdaptor.adaptToHrSlabMasterEntity(hrSlabMasterTO));
//                    return "Data saved successfully";
//                } else {
//                    return "Data already exist!!!";
//                }
//            }
            if (mode.equalsIgnoreCase("UPDATE")) {
//                hrSlabMaster.setAlDesc(hrSlabMasterTO.getAlDesc());
                hrSlabMasterPK.setCompCode(hrSlabMasterTO.getHrSlabMasterPK().getCompCode());
//                hrSlabMasterPK.setDateFrom(hrSlabMasterTO.getHrSlabMasterPK().getDateFrom());
//                hrSlabMasterPK.setDateTo(hrSlabMasterTO.getHrSlabMasterPK().getDateTo());
                hrSlabMasterPK.setNature(hrSlabMasterTO.getHrSlabMasterPK().getNature());
                hrSlabMasterPK.setPurposeCode(hrSlabMasterTO.getHrSlabMasterPK().getPurposeCode());
                hrSlabMasterPK.setSlabCode(hrSlabMasterTO.getHrSlabMasterPK().getSlabCode());
                hrSlabMaster.setHrSlabMasterPK(hrSlabMasterPK);
                List<HrSlabMaster> slabMasterList = hrSlabMasterDAO.slabMasterFindEntitiesWithourAPpflag(hrSlabMaster);
                if (!slabMasterList.isEmpty()) {
                    hrSlabMasterDAO.update(PayrollObjectAdaptor.adaptToHrSlabMasterEntity(hrSlabMasterTO));
                    return "Data updated successfully, Update Employee Detail Accordingly";
                } else {
                    return "NO such data exist for updation!!!";
                }
            }
            if (mode.equalsIgnoreCase("DELETE")) {
                HrSlabMaster hrSlabMaster1 = PayrollObjectAdaptor.adaptToHrSlabMasterEntity(hrSlabMasterTO);
//                hrSlabMaster1.setAppFlg('N');
                List<HrSlabMaster> hrSlabMasterList = hrSlabMasterDAO.slabMasterFindEntities(hrSlabMaster1);
                if (!hrSlabMasterList.isEmpty()) {
                    for (HrSlabMaster hrSlabMasterEntity : hrSlabMasterList) {
                        hrSlabMasterDAO.delete(hrSlabMasterEntity, hrSlabMasterEntity.getHrSlabMasterPK());
                    }
                    return "Data deleted successfully";
                } else {
                    return "No such data exist for deletion!!!";
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveSlabStructure()", e);
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
            logger.error("Exception occured while executing method saveSlabStructure()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveSlabStructure is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

//    @Override
//    public String insertSalaryallocation(SalaryAllocationTO salaryAllocationTO, List<EmpSalaryAllocationGridTO>salaryAddGrid) throws ApplicationException {
//        long begin = System.nanoTime();
//        String selectionCriteria = salaryAllocationTO.getSelectionCriteria(),
//                selectedValues = salaryAllocationTO.getSelectedValues(),
//                updateMonth = salaryAllocationTO.getUpdateMonth(),
//                updatedComponent = salaryAllocationTO.getUpdatedComponent(),
//                authBy = salaryAllocationTO.getAuthBy(),
//                enteredBy = salaryAllocationTO.getEnteredBy(),
//                modeFlag = salaryAllocationTO.getModeFlag(),
//                message = "false";
//        double basicSalary = Double.parseDouble(salaryAllocationTO.getBasicSalary()),
//                updatedComponentAmount = Float.parseFloat(salaryAllocationTO.getUpdatedComponentAmount());
//        char statFlag = salaryAllocationTO.getStatFlag(),
//                statUpFlag = salaryAllocationTO.getStatUpFlag();
//        int defaultComp = salaryAllocationTO.getDefaultComp(),
//                compCode = salaryAllocationTO.getCompanyCode(),
//                count = 0;
//
//        /**
//         * **** declared values **************
//         */
//        long empCode = 0;
//        Date varFromDate,
//                allocationDate = salaryAllocationTO.getAllocationDate(), //Salary Alocation Date
//                calDateFrom = salaryAllocationTO.getCalDateFrom(), //Financial Start Date
//                calDateTo = salaryAllocationTO.getCalDateTo(); //Financial End Date
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        String calDateFromString = formatter.format(calDateFrom),
//                calDateToString = formatter.format(calDateTo);
//        int monthOfYear;
//        String monthName, newPurposeCode, newNature, newAlDesc, slabCriteria = null, flag, updatePurposeCode;
//        double slabCriteriaAmount = 0, varAmount;
//        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
//        HrSalaryAllocationDAO hrSalaryAllocationDAO = new HrSalaryAllocationDAO(em);
//        HrSalaryStructureDAO hrSalaryStructureDAO = new HrSalaryStructureDAO(em);
//        HrSlabMasterDAO hrSlabMasterDAO = new HrSlabMasterDAO(em);
//        try {
//            if (modeFlag.equalsIgnoreCase("SAVE") || modeFlag.equalsIgnoreCase("UPDATE")) {
//                if (selectionCriteria.equalsIgnoreCase("EWE")) {
//                    HrPersonnelDetails hrPersonnelDetailsEntity = hrPersonnelDetailsDAO.findByEmpStatusAndEmpId(compCode, selectedValues, 'Y'); //Retrieve The Active Employee If
//                    if (hrPersonnelDetailsEntity.getHrPersonnelDetailsPK() != null) {
//                        empCode = hrPersonnelDetailsEntity.getHrPersonnelDetailsPK().getEmpCode().longValue();
//                        varFromDate = allocationDate;
//                        while (varFromDate.before(calDateTo) || varFromDate.equals(calDateTo)) {
//                            Calendar c = Calendar.getInstance();
//                            c.setTime(varFromDate);
//                            monthOfYear = c.get(Calendar.MONTH);
//                            monthName = HrmsUtil.getMonthName(monthOfYear);
//                            List<HrSalaryStructure> hrSalaryStructureList = hrSalaryStructureDAO.hrSalStructByCompAndDates(compCode, calDateFromString, calDateToString);
//                            if (!hrSalaryStructureList.isEmpty()) {
//                                for (HrSalaryStructure hrSalaryStructure : hrSalaryStructureList) {
//                                    newPurposeCode = hrSalaryStructure.getHrSalaryStructurePK().getPurposeCode();
//                                    newNature = hrSalaryStructure.getHrSalaryStructurePK().getNature();
//                                    newAlDesc = hrSalaryStructure.getHrSalaryStructurePK().getAlDesc();
//                                    HrSlabMaster hrSlabMaster = new HrSlabMaster();
//                                    HrSlabMasterPK hrSlabMasterPK = new HrSlabMasterPK();
//                                    hrSlabMasterPK.setAlDesc(newAlDesc);
//                                    hrSlabMasterPK.setCompCode(compCode);
////                                    hrSlabMasterPK.setDateFrom(calDateFrom);
////                                    hrSlabMasterPK.setDateTo(calDateTo);
//                                    hrSlabMasterPK.setNature(newNature);
//                                    hrSlabMasterPK.setPurposeCode(newPurposeCode);
//                                    hrSlabMaster.setAppFlg('Y');
//                                    hrSlabMaster.setHrSlabMasterPK(hrSlabMasterPK);
//                                    List<HrSlabMaster> hrSlabMasterList = hrSlabMasterDAO.getSlabMasterByvariousParam(hrSlabMaster, basicSalary);
//                                    if (!hrSlabMasterList.isEmpty()) {
//                                        for (HrSlabMaster hrSlabMaster1 : hrSlabMasterList) {
//                                            slabCriteria = hrSlabMaster1.getSlabCriteria();
//                                            slabCriteriaAmount = hrSlabMaster1.getSlabCriteriaAmt();
//                                        }
//                                        HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                        hrSalaryAllocationPK.setCompCode(compCode);
//                                        hrSalaryAllocationPK.setEmpCode(empCode);
////                                        hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                                        hrSalaryAllocationPK.setCalDateTo(calDateToString);
////                                        hrSalaryAllocationPK.setMonths(monthName);
//                                        hrSalaryAllocationPK.setComponentType(newAlDesc);
//                                        List<HrSalaryAllocation> salaryAllocationList = hrSalaryAllocationDAO.findsalAllocByVariousParameters(hrSalaryAllocationPK);
//                                        if (salaryAllocationList.isEmpty()) {
//                                            if (slabCriteria.equalsIgnoreCase("PERCENTAGE")) {
//                                                varAmount = (slabCriteriaAmount / 100) * basicSalary;
//
////                                                HrSalaryAllocationPK hrSalaryAllocationPK1 = new HrSalaryAllocationPK();
////                                                hrSalaryAllocationPK1.setCalDateFrom(calDateFromString);
////                                                hrSalaryAllocationPK1.setCalDateTo(calDateToString);
////                                                hrSalaryAllocationPK1.setCompCode(compCode);
////                                                hrSalaryAllocationPK1.setComponentType(newAlDesc);
////                                                hrSalaryAllocationPK1.setEmpCode(empCode);
////                                                hrSalaryAllocationPK1.setMonths(monthName);
//                                                HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                                hrSalaryAllocation.setAllocationDate(allocationDate);
//                                                hrSalaryAllocation.setAuthBy(authBy);
//                                                hrSalaryAllocation.setBasicSalary(basicSalary);
//                                                hrSalaryAllocation.setComponentAmount(varAmount);
//                                                hrSalaryAllocation.setDefaultComp(defaultComp);
//                                                hrSalaryAllocation.setEnteredBy(enteredBy);
//                                                hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                                hrSalaryAllocation.setModDate(new Date());
//                                                hrSalaryAllocation.setStatFlag("Y");
//                                                hrSalaryAllocation.setStatUpFlag("Y");
//                                                hrSalaryAllocationDAO.save(hrSalaryAllocation);
//                                                count++;
//                                            }
//
//                                            if (slabCriteria.equalsIgnoreCase("AMOUNT")) {
//                                                varAmount = slabCriteriaAmount;
////                                                HrSalaryAllocationPK hrSalaryAllocationPK1 = new HrSalaryAllocationPK();
////                                                hrSalaryAllocationPK1.setCalDateFrom(calDateFromString);
////                                                hrSalaryAllocationPK1.setCalDateTo(calDateToString);
////                                                hrSalaryAllocationPK1.setCompCode(compCode);
////                                                hrSalaryAllocationPK1.setComponentType(newAlDesc);
////                                                hrSalaryAllocationPK1.setEmpCode(empCode);
////                                                hrSalaryAllocationPK1.setMonths(monthName);
//                                                HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                                hrSalaryAllocation.setAllocationDate(allocationDate);
//                                                hrSalaryAllocation.setAuthBy(authBy);
//                                                hrSalaryAllocation.setBasicSalary(basicSalary);
//                                                hrSalaryAllocation.setComponentAmount(varAmount);
//                                                hrSalaryAllocation.setDefaultComp(defaultComp);
//                                                hrSalaryAllocation.setEnteredBy(enteredBy);
//                                                hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                                hrSalaryAllocation.setModDate(new Date());
//                                                hrSalaryAllocation.setStatFlag("Y");
//                                                hrSalaryAllocation.setStatUpFlag("Y");
//                                                hrSalaryAllocationDAO.save(hrSalaryAllocation);
//                                                count++;
//                                            }
//
//                                        }
//                                    }
//                                }
//                            } else {
//                                return "Hr salary structure not found!!!";
//                            }
//                            if (modeFlag.equalsIgnoreCase("UPDATE")) {
//                                HrSalaryStructurePK hrSalaryStructurePK = new HrSalaryStructurePK();
//                                hrSalaryStructurePK.setAlDesc(updatedComponent);
//                                hrSalaryStructurePK.setCompCode(compCode);
////                                hrSalaryStructurePK.setDateFrom(calDateFromString);
////                                hrSalaryStructurePK.setDateTo(calDateToString);
//                                HrSalaryStructure hrsalaryStructure = hrSalaryStructureDAO.hrSalStructByCompAldescAndDates(hrSalaryStructurePK);
//                                updatePurposeCode = hrsalaryStructure.getHrSalaryStructurePK().getPurposeCode();
//                                if (updatePurposeCode.equalsIgnoreCase("PUR001")) {
//                                    HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
////                                    hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                                    hrSalaryAllocationPK.setCalDateTo(calDateToString);
//                                    hrSalaryAllocationPK.setCompCode(compCode);
//                                    hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                    hrSalaryAllocationPK.setEmpCode(empCode);
////                                    hrSalaryAllocationPK.setMonths(updateMonth);
//                                    HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                    hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                    hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                    hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                    hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                    hrSalaryAllocation.setModDate(new Date());
//                                    hrSalaryAllocation.setAuthBy(authBy);
//                                    hrSalaryAllocation.setEnteredBy(enteredBy);
//                                    hrSalaryAllocation.setAllocationDate(allocationDate);
//                                    hrSalaryAllocation.setBasicSalary(basicSalary);
//                                    hrSalaryAllocation.setDefaultComp(defaultComp);
//                                    hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                    count++;
//
//                                    if (updatedComponent.equalsIgnoreCase("BASIC")) {
//                                        hrSalaryAllocationPK = new HrSalaryAllocationPK();
//////                                        hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
//////                                        hrSalaryAllocationPK.setCalDateTo(calDateToString);
//                                        hrSalaryAllocationPK.setCompCode(compCode);
//                                        hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                        hrSalaryAllocationPK.setEmpCode(empCode);
////                                        hrSalaryAllocationPK.setMonths(updateMonth);
//                                        hrSalaryAllocation = new HrSalaryAllocation();
//                                        hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                        hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                        hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                        hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                        hrSalaryAllocation.setModDate(new Date());
//                                        hrSalaryAllocation.setAuthBy(authBy);
//                                        hrSalaryAllocation.setEnteredBy(enteredBy);
//                                        hrSalaryAllocation.setAllocationDate(allocationDate);
//                                        hrSalaryAllocation.setBasicSalary(updatedComponentAmount);
//                                        hrSalaryAllocation.setDefaultComp(defaultComp);
//
//                                        hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                        count++;
//                                    }
//                                }
//                                if (updatePurposeCode.equalsIgnoreCase("PUR002")) {
//                                    HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
////                                    hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                                    hrSalaryAllocationPK.setCalDateTo(calDateToString);
//                                    hrSalaryAllocationPK.setCompCode(compCode);
//                                    hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                    hrSalaryAllocationPK.setEmpCode(empCode);
////                                    hrSalaryAllocationPK.setMonths(updateMonth);
//
//                                    HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                    hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                    hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                    hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                    hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                    hrSalaryAllocation.setModDate(new Date());
//                                    hrSalaryAllocation.setAuthBy(authBy);
//                                    hrSalaryAllocation.setEnteredBy(enteredBy);
//                                    hrSalaryAllocation.setAllocationDate(allocationDate);
//                                    hrSalaryAllocation.setBasicSalary(updatedComponentAmount);
//                                    hrSalaryAllocation.setDefaultComp(defaultComp);
//                                    hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                    count++;
//                                }
//                            }
//                            Calendar c1 = Calendar.getInstance();
//                            c1.setTime(varFromDate);
//                            c1.add(Calendar.MONTH, 1);
//                            varFromDate = c1.getTime();
//                        }
//                        if (count != 0) {
//                            return "Data saved successfully";
//                        } else {
//                            return "Data could not saved , either the data already exist or allocation date is incorrect!!!";
//                        }
//                    } else {
//                        return "Not an active employee in the company!!!;";
//                    }
//                } else {
//                    if (!selectionCriteria.equalsIgnoreCase("EWE")) {
//                        List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDetailsDAO.findEntityEmpStatusY(compCode, selectedValues);
//                        if (!hrPersonnelDetailsList.isEmpty()) {
//                            for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
//                                empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
//                                varFromDate = allocationDate;
//                                while (varFromDate.before(calDateTo) || varFromDate.equals(calDateTo)) {
//                                    Calendar c = Calendar.getInstance();
//                                    c.setTime(varFromDate);
//                                    monthOfYear = c.get(Calendar.MONTH);
//                                    monthName = HrmsUtil.getMonthName(monthOfYear);
//                                    SalaryAllocationTOExtra salaryAllocationTOExtra = new SalaryAllocationTOExtra();
//                                    salaryAllocationTOExtra.setCalDateFrom(calDateFrom);
//                                    salaryAllocationTOExtra.setCaldateTo(calDateTo);
//                                    salaryAllocationTOExtra.setEmpCode(empCode);
//                                    salaryAllocationTOExtra.setMonth(monthOfYear);
//                                    salaryAllocationTOExtra.setCompCode(compCode);
//                                    salaryAllocationTOExtra.setPurposeCode("PUR001");
//                                    List<HrSalaryAllocation> hrSalaryAllocationList = hrSalaryAllocationDAO.findsalaryAllocateByEmp(salaryAllocationTOExtra);
//                                    for (HrSalaryAllocation hrSalaryAllocation : hrSalaryAllocationList) {
//                                        hrSalaryAllocationDAO.delete(hrSalaryAllocation, hrSalaryAllocation.getHrSalaryAllocationPK());
//                                    }
//                                    salaryAllocationTOExtra = new SalaryAllocationTOExtra();
//                                    salaryAllocationTOExtra.setCalDateFrom(calDateFrom);
//                                    salaryAllocationTOExtra.setCaldateTo(calDateTo);
//                                    salaryAllocationTOExtra.setEmpCode(empCode);
//                                    salaryAllocationTOExtra.setMonth(monthOfYear);
//                                    salaryAllocationTOExtra.setCompCode(compCode);
//                                    salaryAllocationTOExtra.setPurposeCode("PUR002");
//                                    hrSalaryAllocationList = hrSalaryAllocationDAO.findsalaryAllocateByEmp(salaryAllocationTOExtra);
//                                    for (HrSalaryAllocation hrSalaryAllocation : hrSalaryAllocationList) {
//                                        hrSalaryAllocationDAO.delete(hrSalaryAllocation, hrSalaryAllocation.getHrSalaryAllocationPK());
//                                    }
//                                    // }
//                                    Calendar c1 = Calendar.getInstance();
//                                    c1.setTime(varFromDate);
//                                    c1.add(Calendar.MONTH, 1);
//                                    varFromDate = c1.getTime();
//                                }
//                                varFromDate = allocationDate;
//                                while (varFromDate.before(calDateTo) || varFromDate.equals(calDateTo)) {
//                                    Calendar c = Calendar.getInstance();
//                                    c.setTime(varFromDate);
//                                    monthOfYear = c.get(Calendar.MONTH);
//                                    monthName = HrmsUtil.getMonthName(monthOfYear);
//                                    List<HrSalaryStructure> hrSalaryStructureList = hrSalaryStructureDAO.hrSalStructByCompAndDates(compCode, calDateFromString, calDateToString);
//                                    if (!hrSalaryStructureList.isEmpty()) {
//                                        for (HrSalaryStructure hrSalaryStructure : hrSalaryStructureList) {
//                                            newPurposeCode = hrSalaryStructure.getHrSalaryStructurePK().getPurposeCode();
//                                            newNature = hrSalaryStructure.getHrSalaryStructurePK().getNature();
//                                            newAlDesc = hrSalaryStructure.getHrSalaryStructurePK().getAlDesc();
//
//                                            HrSlabMaster hrSlabMaster = new HrSlabMaster();
//                                            HrSlabMasterPK hrSlabMasterPK = new HrSlabMasterPK();
//                                            hrSlabMasterPK.setAlDesc(newAlDesc);
//                                            hrSlabMasterPK.setCompCode(compCode);
////                                            hrSlabMasterPK.setDateFrom(calDateFrom);
////                                            hrSlabMasterPK.setDateTo(calDateTo);
//                                            hrSlabMasterPK.setNature(newNature);
//                                            hrSlabMasterPK.setPurposeCode(newPurposeCode);
//                                            hrSlabMaster.setAppFlg('Y');
//                                            hrSlabMaster.setHrSlabMasterPK(hrSlabMasterPK);
//                                            List<HrSlabMaster> hrSlabMasterList = hrSlabMasterDAO.getSlabMasterByvariousParam(hrSlabMaster, basicSalary);
//                                            if (!hrSlabMasterList.isEmpty()) {
//                                                for (HrSlabMaster hrSlabMaster1 : hrSlabMasterList) {
//                                                    slabCriteria = hrSlabMaster1.getSlabCriteria();
//                                                    slabCriteriaAmount = hrSlabMaster1.getSlabCriteriaAmt();
//                                                }
//                                                HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                                hrSalaryAllocationPK.setCompCode(compCode);
//                                                hrSalaryAllocationPK.setEmpCode(empCode);
////                                                hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                                                hrSalaryAllocationPK.setCalDateTo(calDateToString);
////                                                hrSalaryAllocationPK.setMonths(monthName);
//                                                hrSalaryAllocationPK.setComponentType(newAlDesc);
//                                                List<HrSalaryAllocation> salaryAllocationList = hrSalaryAllocationDAO.findsalAllocByVariousParameters(hrSalaryAllocationPK);
//                                                if (salaryAllocationList.isEmpty()) {
//                                                    if (slabCriteria.equalsIgnoreCase("PERCENTAGE")) {
//                                                        varAmount = (slabCriteriaAmount / 100) * basicSalary;
//
//                                                        HrSalaryAllocationPK hrSalaryAllocationPK1 = new HrSalaryAllocationPK();
////                                                        hrSalaryAllocationPK1.setCalDateFrom(calDateFromString);
////                                                        hrSalaryAllocationPK1.setCalDateTo(calDateToString);
//                                                        hrSalaryAllocationPK1.setCompCode(compCode);
//                                                        hrSalaryAllocationPK1.setComponentType(newAlDesc);
//                                                        hrSalaryAllocationPK1.setEmpCode(empCode);
////                                                        hrSalaryAllocationPK1.setMonths(monthName);
//                                                        HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                                        hrSalaryAllocation.setAllocationDate(allocationDate);
//                                                        hrSalaryAllocation.setAuthBy(authBy);
//                                                        hrSalaryAllocation.setBasicSalary(basicSalary);
//                                                        hrSalaryAllocation.setComponentAmount(varAmount);
//                                                        hrSalaryAllocation.setDefaultComp(defaultComp);
//                                                        hrSalaryAllocation.setEnteredBy(enteredBy);
//                                                        hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                                        hrSalaryAllocation.setModDate(new Date());
//                                                        hrSalaryAllocation.setStatFlag("Y");
//                                                        hrSalaryAllocation.setStatUpFlag("Y");
//
//                                                        hrSalaryAllocationDAO.save(hrSalaryAllocation);
//                                                        count++;
//                                                    }
//
//                                                    if (slabCriteria.equalsIgnoreCase("AMOUNT")) {
//                                                        varAmount = slabCriteriaAmount;
//                                                        HrSalaryAllocationPK hrSalaryAllocationPK1 = new HrSalaryAllocationPK();
////                                                        hrSalaryAllocationPK1.setCalDateFrom(calDateFromString);
////                                                        hrSalaryAllocationPK1.setCalDateTo(calDateToString);
//                                                        hrSalaryAllocationPK1.setCompCode(compCode);
//                                                        hrSalaryAllocationPK1.setComponentType(newAlDesc);
//                                                        hrSalaryAllocationPK1.setEmpCode(empCode);
////                                                        hrSalaryAllocationPK1.setMonths(monthName);
//                                                        HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                                        hrSalaryAllocation.setAllocationDate(allocationDate);
//                                                        hrSalaryAllocation.setAuthBy(authBy);
//                                                        hrSalaryAllocation.setBasicSalary(basicSalary);
//                                                        hrSalaryAllocation.setComponentAmount(varAmount);
//                                                        hrSalaryAllocation.setDefaultComp(defaultComp);
//                                                        hrSalaryAllocation.setEnteredBy(enteredBy);
//                                                        hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                                        hrSalaryAllocation.setModDate(new Date());
//                                                        hrSalaryAllocation.setStatFlag("Y");
//                                                        hrSalaryAllocation.setStatUpFlag("Y");
//
//                                                        hrSalaryAllocationDAO.save(hrSalaryAllocation);
//                                                        count++;
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    } else {
//                                        return "Data not found in hr salary structrure";
//                                    }
//                                    if (modeFlag.equalsIgnoreCase("UPDATE")) {
//                                        HrSalaryStructurePK hrSalaryStructurePK = new HrSalaryStructurePK();
//                                        hrSalaryStructurePK.setAlDesc(updatedComponent);
//                                        hrSalaryStructurePK.setCompCode(compCode);
////                                        hrSalaryStructurePK.setDateFrom(calDateFromString);
////                                        hrSalaryStructurePK.setDateTo(calDateToString);
//
//                                        HrSalaryStructure hrsalaryStructure = hrSalaryStructureDAO.hrSalStructByCompAldescAndDates(hrSalaryStructurePK);
//                                        updatePurposeCode = hrsalaryStructure.getHrSalaryStructurePK().getPurposeCode();
//
//                                        if (updatePurposeCode.equalsIgnoreCase("PUR001")) {
//                                            HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
////                                            hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                                            hrSalaryAllocationPK.setCalDateTo(calDateToString);
//                                            hrSalaryAllocationPK.setCompCode(compCode);
//                                            hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                            hrSalaryAllocationPK.setEmpCode(empCode);
////                                            hrSalaryAllocationPK.setMonths(updateMonth);
//
//                                            HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                            hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                            hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                            hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                            hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                            hrSalaryAllocation.setModDate(new Date());
//                                            hrSalaryAllocation.setAuthBy(authBy);
//                                            hrSalaryAllocation.setEnteredBy(enteredBy);
//                                            hrSalaryAllocation.setAllocationDate(allocationDate);
//                                            hrSalaryAllocation.setBasicSalary(basicSalary);
//                                            hrSalaryAllocation.setDefaultComp(defaultComp);
//
//                                            hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                            count++;
//
//                                            if (updatedComponent.equalsIgnoreCase("BASIC")) {
//                                                hrSalaryAllocationPK = new HrSalaryAllocationPK();
////                                                hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                                                hrSalaryAllocationPK.setCalDateTo(calDateToString);
//                                                hrSalaryAllocationPK.setCompCode(compCode);
//                                                hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                                hrSalaryAllocationPK.setEmpCode(empCode);
////                                                hrSalaryAllocationPK.setMonths(updateMonth);
//
//                                                hrSalaryAllocation = new HrSalaryAllocation();
//                                                hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                                hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                                hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                                hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                                hrSalaryAllocation.setModDate(new Date());
//                                                hrSalaryAllocation.setAuthBy(authBy);
//                                                hrSalaryAllocation.setEnteredBy(enteredBy);
//                                                hrSalaryAllocation.setAllocationDate(allocationDate);
//                                                hrSalaryAllocation.setBasicSalary(updatedComponentAmount);
//                                                hrSalaryAllocation.setDefaultComp(defaultComp);
//
//                                                hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                                count++;
//                                            }
//                                        }
//                                        if (updatePurposeCode.equalsIgnoreCase("PUR002")) {
//                                            HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
////                                            hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                                            hrSalaryAllocationPK.setCalDateTo(calDateToString);
//                                            hrSalaryAllocationPK.setCompCode(compCode);
//                                            hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                            hrSalaryAllocationPK.setEmpCode(empCode);
////                                            hrSalaryAllocationPK.setMonths(updateMonth);
//
//                                            HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                            hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                            hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                            hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                            hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                            hrSalaryAllocation.setModDate(new Date());
//                                            hrSalaryAllocation.setAuthBy(authBy);
//                                            hrSalaryAllocation.setEnteredBy(enteredBy);
//                                            hrSalaryAllocation.setAllocationDate(allocationDate);
//                                            hrSalaryAllocation.setBasicSalary(updatedComponentAmount);
//                                            hrSalaryAllocation.setDefaultComp(defaultComp);
//
//                                            hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                            count++;
//                                            //}
//                                        }
//                                    }
//                                    Calendar c1 = Calendar.getInstance();
//                                    c1.setTime(varFromDate);
//                                    c1.add(Calendar.MONTH, 1);
//                                    varFromDate = c1.getTime();
//                                }
//                            }
//                            if (count != 0) {
//                                return "Data saved successfully";
//                            } else {
//                                return "Data could not saved , either the data already exist or allocation date is incorrect!!!";
//                            }
//                        } else {
//                            return "No data found corresponding to categorization details!!!";
//                        }
//                    }
//                }
//            }
//            if (modeFlag.equalsIgnoreCase("DELETE")) {
//                if (selectionCriteria.equalsIgnoreCase("EWE")) {
//                    flag = "N_DELETED";
//                    HrPersonnelDetails hrPersonnelDetailsEntity = hrPersonnelDetailsDAO.findByEmpStatusAndEmpId(compCode, selectedValues, 'Y');
//                    if (hrPersonnelDetailsEntity.getHrPersonnelDetailsPK() != null) {
//                        empCode = hrPersonnelDetailsEntity.getHrPersonnelDetailsPK().getEmpCode().longValue();
//                        varFromDate = allocationDate;
//                        while (varFromDate.before(calDateTo) || varFromDate.equals(calDateTo)) {
//                            Calendar c = Calendar.getInstance();
//                            c.setTime(varFromDate);
//                            monthOfYear = c.get(Calendar.MONTH);
//                            monthName = HrmsUtil.getMonthName(monthOfYear);
//                            HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
////                            hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                            hrSalaryAllocationPK.setCalDateTo(calDateToString);
//                            hrSalaryAllocationPK.setCompCode(compCode);
//                            hrSalaryAllocationPK.setEmpCode(empCode);
////                            hrSalaryAllocationPK.setMonths(monthName);
//
//                            List<HrSalaryAllocation> hrSalaryAllocationList = hrSalaryAllocationDAO.getSalaryAllocateForMonth(hrSalaryAllocationPK);
//                            for (HrSalaryAllocation hrSalaryAllocation : hrSalaryAllocationList) {
//                                hrSalaryAllocationPK.setComponentType(hrSalaryAllocation.getHrSalaryAllocationPK().getComponentType());
//                                hrSalaryAllocationDAO.delete(hrSalaryAllocation, hrSalaryAllocationPK);
//                                count++;
//                            }
//                            Calendar c1 = Calendar.getInstance();
//                            c1.setTime(varFromDate);
//                            c1.add(Calendar.MONTH, 1);
//                            varFromDate = c1.getTime();
//                        }
//                    } else {
//                        return "Not an Active emloyee";
//                    }
//                    if (count != 0) {
//                        return "Data deleted successfully";
//                    } else {
//                        return "No data exist for deletion!!!";
//                    }
//                }
//                if (!selectionCriteria.equalsIgnoreCase("EWE")) {
//                    flag = "N_DELETED";
//                    List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDetailsDAO.findEntityEmpStatusY(compCode, selectedValues);
//                    if (!hrPersonnelDetailsList.isEmpty()) {
//                        for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
//                            varFromDate = allocationDate;
//                            while (varFromDate.before(calDateTo) || varFromDate.equals(calDateTo)) {
//                                Calendar c = Calendar.getInstance();
//                                c.setTime(varFromDate);
//                                monthOfYear = c.get(Calendar.MONTH);
//                                monthName = HrmsUtil.getMonthName(monthOfYear);
//                                HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
////                                hrSalaryAllocationPK.setCalDateFrom(calDateFromString);
////                                hrSalaryAllocationPK.setCalDateTo(calDateToString);
//                                hrSalaryAllocationPK.setCompCode(compCode);
//                                hrSalaryAllocationPK.setEmpCode(empCode);
////                                hrSalaryAllocationPK.setMonths(monthName);
//                                List<HrSalaryAllocation> salaryAllocationList = hrSalaryAllocationDAO.getSalaryAllocateForMonth(hrSalaryAllocationPK);
//                                for (HrSalaryAllocation hrSalaryAllocation : salaryAllocationList) {
//                                    hrSalaryAllocationDAO.delete(hrSalaryAllocation, hrSalaryAllocationPK);
//                                    count++;
//                                }
//                                Calendar c1 = Calendar.getInstance();
//                                c1.setTime(varFromDate);
//                                c1.add(Calendar.MONTH, 1);
//                                varFromDate = c1.getTime();
//                            }
//                        }
//                    } else {
//                        return "No Data Found corresponding to categorization details!!!";
//                    }
//                    if (count != 0) {
//                        return "Data deleted successfully";
//                    } else {
//                        return "No data exist for deletion!!!";
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Exception occured while executing method insertSalaryallocation()", e);
//            throw new ApplicationException(e.getMessage());
//        }
//        if (logger.isDebugEnabled()) {
//            logger.debug("Execution time for insertSalaryallocation is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
//        }
//        return message;
//    }
//    @Override
//    public String insertSalaryallocation(SalaryAllocationTO salaryAllocationTO, List<EmpSalaryAllocationGridTO> salaryAddGrid) throws ApplicationException {
//        long begin = System.nanoTime();
//        String selectionCriteria = salaryAllocationTO.getSelectionCriteria(),
//                selectedValues = salaryAllocationTO.getSelectedValues(),
//                updatedComponent = salaryAllocationTO.getUpdatedComponent(),
//                authBy = salaryAllocationTO.getAuthBy(),
//                enteredBy = salaryAllocationTO.getEnteredBy(),
//                modeFlag = salaryAllocationTO.getModeFlag(),
//                message = "false";
//        double basicSalary = Double.parseDouble(salaryAllocationTO.getBasicSalary()),
//                updatedComponentAmount = Float.parseFloat(salaryAllocationTO.getUpdatedComponentAmount());
//        char statFlag = salaryAllocationTO.getStatFlag(),
//                statUpFlag = salaryAllocationTO.getStatUpFlag();
//        int defaultComp = salaryAllocationTO.getDefaultComp(),
//                compCode = salaryAllocationTO.getCompanyCode(),
//                count = 0;
//
//        /**
//         * **** declared values **************
//         */
//        long empCode = 0;
//        Date allocationDate = salaryAllocationTO.getAllocationDate(); //Salary Alocation Date
//        String flag = null, updatePurposeCode;
//        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
//        HrSalaryAllocationDAO hrSalaryAllocationDAO = new HrSalaryAllocationDAO(em);
//        HrSalaryStructureDAO hrSalaryStructureDAO = new HrSalaryStructureDAO(em);
//        try {
//            if (modeFlag.equalsIgnoreCase("SAVE")) {
//                if (selectionCriteria.equalsIgnoreCase("EWE")) {
//                    HrPersonnelDetails hrPersonnelDetailsEntity = hrPersonnelDetailsDAO.findByEmpStatusAndEmpId(compCode, selectedValues, 'Y'); //Retrieve The Active Employee If
//                    if (hrPersonnelDetailsEntity.getHrPersonnelDetailsPK() != null) {
//                        empCode = hrPersonnelDetailsEntity.getHrPersonnelDetailsPK().getEmpCode().longValue();
//                        for (EmpSalaryAllocationGridTO salGrid : salaryAddGrid) {
//                            if (!(salGrid.getPassAmt().equalsIgnoreCase("0") || salGrid.getPassAmt().equalsIgnoreCase("0.0"))) {
//                                HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                hrSalaryAllocationPK.setCompCode(compCode);
//                                hrSalaryAllocationPK.setEmpCode(empCode);
//                                hrSalaryAllocationPK.setComponentType(salGrid.getComponent());
//                                hrSalaryAllocation.setAllocationDate(allocationDate);
//                                hrSalaryAllocation.setAuthBy(authBy);
//                                hrSalaryAllocation.setBasicSalary(basicSalary);
//                                hrSalaryAllocation.setComponentAmount(Double.parseDouble(salGrid.getPassAmt()));
//                                hrSalaryAllocation.setDefaultComp(defaultComp);
//                                hrSalaryAllocation.setEnteredBy(enteredBy);
//                                hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                hrSalaryAllocation.setModDate(new Date());
//                                hrSalaryAllocation.setStatFlag("Y");
//                                hrSalaryAllocation.setStatUpFlag("Y");
//                                hrSalaryAllocationDAO.save(hrSalaryAllocation);
//                                count++;
//                            }
//                        }
//                        if (count != 0) {
//                            return "Data saved successfully";
//                        } else {
//                            return "Data could not saved , either the data already exist or allocation date is incorrect!!!";
//                        }
//                    } else {
//                        return "Not an active employee in the company!!!;";
//                    }
//                } else {
//                    if (!selectionCriteria.equalsIgnoreCase("EWE")) {
//                        List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDetailsDAO.findEntityEmpStatusY(compCode, selectedValues);
//                        if (!hrPersonnelDetailsList.isEmpty()) {
//                            for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
//                                empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
//                                for (EmpSalaryAllocationGridTO salGrid : salaryAddGrid) {
//                                    if (!(salGrid.getPassAmt() == "0" || salGrid.getPassAmt() == "0.0")) {
//                                        HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                        HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                        hrSalaryAllocationPK.setCompCode(compCode);
//                                        hrSalaryAllocationPK.setEmpCode(empCode);
//                                        hrSalaryAllocationPK.setComponentType(salGrid.getComponent());
//                                        hrSalaryAllocation.setAllocationDate(allocationDate);
//                                        hrSalaryAllocation.setAuthBy(authBy);
//                                        hrSalaryAllocation.setBasicSalary(basicSalary);
//                                        hrSalaryAllocation.setComponentAmount(Double.parseDouble(salGrid.getPassAmt()));
//                                        hrSalaryAllocation.setDefaultComp(defaultComp);
//                                        hrSalaryAllocation.setEnteredBy(enteredBy);
//                                        hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                        hrSalaryAllocation.setModDate(new Date());
//                                        hrSalaryAllocation.setStatFlag("Y");
//                                        hrSalaryAllocation.setStatUpFlag("Y");
//                                        hrSalaryAllocationDAO.save(hrSalaryAllocation);
//                                        count++;
//                                    }
//                                }
//                            }
//                            if (count != 0) {
//                                return "Data saved successfully";
//                            } else {
//                                return "Data could not saved , either the data already exist or allocation date is incorrect!!!";
//                            }
//                        } else {
//                            return "No data found corresponding to categorization details!!!";
//                        }
//                    }
//                }
//            }
//            if (modeFlag.equalsIgnoreCase("UPDATE")) {
//                if (updatedComponentAmount != 0) {
//                    if (selectionCriteria.equalsIgnoreCase("EWE")) {
//                        HrPersonnelDetails hrPersonnelDetailsEntity = hrPersonnelDetailsDAO.findByEmpStatusAndEmpId(compCode, selectedValues, 'Y'); //Retrieve The Active Employee If
//                        if (hrPersonnelDetailsEntity.getHrPersonnelDetailsPK() != null) {
//                            empCode = hrPersonnelDetailsEntity.getHrPersonnelDetailsPK().getEmpCode().longValue();
//                            if (modeFlag.equalsIgnoreCase("UPDATE")) {
//                                HrSalaryStructurePK hrSalaryStructurePK = new HrSalaryStructurePK();
//                                hrSalaryStructurePK.setAlDesc(updatedComponent);
//                                hrSalaryStructurePK.setCompCode(compCode);
//                                HrSalaryStructure hrsalaryStructure = hrSalaryStructureDAO.hrSalStructByCompAldescAndDates(hrSalaryStructurePK);
//                                updatePurposeCode = hrsalaryStructure.getHrSalaryStructurePK().getPurposeCode();
//                                if (updatePurposeCode.equalsIgnoreCase("PUR001")) {
//                                    HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                    hrSalaryAllocationPK.setCompCode(compCode);
//                                    hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                    hrSalaryAllocationPK.setEmpCode(empCode);
//                                    HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                    hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                    hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                    hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                    hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                    hrSalaryAllocation.setModDate(new Date());
//                                    hrSalaryAllocation.setAuthBy(authBy);
//                                    hrSalaryAllocation.setEnteredBy(enteredBy);
//                                    hrSalaryAllocation.setAllocationDate(allocationDate);
//                                    hrSalaryAllocation.setBasicSalary(basicSalary);
//                                    hrSalaryAllocation.setDefaultComp(defaultComp);
//                                    hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                    count++;
//
//                                    if (updatedComponent.equalsIgnoreCase("BASIC")) {
//                                        hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                        hrSalaryAllocationPK.setCompCode(compCode);
//                                        hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                        hrSalaryAllocationPK.setEmpCode(empCode);
//                                        hrSalaryAllocation = new HrSalaryAllocation();
//                                        hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                        hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                        hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                        hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                        hrSalaryAllocation.setModDate(new Date());
//                                        hrSalaryAllocation.setAuthBy(authBy);
//                                        hrSalaryAllocation.setEnteredBy(enteredBy);
//                                        hrSalaryAllocation.setAllocationDate(allocationDate);
//                                        hrSalaryAllocation.setBasicSalary(updatedComponentAmount);
//                                        hrSalaryAllocation.setDefaultComp(defaultComp);
//
//                                        hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                        count++;
//                                    }
//                                }
//                                if (updatePurposeCode.equalsIgnoreCase("PUR002")) {
//                                    HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                    hrSalaryAllocationPK.setCompCode(compCode);
//                                    hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                    hrSalaryAllocationPK.setEmpCode(empCode);
//                                    HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                    hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                    hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                    hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                    hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                    hrSalaryAllocation.setModDate(new Date());
//                                    hrSalaryAllocation.setAuthBy(authBy);
//                                    hrSalaryAllocation.setEnteredBy(enteredBy);
//                                    hrSalaryAllocation.setAllocationDate(allocationDate);
//                                    hrSalaryAllocation.setBasicSalary(updatedComponentAmount);
//                                    hrSalaryAllocation.setDefaultComp(defaultComp);
//                                    hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                    count++;
//                                }
//                            }
//                            if (count != 0) {
//                                return "Data saved successfully";
//                            } else {
//                                return "Data could not saved , either the data already exist or allocation date is incorrect!!!";
//                            }
//                        } else {
//                            return "Not an active employee in the company!!!;";
//                        }
//                    } else {
//                        if (!selectionCriteria.equalsIgnoreCase("EWE")) {
//                            List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDetailsDAO.findEntityEmpStatusY(compCode, selectedValues);
//                            if (!hrPersonnelDetailsList.isEmpty()) {
//                                for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
//                                    empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
//                                    if (modeFlag.equalsIgnoreCase("UPDATE")) {
//                                        HrSalaryStructurePK hrSalaryStructurePK = new HrSalaryStructurePK();
//                                        hrSalaryStructurePK.setAlDesc(updatedComponent);
//                                        hrSalaryStructurePK.setCompCode(compCode);
//
//                                        HrSalaryStructure hrsalaryStructure = hrSalaryStructureDAO.hrSalStructByCompAldescAndDates(hrSalaryStructurePK);
//                                        updatePurposeCode = hrsalaryStructure.getHrSalaryStructurePK().getPurposeCode();
//
//                                        if (updatePurposeCode.equalsIgnoreCase("PUR001")) {
//                                            HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                            hrSalaryAllocationPK.setCompCode(compCode);
//                                            hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                            hrSalaryAllocationPK.setEmpCode(empCode);
//
//                                            HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                            hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                            hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                            hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                            hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                            hrSalaryAllocation.setModDate(new Date());
//                                            hrSalaryAllocation.setAuthBy(authBy);
//                                            hrSalaryAllocation.setEnteredBy(enteredBy);
//                                            hrSalaryAllocation.setAllocationDate(allocationDate);
//                                            hrSalaryAllocation.setBasicSalary(basicSalary);
//                                            hrSalaryAllocation.setDefaultComp(defaultComp);
//
//                                            hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                            count++;
//
//                                            if (updatedComponent.equalsIgnoreCase("BASIC")) {
//                                                hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                                hrSalaryAllocationPK.setCompCode(compCode);
//                                                hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                                hrSalaryAllocationPK.setEmpCode(empCode);
//
//                                                hrSalaryAllocation = new HrSalaryAllocation();
//                                                hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                                hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                                hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                                hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                                hrSalaryAllocation.setModDate(new Date());
//                                                hrSalaryAllocation.setAuthBy(authBy);
//                                                hrSalaryAllocation.setEnteredBy(enteredBy);
//                                                hrSalaryAllocation.setAllocationDate(allocationDate);
//                                                hrSalaryAllocation.setBasicSalary(updatedComponentAmount);
//                                                hrSalaryAllocation.setDefaultComp(defaultComp);
//
//                                                hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                                count++;
//                                            }
//                                        }
//                                        if (updatePurposeCode.equalsIgnoreCase("PUR002")) {
//                                            HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                                            hrSalaryAllocationPK.setCompCode(compCode);
//                                            hrSalaryAllocationPK.setComponentType(updatedComponent);
//                                            hrSalaryAllocationPK.setEmpCode(empCode);
//
//                                            HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
//                                            hrSalaryAllocation.setComponentAmount(updatedComponentAmount);
//                                            hrSalaryAllocation.setStatFlag(String.valueOf(statFlag));
//                                            hrSalaryAllocation.setStatUpFlag(String.valueOf(statUpFlag));
//                                            hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
//                                            hrSalaryAllocation.setModDate(new Date());
//                                            hrSalaryAllocation.setAuthBy(authBy);
//                                            hrSalaryAllocation.setEnteredBy(enteredBy);
//                                            hrSalaryAllocation.setAllocationDate(allocationDate);
//                                            hrSalaryAllocation.setBasicSalary(updatedComponentAmount);
//                                            hrSalaryAllocation.setDefaultComp(defaultComp);
//
//                                            hrSalaryAllocationDAO.update(hrSalaryAllocation);
//                                            count++;
//                                        }
//                                    }
//                                }
//                                if (count != 0) {
//                                    return "Data saved successfully";
//                                } else {
//                                    return "Data could not saved , either the data already exist or allocation date is incorrect!!!";
//                                }
//                            } else {
//                                return "No data found corresponding to categorization details!!!";
//                            }
//                        }
//                    }
//                }
//            }
//            if (modeFlag.equalsIgnoreCase("DELETE")) {
//                if (selectionCriteria.equalsIgnoreCase("EWE")) {
//                    flag = "N_DELETED";
//                    HrPersonnelDetails hrPersonnelDetailsEntity = hrPersonnelDetailsDAO.findByEmpStatusAndEmpId(compCode, selectedValues, 'Y');
//                    if (hrPersonnelDetailsEntity.getHrPersonnelDetailsPK() != null) {
//                        empCode = hrPersonnelDetailsEntity.getHrPersonnelDetailsPK().getEmpCode().longValue();
//                        HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                        hrSalaryAllocationPK.setCompCode(compCode);
//                        hrSalaryAllocationPK.setEmpCode(empCode);
//
//                        List<HrSalaryAllocation> hrSalaryAllocationList = hrSalaryAllocationDAO.getSalaryAllocateForMonth(hrSalaryAllocationPK);
//                        for (HrSalaryAllocation hrSalaryAllocation : hrSalaryAllocationList) {
//                            if (updatedComponent.equalsIgnoreCase(hrSalaryAllocation.getHrSalaryAllocationPK().getComponentType())) {
//                                hrSalaryAllocationPK.setComponentType(hrSalaryAllocation.getHrSalaryAllocationPK().getComponentType());
//                                hrSalaryAllocationDAO.delete(hrSalaryAllocation, hrSalaryAllocationPK);
//                                count++;
//                            }
//                        }
//                    } else {
//                        return "Not an Active emloyee";
//                    }
//                    if (count != 0) {
//                        return "Data deleted successfully";
//                    } else {
//                        return "No data exist for deletion!!!";
//                    }
//                }
//                if (!selectionCriteria.equalsIgnoreCase("EWE")) {
//                    flag = "N_DELETED";
//                    List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDetailsDAO.findEntityEmpStatusY(compCode, selectedValues);
//                    if (!hrPersonnelDetailsList.isEmpty()) {
//                        for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
//                            HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                            hrSalaryAllocationPK.setCompCode(compCode);
//                            hrSalaryAllocationPK.setEmpCode(empCode);
//                            List<HrSalaryAllocation> salaryAllocationList = hrSalaryAllocationDAO.getSalaryAllocateForMonth(hrSalaryAllocationPK);
//                            for (HrSalaryAllocation hrSalaryAllocation : salaryAllocationList) {
//                                hrSalaryAllocationDAO.delete(hrSalaryAllocation, hrSalaryAllocationPK);
//                                count++;
//                            }
//                        }
//                    } else {
//                        return "No Data Found corresponding to categorization details!!!";
//                    }
//                    if (count != 0) {
//                        return "Data deleted successfully";
//                    } else {
//                        return "No data exist for deletion!!!";
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Exception occured while executing method insertSalaryallocation()", e);
//            throw new ApplicationException(e.getMessage());
//        }
//        if (logger.isDebugEnabled()) {
//            logger.debug("Execution time for insertSalaryallocation is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
//        }
//        return message;
//    }
    @Override
    public List<LeaveMasterTO> getSelectedLeaveData(int compCode, String fromDate, String toDate) throws ApplicationException {
        long begin = System.nanoTime();
        HrLeaveMasterDAO hrLeaveMasterDAO = new HrLeaveMasterDAO(em);
        List<LeaveMasterTO> leaveMasterTOs = new ArrayList<LeaveMasterTO>();
        try {
            List<HrLeaveMaster> hrLeaveMasterList = hrLeaveMasterDAO.findSelectedLeaveCodeList(compCode, fromDate, toDate);
            for (HrLeaveMaster hrLeaveMaster : hrLeaveMasterList) {
                leaveMasterTOs.add(PayrollObjectAdaptor.adaptToHrLeaveMasterTO(hrLeaveMaster));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getLeaveMasterData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLeaveMasterData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getLeaveMasterData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return leaveMasterTOs;
    }

    @Override
    public List<HrPersonnelDetailsTO> getPersonnalData(int compCode, String type, String value) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = new ArrayList<>();
        try {
            List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDetailsDAO.findEmpByCompCodeTypeValue(compCode, type, value);
            for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
                hrPersonnelDetailsTOs.add(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelDetails));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getOpenCalendarList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDetailsTOs;

    }

    @Override
    public int getTotalLeaveDays(int compCode, String leaveCode, long empCode, String hrCalendatDateFrom, String hrCalendatDateTo) throws ApplicationException {
        int result = 0;
        long begin = System.nanoTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        HrLeaveRegisterDAO hrLeaveRegisterDAO = new HrLeaveRegisterDAO(em);
        try {
            List<HrLeaveRegister> hrLeaveRegisters = hrLeaveRegisterDAO.findTotalLeaveDays(compCode, leaveCode, empCode, hrCalendatDateFrom, hrCalendatDateTo);
            for (HrLeaveRegister hrLeaveRegister : hrLeaveRegisters) {
                Date paramFromDate = formatter.parse(hrCalendatDateFrom);
                Date paramToDate = formatter.parse(hrCalendatDateTo);
                Date fromDate = formatter.parse(hrLeaveRegister.getFromDate());
                Date toDate = formatter.parse(hrLeaveRegister.getToDate());
                if (paramFromDate.before(fromDate) && paramToDate.after(toDate)) {
                    result = result + hrLeaveRegister.getDaysTaken();
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getLeaveRegisterData()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getLeaveRegisterData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }

    @Override
    public int getNumOfLeaveDays(int compCode, String leaveCode, String fromDate, String toDate) throws ApplicationException {
        int result = 0;
        long begin = System.nanoTime();
        HrLeaveMasterDAO hrLeaveMasterDAO = new HrLeaveMasterDAO(em);
        try {
            List<HrLeaveMaster> hrLeaveMasterList = hrLeaveMasterDAO.findNumOfLeaveDays(compCode, leaveCode, fromDate, toDate);
            result = hrLeaveMasterList.get(0).getNumDays();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getLeaveRegisterData()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getLeaveRegisterData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }

    @Override
    public String getLeaveRegCode(int compCode) throws ApplicationException {
        String result = "LRG";
        HrLeaveRegisterDAO hrLeaveRegisterDAO = new HrLeaveRegisterDAO(em);
        try {
            int no = 1;
            List<HrLeaveRegister> list = hrLeaveRegisterDAO.leaveRegisterData(compCode);
            if (!list.isEmpty()) {
                Collections.sort(list, new CommonComparator());
                String leavRegCode = list.get(list.size() - 1).getHrLeaveRegisterPK().getLeavRegCode();
                no = Integer.parseInt(leavRegCode.substring(3)) + 1;
            }
            return result + HrmsUtil.lPadding(8, no);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<HrLeaveRegisterTO> getLeaveRegisterData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrLeaveRegisterDAO hrLeaveRegisterDAO = new HrLeaveRegisterDAO(em);
        List<HrLeaveRegisterTO> hrLeaveRegisterTOs = new ArrayList<HrLeaveRegisterTO>();
        try {
            List<HrLeaveRegister> hrLeaveRegisters = hrLeaveRegisterDAO.leaveRegisterData(compCode);
            for (HrLeaveRegister hrLeaveRegister : hrLeaveRegisters) {
                hrLeaveRegisterTOs.add(PayrollObjectAdaptor.adaptToHrLeaveRegisterTO(hrLeaveRegister));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getLeaveRegisterData()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getLeaveRegisterData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrLeaveRegisterTOs;
    }

    @Override
    public String isDateAllowed(int compCode, long empCode, String leaveRegCode, Date leaveFromDate, Date leaveToDate) throws ApplicationException {
        HrLeaveRegisterDAO hrLeaveRegisterDAO = new HrLeaveRegisterDAO(em);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            List<HrLeaveRegister> hrLeaveRegisters = hrLeaveRegisterDAO.byCompCodeAndEmpCode(compCode, empCode, leaveRegCode);
            for (HrLeaveRegister hrLeaveRegister : hrLeaveRegisters) {
                Date fromDate = formatter.parse(hrLeaveRegister.getFromDate());
                Date toDate = formatter.parse(hrLeaveRegister.getToDate());
                if (leaveFromDate.after(fromDate) && leaveFromDate.before(toDate)) {
                    return "N";
                } else if (leaveFromDate.compareTo(fromDate) == 0 || leaveFromDate.compareTo(toDate) == 0) {
                    return "N";
                } else if (leaveFromDate.before(fromDate) && leaveToDate.compareTo(fromDate) >= 0) {
                    return "N";
                }
            }
            return "Y";
        } catch (DAOException e) {
            logger.error("Exception occured while executing method isDateAllowed()", e);
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
            logger.error("Exception occured while executing method isDateAllowed()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }

    @Override
    public String saveLeaveRegisterDetail(HrLeaveRegisterTO hrLeaveRegisterTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        HrLeaveRegister hrLeaveRegister = null;
        String msg = "";
        try {
            HrLeaveRegisterDAO hrLeaveRegisterDAO = new HrLeaveRegisterDAO(em);
            if (mode.equals("1")) {
                String leaveRegCode = getLeaveRegCode(hrLeaveRegisterTO.getHrLeaveRegisterPK().getCompCode());
                hrLeaveRegisterTO.getHrLeaveRegisterPK().setLeavRegCode(leaveRegCode);
            }

            Validator.isNull(hrLeaveRegisterTO);
            hrLeaveRegister = PayrollObjectAdaptor.adaptToHrLeaveRegisterEntity(hrLeaveRegisterTO);
            if (mode.equals("1")) {
                hrLeaveRegisterDAO.save(hrLeaveRegister);
                msg = "Data has been successfully saved.";
            } else if (mode.equals("2")) {
                hrLeaveRegisterDAO.update(hrLeaveRegister);
                msg = "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("3")) {
                hrLeaveRegisterDAO.delete(hrLeaveRegister, hrLeaveRegister.getHrLeaveRegisterPK());
                msg = "Data has been successfully deleted.";
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveRegisterDetail()", e);
            throw new ApplicationException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveLeaveRegisterDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

    @Override
    public List<HrMstShiftTO> getShiftListData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstShiftDAO hrMstShiftDAO = new HrMstShiftDAO(em);
        List<HrMstShiftTO> hrMstShiftTOs = new ArrayList<HrMstShiftTO>();
        try {
            List<HrMstShift> hrMstShifts = hrMstShiftDAO.getShiftListData(compCode);
            for (HrMstShift hrMstShift : hrMstShifts) {
                hrMstShiftTOs.add(PayrollObjectAdaptor.adaptToHrMstShiftEntityTO(hrMstShift));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getOpenCalendarList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrMstShiftTOs;
    }

    @Override
    public String saveShiftMapDetail(HrShiftMapTO hrShiftMapTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrShiftMapTO);

        HrShiftMap hrShiftMap = null;
        try {
            HrShiftMapDAO hrShiftMapDAO = new HrShiftMapDAO(em);

            hrShiftMap = PayrollObjectAdaptor.adaptToHrShiftMapEntity(hrShiftMapTO);
            HrMstShift hms = new HrMstShiftDAO(em).getEntityByShiftCodeAndCompCode(hrShiftMapTO.getHrShiftMapPKTO().getCompCode(), hrShiftMapTO.getHrShiftMapPKTO().getShiftCode());
            if (hms != null) {
                hrShiftMap.setHrMstShift(hms);
            }
            HrPersonnelDetails hrPersonnelDetails = new HrPersonnelDetailsDAO(em).getAllByCompCodeAndEmpCodeOrEMPID(hrShiftMapTO.getHrShiftMapPKTO().getCompCode(), hrShiftMapTO.getHrPersonnelDetailsTO().getHrPersonnelDetailsPKTO().getEmpCode(), hrShiftMapTO.getHrPersonnelDetailsTO().getEmpId());
            hrShiftMap.setHrPersonnelDetails(hrPersonnelDetails);
            if (mode.equalsIgnoreCase("save")) {
                BaseEntity findById = hrShiftMapDAO.findById(hrShiftMap, hrShiftMap.getHrShiftMapPK());
                if (findById == null) {
                    hrShiftMapDAO.save(hrShiftMap);
                } else {
                    return "Entry already exists !";
                }

            } else if (mode.equalsIgnoreCase("update")) {
                hrShiftMapDAO.update(hrShiftMap);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrShiftMapDAO.delete(hrShiftMap, hrShiftMap.getHrShiftMapPK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveShiftMapDetail()", e);
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
            logger.error("Exception occured while executing method saveShiftMapDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveShiftMapDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public List getShiftTableData(int compcode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        List shiftTableDataList = null;
        try {
            shiftTableDataList = hrPersonnelDetailsDAO.getShiftTableData(compcode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getOpenCalendarList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return shiftTableDataList;
    }

    @Override
    public List<HrPersonnelDetailsTO> getCategorizationBasedEmployees(int compCode, String type) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = new ArrayList<HrPersonnelDetailsTO>();
        try {
            List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDetailsDAO.getCategorizationBasedEmployees(compCode, type);
            for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
                hrPersonnelDetailsTOs.add(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelDetails));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getOpenCalendarList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDetailsTOs;
    }

    /**
     * **************************************
     */
    @Override
    public List<EmpSalaryAllocationGridTO> getSalaryAllocationForEmp(GetSalaryAllocationTO getSalaryAllocateData) throws ApplicationException {
        long begin = System.nanoTime();
        int comCode = getSalaryAllocateData.getCompCode();
        String selectionCriteria = getSalaryAllocateData.getSelectionCriteria();
        String selectedValues = getSalaryAllocateData.getSelectedValues();
        String calDateFrom = getSalaryAllocateData.getCalDateFrom();
        String calDateTo = getSalaryAllocateData.getCalDateTo();

        /**
         * ****** variable declaration*******
         */
        long empCode;
        String empName, newCalDateFrom, newCalDateTo, newAllocationDate,
                newMonths, newComponenetType;
        Float newComponentAmount;
        String emId;
        Float basicSalary;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        HrSalaryAllocationDAO hrSalaryAllocationDAO = new HrSalaryAllocationDAO(em);
        List<HrSalaryAllocation> hrsalaryallocationList = new ArrayList<HrSalaryAllocation>();
        List<HrPersonnelDetails> hrPersonnelDetailsList = new ArrayList<HrPersonnelDetails>();
        List<EmpSalaryAllocationGridTO> empsalaryAllocationGridTO = new ArrayList<EmpSalaryAllocationGridTO>();
        try {
            if (selectionCriteria.equalsIgnoreCase("EWE")) {
                HrPersonnelDetails hrPersonnelDetails = hrPersonnelDetailsDAO.findByEmpStatusAndEmpId(comCode, selectedValues, 'Y');
                if (hrPersonnelDetails.getHrPersonnelDetailsPK() != null) {
                    empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
                    empName = hrPersonnelDetails.getEmpName();
                    HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
////                    hrSalaryAllocationPK.setCalDateFrom(calDateFrom);
////                    hrSalaryAllocationPK.setCalDateTo(calDateTo);
                    hrSalaryAllocationPK.setCompCode(comCode);
                    hrSalaryAllocationPK.setEmpCode(empCode);
                    List<HrSalaryAllocation> hrSalaryAllocationList = hrsalaryallocationList = hrSalaryAllocationDAO.findsalAllocByEmpCodeAndCompCode(hrSalaryAllocationPK);
                    for (HrSalaryAllocation hrSalaryAllocation : hrSalaryAllocationList) {
                        EmpSalaryAllocationGridTO emp = new EmpSalaryAllocationGridTO();
                        emp.setAllocationDate(formatter.format(hrSalaryAllocation.getAllocationDate()));
//                        emp.setAmount(hrSalaryAllocation.getComponentAmount());
//                        emp.setComponent(String.valueOf(hrSalaryAllocation.getHrSalaryAllocationPK().getComponentType()));
                        emp.setEmpid(selectedValues);
                        emp.setEmployeeName(empName);
//                        emp.setBasicSalary(hrSalaryAllocation.getHrSalaryAllocationPK().getBasicSalary());
//                        emp.setMonths(hrSalaryAllocation.getHrSalaryAllocationPK().getMonths());
                        empsalaryAllocationGridTO.add(emp);
                    }
                }
            }
            if (!selectionCriteria.equalsIgnoreCase("EWE")) {
                hrPersonnelDetailsList = hrPersonnelDetailsDAO.getCategorizationBasedEmployees(comCode, selectedValues);
                if (!hrPersonnelDetailsList.isEmpty()) {
                    for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
                        empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
                        empName = hrPersonnelDetails.getEmpName();
                        emId = hrPersonnelDetails.getEmpId();
                        HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                        hrSalaryAllocationPK.setCalDateFrom(calDateFrom);
//                        hrSalaryAllocationPK.setCalDateTo(calDateTo);
                        hrSalaryAllocationPK.setCompCode(comCode);
                        hrSalaryAllocationPK.setEmpCode(empCode);
                        List<HrSalaryAllocation> hrSalaryAllocationList = hrsalaryallocationList = hrSalaryAllocationDAO.findsalAllocByEmpCodeAndCompCode(hrSalaryAllocationPK);
                        for (HrSalaryAllocation hrSalaryAllocation : hrSalaryAllocationList) {
                            EmpSalaryAllocationGridTO emp = new EmpSalaryAllocationGridTO();
                            emp.setAllocationDate(formatter.format(hrSalaryAllocation.getAllocationDate()));
//                            emp.setAmount(hrSalaryAllocation.getComponentAmount());
//                            emp.setComponent(String.valueOf(hrSalaryAllocation.getComponentAmount()));
                            emp.setEmpid(emId);
                            emp.setEmployeeName(empName);
//                            emp.setBasicSalary(hrSalaryAllocation.getHrSalaryAllocationPK().getBasicSalary());
//                            emp.setMonths(hrSalaryAllocation.getHrSalaryAllocationPK().getMonths());
                            empsalaryAllocationGridTO.add(emp);
                        }
                    }
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSalaryAllocationForEmp()", e);
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
            logger.error("Exception occured while executing method getSalaryAllocationForEmp()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSalaryAllocationForEmp is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return empsalaryAllocationGridTO;
    }

    /**
     * ********** himanshu *******************************
     */
    // getCompany Names
    @Override
    public String getcmpname(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        String d = "";
        try {
            CompanyMasterDAO cmpnydao = new CompanyMasterDAO(em);
            d = cmpnydao.companyname(compCode);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveHrTaxSalary()", e);
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
            logger.error("Exception occured while executing method saveHrTaxSalary()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveHrTaxSalary is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }

        return d;

    }

    //save data
    @Override
    public String saveAttendenceTracking(String login, String password, HrAttendanceMaintenanceTO hrto) throws ApplicationException {
        long begin = System.nanoTime();
        int compCode = hrto.getHrAttendanceMaintenancePKTO().getCompCode();
        boolean flag;
        String msg = "Some Error";
        HrPersonnelDetailsDAO personaldao = new HrPersonnelDetailsDAO(em);
        HrAttendanceMaintainDAO attendancedao = new HrAttendanceMaintainDAO(em);
        try {
            String empcode = personaldao.checkuserexist(compCode, login, password);
            if (empcode != null) {
                int empCode = Integer.parseInt(empcode);
                flag = attendancedao.checkattend(compCode, empCode);
                if (flag) {
                    flag = attendancedao.checktimeout(compCode, empCode);
                    if (flag) {
                        attendancedao.updatetimout(compCode, empCode);
                        msg = "Data has been Saved.";
                    } else {
                        hrto.getHrAttendanceMaintenancePKTO().setEmpCode(empCode);
                        HrAttendanceMaintainDAO hrmant = new HrAttendanceMaintainDAO(em);
                        HrAttendanceMaintenance hrateen = PayrollObjectAdaptor.adaptToHrAttenMaintainEntity(hrto);
                        hrmant.save(hrateen);
                        msg = "Data has been Saved.";
                    }

                } else {
                    hrto.getHrAttendanceMaintenancePKTO().setEmpCode(empCode);
                    HrAttendanceMaintainDAO hrmant = new HrAttendanceMaintainDAO(em);
                    HrAttendanceMaintenance hrateen = PayrollObjectAdaptor.adaptToHrAttenMaintainEntity(hrto);
                    hrmant.save(hrateen);
                    msg = "Data has been Saved.";
                }
            } else {
                msg = "User Does Not Exist";
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
        return msg;
    }

    // view Data
    @Override
    public List viewDataofAttenden(int compCode, String login, String password, Date d) throws ApplicationException {
        List l = null;
        HrPersonnelDetailsDAO personaldao = new HrPersonnelDetailsDAO(em);
        HrAttendanceMaintainDAO attendancedao = new HrAttendanceMaintainDAO(em);
        try {
            String empcode = personaldao.checkuserexist(compCode, login, password);
            if (empcode != null) {
                int empCode = Integer.parseInt(empcode);
                l = attendancedao.viewdata(compCode, empCode, d);
            }
        } catch (Exception e) {
        }
        return l;
    }

    @Override
    public List getcalanderdata(int compCode) throws ApplicationException {
        PayrollCalendarDAO cmpnydao = new PayrollCalendarDAO(em);
        List l = cmpnydao.datacalander(compCode);
        return l;
    }

    @Override
    public List<HrMstStructTO> leaveAllocationData(int compCode, String ch) throws ApplicationException {
        long begin = System.nanoTime();
        List<HrMstStructTO> hrMstStructTO = new ArrayList<HrMstStructTO>();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        try {
            List<HrMstStruct> hrMstStruct = structMasterDao.findByStructCode(compCode, ch);
            for (HrMstStruct hrmst : hrMstStruct) {
                hrMstStructTO.add(ObjectAdaptorHr.adaptToStructMasterTO(hrmst));
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
        return hrMstStructTO;
    }

    @Override
    public List viewofdataLeavePosting(int compCode, String fromDate, String toDate) throws ApplicationException {
        long begin = System.nanoTime();
        HrLeavePostingDAO hrleavepostin = new HrLeavePostingDAO(em);
        List hrLeavePostings = null;
        try {
            hrLeavePostings = hrleavepostin.viewofdataLeavePosting(compCode, fromDate, toDate);

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
        return hrLeavePostings;
    }

    @Override
    public List<HrPersonnelDetailsTO> findDataPersonalDetailFacade(int compCode, String search, int seatchflag) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO obj = new HrPersonnelDetailsDAO(em);
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTO = new ArrayList<HrPersonnelDetailsTO>();
        try {
            List<HrPersonnelDetails> hr = obj.findDataPersonalDetail(compCode, search, seatchflag);
            for (HrPersonnelDetails h : hr) {
                hrPersonnelDetailsTO.add(com.hrms.adaptor.ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(h));
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(e.getMessage());
//            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
//                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDetailsTO;
    }

    @Override
    public String operationLeaveAllocation(int compCode, String searchCriteria, String category, String flag, HrLeavePostingTO leavePostingTO) throws ApplicationException {
        long begin = System.nanoTime();
        String msg = "System Error";
        int i;
        List<HrLeavePosting> checkData;
        HrPersonnelDetailsDAO hrPersonnelDetailDAO = new HrPersonnelDetailsDAO(em);
        HrLeavePostingDAO leavePostingDAO = new HrLeavePostingDAO(em);
        try {
            if (flag.equals("1")) {
                if (searchCriteria.equals("EWE")) {
                    HrPersonnelDetails emp = hrPersonnelDetailDAO.findByEmpId(compCode, category);
                    if (emp != null) {
                        leavePostingTO.getHrLeavePostingPK().setEmpCode(emp.getHrPersonnelDetailsPK().getEmpCode().longValue());
                        leavePostingTO.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(emp));
                        checkData = leavePostingDAO.checkDupleacteData(leavePostingTO);
                        if (checkData.isEmpty()) {
                            HrLeavePosting entity = PayrollObjectAdaptor.adaptToHrLeavePostingEntity(leavePostingTO);
                            leavePostingDAO.save(entity);
                            msg = "Data has been succesfully Saved !";
                        } else {
                            msg = "You Can not Enter Duplicate Data";
                        }
                    }
                } else {
                    i = 0;
                    List<HrPersonnelDetails> hrPersonnelDetailses = hrPersonnelDetailDAO.findByCategoryWise(compCode, category);
                    if (!hrPersonnelDetailses.isEmpty()) {
                        for (HrPersonnelDetails emp : hrPersonnelDetailses) {
                            leavePostingTO.getHrLeavePostingPK().setEmpCode(emp.getHrPersonnelDetailsPK().getEmpCode().longValue());
                            leavePostingTO.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(emp));
                            checkData = leavePostingDAO.checkDupleacteData(leavePostingTO);
                            if (checkData.isEmpty()) {
                                HrLeavePosting entity = PayrollObjectAdaptor.adaptToHrLeavePostingEntity(leavePostingTO);
                                hrPersonnelDetailDAO.save(entity);
                                i++;
                            }
                        }
                        msg = i + " Data Row Added";
                    } else {
                        msg = "No Data Found";
                    }
                }
            }
            if (flag.equalsIgnoreCase("2")) {
                if (searchCriteria.equals("EWE")) {
                    HrPersonnelDetails emp = hrPersonnelDetailDAO.findByEmpId(compCode, category);
                    if (emp != null) {
                        leavePostingTO.getHrLeavePostingPK().setEmpCode(emp.getHrPersonnelDetailsPK().getEmpCode().longValue());
                        leavePostingTO.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(emp));
                        HrLeavePosting hrLeavePosting = (HrLeavePosting) leavePostingDAO.findById(new HrLeavePosting(), PayrollObjectAdaptor.adaptToHrLeavePostingPKEntity(leavePostingTO.getHrLeavePostingPK()));
                        hrLeavePosting.setPostingDate(leavePostingTO.getPostingDate());
                        leavePostingDAO.update(hrLeavePosting);
                        msg = "Data has been succesfully Updated !";
                    } else {
                        msg = "Employe Status is N";
                    }
                } else {
                    List<HrPersonnelDetails> hrPersonnelDetailses = hrPersonnelDetailDAO.findByCategoryWise(compCode, category);
                    if (!hrPersonnelDetailses.isEmpty()) {
                        for (HrPersonnelDetails emp : hrPersonnelDetailses) {
                            leavePostingTO.getHrLeavePostingPK().setEmpCode(emp.getHrPersonnelDetailsPK().getEmpCode().longValue());
                            leavePostingTO.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(emp));
                            HrLeavePosting hrLeavePosting = (HrLeavePosting) leavePostingDAO.findById(new HrLeavePosting(), PayrollObjectAdaptor.adaptToHrLeavePostingPKEntity(leavePostingTO.getHrLeavePostingPK()));
                            hrLeavePosting.setPostingDate(leavePostingTO.getPostingDate());
                            leavePostingDAO.update(hrLeavePosting);
                        }
                        msg = "Data has been succesfully Updated !";
                    }
                    msg = "No Data Found";
                }
            }
            if (flag.equals("3")) {
                if (searchCriteria.equals("EWE")) {
                    HrPersonnelDetails emp = hrPersonnelDetailDAO.findByEmpId(compCode, category);
                    if (emp != null) {
                        leavePostingTO.getHrLeavePostingPK().setEmpCode(emp.getHrPersonnelDetailsPK().getEmpCode().longValue());
                        leavePostingTO.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(emp));
                        leavePostingDAO.delete(new HrLeavePosting(), PayrollObjectAdaptor.adaptToHrLeavePostingPKEntity(leavePostingTO.getHrLeavePostingPK()));
                        msg = "Data has been succesfully Deleted !";
                    } else {
                        msg = "Employe Status is N";
                    }
                } else {
                    i = 0;
                    List<HrPersonnelDetails> hrPersonnelDetailses = hrPersonnelDetailDAO.findByCategoryWise(compCode, category);
                    if (!hrPersonnelDetailses.isEmpty()) {
                        for (HrPersonnelDetails emp : hrPersonnelDetailses) {
                            leavePostingTO.getHrLeavePostingPK().setEmpCode(emp.getHrPersonnelDetailsPK().getEmpCode().longValue());
                            leavePostingTO.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(emp));
                            leavePostingDAO.delete(new HrLeavePosting(), PayrollObjectAdaptor.adaptToHrLeavePostingPKEntity(leavePostingTO.getHrLeavePostingPK()));
                            i++;
                        }
                        msg = i + " Row Data Deleted";
                    } else {
                        msg = "No Data Found";
                    }

                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(e.getMessage());
//            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
//                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getOpenCalendarList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

    @Override
    public List<HrHolidayMasterTO> getHolidayList(int compCode, Date d1, Date d2) throws ApplicationException {
        long begin = System.nanoTime();
        HrHolidayMasterDAO dao = new HrHolidayMasterDAO(em);
        List<HrHolidayMasterTO> hrHolidayMasterTOs = new ArrayList<HrHolidayMasterTO>();
        try {
            List<HrHolidayMaster> holidayList = dao.getHolidayList(compCode, d1, d2);
            for (HrHolidayMaster hrHolidayMaster : holidayList) {
                hrHolidayMasterTOs.add(PayrollObjectAdaptor.adaptTOHrHolidayMasterTO(hrHolidayMaster));
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
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
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getOpenCalendarList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrHolidayMasterTOs;
    }

    @Override
    public String saveHolidayMasterDetails(String flag, int compCode, HrHolidayMasterTO holidayMasterTO) throws ApplicationException {
        long begin = System.nanoTime();
        HrHolidayMasterDAO dao = new HrHolidayMasterDAO(em);
        String msg = "True";
        try {
            Date fromDate;
            Date toDate;
            Calendar cal = Calendar.getInstance();
            String[] strDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thusday", "Friday", "Saturday"};
            if (flag.equals("2")) {
                fromDate = holidayMasterTO.getHrPayrollCalendar().getPayrollCalendarPKTO().getDateFrom();
                cal.setTime(holidayMasterTO.getHrPayrollCalendar().getPayrollCalendarPKTO().getDateTo());
                cal.add(Calendar.DAY_OF_WEEK, 1);
                toDate = cal.getTime();
                while (fromDate.before(toDate)) {
                    cal.setTime(fromDate);
                    if (strDays[cal.get(Calendar.DAY_OF_WEEK) - 1].equalsIgnoreCase(holidayMasterTO.getWeekDays())) {
                        holidayMasterTO.getHrHolidayMasterPKTO().setHolidayCode(dao.findMaxHolidayCode(compCode) + 1);
                        holidayMasterTO.setHolidayDate(fromDate);
                        holidayMasterTO.setHolidayDateFrom(fromDate);
                        holidayMasterTO.setHolidayDateTo(fromDate);
                        HrHolidayMaster entity = PayrollObjectAdaptor.adaptTOHrHolidayMasterEntity(holidayMasterTO);
                        dao.save(entity);
                    }
                    cal.add(Calendar.DAY_OF_WEEK, 1);
                    fromDate = cal.getTime();
                }
            } else {
                fromDate = holidayMasterTO.getHolidayDateFrom();
                cal.setTime(holidayMasterTO.getHolidayDateTo());
                cal.add(Calendar.DAY_OF_WEEK, 1);
                toDate = cal.getTime();
                while (fromDate.before(toDate)) {
                    cal.setTime(fromDate);
                    holidayMasterTO.getHrHolidayMasterPKTO().setHolidayCode(dao.findMaxHolidayCode(compCode) + 1);
                    holidayMasterTO.setHolidayDate(holidayMasterTO.getHolidayDateTo());
                    holidayMasterTO.setWeekDays(strDays[cal.get(Calendar.DAY_OF_WEEK) - 1]);
                    HrHolidayMaster entity = PayrollObjectAdaptor.adaptTOHrHolidayMasterEntity(holidayMasterTO);
                    dao.save(entity);
                    cal.add(Calendar.DAY_OF_WEEK, 1);
                    fromDate = cal.getTime();
                }
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
        return msg;
    }

    @Override
    public String updateHolidayMasterDetails(HrHolidayMasterTO holidayMasterTO) throws ApplicationException {
        long begin = System.nanoTime();
        HrHolidayMasterDAO dao = new HrHolidayMasterDAO(em);
        String msg = "True";
        try {
            Calendar cal = Calendar.getInstance();
            String[] strDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thusday", "Friday", "Saturday"};
            cal.setTime(holidayMasterTO.getHolidayDateTo());
            holidayMasterTO.setHolidayDate(holidayMasterTO.getHolidayDateTo());
            holidayMasterTO.setWeekDays(strDays[cal.get(Calendar.DAY_OF_WEEK) - 1]);
            HrHolidayMaster entity = PayrollObjectAdaptor.adaptTOHrHolidayMasterEntity(holidayMasterTO);
            dao.update(entity);
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
        return msg;
    }

    @Override
    public String deleteHolidayMasterDetails(List<HrHolidayMasterTOPK> holidayMasterTOPKList) throws ApplicationException {
        long begin = System.nanoTime();
        HrHolidayMasterDAO dao = new HrHolidayMasterDAO(em);
        String msg = "True";
        try {
            for (HrHolidayMasterTOPK holidayMasterTOPK : holidayMasterTOPKList) {
                HrHolidayMaster entity = dao.findByPrimaryKey(PayrollObjectAdaptor.adaptTOHrHolidayMasterPKEntity(holidayMasterTOPK));
                dao.delete(entity);
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
        return msg;
    }

    @Override
    public List<CompanyMasterTO> getCompanyMasterTO(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        CompanyMasterDAO obj = new CompanyMasterDAO(em);
        List<CompanyMasterTO> hrPersonnelDetailsTO = new ArrayList<CompanyMasterTO>();
        try {
            List<CompanyMaster> hr = obj.getCompanyMasterTO(compCode);
            for (CompanyMaster h : hr) {
                hrPersonnelDetailsTO.add(PayrollObjectAdaptor.adaptToCompanyMasterTO(h));
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
        return hrPersonnelDetailsTO;
    }

    public String performOprationSaveTaxinvestment(String flag, HrTaxInvestmentCategoryTO hrTaxInvestmentCategoryTO) {
        long begin = System.nanoTime();
        String result = "";
        HrTaxInvestmentCategoryDAO hrTaxInvestmentCategoryobj = new HrTaxInvestmentCategoryDAO(em);

        try {
            if (flag.equalsIgnoreCase("SAVE")) {
                HrTaxInvestmentCategory hrTaxInvestmentCategoryEntity = PayrollObjectAdaptor.adaptToHrTaxInvestmentCategoryEntity(hrTaxInvestmentCategoryTO);
                hrTaxInvestmentCategoryobj.save(hrTaxInvestmentCategoryEntity);
                return "Data saved successfully";
            }
            if (flag.equalsIgnoreCase("EDIT")) {
                HrTaxInvestmentCategory hrTaxInvestmentCategoryEntity = PayrollObjectAdaptor.adaptToHrTaxInvestmentCategoryEntity(hrTaxInvestmentCategoryTO);
                hrTaxInvestmentCategoryobj.update(hrTaxInvestmentCategoryEntity);
                return "Data updated successfully!";
            }
            if (flag.equalsIgnoreCase("DELETE")) {
                HrTaxInvestmentCategory hrTaxInvestmentCategoryEntity = PayrollObjectAdaptor.adaptToHrTaxInvestmentCategoryEntity(hrTaxInvestmentCategoryTO);
                hrTaxInvestmentCategoryobj.delete(hrTaxInvestmentCategoryEntity, hrTaxInvestmentCategoryEntity.getHrTaxInvestmentCategoryPK());
                return "Data deleted successfully!";
            }

        } catch (NullEntityException ex) {
            java.util.logging.Logger.getLogger(PayrollMasterFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            java.util.logging.Logger.getLogger(PayrollMasterFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * ***********Himanshu End*******
     */
    public String getMaxTaxSlabCode(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrTaxSlabMasterDAO dao = new HrTaxSlabMasterDAO(em);
        String max = "0";
        try {
            max = dao.getMaxTaxSlabCode(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getMaxTaxSlabCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxTaxSlabCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getMaxTaxSlabCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    public String saveUpdateTaxSlabMasterDetails(HrTaxSlabMasterTO hrTaxSlabMasterTO, String mode) throws ApplicationException {
        String msg = "";
        try {
            HrTaxSlabMaster hrTaxSlabMasterEntity = PayrollObjectAdaptor.adaptToHrTaxSlabMasterEntity(hrTaxSlabMasterTO);
            HrTaxSlabMasterDAO masterDAO = new HrTaxSlabMasterDAO(em);
            if (mode.equalsIgnoreCase("save")) {
                masterDAO.save(hrTaxSlabMasterEntity);
                msg = "Record has been saved successfully.";
            } else if (mode.equalsIgnoreCase("edit")) {
                masterDAO.update(hrTaxSlabMasterEntity);
                msg = "Record has been updated successfully.";
            } else if (mode.equalsIgnoreCase("delete")) {
                masterDAO.delete(hrTaxSlabMasterEntity, hrTaxSlabMasterEntity.getHrTaxSlabMasterPK());
                msg = "Record has been deleted successfully.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public List<HrTaxSlabMasterTO> getTaxSlabMasterDetails(int compCode) throws ApplicationException {
        List<HrTaxSlabMasterTO> slabMasterTOs = new ArrayList<HrTaxSlabMasterTO>();
        try {
            HrTaxSlabMasterDAO masterDAO = new HrTaxSlabMasterDAO(em);
            List<HrTaxSlabMaster> taxSlabMasterDetails = masterDAO.getTaxSlabMasterDetails(compCode);
            if (!taxSlabMasterDetails.isEmpty()) {
                for (HrTaxSlabMaster entity : taxSlabMasterDetails) {
                    HrTaxSlabMasterTO adaptToHrTaxSlabMasterTO = PayrollObjectAdaptor.adaptToHrTaxSlabMasterTO(entity);
                    slabMasterTOs.add(adaptToHrTaxSlabMasterTO);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return slabMasterTOs;
    }

    public List<TaxInvestmentCategoryTO> viewDataTaxInvestmentCategory(int compCode) throws ApplicationException {
        HrTaxInvestmentCategoryDAO daoobj = new HrTaxInvestmentCategoryDAO(em);
        List<TaxInvestmentCategoryTO> investmentCategoryTOs = new ArrayList<TaxInvestmentCategoryTO>();

        try {
            List<HrTaxInvestmentCategory> result = daoobj.viewDataTaxInvestmentCategory(compCode);
            for (HrTaxInvestmentCategory entity : result) {
                HrPersonnelDetails tableObj = daoobj.findEmployeeName(entity.getHrTaxInvestmentCategoryPK().getEmpCode().intValue());
                HrTaxInvestmentCategoryTO adaptToHrTaxInvestmentCategoryTO = PayrollObjectAdaptor.adaptToHrTaxInvestmentCategoryTO(entity);
                TaxInvestmentCategoryTO taxInvestmentCategoryTO = new TaxInvestmentCategoryTO();
                taxInvestmentCategoryTO.setToObj(adaptToHrTaxInvestmentCategoryTO);
                taxInvestmentCategoryTO.setEmpName(tableObj.getEmpName());
                investmentCategoryTOs.add(taxInvestmentCategoryTO);
            }
            return investmentCategoryTOs;
        } catch (DAOException e) {
            logger.error("Exception occured while executing method viewDataTaxInvestment()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }

    public String findMaxcategoryCode(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrTaxInvestmentCategoryDAO dao = new HrTaxInvestmentCategoryDAO(em);
        String max = "0";
        try {
            max = dao.findMaxcategoryCode(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method findMaxcategoryCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method findMaxcategoryCode", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for findMaxcategoryCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    public List findEmployeeName(int empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrTaxInvestmentCategoryDAO dao = new HrTaxInvestmentCategoryDAO(em);
        List result = null;
        try {
            result = (List) dao.findEmployeeName(empCode);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectCategoryCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectCategoryCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectCategorycode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }

    @Override
    public List retrieveAllPaidUnPaidLeaves(int compCode, int empcode, String fromDt, String toDt) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select days_taken,paid from hr_leave_register "
                    + "where comp_code=" + compCode + " and emp_code=" + empcode + " and "
                    + "str_to_date(from_date,'%d/%m/%Y') between '" + fromDt + "' and '" + toDt + "' and "
                    + "str_to_date(to_date,'%d/%m/%Y') between '" + fromDt + "' and '" + toDt + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<EmpSalaryAllocationGridTO> getSalaryBreakUp(double salary, int compCode, String empCd) throws ApplicationException {
        List<EmpSalaryAllocationGridTO> empsalaryBreakUpLst = new ArrayList<EmpSalaryAllocationGridTO>();
//        HrSalaryStructureDAO hrSalaryStructureDAO = new HrSalaryStructureDAO(em);
//        HrSlabMasterDAO hrSlabMasterDAO = new HrSlabMasterDAO(em);
//        HrSalaryAllocationDAO hrSalaryAllocationDAO = new HrSalaryAllocationDAO(em);
//        String newPurposeCode, newNature, newAlDesc, slabCriteria = null;
//        double slabCriteriaAmount = 0, varAmount;
//        try {
//            List<HrSalaryStructure> hrSalaryStructureList = hrSalaryStructureDAO.hrSalStructNotSaved(compCode, empCd);
//            if (!hrSalaryStructureList.isEmpty()) {
//                for (HrSalaryStructure hrSalaryStructure : hrSalaryStructureList) {
//                    newPurposeCode = hrSalaryStructure.getHrSalaryStructurePK().getPurposeCode();
//                    newNature = hrSalaryStructure.getHrSalaryStructurePK().getNature();
//                    newAlDesc = hrSalaryStructure.getHrSalaryStructurePK().getAlDesc();
//                    HrSlabMaster hrSlabMaster = new HrSlabMaster();
//                    HrSlabMasterPK hrSlabMasterPK = new HrSlabMasterPK();
//                    hrSlabMaster.setAlDesc(newAlDesc);
//                    hrSlabMasterPK.setCompCode(compCode);
//                    hrSlabMasterPK.setNature(newNature);
//                    hrSlabMasterPK.setPurposeCode(newPurposeCode);
////                    hrSlabMasterPK.setDateFrom(fromDate);
////                    hrSlabMasterPK.setDateTo(toDate);
//                    hrSlabMaster.setAppFlg('Y');
//                    hrSlabMaster.setHrSlabMasterPK(hrSlabMasterPK);
//                    List<HrSlabMaster> hrSlabMasterList = hrSlabMasterDAO.getSlabMasterByvariousParam(hrSlabMaster, salary);
//                    if (!hrSlabMasterList.isEmpty()) {
//                        for (HrSlabMaster hrSlabMaster1 : hrSlabMasterList) {
//                            slabCriteria = hrSlabMaster1.getSlabCriteria();
//                            slabCriteriaAmount = hrSlabMaster1.getSlabCriteriaAmt();
//                        }
//                        HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//                        hrSalaryAllocationPK.setCompCode(compCode);
//                        hrSalaryAllocationPK.setComponent(newAlDesc);
//                        List<HrSalaryAllocation> salaryAllocationList = hrSalaryAllocationDAO.findsalAllocByVariousParameters(hrSalaryAllocationPK);
//                        if (salaryAllocationList.isEmpty()) {
//                            EmpSalaryAllocationGridTO hrSalaryAllocation = new EmpSalaryAllocationGridTO();
//                            if (slabCriteria.equalsIgnoreCase("PERCENTAGE")) {
//                                varAmount = (slabCriteriaAmount / 100) * salary;
//                                hrSalaryAllocation.setPurposeCode(newPurposeCode);
//                                hrSalaryAllocation.setNature(newNature);
//                                hrSalaryAllocation.setComponent(newAlDesc);
//                                hrSalaryAllocation.setBasicSalary(salary);
//                                hrSalaryAllocation.setAmount(varAmount);
//                                empsalaryBreakUpLst.add(hrSalaryAllocation);
//                            }
//                            if (slabCriteria.equalsIgnoreCase("AMOUNT")) {
//                                varAmount = slabCriteriaAmount;
//                                hrSalaryAllocation.setPurposeCode(newPurposeCode);
//                                hrSalaryAllocation.setNature(newNature);
//                                hrSalaryAllocation.setComponent(newAlDesc);
//                                hrSalaryAllocation.setBasicSalary(salary);
//                                hrSalaryAllocation.setAmount(varAmount);
//                                empsalaryBreakUpLst.add(hrSalaryAllocation);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
        return empsalaryBreakUpLst;
    }

    @Override
    public List maxSalaryStructShortCode(int compCode, String purCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrSalaryStructureDAO hrSalStructDAO = new HrSalaryStructureDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrSalStructDAO.maxSalaryStructShortCode(compCode, purCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method maxSalaryStructShortCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method maxSalaryStructShortCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for maxSalaryStructShortCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public String insertSalaryallocation(SalaryAllocationTO salaryAllocationTO, List<EmpSalaryAllocationGridTO> salaryAddGrid) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}