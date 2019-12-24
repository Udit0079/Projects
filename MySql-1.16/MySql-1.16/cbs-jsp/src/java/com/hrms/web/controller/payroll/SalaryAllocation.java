/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.EmployeeDetailGrid;
import com.hrms.web.pojo.EmpSalaryAllocationGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.EmpAllocationGridPojo;
import com.hrms.common.to.EmpSalaryAllocationGridTO;
import com.hrms.common.to.GetSalaryAllocationTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.SalaryAllocationTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollOtherMgmFacadeRemote;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class SalaryAllocation extends BaseBean {

    private static final Logger logger = Logger.getLogger(SalaryAllocation.class);
    private String message;
    private int compCode;
    private String user;
    private String todayDt;
    private String allocationCategory;
    private String categorizationDetails;
    private Date salaryAllocationDate;
    private List<EmpSalaryAllocationGrid> selectedSlabList;
    private String basicSalary;
    private String component;
    private String amount;
    private PayrollOtherMgmFacadeRemote payrollothrMgmRemote;
    private boolean disableCategorizeDetails;
    private String employeeWise = "false";
    private String empSearchCriteria;
    private String empSearchValue;
    private List<EmpSalaryAllocationGrid> allSlabListItem;
    private boolean disableComponent;
    private boolean disableAmount;
    private String function;
    private String focusId;
    private int popupOpen;
    private EmployeeDetailGrid currentItem;
    private List<SelectItem> slablist;
    private List<SelectItem> functionList;
    private List<SelectItem> allocationCategoryList;
    private List<SelectItem> categorizationDetailsList;
    private String slabNumber;
    private List<EmployeeDetailGrid> empSearchTable;
    private List<EmpSalaryAllocationGrid> salaryAllocationGrid;
    private List<EmpSalaryAllocationGridTO> salaryAddGrid;
    private EmpSalaryAllocationGrid salryAllocCurrentItem;
    private EmpSalaryAllocationGridTO salaryAddCurrentItem;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private String gridSalaryAllocation = "false";
    private String categorywise = "false";
    private String mode = "SAVE";
    private boolean disableSave, disableDelete;
    private PayrollTransactionsFacadeRemote payrollRemote;
    private String errorFlag;
    List<EmpAllocationGridPojo> empAllocationGridPojoData;
    List list;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String allocationnDate;
    private List slabListNumberBeforeUpdate;
    List<EmpAllocationGridPojo> allocatedSlabBeforeUpdate;

    /**
     * Creates a new instance of SalaryAllocation
     */
    public SalaryAllocation() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            payrollothrMgmRemote = (PayrollOtherMgmFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollOtherMgmFacade");
            disableAmount = true;
            disableComponent = true;
            disableDelete = true;
            disableSave = false;
            compCode = Integer.parseInt(getOrgnBrCode());
            slablist = new ArrayList<SelectItem>();
            empAllocationGridPojoData = new ArrayList<EmpAllocationGridPojo>();
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("1", "ADD"));
            //functionList.add(new SelectItem("2", "VIEW"));
            functionList.add(new SelectItem("3", "UPDATE"));
            salaryAddGrid = new ArrayList<EmpSalaryAllocationGridTO>();
            allocatedSlabBeforeUpdate = new ArrayList<EmpAllocationGridPojo>();
            //getFinancialYear();
            getInitalData();
            setTodayDt(getTodayDate());
            setUser(getUserName());
        } catch (Exception e) {
            logger.error("Exception occured while executing method SalaryAllocation()", e);
            logger.error("SalaryAllocation()", e);
        }
    }


    /**
     * Get the initial data for allocation category list box
     */
    public void getInitalData() {
        try {
            allocationCategoryList = new ArrayList<SelectItem>();
            allocationCategoryList.add(new SelectItem("0", "--SELECT--"));
            allocationCategoryList.add(new SelectItem("EWE", "EMPLOYEE WISE"));

            categorizationDetailsList = new ArrayList<SelectItem>();
            categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));

            selectedSlabList = new ArrayList<EmpSalaryAllocationGrid>();
            slabListNumberBeforeUpdate = new ArrayList();

        } catch (Exception e) {
            logger.error("Exception occured while executing method getInitalData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onChangeFunction() {
        if (function.equals("0")) {
            setFocusId("optionList");
            setMessage("Please select an function");
            return;
        } else if (function.equals("1")) {
            setMessage("");
            mode = "SAVE";
            setFocusId("SalaryAllocationlist");
        } else if (function.equals("2")) {
            setMessage("");
            mode = "UPDATE";
            setFocusId("SalaryAllocationlist");
        }
    }

    /**
     * function executes on change of allocation category option
     */
    public void onChangeCategory() {
        try {
              categorizationDetailsList = new ArrayList<SelectItem>();
              categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
            if (allocationCategory.equalsIgnoreCase("EWE")){
                employeeWise = "true";
                disableCategorizeDetails = true;
                if (getFunction().equals("1")) {
                    setFocusId("categorizationList");
                    setPopupOpen(1);
                } else if (function.equals("2")) {
                    setPopupOpen(1);
                    setFocusId("basicSalaryText");
                } else if (getFunction().equals("3")) {
                    setFocusId("categorizationList");
                    setPopupOpen(1);
                }
                this.setMessage("");
            } else if (allocationCategory.equalsIgnoreCase("0")) {
                setPopupOpen(0);
                employeeWise = "false";
                setFocusId("SalaryAllocationlist");
                disableCategorizeDetails = false;
                this.setMessage("Please Select Categorization !!");
            } else {
                setPopupOpen(0);
                disableCategorizeDetails = false;
                employeeWise = "false";
                setFocusId("categorizationList");
                PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
                String structCode = this.allocationCategory + "%";

                List<HrMstStructTO> hrMstStructTOs = masterManagementDelegate.getInitialData(compCode, structCode);
                for (HrMstStructTO hrMststruct : hrMstStructTOs) {
                    categorizationDetailsList.add(new SelectItem(hrMststruct.getHrMstStructPKTO().getStructCode(), hrMststruct.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method onChangeSalaryAllocation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method onChangeSalaryAllocation()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method onChangeSalaryAllocation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Get the employee information on selecting the employee wise category
     */
    public void getEmployeeTableDataEmloyeeWise() {
        try {
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            empSearchTable = new ArrayList<>();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollDelegate.getHrPersonnelData(compCode, empSearchCriteria, empSearchValue);
            EmployeeDetailGrid currentEmpRow;
            for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                currentEmpRow = new EmployeeDetailGrid();
                currentEmpRow.setEmpId(hrPersonnelDetailsTO.getEmpId());
                currentEmpRow.setEmpName(hrPersonnelDetailsTO.getEmpName());
                currentEmpRow.setEmpAddress(hrPersonnelDetailsTO.getEmpContAdd());
                currentEmpRow.setEmpPhone(hrPersonnelDetailsTO.getEmpContTel());
                empSearchTable.add(currentEmpRow);
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Displays the grid for salary allocation on selecting employee wise
     */
    public void getSalaryAllocationGridToEmployees() {
        try {
            if (!getFunction().equals("2")) {
                return;
            }
            if (allocationCategory.equalsIgnoreCase("0")) {
                setMessage("Select the allocation category.");
                return;
            }
            if (employeeWise.equalsIgnoreCase("false") && categorizationDetails.equalsIgnoreCase("0")) {
                setMessage("Select the categorization details");
                return;
            }
            salaryAllocationGrid = new ArrayList<>();
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            GetSalaryAllocationTO getSalaryAllocationTO = new GetSalaryAllocationTO();
//            getSalaryAllocationTO.setCalDateFrom(yearFromDate);
//            getSalaryAllocationTO.setCalDateTo(yearToDate);
            getSalaryAllocationTO.setCompCode(compCode);
            if (employeeWise.equalsIgnoreCase("true")) {
                getSalaryAllocationTO.setSelectedValues(currentItem.getEmpId());
            } else {
                getSalaryAllocationTO.setSelectedValues(categorizationDetails);
            }
            getSalaryAllocationTO.setSelectionCriteria(allocationCategory);
            List<EmpSalaryAllocationGridTO> empsalaryAllocationGridTO = payrollDelegate.getSalaryAllocationForEmp(getSalaryAllocationTO);
            for (EmpSalaryAllocationGridTO hrSalaryAllocationGridTo : empsalaryAllocationGridTO) {
                EmpSalaryAllocationGrid empSalaryAllocationGrid = new EmpSalaryAllocationGrid();
                empSalaryAllocationGrid.setAllocationDate(hrSalaryAllocationGridTo.getAllocationDate());
                empSalaryAllocationGrid.setAmount(String.valueOf(hrSalaryAllocationGridTo.getAmount()));
                empSalaryAllocationGrid.setComponent(hrSalaryAllocationGridTo.getComponent());
                empSalaryAllocationGrid.setEmployeeName(hrSalaryAllocationGridTo.getEmployeeName());
                empSalaryAllocationGrid.setEmpid(hrSalaryAllocationGridTo.getEmpid());
//              empSalaryAllocationGrid.setMonths(hrSalaryAllocationGridTo.getMonths());
                empSalaryAllocationGrid.setBasicSalary(hrSalaryAllocationGridTo.getBasicSalary());
                salaryAllocationGrid.add(empSalaryAllocationGrid);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getEmpSalAllocByEmpWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getEmpSalAllocByEmpWise()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmpSalAllocByEmpWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        gridSalaryAllocation = "true";
    }

    public void empSalaryAllocationDataSet() {
        try {
            setSalaryAllocationDate(formatter.parse(salryAllocCurrentItem.getAllocationDate()));
            setAmount(salryAllocCurrentItem.getAmount());
            setComponent(salryAllocCurrentItem.getComponent());
            setBasicSalary(salryAllocCurrentItem.getBasicSalary());
            disableComponent = true;
            disableAmount = false;
            disableDelete = false;
            disableSave = false;
        } catch (Exception e) {
            logger.error("Exception occured while executing method empSalaryAllocationDataSet()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setEmpRowValues() {
        try {
            setMessage("");
            categorizationDetailsList.clear();
            categorizationDetailsList.add(new SelectItem(currentItem.getEmpId(), currentItem.getEmpName()));

            if (getFunction().equals("2")) {
                getSalaryAllocationGridToEmployees();
            }


            if (getFunction().equals("1")) {
                List reList = payrollothrMgmRemote.getDataFromEmployeeId(currentItem.getEmpId());
                allSlabListItem = new ArrayList<EmpSalaryAllocationGrid>();

   //             for (int i = 0; i < reList.size(); i++) {
   //                 Vector vtr = (Vector) reList.get(i);
   //                 EmpSalaryAllocationGrid pojo = new EmpSalaryAllocationGrid();
  //                  pojo.setSlabNumber(vtr.get(0).toString());
  //                  pojo.setBaseComponent(vtr.get(1).toString());
  //                  pojo.setDependentComponent(vtr.get(2).toString());
  //                  pojo.setSlabCriteria(vtr.get(3).toString());
  //                  pojo.setSalarySlabId(vtr.get(4).toString());
  //                  pojo.setEmpcode(vtr.get(5).toString());
  //                  allSlabListItem.add(pojo);
    //            }


            for (Object obj : reList) {
                EmpSalaryAllocationGrid pojo = new EmpSalaryAllocationGrid(); 
                pojo.setSlabNumber(String.valueOf(((Object[]) obj)[0]));
                pojo.setBaseComponent(String.valueOf(((Object[]) obj)[1]));
                pojo.setDependentComponent(String.valueOf(((Object[]) obj)[2]));
                pojo.setSlabCriteria(String.valueOf(((Object[]) obj)[3]));
                pojo.setSalarySlabId(String.valueOf(((Object[]) obj)[4]));
                pojo.setEmpcode(String.valueOf(((Object[]) obj)[5]));
                pojo.setSlabCriteriaAmt(String.valueOf(((Object[]) obj)[6]));
                pojo.setMaxCriteriaAmt(String.valueOf(((Object[]) obj)[7]));
                pojo.setMinCriteriaAmt(String.valueOf(((Object[]) obj)[8]));
                
          
                allSlabListItem.add(pojo);
            }

            }
            if (getFunction().equals("3")) {
                 // added right now
                slabListNumberBeforeUpdate.clear();
                allocatedSlabBeforeUpdate = payrollothrMgmRemote.getPreviousSalAllocDetails(currentItem.getEmpId());
                this.setBasicSalary(allocatedSlabBeforeUpdate.get(0).getBasicSalary());
                this.setAllocationnDate(dmyFormat.format(dmyFormat.parse(allocatedSlabBeforeUpdate.get(0).getAllocationDate())));
       
                for (int i = 0; i < allocatedSlabBeforeUpdate.size(); i++) {
                    slabListNumberBeforeUpdate.add(allocatedSlabBeforeUpdate.get(i).getSlabCode().toString());
                }
              List  reList = payrollothrMgmRemote.getDataFromEmployeeId(currentItem.getEmpId());
                allSlabListItem = new ArrayList<EmpSalaryAllocationGrid>();

                for (Object obj : reList) {

                    EmpSalaryAllocationGrid pojo = new EmpSalaryAllocationGrid();
                    pojo.setSlabNumber(String.valueOf(((Object[]) obj)[0]));
                    pojo.setBaseComponent(String.valueOf(((Object[]) obj)[1]));
                    pojo.setDependentComponent(String.valueOf(((Object[]) obj)[2]));
                    pojo.setSlabCriteria(String.valueOf(((Object[]) obj)[3]));
                    pojo.setSalarySlabId(String.valueOf(((Object[]) obj)[4]));
                    pojo.setEmpcode(String.valueOf(((Object[]) obj)[5])); 
                    pojo.setSlabCriteriaAmt(String.valueOf(((Object[]) obj)[6]));
                    pojo.setMaxCriteriaAmt(String.valueOf(((Object[]) obj)[7]));
                    pojo.setMinCriteriaAmt(String.valueOf(((Object[]) obj)[8]));
              
                    if (slabListNumberBeforeUpdate.contains(String.valueOf(((Object[]) obj)[0]))) {
                        pojo.setSelected(true);
                        pojo.setSlabNumber(String.valueOf(((Object[]) obj)[0]));
                        pojo.setBaseComponent(String.valueOf(((Object[]) obj)[1]));
                        pojo.setDependentComponent(String.valueOf(((Object[]) obj)[2]));
                        pojo.setSlabCriteria(String.valueOf(((Object[]) obj)[3]));
                        pojo.setSalarySlabId(String.valueOf(((Object[]) obj)[4]));
                        pojo.setEmpcode(String.valueOf(((Object[]) obj)[5]));
                        pojo.setSlabCriteriaAmt(String.valueOf(((Object[]) obj)[6]));
                        pojo.setMaxCriteriaAmt(String.valueOf(((Object[]) obj)[7]));
                        pojo.setMinCriteriaAmt(String.valueOf(((Object[]) obj)[8]));
                        selectedSlabList.add(pojo);
                    }
                    allSlabListItem.add(pojo);
                }

            }
        } catch (Exception e) {
            this.setMessage("didn't update");
        }
    }

    public void finalSelectedSlabList() {

        if (salryAllocCurrentItem.isSelected() && !selectedSlabList.contains(salryAllocCurrentItem)) {
            selectedSlabList.add(salryAllocCurrentItem);
        } else {
            if (!salryAllocCurrentItem.isSelected() && !(selectedSlabList.isEmpty())) {
                selectedSlabList.remove(salryAllocCurrentItem);
            }
        }
    }

    public void getDetailsOfSelectedSlabNbr() {
        //      empAllocationGridPojoData = payrollothrMgmRemote.getDataFromSlabNumber(list);
    }

    public void saveSalaryAllocation() {
        String result = "";
        if (getFunction().equals("1")) {
            try {
                String valResult = validations();

                if (!valResult.equalsIgnoreCase("true")) {
                    setMessage(valResult);
                    return;
                }
                if(!this.getMessage().equalsIgnoreCase("")){
                    setMessage(this.getMessage());
                    return;
                }
                
                list = new ArrayList();
                for (int i = 0; i < selectedSlabList.size(); i++) {
                    list.add(selectedSlabList.get(i).getSlabNumber().toString());
                }

                SalaryAllocationTO salaryAllocationTO = new SalaryAllocationTO();
                salaryAllocationTO.setBasicSalary(getBasicSalary());
                salaryAllocationTO.setAllocationDate(ymdFormat.format(dmyFormat.parse(this.allocationnDate)));
                salaryAllocationTO.setAuthBy(getUser());
                salaryAllocationTO.setEmpCode(allSlabListItem.get(0).getEmpcode());
                salaryAllocationTO.setModificationDate(ymdFormat.format(dmyFormat.parse(getTodayDate())));
                salaryAllocationTO.setCompanyCode(compCode);
                salaryAllocationTO.setDefaultComp(compCode);
                salaryAllocationTO.setEnteredBy(getUser());
                salaryAllocationTO.setModeFlag(mode);
                if (employeeWise.equalsIgnoreCase("true")) {
                    salaryAllocationTO.setSelectedValues(currentItem.getEmpId());
                } else {
                    salaryAllocationTO.setSelectedValues(categorizationDetails);
                }
                salaryAllocationTO.setSelectionCriteria(allocationCategory);
                salaryAllocationTO.setStatFlag('Y');
                salaryAllocationTO.setStatUpFlag('Y');
                if (getFunction().equalsIgnoreCase("2")) {
//                salaryAllocationTO.setUpdateMonth(salryAllocCurrentItem.getMonths());
                    salaryAllocationTO.setUpdatedComponent(getComponent());
                    salaryAllocationTO.setUpdatedComponentAmount(getAmount());
                } else {
//                salaryAllocationTO.setUpdateMonth("new");
                    salaryAllocationTO.setUpdatedComponent("extra");
                    salaryAllocationTO.setUpdatedComponentAmount("0.0");
                }

                result = payrollothrMgmRemote.saveSalAllocationDetails(salaryAllocationTO, list);
                refresh();
                if (result.equalsIgnoreCase("Already In Database")) {
                    this.setMessage(result);
                } else {
                    this.setMessage("Data is saved successfully!!");
                }
            } catch (Exception e) {
                logger.error("Exception occured while executing method saveSalaryAllocation()", e);
                this.setMessage(result);
            }
        } else if (getFunction().equals("3")) {
            try {
                 try {
                    String valResult = validations();
            //        getSalaryStruct();
                    if (!valResult.equalsIgnoreCase("true")) {
                        setMessage(valResult);
                        return;
                    }
                    
                    if(!this.getMessage().equalsIgnoreCase("")){
                        setMessage(this.getMessage());
                        return;
                }
                    
                allocatedSlabBeforeUpdate = payrollothrMgmRemote.getPreviousSalAllocDetails(currentItem.getEmpId());
                payrollothrMgmRemote.saveSalAllocationDetailsInHis(allocatedSlabBeforeUpdate,getTodayDt(),getUser());
                payrollothrMgmRemote.deleteOldSalAllocationDetails(allocatedSlabBeforeUpdate);
               
                    list = new ArrayList();
                    for (int i = 0; i < selectedSlabList.size(); i++) {
                        list.add(selectedSlabList.get(i).getSlabNumber().toString());
                    }

                    SalaryAllocationTO salaryAllocationTO = new SalaryAllocationTO();
                    salaryAllocationTO.setBasicSalary(getBasicSalary());
                    salaryAllocationTO.setAllocationDate(ymdFormat.format(dmyFormat.parse(this.allocationnDate)));
                    salaryAllocationTO.setAuthBy(getUser());
                    salaryAllocationTO.setEmpCode(allSlabListItem.get(0).getEmpcode());
                    salaryAllocationTO.setModificationDate(ymdFormat.format(dmyFormat.parse(getTodayDate())));
                    salaryAllocationTO.setCompanyCode(compCode);
                    salaryAllocationTO.setDefaultComp(compCode);
                    salaryAllocationTO.setEnteredBy(getUser());
                    salaryAllocationTO.setModeFlag(mode);
                    if (employeeWise.equalsIgnoreCase("true")) {
                        salaryAllocationTO.setSelectedValues(currentItem.getEmpId());
                    } else {
                        salaryAllocationTO.setSelectedValues(categorizationDetails);
                    }
                    salaryAllocationTO.setSelectionCriteria(allocationCategory);
                    salaryAllocationTO.setStatFlag('Y');
                    salaryAllocationTO.setStatUpFlag('Y');
                    if (getFunction().equalsIgnoreCase("2")) {
//                salaryAllocationTO.setUpdateMonth(salryAllocCurrentItem.getMonths());
                        salaryAllocationTO.setUpdatedComponent(getComponent());
                        salaryAllocationTO.setUpdatedComponentAmount(getAmount());
                    } else {
//                salaryAllocationTO.setUpdateMonth("new");
                        salaryAllocationTO.setUpdatedComponent("extra");
                        salaryAllocationTO.setUpdatedComponentAmount("0.0");
                    }

                    result = payrollothrMgmRemote.saveSalAllocationDetails(salaryAllocationTO, list);
                   
                    refresh();
                    this.setMessage("Data is saved successfully!!");

                } catch (Exception e) {
                    logger.error("Exception occured while executing method saveSalaryAllocation()", e);
                    this.setMessage(e.getMessage());
                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(SalaryAllocation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void deleteSalaryAllocation() {
        mode = "DELETE";
        saveSalaryAllocation();
    }

    public String validations() {
        try {
            if (allocationCategory.equalsIgnoreCase("0")) {
                return "Select the allocation category option!!!";
            }
            if (disableCategorizeDetails == false) {
                if (categorizationDetails.equalsIgnoreCase("0")) {
                    return "Select the categorization details option";
                }
            }
            if (getBasicSalary().equalsIgnoreCase("")) {
                return "Basic salary field cannot be empty!!!";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher basicSalaryAmount = p.matcher(this.getBasicSalary());
            if (!basicSalaryAmount.matches()) {
                return "Enter Valid numeric value for basic salary amount!!!";
            }
            if (Double.parseDouble(getBasicSalary()) < 0) {
                return "Basic salary amount cannot be negative!!!";
            }
            if (this.allocationnDate.equalsIgnoreCase("")) {
                return "Fill the salary allocation date!!!";
            }
            if (disableAmount == false) {
                if (getAmount().equalsIgnoreCase("")) {
                    return "Fill the componenet amount field!!!";
                }
                Matcher amount = p.matcher(this.getAmount());
                if (!amount.matches()) {
                    return "Enter Valid numeric value for amount!!!";
                }
                if (Double.parseDouble(getAmount()) < 0) {
                    return "Component amount cannot be negative!!!";
                }
            }
            if (getFunction().equalsIgnoreCase("1")) {
                if (selectedSlabList.isEmpty()) {
                    return "No record exist to save!!!";
                }
            }
        } catch (NumberFormatException e) {
            return "Check the numeric value!!!";
        } catch (Exception e) {
            logger.error("Exception occured while executing method validations()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "true";
    }

    public void refresh() {
        setAllocationCategory("--SELECT--");
        categorizationDetailsList.clear();
        setBasicSalary("");
        setCategorizationDetails("0");
        setComponent("");
        setDisableAmount(true);
        setDisableComponent(true);
        setAllocationnDate(null);
        selectedSlabList.clear();
        allSlabListItem.clear();
        setEmpSearchValue("");
        setMessage("");
        setFunction("0");
        disableAmount = true;
        disableComponent = true;
        disableDelete = true;
        disableSave = false;
        mode = "SAVE";

    }

    ;

    public void getSalaryStruct() {
        try {
            if (getFunction().equalsIgnoreCase("1") && (allocationCategory.equalsIgnoreCase("EWE"))) {
                if (!getBasicSalary().equalsIgnoreCase("")) {
                    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                    Matcher basicSalaryAmount = p.matcher(this.getBasicSalary());
                    if (!basicSalaryAmount.matches()) {
                        this.setMessage("Enter Valid numeric value for basic salary amount!!!");
                        return;
                    }
                    if (Double.parseDouble(getBasicSalary()) < 0) {
                        this.setMessage("Basic salary amount cannot be negative!!!");
                        return;
                    }

                    String s = payrollothrMgmRemote.getRangeOfBasicSalary(allSlabListItem.get(0).getSalarySlabId(), getBasicSalary());
                    //         if (!(s.equalsIgnoreCase(""))) {
                    //             this.setMessage(s);
                    //             return ;
                    //         } 
                    if (s.equalsIgnoreCase("Invalid Basic Salary")) {
                        //      refresh();
                        this.setMessage("Invalid Basic Salary");

                    }
                    if (s.equalsIgnoreCase("")) {
                        //      refresh();
                        this.setMessage("");

                    }
                    //                 salaryAddGrid = new ArrayList<EmpSalaryAllocationGridTO>();

                    //      PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
                    //  List<EmpSalaryAllocationGridTO> empSalaryAllocationGridTO = payrollDelegate.getSalaryBreakUp(Double.parseDouble(getBasicSalary()), compCode, currentItem.getEmpId());
                    //  List<EmpSalaryAllocationGridTO> empSalaryAllocationGridTO =empAllocationGridPojoData;
                    //
                    //          for (EmpAllocationGridPojo empAllocGridPojoObj : empAllocationGridPojoData) {
                    //              EmpSalaryAllocationGridTO empSalaryAllocationGrid = new EmpSalaryAllocationGridTO();
                    //              empSalaryAllocationGrid.setCompCode(empAllocGridPojoObj.getCompCode());
                    //               empSalaryAllocationGrid.setBaseComponent(empAllocGridPojoObj.getBaseComponent());
                    //               empSalaryAllocationGrid.setDependentComponent(empAllocGridPojoObj.getDependentComponent());                    
                    //                empSalaryAllocationGrid.setSalarySlabId(empAllocGridPojoObj.getSalarySlabId());
                    //                empSalaryAllocationGrid.setSlabCriteria(empAllocGridPojoObj.getSlabCriteria());
                    //                 empSalaryAllocationGrid.setComponentOrder(empAllocGridPojoObj.getComponentOrder());

                    //                 salaryAddGrid.add(empSalaryAllocationGrid);
                    // i = i + 1;
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method validations()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void gridAmtAction() {
        errorFlag = "true";
        String amtVal = salaryAddCurrentItem.getPassAmt();

        if (validateAmount(amtVal) != true) {
            return;
        }

        try {
            if (salaryAddCurrentItem != null) {
                if (Double.parseDouble(salaryAddCurrentItem.getAmount().toString()) < Double.parseDouble(salaryAddCurrentItem.getPassAmt())) {
                    this.setMessage("Please Fill Pass Amount Less Than Component Amount Field.");
                    errorFlag = "false";
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validateAmount(String amtVal) {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (amtVal == null || amtVal.equalsIgnoreCase("")) {
            this.setMessage("Please Fill Amount Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(amtVal);
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid Entry.");
                return false;
            }
        }

        if (amtVal.contains(".")) {
            if (amtVal.indexOf(".") != amtVal.lastIndexOf(".")) {
                this.setMessage("Invalid Amount.Please Fill The Amount Correctly.");
                return false;
            } else if (amtVal.substring(amtVal.indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }

    public String btnExitAction() {
        return "case1";
    }

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public String getCategorywise() {
        return categorywise;
    }

    public void setCategorywise(String categorywise) {
        this.categorywise = categorywise;
    }

    public String getGridSalaryAllocation() {
        return gridSalaryAllocation;
    }

    public void setGridSalaryAllocation(String gridSalaryAllocation) {
        this.gridSalaryAllocation = gridSalaryAllocation;
    }

    public EmpSalaryAllocationGrid getSalryAllocCurrentItem() {
        return salryAllocCurrentItem;
    }

    public void setSalryAllocCurrentItem(EmpSalaryAllocationGrid salryAllocCurrentItem) {
        this.salryAllocCurrentItem = salryAllocCurrentItem;
    }

    public List<EmpSalaryAllocationGrid> getSalaryAllocationGrid() {
        return salaryAllocationGrid;
    }

    public void setSalaryAllocationGrid(List<EmpSalaryAllocationGrid> salaryAllocationGrid) {
        this.salaryAllocationGrid = salaryAllocationGrid;
    }

    public boolean isDisableAmount() {
        return disableAmount;
    }

    public void setDisableAmount(boolean disableAmount) {
        this.disableAmount = disableAmount;
    }

    public boolean isDisableComponent() {
        return disableComponent;
    }

    public void setDisableComponent(boolean disableComponent) {
        this.disableComponent = disableComponent;
    }

    public EmployeeDetailGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EmployeeDetailGrid currentItem) {
        this.currentItem = currentItem;
    }

    public String getEmpSearchValue() {
        return empSearchValue;
    }

    public void setEmpSearchValue(String empSearchValue) {
        this.empSearchValue = empSearchValue;
    }

    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }

    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
    }

    public List<EmployeeDetailGrid> getEmpSearchTable() {
        return empSearchTable;
    }

    public void setEmpSearchTable(List<EmployeeDetailGrid> empSearchTable) {
        this.empSearchTable = empSearchTable;
    }

    public boolean isDisableCategorizeDetails() {
        return disableCategorizeDetails;
    }

    public void setDisableCategorizeDetails(boolean disableCategorizeDetails) {
        this.disableCategorizeDetails = disableCategorizeDetails;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String Amount) {
        this.amount = Amount;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getAllocationCategory() {
        return allocationCategory;
    }

    public void setAllocationCategory(String allocationCategory) {
        this.allocationCategory = allocationCategory;
    }

    public List<SelectItem> getAllocationCategoryList() {
        return allocationCategoryList;
    }

    public void setAllocationCategoryList(List<SelectItem> allocationCategoryList) {
        this.allocationCategoryList = allocationCategoryList;
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Date getSalaryAllocationDate() {
        return salaryAllocationDate;
    }

    public void setSalaryAllocationDate(Date salaryAllocationDate) {
        this.salaryAllocationDate = salaryAllocationDate;
    }

    public String getCategorizationDetails() {
        return categorizationDetails;
    }

    public void setCategorizationDetails(String categorizationDetails) {
        this.categorizationDetails = categorizationDetails;
    }

    public List<SelectItem> getCategorizationDetailsList() {
        return categorizationDetailsList;
    }

    public void setCategorizationDetailsList(List<SelectItem> categorizationDetailsList) {
        this.categorizationDetailsList = categorizationDetailsList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public int getPopupOpen() {
        return popupOpen;
    }

    public void setPopupOpen(int popupOpen) {
        this.popupOpen = popupOpen;
    }

    public List<EmpSalaryAllocationGridTO> getSalaryAddGrid() {
        return salaryAddGrid;
    }

    public void setSalaryAddGrid(List<EmpSalaryAllocationGridTO> salaryAddGrid) {
        this.salaryAddGrid = salaryAddGrid;
    }

    public EmpSalaryAllocationGridTO getSalaryAddCurrentItem() {
        return salaryAddCurrentItem;
    }

    public void setSalaryAddCurrentItem(EmpSalaryAllocationGridTO salaryAddCurrentItem) {
        this.salaryAddCurrentItem = salaryAddCurrentItem;
    }

    public String getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(String errorFlag) {
        this.errorFlag = errorFlag;
    }

    public List<SelectItem> getSlablist() {
        return slablist;
    }

    public void setSlablist(List<SelectItem> slablist) {
        this.slablist = slablist;
    }

    public String getSlabNumber() {
        return slabNumber;
    }

    public void setSlabNumber(String slabNumber) {
        this.slabNumber = slabNumber;
    }

    public List<EmpSalaryAllocationGrid> getSelectedSlabList() {
        return selectedSlabList;
    }

    public void setSelectedSlabList(List<EmpSalaryAllocationGrid> selectedSlabList) {
        this.selectedSlabList = selectedSlabList;
    }

    public List<EmpSalaryAllocationGrid> getAllSlabListItem() {
        return allSlabListItem;
    }

    public void setAllSlabListItem(List<EmpSalaryAllocationGrid> allSlabListItem) {
        this.allSlabListItem = allSlabListItem;
    }

    public List<EmpAllocationGridPojo> getEmpAllocationGridPojoData() {
        return empAllocationGridPojoData;
    }

    public void setEmpAllocationGridPojoData(List<EmpAllocationGridPojo> empAllocationGridPojoData) {
        this.empAllocationGridPojoData = empAllocationGridPojoData;
    }

    public String getAllocationnDate() {
        return allocationnDate;
    }

    public void setAllocationnDate(String allocationnDate) {
        this.allocationnDate = allocationnDate;
    }

    public List<EmpAllocationGridPojo> getAllocatedSlabBeforeUpdate() {
        return allocatedSlabBeforeUpdate;
    }

    public void setAllocatedSlabBeforeUpdate(List<EmpAllocationGridPojo> allocatedSlabBeforeUpdate) {
        this.allocatedSlabBeforeUpdate = allocatedSlabBeforeUpdate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTodayDt() {
        return todayDt;
    }

    public void setTodayDt(String todayDt) {
        this.todayDt = todayDt;
    }
     
     
    
}