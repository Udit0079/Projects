/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.delegate;


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
import com.cbs.dto.loan.CbsSchemeAssetDetailsTO;
import com.cbs.dto.loan.CbsSchemeCaSbSchDetailsTO;
import com.cbs.dto.loan.CbsSchemeCashCrBillsAndOverdraftDetailsTO;
import com.cbs.dto.loan.CbsSchemeCurrencyDetailsTO;
import com.cbs.dto.loan.CbsSchemeCustAccountDetailsTO;
import com.cbs.dto.loan.CbsSchemeDelinquencyDetailsTO;
import com.cbs.dto.loan.CbsSchemeDepositFlowDetailsTO;
import com.cbs.dto.loan.CbsSchemeDepositInterestDefinitionDetailsTO;
import com.cbs.dto.loan.CbsSchemeDepositsSchemeParametersMaintananceTO;
import com.cbs.dto.loan.CbsSchemeDocumentDetailsTO;
import com.cbs.dto.loan.CbsSchemeFeeOrChargesDetailsTO;
import com.cbs.dto.loan.CbsSchemeFlexiFixedDepositsDetailsTO;
import com.cbs.dto.loan.CbsSchemeGeneralSchemeParameterMasterTO;
import com.cbs.dto.loan.CbsSchemeInterestOrServiceChargesDetailsTO;
import com.cbs.dto.loan.CbsSchemeLedgerFolioDetailsCurrencyWiseTO;
import com.cbs.dto.loan.CbsSchemeLoanExceptionDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanInterestDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanPreEiSetupDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanPrepaymentDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanRepaymentCycleDefinitionTO;
import com.cbs.dto.loan.CbsSchemeLoanSchemeDetailsTO;
import com.cbs.dto.loan.CbsSchemeTodReferenceDetailsTO;
import com.cbs.dto.loan.CbsSchemeTransactionReportCodeCurrencyWiseTO;
import com.cbs.dto.loan.CbsSchemeValidInstrumentsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.master.CbsSchemePopUpFormsTO;
import com.cbs.dto.master.GltableTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.SchemeMasterManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class SchemeMasterManagementDelegate {

    private final String jndiHomeName = "SchemeMasterManagementFacade";
    private SchemeMasterManagementFacadeRemote beanRemote = null;

    /**
     * 
     * @throws ServiceLocatorException 
     */
    public SchemeMasterManagementDelegate() throws ServiceLocatorException {
        Object lookup = ServiceLocator.getInstance().lookup(jndiHomeName);
        beanRemote = (SchemeMasterManagementFacadeRemote) lookup;
    }

    public List<CbsRefRecTypeTO> getCurrencyCode(String refRecNo) throws WebException {
        List<CbsRefRecTypeTO> cbsRefRecTOs = new ArrayList<CbsRefRecTypeTO>();
        try {
            cbsRefRecTOs = beanRemote.getCurrencyCode(refRecNo);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsRefRecTOs;
    }
     public String validSchemeMasterAd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.adSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterCad(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.cadSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterAom(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.aomSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterCd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.cdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterPm(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.pmSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterLsd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.lsdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterCrbosd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.crbosdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterCsdd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.csddSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterDd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.ddSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterDfd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.dfdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterDidd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.diddSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterDoip(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.doipSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterDspm(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.dspmSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterFfdd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.ffddSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterGshsd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.gshsdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterIscd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.iscdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterLed(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.ledSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterLfdcw(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.lfdcwSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterLid(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.lidSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterLpd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.lpdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterLpesd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.lpesdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterLrcd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.lrcdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterSfcd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.sfcdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterSpm(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.spmSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterStrd(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.strdSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterSvi(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.sviSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterTedcw(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.tedcwSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public String validSchemeMasterTrccw(String schemeCode) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.trccwSchemeValidation(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    public CbsSchemeLoanSchemeDetailsTO getSchTypeAndCurCodeBySchCode(String schemeCode) throws WebException {
        CbsSchemeLoanSchemeDetailsTO cbsSchLoanSchDtlsTO = new CbsSchemeLoanSchemeDetailsTO();
        try {
            cbsSchLoanSchDtlsTO = beanRemote.getSchTypeAndCurCodeBySchCode(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchLoanSchDtlsTO;
    }

    public List<CbsSchemePopUpFormsTO> getForms(String schemeType) throws WebException {
        List<CbsSchemePopUpFormsTO> cbsSchemePopUpFormsTOs = new ArrayList<CbsSchemePopUpFormsTO>();
        try {
            cbsSchemePopUpFormsTOs = beanRemote.getForms(schemeType);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemePopUpFormsTOs;
    }

    public CbsSchemePopUpFormsTO getPopForms(String formId) throws WebException {
        CbsSchemePopUpFormsTO cbsSchemePopUpFormsTO = new CbsSchemePopUpFormsTO();
        try {
            cbsSchemePopUpFormsTO = beanRemote.getPopUpForm(formId);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemePopUpFormsTO;
    }

    public List<CbsExceptionMasterTO> getExceptionCode() throws WebException {
        List<CbsExceptionMasterTO> cbsExceptionMasterTOs = new ArrayList<CbsExceptionMasterTO>();
        try {
            cbsExceptionMasterTOs = beanRemote.getExceptionCode();
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsExceptionMasterTOs;
    }

    public CbsExceptionMasterTO getExceptionDesc(String expCode) throws WebException {
        CbsExceptionMasterTO cbsExceptionMasterTO = new CbsExceptionMasterTO();
        try {
            cbsExceptionMasterTO = beanRemote.getExceptionCode(expCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsExceptionMasterTO;
    }

    public CbsSchemeCustAccountDetailsTO cadFormData(String schemeCode) throws WebException {
        CbsSchemeCustAccountDetailsTO cbsSchemeCustAccountDetailsTO = new CbsSchemeCustAccountDetailsTO();
        try {
            cbsSchemeCustAccountDetailsTO = beanRemote.cadFormData(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeCustAccountDetailsTO;
    }

    public List<CbsSchemeAssetDetailsTO> getSelectAssetDetails(String schemeCode) throws WebException {
        List<CbsSchemeAssetDetailsTO> cbsSchemeAssetDetailsTOs = new ArrayList<CbsSchemeAssetDetailsTO>();
        try {
            cbsSchemeAssetDetailsTOs = beanRemote.getSelectAssetDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeAssetDetailsTOs;
    }

    public CbsSchemeAccountOpenMatrixTO getSelectAccountOpenMatrix(String schemeCode) throws WebException {
        CbsSchemeAccountOpenMatrixTO cbsSchemeAccountOpenMatrixTO = new CbsSchemeAccountOpenMatrixTO();
        try {
            cbsSchemeAccountOpenMatrixTO = beanRemote.getSelectAccountOpenMatrix(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeAccountOpenMatrixTO;
    }

    public List<CbsLoanInterestCodeMasterTO> getDataTodRef() throws WebException {
        List<CbsLoanInterestCodeMasterTO> cbsLoanInterestCodeMasterTOs = new ArrayList<CbsLoanInterestCodeMasterTO>();
        try {
            cbsLoanInterestCodeMasterTOs = beanRemote.getDataTodRef();
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsLoanInterestCodeMasterTOs;
    }

    public CbsSchemeCurrencyDetailsTO getSelectCurrencyDetail(String schemeCode) throws WebException {
        CbsSchemeCurrencyDetailsTO cbsSchemeCurrencyDetailsTO = new CbsSchemeCurrencyDetailsTO();
        try {
            cbsSchemeCurrencyDetailsTO = beanRemote.getSelectCurrencyDetail(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeCurrencyDetailsTO;
    }

    public CbsSchemeCashCrBillsAndOverdraftDetailsTO getCrBosddetails(String schemeCode) throws WebException {
        CbsSchemeCashCrBillsAndOverdraftDetailsTO cbsSchemeCashCrBillsAndOverdraftDetailsTO = new CbsSchemeCashCrBillsAndOverdraftDetailsTO();
        try {
            cbsSchemeCashCrBillsAndOverdraftDetailsTO = beanRemote.getCrBosddetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeCashCrBillsAndOverdraftDetailsTO;
    }

    public List<CbsSchemeDocumentDetailsTO> getDataDocDetail(String schemeCode) throws WebException {
        List<CbsSchemeDocumentDetailsTO> cbsSchemeDocumentDetailsTOs = new ArrayList<CbsSchemeDocumentDetailsTO>();
        try {
            cbsSchemeDocumentDetailsTOs = beanRemote.getCBSDocumentDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeDocumentDetailsTOs;
    }

    public List<String> getDepositOverDueIntParameter(String schemeCode) throws WebException {
        List<String> depositOverDueIntParameter = new ArrayList<String>();
        try {
            depositOverDueIntParameter = beanRemote.getDepositOverDueIntParameter(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return depositOverDueIntParameter;
    }

    public List<CbsSchemeDepositFlowDetailsTO> getDepositFlowDetails(String schemeCode) throws WebException {
        List<CbsSchemeDepositFlowDetailsTO> cbsSchemeDepositFlowDetailsTOs = new ArrayList<CbsSchemeDepositFlowDetailsTO>();
        try {
            cbsSchemeDepositFlowDetailsTOs = beanRemote.getDepositFlowDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeDepositFlowDetailsTOs;
    }

    public List<CbsSchemeDepositInterestDefinitionDetailsTO> getDIDDDetails(String schemeCode) throws WebException {
        List<CbsSchemeDepositInterestDefinitionDetailsTO> cbsSchemeDepositInterestDefinitionDetailsTOs = new ArrayList<CbsSchemeDepositInterestDefinitionDetailsTO>();
        try {
            cbsSchemeDepositInterestDefinitionDetailsTOs = beanRemote.getDIDDDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeDepositInterestDefinitionDetailsTOs;
    }

    public List<GlSubHeadSchemeTO> getGSHSDDetails(String schemeCode) throws WebException {
        List<GlSubHeadSchemeTO> list = new ArrayList<GlSubHeadSchemeTO>();
        try {
            list = beanRemote.getGSHSDDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return list;
    }

    public GltableTO getGLTable(String acNo) throws WebException {
        GltableTO gltableTO = new GltableTO();
        try {
            gltableTO = beanRemote.getGLTable(acNo);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return gltableTO;
    }

    public CbsSchemeDepositsSchemeParametersMaintananceTO selectDepositParameterMaintenance(String schemeCode) throws WebException {
        CbsSchemeDepositsSchemeParametersMaintananceTO singleTO = new CbsSchemeDepositsSchemeParametersMaintananceTO();
        try {
            singleTO = beanRemote.selectDepositParameterMaintenance(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return singleTO;
    }

    public CbsSchemeInterestOrServiceChargesDetailsTO getIscdDetails(String schemeCode) throws WebException {
        CbsSchemeInterestOrServiceChargesDetailsTO singleTO = new CbsSchemeInterestOrServiceChargesDetailsTO();
        try {
            singleTO = beanRemote.getIscdDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return singleTO;
    }

    public CbsSchemeLoanExceptionDetailsTO getLedDetails(String schemeCode) throws WebException {
        CbsSchemeLoanExceptionDetailsTO singleTO = new CbsSchemeLoanExceptionDetailsTO();
        try {
            singleTO = beanRemote.getLedDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return singleTO;
    }

    public List<CbsSchemeValidInstrumentsTO> getSviDetails(String schemeCode) throws WebException {
        List<CbsSchemeValidInstrumentsTO> listTOs = new ArrayList<CbsSchemeValidInstrumentsTO>();
        try {
            listTOs = beanRemote.getSviDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return listTOs;
    }

    public List<CbsSchemeLedgerFolioDetailsCurrencyWiseTO> getLfdcwDetails(String schemeCode) throws WebException {
        List<CbsSchemeLedgerFolioDetailsCurrencyWiseTO> listTOs = new ArrayList<CbsSchemeLedgerFolioDetailsCurrencyWiseTO>();
        try {
            listTOs = beanRemote.getLfdcwDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return listTOs;
    }

    public List<CbsSchemeLoanRepaymentCycleDefinitionTO> getLrcddetails(String schemeCode) throws WebException {
        List<CbsSchemeLoanRepaymentCycleDefinitionTO> listTOs = new ArrayList<CbsSchemeLoanRepaymentCycleDefinitionTO>();
        try {
            listTOs = beanRemote.getLrcddetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return listTOs;
    }

    public List<CbsSchemeFeeOrChargesDetailsTO> getSfcdDetails(String schemeCode) throws WebException {
        List<CbsSchemeFeeOrChargesDetailsTO> listTOs = new ArrayList<CbsSchemeFeeOrChargesDetailsTO>();
        try {
            listTOs = beanRemote.getSfcdDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return listTOs;
    }

    public List<CbsSchemeTransactionReportCodeCurrencyWiseTO> getTrccwdetails(String schemeCode) throws WebException {
        List<CbsSchemeTransactionReportCodeCurrencyWiseTO> listTOs = new ArrayList<CbsSchemeTransactionReportCodeCurrencyWiseTO>();
        try {
            listTOs = beanRemote.getTrccwdetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return listTOs;
    }

    public List<CbsSchemeTodReferenceDetailsTO> getStrdDetails(String schemeCode) throws WebException {
        List<CbsSchemeTodReferenceDetailsTO> listTOs = new ArrayList<CbsSchemeTodReferenceDetailsTO>();
        try {
            listTOs = beanRemote.getStrdDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return listTOs;
    }

    public List<TodExceptionDetailsTO> getTODExceptionDetailsAccToSchemeTod(String schemeCode) throws WebException {
        List<TodExceptionDetailsTO> todExceptionDetailsTOs = new ArrayList<TodExceptionDetailsTO>();
        try {
            todExceptionDetailsTOs = beanRemote.getTODExceptionDetailsAccToSchemeTod(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return todExceptionDetailsTOs;
    }

    /**************************For save & update***********************************/
    public String saveSchemeMaster(List<CbsSchemeCustAccountDetailsTO> cbsSchemeCustAccountDetailsTOs, List<CbsSchemeAccountOpenMatrixTO> cbsSchemeAccountOpenMatrixTOs, List<CbsSchemeCurrencyDetailsTO> cbsSchemeCurrencyDetailsTOs, List<CbsSchemeCashCrBillsAndOverdraftDetailsTO> cbsSchemeCashCrBillsAndOverdraftDetailsTOs, List<DoipComplexTO> depositOverDueIntParameter, List<GlSubHeadSchemeTO> glSubHeadSchemeTOs, List<CbsSchemeDepositsSchemeParametersMaintananceTO> cbsSchDepSchParaMainTOs, List<CbsSchemeInterestOrServiceChargesDetailsTO> cbsSchIntSerChgDtls, List<CbsSchemeLoanExceptionDetailsTO> cbsSchLoanExpDtls, List<SchemeValidInstrumentTO> schValidComplexTO, List<LedgerFolioDetailsCurWiseTO> ledgerFolioDtls, List<LoanRepaymentCycleDefinationTO> loanRepaymentCyclDefs, List<FeeOrChargesDetailsTO> feeOrChargesDtls, List<TransactionReportCodeTO> transRepCode, List<SchemeTODRefdetailsTO> schemeTodRefdetails, List<TodExceptionDetailsTO> todExceptionDetails, List<SchemeDocumentDetailsTO> schemeDocumentdetails, List<DepositFlowTO> depositFlowTO, List<LoanAssetTO> loanAssetTO, List<DepositIntTO> depositIntTO, List<CbsSchemeFlexiFixedDepositsDetailsTO> cbsSchemeFlexiFixedDepositsDetailsTOs, List<CbsSchemeLoanPrepaymentDetailsTO> cbsSchemeLoanPrepaymentDetailsTOs, List<CbsSchemeLoanSchemeDetailsTO> cbsSchemeLoanSchemeDetailsTOs, List<CbsSchemeCaSbSchDetailsTO> cbsSchemeCaSbSchDetailsTOs, List<DeliquencyTableTO> deliqDetailsTOs, List<CbsSchemeLoanInterestDetailsTO> cbsSchemeLoanInterestDetailsTOs, List<CbsSchemeLoanPreEiSetupDetailsTO> cbsSchemeLoanPreEiSetupDetailsTOs, List<CbsSchemeGeneralSchemeParameterMasterTO> cbsSchemeGeneralSchemeParameterMasterTOs) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.saveSchemeMaster(cbsSchemeCustAccountDetailsTOs, cbsSchemeAccountOpenMatrixTOs, cbsSchemeCurrencyDetailsTOs, cbsSchemeCashCrBillsAndOverdraftDetailsTOs, depositOverDueIntParameter, glSubHeadSchemeTOs, cbsSchDepSchParaMainTOs, cbsSchIntSerChgDtls, cbsSchLoanExpDtls, schValidComplexTO, ledgerFolioDtls, loanRepaymentCyclDefs, feeOrChargesDtls, transRepCode, schemeTodRefdetails, todExceptionDetails, schemeDocumentdetails, depositFlowTO, loanAssetTO, depositIntTO, cbsSchemeFlexiFixedDepositsDetailsTOs, cbsSchemeLoanPrepaymentDetailsTOs, cbsSchemeLoanSchemeDetailsTOs, cbsSchemeCaSbSchDetailsTOs, deliqDetailsTOs,cbsSchemeLoanInterestDetailsTOs,cbsSchemeLoanPreEiSetupDetailsTOs,cbsSchemeGeneralSchemeParameterMasterTOs);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }

    public String updateSchemeMaster(List<CbsSchemeCustAccountDetailsTO> cbsSchemeCustAccountDetailsTOs, List<CbsSchemeAccountOpenMatrixTO> cbsSchemeAccountOpenMatrixTOs, List<CbsSchemeCurrencyDetailsTO> cbsSchemeCurrencyDetailsTOs, List<CbsSchemeCashCrBillsAndOverdraftDetailsTO> cbsSchemeCashCrBillsAndOverdraftDetailsTOs, List<DoipComplexTO> depositOverDueIntParameter, List<GlSubHeadSchemeTO> glSubHeadSchemeTOs, List<CbsSchemeDepositsSchemeParametersMaintananceTO> cbsSchDepSchParaMainTOs, List<CbsSchemeInterestOrServiceChargesDetailsTO> cbsSchIntSerChgDtls, List<CbsSchemeLoanExceptionDetailsTO> cbsSchLoanExpDtls, List<SchemeValidInstrumentTO> schValidComplexTO, List<LedgerFolioDetailsCurWiseTO> ledgerFolioDtls, List<LoanRepaymentCycleDefinationTO> loanRepaymentCyclDefs, List<FeeOrChargesDetailsTO> feeOrChargesDtls, List<TransactionReportCodeTO> transRepCode, List<SchemeTODRefdetailsTO> schemeTodRefdetails, List<TodExceptionDetailsTO> todExceptionDetails, List<SchemeDocumentDetailsTO> schemeDocumentdetails, List<DepositFlowTO> depositFlowTO, List<LoanAssetTO> loanAssetTO, List<DepositIntTO> depositIntTO, List<CbsSchemeFlexiFixedDepositsDetailsTO> cbsSchemeFlexiFixedDepositsDetailsTOs, List<CbsSchemeLoanPrepaymentDetailsTO> cbsSchemeLoanPrepaymentDetailsTOs, List<CbsSchemeLoanSchemeDetailsTO> cbsSchemeLoanSchemeDetailsTOs, List<CbsSchemeCaSbSchDetailsTO> cbsSchemeCaSbSchDetailsTOs, List<DeliquencyTableTO> deliqDetailsTOs, List<CbsSchemeLoanInterestDetailsTO> cbsSchemeLoanInterestDetailsTOs, List<CbsSchemeLoanPreEiSetupDetailsTO> cbsSchemeLoanPreEiSetupDetailsTOs, List<CbsSchemeGeneralSchemeParameterMasterTO> cbsSchemeGeneralSchemeParameterMasterTOs) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.updateSchemeMaster(cbsSchemeCustAccountDetailsTOs, cbsSchemeAccountOpenMatrixTOs, cbsSchemeCurrencyDetailsTOs, cbsSchemeCashCrBillsAndOverdraftDetailsTOs, depositOverDueIntParameter, glSubHeadSchemeTOs, cbsSchDepSchParaMainTOs, cbsSchIntSerChgDtls, cbsSchLoanExpDtls, schValidComplexTO, ledgerFolioDtls, loanRepaymentCyclDefs, feeOrChargesDtls, transRepCode, schemeTodRefdetails, todExceptionDetails, schemeDocumentdetails, depositFlowTO, loanAssetTO, depositIntTO, cbsSchemeFlexiFixedDepositsDetailsTOs, cbsSchemeLoanPrepaymentDetailsTOs, cbsSchemeLoanSchemeDetailsTOs, cbsSchemeCaSbSchDetailsTOs, deliqDetailsTOs,cbsSchemeLoanInterestDetailsTOs,cbsSchemeLoanPreEiSetupDetailsTOs,cbsSchemeGeneralSchemeParameterMasterTOs);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }

    /**************************Save & update end here***********************************/
    /**************************Added by Ankit***********************************/
    public CbsSchemeFlexiFixedDepositsDetailsTO getFfddDetails(String schemeCode) throws WebException {
        CbsSchemeFlexiFixedDepositsDetailsTO cbsSchemeFlexiFixedDepositsDtls = new CbsSchemeFlexiFixedDepositsDetailsTO();
        try {
            cbsSchemeFlexiFixedDepositsDtls = beanRemote.getFfddDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeFlexiFixedDepositsDtls;

    }

    public List<CbsSchemeDelinquencyDetailsTO> showDataDeliqDetails(String schemeCode) throws WebException {
        List<CbsSchemeDelinquencyDetailsTO> cbsSchemeDelinquencyDetailsTOs = new ArrayList<CbsSchemeDelinquencyDetailsTO>();
        try {
            cbsSchemeDelinquencyDetailsTOs = beanRemote.showDataDeliqDetails(schemeCode);

        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeDelinquencyDetailsTOs;
    }

    public CbsSchemeLoanPrepaymentDetailsTO getLPDDetails(String schemeCode) throws WebException {
        CbsSchemeLoanPrepaymentDetailsTO cbsSchemeLoanPrepaymentDetailsTO = new CbsSchemeLoanPrepaymentDetailsTO();
        try {
            cbsSchemeLoanPrepaymentDetailsTO = beanRemote.getLPDDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeLoanPrepaymentDetailsTO;
    }

    public CbsSchemeLoanSchemeDetailsTO getLSDDetails(String schemeCode) throws WebException {
        CbsSchemeLoanSchemeDetailsTO cbsSchemeLoanSchemeDetailsTO = new CbsSchemeLoanSchemeDetailsTO();
        try {
            cbsSchemeLoanSchemeDetailsTO = beanRemote.getLSDDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeLoanSchemeDetailsTO;
    }

    public CbsSchemeCaSbSchDetailsTO getDataIntoSBSchemeParamMain(String schemeCode) throws WebException {
        CbsSchemeCaSbSchDetailsTO cbsSchemeCaSbSchDetailsTO = new CbsSchemeCaSbSchDetailsTO();
        try {
            cbsSchemeCaSbSchDetailsTO = beanRemote.getDataIntoSBSchemeParamMain(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return cbsSchemeCaSbSchDetailsTO;
    }

    public List<CbsSchemeLoanInterestDetailsTO> getLoaninterestDetails(String schemeCode)
            throws WebException {
        List<CbsSchemeLoanInterestDetailsTO> list = new ArrayList<CbsSchemeLoanInterestDetailsTO>();
        try {
            list = beanRemote.getLoanInterestDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return list;
    }

    /**************************Added by Rohit********************************/
    public CbsSchemeLoanPreEiSetupDetailsTO getLoanPreEiDetails(String schemeCode) throws WebException {
        CbsSchemeLoanPreEiSetupDetailsTO listTOs = new CbsSchemeLoanPreEiSetupDetailsTO();
        try {
            listTOs = beanRemote.getSchemeLoanPreEiDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return listTOs;
    }

    public CbsSchemeGeneralSchemeParameterMasterTO getParameterMasterDetails(String schemeCode) throws WebException {
        CbsSchemeGeneralSchemeParameterMasterTO listTOs = new CbsSchemeGeneralSchemeParameterMasterTO();
        try {
            listTOs = beanRemote.getParamMasterDetails(schemeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return listTOs;
    }
}
