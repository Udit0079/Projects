/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_flexi_fixed_deposits_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findAll", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByCurrencyCodeType", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.currencyCodeType = :currencyCodeType"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAutomaticallyCreateDeposits", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.automaticallyCreateDeposits = :automaticallyCreateDeposits"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAutoCrPerdMonths", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.autoCrPerdMonths = :autoCrPerdMonths"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAutoCrPerdDays", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.autoCrPerdDays = :autoCrPerdDays"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAutoCrFreqType", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.autoCrFreqType = :autoCrFreqType"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAutoCrFreqWeekNo", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.autoCrFreqWeekNo = :autoCrFreqWeekNo"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAutoCrFreqWeekDay", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.autoCrFreqWeekDay = :autoCrFreqWeekDay"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAutoCrFreqStartDate", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.autoCrFreqStartDate = :autoCrFreqStartDate"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAutoCrFreqNp", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.autoCrFreqNp = :autoCrFreqNp"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByCreateDepositIfOperativeAccountMoreThan", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.createDepositIfOperativeAccountMoreThan = :createDepositIfOperativeAccountMoreThan"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByCreateDepositsInStepsOf", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.createDepositsInStepsOf = :createDepositsInStepsOf"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByLinkToOperativeAccount", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.linkToOperativeAccount = :linkToOperativeAccount"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByBreakDepositInStepsOf", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.breakDepositInStepsOf = :breakDepositInStepsOf"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByForeClosureInterestMethod", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.foreClosureInterestMethod = :foreClosureInterestMethod"),
    @NamedQuery(name = "CbsSchemeFlexiFixedDepositsDetails.findByAddPreferentialToPenaltyRate", query = "SELECT c FROM CbsSchemeFlexiFixedDepositsDetails c WHERE c.addPreferentialToPenaltyRate = :addPreferentialToPenaltyRate")})
public class CbsSchemeFlexiFixedDepositsDetails extends BaseEntity implements Serializable {
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
    @Size(max = 3)
    @Column(name = "CURRENCY_CODE_TYPE")
    private String currencyCodeType;
    @Size(max = 1)
    @Column(name = "AUTOMATICALLY_CREATE_DEPOSITS")
    private String automaticallyCreateDeposits;
    @Size(max = 3)
    @Column(name = "AUTO_CR_PERD_MONTHS")
    private String autoCrPerdMonths;
    @Size(max = 3)
    @Column(name = "AUTO_CR_PERD_DAYS")
    private String autoCrPerdDays;
    @Size(max = 1)
    @Column(name = "AUTO_CR_FREQ_TYPE")
    private String autoCrFreqType;
    @Size(max = 1)
    @Column(name = "AUTO_CR_FREQ_WEEK_NO")
    private String autoCrFreqWeekNo;
    @Size(max = 1)
    @Column(name = "AUTO_CR_FREQ_WEEK_DAY")
    private String autoCrFreqWeekDay;
    @Size(max = 2)
    @Column(name = "AUTO_CR_FREQ_START_DATE")
    private String autoCrFreqStartDate;
    @Size(max = 1)
    @Column(name = "AUTO_CR_FREQ_NP")
    private String autoCrFreqNp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CREATE_DEPOSIT_IF_OPERATIVE_ACCOUNT_MORE_THAN")
    private BigDecimal createDepositIfOperativeAccountMoreThan;
    @Column(name = "CREATE_DEPOSITS_IN_STEPS_OF")
    private BigDecimal createDepositsInStepsOf;
    @Size(max = 1)
    @Column(name = "LINK_TO_OPERATIVE_ACCOUNT")
    private String linkToOperativeAccount;
    @Column(name = "BREAK_DEPOSIT_IN_STEPS_OF")
    private BigDecimal breakDepositInStepsOf;
    @Size(max = 1)
    @Column(name = "FORE_CLOSURE_INTEREST_METHOD")
    private String foreClosureInterestMethod;
    @Size(max = 1)
    @Column(name = "ADD_PREFERENTIAL_TO_PENALTY_RATE")
    private String addPreferentialToPenaltyRate;

    public CbsSchemeFlexiFixedDepositsDetails() {
    }

