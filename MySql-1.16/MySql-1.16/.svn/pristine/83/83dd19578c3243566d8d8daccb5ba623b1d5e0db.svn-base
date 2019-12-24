/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.FdRdFacadeRemote;
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
 * @author root
 */
public class AccountStatementRecieptWise extends BaseBean {

    private String message;
    private String accountNo, acNoLen, newAcNo;
    private String fromDate;
    private String toDate;
    private String voucherNo;
    private List<SelectItem> voucherNoList;
    private String status;
    private List<SelectItem> statusList;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    private BankProcessManagementFacadeRemote bnkpRemote;
    private FdRdFacadeRemote fdrdRemote;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public AccountStatementRecieptWise() {

        try {
            this.fromDate = dmyFormat.format(new Date());
            this.toDate = dmyFormat.format(new Date());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            bnkpRemote = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup("BankProcessManagementFacade");
            fdrdRemote = (FdRdFacadeRemote) ServiceLocator.getInstance().lookup("FdRdFacade");

            statusList = new ArrayList<>();
           // statusList.add(new SelectItem("ALL", "ALL"));
            statusList.add(new SelectItem("A", "Active"));
            statusList.add(new SelectItem("C", "Closed"));


        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onBlurAccountNumber1() {
        try {
            setMessage("");
            if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please Fill Proper Account No");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(accountNo);
            this.newAcNo = accountNo;
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onBlurAccountNumber() {
        try {
            setMessage("");
            if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please Fill Proper Account No");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(accountNo);
            this.newAcNo = accountNo;

            voucherNoList = new ArrayList<>();
            voucherNoList.add(new SelectItem("ALL", "ALL"));
            List vouList = fdrdRemote.getVoucherNoByAcNo(newAcNo, status,ymdFormat.format(dmyFormat.parse(toDate)));
            if (!vouList.isEmpty()) {
                for (int i = 0; i < vouList.size(); i++) {
                    Vector vtr = (Vector) vouList.get(i);
                    voucherNoList.add(new SelectItem(Long.parseLong(vtr.get(0).toString())));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void printAction() {

        setMessage("");
        try {
            List<AccountStatementReportPojo> resultList = fdrdRemote.getAccountStatementRecieptWiseList(newAcNo, ymdFormat.format(dmyFormat.parse(fromDate)), ymdFormat.format(dmyFormat.parse(toDate)), status, voucherNo);
            if (!resultList.isEmpty()) {
                AccountStatementReportPojo getEle = resultList.get(resultList.size() - 1);
                double closingBalance = getEle.getPendingBal();
                double availableBalance = getEle.getPendingBal();
                resultList.remove(resultList.size() - 1);
                String bankName = "";
                String branchAddress = "";

                List ele = common.getBranchNameandAddress(common.getBrncodeByAcno(newAcNo));
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "FD Account Statement Reciept Wise";
                Map fillParams = new HashMap();

                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("pStmtPeriod", fromDate + " to " + toDate);
                fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pClosingBalance", closingBalance);
                fillParams.put("pAvailableBalance", availableBalance);
                fillParams.put("pIfscCode", bnkpRemote.getIfscCodeByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));

                new ReportBean().jasperReport("ACCOUNT_STATEMENT_RECEIPT1", "text/html",
                        new JRBeanCollectionDataSource(resultList), fillParams, "FD Account Statement Reciept Wise");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void downloadPdf() {
        try {
            List<AccountStatementReportPojo> resultList = fdrdRemote.getAccountStatementRecieptWiseList(newAcNo, ymdFormat.format(dmyFormat.parse(fromDate)), ymdFormat.format(dmyFormat.parse(toDate)), status, voucherNo);
            if (!resultList.isEmpty()) {
                AccountStatementReportPojo getEle = resultList.get(resultList.size() - 1);
                double closingBalance = getEle.getPendingBal();
                double availableBalance = getEle.getPendingBal();
                resultList.remove(resultList.size() - 1);

                String bankName = "";
                String branchAddress = "";

                List ele = common.getBranchNameandAddress(common.getBrncodeByAcno(newAcNo));
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "FD Account Statement Reciept Wise";
                Map fillParams = new HashMap();

                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("pStmtPeriod", fromDate + " to " + toDate);
                fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pClosingBalance", closingBalance);
                fillParams.put("pAvailableBalance", availableBalance);
                fillParams.put("pIfscCode", bnkpRemote.getIfscCodeByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));

                new ReportBean().openPdf("FD Account Statement_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "ACCOUNT_STATEMENT_RECEIPT1", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", "FD Account Statement Reciept Wise");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        setMessage("");
        setAccountNo("");
        setAcNoLen("");
        setNewAcNo("");
        setStatus("ALL");
        setVoucherNo("ALL");
        this.fromDate = dmyFormat.format(new Date());
        this.toDate = dmyFormat.format(new Date());


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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
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

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public List<SelectItem> getVoucherNoList() {
        return voucherNoList;
    }

    public void setVoucherNoList(List<SelectItem> voucherNoList) {
        this.voucherNoList = voucherNoList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }
}
