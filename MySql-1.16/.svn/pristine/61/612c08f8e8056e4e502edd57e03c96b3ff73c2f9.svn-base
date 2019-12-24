package com.hrms.facade;

import com.hrms.adaptor.ObjectAdaptorDefinitions;
import com.hrms.adaptor.PayrollObjectAdaptor;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstBusTO;
import com.hrms.common.to.HrMstCompLoanTO;
import com.hrms.common.to.HrMstDeptSubdeptTO;
import com.hrms.common.to.HrMstRouteTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrMstZoneLocationTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrMstBusDAO;
import com.hrms.dao.HrMstCompLoanDAO;
import com.hrms.dao.HrMstDeptSubdeptDAO;
import com.hrms.dao.HrMstRouteDAO;
import com.hrms.dao.HrMstShiftDAO;
import com.hrms.dao.HrMstStructDAO;
import com.hrms.dao.HrMstZoneLocationDAO;
import com.hrms.dao.PayrollCalendarDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstBus;
import com.hrms.entity.hr.HrMstDeptSubdept;
import com.hrms.entity.hr.HrMstRoute;
import com.hrms.entity.hr.HrMstStruct;
import com.hrms.entity.hr.HrMstZoneLocation;
import com.hrms.entity.payroll.HrMstShift;
import com.hrms.entity.payroll.HrPayrollCalendar;
import com.hrms.entity.personnel.HrMstCompLoan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

@Stateless(mappedName = "DefinitionFacadeRemote")
@Remote({DefinitionFacadeRemote.class})
public class DefinitionFacade implements DefinitionFacadeRemote {

