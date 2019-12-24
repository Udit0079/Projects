/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.ho.AtmStatusPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class AtmStatus extends BaseBean {

    private String errorMsg;
    private String branchCode;
    private String branch;
    private String mode;
    private List<SelectItem> modelist;
    private String fileType;
    private List<SelectItem> fileTypeList;
    private String status;
    private List<SelectItem> statusList;
    private int isSeqMode = 0;
    private String normalDisplay = "none";
    private String seqFileDisplay = "none";
    private List<SelectItem> branchCodeList;
    private List<SelectItem> branchList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    Date fromDate = new Date();
    Date toDate = new Date();
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    private TdReceiptManagementFacadeRemote RemoteCode;
    private MiscReportFacadeRemote remoteFacade;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private List<AtmStatusPojo> reportList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public AtmStatus() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            isSeqMode = ftsRemote.getCodeForReportName("IS-ATM-SEQ-FILE");
            reportTypeList = new ArrayList();
            modelist = new ArrayList();
            statusList = new ArrayList();
            fileTypeList = new ArrayList();


            if (isSeqMode != 1) {
                this.normalDisplay = "none";
                this.seqFileDisplay = "";
                reportTypeList.add(new SelectItem("ALL", "ALL"));
                reportTypeList.add(new SelectItem("A", "Active"));
                reportTypeList.add(new SelectItem("I", "Inactive"));

                List brncode = new ArrayList();
                brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
                branchCodeList = new ArrayList<SelectItem>();
                if (!brncode.isEmpty()) {
                    for (int i = 0; i < brncode.size(); i++) {
                        Vector brnVector = (Vector) brncode.get(i);
                        branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                    }
                }
            } else {
                this.normalDisplay = "";
                this.seqFileDisplay = "none";

                List brncode = new ArrayList();
                brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
                branchList = new ArrayList<SelectItem>();
                if (!brncode.isEmpty()) {
                    for (int i = 0; i < brncode.size(); i++) {
                        Vector brnVector = (Vector) brncode.get(i);
                        branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                    }
                }

                statusList.add(new SelectItem("0", "--Select--"));
                statusList.add(new SelectItem("E", "Entered"));
                statusList.add(new SelectItem("G", "Generated"));
                statusList.add(new SelectItem("ALL", "All"));

                fileTypeList.add(new SelectItem("0", "--Select--"));
                fileTypeList.add(new SelectItem("L", "All"));
                fileTypeList.add(new SelectItem("N", "New Card & Add On Card"));
                fileTypeList.add(new SelectItem("S", "Bulk Card Status Change"));
                fileTypeList.add(new SelectItem("C", "Bulk Name Change"));
                fileTypeList.add(new SelectItem("A", "Bulk Add On Account"));
                fileTypeList.add(new SelectItem("D", "Detail Update"));
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void viewReport() {
        String report = "Atm Card Detail Report";
        try {
            if (calFromDate == null) {
                errorMsg = "Please Fill From Date";
                return;
            }

            if (caltoDate == null) {
                errorMsg = "Please Fill To Date";
                return;
            }
            if (calFromDate.after(caltoDate)) {
                errorMsg = "From Date should be less than To Date !";
                return;
            }
            String FromDt = dmy.format(calFromDate);
            String ToDate = dmy.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = new ArrayList<AtmStatusPojo>();
            reportList = remoteFacade.getAtmStatusData(branchCode, reportType, ymd.format(calFromDate), ymd.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("Atm_Card_Detail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Atm Card Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            refresh();
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {

            if (calFromDate == null) {
                errorMsg = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                errorMsg = "Please Fill To Date";
                return null;
            }
            if (calFromDate.after(caltoDate)) {
                errorMsg = "From Date should be less than To Date !";
                return null;
            }
            reportList = remoteFacade.getAtmStatusData(branchCode, reportType, ymd.format(calFromDate), ymd.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "A/c . Number", 90);
            //AbstractColumn acNo1 = ReportBean.getColumn("acNo1", String.class, "", 100);
            //AbstractColumn acNo2 = ReportBean.getColumn("acNo2", String.class, "", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name", 200);
            AbstractColumn add1 = ReportBean.getColumn("add1", String.class, "Per. Address", 275);
            AbstractColumn add2 = ReportBean.getColumn("add2", String.class, "Mail Address", 275);
            //AbstractColumn add3 = ReportBean.getColumn("add3", String.class, "Address3", 200);
            //AbstractColumn dist = ReportBean.getColumn("dist", String.class, "District", 200);
            //AbstractColumn state = ReportBean.getColumn("state", String.class, "State", 200);
            AbstractColumn atmCardNo = ReportBean.getColumn("atmCardNo", String.class, "Atm Card No", 150);
            AbstractColumn mobileNo = ReportBean.getColumn("mobileNo", String.class, "Mobile No", 100);
            //AbstractColumn type = ReportBean.getColumn("type", String.class, "Type", 100);
            //AbstractColumn verify = ReportBean.getColumn("verify", String.class, "Verify", 50);

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

//            fastReport.addColumn(acNo1);
//            width = width + acNo1.getWidth();
//
//            fastReport.addColumn(acNo2);
//            width = width + acNo2.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(add1);
            width = width + add1.getWidth();

            fastReport.addColumn(add2);
            width = width + add2.getWidth();

//            fastReport.addColumn(add3);
//            width = width + add3.getWidth();
//
//            fastReport.addColumn(dist);
//            width = width + dist.getWidth();
//
//            fastReport.addColumn(state);
//            width = width + state.getWidth();

            fastReport.addColumn(atmCardNo);
            width = width + atmCardNo.getWidth();

            fastReport.addColumn(mobileNo);
            width = width + mobileNo.getWidth();

//            fastReport.addColumn(type);
//            width = width + type.getWidth();
//
//            fastReport.addColumn(verify);
//            width = width + verify.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Atm Card Detail Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), fastReport, "Atm Card Detail Report");

        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
        return fastReport;

    }

    public void refresh() {
        setErrorMsg("");
        setCalFromDate(new Date());
        setCaltoDate(new Date());
    }

    public String exitAction() {
        refresh();
        return "case1";
    }

    
     public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
   

    public void viewcardReport() {
        String report = "Atm Card Mapping Detail Report";
        try {
            if (fileType.equalsIgnoreCase(null) || fileType.equalsIgnoreCase("0") || fileType.equalsIgnoreCase("")) {
                errorMsg = "Please select the file type.";
                return;
            }
            if (status.equalsIgnoreCase(null) || status.equalsIgnoreCase("0") || fileType.equalsIgnoreCase("")) {
                errorMsg = "Please select the status.";
                return;
            }
            if (fromDate == null) {
                errorMsg = "Please Fill From Date";
                return;
            }

            if (toDate == null) {
                errorMsg = "Please Fill To Date";
                return;
            }
            if (fromDate.after(toDate)) {
                errorMsg = "From Date should be less than To Date !";
                return;
            }
            String FromDt = dmy.format(fromDate);
            String ToDate = dmy.format(toDate);
            String duration = FromDt + "  TO  " + ToDate;

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pDate", getTodayDate());
            fillParams.put("pReportName", report);
            if (this.fileType.equalsIgnoreCase("N")) {
                fillParams.put("pFileType", "New Card & Add On Card");
            } else if (this.fileType.equalsIgnoreCase("S")) {
                fillParams.put("pFileType", "Bulk Card Status Change");
            } else if (this.fileType.equalsIgnoreCase("C")) {
                fillParams.put("pFileType", "Bulk Name Change");
            } else if (this.fileType.equalsIgnoreCase("A")) {
                fillParams.put("pFileType", "Bulk Add On Account");
            } else if (this.fileType.equalsIgnoreCase("D")) {
                fillParams.put("pFileType", "Detail Update");
            } else if (this.fileType.equalsIgnoreCase("L")) {
                fillParams.put("pFileType", "All");
            }
            fillParams.put("pStatus", status);

            reportList = remoteFacade.getAtmDetailData(this.branch, this.fileType.trim(), this.status.trim(), ymd.format(fromDate), ymd.format(toDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            new ReportBean().jasperReport("atm_card_mapping_detail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Atm Card Mapping Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            refresh();
        } catch (Exception ex) {
           // ex.printStackTrace();
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void refreshform() {
        this.setErrorMsg("");
        this.setFileType("0");
        this.setStatus("");
        this.setBranch("");
        setFromDate(new Date());
        setToDate(new Date());
    }

    public String exitBtnAction() {
        refreshform();
        return "case1";
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<SelectItem> getModelist() {
        return modelist;
    }

    public void setModelist(List<SelectItem> modelist) {
        this.modelist = modelist;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
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

    public List<AtmStatusPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<AtmStatusPojo> reportList) {
        this.reportList = reportList;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getNormalDisplay() {
        return normalDisplay;
    }

    public void setNormalDisplay(String normalDisplay) {
        this.normalDisplay = normalDisplay;
    }

    public String getSeqFileDisplay() {
        return seqFileDisplay;
    }

    public void setSeqFileDisplay(String seqFileDisplay) {
        this.seqFileDisplay = seqFileDisplay;
    }

    public void setIsSeqMode(int isSeqMode) {
        this.isSeqMode = isSeqMode;
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
}
