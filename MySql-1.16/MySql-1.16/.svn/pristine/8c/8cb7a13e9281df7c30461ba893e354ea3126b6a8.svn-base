/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.BalanceSheetDTO;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Sanjay Khandelwal
 */
public class BalanceSheet extends BaseBean {

    private String message;
    private Date asonDate;
    private List<SelectItem> reportInList;
    private String reportFormat;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> reportFormatIn;
    private String reportIn;
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    Date dt = new Date();
    private int staff;
    private String staffShow;

    public Date getAsonDate() {
        return asonDate;
    }

    public void setAsonDate(Date asonDate) {
        this.asonDate = asonDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getReportInList() {
        return reportInList;
    }

    public void setReportInList(List<SelectItem> reportInList) {
        this.reportInList = reportInList;
    }

    public String getReportIn() {
        return reportIn;
    }

    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public List<SelectItem> getReportFormatIn() {
        return reportFormatIn;
    }

    public void setReportFormatIn(List<SelectItem> reportFormatIn) {
        this.reportFormatIn = reportFormatIn;
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

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }

    public String getStaffShow() {
        return staffShow;
    }

    public void setStaffShow(String staffShow) {
        this.staffShow = staffShow;
    }
    
    
    
    
    
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public BalanceSheet() {
        try {
            setMessage("");
            this.setAsonDate(dt);
            setStaffShow("none");
            reportFormatIn = new ArrayList<SelectItem>();
            reportTypeList = new ArrayList<SelectItem>();
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            reportInList = new ArrayList<SelectItem>();
            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "THOUSANDS"));
            reportInList.add(new SelectItem("L", "LACS"));
            reportInList.add(new SelectItem("C", "CRORE"));

            reportTypeList.add(new SelectItem("", "--Select--"));
            reportTypeList.add(new SelectItem("1", "Balance Sheet"));
            reportTypeList.add(new SelectItem("2", "Balance Sheet Items(RBI)"));
            reportTypeList.add(new SelectItem("3", "Annual Financial Review"));
            String bankName = ftsPostRemote.getBankCode();
            if (bankName.equalsIgnoreCase("kccb")) {
                reportTypeList.add(new SelectItem("4", "Balance Sheet Report (RBI)"));
            }
            int misReportSociety = ftsPostRemote.getCodeForReportName("MIS_REPORT_SOCIETY");
            if(misReportSociety==1){
              reportTypeList.add(new SelectItem("5", "Mis Report Society"));  
               setStaffShow("");
            }

            reportFormatIn.add(new SelectItem("N", "In Detail"));
            reportFormatIn.add(new SelectItem("Y", "In Summary"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        Map map = new HashMap();
        try {
            setMessage("");
            if (this.reportType.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Type!!");
                return;
            }
            if (this.asonDate == null) {
                setMessage("Please Fill the Date");
                return;
            }
            Date dt = new Date();
            if (this.asonDate.compareTo(dt) > 0) {
                setMessage("As on date is greater than current date");
                return;
            }
            String bankName = "";
            String bankAddress = "";
            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(asonDate);
            if(this.reportType.equalsIgnoreCase("1")){
                int preFinYear = 0;
                //            if (cal.get(Calendar.MONTH) <= 2) {
                preFinYear = cal.get(Calendar.YEAR) - 1;
                //            } else {
                //                preFinYear = cal.get(Calendar.YEAR) - 0;
                //            }            
                //            
                String prevYear =dmy.format(asonDate).substring(0,2)+"/"+dmy.format(asonDate).substring(3,5)+"/"+preFinYear;            

                //            List<BalanceSheetDTO> dataList = hoRemote.getBalanceSheetData(ymd.format(asonDate));
                String reportName = "Balance Sheet";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                map.put("pBankName", brNameAndAddList.get(0).toString());
                map.put("pBankAddress", brNameAndAddList.get(1).toString());
                map.put("username", getUserName());
                map.put("report", reportName);
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                map.put("pAmtIn", arr[1]);
                map.put("pPrintedDate", dmy.format(asonDate));
                map.put("pCurFinDate", dmy.format(asonDate));
                map.put("pPreFinDate", prevYear);
                String report_name ="";
                if(reportFormat.equalsIgnoreCase("Y")){
                    report_name = "BalSheetCon";
                } else {
                    report_name = "BalSheetDet";
                }
                List<String> dates = new ArrayList<String>();
                dates.add(ymd.format(asonDate));
                dates.add(ymd.format(dmy.parse(prevYear)));
                List<RbiSossPojo> resultList = hoRemote.getBalanceSheet(report_name, dates, "0A", new BigDecimal(arr[0]), reportFormat,staff);
                if (!resultList.isEmpty()) {
                    //                    new ReportBean().downloadPdf("Form9_" + ymd.format(dmy.parse(this.firstAltDt)), "all_rbi_report_master", 
                    //                            new JRBeanCollectionDataSource(resultList), fillParams);
                    //                new ReportBean().openPdf("Form9_" + ymd.format(dmy.parse(this.asonDate)), "all_rbi_report_master",
                    //                        new JRBeanCollectionDataSource(resultList), map);
                    if(reportFormat.equalsIgnoreCase("Y")){
                        new ReportBean().downloadPdf("BALANCE_SHEET_"+ymd.format(asonDate), "balance_sheet_con", new JRBeanCollectionDataSource(resultList), map);            
                    } else {
                        new ReportBean().downloadPdf("BALANCE_SHEET_"+ymd.format(asonDate), "balance_sheet_detailed", new JRBeanCollectionDataSource(resultList), map);            
                    }

                }
            } else if(this.reportType.equalsIgnoreCase("2") || this.reportType.equalsIgnoreCase("4")) {
                String reportName = "Balance Sheet Items";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                map.put("pBankName", brNameAndAddList.get(0).toString());
                map.put("pBankAddress", brNameAndAddList.get(1).toString());
                map.put("username", getUserName());
                map.put("report", reportName);
                if(this.reportType.equalsIgnoreCase("4")) {
                    map.put("report", "BALANCE SHEET REPORT");
                }
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                map.put("pAmtIn", arr[1]);
                map.put("pPrintedDate", dmy.format(asonDate));
                map.put("pCurFinDate", dmy.format(asonDate));
                String report_name = "BALSHEETITEMS";
                if(this .reportType.equalsIgnoreCase("4")) {
                    report_name= "BalSheetDetRBI";
                }
                List<String> dates = new ArrayList<String>();
                dates.add(ymd.format(asonDate));
                List<RbiSossPojo> resultList = hoRemote.getBalanceSheet(report_name, dates, "0A", new BigDecimal(arr[0]), reportFormat,staff);
                if (!resultList.isEmpty()) {
                    new ReportBean().downloadPdf("BALANCE_SHEET_ITEMS"+ymd.format(asonDate), "balancesheetitems", new JRBeanCollectionDataSource(resultList), map);
                }
            } else if(this.reportType.equalsIgnoreCase("3")) {
                int preFinYear = 0;
                preFinYear = cal.get(Calendar.YEAR) - 1;
                String prevYear =dmy.format(asonDate).substring(0,2)+"/"+dmy.format(asonDate).substring(3,5)+"/"+preFinYear;
                String reportName = "Annual Financial Review";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                map.put("pBankName", brNameAndAddList.get(0).toString());
                map.put("pBankAddress", brNameAndAddList.get(1).toString());
                map.put("username", getUserName());
                map.put("report", reportName);
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                map.put("pAmtIn", arr[1]);
                map.put("pPrintedDate", dmy.format(asonDate));
                map.put("pCurFinDate", dmy.format(asonDate));
                map.put("pPreFinDate", prevYear);
                String report_name = "Financial_Review";
                List<String> dates = new ArrayList<String>();
                dates.add(ymd.format(asonDate));
                dates.add(ymd.format(dmy.parse(prevYear)));
                List<RbiSossPojo> resultList = hoRemote.getBalanceSheet(report_name, dates, "0A", new BigDecimal(arr[0]), reportFormat,staff);
                if (!resultList.isEmpty()) {
                    if(reportFormat.equalsIgnoreCase("Y")){
                        new ReportBean().downloadPdf("ANNUAL_FINANCIAL_REVIEW_"+ymd.format(asonDate), "balance_sheet_con", new JRBeanCollectionDataSource(resultList), map);            
                    } else {
                        new ReportBean().downloadPdf("ANNUAL_FINANCIAL_REVIEW_"+ymd.format(asonDate), "balance_sheet_detailed", new JRBeanCollectionDataSource(resultList), map);            
                    }
                }
            }else if (this.reportType.equalsIgnoreCase("5")) {
              String  prevYear = dmy.format(ymd.parse(CbsUtil.dateAdd(ymd.format(asonDate), -1)));
                String reportName = "Mis Report Society";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                map.put("pBankName", brNameAndAddList.get(0).toString());
                map.put("pBankAddress", brNameAndAddList.get(1).toString());
                map.put("username", getUserName());
                map.put("report", reportName);
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                map.put("pAmtIn", arr[1]);
                map.put("pPrintedDate", dmy.format(asonDate));
                map.put("pCurFinDate", dmy.format(asonDate));
                map.put("pPreFinDate", prevYear);
                String report_name = "";
                if (reportFormat.equalsIgnoreCase("Y")) {
                    report_name = "mis_report_uday_society_con";
                } else {
                    report_name = "mis_report_uday_society";
                }
                List<String> dates = new ArrayList<String>();
                dates.add(ymd.format(asonDate));
                dates.add(ymd.format(dmy.parse(prevYear)));
                List<RbiSossPojo> resultList = hoRemote.getBalanceSheet(report_name, dates, "0A", new BigDecimal(arr[0]), reportFormat,staff);
                if (!resultList.isEmpty()) {
                    if (reportFormat.equalsIgnoreCase("Y")) {
                        new ReportBean().downloadPdf("MIS_REPORT_SOCIETY_" + ymd.format(asonDate), "mis_report_uday_society_con", new JRBeanCollectionDataSource(resultList), map);
                    } else {
                        new ReportBean().downloadPdf("MIS_REPORT_SOCIETY_" + ymd.format(asonDate), "mis_report_uday_society", new JRBeanCollectionDataSource(resultList), map);
                    }
                }
            }

//            String report = "Balance Sheet";
//            map.put("pPrintedDate", dmy.format(asonDate));
//            map.put("pCurrentYear", dmy.format(asonDate));            
//            map.put("pPreviousYear", prevYear);
//            
//            map.put("pAmtIn", this.getReportIn());
//            map.put("username", this.getUserName());
//            map.put("report", report);
//
//            new ReportBean().downloadPdf("BALANCE_SHEET_"+ymd.format(asonDate), "BALANCE_SHEET", new JRBeanCollectionDataSource(dataList), map);            
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setAsonDate(dt);
        this.setReportIn("R");
    }

    public String exit() {
        this.setMessage("");
        this.setAsonDate(dt);
        this.setReportIn("R");
        return "case1";
    }
}