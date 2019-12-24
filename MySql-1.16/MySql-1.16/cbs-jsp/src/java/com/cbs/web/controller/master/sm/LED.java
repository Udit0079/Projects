/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeLoanExceptionDetailsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class LED {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for led.jsp file
    private String nonConformingLoanPeriod;
    private String nonConformingLoanAmount;
    private String disbGreaterThanLoanAmount;
    private String disbNotConformingToSchedule;
    private String disbDateSanctExpiryDate;
    private String intCalculationNotUpToDate;
    private String transferAmountIsGreaterThanCrBalance;
    private String custIdDiffForLoanAndOpAccount;
    private String interestCollectedExceedsLimit;
    private String waiverOfChargesOrInterest;
    private String overrideSystemCalcEiAmount;
    private String pendingSchedulePayments;
    private String repaymentPerdNotEquelToLoanPerd;
    private String rephasementInterestCalcNotUpToDate;
    private String maxHolidayPeriodExceeded;
    private String prepaymentNotAsPerNotice;
    private String valueDatedNotice;
    private String backValueDatedAccountOpening;
    private String stNonConformingLoanPeriod;
    private String stNonConformingLoanAmount;
    private String stDisbGreaterThanLoanAmount;
    private String stDisbNotConformingToSchedule;
    private String stDisbDateSanctExpiryDate;
    private String stIntCalculationNotUpToDate;
    private String stTransferAmountIsGreaterThanCrBalance;
    private String stCustIdDiffForLoanAndOpAccount;
    private String stInterestCollectedExceedsLimit;
    private String stWaiverOfChargesOrInterest;
    private String stOverrideSystemCalcEiAmount;
    private String stPendingSchedulePayments;
    private String stRepaymentPerdNotEquelToLoanPerd;
    private String stRephasementInterestCalcNotUpToDate;
    private String stMaxHolidayPeriodExceeded;
    private String stPrepaymentNotAsPerNotice;
    private String stValueDatedNotice;
    private String stBackValueDatedAccountOpening;
    private boolean ledFlag;

    //Getter-Setter for led.jsp file
    public boolean isLedFlag() {
        return ledFlag;
    }

    public void setLedFlag(boolean ledFlag) {
        this.ledFlag = ledFlag;
    }

    public String getBackValueDatedAccountOpening() {
        return backValueDatedAccountOpening;
    }

    public void setBackValueDatedAccountOpening(String backValueDatedAccountOpening) {
        this.backValueDatedAccountOpening = backValueDatedAccountOpening;
    }

    public String getCustIdDiffForLoanAndOpAccount() {
        return custIdDiffForLoanAndOpAccount;
    }

    public void setCustIdDiffForLoanAndOpAccount(String custIdDiffForLoanAndOpAccount) {
        this.custIdDiffForLoanAndOpAccount = custIdDiffForLoanAndOpAccount;
    }

    public String getDisbDateSanctExpiryDate() {
        return disbDateSanctExpiryDate;
    }

    public void setDisbDateSanctExpiryDate(String disbDateSanctExpiryDate) {
        this.disbDateSanctExpiryDate = disbDateSanctExpiryDate;
    }

    public String getDisbGreaterThanLoanAmount() {
        return disbGreaterThanLoanAmount;
    }

    public void setDisbGreaterThanLoanAmount(String disbGreaterThanLoanAmount) {
        this.disbGreaterThanLoanAmount = disbGreaterThanLoanAmount;
    }

    public String getDisbNotConformingToSchedule() {
        return disbNotConformingToSchedule;
    }

    public void setDisbNotConformingToSchedule(String disbNotConformingToSchedule) {
        this.disbNotConformingToSchedule = disbNotConformingToSchedule;
    }

    public String getIntCalculationNotUpToDate() {
        return intCalculationNotUpToDate;
    }

    public void setIntCalculationNotUpToDate(String intCalculationNotUpToDate) {
        this.intCalculationNotUpToDate = intCalculationNotUpToDate;
    }

    public String getInterestCollectedExceedsLimit() {
        return interestCollectedExceedsLimit;
    }

    public void setInterestCollectedExceedsLimit(String interestCollectedExceedsLimit) {
        this.interestCollectedExceedsLimit = interestCollectedExceedsLimit;
    }

    public String getMaxHolidayPeriodExceeded() {
        return maxHolidayPeriodExceeded;
    }

    public void setMaxHolidayPeriodExceeded(String maxHolidayPeriodExceeded) {
        this.maxHolidayPeriodExceeded = maxHolidayPeriodExceeded;
    }

    public String getNonConformingLoanAmount() {
        return nonConformingLoanAmount;
    }

    public void setNonConformingLoanAmount(String nonConformingLoanAmount) {
        this.nonConformingLoanAmount = nonConformingLoanAmount;
    }

    public String getNonConformingLoanPeriod() {
        return nonConformingLoanPeriod;
    }

    public void setNonConformingLoanPeriod(String nonConformingLoanPeriod) {
        this.nonConformingLoanPeriod = nonConformingLoanPeriod;
    }

    public String getOverrideSystemCalcEiAmount() {
        return overrideSystemCalcEiAmount;
    }

    public void setOverrideSystemCalcEiAmount(String overrideSystemCalcEiAmount) {
        this.overrideSystemCalcEiAmount = overrideSystemCalcEiAmount;
    }

    public String getPendingSchedulePayments() {
        return pendingSchedulePayments;
    }

    public void setPendingSchedulePayments(String pendingSchedulePayments) {
        this.pendingSchedulePayments = pendingSchedulePayments;
    }

    public String getPrepaymentNotAsPerNotice() {
        return prepaymentNotAsPerNotice;
    }

    public void setPrepaymentNotAsPerNotice(String prepaymentNotAsPerNotice) {
        this.prepaymentNotAsPerNotice = prepaymentNotAsPerNotice;
    }

    public String getRepaymentPerdNotEquelToLoanPerd() {
        return repaymentPerdNotEquelToLoanPerd;
    }

    public void setRepaymentPerdNotEquelToLoanPerd(String repaymentPerdNotEquelToLoanPerd) {
        this.repaymentPerdNotEquelToLoanPerd = repaymentPerdNotEquelToLoanPerd;
    }

    public String getRephasementInterestCalcNotUpToDate() {
        return rephasementInterestCalcNotUpToDate;
    }

    public void setRephasementInterestCalcNotUpToDate(String rephasementInterestCalcNotUpToDate) {
        this.rephasementInterestCalcNotUpToDate = rephasementInterestCalcNotUpToDate;
    }

    public String getStBackValueDatedAccountOpening() {
        return stBackValueDatedAccountOpening;
    }

    public void setStBackValueDatedAccountOpening(String stBackValueDatedAccountOpening) {
        this.stBackValueDatedAccountOpening = stBackValueDatedAccountOpening;
    }

    public String getStCustIdDiffForLoanAndOpAccount() {
        return stCustIdDiffForLoanAndOpAccount;
    }

    public void setStCustIdDiffForLoanAndOpAccount(String stCustIdDiffForLoanAndOpAccount) {
        this.stCustIdDiffForLoanAndOpAccount = stCustIdDiffForLoanAndOpAccount;
    }

    public String getStDisbDateSanctExpiryDate() {
        return stDisbDateSanctExpiryDate;
    }

    public void setStDisbDateSanctExpiryDate(String stDisbDateSanctExpiryDate) {
        this.stDisbDateSanctExpiryDate = stDisbDateSanctExpiryDate;
    }

    public String getStDisbGreaterThanLoanAmount() {
        return stDisbGreaterThanLoanAmount;
    }

    public void setStDisbGreaterThanLoanAmount(String stDisbGreaterThanLoanAmount) {
        this.stDisbGreaterThanLoanAmount = stDisbGreaterThanLoanAmount;
    }

    public String getStDisbNotConformingToSchedule() {
        return stDisbNotConformingToSchedule;
    }

    public void setStDisbNotConformingToSchedule(String stDisbNotConformingToSchedule) {
        this.stDisbNotConformingToSchedule = stDisbNotConformingToSchedule;
    }

    public String getStIntCalculationNotUpToDate() {
        return stIntCalculationNotUpToDate;
    }

    public void setStIntCalculationNotUpToDate(String stIntCalculationNotUpToDate) {
        this.stIntCalculationNotUpToDate = stIntCalculationNotUpToDate;
    }

    public String getStInterestCollectedExceedsLimit() {
        return stInterestCollectedExceedsLimit;
    }

    public void setStInterestCollectedExceedsLimit(String stInterestCollectedExceedsLimit) {
        this.stInterestCollectedExceedsLimit = stInterestCollectedExceedsLimit;
    }

    public String getStMaxHolidayPeriodExceeded() {
        return stMaxHolidayPeriodExceeded;
    }

    public void setStMaxHolidayPeriodExceeded(String stMaxHolidayPeriodExceeded) {
        this.stMaxHolidayPeriodExceeded = stMaxHolidayPeriodExceeded;
    }

    public String getStNonConformingLoanAmount() {
        return stNonConformingLoanAmount;
    }

    public void setStNonConformingLoanAmount(String stNonConformingLoanAmount) {
        this.stNonConformingLoanAmount = stNonConformingLoanAmount;
    }

    public String getStNonConformingLoanPeriod() {
        return stNonConformingLoanPeriod;
    }

    public void setStNonConformingLoanPeriod(String stNonConformingLoanPeriod) {
        this.stNonConformingLoanPeriod = stNonConformingLoanPeriod;
    }

    public String getStOverrideSystemCalcEiAmount() {
        return stOverrideSystemCalcEiAmount;
    }

    public void setStOverrideSystemCalcEiAmount(String stOverrideSystemCalcEiAmount) {
        this.stOverrideSystemCalcEiAmount = stOverrideSystemCalcEiAmount;
    }

    public String getStPendingSchedulePayments() {
        return stPendingSchedulePayments;
    }

    public void setStPendingSchedulePayments(String stPendingSchedulePayments) {
        this.stPendingSchedulePayments = stPendingSchedulePayments;
    }

    public String getStPrepaymentNotAsPerNotice() {
        return stPrepaymentNotAsPerNotice;
    }

    public void setStPrepaymentNotAsPerNotice(String stPrepaymentNotAsPerNotice) {
        this.stPrepaymentNotAsPerNotice = stPrepaymentNotAsPerNotice;
    }

    public String getStRepaymentPerdNotEquelToLoanPerd() {
        return stRepaymentPerdNotEquelToLoanPerd;
    }

    public void setStRepaymentPerdNotEquelToLoanPerd(String stRepaymentPerdNotEquelToLoanPerd) {
        this.stRepaymentPerdNotEquelToLoanPerd = stRepaymentPerdNotEquelToLoanPerd;
    }

    public String getStRephasementInterestCalcNotUpToDate() {
        return stRephasementInterestCalcNotUpToDate;
    }

    public void setStRephasementInterestCalcNotUpToDate(String stRephasementInterestCalcNotUpToDate) {
        this.stRephasementInterestCalcNotUpToDate = stRephasementInterestCalcNotUpToDate;
    }

    public String getStTransferAmountIsGreaterThanCrBalance() {
        return stTransferAmountIsGreaterThanCrBalance;
    }

    public void setStTransferAmountIsGreaterThanCrBalance(String stTransferAmountIsGreaterThanCrBalance) {
        this.stTransferAmountIsGreaterThanCrBalance = stTransferAmountIsGreaterThanCrBalance;
    }

    public String getStValueDatedNotice() {
        return stValueDatedNotice;
    }

    public void setStValueDatedNotice(String stValueDatedNotice) {
        this.stValueDatedNotice = stValueDatedNotice;
    }

    public String getStWaiverOfChargesOrInterest() {
        return stWaiverOfChargesOrInterest;
    }

    public void setStWaiverOfChargesOrInterest(String stWaiverOfChargesOrInterest) {
        this.stWaiverOfChargesOrInterest = stWaiverOfChargesOrInterest;
    }

    public String getTransferAmountIsGreaterThanCrBalance() {
        return transferAmountIsGreaterThanCrBalance;
    }

    public void setTransferAmountIsGreaterThanCrBalance(String transferAmountIsGreaterThanCrBalance) {
        this.transferAmountIsGreaterThanCrBalance = transferAmountIsGreaterThanCrBalance;
    }

    public String getValueDatedNotice() {
        return valueDatedNotice;
    }

    public void setValueDatedNotice(String valueDatedNotice) {
        this.valueDatedNotice = valueDatedNotice;
    }

    public String getWaiverOfChargesOrInterest() {
        return waiverOfChargesOrInterest;
    }

    public void setWaiverOfChargesOrInterest(String waiverOfChargesOrInterest) {
        this.waiverOfChargesOrInterest = waiverOfChargesOrInterest;
    }

    /** Creates a new instance of LED */
    public LED() {
        
    }

    //Functionality for led.jsp file
    public void descNonConformingLoanPeriod() {
        schemeMaster.setMessage("");
        try {
            if (this.nonConformingLoanPeriod.equals("")) {
                this.setStNonConformingLoanPeriod("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.nonConformingLoanPeriod);
                this.setStNonConformingLoanPeriod(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStNonConformingLoanPeriod("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descNonConformingLoanAmount() {
        schemeMaster.setMessage("");
        try {
            if (this.nonConformingLoanAmount.equals("")) {
                this.setStNonConformingLoanAmount("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.nonConformingLoanAmount);
                this.setStNonConformingLoanAmount(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStNonConformingLoanAmount("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descDisbGreaterThanLoanAmount() {
        schemeMaster.setMessage("");
        try {
            if (this.disbGreaterThanLoanAmount.equals("")) {
                this.setStDisbGreaterThanLoanAmount("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.disbGreaterThanLoanAmount);
                this.setStDisbGreaterThanLoanAmount(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStDisbGreaterThanLoanAmount("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descDisbNotConformingToSchedule() {
        schemeMaster.setMessage("");
        try {
            if (this.disbNotConformingToSchedule.equals("")) {
                this.setStDisbNotConformingToSchedule("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.disbNotConformingToSchedule);
                this.setStDisbNotConformingToSchedule(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStDisbNotConformingToSchedule("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descDisbDateSanctExpiryDate() {
        schemeMaster.setMessage("");
        try {
            if (this.disbDateSanctExpiryDate.equals("")) {
                this.setStDisbDateSanctExpiryDate("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.disbDateSanctExpiryDate);
                this.setStDisbDateSanctExpiryDate(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStDisbDateSanctExpiryDate("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descIntCalculationNotUpToDate() {
        schemeMaster.setMessage("");
        try {
            if (this.intCalculationNotUpToDate.equals("")) {
                this.setStIntCalculationNotUpToDate("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.intCalculationNotUpToDate);
                this.setStIntCalculationNotUpToDate(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStIntCalculationNotUpToDate("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descTransferAmountIsGreaterThanCrBalance() {
        schemeMaster.setMessage("");
        try {
            if (this.transferAmountIsGreaterThanCrBalance.equals("")) {
                this.setStTransferAmountIsGreaterThanCrBalance("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.transferAmountIsGreaterThanCrBalance);
                this.setStTransferAmountIsGreaterThanCrBalance(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStTransferAmountIsGreaterThanCrBalance("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCustIdDiffForLoanAndOpAccount() {
        schemeMaster.setMessage("");
        try {
            if (this.custIdDiffForLoanAndOpAccount.equals("")) {
                this.setStCustIdDiffForLoanAndOpAccount("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.custIdDiffForLoanAndOpAccount);
                this.setStCustIdDiffForLoanAndOpAccount(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStCustIdDiffForLoanAndOpAccount("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descInterestCollectedExceedsLimit() {
        schemeMaster.setMessage("");
        try {
            if (this.interestCollectedExceedsLimit.equals("")) {
                this.setStInterestCollectedExceedsLimit("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.interestCollectedExceedsLimit);
                this.setStInterestCollectedExceedsLimit(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStInterestCollectedExceedsLimit("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descWaiverOfChargesOrInterest() {
        schemeMaster.setMessage("");
        try {
            if (this.waiverOfChargesOrInterest.equals("")) {
                this.setStWaiverOfChargesOrInterest("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.waiverOfChargesOrInterest);
                this.setStWaiverOfChargesOrInterest(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStWaiverOfChargesOrInterest("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descOverrideSystemCalcEiAmount() {
        schemeMaster.setMessage("");
        try {
            if (this.overrideSystemCalcEiAmount.equals("")) {
                this.setStOverrideSystemCalcEiAmount("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.overrideSystemCalcEiAmount);
                this.setStOverrideSystemCalcEiAmount(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStOverrideSystemCalcEiAmount("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descPendingSchedulePayments() {
        schemeMaster.setMessage("");
        try {
            if (this.pendingSchedulePayments.equals("")) {
                this.setStPendingSchedulePayments("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.pendingSchedulePayments);
                this.setStPendingSchedulePayments(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStPendingSchedulePayments("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descRepaymentPerdNotEquelToLoanPerd() {
        schemeMaster.setMessage("");
        try {
            if (this.repaymentPerdNotEquelToLoanPerd.equals("")) {
                this.setStRepaymentPerdNotEquelToLoanPerd("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.repaymentPerdNotEquelToLoanPerd);
                this.setStRepaymentPerdNotEquelToLoanPerd(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStRepaymentPerdNotEquelToLoanPerd("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descRephasementInterestCalcNotUpToDate() {
        schemeMaster.setMessage("");
        try {
            if (this.rephasementInterestCalcNotUpToDate.equals("")) {
                this.setStRephasementInterestCalcNotUpToDate("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.rephasementInterestCalcNotUpToDate);
                this.setStRephasementInterestCalcNotUpToDate(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStRephasementInterestCalcNotUpToDate("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descMaxHolidayPeriodExceeded() {
        schemeMaster.setMessage("");
        try {
            if (this.maxHolidayPeriodExceeded.equals("")) {
                this.setStMaxHolidayPeriodExceeded("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.maxHolidayPeriodExceeded);
                this.setStMaxHolidayPeriodExceeded(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStMaxHolidayPeriodExceeded("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descPrepaymentNotAsPerNotice() {
        schemeMaster.setMessage("");
        try {
            if (this.prepaymentNotAsPerNotice.equals("")) {
                this.setStPrepaymentNotAsPerNotice("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.prepaymentNotAsPerNotice);
                this.setStPrepaymentNotAsPerNotice(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStPrepaymentNotAsPerNotice("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descValueDatedNotice() {
        schemeMaster.setMessage("");
        try {
            if (this.valueDatedNotice.equals("")) {
                this.setStValueDatedNotice("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.valueDatedNotice);
                this.setStValueDatedNotice(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStValueDatedNotice("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descBackValueDatedAccountOpening() {
        schemeMaster.setMessage("");
        try {
            if (this.backValueDatedAccountOpening.equals("")) {
                this.setStBackValueDatedAccountOpening("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.backValueDatedAccountOpening);
                this.setStBackValueDatedAccountOpening(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStBackValueDatedAccountOpening("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void selectLedDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeLoanExceptionDetailsTO singleTO = schemeMasterManagementDelegate.getLedDetails(schemeMaster.getSchemeCode());
            if (singleTO != null) {
                if (singleTO.getNonConformingLoanPeriod() == null || singleTO.getNonConformingLoanPeriod().equalsIgnoreCase("")) {
                    this.setNonConformingLoanPeriod("");
                } else {
                    this.setNonConformingLoanPeriod(singleTO.getNonConformingLoanPeriod());
                    descNonConformingLoanPeriod();
                }
                if (singleTO.getNonConformingLoanAmount() == null || singleTO.getNonConformingLoanAmount().equalsIgnoreCase("")) {
                    this.setNonConformingLoanAmount("");
                } else {
                    this.setNonConformingLoanAmount(singleTO.getNonConformingLoanAmount());
                    descNonConformingLoanAmount();
                }
                if (singleTO.getDisbGreaterThanLoanAmount() == null || singleTO.getDisbGreaterThanLoanAmount().equalsIgnoreCase("")) {
                    this.setDisbGreaterThanLoanAmount("");
                } else {
                    this.setDisbGreaterThanLoanAmount(singleTO.getDisbGreaterThanLoanAmount());
                    descDisbGreaterThanLoanAmount();
                }
                if (singleTO.getDisbNotConformingToSchedule() == null || singleTO.getDisbNotConformingToSchedule().equalsIgnoreCase("")) {
                    this.setDisbNotConformingToSchedule("");
                } else {
                    this.setDisbNotConformingToSchedule(singleTO.getDisbNotConformingToSchedule());
                    descDisbNotConformingToSchedule();
                }
                if (singleTO.getDisbDateSanctExpiryDate() == null || singleTO.getDisbDateSanctExpiryDate().equalsIgnoreCase("")) {
                    this.setDisbDateSanctExpiryDate("");
                } else {
                    this.setDisbDateSanctExpiryDate(singleTO.getDisbDateSanctExpiryDate());
                    descDisbDateSanctExpiryDate();
                }
                if (singleTO.getIntCalculationNotUpToDate() == null || singleTO.getIntCalculationNotUpToDate().equalsIgnoreCase("")) {
                    this.setIntCalculationNotUpToDate("");
                } else {
                    this.setIntCalculationNotUpToDate(singleTO.getIntCalculationNotUpToDate());
                    descIntCalculationNotUpToDate();
                }
                if (singleTO.getTransferAmountIsGreaterThanCrBalance() == null || singleTO.getTransferAmountIsGreaterThanCrBalance().equalsIgnoreCase("")) {
                    this.setTransferAmountIsGreaterThanCrBalance("");
                } else {
                    this.setTransferAmountIsGreaterThanCrBalance(singleTO.getTransferAmountIsGreaterThanCrBalance());
                    descTransferAmountIsGreaterThanCrBalance();
                }
                if (singleTO.getCustIdDiffForLoanAndOpAccount() == null || singleTO.getCustIdDiffForLoanAndOpAccount().equalsIgnoreCase("")) {
                    this.setCustIdDiffForLoanAndOpAccount("");
                } else {
                    this.setCustIdDiffForLoanAndOpAccount(singleTO.getCustIdDiffForLoanAndOpAccount());
                    descCustIdDiffForLoanAndOpAccount();
                }
                if (singleTO.getInterestCollectedExceedsLimit() == null || singleTO.getInterestCollectedExceedsLimit().equalsIgnoreCase("")) {
                    this.setInterestCollectedExceedsLimit("");
                } else {
                    this.setInterestCollectedExceedsLimit(singleTO.getInterestCollectedExceedsLimit());
                    descInterestCollectedExceedsLimit();
                }
                if (singleTO.getWaiverOfChargesOrInterest() == null || singleTO.getWaiverOfChargesOrInterest().equalsIgnoreCase("")) {
                    this.setWaiverOfChargesOrInterest("");
                } else {
                    this.setWaiverOfChargesOrInterest(singleTO.getWaiverOfChargesOrInterest());
                    descWaiverOfChargesOrInterest();
                }
                if (singleTO.getOverrideSystemCalcEiAmount() == null || singleTO.getOverrideSystemCalcEiAmount().equalsIgnoreCase("")) {
                    this.setOverrideSystemCalcEiAmount("");
                } else {
                    this.setOverrideSystemCalcEiAmount(singleTO.getOverrideSystemCalcEiAmount());
                    descOverrideSystemCalcEiAmount();
                }
                if (singleTO.getPendingSchedulePayments() == null || singleTO.getPendingSchedulePayments().equalsIgnoreCase("")) {
                    this.setPendingSchedulePayments("");
                } else {
                    this.setPendingSchedulePayments(singleTO.getPendingSchedulePayments());
                    descPendingSchedulePayments();
                }
                if (singleTO.getRepaymentPerdNotEqualToLoanPerd() == null || singleTO.getRepaymentPerdNotEqualToLoanPerd().equalsIgnoreCase("")) {
                    this.setRepaymentPerdNotEquelToLoanPerd("");
                } else {
                    this.setRepaymentPerdNotEquelToLoanPerd(singleTO.getRepaymentPerdNotEqualToLoanPerd());
                    descRepaymentPerdNotEquelToLoanPerd();
                }
                if (singleTO.getRephasementInterestCalcNotUpToDate() == null || singleTO.getRephasementInterestCalcNotUpToDate().equalsIgnoreCase("")) {
                    this.setRephasementInterestCalcNotUpToDate("");
                } else {
                    this.setRephasementInterestCalcNotUpToDate(singleTO.getRephasementInterestCalcNotUpToDate());
                    descRephasementInterestCalcNotUpToDate();
                }
                if (singleTO.getMaxHolidayPeriodExceeded() == null || singleTO.getMaxHolidayPeriodExceeded().equalsIgnoreCase("")) {
                    this.setMaxHolidayPeriodExceeded("");
                } else {
                    this.setMaxHolidayPeriodExceeded(singleTO.getMaxHolidayPeriodExceeded());
                    descMaxHolidayPeriodExceeded();
                }
                if (singleTO.getPrepaymentNotAsPerNotice() == null || singleTO.getPrepaymentNotAsPerNotice().equalsIgnoreCase("")) {
                    this.setPrepaymentNotAsPerNotice("");
                } else {
                    this.setPrepaymentNotAsPerNotice(singleTO.getPrepaymentNotAsPerNotice());
                    descPrepaymentNotAsPerNotice();
                }
                if (singleTO.getValueDatedNotice() == null || singleTO.getValueDatedNotice().equalsIgnoreCase("")) {
                    this.setValueDatedNotice("");
                } else {
                    this.setValueDatedNotice(singleTO.getValueDatedNotice());
                    descValueDatedNotice();
                }
                if (singleTO.getBackValueDatedAccountOpening() == null || singleTO.getBackValueDatedAccountOpening().equalsIgnoreCase("")) {
                    this.setBackValueDatedAccountOpening("");
                } else {
                    this.setBackValueDatedAccountOpening(singleTO.getBackValueDatedAccountOpening());
                    descBackValueDatedAccountOpening();
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

    public void refreshLedForm() {
        this.setNonConformingLoanPeriod("");
        this.setNonConformingLoanAmount("");
        this.setDisbGreaterThanLoanAmount("");
        this.setDisbNotConformingToSchedule("");
        this.setDisbDateSanctExpiryDate("");
        this.setIntCalculationNotUpToDate("");
        this.setTransferAmountIsGreaterThanCrBalance("");
        this.setCustIdDiffForLoanAndOpAccount("");
        this.setInterestCollectedExceedsLimit("");
        this.setWaiverOfChargesOrInterest("");
        this.setOverrideSystemCalcEiAmount("");
        this.setPendingSchedulePayments("");
        this.setRepaymentPerdNotEquelToLoanPerd("");
        this.setRephasementInterestCalcNotUpToDate("");
        this.setMaxHolidayPeriodExceeded("");
        this.setPrepaymentNotAsPerNotice("");
        this.setValueDatedNotice("");
        this.setBackValueDatedAccountOpening("");
        this.setStNonConformingLoanPeriod("");
        this.setStNonConformingLoanAmount("");
        this.setStDisbGreaterThanLoanAmount("");
        this.setStDisbNotConformingToSchedule("");
        this.setStDisbDateSanctExpiryDate("");
        this.setStIntCalculationNotUpToDate("");
        this.setStTransferAmountIsGreaterThanCrBalance("");
        this.setStCustIdDiffForLoanAndOpAccount("");
        this.setStInterestCollectedExceedsLimit("");
        this.setStWaiverOfChargesOrInterest("");
        this.setStOverrideSystemCalcEiAmount("");
        this.setStPendingSchedulePayments("");
        this.setStRepaymentPerdNotEquelToLoanPerd("");
        this.setStRephasementInterestCalcNotUpToDate("");
        this.setStMaxHolidayPeriodExceeded("");
        this.setStPrepaymentNotAsPerNotice("");
        this.setStValueDatedNotice("");
        this.setStBackValueDatedAccountOpening("");
    }

    public void enableLedForm() {
        this.ledFlag = false;
    }

    public void disableLedForm() {
        this.ledFlag = true;
    }
}
