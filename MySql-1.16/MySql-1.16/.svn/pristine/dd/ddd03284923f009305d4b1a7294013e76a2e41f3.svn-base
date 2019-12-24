/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.dto.report.ho.MmsReportPojo;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MmsReport extends BaseBean {

    private String message;
    private String branch;
    private String manDateMode;
    private String manDateType;
    private String reportType;
    private String frDt;
    private String toDt;
    private List<SelectItem> branchList;
    private List<SelectItem> manDateModeList;
    private List<SelectItem> manDateTypeList;
    private List<SelectItem> reportTypeList;
    private NpciMandateFacadeRemote npciMandateRemote;
    private CommonReportMethodsRemote commonReport;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public MmsReport() {
        try {
            npciMandateRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            initData();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void initData() {
        try {

            manDateModeList = new ArrayList<SelectItem>();
            manDateModeList.add(new SelectItem("0", "--Select--"));
            manDateModeList.add(new SelectItem("N", "New"));
            manDateModeList.add(new SelectItem("L", "Legacy"));

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("AL", "All"));
            reportTypeList.add(new SelectItem("A", "Accept"));
            reportTypeList.add(new SelectItem("R", "Reject"));

            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("0", "--Select--"));
            List list = commonReport.getAlphacodeAllAndBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String brncode = ele.get(1).toString().trim().length() < 2 ? "0" + ele.get(1).toString().trim()
                        : ele.get(1).toString().trim();
                branchList.add(new SelectItem(brncode, ele.get(0).toString().trim()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void modeOption() {
        manDateTypeList = new ArrayList<SelectItem>();
        if (manDateMode.equalsIgnoreCase("N")) {
            manDateTypeList = new ArrayList<SelectItem>();
            manDateTypeList.add(new SelectItem("0", "--Select--"));
            manDateTypeList.add(new SelectItem("Create"));
            manDateTypeList.add(new SelectItem("Amend"));
            manDateTypeList.add(new SelectItem("Cancel"));
        } else if (manDateMode.equalsIgnoreCase("L")) {
            manDateTypeList = new ArrayList<SelectItem>();
            manDateTypeList.add(new SelectItem("0", "--Select--"));
            manDateTypeList.add(new SelectItem("Create"));
            manDateTypeList.add(new SelectItem("Amend"));
            // manDateTypeList.add(new SelectItem("Cancel"));
        }
    }

    public void downloadPdf() {
        try {
            if (!fieldValidate()) {
                return;
            }
            String reportName = "MMS Report";
            List<MmsReportPojo> dataList = npciMandateRemote.getMmsReport(this.branch, this.manDateMode, this.manDateType,
                    this.reportType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)));
            if (dataList.isEmpty()) {
                this.setMessage("There is no data to show");
            }
            printPDF(reportName, this.frDt + "-" + this.toDt, "MmsReport", dataList);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void printPDF(String reportName, String reportDt, String jrxmlName, List resultList) {
        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = commonReport.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", reportName);
            fillParams.put("period", reportDt);
            if (manDateMode.equalsIgnoreCase("N")) {
                fillParams.put("pManDtMode", "New");
            } else {
                fillParams.put("pManDtMode", "Legacy");
            }
            new ReportBean().openPdf(reportName, jrxmlName, new JRBeanCollectionDataSource(resultList), fillParams);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", reportName);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean fieldValidate() {
        try {
            if (this.branch == null || this.branch.equals("0")) {
                this.setMessage("Please select branch");
                return false;
            }
            if (this.manDateType == null || this.manDateType.equals("0")) {
                this.setMessage("Please select mandate type");
                return false;
            }
            if (this.reportType == null || this.reportType.equals("0")) {
                this.setMessage("Please select report type");
                return false;
            }
            if (this.frDt == null || this.frDt.equals("")) {
                this.setMessage("Please fill from date");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.frDt)) {
                this.setMessage("Please fill proper from date");
                return false;
            }
            if (this.toDt == null || this.toDt.equals("")) {
                this.setMessage("Please fill to date");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.toDt)) {
                this.setMessage("Please fill proper to date");
                return false;
            }
            if (dmy.parse(this.frDt).compareTo(dmy.parse(this.toDt)) > 0) {
                this.setMessage("From date can not be greater than to date.");
                return false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setBranch("0");
        this.setManDateType("0");
        this.setManDateMode("0");
        this.setReportType("0");
        this.setFrDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    //Getter And Setter
    public String getManDateType() {
        return manDateType;
    }

    public void setManDateType(String manDateType) {
        this.manDateType = manDateType;
    }

    public List<SelectItem> getManDateTypeList() {
        return manDateTypeList;
    }

    public void setManDateTypeList(List<SelectItem> manDateTypeList) {
        this.manDateTypeList = manDateTypeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getManDateMode() {
        return manDateMode;
    }

    public void setManDateMode(String manDateMode) {
        this.manDateMode = manDateMode;
    }

    public List<SelectItem> getManDateModeList() {
        return manDateModeList;
    }

    public void setManDateModeList(List<SelectItem> manDateModeList) {
        this.manDateModeList = manDateModeList;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }
}
