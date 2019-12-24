/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.clg;

import com.cbs.dto.report.InwardClearingChequeReturnPojo;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.report.ClgReportFacadeRemote;
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
 * @author Athar Reza
 */
public class InwardClearingChequeReturn extends BaseBean {

    private String errorMsg;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    // private TdReceiptManagementFacadeRemote RemoteCode;
    private CommonReportMethodsRemote common;
    private ClgReportFacadeRemote beanRemote;
    private UploadNeftRtgsMgmtFacadeRemote nrRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<InwardClearingChequeReturnPojo> resultlist = new ArrayList<InwardClearingChequeReturnPojo>();

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

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
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

    public InwardClearingChequeReturn() {
        try {
            beanRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            nrRemote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("UploadNeftRtgsMgmtFacade");
            frmDt = dmy.format(date);
            toDt = dmy.format(date);

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("Inward", "Inward"));
            reportTypeList.add(new SelectItem("Outward", "Outward"));

        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public boolean validate() {
        try {
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check from date";
                return false;
            }
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check to date";
                return false;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                errorMsg = "From date should be less then to date";
                return false;
            }

        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
    }

    public void viewReport() {
        if (validate()) {
            String branchName = "";
            String address = "";
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                String report = "Cheque Returned Memo";
                List brNameAdd = new ArrayList();
                brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }
                if (reportType == null || reportType.equalsIgnoreCase("S")) {
                    setErrorMsg("Please Select Report Type.");
                    return;
                }
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pbankName", branchName);
                fillParams.put("pbankAddress", address);
                fillParams.put("pReportDt", frmDt + " to " + toDt);
                resultlist = beanRemote.getInwardClearingCheque((fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), branchCode, reportType);
                if (resultlist.isEmpty()) {
                    errorMsg = "Data does not exit";
                    return;
                }

                String bankCode = "", jrxmlName = "";
                List<MbSmsSenderBankDetail> bankList = nrRemote.getBankCode();
                if (!bankList.isEmpty()) {
                    MbSmsSenderBankDetail bankEntity = bankList.get(0);
                    bankCode = bankEntity.getBankCode();
                }

                if (bankCode.equalsIgnoreCase("KHAT")) {
                    jrxmlName = "ChequeReturnedMemo";
                } else {
                    jrxmlName = "InwardClearingChequeReturnReport";
                }

                new ReportBean().jasperReport(jrxmlName, "text/html",
                        new JRBeanCollectionDataSource(resultlist), fillParams, "Cheque Returned Memo");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);

            } catch (Exception e) {
                e.printStackTrace();
                setErrorMsg(e.getLocalizedMessage());
            }
        }
    }

    public void btnPdfAction() {
        if (validate()) {
            String branchName = "";
            String address = "";
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                String report = "Cheque Returned Memo";
                List brNameAdd = new ArrayList();
                brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }
                if (reportType == null || reportType.equalsIgnoreCase("S")) {
                    setErrorMsg("Please Select Report Type.");
                    return;
                }
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pbankName", branchName);
                fillParams.put("pbankAddress", address);
                fillParams.put("pReportDt", frmDt + " to " + toDt);

                resultlist = beanRemote.getInwardClearingCheque((fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), branchCode, reportType);
                if (resultlist.isEmpty()) {
                    errorMsg = "Data does not exit";
                    return;
                }
                String bankCode = "", jrxmlName = "";
                List<MbSmsSenderBankDetail> bankList = nrRemote.getBankCode();
                if (!bankList.isEmpty()) {
                    MbSmsSenderBankDetail bankEntity = bankList.get(0);
                    bankCode = bankEntity.getBankCode();
                }
                if (bankCode.equalsIgnoreCase("KHAT")) {
                    jrxmlName = "ChequeReturnedMemo";
                } else {
                    jrxmlName = "InwardClearingChequeReturnReport";
                }
                new ReportBean().openPdf("CHEQUE RETURN MEMO_" + ymd.format(dmy.parse(getTodayDate())), jrxmlName, new JRBeanCollectionDataSource(resultlist), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);

            } catch (Exception e) {
                e.printStackTrace();
                setErrorMsg(e.getLocalizedMessage());
            }
        }
    }

    public void refresh() {
        try {
            this.setErrorMsg("");
            frmDt = dmy.format(new Date());
            toDt = dmy.format(new Date());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
