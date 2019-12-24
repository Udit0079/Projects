package com.cbs.sms.service;

/**
 * @author sjain
 *
 */
public enum SmsType {

    ACCOUNT_REGISTRATION("account registration", "account_registration.vm"),
    PROMOTIONAL("promotional", "promotional.vm"),
    PIN_CHANGE("pin change", "pin_change.vm"),
    BALANCE_IN_ACCOUNT("balance in account", "balance_in_account.vm"),
    TERM_DEPOSIT_MATURITY("term deposit maturity", "term_deposit_maturity.vm"),
    MINI_AC_STATEMENT("mini ac statement", "mini_ac_account.vm"),
    DAILY_AC_STATEMENT("daily ac statement", "daily_ac_account.vm"),
    CASH_DEPOSIT("cash deposit", "cash_deposit.vm"),
    CASH_WITHDRAWAL("cash withdrawal", "cash_withdrawal.vm"),
    TRANSFER_DEPOSIT("transfer deposit", "transfer_deposit.vm"),
    TRANSFER_WITHDRAWAL("transfer withdrawal", "transfer_withdrawal.vm"),
    CLEARING_DEPOSIT("clearing deposit", "clearing_deposit.vm"),
    CHQ_RETURN_CLEARING_DEBIT("chq return clearing debit", "chq_return_clearing_debit.vm"),
    IW_CLG_CHQ_RETURN("iw clg chq return", "iw_clg_chq_return.vm"),
    CLEARING_WITHDRAWAL("clearing withdrawal", "clearing_withdrawal.vm"),
    CHEQUE_BOOK_ISSUE("cheque book issue", "cheque_book_issue.vm"),
    LOAN_EMI_DUE("loan emi due", "loan_emi_due.vm"),
    STOP_SERIES_CHEQUE_PAYMENT("stop series cheque payment", "stop_series_cheque_payment.vm"),
    STOP_CHEQUE_PAYMENT("stop cheque payment", "stop_cheque_payment.vm"),
    ACTIVE_SERIES_CHEQUE_PAYMENT("active series cheque payment", "active_series_cheque_payment.vm"),
    ACTIVE_CHEQUE_PAYMENT("active cheque payment", "active_cheque_payment.vm"),
    SERVICE_UNAVAILABLE("service unavailable", "service_unavailable.vm"),
    INVALID_PIN("invalid pin", "invalid_pin.vm"),
    INVALID_ACCOUNT_TYPE("invalid account type", "invalid_account_type.vm"),
    INTEREST_DEPOSIT("interest deposit", "interest_deposit.vm"),
    INTEREST_WITHDRAWAL("interest withdrawal", "interest_withdrawal.vm"),
    CHARGE_DEPOSIT("charge deposit", "charge_deposit.vm"),
    CHARGE_WITHDRAWAL("charge withdrawal", "charge_withdrawal.vm"),
    INWARD_NEFT_RTGS("inward neft rtgs", "inward_neft_rtgs.vm"),
    INWARD_NEFT_RTGS_OTH("inward neft rtgs oth", "inward_neft_rtgs_oth.vm"),
    OW_UTR_UPDATION("ow utr updation", "ow_utr_updation.vm"),
    OW_UTR_PAID("ow utr paid", "ow_utr_paid.vm"),
    OW_UTR_CANCEL("ow utr cancel", "ow_utr_cancel.vm"),
    TD_LIEN_REMOVING("td lien removing", "td_lien_removing.vm"),
    MINIMUM_BALANCE("minimum balance", "minimum_balance.vm"),
    ATM_WITHDRAWAL("atm withdrawal", "atm_withdrawal.vm"),
    ATM_REVERSAL("atm reversal", "atm_reversal.vm"),
    POS_WITHDRAWAL("pos withdrawal", "pos_withdrawal.vm"),
    POS_REVERSAL("pos reversal", "pos_reversal.vm"),
    ECOM_WITHDRAWAL("ecom withdrawal", "ecom_withdrawal.vm"),
    ECOM_REVERSAL("ecom reversal", "ecom_reversal.vm"),
    MANUAL_CLEARING_WITHDRAWAL("manual clearing withdrawal", "manual_clearing_withdrawal.vm"),
    AC_OPEN_SMS("ac open sms", "ac_open_sms.vm"),
    BIRTH_DAY_SMS("birth day sms", "birth_day_sms.vm"),
    LOAN_EMI_OVERDUE("loan emi overdue", "loan_emi_overdue.vm"),
    CC_OD_LIMIT_EXCEED("cc od limit exceed", "cc_od_limit_exceed.vm"),
    TD_AUTO_RENEWAL("td auto renewal", "td_auto_renewal.vm"),
    TD_AUTO_PAYMENT("td auto payment", "td_auto_payment.vm"),
    TD_RENEWAL_NOTICE("td renewal notice", "td_renewal_notice.vm"),
    TD_AUTO_RENEWAL_NOTICE("td auto renewal notice", "td_auto_renewal_notice.vm"),
    MANDATE_RECEIPT("mandate_receipt", "mandate_receipt.vm"),
    MANDATE_REJECT("mandate_reject", "mandate_reject.vm"),
    TD_MONTHLY_QUARTERLY_INTEREST("td monthly quarterly interest", "td_monthly_quarterly_interest.vm"),
    CTS_CHEQUE("cts cheque", "cts_cheque.vm"),
    SALARY_DEPOSIT("salary deposit", "salary_deposit.vm"),
    IMPS_INWARD_SUCCESS("imps inward success", "imps_inward_success.vm"),
    IMPS_OUTWARD_SUCCESS("imps outward success", "imps_outward_success.vm"),
    IMPS_OUTWARD_FAIL("imps outward fail", "imps_outward_fail.vm"),
    KYC_EXP_ALERT("kyc exp alert", "kyc_exp_alert.vm"),
    DIV_POSTING("div posting", "div_posting.vm"),
    CASH_DEPOSIT_INDR("cash deposit indr", "cash_deposit_indr.vm"),
    CASH_WITHDRAWAL_INDR("cash withdrawal indr", "cash_withdrawal_indr.vm"),
    TRANSFER_DEPOSIT_INDR("transfer deposit indr", "transfer_deposit_indr.vm"),
    TRANSFER_WITHDRAWAL_INDR("transfer withdrawal indr", "transfer_withdrawal_indr.vm"),
    CLEARING_WITHDRAWAL_INDR("clearing withdrawal indr", "clearing_withdrawal_indr.vm"),
    OW_NEFT_RTGS_DEBIT("ow neft rtgs dedit","ow_neft_rtgs_debit.vm");
    private String key;
    private String value;

    SmsType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
