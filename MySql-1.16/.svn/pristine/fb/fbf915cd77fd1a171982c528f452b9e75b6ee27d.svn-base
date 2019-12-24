/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.loan.CCTurnOverReportPojo;
import com.cbs.dto.loan.StatementOfAlmPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
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
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class CCTurnOverReport extends BaseBean {

    private String brncode;
    private List<SelectItem> branchcodeList;
    private String actype;
    private List<SelectItem> acTypeList;
    private String fromDate;
    private String toDate;
    SimpleDateFormat ymdFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private String errorMsg;
    private DDSReportFacadeRemote ddsRemote;
    private LoanReportFacadeRemote local;
    List<CCTurnOverReportPojo> data;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private List<StatementOfAlmPojo> resultlist;

    public CCTurnOverReport() {
        try {
            fromDate = ymdFormat.format(new Date());
            toDate = ymdFormat.format(new Date());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchcodeList = new ArrayList();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchcodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acTypeList = new ArrayList();
            acTypeList.add(new SelectItem("All", "ALL"));
            List list = ddsRemote.getAccountCodeByClassificationAndNature("'D','B'", "CA");
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                acTypeList.add(new SelectItem(element.get(0).toString()));
            }
            reportTypeList = new ArrayList();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("1", "CC TurnOver Report"));
            reportTypeList.add(new SelectItem("2", "Statement Of Alm Report"));

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void viewPdfReport() {
        try {

            if (this.reportType == null || this.reportType.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please Fill Report Type");
                return;
            }

            if (this.fromDate.equalsIgnoreCase("") || this.fromDate == null) {
                this.setErrorMsg("Please Fill From Date !!");
                return;
            }

            if (this.toDate.equalsIgnoreCase("") || this.toDate == null) {
                this.setErrorMsg("Please Fill To Date !!");
                return;
            }

            if (ymdFormat.parse(toDate).before(ymdFormat.parse(fromDate))) {
                this.setErrorMsg("Please Fill Valid Date");
                return;
            }
            if (this.reportType.equalsIgnoreCase("1")) {
                data = new ArrayList();
                data = local.getccTurnOverDetails(this.brncode, this.actype, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)));
                if (!data.isEmpty()) {
                    List bnkList;
                    if (brncode.equalsIgnoreCase("0A")) {
                        bnkList = common.getBranchNameandAddress("90");
                    } else {
                        bnkList = common.getBranchNameandAddress(getBrncode());
                    }
                    String bankName = "", bankAdd = "";
                    if (!bnkList.isEmpty()) {
                        bankName = bnkList.get(0).toString();
                        bankAdd = bnkList.get(1).toString();
                    }
                    Map fillParams = new HashMap();
                    String report = "CC TurnOver Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", fromDate + " to " + toDate );
                    new ReportBean().openPdf("CC TurnOver Report", "CC TurnOver Report", new JRBeanCollectionDataSource(data), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "CC TurnOver Report");
                } else {
                    this.setErrorMsg("Data doesn't exist !!");
                }
            } else if (reportType.equalsIgnoreCase("2")) {
                resultlist = new ArrayList();
                resultlist = local.getStatementOfAlmDetails(this.brncode, this.actype, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)));

                if (!resultlist.isEmpty()) {
                    List bnkList;
                    if (brncode.equalsIgnoreCase("0A")) {
                        bnkList = common.getBranchNameandAddress("90");
                    } else {
                        bnkList = common.getBranchNameandAddress(getBrncode());
                    }
                    String bankName = "", bankAdd = "";
                    if (!bnkList.isEmpty()) {
                        bankName = bnkList.get(0).toString();
                        bankAdd = bnkList.get(1).toString();
                    }
                    Map fillParams = new HashMap();
                    String report = "Statement of ALM Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", fromDate + " to " + toDate );
                    new ReportBean().openPdf("Statement of ALM Report", "Statement of ALM Report", new JRBeanCollectionDataSource(resultlist), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Statement of ALM Report");
                } else {
                    this.setErrorMsg("Data doesn't exist !!");
                }

            }

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        try {

            if (this.reportType == null || this.reportType.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please Fill Report Type");
                return;
            }

            if (this.fromDate.equalsIgnoreCase("") || this.fromDate == null) {
                this.setErrorMsg("Please Fill From Date !!");
                return;
            }

            if (this.toDate.equalsIgnoreCase("") || this.toDate == null) {
                this.setErrorMsg("Please Fill To Date !!");
                return;
            }

            if (ymdFormat.parse(toDate).before(ymdFormat.parse(fromDate))) {
                this.setErrorMsg("Please Fill Valid Date");
                return;
            }
            if (this.reportType.equalsIgnoreCase("1")) {
                data = new ArrayList();
                data = local.getccTurnOverDetails(this.brncode, this.actype, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)));
                if (!data.isEmpty()) {
                    List bnkList;
                    if (brncode.equalsIgnoreCase("0A")) {
                        bnkList = common.getBranchNameandAddress("90");
                    } else {
                        bnkList = common.getBranchNameandAddress(getBrncode());
                    }
                    String bankName = "", bankAdd = "";
                    if (!bnkList.isEmpty()) {
                        bankName = bnkList.get(0).toString();
                        bankAdd = bnkList.get(1).toString();
                    }
                    Map fillParams = new HashMap();
                    String report = "CC TurnOver Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", fromDate + " to " + toDate );
                    new ReportBean().jasperReport("CC TurnOver Report", "text/html",
                    new JRBeanCollectionDataSource(data), fillParams, "CC TurnOver Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    this.setErrorMsg("Data doesn't exist !!");
                }
            } else if (reportType.equalsIgnoreCase("2")) {
                resultlist = new ArrayList();
                resultlist = local.getStatementOfAlmDetails(this.brncode, this.actype, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)));

                if (!resultlist.isEmpty()) {
                    List bnkList;
                    if (brncode.equalsIgnoreCase("0A")) {
                        bnkList = common.getBranchNameandAddress("90");
                    } else {
                        bnkList = common.getBranchNameandAddress(getBrncode());
                    }
                    String bankName = "", bankAdd = "";
                    if (!bnkList.isEmpty()) {
                        bankName = bnkList.get(0).toString();
                        bankAdd = bnkList.get(1).toString();
                    }
                    
                    Map fillParams = new HashMap();
                    String report = "Statement of ALM Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate",fromDate + " to " + toDate);
                    new ReportBean().jasperReport("Statement of ALM Report", "text/html",
                    new JRBeanCollectionDataSource(resultlist), fillParams, "Statement of ALM Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    this.setErrorMsg("Data doesn't exist !!");
                }

            }

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
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
        this.fromDate = ymdFormat.format(new Date());
        this.toDate = ymdFormat.format(new Date());
        this.setReportType("0");
        this.setErrorMsg("");
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

    public String getBrncode() {
        return brncode;
    }

    public void setBrncode(String brncode) {
        this.brncode = brncode;
    }

    public List<SelectItem> getBranchcodeList() {
        return branchcodeList;
    }

    public void setBranchcodeList(List<SelectItem> branchcodeList) {
        this.branchcodeList = branchcodeList;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
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
