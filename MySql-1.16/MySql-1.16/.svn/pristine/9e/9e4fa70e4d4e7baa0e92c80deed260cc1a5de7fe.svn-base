/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.adaptor;

import com.cbs.dto.CbsCustKycDetailsTo;
import com.cbs.dto.customer.CBSBuyerSellerLimitDetailsHisTO;
import com.cbs.dto.customer.CBSBuyerSellerLimitDetailsTO;
import com.cbs.dto.customer.CBSCustCurrencyInfoHisTO;
import com.cbs.dto.customer.CBSCustCurrencyInfoPKTO;
import com.cbs.dto.customer.CBSCustCurrencyInfoTO;
import com.cbs.dto.customer.CBSCustDeliveryChannelDetailsHisTO;
import com.cbs.dto.customer.CBSCustDeliveryChannelDetailsTO;
import com.cbs.dto.customer.CBSCustIdentityDetailsTo;
import com.cbs.dto.customer.CBSCustMISInfoHisTO;
import com.cbs.dto.customer.CBSCustMISInfoTO;
import com.cbs.dto.customer.CBSCustMinorInfoHisTO;
import com.cbs.dto.customer.CBSCustMinorInfoTO;
import com.cbs.dto.customer.CBSCustNREInfoHisTO;
import com.cbs.dto.customer.CBSCustNREInfoTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailHisTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.customer.CBSRelatedPersonsDetailsHisTO;
import com.cbs.dto.customer.CBSRelatedPersonsDetailsPKTO;
import com.cbs.dto.customer.CBSRelatedPersonsDetailsTO;
import com.cbs.dto.customer.CBSTradeFinanceInformationHisTO;
import com.cbs.dto.customer.CBSTradeFinanceInformationTO;
import com.cbs.dto.customer.CbsCustAdditionalAddressDetailsTo;
import com.cbs.dto.customer.CodebookPKTO;
import com.cbs.dto.customer.CodebookTO;
import com.cbs.dto.customer.CustomerMasterPKTO;
import com.cbs.dto.customer.CustomerMasterTO;
import com.cbs.dto.customer.CustomeridHisTO;
import com.cbs.dto.customer.CustomeridPKTO;
import com.cbs.dto.customer.CustomeridTO;
import com.cbs.entity.customer.CBSBuyerSellerLimitDetails;
import com.cbs.entity.customer.CBSBuyerSellerLimitDetailsHis;
import com.cbs.entity.customer.CBSCustCurrencyInfo;
import com.cbs.entity.customer.CBSCustCurrencyInfoHis;
import com.cbs.entity.customer.CBSCustCurrencyInfoPK;
import com.cbs.entity.customer.CBSCustDeliveryChannelDetails;
import com.cbs.entity.customer.CBSCustDeliveryChannelDetailsHis;
import com.cbs.entity.customer.CbsCustMisinfo;
import com.cbs.entity.customer.CbsCustMisinfoHis;
import com.cbs.entity.customer.CBSCustMinorInfo;
import com.cbs.entity.customer.CBSCustMinorInfoHis;
import com.cbs.entity.customer.CbsRelatedPersonsDetails;
import com.cbs.entity.customer.CbsRelatedPersonsDetailsHis;
import com.cbs.entity.customer.CbsRelatedPersonsDetailsPK;
import com.cbs.entity.customer.CBSTradeFinanceInformation;
import com.cbs.entity.customer.CBSTradeFinanceInformationHis;
import com.cbs.entity.customer.CbsCustAdditionalAddressDetails;
import com.cbs.entity.customer.CbsCustAdditionalAddressDetailsHis;
import com.cbs.entity.customer.CbsCustAdditionalAddressDetailsPK;
import com.cbs.entity.customer.CbsCustIdentityDetails;
import com.cbs.entity.customer.CbsCustIdentityDetailsPK;
import com.cbs.entity.customer.CbsCustKycDetails;
import com.cbs.entity.customer.CbsCustNreinfo;
import com.cbs.entity.customer.CbsCustNreinfoHis;
import com.cbs.entity.customer.Customermaster;
import com.cbs.entity.customer.CustomermasterPK;
import com.cbs.entity.customer.Customerid;
import com.cbs.entity.customer.CustomeridHis;
import com.cbs.entity.customer.CustomeridPK;
import com.cbs.entity.ho.investment.Codebook;
import com.cbs.entity.ho.investment.CodebookPK;
import com.cbs.entity.customer.CbsCustomerMasterDetail;
import com.cbs.entity.customer.CbsCustomerMasterDetailHis;

public class ObjectAdaptorCustomer {

    /**
     *
     * @param cbsBuyerSellerLmtDtlHisTO
     * @return
     */
    public static CBSBuyerSellerLimitDetailsHis adaptToCBSBuyerSellerLmtDtlHisEntity(CBSBuyerSellerLimitDetailsHisTO cbsBuyerSellerLmtDtlHisTO) {
        CBSBuyerSellerLimitDetailsHis cbsBuyerSellerLmtDtlHisE = new CBSBuyerSellerLimitDetailsHis();

        cbsBuyerSellerLmtDtlHisE.setCustomerId(cbsBuyerSellerLmtDtlHisTO.getCustomerId());
        cbsBuyerSellerLmtDtlHisE.setDraweeCode(cbsBuyerSellerLmtDtlHisTO.getDraweeCode());
        cbsBuyerSellerLmtDtlHisE.setBillType(cbsBuyerSellerLmtDtlHisTO.getBillType());

        cbsBuyerSellerLmtDtlHisE.setStaus(cbsBuyerSellerLmtDtlHisTO.getStaus());
        cbsBuyerSellerLmtDtlHisE.setImpCurrencyCodeCCY(cbsBuyerSellerLmtDtlHisTO.getImpCurrencyCodeCCY());
        cbsBuyerSellerLmtDtlHisE.setImportLimit(cbsBuyerSellerLmtDtlHisTO.getImportLimit());

        cbsBuyerSellerLmtDtlHisE.setUtilisedImportLimit(cbsBuyerSellerLmtDtlHisTO.getUtilisedImportLimit());
        cbsBuyerSellerLmtDtlHisE.setBuyLimit(cbsBuyerSellerLmtDtlHisTO.getBuyLimit());
        cbsBuyerSellerLmtDtlHisE.setUtilisedBuyLimit(cbsBuyerSellerLmtDtlHisTO.getUtilisedBuyLimit());

        cbsBuyerSellerLmtDtlHisE.setExpCurrencyCodeCCY(cbsBuyerSellerLmtDtlHisTO.getExpCurrencyCodeCCY());
        cbsBuyerSellerLmtDtlHisE.setExportLimit(cbsBuyerSellerLmtDtlHisTO.getExportLimit());
        cbsBuyerSellerLmtDtlHisE.setUtilisedExportLimit(cbsBuyerSellerLmtDtlHisTO.getUtilisedExportLimit());

        cbsBuyerSellerLmtDtlHisE.setSellLimit(cbsBuyerSellerLmtDtlHisTO.getSellLimit());
        cbsBuyerSellerLmtDtlHisE.setUtilisedSellLimit(cbsBuyerSellerLmtDtlHisTO.getUtilisedSellLimit());
        cbsBuyerSellerLmtDtlHisE.setLastChangeUserID(cbsBuyerSellerLmtDtlHisTO.getLastChangeUserID());

        cbsBuyerSellerLmtDtlHisE.setLastChangeTime(cbsBuyerSellerLmtDtlHisTO.getLastChangeTime());
        cbsBuyerSellerLmtDtlHisE.setRecordCreaterID(cbsBuyerSellerLmtDtlHisTO.getRecordCreaterID());
        cbsBuyerSellerLmtDtlHisE.setTxnid(cbsBuyerSellerLmtDtlHisTO.getTxnid());

        return cbsBuyerSellerLmtDtlHisE;
    }

    /**
     *
     * @param cbsBuyerSellerLmtDtlHis
     * @return
     */
    public static CBSBuyerSellerLimitDetailsHisTO adaptToCBSBuyerSellerLmtDtlHisTO(CBSBuyerSellerLimitDetailsHis cbsBuyerSellerLmtDtlHis) {
        CBSBuyerSellerLimitDetailsHisTO cbsBuyerSellerLmtDtlHisTo = new CBSBuyerSellerLimitDetailsHisTO();

        cbsBuyerSellerLmtDtlHisTo.setCustomerId(cbsBuyerSellerLmtDtlHis.getCustomerId());
        cbsBuyerSellerLmtDtlHisTo.setDraweeCode(cbsBuyerSellerLmtDtlHis.getDraweeCode());
        cbsBuyerSellerLmtDtlHisTo.setBillType(cbsBuyerSellerLmtDtlHis.getBillType());

        cbsBuyerSellerLmtDtlHisTo.setStaus(cbsBuyerSellerLmtDtlHis.getStaus());
        cbsBuyerSellerLmtDtlHisTo.setImpCurrencyCodeCCY(cbsBuyerSellerLmtDtlHis.getImpCurrencyCodeCCY());
        cbsBuyerSellerLmtDtlHisTo.setImportLimit(cbsBuyerSellerLmtDtlHis.getImportLimit());

        cbsBuyerSellerLmtDtlHisTo.setUtilisedImportLimit(cbsBuyerSellerLmtDtlHis.getUtilisedImportLimit());
        cbsBuyerSellerLmtDtlHisTo.setBuyLimit(cbsBuyerSellerLmtDtlHis.getBuyLimit());
        cbsBuyerSellerLmtDtlHisTo.setUtilisedBuyLimit(cbsBuyerSellerLmtDtlHis.getUtilisedBuyLimit());

        cbsBuyerSellerLmtDtlHisTo.setExpCurrencyCodeCCY(cbsBuyerSellerLmtDtlHis.getExpCurrencyCodeCCY());
        cbsBuyerSellerLmtDtlHisTo.setExportLimit(cbsBuyerSellerLmtDtlHis.getExportLimit());
        cbsBuyerSellerLmtDtlHisTo.setUtilisedExportLimit(cbsBuyerSellerLmtDtlHis.getUtilisedExportLimit());

        cbsBuyerSellerLmtDtlHisTo.setSellLimit(cbsBuyerSellerLmtDtlHis.getSellLimit());
        cbsBuyerSellerLmtDtlHisTo.setUtilisedSellLimit(cbsBuyerSellerLmtDtlHis.getUtilisedSellLimit());
        cbsBuyerSellerLmtDtlHisTo.setLastChangeUserID(cbsBuyerSellerLmtDtlHis.getLastChangeUserID());

        cbsBuyerSellerLmtDtlHisTo.setLastChangeTime(cbsBuyerSellerLmtDtlHis.getLastChangeTime());
        cbsBuyerSellerLmtDtlHisTo.setRecordCreaterID(cbsBuyerSellerLmtDtlHis.getRecordCreaterID());
        cbsBuyerSellerLmtDtlHisTo.setTxnid(cbsBuyerSellerLmtDtlHis.getTxnid());

        return cbsBuyerSellerLmtDtlHisTo;
    }

    /**
     *
     * @param cbsBuyerSellerLmtDtlTO
     * @return
     */
    public static CBSBuyerSellerLimitDetails adaptToCBSBuyerSellerLmtDtlEntity(CBSBuyerSellerLimitDetailsTO cbsBuyerSellerLmtDtlTO) {
        CBSBuyerSellerLimitDetails cbsBuyerSellerLmtDtlE = new CBSBuyerSellerLimitDetails();

        cbsBuyerSellerLmtDtlE.setCustomerId(cbsBuyerSellerLmtDtlTO.getCustomerId());
        cbsBuyerSellerLmtDtlE.setDraweeCode(cbsBuyerSellerLmtDtlTO.getDraweeCode());
        cbsBuyerSellerLmtDtlE.setBillType(cbsBuyerSellerLmtDtlTO.getBillType());

        cbsBuyerSellerLmtDtlE.setStaus(cbsBuyerSellerLmtDtlTO.getStaus());
        cbsBuyerSellerLmtDtlE.setImpCurrencyCodeCCY(cbsBuyerSellerLmtDtlTO.getImpCurrencyCodeCCY());
        cbsBuyerSellerLmtDtlE.setImportLimit(cbsBuyerSellerLmtDtlTO.getImportLimit());

        cbsBuyerSellerLmtDtlE.setUtilisedImportLimit(cbsBuyerSellerLmtDtlTO.getUtilisedImportLimit());
        cbsBuyerSellerLmtDtlE.setBuyLimit(cbsBuyerSellerLmtDtlTO.getBuyLimit());
        cbsBuyerSellerLmtDtlE.setUtilisedBuyLimit(cbsBuyerSellerLmtDtlTO.getUtilisedBuyLimit());

        cbsBuyerSellerLmtDtlE.setExpCurrencyCodeCCY(cbsBuyerSellerLmtDtlTO.getExpCurrencyCodeCCY());
        cbsBuyerSellerLmtDtlE.setExportLimit(cbsBuyerSellerLmtDtlTO.getExportLimit());
        cbsBuyerSellerLmtDtlE.setUtilisedExportLimit(cbsBuyerSellerLmtDtlTO.getUtilisedExportLimit());

        cbsBuyerSellerLmtDtlE.setSellLimit(cbsBuyerSellerLmtDtlTO.getSellLimit());
        cbsBuyerSellerLmtDtlE.setUtilisedSellLimit(cbsBuyerSellerLmtDtlTO.getUtilisedSellLimit());
        cbsBuyerSellerLmtDtlE.setLastChangeUserID(cbsBuyerSellerLmtDtlTO.getLastChangeUserID());

        cbsBuyerSellerLmtDtlE.setLastChangeTime(cbsBuyerSellerLmtDtlTO.getLastChangeTime());
        cbsBuyerSellerLmtDtlE.setRecordCreaterID(cbsBuyerSellerLmtDtlTO.getRecordCreaterID());
        cbsBuyerSellerLmtDtlE.setCreationTime(cbsBuyerSellerLmtDtlTO.getCreationTime());

        cbsBuyerSellerLmtDtlE.setTsCnt(cbsBuyerSellerLmtDtlTO.getTsCnt());

        return cbsBuyerSellerLmtDtlE;
    }

    /**
     *
     * @param cbsBuyerSellerLmtDtl
     * @return
     */
    public static CBSBuyerSellerLimitDetailsTO adaptToCBSBuyerSellerLmtDtlTO(CBSBuyerSellerLimitDetails cbsBuyerSellerLmtDtl) {
        CBSBuyerSellerLimitDetailsTO cbsBuyerSellerLmtDtlTo = new CBSBuyerSellerLimitDetailsTO();

        cbsBuyerSellerLmtDtlTo.setCustomerId(cbsBuyerSellerLmtDtl.getCustomerId());
        cbsBuyerSellerLmtDtlTo.setDraweeCode(cbsBuyerSellerLmtDtl.getDraweeCode());
        cbsBuyerSellerLmtDtlTo.setBillType(cbsBuyerSellerLmtDtl.getBillType());

        cbsBuyerSellerLmtDtlTo.setStaus(cbsBuyerSellerLmtDtl.getStaus());
        cbsBuyerSellerLmtDtlTo.setImpCurrencyCodeCCY(cbsBuyerSellerLmtDtl.getImpCurrencyCodeCCY());
        cbsBuyerSellerLmtDtlTo.setImportLimit(cbsBuyerSellerLmtDtl.getImportLimit());

        cbsBuyerSellerLmtDtlTo.setUtilisedImportLimit(cbsBuyerSellerLmtDtl.getUtilisedImportLimit());
        cbsBuyerSellerLmtDtlTo.setBuyLimit(cbsBuyerSellerLmtDtl.getBuyLimit());
        cbsBuyerSellerLmtDtlTo.setUtilisedBuyLimit(cbsBuyerSellerLmtDtl.getUtilisedBuyLimit());

        cbsBuyerSellerLmtDtlTo.setExpCurrencyCodeCCY(cbsBuyerSellerLmtDtl.getExpCurrencyCodeCCY());
        cbsBuyerSellerLmtDtlTo.setExportLimit(cbsBuyerSellerLmtDtl.getExportLimit());
        cbsBuyerSellerLmtDtlTo.setUtilisedExportLimit(cbsBuyerSellerLmtDtl.getUtilisedExportLimit());

        cbsBuyerSellerLmtDtlTo.setSellLimit(cbsBuyerSellerLmtDtl.getSellLimit());
        cbsBuyerSellerLmtDtlTo.setUtilisedSellLimit(cbsBuyerSellerLmtDtl.getUtilisedSellLimit());
        cbsBuyerSellerLmtDtlTo.setLastChangeUserID(cbsBuyerSellerLmtDtl.getLastChangeUserID());

        cbsBuyerSellerLmtDtlTo.setLastChangeTime(cbsBuyerSellerLmtDtl.getLastChangeTime());
        cbsBuyerSellerLmtDtlTo.setRecordCreaterID(cbsBuyerSellerLmtDtl.getRecordCreaterID());
        cbsBuyerSellerLmtDtlTo.setCreationTime(cbsBuyerSellerLmtDtl.getCreationTime());

        cbsBuyerSellerLmtDtlTo.setTsCnt(cbsBuyerSellerLmtDtl.getTsCnt());

        return cbsBuyerSellerLmtDtlTo;
    }

    /**
     *
     * @param cbsCustCurrInfoHisTO
     * @return
     */
    public static CBSCustCurrencyInfoHis adaptToCBSCustCurrencyInfoHisEntity(CBSCustCurrencyInfoHisTO cbsCustCurrInfoHisTO) {
        CBSCustCurrencyInfoHis cbsCustCurrInfoHisE = new CBSCustCurrencyInfoHis();

        cbsCustCurrInfoHisE.setCustomerId(cbsCustCurrInfoHisTO.getCustomerId());
        cbsCustCurrInfoHisE.setCurrencyCodeType(cbsCustCurrInfoHisTO.getCurrencyCodeType());
        cbsCustCurrInfoHisE.setWithHoldingTaxLevel(cbsCustCurrInfoHisTO.getWithHoldingTaxLevel());

        cbsCustCurrInfoHisE.setWithHoldingTax(cbsCustCurrInfoHisTO.getWithHoldingTax());
        cbsCustCurrInfoHisE.setWithHoldingTaxLimit(cbsCustCurrInfoHisTO.getWithHoldingTaxLimit());
        cbsCustCurrInfoHisE.setTDSOperativeAccountID(cbsCustCurrInfoHisTO.gettDSOperativeAccountID());

        cbsCustCurrInfoHisE.setCustinterestRateCredit(cbsCustCurrInfoHisTO.getCustinterestRateCredit());
        cbsCustCurrInfoHisE.setCustInterestRateDebit(cbsCustCurrInfoHisTO.getCustInterestRateDebit());
        cbsCustCurrInfoHisE.setPreferentialInterestTillDate(cbsCustCurrInfoHisTO.getPreferentialInterestTillDate());

        cbsCustCurrInfoHisE.setLastChangeUserID(cbsCustCurrInfoHisTO.getLastChangeUserID());
        cbsCustCurrInfoHisE.setLastChangeTime(cbsCustCurrInfoHisTO.getLastChangeTime());
        cbsCustCurrInfoHisE.setRecordCreaterID(cbsCustCurrInfoHisTO.getRecordCreaterID());

        cbsCustCurrInfoHisE.setTxnid(cbsCustCurrInfoHisTO.getTxnid());

        return cbsCustCurrInfoHisE;
    }

    /**
     *
     * @param cbsCustCurrInfoHis
     * @return
     */
    public static CBSCustCurrencyInfoHisTO adaptToCBSCustCurrencyInfoHisTO(CBSCustCurrencyInfoHis cbsCustCurrInfoHis) {
        CBSCustCurrencyInfoHisTO cbsCustCurrInfoHisTo = new CBSCustCurrencyInfoHisTO();

        cbsCustCurrInfoHisTo.setCustomerId(cbsCustCurrInfoHis.getCustomerId());
        cbsCustCurrInfoHisTo.setCurrencyCodeType(cbsCustCurrInfoHis.getCurrencyCodeType());
        cbsCustCurrInfoHisTo.setWithHoldingTaxLevel(cbsCustCurrInfoHis.getWithHoldingTaxLevel());

        cbsCustCurrInfoHisTo.setWithHoldingTax(cbsCustCurrInfoHis.getWithHoldingTax());
        cbsCustCurrInfoHisTo.setWithHoldingTaxLimit(cbsCustCurrInfoHis.getWithHoldingTaxLimit());
        cbsCustCurrInfoHisTo.settDSOperativeAccountID(cbsCustCurrInfoHis.getTDSOperativeAccountID());

        cbsCustCurrInfoHisTo.setCustinterestRateCredit(cbsCustCurrInfoHis.getCustinterestRateCredit());
        cbsCustCurrInfoHisTo.setCustInterestRateDebit(cbsCustCurrInfoHis.getCustInterestRateDebit());
        cbsCustCurrInfoHisTo.setPreferentialInterestTillDate(cbsCustCurrInfoHis.getPreferentialInterestTillDate());

        cbsCustCurrInfoHisTo.setLastChangeUserID(cbsCustCurrInfoHis.getLastChangeUserID());
        cbsCustCurrInfoHisTo.setLastChangeTime(cbsCustCurrInfoHis.getLastChangeTime());
        cbsCustCurrInfoHisTo.setRecordCreaterID(cbsCustCurrInfoHis.getRecordCreaterID());

        cbsCustCurrInfoHisTo.setTxnid(cbsCustCurrInfoHis.getTxnid());

        return cbsCustCurrInfoHisTo;
    }

    /**
     *
     * @param cbsCustCurrInfoPKTO
     * @return
     */
    public static CBSCustCurrencyInfoPK adaptToCBSCustCurrencyInfoPKEntity(CBSCustCurrencyInfoPKTO cbsCustCurrInfoPKTO) {
        CBSCustCurrencyInfoPK cbsCustCurrInfoPKE = new CBSCustCurrencyInfoPK();

        cbsCustCurrInfoPKE.setCustomerId(cbsCustCurrInfoPKTO.getCustomerId());
        cbsCustCurrInfoPKE.setCurrencyCodeType(cbsCustCurrInfoPKTO.getCurrencyCodeType());

        return cbsCustCurrInfoPKE;
    }

    public static CBSCustCurrencyInfoPKTO adaptToCBSCustCurrencyInfoPKTO(CBSCustCurrencyInfoPK cbsCustCurrInfoPK) {
        CBSCustCurrencyInfoPKTO cbsCustCurrInfoPKTo = new CBSCustCurrencyInfoPKTO();

        cbsCustCurrInfoPKTo.setCustomerId(cbsCustCurrInfoPK.getCustomerId());
        cbsCustCurrInfoPKTo.setCurrencyCodeType(cbsCustCurrInfoPK.getCurrencyCodeType());

        return cbsCustCurrInfoPKTo;
    }

    public static CBSCustCurrencyInfo adaptToCBSCustCurrencyInfoEntity(CBSCustCurrencyInfoTO cbsCustCurrInfoTO) {
        CBSCustCurrencyInfo cbsCustCurrInfoE = new CBSCustCurrencyInfo();

        cbsCustCurrInfoE.setCBSCustCurrencyInfoPK(ObjectAdaptorCustomer.adaptToCBSCustCurrencyInfoPKEntity(cbsCustCurrInfoTO.getcBSCustCurrencyInfoPKTO()));

        cbsCustCurrInfoE.setWithHoldingTaxLevel(cbsCustCurrInfoTO.getWithHoldingTaxLevel());
        cbsCustCurrInfoE.setWithHoldingTax(cbsCustCurrInfoTO.getWithHoldingTax());
        cbsCustCurrInfoE.setWithHoldingTaxLimit(cbsCustCurrInfoTO.getWithHoldingTaxLimit());

        cbsCustCurrInfoE.setTDSOperativeAccountID(cbsCustCurrInfoTO.gettDSOperativeAccountID());
        cbsCustCurrInfoE.setCustinterestRateCredit(cbsCustCurrInfoTO.getCustinterestRateCredit());
        cbsCustCurrInfoE.setCustInterestRateDebit(cbsCustCurrInfoTO.getCustInterestRateDebit());

        cbsCustCurrInfoE.setPreferentialInterestTillDate(cbsCustCurrInfoTO.getPreferentialInterestTillDate());
        cbsCustCurrInfoE.setLastChangeUserID(cbsCustCurrInfoTO.getLastChangeUserID());
        cbsCustCurrInfoE.setLastChangeTime(cbsCustCurrInfoTO.getLastChangeTime());

        cbsCustCurrInfoE.setRecordCreaterID(cbsCustCurrInfoTO.getRecordCreaterID());
        cbsCustCurrInfoE.setCreationTime(cbsCustCurrInfoTO.getCreationTime());
        cbsCustCurrInfoE.setTsCnt(cbsCustCurrInfoTO.getTsCnt());

        return cbsCustCurrInfoE;
    }

    public static CBSCustCurrencyInfoTO adaptToCBSCustCurrencyInfoTO(CBSCustCurrencyInfo cbsCustCurrInfo) {
        CBSCustCurrencyInfoTO cbsCustCurrInfoTo = new CBSCustCurrencyInfoTO();

        cbsCustCurrInfoTo.setcBSCustCurrencyInfoPKTO(ObjectAdaptorCustomer.adaptToCBSCustCurrencyInfoPKTO(cbsCustCurrInfo.getCBSCustCurrencyInfoPK()));

        cbsCustCurrInfoTo.setWithHoldingTaxLevel(cbsCustCurrInfo.getWithHoldingTaxLevel());
        cbsCustCurrInfoTo.setWithHoldingTax(cbsCustCurrInfo.getWithHoldingTax());
        cbsCustCurrInfoTo.setWithHoldingTaxLimit(cbsCustCurrInfo.getWithHoldingTaxLimit());

        cbsCustCurrInfoTo.settDSOperativeAccountID(cbsCustCurrInfo.getTDSOperativeAccountID());
        cbsCustCurrInfoTo.setCustinterestRateCredit(cbsCustCurrInfo.getCustinterestRateCredit());
        cbsCustCurrInfoTo.setCustInterestRateDebit(cbsCustCurrInfo.getCustInterestRateDebit());

        cbsCustCurrInfoTo.setPreferentialInterestTillDate(cbsCustCurrInfo.getPreferentialInterestTillDate());
        cbsCustCurrInfoTo.setLastChangeUserID(cbsCustCurrInfo.getLastChangeUserID());
        cbsCustCurrInfoTo.setLastChangeTime(cbsCustCurrInfo.getLastChangeTime());

        cbsCustCurrInfoTo.setRecordCreaterID(cbsCustCurrInfo.getRecordCreaterID());
        cbsCustCurrInfoTo.setCreationTime(cbsCustCurrInfo.getCreationTime());
        cbsCustCurrInfoTo.setTsCnt(cbsCustCurrInfo.getTsCnt());

        return cbsCustCurrInfoTo;
    }

    public static CBSCustDeliveryChannelDetailsHis adaptToCBSCustDelChannelDtlHisEntity(CBSCustDeliveryChannelDetailsHisTO cbsCustDelChannelDtlHisTO) {
        CBSCustDeliveryChannelDetailsHis cbsCustDelChannelDtlHisE = new CBSCustDeliveryChannelDetailsHis();

        cbsCustDelChannelDtlHisE.setCustomerId(cbsCustDelChannelDtlHisTO.getCustomerId());
        cbsCustDelChannelDtlHisE.setATMDebitCardHolder(cbsCustDelChannelDtlHisTO.getaTMDebitCardHolder());
        cbsCustDelChannelDtlHisE.setCreditCardEnabled(cbsCustDelChannelDtlHisTO.getCreditCardEnabled());

        cbsCustDelChannelDtlHisE.setTeleBankingEnabled(cbsCustDelChannelDtlHisTO.getTeleBankingEnabled());
        cbsCustDelChannelDtlHisE.setSmsBankingEnabled(cbsCustDelChannelDtlHisTO.getSmsBankingEnabled());
        cbsCustDelChannelDtlHisE.setINetBankingEnabled(cbsCustDelChannelDtlHisTO.getiNetBankingEnabled());

        cbsCustDelChannelDtlHisE.setMobileBankingEnabled(cbsCustDelChannelDtlHisTO.getMobileBankingEnabled());
        cbsCustDelChannelDtlHisE.setSelfServiceEnabled(cbsCustDelChannelDtlHisTO.getSelfServiceEnabled());
        cbsCustDelChannelDtlHisE.setECSEnabled(cbsCustDelChannelDtlHisTO.geteCSEnabled());

        cbsCustDelChannelDtlHisE.setLastChangeUserID(cbsCustDelChannelDtlHisTO.getLastChangeUserID());
        cbsCustDelChannelDtlHisE.setLastChangeTime(cbsCustDelChannelDtlHisTO.getLastChangeTime());
        cbsCustDelChannelDtlHisE.setRecordCreaterID(cbsCustDelChannelDtlHisTO.getRecordCreaterID());

        cbsCustDelChannelDtlHisE.setTxnid(cbsCustDelChannelDtlHisTO.getTxnid());

        return cbsCustDelChannelDtlHisE;
    }

    public static CBSCustDeliveryChannelDetailsHisTO adaptToCBSCustDelChannelDtlHisTO(CBSCustDeliveryChannelDetailsHis cbsCustDelChannelDtlHis) {
        CBSCustDeliveryChannelDetailsHisTO cbsCustDelChannelDtlHisTo = new CBSCustDeliveryChannelDetailsHisTO();

        cbsCustDelChannelDtlHisTo.setCustomerId(cbsCustDelChannelDtlHis.getCustomerId());
        cbsCustDelChannelDtlHisTo.setaTMDebitCardHolder(cbsCustDelChannelDtlHis.getATMDebitCardHolder());
        cbsCustDelChannelDtlHisTo.setCreditCardEnabled(cbsCustDelChannelDtlHis.getCreditCardEnabled());

        cbsCustDelChannelDtlHisTo.setTeleBankingEnabled(cbsCustDelChannelDtlHis.getTeleBankingEnabled());
        cbsCustDelChannelDtlHisTo.setSmsBankingEnabled(cbsCustDelChannelDtlHis.getSmsBankingEnabled());
        cbsCustDelChannelDtlHisTo.setiNetBankingEnabled(cbsCustDelChannelDtlHis.getINetBankingEnabled());

        cbsCustDelChannelDtlHisTo.setMobileBankingEnabled(cbsCustDelChannelDtlHis.getMobileBankingEnabled());
        cbsCustDelChannelDtlHisTo.setSelfServiceEnabled(cbsCustDelChannelDtlHis.getSelfServiceEnabled());
        cbsCustDelChannelDtlHisTo.seteCSEnabled(cbsCustDelChannelDtlHis.getECSEnabled());

        cbsCustDelChannelDtlHisTo.setLastChangeUserID(cbsCustDelChannelDtlHis.getLastChangeUserID());
        cbsCustDelChannelDtlHisTo.setLastChangeTime(cbsCustDelChannelDtlHis.getLastChangeTime());
        cbsCustDelChannelDtlHisTo.setRecordCreaterID(cbsCustDelChannelDtlHis.getRecordCreaterID());

        cbsCustDelChannelDtlHisTo.setTxnid(cbsCustDelChannelDtlHis.getTxnid());

        return cbsCustDelChannelDtlHisTo;
    }

