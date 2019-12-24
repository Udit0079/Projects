/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "cbs_customer_master_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsCustomerMasterDetail.findAll", query = "SELECT c FROM CbsCustomerMasterDetail c"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustomerid", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.customerid = :customerid"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTitle", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.title = :title"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustname", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custname = :custname"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByShortname", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.shortname = :shortname"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByGender", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.gender = :gender"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMaritalstatus", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.maritalstatus = :maritalstatus"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByFathername", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.fathername = :fathername"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMothername", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mothername = :mothername"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStaffflag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.staffflag = :staffflag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStaffid", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.staffid = :staffid"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMinorflag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.minorflag = :minorflag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByDateOfBirth", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByNriflag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.nriflag = :nriflag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByUINCardNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.uINCardNo = :uINCardNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByVirtualId", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.virtualId = :virtualId"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCommunicationPreference", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.communicationPreference = :communicationPreference"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmployerid", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.employerid = :employerid"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmployeeNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.employeeNo = :employeeNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMobilenumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mobilenumber = :mobilenumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustStatus", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custStatus = :custStatus"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPassportNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.passportNo = :passportNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByIssueDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.issueDate = :issueDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByIssuingAuthority", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.issuingAuthority = :issuingAuthority"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByExpirydate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.expirydate = :expirydate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPlaceOfIssue", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.placeOfIssue = :placeOfIssue"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPreferredLanguage", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.preferredLanguage = :preferredLanguage"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByNameInPreferredLanguage", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.nameInPreferredLanguage = :nameInPreferredLanguage"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByChgTurnOver", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.chgTurnOver = :chgTurnOver"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPurgeAllowed", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.purgeAllowed = :purgeAllowed"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAccountManager", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.accountManager = :accountManager"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAllowSweeps", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.allowSweeps = :allowSweeps"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTradeFinanceFlag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tradeFinanceFlag = :tradeFinanceFlag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findBySwiftCodeStatus", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.swiftCodeStatus = :swiftCodeStatus"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findBySwiftCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.swiftCode = :swiftCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByBcbfid", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.bcbfid = :bcbfid"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCombinedStmtFlag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.combinedStmtFlag = :combinedStmtFlag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStmtFreqType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.stmtFreqType = :stmtFreqType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStmtFreqWeekNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.stmtFreqWeekNo = :stmtFreqWeekNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStmtFreqWeekDay", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.stmtFreqWeekDay = :stmtFreqWeekDay"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStmtFreqStartDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.stmtFreqStartDate = :stmtFreqStartDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStmtFreqNP", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.stmtFreqNP = :stmtFreqNP"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByIntroCustomerId", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.introCustomerId = :introCustomerId"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustTitle", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custTitle = :custTitle"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.name = :name"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAddressLine1", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.addressLine1 = :addressLine1"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAddressLine2", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.addressLine2 = :addressLine2"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByVillage", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.village = :village"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByBlock", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.block = :block"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCityCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.cityCode = :cityCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStateCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.stateCode = :stateCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPostalCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.postalCode = :postalCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCountryCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.countryCode = :countryCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPhoneNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTelexNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.telexNumber = :telexNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByFaxNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.faxNumber = :faxNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findBySalary", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.salary = :salary"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByChargeStatus", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.chargeStatus = :chargeStatus"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByChargeLevelCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.chargeLevelCode = :chargeLevelCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByABBChargeCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.aBBChargeCode = :aBBChargeCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEPSChargeCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.ePSChargeCode = :ePSChargeCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPaperRemittance", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.paperRemittance = :paperRemittance"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByDeliveryChannelChargeCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.deliveryChannelChargeCode = :deliveryChannelChargeCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAccountLevelCharges", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.accountLevelCharges = :accountLevelCharges"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustLevelCharges", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custLevelCharges = :custLevelCharges"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTDSExemptionEndDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tDSExemptionEndDate = :tDSExemptionEndDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTaxSlab", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.taxSlab = :taxSlab"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTDSCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tDSCode = :tDSCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTDSCustomerId", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tDSCustomerId = :tDSCustomerId"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTDSFormReceiveDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tDSFormReceiveDate = :tDSFormReceiveDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTDSExemptionReferenceNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tDSExemptionReferenceNo = :tDSExemptionReferenceNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByExemptionRemarks", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.exemptionRemarks = :exemptionRemarks"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByITFileNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.iTFileNo = :iTFileNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTDSFloorLimit", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tDSFloorLimit = :tDSFloorLimit"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustFinancialDetails", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custFinancialDetails = :custFinancialDetails"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByFinancialYearAndMonth", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.financialYearAndMonth = :financialYearAndMonth"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCurrencyCodeType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.currencyCodeType = :currencyCodeType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPropertyAssets", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.propertyAssets = :propertyAssets"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByBusinessAssets", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.businessAssets = :businessAssets"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByInvestments", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.investments = :investments"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByNetworth", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.networth = :networth"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByDeposits", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.deposits = :deposits"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByOtherBankCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.otherBankCode = :otherBankCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByLimitsWithOtherBank", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.limitsWithOtherBank = :limitsWithOtherBank"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByFundBasedLimit", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.fundBasedLimit = :fundBasedLimit"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByNonFundBasedLimit", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.nonFundBasedLimit = :nonFundBasedLimit"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByOffLineCustDebitLimit", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.offLineCustDebitLimit = :offLineCustDebitLimit"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustSalary", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custSalary = :custSalary"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustFinancialDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custFinancialDate = :custFinancialDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPANGIRNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.pANGIRNumber = :pANGIRNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTINNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tINNumber = :tINNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findBySalesTaxNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.salesTaxNo = :salesTaxNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByExciseNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.exciseNo = :exciseNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByVoterIDNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.voterIDNo = :voterIDNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByDrivingLicenseNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.drivingLicenseNo = :drivingLicenseNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCreditCard", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.creditCard = :creditCard"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCardNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.cardNumber = :cardNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCardIssuer", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.cardIssuer = :cardIssuer"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByBankName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.bankName = :bankName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAcctId", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.acctId = :acctId"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByBranchName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.branchName = :branchName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerAddressLine1", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perAddressLine1 = :perAddressLine1"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPeraddressline2", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.peraddressline2 = :peraddressline2"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerVillage", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perVillage = :perVillage"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerBlock", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perBlock = :perBlock"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerCityCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perCityCode = :perCityCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerStateCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perStateCode = :perStateCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerPostalCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perPostalCode = :perPostalCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerCountryCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perCountryCode = :perCountryCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerPhoneNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perPhoneNumber = :perPhoneNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerTelexNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perTelexNumber = :perTelexNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerFaxNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perFaxNumber = :perFaxNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailAddressLine1", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailAddressLine1 = :mailAddressLine1"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailAddressLine2", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailAddressLine2 = :mailAddressLine2"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailVillage", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailVillage = :mailVillage"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailBlock", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailBlock = :mailBlock"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailCityCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailCityCode = :mailCityCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailStateCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailStateCode = :mailStateCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailPostalCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailPostalCode = :mailPostalCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailCountryCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailCountryCode = :mailCountryCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailPhoneNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailPhoneNumber = :mailPhoneNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailTelexNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailTelexNumber = :mailTelexNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailFaxNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailFaxNumber = :mailFaxNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpAddressLine1", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empAddressLine1 = :empAddressLine1"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpAddressLine2", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empAddressLine2 = :empAddressLine2"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpVillage", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empVillage = :empVillage"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpBlock", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empBlock = :empBlock"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpCityCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empCityCode = :empCityCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpStateCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empStateCode = :empStateCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpPostalCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empPostalCode = :empPostalCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpCountryCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empCountryCode = :empCountryCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpPhoneNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empPhoneNumber = :empPhoneNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpTelexNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empTelexNumber = :empTelexNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpFaxNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empFaxNumber = :empFaxNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmailID", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.emailID = :emailID"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByOperationalRiskRating", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.operationalRiskRating = :operationalRiskRating"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByRatingAsOn", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.ratingAsOn = :ratingAsOn"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCreditRiskRatingInternal", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.creditRiskRatingInternal = :creditRiskRatingInternal"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCreditRatingAsOn", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.creditRatingAsOn = :creditRatingAsOn"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByExternalRatingShortTerm", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.externalRatingShortTerm = :externalRatingShortTerm"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByExternShortRatingAsOn", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.externShortRatingAsOn = :externShortRatingAsOn"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByExternalRatingLongTerm", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.externalRatingLongTerm = :externalRatingLongTerm"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByExternLongRatingAsOn", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.externLongRatingAsOn = :externLongRatingAsOn"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustAcquistionDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custAcquistionDate = :custAcquistionDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByThresoldTransactionLimit", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.thresoldTransactionLimit = :thresoldTransactionLimit"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByThresoldLimitUpdationDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.thresoldLimitUpdationDate = :thresoldLimitUpdationDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustUpdationDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custUpdationDate = :custUpdationDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findBySuspensionFlg", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.suspensionFlg = :suspensionFlg"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPrimaryBrCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.primaryBrCode = :primaryBrCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByLastUpdatedBr", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.lastUpdatedBr = :lastUpdatedBr"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByFirstAccountDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.firstAccountDate = :firstAccountDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAuth", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.auth = :auth"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByLastChangeUserID", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByLastChangeTime", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByRecordCreaterID", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCreationTime", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTsCnt", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tsCnt = :tsCnt"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAadhaarNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.aadhaarNo = :aadhaarNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByLpgId", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.lpgId = :lpgId"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAadhaarLpgAcno", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.aadhaarLpgAcno = :aadhaarLpgAcno"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMandateFlag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mandateFlag = :mandateFlag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMandateDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mandateDate = :mandateDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByRegType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.regType = :regType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMiddleName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.middleName = :middleName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByLastName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findBySpouseName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.spouseName = :spouseName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMaidenName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.maidenName = :maidenName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByNregaJobCard", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.nregaJobCard = :nregaJobCard"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByDlExpiry", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.dlExpiry = :dlExpiry"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByLegalDocument", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.legalDocument = :legalDocument"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByIncomeRange", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.incomeRange = :incomeRange"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByNetworthAsOn", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.networthAsOn = :networthAsOn"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByQualification", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.qualification = :qualification"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPoliticalExposed", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.politicalExposed = :politicalExposed"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriAdd1", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriAdd1 = :juriAdd1"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriAdd2", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriAdd2 = :juriAdd2"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriCity", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriCity = :juriCity"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriState", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriState = :juriState"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriPostal", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriPostal = :juriPostal"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriCountry", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriCountry = :juriCountry"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTan", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tan = :tan"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCin", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.cin = :cin"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerEmail", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perEmail = :perEmail"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailEmail", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailEmail = :mailEmail"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByNationality", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.nationality = :nationality"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByOtherIdentity", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.otherIdentity = :otherIdentity"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPoa", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.poa = :poa"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByStrFlag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.strFlag = :strFlag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAcHolderTypeFlag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.acHolderTypeFlag = :acHolderTypeFlag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAcHolderType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.acHolderType = :acHolderType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAcType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.acType = :acType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCKYCNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.cKYCNo = :cKYCNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByFatherMiddleName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.fatherMiddleName = :fatherMiddleName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByFatherLastName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.fatherLastName = :fatherLastName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findBySpouseMiddleName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.spouseMiddleName = :spouseMiddleName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findBySpouseLastName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.spouseLastName = :spouseLastName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMotherMiddleName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.motherMiddleName = :motherMiddleName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMotherLastName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.motherLastName = :motherLastName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByTinIssuingCountry", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.tinIssuingCountry = :tinIssuingCountry"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustEntityType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custEntityType = :custEntityType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByIdentityNo", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.identityNo = :identityNo"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByIdExpiryDate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.idExpiryDate = :idExpiryDate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerAddType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perAddType = :perAddType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerMailAddSameFlagIndicate", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perMailAddSameFlagIndicate = :perMailAddSameFlagIndicate"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailAddType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailAddType = :mailAddType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailPoa", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailPoa = :mailPoa"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriAddBasedOnFlag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriAddBasedOnFlag = :juriAddBasedOnFlag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriAddType", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriAddType = :juriAddType"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriPoa", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriPoa = :juriPoa"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerDistrict", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perDistrict = :perDistrict"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailDistrict", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailDistrict = :mailDistrict"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByEmpDistrict", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.empDistrict = :empDistrict"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriDistrict", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriDistrict = :juriDistrict"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByPerOtherPOA", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.perOtherPOA = :perOtherPOA"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByMailOtherPOA", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.mailOtherPOA = :mailOtherPOA"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByJuriOtherPOA", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.juriOtherPOA = :juriOtherPOA"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByFatherSpouseFlag", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.fatherSpouseFlag = :fatherSpouseFlag"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByIsdCode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.isdCode = :isdCode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustImage", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custImage = :custImage"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByCustFullName", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.custFullName = :custFullName"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByGstIdentificationNumber", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.gstIdentificationNumber = :gstIdentificationNumber"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAadhaarMode", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.aadhaarMode = :aadhaarMode"),
    @NamedQuery(name = "CbsCustomerMasterDetail.findByAadhaarBankIin", query = "SELECT c FROM CbsCustomerMasterDetail c WHERE c.aadhaarBankIin = :aadhaarBankIin")})
