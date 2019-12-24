/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanPeriodPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
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
public class npaInterest extends BaseBean {

    Date dt = new Date();
    private String errorMsg;
    private String selectRepType;
    private List<SelectItem> selectRepTpList;
    private String selectAcType;
    private List<SelectItem> selectAcTpList;
    Date frmDt;
    Date toDt;
    private LoanReportFacadeRemote loanFacade;
    private CommonReportMethodsRemote common;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    List<LoanPeriodPojo> repDataList = new ArrayList<LoanPeriodPojo>();

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<SelectItem> getSelectAcTpList() {
        return selectAcTpList;
    }

    public void setSelectAcTpList(List<SelectItem> selectAcTpList) {
        this.selectAcTpList = selectAcTpList;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public List<SelectItem> getSelectRepTpList() {
        return selectRepTpList;
    }

    public void setSelectRepTpList(List<SelectItem> selectRepTpList) {
        this.selectRepTpList = selectRepTpList;
    }

    public String getSelectRepType() {
        return selectRepType;
    }

    public void setSelectRepType(String selectRepType) {
        this.selectRepType = selectRepType;
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public npaInterest() {
        try {
            setFrmDt(dt);
            setToDt(dt);

            loanFacade = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            List acTLst = new ArrayList();
            acTLst = loanFacade.getAccList();
            selectAcTpList = new ArrayList<SelectItem>();
            if (!acTLst.isEmpty()) {
                for (int i = 0; i < acTLst.size(); i++) {
                    Vector ele7 = (Vector) acTLst.get(i);
                    selectAcTpList.add(new SelectItem(ele7.get(0).toString(), ele7.get(0).toString()));
                }
            }

            selectRepTpList = new ArrayList<SelectItem>();
            selectRepTpList.add(new SelectItem("Memorandum", "Interest"));
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void viewReport() {
        setErrorMsg("");
        String bankName = "", bankAddress = "";
        if (this.frmDt == null) {
            setErrorMsg("Please Fill From Date.");
        }
        if (this.toDt == null) {
            setErrorMsg("Please Fill To Date.");
        }

        Map fillParams = new HashMap();
        try {
            String report = "NPA INTEREST REPORT";
            if (!this.branchCode.equalsIgnoreCase("0A")) {
                List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
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

            //fillParams.put("pReportFrDate", sdf.format(this.frmDt));
            fillParams.put("pReportToDate", sdf.format(this.frmDt) + " to " + sdf.format(this.toDt));
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("bankName", bankName);
            fillParams.put("bankAddr", bankAddress);
            fillParams.put("repType", this.getSelectRepType());

            repDataList = loanFacade.npaInterestData(this.getSelectRepType(), this.getSelectAcType(), ymd.format(this.getFrmDt()), ymd.format(this.getToDt()), this.branchCode);
            new ReportBean().jasperReport("npaIntRep", "text/html",
                    new JRBeanCollectionDataSource(repDataList), fillParams, "NPA INTEREST REPORT");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setErrorMsg("");
        String bankName = "", bankAddress = "";
        if (this.frmDt == null) {
            setErrorMsg("Please Fill From Date.");
        }
        if (this.toDt == null) {
            setErrorMsg("Please Fill To Date.");
        }

        Map fillParams = new HashMap();
        try {
            String report = "NPA INTEREST REPORT";
            if (!this.branchCode.equalsIgnoreCase("0A")) {
                List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
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

            //fillParams.put("pReportFrDate", sdf.format(this.frmDt));
            fillParams.put("pReportToDate", sdf.format(this.frmDt) + " to " + sdf.format(this.toDt));
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("bankName", bankName);
            fillParams.put("bankAddr", bankAddress);
            fillParams.put("repType", this.getSelectRepType());

            repDataList = loanFacade.npaInterestData(this.getSelectRepType(), this.getSelectAcType(), ymd.format(this.getFrmDt()), ymd.format(this.getToDt()), this.branchCode);

            new ReportBean().openPdf("NPA INTEREST REPORT_" + ymd.format(this.getFrmDt()) + " to " + ymd.format(this.getToDt()), "npaIntRep", new JRBeanCollectionDataSource(repDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String exitAction() {
        refresh();
        return "case1";
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setFrmDt(dt);
        this.setToDt(dt);
    }
}