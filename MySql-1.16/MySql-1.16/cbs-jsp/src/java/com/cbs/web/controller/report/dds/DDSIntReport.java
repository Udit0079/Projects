/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.dds;

import com.cbs.dto.report.DDSIntReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
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
 * @author Athar Reza
 */
public class DDSIntReport extends BaseBean {

    private String message;
    private String acType;
    private List<SelectItem> acTypeList;
    private String frmDt;
    private String toDt;
    private Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<DDSIntReportPojo> reportList = new ArrayList<DDSIntReportPojo>();
    private CommonReportMethodsRemote common;
    private DDSReportFacadeRemote DDSReportRemote = null;
    private final String jndiHomeName = "DDSReportFacade";

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public DDSIntReport() {
        try {
            setFrmDt(dmy.format(dt));
            setToDt(dmy.format(dt));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            DDSReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);

            List acct = new ArrayList();
            acct = DDSReportRemote.getAcodeRdds();
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--Select--"));
            if (!acct.isEmpty()) {
                for (int i = 0; i < acct.size(); i++) {
                    Vector agVector = (Vector) acct.get(i);
                    acTypeList.add(new SelectItem(agVector.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {

        String branchName = "", address = "";
        String report = "DDS Interest Report";
        try {
            if (acType.equalsIgnoreCase("--Select--")) {
                setMessage("Please select Account Type");
                return;
            }
            if (frmDt.equalsIgnoreCase("") || frmDt == null) {
                setMessage("Please fill form date !");
                return;
            }
            if (toDt.equalsIgnoreCase("") || toDt == null) {
                setMessage("please fill to date !");
                return;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less then to date");
                return;
            }
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pReportDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());

            reportList = DDSReportRemote.getDDSIntData(acType, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode());
            if (reportList.isEmpty()) {
                message = "Data does not exist !";
                return;
            }
            new ReportBean().jasperReport("DdsInterestReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "DDS Interest Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        String branchName = "", address = "";
        String report = "DDS Interest Report";
        try {
            if (acType.equalsIgnoreCase("--Select--")) {
                setMessage("Please select Account Type");
                return;
            }
            if (frmDt.equalsIgnoreCase("") || frmDt == null) {
                setMessage("Please fill form date !");
                return;
            }
            if (toDt.equalsIgnoreCase("") || toDt == null) {
                setMessage("please fill to date !");
                return;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less then to date");
                return;
            }
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pReportDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());

            reportList = DDSReportRemote.getDDSIntData(acType, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode());
            if (reportList.isEmpty()) {
                message = "Data does not exist !";
                return;
            }
            new ReportBean().openPdf("DDS Interest Report_"+ ymd.format(dmy.parse(getTodayDate())), "DdsInterestReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        setFrmDt(dmy.format(dt));
        setToDt(dmy.format(dt));
    }

    public String btnExitAction() {
        return "case1";
    }
}