public class CbsCustomerMasterDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "customerid")
    private String customerid;
    @Size(max = 30)
    @Column(name = "title")
    private String title;
    @Size(max = 210)
    @Column(name = "custname")
    private String custname;
    @Size(max = 30)
    @Column(name = "shortname")
    private String shortname;
    @Size(max = 3)
    @Column(name = "gender")
    private String gender;
    @Size(max = 15)
    @Column(name = "maritalstatus")
    private String maritalstatus;
    @Size(max = 210)
    @Column(name = "fathername")
    private String fathername;
    @Size(max = 210)
    @Column(name = "mothername")
    private String mothername;
    @Size(max = 3)
    @Column(name = "staffflag")
    private String staffflag;
    @Size(max = 30)
    @Column(name = "staffid")
    private String staffid;
    @Size(max = 3)
    @Column(name = "minorflag")
    private String minorflag;
    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    @Size(max = 6)
    @Column(name = "nriflag")
    private String nriflag;
    @Size(max = 60)
    @Column(name = "UINCardNo")
    private String uINCardNo;
    @Size(max=20)
    @Column(name ="virtualId")
    private String virtualId;
    @Size(max = 3)
    @Column(name = "communicationPreference")
    private String communicationPreference;
    @Size(max = 30)
    @Column(name = "employerid")
    private String employerid;
    @Size(max = 30)
    @Column(name = "employeeNo")
    private String employeeNo;
    @Size(max = 600)
    @Column(name = "mobilenumber")
    private String mobilenumber;
    @Size(max = 15)
    @Column(name = "CustStatus")
    private String custStatus;
    @Size(max = 96)
    @Column(name = "PassportNo")
    private String passportNo;
    @Column(name = "IssueDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;
    @Size(max = 9)
    @Column(name = "issuingAuthority")
    private String issuingAuthority;
    @Column(name = "Expirydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirydate;
    @Size(max = 54)
    @Column(name = "PlaceOfIssue")
    private String placeOfIssue;
    @Size(max = 51)
    @Column(name = "PreferredLanguage")
    private String preferredLanguage;
    @Size(max = 120)
    @Column(name = "NameInPreferredLanguage")
    private String nameInPreferredLanguage;
    @Size(max = 3)
    @Column(name = "ChgTurnOver")
    private String chgTurnOver;
    @Size(max = 3)
    @Column(name = "PurgeAllowed")
    private String purgeAllowed;
    @Size(max = 30)
    @Column(name = "AccountManager")
    private String accountManager;
    @Size(max = 3)
    @Column(name = "AllowSweeps")
    private String allowSweeps;
    @Size(max = 3)
    @Column(name = "TradeFinanceFlag")
    private String tradeFinanceFlag;
    @Size(max = 3)
    @Column(name = "SwiftCodeStatus")
    private String swiftCodeStatus;
    @Size(max = 36)
    @Column(name = "SwiftCode")
    private String swiftCode;
    @Size(max = 36)
    @Column(name = "BCBFID")
    private String bcbfid;
    @Size(max = 3)
    @Column(name = "CombinedStmtFlag")
    private String combinedStmtFlag;
    @Size(max = 3)
    @Column(name = "StmtFreqType")
    private String stmtFreqType;
    @Size(max = 3)
    @Column(name = "StmtFreqWeekNo")
    private String stmtFreqWeekNo;
    @Size(max = 3)
    @Column(name = "StmtFreqWeekDay")
    private String stmtFreqWeekDay;
    @Size(max = 24)
    @Column(name = "StmtFreqStartDate")
    private String stmtFreqStartDate;
    @Size(max = 3)
    @Column(name = "StmtFreqNP")
    private String stmtFreqNP;
    @Size(max = 30)
    @Column(name = "IntroCustomerId")
    private String introCustomerId;
    @Size(max = 30)
    @Column(name = "CustTitle")
    private String custTitle;
    @Size(max = 120)
    @Column(name = "name")
    private String name;
    @Size(max = 1200)
    @Column(name = "AddressLine1")
    private String addressLine1;
    @Size(max = 1200)
    @Column(name = "AddressLine2")
    private String addressLine2;
    @Size(max = 135)
    @Column(name = "village")
    private String village;
    @Size(max = 135)
    @Column(name = "block")
    private String block;
    @Size(max = 18)
    @Column(name = "CityCode")
    private String cityCode;
    @Size(max = 18)
    @Column(name = "StateCode")
    private String stateCode;
    @Size(max = 33)
    @Column(name = "PostalCode")
    private String postalCode;
    @Size(max = 9)
    @Column(name = "CountryCode")
    private String countryCode;
    @Size(max = 600)
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Size(max = 45)
    @Column(name = "TelexNumber")
    private String telexNumber;
    @Size(max = 45)
    @Column(name = "FaxNumber")
    private String faxNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salary")
    private Double salary;
    @Size(max = 3)
    @Column(name = "ChargeStatus")
    private String chargeStatus;
    @Size(max = 60)
    @Column(name = "ChargeLevelCode")
    private String chargeLevelCode;
    @Size(max = 60)
    @Column(name = "ABBChargeCode")
    private String aBBChargeCode;
    @Size(max = 60)
    @Column(name = "EPSChargeCode")
    private String ePSChargeCode;
    @Size(max = 30)
    @Column(name = "PaperRemittance")
    private String paperRemittance;
    @Size(max = 30)
    @Column(name = "DeliveryChannelChargeCode")
    private String deliveryChannelChargeCode;
    @Size(max = 15)
    @Column(name = "AccountLevelCharges")
    private String accountLevelCharges;
    @Size(max = 15)
    @Column(name = "CustLevelCharges")
    private String custLevelCharges;
    @Column(name = "TDSExemptionEndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tDSExemptionEndDate;
    @Size(max = 15)
    @Column(name = "TaxSlab")
    private String taxSlab;
    @Size(max = 36)
    @Column(name = "TDSCode")
    private String tDSCode;
    @Size(max = 30)
    @Column(name = "TDSCustomerId")
    private String tDSCustomerId;
    @Column(name = "TDSFormReceiveDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tDSFormReceiveDate;
    @Size(max = 90)
    @Column(name = "TDSExemptionReferenceNo")
    private String tDSExemptionReferenceNo;
    @Size(max = 240)
    @Column(name = "ExemptionRemarks")
    private String exemptionRemarks;
    @Size(max = 60)
    @Column(name = "ITFileNo")
    private String iTFileNo;
    @Column(name = "TDSFloorLimit")
    private Double tDSFloorLimit;
    @Size(max = 150)
    @Column(name = "CustFinancialDetails")
    private String custFinancialDetails;
    @Size(max = 15)
    @Column(name = "FinancialYearAndMonth")
    private String financialYearAndMonth;
    @Size(max = 9)
    @Column(name = "CurrencyCodeType")
    private String currencyCodeType;
    @Column(name = "PropertyAssets")
    private Double propertyAssets;
    @Column(name = "BusinessAssets")
    private Double businessAssets;
    @Column(name = "Investments")
    private Double investments;
    @Column(name = "Networth")
    private Double networth;
    @Column(name = "Deposits")
    private Double deposits;
    @Size(max = 18)
    @Column(name = "OtherBankCode")
    private String otherBankCode;
    @Column(name = "LimitsWithOtherBank")
    private Double limitsWithOtherBank;
    @Column(name = "FundBasedLimit")
    private Double fundBasedLimit;
    @Column(name = "NonFundBasedLimit")
    private Double nonFundBasedLimit;
    @Column(name = "OffLineCustDebitLimit")
    private Double offLineCustDebitLimit;
    @Column(name = "CustSalary")
    private Double custSalary;
    @Column(name = "CustFinancialDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date custFinancialDate;
    @Size(max = 60)
    @Column(name = "PAN_GIRNumber")
    private String pANGIRNumber;
    @Size(max = 33)
    @Column(name = "TINNumber")
    private String tINNumber;
    @Size(max = 30)
    @Column(name = "SalesTaxNo")
    private String salesTaxNo;
    @Size(max = 30)
    @Column(name = "ExciseNo")
    private String exciseNo;
    @Size(max = 60)
    @Column(name = "VoterIDNo")
    private String voterIDNo;
    @Size(max = 60)
    @Column(name = "DrivingLicenseNo")
    private String drivingLicenseNo;
    @Size(max = 60)
    @Column(name = "CreditCard")
    private String creditCard;
    @Size(max = 60)
    @Column(name = "CardNumber")
    private String cardNumber;
    @Size(max = 96)
    @Column(name = "CardIssuer")
    private String cardIssuer;
    @Size(max = 120)
    @Column(name = "BankName")
    private String bankName;
    @Size(max = 33)
    @Column(name = "AcctId")
    private String acctId;
    @Size(max = 120)
    @Column(name = "BranchName")
    private String branchName;
    @Size(max = 1200)
    @Column(name = "PerAddressLine1")
    private String perAddressLine1;
    @Size(max = 1200)
    @Column(name = "peraddressline2")
    private String peraddressline2;
    @Size(max = 135)
    @Column(name = "PerVillage")
    private String perVillage;
    @Size(max = 135)
    @Column(name = "PerBlock")
    private String perBlock;
    @Size(max = 18)
    @Column(name = "PerCityCode")
    private String perCityCode;
    @Size(max = 18)
    @Column(name = "PerStateCode")
    private String perStateCode;
    @Size(max = 18)
    @Column(name = "PerPostalCode")
    private String perPostalCode;
    @Size(max = 9)
    @Column(name = "PerCountryCode")
    private String perCountryCode;
    @Size(max = 45)
    @Column(name = "PerPhoneNumber")
    private String perPhoneNumber;
    @Size(max = 45)
    @Column(name = "PerTelexNumber")
    private String perTelexNumber;
    @Size(max = 45)
    @Column(name = "PerFaxNumber")
    private String perFaxNumber;
    @Size(max = 1200)
    @Column(name = "MailAddressLine1")
    private String mailAddressLine1;
    @Size(max = 1200)
    @Column(name = "MailAddressLine2")
    private String mailAddressLine2;
    @Size(max = 135)
    @Column(name = "MailVillage")
    private String mailVillage;
    @Size(max = 135)
    @Column(name = "MailBlock")
    private String mailBlock;
    @Size(max = 18)
    @Column(name = "MailCityCode")
    private String mailCityCode;
    @Size(max = 18)
    @Column(name = "MailStateCode")
    private String mailStateCode;
    @Size(max = 18)
    @Column(name = "MailPostalCode")
    private String mailPostalCode;
    @Size(max = 9)
    @Column(name = "MailCountryCode")
    private String mailCountryCode;
    @Size(max = 45)
    @Column(name = "MailPhoneNumber")
    private String mailPhoneNumber;
    @Size(max = 45)
    @Column(name = "MailTelexNumber")
    private String mailTelexNumber;
    @Size(max = 45)
    @Column(name = "MailFaxNumber")
    private String mailFaxNumber;
    @Size(max = 150)
    @Column(name = "EmpAddressLine1")
    private String empAddressLine1;
    @Size(max = 150)
    @Column(name = "EmpAddressLine2")
    private String empAddressLine2;
    @Size(max = 135)
    @Column(name = "EmpVillage")
    private String empVillage;
    @Size(max = 135)
    @Column(name = "EmpBlock")
    private String empBlock;
    @Size(max = 18)
    @Column(name = "EmpCityCode")
    private String empCityCode;
    @Size(max = 18)
    @Column(name = "EmpStateCode")
    private String empStateCode;
    @Size(max = 18)
    @Column(name = "EmpPostalCode")
    private String empPostalCode;
    @Size(max = 9)
    @Column(name = "EmpCountryCode")
    private String empCountryCode;
    @Size(max = 45)
    @Column(name = "EmpPhoneNumber")
    private String empPhoneNumber;
    @Size(max = 45)
    @Column(name = "EmpTelexNumber")
    private String empTelexNumber;
    @Size(max = 45)
    @Column(name = "EmpFaxNumber")
    private String empFaxNumber;
    @Size(max = 120)
    @Column(name = "EmailID")
    private String emailID;
    @Size(max = 15)
    @Column(name = "OperationalRiskRating")
    private String operationalRiskRating;
    @Column(name = "RatingAsOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ratingAsOn;
    @Size(max = 15)
    @Column(name = "CreditRiskRatingInternal")
    private String creditRiskRatingInternal;
    @Column(name = "CreditRatingAsOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creditRatingAsOn;
    @Size(max = 15)
    @Column(name = "ExternalRatingShortTerm")
    private String externalRatingShortTerm;
    @Column(name = "ExternShortRatingAsOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date externShortRatingAsOn;
    @Size(max = 15)
    @Column(name = "ExternalRatingLongTerm")
    private String externalRatingLongTerm;
    @Column(name = "ExternLongRatingAsOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date externLongRatingAsOn;
    @Column(name = "CustAcquistionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date custAcquistionDate;
    @Column(name = "ThresoldTransactionLimit")
    private Double thresoldTransactionLimit;
    @Column(name = "ThresoldLimitUpdationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thresoldLimitUpdationDate;
    @Column(name = "CustUpdationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date custUpdationDate;
    @Size(max = 3)
    @Column(name = "SuspensionFlg")
    private String suspensionFlg;
    @Size(max = 6)
    @Column(name = "PrimaryBrCode")
    private String primaryBrCode;
    @Size(max = 6)
    @Column(name = "LastUpdatedBr")
    private String lastUpdatedBr;
    @Size(max = 45)
    @Column(name = "FirstAccountDate")
    private String firstAccountDate;
    @Size(max = 3)
    @Column(name = "Auth")
    private String auth;
    @Size(max = 45)
    @Column(name = "LastChangeUserID")
    private String lastChangeUserID;
    @Column(name = "LastChangeTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChangeTime;
    @Size(max = 45)
    @Column(name = "RecordCreaterID")
    private String recordCreaterID;
    @Column(name = "CreationTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Size(max = 15)
    @Column(name = "TsCnt")
    private String tsCnt;
    @Size(max = 48)
    @Column(name = "AADHAAR_NO")
    private String aadhaarNo;
    @Size(max = 51)
    @Column(name = "LPG_ID")
    private String lpgId;
    @Size(max = 36)
    @Column(name = "AADHAAR_LPG_ACNO")
    private String aadhaarLpgAcno;
    @Size(max = 3)
    @Column(name = "MANDATE_FLAG")
    private String mandateFlag;
    @Size(max = 30)
    @Column(name = "MANDATE_DATE")
    private String mandateDate;
    @Size(max = 33)
    @Column(name = "REG_TYPE")
    private String regType;
    @Size(max = 210)
    @Column(name = "middle_name")
    private String middleName;
    @Size(max = 210)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 210)
    @Column(name = "spouse_name")
    private String spouseName;
    @Size(max = 210)
    @Column(name = "maiden_name")
    private String maidenName;
    @Size(max = 75)
    @Column(name = "nrega_job_card")
    private String nregaJobCard;
    @Size(max = 30)
    @Column(name = "dl_expiry")
    private String dlExpiry;
    @Size(max = 18)
    @Column(name = "legal_document")
    private String legalDocument;
    @Size(max = 18)
    @Column(name = "income_range")
    private String incomeRange;
    @Size(max = 30)
    @Column(name = "networth_as_on")
    private String networthAsOn;
    @Size(max = 18)
    @Column(name = "qualification")
    private String qualification;
    @Size(max = 18)
    @Column(name = "political_exposed")
    private String politicalExposed;
    @Size(max = 750)
    @Column(name = "juri_add1")
    private String juriAdd1;
    @Size(max = 750)
    @Column(name = "juri_add2")
    private String juriAdd2;
    @Size(max = 150)
    @Column(name = "juri_city")
    private String juriCity;
    @Size(max = 150)
    @Column(name = "juri_state")
    private String juriState;
    @Size(max = 18)
    @Column(name = "juri_postal")
    private String juriPostal;
    @Size(max = 18)
    @Column(name = "juri_country")
    private String juriCountry;
    @Size(max = 30)
    @Column(name = "tan")
    private String tan;
    @Size(max = 63)
    @Column(name = "cin")
    private String cin;
    @Size(max = 120)
    @Column(name = "per_email")
    private String perEmail;
    @Size(max = 120)
    @Column(name = "mail_email")
    private String mailEmail;
    @Size(max = 18)
    @Column(name = "nationality")
    private String nationality;
    @Size(max = 78)
    @Column(name = "other_identity")
    private String otherIdentity;
    @Size(max = 45)
    @Column(name = "poa")
    private String poa;
    @Size(max = 30)
    @Column(name = "strFlag")
    private String strFlag;
    @Size(max = 6)
    @Column(name = "AcHolderTypeFlag")
    private String acHolderTypeFlag;
    @Size(max = 6)
    @Column(name = "AcHolderType")
    private String acHolderType;
    @Size(max = 6)
    @Column(name = "AcType")
    private String acType;
    @Size(max = 42)
    @Column(name = "CKYCNo")
    private String cKYCNo;
    @Size(max = 150)
    @Column(name = "FatherMiddleName")
    private String fatherMiddleName;
    @Size(max = 150)
    @Column(name = "FatherLastName")
    private String fatherLastName;
    @Size(max = 150)
    @Column(name = "SpouseMiddleName")
    private String spouseMiddleName;
    @Size(max = 150)
    @Column(name = "SpouseLastName")
    private String spouseLastName;
    @Size(max = 150)
    @Column(name = "MotherMiddleName")
    private String motherMiddleName;
    @Size(max = 150)
    @Column(name = "MotherLastName")
    private String motherLastName;
    @Size(max = 18)
    @Column(name = "TinIssuingCountry")
    private String tinIssuingCountry;
    @Size(max = 6)
    @Column(name = "CustEntityType")
    private String custEntityType;
    @Size(max = 75)
    @Column(name = "IdentityNo")
    private String identityNo;
    @Size(max = 24)
    @Column(name = "IdExpiryDate")
    private String idExpiryDate;
    @Size(max = 6)
    @Column(name = "PerAddType")
    private String perAddType;
    @Size(max = 3)
    @Column(name = "PerMailAddSameFlagIndicate")
    private String perMailAddSameFlagIndicate;
    @Size(max = 6)
    @Column(name = "MailAddType")
    private String mailAddType;
    @Size(max = 45)
    @Column(name = "MailPoa")
    private String mailPoa;
    @Size(max = 6)
    @Column(name = "JuriAddBasedOnFlag")
    private String juriAddBasedOnFlag;
    @Size(max = 6)
    @Column(name = "JuriAddType")
    private String juriAddType;
    @Size(max = 45)
    @Column(name = "JuriPoa")
    private String juriPoa;
    @Size(max = 150)
    @Column(name = "PerDistrict")
    private String perDistrict;
    @Size(max = 150)
    @Column(name = "MailDistrict")
    private String mailDistrict;
    @Size(max = 150)
    @Column(name = "EmpDistrict")
    private String empDistrict;
    @Size(max = 150)
    @Column(name = "JuriDistrict")
    private String juriDistrict;
    @Size(max = 150)
    @Column(name = "PerOtherPOA")
    private String perOtherPOA;
    @Size(max = 150)
    @Column(name = "MailOtherPOA")
    private String mailOtherPOA;
    @Size(max = 150)
    @Column(name = "JuriOtherPOA")
    private String juriOtherPOA;
    @Size(max = 3)
    @Column(name = "father_spouse_flag")
    private String fatherSpouseFlag;
    @Size(max = 9)
    @Column(name = "isd_code")
    private String isdCode;
    @Size(max = 90)
    @Column(name = "CustImage")
    private String custImage;
    @Size(max = 810)
    @Column(name = "CustFullName")
    private String custFullName;
    @Size(max = 45)
    @Column(name = "gstIdentificationNumber")
    private String gstIdentificationNumber;
    @Size(max = 1)
    @Column(name = "aadhaar_mode")
    private String aadhaarMode;
    @Size(max = 9)
    @Column(name = "aadhaar_bank_iin")
    private String aadhaarBankIin;

    public CbsCustomerMasterDetail() {
    }

    public CbsCustomerMasterDetail(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getStaffflag() {
        return staffflag;
    }

    public void setStaffflag(String staffflag) {
        this.staffflag = staffflag;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getMinorflag() {
        return minorflag;
    }

    public void setMinorflag(String minorflag) {
        this.minorflag = minorflag;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNriflag() {
        return nriflag;
    }

    public void setNriflag(String nriflag) {
        this.nriflag = nriflag;
    }

    public String getUINCardNo() {
        return uINCardNo;
    }

    public void setUINCardNo(String uINCardNo) {
        this.uINCardNo = uINCardNo;
    }

    public String getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(String communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public String getEmployerid() {
        return employerid;
    }

    public void setEmployerid(String employerid) {
        this.employerid = employerid;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getNameInPreferredLanguage() {
        return nameInPreferredLanguage;
    }

    public void setNameInPreferredLanguage(String nameInPreferredLanguage) {
        this.nameInPreferredLanguage = nameInPreferredLanguage;
    }

    public String getChgTurnOver() {
        return chgTurnOver;
    }

    public void setChgTurnOver(String chgTurnOver) {
        this.chgTurnOver = chgTurnOver;
    }

    public String getPurgeAllowed() {
        return purgeAllowed;
    }

    public void setPurgeAllowed(String purgeAllowed) {
        this.purgeAllowed = purgeAllowed;
    }

    public String getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(String accountManager) {
        this.accountManager = accountManager;
    }

    public String getAllowSweeps() {
        return allowSweeps;
    }

    public void setAllowSweeps(String allowSweeps) {
        this.allowSweeps = allowSweeps;
    }

    public String getTradeFinanceFlag() {
        return tradeFinanceFlag;
    }

    public void setTradeFinanceFlag(String tradeFinanceFlag) {
        this.tradeFinanceFlag = tradeFinanceFlag;
    }

    public String getSwiftCodeStatus() {
        return swiftCodeStatus;
    }

    public void setSwiftCodeStatus(String swiftCodeStatus) {
        this.swiftCodeStatus = swiftCodeStatus;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getBcbfid() {
        return bcbfid;
    }

    public void setBcbfid(String bcbfid) {
        this.bcbfid = bcbfid;
    }

    public String getCombinedStmtFlag() {
        return combinedStmtFlag;
    }

    public void setCombinedStmtFlag(String combinedStmtFlag) {
        this.combinedStmtFlag = combinedStmtFlag;
    }

    public String getStmtFreqType() {
        return stmtFreqType;
    }

    public void setStmtFreqType(String stmtFreqType) {
        this.stmtFreqType = stmtFreqType;
    }

    public String getStmtFreqWeekNo() {
        return stmtFreqWeekNo;
    }

    public void setStmtFreqWeekNo(String stmtFreqWeekNo) {
        this.stmtFreqWeekNo = stmtFreqWeekNo;
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

    public String getStmtFreqNP() {
        return stmtFreqNP;
    }

    public void setStmtFreqNP(String stmtFreqNP) {
        this.stmtFreqNP = stmtFreqNP;
    }

    public String getIntroCustomerId() {
        return introCustomerId;
    }

    public void setIntroCustomerId(String introCustomerId) {
        this.introCustomerId = introCustomerId;
    }

    public String getCustTitle() {
        return custTitle;
    }

    public void setCustTitle(String custTitle) {
        this.custTitle = custTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTelexNumber() {
        return telexNumber;
    }

    public void setTelexNumber(String telexNumber) {
        this.telexNumber = telexNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getChargeLevelCode() {
        return chargeLevelCode;
    }

    public void setChargeLevelCode(String chargeLevelCode) {
        this.chargeLevelCode = chargeLevelCode;
    }

    public String getABBChargeCode() {
        return aBBChargeCode;
    }

    public void setABBChargeCode(String aBBChargeCode) {
        this.aBBChargeCode = aBBChargeCode;
    }

    public String getEPSChargeCode() {
        return ePSChargeCode;
    }

    public void setEPSChargeCode(String ePSChargeCode) {
        this.ePSChargeCode = ePSChargeCode;
    }

    public String getPaperRemittance() {
        return paperRemittance;
    }

    public void setPaperRemittance(String paperRemittance) {
        this.paperRemittance = paperRemittance;
    }

    public String getDeliveryChannelChargeCode() {
        return deliveryChannelChargeCode;
    }

    public void setDeliveryChannelChargeCode(String deliveryChannelChargeCode) {
        this.deliveryChannelChargeCode = deliveryChannelChargeCode;
    }

    public String getAccountLevelCharges() {
        return accountLevelCharges;
    }

    public void setAccountLevelCharges(String accountLevelCharges) {
        this.accountLevelCharges = accountLevelCharges;
    }

    public String getCustLevelCharges() {
        return custLevelCharges;
    }

    public void setCustLevelCharges(String custLevelCharges) {
        this.custLevelCharges = custLevelCharges;
    }

    public Date getTDSExemptionEndDate() {
        return tDSExemptionEndDate;
    }

    public void setTDSExemptionEndDate(Date tDSExemptionEndDate) {
        this.tDSExemptionEndDate = tDSExemptionEndDate;
    }

    public String getTaxSlab() {
        return taxSlab;
    }

    public void setTaxSlab(String taxSlab) {
        this.taxSlab = taxSlab;
    }

    public String getTDSCode() {
        return tDSCode;
    }

    public void setTDSCode(String tDSCode) {
        this.tDSCode = tDSCode;
    }

    public String getTDSCustomerId() {
        return tDSCustomerId;
    }

    public void setTDSCustomerId(String tDSCustomerId) {
        this.tDSCustomerId = tDSCustomerId;
    }

    public Date getTDSFormReceiveDate() {
        return tDSFormReceiveDate;
    }

    public void setTDSFormReceiveDate(Date tDSFormReceiveDate) {
        this.tDSFormReceiveDate = tDSFormReceiveDate;
    }

    public String getTDSExemptionReferenceNo() {
        return tDSExemptionReferenceNo;
    }

    public void setTDSExemptionReferenceNo(String tDSExemptionReferenceNo) {
        this.tDSExemptionReferenceNo = tDSExemptionReferenceNo;
    }

    public String getExemptionRemarks() {
        return exemptionRemarks;
    }

    public void setExemptionRemarks(String exemptionRemarks) {
        this.exemptionRemarks = exemptionRemarks;
    }

    public String getITFileNo() {
        return iTFileNo;
    }

    public void setITFileNo(String iTFileNo) {
        this.iTFileNo = iTFileNo;
    }

    public Double getTDSFloorLimit() {
        return tDSFloorLimit;
    }

    public void setTDSFloorLimit(Double tDSFloorLimit) {
        this.tDSFloorLimit = tDSFloorLimit;
    }

    public String getCustFinancialDetails() {
        return custFinancialDetails;
    }

    public void setCustFinancialDetails(String custFinancialDetails) {
        this.custFinancialDetails = custFinancialDetails;
    }

    public String getFinancialYearAndMonth() {
        return financialYearAndMonth;
    }

    public void setFinancialYearAndMonth(String financialYearAndMonth) {
        this.financialYearAndMonth = financialYearAndMonth;
    }

    public String getCurrencyCodeType() {
        return currencyCodeType;
    }

    public void setCurrencyCodeType(String currencyCodeType) {
        this.currencyCodeType = currencyCodeType;
    }

    public Double getPropertyAssets() {
        return propertyAssets;
    }

    public void setPropertyAssets(Double propertyAssets) {
        this.propertyAssets = propertyAssets;
    }

    public Double getBusinessAssets() {
        return businessAssets;
    }

    public void setBusinessAssets(Double businessAssets) {
        this.businessAssets = businessAssets;
    }

    public Double getInvestments() {
        return investments;
    }

    public void setInvestments(Double investments) {
        this.investments = investments;
    }

    public Double getNetworth() {
        return networth;
    }

    public void setNetworth(Double networth) {
        this.networth = networth;
    }

    public Double getDeposits() {
        return deposits;
    }

    public void setDeposits(Double deposits) {
        this.deposits = deposits;
    }

    public String getOtherBankCode() {
        return otherBankCode;
    }

    public void setOtherBankCode(String otherBankCode) {
        this.otherBankCode = otherBankCode;
    }

    public Double getLimitsWithOtherBank() {
        return limitsWithOtherBank;
    }

    public void setLimitsWithOtherBank(Double limitsWithOtherBank) {
        this.limitsWithOtherBank = limitsWithOtherBank;
    }

    public Double getFundBasedLimit() {
        return fundBasedLimit;
    }

    public void setFundBasedLimit(Double fundBasedLimit) {
        this.fundBasedLimit = fundBasedLimit;
    }

    public Double getNonFundBasedLimit() {
        return nonFundBasedLimit;
    }

    public void setNonFundBasedLimit(Double nonFundBasedLimit) {
        this.nonFundBasedLimit = nonFundBasedLimit;
    }

    public Double getOffLineCustDebitLimit() {
        return offLineCustDebitLimit;
    }

    public void setOffLineCustDebitLimit(Double offLineCustDebitLimit) {
        this.offLineCustDebitLimit = offLineCustDebitLimit;
    }

    public Double getCustSalary() {
        return custSalary;
    }

    public void setCustSalary(Double custSalary) {
        this.custSalary = custSalary;
    }

    public Date getCustFinancialDate() {
        return custFinancialDate;
    }

    public void setCustFinancialDate(Date custFinancialDate) {
        this.custFinancialDate = custFinancialDate;
    }

    public String getPANGIRNumber() {
        return pANGIRNumber;
    }

    public void setPANGIRNumber(String pANGIRNumber) {
        this.pANGIRNumber = pANGIRNumber;
    }

    public String getTINNumber() {
        return tINNumber;
    }

    public void setTINNumber(String tINNumber) {
        this.tINNumber = tINNumber;
    }

    public String getSalesTaxNo() {
        return salesTaxNo;
    }

    public void setSalesTaxNo(String salesTaxNo) {
        this.salesTaxNo = salesTaxNo;
    }

    public String getExciseNo() {
        return exciseNo;
    }

    public void setExciseNo(String exciseNo) {
        this.exciseNo = exciseNo;
    }

    public String getVoterIDNo() {
        return voterIDNo;
    }

    public void setVoterIDNo(String voterIDNo) {
        this.voterIDNo = voterIDNo;
    }

    public String getDrivingLicenseNo() {
        return drivingLicenseNo;
    }

    public void setDrivingLicenseNo(String drivingLicenseNo) {
        this.drivingLicenseNo = drivingLicenseNo;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPerAddressLine1() {
        return perAddressLine1;
    }

    public void setPerAddressLine1(String perAddressLine1) {
        this.perAddressLine1 = perAddressLine1;
    }

    public String getPeraddressline2() {
        return peraddressline2;
    }

    public void setPeraddressline2(String peraddressline2) {
        this.peraddressline2 = peraddressline2;
    }

    public String getPerVillage() {
        return perVillage;
    }

    public void setPerVillage(String perVillage) {
        this.perVillage = perVillage;
    }

    public String getPerBlock() {
        return perBlock;
    }

    public void setPerBlock(String perBlock) {
        this.perBlock = perBlock;
    }

    public String getPerCityCode() {
        return perCityCode;
    }

    public void setPerCityCode(String perCityCode) {
        this.perCityCode = perCityCode;
    }

    public String getPerStateCode() {
        return perStateCode;
    }

    public void setPerStateCode(String perStateCode) {
        this.perStateCode = perStateCode;
    }

    public String getPerPostalCode() {
        return perPostalCode;
    }

    public void setPerPostalCode(String perPostalCode) {
        this.perPostalCode = perPostalCode;
    }

    public String getPerCountryCode() {
        return perCountryCode;
    }

    public void setPerCountryCode(String perCountryCode) {
        this.perCountryCode = perCountryCode;
    }

    public String getPerPhoneNumber() {
        return perPhoneNumber;
    }

    public void setPerPhoneNumber(String perPhoneNumber) {
        this.perPhoneNumber = perPhoneNumber;
    }

    public String getPerTelexNumber() {
        return perTelexNumber;
    }

    public void setPerTelexNumber(String perTelexNumber) {
        this.perTelexNumber = perTelexNumber;
    }

    public String getPerFaxNumber() {
        return perFaxNumber;
    }

    public void setPerFaxNumber(String perFaxNumber) {
        this.perFaxNumber = perFaxNumber;
    }

    public String getMailAddressLine1() {
        return mailAddressLine1;
    }

    public void setMailAddressLine1(String mailAddressLine1) {
        this.mailAddressLine1 = mailAddressLine1;
    }

    public String getMailAddressLine2() {
        return mailAddressLine2;
    }

    public void setMailAddressLine2(String mailAddressLine2) {
        this.mailAddressLine2 = mailAddressLine2;
    }

    public String getMailVillage() {
        return mailVillage;
    }

    public void setMailVillage(String mailVillage) {
        this.mailVillage = mailVillage;
    }

    public String getMailBlock() {
        return mailBlock;
    }

    public void setMailBlock(String mailBlock) {
        this.mailBlock = mailBlock;
    }

    public String getMailCityCode() {
        return mailCityCode;
    }

    public void setMailCityCode(String mailCityCode) {
        this.mailCityCode = mailCityCode;
    }

    public String getMailStateCode() {
        return mailStateCode;
    }

    public void setMailStateCode(String mailStateCode) {
        this.mailStateCode = mailStateCode;
    }

    public String getMailPostalCode() {
        return mailPostalCode;
    }

    public void setMailPostalCode(String mailPostalCode) {
        this.mailPostalCode = mailPostalCode;
    }

    public String getMailCountryCode() {
        return mailCountryCode;
    }

    public void setMailCountryCode(String mailCountryCode) {
        this.mailCountryCode = mailCountryCode;
    }

    public String getMailPhoneNumber() {
        return mailPhoneNumber;
    }

    public void setMailPhoneNumber(String mailPhoneNumber) {
        this.mailPhoneNumber = mailPhoneNumber;
    }

    public String getMailTelexNumber() {
        return mailTelexNumber;
    }

    public void setMailTelexNumber(String mailTelexNumber) {
        this.mailTelexNumber = mailTelexNumber;
    }

    public String getMailFaxNumber() {
        return mailFaxNumber;
    }

    public void setMailFaxNumber(String mailFaxNumber) {
        this.mailFaxNumber = mailFaxNumber;
    }

    public String getEmpAddressLine1() {
        return empAddressLine1;
    }

    public void setEmpAddressLine1(String empAddressLine1) {
        this.empAddressLine1 = empAddressLine1;
    }

    public String getEmpAddressLine2() {
        return empAddressLine2;
    }

    public void setEmpAddressLine2(String empAddressLine2) {
        this.empAddressLine2 = empAddressLine2;
    }

    public String getEmpVillage() {
        return empVillage;
    }

    public void setEmpVillage(String empVillage) {
        this.empVillage = empVillage;
    }

    public String getEmpBlock() {
        return empBlock;
    }

    public void setEmpBlock(String empBlock) {
        this.empBlock = empBlock;
    }

    public String getEmpCityCode() {
        return empCityCode;
    }

    public void setEmpCityCode(String empCityCode) {
        this.empCityCode = empCityCode;
    }

    public String getEmpStateCode() {
        return empStateCode;
    }

    public void setEmpStateCode(String empStateCode) {
        this.empStateCode = empStateCode;
    }

    public String getEmpPostalCode() {
        return empPostalCode;
    }

    public void setEmpPostalCode(String empPostalCode) {
        this.empPostalCode = empPostalCode;
    }

    public String getEmpCountryCode() {
        return empCountryCode;
    }

    public void setEmpCountryCode(String empCountryCode) {
        this.empCountryCode = empCountryCode;
    }

    public String getEmpPhoneNumber() {
        return empPhoneNumber;
    }

    public void setEmpPhoneNumber(String empPhoneNumber) {
        this.empPhoneNumber = empPhoneNumber;
    }

    public String getEmpTelexNumber() {
        return empTelexNumber;
    }

    public void setEmpTelexNumber(String empTelexNumber) {
        this.empTelexNumber = empTelexNumber;
    }

    public String getEmpFaxNumber() {
        return empFaxNumber;
    }

    public void setEmpFaxNumber(String empFaxNumber) {
        this.empFaxNumber = empFaxNumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getOperationalRiskRating() {
        return operationalRiskRating;
    }

    public void setOperationalRiskRating(String operationalRiskRating) {
        this.operationalRiskRating = operationalRiskRating;
    }

    public Date getRatingAsOn() {
        return ratingAsOn;
    }

    public void setRatingAsOn(Date ratingAsOn) {
        this.ratingAsOn = ratingAsOn;
    }

    public String getCreditRiskRatingInternal() {
        return creditRiskRatingInternal;
    }

    public void setCreditRiskRatingInternal(String creditRiskRatingInternal) {
        this.creditRiskRatingInternal = creditRiskRatingInternal;
    }

    public Date getCreditRatingAsOn() {
        return creditRatingAsOn;
    }

    public void setCreditRatingAsOn(Date creditRatingAsOn) {
        this.creditRatingAsOn = creditRatingAsOn;
    }

    public String getExternalRatingShortTerm() {
        return externalRatingShortTerm;
    }

    public void setExternalRatingShortTerm(String externalRatingShortTerm) {
        this.externalRatingShortTerm = externalRatingShortTerm;
    }

    public Date getExternShortRatingAsOn() {
        return externShortRatingAsOn;
    }

    public void setExternShortRatingAsOn(Date externShortRatingAsOn) {
        this.externShortRatingAsOn = externShortRatingAsOn;
    }

    public String getExternalRatingLongTerm() {
        return externalRatingLongTerm;
    }

    public void setExternalRatingLongTerm(String externalRatingLongTerm) {
        this.externalRatingLongTerm = externalRatingLongTerm;
    }

    public Date getExternLongRatingAsOn() {
        return externLongRatingAsOn;
    }

    public void setExternLongRatingAsOn(Date externLongRatingAsOn) {
        this.externLongRatingAsOn = externLongRatingAsOn;
    }

    public Date getCustAcquistionDate() {
        return custAcquistionDate;
    }

    public void setCustAcquistionDate(Date custAcquistionDate) {
        this.custAcquistionDate = custAcquistionDate;
    }

    public Double getThresoldTransactionLimit() {
        return thresoldTransactionLimit;
    }

    public void setThresoldTransactionLimit(Double thresoldTransactionLimit) {
        this.thresoldTransactionLimit = thresoldTransactionLimit;
    }

    public Date getThresoldLimitUpdationDate() {
        return thresoldLimitUpdationDate;
    }

    public void setThresoldLimitUpdationDate(Date thresoldLimitUpdationDate) {
        this.thresoldLimitUpdationDate = thresoldLimitUpdationDate;
    }

    public Date getCustUpdationDate() {
        return custUpdationDate;
    }

    public void setCustUpdationDate(Date custUpdationDate) {
        this.custUpdationDate = custUpdationDate;
    }

    public String getSuspensionFlg() {
        return suspensionFlg;
    }

    public void setSuspensionFlg(String suspensionFlg) {
        this.suspensionFlg = suspensionFlg;
    }

    public String getPrimaryBrCode() {
        return primaryBrCode;
    }

    public void setPrimaryBrCode(String primaryBrCode) {
        this.primaryBrCode = primaryBrCode;
    }

    public String getLastUpdatedBr() {
        return lastUpdatedBr;
    }

    public void setLastUpdatedBr(String lastUpdatedBr) {
        this.lastUpdatedBr = lastUpdatedBr;
    }

    public String getFirstAccountDate() {
        return firstAccountDate;
    }

    public void setFirstAccountDate(String firstAccountDate) {
        this.firstAccountDate = firstAccountDate;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(String tsCnt) {
        this.tsCnt = tsCnt;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public String getLpgId() {
        return lpgId;
    }

    public void setLpgId(String lpgId) {
        this.lpgId = lpgId;
    }

    public String getAadhaarLpgAcno() {
        return aadhaarLpgAcno;
    }

    public void setAadhaarLpgAcno(String aadhaarLpgAcno) {
        this.aadhaarLpgAcno = aadhaarLpgAcno;
    }

    public String getMandateFlag() {
        return mandateFlag;
    }

    public void setMandateFlag(String mandateFlag) {
        this.mandateFlag = mandateFlag;
    }

    public String getMandateDate() {
        return mandateDate;
    }

    public void setMandateDate(String mandateDate) {
        this.mandateDate = mandateDate;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getNregaJobCard() {
        return nregaJobCard;
    }

    public void setNregaJobCard(String nregaJobCard) {
        this.nregaJobCard = nregaJobCard;
    }

    public String getDlExpiry() {
        return dlExpiry;
    }

    public void setDlExpiry(String dlExpiry) {
        this.dlExpiry = dlExpiry;
    }

    public String getLegalDocument() {
        return legalDocument;
    }

    public void setLegalDocument(String legalDocument) {
        this.legalDocument = legalDocument;
    }

    public String getIncomeRange() {
        return incomeRange;
    }

    public void setIncomeRange(String incomeRange) {
        this.incomeRange = incomeRange;
    }

    public String getNetworthAsOn() {
        return networthAsOn;
    }

    public void setNetworthAsOn(String networthAsOn) {
        this.networthAsOn = networthAsOn;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPoliticalExposed() {
        return politicalExposed;
    }

    public void setPoliticalExposed(String politicalExposed) {
        this.politicalExposed = politicalExposed;
    }

    public String getJuriAdd1() {
        return juriAdd1;
    }

    public void setJuriAdd1(String juriAdd1) {
        this.juriAdd1 = juriAdd1;
    }

    public String getJuriAdd2() {
        return juriAdd2;
    }

    public void setJuriAdd2(String juriAdd2) {
        this.juriAdd2 = juriAdd2;
    }

    public String getJuriCity() {
        return juriCity;
    }

    public void setJuriCity(String juriCity) {
        this.juriCity = juriCity;
    }

    public String getJuriState() {
        return juriState;
    }

    public void setJuriState(String juriState) {
        this.juriState = juriState;
    }

    public String getJuriPostal() {
        return juriPostal;
    }

    public void setJuriPostal(String juriPostal) {
        this.juriPostal = juriPostal;
    }

    public String getJuriCountry() {
        return juriCountry;
    }

    public void setJuriCountry(String juriCountry) {
        this.juriCountry = juriCountry;
    }

    public String getTan() {
        return tan;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPerEmail() {
        return perEmail;
    }

    public void setPerEmail(String perEmail) {
        this.perEmail = perEmail;
    }

    public String getMailEmail() {
        return mailEmail;
    }

    public void setMailEmail(String mailEmail) {
        this.mailEmail = mailEmail;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOtherIdentity() {
        return otherIdentity;
    }

    public void setOtherIdentity(String otherIdentity) {
        this.otherIdentity = otherIdentity;
    }

    public String getPoa() {
        return poa;
    }

    public void setPoa(String poa) {
        this.poa = poa;
    }

    public String getStrFlag() {
        return strFlag;
    }

    public void setStrFlag(String strFlag) {
        this.strFlag = strFlag;
    }

    public String getAcHolderTypeFlag() {
        return acHolderTypeFlag;
    }

    public void setAcHolderTypeFlag(String acHolderTypeFlag) {
        this.acHolderTypeFlag = acHolderTypeFlag;
    }

    public String getAcHolderType() {
        return acHolderType;
    }

    public void setAcHolderType(String acHolderType) {
        this.acHolderType = acHolderType;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getCKYCNo() {
        return cKYCNo;
    }

    public void setCKYCNo(String cKYCNo) {
        this.cKYCNo = cKYCNo;
    }

    public String getFatherMiddleName() {
        return fatherMiddleName;
    }

    public void setFatherMiddleName(String fatherMiddleName) {
        this.fatherMiddleName = fatherMiddleName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getSpouseMiddleName() {
        return spouseMiddleName;
    }

    public void setSpouseMiddleName(String spouseMiddleName) {
        this.spouseMiddleName = spouseMiddleName;
    }

    public String getSpouseLastName() {
        return spouseLastName;
    }

    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }

    public String getMotherMiddleName() {
        return motherMiddleName;
    }

    public void setMotherMiddleName(String motherMiddleName) {
        this.motherMiddleName = motherMiddleName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getTinIssuingCountry() {
        return tinIssuingCountry;
    }

    public void setTinIssuingCountry(String tinIssuingCountry) {
        this.tinIssuingCountry = tinIssuingCountry;
    }

    public String getCustEntityType() {
        return custEntityType;
    }

    public void setCustEntityType(String custEntityType) {
        this.custEntityType = custEntityType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(String idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getPerAddType() {
        return perAddType;
    }

    public void setPerAddType(String perAddType) {
        this.perAddType = perAddType;
    }

    public String getPerMailAddSameFlagIndicate() {
        return perMailAddSameFlagIndicate;
    }

    public void setPerMailAddSameFlagIndicate(String perMailAddSameFlagIndicate) {
        this.perMailAddSameFlagIndicate = perMailAddSameFlagIndicate;
    }

    public String getMailAddType() {
        return mailAddType;
    }

    public void setMailAddType(String mailAddType) {
        this.mailAddType = mailAddType;
    }

    public String getMailPoa() {
        return mailPoa;
    }

    public void setMailPoa(String mailPoa) {
        this.mailPoa = mailPoa;
    }

    public String getJuriAddBasedOnFlag() {
        return juriAddBasedOnFlag;
    }

    public void setJuriAddBasedOnFlag(String juriAddBasedOnFlag) {
        this.juriAddBasedOnFlag = juriAddBasedOnFlag;
    }

    public String getJuriAddType() {
        return juriAddType;
    }

    public void setJuriAddType(String juriAddType) {
        this.juriAddType = juriAddType;
    }

    public String getJuriPoa() {
        return juriPoa;
    }

    public void setJuriPoa(String juriPoa) {
        this.juriPoa = juriPoa;
    }

    public String getPerDistrict() {
        return perDistrict;
    }

    public void setPerDistrict(String perDistrict) {
        this.perDistrict = perDistrict;
    }

    public String getMailDistrict() {
        return mailDistrict;
    }

    public void setMailDistrict(String mailDistrict) {
        this.mailDistrict = mailDistrict;
    }

    public String getEmpDistrict() {
        return empDistrict;
    }

    public void setEmpDistrict(String empDistrict) {
        this.empDistrict = empDistrict;
    }

    public String getJuriDistrict() {
        return juriDistrict;
    }

    public void setJuriDistrict(String juriDistrict) {
        this.juriDistrict = juriDistrict;
    }

    public String getPerOtherPOA() {
        return perOtherPOA;
    }

    public void setPerOtherPOA(String perOtherPOA) {
        this.perOtherPOA = perOtherPOA;
    }

    public String getMailOtherPOA() {
        return mailOtherPOA;
    }

    public void setMailOtherPOA(String mailOtherPOA) {
        this.mailOtherPOA = mailOtherPOA;
    }

    public String getJuriOtherPOA() {
        return juriOtherPOA;
    }

    public void setJuriOtherPOA(String juriOtherPOA) {
        this.juriOtherPOA = juriOtherPOA;
    }

    public String getFatherSpouseFlag() {
        return fatherSpouseFlag;
    }

    public void setFatherSpouseFlag(String fatherSpouseFlag) {
        this.fatherSpouseFlag = fatherSpouseFlag;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getCustImage() {
        return custImage;
    }

    public void setCustImage(String custImage) {
        this.custImage = custImage;
    }

    public String getCustFullName() {
        return custFullName;
    }

    public void setCustFullName(String custFullName) {
        this.custFullName = custFullName;
    }

    public String getGstIdentificationNumber() {
        return gstIdentificationNumber;
    }

    public void setGstIdentificationNumber(String gstIdentificationNumber) {
        this.gstIdentificationNumber = gstIdentificationNumber;
    }

    public String getAadhaarMode() {
        return aadhaarMode;
    }

    public void setAadhaarMode(String aadhaarMode) {
        this.aadhaarMode = aadhaarMode;
    }

    public String getAadhaarBankIin() {
        return aadhaarBankIin;
    }

    public void setAadhaarBankIin(String aadhaarBankIin) {
        this.aadhaarBankIin = aadhaarBankIin;
    }

    public String getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerid != null ? customerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsCustomerMasterDetail)) {
            return false;
        }
        CbsCustomerMasterDetail other = (CbsCustomerMasterDetail) object;
        if ((this.customerid == null && other.customerid != null) || (this.customerid != null && !this.customerid.equals(other.customerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustomerMasterDetail[ customerid=" + customerid + " ]";
    }
}
