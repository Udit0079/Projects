package com.cbs.web.controller.report.audit;

import com.cbs.dto.report.AverageVoucherReportDailyPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.AuditReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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

public final class AverageVoucherQuarterlyReport extends BaseBean {
    
    private String message;
    Date calDate = new Date();
    private List<SelectItem> querterList;
    private int quarter;
    
    public AverageVoucherQuarterlyReport() {
        try {
            querterList = new ArrayList<SelectItem>();
            querterList.add(new SelectItem(0, "--Select--"));
            querterList.add(new SelectItem(3, "March"));
            querterList.add(new SelectItem(6, "June"));
            querterList.add(new SelectItem(9, "September"));
            querterList.add(new SelectItem(12, "December"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public String printAction() {
        String bankName = "";
        String branchAddress = "";
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            int year = Integer.parseInt(ymdFormat.format(calDate).substring(0, 4));
            AuditReportFacadeRemote beanRemote = (AuditReportFacadeRemote) ServiceLocator.getInstance().lookup("AuditReportFacade");
            List<AverageVoucherReportDailyPojo> averageVoucherQuarterlyReport = beanRemote.getAverageVoucherQuarterlyReport(quarter, year, getOrgnBrCode());
            if (averageVoucherQuarterlyReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!averageVoucherQuarterlyReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                int totalWorkingDays = common.getTotalWorkingDays(quarter, year, getOrgnBrCode());
                String repName = "Average Voucher Quarterly Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(calDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pEndMonth", common.getMonthName(quarter));
                fillParams.put("pYear", String.valueOf(year));
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("pTotalWorkingDays", totalWorkingDays);
                new ReportBean().jasperReport("Average_Voucher_Report_Quarter_End", "text/html", new JRBeanCollectionDataSource(averageVoucherQuarterlyReport), fillParams, repName);
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
            int year = Integer.parseInt(ymdFormat.format(calDate).substring(0, 4));
            AuditReportFacadeRemote beanRemote = (AuditReportFacadeRemote) ServiceLocator.getInstance().lookup("AuditReportFacade");
            List<AverageVoucherReportDailyPojo> averageVoucherQuarterlyReport = beanRemote.getAverageVoucherQuarterlyReport(quarter, year, getOrgnBrCode());
            if (averageVoucherQuarterlyReport == null) {
                setMessage("System error occurred");
                return;
            }
            if (!averageVoucherQuarterlyReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                int totalWorkingDays = common.getTotalWorkingDays(quarter, year, getOrgnBrCode());
                String repName = "Average Voucher Quarterly Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportDate", dmyFormat.format(calDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pEndMonth", common.getMonthName(quarter));
                fillParams.put("pYear", String.valueOf(year));
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("pTotalWorkingDays", totalWorkingDays);
                new ReportBean().openPdf("Average Voucher Quarterly Report_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "Average_Voucher_Report_Quarter_End", new JRBeanCollectionDataSource(averageVoucherQuarterlyReport), fillParams);
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
    
    public Date getCalDate() {
        return calDate;
    }
    
    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getQuarter() {
        return quarter;
    }
    
    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }
    
    public List<SelectItem> getQuerterList() {
        return querterList;
    }
    
    public void setQuerterList(List<SelectItem> querterList) {
        this.querterList = querterList;
    }
}
