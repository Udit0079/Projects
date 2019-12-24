/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.ho.LoanSchemeWiseDetailPojo;
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
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Raza
 */
public class LoanSchemeWiseDetail extends BaseBean {
    
    private String message;
    // Date calFromDate = new Date();
    Date caltoDate = new Date();
    private String acType;
    private List<SelectItem> acTypeList;
    private String schemeCode;
    private List<SelectItem> schemeCodeList;
    private String schemeCodeDesc;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private LoanReportFacadeRemote loanRemote = null;
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private TdReceiptManagementFacadeRemote RemoteCode;
    private final String jndiHomeName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote beanRemote = null;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private List<LoanSchemeWiseDetailPojo> repList = new ArrayList<LoanSchemeWiseDetailPojo>();
    
    public LoanSchemeWiseDetail() {
        try {
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getAccttype();
            //setSchemeCodeDesc("ALL");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void getAccttype() {
        try {
            List accList = new ArrayList();
            acTypeList = new ArrayList<SelectItem>();
           // accList = common.getAcctTypeNpaList();
            accList = common.getAcctTypeScheme();
            acTypeList.add(new SelectItem("ALL", "ALL"));
            if (!accList.isEmpty()) {
                for (int i = 0; i < accList.size(); i++) {
                    Vector acVector = (Vector) accList.get(i);
                    acTypeList.add(new SelectItem(acVector.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void setScheme() {
        try {
            List resultList7 = loanRemote.schemeCodeCombo(acType);
            schemeCodeList = new ArrayList<SelectItem>();
            schemeCodeList.add(new SelectItem("ALL"));
            if (!resultList7.isEmpty()) {
                for (int i = 0; i < resultList7.size(); i++) {
                    Vector ele7 = (Vector) resultList7.get(i);
                    schemeCodeList.add(new SelectItem(ele7.get(0).toString()));
                }
            }
            setSchemeCodeDesc("ALL");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void setSchemeDesc() {
        try {
            String schemeDesc = loanRemote.schemeCodeDesc(schemeCode);
            schemeCodeDesc = schemeDesc;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void printAction() {
        String report = "Loan Scheme Wise Detail Report";
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            
            if (caltoDate == null) {
                message = "Please Fill To Date";
                return;
            }
            if (acType.equalsIgnoreCase("0")) {
                message = "Please select A/c Type";
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            // String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = ToDate;
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());
            
            repList = loanRemote.getLoanSchemeDetail(branchCode, ymdFormat.format(caltoDate), acType, schemeCode);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }
            
            new ReportBean().jasperReport("LoanSchemeWiseDetail", "text/html",
                    new JRBeanCollectionDataSource(repList), fillParams, "Loan Scheme Wise Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
        
    }
    
    public void btnPdfAction() {
        String report = "Loan Scheme Wise Detail Report";
      
        try {
            
            if (caltoDate == null) {
                message = "Please Fill To Date";
                return;
            }
            if (acType.equalsIgnoreCase("0")) {
                message = "Please select A/c Type";
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            // String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = ToDate;
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());
            
            repList = loanRemote.getLoanSchemeDetail(branchCode, ymdFormat.format(caltoDate), acType, schemeCode);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }

//            new ReportBean().jasperReport("LoanSchemeWiseDetail", "text/html",
//                    new JRBeanCollectionDataSource(repList), fillParams, "Loan Scheme Wise Detail Report");
//            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
//            
            new ReportBean().openPdf("LoanSchemeWiseDetail_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "LoanSchemeWiseDetail", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
            
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void refreshAction() {
        try {
            setMessage("");
            setAcType("ALL");
            setSchemeCode("");
            setSchemeCodeDesc("");
            caltoDate = new Date();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public String exitAction() {
        return "case1";
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
    
    public Date getCaltoDate() {
        return caltoDate;
    }
    
    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getSchemeCode() {
        return schemeCode;
    }
    
    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }
    
    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }
    
    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }
    
    public String getSchemeCodeDesc() {
        return schemeCodeDesc;
    }
    
    public void setSchemeCodeDesc(String schemeCodeDesc) {
        this.schemeCodeDesc = schemeCodeDesc;
    }
}