    public static CBSCustDeliveryChannelDetails adaptToCBSCustDeliveryChannelDtlEntity(CBSCustDeliveryChannelDetailsTO cbsCustDelChannelDtlTO) {
        CBSCustDeliveryChannelDetails cbsCustDelChannelDtlE = new CBSCustDeliveryChannelDetails();

        cbsCustDelChannelDtlE.setCustomerId(cbsCustDelChannelDtlTO.getCustomerId());
        cbsCustDelChannelDtlE.setATMDebitCardHolder(cbsCustDelChannelDtlTO.getaTMDebitCardHolder());
        cbsCustDelChannelDtlE.setCreditCardEnabled(cbsCustDelChannelDtlTO.getCreditCardEnabled());

        cbsCustDelChannelDtlE.setTeleBankingEnabled(cbsCustDelChannelDtlTO.getTeleBankingEnabled());
        cbsCustDelChannelDtlE.setSmsBankingEnabled(cbsCustDelChannelDtlTO.getSmsBankingEnabled());
        cbsCustDelChannelDtlE.setINetBankingEnabled(cbsCustDelChannelDtlTO.getiNetBankingEnabled());

        cbsCustDelChannelDtlE.setMobileBankingEnabled(cbsCustDelChannelDtlTO.getMobileBankingEnabled());
        cbsCustDelChannelDtlE.setSelfServiceEnabled(cbsCustDelChannelDtlTO.getSelfServiceEnabled());
        cbsCustDelChannelDtlE.setECSEnabled(cbsCustDelChannelDtlTO.geteCSEnabled());

        cbsCustDelChannelDtlE.setLastChangeUserID(cbsCustDelChannelDtlTO.getLastChangeUserID());
        cbsCustDelChannelDtlE.setLastChangeTime(cbsCustDelChannelDtlTO.getLastChangeTime());
        cbsCustDelChannelDtlE.setRecordCreaterID(cbsCustDelChannelDtlTO.getRecordCreaterID());

        cbsCustDelChannelDtlE.setCreationTime(cbsCustDelChannelDtlTO.getCreationTime());
        cbsCustDelChannelDtlE.setTsCnt(cbsCustDelChannelDtlTO.getTsCnt());

        return cbsCustDelChannelDtlE;
    }

    public static CBSCustDeliveryChannelDetailsTO adaptToCBSCustDeliveryChannelDtlTO(CBSCustDeliveryChannelDetails cbsCustDelChannelDtl) {
        CBSCustDeliveryChannelDetailsTO cbsCustDelChannelDtlTo = new CBSCustDeliveryChannelDetailsTO();

        cbsCustDelChannelDtlTo.setCustomerId(cbsCustDelChannelDtl.getCustomerId());
        cbsCustDelChannelDtlTo.setaTMDebitCardHolder(cbsCustDelChannelDtl.getATMDebitCardHolder());
        cbsCustDelChannelDtlTo.setCreditCardEnabled(cbsCustDelChannelDtl.getCreditCardEnabled());

        cbsCustDelChannelDtlTo.setTeleBankingEnabled(cbsCustDelChannelDtl.getTeleBankingEnabled());
        cbsCustDelChannelDtlTo.setSmsBankingEnabled(cbsCustDelChannelDtl.getSmsBankingEnabled());
        cbsCustDelChannelDtlTo.setiNetBankingEnabled(cbsCustDelChannelDtl.getINetBankingEnabled());

        cbsCustDelChannelDtlTo.setMobileBankingEnabled(cbsCustDelChannelDtl.getMobileBankingEnabled());
        cbsCustDelChannelDtlTo.setSelfServiceEnabled(cbsCustDelChannelDtl.getSelfServiceEnabled());
        cbsCustDelChannelDtlTo.seteCSEnabled(cbsCustDelChannelDtl.getECSEnabled());

        cbsCustDelChannelDtlTo.setLastChangeUserID(cbsCustDelChannelDtl.getLastChangeUserID());
        cbsCustDelChannelDtlTo.setLastChangeTime(cbsCustDelChannelDtl.getLastChangeTime());
        cbsCustDelChannelDtlTo.setRecordCreaterID(cbsCustDelChannelDtl.getRecordCreaterID());

        cbsCustDelChannelDtlTo.setCreationTime(cbsCustDelChannelDtl.getCreationTime());
        cbsCustDelChannelDtlTo.setTsCnt(cbsCustDelChannelDtl.getTsCnt());

        return cbsCustDelChannelDtlTo;
    }

    public static CbsCustMisinfoHis adaptToCBSCustMISInfoHisEntity(CBSCustMISInfoHisTO cbsCustMISInfoHisTO) {
        CbsCustMisinfoHis cbsCustMISInfoHisE = new CbsCustMisinfoHis();

        cbsCustMISInfoHisE.setCustomerId(cbsCustMISInfoHisTO.getCustomerId());
        cbsCustMISInfoHisE.setType(cbsCustMISInfoHisTO.getType());
        cbsCustMISInfoHisE.setGroupID(cbsCustMISInfoHisTO.getGroupID());

        cbsCustMISInfoHisE.setStatusCode(cbsCustMISInfoHisTO.getStatusCode());
        cbsCustMISInfoHisE.setStatusAsOn(cbsCustMISInfoHisTO.getStatusAsOn());
        cbsCustMISInfoHisE.setOccupationCode(cbsCustMISInfoHisTO.getOccupationCode());

        cbsCustMISInfoHisE.setConstitutionCode(cbsCustMISInfoHisTO.getConstitutionCode());
        cbsCustMISInfoHisE.setCasteCode(cbsCustMISInfoHisTO.getCasteCode());
        cbsCustMISInfoHisE.setCommunityCode(cbsCustMISInfoHisTO.getCommunityCode());

        cbsCustMISInfoHisE.setHealthCode(cbsCustMISInfoHisTO.getHealthCode());
        cbsCustMISInfoHisE.setBusinessSegment(cbsCustMISInfoHisTO.getBusinessSegment());
        cbsCustMISInfoHisE.setSalesTurnover(cbsCustMISInfoHisTO.getSalesTurnover());

        cbsCustMISInfoHisE.setLastChangeUserID(cbsCustMISInfoHisTO.getLastChangeUserID());
        cbsCustMISInfoHisE.setLastChangeTime(cbsCustMISInfoHisTO.getLastChangeTime());
        cbsCustMISInfoHisE.setRecordCreaterID(cbsCustMISInfoHisTO.getRecordCreaterID());

        cbsCustMISInfoHisE.setTxnid(cbsCustMISInfoHisTO.getTxnid());
        cbsCustMISInfoHisE.setIncorporationDate(cbsCustMISInfoHisTO.getIncorpDt());

        cbsCustMISInfoHisE.setIncorporationPlace(cbsCustMISInfoHisTO.getIncorpPlace());
        cbsCustMISInfoHisE.setCommencementDate(cbsCustMISInfoHisTO.getCommDt());
        cbsCustMISInfoHisE.setMisJuriResidence(cbsCustMISInfoHisTO.getMisJuriResidence());

        cbsCustMISInfoHisE.setMisTin(cbsCustMISInfoHisTO.getMisTin());
        cbsCustMISInfoHisE.setMisCity(cbsCustMISInfoHisTO.getMisCity());
        cbsCustMISInfoHisE.setMisBirthCountry(cbsCustMISInfoHisTO.getMisBirthCountry());


        cbsCustMISInfoHisE.setResidenceCountryTaxLaw(cbsCustMISInfoHisTO.getResidenceCountryTaxLaw());
        cbsCustMISInfoHisE.setCountryOfIncorp(cbsCustMISInfoHisTO.getCountryOfIncorp());
        cbsCustMISInfoHisE.setStateOfIncorp(cbsCustMISInfoHisTO.getStateOfIncorp());

        return cbsCustMISInfoHisE;
    }

    public static CBSCustMISInfoHisTO adaptToCBSCustMISInfoHisTO(CbsCustMisinfoHis cbsCustMISInfoHis) {
        CBSCustMISInfoHisTO cbsCustMISInfoHisTo = new CBSCustMISInfoHisTO();

        cbsCustMISInfoHisTo.setCustomerId(cbsCustMISInfoHis.getCustomerId());
        cbsCustMISInfoHisTo.setType(cbsCustMISInfoHis.getType());
        cbsCustMISInfoHisTo.setGroupID(cbsCustMISInfoHis.getGroupID());

        cbsCustMISInfoHisTo.setStatusCode(cbsCustMISInfoHis.getStatusCode());
        cbsCustMISInfoHisTo.setStatusAsOn(cbsCustMISInfoHis.getStatusAsOn());
        cbsCustMISInfoHisTo.setOccupationCode(cbsCustMISInfoHis.getOccupationCode());

        cbsCustMISInfoHisTo.setConstitutionCode(cbsCustMISInfoHis.getConstitutionCode());
        cbsCustMISInfoHisTo.setCasteCode(cbsCustMISInfoHis.getCasteCode());
        cbsCustMISInfoHisTo.setCommunityCode(cbsCustMISInfoHis.getCommunityCode());

        cbsCustMISInfoHisTo.setHealthCode(cbsCustMISInfoHis.getHealthCode());
        cbsCustMISInfoHisTo.setBusinessSegment(cbsCustMISInfoHis.getBusinessSegment());
        cbsCustMISInfoHisTo.setSalesTurnover(cbsCustMISInfoHis.getSalesTurnover());

        cbsCustMISInfoHisTo.setLastChangeUserID(cbsCustMISInfoHis.getLastChangeUserID());
        cbsCustMISInfoHisTo.setLastChangeTime(cbsCustMISInfoHis.getLastChangeTime());
        cbsCustMISInfoHisTo.setRecordCreaterID(cbsCustMISInfoHis.getRecordCreaterID());

        cbsCustMISInfoHisTo.setTxnid(cbsCustMISInfoHis.getTxnid());
        cbsCustMISInfoHisTo.setIncorpDt(cbsCustMISInfoHis.getIncorporationDate());

        cbsCustMISInfoHisTo.setIncorpPlace(cbsCustMISInfoHis.getIncorporationPlace());
        cbsCustMISInfoHisTo.setCommDt(cbsCustMISInfoHis.getCommencementDate());
        cbsCustMISInfoHisTo.setMisJuriResidence(cbsCustMISInfoHis.getMisJuriResidence());

        cbsCustMISInfoHisTo.setMisTin(cbsCustMISInfoHis.getMisTin());
        cbsCustMISInfoHisTo.setMisCity(cbsCustMISInfoHis.getMisCity());
        cbsCustMISInfoHisTo.setMisBirthCountry(cbsCustMISInfoHis.getMisBirthCountry());

        cbsCustMISInfoHisTo.setResidenceCountryTaxLaw(cbsCustMISInfoHis.getResidenceCountryTaxLaw());
        cbsCustMISInfoHisTo.setCountryOfIncorp(cbsCustMISInfoHis.getCountryOfIncorp());
        cbsCustMISInfoHisTo.setStateOfIncorp(cbsCustMISInfoHis.getStateOfIncorp());

        return cbsCustMISInfoHisTo;
    }

    public static CbsCustMisinfo adaptToCBSCustMISInfoEntity(CBSCustMISInfoTO cbsCustMISInfoTO) {
        CbsCustMisinfo cbsCustMISInfoE = new CbsCustMisinfo();

        cbsCustMISInfoE.setCustomerId(cbsCustMISInfoTO.getCustomerId());
        cbsCustMISInfoE.setType(cbsCustMISInfoTO.getType());
        cbsCustMISInfoE.setGroupID(cbsCustMISInfoTO.getGroupID());

        cbsCustMISInfoE.setStatusCode(cbsCustMISInfoTO.getStatusCode());
        cbsCustMISInfoE.setStatusAsOn(cbsCustMISInfoTO.getStatusAsOn());
        cbsCustMISInfoE.setOccupationCode(cbsCustMISInfoTO.getOccupationCode());

        cbsCustMISInfoE.setConstitutionCode(cbsCustMISInfoTO.getConstitutionCode());
        cbsCustMISInfoE.setCasteCode(cbsCustMISInfoTO.getCasteCode());
        cbsCustMISInfoE.setCommunityCode(cbsCustMISInfoTO.getCommunityCode());

        cbsCustMISInfoE.setHealthCode(cbsCustMISInfoTO.getHealthCode());
        cbsCustMISInfoE.setBusinessSegment(cbsCustMISInfoTO.getBusinessSegment());
        cbsCustMISInfoE.setSalesTurnover(cbsCustMISInfoTO.getSalesTurnover());

        cbsCustMISInfoE.setLastChangeUserID(cbsCustMISInfoTO.getLastChangeUserID());
        cbsCustMISInfoE.setLastChangeTime(cbsCustMISInfoTO.getLastChangeTime());
        cbsCustMISInfoE.setRecordCreaterID(cbsCustMISInfoTO.getRecordCreaterID());

        cbsCustMISInfoE.setCreationTime(cbsCustMISInfoTO.getCreationTime());
        cbsCustMISInfoE.setTsCnt(cbsCustMISInfoTO.getTsCnt());
        cbsCustMISInfoE.setIncorporationDate(cbsCustMISInfoTO.getIncorpDt());

        cbsCustMISInfoE.setIncorporationPlace(cbsCustMISInfoTO.getIncorpPlace());
        cbsCustMISInfoE.setCommencementDate(cbsCustMISInfoTO.getCommDt());
        cbsCustMISInfoE.setMisJuriResidence(cbsCustMISInfoTO.getMisJuriResidence());

        cbsCustMISInfoE.setMisTin(cbsCustMISInfoTO.getMisTin());
        cbsCustMISInfoE.setMisCity(cbsCustMISInfoTO.getMisCity());
        cbsCustMISInfoE.setMisBirthCountry(cbsCustMISInfoTO.getMisBirthCountry());

        cbsCustMISInfoE.setResidenceCountryTaxLaw(cbsCustMISInfoTO.getResidenceCountryTaxLaw());
        cbsCustMISInfoE.setCountryOfIncorp(cbsCustMISInfoTO.getCountryOfIncorp());
        cbsCustMISInfoE.setStateOfIncorp(cbsCustMISInfoTO.getStateOfIncorp());

        return cbsCustMISInfoE;
    }

    public static CBSCustMISInfoTO adaptToCBSCustMISInfoTO(CbsCustMisinfo cbsCustMISInfo) {
        CBSCustMISInfoTO cbsCustMISInfoTo = new CBSCustMISInfoTO();

        cbsCustMISInfoTo.setCustomerId(cbsCustMISInfo.getCustomerId());
        cbsCustMISInfoTo.setType(cbsCustMISInfo.getType());
        cbsCustMISInfoTo.setGroupID(cbsCustMISInfo.getGroupID());

        cbsCustMISInfoTo.setStatusCode(cbsCustMISInfo.getStatusCode());
        cbsCustMISInfoTo.setStatusAsOn(cbsCustMISInfo.getStatusAsOn());
        cbsCustMISInfoTo.setOccupationCode(cbsCustMISInfo.getOccupationCode());

        cbsCustMISInfoTo.setConstitutionCode(cbsCustMISInfo.getConstitutionCode());
        cbsCustMISInfoTo.setCasteCode(cbsCustMISInfo.getCasteCode());
        cbsCustMISInfoTo.setCommunityCode(cbsCustMISInfo.getCommunityCode());

        cbsCustMISInfoTo.setHealthCode(cbsCustMISInfo.getHealthCode());
        cbsCustMISInfoTo.setBusinessSegment(cbsCustMISInfo.getBusinessSegment());
        cbsCustMISInfoTo.setSalesTurnover(cbsCustMISInfo.getSalesTurnover());

        cbsCustMISInfoTo.setLastChangeUserID(cbsCustMISInfo.getLastChangeUserID());
        cbsCustMISInfoTo.setLastChangeTime(cbsCustMISInfo.getLastChangeTime());
        cbsCustMISInfoTo.setRecordCreaterID(cbsCustMISInfo.getRecordCreaterID());

        cbsCustMISInfoTo.setCreationTime(cbsCustMISInfo.getCreationTime());
        cbsCustMISInfoTo.setTsCnt(cbsCustMISInfo.getTsCnt());
        cbsCustMISInfoTo.setIncorpDt(cbsCustMISInfo.getIncorporationDate());

        cbsCustMISInfoTo.setIncorpPlace(cbsCustMISInfo.getIncorporationPlace());
        cbsCustMISInfoTo.setCommDt(cbsCustMISInfo.getCommencementDate());
        cbsCustMISInfoTo.setMisJuriResidence(cbsCustMISInfo.getMisJuriResidence());

        cbsCustMISInfoTo.setMisTin(cbsCustMISInfo.getMisTin());
        cbsCustMISInfoTo.setMisCity(cbsCustMISInfo.getMisCity());
        cbsCustMISInfoTo.setMisBirthCountry(cbsCustMISInfo.getMisBirthCountry());

        cbsCustMISInfoTo.setResidenceCountryTaxLaw(cbsCustMISInfo.getResidenceCountryTaxLaw());
        cbsCustMISInfoTo.setCountryOfIncorp(cbsCustMISInfo.getCountryOfIncorp());
        cbsCustMISInfoTo.setStateOfIncorp(cbsCustMISInfo.getStateOfIncorp());

        return cbsCustMISInfoTo;
    }

    public static CBSCustMinorInfoHis adaptToCBSCustMinorInfoHisEntity(CBSCustMinorInfoHisTO cbsCustMinorInfoHisTO) {
        CBSCustMinorInfoHis cbsCustMinorInfoHisE = new CBSCustMinorInfoHis();

        cbsCustMinorInfoHisE.setCustomerId(cbsCustMinorInfoHisTO.getCustomerId());
        cbsCustMinorInfoHisE.setGuardianCode(cbsCustMinorInfoHisTO.getGuardianCode());
        cbsCustMinorInfoHisE.setGuardianTitle(cbsCustMinorInfoHisTO.getGuardianTitle());

        cbsCustMinorInfoHisE.setGuardianName(cbsCustMinorInfoHisTO.getGuardianName());
        cbsCustMinorInfoHisE.setLocalAddress1(cbsCustMinorInfoHisTO.getLocalAddress1());
        cbsCustMinorInfoHisE.setLocalAddress2(cbsCustMinorInfoHisTO.getLocalAddress2());

        cbsCustMinorInfoHisE.setVillage(cbsCustMinorInfoHisTO.getVillage());
        cbsCustMinorInfoHisE.setBlock(cbsCustMinorInfoHisTO.getBlock());
        cbsCustMinorInfoHisE.setCityCode(cbsCustMinorInfoHisTO.getCityCode());

        cbsCustMinorInfoHisE.setStateCode(cbsCustMinorInfoHisTO.getStateCode());
        cbsCustMinorInfoHisE.setPostalCode(cbsCustMinorInfoHisTO.getPostalCode());
        cbsCustMinorInfoHisE.setCountryCode(cbsCustMinorInfoHisTO.getCountryCode());

        cbsCustMinorInfoHisE.setPhoneNumber(cbsCustMinorInfoHisTO.getPhoneNumber());
        cbsCustMinorInfoHisE.setMobileNumber(cbsCustMinorInfoHisTO.getMobileNumber());
        cbsCustMinorInfoHisE.setDateOfBirth(cbsCustMinorInfoHisTO.getDateOfBirth());

        cbsCustMinorInfoHisE.setMajorityDate(cbsCustMinorInfoHisTO.getMajorityDate());
        cbsCustMinorInfoHisE.setEmailID(cbsCustMinorInfoHisTO.getEmailID());
        cbsCustMinorInfoHisE.setLastChangeUserID(cbsCustMinorInfoHisTO.getLastChangeUserID());

        cbsCustMinorInfoHisE.setLastChangeTime(cbsCustMinorInfoHisTO.getLastChangeTime());
        cbsCustMinorInfoHisE.setRecordCreaterID(cbsCustMinorInfoHisTO.getRecordCreaterID());
        cbsCustMinorInfoHisE.setTxnid(cbsCustMinorInfoHisTO.getTxnid());

        return cbsCustMinorInfoHisE;
    }

    public static CBSCustMinorInfoHisTO adaptToCBSCustMinorInfoHisTO(CBSCustMinorInfoHis cbsCustMinorInfoHis) {
        CBSCustMinorInfoHisTO cbsCustMinorInfoHisTo = new CBSCustMinorInfoHisTO();

        cbsCustMinorInfoHisTo.setCustomerId(cbsCustMinorInfoHis.getCustomerId());
        cbsCustMinorInfoHisTo.setGuardianCode(cbsCustMinorInfoHis.getGuardianCode());
        cbsCustMinorInfoHisTo.setGuardianTitle(cbsCustMinorInfoHis.getGuardianTitle());

        cbsCustMinorInfoHisTo.setGuardianName(cbsCustMinorInfoHis.getGuardianName());
        cbsCustMinorInfoHisTo.setLocalAddress1(cbsCustMinorInfoHis.getLocalAddress1());
        cbsCustMinorInfoHisTo.setLocalAddress2(cbsCustMinorInfoHis.getLocalAddress2());

        cbsCustMinorInfoHisTo.setVillage(cbsCustMinorInfoHis.getVillage());
        cbsCustMinorInfoHisTo.setBlock(cbsCustMinorInfoHis.getBlock());
        cbsCustMinorInfoHisTo.setCityCode(cbsCustMinorInfoHis.getCityCode());

        cbsCustMinorInfoHisTo.setStateCode(cbsCustMinorInfoHis.getStateCode());
        cbsCustMinorInfoHisTo.setPostalCode(cbsCustMinorInfoHis.getPostalCode());
        cbsCustMinorInfoHisTo.setCountryCode(cbsCustMinorInfoHis.getCountryCode());

        cbsCustMinorInfoHisTo.setPhoneNumber(cbsCustMinorInfoHis.getPhoneNumber());
        cbsCustMinorInfoHisTo.setMobileNumber(cbsCustMinorInfoHis.getMobileNumber());
        cbsCustMinorInfoHisTo.setDateOfBirth(cbsCustMinorInfoHis.getDateOfBirth());

        cbsCustMinorInfoHisTo.setMajorityDate(cbsCustMinorInfoHis.getMajorityDate());
        cbsCustMinorInfoHisTo.setEmailID(cbsCustMinorInfoHis.getEmailID());
        cbsCustMinorInfoHisTo.setLastChangeUserID(cbsCustMinorInfoHis.getLastChangeUserID());

        cbsCustMinorInfoHisTo.setLastChangeTime(cbsCustMinorInfoHis.getLastChangeTime());
        cbsCustMinorInfoHisTo.setRecordCreaterID(cbsCustMinorInfoHis.getRecordCreaterID());
        cbsCustMinorInfoHisTo.setTxnid(cbsCustMinorInfoHis.getTxnid());

        return cbsCustMinorInfoHisTo;
    }

    public static CBSCustMinorInfo adaptToCBSCustMinorInfoEntity(CBSCustMinorInfoTO cbsCustMinorInfoTO) {
        CBSCustMinorInfo cbsCustMinorInfoE = new CBSCustMinorInfo();

        cbsCustMinorInfoE.setCustomerId(cbsCustMinorInfoTO.getCustomerId());
        cbsCustMinorInfoE.setGuardianCode(cbsCustMinorInfoTO.getGuardianCode());
        cbsCustMinorInfoE.setGuardianTitle(cbsCustMinorInfoTO.getGuardianTitle());

        cbsCustMinorInfoE.setGuardianName(cbsCustMinorInfoTO.getGuardianName());
        cbsCustMinorInfoE.setLocalAddress1(cbsCustMinorInfoTO.getLocalAddress1());
        cbsCustMinorInfoE.setLocalAddress2(cbsCustMinorInfoTO.getLocalAddress2());

        cbsCustMinorInfoE.setVillage(cbsCustMinorInfoTO.getVillage());
        cbsCustMinorInfoE.setBlock(cbsCustMinorInfoTO.getBlock());
        cbsCustMinorInfoE.setCityCode(cbsCustMinorInfoTO.getCityCode());

        cbsCustMinorInfoE.setStateCode(cbsCustMinorInfoTO.getStateCode());
        cbsCustMinorInfoE.setPostalCode(cbsCustMinorInfoTO.getPostalCode());
        cbsCustMinorInfoE.setCountryCode(cbsCustMinorInfoTO.getCountryCode());

        cbsCustMinorInfoE.setPhoneNumber(cbsCustMinorInfoTO.getPhoneNumber());
        cbsCustMinorInfoE.setMobileNumber(cbsCustMinorInfoTO.getMobileNumber());
        cbsCustMinorInfoE.setDateOfBirth(cbsCustMinorInfoTO.getDateOfBirth());

        cbsCustMinorInfoE.setMajorityDate(cbsCustMinorInfoTO.getMajorityDate());
        cbsCustMinorInfoE.setEmailID(cbsCustMinorInfoTO.getEmailID());
        cbsCustMinorInfoE.setLastChangeUserID(cbsCustMinorInfoTO.getLastChangeUserID());

        cbsCustMinorInfoE.setLastChangeTime(cbsCustMinorInfoTO.getLastChangeTime());
        cbsCustMinorInfoE.setRecordCreaterID(cbsCustMinorInfoTO.getRecordCreaterID());
        cbsCustMinorInfoE.setCreationTime(cbsCustMinorInfoTO.getCreationTime());

        cbsCustMinorInfoE.setTsCnt(cbsCustMinorInfoTO.getTsCnt());

        return cbsCustMinorInfoE;
    }

    public static CBSCustMinorInfoTO adaptToCBSCustMinorInfoTO(CBSCustMinorInfo cbsCustMinorInfo) {
        CBSCustMinorInfoTO cbsCustMinorInfoTo = new CBSCustMinorInfoTO();

        cbsCustMinorInfoTo.setCustomerId(cbsCustMinorInfo.getCustomerId());
        cbsCustMinorInfoTo.setGuardianCode(cbsCustMinorInfo.getGuardianCode());
        cbsCustMinorInfoTo.setGuardianTitle(cbsCustMinorInfo.getGuardianTitle());

        cbsCustMinorInfoTo.setGuardianName(cbsCustMinorInfo.getGuardianName());
        cbsCustMinorInfoTo.setLocalAddress1(cbsCustMinorInfo.getLocalAddress1());
        cbsCustMinorInfoTo.setLocalAddress2(cbsCustMinorInfo.getLocalAddress2());

        cbsCustMinorInfoTo.setVillage(cbsCustMinorInfo.getVillage());
        cbsCustMinorInfoTo.setBlock(cbsCustMinorInfo.getBlock());
        cbsCustMinorInfoTo.setCityCode(cbsCustMinorInfo.getCityCode());

        cbsCustMinorInfoTo.setStateCode(cbsCustMinorInfo.getStateCode());
        cbsCustMinorInfoTo.setPostalCode(cbsCustMinorInfo.getPostalCode());
        cbsCustMinorInfoTo.setCountryCode(cbsCustMinorInfo.getCountryCode());

        cbsCustMinorInfoTo.setPhoneNumber(cbsCustMinorInfo.getPhoneNumber());
        cbsCustMinorInfoTo.setMobileNumber(cbsCustMinorInfo.getMobileNumber());
        cbsCustMinorInfoTo.setDateOfBirth(cbsCustMinorInfo.getDateOfBirth());

        cbsCustMinorInfoTo.setMajorityDate(cbsCustMinorInfo.getMajorityDate());
        cbsCustMinorInfoTo.setEmailID(cbsCustMinorInfo.getEmailID());
        cbsCustMinorInfoTo.setLastChangeUserID(cbsCustMinorInfo.getLastChangeUserID());

        cbsCustMinorInfoTo.setLastChangeTime(cbsCustMinorInfo.getLastChangeTime());
        cbsCustMinorInfoTo.setRecordCreaterID(cbsCustMinorInfo.getRecordCreaterID());
        cbsCustMinorInfoTo.setCreationTime(cbsCustMinorInfo.getCreationTime());

        cbsCustMinorInfoTo.setTsCnt(cbsCustMinorInfo.getTsCnt());

        return cbsCustMinorInfoTo;
    }

