/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.LoanRenewalSecurityPojo;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class LoanRenewalSecurity extends BaseBean {

    private String errorMsg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String days;
    private String asOnDate;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private DDSReportFacadeRemote ddsRemote;
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private LoanReportFacadeRemote loanRemote = null;
    private List<LoanRenewalSecurityPojo> reportList = new ArrayList<LoanRenewalSecurityPojo>();

    public LoanRenewalSecurity() {
        try {
            asOnDate = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            getAcTypeByAcNature();
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("Select", "--Select--"));
            reportTypeList.add(new SelectItem("R", "Renewal"));
            reportTypeList.add(new SelectItem("S", "Security"));

        } catch (Exception e) {
        }
    }

//    public void getacNature() {
//        try {
//            acNatureList = new ArrayList<SelectItem>();
//            acNatureList.add(new SelectItem("0", "--Select--"));
//            List acNtureList = ddsRemote.getAccountNatureClassification("'D','B'");
//            if (!acNtureList.isEmpty()) {
//                for (int i = 0; i < acNtureList.size(); i++) {
//                    Vector vec = (Vector) acNtureList.get(i);
//                    acNatureList.add(new SelectItem(vec.get(0)));
//                }
//            }
//        } catch (Exception e) {
//            setErrorMsg(e.getMessage());
//        }
//    }
    public void getAcTypeByAcNature() {
        try {
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("S", "--Select--"));
            List actCodeList = ddsRemote.getAcctCodeByNatureFlag("CA", "'D'");
            if (!actCodeList.isEmpty()) {
                for (int i = 0; i < actCodeList.size(); i++) {
                    Vector vec = (Vector) actCodeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewReport() {
        setErrorMsg("");
        String report = "CC / OD Renewal Report";
        try {

            if (acType == null || acType.equalsIgnoreCase("S")) {
                setErrorMsg("Please select a/c type !");
                return;
            }

            if (reportType == null || reportType.equalsIgnoreCase("Select")) {
                setErrorMsg("Please select report type !");
                return;
            }

            if (asOnDate == null || asOnDate.equalsIgnoreCase("")) {
                setErrorMsg("Please fill As on date");
                return;
            }
            if (!Validator.validateDate(asOnDate)) {
                setErrorMsg("Please select Proper As on date ");
                return;
            }

            if (days == null || days.equalsIgnoreCase("")) {
                setErrorMsg("Please fill days !");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher matcher = p.matcher(this.days);
            if (!matcher.matches()) {
                throw new ApplicationException("Days should be in numeric digits.");
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", "");
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportType", reportType);

            reportList = loanRemote.getLoanRenwalData(branchCode, acType, reportType, ymd.format(dmy.parse(asOnDate)), Integer.parseInt(days));

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exit !");
            }

            new ReportBean().jasperReport("CC_ODRenewal", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Loan Renewal Security Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "CC OD Renewal Report");
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setErrorMsg("");
        String report = "CC OD Renewal Report";
        try {

            if (acType == null || acType.equalsIgnoreCase("S")) {
                setErrorMsg("Please select a/c type !");
                return;
            }

            if (reportType == null || reportType.equalsIgnoreCase("Select")) {
                setErrorMsg("Please select report type !");
                return;
            }

            if (asOnDate == null || asOnDate.equalsIgnoreCase("")) {
                setErrorMsg("Please fill As on date");
                return;
            }
            if (!Validator.validateDate(asOnDate)) {
                setErrorMsg("Please select Proper As on date ");
                return;
            }

            if (days == null || days.equalsIgnoreCase("")) {
                setErrorMsg("Please fill days !");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher matcher = p.matcher(this.days);
            if (!matcher.matches()) {
                throw new ApplicationException("Days should be in numeric digits.");
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", "");
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportType", reportType);

            reportList = loanRemote.getLoanRenwalData(branchCode, acType, reportType, ymd.format(dmy.parse(asOnDate)), Integer.parseInt(days));

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exit !");
            }

            new ReportBean().openPdf("CC OD Renewal Report_" + ymd.format(dmy.parse(asOnDate)), "CC_ODRenewal", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "CC OD Renewal Report");
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        setAcType("S");
        setReportType("Select");
        asOnDate = dmy.format(date);
        setDays("");
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

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
