/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.loanInsurancePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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

/**
 *
 * @author Nishant Srivastava
 */
public class loanInsuranceReport extends BaseBean {

    private String calFromDate;
    private String caltoDate;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String message;
    private String display = "";
    private String insurancePolicy;
    private List<SelectItem> insurancePolicyList;
    private String orderBy;
    private List<SelectItem> orderByList;
    List<loanInsurancePojo> repDataList = new ArrayList<loanInsurancePojo>();
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote loanFacade;
    private String day = "0";
    private String status = "none";
    private String focusId = "";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public OtherReportFacadeRemote getLoanFacade() {
        return loanFacade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setLoanFacade(OtherReportFacadeRemote loanFacade) {
        this.loanFacade = loanFacade;
    }

    public CommonReportMethodsRemote getCommon() {
        return common;
    }

    public void setCommon(CommonReportMethodsRemote common) {
        this.common = common;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public SimpleDateFormat getYmdFormat() {
        return ymdFormat;
    }

    public void setYmdFormat(SimpleDateFormat ymdFormat) {
        this.ymdFormat = ymdFormat;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<loanInsurancePojo> getRepDataList() {
        return repDataList;
    }

    public void setRepDataList(List<loanInsurancePojo> repDataList) {
        this.repDataList = repDataList;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(String caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getInsurancePolicy() {
        return insurancePolicy;
    }

    public void setInsurancePolicy(String insurancePolicy) {
        this.insurancePolicy = insurancePolicy;
    }

    public List<SelectItem> getInsurancePolicyList() {
        return insurancePolicyList;
    }

    public void setInsurancePolicyList(List<SelectItem> insurancePolicyList) {
        this.insurancePolicyList = insurancePolicyList;
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
    SimpleDateFormat ymdFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public loanInsuranceReport() {
        try {
            this.setCaltoDate(getTodayDate());

            loanFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List acct = new ArrayList();
            acct = loanFacade.getAcctTypeList1();
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("ALL"));
            if (!acct.isEmpty()) {
                for (int i = 0; i < acct.size(); i++) {
                    Vector agVector = (Vector) acct.get(i);
                    acctTypeList.add(new SelectItem(agVector.get(0).toString(), agVector.get(1).toString()));
                }
            }
            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("", "--Select--"));
            orderByList.add(new SelectItem("0", "Ac/No"));
            orderByList.add(new SelectItem("1", "Due Dt"));

            insurancePolicyList = new ArrayList<SelectItem>();
            insurancePolicyList.add(new SelectItem("--Select--"));
            insurancePolicyList.add(new SelectItem("ALL"));
            insurancePolicyList.add(new SelectItem("ACTIVE"));
            insurancePolicyList.add(new SelectItem("EXPIRED"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void statusAction() {
        if (this.insurancePolicy.equalsIgnoreCase("EXPIRED")) {
            this.setStatus("");
            setFocusId("toDt");
        } else {
            this.setStatus("none");
            setFocusId("toDt");
        }
    }

    public void PrintViwe() {
        setMessage("");
        if (this.caltoDate == null) {
            setMessage("Please Fill To Date.");
            return;
        }
        if (this.acctType.equalsIgnoreCase("--Select--")) {
            setMessage("Please Fill A/c Type!");
            return;
        }

        Map fillParams = new HashMap();
        try {
            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            String bankName = "";
            String bankAddress = "";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            String report = "Details Of Insurance Policies";
            fillParams.put("pReportToDate", this.caltoDate);
            fillParams.put("SysDate", ymdFormat.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("code", this.orderBy);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", bankAddress);

            String dd1 = this.caltoDate.substring(0, 2);
            String mm1 = this.caltoDate.substring(3, 5);
            String yy1 = this.caltoDate.substring(6, 10);
            String toDt = yy1 + mm1 + dd1;

            repDataList = loanFacade.getInsuranceReport(this.insurancePolicy, this.acctType, toDt, Integer.parseInt(day), getOrgnBrCode());
            if (repDataList.isEmpty()) {
                message = "No value found in database";
                return;
            }
            new ReportBean().jasperReport("InsurancePoliciesReport", "text/html",
                    new JRBeanCollectionDataSource(repDataList), fillParams, "Insurance Policies Report");
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        try {
            setMessage("");
            if (this.caltoDate == null) {
                setMessage("Please Fill To Date.");
                return;
            }
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                setMessage("Please Fill A/c Type!");
                return;
            }

            Map fillParams = new HashMap();
            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            String bankName = "";
            String bankAddress = "";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            String report = "Details Of Insurance Policies";
            fillParams.put("pReportToDate", this.caltoDate);
            fillParams.put("SysDate", ymdFormat.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("code", this.orderBy);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", bankAddress);

            String dd1 = this.caltoDate.substring(0, 2);
            String mm1 = this.caltoDate.substring(3, 5);
            String yy1 = this.caltoDate.substring(6, 10);
            String toDt = yy1 + mm1 + dd1;

            repDataList = loanFacade.getInsuranceReport(this.insurancePolicy, this.acctType, toDt, Integer.parseInt(day), getOrgnBrCode());
            if (repDataList.isEmpty()) {
                message = "No value found in database";
                return;
            }

            new ReportBean().openPdf("InsurancePoliciesReport_" + ymd.format(dmy.parse(this.getTodayDate())), "InsurancePoliciesReport", new JRBeanCollectionDataSource(repDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String closeAction() {
        return "case1";
    }

    public void refresh() {
        setMessage("");
        this.setStatus("none");
        setAcctType("ALL");
        this.setCaltoDate(getTodayDate());
        setInsurancePolicy("--Select--");
    }
}