    public static CbsCustNreinfoHis adaptToCBSCustNREInfoHisEntity(CBSCustNREInfoHisTO cbsCustNREInfoHisTO) {
        CbsCustNreinfoHis cbsCustNREInfoHisE = new CbsCustNreinfoHis();

        cbsCustNREInfoHisE.setCustomerId(cbsCustNREInfoHisTO.getCustomerId());
//        cbsCustNREInfoHisE.setNationality(cbsCustNREInfoHisTO.getNationality());
        cbsCustNREInfoHisE.setNonResidentDate(cbsCustNREInfoHisTO.getNonResidentDate());

        cbsCustNREInfoHisE.setCountryCode(cbsCustNREInfoHisTO.getCountryCode());
//        cbsCustNREInfoHisE.setCountryType(cbsCustNREInfoHisTO.getCountryType());
        cbsCustNREInfoHisE.setLocalContPersonCode(cbsCustNREInfoHisTO.getLocalContPersonCode());

//        cbsCustNREInfoHisE.setLocalPersonTitle(cbsCustNREInfoHisTO.getLocalPersonTitle());
//        cbsCustNREInfoHisE.setLocalContPersonName(cbsCustNREInfoHisTO.getLocalContPersonName());
        cbsCustNREInfoHisE.setLocalAddress1(cbsCustNREInfoHisTO.getLocalAddress1());

        cbsCustNREInfoHisE.setLocalAddress2(cbsCustNREInfoHisTO.getLocalAddress2());
        cbsCustNREInfoHisE.setVillage(cbsCustNREInfoHisTO.getVillage());
//        cbsCustNREInfoHisE.setBlock(cbsCustNREInfoHisTO.getBlock());

        cbsCustNREInfoHisE.setCityCode(cbsCustNREInfoHisTO.getCityCode());
        cbsCustNREInfoHisE.setStateCode(cbsCustNREInfoHisTO.getStateCode());
        cbsCustNREInfoHisE.setPostalCode(cbsCustNREInfoHisTO.getPostalCode());

        cbsCustNREInfoHisE.setPhoneNumber(cbsCustNREInfoHisTO.getPhoneNumber());
        cbsCustNREInfoHisE.setMobileNumber(cbsCustNREInfoHisTO.getMobileNumber());
        cbsCustNREInfoHisE.setNreEmail(cbsCustNREInfoHisTO.getNreEmail());
        cbsCustNREInfoHisE.setNonResidentEndDate(cbsCustNREInfoHisTO.getNonResidentEndDate());

        cbsCustNREInfoHisE.setLastChangeUserID(cbsCustNREInfoHisTO.getLastChangeUserID());
        cbsCustNREInfoHisE.setLastChangeTime(cbsCustNREInfoHisTO.getLastChangeTime());
        cbsCustNREInfoHisE.setRecordCreaterID(cbsCustNREInfoHisTO.getRecordCreaterID());

        cbsCustNREInfoHisE.setTxnid(cbsCustNREInfoHisTO.getTxnid());

        return cbsCustNREInfoHisE;
    }

    public static CBSCustNREInfoHisTO adaptToCBSCustNREInfoHisTO(CbsCustNreinfoHis cbsCustNREInfoHis) {
        CBSCustNREInfoHisTO cbsCustNREInfoHisTo = new CBSCustNREInfoHisTO();

        cbsCustNREInfoHisTo.setCustomerId(cbsCustNREInfoHis.getCustomerId());
//        cbsCustNREInfoHisTo.setNationality(cbsCustNREInfoHis.getNationality());
        cbsCustNREInfoHisTo.setNonResidentDate(cbsCustNREInfoHis.getNonResidentDate());

        cbsCustNREInfoHisTo.setCountryCode(cbsCustNREInfoHis.getCountryCode());
//        cbsCustNREInfoHisTo.setCountryType(cbsCustNREInfoHis.getCountryType());
        cbsCustNREInfoHisTo.setLocalContPersonCode(cbsCustNREInfoHis.getLocalContPersonCode());

//        cbsCustNREInfoHisTo.setLocalPersonTitle(cbsCustNREInfoHis.getLocalPersonTitle());
//        cbsCustNREInfoHisTo.setLocalContPersonName(cbsCustNREInfoHis.getLocalContPersonName());
        cbsCustNREInfoHisTo.setLocalAddress1(cbsCustNREInfoHis.getLocalAddress1());

        cbsCustNREInfoHisTo.setLocalAddress2(cbsCustNREInfoHis.getLocalAddress2());
        cbsCustNREInfoHisTo.setVillage(cbsCustNREInfoHis.getVillage());
//        cbsCustNREInfoHisTo.setBlock(cbsCustNREInfoHis.getBlock());

        cbsCustNREInfoHisTo.setCityCode(cbsCustNREInfoHis.getCityCode());
        cbsCustNREInfoHisTo.setStateCode(cbsCustNREInfoHis.getStateCode());
        cbsCustNREInfoHisTo.setPostalCode(cbsCustNREInfoHis.getPostalCode());

        cbsCustNREInfoHisTo.setPhoneNumber(cbsCustNREInfoHis.getPhoneNumber());
        cbsCustNREInfoHisTo.setMobileNumber(cbsCustNREInfoHis.getMobileNumber());
        cbsCustNREInfoHisTo.setNreEmail(cbsCustNREInfoHis.getNreEmail());
        cbsCustNREInfoHisTo.setNonResidentEndDate(cbsCustNREInfoHis.getNonResidentEndDate());

        cbsCustNREInfoHisTo.setLastChangeUserID(cbsCustNREInfoHis.getLastChangeUserID());
        cbsCustNREInfoHisTo.setLastChangeTime(cbsCustNREInfoHis.getLastChangeTime());
        cbsCustNREInfoHisTo.setRecordCreaterID(cbsCustNREInfoHis.getRecordCreaterID());

        cbsCustNREInfoHisTo.setTxnid(cbsCustNREInfoHis.getTxnid());

        return cbsCustNREInfoHisTo;
    }

    public static CbsCustNreinfo adaptToCBSCustNREInfoEntity(CBSCustNREInfoTO cbsCustNREInfoTO) {
        CbsCustNreinfo cbsCustNREInfoE = new CbsCustNreinfo();

        cbsCustNREInfoE.setCustomerId(cbsCustNREInfoTO.getCustomerId());
//        cbsCustNREInfoE.setNationality(cbsCustNREInfoTO.getNationality());
        cbsCustNREInfoE.setNonResidentDate(cbsCustNREInfoTO.getNonResidentDate());

        cbsCustNREInfoE.setCountryCode(cbsCustNREInfoTO.getCountryCode());
//        cbsCustNREInfoE.setCountryType(cbsCustNREInfoTO.getCountryType());
        cbsCustNREInfoE.setLocalContPersonCode(cbsCustNREInfoTO.getLocalContPersonCode());

//        cbsCustNREInfoE.setLocalPersonTitle(cbsCustNREInfoTO.getLocalPersonTitle());
//        cbsCustNREInfoE.setLocalContPersonName(cbsCustNREInfoTO.getLocalContPersonName());
        cbsCustNREInfoE.setLocalAddress1(cbsCustNREInfoTO.getLocalAddress1());

        cbsCustNREInfoE.setLocalAddress2(cbsCustNREInfoTO.getLocalAddress2());
        cbsCustNREInfoE.setVillage(cbsCustNREInfoTO.getVillage());
//        cbsCustNREInfoE.setBlock(cbsCustNREInfoTO.getBlock());

        cbsCustNREInfoE.setCityCode(cbsCustNREInfoTO.getCityCode());
        cbsCustNREInfoE.setStateCode(cbsCustNREInfoTO.getStateCode());
        cbsCustNREInfoE.setPostalCode(cbsCustNREInfoTO.getPostalCode());

        cbsCustNREInfoE.setPhoneNumber(cbsCustNREInfoTO.getPhoneNumber());
        cbsCustNREInfoE.setMobileNumber(cbsCustNREInfoTO.getMobileNumber());
        cbsCustNREInfoE.setNreEmail(cbsCustNREInfoTO.getNreEmial());
        cbsCustNREInfoE.setNonResidentEndDate(cbsCustNREInfoTO.getNonResidentEndDate());

        cbsCustNREInfoE.setLastChangeUserID(cbsCustNREInfoTO.getLastChangeUserID());
        cbsCustNREInfoE.setLastChangeTime(cbsCustNREInfoTO.getLastChangeTime());
        cbsCustNREInfoE.setRecordCreaterID(cbsCustNREInfoTO.getRecordCreaterID());

        cbsCustNREInfoE.setCreationTime(cbsCustNREInfoTO.getCreationTime());
        cbsCustNREInfoE.setTsCnt(cbsCustNREInfoTO.getTsCnt());

        return cbsCustNREInfoE;
    }

    public static CBSCustNREInfoTO adaptToCBSCustNREInfoTO(CbsCustNreinfo cbsCustNREInfo) {
        CBSCustNREInfoTO cbsCustNREInfoTo = new CBSCustNREInfoTO();

        cbsCustNREInfoTo.setCustomerId(cbsCustNREInfo.getCustomerId());
//        cbsCustNREInfoTo.setNationality(cbsCustNREInfo.getNationality());
        cbsCustNREInfoTo.setNonResidentDate(cbsCustNREInfo.getNonResidentDate());

        cbsCustNREInfoTo.setCountryCode(cbsCustNREInfo.getCountryCode());
//        cbsCustNREInfoTo.setCountryType(cbsCustNREInfo.getCountryType());
        cbsCustNREInfoTo.setLocalContPersonCode(cbsCustNREInfo.getLocalContPersonCode());

//        cbsCustNREInfoTo.setLocalPersonTitle(cbsCustNREInfo.getLocalPersonTitle());
//        cbsCustNREInfoTo.setLocalContPersonName(cbsCustNREInfo.getLocalContPersonName());
        cbsCustNREInfoTo.setLocalAddress1(cbsCustNREInfo.getLocalAddress1());

        cbsCustNREInfoTo.setLocalAddress2(cbsCustNREInfo.getLocalAddress2());
        cbsCustNREInfoTo.setVillage(cbsCustNREInfo.getVillage());
//        cbsCustNREInfoTo.setBlock(cbsCustNREInfo.getBlock());

        cbsCustNREInfoTo.setCityCode(cbsCustNREInfo.getCityCode());
        cbsCustNREInfoTo.setStateCode(cbsCustNREInfo.getStateCode());
        cbsCustNREInfoTo.setPostalCode(cbsCustNREInfo.getPostalCode());

        cbsCustNREInfoTo.setPhoneNumber(cbsCustNREInfo.getPhoneNumber());
        cbsCustNREInfoTo.setMobileNumber(cbsCustNREInfo.getMobileNumber());
        cbsCustNREInfoTo.setNreEmial(cbsCustNREInfo.getNreEmail());
        cbsCustNREInfoTo.setNonResidentEndDate(cbsCustNREInfo.getNonResidentEndDate());

        cbsCustNREInfoTo.setLastChangeUserID(cbsCustNREInfo.getLastChangeUserID());
        cbsCustNREInfoTo.setLastChangeTime(cbsCustNREInfo.getLastChangeTime());
        cbsCustNREInfoTo.setRecordCreaterID(cbsCustNREInfo.getRecordCreaterID());

        cbsCustNREInfoTo.setCreationTime(cbsCustNREInfo.getCreationTime());
        cbsCustNREInfoTo.setTsCnt(cbsCustNREInfo.getTsCnt());

        return cbsCustNREInfoTo;
    }

    public static CbsCustomerMasterDetailHis adaptToCBSCustomerMasterDtlHisEntity(CBSCustomerMasterDetailHisTO cbsCustMastDtlHisTO) {
        CbsCustomerMasterDetailHis cbsCustMastDtlHisE = new CbsCustomerMasterDetailHis();

        cbsCustMastDtlHisE.setCustomerid(cbsCustMastDtlHisTO.getCustomerid());
        cbsCustMastDtlHisE.setTitle(cbsCustMastDtlHisTO.getTitle());
        cbsCustMastDtlHisE.setCustname(cbsCustMastDtlHisTO.getCustname());
        cbsCustMastDtlHisE.setCustFullName(cbsCustMastDtlHisTO.getCustFullName());

        cbsCustMastDtlHisE.setShortname(cbsCustMastDtlHisTO.getShortname());
        cbsCustMastDtlHisE.setGender(cbsCustMastDtlHisTO.getGender());
        cbsCustMastDtlHisE.setMaritalstatus(cbsCustMastDtlHisTO.getMaritalstatus());

        cbsCustMastDtlHisE.setFathername(cbsCustMastDtlHisTO.getFathername());
        cbsCustMastDtlHisE.setMothername(cbsCustMastDtlHisTO.getMothername());
        cbsCustMastDtlHisE.setStaffflag(cbsCustMastDtlHisTO.getStaffflag());

        cbsCustMastDtlHisE.setStaffid(cbsCustMastDtlHisTO.getStaffid());
        cbsCustMastDtlHisE.setMinorflag(cbsCustMastDtlHisTO.getMinorflag());
        cbsCustMastDtlHisE.setDateOfBirth(cbsCustMastDtlHisTO.getDateOfBirth());

        cbsCustMastDtlHisE.setNriflag(cbsCustMastDtlHisTO.getNriflag());
        cbsCustMastDtlHisE.setUINCardNo(cbsCustMastDtlHisTO.getuINCardNo());
        cbsCustMastDtlHisE.setCommunicationPreference(cbsCustMastDtlHisTO.getCommunicationPreference());

        cbsCustMastDtlHisE.setEmployerid(cbsCustMastDtlHisTO.getEmployerid());
        cbsCustMastDtlHisE.setEmployeeNo(cbsCustMastDtlHisTO.getEmployeeNo());
        cbsCustMastDtlHisE.setMobilenumber(cbsCustMastDtlHisTO.getMobilenumber());

        cbsCustMastDtlHisE.setCustStatus(cbsCustMastDtlHisTO.getCustStatus());
        cbsCustMastDtlHisE.setPassportNo(cbsCustMastDtlHisTO.getPassportNo());
        cbsCustMastDtlHisE.setIssueDate(cbsCustMastDtlHisTO.getIssueDate());

        cbsCustMastDtlHisE.setIssuingAuthority(cbsCustMastDtlHisTO.getIssuingAuthority());
        cbsCustMastDtlHisE.setExpirydate(cbsCustMastDtlHisTO.getExpirydate());
        cbsCustMastDtlHisE.setPlaceOfIssue(cbsCustMastDtlHisTO.getPlaceOfIssue());

        cbsCustMastDtlHisE.setPreferredLanguage(cbsCustMastDtlHisTO.getPreferredLanguage());
        cbsCustMastDtlHisE.setNameInPreferredLanguage(cbsCustMastDtlHisTO.getNameInPreferredLanguage());
        cbsCustMastDtlHisE.setChgTurnOver(cbsCustMastDtlHisTO.getChgTurnOver());

        cbsCustMastDtlHisE.setPurgeAllowed(cbsCustMastDtlHisTO.getPurgeAllowed());
        cbsCustMastDtlHisE.setAccountManager(cbsCustMastDtlHisTO.getAccountManager());
        cbsCustMastDtlHisE.setAllowSweeps(cbsCustMastDtlHisTO.getAllowSweeps());

        cbsCustMastDtlHisE.setTradeFinanceFlag(cbsCustMastDtlHisTO.getTradeFinanceFlag());
        cbsCustMastDtlHisE.setSwiftCodeStatus(cbsCustMastDtlHisTO.getSwiftCodeStatus());
        cbsCustMastDtlHisE.setSwiftCode(cbsCustMastDtlHisTO.getSwiftCode());

        cbsCustMastDtlHisE.setBcbfid(cbsCustMastDtlHisTO.getBcbfid());
        cbsCustMastDtlHisE.setCombinedStmtFlag(cbsCustMastDtlHisTO.getCombinedStmtFlag());
        cbsCustMastDtlHisE.setStmtFreqType(cbsCustMastDtlHisTO.getStmtFreqType());

        cbsCustMastDtlHisE.setStmtFreqWeekNo(cbsCustMastDtlHisTO.getStmtFreqWeekNo());
        cbsCustMastDtlHisE.setStmtFreqWeekDay(cbsCustMastDtlHisTO.getStmtFreqWeekDay());
        cbsCustMastDtlHisE.setStmtFreqStartDate(cbsCustMastDtlHisTO.getStmtFreqStartDate());

        cbsCustMastDtlHisE.setStmtFreqNP(cbsCustMastDtlHisTO.getStmtFreqNP());
        cbsCustMastDtlHisE.setIntroCustomerId(cbsCustMastDtlHisTO.getIntroCustomerId());
        cbsCustMastDtlHisE.setCustTitle(cbsCustMastDtlHisTO.getCustTitle());

        cbsCustMastDtlHisE.setName(cbsCustMastDtlHisTO.getName());
        cbsCustMastDtlHisE.setAddressLine1(cbsCustMastDtlHisTO.getAddressLine1());
        cbsCustMastDtlHisE.setAddressLine2(cbsCustMastDtlHisTO.getAddressLine2());

        cbsCustMastDtlHisE.setVillage(cbsCustMastDtlHisTO.getVillage());
        cbsCustMastDtlHisE.setBlock(cbsCustMastDtlHisTO.getBlock());
        cbsCustMastDtlHisE.setCityCode(cbsCustMastDtlHisTO.getCityCode());

        cbsCustMastDtlHisE.setStateCode(cbsCustMastDtlHisTO.getStateCode());
        cbsCustMastDtlHisE.setPostalCode(cbsCustMastDtlHisTO.getPostalCode());
        cbsCustMastDtlHisE.setCountryCode(cbsCustMastDtlHisTO.getCountryCode());

        cbsCustMastDtlHisE.setPhoneNumber(cbsCustMastDtlHisTO.getPhoneNumber());
        cbsCustMastDtlHisE.setTelexNumber(cbsCustMastDtlHisTO.getTelexNumber());
        cbsCustMastDtlHisE.setFaxNumber(cbsCustMastDtlHisTO.getFaxNumber());

        cbsCustMastDtlHisE.setSalary(cbsCustMastDtlHisTO.getSalary());
        cbsCustMastDtlHisE.setChargeStatus(cbsCustMastDtlHisTO.getChargeStatus());
        cbsCustMastDtlHisE.setChargeLevelCode(cbsCustMastDtlHisTO.getChargeLevelCode());

        cbsCustMastDtlHisE.setABBChargeCode(cbsCustMastDtlHisTO.getaBBChargeCode());
        cbsCustMastDtlHisE.setEPSChargeCode(cbsCustMastDtlHisTO.getePSChargeCode());
        cbsCustMastDtlHisE.setPaperRemittance(cbsCustMastDtlHisTO.getPaperRemittance());

        cbsCustMastDtlHisE.setDeliveryChannelChargeCode(cbsCustMastDtlHisTO.getDeliveryChannelChargeCode());
        cbsCustMastDtlHisE.setAccountLevelCharges(cbsCustMastDtlHisTO.getAccountLevelCharges());
        cbsCustMastDtlHisE.setCustLevelCharges(cbsCustMastDtlHisTO.getCustLevelCharges());

        cbsCustMastDtlHisE.setTDSExemptionEndDate(cbsCustMastDtlHisTO.gettDSExemptionEndDate());
        cbsCustMastDtlHisE.setTaxSlab(cbsCustMastDtlHisTO.getTaxSlab());
        cbsCustMastDtlHisE.setTDSCode(cbsCustMastDtlHisTO.gettDSCode());

        cbsCustMastDtlHisE.setTDSCustomerId(cbsCustMastDtlHisTO.gettDSCustomerId());
        cbsCustMastDtlHisE.setTDSFormReceiveDate(cbsCustMastDtlHisTO.gettDSFormReceiveDate());
        cbsCustMastDtlHisE.setTDSExemptionReferenceNo(cbsCustMastDtlHisTO.gettDSExemptionReferenceNo());

        cbsCustMastDtlHisE.setExemptionRemarks(cbsCustMastDtlHisTO.getExemptionRemarks());
        cbsCustMastDtlHisE.setITFileNo(cbsCustMastDtlHisTO.getiTFileNo());
        cbsCustMastDtlHisE.setTDSFloorLimit(cbsCustMastDtlHisTO.gettDSFloorLimit());

        cbsCustMastDtlHisE.setCustFinancialDetails(cbsCustMastDtlHisTO.getCustFinancialDetails());
        cbsCustMastDtlHisE.setFinancialYearAndMonth(cbsCustMastDtlHisTO.getFinancialYearAndMonth());
        cbsCustMastDtlHisE.setCurrencyCodeType(cbsCustMastDtlHisTO.getCurrencyCodeType());

        cbsCustMastDtlHisE.setPropertyAssets(cbsCustMastDtlHisTO.getPropertyAssets());
        cbsCustMastDtlHisE.setBusinessAssets(cbsCustMastDtlHisTO.getBusinessAssets());
        cbsCustMastDtlHisE.setInvestments(cbsCustMastDtlHisTO.getInvestments());

        cbsCustMastDtlHisE.setNetworth(cbsCustMastDtlHisTO.getNetworth());
        cbsCustMastDtlHisE.setDeposits(cbsCustMastDtlHisTO.getDeposits());
        cbsCustMastDtlHisE.setOtherBankCode(cbsCustMastDtlHisTO.getOtherBankCode());

        cbsCustMastDtlHisE.setLimitsWithOtherBank(cbsCustMastDtlHisTO.getLimitsWithOtherBank());
        cbsCustMastDtlHisE.setFundBasedLimit(cbsCustMastDtlHisTO.getFundBasedLimit());
        cbsCustMastDtlHisE.setNonFundBasedLimit(cbsCustMastDtlHisTO.getNonFundBasedLimit());

        cbsCustMastDtlHisE.setOffLineCustDebitLimit(cbsCustMastDtlHisTO.getOffLineCustDebitLimit());
        cbsCustMastDtlHisE.setCustSalary(cbsCustMastDtlHisTO.getCustSalary());
        cbsCustMastDtlHisE.setCustFinancialDate(cbsCustMastDtlHisTO.getCustFinancialDate());

        cbsCustMastDtlHisE.setPANGIRNumber(cbsCustMastDtlHisTO.getpANGIRNumber());
        cbsCustMastDtlHisE.setTINNumber(cbsCustMastDtlHisTO.gettINNumber());
        cbsCustMastDtlHisE.setSalesTaxNo(cbsCustMastDtlHisTO.getSalesTaxNo());

        cbsCustMastDtlHisE.setExciseNo(cbsCustMastDtlHisTO.getExciseNo());
        cbsCustMastDtlHisE.setVoterIDNo(cbsCustMastDtlHisTO.getVoterIDNo());
        cbsCustMastDtlHisE.setDrivingLicenseNo(cbsCustMastDtlHisTO.getDrivingLicenseNo());

        cbsCustMastDtlHisE.setCreditCard(cbsCustMastDtlHisTO.getCreditCard());
        cbsCustMastDtlHisE.setCardNumber(cbsCustMastDtlHisTO.getCardNumber());
        cbsCustMastDtlHisE.setCardIssuer(cbsCustMastDtlHisTO.getCardIssuer());

        cbsCustMastDtlHisE.setBankName(cbsCustMastDtlHisTO.getBankName());
        cbsCustMastDtlHisE.setAcctId(cbsCustMastDtlHisTO.getAcctId());
        cbsCustMastDtlHisE.setBranchName(cbsCustMastDtlHisTO.getBranchName());

        cbsCustMastDtlHisE.setPerAddressLine1(cbsCustMastDtlHisTO.getPerAddressLine1());
        cbsCustMastDtlHisE.setPerAddressLine2(cbsCustMastDtlHisTO.getPerAddressLine2());
        cbsCustMastDtlHisE.setPerVillage(cbsCustMastDtlHisTO.getPerVillage());

        cbsCustMastDtlHisE.setPerBlock(cbsCustMastDtlHisTO.getPerBlock());
        cbsCustMastDtlHisE.setPerCityCode(cbsCustMastDtlHisTO.getPerCityCode());
        cbsCustMastDtlHisE.setPerStateCode(cbsCustMastDtlHisTO.getPerStateCode());

        cbsCustMastDtlHisE.setPerPostalCode(cbsCustMastDtlHisTO.getPerPostalCode());
        cbsCustMastDtlHisE.setPerCountryCode(cbsCustMastDtlHisTO.getPerCountryCode());
        cbsCustMastDtlHisE.setPerPhoneNumber(cbsCustMastDtlHisTO.getPerPhoneNumber());

        cbsCustMastDtlHisE.setPerTelexNumber(cbsCustMastDtlHisTO.getPerTelexNumber());
        cbsCustMastDtlHisE.setPerFaxNumber(cbsCustMastDtlHisTO.getPerFaxNumber());
        cbsCustMastDtlHisE.setMailAddressLine1(cbsCustMastDtlHisTO.getMailAddressLine1());

        cbsCustMastDtlHisE.setMailAddressLine2(cbsCustMastDtlHisTO.getMailAddressLine2());
        cbsCustMastDtlHisE.setMailVillage(cbsCustMastDtlHisTO.getMailVillage());
        cbsCustMastDtlHisE.setMailBlock(cbsCustMastDtlHisTO.getMailBlock());

        cbsCustMastDtlHisE.setMailCityCode(cbsCustMastDtlHisTO.getMailCityCode());
        cbsCustMastDtlHisE.setMailStateCode(cbsCustMastDtlHisTO.getMailStateCode());
        cbsCustMastDtlHisE.setMailPostalCode(cbsCustMastDtlHisTO.getMailPostalCode());

        cbsCustMastDtlHisE.setMailCountryCode(cbsCustMastDtlHisTO.getMailCountryCode());
        cbsCustMastDtlHisE.setMailPhoneNumber(cbsCustMastDtlHisTO.getMailPhoneNumber());
        cbsCustMastDtlHisE.setMailTelexNumber(cbsCustMastDtlHisTO.getMailTelexNumber());

        cbsCustMastDtlHisE.setMailFaxNumber(cbsCustMastDtlHisTO.getMailFaxNumber());
        cbsCustMastDtlHisE.setEmpAddressLine1(cbsCustMastDtlHisTO.getEmpAddressLine1());
        cbsCustMastDtlHisE.setEmpAddressLine2(cbsCustMastDtlHisTO.getEmpAddressLine2());

        cbsCustMastDtlHisE.setEmpVillage(cbsCustMastDtlHisTO.getEmpVillage());
        cbsCustMastDtlHisE.setEmpBlock(cbsCustMastDtlHisTO.getEmpBlock());
        cbsCustMastDtlHisE.setEmpCityCode(cbsCustMastDtlHisTO.getEmpCityCode());

        cbsCustMastDtlHisE.setEmpStateCode(cbsCustMastDtlHisTO.getEmpStateCode());
        cbsCustMastDtlHisE.setEmpPostalCode(cbsCustMastDtlHisTO.getEmpPostalCode());
        cbsCustMastDtlHisE.setEmpCountryCode(cbsCustMastDtlHisTO.getEmpCountryCode());

        cbsCustMastDtlHisE.setEmpPhoneNumber(cbsCustMastDtlHisTO.getEmpPhoneNumber());
        cbsCustMastDtlHisE.setTelexNumber(cbsCustMastDtlHisTO.getTelexNumber());
        cbsCustMastDtlHisE.setEmpFaxNumber(cbsCustMastDtlHisTO.getEmpFaxNumber());

        cbsCustMastDtlHisE.setEmailID(cbsCustMastDtlHisTO.getEmailID());
        cbsCustMastDtlHisE.setOperationalRiskRating(cbsCustMastDtlHisTO.getOperationalRiskRating());
        cbsCustMastDtlHisE.setRatingAsOn(cbsCustMastDtlHisTO.getRatingAsOn());

        cbsCustMastDtlHisE.setCreditRiskRatingInternal(cbsCustMastDtlHisTO.getCreditRiskRatingInternal());
        cbsCustMastDtlHisE.setCreditRatingAsOn(cbsCustMastDtlHisTO.getCreditRatingAsOn());
        cbsCustMastDtlHisE.setExternalRatingShortTerm(cbsCustMastDtlHisTO.getExternalRatingShortTerm());

        cbsCustMastDtlHisE.setExternShortRatingAsOn(cbsCustMastDtlHisTO.getExternShortRatingAsOn());
        cbsCustMastDtlHisE.setExternalRatingLongTerm(cbsCustMastDtlHisTO.getExternalRatingLongTerm());
        cbsCustMastDtlHisE.setExternLongRatingAsOn(cbsCustMastDtlHisTO.getExternLongRatingAsOn());

        cbsCustMastDtlHisE.setCustAcquistionDate(cbsCustMastDtlHisTO.getCustAcquistionDate());
        cbsCustMastDtlHisE.setThresoldTransactionLimit(cbsCustMastDtlHisTO.getThresoldTransactionLimit());
        cbsCustMastDtlHisE.setThresoldLimitUpdationDate(cbsCustMastDtlHisTO.getThresoldLimitUpdationDate());

        cbsCustMastDtlHisE.setCustUpdationDate(cbsCustMastDtlHisTO.getCustUpdationDate());
        cbsCustMastDtlHisE.setSuspensionFlg(cbsCustMastDtlHisTO.getSuspensionFlg());
        cbsCustMastDtlHisE.setPrimaryBrCode(cbsCustMastDtlHisTO.getPrimaryBrCode());

        cbsCustMastDtlHisE.setLastUpdatedBr(cbsCustMastDtlHisTO.getLastUpdatedBr());
        cbsCustMastDtlHisE.setFirstAccountDate(cbsCustMastDtlHisTO.getFirstAccountDate());
        cbsCustMastDtlHisE.setAuth(cbsCustMastDtlHisTO.getAuth());

        cbsCustMastDtlHisE.setLastChangeUserID(cbsCustMastDtlHisTO.getLastChangeUserID());
        cbsCustMastDtlHisE.setLastChangeTime(cbsCustMastDtlHisTO.getLastChangeTime());
        cbsCustMastDtlHisE.setRecordCreaterID(cbsCustMastDtlHisTO.getRecordCreaterID());

        cbsCustMastDtlHisE.setTxnid(cbsCustMastDtlHisTO.getTxnid());
        cbsCustMastDtlHisE.setAadhaarNo(cbsCustMastDtlHisTO.getAdhaarNo());
        cbsCustMastDtlHisE.setLpgId(cbsCustMastDtlHisTO.getLpgId());

        cbsCustMastDtlHisE.setAadhaarLpgAcno(cbsCustMastDtlHisTO.getAdhaarLpgAcno());
        cbsCustMastDtlHisE.setMandateFlag(cbsCustMastDtlHisTO.getMandateFlag());
        cbsCustMastDtlHisE.setMandateDate(cbsCustMastDtlHisTO.getMandateDt());
        //add by rahul
        cbsCustMastDtlHisE.setRegType(cbsCustMastDtlHisTO.getRegType());
        cbsCustMastDtlHisE.setAadhaarMode(cbsCustMastDtlHisTO.getAadhaarMode() == null ? "" : cbsCustMastDtlHisTO.getAadhaarMode());
        cbsCustMastDtlHisE.setAadhaarBankIin(cbsCustMastDtlHisTO.getAadhaarBankIin() == null ? "" : cbsCustMastDtlHisTO.getAadhaarBankIin());
        //
        cbsCustMastDtlHisE.setMiddleName(cbsCustMastDtlHisTO.getMiddleName());
        cbsCustMastDtlHisE.setLastName(cbsCustMastDtlHisTO.getLastName());

        cbsCustMastDtlHisE.setSpouseName(cbsCustMastDtlHisTO.getSpouseName());
        cbsCustMastDtlHisE.setMaidenName(cbsCustMastDtlHisTO.getMaidenName());
        cbsCustMastDtlHisE.setNregaJobCard(cbsCustMastDtlHisTO.getNregaJobCard());

        cbsCustMastDtlHisE.setDlExpiry(cbsCustMastDtlHisTO.getDlExpiry());
        cbsCustMastDtlHisE.setLegalDocument(cbsCustMastDtlHisTO.getLegalDocument());
        cbsCustMastDtlHisE.setIncomeRange(cbsCustMastDtlHisTO.getIncomeRange());

        cbsCustMastDtlHisE.setNetworthAsOn(cbsCustMastDtlHisTO.getNetworthAsOn());
        cbsCustMastDtlHisE.setQualification(cbsCustMastDtlHisTO.getQualification());
        cbsCustMastDtlHisE.setPoliticalExposed(cbsCustMastDtlHisTO.getPoliticalExposed());

        cbsCustMastDtlHisE.setJuriAdd1(cbsCustMastDtlHisTO.getJuriAdd1());
        cbsCustMastDtlHisE.setJuriAdd2(cbsCustMastDtlHisTO.getJuriAdd2());
        cbsCustMastDtlHisE.setJuriCity(cbsCustMastDtlHisTO.getJuriCity());

        cbsCustMastDtlHisE.setJuriState(cbsCustMastDtlHisTO.getJuriState());
        cbsCustMastDtlHisE.setJuriPostal(cbsCustMastDtlHisTO.getJuriPostal());
        cbsCustMastDtlHisE.setJuriCountry(cbsCustMastDtlHisTO.getJuriCountry());

        cbsCustMastDtlHisE.setTan(cbsCustMastDtlHisTO.getTan());
        cbsCustMastDtlHisE.setCin(cbsCustMastDtlHisTO.getCin());
        cbsCustMastDtlHisE.setPerEmail(cbsCustMastDtlHisTO.getPerEmail());
        cbsCustMastDtlHisE.setMailEmail(cbsCustMastDtlHisTO.getMailEmail());
        cbsCustMastDtlHisE.setNationality(cbsCustMastDtlHisTO.getNationality());

        cbsCustMastDtlHisE.setOtherIdentity(cbsCustMastDtlHisTO.getOtherIdentity());
        cbsCustMastDtlHisE.setPoa(cbsCustMastDtlHisTO.getPoa());
        cbsCustMastDtlHisE.setIsdCode(cbsCustMastDtlHisTO.getIsdCode());
        cbsCustMastDtlHisE.setJuriDistrict(cbsCustMastDtlHisTO.getJuriDistrict());

        cbsCustMastDtlHisE.setFatherSpouseFlag(cbsCustMastDtlHisTO.getFatherSpouseFlag());
        cbsCustMastDtlHisE.setCustImage(cbsCustMastDtlHisTO.getCustImage());
        return cbsCustMastDtlHisE;
    }

