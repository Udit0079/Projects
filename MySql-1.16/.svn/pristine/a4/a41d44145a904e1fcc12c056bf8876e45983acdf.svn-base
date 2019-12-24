/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;


import com.cbs.web.pojo.intcal.IntCalTable;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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

public class CaInterestCalc extends BaseBean {

    private String message;
    private String allAccount;
    private String accountWise;
    private String accountType;
    private String accountNo;
    private String accountNoType;
    private String debitGl;
    private String creditGl;
    private String totalCredit;
    private String totalDebit;
    private String viewID = "/pages/master/sm/test.jsp";
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
    private boolean reportFlag;
    private String tmpFromDt;
    private String tmpToDt;
    private final String jndiHomeName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote beanRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    String oldAccNo;

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

    public String getDebitGl() {
        return debitGl;
    }

    public void setDebitGl(String debitGl) {
        this.debitGl = debitGl;
    }

    public String getCreditGl() {
        return creditGl;
    }

    public void setCreditGl(String creditGl) {
        this.creditGl = creditGl;
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
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public CaInterestCalc() {
        try {
            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            if (getHttpRequest().getParameter("code") == null || getHttpRequest().getParameter("code").toString().equalsIgnoreCase("")) {
                this.setAcCloseFlag("");
            } else {
                this.setAcCloseFlag(getHttpRequest().getParameter("code").toString());
                this.setAccountNoType(getHttpRequest().getParameter("code").toString().substring(2, 4));
                this.setAccountNo(getHttpRequest().getParameter("code").toString());
                this.setOldAccNo(getHttpRequest().getParameter("code").toString());
                this.setAccountWise("I");
            }
            intCalc = new ArrayList<IntCalTable>();
            setMessage("");
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
            allAccountList.add(new SelectItem("A", "All Account"));
            accountWiseList = new ArrayList<SelectItem>();
            accountWiseList.add(new SelectItem("I", "Account Wise"));
            List resultList = new ArrayList();
            //List accountList = new ArrayList();
            resultList = beanRemote.getCaAcctType();
            if (!resultList.isEmpty()) {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString()));
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
        intCalc = new ArrayList<IntCalTable>();
        String accType = null;
        String acno = null;
        String option = null;
        //String mode = "cal";
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
                    List dataList = ftsPostRemote.getUnAuthorizedTranListForAcno(acno);
                    if (!dataList.isEmpty()) {
                        this.setMessage("There are some pending authorization !");
                        return;
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
                        setMessage(" To date can not be greater than current Date");
                        this.setToDate(date);
                        return;
                    }
                    option = "I";

                }
            }
            if (allAccount == null || allAccount.equalsIgnoreCase("")) {
            } else if (allAccount.equalsIgnoreCase("A")) {
                List dataList = ftsPostRemote.getUnAuthorizedTranList(accountType, getOrgnBrCode());
                if (!dataList.isEmpty()) {
                    this.setMessage("There are some pending authorization !");
                    return;
                }
                if (ymmd.parse(ymmd.format(date)).before(ymmd.parse(ymmd.format(fromDate))) || ymmd.parse(ymmd.format(date)).equals(ymmd.parse(ymmd.format(fromDate)))) {
                    setMessage("Please check the Interest calculation date.");
                    return;
//                    if(CbsUtil.monthDiff(date,fromDate)>0){
//                        setMessage("Please check the Interest calculation date.");
//                        return;
//                    }else if(CbsUtil.monthDiff(date,toDate)== 0){
//                        if(CbsUtil.dayDiff(date, toDate)>7){
//                            setMessage("Please check the Interest calculation date.");
//                            return;
//                        }
//                    }
                }
                String glHead = beanRemote.getCaAccountCreditGlHeads(accountType);
                setDebitGl(glHead);
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
            } else if (accountWise.equalsIgnoreCase("I")) {
                String glHead = beanRemote.getCaAccountCreditGlHeads(accType);
                setDebitGl(glHead);
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
            if (debitGl== null || debitGl.equalsIgnoreCase("")) {
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
            double amount = 0d;
            this.setTmpFromDt(sdf.format(fromDate));
            this.setTmpToDt(sdf.format(toDate));

            List<LoanIntCalcList> resultList = beanRemote.cbsCaAccountIntCalc(option, accType, acno, sdf.format(fromDate), sdf.format(toDate), creditGl, getUserName(), getOrgnBrCode());
            List<InterestCalculationPojo> reportList = new ArrayList<InterestCalculationPojo>();
            NumberFormat formatter = new DecimalFormat("#0.00");
            if (resultList.isEmpty()) {
                setMessage("Data does not exist");
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
                    if (closingBal > 0.0) {
                        closingBal =  closingBal;
                    }
                    if (acctProduct > 0.0) {
                        acctProduct =  acctProduct;
                    }
                    if (acctInt > 0.0) {
                        acctInt =  acctInt;
                    }
                    mt.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                    pojo.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    pojo.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    mt.setRoi(new BigDecimal(formatter.format(lt.getRoi())));

                    pojo.setRoi(new BigDecimal(formatter.format(lt.getRoi())));
                    mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                    pojo.setInterestAmt(new BigDecimal(formatter.format(acctInt)));
                    amount = amount + acctInt;

                    mt.setIntTableCode(lt.getIntTableCode());
                    pojo.setIntTableCode(lt.getIntTableCode());
                    intCalc.add(mt);
                    reportList.add(pojo);
                }
            }
            printReport(reportList);
            setCreditGl("As per list");
            setTotalCredit(formatter.format(amount));
            setTotalDebit(formatter.format(amount));
            if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                String glHead = beanRemote.getCaAccountCreditGlHeads(accType);
                setDebitGl(glHead);
            } else if (allAccount.equalsIgnoreCase("I")) {
                String glHead = beanRemote.getGlHeads(accType);
                setDebitGl(glHead);
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                String glHead = beanRemote.getCaAccountCreditGlHeads(accType);
                setDebitGl(glHead);
            } else if (accountWise.equalsIgnoreCase("I")) {
                String glHead = beanRemote.getGlHeads(accType);
                setDebitGl(glHead);
            }
            setDisablePost(false);
            setToDisable(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
            setDisablePost(true);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            setDisablePost(true);
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
                String glHead = beanRemote.getCaAccountCreditGlHeads(accountType);
                setDebitGl(glHead);
            }
            if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                accountWise = "";
            } else if (accountWise.equalsIgnoreCase("I")) {
                String glHead = beanRemote.getCaAccountCreditGlHeads(accType);
                setDebitGl(glHead);
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
            if (debitGl == null || debitGl.equalsIgnoreCase("")) {
                setMessage("Credit Account is not Set.");
                return;
            }
            if (accountWise.equalsIgnoreCase("I")) {
                accType = acno.substring(2, 4);
            }
            String result = beanRemote.caAccountInterestPosting(option, accType, acno, sdf.format(fromDate), sdf.format(toDate), creditGl, getUserName(), getOrgnBrCode());
            setMessage(result);
            setAllAccount("");
            setAccountWise("");
            setAccountNo("");

            setAccountNoType("");
            setAccountType("0");
            setDebitGl("");

            setCreditGl("");
            setTotalCredit("");
            setTotalDebit("");
            intCalc.clear();
            setIntCalc(intCalc);
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            setDisablePost(true);
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
            setDisablePost(true);
            setAccountWise("");
            setDisableAcctNo(true);
            //setAccountNoType("0");
            setAccountNo("");
            this.setOldAccNo("");
            setDebitGl("");
            setCreditGl("");
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
            setDebitGl("");
            setCreditGl("");
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
            setDisableAcctNo(false);
            this.setReportFlag(false);
            this.setMessage("");
            setAllAccount("");
            setAccountWise("");
            setAccountNo("");
            this.setOldAccNo("");
            setAccountNoType("");
            setAccountType("0");
            setDebitGl("");
            setCreditGl("");
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
            String glHead = beanRemote.getGlHeads(acctNo);
            setCreditGl(glHead);
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
                ext.redirect(ext.getRequestContextPath() + "/faces/pages/admin/AccountClosingRegister.jsp?code=" + 1);
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
            setDebitGl("");
            setCreditGl("");
            setTotalCredit("");
            setTotalDebit("");

            intCalc = new ArrayList<IntCalTable>();
            String fromDt = "";
            String toDt = "";
            //Float amount = (float) 0;
            if (accountType == null) {
                accountType = "";
            }
            if (accountType.equals("0")) {
                accountType = "";
            }
            if (!accountType.equals("0") && !accountType.equals("")) {
                fromDt = beanRemote.allDepositAccountFromDt(accountType, getOrgnBrCode(), "f");
                if (fromDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setFromDate(sdf.parse(sdf.format(ymd.parse(fromDt))));
                    setFromDisable(true);
                }
                toDt = beanRemote.allDepositAccountFromDt(accountType, getOrgnBrCode(), "t");
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
        setDebitGl("");
        setCreditGl("");
        setTotalCredit("");
        setTotalDebit("");
        intCalc = new ArrayList<IntCalTable>();
        try {
            if (oldAccNo == null || oldAccNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account no.");
                return;
            }
            if (oldAccNo.length() < 12) {
                setMessage("Please enter 12 digits account no.");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(oldAccNo);

            String curBrCode = ftsPostRemote.getCurrentBrnCode(accountNo);
            if (!curBrCode.equalsIgnoreCase(getOrgnBrCode())) {
                setMessage("Account should be of self branch.");
                return;
            }

            String resultList = beanRemote.caAccountWiseFromDt(accountNo, getOrgnBrCode());
            if ((resultList != null) || (!resultList.equalsIgnoreCase(""))) {
                this.setToDate(date);
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(resultList))));
                setFromDisable(true);
                setToDisable(false);
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
                fillParams.put("pReportName", "Interest Calculation");
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pFromDt", tmpFromDt);
                fillParams.put("pToDt", tmpToDt);
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

