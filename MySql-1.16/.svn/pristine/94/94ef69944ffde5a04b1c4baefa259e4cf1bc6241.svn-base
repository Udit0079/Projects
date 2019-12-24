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
import com.cbs.dto.report.LoanOutStandingBalancePojo;
import com.cbs.dto.report.ho.SortedByBalanceForBalWiseReport;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Athar Reza
 */
public class LoanOutStandingBalance extends BaseBean {

    private String errorMsg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String balType;
    private List<SelectItem> balTypeList;
    private String toDt;
    private Date date = new Date();
    private double frAmount;
    private double toAmount;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    List tempList = null;
    Vector tempVector = null;
    List<LoanOutStandingBalancePojo> reportList = new ArrayList<LoanOutStandingBalancePojo>();
    private LoanInterestCalculationFacadeRemote beanRemote = null;
    private final String jndiHomeName = "LoanInterestCalculationFacade";
    private DDSReportFacadeRemote ddsRemote;

    public String getBalType() {
        return balType;
    }

    public void setBalType(String balType) {
        this.balType = balType;
    }

    public List<SelectItem> getBalTypeList() {
        return balTypeList;
    }

    public void setBalTypeList(List<SelectItem> balTypeList) {
        this.balTypeList = balTypeList;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public double getFrAmount() {
        return frAmount;
    }

    public void setFrAmount(double frAmount) {
        this.frAmount = frAmount;
    }

    public double getToAmount() {
        return toAmount;
    }

    public void setToAmount(double toAmount) {
        this.toAmount = toAmount;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public LoanOutStandingBalance() {
        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            toDt = dmy.format(date);

//            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
//            branchCodeList = new ArrayList<SelectItem>();
//            for (int i = 0; i < list.size(); i++) {
//                Vector vtr = (Vector) list.get(i);
//                branchCodeList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
//            }
            List list = new ArrayList();
            list = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector brnVector = (Vector) list.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getAccountNature();
            balTypeList = new ArrayList<SelectItem>();
            // balTypeList.add(new SelectItem("", ""));
            balTypeList.add(new SelectItem("'C','B'", "Cr"));
            balTypeList.add(new SelectItem("'D','B'", "Dr"));

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void getAccountNature() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            List acNtureList = common.getAccountNature();
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void initData() {
        try {
            acctTypeList = new ArrayList<SelectItem>();
            tempList = ddsRemote.getAcctCodeByNatureFlag(acNature, balType);
            acctTypeList.add(new SelectItem("ALL", "ALL"));
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                acctTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        String report = "Outstanding Balance Wise Report";
        String branchName = "";
        String address = "";
        try {

            if (acNature.equalsIgnoreCase("0")) {
                setErrorMsg("Please Select Account Nature!");
                return;
            }

            if (acctType.equalsIgnoreCase("0")) {
                setErrorMsg("Please Select Account Type!");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill Date!");
                return;
            }

            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please fill Proper Date!");
                return;
            }
            if (toAmount == 0) {
                setErrorMsg("Please fill the proper to amount!");
                return;
            }
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }

            Map fillmap = new HashMap();
            fillmap.put("pBranchName", branchName);
            fillmap.put("pAddress", address);
            fillmap.put("pRepName", report);
            fillmap.put("pReportDate", this.toDt);
            fillmap.put("pPrintedBy", this.getUserName());
            String toDate = ymd.format(dmy.parse(toDt));

            reportList = local.getLoanOutStandingReport(branchCode, acctType, toDate, frAmount, toAmount, acNature, balType);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!");
            }

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByBalanceForBalWiseReport());
            Collections.sort(reportList, chObj);

            if (!(acNature.equalsIgnoreCase("TL") || acNature.equalsIgnoreCase("DL") || acNature.equalsIgnoreCase("CA") && balType.equalsIgnoreCase("'D','B'"))) {
                new ReportBean().jasperReport("DepositAccountBalance", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillmap, "Outstanding Balance Wise Report");
            } else {
                new ReportBean().jasperReport("LoanOutStandingBalance", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillmap, "Outstanding Balance Wise Report");
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            if (acNature.equalsIgnoreCase("0")) {
                setErrorMsg("Please Select Account Nature!");
                return null;
            }

            if (acctType.equalsIgnoreCase("0")) {
                setErrorMsg("Please Select Account Type!");
                return null;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill Date!");
                return null;
            }

            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please fill Proper Date!");
                return null;
            }
            if (toAmount == 0) {
                setErrorMsg("Please fill the proper to amount!");
                return null;
            }

            reportList = local.getLoanOutStandingReport(branchCode, acctType, ymd.format(dmy.parse(toDt)), frAmount, toAmount, acNature, balType);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!");
            }

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByBalanceForBalWiseReport());
            Collections.sort(reportList, chObj);

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account No.", 100);
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Customer Id", 60);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name", 200);
            AbstractColumn branch = ReportBean.getColumn("branch", String.class, "Branch Code", 60);
            AbstractColumn productCode = ReportBean.getColumn("productCode", String.class, "Product Code", 60);
            AbstractColumn bal = ReportBean.getColumn("bal", Double.class, "Balance", 100);
            AbstractColumn openingDt = ReportBean.getColumn("openingDt", String.class, "Open Date", 90);
            AbstractColumn matDate = ReportBean.getColumn("matDate", String.class, "Maturity Date", 90);
            AbstractColumn roi = ReportBean.getColumn("roi", Double.class, "Rate of Interest", 60);
            AbstractColumn riskCategory = ReportBean.getColumn("riskCategory", String.class, "Risk Category", 180);
            AbstractColumn status = ReportBean.getColumn("status", String.class, "Status", 130);

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(custId);
            width = width + custId.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(branch);
            width = width + branch.getWidth();

            fastReport.addColumn(productCode);
            width = width + productCode.getWidth();

            fastReport.addColumn(bal);
            bal.setStyle(style);
            width = width + bal.getWidth();

            fastReport.addColumn(openingDt);
            width = width + openingDt.getWidth();

            fastReport.addColumn(matDate);
            width = width + matDate.getWidth();

            fastReport.addColumn(roi);
            roi.setStyle(style);
            width = width + roi.getWidth();

