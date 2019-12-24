/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.SortedByTokenNo;
import com.cbs.dto.report.SortedByTrade;
import com.cbs.dto.report.StatemenrtOfRecoveriesPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
import com.cbs.pojo.SortedByAcTypeDeamanRecovery;
import com.cbs.pojo.SortedByOrder;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Spellar;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Admin
 */
public class DemandRecovery extends BaseBean {

    private String message;
    private String area;
    private List<SelectItem> areaList;
    private String optionType;
    private List<SelectItem> optionTypeList;
    private String repType;
    private List<SelectItem> repList;
    private String tillDate;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private boolean printButton;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private OtherLoanReportFacadeRemote RemoteLocal;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote ftsPosting;
    // private ShareReportFacadeRemote horfr;
    private final String jndiName = "PostalDetailFacade";
    private PostalDetailFacadeRemote remote = null;
    private List<StatemenrtOfRecoveriesPojo> reportList = new ArrayList<StatemenrtOfRecoveriesPojo>();

    public DemandRecovery() {
        try {
            //tillDate = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteLocal = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherLoanReportFacade");
            ftsPosting = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            remote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);

//            areaList = new ArrayList<SelectItem>();
//            areaList.add(new SelectItem("S", "--Select--"));
//            List result = common.getRefRecList("019");
//            if (!result.isEmpty()) {
//                for (int i = 0; i < result.size(); i++) {
//                    Vector ele = (Vector) result.get(i);
//                    areaList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
//                }
//            }

            if (ftsPosting.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("P")) {
                getBranchPlaceOfWorking();
            } else {
                getBranchPlaceOfWorking1();
            }

            monthList = new ArrayList<SelectItem>();
            //Set all months
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }

            optionTypeList = new ArrayList<SelectItem>();
            optionTypeList.add(new SelectItem("S", "--Select--"));
            optionTypeList.add(new SelectItem("AL", "All Account"));
            optionTypeList.add(new SelectItem("AT", "Account Type"));

