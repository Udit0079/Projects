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
import com.cbs.dto.report.NominationRegisterPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
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
 * @author root
 */
public class NomineeDetails extends BaseBean {

    private String branchcode;
    private List<SelectItem> branchcodeList;
    private String nomineeStatus;
    private List<SelectItem> nomineeStatusList;
    private String nomDisplay = "none";
    private String fromDate;
    private String toDate;
    private String errorMsg;
    private String reportType;
    private List<SelectItem> reportTypeList;
    SimpleDateFormat ymdFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    List<NominationRegisterPojo> list;
    private OtherReportFacadeRemote remote;

    public NomineeDetails() {
        try {
            this.fromDate = ymdFormat.format(new Date());
            this.toDate = ymdFormat.format(new Date());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchcodeList = new ArrayList();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchcodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            reportTypeList = new ArrayList();
            reportTypeList.add(new SelectItem("0", "--SELECT--"));
            reportTypeList.add(new SelectItem("1", "NORMAL NOMINEE REPORT"));
            reportTypeList.add(new SelectItem("2", "LOCKER NOMINEE REPORT"));

            nomineeStatusList = new ArrayList();
            nomineeStatusList.add(new SelectItem("0", "--SELECT--"));
            nomineeStatusList.add(new SelectItem("Y", "YES"));
            nomineeStatusList.add(new SelectItem("N", "NO"));



        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void chenageOperation() {
        setErrorMsg("");
        if (reportType.equalsIgnoreCase("1")) {
            this.nomDisplay = "";
        } else {
            this.nomDisplay = "none";
        }

    }

    public void excelDownload() {
        try {
            if (this.fromDate.equalsIgnoreCase("") || this.fromDate == null) {
                this.setErrorMsg("Please Fill From Date");
                return;
            }
            if (this.toDate.equalsIgnoreCase("") || this.toDate == null) {
                this.setErrorMsg("Please Fiil To Date");
                return;
            }
            if (ymdFormat.parse(toDate).before(ymdFormat.parse(fromDate))) {
                this.setErrorMsg("Please Fill valid To Date");
                return;
            }

            if (reportType.equalsIgnoreCase("0") || reportType == null) {
                this.setErrorMsg("Please Fill Report Type");
                return;
            }

            if (reportType.equalsIgnoreCase("1")) {
                if (nomineeStatus.equalsIgnoreCase("0") || reportType == null) {
                    this.setErrorMsg("Please Select  Nomenee Status");
                    return;
                }
            }

            list = new ArrayList();
            list = remote.getNomineeDetails(branchcode, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)), this.reportType, this.nomineeStatus);
            if (reportType.equalsIgnoreCase("1")) {
                String report = "Nomination Register Report";
                FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(list), fastReportBuilder, "Nomination Register Report");
            } else if (reportType.equalsIgnoreCase("2")) {
                String report = "Locker Nomination Register Report";
                FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(list), fastReportBuilder, "Locker Nomination Register Report");
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            if (reportType.equalsIgnoreCase("1")) {
                AbstractColumn accountType = ReportBean.getColumn("actype", String.class, "Account Type", 100);
                AbstractColumn custId = ReportBean.getColumn("customerId", String.class, "Customer Id", 100);
                AbstractColumn acno = ReportBean.getColumn("acno", String.class, "Account No", 200);
                AbstractColumn accountHolderName = ReportBean.getColumn("acnoHolderName", String.class, "Account Holder Name", 200);
                AbstractColumn nomineeNo = ReportBean.getColumn("nomineeNo", String.class, "Nominee No", 60);
                AbstractColumn nomineeName = ReportBean.getColumn("nomName", String.class, "Nominee Name", 200);
                AbstractColumn nomadd = ReportBean.getColumn("nomAdd", String.class, "Nominee Address", 300);
                AbstractColumn nomDob = ReportBean.getColumn("nomDob", String.class, "Nominee DOB", 80);
                AbstractColumn relation = ReportBean.getColumn("relation", String.class, "Relation", 60);

                fastReport.addColumn(accountType);
                width = width + accountType.getWidth();

                fastReport.addColumn(custId);
                width = width + custId.getWidth();

                fastReport.addColumn(acno);
                width = width + acno.getWidth();

                fastReport.addColumn(accountHolderName);
                width = width + accountHolderName.getWidth();

                fastReport.addColumn(nomineeNo);
                width = width + nomineeNo.getWidth();

                fastReport.addColumn(nomineeName);
                width = width + nomineeName.getWidth();

                fastReport.addColumn(nomadd);
                width = width + nomadd.getWidth();

                fastReport.addColumn(nomDob);
                width = width + nomDob.getWidth();

                fastReport.addColumn(relation);
                width = width + relation.getWidth();

                Page page = new Page(1042, width, false);
                fastReport.setMargins(0, 0, 0, 0);
                fastReport.setPageSizeAndOrientation(page);
                fastReport.setTitle("Nomination Register Report");
            } else if (reportType.equalsIgnoreCase("2")) {
                AbstractColumn accountType = ReportBean.getColumn("actype", String.class, "Account Type", 100);
                AbstractColumn custId = ReportBean.getColumn("customerId", String.class, "Customer Id", 100);
                AbstractColumn acno = ReportBean.getColumn("acno", String.class, "Account No", 100);
                AbstractColumn accountHolderName = ReportBean.getColumn("acnoHolderName", String.class, "Account Holder Name", 200);
                AbstractColumn nomineeName = ReportBean.getColumn("nomName", String.class, "Nominee Name", 200);
                AbstractColumn nomineeNo = ReportBean.getColumn("nomineeNo", String.class, "Locker No", 60);
                AbstractColumn relation = ReportBean.getColumn("relation", String.class, "Cab No", 60);

                fastReport.addColumn(accountType);
                width = width + accountType.getWidth();

                fastReport.addColumn(custId);
                width = width + custId.getWidth();

                fastReport.addColumn(acno);
                width = width + acno.getWidth();

                fastReport.addColumn(accountHolderName);
                width = width + accountHolderName.getWidth();

                fastReport.addColumn(nomineeName);
                width = width + nomineeName.getWidth();

                fastReport.addColumn(nomineeNo);
                width = width + nomineeNo.getWidth();

                fastReport.addColumn(relation);
                width = width + relation.getWidth();

                Page page = new Page(1042, width, false);
                fastReport.setMargins(0, 0, 0, 0);
                fastReport.setPageSizeAndOrientation(page);
                fastReport.setTitle("Locker Nomination Register Report");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void viewPdfReport() {
        try {
            if (this.fromDate.equalsIgnoreCase("") || this.fromDate == null) {
                this.setErrorMsg("Please Fill From Date");
                return;
            }
            if (this.toDate.equalsIgnoreCase("") || this.toDate == null) {
                this.setErrorMsg("Please Fiil To Date");
                return;
            }
            if (ymdFormat.parse(toDate).before(ymdFormat.parse(fromDate))) {
                this.setErrorMsg("Please Fill valid To Date");
                return;
            }

            if (reportType.equalsIgnoreCase("0") || reportType == null) {
                this.setErrorMsg("Please Select  Report Type");
                return;
            }

            if (reportType.equalsIgnoreCase("1")) {
                if (nomineeStatus.equalsIgnoreCase("0") || reportType == null) {
                    this.setErrorMsg("Please Select  Nomenee Status");
                    return;
                }
            }

            list = new ArrayList();
            list = remote.getNomineeDetails(branchcode, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)), this.reportType, this.nomineeStatus);
            if (!list.isEmpty()) {
                List bnkList;
                if (branchcode.equalsIgnoreCase("0A")) {
                    bnkList = common.getBranchNameandAddress("90");
                } else {
                    bnkList = common.getBranchNameandAddress(getBranchcode());
                }
                String bankName = "", bankAdd = "";
                if (!bnkList.isEmpty()) {
                    bankName = bnkList.get(0).toString();
                    bankAdd = bnkList.get(1).toString();
                }
                Map fillParams = new HashMap();
                String report = "Nomination Register Report";
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAdd);
                fillParams.put("pPrintedDate", this.getTodayDate());
                fillParams.put("pReportDate", "For the period " + fromDate + " to " + toDate + " ");
                if (reportType.equalsIgnoreCase("1")) {
                    if (nomineeStatus.equalsIgnoreCase("Y")) {
                        fillParams.put("pNomStatus", "Nomine Status : Yes");
                    } else {
                        fillParams.put("pNomStatus", "Nomine Status : No");
                    }
                }

                if (reportType.equalsIgnoreCase("1")) {
                    new ReportBean().openPdf("Nomination Register Report", "Nomination Register Report", new JRBeanCollectionDataSource(list), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Nomination Register Report");
                } else if (reportType.equalsIgnoreCase("2")) {

                    new ReportBean().openPdf("Locker Nomination Register Report", "Locker Nomination Register Report", new JRBeanCollectionDataSource(list), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Locker Nomination Register Report");
                }
            } else {
                this.setErrorMsg("Data Doesn't Exist");
            }

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void refresh() {
        this.branchcode = "0";
        this.fromDate = "";
        this.toDate = "";
        this.reportType = "0";
        this.setErrorMsg("");
        this.nomDisplay = "none";

    }

    public String exitAction() {
        return "case1";
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public List<SelectItem> getBranchcodeList() {
        return branchcodeList;
    }

    public void setBranchcodeList(List<SelectItem> branchcodeList) {
        this.branchcodeList = branchcodeList;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
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

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public SimpleDateFormat getYmdFormat() {
        return ymdFormat;
    }

    public void setYmdFormat(SimpleDateFormat ymdFormat) {
        this.ymdFormat = ymdFormat;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public String getNomineeStatus() {
        return nomineeStatus;
    }

    public void setNomineeStatus(String nomineeStatus) {
        this.nomineeStatus = nomineeStatus;
    }

    public List<SelectItem> getNomineeStatusList() {
        return nomineeStatusList;
    }

    public void setNomineeStatusList(List<SelectItem> nomineeStatusList) {
        this.nomineeStatusList = nomineeStatusList;
    }

    public String getNomDisplay() {
        return nomDisplay;
    }

    public void setNomDisplay(String nomDisplay) {
        this.nomDisplay = nomDisplay;
    }
}