    public static CBSCustomerMasterDetailHisTO adaptToCBSCustomerMasterDtlHisTO(CbsCustomerMasterDetailHis cbsCustMastDtlHis) {
        CBSCustomerMasterDetailHisTO cbsCustMastDtlHisTo = new CBSCustomerMasterDetailHisTO();

        cbsCustMastDtlHisTo.setCustomerid(cbsCustMastDtlHis.getCustomerid());
        cbsCustMastDtlHisTo.setTitle(cbsCustMastDtlHis.getTitle());
        cbsCustMastDtlHisTo.setCustname(cbsCustMastDtlHis.getCustname());
        cbsCustMastDtlHisTo.setCustFullName(cbsCustMastDtlHis.getCustFullName());

        cbsCustMastDtlHisTo.setShortname(cbsCustMastDtlHis.getShortname());
        cbsCustMastDtlHisTo.setGender(cbsCustMastDtlHis.getGender());
        cbsCustMastDtlHisTo.setMaritalstatus(cbsCustMastDtlHis.getMaritalstatus());

        cbsCustMastDtlHisTo.setFathername(cbsCustMastDtlHis.getFathername());
        cbsCustMastDtlHisTo.setMothername(cbsCustMastDtlHis.getMothername());
        cbsCustMastDtlHisTo.setStaffflag(cbsCustMastDtlHis.getStaffflag());

        cbsCustMastDtlHisTo.setStaffid(cbsCustMastDtlHis.getStaffid());
        cbsCustMastDtlHisTo.setMinorflag(cbsCustMastDtlHis.getMinorflag());
        cbsCustMastDtlHisTo.setDateOfBirth(cbsCustMastDtlHis.getDateOfBirth());

        cbsCustMastDtlHisTo.setNriflag(cbsCustMastDtlHis.getNriflag());
        cbsCustMastDtlHisTo.setuINCardNo(cbsCustMastDtlHis.getUINCardNo());
        cbsCustMastDtlHisTo.setCommunicationPreference(cbsCustMastDtlHis.getCommunicationPreference());

        cbsCustMastDtlHisTo.setEmployerid(cbsCustMastDtlHis.getEmployerid());
        cbsCustMastDtlHisTo.setEmployeeNo(cbsCustMastDtlHis.getEmployeeNo());
        cbsCustMastDtlHisTo.setMobilenumber(cbsCustMastDtlHis.getMobilenumber());

        cbsCustMastDtlHisTo.setCustStatus(cbsCustMastDtlHis.getCustStatus());
        cbsCustMastDtlHisTo.setPassportNo(cbsCustMastDtlHis.getPassportNo());
        cbsCustMastDtlHisTo.setIssueDate(cbsCustMastDtlHis.getIssueDate());

        cbsCustMastDtlHisTo.setIssuingAuthority(cbsCustMastDtlHis.getIssuingAuthority());
        cbsCustMastDtlHisTo.setExpirydate(cbsCustMastDtlHis.getExpirydate());
        cbsCustMastDtlHisTo.setPlaceOfIssue(cbsCustMastDtlHis.getPlaceOfIssue());

        cbsCustMastDtlHisTo.setPreferredLanguage(cbsCustMastDtlHis.getPreferredLanguage());
        cbsCustMastDtlHisTo.setNameInPreferredLanguage(cbsCustMastDtlHis.getNameInPreferredLanguage());
        cbsCustMastDtlHisTo.setChgTurnOver(cbsCustMastDtlHis.getChgTurnOver());

        cbsCustMastDtlHisTo.setPurgeAllowed(cbsCustMastDtlHis.getPurgeAllowed());
        cbsCustMastDtlHisTo.setAccountManager(cbsCustMastDtlHis.getAccountManager());
        cbsCustMastDtlHisTo.setAllowSweeps(cbsCustMastDtlHis.getAllowSweeps());

        cbsCustMastDtlHisTo.setTradeFinanceFlag(cbsCustMastDtlHis.getTradeFinanceFlag());
        cbsCustMastDtlHisTo.setSwiftCodeStatus(cbsCustMastDtlHis.getSwiftCodeStatus());
        cbsCustMastDtlHisTo.setSwiftCode(cbsCustMastDtlHis.getSwiftCode());

        cbsCustMastDtlHisTo.setBcbfid(cbsCustMastDtlHis.getBcbfid());
        cbsCustMastDtlHisTo.setCombinedStmtFlag(cbsCustMastDtlHis.getCombinedStmtFlag());
        cbsCustMastDtlHisTo.setStmtFreqType(cbsCustMastDtlHis.getStmtFreqType());

        cbsCustMastDtlHisTo.setStmtFreqWeekNo(cbsCustMastDtlHis.getStmtFreqWeekNo());
        cbsCustMastDtlHisTo.setStmtFreqWeekDay(cbsCustMastDtlHis.getStmtFreqWeekDay());
        cbsCustMastDtlHisTo.setStmtFreqStartDate(cbsCustMastDtlHis.getStmtFreqStartDate());

        cbsCustMastDtlHisTo.setStmtFreqNP(cbsCustMastDtlHis.getStmtFreqNP());
        cbsCustMastDtlHisTo.setIntroCustomerId(cbsCustMastDtlHis.getIntroCustomerId());
        cbsCustMastDtlHisTo.setCustTitle(cbsCustMastDtlHis.getCustTitle());

        cbsCustMastDtlHisTo.setName(cbsCustMastDtlHis.getName());
        cbsCustMastDtlHisTo.setAddressLine1(cbsCustMastDtlHis.getAddressLine1());
        cbsCustMastDtlHisTo.setAddressLine2(cbsCustMastDtlHis.getAddressLine2());

        cbsCustMastDtlHisTo.setVillage(cbsCustMastDtlHis.getVillage());
        cbsCustMastDtlHisTo.setBlock(cbsCustMastDtlHis.getBlock());
        cbsCustMastDtlHisTo.setCityCode(cbsCustMastDtlHis.getCityCode());

        cbsCustMastDtlHisTo.setStateCode(cbsCustMastDtlHis.getStateCode());
        cbsCustMastDtlHisTo.setPostalCode(cbsCustMastDtlHis.getPostalCode());
        cbsCustMastDtlHisTo.setCountryCode(cbsCustMastDtlHis.getCountryCode());

        cbsCustMastDtlHisTo.setPhoneNumber(cbsCustMastDtlHis.getPhoneNumber());
        cbsCustMastDtlHisTo.setTelexNumber(cbsCustMastDtlHis.getTelexNumber());
        cbsCustMastDtlHisTo.setFaxNumber(cbsCustMastDtlHis.getFaxNumber());

        cbsCustMastDtlHisTo.setSalary(cbsCustMastDtlHis.getSalary());
        cbsCustMastDtlHisTo.setChargeStatus(cbsCustMastDtlHis.getChargeStatus());
        cbsCustMastDtlHisTo.setChargeLevelCode(cbsCustMastDtlHis.getChargeLevelCode());

        cbsCustMastDtlHisTo.setaBBChargeCode(cbsCustMastDtlHis.getABBChargeCode());
        cbsCustMastDtlHisTo.setePSChargeCode(cbsCustMastDtlHis.getEPSChargeCode());
        cbsCustMastDtlHisTo.setPaperRemittance(cbsCustMastDtlHis.getPaperRemittance());

        cbsCustMastDtlHisTo.setDeliveryChannelChargeCode(cbsCustMastDtlHis.getDeliveryChannelChargeCode());
        cbsCustMastDtlHisTo.setAccountLevelCharges(cbsCustMastDtlHis.getAccountLevelCharges());
        cbsCustMastDtlHisTo.setCustLevelCharges(cbsCustMastDtlHis.getCustLevelCharges());

        cbsCustMastDtlHisTo.settDSExemptionEndDate(cbsCustMastDtlHis.getTDSExemptionEndDate());
        cbsCustMastDtlHisTo.setTaxSlab(cbsCustMastDtlHis.getTaxSlab());
        cbsCustMastDtlHisTo.settDSCode(cbsCustMastDtlHis.getTDSCode());

        cbsCustMastDtlHisTo.settDSCustomerId(cbsCustMastDtlHis.getTDSCustomerId());
        cbsCustMastDtlHisTo.settDSFormReceiveDate(cbsCustMastDtlHis.getTDSFormReceiveDate());
        cbsCustMastDtlHisTo.settDSExemptionReferenceNo(cbsCustMastDtlHis.getTDSExemptionReferenceNo());

        cbsCustMastDtlHisTo.setExemptionRemarks(cbsCustMastDtlHis.getExemptionRemarks());
        cbsCustMastDtlHisTo.setiTFileNo(cbsCustMastDtlHis.getITFileNo());
        cbsCustMastDtlHisTo.settDSFloorLimit(cbsCustMastDtlHis.getTDSFloorLimit());

        cbsCustMastDtlHisTo.setCustFinancialDetails(cbsCustMastDtlHis.getCustFinancialDetails());
        cbsCustMastDtlHisTo.setFinancialYearAndMonth(cbsCustMastDtlHis.getFinancialYearAndMonth());
        cbsCustMastDtlHisTo.setCurrencyCodeType(cbsCustMastDtlHis.getCurrencyCodeType());

        cbsCustMastDtlHisTo.setPropertyAssets(cbsCustMastDtlHis.getPropertyAssets());
        cbsCustMastDtlHisTo.setBusinessAssets(cbsCustMastDtlHis.getBusinessAssets());
        cbsCustMastDtlHisTo.setInvestments(cbsCustMastDtlHis.getInvestments());

        cbsCustMastDtlHisTo.setNetworth(cbsCustMastDtlHis.getNetworth());
        cbsCustMastDtlHisTo.setDeposits(cbsCustMastDtlHis.getDeposits());
        cbsCustMastDtlHisTo.setOtherBankCode(cbsCustMastDtlHis.getOtherBankCode());

        cbsCustMastDtlHisTo.setLimitsWithOtherBank(cbsCustMastDtlHis.getLimitsWithOtherBank());
        cbsCustMastDtlHisTo.setFundBasedLimit(cbsCustMastDtlHis.getFundBasedLimit());
        cbsCustMastDtlHisTo.setNonFundBasedLimit(cbsCustMastDtlHis.getNonFundBasedLimit());

        cbsCustMastDtlHisTo.setOffLineCustDebitLimit(cbsCustMastDtlHis.getOffLineCustDebitLimit());
        cbsCustMastDtlHisTo.setCustSalary(cbsCustMastDtlHis.getCustSalary());
        cbsCustMastDtlHisTo.setCustFinancialDate(cbsCustMastDtlHis.getCustFinancialDate());

        cbsCustMastDtlHisTo.setpANGIRNumber(cbsCustMastDtlHis.getPANGIRNumber());
        cbsCustMastDtlHisTo.settINNumber(cbsCustMastDtlHis.getTINNumber());
        cbsCustMastDtlHisTo.setSalesTaxNo(cbsCustMastDtlHis.getSalesTaxNo());

        cbsCustMastDtlHisTo.setExciseNo(cbsCustMastDtlHis.getExciseNo());
        cbsCustMastDtlHisTo.setVoterIDNo(cbsCustMastDtlHis.getVoterIDNo());
        cbsCustMastDtlHisTo.setDrivingLicenseNo(cbsCustMastDtlHis.getDrivingLicenseNo());

        cbsCustMastDtlHisTo.setCreditCard(cbsCustMastDtlHis.getCreditCard());
        cbsCustMastDtlHisTo.setCardNumber(cbsCustMastDtlHis.getCardNumber());
        cbsCustMastDtlHisTo.setCardIssuer(cbsCustMastDtlHis.getCardIssuer());

        cbsCustMastDtlHisTo.setBankName(cbsCustMastDtlHis.getBankName());
        cbsCustMastDtlHisTo.setAcctId(cbsCustMastDtlHis.getAcctId());
        cbsCustMastDtlHisTo.setBranchName(cbsCustMastDtlHis.getBranchName());

        cbsCustMastDtlHisTo.setPerAddressLine1(cbsCustMastDtlHis.getPerAddressLine1());
        cbsCustMastDtlHisTo.setPerAddressLine2(cbsCustMastDtlHis.getPerAddressLine2());
        cbsCustMastDtlHisTo.setPerVillage(cbsCustMastDtlHis.getPerVillage());

        cbsCustMastDtlHisTo.setPerBlock(cbsCustMastDtlHis.getPerBlock());
        cbsCustMastDtlHisTo.setPerCityCode(cbsCustMastDtlHis.getPerCityCode());
        cbsCustMastDtlHisTo.setPerStateCode(cbsCustMastDtlHis.getPerStateCode());

        cbsCustMastDtlHisTo.setPerPostalCode(cbsCustMastDtlHis.getPerPostalCode());
        cbsCustMastDtlHisTo.setPerCountryCode(cbsCustMastDtlHis.getPerCountryCode());
        cbsCustMastDtlHisTo.setPerPhoneNumber(cbsCustMastDtlHis.getPerPhoneNumber());

        cbsCustMastDtlHisTo.setPerTelexNumber(cbsCustMastDtlHis.getPerTelexNumber());
        cbsCustMastDtlHisTo.setPerFaxNumber(cbsCustMastDtlHis.getPerFaxNumber());
        cbsCustMastDtlHisTo.setMailAddressLine1(cbsCustMastDtlHis.getMailAddressLine1());

        cbsCustMastDtlHisTo.setMailAddressLine2(cbsCustMastDtlHis.getMailAddressLine2());
        cbsCustMastDtlHisTo.setMailVillage(cbsCustMastDtlHis.getMailVillage());
        cbsCustMastDtlHisTo.setMailBlock(cbsCustMastDtlHis.getMailBlock());

        cbsCustMastDtlHisTo.setMailCityCode(cbsCustMastDtlHis.getMailCityCode());
        cbsCustMastDtlHisTo.setMailStateCode(cbsCustMastDtlHis.getMailStateCode());
        cbsCustMastDtlHisTo.setMailPostalCode(cbsCustMastDtlHis.getMailPostalCode());

        cbsCustMastDtlHisTo.setMailCountryCode(cbsCustMastDtlHis.getMailCountryCode());
        cbsCustMastDtlHisTo.setMailPhoneNumber(cbsCustMastDtlHis.getMailPhoneNumber());
        cbsCustMastDtlHisTo.setMailTelexNumber(cbsCustMastDtlHis.getMailTelexNumber());

        cbsCustMastDtlHisTo.setMailFaxNumber(cbsCustMastDtlHis.getMailFaxNumber());
        cbsCustMastDtlHisTo.setEmpAddressLine1(cbsCustMastDtlHis.getEmpAddressLine1());
        cbsCustMastDtlHisTo.setEmpAddressLine2(cbsCustMastDtlHis.getEmpAddressLine2());

        cbsCustMastDtlHisTo.setEmpVillage(cbsCustMastDtlHis.getEmpVillage());
        cbsCustMastDtlHisTo.setEmpBlock(cbsCustMastDtlHis.getEmpBlock());
        cbsCustMastDtlHisTo.setEmpCityCode(cbsCustMastDtlHis.getEmpCityCode());

        cbsCustMastDtlHisTo.setEmpStateCode(cbsCustMastDtlHis.getEmpStateCode());
        cbsCustMastDtlHisTo.setEmpPostalCode(cbsCustMastDtlHis.getEmpPostalCode());
        cbsCustMastDtlHisTo.setEmpCountryCode(cbsCustMastDtlHis.getEmpCountryCode());

        cbsCustMastDtlHisTo.setEmpPhoneNumber(cbsCustMastDtlHis.getEmpPhoneNumber());
        cbsCustMastDtlHisTo.setEmpTelexNumber(cbsCustMastDtlHis.getEmpTelexNumber());
        cbsCustMastDtlHisTo.setEmpFaxNumber(cbsCustMastDtlHis.getEmpFaxNumber());

        cbsCustMastDtlHisTo.setEmailID(cbsCustMastDtlHis.getEmailID());
        cbsCustMastDtlHisTo.setOperationalRiskRating(cbsCustMastDtlHis.getOperationalRiskRating());
        cbsCustMastDtlHisTo.setRatingAsOn(cbsCustMastDtlHis.getRatingAsOn());

        cbsCustMastDtlHisTo.setCreditRiskRatingInternal(cbsCustMastDtlHis.getCreditRiskRatingInternal());
        cbsCustMastDtlHisTo.setCreditRatingAsOn(cbsCustMastDtlHis.getCreditRatingAsOn());
        cbsCustMastDtlHisTo.setExternalRatingShortTerm(cbsCustMastDtlHis.getExternalRatingShortTerm());

        cbsCustMastDtlHisTo.setExternShortRatingAsOn(cbsCustMastDtlHis.getExternShortRatingAsOn());
        cbsCustMastDtlHisTo.setExternalRatingLongTerm(cbsCustMastDtlHis.getExternalRatingLongTerm());
        cbsCustMastDtlHisTo.setExternLongRatingAsOn(cbsCustMastDtlHis.getExternLongRatingAsOn());

        cbsCustMastDtlHisTo.setCustAcquistionDate(cbsCustMastDtlHis.getCustAcquistionDate());
        cbsCustMastDtlHisTo.setThresoldTransactionLimit(cbsCustMastDtlHis.getThresoldTransactionLimit());
        cbsCustMastDtlHisTo.setThresoldLimitUpdationDate(cbsCustMastDtlHis.getThresoldLimitUpdationDate());

        cbsCustMastDtlHisTo.setCustUpdationDate(cbsCustMastDtlHis.getCustUpdationDate());
        cbsCustMastDtlHisTo.setSuspensionFlg(cbsCustMastDtlHis.getSuspensionFlg());
        cbsCustMastDtlHisTo.setPrimaryBrCode(cbsCustMastDtlHis.getPrimaryBrCode());

        cbsCustMastDtlHisTo.setLastUpdatedBr(cbsCustMastDtlHis.getLastUpdatedBr());
        cbsCustMastDtlHisTo.setFirstAccountDate(cbsCustMastDtlHis.getFirstAccountDate());
        cbsCustMastDtlHisTo.setAuth(cbsCustMastDtlHis.getAuth());

        cbsCustMastDtlHisTo.setLastChangeUserID(cbsCustMastDtlHis.getLastChangeUserID());
        cbsCustMastDtlHisTo.setLastChangeTime(cbsCustMastDtlHis.getLastChangeTime());
        cbsCustMastDtlHisTo.setRecordCreaterID(cbsCustMastDtlHis.getRecordCreaterID());

        cbsCustMastDtlHisTo.setTxnid(cbsCustMastDtlHis.getTxnid());
        cbsCustMastDtlHisTo.setAdhaarNo(cbsCustMastDtlHis.getAadhaarNo());
        cbsCustMastDtlHisTo.setLpgId(cbsCustMastDtlHis.getLpgId());
        cbsCustMastDtlHisTo.setAdhaarLpgAcno(cbsCustMastDtlHis.getAadhaarLpgAcno());

        cbsCustMastDtlHisTo.setMandateFlag(cbsCustMastDtlHis.getMandateFlag());
        cbsCustMastDtlHisTo.setMandateDt(cbsCustMastDtlHis.getMandateDate());
        cbsCustMastDtlHisTo.setRegType(cbsCustMastDtlHis.getRegType());
        //add by rahul
        cbsCustMastDtlHisTo.setAadhaarMode(cbsCustMastDtlHis.getAadhaarMode() == null ? "" : cbsCustMastDtlHis.getAadhaarMode());
        cbsCustMastDtlHisTo.setAadhaarBankIin(cbsCustMastDtlHis.getAadhaarBankIin() == null ? "" : cbsCustMastDtlHis.getAadhaarBankIin());
        //
        cbsCustMastDtlHisTo.setMiddleName(cbsCustMastDtlHis.getMiddleName());
        cbsCustMastDtlHisTo.setLastName(cbsCustMastDtlHis.getLastName());
        cbsCustMastDtlHisTo.setSpouseName(cbsCustMastDtlHis.getSpouseName());

        cbsCustMastDtlHisTo.setMaidenName(cbsCustMastDtlHis.getMaidenName());
        cbsCustMastDtlHisTo.setNregaJobCard(cbsCustMastDtlHis.getNregaJobCard());
        cbsCustMastDtlHisTo.setDlExpiry(cbsCustMastDtlHis.getDlExpiry());

        cbsCustMastDtlHisTo.setLegalDocument(cbsCustMastDtlHis.getLegalDocument());
        cbsCustMastDtlHisTo.setIncomeRange(cbsCustMastDtlHis.getIncomeRange());
        cbsCustMastDtlHisTo.setNetworthAsOn(cbsCustMastDtlHis.getNetworthAsOn());

        cbsCustMastDtlHisTo.setQualification(cbsCustMastDtlHis.getQualification());
        cbsCustMastDtlHisTo.setPoliticalExposed(cbsCustMastDtlHis.getPoliticalExposed());
        cbsCustMastDtlHisTo.setJuriAdd1(cbsCustMastDtlHis.getJuriAdd1());

        cbsCustMastDtlHisTo.setJuriAdd2(cbsCustMastDtlHis.getJuriAdd2());
        cbsCustMastDtlHisTo.setJuriCity(cbsCustMastDtlHis.getJuriCity());
        cbsCustMastDtlHisTo.setJuriState(cbsCustMastDtlHis.getJuriState());

        cbsCustMastDtlHisTo.setJuriPostal(cbsCustMastDtlHis.getJuriPostal());
        cbsCustMastDtlHisTo.setJuriCountry(cbsCustMastDtlHis.getJuriCountry());
        cbsCustMastDtlHisTo.setTan(cbsCustMastDtlHis.getTan());

        cbsCustMastDtlHisTo.setCin(cbsCustMastDtlHis.getCin());
        cbsCustMastDtlHisTo.setPerEmail(cbsCustMastDtlHis.getPerEmail());
        cbsCustMastDtlHisTo.setMailEmail(cbsCustMastDtlHis.getMailEmail());
        cbsCustMastDtlHisTo.setNationality(cbsCustMastDtlHis.getNationality());

        cbsCustMastDtlHisTo.setOtherIdentity(cbsCustMastDtlHis.getOtherIdentity());
        cbsCustMastDtlHisTo.setPoa(cbsCustMastDtlHis.getPoa());

        //New Addition
        cbsCustMastDtlHisTo.setPerAddType(cbsCustMastDtlHis.getPerAddType());

        cbsCustMastDtlHisTo.setMailAddType(cbsCustMastDtlHis.getMailAddType());
        cbsCustMastDtlHisTo.setMailPoa(cbsCustMastDtlHis.getMailPoa());

        cbsCustMastDtlHisTo.setJuriAddBasedOnFlag(cbsCustMastDtlHis.getJuriAddBasedOnFlag());
        cbsCustMastDtlHisTo.setJuriAddType(cbsCustMastDtlHis.getJuriAddType());
        cbsCustMastDtlHisTo.setJuriPoa(cbsCustMastDtlHis.getJuriPoa());

        cbsCustMastDtlHisTo.setAcHolderTypeFlag(cbsCustMastDtlHis.getAcHolderTypeFlag());
        cbsCustMastDtlHisTo.setAcHolderType(cbsCustMastDtlHis.getAcHolderType());
        cbsCustMastDtlHisTo.setAcType(cbsCustMastDtlHis.getAcType());

//        cbsCustMastDtlHisTo.setcKYCNo(cbsCustMastDtlHis.getcKYCNo());
        cbsCustMastDtlHisTo.setcKYCNo(cbsCustMastDtlHis.getCKYCNo());

        cbsCustMastDtlHisTo.setFatherMiddleName(cbsCustMastDtlHis.getFatherMiddleName());
        cbsCustMastDtlHisTo.setFatherLastName(cbsCustMastDtlHis.getFatherLastName());

        cbsCustMastDtlHisTo.setSpouseMiddleName(cbsCustMastDtlHis.getSpouseMiddleName());
        cbsCustMastDtlHisTo.setSpouseLastName(cbsCustMastDtlHis.getSpouseLastName());

        cbsCustMastDtlHisTo.setMotherMiddleName(cbsCustMastDtlHis.getMotherMiddleName());
        cbsCustMastDtlHisTo.setMotherLastName(cbsCustMastDtlHis.getMotherLastName());

//        cbsCustMastDtlHisTo.setcKYCNo(cbsCustMastDtlHis.getcKYCNo());
        cbsCustMastDtlHisTo.setcKYCNo(cbsCustMastDtlHis.getCKYCNo());
        cbsCustMastDtlHisTo.setTinIssuingCountry(cbsCustMastDtlHis.getTinIssuingCountry());

        cbsCustMastDtlHisTo.setCustEntityType(cbsCustMastDtlHis.getCustEntityType());
        cbsCustMastDtlHisTo.setIdentityNo(cbsCustMastDtlHis.getIdentityNo());
        cbsCustMastDtlHisTo.setIdExpiryDate(cbsCustMastDtlHis.getIdExpiryDate());
        cbsCustMastDtlHisTo.setPerMailAddSameFlagIndicate(cbsCustMastDtlHis.getPerMailAddSameFlagIndicate());

        cbsCustMastDtlHisTo.setPerDistrict(cbsCustMastDtlHis.getPerDistrict());
        cbsCustMastDtlHisTo.setMailDistrict(cbsCustMastDtlHis.getMailDistrict());
        cbsCustMastDtlHisTo.setEmpDistrict(cbsCustMastDtlHis.getEmpDistrict());

        cbsCustMastDtlHisTo.setPerOtherPOA(cbsCustMastDtlHis.getPerOtherPOA());
        cbsCustMastDtlHisTo.setMailOtherPOA(cbsCustMastDtlHis.getMailOtherPOA());
        cbsCustMastDtlHisTo.setCustEntityType(cbsCustMastDtlHis.getCustEntityType());
        cbsCustMastDtlHisTo.setJuriOtherPOA(cbsCustMastDtlHis.getJuriOtherPOA());

        cbsCustMastDtlHisTo.setIsdCode(cbsCustMastDtlHis.getIsdCode());
        cbsCustMastDtlHisTo.setJuriDistrict(cbsCustMastDtlHis.getJuriDistrict());

        cbsCustMastDtlHisTo.setFatherSpouseFlag(cbsCustMastDtlHisTo.getFatherSpouseFlag());
        cbsCustMastDtlHisTo.setCustImage(cbsCustMastDtlHisTo.getCustImage());
        //end
        return cbsCustMastDtlHisTo;
    }

