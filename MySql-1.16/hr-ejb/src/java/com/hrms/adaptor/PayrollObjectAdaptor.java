/**
 * payroll Adaptor
 */
package com.hrms.adaptor;

import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrAttendanceDetailsPKTO;
import com.hrms.common.to.HrAttendanceDetailsTO;
import com.hrms.common.to.HrAttendanceMaintenancePKTO;
import com.hrms.common.to.HrAttendanceMaintenanceTO;
import com.hrms.common.to.HrEmpLoanDtPKTO;
import com.hrms.common.to.HrEmpLoanDtTO;
import com.hrms.common.to.HrEmpLoanHdPKTO;
import com.hrms.common.to.HrEmpLoanHdTO;
import com.hrms.common.to.HrHolidayMasterTO;
import com.hrms.common.to.HrHolidayMasterTOPK;
import com.hrms.common.to.HrLeavePostingTO;
import com.hrms.common.to.HrLeavePostingTOPK;
import com.hrms.common.to.HrLeaveRegisterPKTO;
import com.hrms.common.to.HrLeaveRegisterTO;
import com.hrms.common.to.HrMstShiftPKTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.to.LeaveMasterPKTO;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.to.PayrollCalendarPKTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.to.HrSalaryAllocationTO;
import com.hrms.common.to.HrSalaryAllocationTOPK;
import com.hrms.common.to.HrSalaryProcessingPKTO;
import com.hrms.common.to.HrSalaryProcessingTO;
import com.hrms.common.to.HrShiftMapPKTO;
import com.hrms.common.to.HrShiftMapTO;
import com.hrms.common.to.HrSlabMasterPKTO;
import com.hrms.common.to.HrSlabMasterTO;
import com.hrms.common.to.HrTaxInvestmentCategoryPKTO;
import com.hrms.common.to.HrTaxInvestmentCategoryTO;
import com.hrms.common.to.HrTaxSlabMasterPKTO;
import com.hrms.common.to.HrTaxSlabMasterTO;
import com.hrms.entity.payroll.CompanyMaster;
import com.hrms.entity.payroll.HrAttendanceDetails;
import com.hrms.entity.payroll.HrAttendanceDetailsPK;
import com.hrms.entity.payroll.HrAttendanceMaintenance;
import com.hrms.entity.payroll.HrAttendanceMaintenancePK;
import com.hrms.entity.payroll.HrHolidayMaster;
import com.hrms.entity.payroll.HrHolidayMasterPK;
import com.hrms.entity.payroll.HrLeaveMaster;
import com.hrms.entity.payroll.HrLeaveMasterPK;
import com.hrms.entity.payroll.HrLeavePosting;
import com.hrms.entity.payroll.HrLeavePostingPK;
import com.hrms.entity.payroll.HrLeaveRegister;
import com.hrms.entity.payroll.HrLeaveRegisterPK;
import com.hrms.entity.payroll.HrMstShift;
import com.hrms.entity.payroll.HrMstShiftPK;
import com.hrms.entity.payroll.HrPayrollCalendar;
import com.hrms.entity.payroll.HrPayrollCalendarPK;
import com.hrms.entity.payroll.HrSalaryAllocation;
import com.hrms.entity.payroll.HrSalaryAllocationPK;
import com.hrms.entity.payroll.HrSalaryProcessing;
import com.hrms.entity.payroll.HrSalaryProcessingPK;
import com.hrms.entity.payroll.HrShiftMap;
import com.hrms.entity.payroll.HrShiftMapPK;
import com.hrms.entity.payroll.HrSlabMaster;
import com.hrms.entity.payroll.HrSlabMasterPK;
import com.hrms.entity.payroll.HrTaxInvestmentCategory;
import com.hrms.entity.payroll.HrTaxInvestmentCategoryPK;
import com.hrms.entity.payroll.HrTaxSlabMaster;
import com.hrms.entity.payroll.HrTaxSlabMasterPK;
import com.hrms.entity.personnel.HrEmpLoanDt;
import com.hrms.entity.personnel.HrEmpLoanDtPK;
import com.hrms.entity.personnel.HrEmpLoanHd;
import com.hrms.entity.personnel.HrEmpLoanHdPK;

/**
 * @author Sudhir Bisht
 *
 */
public class PayrollObjectAdaptor {

    public static CompanyMasterTO adaptToCompanyMasterTO(CompanyMaster companyMaster) {
        CompanyMasterTO companyMasterTO = new CompanyMasterTO();
        companyMasterTO.setActiveFlag(companyMaster.getActiveFlag());
        companyMasterTO.setAddress(companyMaster.getAddress());
        companyMasterTO.setApplyVat(companyMaster.getApplyVat());
        companyMasterTO.setAuthBy(companyMaster.getAuthBy());
        companyMasterTO.setAuthFlag(companyMaster.getAuthFlag());
        companyMasterTO.setAuthStatus(companyMaster.getAuthStatus());
        companyMasterTO.setBaseCurrencyNotation(companyMaster.getBaseCurrencyNotation());
        companyMasterTO.setBaseCurrrency(companyMaster.getBaseCurrrency());
        companyMasterTO.setBooksBeginingFrom(companyMaster.getBooksBeginingFrom());
        companyMasterTO.setCity(companyMaster.getCity());
        companyMasterTO.setCompanyCode(companyMaster.getCompanyCode());
        companyMasterTO.setCompanyName(companyMaster.getCompanyName());
        companyMasterTO.setCompanyReg(companyMaster.getCompanyReg());
        companyMasterTO.setCountryCode(companyMaster.getCountryCode());
        companyMasterTO.setCstno(companyMaster.getCstno());
        companyMasterTO.setDefCompCode(companyMaster.getDefCompCode());
        companyMasterTO.setDefaultCompany(companyMaster.getDefaultCompany());
        companyMasterTO.setEffectDt(companyMaster.getEffectDt());
        companyMasterTO.setEnteredBy(companyMaster.getEnteredBy());
        companyMasterTO.setFinYearFrom(companyMaster.getFinYearFrom());
        companyMasterTO.setFloatingPoints(companyMaster.getFloatingPoints());
        companyMasterTO.setIncomeTaxNo(companyMaster.getIncomeTaxNo());
        companyMasterTO.setIstNo(companyMaster.getIstNo());
        companyMasterTO.setLstNo(companyMaster.getLstNo());
        companyMasterTO.setMailingName(companyMaster.getMailingName());
        companyMasterTO.setParentCompCode(companyMaster.getParentCompCode());
        companyMasterTO.setParentCompany(companyMaster.getParentCompany());
        companyMasterTO.setPin(companyMaster.getPin());
        companyMasterTO.setServiceTax(companyMaster.getServiceTax());
        companyMasterTO.setState(companyMaster.getState());
        companyMasterTO.setTranTime(companyMaster.getTranTime());
        companyMasterTO.setVatTinNo(companyMaster.getVatTinNo());
        return companyMasterTO;
    }

    public static CompanyMaster adaptToCompanyMasterTO(CompanyMasterTO companyMasterTO) {
        CompanyMaster companyMaster = new CompanyMaster();
        companyMaster.setActiveFlag(companyMasterTO.getActiveFlag());
        companyMaster.setAddress(companyMasterTO.getAddress());
        companyMaster.setApplyVat(companyMasterTO.getApplyVat());
        companyMaster.setAuthBy(companyMasterTO.getAuthBy());
        companyMaster.setAuthFlag(companyMasterTO.getAuthFlag());
        companyMaster.setAuthStatus(companyMasterTO.getAuthStatus());
        companyMaster.setBaseCurrencyNotation(companyMasterTO.getBaseCurrencyNotation());
        companyMaster.setBaseCurrrency(companyMasterTO.getBaseCurrrency());
        companyMaster.setBooksBeginingFrom(companyMasterTO.getBooksBeginingFrom());
        companyMaster.setCity(companyMasterTO.getCity());
        companyMaster.setCompanyCode(companyMasterTO.getCompanyCode());
        companyMaster.setCompanyName(companyMasterTO.getCompanyName());
        companyMaster.setCompanyReg(companyMasterTO.getCompanyReg());
        companyMaster.setCountryCode(companyMasterTO.getCountryCode());
        companyMaster.setCstno(companyMasterTO.getCstno());
        companyMaster.setDefCompCode(companyMasterTO.getDefCompCode());
        companyMaster.setDefaultCompany(companyMasterTO.getDefaultCompany());
        companyMaster.setEffectDt(companyMasterTO.getEffectDt());
        companyMaster.setEnteredBy(companyMasterTO.getEnteredBy());
        companyMaster.setFinYearFrom(companyMasterTO.getFinYearFrom());
        companyMaster.setFloatingPoints(companyMasterTO.getFloatingPoints());
        companyMaster.setIncomeTaxNo(companyMasterTO.getIncomeTaxNo());
        companyMaster.setIstNo(companyMasterTO.getIstNo());
        companyMaster.setLstNo(companyMasterTO.getLstNo());
        companyMaster.setMailingName(companyMasterTO.getMailingName());
        companyMaster.setParentCompCode(companyMasterTO.getParentCompCode());
        companyMaster.setParentCompany(companyMasterTO.getParentCompany());
        companyMaster.setPin(companyMasterTO.getPin());
        companyMaster.setServiceTax(companyMasterTO.getServiceTax());
        companyMaster.setState(companyMasterTO.getState());
        companyMaster.setTranTime(companyMasterTO.getTranTime());
        companyMaster.setVatTinNo(companyMasterTO.getVatTinNo());
        return companyMaster;
    }

    /**
     *
     * @param payrollCalendarPK
     * @return
     */
    public static PayrollCalendarPKTO adaptToPayrollCalendarPKTO(HrPayrollCalendarPK payrollCalendarPK) {
        PayrollCalendarPKTO payrollCalendarPKTO = new PayrollCalendarPKTO();
        payrollCalendarPKTO.setCompCode(payrollCalendarPK.getCompCode());
        payrollCalendarPKTO.setDateFrom(payrollCalendarPK.getDateFrom());
        payrollCalendarPKTO.setDateTo(payrollCalendarPK.getDateTo());
        return payrollCalendarPKTO;
    }

    /**
     *
     * @param payrollCalendar
     * @return
     */
    public static PayrollCalendarTO adaptToPayrollCalendarTO(HrPayrollCalendar payrollCalendar) {
        PayrollCalendarTO payrollCalendarTO = new PayrollCalendarTO();
        payrollCalendarTO.setPayrollCalendarPKTO(PayrollObjectAdaptor.adaptToPayrollCalendarPKTO(payrollCalendar.getHrPayrollCalendarPK()));
        payrollCalendarTO.setAuthBy(payrollCalendar.getAuthBy());
        payrollCalendarTO.setDefaultComp(payrollCalendar.getDefaultComp());
        payrollCalendarTO.setEnteredBy(payrollCalendar.getEnteredBy());
        payrollCalendarTO.setModDate(payrollCalendar.getModDate());
        payrollCalendarTO.setStatFlag(payrollCalendar.getStatFlag());
        payrollCalendarTO.setStatUpFlag(payrollCalendar.getStatUpFlag());
        payrollCalendarTO.setStatusFlag(payrollCalendar.getStatusFlag());
        return payrollCalendarTO;
    }

