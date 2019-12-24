/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_general_scheme_parameter_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findAll", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findBySchemeCode", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findBySchemeType", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findBySchemeDescription", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.schemeDescription = :schemeDescription"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByNativeSchemeDescription", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.nativeSchemeDescription = :nativeSchemeDescription"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByRptTranFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.rptTranFlag = :rptTranFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findBySchemeSupervisorId", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.schemeSupervisorId = :schemeSupervisorId"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStmtFreqType", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.stmtFreqType = :stmtFreqType"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStmtFreqWeekNumber", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.stmtFreqWeekNumber = :stmtFreqWeekNumber"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStmtFreqWeekDay", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.stmtFreqWeekDay = :stmtFreqWeekDay"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStmtFreqStartDate", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.stmtFreqStartDate = :stmtFreqStartDate"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStmtFreqNp", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.stmtFreqNp = :stmtFreqNp"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findBySchemeCrDrInd", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.schemeCrDrInd = :schemeCrDrInd"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTurnOverDetailFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.turnOverDetailFlag = :turnOverDetailFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findBySysGenAccNoFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.sysGenAccNoFlag = :sysGenAccNoFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTurnOverFreqType", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.turnOverFreqType = :turnOverFreqType"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTurnOverFreqWeekNumber", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.turnOverFreqWeekNumber = :turnOverFreqWeekNumber"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTurnOverFreqWeekDay", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.turnOverFreqWeekDay = :turnOverFreqWeekDay"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTurnOverFreqStartDate", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.turnOverFreqStartDate = :turnOverFreqStartDate"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTurnOverFreqNp", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.turnOverFreqNp = :turnOverFreqNp"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAcctPrefix", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.acctPrefix = :acctPrefix"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByNxtNbrTblCode", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.nxtNbrTblCode = :nxtNbrTblCode"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByFcnrSchemeFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.fcnrSchemeFlag = :fcnrSchemeFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAcctClosuerAcrossSolsAlwdFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.acctClosuerAcrossSolsAlwdFlag = :acctClosuerAcrossSolsAlwdFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByEefcSchemeFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.eefcSchemeFlag = :eefcSchemeFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStatusOption", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.statusOption = :statusOption"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByFdCrAcctPlaceHolder", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.fdCrAcctPlaceHolder = :fdCrAcctPlaceHolder"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByCommitCalculationMethod", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.commitCalculationMethod = :commitCalculationMethod"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByFdDrAcctPlaceHolder", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.fdDrAcctPlaceHolder = :fdDrAcctPlaceHolder"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByMinCommitUtilisation", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.minCommitUtilisation = :minCommitUtilisation"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStaffSchemeFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.staffSchemeFlag = :staffSchemeFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByDormantChargePeriodMonths", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.dormantChargePeriodMonths = :dormantChargePeriodMonths"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByDormantChargePeriodDays", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.dormantChargePeriodDays = :dormantChargePeriodDays"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByNreSchemeFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.nreSchemeFlag = :nreSchemeFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByInactiveChargePeriodMonths", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.inactiveChargePeriodMonths = :inactiveChargePeriodMonths"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByInactiveChargePeriodDays", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.inactiveChargePeriodDays = :inactiveChargePeriodDays"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByNewAccountDuration", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.newAccountDuration = :newAccountDuration"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByProductGroup", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.productGroup = :productGroup"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByMinimumPostWorkClass", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.minimumPostWorkClass = :minimumPostWorkClass"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByLinkTranToPurchaseSale", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.linkTranToPurchaseSale = :linkTranToPurchaseSale"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByMinimumAccountClosurePeriodMonths", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.minimumAccountClosurePeriodMonths = :minimumAccountClosurePeriodMonths"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByMinimumAccountClosurePeriodDays", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.minimumAccountClosurePeriodDays = :minimumAccountClosurePeriodDays"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAccountMaintenancePeriod", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.accountMaintenancePeriod = :accountMaintenancePeriod"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByDfltClgTranCode", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.dfltClgTranCode = :dfltClgTranCode"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByDfltInstrumentType", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.dfltInstrumentType = :dfltInstrumentType"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTransactionReferenceNumberFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.transactionReferenceNumberFlag = :transactionReferenceNumberFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByPegInterestForAccountFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.pegInterestForAccountFlag = :pegInterestForAccountFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByModificationofPegAllowedFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.modificationofPegAllowedFlag = :modificationofPegAllowedFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByPegRevCustPrefFromCustMastFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.pegRevCustPrefFromCustMastFlag = :pegRevCustPrefFromCustMastFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAcctPartitionAllowed", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.acctPartitionAllowed = :acctPartitionAllowed"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAutoRenewalPeriodFlag", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.autoRenewalPeriodFlag = :autoRenewalPeriodFlag"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByPdGlSubheadCode", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.pdGlSubheadCode = :pdGlSubheadCode"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStmtMessage", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.stmtMessage = :stmtMessage"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByNativeStmtMessage", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.nativeStmtMessage = :nativeStmtMessage"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByCreatedByUserId", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.createdByUserId = :createdByUserId"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByCreationDate", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByLastUpdatedByUserId", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.lastUpdatedByUserId = :lastUpdatedByUserId"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByLastUpdatedDate", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.lastUpdatedDate = :lastUpdatedDate"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTotalModifications", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.totalModifications = :totalModifications"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByLiabilityExceedsGroupLimitExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.liabilityExceedsGroupLimitExcep = :liabilityExceedsGroupLimitExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByInsufficientAvailableBalanceExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.insufficientAvailableBalanceExcep = :insufficientAvailableBalanceExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAccountNameChangeExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.accountNameChangeExcep = :accountNameChangeExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByStaleInstrumentExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.staleInstrumentExcep = :staleInstrumentExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAccountFrozenExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.accountFrozenExcep = :accountFrozenExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByCustomerIdMismatchDr", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.customerIdMismatchDr = :customerIdMismatchDr"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByCustomerIdMismatchCr", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.customerIdMismatchCr = :customerIdMismatchCr"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByBackDatedTransactionExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.backDatedTransactionExcep = :backDatedTransactionExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByValueDateTransactionExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.valueDateTransactionExcep = :valueDateTransactionExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByCashTransactionExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.cashTransactionExcep = :cashTransactionExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAccountOpenMatrixExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.accountOpenMatrixExcep = :accountOpenMatrixExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByClearingTransactionExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.clearingTransactionExcep = :clearingTransactionExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByTransferTransactionExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.transferTransactionExcep = :transferTransactionExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findBySanctionedLimitExpiredExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.sanctionedLimitExpiredExcep = :sanctionedLimitExpiredExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findBySanctionedExceedsLimitExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.sanctionedExceedsLimitExcep = :sanctionedExceedsLimitExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByCustDrWithoutCheque", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.custDrWithoutCheque = :custDrWithoutCheque"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByRefferedAccountClosure", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.refferedAccountClosure = :refferedAccountClosure"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByUserTodGrantException", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.userTodGrantException = :userTodGrantException"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAutoTodGrantException", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.autoTodGrantException = :autoTodGrantException"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAccountInDrBalanceExp", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.accountInDrBalanceExp = :accountInDrBalanceExp"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAccountInCrBalanceExp", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.accountInCrBalanceExp = :accountInCrBalanceExp"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByOverrideDfLtCheque", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.overrideDfLtCheque = :overrideDfLtCheque"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByInvalidReportCodeExcep", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.invalidReportCodeExcep = :invalidReportCodeExcep"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByMemoPadExists", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.memoPadExists = :memoPadExists"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByDfltIntParmChngException", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.dfltIntParmChngException = :dfltIntParmChngException"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByAcctBalBelowTheReqMin", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.acctBalBelowTheReqMin = :acctBalBelowTheReqMin"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByMinBalPenalChrgNotCalc", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.minBalPenalChrgNotCalc = :minBalPenalChrgNotCalc"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByIntForPastDueNotUpToDate", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.intForPastDueNotUpToDate = :intForPastDueNotUpToDate"),
    @NamedQuery(name = "CbsSchemeGeneralSchemeParameterMaster.findByDrTranToPastDueAsset", query = "SELECT c FROM CbsSchemeGeneralSchemeParameterMaster c WHERE c.drTranToPastDueAsset = :drTranToPastDueAsset")})
