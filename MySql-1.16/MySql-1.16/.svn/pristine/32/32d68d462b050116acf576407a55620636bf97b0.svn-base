/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.LeaveMasterGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.LeaveMasterPKTO;
import com.hrms.common.to.LeaveMasterTO;
//import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class LeaveMaster extends BaseBean {

    private static final Logger logger = Logger.getLogger(LeaveMaster.class);
    private String function;
    private String message;
    private String fromDate;
    private String toDate;
    
    private String leaveNature;
    private int compCode;
    private char encash;
    private String leaveCode;
    private Date applicablDt;
    private String description;
    private String noOfDays;
    private LeaveMasterGrid currentItem;
    private boolean leaveCodeDisable;
    private String mode = "";
    private boolean deleteButtonBoolean;
    private boolean disableLeaveNature;
    private List<SelectItem> leaveNatureOption;
    private List<SelectItem> encashOption;
    private List<SelectItem> functionList;
    private List<LeaveMasterGrid> leaveMasterGrid;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private PayrollTransactionsFacadeRemote payrollRemote;

    public LeaveMaster() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            mode = "save";
            deleteButtonBoolean = true;
            compCode = Integer.parseInt(getOrgnBrCode());
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("1", "ADD"));
            functionList.add(new SelectItem("2", "VIEW"));
            getInitalData();
        } catch (Exception e) {
            logger.error("Exception occured while executing method LeaveMaster()", e);
            logger.error("LeaveMaster()", e);
        }
    }

    public void getInitalData() {
        try {
            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
            List<HrMstStructTO> HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "LEA%");
            leaveNatureOption = new ArrayList<SelectItem>();
            leaveNatureOption.add(new SelectItem("0", "--SELECT--"));
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO HrMstStructTO : HrMstStructTOs) {
                    leaveNatureOption.add(new SelectItem(HrMstStructTO.getHrMstStructPKTO().getStructCode(),
                            HrMstStructTO.getDescription()));
                }
            }
            encashOption = new ArrayList<SelectItem>();
            encashOption.add(new SelectItem("0", "--SELECT--"));
            encashOption.add(new SelectItem("Y", "Yes"));
            encashOption.add(new SelectItem("N", "No"));
            
            List list = payrollRemote.getFinYears(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);                
                this.setFromDate(ele[0].toString().trim());
                this.setToDate(ele[1].toString().trim());
            }
            
