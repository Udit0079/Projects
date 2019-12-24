/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.report.PostingReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ankit Verma
 */
public class SbIntCalIndividual extends BaseBean {

    private String message;
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
    private boolean postReportFlag;
    private List<LoanIntCalcList> intCalc;
    private String acCloseFlag = null;
    private boolean reportFlag;
    private String panelMsg;
    private final String sbIntCalcJndiName = "SbIntCalcFacade";
    private SbIntCalcFacadeRemote sbIntCalcRemote = null;
    //private final String LoanIntTestCalcJndiName = "LoanIntProductTestFacade";
    private CommonReportMethodsRemote common = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    LoanGenralFacadeRemote loanGenralFacade;
    Map<String, List<LoanIntCalcList>> resultMap;
    String oldAccNo, acNoLen;
    private String viewID = "/pages/master/sm/test.jsp";
    
    private String tmpAcNo="";

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isPostReportFlag() {
        return postReportFlag;
    }

    public void setPostReportFlag(boolean postReportFlag) {
        this.postReportFlag = postReportFlag;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
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

    public SbIntCalIndividual() {
        try {
            sbIntCalcRemote = (SbIntCalcFacadeRemote) ServiceLocator.getInstance().lookup(sbIntCalcJndiName);

            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);

            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            this.setAcNoLen(getAcNoLength());
            intCalc = new ArrayList<LoanIntCalcList>();
            setMessage("");
            setDisablePost(true);
            if (getHttpRequest().getParameter("code") == null || getHttpRequest().getParameter("code").toString().equals("")) {
                this.setAcCloseFlag("");
            } else {
                this.setOldAccNo(getHttpRequest().getParameter("code").toString());
                this.setAccountNo(getHttpRequest().getParameter("code").toString());
                this.setAcCloseFlag(getHttpRequest().getParameter("code").toString());
                acctStatus = "1";
            }
            setReportFlag(false);

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
        intCalc = new ArrayList<LoanIntCalcList>();
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
            
            String flag ="false";
            List resultList = sbIntCalcRemote.getAcctType();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    String acCode = ele.get(0).toString();
                    if(accountNo.substring(2,4).equalsIgnoreCase(acCode)){
                        flag = "true";
                        break;
                    }
                }
            }
            
            if(flag.equalsIgnoreCase("false")){
                this.setMessage("Account should be of Saving Nature");
                return;
            }
            
            String result = sbIntCalcRemote.acWiseFromDt(accountNo, getOrgnBrCode());
            this.setToDate(date);
            this.setFromDate(sdf.parse(sdf.format(ymd.parse(result))));
            setFromDisable(true);
            tmpAcNo = accountNo;
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
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

            if (oldAccNo.equalsIgnoreCase("") || oldAccNo == null) {
                setMessage("Please enter the account no.");
                return;
            } else if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please enter proper account no.");
                return;
            } else {
                accountType = ftsPostRemote.getAccountCode(accountNo);
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
                    ddAccountNo(accountType);
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
            List<LoanIntCalcList> resultList = sbIntCalcRemote.cbsSbIntCalc("I", acctStatus, accountType, accountNo, sdf.format(fromDate), sdf.format(toDate), getOrgnBrCode());
            int i = 0;
            if (resultList.isEmpty()) {
                throw new ApplicationException("No detail exists !");
            }

            for (LoanIntCalcList lict : resultList) {
                LoanIntCalcList mt = new LoanIntCalcList();
                //     mt.setsNo(Integer.toString(i + 1));
                mt.setAcNo(lict.getAcNo());

                mt.setCustName(lict.getCustName());
                mt.setFirstDt(sdf.format(ymd.parse(lict.getFirstDt())));
                mt.setLastDt(sdf.format(ymd.parse(lict.getLastDt())));

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
                mt.setClosingBal(Double.parseDouble(formatter.format(closingBal)));
                mt.setProduct(Double.parseDouble(formatter.format(acctProduct)));
                mt.setRoi(Double.parseDouble(formatter.format(lict.getRoi())));
                mt.setTotalInt(Double.parseDouble(formatter.format(acctInt)));

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
            List vec = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();

            fillParams.put("pReportName", repName);
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());

            fillParams.put("pFromDt", frmDate);
            fillParams.put("pToDt", toDt);
            fillParams.put("pBankName", vec.get(0).toString());
            fillParams.put("pBankAdd", vec.get(1).toString());
            new ReportBean().jasperReport("SbIntCalIndividual", "text/html", new JRBeanCollectionDataSource(intCalc), fillParams, report);
            this.setViewID("/report/ReportPagePopUp.jsp");
            setReportFlag(true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void Post() {
        try {
            System.out.println("in post ");
            if (intCalc.isEmpty()) {
                this.setMessage("Please calculate the interest and then post.");
            }

            String result = sbIntCalcRemote.sbInterestPosting(intCalc, "I", acctStatus, accountType, sdf.format(fromDate),
                    sdf.format(toDate), debitAcc, getUserName(), getOrgnBrCode());
            if (result.contains("Successfully")) {
                String[] trsno = result.split(":");
                List<PostingReportPojo> postingReport = common.getPostingReport(Float.parseFloat(trsno[1]), accountType, getUserName(), ymd.format(sdf.parse(getTodayDate())), getOrgnBrCode());
                if (!postingReport.isEmpty()) {
                    String report = "SB Interest Posting Report";
                    postReportFlag = true;
                    Map fillParams = new HashMap();
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
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
            setReportFlag(false);
            setMessage(result);
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
            if (intCalc != null) {
                intCalc.clear();
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
            if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo.length() == 0) {
                this.setMessage("Please enter the Account No.");
                return;
            } else {
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
            setOldAccNo("");
            setAccountNo("");
            setAccountType("0");
            setDebitAcc("");
            setCreditAcc("");

            setTotalCredit("");
            setTotalDebit("");
            setToDisable(false);
            setFromDisable(false);
            setPostReportFlag(false);
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            if (intCalc != null) {
                intCalc.clear();
            }

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
                String url = "/faces/pages/admin/AccountClosingRegister.jsp?code=" + tmpAcNo;
                tmpAcNo = "";
                refresh();
                ext.redirect(ext.getRequestContextPath() + url);
                return "case2";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
