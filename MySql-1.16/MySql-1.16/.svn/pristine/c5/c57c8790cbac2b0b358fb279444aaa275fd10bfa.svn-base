/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.other.SortedByStatus;
import com.cbs.dto.report.LoanPeriodPojo;
import com.cbs.dto.report.SortedByAcnoStatus;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author admin
 */
public class LoanPeriod extends BaseBean {

    private String errorMsg;
    private String reportType;
    private String basedReport;
    private float roifrm;
    private float roito;
    private double senfrm;
    private double sento;
    private String acType;
    private String term;
    private String schemetype;
    private Date frmDt;
    private Date toDt;
    private Date frmDtTran;
    private Date toDtTran;
    private boolean firstPanel;
    private boolean secondPanel;
    private boolean thirdPanel;
    private boolean fourthPanel;
    private boolean roisenboolean;
    private boolean termBoolean;
    private int fromperiod = 0, toperiod;
    private List<SelectItem> schemeList;
    private List<SelectItem> acctypeList;
    private List<SelectItem> reportTypeList;
    private String loanPeriod;
    private List<SelectItem> loanPeriodList;
    private LoanReportFacadeRemote local;
    private OtherLoanReportFacadeRemote other;
    private CommonReportMethodsRemote common;
    private HttpServletRequest req;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat dd = new DecimalFormat("##.##");
    Date date = new Date();
    Map schemeMap = new HashMap();
    Map reportTypeMap = new HashMap();
    private String focusAbleId;
    private String focusAbleRoiId;
    private String branch;
    private List<SelectItem> branchList;

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public List<SelectItem> getLoanPeriodList() {
        return loanPeriodList;
    }

    public void setLoanPeriodList(List<SelectItem> loanPeriodList) {
        this.loanPeriodList = loanPeriodList;
    }

    public String getFocusAbleRoiId() {
        return focusAbleRoiId;
    }

    public void setFocusAbleRoiId(String focusAbleRoiId) {
        this.focusAbleRoiId = focusAbleRoiId;
    }

    public String getFocusAbleId() {
        return focusAbleId;
    }

    public void setFocusAbleId(String focusAbleId) {
        this.focusAbleId = focusAbleId;
    }