    public CbsSchemeFlexiFixedDepositsDetails(String schemeCode) {
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

    public String getCurrencyCodeType() {
        return currencyCodeType;
    }

    public void setCurrencyCodeType(String currencyCodeType) {
        this.currencyCodeType = currencyCodeType;
    }

    public String getAutomaticallyCreateDeposits() {
        return automaticallyCreateDeposits;
    }

    public void setAutomaticallyCreateDeposits(String automaticallyCreateDeposits) {
        this.automaticallyCreateDeposits = automaticallyCreateDeposits;
    }

    public String getAutoCrPerdMonths() {
        return autoCrPerdMonths;
    }

    public void setAutoCrPerdMonths(String autoCrPerdMonths) {
        this.autoCrPerdMonths = autoCrPerdMonths;
    }

    public String getAutoCrPerdDays() {
        return autoCrPerdDays;
    }

    public void setAutoCrPerdDays(String autoCrPerdDays) {
        this.autoCrPerdDays = autoCrPerdDays;
    }

    public String getAutoCrFreqType() {
        return autoCrFreqType;
    }

    public void setAutoCrFreqType(String autoCrFreqType) {
        this.autoCrFreqType = autoCrFreqType;
    }

    public String getAutoCrFreqWeekNo() {
        return autoCrFreqWeekNo;
    }

    public void setAutoCrFreqWeekNo(String autoCrFreqWeekNo) {
        this.autoCrFreqWeekNo = autoCrFreqWeekNo;
    }

    public String getAutoCrFreqWeekDay() {
        return autoCrFreqWeekDay;
    }

    public void setAutoCrFreqWeekDay(String autoCrFreqWeekDay) {
        this.autoCrFreqWeekDay = autoCrFreqWeekDay;
    }

    public String getAutoCrFreqStartDate() {
        return autoCrFreqStartDate;
    }

    public void setAutoCrFreqStartDate(String autoCrFreqStartDate) {
        this.autoCrFreqStartDate = autoCrFreqStartDate;
    }

    public String getAutoCrFreqNp() {
        return autoCrFreqNp;
    }

    public void setAutoCrFreqNp(String autoCrFreqNp) {
        this.autoCrFreqNp = autoCrFreqNp;
    }

    public BigDecimal getCreateDepositIfOperativeAccountMoreThan() {
        return createDepositIfOperativeAccountMoreThan;
    }

    public void setCreateDepositIfOperativeAccountMoreThan(BigDecimal createDepositIfOperativeAccountMoreThan) {
        this.createDepositIfOperativeAccountMoreThan = createDepositIfOperativeAccountMoreThan;
    }

    public BigDecimal getCreateDepositsInStepsOf() {
        return createDepositsInStepsOf;
    }

    public void setCreateDepositsInStepsOf(BigDecimal createDepositsInStepsOf) {
        this.createDepositsInStepsOf = createDepositsInStepsOf;
    }

    public String getLinkToOperativeAccount() {
        return linkToOperativeAccount;
    }

    public void setLinkToOperativeAccount(String linkToOperativeAccount) {
        this.linkToOperativeAccount = linkToOperativeAccount;
    }

    public BigDecimal getBreakDepositInStepsOf() {
        return breakDepositInStepsOf;
    }

    public void setBreakDepositInStepsOf(BigDecimal breakDepositInStepsOf) {
        this.breakDepositInStepsOf = breakDepositInStepsOf;
    }

    public String getForeClosureInterestMethod() {
        return foreClosureInterestMethod;
    }

    public void setForeClosureInterestMethod(String foreClosureInterestMethod) {
        this.foreClosureInterestMethod = foreClosureInterestMethod;
    }

    public String getAddPreferentialToPenaltyRate() {
        return addPreferentialToPenaltyRate;
    }

    public void setAddPreferentialToPenaltyRate(String addPreferentialToPenaltyRate) {
        this.addPreferentialToPenaltyRate = addPreferentialToPenaltyRate;
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
        if (!(object instanceof CbsSchemeFlexiFixedDepositsDetails)) {
            return false;
        }
        CbsSchemeFlexiFixedDepositsDetails other = (CbsSchemeFlexiFixedDepositsDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeFlexiFixedDepositsDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