    /**
     *
     * @param payrollCalendarPKTO
     * @return
     */
    public static HrPayrollCalendarPK adaptToPayrollCalendarEntityPK(PayrollCalendarPKTO payrollCalendarPKTO) {
        HrPayrollCalendarPK hrPayRollCalendarPk = new HrPayrollCalendarPK();
        hrPayRollCalendarPk.setCompCode(payrollCalendarPKTO.getCompCode());
        hrPayRollCalendarPk.setDateFrom(payrollCalendarPKTO.getDateFrom());
        hrPayRollCalendarPk.setDateTo(payrollCalendarPKTO.getDateTo());
        return hrPayRollCalendarPk;
    }

    /**
     *
     * @param payrollCalendarTO
     * @return
     */
    public static HrPayrollCalendar adaptToPayrollCalendarEntity(PayrollCalendarTO payrollCalendarTO) {
        HrPayrollCalendar hrPayRollCalendar = new HrPayrollCalendar();
        hrPayRollCalendar.setAuthBy(payrollCalendarTO.getAuthBy());
        hrPayRollCalendar.setDefaultComp(payrollCalendarTO.getDefaultComp());
        hrPayRollCalendar.setEnteredBy(payrollCalendarTO.getEnteredBy());
        hrPayRollCalendar.setHrPayrollCalendarPK(PayrollObjectAdaptor.adaptToPayrollCalendarEntityPK(payrollCalendarTO.getPayrollCalendarPKTO()));
        hrPayRollCalendar.setModDate(payrollCalendarTO.getModDate());
        hrPayRollCalendar.setStatFlag(payrollCalendarTO.getStatFlag());
        hrPayRollCalendar.setStatUpFlag(payrollCalendarTO.getStatUpFlag());
        hrPayRollCalendar.setStatusFlag(payrollCalendarTO.getStatusFlag());
        return hrPayRollCalendar;
    }

    /**
     *
     * @param levaeMasterPKTO
     * @return
     */
    public static HrLeaveMasterPK adaptToHrLeaveMasterPKEntity(LeaveMasterPKTO levaeMasterPKTO) {
        HrLeaveMasterPK hrLeaveMasterPK = new HrLeaveMasterPK();
        hrLeaveMasterPK.setCompCode(levaeMasterPKTO.getCompCode());
        hrLeaveMasterPK.setDateFrom(levaeMasterPKTO.getDateFrom());
        hrLeaveMasterPK.setDateTo(levaeMasterPKTO.getDateTo());
        hrLeaveMasterPK.setLeaveCode(levaeMasterPKTO.getLeaveCode());
        return hrLeaveMasterPK;
    }

