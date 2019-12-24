/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.CashTranRepPojo;
import com.cbs.dto.report.SortedByTy;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author admin
 */
public class CashTran extends BaseBean {

    /**
     * Creates a new instance of CashTran
     */
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String errorMsg;
    private String reportType;
    private String ty;
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
    public CashTran() {
        try {
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            local = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acctTypeList = new ArrayList<SelectItem>();
            initData();
        } catch (ApplicationException ex) {
            this.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    private void initData() {
        acctTypeList.clear();
        try {
            List result = local.getAcctTypeListCashTrf();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    acctTypeList.add(new SelectItem(vtr.get(0), vtr.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        }
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
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

    public String getTy() {
        return ty;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }

    public String viewReport() {
        String report = "Cash Transaction Report";
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

            List<CashTranRepPojo> resultList = local.cashTransferReport(Integer.parseInt(reportType), accType, amt, fromDt, toDate, branchCode, ty);
            if (!resultList.isEmpty()) {
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByTy());
                Collections.sort(resultList, chObj);
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", frmDt + " to " + toDt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAmount", amt);
                fillParams.put("pType", accType);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pReport", reportType.equals("0") ? "Cash" : reportType.equals("1") ? "Clearing" : "Transfer");
                new ReportBean().jasperReport("Cash_Transaction_Report", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
            } else {
                errorMsg = "Data does not found";
                return null;
            }
        } catch (Exception e) {
            errorMsg = new ApplicationException(e).getMessage();
        }
        return "report";
    }

    public String pdfDownLoad() {
        String report = "Cash Transaction Report";
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

            List<CashTranRepPojo> resultList = local.cashTransferReport(Integer.parseInt(reportType), accType, amt, fromDt, toDate, branchCode, ty);
            if (!resultList.isEmpty()) {
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByTy());
                Collections.sort(resultList, chObj);
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", frmDt + " to " + toDt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAmount", amt);
                fillParams.put("pType", accType);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pReport", reportType.equals("0") ? "Cash" : reportType.equals("1") ? "Clearing" : "Transfer");
                new ReportBean().openPdf("Cash Transaction Report_" + ymd.format(dmy.parse(getTodayDate())), "Cash_Transaction_Report", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                errorMsg = "Data does not found";
                return null;
            }
        } catch (Exception e) {
            errorMsg = new ApplicationException(e).getMessage();
        }
        return "report";
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
