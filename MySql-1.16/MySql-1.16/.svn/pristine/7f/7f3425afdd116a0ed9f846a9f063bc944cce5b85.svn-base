/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.intcal.LoanPenalCalculationFacadeRemote;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class LoanPenalInterestCalculation extends BaseBean {

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
    private boolean disablePost;
    private Date fromDate;
    private Date toDate;
    boolean fromDisable;
    boolean toDisable;
    private List<SelectItem> allAccountList;
    private List<SelectItem> accountTypeList;
    private List<SelectItem> accountWiseList;
    LoanGenralFacadeRemote loanGenralFacade;
    private List<IntCalTable> intCalc;
    private String acCloseFlag = null;
    private final String loanPenalCalcJndiName = "LoanPenalCalculationFacade";
    private LoanPenalCalculationFacadeRemote loanPenalCalculationRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String loanIntCalHomeName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote loanIntCalculationRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    String oldAccNo, acNoLen;
    private String viewID = "/pages/master/sm/test.jsp";
    private boolean reportFlag;

    public boolean isFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public boolean isToDisable() {
        return toDisable;
    }

    public void setToDisable(boolean toDisable) {
        this.toDisable = toDisable;
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

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

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
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public LoanPenalInterestCalculation() {
        try {
            loanPenalCalculationRemote = (LoanPenalCalculationFacadeRemote) ServiceLocator.getInstance().lookup(loanPenalCalcJndiName);
            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            loanIntCalculationRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(loanIntCalHomeName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            this.setAcNoLen(getAcNoLength());
            intCalc = new ArrayList<IntCalTable>();
            this.setMessage("");
            setDisableAcctNo(true);
            setDisableAcctType(true);
            setDisablePost(true);
            getValues();
            this.setReportFlag(false);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getValues() {
        try {
            this.setReportFlag(false);
            allAccountList = new ArrayList<SelectItem>();
            allAccountList.add(new SelectItem("A", "ALL Account"));
            accountWiseList = new ArrayList<SelectItem>();
            accountWiseList.add(new SelectItem("I", "Account Wise"));

            List resultList = new ArrayList();
            //List accountList = new ArrayList();
            resultList = loanPenalCalculationRemote.getAcctType();
            if (resultList.isEmpty()) {
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
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void calculate() {
        this.setMessage("");
        this.setReportFlag(false);
        String accType = "", acno = "", option = "";
        try {
            if (allAccount == null || allAccount.equalsIgnoreCase("")) {
            } else if (allAccount.equalsIgnoreCase("A")) {
                accType = accountType;
                acno = "";
                option = "A";
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
            } else if (accountWise.equalsIgnoreCase("I")) {
                if (oldAccNo.equalsIgnoreCase("") || oldAccNo == null) {
                    setMessage("Please Enter the Account No.");
                    return;
                } else if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please Enter the Account No.");
                    return;
                } else {
                    acno = accountNo;
                    accType = ftsPostRemote.getAccountCode(acno);
                    List resultLt = loanGenralFacade.tableData(acno);
                    if (!resultLt.isEmpty()) {
                        Vector vtr = (Vector) resultLt.get(0);
                        String penalApply = vtr.get(21).toString();
                        if (penalApply.equalsIgnoreCase("N")) {
                            setMessage("This Account No is not  penal apply,Please fill another account !");
                            return;
                        }
                    }
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
                    if (toDate == null) {
                        setMessage("Please Select To Date.");
                        return;
                    } else if (toDate.after(date)) {
                        setMessage(" To date can not be greater current Date");
                        this.setToDate(date);
                        return;
                    }
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
            if (accType.equalsIgnoreCase("0")) {
                setMessage("Please select/fill the account type.");
                return;
            }
            if (accType == null || accType.equals("")) {
                setMessage("Please select/fill the account type.");
                return;
            }

            intCalc = new ArrayList<IntCalTable>();
            double amount = 0d;
            List<List<LoanIntCalcList>> resultList = loanPenalCalculationRemote.cbsLoanPenalCalculation(option, accType, acno, sdf.format(fromDate), sdf.format(toDate), creditAmt, getUserName(), getOrgnBrCode());
            List<InterestCalculationPojo> reportList = new ArrayList<InterestCalculationPojo>();
            NumberFormat formatter = new DecimalFormat("#0.00");
            if (resultList.isEmpty()) {
                this.setMessage("Data does not exist");
                this.setDisablePost(true);
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    List<LoanIntCalcList> loanIntCalTable = resultList.get(i);
                    for (int j = 0; j < loanIntCalTable.size(); j++) {
                        LoanIntCalcList it = loanIntCalTable.get(j);
                        IntCalTable mt = new IntCalTable();
                        InterestCalculationPojo pojo = new InterestCalculationPojo();
                        mt.setsNo(Integer.toString(i + 1));
                        mt.setAcNo(it.getAcNo());
                        mt.setCustName(it.getCustName());

                        pojo.setAcNo(it.getAcNo());
                        pojo.setCustName(it.getCustName());

                        double closingBal = it.getClosingBal();
                        double acctProduct = it.getProduct();
                        double acctInt = it.getTotalInt();
                        if (closingBal < 0.0) {
                            closingBal = -1 * closingBal;
                        }
                        if (acctProduct < 0.0) {
                            acctProduct = -1 * acctProduct;
                        }
                        if (acctInt < 0.0) {
                            acctInt = -1 * acctInt;
                        }
                        mt.setFirstDt(it.getFirstDt());
                        mt.setLastDt(it.getLastDt());
                        pojo.setFromDate(it.getFirstDt());
                        pojo.setToDate(it.getLastDt());
                        mt.setClosingBal(new BigDecimal(formatter.format(it.getClosingBal())));
                        pojo.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                        mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                        pojo.setProduct(new BigDecimal(formatter.format(acctProduct)));
                        mt.setRoi(new BigDecimal(formatter.format(it.getRoi())));
                        pojo.setRoi(new BigDecimal(formatter.format(it.getRoi())));
                        mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                        pojo.setInterestAmt(new BigDecimal(formatter.format(acctInt)));
                        amount = amount + acctInt;
                        mt.setIntTableCode(it.getIntTableCode());
                        mt.setDetails(it.getDetails());
                        pojo.setIntTableCode(it.getIntTableCode());
                        intCalc.add(mt);
                        reportList.add(pojo);
                    }
                }
            }
            printReport(reportList);
            setDebitAmt("As per list");
            setTotalCredit(formatter.format(amount));
            setTotalDebit(formatter.format(amount));
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
            setDisablePost(false);
            setToDisable(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void Post() {
        this.setReportFlag(false);
        String fullAcct = "", accType = "", acno = "", option = "";
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
                } else if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
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
                    if (toDate == null) {
                        setMessage("Please Select To Date.");
                        return;
                    } else if (toDate.after(date)) {
                        setMessage(" To date can not be greater current Date");
                        this.setToDate(date);
                        return;
                    }
                    option = "I";
                }
            }
            if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                allAccount = "";
            } else if (allAccount.equalsIgnoreCase("A")) {
                ddAccountNo(accountType);
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                accountWise = "";
            } else if (accountWise.equalsIgnoreCase("I")) {
                ddAccountNo(accType);
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
            if (allAccount.equalsIgnoreCase("1")) {
                fullAcct = "";
            } else if (accountWise.equalsIgnoreCase("1")) {
                fullAcct = accountNo;
                accType = acno.substring(2, 4);
            }
            intCalc = new ArrayList<IntCalTable>();
            String result = loanPenalCalculationRemote.cbsLoanPenalPosting(option, accType, acno, sdf.format(fromDate), sdf.format(toDate), creditAmt, getUserName(), getOrgnBrCode());
            this.setMessage(result);
            this.setDisablePost(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void disableAcctType() {
        try {
            this.setReportFlag(false);
            setDisableAcctType(false);
            setAccountWise("");
            setDisablePost(true);
            setDisableAcctNo(true);
            setAccountNoType("0");
            setAccountNo("");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
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
            setDisablePost(true);
            setDisableAcctType(true);
            setAccountType("0");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            this.setMessage("");
            intCalc = new ArrayList<IntCalTable>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        try {
            this.setReportFlag(false);
            this.setMessage("");
            setAllAccount("");
            setDisablePost(true);
            setAccountWise("");
            setAccountNo("");
            setAccountNoType("");
            setAccountType("0");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();
            setToDisable(false);
            setFromDisable(false);
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
            refresh();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void setDateAllAccount() {
        try {
            this.setMessage("");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();
            //String resultList;
            String fromDt = "";
            String toDt = "";
            //Float amount = (float) 0;
            if (accountType == null) {
                accountType = "";
            }
            if (accountType.equals("0")) {
                accountType = "";
            }
            System.out.println("accountType -->" + accountType);
            if (!accountType.equals("0") && !accountType.equals("")) {
                fromDt = loanPenalCalculationRemote.allFromDt(accountType, getOrgnBrCode(), "f");
                if (fromDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    // setToDate(sdf.parse(toDt));
                    this.setFromDate(sdf.parse(sdf.format(ymd.parse(fromDt))));
                    setFromDisable(true);
                }
                toDt = loanPenalCalculationRemote.allFromDt(accountType, getOrgnBrCode(), "t");
                if (toDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setToDate(sdf.parse(sdf.format(ymd.parse(toDt))));
                    setToDisable(true);
                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setDateAccountWise() {
        this.setMessage("");
        this.setMessage("");
        setDebitAmt("");
        setCreditAmt("");
        setTotalCredit("");
        setTotalDebit("");
        intCalc = new ArrayList<IntCalTable>();
        try {
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
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
                setMessage("Account should be of self branch.");
                return;
            }
            intCalc = new ArrayList<IntCalTable>();
            String resultList;
            //Float amount = (float) 0;
            resultList = loanPenalCalculationRemote.acWiseFromDt(accountNo, getOrgnBrCode());
            if (resultList.equalsIgnoreCase("Account Nature doesn't exists.")) {
                this.setMessage("Account Nature doesn't exists.");
                return;
                // this.setMessage("Please check the existance of this Account No. in CBS_LOAN_INTEREST_POST_AC_WISE");
            } else if (resultList.equalsIgnoreCase("Account No. Does Not Exist.")) {
                this.setMessage("Account No. Does Not Exist.");
                return;
            } else {
                this.setToDate(date);
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(resultList))));
                setFromDisable(true);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void printReport(List<InterestCalculationPojo> resultList) {
        try {
            List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankDetailsList.size() > 0) {
                String bankName = bankDetailsList.get(0).toString();
                String bankAddress = bankDetailsList.get(1).toString();
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Loan Penal Interest Calculation");
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pFromDt", sdf.format(fromDate));
                fillParams.put("pToDt", sdf.format(toDate));
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);

                new ReportBean().jasperReport("LOAN_INT_CAL_REPORT", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Interest Calculation");
                this.setReportFlag(true);
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
