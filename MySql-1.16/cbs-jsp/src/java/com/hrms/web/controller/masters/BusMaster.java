/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.masters;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstBusPKTO;
import com.hrms.common.to.HrMstBusTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.pojo.BusMasterTable;
import com.hrms.web.controller.Validator;
import com.hrms.web.delegate.DefinitionsDelegate;
import com.hrms.web.exception.WebException;
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

public class BusMaster extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(BusMaster.class);
    private int defaultComp,
            compCode,
            totalEmpRecords,
            currentRow;
    private String statUpFlag,
            message,
            mode,
            enteredBy,
            authBy,
            busNo,
            busDriverName,
            busStartTime,
            busEndTime,
            remarks,
            amPmStart,
            amPmEnd,
            statFlag;
    private Calendar cal;
    private Date startTime, endTime;
    private boolean busFound;
    private List<BusMasterTable> busMasterTable;
    private BusMasterTable currentBusRow,
            currentBusItem = new BusMasterTable();
    private DefinitionsDelegate definitionsDelegate;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a"),
            dmyFormat = new SimpleDateFormat("dd/MM/yyyy"),
            mdyFormat = new SimpleDateFormat("MM'/'dd'/'yy");
    private Validator validator;
    private boolean disableAddButton,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton,
            disableBusNo,
            disableBusDriver,
            disableStartTime,
            disableEndTime,
            disableRemarks,
            showBusGrid;
    private String operation;
    private List<SelectItem> operationList;

    public BusMaster() {
        try {
            definitionsDelegate = new DefinitionsDelegate();
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","View"));
            Date date = new Date();
            cal = new GregorianCalendar();
            cal.setTime(date);
            try {
                compCode = Integer.parseInt(getOrgnBrCode());
            } catch (Exception e) {
            }
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            authBy = getUserName();
            enteredBy = getUserName();
            statFlag = "Y";
            statUpFlag = "Y";
            busFound = false;
            setBusStartTime("");
            setBusEndTime("");
            validator = new Validator();
            onLoadMode();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method BusMaster()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method BusMaster()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void addAction() {
        try {
            mode = "save";
            enableFields();
            setDisableAddButton(true);
            setDisableSaveButton(false);
            setDisableEditButton(true);
            setDisableDeleteButton(true);
            setMessage("Please Fill Details !!");
        } catch (Exception e) {
            logger.error("Exception occured while executing method addAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public boolean validateData() {
        if (busNo.equalsIgnoreCase("") || busNo == null) {
            setMessage("Bus Number Cannot Be Blank");
            return false;
        }
        char c = busNo.charAt(0);
        if (c >= 65 && c <= 90) {
        } else {
            setMessage("Fisrt Character Of Bus Number Must Be A Letter");
            return false;
        }

        if (busDriverName.equalsIgnoreCase("") || busDriverName == null) {
            setMessage("Bus Driver Name Cannot Be Blank");
            return false;
        }
        return true;
    }

    public boolean timeValidation(String busStartTime, String am_pm_start, String busEndTime, String am_pm_end) {
        try {
            if (busStartTime.matches("[0-9:]*") && busStartTime.length() == 5 && busStartTime.charAt(2) == ':' && busEndTime.matches("[0-9:]*") && busEndTime.length() == 5 && busEndTime.charAt(2) == ':') {
                int startHour = Integer.parseInt(busStartTime.substring(0, 2));
                int startMinute = Integer.parseInt(busStartTime.substring(3));
                int endHour = Integer.parseInt(busEndTime.substring(0, 2));
                int endMinute = Integer.parseInt(busEndTime.substring(3));
                if (startHour >= 12 && (am_pm_start.equalsIgnoreCase("AM") || am_pm_start.equalsIgnoreCase("PM"))) {
                    setMessage("Start Hour Cannot be equal to or greater than 12");
                    return false;
                }
                if (endHour >= 12 && (am_pm_end.equalsIgnoreCase("AM") || am_pm_end.equalsIgnoreCase("PM"))) {
                    setMessage("End Hour Cannot be greater equal to or greater than 12");
                    return false;
                }
                if (startHour < 0 || endHour < 0 || startMinute < 0 || endMinute < 0) {
                    setMessage("Time cannot be negative !!");
                    return false;
                }
                if (startMinute >= 60) {
                    setMessage("Start Minute Cannot be equal to or greater than 60");
                    return false;
                }
                if (endMinute >= 60) {
                    setMessage("End Minute Cannot be greater equal to or greater than 60");
                    return false;
                }
            } else {
                setMessage("Incorrect Time Format !!");
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method timeValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return true;
    }

    public boolean busValidation() {
        try {
            List<HrMstBusTO> hrMstBusTOs = definitionsDelegate.getAllByBusNo(compCode);
            for (HrMstBusTO hrMstBusTO : hrMstBusTOs) {
                if (busNo.equalsIgnoreCase(hrMstBusTO.getHrMstBusPKTO().getBusNo())) {
                    busFound = true;
                    busNo = hrMstBusTO.getHrMstBusPKTO().getBusNo();
                    busDriverName = hrMstBusTO.getBusDriver();
                    break;
                } else {
                    busFound = false;
                }
            }
            if (busFound == true) {
                setMessage("Bus Number " + busNo + " Is Already Assigned To " + busDriverName);
                return false;
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return true;
    }

    public void performAction() {
        try {
            HrMstBusTO hrMstBusTO = new HrMstBusTO();
            HrMstBusPKTO hrMstBusPKTO = new HrMstBusPKTO();
            hrMstBusPKTO.setBusNo(busNo);
            hrMstBusPKTO.setCompCode(compCode);
            hrMstBusTO.setHrMstBusPKTO(hrMstBusPKTO);
            hrMstBusTO.setAuthBy(authBy);
            hrMstBusTO.setBusDriver(busDriverName);
            hrMstBusTO.setBusStartTm(startTime);
            hrMstBusTO.setBusEndTm(endTime);
            hrMstBusTO.setDefaultComp(defaultComp);
            hrMstBusTO.setEnteredBy(enteredBy);
            hrMstBusTO.setModDate(Date.class.newInstance());
            hrMstBusTO.setRemarks(remarks);
            hrMstBusTO.setStatFlag(statFlag);
            hrMstBusTO.setStatUpFlag(statUpFlag);
            String result = definitionsDelegate.saveBusMasterDetail(hrMstBusTO, mode);
            cancelAction();
            onLoadMode();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method performAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method performAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveAction() {
        try {
            if (validateData()) {
                authBy = getUserName();
                enteredBy = getUserName();
                if ((mode.equalsIgnoreCase("update") || mode.equalsIgnoreCase("delete")) && timeValidation(busStartTime, amPmStart, busEndTime, amPmEnd)) {
                    busNo = currentBusRow.getBusNo();
                    startTime = timeFormat.parse(busStartTime + " " + amPmStart);
                    endTime = timeFormat.parse(busEndTime + " " + amPmEnd);
                    performAction();
                } else if (mode.equalsIgnoreCase("save")) {
                    if (busValidation() && timeValidation(busStartTime, amPmStart, busEndTime, amPmEnd)) {
                        startTime = timeFormat.parse(busStartTime + " " + amPmStart);
                        endTime = timeFormat.parse(busEndTime + " " + amPmEnd);
                        performAction();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void editAction() {
        try {
            getBusMasterTableData();
            if (!busMasterTable.isEmpty()) {
                showBusGrid = true;
                setMessage(totalEmpRecords + " Row(s) Found !!");
            } else {
                showBusGrid = false;
                setDisableSaveButton(true);
                setDisableDeleteButton(true);
                setOperation("0");
                setMessage("No Bus Data");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteAction() {
        try {
            HrMstBusTO hrMstBusTO = new HrMstBusTO();
            HrMstBusPKTO hrMstBusPKTO = new HrMstBusPKTO();
            hrMstBusPKTO.setBusNo(busNo);
            hrMstBusPKTO.setCompCode(compCode);
            hrMstBusTO.setHrMstBusPKTO(hrMstBusPKTO);
            String result = definitionsDelegate.saveBusMasterDetail(hrMstBusTO, "delete");
            cancelAction();
            onLoadMode();
            setMessage(result);
            setOperation("0");
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelAction() {
        try {
            setBusNo("");
            setBusDriverName("");
            busMasterTable = null;
            currentBusRow = null;
            currentBusItem = null;
            setBusStartTime("");
            setAmPmStart("AM");
            setBusEndTime("");
            setAmPmEnd("AM");
            setRemarks("");
            onLoadMode();
            setOperation("0");
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelAction()", e);
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

    public void getBusMasterTableData() {
        try {
            busMasterTable = new ArrayList<BusMasterTable>();
            int i = 0;
            List<HrMstBusTO> hrMstBusTOs = definitionsDelegate.getAllByBusNo(compCode);
            if (hrMstBusTOs != null) {
                for (HrMstBusTO hrMstBusTO : hrMstBusTOs) {
                    currentBusRow = new BusMasterTable();
                    currentBusRow.setSno(++i);
                    currentBusRow.setBusNo(hrMstBusTO.getHrMstBusPKTO().getBusNo());
                    currentBusRow.setBusStartTime(timeFormat.format(hrMstBusTO.getBusStartTm()));
                    currentBusRow.setBusEndTime(timeFormat.format(hrMstBusTO.getBusEndTm()));
                    currentBusRow.setBusDriverName(hrMstBusTO.getBusDriver());
                    currentBusRow.setRemarks(hrMstBusTO.getRemarks());
                    busMasterTable.add(currentBusRow);
                }
            }
            totalEmpRecords = i;
        } catch (WebException e) {
            logger.error("Exception occured while executing method getBusMasterTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getBusMasterTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setBusRowValues() {
        try {
            setDisableAddButton(true);
            setDisableSaveButton(false);
            setDisableEditButton(true);
            setDisableDeleteButton(false);
            enableFields();
            setBusNo(currentBusItem.getBusNo());
            setBusDriverName(currentBusItem.getBusDriverName());
            setBusStartTime(HrmsUtil.correct12HourTime(currentBusItem.getBusStartTime()).substring(0, 5));
            setAmPmStart(HrmsUtil.correct12HourTime(currentBusItem.getBusStartTime()).substring(6, 8));
            setBusEndTime(HrmsUtil.correct12HourTime(currentBusItem.getBusEndTime()).substring(0, 5));
            setAmPmEnd(HrmsUtil.correct12HourTime(currentBusItem.getBusEndTime()).substring(6, 8));
            setRemarks(currentBusItem.getRemarks());
            mode = "update";
            setMessage("");
        } catch (Exception e) {
            logger.error("Exception occured while executing method setBusRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        setDisableAddButton(false);
        setDisableEditButton(false);
        setDisableSaveButton(true);
        setDisableDeleteButton(true);
        disableFields();
    }

    public void enableFields() {
        setDisableBusDriver(false);
        setDisableBusNo(false);
        setDisableEndTime(false);
        setDisableRemarks(false);
        setDisableStartTime(false);
    }

    public void disableFields() {
        setDisableBusDriver(true);
        setDisableBusNo(true);
        setDisableEndTime(true);
        setDisableRemarks(true);
        setDisableStartTime(true);
    }
    
    public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            addAction();
        } else if (operation.equalsIgnoreCase("2")) {
            editAction();
        }
    }
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

    public String getAmPmEnd() {
        return amPmEnd;
    }

    public void setAmPmEnd(String amPmEnd) {
        this.amPmEnd = amPmEnd;
    }

    public String getAmPmStart() {
        return amPmStart;
    }

    public void setAmPmStart(String amPmStart) {
        this.amPmStart = amPmStart;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getBusDriverName() {
        return busDriverName;
    }

    public void setBusDriverName(String busDriverName) {
        this.busDriverName = busDriverName;
    }

    public String getBusEndTime() {
        return busEndTime;
    }

    public void setBusEndTime(String busEndTime) {
        this.busEndTime = busEndTime;
    }

    public boolean isBusFound() {
        return busFound;
    }

    public void setBusFound(boolean busFound) {
        this.busFound = busFound;
    }

    public List<BusMasterTable> getBusMasterTable() {
        return busMasterTable;
    }

    public void setBusMasterTable(List<BusMasterTable> busMasterTable) {
        this.busMasterTable = busMasterTable;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getBusStartTime() {
        return busStartTime;
    }

    public void setBusStartTime(String busStartTime) {
        this.busStartTime = busStartTime;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public BusMasterTable getCurrentBusItem() {
        return currentBusItem;
    }

    public void setCurrentBusItem(BusMasterTable currentBusItem) {
        this.currentBusItem = currentBusItem;
    }

    public BusMasterTable getCurrentBusRow() {
        return currentBusRow;
    }

    public void setCurrentBusRow(BusMasterTable currentBusRow) {
        this.currentBusRow = currentBusRow;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public DefinitionsDelegate getDefinitionsDelegate() {
        return definitionsDelegate;
    }

    public void setDefinitionsDelegate(DefinitionsDelegate definitionsDelegate) {
        this.definitionsDelegate = definitionsDelegate;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }

    public boolean isDisableBusDriver() {
        return disableBusDriver;
    }

    public void setDisableBusDriver(boolean disableBusDriver) {
        this.disableBusDriver = disableBusDriver;
    }

    public boolean isDisableBusNo() {
        return disableBusNo;
    }

    public void setDisableBusNo(boolean disableBusNo) {
        this.disableBusNo = disableBusNo;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableEditButton() {
        return disableEditButton;
    }

    public void setDisableEditButton(boolean disableEditButton) {
        this.disableEditButton = disableEditButton;
    }

    public boolean isDisableEndTime() {
        return disableEndTime;
    }

    public void setDisableEndTime(boolean disableEndTime) {
        this.disableEndTime = disableEndTime;
    }

    public boolean isDisableRemarks() {
        return disableRemarks;
    }

    public void setDisableRemarks(boolean disableRemarks) {
        this.disableRemarks = disableRemarks;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public boolean isDisableStartTime() {
        return disableStartTime;
    }

    public void setDisableStartTime(boolean disableStartTime) {
        this.disableStartTime = disableStartTime;
    }

    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }

    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public SimpleDateFormat getMdyFormat() {
        return mdyFormat;
    }

    public void setMdyFormat(SimpleDateFormat mdyFormat) {
        this.mdyFormat = mdyFormat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public SimpleDateFormat getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(SimpleDateFormat timeFormat) {
        this.timeFormat = timeFormat;
    }

    public int getTotalEmpRecords() {
        return totalEmpRecords;
    }

    public void setTotalEmpRecords(int totalEmpRecords) {
        this.totalEmpRecords = totalEmpRecords;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isShowBusGrid() {
        return showBusGrid;
    }

    public void setShowBusGrid(boolean showBusGrid) {
        this.showBusGrid = showBusGrid;
    }
}
