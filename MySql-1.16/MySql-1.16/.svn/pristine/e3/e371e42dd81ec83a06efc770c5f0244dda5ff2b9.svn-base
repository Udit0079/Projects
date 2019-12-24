/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.TransactionAggregatePojo;
import com.cbs.dto.report.TransactionAggregateTotalPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class TransactionAggregateReport extends BaseBean {

    private String todayDate;
    private String userName;
    private String errorMsg;
    private String reportType;
    private String accType;
    private Date frmDt;
    private Date toDt;
    private double amt;
    private String brnCode;
    private OtherReportFacadeRemote local;
    private List<SelectItem> acctTypeList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;

    /**
     * Creates a new instance of TransactionAggregateReport
     */
    public TransactionAggregateReport() {
        try {
            todayDate = dmy.format(date);
            frmDt = date;
            toDt = date;
            userName = getHttpRequest().getRemoteUser();
            InetAddress localhost = InetAddress.getByName(WebUtil.getClientIP(this.getHttpRequest()));
            brnCode = Init.IP2BR.getBranch(localhost);
            local = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acctTypeList = new ArrayList<SelectItem>();
            initData();
        } catch (Exception e) {
        }
    }

    private void initData() {
        acctTypeList.clear();
        try {
            List result = local.getAcctTypeList();
            Vector vtr = null;
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    vtr = (Vector) result.get(i);
                    acctTypeList.add(new SelectItem(vtr.get(0), vtr.get(0).toString()));
                }

            }
        } catch (Exception e) {
            errorMsg = new ApplicationException(e).getMessage();
        }

    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(Date frmDt) {
        this.frmDt = frmDt;
    }

    public OtherReportFacadeRemote getLocal() {
        return local;
    }

    public void setLocal(OtherReportFacadeRemote local) {
        this.local = local;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public String viewReport() {
        String report = "Deposit And Withdrawl Details Report";
        List<TransactionAggregatePojo> resultList = new ArrayList<TransactionAggregatePojo>();
        List<TransactionAggregateTotalPojo> resultList2 = new ArrayList<TransactionAggregateTotalPojo>();
        if (validate()) {


            try {

                if (reportType.length() == 1) {
                    resultList = local.transactionAggregateReport(Integer.parseInt(reportType), accType, amt, ymd.format(frmDt), ymd.format(toDt), brnCode);
                } else {
                    resultList2 = local.transactionAggregateReportTotal(Integer.parseInt(reportType), accType, amt, ymd.format(frmDt), ymd.format(toDt), brnCode);
                }

                if (!resultList.isEmpty() || !resultList2.isEmpty()) {
                    String s[] = common.getBranchNameandAddressString(brnCode).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pReportName", report);
                    fillParams.put("pReportDate", dmy.format(frmDt) + " to " + dmy.format(toDt));
                    fillParams.put("pAmt", amt);
                    fillParams.put("pAcType", accType);
                    fillParams.put("pTranType", reportType.equalsIgnoreCase("0") ? "Cash" : reportType.equalsIgnoreCase("1") ? "Clearing" : reportType.equalsIgnoreCase("2") ? "Transfer" : reportType.equalsIgnoreCase("00") ? "Total Cash" : reportType.equalsIgnoreCase("01") ? "Total Clearing" : "Total Transfer");
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    if (reportType.length() == 1) {
                        new ReportBean().jasperReport("Deposit_and_Withdrawl_Details_Report", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    } else {
                        new ReportBean().jasperReport("Deposit_and_Withdrawl_Details_Report_Total", "text/html", new JRBeanCollectionDataSource(resultList2), fillParams, report);
                    }

                    return "report";
                } else {
                    errorMsg = "Data Does Not Exist";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String pdfDownLoad() {
        String report = "Deposit And Withdrawl Details Report";
        List<TransactionAggregatePojo> resultList = new ArrayList<TransactionAggregatePojo>();
        List<TransactionAggregateTotalPojo> resultList2 = new ArrayList<TransactionAggregateTotalPojo>();
        if (validate()) {


            try {

                if (reportType.length() == 1) {
                    resultList = local.transactionAggregateReport(Integer.parseInt(reportType), accType, amt, ymd.format(frmDt), ymd.format(toDt), brnCode);
                } else {
                    resultList2 = local.transactionAggregateReportTotal(Integer.parseInt(reportType), accType, amt, ymd.format(frmDt), ymd.format(toDt), brnCode);
                }

                if (!resultList.isEmpty() || !resultList2.isEmpty()) {
                    String s[] = common.getBranchNameandAddressString(brnCode).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pReportName", report);
                    fillParams.put("pReportDate", dmy.format(frmDt) + " to " + dmy.format(toDt));
                    fillParams.put("pAmt", amt);
                    fillParams.put("pAcType", accType);
                    fillParams.put("pTranType", reportType.equalsIgnoreCase("0") ? "Cash" : reportType.equalsIgnoreCase("1") ? "Clearing" : reportType.equalsIgnoreCase("2") ? "Transfer" : reportType.equalsIgnoreCase("00") ? "Total Cash" : reportType.equalsIgnoreCase("01") ? "Total Clearing" : "Total Transfer");
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);

                    if (reportType.length() == 1) {
                        new ReportBean().openPdf("Deposit And Withdrawl Details Report_" + ymd.format(dmy.parse(getTodayDate())), "Deposit_and_Withdrawl_Details_Report", new JRBeanCollectionDataSource(resultList), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);
                    } else {
                        new ReportBean().openPdf("Deposit And Withdrawl Details Report_" + ymd.format(dmy.parse(getTodayDate())), "Deposit_and_Withdrawl_Details_Report_Total", new JRBeanCollectionDataSource(resultList2), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);
                    }

                    return "report";
                } else {
                    errorMsg = "Data Does Not Exist";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean validate() {
        if (frmDt == null) {
            errorMsg = "Please enter from date";
            return false;
        }
        if (toDt == null) {
            errorMsg = "Please enter to date";
            return false;
        }
        if (amt == 0) {
            errorMsg = "Please enter amount";
            return false;
        }
        if (frmDt.after(toDt)) {
            errorMsg = "Please from date should be less then to date";
            return false;
        }
        return true;
    }

    public void refresh() {
        frmDt = date;
        toDt = date;
        accType = "";
        reportType = "";
        errorMsg = "";
        frmDt = date;
        toDt = date;
        this.setAmt(0d);
    }
}
