package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.LeaveRegisterDataGrid;
import com.hrms.web.pojo.EmployeeDetailGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrLeaveRegisterPKTO;
import com.hrms.common.to.HrLeaveRegisterTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import java.util.GregorianCalendar;

/**
 *
 * @author Navneet
 */
public class LeaveRegister extends BaseBean {

    private static final Logger logger = Logger.getLogger(LeaveRegister.class);
    private String empName, leaveCode, reasonOfLeave, paid, remarks, deptRecommendations, sanctionAuthority, empSearchCriteria,
            empSearchValue, fromDate, toDate, leavRegCode, message, empId, function, focusId;
    private int compCode, daysTaken, totalDaysTaken, numOfLeaveDays, leaveYearFrom;
    private Date leaveFromDate, leaveToDate;
    private boolean disableLeaveCode;
    private long empCode;
    private List<SelectItem> leaveCodeList;
    private List<SelectItem> functionList;
    List<EmployeeDetailGrid> empSearchTable;
    private EmployeeDetailGrid currentItem;
    List<LeaveRegisterDataGrid> leaveRegisterTable;
    private LeaveRegisterDataGrid currentLeaveRegisterItem;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy"), mdyFormat = new SimpleDateFormat("MM'/'dd'/'yy");
    private Calendar cal;
    private PayrollMasterManagementDelegate payrollMasterMgmtFacade;
    private PayrollTransactionsFacadeRemote payrollRemote;

