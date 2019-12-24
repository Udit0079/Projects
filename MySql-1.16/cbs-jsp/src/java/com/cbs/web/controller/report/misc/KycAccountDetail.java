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
import com.cbs.dto.report.KycAccountDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.CbsUtil;
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
 * @author Admin
 */
public class KycAccountDetail extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String reportOption;
    private List<SelectItem> reportOptionList;
    private String acnoNature;
    private List<SelectItem> acnoNatureList;
    private String selectAcType;
    private List<SelectItem> selectAcTypeList;
    private String calFromDate;
    private String caltoDate;
    private String display = "";
    private String naturePanalDisplay = "none";
    private boolean caltoDateDisable;
    private String dateButton;
    private Date date = new Date();
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<KycAccountDetailPojo> repList = new ArrayList<KycAccountDetailPojo>();

    public KycAccountDetail() {
        calFromDate = dmy.format(date);
        caltoDate = dmy.format(date);
        branchList = new ArrayList<SelectItem>();
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            List list = common.getBranchCodeList(getOrgnBrCode());
//            for (int i = 0; i < list.size(); i++) {
//                Vector vtr = (Vector) list.get(i);
//                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
//            }
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele7 = (Vector) list.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("Comp", "As On Date Comp"));
            //reportTypeList.add(new SelectItem("Uncomp", "As On Date Uncomp"));
            reportTypeList.add(new SelectItem("Exp", "As On Date Expiry"));
            reportTypeList.add(new SelectItem("From", "From Date - To Date"));

            reportOptionList = new ArrayList<SelectItem>();
            reportOptionList.add(new SelectItem("S", "--Select--"));
            reportOptionList.add(new SelectItem("Id", "Cust Id"));
            reportOptionList.add(new SelectItem("Acno", "Account"));

            this.setDateButton("From Date");

            getacNature();

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void reportTypeProcess() {
        try {
            this.naturePanalDisplay = "none";
            if (reportType.equalsIgnoreCase("Comp") || reportType.equalsIgnoreCase("Uncomp") || reportType.equalsIgnoreCase("Exp")) {
                display = "none";
                this.setDateButton("As On Date");
            }
            if (reportType.equalsIgnoreCase("From")) {
                display = "";
                this.setDateButton("From Date");
            }
            this.reportOption = "S";
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void actionOnReportOption() {
        setMessage("");
        if (reportOption.equalsIgnoreCase("Id")) {
            this.naturePanalDisplay = "none";
        } else {
            this.naturePanalDisplay = "";
        }

    }

    public void getacNature() {
        try {
            acnoNatureList = new ArrayList<SelectItem>();
            acnoNatureList.add(new SelectItem("ALL", "ALL"));
            List acNtureList = common.getAllNatureList();
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acnoNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {
            selectAcTypeList = new ArrayList<SelectItem>();
            selectAcTypeList.add(new SelectItem("ALL", "ALL"));
            List acTypeList = common.getAccType(acnoNature);
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vec = (Vector) acTypeList.get(i);
                    selectAcTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(String caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public boolean isCaltoDateDisable() {
        return caltoDateDisable;
    }

    public void setCaltoDateDisable(boolean caltoDateDisable) {
        this.caltoDateDisable = caltoDateDisable;
    }

    public String getDateButton() {
        return dateButton;
    }

    public void setDateButton(String dateButton) {
        this.dateButton = dateButton;
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

    public String getAcnoNature() {
        return acnoNature;
    }

    public void setAcnoNature(String acnoNature) {
        this.acnoNature = acnoNature;
    }

    public List<SelectItem> getAcnoNatureList() {
        return acnoNatureList;
    }

    public void setAcnoNatureList(List<SelectItem> acnoNatureList) {
        this.acnoNatureList = acnoNatureList;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public List<SelectItem> getSelectAcTypeList() {
        return selectAcTypeList;
    }

    public void setSelectAcTypeList(List<SelectItem> selectAcTypeList) {
        this.selectAcTypeList = selectAcTypeList;
    }

    public String getNaturePanalDisplay() {
        return naturePanalDisplay;
    }

    public void setNaturePanalDisplay(String naturePanalDisplay) {
        this.naturePanalDisplay = naturePanalDisplay;
    }

    public void viewReport() {
        String report = "Kyc Update Account Detail Report";
        try {
            String frDt = "", toDt = "";
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                setMessage("Please select report type !");
                return;
            }

            if (reportOption == null || reportOption.equalsIgnoreCase("S")) {
                setMessage("Please select Report Option !");
                return;
            }

            if (calFromDate == null || calFromDate.equalsIgnoreCase("")) {
                setMessage("Please fill As on date");
                return;
            }
            if (!Validator.validateDate(calFromDate)) {
                setMessage("Please select Proper As on date ");
                return;
            }

            if (dmy.parse(calFromDate).after(getDate())) {
                setMessage("As on date should be less than current date !");
                return;
            }

            frDt = ymd.format(dmy.parse(calFromDate));
            //toDt = ymd.format(dmy.parse(caltoDate));

            if (reportType.equalsIgnoreCase("From")) {
                if (caltoDate == null || caltoDate.equalsIgnoreCase("")) {
                    setMessage("Please fill As on date");
                    return;
                }
                if (!Validator.validateDate(caltoDate)) {
                    setMessage("Please select Proper As on date ");
                    return;
                }

                if (dmy.parse(caltoDate).after(getDate())) {
                    setMessage("As on date should be less than current date !");
                    return;
                }

                frDt = ymd.format(dmy.parse(calFromDate));
                toDt = ymd.format(dmy.parse(caltoDate));
            }
            String repType = "";
            if (reportType.equalsIgnoreCase("Exp")) {
                repType = "KYC Uncompleted (Expiry)";
            } else {
                repType = "KYC Completed (Registered)";
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());

            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportType", repType);

            repList = remoteFacade.getKycData(branch, frDt, toDt, reportType, reportOption, acnoNature, selectAcType);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("KycAcnoUpdateDetail", "text/html", new JRBeanCollectionDataSource(repList), fillParams, "Kyc Update Account Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            refreshForm();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public FastReportBuilder downloadAction() {
        FastReportBuilder fastReport = new FastReportBuilder();
        String toDate = "";
        String report = "Kyc Update Account Detail Report";
        try {
            String frDt = "", toDt = "";
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                setMessage("Please select report type !");
                return null;
            }

            if (reportOption == null || reportOption.equalsIgnoreCase("S")) {
                setMessage("Please select Report Option !");
                return null;
            }

            if (calFromDate == null || calFromDate.equalsIgnoreCase("")) {
                setMessage("Please fill As on date");
                return null;
            }
            if (!Validator.validateDate(calFromDate)) {
                setMessage("Please select Proper As on date ");
                return null;
            }

            if (dmy.parse(calFromDate).after(getDate())) {
                setMessage("As on date should be less than current date !");
                return null;
            }

            frDt = ymd.format(dmy.parse(calFromDate));
            toDt = ymd.format(dmy.parse(caltoDate));

            if (reportType.equalsIgnoreCase("From")) {
                if (caltoDate == null || caltoDate.equalsIgnoreCase("")) {
                    setMessage("Please fill As on date");
                    return null;
                }
                if (!Validator.validateDate(caltoDate)) {
                    setMessage("Please select Proper As on date ");
                    return null;
                }

                if (dmy.parse(caltoDate).after(getDate())) {
                    setMessage("As on date should be less than current date !");
                    return null;
                }

                frDt = ymd.format(dmy.parse(calFromDate));
                toDt = ymd.format(dmy.parse(caltoDate));
            }
            repList = remoteFacade.getKycData(branch, frDt, toDt, reportType, reportOption, acnoNature, selectAcType);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn customerid = ReportBean.getColumn("custId", String.class, "Customer Id", 100);
            AbstractColumn customername = ReportBean.getColumn("custName", String.class, "Customer Name", 200);
            AbstractColumn accountno = ReportBean.getColumn("acNo", String.class, "A/c No", 100);
            AbstractColumn address = ReportBean.getColumn("add", String.class, "Address", 300);
            AbstractColumn dateofbirth = ReportBean.getColumn("dob", String.class, "Date Of Birth", 60);
            AbstractColumn idproof = ReportBean.getColumn("idProof", String.class, "Id Proof", 80);
            AbstractColumn addressproof = ReportBean.getColumn("addressProof", String.class, "Address Proof", 150);
            AbstractColumn mobileno = ReportBean.getColumn("mobileNo", String.class, "Mobile No", 100);
            AbstractColumn panno = ReportBean.getColumn("panNo", String.class, "Pan No", 100);
            AbstractColumn riskcatagory = ReportBean.getColumn("riskCategory", String.class, "Risk Category", 100);
            AbstractColumn kycupdatedate = ReportBean.getColumn("upDationDt", String.class, "KYC Update Date", 60);
            AbstractColumn renewaldate = ReportBean.getColumn("renewalDt", String.class, "Renewal Date", 60);
            AbstractColumn acstatus = ReportBean.getColumn("accstatus", String.class, "Account Status", 150);

            fastReport.addColumn(customerid);
            width = width + customerid.getWidth();

            fastReport.addColumn(customername);
            width = width + customername.getWidth();

            fastReport.addColumn(accountno);
            width = width + accountno.getWidth();

            fastReport.addColumn(address);
            width = width + address.getWidth();

            fastReport.addColumn(dateofbirth);
            width = width + dateofbirth.getWidth();

            fastReport.addColumn(idproof);
            width = width + idproof.getWidth();

            fastReport.addColumn(addressproof);
            width = width + addressproof.getWidth();

            fastReport.addColumn(mobileno);
            width = width + mobileno.getWidth();

            fastReport.addColumn(panno);
            width = width + panno.getWidth();

            fastReport.addColumn(riskcatagory);
            width = width + riskcatagory.getWidth();

            fastReport.addColumn(kycupdatedate);
            width = width + kycupdatedate.getWidth();

            fastReport.addColumn(renewaldate);
            width = width + renewaldate.getWidth();

            fastReport.addColumn(acstatus);
            width = width + acstatus.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Kyc Update Account Detail Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), fastReport, "Kyc Update Account Detail Report");
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public void viewPdfReport() {
        String report = "Kyc Update Account Detail Report";
        try {
            String frDt = "", toDt = "";
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                setMessage("Please select report type !");
                return;
            }

            if (reportOption == null || reportOption.equalsIgnoreCase("S")) {
                setMessage("Please select Report Option !");
                return;
            }

            if (calFromDate == null || calFromDate.equalsIgnoreCase("")) {
                setMessage("Please fill As on date");
                return;
            }
            if (!Validator.validateDate(calFromDate)) {
                setMessage("Please select Proper As on date ");
                return;
            }

            if (dmy.parse(calFromDate).after(getDate())) {
                setMessage("As on date should be less than current date !");
                return;
            }

            frDt = ymd.format(dmy.parse(calFromDate));
            //toDt = ymd.format(dmy.parse(caltoDate));

            if (reportType.equalsIgnoreCase("From")) {
                if (caltoDate == null || caltoDate.equalsIgnoreCase("")) {
                    setMessage("Please fill As on date");
                    return;
                }
                if (!Validator.validateDate(caltoDate)) {
                    setMessage("Please select Proper As on date ");
                    return;
                }

                if (dmy.parse(caltoDate).after(getDate())) {
                    setMessage("As on date should be less than current date !");
                    return;
                }

                frDt = ymd.format(dmy.parse(calFromDate));
                toDt = ymd.format(dmy.parse(caltoDate));
            }
            String repType = "";
            if (reportType.equalsIgnoreCase("Exp")) {
                repType = "KYC Uncompleted (Expiry)";
            } else {
                repType = "KYC Completed (Registered)";
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());

            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportType", repType);

            repList = remoteFacade.getKycData(branch, frDt, toDt, reportType, reportOption, acnoNature, selectAcType);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            this.reportType = "S";
            this.reportOption = "S";

            // new ReportBean().downloadPdf("Kyc Update Account Detail Report", "KycAcnoUpdateDetail", new JRBeanCollectionDataSource(repList), fillParams);
            new ReportBean().openPdf("Kyc Update Account Detail Report", "KycAcnoUpdateDetail", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Kyc Update Account Detail Report");

            refreshForm();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        setMessage("");
        calFromDate = dmy.format(date);
        caltoDate = dmy.format(date);
        this.reportType = "S";
        this.reportOption = "S";
        this.naturePanalDisplay = "none";
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }
}
