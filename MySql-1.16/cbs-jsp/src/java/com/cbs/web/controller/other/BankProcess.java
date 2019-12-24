/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.pojo.MsgPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class BankProcess extends BaseBean {

    private String date;
    // private String errorMsg;
    boolean dayBeginFlag;
    boolean dayEndFlag;
    private String yearEndFlag;
    private String msgStyle = "error";
    private final String jndiHomeName = "BankProcessManagementFacade";
    private BankProcessManagementFacadeRemote beanRemote = null;
    private List<MsgPojo> msgList;
    private static final Object LOCK = new Object();
    
    public List<MsgPojo> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<MsgPojo> msgList) {
        this.msgList = msgList;
    }

    public String getMsgStyle() {
        return msgStyle;
    }

    public void setMsgStyle(String msgStyle) {
        this.msgStyle = msgStyle;
    }

//    public String getErrorMsg() {
//        return errorMsg;
//    }
//
//    public void setErrorMsg(String errorMsg) {
//        this.errorMsg = errorMsg;
//    }
    public boolean isDayBeginFlag() {
        return dayBeginFlag;
    }

    public void setDayBeginFlag(boolean dayBeginFlag) {
        this.dayBeginFlag = dayBeginFlag;
    }

    public boolean isDayEndFlag() {
        return dayEndFlag;
    }

    public void setDayEndFlag(boolean dayEndFlag) {
        this.dayEndFlag = dayEndFlag;
    }

    public String getYearEndFlag() {
        return yearEndFlag;
    }

    public void setYearEndFlag(String yearEndFlag) {
        this.yearEndFlag = yearEndFlag;
    }
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public BankProcess() {
        try {
            beanRemote = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            date = ymd.format(new Date());
            getInitialData();
        } catch (ApplicationException e) {
            msgList.add(getMsgObj("error", e.getMessage()));
        } catch (Exception ex) {
            msgList.add(getMsgObj("error", ex.getMessage()));
        }
    }

    public void getInitialData() {
        try {
            msgList = new ArrayList<MsgPojo>();
            yearEndFlag = "true";
            String result = beanRemote.getInitialInfo(date);
            if (result.equalsIgnoreCase("Y")) {
                dayBeginFlag = true;
                dayEndFlag = false;
            } else if (result == null || result.equalsIgnoreCase("")) {
                dayBeginFlag = false;
                dayEndFlag = true;
            } else {
                dayBeginFlag = false;
                dayEndFlag = true;
            }
            List list3 = beanRemote.getFinYear();

            if (list3.isEmpty()) {
                throw new ApplicationException("There is no data in the database for the current financial year. Please enter this entry and try again.");
            }
            Vector v3 = (Vector) list3.get(0);

            String finYear = v3.get(0).toString();
            String yearEndDate = Integer.parseInt(finYear) + 1 + "0331";

            String tdDate = getTodayDate().substring(6) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2);
            Long longTdDate = Long.parseLong(tdDate);

            if (longTdDate.compareTo(Long.parseLong(yearEndDate)) > 0 || longTdDate.compareTo(Long.parseLong(yearEndDate)) == 0) {
                yearEndFlag = "false";
            }
            if (beanRemote.isHolidayDate(yearEndDate)) {
                String maxDt = beanRemote.maxWorkingDate(yearEndDate);
                if (maxDt == null || maxDt.equalsIgnoreCase("")) {
                    String holiday = CbsUtil.dateAdd(tdDate, 1);
                    if (beanRemote.isHolidayDate(holiday)) {
                        yearEndFlag = "false";
                    }
                } else {
                    if (ymd.parse(tdDate).compareTo(ymd.parse(maxDt)) == 0) {
                        yearEndFlag = "false";
                    }
                }
            }

        } catch (ApplicationException e) {
            msgList.add(getMsgObj("error", e.getMessage()));
        } catch (Exception ex) {
            msgList.add(getMsgObj("error", ex.getMessage()));
        }
    }

    public void dayBeginProcess() {
        try {
            msgList = new ArrayList<>();
            synchronized (LOCK) {
                msgList = beanRemote.dayBeginProcess(date, getUserName());
            }            
            dayBeginFlag = true;
            dayEndFlag = true;
        } catch (Exception e) {
            msgList.add(getMsgObj("error", e.getMessage()));
        }
    }

    public void dayEndProcess() {
        try {
            msgList = new ArrayList<MsgPojo>();
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String directory = context.getInitParameter("cbsFiles");
            String ckycrTempImageLocation = context.getInitParameter("ckycrTempImageLocation");
            String ctsArchievalImgLocation = context.getInitParameter("cts") + "/BinaryImage/";

            String result = beanRemote.dayEndProcess(date, getUserName(), getOrgnBrCode(),
                    directory, ckycrTempImageLocation, ctsArchievalImgLocation);
            msgList.add(getMsgObj("msg", result));
            dayBeginFlag = true;
            dayEndFlag = true;
        } catch (ApplicationException e) {
            msgList.add(getMsgObj("error", e.getMessage()));
        } catch (Exception ex) {
            msgList.add(getMsgObj("error", ex.getMessage()));
        }
    }

    /*                                    Code For Year End                                */
    public void yearEndProcess() {
        try {
            msgList = new ArrayList<MsgPojo>();
            String yearEndResult = beanRemote.yearEndProcess();
            if (yearEndResult.equalsIgnoreCase("TRUE")) {
                msgList.add(getMsgObj("msg", "Year End Process successfully completed"));
                this.setYearEndFlag("true");
            } else {
                msgList.add(getMsgObj("error", yearEndResult));
                this.setYearEndFlag("false");
            }
        } catch (ApplicationException e) {
            msgList.add(getMsgObj("error", e.getMessage()));
        } catch (Exception ex) {
            setMsgStyle("error");
            msgList.add(getMsgObj("error", ex.getMessage()));
        }
    }

    private MsgPojo getMsgObj(String type, String msg) {
        MsgPojo msgPojo = new MsgPojo();
        msgPojo.setMsgType(type);

        msgPojo.setProcessName("");
        msgPojo.setMessage(msg);
        return msgPojo;
    }

    public String exitForm() {
        msgList = new ArrayList<MsgPojo>();
        return "case1";
    }
}
