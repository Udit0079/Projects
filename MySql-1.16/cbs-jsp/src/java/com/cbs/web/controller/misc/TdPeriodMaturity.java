/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.RdInterestDTO;
import com.cbs.dto.report.TdPeriodMaturityPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class TdPeriodMaturity extends BaseBean {
    // String orgnBrCode;
    // HttpServletRequest req;

    private String errorMsg;
    private String codeBr;
    private List<SelectItem> branchCodeList;
    private String toDt;
    private String acnature;
    private List<SelectItem> acnatureList;
    Date dt = new Date();
    private TdReceiptManagementFacadeRemote tdFacade;
    private CommonReportMethodsRemote common;
    List<TdPeriodMaturityPojo> repDataList = new ArrayList<TdPeriodMaturityPojo>();
    List<RdInterestDTO> repDataList1 = new ArrayList<RdInterestDTO>();

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getCodeBr() {
        return codeBr;
    }

    public void setCodeBr(String codeBr) {
        this.codeBr = codeBr;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getAcnature() {
        return acnature;
    }

    public void setAcnature(String acnature) {
        this.acnature = acnature;
    }

    public List<SelectItem> getAcnatureList() {
        return acnatureList;
    }

    public void setAcnatureList(List<SelectItem> acnatureList) {
        this.acnatureList = acnatureList;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public TdPeriodMaturity() {
        try {
            setToDt(sdf.format(dt));

            tdFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List acTLst = new ArrayList();
            acTLst = common.getBranchCodeList(getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!acTLst.isEmpty()) {
                for (int i = 0; i < acTLst.size(); i++) {
                    Vector ele1 = (Vector) acTLst.get(i);
                    branchCodeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
            List acCodeList = common.getAcctypeNatureconstant();
            acnatureList = new ArrayList<SelectItem>();
            acnatureList.add(new SelectItem("0", "--Select--"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                acnatureList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
            }

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void viewReport() {
        setErrorMsg("");
        String bankName = "", bankAddress = "";
        if (this.toDt == null) {
            setErrorMsg("Please Fill To Date.");
        }

        boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
        if (result == false) {
            this.setErrorMsg("Please Fill proper As On Date !");
            return;
        }

        Map fillParams = new HashMap();
        try {
            List dataList1;
            if (this.codeBr.equalsIgnoreCase("A")) {
                dataList1 = common.getBranchNameandAddress("90");
            } else {
                dataList1 = common.getBranchNameandAddress(this.codeBr);
            }
            if (this.acnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acnature.equalsIgnoreCase(CbsConstant.MS_AC) || acnature.equalsIgnoreCase(CbsConstant.TD_AC)) {

                String report = "TD MATURITY REPORT";
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                fillParams.put("pReportToDate", this.toDt);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("bankName", bankName);
                fillParams.put("bankAddr", bankAddress);

                repDataList = tdFacade.gettdPeriodMaturityList(ymd.format(sdf.parse(this.getToDt())), this.codeBr, acnature);
                if (repDataList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                new ReportBean().jasperReport("tdPeriodMaturity", "text/html",
                        new JRBeanCollectionDataSource(repDataList), fillParams, "TD MATURITY REPORT");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);

            } else if (acnature.equalsIgnoreCase("RD")) {

                String report = "RD MATURITY REPORT";
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                fillParams.put("pReportToDate", this.toDt);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("bankName", bankName);
                fillParams.put("bankAddr", bankAddress);

                repDataList1 = tdFacade.getRdperiodmaturityList(ymd.format(sdf.parse(this.getToDt())), this.codeBr);
                if (repDataList1.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                new ReportBean().jasperReport("rdPeriodMaturity", "text/html",
                        new JRBeanCollectionDataSource(repDataList1), fillParams, "RD MATURITY REPORT");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewPdfReport() {
        setErrorMsg("");
        String bankName = "", bankAddress = "";
        if (this.toDt == null) {
            setErrorMsg("Please Fill To Date.");
        }

        boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
        if (result == false) {
            this.setErrorMsg("Please Fill proper As On Date !");
            return;
        }

        Map fillParams = new HashMap();
        try {
            List dataList1;
            if (this.codeBr.equalsIgnoreCase("A")) {
                dataList1 = common.getBranchNameandAddress("90");
            } else {
                dataList1 = common.getBranchNameandAddress(this.codeBr);
            }
            if (this.acnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acnature.equalsIgnoreCase(CbsConstant.MS_AC) || acnature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                String report = "TD MATURITY REPORT";
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                fillParams.put("pReportToDate", this.toDt);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("bankName", bankName);
                fillParams.put("bankAddr", bankAddress);

                repDataList = tdFacade.gettdPeriodMaturityList(ymd.format(sdf.parse(this.getToDt())), this.codeBr, acnature);
                if (repDataList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                new ReportBean().openPdf("TD MATURITY REPORT_" + ymd.format(sdf.parse(getTodayDate())), "tdPeriodMaturity", new JRBeanCollectionDataSource(repDataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);

            } else if (acnature.equalsIgnoreCase("RD")) {
                String report = "RD MATURITY REPORT";
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                fillParams.put("pReportToDate", this.toDt);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("bankName", bankName);
                fillParams.put("bankAddr", bankAddress);

                repDataList1 = tdFacade.getRdperiodmaturityList(ymd.format(sdf.parse(this.getToDt())), this.codeBr);
                if (repDataList1.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                new ReportBean().openPdf("RD MATURITY REPORT_" + ymd.format(sdf.parse(getTodayDate())), "rdPeriodMaturity", new JRBeanCollectionDataSource(repDataList1), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}