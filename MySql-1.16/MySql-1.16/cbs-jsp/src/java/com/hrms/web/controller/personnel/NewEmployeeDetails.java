package com.hrms.web.controller.personnel;

import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.CustomerMasterDelegate;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.to.payroll.LoanDetailTo;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.controller.Validator;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.pojo.EmployeeGrid;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;



public class NewEmployeeDetails
  extends BaseBean
{
  private static final Logger logger = Logger.getLogger(NewEmployeeDetails.class);
  private String message;
  private String function;
  private String empId;
  private String lastEmpId;
  private String empFName;
  private String empMName;
  private String empLName;
  private String empCardNo;
  private String dateOfBirth;
  private String joiningDate;
  private String workStatusCode; private String bankAccountNo; private String empSearchValue; private String empSearchCriteria; private String empName; private String mode; private String conAddress; private String perAddress; private String currentEmpId; private String customerId; private String loanAccount; private String pfAccount; private String loanAcStatus; private String loanEmi; private String mainEmpId; private String mainLoanAc; private String baseBranch; private String designation; private Character sex; private long empCode; private boolean disableSaveButton; private Integer currentIEmpRow; private char empStatus; private boolean fieldDisable; private boolean custDisable; private EmployeeGrid currentEmpItem = new EmployeeGrid();
  protected int compCode;
  protected int defaultComp;
  private PersonnelUtil personnelUtil;
  private List<EmployeeGrid> empSearchTable;
  private List<LoanDetailTo> loanTable;
  private List<SelectItem> loanAcStatusList;
  private List<SelectItem> workStatusList;
  private List<SelectItem> loanAccountList;
  private List<CompanyMasterTO> companyMasterTOs;
  private List<SelectItem> baseBranchList; private List<SelectItem> designationList; private WebUtil webUtil = new WebUtil();
  protected PersonnelDelegate personnelDelegate;
  protected CustomerMasterDelegate masterDelegate;
  protected PayrollMasterManagementDelegate masterManagementDelegate;
  private PayrollTransactionsFacadeRemote payrollRemote;
  private FtsPostingMgmtFacadeRemote ftsRemote;
  private CommonReportMethodsRemote commomRemote;
  private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
  private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
  private String retirementDate;
  private String uanNo;
  
  public NewEmployeeDetails() {
    try {
      payrollRemote = ((PayrollTransactionsFacadeRemote)HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade"));
      ftsRemote = ((FtsPostingMgmtFacadeRemote)ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade"));
      commomRemote = ((CommonReportMethodsRemote)ServiceLocator.getInstance().lookup("CommonReportMethods"));
      
      personnelDelegate = new PersonnelDelegate();
      masterDelegate = new CustomerMasterDelegate();
      masterManagementDelegate = new PayrollMasterManagementDelegate();
      

      compCode = Integer.parseInt(getOrgnBrCode());
      companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
      if (!companyMasterTOs.isEmpty()) {
        defaultComp = ((CompanyMasterTO)companyMasterTOs.get(0)).getDefCompCode().intValue();
      }
      
      getHttpSession().setAttribute("USER_NAME", getUserName());
      getHttpSession().setAttribute("EMP_CODE", Long.valueOf(empCode));
      getHttpSession().setAttribute("COMP_CODE", Integer.valueOf(compCode));
      getHttpSession().setAttribute("DEFAULT_COMP", Integer.valueOf(defaultComp));
      
      mode = "save";
      getInitialData();
      personnelUtil = new PersonnelUtil();
      setMessage("Please select function.");
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
  }
  
  public void getInitialData() {
    try {
      String structCode = "%";
      workStatusList = new ArrayList();
      workStatusList.add(new SelectItem("0", "--Select--"));
      List<HrMstStructTO> hrMstStructTOs = personnelDelegate.getInitialData(compCode, structCode);
      if (!hrMstStructTOs.isEmpty()) {
        for (HrMstStructTO hrMstStructTO : hrMstStructTOs) {
          if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("STA")) {
            workStatusList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
          }
        }
      }
      loanAccountList = new ArrayList();
      loanAcStatusList = new ArrayList();
      loanAcStatusList.add(new SelectItem("0", "--Select--"));
      loanAcStatusList.add(new SelectItem("Add"));
      
      loanAcStatusList.add(new SelectItem("Delete"));
      
      baseBranchList = new ArrayList();
      baseBranchList.add(new SelectItem("0", "--Select--"));
      List list = commomRemote.getAlphacodeAllAndBranch(getOrgnBrCode());
      for (int i = 0; i < list.size(); i++) {
        Vector ele = (Vector)list.get(i);
        baseBranchList.add(new SelectItem(ele.get(1).toString().length() < 2 ? "0" + ele.get(1).toString() : ele.get(1).toString(), ele.get(0).toString()));
      }
      

      designationList = new ArrayList();
      designationList.add(new SelectItem("0", "--Select--"));
      List<HrMstStructTO> HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "DES%");
      if (!HrMstStructTOs.isEmpty()) {
        for (HrMstStructTO hrMstStructTO : HrMstStructTOs) {
          designationList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
        }
      }
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
  }
  
  public void customerDetail() {
    try {
      if ((customerId == null) || (customerId.equals(""))) {
        setMessage("Please fill Customer Id.");
        return;
      }
      fieldRefresh();
      CBSCustomerMasterDetailTO custTO = masterDelegate.getCustDetailsByCustId(customerId);
      if (custTO == null) {
        setMessage("There is no detail for this customer.");
        return;
      }
      empFName = ((custTO.getCustname() == null) || (custTO.getCustname().equals("")) ? "" : custTO.getCustname());
      empMName = ((custTO.getMiddleName() == null) || (custTO.getMiddleName().equals("")) ? "" : custTO.getMiddleName());
      empLName = ((custTO.getLastName() == null) || (custTO.getLastName().equals("")) ? "" : custTO.getLastName());
      dateOfBirth = (custTO.getDateOfBirth() == null ? dmyFormat.format(new Date()) : dmyFormat.format(custTO.getDateOfBirth()));
      sex = Character.valueOf((custTO.getGender() == null) || (custTO.getGender().equalsIgnoreCase("")) ? "O".charAt(0) : custTO.getGender().charAt(0));
      
      loanAccountList = new ArrayList();
      loanAccountList.add(new SelectItem("0", "--Select--"));
      List list = payrollRemote.getAllLoanAccountForCustomer(customerId);
      if (!list.isEmpty()) {
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = (Object[])list.get(i);
          loanAccountList.add(new SelectItem(obj[1].toString()));
        }
      }
    } catch (Exception ex) {
      logger.error("Exception occured while executing method busValidation()", ex);
      setMessage(ex.getMessage());
    }
  }
  
  public void retirementDetails() {
    if (getWorkStatusCode().equalsIgnoreCase("STA004")) {
      setRetirementDate(getTodayDate());
    }
  }
  
  public HrPersonnelDetailsTO getPersonnelDetailsTO()
  {
    HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
    HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
    try {
      hrPersonnelDetailsPKTO.setCompCode(compCode);
      hrPersonnelDetailsPKTO.setEmpCode(empCode);
      hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
      hrPersonnelDetailsTO.setEmpId(empId);
      hrPersonnelDetailsTO.setEmpName(empName);
      hrPersonnelDetailsTO.setEmpFirName(empFName);
      hrPersonnelDetailsTO.setEmpMidName(empMName);
      hrPersonnelDetailsTO.setEmpLastName(empLName);
      hrPersonnelDetailsTO.setSex(sex.charValue());
      hrPersonnelDetailsTO.setJoinDate(dmyFormat.parse(joiningDate));
      hrPersonnelDetailsTO.setBankAccountCode(bankAccountNo.trim());
      hrPersonnelDetailsTO.setDefaultComp(Integer.valueOf(defaultComp));
      hrPersonnelDetailsTO.setModDate((Date)Date.class.newInstance());
      hrPersonnelDetailsTO.setWorkStatus(workStatusCode);
      hrPersonnelDetailsTO.setPassword(empFName);
      hrPersonnelDetailsTO.setEnteredBy(getUserName());
      hrPersonnelDetailsTO.setBirthDate(dmyFormat.parse(dateOfBirth));
      hrPersonnelDetailsTO.setEmpCardNo(empCardNo);
      hrPersonnelDetailsTO.setEmpContAdd(conAddress);
      hrPersonnelDetailsTO.setEmpStatus(empStatus);
      hrPersonnelDetailsTO.setCustomerId(customerId);
      hrPersonnelDetailsTO.setPfAccount(pfAccount);
      hrPersonnelDetailsTO.setBaseBranch(baseBranch);
      hrPersonnelDetailsTO.setDesignation(designation);
      if (getWorkStatusCode().equalsIgnoreCase("STA004")) {
        hrPersonnelDetailsTO.setRetirementDate(dmyFormat.parse(retirementDate));
      } else {
        hrPersonnelDetailsTO.setRetirementDate(null);
      }
      hrPersonnelDetailsTO.setUanNumber(uanNo);
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
    return hrPersonnelDetailsTO;
  }
  
  public void onChangeFunction() {
    try {
      if (function.equalsIgnoreCase("save")) {
        setDisableSaveButton(false);
        getNewEmpId();
        setMessage("Please Fill Employee Details");
      } else if (function.equalsIgnoreCase("update")) {
        setDisableSaveButton(false);
        setMessage("Please Select A Row To Edit");
      } else {
        empId = "";
        lastEmpId = "";
      }
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
  }
  
  public String getNewEmpId() {
    try {
      lastEmpId = personnelDelegate.getLastEmployeeId(compCode);
      int iEmpId = Integer.parseInt(lastEmpId.substring(3)) + 1;
      currentEmpId = ("EMP" + iEmpId);
      int lastEmpIdLen = lastEmpId.length();
      String str = "";
      int currentEmpIdLen = currentEmpId.length();
      if (currentEmpIdLen < lastEmpIdLen) {
        int diff = lastEmpIdLen - currentEmpIdLen;
        for (int i = 0; i < diff; i++) {
          str = str + "0";
        }
        currentEmpId = ("EMP" + str + iEmpId);
      }
      empId = currentEmpId;
      return empId;
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
    return "Error";
  }
  
  public void saveGeneralDetails() {
    try {
      if ((customerId == null) || (customerId.equals(""))) {
        setMessage("Please fill Customer Id.");
        return;
      }
      if (((empFName == null) && (empMName == null) && (empLName == null)) || ((empFName.equals("")) && (empMName.equals("")) && (empLName.equals(""))))
      {
        setMessage("Customer name should not be blank.");
        return;
      }
      if ((empFName == null) || (empFName.equals(""))) {
        setMessage("Customer first name should not be blank.");
        return;
      }
      if ((dateOfBirth == null) || (dateOfBirth.equals(""))) {
        setMessage("Customer date of birth should not be blank.");
        return;
      }
      if ((sex == null) || (String.valueOf(sex).equals("0"))) {
        setMessage("Customer sex should not be blank.");
        return;
      }
      if ((bankAccountNo == null) || (bankAccountNo.equals("")) || (bankAccountNo.trim().length() != 12)) {
        setMessage("Salary account of customer should not be blank.");
        return;
      }
      String result = ftsRemote.ftsAcnoValidate(bankAccountNo.trim(), 0, "");
      if (!result.equalsIgnoreCase("true")) {
        setMessage(result);
        return;
      }
      if ((!ftsRemote.getAccountNature(bankAccountNo).equalsIgnoreCase("SB")) && (!ftsRemote.getAccountNature(bankAccountNo).equalsIgnoreCase("CA"))) {
        setMessage("Salary account of customer should be of saving or CA nature only.");
        return;
      }
      
      int pfInGl = ftsRemote.getCodeForReportName("PF_IN_GL").intValue();
      if ((pfInGl == 0) && (
        (pfAccount == null) || (pfAccount.equals("")))) {
        setMessage("Please Fill PF Account Number");
        return;
      }
      

      if ((pfAccount != null) && (!pfAccount.equals(""))) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
        if (!p.matcher(pfAccount).matches()) {
          setMessage("Only alphanumeric value is allowed for PPF A/c No.");
          return;
        }
      }
      if ((joiningDate == null) || (joiningDate.equals(""))) {
        setMessage("Employee joining date should not be blank.");
        return;
      }
      if (!new Validator().validateDate_dd_mm_yyyy(joiningDate)) {
        setMessage("Please fill proper joining date of employee.");
        return;
      }
      if ((baseBranch == null) || (baseBranch.equals("0")) || (baseBranch.equals(""))) {
        setMessage("Please select employee base branch.");
        return;
      }
      if ((designation == null) || (designation.equals("0")) || (designation.equals(""))) {
        setMessage("Please select employee designation.");
        return;
      }
      if ((workStatusCode == null) || (workStatusCode.equals("0"))) {
        setMessage("Status of employee should not be blank.");
        return;
      }
      if (workStatusCode.equalsIgnoreCase("STA000")) {
        setEmpStatus('N');
      } else if (workStatusCode.equalsIgnoreCase("STA001")) {
        setEmpStatus('Y');
      } else if (workStatusCode.equalsIgnoreCase("STA002")) {
        setEmpStatus('H');
      } else if (workStatusCode.equalsIgnoreCase("STA003")) {
        setEmpStatus('S');
      } else if (workStatusCode.equalsIgnoreCase("STA004")) {
        setEmpStatus('R');
      } else {
        setEmpStatus('N');
      }
      
//      if ((uanNo == null) || (uanNo.equals(""))) {
//        setMessage("UAN No. should not be blank.");
//        return;
//      }
      


      if (validateData()) {
        mode = function;
        if ((mode.equalsIgnoreCase("save")) || (mode.equalsIgnoreCase("update"))) {
          boolean val = payrollRemote.isSalaryAccountExists(mode, bankAccountNo, empId.substring(3));
          if (val == true) {
            setMessage("This salary a/c is already exist.");
            return;
          }
        }
        empName = (empFName.trim() + " " + empMName.trim());
        empName = (empName.trim() + " " + empLName.trim());
        empName = empName.trim();
        if (empName.length() > 100) {
          empName = empName.substring(0, 100);
        }
        if (mode.equalsIgnoreCase("save")) {
          empCode = (personnelDelegate.getMaxEmpCode() + 1L);
        } else if (mode.equalsIgnoreCase("update")) {
          empCode = currentEmpItem.getEmpCode();
        }
        result = personnelDelegate.saveHrPersonnelEmployeeDetails(getPersonnelDetailsTO(), function);
        if ((result.toLowerCase().contains("successfully")) && 
          (loanTable != null)) {
          payrollRemote.loanAcOperation(loanTable, getUserName());
        }
        
        cancelGeneralDetails();
        setMessage(result);
      }
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
  }
  
  public boolean validateData() {
    if ((empId == null) || (empId.equalsIgnoreCase("")) || (!empId.substring(0, 3).equalsIgnoreCase("EMP"))) {
      setMessage("Employee ID must follow EMP****** format");
      return false;
    }
    return true;
  }
  
  public void getEmployeeTableData() {
    try {
      setLastEmpId("");
      setEmpId("");
      empSearchTable = personnelUtil.getEmployeeTableData(compCode, empSearchCriteria, empSearchValue);
      setMessage(empSearchTable.size() + " rows found");
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
  }
  
  public void fieldRefresh() {
    setMessage("");
    setEmpFName("");
    setEmpMName("");
    setEmpLName("");
    setDateOfBirth(dmyFormat.format(new Date()));
    setSex(Character.valueOf('0'));
    loanAccountList = new ArrayList();
    setBankAccountNo("");
    setEmpCardNo("");
    setJoiningDate("");
    setWorkStatusCode("0");
    fieldDisable = true;
    custDisable = false;
    setLoanAcStatus("0");
    setMainEmpId("");
    setMainLoanAc("");
    setLoanEmi("");
    setPfAccount("");
    setBaseBranch("0");
    setDesignation("0");
    setUanNo("");
  }
  
  public void cancelGeneralDetails() {
    try {
      setMode("save");
      setEmpSearchCriteria("Name");
      setEmpSearchValue("");
      setEmpName("");
      setEmpId("");
      setLastEmpId("");
      setMessage("");
      setFunction("0");
      setWorkStatusCode("0");
      setSex(Character.valueOf('0'));
      setEmpCode(0L);
      setEmpCardNo("");
      setEmpFName("");
      setEmpMName("");
      setEmpLName("");
      setDateOfBirth("");
      setJoiningDate("");
      setBankAccountNo("");
      setCustomerId("");
      loanAccountList = new ArrayList();
      if (empSearchTable != null) {
        empSearchTable.clear();
      }
      fieldDisable = false;
      custDisable = false;
      setLoanAcStatus("0");
      setMainEmpId("");
      setMainLoanAc("");
      setLoanEmi("");
      loanTable = new ArrayList();
      setPfAccount("");
      setBaseBranch("0");
      setDesignation("0");
      setUanNo("");
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
  }
  
  public void setEmpRowValues() {
    try {
      empCode = currentEmpItem.getEmpCode();
      compCode = currentEmpItem.getCompCode().intValue();
      
      getHttpSession().setAttribute("EMP_CODE", Long.valueOf(empCode));
      getHttpSession().setAttribute("COMP_CODE", Integer.valueOf(compCode));
      getHttpSession().setAttribute("DEFAULT_COMP", Integer.valueOf(defaultComp));
      getHttpSession().setAttribute("EMP_ID", empId);
      
      empName = currentEmpItem.getEmpName();
      if (currentEmpItem.getEmpId() != null) {
        setEmpId(currentEmpItem.getEmpId());
      } else {
        setEmpId("");
      }
      if (currentEmpItem.getEmpFName() != null) {
        setEmpFName(currentEmpItem.getEmpFName());
      } else {
        setEmpFName("");
      }
      if (currentEmpItem.getEmpMName() != null) {
        setEmpMName(currentEmpItem.getEmpMName());
      } else {
        setEmpMName("");
      }
      if (currentEmpItem.getEmpLName() != null) {
        setEmpLName(currentEmpItem.getEmpLName());
      } else {
        setEmpLName("");
      }
      if (currentEmpItem.getEmpCardNo() != null) {
        setEmpCardNo(currentEmpItem.getEmpCardNo());
      } else {
        setEmpCardNo("");
      }
      if (currentEmpItem.getDateOfBirth() != null) {
        setDateOfBirth(currentEmpItem.getDateOfBirth());
      } else {
        setDateOfBirth("");
      }
      if (currentEmpItem.getJoiningDate() != null) {
        setJoiningDate(currentEmpItem.getJoiningDate());
      } else {
        setJoiningDate("");
      }
      if (currentEmpItem.getSex().charValue() != '0') {
        setSex(currentEmpItem.getSex());
      } else {
        setSex(Character.valueOf('0'));
      }
      if (currentEmpItem.getWorkStatusCode() != null) {
        setWorkStatusCode(currentEmpItem.getWorkStatusCode());
      } else {
        setWorkStatusCode("");
      }
      if (currentEmpItem.getBankAccountNo() != null) {
        setBankAccountNo(currentEmpItem.getBankAccountNo());
      } else {
        setBankAccountNo("");
      }
      
      if (currentEmpItem.getUanNo() != null) {
        setUanNo(currentEmpItem.getUanNo());
      } else {
        setUanNo("");
      }
      
      fieldDisable = true;
      custDisable = true;
      
      customerId = currentEmpItem.getCustomerId();
      pfAccount = currentEmpItem.getPfAccount();
      baseBranch = currentEmpItem.getBaseBranch();
      designation = currentEmpItem.getDesignation();
      


      loanAccountList = new ArrayList();
      loanAccountList.add(new SelectItem("0", "--Select--"));
      List list = payrollRemote.getAllLoanAccountForCustomer(customerId);
      if (!list.isEmpty()) {
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = (Object[])list.get(i);
          loanAccountList.add(new SelectItem(obj[1].toString()));
        }
      }
      setMessage("");
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
  }
  
  public void onChangeLoanAc() {
    try {
      setMainEmpId("");
      setMainLoanAc("");
      setLoanAcStatus("0");
      setLoanEmi("");
      loanTable = new ArrayList();
      if ((loanAccount != null) && (!loanAccount.equals("0"))) {
        mainEmpId = empId;
        mainLoanAc = loanAccount;
      }
    } catch (Exception ex) {
      setMessage(ex.getMessage());
    }
  }
  
  public void getDetailOnEmpIdAndLoanAc() {
    try {
      if ((loanAcStatus == null) || (loanAcStatus.equals("0"))) {
        setMessage("Please select Status.");
        return;
      }
      if ((loanAcStatus.equals("Modify")) || (loanAcStatus.equals("Delete"))) {
        List list = payrollRemote.getEmiOnEmpIdAndLoanAc(mainEmpId, mainLoanAc);
        loanEmi = "0";
        if (!list.isEmpty()) {
          Object[] obj = (Object[])list.get(0);
          loanEmi = obj[0].toString();
        }
      }
    } catch (Exception ex) {
      setMessage(ex.getMessage());
    }
  }
  
  public void saveInTable() {
    try {
      if ((mainEmpId == null) || (mainEmpId.equals(""))) {
        setMessage("There should be Emp.Id");
        return;
      }
      if ((mainLoanAc == null) || (mainLoanAc.equals(""))) {
        setMessage("There should be Loan A/c");
        return;
      }
      if ((loanAcStatus == null) || (loanAcStatus.equals("0"))) {
        setMessage("Please select Status.");
        return;
      }
      
      if (loanTable.isEmpty()) {
        List list = payrollRemote.getEmiOnEmpIdAndLoanAc(mainEmpId, mainLoanAc);
        if ((loanAcStatus.equalsIgnoreCase("add")) && (!list.isEmpty())) {
          setMessage("You can not add twice to this loan a/c.");
          return; }
        if (((loanAcStatus.equalsIgnoreCase("delete")) || (loanAcStatus.equalsIgnoreCase("modify"))) && (list.isEmpty()))
        {
          setMessage("You can not operate any operation to this loan a/c because there is no detail exist.");
          return;
        }
        
        LoanDetailTo to = new LoanDetailTo();
        to.setEmpId(mainEmpId);
        to.setLoanAc(mainLoanAc);      
        to.setStatus(loanAcStatus);
        to.setCompCode(compCode);
        to.setDefCompCode(defaultComp);
        loanTable.add(to);
      }
    } catch (Exception ex) {
      setMessage(ex.getMessage());
    }
  }
  
  public String exitGeneralDetails() {
    try {
      cancelGeneralDetails();
    } catch (Exception e) {
      logger.error("Exception occured while executing method busValidation()", e);
      setMessage(e.getMessage());
    }
    return "case1";
  }
  
  public String getBankAccountNo() {
    return bankAccountNo;
  }
  
  public void setBankAccountNo(String bankAccountNo) {
    this.bankAccountNo = bankAccountNo;
  }
  
  public EmployeeGrid getCurrentEmpItem() {
    return currentEmpItem;
  }
  
  public void setCurrentEmpItem(EmployeeGrid currentEmpItem) {
    this.currentEmpItem = currentEmpItem;
  }
  
  public Integer getCurrentIEmpRow() {
    return currentIEmpRow;
  }
  
  public void setCurrentIEmpRow(Integer currentIEmpRow) {
    this.currentIEmpRow = currentIEmpRow;
  }
  
  public String getDateOfBirth() {
    return dateOfBirth;
  }
  
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
  
  public boolean isDisableSaveButton() {
    return disableSaveButton;
  }
  
  public void setDisableSaveButton(boolean disableSaveButton) {
    this.disableSaveButton = disableSaveButton;
  }
  
  public String getEmpCardNo() {
    return empCardNo;
  }
  
  public void setEmpCardNo(String empCardNo) {
    this.empCardNo = empCardNo;
  }
  
  public String getEmpFName() {
    return empFName;
  }
  
  public void setEmpFName(String empFName) {
    this.empFName = empFName;
  }
  
  public String getEmpId() {
    return empId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public String getEmpLName() {
    return empLName;
  }
  
  public void setEmpLName(String empLName) {
    this.empLName = empLName;
  }
  
  public String getEmpMName() {
    return empMName;
  }
  
  public void setEmpMName(String empMName) {
    this.empMName = empMName;
  }
  
  public String getEmpSearchCriteria() {
    return empSearchCriteria;
  }
  
  public void setEmpSearchCriteria(String empSearchCriteria) {
    this.empSearchCriteria = empSearchCriteria;
  }
  
  public List<EmployeeGrid> getEmpSearchTable() {
    return empSearchTable;
  }
  
  public void setEmpSearchTable(List<EmployeeGrid> empSearchTable) {
    this.empSearchTable = empSearchTable;
  }
  
  public String getEmpSearchValue() {
    return empSearchValue;
  }
  
  public void setEmpSearchValue(String empSearchValue) {
    this.empSearchValue = empSearchValue;
  }
  
  public String getFunction() {
    return function;
  }
  
  public void setFunction(String function) {
    this.function = function;
  }
  
  public String getJoiningDate() {
    return joiningDate;
  }
  
  public void setJoiningDate(String joiningDate) {
    this.joiningDate = joiningDate;
  }
  
  public String getLastEmpId() {
    return lastEmpId;
  }
  
  public void setLastEmpId(String lastEmpId) {
    this.lastEmpId = lastEmpId;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public Character getSex() {
    return sex;
  }
  
  public void setSex(Character sex) {
    this.sex = sex;
  }
  
  public String getWorkStatusCode() {
    return workStatusCode;
  }
  
  public void setWorkStatusCode(String workStatusCode) {
    this.workStatusCode = workStatusCode;
  }
  
  public List<SelectItem> getWorkStatusList() {
    return workStatusList;
  }
  
  public void setWorkStatusList(List<SelectItem> workStatusList) {
    this.workStatusList = workStatusList;
  }
  
  public int getCompCode() {
    return compCode;
  }
  
  public void setCompCode(int compCode) {
    this.compCode = compCode;
  }
  
  public int getDefaultComp() {
    return defaultComp;
  }
  
  public void setDefaultComp(int defaultComp) {
    this.defaultComp = defaultComp;
  }
  
  public long getEmpCode() {
    return empCode;
  }
  
  public void setEmpCode(long empCode) {
    this.empCode = empCode;
  }
  
  public String getEmpName() {
    return empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getMode() {
    return mode;
  }
  
  public void setMode(String mode) {
    this.mode = mode;
  }
  
  public String getConAddress() {
    return conAddress;
  }
  
  public void setConAddress(String conAddress) {
    this.conAddress = conAddress;
  }
  
  public String getPerAddress() {
    return perAddress;
  }
  
  public void setPerAddress(String perAddress) {
    this.perAddress = perAddress;
  }
  
  public String getCurrentEmpId() {
    return currentEmpId;
  }
  
  public void setCurrentEmpId(String currentEmpId) {
    this.currentEmpId = currentEmpId;
  }
  
  public char getEmpStatus() {
    return empStatus;
  }
  
  public void setEmpStatus(char empStatus) {
    this.empStatus = empStatus;
  }
  
  public List<SelectItem> getLoanAccountList() {
    return loanAccountList;
  }
  
  public void setLoanAccountList(List<SelectItem> loanAccountList) {
    this.loanAccountList = loanAccountList;
  }
  
  public String getLoanAccount() {
    return loanAccount;
  }
  
  public void setLoanAccount(String loanAccount) {
    this.loanAccount = loanAccount;
  }
  
  public String getCustomerId() {
    return customerId;
  }
  
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }
  
  public boolean isFieldDisable() {
    return fieldDisable;
  }
  
  public void setFieldDisable(boolean fieldDisable) {
    this.fieldDisable = fieldDisable;
  }
  
  public boolean isCustDisable() {
    return custDisable;
  }
  
  public void setCustDisable(boolean custDisable) {
    this.custDisable = custDisable;
  }
  
  public String getPfAccount() {
    return pfAccount;
  }
  
  public void setPfAccount(String pfAccount) {
    this.pfAccount = pfAccount;
  }
  
  public String getLoanAcStatus() {
    return loanAcStatus;
  }
  
  public void setLoanAcStatus(String loanAcStatus) {
    this.loanAcStatus = loanAcStatus;
  }
  
  public List<SelectItem> getLoanAcStatusList() {
    return loanAcStatusList;
  }
  
  public void setLoanAcStatusList(List<SelectItem> loanAcStatusList) {
    this.loanAcStatusList = loanAcStatusList;
  }
  
  public String getLoanEmi() {
    return loanEmi;
  }
  
  public void setLoanEmi(String loanEmi) {
    this.loanEmi = loanEmi;
  }
  
  public String getMainEmpId() {
    return mainEmpId;
  }
  
  public void setMainEmpId(String mainEmpId) {
    this.mainEmpId = mainEmpId;
  }
  
  public String getMainLoanAc() {
    return mainLoanAc;
  }
  
  public void setMainLoanAc(String mainLoanAc) {
    this.mainLoanAc = mainLoanAc;
  }
  
  public List<LoanDetailTo> getLoanTable() {
    return loanTable;
  }
  
  public void setLoanTable(List<LoanDetailTo> loanTable) {
    this.loanTable = loanTable;
  }
  
  public String getBaseBranch() {
    return baseBranch;
  }
  
  public void setBaseBranch(String baseBranch) {
    this.baseBranch = baseBranch;
  }
  
  public List<SelectItem> getBaseBranchList() {
    return baseBranchList;
  }
  
  public void setBaseBranchList(List<SelectItem> baseBranchList) {
    this.baseBranchList = baseBranchList;
  }
  
  public String getDesignation() {
    return designation;
  }
  
  public void setDesignation(String designation) {
    this.designation = designation;
  }
  
  public List<SelectItem> getDesignationList() {
    return designationList;
  }
  
  public void setDesignationList(List<SelectItem> designationList) {
    this.designationList = designationList;
  }
  
  public String getRetirementDate() {
    return retirementDate;
  }
  
  public void setRetirementDate(String retirementDate) {
    this.retirementDate = retirementDate;
  }
  
  public String getUanNo() {
    return uanNo;
  }
  
  public void setUanNo(String uanNo) {
    this.uanNo = uanNo;
  }
}