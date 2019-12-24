/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.SmsPostingReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
 * @author Admin
 */
public class SmsPostingReport extends BaseBean {

    private String msg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    private TdReceiptManagementFacadeRemote beanFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<SmsPostingReportPojo> reportList = new ArrayList<SmsPostingReportPojo>();

    public SmsPostingReport() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getCAAcType();
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void getCAAcType() {
        List acType = new ArrayList();
        try {
            acType = common.getCASBAcTypeList();
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("ALL", "ALL"));
            if (!acType.isEmpty()) {
                for (int i = 0; i < acType.size(); i++) {
                    Vector acctVector = (Vector) acType.get(i);
                    acctTypeList.add(new SelectItem(acctVector.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public void viewReport() {
        String report = "Sms Posting Report";
        String branchName = "", address = "";
        try {

            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pbankName", branchName);
            fillParams.put("pbankAddress", address);
            fillParams.put("pReportName", report);
            fillParams.put("pReportDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", this.getUserName());

            reportList = remoteFacade.getSmsPostingDetail(branchCode, acctType, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }

            new ReportBean().jasperReport("Sms_Posting_Report", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Sms Posting Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        String report = "Sms Posting Report";
        String branchName = "", address = "";
        try {

            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pbankName", branchName);
            fillParams.put("pbankAddress", address);
            fillParams.put("pReportName", report);
            fillParams.put("pReportDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", this.getUserName());

            reportList = remoteFacade.getSmsPostingDetail(branchCode, acctType, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }

            new ReportBean().openPdf("Sms Posting Report_" + ymd.format(dmy.parse(frmDt)) + " to " + ymd.format(dmy.parse(toDt)), "Sms_Posting_Report", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void refresh() {
        setMsg("");

    }

    public String exitAction() {
        return "case1";
    }
}
