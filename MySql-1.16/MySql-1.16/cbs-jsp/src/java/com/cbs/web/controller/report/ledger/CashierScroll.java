/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ledger;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.CbsCashClgTimewisePojo;
import com.cbs.dto.report.CbsCashTrfClgScrollTimewisePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class CashierScroll extends BaseBean {

    String message;
    String dropDown1;
    List<SelectItem> dropDown1List;
    String dropDown2;
    List<SelectItem> dropDown2List;
    String dropDown3;
    List<SelectItem> dropDown3List;
    String dropDown4;
    List<SelectItem> dropDown4List;
    String dropDown5;
    List<SelectItem> dropDown5List;
    String dropDown6;
    List<SelectItem> dropDown6List;
    String dropDown7;
    List<SelectItem> dropDown7List;
    String calDate;
    boolean checkbox1;
    String timeFlag;
    List<SelectItem> orderByList;
    private String orderBy;
    private String focusId;
    private boolean disabled;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "LedgerReportFacade";

    private LedgerReportFacadeRemote ledgerReportRemote = null;
    private CommonReportMethodsRemote common;

    List<CbsCashClgTimewisePojo> cashScrollDataList = new ArrayList<CbsCashClgTimewisePojo>();
    List<CbsCashTrfClgScrollTimewisePojo> cashScrollTimewiseDataList = new ArrayList<CbsCashTrfClgScrollTimewisePojo>();

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public boolean isCheckbox1() {
        return checkbox1;
    }

    public void setCheckbox1(boolean checkbox1) {
        this.checkbox1 = checkbox1;
    }

    public String getDropDown1() {
        return dropDown1;
    }

    public void setDropDown1(String dropDown1) {
        this.dropDown1 = dropDown1;
    }

    public List<SelectItem> getDropDown1List() {
        return dropDown1List;
    }

    public void setDropDown1List(List<SelectItem> dropDown1List) {
        this.dropDown1List = dropDown1List;
    }

    public String getDropDown2() {
        return dropDown2;
    }

    public void setDropDown2(String dropDown2) {
        this.dropDown2 = dropDown2;
    }

    public List<SelectItem> getDropDown2List() {
        return dropDown2List;
    }

    public void setDropDown2List(List<SelectItem> dropDown2List) {
        this.dropDown2List = dropDown2List;
    }

    public String getDropDown3() {
        return dropDown3;
    }

    public void setDropDown3(String dropDown3) {
        this.dropDown3 = dropDown3;
    }

    public List<SelectItem> getDropDown3List() {
        return dropDown3List;
    }

    public void setDropDown3List(List<SelectItem> dropDown3List) {
        this.dropDown3List = dropDown3List;
    }

    public String getDropDown4() {
        return dropDown4;
    }

    public void setDropDown4(String dropDown4) {
        this.dropDown4 = dropDown4;
    }

    public List<SelectItem> getDropDown4List() {
        return dropDown4List;
    }

    public void setDropDown4List(List<SelectItem> dropDown4List) {
        this.dropDown4List = dropDown4List;
    }

    public String getDropDown5() {
        return dropDown5;
    }

    public void setDropDown5(String dropDown5) {
        this.dropDown5 = dropDown5;
    }

    public List<SelectItem> getDropDown5List() {
        return dropDown5List;
    }

    public void setDropDown5List(List<SelectItem> dropDown5List) {
        this.dropDown5List = dropDown5List;
    }

    public String getDropDown6() {
        return dropDown6;
    }

    public void setDropDown6(String dropDown6) {
        this.dropDown6 = dropDown6;
    }

    public List<SelectItem> getDropDown6List() {
        return dropDown6List;
    }

    public void setDropDown6List(List<SelectItem> dropDown6List) {
        this.dropDown6List = dropDown6List;
    }

    public String getDropDown7() {
        return dropDown7;
    }

    public void setDropDown7(String dropDown7) {
        this.dropDown7 = dropDown7;
    }

    public List<SelectItem> getDropDown7List() {
        return dropDown7List;
    }

    public void setDropDown7List(List<SelectItem> dropDown7List) {
        this.dropDown7List = dropDown7List;
    }

    public String getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(String timeFlag) {
        this.timeFlag = timeFlag;
    }

    public CashierScroll() {
        try {
            ledgerReportRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            dropDown1List = new ArrayList<SelectItem>();

            List allBranchCodeList = common.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    dropDown1List.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString() : vec.get(1).toString(), vec.get(0).toString()));
                }
            }

            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("trantime", "Transaction Time"));
            orderByList.add(new SelectItem("a.acno", "Account Number"));
            orderByList.add(new SelectItem("recno", "Txn ID"));
            setOrderBy("trantime");

            dropDown3List = new ArrayList<SelectItem>();
            for (int i = 0; i <= 60; i++) {
                if (i < 10) {
                    dropDown3List.add(new SelectItem(String.valueOf("0" + i), String.valueOf("0" + i)));
                } else {
                    dropDown3List.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
                }
            }

            dropDown2List = new ArrayList<SelectItem>();
            for (int i = 0; i <= 12; i++) {
                if (i < 10) {
                    dropDown2List.add(new SelectItem(String.valueOf("0" + i), String.valueOf("0" + i)));
                } else {
                    dropDown2List.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
                }
            }

            dropDown4List = new ArrayList<SelectItem>();
            dropDown4List.add(new SelectItem("AM", "AM"));
            dropDown4List.add(new SelectItem("PM", "PM"));

            dropDown5List = new ArrayList<SelectItem>();
            for (int i = 0; i <= 12; i++) {
                if (i < 10) {
                    dropDown5List.add(new SelectItem(String.valueOf("0" + i), String.valueOf("0" + i)));
                } else {
                    dropDown5List.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
                }
            }

            dropDown7List = new ArrayList<SelectItem>();
            dropDown7List.add(new SelectItem("AM", "AM"));
            dropDown7List.add(new SelectItem("PM", "PM"));

            dropDown6List = new ArrayList<SelectItem>();
            for (int i = 0; i <= 60; i++) {
                if (i < 10) {
                    dropDown6List.add(new SelectItem(String.valueOf("0" + i), String.valueOf("0" + i)));
                } else {
                    dropDown6List.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
                }
            }
            this.setTimeFlag("none");
            this.setCalDate(sdf.format(new Date()));

        } catch (Exception ex) {
            this.setMessage(ex.getLocalizedMessage());
        }
    }

    public void dateValidate() {
        if (calDate.equals(getTodayDate())) {
            this.setDisabled(false);
            this.setFocusId("ddOrderBy");
        } else {
            if(orderBy.equalsIgnoreCase("recno")){
                this.setOrderBy("recno");
            } else if(orderBy.equalsIgnoreCase("a.acno")) {
                this.setOrderBy("a.acno");
            } else if(orderBy.equalsIgnoreCase("trantime")) {
                this.setOrderBy("trantime");
            }
            this.setDisabled(false);
            this.setFocusId("ddOrderBy");
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        String report = "Cashier Scroll";
//        String msg = this.dropDown1;
//        if (msg.equals("0")) {
//            this.setMessage("Please Select the Branch");
//            return;
//        }
        String timeAllowed = "";
        String date = this.calDate;
        if (calDate.equalsIgnoreCase("") || calDate == null) {
            this.setMessage("Please Enter Date");
            return;
        }
        String date2 = calDate;
        String user = getUserName();
        String printTime1 = "";
        String printTime2 = "";
        String toTime = "";
        String fromTime = "";
        String dt1[] = calDate.split("/");
        String frmDate = dt1[2] + "-" + dt1[1] + "-" + dt1[0];
        if (calDate.equals(getTodayDate())) {
            Map fillParams = new HashMap();
            try {
                if (this.checkbox1) {
                    if (dropDown4.equalsIgnoreCase("AM")) {
                        fromTime = frmDate + " " + dropDown2 + ":" + this.dropDown3 + ":00";
                    } else {
                        int hour;
                        if (Integer.parseInt(dropDown2) == 12) {
                            hour = Integer.parseInt(this.dropDown2) + 11;
                        } else {
                            hour = Integer.parseInt(this.dropDown2) + 12;
                        }
                        fromTime = frmDate + " " + hour + ":" + dropDown3 + ":00";
                    }

                    if (this.dropDown7.equalsIgnoreCase("AM")) {
                        toTime = frmDate + " " + this.dropDown5 + ":" + this.dropDown6 + ":00";
                    } else {
                        int hour;
                        if (Integer.parseInt(this.dropDown5) == 12) {
                            hour = Integer.parseInt(this.dropDown5) + 11;
                        } else {
                            hour = Integer.parseInt(this.dropDown5) + 12;
                        }
                        toTime = frmDate + " " + hour + ":" + this.dropDown6 + ":00";
                    }
                    timeAllowed = "Y";
                    if (this.dropDown2.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + this.dropDown3 + " " + this.dropDown4;
                    } else {
                        printTime1 = this.dropDown2 + ":" + this.dropDown3 + " " + this.dropDown4;
                    }
                    if (this.dropDown5.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + this.dropDown6 + " " + this.dropDown7;
                    } else {
                        printTime2 = this.dropDown5 + ":" + this.dropDown6 + " " + this.dropDown7;
                    }
                    date2 = date2 + " " + printTime1 + " To " + printTime2;
                } else if (this.checkbox1 == false) {
                    timeAllowed = "N";
                }
                /**
                 * *
                 */
                fillParams.put("pReportDate", date2);
                fillParams.put("SysDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", user);
                fillParams.put("pReportName", report);
                cashScrollDataList = ledgerReportRemote.getCbsCashClgScrollData(frmDate,
                        "CASH", dropDown1, fromTime, toTime, timeAllowed, orderBy);
                if (!cashScrollDataList.isEmpty()) {
                    new ReportBean().jasperReport("cashier_scroll", "text/html",
                            new JRBeanCollectionDataSource(cashScrollDataList), fillParams, "Cashier Scroll");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    this.setMessage("Data does not exist");
                }
            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        } else {

            Map fillParams = new HashMap();
            try {

                if (this.checkbox1) {
                    timeAllowed = "Y";
                    if (this.dropDown2.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + this.dropDown3 + " " + this.dropDown4;
                    } else {
                        printTime1 = this.dropDown2 + ":" + this.dropDown3 + " " + this.dropDown4;
                    }
                    if (this.dropDown5.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + this.dropDown6 + " " + this.dropDown7;
                    } else {
                        printTime2 = this.dropDown5 + ":" + this.dropDown6 + " " + this.dropDown7;
                    }
                    date2 = date2 + " " + printTime1 + " To " + printTime2;
                } else if (this.checkbox1 == false) {
                    timeAllowed = "N";
                }
                if (this.dropDown4.equalsIgnoreCase("AM")) {

                    fromTime = frmDate + " " + this.dropDown2 + ":" + this.dropDown3 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(this.dropDown2) == 12) {
                        hour = Integer.parseInt(this.dropDown2) + 11;
                    } else {
                        hour = Integer.parseInt(this.dropDown2) + 12;
                    }
                    fromTime = frmDate + " " + hour + ":" + this.dropDown3 + ":00";
                }
                if (this.dropDown7.equalsIgnoreCase("AM")) {
                    toTime = frmDate + " " + this.dropDown5 + ":" + this.dropDown6 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(this.dropDown5) == 12) {
                        hour = Integer.parseInt(this.dropDown5) + 11;
                    } else {
                        hour = Integer.parseInt(this.dropDown5) + 12;
                    }
                    toTime = frmDate + " " + hour + ":" + this.dropDown6 + ":00";
                }
                /**
                 * *
                 */
                fillParams.put("pReportDate", date2);
                fillParams.put("SysDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", user);
                fillParams.put("pReportName", report);
                String dt2[] = date.split("/");
                String indate = dt2[2] + dt2[1] + dt2[0];
                //pass Parameter "A" For Bifercation As in Transfer Scroll Hard Code "A" For ALL
                cashScrollTimewiseDataList = ledgerReportRemote.getCbsCashTrfClgScrollData(indate, "CASH", dropDown1, fromTime, toTime, timeAllowed,orderBy,"A");
                if (!cashScrollTimewiseDataList.isEmpty()) {
                    new ReportBean().jasperReport("cashier_scroll_1", "text/html",
                            new JRBeanCollectionDataSource(cashScrollTimewiseDataList), fillParams, "Cashier Scroll");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    this.setMessage("Data does not exist");
                }
            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        }
    }

    public void pdfDownLoad() {
        this.setMessage("");
        String report = "Cashier Scroll";
//        String msg = this.dropDown1;
//        if (msg.equals("0")) {
//            this.setMessage("Please Select the Branch");
//            return;
//        }
        String timeAllowed = "";
        String date = this.calDate;
        if (calDate.equalsIgnoreCase("") || calDate == null) {
            this.setMessage("Please Enter Date");
            return;
        }
        String date2 = calDate;
        String user = getUserName();
        String printTime1 = "";
        String printTime2 = "";
        String toTime = "";
        String fromTime = "";
        String dt1[] = calDate.split("/");
        String frmDate = dt1[2] + "-" + dt1[1] + "-" + dt1[0];
        if (calDate.equals(getTodayDate())) {
            Map fillParams = new HashMap();
            try {
                if (this.checkbox1) {
                    if (dropDown4.equalsIgnoreCase("AM")) {
                        fromTime = frmDate + " " + dropDown2 + ":" + this.dropDown3 + ":00";
                    } else {
                        int hour;
                        if (Integer.parseInt(dropDown2) == 12) {
                            hour = Integer.parseInt(this.dropDown2) + 11;
                        } else {
                            hour = Integer.parseInt(this.dropDown2) + 12;
                        }
                        fromTime = frmDate + " " + hour + ":" + dropDown3 + ":00";
                    }

                    if (this.dropDown7.equalsIgnoreCase("AM")) {
                        toTime = frmDate + " " + this.dropDown5 + ":" + this.dropDown6 + ":00";
                    } else {
                        int hour;
                        if (Integer.parseInt(this.dropDown5) == 12) {
                            hour = Integer.parseInt(this.dropDown5) + 11;
                        } else {
                            hour = Integer.parseInt(this.dropDown5) + 12;
                        }
                        toTime = frmDate + " " + hour + ":" + this.dropDown6 + ":00";
                    }
                    timeAllowed = "Y";
                    if (this.dropDown2.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + this.dropDown3 + " " + this.dropDown4;
                    } else {
                        printTime1 = this.dropDown2 + ":" + this.dropDown3 + " " + this.dropDown4;
                    }
                    if (this.dropDown5.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + this.dropDown6 + " " + this.dropDown7;
                    } else {
                        printTime2 = this.dropDown5 + ":" + this.dropDown6 + " " + this.dropDown7;
                    }
                    date2 = date2 + " " + printTime1 + " To " + printTime2;
                } else if (this.checkbox1 == false) {
                    timeAllowed = "N";
                }
                /**
                 * *
                 */
                fillParams.put("pReportDate", date2);
                fillParams.put("SysDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", user);
                fillParams.put("pReportName", report);
                cashScrollDataList = ledgerReportRemote.getCbsCashClgScrollData(frmDate,
                        "CASH", dropDown1, fromTime, toTime, timeAllowed, orderBy);
                if (!cashScrollDataList.isEmpty()) {
                    new ReportBean().openPdf("cashier_scroll_" + ymd.format(sdf.parse(this.getTodayDate())), "cashier_scroll", new JRBeanCollectionDataSource(cashScrollDataList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    this.setMessage("Data does not exist");
                }
            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        } else {

            Map fillParams = new HashMap();
            try {

                if (this.checkbox1) {
                    timeAllowed = "Y";
                    if (this.dropDown2.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + this.dropDown3 + " " + this.dropDown4;
                    } else {
                        printTime1 = this.dropDown2 + ":" + this.dropDown3 + " " + this.dropDown4;
                    }
                    if (this.dropDown5.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + this.dropDown6 + " " + this.dropDown7;
                    } else {
                        printTime2 = this.dropDown5 + ":" + this.dropDown6 + " " + this.dropDown7;
                    }
                    date2 = date2 + " " + printTime1 + " To " + printTime2;
                } else if (this.checkbox1 == false) {
                    timeAllowed = "N";
                }
                if (this.dropDown4.equalsIgnoreCase("AM")) {

                    fromTime = frmDate + " " + this.dropDown2 + ":" + this.dropDown3 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(this.dropDown2) == 12) {
                        hour = Integer.parseInt(this.dropDown2) + 11;
                    } else {
                        hour = Integer.parseInt(this.dropDown2) + 12;
                    }
                    fromTime = frmDate + " " + hour + ":" + this.dropDown3 + ":00";
                }
                if (this.dropDown7.equalsIgnoreCase("AM")) {
                    toTime = frmDate + " " + this.dropDown5 + ":" + this.dropDown6 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(this.dropDown5) == 12) {
                        hour = Integer.parseInt(this.dropDown5) + 11;
                    } else {
                        hour = Integer.parseInt(this.dropDown5) + 12;
                    }
                    toTime = frmDate + " " + hour + ":" + this.dropDown6 + ":00";
                }
                /**
                 * *
                 */
                fillParams.put("pReportDate", date2);
                fillParams.put("SysDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", user);
                fillParams.put("pReportName", report);
                String dt2[] = date.split("/");
                String indate = dt2[2] + dt2[1] + dt2[0];
                //pass Parameter "A" For Bifercation As in Transfer Scroll Hard Code "A" For ALL
                cashScrollTimewiseDataList = ledgerReportRemote.getCbsCashTrfClgScrollData(indate, "CASH", dropDown1, fromTime, toTime, timeAllowed,orderBy,"A");
                if (!cashScrollTimewiseDataList.isEmpty()) {
                    new ReportBean().openPdf("cashier_scroll_", "cashier_scroll_1", new JRBeanCollectionDataSource(cashScrollTimewiseDataList), fillParams);

                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    this.setMessage("Data does not exist");
                }
            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        }
    }
     public void excelDownload() {
        this.setMessage("");
        String report = "Cashier Scroll";
        String timeAllowed = "";
        String date = this.calDate;
        if (calDate.equalsIgnoreCase("") || calDate == null) {
            this.setMessage("Please Enter Date");
            return;
        }
        String date2 = calDate;
        String user = getUserName();
        String printTime1 = "";
        String printTime2 = "";
        String toTime = "";
        String fromTime = "";
        String dt1[] = calDate.split("/");
        String frmDate = dt1[2] + "-" + dt1[1] + "-" + dt1[0];        
        if (calDate.equals(getTodayDate())) {
            Map fillParams = new HashMap();
            try {
                if (this.checkbox1) {
                    if (dropDown4.equalsIgnoreCase("AM")) {
                        fromTime = frmDate + " " + dropDown2 + ":" + this.dropDown3 + ":00";
                    } else {
                        int hour;
                        if (Integer.parseInt(dropDown2) == 12) {
                            hour = Integer.parseInt(this.dropDown2) + 11;
                        } else {
                            hour = Integer.parseInt(this.dropDown2) + 12;
                        }
                        fromTime = frmDate + " " + hour + ":" + dropDown3 + ":00";
                    }
                    if (this.dropDown7.equalsIgnoreCase("AM")) {
                        toTime = frmDate + " " + this.dropDown5 + ":" + this.dropDown6 + ":00";
                    } else {
                        int hour;
                        if (Integer.parseInt(this.dropDown5) == 12) {
                            hour = Integer.parseInt(this.dropDown5) + 11;
                        } else {
                            hour = Integer.parseInt(this.dropDown5) + 12;
                        }
                        toTime = frmDate + " " + hour + ":" + this.dropDown6 + ":00";
                    }
                    timeAllowed = "Y";
                    if (this.dropDown2.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + this.dropDown3 + " " + this.dropDown4;
                    } else {
                        printTime1 = this.dropDown2 + ":" + this.dropDown3 + " " + this.dropDown4;
                    }
                    if (this.dropDown5.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + this.dropDown6 + " " + this.dropDown7;
                    } else {
                        printTime2 = this.dropDown5 + ":" + this.dropDown6 + " " + this.dropDown7;
                    }
                    date2 = date2 + " " + printTime1 + " To " + printTime2;
                } else if (this.checkbox1 == false) {
                    timeAllowed = "N";
                }
                fillParams.put("pReportDate", date2);
                fillParams.put("SysDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", user);
                fillParams.put("pReportName", report);
                cashScrollDataList = ledgerReportRemote.getCbsCashClgScrollData(frmDate,
                        "CASH", dropDown1, fromTime, toTime, timeAllowed, orderBy);
                if (!cashScrollDataList.isEmpty()) {
                    FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cashScrollTimewiseDataList), fastReportBuilder, report+this.calDate);
                } else {
                    this.setMessage("Data does not exist");
                }
            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        } else {
            Map fillParams = new HashMap();
            try {
                if (this.checkbox1) {
                    timeAllowed = "Y";
                    if (this.dropDown2.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + this.dropDown3 + " " + this.dropDown4;
                    } else {
                        printTime1 = this.dropDown2 + ":" + this.dropDown3 + " " + this.dropDown4;
                    }
                    if (this.dropDown5.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + this.dropDown6 + " " + this.dropDown7;
                    } else {
                        printTime2 = this.dropDown5 + ":" + this.dropDown6 + " " + this.dropDown7;
                    }
                    date2 = date2 + " " + printTime1 + " To " + printTime2;
                } else if (this.checkbox1 == false) {
                    timeAllowed = "N";
                }
                if (this.dropDown4.equalsIgnoreCase("AM")) {
                    fromTime = frmDate + " " + this.dropDown2 + ":" + this.dropDown3 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(this.dropDown2) == 12) {
                        hour = Integer.parseInt(this.dropDown2) + 11;
                    } else {
                        hour = Integer.parseInt(this.dropDown2) + 12;
                    }
                    fromTime = frmDate + " " + hour + ":" + this.dropDown3 + ":00";
                }
                if (this.dropDown7.equalsIgnoreCase("AM")) {
                    toTime = frmDate + " " + this.dropDown5 + ":" + this.dropDown6 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(this.dropDown5) == 12) {
                        hour = Integer.parseInt(this.dropDown5) + 11;
                    } else {
                        hour = Integer.parseInt(this.dropDown5) + 12;
                    }
                    toTime = frmDate + " " + hour + ":" + this.dropDown6 + ":00";
                }
                fillParams.put("pReportDate", date2);
                fillParams.put("SysDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", user);
                fillParams.put("pReportName", report);
                String dt2[] = date.split("/");
                String indate = dt2[2] + dt2[1] + dt2[0]; 
                //pass Parameter "A" For Bifercation As in Transfer Scroll Hard Code "A" For ALL
                cashScrollTimewiseDataList = ledgerReportRemote.getCbsCashTrfClgScrollData(indate, "CASH", dropDown1, fromTime, toTime, timeAllowed,orderBy,"A");
                if (!cashScrollTimewiseDataList.isEmpty()) {
                    FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cashScrollTimewiseDataList), fastReportBuilder, report +this.calDate );
                } else {
                    this.setMessage("Data does not exist");
                }
            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        }
    }
     
  public FastReportBuilder genrateDaynamicReport() {
      FastReportBuilder fastReport = new FastReportBuilder();
      try {
          int width = 0;
          Style style = new Style();
          style.setHorizontalAlign(HorizontalAlign.CENTER);
          style.setBorder(Border.PEN_2_POINT);
          style.setFont(ar.com.fdvs.dj.domain.constants.Font.ARIAL_BIG_BOLD);
          Style style1 = new Style();
          style1.setHorizontalAlign(HorizontalAlign.CENTER);
          style1.setBorder(Border.PEN_2_POINT);
          style1.setFont(ar.com.fdvs.dj.domain.constants.Font.TIMES_NEW_ROMAN_BIG_BOLD);
          
          AbstractColumn recno = ReportBean.getColumn("recno", Double.class, "Transaction ID", 100);
          AbstractColumn accNo = ReportBean.getColumn("accNo", String.class, "Account Number", 80);
          AbstractColumn accName = ReportBean.getColumn("accName", String.class, "Customer Name", 250);
          AbstractColumn crBal = ReportBean.getColumn("crBal", BigDecimal.class, "Receipts(Cr.)", 80);
          AbstractColumn drBal = ReportBean.getColumn("drBal", BigDecimal.class, "Payments(Dr.)", 80);
          AbstractColumn enterBy = ReportBean.getColumn("enterBy", String.class, "Enter By", 100);
          AbstractColumn authBy = ReportBean.getColumn("authBy", String.class, "Auth By", 100);
          
          fastReport.addColumn(recno);
          width = width + recno.getWidth();          
          
          fastReport.addColumn(accNo);
          width = width + accNo.getWidth();
          
          fastReport.addColumn(accName);
          width = width + accName.getWidth();          
          
          fastReport.addColumn(crBal);
          width = width + crBal.getWidth();
          
          fastReport.addColumn(drBal);
          width = width + drBal.getWidth();
          
          fastReport.addColumn(enterBy);
          width = width + enterBy.getWidth();
          
          fastReport.addColumn(authBy);
          width = width + authBy.getWidth();                    
                    
          
          Page page = new Page(842, width, false);
          
          String bankName = "", bankAddress = "" ;
          List dataList1 = common.getBranchNameandAddress(dropDown1);
          if (dataList1.size() > 0) {
              bankName = (String) dataList1.get(0);
              bankAddress = (String) dataList1.get(1);
          }
          fastReport.setMargins(0, 0, 0, 0);
          fastReport.setPageSizeAndOrientation(page);
          fastReport.setTitleStyle(style);
          fastReport.setTitleHeight(40);
          fastReport.setTitle(bankName.concat(", ").concat(bankAddress));
          fastReport.setSubtitleStyle(style1);
          fastReport.setSubtitle("CASHIER SCROLL "+"("+calDate+")");
          
      } catch (Exception e) {
          e.printStackTrace();
      }
      return fastReport;
  }
    public void checkbox1ProcessValueChange() {
        if (this.checkbox1) {
            this.setTimeFlag("");
            this.setDropDown2("00");
            this.setDropDown3("00");
            this.setDropDown4("AM");
            this.setDropDown5("00");
            this.setDropDown6("00");
            this.setDropDown7("AM");
        } else {
            this.setTimeFlag("none");
            this.setDropDown2("00");
            this.setDropDown3("00");
            this.setDropDown4("AM");
            this.setDropDown5("00");
            this.setDropDown6("00");
            this.setDropDown7("AM");
        }
    }

    public void refreshForm() {
        this.setDropDown1("00");
        this.setOrderBy("trantime");
        this.setCalDate(sdf.format(new Date()));
        this.setCheckbox1(false);
        this.setTimeFlag("none");
        this.setMessage("");
        this.setDropDown2("00");
        this.setDropDown3("00");
        this.setDropDown4("AM");
        this.setDropDown5("00");
        this.setDropDown6("00");
        this.setDropDown7("AM");
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }
}
