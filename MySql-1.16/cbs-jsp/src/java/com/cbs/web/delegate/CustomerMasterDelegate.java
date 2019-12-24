/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.delegate;

import com.cbs.dto.CbsCustKycDetailsTo;
import com.cbs.dto.customer.CBSBuyerSellerLimitDetailsTO;
import com.cbs.dto.customer.CBSCustCurrencyInfoTO;
import com.cbs.dto.customer.CBSCustDeliveryChannelDetailsTO;
import com.cbs.dto.customer.CBSCustIdentityDetailsTo;
import com.cbs.dto.customer.CBSCustMISInfoTO;
import com.cbs.dto.customer.CBSCustMinorInfoTO;
import com.cbs.dto.customer.CBSCustNREInfoTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.customer.CBSRelatedPersonsDetailsTO;
import com.cbs.dto.customer.CBSTradeFinanceInformationTO;
import com.cbs.dto.customer.CbsCustAdditionalAddressDetailsTo;
import com.cbs.dto.customer.CodebookTO;
import com.cbs.dto.loan.CbsKycAssetsTO;
import com.cbs.dto.loan.CbsKycDetailsTO;
import com.cbs.dto.loan.CbsKycLoanTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.master.ParameterinfoReportTO;
import com.cbs.entity.master.Securityinfo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.customer.CustomerManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ANKIT VERMA
 */
public class CustomerMasterDelegate {

    private final String jndiHomeName = "CustomerManagementFacade";
    private CustomerManagementFacadeRemote beanRemote = null;
    private final String ftsJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;

