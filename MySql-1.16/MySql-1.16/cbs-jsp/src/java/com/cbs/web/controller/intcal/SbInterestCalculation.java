/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.web.pojo.intcal.IntCalTable;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanIntProductTestFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
public class SbInterestCalculation extends BaseBean {

    private String message;
    boolean allAcc;
    boolean accWise;
    private String interestOption;
    private String acctStatus;
    private String accountType;
    private String accountNo;
    private String debitAcc;
    private String creditAcc;
    private String totalCredit;
    private String totalDebit;
    private boolean disablePost;
    private Date fromDate;
    private Date toDate;
    boolean fromDisable;
    boolean toDisable;
    private List<SelectItem> interestOptionList;
    private List<SelectItem> acctStatusList;
    private List<SelectItem> accountTypeList;
    private List<IntCalTable> intCalc;
    private String acCloseFlag = null;
    private boolean reportFlag;
    private String panelMsg;
    private boolean provisionInt;
    private final String sbIntCalcJndiName = "SbIntCalcFacade";
    private SbIntCalcFacadeRemote sbIntCalcRemote = null;
    private final String LoanIntTestCalcJndiName = "LoanIntProductTestFacade";
    private LoanIntProductTestFacadeRemote loanIntTestCalcRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    LoanGenralFacadeRemote loanGenralFacade;
    String oldAccNo, acNoLen;
    String focusEle;
    private String viewID = "/pages/master/sm/test.jsp";

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

    public boolean isAccWise() {
        return accWise;
    }

    public void setAccWise(boolean accWise) {
        this.accWise = accWise;
    }

    public boolean isAllAcc() {
        return allAcc;
    }

