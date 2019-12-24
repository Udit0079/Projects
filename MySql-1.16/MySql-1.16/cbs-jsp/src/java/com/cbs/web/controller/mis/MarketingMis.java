/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.mis;

import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import com.cbs.facade.mis.TaskManagementRemoteFacade;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.mis.MarketingMisCombine;
import com.cbs.pojo.mis.MarketingMisPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MarketingMis extends BaseBean {

    private String errorMsg;
    private String branch;
    private String frmdt;
    private String toDt;
    private String misType;
    private String entryType;
    private String reportType;
    private String followUptoDt;
    private String followUpfrmDt;
    private List<SelectItem> misTypeList;
    private List<SelectItem> entryTypeList;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> branchList;
    private List<MarketingMisPojo> reportList;
    private CommonReportMethodsRemote common;
    private String followdatedisplay = "none";
    private TaskManagementRemoteFacade taskManagement;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public MarketingMis() {
        this.setErrorMsg("");
        try {
            this.setFrmdt(getTodayDate());
            this.setToDt(getTodayDate());
            this.setFollowUpfrmDt(getTodayDate());
            this.setFollowUptoDt(getTodayDate());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            taskManagement = (TaskManagementRemoteFacade) ServiceLocator.getInstance().lookup("TaskManagementFacade");

            branchList = new ArrayList<>();
            branchList.add(new SelectItem("S", "--Select--"));
            List list = common.getAlphacodeBasedOnOrgnBranch(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setErrorMsg("Please define branches.");
                return;
            }
            if (common.getAlphacodeByBrncode(getOrgnBrCode()).equalsIgnoreCase("HO")) {
                branchList.add(new SelectItem("0A", "All"));
            }
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                branchList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString()
                        : vec.get(1).toString(), vec.get(0).toString()));
            }

            entryTypeList = new ArrayList<>();
            entryTypeList.add(new SelectItem("0", "--Select--"));
            entryTypeList.add(new SelectItem("Y", "Yearly"));
            entryTypeList.add(new SelectItem("M", "Monthly"));
            entryTypeList.add(new SelectItem("D", "Daily"));

            reportTypeList = new ArrayList<>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("N", "New Entry"));
            reportTypeList.add(new SelectItem("F", "Follow Up Entry"));

            misTypeList = new ArrayList<>();
            misTypeList.add(new SelectItem("S", "---Select---"));
            misTypeList.add(new SelectItem("L", "Lead Mis"));
            misTypeList.add(new SelectItem("C", "Consolidate Mis"));
            misTypeList.add(new SelectItem("B", "Branch Wise"));
            misTypeList.add(new SelectItem("U", "User Wise"));
            misTypeList.add(new SelectItem("P", "User Performance "));
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void entryTypeMode() {
        this.setErrorMsg("");
        if (branch == null || branch.equalsIgnoreCase("")) {
            this.setErrorMsg("Please select the branch ");
            return;
        }
        if (entryType == null || entryType.equalsIgnoreCase("0")) {
            this.setErrorMsg("Please select the entry type");
            return;
        }
//        if (entryType.equalsIgnoreCase("D")) {
//            misTypeList = new ArrayList<>();
//            misTypeList.add(new SelectItem("S", "---Select---"));
//            misTypeList.add(new SelectItem("C", "Consolidate Mis"));
//
//        } else {
//            misTypeList = new ArrayList<>();
//            misTypeList.add(new SelectItem("S", "---Select---"));
//            misTypeList.add(new SelectItem("L", "Lead Mis"));
//            misTypeList.add(new SelectItem("C", "Consolidate Mis"));
//        }
    }

    public void dailyDateCheck() {
        this.setErrorMsg("");
        if (entryType.equalsIgnoreCase("D")) {
            if (!frmdt.equalsIgnoreCase(toDt)) {
                this.setErrorMsg("From date and to date should be same in case of daily");
                return;
            }
        }
    }

    public void setDateCheck() {
        this.setErrorMsg("");
        if (entryType.equalsIgnoreCase("D")) {
            setToDt(frmdt);
        }
    }

    public void misTypeMode() {
        this.setErrorMsg("");
        try {
            this.setFollowUpfrmDt(getTodayDate());
            this.setFollowUptoDt(getTodayDate());

            this.followdatedisplay = "none";
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the Mis type ");
                return;
            }
            if (reportType.equalsIgnoreCase("N")) {
                this.followdatedisplay = "none";
            } else if (reportType.equalsIgnoreCase("F")) {
                this.followdatedisplay = "";
            }
            this.setFollowUpfrmDt(frmdt);
            this.setFollowUptoDt(toDt);
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void printreport() {
        this.setErrorMsg("");
        try {
            if (branch == null || branch.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the branch");
                return;
            }
            if (entryType == null || entryType.endsWith("0")) {
                this.setErrorMsg("Please select the entry type");
                return;
            }

            if (reportType == null || reportType.endsWith("0")) {
                this.setErrorMsg("Please select the report type");
                return;
            }

            if (misType == null || misType.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the mis type");
                return;
            }
            if (this.frmdt == null || this.frmdt.equalsIgnoreCase("")) {
                this.setErrorMsg("Please enter the from date");
                return;
            }
            if (this.toDt == null || this.toDt.equalsIgnoreCase("")) {
                this.setErrorMsg("Please enter the to date");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(frmdt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (dmy.parse(frmdt).compareTo(dmy.parse(toDt)) > 0) {
                setErrorMsg("From date can not be greater than to date!");
                return;
            }
            String report = "Marketing Mis Report";
            String pdate = getTodayDate();
            List brList = null;
            String brnName = "", brnAddress = "";
            brList = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brList.size() > 0) {
                brnName = (String) brList.get(0);
                brnAddress = (String) brList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmdt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            fillParams.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.FALSE);
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            if (misType.equalsIgnoreCase("P")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getStaffPerformanceMisReport(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().jasperReport("Lead_Satff_Performance", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, " Satff Performance ");
            } else if (misType.equalsIgnoreCase("U")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().jasperReport("misDetailsUserConsolidate", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, " Marketing Mis Report ");
            } else if (misType.equalsIgnoreCase("B")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().jasperReport("misDetailsBranchConsolidate", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, " Marketing Mis Report ");
            } else if (misType.equalsIgnoreCase("C")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().jasperReport(entryType.equalsIgnoreCase("Y") ? "misDetails" : "misDetailsDaily", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, " Marketing Mis Report ");
            } else {
                List<MarketingMisPojo> entryDetails = taskManagement.getMktDataAsPerRequirement(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), "E", ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType);
                if (entryDetails.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                List<MarketingMisPojo> updateDetails = taskManagement.getMktDataAsPerRequirement(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), "U", ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType);
                if (updateDetails.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }

                List<MarketingMisCombine> jrxmlList = new ArrayList<MarketingMisCombine>();     //Actual List To Print
                MarketingMisCombine jrxmlPojo = new MarketingMisCombine();
                jrxmlPojo.setEntryDetails(new JRBeanCollectionDataSource(entryDetails));
                jrxmlPojo.setUpdateDetails(new JRBeanCollectionDataSource(updateDetails));
                jrxmlList.add(jrxmlPojo);

                new ReportBean().jasperReport("Lead_Cover", "text/html", new JRBeanCollectionDataSource(jrxmlList), fillParams, " Marketing Mis Report ");
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }

    }

    public void btnPdfAction() {
        this.setErrorMsg("");
        try {
            if (branch == null || branch.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the branch");
                return;
            }
            if (entryType == null || entryType.endsWith("0")) {
                this.setErrorMsg("Please select the entry type");
                return;
            }
            if (reportType == null || reportType.endsWith("0")) {
                this.setErrorMsg("Please select the report type");
                return;
            }
            if (misType == null || misType.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the mis type");
                return;
            }
            if (this.frmdt == null || this.frmdt.equalsIgnoreCase("")) {
                this.setErrorMsg("Please enter the from date");
                return;
            }
            if (this.toDt == null || this.toDt.equalsIgnoreCase("")) {
                this.setErrorMsg("Please enter the to date");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(frmdt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (dmy.parse(frmdt).compareTo(dmy.parse(toDt)) > 0) {
                setErrorMsg("From date can not be greater than to date!");
                return;
            }

            String report = "Marketing Mis Report";
            String pdate = getTodayDate();
            List brList = null;
            String brnName = "", brnAddress = "";
            brList = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brList.size() > 0) {
                brnName = (String) brList.get(0);
                brnAddress = (String) brList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmdt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            fillParams.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.FALSE);
            if (misType.equalsIgnoreCase("P")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getStaffPerformanceMisReport(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().openPdf("Satff_Performance" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)),
                        "Lead_Satff_Performance", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (misType.equalsIgnoreCase("U")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().openPdf("misDetails" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)),
                        "misDetailsUserConsolidate", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (misType.equalsIgnoreCase("B")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().openPdf("misDetails" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)),
                        "misDetailsBranchConsolidate", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (misType.equalsIgnoreCase("C")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().openPdf("misDetails" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)),
                        entryType.equalsIgnoreCase("Y") ? "misDetails" : "misDetailsDaily", new JRBeanCollectionDataSource(reportList), fillParams);
            } else {
                List<MarketingMisPojo> entryDetails = taskManagement.getMktDataAsPerRequirement(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), "E", ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType);
                if (entryDetails.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                List<MarketingMisPojo> updateDetails = taskManagement.getMktDataAsPerRequirement(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), "U", ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType);
                if (updateDetails.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }

                List<MarketingMisCombine> jrxmlList = new ArrayList<MarketingMisCombine>();     //Actual List To Print
                MarketingMisCombine jrxmlPojo = new MarketingMisCombine();
                jrxmlPojo.setEntryDetails(new JRBeanCollectionDataSource(entryDetails));
                jrxmlPojo.setUpdateDetails(new JRBeanCollectionDataSource(updateDetails));
                jrxmlList.add(jrxmlPojo);
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().openPdf("misDetails" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)),
                        "Lead_Cover", new JRBeanCollectionDataSource(jrxmlList), fillParams);

            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void downloadExcel() {
        this.setErrorMsg("");
        try {
            if (branch == null || branch.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the branch");
            }
            if (entryType == null || entryType.endsWith("0")) {
                this.setErrorMsg("Please select the entry type");
                return;
            }
            if (reportType == null || reportType.endsWith("0")) {
                this.setErrorMsg("Please select the report type");
                return;
            }
            if (misType == null || misType.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the mis type");
                return;
            }
            if (this.frmdt == null || this.frmdt.equalsIgnoreCase("")) {
                this.setErrorMsg("Please enter the from date");
                return;
            }
            if (this.toDt == null || this.toDt.equalsIgnoreCase("")) {
                this.setErrorMsg("Please enter the to date");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(frmdt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (dmy.parse(frmdt).compareTo(dmy.parse(toDt)) > 0) {
                setErrorMsg("From date can not be greater than to date!");
                return;
            }
            String report = "Marketing Mis Report";
            String pdate = getTodayDate();
            List brList = null;
            String brnName = "", brnAddress = "";
            brList = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brList.size() > 0) {
                brnName = (String) brList.get(0);
                brnAddress = (String) brList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmdt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            fillParams.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.FALSE);
            if (misType.equalsIgnoreCase("P")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getStaffPerformanceMisReport(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().dynamicExcelJasper("Lead_Satff_Performance",
                        "excel", new JRBeanCollectionDataSource(reportList), fillParams,
                        "Satff_Performance" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)));
            } else if (misType.equalsIgnoreCase("U")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().dynamicExcelJasper("misDetailsUserConsolidate",
                        "excel", new JRBeanCollectionDataSource(reportList), fillParams,
                        "Marketing Mis Report" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)));
            } else if (misType.equalsIgnoreCase("B")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().dynamicExcelJasper("misDetailsBranchConsolidate",
                        "excel", new JRBeanCollectionDataSource(reportList), fillParams,
                        "Marketing Mis Report" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)));
            } else if (misType.equalsIgnoreCase("C")) {
                reportList = new ArrayList<>();
                reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().dynamicExcelJasper(entryType.equalsIgnoreCase("Y") ? "misDetails" : "misDetailsDaily",
                        "excel", new JRBeanCollectionDataSource(reportList), fillParams,
                        "Marketing Mis Report" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)));
            } else {

                List<MarketingMisPojo> entryDetails = taskManagement.getMktDataAsPerRequirement(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), "E", ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType);
                if (entryDetails.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                List<MarketingMisPojo> updateDetails = taskManagement.getMktDataAsPerRequirement(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), "U", ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType);
                if (updateDetails.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }

                List<MarketingMisCombine> jrxmlList = new ArrayList<MarketingMisCombine>();     //Actual List To Print
                MarketingMisCombine jrxmlPojo = new MarketingMisCombine();
                jrxmlPojo.setEntryDetails(new JRBeanCollectionDataSource(entryDetails));
                jrxmlPojo.setUpdateDetails(new JRBeanCollectionDataSource(updateDetails));
                jrxmlList.add(jrxmlPojo);
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().dynamicExcelJasper("Lead_Cover",
                        "excel", new JRBeanCollectionDataSource(jrxmlList), fillParams,
                        " Marketing Mis Report" + ymd.format(dmy.parse(this.frmdt)) + " to " + ymd.format(dmy.parse(this.toDt)));

            }
            reportList = new ArrayList<>();
            reportList = taskManagement.getMarketingMisReportData(this.branch, ymd.format(dmy.parse(this.frmdt)), ymd.format(dmy.parse(this.toDt)), entryType, ymd.format(dmy.parse(this.followUpfrmDt)), ymd.format(dmy.parse(this.followUptoDt)), reportType, misType);
            if (reportList.isEmpty()) {
                this.setErrorMsg("Data does not exists");
                return;
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void refresh() {
        try {
            this.setErrorMsg("");
            this.setEntryType("");
            this.setReportType("");
            this.setBranch("S");
            this.followdatedisplay = "none";
            this.setFrmdt(getTodayDate());
            this.setToDt(getTodayDate());
            this.setFollowUpfrmDt(getTodayDate());
            this.setFollowUptoDt(getTodayDate());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public String exit() {
        return "case1";
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFrmdt() {
        return frmdt;
    }

    public void setFrmdt(String frmdt) {
        this.frmdt = frmdt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<MarketingMisPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<MarketingMisPojo> reportList) {
        this.reportList = reportList;
    }

    public String getMisType() {
        return misType;
    }

    public void setMisType(String misType) {
        this.misType = misType;
    }

    public List<SelectItem> getMisTypeList() {
        return misTypeList;
    }

    public void setMisTypeList(List<SelectItem> misTypeList) {
        this.misTypeList = misTypeList;
    }

    public String getFollowUptoDt() {
        return followUptoDt;
    }

    public void setFollowUptoDt(String followUptoDt) {
        this.followUptoDt = followUptoDt;
    }

    public String getFollowUpfrmDt() {
        return followUpfrmDt;
    }

    public void setFollowUpfrmDt(String followUpfrmDt) {
        this.followUpfrmDt = followUpfrmDt;
    }

    public String getFollowdatedisplay() {
        return followdatedisplay;
    }

    public void setFollowdatedisplay(String followdatedisplay) {
        this.followdatedisplay = followdatedisplay;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public List<SelectItem> getEntryTypeList() {
        return entryTypeList;
    }

    public void setEntryTypeList(List<SelectItem> entryTypeList) {
        this.entryTypeList = entryTypeList;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }
}
