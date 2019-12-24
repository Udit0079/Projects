/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.PrioritySectorPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.facade.report.RbiQuarterlyReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeHalfYearlyRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.RbiSoss1And2ReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.Form2StmtUnSecAdvToDirFirm;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.text.DateFormat;

/**
 *
 * @author saurabhsipl
 */
public class PrioritySectorController extends BaseBean {

    private String msg;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String calDate;
    private String reportIn;
    private String branch;
    private List<SelectItem> branchList;
    private List<SelectItem> reportInList;
    private GLReportFacadeRemote glBeanRemote;
    private RbiReportFacadeHalfYearlyRemote ossBeanPartDRemote;
    private RbiQuarterlyReportFacadeRemote rbiQtrRemote;
    private CommonReportMethodsRemote common;
    private LoanReportFacadeRemote loanRptFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    private RbiReportFacadeRemote ossBeanRemote;
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote; 

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

    public PrioritySectorController() {
        reportTypeList = new ArrayList<SelectItem>();
        reportInList = new ArrayList<SelectItem>();
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ossBeanPartDRemote = (RbiReportFacadeHalfYearlyRemote) ServiceLocator.getInstance().lookup("RbiReportFacadeHalfYearly");
            loanRptFacade = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            rbiQtrRemote = (RbiQuarterlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiQuarterlyReportFacade");
            monthlyRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiMonthlyReportFacade");
            ossBeanRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            rbiSoss1And2Remote = (RbiSoss1And2ReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiSoss1And2ReportFacade");
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
            reportTypeList.add(new SelectItem("PRIOR_OSS", "OSS Module"));
            reportTypeList.add(new SelectItem("PRIOR_XBRL", "XBRL"));

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
        try {
            if (this.reportType == null || this.reportType.equals("")) {
                this.setMsg("Please select report type.");
                return;
            }
            if (this.calDate == null || this.calDate.equals("")) {
                this.setMsg("Please select appropriate date.");
                return;
            }
            BigDecimal totalAdv = new BigDecimal("0"), totalPriorAdv = new BigDecimal("0"), totalWkAdv = new BigDecimal("0"),
                    totalPriorPerAdv = new BigDecimal("0"), totalWkPerOfTotalAdv = new BigDecimal("0"), totalWkPerPriorAdv = new BigDecimal("0"),
                    preTotalAdv = new BigDecimal("0"), preTotalPriorAdv = new BigDecimal("0"), preTotalWkAdv = new BigDecimal("0");
            BigDecimal totalANBCPerAdv = new BigDecimal("0");

            String reportName = "Priority Sector and Weaker Section Advances";
            Map fillParams = new HashMap();
            String[] arr = Util.getReportOptionAndDescription(this.reportIn);
            List brNameAndAddList = common.getBranchNameandAddress(branch.equalsIgnoreCase("0A") ? "90" : branch);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("printedBy", getUserName());
            fillParams.put("pAmtIn", arr[1]);
            fillParams.put("report", reportName);
            fillParams.put("pPrintedDate", dmy.format(dmy.parse(calDate)));
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            List anbcParameter = common.getCode("ANBC_EXTRA");
            String anbcParm = "N";
            if (!anbcParameter.isEmpty()) {
                anbcParm = anbcParameter.get(0).toString();
            }
            BigDecimal glANBC = new BigDecimal("0"), glOBE = new BigDecimal("0"), crOBE = new BigDecimal("0");
            if (anbcParm.equalsIgnoreCase("Y")) {
                List<String> dates = new ArrayList<>();
                String dt = rbiQtrRemote.getMinFinYear(ymd.format(dmy.parse(calDate)));
                dates.add(CbsUtil.dateAdd(dt, -1));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);
                List<RbiSossPojo> anbcList = rbiSoss1And2Remote.getSOSS2("PRIOR_ANBC", CbsUtil.dateAdd(dt, -1), branch, new BigDecimal(arr[0].toString()), "S", osBlancelist, "0");
                List<RbiSossPojo> offBalSheetExp = rbiSoss1And2Remote.getSOSS2("PRIOR_OBE", CbsUtil.dateAdd(dt, -1), branch, new BigDecimal(arr[0].toString()), "S", osBlancelist, "0");
                List<RbiSossPojo> crOBEList = rbiSoss1And2Remote.getSOSS2("PRIOR_CROBE", CbsUtil.dateAdd(dt, -1), branch, new BigDecimal(arr[0].toString()), "S", osBlancelist, "0");
                if (!anbcList.isEmpty()) {
                    for (int t = 0; t < anbcList.size(); t++) {
                        RbiSossPojo pojo = anbcList.get(t);
                        glANBC = glANBC.add(pojo.getAmt());
                    }
                }
                if (!offBalSheetExp.isEmpty()) {
                    for (int t = 0; t < offBalSheetExp.size(); t++) {
                        RbiSossPojo pojo = offBalSheetExp.get(t);
                        glOBE = glOBE.add(pojo.getAmt());
                    }
                }
                if (!crOBEList.isEmpty()) {
                    for (int t = 0; t < crOBEList.size(); t++) {
                        RbiSossPojo pojo = crOBEList.get(t);
                        crOBE = crOBE.add(pojo.getAmt());
                    }
                }
            }

            if (reportType.equalsIgnoreCase("PRIOR_OSS")) {
                List<LoanMisCellaniousPojo> allAccList = new ArrayList<LoanMisCellaniousPojo>();
                allAccList = loanRptFacade.cbsLoanMisReport(branch, "0", "0", ymd.format(dmy.parse(calDate)), "A", 0.0, 99999999999.99, "S", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                for (int k = 0; k < allAccList.size(); k++) {
                    if (allAccList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        totalAdv = totalAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                        if (allAccList.get(k).getSector().equalsIgnoreCase("PRIOR")) {
                            totalPriorAdv = totalPriorAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                            if (allAccList.get(k).getApplicantCategory().equalsIgnoreCase("WK")) {
                                totalWkAdv = totalWkAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                            }
                        }
                    } else {
                        totalAdv = totalAdv.add(allAccList.get(k).getOutstanding());
                        if (allAccList.get(k).getSector().equalsIgnoreCase("PRIOR")) {
                            //totalPriorAdv = totalPriorAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                            totalPriorAdv = totalPriorAdv.add(allAccList.get(k).getOutstanding());
                            if (allAccList.get(k).getApplicantCategory().equalsIgnoreCase("WK")) {
                                totalWkAdv = totalWkAdv.add(allAccList.get(k).getOutstanding());
                            }
                        }
                    }
                }
                List<PrioritySectorPojo> priorityList = ossBeanPartDRemote.getPrioritySectorOSS(reportType, ymd.format(dmy.parse(calDate)), branch, new BigDecimal(arr[0]));
                if (totalAdv.compareTo(new BigDecimal("0")) != 0) {
                    totalAdv = totalAdv.divide(new BigDecimal(arr[0]), 2, BigDecimal.ROUND_HALF_UP);
                }
                if (totalPriorAdv.compareTo(new BigDecimal("0")) != 0) {
                    totalPriorAdv = totalPriorAdv.divide(new BigDecimal(arr[0]), 2, BigDecimal.ROUND_HALF_UP);
                    totalPriorPerAdv = totalPriorAdv.multiply(new BigDecimal("100")).divide((totalAdv.abs().add(glANBC.abs())), 2, BigDecimal.ROUND_HALF_UP);
                    if (glOBE.compareTo(new BigDecimal("0")) != 0) {
                        totalANBCPerAdv = totalPriorAdv.multiply(new BigDecimal("100")).divide(glOBE.abs(), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    if (totalPriorPerAdv.compareTo(totalANBCPerAdv) > 0 ) {
                        totalPriorPerAdv = totalPriorPerAdv;
                    } else {
                        if (totalANBCPerAdv.compareTo(new BigDecimal("0")) == 0 ) {
                            totalPriorPerAdv= totalPriorPerAdv;
                        } else {
                            totalPriorPerAdv = totalANBCPerAdv;
                        }
                    }
                }
                if (totalWkAdv.compareTo(new BigDecimal("0")) != 0) {
                    totalWkAdv = totalWkAdv.divide(new BigDecimal(arr[0]), 2, BigDecimal.ROUND_HALF_UP);
                    totalWkPerPriorAdv = totalWkAdv.multiply(new BigDecimal("100")).divide(totalPriorAdv, 2, BigDecimal.ROUND_HALF_UP);
                    totalWkPerOfTotalAdv = totalWkAdv.multiply(new BigDecimal("100")).divide((totalAdv.abs().add(glANBC.abs())), 2, BigDecimal.ROUND_HALF_UP);
                }
                if (dmy.parse(calDate).after(dmy.parse("09/05/2018"))) {
                    String preYearLastDt = rbiQtrRemote.getMinFinYear(ymd.format(dmy.parse(calDate)));
                    List<LoanMisCellaniousPojo> preYearList = new ArrayList<LoanMisCellaniousPojo>();
                    preYearList = loanRptFacade.cbsLoanMisReport(branch, "0", "0", CbsUtil.dateAdd(preYearLastDt, -1), "A", 0.0, 99999999999.99, "S", "0", "0",
                            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                    for (int k = 0; k < preYearList.size(); k++) {
                        if (preYearList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            preTotalAdv = preTotalAdv.add(preYearList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : preYearList.get(k).getOutstanding());
                        } else {
                            preTotalAdv = preTotalAdv.add(preYearList.get(k).getOutstanding());
                        }
                    }
                    if (preTotalAdv.compareTo(new BigDecimal("0")) != 0) {
                        preTotalAdv = preTotalAdv.divide(new BigDecimal(arr[0]), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    totalAdv = preTotalAdv;
                }
                fillParams.put("pTotalAdv", totalAdv.abs().add(glANBC.abs()));
                fillParams.put("pTotalPriAdv", totalPriorAdv.abs());
                fillParams.put("pTotalPriPercentageOfAdv", totalPriorPerAdv.abs());
                fillParams.put("pTotalWkAdv", totalWkAdv.abs());
                fillParams.put("pTotalWkPercentageOfAdv", totalWkPerOfTotalAdv.abs());
                fillParams.put("pTotalWkPercentageOfPriAdv", totalWkPerPriorAdv.abs());
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
                fillParams.put("pAsOnDate", dateFormat.format(dmy.parse(calDate)));
                fillParams.put("pPrevYearAdv", preTotalAdv.abs().add(glANBC.abs()));
                
                fillParams.put("pTotalOBE", glOBE.abs());
                fillParams.put("pTotalCROBE", crOBE.abs());
                
                List<Form2StmtUnSecAdvToDirFirm> dirAdvList = new ArrayList<Form2StmtUnSecAdvToDirFirm>();
                Form2StmtUnSecAdvToDirFirm pojo1 = new Form2StmtUnSecAdvToDirFirm();
                pojo1.setDirAdvList(new JRBeanCollectionDataSource(priorityList));
                pojo1.setDirAdvList1(new JRBeanCollectionDataSource(priorityList));
                pojo1.setDirAdvList2(new JRBeanCollectionDataSource(priorityList));
                pojo1.setDirAdvList3(new JRBeanCollectionDataSource(priorityList));
                dirAdvList.add(pojo1);
//                new ReportBean().downloadPdf("OSS_Priority_Sector_" + ymd.format(dmy.parse(calDate)), "PrioritySectorOss",
//                        new JRBeanCollectionDataSource(jrxmlList), fillParams);
                if (dmy.parse(calDate).after(dmy.parse("09/05/2018"))) {
                    new ReportBean().openPdf("Priority_Sector_OSS_Annual" + ymd.format(dmy.parse(calDate)), "PrioritySectorOss_new_format", new JRBeanCollectionDataSource(dirAdvList), fillParams);
                } else {
                    new ReportBean().openPdf("Priority_Sector_OSS_Annual" + ymd.format(dmy.parse(calDate)), "PrioritySectorOss", new JRBeanCollectionDataSource(dirAdvList), fillParams);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", reportName);


            } else if (reportType.equalsIgnoreCase("PRIOR_XBRL")) {
//                List<PrioritySectorPojo> priorityList = ossBeanPartDRemote.getPrioritySector(reportType, ymd.format(dmy.parse(calDate)), getOrgnBrCode(), new BigDecimal(arr[0]));
//                new ReportBean().downloadPdf("XBRL_Priority_Sector_" + ymd.format(dmy.parse(calDate)), "PrioritySector",
//                        new JRBeanCollectionDataSource(priorityList), fillParams);
                String preYearLastDt = rbiQtrRemote.getMinFinYear(ymd.format(dmy.parse(calDate)));
                List<LoanMisCellaniousPojo> allAccList = new ArrayList<LoanMisCellaniousPojo>();
                allAccList = loanRptFacade.cbsLoanMisReport(branch, "0", "0", CbsUtil.dateAdd(preYearLastDt, -1), "A", 0.0, 99999999999.99, "S", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                for (int k = 0; k < allAccList.size(); k++) {
                    if (allAccList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        preTotalAdv = preTotalAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                        if (allAccList.get(k).getSector().equalsIgnoreCase("PRIOR")) {
                            preTotalPriorAdv = preTotalPriorAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                            if (allAccList.get(k).getApplicantCategory().equalsIgnoreCase("WK")) {
                                preTotalWkAdv = preTotalWkAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                            }
                        }
                    } else {
                        preTotalAdv = preTotalAdv.add(allAccList.get(k).getOutstanding());
                        if (allAccList.get(k).getSector().equalsIgnoreCase("PRIOR")) {
                            preTotalPriorAdv = preTotalPriorAdv.add(allAccList.get(k).getOutstanding());
                            if (allAccList.get(k).getApplicantCategory().equalsIgnoreCase("WK")) {
                                preTotalWkAdv = preTotalWkAdv.add(allAccList.get(k).getOutstanding());
                            }
                        }
                    }
                }
                allAccList = loanRptFacade.cbsLoanMisReport(branch, "0", "0", ymd.format(dmy.parse(calDate)), "A", 0.0, 99999999999.99, "S", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "N", "0", "N", "0", "N", "N", "0", "0", "0", "0");
                for (int k = 0; k < allAccList.size(); k++) {
                    if (allAccList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        totalAdv = totalAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                        if (allAccList.get(k).getSector().equalsIgnoreCase("PRIOR")) {
                            totalPriorAdv = totalPriorAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                            if (allAccList.get(k).getApplicantCategory().equalsIgnoreCase("WK")) {
                                totalWkAdv = totalWkAdv.add(allAccList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : allAccList.get(k).getOutstanding());
                            }
                        }
                    } else {
                        totalAdv = totalAdv.add(allAccList.get(k).getOutstanding());
                        if (allAccList.get(k).getSector().equalsIgnoreCase("PRIOR")) {
                            totalPriorAdv = totalPriorAdv.add(allAccList.get(k).getOutstanding());
                            if (allAccList.get(k).getApplicantCategory().equalsIgnoreCase("WK")) {
                                totalWkAdv = totalWkAdv.add(allAccList.get(k).getOutstanding());
                            }
                        }
                    }
                }
                List<PrioritySectorPojo> priorityList = ossBeanPartDRemote.getPrioritySectorOSS(reportType, ymd.format(dmy.parse(calDate)), branch, new BigDecimal(arr[0]));
                if (totalPriorAdv.compareTo(new BigDecimal("0")) != 0) {
                    totalPriorAdv = totalPriorAdv.divide(new BigDecimal(arr[0]), 2, BigDecimal.ROUND_HALF_UP);
                    totalPriorPerAdv = totalPriorAdv.multiply(new BigDecimal(arr[0])).multiply(new BigDecimal("100")).divide((preTotalAdv.abs().add(glANBC.abs().multiply(new BigDecimal(arr[0])))), 2, BigDecimal.ROUND_HALF_UP);
                    if (glOBE.compareTo(new BigDecimal("0")) != 0) {
                        totalANBCPerAdv = totalPriorAdv.multiply(new BigDecimal("100")).divide((glOBE.abs()), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    if (totalPriorPerAdv.compareTo(totalANBCPerAdv) > 0 ) {
                        totalPriorPerAdv = totalPriorPerAdv;
                    } else {
                        if (totalANBCPerAdv.compareTo(new BigDecimal("0")) == 0 ) {
                            totalPriorPerAdv= totalPriorPerAdv;
                        } else {
                            totalPriorPerAdv = totalANBCPerAdv;
                        }
                    }
                }
                if (totalAdv.compareTo(new BigDecimal("0")) != 0) {
                    totalAdv = totalAdv.divide(new BigDecimal(arr[0]), 2, BigDecimal.ROUND_HALF_UP);
                }
                if (totalWkAdv.compareTo(new BigDecimal("0")) != 0) {
                    totalWkAdv = totalWkAdv.divide(new BigDecimal(arr[0]), 2, BigDecimal.ROUND_HALF_UP);
                    totalWkPerPriorAdv = totalWkAdv.multiply(new BigDecimal(arr[0])).multiply(new BigDecimal("100")).divide((preTotalAdv.abs().add(glANBC.abs().multiply(new BigDecimal(arr[0])))), 2, BigDecimal.ROUND_HALF_UP);
                    totalWkPerOfTotalAdv = totalWkAdv.multiply(new BigDecimal(arr[0])).multiply(new BigDecimal("100")).divide(totalAdv.abs(), 2, BigDecimal.ROUND_HALF_UP);
                }

                fillParams.put("pTotalAdv", preTotalAdv.abs().divide(new BigDecimal(arr[0]), 2, BigDecimal.ROUND_HALF_UP).add(glANBC.abs()));
                fillParams.put("pTotalPriAdv", totalPriorAdv.abs());
                fillParams.put("pTotalPriPercentageOfAdv", totalPriorPerAdv.abs());
                fillParams.put("pTotalWkAdv", totalWkAdv.abs());
                fillParams.put("pTotalWkPercentageOfAdv", totalWkPerOfTotalAdv.abs());
                fillParams.put("pTotalWkPercentageOfPriAdv", totalWkPerPriorAdv.abs());
                fillParams.put("pTotalOBE", glOBE.abs());
                fillParams.put("pTotalCROBE", crOBE.abs());

//                List<Form2StmtUnSecAdvToDirFirm> dirAdvList = new ArrayList<Form2StmtUnSecAdvToDirFirm>();
//                    Form2StmtUnSecAdvToDirFirm pojo1 = new Form2StmtUnSecAdvToDirFirm();
//                pojo1.setDirAdvList(new JRBeanCollectionDataSource(priorityList));
//                pojo1.setDirAdvList1(new JRBeanCollectionDataSource(priorityList));
//                pojo1.setDirAdvList2(new JRBeanCollectionDataSource(priorityList));
//                pojo1.setDirAdvList3(new JRBeanCollectionDataSource(priorityList));
//                dirAdvList.add(pojo1);            
//                new ReportBean().downloadPdf("OSS_Priority_Sector_" + ymd.format(dmy.parse(calDate)), "PrioritySectorOss",
//                        new JRBeanCollectionDataSource(jrxmlList), fillParams);
                if (dmy.parse(calDate).after(dmy.parse("09/05/2018"))) {
                    new ReportBean().openPdf("Priority_Sector_XBRL" + ymd.format(dmy.parse(calDate)), "PrioritySector_new", new JRBeanCollectionDataSource(priorityList), fillParams);
                } else {
                    new ReportBean().openPdf("Priority_Sector_XBRL" + ymd.format(dmy.parse(calDate)), "PrioritySector", new JRBeanCollectionDataSource(priorityList), fillParams);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", reportName);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
//            System.out.println(" Error Is" + ex);
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