    public void setAllAcc(boolean allAcc) {
        this.allAcc = allAcc;
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

    public List<IntCalTable> getIntCalc() {
        return intCalc;
    }

    public void setIntCalc(List<IntCalTable> intCalc) {
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

    public String getInterestOption() {
        return interestOption;
    }

    public void setInterestOption(String interestOption) {
        this.interestOption = interestOption;
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

    public List<SelectItem> getInterestOptionList() {
        return interestOptionList;
    }

    public void setInterestOptionList(List<SelectItem> interestOptionList) {
        this.interestOptionList = interestOptionList;
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
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public SbInterestCalculation() {
        try {
            sbIntCalcRemote = (SbIntCalcFacadeRemote) ServiceLocator.getInstance().lookup(sbIntCalcJndiName);

            loanIntTestCalcRemote = (LoanIntProductTestFacadeRemote) ServiceLocator.getInstance().lookup(LoanIntTestCalcJndiName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);

            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            this.setAcNoLen(getAcNoLength());
            intCalc = new ArrayList<IntCalTable>();
            setMessage("");
            setDisablePost(true);
            getValues();
            if (getHttpRequest().getParameter("code") == null || getHttpRequest().getParameter("code").toString().equals("")) {
                this.setAcCloseFlag("");
                setAllAcc(true);
            } else {
                this.setOldAccNo(getHttpRequest().getParameter("code").toString());
                this.setAccountNo(getHttpRequest().getParameter("code").toString());
                this.setAcCloseFlag(getHttpRequest().getParameter("code").toString());
                setAllAcc(false);
                setAccWise(true);
                setInterestOption("I");
            }
            if (getHttpRequest().getRequestURL().indexOf("SbInterestProvCalc.jsp") > 0) {
                provisionInt = true;
            } else {
                provisionInt = false;
            }
            setReportFlag(false);

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getValues() {
        try {
            setReportFlag(false);
            interestOptionList = new ArrayList<SelectItem>();
            interestOptionList.add(new SelectItem("A", "All Account"));
            interestOptionList.add(new SelectItem("I", "Indivisual"));

            acctStatusList = new ArrayList<SelectItem>();
            acctStatusList.add(new SelectItem("1", "Operative"));
            acctStatusList.add(new SelectItem("2", "In-Operative"));

            List resultList = sbIntCalcRemote.getAcctType();
            if (!resultList.isEmpty()) {
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

    public void setIntOptions() {
        try {
            intCalc = new ArrayList<IntCalTable>();
            setReportFlag(false);
            if (getInterestOption().equals("A")) {
                setAllAcc(true);
                setAccWise(false);
                setFocusEle("ddAcctStatus");
            } else {
                setAllAcc(false);
                setAccWise(true);
                setFocusEle("txtAccountNo");
            }
            setToDisable(false);
            setFromDisable(false);
            setDisablePost(true);

            setAccountType("0");
            setDebitAcc("");
            setCreditAcc("");
            setTotalCredit("");
            setTotalDebit("");
            setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setDateAllAccount() {
        try {
            this.setMessage("");
            setDebitAcc("");
            setCreditAcc("");
            setTotalCredit("");
            setTotalDebit("");
            intCalc = new ArrayList<IntCalTable>();
            String fromDt = "";
            String toDt = "";
            if (accountType == null || accountType.equals("0")) {
                accountType = "";
            }
            System.out.println("accountType -->" + accountType);
            if (!provisionInt && !accountType.equals("0") && !accountType.equals("")) {
                fromDt = sbIntCalcRemote.allFromDt(accountType, getOrgnBrCode(), "f", acctStatus);
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(fromDt))));
                setFromDisable(true);
                toDt = sbIntCalcRemote.allFromDt(accountType, getOrgnBrCode(), "t", acctStatus);
                this.setToDate(sdf.parse(sdf.format(ymd.parse(toDt))));
                setToDisable(true);
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setDateAccountWise() {
        this.setMessage("");
        setDebitAcc("");
        setCreditAcc("");
        setTotalCredit("");
        setTotalDebit("");
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
            setAccountNo(ftsPostRemote.getNewAccountNumber(oldAccNo));
            String curBrnCode = ftsPostRemote.getCurrentBrnCode(accountNo);
            if (!curBrnCode.equalsIgnoreCase(getOrgnBrCode())) {
                this.setMessage("Account should be of self branch");
                return;
            }
            if (!provisionInt) {
                String result = sbIntCalcRemote.acWiseFromDt(accountNo, getOrgnBrCode());
                this.setToDate(date);
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(result))));
                setFromDisable(true);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String ddAccountNo(String acctCode) throws ApplicationException {
        try {
            List resultList = new ArrayList();
            resultList = sbIntCalcRemote.chkGLHead(acctCode);
            if (resultList.isEmpty()) {
                setDebitAcc("");
                throw new ApplicationException("Account does not exists for debit.");
            } 
            Vector ele = (Vector) resultList.get(0);
            return ele.get(0).toString();
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void calculate() {
        setMessage("");
        setReportFlag(false);
        intCalc = new ArrayList<IntCalTable>();
        try {
            if (interestOption.equals("I")) {
                if (oldAccNo.equalsIgnoreCase("") || oldAccNo == null) {
                    setMessage("Please enter the account no.");
                    return;
                } else if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please enter proper account no.");
                    return;
                } else {
                    accountType = ftsPostRemote.getAccountCode(accountNo);
                    List dataList = ftsPostRemote.getUnAuthorizedTranListForAcno(accountNo);
                    if (!dataList.isEmpty()) {
                        this.setMessage("There are some pending authorization !");
                        return;
                    }
                    List result = loanGenralFacade.accountDetail(accountNo);
                    if (result.isEmpty()) {
                        this.setMessage("Account No. does not exist.");
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
                        setMessage("Please select to date.");
                        return;
                    } else if (toDate.after(date)) {
                        setMessage(" To date can not be greater than current Date");
                        this.setToDate(date);
                        return;
                    } else if (fromDate.after(toDate)) {
                        setMessage("To date can not be greater than From Date");
                        return;
                    }
                    setDebitAcc(ddAccountNo(accountType));
                }
            } else if (interestOption.equals("A")) {
                accountNo = "";
                setDebitAcc(ddAccountNo(accountType));
                List dataList = ftsPostRemote.getUnAuthorizedTranList(accountType,getOrgnBrCode());
                if (!dataList.isEmpty()) {
                    this.setMessage("There are some pending authorization !");
                    return;
                }
            }
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
            double amount = 0d;
            NumberFormat formatter = new DecimalFormat("#0.00");
            List<LoanIntCalcList> resultList = sbIntCalcRemote.cbsSbIntCalc(interestOption, acctStatus, accountType, accountNo, sdf.format(fromDate), sdf.format(toDate), getOrgnBrCode());
            int i = 0;
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }
            for (LoanIntCalcList lict : resultList) {
                IntCalTable mt = new IntCalTable();
                mt.setsNo(Integer.toString(i + 1));
                mt.setAcNo(lict.getAcNo());

                mt.setCustName(lict.getCustName());
                mt.setFirstDt(lict.getFirstDt());
                mt.setLastDt(lict.getLastDt());

                double closingBal = lict.getClosingBal();
                double acctProduct = lict.getProduct();
                double acctInt = lict.getTotalInt();
                if (closingBal < 0.0) {
                    closingBal = -1 * closingBal;
                }
                if (acctProduct < 0.0) {
                    acctProduct = -1 * acctProduct;
                }
                if (acctInt < 0.0) {
                    acctInt = -1 * acctInt;
                }
                mt.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                mt.setRoi(new BigDecimal(formatter.format(lict.getRoi())));
                mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));

                amount = amount + acctInt;
                mt.setIntTableCode(lict.getIntTableCode());
                intCalc.add(mt);
                i++;
            }
            setCreditAcc("As per list");
            setTotalCredit(formatter.format(amount));
            setTotalDebit(formatter.format(amount));

            setDisablePost(false);
            setToDisable(true);


            String frmDate = sdf.format(fromDate);
            String toDt = sdf.format(toDate);

            String repName = "Interest Calculation";
            String report = "Interest Calculation Report";
            Vector vec = loanIntTestCalcRemote.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();

            fillParams.put("pReportName", repName);
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());

            fillParams.put("pFromDt", frmDate);
            fillParams.put("pToDt", toDt);
            fillParams.put("pBankName", vec.get(0).toString());
            fillParams.put("pBankAdd", vec.get(1).toString());
            new ReportBean().jasperReport("LoanIntCalcReport", "text/html", new JRBeanCollectionDataSource(intCalc), fillParams, report);
            this.setViewID("/report/ReportPagePopUp.jsp");
            setReportFlag(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void Post() {
        try {
            if (intCalc.isEmpty()) {
                this.setMessage("Please calculate the interest and then post.");
            }
            List<LoanIntCalcList> intDetailsList = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it;
            for (IntCalTable mt : intCalc) {
                it = new LoanIntCalcList();
                it.setAcNo(mt.getAcNo());
                it.setFirstDt(mt.getFirstDt());

                it.setLastDt(mt.getLastDt());
                it.setTotalInt(mt.getTotalInt().doubleValue());
                intDetailsList.add(it);
            }
            String result = sbIntCalcRemote.sbInterestPosting(intDetailsList, interestOption, acctStatus, accountType, sdf.format(fromDate),
                    sdf.format(toDate), debitAcc, getUserName(), getOrgnBrCode());
            setReportFlag(false);
            setMessage(result);
            setInterestOption("A");
            setAllAcc(true);
            setAccWise(false);

            setOldAccNo("");
            setAccountNo("");
            setAccountType("0");
            setDebitAcc("");
            setCreditAcc("");

            setTotalCredit("");
            setTotalDebit("");
            setToDisable(false);
            setFromDisable(false);

            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void panelmsgShow() {
        try {
            intCalc = new ArrayList<IntCalTable>();
            if (isAllAcc()) {
                this.setPanelMsg("Are you sure to calculate interest cor A/C. Type " + this.accountType);
            } else if (isAccWise()) {
                if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo.length() == 0) {
                    this.setMessage("Please enter the Account No.");
                }
                this.setPanelMsg("Are you sure to calculate interest for A/C. No." + accountNo);
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        try {
            setReportFlag(false);
            setMessage("");
            setInterestOption("A");
            setAllAcc(true);
            setAccWise(false);

            setOldAccNo("");
            setAccountNo("");
            setAccountType("0");
            setDebitAcc("");
            setCreditAcc("");

            setTotalCredit("");
            setTotalDebit("");
            setToDisable(false);
            setFromDisable(false);

            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();

        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        this.setMessage("");
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (this.acCloseFlag == null || this.acCloseFlag.equalsIgnoreCase("")) {
                refresh();
                return "case1";
            } else {
                this.acCloseFlag = "";
                refresh();
                ext.redirect(ext.getRequestContextPath() + "/faces/pages/admin/AccountClosingRegister.jsp?code=" + 1);
                return "case2";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
