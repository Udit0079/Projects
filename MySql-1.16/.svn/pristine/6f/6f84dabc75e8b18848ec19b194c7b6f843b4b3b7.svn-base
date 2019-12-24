/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.AccountTransactionPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class AccountTransactionDetails extends BaseBean {

    private String errorMsg;
    private String acNo;
    private String newAcNo;
    private String reportType;
    private List<SelectItem> acctTypeList;
    private String frmDt;
    private String toDt;
    private double frAmt;
    private double toAmt;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteAcNoTransDetail;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    List<AccountTransactionPojo> reportList = new ArrayList<AccountTransactionPojo>();

    public AccountTransactionDetails() {
        try {
            setFrmDt(getTodayDate());
            setToDt(getTodayDate());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteAcNoTransDetail = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public double getFrAmt() {
        return frAmt;
    }

    public void setFrAmt(double frAmt) {
        this.frAmt = frAmt;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public double getToAmt() {
        return toAmt;
    }

    public void setToAmt(double toAmt) {
        this.toAmt = toAmt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry.Got a null request from faces context");
        }
        return request;
    }

    public void onBlurAccountNumber() {
        try {
            acNo = ftsPostRemote.getNewAccountNumber(acNo);
            this.newAcNo = acNo;
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewReport() {
        String branchName = "";
        String address = "";

        if (acNo == null || acNo.equalsIgnoreCase("")) {
            errorMsg = "Please enter A/c Number";
            return;
        }
        if (acNo.length() != 12) {
            errorMsg = "Please Enter 12 Digit Account No";
            return;
        }
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

        if (frAmt == 0) {
            errorMsg = "Please enter From Amount";
            return;
        }
        if (toAmt == 0) {
            errorMsg = "Please enter To Amount";
            return;
        }

        try {

            String frDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));

            if (ymd.parse(frDate).after(ymd.parse(toDate))) {
                errorMsg = "Please from date should be less then to date";
                return;
            }
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            String report = "Account Transaction Detail Report";
            Map fillmap = new HashMap();
            fillmap.put("pBranchName", branchName);
            fillmap.put("pAddress", address);
            fillmap.put("pRepName", report);
            fillmap.put("pReportDate", frmDt + " To " + toDt);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pFromAmt", frAmt);
            fillmap.put("pToAmt", toAmt);
            fillmap.put("pReportType", reportType.equalsIgnoreCase("0") ? "CASH" : reportType.equalsIgnoreCase("1") ? "CLEARING" : reportType.equalsIgnoreCase("2") ? "TRANSFER" : "ALL");

            reportList = remoteAcNoTransDetail.getAccountTransactionDetails(acNo, reportType, frAmt, toAmt, frDate, toDate);
            new ReportBean().jasperReport("AccountTransactionDetailReport", "text/html", new JRBeanCollectionDataSource(reportList), fillmap, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refreshPage() {
        this.setErrorMsg("");
        this.setAcNo("");
        this.setNewAcNo("");
        this.setFrAmt(0.0d);
        this.setToAmt(0.0d);
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exitPage() {
        refreshPage();
        return "case1";
    }
}
