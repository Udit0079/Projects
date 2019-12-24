package com.cbs.facade.master;

import com.cbs.dto.ReferenceCodeMasterTable;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.ChbookDetailPojo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface BankAndLoanMasterFacadeRemote {

    //BankDirectoryRemote Methods
    public List getBankDirectoryTable(String micr, String code) throws ApplicationException;

    public String updateBankDirectory(String code, String codeno, String bankname, String branch, String phone, String fax, String weekDays) throws ApplicationException;

    public String saveBankDirectory(String micr, String bankCode, String branchNo, String branchName, String branch, String phoneNo, String fax, String weekDays) throws ApplicationException;

    //Bank BankHolidayMarkingRegisterRemote
    public List gridDetailBankHolidayMarkingRegister() throws ApplicationException;

    public String saveRecordBankHolidayMarkingRegister(String holidayDate, String holidayDesc) throws ApplicationException;

    public String deleteDataBankHolidayMarkingRegister(String holidayDate, String holidayDesc, String flag) throws ApplicationException;

    //BranchStatusRemote Methods
    public List dataLoadBranchStatus(String asOnDt) throws ApplicationException;

    //LoanEmiMasterRemote Methods
    public String updatePreviousEMI(String acno, String sno, String status, String payDt, String excessAmt,String userName) throws ApplicationException;

    public List gridDetailLoanEmiMaster(String acno) throws ApplicationException;

    public String saveDetailLoanEmiMaster(String acno, String sno, String status, String payDt, String excessAmt,String userName) throws ApplicationException;

    public String customerDetailLoanEmiMaster(String acno) throws ApplicationException;

    //LoanInspectionChargesMasterRemote Methods
    public List getTLDLCAAcTypeDropDown() throws ApplicationException;

    public String saveInspectionData(List table, String insmntCode) throws ApplicationException;

    public List amountSlabTableLoanInspectionCharges(String instcode) throws ApplicationException;

    //LoanInterestParameterRemote Methods
    public List AcctNatureLoanInterestParameter(String accountNo) throws ApplicationException;

    public List finYearDropDownLoanInterestParameter() throws ApplicationException;
    
    public List getTLDLCASBAcTypeDropDownLoanInterestParameter() throws ApplicationException;

    public List getAcctType(String nature) throws ApplicationException;

    public String interestSaveLoanInterestParameter(String financialYear, String acctType, String interestType, String intOption, String userName, String todayDate, String brnCode, String frDt,String parameter,String charge) throws ApplicationException;

    //ReferenceCodeMasterRemote Methods
    public String daybeginDate() throws ApplicationException;

    public String saveReferenceCodeMaster(List<ReferenceCodeMasterTable> referenceMasterList, String todayDate, String preRefRecNo) throws ApplicationException;

    public List showDataCodeMasterTable(String refCode) throws ApplicationException;
    
    public String savePDCDetail(String acnoCr, String custName, String acnoDr, String bankName,
            String branchName, String ifscCode, String amount, String freq, String date, String chNoFrm, String chNoTo, String enterBy, 
            String brnCode, String areaCode, String bnkCode, String branchCode) throws ApplicationException;
    
    public List getPendingRefLst(String brncCode) throws ApplicationException;
    
    public List getRefDetailList(String refNo) throws ApplicationException;
    
    public String deleteReference(String refNo, String delBy) throws ApplicationException;
    
    public String verifyPDCDetail(String refNo, List<ChbookDetailPojo> pdcList,String acnoCr, String custName, String acnoDr, String bankName, 
            String branchName, String ifscCode, String amount, String enterBy, String authBy, String brnCode, 
            String areaCode, String bnkCode, String branchCode) throws ApplicationException;
    
    public String getUserName(String userId) throws ApplicationException; 
    
}
