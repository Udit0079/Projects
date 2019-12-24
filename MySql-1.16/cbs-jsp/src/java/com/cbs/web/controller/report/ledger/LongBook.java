package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.LongBookPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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

public class LongBook extends BaseBean {

    private String msg;
    private String selectAcType;
    private List<SelectItem> selectAcTypeList;
    private String selectAcStatus;
    private List<SelectItem> selectAcStatusList;
    private String date;
    private boolean chkBoxValue;
    private String selectHH1;
    private List<SelectItem> selectHH1List;
    private String selectMM1;
    private List<SelectItem> selectMM1List;
    private String selectSS1;
    private List<SelectItem> selectSS1List;
    private String selectHH2;
    private List<SelectItem> selectHH2List;
    private String selectMM2;
    private List<SelectItem> selectMM2List;
    private String selectSS2;
    String flag;
    private OtherReportFacadeRemote ledgerReportFacadeLocal;
    private CommonReportMethodsRemote common;
    private List<SelectItem> selectSS2List;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    List<SelectItem> orderByList;
    private String orderBy;
    private String focusId;
    private boolean disabled;

    public LongBook() {
        try {
            ledgerReportFacadeLocal = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            selectAcStatusList = new ArrayList<SelectItem>();
            selectAcStatusList.add(new SelectItem("Select", "--Select--"));
            selectAcStatusList.add(new SelectItem("1", "OPERATIVE"));
            selectAcStatusList.add(new SelectItem("0", "INOPERATIVE"));
            selectAcTypeList = new ArrayList<SelectItem>();
            selectAcTypeList.add(new SelectItem("0", "--Select--"));

            Vector vtr = new Vector();
            List actype = common.getAccTypeExcludePO();

            for (int i = 0; i < actype.size(); i++) {
                vtr = (Vector) actype.get(i);
                selectAcTypeList.add(new SelectItem(vtr.get(0).toString()));
            }
            date = sdf.format(new Date());
            selectHH1List = new ArrayList<SelectItem>();
            selectHH2List = new ArrayList<SelectItem>();
            selectMM1List = new ArrayList<SelectItem>();
            selectMM2List = new ArrayList<SelectItem>();
            selectSS1List = new ArrayList<SelectItem>();
            selectSS1List.add(new SelectItem("AM"));
            selectSS1List.add(new SelectItem("PM"));
            selectSS2List = new ArrayList<SelectItem>();
            selectSS2List.add(new SelectItem("AM"));
            selectSS2List.add(new SelectItem("PM"));
            for (Integer i = 0; i <= 12; i++) {
                String hour = i.toString();
                int length = hour.length();
                if (length < 2) {
                    hour = "0" + hour;
                }
                selectHH1List.add(new SelectItem(hour));
                selectHH2List.add(new SelectItem(hour));
            }
            for (Integer j = 0; j <= 60; j++) {
                String minute = j.toString();
                int length = minute.length();
                if (length < 2) {
                    minute = "0" + minute;
                }
                selectMM1List.add(new SelectItem(minute));
                selectMM2List.add(new SelectItem(minute));
            }
            flag = "display:none";
            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("a.acno", "Account Number"));
            orderByList.add(new SelectItem("recno", "Txn ID"));
            setOrderBy("trantime");
        } catch (ApplicationException e) {
            msg = e.getMessage();
        } catch (Exception e) {
            msg = e.getMessage();
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSelectAcStatus() {
        return selectAcStatus;
    }

    public void setSelectAcStatus(String selectAcStatus) {
        this.selectAcStatus = selectAcStatus;
    }

    public List<SelectItem> getSelectAcStatusList() {
        return selectAcStatusList;
    }

    public void setSelectAcStatusList(List<SelectItem> selectAcStatusList) {
        this.selectAcStatusList = selectAcStatusList;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public List<SelectItem> getSelectAcTypeList() {
        return selectAcTypeList;
    }

    public void setSelectAcTypeList(List<SelectItem> selectAcTypeList) {
        this.selectAcTypeList = selectAcTypeList;
    }

    public String getSelectHH1() {
        return selectHH1;
    }

    public void setSelectHH1(String selectHH1) {
        this.selectHH1 = selectHH1;
    }

    public List<SelectItem> getSelectHH1List() {
        return selectHH1List;
    }

    public void setSelectHH1List(List<SelectItem> selectHH1List) {
        this.selectHH1List = selectHH1List;
    }

    public String getSelectHH2() {
        return selectHH2;
    }

    public void setSelectHH2(String selectHH2) {
        this.selectHH2 = selectHH2;
    }

    public List<SelectItem> getSelectHH2List() {
        return selectHH2List;
    }

    public void setSelectHH2List(List<SelectItem> selectHH2List) {
        this.selectHH2List = selectHH2List;
    }

    public String getSelectMM1() {
        return selectMM1;
    }

    public void setSelectMM1(String selectMM1) {
        this.selectMM1 = selectMM1;
    }

    public List<SelectItem> getSelectMM1List() {
        return selectMM1List;
    }

    public void setSelectMM1List(List<SelectItem> selectMM1List) {
        this.selectMM1List = selectMM1List;
    }

    public String getSelectMM2() {
        return selectMM2;
    }

    public void setSelectMM2(String selectMM2) {
        this.selectMM2 = selectMM2;
    }

    public List<SelectItem> getSelectMM2List() {
        return selectMM2List;
    }

    public void setSelectMM2List(List<SelectItem> selectMM2List) {
        this.selectMM2List = selectMM2List;
    }

    public String getSelectSS1() {
        return selectSS1;
    }

    public void setSelectSS1(String selectSS1) {
        this.selectSS1 = selectSS1;
    }

    public List<SelectItem> getSelectSS1List() {
        return selectSS1List;
    }

    public void setSelectSS1List(List<SelectItem> selectSS1List) {
        this.selectSS1List = selectSS1List;
    }

    public String getSelectSS2() {
        return selectSS2;
    }

    public void setSelectSS2(String selectSS2) {
        this.selectSS2 = selectSS2;
    }

    public List<SelectItem> getSelectSS2List() {
        return selectSS2List;
    }

    public void setSelectSS2List(List<SelectItem> selectSS2List) {
        this.selectSS2List = selectSS2List;
    }

    public boolean isChkBoxValue() {
        return chkBoxValue;
    }

    public void setChkBoxValue(boolean chkBoxValue) {
        this.chkBoxValue = chkBoxValue;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
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

    public void dateValidate() {
        if (date.equals(getTodayDate())) {
            this.setDisabled(false);
            this.setFocusId("ddOrderBy");
        } else {
            if(orderBy.equalsIgnoreCase("recno")){
                this.setOrderBy("recno");
            } else if(orderBy.equalsIgnoreCase("a.acno")) {
                this.setOrderBy("a.acno");
            }
            this.setDisabled(false);
            this.setFocusId("ddOrderBy");
        }
    }
    public String viewReport() {
        msg = validation();
        String printTime1 = "";
        String printTime2 = "";
        if (msg.equalsIgnoreCase("ok")) {
            try {
                String dt1[] = date.split("/");
                String dd3 = dt1[2] + "-" + dt1[1] + "-" + dt1[0];
                String reportDate = dt1[0] + "/" + dt1[1] + "/" + dt1[2];
                String timeAllowed = "";
                if (this.chkBoxValue) {
                    timeAllowed = "1";
                } else {
                    timeAllowed = "0";
                }
                String fromTime = "";
                if (this.selectSS1.equalsIgnoreCase("AM")) {
                    fromTime = dd3 + " " + selectHH1 + ":" + selectMM1 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(selectHH1) == 12) {
                        hour = Integer.parseInt(selectHH1) + 11;
                    } else {
                        hour = Integer.parseInt(selectHH1) + 12;
                    }
                    fromTime = dd3 + " " + hour + ":" + selectMM1 + ":00";
                }
                String toTime = "";
                if (selectSS2.equalsIgnoreCase("AM")) {
                    toTime = dd3 + " " + selectHH2 + ":" + selectMM2 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(this.selectHH2) == 12) {
                        hour = Integer.parseInt(selectHH2) + 11;
                    } else {
                        hour = Integer.parseInt(this.selectHH2) + 12;
                    }
                    toTime = dd3 + " " + hour + ":" + selectMM2 + ":00";
                }

                if (chkBoxValue) {
                    if (selectHH1.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + selectMM1 + selectSS1;
                    } else {
                        printTime1 = selectHH1 + ":" + selectMM1 + "" + selectSS1;
                    }
                    if (selectHH2.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + selectMM2 + "" + selectSS2;
                    } else {
                        printTime2 = selectHH2 + ":" + selectMM2 + "" + selectSS2;
                    }
                    reportDate = reportDate + " " + printTime1 + " to " + printTime2;
                }

                List<LongBookPojo> reportList = ledgerReportFacadeLocal.getLongBook(selectAcType, Integer.parseInt(selectAcStatus), dd3, getOrgnBrCode(), fromTime, toTime, Integer.parseInt(timeAllowed),orderBy);
                if (!reportList.isEmpty()) {
                    String report = common.getAcctDecription(selectAcType) + " " + (selectAcStatus.equalsIgnoreCase("1") ? "OPERATIVE" : "INOPERATIVE ");
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDate", reportDate);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    ReportBean rb = new ReportBean();
                    rb.jasperReport("long_book", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Long Book");
                    return "report";
                } else {
                    msg = "Data does not lie";
                }
            } catch (ApplicationException e) {
                msg = e.getMessage();
            } catch (Exception e) {
                msg = e.getMessage();
            }
        }
        return null;
    }

    public String pdfDownLoad() {
        msg = validation();
        String printTime1 = "";
        String printTime2 = "";
        String report = "Long Book";
        if (msg.equalsIgnoreCase("ok")) {
            try {
                String dt1[] = date.split("/");
                String dd3 = dt1[2] + "-" + dt1[1] + "-" + dt1[0];
                String reportDate = dt1[0] + "/" + dt1[1] + "/" + dt1[2];
                String timeAllowed = "";
                if (this.chkBoxValue) {
                    timeAllowed = "1";
                } else {
                    timeAllowed = "0";
                }
                String fromTime = "";
                if (this.selectSS1.equalsIgnoreCase("AM")) {
                    fromTime = dd3 + " " + selectHH1 + ":" + selectMM1 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(selectHH1) == 12) {
                        hour = Integer.parseInt(selectHH1) + 11;
                    } else {
                        hour = Integer.parseInt(selectHH1) + 12;
                    }
                    fromTime = dd3 + " " + hour + ":" + selectMM1 + ":00";
                }
                String toTime = "";
                if (selectSS2.equalsIgnoreCase("AM")) {
                    toTime = dd3 + " " + selectHH2 + ":" + selectMM2 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(this.selectHH2) == 12) {
                        hour = Integer.parseInt(selectHH2) + 11;
                    } else {
                        hour = Integer.parseInt(this.selectHH2) + 12;
                    }
                    toTime = dd3 + " " + hour + ":" + selectMM2 + ":00";
                }

                if (chkBoxValue) {
                    if (selectHH1.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + selectMM1 + selectSS1;
                    } else {
                        printTime1 = selectHH1 + ":" + selectMM1 + "" + selectSS1;
                    }
                    if (selectHH2.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + selectMM2 + "" + selectSS2;
                    } else {
                        printTime2 = selectHH2 + ":" + selectMM2 + "" + selectSS2;
                    }
                    reportDate = reportDate + " " + printTime1 + " to " + printTime2;
                }

                List<LongBookPojo> reportList = ledgerReportFacadeLocal.getLongBook(selectAcType, Integer.parseInt(selectAcStatus), dd3, getOrgnBrCode(), fromTime, toTime, Integer.parseInt(timeAllowed),orderBy);
                if (!reportList.isEmpty()) {
                    String report1 = common.getAcctDecription(selectAcType) + " " + (selectAcStatus.equalsIgnoreCase("1") ? "Opeartive" : "InOpeartive ");
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", report1);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDate", reportDate);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);

                    new ReportBean().openPdf("long_book_", "long_book", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    msg = "Data does not lie";
                }
            } catch (ApplicationException e) {
                msg = e.getMessage();
            } catch (Exception e) {
                msg = e.getMessage();
            }
        }
        return null;
    }

    public void refreshForm() {
        flag = "display:none";
        date = sdf.format(new Date());
        chkBoxValue = false;
        selectAcStatus = "Select";
        selectAcType = "0";
        msg = "";
    }

    public String exitAction() {
        return "case1";
    }

    public void showTimeBox() {
        if (chkBoxValue) {
            flag = "display:''";
        } else {
            flag = "display:none";
        }
    }

    public String validation() {
        if (selectAcStatus.equalsIgnoreCase("S")) {
            return "Please Select A/C. Status";
        } else if (selectAcType.equalsIgnoreCase("0")) {
            return "Please Select A/C. Type.";
        } else if (date.equalsIgnoreCase("")) {
            return "Please Enter Date.";
        }
        return "ok";
    }
}
