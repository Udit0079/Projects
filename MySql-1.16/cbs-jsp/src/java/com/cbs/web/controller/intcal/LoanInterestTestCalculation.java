/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanIntProductTestFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.intcal.IntCalTable;
import com.cbs.web.pojo.loan.InterestCalculationPojo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class LoanInterestTestCalculation extends BaseBean {

    private String message;
    private String allAccount;
    private String accountWise;
    private String accountType;
    private String accountNo;
    private String accountNoType;
    private String debitAmt;
    private String creditAmt;
    private String totalCredit;
    private String totalDebit;
    private boolean disableAcctType;
    private boolean disableAcctNo;
    private Date fromDate;
    private Date toDate;
    private List<SelectItem> allAccountList;
    private List<SelectItem> accountTypeList;
    private List<SelectItem> accountWiseList;
    LoanGenralFacadeRemote loanGenralFacade;
    private List<IntCalTable> intCalc;
    private String acCloseFlag = null;
    private boolean reportFlag;
    private String tmpFromDt;
    private String tmpToDt;
    private final String loanIntTestCalHomeName = "LoanIntProductTestFacade";
    private LoanIntProductTestFacadeRemote loanIntTestRemote = null;
    private final String loanIntCalHomeName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote loanIntCalculationRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    String oldAccNo, acNoLen;
    private boolean disablePost;
    private String drCrFlag;
    
    private String viewID = "/pages/master/sm/test.jsp";

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getTmpFromDt() {
        return tmpFromDt;
    }

    public void setTmpFromDt(String tmpFromDt) {
        this.tmpFromDt = tmpFromDt;
    }

    public String getTmpToDt() {
        return tmpToDt;
    }

    public void setTmpToDt(String tmpToDt) {
        this.tmpToDt = tmpToDt;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public List<IntCalTable> getIntCalc() {
        return intCalc;
    }

    public void setIntCalc(List<IntCalTable> intCalc) {
        this.intCalc = intCalc;
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

    public String getAccountNoType() {
        return accountNoType;
    }

    public void setAccountNoType(String accountNoType) {
        this.accountNoType = accountNoType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(String creditAmt) {
        this.creditAmt = creditAmt;
    }

    public String getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(String debitAmt) {
        this.debitAmt = debitAmt;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
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

    public String getAccountWise() {
        return accountWise;
    }

    public void setAccountWise(String accountWise) {
        this.accountWise = accountWise;
    }

    public String getAllAccount() {
        return allAccount;
    }

    public void setAllAccount(String allAccount) {
        this.allAccount = allAccount;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<SelectItem> getAllAccountList() {
        return allAccountList;
    }

    public void setAllAccountList(List<SelectItem> allAccountList) {
        this.allAccountList = allAccountList;
    }

    public List<SelectItem> getAccountWiseList() {
        return accountWiseList;
    }

    public void setAccountWiseList(List<SelectItem> accountWiseList) {
        this.accountWiseList = accountWiseList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcCloseFlag() {
        return acCloseFlag;
    }

    public void setAcCloseFlag(String acCloseFlag) {
        this.acCloseFlag = acCloseFlag;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getDrCrFlag() {
        return drCrFlag;
    }

    public void setDrCrFlag(String drCrFlag) {
        this.drCrFlag = drCrFlag;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public LoanInterestTestCalculation() {
        try {
            loanIntTestRemote = (LoanIntProductTestFacadeRemote) ServiceLocator.getInstance().lookup(loanIntTestCalHomeName);
            loanIntCalculationRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(loanIntCalHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            this.setAcNoLen(getAcNoLength());
            if (getHttpRequest().getParameter("code") == null || getHttpRequest().getParameter("code").toString().equalsIgnoreCase("")) {
                this.setAcCloseFlag("");
            } else {
                this.setAcCloseFlag(getHttpRequest().getParameter("code").toString());
                this.setAccountNoType(getHttpRequest().getParameter("code").toString().substring(2, 4));
                this.setAccountNo(getHttpRequest().getParameter("code").toString().substring(4, 10));
            }
            intCalc = new ArrayList<IntCalTable>();
            this.setMessage("");
            this.setDisableAcctNo(true);
            this.setDisableAcctType(true);
            setDisablePost(true);
            getValues();
            this.setReportFlag(false);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getValues() {
        try {
            this.setReportFlag(false);
            allAccountList = new ArrayList<SelectItem>();
            allAccountList.add(new SelectItem("A", "All Account"));
            accountWiseList = new ArrayList<SelectItem>();
            accountWiseList.add(new SelectItem("I", "Account Wise"));
            List resultList = loanIntCalculationRemote.getAcctType();
            if (resultList.size() <= 0) {
                this.setMessage("Loan account code does not exist");
                return;
            } else {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void calculate() {
        this.setMessage("");
        this.setReportFlag(false);
        intCalc = new ArrayList<IntCalTable>();
        String accType = "";
        String acno = "";
        String option = "";
        setMessage("");
        if (allAccount == null || allAccount.equalsIgnoreCase("")) {
        } else if (allAccount.equalsIgnoreCase("A")) {
            accType = accountType;
            acno = "";
            option = "A";
            if (accountType.equalsIgnoreCase("0")) {
                setMessage("Please Select the Account Type.");
                return;
            }
        }
        try {
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
            } else if (accountWise.equalsIgnoreCase("I")) {
                if (oldAccNo.equalsIgnoreCase("") || oldAccNo == null) {
                    setMessage("Please Enter the Account No.");
                    return;
                } else {
                    acno = accountNo;
                    accType = ftsPostRemote.getAccountCode(acno);
                    loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
                    List result = loanGenralFacade.accountDetail(acno);
                    if (result.isEmpty()) {
                        this.setMessage("Account number does not exist");
                        return;
                    } else {
                        for (int i = 0; i < result.size(); i++) {
                            Vector ele = (Vector) result.get(i);
                            if (ele.get(3).toString().equalsIgnoreCase("9")) {
                                this.setMessage("Account has been closed.");
                                return;
                            }
                        }
                    }
                    if (toDate == null) {
                        setMessage("Please select To Date.");
                        return;
                    } 
//                    else if (toDate.after(date)) {
//                        setMessage(" To date can not be greater current date");
//                        this.setToDate(date);
//                        return;
//                    }
                    option = "I";
                }
            }
            if (allAccount == null || allAccount.equalsIgnoreCase("")) {
            } else if (allAccount.equalsIgnoreCase("A")) {
                ddAccountNo(accountType);
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
            } else if (accountWise.equalsIgnoreCase("I")) {
                ddAccountNo(accType);
                if (toDate == null) {
                    setMessage("Please select To Date.");
                    return;
                }
                if (fromDate == null) {
                    setMessage("Please select From Date.");
                    return;
                }
                if (fromDate.after(toDate)) {
                    setMessage("To date can not be greater than From Date");
                    return;
                }
            }
            if (fromDate == null) {
                setMessage("Please select From Date.");
                return;
            }
            if (toDate == null) {
                setMessage("Please select To Date.");
                return;
            }
            if (option == null || option.equals("0") || option.equalsIgnoreCase("")) {
                setMessage("Please select the Radio Button.");
                return;
            }
            if (creditAmt == null || creditAmt.equalsIgnoreCase("")) {
                setMessage("Credit account is not set.");
                return;
            }

            if (accType.equalsIgnoreCase("0")) {
                setMessage("Please select/fill the account type.");
                return;
            }
            if (accType == null || accType.equals("")) {
                setMessage("Please select/fill the account type.");
                return;
            }
            double amount = 0d;
            this.setTmpFromDt(sdf.format(fromDate));
            this.setTmpToDt(sdf.format(toDate));
            List<LoanIntCalcList> resultList = loanIntTestRemote.cbsIntCalLoan(option, accType, acno, sdf.format(fromDate), sdf.format(toDate), creditAmt, getUserName(), getOrgnBrCode());
            List<InterestCalculationPojo> reportList = new ArrayList<InterestCalculationPojo>();
            List<LoanIntCalcList> tempTable = new ArrayList<LoanIntCalcList>();
            NumberFormat formatter = new DecimalFormat("#0.00");
            if (resultList.isEmpty()) {
                this.setMessage("Data does not exist");
                setDisablePost(true);
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    LoanIntCalcList lt = resultList.get(i);
                    IntCalTable mt = new IntCalTable();
                    InterestCalculationPojo pojo = new InterestCalculationPojo();
                    mt.setsNo(Integer.toString(i + 1));
                    pojo.setAcNo(lt.getAcNo());
                    pojo.setCustName(lt.getCustName());

                    mt.setAcNo(lt.getAcNo());
                    mt.setCustName(lt.getCustName());
                    mt.setFirstDt(sdf.format(ymd.parse(lt.getFirstDt())));
                    mt.setLastDt(sdf.format(ymd.parse(lt.getLastDt())));

                    pojo.setFromDate(sdf.format(ymd.parse(lt.getFirstDt())));
                    pojo.setToDate(sdf.format(ymd.parse(lt.getLastDt())));

                    double closingBal = lt.getClosingBal();
                    double acctProduct = lt.getProduct();
                    double acctInt = lt.getTotalInt();
                    if (closingBal < 0.0) {
                        closingBal = -1 * closingBal;
                    }
                    if (acctProduct < 0.0) {
                        acctProduct = -1 * acctProduct;
                    }
                    
                    acctInt = -1 * acctInt;
                    if (acctInt < 0.0) {
                        pojo.setDrCrflag("Cr");
                        mt.setDrCrFlag("Cr");
                    }else{
                        pojo.setDrCrflag("Dr");
                        mt.setDrCrFlag("Dr");
                    }
                    mt.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                    pojo.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    pojo.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    mt.setRoi(new BigDecimal(formatter.format(lt.getRoi())));

                    pojo.setRoi(new BigDecimal(formatter.format(lt.getRoi())));
                    
                    amount = amount + acctInt;
                    mt.setTotalInt(new BigDecimal(formatter.format(Math.abs(acctInt))));
                    pojo.setInterestAmt(new BigDecimal(formatter.format(Math.abs(acctInt))));

                    mt.setIntTableCode(lt.getIntTableCode());
                    pojo.setIntTableCode(lt.getIntTableCode());
                    intCalc.add(mt);
                    reportList.add(pojo);
                }
            }           

            setDebitAmt("As per list");
            setTotalCredit(formatter.format(amount));
            setTotalDebit(formatter.format(Math.abs(amount)));
            if(amount<0){
                setDrCrFlag("Cr");
            }else{
                setDrCrFlag("Dr");
            }
            
            if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                ddAccountNo(accountType);
            } else if (allAccount.equalsIgnoreCase("I")) {
                ddAccountNo(accountType);
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                ddAccountNo(accType);
            } else if (accountWise.equalsIgnoreCase("I")) {
                ddAccountNo(accType);
            }
            this.setReportFlag(true);
            this.setViewID("/report/ReportPagePopUp.jsp");
            printReport(intCalc);
            setDisablePost(false);
//            setToDisable(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
            setDisablePost(false);            
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            setDisablePost(false);
        }
    }

    public void disableAcctType() {
        try {
            this.setReportFlag(false);
            setDisableAcctType(false);
            setAccountWise("");
            setDisableAcctNo(true);
            setAccountNoType("0");
            setAccountNo("");
            this.setOldAccNo("");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            this.setDrCrFlag("");
            this.setMessage("");
            intCalc = new ArrayList<IntCalTable>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void disableAcctTypeNo() {
        try {
            this.setReportFlag(false);
            setDisableAcctNo(false);
            setAllAccount("");
            setDisableAcctType(true);
            setAccountType("0");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            this.setDrCrFlag("");
            this.setMessage("");
            intCalc = new ArrayList<IntCalTable>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        try {
            setDisableAcctNo(false);
            this.setReportFlag(false);
            this.setMessage("");
            setAllAccount("");
            setAccountWise("");
            setAccountNo("");
            this.setOldAccNo("");
            setAccountNoType("");
            setAccountType("0");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            setDrCrFlag("");
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void ddAccountNo(String acctNo) {
        try {
            String glHead = loanIntCalculationRemote.getGlHeads(acctNo);
            setCreditAmt(glHead);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {

            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            this.setMessage("");
            ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
            if (this.acCloseFlag == null || this.acCloseFlag.equalsIgnoreCase("")) {
                refresh();
                return "case1";
            } else {
                this.acCloseFlag = null;
                refresh();
                ext.redirect(ext.getRequestContextPath() + "/faces/accountmanager/AccountClosingRegister.jsp?code=" + 1);
                return "case2";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void setDateAllAccount() {
        try {
            this.setMessage("");
            this.setDebitAmt("");
            this.setCreditAmt("");
            this.setTotalCredit("");
            this.setTotalDebit("");
            this.setDrCrFlag("");
            String fromDt = "";
            String toDt = "";
            if (accountType == null) {
                accountType = "";
            }
            if (accountType.equals("0")) {
                accountType = "";
            }
            if (!accountType.equals("0") && !accountType.equals("")) {
                fromDt = loanIntCalculationRemote.allFromDt(accountType, getOrgnBrCode(), "f");
                if (fromDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setFromDate(sdf.parse(sdf.format(ymd.parse(fromDt))));
                }
                toDt = loanIntCalculationRemote.allFromDt(accountType, getOrgnBrCode(), "t");
                if (toDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setToDate(sdf.parse(sdf.format(ymd.parse(toDt))));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setDateAccountWise() {
        this.setMessage("");
        setDebitAmt("");
        setCreditAmt("");
        setTotalCredit("");
        setTotalDebit("");
        this.setDrCrFlag("");
        intCalc = new ArrayList<IntCalTable>();
        try {
            if (oldAccNo.equalsIgnoreCase("") || oldAccNo == null) {
                setMessage("Please enter the account no.");
                return;
            }
            //if (oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please enter proper account no.");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
            String curBrCode = ftsPostRemote.getCurrentBrnCode(accountNo);
            if (!curBrCode.equalsIgnoreCase(getOrgnBrCode())) {
                setMessage("Account should be self branch.");
                return;
            }
            String resultList = loanIntCalculationRemote.acWiseFromDt(accountNo, getOrgnBrCode());
            if (resultList.equalsIgnoreCase("Account Nature doesn't exists.")) {
                this.setMessage("Account Nature doesn't exists.");
                return;
            } else if (resultList.equalsIgnoreCase("Account No. Does Not Exist.")) {
                this.setMessage("Account No. Does Not Exist.");
                return;
            } else {
                this.setToDate(date);
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(resultList))));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void printReport(List<IntCalTable> intCalc) {
        try {
            List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankDetailsList.size() > 0) {
                String bankName = bankDetailsList.get(0).toString();
                String bankAddress = bankDetailsList.get(1).toString();
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Interest Calculation");
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pFromDt", sdf.format(fromDate));
                fillParams.put("pToDt", sdf.format(toDate));
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAdd", bankAddress);
                new ReportBean().jasperReport("LoanIntCalcReport", "text/html", new JRBeanCollectionDataSource(intCalc), fillParams, "Interest Calculation Report");
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void Post() {
        String accType = null;
        String acno = null;
        String option = null;
        this.setMessage("");
        this.setReportFlag(false);
        //List<LoanIntCalcList> tempTable = new ArrayList<LoanIntCalcList>();
        try {
            if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                allAccount = "";
            } else if (allAccount.equalsIgnoreCase("A")) {
                accType = accountType;
                acno = "";
                option = "A";
                if (accountType.equalsIgnoreCase("0")) {
                    setMessage("Please Select the Account Type.");
                    return;
                }
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                accountWise = "";
            } else if (accountWise.equalsIgnoreCase("I")) {

                if (oldAccNo.equalsIgnoreCase("") || oldAccNo == null) {
                    setMessage("Please Enter the Account No.");
                    return;
                } else {
                    acno = accountNo;
                    accType = ftsPostRemote.getAccountCode(acno);
                    List result;
                    result = loanGenralFacade.accountDetail(acno);
                    if (result.isEmpty()) {
                        this.setMessage("Account No. Does Not Exist.");
                        return;
                    } else {
                        for (int i = 0; i < result.size(); i++) {
                            Vector ele = (Vector) result.get(i);
                            if (ele.get(3).toString().equalsIgnoreCase("9")) {
                                this.setMessage("Account has been closed.");
                                return;
                            }
                        }
                    }
                    option = "I";
                    if (toDate == null) {
                        setMessage("Please Select To Date.");
                        return;
                    } else if (toDate.after(date)) {
                        setMessage("To date can not be greater current Date");
                        this.setToDate(date);
                        return;
                    }
                }
            }
            if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                allAccount = "";
            } else if (allAccount.equalsIgnoreCase("A")) {
                String glHead = loanIntCalculationRemote.getGlHeads(accountType);
                setCreditAmt(glHead);
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                accountWise = "";
            } else if (accountWise.equalsIgnoreCase("I")) {
                String glHead = loanIntCalculationRemote.getGlHeads(accType);
                setCreditAmt(glHead);
                if (toDate == null) {
                    setMessage("Please Select To Date.");
                    return;
                }
                if (fromDate == null) {
                    setMessage("Please Select From Date.");
                    return;
                }
                if (fromDate.after(toDate)) {
                    setMessage("To date can not be greater than From Date");
                    return;
                }
            }
            if (fromDate == null) {
                setMessage("Please Select From Date.");
                return;
            }
            if (toDate == null) {
                setMessage("Please Select To Date.");
                return;
            }
            if (option == null || option.equals("0") || option.equalsIgnoreCase("")) {
                setMessage("Please Select the Radio Button.");
                return;
            }
            if (creditAmt == null || creditAmt.equalsIgnoreCase("")) {
                setMessage("Credit Account is not Set.");
                return;
            }
            if (accountWise.equalsIgnoreCase("I")) {
                accType = acno.substring(2, 4);
            }
            String result = loanIntTestRemote.loanInterestPosting(option, accType, acno, sdf.format(fromDate), sdf.format(toDate), creditAmt, getUserName(), getOrgnBrCode());
            setMessage(result);
            setAllAccount("");
            setAccountWise("");
            setAccountNo("");

            setAccountNoType("");
            setAccountType("0");
            setDebitAmt("");

            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            this.setDrCrFlag("");
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            setDisablePost(true);
            intCalc = new ArrayList<IntCalTable>();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
