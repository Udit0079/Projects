/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.RbiSoss1And2ReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class Oss2Controller extends BaseBean {
    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String calDate;
    private String reportIn;
    private List<SelectItem> reportInList;
    private String reportType;
    private String reportFormat;
    private List<SelectItem> reportFormatIn;
    private List<SelectItem> reportTypeList;
    private GLReportFacadeRemote glBeanRemote;
    private RbiReportFacadeRemote ossBeanRemote;
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote; 
    private CommonReportMethodsRemote common = null;
    private HoReportFacadeRemote hoRemote = null;
    private final String jndiName = "CommonReportMethods";
    Date dt = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    private String noOfEmp;
    
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

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportIn() {
        return reportIn;
    }

    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }

    public List<SelectItem> getReportInList() {
        return reportInList;
    }

    public void setReportInList(List<SelectItem> reportInList) {
        this.reportInList = reportInList;
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

    public String getNoOfEmp() {
        return noOfEmp;
    }
    public void setNoOfEmp(String noOfEmp) {
        this.noOfEmp = noOfEmp;
    }   
        
    public Oss2Controller(){
        reportTypeList = new ArrayList<SelectItem>();
        reportInList = new ArrayList<SelectItem>();
        reportFormatIn = new ArrayList<SelectItem>();
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            ossBeanRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            rbiSoss1And2Remote = (RbiSoss1And2ReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiSoss1And2ReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            monthlyRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiMonthlyReportFacade");
            
            List brnLst = new ArrayList();
            brnLst = glBeanRemote.getBranchCodeListExt(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            
            reportTypeList.add(new SelectItem("","--Select--"));
            reportTypeList.add(new SelectItem("SO", "SOSS2"));
            reportTypeList.add(new SelectItem("XB", "XBRL OSS2"));
            
            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "Thousand"));
            reportInList.add(new SelectItem("L", "Lacs"));
            reportInList.add(new SelectItem("C", "Crore"));
            
            reportFormatIn.add(new SelectItem("N", "In Detail"));
            reportFormatIn.add(new SelectItem("Y", "In Summary"));
            
            this.setCalDate(calDate);
            
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }       
    }
    
        public void downloadPdf() {
        this.setMessage("");
        try {
            String reportName = "";
            Map fillParams = new HashMap();
            BigDecimal reptOpt = null;
//            if (this.reportType.equalsIgnoreCase("SO")) {
                /**
                 * OSS2 Processing
                 */
                 if (this.reportIn.equalsIgnoreCase("T")) {
                     reptOpt = new BigDecimal("1000");
                 } else if (this.reportIn.equalsIgnoreCase("L")) {
                     reptOpt = new BigDecimal("100000");
                 } else if (this.reportIn.equalsIgnoreCase("C")) {
                     reptOpt = new BigDecimal("10000000");
                 } else if (this.reportIn.equalsIgnoreCase("R")) {
                     reptOpt = new BigDecimal("1");
                 }
                String reptName = "" ;
                if(reportType.equalsIgnoreCase("SO")){
                    reptName = "SOSS2";
                    reportName = "SOSS - 2 Statement on Earnings";
                } else if(reportType.equalsIgnoreCase("XB")) {
                    reptName = "XBRLOSS2";
                    reportName = "XBRL OSS2";
                }
                if(noOfEmp.equalsIgnoreCase("")) {
                    setMessage("Please Fill Noumber Of employees!!!");
                    return;
                }
                List<String> dates = new ArrayList<>();                
                dates.add(ymd.format(dmy.parse(calDate)));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceBetweenDateList(branch.equalsIgnoreCase("90") ? "0A" : branch, ossBeanRemote.getMinFinYear(ymd.format(dmy.parse(calDate))), ymd.format(dmy.parse(calDate)));
                List<RbiSossPojo> resultList = rbiSoss1And2Remote.getSOSS2(reptName,ymd.format(dmy.parse(calDate)), branch, reptOpt,reportFormat,osBlancelist,noOfEmp);                
                if (!resultList.isEmpty()) {
                    
                    //It is report printing.
                    List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                    fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                    fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                    fillParams.put("username", getUserName());
                    fillParams.put("report", reportName);
                    fillParams.put("pPrintedDate", calDate);
                    String reportDesc = "";
                    if (this.reportIn.equals("T")) {
                        reportDesc = "Amount (Rs. in Thousand)";
                    } else if (this.reportIn.equals("L")) {
                        reportDesc = "Amount (Rs. in Lac)";
                    } else if (this.reportIn.equals("C")) {
                        reportDesc = "Amount (Rs. in Crore)";
                    } else if (this.reportIn.equals("R")) {
                        reportDesc = "Amount (Rs.)";
                    }
                    fillParams.put("pAmtIn", reportDesc);
                    
                    new ReportBean().downloadPdf("SOSS2_" + ymd.format(dmy.parse(calDate)), "all_rbi_report_master_OSS2", new JRBeanCollectionDataSource(resultList), fillParams);
                    
                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("");
        this.setReportIn("R");
        this.setNoOfEmp("0");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
    
    
}