            this.printButton = true;

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getBranchPlaceOfWorking1() {
        areaList = new ArrayList<SelectItem>();
        try {
            areaList.add(new SelectItem("--Select--"));
            List result = remote.getBranchPlaceOfWorking1(getOrgnBrCode());
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getBranchPlaceOfWorking() {
        areaList = new ArrayList<SelectItem>();
        try {
            areaList.add(new SelectItem("--Select--"));
            TreeSet<String> ts = remote.getBranchPlaceOfWorking(getOrgnBrCode());
            if (!ts.isEmpty()) {
                Iterator<String> it = ts.iterator();
                while (it.hasNext()) {
                    areaList.add(new SelectItem(it.next()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void optAction() {
        setMessage("");
        if (optionType.equalsIgnoreCase("AL")) {
            repList = new ArrayList<SelectItem>();
            repList.add(new SelectItem("Se", "--Select--"));
            repList.add(new SelectItem("De", "Detail"));
            repList.add(new SelectItem("Su", "Summary"));
        } else {
            this.printButton = true;
            repList = new ArrayList<SelectItem>();
            repList.add(new SelectItem("Se", "--Select--"));
            repList.add(new SelectItem("De", "Detail"));
            repList.add(new SelectItem("Su", "Summary"));
            //repList.add(new SelectItem("Atw", "Account Type Wise"));
        }
    }

    public void reportTypeAction() {
        if ((repType.equalsIgnoreCase("De") || repType.equalsIgnoreCase("Su")) && optionType.equalsIgnoreCase("AL")) {
            this.printButton = false;
        } else {
            this.printButton = true;
        }
    }

    public void viewReport() {
        setMessage("");
        String branchName = "", address = "";
        String report = "";

        try {
            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }

            if (this.month == null || this.month.equals("0")) {
                this.setMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setMessage("Please fill proper Year in numeric");
                return;
            }
            if (optionType.equalsIgnoreCase("S")) {
                setMessage("Please select Option Type !");
                return;
            }

            if (repType.equalsIgnoreCase("Se")) {
                setMessage("Please select Report Type !");
                return;
            }

            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            String tillDt = ymd.format(dmy.parse(tillDate));
            String year = tillDt.substring(0, 4);
            String month = CbsUtil.getMonthName(Integer.parseInt(tillDt.substring(4, 6)));
            Map fillParams = new HashMap();

            if (repType.equalsIgnoreCase("De")) {
                report = "Demand Recovery Detail";
            } else {
                report = "Demand Recovery Summary";
            }
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDt", this.tillDate);
            fillParams.put("pPrintedBy", this.getUserName());
            if (ftsPosting.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("RP")) {
                fillParams.put("pArea", this.area);
            } else {
                if (this.area.contains("P")) {
                    fillParams.put("pArea", "PAY BILL NO : " + this.area.substring(8));
                } else {
                    fillParams.put("pArea", "CHECK ROLL NO : " + this.area.substring(10));
                }
            }

            fillParams.put("pMonth", month + " / " + year);

            fillParams.put("pReportType", "text");

            reportList = RemoteLocal.getDemandRecoveriesData(area, tillDt, getOrgnBrCode(), repType, optionType, "");
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            double amt = 0d;
            for (int i = 0; i < reportList.size(); i++) {
                StatemenrtOfRecoveriesPojo val = reportList.get(i);
                amt = +val.getGrTotalAmt();
            }
            Spellar word = new Spellar();
            fillParams.put("pAmtInWord", word.spellAmount(amt).toUpperCase());
            if (optionType.equalsIgnoreCase("AT")) {
                ComparatorChain cnObj = new ComparatorChain();
                cnObj.addComparator(new SortedByAcTypeDeamanRecovery());
                Collections.sort(reportList, cnObj);
            } else {
                ComparatorChain cnObj = new ComparatorChain();
                cnObj.addComparator(new SortedByOrder());
                Collections.sort(reportList, cnObj);
            }

            if (area.contains("P")) {
                if (optionType.equalsIgnoreCase("Al")) {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByTrade());
                    Collections.sort(reportList, cnObj);
                }
            } else if (area.contains("C")) {
                if (optionType.equalsIgnoreCase("Al")) {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByTokenNo());
                    Collections.sort(reportList, cnObj);
                }
            }

//            for (int i = 0; i < reportList.size(); i++) {
//                StatemenrtOfRecoveriesPojo val = reportList.get(i);
//                System.out.println("Cust Id-->" + val.getCustId() + "Acno ->" + val.getLoanAcNo() + "name ->" + val.getCustName() + "father Name ->" + val.getFatherName() + "Out Amount->" + val.getOutStdBal());
//            }

            if (optionType.equalsIgnoreCase("Al")) {
                if (repType.equalsIgnoreCase("De")) {
                    new ReportBean().jasperReport("DemandRecovery_Detail", "text/html",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Demand Recovery");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                } else {
                    new ReportBean().jasperReport("DemandRecovery_Summary", "text/html",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Demand Recovery");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                }
            } else {
                if (repType.equalsIgnoreCase("De")) {
                    new ReportBean().jasperReport("DemandRecovery_Detail_AcType", "text/html",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Demand Recovery");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");


                } else {
                    new ReportBean().jasperReport("DemandRecovery_Detail_AcType_Summary", "text/html",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Demand Recovery");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {

        setMessage("");
        String branchName = "", address = "";
        String report = "";

        try {
            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }

            if (this.month == null || this.month.equals("0")) {
                this.setMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setMessage("Please fill proper Year in numeric");
                return;
            }
            if (optionType.equalsIgnoreCase("S")) {
                setMessage("Please select Option Type !");
                return;
            }

            if (repType.equalsIgnoreCase("Se")) {
                setMessage("Please select Report Type !");
                return;
            }

            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            String tillDt = ymd.format(dmy.parse(tillDate));
            String year = tillDt.substring(0, 4);
            String month = CbsUtil.getMonthName(Integer.parseInt(tillDt.substring(4, 6)));
            Map fillParams = new HashMap();

            if (repType.equalsIgnoreCase("De")) {
                report = "Demand Recovery Detail";
            } else {
                report = "Demand Recovery Summary";
            }
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDt", this.tillDate);
            fillParams.put("pPrintedBy", this.getUserName());
            if (this.area.contains("P")) {
                fillParams.put("pArea", "PAY BILL NO : " + this.area.substring(8));
            } else {
                fillParams.put("pArea", "CHECK ROLL NO : " + this.area.substring(10));
            }
            fillParams.put("pMonth", month + " / " + year);
            fillParams.put("pReportType", "pdf");

            reportList = RemoteLocal.getDemandRecoveriesData(area, tillDt, getOrgnBrCode(), repType, optionType, "");
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            double amt = 0d;
            for (int i = 0; i < reportList.size(); i++) {
                StatemenrtOfRecoveriesPojo val = reportList.get(i);
                amt = +val.getGrTotalAmt();
            }
            Spellar word = new Spellar();
            fillParams.put("pAmtInWord", word.spellAmount(amt).toUpperCase());
            if (optionType.equalsIgnoreCase("AT")) {
                ComparatorChain cnObj = new ComparatorChain();
                cnObj.addComparator(new SortedByAcTypeDeamanRecovery());
                Collections.sort(reportList, cnObj);
            }

            if (area.contains("P")) {
                if (optionType.equalsIgnoreCase("Al")) {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByTrade());
                    Collections.sort(reportList, cnObj);
                }
            } else if (area.contains("C")) {
                if (optionType.equalsIgnoreCase("Al")) {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByTokenNo());
                    Collections.sort(reportList, cnObj);
                }
            }

//            for (int i = 0; i < reportList.size(); i++) {
//                StatemenrtOfRecoveriesPojo val = reportList.get(i);
//               System.out.println("Sr No== " + val.getsNo() + "  Cust Id-->" + val.getCustId() + "Acno ->" + val.getLoanAcNo() + "name ->" + val.getCustName() + "father Name ->" + val.getFatherName() + "Out Amount->" + val.getOutStdBal());
//            }

            if (optionType.equalsIgnoreCase("Al")) {
                if (repType.equalsIgnoreCase("De")) {
                    new ReportBean().openPdf(area + "_Demand Recovery Detail_" + tillDt, "DemandRecovery_Detail", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                } else {
                    new ReportBean().openPdf(area + "_Demand Recovery Summary_" + tillDt, "DemandRecovery_Summary", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                }
            } else {
                if (repType.equalsIgnoreCase("De")) {
                    new ReportBean().openPdf(area + "_Demand Recovery Detail_" + tillDt, "DemandRecovery_Detail_AcType", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                } else {
                    new ReportBean().openPdf(area + "_Demand Recovery Summary_" + tillDt, "DemandRecovery_Detail_AcType_Summary", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                }
            }
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Demand Recovery");
        } catch (Exception ex) {
            ex.printStackTrace();
            setMessage(ex.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {
        setMessage("");
        FastReportBuilder fastReport = new FastReportBuilder();
        String reportName = "";
        try {
            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            String tillDt = ymd.format(dmy.parse(tillDate));
            if (repType.equalsIgnoreCase("Su")) {
                reportName = "Demand Recovery Summary Report";
                reportList = RemoteLocal.getDemandRecoveriesSummaryData(area, tillDt, getOrgnBrCode(), repType, optionType, "Excel");
            } else {
                reportName = "Demand Recovery Detail Report";
                reportList = RemoteLocal.getDemandRecoveriesData(area, tillDt, getOrgnBrCode(), repType, optionType, "Excel");
            }
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            if (!repType.equalsIgnoreCase("De")) {
                if (optionType.equalsIgnoreCase("AT")) {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByAcTypeDeamanRecovery());
                    Collections.sort(reportList, cnObj);
                } else {
                    ComparatorChain cnObj = new ComparatorChain();
                    cnObj.addComparator(new SortedByOrder());
                    Collections.sort(reportList, cnObj);
                }
                if (area.contains("P")) {
                    if (optionType.equalsIgnoreCase("Al")) {
                        ComparatorChain cnObj = new ComparatorChain();
                        cnObj.addComparator(new SortedByTrade());
                        Collections.sort(reportList, cnObj);
                    }
                } else if (area.contains("C")) {
                    if (optionType.equalsIgnoreCase("Al")) {
                        ComparatorChain cnObj = new ComparatorChain();
                        cnObj.addComparator(new SortedByTokenNo());
                        Collections.sort(reportList, cnObj);
                    }
                }
            }
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn personalTokenNo = ReportBean.getColumn("personalTokenNo", String.class, "P.Token No.", 150);
            AbstractColumn trade = ReportBean.getColumn("trade", String.class, "Trade", 150);
            AbstractColumn loanAcNo = ReportBean.getColumn("loanAcNo", String.class, "Account No.", 200);
            AbstractColumn flioNo = ReportBean.getColumn("flioNo", String.class, "Folio No.", 200);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name", 200);
            AbstractColumn outStdBal = ReportBean.getColumn("outStdBal", Double.class, "Outstanding Balance", 150);
            AbstractColumn prinAmt = ReportBean.getColumn("prinAmt", Double.class, "Principal Amount", 150);
            AbstractColumn intAmt = ReportBean.getColumn("intAmt", Double.class, "Interest Amount", 150);
            AbstractColumn theftFund = ReportBean.getColumn("theftFund", Double.class, "Thrift Fund", 150);
            AbstractColumn installmentAmt = ReportBean.getColumn("installmentAmt", Double.class, "Rd Installment Amount", 150);
            AbstractColumn loanEmiAmt = ReportBean.getColumn("loanEmiAmt", Double.class, "Loan-EMI", 150);
            AbstractColumn overDue = ReportBean.getColumn("overDue", Double.class, "OverDue Amount", 150);
//            //AbstractColumn lipAmt = ReportBean.getColumn("lipAmt", Double.class, "Lip Amount", 150);
            AbstractColumn totalAmt = ReportBean.getColumn("totalAmt", Double.class, "Total Amount", 150);


            fastReport.addColumn(personalTokenNo);
            width = width + personalTokenNo.getWidth();

            fastReport.addColumn(trade);
            width = width + trade.getWidth();

            fastReport.addColumn(loanAcNo);
            width = width + loanAcNo.getWidth();

            fastReport.addColumn(flioNo);
            width = width + flioNo.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(outStdBal);
            outStdBal.setStyle(style);
            width = width + 2 * outStdBal.getWidth();

            fastReport.addColumn(prinAmt);
            prinAmt.setStyle(style);
            width = width + 2 * prinAmt.getWidth();

            fastReport.addColumn(intAmt);
            intAmt.setStyle(style);
            width = width + 2 * intAmt.getWidth();

            fastReport.addColumn(theftFund);
            theftFund.setStyle(style);
            width = width + 2 * theftFund.getWidth();

            fastReport.addColumn(installmentAmt);
            installmentAmt.setStyle(style);
            width = width + 2 * installmentAmt.getWidth();

            fastReport.addColumn(loanEmiAmt);
            loanEmiAmt.setStyle(style);
            width = width + 2 * loanEmiAmt.getWidth();

            fastReport.addColumn(overDue);
            overDue.setStyle(style);
            width = width + 2 * overDue.getWidth();



//            fastReport.addColumn(lipAmt);
//            lipAmt.setStyle(style);
//            width = width + 2 * lipAmt.getWidth();





            fastReport.addColumn(totalAmt);
            totalAmt.setStyle(style);
            width = width + 2 * totalAmt.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle(area + " _ " + reportName + " _ " + tillDt);
            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), fastReport, area + " _ " + reportName + " _ " + tillDt);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public void refreshPage() {
        try {
            setMessage("");
            this.setMonth("0");
            this.setYear("");
            this.setArea("S");
            this.setRepType("Se");
            this.setOptionType("S");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<SelectItem> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<SelectItem> areaList) {
        this.areaList = areaList;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepList() {
        return repList;
    }

    public void setRepList(List<SelectItem> repList) {
        this.repList = repList;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public List<SelectItem> getOptionTypeList() {
        return optionTypeList;
    }

    public void setOptionTypeList(List<SelectItem> optionTypeList) {
        this.optionTypeList = optionTypeList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isPrintButton() {
        return printButton;
    }

    public void setPrintButton(boolean printButton) {
        this.printButton = printButton;
    }
}
