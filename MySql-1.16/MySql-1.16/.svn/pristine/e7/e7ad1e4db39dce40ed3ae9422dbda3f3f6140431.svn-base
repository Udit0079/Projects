/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.SuretyLetterPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class SuretyLetter extends BaseBean {

    private String message;
    private String fromDate;
    private String toDate;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private final String jndiHomeNameLoanReportFacade = "OtherLoanReportFacade";
    private OtherLoanReportFacadeRemote loanRemote = null;
    Date date = new Date();
    private String branchCode;
    List<SuretyLetterPojo> resultList = new ArrayList<SuretyLetterPojo>();
    private List<SelectItem> branchCodeList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public SuretyLetter() {
        try {
            setFromDate(dmy.format(date));
            setToDate(dmy.format(date));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanRemote = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            branchCodeList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getBranchCodeList(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchCodeList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString() : vec.get(0).toString(), vec.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.branchCode == null || this.branchCode.equals("")) {
                this.setMessage("Please Select Branch !");
                return;
            }

            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
            }
            resultList = loanRemote.getloanSuretyLetter(ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), branchCode);
            String pReportName = "";
            if (!resultList.isEmpty()) {
                pReportName = "Surety Letter";
                Map fillParams = new HashMap();
                new ReportBean().jasperReport("SuretyLetter", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, pReportName);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", pReportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        this.setMessage("");
        try {

            if (this.branchCode == null || this.branchCode.equals("")) {
                this.setMessage("Please Select Branch !");
                return;
            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
                return;
            }
            resultList = loanRemote.getloanSuretyLetter(ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), branchCode);
            String pReportName = "";
            if (!resultList.isEmpty()) {
                pReportName = "Surety Letter";
                Map fillParams = new HashMap();
                new ReportBean().openPdf("SuretyLetter", "SuretyLetter", new JRBeanCollectionDataSource(resultList), fillParams);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", pReportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    public void btnRefreshAction() {
        this.message = "";
        this.setFromDate(getTodayDate());
        this.setToDate(getTodayDate());
        this.setBranchCode("");
    }

    public String btnExitAction() {
        return "case1";
    }
}
