/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeInterestOrServiceChargesDetailsTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class ISCD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables For iscd.jsp file
    private String intPaidFlag;
    private String intCollFlag;
    private String serviceChgFlag;
    private String parkingAcctTdsHolder;
    private String nrmlPrftLossAcctHolderCr;
    private String penalPLAcctHoldeDr;
    private String nrmlPrftLossActHolderDr;
    private String chqAllowedFlag;
    private String overDueNrmlPrftLossAcctHolderCr;
    private String optActEnoughFundsDebited;
    private String advanceIntAcct;
    private String bookTransScript;
    private String intCalFreqCrType;
    private String intCalFreqCrWeekNo;
    private String intCalFreqCrWeekDay;
    private String intCalFreqCrStartDt;
    private String intCalFreqCrNp;
    private String intCalFreqDrType;
    private String intCalFreqDrWeekNo;
    private String intCalFreqDrWeekDay;
    private String intCalFreqDrStartDt;
    private String intCalFreqDrNp;
    private String intOnQIS;
    private String interestOnStock;
    private String drIntCompoundFreq;
    private String lmtLevelIntFlag;
    private String recovryLossLineholer;
    private String dealerContributionHolder;
    private String lookBackPeriod;
    private String compoundRestDayFlag;
    private String applyDisIntRate;
    private String principalLossLineHolder;
    private String chargeOffAcctHolder;
    private String intWaiverDebitHolder;
    private String overDueIntpaidAcHolder;
    private String intPayAcctHolder;
    private String intRecAcctHolder;
    private String serviceCollAcctHolder;
    private String incomeExpenseAcct;
    //  private String NormalPLAcctHolderDr;
    private String parkingAcctHolder;
    private String micrChqChgAcctHolder;
    private String mergeIntPtranFlag;
    private boolean disableFlagServiceChgDetail;
    private List<SelectItem> freqTypeList;
    private List<SelectItem> npList;
    private List<SelectItem> weekNoList;
    private List<SelectItem> weekDayList;
    private List<SelectItem> deleteTODList;

    //Getter-Setter for iscd.jsp file
    public String getAdvanceIntAcct() {
        return advanceIntAcct;
    }

    public void setAdvanceIntAcct(String advanceIntAcct) {
        this.advanceIntAcct = advanceIntAcct;
    }

    public String getApplyDisIntRate() {
        return applyDisIntRate;
    }

    public void setApplyDisIntRate(String applyDisIntRate) {
        this.applyDisIntRate = applyDisIntRate;
    }

    public String getBookTransScript() {
        return bookTransScript;
    }

    public void setBookTransScript(String bookTransScript) {
        this.bookTransScript = bookTransScript;
    }

    public String getChargeOffAcctHolder() {
        return chargeOffAcctHolder;
    }

    public void setChargeOffAcctHolder(String chargeOffAcctHolder) {
        this.chargeOffAcctHolder = chargeOffAcctHolder;
    }

    public String getChqAllowedFlag() {
        return chqAllowedFlag;
    }

    public void setChqAllowedFlag(String chqAllowedFlag) {
        this.chqAllowedFlag = chqAllowedFlag;
    }

    public String getCompoundRestDayFlag() {
        return compoundRestDayFlag;
    }

    public void setCompoundRestDayFlag(String compoundRestDayFlag) {
        this.compoundRestDayFlag = compoundRestDayFlag;
    }

    public String getDealerContributionHolder() {
        return dealerContributionHolder;
    }

    public void setDealerContributionHolder(String dealerContributionHolder) {
        this.dealerContributionHolder = dealerContributionHolder;
    }

    public boolean isDisableFlagServiceChgDetail() {
        return disableFlagServiceChgDetail;
    }

    public void setDisableFlagServiceChgDetail(boolean disableFlagServiceChgDetail) {
        this.disableFlagServiceChgDetail = disableFlagServiceChgDetail;
    }

    public String getDrIntCompoundFreq() {
        return drIntCompoundFreq;
    }

    public void setDrIntCompoundFreq(String drIntCompoundFreq) {
        this.drIntCompoundFreq = drIntCompoundFreq;
    }

    public List<SelectItem> getFreqTypeList() {
        return freqTypeList;
    }

    public void setFreqTypeList(List<SelectItem> freqTypeList) {
        this.freqTypeList = freqTypeList;
    }

    public String getIncomeExpenseAcct() {
        return incomeExpenseAcct;
    }

    public void setIncomeExpenseAcct(String incomeExpenseAcct) {
        this.incomeExpenseAcct = incomeExpenseAcct;
    }

    public String getIntCalFreqCrNp() {
        return intCalFreqCrNp;
    }

    public void setIntCalFreqCrNp(String intCalFreqCrNp) {
        this.intCalFreqCrNp = intCalFreqCrNp;
    }

    public String getIntCalFreqCrStartDt() {
        return intCalFreqCrStartDt;
    }

    public void setIntCalFreqCrStartDt(String intCalFreqCrStartDt) {
        this.intCalFreqCrStartDt = intCalFreqCrStartDt;
    }

    public String getIntCalFreqCrType() {
        return intCalFreqCrType;
    }

    public void setIntCalFreqCrType(String intCalFreqCrType) {
        this.intCalFreqCrType = intCalFreqCrType;
    }

    public String getIntCalFreqCrWeekDay() {
        return intCalFreqCrWeekDay;
    }

    public void setIntCalFreqCrWeekDay(String intCalFreqCrWeekDay) {
        this.intCalFreqCrWeekDay = intCalFreqCrWeekDay;
    }

    public String getIntCalFreqCrWeekNo() {
        return intCalFreqCrWeekNo;
    }

    public void setIntCalFreqCrWeekNo(String intCalFreqCrWeekNo) {
        this.intCalFreqCrWeekNo = intCalFreqCrWeekNo;
    }

    public String getIntCalFreqDrNp() {
        return intCalFreqDrNp;
    }

    public void setIntCalFreqDrNp(String intCalFreqDrNp) {
        this.intCalFreqDrNp = intCalFreqDrNp;
    }

    public String getIntCalFreqDrStartDt() {
        return intCalFreqDrStartDt;
    }

    public void setIntCalFreqDrStartDt(String intCalFreqDrStartDt) {
        this.intCalFreqDrStartDt = intCalFreqDrStartDt;
    }

    public String getIntCalFreqDrType() {
        return intCalFreqDrType;
    }

    public void setIntCalFreqDrType(String intCalFreqDrType) {
        this.intCalFreqDrType = intCalFreqDrType;
    }

    public String getIntCalFreqDrWeekDay() {
        return intCalFreqDrWeekDay;
    }

    public void setIntCalFreqDrWeekDay(String intCalFreqDrWeekDay) {
        this.intCalFreqDrWeekDay = intCalFreqDrWeekDay;
    }

    public String getIntCalFreqDrWeekNo() {
        return intCalFreqDrWeekNo;
    }

    public void setIntCalFreqDrWeekNo(String intCalFreqDrWeekNo) {
        this.intCalFreqDrWeekNo = intCalFreqDrWeekNo;
    }

    public String getIntCollFlag() {
        return intCollFlag;
    }

    public void setIntCollFlag(String intCollFlag) {
        this.intCollFlag = intCollFlag;
    }

    public String getIntOnQIS() {
        return intOnQIS;
    }

    public void setIntOnQIS(String intOnQIS) {
        this.intOnQIS = intOnQIS;
    }

    public String getIntPaidFlag() {
        return intPaidFlag;
    }

    public void setIntPaidFlag(String intPaidFlag) {
        this.intPaidFlag = intPaidFlag;
    }

    public String getIntPayAcctHolder() {
        return intPayAcctHolder;
    }

    public void setIntPayAcctHolder(String intPayAcctHolder) {
        this.intPayAcctHolder = intPayAcctHolder;
    }

    public String getIntRecAcctHolder() {
        return intRecAcctHolder;
    }

    public void setIntRecAcctHolder(String intRecAcctHolder) {
        this.intRecAcctHolder = intRecAcctHolder;
    }

    public String getIntWaiverDebitHolder() {
        return intWaiverDebitHolder;
    }

    public void setIntWaiverDebitHolder(String intWaiverDebitHolder) {
        this.intWaiverDebitHolder = intWaiverDebitHolder;
    }

    public String getInterestOnStock() {
        return interestOnStock;
    }

    public void setInterestOnStock(String interestOnStock) {
        this.interestOnStock = interestOnStock;
    }

    public String getLmtLevelIntFlag() {
        return lmtLevelIntFlag;
    }

    public void setLmtLevelIntFlag(String lmtLevelIntFlag) {
        this.lmtLevelIntFlag = lmtLevelIntFlag;
    }

    public String getLookBackPeriod() {
        return lookBackPeriod;
    }

    public void setLookBackPeriod(String lookBackPeriod) {
        this.lookBackPeriod = lookBackPeriod;
    }

    public String getMergeIntPtranFlag() {
        return mergeIntPtranFlag;
    }

    public void setMergeIntPtranFlag(String mergeIntPtranFlag) {
        this.mergeIntPtranFlag = mergeIntPtranFlag;
    }

    public String getMicrChqChgAcctHolder() {
        return micrChqChgAcctHolder;
    }

    public void setMicrChqChgAcctHolder(String micrChqChgAcctHolder) {
        this.micrChqChgAcctHolder = micrChqChgAcctHolder;
    }

    public List<SelectItem> getNpList() {
        return npList;
    }

    public void setNpList(List<SelectItem> npList) {
        this.npList = npList;
    }

    public String getNrmlPrftLossAcctHolderCr() {
        return nrmlPrftLossAcctHolderCr;
    }

    public void setNrmlPrftLossAcctHolderCr(String nrmlPrftLossAcctHolderCr) {
        this.nrmlPrftLossAcctHolderCr = nrmlPrftLossAcctHolderCr;
    }

    public String getNrmlPrftLossActHolderDr() {
        return nrmlPrftLossActHolderDr;
    }

    public void setNrmlPrftLossActHolderDr(String nrmlPrftLossActHolderDr) {
        this.nrmlPrftLossActHolderDr = nrmlPrftLossActHolderDr;
    }

    public String getOptActEnoughFundsDebited() {
        return optActEnoughFundsDebited;
    }

    public void setOptActEnoughFundsDebited(String optActEnoughFundsDebited) {
        this.optActEnoughFundsDebited = optActEnoughFundsDebited;
    }

    public String getOverDueIntpaidAcHolder() {
        return overDueIntpaidAcHolder;
    }

    public void setOverDueIntpaidAcHolder(String overDueIntpaidAcHolder) {
        this.overDueIntpaidAcHolder = overDueIntpaidAcHolder;
    }

    public String getOverDueNrmlPrftLossAcctHolderCr() {
        return overDueNrmlPrftLossAcctHolderCr;
    }

    public void setOverDueNrmlPrftLossAcctHolderCr(String overDueNrmlPrftLossAcctHolderCr) {
        this.overDueNrmlPrftLossAcctHolderCr = overDueNrmlPrftLossAcctHolderCr;
    }

    public String getParkingAcctHolder() {
        return parkingAcctHolder;
    }

    public void setParkingAcctHolder(String parkingAcctHolder) {
        this.parkingAcctHolder = parkingAcctHolder;
    }

    public String getParkingAcctTdsHolder() {
        return parkingAcctTdsHolder;
    }

    public void setParkingAcctTdsHolder(String parkingAcctTdsHolder) {
        this.parkingAcctTdsHolder = parkingAcctTdsHolder;
    }

    public String getPenalPLAcctHoldeDr() {
        return penalPLAcctHoldeDr;
    }

    public void setPenalPLAcctHoldeDr(String penalPLAcctHoldeDr) {
        this.penalPLAcctHoldeDr = penalPLAcctHoldeDr;
    }

    public String getPrincipalLossLineHolder() {
        return principalLossLineHolder;
    }

    public void setPrincipalLossLineHolder(String principalLossLineHolder) {
        this.principalLossLineHolder = principalLossLineHolder;
    }

    public String getRecovryLossLineholer() {
        return recovryLossLineholer;
    }

    public void setRecovryLossLineholer(String recovryLossLineholer) {
        this.recovryLossLineholer = recovryLossLineholer;
    }

    public String getServiceChgFlag() {
        return serviceChgFlag;
    }

    public void setServiceChgFlag(String serviceChgFlag) {
        this.serviceChgFlag = serviceChgFlag;
    }

    public String getServiceCollAcctHolder() {
        return serviceCollAcctHolder;
    }

    public void setServiceCollAcctHolder(String serviceCollAcctHolder) {
        this.serviceCollAcctHolder = serviceCollAcctHolder;
    }

    public List<SelectItem> getWeekDayList() {
        return weekDayList;
    }

    public void setWeekDayList(List<SelectItem> weekDayList) {
        this.weekDayList = weekDayList;
    }

    public List<SelectItem> getWeekNoList() {
        return weekNoList;
    }

    public void setWeekNoList(List<SelectItem> weekNoList) {
        this.weekNoList = weekNoList;
    }

    public List<SelectItem> getDeleteTODList() {
        return deleteTODList;
    }

    public void setDeleteTODList(List<SelectItem> deleteTODList) {
        this.deleteTODList = deleteTODList;
    }

    /** Creates a new instance of ISCD */
    public ISCD() {
        
        try {
            deleteTODList = new ArrayList<SelectItem>();
            deleteTODList.add(new SelectItem("0", ""));
            deleteTODList.add(new SelectItem("Y", "Yes"));
            deleteTODList.add(new SelectItem("N", "No"));

            freqTypeList = new ArrayList<SelectItem>();
            freqTypeList.add(new SelectItem("0", " "));
            freqTypeList.add(new SelectItem("D", "Daily"));
            freqTypeList.add(new SelectItem("W", "Weekly"));
            freqTypeList.add(new SelectItem("M", "Monthly"));
            freqTypeList.add(new SelectItem("Q", "Quarterly"));
            freqTypeList.add(new SelectItem("H", "Half Yearly"));
            freqTypeList.add(new SelectItem("Y", "Yearly"));

            weekNoList = new ArrayList<SelectItem>();
            weekNoList.add(new SelectItem("0", " "));
            weekNoList.add(new SelectItem("1", "First Week"));
            weekNoList.add(new SelectItem("2", "Second week"));
            weekNoList.add(new SelectItem("3", "Third week"));
            weekNoList.add(new SelectItem("4", "Forth week"));
            weekNoList.add(new SelectItem("M", "Middle Week(2/3)"));
            weekNoList.add(new SelectItem("L", "Last week"));

            weekDayList = new ArrayList<SelectItem>();
            weekDayList.add(new SelectItem("0", " "));
            weekDayList.add(new SelectItem("1", "Sunday"));
            weekDayList.add(new SelectItem("2", "Monday"));
            weekDayList.add(new SelectItem("3", "Tuesday"));
            weekDayList.add(new SelectItem("4", "Wednesday"));
            weekDayList.add(new SelectItem("5", "Thursday"));
            weekDayList.add(new SelectItem("6", "Friday"));
            weekDayList.add(new SelectItem("7", "Saturday"));

            npList = new ArrayList<SelectItem>();
            npList.add(new SelectItem("0", " "));
            npList.add(new SelectItem("N", "Next"));
            npList.add(new SelectItem("P", "Previous"));
            npList.add(new SelectItem("S", "Skip"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void selectIscdDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeInterestOrServiceChargesDetailsTO singleTO = schemeMasterManagementDelegate.getIscdDetails(schemeMaster.getSchemeCode());
            if (singleTO != null) {
                if (singleTO.getInterestPaidFlag() == null || singleTO.getInterestPaidFlag().equalsIgnoreCase("")) {
                    this.setIntPaidFlag("0");
                } else {
                    this.setIntPaidFlag(singleTO.getInterestPaidFlag());
                }
                if (singleTO.getInterestPayableAccountPlaceholder() == null || singleTO.getInterestPayableAccountPlaceholder().equalsIgnoreCase("")) {
                    this.setIntPayAcctHolder("");
                } else {
                    this.setIntPayAcctHolder(singleTO.getInterestPayableAccountPlaceholder());
                }
                if (singleTO.getIntCollectionFlag() == null || singleTO.getIntCollectionFlag().equalsIgnoreCase("")) {
                    this.setIntCollFlag("0");
                } else {
                    this.setIntCollFlag(singleTO.getIntCollectionFlag());
                }
                if (singleTO.getIntRecbleAccountPlaceholder() == null || singleTO.getIntRecbleAccountPlaceholder().equalsIgnoreCase("")) {
                    this.setIntRecAcctHolder("");
                } else {
                    this.setIntRecAcctHolder(singleTO.getIntRecbleAccountPlaceholder());
                }
                if (singleTO.getServiceChargesFlag() == null || singleTO.getServiceChargesFlag().equalsIgnoreCase("")) {
                    this.setServiceChgFlag("0");
                } else {
                    this.setServiceChgFlag(singleTO.getServiceChargesFlag());
                }
                if (singleTO.getServiceCollAccountPlaceholder() == null || singleTO.getServiceCollAccountPlaceholder().equalsIgnoreCase("")) {
                    this.setServiceCollAcctHolder("");
                } else {
                    this.setServiceCollAcctHolder(singleTO.getServiceCollAccountPlaceholder());
                }
                if (singleTO.getParkingAccountTdsPlaceholder() == null || singleTO.getParkingAccountTdsPlaceholder().equalsIgnoreCase("")) {
                    this.setParkingAcctTdsHolder("");
                } else {
                    this.setParkingAcctTdsHolder(singleTO.getParkingAccountTdsPlaceholder());
                }
                if (singleTO.getIncomeExpenseAccountInHomeCurrency() == null || singleTO.getIncomeExpenseAccountInHomeCurrency().equalsIgnoreCase("")) {
                    this.setIncomeExpenseAcct("");
                } else {
                    this.setIncomeExpenseAcct(singleTO.getIncomeExpenseAccountInHomeCurrency());
                }
                if (singleTO.getNormalProfitAndLossAccountPlaceholderCr() == null || singleTO.getNormalProfitAndLossAccountPlaceholderCr().equalsIgnoreCase("")) {
                    this.setNrmlPrftLossAcctHolderCr("");
                } else {
                    this.setNrmlPrftLossAcctHolderCr(singleTO.getNormalProfitAndLossAccountPlaceholderCr());
                }
                if (singleTO.getNormalProfitAndLossAccountPlaceholderDr() == null || singleTO.getNormalProfitAndLossAccountPlaceholderDr().equalsIgnoreCase("")) {
                    this.setNrmlPrftLossActHolderDr("");
                } else {
                    this.setNrmlPrftLossActHolderDr(singleTO.getNormalProfitAndLossAccountPlaceholderDr());
                }
                if (singleTO.getPenalProfitAndLossAccountPlaceholderDr() == null || singleTO.getPenalProfitAndLossAccountPlaceholderDr().equalsIgnoreCase("")) {
                    this.setPenalPLAcctHoldeDr("");
                } else {
                    this.setPenalPLAcctHoldeDr(singleTO.getPenalProfitAndLossAccountPlaceholderDr());
                }
                if (singleTO.getParkingAccountPlaceholder() == null || singleTO.getParkingAccountPlaceholder().equalsIgnoreCase("")) {
                    this.setParkingAcctHolder("");
                } else {
                    this.setParkingAcctHolder(singleTO.getParkingAccountPlaceholder());
                }
                if (singleTO.getChequeAllowedFlag() == null || singleTO.getChequeAllowedFlag().equalsIgnoreCase("")) {
                    this.setChqAllowedFlag("");
                } else {
                    this.setChqAllowedFlag(singleTO.getChequeAllowedFlag());
                }
                if (singleTO.getMicrChequeChrgAccountPlaceholder() == null || singleTO.getMicrChequeChrgAccountPlaceholder().equalsIgnoreCase("")) {
                    this.setMicrChqChgAcctHolder("");
                } else {
                    this.setMicrChqChgAcctHolder(singleTO.getMicrChequeChrgAccountPlaceholder());
                }
                if (singleTO.getOverDueIntPaidAcPlaceholder() == null || singleTO.getOverDueIntPaidAcPlaceholder().equalsIgnoreCase("")) {
                    this.setOverDueIntpaidAcHolder("");
                } else {
                    this.setOverDueIntpaidAcHolder(singleTO.getOverDueIntPaidAcPlaceholder());
                }
                if (singleTO.getOverDueNormalProfitAndLossAccountPlaceholder() == null || singleTO.getOverDueNormalProfitAndLossAccountPlaceholder().equalsIgnoreCase("")) {
                    this.setOverDueNrmlPrftLossAcctHolderCr("");
                } else {
                    this.setOverDueNrmlPrftLossAcctHolderCr(singleTO.getOverDueNormalProfitAndLossAccountPlaceholder());
                }
                if (singleTO.getMergeIntPtranFlag() == null || singleTO.getMergeIntPtranFlag().equalsIgnoreCase("")) {
                    this.setMergeIntPtranFlag("");
                } else {
                    this.setMergeIntPtranFlag(singleTO.getMergeIntPtranFlag());
                }
                if (singleTO.getOperativeAccountWithoutEnoughFundsToBeDebited() == null || singleTO.getOperativeAccountWithoutEnoughFundsToBeDebited().equalsIgnoreCase("")) {
                    this.setOptActEnoughFundsDebited("");
                } else {
                    this.setOptActEnoughFundsDebited(singleTO.getOperativeAccountWithoutEnoughFundsToBeDebited());
                }
                if (singleTO.getAdvanceInterestAccount() == null || singleTO.getAdvanceInterestAccount().equalsIgnoreCase("")) {
                    this.setAdvanceIntAcct("");
                } else {
                    this.setAdvanceIntAcct(singleTO.getAdvanceInterestAccount());
                }
                if (singleTO.getBookTransScript() == null || singleTO.getBookTransScript().equalsIgnoreCase("")) {
                    this.setBookTransScript("");
                } else {
                    this.setBookTransScript(singleTO.getBookTransScript());
                }
                if (singleTO.getInterestCalculationFreqCrType() == null || singleTO.getInterestCalculationFreqCrType().equalsIgnoreCase("")) {
                    this.setIntCalFreqCrType("");
                } else {
                    this.setIntCalFreqCrType(singleTO.getInterestCalculationFreqCrType());
                }
                if (singleTO.getInterestCalculationFreqCrWeekNo() == null || singleTO.getInterestCalculationFreqCrWeekNo().equalsIgnoreCase("")) {
                    this.setIntCalFreqCrWeekNo("");
                } else {
                    this.setIntCalFreqCrWeekNo(singleTO.getInterestCalculationFreqCrWeekNo());
                }
                if (singleTO.getInterestCalculationFreqCrWeekDay() == null || singleTO.getInterestCalculationFreqCrWeekDay().equalsIgnoreCase("")) {
                    this.setIntCalFreqCrWeekDay("");
                } else {
                    this.setIntCalFreqCrWeekDay(singleTO.getInterestCalculationFreqCrWeekDay());
                }
                if (singleTO.getInterestCalculationFreqCrStartDate() == null || singleTO.getInterestCalculationFreqCrStartDate().equalsIgnoreCase("")) {
                    this.setIntCalFreqCrStartDt("");
                } else {
                    this.setIntCalFreqCrStartDt(singleTO.getInterestCalculationFreqCrStartDate());
                }
                if (singleTO.getInterestCalculationFreqCrNp() == null || singleTO.getInterestCalculationFreqCrNp().equalsIgnoreCase("")) {
                    this.setIntCalFreqCrNp("");
                } else {
                    this.setIntCalFreqCrNp(singleTO.getInterestCalculationFreqCrNp());
                }
                if (singleTO.getInterestCalculationFreqDrType() == null || singleTO.getInterestCalculationFreqDrType().equalsIgnoreCase("")) {
                    this.setIntCalFreqDrType("");
                } else {
                    this.setIntCalFreqDrType(singleTO.getInterestCalculationFreqDrType());
                }
                if (singleTO.getInterestCalculationFreqDrWeekNo() == null || singleTO.getInterestCalculationFreqDrWeekNo().equalsIgnoreCase("")) {
                    this.setIntCalFreqDrWeekNo("");
                } else {
                    this.setIntCalFreqDrWeekNo(singleTO.getInterestCalculationFreqDrWeekNo());
                }
                if (singleTO.getInterestCalculationFreqDrWeekDate() == null || singleTO.getInterestCalculationFreqDrWeekDate().equalsIgnoreCase("")) {
                    this.setIntCalFreqDrWeekDay("");
                } else {
                    this.setIntCalFreqDrWeekDay(singleTO.getInterestCalculationFreqDrWeekDate());
                }
                if (singleTO.getInterestCalculationFreqDrStartDate() == null || singleTO.getInterestCalculationFreqDrStartDate().equalsIgnoreCase("")) {
                    this.setIntCalFreqDrStartDt("");
                } else {
                    this.setIntCalFreqDrStartDt(singleTO.getInterestCalculationFreqDrStartDate());
                }
                if (singleTO.getInterestCalculationFreqDrNp() == null || singleTO.getInterestCalculationFreqDrNp().equalsIgnoreCase("")) {
                    this.setIntCalFreqDrNp("");
                } else {
                    this.setIntCalFreqDrNp(singleTO.getInterestCalculationFreqDrNp());
                }
                if (singleTO.getLimitLevelIntFlag() == null || singleTO.getLimitLevelIntFlag().equalsIgnoreCase("")) {
                    this.setLmtLevelIntFlag("");
                } else {
                    this.setLmtLevelIntFlag(singleTO.getLimitLevelIntFlag());
                }
                if (singleTO.getInterestOnQis() == null || singleTO.getInterestOnQis().equalsIgnoreCase("")) {
                    this.setIntOnQIS("");
                } else {
                    this.setIntOnQIS(singleTO.getInterestOnQis());
                }
                if (singleTO.getLookBackPeriod() == null || singleTO.getLookBackPeriod().equalsIgnoreCase("")) {
                    this.setLookBackPeriod("");
                } else {
                    this.setLookBackPeriod(singleTO.getLookBackPeriod());
                }
                if (singleTO.getInterestOnStock() == null || singleTO.getInterestOnStock().equalsIgnoreCase("")) {
                    this.setInterestOnStock("");
                } else {
                    this.setInterestOnStock(singleTO.getInterestOnStock());
                }
                if (singleTO.getCompoundRestDayFlag() == null || singleTO.getCompoundRestDayFlag().equalsIgnoreCase("")) {
                    this.setCompoundRestDayFlag("");
                } else {
                    this.setCompoundRestDayFlag(singleTO.getCompoundRestDayFlag());
                }
                if (singleTO.getDebitIntCompoundingFreq() == null || singleTO.getDebitIntCompoundingFreq().equalsIgnoreCase("")) {
                    this.setDrIntCompoundFreq("");
                } else {
                    this.setDrIntCompoundFreq(singleTO.getDebitIntCompoundingFreq());
                }

                this.setApplyDisIntRate(singleTO.getApplyDiscountedIntRate().toString());

                if (singleTO.getPrincipalLossLinePlaceholder() == null || singleTO.getPrincipalLossLinePlaceholder().equalsIgnoreCase("")) {
                    this.setPrincipalLossLineHolder("");
                } else {
                    this.setPrincipalLossLineHolder(singleTO.getPrincipalLossLinePlaceholder());
                }
                if (singleTO.getRecoveryLossLinePlaceholer() == null || singleTO.getRecoveryLossLinePlaceholer().equalsIgnoreCase("")) {
                    this.setRecovryLossLineholer("");
                } else {
                    this.setRecovryLossLineholer(singleTO.getRecoveryLossLinePlaceholer());
                }
                if (singleTO.getChargeOffAccountPlaceholder() == null || singleTO.getChargeOffAccountPlaceholder().equalsIgnoreCase("")) {
                    this.setChargeOffAcctHolder("");
                } else {
                    this.setChargeOffAcctHolder(singleTO.getChargeOffAccountPlaceholder());
                }
                if (singleTO.getDealerContributionPlaceholder() == null || singleTO.getDealerContributionPlaceholder().equalsIgnoreCase("")) {
                    this.setDealerContributionHolder("");
                } else {
                    this.setDealerContributionHolder(singleTO.getDealerContributionPlaceholder());
                }
                if (singleTO.getInterestWaiverDebitPlaceholder() == null || singleTO.getInterestWaiverDebitPlaceholder().equalsIgnoreCase("")) {
                    this.setIntWaiverDebitHolder("");
                } else {
                    this.setIntWaiverDebitHolder(singleTO.getInterestWaiverDebitPlaceholder());
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refreshIscdForm() {
        this.setIntPaidFlag("0");
        this.setIntPayAcctHolder("");
        this.setIntCollFlag("0");
        this.setIntRecAcctHolder("");
        this.setServiceChgFlag("0");

        this.setServiceCollAcctHolder("");
        this.setParkingAcctTdsHolder("");
        this.setIncomeExpenseAcct("");
        this.setNrmlPrftLossAcctHolderCr("");
        this.setNrmlPrftLossActHolderDr("");

        this.setPenalPLAcctHoldeDr("");
        this.setParkingAcctHolder("");
        this.setChqAllowedFlag("0");
        this.setMicrChqChgAcctHolder("");
        this.setOverDueIntpaidAcHolder("");

        this.setOverDueNrmlPrftLossAcctHolderCr("");
        this.setMergeIntPtranFlag("0");
        this.setOptActEnoughFundsDebited("0");
        this.setAdvanceIntAcct("");
        this.setBookTransScript("");

        this.setIntCalFreqCrType("0");
        this.setIntCalFreqCrWeekNo("0");
        this.setIntCalFreqCrWeekDay("0");
        this.setIntCalFreqCrStartDt("");
        this.setIntCalFreqCrNp("0");

        this.setIntCalFreqDrType("0");
        this.setIntCalFreqDrWeekNo("0");
        this.setIntCalFreqDrWeekDay("0");
        this.setIntCalFreqDrStartDt("");
        this.setIntCalFreqDrNp("0");

        this.setLmtLevelIntFlag("0");
        this.setIntOnQIS("0");
        this.setLookBackPeriod("");
        this.setInterestOnStock("0");
        this.setCompoundRestDayFlag("0");

        this.setDrIntCompoundFreq("0");
        this.setApplyDisIntRate("0.00");
        this.setPrincipalLossLineHolder("");
        this.setRecovryLossLineholer("");
        this.setChargeOffAcctHolder("");
        this.setDealerContributionHolder("");
        this.setIntWaiverDebitHolder("");
    }

    public void enableIscdForm() {
        this.disableFlagServiceChgDetail = false;
    }

    public void disableIscdForm() {
        this.disableFlagServiceChgDetail = true;
    }
}
