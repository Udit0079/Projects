/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ckycr;

import com.cbs.facade.ckycr.CkycrViewMgmtFacadeRemote;
import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CKYCRStatusDetail extends BaseBean {

    private String successMsg;
    private String errorMsg;
    private String fuction;
    private String customerId;
    private String dateOfBirth;
    private List<SelectItem> fuctionList;
    private boolean customerIdDisable;
    private String updateBtnDispaly = "none";
    private String resendBtnDispaly = "none";
    private String reportBtnDispaly = "none";
    private CkycrViewMgmtFacadeRemote ckycrRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public CKYCRStatusDetail() {
        try {
            ckycrRemote = (CkycrViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrViewMgmtFacade");
            resendBtnDispaly = "none";
            updateBtnDispaly = "none";
            reportBtnDispaly = "none";

            fuctionList = new ArrayList<SelectItem>();
            fuctionList.add(new SelectItem("SELECT", "--Select--"));
            fuctionList.add(new SelectItem("UPDATE", "UPDATE"));
            fuctionList.add(new SelectItem("RESEND", "RESEND"));
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        if (fuction.equalsIgnoreCase("UPDATE")) {
            this.customerIdDisable = false;
            resendBtnDispaly = "none";
            updateBtnDispaly = "";
            reportBtnDispaly = "none";
        }
        if (fuction.equalsIgnoreCase("RESEND")) {
            this.customerIdDisable = false;
            resendBtnDispaly = "";
            updateBtnDispaly = "none";
            reportBtnDispaly = "none";
        }
        if (fuction.equalsIgnoreCase("FAILURE-R")) {
            this.customerIdDisable = true;
            resendBtnDispaly = "none";
            updateBtnDispaly = "none";
            reportBtnDispaly = "";
        }
    }

    public void onblurCustId() {
        try {
            setErrorMsg("");
            setSuccessMsg("");
            CKYCRRequestPojo ckycrDetailsByCustId = (CKYCRRequestPojo) ckycrRemote.getCKYCRDetailByCustId(customerId);
            if (ckycrDetailsByCustId.getCustomerId() == null) {
                setErrorMsg("Sorry! This customer id is invalid or not exist.");
                return;
            }
            if (!getOrgnBrCode().equalsIgnoreCase(ckycrDetailsByCustId.getPrimaryBrCode())) {
                setErrorMsg("Sorry! This customer id is not related to your branch.");
                return;
            }

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void updateReloadAction() {
        try {
            setErrorMsg("");
            String msg = "";
            CKYCRRequestPojo ckycrDetailsByCustId = (CKYCRRequestPojo) ckycrRemote.getCKYCRDetailByCustId(customerId);
            if (validate().equalsIgnoreCase("ok")) {
                if (!getOrgnBrCode().equalsIgnoreCase(ckycrDetailsByCustId.getPrimaryBrCode())) {
                    setErrorMsg("Sorry! This customer id is not related to your branch.");
                    setSuccessMsg("");
                    return;
                }
                if (fuction.equalsIgnoreCase("UPDATE")) {
                    if (!(ckycrDetailsByCustId.getStatus().equalsIgnoreCase("03")
                            || ckycrDetailsByCustId.getStatus().equalsIgnoreCase("01"))) {
                        setErrorMsg("Sorry! Can't update This customer.");
                    } else if (ckycrDetailsByCustId.getStatus().equalsIgnoreCase("03")
                            || ckycrDetailsByCustId.getStatus().equalsIgnoreCase("01")) {
                        msg = ckycrRemote.updateCKYCRDetailByCustId(customerId, fuction, getUserName());
                        setSuccessMsg(msg);
                    }
                } else if (fuction.equalsIgnoreCase("RESEND")) {
                    if (!(ckycrDetailsByCustId.getStatus().equalsIgnoreCase("FAILURE")
                            || ckycrDetailsByCustId.getStatus().equalsIgnoreCase("02"))) {
                        setErrorMsg("Sorry! Can't Resend This customer.");
                    } else if (ckycrDetailsByCustId.getStatus().equalsIgnoreCase("FAILURE")
                            || ckycrDetailsByCustId.getStatus().equalsIgnoreCase("02")) {
                        msg = ckycrRemote.updateCKYCRDetailByCustId(customerId, fuction, getUserName());
                        setSuccessMsg(msg);
                    }
                }
            }
            refreshForm();
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public String validate() {
        setSuccessMsg("");
        if (fuction.equalsIgnoreCase("") || fuction == null) {
            setErrorMsg("Please Select Function.!");
            return "Please Select Function.!";
        }
        if (customerId.equalsIgnoreCase("") || customerId == null) {
            setErrorMsg("Please fill Customer Id.!");
            return "Please fill Customer Id.!";
        }
        return "ok";
    }

    public void btnHtmlAction() {
        try {
            List<CKYCRRequestPojo> ckycrFailureReport = ckycrRemote.getCKYCRFailureRequestReport(this.getOrgnBrCode());
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParam = new HashMap();
            fillParam.put("pReportName", "CKYCR Failure Request Report");
            fillParam.put("pPrintedBy", getUserName());
            fillParam.put("pBankName", branchNameandAddress.get(0));
            fillParam.put("pBankAdd", branchNameandAddress.get(1));

            new ReportBean().jasperReport("CKYCRFailureRequestReport", "text/html",
                    new JRBeanCollectionDataSource(ckycrFailureReport), fillParam, "CKYCR Failure Request Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }

    }

    public void btnPdfAction() {
        try {
            List<CKYCRRequestPojo> ckycrFailureReport = ckycrRemote.getCKYCRFailureRequestReport(fuction);
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParam = new HashMap();
            fillParam.put("pReportName", "CKYCR Failure Request Report");
            fillParam.put("pPrintedBy", getUserName());
            fillParam.put("pBankName", branchNameandAddress.get(0));
            fillParam.put("pBankAdd", branchNameandAddress.get(1));
            new ReportBean().openPdf("CKYCR Failure Request Report_" + ymd.format(dmy.parse(this.getTodayDate())), "CKYCRFailureRequestReport", new JRBeanCollectionDataSource(ckycrFailureReport), fillParam);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "CKYCR Failure Request Report");

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.fuction = "SELECT";
        this.customerId = "";
        this.customerIdDisable = false;
    }

    public String exitForm() {
        return "case1";
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isCustomerIdDisable() {
        return customerIdDisable;
    }

    public void setCustomerIdDisable(boolean customerIdDisable) {
        this.customerIdDisable = customerIdDisable;
    }

    public String getFuction() {
        return fuction;
    }

    public void setFuction(String fuction) {
        this.fuction = fuction;
    }

    public List<SelectItem> getFuctionList() {
        return fuctionList;
    }

    public void setFuctionList(List<SelectItem> fuctionList) {
        this.fuctionList = fuctionList;
    }

    public String getUpdateBtnDispaly() {
        return updateBtnDispaly;
    }

    public void setUpdateBtnDispaly(String updateBtnDispaly) {
        this.updateBtnDispaly = updateBtnDispaly;
    }

    public String getResendBtnDispaly() {
        return resendBtnDispaly;
    }

    public void setResendBtnDispaly(String resendBtnDispaly) {
        this.resendBtnDispaly = resendBtnDispaly;
    }

    public String getReportBtnDispaly() {
        return reportBtnDispaly;
    }

    public void setReportBtnDispaly(String reportBtnDispaly) {
        this.reportBtnDispaly = reportBtnDispaly;
    }
}
