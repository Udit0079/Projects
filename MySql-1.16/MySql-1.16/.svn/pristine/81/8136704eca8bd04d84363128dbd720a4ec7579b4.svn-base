/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.report.DayActivityPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.DailyProcessManagementRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class DailyProcess extends BaseBean {

    private String tempBd;
    private boolean DFlag = false;
    private String monthEndFlag;
    private String yearEndFlag;
    private String dayEndFlag;
    private String dayBeginFlag;
    private String reportFlag = "none";
    private String monthEndName;
    private String errorMsg;
    private String yearEndDate;
    private String msgStyle = "error";
    private String viewID = "/pages/master/sm/test.jsp";
    private final String jndiHomeName = "DailyProcessManagementFacade";
    private DailyProcessManagementRemote beanRemote = null;
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /**
     * ***************************************Start of Getter and
     * Setter*********************************
     */
    public String getMsgStyle() {
        return msgStyle;
    }

    public void setMsgStyle(String msgStyle) {
        this.msgStyle = msgStyle;
    }

    public String getMonthEndName() {
        return monthEndName;
    }

    public void setMonthEndName(String monthEndName) {
        this.monthEndName = monthEndName;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getTempBd() {
        return tempBd;
    }

    public void setTempBd(String tempBd) {
        this.tempBd = tempBd;
    }

    public String getMonthEndFlag() {
        return monthEndFlag;
    }

    public void setMonthEndFlag(String monthEndFlag) {
        this.monthEndFlag = monthEndFlag;
    }

    public String getYearEndFlag() {
        return yearEndFlag;
    }

    public void setYearEndFlag(String yearEndFlag) {
        this.yearEndFlag = yearEndFlag;
    }

    public boolean isDFlag() {
        return DFlag;
    }

    public void setDFlag(boolean DFlag) {
        this.DFlag = DFlag;
    }

    public String getDayBeginFlag() {
        return dayBeginFlag;
    }

    public void setDayBeginFlag(String dayBeginFlag) {
        this.dayBeginFlag = dayBeginFlag;
    }

    public String getDayEndFlag() {
        return dayEndFlag;
    }

    public void setDayEndFlag(String dayEndFlag) {
        this.dayEndFlag = dayEndFlag;
    }

    public String getReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(String reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * ***************************************End of Getter and
     * Setter*********************************
     */
    public DailyProcess() {
        try {
            beanRemote = (DailyProcessManagementRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            checkDayBeginOnFormLoad();
        } catch (ApplicationException e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        }
    }

    public void checkDayBeginOnFormLoad() {
        monthEndFlag = "true";
        yearEndFlag = "true";
        try {
            List listBankDays = beanRemote.selectFromBankDays(getOrgnBrCode());
            if (!listBankDays.isEmpty()) {
                Vector vecBankDays = (Vector) listBankDays.get(0);
                tempBd = vecBankDays.get(0).toString();
                if (DFlag == false) {
                    DFlag = true;
                    List list1 = beanRemote.selectMinDateFromBnkDays(getOrgnBrCode());
                    if (!list1.isEmpty()) {
                        Vector v1 = (Vector) list1.get(0);
                        Long longTempBd = Long.parseLong(tempBd);
                        Long longMinDate = Long.parseLong(v1.get(0).toString().trim());
                        if (longTempBd.equals(longMinDate)) {
                            dayEndFlag = "true";
                            dayBeginFlag = "false";
                            return;
                        }
                    }
                    String tdDate = getTodayDate().substring(6) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2);
                    Long longTdDate = Long.parseLong(tdDate);
                    String minMonthDate = beanRemote.getMinMonthDate(getOrgnBrCode());

                    monthEndName = getMonthName(Integer.parseInt(minMonthDate.substring(4, 6)));
                    Long longMonthDate = Long.parseLong(minMonthDate);
                    if (longTdDate.compareTo(longMonthDate) > 0 || longTdDate.compareTo(longMonthDate) == 0) {
                        monthEndFlag = "false";
                        this.setMsgStyle("msg");
                        this.setErrorMsg("You have to run the month end process for the month of " + monthEndName);
                    }
                    if (beanRemote.isHolidayDate(minMonthDate, getOrgnBrCode())) {
                        String maxDt = beanRemote.maxWorkingDate(minMonthDate, getOrgnBrCode());
                        if (maxDt == null || maxDt.equalsIgnoreCase("")) {
                            String holiday = CbsUtil.dateAdd(tdDate, 1);
                            if (beanRemote.isHolidayDate(holiday, getOrgnBrCode())) {
                                monthEndFlag = "false";
                                this.setMsgStyle("msg");
                                this.setErrorMsg("You have to run the month end process for the month of " + monthEndName);
                            }
                        } else {
                            if (ymd.parse(tdDate).compareTo(ymd.parse(maxDt)) == 0) {
                                monthEndFlag = "false";
                                this.setMsgStyle("msg");
                                this.setErrorMsg("You have to run the month end process for the month of " + monthEndName);
                            }
                        }
                    }

                    yearEndDate = beanRemote.selectMinDateFromYearEnd(getOrgnBrCode());
                    Long longYearEndDt = Long.parseLong(yearEndDate);
                    if (longTdDate.compareTo(longYearEndDt) > 0 || longTdDate.compareTo(longYearEndDt) == 0) {
                        yearEndFlag = "false";
                    }
                    if (beanRemote.isHolidayDate(yearEndDate,getOrgnBrCode())) {
                        String maxDt = beanRemote.maxWorkingDate(yearEndDate,getOrgnBrCode());
                        if (maxDt == null || maxDt.equalsIgnoreCase("")) {
                            String holiday = CbsUtil.dateAdd(tdDate, 1);
                            if (beanRemote.isHolidayDate(holiday,getOrgnBrCode())) {
                                yearEndFlag = "false";
                            }
                        } else {
                            if (ymd.parse(tdDate).compareTo(ymd.parse(maxDt)) == 0) {
                                yearEndFlag = "false";
                            }
                        }
                    }

                    List list4 = beanRemote.selectFromBank2Days(getOrgnBrCode());
                    if (!list4.isEmpty()) {
                        Vector v4 = (Vector) list4.get(0);
                        String date1 = v4.get(0).toString();
                        Long date6 = Long.parseLong(date1.substring(0, 4) + date1.substring(4, 6) + date1.substring(6));
                        if (date6.equals(longTdDate)) {
                            dayEndFlag = "false";
                            dayBeginFlag = "true";
                        }
                    }
                    List list5 = beanRemote.selectFromBank3Days(getOrgnBrCode());
                    if (!list5.isEmpty()) {
                        Vector v5 = (Vector) list5.get(0);
                        String date2 = v5.get(0).toString();
                        Long date7 = Long.parseLong(date2);
                        if (date7.equals(longTdDate)) {
                            dayEndFlag = "false";
                            dayBeginFlag = "true";
                        }
                    }
                }
            } else {
                dayEndFlag = "true";
                dayBeginFlag = "false";
            }
        } catch (ApplicationException e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        }
    }

    private String getMonthName(int month) {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
        return monthName[month - 1];
    }

    public void dayBeginProcess() {
        try {
            List list8 = beanRemote.selectFromCBSBankDays(getTodayDate());
            if (!list8.isEmpty()) {
                Vector v8 = (Vector) list8.get(0);
                String dayBegin = v8.get(0).toString();
                String dayEnd = v8.get(1).toString();
                if (dayBegin.equalsIgnoreCase("N") && dayEnd.equalsIgnoreCase("N")) {
                    this.setMsgStyle("error");
                    this.setErrorMsg("Sorry!! Bank has not started the day.");
                    return;
                } else if (dayBegin.equalsIgnoreCase("Y") && dayEnd.equalsIgnoreCase("Y")) {
                    this.setMsgStyle("error");
                    this.setErrorMsg("Sorry!! The day has been end. You can't begin again.");
                    return;
                }
            } else {
                this.setMsgStyle("error");
                this.setErrorMsg("Sorry!! Bank has not started the day.");
                return;
            }
            this.setErrorMsg("");
            synchronized (beanRemote) {
                String result = beanRemote.dayBeginProcess(getOrgnBrCode(), getTodayDate(), getUserName());
                if (result.equals("True")) {
                    setDayBeginFlag("true");
                    this.setMsgStyle("msg");
                    setErrorMsg("Day Begin Process successfully completed.");
                } else {
                    this.setMsgStyle("error");
                    setErrorMsg(result);
                }
                //checkDayBeginOnFormLoad();
            }
        } catch (ApplicationException e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        }
    }

    public void dayEndProcess() {
        try {
           String result = "";
           if(yearEndFlag.equalsIgnoreCase("false")){
               if(beanRemote.isYearEndCompleted(yearEndDate,getOrgnBrCode())){
                   result = beanRemote.dayEndProcess(tempBd, getOrgnBrCode(), getUserName());
               }else{
                   result = "Please execute the Year End process before Day End Process";
               }
           }else{
               result = beanRemote.dayEndProcess(tempBd, getOrgnBrCode(), getUserName());
           }
           if (result.equals("True")) {
                this.setMsgStyle("msg");
                setErrorMsg("Day End Process successfully completed.");
                setDayEndFlag("true");
                reportFlag = "";
            } else {
                this.setMsgStyle("error");
                setErrorMsg(result);
            }
        } catch (ApplicationException e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getDayActivityReport() {
        try {
            List<DayActivityPojo> dayActivityReport = beanRemote.getDayActivityReport(getOrgnBrCode(), getTodayDate());
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParam = new HashMap();
            fillParam.put("pReportName", "Day Actitvity Report");
            fillParam.put("pPrintedDate", getTodayDate());
            fillParam.put("pPrintedBy", getUserName());
            fillParam.put("pBankName", branchNameandAddress.get(0));
            fillParam.put("pBankAdd", branchNameandAddress.get(1));
            new ReportBean().jasperReport("dayActivityReport", "text/html", new JRBeanCollectionDataSource(dayActivityReport), fillParam, "Day Activity Report");
            this.setViewID("/report/ReportPagePopUp.jsp");
        } catch (Exception e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        }

    }

    /**
     * ****************************Month End
     * Process**********************************
     */
    public void monthEndProcess() {
        try {
            String result = beanRemote.monthEndProcess(monthEndName, getOrgnBrCode(), getTodayDate(), getUserName());
            if (!result.equalsIgnoreCase("True")) {
                this.setMsgStyle("error");
                this.setErrorMsg(result);
                return;
            } else {
                this.setMsgStyle("msg");
                this.setErrorMsg("Month End Process successfully completed.");
                checkDayBeginOnFormLoad();
            }
        } catch (ApplicationException e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        }
    }

    /**
     * ***************************** Code For Year
     * End******************************
     */
    public void yearEndProcess() {
        try {
            //yearEndChk();
            String yearEndResult = beanRemote.yearEndProcess(tempBd, getOrgnBrCode(), getUserName());

            if (yearEndResult.equalsIgnoreCase("TRUE")) {
                this.setMsgStyle("msg");
                this.setErrorMsg("Year End Process successfully completed.");
                checkDayBeginOnFormLoad();
            } else {
                this.setMsgStyle("error");
                this.setErrorMsg(yearEndResult);
            }
        } catch (ApplicationException e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsgStyle("error");
            this.setErrorMsg(e.getMessage());
        }
    }

    /**
     * ******************************************************************************
     */
    public void refreshForm() {
        this.setErrorMsg("");
        reportFlag = "none";
    }

    public String exitForm() {
        refreshForm();
        this.setErrorMsg("");
        return "case1";
    }
}
