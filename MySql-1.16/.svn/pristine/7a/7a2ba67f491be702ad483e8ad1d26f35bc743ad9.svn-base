package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.SundrySuspenseDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SundrySuspenseDetailReport extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String message;
    private Date tillDate;
    private String reportType;
    private String glHead;
    private List<SelectItem> glHeadList;
    private String reportOption;
    private List<SelectItem> reportOptionList;
    private String partialReconcilType;
    private List<SelectItem> partialReconcilTypeList;
    private List<SelectItem> reportTypeList = new ArrayList<SelectItem>();
    private final String glReporfacadejndiName = "GLReportFacade";
    private final String commonReportFacadejndiName = "CommonReportMethods";
    private GLReportFacadeRemote glfacadeBeanRemote = null;
    private CommonReportMethodsRemote commonFacadeBeanRemote = null;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
    private String flag;
    private Date fromDate, toDate;
    private boolean disablePartial;

    public boolean isDisablePartial() {
        return disablePartial;
    }

    public void setDisablePartial(boolean disablePartial) {
        this.disablePartial = disablePartial;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
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

    public GLReportFacadeRemote getGlfacadeBeanRemote() {
        return glfacadeBeanRemote;
    }

    public void setGlfacadeBeanRemote(GLReportFacadeRemote glfacadeBeanRemote) {
        this.glfacadeBeanRemote = glfacadeBeanRemote;
    }

    public CommonReportMethodsRemote getCommonFacadeBeanRemote() {
        return commonFacadeBeanRemote;
    }

    public void setCommonFacadeBeanRemote(CommonReportMethodsRemote commonFacadeBeanRemote) {
        this.commonFacadeBeanRemote = commonFacadeBeanRemote;
    }

    public TdReceiptManagementFacadeRemote getRemoteCode() {
        return RemoteCode;
    }

    public void setRemoteCode(TdReceiptManagementFacadeRemote RemoteCode) {
        this.RemoteCode = RemoteCode;
    }

    public SimpleDateFormat getYmdFormatter() {
        return ymdFormatter;
    }

    public void setYmdFormatter(SimpleDateFormat ymdFormatter) {
        this.ymdFormatter = ymdFormatter;
    }

    public SimpleDateFormat getDmyformatter() {
        return dmyformatter;
    }

    public void setDmyformatter(SimpleDateFormat dmyformatter) {
        this.dmyformatter = dmyformatter;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getGlHead() {
        return glHead;
    }

    public void setGlHead(String glHead) {
        this.glHead = glHead;
    }

    public List<SelectItem> getGlHeadList() {
        return glHeadList;
    }

    public void setGlHeadList(List<SelectItem> glHeadList) {
        this.glHeadList = glHeadList;
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

    public String getPartialReconcilType() {
        return partialReconcilType;
    }

    public void setPartialReconcilType(String partialReconcilType) {
        this.partialReconcilType = partialReconcilType;
    }

    public List<SelectItem> getPartialReconcilTypeList() {
        return partialReconcilTypeList;
    }

    public void setPartialReconcilTypeList(List<SelectItem> partialReconcilTypeList) {
        this.partialReconcilTypeList = partialReconcilTypeList;
    }

    public SundrySuspenseDetailReport() {
        try {
            glfacadeBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(glReporfacadejndiName);
            commonFacadeBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonReportFacadejndiName);
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            this.setMessage("");
            onloadData();
            this.setFromDate(new Date());
            this.setToDate(new Date());
            reportOptionList = new ArrayList<>();
            reportOptionList.add(new SelectItem("S", "--Select--"));
            reportOptionList.add(new SelectItem("U", "Unreconciled"));
            reportOptionList.add(new SelectItem("R", "Reconciled"));

            partialReconcilTypeList = new ArrayList<>();
            partialReconcilTypeList.add(new SelectItem("ALL", "ALL"));
            partialReconcilTypeList.add(new SelectItem("=0", "Full"));
            partialReconcilTypeList.add(new SelectItem("<>0", "Partial"));

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void glHeadDetail() {
        try {
            List glhead = new ArrayList();
            glhead = glfacadeBeanRemote.glHeadList(this.reportType);
            glHeadList = new ArrayList<SelectItem>();
            if (!glhead.isEmpty()) {
                for (int i = 0; i < glhead.size(); i++) {
                    Vector glHeadVec = (Vector) glhead.get(i);
                    glHeadList.add(new SelectItem(glHeadVec.get(0).toString(), glHeadVec.get(1).toString()));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onloadData() {
        reportTypeList.add(new SelectItem("0", "--SELECT--"));
        reportTypeList.add(new SelectItem("11", "SUNDRY"));
        reportTypeList.add(new SelectItem("12", "SUSPENSE"));
    }

    public void onDisable() {
        setMessage("");
        if (reportOption.equalsIgnoreCase("U")) {
            disablePartial = true;

        } else {
            disablePartial = false;
        }
    }

    public String Validtions() {
        try {
            if (reportType.equalsIgnoreCase("0")) {
                return "Report type is not selected!!!";
            }
            if (fromDate == null) {
                return "From date is not set!!!";
            }
            if (toDate == null) {
                return "To date is not set!!!";
            }

            if (reportOption.equalsIgnoreCase("S")) {
                return "Report Option is not selected!!!";
            }

        } catch (Exception e) {
            return "Error occurred during field validations!!!";
        }
        return "true";
    }

    public void printReport() {
        try {
            String valdationResult = Validtions();
            if (!valdationResult.equalsIgnoreCase("true")) {
                setMessage(valdationResult);
                return;
            }
            List<SundrySuspenseDetailPojo> reportSundryList = new ArrayList<SundrySuspenseDetailPojo>();
            setMessage("");
            String report = "";
            if (reportType.equalsIgnoreCase("11")) {
                report = "Sundry Detail Report";
            } else {
                report = "Suspense Detail Report";
            }
            reportSundryList = glfacadeBeanRemote.sundrySuspenseReportDetail(branchCode, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), reportType, glHead, reportOption, partialReconcilType);
            if (reportSundryList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            if (branchCode.equalsIgnoreCase("0A")) {
                branchCode = "90";
            } else {
                branchCode = branchCode;
            }
            List brNameAndAddList = commonFacadeBeanRemote.getBranchNameandAddress(branchCode);
            Map fillParams = new HashMap();
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedDate", dmyformatter.format(fromDate) + " to " + dmyformatter.format(toDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pAcName", ftsPostRemote.getCustName(branchCode + glHead + "01"));
            String jrxmlName = "Sundry_Detail_Report";
            if(partialReconcilType.equalsIgnoreCase("ALL")&& reportOption.equalsIgnoreCase("R")){
                jrxmlName = "Sundry_Detail_Report_grouping";
            }
            new ReportBean().jasperReport(jrxmlName, "text/html", new JRBeanCollectionDataSource(reportSundryList), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void printPDF() {
        try {
            String valdationResult = Validtions();
            if (!valdationResult.equalsIgnoreCase("true")) {
                setMessage(valdationResult);
                return;
            }
            List<SundrySuspenseDetailPojo> reportSundryList = new ArrayList<SundrySuspenseDetailPojo>();
            setMessage("");
            String report = "";
            if (reportType.equalsIgnoreCase("1")) {
                report = "Sundry Detail Report";
            } else {
                report = "Suspense Detail Report";
            }
            reportSundryList = glfacadeBeanRemote.sundrySuspenseReportDetail(branchCode, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), reportType, glHead, reportOption, partialReconcilType);
            if (reportSundryList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            if (branchCode.equalsIgnoreCase("0A")) {
                branchCode = "90";
            } else {
                branchCode = branchCode;
            }
            List brNameAndAddList = commonFacadeBeanRemote.getBranchNameandAddress(branchCode);
            Map fillParams = new HashMap();
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedDate", dmyformatter.format(fromDate) + " to " + dmyformatter.format(toDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pAcName", ftsPostRemote.getCustName(branchCode + glHead + "01"));
            String jrxmlName = "Sundry_Detail_Report";
            if(partialReconcilType.equalsIgnoreCase("ALL")&& reportOption.equalsIgnoreCase("R")){
                jrxmlName = "Sundry_Detail_Report_grouping";
            }
            new ReportBean().openPdf(report, jrxmlName, new JRBeanCollectionDataSource(reportSundryList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            this.setMessage("");
            this.setFromDate(new Date());
            this.setToDate(new Date());
            this.setReportType("0");
            disablePartial = false;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        return "case1";
    }
}
