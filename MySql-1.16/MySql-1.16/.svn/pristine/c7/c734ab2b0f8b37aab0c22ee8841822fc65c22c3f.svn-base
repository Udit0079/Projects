/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.HolidayMasterTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrHolidayMasterTO;
import com.hrms.common.to.HrHolidayMasterTOPK;
import com.hrms.common.to.PayrollCalendarPKTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class HolidayMaster extends BaseBean {

    private static final Logger logger = Logger.getLogger(HolidayMaster.class);
    private String function;
    private String option;
    private int compCode;
    private Date fromDate;
    private Date toDate;
    private Date holidayFromDt;
    private Date holidayToDt;
    private String holidayDesc;
    private String weekDay;
    private String focusId;
    private HolidayMasterTable currentItem;
    private boolean weekHoliday;
    private boolean disableSave;
    private List<HolidayMasterTable> dataTable;
    private List<SelectItem> functionList;
    private List<SelectItem> optionList;
    private List<SelectItem> weekDaysList;
    private String message;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /** Creates a new instance of HolidayMaster */
    public HolidayMaster() {
        try {
            compCode = Integer.parseInt(getOrgnBrCode());
            initData();
        } catch (Exception e) {
            setMessage(e.getMessage());
            logger.error("Exception occured while executing method HolidayMaster()", e);
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("1", "ADD"));
            functionList.add(new SelectItem("2", "VIEW"));

            optionList = new ArrayList<SelectItem>();
            optionList.add(new SelectItem("1", "SIMPLE"));
            optionList.add(new SelectItem("2", "WEEKLY"));

            weekDaysList = new ArrayList<SelectItem>();
            weekDaysList.add(new SelectItem("0", "--SELECT--"));
            weekDaysList.add(new SelectItem("SUNDAY"));
            weekDaysList.add(new SelectItem("MONDAY"));

            weekDaysList.add(new SelectItem("TUESDAY"));
            weekDaysList.add(new SelectItem("WEDNESDAY"));
            weekDaysList.add(new SelectItem("THRUSDAY"));

            weekDaysList.add(new SelectItem("FRIDAY"));
            weekDaysList.add(new SelectItem("FRIDAY"));
            weekDaysList.add(new SelectItem("SATURDAY"));

            PayrollMasterManagementDelegate masterMgmtDelegate = new PayrollMasterManagementDelegate();
            List<PayrollCalendarTO> payrollCalendarTOs = masterMgmtDelegate.getFinancialYear(compCode);
            for (PayrollCalendarTO payrollCalendarTO : payrollCalendarTOs) {
                fromDate = payrollCalendarTO.getPayrollCalendarPKTO().getDateFrom();
                toDate = payrollCalendarTO.getPayrollCalendarPKTO().getDateTo();
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onChangeFunction() {
        if (function.equals("0")) {
            setMessage("Please select a function");
            System.out.println(getMessage());
            return;
        } else if (function.equals("1")) {
        } else if (function.equals("2")) {
            getHolidayList();
        }
    }

    public void setWeeklyHoliday() {
        if (option.equals("1")) {
            weekHoliday = false;
            setWeekDay("0");
            setFocusId("calIntDate1");
        } else {
            setFocusId("weekDayList");
            weekHoliday = true;
            if(getFunction().equals("2")){
                setDisableSave(true);
            }else{
                setDisableSave(false);
            }
        }
    }

    public void getHolidayList() {
        try {
            PayrollMasterManagementDelegate masterMgmtDelegate = new PayrollMasterManagementDelegate();
            List<HrHolidayMasterTO> hrHolidayMasterTOs = masterMgmtDelegate.getHolidayList(compCode, fromDate, toDate);
            if (hrHolidayMasterTOs.isEmpty()) {
                setMessage("There is no data.");
            }
            dataTable = new ArrayList<HolidayMasterTable>();
            HolidayMasterTable item;
            for (HrHolidayMasterTO obj : hrHolidayMasterTOs) {
                item = new HolidayMasterTable();
                item.setHolidayCode(obj.getHrHolidayMasterPKTO().getHolidayCode());
                item.setHolidaydate(sdf.format(obj.getHolidayDate()));
                item.setHolidayday(obj.getWeekDays());
                item.setHolidaydes(obj.getHolidayDesc());
                item.setHolidayfromdt((obj.getHolidayDateFrom()));
                item.setHolidaytodt(obj.getHolidayDateTo());
                dataTable.add(item);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void selectItem() {
        setHolidayFromDt(currentItem.getHolidayfromdt());
        setHolidayToDt(currentItem.getHolidaytodt());
        setWeekDay(currentItem.getHolidayday());
        setHolidayDesc(currentItem.getHolidaydes());
    }

    private HrHolidayMasterTO createHolidayMasterTO() {
        HrHolidayMasterTO holidayMasterTO = new HrHolidayMasterTO();
        PayrollCalendarTO payrollCalTO = new PayrollCalendarTO();
        HrHolidayMasterTOPK holidayMasterPKTO = new HrHolidayMasterTOPK();

        PayrollCalendarPKTO payrollCalPKTO = new PayrollCalendarPKTO();
        payrollCalPKTO.setCompCode(compCode);
        payrollCalPKTO.setDateFrom(fromDate);
        payrollCalPKTO.setDateTo(toDate);
        payrollCalTO.setPayrollCalendarPKTO(payrollCalPKTO);

        holidayMasterPKTO.setCompCode(compCode);
        holidayMasterTO.setHrHolidayMasterPKTO(holidayMasterPKTO);

        holidayMasterTO.setHolidayDateFrom(getHolidayFromDt());
        holidayMasterTO.setHolidayDateTo(getHolidayToDt());
        holidayMasterTO.setWeekDays(getWeekDay());
        if (getOption().equals("1")) {
            holidayMasterTO.setHolidayDesc(getHolidayDesc());
        } else {
            holidayMasterTO.setHolidayDesc(getWeekDay());
        }

        holidayMasterTO.setEnteredBy(getUserName());
        holidayMasterTO.setAuthBy(getUserName());
        holidayMasterTO.setDefaultComp(compCode);

        holidayMasterTO.setModDate(new Date());
        holidayMasterTO.setStatFlag("Y");
        holidayMasterTO.setStatUpFlag("Y");
        holidayMasterTO.setHrPayrollCalendar(payrollCalTO);
        return holidayMasterTO;
    }

    public void saveDetails() {
        try {
            String msg = validation();
            if (!msg.equalsIgnoreCase("True")) {
                setMessage(msg);
                return;
            }
            PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
            HrHolidayMasterTO holidayMasterTO = createHolidayMasterTO();
            if (getFunction().equals("1")) {
                msg = obj.saveHolidayMasterDetails(compCode, getOption(), holidayMasterTO);
                if (msg.equalsIgnoreCase("True")) {
                    setMessage("Holiday details saved successfully.");
                }
            } else {
                holidayMasterTO.getHrHolidayMasterPKTO().setHolidayCode(currentItem.getHolidayCode());
                msg = obj.updateHolidayMasterDetails(holidayMasterTO);
                if (msg.equalsIgnoreCase("True")) {
                    setMessage("Holiday details updated successfully.");
                }
            }
            setFunction("0");
            setOption("1");
            setHolidayFromDt(null);
            setHolidayToDt(null);
            setHolidayDesc("");
            setWeekDay("0");
        } catch (Exception e) {
            setMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDetails() {
        try {
//            String msg = validation();
//            if (!msg.equalsIgnoreCase("True")) {
//                setErrMsg(msg);
//                return;
//            }
            PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
            HrHolidayMasterTOPK holidayMasterTOPK;
            List<HrHolidayMasterTOPK> holidayMasterTOPKList = new ArrayList<HrHolidayMasterTOPK>();
            if (getOption().equals("1")) {
                holidayMasterTOPK = new HrHolidayMasterTOPK();
                holidayMasterTOPK.setCompCode(compCode);
                holidayMasterTOPK.setHolidayCode(currentItem.getHolidayCode());
                holidayMasterTOPKList.add(holidayMasterTOPK);
            } else {
                for (HolidayMasterTable item : dataTable) {
                    if (item.getHolidayday().equalsIgnoreCase(getWeekDay()) && item.getHolidaydes().equalsIgnoreCase(getWeekDay())) {
                        holidayMasterTOPK = new HrHolidayMasterTOPK();
                        holidayMasterTOPK.setCompCode(compCode);
                        holidayMasterTOPK.setHolidayCode(item.getHolidayCode());
                        holidayMasterTOPKList.add(holidayMasterTOPK);
                    }
                }
            }
           String msg = obj.deleteHolidayMasterDetails(holidayMasterTOPKList);
            if (msg.equalsIgnoreCase("True")) {
                setMessage("Holiday details deleted successfully.");
                setFunction("0");
                setOption("1");
                setHolidayFromDt(null);
                setHolidayToDt(null);
                setHolidayDesc("");
                setWeekDay("0");
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void refresh() {
        setMessage("");
        setFunction("0");
        setOption("1");
        setHolidayFromDt(null);
        setHolidayToDt(null);
        setHolidayDesc("");
        setWeekDay("0");
    }

    public String exit() {
        return "case1";
    }

    public String validation() {
        if (getOption().equals("1")) {
            if (holidayFromDt == null) {
                return "Please select Holiday Date from";
            }
            if (holidayToDt == null) {
                return "Please select Holiday Date to";
            }
            if (holidayDesc == null || holidayDesc.equalsIgnoreCase("")) {
                return "Please give Holiday Description";
            }
            if(holidayFromDt.before(fromDate) || holidayFromDt.after(toDate)){
                return "Holyday date from should be between calendar year";
            }
            if(holidayToDt.before(fromDate) || holidayToDt.after(toDate)){
                return "Holyday date to should be between calendar year";
            } 
            else {
                return "True";
            }
        } else {
            if (weekDay == null) {
                return "Please select week Day";
            } else {
                return "True";
            }
        }
    }

    public boolean isWeekHoliday() {
        return weekHoliday;
    }

    public void setWeekHoliday(boolean weekHoliday) {
        this.weekHoliday = weekHoliday;
    }

    public List<HolidayMasterTable> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<HolidayMasterTable> dataTable) {
        this.dataTable = dataTable;
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

    public String getHolidayDesc() {
        return holidayDesc;
    }

    public void setHolidayDesc(String holidayDesc) {
        this.holidayDesc = holidayDesc;
    }

    public Date getHolidayFromDt() {
        return holidayFromDt;
    }

    public void setHolidayFromDt(Date holidayFromDt) {
        this.holidayFromDt = holidayFromDt;
    }

    public Date getHolidayToDt() {
        return holidayToDt;
    }

    public void setHolidayToDt(Date holidayToDt) {
        this.holidayToDt = holidayToDt;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public List<SelectItem> getWeekDaysList() {
        return weekDaysList;
    }

    public void setWeekDaysList(List<SelectItem> weekDaysList) {
        this.weekDaysList = weekDaysList;
    }

    public HolidayMasterTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(HolidayMasterTable currentItem) {
        this.currentItem = currentItem;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

   
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
