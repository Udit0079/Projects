/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ckycr;

import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.ckycr.CkycrViewMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.dto.ckycr.CKYCRFailurePojo;
import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.DateFormat;
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

public class CKYCRFailureReportController extends BaseBean {

    private String msg;
    private String msgStyle;
    private String branch;
    private String mode;
    private String reportType;
    private String fromDate;
    private String toDate;
    private List branchList;
    private List modeList;
    private List reportTypeList;
    private String reportName;
    private boolean btnPrintReportDisable;
    private boolean btnGetDisable;
    private String viewID = "/pages/master/sm/test.jsp";
    private List<CKYCRFailurePojo> customerFailureList;
    private List<CKYCRRequestPojo> customerAllList;
    private CkycrViewMgmtFacadeRemote ckycrRemote;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgStyle() {
        return msgStyle;
    }

    public void setMsgStyle(String msgStyle) {
        this.msgStyle = msgStyle;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List getBranchList() {
        return branchList;
    }

    public void setBranchList(List branchList) {
        this.branchList = branchList;
    }

    public List getModeList() {
        return modeList;
    }

    public void setModeList(List modeList) {
        this.modeList = modeList;
    }

    public List getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isBtnPrintReportDisable() {
        return btnPrintReportDisable;
    }

    public void setBtnPrintReportDisable(boolean btnPrintReportDisable) {
        this.btnPrintReportDisable = btnPrintReportDisable;
    }

    public boolean isBtnGetDisable() {
        return btnGetDisable;
    }

    public void setBtnGetDisable(boolean btnGetDisable) {
        this.btnGetDisable = btnGetDisable;
    }

    public List<CKYCRFailurePojo> getCustomerFailureList() {
        return customerFailureList;
    }

    public void setCustomerFailureList(List<CKYCRFailurePojo> customerFailureList) {
        this.customerFailureList = customerFailureList;
    }

    public List<CKYCRRequestPojo> getCustomerAllList() {
        return customerAllList;
    }

    public void setCustomerAllList(List<CKYCRRequestPojo> customerAllList) {
        this.customerAllList = customerAllList;
    }

    public CKYCRFailureReportController() throws ServiceLocatorException {
        btnGetDisable = true;
        btnPrintReportDisable = true;
        ckycrRemote = (CkycrViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrViewMgmtFacade");
        try {
            String brnCode = this.getOrgnBrCode();
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("SELECT", "--Select--"));
            if (brnCode.equalsIgnoreCase("90")) {
                List list = ckycrRemote.getBranchList();
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    branchList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
                }
            } else {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                branchList.add(new SelectItem(brnCode, common.getBranchNameByBrncode(brnCode)));
            }
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("SELECT", "--Select--"));
            modeList.add(new SelectItem("UPDATE", "UPDATE"));
            modeList.add(new SelectItem("UPLOAD", "UPLOAD"));
            modeList.add(new SelectItem("DOWNLOAD", "DOWNLOAD"));
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void onChangeMode() {
        reportTypeList = new ArrayList<SelectItem>();
        if (mode.equalsIgnoreCase("UPLOAD")) {
            reportTypeList.add(new SelectItem("SELECT", "--Select--"));
            reportTypeList.add(new SelectItem("SUCCESS", "SUCCESS"));
            reportTypeList.add(new SelectItem("FAILURE", "FAILURE"));
            reportTypeList.add(new SelectItem("ENTERED-RESEND", "REQUEST"));
            reportTypeList.add(new SelectItem("UPLOADED", "UPLOADED"));
        } else if (mode.equalsIgnoreCase("DOWNLOAD")) {
            reportTypeList.add(new SelectItem("SELECT", "--Select--"));
            reportTypeList.add(new SelectItem("ENTERED", "REQUEST"));
            reportTypeList.add(new SelectItem("DOWNLOADED", "SUCCESS"));

        } else if (mode.equalsIgnoreCase("UPDATE")) {
            reportTypeList.add(new SelectItem("SELECT", "--Select--"));
            reportTypeList.add(new SelectItem("SUCCESS", "SUCCESS"));
            reportTypeList.add(new SelectItem("FAILURE", "FAILURE"));
            reportTypeList.add(new SelectItem("UPDATE", "REQUEST"));
            reportTypeList.add(new SelectItem("UPLOADED", "UPLOADED"));
        }
    }

    public void validation() {
        if (this.reportType.equalsIgnoreCase("FAILURE")) {
            btnGetDisable = false;
            btnPrintReportDisable = true;
        } else {
            btnGetDisable = true;
            btnPrintReportDisable = false;
            this.customerFailureList = null;
        }
    }

    public void FailureCustomerDetail() {
        try {
            if (branch.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Branch !";
                return;
            }
            if (mode.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Mode !";
                return;
            }
            if (reportType.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Report Type !";
                return;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                this.msgStyle = "error";
                this.msg = "Please fill To Date";
                return;
            }
            if (fromDate == null || fromDate.equalsIgnoreCase("")) {
                this.msgStyle = "error";
                this.msg = "Please fill From Date";
                return;
            }
            if (!Validator.validateDate(fromDate)) {
                this.msgStyle = "error";
                this.msg = "Please select proper From Date";
                return;
            }
            if (!Validator.validateDate(toDate)) {
                this.msgStyle = "error";
                this.msg = "Please select proper To Date";
                return;
            }
            if (dmyFormat.parse(fromDate).compareTo(new Date()) > 0) {
                this.msgStyle = "error";
                this.setMsg("From Date can not be greater than Current Date.");
                return;
            }
            if (dmyFormat.parse(toDate).compareTo(new Date()) > 0) {
                this.msgStyle = "error";
                this.setMsg("To Date can not be greater than Current Date.");
                return;
            }
            if (dmyFormat.parse(fromDate).compareTo(dmyFormat.parse(toDate)) > 0) {
                this.msgStyle = "error";
                this.setMsg("From Date can not be greater than To Date.");
                return;
            }
            String fromDt = ymdFormat.format(dmyFormat.parse(fromDate));
            String toDt = ymdFormat.format(dmyFormat.parse(toDate));
            if (!mode.equalsIgnoreCase("DOWNLOAD")) {
                if (reportType.equalsIgnoreCase("FAILURE")) {
                    customerFailureList = ckycrRemote.getFailureCustomerDetail(this.branch, this.mode, fromDt, toDt);
                } else {
                    customerAllList = ckycrRemote.getInfoForPrintCKYCRReport(this.branch, this.mode, this.reportType, fromDt, toDt);
                    reportName = "CKYCR Report";
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                    Map fillParam = new HashMap();
                    fillParam.put("pReportName", "CKYCR REPORT");
                    fillParam.put("pPrintedBy", getUserName());
                    fillParam.put("pBankName", branchNameandAddress.get(0));
                    fillParam.put("pBankAdd", branchNameandAddress.get(1));
                    fillParam.put("pReportType", this.mode + " , " + this.reportType);
                    fillParam.put("pDateRange", this.fromDate + " To " + this.toDate);
                    new ReportBean().jasperReport("CKYCRReport", "text/html", new JRBeanCollectionDataSource(customerAllList), fillParam, reportName);
                    this.setViewID("/report/ReportPagePopUp.jsp");
                }
            } else {
                customerAllList = ckycrRemote.getDownloadCKYCRReport(branch, mode, reportType, fromDt, toDt);
                reportName = "CKYCR Report";
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParam = new HashMap();
                fillParam.put("pReportName", "CKYCR REPORT");
                fillParam.put("pPrintedBy", getUserName());
                fillParam.put("pBankName", branchNameandAddress.get(0));
                fillParam.put("pBankAdd", branchNameandAddress.get(1));
                fillParam.put("pReportType", this.mode + "," + this.reportType);
                fillParam.put("pDateRange", this.fromDate + " To " + this.toDate);
                new ReportBean().jasperReport("CKYCRReport", "text/html", new JRBeanCollectionDataSource(customerAllList), fillParam, reportName);
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
            this.setMsg("");
        } catch (Exception ex) {
            this.customerFailureList = null;
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void viewPdfReport() {
        try {
            if (branch.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Branch !";
                return;
            }
            if (mode.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Mode !";
                return;
            }
            if (reportType.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Report Type !";
                return;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                this.msgStyle = "error";
                this.msg = "Please fill To Date";
                return;
            }
            if (fromDate == null || fromDate.equalsIgnoreCase("")) {
                this.msgStyle = "error";
                this.msg = "Please fill From Date";
                return;
            }
            if (!Validator.validateDate(fromDate)) {
                this.msgStyle = "error";
                this.msg = "Please select proper From Date";
                return;
            }
            if (!Validator.validateDate(toDate)) {
                this.msgStyle = "error";
                this.msg = "Please select proper To Date";
                return;
            }
            if (dmyFormat.parse(fromDate).compareTo(new Date()) > 0) {
                this.msgStyle = "error";
                this.setMsg("From Date can not be greater than Current Date.");
                return;
            }
            if (dmyFormat.parse(toDate).compareTo(new Date()) > 0) {
                this.msgStyle = "error";
                this.setMsg("To Date can not be greater than Current Date.");
                return;
            }
            if (dmyFormat.parse(fromDate).compareTo(dmyFormat.parse(toDate)) > 0) {
                this.msgStyle = "error";
                this.setMsg("From Date can not be greater than To Date.");
                return;
            }
            String fromDt = ymdFormat.format(dmyFormat.parse(fromDate));
            String toDt = ymdFormat.format(dmyFormat.parse(toDate));
            if (!mode.equalsIgnoreCase("DOWNLOAD")) {
                if (reportType.equalsIgnoreCase("FAILURE")) {
                    customerFailureList = ckycrRemote.getFailureCustomerDetail(this.branch, this.mode, fromDt, toDt);
                } else {
                    customerAllList = ckycrRemote.getInfoForPrintCKYCRReport(this.branch, this.mode, this.reportType, fromDt, toDt);
                    reportName = "CKYCR Report";
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                    Map fillParam = new HashMap();
                    fillParam.put("pReportName", "CKYCR REPORT");
                    fillParam.put("pPrintedBy", getUserName());
                    fillParam.put("pBankName", branchNameandAddress.get(0));
                    fillParam.put("pBankAdd", branchNameandAddress.get(1));
                    fillParam.put("pReportType", this.mode + " , " + this.reportType);
                    fillParam.put("pDateRange", this.fromDate + " To " + this.toDate);

//                    new ReportBean().jasperReport("CKYCRReport", "text/html", new JRBeanCollectionDataSource(customerAllList), fillParam, reportName);
//                    this.setViewID("/report/ReportPagePopUp.jsp");
//                    
                    new ReportBean().openPdf("CKYCR Report_", "CKYCRReport", new JRBeanCollectionDataSource(customerAllList), fillParam);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "CKYCR Report");
                }
            } else {
                customerAllList = ckycrRemote.getDownloadCKYCRReport(branch, mode, reportType, fromDt, toDt);
                reportName = "CKYCR Report";
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParam = new HashMap();
                fillParam.put("pReportName", "CKYCR REPORT");
                fillParam.put("pPrintedBy", getUserName());
                fillParam.put("pBankName", branchNameandAddress.get(0));
                fillParam.put("pBankAdd", branchNameandAddress.get(1));
                fillParam.put("pReportType", this.mode + "," + this.reportType);
                fillParam.put("pDateRange", this.fromDate + " To " + this.toDate);
//                new ReportBean().jasperReport("CKYCRReport", "text/html", new JRBeanCollectionDataSource(customerAllList), fillParam, reportName);
//                this.setViewID("/report/ReportPagePopUp.jsp");

                new ReportBean().openPdf("CKYCR Report_", "CKYCRReport", new JRBeanCollectionDataSource(customerAllList), fillParam);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "CKYCR Report");


            }
            this.setMsg("");
        } catch (Exception ex) {
            this.customerFailureList = null;
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void viewExcelReport() {
        try {
            if (branch.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Branch !";
                return;
            }
            if (mode.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Mode !";
                return;
            }
            if (reportType.equalsIgnoreCase("SELECT")) {
                this.msgStyle = "error";
                this.msg = "Please select Report Type !";
                return;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                this.msgStyle = "error";
                this.msg = "Please fill To Date";
                return;
            }
            if (fromDate == null || fromDate.equalsIgnoreCase("")) {
                this.msgStyle = "error";
                this.msg = "Please fill From Date";
                return;
            }
            if (!Validator.validateDate(fromDate)) {
                this.msgStyle = "error";
                this.msg = "Please select proper From Date";
                return;
            }
            if (!Validator.validateDate(toDate)) {
                this.msgStyle = "error";
                this.msg = "Please select proper To Date";
                return;
            }
            if (dmyFormat.parse(fromDate).compareTo(new Date()) > 0) {
                this.msgStyle = "error";
                this.setMsg("From Date can not be greater than Current Date.");
                return;
            }
            if (dmyFormat.parse(toDate).compareTo(new Date()) > 0) {
                this.msgStyle = "error";
                this.setMsg("To Date can not be greater than Current Date.");
                return;
            }
            if (dmyFormat.parse(fromDate).compareTo(dmyFormat.parse(toDate)) > 0) {
                this.msgStyle = "error";
                this.setMsg("From Date can not be greater than To Date.");
                return;
            }
            String fromDt = ymdFormat.format(dmyFormat.parse(fromDate));
            String toDt = ymdFormat.format(dmyFormat.parse(toDate));
            if (!mode.equalsIgnoreCase("DOWNLOAD")) {
                if (reportType.equalsIgnoreCase("FAILURE")) {
                    customerFailureList = ckycrRemote.getFailureCustomerDetail(this.branch, this.mode, fromDt, toDt);
                } else {
                    customerAllList = ckycrRemote.getInfoForPrintCKYCRReport(this.branch, this.mode, this.reportType, fromDt, toDt);
                    reportName = "CKYCR Report";
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                    Map fillParam = new HashMap();
                    fillParam.put("pReportName", "CKYCR REPORT");
                    fillParam.put("pPrintedBy", getUserName());
                    fillParam.put("pBankName", branchNameandAddress.get(0));
                    fillParam.put("pBankAdd", branchNameandAddress.get(1));
                    fillParam.put("pReportType", this.mode + " , " + this.reportType);
                    fillParam.put("pDateRange", this.fromDate + " To " + this.toDate);

                    // new ReportBean().openPdf("CKYCR Report_", "CKYCRReport", new JRBeanCollectionDataSource(customerAllList), fillParam);

                    new ReportBean().dynamicExcelJasper("CKYCRReport", "excel", new JRBeanCollectionDataSource(customerAllList), fillParam, "CKYCR Report");

                }
            } else {
                customerAllList = ckycrRemote.getDownloadCKYCRReport(branch, mode, reportType, fromDt, toDt);
                reportName = "CKYCR Report";
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParam = new HashMap();
                fillParam.put("pReportName", "CKYCR REPORT");
                fillParam.put("pPrintedBy", getUserName());
                fillParam.put("pBankName", branchNameandAddress.get(0));
                fillParam.put("pBankAdd", branchNameandAddress.get(1));
                fillParam.put("pReportType", this.mode + "," + this.reportType);
                fillParam.put("pDateRange", this.fromDate + " To " + this.toDate);
//                new ReportBean().jasperReport("CKYCRReport", "text/html", new JRBeanCollectionDataSource(customerAllList), fillParam, reportName);
//                this.setViewID("/report/ReportPagePopUp.jsp");

                new ReportBean().dynamicExcelJasper("CKYCRReport", "excel", new JRBeanCollectionDataSource(customerAllList), fillParam, "CKYCR Report");
            }
            this.setMsg("");
        } catch (Exception ex) {
            this.customerFailureList = null;
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void viewReport() {
        try {
            int i, flag = 0;
            for (i = 0; i < customerFailureList.size(); i++) {
                if (customerFailureList.get(i).isCheckBox() == true) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                customerFailureList.get(i).setCheckBox(false);
                String customerId = customerFailureList.get(i).getCustomerId();
                String branchCode = customerFailureList.get(i).getBranchCode();
                String branchName = customerFailureList.get(i).getBranchName();
                String requestBy = customerFailureList.get(i).getRequestby();
                String requestDate = customerFailureList.get(i).getRequestDate();
                String status = customerFailureList.get(i).getStatus();
                String customerName = customerFailureList.get(i).getCustomerName();
                String branchAddress = customerFailureList.get(i).getBranchAddress();
                List<CKYCRFailurePojo> ckycrFailureReport = ckycrRemote.getInfoForPrintFailureReport(customerId, branchCode, customerFailureList.get(i).getMode());
                reportName = "CKYCR Failure Report";
                Map fillParam = new HashMap();
                fillParam.put("pReportName", "CKYCR Failure Report");
                fillParam.put("pPrintedBy", getUserName());
                fillParam.put("pBranchName", branchName);
                fillParam.put("pBranchAddress", branchAddress);
                fillParam.put("pCustomerId", customerId);
                fillParam.put("pCustomerName", customerName);
                fillParam.put("pRequestBy", requestBy);
                fillParam.put("pRequestDate", requestDate);
                fillParam.put("pStatus", status);
                new ReportBean().jasperReport("CKYCRFailureReport", "text/html", new JRBeanCollectionDataSource(ckycrFailureReport), fillParam, reportName);
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
            this.setMsg("");
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void pdfDownload() {
    }

    public void refersh() {
        this.mode = "SELECT";
        this.reportType = "SELECT";
        this.branch = "SELECT";
        this.toDate = "";
        this.fromDate = "";
        this.customerFailureList = null;
        this.setMsg("");
        btnGetDisable = true;
        btnPrintReportDisable = true;
    }

    public String exitForm() {
        return "case1";
    }

    public void test() {
    }
}
