/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.master;


import com.cbs.adaptor.ObjectAdaptorLoan;
import com.cbs.adaptor.ObjectAdaptorMaster;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.loan.CbsLoanInterestCodeMasterDAO;
import com.cbs.dao.loan.CbsSchemeAccountOpenMatrixDAO;
import com.cbs.dao.loan.CbsSchemeAssetDetailsDAO;
import com.cbs.dao.loan.CbsSchemeCaSbSchDetailsDAO;
import com.cbs.dao.loan.CbsSchemeCashCrBillsAndOverdraftDetailsDAO;
import com.cbs.dao.loan.CbsSchemeCurrencyDetailsDAO;
import com.cbs.dao.loan.CbsSchemeCustAccountDetailsDAO;
import com.cbs.dao.loan.CbsSchemeDelinquencyDetailsDAO;
import com.cbs.dao.loan.CbsSchemeDepositFlowDetailsDAO;
import com.cbs.dao.loan.CbsSchemeDepositInterestDefinitionDetailsDAO;
import com.cbs.dao.loan.CbsSchemeDepositOverdueInterestParametersDAO;
import com.cbs.dao.loan.CbsSchemeDepositsSchemeParametersMaintananceDAO;
import com.cbs.dao.loan.CbsSchemeDocumentDetailsDAO;
import com.cbs.dao.loan.CbsSchemeExceptionCodeForDepositsSchemeDAO;
import com.cbs.dao.loan.CbsSchemeFeeOrChargesDetailsDAO;
import com.cbs.dao.loan.CbsSchemeFlexiFixedDepositsDetailsDAO;
import com.cbs.dao.loan.CbsSchemeGeneralSchemeParameterMasterDAO;
import com.cbs.dao.loan.CbsSchemeInterestOrServiceChargesDetailsDAO;
import com.cbs.dao.loan.CbsSchemeLedgerFolioDetailsCurrencyWiseDAO;
import com.cbs.dao.loan.CbsSchemeLoanExceptionDetailsDAO;
import com.cbs.dao.loan.CbsSchemeLoanInterestDetailsDAO;
import com.cbs.dao.loan.CbsSchemeLoanPreEiSetupDetailsDAO;
import com.cbs.dao.loan.CbsSchemeLoanPrepaymentDetailsDAO;
import com.cbs.dao.loan.CbsSchemeLoanRepaymentCycleDefinitionDAO;
import com.cbs.dao.loan.CbsSchemeLoanSchemeDetailsDAO;
import com.cbs.dao.loan.CbsSchemeTodExceptionDetailsCurrencyWiseDAO;
import com.cbs.dao.loan.CbsSchemeTodReferenceDetailsDAO;
import com.cbs.dao.loan.CbsSchemeTransactionReportCodeCurrencyWiseDAO;
import com.cbs.dao.loan.CbsSchemeValidInstrumentsDAO;
import com.cbs.dao.master.CbsExceptionMasterDAO;
import com.cbs.dao.master.CbsGlSubHeadSchemeDetailsDAO;
import com.cbs.dao.master.CbsRefRecTypeDAO;
import com.cbs.dao.master.CbsSchemePopUpFormsDAO;
import com.cbs.dao.master.GlTableDAO;
import com.cbs.dto.complex.DeliquencyTableTO;
import com.cbs.dto.complex.DepositFlowTO;
import com.cbs.dto.complex.DepositIntTO;
import com.cbs.dto.complex.DoipComplexTO;
import com.cbs.dto.complex.FeeOrChargesDetailsTO;
import com.cbs.dto.complex.GlSubHeadSchemeTO;
import com.cbs.dto.complex.LedgerFolioDetailsCurWiseTO;
import com.cbs.dto.complex.LoanAssetTO;
import com.cbs.dto.complex.LoanRepaymentCycleDefinationTO;
import com.cbs.dto.complex.SchemeDocumentDetailsTO;
import com.cbs.dto.complex.SchemeTODRefdetailsTO;
import com.cbs.dto.complex.SchemeValidInstrumentTO;
import com.cbs.dto.complex.TodExceptionDetailsTO;
import com.cbs.dto.complex.TransactionReportCodeTO;
import com.cbs.dto.loan.CbsLoanInterestCodeMasterTO;
import com.cbs.dto.loan.CbsSchemeAccountOpenMatrixTO;
import com.cbs.dto.loan.CbsSchemeAssetDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeAssetDetailsTO;
import com.cbs.dto.loan.CbsSchemeCaSbSchDetailsTO;
import com.cbs.dto.loan.CbsSchemeCashCrBillsAndOverdraftDetailsTO;
import com.cbs.dto.loan.CbsSchemeCurrencyDetailsTO;
import com.cbs.dto.loan.CbsSchemeCustAccountDetailsTO;
import com.cbs.dto.loan.CbsSchemeDelinquencyDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeDelinquencyDetailsTO;
import com.cbs.dto.loan.CbsSchemeDepositFlowDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeDepositFlowDetailsTO;
import com.cbs.dto.loan.CbsSchemeDepositInterestDefinitionDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeDepositInterestDefinitionDetailsTO;
import com.cbs.dto.loan.CbsSchemeDepositOverdueInterestParametersTO;
import com.cbs.dto.loan.CbsSchemeDepositsSchemeParametersMaintananceTO;
import com.cbs.dto.loan.CbsSchemeDocumentDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeDocumentDetailsTO;
import com.cbs.dto.loan.CbsSchemeExceptionCodeForDepositsSchemeTO;
import com.cbs.dto.loan.CbsSchemeFeeOrChargesDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeFeeOrChargesDetailsTO;
import com.cbs.dto.loan.CbsSchemeFlexiFixedDepositsDetailsTO;
import com.cbs.dto.loan.CbsSchemeGeneralSchemeParameterMasterTO;
import com.cbs.dto.loan.CbsSchemeInterestOrServiceChargesDetailsTO;
import com.cbs.dto.loan.CbsSchemeLedgerFolioDetailsCurrencyWisePKTO;
import com.cbs.dto.loan.CbsSchemeLedgerFolioDetailsCurrencyWiseTO;
import com.cbs.dto.loan.CbsSchemeLoanExceptionDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanInterestDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanPreEiSetupDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanPrepaymentDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanRepaymentCycleDefinitionPKTO;
import com.cbs.dto.loan.CbsSchemeLoanRepaymentCycleDefinitionTO;
import com.cbs.dto.loan.CbsSchemeLoanSchemeDetailsTO;
import com.cbs.dto.loan.CbsSchemeTodExceptionDetailsCurrencyWisePKTO;
import com.cbs.dto.loan.CbsSchemeTodExceptionDetailsCurrencyWiseTO;
import com.cbs.dto.loan.CbsSchemeTodReferenceDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeTodReferenceDetailsTO;
import com.cbs.dto.loan.CbsSchemeTransactionReportCodeCurrencyWisePKTO;
import com.cbs.dto.loan.CbsSchemeTransactionReportCodeCurrencyWiseTO;
import com.cbs.dto.loan.CbsSchemeValidInstrumentsPKTO;
import com.cbs.dto.loan.CbsSchemeValidInstrumentsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.dto.master.CbsGlSubHeadSchemeDetailsPKTO;
import com.cbs.dto.master.CbsGlSubHeadSchemeDetailsTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.master.CbsSchemePopUpFormsTO;
import com.cbs.dto.master.GltableTO;
import com.cbs.entity.loan.CbsLoanInterestCodeMaster;
import com.cbs.entity.loan.CbsSchemeAccountOpenMatrix;
import com.cbs.entity.loan.CbsSchemeAssetDetails;
import com.cbs.entity.loan.CbsSchemeCaSbSchDetails;
import com.cbs.entity.loan.CbsSchemeCashCrBillsAndOverdraftDetails;
import com.cbs.entity.loan.CbsSchemeCurrencyDetails;
import com.cbs.entity.loan.CbsSchemeCustAccountDetails;
import com.cbs.entity.loan.CbsSchemeDelinquencyDetails;
import com.cbs.entity.loan.CbsSchemeDepositFlowDetails;
import com.cbs.entity.loan.CbsSchemeDepositInterestDefinitionDetails;
import com.cbs.entity.loan.CbsSchemeDepositOverdueInterestParameters;
import com.cbs.entity.loan.CbsSchemeDepositsSchemeParametersMaintanance;
import com.cbs.entity.loan.CbsSchemeDocumentDetails;
import com.cbs.entity.loan.CbsSchemeExceptionCodeForDepositsScheme;
import com.cbs.entity.loan.CbsSchemeFeeOrChargesDetails;
import com.cbs.entity.loan.CbsSchemeFlexiFixedDepositsDetails;
import com.cbs.entity.loan.CbsSchemeGeneralSchemeParameterMaster;
import com.cbs.entity.loan.CbsSchemeInterestOrServiceChargesDetails;
import com.cbs.entity.loan.CbsSchemeLedgerFolioDetailsCurrencyWise;
import com.cbs.entity.loan.CbsSchemeLoanExceptionDetails;
import com.cbs.entity.loan.CbsSchemeLoanInterestDetails;
import com.cbs.entity.loan.CbsSchemeLoanPreEiSetupDetails;
import com.cbs.entity.loan.CbsSchemeLoanPrepaymentDetails;
import com.cbs.entity.loan.CbsSchemeLoanRepaymentCycleDefinition;
import com.cbs.entity.loan.CbsSchemeLoanSchemeDetails;
import com.cbs.entity.loan.CbsSchemeTodExceptionDetailsCurrencyWise;
import com.cbs.entity.loan.CbsSchemeTodReferenceDetails;
import com.cbs.entity.loan.CbsSchemeTransactionReportCodeCurrencyWise;
import com.cbs.entity.loan.CbsSchemeValidInstruments;
import com.cbs.entity.master.CbsExceptionMaster;
import com.cbs.entity.master.CbsGlSubHeadSchemeDetails;
import com.cbs.entity.master.CbsRefRecType;
import com.cbs.entity.master.CbsSchemePopUpForms;

import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ExceptionCode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
@Stateless(mappedName = "SchemeMasterManagementFacade")
@Remote({SchemeMasterManagementFacadeRemote.class})
public class SchemeMasterManagementFacade implements SchemeMasterManagementFacadeRemote {

    private static final Logger logger = Logger.getLogger(SchemeMasterManagementFacade.class);
    /**
     * Entity Manager
     */
    @PersistenceContext
    private EntityManager entityManager;

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

    public String cadSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeCustAccountDetailsDAO cbsSchCustAccDtlDAO = new CbsSchemeCustAccountDetailsDAO(entityManager);
        try {
            CbsSchemeCustAccountDetails cbsSchCustAccDtlEntity = cbsSchCustAccDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchCustAccDtlEntity != null) {
                return msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;
    }

    public String aomSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeAccountOpenMatrixDAO cbsSchAccOpenMatDAO = new CbsSchemeAccountOpenMatrixDAO(entityManager);
        try {
            CbsSchemeAccountOpenMatrix cbsSchAccOpenMatEntity = cbsSchAccOpenMatDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchAccOpenMatEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;
    }

