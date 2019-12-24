package com.cbs.web.controller.td;

import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.AutoTermDepositRenewalRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.web.pojo.other.TdGrid;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.facade.txn.TdAuthorizationManagementFacadeRemote;
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
import javax.faces.model.SelectItem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class TermDepositRenewal extends BaseBean {

    private String message;
    private String provision;
    private String acctType;
    private String acctNo;
    private String agcode;
    private String accountNo;
    private String custName;
    private String renewedTdDate;
    private String recieptNo;
    private String renewedMatDate;
    private String tdsDeductedForLastFinyear;
    private String roi;
    private String tdsDeducted;
    private String renewedMatAmt;
    private String tdsToBeDeducted;
    private String renewalAccount;
    private String renewalAmount;
    private String ddRecieptNo;
    private String interestOption;
    private String applicableRoi;
    private String year;
    private String month;
    private String days;
    private String stAcctNo;
    private List<TdGrid> tdTable;
    private int currentRow;
    private TdGrid currentItem = new TdGrid();
    private List<SelectItem> ddInterestOption;
    private List<SelectItem> ddApplicableRoi;
    private List<SelectItem> ddRenewalAcct;
    private List<SelectItem> ddRecieptno;
    //private List<SelectItem> ddAcctType;
    private String series;
    private boolean seriesVisisble;
    private List<SelectItem> ddSeries;
    private String rtNoHide;
    private String intopthide;
    private String renewalPeriodhide;
    private String roiHide;
    private String prinAmountHide;
    private float TdsDeductedCal;
    private float TdsDeductedLastFinYearCal;
    private float TdsToBEDeductedCal;
    private float totalIntRateCal;
    private float IntPaidCal;
    private float balint;
    private float prinAmt;
    private float amt;
    private float matpre;
    private float rAmt;
    private String closeActTdsToBeDeducted;
    private String closeActTdsDeducted;
    private String closeActIntFinYear;
    private boolean bFlag;
    private String acctNature;
    private String tdReceipt;
    private String tdDayMth;
    private String tdDayCum;
    private boolean seriesVisible;
    private boolean recieptNoDisabled;
    private boolean stxVisible;
    private boolean savedis;
    private boolean lblDisable;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#0.00");
    private boolean disableAll;
    private final String jndiHomeNameTdCal = "TermDepositeCalculationManagementFacade";
    private TermDepositeCalculationManagementFacadeRemote tdCalMgmtRemote = null;
    private final String jndiHomeNameTdRcpt = "TdReceiptManagementFacade";
    private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;
    private PostalDetailFacadeRemote postalRemote = null;
    private AutoTermDepositRenewalRemote autoRenewRemote;
    private final String jndiHomeName = "TdAuthorizationManagementFacade";
    private TdAuthorizationManagementFacadeRemote tdAuthRemote = null;
    String batchNo;
    String remainInt;
    Date curDt = new Date();
    String tempIntOpt;
    String acNo;
    String newReceiptNo;
    String newVoucherNo, acNoLen;
    private String function;
    private List<SelectItem> functionList;
    private String inputDisFlag = "none";
    private String pendingAcNo;
    private List<SelectItem> pendingAcNoList;
    private String focusId;
    private String pendingRtNo;
    private List<SelectItem> pendingRtNoList;
    private String acNoDisFlag;
    private String btnAllDisFlag;
    private String btnLabel = "Renew";
    private String enterBy;
    private String stYear;
    private String endYear;
    //private String balIntVisible;
    private String remBalInt;
    private static final Object LOCK = new Object();

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

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
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

    public String getAcNoDisFlag() {
        return acNoDisFlag;
    }

    public void setAcNoDisFlag(String acNoDisFlag) {
        this.acNoDisFlag = acNoDisFlag;
    }

    public String getBtnAllDisFlag() {
        return btnAllDisFlag;
    }

    public void setBtnAllDisFlag(String btnAllDisFlag) {
        this.btnAllDisFlag = btnAllDisFlag;
    }

    public String getBtnLabel() {
        return btnLabel;
    }

    public void setBtnLabel(String btnLabel) {
        this.btnLabel = btnLabel;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

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

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getNewReceiptNo() {
        return newReceiptNo;
    }

    public void setNewReceiptNo(String newReceiptNo) {
        this.newReceiptNo = newReceiptNo;
    }

    public String getNewVoucherNo() {
        return newVoucherNo;
    }

    public void setNewVoucherNo(String newVoucherNo) {
        this.newVoucherNo = newVoucherNo;
    }

    public boolean isLblDisable() {
        return lblDisable;
    }

    public void setLblDisable(boolean lblDisable) {
        this.lblDisable = lblDisable;
    }

    public boolean isDisableAll() {
        return disableAll;
    }

    public void setDisableAll(boolean disableAll) {
        this.disableAll = disableAll;
    }

    public boolean isSavedis() {
        return savedis;
    }

    public void setSavedis(boolean savedis) {
        this.savedis = savedis;
    }

    public boolean isStxVisible() {
        return stxVisible;
    }

    public void setStxVisible(boolean stxVisible) {
        this.stxVisible = stxVisible;
    }

    public boolean isRecieptNoDisabled() {
        return recieptNoDisabled;
    }

    public void setRecieptNoDisabled(boolean recieptNoDisabled) {
        this.recieptNoDisabled = recieptNoDisabled;
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

    public boolean isSeriesVisible() {
        return seriesVisible;
    }

    public void setSeriesVisible(boolean seriesVisible) {
        this.seriesVisible = seriesVisible;
    }

    public String getIntopthide() {
        return intopthide;
    }

    public void setIntopthide(String intopthide) {
        this.intopthide = intopthide;
    }

    public String getPrinAmountHide() {
        return prinAmountHide;
    }

    public void setPrinAmountHide(String prinAmountHide) {
        this.prinAmountHide = prinAmountHide;
    }

    public String getRenewalPeriodhide() {
        return renewalPeriodhide;
    }

    public void setRenewalPeriodhide(String renewalPeriodhide) {
        this.renewalPeriodhide = renewalPeriodhide;
    }

    public String getRoiHide() {
        return roiHide;
    }

    public void setRoiHide(String roiHide) {
        this.roiHide = roiHide;
    }

    public String getRtNoHide() {
        return rtNoHide;
    }

    public void setRtNoHide(String rtNoHide) {
        this.rtNoHide = rtNoHide;
    }

    public List<SelectItem> getDdSeries() {
        return ddSeries;
    }

    public void setDdSeries(List<SelectItem> ddSeries) {
        this.ddSeries = ddSeries;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public boolean isSeriesVisisble() {
        return seriesVisisble;
    }

    public void setSeriesVisisble(boolean seriesVisisble) {
        this.seriesVisisble = seriesVisisble;
    }

    public String getStAcctNo() {
        return stAcctNo;
    }

    public void setStAcctNo(String stAcctNo) {
        this.stAcctNo = stAcctNo;
    }

    public List<SelectItem> getDdApplicableRoi() {
        return ddApplicableRoi;
    }

    public void setDdApplicableRoi(List<SelectItem> ddApplicableRoi) {
        this.ddApplicableRoi = ddApplicableRoi;
    }

    public List<SelectItem> getDdInterestOption() {
        return ddInterestOption;
    }

    public void setDdInterestOption(List<SelectItem> ddInterestOption) {
        this.ddInterestOption = ddInterestOption;
    }

    public List<SelectItem> getDdRecieptno() {
        return ddRecieptno;
    }

    public void setDdRecieptno(List<SelectItem> ddRecieptno) {
        this.ddRecieptno = ddRecieptno;
    }

    public List<SelectItem> getDdRenewalAcct() {
        return ddRenewalAcct;
    }

    public void setDdRenewalAcct(List<SelectItem> ddRenewalAcct) {
        this.ddRenewalAcct = ddRenewalAcct;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getApplicableRoi() {
        return applicableRoi;
    }

    public void setApplicableRoi(String applicableRoi) {
        this.applicableRoi = applicableRoi;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDdRecieptNo() {
        return ddRecieptNo;
    }

    public void setDdRecieptNo(String ddRecieptNo) {
        this.ddRecieptNo = ddRecieptNo;
    }

    public String getInterestOption() {
        return interestOption;
    }

    public void setInterestOption(String interestOption) {
        this.interestOption = interestOption;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(String recieptNo) {
        this.recieptNo = recieptNo;
    }

    public String getRenewalAccount() {
        return renewalAccount;
    }

    public void setRenewalAccount(String renewalAccount) {
        this.renewalAccount = renewalAccount;
    }

    public String getRenewalAmount() {
        return renewalAmount;
    }

    public void setRenewalAmount(String renewalAmount) {
        this.renewalAmount = renewalAmount;
    }

    public String getRenewedMatAmt() {
        return renewedMatAmt;
    }

    public void setRenewedMatAmt(String renewedMatAmt) {
        this.renewedMatAmt = renewedMatAmt;
    }

    public String getRenewedMatDate() {
        return renewedMatDate;
    }

    public void setRenewedMatDate(String renewedMatDate) {
        this.renewedMatDate = renewedMatDate;
    }

    public String getRenewedTdDate() {
        return renewedTdDate;
    }

    public void setRenewedTdDate(String renewedTdDate) {
        this.renewedTdDate = renewedTdDate;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getTdsDeducted() {
        return tdsDeducted;
    }

    public void setTdsDeducted(String tdsDeducted) {
        this.tdsDeducted = tdsDeducted;
    }

    public String getTdsDeductedForLastFinyear() {
        return tdsDeductedForLastFinyear;
    }

    public void setTdsDeductedForLastFinyear(String tdsDeductedForLastFinyear) {
        this.tdsDeductedForLastFinyear = tdsDeductedForLastFinyear;
    }

    public String getTdsToBeDeducted() {
        return tdsToBeDeducted;
    }

    public void setTdsToBeDeducted(String tdsToBeDeducted) {
        this.tdsToBeDeducted = tdsToBeDeducted;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAgcode() {
        return agcode;
    }

    public void setAgcode(String agcode) {
        this.agcode = agcode;
    }

    public TdGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TdGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TdGrid> getTdTable() {
        return tdTable;
    }

    public void setTdTable(List<TdGrid> tdTable) {
        this.tdTable = tdTable;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRemainInt() {
        return remainInt;
    }

    public void setRemainInt(String remainInt) {
        this.remainInt = remainInt;
    }

    public String getTempIntOpt() {
        return tempIntOpt;
    }

    public void setTempIntOpt(String tempIntOpt) {
        this.tempIntOpt = tempIntOpt;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

//    public String getBalIntVisible() {
//        return balIntVisible;
//    }
//
//    public void setBalIntVisible(String balIntVisible) {
//        this.balIntVisible = balIntVisible;
//    }
    public String getRemBalInt() {
        return remBalInt;
    }

    public void setRemBalInt(String remBalInt) {
        this.remBalInt = remBalInt;
    }

    public TermDepositRenewal() {
        try {
            this.setMessage("");
            setAgcode("01");
            tdCalMgmtRemote = (TermDepositeCalculationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdCal);
            tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdRcpt);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            postalRemote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup("PostalDetailFacade");
            autoRenewRemote = (AutoTermDepositRenewalRemote) ServiceLocator.getInstance().lookup("AutoTermDepositRenewal");
            tdAuthRemote = (TdAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setAcNoLen(getAcNoLength());
            // getDropDownList();
            getTdReciept();
            setfdGlobal();
            setStxVisible(true);
            setSavedis(true);
            setDisableAll(true);
            setRecieptNoDisabled(true);
            setLblDisable(true);
            setYear("0");
            setMonth("0");
            setDays("0");
            setRoi("0.0");
            setAcNoDisFlag("true");
            setBtnAllDisFlag("true");
            ddInterestOption = new ArrayList<SelectItem>();
            ddInterestOption.add(new SelectItem("S", "Simple"));
            ddInterestOption.add(new SelectItem("M", "Monthly"));
            ddInterestOption.add(new SelectItem("Q", "Quarterly"));
            ddInterestOption.add(new SelectItem("C", "Cumulative"));
            ddInterestOption.add(new SelectItem("Y", "Yearly"));

            ddApplicableRoi = new ArrayList<SelectItem>();
            ddApplicableRoi.add(new SelectItem("--Select--"));
            ddApplicableRoi.add(new SelectItem("W.e.f Of ROI"));
            ddApplicableRoi.add(new SelectItem("Less ROI"));

            ddRenewalAcct = new ArrayList<SelectItem>();
            // ddRenewalAcct.add(new SelectItem("--Select--"));
            ddRenewalAcct.add(new SelectItem("Existing"));
            //  ddRenewalAcct.add(new SelectItem("New"));

            ddRecieptno = new ArrayList<SelectItem>();
            ddRecieptno.add(new SelectItem("New"));
            ddRecieptno.add(new SelectItem("Existing"));

            getOnLoadDroupDownValue();
//            this.balIntVisible = "none";

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getOnLoadDroupDownValue() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("R", "Renewal"));
            functionList.add(new SelectItem("V", "Verify"));
            functionList.add(new SelectItem("D", "Delete"));
            setFunction("R");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void changeFunction() {
        this.setMessage("");
        setRenewalAmount("");
        setTdsDeductedForLastFinyear("");
        setTdsToBeDeducted("");
        setTdsDeducted("");
        setRenewedMatAmt("");
        setRoi("");
        setRenewedMatDate("");
        setYear("");
        setMonth("");
        setDays("");
        setAccountNo("");
        setRecieptNo("");
        setRenewedTdDate("");
        setAcctNo("");
        setStAcctNo("");
        setCloseActIntFinYear("");
        setCloseActTdsDeducted("");
        setCloseActTdsToBeDeducted("");
        if (function.equals("R")) {
            setFocusId("txtaccountDetails");
            setBtnLabel("Renew");
            setInputDisFlag("none");
            setBtnAllDisFlag("false");
            setAcNoDisFlag("false");
//            this.balIntVisible = "";
        } else if (function.equals("V")) {
            setFocusId("ddPAcNo");
            setBtnLabel("Verify");
            setInputDisFlag("");
            setBtnAllDisFlag("true");
            setAcNoDisFlag("true");
            tdTable = new ArrayList<TdGrid>();
            getUnAuthAcNo();
//            this.balIntVisible = "";
        } else {
            setFocusId("ddPAcNo");
            setBtnLabel("Delete");
            setInputDisFlag("");
            setBtnAllDisFlag("true");
            setAcNoDisFlag("true");
            tdTable = new ArrayList<TdGrid>();
            getUnAuthAcNo();
//            this.balIntVisible = "";
        }
    }

    public void getUnAuthAcNo() {
        try {
            this.setMessage("");
            pendingAcNoList = new ArrayList<SelectItem>();
            List dataList = tdAuthRemote.accountNoList(this.getOrgnBrCode());
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
            this.setAccountNo(getPendingAcNo());
            List dataList = tdAuthRemote.getUnAuthRecptNo(getPendingAcNo());
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
            List dataList = tdAuthRemote.getRenewedReceptDetails(getPendingAcNo(), Float.parseFloat(getPendingRtNo()));
            Vector ele = (Vector) dataList.get(0);
            setAcctNo(ele.get(0).toString());
            setCustName(ftsPostRemote.getCustName(ele.get(0).toString()));
            setRenewedTdDate(ele.get(1).toString());
            setRenewedMatDate(ele.get(2).toString());
            setTdsToBeDeducted(ele.get(3).toString());
            setYear(ele.get(4).toString());
            setMonth(ele.get(5).toString());
            setDays(ele.get(6).toString());
            setRtNoHide(ele.get(7).toString());
            setInterestOption(ele.get(8).toString());
            tdDayMth = ele.get(9).toString();
            tdDayCum = ele.get(10).toString();
            setRenewalAmount(ele.get(11).toString());
            setRecieptNo(ele.get(12).toString());
            seriesVisible = true;
            stxVisible = false;
            ddSeries = new ArrayList<SelectItem>();
            ddSeries.add(new SelectItem(ele.get(13).toString()));
            setSeries(ele.get(13).toString());
            setDdRecieptNo(ele.get(14).toString());
            enterBy = ele.get(15).toString();
            rAmt = Float.parseFloat(ele.get(16).toString());
            balint = Float.parseFloat(ele.get(17).toString());
            setRenewalAccount(ele.get(18).toString());
            setRenewedMatAmt(ele.get(19).toString());
            matpre = Float.parseFloat(ele.get(20).toString());
            stYear = ele.get(21).toString();
            endYear = ele.get(22).toString();
            setRoi(ele.get(23).toString());
            setTdsDeductedForLastFinyear(ele.get(24).toString());
            totalIntRateCal = Float.parseFloat(ele.get(25).toString());
            setTdsDeducted(ele.get(26).toString());
            setCloseActTdsToBeDeducted(ele.get(27).toString());
            setCloseActTdsDeducted(ele.get(28).toString());
            setCloseActIntFinYear(ele.get(29).toString());
            setApplicableRoi(ele.get(30).toString());
            setFocusId("btnSave");
            savedis = false;
//            this.balIntVisible = "";
            remBalInt = autoRenewRemote.tdRenewalAuthBalInt(ele.get(0).toString(), Double.parseDouble(ele.get(3).toString()),
                    Float.parseFloat(ele.get(7).toString()), ele.get(8).toString(), ele.get(7).toString(),
                    Float.parseFloat(ele.get(17).toString()), Float.parseFloat(ele.get(20).toString()), Double.parseDouble(ele.get(24).toString()),
                    totalIntRateCal, Double.parseDouble(ele.get(26).toString()), Double.parseDouble(ele.get(27).toString()), "C");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

//    public void getDropDownList() {
//        try {
//            List provisionList = tdRcptMgmtRemote.getProvisionFlag();
//            if (!provisionList.isEmpty()) {
//                Vector ele = (Vector) provisionList.get(0);
//                if (ele.get(0).toString().equals("28")) {
//                    provision = true;
//                } else {
//                    provision = false;
//                }
//            }
//        } catch (Exception ex) {
//            this.setMessage(ex.getMessage());
//        }
//    }
    public void getTdReciept() {
        try {
            List resultList = new ArrayList();
            resultList = tdCalMgmtRemote.getTdRecieptSeq();
            if (resultList.size() > 0) {
                Vector vctReciept = (Vector) resultList.get(0);
                tdReceipt = vctReciept.get(0).toString();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getTableRenewal() {
        try {
            if (acctNo == null || acctNo.equalsIgnoreCase("")) {
                stAcctNo = "";
            } else {
                //if (acctNo.length() < 12) {
                if (!this.acctNo.equalsIgnoreCase("") && ((this.acctNo.length() != 12) && (this.acctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setMessage("Please fill proper account no.");
                    return;
                }
                stAcctNo = ftsPostRemote.getNewAccountNumber(acctNo);

                List accountDetails = tdRcptMgmtRemote.accountStatus(stAcctNo);
                if (accountDetails.isEmpty()) {
                    this.setMessage("A/c no. does not exist. ");
                    return;
                } else {
                    for (int i = 0; i < accountDetails.size(); i++) {
                        Vector ele = (Vector) accountDetails.get(i);
                        String accStat = ele.get(1).toString();
                        if (accStat.equalsIgnoreCase("15")) {
                            this.setMessage("A/c is Deaf Marked. ");
                            tdTable = new ArrayList<TdGrid>();
                            setRecieptNo("");
                            setRenewedTdDate("");
                            setInterestOption("");
                            setAccountNo("");
                            setCustName("");
                            setRecieptNo("");
                            setRenewedTdDate("");
                            setInterestOption("");
                            setYear("");
                            setMonth("");
                            setDays("");
                            setRenewedMatDate("");
                            setTdsDeducted("");
                            setTdsDeductedForLastFinyear("");
                            setTdsToBeDeducted("");
                            setRenewalAmount("");
                            setRenewedMatAmt("");
                            setRoi("");
                            setStAcctNo("");
                            return;
                        }
                    }
                }
            }
            if (!stAcctNo.equals("")) {
                String checkBrnCode = ftsPostRemote.getCurrentBrnCode(stAcctNo);
                if (!checkBrnCode.equals(getOrgnBrCode())) {
                    this.setMessage("Sorry! Other branch account no. is not allowed to proceed.");
                    tdTable = new ArrayList<TdGrid>();
                    setRecieptNo("");
                    setRenewedTdDate("");
                    setInterestOption("");
                    setAccountNo("");
                    setCustName("");
                    setRecieptNo("");
                    setRenewedTdDate("");
                    setInterestOption("");
                    setYear("");
                    setMonth("");
                    setDays("");
                    setRenewedMatDate("");
                    setTdsDeducted("");
                    setTdsDeductedForLastFinyear("");
                    setTdsToBeDeducted("");
                    setRenewalAmount("");
                    return;
                }
            }
            disableAll = false;
            recieptNoDisabled = false;
            this.setMessage("");
            setRecieptNo("");
            setRenewedTdDate("");
            setInterestOption("");
            setAccountNo("");
            setCustName("");
            setRecieptNo("");
            setRenewedTdDate("");
            setInterestOption("");
            setYear("");
            setMonth("");
            setDays("");
            setRenewedMatDate("");
            setTdsDeducted("");
            setTdsDeductedForLastFinyear("");
            setTdsToBeDeducted("");
            setRenewalAmount("");
            tdTable = new ArrayList<TdGrid>();
            Date date = new Date();
            String Date = ymd.format(date);

            List resultLt = new ArrayList();
            resultLt = tdCalMgmtRemote.tableAccountWise(stAcctNo, Date, getOrgnBrCode());

            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    TdGrid rd = new TdGrid();
                    rd.setPrintamt(formatter.format(Double.parseDouble(ele.get(0).toString())));

                    rd.setRecieptNo(ele.get(1).toString());
                    rd.setAcno(ele.get(2).toString());
                    String fdate = (ele.get(3).toString());
                    String w1 = fdate.substring(0, 4);
                    String w2 = fdate.substring(5, 7);
                    String w3 = fdate.substring(8, 10);
                    String issueDt = w3 + "/" + w2 + "/" + w1;
                    rd.setTdDate(issueDt);

                    rd.setRenew(ele.get(4).toString());
                    String matDt = (ele.get(5).toString());
                    String matDt1 = matDt.substring(0, 4);
                    String matDt2 = matDt.substring(5, 7);
                    String matDt3 = matDt.substring(8, 10);
                    String matDate = matDt3 + "/" + matDt2 + "/" + matDt1;

                    rd.setMatDt(matDate);
                    rd.setIntopt(ele.get(6).toString());
                    rd.setRoi(ele.get(7).toString());
                    rd.setYears(ele.get(8).toString());
                    rd.setMonth(ele.get(9).toString());
                    rd.setDays(ele.get(10).toString());

                    rd.setRenewalPeriod(ele.get(11).toString());
                    rd.setControlNo(ele.get(12).toString());
                    rd.setRtNo(ele.get(13).toString());
                    String payDt = (ele.get(14).toString());
                    String payDt1 = payDt.substring(0, 4);
                    String payDt2 = payDt.substring(5, 7);
                    String payDt3 = payDt.substring(8, 10);
                    String payDate = payDt3 + "/" + payDt2 + "/" + payDt1;
                    rd.setPayDate(payDate);
                    tdTable.add(rd);
                }
            } else {
                this.setMessage("Records Does Not Exist Corresponding To This Account No.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void select() {
        try {
            savedis = false;
            this.setMessage("");
            setAccountNo("");
            setRecieptNo("");
            setRenewedTdDate("");
            setInterestOption("");
            setRoi("0.0");
            setRenewedMatAmt("");
            setRenewedMatDate("");
            setAccountNo(currentItem.getAcno());
            setCustName(" " + ftsPostRemote.getCustName(currentItem.getAcno()));
            setRecieptNo(currentItem.getRecieptNo());

            int renewD = postalRemote.getCodeByReportName("RENEWED_DT_FLAG");
//            if(renewD == 0){
//                setRenewedTdDate(currentItem.getMatDt());            
//            }else if(renewD == 1){
//                setRenewedTdDate(sdf.format(curDt));
//            }

            int tDays = postalRemote.getCodeByReportName("TD_RENEWAL_DAYS");
            //String renewedTdDate = "";
            if (renewD == 0) {
                setRenewedTdDate(currentItem.getMatDt());
            } else {
                //long dtDiff = CbsUtil.dayDiff(ymd.parse(CbsUtil.dateAdd(ymd.format(sdf.parse(currentItem.getMatDt())), 1)), ymd.parse(CbsUtil.dateAdd(ymd.format(new Date()), -1)));
                //long dtDiff = CbsUtil.dayDiff(ymd.parse(CbsUtil.dateAdd(ymd.format(sdf.parse(currentItem.getMatDt())), 1)), new Date());
                String frDate = ymd.format(sdf.parse(currentItem.getMatDt()));
                long dtDiff = ftsPostRemote.dateDiff(frDate, ymd.format(new Date()));

                if (dtDiff > tDays) {
                    setRenewedTdDate(sdf.format(curDt));
                } else {
                    setRenewedTdDate(currentItem.getMatDt());
                }
            }

            String productCode = tdRcptMgmtRemote.getProductCode(currentItem.getAcno());
            Map<String, String> intOptMap = CbsUtil.getIntOption(productCode);
            ddInterestOption = new ArrayList<SelectItem>();
            for (Map.Entry<String, String> entry : intOptMap.entrySet()) {
                ddInterestOption.add(new SelectItem(entry.getKey(), entry.getValue()));
            }
            tempIntOpt = currentItem.getIntopt();
            setInterestOption(currentItem.getIntopt());   //for  dual purpose intopthide;
            setYear(currentItem.getYears());
            setMonth(currentItem.getMonth());
            setDays(currentItem.getDays());
            setRtNoHide(currentItem.getRtNo());
            setRenewalPeriodhide(currentItem.getRenewalPeriod());
            setRoiHide(currentItem.getRoi());
            setPrinAmountHide(currentItem.getPrintamt());
            matDate();
            renewalAmount();
            setAcctNature();
            provision = tdRcptMgmtRemote.getProvApplyFlag(currentItem.getAcno());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void visibleSeries() {
        seriesVisisble = true;
    }

    public void setYears() {
    }

    public void setMonths() {
    }

    public void setDays() {
        if (roi.equals(null) || roi.equals("")) {
            setRoi("0.0");
        }
    }

    public void matDate() {
        this.setMessage("");
        try {
            if (validation().equalsIgnoreCase("false")) {
                return;
            }
            String matYears = CbsUtil.yearAdd(ymd.format(sdf.parse(renewedTdDate)), Integer.parseInt(year));
            String tmpRs = CbsUtil.monthAdd(matYears, Integer.parseInt(month));
            String dayResult = CbsUtil.dateAdd(tmpRs, Integer.parseInt(days));
            // String result = tdCalMgmtRemote.GetMatDate(renewedTdDate, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(days));
            this.setRenewedMatDate(sdf.format(ymd.parse(dayResult)));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void rateOfInt() {
        this.setMessage("");
        try {
            if (validation().equalsIgnoreCase("false")) {
                return;
            }
//            String result = tdCalMgmtRemote.GetMatDate(renewedTdDate, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(days));
//            this.setRenewedMatDate(result);
            String matYears = CbsUtil.yearAdd(ymd.format(sdf.parse(renewedTdDate)), Integer.parseInt(year));
            String tmpRs = CbsUtil.monthAdd(matYears, Integer.parseInt(month));
            String dayResult = CbsUtil.dateAdd(tmpRs, Integer.parseInt(days));
            this.setRenewedMatDate(sdf.format(ymd.parse(dayResult)));

            Float appRoi = tdCalMgmtRemote.tdApplicableROIFdRenewal(this.accountNo, this.applicableRoi, Float.parseFloat(renewalAmount), ymd.format(sdf.parse(renewedMatDate)), ymd.format(sdf.parse(renewedTdDate)), ymd.format(curDt));
            this.setRoi(appRoi.toString());

//            float renewIntAmount = tdCalMgmtRemote.tdRenewalFdInterest(this.interestOption, Float.parseFloat(renewalAmount), ymd.format(sdf.parse(renewedMatDate)), ymd.format(sdf.parse(renewedTdDate)), Float.parseFloat(roi), this.year, this.month, this.days, this.getOrgnBrCode());
            String rA = tdRcptMgmtRemote.orgFDInterest(this.interestOption, Float.parseFloat(roi), ymd.format(sdf.parse(renewedTdDate)), ymd.format(sdf.parse(renewedMatDate)), Double.parseDouble(renewalAmount), this.year + "Years" + this.month + "Months" + this.days + "Days", this.getOrgnBrCode());
            float renewIntAmount = Float.parseFloat(rA);
            this.setRenewedMatAmt(formatter.format(Double.parseDouble(Float.toString(Math.round(Float.parseFloat(renewalAmount) + renewIntAmount)))));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void matAmt() {
        this.setMessage("");
        if (validation().equalsIgnoreCase("false")) {
            return;
        }
        rateOfInt();
        matDate();

        if (roi.equalsIgnoreCase("") || roi == null) {
            setRoi("0.0");
        } else if (Double.parseDouble(roi) > 25.0) {
            setRoi("0.0");
        }
        validationPeriod();
        try {
//            float renewIntAmount = tdCalMgmtRemote.tdRenewalFdInterest(this.interestOption, Float.parseFloat(renewalAmount), ymd.format(sdf.parse(renewedMatDate)), ymd.format(sdf.parse(renewedTdDate)), Float.parseFloat(roi), this.year, this.month, this.days, this.getOrgnBrCode());
            String rA = tdRcptMgmtRemote.orgFDInterest(this.interestOption, Float.parseFloat(roi), ymd.format(sdf.parse(renewedTdDate)), ymd.format(sdf.parse(renewedMatDate)), Double.parseDouble(renewalAmount), this.year + "Years" + this.month + "Months" + this.days + "Days", this.getOrgnBrCode());
            float renewIntAmount = Float.parseFloat(rA);
            this.setRenewedMatAmt(formatter.format(Double.parseDouble(Float.toString(Math.round(Float.parseFloat(renewalAmount) + renewIntAmount)))));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void renewalAmount() {
        this.setMessage("");
        int tdcumPrematureFlag = 0;
        try {

            List<Double> resultList = autoRenewRemote.tdGlobal(accountNo, Float.parseFloat(rtNoHide), "True", Float.parseFloat(roiHide), tdcumPrematureFlag, year, month, days, "");

            setTdsDeducted(formatter.format(Double.parseDouble(resultList.get(0).toString())));
            TdsDeductedCal = Float.parseFloat(tdsDeducted);

            setTdsDeductedForLastFinyear(formatter.format(Double.parseDouble(resultList.get(1).toString())));
            TdsDeductedLastFinYearCal = Float.parseFloat(tdsDeductedForLastFinyear);

            float tdsde = Float.parseFloat(resultList.get(2).toString());
            setTdsToBeDeducted(formatter.format(Double.parseDouble(Float.toString(Math.round(tdsde)))));
            TdsToBEDeductedCal = Float.parseFloat(tdsToBeDeducted);

            totalIntRateCal = Float.parseFloat(resultList.get(3).toString());

            IntPaidCal = Float.parseFloat(resultList.get(4).toString());

            setCloseActTdsToBeDeducted(formatter.format(Double.parseDouble(resultList.get(5).toString())));
            setCloseActTdsDeducted(formatter.format(Double.parseDouble(resultList.get(6).toString())));
            setCloseActIntFinYear(formatter.format(Double.parseDouble(resultList.get(7).toString())));
            if (Float.parseFloat(closeActTdsToBeDeducted) < 0) {  // Software Bug #37389 as per Dhiru Sir
                closeActTdsToBeDeducted = "0";
            }

            balint = totalIntRateCal - IntPaidCal;
            prinAmt = Float.parseFloat(prinAmountHide);
            if (interestOption.equalsIgnoreCase("M") || interestOption.equalsIgnoreCase("Q") || interestOption.equalsIgnoreCase("Y")) {
                String provApp = tdRcptMgmtRemote.getProvApplyFlag(accountNo);
                if (!provApp.equals("") && provApp.toUpperCase().contains(interestOption)) {
                    amt = prinAmt + IntPaidCal + balint - TdsToBEDeductedCal;
                } else {
                    amt = prinAmt + balint - TdsToBEDeductedCal;
                }
                amt = amt - Float.parseFloat(closeActTdsToBeDeducted);
                setRenewalAmount(formatter.format(Double.parseDouble(Float.toString(Math.round(amt)))));
                if (amt < prinAmt) {
                    matpre = amt;
                    rAmt = amt;
                } else {
                    amt = prinAmt;
                    matpre = amt;
                    rAmt = amt;
                    setRenewalAmount(formatter.format(Double.parseDouble(Float.toString(Math.round(amt)))));
                }
            } else if (interestOption.equalsIgnoreCase("C") || interestOption.equalsIgnoreCase("S")) {
                amt = prinAmt + IntPaidCal + balint - TdsToBEDeductedCal;
                amt = amt - TdsDeductedCal;
                amt = amt - TdsDeductedLastFinYearCal;
                amt = amt - Float.parseFloat(closeActTdsToBeDeducted);
                setRenewalAmount(formatter.format(Double.parseDouble(Float.toString(Math.round(amt)))));
                matpre = amt;
                rAmt = amt;
            }

            remBalInt = autoRenewRemote.tdRenewalAuthBalInt(acctNo, Double.parseDouble(tdsToBeDeducted),
                    Float.parseFloat(rtNoHide), interestOption, rtNoHide,
                    balint, matpre, Double.parseDouble(resultList.get(1).toString()),
                    totalIntRateCal, Double.parseDouble(resultList.get(0).toString()), Double.parseDouble(resultList.get(5).toString()), "A");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validationPeriod() {
        if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (year.equalsIgnoreCase("0") || year.equalsIgnoreCase("")) && (month.equalsIgnoreCase("0") || month.equalsIgnoreCase(""))) {
            month = "0";
            days = "0";
            year = "0";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (month.equalsIgnoreCase("0") || month.equalsIgnoreCase("")) && (year.equalsIgnoreCase("0") || year.equalsIgnoreCase(""))) {
            month = "0";
            year = "0";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (!month.equalsIgnoreCase("0") || !month.equalsIgnoreCase("")) && (year.equalsIgnoreCase("0") || year.equalsIgnoreCase(""))) {
            days = "0";
            year = "0";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (month.equalsIgnoreCase("0") || month.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("0") || year.equalsIgnoreCase(""))) {
            month = "0";
            days = "0";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("0") || !year.equalsIgnoreCase("")) && (month.equalsIgnoreCase("0") || month.equalsIgnoreCase(""))) {
            month = "0";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (year.equalsIgnoreCase("0") || year.equalsIgnoreCase("")) && (!month.equalsIgnoreCase("0") || !month.equalsIgnoreCase(""))) {
            year = "0";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("0") || !year.equalsIgnoreCase("")) && (!month.equalsIgnoreCase("0") || !month.equalsIgnoreCase(""))) {
            days = "0";
        }
    }

    public void saveBtnAction() {
        if (function.equals("D")) {
            deleteRtDetails();
        } else {
            saveRenewBtnAction();
        }
    }

    public void deleteRtDetails() {
        try {
            String result = tdAuthRemote.deleteTdRenewalDetails(accountNo, Float.parseFloat(rtNoHide));
            clearAction();
            this.setMessage(result);
            batchNo = "";
            remainInt = "";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveRenewBtnAction() {
        try {
            batchNo = "";
            if (validation().equalsIgnoreCase("false")) {
                return;
            }

            if (renewalAccount.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select the Dropdown Renewal Account ");
                return;
            }

            if (tdsToBeDeducted.equalsIgnoreCase("")) {
                this.setMessage("Please Fill The Tds To Be Deducted ");
                return;
            }

            if (roi.equalsIgnoreCase("")) {
                this.setMessage("Please Fill The Rate Of Interest ");
                return;
            }
            if (renewedMatAmt.equalsIgnoreCase("")) {
                this.setMessage("Please Fill The Renewed Maturity Amount,Blur the Focus on Days To Achieve The Renewed Maturity Amount ");
                return;
            }
            if (renewalAmount.equalsIgnoreCase("")) {
                this.setMessage("Please Fill The Renewal Amount ");
                return;
            }
            if ((Float.parseFloat(renewalAmount) < prinAmt) || (Float.parseFloat(renewalAmount) > Math.round(matpre))) {
                this.setMessage("Renewal Amount Can't Be Less Than Prin Amount And Greater Than Previous Maturity Amount");
                return;
            }

            if (ddRecieptNo.equalsIgnoreCase("New")) {
                if (series == null || series.equalsIgnoreCase("")) {
                    this.setMessage("Series Can't be blank");
                    return;
                }
            }

            stYear = "01" + "/" + "01" + "/" + getTodayDate().substring(6);
            endYear = "31" + "/" + "12" + "/" + getTodayDate().substring(6);
            String result = "", alertMsg = "";

            String docMsg = acOpenFacadeRemote.getCustAcTdsDocDtl("", accountNo, "A");
            if (docMsg.equalsIgnoreCase("true")) {
                alertMsg = "Please Collect TDS Doc From This Customer";
            }

            if (function.equals("R")) {
                List list1 = acOpenFacadeRemote.getCustIdForRenewal(accountNo, recieptNo, rtNoHide);
                Vector vtr = (Vector) list1.get(0);
                String custidRenew = vtr.get(0).toString();
                List list2 = acOpenFacadeRemote.isCustIdForRenewalAuth(custidRenew, accountNo);
                if (!list2.isEmpty()) {
                    vtr = (Vector) list2.get(0);
                    String Acno = vtr.get(1).toString();
                    String ReceiptNo = vtr.get(2).toString();
                    String VoucherNo = vtr.get(3).toString();
                    this.setMessage("Please Verify the  A/c No : " + Acno + " ,ReceiptNo: " + ReceiptNo + " ,VoucherNo: " + VoucherNo + " of this customer first");
                    return;
                }
            }
            if (function.equals("R")) {
                synchronized (LOCK) {
                    result = tdAuthRemote.saveTdRenewalDetails(accountNo, ymd.format(sdf.parse(renewedTdDate)), ymd.format(sdf.parse(renewedMatDate)),
                            Double.parseDouble(tdsToBeDeducted), Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(days),
                            Double.parseDouble(rtNoHide), interestOption, Integer.parseInt(tdDayMth), Integer.parseInt(tdDayCum), Double.parseDouble(renewalAmount), Double.parseDouble(recieptNo), series, ddRecieptNo,
                            getUserName(), Math.round(Math.abs(rAmt)), balint, renewalAccount, Double.parseDouble(renewedMatAmt), Math.round(matpre), stYear, endYear, Double.parseDouble(roi),
                            Double.parseDouble(tdsDeductedForLastFinyear), totalIntRateCal, Double.parseDouble(tdsDeducted),
                            Double.parseDouble(closeActTdsToBeDeducted), Double.parseDouble(closeActTdsDeducted), Double.parseDouble(closeActIntFinYear), applicableRoi, getOrgnBrCode());
                }
                clearAction();
                this.setMessage(result);

            } else {
                if (ftsPostRemote.isUserAuthorized(getUserName(), getOrgnBrCode())) {
                    if (this.getUserName().equals(enterBy)) {
                        result = "You can not authorize your own entry.";
                        this.setMessage(result);
                    } else {
                        provision = tdRcptMgmtRemote.getProvApplyFlag(accountNo);
                        if (!provision.equals("") && provision.toUpperCase().contains(interestOption.toUpperCase())) {
                            result = tdCalMgmtRemote.tdProvisionRenewal(accountNo, ymd.format(sdf.parse(renewedTdDate)), ymd.format(sdf.parse(renewedMatDate)),
                                    Float.parseFloat(tdsToBeDeducted), Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(days), Float.parseFloat(rtNoHide),
                                    interestOption, Integer.parseInt(tdDayMth), Integer.parseInt(tdDayCum), renewalAccount, ddRecieptNo, series, Float.parseFloat(recieptNo),
                                    getUserName(), rtNoHide, accountNo, Math.round(Math.abs(rAmt)), getOrgnBrCode(), balint, Math.round(Float.parseFloat(renewalAmount)),
                                    Math.round(matpre), stYear, endYear, Float.parseFloat(roi), Double.parseDouble(renewedMatAmt));
                        } else {
                            synchronized (LOCK) {
                                result = tdCalMgmtRemote.tdRenewalSave(accountNo, ymd.format(sdf.parse(renewedTdDate)), ymd.format(sdf.parse(renewedMatDate)),
                                        Float.parseFloat(tdsToBeDeducted), Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(days), Float.parseFloat(rtNoHide),
                                        interestOption, Integer.parseInt(tdDayMth), Integer.parseInt(tdDayCum), renewalAccount, ddRecieptNo, series, Float.parseFloat(recieptNo),
                                        getUserName(), rtNoHide, Math.round(Math.abs(rAmt)), getOrgnBrCode(), balint, Math.round(Float.parseFloat(renewalAmount)), Math.round(matpre),
                                        stYear, endYear, Float.parseFloat(tdsDeductedForLastFinyear), totalIntRateCal, Float.parseFloat(tdsDeducted), Float.parseFloat(roi),
                                        Double.parseDouble(renewedMatAmt), Float.parseFloat(closeActTdsToBeDeducted));
                            }
                        }

                        if (result.substring(0, 4).equalsIgnoreCase("True")) {
                            NumberFormat format = new DecimalFormat("#0.00");
                            String[] tmpStr = result.split(":");
                            batchNo = tmpStr[1];
                            remainInt = format.format(CbsUtil.round(Double.parseDouble(tmpStr[2]), 0));
                            newReceiptNo = tmpStr[3];
                            newVoucherNo = tmpStr[4];
                            if (renewalAccount.equalsIgnoreCase("New")) {
                                acNo = tmpStr[5];
                                this.setMessage("Account Renewed Successfully, Generated A/C No is-->" + tmpStr[5] + " and Generated Batch No is-->" + tmpStr[1] + " " + alertMsg);
                            } else {
                                acNo = accountNo;
                                this.setMessage("Account Renewed Successfully, Generated Batch No is-->" + tmpStr[1] + " " + alertMsg);
                            }
                        } else {
                            batchNo = "";
                            remainInt = "";
                            this.setMessage(result);
                        }
                    }
                } else {
                    result = "You are not authorized person for verifing this detail.";
                    this.setMessage(result);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void clearAction() {
        try {
            this.setMessage("");
            setRenewalAmount("");
            setTdsDeductedForLastFinyear("");
            setTdsToBeDeducted("");
            setTdsDeducted("");
            setRenewedMatAmt("");
            setRoi("");
            setRenewedMatDate("");
            setYear("");
            setMonth("");
            setDays("");
            setAccountNo("");
            setRecieptNo("");
            setRenewedTdDate("");
            setAcctNo("");
            setStAcctNo("");
            setCloseActIntFinYear("");
            setCloseActTdsDeducted("");
            setCloseActTdsToBeDeducted("");
            tdTable = new ArrayList<TdGrid>();
            setBtnLabel("Renew");
            setInputDisFlag("none");
            setBtnAllDisFlag("false");
            setAcNoDisFlag("false");
            stYear = "";
            endYear = "";
            seriesVisible = false;
            stxVisible = true;
            ddSeries = new ArrayList<SelectItem>();
            remBalInt = "";
            setCustName("");
//            this.balIntVisible = "none";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void clearActionPopUp() {
        try {

            this.setMessage("");
            setRenewalAmount("");
            setTdsDeductedForLastFinyear("");
            setTdsToBeDeducted("");
            setTdsDeducted("");
            setRenewedMatAmt("");
            setRoi("");
            setRenewedMatDate("");
            setYear("");
            setMonth("");
            setDays("");
            setAccountNo("");
            setRecieptNo("");
            setRenewedTdDate("");
            setAcctNo("");
            setStAcctNo("");
            setCloseActIntFinYear("");
            setCloseActTdsDeducted("");
            setCloseActTdsToBeDeducted("");
            stYear = "";
            endYear = "";
            setInputDisFlag("none");
            setBtnAllDisFlag("false");
            setAcNoDisFlag("false");
            //setRenewalAccount("--Select--");
//            this.balIntVisible = "none";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setAcctNature() {
        try {
            acctNature = ftsPostRemote.getAccountNature(currentItem.getAcno());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setBookSeries() {
        try {
            List resultList = new ArrayList();
            resultList = tdCalMgmtRemote.getBookSeries(acctNature, tdReceipt, getOrgnBrCode());
            ddSeries = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    ddSeries.add(new SelectItem(ee.toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setfdGlobal() {
        try {
            List globalFdList = new ArrayList();
            //  String msg;
            globalFdList = tdCalMgmtRemote.getGlobalFdCondition();

            if (globalFdList.size() > 0) {
                Vector ele = (Vector) globalFdList.get(0);
                // tdAmount = ele.get(0).toString();
                tdDayMth = ele.get(1).toString();
                tdDayCum = ele.get(2).toString();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void ddRenewalAcctprocessValueChange() {
        if (renewalAccount.equals("Existing")) {
            seriesVisible = true;
            recieptNoDisabled = false;
            stxVisible = false;
            setBookSeries();
        } else if (renewalAccount.equals("New")) {
            this.setDdRecieptNo("New");
            seriesVisible = true;
            recieptNoDisabled = true;
            stxVisible = false;
            setBookSeries();
        } else if ((renewalAccount.equals("--Select--"))) {
            seriesVisible = false;
            stxVisible = true;
        }
    }

    public String validation() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher roiCheck = p.matcher(roi);
            Matcher daysCheck = p.matcher(days);
            Matcher monthCheck = p.matcher(month);
            Matcher yearcheck = p.matcher(year);

            if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")) {
                this.setMessage("There should be an account number !");
                return "false";
            }
            if (this.applicableRoi.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select applicable roi !");
                return "false";
            }
            if (!roiCheck.matches()) {
                this.setMessage("Please Enter Numeric Value in Rate Of Interest");
                return "false";
            }
            if (Float.parseFloat(roi) < 0) {
                this.setMessage("Please Fill Positive Value In Rate Of Interest ");
                return "false";
            }
            if (!daysCheck.matches()) {
                this.setMessage("Please Enter Numeric Value in Days");
                return "false";
            }
            if (!monthCheck.matches()) {
                this.setMessage("Please Enter Numeric Value in Month");
                return "false";
            }
            if (!yearcheck.matches()) {
                this.setMessage("Please Enter Numeric Value in Year");
                return "false";
            }
            if (Integer.parseInt(days) < 0) {
                this.setMessage("Days Can't Be Less than Zero ");
                return "false";
            }
            if (Integer.parseInt(month) < 0) {
                this.setMessage("Month Less than Zero ");
                return "false";
            }
            if (Integer.parseInt(year) < 0) {
                this.setMessage("Year Can't Be Less than Zero ");
                return "false";
            }

            int skipChk = postalRemote.getCodeByReportName("SKIP_BACK_RENEWAL_CHECK");
            if (skipChk == 1) {
                String frDate = ymd.format(sdf.parse(renewedTdDate));
                int day = ftsPostRemote.dateDiff(frDate, ymd.format(new Date()));
                int tDays = postalRemote.getCodeByReportName("TD_RENEWAL_DAYS");

                if (day > tDays) {
                    this.setMessage("Renewed Date Can't Be Back More Than " + tDays + " Days");
                    return "false";
                }
            }

            return "true";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public void allAccount() {
        try {
            setAcctNo("");
            setStAcctNo("");
            getTableRenewal();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String btnExit() {
        clearAction();
        return "case1";
    }

    public void matAmtRef() {
        this.setRenewedMatAmt("");
        this.setRoi("0.0");
    }
}
