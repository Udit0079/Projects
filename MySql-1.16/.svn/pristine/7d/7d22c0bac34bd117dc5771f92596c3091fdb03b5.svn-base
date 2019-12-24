/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin.customer;

import com.cbs.adaptor.ObjectAdaptorCustomer;
import com.cbs.adaptor.ObjectAdaptorLoan;
import com.cbs.adaptor.ObjectAdaptorMaster;
import com.cbs.constant.CbsConstant;
import com.cbs.dao.customer.CBSBuyerSellerLimitDetailsDAO;
import com.cbs.dao.customer.CBSCustCurrencyInfoDAO;
import com.cbs.dao.customer.CBSCustDeliveryChannelDetailsDAO;
import com.cbs.dao.customer.CBSCustIdentityDetailsDAO;
import com.cbs.dao.customer.CBSCustMISInfoDAO;
import com.cbs.dao.customer.CBSCustMinorInfoDAO;
import com.cbs.dao.customer.CBSCustNREInfoDAO;
import com.cbs.dao.customer.CBSRelatedPersonsDetailsDAO;
import com.cbs.dao.customer.CBSTradeFinanceInformationDAO;
import com.cbs.dao.customer.CbsCustAdditionalAddressDetailsDAO;
import com.cbs.dao.customer.CbsCustKycDetailsDAO;
import com.cbs.dao.customer.CustomerIdDAO;
import com.cbs.dao.customer.CustomerMasterDAO;
import com.cbs.dao.customer.TdCustomermasterDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.loan.CbsKycAssetsDAO;
import com.cbs.dao.loan.CbsKycDetailsDAO;
import com.cbs.dao.loan.CbsKycLoanDAO;
import com.cbs.dao.master.CBSCustomerMasterDetailDAO;
import com.cbs.dao.master.CbsRefRecTypeDAO;
import com.cbs.dao.master.CodeBookDAO;
import com.cbs.dao.master.ParameterinfoReportDAO;
import com.cbs.dao.master.SecurityInfoDAO;
import com.cbs.dao.sms.AccountMasterDAO;
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
import com.cbs.entity.customer.CBSBuyerSellerLimitDetails;
import com.cbs.entity.customer.CBSCustCurrencyInfo;
import com.cbs.entity.customer.CBSCustDeliveryChannelDetails;
import com.cbs.entity.customer.CBSCustMinorInfo;
import com.cbs.entity.customer.CBSCustMinorInfoHis;
import com.cbs.entity.customer.CbsCustomerMasterDetailHis;
import com.cbs.entity.customer.CBSTradeFinanceInformation;
import com.cbs.entity.customer.CbsCustAdditionalAddressDetails;
import com.cbs.entity.customer.CbsCustAdditionalAddressDetailsHis;
import com.cbs.entity.customer.CbsCustIdentityDetails;
import com.cbs.entity.customer.CbsCustIdentityDetailsHis;
import com.cbs.entity.customer.CbsCustKycDetails;
import com.cbs.entity.customer.CbsCustMisinfo;
import com.cbs.entity.customer.CbsCustMisinfoHis;
import com.cbs.entity.customer.CbsCustNreinfo;
import com.cbs.entity.customer.CbsCustNreinfoHis;
import com.cbs.entity.customer.CbsKycAssets;
import com.cbs.entity.customer.CbsKycDetails;
import com.cbs.entity.customer.CbsKycLoan;
import com.cbs.entity.customer.Customermaster;
import com.cbs.entity.customer.Customerid;
import com.cbs.entity.customer.TdCustomermaster;
import com.cbs.entity.ho.investment.Codebook;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.entity.master.CbsRefRecType;

import com.cbs.entity.neftrtgs.AccountMaster;
import com.cbs.entity.customer.CbsCustomerMasterDetail;
import com.cbs.entity.customer.CbsRelatedPersonsDetails;
import com.cbs.entity.customer.CbsRelatedPersonsDetailsHis;
import com.cbs.entity.master.Securityinfo;
import com.cbs.entity.neftrtgs.TDAccountMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ExceptionCode;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.facade.ckycr.CkycrCommonMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;

@Stateless(mappedName = "CustomerManagementFacade")
@TransactionManagement(TransactionManagementType.BEAN)
public class CustomerManagementFacade implements CustomerManagementFacadeRemote {

