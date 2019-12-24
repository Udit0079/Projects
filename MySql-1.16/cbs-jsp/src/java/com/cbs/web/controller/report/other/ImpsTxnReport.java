/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.IMPSTxnParameterPojo;
import com.cbs.dto.report.TransactionDetailsPojo;
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
 * @author root
 */
public class ImpsTxnReport extends BaseBean {

    private String errorMsg;
    private String frmDt;
    private String toDt;
    private String process;
    private String branch;
    private List<SelectItem> branchList;
    private List<SelectItem> processList;
    private String mode;
    private List<SelectItem> modeList;
    private String impsType;
    private String panelDisplay = "";
    private List<SelectItem> impsTypeList;
    private OtherReportFacadeRemote remote;
    private CommonReportMethodsRemote common;
    private List<IMPSTxnParameterPojo> details;
    List<TransactionDetailsPojo> reportList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public ImpsTxnReport() {
        try {
            remote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");



            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            processList = new ArrayList<SelectItem>();
            processList.add(new SelectItem("0", "--Select--"));
            processList.add(new SelectItem("I", "IMPS Inward Txn"));
            processList.add(new SelectItem("O", "IMPS Outward Txn"));
            processList.add(new SelectItem("R", "IMPS Reversal Txn"));

            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("S", "Success"));
            modeList.add(new SelectItem("F", "Failure"));

            impsTypeList = new ArrayList<SelectItem>();
            impsTypeList.add(new SelectItem("B", "Branch imps"));
            impsTypeList.add(new SelectItem("M", "Mobile imps"));

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void impsOption() {
        if (impsType.equalsIgnoreCase("B")) {
            this.panelDisplay = "";
        } else {
            this.panelDisplay = "none";
        }
    }

    public void viewReport() {

        if (validate()) {
            try {
                String report = "IMPS TXN REPORT";
                Map fillParams = new HashMap();
                if (impsType.equalsIgnoreCase("M")) {
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDate", frmDt + " to " + toDt);
                    fillParams.put("pPrintDate", getTodayDate());
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    details = remote.getImpsTxnData(ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), branch);
                    new ReportBean().jasperReport("mobileImpsTxn", "text/html",
                            new JRBeanCollectionDataSource(details), fillParams, "IMPS TXN REPORT");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);

                } else {

                    if (!this.process.equalsIgnoreCase("O")) {
                        String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                        fillParams.put("pReportName", report);
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportDate", frmDt + " to " + toDt);
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        if (mode.equalsIgnoreCase("S")) {
                            fillParams.put("pStatus", "Success");
                        } else {
                            fillParams.put("pStatus", "Failure");
                        }
                        details = remote.getDetailImpsTxnParameter(this.process, this.mode, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getUserName(), this.branch);
                        new ReportBean().jasperReport("impsTxnReport", "text/html",
                                new JRBeanCollectionDataSource(details), fillParams, "IMPS TXN REPORT");
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);
                    } else {
                        String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                        fillParams.put("reportName", report);
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportDate", frmDt + " to " + toDt);
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        fillParams.put("pHeader", "Payment Type");
                        String status = "";
                        if (this.mode.equalsIgnoreCase("S")) {
                            status = "S";
                        } else {
                            status = "U";
                        }

                        reportList = remote.getDetailImpsOutward(this.process, ymd.format(dmy.parse(frmDt)),
                                ymd.format(dmy.parse(toDt)), getUserName(), this.branch, status);
                        new ReportBean().jasperReport("TransactionReport", "text/html",
                                new JRBeanCollectionDataSource(reportList), fillParams, "IMPS TXN REPORT");
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);
                    }
                }
            } catch (Exception ex) {
                this.setErrorMsg(ex.getLocalizedMessage());
            }
        }

    }

    public void pdfDownLoad() {
        if (validate()) {
            try {
                String report = "IMPS TXN REPORT";
                Map fillParams = new HashMap();
                if (impsType.equalsIgnoreCase("M")) {
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDate", frmDt + " to " + toDt);
                    fillParams.put("pPrintDate", getTodayDate());
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    details = remote.getImpsTxnData(ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), branch);
                    new ReportBean().openPdf("IMPS TXN REPORT_" + ymd.format(dmy.parse(getTodayDate())), "mobileImpsTxn", new JRBeanCollectionDataSource(details), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    if (!this.process.equalsIgnoreCase("O")) {
                        String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                        fillParams.put("pReportName", report);
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportDate", frmDt + " to " + toDt);
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        if (mode.equalsIgnoreCase("S")) {
                            fillParams.put("pStatus", "Success");
                        } else {
                            fillParams.put("pStatus", "Failure");
                        }
                        details = remote.getDetailImpsTxnParameter(this.process, this.mode, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getUserName(), getOrgnBrCode());

                        new ReportBean().openPdf("IMPS TXN REPORT_" + ymd.format(dmy.parse(getTodayDate())), "TransactionReport", new JRBeanCollectionDataSource(reportList), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);;
                    } else {
                        String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                        fillParams.put("reportName", report);
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportDate", frmDt + " to " + toDt);
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        fillParams.put("pHeader", "Payment Type");
                        String status = "";
                        if (this.mode.equalsIgnoreCase("S")) {
                            status = "S";
                        } else {
                            status = "U";
                        }
                        reportList = remote.getDetailImpsOutward(this.process, ymd.format(dmy.parse(frmDt)),
                                ymd.format(dmy.parse(toDt)), getUserName(), this.branch, status);
                        new ReportBean().openPdf("IMPS TXN REPORT_" + ymd.format(dmy.parse(getTodayDate())), "impsTxnReport", new JRBeanCollectionDataSource(details), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);;
                    }
                }
            } catch (Exception ex) {
                this.setErrorMsg(ex.getLocalizedMessage());
            }
        }

    }

    public boolean validate() {
        try {
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check from date";
                return false;
            }
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check to date";
                return false;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                errorMsg = "From date should be less then to date";
                return false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setProcess("0");
        this.setMode("S");
        frmDt = dmy.format(new Date());
        toDt = dmy.format(new Date());
    }

    public String exitAction() {
        return "case1";
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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public List<SelectItem> getProcessList() {
        return processList;
    }

    public void setProcessList(List<SelectItem> processList) {
        this.processList = processList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public OtherReportFacadeRemote getRemote() {
        return remote;
    }

    public void setRemote(OtherReportFacadeRemote remote) {
        this.remote = remote;
    }

    public CommonReportMethodsRemote getCommon() {
        return common;
    }

    public void setCommon(CommonReportMethodsRemote common) {
        this.common = common;
    }

    public List<IMPSTxnParameterPojo> getDetails() {
        return details;
    }

    public void setDetails(List<IMPSTxnParameterPojo> details) {
        this.details = details;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getImpsType() {
        return impsType;
    }

    public void setImpsType(String impsType) {
        this.impsType = impsType;
    }

    public List<SelectItem> getImpsTypeList() {
        return impsTypeList;
    }

    public void setImpsTypeList(List<SelectItem> impsTypeList) {
        this.impsTypeList = impsTypeList;
    }

    public String getPanelDisplay() {
        return panelDisplay;
    }

    public void setPanelDisplay(String panelDisplay) {
        this.panelDisplay = panelDisplay;
    }
}
