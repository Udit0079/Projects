package com.cbs.web.controller.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.report.PostingReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.other.DailyProcessManagementRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
public class SbIntCalAll extends BaseBean {

    private String message;
    // private String interestOption;
    private String branchOption;
    private String acctStatus;
    private String accountType;
    //private String accountNo;
    private String debitAcc;
    private String creditAcc;
    private String totalCredit;
    private String totalDebit;
    private boolean disablePost;
    private boolean postReportFlag;
    private boolean disableCal;
    private Date fromDate;
    private Date toDate;
    boolean fromDisable;
    boolean toDisable;
    private List<SelectItem> acctStatusList;
    private List<SelectItem> accountTypeList;
    private List<LoanIntCalcList> intCalc;
    private List<SelectItem> branchOptionList;
    private boolean reportFlag;
    private String panelMsg;
    private final String sbIntCalcJndiName = "SbIntCalcFacade";
    private SbIntCalcFacadeRemote sbIntCalcRemote = null;
    private CommonReportMethodsRemote common = null;
    LoanGenralFacadeRemote loanGenralFacade;
    DailyProcessManagementRemote dpRemote;
    String focusEle;
    Map<String, List<LoanIntCalcList>> resultMap;
    private String viewID = "/pages/master/sm/test.jsp";

    public boolean isDisableCal() {
        return disableCal;
    }

    public void setDisableCal(boolean disableCal) {
        this.disableCal = disableCal;
    }

    public String getBranchOption() {
        return branchOption;
    }

    public void setBranchOption(String branchOption) {
        this.branchOption = branchOption;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
    }

    public String getFocusEle() {
        return focusEle;
    }

