/*
 * CREATED BY       :    ROHIT KRISHNA GUPTA
 */
package com.cbs.web.controller.txn;

import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.txn.ExtensionCounterTransactionGrid;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class ExtensionCounterTransaction {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String acctNo;
    private String acNo;
    private String acName;
    private String balance;
    private String acctType;
    private String agentCode = "01";
    private String errorMessage;
    private String message;
    private String amount;
    private String tranType;
    private String by;
    private String subAcNo;
    private String sumCrAmt;
    private String sumDrAmt;
    private String reciept;
    private String payment;
    private int currentRow;
    public String extCon;
    private HttpServletRequest req;
    private List<SelectItem> acctNoOption;
    private List<SelectItem> extensionCon;
    private List<SelectItem> accountType;
    private List<SelectItem> tranTypeOption;
    private List<SelectItem> byOption;
    private List<ExtensionCounterTransactionGrid> transactionDetail;
    private ExtensionCounterTransactionGrid currentItem = new ExtensionCounterTransactionGrid();
    private final String jndiHomeNameTxn = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeNameFts = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    String oldAccNo;
    String code;

    public List<SelectItem> getAcctNoOption() {
        return acctNoOption;
    }

    public void setAcctNoOption(List<SelectItem> acctNoOption) {
        this.acctNoOption = acctNoOption;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public List<SelectItem> getAccountType() {
        return accountType;
    }

    public void setAccountType(List<SelectItem> accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getByOption() {
        return byOption;
    }

    public void setByOption(List<SelectItem> byOption) {
        this.byOption = byOption;
    }

    public List<SelectItem> getTranTypeOption() {
        return tranTypeOption;
    }

    public void setTranTypeOption(List<SelectItem> tranTypeOption) {
        this.tranTypeOption = tranTypeOption;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public List<ExtensionCounterTransactionGrid> getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(List<ExtensionCounterTransactionGrid> transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    public String getSubAcNo() {
        return subAcNo;
    }

    public void setSubAcNo(String subAcNo) {
        this.subAcNo = subAcNo;
    }

    public String getSumCrAmt() {
        return sumCrAmt;
    }

    public void setSumCrAmt(String sumCrAmt) {
        this.sumCrAmt = sumCrAmt;
    }

    public String getSumDrAmt() {
        return sumDrAmt;
    }

    public void setSumDrAmt(String sumDrAmt) {
        this.sumDrAmt = sumDrAmt;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getReciept() {
        return reciept;
    }

    public void setReciept(String reciept) {
        this.reciept = reciept;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public ExtensionCounterTransactionGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ExtensionCounterTransactionGrid currentItem) {
        this.currentItem = currentItem;
    }

    public String getExtCon() {
        return extCon;
    }

    public void setExtCon(String extCon) {
        this.extCon = extCon;
    }

    public List<SelectItem> getExtensionCon() {
        return extensionCon;
    }

    public void setExtensionCon(List<SelectItem> extensionCon) {
        this.extensionCon = extensionCon;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of ExtensionCounterTransaction */
    public ExtensionCounterTransaction() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            //setUserName(req.getRemoteUser());
            setUserName("nishant");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFts);
            getDropDownList();
            getExtensionCounter();
            this.setErrorMessage("");
            this.setMessage("");
            accountType = new ArrayList<SelectItem>();
            accountType.add(new SelectItem("--Select--"));
            accountType.add(new SelectItem("GL"));
            tranTypeOption = new ArrayList<SelectItem>();
            tranTypeOption.add(new SelectItem("--Select--"));
            tranTypeOption.add(new SelectItem("Cr"));
            tranTypeOption.add(new SelectItem("Dr"));
            byOption = new ArrayList<SelectItem>();
            byOption.add(new SelectItem("--Select--"));
            byOption.add(new SelectItem("Voucher"));
            transactionDetail = new ArrayList<ExtensionCounterTransactionGrid>();

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getDropDownList() {
        try {
            List resultList = new ArrayList();
            resultList = txnRemote.getDropdownList(this.orgnBrCode);
            acctNoOption = new ArrayList<SelectItem>();
            acctNoOption.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acctNoOption.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    //List extCounter = new ArrayList();
    public void getExtensionCounter() {
        try {
            List extCounter = new ArrayList();
            extCounter = txnRemote.extensionCounterDropDown();
            extensionCon = new ArrayList<SelectItem>();
            if (!extCounter.isEmpty()) {
                for (int i = 0; i < extCounter.size(); i++) {
                    Vector ele = (Vector) extCounter.get(i);
                    for (Object ee : ele) {
                        extensionCon.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                this.setErrorMessage("No Extension Counter Exists !!!");
                extensionCon = new ArrayList<SelectItem>();
                extensionCon.add(new SelectItem("--Select--"));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setAcctNo("");
            this.setAmount("");
            this.setBalance("");
            this.setAcName("");
            this.setAcNo("");
            this.setAmount("");
            this.setBalance("");
            this.setSumCrAmt("0.00");
            this.setSumDrAmt("0.00");
            this.setReciept("0.00");
            this.setPayment("0.00");
            this.setAcctType("--Select--");
            this.setTranType("--Select--");
            this.setBy("--Select--");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    List acctInfo = new ArrayList();
    List balanceInfo = new ArrayList();

    public void getAccountDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            //String acType = this.acctType;
            //String code = this.acctNo;
            //String agCode = this.agentCode;
//            int length = code.length();
//            int addedZero = 6 - length;
//            for (int i = 1; i <= addedZero; i++) {
//                code = "0" + code;
//            }
            //acctNo = orgnBrCode + acType + code + agCode;
            if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo == null || this.oldAccNo.equalsIgnoreCase("null")) {
                this.setErrorMessage("Please Enter 12 Digit Account Number.");
                return;
            }
            //if (this.accNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && this.oldAccNo.length() < 12) {
                this.setErrorMessage("Please Enter 12 Digit Account Number.");
                return;
            }
            
            acctNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
            
            code = ftsPostRemote.getAccountNature(acctNo);
            if (!code.equals(CbsConstant.PAY_ORDER)) {
                this.setErrorMessage("Account nature is invalid.");
                return;
            }
            subAcNo = acctNo.substring(2);
            if ((subAcNo.equals(code + "00080001")) || (subAcNo.equals(code + "00090001") || (subAcNo.equals(code + "00020001")))) {
                this.setErrorMessage("Sorry, Entry Prohibited For This Account.");
                resetForm();
                return;
            }

            acctInfo = txnRemote.accountDetail(acctNo);
            if (!acctInfo.isEmpty()) {
                for (int i = 0; i < acctInfo.size(); i++) {
                    Vector ele = (Vector) acctInfo.get(i);
                    this.setAcName(ele.get(0).toString());
                    this.setAcNo(ele.get(1).toString());
                    String postFlag = ele.get(2).toString();
                    if (postFlag.equalsIgnoreCase("99")) {
                        this.setErrorMessage("Entry For This Account No. Is Not Allowed.");
                        return;
                    }
                }
            } else {
                this.setMessage("");
                this.setErrorMessage("Account No. Does Not Exist.");
                resetForm();
                return;
            }
            balanceInfo = txnRemote.getBalance(acctNo);
            if (!balanceInfo.isEmpty()) {
                Vector balList = (Vector) balanceInfo.get(0);
                //this.setBalance(balList.get(0).toString());
                this.setBalance(formatter.format(Double.parseDouble(balList.get(0).toString())));
            } else {
                this.setBalance("0.00");
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    double tmpCrAmt;
    double tmpDrAmt;

    public void loadGrid() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.acNo.equals("") || this.acNo.length() == 0) {
                this.setErrorMessage("Account No. Should Not Be Blank.");
                return;
            }
            if (this.amount.equals("") || this.amount.length() == 0) {
                this.setErrorMessage("Please Enter Amount.");
                return;
            }
            String amt = this.amount;
            boolean blnNumeric = false;
            boolean blnAlpha = false;
            boolean blnSpecialChar = false;
            char chr[] = null;
            if (amt != null) {
                chr = amt.toCharArray();
            }
            for (int i = 0; i < chr.length; i++) {
                if (chr[i] >= '0' && chr[i] <= '9') {
                    blnNumeric = true;
                    break;
                }
            }
            for (int i = 0; i < chr.length; i++) {
                if ((chr[i] >= 'A' && chr[i] <= 'Z') || (chr[i] >= 'a' && chr[i] <= 'z')) {
                    blnAlpha = true;
                    break;
                }
            }

            for (int i = 0; i < chr.length; i++) {
                if ((chr[i] == '/' || chr[i] == '*' || chr[i] == '-'
                        || chr[i] == '+' || chr[i] == '_' || chr[i] == '='
                        || chr[i] == '!' || chr[i] == '@' || chr[i] == '#'
                        || chr[i] == '$' || chr[i] == '%' || chr[i] == '^'
                        || chr[i] == '&' || chr[i] == '(' || chr[i] == ')')) {
                    blnSpecialChar = true;
                    break;
                }
            }
            if (blnSpecialChar == true) {
                this.setErrorMessage("Please Enter Valid Amount(Numbers Only).");
                return;
            }
            if (blnAlpha == true) {
                this.setErrorMessage("Please Enter Valid Amount(Numbers Only).");
                return;
            }
            if (!blnNumeric == true) {
                this.setErrorMessage("Please Enter Valid Amount(Numbers Only).");
                return;
            }
            if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Amount(Numbers Only).");
                    return;
                } else if (this.amount.substring(amount.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Amount Upto Two Decimal Places.");
                    return;
                }
            }
            if (this.extCon.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Extension Counter.");
                return;
            }
            ExtensionCounterTransactionGrid txnBean = createTxnBeanObj();
            transactionDetail.add(txnBean);
            if (this.tranType.equalsIgnoreCase("Cr")) {
                 if (this.subAcNo.equals(code + SiplConstant.GL_CSH_OTH + "01")) {
                    this.reciept = formatter.format(Double.parseDouble(this.amount));
                } else {
                    tmpCrAmt = 0.0d;
                    tmpDrAmt = 0.0d;
                    for (ExtensionCounterTransactionGrid tmpBean : transactionDetail) {
                        if (!tmpBean.getAcNo().substring(2).equals(code + SiplConstant.GL_CSH_OTH + "01")) {
                            if (tmpBean.getCrAmount() == null) {
                                tmpBean.setCrAmount("0");
                            }
                            if (tmpBean.getDrAmount() == null) {
                                tmpBean.setDrAmount("0");
                            }
                            tmpCrAmt = tmpCrAmt + Double.parseDouble(tmpBean.getCrAmount());
                            tmpDrAmt = tmpDrAmt + Double.parseDouble(tmpBean.getDrAmount());
                            this.setSumCrAmt(formatter.format(tmpCrAmt));
                            this.setSumDrAmt(formatter.format(tmpDrAmt));
                        }
                    }
                }

            } else if (this.tranType.equalsIgnoreCase("Dr")) {
                if (this.subAcNo.equals(code + SiplConstant.GL_CSH_OTH+ "01")) {
                    this.payment = formatter.format(Double.parseDouble(this.amount));
                } else {
                    tmpCrAmt = 0.0d;
                    tmpDrAmt = 0.0d;
                    for (ExtensionCounterTransactionGrid tmpBean : transactionDetail) {
                        if (!tmpBean.getAcNo().substring(2).equals(code + SiplConstant.GL_CSH_OTH + "01")) {
                            if (tmpBean.getCrAmount() == null) {
                                tmpBean.setCrAmount("0");
                            }
                            if (tmpBean.getDrAmount() == null) {
                                tmpBean.setDrAmount("0");
                            }
                            tmpCrAmt = tmpCrAmt + Double.parseDouble(tmpBean.getCrAmount());
                            tmpDrAmt = tmpDrAmt + Double.parseDouble(tmpBean.getDrAmount());
                            this.setSumCrAmt(formatter.format(tmpCrAmt));
                            this.setSumDrAmt(formatter.format(tmpDrAmt));
                        }
                    }
                }
            }
            this.setAcName("");
            this.setAcNo("");
            this.setBalance("");
            this.setAmount("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    private ExtensionCounterTransactionGrid createTxnBeanObj() {
        ExtensionCounterTransactionGrid txnBean = new ExtensionCounterTransactionGrid();
        try {
            txnBean.setAcNo(acNo);
            if (this.tranType.equalsIgnoreCase("Cr")) {
                txnBean.setCrAmount(formatter.format(Double.parseDouble(this.amount)));
            }
            if (this.tranType.equalsIgnoreCase("Dr")) {
                txnBean.setDrAmount(formatter.format(Double.parseDouble(this.amount)));
            }
            txnBean.setAcctName(acName);
            if (this.tranType.equalsIgnoreCase("Cr")) {
                txnBean.setTy("0");
            } else if (this.tranType.equalsIgnoreCase("Dr")) {
                txnBean.setTy("1");
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return txnBean;
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (ExtensionCounterTransactionGrid item : transactionDetail) {
            if (item.getAcNo().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void delete() {
        try {
            transactionDetail.remove(currentRow);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    List arraylist = new ArrayList();

    public void saveTxnDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.extCon.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Extension Counter.");
                return;
            }
            String a[][] = new String[transactionDetail.size()][5];
            for (int i = 0; i < transactionDetail.size(); i++) {
                a[i][0] = transactionDetail.get(i).getAcNo().toString();
                a[i][1] = String.valueOf(transactionDetail.get(i).getCrAmount());
                a[i][2] = String.valueOf(transactionDetail.get(i).getDrAmount());
                a[i][3] = transactionDetail.get(i).getAcctName().toString();
                a[i][4] = transactionDetail.get(i).getTy().toString();
            }
            for (int i = 0; i < transactionDetail.size(); i++) {
                for (int j = 0; j < 5; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
            if (arraylist.isEmpty() || arraylist.toString().equalsIgnoreCase("[]")) {
                this.setErrorMessage("No Transaction Detail Exists To Save.");
                return;
            }

            String result = txnRemote.saveExtTxn(arraylist, this.todayDate, this.userName, this.extCon, this.orgnBrCode);
            if (result.equals("true")) {
                this.setMessage("Data Saved Succesfully.");
                transactionDetail = new ArrayList<ExtensionCounterTransactionGrid>();
                arraylist = new ArrayList();
                resetForm();
            } else {
                this.setErrorMessage(result);
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void cancelTransaction() {
        try {
            this.setAcName("");
            this.setAcNo("");
            this.setAmount("");
            this.setBalance("");
            this.setErrorMessage("");
            this.setMessage("");
            this.setSumCrAmt("0.00");
            this.setSumDrAmt("0.00");
            this.setReciept("0.00");
            this.setPayment("0.00");
            transactionDetail = new ArrayList<ExtensionCounterTransactionGrid>();
            arraylist = new ArrayList();
            this.setAcctType("--Select--");
            this.setAcctNo("");
            this.setTranType("--Select--");
            this.setBy("--Select--");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        try {
            cancelTransaction();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
