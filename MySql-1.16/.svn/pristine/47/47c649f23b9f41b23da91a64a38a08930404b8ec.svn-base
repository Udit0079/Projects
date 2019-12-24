/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.RbiSos4Pojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.SortedByAcStatus;
import com.cbs.dto.report.Statement8PoJo;
import com.cbs.dto.report.Statement9PoJo;
import com.cbs.dto.report.ho.Oss7BusinessPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OverDueReportFacadeRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeHalfYearlyRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.StmtOneToTenFacadeRemote;
import com.cbs.pojo.Oss4PartACombinedPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.Oss4JrxmlPojo;
import com.cbs.web.pojo.ho.Oss7JrxmlPojo;
import com.cbs.web.pojo.loan.ComparatorForAcno;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author saurabhsipl
 */
public class StmtOneToTenCtrl extends BaseBean {
    private String message;
    private String reportType;
    private String firstAltDt;
    private String reportIn;
    private String secondAltDt;
    private String thirdAltDt;
    private Integer fridayListSize;
    private String reportFormat;
    private List<SelectItem> reportFormatIn;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> reportInList;
    private final String jndiHomeName = "HoReportFacade";
    private final String jndiGLReprtName = "GLReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private GLReportFacadeRemote GLRemote = null;
    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;
    private final String monthlyJndiName = "RbiMonthlyReportFacade";
    private RbiMonthlyReportFacadeRemote monthlyRemote = null;
    private RbiReportFacadeRemote ossBeanRemote;
    private LoanReportFacadeRemote loanRemote = null;
    private OverDueReportFacadeRemote overDueRemote = null;
    private RbiReportFacadeHalfYearlyRemote halfYearly;
    private StmtOneToTenFacadeRemote stmt;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    
    public StmtOneToTenCtrl() {
        reportTypeList = new ArrayList<SelectItem>();
        reportInList = new ArrayList<SelectItem>();
        reportFormatIn = new ArrayList<SelectItem>();
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            GLRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiGLReprtName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            monthlyRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup(monthlyJndiName);
            ossBeanRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            halfYearly = (RbiReportFacadeHalfYearlyRemote) ServiceLocator.getInstance().lookup("RbiReportFacadeHalfYearly");
            stmt = (StmtOneToTenFacadeRemote) ServiceLocator.getInstance().lookup("StmtOneToTenFacade");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            overDueRemote = (OverDueReportFacadeRemote) ServiceLocator.getInstance().lookup("OverDueReportFacade");

            reportTypeList.add(new SelectItem("one", "STATEMENT -1"));
            reportTypeList.add(new SelectItem("two", "STATEMENT -2"));
            reportTypeList.add(new SelectItem("three", "STATEMENT -3"));
            reportTypeList.add(new SelectItem("four", "STATEMENT -4"));
            reportTypeList.add(new SelectItem("five", "STATEMENT -5"));
            reportTypeList.add(new SelectItem("six", "STATEMENT -6"));
            reportTypeList.add(new SelectItem("seven", "STATEMENT -7"));
            reportTypeList.add(new SelectItem("eight", "STATEMENT -8"));
            reportTypeList.add(new SelectItem("nine", "STATEMENT -9"));
            reportTypeList.add(new SelectItem("ten", "STATEMENT -10"));

            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "THOUSANDS"));
            reportInList.add(new SelectItem("L", "LACS"));
            reportInList.add(new SelectItem("C", "CRORES"));

            reportFormatIn.add(new SelectItem("N", "In Detail"));
            reportFormatIn.add(new SelectItem("Y", "In Summary"));

            btnRefreshAction();
            firstAltDt = "";
            secondAltDt = "";
            thirdAltDt = "";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void downloadPdf() {
        this.setMessage("");
        try {
            String reportName = "", reportName1 = "";
            Map fillParams = new HashMap();
            if(this.firstAltDt.equalsIgnoreCase("")){
                setMessage("Please Fill the Date First!!!");
            }
            List<String> dates = new ArrayList<String>();
            
            String preDt =ossBeanRemote.getMinFinYear(CbsUtil.dateAdd(ymd.format(dmy.parse(this.firstAltDt)) ,-1));
            String prePreDt =ossBeanRemote.getMinFinYear(CbsUtil.dateAdd(preDt,-1));
            dates.add(CbsUtil.dateAdd(prePreDt, -1));
            dates.add(CbsUtil.dateAdd(preDt, -1));
            dates.add(ymd.format(dmy.parse(this.firstAltDt)));
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());            
            fillParams.put("pPrintedDate", dmy.format(CbsUtil.getLastDateOfMonth(dmy.parse(this.firstAltDt))));
            String[] arr = Util.getReportOptionAndDescription(this.reportIn);
            fillParams.put("pAmtIn", arr[1]);
            fillParams.put("username", getUserName());
            fillParams.put("pFAltDate", dmy.format(ymd.parse(dates.get(0))));
            fillParams.put("pSAltDate", dmy.format(ymd.parse(dates.get(1))));
            fillParams.put("pTAltDate", dmy.format(ymd.parse(dates.get(2))));
            reportName = "STATEMENT ONE TO TEN";
            
            if (this.reportType.equalsIgnoreCase("one")) {
                //***** STATEMENT ONE ********//
                reportName1 = "STATEMENT ONE";
                fillParams.put("report", reportName1);
                List<RbiSossPojo> resultList = stmt.getStaementTwo(dates, new BigDecimal(arr[0]), "STMT1",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
                if (!resultList.isEmpty()) {
                    new ReportBean().openPdf("STATEMENT_ONE_", "stmt1",new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                }
            } else if (this.reportType.equalsIgnoreCase("two")) {
                //***** STATEMENT TWO ********//
                reportName1 = "STATEMENT TWO";
                List<RbiSossPojo> resultList = stmt.getStaementTwo(dates, new BigDecimal(arr[0]), "STMT2",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
//                List<RbiSossPojo> resultList = hoRemote.getBalanceSheet("STMT2", dates, "0A", new BigDecimal(arr[0]), this.reportFormat);
                fillParams.put("report", reportName1);
                if (!resultList.isEmpty()) {
                    new ReportBean().openPdf("STATEMENT_TWO_", "stmt2",
                            new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                }
            } else if (this.reportType.equalsIgnoreCase("three")) {
                //***** STATEMENT THREE ********//
                reportName1 = "STATEMENT THREE";
                dates= new ArrayList<String>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                fillParams.put("pFAltDate", dmy.format(ymd.parse(dates.get(0))));
                fillParams.put("report", reportName1);
                List<NpaAccountDetailPojo> resultList = overDueRemote.getNpaDetail("ALL", "ALL", dates.get(0),"0A","Y");
                BigDecimal reptOpt = new BigDecimal(arr[0]);
                List<NpaAccountDetailPojo> dataList = new ArrayList<NpaAccountDetailPojo>();
                for(int k=0;k<resultList.size();k++){
                    NpaAccountDetailPojo pojo = new NpaAccountDetailPojo();
                    pojo.setAcNo(resultList.get(k).getCustName().concat("\n").concat(resultList.get(k).getAcNo()));
                    pojo.setTypeOfLoan(resultList.get(k).getTypeOfLoan());
                    pojo.setAcStatus(resultList.get(k).getAcStatus());
                    pojo.setSancDate(resultList.get(k).getSancDate());
                    if(resultList.get(k).getSancAmt()!= new BigDecimal("0")){
                        pojo.setSancAmt(resultList.get(k).getSancAmt().divide(reptOpt));
                    } else {
                        pojo.setSancAmt(new BigDecimal("0"));
                    }
                    if(resultList.get(k).getOutstdBal() != new BigDecimal("0")) {
                        pojo.setPrincAmt(resultList.get(k).getOutstdBal().divide(reptOpt));
                    } else {
                        pojo.setPrincAmt(new BigDecimal("0"));
                    }
                    if(resultList.get(k).getIntAmt() != new BigDecimal("0")) {
                        pojo.setNpaAmt(resultList.get(k).getIntAmt().divide(reptOpt));
                    } else {
                        pojo.setNpaAmt(new BigDecimal("0"));
                    }
                    pojo.setNpaDate(resultList.get(k).getNpaDate());
                    pojo.setSecNat(resultList.get(k).getSecNat());
                    if(resultList.get(k).getSecVal() != new BigDecimal("0")){
                        pojo.setSecVal(resultList.get(k).getSecVal().divide(reptOpt));
                    } else {
                        pojo.setSecVal(new BigDecimal("0"));
                    }
                    if(resultList.get(k).getSecVal() != new BigDecimal("0")){
                        pojo.setProvReq(resultList.get(k).getProvAmt().divide(reptOpt));
                        pojo.setProvAmt(resultList.get(k).getProvAmt().divide(reptOpt));
                        pojo.setProvPerc(resultList.get(k).getProvPerc());
                    } else {
                        pojo.setProvAmt(new BigDecimal("0"));
                        pojo.setProvReq(new BigDecimal("0"));
                        pojo.setProvPerc(new BigDecimal("0"));
                    }
                    pojo.setNature(resultList.get(k).getNature());
                    pojo.setCurrentStatus(resultList.get(k).getCurrentStatus());
                    dataList.add(pojo);
                }
                ComparatorChain chain = new ComparatorChain();               
                chain.addComparator(new ComparatorForAcno());
                chain.addComparator(new SortedByAcStatus());
                
                Collections.sort(dataList, chain);
                
                new ReportBean().openPdf("STATEMENT_THREE_",
                        "STMT3", new JRBeanCollectionDataSource(dataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpRequest().getSession();
                httpSession.setAttribute("ReportName", reportName);
            } else if (this.reportType.equalsIgnoreCase("four")) {
                //***** STATEMENT FOUR ********//
                reportName1 = "STATEMENT FOUR";
                fillParams.put("report", reportName1);
                List<Oss4JrxmlPojo> jrxmlList = new ArrayList<Oss4JrxmlPojo>();     //Actual List To Print
                Oss4JrxmlPojo jrxmlPojo = new Oss4JrxmlPojo();
                List<Oss4PartACombinedPojo> partAList = stmt.getStaementFourPartAAndB(dates, new BigDecimal(arr[0]), "STMT4-PART-A", "0A",reportIn, reportFormat);
                if (!partAList.isEmpty()) {
                    Oss4PartACombinedPojo partAPojo = partAList.get(0);
                    jrxmlPojo.setPartAi(new JRBeanCollectionDataSource(partAPojo.getRbiPojoTable()));  //Part-A-I
                    jrxmlPojo.setPartAii(new JRBeanCollectionDataSource(partAPojo.getS4List()));       //Part-A-II
                }
                List<RbiSossPojo> partCList= stmt.getStaementTwo(dates, new BigDecimal(arr[0]), "STMT4-PART-C", getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
                List<RbiSossPojo> partDList= stmt.getStaementTwo(dates, new BigDecimal(arr[0]), "STMT4-PART-D", getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
                List<RbiSossPojo> partEList= stmt.getStaementTwo(dates, new BigDecimal(arr[0]), "STMT4-PART-E", getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
                jrxmlPojo.setPartB4ImpList(new JRBeanCollectionDataSource(partCList));
                jrxmlPojo.setPartB4SanList(new JRBeanCollectionDataSource(partDList));
                jrxmlPojo.setPartCiImpList(new JRBeanCollectionDataSource(partEList));
                jrxmlList.add(jrxmlPojo);
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().downloadPdf("STATEMENT_FOUR", "stmt4Cover",new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if (this.reportType.equalsIgnoreCase("five")){
                //***** STATEMENT FIVE ********//
                reportName1 = "STATEMENT FIVE";
                dates= new ArrayList<String>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                fillParams.put("pFAltDate", dmy.format(ymd.parse(dates.get(0))));
                fillParams.put("report", reportName1);
                List<Oss4JrxmlPojo> jrxmlList = new ArrayList<Oss4JrxmlPojo>();     //Actual List To Print
                Oss4JrxmlPojo jrxmlPojo = new Oss4JrxmlPojo();
                List<RbiSos4Pojo> partAList = stmt.getStaementFivePartA(dates,new BigDecimal(arr[0]), "STMT5_PART_A", "0A",reportIn, reportFormat);
                List<RbiSos4Pojo> partBList = stmt.getStaementFive(dates,new BigDecimal(arr[0]), "STMT5_PART_B", "0A",reportIn, reportFormat);
                if (!partAList.isEmpty()) {
                    jrxmlPojo.setPartD(new JRBeanCollectionDataSource(partAList));
                }
                if(reportFormat.equalsIgnoreCase("Y")) {
                    if (!partBList.isEmpty()) {
                        jrxmlPojo.setPartE(new JRBeanCollectionDataSource(partBList));
                    }
                }
                jrxmlList.add(jrxmlPojo);
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                if (reportFormat.equalsIgnoreCase("Y")) {
                    new ReportBean().downloadPdf("Statement_Five", "stmt5Cover",new JRBeanCollectionDataSource(jrxmlList), fillParams); 
                } else {
                    new ReportBean().downloadPdf("Statement_Five", "StmtPartADetail",new JRBeanCollectionDataSource(partAList), fillParams); 
                }
            } else if (this.reportType.equalsIgnoreCase("six")){
                //***** STATEMENT SIX ********//
                dates = new ArrayList<String>();
                String prevDate = ossBeanRemote.getMinFinYear(ymd.format(dmy.parse(this.firstAltDt)));
                dates.add(CbsUtil.dateAdd(prevDate, -1));
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                fillParams.put("pPrevDate",dmy.format(ymd.parse(dates.get(0))));
                fillParams.put("pCurDate", dmy.format(ymd.parse(dates.get(1))));
                reportName1 = "STATEMENT SIX";
                fillParams.put("report", reportName1);
                List<RbiSos4Pojo> resultList = stmt.getStaementSix(dates, new BigDecimal(arr[0]), "STMT6",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().openPdf("Statement_Six", "stmt6",new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", reportName);
            } else if (this.reportType.equalsIgnoreCase("seven")){
                //***** STATEMENT SEVEN ********//
                reportName1 = "STATEMENT SEVEN";
                dates= new ArrayList<String>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                fillParams.put("pFAltDate", dmy.format(ymd.parse(dates.get(0))));
                fillParams.put("report", reportName1);                
                List<LoanMisCellaniousPojo> resultList = stmt.getStaementSeven(dates, new BigDecimal(arr[0]), "",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
                new ReportBean().downloadPdf("Statement_Seven", "stmt7", new JRBeanCollectionDataSource(resultList), fillParams);
            } else if (this.reportType.equalsIgnoreCase("eight")){
                //***** STATEMENT EIGHT ********//
                reportName1 = "STATEMENT EIGHT";
                fillParams.put("report", reportName1);
                dates= new ArrayList<String>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                fillParams.put("pFAltDate", dmy.format(ymd.parse(dates.get(0))));
                List<Statement8PoJo> resultList = stmt.getStaementEight(dates, new BigDecimal(arr[0]), "Customer Id",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);                
                new ReportBean().downloadPdf("Statement_Eight", "stmt8", new JRBeanCollectionDataSource(resultList), fillParams);
            } else if (this.reportType.equalsIgnoreCase("nine")) {
                //***** STATEMENT NINE ********//
                reportName1 = "STATEMENT NINE";
                fillParams.put("report", reportName1);
                dates= new ArrayList<String>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                fillParams.put("pFAltDate", dmy.format(ymd.parse(dates.get(0))));
                List<Statement9PoJo> resultList = stmt.getStaementNine(dates, new BigDecimal(arr[0]), "STMT9",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);                
                
                 new ReportBean().downloadPdf("Statement_Nine", "stmt9", new JRBeanCollectionDataSource(resultList), fillParams);
            } else if (this.reportType.equalsIgnoreCase("ten")){
                //***** STATEMENT TEN ********//
                dates= new ArrayList<String>();
                dates.add(CbsUtil.dateAdd(preDt, -1));
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                List<Oss7BusinessPojo> resultList = stmt.getStaementTen(dates, new BigDecimal(arr[0]), "STMT1",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
                if (resultList.isEmpty()) {
                    this.setMessage("There is no data to print the report.");
                    return;
                }
                fillParams.put("pSAltDate", dmy.format(ymd.parse(dates.get(0))));
                fillParams.put("pTAltDate", dmy.format(ymd.parse(dates.get(1))));
                reportName1 ="STATEMENT TEN";
                fillParams.put("report", reportName1);
                List<Oss7JrxmlPojo> jrxmlList = new ArrayList<Oss7JrxmlPojo>();
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
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().openPdf("Statement_Ten" , "stmt10Cover",new JRBeanCollectionDataSource(jrxmlList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", reportName);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }
    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("one");
        this.setFirstAltDt(getTodayDate());
        this.setReportIn("R");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public String getFirstAltDt() {
        return firstAltDt;
    }

    public void setFirstAltDt(String firstAltDt) {
        this.firstAltDt = firstAltDt;
    }

    public Integer getFridayListSize() {
        return fridayListSize;
    }

    public void setFridayListSize(Integer fridayListSize) {
        this.fridayListSize = fridayListSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getSecondAltDt() {
        return secondAltDt;
    }

    public void setSecondAltDt(String secondAltDt) {
        this.secondAltDt = secondAltDt;
    }

    public String getThirdAltDt() {
        return thirdAltDt;
    }

    public void setThirdAltDt(String thirdAltDt) {
        this.thirdAltDt = thirdAltDt;
    }
    
    
}