    public void setFocusEle(String focusEle) {
        this.focusEle = focusEle;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getPanelMsg() {
        return panelMsg;
    }

    public void setPanelMsg(String panelMsg) {
        this.panelMsg = panelMsg;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
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

    public List<LoanIntCalcList> getIntCalc() {
        return intCalc;
    }

    public void setIntCalc(List<LoanIntCalcList> intCalc) {
        this.intCalc = intCalc;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getCreditAcc() {
        return creditAcc;
    }

    public void setCreditAcc(String creditAcc) {
        this.creditAcc = creditAcc;
    }

    public String getDebitAcc() {
        return debitAcc;
    }

    public void setDebitAcc(String debitAcc) {
        this.debitAcc = debitAcc;
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

//    public String getAccountNo() {
//        return accountNo;
//    }
//
//    public void setAccountNo(String accountNo) {
//        this.accountNo = accountNo;
//    }
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

//    public String getInterestOption() {
//        return interestOption;
//    }
//
//    public void setInterestOption(String interestOption) {
//        this.interestOption = interestOption;
//    }
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

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public List<SelectItem> getAcctStatusList() {
        return acctStatusList;
    }

    public void setAcctStatusList(List<SelectItem> acctStatusList) {
        this.acctStatusList = acctStatusList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    Date date = new Date();

    public boolean isPostReportFlag() {
        return postReportFlag;
    }

    public void setPostReportFlag(boolean postReportFlag) {
        this.postReportFlag = postReportFlag;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public SbIntCalAll() {
        try {
            sbIntCalcRemote = (SbIntCalcFacadeRemote) ServiceLocator.getInstance().lookup(sbIntCalcJndiName);
            dpRemote = (DailyProcessManagementRemote) ServiceLocator.getInstance().lookup("DailyProcessManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            intCalc = new ArrayList<LoanIntCalcList>();
            setMessage("");
            getValues();
            branchOptionList = new ArrayList<SelectItem>();
//            List allBranchCodeList = dpRemote.getAllBranchCodeExceptHO();
            List allBranchCodeList = dpRemote.getAllBranchCode();
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchOptionList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString() : vec.get(0).toString(), vec.get(1).toString()));
                }
            }
            setReportFlag(false);
            setDisableCal(false);
            setDisablePost(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getValues() {
        try {
            setReportFlag(false);
            acctStatusList = new ArrayList<SelectItem>();
            List resultList = sbIntCalcRemote.getAcctType();
            if (!resultList.isEmpty()) {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", "--Select--"));
                accountTypeList.add(new SelectItem("A", "All"));
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

    public void setBranchOptions() {
        try {
            intCalc = new ArrayList<LoanIntCalcList>();
            setReportFlag(false);
            setToDisable(false);
            setFromDisable(false);
            setDisablePost(true);
            setAccountType("0");
            setDebitAcc("");
            setCreditAcc("");
            setTotalCredit("");
            setTotalDebit("");
            setFocusEle("ddAccountType");
            setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setAccountStatus() {
        try {
            this.setMessage("");
            if (accountType == null || accountType.equals("0")) {
                throw new ApplicationException("Please select Account type");
            }
            System.out.println("accountType -->" + accountType);
            if (accountType.equals("A")) {
                acctStatusList = new ArrayList<SelectItem>();
                acctStatusList.add(new SelectItem("A", "All"));
            } else {
                acctStatusList = new ArrayList<SelectItem>();
                acctStatusList.add(new SelectItem("1", "Operative"));
                acctStatusList.add(new SelectItem("2", "In-Operative"));
            }
            setFocusEle("ddAcctStatus");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setDateAllAccount() {
        try {
            this.setMessage("");
            setDebitAcc("");
            setCreditAcc("");
            setTotalCredit("");
            setTotalDebit("");
            intCalc = new ArrayList<LoanIntCalcList>();
            String fromDt = "";
            String toDt = "";
            if (accountType == null || accountType.equals("0")) {
                throw new ApplicationException("Please select Account type");
            }
            System.out.println("accountType -->" + accountType);
            if (accountType.equals("A")) {
                fromDt = sbIntCalcRemote.getAllFromDt("f");
                toDt = sbIntCalcRemote.getAllFromDt("t");

                setDisableCal(true);
                setDisablePost(false);
                setFocusEle("btnCalculate");
            } else {
                fromDt = sbIntCalcRemote.allFromDt(accountType, branchOption, "f", acctStatus);
                toDt = sbIntCalcRemote.allFromDt(accountType, branchOption, "t", acctStatus);

                setDisableCal(false);
                setDisablePost(true);
                setFocusEle("btnPost");
            }
            this.setFromDate(sdf.parse(sdf.format(ymd.parse(fromDt))));
            setFromDisable(true);
            this.setToDate(sdf.parse(sdf.format(ymd.parse(toDt))));
            setToDisable(true);

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void ddAccountNo(String acctNo) {
        try {
            List resultList = new ArrayList();
            resultList = sbIntCalcRemote.chkGLHead(acctNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    setDebitAcc(ele.get(0).toString());
                }
            } else {
                setDebitAcc("");
                this.setMessage("No Account Exists For Debit.");
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void calculate() {
        setMessage("");
        setReportFlag(false);
        intCalc = new ArrayList<LoanIntCalcList>();
        try {
            ddAccountNo(accountType);
            if (fromDate == null) {
                setMessage("Please select from date.");
                return;
            }
            if (toDate == null) {
                setMessage("Please select to date.");
                return;
            }
            if (debitAcc == null || debitAcc.equalsIgnoreCase("")) {
                setMessage("Debit Account is not set.");
                return;
            }
            if (accountType.equalsIgnoreCase("0")) {
                setMessage("Please select account type.");
                return;
            }
            double amount = 0d;
            NumberFormat formatter = new DecimalFormat("#0.00");
            //List<LoanIntCalcList> cbsSbIntCalc = sbIntCalcRemote.cbsSbIntCalc(interestOption, acctStatus, accountType, accountNo, sdf.format(fromDate), sdf.format(toDate), branchOption);
            intCalc = sbIntCalcRemote.cbsSbIntCalc("A", acctStatus, accountType, "", sdf.format(fromDate), sdf.format(toDate), branchOption);

            if (intCalc.isEmpty()) {  // Added for Year-End.
                throw new ApplicationException("There is no product to calculate the interest.");
            }
            for (LoanIntCalcList lict : intCalc) {
                amount = amount + lict.getTotalInt();
            }
            long mills = System.currentTimeMillis();
            setCreditAcc("As per list");
            setTotalCredit(formatter.format(amount));
            setTotalDebit(formatter.format(amount));

            setDisablePost(false);
            setToDisable(true);
            setDisableCal(true);
            this.setMessage("Interest has been calculated successfully. Now you can post your interest !");
            long diff = System.currentTimeMillis() - mills;
            System.out.println("Total report printing time is = " + diff);

            /**
             * *************Commented by Dinesh For Year End
             * Closing***********************
             */
//            if (!accountType.equals("A")) {
//                String frmDate = sdf.format(fromDate);
//                String toDt = sdf.format(toDate);
//
//                String repName = "Interest Calculation";
//                String report = "Interest Calculation Report";
//                List vec = common.getBranchNameandAddress(getOrgnBrCode());
//                Map fillParams = new HashMap();
//
//                fillParams.put("pReportName", repName);
//                fillParams.put("pReportDate", getTodayDate());
//                fillParams.put("pPrintedBy", getUserName());
//
//                fillParams.put("pFromDt", frmDate);
//                fillParams.put("pToDt", toDt);
//                fillParams.put("pBankName", vec.get(0).toString());
//                fillParams.put("pBankAdd", vec.get(1).toString());
//                new ReportBean().jasperReport("SbIntCalAllBranch", "text/html", new JRBeanCollectionDataSource(intCalc), fillParams, report);
//                this.setViewID("/report/ReportPagePopUp.jsp");
//                setReportFlag(true);
//                long diff = System.currentTimeMillis() - mills;
//                System.out.println("Total report printing time is = " + diff);
//            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        }
    }

    public void Post() {
        try {
            String result = "";
            if (accountType.equals("A")) {
                result = sbIntCalcRemote.sbAllIntPosting(sdf.format(fromDate), sdf.format(toDate), getUserName(), branchOption);
            } else {
                if (intCalc.isEmpty()) {
                    this.setMessage("Please calculate the interest and then post.");
                    return;
                }
                result = sbIntCalcRemote.sbInterestPosting(intCalc, "A", acctStatus, accountType, sdf.format(fromDate),
                        sdf.format(toDate), debitAcc, getUserName(), branchOption);

                if (result.contains("Successfully")) {
                    String[] trsno = result.split(":");
                    List<PostingReportPojo> postingReport = common.getPostingReport(Float.parseFloat(trsno[1]), accountType, getUserName(), ymd.format(sdf.parse(getTodayDate())), branchOption);
                    if (!postingReport.isEmpty()) {
                        String report = "SB Interest Posting Report";
                        postReportFlag = true;
                        Map fillParams = new HashMap();
                        String s[] = common.getBranchNameandAddressString(branchOption).split("!");
                        fillParams.put("pReportName", report);
                        fillParams.put("pReportDate", sdf.format(fromDate) + " to " + sdf.format(toDate));
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        new ReportBean().jasperReport("PostingReport", "text/html", new JRBeanCollectionDataSource(postingReport), fillParams, report);
                        this.setViewID("/report/ReportPagePopUp.jsp");
                    } else {
                        setPostReportFlag(false);
                    }
                }
            }
            setReportFlag(false);
            setMessage(result);
            setAccountType("0");
            setDebitAcc("");
            setCreditAcc("");

            setTotalCredit("");
            setTotalDebit("");
            setToDisable(false);
            setFromDisable(false);

            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            if (intCalc != null) {
                intCalc.clear();;
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void panelmsgShow() {
        try {
            if (intCalc != null) {
                intCalc.clear();
            }
            this.setPanelMsg("Are you sure to calculate interest for A/C. Type " + this.accountType);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        try {
            setReportFlag(false);
            setMessage("");

            setAccountType("0");
            setDebitAcc("");
            setCreditAcc("");

            setTotalCredit("");
            setTotalDebit("");
            setToDisable(false);
            setFromDisable(false);

            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            postReportFlag = false;
            if (intCalc != null) {
                intCalc.clear();
            }
            /**
             * New Added Year End*
             */
            setDisableCal(false);
            setDisablePost(true);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        this.setMessage("");
        refresh();
        return "case1";
    }
}
