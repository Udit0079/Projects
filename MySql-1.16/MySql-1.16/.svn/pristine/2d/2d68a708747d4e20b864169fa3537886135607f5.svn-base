/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.KYCcategorisationPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

/**
 *
 * @author Athar Reza
 */
public class KYCcategorisation extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acnoNature;
    private List<SelectItem> acnoNatureList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String toDt;
    private Date date = new Date();
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<KYCcategorisationPojo> repList = new ArrayList<KYCcategorisationPojo>();

    public KYCcategorisation() {
        try {
            setToDt(getTodayDate());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            List brncode = new ArrayList();
            brncode = common.getAlphacodeBasedOnBranch(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(brnVector.get(1).toString())), brnVector.get(0).toString()));
                    //branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getacNature();

        } catch (Exception e) {
        }
    }

    public void getacNature() {
        try {
            acnoNatureList = new ArrayList<SelectItem>();
            acnoNatureList.add(new SelectItem("0", "--Select--"));
            List acNtureList = common.getAllNatureList();
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acnoNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("ALL", "ALL"));
            List acTypeList = common.getAccType(acnoNature);
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vec = (Vector) acTypeList.get(i);
                    acctTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getAcnoNature() {
        return acnoNature;
    }

    public void setAcnoNature(String acnoNature) {
        this.acnoNature = acnoNature;
    }

    public List<SelectItem> getAcnoNatureList() {
        return acnoNatureList;
    }

    public void setAcnoNatureList(List<SelectItem> acnoNatureList) {
        this.acnoNatureList = acnoNatureList;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public void viewPdfReport() {
        setMessage("");
        String toDate = "", branchName = "", address = "";
        try {
            if (acnoNature == null || acnoNature.equalsIgnoreCase("0")) {
                setMessage("Please select Account Nature !");
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper to Date");
                return;
            }

            toDate = ymd.format(dmy.parse(toDt));
            String report = "Risk Category Detail Report";
            Map fillParams = new HashMap();
            List brNameAdd = common.getBranchNameandAddress(branchCode);
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            fillParams.put("pReportDt", ymd.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);

            repList = remoteFacade.getKYCcategDetails(branchCode, acnoNature, acctType, toDate);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }

            new ReportBean().openPdf("Risk Category Detail Report_" + toDate, "RiskCategoryDetail", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {
        FastReportBuilder fastReport = new FastReportBuilder();
        String toDate = "";
        try {

            if (acnoNature == null || acnoNature.equalsIgnoreCase("0")) {
                setMessage("Please select Account Nature !");
                return null;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return null;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper to Date");
                return null;
            }

            toDate = ymd.format(dmy.parse(toDt));

            repList = remoteFacade.getKYCcategDetails(branchCode, acnoNature, acctType, toDate);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "A/c . Number", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name", 200);
            AbstractColumn kycCateg = ReportBean.getColumn("kycCateg", String.class, "Risk Category", 150);
            //AbstractColumn bal = ReportBean.getColumn("bal", BigDecimal.class, "Average Balance", 150);
            AbstractColumn orrRatingDt = ReportBean.getColumn("orrRatingDt", String.class, "Risk Rating Update Date", 90);
            AbstractColumn annualIncome = ReportBean.getColumn("annualIncome", BigDecimal.class, "Annual Income", 150);

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(kycCateg);
            width = width + kycCateg.getWidth();

            fastReport.addColumn(orrRatingDt);
            width = width + orrRatingDt.getWidth();

//            fastReport.addColumn(bal);
//            bal.setStyle(style);
//            width = width + 2 * bal.getWidth();

            fastReport.addColumn(annualIncome);
            annualIncome.setStyle(style);
            width = width + annualIncome.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Risk Category Detail Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), fastReport, "Risk Category Detail Report");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setAcnoNature("0");
        this.setToDt(getTodayDate());
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
