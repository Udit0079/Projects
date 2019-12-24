/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.RbiSoss1AnnexI2;
import com.cbs.dto.report.RbiSoss1AnnexI3And4;
import com.cbs.dto.report.RbiSoss1AnnexI5;
import com.cbs.dto.report.RbiSoss1AnnexII;
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
import com.cbs.web.pojo.ho.Soss1ConsolidatePojo;
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
public class Oss1Controller extends BaseBean {

    private String message;
    private String branch;    
    private List<SelectItem> branchList;
    private String calDate;
    private String reportIn;
    private List<SelectItem> reportInList;
    private String reportFormat;
    private List<SelectItem> reportFormatIn;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private GLReportFacadeRemote glBeanRemote;
    private RbiReportFacadeRemote ossBeanRemote;
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote ;
    private CommonReportMethodsRemote common = null;
    private HoReportFacadeRemote hoRemote = null;
    private final String jndiName = "CommonReportMethods";
    Date dt = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    private RbiMonthlyReportFacadeRemote monthlyRemote;

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
    
    public Oss1Controller() {
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

            reportTypeList.add(new SelectItem("", "--Select--"));
            reportTypeList.add(new SelectItem("SO", "SOSS1"));
            reportTypeList.add(new SelectItem("XB", "XBRL OSS1"));
//            reportTypeList.add(new SelectItem("AI1", "Annexture I-1"));
//            reportTypeList.add(new SelectItem("AI2", "Annexture I-2"));
//            reportTypeList.add(new SelectItem("AI34", "Annexture I-3 & 4"));
//            reportTypeList.add(new SelectItem("AI5", "Annexture I-5"));
//            reportTypeList.add(new SelectItem("AII", "Annexture II"));
//            reportTypeList.add(new SelectItem("DDB", "Details of Depositors And Borrower"));

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
            String reportName = "", reptName = "",reportDesc = "";
            Map fillParams = new HashMap();
            BigDecimal reptOpt = null;
            if (this.reportIn.equalsIgnoreCase("T")) {
                reptOpt = new BigDecimal("1000");
                reportDesc = "Amount (Rs. in Thousand)";
            } else if (this.reportIn.equalsIgnoreCase("L")) {
                reptOpt = new BigDecimal("100000");
                reportDesc = "Amount (Rs. in Lac)";
            } else if (this.reportIn.equalsIgnoreCase("C")) {
                reptOpt = new BigDecimal("10000000");
                reportDesc = "Amount (Rs. in Crore)";
            } else if (this.reportIn.equalsIgnoreCase("R")) {
                reptOpt = new BigDecimal("1");
                reportDesc = "Amount (Rs.)";
            }
            
            reportName = "SOSS-1 Statement on Assets and Liabilities";
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("username", getUserName());
            fillParams.put("pReport", reportName);
            fillParams.put("pPrintedDate", calDate);
            fillParams.put("pAmtIn", reportDesc);
            
            /**
             * OSS1 Processing
             */
            if (this.reportType.equalsIgnoreCase("SO") || this.reportType.equalsIgnoreCase("XB")/*|| this.reportType.equalsIgnoreCase("AI1") || this.reportType.equalsIgnoreCase("DDB")*/) {
                
                if(this.reportType.equalsIgnoreCase("SO")){
                    reptName = "SOSS1";
                    reportName = "SOSS-1 Statement on Assets and Liabilities";                    
                } else if(this.reportType.equalsIgnoreCase("XB")){
                    reptName = "XBRLOSS1";
                    reportName ="XBRL SOSS1";
                }
                List<String> dates = new ArrayList<>();
                String dt =ymd.format(dmy.parse(calDate));
                dates.add(dt);
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);                
                fillParams.put("pReport", reportName);                
                List<RbiSossPojo> resultListSoss1 = rbiSoss1And2Remote.getSOSS2(reptName, ymd.format(dmy.parse(calDate)), branch, reptOpt, reportFormat,osBlancelist,"0");
              
                reptName = "SOSS1AI1";
                List<RbiSossPojo> resultListSoss1AI1 = rbiSoss1And2Remote.getSOSS2(reptName, ymd.format(dmy.parse(calDate)), branch, reptOpt, reportFormat,osBlancelist,"0");
                
                reptName = "SOSS1AI2";
                List<RbiSoss1AnnexI2> resultListSoss1AnnexI2 = rbiSoss1And2Remote.getSOSS1AnnexI2(reptName, ymd.format(dmy.parse(calDate)), branch, reptOpt);
                
                reptName = "SOSS1AI34";
                List<RbiSoss1AnnexI3And4> resultListSOSS1AI34 = rbiSoss1And2Remote.getSOSS1AnnexI3And4(reptName, ymd.format(dmy.parse(calDate)), branch, reptOpt);
               
                reptName = "SOSS1AI5";
                List<RbiSoss1AnnexI5> resultListSoss1AnnexI5 = rbiSoss1And2Remote.getSOSS1AnnexI5(reptName, ymd.format(dmy.parse(calDate)), branch, reptOpt);
                
                reptName = "SOSS1AII";
                List<RbiSoss1AnnexII> resultListSoss1AnnexII = rbiSoss1And2Remote.getSOSS1AnnexII(reptName, ymd.format(dmy.parse(calDate)), branch, reptOpt);
                
                reptName = "SOSS1DDB";
                List<RbiSossPojo> resultListSoss1DDB = rbiSoss1And2Remote.getSOSS2(reptName, ymd.format(dmy.parse(calDate)), branch, reptOpt, reportFormat,osBlancelist,"0");
                
                List<Soss1ConsolidatePojo> jrxmlList = new ArrayList<Soss1ConsolidatePojo>();     //Actual List To Print
                Soss1ConsolidatePojo jrxmlPojo = new Soss1ConsolidatePojo();
                jrxmlPojo.setRbiSoss1List(new JRBeanCollectionDataSource(resultListSoss1));
                jrxmlPojo.setRbiSoss1Annex1List(new JRBeanCollectionDataSource(resultListSoss1AI1));
                jrxmlPojo.setRbiSoss1AnnexI2List(new JRBeanCollectionDataSource(resultListSoss1AnnexI2));
                jrxmlPojo.setRbiSoss1Annex3And4List(new JRBeanCollectionDataSource(resultListSOSS1AI34));
                jrxmlPojo.setRbiSoss1AnnexI5List(new JRBeanCollectionDataSource(resultListSoss1AnnexI5));
                jrxmlPojo.setRbiSoss1AnnexIIList(new JRBeanCollectionDataSource(resultListSoss1AnnexII));
                jrxmlPojo.setRbiSoss1DDBList(new JRBeanCollectionDataSource(resultListSoss1DDB));
                jrxmlList.add(jrxmlPojo);
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().downloadPdf("SOSS1_" + ymd.format(dmy.parse(calDate)), "SOSS1_COVER", new JRBeanCollectionDataSource(jrxmlList), fillParams);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("");
        this.setReportIn("R");
        this.setReportFormat("N");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}