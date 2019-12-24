/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.FormNinePojo;
import com.cbs.dto.report.FormOnePojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.Form1UnencumAppSecPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeHalfYearlyRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.RbiSoss1And2ReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

/**
 *
 * @author root
 */
public class Form1Controller extends BaseBean {

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
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote ;
    private RbiReportFacadeHalfYearlyRemote halfYearly;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getFirstAltDt() {
        return firstAltDt;
    }

    public void setFirstAltDt(String firstAltDt) {
        this.firstAltDt = firstAltDt;
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

    public Integer getFridayListSize() {
        return fridayListSize;
    }

    public void setFridayListSize(Integer fridayListSize) {
        this.fridayListSize = fridayListSize;
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

    /**
     * Creates a new instance of Form1Controller
     */
    public Form1Controller() {
        reportTypeList = new ArrayList<SelectItem>();
        reportInList = new ArrayList<SelectItem>();
        reportFormatIn = new ArrayList<SelectItem>();
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            GLRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiGLReprtName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            monthlyRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup(monthlyJndiName);
            ossBeanRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            rbiSoss1And2Remote = (RbiSoss1And2ReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiSoss1And2ReportFacade");
            halfYearly = (RbiReportFacadeHalfYearlyRemote) ServiceLocator.getInstance().lookup("RbiReportFacadeHalfYearly");

            reportTypeList.add(new SelectItem("one", "FORM-1"));
            reportTypeList.add(new SelectItem("partC", "Part C Unencumbered Approved Securities /XBRL_FORM1_Part_C"));
            reportTypeList.add(new SelectItem("nine", "FORM-9"));
            reportTypeList.add(new SelectItem("ma", "MONETARY AGGREGAATES"));
            reportTypeList.add(new SelectItem("XB1", "XBRL FORM 1"));
            reportTypeList.add(new SelectItem("XB9", "XBRL FORM 9"));

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

    public void processOnFriday() {
        this.setMessage("");
        try {
            if (this.reportType.equalsIgnoreCase("one")) {
                String msg = validateFirstAltFridayDt(this.firstAltDt);
                if (!msg.equalsIgnoreCase("true")) {
                    this.setMessage(msg);
                    return;
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateFirstAltFridayDt(String fAltDt) {
        String msg = "true";
        try {
            if (this.firstAltDt == null || this.firstAltDt.equals("")) {
                return "Please fill First Alternate Friday.";
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.firstAltDt);
            if (result == false) {
                return "Please fill correct First Alternate Friday.";
            }
            boolean isFriday = hoRemote.isFridayDate(ymd.format(dmy.parse(this.firstAltDt)));
            if (!isFriday) {
                return "Please fill correct First Alternate Friday.";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return msg;
    }

    public void generateFridayDate() {
        this.setMessage("");
        secondAltDt = "";
        thirdAltDt = "";
        try {
            fridayListSize = 1;
            Date lastMonthDtOfFriday = CbsUtil.getLastDateOfMonth(dmy.parse(this.firstAltDt));
            List tempFridayList = hoRemote.getAllAlternateFriday(ymd.format(dmy.parse(this.firstAltDt)), ymd.format(lastMonthDtOfFriday));
            if (!tempFridayList.isEmpty()) {
                Integer tempListSize = tempFridayList.size();
                fridayListSize = fridayListSize + tempListSize;
                if (tempListSize == 1) {
                    Vector element = (Vector) tempFridayList.get(0);
                    secondAltDt = element.get(0).toString();
                } else if (tempListSize == 2) {
                    for (int i = 0; i < tempFridayList.size(); i++) {
                        Vector element = (Vector) tempFridayList.get(i);
                        if (i == 0) {
                            secondAltDt = element.get(0).toString();
                        } else if (i == 1) {
                            thirdAltDt = element.get(0).toString();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void downloadPdf() {
        this.setMessage("");
        try {
            String reportName = "", reportName1 = "";

            Map fillParams = new HashMap();
            if (this.reportType.equalsIgnoreCase("one")) {
                /*Form1 Processing*/
                String msg = validateFirstAltFridayDt(this.firstAltDt);
                if (!msg.equalsIgnoreCase("true")) {
                    this.setMessage(msg);
                    return;
                }
                generateFridayDate();
                //It is report printing.
                reportName1 = "Form I-Statements of compliance with CRR & SLR";
                reportName = "formOneReport";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("username", getUserName());
                fillParams.put("report", reportName1);
                fillParams.put("pPrintedDate", dmy.format(CbsUtil.getLastDateOfMonth(dmy.parse(this.firstAltDt))));
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                fillParams.put("pAmtIn", arr[1]);
                fillParams.put("pFAltDate", this.firstAltDt);

                List<String> dates = new ArrayList<String>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));

                if (this.secondAltDt != null && !(this.secondAltDt.equalsIgnoreCase(""))) {
                    fillParams.put("pSAltDate", dmy.format(ymd.parse(this.secondAltDt)));
                    dates.add(this.secondAltDt);
                } else {
                    fillParams.put("pSAltDate", "");
                }
                if (this.thirdAltDt != null && !(this.thirdAltDt.equalsIgnoreCase(""))) {
                    fillParams.put("pTAltDate", dmy.format(ymd.parse(this.thirdAltDt)));
                    dates.add(this.thirdAltDt);
                } else {
                    fillParams.put("pTAltDate", "");
                }
//                System.out.println("Start Form1:"+new Date());
                List<RbiSossPojo> resultList = monthlyRemote.getForm1DetailsAsPerGlb(dates, new BigDecimal(arr[0]), "FORM1",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
//                System.out.println("End Form1:"+new Date());
                if (!resultList.isEmpty()) {
                    for (RbiSossPojo pojo : resultList) {
                        System.out.println(pojo.getDescription());
                    }
//                    new ReportBean().downloadPdf("Form1_" + ymd.format(dmy.parse(this.firstAltDt)), "all_rbi_report_master_form1", 
//                            new JRBeanCollectionDataSource(resultList), fillParams);
                    new ReportBean().openPdf("Form1_" + ymd.format(dmy.parse(this.firstAltDt)), "all_rbi_report_master_form1",
                            new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                }
            } else if (this.reportType.equalsIgnoreCase("nine")) {
                /*Form9 Processing*/
                reportName1 = "FORM IX-Assets and Liabilities as on last Friday of the month";
                reportName = "formNineReport";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("username", getUserName());
                fillParams.put("report", reportName1);
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                fillParams.put("pAmtIn", arr[1]);
                fillParams.put("pPrintedDate", dmy.format(dmy.parse(this.firstAltDt)));
                List<String> dates = new ArrayList<>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);

                List<RbiSossPojo> resultList = rbiSoss1And2Remote.getSOSS2("FORM9", ymd.format(dmy.parse(this.firstAltDt)), getOrgnBrCode(), new BigDecimal(arr[0]), reportFormat,osBlancelist,"0");
                if (!resultList.isEmpty()) {
//                    new ReportBean().downloadPdf("Form9_" + ymd.format(dmy.parse(this.firstAltDt)), "all_rbi_report_master", 
//                            new JRBeanCollectionDataSource(resultList), fillParams);
                    new ReportBean().openPdf("Form9_" + ymd.format(dmy.parse(this.firstAltDt)), "all_rbi_report_master",
                            new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                }
            } else if (this.reportType.equalsIgnoreCase("ma")) {
                /*Monetary_Aggregates Processing*/
                reportName1 = "Monetary Aggregates";
                reportName = "formNineReport";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("username", getUserName());
                fillParams.put("report", reportName1);
                fillParams.put("pPrintedDate", this.firstAltDt);
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                fillParams.put("pAmtIn", arr[1]);
                List<String> dates = new ArrayList<>();
                dates.add(ymd.format(dmy.parse(firstAltDt)));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);
                List<RbiSossPojo> resultList = rbiSoss1And2Remote.getSOSS2("MON_AGG", ymd.format(dmy.parse(this.firstAltDt)), getOrgnBrCode(), new BigDecimal(arr[0]), reportFormat,osBlancelist,"0");
                if (!resultList.isEmpty()) {
//                    new ReportBean().downloadPdf("Monetary_Aggregates_" + ymd.format(dmy.parse(this.firstAltDt)),
//                            "all_rbi_report_master", new JRBeanCollectionDataSource(resultList), fillParams);

                    new ReportBean().openPdf("Monetary_Aggregates_" + ymd.format(dmy.parse(this.firstAltDt)),
                            "all_rbi_report_master", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                }
            }else if (this.reportType.equalsIgnoreCase("partC")) {
                /*Form9 Processing*/
                reportName1 = "Part C Compliance with Section 24 XII. c) Unencumbered Approved Securities -(Specify)/XBRL_FORM1_Part_C";
                String report = "Form 1 - Statement of compliance with CRR & SLR/XBRL FORM1 Part C";
                reportName = "formNineReport";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("username", getUserName());
                fillParams.put("report", report);
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                fillParams.put("pAmtIn", arr[1]);
                fillParams.put("pPrintedDate", dmy.format(dmy.parse(this.firstAltDt)));

                List<Form1UnencumAppSecPojo> resultList = halfYearly.getUnencumberedApprovedSecurities(ymd.format(dmy.parse(this.firstAltDt)), new BigDecimal(arr[0]));
                if (!resultList.isEmpty()) {
                    new ReportBean().openPdf("FORM1_Part_C_Unencumbered_Approved_Security_XBRL_FORM1_Part_C" + ymd.format(dmy.parse(this.firstAltDt)), "PartCUnEncumberedAppSecurity",
                            new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                }
            } else if (this.reportType.equalsIgnoreCase("XB9")) {
                /*XBRL FORM 9 Processing*/
                reportName1 = "XBRL FORM 9";
                reportName = "formNineReport";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("username", getUserName());
                fillParams.put("report", reportName1);
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                fillParams.put("pAmtIn", arr[1]);
                fillParams.put("pPrintedDate", dmy.format(dmy.parse(this.firstAltDt)));                
                List<String> dates = new ArrayList<>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);
                List<RbiSossPojo> resultList = rbiSoss1And2Remote.getSOSS2("XBRLFORM9", ymd.format(dmy.parse(this.firstAltDt)), getOrgnBrCode(), new BigDecimal(arr[0]), reportFormat,osBlancelist,"0");
                if (!resultList.isEmpty()) {
                    new ReportBean().openPdf("XBRL_FORM9_" + ymd.format(dmy.parse(this.firstAltDt)), "all_rbi_report_master",
                            new JRBeanCollectionDataSource(resultList), fillParams);
                }                
            } else if (this.reportType.equalsIgnoreCase("XB1")) {
                /*Form1 Processing*/
                String msg = validateFirstAltFridayDt(this.firstAltDt);
                if (!msg.equalsIgnoreCase("true")) {
                    this.setMessage(msg);
                    return;
                }
                generateFridayDate();
                //It is report printing.
                reportName1 = "XBRL FORM 1";
                reportName = "formOneReport";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("username", getUserName());
                fillParams.put("report", reportName1);
                fillParams.put("pPrintedDate", dmy.format(CbsUtil.getLastDateOfMonth(dmy.parse(this.firstAltDt))));
                String[] arr = Util.getReportOptionAndDescription(this.reportIn);
                fillParams.put("pAmtIn", arr[1]);
                fillParams.put("pFAltDate", this.firstAltDt);
                List<String> dates = new ArrayList<String>();
                dates.add(ymd.format(dmy.parse(this.firstAltDt)));
                if (this.secondAltDt != null && !(this.secondAltDt.equalsIgnoreCase(""))) {
                    fillParams.put("pSAltDate", dmy.format(ymd.parse(this.secondAltDt)));
                    dates.add(this.secondAltDt);
                } else {
                    fillParams.put("pSAltDate", "");
                }
                if (this.thirdAltDt != null && !(this.thirdAltDt.equalsIgnoreCase(""))) {
                    fillParams.put("pTAltDate", dmy.format(ymd.parse(this.thirdAltDt)));
                    dates.add(this.thirdAltDt);
                } else {
                    fillParams.put("pTAltDate", "");
                }
                List<RbiSossPojo> resultList = monthlyRemote.getForm1DetailsAsPerGlb(dates, new BigDecimal(arr[0]), "XBRLFORM1",
                        getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), this.reportIn, this.reportFormat);
                if (!resultList.isEmpty()) {
                    for (RbiSossPojo pojo : resultList) {
                        System.out.println(pojo.getDescription());
                    }
                    new ReportBean().openPdf("XBRL_FORM1" + ymd.format(dmy.parse(this.firstAltDt)), "all_rbi_report_master_form1",
                            new JRBeanCollectionDataSource(resultList), fillParams);
                }
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", reportName);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    public List<BigDecimal> getFormulaRowAmount(List<FormOnePojo> tempDataList, Integer fGno, Integer fSGno,
            Integer fSSGno, Integer fSSSGno, Integer tempListSize, String formula) {
        List<BigDecimal> formulaAmtList = new ArrayList<BigDecimal>();
        BigDecimal fAmt = new BigDecimal("0");
        BigDecimal sAmt = new BigDecimal("0");
        BigDecimal tAmt = new BigDecimal("0");
        try {
            if (fSSGno != 0 && fSSSGno != 0) {
                for (int i = 0; i < tempDataList.size(); i++) {
                    FormOnePojo pojo = tempDataList.get(i);
                    if (pojo.getGno() == fGno && pojo.getSgno() == fSGno && pojo.getSsgno() == fSSGno && pojo.getSssgno() == fSSSGno) {
                        BigDecimal tempFAmt = pojo.getFirstAmount().multiply(new BigDecimal(formula));
                        fAmt = fAmt.add(tempFAmt);
                        if (tempListSize == 2) {
                            BigDecimal tempSAmt = pojo.getSecondAmount().multiply(new BigDecimal(formula));
                            sAmt = sAmt.add(tempSAmt);
                        } else if (tempListSize == 3) {
                            BigDecimal tempSAmt = pojo.getSecondAmount().multiply(new BigDecimal(formula));
                            sAmt = sAmt.add(tempSAmt);
                            BigDecimal tempTAmt = pojo.getThirdAmount().multiply(new BigDecimal(formula));
                            tAmt = tAmt.add(tempTAmt);
                        }
                    }
                }
                formulaAmtList.add(fAmt);
                formulaAmtList.add(sAmt);
                formulaAmtList.add(tAmt);
            } else if (fSSGno != 0 && fSSSGno == 0) {
                for (int i = 0; i < tempDataList.size(); i++) {
                    FormOnePojo pojo = tempDataList.get(i);
                    if (pojo.getGno() == fGno && pojo.getSgno() == fSGno && pojo.getSsgno() == fSSGno) {
                        BigDecimal tempFAmt = pojo.getFirstAmount() == null ? new BigDecimal("0") : pojo.getFirstAmount().multiply(new BigDecimal(formula));
                        fAmt = fAmt.add(tempFAmt);
                        if (tempListSize == 2) {
                            BigDecimal tempSAmt = pojo.getSecondAmount() == null ? new BigDecimal("0") : pojo.getSecondAmount().multiply(new BigDecimal(formula));
                            sAmt = sAmt.add(tempSAmt);
                        } else if (tempListSize == 3) {
                            BigDecimal tempSAmt = pojo.getSecondAmount() == null ? new BigDecimal("0") : pojo.getSecondAmount().multiply(new BigDecimal(formula));
                            sAmt = sAmt.add(tempSAmt);
                            BigDecimal tempTAmt = pojo.getThirdAmount() == null ? new BigDecimal("0") : pojo.getThirdAmount().multiply(new BigDecimal(formula));
                            tAmt = tAmt.add(tempTAmt);
                        }
                    }
                }
                formulaAmtList.add(fAmt);
                formulaAmtList.add(sAmt);
                formulaAmtList.add(tAmt);
            } else if (fSSGno == 0 && fSSSGno == 0) {
                for (int i = 0; i < tempDataList.size(); i++) {
                    FormOnePojo pojo = tempDataList.get(i);
                    if (pojo.getGno() == fGno && pojo.getSgno() == fSGno) {
                        BigDecimal tempFAmt = pojo.getFirstAmount() == null ? new BigDecimal("0") : pojo.getFirstAmount().multiply(new BigDecimal(formula));
                        fAmt = fAmt.add(tempFAmt);
                        if (tempListSize == 2) {
                            BigDecimal tempSAmt = pojo.getSecondAmount() == null ? new BigDecimal("0") : pojo.getSecondAmount().multiply(new BigDecimal(formula));
                            sAmt = sAmt.add(tempSAmt);
                        } else if (tempListSize == 3) {
                            BigDecimal tempSAmt = pojo.getSecondAmount() == null ? new BigDecimal("0") : pojo.getSecondAmount().multiply(new BigDecimal(formula));
                            sAmt = sAmt.add(tempSAmt);
                            BigDecimal tempTAmt = pojo.getThirdAmount() == null ? new BigDecimal("0") : pojo.getThirdAmount().multiply(new BigDecimal(formula));
                            tAmt = tAmt.add(tempTAmt);
                        }
                    }
                }
                formulaAmtList.add(fAmt);
                formulaAmtList.add(sAmt);
                formulaAmtList.add(tAmt);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return formulaAmtList;
    }

    public BigDecimal getFormulaRowNineAmount(List<FormNinePojo> tempDataList, Integer fGno, Integer fSGno, Integer fSSGno, Integer fSSSGno, String formula) {
        BigDecimal nineAmount = new BigDecimal("0");
        BigDecimal fAmt = new BigDecimal("0");
        try {
            if (fSSGno != 0 && fSSSGno != 0) {
                for (int i = 0; i < tempDataList.size(); i++) {
                    FormNinePojo pojo = tempDataList.get(i);
                    if (pojo.getGno() == fGno && pojo.getSgno() == fSGno && pojo.getSsgno() == fSSGno && pojo.getSssgno() == fSSSGno) {
                        nineAmount = pojo.getFirstAmount().multiply(new BigDecimal(formula));
                    }
                }
            } else if (fSSGno != 0 && fSSSGno == 0) {
                for (int i = 0; i < tempDataList.size(); i++) {
                    FormNinePojo pojo = tempDataList.get(i);
                    if (pojo.getGno() == fGno && pojo.getSgno() == fSGno && pojo.getSsgno() == fSSGno) {
                        BigDecimal tempFAmt = pojo.getFirstAmount() == null ? new BigDecimal("0") : pojo.getFirstAmount().multiply(new BigDecimal(formula));
                        fAmt = fAmt.add(tempFAmt);
                    }
                }
                nineAmount = fAmt;
            } else if (fSSGno == 0 && fSSSGno == 0) {
                for (int i = 0; i < tempDataList.size(); i++) {
                    FormNinePojo pojo = tempDataList.get(i);
                    if (pojo.getGno() == fGno && pojo.getSgno() == fSGno) {
                        BigDecimal tempFAmt = pojo.getFirstAmount() == null ? new BigDecimal("0") : pojo.getFirstAmount().multiply(new BigDecimal(formula));
                        fAmt = fAmt.add(tempFAmt);
                    }
                }
                nineAmount = fAmt;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return nineAmount;
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("one");
        this.setFirstAltDt(getTodayDate());
        this.setReportIn("L");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
