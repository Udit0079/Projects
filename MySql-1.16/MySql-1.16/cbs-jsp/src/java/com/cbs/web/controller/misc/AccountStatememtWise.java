/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;



import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Nishant Srivastava
 */
public class AccountStatememtWise extends BaseBean  {
  
    private String message;
    MiscReportFacadeRemote beanRemote;
    private String accountNo;
    private String fromDate;
    private String toDate;
    private String lastStmtFromDate;
    private String lastStmtToDate;
    private String lastStmtPrintedDate;
    private String lastStmtPrintedBy;
    private String newAcNo;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }    

    public String getLastStmtFromDate() {
        return lastStmtFromDate;
    }

    public void setLastStmtFromDate(String lastStmtFromDate) {
        this.lastStmtFromDate = lastStmtFromDate;
    }

    public String getLastStmtPrintedDate() {
        return lastStmtPrintedDate;
    }

    public void setLastStmtPrintedDate(String lastStmtPrintedDate) {
        this.lastStmtPrintedDate = lastStmtPrintedDate;
    }

    public String getLastStmtPrintedBy() {
        return lastStmtPrintedBy;
    }

    public void setLastStmtPrintedBy(String lastStmtPrintedBy) {
        this.lastStmtPrintedBy = lastStmtPrintedBy;
    }

    public String getLastStmtToDate() {
        return lastStmtToDate;
    }

    public void setLastStmtToDate(String lastStmtToDate) {
        this.lastStmtToDate = lastStmtToDate;
    }   

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }    

    public AccountStatememtWise () {
        try {
            this.setFromDate(getTodayDate());
            this.setToDate(getTodayDate());
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onBlurAccountNumber() {
        try {
            setMessage("");
            setLastStmtFromDate("");
            setLastStmtPrintedDate("");
            setLastStmtPrintedBy("");
            setLastStmtToDate("");
            if (accountNo.length() != 12) {
                setMessage("Please Enter 12 Digit Account No");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(accountNo);
            this.newAcNo = accountNo;
            List ele = beanRemote.getAccountDetails(accountNo);
            if (ele != null) {
                if (ele.get(0) != null) {
                    setLastStmtFromDate(ele.get(0).toString());
                }
                if (ele.get(1) != null) {
                    setLastStmtToDate(ele.get(1).toString());
                }
                if (ele.get(2) != null) {
                    setLastStmtPrintedBy(ele.get(2).toString());
                }
                if (ele.get(3) != null) {
                    setLastStmtPrintedDate(ele.get(3).toString());
                }
            } else {
                setMessage("No last statement record exists !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        fromDate = fromDate.substring(6)+fromDate.substring(3, 5)+fromDate.substring(0, 2);
        toDate = toDate.substring(6)+toDate.substring(3, 5)+toDate.substring(0, 2);
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
                        List<AccountStatementReportPojo> accountStatementpojo = beanRemote.getAccountStatementReportWise(this.newAcNo, fromDate, toDate);
            System.out.println(accountStatementpojo.size());
            int rows = accountStatementpojo.size();
            if (accountStatementpojo == null) {
                setMessage("Account number does not exists !!");
                return null;
            }
            if (!accountStatementpojo.isEmpty()) {
                AccountStatementReportPojo getEle = accountStatementpojo.get(accountStatementpojo.size() - 1);
                double closingBalance = getEle.getBalance();
                double availableBalance = closingBalance - getEle.getPendingBal();
                String bankName = "";
                String branchAddress = "";
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(this.accountNo.substring(0,2));
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "Account Statement";
                Map fillParams = new HashMap();
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("pStmtPeriod", dmyFormat.format(ymdFormat.parse(fromDate)) + " to " + dmyFormat.format(ymdFormat.parse(toDate)));
                fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pClosingBalance", closingBalance);
                fillParams.put("pAvailableBalance", availableBalance);
                
                String acNature = getEle.getAcNature();
                if(acNature.equalsIgnoreCase("DS")){
                    fillParams.put("vChqNo", "Receipt No.");
                }else{
                    fillParams.put("vChqNo", "Cheque No.");
                }                
                
                HttpSession session = getHttpSession();
                session.setAttribute("actualFromDt", fromDate);
                session.setAttribute("actualToDt", toDate);
                session.setAttribute("custNo", this.newAcNo);
                session.setAttribute("rows", rows);
                ReportBean reportBean = new ReportBean();
                reportBean.jasperReport("ACCOUNTSTATEMENTWISE", "text/html", new JRBeanCollectionDataSource(accountStatementpojo), fillParams, repName);
                session.setAttribute("pages", reportBean.TOTAL_NO_OF_PAGES_IN_REPORT);
                return "report";
            } else {
                setMessage("No data to print !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String exitAction() {
        return "case1";
    }

}
  

