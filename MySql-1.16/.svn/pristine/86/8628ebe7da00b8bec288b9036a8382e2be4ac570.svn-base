/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.TransactionCountPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.common.exception.ApplicationException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class TransactionCount extends BaseBean {

    private String message;
    private String stxtDate;
    private String dropDown2;
    private List<SelectItem> dropDown2List;
    private String dropDown3;
    private List<SelectItem> dropDown3List;
    private String dropDown4;
    private List<SelectItem> dropDown4List;
    private String dropDown5;
    private List<SelectItem> dropDown5List;
    private String dropDown6;
    private List<SelectItem> dropDown6List;
    private String dropDown7;
    private List<SelectItem> dropDown7List;
    private String frDate;
    private String toDate;
    private boolean checkbox1;
    private String timeFlag;
    private List<SelectItem> branchOptionList;
    private String branchOption;
    private final String jndiHomeName = "LedgerReportFacade";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private LedgerReportFacadeRemote ledgerReportRemote = null;
    private CommonReportMethodsRemote common;
    private List<TransactionCountPojo> reportList = new ArrayList<TransactionCountPojo>();

    public TransactionCount() {
        try {
            ledgerReportRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

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
            this.setFrDate(sdf.format(new Date()));
            this.setToDate(sdf.format(new Date()));
            branchOptionList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchOptionList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString() : vec.get(1).toString(), vec.get(0).toString()));
                }
            }
            this.setDropDown4("AM");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
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

    public void btnHtmlAction() {
        this.setMessage("");
        String report = "Transaction Summary";

        String timeAllowed = "";
        String date = this.frDate;
        if (frDate.equalsIgnoreCase("") || date == null) {
            this.setMessage("Please Enter Date");
            return;
        }
        if (toDate.equalsIgnoreCase("") || toDate == null) {
            this.setMessage("Please Enter Date");
            return;
        }

        String date2 = toDate;
        Map fillParams = new HashMap();
        try {

            String printTime1 = "";
            String printTime2 = "";
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
            String fromTime = "";
            String dt2[] = frDate.split("/");
            String fdate = dt2[2] + "-" + dt2[1] + "-" + dt2[0];
            String dt3[] = toDate.split("/");
            String tdate = dt3[2] + "-" + dt3[1] + "-" + dt3[0];
            if (this.dropDown4.equalsIgnoreCase("AM")) {
                fromTime = fdate + " " + this.dropDown2 + ":" + this.dropDown3 + ":00";
            } else {
                int hour;
                if (Integer.parseInt(this.dropDown2) == 12) {
                    hour = Integer.parseInt(this.dropDown2) + 11;
                } else {
                    hour = Integer.parseInt(this.dropDown2) + 12;
                }
                fromTime = fdate + " " + hour + ":" + this.dropDown3 + ":00";
            }
            String toTime = "";
            if (this.dropDown7.equalsIgnoreCase("AM")) {
                toTime = tdate + " " + this.dropDown5 + ":" + this.dropDown6 + ":00";
            } else {
                int hour;
                if (Integer.parseInt(this.dropDown5) == 12) {
                    hour = Integer.parseInt(this.dropDown5) + 11;
                } else {
                    hour = Integer.parseInt(this.dropDown5) + 12;
                }
                toTime = tdate + " " + hour + ":" + this.dropDown6 + ":00";
            }
            /**
             * *
             */
            String branchName = "", address = "";
            List bnkAddList = common.getBranchNameandAddress(this.getBranchOption());
            if (!bnkAddList.isEmpty()) {
                branchName = (String) bnkAddList.get(0);
                address = (String) bnkAddList.get(1);
            }
            if (timeAllowed.equalsIgnoreCase("Y")) {
                fillParams.put("pReportDt", this.frDate + " " + printTime1 + " To " + printTime2);
            } else {
                fillParams.put("pReportDt", this.frDate + " " + printTime1 + " To " + this.toDate + " " + printTime2);
            }
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);

            reportList = ledgerReportRemote.getTransactionCountData(fdate, tdate, this.getBranchOption(), fromTime, toTime, timeAllowed);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("TransactionCount", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Transaction Summary");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }
    
    public void viewPdfReport(){
        
        this.setMessage("");
        String report = "Transaction Summary";

        String timeAllowed = "";
        String date = this.frDate;
        if (frDate.equalsIgnoreCase("") || date == null) {
            this.setMessage("Please Enter Date");
            return;
        }
        if (toDate.equalsIgnoreCase("") || toDate == null) {
            this.setMessage("Please Enter Date");
            return;
        }

        String date2 = toDate;
        Map fillParams = new HashMap();
        try {

            String printTime1 = "";
            String printTime2 = "";
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
            String fromTime = "";
            String dt2[] = frDate.split("/");
            String fdate = dt2[2] + "-" + dt2[1] + "-" + dt2[0];
            String dt3[] = toDate.split("/");
            String tdate = dt3[2] + "-" + dt3[1] + "-" + dt3[0];
            if (this.dropDown4.equalsIgnoreCase("AM")) {
                fromTime = fdate + " " + this.dropDown2 + ":" + this.dropDown3 + ":00";
            } else {
                int hour;
                if (Integer.parseInt(this.dropDown2) == 12) {
                    hour = Integer.parseInt(this.dropDown2) + 11;
                } else {
                    hour = Integer.parseInt(this.dropDown2) + 12;
                }
                fromTime = fdate + " " + hour + ":" + this.dropDown3 + ":00";
            }
            String toTime = "";
            if (this.dropDown7.equalsIgnoreCase("AM")) {
                toTime = tdate + " " + this.dropDown5 + ":" + this.dropDown6 + ":00";
            } else {
                int hour;
                if (Integer.parseInt(this.dropDown5) == 12) {
                    hour = Integer.parseInt(this.dropDown5) + 11;
                } else {
                    hour = Integer.parseInt(this.dropDown5) + 12;
                }
                toTime = tdate + " " + hour + ":" + this.dropDown6 + ":00";
            }
            /**
             * *
             */
            String branchName = "", address = "";
            List bnkAddList = common.getBranchNameandAddress(this.getBranchOption());
            if (!bnkAddList.isEmpty()) {
                branchName = (String) bnkAddList.get(0);
                address = (String) bnkAddList.get(1);
            }
            if (timeAllowed.equalsIgnoreCase("Y")) {
                fillParams.put("pReportDt", this.frDate + " " + printTime1 + " To " + printTime2);
            } else {
                fillParams.put("pReportDt", this.frDate + " " + printTime1 + " To " + this.toDate + " " + printTime2);
            }
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);

            reportList = ledgerReportRemote.getTransactionCountData(fdate, tdate, this.getBranchOption(), fromTime, toTime, timeAllowed);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf("Transaction Summary_"+ymd.format(new Date()), "TransactionCount",new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        } 
    }

    public void refreshForm() {

        this.setFrDate(sdf.format(new Date()));
        this.setToDate(sdf.format(new Date()));
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStxtDate() {
        return stxtDate;
    }

    public void setStxtDate(String stxtDate) {
        this.stxtDate = stxtDate;
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

    public String getFrDate() {
        return frDate;
    }

    public void setFrDate(String frDate) {
        this.frDate = frDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public boolean isCheckbox1() {
        return checkbox1;
    }

    public void setCheckbox1(boolean checkbox1) {
        this.checkbox1 = checkbox1;
    }

    public String getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(String timeFlag) {
        this.timeFlag = timeFlag;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
    }

    public String getBranchOption() {
        return branchOption;
    }

    public void setBranchOption(String branchOption) {
        this.branchOption = branchOption;
    }
}
