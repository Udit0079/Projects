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
import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.CashDepositAggregateAnyOneDayPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.SortedByDepAmt;
import com.cbs.pojo.SortedByTxnDt;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Athar Reza
 */
public class CashDepositAggregateAnyOneDay extends BaseBean {

    private String errorMsg;
    private String reportType;
    private String accType;
    private List<SelectItem> acctTypeList;
    private String option;
    private List<SelectItem> optionList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private Date frmDt;
    private Date toDt;
    private double amt;
    private Date date = new Date();
    private List<SelectItem> branchOptionList;
    private String branchOption;
    private boolean disableRepType;
    private OtherReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private DDSReportFacadeRemote ddsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<CashDepositAggregateAnyOneDayPojo> reportList = new ArrayList<CashDepositAggregateAnyOneDayPojo>();

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }

    public Date getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(Date frmDt) {
        this.frmDt = frmDt;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
    }

    public String getBranchOption() {
        return branchOption;
    }

    public void setBranchOption(String branchOption) {
        this.branchOption = branchOption;
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

    public boolean isDisableRepType() {
        return disableRepType;
    }

    public void setDisableRepType(boolean disableRepType) {
        this.disableRepType = disableRepType;
    }

    public CashDepositAggregateAnyOneDay() {
        try {

            frmDt = date;
            toDt = date;

            local = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            acNatureList = new ArrayList<SelectItem>();
            acctTypeList = new ArrayList<SelectItem>();

            optionList = new ArrayList<SelectItem>();
            optionList.add(new SelectItem("0", "--Select--"));
            optionList.add(new SelectItem("Pan No", "Pan No"));
            optionList.add(new SelectItem("Form 60", "Form 60"));
            optionList.add(new SelectItem("Both", "Both"));
            // initData();
            classificationAction();


//            branchOptionList = new ArrayList<SelectItem>();
//            List allBranchCodeList = common.getAlphacodeAllAndBranch(this.getOrgnBrCode());
//            if (!allBranchCodeList.isEmpty()) {
//                for (int i = 0; i < allBranchCodeList.size(); i++) {
//                    Vector vec = (Vector) allBranchCodeList.get(i);
//                    branchOptionList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString() : vec.get(1).toString(), vec.get(0).toString()));
//                }
//            }

            branchOptionList = new ArrayList<SelectItem>();
            List list = RemoteCode.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchOptionList.add(new SelectItem(vtr.get(0).toString().length() < 2 ? "0" + vtr.get(0).toString() : vtr.get(0).toString(), vtr.get(1).toString()));
            }


        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void classificationAction() {
        this.setErrorMsg("");
        acNatureList = new ArrayList<SelectItem>();
        try {
            acNatureList.add(new SelectItem("--Select--"));
            List list = ddsRemote.getAccountNatureClassification("'C'");
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                acNatureList.add(new SelectItem(element.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void getAcTypeForNature() {
        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.TD_AC)) {
            this.setDisableRepType(true);
        } else {
            this.setDisableRepType(false);
        }
        acctTypeList = new ArrayList<SelectItem>();
        try {
            acctTypeList.add(new SelectItem("--Select--"));
            List list = ddsRemote.getAccountCodeByClassificationAndNature("'C'", this.acNature);
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                acctTypeList.add(new SelectItem(element.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

//    private void initData() {
//        acctTypeList.clear();
//        try {
//            List result = local.getAcctTypeList();
//            Vector vtr = null;
//            if (!result.isEmpty()) {
//                for (int i = 0; i < result.size(); i++) {
//                    vtr = (Vector) result.get(i);
//                    acctTypeList.add(new SelectItem(vtr.get(0), vtr.get(0).toString()));
//                }
//            }
//        } catch (Exception e) {
//            errorMsg = new ApplicationException(e).getMessage();
//        }
//    }
    public boolean validate() {
        if (frmDt == null) {
            errorMsg = "Please enter from date";
            return false;
        }
        if (toDt == null) {
            errorMsg = "Please enter to date";
            return false;
        }
        if (amt == 0) {
            errorMsg = "Please enter amount";
            return false;
        }
        if (frmDt.after(toDt)) {
            errorMsg = "Please from date should be less then to date";
            return false;
        }
        return true;
    }

    public void viewReport() {
        String report = "Cash Deposit Any One Day";
        setErrorMsg("");
        String branchName = "";
        String address = "";
        String lable10 = "", lable12 = "", lable13 = "";
        try {
            if (validate()) {
                if (reportType.equalsIgnoreCase("")) {
                    reportType = "0";
                }
                List brnAddrList = new ArrayList();
                brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
                if (!brnAddrList.isEmpty()) {
                    branchName = (String) brnAddrList.get(0);
                    address = (String) brnAddrList.get(1);
                }
                if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    lable10 = "Payment Mode Cash/Cheque/Transfer/Re-investment";
                    lable12 = "Date of Maturity";
                    lable13 = "Rate of Interest";
                    report = "Cash Deposit Any One Day(Time Deposit Exceeding)";
                }
                Map fillParams = new HashMap();
                fillParams.put("pBnkName", branchName);
                fillParams.put("pBnkAddr", address);
                fillParams.put("pReprtName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", dmy.format(frmDt) + " To " + dmy.format(toDt));
                if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    fillParams.put("pLable10", lable10);
                    fillParams.put("pLable12", lable12);
                    fillParams.put("pLable13", lable13);
                }

                reportList = local.getCashDepositAnyOneDay(Integer.parseInt(reportType), accType, amt, ymd.format(frmDt), ymd.format(toDt), this.getBranchOption(), option);
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist!!!");
                }

                if (!(acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByTxnDt());
                    cnObj.addComparator(new SortedByDepAmt());
                    Collections.sort(reportList, cnObj);
                }

                new ReportBean().jasperReport("CashDepositAnyOneDay", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Cash Deposit Any One Day Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void printAction() {
        this.setErrorMsg("");
        try {
            if (reportType.equalsIgnoreCase("")) {
                reportType = "0";
            }
            if (validate()) {
                reportList = local.getCashDepositAnyOneDay(Integer.parseInt(reportType), accType, amt, ymd.format(frmDt), ymd.format(toDt), this.getBranchOption(), option);
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist!!!");
                }
                if (!(acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByTxnDt());
                    cnObj.addComparator(new SortedByDepAmt());
                    Collections.sort(reportList, cnObj);
                }

                FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), fastReportBuilder, "Cash Deposit Any One Day Report");
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getMessage());
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void btnPdfAction() {
        String report = "Cash Deposit Any One Day Report";
        setErrorMsg("");
        String branchName = "";
        String address = "";
        String lable10 = "", lable12 = "", lable13 = "";
        try {
            if (validate()) {
                if (reportType.equalsIgnoreCase("")) {
                    reportType = "0";
                }
                List brnAddrList = new ArrayList();
                brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
                if (!brnAddrList.isEmpty()) {
                    branchName = (String) brnAddrList.get(0);
                    address = (String) brnAddrList.get(1);
                }

                if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    lable10 = "Payment Mode Cash/Cheque/Transfer/Re-investment";
                    lable12 = "Date of Maturity";
                    lable13 = "Rate of Interest";
                    report = "Cash Deposit Any One Day(Time Deposit Exceeding)";
                }

                Map fillParams = new HashMap();
                fillParams.put("pBnkName", branchName);
                fillParams.put("pBnkAddr", address);
                fillParams.put("pReprtName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", dmy.format(frmDt) + " To " + dmy.format(toDt));
                if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    fillParams.put("pLable10", lable10);
                    fillParams.put("pLable12", lable12);
                    fillParams.put("pLable13", lable13);
                    report = "Cash Deposit Any One Day Report";
                }

                reportList = local.getCashDepositAnyOneDay(Integer.parseInt(reportType), accType, amt, ymd.format(frmDt), ymd.format(toDt), this.getBranchOption(), option);
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist!!!");
                }
                if (!(acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByTxnDt());
                    cnObj.addComparator(new SortedByDepAmt());
                    Collections.sort(reportList, cnObj);
                }

                new ReportBean().openPdf("Cash Deposit Any One Day(Time Deposit Exceeding)" + ymd.format(frmDt) + " to " + ymd.format(toDt), "CashDepositAnyOneDaypdf", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            AbstractColumn tranType = null;
            AbstractColumn matDate = null;
            AbstractColumn roi = null;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            AbstractColumn jointName = ReportBean.getColumn("jointName", String.class, "Joint Transaction Account(Depositers Account Holders)", 120);   //1
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name Of the Transacting Party(Depositers Account Holders)", 150); //2
            AbstractColumn acType = ReportBean.getColumn("acType", String.class, "Whether Govt/Non-Govt", 70);  //3
            AbstractColumn acStatus = ReportBean.getColumn("acStatus", String.class, "Status Of the Transacting Party(Depositers Account Holders)", 100);   //4
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "PAN Of the Transacting Party(Depositers Account Holders)", 100);    //5
            AbstractColumn custDob = ReportBean.getColumn("custDob", String.class, "Date of Birth/Date of Incorporation of the Transacting Party(Depositers Account Holders)", 70); //6
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Name of father of the Transacting Party(Depositers Account Holders)", 150);   //7
            AbstractColumn address = ReportBean.getColumn("address", String.class, "Address Of the Transacting Party(Depositers Account Holders)-Flat No./House No./Premises No./Building Name/Floor No./Block/Sector/Road/Street/Locality/Colony/City/District/State/UT/Pin Code", 500);   //8
            AbstractColumn amount = ReportBean.getColumn("amount", Double.class, "Transaction Amount(to the nearest rupee)(Aggregate Deposit Amount for a day)", 80);   //9 
            if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                tranType = ReportBean.getColumn("tranType", String.class, "Payment Mode Cash/Cheque/Transfer/Re-investment", 90);
            }
            AbstractColumn txnDate = ReportBean.getColumn("txnDate", String.class, "Date Of Deposit", 70);  //10
            if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                matDate = ReportBean.getColumn("matDate", String.class, "Date of Maturity", 90);
                roi = ReportBean.getColumn("roi", String.class, "Rate of Interest", 90);
            }
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Deposit Account No.", 90);    //11

            //AbstractColumn txnDate = ReportBean.getColumn("txnDate", String.class, "Date Of Txn", 60);
            //AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account No", 100);
            //AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name Of Depositor", 300);
            //AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father/Husband Name", 300);
            //AbstractColumn address = ReportBean.getColumn("address", String.class, "Address", 500);
            //AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "Pan/Form 60", 100);
            //AbstractColumn amount = ReportBean.getColumn("amount", Double.class, "Amount", 100);

            fastReport.addColumn(jointName);
            width = width + jointName.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(acType);
            width = width + acType.getWidth();

            fastReport.addColumn(acStatus);
            width = width + acStatus.getWidth();

            fastReport.addColumn(panNo);
            width = width + panNo.getWidth();

            fastReport.addColumn(custDob);
            width = width + custDob.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(address);
            width = width + address.getWidth();

            fastReport.addColumn(amount);
            amount.setStyle(style);
            width = width + amount.getWidth();
            if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                fastReport.addColumn(tranType);
                width = width + tranType.getWidth();
            }

            fastReport.addColumn(txnDate);
            width = width + txnDate.getWidth();

            if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                fastReport.addColumn(matDate);
                width = width + matDate.getWidth();

                fastReport.addColumn(roi);
                roi.setStyle(style);
                width = width + roi.getWidth();
            }

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            if (acNature.equalsIgnoreCase(CbsConstant.TD_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                fastReport.setTitle("Cash Deposit Any One Day Report(Time Deposit Exceeding)");
            } else {
                fastReport.setTitle("Cash Deposit Any One Day Report");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void refresh() {
        this.setErrorMsg("");
        frmDt = date;
        toDt = date;
        reportType = "";
        errorMsg = "";
        option = "";
        this.setAmt(0d);
    }
}
