/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.DeletedTransactionPojo;
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
 * @author Admin
 */
public class DeletedTransaction extends BaseBean {

    private String message;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String acNo, acNoLen;
    private String branch;
    private List<SelectItem> branchList;
    private String frDt;
    private String toDt;
    private Boolean disableType;
    private Date date = new Date();
    private TdReceiptManagementFacadeRemote RemoteCode;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<DeletedTransactionPojo> reportList = new ArrayList<DeletedTransactionPojo>();

    public DeletedTransaction() {
        try {

            setFrDt(dmy.format(date));
            setToDt(dmy.format(date));
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            this.setAcNoLen(getAcNoLength());
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("A", "Account"));
            reportTypeList.add(new SelectItem("IU", "Individual UserId"));
            reportTypeList.add(new SelectItem("AU", "All UserId"));


//            branchList = new ArrayList<SelectItem>();
//            List brncode = new ArrayList();
//            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
//            if (!brncode.isEmpty()) {
//                for (int i = 0; i < brncode.size(); i++) {
//                    Vector brnVector = (Vector) brncode.get(i);
//                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
//                }
//            }

            setBranch("User Id");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onReportType() {
        setMessage("");
        if (reportType.equalsIgnoreCase("A")) {
            branch = "Account No.";
        } else {
            branch = "User Id";
        }
        if (reportType.equalsIgnoreCase("AU")) {
            this.disableType = true;
        } else {
            this.disableType = false;
        }
    }

    public void printAction() {
        setMessage("");
        try {
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                setMessage("Please Select Report Type !");
                return;
            }

            if (reportType.equalsIgnoreCase("A")) {
                //if (acNo.length() != 12) {
                if ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                    setMessage("Please fill Proper Account No !");
                    return;
                }
            } else {
                if (reportType.equalsIgnoreCase("IU")) {
                    if (acNo.equalsIgnoreCase("")) {
                        setMessage("Please fill User Id !");
                        return;
                    }
                }
            }

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(frDt)) {
                setMessage("Please fill proper from date ! ");
                return;
            }

            if (dmy.parse(frDt).after(getDate())) {
                setMessage("From date should be less than current date !");
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(toDt)) {
                setMessage("Please fill proper to date ! ");
                return;
            }

            if (dmy.parse(toDt).after(getDate())) {
                setMessage("To date should be less than current date !");
                return;
            }

            if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less than current To date !");
                return;
            }
            String report = "Deleted Transaction detail";
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", frDt + " to " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = remoteFacade.getDeletedTransactionData(branch, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), acNo, reportType);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("Deleted_Transaction", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Deleted Transaction detail");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        try {
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                setMessage("Please Select Report Type !");
                return;
            }

            if (reportType.equalsIgnoreCase("A")) {
                //if (acNo.length() != 12) {
                if ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                    setMessage("Please fill Account No !");
                    return;
                }
            } else {
                if (reportType.equalsIgnoreCase("IU")) {
                    if (acNo.equalsIgnoreCase("")) {
                        setMessage("Please fill User Id !");
                        return;
                    }
                }

            }
            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(frDt)) {
                setMessage("Please fill proper from date ! ");
                return;
            }

            if (dmy.parse(frDt).after(getDate())) {
                setMessage("from date should be less than current date !");
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(toDt)) {
                setMessage("Please fill proper to date ! ");
                return;
            }

            if (dmy.parse(toDt).after(getDate())) {
                setMessage("to date should be less than current date !");
                return;
            }

            if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less than current To date !");
                return;
            }
            String report = "Deleted Transaction detail";
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", frDt + " to " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = remoteFacade.getDeletedTransactionData(branch, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), acNo, reportType);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf("Deleted Transaction detail", "Deleted_Transaction", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Deleted Transaction detail");

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        setReportType("S");
        setAcNo("");
        setFrDt(dmy.format(date));
        setToDt(dmy.format(date));
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public Boolean getDisableType() {
        return disableType;
    }

    public void setDisableType(Boolean disableType) {
        this.disableType = disableType;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
}