public class CbsSchemeGeneralSchemeParameterMaster extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 80)
    @Column(name = "SCHEME_DESCRIPTION")
    private String schemeDescription;
    @Size(max = 80)
    @Column(name = "NATIVE_SCHEME_DESCRIPTION")
    private String nativeSchemeDescription;
    @Size(max = 1)
    @Column(name = "RPT_TRAN_FLAG")
    private String rptTranFlag;
    @Size(max = 12)
    @Column(name = "SCHEME_SUPERVISOR_ID")
    private String schemeSupervisorId;
    @Size(max = 1)
    @Column(name = "STMT_FREQ_TYPE")
    private String stmtFreqType;
    @Size(max = 1)
    @Column(name = "STMT_FREQ_WEEK_NUMBER")
    private String stmtFreqWeekNumber;
    @Size(max = 1)
    @Column(name = "STMT_FREQ_WEEK_DAY")
    private String stmtFreqWeekDay;
    @Size(max = 2)
    @Column(name = "STMT_FREQ_START_DATE")
    private String stmtFreqStartDate;
    @Size(max = 1)
    @Column(name = "STMT_FREQ_NP")
    private String stmtFreqNp;
    @Size(max = 1)
    @Column(name = "SCHEME_CR_DR_IND")
    private String schemeCrDrInd;
    @Size(max = 1)
    @Column(name = "TURN_OVER_DETAIL_FLAG")
    private String turnOverDetailFlag;
    @Size(max = 1)
    @Column(name = "SYS_GEN_ACC_NO_FLAG")
    private String sysGenAccNoFlag;
    @Size(max = 1)
    @Column(name = "TURN_OVER_FREQ_TYPE")
    private String turnOverFreqType;
    @Size(max = 1)
    @Column(name = "TURN_OVER_FREQ_WEEK_NUMBER")
    private String turnOverFreqWeekNumber;
    @Size(max = 1)
    @Column(name = "TURN_OVER_FREQ_WEEK_DAY")
    private String turnOverFreqWeekDay;
    @Size(max = 2)
    @Column(name = "TURN_OVER_FREQ_START_DATE")
    private String turnOverFreqStartDate;
    @Size(max = 1)
    @Column(name = "TURN_OVER_FREQ_NP")
    private String turnOverFreqNp;
    @Size(max = 2)
    @Column(name = "ACCT_PREFIX")
    private String acctPrefix;
    @Size(max = 200)
    @Column(name = "NXT_NBR_TBL_CODE")
    private String nxtNbrTblCode;
    @Size(max = 1)
    @Column(name = "FCNR_SCHEME_FLAG")
    private String fcnrSchemeFlag;
    @Size(max = 1)
    @Column(name = "ACCT_CLOSUER_ACROSS_SOLS_ALWD_FLAG")
    private String acctClosuerAcrossSolsAlwdFlag;
    @Size(max = 1)
    @Column(name = "EEFC_SCHEME_FLAG")
    private String eefcSchemeFlag;
    @Size(max = 1)
    @Column(name = "STATUS_OPTION")
    private String statusOption;
    @Size(max = 12)
    @Column(name = "FD_CR_ACCT_PLACE_HOLDER")
    private String fdCrAcctPlaceHolder;
    @Size(max = 1)
    @Column(name = "COMMIT_CALCULATION_METHOD")
    private String commitCalculationMethod;
    @Size(max = 12)
    @Column(name = "FD_DR_ACCT_PLACE_HOLDER")
    private String fdDrAcctPlaceHolder;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MIN_COMMIT_UTILISATION")
    private BigDecimal minCommitUtilisation;
    @Size(max = 1)
    @Column(name = "STAFF_SCHEME_FLAG")
    private String staffSchemeFlag;
    @Size(max = 2)
    @Column(name = "DORMANT_CHARGE_PERIOD_MONTHS")
    private String dormantChargePeriodMonths;
    @Size(max = 3)
    @Column(name = "DORMANT_CHARGE_PERIOD_DAYS")
    private String dormantChargePeriodDays;
    @Size(max = 1)
    @Column(name = "NRE_SCHEME_FLAG")
    private String nreSchemeFlag;
    @Size(max = 2)
    @Column(name = "INACTIVE_CHARGE_PERIOD_MONTHS")
    private String inactiveChargePeriodMonths;
    @Size(max = 3)
    @Column(name = "INACTIVE_CHARGE_PERIOD_DAYS")
    private String inactiveChargePeriodDays;
    @Size(max = 2)
    @Column(name = "NEW_ACCOUNT_DURATION")
    private String newAccountDuration;
    @Size(max = 3)
    @Column(name = "PRODUCT_GROUP")
    private String productGroup;
    @Size(max = 5)
    @Column(name = "MINIMUM_POST_WORK_CLASS")
    private String minimumPostWorkClass;
    @Size(max = 1)
    @Column(name = "LINK_TRAN_TO_PURCHASE_SALE")
    private String linkTranToPurchaseSale;
    @Size(max = 2)
    @Column(name = "MINIMUM_ACCOUNT_CLOSURE_PERIOD_MONTHS")
    private String minimumAccountClosurePeriodMonths;
    @Size(max = 3)
    @Column(name = "MINIMUM_ACCOUNT_CLOSURE_PERIOD_DAYS")
    private String minimumAccountClosurePeriodDays;
    @Size(max = 1)
    @Column(name = "ACCOUNT_MAINTENANCE_PERIOD")
    private String accountMaintenancePeriod;
    @Size(max = 12)
    @Column(name = "DFLT_CLG_TRAN_CODE")
    private String dfltClgTranCode;
    @Size(max = 3)
    @Column(name = "DFLT_INSTRUMENT_TYPE")
    private String dfltInstrumentType;
    @Size(max = 1)
    @Column(name = "TRANSACTION_REFERENCE_NUMBER_FLAG")
    private String transactionReferenceNumberFlag;
    @Size(max = 1)
    @Column(name = "PEG_INTEREST_FOR_ACCOUNT_FLAG")
    private String pegInterestForAccountFlag;
    @Size(max = 1)
    @Column(name = "MODIFICATIONOF_PEG_ALLOWED_FLAG")
    private String modificationofPegAllowedFlag;
    @Size(max = 1)
    @Column(name = "PEG_REV_CUST_PREF_FROM_CUST_MAST_FLAG")
    private String pegRevCustPrefFromCustMastFlag;
    @Size(max = 1)
    @Column(name = "ACCT_PARTITION_ALLOWED")
    private String acctPartitionAllowed;
    @Size(max = 1)
    @Column(name = "AUTO_RENEWAL_PERIOD_FLAG")
    private String autoRenewalPeriodFlag;
    @Size(max = 12)
    @Column(name = "PD_GL_SUBHEAD_CODE")
    private String pdGlSubheadCode;
    @Size(max = 100)
    @Column(name = "STMT_MESSAGE")
    private String stmtMessage;
    @Size(max = 100)
    @Column(name = "NATIVE_STMT_MESSAGE")
    private String nativeStmtMessage;
    @Size(max = 15)
    @Column(name = "CREATED_BY_USER_ID")
    private String createdByUserId;
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Size(max = 15)
    @Column(name = "LAST_UPDATED_BY_USER_ID")
    private String lastUpdatedByUserId;
    @Column(name = "LAST_UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;
    @Column(name = "TOTAL_MODIFICATIONS")
    private Integer totalModifications;
    @Size(max = 3)
    @Column(name = "LIABILITY_EXCEEDS_GROUP_LIMIT_EXCEP")
    private String liabilityExceedsGroupLimitExcep;
    @Size(max = 3)
    @Column(name = "INSUFFICIENT_AVAILABLE_BALANCE_EXCEP")
    private String insufficientAvailableBalanceExcep;
    @Size(max = 3)
    @Column(name = "ACCOUNT_NAME_CHANGE_EXCEP")
    private String accountNameChangeExcep;
    @Size(max = 3)
    @Column(name = "STALE_INSTRUMENT_EXCEP")
    private String staleInstrumentExcep;
    @Size(max = 3)
    @Column(name = "ACCOUNT_FROZEN_EXCEP")
    private String accountFrozenExcep;
    @Size(max = 3)
    @Column(name = "CUSTOMER_ID_MISMATCH_DR")
    private String customerIdMismatchDr;
    @Size(max = 3)
    @Column(name = "CUSTOMER_ID_MISMATCH_CR")
    private String customerIdMismatchCr;
    @Size(max = 3)
    @Column(name = "BACK_DATED_TRANSACTION_EXCEP")
    private String backDatedTransactionExcep;
    @Size(max = 3)
    @Column(name = "VALUE_DATE_TRANSACTION_EXCEP")
    private String valueDateTransactionExcep;
    @Size(max = 3)
    @Column(name = "CASH_TRANSACTION_EXCEP")
    private String cashTransactionExcep;
    @Size(max = 3)
    @Column(name = "ACCOUNT_OPEN_MATRIX_EXCEP")
    private String accountOpenMatrixExcep;
    @Size(max = 3)
    @Column(name = "CLEARING_TRANSACTION_EXCEP")
    private String clearingTransactionExcep;
    @Size(max = 3)
    @Column(name = "TRANSFER_TRANSACTION_EXCEP")
    private String transferTransactionExcep;
    @Size(max = 3)
    @Column(name = "SANCTIONED_LIMIT_EXPIRED_EXCEP")
    private String sanctionedLimitExpiredExcep;
    @Size(max = 3)
    @Column(name = "SANCTIONED_EXCEEDS_LIMIT_EXCEP")
    private String sanctionedExceedsLimitExcep;
    @Size(max = 3)
    @Column(name = "CUST_DR_WITHOUT_CHEQUE")
    private String custDrWithoutCheque;
    @Size(max = 3)
    @Column(name = "REFFERED_ACCOUNT_CLOSURE")
    private String refferedAccountClosure;
    @Size(max = 3)
    @Column(name = "USER_TOD_GRANT_EXCEPTION")
    private String userTodGrantException;
    @Size(max = 3)
    @Column(name = "AUTO_TOD_GRANT_EXCEPTION")
    private String autoTodGrantException;
    @Size(max = 3)
    @Column(name = "ACCOUNT_IN_DR_BALANCE_EXP")
    private String accountInDrBalanceExp;
    @Size(max = 3)
    @Column(name = "ACCOUNT_IN_CR_BALANCE_EXP")
    private String accountInCrBalanceExp;
    @Size(max = 3)
    @Column(name = "OVERRIDE_DF_LT_CHEQUE")
    private String overrideDfLtCheque;
    @Size(max = 3)
    @Column(name = "INVALID_REPORT_CODE_EXCEP")
    private String invalidReportCodeExcep;
    @Size(max = 3)
    @Column(name = "MEMO_PAD_EXISTS")
    private String memoPadExists;
    @Size(max = 3)
    @Column(name = "DFLT_INT_PARM_CHNG_EXCEPTION")
    private String dfltIntParmChngException;
    @Size(max = 3)
    @Column(name = "ACCT_BAL_BELOW_THE_REQ_MIN")
    private String acctBalBelowTheReqMin;
    @Size(max = 3)
    @Column(name = "MIN_BAL_PENAL_CHRG_NOT_CALC")
    private String minBalPenalChrgNotCalc;
    @Size(max = 3)
    @Column(name = "INT_FOR_PAST_DUE_NOT_UP_TO_DATE")
    private String intForPastDueNotUpToDate;
    @Size(max = 3)
    @Column(name = "DR_TRAN_TO_PAST_DUE_ASSET")
    private String drTranToPastDueAsset;

    public CbsSchemeGeneralSchemeParameterMaster() {
    }

    public CbsSchemeGeneralSchemeParameterMaster(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getSchemeDescription() {
        return schemeDescription;
    }

    public void setSchemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
    }

    public String getNativeSchemeDescription() {
        return nativeSchemeDescription;
    }

    public void setNativeSchemeDescription(String nativeSchemeDescription) {
        this.nativeSchemeDescription = nativeSchemeDescription;
    }

    public String getRptTranFlag() {
        return rptTranFlag;
    }

    public void setRptTranFlag(String rptTranFlag) {
        this.rptTranFlag = rptTranFlag;
    }

    public String getSchemeSupervisorId() {
        return schemeSupervisorId;
    }

    public void setSchemeSupervisorId(String schemeSupervisorId) {
        this.schemeSupervisorId = schemeSupervisorId;
    }

    public String getStmtFreqType() {
        return stmtFreqType;
    }

    public void setStmtFreqType(String stmtFreqType) {
        this.stmtFreqType = stmtFreqType;
    }

    public String getStmtFreqWeekNumber() {
        return stmtFreqWeekNumber;
    }

    public void setStmtFreqWeekNumber(String stmtFreqWeekNumber) {
        this.stmtFreqWeekNumber = stmtFreqWeekNumber;
    }

    public String getStmtFreqWeekDay() {
        return stmtFreqWeekDay;
    }

    public void setStmtFreqWeekDay(String stmtFreqWeekDay) {
        this.stmtFreqWeekDay = stmtFreqWeekDay;
    }

    public String getStmtFreqStartDate() {
        return stmtFreqStartDate;
    }

    public void setStmtFreqStartDate(String stmtFreqStartDate) {
        this.stmtFreqStartDate = stmtFreqStartDate;
    }

    public String getStmtFreqNp() {
        return stmtFreqNp;
    }

    public void setStmtFreqNp(String stmtFreqNp) {
        this.stmtFreqNp = stmtFreqNp;
    }

    public String getSchemeCrDrInd() {
        return schemeCrDrInd;
    }

    public void setSchemeCrDrInd(String schemeCrDrInd) {
        this.schemeCrDrInd = schemeCrDrInd;
    }

    public String getTurnOverDetailFlag() {
        return turnOverDetailFlag;
    }

    public void setTurnOverDetailFlag(String turnOverDetailFlag) {
        this.turnOverDetailFlag = turnOverDetailFlag;
    }

    public String getSysGenAccNoFlag() {
        return sysGenAccNoFlag;
    }

    public void setSysGenAccNoFlag(String sysGenAccNoFlag) {
        this.sysGenAccNoFlag = sysGenAccNoFlag;
    }

    public String getTurnOverFreqType() {
        return turnOverFreqType;
    }

    public void setTurnOverFreqType(String turnOverFreqType) {
        this.turnOverFreqType = turnOverFreqType;
    }

    public String getTurnOverFreqWeekNumber() {
        return turnOverFreqWeekNumber;
    }

    public void setTurnOverFreqWeekNumber(String turnOverFreqWeekNumber) {
        this.turnOverFreqWeekNumber = turnOverFreqWeekNumber;
    }

    public String getTurnOverFreqWeekDay() {
        return turnOverFreqWeekDay;
    }

    public void setTurnOverFreqWeekDay(String turnOverFreqWeekDay) {
        this.turnOverFreqWeekDay = turnOverFreqWeekDay;
    }

    public String getTurnOverFreqStartDate() {
        return turnOverFreqStartDate;
    }

    public void setTurnOverFreqStartDate(String turnOverFreqStartDate) {
        this.turnOverFreqStartDate = turnOverFreqStartDate;
    }

    public String getTurnOverFreqNp() {
        return turnOverFreqNp;
    }

    public void setTurnOverFreqNp(String turnOverFreqNp) {
        this.turnOverFreqNp = turnOverFreqNp;
    }

    public String getAcctPrefix() {
        return acctPrefix;
    }

    public void setAcctPrefix(String acctPrefix) {
        this.acctPrefix = acctPrefix;
    }

    public String getNxtNbrTblCode() {
        return nxtNbrTblCode;
    }

    public void setNxtNbrTblCode(String nxtNbrTblCode) {
        this.nxtNbrTblCode = nxtNbrTblCode;
    }

    public String getFcnrSchemeFlag() {
        return fcnrSchemeFlag;
    }

    public void setFcnrSchemeFlag(String fcnrSchemeFlag) {
        this.fcnrSchemeFlag = fcnrSchemeFlag;
    }

    public String getAcctClosuerAcrossSolsAlwdFlag() {
        return acctClosuerAcrossSolsAlwdFlag;
    }

    public void setAcctClosuerAcrossSolsAlwdFlag(String acctClosuerAcrossSolsAlwdFlag) {
        this.acctClosuerAcrossSolsAlwdFlag = acctClosuerAcrossSolsAlwdFlag;
    }

    public String getEefcSchemeFlag() {
        return eefcSchemeFlag;
    }

    public void setEefcSchemeFlag(String eefcSchemeFlag) {
        this.eefcSchemeFlag = eefcSchemeFlag;
    }

    public String getStatusOption() {
        return statusOption;
    }

    public void setStatusOption(String statusOption) {
        this.statusOption = statusOption;
    }

    public String getFdCrAcctPlaceHolder() {
        return fdCrAcctPlaceHolder;
    }

    public void setFdCrAcctPlaceHolder(String fdCrAcctPlaceHolder) {
        this.fdCrAcctPlaceHolder = fdCrAcctPlaceHolder;
    }

    public String getCommitCalculationMethod() {
        return commitCalculationMethod;
    }

    public void setCommitCalculationMethod(String commitCalculationMethod) {
        this.commitCalculationMethod = commitCalculationMethod;
    }

    public String getFdDrAcctPlaceHolder() {
        return fdDrAcctPlaceHolder;
    }

    public void setFdDrAcctPlaceHolder(String fdDrAcctPlaceHolder) {
        this.fdDrAcctPlaceHolder = fdDrAcctPlaceHolder;
    }

    public BigDecimal getMinCommitUtilisation() {
        return minCommitUtilisation;
    }

    public void setMinCommitUtilisation(BigDecimal minCommitUtilisation) {
        this.minCommitUtilisation = minCommitUtilisation;
    }

    public String getStaffSchemeFlag() {
        return staffSchemeFlag;
    }

    public void setStaffSchemeFlag(String staffSchemeFlag) {
        this.staffSchemeFlag = staffSchemeFlag;
    }

    public String getDormantChargePeriodMonths() {
        return dormantChargePeriodMonths;
    }

    public void setDormantChargePeriodMonths(String dormantChargePeriodMonths) {
        this.dormantChargePeriodMonths = dormantChargePeriodMonths;
    }

    public String getDormantChargePeriodDays() {
        return dormantChargePeriodDays;
    }

    public void setDormantChargePeriodDays(String dormantChargePeriodDays) {
        this.dormantChargePeriodDays = dormantChargePeriodDays;
    }

    public String getNreSchemeFlag() {
        return nreSchemeFlag;
    }

    public void setNreSchemeFlag(String nreSchemeFlag) {
        this.nreSchemeFlag = nreSchemeFlag;
    }

    public String getInactiveChargePeriodMonths() {
        return inactiveChargePeriodMonths;
    }

    public void setInactiveChargePeriodMonths(String inactiveChargePeriodMonths) {
        this.inactiveChargePeriodMonths = inactiveChargePeriodMonths;
    }

    public String getInactiveChargePeriodDays() {
        return inactiveChargePeriodDays;
    }

    public void setInactiveChargePeriodDays(String inactiveChargePeriodDays) {
        this.inactiveChargePeriodDays = inactiveChargePeriodDays;
    }

    public String getNewAccountDuration() {
        return newAccountDuration;
    }

    public void setNewAccountDuration(String newAccountDuration) {
        this.newAccountDuration = newAccountDuration;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getMinimumPostWorkClass() {
        return minimumPostWorkClass;
    }

    public void setMinimumPostWorkClass(String minimumPostWorkClass) {
        this.minimumPostWorkClass = minimumPostWorkClass;
    }

    public String getLinkTranToPurchaseSale() {
        return linkTranToPurchaseSale;
    }

    public void setLinkTranToPurchaseSale(String linkTranToPurchaseSale) {
        this.linkTranToPurchaseSale = linkTranToPurchaseSale;
    }

    public String getMinimumAccountClosurePeriodMonths() {
        return minimumAccountClosurePeriodMonths;
    }

    public void setMinimumAccountClosurePeriodMonths(String minimumAccountClosurePeriodMonths) {
        this.minimumAccountClosurePeriodMonths = minimumAccountClosurePeriodMonths;
    }

    public String getMinimumAccountClosurePeriodDays() {
        return minimumAccountClosurePeriodDays;
    }

    public void setMinimumAccountClosurePeriodDays(String minimumAccountClosurePeriodDays) {
        this.minimumAccountClosurePeriodDays = minimumAccountClosurePeriodDays;
    }

    public String getAccountMaintenancePeriod() {
        return accountMaintenancePeriod;
    }

    public void setAccountMaintenancePeriod(String accountMaintenancePeriod) {
        this.accountMaintenancePeriod = accountMaintenancePeriod;
    }

    public String getDfltClgTranCode() {
        return dfltClgTranCode;
    }

    public void setDfltClgTranCode(String dfltClgTranCode) {
        this.dfltClgTranCode = dfltClgTranCode;
    }

    public String getDfltInstrumentType() {
        return dfltInstrumentType;
    }

    public void setDfltInstrumentType(String dfltInstrumentType) {
        this.dfltInstrumentType = dfltInstrumentType;
    }

    public String getTransactionReferenceNumberFlag() {
        return transactionReferenceNumberFlag;
    }

    public void setTransactionReferenceNumberFlag(String transactionReferenceNumberFlag) {
        this.transactionReferenceNumberFlag = transactionReferenceNumberFlag;
    }

    public String getPegInterestForAccountFlag() {
        return pegInterestForAccountFlag;
    }

    public void setPegInterestForAccountFlag(String pegInterestForAccountFlag) {
        this.pegInterestForAccountFlag = pegInterestForAccountFlag;
    }

    public String getModificationofPegAllowedFlag() {
        return modificationofPegAllowedFlag;
    }

    public void setModificationofPegAllowedFlag(String modificationofPegAllowedFlag) {
        this.modificationofPegAllowedFlag = modificationofPegAllowedFlag;
    }

    public String getPegRevCustPrefFromCustMastFlag() {
        return pegRevCustPrefFromCustMastFlag;
    }

    public void setPegRevCustPrefFromCustMastFlag(String pegRevCustPrefFromCustMastFlag) {
        this.pegRevCustPrefFromCustMastFlag = pegRevCustPrefFromCustMastFlag;
    }

    public String getAcctPartitionAllowed() {
        return acctPartitionAllowed;
    }

    public void setAcctPartitionAllowed(String acctPartitionAllowed) {
        this.acctPartitionAllowed = acctPartitionAllowed;
    }

    public String getAutoRenewalPeriodFlag() {
        return autoRenewalPeriodFlag;
    }

    public void setAutoRenewalPeriodFlag(String autoRenewalPeriodFlag) {
        this.autoRenewalPeriodFlag = autoRenewalPeriodFlag;
    }

    public String getPdGlSubheadCode() {
        return pdGlSubheadCode;
    }

    public void setPdGlSubheadCode(String pdGlSubheadCode) {
        this.pdGlSubheadCode = pdGlSubheadCode;
    }

    public String getStmtMessage() {
        return stmtMessage;
    }

    public void setStmtMessage(String stmtMessage) {
        this.stmtMessage = stmtMessage;
    }

    public String getNativeStmtMessage() {
        return nativeStmtMessage;
    }

    public void setNativeStmtMessage(String nativeStmtMessage) {
        this.nativeStmtMessage = nativeStmtMessage;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedByUserId() {
        return lastUpdatedByUserId;
    }

    public void setLastUpdatedByUserId(String lastUpdatedByUserId) {
        this.lastUpdatedByUserId = lastUpdatedByUserId;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getTotalModifications() {
        return totalModifications;
    }

    public void setTotalModifications(Integer totalModifications) {
        this.totalModifications = totalModifications;
    }

    public String getLiabilityExceedsGroupLimitExcep() {
        return liabilityExceedsGroupLimitExcep;
    }

    public void setLiabilityExceedsGroupLimitExcep(String liabilityExceedsGroupLimitExcep) {
        this.liabilityExceedsGroupLimitExcep = liabilityExceedsGroupLimitExcep;
    }

    public String getInsufficientAvailableBalanceExcep() {
        return insufficientAvailableBalanceExcep;
    }

    public void setInsufficientAvailableBalanceExcep(String insufficientAvailableBalanceExcep) {
        this.insufficientAvailableBalanceExcep = insufficientAvailableBalanceExcep;
    }

    public String getAccountNameChangeExcep() {
        return accountNameChangeExcep;
    }

    public void setAccountNameChangeExcep(String accountNameChangeExcep) {
        this.accountNameChangeExcep = accountNameChangeExcep;
    }

    public String getStaleInstrumentExcep() {
        return staleInstrumentExcep;
    }

    public void setStaleInstrumentExcep(String staleInstrumentExcep) {
        this.staleInstrumentExcep = staleInstrumentExcep;
    }

    public String getAccountFrozenExcep() {
        return accountFrozenExcep;
    }

    public void setAccountFrozenExcep(String accountFrozenExcep) {
        this.accountFrozenExcep = accountFrozenExcep;
    }

    public String getCustomerIdMismatchDr() {
        return customerIdMismatchDr;
    }

    public void setCustomerIdMismatchDr(String customerIdMismatchDr) {
        this.customerIdMismatchDr = customerIdMismatchDr;
    }

    public String getCustomerIdMismatchCr() {
        return customerIdMismatchCr;
    }

    public void setCustomerIdMismatchCr(String customerIdMismatchCr) {
        this.customerIdMismatchCr = customerIdMismatchCr;
    }

    public String getBackDatedTransactionExcep() {
        return backDatedTransactionExcep;
    }

    public void setBackDatedTransactionExcep(String backDatedTransactionExcep) {
        this.backDatedTransactionExcep = backDatedTransactionExcep;
    }

    public String getValueDateTransactionExcep() {
        return valueDateTransactionExcep;
    }

    public void setValueDateTransactionExcep(String valueDateTransactionExcep) {
        this.valueDateTransactionExcep = valueDateTransactionExcep;
    }

    public String getCashTransactionExcep() {
        return cashTransactionExcep;
    }

    public void setCashTransactionExcep(String cashTransactionExcep) {
        this.cashTransactionExcep = cashTransactionExcep;
    }

    public String getAccountOpenMatrixExcep() {
        return accountOpenMatrixExcep;
    }

    public void setAccountOpenMatrixExcep(String accountOpenMatrixExcep) {
        this.accountOpenMatrixExcep = accountOpenMatrixExcep;
    }

    public String getClearingTransactionExcep() {
        return clearingTransactionExcep;
    }

    public void setClearingTransactionExcep(String clearingTransactionExcep) {
        this.clearingTransactionExcep = clearingTransactionExcep;
    }

    public String getTransferTransactionExcep() {
        return transferTransactionExcep;
    }

    public void setTransferTransactionExcep(String transferTransactionExcep) {
        this.transferTransactionExcep = transferTransactionExcep;
    }

    public String getSanctionedLimitExpiredExcep() {
        return sanctionedLimitExpiredExcep;
    }

    public void setSanctionedLimitExpiredExcep(String sanctionedLimitExpiredExcep) {
        this.sanctionedLimitExpiredExcep = sanctionedLimitExpiredExcep;
    }

    public String getSanctionedExceedsLimitExcep() {
        return sanctionedExceedsLimitExcep;
    }

    public void setSanctionedExceedsLimitExcep(String sanctionedExceedsLimitExcep) {
        this.sanctionedExceedsLimitExcep = sanctionedExceedsLimitExcep;
    }

    public String getCustDrWithoutCheque() {
        return custDrWithoutCheque;
    }

    public void setCustDrWithoutCheque(String custDrWithoutCheque) {
        this.custDrWithoutCheque = custDrWithoutCheque;
    }

    public String getRefferedAccountClosure() {
        return refferedAccountClosure;
    }

    public void setRefferedAccountClosure(String refferedAccountClosure) {
        this.refferedAccountClosure = refferedAccountClosure;
    }

    public String getUserTodGrantException() {
        return userTodGrantException;
    }

    public void setUserTodGrantException(String userTodGrantException) {
        this.userTodGrantException = userTodGrantException;
    }

    public String getAutoTodGrantException() {
        return autoTodGrantException;
    }

    public void setAutoTodGrantException(String autoTodGrantException) {
        this.autoTodGrantException = autoTodGrantException;
    }

    public String getAccountInDrBalanceExp() {
        return accountInDrBalanceExp;
    }

    public void setAccountInDrBalanceExp(String accountInDrBalanceExp) {
        this.accountInDrBalanceExp = accountInDrBalanceExp;
    }

    public String getAccountInCrBalanceExp() {
        return accountInCrBalanceExp;
    }

    public void setAccountInCrBalanceExp(String accountInCrBalanceExp) {
        this.accountInCrBalanceExp = accountInCrBalanceExp;
    }

    public String getOverrideDfLtCheque() {
        return overrideDfLtCheque;
    }

    public void setOverrideDfLtCheque(String overrideDfLtCheque) {
        this.overrideDfLtCheque = overrideDfLtCheque;
    }

    public String getInvalidReportCodeExcep() {
        return invalidReportCodeExcep;
    }

    public void setInvalidReportCodeExcep(String invalidReportCodeExcep) {
        this.invalidReportCodeExcep = invalidReportCodeExcep;
    }

    public String getMemoPadExists() {
        return memoPadExists;
    }

    public void setMemoPadExists(String memoPadExists) {
        this.memoPadExists = memoPadExists;
    }

    public String getDfltIntParmChngException() {
        return dfltIntParmChngException;
    }

    public void setDfltIntParmChngException(String dfltIntParmChngException) {
        this.dfltIntParmChngException = dfltIntParmChngException;
    }

    public String getAcctBalBelowTheReqMin() {
        return acctBalBelowTheReqMin;
    }

    public void setAcctBalBelowTheReqMin(String acctBalBelowTheReqMin) {
        this.acctBalBelowTheReqMin = acctBalBelowTheReqMin;
    }

    public String getMinBalPenalChrgNotCalc() {
        return minBalPenalChrgNotCalc;
    }

    public void setMinBalPenalChrgNotCalc(String minBalPenalChrgNotCalc) {
        this.minBalPenalChrgNotCalc = minBalPenalChrgNotCalc;
    }

    public String getIntForPastDueNotUpToDate() {
        return intForPastDueNotUpToDate;
    }

    public void setIntForPastDueNotUpToDate(String intForPastDueNotUpToDate) {
        this.intForPastDueNotUpToDate = intForPastDueNotUpToDate;
    }

    public String getDrTranToPastDueAsset() {
        return drTranToPastDueAsset;
    }

    public void setDrTranToPastDueAsset(String drTranToPastDueAsset) {
        this.drTranToPastDueAsset = drTranToPastDueAsset;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeGeneralSchemeParameterMaster)) {
            return false;
        }
        CbsSchemeGeneralSchemeParameterMaster other = (CbsSchemeGeneralSchemeParameterMaster) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeGeneralSchemeParameterMaster[ schemeCode=" + schemeCode + " ]";
    }
    
}
