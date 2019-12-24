/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.cbs.utils.CbsUtil;
import com.hrms.web.pojo.PayRollCalenrGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.PayrollCalendarPKTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.entity.payroll.HrPayrollCalendar;
import com.hrms.entity.payroll.HrPayrollCalendarPK;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class PayRollCalendar extends BaseBean {

    private static final Logger logger = Logger.getLogger(PayRollCalendar.class);
    private String message;
    private Date dateFrom;
    private Date dateTo;
    private List<SelectItem> selectCriteriaList;
    private String selectCriteria;
    private boolean disableList;
    private boolean closeButton;
    private Integer compCode;
    private List<PayRollCalenrGrid> gridData = new ArrayList<PayRollCalenrGrid>();
    private PayRollCalenrGrid payrollDbl;
    private boolean rendered = false;
    private Date oldFromDate;
    private Date oldTodate;
    private String mode = "SAVE";
    private boolean disableDelete;
    private List<SelectItem> functionList;
    private String function;

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

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public Date getOldFromDate() {
        return oldFromDate;
    }

    public void setOldFromDate(Date oldFromDate) {
        this.oldFromDate = oldFromDate;
    }

    public Date getOldTodate() {
        return oldTodate;
    }

    public void setOldTodate(Date oldTodate) {
        this.oldTodate = oldTodate;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public PayRollCalenrGrid getPayrollDbl() {
        return payrollDbl;
    }

    public void setPayrollDbl(PayRollCalenrGrid payrollDbl) {
        this.payrollDbl = payrollDbl;
    }

    public List<PayRollCalenrGrid> getGridData() {
        return gridData;
    }

    public void setGridData(List<PayRollCalenrGrid> gridData) {
        this.gridData = gridData;
    }

    public boolean isCloseButton() {
        return closeButton;
    }

    public void setCloseButton(boolean closeButton) {
        this.closeButton = closeButton;
    }

    public boolean isDisableList() {
        return disableList;
    }

    public void setDisableList(boolean disableList) {
        this.disableList = disableList;
    }

    public String getSelectCriteria() {
        return selectCriteria;
    }

    public void setSelectCriteria(String selectCriteria) {
        this.selectCriteria = selectCriteria;
    }

    public List<SelectItem> getSelectCriteriaList() {
        return selectCriteriaList;
    }

    public void setSelectCriteriaList(List<SelectItem> selectCriteriaList) {
        this.selectCriteriaList = selectCriteriaList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Create an instance for payroll calendar
     */
    public PayRollCalendar() {
        try {
            this.setMessage("");
            disableList = true;
            closeButton = true;
            disableDelete = true;

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("1", "Add"));
            functionList.add(new SelectItem("2", "View"));

            selectCriteriaList = new ArrayList<SelectItem>();
            selectCriteriaList.add(new SelectItem("0", "--SELECT--"));
            selectCriteriaList.add(new SelectItem("AUTOMATED"));
            selectCriteriaList.add(new SelectItem("MANUAL"));

            compCode = Integer.parseInt(getOrgnBrCode());

        } catch (Exception e) {
            logger.error("Exception occured while executing method PayRollCalendar()", e);
            logger.error("PayRollCalendar()", e);
        }
    }

    public void onChangeFunction() {
        if (function.equals("0")) {
            setMessage("Please select an function");
            return;
        } else if (function.equals("1")) {
            closeButton = true;
            disableList = true;
            disableDelete = true;
            mode = "SAVE";
        } else if (function.equals("2")) {
            getFinancialYear();
        }
    }

    /**
     * function for save the calendar , deletion is perform on mode DELETE
     */
    public void saveDeletePayrollCalendar() {
        try {
            if (getDateFrom() == null) {
                setMessage("From date field cannot be empty!!!");
                return;
            }
            if (getDateTo() == null) {
                setMessage("To date field cannot be empty!!!");
                return;
            }
            message = validations();
            if (message.equalsIgnoreCase("true")) {
                String statflag = "Y";
                String statUpflag = "Y";
                String statusFlag = "OPEN";
                PayrollCalendarTO payrollCalendarTO = new PayrollCalendarTO();
                PayrollCalendarPKTO payrollCalendarPKTO = new PayrollCalendarPKTO();
                payrollCalendarPKTO.setCompCode(compCode);
                payrollCalendarPKTO.setDateFrom(getDateFrom());
                payrollCalendarPKTO.setDateTo(getDateTo());
                payrollCalendarTO.setAuthBy(getUserName());
                payrollCalendarTO.setDefaultComp(compCode);
                payrollCalendarTO.setEnteredBy(getUserName());
                payrollCalendarTO.setModDate(new Date());
                payrollCalendarTO.setPayrollCalendarPKTO(payrollCalendarPKTO);
                payrollCalendarTO.setStatFlag(statflag);
                payrollCalendarTO.setStatUpFlag(statUpflag);
                payrollCalendarTO.setStatusFlag(statusFlag);
                PayrollMasterManagementDelegate payRollDelegate = new PayrollMasterManagementDelegate();
                String result = payRollDelegate.saveDeletePayrollCalendar(payrollCalendarTO, mode);
                refresh();
                //getFinancialYear();
                this.setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveDeletePayrollCalendar()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveDeletePayrollCalendar()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveDeletePayrollCalendar()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * function displays the open calendar list on load of page
     */
    public void getFinancialYear() {
        try {
            if (!gridData.isEmpty()) {
                gridData.clear();
            }
            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
            List<PayrollCalendarTO> payrollCalendarTOs = masterManagementDelegate.getFinancialYear(compCode);
            if (!payrollCalendarTOs.isEmpty()) {
                for (PayrollCalendarTO payrollCalendarTO : payrollCalendarTOs) {
                    PayRollCalenrGrid calendarGrid = new PayRollCalenrGrid();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    calendarGrid.setFromdate(formatter.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateFrom()));
                    calendarGrid.setToDate(formatter.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateTo()));
                    calendarGrid.setStatus(payrollCalendarTO.getStatusFlag());
                    gridData.add(calendarGrid);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getFinancialYear()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getFinancialYear()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getFinancialYear()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Function executes on click of the calendar grid row values
     */
    public void setPayrollCalenderValues() {
        try {
            mode = "DELETE";
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            setDateFrom(sf.parse(payrollDbl.getFromdate()));
            setOldFromDate(sf.parse(payrollDbl.getFromdate()));
            setDateTo(sf.parse(payrollDbl.getToDate()));
            setOldTodate(sf.parse(payrollDbl.getToDate()));
            closeButton = false;
            disableList = false;
            disableDelete = false;
        } catch (ParseException e) {
            logger.error("Exception occured while executing method setPayrollCalenderValues()", e);
        }
    }

    /**
     * Function to close the payroll calendar
     */
    public void closeYearButton() {
        try {
            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setMessage(valResult);
                return;
            }
            HrPayrollCalendar hrPayrollCalendar = new HrPayrollCalendar();
            HrPayrollCalendarPK hrPayrollCalendarPK = new HrPayrollCalendarPK();
            hrPayrollCalendarPK.setCompCode(compCode);
            hrPayrollCalendarPK.setDateFrom(dateFrom);
            hrPayrollCalendarPK.setDateTo(dateTo);
            hrPayrollCalendar.setAuthBy(getUserName());
            hrPayrollCalendar.setEnteredBy(getUserName());
            hrPayrollCalendar.setDefaultComp(compCode);
            hrPayrollCalendar.setHrPayrollCalendarPK(hrPayrollCalendarPK);
            hrPayrollCalendar.setStatFlag("Y");
            hrPayrollCalendar.setStatUpFlag("Y");
            hrPayrollCalendar.setStatusFlag("OPEN");
            PayrollMasterManagementDelegate payrollMasterManagementDelegate = new PayrollMasterManagementDelegate();
            payrollMasterManagementDelegate.closePayrollCalendarButton(hrPayrollCalendar, this.selectCriteria);
        } catch (WebException e) {
            logger.error("Exception occured while executing method closeYearButton()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method closeYearButton()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method closeYearButton()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String validations() {
        if (getDateFrom() == null) {
            return "From date field cannot be empty!!!";
        }
        if (getDateTo() == null) {
            return "To date field cannot be empty!!!";
        }
        if(getDateTo().before(getDateFrom()))
        {
            return "To Date must be greater than From Date!!!";
        }
        if (CbsUtil.monthDiff(getDateFrom(), getDateTo()) < 11) {
            return "'From Date' and 'To Date' should have 1 Year difference.";
        }
        if (closeButton == false) {
            if (this.selectCriteria.equals("0")) {
                return "Closing criteria cannot be blank!!!";
            }
        }
        return "true";
    }

    public void refresh() {
        setDateFrom(null);
        setDateTo(null);
        disableList = true;
        closeButton = true;
        disableDelete = true;
        mode = "SAVE";
        this.setMessage("");
        setFunction("0");
        setSelectCriteria("0");
    }

    public String btnExitAction() {
        refresh();
        return "case1";
    }
}
