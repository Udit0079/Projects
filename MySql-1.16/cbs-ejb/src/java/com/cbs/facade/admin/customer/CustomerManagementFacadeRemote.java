/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin.customer;

import com.cbs.dto.customer.CBSCustCurrencyInfoTO;
import com.cbs.dto.customer.CBSCustIdentityDetailsTo;
import com.cbs.dto.customer.CbsCustAdditionalAddressDetailsTo;
import com.cbs.dto.customer.CodebookTO;
import com.cbs.dto.master.ParameterinfoReportTO;
import com.cbs.entity.master.Securityinfo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Ankit Verma
 */
@Remote
public interface CustomerManagementFacadeRemote {

    public java.util.List<com.cbs.dto.master.CbsRefRecTypeTO> getCurrencyCode(java.lang.String refRecNo) throws com.cbs.exception.ApplicationException;

    public com.cbs.dto.customer.CBSCustomerMasterDetailTO getCustDetailsByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.customer.CodebookTO> getAssetOwnership(java.lang.String assetType) throws com.cbs.exception.ApplicationException;

    public CodebookTO getAssetsByDescription(java.lang.String description) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.customer.CodebookTO> getAssetType() throws com.cbs.exception.ApplicationException;

    public List<CBSCustCurrencyInfoTO> getCurrencyInfoByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public com.cbs.dto.customer.CBSCustMISInfoTO getMisInfoByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public com.cbs.dto.customer.CBSCustMinorInfoTO getMinorInfoByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public com.cbs.dto.customer.CBSCustNREInfoTO getNreInfoByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public com.cbs.dto.customer.CBSTradeFinanceInformationTO gettdFInfoByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public List<com.cbs.dto.customer.CBSRelatedPersonsDetailsTO> getRelatedPersonInfoByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public com.cbs.dto.customer.CBSBuyerSellerLimitDetailsTO getBsLimitDetailsByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public com.cbs.dto.customer.CBSCustDeliveryChannelDetailsTO getCusDeliveryChannelDetailsByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public com.cbs.dto.loan.CbsKycDetailsTO getKycDetailsByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.loan.CbsKycAssetsTO> getKycAssetsByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.loan.CbsKycLoanTO> getKycLoanByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

//    public java.lang.String saveUpdateCustomer(com.cbs.dto.customer.CBSCustomerMasterDetailTO customerMasterDetailTO,
//            java.util.List<com.cbs.dto.customer.CBSCustCurrencyInfoTO> currencyInfoTOs, com.cbs.dto.customer.CBSCustMinorInfoTO minorInfoTO,
//            com.cbs.dto.customer.CBSCustNREInfoTO nREInfoTO, com.cbs.dto.customer.CBSCustMISInfoTO mISInfoTO,
//            com.cbs.dto.customer.CBSTradeFinanceInformationTO informationTO,
//            java.util.List<com.cbs.dto.customer.CBSRelatedPersonsDetailsTO> reletedPersonDetailsTos,
//            com.cbs.dto.customer.CBSBuyerSellerLimitDetailsTO buyerSellerLimitDetailsTO,
//            com.cbs.dto.customer.CBSCustDeliveryChannelDetailsTO channelDetailsTO,
//            com.cbs.dto.loan.CbsKycDetailsTO kycDetailsTO, java.util.List<com.cbs.dto.loan.CbsKycAssetsTO> assetsTOs,
//            java.util.List<com.cbs.dto.loan.CbsKycLoanTO> loanTOs, java.lang.String function,
//            java.lang.String userName, java.lang.String brncode) throws com.cbs.exception.ApplicationException;
    public java.lang.String saveUpdateCustomer(com.cbs.dto.customer.CBSCustomerMasterDetailTO customerMasterDetailTO,
            com.cbs.dto.customer.CBSCustMinorInfoTO minorInfoTO, com.cbs.dto.customer.CBSCustNREInfoTO nREInfoTO,
            com.cbs.dto.customer.CBSCustMISInfoTO mISInfoTO, java.util.List<com.cbs.dto.customer.CBSRelatedPersonsDetailsTO> reletedPersonDetailsTos,
            List<com.cbs.dto.customer.CBSCustIdentityDetailsTo> idTypeList, List<CbsCustAdditionalAddressDetailsTo> AddressDetailsList, java.lang.String function, java.lang.String userName, java.lang.String brncode)
            throws com.cbs.exception.ApplicationException;

    public Securityinfo getSecurityInfoDetailsByUserid(java.lang.String userId) throws com.cbs.exception.ApplicationException;

    public List<Object[]> getUnVerifyCustIdsByBrnCode(java.lang.String brnCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String suspendUnsuspendVerifyCustomer(java.lang.String custId, java.lang.String func, String userName) throws com.cbs.exception.ApplicationException;

    public ParameterinfoReportTO getAgeLimit(String reportName) throws com.cbs.exception.ApplicationException;

    public String getCustomerName(String customerId) throws ApplicationException;

    public List getCustomerAllNames(String customerId) throws ApplicationException;

    public com.cbs.dto.CbsCustKycDetailsTo getCustKycDetailsByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public List<CBSCustIdentityDetailsTo> getCustIdentityDetailsByCustId(String custId) throws ApplicationException;

    public List<CbsCustAdditionalAddressDetailsTo> getCustAddressDetailsByCustId(String custId) throws ApplicationException;

    public List getDistrictListLike(String stateCode, String args, String callFor) throws ApplicationException;

    public String getRecRefDiscription(String RecRefNo, String RecRefCode) throws ApplicationException;

    public String getCustomerImageMapp(String RecRefNo, String RecRefCode) throws ApplicationException;
}