    private static final Logger logger = Logger.getLogger(CustomerManagementFacade.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote facadeRemote;
    @EJB
    private NpciMgmtFacadeRemote npciFacade;
    @EJB
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    @EJB
    private CkycrCommonMgmtFacadeRemote ckycrCommonRemote;
    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

    public List<CbsRefRecTypeTO> getCurrencyCode(String refRecNo) throws ApplicationException {
        long begin = System.nanoTime();
        CbsRefRecTypeDAO cbsRefRecDAO = new CbsRefRecTypeDAO(entityManager);
        List<CbsRefRecTypeTO> cbsRefRecTypeTOs = new ArrayList<CbsRefRecTypeTO>();
        try {
            List<CbsRefRecType> cbsRefRecTypeEntity = cbsRefRecDAO.getCurrencyCode(refRecNo);
            for (CbsRefRecType cbsRefRecE : cbsRefRecTypeEntity) {
                cbsRefRecTypeTOs.add(ObjectAdaptorMaster.adaptToCbsRefRecTypeTO(cbsRefRecE));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getCurrencyCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }

        } catch (Exception e) {
            logger.error("Exception occured while executing method getCurrencyCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getCurrencyCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsRefRecTypeTOs;
    }

    public CBSCustomerMasterDetailTO getCustDetailsByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        CBSCustomerMasterDetailDAO masterDetailDAO = new CBSCustomerMasterDetailDAO(entityManager);
        CBSCustomerMasterDetailTO cBSCustomerMasterDetailTO = new CBSCustomerMasterDetailTO();
        try {
            CbsCustomerMasterDetail cBSCustomerMasterDetailEntity = masterDetailDAO.getCustomerDetailByCustId(custId);
            if (cBSCustomerMasterDetailEntity == null) {
                throw new ApplicationException("Invalid customer id " + custId);
            }
            cBSCustomerMasterDetailTO = ObjectAdaptorCustomer.adaptToCBSCustomerMasterDtlTO(cBSCustomerMasterDetailEntity);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getCustDetailsByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (ApplicationException e) {
            logger.error("ApplicationException occured while executing method getCustDetailsByCustId()", e);
            throw new ApplicationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getCustDetailsByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getCustDetailsByCustId is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cBSCustomerMasterDetailTO;
    }

    public List<CodebookTO> getAssetOwnership(String assetType) throws ApplicationException {
        long begin = System.nanoTime();
        List<CodebookTO> resultList = new ArrayList<CodebookTO>();
        try {
            List<Codebook> assetOwnership = new CodeBookDAO(entityManager).getAssetOwnership(assetType);
            if (!assetOwnership.isEmpty()) {
                for (Codebook entityList : assetOwnership) {
                    resultList.add(ObjectAdaptorCustomer.adaptToCodebookTO(entityList));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAssetOwnership()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }

        }  catch (Exception e) {
            logger.error("Exception occured while executing method getAssetOwnership()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAssetOwnership() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return resultList;
    }

    public CodebookTO getAssetsByDescription(String description) throws ApplicationException {
        long begin = System.nanoTime();
        CodebookTO codebookTO;
        try {
            Codebook entity = new CodeBookDAO(entityManager).getAssetsByDescription(description);
            codebookTO = ObjectAdaptorCustomer.adaptToCodebookTO(entity);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAssetsByDescription()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }

        } catch (Exception e) {
            logger.error("Exception occured while executing method getAssetsByDescription()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAssetsByDescription() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return codebookTO;
    }

    public List<CodebookTO> getAssetType() throws ApplicationException {
        long begin = System.nanoTime();
        List<CodebookTO> resultList = new ArrayList<CodebookTO>();
        try {
            List<Codebook> assetOwnership = new CodeBookDAO(entityManager).getAssetType();
            if (!assetOwnership.isEmpty()) {
                for (Codebook entityList : assetOwnership) {
                    resultList.add(ObjectAdaptorCustomer.adaptToCodebookTO(entityList));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAssetType()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAssetType()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAssetType() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return resultList;
    }

    public List<CBSCustCurrencyInfoTO> getCurrencyInfoByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        List<CBSCustCurrencyInfoTO> currencyInfoTOs = new ArrayList<CBSCustCurrencyInfoTO>();
        try {
            List<CBSCustCurrencyInfo> currencyDetails = new CBSCustCurrencyInfoDAO(entityManager).getCurrencyDetailsByCustomerId(custId);
            if (!currencyDetails.isEmpty()) {
                for (CBSCustCurrencyInfo currencyInfo : currencyDetails) {
                    CBSCustCurrencyInfoTO currencyInfoTO = new CBSCustCurrencyInfoTO();
                    currencyInfoTO = ObjectAdaptorCustomer.adaptToCBSCustCurrencyInfoTO(currencyInfo);
                    currencyInfoTOs.add(currencyInfoTO);
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getCurrencyInfoByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCurrencyInfoByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getCurrencyInfoByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return currencyInfoTOs;
    }

    public CBSCustMISInfoTO getMisInfoByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        CBSCustMISInfoTO mISInfoTO = null;
        try {
            CbsCustMisinfo custMISInfo = new CBSCustMISInfoDAO(entityManager).getCustMisDetailsByCustId(custId);
            if (custMISInfo != null) {
                mISInfoTO = ObjectAdaptorCustomer.adaptToCBSCustMISInfoTO(custMISInfo);
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getMisInfoByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMisInfoByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getMisInfoByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return mISInfoTO;
    }

    public CBSCustMinorInfoTO getMinorInfoByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        CBSCustMinorInfoTO minorInfoTO = null;
        try {
            CBSCustMinorInfo custMinorInfo = new CBSCustMinorInfoDAO(entityManager).getCustMinorDetailsByCustId(custId);
            if (custMinorInfo != null) {
                minorInfoTO = ObjectAdaptorCustomer.adaptToCBSCustMinorInfoTO(custMinorInfo);
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getMinorInfoByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMinorInfoByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getMinorInfoByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return minorInfoTO;
    }

    public CBSCustNREInfoTO getNreInfoByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        CBSCustNREInfoTO nreInfoTO = null;
        try {
            CbsCustNreinfo custNreInfo = new CBSCustNREInfoDAO(entityManager).getCustNreDetailsByCustId(custId);
            if (custNreInfo != null) {
                nreInfoTO = ObjectAdaptorCustomer.adaptToCBSCustNREInfoTO(custNreInfo);
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getNreInfoByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getNreInfoByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getNreInfoByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return nreInfoTO;
    }

    public CBSTradeFinanceInformationTO gettdFInfoByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        CBSTradeFinanceInformationTO financeInformationTO = null;
        try {
            CBSTradeFinanceInformation tradeFinanceInformation = new CBSTradeFinanceInformationDAO(entityManager).getTDFinanceDetailsByCustId(custId);
            if (tradeFinanceInformation != null) {
                financeInformationTO = ObjectAdaptorCustomer.adaptToCBSTradeFinInfoTO(tradeFinanceInformation);
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method gettdFInfoByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method gettdFInfoByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for gettdFInfoByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return financeInformationTO;
    }

    public List<CBSRelatedPersonsDetailsTO> getRelatedPersonInfoByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        List<CBSRelatedPersonsDetailsTO> relatedPersonsDetailsListTO = new ArrayList<CBSRelatedPersonsDetailsTO>();
        try {
            List<CbsRelatedPersonsDetails> personsDetailsList = new CBSRelatedPersonsDetailsDAO(entityManager).getCustRelatedPersonDetailsByCustId(custId);
            if (!personsDetailsList.isEmpty()) {
                for (CbsRelatedPersonsDetails personsDetailsEntity : personsDetailsList) {
                    relatedPersonsDetailsListTO.add(ObjectAdaptorCustomer.adaptToCBSRelatedPersonsDetailsTO(personsDetailsEntity));
                }
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getRelatedPersonInfoByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getRelatedPersonInfoByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getRelatedPersonInfoByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return relatedPersonsDetailsListTO;
    }

    public CBSBuyerSellerLimitDetailsTO getBsLimitDetailsByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        CBSBuyerSellerLimitDetailsTO detailsTO = null;
        try {
            CBSBuyerSellerLimitDetails limitDetails = new CBSBuyerSellerLimitDetailsDAO(entityManager).getBsLimitByCustId(custId);
            if (limitDetails != null) {
                detailsTO = ObjectAdaptorCustomer.adaptToCBSBuyerSellerLmtDtlTO(limitDetails);
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getBsLimitDetailsByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        }  catch (Exception e) {
            logger.error("Exception occured while executing method getBsLimitDetailsByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getBsLimitDetailsByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return detailsTO;
    }

    public CBSCustDeliveryChannelDetailsTO getCusDeliveryChannelDetailsByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        CBSCustDeliveryChannelDetailsTO detailsTO = null;
        try {
            CBSCustDeliveryChannelDetails limitDetails = new CBSCustDeliveryChannelDetailsDAO(entityManager).getCusDeliveryChannelDetailsByCustId(custId);
            if (limitDetails != null) {
                detailsTO = ObjectAdaptorCustomer.adaptToCBSCustDeliveryChannelDtlTO(limitDetails);
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getCusDeliveryChannelDetailsByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCusDeliveryChannelDetailsByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getCusDeliveryChannelDetailsByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return detailsTO;
    }

    public CbsKycDetailsTO getKycDetailsByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        CbsKycDetailsTO detailsTO = null;
        try {
            CbsKycDetails kycDetails = new CbsKycDetailsDAO(entityManager).getKycDetailsByCustId(custId);
            if (kycDetails != null) {
                detailsTO = ObjectAdaptorLoan.adaptToCbsKycDetailsTO(kycDetails);
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getKycDetailsByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getKycDetailsByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getKycDetailsByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return detailsTO;
    }

    public List<CbsKycAssetsTO> getKycAssetsByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsKycAssetsTO> kycAssetsTOs = new ArrayList<CbsKycAssetsTO>();
        try {
            List<CbsKycAssets> kycDetailsList = new CbsKycAssetsDAO(entityManager).getKycAssetsByCustId(custId);
            if (!kycDetailsList.isEmpty()) {
                for (CbsKycAssets kycDetailsEntity : kycDetailsList) {
                    kycAssetsTOs.add(ObjectAdaptorLoan.adaptToCbsKycAssetsTO(kycDetailsEntity));
                }
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getKycAssetsByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getKycAssetsByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getKycAssetsByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return kycAssetsTOs;
    }

    public List<CbsKycLoanTO> getKycLoanByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsKycLoanTO> kycLoanTOs = new ArrayList<CbsKycLoanTO>();
        try {
            List<CbsKycLoan> kycDetailsList = new CbsKycLoanDAO(entityManager).getKycLoanByCustId(custId);
            if (!kycDetailsList.isEmpty()) {
                for (CbsKycLoan kycDetailsEntity : kycDetailsList) {
                    kycLoanTOs.add(ObjectAdaptorLoan.adaptToCbsKycLoanTO(kycDetailsEntity));
                }
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getKycLoanByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getKycLoanByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getKycLoanByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return kycLoanTOs;
    }

    public String saveUpdateCustomer(CBSCustomerMasterDetailTO customerMasterDetailTO,
            CBSCustMinorInfoTO minorInfoTO, CBSCustNREInfoTO nREInfoTO, CBSCustMISInfoTO mISInfoTO,
            List<CBSRelatedPersonsDetailsTO> reletedPersonDetailsTos,
            List<CBSCustIdentityDetailsTo> idTypeList, List<CbsCustAdditionalAddressDetailsTo> addressDetailsList, String function,
            String userName, String brncode) throws ApplicationException {
        String msg = "";
         Date date = new Date();
         SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        UserTransaction ut = context.getUserTransaction();
        CbsCustKycDetailsTo custKycDetailsTo = new CbsCustKycDetailsTo();
        try {
            CBSCustomerMasterDetailDAO masterDetailDAO = new CBSCustomerMasterDetailDAO(entityManager);
            if (function.equalsIgnoreCase("A")) {
                long maxCustId = masterDetailDAO.getMaxCustId() + 1;
                customerMasterDetailTO.setCustomerid(String.valueOf(maxCustId));
                customerMasterDetailTO.setCreationTime(date);
                customerMasterDetailTO.setRecordCreaterID(userName);
                customerMasterDetailTO.setLastChangeTime(date);
                customerMasterDetailTO.setLastChangeUserID(userName);
                customerMasterDetailTO.setPrimaryBrCode(brncode);
                customerMasterDetailTO.setTsCnt("0");
                customerMasterDetailTO.setCustUpdationDate(date);
                customerMasterDetailTO.setAuth("N");
                customerMasterDetailTO.setSuspensionFlg("");
                String custConcatFullName = customerMasterDetailTO.getCustname().trim() + " " + customerMasterDetailTO.getMiddleName().trim();
                custConcatFullName = custConcatFullName.trim() + " " + customerMasterDetailTO.getLastName().trim();
                customerMasterDetailTO.setCustFullName(custConcatFullName.trim());
                CbsCustomerMasterDetail cBSCustomerMasterDtlEntity = ObjectAdaptorCustomer.adaptToCBSCustomerMasterDtlEntity(customerMasterDetailTO);
                ut.begin();
                masterDetailDAO.save(cBSCustomerMasterDtlEntity);

                mISInfoTO.setCustomerId(String.valueOf(maxCustId));
                mISInfoTO.setLastChangeTime(date);
                mISInfoTO.setCreationTime(date);
                mISInfoTO.setRecordCreaterID(userName);
                mISInfoTO.setTsCnt("0");
                mISInfoTO.setStatusCode("A");
                CbsCustMisinfo cBSCustMISInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustMISInfoEntity(mISInfoTO);
                masterDetailDAO.save(cBSCustMISInfoEntity);

                if (customerMasterDetailTO.getMinorflag().equalsIgnoreCase("Y")) {
                    minorInfoTO.setCustomerId(String.valueOf(maxCustId));
                    minorInfoTO.setLastChangeTime(date);
                    minorInfoTO.setRecordCreaterID(userName);
                    minorInfoTO.setCreationTime(date);
                    minorInfoTO.setTsCnt("0");
                    CBSCustMinorInfo cBSCustMinorInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustMinorInfoEntity(minorInfoTO);
                    masterDetailDAO.save(cBSCustMinorInfoEntity);
                }

                if (customerMasterDetailTO.getNriflag().equalsIgnoreCase("N")) {
                    nREInfoTO.setCustomerId(String.valueOf(maxCustId));
                    nREInfoTO.setLastChangeTime(date);
                    nREInfoTO.setRecordCreaterID(userName);
                    nREInfoTO.setCreationTime(date);
                    nREInfoTO.setTsCnt("0");
                    CbsCustNreinfo cBSCustNREInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustNREInfoEntity(nREInfoTO);
                    masterDetailDAO.save(cBSCustNREInfoEntity);
                }
                CbsCustKycDetailsDAO custKycDetailsDAO = new CbsCustKycDetailsDAO(entityManager);
                //New Addition for CbsCustKysDetail At the time of addition
                custKycDetailsTo.setCustomerId(String.valueOf(maxCustId));
                custKycDetailsTo.setTypeOfDocSubmitted(mISInfoTO.getTypeOfDocSubmit());
                custKycDetailsTo.setRiskCategory(customerMasterDetailTO.getOperationalRiskRating());
                custKycDetailsTo.setBrnCode(brncode);
                custKycDetailsTo.setKycCreatedBy(userName);
                custKycDetailsTo.setKycCreationDate(date);
                custKycDetailsTo.setCkycrUpdateFlag("N");
                CbsCustKycDetails custKycDetails = ObjectAdaptorCustomer.adapatToCbsCustKycDetails(custKycDetailsTo);
                custKycDetailsDAO.save(custKycDetails);
                //End




//                if (informationTO.getName() != null) {
//                    informationTO.setCustomerId(String.valueOf(maxCustId));
//                    informationTO.setLastChangeTime(date);
//                    informationTO.setLastChangeUserID(userName);
//                    informationTO.setCreationTime(date);
//                    informationTO.setRecordCreaterID(userName);
//                    informationTO.setTsCnt("0");
//                    CBSTradeFinanceInformation cBSTradeFinInfoEntity = ObjectAdaptorCustomer.adaptToCBSTradeFinInfoEntity(informationTO);
//                    masterDetailDAO.save(cBSTradeFinInfoEntity);
//                }


//                if (buyerSellerLimitDetailsTO.getDraweeCode() != null) {
//                    buyerSellerLimitDetailsTO.setCustomerId(String.valueOf(maxCustId));
//                    buyerSellerLimitDetailsTO.setLastChangeTime(date);
//                    buyerSellerLimitDetailsTO.setLastChangeUserID(userName);
//                    buyerSellerLimitDetailsTO.setRecordCreaterID(userName);
//                    buyerSellerLimitDetailsTO.setCreationTime(date);
//                    buyerSellerLimitDetailsTO.setTsCnt("0");
//                    CBSBuyerSellerLimitDetails cBSBuyerSellerLmtDtlEntity = ObjectAdaptorCustomer.adaptToCBSBuyerSellerLmtDtlEntity(buyerSellerLimitDetailsTO);
//                    masterDetailDAO.save(cBSBuyerSellerLmtDtlEntity);
//                }


//                if (channelDetailsTO.getaTMDebitCardHolder() != null) {
//                    channelDetailsTO.setCustomerId(String.valueOf(maxCustId));
//                    channelDetailsTO.setLastChangeTime(date);
//                    channelDetailsTO.setCreationTime(date);
//                    channelDetailsTO.setLastChangeUserID(userName);
//                    channelDetailsTO.setRecordCreaterID(userName);
//                    channelDetailsTO.setTsCnt("0");
//                    CBSCustDeliveryChannelDetails cBSCustDeliveryChannelDtlEntity = ObjectAdaptorCustomer.adaptToCBSCustDeliveryChannelDtlEntity(channelDetailsTO);
//                    masterDetailDAO.save(cBSCustDeliveryChannelDtlEntity);
//                }


//                if (kycDetailsTO.getEduDetails() != null) {
//                    kycDetailsTO.setCustomerId(String.valueOf(maxCustId));
//                    kycDetailsTO.setLastChangeTime(date);
//                    kycDetailsTO.setCreationTime(date);
//                    kycDetailsTO.setLastChangeUserID(userName);
//                    kycDetailsTO.setRecordCreaterID(userName);
//                    kycDetailsTO.setTsCnt("0");
//                    CbsKycDetails cbsKycDetailsEntity = ObjectAdaptorLoan.adaptToCbsKycDetailsEntity(kycDetailsTO);
//                    masterDetailDAO.save(cbsKycDetailsEntity);
//
//                    if (!assetsTOs.isEmpty()) {
//                        for (CbsKycAssetsTO assetsTO : assetsTOs) {
//                            CbsKycAssetsPKTO pKTO = new CbsKycAssetsPKTO();
//                            pKTO.setCustomerId(String.valueOf(maxCustId));
//                            pKTO.setTxnId(1);
//                            assetsTO.setCbsKycAssetsPKTO(pKTO);
//                            assetsTO.setLastChangeTime(date);
//                            assetsTO.setCreationTime(date);
//                            assetsTO.setLastChangeUserID(userName);
//                            assetsTO.setRecordCreaterID(userName);
//                            assetsTO.setTsCnt("0");
//                            CbsKycAssets cbsKycAssetsEntity = ObjectAdaptorLoan.adaptToCbsKycAssetsEntity(assetsTO);
//                            masterDetailDAO.save(cbsKycAssetsEntity);
//                        }
//
//                    }
//                    if (!loanTOs.isEmpty()) {
//                        for (CbsKycLoanTO loanTO : loanTOs) {
//                            CbsKycLoanPKTO pKTO = new CbsKycLoanPKTO();
//                            pKTO.setCustomerId(String.valueOf(maxCustId));
//                            pKTO.setTxnId(1);
//                            loanTO.setCbsKycLoanPKTO(pKTO);
//                            loanTO.setLastChangeTime(date);
//                            loanTO.setCreationTime(date);
//                            loanTO.setLastChangeUserID(userName);
//                            loanTO.setRecordCreaterID(userName);
//                            loanTO.setTsCnt("0");
//                            CbsKycLoan cbsKycLoanEntity = ObjectAdaptorLoan.adaptToCbsKycLoanEntity(loanTO);
//                            masterDetailDAO.save(cbsKycLoanEntity);
//                        }
//                    }
//
//                }
              if (!reletedPersonDetailsTos.isEmpty()) {
                   int i = 1;
                   for (CBSRelatedPersonsDetailsTO detailsTO : reletedPersonDetailsTos) {
//                        CBSRelatedPersonsDetailsPKTO pKTO = new CBSRelatedPersonsDetailsPKTO();
//                        pKTO.setCustomerId(String.valueOf(maxCustId));
//                        pKTO.setPersonSrNo(++i);
//                        detailsTO.setcBSRelatedPersonsDetailsPKTO(pKTO);
                            detailsTO.setLastChangeTime(date);
                            detailsTO.setLastChangeUserID(userName);
                            detailsTO.setCreationTime(date);
                            detailsTO.setRecordCreaterID(userName);
                            detailsTO.setTsCnt("0");
                        CbsRelatedPersonsDetails cBSRelatedPersonsDetailsEntity = ObjectAdaptorCustomer.adaptToCBSRelatedPersonsDetailsEntity(detailsTO);
                        masterDetailDAO.save(cBSRelatedPersonsDetailsEntity);
                    }
                  }
               
                if (!idTypeList.isEmpty()) {
                    List<CBSCustIdentityDetailsTo> CustIdentityDetailsTo = idTypeList;
                    for (CBSCustIdentityDetailsTo pojo : CustIdentityDetailsTo) {
                        pojo.setCustomerId(String.valueOf(maxCustId));
                        pojo.setEnterBy(userName);
                        pojo.setIdentityExpiryDate(pojo.getIdentityExpiryDate().equalsIgnoreCase("") ? "" : yyyyMMdd.format(ddMMyyyy.parse(pojo.getIdentityExpiryDate())));
                        CbsCustIdentityDetails custIdentityDetailsE = ObjectAdaptorCustomer.adaptToCbsCustIdentityDetailsEntity(pojo);
                        custIdentityDetailsE.setEnterDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date)));
                        masterDetailDAO.save(custIdentityDetailsE);
                    }
                }

                if (!addressDetailsList.isEmpty()) {
                    for (CbsCustAdditionalAddressDetailsTo pojo : addressDetailsList) {
                        pojo.setCustomerId(String.valueOf(maxCustId));
                        pojo.setsNo(String.valueOf((addressDetailsList.indexOf(pojo) + 1)));
                        pojo.setEnterBy(userName);
                        pojo.setEnterDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date)));

                        pojo.setDateofDeclaration(yyyyMMdd.format(date));
                        pojo.setPlaceofDeclaration(ckycrCommonRemote.getBranchCity(Integer.parseInt(brncode)));
                        CbsCustAdditionalAddressDetails custAddressDetailsE = ObjectAdaptorCustomer.adaptToCbsCustAdditionalAddressDetailsE(pojo);
                        masterDetailDAO.save(custAddressDetailsE);
                    }
                }
               
//                if (!currencyInfoTOs.isEmpty()) {
//                    for (CBSCustCurrencyInfoTO currencyInfoTO : currencyInfoTOs) {
//                        currencyInfoTO.getcBSCustCurrencyInfoPKTO().setCustomerId(String.valueOf(maxCustId));
//                        currencyInfoTO.setLastChangeTime(date);
//                        currencyInfoTO.setCreationTime(date);
//                        currencyInfoTO.setLastChangeUserID(userName);
//                        currencyInfoTO.setRecordCreaterID(userName);
//                        currencyInfoTO.setTsCnt("0");
//                        CBSCustCurrencyInfo cBSCustCurrencyInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustCurrencyInfoEntity(currencyInfoTO);
//                        masterDetailDAO.save(cBSCustCurrencyInfoEntity);
//                    }
//                } else {
//                    CBSCustCurrencyInfoPKTO pKTO = new CBSCustCurrencyInfoPKTO();
//                    pKTO.setCustomerId(String.valueOf(maxCustId));
//                    pKTO.setCurrencyCodeType("INR");
//                    CBSCustCurrencyInfoTO infoTO = new CBSCustCurrencyInfoTO();
//                    infoTO.setcBSCustCurrencyInfoPKTO(pKTO);
//                    infoTO.setWithHoldingTax(0.0);
//                    infoTO.setWithHoldingTaxLimit(0.0);
//                    infoTO.setWithHoldingTaxLevel("0.0");
//                    infoTO.setCustInterestRateDebit(0.0);
//                    infoTO.setCustinterestRateCredit(0.0);
//                    infoTO.setcBSCustCurrencyInfoPKTO(pKTO);
//                    infoTO.setLastChangeTime(date);
//                    infoTO.setCreationTime(date);
//                    infoTO.setLastChangeUserID(userName);
//                    infoTO.setRecordCreaterID(userName);
//                    infoTO.setTsCnt("0");
//                    CBSCustCurrencyInfo cBSCustCurrencyInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustCurrencyInfoEntity(infoTO);
//                    masterDetailDAO.save(cBSCustCurrencyInfoEntity);
//                }

                ut.commit();
                 msg = "Record has been successfully saved. Generated Customer id is " + String.valueOf(maxCustId);
            } else if (function.equalsIgnoreCase("M")) {
                CBSCustMISInfoDAO mISInfoDAO = new CBSCustMISInfoDAO(entityManager);
             CbsCustomerMasterDetail customerMasterDetail = (CbsCustomerMasterDetail) masterDetailDAO.findById(new CbsCustomerMasterDetail(), customerMasterDetailTO.getCustomerid());
                if (customerMasterDetail != null) {
                    ut.begin();
                    CbsCustomerMasterDetailHis hisEntity = new CbsCustomerMasterDetailHis();
                    hisEntity.setCustomerid(customerMasterDetail.getCustomerid());
                    hisEntity.setTitle(customerMasterDetail.getTitle());
                    hisEntity.setCustname(customerMasterDetail.getCustname());
                    hisEntity.setShortname(customerMasterDetail.getShortname());
                    hisEntity.setGender(customerMasterDetail.getGender());
                    hisEntity.setMaritalstatus(customerMasterDetail.getMaritalstatus());
                    hisEntity.setCustStatus(customerMasterDetail.getCustStatus());
                    hisEntity.setFathername(customerMasterDetail.getFathername());
                    hisEntity.setMothername(customerMasterDetail.getMothername());
                    hisEntity.setStaffflag(customerMasterDetail.getStaffflag());
                    hisEntity.setStaffid(customerMasterDetail.getStaffid());
                    hisEntity.setDateOfBirth(ymd.format(customerMasterDetail.getDateOfBirth()));
                    hisEntity.setNriflag(customerMasterDetail.getNriflag());
                    hisEntity.setPreferredLanguage(customerMasterDetail.getPreferredLanguage());
                    hisEntity.setNameInPreferredLanguage(customerMasterDetail.getNameInPreferredLanguage());
                    hisEntity.setChgTurnOver(customerMasterDetail.getChgTurnOver());
                    hisEntity.setPurgeAllowed(customerMasterDetail.getPurgeAllowed());
                    hisEntity.setMinorflag(customerMasterDetail.getMinorflag());
                    hisEntity.setPassportNo(customerMasterDetail.getPassportNo());
                    hisEntity.setIssueDate(customerMasterDetail.getIssueDate() != null ? ymd.format(customerMasterDetail.getIssueDate()) : null);
                    hisEntity.setIssuingAuthority(customerMasterDetail.getIssuingAuthority());
                    hisEntity.setExpirydate(customerMasterDetail.getExpirydate() != null ? ymd.format(customerMasterDetail.getExpirydate()) : null);
                    hisEntity.setPlaceOfIssue(customerMasterDetail.getPlaceOfIssue());
                    hisEntity.setMobilenumber(customerMasterDetail.getMobilenumber());
                    hisEntity.setVirtualId(customerMasterDetail.getVirtualId()== null ? "" : customerMasterDetail.getVirtualId());
                    hisEntity.setCommunicationPreference(customerMasterDetail.getCommunicationPreference());
                    //New Addition for Aadhaar No.
                    hisEntity.setAadhaarNo(customerMasterDetail.getAadhaarNo());
                    hisEntity.setLpgId(customerMasterDetail.getLpgId());
                    hisEntity.setAadhaarLpgAcno(customerMasterDetail.getAadhaarLpgAcno());
                    hisEntity.setMandateFlag(customerMasterDetail.getMandateFlag());
                    hisEntity.setMandateDate(customerMasterDetail.getMandateDate());
                    hisEntity.setRegType(customerMasterDetail.getRegType());
                    hisEntity.setAadhaarMode(customerMasterDetail.getAadhaarMode() == null ? "" : customerMasterDetail.getAadhaarMode());
                    hisEntity.setAadhaarBankIin(customerMasterDetail.getAadhaarBankIin()  == null ? "" : customerMasterDetail.getAadhaarBankIin());
                    //for customerMasterDetail
                    hisEntity.setAccountManager(customerMasterDetail.getAccountManager());
                    hisEntity.setAllowSweeps(customerMasterDetail.getAllowSweeps());
                    hisEntity.setTradeFinanceFlag(customerMasterDetail.getTradeFinanceFlag());
                    hisEntity.setSwiftCodeStatus(customerMasterDetail.getSwiftCodeStatus());
                    hisEntity.setSwiftCode(customerMasterDetail.getSwiftCode());
                    hisEntity.setBcbfid(customerMasterDetail.getBcbfid());
                    hisEntity.setCombinedStmtFlag(customerMasterDetail.getCombinedStmtFlag());
                    hisEntity.setStmtFreqType(customerMasterDetail.getStmtFreqType());
                    hisEntity.setStmtFreqWeekNo(customerMasterDetail.getStmtFreqWeekNo());
                    hisEntity.setStmtFreqWeekDay(customerMasterDetail.getStmtFreqWeekDay());
                    hisEntity.setStmtFreqStartDate(customerMasterDetail.getStmtFreqStartDate() != null ? customerMasterDetail.getStmtFreqStartDate().toString() : "");
                    hisEntity.setStmtFreqNP(customerMasterDetail.getStmtFreqNP());
                    hisEntity.setSalary(customerMasterDetail.getSalary() != null && !customerMasterDetail.getSalary().toString().equalsIgnoreCase("") ? Double.parseDouble(customerMasterDetail.getSalary().toString()) : 0.0);
                    hisEntity.setChargeStatus(customerMasterDetail.getChargeStatus());
                    hisEntity.setChargeLevelCode(customerMasterDetail.getChargeLevelCode());
                    hisEntity.setABBChargeCode(customerMasterDetail.getABBChargeCode());
                    hisEntity.setEPSChargeCode(customerMasterDetail.getEPSChargeCode());
                    hisEntity.setPaperRemittance(customerMasterDetail.getPaperRemittance());
                    hisEntity.setDeliveryChannelChargeCode(customerMasterDetail.getDeliveryChannelChargeCode());
                    hisEntity.setAccountLevelCharges(customerMasterDetail.getAccountLevelCharges());
                    hisEntity.setCustLevelCharges(customerMasterDetail.getCustLevelCharges());
                    hisEntity.setTaxSlab(customerMasterDetail.getTaxSlab());
                    hisEntity.setITFileNo(customerMasterDetail.getITFileNo());
                    hisEntity.setTDSCode(customerMasterDetail.getTDSCode());
                    hisEntity.setTDSCustomerId(customerMasterDetail.getTDSCustomerId());
                    hisEntity.setTDSFormReceiveDate(customerMasterDetail.getTDSFormReceiveDate() != null ? ymd.format(customerMasterDetail.getTDSFormReceiveDate()) : null);
                    hisEntity.setTDSExemptionReferenceNo(customerMasterDetail.getTDSExemptionReferenceNo());
                    hisEntity.setTDSExemptionEndDate(customerMasterDetail.getTDSExemptionEndDate() != null ? ymd.format(customerMasterDetail.getTDSExemptionEndDate()) : null);
                    hisEntity.setTDSFloorLimit(customerMasterDetail.getTDSFloorLimit() != null && !customerMasterDetail.getTDSFloorLimit().toString().equalsIgnoreCase("") ? Double.parseDouble(customerMasterDetail.getTDSFloorLimit().toString()) : 0.0);
                    hisEntity.setPANGIRNumber(customerMasterDetail.getPANGIRNumber());
                    hisEntity.setTINNumber(customerMasterDetail.getTINNumber());
                    hisEntity.setSalesTaxNo(customerMasterDetail.getSalesTaxNo());
                    hisEntity.setExciseNo(customerMasterDetail.getExciseNo());
                    hisEntity.setSalesTaxNo(customerMasterDetail.getSalesTaxNo());
                    hisEntity.setVoterIDNo(customerMasterDetail.getVoterIDNo());
                    hisEntity.setDrivingLicenseNo(customerMasterDetail.getDrivingLicenseNo());

                    //for customerMasterDetail Tab
                    hisEntity.setIntroCustomerId(customerMasterDetail.getIntroCustomerId());
                    hisEntity.setCustTitle(customerMasterDetail.getCustTitle());
                    hisEntity.setName(customerMasterDetail.getName());
                    hisEntity.setAddressLine1(customerMasterDetail.getAddressLine1());
                    hisEntity.setAddressLine2(customerMasterDetail.getAddressLine2());
                    hisEntity.setVillage(customerMasterDetail.getVillage());
                    hisEntity.setBlock(customerMasterDetail.getBlock());
                    hisEntity.setCityCode(customerMasterDetail.getCityCode());
                    hisEntity.setStateCode(customerMasterDetail.getStateCode());
                    hisEntity.setPostalCode(customerMasterDetail.getPostalCode());
                    hisEntity.setCountryCode(customerMasterDetail.getCountryCode());

                    hisEntity.setPhoneNumber(customerMasterDetail.getPhoneNumber());
                    hisEntity.setTelexNumber(customerMasterDetail.getTelexNumber());
                    hisEntity.setFaxNumber(customerMasterDetail.getFaxNumber());
                    //     hisEntity.setFinancialYearAndMonth(customerMasterDetail.getCustFinanYear() + " " + customerMasterDetail.getCustFinanMon());
                    hisEntity.setFinancialYearAndMonth(null);
                    hisEntity.setCurrencyCodeType(customerMasterDetail.getCurrencyCodeType());
                    hisEntity.setBusinessAssets(Double.parseDouble(customerMasterDetail.getBusinessAssets() != null && !customerMasterDetail.getBusinessAssets().toString().equalsIgnoreCase("") ? customerMasterDetail.getBusinessAssets().toString() : "0.0"));
                    hisEntity.setPropertyAssets(Double.parseDouble(customerMasterDetail.getPropertyAssets() != null && !customerMasterDetail.getPropertyAssets().toString().equalsIgnoreCase("") ? customerMasterDetail.getPropertyAssets().toString() : "0.0"));
                    hisEntity.setInvestments(Double.parseDouble(customerMasterDetail.getInvestments() != null && !customerMasterDetail.getInvestments().toString().equalsIgnoreCase("") ? customerMasterDetail.getInvestments().toString() : "0.0"));
                    hisEntity.setNetworth(Double.parseDouble(customerMasterDetail.getNetworth() != null && !customerMasterDetail.getNetworth().toString().equalsIgnoreCase("") ? customerMasterDetail.getNetworth().toString() : "0.0"));
                    hisEntity.setDeposits(Double.parseDouble(customerMasterDetail.getDeposits() != null && !customerMasterDetail.getDeposits().toString().equalsIgnoreCase("") ? customerMasterDetail.getDeposits().toString() : "0.0"));
                    hisEntity.setOtherBankCode(customerMasterDetail.getOtherBankCode());
                    hisEntity.setLimitsWithOtherBank(Double.parseDouble(customerMasterDetail.getLimitsWithOtherBank() != null && !customerMasterDetail.getLimitsWithOtherBank().toString().equalsIgnoreCase("") ? customerMasterDetail.getLimitsWithOtherBank().toString() : "0.0"));
                    hisEntity.setFundBasedLimit(Double.parseDouble(customerMasterDetail.getFundBasedLimit() != null && !customerMasterDetail.getFundBasedLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getFundBasedLimit().toString() : "0.0"));
                    hisEntity.setNonFundBasedLimit(Double.parseDouble(customerMasterDetail.getNonFundBasedLimit() != null && !customerMasterDetail.getNonFundBasedLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getNonFundBasedLimit().toString() : "0.0"));
                    hisEntity.setOffLineCustDebitLimit(Double.parseDouble(customerMasterDetail.getOffLineCustDebitLimit() != null && !customerMasterDetail.getOffLineCustDebitLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getOffLineCustDebitLimit().toString() : "0.0"));
                    hisEntity.setSalary(Double.parseDouble(customerMasterDetail.getSalary() != null && !customerMasterDetail.getSalary().toString().equalsIgnoreCase("") ? customerMasterDetail.getSalary().toString() : "0.0"));
                    hisEntity.setCustFinancialDate(customerMasterDetail.getCustFinancialDate() != null ? ymd.format(customerMasterDetail.getCustFinancialDate()) : null);
                    hisEntity.setCreditCard(customerMasterDetail.getCreditCard());
                    hisEntity.setCardNumber(customerMasterDetail.getCardNumber());
                    hisEntity.setCardIssuer(customerMasterDetail.getCardIssuer());
                    hisEntity.setBankName(customerMasterDetail.getBankName());
                    hisEntity.setAcctId(customerMasterDetail.getAcctId());

                    //for AddressInfo tab
                    hisEntity.setPerAddressLine1(customerMasterDetail.getPerAddressLine1());
//                    hisEntity.setPerAddressLine2(customerMasterDetail.getPerAddressLine2());
                    hisEntity.setPerAddressLine2(customerMasterDetail.getPeraddressline2());
                    hisEntity.setPerVillage(customerMasterDetail.getPerVillage());
                    hisEntity.setPerBlock(customerMasterDetail.getPerBlock());
                    hisEntity.setPerCityCode(customerMasterDetail.getPerCityCode());
                    hisEntity.setPerStateCode(customerMasterDetail.getPerStateCode());
                    hisEntity.setPerCountryCode(customerMasterDetail.getPerCountryCode());
                    hisEntity.setPerPostalCode(customerMasterDetail.getPerPostalCode());
                    hisEntity.setPerPhoneNumber(customerMasterDetail.getPerPhoneNumber());
                    hisEntity.setPerFaxNumber(customerMasterDetail.getPerFaxNumber());
                    hisEntity.setPerTelexNumber(customerMasterDetail.getPerTelexNumber());

                    hisEntity.setMailAddressLine1(customerMasterDetail.getMailAddressLine1());
                    hisEntity.setMailAddressLine2(customerMasterDetail.getMailAddressLine2());
                    hisEntity.setMailVillage(customerMasterDetail.getMailVillage());
                    hisEntity.setMailBlock(customerMasterDetail.getMailBlock());
                    hisEntity.setMailCityCode(customerMasterDetail.getMailCityCode());
                    hisEntity.setMailStateCode(customerMasterDetail.getMailStateCode());
                    hisEntity.setMailCountryCode(customerMasterDetail.getMailCountryCode());
                    hisEntity.setMailPostalCode(customerMasterDetail.getMailPostalCode());
                    hisEntity.setMailPhoneNumber(customerMasterDetail.getMailPhoneNumber());
                    hisEntity.setMailFaxNumber(customerMasterDetail.getMailFaxNumber());
                    hisEntity.setMailTelexNumber(customerMasterDetail.getMailTelexNumber());

                    hisEntity.setEmployerid(customerMasterDetail.getEmployerid());
                    hisEntity.setEmployeeNo(customerMasterDetail.getEmployeeNo());
                    hisEntity.setEmpAddressLine1(customerMasterDetail.getEmpAddressLine1());
                    hisEntity.setEmpAddressLine2(customerMasterDetail.getEmpAddressLine2());
                    hisEntity.setEmpVillage(customerMasterDetail.getEmpVillage());
                    hisEntity.setEmpBlock(customerMasterDetail.getEmpBlock());
                    hisEntity.setEmpCityCode(customerMasterDetail.getEmpCityCode());
                    hisEntity.setEmpStateCode(customerMasterDetail.getEmpStateCode());
                    hisEntity.setEmpPostalCode(customerMasterDetail.getEmpPostalCode());
                    hisEntity.setEmpCountryCode(customerMasterDetail.getEmpCountryCode());
                    hisEntity.setEmpPhoneNumber(customerMasterDetail.getEmpPhoneNumber());
                    hisEntity.setEmpTelexNumber(customerMasterDetail.getEmpTelexNumber());
                    hisEntity.setEmpFaxNumber(customerMasterDetail.getEmpFaxNumber());
                    hisEntity.setEmailID(customerMasterDetail.getEmailID());
                    hisEntity.setRecordCreaterID(customerMasterDetail.getRecordCreaterID());
                    hisEntity.setLastChangeTime(date);
                    hisEntity.setLastChangeUserID(customerMasterDetail.getLastChangeUserID());
                    hisEntity.setPrimaryBrCode(customerMasterDetail.getPrimaryBrCode());

                    hisEntity.setOperationalRiskRating(customerMasterDetail.getOperationalRiskRating());
                    hisEntity.setRatingAsOn(customerMasterDetail.getRatingAsOn() != null ? ymd.format(customerMasterDetail.getRatingAsOn()) : null);
                    hisEntity.setCreditRiskRatingInternal(customerMasterDetail.getCreditRiskRatingInternal());
                    hisEntity.setCreditRatingAsOn(customerMasterDetail.getCreditRatingAsOn() != null ? ymd.format(customerMasterDetail.getCreditRatingAsOn()) : null);
                    hisEntity.setExternalRatingShortTerm(customerMasterDetail.getExternalRatingShortTerm());
                    hisEntity.setExternShortRatingAsOn(customerMasterDetail.getExternShortRatingAsOn() != null ? ymd.format(customerMasterDetail.getExternShortRatingAsOn()) : null);
                    hisEntity.setExternalRatingLongTerm(customerMasterDetail.getExternalRatingLongTerm());
                    hisEntity.setExternLongRatingAsOn(customerMasterDetail.getExternLongRatingAsOn() != null ? ymd.format(customerMasterDetail.getExternLongRatingAsOn()) : null);
                    hisEntity.setCustAcquistionDate(customerMasterDetail.getCustAcquistionDate() != null ? ymd.format(customerMasterDetail.getCustAcquistionDate()) : null);
                    hisEntity.setThresoldTransactionLimit(Double.parseDouble(customerMasterDetail.getThresoldTransactionLimit() != null && !customerMasterDetail.getThresoldTransactionLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getThresoldTransactionLimit().toString() : "0.0"));
                    hisEntity.setThresoldLimitUpdationDate(customerMasterDetail.getThresoldLimitUpdationDate() != null ? ymd.format(customerMasterDetail.getThresoldLimitUpdationDate()) : null);
                    hisEntity.setCustUpdationDate(customerMasterDetail.getCustUpdationDate() != null ? ymd.format(customerMasterDetail.getCustUpdationDate()) : null);
                    hisEntity.setCustStatus(customerMasterDetail.getCustStatus());
                    hisEntity.setSuspensionFlg(customerMasterDetail.getSuspensionFlg());
                    hisEntity.setAuth(customerMasterDetail.getAuth());

                    //New Addition
                    hisEntity.setMiddleName(customerMasterDetail.getMiddleName());
                    hisEntity.setLastName(customerMasterDetail.getLastName());
                    hisEntity.setCustFullName(customerMasterDetail.getCustFullName());
                    hisEntity.setGstIdentificationNumber(customerMasterDetail.getGstIdentificationNumber());
                    hisEntity.setSpouseName(customerMasterDetail.getSpouseName());
                    hisEntity.setMaidenName(customerMasterDetail.getMaidenName());
                    hisEntity.setNregaJobCard(customerMasterDetail.getNregaJobCard());
                    hisEntity.setDlExpiry(customerMasterDetail.getDlExpiry());
                    hisEntity.setLegalDocument(customerMasterDetail.getLegalDocument());
                    hisEntity.setIncomeRange(customerMasterDetail.getIncomeRange());
                    hisEntity.setNetworthAsOn(customerMasterDetail.getNetworthAsOn());
                    hisEntity.setQualification(customerMasterDetail.getQualification());
                    hisEntity.setPoliticalExposed(customerMasterDetail.getPoliticalExposed());
                    hisEntity.setJuriAdd1(customerMasterDetail.getJuriAdd1());
                    hisEntity.setJuriAdd2(customerMasterDetail.getJuriAdd2());
                    hisEntity.setJuriCity(customerMasterDetail.getJuriCity());
                    hisEntity.setJuriState(customerMasterDetail.getJuriState());
                    hisEntity.setJuriPostal(customerMasterDetail.getJuriPostal());
                    hisEntity.setJuriCountry(customerMasterDetail.getJuriCountry());
                    hisEntity.setTan(customerMasterDetail.getTan());
                    hisEntity.setCin(customerMasterDetail.getCin());
                    hisEntity.setPerEmail(customerMasterDetail.getPerEmail());
                    hisEntity.setMailEmail(customerMasterDetail.getMailEmail());
                    hisEntity.setNationality(customerMasterDetail.getNationality());
                    hisEntity.setOtherIdentity(customerMasterDetail.getOtherIdentity());
                    hisEntity.setPoa(customerMasterDetail.getPoa());
                    
                    hisEntity.setPerAddType(customerMasterDetail.getPerAddType());
                    hisEntity.setMailAddType(customerMasterDetail.getMailAddType());
                    hisEntity.setMailPoa(customerMasterDetail.getMailPoa());

                    hisEntity.setPerMailAddSameFlagIndicate(customerMasterDetail.getPerMailAddSameFlagIndicate());
                    hisEntity.setJuriAddBasedOnFlag(customerMasterDetail.getJuriAddBasedOnFlag());
                    hisEntity.setJuriAddType(customerMasterDetail.getJuriAddType());
                    hisEntity.setJuriPoa(customerMasterDetail.getJuriPoa());

                    hisEntity.setAcHolderTypeFlag(customerMasterDetail.getAcHolderTypeFlag());
                    hisEntity.setAcHolderType(customerMasterDetail.getAcHolderType());
                    hisEntity.setAcType(customerMasterDetail.getAcType());
//                    hisEntity.setcKYCNo(customerMasterDetail.getcKYCNo());
                    hisEntity.setCKYCNo(customerMasterDetail.getCKYCNo());
                    hisEntity.setFatherMiddleName(customerMasterDetail.getFatherMiddleName());
                    hisEntity.setFatherLastName(customerMasterDetail.getFatherLastName());
                    hisEntity.setSpouseMiddleName(customerMasterDetail.getSpouseMiddleName());
                    hisEntity.setSpouseLastName(customerMasterDetail.getSpouseLastName());
                    hisEntity.setMotherMiddleName(customerMasterDetail.getMotherMiddleName());
                    hisEntity.setMotherLastName(customerMasterDetail.getMotherLastName());
                    hisEntity.setTinIssuingCountry(customerMasterDetail.getTinIssuingCountry());
                    hisEntity.setTinIssuingCountry(customerMasterDetail.getTinIssuingCountry());
                    hisEntity.setCustEntityType(customerMasterDetail.getCustEntityType());
                    hisEntity.setIdentityNo(customerMasterDetail.getIdentityNo());
                    hisEntity.setIdExpiryDate(customerMasterDetail.getIdExpiryDate());

                    hisEntity.setPerDistrict(customerMasterDetail.getPerDistrict());
                    hisEntity.setMailDistrict(customerMasterDetail.getMailDistrict());
                    hisEntity.setEmpDistrict(customerMasterDetail.getEmpDistrict());

                    hisEntity.setPerOtherPOA(customerMasterDetail.getPerOtherPOA());
                    hisEntity.setMailOtherPOA(customerMasterDetail.getMailOtherPOA());
                    hisEntity.setCustEntityType(customerMasterDetail.getCustEntityType());
                    hisEntity.setJuriOtherPOA(customerMasterDetail.getJuriOtherPOA());
                    hisEntity.setFatherSpouseFlag(customerMasterDetail.getFatherSpouseFlag());
                    hisEntity.setCustImage(customerMasterDetail.getCustImage());
                    hisEntity.setSuspensionFlg(customerMasterDetail.getSuspensionFlg());
                    //END

                    masterDetailDAO.save(hisEntity);
                    
                    
                    customerMasterDetailTO.setAuth("N");
                    customerMasterDetailTO.setSuspensionFlg(customerMasterDetail.getSuspensionFlg());
                    customerMasterDetailTO.setRecordCreaterID(userName);
                    customerMasterDetailTO.setLastChangeTime(date);
                    customerMasterDetailTO.setCreationTime(customerMasterDetail.getCreationTime());
                    customerMasterDetailTO.setLastChangeUserID(userName);
                    customerMasterDetailTO.setPrimaryBrCode(brncode);
                    String custConcatFullName = customerMasterDetailTO.getCustname().trim() + " " + customerMasterDetailTO.getMiddleName().trim();
                    custConcatFullName = custConcatFullName.trim() + " " + customerMasterDetailTO.getLastName().trim();
                    customerMasterDetailTO.setCustFullName(custConcatFullName.trim());

                    customerMasterDetailTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_customer_master_detail", customerMasterDetail.getCustomerid()) + 1));
                    CbsCustomerMasterDetail cBSCustomerMasterDtlEntity = ObjectAdaptorCustomer.adaptToCBSCustomerMasterDtlEntity(customerMasterDetailTO);

                    masterDetailDAO.update(cBSCustomerMasterDtlEntity);

                    ut.commit();
                    CbsCustMisinfo misInfo = (CbsCustMisinfo) mISInfoDAO.findById(new CbsCustMisinfo(), customerMasterDetailTO.getCustomerid());
                    if (misInfo != null) {
                        //save misinfo history
                        ut.begin();
                        CbsCustMisinfoHis misHis = new CbsCustMisinfoHis();
                        misHis.setCustomerId(misInfo.getCustomerId());
                        misHis.setType(misInfo.getType());
                        misHis.setGroupID(misInfo.getGroupID());
                        misHis.setStatusCode(misInfo.getStatusCode());
                        misHis.setStatusAsOn(misInfo.getStatusAsOn());
                        misHis.setOccupationCode(misInfo.getOccupationCode());
                        misHis.setConstitutionCode(misInfo.getConstitutionCode());
                        misHis.setCasteCode(misInfo.getCasteCode());
                        misHis.setCommunityCode(misInfo.getCommunityCode());
                        misHis.setHealthCode(misInfo.getHealthCode());
                        misHis.setBusinessSegment(misInfo.getBusinessSegment());
                        misHis.setSalesTurnover(Double.parseDouble(misInfo.getSalesTurnover() != null && !misInfo.getSalesTurnover().toString().equalsIgnoreCase("") ? misInfo.getSalesTurnover().toString() : "0.0"));
                        misHis.setLastChangeUserID(userName);
                        misHis.setLastChangeTime(date);
                        //New Addition
                        misHis.setIncorporationDate(misInfo.getIncorporationDate());
                        misHis.setIncorporationPlace(misInfo.getIncorporationPlace());
                        misHis.setCommencementDate(misInfo.getCommencementDate());
                        misHis.setMisJuriResidence(misInfo.getMisJuriResidence());
                        misHis.setMisTin(misInfo.getMisTin());
                        misHis.setMisCity(misInfo.getMisCity());
                        misHis.setMisBirthCountry(misInfo.getMisBirthCountry());

                        masterDetailDAO.save(misHis);

                        //update misinfo
                        mISInfoTO.setLastChangeTime(date);
                        mISInfoTO.setLastChangeUserID(userName);
                        mISInfoTO.setCreationTime(misInfo.getCreationTime());
                        mISInfoTO.setRecordCreaterID(misInfo.getRecordCreaterID());
                        mISInfoTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_cust_misinfo", customerMasterDetail.getCustomerid()) + 1));
                        mISInfoTO.setStatusCode("M");
                        CbsCustMisinfo cBSCustMISInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustMISInfoEntity(mISInfoTO);
                        masterDetailDAO.update(cBSCustMISInfoEntity);
                        ut.commit();
                    }
                    if (misInfo == null) {
                        mISInfoTO.setLastChangeTime(date);
                        mISInfoTO.setLastChangeUserID(userName);
                        mISInfoTO.setCreationTime(date);
                        mISInfoTO.setRecordCreaterID(userName);
                        mISInfoTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_cust_misinfo", customerMasterDetail.getCustomerid()) + 1));
                        mISInfoTO.setStatusCode("M");
                        CbsCustMisinfo cBSCustMISInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustMISInfoEntity(mISInfoTO);
                        ut.begin();
                        masterDetailDAO.save(cBSCustMISInfoEntity);
                        ut.commit();
                    }
//                    if (customerMasterDetailTO.getMinorflag().equalsIgnoreCase("Y")) {
                    CBSCustMinorInfoDAO minorInfoDAO = new CBSCustMinorInfoDAO(entityManager);
                    CBSCustMinorInfo minorInfo = (CBSCustMinorInfo) minorInfoDAO.findById(new CBSCustMinorInfo(), customerMasterDetailTO.getCustomerid());
                    if (minorInfo != null) {
                        ut.begin();
                        // update minor his
                        CBSCustMinorInfoHis minorHis = new CBSCustMinorInfoHis();
                        minorHis.setCustomerId(minorInfo.getCustomerId());
                        minorHis.setDateOfBirth(minorInfo.getDateOfBirth());
                        minorHis.setMajorityDate(minorInfo.getMajorityDate());
                        minorHis.setGuardianCode(minorInfo.getGuardianCode());
                        minorHis.setGuardianTitle(minorInfo.getGuardianTitle());
                        minorHis.setGuardianName(minorInfo.getGuardianName());
                        minorHis.setLocalAddress1(minorInfo.getLocalAddress1());
                        minorHis.setLocalAddress2(minorInfo.getLocalAddress2());
                        minorHis.setVillage(minorInfo.getVillage());
                        minorHis.setBlock(minorInfo.getBlock());
                        minorHis.setCityCode(minorInfo.getCityCode());
                        minorHis.setStateCode(minorInfo.getStateCode());
                        minorHis.setPostalCode(minorInfo.getPostalCode());
                        minorHis.setCountryCode(minorInfo.getCountryCode());
                        minorHis.setPhoneNumber(minorInfo.getPhoneNumber());
                        minorHis.setMobileNumber(minorInfo.getMobileNumber());
                        minorHis.setEmailID(minorInfo.getEmailID());
                        
                        // new added code on 25/01/2019
                        minorHis.setLastChangeTime(date);
                        minorHis.setLastChangeUserID(userName);
                        minorHis.setRecordCreaterID(minorInfo.getRecordCreaterID());
                        
                        masterDetailDAO.save(minorHis);

                        //update minor info
                        minorInfoTO.setGuardianCode(minorInfoTO.getGuardianCode());
                        minorInfoTO.setMajorityDate(minorInfoTO.getMajorityDate());

                        minorInfoTO.setLastChangeTime(date);
                        minorInfoTO.setLastChangeUserID(userName);
                        minorInfoTO.setRecordCreaterID(minorInfo.getRecordCreaterID());
                        minorInfoTO.setCreationTime(minorInfo.getCreationTime());
                        minorInfoTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_cust_minorinfo", customerMasterDetail.getCustomerid()) + 1));
                        CBSCustMinorInfo cBSCustMinorInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustMinorInfoEntity(minorInfoTO);
                        masterDetailDAO.update(cBSCustMinorInfoEntity);
                        ut.commit();
                    }
                    if (minorInfo == null) {
                        ut.begin();
                        minorInfoTO.setLastChangeTime(date);
                        minorInfoTO.setLastChangeUserID(userName);
                        minorInfoTO.setRecordCreaterID(userName);
                        minorInfoTO.setCreationTime(date);
                        minorInfoTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_cust_minorinfo", customerMasterDetail.getCustomerid()) + 1));
                        CBSCustMinorInfo cBSCustMinorInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustMinorInfoEntity(minorInfoTO);
                       
                        masterDetailDAO.save(cBSCustMinorInfoEntity);
                        ut.commit();
                    }
//                    }
//                    if (customerMasterDetailTO.getNriflag().equalsIgnoreCase("Y")) {
                    // save nreInfo History
                    CbsCustNreinfo nreInfo = (CbsCustNreinfo) mISInfoDAO.findById(new CbsCustNreinfo(), customerMasterDetailTO.getCustomerid());
                    if (nreInfo != null) {
                        ut.begin();
                        CbsCustNreinfoHis nREInfoHis = new CbsCustNreinfoHis();
                        nREInfoHis.setCustomerId(nreInfo.getCustomerId());
                        nREInfoHis.setNonResidentDate(nreInfo.getNonResidentDate());
                        nREInfoHis.setCountryCode(nreInfo.getCountryCode());
                        nREInfoHis.setCountryType(nreInfo.getCountryType());
                        nREInfoHis.setLocalContPersonCode(nreInfo.getLocalContPersonCode());
                        nREInfoHis.setLocalPersonTitle(nreInfo.getLocalPersonTitle());
                        nREInfoHis.setLocalContPersonName(nreInfo.getLocalContPersonName());
                        nREInfoHis.setLocalAddress1(nreInfo.getLocalAddress1());
                        nREInfoHis.setLocalAddress2(nreInfo.getLocalAddress2());
                        nREInfoHis.setVillage(nreInfo.getVillage());
                        nREInfoHis.setBlock(nreInfo.getBlock());
                        nREInfoHis.setCityCode(nreInfo.getCityCode());
                        nREInfoHis.setStateCode(nreInfo.getStateCode());
                        nREInfoHis.setPostalCode(nreInfo.getPostalCode());
                        nREInfoHis.setCountryCode(nreInfo.getCountryCode());
                        nREInfoHis.setPhoneNumber(nreInfo.getPhoneNumber());
                        nREInfoHis.setNreEmail(nreInfo.getNreEmail());
                        nREInfoHis.setMobileNumber(nreInfo.getMobileNumber());
                        nREInfoHis.setNonResidentEndDate(nreInfo.getNonResidentEndDate());

                        masterDetailDAO.save(nREInfoHis);
                        //update nreInfo
//                            nREInfoTO.setCustomerId(customerMasterDetailTO.getCustomerid());
//                            nREInfoTO.setNonResidentDate(nREInfoTO.getNonResidentDate());
//                            nREInfoTO.setCountryCode(nREInfoTO.getCountryCode());
//                            nREInfoTO.setLocalContPersonCode(nREInfoTO.getLocalContPersonCode());
//                            nREInfoTO.setLocalAddress1(nREInfoTO.getLocalAddress1());
//                            nREInfoTO.setLocalAddress2(nREInfoTO.getLocalAddress2());
//                            nREInfoTO.setVillage(nREInfoTO.getVillage());
//                            nREInfoTO.setCityCode(nREInfoTO.getCityCode());
//                            nREInfoTO.setStateCode(nREInfoTO.getStateCode());
//                            nREInfoTO.setPostalCode(nREInfoTO.getPostalCode());
//                            nREInfoTO.setPhoneNumber(nREInfoTO.get);
//                            nREInfoTO.setMobileNumber(userName);
//                            nREInfoTO.setNreEmial(msg);
//                            nREInfoTO.setNonResidentEndDate(date);

                        nREInfoTO.setLastChangeTime(date);
                        nREInfoTO.setLastChangeUserID(userName);
                        nREInfoTO.setRecordCreaterID(nreInfo.getRecordCreaterID());
                        nREInfoTO.setCreationTime(nreInfo.getCreationTime());
                        nREInfoTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_cust_nreinfo", customerMasterDetail.getCustomerid()) + 1));
                        CbsCustNreinfo cBSCustNREInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustNREInfoEntity(nREInfoTO);
                        masterDetailDAO.update(cBSCustNREInfoEntity);
                        ut.commit();

                    }
                    if (nreInfo == null) {
                        ut.begin();
                        nREInfoTO.setLastChangeTime(date);
                        nREInfoTO.setLastChangeUserID(userName);
                        nREInfoTO.setRecordCreaterID(userName);
                        nREInfoTO.setCreationTime(date);
                        nREInfoTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_cust_nreinfo", customerMasterDetail.getCustomerid()) + 1));
                        CbsCustNreinfo cBSCustNREInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustNREInfoEntity(nREInfoTO);
                        
                        masterDetailDAO.save(cBSCustNREInfoEntity);
                        ut.commit();
                    }
//                    }

//                    if (informationTO.getName() != null) {
//                        CBSTradeFinanceInformation tradeFinanceInfo = (CBSTradeFinanceInformation) mISInfoDAO.findById(new CBSTradeFinanceInformation(), customerMasterDetailTO.getCustomerid());
//                        if (tradeFinanceInfo != null) {
//                            ut.begin();
//                            //save tfInfo history
//                            CBSTradeFinanceInformationHis tfInfoHis = new CBSTradeFinanceInformationHis();
//                            tfInfoHis.setCustomerId(tradeFinanceInfo.getCustomerId());
//                            tfInfoHis.setName(tradeFinanceInfo.getName());
//                            tfInfoHis.setAddressLine1(tradeFinanceInfo.getAddressLine1());
//                            tfInfoHis.setAddressLine2(tradeFinanceInfo.getAddressLine2());
//                            tfInfoHis.setCityCode(tradeFinanceInfo.getCityCode());
//                            tfInfoHis.setPostalCode(tradeFinanceInfo.getPostalCode());
//                            tfInfoHis.setCountryCode(tradeFinanceInfo.getCountryCode());
//                            tfInfoHis.setPhoneNumber(tradeFinanceInfo.getPhoneNumber());
//                            tfInfoHis.setFaxNumber(tradeFinanceInfo.getFaxNumber());
//                            tfInfoHis.setTelexNumber(tradeFinanceInfo.getTelexNumber());
//                            tfInfoHis.setNative1(tradeFinanceInfo.getNative1());
//                            tfInfoHis.setInlandTradeAllowed(tradeFinanceInfo.getInlandTradeAllowed());
//                            tfInfoHis.setReviewDate(tradeFinanceInfo.getReviewDate());
//                            tfInfoHis.setPresentOutstandingLiability(Double.parseDouble(tradeFinanceInfo.getPresentOutstandingLiability() != null && !tradeFinanceInfo.getPresentOutstandingLiability().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getPresentOutstandingLiability().toString() : "0.0"));
//                            tfInfoHis.setIndustryType(tradeFinanceInfo.getIndustryType());
//                            tfInfoHis.setType(tradeFinanceInfo.getType());
//                            tfInfoHis.setExportUnitFlag(tradeFinanceInfo.getExportUnitFlag());
//                            tfInfoHis.setStatus(tradeFinanceInfo.getStatus());
//                            tfInfoHis.setPartyConstitution(tradeFinanceInfo.getPartyConstitution());
//                            tfInfoHis.setSpecialParty(tradeFinanceInfo.getSpecialParty());
//                            tfInfoHis.setPartyType(tradeFinanceInfo.getPartyType());
//                            tfInfoHis.setProductionCycle(tradeFinanceInfo.getProductionCycle());
//                            tfInfoHis.setTradeExpCode(tradeFinanceInfo.getTradeExpCode());
//                            tfInfoHis.setCodeGivenByCentralBANK(tradeFinanceInfo.getCodeGivenByCentralBANK());
//                            tfInfoHis.setCodeGivenByTradeAuthority(tradeFinanceInfo.getCodeGivenByTradeAuthority());
//                            tfInfoHis.setDCMargin(Double.parseDouble(tradeFinanceInfo.getDCMargin() != null && !tradeFinanceInfo.getDCMargin().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getDCMargin().toString() : "0.0"));
//                            tfInfoHis.setDCSanctioningAuthority(tradeFinanceInfo.getDCSanctioningAuthority());
//                            tfInfoHis.setDCSanctionExpiryDate(tradeFinanceInfo.getDCSanctionExpiryDate());
//                            tfInfoHis.setDCNextNumberCode(tradeFinanceInfo.getDCNextNumberCode());
//                            tfInfoHis.setDocumentCreditLimit(Double.parseDouble(tradeFinanceInfo.getDocumentCreditLimit() != null && !tradeFinanceInfo.getDocumentCreditLimit().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getDocumentCreditLimit().toString() : "0.0"));
//                            tfInfoHis.setFCMargin(Double.parseDouble(tradeFinanceInfo.getFCMargin() != null && !tradeFinanceInfo.getFCMargin().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getFCMargin().toString() : "0.0"));
//                            tfInfoHis.setFCSanctioningAuthority(tradeFinanceInfo.getFCSanctioningAuthority());
//                            tfInfoHis.setFCSanctionExpiryDate(tradeFinanceInfo.getFCSanctionExpiryDate());
//                            tfInfoHis.setFCNextNumberCode(tradeFinanceInfo.getFCNextNumberCode());
//                            tfInfoHis.setForwardContractLimit(Double.parseDouble(tradeFinanceInfo.getForwardContractLimit() != null && !tradeFinanceInfo.getForwardContractLimit().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getForwardContractLimit().toString() : "0.0"));
//                            tfInfoHis.setPCMargin(Double.parseDouble(tradeFinanceInfo.getPCMargin() != null && !tradeFinanceInfo.getPCMargin().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getPCMargin().toString() : "0.0"));
//                            tfInfoHis.setPCSanctioningAuthority(tradeFinanceInfo.getPCSanctioningAuthority());
//                            tfInfoHis.setPCSanctionExpiryDate(tradeFinanceInfo.getPCSanctionExpiryDate());
//                            tfInfoHis.setPCNextNumberCode(tradeFinanceInfo.getPCNextNumberCode());
//                            tfInfoHis.setPackingContractLimit(Double.parseDouble(tradeFinanceInfo.getPackingContractLimit() != null && !tradeFinanceInfo.getPackingContractLimit().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getPackingContractLimit().toString() : "0.0"));
//                            tfInfoHis.setBGMargin(Double.parseDouble(tradeFinanceInfo.getBGMargin() != null && !tradeFinanceInfo.getBGMargin().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getBGMargin().toString() : "0.0"));
//                            tfInfoHis.setBGSanctioningAuthority(tradeFinanceInfo.getBGSanctioningAuthority());
//                            tfInfoHis.setBGSanctionExpiryDate(tradeFinanceInfo.getBGSanctionExpiryDate());
//                            tfInfoHis.setBGNextNumberCode(tradeFinanceInfo.getBGNextNumberCode());
//                            tfInfoHis.setBankGuaranteeLimit(Double.parseDouble(tradeFinanceInfo.getBankGuaranteeLimit() != null && !tradeFinanceInfo.getBankGuaranteeLimit().toString().equalsIgnoreCase("") ? tradeFinanceInfo.getBankGuaranteeLimit().toString() : "0.0"));
//                            masterDetailDAO.save(tfInfoHis);
//                            //update trade finance info
//                            informationTO.setLastChangeTime(date);
//                            informationTO.setLastChangeUserID(userName);
//                            informationTO.setCreationTime(tradeFinanceInfo.getCreationTime());
//                            informationTO.setRecordCreaterID(tradeFinanceInfo.getRecordCreaterID());
//                            informationTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_trade_finance_information", customerMasterDetail.getCustomerid()) + 1));
//                            CBSTradeFinanceInformation cBSTradeFinInfoEntity = ObjectAdaptorCustomer.adaptToCBSTradeFinInfoEntity(informationTO);
//                            masterDetailDAO.update(cBSTradeFinInfoEntity);
//                            ut.commit();
//                        }
//                        if (tradeFinanceInfo == null) {
//                            informationTO.setLastChangeTime(date);
//                            informationTO.setLastChangeUserID(userName);
//                            informationTO.setCreationTime(date);
//                            informationTO.setRecordCreaterID(userName);
//                            informationTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_trade_finance_information", customerMasterDetail.getCustomerid()) + 1));
//                            CBSTradeFinanceInformation cBSTradeFinInfoEntity = ObjectAdaptorCustomer.adaptToCBSTradeFinInfoEntity(informationTO);
//                            ut.begin();
//                            masterDetailDAO.save(cBSTradeFinInfoEntity);
//                            ut.commit();
//                        }
//
//                    }
//                    if (buyerSellerLimitDetailsTO.getDraweeCode() != null) {
//                        CBSBuyerSellerLimitDetails bsLimitInfo = (CBSBuyerSellerLimitDetails) mISInfoDAO.findById(new CBSBuyerSellerLimitDetails(), customerMasterDetailTO.getCustomerid());
//                        if (bsLimitInfo != null) {
//                            ut.begin();
//                            //save bsLimitInfo History
//                            CBSBuyerSellerLimitDetailsHis bsLimitHis = new CBSBuyerSellerLimitDetailsHis();
//                            bsLimitHis.setCustomerId(bsLimitInfo.getCustomerId());
//                            bsLimitHis.setDraweeCode(bsLimitInfo.getDraweeCode());
//                            bsLimitHis.setBillType(bsLimitInfo.getBillType());
//                            bsLimitHis.setStaus(bsLimitInfo.getStaus());
//                            bsLimitHis.setImpCurrencyCodeCCY(bsLimitInfo.getImpCurrencyCodeCCY());
//                            bsLimitHis.setImportLimit(Double.parseDouble(bsLimitInfo.getImportLimit() != null && !bsLimitInfo.getImportLimit().toString().equalsIgnoreCase("") ? bsLimitInfo.getImportLimit().toString() : "0.0"));
//                            bsLimitHis.setUtilisedImportLimit(Double.parseDouble(bsLimitInfo.getUtilisedImportLimit() != null && !bsLimitInfo.getUtilisedImportLimit().toString().equalsIgnoreCase("") ? bsLimitInfo.getUtilisedImportLimit().toString() : "0.0"));
//                            bsLimitHis.setBuyLimit(Double.parseDouble(bsLimitInfo.getBuyLimit() != null && !bsLimitInfo.getBuyLimit().toString().equalsIgnoreCase("") ? bsLimitInfo.getBuyLimit().toString() : "0.0"));
//                            bsLimitHis.setUtilisedBuyLimit(Double.parseDouble(bsLimitInfo.getUtilisedBuyLimit() != null && !bsLimitInfo.getUtilisedBuyLimit().toString().equalsIgnoreCase("") ? bsLimitInfo.getUtilisedBuyLimit().toString() : "0.0"));
//                            bsLimitHis.setExpCurrencyCodeCCY(bsLimitInfo.getExpCurrencyCodeCCY());
//                            bsLimitHis.setExportLimit(Double.parseDouble(bsLimitInfo.getExportLimit() != null && !bsLimitInfo.getExportLimit().toString().equalsIgnoreCase("") ? bsLimitInfo.getExportLimit().toString() : "0.0"));
//                            bsLimitHis.setUtilisedExportLimit(Double.parseDouble(bsLimitInfo.getUtilisedExportLimit() != null && !bsLimitInfo.getUtilisedExportLimit().toString().equalsIgnoreCase("") ? bsLimitInfo.getUtilisedExportLimit().toString() : "0.0"));
//                            bsLimitHis.setSellLimit(Double.parseDouble(bsLimitInfo.getSellLimit() != null && !bsLimitInfo.getSellLimit().toString().equalsIgnoreCase("") ? bsLimitInfo.getSellLimit().toString() : "0.0"));
//                            bsLimitHis.setUtilisedSellLimit(Double.parseDouble(bsLimitInfo.getUtilisedSellLimit() != null && !bsLimitInfo.getUtilisedSellLimit().toString().equalsIgnoreCase("") ? bsLimitInfo.getUtilisedSellLimit().toString() : "0.0"));
//
//                            masterDetailDAO.save(bsLimitHis);
//                            //update bslimit info
//                            buyerSellerLimitDetailsTO.setLastChangeTime(date);
//                            buyerSellerLimitDetailsTO.setLastChangeUserID(userName);
//                            buyerSellerLimitDetailsTO.setRecordCreaterID(bsLimitInfo.getRecordCreaterID());
//                            buyerSellerLimitDetailsTO.setCreationTime(bsLimitInfo.getCreationTime());
//                            buyerSellerLimitDetailsTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_buyer_seller_limit_details", customerMasterDetail.getCustomerid()) + 1));
//                            CBSBuyerSellerLimitDetails cBSBuyerSellerLmtDtlEntity = ObjectAdaptorCustomer.adaptToCBSBuyerSellerLmtDtlEntity(buyerSellerLimitDetailsTO);
//                            masterDetailDAO.update(cBSBuyerSellerLmtDtlEntity);
//                            ut.commit();
//
//                        }
//                        if (bsLimitInfo == null) {
//                            buyerSellerLimitDetailsTO.setLastChangeTime(date);
//                            buyerSellerLimitDetailsTO.setLastChangeUserID(userName);
//                            buyerSellerLimitDetailsTO.setRecordCreaterID(userName);
//                            buyerSellerLimitDetailsTO.setCreationTime(date);
//                            buyerSellerLimitDetailsTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_buyer_seller_limit_details", customerMasterDetail.getCustomerid()) + 1));
//                            CBSBuyerSellerLimitDetails cBSBuyerSellerLmtDtlEntity = ObjectAdaptorCustomer.adaptToCBSBuyerSellerLmtDtlEntity(buyerSellerLimitDetailsTO);
//                            ut.begin();
//                            masterDetailDAO.save(cBSBuyerSellerLmtDtlEntity);
//                            ut.commit();
//                        }
//
//                    }
//                    if (channelDetailsTO.getaTMDebitCardHolder() != null) {
//                        CBSCustDeliveryChannelDetails channelDetails = (CBSCustDeliveryChannelDetails) mISInfoDAO.findById(new CBSCustDeliveryChannelDetails(), customerMasterDetailTO.getCustomerid());
//                        if (channelDetails != null) {
//                            ut.begin();
//                            CBSCustDeliveryChannelDetailsHis channelDetailsHis = new CBSCustDeliveryChannelDetailsHis();
//                            channelDetailsHis.setCustomerId(channelDetails.getCustomerId());
//                            channelDetailsHis.setATMDebitCardHolder(channelDetails.getATMDebitCardHolder());
//                            channelDetailsHis.setCreditCardEnabled(channelDetails.getCreditCardEnabled());
//                            channelDetailsHis.setMobileBankingEnabled(channelDetails.getMobileBankingEnabled());
//                            channelDetailsHis.setSmsBankingEnabled(channelDetails.getSmsBankingEnabled());
//                            channelDetailsHis.setINetBankingEnabled(channelDetails.getINetBankingEnabled());
//                            channelDetailsHis.setTeleBankingEnabled(channelDetails.getTeleBankingEnabled());
//                            channelDetailsHis.setSelfServiceEnabled(channelDetails.getSelfServiceEnabled());
//                            channelDetailsHis.setECSEnabled(channelDetails.getECSEnabled());
//                            channelDetailsHis.setLastChangeTime(channelDetails.getLastChangeTime());
//                            channelDetailsHis.setLastChangeUserID(channelDetails.getLastChangeUserID());
//                            channelDetailsTO.setRecordCreaterID(channelDetails.getRecordCreaterID());
//                            masterDetailDAO.save(channelDetailsHis);
//
//                            //update channel details
//                            channelDetailsTO.setLastChangeTime(date);
//                            channelDetailsTO.setLastChangeUserID(userName);
//                            channelDetailsTO.setCreationTime(channelDetails.getCreationTime());
//                            channelDetailsTO.setRecordCreaterID(channelDetails.getRecordCreaterID());
//                            channelDetailsTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_cust_delivery_channel_details", customerMasterDetail.getCustomerid()) + 1));
//                            CBSCustDeliveryChannelDetails cBSCustDeliveryChannelDtlEntity = ObjectAdaptorCustomer.adaptToCBSCustDeliveryChannelDtlEntity(channelDetailsTO);
//                            masterDetailDAO.update(cBSCustDeliveryChannelDtlEntity);
//                            ut.commit();
//
//                        }
//                        if (channelDetails == null) {
//                            channelDetailsTO.setLastChangeTime(date);
//                            channelDetailsTO.setLastChangeUserID(userName);
//                            channelDetailsTO.setCreationTime(date);
//                            channelDetailsTO.setRecordCreaterID(userName);
//                            channelDetailsTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_cust_delivery_channel_details", customerMasterDetail.getCustomerid()) + 1));
//                            CBSCustDeliveryChannelDetails cBSCustDeliveryChannelDtlEntity = ObjectAdaptorCustomer.adaptToCBSCustDeliveryChannelDtlEntity(channelDetailsTO);
//                            ut.begin();
//                            masterDetailDAO.save(cBSCustDeliveryChannelDtlEntity);
//                            ut.commit();
//                        }
//
//                    }
//                    if (kycDetailsTO.getEduDetails() != null) {
//                        CbsKycDetails kycDetails = (CbsKycDetails) mISInfoDAO.findById(new CbsKycDetails(), customerMasterDetailTO.getCustomerid());
//                        if (kycDetails != null) {
//                            ut.begin();
//                            CbsKycDetailsHis kycDetailsHis = new CbsKycDetailsHis();
//                            kycDetailsHis.setDependents(kycDetails.getDependents());
//                            kycDetailsHis.setNoMales10(kycDetails.getNoMales10() == null || kycDetails.getNoMales10().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoMales10());
//                            kycDetailsHis.setNoMales20(kycDetails.getNoMales20() == null || kycDetails.getNoMales20().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoMales20());
//                            kycDetailsHis.setNoMales45(kycDetails.getNoMales45() == null || kycDetails.getNoMales45().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoMales45());
//                            kycDetailsHis.setNoMales60(kycDetails.getNoMales60() == null || kycDetails.getNoMales60().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoMales60());
//                            kycDetailsHis.setNoMalesAbove61(kycDetails.getNoMalesAbove61() == null || kycDetails.getNoMalesAbove61().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoMalesAbove61());
//                            kycDetailsHis.setNoFem10(kycDetails.getNoFem10() == null || kycDetails.getNoFem10().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoFem10());
//                            kycDetailsHis.setNoFem20(kycDetails.getNoFem20() == null || kycDetails.getNoFem20().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoFem20());
//                            kycDetailsHis.setNoFem45(kycDetails.getNoFem45() == null || kycDetails.getNoFem45().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoFem45());
//                            kycDetailsHis.setNoFem60(kycDetails.getNoFem60() == null || kycDetails.getNoFem60().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoFem60());
//                            kycDetailsHis.setNoFemAbove61(kycDetails.getNoFemAbove61() == null || kycDetails.getNoFemAbove61().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoFemAbove61());
//
//
//
//                            kycDetailsHis.setNoChild(kycDetails.getNoChild() == null || kycDetails.getNoChild().toString().equalsIgnoreCase("") ? 0 : kycDetails.getNoChild());
//
//                            kycDetailsHis.setAbRelName1(kycDetails.getAbRelName1());
//                            kycDetailsHis.setAbRelName2(kycDetails.getAbRelName2());
//                            kycDetailsHis.setAbRelName3(kycDetails.getAbRelName3());
//                            kycDetailsHis.setAbRelAdd1(kycDetails.getAbRelAdd1());
//                            kycDetailsHis.setAbRelAdd2(kycDetails.getAbRelAdd2());
//                            kycDetailsHis.setAbRelAdd3(kycDetails.getAbRelAdd3());
//
//                            kycDetailsHis.setAbVisited(kycDetails.getAbVisited() == null || kycDetails.getAbVisited().toString().equalsIgnoreCase("") ? 0 : kycDetails.getAbVisited());
//
//                            kycDetailsHis.setBankRel(kycDetails.getBankRel() != null ? kycDetails.getBankRel() : '\0');
//                            kycDetailsHis.setCreditcard(kycDetails.getCreditcard() != null ? kycDetails.getCreditcard() : '\0');
//                            kycDetailsHis.setDirRel(kycDetails.getDirRel() != null ? kycDetails.getDirRel() : '\0');
//                            kycDetailsHis.setMedinsurence(kycDetails.getMedinsurence() != null ? kycDetails.getMedinsurence() : '\0');
//                            kycDetailsHis.setCreationTime(kycDetails.getCreationTime());
//                            kycDetailsHis.setLastChangeTime(kycDetails.getLastChangeTime());
//                            kycDetailsHis.setLastChangeUserID(kycDetails.getLastChangeUserID());
//                            kycDetailsHis.setRecordCreaterID(kycDetails.getRecordCreaterID());
//
//                            //save kyc details his
//                            masterDetailDAO.save(kycDetailsHis);
//
//                            //update kyc details
//                            kycDetailsTO.setLastChangeTime(date);
//                            kycDetailsTO.setCreationTime(kycDetails.getCreationTime());
//                            kycDetailsTO.setLastChangeUserID(userName);
//                            kycDetailsTO.setRecordCreaterID(kycDetails.getRecordCreaterID());
//                            kycDetailsTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_kyc_details", customerMasterDetail.getCustomerid()) + 1));
//                            CbsKycDetails cbsKycDetailsEntity = ObjectAdaptorLoan.adaptToCbsKycDetailsEntity(kycDetailsTO);
//                            masterDetailDAO.update(cbsKycDetailsEntity);
//                            ut.commit();
//                        }
//                        if (kycDetails == null) {
//                            ut.begin();
//                            kycDetailsTO.setLastChangeTime(date);
//                            kycDetailsTO.setCreationTime(date);
//                            kycDetailsTO.setLastChangeUserID(userName);
//                            kycDetailsTO.setRecordCreaterID(userName);
//                            kycDetailsTO.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_kyc_details", customerMasterDetail.getCustomerid()) + 1));
//                            CbsKycDetails cbsKycDetailsEntity = ObjectAdaptorLoan.adaptToCbsKycDetailsEntity(kycDetailsTO);
//                            masterDetailDAO.save(cbsKycDetailsEntity);
//                            ut.commit();
//                        }
//
//
//
//                        if (!assetsTOs.isEmpty()) {
//                            CbsKycAssetsDAO assetsDAO = new CbsKycAssetsDAO(entityManager);
//                            List<CbsKycAssets> kycAssetsByCustId = assetsDAO.getKycAssetsByCustId(customerMasterDetail.getCustomerid());
//                            if (!kycAssetsByCustId.isEmpty()) {
//                                List<CbsKycAssetsHis> assetsHisList = new ArrayList<CbsKycAssetsHis>();
//                                for (CbsKycAssets assets : kycAssetsByCustId) {
//                                    CbsKycAssetsHisPK assetsHisPK = new CbsKycAssetsHisPK();
//                                    assetsHisPK.setCustomerId(assets.getCbsKycAssetsPK().getCustomerId());
//                                    assetsHisPK.setTxnId(assets.getCbsKycAssetsPK().getTxnId());
//                                    CbsKycAssetsHis assetsHis = new CbsKycAssetsHis();
//                                    assetsHis.setCbsKycAssetsHisPK(assetsHisPK);
//                                    assetsHis.setAssets(assets.getAssets());
//                                    assetsHis.setAssetstype(assets.getAssetstype());
//                                    assetsHis.setAssetsvalue(assets.getAssetsvalue());
//                                    assetsHisList.add(assetsHis);
//                                    ut.begin();
//                                    //delete all details from cbskycAssets one by one
//                                    assetsDAO.delete(assets, assets.getCbsKycAssetsPK());
//                                    ut.commit();
//
//                                }
//                                for (CbsKycAssetsHis assHis : assetsHisList) {
//                                    ut.begin();
//                                    masterDetailDAO.save(assHis);
//                                    ut.commit();
//                                }
//                            }
//
//                            int maxTxnIdByCustId = assetsDAO.getMaxTxnIdByCustId(customerMasterDetail.getCustomerid());
//
//                            for (CbsKycAssetsTO assetsTO : assetsTOs) {
//                                CbsKycAssetsPKTO pKTO = new CbsKycAssetsPKTO();
//                                pKTO.setCustomerId(customerMasterDetail.getCustomerid());
//                                pKTO.setTxnId(++maxTxnIdByCustId);
//                                assetsTO.setCbsKycAssetsPKTO(pKTO);
//                                assetsTO.setLastChangeTime(date);
//                                assetsTO.setCreationTime(date);
//                                assetsTO.setLastChangeUserID(userName);
//                                assetsTO.setRecordCreaterID(userName);
//                                assetsTO.setTsCnt("0");
//                                CbsKycAssets cbsKycAssetsEntity = ObjectAdaptorLoan.adaptToCbsKycAssetsEntity(assetsTO);
//                                ut.begin();
//                                masterDetailDAO.save(cbsKycAssetsEntity);
//                                ut.commit();
//                            }
//                        }
//                        if (!loanTOs.isEmpty()) {
//                            CbsKycLoanDAO loanDAO = new CbsKycLoanDAO(entityManager);
//                            List<CbsKycLoan> kycAssetsByCustId = loanDAO.getKycLoanByCustId(customerMasterDetail.getCustomerid());
//                            if (!kycAssetsByCustId.isEmpty()) {
//                                List<CbsKycLoanHis> loanHisList = new ArrayList<CbsKycLoanHis>();
//                                for (CbsKycLoan loans : kycAssetsByCustId) {
//                                    CbsKycLoanHisPK loanHisPK = new CbsKycLoanHisPK();
//                                    loanHisPK.setCustomerId(loans.getCbsKycLoanPK().getCustomerId());
//                                    loanHisPK.setTxnId(loans.getCbsKycLoanPK().getTxnId());
//                                    CbsKycLoanHis kycLoanHis = new CbsKycLoanHis();
//                                    kycLoanHis.setCbsKycLoanHisPK(loanHisPK);
//                                    kycLoanHis.setCreationTime(loans.getCreationTime());
//                                    kycLoanHis.setLoanamt(loans.getLoanamt());
//                                    kycLoanHis.setLoantype(loans.getLoantype());
//                                    kycLoanHis.setRecordCreaterID(loans.getRecordCreaterID());
//                                    loanHisList.add(kycLoanHis);
//                                    ut.begin();
//                                    //delete all details from cbskycLoan one by one
//                                    loanDAO.delete(loans, loans.getCbsKycLoanPK());
//                                    ut.commit();
//                                }
//                                //saving history of cbsKycLoan
//                                for (CbsKycLoanHis loanHis : loanHisList) {
//                                    ut.begin();
//                                    loanDAO.save(loanHis);
//                                    ut.commit();
//                                }
//                            }
//
//                            int maxTxnIdByCustId = loanDAO.getMaxTxnIdByCustId(customerMasterDetail.getCustomerid());
//                            for (CbsKycLoanTO loanTO : loanTOs) {
//                                CbsKycLoanPKTO pKTO = new CbsKycLoanPKTO();
//                                pKTO.setCustomerId(customerMasterDetail.getCustomerid());
//                                pKTO.setTxnId(++maxTxnIdByCustId);
//                                loanTO.setCbsKycLoanPKTO(pKTO);
//                                loanTO.setLastChangeTime(date);
//                                loanTO.setCreationTime(date);
//                                loanTO.setLastChangeUserID(userName);
//                                loanTO.setRecordCreaterID(userName);
//                                loanTO.setTsCnt("0");
//                                CbsKycLoan cbsKycLoanEntity = ObjectAdaptorLoan.adaptToCbsKycLoanEntity(loanTO);
//                                ut.begin();
//                                masterDetailDAO.save(cbsKycLoanEntity);
//                                ut.commit();
//                            }
//
//                        }
//                    }
                    if (!reletedPersonDetailsTos.isEmpty()) {
                        CBSRelatedPersonsDetailsDAO personsDetailsDAO = new CBSRelatedPersonsDetailsDAO(entityManager);
                        List<CbsRelatedPersonsDetails> custRelatedPersonDetailsByCustId = personsDetailsDAO.getCustRelatedPersonDetailsByCustId(customerMasterDetail.getCustomerid());
                        if (!custRelatedPersonDetailsByCustId.isEmpty()) {
                            List<CbsRelatedPersonsDetailsHis> relatedPersonsDetailsHises = new ArrayList<CbsRelatedPersonsDetailsHis>();
                            for (CbsRelatedPersonsDetails relatedPersonsDetails : custRelatedPersonDetailsByCustId) {
                                CbsRelatedPersonsDetailsHis personsDetailsHis = new CbsRelatedPersonsDetailsHis();
                                personsDetailsHis.setCustomerId(customerMasterDetail.getCustomerid());
                                personsDetailsHis.setPersonSrNo(relatedPersonsDetails.getCbsRelatedPersonsDetailsPK().getPersonSrNo());
                                personsDetailsHis.setPersonCustomerId(relatedPersonsDetails.getPersonCustomerId());
                                personsDetailsHis.setPersonFirstName(relatedPersonsDetails.getPersonFirstName()==null ? "" : relatedPersonsDetails.getPersonFirstName());
                                personsDetailsHis.setPersonMiddleName(relatedPersonsDetails.getPersonMiddleName()== null ? "" : relatedPersonsDetails.getPersonMiddleName());
                                personsDetailsHis.setPersonLastName(relatedPersonsDetails.getPersonLastName()== null ? "" : relatedPersonsDetails.getPersonLastName());
                                personsDetailsHis.setRelationshipCode(relatedPersonsDetails.getRelationshipCode());
                                personsDetailsHis.setPersonPAN(relatedPersonsDetails.getPersonPAN()== null ? "" : relatedPersonsDetails.getPersonPAN());
                                personsDetailsHis.setPersonUID(relatedPersonsDetails.getPersonUID()== null ? "" : relatedPersonsDetails.getPersonUID());
                                personsDetailsHis.setPersonVoterId(relatedPersonsDetails.getPersonVoterId()== null ? "" : relatedPersonsDetails.getPersonVoterId());
                                personsDetailsHis.setPersonNrega(relatedPersonsDetails.getPersonNrega()== null ? "" : relatedPersonsDetails.getPersonNrega());
                                personsDetailsHis.setPersonDL(relatedPersonsDetails.getPersonDL()== null ? "" : relatedPersonsDetails.getPersonDL());
                                personsDetailsHis.setPersonDLExpiry(relatedPersonsDetails.getPersonDLExpiry()== null ? "" : relatedPersonsDetails.getPersonDLExpiry());
                                personsDetailsHis.setPersonPassportNo(relatedPersonsDetails.getPersonPassportNo()== null ? "" : relatedPersonsDetails.getPersonPassportNo());
                                personsDetailsHis.setPersonPassportExpiry(relatedPersonsDetails.getPersonPassportExpiry()== null ? "" : relatedPersonsDetails.getPersonPassportExpiry());
                                personsDetailsHis.setPersonDIN(relatedPersonsDetails.getPersonDIN()== null ? "" : relatedPersonsDetails.getPersonDIN());
                                personsDetailsHis.setPersonpoliticalexposed(relatedPersonsDetails.getPersonpoliticalexposed()== null ? "" : relatedPersonsDetails.getPersonpoliticalexposed());
                                personsDetailsHis.setPersonAdd1(relatedPersonsDetails.getPersonAdd1()== null ? "" : relatedPersonsDetails.getPersonAdd1());
                                personsDetailsHis.setPersonAdd2(relatedPersonsDetails.getPersonAdd2()== null ? "" : relatedPersonsDetails.getPersonAdd2());
                                personsDetailsHis.setPersonCity(relatedPersonsDetails.getPersonCity()== null ? "" : relatedPersonsDetails.getPersonCity());
                                personsDetailsHis.setPersonState(relatedPersonsDetails.getPersonState()== null ? "" : relatedPersonsDetails.getPersonState());
                                personsDetailsHis.setPersonCountry(relatedPersonsDetails.getPersonCountry()== null ? "" : relatedPersonsDetails.getPersonCountry());
                                personsDetailsHis.setPersonPIN(relatedPersonsDetails.getPersonPIN()== null ? "" : relatedPersonsDetails.getPersonPIN());
                                personsDetailsHis.setDelFlag(relatedPersonsDetails.getDelFlag());
                                personsDetailsHis.setDescriptipon(relatedPersonsDetails.getDescriptipon()== null ? "" : relatedPersonsDetails.getDescriptipon());
                                personsDetailsHis.setRecordCreaterID(relatedPersonsDetails.getRecordCreaterID()== null ? "" : relatedPersonsDetails.getRecordCreaterID());
                                personsDetailsHis.setLastChangeTime(date);
                                
                               

                                relatedPersonsDetailsHises.add(personsDetailsHis);
                                ut.begin();
                                //delete record from cbs related person details
                                personsDetailsDAO.delete(relatedPersonsDetails, relatedPersonsDetails.getCbsRelatedPersonsDetailsPK());
                                ut.commit();

                            }
                            for (CbsRelatedPersonsDetailsHis detailsHis : relatedPersonsDetailsHises) {
                                ut.begin();
                                personsDetailsDAO.save(detailsHis);
                                ut.commit();
                            }
                        }
//                        int maxTxnIdByCustId = personsDetailsDAO.getMaxTxnIdByCustId(customerMasterDetail.getCustomerid());

                        for (CBSRelatedPersonsDetailsTO detailsTO : reletedPersonDetailsTos) {
//                            CBSRelatedPersonsDetailsPKTO pKTO = new CBSRelatedPersonsDetailsPKTO();
//                            pKTO.setCustomerId(customerMasterDetail.getCustomerid());
//                            pKTO.setPersonSrNo(++maxTxnIdByCustId);
//                            detailsTO.setcBSRelatedPersonsDetailsPKTO(pKTO);
                            detailsTO.setLastChangeTime(date);
                            detailsTO.setLastChangeUserID(userName);
                            detailsTO.setCreationTime(date);
                            detailsTO.setRecordCreaterID(userName);
                            detailsTO.setTsCnt("0");
                            CbsRelatedPersonsDetails cBSRelatedPersonsDetailsEntity = ObjectAdaptorCustomer.adaptToCBSRelatedPersonsDetailsEntity(detailsTO);
                            ut.begin();
                            masterDetailDAO.save(cBSRelatedPersonsDetailsEntity);
                            ut.commit();
                        }

                    }
                     
                   

                    //if (!idTypeList.isEmpty()) {

                        CBSCustIdentityDetailsDAO custIdentityDetailsDAO = new CBSCustIdentityDetailsDAO(entityManager);
                        List<CbsCustIdentityDetails> custIdentityDetailsByCustId = custIdentityDetailsDAO.getCustIdentityDetailsCustId(customerMasterDetail.getCustomerid());
                        if (!custIdentityDetailsByCustId.isEmpty()) {
                            List<CbsCustIdentityDetailsHis> custIdentityDetailsHisList = new ArrayList<CbsCustIdentityDetailsHis>();

                            for (CbsCustIdentityDetails custIdentityDetails : custIdentityDetailsByCustId) {
                                CbsCustIdentityDetailsHis custIdentityDetailsHis = new CbsCustIdentityDetailsHis();

                                custIdentityDetailsHis.setCustomerId(custIdentityDetails.getCbsCustIdentityDetailsPK().getCustomerId());
                                custIdentityDetailsHis.setIdentificationType(custIdentityDetails.getCbsCustIdentityDetailsPK().getIdentificationType());

                                custIdentityDetailsHis.setIdentityNo(custIdentityDetails.getIdentityNo());
                                custIdentityDetailsHis.setIdExpiryDate(custIdentityDetails.getIdExpiryDate());
                                custIdentityDetailsHis.setOtherIdentificationType(custIdentityDetails.getOtherIdentificationType());
                                custIdentityDetailsHis.setTinIssuingCountry(custIdentityDetails.getTinIssuingCountry());
                                custIdentityDetailsHis.setEnterBy(custIdentityDetails.getEnterBy());
                                custIdentityDetailsHis.setEnterDate(custIdentityDetails.getEnterDate());
                                custIdentityDetailsHis.setEnterTime(date);
                                custIdentityDetailsHisList.add(custIdentityDetailsHis);
                                ut.begin();
                                //delete record from cbs related person details
                                custIdentityDetailsDAO.delete(custIdentityDetails, custIdentityDetails.getCbsCustIdentityDetailsPK());
                                ut.commit();
                            }
                            for (CbsCustIdentityDetailsHis custIdentityDetailsHis : custIdentityDetailsHisList) {
                                ut.begin();
                                custIdentityDetailsDAO.save(custIdentityDetailsHis);
                                ut.commit();
                            }
//                            
                        }
//                       
                        for (CBSCustIdentityDetailsTo IdTypeTO : idTypeList) {
                            IdTypeTO.setCustomerId(customerMasterDetail.getCustomerid());
                            IdTypeTO.setEnterBy(userName);
                            IdTypeTO.setIdentityExpiryDate(IdTypeTO.getIdentityExpiryDate().equalsIgnoreCase("") ? "" : yyyyMMdd.format(ddMMyyyy.parse(IdTypeTO.getIdentityExpiryDate())));
                            CbsCustIdentityDetails custIdentityDetailsEntity = ObjectAdaptorCustomer.adaptToCbsCustIdentityDetailsEntity(IdTypeTO);
                            custIdentityDetailsEntity.setEnterDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date)));
                            ut.begin();
                            masterDetailDAO.save(custIdentityDetailsEntity);
                            ut.commit();
                        }

                    //}

                    if (!addressDetailsList.isEmpty()) {

                        CbsCustAdditionalAddressDetailsDAO custAddressDetailsDAO = new CbsCustAdditionalAddressDetailsDAO(entityManager);
                        List<CbsCustAdditionalAddressDetails> custAddressDetailsByCustId = custAddressDetailsDAO.getCustAddressDetailsByCustId(customerMasterDetail.getCustomerid());
                        if (!custAddressDetailsByCustId.isEmpty()) {
                            List<CbsCustAdditionalAddressDetailsHis> custAddressDetailsHisList = new ArrayList<CbsCustAdditionalAddressDetailsHis>();

                            for (CbsCustAdditionalAddressDetails custAddDetails : custAddressDetailsByCustId) {
                                CbsCustAdditionalAddressDetailsHis custAddressDetailsHis = ObjectAdaptorCustomer.adaptToCbsCustAdditionalAddressDetailsHisE(custAddDetails);
                                custAddressDetailsHis.setEnterTime(custAddDetails.getEnterDate());
                                custAddressDetailsHisList.add(custAddressDetailsHis);
                                ut.begin();
                                //delete record from cbs related person details
                                custAddressDetailsDAO.delete(custAddDetails, custAddDetails.getCbsCustAdditionalAddressDetailsPK());
                                ut.commit();
                            }
                            for (CbsCustAdditionalAddressDetailsHis custAddressDetailsHis : custAddressDetailsHisList) {
                                ut.begin();
                                custAddressDetailsDAO.save(custAddressDetailsHis);
                                ut.commit();
                            }
                        }


                        for (CbsCustAdditionalAddressDetailsTo pojo : addressDetailsList) {
                            pojo.setCustomerId(String.valueOf(customerMasterDetail.getCustomerid()));
                            pojo.setEnterBy(userName);
                            pojo.setEnterDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date)));
                            pojo.setDateofDeclaration(yyyyMMdd.format(date));
                            pojo.setPlaceofDeclaration(ckycrCommonRemote.getBranchCity(Integer.parseInt(brncode)));
                            CbsCustAdditionalAddressDetails custAddressDetailsE = ObjectAdaptorCustomer.adaptToCbsCustAdditionalAddressDetailsE(pojo);
                            ut.begin();
                            custAddressDetailsDAO.save(custAddressDetailsE);
                            ut.commit();
                        }
                    }

//                    if (!currencyInfoTOs.isEmpty()) {
//                        CBSCustCurrencyInfoDAO custCurrencyInfoDAO = new CBSCustCurrencyInfoDAO(entityManager);
//                        List<CBSCustCurrencyInfo> currencyDetailsByCustomerId = custCurrencyInfoDAO.getCurrencyDetailsByCustomerId(customerMasterDetail.getCustomerid());
//                        List<CBSCustCurrencyInfoHis> currencyInfoHises = new ArrayList<CBSCustCurrencyInfoHis>();
//                        if (!currencyDetailsByCustomerId.isEmpty()) {
//                            for (CBSCustCurrencyInfo pojo : currencyDetailsByCustomerId) {
//                                CBSCustCurrencyInfoHis currencyInfoHis = new CBSCustCurrencyInfoHis();
//                                currencyInfoHis.setCurrencyCodeType(pojo.getCBSCustCurrencyInfoPK().getCurrencyCodeType());
//                                currencyInfoHis.setCustomerId(customerMasterDetail.getCustomerid());
//                                currencyInfoHis.setWithHoldingTaxLevel(pojo.getWithHoldingTaxLevel());
//                                currencyInfoHis.setWithHoldingTax(Double.parseDouble(pojo.getWithHoldingTax() != null && !pojo.getWithHoldingTax().toString().equalsIgnoreCase("") ? pojo.getWithHoldingTax().toString() : "0.0"));
//                                currencyInfoHis.setWithHoldingTaxLimit(Double.parseDouble(pojo.getWithHoldingTaxLimit() != null && !pojo.getWithHoldingTaxLimit().toString().equalsIgnoreCase("") ? pojo.getWithHoldingTaxLimit().toString() : "0.0"));
//                                currencyInfoHis.setPreferentialInterestTillDate(pojo.getPreferentialInterestTillDate());
//                                currencyInfoHis.setTDSOperativeAccountID(pojo.getTDSOperativeAccountID());
//                                currencyInfoHis.setCustinterestRateCredit(Double.parseDouble(pojo.getCustinterestRateCredit() != null && !pojo.getCustinterestRateCredit().toString().equalsIgnoreCase("") ? pojo.getCustinterestRateCredit().toString() : "0.0"));
//                                currencyInfoHis.setCustInterestRateDebit(Double.parseDouble(pojo.getCustInterestRateDebit() != null && !pojo.getCustInterestRateDebit().toString().equalsIgnoreCase("") ? pojo.getCustInterestRateDebit().toString() : "0.0"));
//                                currencyInfoHis.setLastChangeTime(pojo.getLastChangeTime());
//                                currencyInfoHis.setLastChangeUserID(pojo.getLastChangeUserID());
//                                currencyInfoHis.setRecordCreaterID(pojo.getRecordCreaterID());
//                                currencyInfoHises.add(currencyInfoHis);
//                                ut.begin();
//                                //delete the records of currency details
//                                custCurrencyInfoDAO.delete(pojo, pojo.getCBSCustCurrencyInfoPK());
//                                ut.commit();
//
//                            }
//                        }
//                        //saving currency details history
//                        for (CBSCustCurrencyInfoHis detailsHis : currencyInfoHises) {
//                            ut.begin();
//                            custCurrencyInfoDAO.save(detailsHis);
//                            ut.commit();
//                        }
//
//                        //saving new entries to currency details
//                        for (CBSCustCurrencyInfoTO currencyInfoTO : currencyInfoTOs) {
//                            currencyInfoTO.setLastChangeTime(date);
//                            currencyInfoTO.setCreationTime(date);
//                            currencyInfoTO.setLastChangeUserID(userName);
//                            currencyInfoTO.setRecordCreaterID(userName);
//                            currencyInfoTO.setTsCnt("0");
//                            CBSCustCurrencyInfo cBSCustCurrencyInfoEntity = ObjectAdaptorCustomer.adaptToCBSCustCurrencyInfoEntity(currencyInfoTO);
//                            ut.begin();
//                            masterDetailDAO.save(cBSCustCurrencyInfoEntity);
//                            ut.commit();
//                        }
//                    }

                    /*Updation of corresponding customer/account master table*/
                    CustomerIdDAO customerIdDAO = new CustomerIdDAO(entityManager);
                    List<Customerid> resultList = customerIdDAO.getRowByCustId(Long.parseLong(customerMasterDetailTO.getCustomerid()));

                    if (!resultList.isEmpty()) {
                        for (int i = 0; i < resultList.size(); i++) {
                            Customerid obj = resultList.get(i);
                            String accountNo = obj.getCustomeridPK().getAcno();
                            String crAddress = customerMasterDetailTO.getMailAddressLine1() + " " + customerMasterDetailTO.getMailAddressLine2() + " " + customerMasterDetailTO.getMailVillage();
                            String prAddress = customerMasterDetailTO.getPerAddressLine1() + " " + customerMasterDetailTO.getPerAddressLine2() + " " + customerMasterDetailTO.getPerVillage();
                            String phoneNo = customerMasterDetailTO.getMobilenumber() + "," + customerMasterDetailTO.getPerPhoneNumber() + "," + customerMasterDetailTO.getMailPhoneNumber();
                            String panNo = customerMasterDetailTO.getpANGIRNumber();
                            String title = customerMasterDetailTO.getTitle();
                            String custName = customerMasterDetailTO.getCustname().trim() + " " + customerMasterDetailTO.getMiddleName().trim();
                            custName = custName.trim() + " " + customerMasterDetailTO.getLastName().trim();
                            String fatherName = customerMasterDetailTO.getFathername().trim() + " " + customerMasterDetailTO.getFatherMiddleName().trim();
                            fatherName = fatherName.trim() + " " + customerMasterDetailTO.getFatherLastName().trim();
                            String dob = yyyyMMdd.format(customerMasterDetailTO.getDateOfBirth());
//                            String fatherName = customerMasterDetailTO.getFathername();
                            String actype = facadeRemote.getAccountCode(accountNo);
                            String currentBrnCode = facadeRemote.getCurrentBrnCode(accountNo);
                            String acNat = facadeRemote.getAcNatureByCode(actype);

                            //Short occupationCode = Short.parseShort(mISInfoTO.getOccupationCode()); //---------
                            Short occupationCode = mISInfoTO.getOccupationCode().equalsIgnoreCase("") || mISInfoTO.getOccupationCode() == null ? 26 : Short.parseShort(mISInfoTO.getOccupationCode());

                            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                ut.begin();
                                TdCustomermasterDAO TdCustomermasterDAO = new TdCustomermasterDAO(entityManager);
                                TdCustomermaster tdCustomermaster = TdCustomermasterDAO.getTDCustomerMaster(accountNo.substring(4, 10), actype, currentBrnCode);

                                tdCustomermaster.setTitle(title);
                                tdCustomermaster.setCustname(custName.trim());
                                tdCustomermaster.setCraddress(ParseFileUtil.getParticularLengthData(crAddress, 100));
                                tdCustomermaster.setPraddress(ParseFileUtil.getParticularLengthData(prAddress, 100));
                                tdCustomermaster.setPhoneno(phoneNo);
                                tdCustomermaster.setPanno(panNo);
                                tdCustomermaster.setDob(dob);
                                tdCustomermaster.setFathername(fatherName);
                                tdCustomermaster.setOccupation(occupationCode);

                                TdCustomermasterDAO.update(tdCustomermaster);

                                AccountMasterDAO accountMasterDAO = new AccountMasterDAO(entityManager);
                                TDAccountMaster tdAccountMaster = accountMasterDAO.getEntityByTDAcno(accountNo);
                                tdAccountMaster.setCustname(custName.trim());
                                tdAccountMaster.setOrgnCode(occupationCode);
                                accountMasterDAO.update(tdAccountMaster);

                                ut.commit();
                            } else {
                                ut.begin();
                                CustomerMasterDAO customerMasterDAO = new CustomerMasterDAO(entityManager);
                                Customermaster customerMaster = customerMasterDAO.getCustomerMaster(accountNo.substring(4, 10), actype, accountNo.substring(10), currentBrnCode);
                                customerMaster.setTitle(title);
                                customerMaster.setCustname(custName.trim());
                                customerMaster.setCraddress(ParseFileUtil.getParticularLengthData(crAddress, 100));
                                customerMaster.setPraddress(ParseFileUtil.getParticularLengthData(prAddress, 100));
                                customerMaster.setPhoneno(phoneNo);
                                customerMaster.setPanno(panNo);
                                customerMaster.setDob(dob);
                                customerMaster.setFathername(fatherName);
                                customerMaster.setOccupation(occupationCode);

                                customerMasterDAO.update(customerMaster);

                                AccountMasterDAO accountMasterDAO = new AccountMasterDAO(entityManager);
                                AccountMaster accountMaster = accountMasterDAO.getEntityByAcno(accountNo);
                                accountMaster.setCustname(custName.trim());
                                accountMaster.setOrgnCode(occupationCode);
                                accountMasterDAO.update(accountMaster);

                                ut.commit();
                            }
                        }
                    }
                    /**
                     * end
                     */
                    //To save an entry in CbsCustkycDetail at the time of modification
                    if (customerMasterDetailTO != null && minorInfoTO != null) {
                        ut.begin();
                        custKycDetailsTo.setCustomerId(customerMasterDetail.getCustomerid());
                        custKycDetailsTo.setTypeOfDocSubmitted(mISInfoTO.getTypeOfDocSubmit());
                        custKycDetailsTo.setRiskCategory(customerMasterDetailTO.getOperationalRiskRating());
                        custKycDetailsTo.setBrnCode(brncode);
                        custKycDetailsTo.setKycCreatedBy(userName);
                        custKycDetailsTo.setKycCreationDate(date);
                        custKycDetailsTo.setCkycrUpdateFlag("N");
                        CbsCustKycDetails custKycDetails = ObjectAdaptorCustomer.adapatToCbsCustKycDetails(custKycDetailsTo);
                        masterDetailDAO.save(custKycDetails);
                        ut.commit();
//                        //End
//
                    }
                    msg = "Record has been successfully updated.";
                } else {
                    msg = "No customer exists for id " + customerMasterDetailTO.getCustomerid();
                }
            } else if (function.equalsIgnoreCase("U")) {
            } else if (function.equalsIgnoreCase("V")) {
                CbsCustomerMasterDetail customerMasterDetail = (CbsCustomerMasterDetail) masterDetailDAO.findById(new CbsCustomerMasterDetail(), customerMasterDetailTO.getCustomerid());
                if (customerMasterDetail != null) {

                    CbsCustKycDetailsDAO custKycDetailsDAO = new CbsCustKycDetailsDAO(entityManager);
                    int maxTxId = custKycDetailsDAO.getMaxTxnId("cbs_cust_kyc_details", customerMasterDetail.getCustomerid());
                    CbsCustKycDetails custKycDetail = (CbsCustKycDetails) masterDetailDAO.findById(new CbsCustKycDetails(), maxTxId);
                    AdminManagementFacadeRemote beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");

                    customerMasterDetail.setAuth("Y");
                    customerMasterDetail.setLastChangeUserID(userName);
                    customerMasterDetail.setLastChangeTime(date);
                    customerMasterDetail.setCustStatus("V");

                    custKycDetail.setKycVerifiedBy(userName);
                    custKycDetail.setKycVerifiedDate(date);
                    custKycDetail.setKycVerifiedUserName(beanRemote.getUserName(userName));

                    ut.begin();
                    masterDetailDAO.update(customerMasterDetail);
                    custKycDetailsDAO.update(custKycDetail);
                    ut.commit();
                    msg = "Customer " + customerMasterDetail.getCustomerid() + " has been verified successfully.";
                }
            }

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return msg;
    }

    public Securityinfo getSecurityInfoDetailsByUserid(String userId) throws ApplicationException {
        Securityinfo entity = null;
        try {
            SecurityInfoDAO dAO = new SecurityInfoDAO(entityManager);
            entity = dAO.findByUserId(userId);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return entity;
    }

    public List<Object[]> getUnVerifyCustIdsByBrnCode(String brnCode) throws ApplicationException {
        CBSCustomerMasterDetailDAO dAO = new CBSCustomerMasterDetailDAO(entityManager);
        try {
            return (List<Object[]>) (dAO.getUnVerifyCustIdsByBrnCode(brnCode));
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String suspendUnsuspendVerifyCustomer(String custId, String func, String userName) throws ApplicationException {
        String msg = "";
        try {
            UserTransaction ut = context.getUserTransaction();
            CBSCustomerMasterDetailDAO masterDetailDAO = new CBSCustomerMasterDetailDAO(entityManager);
            CBSCustMISInfoDAO mISInfoDAO = new CBSCustMISInfoDAO(entityManager);
            CbsCustomerMasterDetail customerMasterDetail = (CbsCustomerMasterDetail) masterDetailDAO.findById(new CbsCustomerMasterDetail(), custId);
            Date date = new Date();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (func.equalsIgnoreCase("U")) {
                if (customerMasterDetail != null) {
                    if (customerMasterDetail.getSuspensionFlg() == null ? false : customerMasterDetail.getSuspensionFlg().equalsIgnoreCase("N")) {
                        msg = "Customer " + customerMasterDetail.getCustomerid() + " has been already Unsuspended.";
                    } else {
                        ut.begin();
                        CbsCustomerMasterDetailHis hisEntity = new CbsCustomerMasterDetailHis();
                        hisEntity.setCustomerid(customerMasterDetail.getCustomerid());
                        hisEntity.setTitle(customerMasterDetail.getTitle());
                        hisEntity.setCustname(customerMasterDetail.getCustname());
                        hisEntity.setShortname(customerMasterDetail.getShortname());
                        hisEntity.setGender(customerMasterDetail.getGender());
                        hisEntity.setMaritalstatus(customerMasterDetail.getMaritalstatus());
                        hisEntity.setCustStatus(customerMasterDetail.getCustStatus());
                        hisEntity.setFathername(customerMasterDetail.getFathername());
                        hisEntity.setMothername(customerMasterDetail.getMothername());
                        hisEntity.setStaffflag(customerMasterDetail.getStaffflag());
                        hisEntity.setStaffid(customerMasterDetail.getStaffid());
                        hisEntity.setDateOfBirth(ymd.format(customerMasterDetail.getDateOfBirth()));
                        hisEntity.setNriflag(customerMasterDetail.getNriflag());
                        hisEntity.setPreferredLanguage(customerMasterDetail.getPreferredLanguage());
                        hisEntity.setNameInPreferredLanguage(customerMasterDetail.getNameInPreferredLanguage());
                        hisEntity.setChgTurnOver(customerMasterDetail.getChgTurnOver());
                        hisEntity.setPurgeAllowed(customerMasterDetail.getPurgeAllowed());
                        hisEntity.setMinorflag(customerMasterDetail.getMinorflag());
                        hisEntity.setPassportNo(customerMasterDetail.getPassportNo());
                        hisEntity.setIssueDate(customerMasterDetail.getIssueDate() != null ? ymd.format(customerMasterDetail.getIssueDate()) : null);
                        hisEntity.setIssuingAuthority(customerMasterDetail.getIssuingAuthority());
                        hisEntity.setExpirydate(customerMasterDetail.getExpirydate() != null ? ymd.format(customerMasterDetail.getExpirydate()) : null);
                        hisEntity.setPlaceOfIssue(customerMasterDetail.getPlaceOfIssue());
                        hisEntity.setMobilenumber(customerMasterDetail.getMobilenumber());
                        hisEntity.setCommunicationPreference(customerMasterDetail.getCommunicationPreference());
                        //add by rahul
                        hisEntity.setAadhaarMode(customerMasterDetail.getAadhaarMode()== null ? "" : customerMasterDetail.getAadhaarMode());
                        hisEntity.setAadhaarBankIin(customerMasterDetail.getAadhaarBankIin()  == null ? "" : customerMasterDetail.getAadhaarBankIin());
                       // end
                        //for customerMasterDetail
                        hisEntity.setAccountManager(customerMasterDetail.getAccountManager());
                        hisEntity.setAllowSweeps(customerMasterDetail.getAllowSweeps());
                        hisEntity.setTradeFinanceFlag(customerMasterDetail.getTradeFinanceFlag());
                        hisEntity.setSwiftCodeStatus(customerMasterDetail.getSwiftCodeStatus());
                        hisEntity.setSwiftCode(customerMasterDetail.getSwiftCode());
                        hisEntity.setBcbfid(customerMasterDetail.getBcbfid());
                        hisEntity.setCombinedStmtFlag(customerMasterDetail.getCombinedStmtFlag());
                        hisEntity.setStmtFreqType(customerMasterDetail.getStmtFreqType());
                        hisEntity.setStmtFreqWeekNo(customerMasterDetail.getStmtFreqWeekNo());
                        hisEntity.setStmtFreqWeekDay(customerMasterDetail.getStmtFreqWeekDay());
                        hisEntity.setStmtFreqStartDate(customerMasterDetail.getStmtFreqStartDate() != null ? customerMasterDetail.getStmtFreqStartDate().toString() : "");
                        hisEntity.setStmtFreqNP(customerMasterDetail.getStmtFreqNP());
                        hisEntity.setSalary(customerMasterDetail.getSalary() != null && !customerMasterDetail.getSalary().toString().equalsIgnoreCase("") ? Double.parseDouble(customerMasterDetail.getSalary().toString()) : 0.0);
                        hisEntity.setChargeStatus(customerMasterDetail.getChargeStatus());
                        hisEntity.setChargeLevelCode(customerMasterDetail.getChargeLevelCode());
                        hisEntity.setABBChargeCode(customerMasterDetail.getABBChargeCode());
                        hisEntity.setEPSChargeCode(customerMasterDetail.getEPSChargeCode());
                        hisEntity.setPaperRemittance(customerMasterDetail.getPaperRemittance());
                        hisEntity.setDeliveryChannelChargeCode(customerMasterDetail.getDeliveryChannelChargeCode());
                        hisEntity.setAccountLevelCharges(customerMasterDetail.getAccountLevelCharges());
                        hisEntity.setCustLevelCharges(customerMasterDetail.getCustLevelCharges());
                        hisEntity.setTaxSlab(customerMasterDetail.getTaxSlab());
                        hisEntity.setITFileNo(customerMasterDetail.getITFileNo());
                        hisEntity.setTDSCode(customerMasterDetail.getTDSCode());
                        hisEntity.setTDSCustomerId(customerMasterDetail.getTDSCustomerId());
                        hisEntity.setTDSFormReceiveDate(customerMasterDetail.getTDSFormReceiveDate() != null ? ymd.format(customerMasterDetail.getTDSFormReceiveDate()) : null);
                        hisEntity.setTDSExemptionReferenceNo(customerMasterDetail.getTDSExemptionReferenceNo());
                        hisEntity.setTDSExemptionEndDate(customerMasterDetail.getTDSExemptionEndDate() != null ? ymd.format(customerMasterDetail.getTDSExemptionEndDate()) : null);
                        hisEntity.setTDSFloorLimit(customerMasterDetail.getTDSFloorLimit() != null && !customerMasterDetail.getTDSFloorLimit().toString().equalsIgnoreCase("") ? Double.parseDouble(customerMasterDetail.getTDSFloorLimit().toString()) : 0.0);
                        hisEntity.setPANGIRNumber(customerMasterDetail.getPANGIRNumber());
                        hisEntity.setTINNumber(customerMasterDetail.getTINNumber());
                        hisEntity.setSalesTaxNo(customerMasterDetail.getSalesTaxNo());
                        hisEntity.setExciseNo(customerMasterDetail.getExciseNo());
                        hisEntity.setSalesTaxNo(customerMasterDetail.getSalesTaxNo());
                        hisEntity.setVoterIDNo(customerMasterDetail.getVoterIDNo());
                        hisEntity.setDrivingLicenseNo(customerMasterDetail.getDrivingLicenseNo());

                        //for customerMasterDetail Tab
                        hisEntity.setIntroCustomerId(customerMasterDetail.getIntroCustomerId());
                        hisEntity.setCustTitle(customerMasterDetail.getCustTitle());
                        hisEntity.setName(customerMasterDetail.getName());
                        hisEntity.setAddressLine1(customerMasterDetail.getAddressLine1());
                        hisEntity.setAddressLine2(customerMasterDetail.getAddressLine2());
                        hisEntity.setVillage(customerMasterDetail.getVillage());
                        hisEntity.setBlock(customerMasterDetail.getBlock());
                        hisEntity.setCityCode(customerMasterDetail.getCityCode());
                        hisEntity.setStateCode(customerMasterDetail.getStateCode());
                        hisEntity.setPostalCode(customerMasterDetail.getPostalCode());
                        hisEntity.setCountryCode(customerMasterDetail.getCountryCode());

                        hisEntity.setPhoneNumber(customerMasterDetail.getPhoneNumber());
                        hisEntity.setTelexNumber(customerMasterDetail.getTelexNumber());
                        hisEntity.setFaxNumber(customerMasterDetail.getFaxNumber());
                        //     hisEntity.setFinancialYearAndMonth(customerMasterDetail.getCustFinanYear() + " " + customerMasterDetail.getCustFinanMon());
                        hisEntity.setFinancialYearAndMonth(null);
                        hisEntity.setCurrencyCodeType(customerMasterDetail.getCurrencyCodeType());
                        hisEntity.setBusinessAssets(Double.parseDouble(customerMasterDetail.getBusinessAssets() != null && !customerMasterDetail.getBusinessAssets().toString().equalsIgnoreCase("") ? customerMasterDetail.getBusinessAssets().toString() : "0.0"));
                        hisEntity.setPropertyAssets(Double.parseDouble(customerMasterDetail.getPropertyAssets() != null && !customerMasterDetail.getPropertyAssets().toString().equalsIgnoreCase("") ? customerMasterDetail.getPropertyAssets().toString() : "0.0"));
                        hisEntity.setInvestments(Double.parseDouble(customerMasterDetail.getInvestments() != null && !customerMasterDetail.getInvestments().toString().equalsIgnoreCase("") ? customerMasterDetail.getInvestments().toString() : "0.0"));
                        hisEntity.setNetworth(Double.parseDouble(customerMasterDetail.getNetworth() != null && !customerMasterDetail.getNetworth().toString().equalsIgnoreCase("") ? customerMasterDetail.getNetworth().toString() : "0.0"));
                        hisEntity.setDeposits(Double.parseDouble(customerMasterDetail.getDeposits() != null && !customerMasterDetail.getDeposits().toString().equalsIgnoreCase("") ? customerMasterDetail.getDeposits().toString() : "0.0"));
                        hisEntity.setOtherBankCode(customerMasterDetail.getOtherBankCode());
                        hisEntity.setLimitsWithOtherBank(Double.parseDouble(customerMasterDetail.getLimitsWithOtherBank() != null && !customerMasterDetail.getLimitsWithOtherBank().toString().equalsIgnoreCase("") ? customerMasterDetail.getLimitsWithOtherBank().toString() : "0.0"));
                        hisEntity.setFundBasedLimit(Double.parseDouble(customerMasterDetail.getFundBasedLimit() != null && !customerMasterDetail.getFundBasedLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getFundBasedLimit().toString() : "0.0"));
                        hisEntity.setNonFundBasedLimit(Double.parseDouble(customerMasterDetail.getNonFundBasedLimit() != null && !customerMasterDetail.getNonFundBasedLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getNonFundBasedLimit().toString() : "0.0"));
                        hisEntity.setOffLineCustDebitLimit(Double.parseDouble(customerMasterDetail.getOffLineCustDebitLimit() != null && !customerMasterDetail.getOffLineCustDebitLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getOffLineCustDebitLimit().toString() : "0.0"));
                        hisEntity.setSalary(Double.parseDouble(customerMasterDetail.getSalary() != null && !customerMasterDetail.getSalary().toString().equalsIgnoreCase("") ? customerMasterDetail.getSalary().toString() : "0.0"));
                        hisEntity.setCustFinancialDate(customerMasterDetail.getCustFinancialDate() != null ? ymd.format(customerMasterDetail.getCustFinancialDate()) : null);
                        hisEntity.setCreditCard(customerMasterDetail.getCreditCard());
                        hisEntity.setCardNumber(customerMasterDetail.getCardNumber());
                        hisEntity.setCardIssuer(customerMasterDetail.getCardIssuer());
                        hisEntity.setBankName(customerMasterDetail.getBankName());
                        hisEntity.setAcctId(customerMasterDetail.getAcctId());

                        //for AddressInfo tab
                        hisEntity.setPerAddressLine1(customerMasterDetail.getPerAddressLine1());
//                        hisEntity.setPerAddressLine2(customerMasterDetail.getPerAddressLine2());
                        hisEntity.setPerAddressLine2(customerMasterDetail.getPeraddressline2());
                        hisEntity.setPerVillage(customerMasterDetail.getPerVillage());
                        hisEntity.setPerBlock(customerMasterDetail.getPerBlock());
                        hisEntity.setPerCityCode(customerMasterDetail.getPerCityCode());
                        hisEntity.setPerStateCode(customerMasterDetail.getPerStateCode());
                        hisEntity.setPerCountryCode(customerMasterDetail.getPerCountryCode());
                        hisEntity.setPerPostalCode(customerMasterDetail.getPerPostalCode());
                        hisEntity.setPerPhoneNumber(customerMasterDetail.getPerPhoneNumber());
                        hisEntity.setPerFaxNumber(customerMasterDetail.getPerFaxNumber());
                        hisEntity.setPerTelexNumber(customerMasterDetail.getPerTelexNumber());

                        hisEntity.setMailAddressLine1(customerMasterDetail.getMailAddressLine1());
                        hisEntity.setMailAddressLine2(customerMasterDetail.getMailAddressLine2());
                        hisEntity.setMailVillage(customerMasterDetail.getMailVillage());
                        hisEntity.setMailBlock(customerMasterDetail.getMailBlock());
                        hisEntity.setMailCityCode(customerMasterDetail.getMailCityCode());
                        hisEntity.setMailStateCode(customerMasterDetail.getMailStateCode());
                        hisEntity.setMailCountryCode(customerMasterDetail.getMailCountryCode());
                        hisEntity.setMailPostalCode(customerMasterDetail.getMailPostalCode());
                        hisEntity.setMailPhoneNumber(customerMasterDetail.getMailPhoneNumber());
                        hisEntity.setMailFaxNumber(customerMasterDetail.getMailFaxNumber());
                        hisEntity.setMailTelexNumber(customerMasterDetail.getMailTelexNumber());

                        hisEntity.setEmployerid(customerMasterDetail.getEmployerid());
                        hisEntity.setEmployeeNo(customerMasterDetail.getEmployeeNo());
                        hisEntity.setEmpAddressLine1(customerMasterDetail.getEmpAddressLine1());
                        hisEntity.setEmpAddressLine2(customerMasterDetail.getEmpAddressLine2());
                        hisEntity.setEmpVillage(customerMasterDetail.getEmpVillage());
                        hisEntity.setEmpBlock(customerMasterDetail.getEmpBlock());
                        hisEntity.setEmpCityCode(customerMasterDetail.getEmpCityCode());
                        hisEntity.setEmpStateCode(customerMasterDetail.getEmpStateCode());
                        hisEntity.setEmpPostalCode(customerMasterDetail.getEmpPostalCode());
                        hisEntity.setEmpCountryCode(customerMasterDetail.getEmpCountryCode());
                        hisEntity.setEmpPhoneNumber(customerMasterDetail.getEmpPhoneNumber());
                        hisEntity.setEmpTelexNumber(customerMasterDetail.getEmpTelexNumber());
                        hisEntity.setEmpFaxNumber(customerMasterDetail.getEmpFaxNumber());
                        hisEntity.setEmailID(customerMasterDetail.getEmailID());
                        hisEntity.setRecordCreaterID(customerMasterDetail.getRecordCreaterID());
                        hisEntity.setLastChangeTime(customerMasterDetail.getLastChangeTime());
                        hisEntity.setLastChangeUserID(customerMasterDetail.getLastChangeUserID());
                        hisEntity.setPrimaryBrCode(customerMasterDetail.getPrimaryBrCode());

                        hisEntity.setOperationalRiskRating(customerMasterDetail.getOperationalRiskRating());
                        hisEntity.setRatingAsOn(customerMasterDetail.getRatingAsOn() != null ? ymd.format(customerMasterDetail.getRatingAsOn()) : null);
                        hisEntity.setCreditRiskRatingInternal(customerMasterDetail.getCreditRiskRatingInternal());
                        hisEntity.setCreditRatingAsOn(customerMasterDetail.getCreditRatingAsOn() != null ? ymd.format(customerMasterDetail.getCreditRatingAsOn()) : null);
                        hisEntity.setExternalRatingShortTerm(customerMasterDetail.getExternalRatingShortTerm());
                        hisEntity.setExternShortRatingAsOn(customerMasterDetail.getExternShortRatingAsOn() != null ? ymd.format(customerMasterDetail.getExternShortRatingAsOn()) : null);
                        hisEntity.setExternalRatingLongTerm(customerMasterDetail.getExternalRatingLongTerm());
                        hisEntity.setExternLongRatingAsOn(customerMasterDetail.getExternLongRatingAsOn() != null ? ymd.format(customerMasterDetail.getExternLongRatingAsOn()) : null);
                        hisEntity.setCustAcquistionDate(customerMasterDetail.getCustAcquistionDate() != null ? ymd.format(customerMasterDetail.getCustAcquistionDate()) : null);
                        hisEntity.setThresoldTransactionLimit(Double.parseDouble(customerMasterDetail.getThresoldTransactionLimit() != null && !customerMasterDetail.getThresoldTransactionLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getThresoldTransactionLimit().toString() : "0.0"));
                        hisEntity.setThresoldLimitUpdationDate(customerMasterDetail.getThresoldLimitUpdationDate() != null ? ymd.format(customerMasterDetail.getThresoldLimitUpdationDate()) : null);
                        hisEntity.setCustUpdationDate(customerMasterDetail.getCustUpdationDate() != null ? ymd.format(customerMasterDetail.getCustUpdationDate()) : null);
                        hisEntity.setCustStatus(customerMasterDetail.getCustStatus());
                        hisEntity.setAuth(customerMasterDetail.getAuth());

                        //New Addition
                        hisEntity.setPerAddType(customerMasterDetail.getPerAddType());

                        hisEntity.setPerMailAddSameFlagIndicate(customerMasterDetail.getPerMailAddSameFlagIndicate());
                        hisEntity.setMailAddType(customerMasterDetail.getMailAddType());
                        hisEntity.setMailPoa(customerMasterDetail.getMailPoa());

                        hisEntity.setJuriAddBasedOnFlag(customerMasterDetail.getJuriAddBasedOnFlag());
                        hisEntity.setJuriAddType(customerMasterDetail.getJuriAddType());
                        hisEntity.setJuriPoa(customerMasterDetail.getJuriPoa());

                        hisEntity.setAcHolderTypeFlag(customerMasterDetail.getAcHolderTypeFlag());
                        hisEntity.setAcHolderType(customerMasterDetail.getAcHolderType());
                        hisEntity.setAcType(customerMasterDetail.getAcType());

//                        hisEntity.setcKYCNo(customerMasterDetail.getcKYCNo());
                        hisEntity.setCKYCNo(customerMasterDetail.getCKYCNo());

                        hisEntity.setCustFullName(customerMasterDetail.getCustFullName());

                        hisEntity.setFatherMiddleName(customerMasterDetail.getFatherMiddleName());
                        hisEntity.setFatherLastName(customerMasterDetail.getFatherLastName());

                        hisEntity.setSpouseMiddleName(customerMasterDetail.getSpouseMiddleName());
                        hisEntity.setSpouseLastName(customerMasterDetail.getSpouseLastName());

                        hisEntity.setMotherMiddleName(customerMasterDetail.getMotherMiddleName());
                        hisEntity.setMotherLastName(customerMasterDetail.getMotherLastName());

//                        hisEntity.setcKYCNo(customerMasterDetail.getcKYCNo());
                        hisEntity.setCKYCNo(customerMasterDetail.getCKYCNo());
                        hisEntity.setTinIssuingCountry(customerMasterDetail.getTinIssuingCountry());

                        hisEntity.setCustEntityType(customerMasterDetail.getCustEntityType());
                        hisEntity.setIdentityNo(customerMasterDetail.getIdentityNo());
                        hisEntity.setIdExpiryDate(customerMasterDetail.getIdExpiryDate());

                        hisEntity.setPerDistrict(customerMasterDetail.getPerDistrict());
                        hisEntity.setMailDistrict(customerMasterDetail.getMailDistrict());
                        hisEntity.setEmpDistrict(customerMasterDetail.getEmpDistrict());

                        hisEntity.setPerOtherPOA(customerMasterDetail.getPerOtherPOA());
                        hisEntity.setMailOtherPOA(customerMasterDetail.getMailOtherPOA());
                        hisEntity.setCustEntityType(customerMasterDetail.getCustEntityType());
                        hisEntity.setJuriOtherPOA(customerMasterDetail.getJuriOtherPOA());
                        hisEntity.setFatherSpouseFlag(customerMasterDetail.getFatherSpouseFlag());
                        hisEntity.setLegalDocument(customerMasterDetail.getLegalDocument());
                        hisEntity.setPoa(customerMasterDetail.getPoa());

                        hisEntity.setCustImage(customerMasterDetail.getCustImage());
                        //end

                        //save customer history      
                        masterDetailDAO.save(hisEntity);

                        //update customer
//                        customerMasterDetail.setCustStatus("U");
                        customerMasterDetail.setAuth("N");
                        customerMasterDetail.setSuspensionFlg("N");
                        customerMasterDetail.setLastChangeTime(date);
                        customerMasterDetail.setLastChangeUserID(userName);
                        customerMasterDetail.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_customer_master_detail", customerMasterDetail.getCustomerid()) + 1));
                        masterDetailDAO.update(customerMasterDetail);

                        //update misinfo
                        CbsCustMisinfo cBSCustMISInfo = (CbsCustMisinfo) mISInfoDAO.findById(new CbsCustMisinfo(), custId);
                        if (cBSCustMISInfo != null) {
                            cBSCustMISInfo.setStatusCode("U");
                            mISInfoDAO.update(cBSCustMISInfo);
                        }
                        ut.commit();
                        msg = "Customer " + customerMasterDetail.getCustomerid() + " has been Unsuspended successfully.";
                    }
                }
            } else if (func.equalsIgnoreCase("S")) {
                //Addition on 20/10/2015
                boolean isActive = customerMgmtRemote.isActiveAcOnCustomer(custId);
                if (isActive == true) {
                    return msg = "There is some active a/c on this customer so you can not suspend it.";
                }
                //End here

                if (customerMasterDetail != null) {
                    if (customerMasterDetail.getSuspensionFlg() == null ? false : customerMasterDetail.getSuspensionFlg().equalsIgnoreCase("Y")) {
                        msg = "Customer " + customerMasterDetail.getCustomerid() + " has been already Suspended.";
                    } else {
                        ut.begin();
                        CbsCustomerMasterDetailHis hisEntity = new CbsCustomerMasterDetailHis();
                        hisEntity.setCustomerid(customerMasterDetail.getCustomerid());
                        hisEntity.setTitle(customerMasterDetail.getTitle());
                        hisEntity.setCustname(customerMasterDetail.getCustname());
                        hisEntity.setShortname(customerMasterDetail.getShortname());
                        hisEntity.setGender(customerMasterDetail.getGender());
                        hisEntity.setMaritalstatus(customerMasterDetail.getMaritalstatus());
                        hisEntity.setCustStatus(customerMasterDetail.getCustStatus());
                        hisEntity.setFathername(customerMasterDetail.getFathername());
                        hisEntity.setMothername(customerMasterDetail.getMothername());
                        hisEntity.setStaffflag(customerMasterDetail.getStaffflag());
                        hisEntity.setStaffid(customerMasterDetail.getStaffid());
                        hisEntity.setDateOfBirth(ymd.format(customerMasterDetail.getDateOfBirth()));
                        hisEntity.setNriflag(customerMasterDetail.getNriflag());
                        hisEntity.setPreferredLanguage(customerMasterDetail.getPreferredLanguage());
                        hisEntity.setNameInPreferredLanguage(customerMasterDetail.getNameInPreferredLanguage());
                        hisEntity.setChgTurnOver(customerMasterDetail.getChgTurnOver());
                        hisEntity.setPurgeAllowed(customerMasterDetail.getPurgeAllowed());
                        hisEntity.setMinorflag(customerMasterDetail.getMinorflag());
                        hisEntity.setPassportNo(customerMasterDetail.getPassportNo());
                        hisEntity.setIssueDate(customerMasterDetail.getIssueDate() != null ? ymd.format(customerMasterDetail.getIssueDate()) : null);
                        hisEntity.setIssuingAuthority(customerMasterDetail.getIssuingAuthority());
                        hisEntity.setExpirydate(customerMasterDetail.getExpirydate() != null ? ymd.format(customerMasterDetail.getExpirydate()) : null);
                        hisEntity.setPlaceOfIssue(customerMasterDetail.getPlaceOfIssue());
                        hisEntity.setMobilenumber(customerMasterDetail.getMobilenumber());
                        hisEntity.setCommunicationPreference(customerMasterDetail.getCommunicationPreference());
                        //add by rahul
                        hisEntity.setAadhaarMode(customerMasterDetail.getAadhaarMode()== null ? "" : customerMasterDetail.getAadhaarMode());
                        hisEntity.setAadhaarBankIin(customerMasterDetail.getAadhaarBankIin()  == null ? "" : customerMasterDetail.getAadhaarBankIin());
                        //
                        //for customerMasterDetail
                        hisEntity.setAccountManager(customerMasterDetail.getAccountManager());
                        hisEntity.setAllowSweeps(customerMasterDetail.getAllowSweeps());
                        hisEntity.setTradeFinanceFlag(customerMasterDetail.getTradeFinanceFlag());
                        hisEntity.setSwiftCodeStatus(customerMasterDetail.getSwiftCodeStatus());
                        hisEntity.setSwiftCode(customerMasterDetail.getSwiftCode());
                        hisEntity.setBcbfid(customerMasterDetail.getBcbfid());
                        hisEntity.setCombinedStmtFlag(customerMasterDetail.getCombinedStmtFlag());
                        hisEntity.setStmtFreqType(customerMasterDetail.getStmtFreqType());
                        hisEntity.setStmtFreqWeekNo(customerMasterDetail.getStmtFreqWeekNo());
                        hisEntity.setStmtFreqWeekDay(customerMasterDetail.getStmtFreqWeekDay());
                        hisEntity.setStmtFreqStartDate(customerMasterDetail.getStmtFreqStartDate() != null ? customerMasterDetail.getStmtFreqStartDate().toString() : "");
                        hisEntity.setStmtFreqNP(customerMasterDetail.getStmtFreqNP());
                        hisEntity.setSalary(customerMasterDetail.getSalary() != null && !customerMasterDetail.getSalary().toString().equalsIgnoreCase("") ? Double.parseDouble(customerMasterDetail.getSalary().toString()) : 0.0);
                        hisEntity.setChargeStatus(customerMasterDetail.getChargeStatus());
                        hisEntity.setChargeLevelCode(customerMasterDetail.getChargeLevelCode());
                        hisEntity.setABBChargeCode(customerMasterDetail.getABBChargeCode());
                        hisEntity.setEPSChargeCode(customerMasterDetail.getEPSChargeCode());
                        hisEntity.setPaperRemittance(customerMasterDetail.getPaperRemittance());
                        hisEntity.setDeliveryChannelChargeCode(customerMasterDetail.getDeliveryChannelChargeCode());
                        hisEntity.setAccountLevelCharges(customerMasterDetail.getAccountLevelCharges());
                        hisEntity.setCustLevelCharges(customerMasterDetail.getCustLevelCharges());
                        hisEntity.setTaxSlab(customerMasterDetail.getTaxSlab());
                        hisEntity.setITFileNo(customerMasterDetail.getITFileNo());
                        hisEntity.setTDSCode(customerMasterDetail.getTDSCode());
                        hisEntity.setTDSCustomerId(customerMasterDetail.getTDSCustomerId());
                        hisEntity.setTDSFormReceiveDate(customerMasterDetail.getTDSFormReceiveDate() != null ? ymd.format(customerMasterDetail.getTDSFormReceiveDate()) : null);
                        hisEntity.setTDSExemptionReferenceNo(customerMasterDetail.getTDSExemptionReferenceNo());
                        hisEntity.setTDSExemptionEndDate(customerMasterDetail.getTDSExemptionEndDate() != null ? ymd.format(customerMasterDetail.getTDSExemptionEndDate()) : null);
                        hisEntity.setTDSFloorLimit(customerMasterDetail.getTDSFloorLimit() != null && !customerMasterDetail.getTDSFloorLimit().toString().equalsIgnoreCase("") ? Double.parseDouble(customerMasterDetail.getTDSFloorLimit().toString()) : 0.0);
                        hisEntity.setPANGIRNumber(customerMasterDetail.getPANGIRNumber());
                        hisEntity.setTINNumber(customerMasterDetail.getTINNumber());
                        hisEntity.setSalesTaxNo(customerMasterDetail.getSalesTaxNo());
                        hisEntity.setExciseNo(customerMasterDetail.getExciseNo());
                        hisEntity.setSalesTaxNo(customerMasterDetail.getSalesTaxNo());
                        hisEntity.setVoterIDNo(customerMasterDetail.getVoterIDNo());
                        hisEntity.setDrivingLicenseNo(customerMasterDetail.getDrivingLicenseNo());

                        //for customerMasterDetail Tab
                        hisEntity.setIntroCustomerId(customerMasterDetail.getIntroCustomerId());
                        hisEntity.setCustTitle(customerMasterDetail.getCustTitle());
                        hisEntity.setName(customerMasterDetail.getName());
                        hisEntity.setAddressLine1(customerMasterDetail.getAddressLine1());
                        hisEntity.setAddressLine2(customerMasterDetail.getAddressLine2());
                        hisEntity.setVillage(customerMasterDetail.getVillage());
                        hisEntity.setBlock(customerMasterDetail.getBlock());
                        hisEntity.setCityCode(customerMasterDetail.getCityCode());
                        hisEntity.setStateCode(customerMasterDetail.getStateCode());
                        hisEntity.setPostalCode(customerMasterDetail.getPostalCode());
                        hisEntity.setCountryCode(customerMasterDetail.getCountryCode());

                        hisEntity.setPhoneNumber(customerMasterDetail.getPhoneNumber());
                        hisEntity.setTelexNumber(customerMasterDetail.getTelexNumber());
                        hisEntity.setFaxNumber(customerMasterDetail.getFaxNumber());
                        //     hisEntity.setFinancialYearAndMonth(customerMasterDetail.getCustFinanYear() + " " + customerMasterDetail.getCustFinanMon());
                        hisEntity.setFinancialYearAndMonth(null);
                        hisEntity.setCurrencyCodeType(customerMasterDetail.getCurrencyCodeType());
                        hisEntity.setBusinessAssets(Double.parseDouble(customerMasterDetail.getBusinessAssets() != null && !customerMasterDetail.getBusinessAssets().toString().equalsIgnoreCase("") ? customerMasterDetail.getBusinessAssets().toString() : "0.0"));
                        hisEntity.setPropertyAssets(Double.parseDouble(customerMasterDetail.getPropertyAssets() != null && !customerMasterDetail.getPropertyAssets().toString().equalsIgnoreCase("") ? customerMasterDetail.getPropertyAssets().toString() : "0.0"));
                        hisEntity.setInvestments(Double.parseDouble(customerMasterDetail.getInvestments() != null && !customerMasterDetail.getInvestments().toString().equalsIgnoreCase("") ? customerMasterDetail.getInvestments().toString() : "0.0"));
                        hisEntity.setNetworth(Double.parseDouble(customerMasterDetail.getNetworth() != null && !customerMasterDetail.getNetworth().toString().equalsIgnoreCase("") ? customerMasterDetail.getNetworth().toString() : "0.0"));
                        hisEntity.setDeposits(Double.parseDouble(customerMasterDetail.getDeposits() != null && !customerMasterDetail.getDeposits().toString().equalsIgnoreCase("") ? customerMasterDetail.getDeposits().toString() : "0.0"));
                        hisEntity.setOtherBankCode(customerMasterDetail.getOtherBankCode());
                        hisEntity.setLimitsWithOtherBank(Double.parseDouble(customerMasterDetail.getLimitsWithOtherBank() != null && !customerMasterDetail.getLimitsWithOtherBank().toString().equalsIgnoreCase("") ? customerMasterDetail.getLimitsWithOtherBank().toString() : "0.0"));
                        hisEntity.setFundBasedLimit(Double.parseDouble(customerMasterDetail.getFundBasedLimit() != null && !customerMasterDetail.getFundBasedLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getFundBasedLimit().toString() : "0.0"));
                        hisEntity.setNonFundBasedLimit(Double.parseDouble(customerMasterDetail.getNonFundBasedLimit() != null && !customerMasterDetail.getNonFundBasedLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getNonFundBasedLimit().toString() : "0.0"));
                        hisEntity.setOffLineCustDebitLimit(Double.parseDouble(customerMasterDetail.getOffLineCustDebitLimit() != null && !customerMasterDetail.getOffLineCustDebitLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getOffLineCustDebitLimit().toString() : "0.0"));
                        hisEntity.setSalary(Double.parseDouble(customerMasterDetail.getSalary() != null && !customerMasterDetail.getSalary().toString().equalsIgnoreCase("") ? customerMasterDetail.getSalary().toString() : "0.0"));
                        hisEntity.setCustFinancialDate(customerMasterDetail.getCustFinancialDate() != null ? ymd.format(customerMasterDetail.getCustFinancialDate()) : null);
                        hisEntity.setCreditCard(customerMasterDetail.getCreditCard());
                        hisEntity.setCardNumber(customerMasterDetail.getCardNumber());
                        hisEntity.setCardIssuer(customerMasterDetail.getCardIssuer());
                        hisEntity.setBankName(customerMasterDetail.getBankName());
                        hisEntity.setAcctId(customerMasterDetail.getAcctId());

                        //for AddressInfo tab
                        hisEntity.setPerAddressLine1(customerMasterDetail.getPerAddressLine1());
//                        hisEntity.setPerAddressLine2(customerMasterDetail.getPerAddressLine2());
                        hisEntity.setPerAddressLine2(customerMasterDetail.getAddressLine2());
                        hisEntity.setPerVillage(customerMasterDetail.getPerVillage());
                        hisEntity.setPerBlock(customerMasterDetail.getPerBlock());
                        hisEntity.setPerCityCode(customerMasterDetail.getPerCityCode());
                        hisEntity.setPerStateCode(customerMasterDetail.getPerStateCode());
                        hisEntity.setPerCountryCode(customerMasterDetail.getPerCountryCode());
                        hisEntity.setPerPostalCode(customerMasterDetail.getPerPostalCode());
                        hisEntity.setPerPhoneNumber(customerMasterDetail.getPerPhoneNumber());
                        hisEntity.setPerFaxNumber(customerMasterDetail.getPerFaxNumber());
                        hisEntity.setPerTelexNumber(customerMasterDetail.getPerTelexNumber());

                        hisEntity.setMailAddressLine1(customerMasterDetail.getMailAddressLine1());
                        hisEntity.setMailAddressLine2(customerMasterDetail.getMailAddressLine2());
                        hisEntity.setMailVillage(customerMasterDetail.getMailVillage());
                        hisEntity.setMailBlock(customerMasterDetail.getMailBlock());
                        hisEntity.setMailCityCode(customerMasterDetail.getMailCityCode());
                        hisEntity.setMailStateCode(customerMasterDetail.getMailStateCode());
                        hisEntity.setMailCountryCode(customerMasterDetail.getMailCountryCode());
                        hisEntity.setMailPostalCode(customerMasterDetail.getMailPostalCode());
                        hisEntity.setMailPhoneNumber(customerMasterDetail.getMailPhoneNumber());
                        hisEntity.setMailFaxNumber(customerMasterDetail.getMailFaxNumber());
                        hisEntity.setMailTelexNumber(customerMasterDetail.getMailTelexNumber());

                        hisEntity.setEmployerid(customerMasterDetail.getEmployerid());
                        hisEntity.setEmployeeNo(customerMasterDetail.getEmployeeNo());
                        hisEntity.setEmpAddressLine1(customerMasterDetail.getEmpAddressLine1());
                        hisEntity.setEmpAddressLine2(customerMasterDetail.getEmpAddressLine2());
                        hisEntity.setEmpVillage(customerMasterDetail.getEmpVillage());
                        hisEntity.setEmpBlock(customerMasterDetail.getEmpBlock());
                        hisEntity.setEmpCityCode(customerMasterDetail.getEmpCityCode());
                        hisEntity.setEmpStateCode(customerMasterDetail.getEmpStateCode());
                        hisEntity.setEmpPostalCode(customerMasterDetail.getEmpPostalCode());
                        hisEntity.setEmpCountryCode(customerMasterDetail.getEmpCountryCode());
                        hisEntity.setEmpPhoneNumber(customerMasterDetail.getEmpPhoneNumber());
                        hisEntity.setEmpTelexNumber(customerMasterDetail.getEmpTelexNumber());
                        hisEntity.setEmpFaxNumber(customerMasterDetail.getEmpFaxNumber());
                        hisEntity.setEmailID(customerMasterDetail.getEmailID());
                        hisEntity.setRecordCreaterID(customerMasterDetail.getRecordCreaterID());
                        hisEntity.setLastChangeTime(customerMasterDetail.getLastChangeTime());
                        hisEntity.setLastChangeUserID(customerMasterDetail.getLastChangeUserID());
                        hisEntity.setPrimaryBrCode(customerMasterDetail.getPrimaryBrCode());

                        hisEntity.setOperationalRiskRating(customerMasterDetail.getOperationalRiskRating());
                        hisEntity.setRatingAsOn(customerMasterDetail.getRatingAsOn() != null ? ymd.format(customerMasterDetail.getRatingAsOn()) : null);
                        hisEntity.setCreditRiskRatingInternal(customerMasterDetail.getCreditRiskRatingInternal());
                        hisEntity.setCreditRatingAsOn(customerMasterDetail.getCreditRatingAsOn() != null ? ymd.format(customerMasterDetail.getCreditRatingAsOn()) : null);
                        hisEntity.setExternalRatingShortTerm(customerMasterDetail.getExternalRatingShortTerm());
                        hisEntity.setExternShortRatingAsOn(customerMasterDetail.getExternShortRatingAsOn() != null ? ymd.format(customerMasterDetail.getExternShortRatingAsOn()) : null);
                        hisEntity.setExternalRatingLongTerm(customerMasterDetail.getExternalRatingLongTerm());
                        hisEntity.setExternLongRatingAsOn(customerMasterDetail.getExternLongRatingAsOn() != null ? ymd.format(customerMasterDetail.getExternLongRatingAsOn()) : null);
                        hisEntity.setCustAcquistionDate(customerMasterDetail.getCustAcquistionDate() != null ? ymd.format(customerMasterDetail.getCustAcquistionDate()) : null);
                        hisEntity.setThresoldTransactionLimit(Double.parseDouble(customerMasterDetail.getThresoldTransactionLimit() != null && !customerMasterDetail.getThresoldTransactionLimit().toString().equalsIgnoreCase("") ? customerMasterDetail.getThresoldTransactionLimit().toString() : "0.0"));
                        hisEntity.setThresoldLimitUpdationDate(customerMasterDetail.getThresoldLimitUpdationDate() != null ? ymd.format(customerMasterDetail.getThresoldLimitUpdationDate()) : null);
                        hisEntity.setCustUpdationDate(customerMasterDetail.getCustUpdationDate() != null ? ymd.format(customerMasterDetail.getCustUpdationDate()) : null);
                        hisEntity.setCustStatus(customerMasterDetail.getCustStatus());
                        hisEntity.setAuth(customerMasterDetail.getAuth());

                        //New Addition
                        hisEntity.setPerAddType(customerMasterDetail.getPerAddType());

                        hisEntity.setPerMailAddSameFlagIndicate(customerMasterDetail.getPerMailAddSameFlagIndicate());
                        hisEntity.setMailAddType(customerMasterDetail.getMailAddType());
                        hisEntity.setMailPoa(customerMasterDetail.getMailPoa());

                        hisEntity.setJuriAddBasedOnFlag(customerMasterDetail.getJuriAddBasedOnFlag());
                        hisEntity.setJuriAddType(customerMasterDetail.getJuriAddType());
                        hisEntity.setJuriPoa(customerMasterDetail.getJuriPoa());

                        hisEntity.setAcHolderTypeFlag(customerMasterDetail.getAcHolderTypeFlag());
                        hisEntity.setAcHolderType(customerMasterDetail.getAcHolderType());
                        hisEntity.setAcType(customerMasterDetail.getAcType());

//                        hisEntity.setcKYCNo(customerMasterDetail.getcKYCNo());
                        hisEntity.setCKYCNo(customerMasterDetail.getCKYCNo());

                        hisEntity.setFatherMiddleName(customerMasterDetail.getFatherMiddleName());
                        hisEntity.setFatherLastName(customerMasterDetail.getFatherLastName());

                        hisEntity.setSpouseMiddleName(customerMasterDetail.getSpouseMiddleName());
                        hisEntity.setSpouseLastName(customerMasterDetail.getSpouseLastName());

                        hisEntity.setMotherMiddleName(customerMasterDetail.getMotherMiddleName());
                        hisEntity.setMotherLastName(customerMasterDetail.getMotherLastName());

//                        hisEntity.setcKYCNo(customerMasterDetail.getcKYCNo());
                        hisEntity.setCKYCNo(customerMasterDetail.getCKYCNo());
                        hisEntity.setTinIssuingCountry(customerMasterDetail.getTinIssuingCountry());

                        hisEntity.setCustEntityType(customerMasterDetail.getCustEntityType());
                        hisEntity.setIdentityNo(customerMasterDetail.getIdentityNo());
                        hisEntity.setIdExpiryDate(customerMasterDetail.getIdExpiryDate());

                        hisEntity.setPerDistrict(customerMasterDetail.getPerDistrict());
                        hisEntity.setMailDistrict(customerMasterDetail.getMailDistrict());
                        hisEntity.setEmpDistrict(customerMasterDetail.getEmpDistrict());

                        hisEntity.setPerOtherPOA(customerMasterDetail.getPerOtherPOA());
                        hisEntity.setMailOtherPOA(customerMasterDetail.getMailOtherPOA());
                        hisEntity.setCustEntityType(customerMasterDetail.getCustEntityType());
                        hisEntity.setJuriOtherPOA(customerMasterDetail.getJuriOtherPOA());
                        hisEntity.setLegalDocument(customerMasterDetail.getLegalDocument());
                        hisEntity.setPoa(customerMasterDetail.getPoa());
                        hisEntity.setFatherSpouseFlag(customerMasterDetail.getFatherSpouseFlag());
                        hisEntity.setCustImage(customerMasterDetail.getCustImage());
                        hisEntity.setCustFullName(customerMasterDetail.getCustFullName());
                        //end
                        //save customer history      
                        masterDetailDAO.save(hisEntity);

                        //update customer
//                        customerMasterDetail.setCustStatus("S");
                        customerMasterDetail.setAuth("N");
                        customerMasterDetail.setSuspensionFlg("Y");
                        customerMasterDetail.setLastChangeTime(date);
                        customerMasterDetail.setLastChangeUserID(userName);
                        customerMasterDetail.setTsCnt(String.valueOf(mISInfoDAO.getMaxTsCnt("cbs_customer_master_detail", customerMasterDetail.getCustomerid()) + 1));
                        masterDetailDAO.update(customerMasterDetail);

                        //update misinfo
                        CbsCustMisinfo cBSCustMISInfo = (CbsCustMisinfo) mISInfoDAO.findById(new CbsCustMisinfo(), custId);
                        if (cBSCustMISInfo != null) {
                            cBSCustMISInfo.setStatusCode("S");
                            mISInfoDAO.update(cBSCustMISInfo);
                        }
                        ut.commit();
                        msg = "Customer " + customerMasterDetail.getCustomerid() + " has been Suspended successfully.";
                    }
                }
            } else if (func.equalsIgnoreCase("V")) {
                CbsCustKycDetailsDAO custKycDetailsDAO = new CbsCustKycDetailsDAO(entityManager);
                Integer maxTxId = custKycDetailsDAO.getMaxTxnId("cbs_cust_kyc_details", customerMasterDetail.getCustomerid());

                ut.begin();
                //update kyc information
                CbsCustKycDetails custKycDetail = (CbsCustKycDetails) custKycDetailsDAO.findById(new CbsCustKycDetails(), maxTxId);
                if (custKycDetail != null) {
                    AdminManagementFacadeRemote beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");
                    custKycDetail.setKycVerifiedBy(userName);
                    custKycDetail.setKycVerifiedDate(date);
                    custKycDetail.setKycVerifiedUserName(beanRemote.getUserName(userName));
                    custKycDetailsDAO.update(custKycDetail);
                }

                customerMasterDetail.setAuth("Y");
                customerMasterDetail.setLastChangeTime(date);
                customerMasterDetail.setLastChangeUserID(userName);
                masterDetailDAO.update(customerMasterDetail);
                //update misinfo
                CbsCustMisinfo cBSCustMISInfo = (CbsCustMisinfo) mISInfoDAO.findById(new CbsCustMisinfo(), custId);
                if (cBSCustMISInfo != null) {
                    cBSCustMISInfo.setStatusCode("V");
                    mISInfoDAO.update(cBSCustMISInfo);
                }
                ut.commit();
                try {
                    npciFacade.npciRegistration(custId, "", userName, customerMasterDetail.getRegType());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                msg = "Customer " + customerMasterDetail.getCustomerid() + " has been verified successfully.";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public ParameterinfoReportTO getAgeLimit(String reportName) throws ApplicationException {
        ParameterinfoReportDAO parameterinfoReportDAO = new ParameterinfoReportDAO(entityManager);
        ParameterinfoReportTO to = new ParameterinfoReportTO();
        try {
            ParameterinfoReport entity = parameterinfoReportDAO.getCodeByReportName(reportName);
            to = ObjectAdaptorMaster.adaptToParameterinfoReportTO(entity);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return to;
    }

    private String getCityName(String cityCode) throws ApplicationException {
        try {
            List<CbsRefRecTypeTO> cityList = getCurrencyCode("001");
            for (CbsRefRecTypeTO obj : cityList) {
                if (obj.getCbsRefRecTypePKTO().getRefCode().equals(cityCode)) {
                    return obj.getRefDesc();
                }
            }
            return "";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getCustomerName(String customerId) throws ApplicationException {
        try {
            List list = entityManager.createNativeQuery("select concat(ifnull(custname,''),' ',"
                    + "ifnull(middle_name,''),' ',ifnull(last_name,'')) as custname from "
                    + "cbs_customer_master_detail where customerid='" + customerId + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no such customer id.");
            }
            Vector ele = (Vector) list.get(0);
            return ele.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getCustomerAllNames(String customerId) throws ApplicationException {
        try {
            List list = entityManager.createNativeQuery("select ifnull(custname,'') as firstname,"
                    + "ifnull(middle_name,'') as middlename,ifnull(last_name,'') as lastname "
                    + "from cbs_customer_master_detail where customerid='" + customerId + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no such customer id.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

//    public CbsCustKycDetailsTo getCustKycDetailsByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException {
//        return new CbsCustKycDetailsTo();
//    }
    public CbsCustKycDetailsTo getCustKycDetailsByCustId(java.lang.String custId) throws com.cbs.exception.ApplicationException {
        long begin = System.nanoTime();
        CbsCustKycDetailsTo custKycDetailsTo = null;
        try {
            CbsCustKycDetails custKycDetails = new CbsCustKycDetailsDAO(entityManager).getCbsCustKycDetailsByCustId(custId);
            if (custKycDetails != null) {
                custKycDetailsTo = ObjectAdaptorCustomer.adapatToCbsCustKycDetailsTo(custKycDetails);
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getCustKycDetailsByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCustKycDetailsByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getCustKycDetailsByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return custKycDetailsTo;
    }

    public List getDistrictListLike(String stateCode, String args, String callFor) throws ApplicationException {

        try {
            String condition;
            if (callFor.equalsIgnoreCase("V")) {
                condition = args;
            } else {
                condition = "%" + args + "%";
            }
            List list = entityManager.createNativeQuery("select distinct a.REF_DESC from cbs_ref_rec_type a,"
                    + "cbs_ref_rec_mapping b where a.REF_CODE = b.ss_GNO and "
                    + "a.ref_rec_no = '011' and b.GNO = '" + stateCode + "' and b.S_GNO = 'DIST' "
                    + "and REF_DESC like '" + condition + "' order by b.order_by").getResultList();
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getRecRefDiscription(String RecRefNo, String RecRefCode) throws ApplicationException {
        try {
            List list = entityManager.createNativeQuery("select REF_DESC from cbs_ref_rec_type where"
                    + " REF_REC_NO='" + RecRefNo + "' and REF_CODE='" + RecRefCode + "'").getResultList();
            if (list.size() > 0) {
                return ((Vector) list.get(0)).get(0).toString();
            } else {
                return "";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getCustomerImageMapp(String RecRefNo, String RecRefCode) throws ApplicationException {
        try {
            List list = entityManager.createNativeQuery("select SSSS_GNO from cbs_ref_rec_mapping where GNO='06'"
                    + " and SS_GNO='" + RecRefNo + "' and SSS_GNO='" + RecRefCode + "'").getResultList();
            if (list.size() > 0) {
                return ((Vector) list.get(0)).get(0).toString();
            } else {
                return "";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<CBSCustIdentityDetailsTo> getCustIdentityDetailsByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        List<CBSCustIdentityDetailsTo> custIdentityDetailsToList = new ArrayList<CBSCustIdentityDetailsTo>();
        try {
            List<CbsCustIdentityDetails> custIdentityDetailsList;
            custIdentityDetailsList = new CBSCustIdentityDetailsDAO(entityManager).getCustIdentityDetailsCustId(custId);
            for (CbsCustIdentityDetails obj : custIdentityDetailsList) {
                CBSCustIdentityDetailsTo custIdentityDetailsTo = ObjectAdaptorCustomer.adaptToCbsCustIdentityDetailsTO(obj);
                custIdentityDetailsToList.add(custIdentityDetailsTo);
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getCustKycDetailsByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCustKycDetailsByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getCustKycDetailsByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return custIdentityDetailsToList;
    }

    public List<CbsCustAdditionalAddressDetailsTo> getCustAddressDetailsByCustId(String custId) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsCustAdditionalAddressDetailsTo> custAddressDetailsToList = new ArrayList<CbsCustAdditionalAddressDetailsTo>();
        try {
            List<CbsCustAdditionalAddressDetails> custAddressDetailsList;
            custAddressDetailsList = new CbsCustAdditionalAddressDetailsDAO(entityManager).getCustAddressDetailsByCustId(custId);
            for (CbsCustAdditionalAddressDetails obj : custAddressDetailsList) {
                CbsCustAdditionalAddressDetailsTo custAddressDetailsTo = ObjectAdaptorCustomer.adaptToCbsCustAdditionalAddressDetailsTo(obj);
                custAddressDetailsToList.add(custAddressDetailsTo);
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getCustAddressDetailsByCustId()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getCustAddressDetailsByCustId()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getCustAddressDetailsByCustId() is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return custAddressDetailsToList;
    }
}