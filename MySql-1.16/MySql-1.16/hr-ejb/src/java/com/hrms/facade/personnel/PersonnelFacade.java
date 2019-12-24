package com.hrms.facade.personnel;

import com.hrms.adaptor.ObjectAdaptorPersonnel;
import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.adaptor.PayrollObjectAdaptor;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrEmpAdvanceDtTO;
import com.hrms.common.to.HrEmpAdvanceHdTO;
import com.hrms.common.to.HrEmpLoanDtTO;
import com.hrms.common.to.HrEmpLoanHdTO;
import com.hrms.common.to.HrMembershipDetailTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDependentTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrPersonnelHobbyTO;
import com.hrms.common.to.HrPersonnelLangTO;
import com.hrms.common.to.HrPersonnelPreviousCompanyTO;
import com.hrms.common.to.HrPersonnelQualificationTO;
import com.hrms.common.to.HrPersonnelReferenceTO;
import com.hrms.common.to.HrPersonnelTransportTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.AdminUserDAO;
import com.hrms.dao.HrApprisalDetailsDAO;
import com.hrms.dao.HrApprisalPerformanceDtDAO;
import com.hrms.dao.HrAttendanceDetailsDAO;
import com.hrms.dao.HrAttendanceMaintenanceDAO;
import com.hrms.dao.HrEmpAdvanceDtDAO;
import com.hrms.dao.HrEmpAdvanceHdDAO;
import com.hrms.dao.HrEmpLoanDtDAO;
import com.hrms.dao.HrEmpLoanHdDAO;
import com.hrms.dao.HrLeavePostingDAO;
import com.hrms.dao.HrLeaveRegisterDAO;
import com.hrms.dao.HrMembershipDetailDAO;
import com.hrms.dao.HrMstStructDAO;
import com.hrms.dao.HrPayrollCloseTraceDAO;
import com.hrms.dao.HrPersonnelDependentDAO;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.HrPersonnelHobbyDAO;
import com.hrms.dao.HrPersonnelLangDAO;
import com.hrms.dao.HrPersonnelPreviousCompanyDAO;
import com.hrms.dao.HrPersonnelQualificationDAO;
import com.hrms.dao.HrPersonnelReferenceDAO;
import com.hrms.dao.HrPersonnelTransportDAO;
import com.hrms.dao.HrSalaryAllocationDAO;
import com.hrms.dao.HrSalaryProcessingDAO;
import com.hrms.dao.HrShiftMapDAO;
import com.hrms.dao.HrTrainingExecutionDAO;
import com.hrms.dao.HrTrainingPlanDAO;
import com.hrms.dao.PayrollCalendarDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstStruct;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.hr.HrPersonnelDetailsHis;
import com.hrms.entity.hr.HrPersonnelDetailsPK;
import com.hrms.entity.hr.HrTrainingExecution;
import com.hrms.entity.hr.HrTrainingExecutionPK;
import com.hrms.entity.hr.HrTrainingPlan;
import com.hrms.entity.hr.HrTrainingPlanPK;
import com.hrms.entity.payroll.HrAttendanceDetails;
import com.hrms.entity.payroll.HrAttendanceDetailsPK;
import com.hrms.entity.payroll.HrAttendanceMaintenance;
import com.hrms.entity.payroll.HrAttendanceMaintenancePK;
import com.hrms.entity.payroll.HrLeavePosting;
import com.hrms.entity.payroll.HrLeavePostingPK;
import com.hrms.entity.payroll.HrLeaveRegister;
import com.hrms.entity.payroll.HrLeaveRegisterPK;
import com.hrms.entity.payroll.HrPayrollCalendar;
import com.hrms.entity.payroll.HrPayrollCloseTrace;
import com.hrms.entity.payroll.HrPayrollCloseTracePK;
import com.hrms.entity.payroll.HrSalaryAllocation;
import com.hrms.entity.payroll.HrSalaryAllocationPK;
import com.hrms.entity.payroll.HrSalaryProcessing;
import com.hrms.entity.payroll.HrSalaryProcessingPK;
import com.hrms.entity.payroll.HrShiftMap;
import com.hrms.entity.payroll.HrShiftMapPK;
import com.hrms.entity.personnel.HrApprisalDetails;
import com.hrms.entity.personnel.HrApprisalDetailsPK;
import com.hrms.entity.personnel.HrApprisalPerformanceDt;
import com.hrms.entity.personnel.HrApprisalPerformanceDtPK;
import com.hrms.entity.personnel.HrEmpAdvanceDt;
import com.hrms.entity.personnel.HrEmpAdvanceDtPK;
import com.hrms.entity.personnel.HrEmpAdvanceHd;
import com.hrms.entity.personnel.HrEmpAdvanceHdPK;
import com.hrms.entity.personnel.HrEmpLoanDt;
import com.hrms.entity.personnel.HrEmpLoanDtPK;
import com.hrms.entity.personnel.HrEmpLoanHd;
import com.hrms.entity.personnel.HrEmpLoanHdPK;
import com.hrms.entity.personnel.HrMembershipDetail;
import com.hrms.entity.personnel.HrMembershipDetailPK;
import com.hrms.entity.personnel.HrPersonnelDependent;
import com.hrms.entity.personnel.HrPersonnelDependentPK;
import com.hrms.entity.personnel.HrPersonnelHobby;
import com.hrms.entity.personnel.HrPersonnelHobbyPK;
import com.hrms.entity.personnel.HrPersonnelLang;
import com.hrms.entity.personnel.HrPersonnelLangPK;
import com.hrms.entity.personnel.HrPersonnelPreviousCompany;
import com.hrms.entity.personnel.HrPersonnelPreviousCompanyPK;
import com.hrms.entity.personnel.HrPersonnelQualification;
import com.hrms.entity.personnel.HrPersonnelQualificationPK;
import com.hrms.entity.personnel.HrPersonnelReference;
import com.hrms.entity.personnel.HrPersonnelReferencePK;
import com.hrms.entity.personnel.HrPersonnelTransport;
import com.hrms.entity.personnel.HrPersonnelTransportPK;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

@Stateless(mappedName = "PersonnelFacadeRemote")
@Remote({PersonnelFacadeRemote.class})
public class PersonnelFacade implements PersonnelFacadeRemote {

