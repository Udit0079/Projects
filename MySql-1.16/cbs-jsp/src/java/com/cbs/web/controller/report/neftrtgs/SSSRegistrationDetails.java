/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.neftrtgs;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.SSSFileGeneratorFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.SSSRegistrationPojo;
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
 * @author Admin
 */
public class SSSRegistrationDetails extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String schemeCode;
    private List<SelectItem> schemeCodeList;
    private String vendorCode;
    private List<SelectItem> vendorCodeList;
    private String repStatus;
    private List<SelectItem> repStatusList;
    private Date frDt = new Date();
    private Date toDt = new Date();
    private Date uploadDt = new Date();
    private String status;
    private List<SelectItem> statusList;
    private String ctrFile;
    private List<SelectItem> ctrFileList;
    private String display = "none";
    private String repDisplay = "";
    private String genType;
    private List<SelectItem> genTypeList;
    private CommonReportMethodsRemote RemoteCode;
    private SSSFileGeneratorFacadeRemote sssReportRemote;
    List<SSSRegistrationPojo> reportList = new ArrayList<SSSRegistrationPojo>();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public SSSRegistrationDetails() {
        try {
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            sssReportRemote = (SSSFileGeneratorFacadeRemote) ServiceLocator.getInstance().lookup("SSSFileGeneratorFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("A", "ALL"));
            statusList.add(new SelectItem("E", "Pending"));
            statusList.add(new SelectItem("L", "Premium  Calculate"));
            statusList.add(new SelectItem("R", "Registrated"));
            statusList.add(new SelectItem("Re", "Renew"));
           
            repStatusList = new ArrayList<SelectItem>();
            repStatusList.add(new SelectItem("D", "Detail"));
            repStatusList.add(new SelectItem("S", "Summary"));
            repStatusList.add(new SelectItem("T", "Rejection"));
           // repStatusList.add(new SelectItem("Re", "Renew"));
            
            genTypeList = new ArrayList<SelectItem>();
            genTypeList.add(new SelectItem("ALL", "ALL"));
            genTypeList.add(new SelectItem("F", "Female"));
            genTypeList.add(new SelectItem("M", "Male"));
            genTypeList.add(new SelectItem("O", "Other"));

            schemeCodeList = new ArrayList<SelectItem>();
            vendorCodeList = new ArrayList<SelectItem>();
            ctrFileList = new ArrayList<SelectItem>();

            getScheme();
           // getVendor();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getScheme() {
        try {
            schemeCodeList = new ArrayList<SelectItem>();
            List list = RemoteCode.getRefRecList("215");
            schemeCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                schemeCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void getVendor() {
        try {
            vendorCodeList = new ArrayList<SelectItem>();
            List list = sssReportRemote.getVendors(schemeCode);
            vendorCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                vendorCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void onChange() {
        try {
            if (repStatus.equalsIgnoreCase("T")) {
                setDisplay("");
                //retriveActualFile();
                setRepDisplay("none");
            } else {
                setDisplay("none");
                setRepDisplay("");
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void retriveActualFile() {
        try {
            ctrFileList = new ArrayList<SelectItem>();
            ctrFileList.add(new SelectItem("--Select--"));
            List list = sssReportRemote.getActualFile(schemeCode, vendorCode, ymd.format(uploadDt));
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                ctrFileList.add(new SelectItem(vec.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void printAction() {

        String report = "";
        if (repStatus.equalsIgnoreCase("T")) {
            report = "SSS File Rejection Detail";
        } else {
            report = "SSS Account Registration Detail";
        }
        if (schemeCode.equalsIgnoreCase("S")) {
            setMessage("Please select Scheme Name.");
            return;
        }
        if (vendorCode.equalsIgnoreCase("S")) {
            setMessage("Please select Vendor Name.");
            return;
        }

        if (frDt == null) {
            setMessage("Please fill From Date");
            return;
        }

        if (!Validator.validateDate(dmy.format(frDt))) {
            setMessage("Please fill proper From Date");
            return;
        }
        if (frDt.after(new Date())) {
            setMessage("From Date should be less than Current Date.");
            return;
        }

        if (toDt == null) {
            setMessage("Please fill To Date");
            return;
        }

        if (!Validator.validateDate(dmy.format(toDt))) {
            setMessage("Please fill proper To Date");
            return;
        }
        if (toDt.after(new Date())) {
            setMessage("To Date should be less than Current Date.");
            return;
        }

        if (repStatus.equalsIgnoreCase("T")) {
            if (ctrFile.equalsIgnoreCase("--Select--")) {
                setMessage("Please select Control File");
                return;
            }
        }

        try {
            double premiumAmt = 0d;
            if (!schemeCode.equalsIgnoreCase("APY")) {
                premiumAmt = sssReportRemote.getPremiumAmt(schemeCode, vendorCode);
            }

            Map fillParams = new HashMap();
            List brNameAndAddList = RemoteCode.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", dmy.format(frDt));
            fillParams.put("pReportName", report);
            fillParams.put("pScheme", this.schemeCode.equalsIgnoreCase("PMJJBY") ? "JEEVAN JYOTI BIMA YOJANA" : schemeCode.equalsIgnoreCase("PMSBY") ? "SURAKSHA BIMA YOJANA" : schemeCode.equalsIgnoreCase("APY") ? "ATAL PENSION YOJNA" : "");
            fillParams.put("pVendor", this.vendorCode.equalsIgnoreCase("LIC") ? "LIC OF INDIA" : vendorCode.equalsIgnoreCase("UIC") ? "UNITED INDIA INSURANCE" : vendorCode.equalsIgnoreCase("OIC") ? "ORIENTAL INSURANCE" : "");
            fillParams.put("premium", premiumAmt);
            if (repStatus.equalsIgnoreCase("T")) {
                fillParams.put("pRejectedFile", this.ctrFile);
            }

            if (repStatus.equalsIgnoreCase("S")) {
                reportList = sssReportRemote.getSSSReportSummaryData(branchCode, schemeCode, vendorCode, ymd.format(frDt), ymd.format(toDt), status);
            } else if (repStatus.equalsIgnoreCase("D")) {
                reportList = sssReportRemote.getSSSReportData(branchCode, schemeCode, vendorCode, ymd.format(frDt), ymd.format(toDt), status,genType,repStatus);
            } else if (repStatus.equalsIgnoreCase("T")) {
                reportList = sssReportRemote.getRejectedFileDetail(branchCode, schemeCode, vendorCode, ctrFile, ymd.format(uploadDt));
            }


            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            if (repStatus.equalsIgnoreCase("D")) {
                new ReportBean().jasperReport("SSSAcRegistration", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "SSS Account Registration Detail");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else if (repStatus.equalsIgnoreCase("S")) {

                new ReportBean().jasperReport("SSSAcRegistration_Summary", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "SSS Account Registration Detail");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);

            } else if (repStatus.equalsIgnoreCase("T")) {
                new ReportBean().jasperReport("SSSRejectedFileDetail", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "SSS File Rejection Detail");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void downloadPdf() {

        String report = "";
        if (repStatus.equalsIgnoreCase("T")) {
            report = "SSS File Rejection Detail";
        } else {
            report = "SSS Account Registration Detail";
        }

        if (schemeCode.equalsIgnoreCase("S")) {
            setMessage("Please select Scheme Name.");
            return;
        }
        if (vendorCode.equalsIgnoreCase("S")) {
            setMessage("Please select Vendor Name.");
            return;
        }

        if (frDt == null) {
            setMessage("Please fill From Date");
            return;
        }

        if (!Validator.validateDate(dmy.format(frDt))) {
            setMessage("Please fill proper From Date");
            return;
        }
        if (frDt.after(new Date())) {
            setMessage("From Date should be less than Current Date.");
            return;
        }

        if (toDt == null) {
            setMessage("Please fill To Date");
            return;
        }

        if (!Validator.validateDate(dmy.format(toDt))) {
            setMessage("Please fill proper To Date");
            return;
        }
        if (toDt.after(new Date())) {
            setMessage("To Date should be less than Current Date.");
            return;
        }

        try {
            double premiumAmt = 0d;
            if (!schemeCode.equalsIgnoreCase("APY")) {
                premiumAmt = sssReportRemote.getPremiumAmt(schemeCode, vendorCode);
            }

            Map fillParams = new HashMap();
            List brNameAndAddList = RemoteCode.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", dmy.format(frDt));
            fillParams.put("pReportName", report);
            fillParams.put("pScheme", this.schemeCode.equalsIgnoreCase("PMJJBY") ? "JEEVAN JYOTI BIMA YOJANA" : schemeCode.equalsIgnoreCase("PMSBY") ? "SURAKSHA BIMA YOJANA" : schemeCode.equalsIgnoreCase("APY") ? "ATAL PENSION YOJNA" : "");
            fillParams.put("pVendor", this.vendorCode.equalsIgnoreCase("LIC") ? "LIC OF INDIA" : vendorCode.equalsIgnoreCase("NSDL") ? "NSDL" : vendorCode.equalsIgnoreCase("OIC") ? "ORIENTAL INSURANCE" : "");
            fillParams.put("premium", premiumAmt);
            if (repStatus.equalsIgnoreCase("T")) {
                fillParams.put("pRejectedFile", this.ctrFile);
            }


            if (repStatus.equalsIgnoreCase("S")) {
                reportList = sssReportRemote.getSSSReportSummaryData(branchCode, schemeCode, vendorCode, ymd.format(frDt), ymd.format(toDt), status);
            } else if (repStatus.equalsIgnoreCase("D")) {
                reportList = sssReportRemote.getSSSReportData(branchCode, schemeCode, vendorCode, ymd.format(frDt), ymd.format(toDt), status,genType,repStatus);
            } else if (repStatus.equalsIgnoreCase("T")) {
                reportList = sssReportRemote.getRejectedFileDetail(branchCode, schemeCode, vendorCode, ctrFile, ymd.format(uploadDt));
            }

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            if (repStatus.equalsIgnoreCase("D")) {
                new ReportBean().openPdf("SSSAcRegistration_" + ymd.format(dmy.parse(this.getTodayDate())), "SSSAcRegistration", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else if (repStatus.equalsIgnoreCase("S")) {
                new ReportBean().openPdf("SSSAcRegistration_Summary_" + ymd.format(dmy.parse(this.getTodayDate())), "SSSAcRegistration_Summary", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else if (repStatus.equalsIgnoreCase("T")) {
                new ReportBean().openPdf("SSSRejectedFileDetail_" + ymd.format(dmy.parse(this.getTodayDate())), "SSSRejectedFileDetail", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refresh() {
        setMessage("");
        setSchemeCode("S");
        setVendorCode("S");
        setFrDt(new Date());
        setToDt(new Date());
        setRepStatus("D");
        setUploadDt(new Date());
        setDisplay("none");
        setRepDisplay("");
    }

    public String exitAction() {
        refresh();
        return "case1";
    }
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public Date getFrDt() {
        return frDt;
    }

    public void setFrDt(Date frDt) {
        this.frDt = frDt;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
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

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }

    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public List<SelectItem> getVendorCodeList() {
        return vendorCodeList;
    }

    public void setVendorCodeList(List<SelectItem> vendorCodeList) {
        this.vendorCodeList = vendorCodeList;
    }

    public String getRepStatus() {
        return repStatus;
    }

    public void setRepStatus(String repStatus) {
        this.repStatus = repStatus;
    }

    public List<SelectItem> getRepStatusList() {
        return repStatusList;
    }

    public void setRepStatusList(List<SelectItem> repStatusList) {
        this.repStatusList = repStatusList;
    }

    public Date getUploadDt() {
        return uploadDt;
    }

    public void setUploadDt(Date uploadDt) {
        this.uploadDt = uploadDt;
    }

    public String getCtrFile() {
        return ctrFile;
    }

    public void setCtrFile(String ctrFile) {
        this.ctrFile = ctrFile;
    }

    public List<SelectItem> getCtrFileList() {
        return ctrFileList;
    }

    public void setCtrFileList(List<SelectItem> ctrFileList) {
        this.ctrFileList = ctrFileList;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getRepDisplay() {
        return repDisplay;
    }

    public void setRepDisplay(String repDisplay) {
        this.repDisplay = repDisplay;
    }

    public String getGenType() {
        return genType;
    }

    public void setGenType(String genType) {
        this.genType = genType;
    }

    public List<SelectItem> getGenTypeList() {
        return genTypeList;
    }

    public void setGenTypeList(List<SelectItem> genTypeList) {
        this.genTypeList = genTypeList;
    }
    
    
}