    private static final Logger logger = Logger.getLogger(DefinitionFacade.class);
    @PersistenceContext
    private EntityManager em;

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
    public List<HrMstBusTO> getAllByBusNo(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstBusDAO hrMstBusDAO = new HrMstBusDAO(em);
        List<HrMstBusTO> hrMstBusTOs = new ArrayList<HrMstBusTO>();
        try {
            List<HrMstBus> hrMstBusList = hrMstBusDAO.getAllByBusNo(compCode);
            for (HrMstBus hrMstBus : hrMstBusList) {
                hrMstBusTOs.add(ObjectAdaptorDefinitions.adaptToHrMstBusTO(hrMstBus));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAllByBusNo()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAllByBusNo()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAllByBusNo is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrMstBusTOs;
    }

    @Override
    public String saveBusMasterDetail(HrMstBusTO hrMstBusTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrMstBusTO);
        try {
            HrMstBusDAO hrMstBusDAO = new HrMstBusDAO(em);
            HrMstBus hrMstBus = ObjectAdaptorDefinitions.adaptToHrMstBusEntity(hrMstBusTO);
            if (mode.equalsIgnoreCase("save")) {
                hrMstBusDAO.save(hrMstBus);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrMstBusDAO.update(hrMstBus);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrMstBusDAO.delete(hrMstBus, hrMstBus.getHrMstBusPK());
                return "Data has been successfully deleted.";
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveBusMasterDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveBusMasterDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public List<HrMstRouteTO> getAllByRouteNo(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstRouteDAO hrMstBusDAO = new HrMstRouteDAO(em);
        List<HrMstRouteTO> hrMstRouteTOs = new ArrayList<HrMstRouteTO>();
        try {
            List<HrMstRoute> hrMstRouteList = hrMstBusDAO.getAllByRouteNo(compCode);
            for (HrMstRoute hrMstRoute : hrMstRouteList) {
                hrMstRouteTOs.add(ObjectAdaptorDefinitions.adaptToHrMstRouteTO(hrMstRoute));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAllByRouteNo()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAllByRouteNo()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAllByRouteNo is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrMstRouteTOs;
    }

    @Override
    public String saveRouteDetail(HrMstRouteTO hrMstRouteTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrMstRouteTO);
        try {
            HrMstRouteDAO hrMstRouteDAO = new HrMstRouteDAO(em);
            HrMstRoute hrMstRoute = ObjectAdaptorDefinitions.adaptToHrMstRouteEntity(hrMstRouteTO);
            if (mode.equalsIgnoreCase("save")) {
                hrMstRouteDAO.save(hrMstRoute);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrMstRouteDAO.update(hrMstRoute);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrMstRouteDAO.delete(hrMstRoute, hrMstRoute.getHrMstRoutePK());
                return "Data has been successfully deleted.";
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveRouteDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveRouteDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public List<HrMstStructTO> getIntialData(int compCode, String structCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();

        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, structCode);
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(com.hrms.adaptor.ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
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
    public List<HrMstDeptSubdeptTO> getAllHrMstDeptSubdeptByDeptcodeSubdeptcode(int compCode, String deptCode, String subDeptCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstDeptSubdeptDAO deptSubdeptDAO = new HrMstDeptSubdeptDAO(em);
        List<HrMstDeptSubdeptTO> deptSubdeptTOs = new ArrayList<HrMstDeptSubdeptTO>();

        try {
            List<HrMstDeptSubdept> deptSubdeptList = deptSubdeptDAO.getAllByDeptcodeSubdeptcode(compCode, deptCode, subDeptCode);
            for (HrMstDeptSubdept hrMstDeptSubdept : deptSubdeptList) {
                deptSubdeptTOs.add(ObjectAdaptorDefinitions.adaptToHrMstDeptSubdeptTO(hrMstDeptSubdept));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAllHrMstDeptSubdeptByDeptcodeSubdeptcode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAllHrMstDeptSubdeptByDeptcodeSubdeptcode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAllHrMstDeptSubdeptByDeptcodeSubdeptcode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return deptSubdeptTOs;
    }

    @Override
    public List<HrMstDeptSubdeptTO> getAllHrMstDeptSubdeptByCompcode(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstDeptSubdeptDAO deptSubdeptDAO = new HrMstDeptSubdeptDAO(em);
        List<HrMstDeptSubdeptTO> deptSubdeptTOs = new ArrayList<HrMstDeptSubdeptTO>();

        try {
            List<HrMstDeptSubdept> deptSubdeptList = deptSubdeptDAO.getAllByCompcode(compCode);
            for (HrMstDeptSubdept hrMstDeptSubdept : deptSubdeptList) {
                deptSubdeptTOs.add(ObjectAdaptorDefinitions.adaptToHrMstDeptSubdeptTO(hrMstDeptSubdept));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAllHrMstDeptSubdeptByCompcode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAllHrMstDeptSubdeptByCompcode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAllHrMstDeptSubdeptByCompcode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return deptSubdeptTOs;
    }

    @Override
    public String saveLoanBudgetMasterDetail(HrMstCompLoanTO hrMstCompLoanTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrMstCompLoanTO);
        try {
            HrMstCompLoanDAO hrMstBusDAO = new HrMstCompLoanDAO(em);
            HrMstCompLoan hrMstCompLoan = ObjectAdaptorDefinitions.adaptToHrMstCompLoanEntity(hrMstCompLoanTO);
            if (mode.equalsIgnoreCase("save")) {
                hrMstBusDAO.save(hrMstCompLoan);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrMstBusDAO.update(hrMstCompLoan);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrMstBusDAO.delete(hrMstCompLoan, hrMstCompLoan.getHrMstCompLoanPK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveLoanBudgetMasterDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method saveLoanBudgetMasterDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveLoanBudgetMasterDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public List<HrMstCompLoanTO> getHrMstCompLoanData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstCompLoanDAO hrMstCompLoanDAO = new HrMstCompLoanDAO(em);
        List<HrMstCompLoanTO> hrMstCompLoanTOs = new ArrayList<HrMstCompLoanTO>();
        try {
            List<HrMstCompLoan> hrMstCompLoanList = hrMstCompLoanDAO.getAllByCompcode(compCode);
            for (HrMstCompLoan hrMstCompLoan : hrMstCompLoanList) {
                hrMstCompLoanTOs.add(ObjectAdaptorDefinitions.adaptToHrMstCompLoanTO(hrMstCompLoan));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getHrMstCompLoanData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrMstCompLoanData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getHrMstCompLoanData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }

        return hrMstCompLoanTOs;
    }

    @Override
    public List<HrMstShiftTO> getShiftMasterByCompCode(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstShiftDAO hrMstShiftDAO = new HrMstShiftDAO(em);
        List<HrMstShiftTO> hrMstShiftTOs = new ArrayList<HrMstShiftTO>();
        try {
            List<HrMstShift> hrMstShiftList = hrMstShiftDAO.getShiftMasterByCompCode(compCode);
            for (HrMstShift hrMstShift : hrMstShiftList) {
                hrMstShiftTOs.add(ObjectAdaptorDefinitions.adaptToHrMstShiftEntityTO(hrMstShift));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getShiftMasterByCompCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getShiftMasterByCompCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getShiftMasterByCompCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrMstShiftTOs;
    }

    @Override
    public String saveShiftMasterDetail(HrMstShiftTO hrMstShiftTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrMstShiftTO);
        try {
            HrMstShiftDAO hrMstShiftDAO = new HrMstShiftDAO(em);
            HrMstShift hrMstShift = ObjectAdaptorDefinitions.adaptToHrMstShiftEntity(hrMstShiftTO);
            if (mode.equalsIgnoreCase("save")) {
                hrMstShiftDAO.save(hrMstShift);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrMstShiftDAO.update(hrMstShift);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrMstShiftDAO.delete(hrMstShift, hrMstShift.getHrMstShiftPK());
                return "Data has been successfully deleted.";
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveShiftMasterDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveShiftMasterDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public String saveDepartmentSubDepartmentMasterDetail(HrMstDeptSubdeptTO hrMstDeptSubdeptTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrMstDeptSubdeptTO);
        try {
            HrMstDeptSubdeptDAO hrMstDeptSubdeptDAO = new HrMstDeptSubdeptDAO(em);
            HrMstDeptSubdept hrMstDeptSubdept = ObjectAdaptorDefinitions.adaptToHrMstDeptSubdeptEntity(hrMstDeptSubdeptTO);
            if (mode.equalsIgnoreCase("save")) {
                hrMstDeptSubdeptDAO.save(hrMstDeptSubdept);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrMstDeptSubdeptDAO.update(hrMstDeptSubdept);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrMstDeptSubdeptDAO.delete(hrMstDeptSubdept, hrMstDeptSubdept.getHrMstDeptSubdeptPK());
                return "Data has been successfully deleted.";
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveDepartmentSubDepartmentMasterDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveDepartmentSubDepartmentMasterDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }

    @Override
    public List getSubDepartment(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstDeptSubdeptDAO deptSubdeptDAO = new HrMstDeptSubdeptDAO(em);
        List list = null;
        try {
            list = deptSubdeptDAO.getSubDepartment(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSubDepartment()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSubDepartment()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSubDepartment is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return list;
    }

    @Override
    public List<HrMstDeptSubdeptTO> getAllHrMstDeptSubdeptByDeptcode(int compCode, String deptCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstDeptSubdeptDAO deptSubdeptDAO = new HrMstDeptSubdeptDAO(em);
        List<HrMstDeptSubdeptTO> deptTOs = new ArrayList<HrMstDeptSubdeptTO>();

        try {
            List<HrMstDeptSubdept> deptSubdeptList = deptSubdeptDAO.getAllByDeptcode(compCode, deptCode);
            for (HrMstDeptSubdept hrMstDeptSubdept : deptSubdeptList) {
                deptTOs.add(ObjectAdaptorDefinitions.adaptToHrMstDeptSubdeptTO(hrMstDeptSubdept));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAllHrMstDeptSubdeptByDeptcode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAllHrMstDeptSubdeptByDeptcode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAllHrMstDeptSubdeptByDeptcode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return deptTOs;
    }

    @Override
    public List getSubDepartmentsAssigned(int compCode, String deptCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstDeptSubdeptDAO deptSubdeptDAO = new HrMstDeptSubdeptDAO(em);
        List list = null;
        try {
            list = deptSubdeptDAO.getSubDepartmentsAssigned(compCode, deptCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSubDepartmentsAssigned()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSubDepartmentsAssigned()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSubDepartmentsAssigned is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return list;
    }

    @Override
    public List getAvailableLocations(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstZoneLocationDAO hrMstZoneLocationDAO = new HrMstZoneLocationDAO(em);
        List list = null;
        try {
            list = hrMstZoneLocationDAO.getAvailableLocations(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAvailableLocations()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAvailableLocations()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAvailableLocations is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return list;
    }

    @Override
    public List getAssignedLocations(int compCode, String zoneValue) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstZoneLocationDAO deptSubdeptDAO = new HrMstZoneLocationDAO(em);
        List list = null;
        try {
            list = deptSubdeptDAO.getAssignedLocations(compCode, zoneValue);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAssignedLocations()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAssignedLocations()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAssignedLocations is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return list;
    }

    @Override
    public String saveZoneLocationMasterDetail(HrMstZoneLocationTO hrMstZoneLocationTO, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrMstZoneLocationTO);
        try {
            HrMstZoneLocationDAO hrMstZoneLocationDAO = new HrMstZoneLocationDAO(em);
            HrMstZoneLocation hrMstZoneLocation = ObjectAdaptorDefinitions.adaptToHrMstZoneLocationEntity(hrMstZoneLocationTO);
            if (mode.equalsIgnoreCase("save")) {
                hrMstZoneLocationDAO.save(hrMstZoneLocation);
                return "Data has been successfully saved.";
            } else if (mode.equalsIgnoreCase("update")) {
                hrMstZoneLocationDAO.update(hrMstZoneLocation);
                return "Data has been successfully updated.";
            } else if (mode.equalsIgnoreCase("delete")) {
                hrMstZoneLocationDAO.delete(hrMstZoneLocation, hrMstZoneLocation.getHrMstZoneLocationPK());
                return "Data has been successfully deleted.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveZoneLocationMasterDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveZoneLocationMasterDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveZoneLocationMasterDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "";
    }
}
