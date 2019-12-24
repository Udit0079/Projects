/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.payroll;

import com.hrms.common.complexTO.TaxInvestmentCategoryTO;
import com.hrms.common.exception.ApplicationException;
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
import com.hrms.common.to.SalaryAllocationTO;
import com.hrms.entity.payroll.HrPayrollCalendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sudhir Bisht
 */
public interface PayrollMasterFacadeRemote {

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public List<PayrollCalendarTO> getFinYear(int compCode) throws ApplicationException;

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public List<HrMstStructTO> getIntialData(int compCode, String structCode) throws ApplicationException;

    /**
     *
     * @param leavmstObj
     * @return
     * @throws ApplicationException
     */
    public String saveUpdateDeleteLeaveDetail(LeaveMasterTO leavmstObj, String mode) throws ApplicationException;

    /**
     * Function to save the calendar in open mode and delete the Open payroll
     * calendar . Once a calendar has been closed , cannot be reopen again.
     *
     * @param payrollCalendarTO
     * @param mod
     * @return
     * @throws ApplicationException
     */
    public String payrollCalendarSaveDelete(PayrollCalendarTO payrollCalendarTO, String mod) throws ApplicationException;

    /**
     *
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public List<PayrollCalendarTO> displayPayrollCalendarGrid(int compCode) throws ApplicationException;

    /**
     * Function to close the open the payroll calendar
     *
     * @param closeYearButtonTO
     * @return
     * @throws ApplicationException
     */
    public String closePayrollCalendar(HrPayrollCalendar hrPayrollCalendar, String closeMode) throws ApplicationException;

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public List<HrSalaryStructureTO> gettaxableComponent(int compCode) throws ApplicationException;

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public List<PayrollCalendarTO> getOpenCalendarList(int compCode) throws ApplicationException;

    /**
     *
     * @param hrTaxSal
     * @param hrSaltaxComp
     * @param salCompo
     * @param operation
     * @return
     * @throws ApplicationException
     */
    // public String saveHrTaxSalary(HrTaxSalTO hrTaxSal, String salCompo, String operation) throws ApplicationException;
    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public List<LeaveMasterTO> getLeaveMasterData(int compCode) throws ApplicationException;

    /**
     *
     * @param compCode
     * @param fromDate
     * @param toDate
     * @return
     * @throws ApplicationException
     */
//    public List getHrSalaryStructure(int compCode, String fromDate, String toDate) throws ApplicationException;
    public List getHrSalaryStructure(int compCode) throws ApplicationException;

    /**
     *
     * @param hrSalaryStructureTO
     * @param mode
     * @return
     * @throws ApplicationException
     */
    public String saveHrSalaryStructure(HrSalaryStructureTO hrSalaryStructureTO, String mode) throws ApplicationException;

    /**
     *
     * @param hrSalaryStructurePKTO
     * @return
     * @throws ApplicationException
     */
    public List<HrSalaryStructureTO> getSlabMasterInitialData(HrSalaryStructurePKTO hrSalaryStructurePKTO) throws ApplicationException;

    /**
     *
     * @param compCode
     * @param purposeCode
     * @param fromDate
     * @param toDate
     * @return
     * @throws ApplicationException
     */
    public List getSlabMasterStructureGrid(int compCode, String purposeCode, Date fromDate, Date toDate) throws ApplicationException;

    /**
     *
     * @param hrSlabMasterTO
     * @param mode
     * @return
     * @throws ApplicationException
     */
    public String saveSlabStructure(HrSlabMasterTO hrSlabMasterTO, String mode) throws ApplicationException;

    /**
     *
     * @param salaryAllocationTO
     * @return
     * @throws ApplicationException
     */
    public String insertSalaryallocation(SalaryAllocationTO salaryAllocationTO, List<EmpSalaryAllocationGridTO>salaryAddGrid) throws ApplicationException;

    /**
     * ****** navneet code ************
     */
    /**
     *
     * @param compCode
     * @param fromDate
     * @param toDate
     * @return
     * @throws ApplicationException
     */
    public List<LeaveMasterTO> getSelectedLeaveData(int compCode, String fromDate, String toDate) throws ApplicationException;

    /**
     *
     * @param hrLeaveRegisterTO
     * @param mode
     * @return
     * @throws ApplicationException
     */
    public String saveLeaveRegisterDetail(HrLeaveRegisterTO hrLeaveRegisterTO, String mode) throws ApplicationException;

    /**
     *
     * @param hrShiftMapTO
     * @param mode
     * @return
     * @throws ApplicationException
     */
    public String saveShiftMapDetail(HrShiftMapTO hrShiftMapTO, String mode) throws ApplicationException;

    /**
     *
     * @param compCode
     * @param type
     * @param value
     * @return
     * @throws ApplicationException
     */
    public List<HrPersonnelDetailsTO> getPersonnalData(int compCode, String type, String value) throws ApplicationException;

