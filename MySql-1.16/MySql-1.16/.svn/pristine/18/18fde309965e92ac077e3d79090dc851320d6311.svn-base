/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.delegate;

import com.hrms.common.complexTO.TaxInvestmentCategoryTO;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.EmpSalaryAllocationGridTO;
import com.hrms.common.to.GetSalaryAllocationTO;
import com.hrms.common.to.HrAttendanceMaintenanceTO;
import com.hrms.common.to.HrHolidayMasterTO;
import com.hrms.common.to.HrHolidayMasterTOPK;
import com.hrms.common.to.HrLeavePostingTO;
import com.hrms.common.to.HrLeaveRegisterTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrSalaryStructurePKTO;
import com.hrms.common.to.HrSalaryStructureTO;
import com.hrms.common.to.HrShiftMapTO;
import com.hrms.common.to.HrSlabMasterTO;
import com.hrms.common.to.HrTaxInvestmentCategoryTO;
import com.hrms.common.to.HrTaxSlabMasterTO;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.to.SalaryAllocationTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.entity.payroll.HrPayrollCalendar;
import com.hrms.facade.payroll.PayrollMasterFacadeRemote;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmpSalaryAllocationGrid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sudhir Bisht
 */
public class PayrollMasterManagementDelegate {

    private final String jndiHomeName = "PayrollMasterFacade";
    private PayrollMasterFacadeRemote beanRemote = null;

