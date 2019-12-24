/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.td;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.AutoTermDepositRenewalRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.TdPaymentGrid;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class TDPayment extends BaseBean {

    private String enterBy;
    private boolean disableFlag;
    private String inputDisFlag = "none";
    private String pendingAcNo;
    private String pendingRtNo;
    private String function;
    private List<SelectItem> pendingAcNoList;
    private List<SelectItem> pendingRtNoList;
    private List<SelectItem> functionList;
    private String btnLabel = "Save";
    private String focusId;
    private String intLbl;
    private String provision;
    private String message;
    private String custName;
    private String operationMode;
    private String jtUgName;
    private List<TdPaymentGrid> payment;
    private int currentRow;
    private TdPaymentGrid currentItem;
    private String rtNo;
    private String matDt;
    private String issueDt;
    private String custCategory;
    private String interestPaid;
    private String actualTotalInterest;
    private String remainingInterest;
    private String deductForLastFinalFear;
    private String tdsDeducted;
    private String tdsToBeDeducted;
    private String netAmount;
    private String interestOption;
    private String contractedRoi;
    private String noOfActiveDays;
    private String currentStatus;
    private String applicableRate;
    private Double prinAmount;
    private String fullAccNo;
    private String penalityApplicable;
    private String matPreFlag;
    private String netContractedRoi;
    private String previousBalanceAc;
    private String flag;
    private String penalityFlag;
    private String acInfo;
    private String oldAcNo, acNoLen;
    private float receiptNo;
    private String closeActTdsToBeDeducted;
    private String closeActTdsDeducted;
    private String closeActIntFinYear;
    private final String jndiHomeNameTdCal = "TermDepositeCalculationManagementFacade";
    private TermDepositeCalculationManagementFacadeRemote tdCalMgmtRemote = null;
    private final String jndiHomeNameTdRcpt = "TdReceiptManagementFacade";
    private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private AutoTermDepositRenewalRemote autoTdRemote = null;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public String getCloseActTdsToBeDeducted() {
        return closeActTdsToBeDeducted;
    }

    public void setCloseActTdsToBeDeducted(String closeActTdsToBeDeducted) {
        this.closeActTdsToBeDeducted = closeActTdsToBeDeducted;
    }

    public String getCloseActTdsDeducted() {
        return closeActTdsDeducted;
    }

    public void setCloseActTdsDeducted(String closeActTdsDeducted) {
        this.closeActTdsDeducted = closeActTdsDeducted;
    }

    public String getCloseActIntFinYear() {
        return closeActIntFinYear;
    }

    public void setCloseActIntFinYear(String closeActIntFinYear) {
        this.closeActIntFinYear = closeActIntFinYear;
    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
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

    public List<SelectItem> getPendingAcNoList() {
        return pendingAcNoList;
    }

    public void setPendingAcNoList(List<SelectItem> pendingAcNoList) {
        this.pendingAcNoList = pendingAcNoList;
    }

    public String getPendingRtNo() {
        return pendingRtNo;
    }

    public void setPendingRtNo(String pendingRtNo) {
        this.pendingRtNo = pendingRtNo;
    }

    public List<SelectItem> getPendingRtNoList() {
        return pendingRtNoList;
    }

    public void setPendingRtNoList(List<SelectItem> pendingRtNoList) {
        this.pendingRtNoList = pendingRtNoList;
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

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getIntLbl() {
        return intLbl;
    }

    public void setIntLbl(String intLbl) {
        this.intLbl = intLbl;
    }

    public String getPenalityFlag() {
        return penalityFlag;
    }

    public void setPenalityFlag(String penalityFlag) {
        this.penalityFlag = penalityFlag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPreviousBalanceAc() {
        return previousBalanceAc;
    }

    public void setPreviousBalanceAc(String previousBalanceAc) {
        this.previousBalanceAc = previousBalanceAc;
    }

    public String getNetContractedRoi() {
        return netContractedRoi;
    }

    public void setNetContractedRoi(String netContractedRoi) {
        this.netContractedRoi = netContractedRoi;
    }

    public String getMatPreFlag() {
        return matPreFlag;
    }

    public void setMatPreFlag(String matPreFlag) {
        this.matPreFlag = matPreFlag;
    }

    public String getPenalityApplicable() {
        return penalityApplicable;
    }

    public void setPenalityApplicable(String penalityApplicable) {
        this.penalityApplicable = penalityApplicable;
    }

    public String getFullAccNo() {
        return fullAccNo;
    }

    public void setFullAccNo(String fullAccNo) {
        this.fullAccNo = fullAccNo;
    }

    public Double getPrinAmount() {
        return prinAmount;
    }

    public void setPrinAmount(Double prinAmount) {
        this.prinAmount = prinAmount;
    }

    public String getApplicableRate() {
        return applicableRate;
    }

    public void setApplicableRate(String applicableRate) {
        this.applicableRate = applicableRate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getNoOfActiveDays() {
        return noOfActiveDays;
    }

    public void setNoOfActiveDays(String noOfActiveDays) {
        this.noOfActiveDays = noOfActiveDays;
    }

    public String getContractedRoi() {
        return contractedRoi;
    }

    public void setContractedRoi(String contractedRoi) {
        this.contractedRoi = contractedRoi;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public String getInterestOption() {
        return interestOption;
    }

    public void setInterestOption(String interestOption) {
        this.interestOption = interestOption;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getTdsToBeDeducted() {
        return tdsToBeDeducted;
    }

    public void setTdsToBeDeducted(String tdsToBeDeducted) {
        this.tdsToBeDeducted = tdsToBeDeducted;
    }

    public String getTdsDeducted() {
        return tdsDeducted;
    }

    public void setTdsDeducted(String tdsDeducted) {
        this.tdsDeducted = tdsDeducted;
    }

    public String getDeductForLastFinalFear() {
        return deductForLastFinalFear;
    }

    public void setDeductForLastFinalFear(String deductForLastFinalFear) {
        this.deductForLastFinalFear = deductForLastFinalFear;
    }

    public String getRemainingInterest() {
        return remainingInterest;
    }

    public void setRemainingInterest(String remainingInterest) {
        this.remainingInterest = remainingInterest;
    }

    public String getActualTotalInterest() {
        return actualTotalInterest;
    }

    public void setActualTotalInterest(String actualTotalInterest) {
        this.actualTotalInterest = actualTotalInterest;
    }

    public String getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(String interestPaid) {
        this.interestPaid = interestPaid;
    }

    public String getCustCategory() {
        return custCategory;
    }

    public void setCustCategory(String custCategory) {
        this.custCategory = custCategory;
    }

    public String getMatDt() {
        return matDt;
    }

    public void setMatDt(String matDt) {
        this.matDt = matDt;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public TdPaymentGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TdPaymentGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<TdPaymentGrid> getPayment() {
        return payment;
    }

    public void setPayment(List<TdPaymentGrid> payment) {
        this.payment = payment;
    }

    public String getJtUgName() {
        return jtUgName;
    }

    public void setJtUgName(String jtUgName) {
        this.jtUgName = jtUgName;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOldAcNo() {
        return oldAcNo;
    }

    public void setOldAcNo(String oldAcNo) {
        this.oldAcNo = oldAcNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    /**
     * Creates a new instance of TDPayment
     */
    public TDPayment() {
        try {
            tdCalMgmtRemote = (TermDepositeCalculationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdCal);
            tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdRcpt);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            autoTdRemote = (AutoTermDepositRenewalRemote) ServiceLocator.getInstance().lookup("AutoTermDepositRenewal");
            this.setAcNoLen(getAcNoLength());
            getOnLoadDroupDownValue();
            this.setMessage("");
            setPenalityApplicable("0.00");
            setApplicableRate("0.00");
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getOnLoadDroupDownValue() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("P", "Payment"));
            functionList.add(new SelectItem("V", "Verify"));
            functionList.add(new SelectItem("D", "Delete"));
            setFunction("P");
            setIntLbl("Interest Paid");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void changeFunction() {
        if (function.equals("P")) {
            setFocusId("txtAcno");
            setBtnLabel("Save");
            setInputDisFlag("none");
            setDisableFlag(false);
            setPenalityFlag("false");
        } else if (function.equals("V")) {
            //getUnAuthAcNo();
            setFocusId("ddPAcNo");
            setBtnLabel("Verify");
            setInputDisFlag("");
            setOldAcNo("");
            setFullAccNo("");
            setDisableFlag(true);
            setPenalityFlag("true");
            getUnAuthAcNo();
        } else {
            //getUnAuthAcNo();
            setFocusId("ddPAcNo");
            setBtnLabel("Delete");
            setOldAcNo("");
            setFullAccNo("");
            setInputDisFlag("");
            setDisableFlag(true);
            setPenalityFlag("true");
            getUnAuthAcNo();
        }
        payment = new ArrayList<TdPaymentGrid>();
    }

    public void getUnAuthAcNo() {
        try {
            this.setMessage("");
            pendingAcNoList = new ArrayList<SelectItem>();
            List dataList = tdCalMgmtRemote.getUnAuthAcNo(getOrgnBrCode());
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                pendingAcNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getUnAuthRtNo() {
        try {
            this.setMessage("");
            pendingRtNoList = new ArrayList<SelectItem>();
            this.setOldAcNo(getPendingAcNo());
            setFullAccNo(getPendingAcNo());
            List dataList = tdCalMgmtRemote.getUnAuthRtNo(getPendingAcNo());
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
            this.setMessage("");
            acInfo = tdCalMgmtRemote.cbsTdPaymentAccInfo(getPendingAcNo(), getOrgnBrCode());
            if (acInfo.equals("Account does not exist")) {
                initializeControls();
                this.setMessage(acInfo);
                return;
            } else {
                String[] values = null;
                String spliter = ": ";
                values = acInfo.split(spliter);
                String custNames = values[0];
                String jtName1 = values[1];
                String jtName2 = values[2];
                String description = values[3];
                String custType = values[4];
                setCustCategory(custType);
                String guarName = values[7];
                if ((guarName.equals("null")) || (guarName.equals(""))) {
                    guarName = "";
                }
                String jtName = jtName1 + jtName2 + guarName;
                setCustName(custNames);
                setOperationMode(description);
                setJtUgName(jtName);

                List dataList = tdCalMgmtRemote.getRtDetails(getPendingAcNo(), Float.parseFloat(getPendingRtNo()));
                Vector ele = (Vector) dataList.get(0);
                //0,    1,       2,         3      4      5         6           7           8           9     10
                // ACNO,VoucherNo,ReceiptNo,IntOpt,Status,PrinAmt,RemainingInt,TdsToBeDed,TDSDeducted,IntPaid,FDDT,
                // 11    12       13      14  15           16      17   18      19
                //MatDt,FinalAmt,Penalty,ROI,ActualTotInt,EnterBy,Auth,AuthBy,TranTime
                setRtNo(ele.get(1).toString());
                setInterestPaid(formatter.format(Double.parseDouble(ele.get(9).toString())));
                setActualTotalInterest(formatter.format(Double.parseDouble(ele.get(15).toString())));
                setRemainingInterest(formatter.format(Math.round(Double.parseDouble(ele.get(6).toString()))));
                // setDeductForLastFinalFear(formatter.format(Double.parseDouble(values[3])));
                setTdsDeducted(formatter.format(Double.parseDouble(ele.get(8).toString())));
                setTdsToBeDeducted(formatter.format(Double.parseDouble(ele.get(7).toString())));
                setNetAmount(formatter.format(Double.parseDouble(ele.get(12).toString())));

                /*if (values[7].equals("0")) {
                 } else {
                 setNoOfActiveDays(values[7]);
                 }*/

                setApplicableRate(formatter.format(Double.parseDouble(ele.get(19).toString())));
                if (ele.get(4).toString().equals("M")) {
                    setCurrentStatus("Mature Withdrawal");
                    setFocusId("btnCompleted");
                } else {
                    setCurrentStatus("PreMature Withdrawal");
                    setFocusId("txtPenaltyApplication");
                }
                //setMatPreFlag(values[10]);
                String intOpt = ele.get(3).toString();
                provision = tdRcptMgmtRemote.getProvApplyFlag(getPendingAcNo());
                if (!provision.equals("") && provision.toUpperCase().contains(intOpt.toUpperCase())) {
                    setIntLbl("Provision Interest");
                } else {
                    setIntLbl("Interest Paid");
                }
                if (intOpt.equals("C")) {
                    intOpt = "Cumulative";
                } else if (intOpt.equals("S")) {
                    intOpt = "Simple";
                } else if (intOpt.equals("Q")) {
                    intOpt = "Quarterly";
                } else if (intOpt.equals("M")) {
                    intOpt = "Monthly";
                }
                setInterestOption(intOpt);
                setMatDt(ele.get(11).toString());
                setIssueDt(ele.get(10).toString());

                setContractedRoi(formatter.format(Double.parseDouble(tdCalMgmtRemote.getContractedRoi(fullAccNo, Float.parseFloat(ele.get(1).toString())))));//todo
                setPenalityApplicable(formatter.format(Double.parseDouble(ele.get(13).toString())));
                setNetContractedRoi(formatter.format(Double.parseDouble(ele.get(20).toString())));

                setCloseActTdsToBeDeducted(formatter.format(Double.parseDouble(ele.get(21).toString())));
                setCloseActTdsDeducted(formatter.format(Double.parseDouble(ele.get(22).toString())));
                setCloseActIntFinYear(formatter.format(Double.parseDouble(ele.get(23).toString())));
                setPrinAmount(Double.parseDouble(ele.get(5).toString()));

                receiptNo = Float.parseFloat(ele.get(2).toString());
                enterBy = ele.get(16).toString();
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAccountNoInfo() {
        payment = new ArrayList<TdPaymentGrid>();
        this.setMessage("");
        setCustName("");
        setOperationMode("");
        setJtUgName("");

        if ((oldAcNo == null) || (oldAcNo.equalsIgnoreCase(""))) {
            initializeControls();
            this.setMessage("Please enter the Account Number");
            return;
        }
        //if (oldAcNo.length() < 12) {
        if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            initializeControls();
            this.setMessage("Please enter proper Account Number");
            return;
        }
        try {
            fullAccNo = ftsPostRemote.getNewAccountNumber(oldAcNo);
            if (fullAccNo.equalsIgnoreCase("Account number does not exist")) {
                initializeControls();
                this.setMessage(fullAccNo);
                return;
            }

            List accountDetails = tdRcptMgmtRemote.accountStatus(fullAccNo);
            if (accountDetails.isEmpty()) {
                this.setMessage("Account number does not exist");
                return;
            } else {
                for (int i = 0; i < accountDetails.size(); i++) {
                    Vector ele = (Vector) accountDetails.get(i);
                    String accStat = ele.get(1).toString();
                    if (accStat.equalsIgnoreCase("15")) {
                        initializeControls();
                        this.setMessage("Account is Deaf Marked");
                        return;
                    }
                }
            }

            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(fullAccNo);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                this.setMessage("Sorry! Account Number of Other branch does not allowed.");
                return;
            }
            acInfo = tdCalMgmtRemote.cbsTdPaymentAccInfo(fullAccNo, getOrgnBrCode());
            if (acInfo.equals("Account does not exist")) {
                initializeControls();
                this.setMessage(acInfo);
                return;
            } else {
                String[] values = null;
                String spliter = ": ";
                values = acInfo.split(spliter);
                String custNames = values[0];
                String jtName1 = values[1];
                String jtName2 = values[2];
                String description = values[3];
                String custType = values[4];
                setCustCategory(custType);
                String guarName = values[7];
                if ((guarName.equals("null")) || (guarName.equals(""))) {
                    guarName = "";
                }
                String jtName = jtName1 + jtName2 + guarName;
                setCustName(custNames);
                setOperationMode(description);
                setJtUgName(jtName);
            }
            List tableInfo = tdCalMgmtRemote.getTableDetail(fullAccNo, getOrgnBrCode());
            if (!tableInfo.isEmpty()) {
                for (int i = 0; i < tableInfo.size(); i++) {
                    Vector ele = (Vector) tableInfo.get(i);
                    TdPaymentGrid tdPay = new TdPaymentGrid();
                    tdPay.setVoucherNo(Float.parseFloat(ele.get(0).toString()));
                    tdPay.setReceiptNo(Float.parseFloat(ele.get(1).toString()));
                    tdPay.setSeqNo(Float.parseFloat(ele.get(2).toString()));
                    tdPay.setPrinAmt(Double.parseDouble(ele.get(3).toString()));
                    tdPay.setTdMadeDate(ele.get(4).toString());
                    tdPay.setFdDate(ele.get(5).toString());
                    tdPay.setMatDate(ele.get(6).toString());
                    tdPay.setIntOpt(ele.get(7).toString());
                    tdPay.setRoi(Float.parseFloat(ele.get(8).toString()));
                    tdPay.setStatus(ele.get(9).toString());
                    payment.add(tdPay);
                }
            } else {
                this.setMessage("Data does not Exist");
            }
            setRtNo("");
            setContractedRoi("0.00");
            setIssueDt("");
            setMatDt("");
            setInterestOption("");
            setCurrentStatus("");
            setApplicableRate("0.00");
            setPrinAmount(0.0d);
            setInterestPaid("0.00");
            setRemainingInterest("0.00");
            setActualTotalInterest("0.00");
            setNetAmount("0.00");
            setTdsDeducted("0.00");
            setTdsToBeDeducted("0.00");
            setDeductForLastFinalFear("0.00");
            setNoOfActiveDays("");
            setPreviousBalanceAc("");
            setPenalityApplicable("0.00");
            setNetContractedRoi("0.00");

            setCloseActIntFinYear("0.00");
            setCloseActTdsToBeDeducted("0.00");
            setCloseActTdsDeducted("0.00");

        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getRtInfo() {
        this.setMessage("");
        setPenalityApplicable("0.00");

        try {
            setRtNo(String.valueOf(currentItem.getVoucherNo()));
            String fdDts = currentItem.getFdDate();
            String dd = fdDts.substring(0, 2);
            String mm = fdDts.substring(3, 5);
            String yy = fdDts.substring(6, 10);
            String fdDates = yy + "" + mm + "" + dd;
            String matDts = currentItem.getMatDate();
            String dd1 = matDts.substring(0, 2);
            String mm1 = matDts.substring(3, 5);
            String yy1 = matDts.substring(6, 10);
            String matDates = yy1 + "" + mm1 + "" + dd1;
            Double pAmt = currentItem.getPrinAmt();
            String prAmt = String.valueOf(pAmt);
            String rtNoInfo = tdCalMgmtRemote.cbsTdPaymentRtno(fullAccNo, Float.parseFloat(rtNo), currentItem.getRoi(), fdDates, matDates, getUserName(), Double.parseDouble(prAmt), custCategory, getOrgnBrCode(), "", "");
            if (rtNoInfo.equals("This Receipt No. Does Not Exist")) {
                initializeControls();
                this.setMessage(rtNoInfo);
                return;
            } else if (rtNoInfo.equals("This Receipt is closed and Amount is Paid")) {
                initializeControls();
                this.setMessage(rtNoInfo);
                return;
            } else if (rtNoInfo.equals("The Receipt has been marked closed and amount is transferred to")) {
                initializeControls();
                this.setMessage(rtNoInfo);
                return;
            } else if (rtNoInfo.equals("Lien Marked against Deposit . To close the Deposit remove Lien Marking against Deposit")) {
                String acnoLien = tdCalMgmtRemote.LienAcno(fullAccNo);
                initializeControls();
                payment.clear();
                // this.setMessage(rtNoInfo);
                this.setMessage("Lien Marked against " + acnoLien + ". To close the Deposit remove Lien Marking against Deposit");
                return;
            } else {
                String[] values = null;
                String spliter = ": ";
                values = rtNoInfo.split(spliter);
                setInterestPaid(formatter.format(Double.parseDouble(values[0])));
                setActualTotalInterest(formatter.format(Double.parseDouble(values[1])));
                setRemainingInterest(formatter.format(Math.round(Double.parseDouble(values[2]))));
                setDeductForLastFinalFear(formatter.format(Double.parseDouble(values[3])));
                setTdsDeducted(formatter.format(Double.parseDouble(values[4])));
                if (Double.parseDouble(values[5]) < 0) {
                    setTdsToBeDeducted("0");
                } else {
                    setTdsToBeDeducted(formatter.format(Double.parseDouble(values[5])));
                }
                setNetAmount(formatter.format(Double.parseDouble(values[6])));
                if (values[7].equals("0")) {
                } else {
                    setNoOfActiveDays(values[7]);
                }
                setApplicableRate(formatter.format(Double.parseDouble(values[8])));
                setCurrentStatus(values[9]);
                setMatPreFlag(values[10]);
                //setOtherTdsToBeDeducted(formatter.format(Double.parseDouble(values[11])));
                //setTotalTdsToBeDeducted(formatter.format(Double.parseDouble(values[11]) + Double.parseDouble(values[5])));

                setCloseActIntFinYear(formatter.format(Double.parseDouble(values[13])));
                setCloseActTdsToBeDeducted(formatter.format(Double.parseDouble(values[11])));
                setCloseActTdsDeducted(formatter.format(Double.parseDouble(values[12])));

                String intOpt = currentItem.getIntOpt();
                provision = tdRcptMgmtRemote.getProvApplyFlag(fullAccNo);
                if (!provision.equals("") && provision.toUpperCase().contains(intOpt.toUpperCase())) {
                    setIntLbl("Provision Interest");
                } else {
                    setIntLbl("Interest Paid");
                }
                if (intOpt.equals("C")) {
                    intOpt = "Cumulative";
                } else if (intOpt.equals("S")) {
                    intOpt = "Simple";
                } else if (intOpt.equals("Q")) {
                    intOpt = "Quarterly";
                } else if (intOpt.equals("M")) {
                    intOpt = "Monthly";
                } else if (intOpt.equals("Y")) {
                    intOpt = "Yearly";
                }
                setInterestOption(intOpt);
                setMatDt(currentItem.getMatDate());
                setIssueDt(currentItem.getFdDate());
                setContractedRoi(formatter.format(Double.parseDouble(String.valueOf(currentItem.getRoi()))));
                setPrinAmount(currentItem.getPrinAmt());
                receiptNo = currentItem.getReceiptNo();
                if (currentStatus.equals("Mature Withdrawal")) {
                    penalityFlag = "true";
                    setFocusId("btnCompleted");
                } else {
                    penalityFlag = "false";
                    setFocusId("txtPenaltyApplication");
                }
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getPenalityApplication() {
        this.setMessage("");
        this.setNetContractedRoi("0.00");

        if ((oldAcNo == null) || (oldAcNo.equalsIgnoreCase(""))) {
            initializeControls();
            this.setMessage("Please Enter The Account No");
            return;
        }
        if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            initializeControls();
            this.setMessage("Please Enter The Proper Account No");
            return;
        }

        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (acInfo.equals("Account Does Not Exist")) {
            initializeControls();
            this.setMessage(acInfo);
            return;
        }
        if ((this.issueDt == null) || (this.issueDt.equalsIgnoreCase(""))) {
            this.setMessage("Please Select Table Data And Than get Penality Application.");
            return;
        }
        if ((this.matDt == null) || (this.matDt.equalsIgnoreCase(""))) {
            this.setMessage("Please Select Table Data And Than get Penality Application.");
            return;
        }
        if ((applicableRate == null) || (applicableRate.equalsIgnoreCase(""))) {
            this.setMessage("Please get Rt. Number Information");
            return;
        }
        Matcher applicableRateCheck = p.matcher(applicableRate);
        if (!applicableRateCheck.matches()) {
            this.setMessage("Please Enter Valid Applicable Rate.");
            return;
        }
        if ((penalityApplicable == null) || (penalityApplicable.equalsIgnoreCase(""))) {
            this.setMessage("Please Enter The penality Application");
            return;
        }
        Matcher penalityApplicableCheck = p.matcher(penalityApplicable);
        if (!penalityApplicableCheck.matches()) {
            this.setMessage("Please Enter Valid penality Application.");
            return;
        }
        try {
            if ((interestOption.equals("Monthly")) || (interestOption.equals("Quarterly"))) {
                flag = "True";
            }
            setRtNo(String.valueOf(currentItem.getVoucherNo()));
            String fdDts = currentItem.getFdDate();
            String dd = fdDts.substring(0, 2);
            String mm = fdDts.substring(3, 5);
            String yy = fdDts.substring(6, 10);
            String fdDates = yy + "" + mm + "" + dd;
            String matDts = currentItem.getMatDate();
            String dd1 = matDts.substring(0, 2);
            String mm1 = matDts.substring(3, 5);
            String yy1 = matDts.substring(6, 10);
            String matDates = yy1 + "" + mm1 + "" + dd1;
            Double pAmt = currentItem.getPrinAmt();
            // String prAmt = String.valueOf(pAmt);
            float netConRoi = Float.parseFloat(applicableRate) - Float.parseFloat(penalityApplicable);
            if (netConRoi < 0) {
                setNetContractedRoi("0.00");
            } else {
                /**
                 * *Changed by dinesh**
                 */
                setNetContractedRoi(formatter.format(Double.parseDouble(String.valueOf(netConRoi))));
                float contractRoi = Float.parseFloat(this.getContractedRoi());
                if (netConRoi > contractRoi) {
                    this.setMessage("Net contracted roi can not be greater than contracted roi");
                    return;
                }
                /*else {
                 setNetContractedRoi(formatter.format(Double.parseDouble(String.valueOf(netConRoi))));
                 }*/
                /**
                 * *end here**
                 */
            }
            String penality = tdCalMgmtRemote.cbsPenalityApply(fullAccNo, Float.parseFloat(rtNo), fdDates, matDates, getUserName(),
                    pAmt, applicableRate, penalityApplicable, interestOption, getOrgnBrCode(), custCategory, "", "");
            if (penality.equals("SORRY! Please Fill Correct Penality")) {
                initializeControls();
                this.setMessage(penality);
                return;
            } else if (penality.equals("SORRY! Penalty Can Not Be Greater Than Applicable ROI")) {
                initializeControls();
                this.setMessage(penality);
                return;
            } else {
                String[] values = null;
                String spliter = ": ";
                values = penality.split(spliter);

                setInterestPaid(formatter.format(Double.parseDouble(values[0])));
                setActualTotalInterest(formatter.format(Double.parseDouble(values[1])));
                setRemainingInterest(formatter.format(Double.parseDouble(values[2])));
                setTdsDeducted(formatter.format(Double.parseDouble(values[4])));
                setDeductForLastFinalFear(formatter.format(Double.parseDouble(values[3])));
                if (Double.parseDouble(values[5]) < 0) {
                    setTdsToBeDeducted("0");
                } else {
                    setTdsToBeDeducted(formatter.format(Double.parseDouble(values[5])));
                }
                setNetAmount(formatter.format(Double.parseDouble(values[6])));
                setPreviousBalanceAc(values[7]);
//                setOtherTdsToBeDeducted(formatter.format(Double.parseDouble(values[9])));
//                setTotalTdsToBeDeducted(formatter.format(Double.parseDouble(values[9]) + Double.parseDouble(values[5])));
//                
                setCloseActIntFinYear(formatter.format(Double.parseDouble(values[11])));
                setCloseActTdsToBeDeducted(formatter.format(Double.parseDouble(values[9])));
                setCloseActTdsDeducted(formatter.format(Double.parseDouble(values[10])));
                this.setMessage("");
            }

        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveAction() {
        if (function.equals("D")) {
            deleteRtDetails();
        } else {
            saveRtDetails();
        }
    }

    public void saveRtDetails() {
        this.setMessage("");
        if ((oldAcNo == null) || (oldAcNo.equalsIgnoreCase(""))) {
            initializeControls();
            this.setMessage("Please Enter The Account No");
            return;
        }
        if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            initializeControls();
            this.setMessage("Please Enter The Proper Account No");
            return;
        }
        try {
            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(fullAccNo);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                this.setMessage("Sorry! Other branch account no. is not allowed to proceed.");
                return;
            }
            if (acInfo.equals("Account Does Not Exist")) {
                initializeControls();
                this.setMessage(acInfo);
                return;
            }
            if ((this.issueDt == null) || (this.issueDt.equalsIgnoreCase(""))) {
                this.setMessage("Please Select Table Data And Than Complete.");
                return;
            }
            if ((this.matDt == null) || (this.matDt.equalsIgnoreCase(""))) {
                this.setMessage("Please Select Table Data And Than Complete.");
                return;
            }
            String fdDates = issueDt.substring(6, 10) + issueDt.substring(3, 5) + issueDt.substring(0, 2);
            String matDates = matDt.substring(6, 10) + matDt.substring(3, 5) + matDt.substring(0, 2);
            if (Float.parseFloat(netContractedRoi) > Float.parseFloat(contractedRoi)) {
                this.setMessage("Net contracted roi can not be greater than contracted roi");
                return;
            }
            float roi = Float.parseFloat(netContractedRoi);
            if (roi == 0) {
                roi = Float.parseFloat(contractedRoi);
            }
            if (currentStatus.equals("Mature Withdrawal")) {
                currentStatus = "MATURE";
            } else {
                currentStatus = "PREMATURE";
            }
            if ((fullAccNo == null) || (fullAccNo.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Account Number");
                return;
            }
            if ((rtNo == null) || (rtNo.equalsIgnoreCase(""))) {
                this.setMessage("Please Select Table");
                return;
            }
            if (!currentStatus.equals("MATURE")) {
                if ((penalityApplicable == null) || (penalityApplicable.equalsIgnoreCase(""))) {
                    this.setMessage("Please Enter Penality Application");
                    return;
                }
            }
            String prnAmt = String.valueOf(prinAmount);
            String result = "";
            if (function.equals("P")) {
                result = tdCalMgmtRemote.saveTdPaymentDetails(fullAccNo, Float.parseFloat(rtNo), receiptNo, interestOption, currentStatus, Double.parseDouble(prnAmt),
                        Double.parseDouble(remainingInterest), Double.parseDouble(tdsToBeDeducted), Double.parseDouble(tdsDeducted), Double.parseDouble(interestPaid),
                        fdDates, matDates, Double.parseDouble(netAmount), Float.parseFloat(penalityApplicable), Float.parseFloat(this.getContractedRoi()),
                        getUserName(), getOrgnBrCode(), Double.parseDouble(actualTotalInterest), Float.parseFloat(this.getApplicableRate()),
                        Float.parseFloat(this.getNetContractedRoi()), Double.parseDouble(closeActTdsToBeDeducted), Double.parseDouble(closeActTdsDeducted),
                        Double.parseDouble(closeActIntFinYear));
            } else {
                if (ftsPostRemote.isUserAuthorized(getUserName(), getOrgnBrCode())) {
                    if (this.getUserName().equals(enterBy)) {
                        setOldAcNo("");
                        result = "You can not authorize your own entry.";
                    } else {
                        if (!provision.equals("") && provision.toUpperCase().contains(interestOption.substring(0, 1).toUpperCase())) {
                            result = tdCalMgmtRemote.provisionCompleteAction(fullAccNo, Float.parseFloat(rtNo), interestOption, currentStatus, Float.parseFloat(prnAmt),
                                    Double.parseDouble(remainingInterest), Float.parseFloat(tdsToBeDeducted), Double.parseDouble(interestPaid), fdDates,
                                    matDates, Float.parseFloat(netAmount), Float.parseFloat(penalityApplicable), roi, enterBy, getOrgnBrCode(),
                                    Double.parseDouble(actualTotalInterest), "Y", getUserName());
                        } else {
                            result = tdCalMgmtRemote.completeButton(fullAccNo, Float.parseFloat(rtNo), interestOption, currentStatus, Float.parseFloat(prnAmt),
                                    Double.parseDouble(remainingInterest), Float.parseFloat(tdsToBeDeducted), Float.parseFloat(tdsDeducted),
                                    Double.parseDouble(interestPaid), fdDates, matDates, Double.parseDouble(netAmount), Float.parseFloat(penalityApplicable),
                                    roi, enterBy, getOrgnBrCode(), Double.parseDouble(actualTotalInterest), "Y", getUserName(), Float.parseFloat(closeActTdsToBeDeducted));

//                            result = autoTdRemote.completeButton(fullAccNo, Float.parseFloat(rtNo), interestOption, currentStatus, Float.parseFloat(prnAmt),
//                                    Double.parseDouble(remainingInterest), Float.parseFloat(tdsToBeDeducted), Float.parseFloat(tdsDeducted),
//                                    Double.parseDouble(interestPaid), fdDates, matDates, Double.parseDouble(netAmount), Float.parseFloat(penalityApplicable),
//                                    roi, getUserName(), getOrgnBrCode(), Double.parseDouble(actualTotalInterest), "Y", getUserName(), Float.parseFloat(closeActTdsToBeDeducted), "", "");
                        }
                    }
                } else {
                    setOldAcNo("");
                    result = "You are not authorized person for verifing this detail.";
                }
            }
            this.setMessage(result);
            initializeControls();
            setFullAccNo("");
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void deleteRtDetails() {
        try {
            String result = tdCalMgmtRemote.deleteTdPaymentDetails(fullAccNo, Float.parseFloat(rtNo));
            this.setMessage(result);
            initializeControls();
            setFullAccNo("");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void initializeControls() {
        try {
            setRtNo("");
            setContractedRoi("0.00");
            setIssueDt("");
            setMatDt("");
            setInterestOption("");
            setCurrentStatus("");
            setApplicableRate("0.00");
            setPrinAmount(0.0d);
            setInterestPaid("0.00");
            setRemainingInterest("0.00");
            setActualTotalInterest("0.00");
            setNetAmount("0.00");
            setTdsDeducted("0.00");
            setTdsToBeDeducted("0.00");
            setDeductForLastFinalFear("0.00");
            setNoOfActiveDays("");
            setPreviousBalanceAc("");
            setNetContractedRoi("0.00");
            setCustName("");
            setOperationMode("");
            setJtUgName("");

            setCloseActIntFinYear("0.00");
            setCloseActTdsToBeDeducted("0.00");
            setCloseActTdsDeducted("0.00");

            payment = new ArrayList<TdPaymentGrid>();
            pendingAcNoList = new ArrayList<SelectItem>();
            pendingRtNoList = new ArrayList<SelectItem>();
            setFunction("P");
            setPendingAcNo("");
            setPendingRtNo("");
            setDisableFlag(false);
            setInputDisFlag("none");
            setBtnLabel("Save");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void pageRefresh() {
        try {
            this.setMessage("");
            setRtNo("");
            setContractedRoi("0.00");
            setIssueDt("");
            setMatDt("");
            setInterestOption("");
            setCurrentStatus("");
            setApplicableRate("0.00");
            setPrinAmount(0.0d);
            setInterestPaid("0.00");
            setRemainingInterest("0.00");
            setActualTotalInterest("0.00");
            setNetAmount("0.00");
            setTdsDeducted("0.00");
            setTdsToBeDeducted("0.00");
            setDeductForLastFinalFear("0.00");
            setNoOfActiveDays("");
            setPreviousBalanceAc("");
            setPenalityApplicable("0");
            setNetContractedRoi("0.00");
            setCustName("");
            setOperationMode("");
            setJtUgName("");
            setFullAccNo("");
            setOldAcNo("");

            setCloseActIntFinYear("0.00");
            setCloseActTdsToBeDeducted("0.00");
            setCloseActTdsDeducted("0.00");

            payment = new ArrayList<TdPaymentGrid>();
            pendingAcNoList = new ArrayList<SelectItem>();
            pendingRtNoList = new ArrayList<SelectItem>();
            setFunction("P");
            setPendingAcNo("");
            setPendingRtNo("");
            setDisableFlag(false);
            setInputDisFlag("none");
            setBtnLabel("Save");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String btnExit_action() {
        this.setMessage("");
        pageRefresh();
        return "case1";
    }

    public void checkApplicableRate() {
        this.setMessage("");
        try {
            float contractRoi = Float.parseFloat(this.getContractedRoi());
            float applicableRoi = Float.parseFloat(this.getApplicableRate());
            if (applicableRoi > contractRoi) {
                this.setMessage("Applicable rate can not be greater than contracted roi");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