    public static CbsCustomerMasterDetail adaptToCBSCustomerMasterDtlEntity(CBSCustomerMasterDetailTO cbsCustMastDtlTO) {
        CbsCustomerMasterDetail cbsCustMastDtlE = new CbsCustomerMasterDetail();


        cbsCustMastDtlE.setCustomerid(cbsCustMastDtlTO.getCustomerid());
        cbsCustMastDtlE.setTitle(cbsCustMastDtlTO.getTitle());
        cbsCustMastDtlE.setCustname(cbsCustMastDtlTO.getCustname());
        cbsCustMastDtlE.setCustFullName(cbsCustMastDtlTO.getCustFullName());
        cbsCustMastDtlE.setGstIdentificationNumber(cbsCustMastDtlTO.getGstIdentificationNumber());

        cbsCustMastDtlE.setShortname(cbsCustMastDtlTO.getShortname());
        cbsCustMastDtlE.setGender(cbsCustMastDtlTO.getGender());
        cbsCustMastDtlE.setMaritalstatus(cbsCustMastDtlTO.getMaritalstatus());

        cbsCustMastDtlE.setFathername(cbsCustMastDtlTO.getFathername());
        cbsCustMastDtlE.setMothername(cbsCustMastDtlTO.getMothername());
        cbsCustMastDtlE.setStaffflag(cbsCustMastDtlTO.getStaffflag());

        cbsCustMastDtlE.setStaffid(cbsCustMastDtlTO.getStaffid());
        cbsCustMastDtlE.setMinorflag(cbsCustMastDtlTO.getMinorflag());
        cbsCustMastDtlE.setDateOfBirth(cbsCustMastDtlTO.getDateOfBirth());

        cbsCustMastDtlE.setNriflag(cbsCustMastDtlTO.getNriflag());
        cbsCustMastDtlE.setUINCardNo(cbsCustMastDtlTO.getuINCardNo());
        cbsCustMastDtlE.setVirtualId(cbsCustMastDtlTO.getVirtualId() == null ? "" : cbsCustMastDtlTO.getVirtualId());
        cbsCustMastDtlE.setCommunicationPreference(cbsCustMastDtlTO.getCommunicationPreference());

        cbsCustMastDtlE.setEmployerid(cbsCustMastDtlTO.getEmployerid());
        cbsCustMastDtlE.setEmployeeNo(cbsCustMastDtlTO.getEmployeeNo());
        cbsCustMastDtlE.setMobilenumber(cbsCustMastDtlTO.getMobilenumber());

        cbsCustMastDtlE.setCustStatus(cbsCustMastDtlTO.getCustStatus());
        cbsCustMastDtlE.setPassportNo(cbsCustMastDtlTO.getPassportNo());
        cbsCustMastDtlE.setIssueDate(cbsCustMastDtlTO.getIssueDate());

        cbsCustMastDtlE.setIssuingAuthority(cbsCustMastDtlTO.getIssuingAuthority());
        cbsCustMastDtlE.setExpirydate(cbsCustMastDtlTO.getExpirydate());
        cbsCustMastDtlE.setPlaceOfIssue(cbsCustMastDtlTO.getPlaceOfIssue());

        cbsCustMastDtlE.setPreferredLanguage(cbsCustMastDtlTO.getPreferredLanguage());
        cbsCustMastDtlE.setNameInPreferredLanguage(cbsCustMastDtlTO.getNameInPreferredLanguage());
        cbsCustMastDtlE.setChgTurnOver(cbsCustMastDtlTO.getChgTurnOver());

        cbsCustMastDtlE.setPurgeAllowed(cbsCustMastDtlTO.getPurgeAllowed());
        cbsCustMastDtlE.setAccountManager(cbsCustMastDtlTO.getAccountManager());
        cbsCustMastDtlE.setAllowSweeps(cbsCustMastDtlTO.getAllowSweeps());

        cbsCustMastDtlE.setTradeFinanceFlag(cbsCustMastDtlTO.getTradeFinanceFlag());
        cbsCustMastDtlE.setSwiftCodeStatus(cbsCustMastDtlTO.getSwiftCodeStatus());
        cbsCustMastDtlE.setSwiftCode(cbsCustMastDtlTO.getSwiftCode());

        cbsCustMastDtlE.setBcbfid(cbsCustMastDtlTO.getBcbfid());
        cbsCustMastDtlE.setCombinedStmtFlag(cbsCustMastDtlTO.getCombinedStmtFlag());
        cbsCustMastDtlE.setStmtFreqType(cbsCustMastDtlTO.getStmtFreqType());

        cbsCustMastDtlE.setStmtFreqWeekNo(cbsCustMastDtlTO.getStmtFreqWeekNo());
        cbsCustMastDtlE.setStmtFreqWeekDay(cbsCustMastDtlTO.getStmtFreqWeekDay());
        cbsCustMastDtlE.setStmtFreqStartDate(cbsCustMastDtlTO.getStmtFreqStartDate());

        cbsCustMastDtlE.setStmtFreqNP(cbsCustMastDtlTO.getStmtFreqNP());
        cbsCustMastDtlE.setIntroCustomerId(cbsCustMastDtlTO.getIntroCustomerId());
        cbsCustMastDtlE.setCustTitle(cbsCustMastDtlTO.getCustTitle());

        cbsCustMastDtlE.setName(cbsCustMastDtlTO.getName());
        cbsCustMastDtlE.setAddressLine1(cbsCustMastDtlTO.getAddressLine1());
        cbsCustMastDtlE.setAddressLine2(cbsCustMastDtlTO.getAddressLine2());

        cbsCustMastDtlE.setVillage(cbsCustMastDtlTO.getVillage());
        cbsCustMastDtlE.setBlock(cbsCustMastDtlTO.getBlock());
        cbsCustMastDtlE.setCityCode(cbsCustMastDtlTO.getCityCode());

        cbsCustMastDtlE.setStateCode(cbsCustMastDtlTO.getStateCode());
        cbsCustMastDtlE.setPostalCode(cbsCustMastDtlTO.getPostalCode());
        cbsCustMastDtlE.setCountryCode(cbsCustMastDtlTO.getCountryCode());

        cbsCustMastDtlE.setPhoneNumber(cbsCustMastDtlTO.getPhoneNumber());
        cbsCustMastDtlE.setTelexNumber(cbsCustMastDtlTO.getTelexNumber());
        cbsCustMastDtlE.setFaxNumber(cbsCustMastDtlTO.getFaxNumber());

        cbsCustMastDtlE.setSalary(cbsCustMastDtlTO.getSalary());
        cbsCustMastDtlE.setChargeStatus(cbsCustMastDtlTO.getChargeStatus());
        cbsCustMastDtlE.setChargeLevelCode(cbsCustMastDtlTO.getChargeLevelCode());

        cbsCustMastDtlE.setABBChargeCode(cbsCustMastDtlTO.getaBBChargeCode());
        cbsCustMastDtlE.setEPSChargeCode(cbsCustMastDtlTO.getePSChargeCode());
        cbsCustMastDtlE.setPaperRemittance(cbsCustMastDtlTO.getPaperRemittance());

        cbsCustMastDtlE.setDeliveryChannelChargeCode(cbsCustMastDtlTO.getDeliveryChannelChargeCode());
        cbsCustMastDtlE.setAccountLevelCharges(cbsCustMastDtlTO.getAccountLevelCharges());
        cbsCustMastDtlE.setCustLevelCharges(cbsCustMastDtlTO.getCustLevelCharges());

        cbsCustMastDtlE.setTDSExemptionEndDate(cbsCustMastDtlTO.gettDSExemptionEndDate());
        cbsCustMastDtlE.setTaxSlab(cbsCustMastDtlTO.getTaxSlab());
        cbsCustMastDtlE.setTDSCode(cbsCustMastDtlTO.gettDSCode());

        cbsCustMastDtlE.setTDSCustomerId(cbsCustMastDtlTO.gettDSCustomerId());
        cbsCustMastDtlE.setTDSFormReceiveDate(cbsCustMastDtlTO.gettDSFormReceiveDate());
        cbsCustMastDtlE.setTDSExemptionReferenceNo(cbsCustMastDtlTO.gettDSExemptionReferenceNo());

        cbsCustMastDtlE.setExemptionRemarks(cbsCustMastDtlTO.getExemptionRemarks());
        cbsCustMastDtlE.setITFileNo(cbsCustMastDtlTO.getiTFileNo());
        cbsCustMastDtlE.setTDSFloorLimit(cbsCustMastDtlTO.gettDSFloorLimit());

        cbsCustMastDtlE.setCustFinancialDetails(cbsCustMastDtlTO.getCustFinancialDetails());
        cbsCustMastDtlE.setFinancialYearAndMonth(cbsCustMastDtlTO.getFinancialYearAndMonth());
        cbsCustMastDtlE.setCurrencyCodeType(cbsCustMastDtlTO.getCurrencyCodeType());

        cbsCustMastDtlE.setPropertyAssets(cbsCustMastDtlTO.getPropertyAssets());
        cbsCustMastDtlE.setBusinessAssets(cbsCustMastDtlTO.getBusinessAssets());
        cbsCustMastDtlE.setInvestments(cbsCustMastDtlTO.getInvestments());

        cbsCustMastDtlE.setNetworth(cbsCustMastDtlTO.getNetworth());
        cbsCustMastDtlE.setDeposits(cbsCustMastDtlTO.getDeposits());
        cbsCustMastDtlE.setOtherBankCode(cbsCustMastDtlTO.getOtherBankCode());

        cbsCustMastDtlE.setLimitsWithOtherBank(cbsCustMastDtlTO.getLimitsWithOtherBank());
        cbsCustMastDtlE.setFundBasedLimit(cbsCustMastDtlTO.getFundBasedLimit());
        cbsCustMastDtlE.setNonFundBasedLimit(cbsCustMastDtlTO.getNonFundBasedLimit());

        cbsCustMastDtlE.setOffLineCustDebitLimit(cbsCustMastDtlTO.getOffLineCustDebitLimit());
        cbsCustMastDtlE.setCustSalary(cbsCustMastDtlTO.getCustSalary());
        cbsCustMastDtlE.setCustFinancialDate(cbsCustMastDtlTO.getCustFinancialDate());

        cbsCustMastDtlE.setPANGIRNumber(cbsCustMastDtlTO.getpANGIRNumber());
        cbsCustMastDtlE.setTINNumber(cbsCustMastDtlTO.gettINNumber());
        cbsCustMastDtlE.setSalesTaxNo(cbsCustMastDtlTO.getSalesTaxNo());

        cbsCustMastDtlE.setExciseNo(cbsCustMastDtlTO.getExciseNo());
        cbsCustMastDtlE.setVoterIDNo(cbsCustMastDtlTO.getVoterIDNo());
        cbsCustMastDtlE.setDrivingLicenseNo(cbsCustMastDtlTO.getDrivingLicenseNo());

        cbsCustMastDtlE.setCreditCard(cbsCustMastDtlTO.getCreditCard());
        cbsCustMastDtlE.setCardNumber(cbsCustMastDtlTO.getCardNumber());
        cbsCustMastDtlE.setCardIssuer(cbsCustMastDtlTO.getCardIssuer());

        cbsCustMastDtlE.setBankName(cbsCustMastDtlTO.getBankName());
        cbsCustMastDtlE.setAcctId(cbsCustMastDtlTO.getAcctId());
        cbsCustMastDtlE.setBranchName(cbsCustMastDtlTO.getBranchName());

        cbsCustMastDtlE.setPerAddressLine1(cbsCustMastDtlTO.getPerAddressLine1());
//        cbsCustMastDtlE.setPerAddressLine2(cbsCustMastDtlTO.getPerAddressLine2());
        cbsCustMastDtlE.setPeraddressline2(cbsCustMastDtlTO.getPerAddressLine2());
        cbsCustMastDtlE.setPerVillage(cbsCustMastDtlTO.getPerVillage());

        cbsCustMastDtlE.setPerBlock(cbsCustMastDtlTO.getPerBlock());
        cbsCustMastDtlE.setPerCityCode(cbsCustMastDtlTO.getPerCityCode());
        cbsCustMastDtlE.setPerStateCode(cbsCustMastDtlTO.getPerStateCode());

        cbsCustMastDtlE.setPerPostalCode(cbsCustMastDtlTO.getPerPostalCode());
        cbsCustMastDtlE.setPerCountryCode(cbsCustMastDtlTO.getPerCountryCode());
        cbsCustMastDtlE.setPerPhoneNumber(cbsCustMastDtlTO.getPerPhoneNumber());

        cbsCustMastDtlE.setPerTelexNumber(cbsCustMastDtlTO.getPerTelexNumber());
        cbsCustMastDtlE.setPerFaxNumber(cbsCustMastDtlTO.getPerFaxNumber());
        cbsCustMastDtlE.setMailAddressLine1(cbsCustMastDtlTO.getMailAddressLine1());

        cbsCustMastDtlE.setMailAddressLine2(cbsCustMastDtlTO.getMailAddressLine2());
        cbsCustMastDtlE.setMailVillage(cbsCustMastDtlTO.getMailVillage());
        cbsCustMastDtlE.setMailBlock(cbsCustMastDtlTO.getMailBlock());

        cbsCustMastDtlE.setMailCityCode(cbsCustMastDtlTO.getMailCityCode());
        cbsCustMastDtlE.setMailStateCode(cbsCustMastDtlTO.getMailStateCode());
        cbsCustMastDtlE.setMailPostalCode(cbsCustMastDtlTO.getMailPostalCode());

        cbsCustMastDtlE.setMailCountryCode(cbsCustMastDtlTO.getMailCountryCode());
        cbsCustMastDtlE.setMailPhoneNumber(cbsCustMastDtlTO.getMailPhoneNumber());
        cbsCustMastDtlE.setMailTelexNumber(cbsCustMastDtlTO.getMailTelexNumber());

        cbsCustMastDtlE.setMailFaxNumber(cbsCustMastDtlTO.getMailFaxNumber());
        cbsCustMastDtlE.setEmpAddressLine1(cbsCustMastDtlTO.getEmpAddressLine1());
        cbsCustMastDtlE.setEmpAddressLine2(cbsCustMastDtlTO.getEmpAddressLine2());

        cbsCustMastDtlE.setEmpVillage(cbsCustMastDtlTO.getEmpVillage());
        cbsCustMastDtlE.setEmpBlock(cbsCustMastDtlTO.getEmpBlock());
        cbsCustMastDtlE.setEmpCityCode(cbsCustMastDtlTO.getEmpCityCode());

        cbsCustMastDtlE.setEmpStateCode(cbsCustMastDtlTO.getEmpStateCode());
        cbsCustMastDtlE.setEmpPostalCode(cbsCustMastDtlTO.getEmpPostalCode());
        cbsCustMastDtlE.setEmpCountryCode(cbsCustMastDtlTO.getEmpCountryCode());

        cbsCustMastDtlE.setEmpPhoneNumber(cbsCustMastDtlTO.getEmpPhoneNumber());
        cbsCustMastDtlE.setEmpTelexNumber(cbsCustMastDtlTO.getEmpTelexNumber());
        cbsCustMastDtlE.setEmpFaxNumber(cbsCustMastDtlTO.getEmpFaxNumber());

        cbsCustMastDtlE.setEmailID(cbsCustMastDtlTO.getEmailID());
        cbsCustMastDtlE.setOperationalRiskRating(cbsCustMastDtlTO.getOperationalRiskRating());
        cbsCustMastDtlE.setRatingAsOn(cbsCustMastDtlTO.getRatingAsOn());

        cbsCustMastDtlE.setCreditRiskRatingInternal(cbsCustMastDtlTO.getCreditRiskRatingInternal());
        cbsCustMastDtlE.setCreditRatingAsOn(cbsCustMastDtlTO.getCreditRatingAsOn());
        cbsCustMastDtlE.setExternalRatingShortTerm(cbsCustMastDtlTO.getExternalRatingShortTerm());

        cbsCustMastDtlE.setExternShortRatingAsOn(cbsCustMastDtlTO.getExternShortRatingAsOn());
        cbsCustMastDtlE.setExternalRatingLongTerm(cbsCustMastDtlTO.getExternalRatingLongTerm());
        cbsCustMastDtlE.setExternLongRatingAsOn(cbsCustMastDtlTO.getExternLongRatingAsOn());

        cbsCustMastDtlE.setCustAcquistionDate(cbsCustMastDtlTO.getCustAcquistionDate());
        cbsCustMastDtlE.setThresoldTransactionLimit(cbsCustMastDtlTO.getThresoldTransactionLimit());
        cbsCustMastDtlE.setThresoldLimitUpdationDate(cbsCustMastDtlTO.getThresoldLimitUpdationDate());

        cbsCustMastDtlE.setCustUpdationDate(cbsCustMastDtlTO.getCustUpdationDate());
        cbsCustMastDtlE.setSuspensionFlg(cbsCustMastDtlTO.getSuspensionFlg());
        cbsCustMastDtlE.setPrimaryBrCode(cbsCustMastDtlTO.getPrimaryBrCode());

        cbsCustMastDtlE.setLastUpdatedBr(cbsCustMastDtlTO.getLastUpdatedBr());
        cbsCustMastDtlE.setFirstAccountDate(cbsCustMastDtlTO.getFirstAccountDate());
        cbsCustMastDtlE.setAuth(cbsCustMastDtlTO.getAuth());

        cbsCustMastDtlE.setLastChangeUserID(cbsCustMastDtlTO.getLastChangeUserID());
        cbsCustMastDtlE.setLastChangeTime(cbsCustMastDtlTO.getLastChangeTime());
        cbsCustMastDtlE.setRecordCreaterID(cbsCustMastDtlTO.getRecordCreaterID());

//        cbsCustMastDtlE.setCreationTime(cbsCustMastDtlTO.getCreationTime());
        cbsCustMastDtlE.setTsCnt(cbsCustMastDtlTO.getTsCnt());

        cbsCustMastDtlE.setAadhaarNo(cbsCustMastDtlTO.getAdhaarNo());
        cbsCustMastDtlE.setLpgId(cbsCustMastDtlTO.getLpgId());
        cbsCustMastDtlE.setAadhaarLpgAcno(cbsCustMastDtlTO.getAdhaarLpgAcno());

        cbsCustMastDtlE.setMandateFlag(cbsCustMastDtlTO.getMandateFlag());
        cbsCustMastDtlE.setMandateDate(cbsCustMastDtlTO.getMandateDt());
        cbsCustMastDtlE.setRegType(cbsCustMastDtlTO.getRegType());
        //add by rahul
        cbsCustMastDtlE.setAadhaarMode(cbsCustMastDtlTO.getAadhaarMode() == null ? "" : cbsCustMastDtlTO.getAadhaarMode());
        cbsCustMastDtlE.setAadhaarBankIin(cbsCustMastDtlTO.getAadhaarBankIin() == null ? "" : cbsCustMastDtlTO.getAadhaarBankIin());
        //
        cbsCustMastDtlE.setMiddleName(cbsCustMastDtlTO.getMiddleName());
        cbsCustMastDtlE.setLastName(cbsCustMastDtlTO.getLastName());
        cbsCustMastDtlE.setSpouseName(cbsCustMastDtlTO.getSpouseName());

        cbsCustMastDtlE.setMaidenName(cbsCustMastDtlTO.getMaidenName());
        cbsCustMastDtlE.setNregaJobCard(cbsCustMastDtlTO.getNregaJobCard());
        cbsCustMastDtlE.setDlExpiry(cbsCustMastDtlTO.getDlExpiry());

        cbsCustMastDtlE.setLegalDocument(cbsCustMastDtlTO.getLegalDocument());
        cbsCustMastDtlE.setIncomeRange(cbsCustMastDtlTO.getIncomeRange());
        cbsCustMastDtlE.setNetworthAsOn(cbsCustMastDtlTO.getNetworthAsOn());

        cbsCustMastDtlE.setQualification(cbsCustMastDtlTO.getQualification());
        cbsCustMastDtlE.setPoliticalExposed(cbsCustMastDtlTO.getPoliticalExposed());
        cbsCustMastDtlE.setJuriAdd1(cbsCustMastDtlTO.getJuriAdd1());

        cbsCustMastDtlE.setJuriAdd2(cbsCustMastDtlTO.getJuriAdd2());
        cbsCustMastDtlE.setJuriCity(cbsCustMastDtlTO.getJuriCity());
        cbsCustMastDtlE.setJuriState(cbsCustMastDtlTO.getJuriState());

        cbsCustMastDtlE.setJuriPostal(cbsCustMastDtlTO.getJuriPostal());
        cbsCustMastDtlE.setJuriCountry(cbsCustMastDtlTO.getJuriCountry());
        cbsCustMastDtlE.setTan(cbsCustMastDtlTO.getTan());

        cbsCustMastDtlE.setCin(cbsCustMastDtlTO.getCin());
        cbsCustMastDtlE.setPerEmail(cbsCustMastDtlTO.getPerEmail());
        cbsCustMastDtlE.setMailEmail(cbsCustMastDtlTO.getMailEmail());
        cbsCustMastDtlE.setNationality(cbsCustMastDtlTO.getNationality());

        cbsCustMastDtlE.setOtherIdentity(cbsCustMastDtlTO.getOtherIdentity());
        cbsCustMastDtlE.setPoa(cbsCustMastDtlTO.getPoa());
        cbsCustMastDtlE.setStrFlag(cbsCustMastDtlTO.getStrFlag());
        //addition 02/01/2017
        cbsCustMastDtlE.setPerAddType(cbsCustMastDtlTO.getPerAddType());
        cbsCustMastDtlE.setMailAddType(cbsCustMastDtlTO.getMailAddType());
        cbsCustMastDtlE.setMailPoa(cbsCustMastDtlTO.getMailPoa());

        cbsCustMastDtlE.setJuriAddBasedOnFlag(cbsCustMastDtlTO.getJuriAddBasedOnFlag());
        cbsCustMastDtlE.setJuriAddType(cbsCustMastDtlTO.getJuriAddType());
        cbsCustMastDtlE.setJuriPoa(cbsCustMastDtlTO.getJuriPoa());

        cbsCustMastDtlE.setCustEntityType(cbsCustMastDtlTO.getCustEntityType());
        cbsCustMastDtlE.setAcHolderTypeFlag(cbsCustMastDtlTO.getAcHolderTypeFlag());
        cbsCustMastDtlE.setAcHolderType(cbsCustMastDtlTO.getAcHolderType());
        cbsCustMastDtlE.setAcType(cbsCustMastDtlTO.getAcType());

//        cbsCustMastDtlE.setcKYCNo(cbsCustMastDtlTO.getcKYCNo());
        cbsCustMastDtlE.setCKYCNo(cbsCustMastDtlTO.getcKYCNo());

        cbsCustMastDtlE.setFatherMiddleName(cbsCustMastDtlTO.getFatherMiddleName());
        cbsCustMastDtlE.setFatherLastName(cbsCustMastDtlTO.getFatherLastName());
        cbsCustMastDtlE.setSpouseMiddleName(cbsCustMastDtlTO.getSpouseMiddleName());
        cbsCustMastDtlE.setSpouseLastName(cbsCustMastDtlTO.getSpouseLastName());
        cbsCustMastDtlE.setMotherMiddleName(cbsCustMastDtlTO.getMotherMiddleName());
        cbsCustMastDtlE.setMotherLastName(cbsCustMastDtlTO.getMotherLastName());

        cbsCustMastDtlE.setTinIssuingCountry(cbsCustMastDtlTO.getTinIssuingCountry());
        cbsCustMastDtlE.setIdentityNo(cbsCustMastDtlTO.getIdentityNo());
        cbsCustMastDtlE.setIdExpiryDate(cbsCustMastDtlTO.getIdExpiryDate());
        cbsCustMastDtlE.setPerMailAddSameFlagIndicate(cbsCustMastDtlTO.getPerMailAddSameFlagIndicate());

        cbsCustMastDtlE.setPerDistrict(cbsCustMastDtlTO.getPerDistrict());
        cbsCustMastDtlE.setMailDistrict(cbsCustMastDtlTO.getMailDistrict());
        cbsCustMastDtlE.setEmpDistrict(cbsCustMastDtlTO.getEmpDistrict());

        cbsCustMastDtlE.setPerOtherPOA(cbsCustMastDtlTO.getPerOtherPOA());
        cbsCustMastDtlE.setMailOtherPOA(cbsCustMastDtlTO.getMailOtherPOA());
        cbsCustMastDtlE.setCustEntityType(cbsCustMastDtlTO.getCustEntityType());
        cbsCustMastDtlE.setJuriOtherPOA(cbsCustMastDtlTO.getJuriOtherPOA());

        cbsCustMastDtlE.setCreationTime(cbsCustMastDtlTO.getCreationTime());
        cbsCustMastDtlE.setIsdCode(cbsCustMastDtlTO.getIsdCode());
        cbsCustMastDtlE.setJuriDistrict(cbsCustMastDtlTO.getJuriDistrict());

        cbsCustMastDtlE.setFatherSpouseFlag(cbsCustMastDtlTO.getFatherSpouseFlag());
        cbsCustMastDtlE.setCustImage(cbsCustMastDtlTO.getCustImage());
        cbsCustMastDtlE.setSuspensionFlg(cbsCustMastDtlTO.getSuspensionFlg());
        //end
        return cbsCustMastDtlE;
    }

