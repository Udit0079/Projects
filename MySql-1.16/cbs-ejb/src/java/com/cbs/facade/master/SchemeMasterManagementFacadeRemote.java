/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.master;

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
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface SchemeMasterManagementFacadeRemote {

    public List<CbsRefRecTypeTO> getCurrencyCode(String refRecNo) throws ApplicationException;

    public CbsSchemeLoanSchemeDetailsTO getSchTypeAndCurCodeBySchCode(String schemeCode) throws ApplicationException;

    public List<CbsSchemePopUpFormsTO> getForms(String schemeType) throws ApplicationException;

    public CbsSchemePopUpFormsTO getPopUpForm(String formId) throws ApplicationException;

    public List<CbsExceptionMasterTO> getExceptionCode() throws ApplicationException;

    public CbsExceptionMasterTO getExceptionCode(String expCode) throws ApplicationException;

    public CbsSchemeCustAccountDetailsTO cadFormData(String schemeCode) throws ApplicationException;

    public List<CbsSchemeAssetDetailsTO> getSelectAssetDetails(String schemeCode) throws ApplicationException;

    public CbsSchemeAccountOpenMatrixTO getSelectAccountOpenMatrix(String schemeCode) throws ApplicationException;

    public List<CbsLoanInterestCodeMasterTO> getDataTodRef() throws ApplicationException;

    public CbsSchemeCurrencyDetailsTO getSelectCurrencyDetail(String schemeCode) throws ApplicationException;

    public CbsSchemeCashCrBillsAndOverdraftDetailsTO getCrBosddetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeDocumentDetailsTO> getCBSDocumentDetails(String schemeCode) throws ApplicationException;

    public List<String> getDepositOverDueIntParameter(String schemeCode) throws ApplicationException;

    public List<CbsSchemeDepositFlowDetailsTO> getDepositFlowDetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeDepositInterestDefinitionDetailsTO> getDIDDDetails(String schemeCode) throws ApplicationException;

    public List<GlSubHeadSchemeTO> getGSHSDDetails(String schemeCode) throws ApplicationException;

    public GltableTO getGLTable(String acNo) throws ApplicationException;

    public CbsSchemeDepositsSchemeParametersMaintananceTO selectDepositParameterMaintenance(String schemeCode) throws ApplicationException;

    public CbsSchemeInterestOrServiceChargesDetailsTO getIscdDetails(String schemeCode) throws ApplicationException;

    public CbsSchemeLoanExceptionDetailsTO getLedDetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeValidInstrumentsTO> getSviDetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeLedgerFolioDetailsCurrencyWiseTO> getLfdcwDetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeLoanRepaymentCycleDefinitionTO> getLrcddetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeFeeOrChargesDetailsTO> getSfcdDetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeTransactionReportCodeCurrencyWiseTO> getTrccwdetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeTodReferenceDetailsTO> getStrdDetails(String schemeCode) throws ApplicationException;

    public List<TodExceptionDetailsTO> getTODExceptionDetailsAccToSchemeTod(String schemeCode) throws ApplicationException;

    public String saveSchemeMaster(List<CbsSchemeCustAccountDetailsTO> cbsSchemeCustAccountDetailsTOs, List<CbsSchemeAccountOpenMatrixTO> cbsSchemeAccountOpenMatrixTOs, 
    	List<CbsSchemeCurrencyDetailsTO> cbsSchemeCurrencyDetailsTOs, List<CbsSchemeCashCrBillsAndOverdraftDetailsTO> cbsSchemeCashCrBillsAndOverdraftDetailsTOs, 
    	List<DoipComplexTO> depositOverDueIntParameter, List<GlSubHeadSchemeTO> glSubHeadSchemeTOs, List<CbsSchemeDepositsSchemeParametersMaintananceTO> cbsSchDepSchParaMainTOs, 
    	List<CbsSchemeInterestOrServiceChargesDetailsTO> cbsSchIntSerChgDtls, List<CbsSchemeLoanExceptionDetailsTO> cbsSchLoanExpDtls, List<SchemeValidInstrumentTO> schValidComplexTO, 
    	List<LedgerFolioDetailsCurWiseTO> ledgerFolioDtls, List<LoanRepaymentCycleDefinationTO> loanRepaymentCyclDefs, List<FeeOrChargesDetailsTO> feeOrChargesDtls, 
    	List<TransactionReportCodeTO> transRepCode, List<SchemeTODRefdetailsTO> schemeTodRefdetails, List<TodExceptionDetailsTO> todExceptionDetails, 
    	List<SchemeDocumentDetailsTO> schemeDocumentdetails, List<DepositFlowTO> depositFlowTO, List<LoanAssetTO> loanAssetTO, List<DepositIntTO> depositIntTO, 
    	List<CbsSchemeFlexiFixedDepositsDetailsTO> cbsSchemeFlexiFixedDepositsDetailsTOs, List<CbsSchemeLoanPrepaymentDetailsTO> cbsSchemeLoanPrepaymentDetailsTOs, 
    	List<CbsSchemeLoanSchemeDetailsTO> cbsSchemeLoanSchemeDetailsTOs, List<CbsSchemeCaSbSchDetailsTO> cbsSchemeCaSbSchDetailsTOs, List<DeliquencyTableTO> deliqDetailsTOs, 
    	List<CbsSchemeLoanInterestDetailsTO> cbsSchemeLoanInterestDetailsTOs, List<CbsSchemeLoanPreEiSetupDetailsTO> cbsSchemeLoanPreEiSetupDetailsTOs, 
    	List<CbsSchemeGeneralSchemeParameterMasterTO> cbsSchemeGeneralSchemeParameterMasterTOs) throws ApplicationException;

    public String updateSchemeMaster(List<CbsSchemeCustAccountDetailsTO> cbsSchemeCustAccountDetailsTOs, List<CbsSchemeAccountOpenMatrixTO> cbsSchemeAccountOpenMatrixTOs, 
    	List<CbsSchemeCurrencyDetailsTO> cbsSchemeCurrencyDetailsTOs, List<CbsSchemeCashCrBillsAndOverdraftDetailsTO> cbsSchemeCashCrBillsAndOverdraftDetailsTOs, 
    	List<DoipComplexTO> depositOverDueIntParameter, List<GlSubHeadSchemeTO> glSubHeadSchemeTOs, List<CbsSchemeDepositsSchemeParametersMaintananceTO> cbsSchDepSchParaMainTOs, 
    	List<CbsSchemeInterestOrServiceChargesDetailsTO> cbsSchIntSerChgDtls, List<CbsSchemeLoanExceptionDetailsTO> cbsSchLoanExpDtls, List<SchemeValidInstrumentTO> schValidComplexTO, 
    	List<LedgerFolioDetailsCurWiseTO> ledgerFolioDtls, List<LoanRepaymentCycleDefinationTO> loanRepaymentCyclDefs, List<FeeOrChargesDetailsTO> feeOrChargesDtls, 
    	List<TransactionReportCodeTO> transRepCode, List<SchemeTODRefdetailsTO> schemeTodRefdetails, List<TodExceptionDetailsTO> todExceptionDetails, 
    	List<SchemeDocumentDetailsTO> schemeDocumentdetails, List<DepositFlowTO> depositFlowTO, List<LoanAssetTO> loanAssetTO, List<DepositIntTO> depositIntTO, 
    	List<CbsSchemeFlexiFixedDepositsDetailsTO> cbsSchemeFlexiFixedDepositsDetailsTOs, List<CbsSchemeLoanPrepaymentDetailsTO> cbsSchemeLoanPrepaymentDetailsTOs, 
    	List<CbsSchemeLoanSchemeDetailsTO> cbsSchemeLoanSchemeDetailsTOs, List<CbsSchemeCaSbSchDetailsTO> cbsSchemeCaSbSchDetailsTOs, List<DeliquencyTableTO> deliqDetailsTOs, 
    	List<CbsSchemeLoanInterestDetailsTO> cbsSchemeLoanInterestDetailsTOs, List<CbsSchemeLoanPreEiSetupDetailsTO> cbsSchemeLoanPreEiSetupDetailsTOs, 
    	List<CbsSchemeGeneralSchemeParameterMasterTO> cbsSchemeGeneralSchemeParameterMasterTOs) throws ApplicationException;

    /******************Added by Ankit**********************************/
    public CbsSchemeFlexiFixedDepositsDetailsTO getFfddDetails(String schemeCode) throws ApplicationException;

    public List<CbsSchemeDelinquencyDetailsTO> showDataDeliqDetails(String schemeCode) throws ApplicationException;

    public CbsSchemeLoanPrepaymentDetailsTO getLPDDetails(String schemeCode) throws ApplicationException;

    public CbsSchemeLoanSchemeDetailsTO getLSDDetails(String schemeCode) throws ApplicationException;

    public CbsSchemeCaSbSchDetailsTO getDataIntoSBSchemeParamMain(String schemeCode) throws ApplicationException;

    public List<CbsSchemeLoanInterestDetailsTO> getLoanInterestDetails(String schemeCode) throws ApplicationException;

    /*****************Added by Rohit********************************/
    public CbsSchemeLoanPreEiSetupDetailsTO getSchemeLoanPreEiDetails(String schemeCode) throws ApplicationException;

    public CbsSchemeGeneralSchemeParameterMasterTO getParamMasterDetails(String schemeCode) throws ApplicationException;
    //Added by Ankit

    public String cadSchemeValidation(String schemeCode) throws ApplicationException;

    public String aomSchemeValidation(String schemeCode) throws ApplicationException;

    public String cdSchemeValidation(String schemeCode) throws ApplicationException;

    public String lsdSchemeValidation(String schemeCode) throws ApplicationException;

    public String pmSchemeValidation(String schemeCode) throws ApplicationException;

    public String crbosdSchemeValidation(String schemeCode) throws ApplicationException;

    public String adSchemeValidation(String schemeCode) throws ApplicationException;

    public String csddSchemeValidation(String schemeCode) throws ApplicationException;

    public String ddSchemeValidation(String schemeCode) throws ApplicationException;

    public String dfdSchemeValidation(String schemeCode) throws ApplicationException;

    public String diddSchemeValidation(String schemeCode) throws ApplicationException;

    public String doipSchemeValidation(String schemeCode) throws ApplicationException;

    public String dspmSchemeValidation(String schemeCode) throws ApplicationException;

    public String ffddSchemeValidation(String schemeCode) throws ApplicationException;

    public String gshsdSchemeValidation(String schemeCode) throws ApplicationException;

    public String iscdSchemeValidation(String schemeCode) throws ApplicationException;

    public String ledSchemeValidation(String schemeCode) throws ApplicationException;

    public String lfdcwSchemeValidation(String schemeCode) throws ApplicationException;

    public String lidSchemeValidation(String schemeCode) throws ApplicationException;

    public String lpdSchemeValidation(String schemeCode) throws ApplicationException;

    public String lpesdSchemeValidation(String schemeCode) throws ApplicationException;

    public String lrcdSchemeValidation(String schemeCode) throws ApplicationException;

    public String sfcdSchemeValidation(String schemeCode) throws ApplicationException;

    public String spmSchemeValidation(String schemeCode) throws ApplicationException;

    public String strdSchemeValidation(String schemeCode) throws ApplicationException;

    public String sviSchemeValidation(String schemeCode) throws ApplicationException;

    public String tedcwSchemeValidation(String schemeCode) throws ApplicationException;

    public String trccwSchemeValidation(String schemeCode) throws ApplicationException;
}
