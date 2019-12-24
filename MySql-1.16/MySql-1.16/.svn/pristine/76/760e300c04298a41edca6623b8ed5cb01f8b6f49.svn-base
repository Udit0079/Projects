package com.hrms.web.controller.personnel;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrEmpAdvanceDtPKTO;
import com.hrms.common.to.HrEmpAdvanceDtTO;
import com.hrms.common.to.HrEmpAdvanceHdPKTO;
import com.hrms.common.to.HrEmpAdvanceHdTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.controller.personnel.PersonnelUtil;
import com.hrms.web.pojo.AdvanceGrid;
import com.hrms.web.pojo.EmployeeGrid;
import com.hrms.web.pojo.InstallmentGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class AdvanceActivity extends BaseBean{

    private String empId,
            empName,
            department,
            designation,
            grade,
            statFlag,
            statUpFlag,
            message,
            mode,
            enteredBy,
            authBy,
            advanceType,
            sanctionAmount,
            noOfInstallment,
            periodicity,
            empSearchCriteria,
            empSearchValue,
            deptCode,
            desgCode,
            gradeCode;
    private boolean disableSaveButton,
            disableViewButton,
            disableDeleteButton,
            flag1;
    private int defaultComp,
            compCode;
    private long advNo,
            empCode;
    private List<SelectItem> advancesList,
            periodicityList;
    private List<InstallmentGrid> table;
    private List<EmployeeGrid> empSearchTable;
    private List<AdvanceGrid> advanceTable;
    private List<CompanyMasterTO> companyMasterTOs;
    private PersonnelUtil personnelUtil = new PersonnelUtil();
    private Validator validator = new Validator();
    private AdvanceGrid currentAdvanceItem = new AdvanceGrid();
    private EmployeeGrid currentEmpItem = new EmployeeGrid();
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy"),
            ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(AdvanceActivity.class);
    private Calendar cal;
    private PersonnelDelegate personnelDelegate;
    private Date startingDate,
            sanctionDate;
    private double basicSalary;
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
    
    

    public AdvanceActivity() {
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
            getAdvancesListDetail();
            getPeriodicityListDetail();
            onLoad();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method AdvanceActivity()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method AdvanceActivity()", e);
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
            setSanctionAmount("");
            setNoOfInstallment("");
            setPeriodicity("--Select--");
            advNo = 0L;
            setAdvanceType("--Select--");
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
                    advNo = personnelDelegate.getMaxAdvNoFromHrEmpAdvanceDt(compCode) + 1;
                    if (table == null) {
                        table = personnelUtil.calculateAdvanceInstallments(Double.parseDouble(sanctionAmount), Integer.parseInt(noOfInstallment), periodicity.charAt(0), ymdFormat.format(startingDate));
                    }
                    int size = table.size() - 1;
                    HrEmpAdvanceHdTO hrEmpAdvanceHdTO = getHrEmpAdvanceHdTO(Double.parseDouble(sanctionAmount), table.get(size).getSno(), table.size(), table.get(size).getRepayFlag(), advNo, dmyFormat.parse(table.get(size).getDueDate()), new Date());
                    List<HrEmpAdvanceDtTO> hrEmpAdvanceDtTOList = new ArrayList<HrEmpAdvanceDtTO>();
                    for (InstallmentGrid list : table) {
                        hrEmpAdvanceDtTOList.add(getHrEmpAdvanceDtTO(list.getPrincipal(), list.getSno(), table.size(), list.getRepayFlag(), advNo, dmyFormat.parse(list.getDueDate()), new Date()));
                    }
                    if (hrEmpAdvanceHdTO != null && hrEmpAdvanceDtTOList.size() == table.size()) {
                        String result = personnelDelegate.saveEmpAdvanceDetail(hrEmpAdvanceHdTO, hrEmpAdvanceDtTOList, mode);
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
            advNo = currentAdvanceItem.getEmpAdvNo();
            List<HrEmpAdvanceDtTO> hrEmpAdvanceDtTOList = new ArrayList<HrEmpAdvanceDtTO>();
            for (int i = 0; i < Integer.parseInt(noOfInstallment); i++) {
                HrEmpAdvanceDtTO hrEmpAdvanceDtTO = new HrEmpAdvanceDtTO();
                HrEmpAdvanceDtPKTO hrEmpAdvanceDtPKTO = new HrEmpAdvanceDtPKTO();
                hrEmpAdvanceDtPKTO.setAdvance(advanceType);
                hrEmpAdvanceDtPKTO.setCompCode(compCode);
                hrEmpAdvanceDtPKTO.setDueDate(dueDate);
                hrEmpAdvanceDtPKTO.setEmpAdvNo(advNo);
                hrEmpAdvanceDtPKTO.setEmpCode(empCode);
                hrEmpAdvanceDtTO.setHrMstStructTO(getHrMstStructTO(compCode, advanceType));
                hrEmpAdvanceDtTO.setHrPersonnelDetailsTO(getHrPersonnelDetailsTO(compCode, empCode));
                hrEmpAdvanceDtTO.setHrEmpAdvanceDtPKTO(hrEmpAdvanceDtPKTO);
                hrEmpAdvanceDtTOList.add(hrEmpAdvanceDtTO);
                dueDate = dmyFormat.parse(dmyFormat.format(ymdFormat.parse(HrmsUtil.monthAdd(ymdFormat.format(dueDate), iPeriod))));
            }
            HrEmpAdvanceHdTO hrEmpAdvanceHdTO = new HrEmpAdvanceHdTO();
            HrEmpAdvanceHdPKTO hrEmpAdvanceHdPKTO = new HrEmpAdvanceHdPKTO();
            hrEmpAdvanceHdPKTO.setCompCode(compCode);
            hrEmpAdvanceHdPKTO.setEmpCode(empCode);
            hrEmpAdvanceHdPKTO.setAdvance(advanceType);
            hrEmpAdvanceHdPKTO.setEmpAdvNo(advNo);
            hrEmpAdvanceHdTO.setHrEmpAdvanceHdPKTO(hrEmpAdvanceHdPKTO);
            hrEmpAdvanceHdTO.setHrMstStructTO(getHrMstStructTO(compCode, advanceType));
            hrEmpAdvanceHdTO.setHrPersonnelDetailsTO(getHrPersonnelDetailsTO(compCode, empCode));
            String result = personnelDelegate.saveEmpAdvanceDetail(hrEmpAdvanceHdTO, hrEmpAdvanceDtTOList, mode);
            cancelAction();
            setMessage(result);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    private HrEmpAdvanceHdTO getHrEmpAdvanceHdTO(double installment, int varInstall, int totInstall, char repayFlag, long advNo, Date dueDate, Date modDate) {
        HrEmpAdvanceHdTO hrEmpAdvanceHdTO = new HrEmpAdvanceHdTO();
        HrEmpAdvanceHdPKTO hrEmpAdvanceHdPKTO = new HrEmpAdvanceHdPKTO();
        try {
            hrEmpAdvanceHdPKTO.setAdvance(advanceType);
            hrEmpAdvanceHdPKTO.setCompCode(compCode);
            hrEmpAdvanceHdPKTO.setEmpAdvNo(advNo);
            hrEmpAdvanceHdPKTO.setEmpCode(empCode);
            hrEmpAdvanceHdTO.setAuthBy(authBy);
            hrEmpAdvanceHdTO.setDefaultComp(defaultComp);
            hrEmpAdvanceHdTO.setEnteredBy(enteredBy);
            hrEmpAdvanceHdTO.setHrEmpAdvanceHdPKTO(hrEmpAdvanceHdPKTO);
            hrEmpAdvanceHdTO.setHrMstStructTO(this.getHrMstStructTO(compCode, advanceType));
            hrEmpAdvanceHdTO.setHrPersonnelDetailsTO(this.getHrPersonnelDetailsTO(compCode, empCode));
            hrEmpAdvanceHdTO.setModDate(modDate);
            hrEmpAdvanceHdTO.setNoInstall(totInstall);
            hrEmpAdvanceHdTO.setPeriodicity(periodicity.charAt(0));
            hrEmpAdvanceHdTO.setRepayFlag(repayFlag);
            hrEmpAdvanceHdTO.setSanctionAmt(installment);
            hrEmpAdvanceHdTO.setSanctionDate(sanctionDate);
            hrEmpAdvanceHdTO.setStartDate(startingDate);
            hrEmpAdvanceHdTO.setStatFlag(statFlag);
            hrEmpAdvanceHdTO.setStatUpFlag(statUpFlag);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrEmpAdvanceHdTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrEmpAdvanceHdTO;
    }

    private HrEmpAdvanceDtTO getHrEmpAdvanceDtTO(double installment, int varInstall, int totInstall, char repayFlag, long advNo, Date dueDate, Date modDate) {
        HrEmpAdvanceDtTO hrEmpAdvanceDtTO = new HrEmpAdvanceDtTO();
        HrEmpAdvanceDtPKTO hrEmpAdvanceDtPKTO = new HrEmpAdvanceDtPKTO();
        try {
            hrEmpAdvanceDtPKTO.setAdvance(advanceType);
            hrEmpAdvanceDtPKTO.setCompCode(compCode);
            hrEmpAdvanceDtPKTO.setDueDate(dueDate);
            hrEmpAdvanceDtPKTO.setEmpAdvNo(advNo);
            hrEmpAdvanceDtPKTO.setEmpCode(empCode);
            hrEmpAdvanceDtTO.setHrEmpAdvanceDtPKTO(hrEmpAdvanceDtPKTO);
            hrEmpAdvanceDtTO.setAmount(installment);
            hrEmpAdvanceDtTO.setAuthBy(getUserName());
            hrEmpAdvanceDtTO.setDefaultComp(defaultComp);
            hrEmpAdvanceDtTO.setEnteredBy(enteredBy);
            hrEmpAdvanceDtTO.setModDate(modDate);
            hrEmpAdvanceDtTO.setRepayFlag(repayFlag);
            hrEmpAdvanceDtTO.setStatFlag(statFlag);
            hrEmpAdvanceDtTO.setStatUpFlag(statUpFlag);
            hrEmpAdvanceDtTO.setTotInstall(totInstall);
            hrEmpAdvanceDtTO.setVarInstall(varInstall);
            hrEmpAdvanceDtTO.setHrMstStructTO(getHrMstStructTO(compCode, advanceType));
            hrEmpAdvanceDtTO.setHrPersonnelDetailsTO(getHrPersonnelDetailsTO(compCode, empCode));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrEmpAdvanceDtTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrEmpAdvanceDtTO;
    }

    public HrMstStructTO getHrMstStructTO(int compCode, String advanceType) {
        HrMstStructTO hrMstStructTO = new HrMstStructTO();
        HrMstStructPKTO hrMstStructPKTO = new HrMstStructPKTO();
        try {
            hrMstStructPKTO.setCompCode(compCode);
            hrMstStructPKTO.setStructCode(advanceType);
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

    public void calculateInstallmentPlan() {
        try {
            message = "";
            if (validate()) {
                table = personnelUtil.calculateAdvanceInstallments(Double.parseDouble(sanctionAmount), Integer.parseInt(noOfInstallment), periodicity.charAt(0), ymdFormat.format(startingDate));
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

    private boolean validate() {
        try {
            setMessage("");
            if (empCode == 0) {
                flag1 = false;
                setMessage("Please Select An Employee !!");
                return false;
            }
            if (advanceType.equalsIgnoreCase("0")) {
                flag1 = false;
                setMessage("Please Select Advance Type !!");
                return false;
            }
            if (!validator.validateDoublePositive(sanctionAmount) || sanctionAmount.equalsIgnoreCase("") || sanctionAmount.equalsIgnoreCase("0.0")) {
                flag1 = false;
                setMessage("Please Fill Sanction Amount !!");
                return false;
            }
            if (!validator.validateInteger(noOfInstallment) || noOfInstallment.equalsIgnoreCase("") || noOfInstallment.equalsIgnoreCase("0.0")) {
                flag1 = false;
                setMessage("Please Fill No of Installments !!");
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

    public void getAdvanceTableData() {
        try {
            message = "";
            advanceTable = new ArrayList<AdvanceGrid>();
            List advanceTableData = personnelDelegate.getAdvanceTableData(compCode);
            if (!advanceTableData.isEmpty()) {
                for (int i = 0; i < advanceTableData.size(); i++) {
                    Object[] ob = (Object[]) advanceTableData.get(i);
                    AdvanceGrid currentAdvanceRow = new AdvanceGrid();
                    long empAdvNo = Long.parseLong(ob[0].toString());
                    long EmpCode = Long.parseLong(ob[1].toString());
                    String type = ob[2].toString();
                    String sanDate = dmyFormat.format(ob[3]);
                    String empIdd = ob[4].toString();
                    String empIName = ob[5].toString();
                    String desc = ob[6].toString();
                    double amount = Double.parseDouble(ob[7].toString());
                    currentAdvanceRow.setSno(i + 1);
                    currentAdvanceRow.setEmpId(empIdd);
                    currentAdvanceRow.setType(type);
                    currentAdvanceRow.setEmpCode(EmpCode);
                    currentAdvanceRow.setEmpAdvNo(empAdvNo);
                    currentAdvanceRow.setEmpName(empIName);
                    currentAdvanceRow.setAdvance(desc);
                    currentAdvanceRow.setSanctionDate(sanDate);
                    currentAdvanceRow.setDescription(desc);
                    currentAdvanceRow.setSanctionAmount(amount);
                    advanceTable.add(currentAdvanceRow);
                }
            } else {
                setMessage("No Record Found !!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAdvanceTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvanceTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setAdvanceRowValues() {
        try {
            message = "";
            mode = "update";
            setEmpId(currentAdvanceItem.getEmpId());
            setEmpName(currentAdvanceItem.getEmpName());
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = personnelDelegate.getHrPersonnelData(compCode, "ID", currentAdvanceItem.getEmpId());
            for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                currentAdvanceItem.setEmpCode(hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode());
                currentAdvanceItem.setDeptCode(hrPersonnelDetailsTO.getDeptCode());
                currentAdvanceItem.setDesgCode(hrPersonnelDetailsTO.getDesgCode());

                currentAdvanceItem.setGradeCode(hrPersonnelDetailsTO.getGradeCode());
                if (hrPersonnelDetailsTO.getBasicSal() != null) {
                    currentAdvanceItem.setBasicSalary(hrPersonnelDetailsTO.getBasicSal());
                } else {
                    currentAdvanceItem.setBasicSalary(0.00);
                }
            }
            advNo = currentAdvanceItem.getEmpAdvNo();
            empCode = currentAdvanceItem.getEmpCode();
            deptCode = currentAdvanceItem.getDeptCode();
            desgCode = currentAdvanceItem.getDesgCode();
            gradeCode = currentAdvanceItem.getGradeCode();
            basicSalary = currentAdvanceItem.getBasicSalary();
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
            advNo = currentAdvanceItem.getEmpAdvNo();
            List<HrEmpAdvanceHdTO> hrEmpAdvanceHdTOs = personnelDelegate.getAdvanceDetails(compCode, advNo);
            if (!hrEmpAdvanceHdTOs.isEmpty()) {
                for (HrEmpAdvanceHdTO hrEmpAdvanceHdTO : hrEmpAdvanceHdTOs) {
                    setAdvanceType(hrEmpAdvanceHdTO.getHrEmpAdvanceHdPKTO().getAdvance());
                    setSanctionDate(hrEmpAdvanceHdTO.getSanctionDate());
                    setSanctionAmount(String.valueOf(hrEmpAdvanceHdTO.getSanctionAmt()));
                    setNoOfInstallment(String.valueOf(hrEmpAdvanceHdTO.getNoInstall()));
                    setPeriodicity(String.valueOf(hrEmpAdvanceHdTO.getPeriodicity()));
                    setStartingDate(hrEmpAdvanceHdTO.getStartDate());
                }
            }
            setDisableSaveButton(true);
            setDisableDeleteButton(false);
            setDisableViewButton(true);
        } catch (WebException e) {
            logger.error("Exception occured while executing method setAdvanceRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method setAdvanceRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
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
        } catch (WebException e) {
            logger.error("Exception occured while executing method getEmployeeTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableData()", e);
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

    public void getAdvancesListDetail() {
        try {
            advancesList = new ArrayList<SelectItem>();
            advancesList.add(new SelectItem("0", "--Select--"));
            List<HrMstStructTO> adva = personnelDelegate.getInitialData(compCode, "ADV%");
            if (!adva.isEmpty()) {
                for (HrMstStructTO hrMstStructTO : adva) {
                    advancesList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                }
            } else {
                advancesList.add(new SelectItem("0", "--Select--"));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAdvancesListDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvancesListDetail()", e);
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
            getAdvanceTableData();
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
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

    public List<AdvanceGrid> getAdvanceTable() {
        return advanceTable;
    }

    public void setAdvanceTable(List<AdvanceGrid> advanceTable) {
        this.advanceTable = advanceTable;
    }

    public String getAdvanceType() {
        return advanceType;
    }

    public void setAdvanceType(String advanceType) {
        this.advanceType = advanceType;
    }

    public List<SelectItem> getAdvancesList() {
        return advancesList;
    }

    public void setAdvancesList(List<SelectItem> advancesList) {
        this.advancesList = advancesList;
    }

    public AdvanceGrid getCurrentAdvanceItem() {
        return currentAdvanceItem;
    }

    public void setCurrentAdvanceItem(AdvanceGrid currentAdvanceItem) {
        this.currentAdvanceItem = currentAdvanceItem;
    }

    public EmployeeGrid getCurrentEmpItem() {
        return currentEmpItem;
    }

    public void setCurrentEmpItem(EmployeeGrid currentEmpItem) {
        this.currentEmpItem = currentEmpItem;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSanctionAmount() {
        return sanctionAmount;
    }

    public void setSanctionAmount(String sanctionAmount) {
        this.sanctionAmount = sanctionAmount;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public List<InstallmentGrid> getTable() {
        return table;
    }

    public void setTable(List<InstallmentGrid> table) {
        this.table = table;
    }

    public boolean isDisableViewButton() {
        return disableViewButton;
    }

    public void setDisableViewButton(boolean disableViewButton) {
        this.disableViewButton = disableViewButton;
    }
}
