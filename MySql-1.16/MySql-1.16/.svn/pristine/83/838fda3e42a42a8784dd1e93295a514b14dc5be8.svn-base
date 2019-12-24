package com.hrms.web.controller.masters;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstShiftPKTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.pojo.ShiftMasterTable;
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
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ShiftMaster extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(ShiftMaster.class);
    private String description,
            timeIn,
            timeOut,
            breakFrom,
            breakTo,
            graceTimeIn,
            graceTimeOut,
            graceBreakTime,
            odTimeFirstHalf,
            odTimeSecondHalf,
            statFlag,
            statUpFlag,
            message,
            calFromDate,
            calToDate,
            mode,
            enteredBy,
            authBy, shiftCode;
    private Date dtTimeOut,
            dtBreakFrom,
            dtBreakTo,
            dtGraceTimeIn,
            dtGraceTimeOut,
            dtGraceBreakTime,
            dtOdTimeFirstHalf,
            dtOdTimeSecondHalf,
            dtTimeIn;
    Calendar cal;
    private HttpServletRequest request;
    private boolean shiftFound;
    List<ShiftMasterTable> shiftMasterTable;
    ShiftMasterTable currentShiftRow;
    private ShiftMasterTable currentShiftItem = new ShiftMasterTable();
    private int defaultComp,
            compCode,
            totalShiftRecords,
            currentRow;
    DefinitionsDelegate definitionsDelegate;
    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private boolean showShiftTable,
            disableDescription,
            disableShiftCode,
            disableTimeOut,
            disableBreakFrom,
            disableBreakTo,
            disableGraceTimeIn,
            disableGraceTimeOut,
            disableGraceBreakTime,
            disableOdisableimeFirstHalf,
            disableOdisableimeSecondHalf,
            disableTimeIn,
            disableSaveButton,
            disableDeleteButton;
    private String operation;
    private List<SelectItem> operationList;

    public ShiftMaster() {
        try {
            definitionsDelegate = new DefinitionsDelegate();
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
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
            shiftFound = false;
            onLoadMode();
            calFromDate = (WebUtil.getFinancialYear(compCode)).get(0);
            calToDate = (WebUtil.getFinancialYear(compCode)).get(1);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method ShiftMaster()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method ShiftMaster()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getShiftMasterTableData() {
        
        try {
            shiftMasterTable = new ArrayList<ShiftMasterTable>();
            int i = 0;
            List<HrMstShiftTO> hrMstShiftTOs = definitionsDelegate.getShiftMasterByCompCode(compCode);
            if (hrMstShiftTOs != null) {
                for (HrMstShiftTO hrMstShiftTO : hrMstShiftTOs) {
                    currentShiftRow = new ShiftMasterTable();
                    currentShiftRow.setSno(++i);
                    currentShiftRow.setShiftCode(hrMstShiftTO.getHrMstShiftPKTO().getShiftCode());
                    currentShiftRow.setDescription(hrMstShiftTO.getShiftDesc());
                    currentShiftRow.setTimeIn(timeFormat.format(hrMstShiftTO.getShiftIn()));
                    currentShiftRow.setTimeOut(timeFormat.format(hrMstShiftTO.getShiftOut()));
                    currentShiftRow.setBreakFrom(timeFormat.format(hrMstShiftTO.getShiftBfrom()));
                    currentShiftRow.setBreakTo(timeFormat.format(hrMstShiftTO.getShiftBto()));
                    currentShiftRow.setGraceTimeIn(timeFormat.format(hrMstShiftTO.getGraceShiftIn()));
                    currentShiftRow.setGraceTimeOut(timeFormat.format(hrMstShiftTO.getGraceShiftOut()));
                    currentShiftRow.setGraceBreakTime(timeFormat.format(hrMstShiftTO.getGraceShiftBreak()));
                    currentShiftRow.setOdTimeFirstHalf(timeFormat.format(hrMstShiftTO.getOdTimeFirst()));
                    currentShiftRow.setOdTimeSecondHalf(timeFormat.format(hrMstShiftTO.getOdTimeSecond()));
                    shiftMasterTable.add(currentShiftRow);
                }
            } else {
                setMessage("No Data");
            }
            totalShiftRecords = i;
        } catch (WebException e) {
            logger.error("Exception occured while executing method getShiftMasterTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getShiftMasterTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }
     public void setOperation()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        }
        else if(operation.equalsIgnoreCase("1"))
        {
            addAction();
        }  
        else if(operation.equalsIgnoreCase("2"))
        {
            editAction();
        }
    }
    public void addAction() {
        try {
            mode = "save";
            setMessage("Please Fill Details !!");
            enableFields();
            setDisableSaveButton(false);
            setDisableDeleteButton(true);
           } catch (Exception e) {
            logger.error("Exception occured while executing method addAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveAction() {
        try {
            if (validate()) {
                if (validate24HourTime(timeIn) && validate24HourTime(timeOut) && validate24HourTime(breakFrom) && validate24HourTime(breakTo) && validate24HourTime(graceTimeIn) && validate24HourTime(graceTimeOut) && validate24HourTime(graceBreakTime) && validate24HourTime(odTimeFirstHalf) && validate24HourTime(odTimeSecondHalf)) {
                    dtTimeIn = timeFormat.parse(timeIn);
                    dtTimeOut = timeFormat.parse(timeOut);
//                    if(dtTimeOut.compareTo(dtTimeIn)<=0)
//                    {
//                        setMessage("Time out must be greater than time in !");
//                        return;
//                    }
                    dtBreakFrom = timeFormat.parse(breakFrom);
                    dtBreakTo = timeFormat.parse(breakTo);
//                    if(dtBreakTo.compareTo(dtBreakFrom)<=0)
//                    {
//                        setMessage("Break to must be greater than break from !");
//                        return;
//                    }
                    dtGraceTimeIn = timeFormat.parse(graceTimeIn);
                    dtGraceTimeOut = timeFormat.parse(graceTimeOut);
//                      if(dtGraceTimeOut.compareTo(dtGraceTimeIn)<=0)
//                    {
//                        setMessage("Grace time out must be greater than grace time in !");
//                        return;
//                    }
                    dtGraceBreakTime = timeFormat.parse(graceBreakTime);
//                    if(dtGraceBreakTime.compareTo(dtGraceTimeIn)>=0&&dtGraceTimeOut.compareTo(dtGraceBreakTime)<=0)
//                    {
//                        setMessage("Grace break time must be grater than Grace time in and lower than Grace time out !");
//                        return;
//                    }
                    dtOdTimeFirstHalf = timeFormat.parse(odTimeFirstHalf);
                    dtOdTimeSecondHalf = timeFormat.parse(odTimeSecondHalf);
//                    if(dtOdTimeSecondHalf.compareTo(dtOdTimeFirstHalf)<=0)
//                    {
//                        setMessage("Od time second half time  must be greater than Od time first half !");
//                        return;
//                    }
                    authBy = getUserName();
                    enteredBy = getUserName();
                    performAction();
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void editAction() {
        try {
            getShiftMasterTableData();
            setDisableDeleteButton(false);
            setDisableSaveButton(false);
            if (!shiftMasterTable.isEmpty()) {
                showShiftTable = true;
                setMessage(totalShiftRecords + " Shift Record(s) Found !!");
            } else {
                showShiftTable = false;
                  setOperation("0");
                setMessage("No Shift Record Found !!");
                setDisableDeleteButton(true);
            setDisableSaveButton(true);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteAction() {
        try {
            if (currentShiftItem.getShiftCode() != null) {
                HrMstShiftTO hrMstShiftTO = new HrMstShiftTO();
                HrMstShiftPKTO hrMstShiftPKTO = new HrMstShiftPKTO();
                hrMstShiftPKTO.setCompCode(compCode);
                hrMstShiftPKTO.setShiftCode(currentShiftItem.getShiftCode());
                hrMstShiftTO.setHrMstShiftPKTO(hrMstShiftPKTO);
                String result = definitionsDelegate.saveShiftMasterDetail(hrMstShiftTO, "delete");
                setMessage(result);
                cancelAction();
                 onLoadMode();
                 setOperation("0");
               
            } else {
                setMessage("Please Select Atleast One Row To Delete !!");
            }
            getShiftMasterTableData();
            
        } catch (WebException e) {
            setMessage("Database Exception Occured While Executing deleteAction():" + e.getCause());
        }  catch (Exception e) {
            System.out.println("-" + e.getCause());
            setMessage("Unknown Exception Occured While Executing deleteAction():" + e.getClass());
        }
    }

    public void cancelAction() {
        try {
            setShiftCode("");
            setDescription("");
            setTimeIn("");
            setTimeOut("");
            setBreakFrom("");
            setBreakTo("");
            setGraceTimeIn("");
            setGraceTimeOut("");
            setGraceBreakTime("");
            setOdTimeFirstHalf("");
            setOdTimeSecondHalf("");
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

    public void setShiftRowValues() {
        try {
            setMode("update");
            setMessage("");
            setDisableSaveButton(false);
            setDisableDeleteButton(false);
            setShiftCode(currentShiftItem.getShiftCode());
            setDescription(currentShiftItem.getDescription());
            setTimeIn(HrmsUtil.correct24HourTime(currentShiftItem.getTimeIn()));
            setTimeOut(HrmsUtil.correct24HourTime(currentShiftItem.getTimeOut()));
            setBreakFrom(HrmsUtil.correct24HourTime(currentShiftItem.getBreakFrom()));
            setBreakTo(HrmsUtil.correct24HourTime(currentShiftItem.getBreakTo()));
            setGraceTimeIn(HrmsUtil.correct24HourTime(currentShiftItem.getGraceTimeIn()));
            setGraceTimeOut(HrmsUtil.correct24HourTime(currentShiftItem.getGraceTimeOut()));
            setGraceBreakTime(HrmsUtil.correct24HourTime(currentShiftItem.getGraceBreakTime()));
            setOdTimeFirstHalf(HrmsUtil.correct24HourTime(currentShiftItem.getOdTimeFirstHalf()));
            setOdTimeSecondHalf(HrmsUtil.correct24HourTime(currentShiftItem.getOdTimeSecondHalf()));
            enableFields();
        } catch (Exception e) {
            logger.error("Exception occured while executing method setShiftRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public boolean performAction() {
       try {
            if (mode.equalsIgnoreCase("save")) {
                List<HrMstShiftTO> hrMstShiftTOs = definitionsDelegate.getShiftMasterByCompCode(compCode);
                if (hrMstShiftTOs != null) {
                    for (HrMstShiftTO hrMstShiftTO : hrMstShiftTOs) {
                        if (shiftCode.equalsIgnoreCase(hrMstShiftTO.getHrMstShiftPKTO().getShiftCode())) {
                            setMessage("Duplicate Shift Code");
                            return false;
                        }
                    }
                }
            }
            String result = definitionsDelegate.saveShiftMasterDetail(getHrMstShiftTO(), mode);
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
        return true;
    }

    public boolean validate() {
        if (shiftCode.equals("") || shiftCode == null) {
            setMessage("Shift Code Cannot Be Blank!");
            return false;
        }
        if (description.equals("") || description == null) {
            setMessage("Shift Description Cannot Be Blank!");
            return false;
        }
        return true;
    }

    public boolean validate24HourTime(String time) {
        if (time.matches("[0-9:]*") && time.length() == 5 && time.charAt(2) == ':') {
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(3));
            if (hour < 0 || minute < 0) {
                setMessage("Time in " + time + " cannot be negative !!");
                return false;
            }
            if (minute >= 60) {
                setMessage("Start Minute in " + time + " Cannot be equal to or greater than 60");
                return false;
            }
            if (hour >= 24) {
                setMessage("Hour in " + time + " Cannot be equal to or greater than 24");
                return false;
            }
        } else {
            setMessage("Incorrect Time Format !!");
            return false;
        }
        return true;
    }

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        disableFields();
         setDisableSaveButton(true);
        setDisableDeleteButton(true);
       
     }

    void enableFields() {
        setDisableShiftCode(false);
        setDisableBreakFrom(false);
        setDisableBreakTo(false);
        setDisableDescription(false);
        setDisableGraceBreakTime(false);
        setDisableGraceTimeIn(false);
        setDisableGraceTimeOut(false);
        setDisableOdisableimeFirstHalf(false);
        setDisableOdisableimeSecondHalf(false);
        setDisableTimeIn(false);
        setDisableTimeOut(false);
    }

    void disableFields() {
        setDisableShiftCode(true);
        setDisableBreakFrom(true);
        setDisableBreakTo(true);
        setDisableDescription(true);
        setDisableGraceBreakTime(true);
        setDisableGraceTimeIn(true);
        setDisableGraceTimeOut(true);
        setDisableOdisableimeFirstHalf(true);
        setDisableOdisableimeSecondHalf(true);
        setDisableTimeIn(true);
        setDisableTimeOut(true);
    }

    private HrMstShiftTO getHrMstShiftTO() {
        HrMstShiftTO hrMstShiftTO = new HrMstShiftTO();
        HrMstShiftPKTO hrMstShiftPKTO = new HrMstShiftPKTO();
        try {
            hrMstShiftPKTO.setCompCode(compCode);
            hrMstShiftPKTO.setShiftCode(shiftCode);
            hrMstShiftTO.setHrMstShiftPKTO(hrMstShiftPKTO);
            hrMstShiftTO.setAuthBy(authBy);
            hrMstShiftTO.setDefaultComp(defaultComp);
            hrMstShiftTO.setEnteredBy(enteredBy);
            hrMstShiftTO.setShiftIn(dtTimeIn);
            hrMstShiftTO.setShiftOut(dtTimeOut);
            hrMstShiftTO.setShiftBfrom(dtBreakFrom);
            hrMstShiftTO.setShiftBto(dtBreakTo);
            hrMstShiftTO.setGraceShiftBreak(dtGraceBreakTime);
            hrMstShiftTO.setGraceShiftIn(dtGraceTimeIn);
            hrMstShiftTO.setGraceShiftOut(dtGraceTimeOut);
            hrMstShiftTO.setOdTimeFirst(dtOdTimeFirstHalf);
            hrMstShiftTO.setOdTimeSecond(dtOdTimeSecondHalf);
            hrMstShiftTO.setModDate(Date.class.newInstance());
            hrMstShiftTO.setShiftDesc(description);
            hrMstShiftTO.setStatFlag(statFlag);
            hrMstShiftTO.setStatUpFlag(statUpFlag);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrMstShiftTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrMstShiftTO;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getBreakFrom() {
        return breakFrom;
    }

    public void setBreakFrom(String breakFrom) {
        this.breakFrom = breakFrom;
    }

    public String getBreakTo() {
        return breakTo;
    }

    public void setBreakTo(String breakTo) {
        this.breakTo = breakTo;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCalToDate() {
        return calToDate;
    }

    public void setCalToDate(String calToDate) {
        this.calToDate = calToDate;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public ShiftMasterTable getCurrentShiftItem() {
        return currentShiftItem;
    }

    public void setCurrentShiftItem(ShiftMasterTable currentShiftItem) {
        this.currentShiftItem = currentShiftItem;
    }

    public ShiftMasterTable getCurrentShiftRow() {
        return currentShiftRow;
    }

    public void setCurrentShiftRow(ShiftMasterTable currentShiftRow) {
        this.currentShiftRow = currentShiftRow;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isDisableBreakFrom() {
        return disableBreakFrom;
    }

    public void setDisableBreakFrom(boolean disableBreakFrom) {
        this.disableBreakFrom = disableBreakFrom;
    }

    public boolean isDisableBreakTo() {
        return disableBreakTo;
    }

    public void setDisableBreakTo(boolean disableBreakTo) {
        this.disableBreakTo = disableBreakTo;
    }


    public boolean isDisableDescription() {
        return disableDescription;
    }

    public void setDisableDescription(boolean disableDescription) {
        this.disableDescription = disableDescription;
    }
    public boolean isDisableGraceBreakTime() {
        return disableGraceBreakTime;
    }

    public void setDisableGraceBreakTime(boolean disableGraceBreakTime) {
        this.disableGraceBreakTime = disableGraceBreakTime;
    }

    public boolean isDisableGraceTimeIn() {
        return disableGraceTimeIn;
    }

    public void setDisableGraceTimeIn(boolean disableGraceTimeIn) {
        this.disableGraceTimeIn = disableGraceTimeIn;
    }

    public boolean isDisableGraceTimeOut() {
        return disableGraceTimeOut;
    }

    public void setDisableGraceTimeOut(boolean disableGraceTimeOut) {
        this.disableGraceTimeOut = disableGraceTimeOut;
    }

    public boolean isDisableOdisableimeFirstHalf() {
        return disableOdisableimeFirstHalf;
    }

    public void setDisableOdisableimeFirstHalf(boolean disableOdisableimeFirstHalf) {
        this.disableOdisableimeFirstHalf = disableOdisableimeFirstHalf;
    }

    public boolean isDisableOdisableimeSecondHalf() {
        return disableOdisableimeSecondHalf;
    }

    public void setDisableOdisableimeSecondHalf(boolean disableOdisableimeSecondHalf) {
        this.disableOdisableimeSecondHalf = disableOdisableimeSecondHalf;
    }

    public boolean isDisableShiftCode() {
        return disableShiftCode;
    }

    public void setDisableShiftCode(boolean disableShiftCode) {
        this.disableShiftCode = disableShiftCode;
    }

    public boolean isDisableTimeIn() {
        return disableTimeIn;
    }

    public void setDisableTimeIn(boolean disableTimeIn) {
        this.disableTimeIn = disableTimeIn;
    }

    public boolean isDisableTimeOut() {
        return disableTimeOut;
    }

    public void setDisableTimeOut(boolean disableTimeOut) {
        this.disableTimeOut = disableTimeOut;
    }

    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }

    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }

    public Date getDtBreakFrom() {
        return dtBreakFrom;
    }

    public void setDtBreakFrom(Date dtBreakFrom) {
        this.dtBreakFrom = dtBreakFrom;
    }

    public Date getDtBreakTo() {
        return dtBreakTo;
    }

    public void setDtBreakTo(Date dtBreakTo) {
        this.dtBreakTo = dtBreakTo;
    }

    public Date getDtGraceBreakTime() {
        return dtGraceBreakTime;
    }

    public void setDtGraceBreakTime(Date dtGraceBreakTime) {
        this.dtGraceBreakTime = dtGraceBreakTime;
    }

    public Date getDtGraceTimeIn() {
        return dtGraceTimeIn;
    }

    public void setDtGraceTimeIn(Date dtGraceTimeIn) {
        this.dtGraceTimeIn = dtGraceTimeIn;
    }

    public Date getDtGraceTimeOut() {
        return dtGraceTimeOut;
    }

    public void setDtGraceTimeOut(Date dtGraceTimeOut) {
        this.dtGraceTimeOut = dtGraceTimeOut;
    }

    public Date getDtOdTimeFirstHalf() {
        return dtOdTimeFirstHalf;
    }

    public void setDtOdTimeFirstHalf(Date dtOdTimeFirstHalf) {
        this.dtOdTimeFirstHalf = dtOdTimeFirstHalf;
    }

    public Date getDtOdTimeSecondHalf() {
        return dtOdTimeSecondHalf;
    }

    public void setDtOdTimeSecondHalf(Date dtOdTimeSecondHalf) {
        this.dtOdTimeSecondHalf = dtOdTimeSecondHalf;
    }

    public Date getDtTimeIn() {
        return dtTimeIn;
    }

    public void setDtTimeIn(Date dtTimeIn) {
        this.dtTimeIn = dtTimeIn;
    }

    public Date getDtTimeOut() {
        return dtTimeOut;
    }

    public void setDtTimeOut(Date dtTimeOut) {
        this.dtTimeOut = dtTimeOut;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getGraceBreakTime() {
        return graceBreakTime;
    }

    public void setGraceBreakTime(String graceBreakTime) {
        this.graceBreakTime = graceBreakTime;
    }

    public String getGraceTimeIn() {
        return graceTimeIn;
    }

    public void setGraceTimeIn(String graceTimeIn) {
        this.graceTimeIn = graceTimeIn;
    }

    public String getGraceTimeOut() {
        return graceTimeOut;
    }

    public void setGraceTimeOut(String graceTimeOut) {
        this.graceTimeOut = graceTimeOut;
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

    public String getOdTimeFirstHalf() {
        return odTimeFirstHalf;
    }

    public void setOdTimeFirstHalf(String odTimeFirstHalf) {
        this.odTimeFirstHalf = odTimeFirstHalf;
    }

    public String getOdTimeSecondHalf() {
        return odTimeSecondHalf;
    }

    public void setOdTimeSecondHalf(String odTimeSecondHalf) {
        this.odTimeSecondHalf = odTimeSecondHalf;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public boolean isShiftFound() {
        return shiftFound;
    }

    public void setShiftFound(boolean shiftFound) {
        this.shiftFound = shiftFound;
    }

    public List<ShiftMasterTable> getShiftMasterTable() {
        return shiftMasterTable;
    }

    public void setShiftMasterTable(List<ShiftMasterTable> shiftMasterTable) {
        this.shiftMasterTable = shiftMasterTable;
    }

    public boolean isShowShiftTable() {
        return showShiftTable;
    }

    public void setShowShiftTable(boolean showShiftTable) {
        this.showShiftTable = showShiftTable;
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

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public int getTotalShiftRecords() {
        return totalShiftRecords;
    }

    public void setTotalShiftRecords(int totalShiftRecords) {
        this.totalShiftRecords = totalShiftRecords;
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
    
}