    public static CBSCustomerMasterDetailTO adaptToCBSCustomerMasterDtlTO(CbsCustomerMasterDetail cbsCustMastDtl) {
        CBSCustomerMasterDetailTO cbsCustMastDtlTo = new CBSCustomerMasterDetailTO();

        cbsCustMastDtlTo.setCustomerid(cbsCustMastDtl.getCustomerid());
        cbsCustMastDtlTo.setTitle(cbsCustMastDtl.getTitle());
        cbsCustMastDtlTo.setCustname(cbsCustMastDtl.getCustname());
        cbsCustMastDtlTo.setCustFullName(cbsCustMastDtl.getCustFullName());
        cbsCustMastDtlTo.setGstIdentificationNumber(cbsCustMastDtl.getGstIdentificationNumber());


        cbsCustMastDtlTo.setShortname(cbsCustMastDtl.getShortname());
        cbsCustMastDtlTo.setGender(cbsCustMastDtl.getGender());
        cbsCustMastDtlTo.setMaritalstatus(cbsCustMastDtl.getMaritalstatus());

        cbsCustMastDtlTo.setFathername(cbsCustMastDtl.getFathername());
        cbsCustMastDtlTo.setMothername(cbsCustMastDtl.getMothername());
        cbsCustMastDtlTo.setStaffflag(cbsCustMastDtl.getStaffflag());

        cbsCustMastDtlTo.setStaffid(cbsCustMastDtl.getStaffid());
        cbsCustMastDtlTo.setMinorflag(cbsCustMastDtl.getMinorflag());
        cbsCustMastDtlTo.setDateOfBirth(cbsCustMastDtl.getDateOfBirth());

        cbsCustMastDtlTo.setNriflag(cbsCustMastDtl.getNriflag());
        cbsCustMastDtlTo.setuINCardNo(cbsCustMastDtl.getUINCardNo());
        cbsCustMastDtlTo.setCommunicationPreference(cbsCustMastDtl.getCommunicationPreference());
        cbsCustMastDtlTo.setVirtualId(cbsCustMastDtl.getVirtualId()==null ? "" : cbsCustMastDtl.getVirtualId());

        cbsCustMastDtlTo.setEmployerid(cbsCustMastDtl.getEmployerid());
        cbsCustMastDtlTo.setEmployeeNo(cbsCustMastDtl.getEmployeeNo());
        cbsCustMastDtlTo.setMobilenumber(cbsCustMastDtl.getMobilenumber());

        cbsCustMastDtlTo.setCustStatus(cbsCustMastDtl.getCustStatus());
        cbsCustMastDtlTo.setPassportNo(cbsCustMastDtl.getPassportNo());
        cbsCustMastDtlTo.setIssueDate(cbsCustMastDtl.getIssueDate());

        cbsCustMastDtlTo.setIssuingAuthority(cbsCustMastDtl.getIssuingAuthority());
        cbsCustMastDtlTo.setExpirydate(cbsCustMastDtl.getExpirydate());
        cbsCustMastDtlTo.setPlaceOfIssue(cbsCustMastDtl.getPlaceOfIssue());

        cbsCustMastDtlTo.setPreferredLanguage(cbsCustMastDtl.getPreferredLanguage());
        cbsCustMastDtlTo.setNameInPreferredLanguage(cbsCustMastDtl.getNameInPreferredLanguage());
        cbsCustMastDtlTo.setChgTurnOver(cbsCustMastDtl.getChgTurnOver());

        cbsCustMastDtlTo.setPurgeAllowed(cbsCustMastDtl.getPurgeAllowed());
        cbsCustMastDtlTo.setAccountManager(cbsCustMastDtl.getAccountManager());
        cbsCustMastDtlTo.setAllowSweeps(cbsCustMastDtl.getAllowSweeps());

        cbsCustMastDtlTo.setTradeFinanceFlag(cbsCustMastDtl.getTradeFinanceFlag());
        cbsCustMastDtlTo.setSwiftCodeStatus(cbsCustMastDtl.getSwiftCodeStatus());
        cbsCustMastDtlTo.setSwiftCode(cbsCustMastDtl.getSwiftCode());

        cbsCustMastDtlTo.setBcbfid(cbsCustMastDtl.getBcbfid());
        cbsCustMastDtlTo.setCombinedStmtFlag(cbsCustMastDtl.getCombinedStmtFlag());
        cbsCustMastDtlTo.setStmtFreqType(cbsCustMastDtl.getStmtFreqType());

        cbsCustMastDtlTo.setStmtFreqWeekNo(cbsCustMastDtl.getStmtFreqWeekNo());
        cbsCustMastDtlTo.setStmtFreqWeekDay(cbsCustMastDtl.getStmtFreqWeekDay());
        cbsCustMastDtlTo.setStmtFreqStartDate(cbsCustMastDtl.getStmtFreqStartDate());

        cbsCustMastDtlTo.setStmtFreqNP(cbsCustMastDtl.getStmtFreqNP());
        cbsCustMastDtlTo.setIntroCustomerId(cbsCustMastDtl.getIntroCustomerId());
        cbsCustMastDtlTo.setCustTitle(cbsCustMastDtl.getCustTitle());

        cbsCustMastDtlTo.setName(cbsCustMastDtl.getName());
        cbsCustMastDtlTo.setAddressLine1(cbsCustMastDtl.getAddressLine1());
        cbsCustMastDtlTo.setAddressLine2(cbsCustMastDtl.getAddressLine2());

        cbsCustMastDtlTo.setVillage(cbsCustMastDtl.getVillage());
        cbsCustMastDtlTo.setBlock(cbsCustMastDtl.getBlock());
        cbsCustMastDtlTo.setCityCode(cbsCustMastDtl.getCityCode());

        cbsCustMastDtlTo.setStateCode(cbsCustMastDtl.getStateCode());
        cbsCustMastDtlTo.setPostalCode(cbsCustMastDtl.getPostalCode());
        cbsCustMastDtlTo.setCountryCode(cbsCustMastDtl.getCountryCode());

        cbsCustMastDtlTo.setPhoneNumber(cbsCustMastDtl.getPhoneNumber());
        cbsCustMastDtlTo.setTelexNumber(cbsCustMastDtl.getTelexNumber());
        cbsCustMastDtlTo.setFaxNumber(cbsCustMastDtl.getFaxNumber());

        cbsCustMastDtlTo.setSalary(cbsCustMastDtl.getSalary());
        cbsCustMastDtlTo.setChargeStatus(cbsCustMastDtl.getChargeStatus());
        cbsCustMastDtlTo.setChargeLevelCode(cbsCustMastDtl.getChargeLevelCode());

        cbsCustMastDtlTo.setaBBChargeCode(cbsCustMastDtl.getABBChargeCode());
        cbsCustMastDtlTo.setePSChargeCode(cbsCustMastDtl.getEPSChargeCode());
        cbsCustMastDtlTo.setPaperRemittance(cbsCustMastDtl.getPaperRemittance());

        cbsCustMastDtlTo.setDeliveryChannelChargeCode(cbsCustMastDtl.getDeliveryChannelChargeCode());
        cbsCustMastDtlTo.setAccountLevelCharges(cbsCustMastDtl.getAccountLevelCharges());
        cbsCustMastDtlTo.setCustLevelCharges(cbsCustMastDtl.getCustLevelCharges());

        cbsCustMastDtlTo.settDSExemptionEndDate(cbsCustMastDtl.getTDSExemptionEndDate());
        cbsCustMastDtlTo.setTaxSlab(cbsCustMastDtl.getTaxSlab());
        cbsCustMastDtlTo.settDSCode(cbsCustMastDtl.getTDSCode());

        cbsCustMastDtlTo.settDSCustomerId(cbsCustMastDtl.getTDSCustomerId());
        cbsCustMastDtlTo.settDSFormReceiveDate(cbsCustMastDtl.getTDSFormReceiveDate());
        cbsCustMastDtlTo.settDSExemptionReferenceNo(cbsCustMastDtl.getTDSExemptionReferenceNo());

        cbsCustMastDtlTo.setExemptionRemarks(cbsCustMastDtl.getExemptionRemarks());
        cbsCustMastDtlTo.setiTFileNo(cbsCustMastDtl.getITFileNo());
        cbsCustMastDtlTo.settDSFloorLimit(cbsCustMastDtl.getTDSFloorLimit());

        cbsCustMastDtlTo.setCustFinancialDetails(cbsCustMastDtl.getCustFinancialDetails());
        cbsCustMastDtlTo.setFinancialYearAndMonth(cbsCustMastDtl.getFinancialYearAndMonth());
        cbsCustMastDtlTo.setCurrencyCodeType(cbsCustMastDtl.getCurrencyCodeType());

        cbsCustMastDtlTo.setPropertyAssets(cbsCustMastDtl.getPropertyAssets());
        cbsCustMastDtlTo.setBusinessAssets(cbsCustMastDtl.getBusinessAssets());
        cbsCustMastDtlTo.setInvestments(cbsCustMastDtl.getInvestments());

        cbsCustMastDtlTo.setNetworth(cbsCustMastDtl.getNetworth());
        cbsCustMastDtlTo.setDeposits(cbsCustMastDtl.getDeposits());
        cbsCustMastDtlTo.setOtherBankCode(cbsCustMastDtl.getOtherBankCode());

        cbsCustMastDtlTo.setLimitsWithOtherBank(cbsCustMastDtl.getLimitsWithOtherBank());
        cbsCustMastDtlTo.setFundBasedLimit(cbsCustMastDtl.getFundBasedLimit());
        cbsCustMastDtlTo.setNonFundBasedLimit(cbsCustMastDtl.getNonFundBasedLimit());

        cbsCustMastDtlTo.setOffLineCustDebitLimit(cbsCustMastDtl.getOffLineCustDebitLimit());
        cbsCustMastDtlTo.setCustSalary(cbsCustMastDtl.getCustSalary());
        cbsCustMastDtlTo.setCustFinancialDate(cbsCustMastDtl.getCustFinancialDate());

        cbsCustMastDtlTo.setpANGIRNumber(cbsCustMastDtl.getPANGIRNumber());
        cbsCustMastDtlTo.settINNumber(cbsCustMastDtl.getTINNumber());
        cbsCustMastDtlTo.setSalesTaxNo(cbsCustMastDtl.getSalesTaxNo());

        cbsCustMastDtlTo.setExciseNo(cbsCustMastDtl.getExciseNo());
        cbsCustMastDtlTo.setVoterIDNo(cbsCustMastDtl.getVoterIDNo());
        cbsCustMastDtlTo.setDrivingLicenseNo(cbsCustMastDtl.getDrivingLicenseNo());

        cbsCustMastDtlTo.setCreditCard(cbsCustMastDtl.getCreditCard());
        cbsCustMastDtlTo.setCardNumber(cbsCustMastDtl.getCardNumber());
        cbsCustMastDtlTo.setCardIssuer(cbsCustMastDtl.getCardIssuer());

        cbsCustMastDtlTo.setBankName(cbsCustMastDtl.getBankName());
        cbsCustMastDtlTo.setAcctId(cbsCustMastDtl.getAcctId());
        cbsCustMastDtlTo.setBranchName(cbsCustMastDtl.getBranchName());

        cbsCustMastDtlTo.setPerAddressLine1(cbsCustMastDtl.getPerAddressLine1());
//        cbsCustMastDtlTo.setPerAddressLine2(cbsCustMastDtl.getPerAddressLine2());
        cbsCustMastDtlTo.setPerAddressLine2(cbsCustMastDtl.getPeraddressline2());
        cbsCustMastDtlTo.setPerVillage(cbsCustMastDtl.getPerVillage());

        cbsCustMastDtlTo.setPerBlock(cbsCustMastDtl.getPerBlock());
        cbsCustMastDtlTo.setPerCityCode(cbsCustMastDtl.getPerCityCode());
        cbsCustMastDtlTo.setPerStateCode(cbsCustMastDtl.getPerStateCode());

        cbsCustMastDtlTo.setPerPostalCode(cbsCustMastDtl.getPerPostalCode());
        cbsCustMastDtlTo.setPerCountryCode(cbsCustMastDtl.getPerCountryCode());
        cbsCustMastDtlTo.setPerPhoneNumber(cbsCustMastDtl.getPerPhoneNumber());

        cbsCustMastDtlTo.setPerTelexNumber(cbsCustMastDtl.getPerTelexNumber());
        cbsCustMastDtlTo.setPerFaxNumber(cbsCustMastDtl.getPerFaxNumber());
        cbsCustMastDtlTo.setMailAddressLine1(cbsCustMastDtl.getMailAddressLine1());

        cbsCustMastDtlTo.setMailAddressLine2(cbsCustMastDtl.getMailAddressLine2());
        cbsCustMastDtlTo.setMailVillage(cbsCustMastDtl.getMailVillage());
        cbsCustMastDtlTo.setMailBlock(cbsCustMastDtl.getMailBlock());

        cbsCustMastDtlTo.setMailCityCode(cbsCustMastDtl.getMailCityCode());
        cbsCustMastDtlTo.setMailStateCode(cbsCustMastDtl.getMailStateCode());
        cbsCustMastDtlTo.setMailPostalCode(cbsCustMastDtl.getMailPostalCode());

        cbsCustMastDtlTo.setMailCountryCode(cbsCustMastDtl.getMailCountryCode());
        cbsCustMastDtlTo.setMailPhoneNumber(cbsCustMastDtl.getMailPhoneNumber());
        cbsCustMastDtlTo.setMailTelexNumber(cbsCustMastDtl.getMailTelexNumber());

        cbsCustMastDtlTo.setMailFaxNumber(cbsCustMastDtl.getMailFaxNumber());
        cbsCustMastDtlTo.setEmpAddressLine1(cbsCustMastDtl.getEmpAddressLine1());
        cbsCustMastDtlTo.setEmpAddressLine2(cbsCustMastDtl.getEmpAddressLine2());

        cbsCustMastDtlTo.setEmpVillage(cbsCustMastDtl.getEmpVillage());
        cbsCustMastDtlTo.setEmpBlock(cbsCustMastDtl.getEmpBlock());
        cbsCustMastDtlTo.setEmpCityCode(cbsCustMastDtl.getEmpCityCode());

        cbsCustMastDtlTo.setEmpStateCode(cbsCustMastDtl.getEmpStateCode());
        cbsCustMastDtlTo.setEmpPostalCode(cbsCustMastDtl.getEmpPostalCode());
        cbsCustMastDtlTo.setEmpCountryCode(cbsCustMastDtl.getEmpCountryCode());

        cbsCustMastDtlTo.setEmpPhoneNumber(cbsCustMastDtl.getEmpPhoneNumber());
        cbsCustMastDtlTo.setEmpTelexNumber(cbsCustMastDtl.getEmpTelexNumber());
        cbsCustMastDtlTo.setEmpFaxNumber(cbsCustMastDtl.getEmpFaxNumber());

        cbsCustMastDtlTo.setEmailID(cbsCustMastDtl.getEmailID());
        cbsCustMastDtlTo.setOperationalRiskRating(cbsCustMastDtl.getOperationalRiskRating());
        cbsCustMastDtlTo.setRatingAsOn(cbsCustMastDtl.getRatingAsOn());

        cbsCustMastDtlTo.setCreditRiskRatingInternal(cbsCustMastDtl.getCreditRiskRatingInternal());
        cbsCustMastDtlTo.setCreditRatingAsOn(cbsCustMastDtl.getCreditRatingAsOn());
        cbsCustMastDtlTo.setExternalRatingShortTerm(cbsCustMastDtl.getExternalRatingShortTerm());

        cbsCustMastDtlTo.setExternShortRatingAsOn(cbsCustMastDtl.getExternShortRatingAsOn());
        cbsCustMastDtlTo.setExternalRatingLongTerm(cbsCustMastDtl.getExternalRatingLongTerm());
        cbsCustMastDtlTo.setExternLongRatingAsOn(cbsCustMastDtl.getExternLongRatingAsOn());

        cbsCustMastDtlTo.setCustAcquistionDate(cbsCustMastDtl.getCustAcquistionDate());
        cbsCustMastDtlTo.setThresoldTransactionLimit(cbsCustMastDtl.getThresoldTransactionLimit());
        cbsCustMastDtlTo.setThresoldLimitUpdationDate(cbsCustMastDtl.getThresoldLimitUpdationDate());

        cbsCustMastDtlTo.setCustUpdationDate(cbsCustMastDtl.getCustUpdationDate());
        cbsCustMastDtlTo.setSuspensionFlg(cbsCustMastDtl.getSuspensionFlg());
        cbsCustMastDtlTo.setPrimaryBrCode(cbsCustMastDtl.getPrimaryBrCode());

        cbsCustMastDtlTo.setLastUpdatedBr(cbsCustMastDtl.getLastUpdatedBr());
        cbsCustMastDtlTo.setFirstAccountDate(cbsCustMastDtl.getFirstAccountDate());
        cbsCustMastDtlTo.setAuth(cbsCustMastDtl.getAuth());

        cbsCustMastDtlTo.setLastChangeUserID(cbsCustMastDtl.getLastChangeUserID());
        cbsCustMastDtlTo.setLastChangeTime(cbsCustMastDtl.getLastChangeTime());
        cbsCustMastDtlTo.setRecordCreaterID(cbsCustMastDtl.getRecordCreaterID());

        cbsCustMastDtlTo.setCreationTime(cbsCustMastDtl.getCreationTime());
        cbsCustMastDtlTo.setTsCnt(cbsCustMastDtl.getTsCnt());

        cbsCustMastDtlTo.setAdhaarNo(cbsCustMastDtl.getAadhaarNo());
        cbsCustMastDtlTo.setLpgId(cbsCustMastDtl.getLpgId());
        cbsCustMastDtlTo.setAdhaarLpgAcno(cbsCustMastDtl.getAadhaarLpgAcno());

        cbsCustMastDtlTo.setMandateFlag(cbsCustMastDtl.getMandateFlag());
        cbsCustMastDtlTo.setMandateDt(cbsCustMastDtl.getMandateDate());
        cbsCustMastDtlTo.setRegType(cbsCustMastDtl.getRegType());
        //add by rahul
        cbsCustMastDtlTo.setAadhaarMode(cbsCustMastDtl.getAadhaarMode() == null ? "" : cbsCustMastDtl.getAadhaarMode());
        cbsCustMastDtlTo.setAadhaarBankIin(cbsCustMastDtl.getAadhaarBankIin() == null ? "" : cbsCustMastDtl.getAadhaarBankIin());
        //
        cbsCustMastDtlTo.setMiddleName(cbsCustMastDtl.getMiddleName());
        cbsCustMastDtlTo.setLastName(cbsCustMastDtl.getLastName());
        cbsCustMastDtlTo.setSpouseName(cbsCustMastDtl.getSpouseName());

        cbsCustMastDtlTo.setMaidenName(cbsCustMastDtl.getMaidenName());
        cbsCustMastDtlTo.setNregaJobCard(cbsCustMastDtl.getNregaJobCard());
        cbsCustMastDtlTo.setDlExpiry(cbsCustMastDtl.getDlExpiry());

        cbsCustMastDtlTo.setLegalDocument(cbsCustMastDtl.getLegalDocument());
        cbsCustMastDtlTo.setIncomeRange(cbsCustMastDtl.getIncomeRange());
        cbsCustMastDtlTo.setNetworthAsOn(cbsCustMastDtl.getNetworthAsOn());

        cbsCustMastDtlTo.setQualification(cbsCustMastDtl.getQualification());
        cbsCustMastDtlTo.setPoliticalExposed(cbsCustMastDtl.getPoliticalExposed());
        cbsCustMastDtlTo.setJuriAdd1(cbsCustMastDtl.getJuriAdd1());

        cbsCustMastDtlTo.setJuriAdd2(cbsCustMastDtl.getJuriAdd2());
        cbsCustMastDtlTo.setJuriCity(cbsCustMastDtl.getJuriCity());
        cbsCustMastDtlTo.setJuriState(cbsCustMastDtl.getJuriState());

        cbsCustMastDtlTo.setJuriPostal(cbsCustMastDtl.getJuriPostal());
        cbsCustMastDtlTo.setJuriCountry(cbsCustMastDtl.getJuriCountry());
        cbsCustMastDtlTo.setTan(cbsCustMastDtl.getTan());

        cbsCustMastDtlTo.setCin(cbsCustMastDtl.getCin());
        cbsCustMastDtlTo.setPerEmail(cbsCustMastDtl.getPerEmail());
        cbsCustMastDtlTo.setMailEmail(cbsCustMastDtl.getMailEmail());
        cbsCustMastDtlTo.setNationality(cbsCustMastDtl.getNationality());

        cbsCustMastDtlTo.setOtherIdentity(cbsCustMastDtl.getOtherIdentity());
        cbsCustMastDtlTo.setPoa(cbsCustMastDtl.getPoa());

        cbsCustMastDtlTo.setPerAddType(cbsCustMastDtl.getPerAddType());
        cbsCustMastDtlTo.setMailAddType(cbsCustMastDtl.getMailAddType());
        cbsCustMastDtlTo.setMailPoa(cbsCustMastDtl.getMailPoa());

        cbsCustMastDtlTo.setJuriAddBasedOnFlag(cbsCustMastDtl.getJuriAddBasedOnFlag());
        cbsCustMastDtlTo.setJuriAddType(cbsCustMastDtl.getJuriAddType());
        cbsCustMastDtlTo.setJuriPoa(cbsCustMastDtl.getJuriPoa());

        cbsCustMastDtlTo.setCustEntityType(cbsCustMastDtl.getCustEntityType());
        cbsCustMastDtlTo.setAcHolderTypeFlag(cbsCustMastDtl.getAcHolderTypeFlag());
        cbsCustMastDtlTo.setAcHolderType(cbsCustMastDtl.getAcHolderType());
        cbsCustMastDtlTo.setAcType(cbsCustMastDtl.getAcType());

//        cbsCustMastDtlTo.setcKYCNo(cbsCustMastDtl.getcKYCNo());
        cbsCustMastDtlTo.setcKYCNo(cbsCustMastDtl.getCKYCNo());

        cbsCustMastDtlTo.setFatherMiddleName(cbsCustMastDtl.getFatherMiddleName());
        cbsCustMastDtlTo.setFatherLastName(cbsCustMastDtl.getFatherLastName());
        cbsCustMastDtlTo.setSpouseMiddleName(cbsCustMastDtl.getSpouseMiddleName());
        cbsCustMastDtlTo.setSpouseLastName(cbsCustMastDtl.getSpouseLastName());
        cbsCustMastDtlTo.setMotherMiddleName(cbsCustMastDtl.getMotherMiddleName());
        cbsCustMastDtlTo.setMotherLastName(cbsCustMastDtl.getMotherLastName());

        cbsCustMastDtlTo.setTinIssuingCountry(cbsCustMastDtl.getTinIssuingCountry());

        cbsCustMastDtlTo.setIdentityNo(cbsCustMastDtl.getIdentityNo());
        cbsCustMastDtlTo.setIdExpiryDate(cbsCustMastDtl.getIdExpiryDate());
        cbsCustMastDtlTo.setPerMailAddSameFlagIndicate(cbsCustMastDtl.getPerMailAddSameFlagIndicate());

        cbsCustMastDtlTo.setPerDistrict(cbsCustMastDtl.getPerDistrict());
        cbsCustMastDtlTo.setMailDistrict(cbsCustMastDtl.getMailDistrict());
        cbsCustMastDtlTo.setEmpDistrict(cbsCustMastDtl.getEmpDistrict());

        cbsCustMastDtlTo.setPerOtherPOA(cbsCustMastDtl.getPerOtherPOA());
        cbsCustMastDtlTo.setMailOtherPOA(cbsCustMastDtl.getMailOtherPOA());
        cbsCustMastDtlTo.setCustEntityType(cbsCustMastDtl.getCustEntityType());
        cbsCustMastDtlTo.setJuriOtherPOA(cbsCustMastDtl.getJuriOtherPOA());

        cbsCustMastDtlTo.setIsdCode(cbsCustMastDtl.getIsdCode());
        cbsCustMastDtlTo.setJuriDistrict(cbsCustMastDtl.getJuriDistrict());

        cbsCustMastDtlTo.setFatherSpouseFlag(cbsCustMastDtl.getFatherSpouseFlag());
        cbsCustMastDtlTo.setCustImage(cbsCustMastDtl.getCustImage());
        //end

        return cbsCustMastDtlTo;
    }

    public static CbsRelatedPersonsDetailsHis adaptToCBSRelatedPersonsDtlsHisEntity(CBSRelatedPersonsDetailsHisTO cbsRelPersonsDtlsHisTO) {
        CbsRelatedPersonsDetailsHis cbsRelPersonsDtlsHisE = new CbsRelatedPersonsDetailsHis();

        cbsRelPersonsDtlsHisE.setCustomerId(cbsRelPersonsDtlsHisTO.getCustomerId());
        cbsRelPersonsDtlsHisE.setPersonSrNo(cbsRelPersonsDtlsHisTO.getSrNo());
        cbsRelPersonsDtlsHisE.setPersonCustomerId(cbsRelPersonsDtlsHisTO.getPersonCustomerId());
        cbsRelPersonsDtlsHisE.setPersonFirstName(cbsRelPersonsDtlsHisTO.getPersonFirstName()== null ? "" : cbsRelPersonsDtlsHisTO.getPersonFirstName());
        cbsRelPersonsDtlsHisE.setPersonMiddleName(cbsRelPersonsDtlsHisTO.getPersonMiddleName()== null ? "" : cbsRelPersonsDtlsHisTO.getPersonMiddleName());

        cbsRelPersonsDtlsHisE.setPersonLastName(cbsRelPersonsDtlsHisTO.getPersonLastName()== null ? "" : cbsRelPersonsDtlsHisTO.getPersonLastName());
        cbsRelPersonsDtlsHisE.setRelationshipCode(cbsRelPersonsDtlsHisTO.getRelationshipCode());
        cbsRelPersonsDtlsHisE.setPersonPAN(cbsRelPersonsDtlsHisTO.getPan()== null ? "" : cbsRelPersonsDtlsHisTO.getPan());
        cbsRelPersonsDtlsHisE.setPersonUID(cbsRelPersonsDtlsHisTO.getUid()== null ? "" : cbsRelPersonsDtlsHisTO.getUid());
        cbsRelPersonsDtlsHisE.setPersonVoterId(cbsRelPersonsDtlsHisTO.getVoterId()== null ? "" : cbsRelPersonsDtlsHisTO.getVoterId());

        cbsRelPersonsDtlsHisE.setPersonNrega(cbsRelPersonsDtlsHisTO.getNrega()== null ? "" : cbsRelPersonsDtlsHisTO.getNrega());
        cbsRelPersonsDtlsHisE.setPersonDL(cbsRelPersonsDtlsHisTO.getDl()== null ? "" : cbsRelPersonsDtlsHisTO.getDl());
        cbsRelPersonsDtlsHisE.setPersonDLExpiry(cbsRelPersonsDtlsHisTO.getDlExpiry()== null ? "" :cbsRelPersonsDtlsHisTO.getDlExpiry());
        cbsRelPersonsDtlsHisE.setPersonPassportNo(cbsRelPersonsDtlsHisTO.getPassportNo()== null ? "" : cbsRelPersonsDtlsHisTO.getPassportNo());
        cbsRelPersonsDtlsHisE.setPersonPassportExpiry(cbsRelPersonsDtlsHisTO.getPassportExpiry()== null ? "" : cbsRelPersonsDtlsHisTO.getPassportExpiry());

        cbsRelPersonsDtlsHisE.setPersonDIN(cbsRelPersonsDtlsHisTO.getDin()== null ? "" : cbsRelPersonsDtlsHisTO.getDin());
        cbsRelPersonsDtlsHisE.setPersonpoliticalexposed(cbsRelPersonsDtlsHisTO.getExposed()== null ? "" : cbsRelPersonsDtlsHisTO.getExposed());
        cbsRelPersonsDtlsHisE.setPersonAdd1(cbsRelPersonsDtlsHisTO.getAdd1()== null ? "" : cbsRelPersonsDtlsHisTO.getAdd1());
        cbsRelPersonsDtlsHisE.setPersonAdd2(cbsRelPersonsDtlsHisTO.getAdd2()== null ? "" : cbsRelPersonsDtlsHisTO.getAdd2());
        cbsRelPersonsDtlsHisE.setPersonCity(cbsRelPersonsDtlsHisTO.getCity()== null ? "" : cbsRelPersonsDtlsHisTO.getCity());

        cbsRelPersonsDtlsHisE.setPersonState(cbsRelPersonsDtlsHisTO.getState()== null ? "" : cbsRelPersonsDtlsHisTO.getState());
        cbsRelPersonsDtlsHisE.setPersonCountry(cbsRelPersonsDtlsHisTO.getCountry()== null ? "" :cbsRelPersonsDtlsHisTO.getCountry());
        cbsRelPersonsDtlsHisE.setPersonPIN(cbsRelPersonsDtlsHisTO.getPin()== null ? "" : cbsRelPersonsDtlsHisTO.getPin());
        cbsRelPersonsDtlsHisE.setDelFlag(cbsRelPersonsDtlsHisTO.getDelFlag());
        cbsRelPersonsDtlsHisE.setDescriptipon(cbsRelPersonsDtlsHisTO.getDescriptipon()== null ? "" : cbsRelPersonsDtlsHisTO.getDescriptipon());

        cbsRelPersonsDtlsHisE.setLastChangeUserID(cbsRelPersonsDtlsHisTO.getLastChangeUserID());
        cbsRelPersonsDtlsHisE.setLastChangeTime(cbsRelPersonsDtlsHisTO.getLastChangeTime());
        cbsRelPersonsDtlsHisE.setRecordCreaterID(cbsRelPersonsDtlsHisTO.getRecordCreaterID());
        cbsRelPersonsDtlsHisE.setCreationTime(cbsRelPersonsDtlsHisTO.getCreationTime());
        cbsRelPersonsDtlsHisE.setTxnId(cbsRelPersonsDtlsHisTO.getTxnid());

        return cbsRelPersonsDtlsHisE;
    }

    public static CBSRelatedPersonsDetailsHisTO adaptToCBSRelatedPersonsDtlsHisTO(CbsRelatedPersonsDetailsHis cbsRelPersonsDtlsHis) {
        CBSRelatedPersonsDetailsHisTO cbsRelPersonsDtlsHisTo = new CBSRelatedPersonsDetailsHisTO();

        cbsRelPersonsDtlsHisTo.setCustomerId(cbsRelPersonsDtlsHis.getCustomerId());
        cbsRelPersonsDtlsHisTo.setSrNo(cbsRelPersonsDtlsHis.getPersonSrNo());
        cbsRelPersonsDtlsHisTo.setPersonCustomerId(cbsRelPersonsDtlsHis.getPersonCustomerId());
        cbsRelPersonsDtlsHisTo.setPersonFirstName(cbsRelPersonsDtlsHis.getPersonFirstName()== null ? "" : cbsRelPersonsDtlsHis.getPersonFirstName());
        cbsRelPersonsDtlsHisTo.setPersonMiddleName(cbsRelPersonsDtlsHis.getPersonMiddleName()== null ? "" : cbsRelPersonsDtlsHis.getPersonMiddleName());

        cbsRelPersonsDtlsHisTo.setPersonLastName(cbsRelPersonsDtlsHis.getPersonLastName()== null ? "" : cbsRelPersonsDtlsHis.getPersonLastName());
        cbsRelPersonsDtlsHisTo.setRelationshipCode(cbsRelPersonsDtlsHis.getRelationshipCode());
        cbsRelPersonsDtlsHisTo.setPan(cbsRelPersonsDtlsHis.getPersonPAN()== null ? "" : cbsRelPersonsDtlsHis.getPersonPAN());
        cbsRelPersonsDtlsHisTo.setUid(cbsRelPersonsDtlsHis.getPersonUID()== null ? "" : cbsRelPersonsDtlsHis.getPersonUID());
        cbsRelPersonsDtlsHisTo.setVoterId(cbsRelPersonsDtlsHis.getPersonVoterId()== null ? "" : cbsRelPersonsDtlsHis.getPersonVoterId());

        cbsRelPersonsDtlsHisTo.setNrega(cbsRelPersonsDtlsHis.getPersonNrega()== null ? "" : cbsRelPersonsDtlsHis.getPersonNrega());
        cbsRelPersonsDtlsHisTo.setDl(cbsRelPersonsDtlsHis.getPersonDL()== null ? "" : cbsRelPersonsDtlsHis.getPersonDL());
        cbsRelPersonsDtlsHisTo.setDlExpiry(cbsRelPersonsDtlsHis.getPersonDLExpiry()== null ? "" : cbsRelPersonsDtlsHis.getPersonDLExpiry());
        cbsRelPersonsDtlsHisTo.setPassportNo(cbsRelPersonsDtlsHis.getPersonPassportNo()== null ? "" : cbsRelPersonsDtlsHis.getPersonPassportNo());
        cbsRelPersonsDtlsHisTo.setPassportExpiry(cbsRelPersonsDtlsHis.getPersonPassportExpiry()== null ? "" : cbsRelPersonsDtlsHis.getPersonPassportExpiry());

        cbsRelPersonsDtlsHisTo.setDin(cbsRelPersonsDtlsHis.getPersonDIN()== null ? "" : cbsRelPersonsDtlsHis.getPersonDIN());
        cbsRelPersonsDtlsHisTo.setExposed(cbsRelPersonsDtlsHis.getPersonpoliticalexposed()== null ? "" :cbsRelPersonsDtlsHis.getPersonpoliticalexposed());
        cbsRelPersonsDtlsHisTo.setAdd1(cbsRelPersonsDtlsHis.getPersonAdd1()== null ? "" : cbsRelPersonsDtlsHis.getPersonAdd1());
        cbsRelPersonsDtlsHisTo.setAdd2(cbsRelPersonsDtlsHis.getPersonAdd2()== null ? "" : cbsRelPersonsDtlsHis.getPersonAdd2());
        cbsRelPersonsDtlsHisTo.setCity(cbsRelPersonsDtlsHis.getPersonCity()== null ? "" : cbsRelPersonsDtlsHis.getPersonCity());

        cbsRelPersonsDtlsHisTo.setState(cbsRelPersonsDtlsHis.getPersonState()== null ? "" : cbsRelPersonsDtlsHis.getPersonState());
        cbsRelPersonsDtlsHisTo.setCountry(cbsRelPersonsDtlsHis.getPersonCountry()== null ? "" :cbsRelPersonsDtlsHis.getPersonCountry());
        cbsRelPersonsDtlsHisTo.setPin(cbsRelPersonsDtlsHis.getPersonPIN()== null ? "" : cbsRelPersonsDtlsHis.getPersonPIN());
        cbsRelPersonsDtlsHisTo.setDelFlag(cbsRelPersonsDtlsHis.getDelFlag());
        cbsRelPersonsDtlsHisTo.setDescriptipon(cbsRelPersonsDtlsHis.getDescriptipon()==null ? "" : cbsRelPersonsDtlsHisTo.getDescriptipon());

        cbsRelPersonsDtlsHisTo.setLastChangeUserID(cbsRelPersonsDtlsHis.getLastChangeUserID());
        cbsRelPersonsDtlsHisTo.setLastChangeTime(cbsRelPersonsDtlsHis.getLastChangeTime());
        cbsRelPersonsDtlsHisTo.setRecordCreaterID(cbsRelPersonsDtlsHis.getRecordCreaterID());
        cbsRelPersonsDtlsHisTo.setCreationTime(cbsRelPersonsDtlsHis.getCreationTime());
        cbsRelPersonsDtlsHisTo.setTxnid(cbsRelPersonsDtlsHis.getTxnId());

        return cbsRelPersonsDtlsHisTo;
    }

