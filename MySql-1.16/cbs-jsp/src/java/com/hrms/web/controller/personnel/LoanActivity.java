package com.hrms.web.controller.personnel;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;

import com.hrms.common.to.HrEmpLoanDtPKTO;
import com.hrms.common.to.HrEmpLoanDtTO;
import com.hrms.common.to.HrEmpLoanHdPKTO;
import com.hrms.common.to.HrEmpLoanHdTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmployeeGrid;
import com.hrms.web.pojo.InstallmentGrid;
import com.hrms.web.pojo.LoanGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

public class LoanActivity extends BaseBean{

    private String message,
            enteredBy,
            authBy,
            statFlag,
            statUpFlag,
            mode,
            empSearchCriteria,
            empSearchValue,
            empId,
            empName,
            deptCode,
            desgCode,
            gradeCode,
            department,
            designation,
            grade,
            loanPlan,
            loanType,
            sanctionAmount,
            ROI,
            noOfInstallment,
            periodicity;
    private int defaultComp,
            compCode;
    private List<CompanyMasterTO> companyMasterTOs;
    private List<EmployeeGrid> empSearchTable;
    private EmployeeGrid currentEmpItem;
    private List<InstallmentGrid> table;
    private List<LoanGrid> loanTable;
    private LoanGrid currentLoanItem;
    private long empCode,
            empLoanNo;
    private double basicSalary;
    private WebUtil webUtil = new WebUtil();
    private PersonnelUtil personnelUtil = new PersonnelUtil();
    private Validator validator = new Validator();
    private List<SelectItem> loansList,
            periodicityList,
            loanPlanList;
    private Date sanctionDate,
            startingDate;
    private static final Logger logger = Logger.getLogger(LoanActivity.class);
    private PersonnelDelegate personnelDelegate;
    Calendar cal;
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy"),
            ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private boolean disableSaveButton,
            disableDeleteButton,
            disableViewButton, flag1;
     private String operation;
    private List<SelectItem> operationList;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<SelectItem> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<SelectItem> operationList) {
        this.operationList = operationList;
    }
    
    

    public LoanActivity() {
        try {
            personnelDelegate = new PersonnelDelegate();
            Date date = new Date();
            cal = new GregorianCalendar();
            cal.setTime(date);
            compCode = Integer.parseInt(getOrgnBrCode());
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","View"));
            enteredBy = getUserName();
            statFlag = "Y";
            statUpFlag = "Y";
            getLoansListDetail();
            getPeriodicityListDetail();
            getLoanPlanListDetail();
            onLoad();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method LoanActivity()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method LoanActivity()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    private void onLoad() {
        try {
            sanctionDate = dmyFormat.parse(getTodayDate());
            startingDate = dmyFormat.parse(getTodayDate());
            mode = "save";
            authBy = "";
            message = "";
            setDisableSaveButton(true);
            setDisableDeleteButton(true);
            setDisableViewButton(false);
        } catch (Exception e) {
            logger.error("Exception occured while executing method onLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelAction() {
        try {
            onLoad();
            setEmpId("");
            setEmpName("");
            setDepartment("");
            setDesignation("");
            setGrade("");
            setLoanType("--Select--");
            setSanctionAmount("");
            setROI("");
            setNoOfInstallment("");
            setLoanPlan("--Select--");
            setPeriodicity("--Select--");
            setEmpLoanNo(0);
            setBasicSalary(0.00);
            setEmpSearchCriteria("");
            setOperation("0");
            empSearchTable = new ArrayList<EmployeeGrid>();
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveAction() {
        try {
            message = "";
            if (mode.equalsIgnoreCase("save")) {
                if (!empId.equalsIgnoreCase("")) {
                    empLoanNo = personnelDelegate.getMaxLoanNoFromHrEmpLoanDt(compCode) + 1;
                    if (table == null) {
                        table = personnelUtil.calculateLoanInstallments(loanPlan, Double.parseDouble(sanctionAmount), Double.parseDouble(ROI), Integer.parseInt(noOfInstallment), periodicity.charAt(0), ymdFormat.format(startingDate));
                    }
                    int size = table.size() - 1;
                    HrEmpLoanHdTO hrEmpLoanHdTO = getHrEmpLoanHdTO(Double.parseDouble(sanctionAmount), table.get(size).getSno(), table.size(), table.get(size).getRepayFlag(), empLoanNo, dmyFormat.parse(table.get(size).getDueDate()), new Date());
                    List<HrEmpLoanDtTO> hrEmpLoanDtTOList = new ArrayList<HrEmpLoanDtTO>();
                    for (InstallmentGrid list : table) {
                        hrEmpLoanDtTOList.add(getHrEmpLoanDtTO(list.getPrincipal(), list.getInterestAmount(), list.getSno(), table.size(), list.getRepayFlag(), empLoanNo, dmyFormat.parse(list.getDueDate()), new Date()));
                    }
                    if (hrEmpLoanHdTO != null && hrEmpLoanDtTOList.size() == table.size()) {
                        String result = personnelDelegate.saveEmpLoanDetail(hrEmpLoanHdTO, hrEmpLoanDtTOList, mode);
                        cancelAction();
                        setMessage(result);
                    }
                } else {
                    setMessage("Please Select Atleast One Employee");
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    private HrEmpLoanHdTO getHrEmpLoanHdTO(double installment, int varInstall, int totInstall, char repayFlag, long empLoanNo, Date dueDate, Date modDate) {
        HrEmpLoanHdTO hrEmpLoanHdTO = new HrEmpLoanHdTO();
        HrEmpLoanHdPKTO hrEmpLoanHdPKTO = new HrEmpLoanHdPKTO();
        try {
            hrEmpLoanHdPKTO.setCompCode(compCode);
            hrEmpLoanHdPKTO.setEmpCode(empCode);
            hrEmpLoanHdPKTO.setLoanType(loanType);
            hrEmpLoanHdPKTO.setEmpLoanNo(empLoanNo);
            hrEmpLoanHdTO.setAuthBy(authBy);
            hrEmpLoanHdTO.setDefaultComp(defaultComp);
            hrEmpLoanHdTO.setEnteredBy(enteredBy);
            hrEmpLoanHdTO.setHrEmpLoanHdPKTO(hrEmpLoanHdPKTO);
            hrEmpLoanHdTO.setHrMstStructTO(this.getHrMstStructTO(compCode, loanType));
            hrEmpLoanHdTO.setHrPersonnelDetailsTO(this.getHrPersonnelDetailsTO(compCode, empCode));
            hrEmpLoanHdTO.setInstPlan(loanPlan);
            hrEmpLoanHdTO.setPeriodicity(periodicity.charAt(0));
            hrEmpLoanHdTO.setRepayFlag(repayFlag);
            hrEmpLoanHdTO.setRoi(Double.parseDouble(ROI));
            hrEmpLoanHdTO.setSanctionAmt(installment);
            hrEmpLoanHdTO.setSanctionDate(sanctionDate);
            hrEmpLoanHdTO.setStartDate(startingDate);
            hrEmpLoanHdTO.setModDate(modDate);
            hrEmpLoanHdTO.setNoInst(totInstall);
            hrEmpLoanHdTO.setStatFlag(statFlag);
            hrEmpLoanHdTO.setStatUpFlag(statUpFlag);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrEmpLoanHdTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrEmpLoanHdTO;
    }

    private HrEmpLoanDtTO getHrEmpLoanDtTO(double principal, double interestAmount, int varInstall, int totInstall, char repayFlag, long empLoanNo, Date dueDate, Date modDate) {
        HrEmpLoanDtTO hrEmpLoanDtTO = new HrEmpLoanDtTO();
        HrEmpLoanDtPKTO hrEmpLoanDtPKTO = new HrEmpLoanDtPKTO();
        try {
            hrEmpLoanDtPKTO.setCompCode(compCode);
            hrEmpLoanDtPKTO.setDueDate(dueDate);
            hrEmpLoanDtPKTO.setEmpCode(empCode);
            hrEmpLoanDtPKTO.setEmpLoanNo(empLoanNo);
            hrEmpLoanDtPKTO.setLoanType(loanType);
            hrEmpLoanDtTO.setAuthBy(getUserName());
            hrEmpLoanDtTO.setDefaultComp(defaultComp);
            hrEmpLoanDtTO.setEnteredBy(enteredBy);
            hrEmpLoanDtTO.setHrEmpLoanDtPKTO(hrEmpLoanDtPKTO);
            hrEmpLoanDtTO.setHrMstStructTO(getHrMstStructTO(compCode, loanType));
            hrEmpLoanDtTO.setHrPersonnelDetailsTO(getHrPersonnelDetailsTO(compCode, empCode));
            hrEmpLoanDtTO.setInterest(interestAmount);
            hrEmpLoanDtTO.setModDate(modDate);
            hrEmpLoanDtTO.setPrincipal(principal);
            hrEmpLoanDtTO.setRepayFlag(repayFlag);
            hrEmpLoanDtTO.setStatFlag(statFlag);
            hrEmpLoanDtTO.setStatUpFlag(statUpFlag);
            hrEmpLoanDtTO.setTotInstall(totInstall);
            hrEmpLoanDtTO.setVarInstall(varInstall);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrEmpLoanDtTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrEmpLoanDtTO;
    }

    public void deleteAction() {
        try {
            message = "";
            mode = "delete";
            int iPeriod = 0;
            if (periodicity.equalsIgnoreCase("M")) {
                iPeriod = 1;
            } else if (periodicity.equalsIgnoreCase("Q")) {
                iPeriod = 3;
            } else if (periodicity.equalsIgnoreCase("H")) {
                iPeriod = 6;
            } else if (periodicity.equalsIgnoreCase("Y")) {
                iPeriod = 12;
            }
            Date dueDate = startingDate;
            empLoanNo = currentLoanItem.getEmpLoanNo();
            List<HrEmpLoanDtTO> hrEmpLoanDtTOList = new ArrayList<HrEmpLoanDtTO>();
            for (int i = 0; i < Integer.parseInt(noOfInstallment); i++) {
                HrEmpLoanDtTO hrEmpLoanDtTO = new HrEmpLoanDtTO();
                HrEmpLoanDtPKTO hrEmpLoanDtPKTO = new HrEmpLoanDtPKTO();
                hrEmpLoanDtPKTO.setCompCode(compCode);
                hrEmpLoanDtPKTO.setDueDate(dueDate);
                hrEmpLoanDtPKTO.setEmpCode(empCode);
                hrEmpLoanDtPKTO.setEmpLoanNo(empLoanNo);
                hrEmpLoanDtPKTO.setLoanType(loanType);
                hrEmpLoanDtTO.setHrMstStructTO(getHrMstStructTO(compCode, loanType));
                hrEmpLoanDtTO.setHrPersonnelDetailsTO(getHrPersonnelDetailsTO(compCode, empCode));
                hrEmpLoanDtTO.setHrEmpLoanDtPKTO(hrEmpLoanDtPKTO);
                hrEmpLoanDtTOList.add(hrEmpLoanDtTO);
                dueDate = dmyFormat.parse(dmyFormat.format(ymdFormat.parse(HrmsUtil.monthAdd(ymdFormat.format(dueDate), iPeriod))));
            }
            HrEmpLoanHdTO hrEmpLoanHdTO = new HrEmpLoanHdTO();
            HrEmpLoanHdPKTO hrEmpLoanHdPKTO = new HrEmpLoanHdPKTO();
            hrEmpLoanHdPKTO.setCompCode(compCode);
            hrEmpLoanHdPKTO.setEmpCode(empCode);
            hrEmpLoanHdPKTO.setLoanType(loanType);
            hrEmpLoanHdPKTO.setEmpLoanNo(empLoanNo);
            hrEmpLoanHdTO.setHrEmpLoanHdPKTO(hrEmpLoanHdPKTO);
            hrEmpLoanHdTO.setHrMstStructTO(getHrMstStructTO(compCode, loanType));
            hrEmpLoanHdTO.setHrPersonnelDetailsTO(getHrPersonnelDetailsTO(compCode, empCode));
            String result = personnelDelegate.saveEmpLoanDetail(hrEmpLoanHdTO, hrEmpLoanDtTOList, mode);
            cancelAction();
            setMessage(result);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitAction() {
        try {
            cancelAction();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public void setEmpRowValues() {
        try {
            message = "";
            setEmpId(currentEmpItem.getEmpId());
            setEmpName(currentEmpItem.getEmpName());
            empCode = currentEmpItem.getEmpCode();
            deptCode = currentEmpItem.getDeptCode();
            desgCode = currentEmpItem.getDesgCode();
            gradeCode = currentEmpItem.getGradeCode();
            basicSalary = currentEmpItem.getBasicSalary();
            List<HrMstStructTO> hrMstStructTOs1 = personnelDelegate.getInitialData(compCode, deptCode);
            for (HrMstStructTO hrMstStructTO : hrMstStructTOs1) {
                department = hrMstStructTO.getDescription();
            }
            List<HrMstStructTO> hrMstStructTOs2 = personnelDelegate.getInitialData(compCode, desgCode);
            for (HrMstStructTO hrMstStructTO : hrMstStructTOs2) {
                designation = hrMstStructTO.getDescription();
            }
            List<HrMstStructTO> hrMstStructTOs3 = personnelDelegate.getInitialData(compCode, gradeCode);
            for (HrMstStructTO hrMstStructTO : hrMstStructTOs3) {
                grade = hrMstStructTO.getDescription();
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method setEmpRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method setEmpRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    private boolean validate() {
        try {
            setMessage("");
            if (empCode == 0) {
                flag1 = false;
                setMessage("Please Select An Employee !!");
                return false;
            }
            if (loanType.equalsIgnoreCase("0")) {
                flag1 = false;
                setMessage("Please Select Proper Loan Type !!");
                return false;
            }
            if (!validator.validateDoublePositive(sanctionAmount) || sanctionAmount.equalsIgnoreCase("") || sanctionAmount.equalsIgnoreCase("0.0")) {
                flag1 = false;
                setMessage("Please Fill Proper Sanction Amount !!");
                return false;
            }
            if (!validator.validateDoublePositive(ROI) || ROI.equalsIgnoreCase("") || ROI.equalsIgnoreCase("0.0")) {
                flag1 = false;
                setMessage("Please Fill Proper ROI !!");
                return false;
            }
            if (!validator.validateInteger(noOfInstallment) || noOfInstallment.equalsIgnoreCase("") || noOfInstallment.equalsIgnoreCase("0.0")) {
                flag1 = false;
                setMessage("Please Fill Proper No of Installments !!");
                return false;
            }
            if (loanPlan.equalsIgnoreCase("0")) {
                flag1 = false;
                setMessage("Please Select Installment Plan !!");
                return false;
            }
            if (periodicity.equalsIgnoreCase("0")) {
                flag1 = false;
                setMessage("Please Select Periodicity !!");
                return false;
            }
            flag1 = true;
            return true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method validate()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
            return false;
        }
    }

    public void calculateInstallmentPlan() {
        try {
            message = "";
            if (validate()) {
                table = personnelUtil.calculateLoanInstallments(loanPlan, Double.parseDouble(sanctionAmount), Double.parseDouble(ROI), Integer.parseInt(noOfInstallment), periodicity.charAt(0), ymdFormat.format(startingDate));
                if (table != null || !table.isEmpty()) {
                    setDisableSaveButton(false);
                    setDisableViewButton(true);
                } else {
                    setDisableSaveButton(true);
                    setDisableViewButton(false);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method calculateInstallmentPlan()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method calculateInstallmentPlan()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getLoanTableData() {
        try {
            message = "";
            loanTable = new ArrayList<LoanGrid>();
            List loanTableData = personnelDelegate.getLoanTableData(compCode);
            if (!loanTableData.isEmpty()) {
                for (int i = 0; i < loanTableData.size(); i++) {
                    Object[] ob = (Object[]) loanTableData.get(i);
                    LoanGrid currentLoanRow = new LoanGrid();
                    long EmpLoanNo = Long.parseLong(ob[0].toString());
                    long EmpCode = Long.parseLong(ob[1].toString());
                    String type = ob[2].toString();
                    String sanDate = dmyFormat.format(ob[3]);
                    String empIdd = ob[4].toString();
                    String empIName = ob[5].toString();
                    String desc = ob[6].toString();
                    double amount = Double.parseDouble(ob[7].toString());
                    String instPlan = ob[8].toString();
                    currentLoanRow.setSno(i + 1);
                    currentLoanRow.setEmpLoanNo(EmpLoanNo);
                    currentLoanRow.setEmpId(empIdd);
                    currentLoanRow.setEmpName(empIName);
                    currentLoanRow.setSanctionDate(sanDate);
                    currentLoanRow.setSanctionAmount(amount);
                    currentLoanRow.setEmpCode(EmpCode);
                    currentLoanRow.setLoanType(type);
                    currentLoanRow.setLoanTypeDescription(desc);
                    currentLoanRow.setInstallmentPlan(instPlan);
                    currentLoanRow.setNoOfInstallment(defaultComp);
                    loanTable.add(currentLoanRow);
                }
            } else {
                setMessage("No Record Found !!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getLoanTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setLoanRowValues() {
        try {
            message = "";
            mode = "update";
            setEmpId(currentLoanItem.getEmpId());
            setEmpName(currentLoanItem.getEmpName());
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = personnelDelegate.getHrPersonnelData(compCode, "ID", currentLoanItem.getEmpId());
            for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                currentLoanItem.setEmpCode(hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode());
                currentLoanItem.setDeptCode(hrPersonnelDetailsTO.getDeptCode());
                currentLoanItem.setDesgCode(hrPersonnelDetailsTO.getDesgCode());

                currentLoanItem.setGradeCode(hrPersonnelDetailsTO.getGradeCode());
                if (hrPersonnelDetailsTO.getBasicSal() != null) {
                    currentLoanItem.setBasicSalary(hrPersonnelDetailsTO.getBasicSal());
                } else {
                    currentLoanItem.setBasicSalary(0.00);
                }
            }
            empLoanNo = currentLoanItem.getEmpLoanNo();
            empCode = currentLoanItem.getEmpCode();
            deptCode = currentLoanItem.getDeptCode();
            desgCode = currentLoanItem.getDesgCode();
            gradeCode = currentLoanItem.getGradeCode();
            basicSalary = currentLoanItem.getBasicSalary();
            List<HrMstStructTO> hrMstStructTOs1 = personnelDelegate.getInitialData(compCode, deptCode);
            for (HrMstStructTO hrMstStructTO : hrMstStructTOs1) {
                setDepartment(hrMstStructTO.getDescription());
            }
            List<HrMstStructTO> hrMstStructTOs2 = personnelDelegate.getInitialData(compCode, desgCode);
            for (HrMstStructTO hrMstStructTO : hrMstStructTOs2) {
                setDesignation(hrMstStructTO.getDescription());
            }
            List<HrMstStructTO> hrMstStructTOs3 = personnelDelegate.getInitialData(compCode, gradeCode);
            for (HrMstStructTO hrMstStructTO : hrMstStructTOs3) {
                setGrade(hrMstStructTO.getDescription());
            }
            for (SelectItem list : loansList) {
                if (list.getLabel().equalsIgnoreCase(currentLoanItem.getLoanType())) {
                    setLoanType(list.getValue().toString());
                }
            }
            List<HrEmpLoanHdTO> hrEmpLoanHdTOs = personnelDelegate.getLoanDetails(compCode, empLoanNo);
            for (HrEmpLoanHdTO hrEmpLoanHdTO : hrEmpLoanHdTOs) {
                setLoanType(hrEmpLoanHdTO.getHrEmpLoanHdPKTO().getLoanType());
                setSanctionDate(hrEmpLoanHdTO.getSanctionDate());
                setSanctionAmount(String.valueOf(hrEmpLoanHdTO.getSanctionAmt()));
                setROI(String.valueOf(hrEmpLoanHdTO.getRoi()));
                setLoanPlan(hrEmpLoanHdTO.getInstPlan());
                setNoOfInstallment(String.valueOf(hrEmpLoanHdTO.getNoInst()));
                setPeriodicity(String.valueOf(hrEmpLoanHdTO.getPeriodicity()));
                setStartingDate(hrEmpLoanHdTO.getStartDate());
            }
            setDisableSaveButton(true);
            setDisableDeleteButton(false);
            setDisableViewButton(true);
        } catch (WebException e) {
            logger.error("Exception occured while executing method setLoanRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method setLoanRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public HrMstStructTO getHrMstStructTO(int compCode, String loanType) {
        HrMstStructTO hrMstStructTO = new HrMstStructTO();
        HrMstStructPKTO hrMstStructPKTO = new HrMstStructPKTO();
        try {
            hrMstStructPKTO.setCompCode(compCode);
            hrMstStructPKTO.setStructCode(loanType);
            hrMstStructTO.setHrMstStructPKTO(hrMstStructPKTO);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrMstStructTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrMstStructTO;
    }

    public HrPersonnelDetailsTO getHrPersonnelDetailsTO(int compCode, long empCode) {
        HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
        HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
        try {
            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrPersonnelDetailsTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelDetailsTO;
    }

    public void getEmployeeTableData() {
        try {
            message = "";
            empSearchTable = new ArrayList<EmployeeGrid>();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = personnelDelegate.getHrPersonnelData(compCode, empSearchCriteria, empSearchValue);
            int i = 0;
            if (!hrPersonnelDetailsTOs.isEmpty()) {
                for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                    EmployeeGrid currentEmpRow = new EmployeeGrid();
                    currentEmpRow.setSno(++i);
                    currentEmpRow.setEmpId(hrPersonnelDetailsTO.getEmpId());
                    currentEmpRow.setEmpName(hrPersonnelDetailsTO.getEmpName());
                    currentEmpRow.setEmpAddress(hrPersonnelDetailsTO.getEmpContAdd());
                    currentEmpRow.setEmpPhone(hrPersonnelDetailsTO.getEmpContTel());

                    currentEmpRow.setEmpCode(hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode());
                    currentEmpRow.setDeptCode(hrPersonnelDetailsTO.getDeptCode());
                    currentEmpRow.setDesgCode(hrPersonnelDetailsTO.getDesgCode());

                    currentEmpRow.setGradeCode(hrPersonnelDetailsTO.getGradeCode());
                    if (hrPersonnelDetailsTO.getBasicSal() != null) {
                        currentEmpRow.setBasicSalary(hrPersonnelDetailsTO.getBasicSal());
                    } else {
                        currentEmpRow.setBasicSalary(0.00);
                    }
                    empSearchTable.add(currentEmpRow);
                }
            } else {
                setMessage("No Record Found !!");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getLoansListDetail() {
        try {
            loansList = new ArrayList<SelectItem>();
            loansList.add(new SelectItem("0", "--Select--"));
            List<HrMstStructTO> adva = personnelDelegate.getInitialData(compCode, "LOA%");
            if (!adva.isEmpty()) {
                for (HrMstStructTO hrMstStructTO : adva) {
                    loansList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription().toUpperCase()));
                }
            } else {
                loansList.add(new SelectItem("0", "--Select--"));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getLoansListDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoansListDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getPeriodicityListDetail() {
        try {
            periodicityList = new ArrayList<SelectItem>();
            periodicityList.add(new SelectItem("0", "--Select--"));
            periodicityList.add(new SelectItem("M", "MONTHLY"));
            periodicityList.add(new SelectItem("Q", "QUARTERLY"));
            periodicityList.add(new SelectItem("H", "HALF YEARLY"));
            periodicityList.add(new SelectItem("Y", "YEARLY"));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getPeriodicityListDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    private void getLoanPlanListDetail() {
        try {
            loanPlanList = new ArrayList<SelectItem>();
            loanPlanList.add(new SelectItem("0", "--Select--"));
            loanPlanList.add(new SelectItem("EMI", "EMI"));
            loanPlanList.add(new SelectItem("EPRP", "EPRP"));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanPlanListDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

      public void setOperationOnBlur()
    {
        setMessage("");
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        }else if (operation.equalsIgnoreCase("1")) {
            mode="save";
        } else if (operation.equalsIgnoreCase("2")) {
            mode="update";
            getLoanTableData();
        }
    }
    
    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public boolean isDisableViewButton() {
        return disableViewButton;
    }

    public void setDisableViewButton(boolean disableViewButton) {
        this.disableViewButton = disableViewButton;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<SelectItem> getLoanPlanList() {
        return loanPlanList;
    }

    public void setLoanPlanList(List<SelectItem> loanPlanList) {
        this.loanPlanList = loanPlanList;
    }

    public List<LoanGrid> getLoanTable() {
        return loanTable;
    }

    public void setLoanTable(List<LoanGrid> loanTable) {
        this.loanTable = loanTable;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public List<SelectItem> getLoansList() {
        return loansList;
    }

    public void setLoansList(List<SelectItem> loansList) {
        this.loansList = loansList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNoOfInstallment() {
        return noOfInstallment;
    }

    public void setNoOfInstallment(String noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public List<SelectItem> getPeriodicityList() {
        return periodicityList;
    }

    public void setPeriodicityList(List<SelectItem> periodicityList) {
        this.periodicityList = periodicityList;
    }

    public String getSanctionAmount() {
        return sanctionAmount;
    }

    public void setSanctionAmount(String sanctionAmount) {
        this.sanctionAmount = sanctionAmount;
    }

    public Date getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(Date sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public List<InstallmentGrid> getTable() {
        return table;
    }

    public void setTable(List<InstallmentGrid> table) {
        this.table = table;
    }

    public String getROI() {
        return ROI;
    }

    public void setROI(String ROI) {
        this.ROI = ROI;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public long getEmpLoanNo() {
        return empLoanNo;
    }

    public void setEmpLoanNo(long empLoanNo) {
        this.empLoanNo = empLoanNo;
    }

    public String getLoanPlan() {
        return loanPlan;
    }

    public void setLoanPlan(String loanPlan) {
        this.loanPlan = loanPlan;
    }

    public EmployeeGrid getCurrentEmpItem() {
        return currentEmpItem;
    }

    public void setCurrentEmpItem(EmployeeGrid currentEmpItem) {
        this.currentEmpItem = currentEmpItem;
    }

    public LoanGrid getCurrentLoanItem() {
        return currentLoanItem;
    }

    public void setCurrentLoanItem(LoanGrid currentLoanItem) {
        this.currentLoanItem = currentLoanItem;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }
}
