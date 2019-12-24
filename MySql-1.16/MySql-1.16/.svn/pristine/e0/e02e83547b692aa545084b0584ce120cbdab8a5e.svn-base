/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.delegate;


import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrAttendanceDetailsTO;
import com.hrms.common.to.HrSalaryProcessingTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmpAttenDetailsGrid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class PayrollTransactionsManagementDelegate {

    private final String jndiHomeName = "PayrollTransactionsFacade";
    private PayrollTransactionsFacadeRemote beanRemote = null;

    public PayrollTransactionsManagementDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (PayrollTransactionsFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }    
    }

    /**
     * function to get the all Employee attendance details for the company and
     * year passed
     *
     * @param compCode
     * @param year
     * @return
     * @throws WebException
     */
    public List getAttendanceDetails(int compCode, int year) throws WebException {
        List attendanceList = new ArrayList();
        try {
            attendanceList = beanRemote.getAttendanceDetails(compCode, year);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return attendanceList;
    }

    /**
     *
     * @param hrAttendanceDetailsTO
     * @return
     * @throws WebException
     */
    public String editAttendanceDetails(HrAttendanceDetailsTO hrAttendanceDetailsTO, String empId) throws WebException {
        try {
            String result = beanRemote.editAttendanceDetails(hrAttendanceDetailsTO, empId);
            return result;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param hrAttendanceDetailsTO
     * @param category
     * @param categoryDetails
     * @return
     * @throws WebException
     */
    public String postAttendanceProcessing(HrAttendanceDetailsTO hrAttendanceDetailsTO, String category, String categoryDetails, String mode,String fromdate ,String todate ) throws WebException {
        try {
            String result = beanRemote.postAttendanceProcessing(hrAttendanceDetailsTO, category, categoryDetails, mode,fromdate,todate);
            return result;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     * Function for save,edit and deletion of Hr Attendance details
     *
     * @param hrAttendanceDetailsTO
     * @param selectionCategory
     * @param categorizationDetails
     * @param mode
     * @return
     * @throws WebException
     */
    public String saveAttendanceProcessing(HrAttendanceDetailsTO hrAttendanceDetailsTO, String selectionCategory, String categorizationDetails, String mode) throws WebException {
        try {
            String result = beanRemote.saveAttendanceProcessing(hrAttendanceDetailsTO, selectionCategory, categorizationDetails, mode);
            return result;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param hrSalaryProcessingTO
     * @param selectionCriteria
     * @param selectedValues
     * @return
     * @throws WebException
     */
    public String salaryPosting(List<HrSalaryProcessingTO> hrSalaryProcessingTOs, String processingCateory, String categorizationDetails, String authBy, Date fstDate, Date lastDt) throws Exception {
        String result = "";
        try {
            result = beanRemote.salaryPosting(hrSalaryProcessingTOs, processingCateory, categorizationDetails, authBy, fstDate, lastDt);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param hrSalaryProcessingTO
     * @param selectionCriteria
     * @param selectedValues
     * @param mode
     * @return
     * @throws WebException
     */
    public List<HrSalaryProcessingTO> salaryCalculation(HrSalaryProcessingTO hrSalaryProcessingTO, String selectionCriteria, String selectedValues, String month) throws Exception {
        try {
            return beanRemote.salaryCalculation(hrSalaryProcessingTO, selectionCriteria, selectedValues, month);
        } catch (ApplicationException e) {
            throw new Exception(e.getExceptionCode().getExceptionMessage());
        }
    }
    
    /**
     *
     * @param hrSalaryProcessingTO
     * @param selectionCriteria
     * @param selectedValues
     * @param mode
     * @return
     * @throws WebException
     */
    public List<HrSalaryProcessingTO> salaryCalculationProjection(HrSalaryProcessingTO hrSalaryProcessingTO, String selectionCriteria, String selectedValues, String month) throws Exception {
        try {
            return beanRemote.salaryCalculationProjection(hrSalaryProcessingTO, selectionCriteria, selectedValues, month);
        } catch (ApplicationException e) {
            throw new Exception(e.getExceptionCode().getExceptionMessage());
        }
    }
}