//            fastReport.addColumn(roi);
//            width = width + roi.getWidth();

            fastReport.addColumn(riskCategory);
            width = width + riskCategory.getWidth();

            fastReport.addColumn(status);
            width = width + status.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Deposit Account Information");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), fastReport, "Deposit Account Information");

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
        return fastReport;
    }

    public void pdfDownLoad() {
        String report = "Outstanding Balance Wise Report";
        String branchName = "";
        String address = "";
        try {

            if (acNature.equalsIgnoreCase("0")) {
                setErrorMsg("Please Select Account Nature!");
                return;
            }

            if (acctType.equalsIgnoreCase("0")) {
                setErrorMsg("Please Select Account Type!");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill Date!");
                return;
            }

            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please fill Proper Date!");
                return;
            }
            if (toAmount == 0) {
                setErrorMsg("Please fill the proper to amount!");
                return;
            }
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }

            Map fillmap = new HashMap();
            fillmap.put("pBranchName", branchName);
            fillmap.put("pAddress", address);
            fillmap.put("pRepName", report);
            fillmap.put("pReportDate", this.toDt);
            fillmap.put("pPrintedBy", this.getUserName());
            String toDate = ymd.format(dmy.parse(toDt));

            reportList = local.getLoanOutStandingReport(branchCode, acctType, toDate, frAmount, toAmount, acNature, balType);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!");
            }

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByBalanceForBalWiseReport());
            Collections.sort(reportList, chObj);
            if (!(acNature.equalsIgnoreCase("TL") || acNature.equalsIgnoreCase("DL") || acNature.equalsIgnoreCase("CA") && balType.equalsIgnoreCase("'D','B'"))) {
                new ReportBean().openPdf("Outstanding Balance Wise Report_" + toDate, "DepositAccountBalance", new JRBeanCollectionDataSource(reportList), fillmap);
            } else {
                new ReportBean().openPdf("Outstanding Balance Wise Report_" + toDate, "LoanOutStandingBalance", new JRBeanCollectionDataSource(reportList), fillmap);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        try {
            errorMsg = "";
            this.acNature = "0";
            toDt = dmy.format(new Date());
            this.setFrAmount(0d);
            this.setToAmount(0d);
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        try {
            refresh();
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return "case1";
    }
}
