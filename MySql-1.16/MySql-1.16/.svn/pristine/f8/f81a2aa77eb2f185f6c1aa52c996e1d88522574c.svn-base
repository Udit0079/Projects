/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.payroll;

import com.cbs.pojo.ConsolidatedFinancialSalaryPojo;
import com.cbs.pojo.FinancialSalProjectionPojo;
import com.cbs.pojo.SalarySheetPojo;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrAttendanceDetailsTO;
import com.hrms.common.to.HrSalaryProcessingTO;
import com.hrms.to.payroll.LoanDetailTo;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir Kr Bisht
 */
@Remote
public interface PayrollTransactionsFacadeRemote {

    public List getAttendanceDetails(int compCode, int year ) throws ApplicationException;

    public String postAttendanceProcessing(HrAttendanceDetailsTO hrAttendanceDetailsTO, String category, String categoryDetails, String mode ,String fromdate ,String todate ) throws ApplicationException;

    public String editAttendanceDetails(HrAttendanceDetailsTO hrAttendanceDetailsTO, String empId) throws ApplicationException;

    public String saveAttendanceProcessing(HrAttendanceDetailsTO hrAttendanceDetailsTO, String selectionCategory, String categorizationDetails, String mode) throws ApplicationException;

    public String salaryPosting(List<HrSalaryProcessingTO> hrSalaryProcessingTOs, String selectionCriteria, String selectedValues, String authBy, Date fstDt, Date lastDt) throws Exception;

    public List<HrSalaryProcessingTO> salaryCalculation(HrSalaryProcessingTO hrSalaryProcessingTO, String selectionCriteria, String selectedValues, String month) throws Exception;

    public List getAllLoanAccountForCustomer(String customerId) throws ApplicationException;

    public boolean isSalaryAccountExists(String mode, String salaryAccount, String empCode) throws ApplicationException;

    public List getEmiOnEmpIdAndLoanAc(String empId, String loanAc) throws ApplicationException;

    public String loanAcOperation(List<LoanDetailTo> table, String userName) throws ApplicationException;

    public List getYears(String compCode) throws ApplicationException;

    public List getFinYears(String compCode) throws ApplicationException;
    
    public List getYearList() throws ApplicationException ;

    public List<HrSalaryProcessingTO> salaryCalculationProjection(HrSalaryProcessingTO hrSalaryProcessingTO, String selectionCriteria, String selectedValues, String month) throws Exception;

    public List getLoanAcList(int compCode, String empCode) throws ApplicationException;

    public String getSaveData(String authBy,String enterBy,String salaryFromAccount, String salaryToAccount, String effectivedt,String modDate) throws ApplicationException;

    public List<FinancialSalProjectionPojo> getErnFinancialPrjnData(String empId, String frmDate, String toDate ,String purposeType)  throws ApplicationException;
    
    public List<ConsolidatedFinancialSalaryPojo> consolidatedFinancialSalaryReport(String frdt ,String todt) throws ApplicationException;
    
    
}
