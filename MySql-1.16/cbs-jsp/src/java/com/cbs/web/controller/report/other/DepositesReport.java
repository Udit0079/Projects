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
import com.cbs.dto.report.DepositesReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
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
public class DepositesReport extends BaseBean {

    private String errorMsg;
    private String accType;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private List<SelectItem> acctTypeList;
    private String reportFormat;
    private List<SelectItem> reportFormatIn;
    private String frmDt;
    private String toDt;
    private double amt;
    private OtherReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<DepositesReportPojo> repList = new ArrayList<DepositesReportPojo>();

    public DepositesReport() {
        try {
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
            local = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acctTypeList = new ArrayList<SelectItem>();
            reportFormatIn = new ArrayList<SelectItem>();
            initData();
            reportFormatIn.add(new SelectItem("D", "In Detail"));
            reportFormatIn.add(new SelectItem("S", "In Summary"));
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    private void initData() {
        acctTypeList.clear();
        try {
            //List result = local.getAcctsbcaTypeList();
            List result = local.getAcctTypeList();
            Vector vtr = null;
            if (!result.isEmpty()) {
                acctTypeList.add(new SelectItem("ALL", "ALL"));
                for (int i = 0; i < result.size(); i++) {
                    vtr = (Vector) result.get(i);
                    acctTypeList.add(new SelectItem(vtr.get(0), vtr.get(0).toString()));
                }
            }
            // acctTypeList.add(new SelectItem("ALL", "ALL"));
        } catch (Exception e) {
        }
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

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public List<SelectItem> getReportFormatIn() {
        return reportFormatIn;
    }

    public void setReportFormatIn(List<SelectItem> reportFormatIn) {
        this.reportFormatIn = reportFormatIn;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
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

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String viewReport() {
        try {
            String fromDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return null;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please select Proper from date ");
                return null;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill to Date");
                return null;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please select Proper to Date");
                return null;
            }
            if (amt == 0) {
                setErrorMsg("Please fill Amount");
                return null;
            }

            repList = local.getDepositesReport("JRXML", accType, fromDate, toDate, branchCode, amt, reportFormat);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }
            String ad[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
            String report = "Cash Deposit Report";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pReportDate", frmDt + " to " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pAmount", amt);
            fillParams.put("pType", accType);
            fillParams.put("pbankName", ad[0]);
            fillParams.put("pbankAddress", ad[1]);
            new ReportBean().jasperReport("DepositesReport", "text/html", new JRBeanCollectionDataSource(repList), fillParams, report);
            return "report";
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
            return null;
        }
    }

    public void viewPdfReport() {
        try {
            String fromDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));
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
            if (amt == 0) {
                setErrorMsg("Please fill Amount");
                return;
            }

            repList = local.getDepositesReport("JRXML", accType, fromDate, toDate, branchCode, amt, reportFormat);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }
            String ad[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
            String report = "Cash Deposit Report";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pReportDate", frmDt + " to " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pAmount", amt);
            fillParams.put("pType", accType);
            fillParams.put("pbankName", ad[0]);
            fillParams.put("pbankAddress", ad[1]);
            new ReportBean().openPdf("Cash Deposit Report_" + ymd.format(dmy.parse(getTodayDate())), "DepositesReport", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            String fromDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return null;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please select Proper from date ");
                return null;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill to Date");
                return null;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please select Proper to Date");
                return null;
            }
            if (amt == 0) {
                setErrorMsg("Please fill Amount");
                return null;
            }
            repList = local.getDepositesReport("EXCEL", accType, fromDate, toDate, branchCode, amt, reportFormat);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name of Depositor", 200);
            AbstractColumn fName = ReportBean.getColumn("fName", String.class, "Father's Name ", 200);
            AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 60);
            AbstractColumn accNo = ReportBean.getColumn("accNo", String.class, "A/c.Number", 100);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "Pan Number", 80);
            AbstractColumn custAdd = ReportBean.getColumn("custAdd", String.class, "Customer Address", 500);
            AbstractColumn crAmt = ReportBean.getColumn("crAmt", BigDecimal.class, "Deposite Amt", 150);
            AbstractColumn depositeDt = ReportBean.getColumn("depositeDt", String.class, "Deposite date", 60);
            AbstractColumn intAmt = ReportBean.getColumn("intAmt", BigDecimal.class, "Int Amount", 80);
            AbstractColumn paymtDt = ReportBean.getColumn("paymtDt", String.class, "Date of paymt.Int", 60);

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(fName);
            width = width + fName.getWidth();

            fastReport.addColumn(dob);
            width = width + dob.getWidth();

            fastReport.addColumn(accNo);
            width = width + accNo.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(custAdd);
            width = width + custAdd.getWidth();

            fastReport.addColumn(crAmt);
            crAmt.setStyle(style);
            width = width + 2 * crAmt.getWidth();

            fastReport.addColumn(depositeDt);
            width = width + depositeDt.getWidth();

            fastReport.addColumn(intAmt);
            intAmt.setStyle(style);

            fastReport.addColumn(paymtDt);
            width = width + paymtDt.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Cash Deposite Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), fastReport, "Cash Deposite Report");

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

        return fastReport;
    }

    public void refreshPage() {
        this.setErrorMsg("");
        this.setAmt(0.0d);
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exitPage() {
        refreshPage();
        return "case1";
    }
}