    public static CbsRelatedPersonsDetailsPK adaptToCBSRelatedPersonsDetailsPKEntity(CBSRelatedPersonsDetailsPKTO cbsRelatedPersonsDetailsPKTO) {
        CbsRelatedPersonsDetailsPK cbsRelPersDtlsPKE = new CbsRelatedPersonsDetailsPK();

        cbsRelPersDtlsPKE.setCustomerId(cbsRelatedPersonsDetailsPKTO.getCustomerId());
        cbsRelPersDtlsPKE.setPersonSrNo(cbsRelatedPersonsDetailsPKTO.getPersonSrNo());

        return cbsRelPersDtlsPKE;
    }

    public static CBSRelatedPersonsDetailsPKTO adaptToCBSRelatedPersonsDetailsPKTO(CbsRelatedPersonsDetailsPK cbsRelatedPersonsDetailsPK) {
        CBSRelatedPersonsDetailsPKTO cbsRelPersDtlsPKTo = new CBSRelatedPersonsDetailsPKTO();

        cbsRelPersDtlsPKTo.setCustomerId(cbsRelatedPersonsDetailsPK.getCustomerId());
        cbsRelPersDtlsPKTo.setPersonSrNo(cbsRelatedPersonsDetailsPK.getPersonSrNo());

        return cbsRelPersDtlsPKTo;
    }

    public static CbsRelatedPersonsDetails adaptToCBSRelatedPersonsDetailsEntity(CBSRelatedPersonsDetailsTO cbsRelPersonsDtlsTO) {
        CbsRelatedPersonsDetails cbsRelPersonsDtlsE = new CbsRelatedPersonsDetails();

        cbsRelPersonsDtlsE.setCbsRelatedPersonsDetailsPK(ObjectAdaptorCustomer.adaptToCBSRelatedPersonsDetailsPKEntity(cbsRelPersonsDtlsTO.getcBSRelatedPersonsDetailsPKTO()));

        cbsRelPersonsDtlsE.setPersonCustomerId(cbsRelPersonsDtlsTO.getPersonCustomerId());
        cbsRelPersonsDtlsE.setPersonFirstName(cbsRelPersonsDtlsTO.getPersonFirstName()==null ? "" :cbsRelPersonsDtlsTO.getPersonFirstName());
        cbsRelPersonsDtlsE.setPersonMiddleName(cbsRelPersonsDtlsTO.getPersonMiddleName()==null ? "" :cbsRelPersonsDtlsTO.getPersonMiddleName());

        cbsRelPersonsDtlsE.setPersonLastName(cbsRelPersonsDtlsTO.getPersonLastName()==null ? "" :cbsRelPersonsDtlsTO.getPersonLastName());
        cbsRelPersonsDtlsE.setRelationshipCode(cbsRelPersonsDtlsTO.getRelationshipCode());
        cbsRelPersonsDtlsE.setPersonPAN(cbsRelPersonsDtlsTO.getPan()== null ? "" :cbsRelPersonsDtlsTO.getPan());
        cbsRelPersonsDtlsE.setPersonUID(cbsRelPersonsDtlsTO.getUid()== null ? "" :cbsRelPersonsDtlsTO.getUid());
        cbsRelPersonsDtlsE.setPersonVoterId(cbsRelPersonsDtlsTO.getVoterId()== null ? "" :cbsRelPersonsDtlsTO.getVoterId());

        cbsRelPersonsDtlsE.setPersonNrega(cbsRelPersonsDtlsTO.getNrega()== null ? "" :cbsRelPersonsDtlsTO.getNrega());
        cbsRelPersonsDtlsE.setPersonDL(cbsRelPersonsDtlsTO.getDl()== null ? "" : cbsRelPersonsDtlsTO.getDl());
        cbsRelPersonsDtlsE.setPersonDLExpiry(cbsRelPersonsDtlsTO.getDlExpiry()== null ? "" : cbsRelPersonsDtlsTO.getDlExpiry());
        cbsRelPersonsDtlsE.setPersonPassportNo(cbsRelPersonsDtlsTO.getPassportNo()== null ? "" :cbsRelPersonsDtlsTO.getPassportNo());
        cbsRelPersonsDtlsE.setPersonPassportExpiry(cbsRelPersonsDtlsTO.getPassportExpiry()==null ? "" : cbsRelPersonsDtlsTO.getPassportExpiry());

        cbsRelPersonsDtlsE.setPersonDIN(cbsRelPersonsDtlsTO.getDin()== null ? "" : cbsRelPersonsDtlsTO.getDin());
        cbsRelPersonsDtlsE.setPersonpoliticalexposed(cbsRelPersonsDtlsTO.getExposed()==null ? "" : cbsRelPersonsDtlsTO.getExposed());
        cbsRelPersonsDtlsE.setPersonAdd1(cbsRelPersonsDtlsTO.getAdd1()== null ? "" : cbsRelPersonsDtlsTO.getAdd1());
        cbsRelPersonsDtlsE.setPersonAdd2(cbsRelPersonsDtlsTO.getAdd2()== null ? "" : cbsRelPersonsDtlsTO.getAdd2());
        cbsRelPersonsDtlsE.setPersonCity(cbsRelPersonsDtlsTO.getCity()== null ? "" : cbsRelPersonsDtlsTO.getCity());

        cbsRelPersonsDtlsE.setPersonState(cbsRelPersonsDtlsTO.getState()== null ? "" : cbsRelPersonsDtlsTO.getState());
        cbsRelPersonsDtlsE.setPersonCountry(cbsRelPersonsDtlsTO.getCountry()==null ? "" : cbsRelPersonsDtlsTO.getCountry());
        cbsRelPersonsDtlsE.setPersonPIN(cbsRelPersonsDtlsTO.getPin() == null ? "" : cbsRelPersonsDtlsTO.getPin());
        cbsRelPersonsDtlsE.setDelFlag(cbsRelPersonsDtlsTO.getDelFlag());
        cbsRelPersonsDtlsE.setDescriptipon(cbsRelPersonsDtlsTO.getDescriptipon()== null ? "" : cbsRelPersonsDtlsTO.getDescriptipon());

        cbsRelPersonsDtlsE.setLastChangeUserID(cbsRelPersonsDtlsTO.getLastChangeUserID());
        cbsRelPersonsDtlsE.setLastChangeTime(cbsRelPersonsDtlsTO.getLastChangeTime());
        cbsRelPersonsDtlsE.setRecordCreaterID(cbsRelPersonsDtlsTO.getRecordCreaterID());
        cbsRelPersonsDtlsE.setCreationTime(cbsRelPersonsDtlsTO.getCreationTime());
        cbsRelPersonsDtlsE.setTsCnt(cbsRelPersonsDtlsTO.getTsCnt()== null ? "" : cbsRelPersonsDtlsTO.getTsCnt());

        return cbsRelPersonsDtlsE;
    }

    public static CBSRelatedPersonsDetailsTO adaptToCBSRelatedPersonsDetailsTO(CbsRelatedPersonsDetails cbsRelPersonsDtls) {
        CBSRelatedPersonsDetailsTO cbsRelPersonsDtlsTo = new CBSRelatedPersonsDetailsTO();

        cbsRelPersonsDtlsTo.setcBSRelatedPersonsDetailsPKTO(ObjectAdaptorCustomer.adaptToCBSRelatedPersonsDetailsPKTO(cbsRelPersonsDtls.getCbsRelatedPersonsDetailsPK()));
//
        cbsRelPersonsDtlsTo.setPersonCustomerId(cbsRelPersonsDtls.getPersonCustomerId());
        cbsRelPersonsDtlsTo.setPersonFirstName(cbsRelPersonsDtls.getPersonFirstName()==null ? "" : cbsRelPersonsDtls.getPersonFirstName());
        cbsRelPersonsDtlsTo.setPersonMiddleName(cbsRelPersonsDtls.getPersonMiddleName()== null ? "" : cbsRelPersonsDtls.getPersonMiddleName());

        cbsRelPersonsDtlsTo.setPersonLastName(cbsRelPersonsDtls.getPersonLastName()== null ? "" : cbsRelPersonsDtls.getPersonLastName());
        cbsRelPersonsDtlsTo.setRelationshipCode(cbsRelPersonsDtls.getRelationshipCode());
        cbsRelPersonsDtlsTo.setPan(cbsRelPersonsDtls.getPersonPAN()== null ? "" : cbsRelPersonsDtls.getPersonPAN());
        cbsRelPersonsDtlsTo.setUid(cbsRelPersonsDtls.getPersonUID()== null ? "" : cbsRelPersonsDtls.getPersonUID());
        cbsRelPersonsDtlsTo.setVoterId(cbsRelPersonsDtls.getPersonVoterId()== null ? "" : cbsRelPersonsDtls.getPersonVoterId());

        cbsRelPersonsDtlsTo.setNrega(cbsRelPersonsDtls.getPersonNrega()==null ? "" : cbsRelPersonsDtls.getPersonNrega());
        cbsRelPersonsDtlsTo.setDl(cbsRelPersonsDtls.getPersonDL()== null ? "" : cbsRelPersonsDtls.getPersonDL());
        cbsRelPersonsDtlsTo.setDlExpiry(cbsRelPersonsDtls.getPersonDLExpiry()== null ? "" : cbsRelPersonsDtls.getPersonDLExpiry());
        cbsRelPersonsDtlsTo.setPassportNo(cbsRelPersonsDtls.getPersonPassportNo()== null ? "" : cbsRelPersonsDtls.getPersonPassportNo());
        cbsRelPersonsDtlsTo.setPassportExpiry(cbsRelPersonsDtls.getPersonPassportExpiry()== null ? "" : cbsRelPersonsDtls.getPersonPassportExpiry());

        cbsRelPersonsDtlsTo.setDin(cbsRelPersonsDtls.getPersonDIN()== null ? "" : cbsRelPersonsDtls.getPersonDIN());
        cbsRelPersonsDtlsTo.setExposed(cbsRelPersonsDtls.getPersonpoliticalexposed()== null ? "" : cbsRelPersonsDtls.getPersonpoliticalexposed());
        cbsRelPersonsDtlsTo.setAdd1(cbsRelPersonsDtls.getPersonAdd1()== null ? "" : cbsRelPersonsDtls.getPersonAdd1());
        cbsRelPersonsDtlsTo.setAdd2(cbsRelPersonsDtls.getPersonAdd2()== null ? "" : cbsRelPersonsDtls.getPersonAdd2());
        cbsRelPersonsDtlsTo.setCity(cbsRelPersonsDtls.getPersonCity()== null ? "" : cbsRelPersonsDtls.getPersonCity());

        cbsRelPersonsDtlsTo.setState(cbsRelPersonsDtls.getPersonState()== null ? "" : cbsRelPersonsDtls.getPersonState());
        cbsRelPersonsDtlsTo.setCountry(cbsRelPersonsDtls.getPersonCountry()== null ? "" : cbsRelPersonsDtls.getPersonCountry());
        cbsRelPersonsDtlsTo.setPin(cbsRelPersonsDtls.getPersonPIN()== null ? "" : cbsRelPersonsDtls.getPersonPIN());
        cbsRelPersonsDtlsTo.setDelFlag(cbsRelPersonsDtls.getDelFlag());
        cbsRelPersonsDtlsTo.setDescriptipon(cbsRelPersonsDtls.getDescriptipon()== null ? "" : cbsRelPersonsDtls.getDescriptipon());

        cbsRelPersonsDtlsTo.setLastChangeUserID(cbsRelPersonsDtls.getLastChangeUserID());
        cbsRelPersonsDtlsTo.setLastChangeTime(cbsRelPersonsDtls.getLastChangeTime());
        cbsRelPersonsDtlsTo.setRecordCreaterID(cbsRelPersonsDtls.getRecordCreaterID());
        cbsRelPersonsDtlsTo.setCreationTime(cbsRelPersonsDtls.getCreationTime());
        cbsRelPersonsDtlsTo.setTsCnt(cbsRelPersonsDtls.getTsCnt()==null ? "" : cbsRelPersonsDtlsTo.getTsCnt());

        return cbsRelPersonsDtlsTo;
    }

    public static CBSTradeFinanceInformationHis adaptToCBSTradeFinInfoHisEntity(CBSTradeFinanceInformationHisTO cbsTradeFinInfoHisTO) {
        CBSTradeFinanceInformationHis cbsTradeFinInfoHisE = new CBSTradeFinanceInformationHis();

        cbsTradeFinInfoHisE.setCustomerId(cbsTradeFinInfoHisTO.getCustomerId());
        cbsTradeFinInfoHisE.setName(cbsTradeFinInfoHisTO.getName());
        cbsTradeFinInfoHisE.setAddressLine1(cbsTradeFinInfoHisTO.getAddressLine1());

        cbsTradeFinInfoHisE.setAddressLine2(cbsTradeFinInfoHisTO.getAddressLine2());
        cbsTradeFinInfoHisE.setCityCode(cbsTradeFinInfoHisTO.getCityCode());
        cbsTradeFinInfoHisE.setPostalCode(cbsTradeFinInfoHisTO.getPostalCode());

        cbsTradeFinInfoHisE.setCountryCode(cbsTradeFinInfoHisTO.getCountryCode());
        cbsTradeFinInfoHisE.setPhoneNumber(cbsTradeFinInfoHisTO.getPhoneNumber());
        cbsTradeFinInfoHisE.setFaxNumber(cbsTradeFinInfoHisTO.getFaxNumber());

        cbsTradeFinInfoHisE.setTelexNumber(cbsTradeFinInfoHisTO.getTelexNumber());
        cbsTradeFinInfoHisE.setNative1(cbsTradeFinInfoHisTO.getNative1());
        cbsTradeFinInfoHisE.setInlandTradeAllowed(cbsTradeFinInfoHisTO.getInlandTradeAllowed());

        cbsTradeFinInfoHisE.setReviewDate(cbsTradeFinInfoHisTO.getReviewDate());
        cbsTradeFinInfoHisE.setIndustryType(cbsTradeFinInfoHisTO.getIndustryType());
        cbsTradeFinInfoHisE.setExportUnitFlag(cbsTradeFinInfoHisTO.getExportUnitFlag());

        cbsTradeFinInfoHisE.setStatus(cbsTradeFinInfoHisTO.getStatus());
        cbsTradeFinInfoHisE.setTradeExpCode(cbsTradeFinInfoHisTO.getTradeExpCode());
        cbsTradeFinInfoHisE.setPartyConstitution(cbsTradeFinInfoHisTO.getPartyConstitution());

        cbsTradeFinInfoHisE.setSpecialParty(cbsTradeFinInfoHisTO.getSpecialParty());
        cbsTradeFinInfoHisE.setPartyType(cbsTradeFinInfoHisTO.getPartyType());
        cbsTradeFinInfoHisE.setProductionCycle(cbsTradeFinInfoHisTO.getProductionCycle());

        cbsTradeFinInfoHisE.setCodeGivenByCentralBANK(cbsTradeFinInfoHisTO.getCodeGivenByCentralBANK());
        cbsTradeFinInfoHisE.setCodeGivenByTradeAuthority(cbsTradeFinInfoHisTO.getCodeGivenByTradeAuthority());
        cbsTradeFinInfoHisE.setType(cbsTradeFinInfoHisTO.getType());

        cbsTradeFinInfoHisE.setForwardContractLimit(cbsTradeFinInfoHisTO.getForwardContractLimit());
        cbsTradeFinInfoHisE.setFCMargin(cbsTradeFinInfoHisTO.getfCMargin());
        cbsTradeFinInfoHisE.setFCSanctioningAuthority(cbsTradeFinInfoHisTO.getfCSanctioningAuthority());

        cbsTradeFinInfoHisE.setFCSanctionExpiryDate(cbsTradeFinInfoHisTO.getfCSanctionExpiryDate());
        cbsTradeFinInfoHisE.setFCNextNumberCode(cbsTradeFinInfoHisTO.getfCNextNumberCode());
        cbsTradeFinInfoHisE.setDocumentCreditLimit(cbsTradeFinInfoHisTO.getDocumentCreditLimit());

        cbsTradeFinInfoHisE.setDCMargin(cbsTradeFinInfoHisTO.getdCMargin());
        cbsTradeFinInfoHisE.setDCSanctioningAuthority(cbsTradeFinInfoHisTO.getdCSanctioningAuthority());
        cbsTradeFinInfoHisE.setDCSanctionExpiryDate(cbsTradeFinInfoHisTO.getdCSanctionExpiryDate());

        cbsTradeFinInfoHisE.setDCNextNumberCode(cbsTradeFinInfoHisTO.getdCNextNumberCode());
        cbsTradeFinInfoHisE.setPackingContractLimit(cbsTradeFinInfoHisTO.getPackingContractLimit());
        cbsTradeFinInfoHisE.setPCMargin(cbsTradeFinInfoHisTO.getpCMargin());

        cbsTradeFinInfoHisE.setPCSanctioningAuthority(cbsTradeFinInfoHisTO.getpCSanctioningAuthority());
        cbsTradeFinInfoHisE.setPCSanctionExpiryDate(cbsTradeFinInfoHisTO.getpCSanctionExpiryDate());
        cbsTradeFinInfoHisE.setPCNextNumberCode(cbsTradeFinInfoHisTO.getpCNextNumberCode());

        cbsTradeFinInfoHisE.setBankGuaranteeLimit(cbsTradeFinInfoHisTO.getBankGuaranteeLimit());
        cbsTradeFinInfoHisE.setBGMargin(cbsTradeFinInfoHisTO.getbGMargin());
        cbsTradeFinInfoHisE.setBGSanctioningAuthority(cbsTradeFinInfoHisTO.getbGSanctioningAuthority());

        cbsTradeFinInfoHisE.setBGSanctionExpiryDate(cbsTradeFinInfoHisTO.getbGSanctionExpiryDate());
        cbsTradeFinInfoHisE.setBGNextNumberCode(cbsTradeFinInfoHisTO.getbGNextNumberCode());
        cbsTradeFinInfoHisE.setPresentOutstandingLiability(cbsTradeFinInfoHisTO.getPresentOutstandingLiability());

        cbsTradeFinInfoHisE.setLastChangeUserID(cbsTradeFinInfoHisTO.getLastChangeUserID());
        cbsTradeFinInfoHisE.setLastChangeTime(cbsTradeFinInfoHisTO.getLastChangeTime());
        cbsTradeFinInfoHisE.setRecordCreaterID(cbsTradeFinInfoHisTO.getRecordCreaterID());

        cbsTradeFinInfoHisE.setTxnid(cbsTradeFinInfoHisTO.getTxnid());

        return cbsTradeFinInfoHisE;
    }

    public static CBSTradeFinanceInformationHisTO adaptToCBSTradeFinInfoHisTO(CBSTradeFinanceInformationHis cbsTradeFinInfoHis) {
        CBSTradeFinanceInformationHisTO cbsTradeFinInfoHisTo = new CBSTradeFinanceInformationHisTO();

        cbsTradeFinInfoHisTo.setCustomerId(cbsTradeFinInfoHis.getCustomerId());
        cbsTradeFinInfoHisTo.setName(cbsTradeFinInfoHis.getName());
        cbsTradeFinInfoHisTo.setAddressLine1(cbsTradeFinInfoHis.getAddressLine1());

        cbsTradeFinInfoHisTo.setAddressLine2(cbsTradeFinInfoHis.getAddressLine2());
        cbsTradeFinInfoHisTo.setCityCode(cbsTradeFinInfoHis.getCityCode());
        cbsTradeFinInfoHisTo.setPostalCode(cbsTradeFinInfoHis.getPostalCode());

        cbsTradeFinInfoHisTo.setCountryCode(cbsTradeFinInfoHis.getCountryCode());
        cbsTradeFinInfoHisTo.setPhoneNumber(cbsTradeFinInfoHis.getPhoneNumber());
        cbsTradeFinInfoHisTo.setFaxNumber(cbsTradeFinInfoHis.getFaxNumber());

        cbsTradeFinInfoHisTo.setTelexNumber(cbsTradeFinInfoHis.getTelexNumber());
        cbsTradeFinInfoHisTo.setNative1(cbsTradeFinInfoHis.getNative1());
        cbsTradeFinInfoHisTo.setInlandTradeAllowed(cbsTradeFinInfoHis.getInlandTradeAllowed());

        cbsTradeFinInfoHisTo.setReviewDate(cbsTradeFinInfoHis.getReviewDate());
        cbsTradeFinInfoHisTo.setIndustryType(cbsTradeFinInfoHis.getIndustryType());
        cbsTradeFinInfoHisTo.setExportUnitFlag(cbsTradeFinInfoHis.getExportUnitFlag());

        cbsTradeFinInfoHisTo.setStatus(cbsTradeFinInfoHis.getStatus());
        cbsTradeFinInfoHisTo.setTradeExpCode(cbsTradeFinInfoHis.getTradeExpCode());
        cbsTradeFinInfoHisTo.setPartyConstitution(cbsTradeFinInfoHis.getPartyConstitution());

        cbsTradeFinInfoHisTo.setSpecialParty(cbsTradeFinInfoHis.getSpecialParty());
        cbsTradeFinInfoHisTo.setPartyType(cbsTradeFinInfoHis.getPartyType());
        cbsTradeFinInfoHisTo.setProductionCycle(cbsTradeFinInfoHis.getProductionCycle());

        cbsTradeFinInfoHisTo.setCodeGivenByCentralBANK(cbsTradeFinInfoHis.getCodeGivenByCentralBANK());
        cbsTradeFinInfoHisTo.setCodeGivenByTradeAuthority(cbsTradeFinInfoHis.getCodeGivenByTradeAuthority());
        cbsTradeFinInfoHisTo.setType(cbsTradeFinInfoHis.getType());

        cbsTradeFinInfoHisTo.setForwardContractLimit(cbsTradeFinInfoHis.getForwardContractLimit());
        cbsTradeFinInfoHisTo.setfCMargin(cbsTradeFinInfoHis.getFCMargin());
        cbsTradeFinInfoHisTo.setfCSanctioningAuthority(cbsTradeFinInfoHis.getFCSanctioningAuthority());

        cbsTradeFinInfoHisTo.setfCSanctionExpiryDate(cbsTradeFinInfoHis.getFCSanctionExpiryDate());
        cbsTradeFinInfoHisTo.setfCNextNumberCode(cbsTradeFinInfoHis.getFCNextNumberCode());
        cbsTradeFinInfoHisTo.setDocumentCreditLimit(cbsTradeFinInfoHis.getDocumentCreditLimit());

        cbsTradeFinInfoHisTo.setdCMargin(cbsTradeFinInfoHis.getDCMargin());
        cbsTradeFinInfoHisTo.setdCSanctioningAuthority(cbsTradeFinInfoHis.getDCSanctioningAuthority());
        cbsTradeFinInfoHisTo.setdCSanctionExpiryDate(cbsTradeFinInfoHis.getDCSanctionExpiryDate());

        cbsTradeFinInfoHisTo.setdCNextNumberCode(cbsTradeFinInfoHis.getDCNextNumberCode());
        cbsTradeFinInfoHisTo.setPackingContractLimit(cbsTradeFinInfoHis.getPackingContractLimit());
        cbsTradeFinInfoHisTo.setpCMargin(cbsTradeFinInfoHis.getPCMargin());

        cbsTradeFinInfoHisTo.setpCSanctioningAuthority(cbsTradeFinInfoHis.getPCSanctioningAuthority());
        cbsTradeFinInfoHisTo.setpCSanctionExpiryDate(cbsTradeFinInfoHis.getPCSanctionExpiryDate());
        cbsTradeFinInfoHisTo.setpCNextNumberCode(cbsTradeFinInfoHis.getPCNextNumberCode());

        cbsTradeFinInfoHisTo.setBankGuaranteeLimit(cbsTradeFinInfoHis.getBankGuaranteeLimit());
        cbsTradeFinInfoHisTo.setbGMargin(cbsTradeFinInfoHis.getBGMargin());
        cbsTradeFinInfoHisTo.setbGSanctioningAuthority(cbsTradeFinInfoHis.getBGSanctioningAuthority());

        cbsTradeFinInfoHisTo.setbGSanctionExpiryDate(cbsTradeFinInfoHis.getBGSanctionExpiryDate());
        cbsTradeFinInfoHisTo.setbGNextNumberCode(cbsTradeFinInfoHis.getBGNextNumberCode());
        cbsTradeFinInfoHisTo.setPresentOutstandingLiability(cbsTradeFinInfoHis.getPresentOutstandingLiability());

        cbsTradeFinInfoHisTo.setLastChangeUserID(cbsTradeFinInfoHis.getLastChangeUserID());
        cbsTradeFinInfoHisTo.setLastChangeTime(cbsTradeFinInfoHis.getLastChangeTime());
        cbsTradeFinInfoHisTo.setRecordCreaterID(cbsTradeFinInfoHis.getRecordCreaterID());

        cbsTradeFinInfoHisTo.setTxnid(cbsTradeFinInfoHis.getTxnid());

        return cbsTradeFinInfoHisTo;
    }

    public static CBSTradeFinanceInformation adaptToCBSTradeFinInfoEntity(CBSTradeFinanceInformationTO cbsTradeFinInfoTO) {
        CBSTradeFinanceInformation cbsTradeFinInfoE = new CBSTradeFinanceInformation();

        cbsTradeFinInfoE.setCustomerId(cbsTradeFinInfoTO.getCustomerId());
        cbsTradeFinInfoE.setName(cbsTradeFinInfoTO.getName());
        cbsTradeFinInfoE.setAddressLine1(cbsTradeFinInfoTO.getAddressLine1());

        cbsTradeFinInfoE.setAddressLine2(cbsTradeFinInfoTO.getAddressLine2());
        cbsTradeFinInfoE.setCityCode(cbsTradeFinInfoTO.getCityCode());
        cbsTradeFinInfoE.setPostalCode(cbsTradeFinInfoTO.getPostalCode());

        cbsTradeFinInfoE.setCountryCode(cbsTradeFinInfoTO.getCountryCode());
        cbsTradeFinInfoE.setPhoneNumber(cbsTradeFinInfoTO.getPhoneNumber());
        cbsTradeFinInfoE.setFaxNumber(cbsTradeFinInfoTO.getFaxNumber());

        cbsTradeFinInfoE.setTelexNumber(cbsTradeFinInfoTO.getTelexNumber());
        cbsTradeFinInfoE.setNative1(cbsTradeFinInfoTO.getNative1());
        cbsTradeFinInfoE.setInlandTradeAllowed(cbsTradeFinInfoTO.getInlandTradeAllowed());

        cbsTradeFinInfoE.setReviewDate(cbsTradeFinInfoTO.getReviewDate());
        cbsTradeFinInfoE.setIndustryType(cbsTradeFinInfoTO.getIndustryType());
        cbsTradeFinInfoE.setExportUnitFlag(cbsTradeFinInfoTO.getExportUnitFlag());

        cbsTradeFinInfoE.setStatus(cbsTradeFinInfoTO.getStatus());
        cbsTradeFinInfoE.setTradeExpCode(cbsTradeFinInfoTO.getTradeExpCode());
        cbsTradeFinInfoE.setPartyConstitution(cbsTradeFinInfoTO.getPartyConstitution());

        cbsTradeFinInfoE.setSpecialParty(cbsTradeFinInfoTO.getSpecialParty());
        cbsTradeFinInfoE.setPartyType(cbsTradeFinInfoTO.getPartyType());
        cbsTradeFinInfoE.setProductionCycle(cbsTradeFinInfoTO.getProductionCycle());

        cbsTradeFinInfoE.setCodeGivenByCentralBANK(cbsTradeFinInfoTO.getCodeGivenByCentralBANK());
        cbsTradeFinInfoE.setCodeGivenByTradeAuthority(cbsTradeFinInfoTO.getCodeGivenByTradeAuthority());
        cbsTradeFinInfoE.setType(cbsTradeFinInfoTO.getType());

        cbsTradeFinInfoE.setForwardContractLimit(cbsTradeFinInfoTO.getForwardContractLimit());
        cbsTradeFinInfoE.setFCMargin(cbsTradeFinInfoTO.getfCMargin());
        cbsTradeFinInfoE.setFCSanctioningAuthority(cbsTradeFinInfoTO.getfCSanctioningAuthority());

        cbsTradeFinInfoE.setFCSanctionExpiryDate(cbsTradeFinInfoTO.getfCSanctionExpiryDate());
        cbsTradeFinInfoE.setFCNextNumberCode(cbsTradeFinInfoTO.getfCNextNumberCode());
        cbsTradeFinInfoE.setDocumentCreditLimit(cbsTradeFinInfoTO.getDocumentCreditLimit());

        cbsTradeFinInfoE.setDCMargin(cbsTradeFinInfoTO.getdCMargin());
        cbsTradeFinInfoE.setDCSanctioningAuthority(cbsTradeFinInfoTO.getdCSanctioningAuthority());
        cbsTradeFinInfoE.setDCSanctionExpiryDate(cbsTradeFinInfoTO.getdCSanctionExpiryDate());

        cbsTradeFinInfoE.setDCNextNumberCode(cbsTradeFinInfoTO.getdCNextNumberCode());
        cbsTradeFinInfoE.setPackingContractLimit(cbsTradeFinInfoTO.getPackingContractLimit());
        cbsTradeFinInfoE.setPCMargin(cbsTradeFinInfoTO.getpCMargin());

        cbsTradeFinInfoE.setPCSanctioningAuthority(cbsTradeFinInfoTO.getpCSanctioningAuthority());
        cbsTradeFinInfoE.setPCSanctionExpiryDate(cbsTradeFinInfoTO.getpCSanctionExpiryDate());
        cbsTradeFinInfoE.setPCNextNumberCode(cbsTradeFinInfoTO.getpCNextNumberCode());

        cbsTradeFinInfoE.setBankGuaranteeLimit(cbsTradeFinInfoTO.getBankGuaranteeLimit());
        cbsTradeFinInfoE.setBGMargin(cbsTradeFinInfoTO.getbGMargin());
        cbsTradeFinInfoE.setBGSanctioningAuthority(cbsTradeFinInfoTO.getbGSanctioningAuthority());

        cbsTradeFinInfoE.setBGSanctionExpiryDate(cbsTradeFinInfoTO.getbGSanctionExpiryDate());
        cbsTradeFinInfoE.setBGNextNumberCode(cbsTradeFinInfoTO.getbGNextNumberCode());
        cbsTradeFinInfoE.setPresentOutstandingLiability(cbsTradeFinInfoTO.getPresentOutstandingLiability());

        cbsTradeFinInfoE.setLastChangeUserID(cbsTradeFinInfoTO.getLastChangeUserID());
        cbsTradeFinInfoE.setLastChangeTime(cbsTradeFinInfoTO.getLastChangeTime());
        cbsTradeFinInfoE.setRecordCreaterID(cbsTradeFinInfoTO.getRecordCreaterID());

        cbsTradeFinInfoE.setCreationTime(cbsTradeFinInfoTO.getCreationTime());
        cbsTradeFinInfoE.setTsCnt(cbsTradeFinInfoTO.getTsCnt());

        return cbsTradeFinInfoE;
    }