    /**
     *
     * @param leaveMasterTO
     * @return
     */
    public static HrLeaveMaster adaptToHrLeaveMasterEntity(LeaveMasterTO leaveMasterTO) {
        HrLeaveMaster hrLeaveMaster = new HrLeaveMaster();
        hrLeaveMaster.setApplicableDate(leaveMasterTO.getApplicableDate());
        hrLeaveMaster.setAuthBy(leaveMasterTO.getAuthBy());
        hrLeaveMaster.setDefaultComp(leaveMasterTO.getDefaultComp());
        hrLeaveMaster.setDescription(leaveMasterTO.getDescription());
        hrLeaveMaster.setEncash(leaveMasterTO.getEncash());
        hrLeaveMaster.setEnteredBy(leaveMasterTO.getEnteredBy());
        hrLeaveMaster.setHrLeaveMasterPK(PayrollObjectAdaptor.adaptToHrLeaveMasterPKEntity(leaveMasterTO.getLeaveMasterPKTO()));
        hrLeaveMaster.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(leaveMasterTO.getStructMasterTO()));
        hrLeaveMaster.setLeaveNature(leaveMasterTO.getLeaveNature());
        hrLeaveMaster.setModDate(leaveMasterTO.getModDate());
        hrLeaveMaster.setNumDays(leaveMasterTO.getNumDays());
        hrLeaveMaster.setStatFlag(leaveMasterTO.getStatFlag());
        hrLeaveMaster.setStatUpFlag(leaveMasterTO.getStatUpFlag());
        return hrLeaveMaster;
    }

    /**
     *
     * @param leaveMasterPK
     * @return
     */
    public static LeaveMasterPKTO adaptToHrLeaveMasterPKTO(HrLeaveMasterPK leaveMasterPK) {
        LeaveMasterPKTO leaveMasterPKTO = new LeaveMasterPKTO();
        leaveMasterPKTO.setCompCode(leaveMasterPK.getCompCode());
        leaveMasterPKTO.setDateFrom(leaveMasterPK.getDateFrom());
        leaveMasterPKTO.setDateTo(leaveMasterPK.getDateTo());
        leaveMasterPKTO.setLeaveCode(leaveMasterPK.getLeaveCode());
        return leaveMasterPKTO;

    }

    /**
     *
     * @param leaveMaster
     * @return
     */
    public static LeaveMasterTO adaptToHrLeaveMasterTO(HrLeaveMaster leaveMaster) {
        LeaveMasterTO leaveMasterTO = new LeaveMasterTO();
        leaveMasterTO.setApplicableDate(leaveMaster.getApplicableDate());
        leaveMasterTO.setAuthBy(leaveMaster.getAuthBy());
        leaveMasterTO.setDefaultComp(leaveMaster.getDefaultComp());
        leaveMasterTO.setDescription(leaveMaster.getDescription());
        leaveMasterTO.setEncash(leaveMaster.getEncash());
        leaveMasterTO.setEnteredBy(leaveMaster.getEnteredBy());
        leaveMasterTO.setLeaveMasterPKTO(PayrollObjectAdaptor.adaptToHrLeaveMasterPKTO(leaveMaster.getHrLeaveMasterPK()));
        leaveMasterTO.setLeaveNature(leaveMaster.getLeaveNature());
        leaveMasterTO.setModDate(leaveMaster.getModDate());
        leaveMasterTO.setNumDays(leaveMaster.getNumDays());
        leaveMasterTO.setStatFlag(leaveMaster.getStatFlag());
        leaveMasterTO.setStatUpFlag(leaveMaster.getStatUpFlag());
        leaveMasterTO.setStructMasterTO(ObjectAdaptorHr.adaptToStructMasterTO(leaveMaster.getHrMstStruct()));
        return leaveMasterTO;

    }

    /**
     *
     * @param hrAttendanceDetailsPKTO
     * @return
     */
    public static HrAttendanceDetailsPK adaptToAttendanceDetailsPKEntity(HrAttendanceDetailsPKTO hrAttendanceDetailsPKTO) {
        HrAttendanceDetailsPK hrAttendanceDetailsPK = new HrAttendanceDetailsPK();
        hrAttendanceDetailsPK.setAttenMonth(hrAttendanceDetailsPKTO.getAttenMonth());
        hrAttendanceDetailsPK.setAttenYear(hrAttendanceDetailsPKTO.getAttenYear());
        hrAttendanceDetailsPK.setCompCode(hrAttendanceDetailsPKTO.getCompCode());
        hrAttendanceDetailsPK.setEmpCode(hrAttendanceDetailsPKTO.getEmpCode());
        return hrAttendanceDetailsPK;
    }

    /**
     *
     * @param hrAttendanceDetailsTO
     * @return
     */
    public static HrAttendanceDetails adaptToAttendanceDetailsEntity(HrAttendanceDetailsTO hrAttendanceDetailsTO) {
        HrAttendanceDetails hrAttendanceDetails = new HrAttendanceDetails();
        hrAttendanceDetails.setAbsentDays(hrAttendanceDetailsTO.getAbsentDays());
        hrAttendanceDetails.setAuthBy(hrAttendanceDetailsTO.getAuthBy());
        hrAttendanceDetails.setDefaultComp(hrAttendanceDetailsTO.getDefaultComp());
        hrAttendanceDetails.setEnteredBy(hrAttendanceDetailsTO.getEnteredBy());
        hrAttendanceDetails.setHrAttendanceDetailsPK(PayrollObjectAdaptor.adaptToAttendanceDetailsPKEntity(hrAttendanceDetailsTO.getHrAttendanceDetailsPKTO()));
        hrAttendanceDetails.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrAttendanceDetailsTO.getHrPersonnelDetailsTO()));
        hrAttendanceDetails.setModDate(hrAttendanceDetailsTO.getModDate());
        hrAttendanceDetails.setOverTimePeriod(hrAttendanceDetailsTO.getOverTimePeriod());
        hrAttendanceDetails.setOverTimeUnit(hrAttendanceDetailsTO.getOverTimeUnit());
        hrAttendanceDetails.setPostFlag(hrAttendanceDetailsTO.getPostFlag());
        hrAttendanceDetails.setPresentDays(hrAttendanceDetailsTO.getPresentDays());
        hrAttendanceDetails.setProcessDateFrom(hrAttendanceDetailsTO.getProcessDateFrom());
        hrAttendanceDetails.setProcessDateTo(hrAttendanceDetailsTO.getProcessDateTo());
        hrAttendanceDetails.setStatFlag(hrAttendanceDetailsTO.getStatFlag());
        hrAttendanceDetails.setStatUpFlag(hrAttendanceDetailsTO.getStatUpFlag());
        hrAttendanceDetails.setWorkingDays(hrAttendanceDetailsTO.getWorkingDays());
        return hrAttendanceDetails;
    }

    /**
     *
     * @param hrAttendanceDetailsPK
     * @return
     */
    public static HrAttendanceDetailsPKTO adaptToAttendanceDetailsPKTO(HrAttendanceDetailsPK hrAttendanceDetailsPK) {
        HrAttendanceDetailsPKTO hrAttendanceDetailsPKTO = new HrAttendanceDetailsPKTO();
        hrAttendanceDetailsPKTO.setAttenMonth(hrAttendanceDetailsPK.getAttenMonth());
        hrAttendanceDetailsPKTO.setAttenYear(hrAttendanceDetailsPK.getAttenYear());
        hrAttendanceDetailsPKTO.setCompCode(hrAttendanceDetailsPK.getCompCode());
        hrAttendanceDetailsPKTO.setEmpCode(hrAttendanceDetailsPK.getEmpCode());
        return hrAttendanceDetailsPKTO;
    }

    /**
     *
     * @param hrAttendanceDetails
     * @return
     */
    public static HrAttendanceDetailsTO adaptToAttendanceDetailsTO(HrAttendanceDetails hrAttendanceDetails) {
        HrAttendanceDetailsTO hrAttendanceDetailsTO = new HrAttendanceDetailsTO();
        hrAttendanceDetailsTO.setAbsentDays(hrAttendanceDetails.getAbsentDays());
        hrAttendanceDetailsTO.setAuthBy(hrAttendanceDetails.getAuthBy());
        hrAttendanceDetailsTO.setDefaultComp(hrAttendanceDetails.getDefaultComp());
        hrAttendanceDetailsTO.setEnteredBy(hrAttendanceDetails.getEnteredBy());
        hrAttendanceDetailsTO.setHrAttendanceDetailsPKTO(PayrollObjectAdaptor.adaptToAttendanceDetailsPKTO(hrAttendanceDetails.getHrAttendanceDetailsPK()));
        hrAttendanceDetailsTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrAttendanceDetails.getHrPersonnelDetails()));
        hrAttendanceDetailsTO.setModDate(hrAttendanceDetails.getModDate());
        hrAttendanceDetailsTO.setOverTimePeriod(hrAttendanceDetails.getOverTimePeriod());
        hrAttendanceDetailsTO.setOverTimeUnit(hrAttendanceDetails.getOverTimeUnit());
        hrAttendanceDetailsTO.setPostFlag(hrAttendanceDetails.getPostFlag());
        hrAttendanceDetailsTO.setPresentDays(hrAttendanceDetails.getPresentDays());
        hrAttendanceDetailsTO.setProcessDateFrom(hrAttendanceDetails.getProcessDateFrom());
        hrAttendanceDetailsTO.setProcessDateTo(hrAttendanceDetails.getProcessDateTo());
        hrAttendanceDetailsTO.setStatFlag(hrAttendanceDetails.getStatFlag());
        hrAttendanceDetailsTO.setStatUpFlag(hrAttendanceDetails.getStatUpFlag());
        hrAttendanceDetailsTO.setWorkingDays(hrAttendanceDetails.getWorkingDays());
        return hrAttendanceDetailsTO;
    }

    /**
     *
     * @param hrAttendanceMaintenancePKTO
     * @return
     */
    public static HrAttendanceMaintenancePK adaptToHrAttenMaintainPKEntity(HrAttendanceMaintenancePKTO hrAttendanceMaintenancePKTO) {
        HrAttendanceMaintenancePK hrAttendanceMaintenanacePK = new HrAttendanceMaintenancePK();
        hrAttendanceMaintenanacePK.setAttenDate(hrAttendanceMaintenancePKTO.getAttenDate());
        hrAttendanceMaintenanacePK.setCompCode(hrAttendanceMaintenancePKTO.getCompCode());
        hrAttendanceMaintenanacePK.setEmpCode(hrAttendanceMaintenancePKTO.getEmpCode());
        return hrAttendanceMaintenanacePK;

    }

    /**
     *
     * @param hrAttendanceMaitenanaceTO
     * @return
     */
    public static HrAttendanceMaintenance adaptToHrAttenMaintainEntity(HrAttendanceMaintenanceTO hrAttendanceMaitenanaceTO) {
        HrAttendanceMaintenance hrAttendanceMaintenance = new HrAttendanceMaintenance();
        hrAttendanceMaintenance.setAttenStatusFixed(hrAttendanceMaitenanaceTO.getAttenStatusFixed());
        hrAttendanceMaintenance.setAuthBy(hrAttendanceMaitenanaceTO.getAuthBy());
        hrAttendanceMaintenance.setDefaultComp(hrAttendanceMaitenanaceTO.getDefaultComp());
        hrAttendanceMaintenance.setEnteredBy(hrAttendanceMaitenanaceTO.getEnteredBy());
        hrAttendanceMaintenance.setHrAttendanceMaintenancePK(PayrollObjectAdaptor.adaptToHrAttenMaintainPKEntity(hrAttendanceMaitenanaceTO.getHrAttendanceMaintenancePKTO()));
        hrAttendanceMaintenance.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrAttendanceMaitenanaceTO.getHrPersonnelDetailsTO()));
        hrAttendanceMaintenance.setModDate(hrAttendanceMaitenanaceTO.getModDate());
        hrAttendanceMaintenance.setStatFlag(hrAttendanceMaitenanaceTO.getStatFlag());
        hrAttendanceMaintenance.setStatUpFlag(hrAttendanceMaitenanaceTO.getStatUpFlag());
        hrAttendanceMaintenance.setTimeIn(hrAttendanceMaitenanaceTO.getTimeIn());
        hrAttendanceMaintenance.setTimeOut(hrAttendanceMaitenanaceTO.getTimeOut());
        return hrAttendanceMaintenance;
    }

    /**
     *
     * @param hrAttendanceMaintenancePK
     * @return
     */
    public static HrAttendanceMaintenancePKTO adaptToHrAttenMaintainPKTO(HrAttendanceMaintenancePK hrAttendanceMaintenancePK) {
        HrAttendanceMaintenancePKTO hrAttendanceMaintenanacePKTO = new HrAttendanceMaintenancePKTO();
        hrAttendanceMaintenanacePKTO.setAttenDate(hrAttendanceMaintenancePK.getAttenDate());
        hrAttendanceMaintenanacePKTO.setCompCode(hrAttendanceMaintenancePK.getCompCode());
        hrAttendanceMaintenanacePKTO.setEmpCode(hrAttendanceMaintenancePK.getEmpCode());
        return hrAttendanceMaintenanacePKTO;
    }

    /**
     *
     * @param hrAttendanceMaitenanace
     * @return
     */
    public static HrAttendanceMaintenanceTO adaptToHrAttenMaintainTO(HrAttendanceMaintenance hrAttendanceMaitenanace) {
        HrAttendanceMaintenanceTO hrAttendanceMaintenanceTO = new HrAttendanceMaintenanceTO();
        hrAttendanceMaintenanceTO.setAttenStatusFixed(hrAttendanceMaitenanace.getAttenStatusFixed());
        hrAttendanceMaintenanceTO.setAuthBy(hrAttendanceMaitenanace.getAuthBy());
        hrAttendanceMaintenanceTO.setDefaultComp(hrAttendanceMaitenanace.getDefaultComp());
        hrAttendanceMaintenanceTO.setEnteredBy(hrAttendanceMaitenanace.getEnteredBy());
        hrAttendanceMaintenanceTO.setHrAttendanceMaintenancePKTO(PayrollObjectAdaptor.adaptToHrAttenMaintainPKTO(hrAttendanceMaitenanace.getHrAttendanceMaintenancePK()));
        hrAttendanceMaintenanceTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrAttendanceMaitenanace.getHrPersonnelDetails()));
        hrAttendanceMaintenanceTO.setModDate(hrAttendanceMaitenanace.getModDate());
        hrAttendanceMaintenanceTO.setStatFlag(hrAttendanceMaitenanace.getStatFlag());
        hrAttendanceMaintenanceTO.setStatUpFlag(hrAttendanceMaitenanace.getStatUpFlag());
        hrAttendanceMaintenanceTO.setTimeIn(hrAttendanceMaitenanace.getTimeIn());
        hrAttendanceMaintenanceTO.setTimeOut(hrAttendanceMaitenanace.getTimeOut());
        return hrAttendanceMaintenanceTO;
    }

    /**
     *
     * @param hrHolidayMasterTOPK
     * @return
     */
    public static HrHolidayMasterPK adaptTOHrHolidayMasterPKEntity(HrHolidayMasterTOPK hrHolidayMasterTOPK) {
        HrHolidayMasterPK hrHolidayMasterPK = new HrHolidayMasterPK();
        hrHolidayMasterPK.setCompCode(hrHolidayMasterTOPK.getCompCode());
        hrHolidayMasterPK.setHolidayCode(hrHolidayMasterTOPK.getHolidayCode());
        return hrHolidayMasterPK;
    }

    /**
     *
     * @param hrHolidayMasterTO
     * @return
     */
    public static HrHolidayMaster adaptTOHrHolidayMasterEntity(HrHolidayMasterTO hrHolidayMasterTO) {
        HrHolidayMaster hrHolidayMaster = new HrHolidayMaster();
        hrHolidayMaster.setAuthBy(hrHolidayMasterTO.getAuthBy());
        hrHolidayMaster.setDefaultComp(hrHolidayMasterTO.getDefaultComp());
        hrHolidayMaster.setEnteredBy(hrHolidayMasterTO.getEnteredBy());
        hrHolidayMaster.setHolidayDate(hrHolidayMasterTO.getHolidayDate());
        hrHolidayMaster.setHolidayDateFrom(hrHolidayMasterTO.getHolidayDateFrom());
        hrHolidayMaster.setHolidayDateTo(hrHolidayMasterTO.getHolidayDateTo());
        hrHolidayMaster.setHolidayDesc(hrHolidayMasterTO.getHolidayDesc());
        hrHolidayMaster.setHrHolidayMasterPK(PayrollObjectAdaptor.adaptTOHrHolidayMasterPKEntity(hrHolidayMasterTO.getHrHolidayMasterPKTO()));
        hrHolidayMaster.setHrPayrollCalendar(PayrollObjectAdaptor.adaptToPayrollCalendarEntity(hrHolidayMasterTO.getHrPayrollCalendar()));
        hrHolidayMaster.setModDate(hrHolidayMasterTO.getModDate());
        hrHolidayMaster.setStatFlag(hrHolidayMasterTO.getStatFlag());
        hrHolidayMaster.setStatUpFlag(hrHolidayMasterTO.getStatUpFlag());
        hrHolidayMaster.setWeekDays(hrHolidayMasterTO.getWeekDays());
        return hrHolidayMaster;
    }

    /**
     *
     * @param hrHolidayMasterPK
     * @return
     */
    public static HrHolidayMasterTOPK adaptTOHrHolidayMasterPKTO(HrHolidayMasterPK hrHolidayMasterPK) {
        HrHolidayMasterTOPK hrHolidayMasterTOPK = new HrHolidayMasterTOPK();
        hrHolidayMasterTOPK.setCompCode(hrHolidayMasterPK.getCompCode());
        hrHolidayMasterTOPK.setHolidayCode(hrHolidayMasterPK.getHolidayCode());
        return hrHolidayMasterTOPK;
    }

    /**
     *
     * @param hrHolidayMaster
     * @return
     */
    public static HrHolidayMasterTO adaptTOHrHolidayMasterTO(HrHolidayMaster hrHolidayMaster) {
        HrHolidayMasterTO hrHolidayMasterTO = new HrHolidayMasterTO();
        hrHolidayMasterTO.setAuthBy(hrHolidayMaster.getAuthBy());
        hrHolidayMasterTO.setDefaultComp(hrHolidayMaster.getDefaultComp());
        hrHolidayMasterTO.setEnteredBy(hrHolidayMaster.getEnteredBy());
        hrHolidayMasterTO.setHolidayDate(hrHolidayMaster.getHolidayDate());
        hrHolidayMasterTO.setHolidayDateFrom(hrHolidayMaster.getHolidayDateFrom());
        hrHolidayMasterTO.setHolidayDateTo(hrHolidayMaster.getHolidayDateTo());
        hrHolidayMasterTO.setHolidayDesc(hrHolidayMaster.getHolidayDesc());
        hrHolidayMasterTO.setHrHolidayMasterPKTO(PayrollObjectAdaptor.adaptTOHrHolidayMasterPKTO(hrHolidayMaster.getHrHolidayMasterPK()));
        hrHolidayMasterTO.setHrPayrollCalendar(PayrollObjectAdaptor.adaptToPayrollCalendarTO(hrHolidayMaster.getHrPayrollCalendar()));
        hrHolidayMasterTO.setModDate(hrHolidayMaster.getModDate());
        hrHolidayMasterTO.setStatFlag(hrHolidayMaster.getStatFlag());
        hrHolidayMasterTO.setStatUpFlag(hrHolidayMaster.getStatUpFlag());
        hrHolidayMasterTO.setWeekDays(hrHolidayMaster.getWeekDays());
        return hrHolidayMasterTO;
    }

    /**
     *
     * @param hrLeavePostingTOPK
     * @return
     */
    public static HrLeavePostingPK adaptToHrLeavePostingPKEntity(HrLeavePostingTOPK hrLeavePostingTOPK) {
        HrLeavePostingPK hrLeavePostingPK = new HrLeavePostingPK();
        hrLeavePostingPK.setCompCode(hrLeavePostingTOPK.getCompCode());
        hrLeavePostingPK.setDateFrom(hrLeavePostingTOPK.getDateFrom());
        hrLeavePostingPK.setDateTo(hrLeavePostingTOPK.getDateTo());
        hrLeavePostingPK.setEmpCode(hrLeavePostingTOPK.getEmpCode());
        hrLeavePostingPK.setLeaveCode(hrLeavePostingTOPK.getLeaveCode());
        return hrLeavePostingPK;
    }

    /**
     *
     * @param hrLeavePostingTO
     * @return
     */
    public static HrLeavePosting adaptToHrLeavePostingEntity(HrLeavePostingTO hrLeavePostingTO) {
        HrLeavePosting hrLeavePosting = new HrLeavePosting();
        hrLeavePosting.setAuthBy(hrLeavePostingTO.getAuthBy());
        hrLeavePosting.setDefaultComp(hrLeavePostingTO.getDefaultComp());
        hrLeavePosting.setEnteredBy(hrLeavePostingTO.getEnteredBy());
        hrLeavePosting.setHrLeavePostingPK(PayrollObjectAdaptor.adaptToHrLeavePostingPKEntity(hrLeavePostingTO.getHrLeavePostingPK()));
        hrLeavePosting.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrLeavePostingTO.getHrPersonnelDetails()));
        hrLeavePosting.setModDate(hrLeavePostingTO.getModDate());
        hrLeavePosting.setPostingDate(hrLeavePostingTO.getPostingDate());
        hrLeavePosting.setStatFlag(hrLeavePostingTO.getStatFlag());
        hrLeavePosting.setStatUpFlag(hrLeavePostingTO.getStatUpFlag());
        return hrLeavePosting;
    }

    /**
     *
     * @param hrLeavePostingPK
     * @return
     */
    public static HrLeavePostingTOPK adaptToHrLeavePostingPKTO(HrLeavePostingPK hrLeavePostingPK) {
        HrLeavePostingTOPK hrLeavePostingTOPK = new HrLeavePostingTOPK();
        hrLeavePostingTOPK.setCompCode(hrLeavePostingPK.getCompCode());
        hrLeavePostingTOPK.setDateFrom(hrLeavePostingPK.getDateFrom());
        hrLeavePostingTOPK.setDateTo(hrLeavePostingPK.getDateTo());
        hrLeavePostingTOPK.setEmpCode(hrLeavePostingPK.getEmpCode());
        hrLeavePostingTOPK.setLeaveCode(hrLeavePostingPK.getLeaveCode());
        return hrLeavePostingTOPK;
    }

    /**
     *
     * @param hrLeavePosting
     * @return
     */
    public static HrLeavePostingTO adaptToHrLeavePostingTO(HrLeavePosting hrLeavePosting) {
        HrLeavePostingTO hrLeavePostingTO = new HrLeavePostingTO();
        hrLeavePostingTO.setAuthBy(hrLeavePosting.getAuthBy());
        hrLeavePostingTO.setDefaultComp(hrLeavePosting.getDefaultComp());
        hrLeavePostingTO.setEnteredBy(hrLeavePosting.getEnteredBy());
        hrLeavePostingTO.setHrLeavePostingPK(PayrollObjectAdaptor.adaptToHrLeavePostingPKTO(hrLeavePosting.getHrLeavePostingPK()));
        hrLeavePostingTO.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrLeavePosting.getHrPersonnelDetails()));
        hrLeavePostingTO.setModDate(hrLeavePosting.getModDate());
        hrLeavePostingTO.setPostingDate(hrLeavePosting.getPostingDate());
        hrLeavePostingTO.setStatFlag(hrLeavePosting.getStatFlag());
        hrLeavePostingTO.setStatUpFlag(hrLeavePosting.getStatUpFlag());
        return hrLeavePostingTO;
    }

    /**
     *
     * @param hrLeaveRegisterPKTO
     * @return
     */
    public static HrLeaveRegisterPK adaptToHrLeaveRegisterPKEntity(HrLeaveRegisterPKTO hrLeaveRegisterPKTO) {
        HrLeaveRegisterPK hrLeaveRegisterPK = new HrLeaveRegisterPK();
        hrLeaveRegisterPK.setCompCode(hrLeaveRegisterPKTO.getCompCode());
        hrLeaveRegisterPK.setEmpCode(hrLeaveRegisterPKTO.getEmpCode());
        hrLeaveRegisterPK.setLeavRegCode(hrLeaveRegisterPKTO.getLeavRegCode());
        return hrLeaveRegisterPK;
    }

    /**
     *
     * @param hrLeaveRegisterTO
     * @return
     */
    public static HrLeaveRegister adaptToHrLeaveRegisterEntity(HrLeaveRegisterTO hrLeaveRegisterTO) {
        HrLeaveRegister hrLeaveRegister = new HrLeaveRegister();
        hrLeaveRegister.setAuthBy(hrLeaveRegisterTO.getAuthBy());
        hrLeaveRegister.setDaysTaken(hrLeaveRegisterTO.getDaysTaken());
        hrLeaveRegister.setDefaultComp(hrLeaveRegister.getDefaultComp());
        hrLeaveRegister.setDepartRecom(hrLeaveRegisterTO.getDepartRecom());
        hrLeaveRegister.setEnteredBy(hrLeaveRegisterTO.getEnteredBy());
        hrLeaveRegister.setFromDate(hrLeaveRegisterTO.getFromDate());
        hrLeaveRegister.setHrLeaveRegisterPK(PayrollObjectAdaptor.adaptToHrLeaveRegisterPKEntity(hrLeaveRegisterTO.getHrLeaveRegisterPK()));
        hrLeaveRegister.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrLeaveRegisterTO.getHrPersonnelDetailsTO()));
        hrLeaveRegister.setLeaveCode(hrLeaveRegisterTO.getLeaveCode());
        hrLeaveRegister.setModDate(hrLeaveRegisterTO.getModDate());
        hrLeaveRegister.setPaid(hrLeaveRegisterTO.getPaid());
        hrLeaveRegister.setReasLeave(hrLeaveRegisterTO.getReasLeave());
        hrLeaveRegister.setRemarks(hrLeaveRegisterTO.getRemarks());
        hrLeaveRegister.setSanctAuth(hrLeaveRegisterTO.getSanctAuth());
        hrLeaveRegister.setStatFlag(hrLeaveRegisterTO.getStatFlag());
        hrLeaveRegister.setStatUpFlag(hrLeaveRegisterTO.getStatUpFlag());
        hrLeaveRegister.setToDate(hrLeaveRegisterTO.getToDate());
        return hrLeaveRegister;
    }

    /**
     *
     * @param hrLeaveRegisterPK
     * @return
     */
    public static HrLeaveRegisterPKTO adaptToHrLeaveRegisterPKTO(HrLeaveRegisterPK hrLeaveRegisterPK) {
        HrLeaveRegisterPKTO hrLeaveRegisterPKTO = new HrLeaveRegisterPKTO();
        hrLeaveRegisterPKTO.setCompCode(hrLeaveRegisterPK.getCompCode());
        hrLeaveRegisterPKTO.setEmpCode(hrLeaveRegisterPK.getEmpCode());
        hrLeaveRegisterPKTO.setLeavRegCode(hrLeaveRegisterPK.getLeavRegCode());
        return hrLeaveRegisterPKTO;
    }

    /**
     *
     * @param hrLeaveRegister
     * @return
     */
    public static HrLeaveRegisterTO adaptToHrLeaveRegisterTO(HrLeaveRegister hrLeaveRegister) {
        HrLeaveRegisterTO hrLeaveRegisterTO = new HrLeaveRegisterTO();
        hrLeaveRegisterTO.setAuthBy(hrLeaveRegister.getAuthBy());
        hrLeaveRegisterTO.setDaysTaken(hrLeaveRegister.getDaysTaken());
        hrLeaveRegisterTO.setDefaultComp(hrLeaveRegister.getDefaultComp());
        hrLeaveRegisterTO.setDepartRecom(hrLeaveRegister.getDepartRecom());
        hrLeaveRegisterTO.setEnteredBy(hrLeaveRegister.getEnteredBy());
        hrLeaveRegisterTO.setFromDate(hrLeaveRegister.getFromDate());
        hrLeaveRegisterTO.setHrLeaveRegisterPK(PayrollObjectAdaptor.adaptToHrLeaveRegisterPKTO(hrLeaveRegister.getHrLeaveRegisterPK()));
        hrLeaveRegisterTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrLeaveRegister.getHrPersonnelDetails()));
        hrLeaveRegisterTO.setLeaveCode(hrLeaveRegister.getLeaveCode());
        hrLeaveRegisterTO.setModDate(hrLeaveRegister.getModDate());
        hrLeaveRegisterTO.setPaid(hrLeaveRegister.getPaid());
        hrLeaveRegisterTO.setReasLeave(hrLeaveRegister.getReasLeave());
        hrLeaveRegisterTO.setRemarks(hrLeaveRegister.getRemarks());
        hrLeaveRegisterTO.setSanctAuth(hrLeaveRegister.getSanctAuth());
        hrLeaveRegisterTO.setStatFlag(hrLeaveRegister.getStatFlag());
        hrLeaveRegisterTO.setStatUpFlag(hrLeaveRegister.getStatUpFlag());
        hrLeaveRegisterTO.setToDate(hrLeaveRegister.getToDate());
        return hrLeaveRegisterTO;
    }

    /**
     *
     * @param hrMstShiftPKTO
     * @return
     */
    public static HrMstShiftPK adaptToHrMstShiftPKEntity(HrMstShiftPKTO hrMstShiftPKTO) {
        HrMstShiftPK hrMstShiftPK = new HrMstShiftPK();
        hrMstShiftPK.setCompCode(hrMstShiftPKTO.getCompCode());
        hrMstShiftPK.setShiftCode(hrMstShiftPKTO.getShiftCode());
        return hrMstShiftPK;
    }

    /**
     *
     * @param hrMstShiftTO
     * @return
     */
    public static HrMstShift adaptToHrMstShiftEntity(HrMstShiftTO hrMstShiftTO) {
        HrMstShift hrMstShift = new HrMstShift();
        hrMstShift.setAuthBy(hrMstShiftTO.getAuthBy());
        hrMstShift.setDefaultComp(hrMstShiftTO.getDefaultComp());
        hrMstShift.setEnteredBy(hrMstShiftTO.getEnteredBy());
        hrMstShift.setGraceShiftBreak(hrMstShiftTO.getGraceShiftBreak());
        hrMstShift.setGraceShiftIn(hrMstShiftTO.getGraceShiftIn());
        hrMstShift.setGraceShiftOut(hrMstShiftTO.getGraceShiftOut());
        hrMstShift.setHrMstShiftPK(PayrollObjectAdaptor.adaptToHrMstShiftPKEntity(hrMstShiftTO.getHrMstShiftPKTO()));
        hrMstShift.setModDate(hrMstShiftTO.getModDate());
        hrMstShift.setOdTimeFirst(hrMstShiftTO.getOdTimeFirst());
        hrMstShift.setOdTimeSecond(hrMstShiftTO.getOdTimeSecond());
        hrMstShift.setShiftBfrom(hrMstShiftTO.getShiftBfrom());
        hrMstShift.setShiftBto(hrMstShiftTO.getShiftBto());
        hrMstShift.setShiftDesc(hrMstShiftTO.getShiftDesc());
        hrMstShift.setShiftIn(hrMstShiftTO.getShiftIn());
        hrMstShift.setShiftOut(hrMstShiftTO.getShiftOut());
        hrMstShift.setShiftOutPunch(hrMstShiftTO.getShiftOutPunch());
        hrMstShift.setShiftPunchTime(hrMstShiftTO.getShiftPunchTime());
        hrMstShift.setStatFlag(hrMstShiftTO.getStatFlag());
        hrMstShift.setStatUpFlag(hrMstShiftTO.getStatUpFlag());
        return hrMstShift;
    }

    /**
     *
     * @param hrMstShiftPK
     * @return
     */
    public static HrMstShiftPKTO adaptToHrMstShiftPKTO(HrMstShiftPK hrMstShiftPK) {
        HrMstShiftPKTO hrMstShiftPKTO = new HrMstShiftPKTO();
        hrMstShiftPKTO.setCompCode(hrMstShiftPK.getCompCode());
        hrMstShiftPKTO.setShiftCode(hrMstShiftPK.getShiftCode());
        return hrMstShiftPKTO;
    }

    /**
     *
     * @param hrMstShift
     * @return
     */
    public static HrMstShiftTO adaptToHrMstShiftEntityTO(HrMstShift hrMstShift) {
        //////////////   collection
        HrMstShiftTO hrMstShiftTO = new HrMstShiftTO();
        hrMstShiftTO.setAuthBy(hrMstShift.getAuthBy());
        hrMstShiftTO.setDefaultComp(hrMstShift.getDefaultComp());
        hrMstShiftTO.setEnteredBy(hrMstShift.getEnteredBy());
        hrMstShiftTO.setGraceShiftBreak(hrMstShift.getGraceShiftBreak());
        hrMstShiftTO.setGraceShiftIn(hrMstShift.getGraceShiftIn());
        hrMstShiftTO.setGraceShiftOut(hrMstShift.getGraceShiftOut());
        hrMstShiftTO.setHrMstShiftPKTO(PayrollObjectAdaptor.adaptToHrMstShiftPKTO(hrMstShift.getHrMstShiftPK()));
        hrMstShiftTO.setModDate(hrMstShift.getModDate());
        hrMstShiftTO.setOdTimeFirst(hrMstShift.getOdTimeFirst());
        hrMstShiftTO.setOdTimeSecond(hrMstShift.getOdTimeSecond());
        hrMstShiftTO.setShiftBfrom(hrMstShift.getShiftBfrom());
        hrMstShiftTO.setShiftBto(hrMstShift.getShiftBto());
        hrMstShiftTO.setShiftDesc(hrMstShift.getShiftDesc());
        hrMstShiftTO.setShiftIn(hrMstShift.getShiftIn());
        hrMstShiftTO.setShiftOut(hrMstShift.getShiftOut());
        hrMstShiftTO.setShiftOutPunch(hrMstShift.getShiftOutPunch());
        hrMstShiftTO.setShiftPunchTime(hrMstShift.getShiftPunchTime());
        hrMstShiftTO.setStatFlag(hrMstShift.getStatFlag());
        hrMstShiftTO.setStatUpFlag(hrMstShift.getStatUpFlag());
        return hrMstShiftTO;

    }

    /**
     *
     * @param hrSalaryAllocationTOPK
     * @return
     */
    public static HrSalaryAllocationPK adaptToHrSalaryAllocationPKEntity(HrSalaryAllocationTOPK hrSalaryAllocationTOPK) {
        HrSalaryAllocationPK hrSalaryAllocationPK = new HrSalaryAllocationPK();
//        hrSalaryAllocationPK.setCalDateFrom(hrSalaryAllocationTOPK.getCalDateFrom());
//        hrSalaryAllocationPK.setCalDateTo(hrSalaryAllocationTOPK.getCalDateTo());
        hrSalaryAllocationPK.setCompCode(hrSalaryAllocationTOPK.getCompCode());
//        hrSalaryAllocationPK.setComponentType(hrSalaryAllocationTOPK.getComponentType());
        hrSalaryAllocationPK.setEmpCode(hrSalaryAllocationTOPK.getEmpCode());
//        hrSalaryAllocationPK.setMonths(hrSalaryAllocationTOPK.getMonths());
        return hrSalaryAllocationPK;
    }

    /**
     *
     * @param hrSalaryAllocationTO
     * @return
     */
    public static HrSalaryAllocation adaptToHrsalaryAllocationEntity(HrSalaryAllocationTO hrSalaryAllocationTO) {
        HrSalaryAllocation hrSalaryAllocation = new HrSalaryAllocation();
        hrSalaryAllocation.setAllocationDate(hrSalaryAllocationTO.getAllocationDate());
        hrSalaryAllocation.setAuthBy(hrSalaryAllocationTO.getAuthBy());
//        hrSalaryAllocation.setBasicSalary(hrSalaryAllocationTO.getBasicSalary());
//        hrSalaryAllocation.setDefaultComp(hrSalaryAllocationTO.getDefaultComp());
        hrSalaryAllocation.setEnteredBy(hrSalaryAllocationTO.getEnteredBy());
        //  hrSalaryAllocation.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrSalaryAllocationTO.getHrPersonnelDetailsTO()));
        hrSalaryAllocation.setHrSalaryAllocationPK(PayrollObjectAdaptor.adaptToHrSalaryAllocationPKEntity(hrSalaryAllocationTO.getHrSalaryAllocationPK()));
        hrSalaryAllocation.setModDate(hrSalaryAllocationTO.getModDate());
        hrSalaryAllocation.setStatFlag(hrSalaryAllocationTO.getStatFlag());
        hrSalaryAllocation.setStatUpFlag(hrSalaryAllocationTO.getStatUpFlag());
        return hrSalaryAllocation;
    }

    /**
     *
     * @param hrSalaryAllocationPK
     * @return
     */
    public static HrSalaryAllocationTOPK adaptToHrSalaryAllocationPKTO(HrSalaryAllocationPK hrSalaryAllocationPK) {
        HrSalaryAllocationTOPK hrSalaryAllocationTOPK = new HrSalaryAllocationTOPK();
//        hrSalaryAllocationTOPK.setCalDateFrom(hrSalaryAllocationPK.getCalDateFrom());
//        hrSalaryAllocationTOPK.setCalDateTo(hrSalaryAllocationPK.getCalDateTo());
        hrSalaryAllocationTOPK.setCompCode(hrSalaryAllocationPK.getCompCode());
//        hrSalaryAllocationTOPK.setComponentType(hrSalaryAllocationPK.getComponentType());
        hrSalaryAllocationTOPK.setEmpCode(hrSalaryAllocationTOPK.getEmpCode());
        hrSalaryAllocationTOPK.setMonths(hrSalaryAllocationTOPK.getMonths());
        return hrSalaryAllocationTOPK;
    }

    /**
     *
     * @param hrSalaryAllocation
     * @return
     */
    public static HrSalaryAllocationTO adaptToHrsalaryAllocationTOEntity(HrSalaryAllocation hrSalaryAllocation) {
        HrSalaryAllocationTO hrSalaryAllocationTO = new HrSalaryAllocationTO();
        hrSalaryAllocationTO.setAllocationDate(hrSalaryAllocation.getAllocationDate());
        hrSalaryAllocationTO.setAuthBy(hrSalaryAllocation.getAuthBy());
        hrSalaryAllocationTO.setBasicSalary(hrSalaryAllocation.getBasicSalary());
//        hrSalaryAllocationTO.setDefaultComp(hrSalaryAllocation.getDefaultComp());
        hrSalaryAllocationTO.setEnteredBy(hrSalaryAllocation.getEnteredBy());
//        hrSalaryAllocationTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrSalaryAllocation.getHrPersonnelDetails()));
        hrSalaryAllocationTO.setHrSalaryAllocationPK(PayrollObjectAdaptor.adaptToHrSalaryAllocationPKTO(hrSalaryAllocation.getHrSalaryAllocationPK()));
        hrSalaryAllocationTO.setModDate(hrSalaryAllocation.getModDate());
        hrSalaryAllocationTO.setStatFlag(hrSalaryAllocation.getStatFlag());
        hrSalaryAllocationTO.setStatUpFlag(hrSalaryAllocation.getStatUpFlag());
        return hrSalaryAllocationTO;
    }

    /**
     *
     * @param hrSalaryProcessingPKTO
     * @return
     */
    public static HrSalaryProcessingPK adaptToHrSalaryProcessingPKEntity(HrSalaryProcessingPKTO hrSalaryProcessingPKTO) {
        HrSalaryProcessingPK hrSalaryProcessingPK = new HrSalaryProcessingPK();
        hrSalaryProcessingPK.setCalDateFrom(hrSalaryProcessingPKTO.getCalDateFrom());
        hrSalaryProcessingPK.setCalDateTo(hrSalaryProcessingPKTO.getCalDateTo());
        hrSalaryProcessingPK.setCompCode(hrSalaryProcessingPKTO.getCompCode());
        hrSalaryProcessingPK.setEmpCode(hrSalaryProcessingPKTO.getEmpCode());
        hrSalaryProcessingPK.setMonths(hrSalaryProcessingPKTO.getMonths());
        return hrSalaryProcessingPK;
    }

    /**
     *
     * @param hrSalaryProcessingTO
     * @return
     */
    public static HrSalaryProcessing adaptToHrSalaryProcessingEntity(HrSalaryProcessingTO hrSalaryProcessingTO) {
        HrSalaryProcessing hrSalaryProcessing = new HrSalaryProcessing();
        hrSalaryProcessing.setAuthBy(hrSalaryProcessingTO.getAuthBy());
        hrSalaryProcessing.setDefaultComp(hrSalaryProcessingTO.getDefaultComp());
        hrSalaryProcessing.setEnteredBy(hrSalaryProcessingTO.getEnteredBy());
        //hrSalaryProcessing.setHrPayrollCalendar(PayrollObjectAdaptor.adaptToPayrollCalendarEntity(hrSalaryProcessingTO.getHrPayrollCalendarTO()));
        hrSalaryProcessing.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrSalaryProcessingTO.getHrPersonnelDetailsTO()));
        hrSalaryProcessing.setHrSalaryProcessingPK(PayrollObjectAdaptor.adaptToHrSalaryProcessingPKEntity(hrSalaryProcessingTO.getHrSalaryProcessingPK()));
        hrSalaryProcessing.setModDate(hrSalaryProcessingTO.getModDate());
        hrSalaryProcessing.setPostFlag(hrSalaryProcessingTO.getPostFlag());
        hrSalaryProcessing.setReceiptFlag(hrSalaryProcessingTO.getReceiptFlag());
        hrSalaryProcessing.setRefNo(hrSalaryProcessingTO.getRefNo());
        hrSalaryProcessing.setSalary(hrSalaryProcessingTO.getSalary());
        hrSalaryProcessing.setStatFlag(hrSalaryProcessingTO.getStatFlag());
        hrSalaryProcessing.setStatUpFlag(hrSalaryProcessingTO.getStatUpFlag());
        hrSalaryProcessing.setGrossSalary(hrSalaryProcessingTO.getGrossSalary());
        hrSalaryProcessing.setDeductiveTax(hrSalaryProcessingTO.getDeductiveTax());
        return hrSalaryProcessing;
    }

    /**
     *
     * @param hrSalaryProcessingPK
     * @return
     */
    public static HrSalaryProcessingPKTO adaptToHrSalaryProcessingPKTO(HrSalaryProcessingPK hrSalaryProcessingPK) {
        HrSalaryProcessingPKTO hrSalaryProcessingPKTO = new HrSalaryProcessingPKTO();
        hrSalaryProcessingPKTO.setCalDateFrom(hrSalaryProcessingPK.getCalDateFrom());
        hrSalaryProcessingPKTO.setCalDateTo(hrSalaryProcessingPK.getCalDateTo());
        hrSalaryProcessingPKTO.setCompCode(hrSalaryProcessingPK.getCompCode());
        hrSalaryProcessingPKTO.setEmpCode(hrSalaryProcessingPK.getEmpCode());
        hrSalaryProcessingPKTO.setMonths(hrSalaryProcessingPK.getMonths());
        return hrSalaryProcessingPKTO;
    }

    /**
     *
     * @param hrSalaryProcessing
     * @return
     */
    public static HrSalaryProcessingTO adaptToHrSalaryProcessingTO(HrSalaryProcessing hrSalaryProcessing) {
        HrSalaryProcessingTO hrSalaryProcessingTO = new HrSalaryProcessingTO();
        hrSalaryProcessingTO.setAuthBy(hrSalaryProcessing.getAuthBy());
        hrSalaryProcessingTO.setDefaultComp(hrSalaryProcessing.getDefaultComp());
        hrSalaryProcessingTO.setEnteredBy(hrSalaryProcessing.getEnteredBy());
        //hrSalaryProcessingTO.setHrPayrollCalendarTO(PayrollObjectAdaptor.adaptToPayrollCalendarTO(hrSalaryProcessing.getHrPayrollCalendar()));
        hrSalaryProcessingTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrSalaryProcessing.getHrPersonnelDetails()));
        hrSalaryProcessingTO.setHrSalaryProcessingPK(PayrollObjectAdaptor.adaptToHrSalaryProcessingPKTO(hrSalaryProcessing.getHrSalaryProcessingPK()));
        hrSalaryProcessingTO.setModDate(hrSalaryProcessing.getModDate());
        hrSalaryProcessingTO.setPostFlag(hrSalaryProcessing.getPostFlag());
        hrSalaryProcessingTO.setReceiptFlag(hrSalaryProcessing.getReceiptFlag());
        hrSalaryProcessingTO.setRefNo(hrSalaryProcessing.getRefNo());
        hrSalaryProcessingTO.setSalary(hrSalaryProcessing.getSalary());
        hrSalaryProcessingTO.setStatFlag(hrSalaryProcessing.getStatFlag());
        hrSalaryProcessingTO.setStatUpFlag(hrSalaryProcessing.getStatUpFlag());
        hrSalaryProcessingTO.setGrossSalary(hrSalaryProcessing.getGrossSalary());
        hrSalaryProcessingTO.setDeductiveTax(hrSalaryProcessing.getDeductiveTax());
        return hrSalaryProcessingTO;
    }

    /**
     *
     * @param hrSlabMasterPKTO
     * @return
     */
    public static HrSlabMasterPK adaptToHrSlabMasterPKEntity(HrSlabMasterPKTO hrSlabMasterPKTO) {
        HrSlabMasterPK hrSlabMasterPK = new HrSlabMasterPK();
//        hrSlabMasterPK.setAlDesc(hrSlabMasterPKTO.getAlDesc());
        hrSlabMasterPK.setCompCode(hrSlabMasterPKTO.getCompCode());
//        hrSlabMasterPK.setDateFrom(hrSlabMasterPKTO.getDateFrom());
//        hrSlabMasterPK.setDateTo(hrSlabMasterPKTO.getDateTo());
        hrSlabMasterPK.setNature(hrSlabMasterPKTO.getNature());
        hrSlabMasterPK.setPurposeCode(hrSlabMasterPKTO.getPurposeCode());
        hrSlabMasterPK.setSlabCode(hrSlabMasterPKTO.getSlabCode());
//        hrSlabMasterPK.setRangeFrom(hrSlabMasterPKTO.getRangeFrom());
//        hrSlabMasterPK.setRangeTo(hrSlabMasterPKTO.getRangeTo());
//        hrSlabMasterPK.setSalarySlabId(hrSlabMasterPKTO.getSalarySlabId());
        return hrSlabMasterPK;
    }

    /**
     *
     * @param hrSlabMasterTO
     * @return
     */
    public static HrSlabMaster adaptToHrSlabMasterEntity(HrSlabMasterTO hrSlabMasterTO) {
        HrSlabMaster hrSlabMaster = new HrSlabMaster();
        hrSlabMaster.setAppFlg(hrSlabMasterTO.getAppFlg());
        hrSlabMaster.setAuthBy(hrSlabMasterTO.getAuthBy());
        hrSlabMaster.setDefaultComp(hrSlabMasterTO.getDefaultComp());
        hrSlabMaster.setEnteredBy(hrSlabMasterTO.getEnteredBy());
        //     hrSlabMaster.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrSlabMasterTO.getHrMstStructTO()));
        //   hrSlabMaster.setHrMstStruct1(ObjectAdaptorHr.adaptToMstStructEntity(hrSlabMasterTO.getHrMstStruct1TO()));
        hrSlabMaster.setHrSlabMasterPK(PayrollObjectAdaptor.adaptToHrSlabMasterPKEntity(hrSlabMasterTO.getHrSlabMasterPK()));
        hrSlabMaster.setModDate(hrSlabMasterTO.getModDate());
        hrSlabMaster.setSlabCriteria(hrSlabMasterTO.getSlabCriteria());
//        hrSlabMaster.setSlabCriteriaAmt(hrSlabMasterTO.getSlabCriteriaAmt());
        hrSlabMaster.setStatFlag(hrSlabMasterTO.getStatFlag());
        hrSlabMaster.setStatUpFlag(hrSlabMasterTO.getStatUpFlag());
//        hrSlabMaster.setAlDesc(hrSlabMasterTO.getAlDesc());
        return hrSlabMaster;
    }

    /**
     *
     * @param hrSlabMasterPK
     * @return
     */
    public static HrSlabMasterPKTO adaptToHrSlabMasterPKTO(HrSlabMasterPK hrSlabMasterPK) {
        HrSlabMasterPKTO hrSlabMasterPKTO = new HrSlabMasterPKTO();
//        hrSlabMasterPKTO.setAlDesc(hrSlabMasterPK.getAlDesc());
        hrSlabMasterPKTO.setCompCode(hrSlabMasterPK.getCompCode());
//        hrSlabMasterPKTO.setDateFrom(hrSlabMasterPK.getDateFrom());
//        hrSlabMasterPKTO.setDateTo(hrSlabMasterPK.getDateTo());
        hrSlabMasterPKTO.setNature(hrSlabMasterPK.getNature());
        hrSlabMasterPKTO.setPurposeCode(hrSlabMasterPK.getPurposeCode());
        hrSlabMasterPKTO.setSlabCode(hrSlabMasterPK.getSlabCode());
//
//        hrSlabMasterPKTO.setRangeFrom(hrSlabMasterPK.getRangeFrom());
//        hrSlabMasterPKTO.setRangeTo(hrSlabMasterPK.getRangeTo());
//        hrSlabMasterPKTO.setSalarySlabId(hrSlabMasterPK.getSalarySlabId());

        return hrSlabMasterPKTO;
    }

    /**
     *
     * @param hrSlabMaster
     * @return
     */
    public static HrSlabMasterTO adaptToHrSlabMasterTO(HrSlabMaster hrSlabMaster) {
        HrSlabMasterTO hrSlabMasterTO = new HrSlabMasterTO();
        hrSlabMasterTO.setAppFlg(hrSlabMaster.getAppFlg());
        hrSlabMasterTO.setAuthBy(hrSlabMaster.getAuthBy());
        hrSlabMasterTO.setDefaultComp(hrSlabMaster.getDefaultComp());
        hrSlabMasterTO.setEnteredBy(hrSlabMaster.getEnteredBy());
        //   hrSlabMasterTO.setHrMstStruct1TO(ObjectAdaptorHr.adaptToStructMasterTO(hrSlabMaster.getHrMstStruct1()));
        //   hrSlabMasterTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrSlabMaster.getHrMstStruct()));
        hrSlabMasterTO.setHrSlabMasterPK(PayrollObjectAdaptor.adaptToHrSlabMasterPKTO(hrSlabMaster.getHrSlabMasterPK()));
        hrSlabMasterTO.setModDate(hrSlabMaster.getModDate());
//        hrSlabMasterTO.setRangeFrom(hrSlabMaster.getRangeFrom());
//        hrSlabMasterTO.setRangeTo(hrSlabMaster.getRangeTo());
        hrSlabMasterTO.setSlabCriteria(hrSlabMaster.getSlabCriteria());
        hrSlabMasterTO.setSlabCriteriaAmt(hrSlabMaster.getSlabCriteriaAmt());
        hrSlabMasterTO.setStatFlag(hrSlabMaster.getStatFlag());
        hrSlabMasterTO.setStatUpFlag(hrSlabMaster.getStatUpFlag());
//        hrSlabMasterTO.setAlDesc(hrSlabMaster.getAlDesc());
        return hrSlabMasterTO;
    }

    /**
     *
     * @param hrShiftMapPKTO
     * @return
     */
    /**
     *
     * @param hrShiftMapPKTO
     * @return
     */
    public static HrShiftMapPK adaptToHrShiftMapPKEntity(HrShiftMapPKTO hrShiftMapPKTO) {
        HrShiftMapPK hrShiftMapPK = new HrShiftMapPK();
        hrShiftMapPK.setCompCode(hrShiftMapPKTO.getCompCode());
        hrShiftMapPK.setEmpCode(hrShiftMapPKTO.getEmpCode());
        hrShiftMapPK.setShiftCode(hrShiftMapPKTO.getShiftCode());
        return hrShiftMapPK;
    }

    /**
     *
     * @param hrShiftMapTO
     * @return
     */
    public static HrShiftMap adaptToHrShiftMapEntity(HrShiftMapTO hrShiftMapTO) {
        HrShiftMap hrShiftMap = new HrShiftMap();
        hrShiftMap.setAuthBy(hrShiftMapTO.getAuthBy());
        hrShiftMap.setDefaultComp(hrShiftMapTO.getDefaultComp());
        hrShiftMap.setEnteredBy(hrShiftMapTO.getEnteredBy());
        hrShiftMap.setHrMstShift(PayrollObjectAdaptor.adaptToHrMstShiftEntity(hrShiftMapTO.getHrMstShiftTO()));
        hrShiftMap.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrShiftMapTO.getHrPersonnelDetailsTO()));
        hrShiftMap.setHrShiftMapPK(PayrollObjectAdaptor.adaptToHrShiftMapPKEntity(hrShiftMapTO.getHrShiftMapPKTO()));
        hrShiftMap.setModDate(hrShiftMapTO.getModDate());
        hrShiftMap.setStatFlag(hrShiftMapTO.getStatFlag());
        hrShiftMap.setStatUpFlag(hrShiftMapTO.getStatUpFlag());
        return hrShiftMap;
    }

    /**
     *
     * @param hrShiftMapPK
     * @return
     */
    public static HrShiftMapPKTO adaptToHrShiftMapPKTO(HrShiftMapPK hrShiftMapPK) {
        HrShiftMapPKTO hrShiftMapPKTO = new HrShiftMapPKTO();
        hrShiftMapPKTO.setCompCode(hrShiftMapPK.getCompCode());
        hrShiftMapPKTO.setEmpCode(hrShiftMapPK.getEmpCode());
        hrShiftMapPKTO.setShiftCode(hrShiftMapPK.getShiftCode());
        return hrShiftMapPKTO;

    }

    /**
     *
     * @param hrShiftMap
     * @return
     */
    public static HrShiftMapTO adaptToHrShiftMapTO(HrShiftMap hrShiftMap) {
        HrShiftMapTO hrShiftMapTO = new HrShiftMapTO();
        hrShiftMapTO.setAuthBy(hrShiftMap.getAuthBy());
        hrShiftMapTO.setDefaultComp(hrShiftMap.getDefaultComp());
        hrShiftMapTO.setEnteredBy(hrShiftMap.getEnteredBy());
        hrShiftMapTO.setHrMstShiftTO(PayrollObjectAdaptor.adaptToHrMstShiftEntityTO(hrShiftMap.getHrMstShift()));
        hrShiftMapTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrShiftMap.getHrPersonnelDetails()));
        hrShiftMapTO.setHrShiftMapPKTO(PayrollObjectAdaptor.adaptToHrShiftMapPKTO(hrShiftMap.getHrShiftMapPK()));
        hrShiftMapTO.setModDate(hrShiftMap.getModDate());
        hrShiftMapTO.setStatFlag(hrShiftMap.getStatFlag());
        hrShiftMapTO.setStatUpFlag(hrShiftMap.getStatUpFlag());
        return hrShiftMapTO;
    }

    public static HrEmpLoanHdPK adaptToHrEmpLoanHdPKEntity(HrEmpLoanHdPKTO hrEmpLoanHdPKTO) {
        HrEmpLoanHdPK hrEmpLoanHdPK = new HrEmpLoanHdPK();
        hrEmpLoanHdPK.setCompCode(hrEmpLoanHdPKTO.getCompCode());
        hrEmpLoanHdPK.setEmpCode(hrEmpLoanHdPKTO.getEmpCode());
        hrEmpLoanHdPK.setEmpLoanNo(hrEmpLoanHdPKTO.getEmpLoanNo());
        hrEmpLoanHdPK.setLoanType(hrEmpLoanHdPKTO.getLoanType());
        return hrEmpLoanHdPK;
    }

    public static HrEmpLoanHd adaptToHrEmpLoanHdEntity(HrEmpLoanHdTO hrEmpLoanHdTO) {
        HrEmpLoanHd hrEmpLoanHd = new HrEmpLoanHd();
        hrEmpLoanHd.setAuthBy(hrEmpLoanHdTO.getAuthBy());
        hrEmpLoanHd.setDefaultComp(hrEmpLoanHdTO.getDefaultComp());
        hrEmpLoanHd.setEnteredBy(hrEmpLoanHdTO.getEnteredBy());
        hrEmpLoanHd.setHrEmpLoanHdPK(PayrollObjectAdaptor.adaptToHrEmpLoanHdPKEntity(hrEmpLoanHdTO.getHrEmpLoanHdPKTO()));
        hrEmpLoanHd.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrEmpLoanHdTO.getHrMstStructTO()));
        hrEmpLoanHd.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrEmpLoanHdTO.getHrPersonnelDetailsTO()));
        hrEmpLoanHd.setInstPlan(hrEmpLoanHdTO.getInstPlan());
        hrEmpLoanHd.setModDate(hrEmpLoanHdTO.getModDate());
        hrEmpLoanHd.setNoInst(hrEmpLoanHdTO.getNoInst());
        hrEmpLoanHd.setPeriodicity(hrEmpLoanHdTO.getPeriodicity());
        hrEmpLoanHd.setRepayFlag(hrEmpLoanHdTO.getRepayFlag());
        hrEmpLoanHd.setRoi(hrEmpLoanHdTO.getRoi());
        hrEmpLoanHd.setSanctionAmt(hrEmpLoanHdTO.getSanctionAmt());
        hrEmpLoanHd.setSanctionDate(hrEmpLoanHdTO.getSanctionDate());
        hrEmpLoanHd.setStartDate(hrEmpLoanHdTO.getStartDate());
        hrEmpLoanHd.setStatFlag(hrEmpLoanHdTO.getStatFlag());
        hrEmpLoanHd.setStatUpFlag(hrEmpLoanHdTO.getStatUpFlag());
        return hrEmpLoanHd;
    }

    public static HrEmpLoanHdPKTO adaptToHrEmpLoanHdPKTO(HrEmpLoanHdPK hrEmpLoanHdPK) {
        HrEmpLoanHdPKTO hrEmpLoanHdPKTO = new HrEmpLoanHdPKTO();
        hrEmpLoanHdPKTO.setCompCode(hrEmpLoanHdPK.getCompCode());
        hrEmpLoanHdPKTO.setEmpCode(hrEmpLoanHdPK.getEmpCode());
        hrEmpLoanHdPKTO.setEmpLoanNo(hrEmpLoanHdPK.getEmpLoanNo());
        hrEmpLoanHdPKTO.setLoanType(hrEmpLoanHdPK.getLoanType());
        return hrEmpLoanHdPKTO;
    }

    public static HrEmpLoanHdTO adaptToHrEmpLoanHdEntityTO(HrEmpLoanHd hrEmpLoanHd) {
        HrEmpLoanHdTO hrEmpLoanHdTO = new HrEmpLoanHdTO();
        hrEmpLoanHdTO.setAuthBy(hrEmpLoanHd.getAuthBy());
        hrEmpLoanHdTO.setDefaultComp(hrEmpLoanHd.getDefaultComp());
        hrEmpLoanHdTO.setEnteredBy(hrEmpLoanHd.getEnteredBy());
        hrEmpLoanHdTO.setHrEmpLoanHdPKTO(PayrollObjectAdaptor.adaptToHrEmpLoanHdPKTO(hrEmpLoanHd.getHrEmpLoanHdPK()));
        hrEmpLoanHdTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrEmpLoanHd.getHrMstStruct()));
        hrEmpLoanHdTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrEmpLoanHd.getHrPersonnelDetails()));
        hrEmpLoanHdTO.setInstPlan(hrEmpLoanHd.getInstPlan());
        hrEmpLoanHdTO.setModDate(hrEmpLoanHd.getModDate());
        hrEmpLoanHdTO.setNoInst(hrEmpLoanHd.getNoInst());
        hrEmpLoanHdTO.setPeriodicity(hrEmpLoanHd.getPeriodicity());
        hrEmpLoanHdTO.setRepayFlag(hrEmpLoanHd.getRepayFlag());
        hrEmpLoanHdTO.setRoi(hrEmpLoanHd.getRoi());
        hrEmpLoanHdTO.setSanctionAmt(hrEmpLoanHd.getSanctionAmt());
        hrEmpLoanHdTO.setSanctionDate(hrEmpLoanHd.getSanctionDate());
        hrEmpLoanHdTO.setStartDate(hrEmpLoanHd.getStartDate());
        hrEmpLoanHdTO.setStatFlag(hrEmpLoanHd.getStatFlag());
        hrEmpLoanHdTO.setStatUpFlag(hrEmpLoanHd.getStatUpFlag());
        return hrEmpLoanHdTO;
    }

    public static HrEmpLoanDtPK adaptToHrEmpLoanDtPKEntity(HrEmpLoanDtPKTO hrEmpLoanDtPKTO) {
        HrEmpLoanDtPK hrEmpLoanDtPK = new HrEmpLoanDtPK();
        hrEmpLoanDtPK.setCompCode(hrEmpLoanDtPKTO.getCompCode());
        hrEmpLoanDtPK.setDueDate(hrEmpLoanDtPKTO.getDueDate());
        hrEmpLoanDtPK.setEmpCode(hrEmpLoanDtPKTO.getEmpCode());
        hrEmpLoanDtPK.setEmpLoanNo(hrEmpLoanDtPKTO.getEmpLoanNo());
        hrEmpLoanDtPK.setLoanType(hrEmpLoanDtPKTO.getLoanType());
        return hrEmpLoanDtPK;
    }

    public static HrEmpLoanDt adaptToHrEmpLoanDtEntity(HrEmpLoanDtTO hrEmpLoanDtTO) {
        HrEmpLoanDt hrEmpLoanDt = new HrEmpLoanDt();
        hrEmpLoanDt.setAuthBy(hrEmpLoanDtTO.getAuthBy());
        hrEmpLoanDt.setDefaultComp(hrEmpLoanDtTO.getDefaultComp());
        hrEmpLoanDt.setEnteredBy(hrEmpLoanDtTO.getEnteredBy());
        hrEmpLoanDt.setHrEmpLoanDtPK(PayrollObjectAdaptor.adaptToHrEmpLoanDtPKEntity(hrEmpLoanDtTO.getHrEmpLoanDtPKTO()));
        hrEmpLoanDt.setHrEmpLoanHd(PayrollObjectAdaptor.adaptToHrEmpLoanHdEntity(hrEmpLoanDtTO.getHrEmpLoanHdTO()));
        hrEmpLoanDt.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrEmpLoanDtTO.getHrMstStructTO()));
        hrEmpLoanDt.setHrPersonnelDetails(ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrEmpLoanDtTO.getHrPersonnelDetailsTO()));
        hrEmpLoanDt.setInterest(hrEmpLoanDtTO.getInterest());
        hrEmpLoanDt.setModDate(hrEmpLoanDtTO.getModDate());
        hrEmpLoanDt.setPrincipal(hrEmpLoanDtTO.getPrincipal());
        hrEmpLoanDt.setRepayFlag(hrEmpLoanDtTO.getRepayFlag());
        hrEmpLoanDt.setStatFlag(hrEmpLoanDtTO.getStatFlag());
        hrEmpLoanDt.setStatUpFlag(hrEmpLoanDtTO.getStatUpFlag());
        hrEmpLoanDt.setTotInstall(hrEmpLoanDtTO.getTotInstall());
        hrEmpLoanDt.setVarInstall(hrEmpLoanDtTO.getVarInstall());
        return hrEmpLoanDt;
    }

    public static HrEmpLoanDtPKTO adaptToHrEmpLoanDtPKTO(HrEmpLoanDtPK hrEmpLoanDtPK) {
        HrEmpLoanDtPKTO hrEmpLoanDtPKTO = new HrEmpLoanDtPKTO();
        hrEmpLoanDtPKTO.setCompCode(hrEmpLoanDtPK.getCompCode());
        hrEmpLoanDtPKTO.setDueDate(hrEmpLoanDtPK.getDueDate());
        hrEmpLoanDtPKTO.setEmpCode(hrEmpLoanDtPK.getEmpCode());
        hrEmpLoanDtPKTO.setEmpLoanNo(hrEmpLoanDtPK.getEmpLoanNo());
        hrEmpLoanDtPKTO.setLoanType(hrEmpLoanDtPK.getLoanType());
        return hrEmpLoanDtPKTO;
    }

    public static HrEmpLoanDtTO adaptToHrEmpLoanDtTO(HrEmpLoanDt hrEmpLoanDt) {
        HrEmpLoanDtTO hrEmpLoanDtTO = new HrEmpLoanDtTO();
        hrEmpLoanDtTO.setAuthBy(hrEmpLoanDt.getAuthBy());
        hrEmpLoanDtTO.setDefaultComp(hrEmpLoanDt.getDefaultComp());
        hrEmpLoanDtTO.setEnteredBy(hrEmpLoanDt.getEnteredBy());
        hrEmpLoanDtTO.setHrEmpLoanDtPKTO(PayrollObjectAdaptor.adaptToHrEmpLoanDtPKTO(hrEmpLoanDt.getHrEmpLoanDtPK()));
        hrEmpLoanDtTO.setHrEmpLoanHdTO(PayrollObjectAdaptor.adaptToHrEmpLoanHdEntityTO(hrEmpLoanDt.getHrEmpLoanHd()));
        hrEmpLoanDtTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrEmpLoanDt.getHrMstStruct()));
        hrEmpLoanDtTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrEmpLoanDt.getHrPersonnelDetails()));
        hrEmpLoanDtTO.setInterest(hrEmpLoanDt.getInterest());
        hrEmpLoanDtTO.setModDate(hrEmpLoanDt.getModDate());
        hrEmpLoanDtTO.setPrincipal(hrEmpLoanDt.getPrincipal());
        hrEmpLoanDtTO.setRepayFlag(hrEmpLoanDt.getRepayFlag());
        hrEmpLoanDtTO.setStatFlag(hrEmpLoanDt.getStatFlag());
        hrEmpLoanDtTO.setStatUpFlag(hrEmpLoanDt.getStatUpFlag());
        hrEmpLoanDtTO.setTotInstall(hrEmpLoanDt.getTotInstall());
        hrEmpLoanDtTO.setVarInstall(hrEmpLoanDt.getVarInstall());
        return hrEmpLoanDtTO;
    }

    public static HrTaxSlabMasterPK adaptToHrTaxSlabMasterPKEntity(HrTaxSlabMasterPKTO hrTaxSlabMasterPKTO) {
        HrTaxSlabMasterPK hrTaxSlabMasterPK = new HrTaxSlabMasterPK();
        hrTaxSlabMasterPK.setCompCode(hrTaxSlabMasterPKTO.getCompCode());
        hrTaxSlabMasterPK.setTaxFor(hrTaxSlabMasterPKTO.getTaxFor());
        hrTaxSlabMasterPK.setTaxSlabCode(hrTaxSlabMasterPKTO.getTaxSlabCode());
        return hrTaxSlabMasterPK;
    }

    public static HrTaxSlabMaster adaptToHrTaxSlabMasterEntity(HrTaxSlabMasterTO hrTaxSlabMasterTO) {
        HrTaxSlabMaster hrTaxSlabMaster = new HrTaxSlabMaster();
        hrTaxSlabMaster.setHrTaxSlabMasterPK(adaptToHrTaxSlabMasterPKEntity(hrTaxSlabMasterTO.getHrTaxSlabMasterPKTO()));
        hrTaxSlabMaster.setApplicableTax(hrTaxSlabMasterTO.getApplicableTax());
        hrTaxSlabMaster.setAuthBy(hrTaxSlabMasterTO.getAuthBy());
        hrTaxSlabMaster.setDefaultComp(hrTaxSlabMasterTO.getDefaultComp());
        hrTaxSlabMaster.setEnteredBy(hrTaxSlabMasterTO.getEnteredBy());
        hrTaxSlabMaster.setModDate(hrTaxSlabMasterTO.getModDate());
        hrTaxSlabMaster.setRangeFrom(hrTaxSlabMasterTO.getRangeFrom());
        hrTaxSlabMaster.setRangeTo(hrTaxSlabMasterTO.getRangeTo());
        hrTaxSlabMaster.setStatFlag(hrTaxSlabMasterTO.getStatFlag());
        hrTaxSlabMaster.setStatUpFlag(hrTaxSlabMasterTO.getStatUpFlag());
        return hrTaxSlabMaster;
    }

    public static HrTaxSlabMasterPKTO adaptToHrTaxSlabMasterPKTO(HrTaxSlabMasterPK hrTaxSlabMasterPK) {
        HrTaxSlabMasterPKTO hrTaxSlabMasterPKTO = new HrTaxSlabMasterPKTO();
        hrTaxSlabMasterPKTO.setCompCode(hrTaxSlabMasterPK.getCompCode());
        hrTaxSlabMasterPKTO.setTaxFor(hrTaxSlabMasterPK.getTaxFor());
        hrTaxSlabMasterPKTO.setTaxSlabCode(hrTaxSlabMasterPK.getTaxSlabCode());
        return hrTaxSlabMasterPKTO;
    }

    public static HrTaxSlabMasterTO adaptToHrTaxSlabMasterTO(HrTaxSlabMaster hrTaxSlabMaster) {
        HrTaxSlabMasterTO hrTaxSlabMasterTO = new HrTaxSlabMasterTO();
        hrTaxSlabMasterTO.setHrTaxSlabMasterPKTO(adaptToHrTaxSlabMasterPKTO(hrTaxSlabMaster.getHrTaxSlabMasterPK()));
        hrTaxSlabMasterTO.setApplicableTax(hrTaxSlabMaster.getApplicableTax());
        hrTaxSlabMasterTO.setAuthBy(hrTaxSlabMaster.getAuthBy());
        hrTaxSlabMasterTO.setDefaultComp(hrTaxSlabMaster.getDefaultComp());
        hrTaxSlabMasterTO.setEnteredBy(hrTaxSlabMaster.getEnteredBy());
        hrTaxSlabMasterTO.setModDate(hrTaxSlabMaster.getModDate());
        hrTaxSlabMasterTO.setRangeFrom(hrTaxSlabMaster.getRangeFrom());
        hrTaxSlabMasterTO.setRangeTo(hrTaxSlabMaster.getRangeTo());
        hrTaxSlabMasterTO.setStatFlag(hrTaxSlabMaster.getStatFlag());
        hrTaxSlabMasterTO.setStatUpFlag(hrTaxSlabMaster.getStatUpFlag());
        return hrTaxSlabMasterTO;
    }

    public static HrTaxInvestmentCategoryPK adaptToHrTaxInvestmentCategoryPKEntity(HrTaxInvestmentCategoryPKTO hrTaxInvestmentCategoryPKTO) {
        HrTaxInvestmentCategoryPK hrTaxInvestmentCategoryPK = new HrTaxInvestmentCategoryPK();
        hrTaxInvestmentCategoryPK.setCompCode(hrTaxInvestmentCategoryPKTO.getCompCode());
        hrTaxInvestmentCategoryPK.setCategoryCode(hrTaxInvestmentCategoryPKTO.getCategoryCode());
        hrTaxInvestmentCategoryPK.setEmpCode(hrTaxInvestmentCategoryPKTO.getEmpCode());
        return hrTaxInvestmentCategoryPK;
    }

    public static HrTaxInvestmentCategory adaptToHrTaxInvestmentCategoryEntity(HrTaxInvestmentCategoryTO hrTaxInvestmentCategoryTO) {
        HrTaxInvestmentCategory hrTaxInvestmentCategory = new HrTaxInvestmentCategory();
        hrTaxInvestmentCategory.setAuthBy(hrTaxInvestmentCategoryTO.getAuthBy());
        hrTaxInvestmentCategory.setCategoryAmt(hrTaxInvestmentCategoryTO.getCategoryAmt());
        hrTaxInvestmentCategory.setCategoryMaxLimit(hrTaxInvestmentCategoryTO.getCategoryMaxLimit());
        hrTaxInvestmentCategory.setDefaultComp(hrTaxInvestmentCategoryTO.getDefaultComp());
        hrTaxInvestmentCategory.setEnteredBy(hrTaxInvestmentCategoryTO.getEnteredBy());
        hrTaxInvestmentCategory.setHrTaxInvestmentCategoryPK(adaptToHrTaxInvestmentCategoryPKEntity(hrTaxInvestmentCategoryTO.getHrTaxInvestmentCategoryPKTO()));
        hrTaxInvestmentCategory.setModDate(hrTaxInvestmentCategoryTO.getModDate());
        return hrTaxInvestmentCategory;
    }

    public static HrTaxInvestmentCategoryPKTO adaptToHrTaxInvestmentCategoryPKTO(HrTaxInvestmentCategoryPK hrTaxInvestmentCategoryPK) {
        HrTaxInvestmentCategoryPKTO hrTaxInvestmentCategoryPKTO = new HrTaxInvestmentCategoryPKTO();
        hrTaxInvestmentCategoryPKTO.setCompCode(hrTaxInvestmentCategoryPK.getCompCode());
        hrTaxInvestmentCategoryPKTO.setCategoryCode(hrTaxInvestmentCategoryPK.getCategoryCode());
        hrTaxInvestmentCategoryPKTO.setEmpCode(hrTaxInvestmentCategoryPK.getEmpCode());
        return hrTaxInvestmentCategoryPKTO;
    }

    public static HrTaxInvestmentCategoryTO adaptToHrTaxInvestmentCategoryTO(HrTaxInvestmentCategory hrTaxInvestmentCategory) {
        HrTaxInvestmentCategoryTO hrTaxInvestmentCategoryTO = new HrTaxInvestmentCategoryTO();
        hrTaxInvestmentCategoryTO.setAuthBy(hrTaxInvestmentCategory.getAuthBy());
        hrTaxInvestmentCategoryTO.setCategoryAmt(hrTaxInvestmentCategory.getCategoryAmt());
        hrTaxInvestmentCategoryTO.setCategoryMaxLimit(hrTaxInvestmentCategory.getCategoryMaxLimit());
        hrTaxInvestmentCategoryTO.setDefaultComp(hrTaxInvestmentCategory.getDefaultComp());
        hrTaxInvestmentCategoryTO.setEnteredBy(hrTaxInvestmentCategory.getEnteredBy());
        hrTaxInvestmentCategoryTO.setHrTaxInvestmentCategoryPKTO(adaptToHrTaxInvestmentCategoryPKTO(hrTaxInvestmentCategory.getHrTaxInvestmentCategoryPK()));
        hrTaxInvestmentCategoryTO.setModDate(hrTaxInvestmentCategory.getModDate());
        return hrTaxInvestmentCategoryTO;
    }
}
