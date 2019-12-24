/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.LoanAcDetailStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class LoanAcDetailStatement extends BaseBean {

    private String message;
    private String allAccount;
    private List<SelectItem> allAccountList;
    private String accountType;
    private boolean disableAcctType;
    private List<SelectItem> accountTypeList;
    private String accountWise;
    private List<SelectItem> accountWiseList;
    private String oldAccNo;
    private boolean disableAcctNo;
    private String accountNo;
    private String frmDt;
    private boolean disableFrmDt;
    private String toDt;
    Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private LoanInterestCalculationFacadeRemote beanRemote = null;
    private final String jndiHomeName = "LoanInterestCalculationFacade";
    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private LoanReportFacadeRemote loanRemote = null;
    private final String jndiHomeNameLoan = "LoanReportFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomenameFtsPost = "FtsPostingMgmtFacade";
    List<LoanAcDetailStatementPojo> reportList = new ArrayList<LoanAcDetailStatementPojo>();

    public boolean isDisableFrmDt() {
        return disableFrmDt;
    }

    public void setDisableFrmDt(boolean disableFrmDt) {
        this.disableFrmDt = disableFrmDt;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getAccountWise() {
        return accountWise;
    }

    public void setAccountWise(String accountWise) {
        this.accountWise = accountWise;
    }

    public List<SelectItem> getAccountWiseList() {
        return accountWiseList;
    }

    public void setAccountWiseList(List<SelectItem> accountWiseList) {
        this.accountWiseList = accountWiseList;
    }

    public String getAllAccount() {
        return allAccount;
    }

    public void setAllAccount(String allAccount) {
        this.allAccount = allAccount;
    }

    public List<SelectItem> getAllAccountList() {
        return allAccountList;
    }

    public void setAllAccountList(List<SelectItem> allAccountList) {
        this.allAccountList = allAccountList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDisableAcctNo() {
        return disableAcctNo;
    }

    public void setDisableAcctNo(boolean disableAcctNo) {
        this.disableAcctNo = disableAcctNo;
    }

    public boolean isDisableAcctType() {
        return disableAcctType;
    }

    public void setDisableAcctType(boolean disableAcctType) {
        this.disableAcctType = disableAcctType;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public LoanAcDetailStatement() {

        try {
            this.setFrmDt(dmy.format(date));
            this.setToDt(dmy.format(date));
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoan);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomenameFtsPost);
            setDisableAcctType(true);
            setDisableAcctNo(true);
            getAcType();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAcType() {
        try {

            allAccountList = new ArrayList<SelectItem>();
            allAccountList.add(new SelectItem("A", "All Account"));
            accountWiseList = new ArrayList<SelectItem>();
            accountWiseList.add(new SelectItem("I", "Account Wise"));

            List acTypeList = new ArrayList();
            acTypeList = beanRemote.getAcctType();
            if (!acTypeList.isEmpty()) {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", ""));
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector ele = (Vector) acTypeList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getdisableAcctType() {
        this.setMessage("");
        try {
            this.setDisableAcctType(false);
            this.setAccountWise("");
            this.setAccountNo("");
            this.setDisableAcctNo(true);
            this.setDisableFrmDt(true);
            this.setOldAccNo("");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void disableAcctTypeNo() {
        this.setMessage("");
        try {
            this.setDisableAcctNo(false);
            this.setAllAccount("");
            setAccountType("0");
            this.setDisableAcctType(true);
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                setMessage("Please Enter Your Account No!");
                return;
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void getdisableAcctTypeNo() {
        this.setMessage("");
        try {

            this.setDisableAcctType(true);
            this.setAllAccount("");
            setAccountType("0");
            // this.setDisableAcctNo(false);
            if (oldAccNo.length() < 12) {
                setMessage("Please Enter 12 digits Account No!");
                return;
            }
            oldAccNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
            this.accountNo = oldAccNo;

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry.Got a null request from faces context");
        }
        return request;
    }

    public void viewReport() {
        String brnName = "";
        String brnAdd = "";
        String report = "Loan Account Detail Statement(ALL)";
        String report1 = "Loan Account Detail Statement(Individual)";

        try {
            List bnkaddList = new ArrayList();
            bnkaddList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bnkaddList.isEmpty()) {
                brnName = (String) bnkaddList.get(0);
                brnAdd = (String) bnkaddList.get(1);
            }
            Map fillParams = new HashMap();  
            fillParams.put("pBrName", brnName);
            fillParams.put("pBrnAdd", brnAdd);
            fillParams.put("pPrintedBy", getUserName());
            if (accountType.equalsIgnoreCase(accountType)&& !accountType.equalsIgnoreCase("0")) {
                fillParams.put("pReportDt", toDt);
                fillParams.put("pAcType", this.accountType);
                fillParams.put("pReportName", report);
            } else {
                fillParams.put("pReportName", report1);
                fillParams.put("pReportDt", frmDt + " to " + toDt);
                fillParams.put("pAcNo", this.accountNo);
            }
            String frDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));

            reportList = loanRemote.getLoanAcDetailStmt(getOrgnBrCode(), accountType, accountNo, frDate, toDate);
            if(reportList.isEmpty()){
                throw new ApplicationException("Data does not exist!!!");
            }

            if (accountType.equalsIgnoreCase(accountType) && !accountType.equalsIgnoreCase("0")) {

                new ReportBean().jasperReport("AllLoanAccountDetail", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            } else {

                new ReportBean().jasperReport("LoanAccountDetailStmt", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report1);
            }


        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void pdfDownLoad(){
        String brnName = "";
        String brnAdd = "";
        String report = "Loan Account Detail Statement(ALL)";
        String report1 = "Loan Account Detail Statement(Individual)";

        try {
            List bnkaddList = new ArrayList();
            bnkaddList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bnkaddList.isEmpty()) {
                brnName = (String) bnkaddList.get(0);
                brnAdd = (String) bnkaddList.get(1);
            }
            Map fillParams = new HashMap();  
            fillParams.put("pBrName", brnName);
            fillParams.put("pBrnAdd", brnAdd);
            fillParams.put("pPrintedBy", getUserName());
            if (accountType.equalsIgnoreCase(accountType)&& !accountType.equalsIgnoreCase("0")) {
                fillParams.put("pReportDt", toDt);
                fillParams.put("pAcType", this.accountType);
                fillParams.put("pReportName", report);
            } else {
                fillParams.put("pReportName", report1);
                fillParams.put("pReportDt", frmDt + " to " + toDt);
                fillParams.put("pAcNo", this.accountNo);
            }
            String frDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));

            reportList = loanRemote.getLoanAcDetailStmt(getOrgnBrCode(), accountType, accountNo, frDate, toDate);
            if(reportList.isEmpty()){
                throw new ApplicationException("Data does not exist!!!");
            }

            if (accountType.equalsIgnoreCase(accountType) && !accountType.equalsIgnoreCase("0")) {
                new ReportBean().openPdf("AllLoanAccountDetail", "text/html", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                new ReportBean().openPdf("LoanAccountDetailStmt", "text/html", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report1);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        } 
    }

    public void btnRefreshAction() {
        try {
            setDisableAcctNo(false);
            this.setMessage("");
            setAllAccount("");
            setAccountWise("");
            setAccountNo("");
            this.setOldAccNo("");
            setAccountType("0");
            setFrmDt(dmy.format(date));
            setToDt(dmy.format(date));
            setDisableFrmDt(false);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String btnExitAction() {
        return "case1";
    }
}
