/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.DepositWithdrawlPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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

/**
 *
 * @author admin
 */
public class DepostWithdrawl extends BaseBean {

    private String errorMsg;
    private String reportType;
    private String accType;
    private String frmDt;
    private String toDt;
    private double amt;
    private OtherReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private List<SelectItem> acctTypeList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /**
     * Creates a new instance of DepostWithdrawl
     */
    public DepostWithdrawl() {
        try {
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
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
            acctTypeList.add(new SelectItem("ALL", "ALL"));
        } catch (Exception e) {
        }
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

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String viewReport() {
        if (frmDt == null || frmDt.equalsIgnoreCase("")) {
            errorMsg = "Please enter from date";
            return null;
        }
        if (!Validator.validateDate(frmDt)) {
            errorMsg = "Please select proper From Date";
            return null;
        }
        if (toDt == null || toDt.equalsIgnoreCase("")) {
            errorMsg = "Please enter to date";
            return null;
        }
        if (!Validator.validateDate(toDt)) {
            errorMsg = "Please select proper To Date";
            return null;
        }
        if (amt == 0) {
            errorMsg = "Please enter amount";
            return null;
        }

        String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
        String toDate = toDt.substring(6) + toDt.substring(3, 5) + toDt.substring(0, 2);

        try {
            if (ymd.parse(fromDt).after(ymd.parse(toDate))) {
                errorMsg = "Please from date should be less then to date";
                return null;
            }
            List<DepositWithdrawlPojo> resultList = local.depositWithdrawlReport(Integer.parseInt(reportType), accType, fromDt, toDate, getOrgnBrCode(), amt);
            if (!resultList.isEmpty()) {
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Deposit Withdrawl Report");
                fillParams.put("pReport Date", frmDt + " to " + toDt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAmount", amt);
                fillParams.put("pType", accType);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pReport", reportType.equalsIgnoreCase("0") ? "Cash" : reportType.equalsIgnoreCase("1") ? "Clearing" : "Transfer");
                new ReportBean().jasperReport("CBS_REP_DEPOSIT_WITHDRAWL", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Deposit Withdrawl Report");
            } else {
                errorMsg = "Data does not found";
                return null;
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
        return "report";
    }

    public void viewPdfReport() {

        if (frmDt == null || frmDt.equalsIgnoreCase("")) {
            errorMsg = "Please enter from date";
            return;
        }
        if (!Validator.validateDate(frmDt)) {
            errorMsg = "Please select proper From Date";
            return;
        }
        if (toDt == null || toDt.equalsIgnoreCase("")) {
            errorMsg = "Please enter to date";
            return;
        }
        if (!Validator.validateDate(toDt)) {
            errorMsg = "Please select proper To Date";
            return;
        }
        if (amt == 0) {
            errorMsg = "Please enter amount";
            return;
        }

        String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
        String toDate = toDt.substring(6) + toDt.substring(3, 5) + toDt.substring(0, 2);

        try {
            if (ymd.parse(fromDt).after(ymd.parse(toDate))) {
                errorMsg = "Please from date should be less then to date";
                return;
            }
            List<DepositWithdrawlPojo> resultList = local.depositWithdrawlReport(Integer.parseInt(reportType), accType, fromDt, toDate, getOrgnBrCode(), amt);
            if (!resultList.isEmpty()) {
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                String report = "Deposit Withdrawl Report";

                fillParams.put("pReportName", report);
                fillParams.put("pReport Date", frmDt + " to " + toDt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAmount", amt);
                fillParams.put("pType", accType);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pReport", reportType.equalsIgnoreCase("0") ? "Cash" : reportType.equalsIgnoreCase("1") ? "Clearing" : "Transfer");

                new ReportBean().openPdf("Deposit Withdrawl Report_"+ymd.format(dmy.parse(getTodayDate())), "CBS_REP_DEPOSIT_WITHDRAWL", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                errorMsg = "Data does not found";
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void refreshPage() {
        this.setErrorMsg("");
        this.setAmt(0.0d);
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exitPage() {
        refreshPage();
        return "case1";
    }
}