    public List<SelectItem> getAcctypeList() {
        return acctypeList;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public void setAcctypeList(List<SelectItem> acctypeList) {
        this.acctypeList = acctypeList;
    }

    public boolean isTermBoolean() {
        return termBoolean;
    }

    public void setTermBoolean(boolean termBoolean) {
        this.termBoolean = termBoolean;
    }

    public boolean isRoisenboolean() {
        return roisenboolean;
    }

    public void setRoisenboolean(boolean roisenboolean) {
        this.roisenboolean = roisenboolean;
    }

    public boolean isFirstPanel() {
        return firstPanel;
    }

    public void setFirstPanel(boolean firstPanel) {
        this.firstPanel = firstPanel;
    }

    public boolean isFourthPanel() {
        return fourthPanel;
    }

    public void setFourthPanel(boolean fourthPanel) {
        this.fourthPanel = fourthPanel;
    }

    public boolean isSecondPanel() {
        return secondPanel;
    }

    public void setSecondPanel(boolean secondPanel) {
        this.secondPanel = secondPanel;
    }

    public boolean isThirdPanel() {
        return thirdPanel;
    }

    public void setThirdPanel(boolean thirdPanel) {
        this.thirdPanel = thirdPanel;
    }

    public Date getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(Date frmDt) {
        this.frmDt = frmDt;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public Date getFrmDtTran() {
        return frmDtTran;
    }

    public void setFrmDtTran(Date frmDtTran) {
        this.frmDtTran = frmDtTran;
    }

    public Date getToDtTran() {
        return toDtTran;
    }

    public void setToDtTran(Date toDtTran) {
        this.toDtTran = toDtTran;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getBasedReport() {
        return basedReport;
    }

    public void setBasedReport(String basedReport) {
        this.basedReport = basedReport;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public float getRoifrm() {
        return roifrm;
    }

    public void setRoifrm(float roifrm) {
        this.roifrm = roifrm;
    }

    public float getRoito() {
        return roito;
    }

    public void setRoito(float roito) {
        this.roito = roito;
    }

    public String getSchemetype() {
        return schemetype;
    }

    public void setSchemetype(String schemetype) {
        this.schemetype = schemetype;
    }

    public double getSenfrm() {
        return senfrm;
    }

    public void setSenfrm(double senfrm) {
        this.senfrm = senfrm;
    }

    public double getSento() {
        return sento;
    }

    public void setSento(double sento) {
        this.sento = sento;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<SelectItem> getSchemeList() {
        return schemeList;
    }

    public void setSchemeList(List<SelectItem> schemeList) {
        this.schemeList = schemeList;
    }

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

    /**
     * Creates a new instance of LoanPeriod
     */
    public LoanPeriod() {
        schemeList = new ArrayList<SelectItem>();
        acctypeList = new ArrayList<SelectItem>();
        reportTypeList = new ArrayList<SelectItem>();
        schemeList.add(new SelectItem("Select", "Select"));
        reportTypeList.add(new SelectItem("-2", "ALL PERIOD BASED REPORT"));
        reportTypeList.add(new SelectItem("-1", "ALL NON PERIOD BASED REPORT"));
        reportTypeList.add(new SelectItem("0", "PERIOD BASED REPORT"));
        reportTypeList.add(new SelectItem("1", "NON PERIOD BASED REPORT"));
        reportTypeMap.put("-2", "ALL PERIOD BASED REPORT");
        reportTypeMap.put("-1", "ALL NON PERIOD BASED REPORT");
        reportTypeMap.put("0", "PERIOD BASED REPORT");
        reportTypeMap.put("1", "NON PERIOD BASED REPORT");

        loanPeriodList = new ArrayList<SelectItem>();
        loanPeriodList.add(new SelectItem("ALL", "ALL"));
        loanPeriodList.add(new SelectItem("Active", "Active"));
        loanPeriodList.add(new SelectItem("Closed", "Closed"));

        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            other = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherLoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            frmDt = date;
            toDt = date;
            frmDtTran = date;
            toDtTran = date;
            roisenboolean = true;
            termBoolean = false;
            firstPanel = false;
            secondPanel = false;

            acctypeList.add(new SelectItem("Select", "--Select--"));
            acctypeList.add(new SelectItem("ALL", "ALL"));
            List listAcctCode = local.getDistinctAcCodeByAcNature();
            for (Object element : listAcctCode) {
                Vector vector = (Vector) element;
                acctypeList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
            }

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
    }

    public void reportBased() {
        switch (Integer.parseInt(reportType)) {
            case -2:
                roisenboolean = true;
                termBoolean = false;
                firstPanel = false;
                secondPanel = false;
                roifrm = 0;
                roito = 99;
                senfrm = 0;
                sento = 9999999999999.9;
                focusAbleId = "ddlAccctype";
                break;
            case -1:
                roisenboolean = true;
                termBoolean = true;
                firstPanel = false;
                secondPanel = false;
                roifrm = 0;
                roito = 99;
                senfrm = 0;
                sento = 9999999999999.9;
                fromperiod = 0;
                toperiod = 9999999;
                focusAbleId = "ddlAccctype";
                break;
            case 0:
                roisenboolean = false;
                termBoolean = false;
                firstPanel = true;
                secondPanel = false;
                roifrm = 0;
                roito = 99;
                senfrm = 0;
                sento = 9999999999999.9;
                focusAbleId = "roibasedddl";
                break;
            case 1:
                roisenboolean = false;
                termBoolean = true;
                firstPanel = true;
                secondPanel = false;
                roifrm = 0;
                roito = 99;
                senfrm = 0;
                sento = 9999999999999.9;
                fromperiod = 0;
                toperiod = 9999999;
                focusAbleId = "roibasedddl";
                break;
        }

    }

    public void sencionedBased() {
        switch (Integer.parseInt(basedReport)) {
            case 0:
                firstPanel = true;
                secondPanel = false;
                senfrm = 0;
                sento = 0.0;
                focusAbleRoiId = "txtfrom";
                break;
            case 1:
                firstPanel = false;
                secondPanel = true;
                roifrm = 0;
                roito = 99;
                focusAbleRoiId = "txtsanfrom";
                break;
        }
    }

    public String viewReport() {
        String bankName = "ALL", bankAddress = "ALL";
        String report = (String) reportTypeMap.get(reportType);
        String based = "", termreport = "";
        try {
            if (frmDt == null) {
                setErrorMsg("Please insert From date !");
                return null;
            }
            if (toDt == null) {
                setErrorMsg("Please insert To date !");
                return null;
            }
            if (frmDtTran == null) {
                setErrorMsg("Please insert Interest from date !");
                return null;
            }
            if (toDtTran == null) {
                setErrorMsg("Please insert Interest To date !");
                return null;
            }
            if (reportType.equalsIgnoreCase("-2")) {
                roifrm = 0;
                roito = 99;
                senfrm = 0;
                sento = 9999999999999.9;

            } else if (reportType.equalsIgnoreCase("-1")) {
                roifrm = 0;
                roito = 99;
                senfrm = 0;
                sento = 9999999999999.9;
            } else if (reportType.equalsIgnoreCase("0")) {
                if (basedReport.equals("0")) {
                    senfrm = 0;
                    sento = 999999999999.99;
                    based = " ROI Based between " + dd.format(roifrm) + " to " + dd.format(roito);
                } else if (basedReport.equals("1")) {
                    roifrm = 0;
                    roito = 99;
                    based = " Sanctioned Amount Based " + dd.format(senfrm) + " to " + dd.format(sento);
                }
            } else if (reportType.equalsIgnoreCase("1")) {
                if (basedReport.equals("0")) {
                    senfrm = 0;
                    sento = 99999999999.99;
                    based = " ROI Based between " + dd.format(roifrm) + " to " + dd.format(roito);
                } else if (basedReport.equals("1")) {
                    roifrm = 0;
                    roito = 99;
                    based = " Sanctioned Amount Based " + dd.format(senfrm) + " to " + dd.format(sento);
                }
            }
            if (!termBoolean) {
                switch (Integer.parseInt(term)) {
                    case 0:
                        fromperiod = 0;
                        toperiod = 15;
                        termreport = "Short Term";
                        break;
                    case 1:
                        fromperiod = 16;
                        toperiod = 60;
                        termreport = "Medium Term";
                        break;
                    case 2:
                        fromperiod = 61;
                        toperiod = 999999;
                        termreport = "Long Term";
                        break;
                    case 3:
                        fromperiod = 0;
                        toperiod = 999999;
                        termreport = "ALL Term";
                        break;

                }
            }
            List<LoanPeriodPojo> resuList = other.loanPeriodReport(acType, Integer.parseInt(term), fromperiod, toperiod, roifrm, roito, senfrm, sento, ymd.format(frmDt), ymd.format(toDt), schemetype, this.getBranch(), reportType, basedReport, loanPeriod, ymd.format(frmDtTran), ymd.format(toDtTran));
            if (!resuList.isEmpty()) {
                Map fillParams = new HashMap();
                if (!this.branch.equalsIgnoreCase("0A")) {
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                } else {
                    List dataList1 = common.getBranchNameandAddress("90");
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                }
                String param = "Account Status : " + loanPeriod + " ; " + "Term : " + termreport + "; Interest: " + dmy.format(frmDtTran) + " To " + dmy.format(toDtTran);
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);
                fillParams.put("pReportDate", dmy.format(frmDt) + " To " + dmy.format(toDt));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pterm", param);
                fillParams.put("pReportType", acType);
                fillParams.put("pReportbased", based);
                fillParams.put("pScheme", schemeMap.get(schemetype).toString());

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByAcnoStatus());
                Collections.sort(resuList, chObj);

                new ReportBean().jasperReport("LoanPeriod", "text/html", new JRBeanCollectionDataSource(resuList), fillParams, "Loan Period Report");
                return "report";
            } else {
                errorMsg = "No detail exits !";
            }
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
        return null;
    }

    public String pdfDownLoad() {
        String bankName = "ALL", bankAddress = "ALL";
        String report = (String) reportTypeMap.get(reportType);
        String based = "", termreport = "";
        try {
            if (frmDt == null) {
                setErrorMsg("Please insert From date !");
                return null;
            }
            if (toDt == null) {
                setErrorMsg("Please insert To date !");
                return null;
            }
            if (frmDtTran == null) {
                setErrorMsg("Please insert Interest from date !");
                return null;
            }
            if (toDtTran == null) {
                setErrorMsg("Please insert Interest To date !");
                return null;
            }
            if (reportType.equalsIgnoreCase("-2")) {
                roifrm = 0;
                roito = 99;
                senfrm = 0;
                sento = 9999999999999.9;

            } else if (reportType.equalsIgnoreCase("-1")) {
                roifrm = 0;
                roito = 99;
                senfrm = 0;
                sento = 9999999999999.9;
            } else if (reportType.equalsIgnoreCase("0")) {
                if (basedReport.equals("0")) {
                    senfrm = 0;
                    sento = 999999999999.99;
                    based = " ROI Based between " + dd.format(roifrm) + " to " + dd.format(roito);
                } else if (basedReport.equals("1")) {
                    roifrm = 0;
                    roito = 99;
                    based = " Sanctioned Amount Based " + dd.format(senfrm) + " to " + dd.format(sento);
                }
            } else if (reportType.equalsIgnoreCase("1")) {
                if (basedReport.equals("0")) {
                    senfrm = 0;
                    sento = 99999999999.99;
                    based = " ROI Based between " + dd.format(roifrm) + " to " + dd.format(roito);
                } else if (basedReport.equals("1")) {
                    roifrm = 0;
                    roito = 99;
                    based = " Sanctioned Amount Based " + dd.format(senfrm) + " to " + dd.format(sento);
                }
            }
            if (!termBoolean) {
                switch (Integer.parseInt(term)) {
                    case 0:
                        fromperiod = 0;
                        toperiod = 15;
                        termreport = "Short Term";
                        break;
                    case 1:
                        fromperiod = 16;
                        toperiod = 60;
                        termreport = "Medium Term";
                        break;
                    case 2:
                        fromperiod = 61;
                        toperiod = 999999;
                        termreport = "Long Term";
                        break;
                    case 3:
                        fromperiod = 0;
                        toperiod = 999999;
                        termreport = "ALL Term";
                        break;

                }
            }
            List<LoanPeriodPojo> resuList = other.loanPeriodReport(acType, Integer.parseInt(term), fromperiod, toperiod, roifrm, roito, senfrm, sento, ymd.format(frmDt), ymd.format(toDt), schemetype, this.getBranch(), reportType, basedReport, loanPeriod, ymd.format(frmDtTran), ymd.format(toDtTran));
            if (!resuList.isEmpty()) {
                Map fillParams = new HashMap();
                if (!this.branch.equalsIgnoreCase("0A")) {
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                } else {
                    List dataList1 = common.getBranchNameandAddress("90");
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                }
                String param = "Account Status : " + loanPeriod + " ; " + "Term : " + termreport + "; Interest: " + dmy.format(frmDtTran) + " To " + dmy.format(toDtTran);
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);
                fillParams.put("pReportDate", dmy.format(frmDt) + " To " + dmy.format(toDt));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pterm", param);
                fillParams.put("pReportType", acType);
                fillParams.put("pReportbased", based);
                fillParams.put("pScheme", schemeMap.get(schemetype).toString());
                
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByAcnoStatus());
                Collections.sort(resuList, chObj);

                new ReportBean().openPdf("Loan Period Report_" + ymd.format(frmDt) + " to " + ymd.format(toDt), "LoanPeriod", new JRBeanCollectionDataSource(resuList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "Loan Period Report");
            } else {
                errorMsg = "No detail exits !";
            }
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
        return null;
    }

    public void blurAcctype() {
        if (schemeList != null) {
            schemeList.clear();
            schemeMap.clear();
        }

        Vector vtr = null;
        try {

            List result = null;
            result = local.getSchemeType(acType);

            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                schemeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
                schemeMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }


    }

    public String exitAction() {
        return "case1";
    }

    public void refresh() {
        frmDt = date;
        toDt = date;
        frmDtTran = date;
        toDtTran = date;
        errorMsg = "";
    }
}
