/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.clg;

import com.cbs.dto.report.ho.InoutWardClearingPassReturnPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Raza
 */
public class InoutWardClearingPassReturn extends BaseBean {

    private String errorMsg;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String reportBased;
    private List<SelectItem> reportBasedList;
    private String acnoWise;
    private List<SelectItem> acnoWiseList;
    private String acNo;
    private String newAcno;
    private String displayReport;
    private String displayAcNo;
    private String levelName;
    private CommonReportMethodsRemote common;
    private ClgReportFacadeRemote beanRemote;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<InoutWardClearingPassReturnPojo> reportList = new ArrayList<InoutWardClearingPassReturnPojo>();

    public InoutWardClearingPassReturn() {
        try {
            this.setLevelName("Report Based");
            this.setDisplayReport("");
            this.setDisplayAcNo("none");
            acnoWiseList = new ArrayList<SelectItem>();
            acnoWiseList.add(new SelectItem("Select", "--Select--"));
            acnoWiseList.add(new SelectItem("IN", "Individual Account"));
            acnoWiseList.add(new SelectItem("AL", "All Account"));

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("Select", "--Select--"));
            reportTypeList.add(new SelectItem("Inward Clearing Cheques", "Inward Clearing Cheques"));
            reportTypeList.add(new SelectItem("Outward Clearing Cheques", "Outward Clearing Cheques"));

            reportBasedList = new ArrayList<SelectItem>();
            reportBasedList.add(new SelectItem("Select", "--Select--"));
            reportBasedList.add(new SelectItem("Detail", "Detail"));
            reportBasedList.add(new SelectItem("Inward Date Wise", "Inward Date Wise"));
            reportBasedList.add(new SelectItem("Outward Date Wise", "Outward Date Wise"));
            reportBasedList.add(new SelectItem("Summary", "Summary"));

            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            beanRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void onAcnoWise() {
        setErrorMsg("");
        if (acnoWise == null || acnoWise.equalsIgnoreCase("Select")) {
            setErrorMsg("Please select Account Wise !");
            return;
        }

        if (acnoWise.equalsIgnoreCase("AL")) {
            this.setLevelName("Report Based");
            this.setDisplayReport("");
            this.setDisplayAcNo("none");
        } else if (acnoWise.equalsIgnoreCase("IN")) {
            this.setLevelName("Account No.");
            this.setDisplayReport("none");
            this.setDisplayAcNo("");
        } else {
            this.setDisplayReport("");
            this.setDisplayAcNo("none");
        }
    }

    public void setOnAcNo() {
        setErrorMsg("");
        try {
            if (acnoWise.equalsIgnoreCase("IN")) {
                if (acNo == null || acNo.equalsIgnoreCase("")) {
                    this.setErrorMsg("Please fill A/c No.");
                    return;
                }
            }
            this.newAcno = ftsPostRemote.getNewAccountNumber(acNo);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

    }

    public void viewReport() {

        String branchName = "", address = "";
        String report = "Inward Outward Cheque Pass/Return";

        try {

            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please select Proper from date ");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill to Date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please select Proper to Date");
                return;
            }

            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                setErrorMsg("From date should less than To date !");
                return;
            }
            if (reportType.equalsIgnoreCase("Select")) {
                setErrorMsg("Please select Report Type !");
                return;
            }

            if (acnoWise.equalsIgnoreCase("Select")) {
                setErrorMsg("Please select Account Wise !");
                return;
            }

            if (acnoWise.equalsIgnoreCase("AL")) {
                if (reportBased.equalsIgnoreCase("Select")) {
                    setErrorMsg("Please select Report Based !");
                    return;
                }
            } else {
                if (acNo == null || acNo.equalsIgnoreCase("")) {
                    setErrorMsg("Please fill Proper A/c No !");
                    return;
                }
            }

            List brnAddrList = new ArrayList();
            if (branchCode.equalsIgnoreCase("0A")) {
                brnAddrList = common.getBranchNameandAddress("90");
            } else {
                brnAddrList = common.getBranchNameandAddress(branchCode);
            }

            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            String hColumn = "";
            if (reportType.equalsIgnoreCase("Inward Clearing Cheques")) {
                hColumn = "Favour Of";
            } else {
                hColumn = "Bank Name";
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            if (reportBased.equalsIgnoreCase("Inward Date Wise") || reportBased.equalsIgnoreCase("Outward Date Wise")) {
                fillParams.put("pReprtName", "Inward & Outward Returns Detail (%age)");
            } else {
                fillParams.put("pReprtName", report);
            }
            fillParams.put("pReportDate", frmDt + " To " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportType", reportType);
            fillParams.put("pHeader", hColumn);
            String forDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));
            if (acnoWise.equalsIgnoreCase("IN")) {
                reportBased = "Detail";
            }

            reportList = beanRemote.getInoutwardChkPassReturn(reportBased, reportType, branchCode, forDate, toDate, acnoWise, acNo);
            if (reportList.isEmpty()) {
                errorMsg = "Data does not exit";
                return;
            }
            if (reportBased.equalsIgnoreCase("Detail")) {
                new ReportBean().jasperReport("ChequePassReturnDetail", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Inward Outward Cheque Pass/Return");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else if (reportBased.equalsIgnoreCase("Summary")) {
                new ReportBean().jasperReport("Pass_Return_Percent", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Inward Outward Cheque Pass/Return");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else if (reportBased.equalsIgnoreCase("Inward Date Wise") || reportBased.equalsIgnoreCase("Outward Date Wise")) {
                new ReportBean().jasperReport("Inward&OutwardReturnsDetail", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Inward Outward Cheque Pass/Return");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String getReportBased() {
        return reportBased;
    }

    public void setReportBased(String reportBased) {
        this.reportBased = reportBased;
    }

    public List<SelectItem> getReportBasedList() {
        return reportBasedList;
    }

    public void setReportBasedList(List<SelectItem> reportBasedList) {
        this.reportBasedList = reportBasedList;
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
        this.setLevelName("Report Based");
        this.setDisplayReport("");
        this.setDisplayAcNo("none");
        this.reportType = "Select";
        this.reportBased = "Select";
        this.acnoWise = "Select";
        this.acNo = "";
        this.newAcno = "";
    }

    public String exitAction() {
        refresh();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getAcnoWise() {
        return acnoWise;
    }

    public void setAcnoWise(String acnoWise) {
        this.acnoWise = acnoWise;
    }

    public List<SelectItem> getAcnoWiseList() {
        return acnoWiseList;
    }

    public void setAcnoWiseList(List<SelectItem> acnoWiseList) {
        this.acnoWiseList = acnoWiseList;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getDisplayReport() {
        return displayReport;
    }

    public void setDisplayReport(String displayReport) {
        this.displayReport = displayReport;
    }

    public String getDisplayAcNo() {
        return displayAcNo;
    }

    public void setDisplayAcNo(String displayAcNo) {
        this.displayAcNo = displayAcNo;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