//            List<PayrollCalendarTO> payrollCalendarTOs = masterManagementDelegate.getFinancialYear(compCode);
//            if (!payrollCalendarTOs.isEmpty()) {
//                for (PayrollCalendarTO payrollCalendarTO : payrollCalendarTOs) {
//                    this.setFromDate(sdf.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateFrom()));
//                    this.setToDate(sdf.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateTo()));
//                }
//            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getInitialEventData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getInitialEventData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInitialEventData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onChangeFunction(){
         if (function.equals("0")) {
            setMessage("Please select an function");
            return;
        } else if (function.equals("1")) {
        } else if (function.equals("2")) {
            leaveGridData();
        }
    }
    
    public void leaveGridData() {
        try {
            leaveMasterGrid = new ArrayList<LeaveMasterGrid>();
            PayrollMasterManagementDelegate payrollRollDelegate = new PayrollMasterManagementDelegate();
            List<LeaveMasterTO> leaveMasterTOs = payrollRollDelegate.getLeaveMasterData(compCode);
            if (!leaveMasterTOs.isEmpty()) {
                for (LeaveMasterTO leaveMasterTO : leaveMasterTOs) {
                    LeaveMasterGrid leaveMasterRow = new LeaveMasterGrid();
                    leaveMasterRow.setApplicableDate(leaveMasterTO.getApplicableDate());
                    leaveMasterRow.setDescription(leaveMasterTO.getDescription());
                    leaveMasterRow.setEnCash(leaveMasterTO.getEncash());
                    leaveMasterRow.setFromDate(leaveMasterTO.getLeaveMasterPKTO().getDateFrom());
                    
                    leaveMasterRow.setToDate(leaveMasterTO.getLeaveMasterPKTO().getDateTo());
                    leaveMasterRow.setLeaveCode(leaveMasterTO.getLeaveMasterPKTO().getLeaveCode());
                    leaveMasterRow.setLeaveDesc(leaveMasterTO.getStructMasterTO().getDescription());
                    leaveMasterRow.setLeaveNature(leaveMasterTO.getLeaveNature());
                    leaveMasterRow.setNoOfDays(leaveMasterTO.getNumDays());
                    leaveMasterGrid.add(leaveMasterRow);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method leaveGridData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method leaveGridData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method leaveGridData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public void setLeaveDetails() {
        try {
            setMessage("");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            setApplicablDt(formatter.parse(currentItem.getApplicableDate()));
            this.setDescription(currentItem.getDescription());
            setEncash(currentItem.getEnCash());
            setFromDate(currentItem.getFromDate());
            setToDate(currentItem.getToDate());
            setLeaveCode(currentItem.getLeaveCode());
            setLeaveNature(currentItem.getLeaveNature());
            setNoOfDays(String.valueOf(currentItem.getNoOfDays()));
            leaveCodeDisable = true;
            disableLeaveNature = true;
            deleteButtonBoolean = false;
            mode = "update";
        } catch (ParseException ex) {
            logger.error("Exception occured while executing method setRowValuesForUpdate()", ex);
        }
    }

    public void deleteLeaveDetails() {
        mode = "delete";
        saveLeaveDetail();
    }

    public void saveLeaveDetail() {
        try {
            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setMessage(valResult);
                return;
            }
            LeaveMasterTO leavmstObj = new LeaveMasterTO();
            LeaveMasterPKTO leaveMasterPKTO = new LeaveMasterPKTO();

            leaveMasterPKTO.setCompCode(compCode);
            leaveMasterPKTO.setLeaveCode(this.getLeaveCode());
            leaveMasterPKTO.setDateFrom(this.getFromDate());
            leaveMasterPKTO.setDateTo(this.getToDate());

            leavmstObj.setLeaveMasterPKTO(leaveMasterPKTO);
            leavmstObj.setApplicableDate(sdf.format(this.getApplicablDt()));
            leavmstObj.setDescription(this.getDescription());
            leavmstObj.setEncash(this.getEncash());

            leavmstObj.setModDate(new Date());
            leavmstObj.setNumDays(Integer.parseInt(this.getNoOfDays()));
            leavmstObj.setStatUpFlag("Y");

            leavmstObj.setDefaultComp(1);
            leavmstObj.setStatFlag("N");
            leavmstObj.setAuthBy(this.getUserName());
            leavmstObj.setEnteredBy(this.getUserName());
            leavmstObj.setLeaveNature(this.getLeaveNature());

            HrMstStructTO HrMstStructTO = new HrMstStructTO();
            HrMstStructPKTO HrMstStructPKTO = new HrMstStructPKTO();
            HrMstStructPKTO.setCompCode(compCode);
            HrMstStructPKTO.setStructCode(this.getLeaveNature());
            HrMstStructTO.setHrMstStructPKTO(HrMstStructPKTO);
            leavmstObj.setStructMasterTO(HrMstStructTO);

            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
            String result = masterManagementDelegate.saveLeaveDetail(leavmstObj, mode);
            refresh();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String validations() {
        try {
            if (getLeaveCode().equalsIgnoreCase("")) {
                return "Leave code field cannot be empty!!!";
            }
            if (leaveNature.equalsIgnoreCase("0")) {
                return "Select the leave nature option!!!";
            }
            if (getApplicablDt() == null) {
                return "Fill the applicable date!!!";
            }
            if (getDescription().equalsIgnoreCase("")) {
                return "Description field cannot be empty!!!";
            }
            if (getNoOfDays().equalsIgnoreCase("")) {
                return "Fill the no of days field!!!";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher daysMatch = p.matcher(this.getNoOfDays());
            if (!daysMatch.matches()) {
                return "Enter Valid numeric value for no of days!!!";
            }
            if (Double.parseDouble(getNoOfDays()) < 0) {
                return "Number of days cannot be negative!!!";
            }
            if (encash == '0') {
                return "Select the appropriate option of encash!!!";
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
        setEncash('0');
        setLeaveNature("0");
        setLeaveCode("");
        setDescription("");
        setNoOfDays("");
        setMessage("");
        setApplicablDt(null);
        setFunction("0");
        deleteButtonBoolean = true;
        disableLeaveNature = false;
        leaveCodeDisable = false;
        mode = "save";
    }

    public String btnExitAction() {
        refresh();
        return "case1";
    }
    
       
    public boolean isDisableLeaveNature() {
        return disableLeaveNature;
    }

    public void setDisableLeaveNature(boolean disableLeaveNature) {
        this.disableLeaveNature = disableLeaveNature;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isDeleteButtonBoolean() {
        return deleteButtonBoolean;
    }

    public void setDeleteButtonBoolean(boolean deleteButtonBoolean) {
        this.deleteButtonBoolean = deleteButtonBoolean;
    }

    public boolean isLeaveCodeDisable() {
        return leaveCodeDisable;
    }

    public void setLeaveCodeDisable(boolean leaveCodeDisable) {
        this.leaveCodeDisable = leaveCodeDisable;
    }

    public LeaveMasterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LeaveMasterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<LeaveMasterGrid> getLeaveMasterGrid() {
        return leaveMasterGrid;
    }

    public void setLeaveMasterGrid(List<LeaveMasterGrid> leaveMasterGrid) {
        this.leaveMasterGrid = leaveMasterGrid;
    }

    public Date getApplicablDt() {
        return applicablDt;
    }

    public void setApplicablDt(Date applicablDt) {
        this.applicablDt = applicablDt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getEncash() {
        return encash;
    }

    public void setEncash(char encash) {
        this.encash = encash;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getLeaveNature() {
        return leaveNature;
    }

    public void setLeaveNature(String leaveNature) {
        this.leaveNature = leaveNature;
    }

    public List<SelectItem> getLeaveNatureOption() {
        return leaveNatureOption;
    }

    public void setLeaveNatureOption(List<SelectItem> leaveNatureOption) {
        this.leaveNatureOption = leaveNatureOption;
    }

    public List<SelectItem> getEncashOption() {
        return encashOption;
    }

    public void setEncashOption(List<SelectItem> encashOption) {
        this.encashOption = encashOption;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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
}
