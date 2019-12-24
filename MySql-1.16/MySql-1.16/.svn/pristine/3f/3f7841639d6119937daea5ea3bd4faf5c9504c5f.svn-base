package com.cbs.web.controller.td;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class NewTdRecieptCreation extends BaseBean {

    public NewTdRecieptCreation() {
        try {
            setMessage("");
            tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            reportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            this.setAcNoLen(getAcNoLength());
            onLoad();
            acctCrdVisible = true;
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onLoad() {
        setIntOptionList(new ArrayList<SelectItem>());
        intOptionList.add(new SelectItem("0", "--SELECT--"));

        functionList = new ArrayList<SelectItem>();
        functionList.add(new SelectItem("C", "Create"));
        functionList.add(new SelectItem("M", "Modify"));
        functionList.add(new SelectItem("D", "Delete"));
        functionList.add(new SelectItem("V", "Verify"));

        setAutoRenewList(new ArrayList<SelectItem>());
        autoRenewList.add(new SelectItem("Y", "Yes"));
        autoRenewList.add(new SelectItem("N", "No"));

        setAutoPayList(new ArrayList<SelectItem>());
        autoPayList.add(new SelectItem("N", "No"));
        autoPayList.add(new SelectItem("Y", "Yes"));

        bookSeriesList = new ArrayList<SelectItem>();
        bookSeriesList.add(new SelectItem("0", ""));
        setReceipt();
        setfdGlobal();
        setAutoValues();
    }

    public void setAutoValues() {
        try {
            autoRenew = "N";
            autoPay = "N";
            Integer activeCode = ftsPostRemote.getCodeForReportName("TD-RENEWAL");
            if (activeCode == 1) {
                autoRenewVar = "";
                autoRenew = "Y";
            }
            activeCode = ftsPostRemote.getCodeForReportName("TD-PAYMENT");
            if (activeCode == 1) {
                autoPaymentVar = "";
                autoPay = "Y";
            }
            backDateReceipt = ftsPostRemote.getCodeForReportName("BACK-DATE-RECEIPT");
            setIntLabel("Interest Amount");
            setInterest("0.00");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void changeFunction() {
        if (function.equals("C")) {
            setInputDisFlag("none");
            setFocusId("txtAccountNo");
            setBtnLabel("Save");
            setModifyFlag(false);
            setVerifyFlag(false);
        } else if (function.equals("V")) {
            setInputDisFlag("");
            setBtnLabel("Verify");
            setOldAcNo("");
            getUnAuthAcNo();
            setFocusId("ddPAcNo");
            setModifyFlag(true);
            setVerifyFlag(true);
        } else if (function.equals("M")) {
            setInputDisFlag("");
            setBtnLabel("Update");
            setOldAcNo("");
            setFocusId("ddPAcNo");
            getUnAuthAcNo();
            setModifyFlag(true);
            setVerifyFlag(false);
        } else {
            setInputDisFlag("");
            setBtnLabel("Delete");
            setOldAcNo("");
            getUnAuthAcNo();
            setFocusId("ddPAcNo");
            setModifyFlag(true);
            setVerifyFlag(true);
        }
    }

    public void getUnAuthAcNo() {
        try {
            this.setMessage("");
            pendingAcNoList = new ArrayList<SelectItem>();
            List dataList = tdRcptMgmtRemote.getUnAuthAcNo(getOrgnBrCode(), ymd.format(sdf.parse(getTodayDate())));
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                pendingAcNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getUnAuthTxnId() {
        try {
            this.setMessage("");
            pendingRtNoList = new ArrayList<SelectItem>();
            this.setOldAcNo(getPendingAcNo());
            List dataList = tdRcptMgmtRemote.getUnAuthTxnId(getPendingAcNo(), ymd.format(sdf.parse(getTodayDate())));
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                pendingRtNoList.add(new SelectItem(ele.get(0).toString()));
            }
            setFocusId("ddPRtNo");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getRtDetails() {
        try {
            this.paidAcnoName = "";
            setOldAcNo(pendingAcNo);
            setAcctNo(pendingAcNo);
            getCustomerFdInfo();
            List receiptList = tdRcptMgmtRemote.getRtDetails(pendingAcNo, Float.parseFloat(getPendingRtNo()));

            Vector ele = (Vector) receiptList.get(0);
            setAmount(formatter.format(Double.parseDouble(ele.get(0).toString())));
            setPrincipalAmt(formatter.format(Double.parseDouble(ele.get(0).toString())));

            setTdDate(sdf.parse(ele.get(1).toString()));
            setIntOption(ele.get(2).toString());
            setAcToCredit(ele.get(3).toString());
            setCrediterAccount(ele.get(3) == null ? "" : ele.get(3).toString());
            setAutoRenew(ele.get(4).toString());

            setYear(ele.get(5).toString());
            setMonths(ele.get(6).toString());
            setDays(ele.get(7).toString());

            setRateOfInterest(ele.get(8).toString());
            setMatDate(ele.get(9).toString());
            setBookSeries(ele.get(10).toString());
            enteredBy = ele.get(11).toString();
            period = getYear() + "Years" + getMonths() + "Months" + getDays() + "Days";
            setMatAmount();
//            setFocusId("ddInterestOption");
            setFocusId("txtAmount");
            //Addition on 02/12/2015
            setAutoPay(ele.get(12).toString());
            setPaidAcno(ele.get(13).toString());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void getCustomerFdInfo() {
        try {
            if (oldAcNo == null || oldAcNo.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Enter the Account No.");
            }

            if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                throw new ApplicationException("Please Enter the Proper Account No.");
            }

            acctNo = ftsPostRemote.getNewAccountNumber(oldAcNo);
            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(acctNo);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                throw new ApplicationException("Sorry! Other branch account no. is not allowed to proceed.");
            }
            setMessage("");

            String productCode = tdRcptMgmtRemote.getProductCode(acctNo);
            Map<String, String> intOptMap = CbsUtil.getIntOption(productCode);
            intOptionList = new ArrayList<SelectItem>();
            intOptionList.add(new SelectItem("0", "--SELECT--"));
            for (Map.Entry<String, String> entry : intOptMap.entrySet()) {
                intOptionList.add(new SelectItem(entry.getKey(), entry.getValue()));
            }
            List resultList = tdRcptMgmtRemote.getCustFdInfo(acctNo);
            if (resultList.isEmpty()) {
                throw new ApplicationException("Account Detail does not exist");
            }

            Vector acVect = (Vector) resultList.get(0);
            setName(acVect.get(1).toString());
            setJtName(acVect.get(4).toString());

            setBalance(formatter.format(Double.parseDouble(acVect.get(6).toString())));

            String dsgTypeDesc = reportMethodsRemote.getRefRecDesc("235", acVect.get(2).toString());
            setCustNature(dsgTypeDesc);
            setCustNaturePass(acVect.get(2).toString());
//            if (acVect.get(2).toString().equalsIgnoreCase("SC")) {
//                setCustNature("Senior Citizen");
//                setCustNaturePass("SC");
//            } else if (acVect.get(2).toString().equalsIgnoreCase("ST")) {
//                setCustNature("Staff");
//                setCustNaturePass("ST");
//            } else {
//                setCustNature("Others");
//                setCustNaturePass("OT");
//            }            
            if (acVect.get(3).toString().equalsIgnoreCase("N")) {
                setTds("Not Applicable");
            } else if (acVect.get(3).toString().equalsIgnoreCase("Y")) {
                setTds("Applicable");
            } else {
                setTds("Compulsory");
            }
            setAcctNature();
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getAcctToBeCredited() {
        try {
            setMessage("");
            if (acToCredit == null || acToCredit.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Enter the Account No. To Be Credited");
            }
            //if (acToCredit.length() < 12) {
            if (!this.acToCredit.equalsIgnoreCase("") && ((this.acToCredit.length() != 12) && (this.acToCredit.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                throw new ApplicationException("Please enter proper account no.");
            }
            crediterAccount = ftsPostRemote.getNewAccountNumber(acToCredit);
            String accNat = ftsPostRemote.getAccountNature(crediterAccount);

            List resultList = tdRcptMgmtRemote.cbsIntroInfo(crediterAccount, accNat);
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            Vector acctVect = (Vector) resultList.get(0);
            setCrediterAccount(acctVect.get(0).toString());

        } catch (ApplicationException ex) {
            setCrediterAccount("");
            setAcToCredit("");
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            setCrediterAccount("");
            setAcToCredit("");
            this.setMessage(ex.getMessage());
        }
    }

    public void setPrincAmount() {
        if (getSaveFlag().equalsIgnoreCase("true")) {
            setMessage("Please Fill TD Condition From TD Condition Master Form Before New Receipt Creation");
            return;
        }
        this.setMessage("");
        setPrincipalAmt("0.00");
        if (this.amount.equalsIgnoreCase("") || this.amount.length() == 0) {
            setMessage("Amount Cannot Be Blank or Zero.");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher amountCheck = p.matcher(amount);
        if (!amountCheck.matches()) {
            setMessage("Please Enter Valid Amount.");
            return;
        }
        if (!amount.matches("[0-9.]*")) {
            setMessage("Please Enter Valid Amount.");
            return;
        }
        if (this.amount.contains(".")) {
            if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                this.setMessage("Invalid Amount.Please Fill The Amount Correctly.");
                return;
            }
        }
        if (Double.parseDouble(amount) < 0) {
            setMessage("Amount Cannot Be Blank/Zero/less than Zero.");
            return;
        }
        if (Double.parseDouble(amount) > Double.parseDouble(tdAmount)) {
            setMessage("Please note TD amount is over limit of Rs. " + tdAmount + " Please take care of PAN No., Form 60, etc.");
            return;
        }
        if (amount != null || !amount.equalsIgnoreCase("")) {
            if (Float.parseFloat(amount) < 0) {
                setPrincipalAmt("0.00");
            } else {
                setPrincipalAmt(formatter.format(Double.parseDouble(amount)));
            }
        }
    }

    public void setMatDate() {
        setMatDate("");
        setMessage("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (validationPeriod().equalsIgnoreCase("false")) {
            return;
        }
        if (amount == null || amount.equalsIgnoreCase("") || amount.length() == 0) {
            setMessage("Amount Cannot Be Blank or Zero.");
            return;
        }
        if (balance == null || balance.equalsIgnoreCase("") || balance.length() == 0) {
            setMessage("Balance Cannot Be Blank or Zero. Please Enter the Account No.");
            return;
        } else if (amount != null && balance != null) {
            Matcher amountCheck = p.matcher(amount);
            if (!amountCheck.matches()) {
                setMessage("Please Enter Valid Amount.");
                return;
            } else if (!amount.matches("[0-9.]*")) {
                setMessage("Please Enter Valid Amount.");
                return;
            } else if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    this.setMessage("Invalid Amount.Please Fill The Amount Correctly.");
                    return;
                }
            } else if (Float.parseFloat(amount) < 0) {
                setMessage("Amount Cannot Be Blank/Zero/less than Zero.");
                return;
            }
            if (!balance.matches("[0-9.]*")) {
                setMessage("Please Enter Valid Balance.");
                return;
            } else if (this.balance.contains(".")) {
                if (this.balance.indexOf(".") != this.balance.lastIndexOf(".")) {
                    this.setMessage("Invalid Balance.Please Fill The Balance Correctly.");
                    return;
                }
            } else if (Double.parseDouble(balance) < 0) {
                setMessage("Balance Cannot Be Blank or Zero.");
                return;
            } else {
                if (Double.parseDouble(amount) > Double.parseDouble(balance)) {
                    setMessage("TD Amount Should Be less Than Balance.");
                    return;
                }
            }
        } else {
            return;
        }
        if (custNaturePass == null || custNaturePass.equalsIgnoreCase("")) {
            setMessage("Please set the Customer Nature.");
            return;
        }
        if (intOption.equalsIgnoreCase("0")) {
            setMessage("Please Select the Interest Option");
            setMatDate("");
            setRateOfInterest("");
            setMatAmount("0.00");
            return;
        }
        if ((year == null || year.equalsIgnoreCase("0") || year.equalsIgnoreCase("")) && (months == null
                || months.equalsIgnoreCase("0") || months.equalsIgnoreCase("")) && (days != null
                || !days.equalsIgnoreCase("0") || !days.equalsIgnoreCase(""))) {
            if (intOption.equalsIgnoreCase("M") || intOption.equalsIgnoreCase("Q") || intOption.equalsIgnoreCase("S")) {
                if (Integer.parseInt(days) < Integer.parseInt(tdDayMth)) {
                    setMessage("Please note duration cannot be less than " + tdDayMth + ". Please re-enter period");
                    setMatDate("");
                    setRateOfInterest("");
                    setMatAmount("0.00");
                    return;
                }
            }
            if (intOption.equalsIgnoreCase("C")) {
                if (Integer.parseInt(days) < Integer.parseInt(tdDayCum)) {
                    setMessage("Please note duration cannot be less than " + tdDayCum + ". Please re-enter period");
                    setMatDate("");
                    setRateOfInterest("");
                    setMatAmount("0.00");
                    return;
                }
            }
        }
        try {
            String newDt = CbsUtil.yearAdd(ymd.format(tdDate), Integer.parseInt(year));
            newDt = CbsUtil.monthAdd(newDt, Integer.parseInt(months));
            newDt = CbsUtil.dateAdd(newDt, Integer.parseInt(days));
            setMatDate(sdf.format(ymd.parse(newDt)));
            setroi();
            setMessage("");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setMatAmount() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (rateOfInterest == null || rateOfInterest.equalsIgnoreCase("") || rateOfInterest.equalsIgnoreCase("0.0")) {
            setMessage("ROI Cannot Be Blank/Zero/less than zero.");
            return;
        } else {
            Matcher roiCheck = p.matcher(rateOfInterest);
            if (!roiCheck.matches()) {
                setMessage("Pleae Enter Valid Rate Of Interest.");
                return;
            }
            if (!rateOfInterest.matches("[0-9.]*")) {
                setMessage("Please Enter Valid Balance.");
                return;
            } else if (this.rateOfInterest.contains(".")) {
                if (this.rateOfInterest.indexOf(".") != this.rateOfInterest.lastIndexOf(".")) {
                    this.setMessage("Invalid ROI.Please Fill The ROI Correctly.");
                    return;
                }
            } else if (Float.parseFloat(rateOfInterest) > 25) {
                setMessage("ROI Can not be greater than 25.");
                return;
            }
        }

        if (amount == null || amount.equalsIgnoreCase("") || amount.equalsIgnoreCase("0")) {
            setMessage("Amount Cannot Be Blank or Zero.");
            return;
        } else if (amount != null) {
            Matcher amountCheck = p.matcher(amount);
            Matcher amountbalance = p.matcher(balance);
            if (!amountCheck.matches()) {
                setMessage("Please Enter Valid Amount. ");
                return;
            } else if (!amount.matches("[0-9.]*")) {
                setMessage("Please Enter Valid Amount.");
                return;
            } else if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    this.setMessage("Invalid Amount.Please Fill The Amount Correctly.");
                    return;
                }
            } else if (Double.parseDouble(amount) < 0) {
                setMessage("Amount Cannot Be Blank or Zero.");
                return;
            } else if (!amountbalance.matches()) {
                setMessage("Please Enter Valid Balance. ");
                return;
            } else if (!balance.matches("[0-9.]*")) {
                setMessage("Please Enter Valid Balance.");
                return;
            } else if (this.balance.contains(".")) {
                if (this.balance.indexOf(".") != this.balance.lastIndexOf(".")) {
                    this.setMessage("Invalid Balance.Please Fill The Balance Correctly.");
                    return;
                }
            } else if (Float.parseFloat(balance) < 0) {
                setMessage("Balance Cannot Be Blank or Zero.");
                return;
            } else {
                if (Float.parseFloat(amount) > Float.parseFloat(balance)) {
                    setMessage("TD Amount Should Be less Than Balance.");
                    return;
                }
            }
        }
        if (matDate == null || matDate.equalsIgnoreCase("")) {
            setMessage("Please Calculate the MatDate.");
            return;
        }
        if (intOption.equalsIgnoreCase("0")) {
            setMessage("Please Select the Interest Option.");
            return;
        }
        SimpleDateFormat sdfdhd = new SimpleDateFormat("yyyyMMdd");
        String dates = sdfdhd.format(this.tdDate);
        try {
            String msg;
            msg = tdRcptMgmtRemote.orgFDInterest(intOption, Float.parseFloat(rateOfInterest), dates, ymd.format(sdf.parse(matDate)), Double.parseDouble(amount), period, this.getOrgnBrCode());
            double totalMatAmount = Double.parseDouble(msg) + Double.parseDouble(amount);
            setMatAmount(formatter.format(totalMatAmount));
            interest = "0.00";
            if (intOption.equals("M") || intOption.equals("Q") || intOption.equals("Y")) {
                String tmpDate = "";
                if (intOption.equals("M")) {
                    tmpDate = CbsUtil.monthAdd(dates, 1);
                    setIntLabel("Monthly Interest");
                }
                if (intOption.equals("Q")) {
                    tmpDate = CbsUtil.monthAdd(dates, 3);
                    setIntLabel("Quarterly Interest");
                }
                if (intOption.equals("Y")) {
                    tmpDate = CbsUtil.monthAdd(dates, 12);
                    setIntLabel("Yearly Interest");
                }

                msg = tdRcptMgmtRemote.orgFDInterest(intOption, Float.parseFloat(rateOfInterest), dates, tmpDate, Double.parseDouble(amount), period, this.getOrgnBrCode());
                setInterest(formatter.format(Double.parseDouble(msg)));
            }else{
                interest = formatter.format(Double.parseDouble(msg));
            }

        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setroi() {
        try {
            Float roi = tdRcptMgmtRemote.tdApplicableROI(acctNo, custNaturePass, Double.parseDouble(amount), ymd.format(sdf.parse(matDate)), ymd.format(tdDate), ymd.format(sdf.parse(getTodayDate())), ftsPostRemote.getAccountNature(acctNo));
            setRateOfInterest(String.valueOf(CbsUtil.round(roi, 2)));
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setAcctNature() {
        try {
            acctNature = ftsPostRemote.getAccountNature(acctNo);
            setBookSeries();

        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setBookSeries() {
        try {
            bookSeriesList = new ArrayList<SelectItem>();
            bookSeriesList.add(new SelectItem("0", "--SELECT--"));
            List bookList = tdRcptMgmtRemote.getBookSeries(acctNature, tdReceipt, getOrgnBrCode());
            if (bookList.isEmpty()) {
                throw new ApplicationException("Book Series does not exit");
            }
            for (int i = 0; i < bookList.size(); i++) {
                Vector ele = (Vector) bookList.get(i);
                bookSeriesList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setfdGlobal() {
        try {
            setSaveFlag("false");
            List globalFdList = tdRcptMgmtRemote.getGlobalFdCondition();
            if (globalFdList.isEmpty()) {
                setSaveFlag("true");
                throw new ApplicationException("Please Fill TD Condition From TD Condition Master Form Before New Receipt Creation");
            }
            Vector ele = (Vector) globalFdList.get(0);
            tdAmount = ele.get(0).toString();
            tdDayMth = ele.get(1).toString();
            tdDayCum = ele.get(2).toString();
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setReceipt() {
        try {
//            List autoRenewList = new ArrayList();
//            List tdRecieptSeqList = new ArrayList();
//            autoRenewList = tdRcptMgmtRemote.getAutoRenew();
//            Vector ele = (Vector) autoRenewList.get(0);
//            String autoRenew = ele.get(0).toString();
//            if (autoRenew.equalsIgnoreCase("0")) {
//                setAutoRenew("N");
//            } else if (autoRenew.equalsIgnoreCase("1")) {
//                setAutoRenew("Y");
//            }
            List tdRecieptSeqList = tdRcptMgmtRemote.getTdRecieptSeq();
            if (tdRecieptSeqList.size() > 0) {
                Vector vctReciept = (Vector) tdRecieptSeqList.get(0);
                tdReceipt = vctReciept.get(0).toString();
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveAction() {
        if (SaveValidation().equalsIgnoreCase("false")) {
            return;
        }
        if (validateAutoPay() == false) {
            return;
        }
        if (validationPeriod().equalsIgnoreCase("false")) {
            return;
        }
        String acnoCredit;
        if (intOption.equalsIgnoreCase("C") || intOption.equalsIgnoreCase("S")) {
            acnoCredit = "";
        } else {
            acnoCredit = crediterAccount;
        }
        try {
            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(acctNo);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                this.setMessage("Sorry! Other branch account no. is not allowed to proceed.");
                return;
            }
            if (function.equals("C")) {
                String chkSr = tdRcptMgmtRemote.getValidateBookSeries(acctNature, this.acctNo.substring(2, 4), tdReceipt, getOrgnBrCode(), bookSeries);
                if (chkSr.equalsIgnoreCase("false")) {
                    this.setMessage("Book Series does not exit");
                    return;
                }

                String rs = tdRcptMgmtRemote.newTdReciptCreation(acctNature, acctNo, ymd.format(sdf.parse(getTodayDate())), ymd.format(sdf.parse(getTodayDate())),
                        ymd.format(tdDate), ymd.format(sdf.parse(matDate)), Double.parseDouble(amount), bookSeries, getUserName(), intOption, glHead,
                        acnoCredit, Float.parseFloat(rateOfInterest), Integer.parseInt(year), Integer.parseInt(months), Integer.parseInt(days), autoRenew, getOrgnBrCode(), autoPay, paidAcno);
                setMessage(rs);
                resetValues();
            } else if (function.equals("V")) {
                if (this.getUserName().equals(enteredBy)) {
                    throw new ApplicationException("You can not authorize your own entry.");
                }
                if (!ftsPostRemote.isUserAuthorized(getUserName(), getOrgnBrCode())) {
                    throw new ApplicationException("You are not authorized person for verifing this detail.");
                }
                String rs = tdRcptMgmtRemote.newTdReciptAuthorization(acctNature, acctNo, ymd.format(sdf.parse(getTodayDate())),
                        ymd.format(sdf.parse(getTodayDate())), ymd.format(tdDate), ymd.format(sdf.parse(matDate)), Double.parseDouble(amount),
                        tdReceipt, bookSeries, getOrgnBrCode(), this.acctNo.substring(2, 4), enteredBy, intOption, glHead, acnoCredit,
                        Float.parseFloat(rateOfInterest), period, Integer.parseInt(year), Integer.parseInt(months), Integer.parseInt(days),
                        autoRenew, getUserName(), Long.parseLong(pendingRtNo), autoPay, paidAcno);
                setMessage(rs);
                resetValues();
            } else {
                String result = tdRcptMgmtRemote.modifyReceiptDetails(acctNature, function, acctNo, pendingRtNo, intOption, acnoCredit, autoRenew,
                        Integer.parseInt(days), Integer.parseInt(months), Integer.parseInt(year), Float.parseFloat(rateOfInterest),
                        ymd.format(sdf.parse(matDate)), period, getUserName(), getOrgnBrCode(), autoPay, paidAcno);
                setMessage(result);
                resetValues();
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String SaveValidation() {
        if (getSaveFlag().equalsIgnoreCase("true")) {
            setMessage("Please Fill TD Condition From TD Condition Master Form Before New Receipt Creation");
            return "false";
        }
        setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (acctNo == null || acctNo.equalsIgnoreCase("")) {
                setMessage("Please Enter the Account No.");
                return "false";
            }
            if (amount == null || amount.equalsIgnoreCase("") || amount.length() == 0) {
                setMessage("Amount Cannot Be Blank or Zero.");
                return "false";
            } else if (amount != null) {
                Matcher amountCheck = p.matcher(amount);
                if (!amountCheck.matches()) {
                    setMessage("Please Enter Valid Amount.");
                    return "false";
                } else if (!amount.matches("[0-9.]*")) {
                    setMessage("Please Enter Valid Amount.");
                    return "false";
                } else if (this.amount.contains(".")) {
                    if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                        this.setMessage("Invalid Amount.Please Fill The Amount Correctly.");
                        return "false";
                    }
                } else if (Float.parseFloat(amount) < 0) {
                    setMessage("Amount Cannot Be Blank or Zero.");
                    return "false";
                } else {
                    if (Float.parseFloat(amount) > Float.parseFloat(balance)) {
                        setMessage("TD Amount Should Be less Than Balance.");
                        return "false";
                    }
                }
            }
            if (tdDate.after(sdf.parse(getTodayDate()))) {
                setMessage("Please Check The Date.");
                return "false";
            }
            if (backDateReceipt == 0 && tdDate.before(sdf.parse(getTodayDate()))) {
                setMessage("Please Check The Date.");
                return "false";
            }

            if (intOption == null | intOption.equalsIgnoreCase("0")) {
                setMessage("Please Select the Interest Option.");
                return "false";
            }
            if ((intOption.equalsIgnoreCase("M") || intOption.equalsIgnoreCase("Q")) && (acToCredit.equalsIgnoreCase(""))) {
                setMessage("Please enter A/c no to where interest should be transferred");
                return "false";
            }

            if ((year.equalsIgnoreCase("0") && months.equalsIgnoreCase("0") && days.equalsIgnoreCase("0")) || (year.equalsIgnoreCase("") && months.equalsIgnoreCase("") && days.equalsIgnoreCase("")) || (year == null || months == null || days == null)) {
                setMessage("Please Enter the Duration.");
                return "false";
            }
            if (rateOfInterest == null || rateOfInterest.equalsIgnoreCase("") || rateOfInterest.equalsIgnoreCase("0.0") || rateOfInterest.equalsIgnoreCase("0")) {
                setMessage("ROI Cannot Be Blank or Zero.");
                return "false";
            } else {
                Matcher roiCheck = p.matcher(rateOfInterest);
                if (!roiCheck.matches()) {
                    setMessage("Please Enter Valid Rate Of Interest.");
                    return "false";

                } else if (Float.parseFloat(rateOfInterest) < 0) {
                    setMessage("ROI Can not be less than 0.");
                    return "false";

                } else if (Float.parseFloat(rateOfInterest) > 25) {
                    setMessage("ROI Can not be greater than 25.");
                    return "false";
                }
            }
            if (matDate != null || !matDate.equalsIgnoreCase("")) {
                if ((year == null || year.equalsIgnoreCase("0")) && (months == null || months.equalsIgnoreCase("0")) && (days == null || !days.equalsIgnoreCase("0"))) {
                    if (intOption.equalsIgnoreCase("M") || intOption.equalsIgnoreCase("Q") || intOption.equalsIgnoreCase("S")) {
                        if (Integer.parseInt(days) < Integer.parseInt(tdDayMth)) {
                            setMessage("Please note duration cannot be less than " + tdDayMth + ". Please re-enter period");
                            return "false";
                        }
                    }

                    if (intOption.equalsIgnoreCase("C")) {
                        if (Integer.parseInt(days) < Integer.parseInt(tdDayCum)) {
                            setMessage("Please note duration cannot be less than " + tdDayCum + ". Please re-enter period");
                            return "false";
                        }
                    }
                }
            } else {
                setMessage("Please Set the Mat Date.");
                return "false";
            }
            
            if (intOption.equalsIgnoreCase("C")) {
                long dayDiff = CbsUtil.dayDiff(tdDate, sdf.parse(matDate));
                if (dayDiff < Integer.parseInt(tdDayCum)) {
                    setMessage("Please note duration cannot be less than " + tdDayCum + ". Please re-enter period");
                    return "false";
                }
            }
            
            if (matAmount == null || matAmount.equalsIgnoreCase("")) {
                setMessage("Please Set the MatAmount.");
                return "false";
            }
            if (function.equals("C") && (bookSeries == null || bookSeries.equalsIgnoreCase("0"))) {
                setMessage("Please Select the BookSeries.");
                return "false";
            }
            if (backDateReceipt == 0) {
                String recChk = ftsPostRemote.isReceiptExist(acctNo);
                String frDate = ymd.format(tdDate);
                int day = ftsPostRemote.dateDiff(frDate, ymd.format(new Date()));
                if (recChk.equalsIgnoreCase("true")) {
                    if (day > 14) {
                        this.setMessage("Renewed Date Can't Be Less More Than 14 Days");
                        return "false";
                    }
                } else {
                    if (day > 0) {
                        this.setMessage("Renewed Date Can't Be Less From Current Date");
                        return "false";
                    }
                }
            }
            return "true";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public String validationPeriod() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (year != null || !year.equalsIgnoreCase("")) {
            Matcher yearCheck = p.matcher(year);
            if (!yearCheck.matches()) {
                setMessage("Please Enter Valid Years.");
                return "false";
            }
            if (!year.matches("[0-9]*")) {
                setYear("");
                setMessage("Please Enter Valid Year.");
                return "false";
            }
        }
        if (months != null || !months.equalsIgnoreCase("")) {
            Matcher monthsCheck = p.matcher(months);
            if (!monthsCheck.matches()) {
                setMessage("Please Enter Valid Months.");
                return "false";
            }
            if (!months.matches("[0-9]*")) {
                setMonths("");
                setMessage("Please Enter Valid Months.");
                return "false";
            }
        }
        if (days != null || !days.equalsIgnoreCase("")) {
            Matcher daysCheck = p.matcher(days);
            if (!daysCheck.matches()) {
                setMessage("Please Enter Valid Days.");
                return "false";
            }
            if (!days.matches("[0-9]*")) {
                setDays("");
                setMessage("Please Enter Valid Days.");
                return "false";
            }
        }

        if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (year.equalsIgnoreCase("0") || year.equalsIgnoreCase("")) && (months.equalsIgnoreCase("0") || months.equalsIgnoreCase(""))) {
            months = "0";
            days = "0";
            year = "0";
            period = "0";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (months.equalsIgnoreCase("0") || months.equalsIgnoreCase("")) && (year.equalsIgnoreCase("0") || year.equalsIgnoreCase(""))) {
            months = "0";
            year = "0";
            period = days + "Days";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (!months.equalsIgnoreCase("0") || !months.equalsIgnoreCase("")) && (year.equalsIgnoreCase("0") || year.equalsIgnoreCase(""))) {
            days = "0";
            year = "0";
            period = months + "Months";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (months.equalsIgnoreCase("0") || months.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("0") || year.equalsIgnoreCase(""))) {
            months = "0";
            days = "0";
            period = year + "Years";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("0") || !year.equalsIgnoreCase("")) && (months.equalsIgnoreCase("0") || months.equalsIgnoreCase(""))) {
            months = "0";
            period = year + "Years" + days + "Days";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (year.equalsIgnoreCase("0") || year.equalsIgnoreCase("")) && (!months.equalsIgnoreCase("0") || !months.equalsIgnoreCase(""))) {
            year = "0";
            period = months + "Months" + days + "Days";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("0") || !year.equalsIgnoreCase("")) && (!months.equalsIgnoreCase("0") || !months.equalsIgnoreCase(""))) {
            days = "0";
            period = year + "Years" + months + "Months";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("0") || !year.equalsIgnoreCase("")) && (!months.equalsIgnoreCase("0") || !months.equalsIgnoreCase(""))) {
            period = year + "Years" + months + "Months" + days + "Days";
        }
        return "true";
    }

    public void checkTdDate() {
        try {
            if (backDateReceipt == 1) {
                if (tdDate.after(sdf.parse(getTodayDate()))) {
                    setMessage("TD Date cannot be greater or less than the current date.");
                }
            } else {
                if (tdDate.after(sdf.parse(getTodayDate())) || tdDate.before(sdf.parse(getTodayDate()))) {
                    setMessage("TD Date cannot be greater or less than the current date.");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validateAutoPay() {
        try {
            if (autoPay.equals("Y")) {
                if (autoRenew.equals("Y")) {
                    setMessage("Both auto renew and auto payment can not be applicable together.");
                    return false;
                }
                if (paidAcno == null || paidAcno.equals("") || paidAcno.trim().length() != 12) {
                    setMessage("In case when auto payment is applicable then auto payment a/c is mandatory.");
                    return false;
                }
                String result = ftsPostRemote.ftsAcnoValidate(paidAcno, 0, "");
                if (!result.equalsIgnoreCase("true")) {
                    setMessage(result);
                    return false;
                }
                String paidAcNature = ftsPostRemote.getAccountNature(paidAcno);
                if (!(paidAcNature.equals(CbsConstant.SAVING_AC)
                        || (paidAcNature.equals(CbsConstant.CURRENT_AC)
                        && ftsPostRemote.getAcctTypeByCode(paidAcno.substring(2, 4)).equals(CbsConstant.CURRENT_AC)))) {
                    setMessage("Only saving and current a/c is required in Auto Payment A/c.");
                    return false;
                }
            } else {
                autoPay = "N";
                paidAcno = "";
                paidAcnoName = "";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void getCustName() {
        paidAcnoName = "";
        try {
            if (autoPay.equals("Y")) {
                setPaidAcnoName(ftsPostRemote.getCustName(paidAcno));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetValues() {
        setAcToCredit("");
        setAcctNo("");
        this.setOldAcNo("");
        setAmount("");
//        setAutoRenew("N");
        setBalance("0.00");
        setBookSeries("0");
        setCrediterAccount("");
        setYear("0");
        setStatus("");
        //setRemark("");
        setRateOfInterest("");
        setPrincipalAmt("0.00");
        setName("");
        setMonths("0");
        setMatAmount("0.00");
        setCustNature("");
        setCustNaturePass("");
        setDays("0");
        setDescription("");
        setIntOption("0");
        setJtName("");
        setMatDate("");
        setTds("");
        setPendingAcNo("");
        pendingAcNoList = new ArrayList<SelectItem>();
        setPendingRtNo("");
        pendingRtNoList = new ArrayList<SelectItem>();
        setFunction("C");
        setReceiptNo("");
        setInputDisFlag("none");
        //Addition on 02/12/2015
//        setAutoPay("N");
        setAutoValues();
        setPaidAcno("");
        setPaidAcnoName("");
        setInterest("0.00");
        setIntLabel("Interest Amount");
    }

    public void resetValue() {
        setAcToCredit("");
        setAcctNo("");
        this.setOldAcNo("");
        setAmount("");
//        setAutoRenew("N");
        setBalance("0.00");
        setBookSeries("0");
        setCrediterAccount("");
        setYear("0");
        setStatus("");
        setRateOfInterest("");
        setPrincipalAmt("0.00");
        setName("");
        setMonths("0");
        setMessage("");
        setMatAmount("0.00");
        setCustNature("");
        setCustNaturePass("");
        setDays("0");
        setDescription("");
        setIntOption("0");
        setJtName("");
        setMatDate("");
        setTds("");
        setPendingAcNo("");
        pendingAcNoList = new ArrayList<SelectItem>();
        setPendingRtNo("");
        pendingRtNoList = new ArrayList<SelectItem>();
        setFunction("C");
        setReceiptNo("");
        setInputDisFlag("none");
        //Addition on 02/12/2015
//        setAutoPay("N");
        setAutoValues();
        setPaidAcno("");
        setPaidAcnoName("");
        setInterest("0.00");
        setIntLabel("Interest Amount");
    }

    public void setInVIsibleAcToCredit() {
        if (intOption.equalsIgnoreCase("C")) {
            setCrediterAccount("");
            setAcToCredit("");
            acctCrdVisible = false;
        } else if (intOption.equalsIgnoreCase("Q")) {
            acctCrdVisible = true;
        } else if (intOption.equalsIgnoreCase("M")) {
            acctCrdVisible = true;
        } else if (intOption.equalsIgnoreCase("S")) {
            acctCrdVisible = false;
        } else if (intOption.equalsIgnoreCase("Y")) {
            acctCrdVisible = true;
        }
    }

    public String exitBtnAction() {
        resetValue();
        return "case1";
    }
    //private String seriesDisplayFlag = "";
    private String interest;
    private String intLabel;
    private String receiptNo;
    private String oldAcNo;
    private String acctNo;
    private String name;
    private String jtName;
    private String balance;
    private String amount;
    private Date tdDate = new Date();
    private String intOption;
    private String acToCredit;
    private String custNature;
    private String custNaturePass;
    private String year = "0";
    private String months = "0";
    private String days = "0";
    private String rateOfInterest;
    private String matDate;
    private String principalAmt;
    private String matAmount;
    private String autoRenew;
    private String bookSeries;
    private String tds;
    private String crediterAccount;
    private String status;
    private String description;
    private String saveFlag = "false";
    private String focusId;
    private String message;
    private String tdReceipt = "";
    private String acctNature;
    private String tdAmount;
    private String tdDayMth;
    private String tdDayCum;
    private String period;
    private String glHead = "";
    private boolean acctCrdVisible;
    Date date = new Date();
    private String inputDisFlag = "none";
    private String pendingAcNo;
    private String pendingRtNo;
    private String function;
    private String btnLabel = "Save";
    private boolean modifyFlag;
    private boolean verifyFlag;
    private String enteredBy;
    private String autoPay;
    private String paidAcno, acNoLen;
    private String paidAcnoName;
    private int backDateReceipt;
    private String autoRenewVar = "none";
    private String autoPaymentVar = "none";
    private List<SelectItem> pendingAcNoList;
    private List<SelectItem> pendingRtNoList;
    private List<SelectItem> intOptionList;
    private List<SelectItem> functionList;
    private List<SelectItem> autoRenewList;
    private List<SelectItem> bookSeriesList;
    private List<SelectItem> autoPayList;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#0.00");
    private final String jndiHomeName = "TdReceiptManagementFacade";
    private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private CommonReportMethodsRemote reportMethodsRemote = null;

    public String getIntLabel() {
        return intLabel;
    }

    public void setIntLabel(String intLabel) {
        this.intLabel = intLabel;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getBtnLabel() {
        return btnLabel;
    }

    public void setBtnLabel(String btnLabel) {
        this.btnLabel = btnLabel;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getInputDisFlag() {
        return inputDisFlag;
    }

    public void setInputDisFlag(String inputDisFlag) {
        this.inputDisFlag = inputDisFlag;
    }

    public String getPendingAcNo() {
        return pendingAcNo;
    }

    public void setPendingAcNo(String pendingAcNo) {
        this.pendingAcNo = pendingAcNo;
    }

    public String getPendingRtNo() {
        return pendingRtNo;
    }

    public void setPendingRtNo(String pendingRtNo) {
        this.pendingRtNo = pendingRtNo;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getPendingAcNoList() {
        return pendingAcNoList;
    }

    public void setPendingAcNoList(List<SelectItem> pendingAcNoList) {
        this.pendingAcNoList = pendingAcNoList;
    }

    public List<SelectItem> getPendingRtNoList() {
        return pendingRtNoList;
    }

    public void setPendingRtNoList(List<SelectItem> pendingRtNoList) {
        this.pendingRtNoList = pendingRtNoList;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public boolean isAcctCrdVisible() {
        return acctCrdVisible;
    }

    public void setAcctCrdVisible(boolean acctCrdVisible) {
        this.acctCrdVisible = acctCrdVisible;
    }

    public List<SelectItem> getBookSeriesList() {
        return bookSeriesList;
    }

    public void setBookSeriesList(List<SelectItem> bookSeriesList) {
        this.bookSeriesList = bookSeriesList;
    }

    public List<SelectItem> getAutoRenewList() {
        return autoRenewList;
    }

    public void setAutoRenewList(List<SelectItem> autoRenewList) {
        this.autoRenewList = autoRenewList;
    }

    public String getCrediterAccount() {
        return crediterAccount;
    }

    public void setCrediterAccount(String crediterAccount) {
        this.crediterAccount = crediterAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getAcToCredit() {
        return acToCredit;
    }

    public void setAcToCredit(String acToCredit) {
        this.acToCredit = acToCredit;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(String autoRenew) {
        this.autoRenew = autoRenew;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBookSeries() {
        return bookSeries;
    }

    public void setBookSeries(String bookSeries) {
        this.bookSeries = bookSeries;
    }

    public String getCustNature() {
        return custNature;
    }

    public void setCustNature(String custNature) {
        this.custNature = custNature;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getIntOption() {
        return intOption;
    }

    public void setIntOption(String intOption) {
        this.intOption = intOption;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getMatAmount() {
        return matAmount;
    }

    public void setMatAmount(String matAmount) {
        this.matAmount = matAmount;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipalAmt() {
        return principalAmt;
    }

    public void setPrincipalAmt(String principalAmt) {
        this.principalAmt = principalAmt;
    }

    public String getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(String rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public Date getTdDate() {
        return tdDate;
    }

    public void setTdDate(Date tdDate) {
        this.tdDate = tdDate;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<SelectItem> getIntOptionList() {
        return intOptionList;
    }

    public void setIntOptionList(List<SelectItem> intOptionList) {
        this.intOptionList = intOptionList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(String saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getOldAcNo() {
        return oldAcNo;
    }

    public void setOldAcNo(String oldAcNo) {
        this.oldAcNo = oldAcNo;
    }

    public String getCustNaturePass() {
        return custNaturePass;
    }

    public void setCustNaturePass(String custNaturePass) {
        this.custNaturePass = custNaturePass;
    }

    public boolean isModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(boolean modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public boolean isVerifyFlag() {
        return verifyFlag;
    }

    public void setVerifyFlag(boolean verifyFlag) {
        this.verifyFlag = verifyFlag;
    }

    public String getAutoPay() {
        return autoPay;
    }

    public void setAutoPay(String autoPay) {
        this.autoPay = autoPay;
    }

    public List<SelectItem> getAutoPayList() {
        return autoPayList;
    }

    public void setAutoPayList(List<SelectItem> autoPayList) {
        this.autoPayList = autoPayList;
    }

    public String getPaidAcno() {
        return paidAcno;
    }

    public void setPaidAcno(String paidAcno) {
        this.paidAcno = paidAcno;
    }

    public String getPaidAcnoName() {
        return paidAcnoName;
    }

    public void setPaidAcnoName(String paidAcnoName) {
        this.paidAcnoName = paidAcnoName;
    }

    public String getAutoRenewVar() {
        return autoRenewVar;
    }

    public void setAutoRenewVar(String autoRenewVar) {
        this.autoRenewVar = autoRenewVar;
    }

    public String getAutoPaymentVar() {
        return autoPaymentVar;
    }

    public void setAutoPaymentVar(String autoPaymentVar) {
        this.autoPaymentVar = autoPaymentVar;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
}
