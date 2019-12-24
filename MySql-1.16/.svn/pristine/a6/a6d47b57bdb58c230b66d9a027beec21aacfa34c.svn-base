package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareCertPojo;
import com.cbs.dto.report.SharePaymentPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class SharePaymentAndShareCertificateReport extends BaseBean {

    private String message;
    private Date date = new Date();
    ShareReportFacadeRemote beanRemote;
    CommonReportMethodsRemote commonRemote;
    private String reportType, closingDate;
    private Date calFromDate;
    private Date caltoDate;
    private boolean toDateDisable;
    private List<SelectItem> modeList;
    private List<SelectItem> typeList;

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public boolean isToDateDisable() {
        return toDateDisable;
    }

    public void setToDateDisable(boolean toDateDisable) {
        this.toDateDisable = toDateDisable;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public SharePaymentAndShareCertificateReport() {
        try {
            beanRemote = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("0", "Share Payment Report"));
            typeList.add(new SelectItem("1", "Share Certificate Report"));
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("0", "Between"));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        try {
            if (calFromDate == null) {
                setMessage("Please fill from Date");
                return null;
            }
            if (caltoDate == null) {
                setMessage("Please fill To Date");
                return null;
            }
            if (reportType.equalsIgnoreCase("1")) {
                List<ShareCertPojo> shareCertificateReport = beanRemote.shareCertificateReport(ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
                if (!shareCertificateReport.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String repName = "CERTIFICATE ISSUE REPORT";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", sdf.format(calFromDate) + " to " + sdf.format(caltoDate));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", repName);
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().jasperReport("CertificateIssueReport", "text/html", new JRBeanCollectionDataSource(shareCertificateReport), fillParams, repName);
                    return "report";
                } else {
                    setMessage("No data to print");
                }
            } else if (reportType.equalsIgnoreCase("0")) {
                List<SharePaymentPojo> sharePaymentReport = beanRemote.sharePaymentReport(ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
                if (!sharePaymentReport.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String repName = "SHARE PAYMENT REPORT";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", sdf.format(calFromDate) + " to " + sdf.format(caltoDate));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", repName);
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().jasperReport("SharePaymentReport", "text/html", new JRBeanCollectionDataSource(sharePaymentReport), fillParams, repName);
                    return "report";
                } else {
                    setMessage("No data to print");
                }
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        setMessage("");
        try {
            if (calFromDate == null) {
                setMessage("Please fill from Date");
                return null;
            }
            if (caltoDate == null) {
                setMessage("Please fill To Date");
                return null;
            }
            if (reportType.equalsIgnoreCase("1")) {
                List<ShareCertPojo> shareCertificateReport = beanRemote.shareCertificateReport(ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
                if (!shareCertificateReport.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String repName = "CERTIFICATE ISSUE REPORT";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", sdf.format(calFromDate) + " to " + sdf.format(caltoDate));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", repName);
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().openPdf("CERTIFICATE ISSUE REPORT", "CertificateIssueReport", new JRBeanCollectionDataSource(shareCertificateReport), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", repName);
                } else {
                    setMessage("No data to print");
                }
            } else if (reportType.equalsIgnoreCase("0")) {
                List<SharePaymentPojo> sharePaymentReport = beanRemote.sharePaymentReport(ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
                if (!sharePaymentReport.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String repName = "SHARE PAYMENT REPORT";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", sdf.format(calFromDate) + " to " + sdf.format(caltoDate));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", repName);
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().openPdf("SHARE PAYMENT REPORT", "SharePaymentReport", new JRBeanCollectionDataSource(sharePaymentReport), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", repName);
                } else {
                    setMessage("No data to print");
                }
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void refreshAction() {
        try {
            setMessage("");
            calFromDate = date;
            caltoDate = date;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
