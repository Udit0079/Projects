/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
import com.cbs.pojo.FreshRenewalEnhashmentPojo;
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
public class FreshRenewalEnhasmentOdLimit extends BaseBean {

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
    private List<SelectItem> branchCodeList;
    private String reportOption;
    private List<SelectItem> reportOptionList;
    private String acctNature;
    private List<SelectItem> acctNatureList;
    private String actType;
    private List<SelectItem> actTypeList;
    private boolean disable;
    private DDSReportFacadeRemote ddsRemote;
    private String todayDate;
    List<FreshRenewalEnhashmentPojo> resultList = new ArrayList<FreshRenewalEnhashmentPojo>();

    public String getAcctNature() {
        return acctNature;
    }

    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }

    public List<SelectItem> getAcctNatureList() {
        return acctNatureList;
    }

    public void setAcctNatureList(List<SelectItem> acctNatureList) {
        this.acctNatureList = acctNatureList;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public List<SelectItem> getActTypeList() {
        return actTypeList;
    }

    public void setActTypeList(List<SelectItem> actTypeList) {
        this.actTypeList = actTypeList;
    }

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

    public String getReportOption() {
        return reportOption;
    }

    public void setReportOption(String reportOption) {
        this.reportOption = reportOption;
    }

    public List<SelectItem> getReportOptionList() {
        return reportOptionList;
    }

    public void setReportOptionList(List<SelectItem> reportOptionList) {
        this.reportOptionList = reportOptionList;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public FreshRenewalEnhasmentOdLimit() {
        try {
            setFromDate(dmy.format(date));
            setToDate(dmy.format(date));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanRemote = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            branchCodeList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getBranchCodeList(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchCodeList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString() : vec.get(0).toString(), vec.get(1).toString()));
                }
            }
            reportOptionList = new ArrayList<SelectItem>();
            reportOptionList.add(new SelectItem("0", "--Select--"));
            reportOptionList.add(new SelectItem("F", "Fresh Limit"));
            reportOptionList.add(new SelectItem("R", "Renewed Limit"));
            reportOptionList.add(new SelectItem("E", "Enhanced Limit"));
//            reportOptionList.add(new SelectItem("Ec", "Exceed Limit"));
            reportOptionList.add(new SelectItem("Ex", "To be Expiry"));
            acctNatureList = new ArrayList<>();
            acctNatureList.add(new SelectItem("--Select--"));
            List list = ddsRemote.getAccountNatureClassification("'D','B'");
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                acctNatureList.add(new SelectItem(element.get(0).toString()));
            }

            this.disable = true;
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void optionAction() {
        setMessage("");
        if (reportOption.equalsIgnoreCase("Ex")) {
            this.disable = false;
        } else {
            this.disable = true;
        }
    }

    public void getAcTypeForNature() {
        setMessage("");
        actTypeList = new ArrayList<SelectItem>();
        try {
            actTypeList.add(new SelectItem("All"));
            List list = ddsRemote.getAccountCodeByClassificationAndNature("'D','B'", this.acctNature);
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                actTypeList.add(new SelectItem(element.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.branchCode == null || this.branchCode.equals("")) {
                this.setMessage("Please Select Branch !");
                return;
            }

            if (reportOption == null || reportOption.equalsIgnoreCase("0")) {
                this.setMessage("Please Select Report Option !");
                return;
            }

            if (reportOption.equalsIgnoreCase("Ex")) {
                if (acctNature.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please Select Account Nature !");
                    return;
                }
            }

            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
            }
            resultList = loanRemote.getFreshRenewalEnhshmentData(branchCode, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), reportOption, acctNature, actType);
            String reportName = "", report = "Fresh/Renewed/Enhanced Limit";
            if (!resultList.isEmpty()) {
                if (reportOption.equalsIgnoreCase("F")) {
                    reportName = "Fresh Limit Detail";
                } else if (reportOption.equalsIgnoreCase("R")) {
                    reportName = "Renewed Limit Detail";
                } else if (reportOption.equalsIgnoreCase("E")) {
                    reportName = "Enhanced/ Reduced Limit Detail";
                } else if (reportOption.equalsIgnoreCase("Ex")) {
                    reportName = "Expiry Limit Detail";
                }
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();
                fillParams.put("pReportName", reportName);
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pReportDt", fromDate + " to " + toDate);
                fillParams.put("pPrintedBy", getUserName());
                new ReportBean().jasperReport("FreshRewalEnhancedLimit", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", report);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void printLetter() {
        this.setMessage(message);
        try {
            resultList = loanRemote.getFreshRenewalEnhshmentLetter(branchCode, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), reportOption, acctNature, actType);
            String reportName = "";
            String Report ="";
            
            if (!resultList.isEmpty()) {
                if (reportOption.equalsIgnoreCase("F")) {
                    reportName = "Fresh Limit Letter";
                } else if (reportOption.equalsIgnoreCase("R")) {
                    reportName = "Renewed Limit Letter";
                      Report = "Od_Renew_Letter";
                } else if (reportOption.equalsIgnoreCase("E")) {
                    reportName = "Enhanced Reduced Letter";
                     Report = "Enhanced_Reduced_Letter";
                } else if (reportOption.equalsIgnoreCase("Ex")) {
                    reportName = "Expiry Letter";
                    Report =  "Expiry_Letter";
                }
//                else if(reportOption.equalsIgnoreCase("Ec")){
//                    reportName = "Exceed Limit Letter";
//                    Report  = "Exceed_Limit_Letter";
//                }
                Map fillParams = new HashMap();
                fillParams.put("pReportDt", getTodayDate());
                new ReportBean().openPdf(reportName, Report, new JRBeanCollectionDataSource(resultList), fillParams);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", reportName);
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
            if (reportOption == null || reportOption.equalsIgnoreCase("0")) {
                this.setMessage("Please Select Report Option !");
                return;
            }

            if (reportOption.equalsIgnoreCase("Ex")) {
                if (acctNature.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please Select Account Nature !");
                    return;
                }
            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
                return;
            }
            resultList = loanRemote.getFreshRenewalEnhshmentData(branchCode, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), reportOption, acctNature, actType);
            String reportName = "", report = "Fresh/Renewed/Enhanced Limit";
            if (!resultList.isEmpty()) {
                if (reportOption.equalsIgnoreCase("F")) {
                    reportName = "Fresh Limit Detail";
                } else if (reportOption.equalsIgnoreCase("R")) {
                    reportName = "Renewed Limit Detail";
                } else if (reportOption.equalsIgnoreCase("E")) {
                    reportName = "Enhanced Reduced Limit Detail";
                } else if (reportOption.equalsIgnoreCase("Ex")) {
                    reportName = "Expiry Limit Detail";
                }
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();
                fillParams.put("pReportName", reportName);
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pReportDt", fromDate + " to " + toDate);
                fillParams.put("pPrintedBy", getUserName());
                new ReportBean().openPdf(reportName, "FreshRewalEnhancedLimit", new JRBeanCollectionDataSource(resultList), fillParams);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", report);
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
        this.reportOption = "0";
        this.acctNature = "--Select--";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
