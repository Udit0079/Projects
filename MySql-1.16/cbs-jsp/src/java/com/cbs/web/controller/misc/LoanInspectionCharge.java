/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.ChargesObject;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.MinBalanceChargesFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.misc.LoanInspectionTable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class LoanInspectionCharge extends BaseBean {

    MinBalanceChargesFacadeRemote remoteObject;
    private String acctType;
    private String myDate;
    private boolean bFlag;
    private String message;
    private double debitAmount;
    private double creditAmount;
    private String glAccountNo;
    private boolean postDisable;
    private boolean calDisable;
    private String accoutToDebited;
    private String crAccount;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    private List<SelectItem> accountype;
    private List<SelectItem> comboStatus;
    private String status;
    private List<LoanInspectionTable> minBalance;
    private boolean amountDisable;
    private boolean glDisable;
    private String yearend;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getComboStatus() {
        return comboStatus;
    }

    public void setComboStatus(List<SelectItem> comboStatus) {
        this.comboStatus = comboStatus;
    }

    public boolean isGlDisable() {
        return glDisable;
    }

    public void setGlDisable(boolean glDisable) {
        this.glDisable = glDisable;
    }

    public boolean isAmountDisable() {
        return amountDisable;
    }

    public void setAmountDisable(boolean amountDisable) {
        this.amountDisable = amountDisable;
    }

    public String getAccoutToDebited() {
        return accoutToDebited;
    }

    public void setAccoutToDebited(String accoutToDebited) {
        this.accoutToDebited = accoutToDebited;
    }

    public boolean isCalDisable() {
        return calDisable;
    }

    public void setCalDisable(boolean calDisable) {
        this.calDisable = calDisable;
    }

    public boolean isPostDisable() {
        return postDisable;
    }

    public void setPostDisable(boolean postDisable) {
        this.postDisable = postDisable;
    }

    public String getGlAccountNo() {
        return glAccountNo;
    }

    public void setGlAccountNo(String glAccountNo) {
        this.glAccountNo = glAccountNo;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public List<LoanInspectionTable> getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(List<LoanInspectionTable> minBalance) {
        this.minBalance = minBalance;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

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

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

    public String getMyDate() {
        return myDate;
    }

    public void setMyDate(String myDate) {
        this.myDate = myDate;
    }

    public List<SelectItem> getAccountype() {
        return accountype;
    }

    public void setAccountype(List<SelectItem> accountype) {
        this.accountype = accountype;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    /**
     * Creates a new instance of MinBalanceCharges
     */
    public LoanInspectionCharge() {
        try {
            remoteObject = (MinBalanceChargesFacadeRemote) ServiceLocator.getInstance().lookup("MinBalanceChargesFacade");
            setDate();
            setCalDisable(false);
            setPostDisable(true);
            accountTypeDropDown();
            setAmountDisable(true);
            setGlDisable(true);
            setAccoutToDebited("As per List In The Report");
            minBalance = new ArrayList<LoanInspectionTable>();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    /**
     * ********************* Functionality Of Dropdown Account Type
     * ***********************
     */
    public void accountTypeDropDown() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.getAccountType();
            comboStatus = new ArrayList<SelectItem>();
            comboStatus.add(new SelectItem("Loan A/c"));
            if (!resultList.isEmpty()) {
                accountype = new ArrayList<SelectItem>();
                accountype.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        accountype.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                accountype = new ArrayList<SelectItem>();
                accountype.add(new SelectItem("--Select--"));
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    /**
     * ************* Functionaity Of setDate To Setting The Date In Calender
     * **************
     */
    public void setDate() throws ParseException, java.text.ParseException {
        yearEnd();
        String dt = 01 + "/" + 04 + "/" + yearend;
        setFromDate(sdf.parse(dt));
    }

    public void fromdt() throws java.text.ParseException {
        int dat = Integer.parseInt(yearend) + 1;
        String yearend1 = Integer.toString(dat);
        String dt = 31 + "/" + 03 + "/" + yearend1;
        setToDate(sdf.parse(dt));

    }

    /**
     * **************** END *********************
     */
    /**
     * *************** Functionaity To Set The Value In TxtA/c To Be Credited
     * *******************
     */
    public void accountToCredit() {
        setMessage("");
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.setAcToCreditInspection(acctType);
            if (resultList.size() > 0) {
                Vector ele = (Vector) resultList.get(0);
                crAccount = ele.get(0).toString();
                setGlAccountNo(crAccount);
                setDebitAmount(0);
                setCreditAmount(0);
                setAccoutToDebited("");
                minBalance.clear();
                setCalDisable(false);
                setPostDisable(true);
            } else {
                setDebitAmount(0);
                setCreditAmount(0);
                setAccoutToDebited("");
                setGlAccountNo("");
                setMessage("");
                minBalance.clear();
                setPostDisable(true);
                setCalDisable(true);
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void postBtnAction() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher insNo = p.matcher(glAccountNo);
            if (glAccountNo.equals("") || glAccountNo == null) {
                this.setMessage("Please Enter the A/c To Be Credited");
                return;
            }
            if (fromDate == null || fromDate.equals("")) {
                this.setMessage("Please fill the From Date");
                return;
            }
            if (toDate == null || toDate.equals("")) {
                this.setMessage("Please fill the To Date");
                return;
            }
            String currentDate;
            Date date = new Date();
            currentDate = ymd.format(date);
            String result = remoteObject.loanInspectionChargesPost(acctType, glAccountNo, ymd.format(fromDate),
                    ymd.format(toDate), getUserName(), getOrgnBrCode(), currentDate);
            this.setMessage(result);
            setPostDisable(true);
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void calculateBtnAction() {
        if (acctType.equals("--Select--")) {
            this.setMessage("Please Select the value from  DropDown Account Type.");
            return;
        }
        this.setMessage("");
        if (fromDate == null || fromDate.equals("")) {
            this.setMessage("Please fill the From Date");
            return;
        }
        if (toDate == null || toDate.equals("")) {
            this.setMessage("Please fill the To Date");
            return;
        }
        try {
            String currentDate;
            Date date = new Date();
            currentDate = ymd.format(date);
            double total = 0f;
            List<ChargesObject> resultList = remoteObject.loanInspectionChargesCalculate(acctType, ymd.format(fromDate), ymd.format(toDate), getOrgnBrCode(), getUserName(), currentDate);
            if (resultList.size() > 0 && resultList.get(0).getMsg().equals("TRUE")) {
                int i = 0;
                for (ChargesObject chargeItem : resultList) {
                    LoanInspectionTable rd = new LoanInspectionTable();
                    rd.setsNo(i++);
                    rd.setAccountNo(chargeItem.getAcNo());
                    rd.setCustName(chargeItem.getCustName());
                    rd.setTransaction(String.valueOf(chargeItem.getTrans()));
                    rd.setLimit(String.valueOf(chargeItem.getLimit()));
                    rd.setCharges(chargeItem.getPenalty());
                    total = total + chargeItem.getPenalty();
                    minBalance.add(rd);
                }
                setDebitAmount(total);
                setCreditAmount(total);
                setGlAccountNo(crAccount);
                setPostDisable(false);
                setCalDisable(true);
                glDisable = false;
            } else {
                setPostDisable(true);
                setCalDisable(true);
                this.setMessage(resultList.get(0).getMsg());
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    /**
     * **************** END *********************
     */
    /**
     * ************* Functionaity Of Blank the Value **************
     */
    public void resetValue() {

        setDebitAmount(0);
        setCreditAmount(0);
        setAccoutToDebited("");
        setGlAccountNo("");
        setMessage("");
        minBalance = new ArrayList<LoanInspectionTable>();
        setAcctType("--Select--");
        glDisable = true;

    }

    public void yearEnd() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.yearEnd(getOrgnBrCode());
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    yearend = ele.get(0).toString();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    /**
     * **************** END *********************
     */
    public String btnExit() {
        resetValue();
        return "case1";
    }
}
