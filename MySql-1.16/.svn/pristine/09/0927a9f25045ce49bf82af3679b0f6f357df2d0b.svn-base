package com.cbs.web.controller.report.misc;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.dto.report.LoanAccStmPojo;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
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
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class AccountStatement extends BaseBean {

    private String message;
    MiscReportFacadeRemote beanRemote;
    private String accountNo, acNoLen;
    private String fromDate;
    private String toDate;
    private String lastStmtFromDate;
    private String lastStmtToDate;
    private String lastStmtPrintedDate;
    private String lastStmtPrintedBy;
    private String newAcNo;
    private String display = "";
    private BankProcessManagementFacadeRemote bnkpRemote;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private UploadNeftRtgsMgmtFacadeRemote nrRemote = null;
    private CommonReportMethodsRemote commonRemote;
    private LoanReportFacadeRemote loanRemote = null;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private String bnkCode;

    public AccountStatement() {
        try {
            this.setFromDate(getTodayDate());
            this.setToDate(getTodayDate());
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            nrRemote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("UploadNeftRtgsMgmtFacade");
            bnkpRemote = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup("BankProcessManagementFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            this.setAcNoLen(getAcNoLength());
            List<MbSmsSenderBankDetail> bankList = nrRemote.getBankCode();

            if (!bankList.isEmpty()) {
                MbSmsSenderBankDetail bankEntity = bankList.get(0);
                bnkCode = bankEntity.getBankCode();
//                if (bankEntity.getBankCode().equalsIgnoreCase("ccbl")) {
//                    display = "";
//                } else {
//                    display = "none";
//                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void onBlurAccountNumber() {
        try {
            setMessage("");
            setLastStmtFromDate("");
            setLastStmtPrintedDate("");
            setLastStmtPrintedBy("");
            setLastStmtToDate("");
            //if (accountNo.length() != 12) {
            if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please Fill Proper Account No");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(accountNo);
            this.newAcNo = accountNo;
            String acctNature = ftsPostRemote.getAccountNature(accountNo);
            if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                setMessage("Please fill only deposit account !");
                return;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List accnoTypeList = commonRemote.getCaAdvanceAccount(acctNature, accountNo.substring(2, 4));
                if (!accnoTypeList.isEmpty()) {
                    setMessage("Please fill only deposit account !");
                    return;
                }
            }

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
        setMessage("");
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

            if (fromDate == null || fromDate.equalsIgnoreCase("")) {
                setMessage("Please fill From Date !!");
                return null;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                setMessage("Please fill To Date !!");
                return null;
            }
            fromDate = fromDate.substring(6) + fromDate.substring(3, 5) + fromDate.substring(0, 2);
            toDate = toDate.substring(6) + toDate.substring(3, 5) + toDate.substring(0, 2);
            if (ymdFormat.parse(fromDate).after(ymdFormat.parse(toDate))) {
                setMessage("From Date should be less than To Date !!");
                return null;
            }
            if (ymdFormat.parse(toDate).after(new Date())) {
                setMessage("To Date should be less than Current Date !!");
                return null;
            }
            String acNature = ftsPostRemote.getAccountNature(newAcNo);
            if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                List npaStatusList = new ArrayList();
                Map fillParams = new HashMap();

                String report = "RD ACCOUNT STATEMENT";
                String showPeriod = "Y";


                List<LoanAccStmPojo> resultList = loanRemote.loanAccountStatement(newAcNo, fromDate, toDate);
                if (!resultList.isEmpty()) {
                    //String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String s[] = commonRemote.getBranchNameandAddressString(commonRemote.getBrncodeByAcno(newAcNo)).split("!");
                    fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                    fillParams.put("pStmtPeriod", dmyFormat.format(ymdFormat.parse(fromDate)) + " to " + dmyFormat.format(ymdFormat.parse(toDate)));
                    fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pShowPeriod", showPeriod);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("image", "/opt/images/logo.png");
                    List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                    Vector vtr = (Vector) brinfoList.get(0);
                    fillParams.put("pBranchName", vtr.get(0).toString());
                    fillParams.put("pBranchEmail", vtr.get(1).toString());
                    String bankOrSoc = "";
                    Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("Society");
                    if (bnkOrSoc == 1) {
                        bankOrSoc = "Society";
                    } else {
                        bankOrSoc = "Bank";
                    }
                    fillParams.put("pBankOrSociety", bankOrSoc);
                }
                if (npaStatusList.isEmpty()) {
                    if (!resultList.isEmpty()) {
                        new ReportBean().jasperReport("LoanAccountStatement", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                        return "report";
                    } else {
                        setMessage("Data does not exist");
                    }
                }
            } else {
                List<AccountStatementReportPojo> accountStatementReport = beanRemote.getAccountStatementReport(this.newAcNo, fromDate, toDate);
                System.out.println(accountStatementReport.size());
                int rows = accountStatementReport.size();
                if (accountStatementReport == null) {
                    setMessage("Account number does not exists !!");
                    return null;
                }
                if (!accountStatementReport.isEmpty()) {
                    AccountStatementReportPojo getEle = accountStatementReport.get(accountStatementReport.size() - 1);
                    double closingBalance = getEle.getBalance();
                    double availableBalance = closingBalance - getEle.getPendingBal();
                    String bankName = "";
                    String branchAddress = "";
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(common.getBrncodeByAcno(newAcNo));
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String repName = "Account Statement";
                    Map fillParams = new HashMap();
                    fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pStmtPeriod", dmyFormat.format(ymdFormat.parse(fromDate)) + " to " + dmyFormat.format(ymdFormat.parse(toDate)));
                    fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", repName);
                    fillParams.put("pClosingBalance", closingBalance);
                    fillParams.put("pAvailableBalance", availableBalance);
                    fillParams.put("pIfscCode", bnkpRemote.getIfscCodeByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("image", "/opt/images/logo.png");
                    List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                    Vector vtr = (Vector) brinfoList.get(0);
                    fillParams.put("pBranchName", vtr.get(0).toString());
                    fillParams.put("pBranchEmail", vtr.get(1).toString());
                    String bankOrSoc = "";
                    Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("Society");
                    if (bnkOrSoc == 1) {
                        bankOrSoc = "Society";
                    } else {
                        bankOrSoc = "Bank";
                    }
                    fillParams.put("pBankOrSociety", bankOrSoc);

                    String acNat = getEle.getAcNature();
                    if (acNat.equalsIgnoreCase("DS")) {
                        fillParams.put("vChqNo", "Receipt No.");
                    } else {
                        fillParams.put("vChqNo", "Cheque No.");
                    }

                    HttpSession session = getHttpSession();
                    session.setAttribute("actualFromDt", fromDate);
                    session.setAttribute("actualToDt", toDate);
                    session.setAttribute("custNo", this.newAcNo);
                    session.setAttribute("rows", rows);
                    ReportBean reportBean = new ReportBean();

                    //String acNature = this.newAcNo.substring(3, 2);

                    if ((acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) && ftsPostRemote.existInParameterInfoReport("FD-STATEMENT")) {

                        fillParams.put("pPrint", "Yes");
                        List<AccountStatementReportPojo> fdList = beanRemote.getAccountStatementFdData(this.newAcNo, fromDate, toDate);
                        List<AccountStatementReportPojo> finalList = getFinalList(accountStatementReport, fdList);

                        reportBean.jasperReport("ACCOUNT_STATEMENT_FD", "text/html", new JRBeanCollectionDataSource(finalList), fillParams, repName);
                        session.setAttribute("pages", reportBean.TOTAL_NO_OF_PAGES_IN_REPORT);
                        return "report";
                    } else {
//                session.setAttribute("pages", reportBean.TOTAL_NO_OF_PAGES_IN_REPORT);
                        reportBean.jasperReport("ACCOUNT_STATEMENT", "text/html", new JRBeanCollectionDataSource(accountStatementReport), fillParams, repName);
                        session.setAttribute("pages", reportBean.TOTAL_NO_OF_PAGES_IN_REPORT);
                        return "report";
                    }

                } else {
                    setMessage("No data to print !!");
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String downLoadExcelAction() {
        setMessage("");
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

            if (fromDate == null || fromDate.equalsIgnoreCase("")) {
                setMessage("Please fill From Date !!");
                return null;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                setMessage("Please fill To Date !!");
                return null;
            }
            fromDate = fromDate.substring(6) + fromDate.substring(3, 5) + fromDate.substring(0, 2);
            toDate = toDate.substring(6) + toDate.substring(3, 5) + toDate.substring(0, 2);
            if (ymdFormat.parse(fromDate).after(ymdFormat.parse(toDate))) {
                setMessage("From Date should be less than To Date !!");
                return null;
            }
            if (ymdFormat.parse(toDate).after(new Date())) {
                setMessage("To Date should be less than Current Date !!");
                return null;
            }
            String acNature = ftsPostRemote.getAccountNature(newAcNo);
            if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                List npaStatusList = new ArrayList();
                Map fillParams = new HashMap();

                String report = "RD ACCOUNT STATEMENT";
                String showPeriod = "Y";


                List<LoanAccStmPojo> resultList = loanRemote.loanAccountStatement(newAcNo, fromDate, toDate);
                if (!resultList.isEmpty()) {
                    //String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String s[] = commonRemote.getBranchNameandAddressString(commonRemote.getBrncodeByAcno(newAcNo)).split("!");
                    fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                    fillParams.put("pStmtPeriod", dmyFormat.format(ymdFormat.parse(fromDate)) + " to " + dmyFormat.format(ymdFormat.parse(toDate)));
                    fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pShowPeriod", showPeriod);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("image", "/opt/images/logo.png");
                    List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                    Vector vtr = (Vector) brinfoList.get(0);
                    fillParams.put("pBranchName", vtr.get(0).toString());
                    fillParams.put("pBranchEmail", vtr.get(1).toString());
                    String bankOrSoc = "";
                    Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("Society");
                    if (bnkOrSoc == 1) {
                        bankOrSoc = "Society";
                    } else {
                        bankOrSoc = "Bank";
                    }
                    fillParams.put("pBankOrSociety", bankOrSoc);
                }
                if (npaStatusList.isEmpty()) {
                    if (!resultList.isEmpty()) {
                        new ReportBean().dynamicExcelJasper("LoanAccountStatement", "excel", new JRBeanCollectionDataSource(resultList), fillParams, report);
//                        new ReportBean().jasperReport("LoanAccountStatement", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
//                        return "report";
                    } else {
                        setMessage("Data does not exist");
                    }
                }
            } else {
                List<AccountStatementReportPojo> accountStatementReport = beanRemote.getAccountStatementReport(this.newAcNo, fromDate, toDate);
                System.out.println(accountStatementReport.size());
                int rows = accountStatementReport.size();
                if (accountStatementReport == null) {
                    setMessage("Account number does not exists !!");
                    return null;
                }
                if (!accountStatementReport.isEmpty()) {
                    AccountStatementReportPojo getEle = accountStatementReport.get(accountStatementReport.size() - 1);
                    double closingBalance = getEle.getBalance();
                    double availableBalance = closingBalance - getEle.getPendingBal();
                    String bankName = "";
                    String branchAddress = "";
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(common.getBrncodeByAcno(newAcNo));
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String repName = "Account Statement";
                    Map fillParams = new HashMap();
                    fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pStmtPeriod", dmyFormat.format(ymdFormat.parse(fromDate)) + " to " + dmyFormat.format(ymdFormat.parse(toDate)));
                    fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", repName);
                    fillParams.put("pClosingBalance", closingBalance);
                    fillParams.put("pAvailableBalance", availableBalance);
                    fillParams.put("pIfscCode", bnkpRemote.getIfscCodeByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("image", "/opt/images/logo.png");
                    List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                    Vector vtr = (Vector) brinfoList.get(0);
                    fillParams.put("pBranchName", vtr.get(0).toString());
                    fillParams.put("pBranchEmail", vtr.get(1).toString());
                    String bankOrSoc = "";
                    Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("Society");
                    if (bnkOrSoc == 1) {
                        bankOrSoc = "Society";
                    } else {
                        bankOrSoc = "Bank";
                    }
                    fillParams.put("pBankOrSociety", bankOrSoc);

                    String acNat = getEle.getAcNature();
                    if (acNat.equalsIgnoreCase("DS")) {
                        fillParams.put("vChqNo", "Receipt No.");
                    } else {
                        fillParams.put("vChqNo", "Cheque No.");
                    }

                    HttpSession session = getHttpSession();
                    session.setAttribute("actualFromDt", fromDate);
                    session.setAttribute("actualToDt", toDate);
                    session.setAttribute("custNo", this.newAcNo);
                    session.setAttribute("rows", rows);
                    ReportBean reportBean = new ReportBean();

                    //String acNature = this.newAcNo.substring(3, 2);

                    if ((acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) && ftsPostRemote.existInParameterInfoReport("FD-STATEMENT")) {

                        fillParams.put("pPrint", "Yes");
                        List<AccountStatementReportPojo> fdList = beanRemote.getAccountStatementFdData(this.newAcNo, fromDate, toDate);
                        List<AccountStatementReportPojo> finalList = getFinalList(accountStatementReport, fdList);
                        new ReportBean().dynamicExcelJasper("ACCOUNT_STATEMENT_FD", "excel", new JRBeanCollectionDataSource(finalList), fillParams, repName);
                        //reportBean.jasperReport("ACCOUNT_STATEMENT_FD", "text/html", new JRBeanCollectionDataSource(finalList), fillParams, repName);
                        session.setAttribute("pages", reportBean.TOTAL_NO_OF_PAGES_IN_REPORT);
                        return "report";

                    } else {
                        //session.setAttribute("pages", reportBean.TOTAL_NO_OF_PAGES_IN_REPORT);
                         //Map fillParams1 = new HashMap();
                        new ReportBean().dynamicExcelJasper("ACCOUNT_STATEMENT_EXCEL", "excel", new JRBeanCollectionDataSource(accountStatementReport), fillParams, repName);
                        // reportBean.jasperReport("ACCOUNT_STATEMENT", "text/html", new JRBeanCollectionDataSource(accountStatementReport), fillParams, repName);
                        session.setAttribute("pages", reportBean.TOTAL_NO_OF_PAGES_IN_REPORT);
                        //return "report";
                    }

                } else {
                    setMessage("No data to print !!");
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String downloadPdf() {
        setMessage("");
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            if (fromDate == null || fromDate.equalsIgnoreCase("")) {
                setMessage("Please fill From Date !!");
                return null;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                setMessage("Please fill To Date !!");
                return null;
            }
            fromDate = fromDate.substring(6) + fromDate.substring(3, 5) + fromDate.substring(0, 2);
            toDate = toDate.substring(6) + toDate.substring(3, 5) + toDate.substring(0, 2);
            if (ymdFormat.parse(fromDate).after(ymdFormat.parse(toDate))) {
                setMessage("From Date should be less than To Date !!");
                return null;
            }
            if (ymdFormat.parse(toDate).after(new Date())) {
                setMessage("To Date should be less than Current Date !!");
                return null;
            }

            String acNature = ftsPostRemote.getAccountNature(newAcNo);
            if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                List npaStatusList = new ArrayList();
                Map fillParams = new HashMap();

                String report = "RD ACCOUNT STATEMENT";
                String showPeriod = "Y";


                List<LoanAccStmPojo> resultList = loanRemote.loanAccountStatement(newAcNo, fromDate, toDate);
                if (!resultList.isEmpty()) {
                    //String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String s[] = commonRemote.getBranchNameandAddressString(commonRemote.getBrncodeByAcno(newAcNo)).split("!");
                    fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                    fillParams.put("pStmtPeriod", dmyFormat.format(ymdFormat.parse(fromDate)) + " to " + dmyFormat.format(ymdFormat.parse(toDate)));
                    fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pShowPeriod", showPeriod);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("image", "/opt/images/logo.png");
                    List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                    Vector vtr = (Vector) brinfoList.get(0);
                    fillParams.put("pBranchName", vtr.get(0).toString());
                    fillParams.put("pBranchEmail", vtr.get(1).toString());
                    String bankOrSoc = "";
                    Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("Society");
                    if (bnkOrSoc == 1) {
                        bankOrSoc = "Society";
                    } else {
                        bankOrSoc = "Bank";
                    }
                    fillParams.put("pBankOrSociety", bankOrSoc);
                }

                if (!resultList.isEmpty()) {
                    new ReportBean().downloadPdf(newAcNo + fromDate + toDate, "LoanAccountStatement", new JRBeanCollectionDataSource(resultList), fillParams);
                    //return "report";
                } else {
                    setMessage("Data does not exist");
                }

            } else {
                List<AccountStatementReportPojo> accountStatementReport = beanRemote.getAccountStatementReport(this.newAcNo, fromDate, toDate);
                System.out.println(accountStatementReport.size());
                int rows = accountStatementReport.size();

                if (!accountStatementReport.isEmpty()) {
                    AccountStatementReportPojo getEle = accountStatementReport.get(accountStatementReport.size() - 1);
                    double closingBalance = getEle.getBalance();
                    double availableBalance = closingBalance - getEle.getPendingBal();
                    String bankName = "";
                    String branchAddress = "";
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(common.getBrncodeByAcno(newAcNo));
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String repName = "Account Statement";
                    Map fillParams = new HashMap();
                    fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pStmtPeriod", dmyFormat.format(ymdFormat.parse(fromDate)) + " to " + dmyFormat.format(ymdFormat.parse(toDate)));
                    fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", repName);
                    fillParams.put("pClosingBalance", closingBalance);
                    fillParams.put("pAvailableBalance", availableBalance);
                    fillParams.put("pIfscCode", bnkpRemote.getIfscCodeByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcNo.substring(0, 2))));
                    fillParams.put("image", "/opt/images/logo.png");
                    List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                    Vector vtr = (Vector) brinfoList.get(0);
                    fillParams.put("pBranchName", vtr.get(0).toString());
                    fillParams.put("pBranchEmail", vtr.get(1).toString());
                    String bankOrSoc = "";
                    Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("Society");
                    if (bnkOrSoc == 1) {
                        bankOrSoc = "Society";
                    } else {
                        bankOrSoc = "Bank";
                    }
                    fillParams.put("pBankOrSociety", bankOrSoc);

                    String acNat = getEle.getAcNature();
                    if (acNat.equalsIgnoreCase("DS")) {
                        fillParams.put("vChqNo", "Receipt No.");
                    } else {
                        fillParams.put("vChqNo", "Cheque No.");
                    }

                    HttpSession session = getHttpSession();
                    session.setAttribute("actualFromDt", fromDate);
                    session.setAttribute("actualToDt", toDate);
                    session.setAttribute("custNo", this.newAcNo);
                    session.setAttribute("rows", rows);

                    new ReportBean().openPdf(repName, "ACCOUNT_STATEMENT_PDF", new JRBeanCollectionDataSource(accountStatementReport), fillParams);
                    session.setAttribute("ReportName", "Account Statement");
                    return "report";
                } else {
                    this.setMessage("No data to print !!");
                    return null;
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return null;
    }

    public void downloadAction() {
        if (bnkCode.equalsIgnoreCase("CCBL")) {
            downloadExl();
        } else {
            downLoadExcelAction();
        }
    }

    public void downloadExl() {
        fromDate = fromDate.substring(6) + fromDate.substring(3, 5) + fromDate.substring(0, 2);
        toDate = toDate.substring(6) + toDate.substring(3, 5) + toDate.substring(0, 2);
        try {
            List<AccountStatementReportPojo> accountStatementReport = beanRemote.getAccountStatementReport(this.newAcNo, fromDate, toDate);
            System.out.println(accountStatementReport.size());
            if (!accountStatementReport.isEmpty()) {
                FastReportBuilder fastReportBuilder = genrateDaynamicReport("Account Statement");
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(accountStatementReport), fastReportBuilder, "Account Statement");
            } else {
                this.setMessage("No data to print !!");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public List<AccountStatementReportPojo> getFinalList(List<AccountStatementReportPojo> resultList, List<AccountStatementReportPojo> fdList) {
        List<AccountStatementReportPojo> finalList = new ArrayList<AccountStatementReportPojo>();
        try {

            for (int i = 0; i < resultList.size(); i++) {
                AccountStatementReportPojo pojo = new AccountStatementReportPojo();
                AccountStatementReportPojo mainPojo = resultList.get(i);

                pojo.setAcNo(mainPojo.getAcNo());
                pojo.setPerStatus(mainPojo.getPerStatus());
                pojo.setAcStatusDesc(mainPojo.getAcStatusDesc());
                pojo.setAcType(mainPojo.getAcType());
                pojo.setAcStatus(mainPojo.getAcStatus());
                pojo.setBalance(mainPojo.getBalance());
                pojo.setChequeNo(mainPojo.getChequeNo());
                pojo.setCrAdd(mainPojo.getCrAdd());
                pojo.setCustName(mainPojo.getCustName());
                pojo.setDate(mainPojo.getDate());
                pojo.setDeposit(mainPojo.getDeposit());
                pojo.setJtName(mainPojo.getJtName());
                pojo.setNomination(mainPojo.getNomination());
                pojo.setOpeningBal(mainPojo.getOpeningBal());
                pojo.setParticulars(mainPojo.getParticulars());
                pojo.setPendingBal(mainPojo.getPendingBal());
                pojo.setPrAdd(mainPojo.getPrAdd());
                pojo.setValueDate(mainPojo.getValueDate());
                pojo.setWithdrawl(mainPojo.getWithdrawl());
                pojo.setAcNature(mainPojo.getAcNature());
                pojo.setJtName2(mainPojo.getJtName2());
                pojo.setJtName3(mainPojo.getJtName3());
                pojo.setJtName4(mainPojo.getJtName4());
                pojo.setGstrn(mainPojo.getGstrn());
                pojo.setType("N");
                finalList.add(pojo);
            }

            for (int i = 0; i < fdList.size(); i++) {
                AccountStatementReportPojo pojo = new AccountStatementReportPojo();
                AccountStatementReportPojo fdPojo = fdList.get(i);
                AccountStatementReportPojo mainPojo = resultList.get(0);

                pojo.setAcNo(fdPojo.getAcNo());
                pojo.setPerStatus(mainPojo.getPerStatus());
                pojo.setAcStatusDesc(mainPojo.getAcStatusDesc());
                pojo.setAcType(fdPojo.getAcType());

                pojo.setAcStatus(mainPojo.getAcStatus());
                pojo.setBalance(mainPojo.getBalance());
                pojo.setChequeNo(fdPojo.getChequeNo());
                pojo.setCrAdd(mainPojo.getCrAdd());
                pojo.setCustName(mainPojo.getCustName());
                pojo.setDate(fdPojo.getDate());
                pojo.setDeposit(fdPojo.getDeposit());
                pojo.setJtName(mainPojo.getJtName());
                pojo.setNomination(mainPojo.getNomination());
                pojo.setOpeningBal(mainPojo.getOpeningBal());
                pojo.setParticulars(fdPojo.getParticulars());
                pojo.setPendingBal(mainPojo.getPendingBal());
                pojo.setPrAdd(mainPojo.getPrAdd());
                pojo.setValueDate("");
                pojo.setWithdrawl(fdPojo.getWithdrawl());
                pojo.setAcNature(mainPojo.getAcNature());
                pojo.setJtName2(mainPojo.getJtName2());
                pojo.setJtName3(mainPojo.getJtName3());
                pojo.setJtName4(mainPojo.getJtName4());
                pojo.setGstrn(mainPojo.getGstrn());
                pojo.setType("G");
                finalList.add(pojo);
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
        return finalList;
    }

    public FastReportBuilder genrateDaynamicReport(String reportName) {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account No", 90);
            AbstractColumn name = ReportBean.getColumn("custName", String.class, "Customer Name", 180);

            AbstractColumn date = ReportBean.getColumn("date", String.class, "Date", 80);
            AbstractColumn valueDt = ReportBean.getColumn("valueDate", String.class, "Value Date", 80);

            AbstractColumn particulars = ReportBean.getColumn("particulars", String.class, "Particular", 250);

            AbstractColumn chequeNo = ReportBean.getColumn("chequeNo", String.class, "Inst No", 80);

            // AbstractColumn withdrawl = ReportBean.getColumn("withdrawl", Double.class, "Debit", 80);
            // AbstractColumn deposit = ReportBean.getColumn("deposit", Double.class, "Credit", 80);
            // AbstractColumn balance = ReportBean.getColumn("balance", Double.class, "Balance", 80);

            ColumnBuilder clmBuilderWithdrawal = ColumnBuilder.getNew();

            clmBuilderWithdrawal.setTitle("Debit");
            clmBuilderWithdrawal.setWidth(80);
            clmBuilderWithdrawal.setFixedWidth(true);
            clmBuilderWithdrawal.setPattern("##,##0.00");
            clmBuilderWithdrawal.setColumnProperty("Debit", Double.class.getName(), "withdrawl");
            AbstractColumn withdrawl = clmBuilderWithdrawal.build();

            ColumnBuilder clmBuilderDep = ColumnBuilder.getNew();

            clmBuilderDep.setTitle("Credit");
            clmBuilderDep.setWidth(80);
            clmBuilderDep.setFixedWidth(true);
            clmBuilderDep.setPattern("##,##0.00");
            clmBuilderDep.setColumnProperty("Credit", Double.class.getName(), "deposit");

            AbstractColumn deposit = clmBuilderDep.build();

            ColumnBuilder clmBuilderBal = ColumnBuilder.getNew();

            clmBuilderBal.setTitle("Balance");
            clmBuilderBal.setWidth(80);
            clmBuilderBal.setFixedWidth(true);
            clmBuilderBal.setPattern("##,##0.00");
            clmBuilderBal.setColumnProperty("Balance", Double.class.getName(), "balance");

            AbstractColumn balance = clmBuilderBal.build();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(name);
            width = width + name.getWidth();

            fastReport.addColumn(date);
            width = width + date.getWidth();

            fastReport.addColumn(valueDt);
            width = width + valueDt.getWidth();

            fastReport.addColumn(particulars);
            width = width + particulars.getWidth();

            fastReport.addColumn(chequeNo);
            width = width + chequeNo.getWidth();



            fastReport.addColumn(withdrawl);
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);

            style.setBorder(Border.THIN);
            withdrawl.setStyle(style);
            deposit.setStyle(style);
            balance.setStyle(style);

            fastReport.addColumn(deposit);
            fastReport.addColumn(balance);
            width = width + 3 * deposit.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle(reportName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getBnkCode() {
        return bnkCode;
    }

    public void setBnkCode(String bnkCode) {
        this.bnkCode = bnkCode;
    }
}
