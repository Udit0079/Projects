package com.cbs.web.controller.report.audit;

import com.cbs.dto.report.AverageVoucherReportDailyPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.AuditReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class AverageVoucherDailyReport extends BaseBean {

    private String message;
    private Date fromDate = new Date();
    private Date toDate = new Date();

    public AverageVoucherDailyReport() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String printAction() {
        String bankName = "";
        String branchAddress = "";
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            AuditReportFacadeRemote beanRemote = (AuditReportFacadeRemote) ServiceLocator.getInstance().lookup("AuditReportFacade");
            List<AverageVoucherReportDailyPojo> averageVoucherDailyReport = beanRemote.getAverageVoucherDailyReport(ymdFormat.format(fromDate), ymdFormat.format(toDate), getOrgnBrCode());
            if (averageVoucherDailyReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!averageVoucherDailyReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "Average Voucher Daily Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportDate", dmyFormat.format(fromDate) + " to " + dmyFormat.format(toDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pDateDifference", (int) CbsUtil.dayDiff(fromDate, toDate) + 1);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                new ReportBean().jasperReport("Average_Voucher_Report_Daily", "text/html", new JRBeanCollectionDataSource(averageVoucherDailyReport), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void btnPdfAction() {
        String bankName = "";
        String branchAddress = "";
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            AuditReportFacadeRemote beanRemote = (AuditReportFacadeRemote) ServiceLocator.getInstance().lookup("AuditReportFacade");
            List<AverageVoucherReportDailyPojo> averageVoucherDailyReport = beanRemote.getAverageVoucherDailyReport(ymdFormat.format(fromDate), ymdFormat.format(toDate), getOrgnBrCode());
            if (averageVoucherDailyReport == null) {
                setMessage("System error occurred");
                return;
            }
            if (!averageVoucherDailyReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "Average Voucher Daily Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportDate", dmyFormat.format(fromDate) + " to " + dmyFormat.format(toDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pDateDifference", (int) CbsUtil.dayDiff(fromDate, toDate) + 1);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                new ReportBean().openPdf("Average Voucher Daily Report_" + ymdFormat.format(fromDate) + " to " + ymdFormat.format(toDate), "Average_Voucher_Report_Daily", new JRBeanCollectionDataSource(averageVoucherDailyReport), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
            } else {
                setMessage("No data to print !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return;
    }

    public String exitAction() {
        return "case1";
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
