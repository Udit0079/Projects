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
import com.cbs.dto.report.CreditBalancePojo;
import com.cbs.dto.report.TdsOnCashWithdrawalPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class TdsOnCashWithdrawal extends BaseBean {

    private String branchcode;
    private List<SelectItem> branchcodeList;
    private String fromDate;
    private String toDate;
    private String financialfromDate;
    private String financialtoDate;
    SimpleDateFormat ymdFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String errorMsg;
    private CommonReportMethodsRemote common;
    MiscReportFacadeRemote beanRemote;
    List<TdsOnCashWithdrawalPojo> resultList;
    List<CreditBalancePojo> dataList;
    private PayrollTransactionsFacadeRemote payrollRemote;
    private List<SelectItem> reporttype;
    private List<SelectItem> reportoption;
    private String reptype;
    private String repopt;
    private String reportOptdisplay;
    private String displayDownloadBtn;

    public TdsOnCashWithdrawal() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchcodeList = new ArrayList();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchcodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            financialYear();
            this.toDate = "31/08/2019";
            this.financialfromDate = "01/09/2019";

            reporttype = new ArrayList();
            reporttype.add(new SelectItem("0", "--Select--"));
            reporttype.add(new SelectItem("1", "TDS On CashWithdrawal"));
            reporttype.add(new SelectItem("2", "Credit Balance"));

            reportoption = new ArrayList();
            reportoption.add(new SelectItem("0", "--Select--"));
            reportoption.add(new SelectItem("1", "Detail"));
            reportoption.add(new SelectItem("2", "Summary"));

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void financialYear() {
        try {
            List list = payrollRemote.getFinYears("90");
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);
                fromDate = ele[0].toString().trim();
                financialtoDate = ele[1].toString().trim();
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void onblurreptype() {
        if (reptype.equalsIgnoreCase("1")) {
            this.reportOptdisplay = "none";
            this.displayDownloadBtn = "none";
        } else {
            this.reportOptdisplay = "";
            this.displayDownloadBtn = "";
        }
    }

    public void excelDownload() {
        try {
            if (this.fromDate.equalsIgnoreCase("") || this.fromDate == null) {
                this.setErrorMsg("Please Fill From Date");
                return;
            }

            if (this.toDate.equalsIgnoreCase("") || this.toDate == null) {
                this.setErrorMsg("Please Fill To Date");
                return;
            }

            if (ymdFormat.parse(this.toDate).before(ymdFormat.parse(this.fromDate))) {
                this.setErrorMsg("fromDate cant be greater than toDate");
                return;
            }

            if (reptype == null || reptype.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please Select Report Type");
                return;
            }

            if (reptype.equalsIgnoreCase("2") && (repopt == null || repopt.equalsIgnoreCase("0"))) {
                this.setErrorMsg("Please Select Report Option");
                return;
            }

            dataList = new ArrayList();

            dataList = new ArrayList();
            dataList = beanRemote.getCreditBalanceDetails(branchcode, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)), repopt);

            String report = "Credit Deposit Balance Report";
            FastReportBuilder fastReportBuilder = genrateDaynamicReport();
            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dataList), fastReportBuilder, "Credit Deposit Balance Report");

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
              AbstractColumn acno = null;
            AbstractColumn customerid = ReportBean.getColumn("customerid", String.class, "Customer Id", 100);
            if(repopt.equalsIgnoreCase("1")){
             acno = ReportBean.getColumn("acNo", String.class, "Account No", 100);
            }
            AbstractColumn customerName = ReportBean.getColumn("customerName", String.class, "Customer Name", 300);
            AbstractColumn depositBal = ReportBean.getColumn("depositbal", BigDecimal.class, "Deposit Balance", 100);
           

            fastReport.addColumn(customerid);
            customerid.setStyle(style);
            width = width + customerid.getWidth();
             if(repopt.equalsIgnoreCase("1")){
            fastReport.addColumn(acno);
            width = width + acno.getWidth();  
             }
          

            fastReport.addColumn(customerName);
            width = width + customerName.getWidth();

            fastReport.addColumn(depositBal);
            depositBal.setStyle(style);
            width = width + depositBal.getWidth();

            
            Page page = new Page(1042, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Credit Deposit Balance Report");

        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
        return fastReport;
    }

    public void btnHtmlAction() {
        try {
            if (this.fromDate.equalsIgnoreCase("") || this.fromDate == null) {
                this.setErrorMsg("Please Fill From Date");
                return;
            }

            if (this.toDate.equalsIgnoreCase("") || this.toDate == null) {
                this.setErrorMsg("Please Fill To Date");
                return;
            }

            if (ymdFormat.parse(this.toDate).before(ymdFormat.parse(this.fromDate))) {
                this.setErrorMsg("fromDate cant be greater than toDate");
                return;
            }

            if (reptype == null || reptype.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please Select Report Type");
                return;
            }

            if (reptype.equalsIgnoreCase("2") && (repopt == null || repopt.equalsIgnoreCase("0"))) {
                this.setErrorMsg("Please Select Report Option");
                return;
            }

            resultList = new ArrayList();
            if (reptype.equalsIgnoreCase("1")) {
                resultList = beanRemote.getTdsOnWithdrawalDetails(branchcode, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)), ymd.format(ymdFormat.parse(this.financialfromDate)), ymd.format(ymdFormat.parse(this.financialtoDate)));
                if (!resultList.isEmpty()) {
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
                    String report = "TDS On CashWithDrawal Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", "For the period " + fromDate + " to " + toDate + " ");
                    new ReportBean().jasperReport("TDS On CashWithDrawal Report", "text/html",
                            new JRBeanCollectionDataSource(resultList), fillParams, "TDS On CashWithDrawal Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                } else {
                    this.setErrorMsg("Data doesn't exist!!");
                }
            } else if (reptype.equalsIgnoreCase("2")) {

                dataList = new ArrayList();
                dataList = beanRemote.getCreditBalanceDetails(branchcode, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)), repopt);
                if (!dataList.isEmpty()) {
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
                    String report = "Credit Deposit Balance Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", fromDate + " to " + toDate);
                    new ReportBean().jasperReport("Credit Deposit Balance Report", "text/html",
                            new JRBeanCollectionDataSource(dataList), fillParams, "Credit Deposit Balance Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                } else {
                    this.setErrorMsg("Data doesn't exist!!");
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

    public void viewPdfReport() {
        try {

            if (this.fromDate.equalsIgnoreCase("") || this.fromDate == null) {
                this.setErrorMsg("Please Fill From Date");
                return;
            }

            if (this.toDate.equalsIgnoreCase("") || this.toDate == null) {
                this.setErrorMsg("Please Fill To Date");
                return;
            }

            if (ymdFormat.parse(this.toDate).before(ymdFormat.parse(this.fromDate))) {
                this.setErrorMsg("fromDate cant be greater than toDate");
                return;
            }

            if (reptype == null || reptype.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please Select Report Type");
                return;
            }

            if (reptype.equalsIgnoreCase("2") && (repopt == null || repopt.equalsIgnoreCase("0"))) {
                this.setErrorMsg("Please Select Report Option");
                return;
            }

            resultList = new ArrayList();
            if (reptype.equalsIgnoreCase("1")) {
                resultList = beanRemote.getTdsOnWithdrawalDetails(branchcode, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)), ymd.format(ymdFormat.parse(this.financialfromDate)), ymd.format(ymdFormat.parse(this.financialtoDate)));
                if (!resultList.isEmpty()) {
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
                    String report = "TDS On CashWithDrawal Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", "For the period " + fromDate + " to " + toDate + " ");
                    new ReportBean().openPdf("TDS On CashWithDrawal Report", "TDS On CashWithDrawal Report", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "TDS On CashWithDrawal Report");
                } else {
                    this.setErrorMsg("Data doesn't exist!!");
                }
            } else {

                dataList = new ArrayList();
                dataList = beanRemote.getCreditBalanceDetails(branchcode, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)), repopt);
                if (!dataList.isEmpty()) {
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
                    String report = "Credit Deposit Balance Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", fromDate + "to" + toDate);
                    new ReportBean().openPdf("Credit Deposit Balance Report", "Credit Deposit Balance Report", new JRBeanCollectionDataSource(dataList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Credit Deposit Balance Report");
                } else {
                    this.setErrorMsg("Data doesn't exist!!");
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }

    }

    public void refresh() {
        financialYear();
    }

    public String exitAction() {
        return "case1";
    }

    public String getFinancialfromDate() {
        return financialfromDate;
    }

    public void setFinancialfromDate(String financialfromDate) {
        this.financialfromDate = financialfromDate;
    }

    public String getFinancialtoDate() {
        return financialtoDate;
    }

    public void setFinancialtoDate(String financialtoDate) {
        this.financialtoDate = financialtoDate;
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

    public List<SelectItem> getReporttype() {
        return reporttype;
    }

    public void setReporttype(List<SelectItem> reporttype) {
        this.reporttype = reporttype;
    }

    public List<SelectItem> getReportoption() {
        return reportoption;
    }

    public void setReportoption(List<SelectItem> reportoption) {
        this.reportoption = reportoption;
    }

    public String getReptype() {
        return reptype;
    }

    public void setReptype(String reptype) {
        this.reptype = reptype;
    }

    public String getRepopt() {
        return repopt;
    }

    public void setRepopt(String repopt) {
        this.repopt = repopt;
    }

    public String getReportOptdisplay() {
        return reportOptdisplay;
    }

    public void setReportOptdisplay(String reportOptdisplay) {
        this.reportOptdisplay = reportOptdisplay;
    }

    public String getDisplayDownloadBtn() {
        return displayDownloadBtn;
    }

    public void setDisplayDownloadBtn(String displayDownloadBtn) {
        this.displayDownloadBtn = displayDownloadBtn;
    }
    
    
}
