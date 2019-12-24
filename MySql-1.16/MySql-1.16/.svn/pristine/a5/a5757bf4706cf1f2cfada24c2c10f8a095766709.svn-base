/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LimitRenewalNoticePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
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
public class LimitRenewalNotice extends BaseBean {

    private String message;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String accountType;
    private List<SelectItem> accountTypeList;
    private String fromDate;
    private String toDate;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private LoanReportFacadeRemote local;
    private DDSReportFacadeRemote ddsRemote;
    private List<LimitRenewalNoticePojo> resultList;
    private String orgnbrcode;
    private OtherLoanReportFacadeRemote otherLoan;

    public LimitRenewalNotice() {
        try {

            setFromDate(dmy.format(new Date()));
            setToDate(dmy.format(new Date()));
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            otherLoan = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherLoanReportFacade");
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("S", "--Select--"));
            acNatureList.add(new SelectItem("CA", "CA"));
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {

            accountTypeList = new ArrayList<SelectItem>();
            accountTypeList.add(new SelectItem("ALL", "ALL"));
            List actCodeList = ddsRemote.getAccountCodeByClassificationAndNature("'B','D'", this.acNature);
            if (!actCodeList.isEmpty()) {
                for (int i = 0; i < actCodeList.size(); i++) {
                    Vector vec = (Vector) actCodeList.get(i);
                    accountTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
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

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.acNature == null || this.acNature.equals("")) {
                this.setMessage("Please Select nature !");
                return;
            }
            if (this.accountType == null || this.accountType.equals("")) {
                this.setMessage("Please Select accountType !");
                return;
            }

            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
            }

            resultList = otherLoan.getlimitRenewalNotice(acNature, accountType, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), getOrgnBrCode());

            if (!resultList.isEmpty()) {
                Map fillParams = new HashMap();
                new ReportBean().jasperReport("LimitRenewalNotice", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "LimitRenewalNotice");
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", "Limit Renewal Notice");
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
            if (this.acNature == null || this.acNature.equals("")) {
                this.setMessage("Please Select nature !");
                return;
            }
            if (this.accountType == null || this.accountType.equals("")) {
                this.setMessage("Please Select accountType !");
                return;
            }

            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
            }

            resultList = otherLoan.getlimitRenewalNotice(acNature, accountType, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), getOrgnBrCode());
            if (!resultList.isEmpty()) {
                Map fillParams = new HashMap();
                new ReportBean().openPdf("Limit Renewal Notice", "LimitRenewalNotice", new JRBeanCollectionDataSource(resultList), fillParams);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", "Limit Renewal Notice");
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setAccountType("ALL");
        this.setAcNature("S");
        this.setFromDate(getTodayDate());
        this.setToDate(getTodayDate());

    }

    public String btnExitAction() {
        return "case1";
    }

    public void getacNature() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            List acNtureList = common.getAllAcNature();
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