    public CustomerMasterDelegate() throws ApplicationException {
        try {
            beanRemote = (CustomerManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(ftsJndiName);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<CbsRefRecTypeTO> getFunctionValues(String refRecNo) throws ApplicationException {
        List<CbsRefRecTypeTO> cbsRefRecTOs = new ArrayList<CbsRefRecTypeTO>();
        try {
            cbsRefRecTOs = beanRemote.getCurrencyCode(refRecNo);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return cbsRefRecTOs;
    }

    public CBSCustomerMasterDetailTO getCustDetailsByCustId(String custId) throws ApplicationException {
        CBSCustomerMasterDetailTO masterDetailTO = null;
        try {
            masterDetailTO = beanRemote.getCustDetailsByCustId(custId);
        } catch (ApplicationException e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return masterDetailTO;
    }

    public List<CodebookTO> getAssetOwnership(String assetType) throws ApplicationException {
        List<CodebookTO> codeBookList = null;
        try {
            codeBookList = beanRemote.getAssetOwnership(assetType);
        } catch (ApplicationException e) {

            throw new ApplicationException(e.getMessage());
        }
        return codeBookList;

    }

    public CodebookTO getAssetsByDescription(String description) throws ApplicationException {
        CodebookTO codebookTO;
        try {
            codebookTO = beanRemote.getAssetsByDescription(description);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return codebookTO;

    }

    public List<CodebookTO> getAssetType() throws ApplicationException {
        List<CodebookTO> codeBookList = null;
        try {
            codeBookList = beanRemote.getAssetType();
        } catch (ApplicationException e) {

            throw new ApplicationException(e.getMessage());
        }
        return codeBookList;

    }

    public List<CBSCustCurrencyInfoTO> getCurrencyInfoByCustId(String custId) throws ApplicationException {
        List<CBSCustCurrencyInfoTO> custCurrencyInfoTO = new ArrayList<CBSCustCurrencyInfoTO>();
        try {
            custCurrencyInfoTO = beanRemote.getCurrencyInfoByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return custCurrencyInfoTO;

    }

    public CBSCustMISInfoTO getMISInfoByCustId(String custId) throws ApplicationException {
        CBSCustMISInfoTO custMISInfoTO = null;
        try {
            custMISInfoTO = beanRemote.getMisInfoByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return custMISInfoTO;
    }

    public CBSCustMinorInfoTO getMinorInfoByCustId(String custId) throws ApplicationException {
        CBSCustMinorInfoTO custMinorInfoTO = null;
        try {
            custMinorInfoTO = beanRemote.getMinorInfoByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return custMinorInfoTO;
    }

    public CBSCustNREInfoTO getNreInfoByCustId(String custId) throws ApplicationException {
        CBSCustNREInfoTO custNREInfoTO = null;
        try {
            custNREInfoTO = beanRemote.getNreInfoByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return custNREInfoTO;
    }

    public CBSTradeFinanceInformationTO gettdFInfoByCustId(String custId) throws ApplicationException {
        CBSTradeFinanceInformationTO informationTO = null;
        try {
            informationTO = beanRemote.gettdFInfoByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return informationTO;
    }

    public List<CBSRelatedPersonsDetailsTO> getRelatedPersonInfoByCustId(String custId) throws ApplicationException {
        List<CBSRelatedPersonsDetailsTO> personsDetailsTO = new ArrayList<CBSRelatedPersonsDetailsTO>();
        try {
            personsDetailsTO = beanRemote.getRelatedPersonInfoByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return personsDetailsTO;
    }

    public CBSBuyerSellerLimitDetailsTO getBsLimitDetailsByCustId(String custId) throws ApplicationException {
        CBSBuyerSellerLimitDetailsTO detailsTO = null;
        try {
            detailsTO = beanRemote.getBsLimitDetailsByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return detailsTO;
    }

    public CBSCustDeliveryChannelDetailsTO getCusDeliveryChannelDetailsByCustId(String custId) throws ApplicationException {
        CBSCustDeliveryChannelDetailsTO detailsTO = null;
        try {
            detailsTO = beanRemote.getCusDeliveryChannelDetailsByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return detailsTO;

    }

    public CbsKycDetailsTO getKycDetailsByCustId(String custId) throws ApplicationException {
        CbsKycDetailsTO detailsTO = null;
        try {
            detailsTO = beanRemote.getKycDetailsByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return detailsTO;

    }

    public List<CbsKycAssetsTO> getKycAssetsByCustId(String custId) throws ApplicationException {
        List<CbsKycAssetsTO> CbsKycAssetsTOs = new ArrayList<CbsKycAssetsTO>();
        try {
            CbsKycAssetsTOs = beanRemote.getKycAssetsByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return CbsKycAssetsTOs;
    }

    public List<CbsKycLoanTO> getKycLoanByCustId(String custId) throws ApplicationException {
        List<CbsKycLoanTO> CbsKycLoanTOs = new ArrayList<CbsKycLoanTO>();
        try {
            CbsKycLoanTOs = beanRemote.getKycLoanByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return CbsKycLoanTOs;
    }

//    public String saveUpdateCustomer(CBSCustomerMasterDetailTO customerMasterDetailTO, List<CBSCustCurrencyInfoTO> currencyInfoTOs, CBSCustMinorInfoTO minorInfoTO, CBSCustNREInfoTO nREInfoTO, CBSCustMISInfoTO mISInfoTO, CBSTradeFinanceInformationTO informationTO, List<CBSRelatedPersonsDetailsTO> reletedPersonDetailsTos, CBSBuyerSellerLimitDetailsTO buyerSellerLimitDetailsTO, CBSCustDeliveryChannelDetailsTO channelDetailsTO, CbsKycDetailsTO kycDetailsTO, List<CbsKycAssetsTO> assetsTOs, List<CbsKycLoanTO> loanTOs, String function, String userName, String brncode) throws ApplicationException {
//        String msg = "";
//        try {
//            msg = beanRemote.saveUpdateCustomer(customerMasterDetailTO, currencyInfoTOs, minorInfoTO, nREInfoTO, mISInfoTO,
//                    informationTO, reletedPersonDetailsTos, buyerSellerLimitDetailsTO, channelDetailsTO, kycDetailsTO, assetsTOs,
//                    loanTOs, function, userName, brncode);
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return msg;
//    }
    public String saveUpdateCustomer(CBSCustomerMasterDetailTO customerMasterDetailTO,
            CBSCustMinorInfoTO minorInfoTO,
            CBSCustNREInfoTO nREInfoTO, CBSCustMISInfoTO mISInfoTO,
            List<CBSRelatedPersonsDetailsTO> reletedPersonDetailsTos,
            List<CBSCustIdentityDetailsTo> idTypeList, List<CbsCustAdditionalAddressDetailsTo> AddressDetailsList, String function,
            String userName, String brncode) throws ApplicationException {
        String msg = "";
        try {
            msg = beanRemote.saveUpdateCustomer(customerMasterDetailTO, minorInfoTO, nREInfoTO, mISInfoTO,
                    reletedPersonDetailsTos, idTypeList, AddressDetailsList, function, userName, brncode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public Securityinfo getSecurityInfoDetailsByUserid(String userId) throws ApplicationException {
        Securityinfo entity = null;
        try {
            entity = beanRemote.getSecurityInfoDetailsByUserid(userId);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return entity;
    }

    public List<Object[]> getUnVerifyCustIdsByBrnCode(String brnCode) throws ApplicationException {
        try {
            return (List<Object[]>) beanRemote.getUnVerifyCustIdsByBrnCode(brnCode);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String suspendUnsuspendVerifyCustomer(String custId, String func, String userName) throws ApplicationException {
        String msg = "";
        try {
            msg = beanRemote.suspendUnsuspendVerifyCustomer(custId, func, userName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public ParameterinfoReportTO getAgeLimit(String reportName) throws ApplicationException {
        ParameterinfoReportTO to = new ParameterinfoReportTO();
        try {
            to = beanRemote.getAgeLimit(reportName);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return to;
    }

    public String isPrimaryAc(String custId, String acno) throws ApplicationException {
        try {
            return ftsRemote.isPrimaryAc(custId, acno);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String isAadharExists(String custId, String aadharNo) throws ApplicationException {
        try {
            return ftsRemote.isAadharExists(custId, aadharNo);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public String isIdentityExists(String identityNo) throws ApplicationException{
        try{
           return ftsRemote.isIdentityNoExists(identityNo);
        }catch(Exception ex){
            throw new ApplicationException(ex.getMessage());
        }
    }
    
    public String isCustomerIdentityNoExists(String identityNo) throws ApplicationException{
        try{
           return ftsRemote.isCustomerIdentityNoExists(identityNo);
        }catch(Exception ex){
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String isLpgIdExists(String custId, String lpgId) throws ApplicationException {
        try {
            return ftsRemote.isLpgIdExists(custId, lpgId);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    // public CbsCustKycDetailsTo getSecurityInfoDetailsByUserid(String userId) throws ApplicationException {
    public CbsCustKycDetailsTo getCustKycDetailsByCustId(String custId) throws ApplicationException {
        CbsCustKycDetailsTo custKycDetails = null;
        try {
            custKycDetails = beanRemote.getCustKycDetailsByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return custKycDetails;
    }

    public List getDistrictListLike(String stateCode, String args, String callFor) throws ApplicationException {
        List list = null;
        try {
            list = beanRemote.getDistrictListLike(stateCode, args, callFor);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public String getRecRefDiscription(String RecRefNo, String RecRefCode) throws ApplicationException {
        String RecRefDiscription = null;
        try {
            RecRefDiscription = beanRemote.getRecRefDiscription(RecRefNo, RecRefCode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return RecRefDiscription;
    }

    public List<CBSCustIdentityDetailsTo> getCustIdentityDetailsByCustId(String custId) throws ApplicationException {
        List<CBSCustIdentityDetailsTo> custIdentityDetailsList = null;
        try {
            custIdentityDetailsList = beanRemote.getCustIdentityDetailsByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return custIdentityDetailsList;
    }

    public List<CbsCustAdditionalAddressDetailsTo> getCustAddressDetailsByCustId(String custId) throws ApplicationException {
        List<CbsCustAdditionalAddressDetailsTo> custAddressDetailsList = null;
        try {
            custAddressDetailsList = beanRemote.getCustAddressDetailsByCustId(custId);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return custAddressDetailsList;
    }

    public String getCustomerImageMapp(String RecRefNo, String RecRefCode) throws ApplicationException {
        String RecRefDiscription = null;
        try {
            RecRefDiscription = beanRemote.getCustomerImageMapp(RecRefNo, RecRefCode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return RecRefDiscription;
    }
}