    public String cdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeCurrencyDetailsDAO cbsSchCurDtlDAO = new CbsSchemeCurrencyDetailsDAO(entityManager);
        try {
            CbsSchemeCurrencyDetails cbsSchemeCurrencyEntity = cbsSchCurDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchemeCurrencyEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String lsdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeLoanSchemeDetailsDAO cbsSchLoanSchDtlDAO = new CbsSchemeLoanSchemeDetailsDAO(entityManager);
        try {
            CbsSchemeLoanSchemeDetails cbsSchemeLoanSchemeEntity = cbsSchLoanSchDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchemeLoanSchemeEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;


    }

    public String pmSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeGeneralSchemeParameterMasterDAO cbsSchGenSchParaMasterDAO = new CbsSchemeGeneralSchemeParameterMasterDAO(entityManager);
        try {
            CbsSchemeGeneralSchemeParameterMaster cbsSchemeGeneralSchemeParameterEntity = cbsSchGenSchParaMasterDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchemeGeneralSchemeParameterEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String crbosdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeCashCrBillsAndOverdraftDetailsDAO cbsSchCshCrBillOverDraftDAO = new CbsSchemeCashCrBillsAndOverdraftDetailsDAO(entityManager);
        try {
            CbsSchemeCashCrBillsAndOverdraftDetails CbsSchemeCashCrBillsAndOverdraftDetailsEntity = cbsSchCshCrBillOverDraftDAO.getEntityBySchemeCode(schemeCode);
            if (CbsSchemeCashCrBillsAndOverdraftDetailsEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;


    }

    public String adSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeAssetDetailsDAO cbsSchAssetDtlDAO = new CbsSchemeAssetDetailsDAO(entityManager);
        try {
            List<CbsSchemeAssetDetails> cbsSchAssetDtlEntity = cbsSchAssetDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchAssetDtlEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String csddSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeDocumentDetailsDAO cbsSchDocDtlDAO = new CbsSchemeDocumentDetailsDAO(entityManager);
        try {
            List<CbsSchemeDocumentDetails> cbsSchDocDtls = cbsSchDocDtlDAO.getAllDataBySchemeCode(schemeCode);
            if (cbsSchDocDtls.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;


    }

    public String ddSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeDelinquencyDetailsDAO cbsSchDelDtlDAO = new CbsSchemeDelinquencyDetailsDAO(entityManager);
        try {
            List<CbsSchemeDelinquencyDetails> cbsSchDelDtlEntity = cbsSchDelDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchDelDtlEntity.size() > 0) {

                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String dfdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeDepositFlowDetailsDAO cbsSchDepFlowDtlDAO = new CbsSchemeDepositFlowDetailsDAO(entityManager);
        try {
            List<CbsSchemeDepositFlowDetails> cbsSchDepFlowDtlEntity = cbsSchDepFlowDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchDepFlowDtlEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String diddSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeDepositInterestDefinitionDetailsDAO cbsSchDepIntDefDtlDAO = new CbsSchemeDepositInterestDefinitionDetailsDAO(entityManager);
        try {
            List<CbsSchemeDepositInterestDefinitionDetails> cbsSchDepIntDefDtlEntity = cbsSchDepIntDefDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchDepIntDefDtlEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String doipSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeDepositOverdueInterestParametersDAO cbsSchDepOverIntParaDAO = new CbsSchemeDepositOverdueInterestParametersDAO(entityManager);
        try {
            CbsSchemeDepositOverdueInterestParameters cbsSchDepOverIntParaEntity = cbsSchDepOverIntParaDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchDepOverIntParaEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;


    }

    public String dspmSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeDepositsSchemeParametersMaintananceDAO cbsSchDepSchParaMainDAO = new CbsSchemeDepositsSchemeParametersMaintananceDAO(entityManager);
        try {
            CbsSchemeDepositsSchemeParametersMaintanance cbsSchDepSchParaMainEntity = cbsSchDepSchParaMainDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchDepSchParaMainEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String ffddSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeFlexiFixedDepositsDetailsDAO cbsSchFlexiFixedDepDtlDAO = new CbsSchemeFlexiFixedDepositsDetailsDAO(entityManager);
        try {
            CbsSchemeFlexiFixedDepositsDetails cbsSchFlexiFixedDepDtlEntity = cbsSchFlexiFixedDepDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchFlexiFixedDepDtlEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String gshsdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsGlSubHeadSchemeDetailsDAO cbsGlSubHeadSchDtlDAO = new CbsGlSubHeadSchemeDetailsDAO(entityManager);
        try {
            List<CbsGlSubHeadSchemeDetails> cbsGlSubHeadSchDtlEntity = cbsGlSubHeadSchDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsGlSubHeadSchDtlEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String iscdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeInterestOrServiceChargesDetailsDAO cbsSchIntOrSerChgDtlDAO = new CbsSchemeInterestOrServiceChargesDetailsDAO(entityManager);
        try {
            CbsSchemeInterestOrServiceChargesDetails cbsSchIntOrSerChgDtlEntity = cbsSchIntOrSerChgDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchIntOrSerChgDtlEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String ledSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeLoanExceptionDetailsDAO cbsSchLoanExDtlDAO = new CbsSchemeLoanExceptionDetailsDAO(entityManager);
        try {
            CbsSchemeLoanExceptionDetails cbsSchLoanExDtlEntity = cbsSchLoanExDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchLoanExDtlEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String lfdcwSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeLedgerFolioDetailsCurrencyWiseDAO cbsSchLedFolioDtlDAO = new CbsSchemeLedgerFolioDetailsCurrencyWiseDAO(entityManager);
        try {
            List<CbsSchemeLedgerFolioDetailsCurrencyWise> cbsSchLedFolioDtlEntity = cbsSchLedFolioDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchLedFolioDtlEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String lidSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeLoanInterestDetailsDAO cbsSchLoanIntDtlDAO = new CbsSchemeLoanInterestDetailsDAO(entityManager);
        try {
            List<CbsSchemeLoanInterestDetails> cbsSchLoanIntDtlEntity = cbsSchLoanIntDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchLoanIntDtlEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String lpdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeLoanPrepaymentDetailsDAO cbsSchLoanPrePaymentDtlDAO = new CbsSchemeLoanPrepaymentDetailsDAO(entityManager);
        try {
            CbsSchemeLoanPrepaymentDetails cbsSchLoanPrePaymentDtlEntity = cbsSchLoanPrePaymentDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchLoanPrePaymentDtlEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String lpesdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeLoanPreEiSetupDetailsDAO cbsSchemeLoanPreEiSetupDetailsDAO = new CbsSchemeLoanPreEiSetupDetailsDAO(entityManager);
        try {
            CbsSchemeLoanPreEiSetupDetails CbsSchemeLoanPreEiSetupDetailsEntity = cbsSchemeLoanPreEiSetupDetailsDAO.getEntityBySchemeCode(schemeCode);
            if (CbsSchemeLoanPreEiSetupDetailsEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String lrcdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeLoanRepaymentCycleDefinitionDAO cbsSchLoanRepCycledefDAO = new CbsSchemeLoanRepaymentCycleDefinitionDAO(entityManager);
        try {
            List<CbsSchemeLoanRepaymentCycleDefinition> cbsSchLoanRepCycledefEntity = cbsSchLoanRepCycledefDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchLoanRepCycledefEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String sfcdSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeFeeOrChargesDetailsDAO cbsSchFeeOrChgDtlDAO = new CbsSchemeFeeOrChargesDetailsDAO(entityManager);
        try {
            List<CbsSchemeFeeOrChargesDetails> cbsSchFeeOrChgDtlEntity = cbsSchFeeOrChgDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchFeeOrChgDtlEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }

    public String spmSchemeValidation(String schemeCode) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeCaSbSchDetailsDAO cbsSchCASBSchDtlDAO = new CbsSchemeCaSbSchDetailsDAO(entityManager);
        try {
            CbsSchemeCaSbSchDetails cbsSchCASBSchDtlEntity = cbsSchCASBSchDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchCASBSchDtlEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }
    public String strdSchemeValidation(String schemeCode) throws ApplicationException{
         String msg = "";
        long begin = System.nanoTime();
        CbsSchemeTodReferenceDetailsDAO cbsSchTodRefDtlDAO = new CbsSchemeTodReferenceDetailsDAO(entityManager);
        try {
            List<CbsSchemeTodReferenceDetails> cbsSchTodRefDtlEntity = cbsSchTodRefDtlDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchTodRefDtlEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;

    }
    public String sviSchemeValidation(String schemeCode) throws ApplicationException{
      String msg = "";
        long begin = System.nanoTime();
       CbsSchemeValidInstrumentsDAO cbsSchValidInstDAO = new CbsSchemeValidInstrumentsDAO(entityManager);
        try {
            List<CbsSchemeValidInstruments> cbsSchValidInstEntity = cbsSchValidInstDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchValidInstEntity.size() > 0) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;
  
    }
    public String tedcwSchemeValidation(String schemeCode) throws ApplicationException{
     String msg = "";
        long begin = System.nanoTime();
        CbsSchemeTodExceptionDetailsCurrencyWiseDAO cbsSchTodExDtlCurWiseDAO = new CbsSchemeTodExceptionDetailsCurrencyWiseDAO(entityManager);
        try {
            CbsSchemeTodExceptionDetailsCurrencyWise cbsSchTodExDtlCurWiseEntity = cbsSchTodExDtlCurWiseDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchTodExDtlCurWiseEntity != null) {
                msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;
     
    }
    public String trccwSchemeValidation(String schemeCode) throws ApplicationException{
      String msg = "";
        long begin = System.nanoTime();
          CbsSchemeTransactionReportCodeCurrencyWiseDAO cbsSchTranRepCodeCurWiseDAO = new CbsSchemeTransactionReportCodeCurrencyWiseDAO(entityManager);
        try {
             List<CbsSchemeTransactionReportCodeCurrencyWise> cbsSchTranRepCodeCurWiseEntity = cbsSchTranRepCodeCurWiseDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchTranRepCodeCurWiseEntity.size() > 0) {
               msg = "true";
            } else {
                msg = "false";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method schemeMasterCheckCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for schemeMasterCheckCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;
       
    }
   
    public CbsSchemeLoanSchemeDetailsTO getSchTypeAndCurCodeBySchCode(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeLoanSchemeDetailsDAO cbsSchLoanSchDtlsDAO = new CbsSchemeLoanSchemeDetailsDAO(entityManager);
        CbsSchemeLoanSchemeDetailsTO cbsSchLoanSchDtlsTO = new CbsSchemeLoanSchemeDetailsTO();
        try {
            Object[] obj = (Object[]) cbsSchLoanSchDtlsDAO.getSchTypeAndCurCodeBySchCode(schemeCode);
            cbsSchLoanSchDtlsTO.setCurrencyCode(obj[0].toString());
            cbsSchLoanSchDtlsTO.setSchemeType(obj[1].toString());
        } catch (DAOException e) {
            logger.error("Exception occured while executing method findAllEvents()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllEvents()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for findAllEvents is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchLoanSchDtlsTO;
    }

    public List<CbsSchemePopUpFormsTO> getForms(String schemeType) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemePopUpFormsDAO cbsSchemePopUpFormsDAO = new CbsSchemePopUpFormsDAO(entityManager);
        List<CbsSchemePopUpFormsTO> cbsSchemePopUpFormsTOs = new ArrayList<CbsSchemePopUpFormsTO>();
        try {
            List<CbsSchemePopUpForms> cbsSchemePopUpForms = cbsSchemePopUpFormsDAO.getForms(schemeType);
            for (CbsSchemePopUpForms popUp : cbsSchemePopUpForms) {
                cbsSchemePopUpFormsTOs.add(ObjectAdaptorMaster.adaptToCBSSchemePopUpFormsTO(popUp));
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
        return cbsSchemePopUpFormsTOs;
    }

    public CbsSchemePopUpFormsTO getPopUpForm(String formId) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemePopUpFormsDAO cbsSchemePopUpFormsDAO = new CbsSchemePopUpFormsDAO(entityManager);
        CbsSchemePopUpFormsTO cbsSchemePopUpFormsTO = new CbsSchemePopUpFormsTO();
        try {
            CbsSchemePopUpForms cbsSchemePopUpForms = cbsSchemePopUpFormsDAO.findByPK(formId);
            cbsSchemePopUpFormsTO = ObjectAdaptorMaster.adaptToCBSSchemePopUpFormsTO(cbsSchemePopUpForms);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPopUpForm is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemePopUpFormsTO;
    }

    public List<CbsExceptionMasterTO> getExceptionCode() throws ApplicationException {
        long begin = System.nanoTime();
        CbsExceptionMasterDAO cbsExceptionMasterDAO = new CbsExceptionMasterDAO(entityManager);
        List<CbsExceptionMasterTO> cbsExceptionMasterTOs = new ArrayList<CbsExceptionMasterTO>();
        try {
            List<CbsExceptionMaster> cbsExceptionMasterEntity = cbsExceptionMasterDAO.geAllExceptionCode();
            if (cbsExceptionMasterEntity.size() > 0) {
                for (CbsExceptionMaster cbsExEntity : cbsExceptionMasterEntity) {
                    cbsExceptionMasterTOs.add(ObjectAdaptorMaster.adaptToCbsExceptionMasterTO(cbsExEntity));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getExceptionCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getExceptionCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getExceptionCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsExceptionMasterTOs;
    }

    public CbsExceptionMasterTO getExceptionCode(String expCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsExceptionMasterDAO cbsExceptionMasterDAO = new CbsExceptionMasterDAO(entityManager);
        CbsExceptionMasterTO cbsExceptionMasterTO = new CbsExceptionMasterTO();
        try {
            CbsExceptionMaster cbsExceptionMaster = cbsExceptionMasterDAO.findByPK(expCode);
            cbsExceptionMasterTO = ObjectAdaptorMaster.adaptToCbsExceptionMasterTO(cbsExceptionMaster);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getExceptionCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getExceptionCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getExceptionCode is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsExceptionMasterTO;
    }

    public CbsSchemeCustAccountDetailsTO cadFormData(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeCustAccountDetailsTO cbsSchemeCustAccountDetailsTO = new CbsSchemeCustAccountDetailsTO();
        CbsSchemeCustAccountDetailsDAO cbsSchemeCustAccountDetailsDAO = new CbsSchemeCustAccountDetailsDAO(entityManager);
        try {
            CbsSchemeCustAccountDetails cbsSchemeCustAccountDetails = cbsSchemeCustAccountDetailsDAO.findByPK(schemeCode);
            cbsSchemeCustAccountDetailsTO = ObjectAdaptorLoan.adaptToCBSSchemeCustAccountDetailsTO(cbsSchemeCustAccountDetails);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method cadFormData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method cadFormData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for cadFormData is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeCustAccountDetailsTO;
    }

    public List<CbsSchemeAssetDetailsTO> getSelectAssetDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeAssetDetailsTO> cbsSchemeAssetDetailsTOs = new ArrayList<CbsSchemeAssetDetailsTO>();
        CbsSchemeAssetDetailsDAO cbsSchemeAssetDetailsDAO = new CbsSchemeAssetDetailsDAO(entityManager);
        try {
            List<CbsSchemeAssetDetails> cbsSchemeAssetDetails = cbsSchemeAssetDetailsDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchemeAssetDetails.size() > 0) {
                for (CbsSchemeAssetDetails assetDetails : cbsSchemeAssetDetails) {
                    cbsSchemeAssetDetailsTOs.add(ObjectAdaptorLoan.adaptToCBSSchemeAssetDetailsTO(assetDetails));
                }
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeAssetDetailsTOs;
    }

    public CbsSchemeAccountOpenMatrixTO getSelectAccountOpenMatrix(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeAccountOpenMatrixTO cbsSchemeAccountOpenMatrixTO = new CbsSchemeAccountOpenMatrixTO();
        CbsSchemeAccountOpenMatrixDAO cbsSchemeAccountOpenMatrixDAO = new CbsSchemeAccountOpenMatrixDAO(entityManager);
        try {
            CbsSchemeAccountOpenMatrix cbsSchemeAccountOpenMatrix = cbsSchemeAccountOpenMatrixDAO.getEntityBySchemeCode(schemeCode);
            cbsSchemeAccountOpenMatrixTO = ObjectAdaptorLoan.adaptToCBSSchemeAccountOpenMatrixTO(cbsSchemeAccountOpenMatrix);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeAccountOpenMatrixTO;
    }

    public List<CbsLoanInterestCodeMasterTO> getDataTodRef() throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsLoanInterestCodeMasterTO> cbsLoanInterestCodeMasterTOs = new ArrayList<CbsLoanInterestCodeMasterTO>();
        CbsLoanInterestCodeMasterDAO cbsLoanInterestCodeMasterDAO = new CbsLoanInterestCodeMasterDAO(entityManager);
        try {
            List<CbsLoanInterestCodeMaster> cbsLoanInterestCodeMasters = cbsLoanInterestCodeMasterDAO.getDataTodRef();
            if (cbsLoanInterestCodeMasters.size() > 0) {
                for (CbsLoanInterestCodeMaster entityList : cbsLoanInterestCodeMasters) {
                    cbsLoanInterestCodeMasterTOs.add(ObjectAdaptorLoan.adaptToCbsLoanInterestCodeMasterTO(entityList));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsLoanInterestCodeMasterTOs;
    }

    public CbsSchemeCurrencyDetailsTO getSelectCurrencyDetail(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeCurrencyDetailsTO cbsSchemeCurrencyDetailsTO = new CbsSchemeCurrencyDetailsTO();
        CbsSchemeCurrencyDetailsDAO cbsSchemeCurrencyDetailsDAO = new CbsSchemeCurrencyDetailsDAO(entityManager);
        try {
            CbsSchemeCurrencyDetails cbsSchemeCurrencyDetails = cbsSchemeCurrencyDetailsDAO.getEntityBySchemeCode(schemeCode);
            cbsSchemeCurrencyDetailsTO = ObjectAdaptorLoan.adaptToCBSSchemeCurrencyDetailsTO(cbsSchemeCurrencyDetails);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeCurrencyDetailsTO;
    }

    public CbsSchemeCashCrBillsAndOverdraftDetailsTO getCrBosddetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeCashCrBillsAndOverdraftDetailsTO cbsSchemeCashCrBillsAndOverdraftDetailsTO = new CbsSchemeCashCrBillsAndOverdraftDetailsTO();
        CbsSchemeCashCrBillsAndOverdraftDetailsDAO cbsSchemeCashCrBillsAndOverdraftDetailsDAO = new CbsSchemeCashCrBillsAndOverdraftDetailsDAO(entityManager);
        try {
            CbsSchemeCashCrBillsAndOverdraftDetails correspEntity = cbsSchemeCashCrBillsAndOverdraftDetailsDAO.getEntityBySchemeCode(schemeCode);
            cbsSchemeCashCrBillsAndOverdraftDetailsTO = ObjectAdaptorLoan.adaptToCBSSchemeCashCrBillsAndOverdraftDetailsTO(correspEntity);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeCashCrBillsAndOverdraftDetailsTO;
    }

    public List<CbsSchemeDocumentDetailsTO> getCBSDocumentDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeDocumentDetailsTO> cbsSchemeDocumentDetailsTOs = new ArrayList<CbsSchemeDocumentDetailsTO>();
        CbsSchemeDocumentDetailsDAO cbsSchemeDocumentDetailsDAO = new CbsSchemeDocumentDetailsDAO(entityManager);
        try {
            List<CbsSchemeDocumentDetails> cbsSchemeDocumentDetails = cbsSchemeDocumentDetailsDAO.getAllDataBySchemeCode(schemeCode);
            if (cbsSchemeDocumentDetails.size() > 0) {
                for (CbsSchemeDocumentDetails entityList : cbsSchemeDocumentDetails) {
                    cbsSchemeDocumentDetailsTOs.add(ObjectAdaptorLoan.adaptToCbsSchemeDocumentDetailsTO(entityList));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeDocumentDetailsTOs;
    }

    public List<String> getDepositOverDueIntParameter(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<String> depositOverDueIntParameter = new ArrayList<String>();
        CbsSchemeExceptionCodeForDepositsSchemeDAO cbsSchemeExceptionCodeForDepositsSchemeDAO = new CbsSchemeExceptionCodeForDepositsSchemeDAO(entityManager);
        try {
            Object[] obj = (Object[]) cbsSchemeExceptionCodeForDepositsSchemeDAO.getDepositOverDueIntParameter(schemeCode);
            for (int i = 0; i < obj.length; i++) {
                depositOverDueIntParameter.add(obj[i].toString());
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return depositOverDueIntParameter;
    }

    public List<CbsSchemeDepositFlowDetailsTO> getDepositFlowDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeDepositFlowDetailsTO> cbsSchemeDepositFlowDetailsTO = new ArrayList<CbsSchemeDepositFlowDetailsTO>();
        CbsSchemeDepositFlowDetailsDAO cbsSchemeDepositFlowDetailsDAO = new CbsSchemeDepositFlowDetailsDAO(entityManager);
        try {
            List<CbsSchemeDepositFlowDetails> cbsSchemeDepositFlowDetails = cbsSchemeDepositFlowDetailsDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchemeDepositFlowDetails.size() > 0) {
                for (CbsSchemeDepositFlowDetails entityList : cbsSchemeDepositFlowDetails) {
                    cbsSchemeDepositFlowDetailsTO.add(ObjectAdaptorLoan.adaptToCBSSchemeDepositFlowDetailsTO(entityList));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeDepositFlowDetailsTO;
    }

    public List<CbsSchemeDepositInterestDefinitionDetailsTO> getDIDDDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeDepositInterestDefinitionDetailsTO> toS = new ArrayList<CbsSchemeDepositInterestDefinitionDetailsTO>();
        CbsSchemeDepositInterestDefinitionDetailsDAO dao = new CbsSchemeDepositInterestDefinitionDetailsDAO(entityManager);
        try {
            List<CbsSchemeDepositInterestDefinitionDetails> cbsSchemeDepositInterestDefinitionDetails = dao.getEntityBySchemeCode(schemeCode);
            if (cbsSchemeDepositInterestDefinitionDetails.size() > 0) {
                for (CbsSchemeDepositInterestDefinitionDetails entity : cbsSchemeDepositInterestDefinitionDetails) {
                    toS.add(ObjectAdaptorLoan.adaptToCBSSchemeDepositInterestDefinitionDetailsTO(entity));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDIDDDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDIDDDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDIDDDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return toS;
    }

    public List<GlSubHeadSchemeTO> getGSHSDDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<GlSubHeadSchemeTO> listTOs = new ArrayList<GlSubHeadSchemeTO>();
        CbsGlSubHeadSchemeDetailsDAO dao = new CbsGlSubHeadSchemeDetailsDAO(entityManager);
        try {
            List<Object[]> obj = dao.getGSHSDDetails(schemeCode);
            if (obj.size() > 0) {
                for (int i = 0; i < obj.size(); i++) {
                    GlSubHeadSchemeTO toObj = new GlSubHeadSchemeTO();
                    Object[] listContentObj = obj.get(i);
                    CbsGlSubHeadSchemeDetailsTO entityTO = ObjectAdaptorMaster.adaptToCbsGlSubHeadSchDtlsTO((CbsGlSubHeadSchemeDetails) listContentObj[0]);
                    CbsGlSubHeadSchemeDetailsPKTO pKTo = entityTO.getCbsGlSubHeadSchemeDetailsPKTO();

                    toObj.setSchemeCode(pKTo.getSchemeCode());
                    toObj.setSchemeType(entityTO.getSchemeType());
                    toObj.setGlSubHeadCode(pKTo.getGlSubHeadCode());
                    toObj.setDefaultFlag(entityTO.getDfltFlag());

                    toObj.setDelFlag(entityTO.getDelFlag());
                    toObj.setAcName(listContentObj[1].toString());
                    //toObj.setSaveUpdateCounter("G");
                    listTOs.add(toObj);
                }
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method getGSHSDDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getGSHSDDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getGSHSDDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return listTOs;
    }

    public GltableTO getGLTable(String acNo) throws ApplicationException {
        long begin = System.nanoTime();
        GltableTO to = new GltableTO();
        GlTableDAO dao = new GlTableDAO(entityManager);
        try {
            Gltable gltable = dao.findByPK(acNo);
            to = ObjectAdaptorMaster.adaptToGltableTO(gltable);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getGLTable()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getGLTable()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getGLTable is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return to;
    }

    public CbsSchemeDepositsSchemeParametersMaintananceTO selectDepositParameterMaintenance(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeDepositsSchemeParametersMaintananceTO singleTO = new CbsSchemeDepositsSchemeParametersMaintananceTO();
        CbsSchemeDepositsSchemeParametersMaintananceDAO dao = new CbsSchemeDepositsSchemeParametersMaintananceDAO(entityManager);
        try {
            CbsSchemeDepositsSchemeParametersMaintanance singleEntity = dao.getEntityBySchemeCode(schemeCode);
            singleTO = ObjectAdaptorLoan.adaptToCBSSchemeDepositsSchemeParametersMaintananceTO(singleEntity);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return singleTO;
    }

    public CbsSchemeInterestOrServiceChargesDetailsTO getIscdDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeInterestOrServiceChargesDetailsTO singleTO = new CbsSchemeInterestOrServiceChargesDetailsTO();
        CbsSchemeInterestOrServiceChargesDetailsDAO dao = new CbsSchemeInterestOrServiceChargesDetailsDAO(entityManager);
        try {
            CbsSchemeInterestOrServiceChargesDetails singleEntity = dao.getEntityBySchemeCode(schemeCode);
            singleTO = ObjectAdaptorLoan.adaptToCbsSchemeInterestOrServiceChargesDetailsTO(singleEntity);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return singleTO;
    }

    public CbsSchemeLoanExceptionDetailsTO getLedDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeLoanExceptionDetailsTO singleTO = new CbsSchemeLoanExceptionDetailsTO();
        CbsSchemeLoanExceptionDetailsDAO dao = new CbsSchemeLoanExceptionDetailsDAO(entityManager);
        try {
            CbsSchemeLoanExceptionDetails singleEntity = dao.getEntityBySchemeCode(schemeCode);
            singleTO = ObjectAdaptorLoan.adaptToCbsSchemeLoanExceptionDetailsTO(singleEntity);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return singleTO;
    }

    public List<CbsSchemeValidInstrumentsTO> getSviDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeValidInstrumentsTO> listTOs = new ArrayList<CbsSchemeValidInstrumentsTO>();
        CbsSchemeValidInstrumentsDAO dao = new CbsSchemeValidInstrumentsDAO(entityManager);
        try {
            List<CbsSchemeValidInstruments> listEntity = dao.getEntityBySchemeCode(schemeCode);
            if (listEntity.size() > 0) {
                for (CbsSchemeValidInstruments singleEntity : listEntity) {
                    listTOs.add(ObjectAdaptorLoan.adaptToCbsSchemeValidInstrumentsTO(singleEntity));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return listTOs;
    }

    public List<CbsSchemeLedgerFolioDetailsCurrencyWiseTO> getLfdcwDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeLedgerFolioDetailsCurrencyWiseTO> listTOs = new ArrayList<CbsSchemeLedgerFolioDetailsCurrencyWiseTO>();
        CbsSchemeLedgerFolioDetailsCurrencyWiseDAO dao = new CbsSchemeLedgerFolioDetailsCurrencyWiseDAO(entityManager);
        try {
            List<CbsSchemeLedgerFolioDetailsCurrencyWise> listEntity = dao.getEntityBySchemeCode(schemeCode);
            if (listEntity.size() > 0) {
                for (CbsSchemeLedgerFolioDetailsCurrencyWise singleEntity : listEntity) {
                    listTOs.add(ObjectAdaptorLoan.adaptToCbsSchemeLedgerFolioDetailsCurrencyWiseTO(singleEntity));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return listTOs;
    }

    public List<CbsSchemeLoanRepaymentCycleDefinitionTO> getLrcddetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeLoanRepaymentCycleDefinitionTO> listTOs = new ArrayList<CbsSchemeLoanRepaymentCycleDefinitionTO>();
        CbsSchemeLoanRepaymentCycleDefinitionDAO dao = new CbsSchemeLoanRepaymentCycleDefinitionDAO(entityManager);
        try {
            List<CbsSchemeLoanRepaymentCycleDefinition> listEntity = dao.getEntityBySchemeCode(schemeCode);
            if (listEntity.size() > 0) {
                for (CbsSchemeLoanRepaymentCycleDefinition singleEntity : listEntity) {
                    listTOs.add(ObjectAdaptorLoan.adaptToCbsSchemeLoanRepaymentCycleDefinitionTO(singleEntity));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return listTOs;
    }

    public List<CbsSchemeFeeOrChargesDetailsTO> getSfcdDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeFeeOrChargesDetailsTO> listTOs = new ArrayList<CbsSchemeFeeOrChargesDetailsTO>();
        CbsSchemeFeeOrChargesDetailsDAO dao = new CbsSchemeFeeOrChargesDetailsDAO(entityManager);
        try {
            List<CbsSchemeFeeOrChargesDetails> listEntity = dao.getEntityBySchemeCode(schemeCode);
            if (listEntity.size() > 0) {
                for (CbsSchemeFeeOrChargesDetails singleEntity : listEntity) {
                    listTOs.add(ObjectAdaptorLoan.adaptToCbsSchemeFeeOrChargesDetailsTO(singleEntity));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return listTOs;
    }

    public List<CbsSchemeTransactionReportCodeCurrencyWiseTO> getTrccwdetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeTransactionReportCodeCurrencyWiseTO> listTOs = new ArrayList<CbsSchemeTransactionReportCodeCurrencyWiseTO>();
        CbsSchemeTransactionReportCodeCurrencyWiseDAO dao = new CbsSchemeTransactionReportCodeCurrencyWiseDAO(entityManager);
        try {
            List<CbsSchemeTransactionReportCodeCurrencyWise> listEntity = dao.getEntityBySchemeCode(schemeCode);
            if (listEntity.size() > 0) {
                for (CbsSchemeTransactionReportCodeCurrencyWise singleEntity : listEntity) {
                    listTOs.add(ObjectAdaptorLoan.adaptToCbsSchemeTransactionReportCodeCurrencyWiseTO(singleEntity));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return listTOs;
    }

    public List<CbsSchemeTodReferenceDetailsTO> getStrdDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeTodReferenceDetailsTO> listTOs = new ArrayList<CbsSchemeTodReferenceDetailsTO>();
        CbsSchemeTodReferenceDetailsDAO dao = new CbsSchemeTodReferenceDetailsDAO(entityManager);
        try {
            List<CbsSchemeTodReferenceDetails> listEntity = dao.getEntityBySchemeCode(schemeCode);
            if (listEntity.size() > 0) {
                for (CbsSchemeTodReferenceDetails sigleEntity : listEntity) {
                    listTOs.add(ObjectAdaptorLoan.adaptToCbsSchemeTodReferenceDetailsTo(sigleEntity));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return listTOs;
    }

    public List<TodExceptionDetailsTO> getTODExceptionDetailsAccToSchemeTod(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<TodExceptionDetailsTO> listTOs = new ArrayList<TodExceptionDetailsTO>();
        CbsSchemeTodExceptionDetailsCurrencyWiseDAO dao = new CbsSchemeTodExceptionDetailsCurrencyWiseDAO(entityManager);
        try {
            List<Object[]> obj = dao.getDataGlTableAccToSchemeTodDetails(schemeCode);
            if (obj.size() > 0) {
                for (int i = 0; i < obj.size(); i++) {
                    TodExceptionDetailsTO toObj = new TodExceptionDetailsTO();
                    Object[] listContentObj = obj.get(i);
                    CbsSchemeTodExceptionDetailsCurrencyWiseTO todSingleTO = ObjectAdaptorLoan.adaptToCbsSchemeTodExceptionDetailsCurrencyWiseTO((CbsSchemeTodExceptionDetailsCurrencyWise) listContentObj[0]);
                    CbsExceptionMasterTO expSingleTO = ObjectAdaptorMaster.adaptToCbsExceptionMasterTO((CbsExceptionMaster) listContentObj[1]);
                    toObj.setBeginAmount(todSingleTO.getBeginAmount());
                    toObj.setCurrencyCode(todSingleTO.getCbsSchemeTodExceptionDetailsCurrencyWisePKTO().getCurrencyCode());
                    toObj.setDelFlag(todSingleTO.getDelFlag());
                    toObj.setEndAmount(todSingleTO.getEndAmount());
                    toObj.setExceptionCode(expSingleTO.getExceptionCode());
                    toObj.setExceptionDesc(expSingleTO.getExceptionDesc());
                    toObj.setExceptionType(expSingleTO.getExceptionType());
                    toObj.setSchemeCode(todSingleTO.getCbsSchemeTodExceptionDetailsCurrencyWisePKTO().getSchemeCode());
                    toObj.setSchemeType(todSingleTO.getSchemeType());
                    toObj.setTodException(todSingleTO.getTodException());
                    toObj.setTodSrlNo(todSingleTO.getCbsSchemeTodExceptionDetailsCurrencyWisePKTO().getTodSrlNo());
                    toObj.setCounterSaveUpdate("G");
                    listTOs.add(toObj);
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getGSHSDDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getGSHSDDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getGSHSDDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return listTOs;
    }

    /********Save & Update Handling*********/
    public String saveSchemeMaster(List<CbsSchemeCustAccountDetailsTO> cbsSchemeCustAccountDetailsTOs, List<CbsSchemeAccountOpenMatrixTO> cbsSchemeAccountOpenMatrixTOs, List<CbsSchemeCurrencyDetailsTO> cbsSchemeCurrencyDetailsTOs, List<CbsSchemeCashCrBillsAndOverdraftDetailsTO> cbsSchemeCashCrBillsAndOverdraftDetailsTOs, List<DoipComplexTO> depositOverDueIntParameter, List<GlSubHeadSchemeTO> glSubHeadSchemeTOs, List<CbsSchemeDepositsSchemeParametersMaintananceTO> cbsSchDepSchParaMainTOs, List<CbsSchemeInterestOrServiceChargesDetailsTO> cbsSchIntSerChgDtls, List<CbsSchemeLoanExceptionDetailsTO> cbsSchLoanExpDtls, List<SchemeValidInstrumentTO> schValidComplexTO, List<LedgerFolioDetailsCurWiseTO> ledgerFolioDtls, List<LoanRepaymentCycleDefinationTO> loanRepaymentCyclDefs, List<FeeOrChargesDetailsTO> feeOrChargesDtls, List<TransactionReportCodeTO> transRepCode, List<SchemeTODRefdetailsTO> schemeTodRefdetails, List<TodExceptionDetailsTO> todExceptionDetails, List<SchemeDocumentDetailsTO> schemeDocumentdetails, List<DepositFlowTO> depositFlowTO, List<LoanAssetTO> loanAssetTO, List<DepositIntTO> depositIntTO, List<CbsSchemeFlexiFixedDepositsDetailsTO> cbsSchemeFlexiFixedDepositsDetailsTOs, List<CbsSchemeLoanPrepaymentDetailsTO> cbsSchemeLoanPrepaymentDetailsTOs, List<CbsSchemeLoanSchemeDetailsTO> cbsSchemeLoanSchemeDetailsTOs, List<CbsSchemeCaSbSchDetailsTO> cbsSchemeCaSbSchDetailsTOs, List<DeliquencyTableTO> deliqDetailsTOs, List<CbsSchemeLoanInterestDetailsTO> cbsSchemeLoanInterestDetailsTOs, List<CbsSchemeLoanPreEiSetupDetailsTO> cbsSchemeLoanPreEiSetupDetailsTOs, List<CbsSchemeGeneralSchemeParameterMasterTO> cbsSchemeGeneralSchemeParameterMasterTOs) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeFlexiFixedDepositsDetailsDAO ffddDaoObj = new CbsSchemeFlexiFixedDepositsDetailsDAO(entityManager);
        CbsSchemeDelinquencyDetailsDAO ddDao = new CbsSchemeDelinquencyDetailsDAO(entityManager);
        CbsSchemeLoanPrepaymentDetailsDAO lpdDao = new CbsSchemeLoanPrepaymentDetailsDAO(entityManager);
        CbsSchemeLoanSchemeDetailsDAO lsdDao = new CbsSchemeLoanSchemeDetailsDAO(entityManager);
        CbsSchemeCaSbSchDetailsDAO spmDao = new CbsSchemeCaSbSchDetailsDAO(entityManager);
        CbsSchemeLoanInterestDetailsDAO lidDao = new CbsSchemeLoanInterestDetailsDAO(entityManager);
        CbsSchemeLoanPreEiSetupDetailsDAO lpesdDao = new CbsSchemeLoanPreEiSetupDetailsDAO(entityManager);
        CbsSchemeGeneralSchemeParameterMasterDAO pmDao = new CbsSchemeGeneralSchemeParameterMasterDAO(entityManager);
        CbsSchemeTodExceptionDetailsCurrencyWiseDAO todDao = new CbsSchemeTodExceptionDetailsCurrencyWiseDAO(entityManager);
        CbsSchemeTodReferenceDetailsDAO strdDao = new CbsSchemeTodReferenceDetailsDAO(entityManager);
        CbsSchemeTransactionReportCodeCurrencyWiseDAO trccwDao = new CbsSchemeTransactionReportCodeCurrencyWiseDAO(entityManager);
        CbsSchemeFeeOrChargesDetailsDAO sfcdDao = new CbsSchemeFeeOrChargesDetailsDAO(entityManager);
        CbsSchemeAssetDetailsDAO adDao = new CbsSchemeAssetDetailsDAO(entityManager);
        CbsSchemeDepositInterestDefinitionDetailsDAO diddDao = new CbsSchemeDepositInterestDefinitionDetailsDAO(entityManager);
        CbsSchemeDepositFlowDetailsDAO dfdDao = new CbsSchemeDepositFlowDetailsDAO(entityManager);
        CbsSchemeDocumentDetailsDAO csddDao = new CbsSchemeDocumentDetailsDAO(entityManager);
        /*****Added by dinesh***/
        CbsSchemeCustAccountDetailsDAO cad = new CbsSchemeCustAccountDetailsDAO(entityManager);
        CbsSchemeAccountOpenMatrixDAO aom = new CbsSchemeAccountOpenMatrixDAO(entityManager);
        CbsSchemeCurrencyDetailsDAO cd = new CbsSchemeCurrencyDetailsDAO(entityManager);
        CbsSchemeCashCrBillsAndOverdraftDetailsDAO crbosd = new CbsSchemeCashCrBillsAndOverdraftDetailsDAO(entityManager);
        CbsSchemeExceptionCodeForDepositsSchemeDAO doipExp = new CbsSchemeExceptionCodeForDepositsSchemeDAO(entityManager);
        CbsSchemeDepositOverdueInterestParametersDAO doipParam = new CbsSchemeDepositOverdueInterestParametersDAO(entityManager);
        CbsGlSubHeadSchemeDetailsDAO gshsd = new CbsGlSubHeadSchemeDetailsDAO(entityManager);
        CbsSchemeDepositsSchemeParametersMaintananceDAO dspm = new CbsSchemeDepositsSchemeParametersMaintananceDAO(entityManager);
        CbsSchemeInterestOrServiceChargesDetailsDAO iscd = new CbsSchemeInterestOrServiceChargesDetailsDAO(entityManager);
        CbsSchemeLoanExceptionDetailsDAO led = new CbsSchemeLoanExceptionDetailsDAO(entityManager);
        CbsSchemeLedgerFolioDetailsCurrencyWiseDAO lfdcw = new CbsSchemeLedgerFolioDetailsCurrencyWiseDAO(entityManager);
        CbsSchemeLoanRepaymentCycleDefinitionDAO lrcd = new CbsSchemeLoanRepaymentCycleDefinitionDAO(entityManager);
        CbsSchemeFeeOrChargesDetailsDAO sfcd = new CbsSchemeFeeOrChargesDetailsDAO(entityManager);
        CbsSchemeValidInstrumentsDAO svi = new CbsSchemeValidInstrumentsDAO(entityManager);

        try {
            /***ffdd form processing***/
            if (cbsSchemeFlexiFixedDepositsDetailsTOs != null) {
                for (CbsSchemeFlexiFixedDepositsDetailsTO singleTO : cbsSchemeFlexiFixedDepositsDetailsTOs) {
                    CbsSchemeFlexiFixedDepositsDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeFlexiFixedDepositsDetailsEntity(singleTO);
                    ffddDaoObj.save(singleEntity);
                }
            }
            /***DD form processing***/
            if (deliqDetailsTOs != null) {
                CbsSchemeDelinquencyDetailsTO orgToObj = new CbsSchemeDelinquencyDetailsTO();
                CbsSchemeDelinquencyDetailsPKTO orgPkToObj = new CbsSchemeDelinquencyDetailsPKTO();

                for (DeliquencyTableTO singleComTO : deliqDetailsTOs) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDelinqCycle(singleComTO.getDeliqCycle());
                        orgToObj.setCbsSchemeDelinquencyDetailsPKTO(orgPkToObj);
                        orgToObj.setPlaceHolder(singleComTO.getPlaceHolder());
                        orgToObj.setProvisionPercent(singleComTO.getProvisionPercent());
                        orgToObj.setDaysPastDue(singleComTO.getDaysPastDue());
                        orgToObj.setDelFlag(singleComTO.getDelFlagDeliq());

                        CbsSchemeDelinquencyDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDelInquencyDetailsEntity(orgToObj);
                        ddDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDelinqCycle(singleComTO.getDeliqCycle());
                        orgToObj.setCbsSchemeDelinquencyDetailsPKTO(orgPkToObj);
                        orgToObj.setPlaceHolder(singleComTO.getPlaceHolder());
                        orgToObj.setProvisionPercent(singleComTO.getProvisionPercent());
                        orgToObj.setDaysPastDue(singleComTO.getDaysPastDue());
                        orgToObj.setDelFlag(singleComTO.getDelFlagDeliq());
                        CbsSchemeDelinquencyDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDelInquencyDetailsEntity(orgToObj);
                        ddDao.update(singleEntity);
                    }
                }
            }
            /***lpd form processing***/
            if (cbsSchemeLoanPrepaymentDetailsTOs != null) {
                for (CbsSchemeLoanPrepaymentDetailsTO singleTO : cbsSchemeLoanPrepaymentDetailsTOs) {
                    CbsSchemeLoanPrepaymentDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanPrepaymentDetailsEntity(singleTO);
                    lpdDao.save(singleEntity);
                }
            }
            /***lsd form processing***/
            if (cbsSchemeLoanSchemeDetailsTOs != null) {
                for (CbsSchemeLoanSchemeDetailsTO singleTO : cbsSchemeLoanSchemeDetailsTOs) {
                    CbsSchemeLoanSchemeDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanSchemeDetailsEntity(singleTO);
                    lsdDao.save(singleEntity);
                }
            }
            /***spm form processing***/
            if (cbsSchemeCaSbSchDetailsTOs != null) {
                for (CbsSchemeCaSbSchDetailsTO singleTO : cbsSchemeCaSbSchDetailsTOs) {
                    CbsSchemeCaSbSchDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeCASBSchDetailsEntity(singleTO);
                    spmDao.save(singleEntity);
                }
            }
            /***lid form processing***/
            if (cbsSchemeLoanInterestDetailsTOs != null) {
                for (CbsSchemeLoanInterestDetailsTO singleTO : cbsSchemeLoanInterestDetailsTOs) {
                    CbsSchemeLoanInterestDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanInterestDetailsEntity(singleTO);
                    lidDao.save(singleEntity);
                }
            }
            /***lpesd form processing***/
            if (cbsSchemeLoanPreEiSetupDetailsTOs != null) {
                for (CbsSchemeLoanPreEiSetupDetailsTO singleTO : cbsSchemeLoanPreEiSetupDetailsTOs) {
                    CbsSchemeLoanPreEiSetupDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanPreEiSetupDetailsEntity(singleTO);
                    lpesdDao.save(singleEntity);
                }
            }
            /***pm form processing***/
            if (cbsSchemeGeneralSchemeParameterMasterTOs != null) {
                for (CbsSchemeGeneralSchemeParameterMasterTO singleTO : cbsSchemeGeneralSchemeParameterMasterTOs) {
                    CbsSchemeGeneralSchemeParameterMaster singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeGeneralSchemeParameterMasterEntity(singleTO);
                    pmDao.save(singleEntity);
                }
            }
            /***TEDCW form processing***/
            if (todExceptionDetails != null) {
                CbsSchemeTodExceptionDetailsCurrencyWiseTO orgToObj = new CbsSchemeTodExceptionDetailsCurrencyWiseTO();
                CbsSchemeTodExceptionDetailsCurrencyWisePKTO orgPkToObj = new CbsSchemeTodExceptionDetailsCurrencyWisePKTO();
                for (TodExceptionDetailsTO singleComTO : todExceptionDetails) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        Object obj = (Object) todDao.getTodSerialNo();
                        if (obj==null||obj.toString() == null || obj.toString().equalsIgnoreCase("")) {
                            orgPkToObj.setTodSrlNo("1");
                        } else {
                            Integer sn = Integer.parseInt(obj.toString());
                            sn = sn + 1;
                            orgPkToObj.setTodSrlNo(sn.toString());
                        }
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setCurrencyCode(singleComTO.getCurrencyCode());

                        orgToObj.setCbsSchemeTodExceptionDetailsCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setBeginAmount(singleComTO.getBeginAmount());
                        orgToObj.setEndAmount(singleComTO.getEndAmount());
                        orgToObj.setTodException(singleComTO.getTodException());
                        orgToObj.setDelFlag(singleComTO.getDelFlag());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        CbsSchemeTodExceptionDetailsCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTodExceptionDetailsCurrencyWiseEntity(orgToObj);
                        todDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setCurrencyCode(singleComTO.getCurrencyCode());
                        orgPkToObj.setTodSrlNo(singleComTO.getTodSrlNo());
                        orgToObj.setCbsSchemeTodExceptionDetailsCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setBeginAmount(singleComTO.getBeginAmount());
                        orgToObj.setEndAmount(singleComTO.getEndAmount());
                        orgToObj.setTodException(singleComTO.getTodException());
                        orgToObj.setDelFlag(singleComTO.getDelFlag());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        CbsSchemeTodExceptionDetailsCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTodExceptionDetailsCurrencyWiseEntity(orgToObj);
                        todDao.update(singleEntity);
                    }
                }
            }
            /***STRD form processing***/
            if (schemeTodRefdetails != null) {
                CbsSchemeTodReferenceDetailsTO orgToObj = new CbsSchemeTodReferenceDetailsTO();
                CbsSchemeTodReferenceDetailsPKTO orgPkToObj = new CbsSchemeTodReferenceDetailsPKTO();
                for (SchemeTODRefdetailsTO singleComTO : schemeTodRefdetails) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setReferenceType(singleComTO.getReferenceType());
                        orgToObj.setCbsSchemeTodReferenceDetailsPKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setDelFlag(singleComTO.getDelFlagTodRef());
                        orgToObj.setDiscretAdvnType(singleComTO.getDiscretAdvnType());
                        orgToObj.setDiscretAdvnCategory(singleComTO.getDiscretAdvnCategory());
                        orgToObj.setDiscretNumberOfDays(singleComTO.getDiscretNumberOfDays());
                        orgToObj.setPenalDays(singleComTO.getPenalDays());
                        orgToObj.setIntFlag(singleComTO.getIntFlag());
                        orgToObj.setFreeTextCode(singleComTO.getFreeTxtCode());
                        orgToObj.setInterestTableCode(singleComTO.getInterestTableCode());
                        orgToObj.setToLevelIntTblCode(singleComTO.getToLevelIntTblCode());
                        CbsSchemeTodReferenceDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTodReferenceDetailsEntity(orgToObj);
                        strdDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setReferenceType(singleComTO.getReferenceType());
                        orgToObj.setCbsSchemeTodReferenceDetailsPKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setDelFlag(singleComTO.getDelFlagTodRef());
                        orgToObj.setDiscretAdvnType(singleComTO.getDiscretAdvnType());
                        orgToObj.setDiscretAdvnCategory(singleComTO.getDiscretAdvnCategory());
                        orgToObj.setDiscretNumberOfDays(singleComTO.getDiscretNumberOfDays());
                        orgToObj.setPenalDays(singleComTO.getPenalDays());
                        orgToObj.setIntFlag(singleComTO.getIntFlag());
                        orgToObj.setFreeTextCode(singleComTO.getFreeTxtCode());
                        orgToObj.setInterestTableCode(singleComTO.getInterestTableCode());
                        orgToObj.setToLevelIntTblCode(singleComTO.getToLevelIntTblCode());
                        CbsSchemeTodReferenceDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTodReferenceDetailsEntity(orgToObj);
                        strdDao.update(singleEntity);
                    }
                }
            }
            /***TRCCW form processing***/
            if (transRepCode != null) {
                CbsSchemeTransactionReportCodeCurrencyWiseTO orgToObj = new CbsSchemeTransactionReportCodeCurrencyWiseTO();
                CbsSchemeTransactionReportCodeCurrencyWisePKTO orgPkToObj = new CbsSchemeTransactionReportCodeCurrencyWisePKTO();
                for (TransactionReportCodeTO singleComTO : transRepCode) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setTransactionReportCode(singleComTO.getTransactionReportCode());
                        orgToObj.setCbsSchemeTransactionReportCodeCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setCrAmtLimit(new BigDecimal(singleComTO.getCrAmt()));
                        orgToObj.setDrAmtLimit(new BigDecimal(singleComTO.getDrAmt()));
                        orgToObj.setCurrencyCode(singleComTO.getCurrencyCodeTranRep());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setDelFlag(singleComTO.getDeleteTranCode());
                        CbsSchemeTransactionReportCodeCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTransactionReportCodeCurrencyWiseEntity(orgToObj);
                        trccwDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setTransactionReportCode(singleComTO.getTransactionReportCode());
                        orgToObj.setCbsSchemeTransactionReportCodeCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setCrAmtLimit(new BigDecimal(singleComTO.getCrAmt()));
                        orgToObj.setDrAmtLimit(new BigDecimal(singleComTO.getDrAmt()));
                        orgToObj.setCurrencyCode(singleComTO.getCurrencyCodeTranRep());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setDelFlag(singleComTO.getDeleteTranCode());
                        CbsSchemeTransactionReportCodeCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTransactionReportCodeCurrencyWiseEntity(orgToObj);
                        trccwDao.update(singleEntity);
                    }
                }
            }
            /***SFCD form processing***/
            if (feeOrChargesDtls != null) {
                CbsSchemeFeeOrChargesDetailsTO orgToObj = new CbsSchemeFeeOrChargesDetailsTO();
                CbsSchemeFeeOrChargesDetailsPKTO orgPkToObj = new CbsSchemeFeeOrChargesDetailsPKTO();
                for (FeeOrChargesDetailsTO singleComTO : feeOrChargesDtls) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setChargeType(singleComTO.getChargesType());
                        orgToObj.setCbsSchemeFeeOrChargesDetailsPKTO(orgPkToObj);
                        orgToObj.setAmortMethod(singleComTO.getAmortMethod());
                        orgToObj.setAmortize(singleComTO.getAmortize());
                        orgToObj.setCurrencyCode(singleComTO.getCurrencyCode());
                        orgToObj.setAssessOrDmd(singleComTO.getAssertOrDmd());
                        orgToObj.setChargeDescription(singleComTO.getChargesDesc());
                        orgToObj.setChargeEventId(singleComTO.getChargesEventId());
                        orgToObj.setConsiderForIrr(singleComTO.getConsiderForIrr());
                        orgToObj.setCrPlaceholder(singleComTO.getCrPlaceHolder());
                        orgToObj.setDeductible(singleComTO.getDeductible());
                        orgToObj.setDelFlag(singleComTO.getDelFlagFeeCharges());
                        orgToObj.setDrPlaceholder(singleComTO.getDrPlaceHolder());
                        orgToObj.setMultipleFlag(singleComTO.getMultipleFlag());
                        orgToObj.setPrepaymentFee(new BigDecimal(singleComTO.getPrePaymentFee()));
                        CbsSchemeFeeOrChargesDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeFeeOrChargesDetailsEntity(orgToObj);
                        sfcdDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setChargeType(singleComTO.getChargesType());
                        orgToObj.setCbsSchemeFeeOrChargesDetailsPKTO(orgPkToObj);
                        orgToObj.setAmortMethod(singleComTO.getAmortMethod());
                        orgToObj.setAmortize(singleComTO.getAmortize());
                        orgToObj.setCurrencyCode(singleComTO.getCurrencyCode());
                        orgToObj.setAssessOrDmd(singleComTO.getAssertOrDmd());
                        orgToObj.setChargeDescription(singleComTO.getChargesDesc());
                        orgToObj.setChargeEventId(singleComTO.getChargesEventId());
                        orgToObj.setConsiderForIrr(singleComTO.getConsiderForIrr());
                        orgToObj.setCrPlaceholder(singleComTO.getCrPlaceHolder());
                        orgToObj.setDeductible(singleComTO.getDeductible());
                        orgToObj.setDelFlag(singleComTO.getDelFlagFeeCharges());
                        orgToObj.setDrPlaceholder(singleComTO.getDrPlaceHolder());
                        orgToObj.setMultipleFlag(singleComTO.getMultipleFlag());
                        orgToObj.setPrepaymentFee(new BigDecimal(singleComTO.getPrePaymentFee()));
                        CbsSchemeFeeOrChargesDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeFeeOrChargesDetailsEntity(orgToObj);
                        sfcdDao.update(singleEntity);
                    }
                }
            }
            /***Added by dinesh****/
            /***cad form processing***/
            if (cbsSchemeCustAccountDetailsTOs != null) {
                for (CbsSchemeCustAccountDetailsTO singleTO : cbsSchemeCustAccountDetailsTOs) {
                    CbsSchemeCustAccountDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeCustAccountDetailsEntity(singleTO);
                    cad.save(singleEntity);
                }


            }

            /***aom form processing***/
            if (cbsSchemeAccountOpenMatrixTOs != null) {
                for (CbsSchemeAccountOpenMatrixTO singleTO : cbsSchemeAccountOpenMatrixTOs) {
                    CbsSchemeAccountOpenMatrix singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeAccountOpenMatrixEntity(singleTO);
                    aom.save(singleEntity);
                }
            }

            /***cd form processing***/
            if (cbsSchemeCurrencyDetailsTOs != null) {
                for (CbsSchemeCurrencyDetailsTO singleTO : cbsSchemeCurrencyDetailsTOs) {
                    CbsSchemeCurrencyDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeCurrencyDetailsEntity(singleTO);
                    cd.save(singleEntity);
                }
            }

            /***crbosd form processing***/
            if (cbsSchemeCashCrBillsAndOverdraftDetailsTOs != null) {
                for (CbsSchemeCashCrBillsAndOverdraftDetailsTO singleTO : cbsSchemeCashCrBillsAndOverdraftDetailsTOs) {
                    CbsSchemeCashCrBillsAndOverdraftDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeCashCrBillsAndOverdraftDetailsEntity(singleTO);
                    crbosd.save(singleEntity);
                }
            }

            /***doip form processing***/
            if (depositOverDueIntParameter != null) {
                CbsSchemeExceptionCodeForDepositsSchemeTO objExp = new CbsSchemeExceptionCodeForDepositsSchemeTO();
                CbsSchemeDepositOverdueInterestParametersTO objParam = new CbsSchemeDepositOverdueInterestParametersTO();
                for (DoipComplexTO singleTO : depositOverDueIntParameter) {
                    objExp.setSchemeCode(singleTO.getSchemeCode());
                    objExp.setSchemeType(singleTO.getSchemeType());
                    objExp.setCurrencyCode(singleTO.getCurrencyCode());
                    objExp.setRenewalPeriodExcd(singleTO.getRenewalPerdExcd());

                    objExp.setMaximumPeriod(singleTO.getMaxPerd());
                    objExp.setMaximumAmount(singleTO.getMaxAmt());
                    objExp.setMinorDepPreclosure(singleTO.getMinorDepPreclosure());
                    objExp.setExtension(singleTO.getExtension());

                    objExp.setSplCatgClosure(singleTO.getSplCatgClosure());
                    objExp.setMatAmtTolerance(singleTO.getMatAmtTolerance());
                    objExp.setNilPenalty(singleTO.getNilPenalty());
                    objExp.setDiscontinuedInstl(singleTO.getDisContinuedInst());

                    objExp.setTransferIn(singleTO.getTransferIn());
                    objExp.setAcctVerBalCheck(singleTO.getAcctVerBalCheck());
                    objExp.setSystemDrTransAllwd(singleTO.getSystemDrTransAllowed());
                    objExp.setDuplicateOrReprintReport(singleTO.getDupReprntRcpt());

                    objExp.setPrematureClosure(singleTO.getPreMatureClosure());
                    objExp.setNoticePerdBelowMinNoticePerd(singleTO.getNoticePerdMinNoticePerd());
                    objExp.setDefaultValueForPreferentialIntChgd(singleTO.getDefaultValueForPreIntChgd());
                    objExp.setBackValueDatedAccountOpening(singleTO.getBackValueDateAccOpen());
                    objExp.setFutureValueDatedAccountOpening(singleTO.getFutureValueDateAccOpen());

                    CbsSchemeExceptionCodeForDepositsScheme expEntity = ObjectAdaptorLoan.adaptToCbsSchemeExceptionCodeForDepositsSchemeEntity(objExp);
                    doipExp.save(expEntity);

                    objParam.setSchemeCode(singleTO.getSchemeCode());
                    objParam.setSchemeType(singleTO.getSchemeType());
                    objParam.setCurrencyCode(singleTO.getCurrencyCode());
                    objParam.setOverdueGlSubHeadCode(singleTO.getOverDueGlSubHeadCode());
                    objParam.setOverdueInterestCode(singleTO.getOverDueInterestCode());
                    objParam.setOverdueInterestTblCodeType(singleTO.getOverDueIntTblCodeType());
                    objParam.setOverdueInterestCalcMethod(singleTO.getOverDueintCalcMethod());

                    CbsSchemeDepositOverdueInterestParameters paramEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositOverdueInterestParametersEntity(objParam);
                    doipParam.save(paramEntity);
                }
            }

            /***gshsd form processing***/
            if (glSubHeadSchemeTOs != null) {
                CbsGlSubHeadSchemeDetailsTO orgToObj = new CbsGlSubHeadSchemeDetailsTO();
                CbsGlSubHeadSchemeDetailsPKTO orgPkToObj = new CbsGlSubHeadSchemeDetailsPKTO();

                for (GlSubHeadSchemeTO singleCompTO : glSubHeadSchemeTOs) {
                    String flag = singleCompTO.getSaveUpdateCounter();
                    if (flag.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setGlSubHeadCode(singleCompTO.getGlSubHeadCode());
                        orgToObj.setCbsGlSubHeadSchemeDetailsPKTO(orgPkToObj);

                        orgToObj.setSchemeType(singleCompTO.getSchemeType());
                        orgToObj.setDfltFlag(singleCompTO.getDefaultFlag());
                        orgToObj.setDelFlag(singleCompTO.getDelFlag());

                        CbsGlSubHeadSchemeDetails singleEntity = ObjectAdaptorMaster.adaptToCbsGlSubHeadSchDtlsEntity(orgToObj);
                        gshsd.save(singleEntity);
                    }
                    if (flag.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setGlSubHeadCode(singleCompTO.getGlSubHeadCode());
                        orgToObj.setCbsGlSubHeadSchemeDetailsPKTO(orgPkToObj);

                        orgToObj.setSchemeType(singleCompTO.getSchemeType());
                        orgToObj.setDfltFlag(singleCompTO.getDefaultFlag());
                        orgToObj.setDelFlag(singleCompTO.getDelFlag());

                        CbsGlSubHeadSchemeDetails singleEntity = ObjectAdaptorMaster.adaptToCbsGlSubHeadSchDtlsEntity(orgToObj);
                        gshsd.update(singleEntity);
                    }
                }
            }

            /***dspm form processing***/
            if (cbsSchDepSchParaMainTOs != null) {
                for (CbsSchemeDepositsSchemeParametersMaintananceTO singleTO : cbsSchDepSchParaMainTOs) {
                    CbsSchemeDepositsSchemeParametersMaintanance singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositsSchemeParametersMaintananceEntity(singleTO);
                    dspm.save(singleEntity);
                }
            }

            /***iscd form processing***/
            if (cbsSchIntSerChgDtls != null) {
                for (CbsSchemeInterestOrServiceChargesDetailsTO singleTO : cbsSchIntSerChgDtls) {
                    CbsSchemeInterestOrServiceChargesDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeInterestOrServiceChargesDetailsEntity(singleTO);
                    iscd.save(singleEntity);
                }
            }

            /***led form processing***/
            if (cbsSchLoanExpDtls != null) {
                for (CbsSchemeLoanExceptionDetailsTO singleTO : cbsSchLoanExpDtls) {
                    CbsSchemeLoanExceptionDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanExceptionDetailsEntity(singleTO);
                    led.save(singleEntity);
                }
            }

            /***lfdcw form processing***/
            if (ledgerFolioDtls != null) {
                CbsSchemeLedgerFolioDetailsCurrencyWiseTO orgToObj = new CbsSchemeLedgerFolioDetailsCurrencyWiseTO();
                CbsSchemeLedgerFolioDetailsCurrencyWisePKTO orgPkToObj = new CbsSchemeLedgerFolioDetailsCurrencyWisePKTO();

                for (LedgerFolioDetailsCurWiseTO singleCompTO : ledgerFolioDtls) {
                    String flag = singleCompTO.getSaveUpdateFlag();
                    if (flag.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setStartAmount(new BigDecimal(singleCompTO.getStartAmt()));
                        orgPkToObj.setEndAmount(new BigDecimal(singleCompTO.getEndAmt()));

                        orgToObj.setCbsSchemeLedgerFolioDetailsCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleCompTO.getSchemeType());
                        orgToObj.setCurrencyCode(singleCompTO.getCurrencyCode());
                        orgToObj.setFreeFolios(singleCompTO.getFreeFolios());
                        orgToObj.setDelFlag(singleCompTO.getDelflaf());

                        CbsSchemeLedgerFolioDetailsCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLedgerFolioDetailsCurrencyWiseEntity(orgToObj);
                        lfdcw.save(singleEntity);
                    }
                    if (flag.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setStartAmount(new BigDecimal(singleCompTO.getStartAmt()));
                        orgPkToObj.setEndAmount(new BigDecimal(singleCompTO.getEndAmt()));

                        orgToObj.setCbsSchemeLedgerFolioDetailsCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleCompTO.getSchemeType());
                        orgToObj.setCurrencyCode(singleCompTO.getCurrencyCode());
                        orgToObj.setFreeFolios(singleCompTO.getFreeFolios());
                        orgToObj.setDelFlag(singleCompTO.getDelflaf());

                        CbsSchemeLedgerFolioDetailsCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLedgerFolioDetailsCurrencyWiseEntity(orgToObj);
                        lfdcw.update(singleEntity);
                    }
                }
            }

            /***lrcd form processing***/
            if (loanRepaymentCyclDefs != null) {
                CbsSchemeLoanRepaymentCycleDefinitionTO orgToObj = new CbsSchemeLoanRepaymentCycleDefinitionTO();
                CbsSchemeLoanRepaymentCycleDefinitionPKTO orgPkToObj = new CbsSchemeLoanRepaymentCycleDefinitionPKTO();

                for (LoanRepaymentCycleDefinationTO singleCompTO : loanRepaymentCyclDefs) {
                    String flag = singleCompTO.getCounterSaveUpdate();
                    if (flag.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setAccountOpenFromDate(singleCompTO.getAcOpenFromDate());
                        orgToObj.setCbsSchemeLoanRepaymentCycleDefinitionPKTO(orgPkToObj);

                        orgToObj.setAccountOpenToDate(singleCompTO.getAcOpenToDate());
                        orgToObj.setCurrentOrNextMonthIndicator(singleCompTO.getMonthIndicator());
                        orgToObj.setRepaymentStartDate(singleCompTO.getRepaymentStartDate());
                        orgToObj.setDelFlag(singleCompTO.getDelFlag());

                        CbsSchemeLoanRepaymentCycleDefinition singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanRepaymentCycleDefinitionEntity(orgToObj);
                        lrcd.save(singleEntity);
                    }
                    if (flag.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setAccountOpenFromDate(singleCompTO.getAcOpenFromDate());
                        orgToObj.setCbsSchemeLoanRepaymentCycleDefinitionPKTO(orgPkToObj);

                        orgToObj.setAccountOpenToDate(singleCompTO.getAcOpenToDate());
                        orgToObj.setCurrentOrNextMonthIndicator(singleCompTO.getMonthIndicator());
                        orgToObj.setRepaymentStartDate(singleCompTO.getRepaymentStartDate());
                        orgToObj.setDelFlag(singleCompTO.getDelFlag());

                        CbsSchemeLoanRepaymentCycleDefinition singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanRepaymentCycleDefinitionEntity(orgToObj);
                        lrcd.update(singleEntity);
                    }
                }
            }

            /***svi form processing***/
            if (schValidComplexTO != null) {
                CbsSchemeValidInstrumentsTO orgToObj = new CbsSchemeValidInstrumentsTO();
                CbsSchemeValidInstrumentsPKTO orgPkToObj = new CbsSchemeValidInstrumentsPKTO();

                for (SchemeValidInstrumentTO singleComTO : schValidComplexTO) {
                    String saveUpdateFlag = singleComTO.getSaveUpdateFlag();
                    if (saveUpdateFlag.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setInstrumentCode(singleComTO.getInstrumentCode());

                        orgToObj.setCbsSchemeValidInstrumentsPKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setCrDrIndFlag(singleComTO.getCrDrIndFlag());
                        orgToObj.setDelFlag(singleComTO.getDelFlag());

                        CbsSchemeValidInstruments singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeValidInstrumentsEntity(orgToObj);
                        svi.save(singleEntity);
                    }
                    if (saveUpdateFlag.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setInstrumentCode(singleComTO.getInstrumentCode());

                        orgToObj.setCbsSchemeValidInstrumentsPKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setCrDrIndFlag(singleComTO.getCrDrIndFlag());
                        orgToObj.setDelFlag(singleComTO.getDelFlag());

                        CbsSchemeValidInstruments singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeValidInstrumentsEntity(orgToObj);
                        svi.update(singleEntity);
                    }
                }
            }
            /***Added by Ankit***/
            /***AD form processing***/
            if (loanAssetTO != null) {
                CbsSchemeAssetDetailsTO orgToObj = new CbsSchemeAssetDetailsTO();
                CbsSchemeAssetDetailsPKTO orgPkToObj = new CbsSchemeAssetDetailsPKTO();
                for (LoanAssetTO singleComTO : loanAssetTO) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDpdCounter(singleComTO.getTbdPDCounter());
                        orgToObj.setCbsSchemeAssetDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getTbdelFlag());
                        orgToObj.setIntAccre(singleComTO.getTbIntAccre());
                        orgToObj.setIntFlagBk(singleComTO.getTbIntFlagBk());
                        orgToObj.setIntFlagColl(singleComTO.getTbIntFlagColl());
                        orgToObj.setIntSuspPlaceholder(singleComTO.getTbIntSuspPlaceHolder());
                        orgToObj.setMainClass(singleComTO.getTbMainClass());
                        orgToObj.setPdFlag(singleComTO.getTbPDFlag());
                        orgToObj.setPlaceholdersCr(singleComTO.getTbPlaceHoldersCr());
                        orgToObj.setProvisionDr(singleComTO.getTbprovisionDR());
                        orgToObj.setSubClass(singleComTO.getTbSubClass());
                        CbsSchemeAssetDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeAssetDetailsEntity(orgToObj);
                        adDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDpdCounter(singleComTO.getTbdPDCounter());
                        orgToObj.setCbsSchemeAssetDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getTbdelFlag());
                        orgToObj.setIntAccre(singleComTO.getTbIntAccre());
                        orgToObj.setIntFlagBk(singleComTO.getTbIntFlagBk());
                        orgToObj.setIntFlagColl(singleComTO.getTbIntFlagColl());
                        orgToObj.setIntSuspPlaceholder(singleComTO.getTbIntSuspPlaceHolder());
                        orgToObj.setMainClass(singleComTO.getTbMainClass());
                        orgToObj.setPdFlag(singleComTO.getTbPDFlag());
                        orgToObj.setPlaceholdersCr(singleComTO.getTbPlaceHoldersCr());
                        orgToObj.setProvisionDr(singleComTO.getTbprovisionDR());
                        orgToObj.setSubClass(singleComTO.getTbSubClass());
                        CbsSchemeAssetDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeAssetDetailsEntity(orgToObj);
                        adDao.update(singleEntity);
                    }
                }
            }
            /***DIDD form processing***/
            if (depositIntTO != null) {
                CbsSchemeDepositInterestDefinitionDetailsTO orgToObj = new CbsSchemeDepositInterestDefinitionDetailsTO();
                CbsSchemeDepositInterestDefinitionDetailsPKTO orgPkToObj = new CbsSchemeDepositInterestDefinitionDetailsPKTO();
                for (DepositIntTO singleComTO : depositIntTO) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdateDeposit();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setInterestMethod(singleComTO.getTblInterestMethod());
                        orgToObj.setCbsSchemeDepositInterestDefinitionDetailsPKTO(orgPkToObj);
                        orgToObj.setBaseAmountInd(singleComTO.getTblBaseAmtInd());
                        orgToObj.setBrokenPeriodBase(singleComTO.getTblBrokenPeriodBase());
                        orgToObj.setBrokenPeriodMthd(singleComTO.getTblBrokenPeriodMethod());
                        orgToObj.setCompoundingBase(singleComTO.getTblCompoundingBase());
                        orgToObj.setCompoundingPeriod(singleComTO.getTblCompoundingPeriod());
                        orgToObj.setDelFlag(singleComTO.getTblDeleteFlag());
                        orgToObj.setMaxDepositPeriodDays(singleComTO.getTblMaxDepositPeriodDays());
                        orgToObj.setMaxDepositPeriodMonths(singleComTO.getTblMaxDepositPeriodMonths());
                        orgToObj.setMinCompoundingBase(singleComTO.getTblMinCompoundingBase());
                        orgToObj.setMinCompoundingPeriod(singleComTO.getTblMinCompoundingPeriod());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        CbsSchemeDepositInterestDefinitionDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositInterestDefinitionDetailsEntity(orgToObj);
                        diddDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setInterestMethod(singleComTO.getTblInterestMethod());
                        orgToObj.setCbsSchemeDepositInterestDefinitionDetailsPKTO(orgPkToObj);
                        orgToObj.setBaseAmountInd(singleComTO.getTblBaseAmtInd());
                        orgToObj.setBrokenPeriodBase(singleComTO.getTblBrokenPeriodBase());
                        orgToObj.setBrokenPeriodMthd(singleComTO.getTblBrokenPeriodMethod());
                        orgToObj.setCompoundingBase(singleComTO.getTblCompoundingBase());
                        orgToObj.setCompoundingPeriod(singleComTO.getTblCompoundingPeriod());
                        orgToObj.setDelFlag(singleComTO.getTblDeleteFlag());
                        orgToObj.setMaxDepositPeriodDays(singleComTO.getTblMaxDepositPeriodDays());
                        orgToObj.setMaxDepositPeriodMonths(singleComTO.getTblMaxDepositPeriodMonths());
                        orgToObj.setMinCompoundingBase(singleComTO.getTblMinCompoundingBase());
                        orgToObj.setMinCompoundingPeriod(singleComTO.getTblMinCompoundingPeriod());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        CbsSchemeDepositInterestDefinitionDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositInterestDefinitionDetailsEntity(orgToObj);
                        diddDao.update(singleEntity);
                    }
                }
            }
            /***DFD form processing***/
            if (depositFlowTO != null) {
                CbsSchemeDepositFlowDetailsTO orgToObj = new CbsSchemeDepositFlowDetailsTO();
                CbsSchemeDepositFlowDetailsPKTO orgPkToObj = new CbsSchemeDepositFlowDetailsPKTO();
                for (DepositFlowTO singleComTO : depositFlowTO) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdateFlow();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setFlowCode(singleComTO.getTblFlowCode());
                        orgPkToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setCbsSchemeDepositFlowDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getTblDelFlagFlow());
                        orgToObj.setFlowFreqDays(singleComTO.getTblFlowFreqDays());
                        orgToObj.setFlowFreqMonths(singleComTO.getTblFlowFreqMonths());
                        orgToObj.setFlowPeriodBegin(singleComTO.getTblFlowPeriodBegin());
                        orgToObj.setFlowPeriodEnd(singleComTO.getTblFlowPeriodEnd());
                        CbsSchemeDepositFlowDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositFlowDetailsEntity(orgToObj);
                        dfdDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setFlowCode(singleComTO.getTblFlowCode());
                        orgPkToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setCbsSchemeDepositFlowDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getTblDelFlagFlow());
                        orgToObj.setFlowFreqDays(singleComTO.getTblFlowFreqDays());
                        orgToObj.setFlowFreqMonths(singleComTO.getTblFlowFreqMonths());
                        orgToObj.setFlowPeriodBegin(singleComTO.getTblFlowPeriodBegin());
                        orgToObj.setFlowPeriodEnd(singleComTO.getTblFlowPeriodEnd());
                        CbsSchemeDepositFlowDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositFlowDetailsEntity(orgToObj);
                        dfdDao.update(singleEntity);
                    }
                }
            }
            /***CSDD form processing***/
            if (schemeDocumentdetails != null) {
                CbsSchemeDocumentDetailsTO orgToObj = new CbsSchemeDocumentDetailsTO();
                CbsSchemeDocumentDetailsPKTO orgPkToObj = new CbsSchemeDocumentDetailsPKTO();
                for (SchemeDocumentDetailsTO singleComTO : schemeDocumentdetails) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDocumentCode(singleComTO.getDocumentCode());
                        orgToObj.setCbsSchemeDocumentDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getDelFlagDocDetail());
                        orgToObj.setDocumentDescription(singleComTO.getDocumentDesc());
                        orgToObj.setMandatoryDocument(singleComTO.getMandatoryDoc());
                        CbsSchemeDocumentDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeDocumentDetailsEntity(orgToObj);
                        csddDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDocumentCode(singleComTO.getDocumentCode());
                        orgToObj.setCbsSchemeDocumentDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getDelFlagDocDetail());
                        orgToObj.setDocumentDescription(singleComTO.getDocumentDesc());
                        orgToObj.setMandatoryDocument(singleComTO.getMandatoryDoc());
                        CbsSchemeDocumentDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeDocumentDetailsEntity(orgToObj);
                        csddDao.update(singleEntity);
                    }
                }
            }
            msg = "true";
        } catch (DAOException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getGSHSDDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getGSHSDDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getGSHSDDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return msg;
    }

    public String updateSchemeMaster(List<CbsSchemeCustAccountDetailsTO> cbsSchemeCustAccountDetailsTOs, List<CbsSchemeAccountOpenMatrixTO> cbsSchemeAccountOpenMatrixTOs, List<CbsSchemeCurrencyDetailsTO> cbsSchemeCurrencyDetailsTOs, List<CbsSchemeCashCrBillsAndOverdraftDetailsTO> cbsSchemeCashCrBillsAndOverdraftDetailsTOs, List<DoipComplexTO> depositOverDueIntParameter, List<GlSubHeadSchemeTO> glSubHeadSchemeTOs, List<CbsSchemeDepositsSchemeParametersMaintananceTO> cbsSchDepSchParaMainTOs, List<CbsSchemeInterestOrServiceChargesDetailsTO> cbsSchIntSerChgDtls, List<CbsSchemeLoanExceptionDetailsTO> cbsSchLoanExpDtls, List<SchemeValidInstrumentTO> schValidComplexTO, List<LedgerFolioDetailsCurWiseTO> ledgerFolioDtls, List<LoanRepaymentCycleDefinationTO> loanRepaymentCyclDefs, List<FeeOrChargesDetailsTO> feeOrChargesDtls, List<TransactionReportCodeTO> transRepCode, List<SchemeTODRefdetailsTO> schemeTodRefdetails, List<TodExceptionDetailsTO> todExceptionDetails, List<SchemeDocumentDetailsTO> schemeDocumentdetails, List<DepositFlowTO> depositFlowTO, List<LoanAssetTO> loanAssetTO, List<DepositIntTO> depositIntTO, List<CbsSchemeFlexiFixedDepositsDetailsTO> cbsSchemeFlexiFixedDepositsDetailsTOs, List<CbsSchemeLoanPrepaymentDetailsTO> cbsSchemeLoanPrepaymentDetailsTOs, List<CbsSchemeLoanSchemeDetailsTO> cbsSchemeLoanSchemeDetailsTOs, List<CbsSchemeCaSbSchDetailsTO> cbsSchemeCaSbSchDetailsTOs, List<DeliquencyTableTO> deliqDetailsTOs, List<CbsSchemeLoanInterestDetailsTO> cbsSchemeLoanInterestDetailsTOs, List<CbsSchemeLoanPreEiSetupDetailsTO> cbsSchemeLoanPreEiSetupDetailsTOs, List<CbsSchemeGeneralSchemeParameterMasterTO> cbsSchemeGeneralSchemeParameterMasterTOs) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        CbsSchemeFlexiFixedDepositsDetailsDAO ffddDaoObj = new CbsSchemeFlexiFixedDepositsDetailsDAO(entityManager);
        CbsSchemeDelinquencyDetailsDAO ddDao = new CbsSchemeDelinquencyDetailsDAO(entityManager);
        CbsSchemeLoanPrepaymentDetailsDAO lpdDao = new CbsSchemeLoanPrepaymentDetailsDAO(entityManager);
        CbsSchemeLoanSchemeDetailsDAO lsdDao = new CbsSchemeLoanSchemeDetailsDAO(entityManager);
        CbsSchemeCaSbSchDetailsDAO spmDao = new CbsSchemeCaSbSchDetailsDAO(entityManager);
        CbsSchemeLoanInterestDetailsDAO lidDao = new CbsSchemeLoanInterestDetailsDAO(entityManager);
        CbsSchemeLoanPreEiSetupDetailsDAO lpesdDao = new CbsSchemeLoanPreEiSetupDetailsDAO(entityManager);
        CbsSchemeGeneralSchemeParameterMasterDAO pmDao = new CbsSchemeGeneralSchemeParameterMasterDAO(entityManager);
        CbsSchemeTodExceptionDetailsCurrencyWiseDAO todDao = new CbsSchemeTodExceptionDetailsCurrencyWiseDAO(entityManager);
        CbsSchemeTodReferenceDetailsDAO strdDao = new CbsSchemeTodReferenceDetailsDAO(entityManager);
        CbsSchemeTransactionReportCodeCurrencyWiseDAO trccwDao = new CbsSchemeTransactionReportCodeCurrencyWiseDAO(entityManager);
        CbsSchemeFeeOrChargesDetailsDAO sfcdDao = new CbsSchemeFeeOrChargesDetailsDAO(entityManager);
        CbsSchemeAssetDetailsDAO adDao = new CbsSchemeAssetDetailsDAO(entityManager);
        CbsSchemeDepositInterestDefinitionDetailsDAO diddDao = new CbsSchemeDepositInterestDefinitionDetailsDAO(entityManager);
        CbsSchemeDepositFlowDetailsDAO dfdDao = new CbsSchemeDepositFlowDetailsDAO(entityManager);
        CbsSchemeDocumentDetailsDAO csddDao = new CbsSchemeDocumentDetailsDAO(entityManager);
        /****Added by dinesh****/
        CbsSchemeCustAccountDetailsDAO cad = new CbsSchemeCustAccountDetailsDAO(entityManager);
        CbsSchemeAccountOpenMatrixDAO aom = new CbsSchemeAccountOpenMatrixDAO(entityManager);
        CbsSchemeCurrencyDetailsDAO cd = new CbsSchemeCurrencyDetailsDAO(entityManager);
        CbsSchemeCashCrBillsAndOverdraftDetailsDAO crbosd = new CbsSchemeCashCrBillsAndOverdraftDetailsDAO(entityManager);
        CbsSchemeExceptionCodeForDepositsSchemeDAO doipExp = new CbsSchemeExceptionCodeForDepositsSchemeDAO(entityManager);
        CbsSchemeDepositOverdueInterestParametersDAO doipParam = new CbsSchemeDepositOverdueInterestParametersDAO(entityManager);
        CbsGlSubHeadSchemeDetailsDAO gshsd = new CbsGlSubHeadSchemeDetailsDAO(entityManager);
        CbsSchemeDepositsSchemeParametersMaintananceDAO dspm = new CbsSchemeDepositsSchemeParametersMaintananceDAO(entityManager);
        CbsSchemeInterestOrServiceChargesDetailsDAO iscd = new CbsSchemeInterestOrServiceChargesDetailsDAO(entityManager);
        CbsSchemeLoanExceptionDetailsDAO led = new CbsSchemeLoanExceptionDetailsDAO(entityManager);
        CbsSchemeLedgerFolioDetailsCurrencyWiseDAO lfdcw = new CbsSchemeLedgerFolioDetailsCurrencyWiseDAO(entityManager);
        CbsSchemeLoanRepaymentCycleDefinitionDAO lrcd = new CbsSchemeLoanRepaymentCycleDefinitionDAO(entityManager);
        CbsSchemeFeeOrChargesDetailsDAO sfcd = new CbsSchemeFeeOrChargesDetailsDAO(entityManager);
        CbsSchemeValidInstrumentsDAO svi = new CbsSchemeValidInstrumentsDAO(entityManager);
        try {
            /***ffdd form processing***/
            if (cbsSchemeFlexiFixedDepositsDetailsTOs != null) {
                for (CbsSchemeFlexiFixedDepositsDetailsTO singleTO : cbsSchemeFlexiFixedDepositsDetailsTOs) {
                    CbsSchemeFlexiFixedDepositsDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeFlexiFixedDepositsDetailsEntity(singleTO);
                    ffddDaoObj.update(singleEntity);
                }
            }
            /***dd form processing***/
            if (deliqDetailsTOs != null) {
                CbsSchemeDelinquencyDetailsTO orgToObj = new CbsSchemeDelinquencyDetailsTO();
                CbsSchemeDelinquencyDetailsPKTO orgPkToObj = new CbsSchemeDelinquencyDetailsPKTO();

                for (DeliquencyTableTO singleComTO : deliqDetailsTOs) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDelinqCycle(singleComTO.getDeliqCycle());
                        orgToObj.setCbsSchemeDelinquencyDetailsPKTO(orgPkToObj);
                        orgToObj.setDaysPastDue(singleComTO.getDaysPastDue());
                        orgToObj.setPlaceHolder(singleComTO.getPlaceHolder());
                        orgToObj.setProvisionPercent(singleComTO.getProvisionPercent());
                        orgToObj.setDelFlag(singleComTO.getDelFlagDeliq());

                        CbsSchemeDelinquencyDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDelInquencyDetailsEntity(orgToObj);
                        ddDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDelinqCycle(singleComTO.getDeliqCycle());
                        orgToObj.setCbsSchemeDelinquencyDetailsPKTO(orgPkToObj);
                        orgToObj.setDaysPastDue(singleComTO.getDaysPastDue());
                        orgToObj.setPlaceHolder(singleComTO.getPlaceHolder());
                        orgToObj.setProvisionPercent(singleComTO.getProvisionPercent());
                        orgToObj.setDelFlag(singleComTO.getDelFlagDeliq());
                        CbsSchemeDelinquencyDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDelInquencyDetailsEntity(orgToObj);
                        ddDao.update(singleEntity);
                    }
                }
            }
            /***lpd form processing***/
            if (cbsSchemeLoanPrepaymentDetailsTOs != null) {
                for (CbsSchemeLoanPrepaymentDetailsTO singleTO : cbsSchemeLoanPrepaymentDetailsTOs) {
                    CbsSchemeLoanPrepaymentDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanPrepaymentDetailsEntity(singleTO);
                    lpdDao.update(singleEntity);
                }
            }
            /***lsd form processing***/
            if (cbsSchemeLoanSchemeDetailsTOs != null) {
                for (CbsSchemeLoanSchemeDetailsTO singleTO : cbsSchemeLoanSchemeDetailsTOs) {
                    CbsSchemeLoanSchemeDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanSchemeDetailsEntity(singleTO);
                    lsdDao.update(singleEntity);
                }
            }
            /***spm form processing***/
            if (cbsSchemeCaSbSchDetailsTOs != null) {
                for (CbsSchemeCaSbSchDetailsTO singleTO : cbsSchemeCaSbSchDetailsTOs) {
                    CbsSchemeCaSbSchDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeCASBSchDetailsEntity(singleTO);
                    spmDao.update(singleEntity);
                }
            }
            /***lid form processing***/
            if (cbsSchemeLoanInterestDetailsTOs != null) {
                for (CbsSchemeLoanInterestDetailsTO singleTO : cbsSchemeLoanInterestDetailsTOs) {
                    CbsSchemeLoanInterestDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanInterestDetailsEntity(singleTO);
                    lidDao.update(singleEntity);
                }
            }
            /***lpesd form processing***/
            if (cbsSchemeLoanPreEiSetupDetailsTOs != null) {
                for (CbsSchemeLoanPreEiSetupDetailsTO singleTO : cbsSchemeLoanPreEiSetupDetailsTOs) {
                    CbsSchemeLoanPreEiSetupDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanPreEiSetupDetailsEntity(singleTO);
                    lpesdDao.update(singleEntity);
                }
            }
            /***pm form processing***/
            if (cbsSchemeGeneralSchemeParameterMasterTOs != null) {
                for (CbsSchemeGeneralSchemeParameterMasterTO singleTO : cbsSchemeGeneralSchemeParameterMasterTOs) {
                    CbsSchemeGeneralSchemeParameterMaster singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeGeneralSchemeParameterMasterEntity(singleTO);
                    pmDao.update(singleEntity);
                }
            }
            /***TEDCW form processing***/
            if (todExceptionDetails != null) {
                CbsSchemeTodExceptionDetailsCurrencyWiseTO orgToObj = new CbsSchemeTodExceptionDetailsCurrencyWiseTO();
                CbsSchemeTodExceptionDetailsCurrencyWisePKTO orgPkToObj = new CbsSchemeTodExceptionDetailsCurrencyWisePKTO();
                for (TodExceptionDetailsTO singleComTO : todExceptionDetails) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        Object obj = (Object) todDao.getTodSerialNo();
                        if (obj==null||obj.toString() == null || obj.toString().equalsIgnoreCase("")) {
                            orgPkToObj.setTodSrlNo("1");
                        } else {
                            Integer sn = Integer.parseInt(obj.toString());
                            sn = sn + 1;
                            orgPkToObj.setTodSrlNo(sn.toString());
                        }
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setCurrencyCode(singleComTO.getCurrencyCode());

                        orgToObj.setCbsSchemeTodExceptionDetailsCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setBeginAmount(singleComTO.getBeginAmount());
                        orgToObj.setEndAmount(singleComTO.getEndAmount());
                        orgToObj.setTodException(singleComTO.getTodException());
                        orgToObj.setDelFlag(singleComTO.getDelFlag());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        CbsSchemeTodExceptionDetailsCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTodExceptionDetailsCurrencyWiseEntity(orgToObj);
                        todDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setCurrencyCode(singleComTO.getCurrencyCode());
                        orgPkToObj.setTodSrlNo(singleComTO.getTodSrlNo());
                        orgToObj.setCbsSchemeTodExceptionDetailsCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setBeginAmount(singleComTO.getBeginAmount());
                        orgToObj.setEndAmount(singleComTO.getEndAmount());
                        orgToObj.setTodException(singleComTO.getTodException());
                        orgToObj.setDelFlag(singleComTO.getDelFlag());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        CbsSchemeTodExceptionDetailsCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTodExceptionDetailsCurrencyWiseEntity(orgToObj);
                        todDao.update(singleEntity);
                    }
                }
            }
            /***STRD form processing***/
            if (schemeTodRefdetails != null) {
                CbsSchemeTodReferenceDetailsTO orgToObj = new CbsSchemeTodReferenceDetailsTO();
                CbsSchemeTodReferenceDetailsPKTO orgPkToObj = new CbsSchemeTodReferenceDetailsPKTO();
                for (SchemeTODRefdetailsTO singleComTO : schemeTodRefdetails) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setReferenceType(singleComTO.getReferenceType());
                        orgToObj.setCbsSchemeTodReferenceDetailsPKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setDelFlag(singleComTO.getDelFlagTodRef());
                        orgToObj.setDiscretAdvnType(singleComTO.getDiscretAdvnType());
                        orgToObj.setDiscretAdvnCategory(singleComTO.getDiscretAdvnCategory());
                        orgToObj.setDiscretNumberOfDays(singleComTO.getDiscretNumberOfDays());
                        orgToObj.setPenalDays(singleComTO.getPenalDays());
                        orgToObj.setIntFlag(singleComTO.getIntFlag());
                        orgToObj.setFreeTextCode(singleComTO.getFreeTxtCode());
                        orgToObj.setInterestTableCode(singleComTO.getInterestTableCode());
                        orgToObj.setToLevelIntTblCode(singleComTO.getToLevelIntTblCode());
                        CbsSchemeTodReferenceDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTodReferenceDetailsEntity(orgToObj);
                        strdDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setReferenceType(singleComTO.getReferenceType());
                        orgToObj.setCbsSchemeTodReferenceDetailsPKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setDelFlag(singleComTO.getDelFlagTodRef());
                        orgToObj.setDiscretAdvnType(singleComTO.getDiscretAdvnType());
                        orgToObj.setDiscretAdvnCategory(singleComTO.getDiscretAdvnCategory());
                        orgToObj.setDiscretNumberOfDays(singleComTO.getDiscretNumberOfDays());
                        orgToObj.setPenalDays(singleComTO.getPenalDays());
                        orgToObj.setIntFlag(singleComTO.getIntFlag());
                        orgToObj.setFreeTextCode(singleComTO.getFreeTxtCode());
                        orgToObj.setInterestTableCode(singleComTO.getInterestTableCode());
                        orgToObj.setToLevelIntTblCode(singleComTO.getToLevelIntTblCode());
                        CbsSchemeTodReferenceDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTodReferenceDetailsEntity(orgToObj);
                        strdDao.update(singleEntity);
                    }
                }
            }
            /***TRCCW form processing***/
            if (transRepCode != null) {
                CbsSchemeTransactionReportCodeCurrencyWiseTO orgToObj = new CbsSchemeTransactionReportCodeCurrencyWiseTO();
                CbsSchemeTransactionReportCodeCurrencyWisePKTO orgPkToObj = new CbsSchemeTransactionReportCodeCurrencyWisePKTO();
                for (TransactionReportCodeTO singleComTO : transRepCode) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setTransactionReportCode(singleComTO.getTransactionReportCode());
                        orgToObj.setCbsSchemeTransactionReportCodeCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setCrAmtLimit(new BigDecimal(singleComTO.getCrAmt()));
                        orgToObj.setDrAmtLimit(new BigDecimal(singleComTO.getDrAmt()));
                        orgToObj.setCurrencyCode(singleComTO.getCurrencyCodeTranRep());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setDelFlag(singleComTO.getDeleteTranCode());
                        CbsSchemeTransactionReportCodeCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTransactionReportCodeCurrencyWiseEntity(orgToObj);
                        trccwDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setTransactionReportCode(singleComTO.getTransactionReportCode());
                        orgToObj.setCbsSchemeTransactionReportCodeCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setCrAmtLimit(new BigDecimal(singleComTO.getCrAmt()));
                        orgToObj.setDrAmtLimit(new BigDecimal(singleComTO.getDrAmt()));
                        orgToObj.setCurrencyCode(singleComTO.getCurrencyCodeTranRep());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setDelFlag(singleComTO.getDeleteTranCode());
                        CbsSchemeTransactionReportCodeCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeTransactionReportCodeCurrencyWiseEntity(orgToObj);
                        trccwDao.update(singleEntity);
                    }
                }
            }
            /***SFCD form processing***/
            if (feeOrChargesDtls != null) {
                CbsSchemeFeeOrChargesDetailsTO orgToObj = new CbsSchemeFeeOrChargesDetailsTO();
                CbsSchemeFeeOrChargesDetailsPKTO orgPkToObj = new CbsSchemeFeeOrChargesDetailsPKTO();
                for (FeeOrChargesDetailsTO singleComTO : feeOrChargesDtls) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setChargeType(singleComTO.getChargesType());
                        orgToObj.setCbsSchemeFeeOrChargesDetailsPKTO(orgPkToObj);
                        orgToObj.setAmortMethod(singleComTO.getAmortMethod());
                        orgToObj.setAmortize(singleComTO.getAmortize());
                        orgToObj.setCurrencyCode(singleComTO.getCurrencyCode());
                        orgToObj.setAssessOrDmd(singleComTO.getAssertOrDmd());
                        orgToObj.setChargeDescription(singleComTO.getChargesDesc());
                        orgToObj.setChargeEventId(singleComTO.getChargesEventId());
                        orgToObj.setConsiderForIrr(singleComTO.getConsiderForIrr());
                        orgToObj.setCrPlaceholder(singleComTO.getCrPlaceHolder());
                        orgToObj.setDeductible(singleComTO.getDeductible());
                        orgToObj.setDelFlag(singleComTO.getDelFlagFeeCharges());
                        orgToObj.setDrPlaceholder(singleComTO.getDrPlaceHolder());
                        orgToObj.setMultipleFlag(singleComTO.getMultipleFlag());
                        orgToObj.setPrepaymentFee(new BigDecimal(singleComTO.getPrePaymentFee()));
                        CbsSchemeFeeOrChargesDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeFeeOrChargesDetailsEntity(orgToObj);
                        sfcdDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setChargeType(singleComTO.getChargesType());
                        orgToObj.setCbsSchemeFeeOrChargesDetailsPKTO(orgPkToObj);
                        orgToObj.setAmortMethod(singleComTO.getAmortMethod());
                        orgToObj.setAmortize(singleComTO.getAmortize());
                        orgToObj.setCurrencyCode(singleComTO.getCurrencyCode());
                        orgToObj.setAssessOrDmd(singleComTO.getAssertOrDmd());
                        orgToObj.setChargeDescription(singleComTO.getChargesDesc());
                        orgToObj.setChargeEventId(singleComTO.getChargesEventId());
                        orgToObj.setConsiderForIrr(singleComTO.getConsiderForIrr());
                        orgToObj.setCrPlaceholder(singleComTO.getCrPlaceHolder());
                        orgToObj.setDeductible(singleComTO.getDeductible());
                        orgToObj.setDelFlag(singleComTO.getDelFlagFeeCharges());
                        orgToObj.setDrPlaceholder(singleComTO.getDrPlaceHolder());
                        orgToObj.setMultipleFlag(singleComTO.getMultipleFlag());
                        orgToObj.setPrepaymentFee(new BigDecimal(singleComTO.getPrePaymentFee()));
                        CbsSchemeFeeOrChargesDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeFeeOrChargesDetailsEntity(orgToObj);
                        sfcdDao.update(singleEntity);
                    }
                }
            }
            /***Added by dinesh****/
            /***cad form processing***/
            if (cbsSchemeCustAccountDetailsTOs != null) {
                for (CbsSchemeCustAccountDetailsTO singleTO : cbsSchemeCustAccountDetailsTOs) {
                    CbsSchemeCustAccountDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeCustAccountDetailsEntity(singleTO);
                    cad.update(singleEntity);
                }
            }

            /***aom form processing***/
            if (cbsSchemeAccountOpenMatrixTOs != null) {
                for (CbsSchemeAccountOpenMatrixTO singleTO : cbsSchemeAccountOpenMatrixTOs) {
                    CbsSchemeAccountOpenMatrix singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeAccountOpenMatrixEntity(singleTO);
                    aom.update(singleEntity);
                }
            }

            /***cd form processing***/
            if (cbsSchemeCurrencyDetailsTOs != null) {
                for (CbsSchemeCurrencyDetailsTO singleTO : cbsSchemeCurrencyDetailsTOs) {
                    CbsSchemeCurrencyDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeCurrencyDetailsEntity(singleTO);
                    cd.update(singleEntity);
                }
            }

            /***crbosd form processing***/
            if (cbsSchemeCashCrBillsAndOverdraftDetailsTOs != null) {
                for (CbsSchemeCashCrBillsAndOverdraftDetailsTO singleTO : cbsSchemeCashCrBillsAndOverdraftDetailsTOs) {
                    CbsSchemeCashCrBillsAndOverdraftDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeCashCrBillsAndOverdraftDetailsEntity(singleTO);
                    crbosd.update(singleEntity);
                }
            }

            /***doip form processing***/
            if (depositOverDueIntParameter != null) {
                CbsSchemeExceptionCodeForDepositsSchemeTO objExp = new CbsSchemeExceptionCodeForDepositsSchemeTO();
                CbsSchemeDepositOverdueInterestParametersTO objParam = new CbsSchemeDepositOverdueInterestParametersTO();
                for (DoipComplexTO singleTO : depositOverDueIntParameter) {
                    objExp.setSchemeCode(singleTO.getSchemeCode());
                    objExp.setSchemeType(singleTO.getSchemeType());
                    objExp.setCurrencyCode(singleTO.getCurrencyCode());
                    objExp.setRenewalPeriodExcd(singleTO.getRenewalPerdExcd());

                    objExp.setMaximumPeriod(singleTO.getMaxPerd());
                    objExp.setMaximumAmount(singleTO.getMaxAmt());
                    objExp.setMinorDepPreclosure(singleTO.getMinorDepPreclosure());
                    objExp.setExtension(singleTO.getExtension());

                    objExp.setSplCatgClosure(singleTO.getSplCatgClosure());
                    objExp.setMatAmtTolerance(singleTO.getMatAmtTolerance());
                    objExp.setNilPenalty(singleTO.getNilPenalty());
                    objExp.setDiscontinuedInstl(singleTO.getDisContinuedInst());

                    objExp.setTransferIn(singleTO.getTransferIn());
                    objExp.setAcctVerBalCheck(singleTO.getAcctVerBalCheck());
                    objExp.setSystemDrTransAllwd(singleTO.getSystemDrTransAllowed());
                    objExp.setDuplicateOrReprintReport(singleTO.getDupReprntRcpt());

                    objExp.setPrematureClosure(singleTO.getPreMatureClosure());
                    objExp.setNoticePerdBelowMinNoticePerd(singleTO.getNoticePerdMinNoticePerd());
                    objExp.setDefaultValueForPreferentialIntChgd(singleTO.getDefaultValueForPreIntChgd());
                    objExp.setBackValueDatedAccountOpening(singleTO.getBackValueDateAccOpen());
                    objExp.setFutureValueDatedAccountOpening(singleTO.getFutureValueDateAccOpen());

                    CbsSchemeExceptionCodeForDepositsScheme expEntity = ObjectAdaptorLoan.adaptToCbsSchemeExceptionCodeForDepositsSchemeEntity(objExp);
                    doipExp.update(expEntity);

                    objParam.setSchemeCode(singleTO.getSchemeCode());
                    objParam.setSchemeType(singleTO.getSchemeType());
                    objParam.setCurrencyCode(singleTO.getCurrencyCode());
                    objParam.setOverdueGlSubHeadCode(singleTO.getOverDueGlSubHeadCode());
                    objParam.setOverdueInterestCode(singleTO.getOverDueInterestCode());
                    objParam.setOverdueInterestTblCodeType(singleTO.getOverDueIntTblCodeType());
                    objParam.setOverdueInterestCalcMethod(singleTO.getOverDueintCalcMethod());

                    CbsSchemeDepositOverdueInterestParameters paramEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositOverdueInterestParametersEntity(objParam);
                    doipParam.update(paramEntity);
                }
            }

            /***gshsd form processing***/
            if (glSubHeadSchemeTOs != null) {
                CbsGlSubHeadSchemeDetailsTO orgToObj = new CbsGlSubHeadSchemeDetailsTO();
                CbsGlSubHeadSchemeDetailsPKTO orgPkToObj = new CbsGlSubHeadSchemeDetailsPKTO();

                for (GlSubHeadSchemeTO singleCompTO : glSubHeadSchemeTOs) {
                    String flag = singleCompTO.getSaveUpdateCounter();
                    if (flag.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setGlSubHeadCode(singleCompTO.getGlSubHeadCode());
                        orgToObj.setCbsGlSubHeadSchemeDetailsPKTO(orgPkToObj);

                        orgToObj.setSchemeType(singleCompTO.getSchemeType());
                        orgToObj.setDfltFlag(singleCompTO.getDefaultFlag());
                        orgToObj.setDelFlag(singleCompTO.getDelFlag());

                        CbsGlSubHeadSchemeDetails singleEntity = ObjectAdaptorMaster.adaptToCbsGlSubHeadSchDtlsEntity(orgToObj);
                        gshsd.save(singleEntity);
                    }
                    if (flag.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setGlSubHeadCode(singleCompTO.getGlSubHeadCode());
                        orgToObj.setCbsGlSubHeadSchemeDetailsPKTO(orgPkToObj);

                        orgToObj.setSchemeType(singleCompTO.getSchemeType());
                        orgToObj.setDfltFlag(singleCompTO.getDefaultFlag());
                        orgToObj.setDelFlag(singleCompTO.getDelFlag());

                        CbsGlSubHeadSchemeDetails singleEntity = ObjectAdaptorMaster.adaptToCbsGlSubHeadSchDtlsEntity(orgToObj);
                        gshsd.update(singleEntity);
                    }
                }
            }

            /***dspm form processing***/
            if (cbsSchDepSchParaMainTOs != null) {
                for (CbsSchemeDepositsSchemeParametersMaintananceTO singleTO : cbsSchDepSchParaMainTOs) {
                    CbsSchemeDepositsSchemeParametersMaintanance singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositsSchemeParametersMaintananceEntity(singleTO);
                    dspm.update(singleEntity);
                }
            }

            /***iscd form processing***/
            if (cbsSchIntSerChgDtls != null) {
                for (CbsSchemeInterestOrServiceChargesDetailsTO singleTO : cbsSchIntSerChgDtls) {
                    CbsSchemeInterestOrServiceChargesDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeInterestOrServiceChargesDetailsEntity(singleTO);
                    iscd.update(singleEntity);
                }
            }

            /***led form processing***/
            if (cbsSchLoanExpDtls != null) {
                for (CbsSchemeLoanExceptionDetailsTO singleTO : cbsSchLoanExpDtls) {
                    CbsSchemeLoanExceptionDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanExceptionDetailsEntity(singleTO);
                    led.update(singleEntity);
                }
            }

            /***lfdcw form processing***/
            if (ledgerFolioDtls != null) {
                CbsSchemeLedgerFolioDetailsCurrencyWiseTO orgToObj = new CbsSchemeLedgerFolioDetailsCurrencyWiseTO();
                CbsSchemeLedgerFolioDetailsCurrencyWisePKTO orgPkToObj = new CbsSchemeLedgerFolioDetailsCurrencyWisePKTO();

                for (LedgerFolioDetailsCurWiseTO singleCompTO : ledgerFolioDtls) {
                    String flag = singleCompTO.getSaveUpdateFlag();
                    if (flag.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setStartAmount(new BigDecimal(singleCompTO.getStartAmt()));
                        orgPkToObj.setEndAmount(new BigDecimal(singleCompTO.getEndAmt()));

                        orgToObj.setCbsSchemeLedgerFolioDetailsCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleCompTO.getSchemeType());
                        orgToObj.setCurrencyCode(singleCompTO.getCurrencyCode());
                        orgToObj.setFreeFolios(singleCompTO.getFreeFolios());
                        orgToObj.setDelFlag(singleCompTO.getDelflaf());

                        CbsSchemeLedgerFolioDetailsCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLedgerFolioDetailsCurrencyWiseEntity(orgToObj);
                        lfdcw.save(singleEntity);
                    }
                    if (flag.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setStartAmount(new BigDecimal(singleCompTO.getStartAmt()));
                        orgPkToObj.setEndAmount(new BigDecimal(singleCompTO.getEndAmt()));

                        orgToObj.setCbsSchemeLedgerFolioDetailsCurrencyWisePKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleCompTO.getSchemeType());
                        orgToObj.setCurrencyCode(singleCompTO.getCurrencyCode());
                        orgToObj.setFreeFolios(singleCompTO.getFreeFolios());
                        orgToObj.setDelFlag(singleCompTO.getDelflaf());

                        CbsSchemeLedgerFolioDetailsCurrencyWise singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLedgerFolioDetailsCurrencyWiseEntity(orgToObj);
                        lfdcw.update(singleEntity);
                    }
                }
            }

            /***lrcd form processing***/
            if (loanRepaymentCyclDefs != null) {
                CbsSchemeLoanRepaymentCycleDefinitionTO orgToObj = new CbsSchemeLoanRepaymentCycleDefinitionTO();
                CbsSchemeLoanRepaymentCycleDefinitionPKTO orgPkToObj = new CbsSchemeLoanRepaymentCycleDefinitionPKTO();

                for (LoanRepaymentCycleDefinationTO singleCompTO : loanRepaymentCyclDefs) {
                    String flag = singleCompTO.getCounterSaveUpdate();
                    if (flag.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setAccountOpenFromDate(singleCompTO.getAcOpenFromDate());
                        orgToObj.setCbsSchemeLoanRepaymentCycleDefinitionPKTO(orgPkToObj);

                        orgToObj.setAccountOpenToDate(singleCompTO.getAcOpenToDate());
                        orgToObj.setCurrentOrNextMonthIndicator(singleCompTO.getMonthIndicator());
                        orgToObj.setRepaymentStartDate(singleCompTO.getRepaymentStartDate());
                        orgToObj.setDelFlag(singleCompTO.getDelFlag());

                        CbsSchemeLoanRepaymentCycleDefinition singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanRepaymentCycleDefinitionEntity(orgToObj);
                        lrcd.save(singleEntity);
                    }
                    if (flag.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleCompTO.getSchemeCode());
                        orgPkToObj.setAccountOpenFromDate(singleCompTO.getAcOpenFromDate());
                        orgToObj.setCbsSchemeLoanRepaymentCycleDefinitionPKTO(orgPkToObj);

                        orgToObj.setAccountOpenToDate(singleCompTO.getAcOpenToDate());
                        orgToObj.setCurrentOrNextMonthIndicator(singleCompTO.getMonthIndicator());
                        orgToObj.setRepaymentStartDate(singleCompTO.getRepaymentStartDate());
                        orgToObj.setDelFlag(singleCompTO.getDelFlag());

                        CbsSchemeLoanRepaymentCycleDefinition singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeLoanRepaymentCycleDefinitionEntity(orgToObj);
                        lrcd.update(singleEntity);
                    }
                }
            }
            /***svi form processing***/
            if (schValidComplexTO != null) {
                CbsSchemeValidInstrumentsTO orgToObj = new CbsSchemeValidInstrumentsTO();
                CbsSchemeValidInstrumentsPKTO orgPkToObj = new CbsSchemeValidInstrumentsPKTO();

                for (SchemeValidInstrumentTO singleComTO : schValidComplexTO) {
                    String saveUpdateFlag = singleComTO.getSaveUpdateFlag();
                    if (saveUpdateFlag.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setInstrumentCode(singleComTO.getInstrumentCode());

                        orgToObj.setCbsSchemeValidInstrumentsPKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setCrDrIndFlag(singleComTO.getCrDrIndFlag());
                        orgToObj.setDelFlag(singleComTO.getDelFlag());

                        CbsSchemeValidInstruments singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeValidInstrumentsEntity(orgToObj);
                        svi.save(singleEntity);
                    }
                    if (saveUpdateFlag.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setInstrumentCode(singleComTO.getInstrumentCode());

                        orgToObj.setCbsSchemeValidInstrumentsPKTO(orgPkToObj);
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setCrDrIndFlag(singleComTO.getCrDrIndFlag());
                        orgToObj.setDelFlag(singleComTO.getDelFlag());

                        CbsSchemeValidInstruments singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeValidInstrumentsEntity(orgToObj);
                        svi.update(singleEntity);
                    }
                }
            }
            /***Added by Ankit***/
            /***AD form processing***/
            if (loanAssetTO != null) {
                CbsSchemeAssetDetailsTO orgToObj = new CbsSchemeAssetDetailsTO();
                CbsSchemeAssetDetailsPKTO orgPkToObj = new CbsSchemeAssetDetailsPKTO();
                for (LoanAssetTO singleComTO : loanAssetTO) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDpdCounter(singleComTO.getTbdPDCounter());
                        orgToObj.setCbsSchemeAssetDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getTbdelFlag());
                        orgToObj.setIntAccre(singleComTO.getTbIntAccre());
                        orgToObj.setIntFlagBk(singleComTO.getTbIntFlagBk());
                        orgToObj.setIntFlagColl(singleComTO.getTbIntFlagColl());
                        orgToObj.setIntSuspPlaceholder(singleComTO.getTbIntSuspPlaceHolder());
                        orgToObj.setMainClass(singleComTO.getTbMainClass());
                        orgToObj.setPdFlag(singleComTO.getTbPDFlag());
                        orgToObj.setPlaceholdersCr(singleComTO.getTbPlaceHoldersCr());
                        orgToObj.setProvisionDr(singleComTO.getTbprovisionDR());
                        orgToObj.setSubClass(singleComTO.getTbSubClass());
                        CbsSchemeAssetDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeAssetDetailsEntity(orgToObj);
                        adDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDpdCounter(singleComTO.getTbdPDCounter());
                        orgToObj.setCbsSchemeAssetDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getTbdelFlag());
                        orgToObj.setIntAccre(singleComTO.getTbIntAccre());
                        orgToObj.setIntFlagBk(singleComTO.getTbIntFlagBk());
                        orgToObj.setIntFlagColl(singleComTO.getTbIntFlagColl());
                        orgToObj.setIntSuspPlaceholder(singleComTO.getTbIntSuspPlaceHolder());
                        orgToObj.setMainClass(singleComTO.getTbMainClass());
                        orgToObj.setPdFlag(singleComTO.getTbPDFlag());
                        orgToObj.setPlaceholdersCr(singleComTO.getTbPlaceHoldersCr());
                        orgToObj.setProvisionDr(singleComTO.getTbprovisionDR());
                        orgToObj.setSubClass(singleComTO.getTbSubClass());
                        CbsSchemeAssetDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeAssetDetailsEntity(orgToObj);
                        adDao.update(singleEntity);
                    }
                }
            }
            /***DIDD form processing***/
            if (depositIntTO != null) {
                CbsSchemeDepositInterestDefinitionDetailsTO orgToObj = new CbsSchemeDepositInterestDefinitionDetailsTO();
                CbsSchemeDepositInterestDefinitionDetailsPKTO orgPkToObj = new CbsSchemeDepositInterestDefinitionDetailsPKTO();
                for (DepositIntTO singleComTO : depositIntTO) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdateDeposit();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setInterestMethod(singleComTO.getTblInterestMethod());
                        orgToObj.setCbsSchemeDepositInterestDefinitionDetailsPKTO(orgPkToObj);
                        orgToObj.setBaseAmountInd(singleComTO.getTblBaseAmtInd());
                        orgToObj.setBrokenPeriodBase(singleComTO.getTblBrokenPeriodBase());
                        orgToObj.setBrokenPeriodMthd(singleComTO.getTblBrokenPeriodMethod());
                        orgToObj.setCompoundingBase(singleComTO.getTblCompoundingBase());
                        orgToObj.setCompoundingPeriod(singleComTO.getTblCompoundingPeriod());
                        orgToObj.setDelFlag(singleComTO.getTblDeleteFlag());
                        orgToObj.setMaxDepositPeriodDays(singleComTO.getTblMaxDepositPeriodDays());
                        orgToObj.setMaxDepositPeriodMonths(singleComTO.getTblMaxDepositPeriodMonths());
                        orgToObj.setMinCompoundingBase(singleComTO.getTblMinCompoundingBase());
                        orgToObj.setMinCompoundingPeriod(singleComTO.getTblMinCompoundingPeriod());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        CbsSchemeDepositInterestDefinitionDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositInterestDefinitionDetailsEntity(orgToObj);
                        diddDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setInterestMethod(singleComTO.getTblInterestMethod());
                        orgToObj.setCbsSchemeDepositInterestDefinitionDetailsPKTO(orgPkToObj);
                        orgToObj.setBaseAmountInd(singleComTO.getTblBaseAmtInd());
                        orgToObj.setBrokenPeriodBase(singleComTO.getTblBrokenPeriodBase());
                        orgToObj.setBrokenPeriodMthd(singleComTO.getTblBrokenPeriodMethod());
                        orgToObj.setCompoundingBase(singleComTO.getTblCompoundingBase());
                        orgToObj.setCompoundingPeriod(singleComTO.getTblCompoundingPeriod());
                        orgToObj.setDelFlag(singleComTO.getTblDeleteFlag());
                        orgToObj.setMaxDepositPeriodDays(singleComTO.getTblMaxDepositPeriodDays());
                        orgToObj.setMaxDepositPeriodMonths(singleComTO.getTblMaxDepositPeriodMonths());
                        orgToObj.setMinCompoundingBase(singleComTO.getTblMinCompoundingBase());
                        orgToObj.setMinCompoundingPeriod(singleComTO.getTblMinCompoundingPeriod());
                        orgToObj.setSchemeType(singleComTO.getSchemeType());
                        CbsSchemeDepositInterestDefinitionDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositInterestDefinitionDetailsEntity(orgToObj);
                        diddDao.update(singleEntity);
                    }
                }
            }
            /***DFD form processing***/
            if (depositFlowTO != null) {
                CbsSchemeDepositFlowDetailsTO orgToObj = new CbsSchemeDepositFlowDetailsTO();
                CbsSchemeDepositFlowDetailsPKTO orgPkToObj = new CbsSchemeDepositFlowDetailsPKTO();
                for (DepositFlowTO singleComTO : depositFlowTO) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdateFlow();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setFlowCode(singleComTO.getTblFlowCode());
                        orgPkToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setCbsSchemeDepositFlowDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getTblDelFlagFlow());
                        orgToObj.setFlowFreqDays(singleComTO.getTblFlowFreqDays());
                        orgToObj.setFlowFreqMonths(singleComTO.getTblFlowFreqMonths());
                        orgToObj.setFlowPeriodBegin(singleComTO.getTblFlowPeriodBegin());
                        orgToObj.setFlowPeriodEnd(singleComTO.getTblFlowPeriodEnd());
                        CbsSchemeDepositFlowDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositFlowDetailsEntity(orgToObj);
                        dfdDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setFlowCode(singleComTO.getTblFlowCode());
                        orgPkToObj.setSchemeType(singleComTO.getSchemeType());
                        orgToObj.setCbsSchemeDepositFlowDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getTblDelFlagFlow());
                        orgToObj.setFlowFreqDays(singleComTO.getTblFlowFreqDays());
                        orgToObj.setFlowFreqMonths(singleComTO.getTblFlowFreqMonths());
                        orgToObj.setFlowPeriodBegin(singleComTO.getTblFlowPeriodBegin());
                        orgToObj.setFlowPeriodEnd(singleComTO.getTblFlowPeriodEnd());
                        CbsSchemeDepositFlowDetails singleEntity = ObjectAdaptorLoan.adaptToCBSSchemeDepositFlowDetailsEntity(orgToObj);
                        dfdDao.update(singleEntity);
                    }
                }
            }
            /***CSDD form processing***/
            if (schemeDocumentdetails != null) {
                CbsSchemeDocumentDetailsTO orgToObj = new CbsSchemeDocumentDetailsTO();
                CbsSchemeDocumentDetailsPKTO orgPkToObj = new CbsSchemeDocumentDetailsPKTO();
                for (SchemeDocumentDetailsTO singleComTO : schemeDocumentdetails) {
                    String counterSaveUpdate = singleComTO.getCounterSaveUpdate();
                    if (counterSaveUpdate.equalsIgnoreCase("S")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDocumentCode(singleComTO.getDocumentCode());
                        orgToObj.setCbsSchemeDocumentDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getDelFlagDocDetail());
                        orgToObj.setDocumentDescription(singleComTO.getDocumentDesc());
                        orgToObj.setMandatoryDocument(singleComTO.getMandatoryDoc());
                        CbsSchemeDocumentDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeDocumentDetailsEntity(orgToObj);
                        csddDao.save(singleEntity);
                    }
                    if (counterSaveUpdate.equalsIgnoreCase("U")) {
                        orgPkToObj.setSchemeCode(singleComTO.getSchemeCode());
                        orgPkToObj.setDocumentCode(singleComTO.getDocumentCode());
                        orgToObj.setCbsSchemeDocumentDetailsPKTO(orgPkToObj);
                        orgToObj.setDelFlag(singleComTO.getDelFlagDocDetail());
                        orgToObj.setDocumentDescription(singleComTO.getDocumentDesc());
                        orgToObj.setMandatoryDocument(singleComTO.getMandatoryDoc());
                        CbsSchemeDocumentDetails singleEntity = ObjectAdaptorLoan.adaptToCbsSchemeDocumentDetailsEntity(orgToObj);
                        csddDao.update(singleEntity);
                    }
                }
            }
            msg = "true";
        } catch (DAOException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getGSHSDDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method getGSHSDDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getGSHSDDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }



        return msg;
    }

    /********************************************Added by Ankit********************************************************/
    public CbsSchemeFlexiFixedDepositsDetailsTO getFfddDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeFlexiFixedDepositsDetailsTO CbsSchemeFlexiFixedDepositsDtlsTO = new CbsSchemeFlexiFixedDepositsDetailsTO();
        CbsSchemeFlexiFixedDepositsDetailsDAO CbsSchemeFlexiFixedDepositsDtlsDAO = new CbsSchemeFlexiFixedDepositsDetailsDAO(entityManager);
        try {
            CbsSchemeFlexiFixedDepositsDetails cbsSchemeFlexiFixedDepositsDetails = CbsSchemeFlexiFixedDepositsDtlsDAO.getEntityBySchemeCode(schemeCode);
            CbsSchemeFlexiFixedDepositsDtlsTO = ObjectAdaptorLoan.adaptToCbsSchemeFlexiFixedDepositsDetailsTO(cbsSchemeFlexiFixedDepositsDetails);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPopUpForm is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return CbsSchemeFlexiFixedDepositsDtlsTO;
    }

    public List<CbsSchemeDelinquencyDetailsTO> showDataDeliqDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeDelinquencyDetailsTO> cbsSchemeDelinquencyDetailsTOs = new ArrayList<CbsSchemeDelinquencyDetailsTO>();
        CbsSchemeDelinquencyDetailsDAO cbsSchemeDelinquencyDetailsDAO = new CbsSchemeDelinquencyDetailsDAO(entityManager);
        try {
            List<CbsSchemeDelinquencyDetails> cbsSchemeDelinquencyDetails = cbsSchemeDelinquencyDetailsDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchemeDelinquencyDetails.size() > 0) {
                for (CbsSchemeDelinquencyDetails entityList : cbsSchemeDelinquencyDetails) {
                    cbsSchemeDelinquencyDetailsTOs.add(ObjectAdaptorLoan.adaptToCBSSchemeDelInquencyDetailsTO(entityList));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeDelinquencyDetailsTOs;
    }

    public CbsSchemeLoanPrepaymentDetailsTO getLPDDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeLoanPrepaymentDetailsTO cbsSchemeLoanPrepaymentDetailsTO = new CbsSchemeLoanPrepaymentDetailsTO();
        CbsSchemeLoanPrepaymentDetailsDAO cbsSchemeLoanPrepaymentDetailsDAO = new CbsSchemeLoanPrepaymentDetailsDAO(entityManager);
        try {
            CbsSchemeLoanPrepaymentDetails cbsSchemeLoanPrepaymentDetails = cbsSchemeLoanPrepaymentDetailsDAO.getEntityBySchemeCode(schemeCode);
            cbsSchemeLoanPrepaymentDetailsTO = ObjectAdaptorLoan.adaptToCbsSchemeLoanPrepaymentDetailsTO(cbsSchemeLoanPrepaymentDetails);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPopUpForm is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeLoanPrepaymentDetailsTO;
    }

    public CbsSchemeLoanSchemeDetailsTO getLSDDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeLoanSchemeDetailsTO cbsSchemeLoanSchemeDetailsTO = new CbsSchemeLoanSchemeDetailsTO();
        CbsSchemeLoanSchemeDetailsDAO cbsSchemeLoanSchemeDetailsDAO = new CbsSchemeLoanSchemeDetailsDAO(entityManager);
        try {
            CbsSchemeLoanSchemeDetails cbsSchemeLoanSchemeDetails = cbsSchemeLoanSchemeDetailsDAO.getEntityBySchemeCode(schemeCode);
            cbsSchemeLoanSchemeDetailsTO = ObjectAdaptorLoan.adaptToCbsSchemeLoanSchemeDetailsTO(cbsSchemeLoanSchemeDetails);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPopUpForm is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeLoanSchemeDetailsTO;
    }

    public CbsSchemeCaSbSchDetailsTO getDataIntoSBSchemeParamMain(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeCaSbSchDetailsTO cbsSchemeCaSbSchDetailsTO = new CbsSchemeCaSbSchDetailsTO();
        CbsSchemeCaSbSchDetailsDAO cbsSchemeCaSbSchDetailsDAO = new CbsSchemeCaSbSchDetailsDAO(entityManager);
        try {
            CbsSchemeCaSbSchDetails cbsSchemeCaSbSchDetails = cbsSchemeCaSbSchDetailsDAO.getEntityBySchemeCode(schemeCode);
            cbsSchemeCaSbSchDetailsTO = ObjectAdaptorLoan.adaptToCBSSchemeCASBSchDetailsTO(cbsSchemeCaSbSchDetails);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getPopUpForm()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getPopUpForm is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return cbsSchemeCaSbSchDetailsTO;
    }

    public List<CbsSchemeLoanInterestDetailsTO> getLoanInterestDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<CbsSchemeLoanInterestDetailsTO> cbsSchemeLoanInterestDetailsTO = new ArrayList<CbsSchemeLoanInterestDetailsTO>();
        CbsSchemeLoanInterestDetailsDAO cbsSchemeLoanInterestDetailsDAO = new CbsSchemeLoanInterestDetailsDAO(entityManager);
        try {
            List<CbsSchemeLoanInterestDetails> cbsSchemeLoanInterestDetails = cbsSchemeLoanInterestDetailsDAO.getEntityBySchemeCode(schemeCode);
            if (cbsSchemeLoanInterestDetails.size() > 0) {
                for (CbsSchemeLoanInterestDetails entityList : cbsSchemeLoanInterestDetails) {
                    cbsSchemeLoanInterestDetailsTO.add(ObjectAdaptorLoan.adaptToCbsSchemeLoanInterestDetailsTO(entityList));
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSelectAssetDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getSelectAssetDetails is " + (System.nanoTime() - begin)
                    * 0.000000001
                    + " seconds");
        }
        return cbsSchemeLoanInterestDetailsTO;
    }

    /********************************************Added by Rohit****************************************************/
    public CbsSchemeLoanPreEiSetupDetailsTO getSchemeLoanPreEiDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeLoanPreEiSetupDetailsTO singleTO = new CbsSchemeLoanPreEiSetupDetailsTO();
        CbsSchemeLoanPreEiSetupDetailsDAO dao = new CbsSchemeLoanPreEiSetupDetailsDAO(entityManager);
        try {
            CbsSchemeLoanPreEiSetupDetails singleEntity = dao.getEntityBySchemeCode(schemeCode);
            singleTO = ObjectAdaptorLoan.adaptToCbsSchemeLoanPreEiSetupDetailsTO(singleEntity);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return singleTO;
    }

    public CbsSchemeGeneralSchemeParameterMasterTO getParamMasterDetails(String schemeCode) throws ApplicationException {
        long begin = System.nanoTime();
        CbsSchemeGeneralSchemeParameterMasterTO singleTO = new CbsSchemeGeneralSchemeParameterMasterTO();
        CbsSchemeGeneralSchemeParameterMasterDAO dao = new CbsSchemeGeneralSchemeParameterMasterDAO(entityManager);
        try {
            CbsSchemeGeneralSchemeParameterMaster singleEntity = dao.getEntityBySchemeCode(schemeCode);
            singleTO = ObjectAdaptorLoan.adaptToCbsSchemeGeneralSchemeParameterMasterTO(singleEntity);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDepositParameterMaintenance()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for selectDepositParameterMaintenance is " + (System.nanoTime() - begin) * 0.000000001
                    + " seconds");
        }
        return singleTO;
    }
}
