/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.Oss4PartGExpPojo;
import com.cbs.dto.report.Oss4PartGMgmtPojo;
import com.cbs.dto.report.Oss4PartGPojo;
import com.cbs.dto.report.Oss4PartGSecAdvPojo;
import com.cbs.dto.report.Oss4PartGUnSecAdvPojo;
import com.cbs.dto.report.RbiSos4Pojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.RbiReportPartDFacadeRemote;
import com.cbs.facade.report.RbiSoss1And2ReportFacadeRemote;
import com.cbs.pojo.Oss4PartACombinedPojo;
import com.cbs.pojo.TopLoanPojo;
import com.cbs.pojo.TopLoanPojoComparatorForSrNo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.Oss4JrxmlPojo;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class Oss4Controller extends BaseBean {

    private String msg;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String calDate;
    private String reportIn;
    private List<SelectItem> reportInList;
//    private GLReportFacadeRemote glBeanRemote;
    private RbiReportPartDFacadeRemote ossBeanPartDRemote;
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote; 
    private RbiReportFacadeRemote ossBeanRemote;
    private CommonReportMethodsRemote common;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private RbiMonthlyReportFacadeRemote monthlyRemote;

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public Oss4Controller() {
        reportTypeList = new ArrayList<SelectItem>();
        reportInList = new ArrayList<SelectItem>();
        try {
//            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ossBeanPartDRemote = (RbiReportPartDFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportPartDFacade");
            monthlyRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiMonthlyReportFacade");
            rbiSoss1And2Remote = (RbiSoss1And2ReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiSoss1And2ReportFacade");
            ossBeanRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");

            reportTypeList.add(new SelectItem("", "--Select--"));
            reportTypeList.add(new SelectItem("oss4A", "OSS/SOSS/XBRL - 4 Part A"));
            reportTypeList.add(new SelectItem("oss4B", "OSS/SOSS/XBRL - 4 Part B"));
            reportTypeList.add(new SelectItem("oss4C", "OSS/SOSS/XBRL - 4 Part C"));
            reportTypeList.add(new SelectItem("oss4D", "SOSS-4 Part- D / OSS 6 /XBRL OSS 5 Part-D"));
            reportTypeList.add(new SelectItem("oss4E", "SOSS-4 Part- E / OSS 6 /XBRL OSS 5 Part-E"));
            reportTypeList.add(new SelectItem("oss4F", "SOSS-4 Part- F / OSS 6 /XBRL OSS 5 Part-F"));
            reportTypeList.add(new SelectItem("oss4Indstry", "SOSS-4 Part- F / OSS 6 /XBRL OSS 5 Industry Wise"));
            reportTypeList.add(new SelectItem("oss4G", "SOSS-4 Part-G / OSS-5 / XBRL OSS 6 "));

            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "Thousand"));
            reportInList.add(new SelectItem("L", "Lacs"));
            reportInList.add(new SelectItem("C", "Crore"));
            this.setCalDate(getTodayDate());
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void downloadPdf() {
        this.setMsg("");
        try {
            if (this.reportType == null || this.reportType.equals("")) {
                this.setMsg("Please select report type.");
                return;
            }
            if (this.calDate == null || this.calDate.equals("")) {
                this.setMsg("Please select appropriate date.");
                return;
            }
            String reportName = "SOSS - 4 Statement on Non Performing Assets, Large Exposures, Segmentwise Advances & NDTL/NOF Ratio";
            Map fillParams = new HashMap();
            String[] arr = Util.getReportOptionAndDescription(this.reportIn);
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            List<RbiSossPojo> resultList =new ArrayList<RbiSossPojo>();

            List<Oss4JrxmlPojo> jrxmlList = new ArrayList<Oss4JrxmlPojo>();     //Actual List To Print
            Oss4JrxmlPojo jrxmlPojo = new Oss4JrxmlPojo();
            if(reportType.equalsIgnoreCase("oss4A")) {
                reportName = "OSS/SOSS/XBRL - 4 Part A";
                 /*Part-A*/
                List<String> dates = new ArrayList<>();
                dates.add(ymd.format(dmy.parse(calDate)));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);
                List<Oss4PartACombinedPojo> partAList = ossBeanPartDRemote.getSOS4PartA(ymd.format(dmy.parse(calDate)), getOrgnBrCode(), new BigDecimal(arr[0]),osBlancelist);
                if (!partAList.isEmpty()) {
                    Oss4PartACombinedPojo partAPojo = partAList.get(0);
                    jrxmlPojo.setPartAi(new JRBeanCollectionDataSource(partAPojo.getRbiPojoTable()));  //Part-A-I
                    jrxmlPojo.setPartAii(new JRBeanCollectionDataSource(partAPojo.getS4List()));       //Part-A-II
                }
            } else if(reportType.equalsIgnoreCase("oss4B")) {
                reportName = "OSS/SOSS/XBRL - 4 Part B";
                /*Part-B*/
                /**
                * This report will print separate for 50 impaired credit account and
                * 50 (More than 100 lakhs sanction limit account)
                */
                List<TopLoanPojo> partBList = ossBeanPartDRemote.getSoss4PartB4(ymd.format(dmy.parse(calDate)), getOrgnBrCode(), new BigDecimal(arr[0]));
                if (!partBList.isEmpty()) {
                    List<TopLoanPojo> impList = new ArrayList<TopLoanPojo>();   //Separate impared account datalist.
                    List<TopLoanPojo> sanList = new ArrayList<TopLoanPojo>();   //Separate sanction account datalist.
                    int i = 0;
                    for (TopLoanPojo pojo : partBList) {
                        i = i + 1;
                        TopLoanPojo impPojo = new TopLoanPojo();
                        TopLoanPojo sanPojo = new TopLoanPojo();
                        if (pojo.getDataClass().equals("IMPARED")) {
                            impPojo.setSno(i);
                            impPojo.setAccountNo(pojo.getAccountNo());
                            impPojo.setBorrowerName(pojo.getBorrowerName());
                            impPojo.setOutStanding(pojo.getOutStanding());
                            impPojo.setSubSector(pojo.getSubSector());
                            impPojo.setFunded(pojo.getFunded());
                            impPojo.setNonFunded(pojo.getNonFunded());
                            impPojo.setPercToAdvance(pojo.getPercToAdvance());
                            impPojo.setCapitalFund(pojo.getCapitalFund());
                            impPojo.setPercToCapital(pojo.getPercToCapital());
                            impPojo.setRiskClassification(pojo.getRiskClassification());
                            impPojo.setRowCount(pojo.getRowCount());

                            impList.add(impPojo);
                        } else if (pojo.getDataClass().equals("SANCTIONED")) {
                            sanPojo.setSno(i);
                            sanPojo.setAccountNo(pojo.getAccountNo());
                            sanPojo.setBorrowerName(pojo.getBorrowerName());
                            sanPojo.setOutStanding(pojo.getOutStanding());
                            sanPojo.setSubSector(pojo.getSubSector());
                            sanPojo.setFunded(pojo.getFunded());
                            sanPojo.setNonFunded(pojo.getNonFunded());
                            sanPojo.setPercToAdvance(pojo.getPercToAdvance());
                            sanPojo.setCapitalFund(pojo.getCapitalFund());
                            sanPojo.setPercToCapital(pojo.getPercToCapital());
                            sanPojo.setRiskClassification(pojo.getRiskClassification());
                            sanPojo.setRowCount(pojo.getRowCount());

                            sanList.add(sanPojo);
                        }
                    }
                    Integer rowCount = 0;
                    if (!impList.isEmpty()) {
                        rowCount = Integer.parseInt(impList.get(0).getRowCount());
                        Collections.sort(impList, new TopLoanPojoComparatorForSrNo());
                        if (impList.size() > rowCount) {
                            impList = impList.subList(0, rowCount);
                        }
                    }
                    if (!sanList.isEmpty()) {
                        rowCount = Integer.parseInt(sanList.get(0).getRowCount());
                        Collections.sort(sanList, new TopLoanPojoComparatorForSrNo());
                        if (sanList.size() > rowCount) {
                            sanList = sanList.subList(0, rowCount);
                        }
                    }
                    jrxmlPojo.setPartB4ImpList(new JRBeanCollectionDataSource(impList));
                    jrxmlPojo.setPartB4SanList(new JRBeanCollectionDataSource(sanList));
                }                
            } else if(reportType.equalsIgnoreCase("oss4C")) {
                reportName = "OSS/SOSS/XBRL - 4 Part C";                
                /*Part-C*/
                // For Single Borrower
                List<String> dates = new ArrayList<String>();
                dates.add(ymd.format(dmy.parse(calDate)));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);
                List<TopLoanPojo> partCiList = ossBeanPartDRemote.getSoss4PartC1(ymd.format(dmy.parse(calDate)), getOrgnBrCode(), new BigDecimal(arr[0]),"1",osBlancelist);
                if (!partCiList.isEmpty()) {
                    jrxmlPojo.setPartCiImpList(new JRBeanCollectionDataSource(partCiList));
                }
                // For Group Borrowers
                List<TopLoanPojo> partC2List = ossBeanPartDRemote.getSoss4PartC1(ymd.format(dmy.parse(calDate)), getOrgnBrCode(), new BigDecimal(arr[0]),"2",osBlancelist);
                if (!partC2List.isEmpty()) {
                    jrxmlPojo.setPartC2ImpList(new JRBeanCollectionDataSource(partC2List));
                }
            } else if (reportType.equalsIgnoreCase("oss4D")|| reportType.equalsIgnoreCase("oss4E")|| reportType.equalsIgnoreCase("oss4F") || reportType.equalsIgnoreCase("oss4Indstry") ){
                reportName = "SOSS4 Part D,E,F / OSS 5/ XBRL OSS 5 ";
                /*Part-D*/
//                System.out.println("Soss4 Part D:"+new Date());
                if(reportType.equalsIgnoreCase("oss4D")) {
                    List<RbiSos4Pojo> partDList = ossBeanPartDRemote.getPartD(ymd.format(dmy.parse(calDate)),  getOrgnBrCode().equalsIgnoreCase("90") ?"0A" : getOrgnBrCode(), new BigDecimal(arr[0]));
                    if (!partDList.isEmpty()) {
                        jrxmlPojo.setPartD(new JRBeanCollectionDataSource(partDList));
                    }
                } else if(reportType.equalsIgnoreCase("oss4E")) {
                /*Part-E*/
//                System.out.println("Soss4 Part E:"+new Date());
                    List<RbiSos4Pojo> partEList = ossBeanPartDRemote.getSOS4PartE(ymd.format(dmy.parse(calDate)), getOrgnBrCode().equalsIgnoreCase("90") ?"0A" : getOrgnBrCode(), new BigDecimal(arr[0]));
                    if (!partEList.isEmpty()) {
                        jrxmlPojo.setPartE(new JRBeanCollectionDataSource(partEList));
                    }
                } else if(reportType.equalsIgnoreCase("oss4F")) {
                /*Part-F*/
//                System.out.println("Soss4 Part F:"+new Date());
                    List<RbiSos4Pojo> partFList = ossBeanPartDRemote.getSOS4PartF(ymd.format(dmy.parse(calDate)), getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), new BigDecimal(arr[0]),"N");
                    if (!partFList.isEmpty()) {
                        jrxmlPojo.setPartF(new JRBeanCollectionDataSource(partFList));
                    }
//                    List<String> dates = new ArrayList<>();
//                    String dt = ymd.format(dmy.parse(calDate));
//                    dates.add(dt);
//                    List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);                
//                    resultList = rbiSoss1And2Remote.getSOSS2("OSS4-PART-F", ymd.format(dmy.parse(calDate)), getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), new BigDecimal(arr[0]), "N", osBlancelist, "0");
//                    if (!resultList.isEmpty()) {
//
//                        //It is report printing.
//                        String reportDesc = "";
//                        if (this.reportIn.equals("T")) {
//                            reportDesc = "Amount (Rs. in Thousand)";
//                        } else if (this.reportIn.equals("L")) {
//                            reportDesc = "Amount (Rs. in Lac)";
//                        } else if (this.reportIn.equals("C")) {
//                            reportDesc = "Amount (Rs. in Crore)";
//                        } else if (this.reportIn.equals("R")) {
//                            reportDesc = "Amount (Rs.)";
//                        }
//                        fillParams.put("pAmtIn", reportDesc);
//                    }
                } else if(reportType.equalsIgnoreCase("oss4Indstry")) {
                    List<RbiSos4Pojo> industryList = ossBeanPartDRemote.getIndustryWiseExposure("OSS4-INDUSTRY-EXPO",ymd.format(dmy.parse(calDate)),  getOrgnBrCode().equalsIgnoreCase("90") ?"0A" : getOrgnBrCode(), new BigDecimal(arr[0]));
                    if(!industryList.isEmpty()) {
                        jrxmlPojo.setIndustryWise(new JRBeanCollectionDataSource(industryList));
                    }
                }
            } else if(reportType.equalsIgnoreCase("oss4G")){
                reportName = "SOSS4 Part G / OSS 6 / XBRL OSS 6 ";
                /*Part-G*/
//                System.out.println("Soss4 Part G:"+new Date());
                List<Oss4PartGPojo> partGList = ossBeanPartDRemote.getSOS4PartG(ymd.format(dmy.parse(calDate)), getOrgnBrCode(), new BigDecimal(arr[0]));
                for (int i = 0; i < partGList.size(); i++) {
                    Oss4PartGPojo partGPojo = partGList.get(i);
                    List<Oss4PartGMgmtPojo> mgmtPojo = partGPojo.getOss4PartGMgmtList();
                    List<Oss4PartGUnSecAdvPojo> unSecAdvPojo = partGPojo.getOss4PartGUnSecAdvList();
                    List<Oss4PartGExpPojo> expPojo = partGPojo.getOss4PartGExpList();
                    List<Oss4PartGSecAdvPojo> secAdvPojo = partGPojo.getOss4PartGSecAdvList();

                    jrxmlPojo.setPartGMgmtPojo(new JRBeanCollectionDataSource(mgmtPojo));
                    jrxmlPojo.setPartGUnSecAdvPojo(new JRBeanCollectionDataSource(unSecAdvPojo));
                    jrxmlPojo.setPartGExpPojo(new JRBeanCollectionDataSource(expPojo));
                    jrxmlPojo.setPartGSecAdvPojo(new JRBeanCollectionDataSource(secAdvPojo));
                }
                for (int k = 0; k < jrxmlList.size(); k++) {
                    Oss4JrxmlPojo p = jrxmlList.get(k);
                    List<Oss4PartGUnSecAdvPojo> unSecList = (List<Oss4PartGUnSecAdvPojo>) p.getPartGUnSecAdvPojo().getData();
                    for (int z = 0; z < unSecList.size(); z++) {
                        Oss4PartGUnSecAdvPojo unSecPojo = unSecList.get(z);
//                        System.out.println("Rel-Name--->" + unSecPojo.getRelName());
                    }
                }
            }
            jrxmlList.add(jrxmlPojo);
            /*Report Checking*/
            
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("username", getUserName());
            fillParams.put("pAmtIn", arr[1]);
            fillParams.put("report", reportName);
            fillParams.put("pPrintedDate", dmy.format(dmy.parse(calDate)));
            fillParams.put("pDateOfFormation", dmy.format(new Date()));
            /*Report Printing*/
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            if(reportType.equalsIgnoreCase("oss4A")){
                new ReportBean().downloadPdf("OSS4_Part_A_" + ymd.format(dmy.parse(calDate)), "soss4Cover",
                        new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if(reportType.equalsIgnoreCase("oss4B")){
                new ReportBean().downloadPdf("OSS4_Part_B_" + ymd.format(dmy.parse(calDate)), "soss4Cover",
                        new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if(reportType.equalsIgnoreCase("oss4C")){
                new ReportBean().downloadPdf("OSS4_Part_C_" + ymd.format(dmy.parse(calDate)), "soss4Cover",
                        new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if (reportType.equalsIgnoreCase("oss4D")) {
                new ReportBean().downloadPdf("OSS4_Part_D_" + ymd.format(dmy.parse(calDate)), "soss5Cover",
                    new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if (reportType.equalsIgnoreCase("oss4E")) {
                new ReportBean().downloadPdf("OSS4_Part_E_" + ymd.format(dmy.parse(calDate)), "soss5Cover",
                    new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if (reportType.equalsIgnoreCase("oss4F")) {
                new ReportBean().downloadPdf("OSS4_Part_F_" + ymd.format(dmy.parse(calDate)), "soss5Cover",
                    new JRBeanCollectionDataSource(jrxmlList), fillParams);
//                new ReportBean().downloadPdf("OSS4_Part_F_" + ymd.format(dmy.parse(calDate)), 
//                        "all_rbi_report_master_OSS2", new JRBeanCollectionDataSource(resultList), fillParams);
            }  if (reportType.equalsIgnoreCase("oss4Indstry")) {
                new ReportBean().downloadPdf("OSS4_Part_Indstry_" + ymd.format(dmy.parse(calDate)), "soss5Cover",
                    new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if(reportType.equalsIgnoreCase("oss4G")) {
                new ReportBean().downloadPdf("OSS4_Part_G_" + ymd.format(dmy.parse(calDate)), "soss6Cover",
                    new JRBeanCollectionDataSource(jrxmlList), fillParams);
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMsg("");
        this.setReportType("");
        this.setCalDate(getTodayDate());
        this.setReportIn("R");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
