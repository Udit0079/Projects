/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.other.BranchPerformancePojo;
import com.cbs.exception.ApplicationException;
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
public class BranchPerformance extends BaseBean {

    private String errorMsg;
    private String frmDt;
    private String toDt;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private OtherReportFacadeRemote remote;
    private CommonReportMethodsRemote common;
    private List<BranchPerformancePojo> details;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public BranchPerformance() {
        try {
            remote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

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
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void viewReport() throws ApplicationException {
        if (validate()) {
            try {
                String report = "Branch Performance";
                Map fillParams = new HashMap();
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", frmDt + " to " + toDt);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);

                details = remote.getDetailBranchPerformance(frmDt, toDt, branchCode);
                new ReportBean().jasperReport("BranchPerformancrReport", "text/html",
                        new JRBeanCollectionDataSource(details), fillParams, "Branch Performance");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } catch (Exception ex) {
                this.setErrorMsg(ex.getLocalizedMessage());
            }
        }
    }

    public void pdfDownLoad() {
        if (validate()) {
            try {
                String report = "Branch Performance";
                Map fillParams = new HashMap();
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", frmDt + " to " + toDt);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);

                details = remote.getDetailBranchPerformance(frmDt, toDt, branchCode);
                new ReportBean().openPdf("Branch Performance_" + ymd.format(dmy.parse(frmDt)) + " - " + ymd.format(dmy.parse(toDt)), "BranchPerformancrReport", new JRBeanCollectionDataSource(details), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);;
            } catch (Exception ex) {
                this.setErrorMsg(ex.getLocalizedMessage());
            }
        }

    }

    public void excelPrint() {
        details = new ArrayList<BranchPerformancePojo>();
        try {
            details = remote.getDetailBranchPerformance(frmDt, toDt, branchCode);
            FastReportBuilder fastReportBuilder = generateDynamicReport();
            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(details), fastReportBuilder, "Branch Performance" + ymd.format(dmy.parse(frmDt)) + " - " + ymd.format(dmy.parse(toDt)));
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public FastReportBuilder generateDynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            AbstractColumn srNo = ReportBean.getColumn("srNo", Integer.class, "Sr No.", 100);
            AbstractColumn branchName = ReportBean.getColumn("branchName", String.class, "Branch", 100);
            AbstractColumn noOfACOpen = ReportBean.getColumn("noOfACOpen", Long.class, "Total No Of Account Open", 100);
            AbstractColumn noOfACClosed = ReportBean.getColumn("noOfACClosed", Long.class, "Total No Of Account Closed", 100);
            AbstractColumn acOpenNetOff = ReportBean.getColumn("acOpenNetOff", Long.class, "Account Open Net Off", 100);
            AbstractColumn noOfLoanAcOpen = ReportBean.getColumn("noOfLoanAcOpen", Long.class, "No of Loan Account Open", 100);
            AbstractColumn noOfLockerIssue = ReportBean.getColumn("noOfLockerIssue", Long.class, "No of Locker Issue", 100);
            AbstractColumn noOfLockerSurrender = ReportBean.getColumn("noOfLockerSurrender", Long.class, "No Of Locker Surrender", 150);
            AbstractColumn lockerIssueNetOff = ReportBean.getColumn("lockerIssueNetOff", Long.class, "Locker issue Net Off", 100);
            AbstractColumn depositAmt = ReportBean.getColumn("depositAmt", Double.class, "Deposit Amount", 100);
            AbstractColumn atmIssue = ReportBean.getColumn("atmIssue", String.class, "No of Atm Issue", 75);
            AbstractColumn dayBegin = ReportBean.getColumn("dayBegin", String.class, "SOD", 140);
            AbstractColumn dayEnd = ReportBean.getColumn("dayEnd", String.class, "EOD", 140);

            fastReport.addColumn(srNo);
            width = width + srNo.getWidth();

            fastReport.addColumn(branchName);
            width = width + branchName.getWidth();

            fastReport.addColumn(noOfACOpen);
            width = width + noOfACOpen.getWidth();

            fastReport.addColumn(noOfACClosed);
            width = width + noOfACClosed.getWidth();

            fastReport.addColumn(acOpenNetOff);
            width = width + acOpenNetOff.getWidth();

            fastReport.addColumn(noOfLoanAcOpen);
            width = width + noOfLoanAcOpen.getWidth();

            fastReport.addColumn(noOfLockerIssue);
            width = width + noOfLockerIssue.getWidth();

            fastReport.addColumn(noOfLockerSurrender);
            width = width + noOfLockerSurrender.getWidth();

            fastReport.addColumn(lockerIssueNetOff);
            width = width + lockerIssueNetOff.getWidth();

            fastReport.addColumn(depositAmt);
            width = width + depositAmt.getWidth();
            
            fastReport.addColumn(atmIssue);
            width = width + atmIssue.getWidth();

            fastReport.addColumn(dayBegin);
            width = width + dayBegin.getWidth();

            fastReport.addColumn(dayEnd);
            width = width + dayEnd.getWidth();

            Page page = new Page(1012, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Branch Performance");

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return fastReport;
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
        setErrorMsg("");
        frmDt = dmy.format(new Date());
        toDt = dmy.format(new Date());
    }

    public String exitAction() {
        return "case1";
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

    public List<BranchPerformancePojo> getDetails() {
        return details;
    }

    public void setDetails(List<BranchPerformancePojo> details) {
        this.details = details;
    }
}
