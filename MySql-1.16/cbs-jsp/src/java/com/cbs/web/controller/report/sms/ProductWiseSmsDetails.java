/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.sms;

import com.cbs.dto.sms.ProductWiseSmsDetailsPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
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
public class ProductWiseSmsDetails  extends BaseBean{
 private String message;
    private String branch;
    private String acNature;
    private String accountType;
    private String fromDate;
    private String toDate;
    private List<SelectItem> branchTypeList;
    private List<SelectItem> acNatureList;
    private List<SelectItem> accountTypeList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote commonReport;
    private DDSReportFacadeRemote ddsRemote;
    private SmsManagementFacadeRemote smsremote;
    private List<ProductWiseSmsDetailsPojo> resultList;

    public ProductWiseSmsDetails() {
        try {
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            smsremote = (SmsManagementFacadeRemote) ServiceLocator.getInstance().lookup("SmsManagementFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            List brncode = new ArrayList();
            brncode = commonReport.getBranchCodeList(this.getOrgnBrCode());
            branchTypeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchTypeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            List natureType = commonReport.getAllAcNature();
            if (!natureType.isEmpty()) {
                for (int i = 0; i < natureType.size(); i++) {
                    Vector vec = (Vector) natureType.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

//    public void getacNature() {
//        try {
//            acNatureList = new ArrayList<SelectItem>();
//            acNatureList.add(new SelectItem("0", "--Select--"));
//            List natureType = commonReport.getAllAcNature();
//            if (!natureType.isEmpty()) {
//                for (int i = 0; i < natureType.size(); i++) {
//                    Vector vec = (Vector) natureType.get(i);
//                    acNatureList.add(new SelectItem(vec.get(0)));
//                }
//            }
//        } catch (Exception ex) {
//            this.setMessage(ex.getMessage());
//        }
//    }
    public void getAccountDetails() {
        try {
            accountTypeList = new ArrayList<SelectItem>();
            accountTypeList.add(new SelectItem("ALL", "ALL"));
            List acTypeList = commonReport.getAccType(acNature);
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vec = (Vector) acTypeList.get(i);
                    accountTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public List<SelectItem> getBranchTypeList() {
        return branchTypeList;
    }

    public void setBranchTypeList(List<SelectItem> branchTypeList) {
        this.branchTypeList = branchTypeList;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

            resultList = smsremote.getProductWiseSmSDetails(branch, acNature, accountType, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)));
            String pReportName = "", pReportDate = "", pPrintedBy = "", pbankName = "";

            if (!resultList.isEmpty()) {
                pReportName = "Product wise sms details";
                List branchNameandAddress = commonReport.getBranchNameandAddress(getOrgnBrCode());
                pbankName = commonReport.getBankName();
                pReportDate = getTodayDate();
                pPrintedBy = getUserName();

                Map fillParams = new HashMap();

                fillParams.put("pRepName", pReportName);
                fillParams.put("pReportDate", pReportDate);
                fillParams.put("pPrintedBy", pPrintedBy);
                fillParams.put("pBankName", branchNameandAddress.get(0));
                fillParams.put("pBankAddress", branchNameandAddress.get(1));
                
                new ReportBean().jasperReport("Productwisesmsdetails", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, pReportName);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", pReportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    public void btnPdfDownload() {
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

            resultList = smsremote.getProductWiseSmSDetails(branch, acNature, accountType, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)));
            String pReportName = "", pReportDate = "", pPrintedBy = "", pbankName = "";

            if (!resultList.isEmpty()) {
                pReportName = "Product wise sms details";
                List branchNameandAddress = commonReport.getBranchNameandAddress(getOrgnBrCode());
                pbankName = commonReport.getBankName();
                pReportDate = getTodayDate();
                pPrintedBy = getUserName();

                Map fillParams = new HashMap();
                fillParams.put("pRepName", pReportName);
                fillParams.put("pReportDate", pReportDate);
                fillParams.put("pPrintedBy", pPrintedBy);
                fillParams.put("pBankName", branchNameandAddress.get(0));
                fillParams.put("pBankAddress", branchNameandAddress.get(1));
                
                new ReportBean().openPdf("Productwisesmsdetails", "Productwisesmsdetails", new JRBeanCollectionDataSource(resultList), fillParams);
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
        setBranch("");
        setAcNature("0");
        setAccountType("ALL");
        setFromDate(getTodayDate());
        setToDate(getTodayDate());

    }

    public String btnExitAction() {
        return "case1";
    }
}
