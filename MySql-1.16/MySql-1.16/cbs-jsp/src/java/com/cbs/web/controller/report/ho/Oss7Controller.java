/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.Oss7BusinessPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.RbiQuarterlyReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.Oss7JrxmlPojo;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class Oss7Controller extends BaseBean {

    private String message;
    private String reportType;
    private String reportDt;
    private String reportIn;
    private String reportFormat;
    private List<SelectItem> reportFormatIn;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> reportInList;
    private CommonReportMethodsRemote common;
    private RbiQuarterlyReportFacadeRemote quarterlyRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public Oss7Controller() {
        this.setMessage("");
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            quarterlyRemote = (RbiQuarterlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiQuarterlyReportFacade");
            onLoadData();
            this.setReportDt(getTodayDate());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onLoadData() {
        reportTypeList = new ArrayList<SelectItem>();
        reportInList = new ArrayList<SelectItem>();
        reportFormatIn = new ArrayList<SelectItem>();
        try {
            reportTypeList.add(new SelectItem("", "--Select--"));
            reportTypeList.add(new SelectItem("oss7", "OSS-7 Statement on CRAR"));
            reportTypeList.add(new SelectItem("XB", "XBRL OSS 7"));

            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "Thousand"));
            reportInList.add(new SelectItem("L", "Lacs"));
            reportInList.add(new SelectItem("C", "Crore"));
            
            reportFormatIn.add(new SelectItem("N", "In Detail"));
            reportFormatIn.add(new SelectItem("Y", "In Summary"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void dtProcessAction() {
        this.setMessage("");
        try {
            if (this.reportType == null || this.reportType.equals("")) {
                this.setMessage("Please select Report Type.");
                return;
            }
            if (this.reportDt == null || this.reportDt.equals("")) {
                this.setMessage("Please fill Report Date.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.reportDt)) {
                this.setMessage("Please fill correct Report Date.");
                return;
            }
            if (dmy.parse(reportDt).compareTo(dmy.parse(getTodayDate())) > 0) {
                this.setMessage("Report date can not greater than today date.");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void downloadPdf() {
        this.setMessage("");
        try {
            String reportName = "OSS-7 Statement on CRAR";
            Map fillParams = new HashMap();

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("report", reportName);
            fillParams.put("pPrintedDate", this.reportDt);
            String[] arr = Util.getReportOptionAndDescription(this.reportIn);
            fillParams.put("pAmtIn", arr[1]);
            List<Oss7BusinessPojo> resultList= null;
            if(reportType.equalsIgnoreCase("XB")){
                resultList = quarterlyRemote.getXBRLOss7Details(reportType, ymd.format(dmy.parse(this.reportDt)),
                    new BigDecimal(arr[0]), getOrgnBrCode(), reportFormat);
                reportName = "XBRL OSS 7";
            } else if(reportType.equalsIgnoreCase("oss7")){
                reportName = "OSS-7 Statement on CRAR";
                resultList = quarterlyRemote.getOss7Details(reportType, ymd.format(dmy.parse(this.reportDt)),
                    new BigDecimal(arr[0]), getOrgnBrCode(), reportFormat);
            }
            fillParams.put("report", reportName);
//            List<Oss7BusinessPojo> resultList = quarterlyRemote.getOss7Details(reportType, ymd.format(dmy.parse(this.reportDt)),
//                    new BigDecimal(arr[0]), getOrgnBrCode(), reportFormat);
            if (resultList.isEmpty()) {
                this.setMessage("There is no data to print the report.");
                return;
            }
            List<Oss7JrxmlPojo> jrxmlList = new ArrayList<Oss7JrxmlPojo>();     //Actual List To Print
            for (Oss7BusinessPojo businessPojo : resultList) {
                Oss7JrxmlPojo jrxmlPojo = new Oss7JrxmlPojo();

                List<RbiSossPojo> oss7PartAList = businessPojo.getOss7PartAList();
                List<RbiSossPojo> oss7PartBList = businessPojo.getOss7PartBList();
                List<RbiSossPojo> oss7PartCSec1List = businessPojo.getOss7PartCSec1List();
                List<RbiSossPojo> oss7PartCSec2List = businessPojo.getOss7PartCSec2List();
                List<RbiSossPojo> oss7PartCSec2iList = businessPojo.getOss7PartCSec2iList();

                jrxmlPojo.setOss7PartAList(new JRBeanCollectionDataSource(oss7PartAList));
                jrxmlPojo.setOss7PartBList(new JRBeanCollectionDataSource(oss7PartBList));
                jrxmlPojo.setOss7PartCSec1List(new JRBeanCollectionDataSource(oss7PartCSec1List));
                jrxmlPojo.setOss7PartCSec2List(new JRBeanCollectionDataSource(oss7PartCSec2List));
                jrxmlPojo.setOss7PartCSec2iList(new JRBeanCollectionDataSource(oss7PartCSec2iList));

                jrxmlList.add(jrxmlPojo);
            }
            /*Report Checking*/
//            for (int i = 0; i < jrxmlList.size(); i++) {
//                Oss7JrxmlPojo obj = jrxmlList.get(i);
//                List<RbiSossPojo> partA = (List<RbiSossPojo>) obj.getOss7PartAList().getData();
//                for (int k = 0; k < partA.size(); k++) {
//                    RbiSossPojo grid = partA.get(k);
//                    System.out.println("Data::" + grid + "\n");
//                    System.out.println("Description::" + grid.getDescription());
//                }
//            }
            /*Report Printing*/
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            new ReportBean().downloadPdf("OSS7_" + ymd.format(dmy.parse(reportDt)), "soss7Cover",
                    new JRBeanCollectionDataSource(jrxmlList), fillParams);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("");
        this.setReportDt(getTodayDate());
        this.setReportIn("L");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    /*Getter And Setter*/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    public String getReportIn() {
        return reportIn;
    }

    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public List<SelectItem> getReportInList() {
        return reportInList;
    }

    public void setReportInList(List<SelectItem> reportInList) {
        this.reportInList = reportInList;
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
    
}