    public SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    
    private static final Logger logger = Logger.getLogger(PersonnelFacade.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public String getAuthorization(String authorizedUser, String authorizedPassword, String formName) throws ApplicationException {
        try {
            AdminUserDAO adminUserDAO = new AdminUserDAO(em);
            return (adminUserDAO.checkUserNamePassword(authorizedUser, authorizedPassword, formName));
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }

    @Override
    public List<HrMstStructTO> getIntialData(int compCode, String structCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();

        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, structCode);
            if (!structMasterList.isEmpty()) {
                for (HrMstStruct hrMstStruct : structMasterList) {
                    structMasterTOs.add(com.hrms.adaptor.ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
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
    public List<HrPersonnelDetailsTO> getPersonnalData(int compCode, String type, String value) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = new ArrayList<HrPersonnelDetailsTO>();
        try {
            List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDetailsDAO.findEmpByCompCodeTypeValue(compCode, type, value);
            for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
                hrPersonnelDetailsTOs.add(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelDetails));
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getOpenCalendarList()", e);
            throw new ApplicationException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getOpenCalendarList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDetailsTOs;

    }

    @Override
    public long getMaxAdvNoFromHrEmpAdvanceDt(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        long max = 0;
        HrEmpAdvanceDtDAO hrEmpAdvanceDtDAO = new HrEmpAdvanceDtDAO(em);
        try {
            max = hrEmpAdvanceDtDAO.getMaxAdvNoFromHrEmpAdvanceDt(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getMaxAdvNoFromHrEmpAdvanceDt()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    @Override
    public List getAdvanceTableData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrEmpAdvanceHdDAO hrEmpAdvanceHdDAO = new HrEmpAdvanceHdDAO(em);
        List list = null;
        try {
            list = hrEmpAdvanceHdDAO.getAdvanceTableData(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return list;
    }

    @Override
    public List getLoanTableData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrEmpLoanHdDAO hrEmpLoanHdDAO = new HrEmpLoanHdDAO(em);
        List list = null;
        try {
            list = hrEmpLoanHdDAO.getLoanTableData(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return list;
    }

    @Override
    public List<HrEmpLoanHdTO> getLoanDetails(int compCode, long empLoanNo) throws ApplicationException {
        long begin = System.nanoTime();
        HrEmpLoanHdDAO hrEmpLoanHdDAO = new HrEmpLoanHdDAO(em);
        List<HrEmpLoanHdTO> hrEmpLoanHdTOs = new ArrayList<HrEmpLoanHdTO>();
        try {
            List<HrEmpLoanHd> hrEmpLoanHdList = hrEmpLoanHdDAO.getLoanDetails(compCode, empLoanNo);
            for (HrEmpLoanHd hrEmpLoanHd : hrEmpLoanHdList) {
                hrEmpLoanHdTOs.add(ObjectAdaptorPersonnel.adaptToHrEmpLoanHdTO(hrEmpLoanHd));
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
        return hrEmpLoanHdTOs;
    }

    @Override
    public List<HrEmpAdvanceHdTO> getAdvanceDetails(int compCode, long advNo) throws ApplicationException {
        long begin = System.nanoTime();
        HrEmpAdvanceHdDAO hrEmpAdvanceHdDAO = new HrEmpAdvanceHdDAO(em);
        List<HrEmpAdvanceHdTO> hrEmpAdvanceHdTOs = new ArrayList<HrEmpAdvanceHdTO>();
        try {
            List<HrEmpAdvanceHd> hrEmpAdvanceHdList = hrEmpAdvanceHdDAO.getAdvanceDetails(compCode, advNo);
            for (HrEmpAdvanceHd hrEmpAdvanceHd : hrEmpAdvanceHdList) {
                hrEmpAdvanceHdTOs.add(ObjectAdaptorPersonnel.adaptToHrEmpAdvanceHdTO(hrEmpAdvanceHd));
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
        return hrEmpAdvanceHdTOs;
    }

    @Override
    public long getMaxLoanNoFromHrEmpLoanDt(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        long max = 0;
        HrEmpLoanDtDAO hrEmpLoanDtDAO = new HrEmpLoanDtDAO(em);
        try {
            max = hrEmpLoanDtDAO.getMaxAdvNoFromHrEmpLoanDt(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    @Override
    public List getGradeAnddesignationList(int compCode) throws ApplicationException {
        HrMstStructDAO hrMstStructDAO = new HrMstStructDAO(em);
        List list = null;
        try {
            list = hrMstStructDAO.getGradeAnddesignationList(compCode);
            return list;
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }

    @Override
    public String getLastEmployeeId(int compCode) throws ApplicationException {
        try {
            HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
            return (hrPersonnelDetailsDAO.getLastEmployeeId(compCode));
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }

    @Override
    public long getMaxEmpCode() throws ApplicationException {
        long begin = System.nanoTime();
        long max = 0;
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        try {
            max = hrPersonnelDetailsDAO.getMaxEmpCode();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    @Override
    public HrPersonnelDetailsTO getAllByCompCodeAndEmpCodeOrEMPID(int compCode, long empCode, String empId) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        HrPersonnelDetailsTO hrPersonnelDetailsTO = null;
        try {
            HrPersonnelDetails hrPersonnelDetails = hrPersonnelDetailsDAO.getAllByCompCodeAndEmpCodeOrEMPID(compCode, empCode, empId);
            hrPersonnelDetailsTO = ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelDetails);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAllByCompCodeAndEmpCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAllByCompCodeAndEmpCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAllByCompCodeAndEmpCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDetailsTO;
    }

    @Override
    public long getMaxRefCode() throws ApplicationException {
        long begin = System.nanoTime();
        long max = 0;
        HrPersonnelReferenceDAO hrPersonnelReferenceDAO = new HrPersonnelReferenceDAO(em);
        try {
            max = hrPersonnelReferenceDAO.getMaxRefCode();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    @Override
    public List<HrPersonnelReferenceTO> getReferenceTableData(long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelReferenceDAO hrPersonnelReferenceDAO = new HrPersonnelReferenceDAO(em);
        List<HrPersonnelReferenceTO> hrPersonnelReferenceTOs = new ArrayList<HrPersonnelReferenceTO>();
        try {
            List<HrPersonnelReference> hrPersonnelReferenceList = hrPersonnelReferenceDAO.findEmpByEmpCode(empCode);
            for (HrPersonnelReference hrPersonnelReference : hrPersonnelReferenceList) {
                hrPersonnelReferenceTOs.add(ObjectAdaptorPersonnel.adaptTOHrPersonnelReferenceTO(hrPersonnelReference));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelReferenceTOs;
    }

    @Override
    public String saveHrPersonnelReference(HrPersonnelReferenceTO hrPersonnelReferenceTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelReferenceTO);
        try {
            HrPersonnelReferenceDAO hrPersonnelReferenceDAO = new HrPersonnelReferenceDAO(em);
            HrPersonnelReference hrPersonnelReference = ObjectAdaptorPersonnel.adaptTOHrPersonnelReferenceEntity(hrPersonnelReferenceTO);
            if (mode.equalsIgnoreCase("save")) {
                hrPersonnelReferenceDAO.save(hrPersonnelReference);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrPersonnelReferenceDAO.update(hrPersonnelReference);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrPersonnelReferenceDAO.delete(hrPersonnelReference, hrPersonnelReference.getHrPersonnelReferencePK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public String saveHrPersonnelQualification(HrPersonnelQualificationTO hrPersonnelQualificationTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelQualificationTO);
        try {
            HrPersonnelQualificationDAO hrPersonnelQualificationDAO = new HrPersonnelQualificationDAO(em);
            HrPersonnelQualification hrPersonnelQualification = ObjectAdaptorPersonnel.adaptTOHrPersonnelQualificationEntity(hrPersonnelQualificationTO);
            if (mode.equalsIgnoreCase("save")) {
                hrPersonnelQualificationDAO.save(hrPersonnelQualification);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrPersonnelQualificationDAO.update(hrPersonnelQualification);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrPersonnelQualificationDAO.delete(hrPersonnelQualification, hrPersonnelQualification.getHrPersonnelQualificationPK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public List<HrPersonnelQualificationTO> getQualificationTableData(long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelQualificationDAO hrPersonnelQualificationDAO = new HrPersonnelQualificationDAO(em);
        List<HrPersonnelQualificationTO> hrPersonnelQualificationTOs = new ArrayList<HrPersonnelQualificationTO>();
        try {
            List<HrPersonnelQualification> hrPersonnelQualificationList = hrPersonnelQualificationDAO.findEmpByEmpCode(empCode);
            for (HrPersonnelQualification hrPersonnelQualification : hrPersonnelQualificationList) {
                hrPersonnelQualificationTOs.add(ObjectAdaptorPersonnel.adaptTOHrPersonnelQualificationTO(hrPersonnelQualification));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelQualificationTOs;
    }

    @Override
    public List<HrPersonnelDependentTO> getDependentTableData(long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDependentDAO hrPersonnelDependentDAO = new HrPersonnelDependentDAO(em);
        List<HrPersonnelDependentTO> hrPersonnelDependentTOs = new ArrayList<HrPersonnelDependentTO>();
        try {
            List<HrPersonnelDependent> hrPersonnelDependentList = hrPersonnelDependentDAO.findEmpByEmpCode(empCode);
            for (HrPersonnelDependent hrPersonnelDependent : hrPersonnelDependentList) {
                hrPersonnelDependentTOs.add(ObjectAdaptorPersonnel.adaptTOHrPersonnelDependentTO(hrPersonnelDependent));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDependentTOs;
    }

    @Override
    public String saveHrPersonnelDependent(HrPersonnelDependentTO hrPersonnelDependentTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelDependentTO);
        try {
            HrPersonnelDependentDAO hrPersonnelDependentDAO = new HrPersonnelDependentDAO(em);
            HrPersonnelDependent hrPersonnelDependent = ObjectAdaptorPersonnel.adaptTOHrPersonnelDependentEntity(hrPersonnelDependentTO);
            if (mode.equalsIgnoreCase("save")) {
                hrPersonnelDependentDAO.save(hrPersonnelDependent);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrPersonnelDependentDAO.update(hrPersonnelDependent);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrPersonnelDependentDAO.delete(hrPersonnelDependent, hrPersonnelDependent.getHrPersonnelDependentPK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public long getMaxDependentCode() throws ApplicationException {
        long begin = System.nanoTime();
        long max = 0;
        HrPersonnelDependentDAO hrPersonnelDependentDAO = new HrPersonnelDependentDAO(em);
        try {
            max = hrPersonnelDependentDAO.getMaxDependentCode();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    @Override
    public List<HrPersonnelPreviousCompanyTO> getExperienceData(long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelPreviousCompanyDAO hrPersonnelPreviousCompanyDAO = new HrPersonnelPreviousCompanyDAO(em);
        List<HrPersonnelPreviousCompanyTO> hrPersonnelPreviousCompanyTOs = new ArrayList<HrPersonnelPreviousCompanyTO>();
        try {
            List<HrPersonnelPreviousCompany> hrPersonnelPreviousCompanyList = hrPersonnelPreviousCompanyDAO.getExperienceData(empCode);
            for (HrPersonnelPreviousCompany hrPersonnelPreviousCompany : hrPersonnelPreviousCompanyList) {
                hrPersonnelPreviousCompanyTOs.add(ObjectAdaptorPersonnel.adaptTOHrPersonnelPreviousCompanyTO(hrPersonnelPreviousCompany));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getPreviousCompanyTableData()", e);
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
            logger.error("Exception occured while executing method getPreviousCompanyTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPreviousCompanyTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelPreviousCompanyTOs;
    }

    @Override
    public long getMaxPrevCompCode() throws ApplicationException {
        long begin = System.nanoTime();
        long max = 0;
        HrPersonnelPreviousCompanyDAO hrPersonnelPreviousCompanyDAO = new HrPersonnelPreviousCompanyDAO(em);
        try {
            max = hrPersonnelPreviousCompanyDAO.getMaxRefCode();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getPreviousCompanyTableData()", e);
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
            logger.error("Exception occured while executing method getPreviousCompanyTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPreviousCompanyTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    @Override
    public String saveHrPersonnelPreviousCompany(HrPersonnelPreviousCompanyTO hrPersonnelPreviousCompanyTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelPreviousCompanyTO);
        try {
            HrPersonnelPreviousCompanyDAO hrPersonnelPreviousCompanyDAO = new HrPersonnelPreviousCompanyDAO(em);
            HrPersonnelPreviousCompany hrPersonnelPreviousCompany = ObjectAdaptorPersonnel.adaptTOHrPersonnelPreviousCompanyEntity(hrPersonnelPreviousCompanyTO);
            if (mode.equalsIgnoreCase("save")) {
                hrPersonnelPreviousCompanyDAO.save(hrPersonnelPreviousCompany);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrPersonnelPreviousCompanyDAO.update(hrPersonnelPreviousCompany);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrPersonnelPreviousCompanyDAO.delete(hrPersonnelPreviousCompany, hrPersonnelPreviousCompany.getHrPersonnelPreviousCompanyPK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getPreviousCompanyTableData()", e);
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
            logger.error("Exception occured while executing method getPreviousCompanyTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPreviousCompanyTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public List<HrPersonnelTransportTO> getTransportData(long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelTransportDAO hrPersonnelTransportDAO = new HrPersonnelTransportDAO(em);
        List<HrPersonnelTransportTO> hrPersonnelTransportTOs = new ArrayList<HrPersonnelTransportTO>();
        try {
            List<HrPersonnelTransport> hrPersonnelTransportList = hrPersonnelTransportDAO.getTransportData(empCode);
            for (HrPersonnelTransport hrPersonnelTransport : hrPersonnelTransportList) {
                hrPersonnelTransportTOs.add(ObjectAdaptorPersonnel.adaptTOHrPersonnelTransportTO(hrPersonnelTransport));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getTransportTableData()", e);
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
            logger.error("Exception occured while executing method getTransportTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getTransportTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelTransportTOs;
    }

    @Override
    public String saveHrPersonnelTransport(HrPersonnelTransportTO hrPersonnelTransportTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelTransportTO);
        try {
            HrPersonnelTransportDAO hrPersonnelTransportDAO = new HrPersonnelTransportDAO(em);
            HrPersonnelTransport hrPersonnelTransport = ObjectAdaptorPersonnel.adaptTOHrPersonnelTransportEntity(hrPersonnelTransportTO);
            if (mode.equalsIgnoreCase("save")) {
                hrPersonnelTransportDAO.save(hrPersonnelTransport);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrPersonnelTransportDAO.update(hrPersonnelTransport);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrPersonnelTransportDAO.delete(hrPersonnelTransport, hrPersonnelTransport.getHrPersonnelTransportPK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getTransportTableData()", e);
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
            logger.error("Exception occured while executing method getTransportTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getTransportTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public int getMaxMemNo() throws ApplicationException {
        long begin = System.nanoTime();
        int max = 0;
        HrMembershipDetailDAO hrMembershipDetailDAO = new HrMembershipDetailDAO(em);
        try {
            max = hrMembershipDetailDAO.getMaxMemNo();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getMaxMemNo()", e);
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
            logger.error("Exception occured while executing method getMaxMemNo()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getMaxMemNo is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return max;
    }

    @Override
    public String saveHrMembershipDetail(HrMembershipDetailTO hrMembershipDetailTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrMembershipDetailTO);
        try {
            HrMembershipDetailDAO hrMembershipDetailDAO = new HrMembershipDetailDAO(em);
            HrMembershipDetail hrMembershipDetail = ObjectAdaptorPersonnel.adaptTOHrMembershipDetailEntity(hrMembershipDetailTO);
            List<HrMembershipDetailTO> detailTOs = getMembershipData(hrMembershipDetailTO.getHrMembershipDetailPKTO().getEmpCode());
            if (!detailTOs.isEmpty()) {
                hrMembershipDetail.setPassportNo(detailTOs.get(0).getPassportNo());
                hrMembershipDetail.setPassportDate(detailTOs.get(0).getPassportDate());
            }
            if (mode.equalsIgnoreCase("save")) {
                HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
                int compCode = hrMembershipDetailTO.getHrMembershipDetailPKTO().getCompCode();
                long empCode = hrMembershipDetailTO.getHrMembershipDetailPKTO().getEmpCode();
                HrPersonnelDetails hrPersonnelDetails1 = hrPersonnelDetailsDAO.getAllByCompCodeAndEmpCodeOrEMPID(compCode, empCode, "");
                hrPersonnelDetails1.setAccomdType(hrMembershipDetailTO.getAccomdType());
                hrPersonnelDetails1.setIndivMember(hrMembershipDetailTO.getIndivMember());
                hrPersonnelDetails1.setInsuranceNo(hrMembershipDetailTO.getInsuranceNo());
                if (hrMembershipDetailTO.getPassportDate() != null || !hrMembershipDetailTO.getPassportDate().equalsIgnoreCase("")) {
                    hrPersonnelDetails1.setPassportDate(dmyFormat.parse(hrMembershipDetailTO.getPassportDate()));
                }
                hrPersonnelDetails1.setPassportNo(hrMembershipDetailTO.getPassportNo());
                hrPersonnelDetails1.setProfMember(hrMembershipDetailTO.getProfMember());
                if (hrMembershipDetailTO.getTravelOver() != null || !hrMembershipDetailTO.getTravelOver().equalsIgnoreCase("")) {
                    hrPersonnelDetails1.setTravOverseasStatus(hrMembershipDetailTO.getTravelOver().charAt(0));
                }
                hrPersonnelDetailsDAO.update(hrPersonnelDetails1);
                hrMembershipDetailDAO.save(hrMembershipDetail);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                try {
                    HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
                    int compCode = hrMembershipDetailTO.getHrMembershipDetailPKTO().getCompCode();
                    long empCode = hrMembershipDetailTO.getHrMembershipDetailPKTO().getEmpCode();
                    HrPersonnelDetails hrPersonnelDetails1 = hrPersonnelDetailsDAO.getAllByCompCodeAndEmpCodeOrEMPID(compCode, empCode, "");
                    hrPersonnelDetails1.setAccomdType(hrMembershipDetailTO.getAccomdType());
                    hrPersonnelDetails1.setIndivMember(hrMembershipDetailTO.getIndivMember());
                    hrPersonnelDetails1.setInsuranceNo(hrMembershipDetailTO.getInsuranceNo());
                    if (hrMembershipDetailTO.getPassportDate() != null || !hrMembershipDetailTO.getPassportDate().equalsIgnoreCase("")) {
                        hrPersonnelDetails1.setPassportDate(dmyFormat.parse(hrMembershipDetailTO.getPassportDate()));
                    }
                    hrPersonnelDetails1.setPassportNo(hrMembershipDetailTO.getPassportNo());
                    hrPersonnelDetails1.setProfMember(hrMembershipDetailTO.getProfMember());
                    if (hrMembershipDetailTO.getTravelOver() != null || !hrMembershipDetailTO.getTravelOver().equalsIgnoreCase("")) {
                        hrPersonnelDetails1.setTravOverseasStatus(hrMembershipDetailTO.getTravelOver().charAt(0));
                    }
                    hrPersonnelDetailsDAO.update(hrPersonnelDetails1);
                } catch (Exception e) {
                    System.out.println("Exception occurred while saving data in personnel details table with membership detail table");
                    System.out.println(e.getCause());
                }
                hrMembershipDetailDAO.update(hrMembershipDetail);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrMembershipDetailDAO.delete(hrMembershipDetail, hrMembershipDetail.getHrMembershipDetailPK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getMembershipDetailTableData()", e);
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
            logger.error("Exception occured while executing method getMembershipDetailTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getMembershipDetailTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public List<HrMembershipDetailTO> getMembershipData(long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMembershipDetailDAO hrMembershipDetailDAO = new HrMembershipDetailDAO(em);
        List<HrMembershipDetailTO> hrMembershipDetailTOs = new ArrayList<HrMembershipDetailTO>();
        try {
            List<HrMembershipDetail> hrMembershipDetailList = hrMembershipDetailDAO.getMembershipData(empCode);
            for (HrMembershipDetail hrMembershipDetail : hrMembershipDetailList) {
                hrMembershipDetailTOs.add(ObjectAdaptorPersonnel.adaptTOHrMembershipDetailTO(hrMembershipDetail));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getMembershipDetailTableData()", e);
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
            logger.error("Exception occured while executing method getMembershipDetailTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getMembershipDetailTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrMembershipDetailTOs;
    }

    @Override
    public String saveHrPersonnelJobDetails(HrPersonnelDetailsTO hrPersonnelDetailsTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelDetailsTO);
        try {
            HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
            if (mode.equalsIgnoreCase("update")) {
                int compCode = hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getCompCode();
                long empCode = hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode();
                String empId = hrPersonnelDetailsTO.getEmpId();
                HrPersonnelDetails hrPersonnelDetails1 = hrPersonnelDetailsDAO.getAllByCompCodeAndEmpCodeOrEMPID(compCode, empCode, empId);
                hrPersonnelDetails1.setTotExpr(hrPersonnelDetailsTO.getTotExpr().floatValue());
                hrPersonnelDetails1.setAutoExpr(hrPersonnelDetailsTO.getAutoExpr().floatValue());
                hrPersonnelDetails1.setNoticePeriod(hrPersonnelDetailsTO.getNoticePeriod().floatValue());
                hrPersonnelDetails1.setProbPeriod(hrPersonnelDetailsTO.getProbPeriod().floatValue());
                hrPersonnelDetails1.setConfirmationDate(hrPersonnelDetailsTO.getConfirmationDate());
                hrPersonnelDetails1.setOtEligibility(hrPersonnelDetailsTO.getOtEligibility());
                hrPersonnelDetails1.setRepTo(hrPersonnelDetailsTO.getRepTo());
                hrPersonnelDetails1.setSkillCode(hrPersonnelDetailsTO.getSkillCode());
                hrPersonnelDetails1.setSpecialCode(hrPersonnelDetailsTO.getSpecialCode());
                hrPersonnelDetails1.setCareer(hrPersonnelDetailsTO.getCareer());
                hrPersonnelDetails1.setJobdesc(hrPersonnelDetailsTO.getJobdesc());
                hrPersonnelDetailsDAO.update(hrPersonnelDetails1);
                return "Data has been successfully updated.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public String saveHrPersonnelEmployeeDetails(HrPersonnelDetailsTO hrPersonnelDetailsTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelDetailsTO);
        try {
            HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
            HrPersonnelDetails hrPersonnelDetails = ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrPersonnelDetailsTO);
            if (mode.equalsIgnoreCase("save")) {
                //Already customer id existence.
                List<HrPersonnelDetails> list = hrPersonnelDetailsDAO.findByCustomerId(hrPersonnelDetailsTO.getCustomerId().trim());
                if (!list.isEmpty()) {
                    return "This customer Id is already registered.";
                }
                //End here
                hrPersonnelDetailsDAO.save(hrPersonnelDetails);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                //Pertaining the history detail
                HrPersonnelDetailsPK hrPersonnelDetailsPK = ObjectAdaptorHr.adaptToHrPeronnelDetailPKEntity(hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO());
                HrPersonnelDetails obj = (HrPersonnelDetails) hrPersonnelDetailsDAO.findById(new HrPersonnelDetails(), hrPersonnelDetailsPK);
                if (obj != null) {
                    HrPersonnelDetailsHis hrPersonnelDetailsHis = new HrPersonnelDetailsHis();

                    hrPersonnelDetailsHis.setBankAccountCode(obj.getBankAccountCode());
                    hrPersonnelDetailsHis.setBirthDate(obj.getBirthDate());
                    hrPersonnelDetailsHis.setDefaultComp(obj.getDefaultComp());
                    hrPersonnelDetailsHis.setEmpCardNo(obj.getEmpCardNo() == null ? "" : obj.getEmpCardNo());
                    hrPersonnelDetailsHis.setEmpFirName(obj.getEmpFirName() == null ? "" : obj.getEmpFirName());
                    hrPersonnelDetailsHis.setEmpId(obj.getEmpId() == null ? "" : obj.getEmpId());
                    hrPersonnelDetailsHis.setEmpName(obj.getEmpName() == null ? "" : obj.getEmpName());
                    hrPersonnelDetailsHis.setEmpStatus(obj.getEmpStatus());
                    hrPersonnelDetailsHis.setEnteredBy(obj.getEnteredBy());
                    hrPersonnelDetailsHis.setJoinDate(obj.getJoinDate());
                  //  hrPersonnelDetailsHis.setModDate(obj.getModDate());
                    hrPersonnelDetailsHis.setModDate(date);
                    hrPersonnelDetailsHis.setPassword(obj.getPassword() == null ? "" : obj.getPassword());
                    hrPersonnelDetailsHis.setSex(obj.getSex());
                    hrPersonnelDetailsHis.setWorkStatus(obj.getWorkStatus());
                    hrPersonnelDetailsHis.setCompCode(obj.getHrPersonnelDetailsPK().getCompCode());
                    hrPersonnelDetailsHis.setEmpCode(obj.getHrPersonnelDetailsPK().getEmpCode().longValue());
                    hrPersonnelDetailsHis.setCustomerId(obj.getCustomerId());
                    hrPersonnelDetailsHis.setPfAccount(obj.getPfAccount() == null ? "" : obj.getPfAccount());
                    hrPersonnelDetailsHis.setBaseBranch(obj.getBaseBranch());
                    hrPersonnelDetailsHis.setDesgCode(obj.getDesgCode());
                    hrPersonnelDetailsHis.setRetirementDate(obj.getRetirementDate());
                    hrPersonnelDetailsHis.setUanNumber(obj.getUanNumber());
                    
                    hrPersonnelDetailsDAO.save(hrPersonnelDetailsHis);
                }

                //New updation
                int compCode = hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getCompCode();
                long empCode = hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode();
                String empId = hrPersonnelDetailsTO.getEmpId();
                HrPersonnelDetails hrPersonnelDetails1 = hrPersonnelDetailsDAO.getAllByCompCodeAndEmpCodeOrEMPID(compCode, empCode, empId);
                hrPersonnelDetails1.setEmpFirName(hrPersonnelDetailsTO.getEmpFirName());
                hrPersonnelDetails1.setEmpStatus(hrPersonnelDetailsTO.getEmpStatus());
                hrPersonnelDetails1.setEmpMidName(hrPersonnelDetailsTO.getEmpMidName());
                hrPersonnelDetails1.setEmpLastName(hrPersonnelDetailsTO.getEmpLastName());
                hrPersonnelDetails1.setEmpCardNo(hrPersonnelDetailsTO.getEmpCardNo());
                hrPersonnelDetails1.setBirthDate(hrPersonnelDetailsTO.getBirthDate());
                hrPersonnelDetails1.setJoinDate(hrPersonnelDetailsTO.getJoinDate());
                hrPersonnelDetails1.setSex(hrPersonnelDetailsTO.getSex());
                hrPersonnelDetails1.setWorkStatus(hrPersonnelDetailsTO.getWorkStatus());
                hrPersonnelDetails1.setBlock(hrPersonnelDetailsTO.getBlock());
                hrPersonnelDetails1.setUnitName(hrPersonnelDetailsTO.getUnitName());
                hrPersonnelDetails1.setGradeCode(hrPersonnelDetailsTO.getGradeCode());
                hrPersonnelDetails1.setDesgCode(hrPersonnelDetailsTO.getDesgCode());
                hrPersonnelDetails1.setEmpType(hrPersonnelDetailsTO.getEmpType());
                hrPersonnelDetails1.setCatgCode(hrPersonnelDetailsTO.getCatgCode());
                hrPersonnelDetails1.setZones(hrPersonnelDetailsTO.getZones());
                hrPersonnelDetails1.setLocatCode(hrPersonnelDetailsTO.getLocatCode());
                hrPersonnelDetails1.setDeptCode(hrPersonnelDetailsTO.getDeptCode());
                hrPersonnelDetails1.setSubdeptCode(hrPersonnelDetailsTO.getSubdeptCode());
                hrPersonnelDetails1.setBankCode(hrPersonnelDetailsTO.getBankCode());
                hrPersonnelDetails1.setBankAccountCode(hrPersonnelDetailsTO.getBankAccountCode());
                hrPersonnelDetails1.setFinAccountCode(hrPersonnelDetailsTO.getFinAccountCode());
                hrPersonnelDetails1.setPayMode(hrPersonnelDetailsTO.getPayMode());
                hrPersonnelDetails1.setEsiMember(hrPersonnelDetailsTO.getEsiMember());
                hrPersonnelDetails1.setPfMember(hrPersonnelDetailsTO.getPfMember());
                hrPersonnelDetails1.setVpfMember(hrPersonnelDetailsTO.getVpfMember());
                hrPersonnelDetails1.setTrustMember(hrPersonnelDetailsTO.getTrustMember());
                hrPersonnelDetails1.setEmpPermAdd(hrPersonnelDetailsTO.getEmpPermAdd());
                hrPersonnelDetails1.setEmpPermCity(hrPersonnelDetailsTO.getEmpPermCity());
                hrPersonnelDetails1.setEmpPermState(hrPersonnelDetailsTO.getEmpPermState());
                hrPersonnelDetails1.setEmpPermTel(hrPersonnelDetailsTO.getEmpPermTel());
                hrPersonnelDetails1.setEmpContAdd(hrPersonnelDetailsTO.getEmpContAdd());
                hrPersonnelDetails1.setEmpContCity(hrPersonnelDetailsTO.getEmpContCity());
                hrPersonnelDetails1.setEmpContState(hrPersonnelDetailsTO.getEmpContState());
                hrPersonnelDetails1.setEmpPermPin(hrPersonnelDetailsTO.getEmpPermPin());
                hrPersonnelDetails1.setEmpContPin(hrPersonnelDetailsTO.getEmpContPin());
                hrPersonnelDetails1.setEmpContTel(hrPersonnelDetailsTO.getEmpContTel());
                hrPersonnelDetails1.setEmailId(hrPersonnelDetailsTO.getEmailId());
                hrPersonnelDetails1.setPfAccount(hrPersonnelDetailsTO.getPfAccount());
                hrPersonnelDetails1.setBaseBranch(hrPersonnelDetailsTO.getBaseBranch());
                hrPersonnelDetails1.setDesgCode(hrPersonnelDetailsTO.getDesignation());
                hrPersonnelDetails1.setRetirementDate(hrPersonnelDetailsTO.getRetirementDate());
                hrPersonnelDetails1.setUanNumber(hrPersonnelDetailsTO.getUanNumber());
                hrPersonnelDetails1.setModDate(date);

                hrPersonnelDetailsDAO.update(hrPersonnelDetails1);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("updateEmpOtherDetails")) {
                int compCode = hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getCompCode();
                long empCode = hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode();
                String empId = hrPersonnelDetailsTO.getEmpId();
                HrPersonnelDetails hrPersonnelDetails1 = hrPersonnelDetailsDAO.getAllByCompCodeAndEmpCodeOrEMPID(compCode, empCode, empId);
//                hrPersonnelDetails.setTotExpr(hrPersonnelDetails1.getTotExpr());
//                hrPersonnelDetails.setAutoExpr(hrPersonnelDetails1.getAutoExpr());
//                hrPersonnelDetails.setNoticePeriod(hrPersonnelDetails1.getNoticePeriod());
//                hrPersonnelDetails.setProbPeriod(hrPersonnelDetails1.getProbPeriod());
//                hrPersonnelDetails.setConfirmationDate(hrPersonnelDetails1.getConfirmationDate());
//                hrPersonnelDetails.setOtEligibility(hrPersonnelDetails1.getOtEligibility());
//                hrPersonnelDetails.setRepTo(hrPersonnelDetails1.getRepTo());
//                hrPersonnelDetails.setSkillCode(hrPersonnelDetails1.getSkillCode());
//                hrPersonnelDetails.setSpecialCode(hrPersonnelDetails1.getSpecialCode());
//                hrPersonnelDetails.setCareer(hrPersonnelDetails1.getCareer());
//                hrPersonnelDetails.setJobdesc(hrPersonnelDetails1.getJobdesc());

                hrPersonnelDetails1.setFathHusName(hrPersonnelDetailsTO.getFathHusName());
                hrPersonnelDetails1.setHeight(hrPersonnelDetailsTO.getHeight());
                hrPersonnelDetails1.setWeight(hrPersonnelDetailsTO.getWeight());
                hrPersonnelDetails1.setHealth(hrPersonnelDetailsTO.getHealth());
                hrPersonnelDetails1.setBloodGrp(hrPersonnelDetailsTO.getBloodGrp());
                hrPersonnelDetails1.setBirthCity(hrPersonnelDetailsTO.getBirthCity());
                hrPersonnelDetails1.setBirthState(hrPersonnelDetailsTO.getBirthState());
                hrPersonnelDetails1.setWeddingDate(hrPersonnelDetailsTO.getWeddingDate());
                hrPersonnelDetails1.setEmailId(hrPersonnelDetailsTO.getEmailId());
                hrPersonnelDetails1.setCertAdosNo(hrPersonnelDetailsTO.getCertAdosNo());
                hrPersonnelDetails1.setCertAdosDate(hrPersonnelDetailsTO.getCertAdosDate());
                hrPersonnelDetails1.setCertTokNo(hrPersonnelDetailsTO.getCertTokNo());
                hrPersonnelDetails1.setCertRef(hrPersonnelDetailsTO.getCertRef());
                hrPersonnelDetails1.setRelay(hrPersonnelDetailsTO.getRelay());
                hrPersonnelDetails1.setRelayDate(hrPersonnelDetailsTO.getRelayDate());
                hrPersonnelDetails1.setNation(hrPersonnelDetailsTO.getNation());
                hrPersonnelDetails1.setChest(hrPersonnelDetailsTO.getChest());
                hrPersonnelDetails1.setReligion(hrPersonnelDetailsTO.getReligion());
                hrPersonnelDetails1.setMaritalStatus(hrPersonnelDetailsTO.getMaritalStatus());
                hrPersonnelDetails1.setVisaDet(hrPersonnelDetailsTO.getVisaDet());
                hrPersonnelDetails1.setChildren(hrPersonnelDetailsTO.getChildren());
                hrPersonnelDetails1.setFatherHusOcc(hrPersonnelDetailsTO.getFatherHusOcc());
                hrPersonnelDetails1.setFatherHusDesig(hrPersonnelDetailsTO.getFatherHusDesig());
                hrPersonnelDetails1.setFatherHusOrg(hrPersonnelDetailsTO.getFatherHusOrg());
                hrPersonnelDetails1.setFatherHusPhone(hrPersonnelDetailsTO.getFatherHusPhone());

                hrPersonnelDetailsDAO.update(hrPersonnelDetails1);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                int compCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getCompCode();
                long empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
                try {
                    HrPersonnelDependent hrPersonnelDependent = new HrPersonnelDependent();
                    HrPersonnelDependentPK hrPersonnelDependentPK = new HrPersonnelDependentPK();
                    hrPersonnelDependentPK.setCompCode(compCode);
                    hrPersonnelDependentPK.setEmpCode(empCode);
                    hrPersonnelDependent.setHrPersonnelDependentPK(hrPersonnelDependentPK);
                    HrPersonnelDependentDAO hrPersonnelDependentDAO = new HrPersonnelDependentDAO(em);
                    hrPersonnelDependentDAO.delete(hrPersonnelDependent, hrPersonnelDependent.getHrPersonnelDependentPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrPersonnelDependent");
                }

                try {
                    HrPersonnelPreviousCompany hrPersonnelPreviousCompany = new HrPersonnelPreviousCompany();
                    HrPersonnelPreviousCompanyPK hrPersonnelPreviousCompanyPK = new HrPersonnelPreviousCompanyPK();
                    hrPersonnelPreviousCompanyPK.setCompCode(compCode);
                    hrPersonnelPreviousCompanyPK.setEmpCode(empCode);
                    hrPersonnelPreviousCompany.setHrPersonnelPreviousCompanyPK(hrPersonnelPreviousCompanyPK);
                    HrPersonnelPreviousCompanyDAO hrPersonnelPreviousCompanyDAO = new HrPersonnelPreviousCompanyDAO(em);
                    hrPersonnelPreviousCompanyDAO.delete(hrPersonnelPreviousCompany, hrPersonnelPreviousCompany.getHrPersonnelPreviousCompanyPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrPersonnelPreviousCompany");
                }

                try {
                    HrMembershipDetail hrMembershipDetail = new HrMembershipDetail();
                    HrMembershipDetailPK hrMembershipDetailPK = new HrMembershipDetailPK();
                    hrMembershipDetailPK.setCompCode(compCode);
                    hrMembershipDetailPK.setEmpCode(empCode);
                    hrMembershipDetail.setHrMembershipDetailPK(hrMembershipDetailPK);
                    HrMembershipDetailDAO hrMembershipDetailDAO = new HrMembershipDetailDAO(em);
                    hrMembershipDetailDAO.delete(hrMembershipDetail, hrMembershipDetail.getHrMembershipDetailPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrMembershipDetail");
                }
                try {
                    HrPersonnelLang hrPersonnelLang = new HrPersonnelLang();
                    HrPersonnelLangPK hrPersonnelLangPK = new HrPersonnelLangPK();
                    hrPersonnelLangPK.setCompCode(compCode);
                    hrPersonnelLangPK.setEmpCode(empCode);
                    hrPersonnelLang.setHrPersonnelLangPK(hrPersonnelLangPK);
                    HrPersonnelLangDAO hrPersonnelLangDAO = new HrPersonnelLangDAO(em);
                    hrPersonnelLangDAO.delete(hrPersonnelLang, hrPersonnelLang.getHrPersonnelLangPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrPersonnelLang");
                }
                try {
                    HrPersonnelHobby hrPersonnelHobby = new HrPersonnelHobby();
                    HrPersonnelHobbyPK hrPersonnelHobbyPK = new HrPersonnelHobbyPK();
                    hrPersonnelHobbyPK.setCompCode(compCode);
                    hrPersonnelHobbyPK.setEmpCode(empCode);
                    hrPersonnelHobby.setHrPersonnelHobbyPK(hrPersonnelHobbyPK);
                    HrPersonnelHobbyDAO hrPersonnelHobbyDAO = new HrPersonnelHobbyDAO(em);
                    hrPersonnelHobbyDAO.delete(hrPersonnelHobby, hrPersonnelHobby.getHrPersonnelHobbyPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrPersonnelHobby");
                }
                try {
                    HrPersonnelQualification hrPersonnelQualification = new HrPersonnelQualification();
                    HrPersonnelQualificationPK hrPersonnelQualificationPK = new HrPersonnelQualificationPK();
                    hrPersonnelQualificationPK.setCompCode(compCode);
                    hrPersonnelQualificationPK.setEmpCode(empCode);
                    hrPersonnelQualification.setHrPersonnelQualificationPK(hrPersonnelQualificationPK);
                    HrPersonnelQualificationDAO hrPersonnelQualificationDAO = new HrPersonnelQualificationDAO(em);
                    hrPersonnelQualificationDAO.delete(hrPersonnelQualification, hrPersonnelQualification.getHrPersonnelQualificationPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrPersonnelQualification");
                }
                try {
                    HrPersonnelReference hrPersonnelReference = new HrPersonnelReference();
                    HrPersonnelReferencePK hrPersonnelReferencePK = new HrPersonnelReferencePK();
                    hrPersonnelReferencePK.setCompCode(compCode);
                    hrPersonnelReferencePK.setEmpCode(empCode);
                    hrPersonnelReference.setHrPersonnelReferencePK(hrPersonnelReferencePK);
                    HrPersonnelReferenceDAO hrPersonnelReferenceDAO = new HrPersonnelReferenceDAO(em);
                    hrPersonnelReferenceDAO.delete(hrPersonnelReference, hrPersonnelReference.getHrPersonnelReferencePK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrPersonnelReference");
                }
                try {
                    HrPersonnelTransport hrPersonnelTransport = new HrPersonnelTransport();
                    HrPersonnelTransportPK hrPersonnelTransportPK = new HrPersonnelTransportPK();
                    hrPersonnelTransportPK.setCompCode(compCode);
                    hrPersonnelTransportPK.setEmpCode(empCode);
                    hrPersonnelTransport.setHrPersonnelTransportPK(hrPersonnelTransportPK);
                    HrPersonnelTransportDAO hrPersonnelTransportDAO = new HrPersonnelTransportDAO(em);
                    hrPersonnelTransportDAO.delete(hrPersonnelTransport, hrPersonnelTransport.getHrPersonnelTransportPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrPersonnelTransport");
                }

                try {
                    HrApprisalPerformanceDt hrApprisalPerformanceDt = new HrApprisalPerformanceDt();
                    HrApprisalPerformanceDtPK hrApprisalPerformanceDtPK = new HrApprisalPerformanceDtPK();
                    hrApprisalPerformanceDtPK.setCompCode(compCode);
                    hrApprisalPerformanceDtPK.setEmpCode(empCode);
                    hrApprisalPerformanceDt.setHrApprisalPerformanceDtPK(hrApprisalPerformanceDtPK);
                    HrApprisalPerformanceDtDAO hrApprisalPerformanceDtDAO = new HrApprisalPerformanceDtDAO(em);
                    hrApprisalPerformanceDtDAO.delete(hrApprisalPerformanceDt, hrApprisalPerformanceDt.getHrApprisalPerformanceDtPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrApprisalPerformanceDt");
                }

                try {
                    HrTrainingExecution hrTrainingExecution = new HrTrainingExecution();
                    HrTrainingExecutionPK hrTrainingExecutionPK = new HrTrainingExecutionPK();
                    hrTrainingExecutionPK.setCompCode(compCode);
                    hrTrainingExecutionPK.setEmpCode(empCode);
                    hrTrainingExecution.setHrTrainingExecutionPK(hrTrainingExecutionPK);
                    HrTrainingExecutionDAO hrTrainingExecutionDAO = new HrTrainingExecutionDAO(em);
                    hrTrainingExecutionDAO.delete(hrTrainingExecution, hrTrainingExecution.getHrTrainingExecutionPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrTrainingExecution");
                }

                try {
                    HrAttendanceMaintenance hrAttendanceMaintenance = new HrAttendanceMaintenance();
                    HrAttendanceMaintenancePK hrAttendanceMaintenancePK = new HrAttendanceMaintenancePK();
                    hrAttendanceMaintenancePK.setCompCode(compCode);
                    hrAttendanceMaintenancePK.setEmpCode(empCode);
                    hrAttendanceMaintenance.setHrAttendanceMaintenancePK(hrAttendanceMaintenancePK);
                    HrAttendanceMaintenanceDAO hrAttendanceMaintenanceDAO = new HrAttendanceMaintenanceDAO(em);
                    hrAttendanceMaintenanceDAO.delete(hrAttendanceMaintenance, hrAttendanceMaintenance.getHrAttendanceMaintenancePK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrAttendanceMaintenance");
                }

                try {
                    HrEmpAdvanceDt hrEmpAdvanceDt = new HrEmpAdvanceDt();
                    HrEmpAdvanceDtPK hrEmpAdvanceDtPK = new HrEmpAdvanceDtPK();
                    hrEmpAdvanceDtPK.setCompCode(compCode);
                    hrEmpAdvanceDtPK.setEmpCode(empCode);
                    hrEmpAdvanceDt.setHrEmpAdvanceDtPK(hrEmpAdvanceDtPK);
                    HrEmpAdvanceDtDAO hrEmpAdvanceDtDAO = new HrEmpAdvanceDtDAO(em);
                    hrEmpAdvanceDtDAO.delete(hrEmpAdvanceDt, hrEmpAdvanceDt.getHrEmpAdvanceDtPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrEmpAdvanceDt");
                }



                try {
                    HrEmpAdvanceHd hrEmpAdvanceHd = new HrEmpAdvanceHd();
                    HrEmpAdvanceHdPK hrEmpAdvanceHdPK = new HrEmpAdvanceHdPK();
                    hrEmpAdvanceHdPK.setCompCode(compCode);
                    hrEmpAdvanceHdPK.setEmpCode(empCode);
                    hrEmpAdvanceHd.setHrEmpAdvanceHdPK(hrEmpAdvanceHdPK);
                    HrEmpAdvanceHdDAO hrEmpAdvanceHdDAO = new HrEmpAdvanceHdDAO(em);
                    hrEmpAdvanceHdDAO.delete(hrEmpAdvanceHd, hrEmpAdvanceHd.getHrEmpAdvanceHdPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrEmpAdvanceHd");
                }

                try {
                    HrLeavePosting hrLeavePosting = new HrLeavePosting();
                    HrLeavePostingPK hrLeavePostingPK = new HrLeavePostingPK();
                    hrLeavePostingPK.setCompCode(compCode);
                    hrLeavePostingPK.setEmpCode(empCode);
                    hrLeavePosting.setHrLeavePostingPK(hrLeavePostingPK);
                    HrLeavePostingDAO hrLeavePostingDAO = new HrLeavePostingDAO(em);
                    hrLeavePostingDAO.delete(hrLeavePosting, hrLeavePosting.getHrLeavePostingPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrLeavePosting");
                }



                try {
                    HrApprisalDetails hrApprisalDetails = new HrApprisalDetails();
                    HrApprisalDetailsPK hrApprisalDetailsPK = new HrApprisalDetailsPK();
                    hrApprisalDetailsPK.setCompCode(compCode);
                    hrApprisalDetailsPK.setEmpCode(empCode);
                    hrApprisalDetails.setHrApprisalDetailsPK(hrApprisalDetailsPK);
                    HrApprisalDetailsDAO hrApprisalDetailsDAO = new HrApprisalDetailsDAO(em);
                    hrApprisalDetailsDAO.delete(hrApprisalDetails, hrApprisalDetails.getHrApprisalDetailsPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrApprisalDetails");
                }

                try {
                   
                    
                    HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
                    HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
                    hrSalaryAllocationPK.setCompCode(compCode);
                    hrSalaryAllocationPK.setEmpCode(empCode);
                    hrSalaryAllocation.setHrSalaryAllocationPK(hrSalaryAllocationPK);
                    HrSalaryAllocationDAO hrSalaryAllocationDAO = new HrSalaryAllocationDAO(em);
                    hrSalaryAllocationDAO.delete(hrSalaryAllocation, hrSalaryAllocation.getHrSalaryAllocationPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrSalaryAllocation");
                }

                try {
                    HrLeaveRegister hrLeaveRegister = new HrLeaveRegister();
                    HrLeaveRegisterPK hrLeaveRegisterPK = new HrLeaveRegisterPK();
                    hrLeaveRegisterPK.setCompCode(compCode);
                    hrLeaveRegisterPK.setEmpCode(empCode);
                    hrLeaveRegister.setHrLeaveRegisterPK(hrLeaveRegisterPK);
                    HrLeaveRegisterDAO hrLeaveRegisterDAO = new HrLeaveRegisterDAO(em);
                    hrLeaveRegisterDAO.delete(hrLeaveRegister, hrLeaveRegister.getHrLeaveRegisterPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrLeaveRegister");
                }

                try {
                    HrShiftMap hrShiftMap = new HrShiftMap();
                    HrShiftMapPK hrShiftMapPK = new HrShiftMapPK();
                    hrShiftMapPK.setCompCode(compCode);
                    hrShiftMapPK.setEmpCode(empCode);
                    hrShiftMap.setHrShiftMapPK(hrShiftMapPK);
                    HrShiftMapDAO hrShiftMapDAO = new HrShiftMapDAO(em);
                    hrShiftMapDAO.delete(hrShiftMap, hrShiftMap.getHrShiftMapPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrShiftMap");
                }

                try {
                    HrAttendanceDetails hrAttendanceDetails = new HrAttendanceDetails();
                    HrAttendanceDetailsPK hrAttendanceDetailsPK = new HrAttendanceDetailsPK();
                    hrAttendanceDetailsPK.setCompCode(compCode);
                    hrAttendanceDetailsPK.setEmpCode(empCode);
                    hrAttendanceDetails.setHrAttendanceDetailsPK(hrAttendanceDetailsPK);
                    HrAttendanceDetailsDAO hrAttendanceDetailsDAO = new HrAttendanceDetailsDAO(em);
                    hrAttendanceDetailsDAO.delete(hrAttendanceDetails, hrAttendanceDetails.getHrAttendanceDetailsPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrAttendanceDetails");
                }


                try {
                    HrSalaryProcessing hrSalaryProcessing = new HrSalaryProcessing();
                    HrSalaryProcessingPK hrSalaryProcessingPK = new HrSalaryProcessingPK();
                    hrSalaryProcessingPK.setCompCode(compCode);
                    hrSalaryProcessingPK.setEmpCode(empCode);
                    hrSalaryProcessing.setHrSalaryProcessingPK(hrSalaryProcessingPK);
                    HrSalaryProcessingDAO hrSalaryProcessingDAO = new HrSalaryProcessingDAO(em);
                    hrSalaryProcessingDAO.delete(hrSalaryProcessing, hrSalaryProcessing.getHrSalaryProcessingPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrSalaryProcessing");
                }



                try {
                    HrPayrollCloseTrace hrPayrollCloseTrace = new HrPayrollCloseTrace();
                    HrPayrollCloseTracePK hrPayrollCloseTracePK = new HrPayrollCloseTracePK();
                    hrPayrollCloseTracePK.setCompCode(compCode);
                    hrPayrollCloseTracePK.setEmpCode(empCode);
                    hrPayrollCloseTrace.setHrPayrollCloseTracePK(hrPayrollCloseTracePK);
                    HrPayrollCloseTraceDAO hrPayrollCloseTraceDAO = new HrPayrollCloseTraceDAO(em);
                    hrPayrollCloseTraceDAO.delete(hrPayrollCloseTrace, hrPayrollCloseTrace.getHrPayrollCloseTracePK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrPayrollCloseTrace");
                }


                try {
                    HrTrainingPlan hrTrainingPlan = new HrTrainingPlan();
                    HrTrainingPlanPK hrTrainingPlanPK = new HrTrainingPlanPK();
                    hrTrainingPlanPK.setCompCode(compCode);
                    hrTrainingPlanPK.setEmpCode(empCode);
                    hrTrainingPlan.setHrTrainingPlanPK(hrTrainingPlanPK);
                    HrTrainingPlanDAO hrTrainingPlanDAO = new HrTrainingPlanDAO(em);
                    hrTrainingPlanDAO.delete(hrTrainingPlan, hrTrainingPlan.getHrTrainingPlanPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrTrainingPlan");
                }

                try {
                    HrEmpLoanDt hrEmpLoanDt = new HrEmpLoanDt();
                    HrEmpLoanDtPK hrEmpLoanDtPK = new HrEmpLoanDtPK();
                    hrEmpLoanDtPK.setCompCode(compCode);
                    hrEmpLoanDtPK.setEmpCode(empCode);
                    hrEmpLoanDt.setHrEmpLoanDtPK(hrEmpLoanDtPK);
                    HrEmpLoanDtDAO hrEmpLoanDtDAO = new HrEmpLoanDtDAO(em);
                    hrEmpLoanDtDAO.delete(hrEmpLoanDt, hrEmpLoanDt.getHrEmpLoanDtPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrEmpLoanDt");
                }

                try {
                    HrEmpLoanHd hrEmpLoanHd = new HrEmpLoanHd();
                    HrEmpLoanHdPK hrEmpLoanHdPK = new HrEmpLoanHdPK();
                    hrEmpLoanHdPK.setCompCode(compCode);
                    hrEmpLoanHdPK.setEmpCode(empCode);
                    hrEmpLoanHd.setHrEmpLoanHdPK(hrEmpLoanHdPK);
                    HrEmpLoanHdDAO hrEmpLoanHdDAO = new HrEmpLoanHdDAO(em);
                    hrEmpLoanHdDAO.delete(hrEmpLoanHd, hrEmpLoanHd.getHrEmpLoanHdPK());
                } catch (Exception e) {
                    System.out.println("Exception occured-HrEmpLoanHd");
                }
                try {
                    hrPersonnelDetailsDAO.delete(hrPersonnelDetails, hrPersonnelDetails.getHrPersonnelDetailsPK());
                    return "Data has been successfully deleted.";
                } catch (Exception e) {
                    return "Data has not been successfully deleted.";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";

    }

    @Override
    public List<HrPersonnelHobbyTO> getHobbyData(long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelHobbyDAO hrPersonnelHobbyDAO = new HrPersonnelHobbyDAO(em);
        List<HrPersonnelHobbyTO> hrPersonnelHobbyTOs = new ArrayList<HrPersonnelHobbyTO>();
        try {
            List<HrPersonnelHobby> hrPersonnelHobbyList = hrPersonnelHobbyDAO.getHobbyData(empCode);
            for (HrPersonnelHobby hrPersonnelHobby : hrPersonnelHobbyList) {
                hrPersonnelHobbyTOs.add(ObjectAdaptorPersonnel.adaptTOHrPersonnelHobbyTO(hrPersonnelHobby));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getHobbyTableData()", e);
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
            logger.error("Exception occured while executing method getHobbyTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getHobbyTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelHobbyTOs;
    }

    @Override
    public List<HrPersonnelLangTO> getLanguageData(long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelLangDAO hrPersonnelLangDAO = new HrPersonnelLangDAO(em);
        List<HrPersonnelLangTO> hrPersonnelLangTOs = new ArrayList<HrPersonnelLangTO>();
        try {
            List<HrPersonnelLang> hrPersonnelLangList = hrPersonnelLangDAO.getLangData(empCode);
            for (HrPersonnelLang hrPersonnelLang : hrPersonnelLangList) {
                hrPersonnelLangTOs.add(ObjectAdaptorPersonnel.adaptTOHrPersonnelLangTO(hrPersonnelLang));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getLangTableData()", e);
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
            logger.error("Exception occured while executing method getLangTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getLangTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelLangTOs;
    }

    @Override
    public String saveHrPersonnelHobby(HrPersonnelHobbyTO hrPersonnelHobbyTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelHobbyTO);
        try {
            HrPersonnelHobbyDAO hrPersonnelHobbyDAO = new HrPersonnelHobbyDAO(em);
            HrPersonnelHobby hrPersonnelHobby = ObjectAdaptorPersonnel.adaptTOHrPersonnelHobbyEntity(hrPersonnelHobbyTO);
            if (mode.equalsIgnoreCase("save")) {
                hrPersonnelHobbyDAO.save(hrPersonnelHobby);
                return "Hobby has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrPersonnelHobbyDAO.update(hrPersonnelHobby);
                return "Hobby has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrPersonnelHobbyDAO.delete(hrPersonnelHobby, hrPersonnelHobby.getHrPersonnelHobbyPK());
                return "Hobby has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public String saveHrPersonnelLang(HrPersonnelLangTO hrPersonnelLangTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrPersonnelLangTO);
        try {
            HrPersonnelLangDAO hrPersonnelLangDAO = new HrPersonnelLangDAO(em);
            HrPersonnelLang hrPersonnelLang = ObjectAdaptorPersonnel.adaptTOHrPersonnelLangEntity(hrPersonnelLangTO);
            if (mode.equalsIgnoreCase("save")) {
                hrPersonnelLangDAO.save(hrPersonnelLang);
                return "Language has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrPersonnelLangDAO.update(hrPersonnelLang);
                return "Language has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrPersonnelLangDAO.delete(hrPersonnelLang, hrPersonnelLang.getHrPersonnelLangPK());
                return "Language has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
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
            logger.error("Exception occured while executing method getDependentTableData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDependentTableData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public List getAdvanceTableDataForAuthorization(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrEmpAdvanceHdDAO hrEmpAdvanceHdDAO = new HrEmpAdvanceHdDAO(em);
        List list = null;
        try {
            list = hrEmpAdvanceHdDAO.getAdvanceTableDataForAuthorization(compCode);
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
        return list;
    }

    @Override
    public List getLoanTableDataForAuthorization(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrEmpLoanHdDAO hrEmpLoanHdDAO = new HrEmpLoanHdDAO(em);
        List list = null;
        try {
            list = hrEmpLoanHdDAO.getLoanTableDataForAuthorization(compCode);
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
        return list;
    }

    @Override
    public String saveEmpAdvanceDetail(HrEmpAdvanceHdTO hrEmpAdvanceHdTO, List<HrEmpAdvanceDtTO> hrEmpAdvanceDtTOList, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrEmpAdvanceHdTO);
        Validator.isNull(hrEmpAdvanceDtTOList);
        try {
            HrEmpAdvanceHdDAO hrEmpAdvanceHdDAO = new HrEmpAdvanceHdDAO(em);
            if (mode.equalsIgnoreCase("save")) {
                HrEmpAdvanceHd hrEmpAdvanceHd = ObjectAdaptorPersonnel.adaptToHrEmpAdvanceHdEntity(hrEmpAdvanceHdTO);
                hrEmpAdvanceHdDAO.save(hrEmpAdvanceHd);
                HrEmpAdvanceDtDAO hrEmpAdvanceDtDAO = new HrEmpAdvanceDtDAO(em);
                for (HrEmpAdvanceDtTO advanceDtTO : hrEmpAdvanceDtTOList) {
                    HrEmpAdvanceDt hrEmpAdvanceDt = ObjectAdaptorPersonnel.adaptToHrEmpAdvanceDtEntity(advanceDtTO);
                    hrEmpAdvanceDtDAO.save(hrEmpAdvanceDt);
                }
                return "Data has been successfully saved.";
            }
            if (mode.equalsIgnoreCase("delete")) {
                HrEmpAdvanceDtDAO hrEmpAdvanceDtDAO = new HrEmpAdvanceDtDAO(em);
                for (HrEmpAdvanceDtTO advanceDtTO : hrEmpAdvanceDtTOList) {
                    HrEmpAdvanceDt hrEmpAdvanceDt = ObjectAdaptorPersonnel.adaptToHrEmpAdvanceDtEntity(advanceDtTO);
                    hrEmpAdvanceDtDAO.delete(hrEmpAdvanceDt, hrEmpAdvanceDt.getHrEmpAdvanceDtPK());
                }
                int compCode = hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getCompCode();
                long empAdvNo = hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getEmpAdvNo();
                HrEmpAdvanceHd hrEmpAdvanceHd = hrEmpAdvanceHdDAO.findEntity(compCode, empAdvNo);
                if (hrEmpAdvanceHd.getHrEmpAdvanceHdPK() != null) {
                    hrEmpAdvanceHdDAO.delete(hrEmpAdvanceHd, hrEmpAdvanceHd.getHrEmpAdvanceHdPK());
                }
                return "Data has been successfully deleted.";
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
        return "";
    }

    @Override
    public String authorizeEmpAdvanceDetail(HrEmpAdvanceHdTO hrEmpAdvanceHdTO) throws ApplicationException {
        Validator.isNull(hrEmpAdvanceHdTO);
        try {
            HrEmpAdvanceHdDAO hrEmpAdvanceHdDAO = new HrEmpAdvanceHdDAO(em);
            int compCode = hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getCompCode();
            long empAdvNo = hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getEmpAdvNo();
            HrEmpAdvanceHd hrEmpAdvanceHd = hrEmpAdvanceHdDAO.findEntity(compCode, empAdvNo);
            if (hrEmpAdvanceHd.getEnteredBy().equalsIgnoreCase(hrEmpAdvanceHdTO.getAuthBy())) {
                return "You Cannot Authorize Your Own Entry !!";
            }
            hrEmpAdvanceHd.setAuthBy(hrEmpAdvanceHdTO.getAuthBy());
            hrEmpAdvanceHdDAO.update(hrEmpAdvanceHd);
            return "Authorization Successful !!";
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
    }

    @Override
    public String authorizeEmpLoanDetail(HrEmpLoanHdTO hrEmpLoanHdTO) throws ApplicationException {
        Validator.isNull(hrEmpLoanHdTO);
        try {
            HrEmpLoanHdDAO hrEmpLoanHdDAO = new HrEmpLoanHdDAO(em);
            int compCode = hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getCompCode();
            long empLoanNo = hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getEmpLoanNo();
            HrEmpLoanHd hrEmpLoanHd = hrEmpLoanHdDAO.findEntity(compCode, empLoanNo);
            if (hrEmpLoanHd.getEnteredBy().equalsIgnoreCase(hrEmpLoanHdTO.getAuthBy())) {
                return "You Cannot Authorize Your Own Entry !!";
            }
            hrEmpLoanHd.setAuthBy(hrEmpLoanHdTO.getAuthBy());
            hrEmpLoanHdDAO.update(hrEmpLoanHd);
            return "Authorization Successful !!";
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
    }

    @Override
    public String saveEmpLoanDetail(HrEmpLoanHdTO hrEmpLoanHdTO, List<HrEmpLoanDtTO> hrEmpLoanDtTOList, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrEmpLoanHdTO);
        Validator.isNull(hrEmpLoanDtTOList);
        try {
            HrEmpLoanHdDAO hrEmpLoanHdDAO = new HrEmpLoanHdDAO(em);
            if (mode.equalsIgnoreCase("save")) {
                HrEmpLoanHd hrEmpLoanHd = ObjectAdaptorPersonnel.adaptToHrEmpLoanHdEntity(hrEmpLoanHdTO);
                hrEmpLoanHdDAO.save(hrEmpLoanHd);
                HrEmpLoanDtDAO hrEmpLoanDtDAO = new HrEmpLoanDtDAO(em);
                for (HrEmpLoanDtTO advanceDtTO : hrEmpLoanDtTOList) {
                    HrEmpLoanDt hrEmpLoanDt = ObjectAdaptorPersonnel.adaptToHrEmpLoanDtEntity(advanceDtTO);
                    hrEmpLoanDtDAO.save(hrEmpLoanDt);
                }
                return "Data has been successfully saved.";
            }
            if (mode.equalsIgnoreCase("delete")) {
                HrEmpLoanDtDAO hrEmpLoanDtDAO = new HrEmpLoanDtDAO(em);
                for (HrEmpLoanDtTO advanceDtTO : hrEmpLoanDtTOList) {
                    HrEmpLoanDt hrEmpLoanDt = ObjectAdaptorPersonnel.adaptToHrEmpLoanDtEntity(advanceDtTO);
                    hrEmpLoanDtDAO.delete(hrEmpLoanDt, hrEmpLoanDt.getHrEmpLoanDtPK());
                }
                int compCode = hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getCompCode();
                long empLoanNo = hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getEmpLoanNo();
                HrEmpLoanHd hrEmpLoanHd = hrEmpLoanHdDAO.findEntity(compCode, empLoanNo);
                if (hrEmpLoanHd.getHrEmpLoanHdPK() != null) {
                    hrEmpLoanHdDAO.delete(hrEmpLoanHd, hrEmpLoanHd.getHrEmpLoanHdPK());
                }
                return "Data has been successfully deleted.";
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
        return "";
    }
}
