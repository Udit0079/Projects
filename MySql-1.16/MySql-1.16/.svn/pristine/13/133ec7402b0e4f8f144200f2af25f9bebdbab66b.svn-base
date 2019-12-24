/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.pojo.GuaranteeOnLoanPojo;
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
public class GauranteeOnLoan extends BaseBean {

    private String message;
    private String brCode;
    private List<SelectItem> branchCodeList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String acNo;
    private String newAcNo;
    private String visible;
    Date calDate = new Date();
    CommonReportMethodsRemote RemoteCode;
    DDSReportFacadeRemote ddsRemote;
    FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<GuaranteeOnLoanPojo> reportList = new ArrayList<GuaranteeOnLoanPojo>();

    public GauranteeOnLoan() {
        try {
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("ALL", "ALL"));
            reportTypeList.add(new SelectItem("AC", "Account Wise"));

            List branchCode = new ArrayList();
            branchCode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            // branchCodeList.add(new SelectItem("ALL"));
            if (!branchCode.isEmpty()) {
                for (int i = 0; i < branchCode.size(); i++) {
                    Vector brnVector = (Vector) branchCode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            
             this.visible = "none";
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onReportAction() {

        if (reportType.equalsIgnoreCase("ALL")) {
            this.visible = "none";
        } else {
            this.visible = "";
        }

    }

    public void acnoOnBulur() {

        try {
            Validator validate = new Validator();
            if (acNo.equalsIgnoreCase("")) {
                message = "Please enter the Folio No";
                return;
            } else if (acNo.length() < 12) {
                message = "Please enter the valid Folio No";
                return;
            } else if (!validate.validateStringAllNoSpace(acNo)) {
                message = "Please enter the valid Account No";
                return;
            }
            newAcNo = ftsRemote.getNewAccountNumber(this.acNo);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewPdfReport() {
        setMessage("");
        String report = "Guarantor Overdue";
        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = RemoteCode.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", dmy.format(calDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = ddsRemote.getGauranteeOnLoanData(brCode, ymd.format(calDate), reportType,acNo);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exit !");
            }

            new ReportBean().openPdf("Guarantor Overdue_" + ymd.format(calDate), "GaurantorOnLoan", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void printAction() {
        setMessage("");
        String report = "Guarantor Overdue";
        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = RemoteCode.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", dmy.format(calDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = ddsRemote.getGauranteeOnLoanData(brCode, ymd.format(calDate), reportType,acNo);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exit !");
            }

            new ReportBean().jasperReport("GaurantorOnLoan", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");


        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void refreshPage(){
        setMessage("");
        this.visible = "none";
    }

    public String exitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
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

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }
}