    public static CBSTradeFinanceInformationTO adaptToCBSTradeFinInfoTO(CBSTradeFinanceInformation cbsTradeFinInfo) {
        CBSTradeFinanceInformationTO cbsTradeFinInfoTo = new CBSTradeFinanceInformationTO();

        cbsTradeFinInfoTo.setCustomerId(cbsTradeFinInfo.getCustomerId());
        cbsTradeFinInfoTo.setName(cbsTradeFinInfo.getName());
        cbsTradeFinInfoTo.setAddressLine1(cbsTradeFinInfo.getAddressLine1());

        cbsTradeFinInfoTo.setAddressLine2(cbsTradeFinInfo.getAddressLine2());
        cbsTradeFinInfoTo.setCityCode(cbsTradeFinInfo.getCityCode());
        cbsTradeFinInfoTo.setPostalCode(cbsTradeFinInfo.getPostalCode());

        cbsTradeFinInfoTo.setCountryCode(cbsTradeFinInfo.getCountryCode());
        cbsTradeFinInfoTo.setPhoneNumber(cbsTradeFinInfo.getPhoneNumber());
        cbsTradeFinInfoTo.setFaxNumber(cbsTradeFinInfo.getFaxNumber());

        cbsTradeFinInfoTo.setTelexNumber(cbsTradeFinInfo.getTelexNumber());
        cbsTradeFinInfoTo.setNative1(cbsTradeFinInfo.getNative1());
        cbsTradeFinInfoTo.setInlandTradeAllowed(cbsTradeFinInfo.getInlandTradeAllowed());

        cbsTradeFinInfoTo.setReviewDate(cbsTradeFinInfo.getReviewDate());
        cbsTradeFinInfoTo.setIndustryType(cbsTradeFinInfo.getIndustryType());
        cbsTradeFinInfoTo.setExportUnitFlag(cbsTradeFinInfo.getExportUnitFlag());

        cbsTradeFinInfoTo.setStatus(cbsTradeFinInfo.getStatus());
        cbsTradeFinInfoTo.setTradeExpCode(cbsTradeFinInfo.getTradeExpCode());
        cbsTradeFinInfoTo.setPartyConstitution(cbsTradeFinInfo.getPartyConstitution());

        cbsTradeFinInfoTo.setSpecialParty(cbsTradeFinInfo.getSpecialParty());
        cbsTradeFinInfoTo.setPartyType(cbsTradeFinInfo.getPartyType());
        cbsTradeFinInfoTo.setProductionCycle(cbsTradeFinInfo.getProductionCycle());

        cbsTradeFinInfoTo.setCodeGivenByCentralBANK(cbsTradeFinInfo.getCodeGivenByCentralBANK());
        cbsTradeFinInfoTo.setCodeGivenByTradeAuthority(cbsTradeFinInfo.getCodeGivenByTradeAuthority());
        cbsTradeFinInfoTo.setType(cbsTradeFinInfo.getType());

        cbsTradeFinInfoTo.setForwardContractLimit(cbsTradeFinInfo.getForwardContractLimit());
        cbsTradeFinInfoTo.setfCMargin(cbsTradeFinInfo.getFCMargin());
        cbsTradeFinInfoTo.setfCSanctioningAuthority(cbsTradeFinInfo.getFCSanctioningAuthority());

        cbsTradeFinInfoTo.setfCSanctionExpiryDate(cbsTradeFinInfo.getFCSanctionExpiryDate());
        cbsTradeFinInfoTo.setfCNextNumberCode(cbsTradeFinInfo.getFCNextNumberCode());
        cbsTradeFinInfoTo.setDocumentCreditLimit(cbsTradeFinInfo.getDocumentCreditLimit());

        cbsTradeFinInfoTo.setdCMargin(cbsTradeFinInfo.getDCMargin());
        cbsTradeFinInfoTo.setdCSanctioningAuthority(cbsTradeFinInfo.getDCSanctioningAuthority());
        cbsTradeFinInfoTo.setdCSanctionExpiryDate(cbsTradeFinInfo.getDCSanctionExpiryDate());

        cbsTradeFinInfoTo.setdCNextNumberCode(cbsTradeFinInfo.getDCNextNumberCode());
        cbsTradeFinInfoTo.setPackingContractLimit(cbsTradeFinInfo.getPackingContractLimit());
        cbsTradeFinInfoTo.setpCMargin(cbsTradeFinInfo.getPCMargin());

        cbsTradeFinInfoTo.setpCSanctioningAuthority(cbsTradeFinInfo.getPCSanctioningAuthority());
        cbsTradeFinInfoTo.setpCSanctionExpiryDate(cbsTradeFinInfo.getPCSanctionExpiryDate());
        cbsTradeFinInfoTo.setpCNextNumberCode(cbsTradeFinInfo.getPCNextNumberCode());

        cbsTradeFinInfoTo.setBankGuaranteeLimit(cbsTradeFinInfo.getBankGuaranteeLimit());
        cbsTradeFinInfoTo.setbGMargin(cbsTradeFinInfo.getBGMargin());
        cbsTradeFinInfoTo.setbGSanctioningAuthority(cbsTradeFinInfo.getBGSanctioningAuthority());

        cbsTradeFinInfoTo.setbGSanctionExpiryDate(cbsTradeFinInfo.getBGSanctionExpiryDate());
        cbsTradeFinInfoTo.setbGNextNumberCode(cbsTradeFinInfo.getBGNextNumberCode());
        cbsTradeFinInfoTo.setPresentOutstandingLiability(cbsTradeFinInfo.getPresentOutstandingLiability());

        cbsTradeFinInfoTo.setLastChangeUserID(cbsTradeFinInfo.getLastChangeUserID());
        cbsTradeFinInfoTo.setLastChangeTime(cbsTradeFinInfo.getLastChangeTime());
        cbsTradeFinInfoTo.setRecordCreaterID(cbsTradeFinInfo.getRecordCreaterID());

        cbsTradeFinInfoTo.setCreationTime(cbsTradeFinInfo.getCreationTime());
        cbsTradeFinInfoTo.setTsCnt(cbsTradeFinInfo.getTsCnt());

        return cbsTradeFinInfoTo;
    }

    public static CodebookPK adaptToCodebookPKEntity(CodebookPKTO codebookPKTO) {
        CodebookPK codebookPKE = new CodebookPK();

        codebookPKE.setGroupCode(codebookPKTO.getGroupCode());
        codebookPKE.setCode(codebookPKTO.getCode());

        return codebookPKE;
    }

    public static CodebookPKTO adaptToCodebookPKTO(CodebookPK codebookPK) {
        CodebookPKTO codebookPKTo = new CodebookPKTO();

        codebookPKTo.setGroupCode(codebookPK.getGroupCode());
        codebookPKTo.setCode(codebookPK.getCode());

        return codebookPKTo;
    }

    public static Codebook adaptToCodebookEntity(CodebookTO codebookTO) {
        Codebook codebookE = new Codebook();

        codebookE.setCodebookPK(ObjectAdaptorCustomer.adaptToCodebookPKEntity(codebookTO.getCodebookPKTO()));
        codebookE.setDescription(codebookTO.getDescription());

        return codebookE;
    }

    public static CodebookTO adaptToCodebookTO(Codebook codebook) {
        CodebookTO codebookTo = new CodebookTO();

        codebookTo.setCodebookPKTO(ObjectAdaptorCustomer.adaptToCodebookPKTO(codebook.getCodebookPK()));
        codebookTo.setDescription(codebook.getDescription());

        return codebookTo;
    }

    public static CustomermasterPK adaptToCustomerMasterPKEntity(CustomerMasterPKTO customerMasterPKTO) {
        CustomermasterPK customerMasterPKE = new CustomermasterPK();

        customerMasterPKE.setCustno(customerMasterPKTO.getCustNo());
        customerMasterPKE.setActype(customerMasterPKTO.getAcType());
        customerMasterPKE.setBrncode(customerMasterPKTO.getBrncode());

        return customerMasterPKE;
    }

    public static CustomerMasterPKTO adaptToCustomerMasterPKTO(CustomermasterPK customerMasterPK) {
        CustomerMasterPKTO customerMasterPKTo = new CustomerMasterPKTO();

        customerMasterPKTo.setCustNo(customerMasterPK.getCustno());
        customerMasterPKTo.setAcType(customerMasterPK.getActype());
        customerMasterPKTo.setAcType(customerMasterPK.getAgcode());
        customerMasterPKTo.setBrncode(customerMasterPK.getBrncode());

        return customerMasterPKTo;
    }

    public static Customermaster adaptToCustomerMasterEntity(CustomerMasterTO customerMasterTO) {
        Customermaster customerMasterE = new Customermaster();

        customerMasterE.setCustomermasterPK(ObjectAdaptorCustomer.adaptToCustomerMasterPKEntity(customerMasterTO.getCustomerMasterPKTO()));

        customerMasterE.setTitle(customerMasterTO.getTitle());
        customerMasterE.setCustname(customerMasterTO.getCustName());
        customerMasterE.setCraddress(customerMasterTO.getCrAddress());

        customerMasterE.setPraddress(customerMasterTO.getPrAddress());
        customerMasterE.setPhoneno(customerMasterTO.getPhoneNo());
        customerMasterE.setPanno(customerMasterTO.getPanNo());

        customerMasterE.setStatus(customerMasterTO.getStatus());
        customerMasterE.setGrdname(customerMasterTO.getGrdName());
        customerMasterE.setRelation(customerMasterTO.getRelation());

        customerMasterE.setDob(customerMasterTO.getDob());
        customerMasterE.setOccupation(customerMasterTO.getOccupation());
        customerMasterE.setEnteredby(customerMasterTO.getEnteredBy());

        customerMasterE.setLastupdatedt(customerMasterTO.getLastUpdateDt());
        customerMasterE.setRemarks(customerMasterTO.getRemarks());
        customerMasterE.setCustLines(customerMasterTO.getLines());

        customerMasterE.setAgcode1(customerMasterTO.getAgcode1());
        customerMasterE.setFathername(customerMasterTO.getFathername());

        return customerMasterE;
    }

    public static CustomerMasterTO adaptToCustomerMasterTO(Customermaster customerMaster) {
        CustomerMasterTO customerMasterTo = new CustomerMasterTO();

        customerMasterTo.setCustomerMasterPKTO(ObjectAdaptorCustomer.adaptToCustomerMasterPKTO(customerMaster.getCustomermasterPK()));

        customerMasterTo.setTitle(customerMaster.getTitle());
        customerMasterTo.setCustName(customerMaster.getCustname());

        customerMasterTo.setCrAddress(customerMaster.getCraddress());

        customerMasterTo.setPrAddress(customerMaster.getPraddress());
        customerMasterTo.setPhoneNo(customerMaster.getPhoneno());
        customerMasterTo.setPanNo(customerMaster.getPanno());

        customerMasterTo.setStatus(customerMaster.getStatus());
        customerMasterTo.setGrdName(customerMaster.getGrdname());
        customerMasterTo.setRelation(customerMaster.getRelation());

        customerMasterTo.setDob(customerMaster.getDob());
        customerMasterTo.setOccupation(customerMaster.getOccupation());
        customerMasterTo.setEnteredBy(customerMaster.getEnteredby());

        customerMasterTo.setLastUpdateDt(customerMaster.getLastupdatedt());
        customerMasterTo.setRemarks(customerMaster.getRemarks());
        customerMasterTo.setLines(customerMaster.getCustLines());

        customerMasterTo.setAgcode1(customerMaster.getAgcode1());
        customerMasterTo.setFathername(customerMaster.getFathername());

        return customerMasterTo;
    }

    public static CustomeridHis adaptToCustomeridHisEntity(CustomeridHisTO customeridHisTO) {
        CustomeridHis customeridHisE = new CustomeridHis();

        customeridHisE.setCustId(customeridHisTO.getCustId());
        customeridHisE.setAcno(customeridHisTO.getAcno());
        customeridHisE.setEnterBy(customeridHisTO.getEnterBy());

        customeridHisE.setTranTime(customeridHisTO.getTranTime());
        customeridHisE.setTaxcat(customeridHisTO.getTaxcat());
        customeridHisE.setTxnBrn(customeridHisTO.getTxnBrn());

        customeridHisE.setTxnid(customeridHisTO.getTxnid());

        return customeridHisE;
    }

    public static CustomeridHisTO adaptToCustomeridHisTO(CustomeridHis customeridHis) {
        CustomeridHisTO customeridHisTo = new CustomeridHisTO();

        customeridHisTo.setCustId(customeridHis.getCustId());
        customeridHisTo.setAcno(customeridHis.getAcno());
        customeridHisTo.setEnterBy(customeridHis.getEnterBy());

        customeridHisTo.setTranTime(customeridHis.getTranTime());
        customeridHisTo.setTaxcat(customeridHis.getTaxcat());
        customeridHisTo.setTxnBrn(customeridHis.getTxnBrn());

        customeridHisTo.setTxnid(customeridHis.getTxnid());

        return customeridHisTo;
    }

    public static CustomeridPK adaptToCustomeridPKEntity(CustomeridPKTO customeridPKTO) {
        CustomeridPK customeridPKE = new CustomeridPK();

        customeridPKE.setCustId(customeridPKTO.getCustId());
        customeridPKE.setAcno(customeridPKTO.getAcno());

        return customeridPKE;
    }

    public static CustomeridPKTO adaptToCustomeridPKTO(CustomeridPK customeridPK) {
        CustomeridPKTO customeridPKTo = new CustomeridPKTO();

        customeridPKTo.setCustId(customeridPK.getCustId());
        customeridPKTo.setAcno(customeridPK.getAcno());

        return customeridPKTo;
    }

    public static Customerid adapatToCustomeridEntity(CustomeridTO customeridTO) {
        Customerid customeridE = new Customerid();

        customeridE.setCustomeridPK(ObjectAdaptorCustomer.adaptToCustomeridPKEntity(customeridTO.getCustomeridPKTO()));

        customeridE.setEnterBy(customeridTO.getEnterBy());
        customeridE.setTaxcat(customeridTO.getTaxcat());
        customeridE.setTranTime(customeridTO.getTranTime());

        customeridE.setTxnBrn(customeridTO.getTxnBrn());

        return customeridE;
    }

    public static CustomeridTO adapatToCustomeridTO(Customerid customerid) {
        CustomeridTO customeridTo = new CustomeridTO();

        customeridTo.setCustomeridPKTO(ObjectAdaptorCustomer.adaptToCustomeridPKTO(customerid.getCustomeridPK()));

        customeridTo.setEnterBy(customerid.getEnterBy());
        customeridTo.setTaxcat(customerid.getTaxcat());
        customeridTo.setTranTime(customerid.getTranTime());

        customeridTo.setTxnBrn(customerid.getTxnBrn());

        return customeridTo;
    }

    public static CbsCustKycDetails adapatToCbsCustKycDetails(CbsCustKycDetailsTo custKycDetailsTo) {
        CbsCustKycDetails custKycDetails = new CbsCustKycDetails();
        custKycDetails.setCustomerId(custKycDetailsTo.getCustomerId());
        custKycDetails.setRiskCategory(custKycDetailsTo.getRiskCategory());
        custKycDetails.setTypeOfDocSubmitted(custKycDetailsTo.getTypeOfDocSubmitted());
        custKycDetails.setBrnCode(custKycDetailsTo.getBrnCode());
        custKycDetails.setErrorCode(custKycDetailsTo.getErrorCode());
        custKycDetails.setCkycrUpdateFlag(custKycDetailsTo.getCkycrUpdateFlag());
        custKycDetails.setKycCreatedBy(custKycDetailsTo.getKycCreatedBy());
        custKycDetails.setKycCreationDate(custKycDetailsTo.getKycCreationDate());
        custKycDetails.setKycVerifiedBy(custKycDetailsTo.getKycVerifiedBy());
        custKycDetails.setKycVerifiedDate(custKycDetailsTo.getKycVerifiedDate());
        custKycDetails.setKycVerifiedUserName(custKycDetailsTo.getKycVerifiedUserName());
        return custKycDetails;
    }

    public static CbsCustKycDetailsTo adapatToCbsCustKycDetailsTo(CbsCustKycDetails custKycDetails) {
        CbsCustKycDetailsTo custKycDetailsTo = new CbsCustKycDetailsTo();
        custKycDetailsTo.setCustomerId(custKycDetails.getCustomerId());
        custKycDetailsTo.setRiskCategory(custKycDetails.getRiskCategory());
        custKycDetailsTo.setTypeOfDocSubmitted(custKycDetails.getTypeOfDocSubmitted());
        custKycDetailsTo.setBrnCode(custKycDetails.getBrnCode());
        custKycDetailsTo.setErrorCode(custKycDetails.getErrorCode());
        custKycDetailsTo.setCkycrUpdateFlag(custKycDetails.getCkycrUpdateFlag());
        custKycDetailsTo.setKycCreatedBy(custKycDetails.getKycCreatedBy());
        custKycDetailsTo.setKycCreationDate(custKycDetails.getKycCreationDate());
        custKycDetailsTo.setKycVerifiedBy(custKycDetails.getKycVerifiedBy());
        custKycDetailsTo.setKycVerifiedDate(custKycDetails.getKycVerifiedDate());
        custKycDetailsTo.setKycVerifiedUserName(custKycDetails.getKycVerifiedUserName());
        return custKycDetailsTo;
    }

    public static CbsCustIdentityDetails adaptToCbsCustIdentityDetailsEntity(CBSCustIdentityDetailsTo custIdentityDetailsTo) {
        CbsCustIdentityDetails custIdentityDetailsE = new CbsCustIdentityDetails();
        CbsCustIdentityDetailsPK custIdentityDetailsPK = new CbsCustIdentityDetailsPK();

        custIdentityDetailsPK.setCustomerId(custIdentityDetailsTo.getCustomerId());
        custIdentityDetailsPK.setIdentificationType(custIdentityDetailsTo.getIdentificationType());
        custIdentityDetailsE.setCbsCustIdentityDetailsPK(custIdentityDetailsPK);

        custIdentityDetailsE.setIdentityNo(custIdentityDetailsTo.getIdentityNo());
        custIdentityDetailsE.setIdExpiryDate(custIdentityDetailsTo.getIdentityExpiryDate());
        custIdentityDetailsE.setOtherIdentificationType(custIdentityDetailsTo.getOtherIdentificationType());
        custIdentityDetailsE.setTinIssuingCountry(custIdentityDetailsTo.getTinIssuingCountry());

        custIdentityDetailsE.setEnterBy(custIdentityDetailsTo.getEnterBy());
//        custIdentityDetailsE.setEnterDate(new java.util.Date());
        return custIdentityDetailsE;
    }

    public static CBSCustIdentityDetailsTo adaptToCbsCustIdentityDetailsTO(CbsCustIdentityDetails custIdentityDetails) {
        CBSCustIdentityDetailsTo custIdentityDetailsTo = new CBSCustIdentityDetailsTo();

        custIdentityDetailsTo.setCustomerId(custIdentityDetails.getCbsCustIdentityDetailsPK().getCustomerId());
        custIdentityDetailsTo.setIdentificationType(custIdentityDetails.getCbsCustIdentityDetailsPK().getIdentificationType());

        custIdentityDetailsTo.setIdentityNo(custIdentityDetails.getIdentityNo());
        custIdentityDetailsTo.setIdentityExpiryDate(custIdentityDetails.getIdExpiryDate());
        custIdentityDetailsTo.setOtherIdentificationType(custIdentityDetails.getOtherIdentificationType());
        custIdentityDetailsTo.setTinIssuingCountry(custIdentityDetails.getTinIssuingCountry());

        custIdentityDetailsTo.setEnterBy(custIdentityDetails.getEnterBy());

        return custIdentityDetailsTo;
    }

    public static CbsCustAdditionalAddressDetailsTo adaptToCbsCustAdditionalAddressDetailsTo(CbsCustAdditionalAddressDetails custAddAddressDetails) {
        CbsCustAdditionalAddressDetailsTo custAddAddressDetailsTo = new CbsCustAdditionalAddressDetailsTo();

        custAddAddressDetailsTo.setCustomerId(custAddAddressDetails.getCbsCustAdditionalAddressDetailsPK().getCustomerId());
        custAddAddressDetailsTo.setsNo(custAddAddressDetails.getCbsCustAdditionalAddressDetailsPK().getSno());
        custAddAddressDetailsTo.setAddressType(custAddAddressDetails.getAddressType());

        custAddAddressDetailsTo.setLocalAddressLine1(custAddAddressDetails.getLocalAddressLine1());
        custAddAddressDetailsTo.setLocalAddressLine2(custAddAddressDetails.getLocalAddressLine2());
        custAddAddressDetailsTo.setLocalAddressLine3(custAddAddressDetails.getLocalAddressLine3());

        custAddAddressDetailsTo.setLocalAddressCityVillage(custAddAddressDetails.getLocalAddressCityVillage());
        custAddAddressDetailsTo.setLocalAddressCountry(custAddAddressDetails.getLocalAddressCountry());
        custAddAddressDetailsTo.setLocalAddressState(custAddAddressDetails.getLocalAddressState());
        custAddAddressDetailsTo.setLocalAddressDistrict(custAddAddressDetails.getLocalAddressDistrict());
        custAddAddressDetailsTo.setLocalAddressPINCode(custAddAddressDetails.getLocalAddressPINCode());
        custAddAddressDetailsTo.setProofofAdd(custAddAddressDetails.getProofofAdd());

        custAddAddressDetailsTo.setResiTelSTDCode(custAddAddressDetails.getResiTelSTDCode());
        custAddAddressDetailsTo.setResiTelNo(custAddAddressDetails.getResiTelNo());

        custAddAddressDetailsTo.setOfficeTelSTDCode(custAddAddressDetails.getOfficeTelSTDCode());
        custAddAddressDetailsTo.setOfficeTelNo(custAddAddressDetails.getOfficeTelNo());

        custAddAddressDetailsTo.setMobISDCode(custAddAddressDetails.getMobISDCode());
        custAddAddressDetailsTo.setMobileNo(custAddAddressDetails.getMobileNo());

        custAddAddressDetailsTo.setFaxSTDCode(custAddAddressDetails.getFaxSTDCode());
        custAddAddressDetailsTo.setFaxNo(custAddAddressDetails.getFaxNo());

        custAddAddressDetailsTo.setEmailID(custAddAddressDetails.getEmailID());

        custAddAddressDetailsTo.setDateofDeclaration(custAddAddressDetails.getDateofDeclaration());
        custAddAddressDetailsTo.setPlaceofDeclaration(custAddAddressDetails.getPlaceofDeclaration());

        custAddAddressDetailsTo.setEnterBy(custAddAddressDetails.getEnterBy());
        custAddAddressDetailsTo.setEnterDate(custAddAddressDetails.getEnterDate());

        return custAddAddressDetailsTo;
    }

    public static CbsCustAdditionalAddressDetails adaptToCbsCustAdditionalAddressDetailsE(CbsCustAdditionalAddressDetailsTo custAddAddressDetailsTo) {
        CbsCustAdditionalAddressDetails custAddAddressDetailsE = new CbsCustAdditionalAddressDetails();
        CbsCustAdditionalAddressDetailsPK custAddAddressDetailsPKE = new CbsCustAdditionalAddressDetailsPK();
        custAddAddressDetailsPKE.setCustomerId(custAddAddressDetailsTo.getCustomerId());
        custAddAddressDetailsPKE.setSno(custAddAddressDetailsTo.getsNo());

        custAddAddressDetailsE.setCbsCustAdditionalAddressDetailsPK(custAddAddressDetailsPKE);

        custAddAddressDetailsE.setAddressType(custAddAddressDetailsTo.getAddressType());
        custAddAddressDetailsE.setLocalAddressLine1(custAddAddressDetailsTo.getLocalAddressLine1());
        custAddAddressDetailsE.setLocalAddressLine2(custAddAddressDetailsTo.getLocalAddressLine2());
        custAddAddressDetailsE.setLocalAddressLine3(custAddAddressDetailsTo.getLocalAddressLine3());

        custAddAddressDetailsE.setLocalAddressCityVillage(custAddAddressDetailsTo.getLocalAddressCityVillage());
        custAddAddressDetailsE.setLocalAddressCountry(custAddAddressDetailsTo.getLocalAddressCountry());
        custAddAddressDetailsE.setLocalAddressState(custAddAddressDetailsTo.getLocalAddressState());
        custAddAddressDetailsE.setLocalAddressDistrict(custAddAddressDetailsTo.getLocalAddressDistrict());
        custAddAddressDetailsE.setLocalAddressPINCode(custAddAddressDetailsTo.getLocalAddressPINCode());
        custAddAddressDetailsE.setProofofAdd(custAddAddressDetailsTo.getProofofAdd());

        custAddAddressDetailsE.setResiTelSTDCode(custAddAddressDetailsTo.getResiTelSTDCode());
        custAddAddressDetailsE.setResiTelNo(custAddAddressDetailsTo.getResiTelNo());

        custAddAddressDetailsE.setOfficeTelSTDCode(custAddAddressDetailsTo.getOfficeTelSTDCode());
        custAddAddressDetailsE.setOfficeTelNo(custAddAddressDetailsTo.getOfficeTelNo());

        custAddAddressDetailsE.setMobISDCode(custAddAddressDetailsTo.getMobISDCode());
        custAddAddressDetailsE.setMobileNo(custAddAddressDetailsTo.getMobileNo());

        custAddAddressDetailsE.setFaxSTDCode(custAddAddressDetailsTo.getFaxSTDCode());
        custAddAddressDetailsE.setFaxNo(custAddAddressDetailsTo.getFaxNo());

        custAddAddressDetailsE.setEmailID(custAddAddressDetailsTo.getEmailID());

        custAddAddressDetailsE.setDateofDeclaration(custAddAddressDetailsTo.getDateofDeclaration());
        custAddAddressDetailsE.setPlaceofDeclaration(custAddAddressDetailsTo.getPlaceofDeclaration());

        custAddAddressDetailsE.setEnterBy(custAddAddressDetailsTo.getEnterBy());
        custAddAddressDetailsE.setEnterDate(custAddAddressDetailsTo.getEnterDate());

        return custAddAddressDetailsE;
    }

    public static CbsCustAdditionalAddressDetailsHis adaptToCbsCustAdditionalAddressDetailsHisE(CbsCustAdditionalAddressDetails custAddAddressDetails) {
        CbsCustAdditionalAddressDetailsHis custAddAddressDetailsHis = new CbsCustAdditionalAddressDetailsHis();

        custAddAddressDetailsHis.setCustomerId(custAddAddressDetails.getCbsCustAdditionalAddressDetailsPK().getCustomerId());
        custAddAddressDetailsHis.setSno(custAddAddressDetails.getCbsCustAdditionalAddressDetailsPK().getSno());

        custAddAddressDetailsHis.setAddressType(custAddAddressDetails.getAddressType());

        custAddAddressDetailsHis.setLocalAddressLine1(custAddAddressDetails.getLocalAddressLine1());
        custAddAddressDetailsHis.setLocalAddressLine2(custAddAddressDetails.getLocalAddressLine2());
        custAddAddressDetailsHis.setLocalAddressLine3(custAddAddressDetails.getLocalAddressLine3());

        custAddAddressDetailsHis.setLocalAddressCityVillage(custAddAddressDetails.getLocalAddressCityVillage());
        custAddAddressDetailsHis.setLocalAddressCountry(custAddAddressDetails.getLocalAddressCountry());
        custAddAddressDetailsHis.setLocalAddressState(custAddAddressDetails.getLocalAddressState());
        custAddAddressDetailsHis.setLocalAddressDistrict(custAddAddressDetails.getLocalAddressDistrict());
        custAddAddressDetailsHis.setLocalAddressPINCode(custAddAddressDetails.getLocalAddressPINCode());
        custAddAddressDetailsHis.setProofofAdd(custAddAddressDetails.getProofofAdd());

        custAddAddressDetailsHis.setResiTelSTDCode(custAddAddressDetails.getResiTelSTDCode());
        custAddAddressDetailsHis.setResiTelNo(custAddAddressDetails.getResiTelNo());

        custAddAddressDetailsHis.setOfficeTelSTDCode(custAddAddressDetails.getOfficeTelSTDCode());
        custAddAddressDetailsHis.setOfficeTelNo(custAddAddressDetails.getOfficeTelNo());

        custAddAddressDetailsHis.setMobISDCode(custAddAddressDetails.getMobISDCode());
        custAddAddressDetailsHis.setMobileNo(custAddAddressDetails.getMobileNo());

        custAddAddressDetailsHis.setFaxSTDCode(custAddAddressDetails.getFaxSTDCode());
        custAddAddressDetailsHis.setFaxNo(custAddAddressDetails.getFaxNo());

        custAddAddressDetailsHis.setEmailID(custAddAddressDetails.getEmailID());

        custAddAddressDetailsHis.setDateofDeclaration(custAddAddressDetails.getDateofDeclaration());
        custAddAddressDetailsHis.setPlaceofDeclaration(custAddAddressDetails.getPlaceofDeclaration());

        custAddAddressDetailsHis.setEnterBy(custAddAddressDetails.getEnterBy());
        custAddAddressDetailsHis.setEnterDate(custAddAddressDetails.getEnterDate());

        return custAddAddressDetailsHis;
    }
}