/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanSanctionDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class LoanSanctionDetail extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    // MiscReportFacadeRemote beanRemote;
    private String acType;
    private List<SelectItem> acTypeList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    TdReceiptManagementFacadeRemote RemoteCode;
    private LoanInterestCalculationFacadeRemote beanRemote = null;
    private final String jndiHomeName = "LoanInterestCalculationFacade";
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private LoanReportFacadeRemote loanRemote = null;
    private CommonReportMethodsRemote common;
    List tempList = null;
    Vector tempVector = null;
    List<LoanSanctionDetailPojo> reportList = new ArrayList<LoanSanctionDetailPojo>();

    public LoanSanctionDetail() {
        try {
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acTypeList = new ArrayList<SelectItem>();
            initData();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    private void initData() {
        try {
            tempList = beanRemote.getAcctType();
            acTypeList.add(new SelectItem("0", "--Select--"));
            acTypeList.add(new SelectItem("A", "ALL"));
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                acTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
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

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public void printAction() {
        String report = "Loan Sanction Detail Report";
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill To Date";
                return;
            }
            if (acType.equalsIgnoreCase("0")) {
                message = "Please select A/c Type";
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());

            reportList = loanRemote.getLoanSanctionDetail(branchCode, acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }

            new ReportBean().jasperReport("LoanSanctionDetail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Loan Sanction Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");


        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void refreshAction() {
        try {
            setMessage("");
            setAcType("0");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