    public LeaveRegister() {
        try {
            payrollMasterMgmtFacade = new PayrollMasterManagementDelegate();
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            message = "";
            Date date = new Date();
            cal = new GregorianCalendar();
            cal.setTime(date);
            compCode = Integer.parseInt(getOrgnBrCode());
            leaveFromDate = date;
            leaveToDate = date;
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("1", "ADD"));
            functionList.add(new SelectItem("2", "VIEW"));
            leaveCodeList = new ArrayList<SelectItem>();
            leaveCodeList.add(new SelectItem("--Select--"));
            this.setDisableLeaveCode(true);
            setLeaveCodeValues();
            
            List list = payrollRemote.getFinYears(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);                
                fromDate = ele[0].toString().trim();
                toDate = ele[1].toString().trim();
            }
            
//            fromDate = (WebUtil.getFinancialYear(compCode)).get(0);
//            toDate = (WebUtil.getFinancialYear(compCode)).get(1);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method LeaveRegister()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method LeaveRegister()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onChangeFunction() {
        if (function.equals("0")) {
            setMessage("Please select an function");
            return;
        } else if (function.equals("1")) {
            setFocusId("searchcriteria");
            empSearchTable = new ArrayList<EmployeeDetailGrid>();
        } else if (function.equals("2")) {
            getLeaveData();
            setFocusId("ddleaveCodeList");
        }
    }

    public void getEmployeeTableData() {
        try {
            empSearchTable = new ArrayList<EmployeeDetailGrid>();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollMasterMgmtFacade.getHrPersonnelData(compCode, empSearchCriteria, empSearchValue);
            int i = 0;
            EmployeeDetailGrid currentEmpRow;
            for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                currentEmpRow = new EmployeeDetailGrid();
                currentEmpRow.setSno(++i);
                currentEmpRow.setEmpId(hrPersonnelDetailsTO.getEmpId());
                currentEmpRow.setEmpName(hrPersonnelDetailsTO.getEmpName());
                currentEmpRow.setEmpAddress(hrPersonnelDetailsTO.getEmpContAdd());
                currentEmpRow.setEmpPhone(hrPersonnelDetailsTO.getEmpContTel());
                empSearchTable.add(currentEmpRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getEmployeeTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setEmpRowValues() {
        try {
            this.setEmpId(currentItem.getEmpId());
            this.setEmpName(currentItem.getEmpName());
            this.setDisableLeaveCode(false);
            setFocusId("ddleaveCodeList");
        } catch (Exception e) {
            logger.error("Exception occured while executing method setEmpRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setLeaveRegisterRowValues() {
        try {
            leavRegCode = currentLeaveRegisterItem.getLeaveRegCode();
            this.setEmpId(currentLeaveRegisterItem.getEmpId());
            this.setEmpName(currentLeaveRegisterItem.getEmpName());
            this.setReasonOfLeave(currentLeaveRegisterItem.getReasonOfLeave());
            this.setDaysTaken(currentLeaveRegisterItem.getDaysTaken());

            setLeaveCode(currentLeaveRegisterItem.getLeaveCode());
            this.setLeaveFromDate(dmyFormat.parse(currentLeaveRegisterItem.getLeaveFromDate()));
            this.setLeaveToDate(dmyFormat.parse(currentLeaveRegisterItem.getLeaveToDate()));

            if (currentLeaveRegisterItem.getDeptRecommendations().equalsIgnoreCase("Y")) {
                this.setDeptRecommendations("Yes");
            } else if (currentLeaveRegisterItem.getDeptRecommendations().equalsIgnoreCase("N")) {
                this.setDeptRecommendations("No");
            }
            if (currentLeaveRegisterItem.getPaid().equalsIgnoreCase("Y")) {
                this.setPaid("Yes");
            } else if (currentLeaveRegisterItem.getDeptRecommendations().equalsIgnoreCase("N")) {
                this.setPaid("No");
            }
            this.setRemarks(currentLeaveRegisterItem.getRemarks());
            this.setSanctionAuthority(currentLeaveRegisterItem.getSanctionAuthority());
        } catch (Exception e) {
            logger.error("Exception occured while executing method setLeaveRegisterRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setLeaveCodeValues() {
        try {
            leaveCodeList = new ArrayList<SelectItem>();
            leaveCodeList.add(new SelectItem("--Select--"));
            List<LeaveMasterTO> leaveMasterTOs = payrollMasterMgmtFacade.getLeaveMasterData(compCode);
            if (!leaveMasterTOs.isEmpty()) {
                for (LeaveMasterTO leaveMasterTO : leaveMasterTOs) {
                    leaveCodeList.add(new SelectItem(leaveMasterTO.getLeaveMasterPKTO().getLeaveCode(), leaveMasterTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method setLeaveCodeValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method setLeaveCodeValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getLeaveData() {
        try {
            leaveRegisterTable = new ArrayList<LeaveRegisterDataGrid>();
            List<HrLeaveRegisterTO> hrLeaveRegisterTOs = payrollMasterMgmtFacade.getLeaveRegisterData(compCode);
            int i = 0;
            LeaveRegisterDataGrid currentLeaveRegisterEmpRow;
            for (HrLeaveRegisterTO hrLeaveRegisterTO : hrLeaveRegisterTOs) {
                currentLeaveRegisterEmpRow = new LeaveRegisterDataGrid();
                currentLeaveRegisterEmpRow.setSno(++i);
                currentLeaveRegisterEmpRow.setLeaveRegCode(hrLeaveRegisterTO.getHrLeaveRegisterPK().getLeavRegCode());
                currentLeaveRegisterEmpRow.setEmpId(hrLeaveRegisterTO.getHrPersonnelDetailsTO().getEmpId());
                currentLeaveRegisterEmpRow.setEmpName(hrLeaveRegisterTO.getHrPersonnelDetailsTO().getEmpName());
                currentLeaveRegisterEmpRow.setPaid(hrLeaveRegisterTO.getPaid().toString());
                currentLeaveRegisterEmpRow.setSanctionAuthority(hrLeaveRegisterTO.getSanctAuth());
                currentLeaveRegisterEmpRow.setLeaveCode(hrLeaveRegisterTO.getLeaveCode());
                currentLeaveRegisterEmpRow.setDaysTaken(hrLeaveRegisterTO.getDaysTaken());
                currentLeaveRegisterEmpRow.setLeaveFromDate(hrLeaveRegisterTO.getFromDate());
                currentLeaveRegisterEmpRow.setLeaveToDate(hrLeaveRegisterTO.getToDate());
                currentLeaveRegisterEmpRow.setDeptRecommendations(hrLeaveRegisterTO.getDepartRecom() + "");
                currentLeaveRegisterEmpRow.setReasonOfLeave(hrLeaveRegisterTO.getReasLeave());
                currentLeaveRegisterEmpRow.setRemarks(hrLeaveRegisterTO.getRemarks());
                leaveRegisterTable.add(currentLeaveRegisterEmpRow);
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method editLeaveData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method editLeaveData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveLeaveData() {
        try {
            if (validate()) {
                String result = payrollMasterMgmtFacade.saveLeaveRegisterDetail(getTOObject(), getFunction());
                cancelAction();
                this.setMessage(result);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveData()", e);
            this.setMessage(e.getMessage());
//            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteLeaveData() {
        try {
            if (validate()) {
                String result = payrollMasterMgmtFacade.saveLeaveRegisterDetail(getTOObject(), "3");
                cancelAction();
                this.setMessage(result);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteLeaveData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelAction() {
        try {
            empId = "";
            empName = "";
            leaveFromDate = dmyFormat.parse(getTodayDate());
            leaveToDate = dmyFormat.parse(getTodayDate());
            leaveCode = "--Select--";
            reasonOfLeave = "";
            paid = "Yes";
            remarks = "";
            deptRecommendations = "";
            sanctionAuthority = "";
            empSearchValue = "";
            empSearchTable = null;
            currentItem = null;
            message = "";
            this.setDaysTaken(0);
            this.setNumOfLeaveDays(0);
            this.setTotalDaysTaken(0);
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitAction() {
        cancelAction();
        return "case1";
    }

    private HrLeaveRegisterTO getTOObject() {
        HrLeaveRegisterTO hrLeaveRegisterTO = new HrLeaveRegisterTO();
        try {
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollMasterMgmtFacade.getHrPersonnelData(compCode, "ID", empId);
            empCode = hrPersonnelDetailsTOs.get(0).getHrPersonnelDetailsPKTO().getEmpCode();

            HrLeaveRegisterPKTO hrLeaveRegisterPKTO = new HrLeaveRegisterPKTO();
            HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
            HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();

            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrLeaveRegisterPKTO.setCompCode(compCode);
            hrLeaveRegisterPKTO.setEmpCode(empCode);
            if (function.equals("2")) {
                hrLeaveRegisterPKTO.setLeavRegCode(leavRegCode);
            }

            hrLeaveRegisterTO.setFromDate(dmyFormat.format(leaveFromDate));
            hrLeaveRegisterTO.setToDate(dmyFormat.format(leaveToDate));
            hrLeaveRegisterTO.setLeaveCode(leaveCode);

            hrLeaveRegisterTO.setDaysTaken(daysTaken);
            hrLeaveRegisterTO.setReasLeave(reasonOfLeave);
            hrLeaveRegisterTO.setPaid(paid.charAt(0));
            hrLeaveRegisterTO.setRemarks(remarks);

            hrLeaveRegisterTO.setDepartRecom(deptRecommendations.charAt(0));
            hrLeaveRegisterTO.setSanctAuth(sanctionAuthority);
            hrLeaveRegisterTO.setDefaultComp(compCode);

            hrLeaveRegisterTO.setStatFlag("Y");
            hrLeaveRegisterTO.setStatUpFlag("Y");
            hrLeaveRegisterTO.setModDate(new Date());
            hrLeaveRegisterTO.setAuthBy(getUserName());
            hrLeaveRegisterTO.setEnteredBy(getUserName());

            hrLeaveRegisterTO.setHrLeaveRegisterPK(hrLeaveRegisterPKTO);

            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
            hrLeaveRegisterTO.setHrPersonnelDetailsTO(hrPersonnelDetailsTO);
        } catch (WebException ex) {
            setMessage(ex.getMessage());
        }
        return hrLeaveRegisterTO;
    }

    public boolean validate() {
        if (leaveFromDate == null) {
            this.setMessage("From Date Cannot Be Blank !!");
            return false;
        }
        if (leaveToDate == null) {
            this.setMessage("To Date Cannot Be Blank !!");
            return false;
        }
        if (empId == null || empName == null || empId.equals("") || empName.equals("")) {
            message = "Please Select A Row !!";
            return false;
        }
        if (leaveCode.equalsIgnoreCase("--Select--")) {
            message = "Please Select A Leave Code !!";
            return false;
        }
        return true;
    }

    public void isLeaveAllowed() {
        try {
            if (empId == null || empId.equals("")) {
                message = "You have not selected any employee.";
                return;
            }
            if (leaveCode.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Leave Code");
                return;
            }
            if (leaveToDate.compareTo(leaveFromDate) < 0) {
                this.setMessage("To Date should be greater than From Date !!");
                return;
            }
            String paramFromDate = dmyFormat.format(leaveFromDate);
            String paramToDate = dmyFormat.format(leaveToDate);
            cal.setTime(dmyFormat.parse(fromDate));// setting calendar to financial year
            int calYearFrom = cal.get(Calendar.YEAR);

            cal.setTime(dmyFormat.parse(toDate));// setting calendar to financial year
            int calYearTo = cal.get(Calendar.YEAR);
            cal.setTime(leaveFromDate);// setting calendar to leave from year
            int leaveMonthFrom = cal.get(Calendar.MONTH) + 1;

            cal.setTime(leaveToDate);// setting calendar to leave to year
            if (leaveMonthFrom == 1 || leaveMonthFrom == 2 || leaveMonthFrom == 3) {
                paramFromDate = "01/01/" + calYearTo;
                paramToDate = "31/12/" + calYearTo;
            } else {
                paramFromDate = "01/01/" + calYearFrom;
                paramToDate = "31/12/" + calYearFrom;
            }
            daysTaken = (int) HrmsUtil.dayDiff(leaveFromDate, leaveToDate) + 1;
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollMasterMgmtFacade.getHrPersonnelData(compCode, "ID", empId);
            empCode = hrPersonnelDetailsTOs.get(0).getHrPersonnelDetailsPKTO().getEmpCode();
            totalDaysTaken = payrollMasterMgmtFacade.getTotalLeaveDays(compCode, leaveCode, empCode, paramFromDate, paramToDate);
            numOfLeaveDays = payrollMasterMgmtFacade.getNumOfLeaveDays(compCode, leaveCode, fromDate, toDate);
            if (getFunction().equalsIgnoreCase("1")) {
                if ((totalDaysTaken + daysTaken) > numOfLeaveDays) {
                    this.setMessage("You are trying to take more than the granted leave");
                    return;
                }
                leavRegCode = "0";
            } else if (getFunction().equalsIgnoreCase("2")) {
                if ((totalDaysTaken + daysTaken) > numOfLeaveDays) {
                    this.setMessage("You are trying to take more than the granted leave");
                    return;
                }
            }

            if ((payrollMasterMgmtFacade.isDateAllowed(compCode, empCode, leavRegCode, leaveFromDate, leaveToDate)).equalsIgnoreCase("N")) {
                this.setMessage("You are trying to take leave on the date which has already been taken as leave");
                return;
            }
            message = "Leave Allowed";
            setFocusId("paidList");
            return;
        } catch (WebException e) {
            logger.error("Exception occured while executing method isLeaveAllowed()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
            return;
        } catch (Exception e) {
            logger.error("Exception occured while executing method isLeaveAllowed()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
            return;
        }

    }

    /* getters and setters */
    public List<SelectItem> getLeaveCodeList() {
        return leaveCodeList;
    }

    public void setLeaveCodeList(List<SelectItem> leaveCodeList) {
        this.leaveCodeList = leaveCodeList;
    }

    public Date getLeaveFromDate() {
        return leaveFromDate;
    }

    public void setLeaveFromDate(Date leaveFromDate) {
        this.leaveFromDate = leaveFromDate;
    }

    public Date getLeaveToDate() {
        return leaveToDate;
    }

    public void setLeaveToDate(Date leaveToDate) {
        this.leaveToDate = leaveToDate;
    }

    public int getLeaveYearFrom() {
        return leaveYearFrom;
    }

    public void setLeaveYearFrom(int leaveYearFrom) {
        this.leaveYearFrom = leaveYearFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmployeeDetailGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EmployeeDetailGrid currentItem) {
        this.currentItem = currentItem;
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

    public boolean isDisableLeaveCode() {
        return disableLeaveCode;
    }

    public void setDisableLeaveCode(boolean disableLeaveCode) {
        this.disableLeaveCode = disableLeaveCode;
    }

    public int getDaysTaken() {
        return daysTaken;
    }

    public void setDaysTaken(int daysTaken) {
        this.daysTaken = daysTaken;
    }

    public String getDeptRecommendations() {
        return deptRecommendations;
    }

    public void setDeptRecommendations(String deptRecommendations) {
        this.deptRecommendations = deptRecommendations;
    }

    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }

    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
    }

    public String getEmpSearchValue() {
        return empSearchValue;
    }

    public void setEmpSearchValue(String empSearchValue) {
        this.empSearchValue = empSearchValue;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getReasonOfLeave() {
        return reasonOfLeave;
    }

    public void setReasonOfLeave(String reasonOfLeave) {
        this.reasonOfLeave = reasonOfLeave;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSanctionAuthority() {
        return sanctionAuthority;
    }

    public void setSanctionAuthority(String sanctionAuthority) {
        this.sanctionAuthority = sanctionAuthority;
    }

    public LeaveRegisterDataGrid getCurrentLeaveRegisterItem() {
        return currentLeaveRegisterItem;
    }

    public void setCurrentLeaveRegisterItem(LeaveRegisterDataGrid currentLeaveRegisterItem) {
        this.currentLeaveRegisterItem = currentLeaveRegisterItem;
    }

    public List<LeaveRegisterDataGrid> getLeaveRegisterTable() {
        return leaveRegisterTable;
    }

    public void setLeaveRegisterTable(List<LeaveRegisterDataGrid> leaveRegisterTable) {
        this.leaveRegisterTable = leaveRegisterTable;
    }

    public int getNumOfLeaveDays() {
        return numOfLeaveDays;
    }

    public void setNumOfLeaveDays(int numOfLeaveDays) {
        this.numOfLeaveDays = numOfLeaveDays;
    }

    public int getTotalDaysTaken() {
        return totalDaysTaken;
    }

    public void setTotalDaysTaken(int totalDaysTaken) {
        this.totalDaysTaken = totalDaysTaken;
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

    public List<EmployeeDetailGrid> getEmpSearchTable() {
        return empSearchTable;
    }

    public void setEmpSearchTable(List<EmployeeDetailGrid> empSearchTable) {
        this.empSearchTable = empSearchTable;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
}
