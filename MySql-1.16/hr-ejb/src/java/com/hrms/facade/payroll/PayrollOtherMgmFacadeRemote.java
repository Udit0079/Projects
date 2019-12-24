/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.payroll;

import com.cbs.pojo.BonusChecklistPojo;
import com.cbs.pojo.GPFRegisterPojo;
import com.cbs.pojo.SalaryRegisterPojo;
import com.cbs.pojo.SalarySheetPojo;
import com.cbs.pojo.consolidatedMsrPojo;
import com.hrms.common.to.HrSalaryProcessingTO;
import com.hrms.common.to.EmpAllocationGridPojo;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrSlabMasterTO;
import com.hrms.common.to.SalaryAllocationTO;
import com.hrms.entity.hr.HrPersonnelDetails;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface PayrollOtherMgmFacadeRemote {

    public List getBasicSalarySlabList(String date) throws ApplicationException;

    public String saveModifySlabMasterBatchData(List<HrSlabMasterTO> slabMasterBatch, String function) throws ApplicationException;
   // aditya modified
    public List<HrSlabMasterTO> getSlabMasterData(String desigId) throws ApplicationException;
   // aditya modified
                                          
    public List<HrSalaryProcessingTO> salaryCalculation(HrSalaryProcessingTO comingHrSalaryProcessingTO,
            String selectionCriteria, String selectedValues, String month) throws Exception;
                                       
    //Aditya added
    public List getDataFromEmployeeId(String empId) throws ApplicationException;
    
    public List<EmpAllocationGridPojo> getDataFromSlabNumber(List finalSelectedSlabCode) throws ApplicationException;

    public String getRangeOfBasicSalary(String salSlabId,String basicSal)throws ApplicationException;
    
    public String saveSalAllocationDetails(SalaryAllocationTO salAllocTo, List slabNumberList) throws ApplicationException;

    public List<EmpAllocationGridPojo> getPreviousSalAllocDetails(String empId) throws ApplicationException;

    public String saveSalAllocationDetailsInHis(List<EmpAllocationGridPojo> list,String modifiedDate,String User)throws ApplicationException;
    
    public String deleteOldSalAllocationDetails(List<EmpAllocationGridPojo> list)throws ApplicationException;  
    
    public List<HrPersonnelDetails> allEmployeeList() throws ApplicationException;
    
    public String maxSlabCode() throws ApplicationException;
    
    public List<GPFRegisterPojo> getGpfRegisterData(String month,String year,String todayDate ) throws ApplicationException;
    
    public List<BonusChecklistPojo> getBonusCheckListData(String branchName,String fromDate,String toDate)throws ApplicationException;
    
    public List<SalaryRegisterPojo> getMonthlySalRegisterData(String category,String branch,String month,String year) throws ApplicationException;
        
    public String getEmpBranchCode(String EmpId) throws ApplicationException;  
    
    public List<consolidatedMsrPojo> getConsolidatedMsrData(String month,String year) throws ApplicationException;
     
     public String getEcrFileData(String path, String month, String year) throws ApplicationException;

}
  