    /**
     *
     * @param compCode
     * @param leaveCode
     * @param empCode
     * @param hrCalendatDateFrom
     * @param hrCalendatDateTo
     * @return
     * @throws ApplicationException
     */
    public int getTotalLeaveDays(int compCode, String leaveCode, long empCode, String hrCalendatDateFrom, String hrCalendatDateTo) throws ApplicationException;

    /**
     *
     * @param compCode
     * @param leaveCode
     * @param fromDate
     * @param toDate
     * @return
     * @throws ApplicationException
     */
    public int getNumOfLeaveDays(int compCode, String leaveCode, String fromDate, String toDate) throws ApplicationException;

    /**
     *
     * @param compCode
     * @param empCode
     * @param leaveFromDate
     * @param leaveToDate
     * @return
     * @throws ApplicationException
     */
    public String isDateAllowed(int compCode, long empCode, String leaveRegCode, Date leaveFromDate, Date leaveToDate) throws ApplicationException;

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public String getLeaveRegCode(int compCode) throws ApplicationException;

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public List<HrLeaveRegisterTO> getLeaveRegisterData(int compCode) throws ApplicationException;

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    public List<HrMstShiftTO> getShiftListData(int compCode) throws ApplicationException;

    /**
     *
     * @param compcode
     * @return
     * @throws ApplicationException
     */
    public List getShiftTableData(int compcode) throws ApplicationException;

    /**
     *
     * @param compCode
     * @param type
     * @return
     * @throws ApplicationException
     */
    public List<HrPersonnelDetailsTO> getCategorizationBasedEmployees(int compCode, String type) throws ApplicationException;

    /**
     *
     * @param getSalaryAllocateData
     * @return
     */
    public List<EmpSalaryAllocationGridTO> getSalaryAllocationForEmp(GetSalaryAllocationTO getSalaryAllocateData) throws ApplicationException;

    /**
     * Function to get tax master by gross salary wise
     *
     * @param hrTaxSalTOPK
     * @return
     * @throws ApplicationException
     */
    //   public List<HrTaxSalTO> getTaxMasterGrossSalaryWise(HrTaxSalPKTO hrTaxSalTOPK) throws ApplicationException;
    /**
     * Get salary tax component wise
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    //  public List<HrTaxSalCompTO> getTaxMasterComponentWise(int compCode) throws ApplicationException;
    /**
     * ************** himanshu **********
     */
    public String getcmpname(int compCode) throws com.hrms.common.exception.ApplicationException;

    public List getcalanderdata(int compCode) throws ApplicationException;

    public List viewDataofAttenden(int compCode, String login, String password, Date d) throws ApplicationException;

    public String saveAttendenceTracking(String login, String password, HrAttendanceMaintenanceTO hrto) throws ApplicationException;

    public List<HrMstStructTO> leaveAllocationData(int compCode, java.lang.String ch) throws ApplicationException;

    public List viewofdataLeavePosting(int compCode, String fromDate, String toDate) throws ApplicationException;

    public List<HrPersonnelDetailsTO> findDataPersonalDetailFacade(int compCode, java.lang.String search, int seatchflag) throws ApplicationException;

    public String operationLeaveAllocation(int compCode, String searchCriteria, String selectedvalue, String flag, HrLeavePostingTO hrto) throws ApplicationException;

    public List<HrHolidayMasterTO> getHolidayList(int compCode, java.util.Date d1, java.util.Date d2) throws ApplicationException;

    public String saveHolidayMasterDetails(String flag, int compCode, HrHolidayMasterTO to) throws ApplicationException;

    public String updateHolidayMasterDetails(HrHolidayMasterTO to) throws ApplicationException;

    public String deleteHolidayMasterDetails(List<HrHolidayMasterTOPK> holidayMasterTOPKList) throws ApplicationException;

    public List<CompanyMasterTO> getCompanyMasterTO(int compCode) throws ApplicationException;

    public String performOprationSaveTaxinvestment(String flag, HrTaxInvestmentCategoryTO hrTaxInvestmentCategoryTO);

    public java.lang.String getMaxTaxSlabCode(int compCode) throws com.hrms.common.exception.ApplicationException;

    public java.lang.String saveUpdateTaxSlabMasterDetails(com.hrms.common.to.HrTaxSlabMasterTO hrTaxSlabMasterTO, java.lang.String mode) throws com.hrms.common.exception.ApplicationException;

    public java.util.List<com.hrms.common.to.HrTaxSlabMasterTO> getTaxSlabMasterDetails(int compCode) throws com.hrms.common.exception.ApplicationException;

    public java.util.List<TaxInvestmentCategoryTO> viewDataTaxInvestmentCategory(int compCode) throws ApplicationException;

    public java.lang.String findMaxcategoryCode(int compCode) throws com.hrms.common.exception.ApplicationException;

    public List findEmployeeName(int empCode) throws ApplicationException;

    public List retrieveAllPaidUnPaidLeaves(int compCode, int empcode, String fromDt, String toDt) throws ApplicationException;
    
    public List<EmpSalaryAllocationGridTO> getSalaryBreakUp(double salary, int compCode, String empCd) throws ApplicationException;
    
    public List maxSalaryStructShortCode(int compCode, String purCode) throws ApplicationException;
}