    /**
     * No argument constructor
     *
     * @throws ServiceLocatorException
     */
    public PayrollMasterManagementDelegate() throws ServiceLocatorException {
        try {
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (PayrollMasterFacadeRemote) lookup;
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<HrMstStructTO> getInitialData(int compCode, String structCode) throws WebException {
        List<HrMstStructTO> HrMstStructTOs = new ArrayList<HrMstStructTO>();
        try {
            HrMstStructTOs = beanRemote.getIntialData(compCode, structCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return HrMstStructTOs;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<PayrollCalendarTO> getFinancialYear(int compCode) throws WebException {
        List<PayrollCalendarTO> payrollCalendarTOs = new ArrayList<PayrollCalendarTO>();
        try {
            payrollCalendarTOs = beanRemote.getFinYear(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return payrollCalendarTOs;
    }

    /**
     *
     * @param leavmstObj
     * @return
     * @throws WebException
     */
    public String saveLeaveDetail(LeaveMasterTO leavmstObj, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveUpdateDeleteLeaveDetail(leavmstObj, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());

        }
        return result;
    }

    /**
     *
     * @param payrollCalendarTO
     * @param mod
     * @return
     * @throws WebException
     */
    public String saveDeletePayrollCalendar(PayrollCalendarTO payrollCalendarTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.payrollCalendarSaveDelete(payrollCalendarTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
//    public List<PayrollCalendarTO> displayPayRollCalendarGrid(int compCode) throws WebException {
//        List<PayrollCalendarTO> payrollCalendarTO;
//        try {
//            payrollCalendarTO = beanRemote.displayPayrollCalendarGrid(compCode);
//        } catch (ApplicationException e) {
//            throw new WebException(e.getExceptionCode().getMessageKey());
//        }
//        return payrollCalendarTO;
//
//    }
    /**
     *
     * @param closeYearButtonTO
     * @return
     * @throws WebException
     */
    public String closePayrollCalendarButton(HrPayrollCalendar hrPayrollCalendar, String closeMode) throws WebException {
        String result;
        try {
            result = beanRemote.closePayrollCalendar(hrPayrollCalendar, closeMode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<HrSalaryStructureTO> getTaxableComponent(int compCode) throws WebException {
        List<HrSalaryStructureTO> hrSalaryStructureTO;
        try {
            hrSalaryStructureTO = beanRemote.gettaxableComponent(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return hrSalaryStructureTO;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<PayrollCalendarTO> getOpenPayrollCalendarList(int compCode) throws WebException {
        List<PayrollCalendarTO> payrollCalendarTOs = new ArrayList<PayrollCalendarTO>();
        try {
            payrollCalendarTOs = beanRemote.getOpenCalendarList(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return payrollCalendarTOs;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<LeaveMasterTO> getLeaveMasterData(int compCode) throws WebException {
        try {
            List<LeaveMasterTO> leaveMasterTOs = beanRemote.getLeaveMasterData(compCode);
            return leaveMasterTOs;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @param dateFrom
     * @param toDate
     * @return
     * @throws WebException
     */
    public List<HrPersonnelDetailsTO> getHrPersonnelData(int compcode, String type, String value) throws WebException {
        try {
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = beanRemote.getPersonnalData(compcode, type, value);
            return hrPersonnelDetailsTOs;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public List getShiftTableData(int compcode) throws WebException {
        try {
            return (beanRemote.getShiftTableData(compcode));
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public int getTotalLeaveDays(int compCode, String leaveCode, long empCode, String hrCalendatDateFrom, String hrCalendatDateTo) throws WebException {
        try {
            int totalDays = beanRemote.getTotalLeaveDays(compCode, leaveCode, empCode, hrCalendatDateFrom, hrCalendatDateTo);
            return totalDays;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public int getNumOfLeaveDays(int compCode, String leaveCode, String fromDate, String toDate) throws WebException {
        try {
            int numDays = beanRemote.getNumOfLeaveDays(compCode, leaveCode, fromDate, toDate);
            return numDays;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public String isDateAllowed(int compCode, long empCode, String leaveRegCode, Date leaveFromDate, Date leaveToDate) throws WebException {
        String result = "";
        try {
            result = beanRemote.isDateAllowed(compCode, empCode, leaveRegCode, leaveFromDate, leaveToDate);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String getLeaveRegCode(int compCode) throws WebException {
        String result = "";
        try {
            result = beanRemote.getLeaveRegCode(compCode);
            return result;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public List<HrLeaveRegisterTO> getLeaveRegisterData(int compcode) throws WebException {
        try {
            List<HrLeaveRegisterTO> hrLeaveRegisterTOs = beanRemote.getLeaveRegisterData(compcode);
            return hrLeaveRegisterTOs;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public List<HrMstShiftTO> getShiftList(int compCode) throws WebException {
        try {
            List<HrMstShiftTO> hrMstShiftTOs = beanRemote.getShiftListData(compCode);
            return hrMstShiftTOs;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public String saveShiftMapDetail(HrShiftMapTO hrShiftMapTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveShiftMapDetail(hrShiftMapTO, mode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<HrPersonnelDetailsTO> getCategorizationBasedEmployees(int compCode, String type) throws WebException {
        try {
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = beanRemote.getCategorizationBasedEmployees(compCode, type);
            return hrPersonnelDetailsTOs;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    /**
     * *****************************************
     */
//    public List getHrSalaryStructure(int compCode, String dateFrom, String toDate) throws WebException {
    public List getHrSalaryStructure(int compCode) throws WebException {
        try {
//            List hrSalarystructureList = beanRemote.getHrSalaryStructure(compCode, dateFrom, toDate);
            List hrSalarystructureList = beanRemote.getHrSalaryStructure(compCode);
            return hrSalarystructureList;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param hrSalaryStructureTO
     * @param mode
     * @return
     * @throws WebException
     */
    public String saveHrSalaryStructure(HrSalaryStructureTO hrSalaryStructureTO, String mode) throws WebException {
        String result = "";
        try {
            result = beanRemote.saveHrSalaryStructure(hrSalaryStructureTO, mode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param hrSalaryStructurePKTO
     * @return
     * @throws WebException
     */
    public List<HrSalaryStructureTO> getSlabMasterInitialData(HrSalaryStructurePKTO hrSalaryStructurePKTO) throws WebException {
        List<HrSalaryStructureTO> hrSalaryStructureListTOs;
        try {
            hrSalaryStructureListTOs = beanRemote.getSlabMasterInitialData(hrSalaryStructurePKTO);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return hrSalaryStructureListTOs;
    }

    /**
     *
     * @param compCode
     * @param purposeCode
     * @param fromDate
     * @param toDate
     * @return
     * @throws WebException
     */
    public List getSlabMasterStructureGrid(int compCode, String purposeCode, Date fromDate, Date toDate) throws WebException {
        try {
            List slabStructureGrid = beanRemote.getSlabMasterStructureGrid(compCode, purposeCode, fromDate, toDate);
            return slabStructureGrid;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }

    }

    /**
     *
     * @param hrSlabMasterTO
     * @param mode
     * @return
     * @throws WebException
     */
    public String saveSlabStructure(HrSlabMasterTO hrSlabMasterTO, String mode) throws WebException {
        String result = "";
        try {
            result = beanRemote.saveSlabStructure(hrSlabMasterTO, mode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param salaryAllocationTO
     * @return
     * @throws WebException
     */
    public String saveSalaryAllocation(SalaryAllocationTO salaryAllocationTO, List<EmpSalaryAllocationGridTO> salaryAddGrid) throws WebException {
        String message = "";
        try {
            message = beanRemote.insertSalaryallocation(salaryAllocationTO, salaryAddGrid);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return message;
    }

    /**
     *
     * @param getSalaryAllocateData
     * @return
     * @throws WebException
     */
    public List<EmpSalaryAllocationGridTO> getSalaryAllocationForEmp(GetSalaryAllocationTO getSalaryAllocateData) throws WebException {
        List<EmpSalaryAllocationGridTO> hrsalaryAllocationList = new ArrayList<EmpSalaryAllocationGridTO>();
        try {
            hrsalaryAllocationList = beanRemote.getSalaryAllocationForEmp(getSalaryAllocateData);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return hrsalaryAllocationList;
    }

    public String saveLeaveRegisterDetail(HrLeaveRegisterTO hrLeaveRegisterTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveLeaveRegisterDetail(hrLeaveRegisterTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());

        }
        return result;
    }

    /**
     * ****************** himanshu     ***************
     */
    //Attendance Tracking get Company Name
    public String getCompanyName(int compCode) throws WebException {

        try {
            String s = beanRemote.getcmpname(compCode);
            return s;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }

    }

//Get Calender Year
    public List getcalander(int compCode) throws WebException {

        try {
            List l = beanRemote.getcalanderdata(compCode);
            return l;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }
//Get Data

    public List viewdata(int compCode, String user, String password, Date d) throws WebException {
        try {
            List l = beanRemote.viewDataofAttenden(compCode, user, password, d);
            return l;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }

    }

// Save the data
    public String insert(String user, String password, HrAttendanceMaintenanceTO hrto) throws WebException {

        try {
            System.out.println("in Delegate");
            String s = beanRemote.saveAttendenceTracking(user, password, hrto);
            return s;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public List<HrMstStructTO> getinitData(int compCode, String ch) throws WebException {
        List<HrMstStructTO> data = new ArrayList<HrMstStructTO>();
        try {
            data = beanRemote.leaveAllocationData(compCode, ch);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return data;
    }

    public List gridviewdata(int compCode, String fromDate, String toDate) throws WebException {
        List data = new ArrayList();
        try {
            data = beanRemote.viewofdataLeavePosting(compCode, fromDate, toDate);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return data;
    }

    public List<HrPersonnelDetailsTO> findDataPersonalDetailDelegat(int compCode, String search, int seatchflag) throws WebException {
        List<HrPersonnelDetailsTO> data = new ArrayList<HrPersonnelDetailsTO>();
        try {
            data = beanRemote.findDataPersonalDetailFacade(compCode, search, seatchflag);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return data;
    }

    public String performOperationLeaveAllocation(int compCode, String searchCriteria, String selectedvalue, String flag, HrLeavePostingTO hrto) throws WebException {
        String msg = "System Error";
        try {
            msg = beanRemote.operationLeaveAllocation(compCode, searchCriteria, selectedvalue, flag, hrto);
        } catch (ApplicationException e) {
            throw new WebException(e.getMessage());
        }
        return msg;
    }

    public List getHolidayList(int compCode, Date d1, Date d2) throws WebException {
        try {
            List result = beanRemote.getHolidayList(compCode, d1, d2);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public String saveHolidayMasterDetails(int compCode, String flag, HrHolidayMasterTO hr) throws WebException {
        String msg = "System Error";
        try {
            msg = beanRemote.saveHolidayMasterDetails(flag, compCode, hr);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return msg;
    }

    public String updateHolidayMasterDetails(HrHolidayMasterTO holidayMasterTO) throws WebException {
        try {
            return beanRemote.updateHolidayMasterDetails(holidayMasterTO);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
    }

    public String deleteHolidayMasterDetails(List<HrHolidayMasterTOPK> holidayMasterTOPKList) throws WebException {
        try {
            return beanRemote.deleteHolidayMasterDetails(holidayMasterTOPKList);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
    }

    /**
     * ***************** himanshu end ***********
     */
    public List<HrTaxSlabMasterTO> getTaxSlabMasterDetails(int compCode) throws ApplicationException {
        try {
            return beanRemote.getTaxSlabMasterDetails(compCode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String saveUpdateTaxSlabMasterDetails(HrTaxSlabMasterTO hrTaxSlabMasterTO, String mode) throws ApplicationException {
        try {
            return beanRemote.saveUpdateTaxSlabMasterDetails(hrTaxSlabMasterTO, mode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String getMaxTaxSlabCode(int compCode) throws ApplicationException {
        try {
            return beanRemote.getMaxTaxSlabCode(compCode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String performOprationSaveTaxinvestment(String flag, HrTaxInvestmentCategoryTO hrTaxInvestmentCategoryTO) throws WebException {
        String result = "";
        try {
            result = beanRemote.performOprationSaveTaxinvestment(flag, hrTaxInvestmentCategoryTO);
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
        }
        return result;
    }

    public List<TaxInvestmentCategoryTO> viewDataTaxInvestmentCategory(int compCode) throws WebException {

        try {
            return beanRemote.viewDataTaxInvestmentCategory(compCode);

        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public String findMaxcategoryCode(int compCode) throws ApplicationException {
        try {
            return beanRemote.findMaxcategoryCode(compCode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List findEmployeeName(int empCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            return beanRemote.findEmployeeName(empCode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<EmpSalaryAllocationGridTO> getSalaryBreakUp(double salary, int compCode, String empCd) throws WebException {
        List<EmpSalaryAllocationGridTO> hrsalaryBreakUpList = new ArrayList<EmpSalaryAllocationGridTO>();
        try {
            hrsalaryBreakUpList = beanRemote.getSalaryBreakUp(salary, compCode, empCd);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return hrsalaryBreakUpList;
    }

    public List maxSalaryStructShortCode(int compCode, String purCode) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.maxSalaryStructShortCode(compCode, purCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return detailList;
    }
}
