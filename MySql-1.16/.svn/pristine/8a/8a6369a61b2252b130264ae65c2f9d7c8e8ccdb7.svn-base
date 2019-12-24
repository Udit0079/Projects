/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;



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
import com.cbs.dto.loan.CbsSchemeAccountOpenMatrixTO;
import com.cbs.dto.loan.CbsSchemeCaSbSchDetailsTO;
import com.cbs.dto.loan.CbsSchemeCashCrBillsAndOverdraftDetailsTO;
import com.cbs.dto.loan.CbsSchemeCurrencyDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeCurrencyDetailsTO;
import com.cbs.dto.loan.CbsSchemeCustAccountDetailsTO;
import com.cbs.dto.loan.CbsSchemeDepositsSchemeParametersMaintananceTO;
import com.cbs.dto.loan.CbsSchemeFlexiFixedDepositsDetailsTO;
import com.cbs.dto.loan.CbsSchemeGeneralSchemeParameterMasterTO;
import com.cbs.dto.loan.CbsSchemeInterestOrServiceChargesDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanExceptionDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanInterestDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanPreEiSetupDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanPrepaymentDetailsTO;
import com.cbs.dto.loan.CbsSchemeLoanSchemeDetailsTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.DeliquencyTable;
import com.cbs.web.pojo.master.DepositFlow;
import com.cbs.web.pojo.master.DepositInt;
import com.cbs.web.pojo.master.GlSubHeadTable;
import com.cbs.web.pojo.master.LoanAsset;
import com.cbs.web.pojo.master.LoanRepaymentCycleDefination;
import com.cbs.web.pojo.master.SchemeDocumentDetails;
import com.cbs.web.pojo.master.SchemeFeeOrChargesDetails;
import com.cbs.web.pojo.master.SchemeTodCurrencyWiseTable;
import com.cbs.web.pojo.master.SchemeTodReferenceDetails;
import com.cbs.web.pojo.master.SchemeTranReportCodeCurrWise;
import com.cbs.web.pojo.master.SchemeValidInstrumentsTable;
import com.cbs.web.pojo.master.TblLedgerFolio;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class OperationSchemeMaster {

    private static final Logger logger = Logger.getLogger(OperationSchemeMaster.class);
    SchemeMaster schemeMaster;
    private AD aD;
    private CAD cAD;
    private AOM aOM;
    private CD cD;
    private CRBOSD crBosd;
    private CSDD cSDD;
    private DOIP dOIP;
    private DFD dFD;
    private DIDD dIDD;
    private GSHSD gSHSD;
    private DSPM dSPM;
    private ISCD iSCD;
    private LED lED;
    private SVI sVI;
    private LFDCW lFDCW;
    private LRCD lRCD;
    private SFCD sFCD;
    private TRCCW tRCCW;
    private STRD sTRD;
    private TEDCW tEDCW;
    //Added by Ankit
    private FFDD fFDD;
    private DD ddD;
    private LPD lPD;
    private LSD lSD;
    private SPM sPM;
    private LID lID;
    //Addition end here
    //Added by Rohit
    private LPESD lPESD;
    private PM pM;
    private String msg;
    private boolean popUpFlag;
    //Addition end here
    List<CbsSchemeCustAccountDetailsTO> cbsSchemeCustAccountDetailsTOs; //cad form list
    List<CbsSchemeAccountOpenMatrixTO> cbsSchemeAccountOpenMatrixTOs;   //aom form list
    List<CbsSchemeCurrencyDetailsTO> cbsSchemeCurrencyDetailsTOs;   //cd form list
    List<CbsSchemeCashCrBillsAndOverdraftDetailsTO> cbsSchemeCashCrBillsAndOverdraftDetailsTOs;     //crbosd from list
    List<DoipComplexTO> depositOverDueIntParameter;    //doip form list
    List<GlSubHeadSchemeTO> GlSubHeadSchemeTOs; //gshsd form list
    List<CbsSchemeDepositsSchemeParametersMaintananceTO> cbsSchDepSchParaMainTOs;   //dspm form list
    List<CbsSchemeInterestOrServiceChargesDetailsTO> cbsSchIntSerChgDtls;   //iscd form list
    List<CbsSchemeLoanExceptionDetailsTO> cbsSchLoanExpDtls;    //led form list
    List<SchemeValidInstrumentTO> schValidComplexTO;    //svi form list
    List<LedgerFolioDetailsCurWiseTO> ledgerFolioDtls;  //lfdcw form list
    List<LoanRepaymentCycleDefinationTO> loanRepaymentCyclDefs;     //lrcd form list
    List<FeeOrChargesDetailsTO> feeOrChargesDtls;   //sfcd form list
    List<TransactionReportCodeTO> transRepCode;
    List<SchemeTODRefdetailsTO> schemeTodRefdetails;
    List<TodExceptionDetailsTO> todExceptionDetails;
    List<SchemeDocumentDetailsTO> schemeDocumentdetails;
    List<DepositFlowTO> depositFlowTO;
    List<LoanAssetTO> loanAssetTO;
    List<DepositIntTO> depositIntTO;
    //Added by Ankit
    List<CbsSchemeFlexiFixedDepositsDetailsTO> cbsSchemeFlexiFixedDepositsDetailsTOs;
    List<CbsSchemeLoanPrepaymentDetailsTO> cbsSchemeLoanPrepaymentDetailsTOs;
    List<CbsSchemeLoanSchemeDetailsTO> cbsSchemeLoanSchemeDetailsTOs;
    List<CbsSchemeCaSbSchDetailsTO> cbsSchemeCaSbSchDetailsTOs;
    List<DeliquencyTableTO> deliqDetailsTOs;
    List<CbsSchemeLoanInterestDetailsTO> cbsSchemeLoanInterestDetails;
    //Added by Rohit
    List<CbsSchemeLoanPreEiSetupDetailsTO> cbsSchLoanPreSetupDet;
    List<CbsSchemeGeneralSchemeParameterMasterTO> cbsGenSchemeParamMasterDet;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AD getaD() {
        return aD;
    }

    public void setaD(AD aD) {
        this.aD = aD;
    }

    public CAD getcAD() {
        return cAD;
    }

    public void setcAD(CAD cAD) {
        this.cAD = cAD;
    }

    public AOM getaOM() {
        return aOM;
    }

    public void setaOM(AOM aOM) {
        this.aOM = aOM;
    }

    public CD getcD() {
        return cD;
    }

    public void setcD(CD cD) {
        this.cD = cD;
    }

    public CRBOSD getCrBosd() {
        return crBosd;
    }

    public void setCrBosd(CRBOSD crBosd) {
        this.crBosd = crBosd;
    }

    public CSDD getcSDD() {
        return cSDD;
    }

    public void setcSDD(CSDD cSDD) {
        this.cSDD = cSDD;
    }

    public DOIP getdOIP() {
        return dOIP;
    }

    public void setdOIP(DOIP dOIP) {
        this.dOIP = dOIP;
    }

    public DFD getdFD() {
        return dFD;
    }

    public void setdFD(DFD dFD) {
        this.dFD = dFD;
    }

    public DIDD getdIDD() {
        return dIDD;
    }

    public void setdIDD(DIDD dIDD) {
        this.dIDD = dIDD;
    }

    public GSHSD getgSHSD() {
        return gSHSD;
    }

    public void setgSHSD(GSHSD gSHSD) {
        this.gSHSD = gSHSD;
    }

    public DSPM getdSPM() {
        return dSPM;
    }

    public void setdSPM(DSPM dSPM) {
        this.dSPM = dSPM;
    }

    public ISCD getiSCD() {
        return iSCD;
    }

    public void setiSCD(ISCD iSCD) {
        this.iSCD = iSCD;
    }

    public LED getlED() {
        return lED;
    }

    public void setlED(LED lED) {
        this.lED = lED;
    }

    public SVI getsVI() {
        return sVI;
    }

    public void setsVI(SVI sVI) {
        this.sVI = sVI;
    }

    public LFDCW getlFDCW() {
        return lFDCW;
    }

    public void setlFDCW(LFDCW lFDCW) {
        this.lFDCW = lFDCW;
    }

    public LRCD getlRCD() {
        return lRCD;
    }

    public void setlRCD(LRCD lRCD) {
        this.lRCD = lRCD;
    }

    public SFCD getsFCD() {
        return sFCD;
    }

    public void setsFCD(SFCD sFCD) {
        this.sFCD = sFCD;
    }

    public TRCCW gettRCCW() {
        return tRCCW;
    }

    public void settRCCW(TRCCW tRCCW) {
        this.tRCCW = tRCCW;
    }

    public STRD getsTRD() {
        return sTRD;
    }

    public void setsTRD(STRD sTRD) {
        this.sTRD = sTRD;
    }

    public TEDCW gettEDCW() {
        return tEDCW;
    }

    public void settEDCW(TEDCW tEDCW) {
        this.tEDCW = tEDCW;
    }
    //Added by Ankit

    public DD getDdD() {
        return ddD;
    }

    public void setDdD(DD ddD) {
        this.ddD = ddD;
    }

    public FFDD getfFDD() {
        return fFDD;
    }

    public void setfFDD(FFDD fFDD) {
        this.fFDD = fFDD;
    }

    public LPD getlPD() {
        return lPD;
    }

    public void setlPD(LPD lPD) {
        this.lPD = lPD;
    }

    public LSD getlSD() {
        return lSD;
    }

    public void setlSD(LSD lSD) {
        this.lSD = lSD;
    }

    public SPM getsPM() {
        return sPM;
    }

    public void setsPM(SPM sPM) {
        this.sPM = sPM;
    }

    public LID getlID() {
        return lID;
    }

    public void setlID(LID lID) {
        this.lID = lID;
    }

    //Addition end here
    //Added by Rohit
    public LPESD getlPESD() {
        return lPESD;
    }

    public void setlPESD(LPESD lPESD) {
        this.lPESD = lPESD;
    }

    public PM getpM() {
        return pM;
    }

    public void setpM(PM pM) {
        this.pM = pM;
    }

    public boolean isPopUpFlag() {
        return popUpFlag;
    }

    public void setPopUpFlag(boolean popUpFlag) {
        this.popUpFlag = popUpFlag;
    }

    //Addition end here
    /** Creates a new instance of OperationSchemeMaster */
    public OperationSchemeMaster() {
    }

    public void formData() {
        try {

            System.out.println("For CAD[Customer Account Details] form processing");
            if (cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && cAD.getSchemeMaster().getPageType().equalsIgnoreCase("cad")) {
                cAD.refreshCADForm();
                cAD.enableCADForm();
            }
            if (cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && cAD.getSchemeMaster().getPageType().equalsIgnoreCase("cad")) {
                cAD.refreshCADForm();
                cAD.enableCADForm();
                cAD.cadFormData();
            }
            if (cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && cAD.getSchemeMaster().getPageType().equalsIgnoreCase("cad")) {
                cAD.refreshCADForm();
                cAD.cadFormData();
                cAD.disableCADForm();
            }
            System.out.println("For AOM[Account Open Matrix] form processing");
            if (aOM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && aOM.getSchemeMaster().getPageType().equalsIgnoreCase("aom")) {
                aOM.refreshAOMForm();
                aOM.enableAOMForm();

            }
            if (aOM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && aOM.getSchemeMaster().getPageType().equalsIgnoreCase("aom")) {
                aOM.refreshAOMForm();
                aOM.enableAOMForm();
                aOM.selectOpenMatrix();
            }
            if (aOM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && aOM.getSchemeMaster().getPageType().equalsIgnoreCase("aom")) {
                aOM.refreshAOMForm();
                aOM.selectOpenMatrix();
                aOM.disableAOMForm();
            }

            System.out.println("For CD[Currency Details] form processing");
            if (cD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && cD.getSchemeMaster().getPageType().equalsIgnoreCase("cd")) {
                cD.refreshCDForm();
                cD.enableCDForm();
                cD.resetAllLimit();

            }
            if (cD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && cD.getSchemeMaster().getPageType().equalsIgnoreCase("cd")) {
                cD.refreshCDForm();
                cD.enableCDForm();
                cD.selectCurrencyDetails();
            }
            if (cD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && cD.getSchemeMaster().getPageType().equalsIgnoreCase("cd")) {
                cD.refreshCDForm();
                cD.selectCurrencyDetails();
                cD.disableCDForm();
            }

            System.out.println("For CRBOSD[Cash Cr Bill and OverDraft Scheme details] form processing");
            if (crBosd.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && crBosd.getSchemeMaster().getPageType().equalsIgnoreCase("crbosd")) {
                crBosd.refreshCrBosdForm();
                crBosd.enableCrBosdForm();
                crBosd.resetAllLimit();

            }
            if (crBosd.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && crBosd.getSchemeMaster().getPageType().equalsIgnoreCase("crbosd")) {
                crBosd.refreshCrBosdForm();
                crBosd.enableCrBosdForm();
                crBosd.selectCrBosdDetails();
            }
            if (crBosd.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && crBosd.getSchemeMaster().getPageType().equalsIgnoreCase("crbosd")) {
                crBosd.refreshCrBosdForm();
                crBosd.selectCrBosdDetails();
                crBosd.disableCrBosdForm();
            }

            System.out.println("For CSDD[CBS Scheme Document Details] form processing");
            if (cSDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && cSDD.getSchemeMaster().getPageType().equalsIgnoreCase("csdd")) {
                cSDD.refreshCSDDForm();
                cSDD.getDocDetail().clear();
                cSDD.enableCSDDForm();

            }
            if (cSDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && cSDD.getSchemeMaster().getPageType().equalsIgnoreCase("csdd")) {
                cSDD.refreshCSDDForm();
                cSDD.enableCSDDForm();
                cSDD.getDocDetail().clear();
                cSDD.selectCSDDDetails();
            }
            if (cSDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && cSDD.getSchemeMaster().getPageType().equalsIgnoreCase("csdd")) {
                cSDD.refreshCSDDForm();
                cSDD.getDocDetail().clear();
                cSDD.selectCSDDDetails();
                cSDD.disableCSDDForm();
            }

            System.out.println("For DOIP[Deposit Overdue Interest Parameters] form processing");
            if (dOIP.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && dOIP.getSchemeMaster().getPageType().equalsIgnoreCase("doip")) {
                dOIP.refreshDOIPForm();
                dOIP.enableDOIPForm();

            }
            if (dOIP.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && dOIP.getSchemeMaster().getPageType().equalsIgnoreCase("doip")) {
                dOIP.refreshDOIPForm();
                dOIP.enableDOIPForm();
                dOIP.selectDOIPDetails();
            }
            if (dOIP.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && dOIP.getSchemeMaster().getPageType().equalsIgnoreCase("doip")) {
                dOIP.refreshDOIPForm();
                dOIP.selectDOIPDetails();
                dOIP.disableDOIPForm();
            }

            System.out.println("For DFD[Deposit Flow Details] form processing");
            if (dFD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && dFD.getSchemeMaster().getPageType().equalsIgnoreCase("dfd")) {
                dFD.refreshDFDForm();
                dFD.getDepFlow().clear();
                dFD.enableDFDForm();
                dFD.disableFlowCode = false;
            }
            if (dFD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && dFD.getSchemeMaster().getPageType().equalsIgnoreCase("dfd")) {
                dFD.refreshDFDForm();
                dFD.enableDFDForm();
                dFD.disableFlowCode = false;
                dFD.getDepFlow().clear();
                dFD.selectDFDDetails();
            }
            if (dFD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && dFD.getSchemeMaster().getPageType().equalsIgnoreCase("dfd")) {
                dFD.refreshDFDForm();
                dFD.getDepFlow().clear();
                dFD.selectDFDDetails();
                dFD.disableDFDForm();
                dFD.disableFlowCode = true;
            }

            System.out.println("For AD[Asset Details] form processing");
            if (aD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && aD.getSchemeMaster().getPageType().equalsIgnoreCase("ad")) {
                aD.refreshADForm();
                aD.getAsset().clear();
                aD.enableADForm();
                aD.disablePDP = false;

            }
            if (aD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && aD.getSchemeMaster().getPageType().equalsIgnoreCase("ad")) {
                aD.refreshADForm();
                aD.enableADForm();
                aD.disablePDP = false;
                aD.getAsset().clear();
                aD.selectADDetails();
            }
            if (aD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && aD.getSchemeMaster().getPageType().equalsIgnoreCase("ad")) {
                aD.refreshADForm();
                aD.getAsset().clear();
                aD.selectADDetails();
                aD.disableADForm();
                aD.disablePDP = true;
            }

            System.out.println("For DIDD[Deposit Interest Definition Details] form processing");
            if (dIDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && dIDD.getSchemeMaster().getPageType().equalsIgnoreCase("didd")) {
                dIDD.refreshDIDDForm();
                dIDD.getDeposit().clear();
                dIDD.enableDIDDForm();
                dIDD.disableInterest = false;

            }
            if (dIDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && dIDD.getSchemeMaster().getPageType().equalsIgnoreCase("didd")) {
                dIDD.refreshDIDDForm();
                dIDD.enableDIDDForm();
                dIDD.disableInterest = false;
                dIDD.getDeposit().clear();
                dIDD.selectDIDDDetails();
            }
            if (dIDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && dIDD.getSchemeMaster().getPageType().equalsIgnoreCase("didd")) {
                dIDD.refreshDIDDForm();
                dIDD.getDeposit().clear();
                dIDD.selectDIDDDetails();
                dIDD.disableDIDDForm();
                dIDD.disableInterest = true;
            }

            System.out.println("For GSHSD[Gl Sub Head Scheme Details] form processing");
            if (gSHSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && gSHSD.getSchemeMaster().getPageType().equalsIgnoreCase("gshsd")) {
                gSHSD.refreshGSHSDForm();
                gSHSD.getGlHead().clear();
                gSHSD.enableGSHSDForm();
            }
            if (gSHSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && gSHSD.getSchemeMaster().getPageType().equalsIgnoreCase("gshsd")) {
                gSHSD.refreshGSHSDForm();
                gSHSD.enableGSHSDForm();
                gSHSD.getGlHead().clear();
                gSHSD.selectGSHSDDetails();
            }
            if (gSHSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && gSHSD.getSchemeMaster().getPageType().equalsIgnoreCase("gshsd")) {
                gSHSD.refreshGSHSDForm();
                gSHSD.getGlHead().clear();
                gSHSD.selectGSHSDDetails();
                gSHSD.disableGSHSDForm();
            }

            System.out.println("For DSPM[Deposit Scheme Parameters Maintenance] form processing");
            if (dSPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && dSPM.getSchemeMaster().getPageType().equalsIgnoreCase("dspm")) {
                dSPM.refreshDspmForm();
                dSPM.enableDspmForm();
            }
            if (dSPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && dSPM.getSchemeMaster().getPageType().equalsIgnoreCase("dspm")) {
                dSPM.refreshDspmForm();
                dSPM.enableDspmForm();
                dSPM.selectDspmDetails();
            }
            if (dSPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && dSPM.getSchemeMaster().getPageType().equalsIgnoreCase("dspm")) {
                dSPM.refreshDspmForm();
                dSPM.selectDspmDetails();
                dSPM.disableDspmForm();
            }

            System.out.println("For ISCD[Interest Or Service Charge Details] form processing");
            if (iSCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && iSCD.getSchemeMaster().getPageType().equalsIgnoreCase("iscd")) {
                iSCD.refreshIscdForm();
                iSCD.enableIscdForm();
            }
            if (iSCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && iSCD.getSchemeMaster().getPageType().equalsIgnoreCase("iscd")) {
                iSCD.refreshIscdForm();
                iSCD.enableIscdForm();
                iSCD.selectIscdDetails();
            }
            if (iSCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && iSCD.getSchemeMaster().getPageType().equalsIgnoreCase("iscd")) {
                iSCD.refreshIscdForm();
                iSCD.selectIscdDetails();
                iSCD.disableIscdForm();
            }

            System.out.println("For LED[Loan Exception Details] form processing");
            if (lED.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && lED.getSchemeMaster().getPageType().equalsIgnoreCase("led")) {
                lED.refreshLedForm();
                lED.enableLedForm();
            }
            if (lED.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && lED.getSchemeMaster().getPageType().equalsIgnoreCase("led")) {
                lED.refreshLedForm();
                lED.enableLedForm();
                lED.selectLedDetails();
            }
            if (lED.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && lED.getSchemeMaster().getPageType().equalsIgnoreCase("led")) {
                lED.refreshLedForm();
                lED.selectLedDetails();
                lED.disableLedForm();
            }

            System.out.println("For SVI[Scheme Valid Instrument] form processing");
            if (sVI.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && sVI.getSchemeMaster().getPageType().equalsIgnoreCase("svi")) {
                sVI.refreshSviForm();
                sVI.getSchemeValid().clear();
                sVI.enableSviForm();
            }
            if (sVI.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && sVI.getSchemeMaster().getPageType().equalsIgnoreCase("svi")) {
                sVI.refreshSviForm();
                sVI.enableSviForm();
                sVI.getSchemeValid().clear();
                sVI.selectSviDetails();
            }
            if (sVI.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && sVI.getSchemeMaster().getPageType().equalsIgnoreCase("svi")) {
                sVI.refreshSviForm();
                sVI.getSchemeValid().clear();
                sVI.selectSviDetails();
                sVI.disableSviForm();
            }

            System.out.println("For LFDCW[Ledger Folio Details Currency Wise] form processing");
            if (lFDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && lFDCW.getSchemeMaster().getPageType().equalsIgnoreCase("lfdcw")) {
                lFDCW.refreshLfdcwForm();
                lFDCW.getLedger().clear();
                lFDCW.enableLfdcwForm();
                lFDCW.disableStartAmount = false;
                lFDCW.disableendAmount = false;
            }
            if (lFDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && lFDCW.getSchemeMaster().getPageType().equalsIgnoreCase("lfdcw")) {
                lFDCW.refreshLfdcwForm();
                lFDCW.enableLfdcwForm();
                lFDCW.disableStartAmount = false;
                lFDCW.disableendAmount = false;
                lFDCW.getLedger().clear();
                lFDCW.selectLfdcwDetails();
            }
            if (lFDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && lFDCW.getSchemeMaster().getPageType().equalsIgnoreCase("lfdcw")) {
                lFDCW.refreshLfdcwForm();
                lFDCW.getLedger().clear();
                lFDCW.selectLfdcwDetails();
                lFDCW.disableLfdcwForm();
                lFDCW.disableStartAmount = true;
                lFDCW.disableendAmount = true;
            }

            System.out.println("For LRCD[Loan Repayment Cycle Defination] form processing");
            if (lRCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && lRCD.getSchemeMaster().getPageType().equalsIgnoreCase("lrcd")) {
                lRCD.refreshLrcdForm();
                lRCD.getLoanRepay().clear();
            }
            if (lRCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && lRCD.getSchemeMaster().getPageType().equalsIgnoreCase("lrcd")) {
                lRCD.refreshLrcdForm();
                lRCD.getLoanRepay().clear();
                lRCD.selectLrcdDetails();
            }
            if (lRCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && lRCD.getSchemeMaster().getPageType().equalsIgnoreCase("lrcd")) {
                lRCD.refreshLrcdForm();
                lRCD.getLoanRepay().clear();
                lRCD.selectLrcdDetails();
            }

            System.out.println("For SFCD[Scheme Fee or Charges Details] form processing");
            if (sFCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && sFCD.getSchemeMaster().getPageType().equalsIgnoreCase("sfcd")) {
                sFCD.refreshSfcdForm();
                sFCD.getFeeCharges().clear();
                sFCD.enableSfcdForm();
                sFCD.chargesTypedisable = false;
            }
            if (sFCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && sFCD.getSchemeMaster().getPageType().equalsIgnoreCase("sfcd")) {
                sFCD.refreshSfcdForm();
                sFCD.enableSfcdForm();
                sFCD.chargesTypedisable = false;
                sFCD.getFeeCharges().clear();
                sFCD.selectSfcdDetails();
            }
            if (sFCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && sFCD.getSchemeMaster().getPageType().equalsIgnoreCase("sfcd")) {
                sFCD.refreshSfcdForm();
                sFCD.getFeeCharges().clear();
                sFCD.selectSfcdDetails();
                sFCD.disableSfcdForm();
                sFCD.chargesTypedisable = true;
            }

            System.out.println("For TRCCW[Transaction Report Code Currency Wise");
            if (tRCCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && tRCCW.getSchemeMaster().getPageType().equalsIgnoreCase("trccw")) {
                tRCCW.refreshTrccwForm();
                tRCCW.getTranRepCodeWise().clear();
                tRCCW.enableTrccwForm();
                tRCCW.tranReportCodeDisable = false;
            }
            if (tRCCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && tRCCW.getSchemeMaster().getPageType().equalsIgnoreCase("trccw")) {
                tRCCW.refreshTrccwForm();
                tRCCW.enableTrccwForm();
                tRCCW.tranReportCodeDisable = false;
                tRCCW.getTranRepCodeWise().clear();
                tRCCW.selectTrccwDetails();
            }
            if (tRCCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && tRCCW.getSchemeMaster().getPageType().equalsIgnoreCase("trccw")) {
                tRCCW.refreshTrccwForm();
                tRCCW.getTranRepCodeWise().clear();
                tRCCW.selectTrccwDetails();
                tRCCW.disableTrccwForm();
                tRCCW.tranReportCodeDisable = true;
            }

            System.out.println("For STRD[Scheme Tod Reference Details");
            if (sTRD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && sTRD.getSchemeMaster().getPageType().equalsIgnoreCase("strd")) {
                sTRD.refreshStrdForm();
                sTRD.getTodRef().clear();
                sTRD.enableStrdForm();
                sTRD.disableFlagReferenceType = false;
            }
            if (sTRD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && sTRD.getSchemeMaster().getPageType().equalsIgnoreCase("strd")) {
                sTRD.refreshStrdForm();
                sTRD.enableStrdForm();
                sTRD.disableFlagReferenceType = false;
                sTRD.getTodRef().clear();
                sTRD.selectStrdDetails();
            }
            if (sTRD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && sTRD.getSchemeMaster().getPageType().equalsIgnoreCase("strd")) {
                sTRD.refreshStrdForm();
                sTRD.getTodRef().clear();
                sTRD.selectStrdDetails();
                sTRD.disableStrdForm();
                sTRD.disableFlagReferenceType = true;
            }

            System.out.println("For TEDCW[TOD Exception Details Currency Wise");
            if (tEDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && tEDCW.getSchemeMaster().getPageType().equalsIgnoreCase("tedcw")) {
                tEDCW.refreshTEDCWForm();
                tEDCW.getSchemeTod().clear();
                tEDCW.enableTEDCWForm();
            }
            if (tEDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && tEDCW.getSchemeMaster().getPageType().equalsIgnoreCase("tedcw")) {
                tEDCW.refreshTEDCWForm();
                tEDCW.enableTEDCWForm();
                tEDCW.getSchemeTod().clear();
                tEDCW.selectTEDCWDetails();
            }
            if (tEDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && tEDCW.getSchemeMaster().getPageType().equalsIgnoreCase("tedcw")) {
                tEDCW.refreshTEDCWForm();
                tEDCW.getSchemeTod().clear();
                tEDCW.selectTEDCWDetails();
                tEDCW.disableTEDCWForm();
            }

            /***********Added by Ankit**************************/
            System.out.println("For FFDD[Flexi Fixed Deposits Details] form processing");
            if (fFDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && fFDD.getSchemeMaster().getPageType().equalsIgnoreCase("ffdd")) {
                fFDD.refreshFFDDForm();
                fFDD.enableFFDDForm();
            }
            if (fFDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && fFDD.getSchemeMaster().getPageType().equalsIgnoreCase("ffdd")) {
                fFDD.refreshFFDDForm();
                fFDD.enableFFDDForm();
                fFDD.selectFFDDDetails();
            }
            if (fFDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && fFDD.getSchemeMaster().getPageType().equalsIgnoreCase("ffdd")) {
                fFDD.refreshFFDDForm();
                fFDD.selectFFDDDetails();
                fFDD.disableFFDDForm();
            }

            System.out.println("For DD[Delinquency  Details] form processing");
            if (ddD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && ddD.getSchemeMaster().getPageType().equalsIgnoreCase("dd")) {
                ddD.refreshDDForm();
                ddD.getDeliqDetails().clear();
                ddD.enableDDForm();
                ddD.dpFlag = false;
            }
            if (ddD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && ddD.getSchemeMaster().getPageType().equalsIgnoreCase("dd")) {
                ddD.refreshDDForm();
                ddD.enableDDForm();
                ddD.dpFlag = false;
                ddD.getDeliqDetails().clear();
                ddD.getDDDetails();
            }
            if (ddD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && ddD.getSchemeMaster().getPageType().equalsIgnoreCase("dd")) {
                ddD.refreshDDForm();
                ddD.getDeliqDetails().clear();
                ddD.getDDDetails();
                ddD.disableDDForm();
                ddD.dpFlag = true;
            }

            System.out.println("For LPD[Loan Prepayment Details] form processing");
            if (lPD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && lPD.getSchemeMaster().getPageType().equalsIgnoreCase("lpd")) {
                lPD.refreshLPDForm();
                lPD.enableLPDForm();
            }
            if (lPD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && lPD.getSchemeMaster().getPageType().equalsIgnoreCase("lpd")) {
                lPD.refreshLPDForm();
                lPD.enableLPDForm();
                lPD.selectLPDDetails();
            }
            if (lPD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && lPD.getSchemeMaster().getPageType().equalsIgnoreCase("lpd")) {
                lPD.refreshLPDForm();
                lPD.selectLPDDetails();
                lPD.disableLPDForm();
            }

            System.out.println("For LSD[Loan Scheme Details] form processing");
            if (lSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && lSD.getSchemeMaster().getPageType().equalsIgnoreCase("lsd")) {
                lSD.refreshLSDForm();
                lSD.enableLSDForm();
            }
            if (lSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && lSD.getSchemeMaster().getPageType().equalsIgnoreCase("lsd")) {
                lSD.refreshLSDForm();
                lSD.enableLSDForm();
                lSD.selectLSDDetails();
            }
            if (lSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && lSD.getSchemeMaster().getPageType().equalsIgnoreCase("lsd")) {
                lSD.refreshLSDForm();
                lSD.selectLSDDetails();
                lSD.disableLSDForm();
            }

            System.out.println("For SPM[SB Scheme Parameters Maintenance] form processing");
            if (sPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && sPM.getSchemeMaster().getPageType().equalsIgnoreCase("spm")) {
                sPM.refreshSPMForm();
                sPM.enableSPMForm();
            }
            if (sPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && sPM.getSchemeMaster().getPageType().equalsIgnoreCase("spm")) {
                sPM.refreshSPMForm();
                sPM.enableSPMForm();
                sPM.selectSPMDetails();
            }
            if (sPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && sPM.getSchemeMaster().getPageType().equalsIgnoreCase("spm")) {
                sPM.refreshSPMForm();
                sPM.selectSPMDetails();
                sPM.disableSPMForm();
            }

            System.out.println("For LID[Loan Interest Details] form processing");
            if (lID.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")
                    && lID.getSchemeMaster().getPageType().equalsIgnoreCase("lid")) {
                lID.refreshLidForm();
                lID.enableLidForm();
            }
            if (lID.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")
                    && lID.getSchemeMaster().getPageType().equalsIgnoreCase("lid")) {
                lID.refreshLidForm();
                lID.enableLidForm();
                lID.selectsschemeLoanInterestDetails();
            }
            if (lID.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I")
                    && lID.getSchemeMaster().getPageType().equalsIgnoreCase("lid")) {
                lID.refreshLidForm();
                lID.selectsschemeLoanInterestDetails();
                lID.disableLidForm();
            }

            /**Added By Rohit***/
            System.out.println("For LPESD[Loan Pre Ei Setup Details] form processing");
            if (lPESD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && lPESD.getSchemeMaster().getPageType().equalsIgnoreCase("lpesd")) {
                lPESD.refreshLpesdForm();
                lPESD.enableLpesdForm();
            }
            if (lPESD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && lPESD.getSchemeMaster().getPageType().equalsIgnoreCase("lpesd")) {
                lPESD.refreshLpesdForm();
                lPESD.enableLpesdForm();
                lPESD.selectLpesdDetails();
            }
            if (lPESD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && lPESD.getSchemeMaster().getPageType().equalsIgnoreCase("lpesd")) {
                lPESD.refreshLpesdForm();
                lPESD.selectLpesdDetails();
                lPESD.disableLpesdForm();
            }

            System.out.println("For PM[Parameter Master] form processing");
            if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") && pM.getSchemeMaster().getPageType().equalsIgnoreCase("pm")) {
                pM.refreshPmForm();
                pM.enablePmForm();
            }
            if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M") && pM.getSchemeMaster().getPageType().equalsIgnoreCase("pm")) {
                pM.refreshPmForm();
                pM.enablePmForm();
                pM.selectPmDetails();
            }
            if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("I") && pM.getSchemeMaster().getPageType().equalsIgnoreCase("pm")) {
                pM.refreshPmForm();
                pM.selectPmDetails();
                pM.disablePmForm();
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void addModifyData() {
        try {
            System.out.println("In AddModifyData method for CAD form processing");
            if ((cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && cAD.getSchemeMaster().getPageType().equalsIgnoreCase("cad")) {
                cbsSchemeCustAccountDetailsTOs = new ArrayList<CbsSchemeCustAccountDetailsTO>();
                CbsSchemeCustAccountDetailsTO cbsSchemeCustAccountDetailsTO = new CbsSchemeCustAccountDetailsTO();
                cbsSchemeCustAccountDetailsTO.setSchemeCode(cAD.getSchemeMaster().getSchemeCode());
                cbsSchemeCustAccountDetailsTO.setSchemeType(cAD.getSchemeMaster().getSchemeType());
                cbsSchemeCustAccountDetailsTO.setChequeNotAllowed(cAD.getChequeNotAllowed());

                cbsSchemeCustAccountDetailsTO.setChequeNotIssued(cAD.getChequeNotIssued());
                cbsSchemeCustAccountDetailsTO.setChequeIssuedToMinor(cAD.getChequeIssuedToMinor());
                cbsSchemeCustAccountDetailsTO.setChequeStopped(cAD.getChequeStopped());
                cbsSchemeCustAccountDetailsTO.setChequeCautioned(cAD.getChequeCautioned());

                cbsSchemeCustAccountDetailsTO.setIntroducerNotCust(cAD.getIntroducerNotCust());
                cbsSchemeCustAccountDetailsTO.setIntroducerNewCust(cAD.getIntroducerNewCust());
                cbsSchemeCustAccountDetailsTO.setEmployeeOwnAccount(cAD.getEmployeesOwnAccount());
                cbsSchemeCustAccountDetailsTO.setSiPerfixCharges(cAD.getsIPerfixCharges());

                cbsSchemeCustAccountDetailsTO.setDrAgainstCc(cAD.getDrAgainstCC());
                cbsSchemeCustAccountDetailsTO.setClsPendingJobs(cAD.getClsPendingJobs());
                cbsSchemeCustAccountDetailsTO.setClsPendingSi(cAD.getClsPendingSI());
                cbsSchemeCustAccountDetailsTO.setClsPendingCc(cAD.getClsPendingCC());

                cbsSchemeCustAccountDetailsTO.setClsPendingChq(cAD.getClsPendingChq());
                cbsSchemeCustAccountDetailsTO.setClsPendingProxy(cAD.getClsPendingProxy());
                cbsSchemeCustAccountDetailsTO.setChequeIssuedButNotAck(cAD.getChequeIssuedButNotAck());
                cbsSchemeCustAccountDetailsTO.setChequeUnusable(cAD.getChequeUnusable());
                cbsSchemeCustAccountDetailsTO.setChequeStoppedButNotVerified(cAD.getChequeStoppedButNotVerified());
                cbsSchemeCustAccountDetailsTOs.add(cbsSchemeCustAccountDetailsTO);
                if (cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(cAD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(cAD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for AOM form processing");
            if ((aOM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || aOM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && aOM.getSchemeMaster().getPageType().equalsIgnoreCase("aom")) {
                cbsSchemeAccountOpenMatrixTOs = new ArrayList<CbsSchemeAccountOpenMatrixTO>();
                CbsSchemeAccountOpenMatrixTO cbsSchemeAccountOpenMatrixTO = new CbsSchemeAccountOpenMatrixTO();
                cbsSchemeAccountOpenMatrixTO.setSchemeCode(aOM.getSchemeMaster().getSchemeCode());
                cbsSchemeAccountOpenMatrixTO.setSchemeType(aOM.getSchemeMaster().getSchemeType());

                cbsSchemeAccountOpenMatrixTO.setMatrixDescription(aOM.getMatrixDesc());
                cbsSchemeAccountOpenMatrixTO.setValidInvalidFlag(aOM.getValidInvalidFlag());
                cbsSchemeAccountOpenMatrixTO.setCustStatus(aOM.getCustStatus());
                cbsSchemeAccountOpenMatrixTO.setCustSector(aOM.getCustSector());

                cbsSchemeAccountOpenMatrixTO.setCustSubSector(aOM.getCustSubSector());
                cbsSchemeAccountOpenMatrixTO.setCustTypeCode(aOM.getCustTypeCode());
                cbsSchemeAccountOpenMatrixTO.setCustConstitution(aOM.getCustConstitution());
                cbsSchemeAccountOpenMatrixTO.setCusTempId(aOM.getCustEmpId());

                cbsSchemeAccountOpenMatrixTO.setCustOtherBank(aOM.getCustOtherBank());
                cbsSchemeAccountOpenMatrixTO.setModeOfOperation(aOM.getModeOfOperation());
                cbsSchemeAccountOpenMatrixTO.setGuaranteeCover(aOM.getGuaranteeCover());
                cbsSchemeAccountOpenMatrixTO.setNatureOfAdvance(aOM.getNatureOfAdvance());

                cbsSchemeAccountOpenMatrixTO.setTypeOfAdvance(aOM.getTypeOfAdvance());
                cbsSchemeAccountOpenMatrixTO.setModeOfAdvance(aOM.getModeOfAdvance());
                cbsSchemeAccountOpenMatrixTO.setPurposeOfAdvance(aOM.getPurposeOfAdvance());
                cbsSchemeAccountOpenMatrixTO.setCustMinorFlag(aOM.getCustMinorFlag());

                cbsSchemeAccountOpenMatrixTO.setChequeAllowedFlag(aOM.getChequedAllowedFlag());
                cbsSchemeAccountOpenMatrixTO.setAccountTurnDetail(aOM.getAccountTurnDetail());
                cbsSchemeAccountOpenMatrixTO.setAccountOwnership(aOM.getAccountOwnership());
                cbsSchemeAccountOpenMatrixTO.setDelFlag(aOM.getDelFlagmat());
                cbsSchemeAccountOpenMatrixTOs.add(cbsSchemeAccountOpenMatrixTO);
                if (aOM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(aOM.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (aOM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(aOM.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for CD form processing");
            if ((cD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || cD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && cD.getSchemeMaster().getPageType().equalsIgnoreCase("cd")) {
                cbsSchemeCurrencyDetailsTOs = new ArrayList<CbsSchemeCurrencyDetailsTO>();
                CbsSchemeCurrencyDetailsTO cbsSchemeCurrencyDetailsTO = new CbsSchemeCurrencyDetailsTO();
                CbsSchemeCurrencyDetailsPKTO cbsSchemeCurrencyDetailsPKTO = new CbsSchemeCurrencyDetailsPKTO();
                cbsSchemeCurrencyDetailsPKTO.setSchemeCode(cD.getSchemeMaster().getSchemeCode());
                cbsSchemeCurrencyDetailsPKTO.setCurrencyCode(cD.getSchemeMaster().getCurrencyType());
                cbsSchemeCurrencyDetailsTO.setCbsSchemeCurrencyDetailsPKTO(cbsSchemeCurrencyDetailsPKTO);

                cbsSchemeCurrencyDetailsTO.setSchemeType(cD.getSchemeMaster().getSchemeType());
                cbsSchemeCurrencyDetailsTO.setAcctReportCode(cD.getAcctReportCode());
                cbsSchemeCurrencyDetailsTO.setDefaultNationalRate(cD.getDefaultNationalRate());
                cbsSchemeCurrencyDetailsTO.setRevaluationAcctPlaceholder(cD.getRevaluationAcctPlaceholder());

                cbsSchemeCurrencyDetailsTO.setCrIntRateCode(cD.getCrIntRateCode());
                cbsSchemeCurrencyDetailsTO.setDrIntRateCode(cD.getDrIntRateCode());
                cbsSchemeCurrencyDetailsTO.setDefaultCustPreferentialToAcct(cD.getDefaultCustPreferentialToAcct());
                cbsSchemeCurrencyDetailsTO.setAcctOpenEvent(cD.getAcctOpenEvent());

                cbsSchemeCurrencyDetailsTO.setAcctMatchEvent(cD.getAcctMatchEvent());
                cbsSchemeCurrencyDetailsTO.setAcctClosureEvent(cD.getAcctClosureEvent());
                cbsSchemeCurrencyDetailsTO.setLedgerFolioCalcEvent(cD.getLedgerFolioCalcEvent());
                cbsSchemeCurrencyDetailsTO.setAdhocPassSheetPrintEvent(cD.getAdhocPassSheetPrintEvent());

                cbsSchemeCurrencyDetailsTO.setRegPassSheetPrintEvent(cD.getRegPassSheetPrintEvent());
                cbsSchemeCurrencyDetailsTO.setCommitmentEvent(cD.getCommitmentEvent());
                cbsSchemeCurrencyDetailsTO.setCrCashLimit(new BigDecimal(cD.getCrCashLimit()));
                cbsSchemeCurrencyDetailsTO.setDrCashLimit(new BigDecimal(cD.getDrCashLimit()));

                cbsSchemeCurrencyDetailsTO.setCrClgLimit(new BigDecimal(cD.getCrClgLimit()));
                cbsSchemeCurrencyDetailsTO.setDrClgLimit(new BigDecimal(cD.getDrClgLimit()));
                cbsSchemeCurrencyDetailsTO.setCrXrefLimit(new BigDecimal(cD.getCrXrefLimit()));
                cbsSchemeCurrencyDetailsTO.setDrXrefLimit(new BigDecimal(cD.getDrXrefLimit()));

                cbsSchemeCurrencyDetailsTO.setDrCashAbnLimit(new BigDecimal(cD.getDrCashAbnLimit()));
                cbsSchemeCurrencyDetailsTO.setDrClgAbnLimit(new BigDecimal(cD.getDrClgAbnLimit()));
                cbsSchemeCurrencyDetailsTO.setDrxRefAbnLimit(new BigDecimal(cD.getDrXrefAbnLimit()));
                cbsSchemeCurrencyDetailsTO.setNewAccountAbnTranAmt(new BigDecimal(cD.getNewAccountAbnTranAmt()));

                cbsSchemeCurrencyDetailsTO.setInterestTableCode(cD.getInterestTableCode1());
                cbsSchemeCurrencyDetailsTO.setCrMin(new BigDecimal(cD.getCrMin()));
                cbsSchemeCurrencyDetailsTO.setCrMax(new BigDecimal(cD.getCrMax()));
                cbsSchemeCurrencyDetailsTO.setDrMin(new BigDecimal(cD.getDrMin()));

                cbsSchemeCurrencyDetailsTO.setDrMax(new BigDecimal(cD.getDrMax()));
                cbsSchemeCurrencyDetailsTO.setCrDaysInAYear(cD.getCrDaysInaYear());
                cbsSchemeCurrencyDetailsTO.setCrLeapYearAdjustment(cD.getCrLeapYearAdjustment());
                cbsSchemeCurrencyDetailsTO.setDrDaysinaYear(cD.getDrDaysinaYear());

                cbsSchemeCurrencyDetailsTO.setDrLeapYearAdjustment(cD.getDrLeapYearAdjustment());
                cbsSchemeCurrencyDetailsTO.setMinIntPaidAmt(new BigDecimal(cD.getMinIntPaidAmt()));
                cbsSchemeCurrencyDetailsTO.setMinIntCollAmt(new BigDecimal(cD.getMinIntCollAmt()));
                cbsSchemeCurrencyDetailsTO.setBookAdvanceInt(cD.getBookAdvanceInt());

                cbsSchemeCurrencyDetailsTO.setMicrChrgEvent(cD.getmICRChrgEvent());
                cbsSchemeCurrencyDetailsTO.setResidualIntAdjustmentAmountForBooking(new BigDecimal(cD.getResidualIntAdjustmentAmountforBooking()));
                cbsSchemeCurrencyDetailsTO.setWithoutTaxAmountRndSt(cD.getWithoutTaxAmountRndSt());
                cbsSchemeCurrencyDetailsTO.setWithoutTaxAmountRndAmount(new BigDecimal(cD.getWithoutTaxAmountRndAmount()));

                cbsSchemeCurrencyDetailsTO.setIntPrdRndStCr(cD.getIntPrdRndStCr());
                cbsSchemeCurrencyDetailsTO.setIntPrdRndAmountCr(new BigDecimal(cD.getIntPrdRndAmountCr()));
                cbsSchemeCurrencyDetailsTO.setIntPrdRoundingStartDr(cD.getIntPrdRoundingStartDr());
                cbsSchemeCurrencyDetailsTO.setIntPrdRoundingAmtDr(new BigDecimal(cD.getIntPrdRoundingAmtDr()));

                cbsSchemeCurrencyDetailsTO.setIntAmtRoundingStartCr(cD.getIntAmRoundingStartCr());
                cbsSchemeCurrencyDetailsTO.setIntAmtRoundingAmtCr(new BigDecimal(cD.getIntAmtRoundingAmtCr()));
                cbsSchemeCurrencyDetailsTO.setIntAmtRoundingAmtDr(new BigDecimal(cD.getIntAmtRoundingAmtDr()));
                cbsSchemeCurrencyDetailsTO.setIntAmtRoundingAmtDr(new BigDecimal(cD.getIntAmtRoundingAmtDr()));

                cbsSchemeCurrencyDetailsTO.setIntPaidRptCode(cD.getIntPaidRptCode());
                cbsSchemeCurrencyDetailsTO.setIntCollRptCode(cD.getIntCollRptCode());
                cbsSchemeCurrencyDetailsTO.setIntDrRptCode(cD.getIntDrRptCode());
                cbsSchemeCurrencyDetailsTO.setIntCrRptCode(cD.getIntCrRptCode());

                cbsSchemeCurrencyDetailsTO.setTaxCollRptCode(cD.getTaxCollRptCode());
                cbsSchemeCurrencyDetailsTO.setTaxCollAccountPlaceholder(cD.getTaxCollAccountPlaceholder());
                cbsSchemeCurrencyDetailsTO.setTaxFlag(cD.getTaxFlag());
                cbsSchemeCurrencyDetailsTO.setIncludeFloorLimitForTax(new BigDecimal(cD.getIncludeFloorLimitForTax()));

                cbsSchemeCurrencyDetailsTO.setProportionateFloorLimit(cD.getProportionateFloorLimit());
                cbsSchemeCurrencyDetailsTO.setWithoutTaxFlrLim(new BigDecimal(cD.getWithoutTaxFlrLim()));
                cbsSchemeCurrencyDetailsTO.setWithholdingTax(new BigDecimal(cD.getWithHoldingTax()));
                cbsSchemeCurrencyDetailsTO.setWtaxMaturityAdjReqd(cD.getwTAXMaturityAdjReqd());

                cbsSchemeCurrencyDetailsTO.setEndOfDayBalCheck(cD.getEndofDayBalCheck());
                cbsSchemeCurrencyDetailsTO.setEndOfDayMinBalance(new BigDecimal(cD.getEndOfDayMinBalance()));
                cbsSchemeCurrencyDetailsTO.setDrcrInd(cD.getDrCrInd());
                cbsSchemeCurrencyDetailsTO.setEndOfDayMaxBalance(new BigDecimal(cD.getEndOfDayMaxBalance()));

                cbsSchemeCurrencyDetailsTO.setDrcrInd1(cD.getDrCrInd1());
                cbsSchemeCurrencyDetailsTO.setEodMinBalanceExceptionCode(cD.geteODMinBalanceExceptionCode());
                cbsSchemeCurrencyDetailsTO.setEodMaxBalanceExceptionCode(cD.geteODMaxBalanceExceptionCode());
                cbsSchemeCurrencyDetailsTO.setCashLimitDr(cD.getCashLimitDr());

                cbsSchemeCurrencyDetailsTO.setClgLimitDr(cD.getClgLimitDr());
                cbsSchemeCurrencyDetailsTO.setTransferLimitDr(cD.getTransferLimitDr());
                cbsSchemeCurrencyDetailsTO.setCashLimitCr(cD.getCashLimitCr());
                cbsSchemeCurrencyDetailsTO.setClgLimitCr(cD.getClgLimitCr());

                cbsSchemeCurrencyDetailsTO.setTransferLimitCr(cD.getTransferLimitCr());
                cbsSchemeCurrencyDetailsTO.setLateCashTran(cD.getLateCashTran());
                cbsSchemeCurrencyDetailsTO.setTranAmtLimit(cD.getTranAmtLimit());
                cbsSchemeCurrencyDetailsTO.setDrTranAmtNotAllowed(cD.getDrTranAmtNotAllowed());

                cbsSchemeCurrencyDetailsTO.setCrTranAmtNotAllowed(cD.getCrTranAmtNotAllowed());
                cbsSchemeCurrencyDetailsTO.setMinBalanceEvent(cD.getMinBalanceEvent());
                cbsSchemeCurrencyDetailsTO.setAcctMinBalance(new BigDecimal(cD.getAcctMinBalance()));
                cbsSchemeCurrencyDetailsTO.setSweepMinBalance(new BigDecimal(cD.getSweepMinBalance()));

                cbsSchemeCurrencyDetailsTO.setMinChargePeriod(new BigDecimal(cD.getMinChargePeriod()));
                cbsSchemeCurrencyDetailsTO.setPenalChargePeriod(new BigDecimal(cD.getPenalChargePeriod()));
                cbsSchemeCurrencyDetailsTO.setMinBalanceServiceChrg(new BigDecimal(cD.getMinBalanceServiceChrg()));
                cbsSchemeCurrencyDetailsTO.setRegularIntCertificatePrintingEvent(cD.getRegularIntCertificatePrintingEvent());
                cbsSchemeCurrencyDetailsTO.setAdhocIntCertificatePrintingEvent(cD.getAdhocIntCertificatePrintingEvent());
                cbsSchemeCurrencyDetailsTOs.add(cbsSchemeCurrencyDetailsTO);
                if (cD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(cD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (cD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(cD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for CRBOSD form processing");
            if ((crBosd.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || crBosd.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && crBosd.getSchemeMaster().getPageType().equalsIgnoreCase("crbosd")) {
                cbsSchemeCashCrBillsAndOverdraftDetailsTOs = new ArrayList<CbsSchemeCashCrBillsAndOverdraftDetailsTO>();
                CbsSchemeCashCrBillsAndOverdraftDetailsTO cbsSchemeCashCrBillsAndOverdraftDetailsTO = new CbsSchemeCashCrBillsAndOverdraftDetailsTO();
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setSchemeCode(crBosd.getSchemeMaster().getSchemeCode());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setSchemeType(crBosd.getSchemeMaster().getSchemeType());

                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setCurrencyCode(crBosd.getSchemeMaster().getCurrencyType());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setMaxSanctionLimit(new BigDecimal(crBosd.getMaxSanction()));
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setDebitBalanceLimit(new BigDecimal(crBosd.getDebitBalance()));
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setLedgerFolioChargeOrFolio(Long.parseLong(crBosd.getLedgerFolio()));

                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setInactiveAccountAbrnmlTranLimit(new BigDecimal(crBosd.getInactiveAccount()));
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setDormantAccountAbrnmlTranLimit(new BigDecimal(crBosd.getDormantAccount()));
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setMaximumPenalInterest(new BigDecimal(crBosd.getMaximunPenal()));
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setDurationtoMarkAccountAsInactive(crBosd.getDurationToMark());

                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setDurationFromInactiveToDormant(crBosd.getDurationFrom());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setDormantAccountChargeEvent(crBosd.getDormantAccountTranlimit());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setInactiveAccountChargeEvent(crBosd.getInactiveAccountabrml());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setAllowSweeps(crBosd.getAllowSweeps());

                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setDebitAgainstUnclearBalance(crBosd.getDebitAgainst());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setDebitBalanceLimitExec(crBosd.getDebitBalanceabrnml());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setBpPtranOutsideBills(crBosd.getbPPtranOutside());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setSancOrDisbExceedExpOrdOrDc(crBosd.getSancOrDisbExceed());

                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setSancOrDisbWithoutExpOrdOrDc(crBosd.getSancOrDisbWithout());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setCiTranToInactiveAcct(crBosd.getCiTranToInact());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setCiDrTranToDormantAcct(crBosd.getcIDRTranTo());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setBiDrTranToDormantAcct(crBosd.getbIDRTranToDorman());

                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setChequeIssuedToInactiveAcct(crBosd.getChequeIssued());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setChequeIssuedToDormantAcct(crBosd.getChequeIssuedToDormant());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setSanctionLimitCompletelyUtilised(crBosd.getSanctionLimitCompletely());
                cbsSchemeCashCrBillsAndOverdraftDetailsTO.setAcctMiniBalBelowSchemeMinBal(crBosd.getAcctMiniBalBelowScheme());
                cbsSchemeCashCrBillsAndOverdraftDetailsTOs.add(cbsSchemeCashCrBillsAndOverdraftDetailsTO);
                if (crBosd.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    listOfLsd();
                    listOfPm();
                }
            }

            System.out.println("In AddModifyData method for CSDD form processing");
            if ((cSDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || cSDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && cSDD.getSchemeMaster().getPageType().equalsIgnoreCase("csdd")) {
                schemeDocumentdetails = new ArrayList<SchemeDocumentDetailsTO>();
                for (int i = 0; i < cSDD.getDocDetail().size(); i++) {
                    //It is a complex TO
                    SchemeDocumentDetailsTO objTO = new SchemeDocumentDetailsTO();
                    SchemeDocumentDetails tblObj = cSDD.getDocDetail().get(i);
                    objTO.setSchemeCode(cSDD.getSchemeMaster().getSchemeCode());
                    objTO.setDocumentCode(tblObj.getDocumentCode());
                    objTO.setDocumentDesc(tblObj.getDocumentDesc());
                    objTO.setMandatoryDoc(tblObj.getMandatoryDoc());
                    objTO.setDelFlagDocDetail(tblObj.getDelFlagDocDetail());
                    objTO.setCounterSaveUpdate(tblObj.getCounterSaveUpdate());
                    schemeDocumentdetails.add(objTO);
                }
                if (cSDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(cSDD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (cSDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(cSDD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for DOIP form processing");
            if ((dOIP.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || dOIP.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && dOIP.getSchemeMaster().getPageType().equalsIgnoreCase("doip")) {
                depositOverDueIntParameter = new ArrayList<DoipComplexTO>();
                DoipComplexTO toObj = new DoipComplexTO();
                toObj.setSchemeCode(dOIP.getSchemeMaster().getSchemeCode());
                toObj.setSchemeType(dOIP.getSchemeMaster().getSchemeType());
                toObj.setCurrencyCode(dOIP.getSchemeMaster().getCurrencyType());
                toObj.setOverDueGlSubHeadCode(dOIP.getOverdueGLSubheadCode());
                toObj.setOverDueInterestCode(dOIP.getOverdueIntCode());
                toObj.setOverDueIntTblCodeType(dOIP.getOverdueIntTblCode());
                toObj.setOverDueintCalcMethod(dOIP.getOverdueIntCalcMethod());
                toObj.setRenewalPerdExcd(dOIP.getRenewalPerdExcd());
                toObj.setMaxPerd(dOIP.getMaxPerd());
                toObj.setMaxAmt(dOIP.getMaxAmt());
                toObj.setMinorDepPreclosure(dOIP.getMinorDepPreclosure());
                toObj.setExtension(dOIP.getExtension());
                toObj.setSplCatgClosure(dOIP.getSplCatgClosure());
                toObj.setMatAmtTolerance(dOIP.getMatAmtTolerance());
                toObj.setNilPenalty(dOIP.getNilPenalty());
                toObj.setDisContinuedInst(dOIP.getDisContinuedInst());
                toObj.setTransferIn(dOIP.getTransferIn());
                toObj.setAcctVerBalCheck(dOIP.getAcctVerBalCheck());
                toObj.setSystemDrTransAllowed(dOIP.getSystemDrTransAllowed());
                toObj.setDupReprntRcpt(dOIP.getDupReprntRcpt());
                toObj.setPreMatureClosure(dOIP.getPreMatureClosure());
                toObj.setNoticePerdMinNoticePerd(dOIP.getNoticePerdMinNoticePerd());
                toObj.setDefaultValueForPreIntChgd(dOIP.getDefaultValueForPreIntChgd());
                toObj.setBackValueDateAccOpen(dOIP.getBackValueDateAccOpen());
                toObj.setFutureValueDateAccOpen(dOIP.getFutureValueDateAccOpen());
                depositOverDueIntParameter.add(toObj);
                if (dOIP.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(dOIP.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (dOIP.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(dOIP.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for DFD form processing");
            if ((dFD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || dFD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && dFD.getSchemeMaster().getPageType().equalsIgnoreCase("dfd")) {
                depositFlowTO = new ArrayList<DepositFlowTO>();
                for (int i = 0; i < dFD.getDepFlow().size(); i++) {
                    //It is a complex TO
                    DepositFlowTO objTO = new DepositFlowTO();
                    DepositFlow tblObj = dFD.getDepFlow().get(i);
                    objTO.setSchemeCode(dFD.getSchemeMaster().getSchemeCode());
                    objTO.setSchemeType(dFD.getSchemeMaster().getSchemeType());
                    objTO.setTblFlowCode(tblObj.getTblFlowCode());
                    objTO.setTblFlowFreqMonths(tblObj.getTblFlowFreqMonths());
                    objTO.setTblFlowFreqDays(tblObj.getTblFlowFreqDays());
                    objTO.setTblFlowPeriodBegin(tblObj.getTblFlowPeriodBegin());
                    objTO.setTblFlowPeriodEnd(tblObj.getTblFlowPeriodEnd());
                    objTO.setTblDelFlagFlow(tblObj.getTblDelFlagFlow());
                    objTO.setCounterSaveUpdateFlow(tblObj.getCounterSaveUpdateFlow());
                    depositFlowTO.add(objTO);
                }
                if (dFD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(dFD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (dFD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(dFD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for AD form processing");
            if ((aD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || aD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && aD.getSchemeMaster().getPageType().equalsIgnoreCase("ad")) {
                loanAssetTO = new ArrayList<LoanAssetTO>();
                for (int i = 0; i < aD.getAsset().size(); i++) {
                    //It is a complex TO
                    LoanAssetTO objTO = new LoanAssetTO();
                    LoanAsset tblObj = aD.getAsset().get(i);
                    objTO.setSchemeCode(aD.getSchemeMaster().getSchemeCode());
                    objTO.setTbdPDCounter(tblObj.getTbdPDCounter());
                    objTO.setTbMainClass(tblObj.getTbMainClass());
                    objTO.setTbSubClass(tblObj.getTbSubClass());
                    objTO.setTbIntAccre(tblObj.getTbIntAccre());
                    objTO.setTbIntFlagBk(tblObj.getTbIntFlagBk());
                    objTO.setTbIntFlagColl(tblObj.getTbIntFlagColl());
                    objTO.setTbPDFlag(tblObj.getTbPDFlag());
                    objTO.setTbIntSuspPlaceHolder(tblObj.getTbIntSuspPlaceHolder());
                    objTO.setTbprovisionDR(tblObj.getTbprovisionDR());
                    objTO.setTbPlaceHoldersCr(tblObj.getTbPlaceHoldersCr());
                    objTO.setTbdelFlag(tblObj.getTbdelFlag());
                    objTO.setCounterSaveUpdate(tblObj.getCounterSaveUpdate());
                    loanAssetTO.add(objTO);
                }
                if (aD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(aD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (aD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(aD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for DIDD form processing");
            if ((dIDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || dIDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && dIDD.getSchemeMaster().getPageType().equalsIgnoreCase("didd")) {
                depositIntTO = new ArrayList<DepositIntTO>();
                for (int i = 0; i < dIDD.getDeposit().size(); i++) {
                    //It is a complex TO
                    DepositIntTO objTO = new DepositIntTO();
                    DepositInt tblObj = dIDD.getDeposit().get(i);
                    objTO.setSchemeCode(dIDD.getSchemeMaster().getSchemeCode());
                    objTO.setSchemeType(dIDD.getSchemeMaster().getSchemeType());
                    objTO.setTblInterestMethod(tblObj.getTblInterestMethod());
                    objTO.setTblMaxDepositPeriodMonths(tblObj.getTblMaxDepositPeriodMonths());
                    objTO.setTblMaxDepositPeriodDays(tblObj.getTblMaxDepositPeriodDays());
                    objTO.setTblBaseAmtInd(tblObj.getTblBaseAmtInd());
                    objTO.setTblCompoundingPeriod(tblObj.getTblCompoundingPeriod());
                    objTO.setTblCompoundingBase(tblObj.getTblCompoundingBase());
                    objTO.setTblMinCompoundingPeriod(tblObj.getTblMinCompoundingPeriod());
                    objTO.setTblMinCompoundingBase(tblObj.getTblMinCompoundingBase());
                    objTO.setTblBrokenPeriodMethod(tblObj.getTblBrokenPeriodMethod());
                    objTO.setTblBrokenPeriodBase(tblObj.getTblBrokenPeriodBase());
                    objTO.setTblDeleteFlag(tblObj.getTblDeleteFlag());
                    objTO.setCounterSaveUpdateDeposit(tblObj.getCounterSaveUpdateDeposit());
                    depositIntTO.add(objTO);
                }
                if (dIDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(dIDD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (dIDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(dIDD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for GSHSD form processing");
            if ((gSHSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || gSHSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && gSHSD.getSchemeMaster().getPageType().equalsIgnoreCase("gshsd")) {
                GlSubHeadSchemeTOs = new ArrayList<GlSubHeadSchemeTO>();
                for (int i = 0; i < gSHSD.getGlHead().size(); i++) {
                    GlSubHeadSchemeTO singleTO = new GlSubHeadSchemeTO();
                    GlSubHeadTable tableObj = gSHSD.getGlHead().get(i);
                    singleTO.setSchemeCode(gSHSD.getSchemeMaster().getSchemeCode());
                    singleTO.setSchemeType(gSHSD.getSchemeMaster().getSchemeType());
                    singleTO.setGlSubHeadCode(tableObj.getGlSubHeadCode());
                    singleTO.setDefaultFlag(tableObj.getDefaultFlag());
                    singleTO.setDelFlag(tableObj.getDeleteFlag());
                    singleTO.setAcName(tableObj.getGlSubHead());
                    singleTO.setSaveUpdateCounter(tableObj.getCounterSaveUpdate());
                    GlSubHeadSchemeTOs.add(singleTO);
                }
                if (gSHSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(gSHSD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (gSHSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(gSHSD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for DSPM form processing");
            if ((dSPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || dSPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && dSPM.getSchemeMaster().getPageType().equalsIgnoreCase("dspm")) {
                cbsSchDepSchParaMainTOs = new ArrayList<CbsSchemeDepositsSchemeParametersMaintananceTO>();
                CbsSchemeDepositsSchemeParametersMaintananceTO singleTO = new CbsSchemeDepositsSchemeParametersMaintananceTO();
                singleTO.setSchemeCode(dSPM.getSchemeMaster().getSchemeCode());
                singleTO.setSchemeType(dSPM.getSchemeMaster().getSchemeType());
                singleTO.setCurrencyCode(dSPM.getSchemeMaster().getCurrencyType());
                singleTO.setDepositAmountMinimum(new BigDecimal(dSPM.getDepositAmountMinimum()));
                singleTO.setDepositAmountMaximum(new BigDecimal(dSPM.getDepositAmountMaximum()));
                singleTO.setDepositAmountSteps(new BigDecimal(dSPM.getDepositAmountSteps()));
                singleTO.setPeriodMiniMonths(dSPM.getPeriodMiniMonths());
                singleTO.setPeriodMiniDays(dSPM.getPeriodMiniDaysMain());
                singleTO.setPeriodMaxMonths(dSPM.getPeriodMaxMonthsMain());
                singleTO.setPeriodMaxDays(dSPM.getPeriodMaxDaysMain());
                singleTO.setPeriodStepsMonths(dSPM.getPeriodStepsMonths());
                singleTO.setPeriodStepsDays(dSPM.getPeriodStepDays());
                singleTO.setDepositReportTemplate(dSPM.getDepositReportTemplate());
                singleTO.setDepositType(dSPM.getDepositType());
                singleTO.setAutoRenewal(dSPM.getAutoRenewalMain());
                singleTO.setMaxRenewalAllowed(dSPM.getMaxRenewalAllowed());
                singleTO.setRenewalPeriodMonths(dSPM.getRenewalPeriodMonths());
                singleTO.setRenewalPeriodDays(dSPM.getRenewalPeriodDays());
                singleTO.setRenewalAllowedPeriod(dSPM.getRenewalAllowedPeriod());
                singleTO.setAutoRenewalGracePeriod(dSPM.getAutoRenewalGracePeriod());
                singleTO.setSundryDepositPlaceholder(dSPM.getSundryDepositPlaceholder());
                singleTO.setRepaymentReportCode(dSPM.getRepaymentReportCode());
                singleTO.setValueDatedClosure(dSPM.getValueDatedClosure());
                singleTO.setCallDepositNoticePeriodMonths(dSPM.getCallDepositNoticePeriodMonths());
                singleTO.setCallDepositNoticePeriodDays(dSPM.getCallDepositNoticePeriodDays());
                singleTO.setDelayInstallmentTblCode(dSPM.getDelayInstallmentTblCode());
                singleTO.setDelayWithinMonth(dSPM.getDelayWithinMonth());
                singleTO.setDelayAllowedPeriodMonths(dSPM.getDelayAllowedPeriodMonths());
                singleTO.setDelayAllowedPeriodDays(dSPM.getDelayAllowedPeriodDays());
                singleTO.setPenalFeePlaceholder(dSPM.getPenalFeePlaceholder());
                singleTO.setPenalFeeReportCode(dSPM.getPenalFeeReportCode());
                singleTO.setPrintPbDr(dSPM.getPrintPBDR());
                singleTO.setMatAmtToleranceLimit(dSPM.getMatAmtToleranceLimit());
                singleTO.setUseInventory(dSPM.getUseInventory());
                singleTO.setInventoryClass(dSPM.getInventoryClass());
                singleTO.setInventoryType(dSPM.getInventoryType());
                singleTO.setInventoryLoanClass(dSPM.getInventoryLoanClass());
                singleTO.setInventoryLoanCode(dSPM.getInventoryLoanCode());
                singleTO.setCommissionPlaceholder(dSPM.getCommissionPlaceholder());
                singleTO.setServiceChargeTblCode(dSPM.getServiceChargeTblCode());
                singleTO.setCommissionTblCode(dSPM.getCommissionTblCode());
                singleTO.setPreOrPartClosurePenaltyCode(dSPM.getPrePartClosurePenaltyCode());
                singleTO.setCommissionReportCode(dSPM.getCommissionReportCode());
                singleTO.setAllowSweeps(dSPM.getAllowSweepsmain());
                singleTO.setAllowPartClosure(dSPM.getAllowPartClosure());
                cbsSchDepSchParaMainTOs.add(singleTO);
                if (dSPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(dSPM.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (dSPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(dSPM.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for ISCD form processing");
            if ((iSCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || iSCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && iSCD.getSchemeMaster().getPageType().equalsIgnoreCase("iscd")) {
                cbsSchIntSerChgDtls = new ArrayList<CbsSchemeInterestOrServiceChargesDetailsTO>();
                CbsSchemeInterestOrServiceChargesDetailsTO singleTO = new CbsSchemeInterestOrServiceChargesDetailsTO();
                singleTO.setSchemeCode(iSCD.getSchemeMaster().getSchemeCode());
                singleTO.setSchemeType(iSCD.getSchemeMaster().getSchemeType());
                singleTO.setInterestPaidFlag(iSCD.getIntPaidFlag());
                singleTO.setInterestPayableAccountPlaceholder(iSCD.getIntPayAcctHolder());
                singleTO.setIntCollectionFlag(iSCD.getIntCollFlag());
                singleTO.setIntRecbleAccountPlaceholder(iSCD.getIntRecAcctHolder());
                singleTO.setServiceChargesFlag(iSCD.getServiceChgFlag());
                singleTO.setServiceCollAccountPlaceholder(iSCD.getServiceCollAcctHolder());
                singleTO.setParkingAccountTdsPlaceholder(iSCD.getParkingAcctTdsHolder());
                singleTO.setIncomeExpenseAccountInHomeCurrency(iSCD.getIncomeExpenseAcct());
                singleTO.setNormalProfitAndLossAccountPlaceholderCr(iSCD.getNrmlPrftLossAcctHolderCr());
                singleTO.setNormalProfitAndLossAccountPlaceholderDr(iSCD.getNrmlPrftLossActHolderDr());
                singleTO.setPenalProfitAndLossAccountPlaceholderDr(iSCD.getPenalPLAcctHoldeDr());
                singleTO.setParkingAccountPlaceholder(iSCD.getParkingAcctHolder());
                singleTO.setChequeAllowedFlag(iSCD.getChqAllowedFlag());
                singleTO.setMicrChequeChrgAccountPlaceholder(iSCD.getMicrChqChgAcctHolder());
                singleTO.setOverDueIntPaidAcPlaceholder(iSCD.getOverDueIntpaidAcHolder());
                singleTO.setOverDueNormalProfitAndLossAccountPlaceholder(iSCD.getOverDueNrmlPrftLossAcctHolderCr());
                singleTO.setMergeIntPtranFlag(iSCD.getMergeIntPtranFlag());
                singleTO.setOperativeAccountWithoutEnoughFundsToBeDebited(iSCD.getOptActEnoughFundsDebited());
                singleTO.setAdvanceInterestAccount(iSCD.getAdvanceIntAcct());
                singleTO.setBookTransScript(iSCD.getBookTransScript());
                singleTO.setInterestCalculationFreqCrType(iSCD.getIntCalFreqCrType());
                singleTO.setInterestCalculationFreqCrWeekNo(iSCD.getIntCalFreqCrWeekNo());
                singleTO.setInterestCalculationFreqCrWeekDay(iSCD.getIntCalFreqCrWeekDay());
                singleTO.setInterestCalculationFreqCrStartDate(iSCD.getIntCalFreqCrStartDt());
                singleTO.setInterestCalculationFreqCrNp(iSCD.getIntCalFreqCrNp());
                singleTO.setInterestCalculationFreqDrType(iSCD.getIntCalFreqDrType());
                singleTO.setInterestCalculationFreqDrWeekNo(iSCD.getIntCalFreqDrWeekNo());
                singleTO.setInterestCalculationFreqDrWeekDate(iSCD.getIntCalFreqDrWeekDay());
                singleTO.setInterestCalculationFreqDrStartDate(iSCD.getIntCalFreqDrStartDt());
                singleTO.setInterestCalculationFreqDrNp(iSCD.getIntCalFreqDrNp());
                singleTO.setLimitLevelIntFlag(iSCD.getLmtLevelIntFlag());
                singleTO.setInterestOnQis(iSCD.getIntOnQIS());
                singleTO.setLookBackPeriod(iSCD.getLookBackPeriod());
                singleTO.setInterestOnStock(iSCD.getInterestOnStock());
                singleTO.setCompoundRestDayFlag(iSCD.getCompoundRestDayFlag());
                singleTO.setDebitIntCompoundingFreq(iSCD.getDrIntCompoundFreq());
                singleTO.setApplyDiscountedIntRate(Double.parseDouble(iSCD.getApplyDisIntRate()));
                singleTO.setPrincipalLossLinePlaceholder(iSCD.getPrincipalLossLineHolder());
                singleTO.setRecoveryLossLinePlaceholer(iSCD.getRecovryLossLineholer());
                singleTO.setChargeOffAccountPlaceholder(iSCD.getChargeOffAcctHolder());
                singleTO.setDealerContributionPlaceholder(iSCD.getDealerContributionHolder());
                singleTO.setInterestWaiverDebitPlaceholder(iSCD.getIntWaiverDebitHolder());
                cbsSchIntSerChgDtls.add(singleTO);
                if (iSCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(iSCD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (iSCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(iSCD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for LED form processing");
            if ((lED.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || lED.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && lED.getSchemeMaster().getPageType().equalsIgnoreCase("led")) {
                cbsSchLoanExpDtls = new ArrayList<CbsSchemeLoanExceptionDetailsTO>();
                CbsSchemeLoanExceptionDetailsTO singleTO = new CbsSchemeLoanExceptionDetailsTO();
                singleTO.setSchemeCode(lED.getSchemeMaster().getSchemeCode());
                singleTO.setCurrencyCode(lED.getSchemeMaster().getCurrencyType());
                singleTO.setSchemeType(lED.getSchemeMaster().getSchemeType());
                singleTO.setNonConformingLoanPeriod(lED.getNonConformingLoanPeriod());
                singleTO.setNonConformingLoanAmount(lED.getNonConformingLoanAmount());
                singleTO.setDisbGreaterThanLoanAmount(lED.getDisbGreaterThanLoanAmount());
                singleTO.setDisbNotConformingToSchedule(lED.getDisbNotConformingToSchedule());
                singleTO.setDisbDateSanctExpiryDate(lED.getDisbDateSanctExpiryDate());
                singleTO.setIntCalculationNotUpToDate(lED.getIntCalculationNotUpToDate());
                singleTO.setTransferAmountIsGreaterThanCrBalance(lED.getTransferAmountIsGreaterThanCrBalance());
                singleTO.setCustIdDiffForLoanAndOpAccount(lED.getCustIdDiffForLoanAndOpAccount());
                singleTO.setInterestCollectedExceedsLimit(lED.getInterestCollectedExceedsLimit());
                singleTO.setWaiverOfChargesOrInterest(lED.getWaiverOfChargesOrInterest());
                singleTO.setOverrideSystemCalcEiAmount(lED.getOverrideSystemCalcEiAmount());
                singleTO.setPendingSchedulePayments(lED.getPendingSchedulePayments());
                singleTO.setRepaymentPerdNotEqualToLoanPerd(lED.getRepaymentPerdNotEquelToLoanPerd());
                singleTO.setRephasementInterestCalcNotUpToDate(lED.getRephasementInterestCalcNotUpToDate());
                singleTO.setMaxHolidayPeriodExceeded(lED.getMaxHolidayPeriodExceeded());
                singleTO.setPrepaymentNotAsPerNotice(lED.getPrepaymentNotAsPerNotice());
                singleTO.setValueDatedNotice(lED.getValueDatedNotice());
                singleTO.setBackValueDatedAccountOpening(lED.getBackValueDatedAccountOpening());
                cbsSchLoanExpDtls.add(singleTO);
                if (lED.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(lED.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (lED.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(lED.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for SVI form processing");
            if ((sVI.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || sVI.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && sVI.getSchemeMaster().getPageType().equalsIgnoreCase("svi")) {
                schValidComplexTO = new ArrayList<SchemeValidInstrumentTO>();
                for (int i = 0; i < sVI.getSchemeValid().size(); i++) {
                    //It is a complex TO
                    SchemeValidInstrumentTO objTO = new SchemeValidInstrumentTO();
                    SchemeValidInstrumentsTable tblObj = sVI.getSchemeValid().get(i);
                    objTO.setSchemeCode(sVI.getSchemeMaster().getSchemeCode());
                    objTO.setSchemeType(sVI.getSchemeMaster().getSchemeType());
                    objTO.setInstrumentCode(tblObj.getInstrumentCode());
                    objTO.setCrDrIndFlag(tblObj.getCrDrIndFlg());
                    objTO.setDelFlag(tblObj.getDeleteSchemeValid());
                    objTO.setSaveUpdateFlag(tblObj.getCounterSaveUpdate());
                    schValidComplexTO.add(objTO);
                }
                if (sVI.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(sVI.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (sVI.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(sVI.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for LFDCW form processing");
            if ((lFDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || lFDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && lFDCW.getSchemeMaster().getPageType().equalsIgnoreCase("lfdcw")) {
                ledgerFolioDtls = new ArrayList<LedgerFolioDetailsCurWiseTO>();
                for (int i = 0; i < lFDCW.getLedger().size(); i++) {
                    //It is a complex TO
                    LedgerFolioDetailsCurWiseTO objTO = new LedgerFolioDetailsCurWiseTO();
                    TblLedgerFolio tblObj = lFDCW.getLedger().get(i);
                    objTO.setSchemeCode(lFDCW.getSchemeMaster().getSchemeCode());
                    objTO.setSchemeType(lFDCW.getSchemeMaster().getSchemeType());
                    objTO.setCurrencyCode(lFDCW.getSchemeMaster().getCurrencyType());
                    objTO.setStartAmt(tblObj.getTblStartAmount());
                    objTO.setEndAmt(tblObj.getTblEndAmount());
                    objTO.setFreeFolios(tblObj.getTblFreeFolios());
                    objTO.setDelflaf(tblObj.getTblDelFlagledger());
                    objTO.setSaveUpdateFlag(tblObj.getCounterSaveUpdateLedger());
                    ledgerFolioDtls.add(objTO);
                }
                if (lFDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(lFDCW.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (lFDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(lFDCW.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for LRCD form processing");
            if ((lRCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || lRCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && lRCD.getSchemeMaster().getPageType().equalsIgnoreCase("lrcd")) {
                loanRepaymentCyclDefs = new ArrayList<LoanRepaymentCycleDefinationTO>();
                for (int i = 0; i < lRCD.getLoanRepay().size(); i++) {
                    //It is a complex TO
                    LoanRepaymentCycleDefinationTO objTO = new LoanRepaymentCycleDefinationTO();
                    LoanRepaymentCycleDefination tblObj = lRCD.getLoanRepay().get(i);
                    objTO.setSchemeCode(lRCD.getSchemeMaster().getSchemeCode());
                    objTO.setAcOpenFromDate(tblObj.getAcOpenFromDate());
                    objTO.setAcOpenToDate(tblObj.getAcOpenToDate());
                    objTO.setRepaymentStartDate(tblObj.getRepaymentStartDate());
                    objTO.setMonthIndicator(tblObj.getMonthIndicator());
                    objTO.setDelFlag(tblObj.getDelFlag());
                    objTO.setCounterSaveUpdate(tblObj.getCounterSaveUpdate());
                    loanRepaymentCyclDefs.add(objTO);
                }
                if (lRCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(lRCD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (lRCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(lRCD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for SFCD form processing");
            if ((sFCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || sFCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && sFCD.getSchemeMaster().getPageType().equalsIgnoreCase("sfcd")) {
                feeOrChargesDtls = new ArrayList<FeeOrChargesDetailsTO>();
                for (int i = 0; i < sFCD.getFeeCharges().size(); i++) {
                    //It is a complex TO
                    FeeOrChargesDetailsTO objTO = new FeeOrChargesDetailsTO();
                    SchemeFeeOrChargesDetails tblObj = sFCD.getFeeCharges().get(i);
                    objTO.setSchemeCode(sFCD.getSchemeMaster().getSchemeCode());
                    objTO.setCurrencyCode(sFCD.getSchemeMaster().getCurrencyType());
                    objTO.setChargesType(tblObj.getChargesType());
                    objTO.setAmortize(tblObj.getAmortize());
                    objTO.setChargesDesc(tblObj.getChargesDesc());
                    objTO.setAmortMethod(tblObj.getAmortMethod());
                    objTO.setChargesEventId(tblObj.getChargesEventId());
                    objTO.setDrPlaceHolder(tblObj.getDrPlaceHolder());
                    objTO.setCrPlaceHolder(tblObj.getCrPlaceHolder());
                    objTO.setAssertOrDmd(tblObj.getAssertOrDmd());
                    objTO.setDeductible(tblObj.getDeductible());
                    objTO.setConsiderForIrr(tblObj.getConsiderForIrr());
                    objTO.setMultipleFlag(tblObj.getMultipleFlag());
                    objTO.setDelFlagFeeCharges(tblObj.getDelFlagFeeCharges());
                    objTO.setPrePaymentFee("0.00");
                    objTO.setCounterSaveUpdate(tblObj.getCounterSaveUpdate());
                    feeOrChargesDtls.add(objTO);
                }
                if (sFCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(sFCD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (sFCD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(sFCD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for TRCCW form processing");
            if ((tRCCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || tRCCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && tRCCW.getSchemeMaster().getPageType().equalsIgnoreCase("trccw")) {
                transRepCode = new ArrayList<TransactionReportCodeTO>();
                for (int i = 0; i < tRCCW.getTranRepCodeWise().size(); i++) {
                    //It is a complex TO
                    TransactionReportCodeTO objTO = new TransactionReportCodeTO();
                    SchemeTranReportCodeCurrWise tblObj = tRCCW.getTranRepCodeWise().get(i);
                    objTO.setSchemeCode(tRCCW.getSchemeMaster().getSchemeCode());
                    objTO.setTransactionReportCode(tblObj.getTransactionReportCode());
                    objTO.setCurrencyCodeTranRep(tblObj.getCurrencyCodeTranRep());
                    objTO.setSchemeType(tRCCW.getSchemeMaster().getSchemeType());
                    objTO.setDrAmt(tblObj.getDrAmt());
                    objTO.setCrAmt(tblObj.getCrAmt());
                    objTO.setDeleteTranCode(tblObj.getDeleteTranCode());
                    objTO.setCounterSaveUpdate(tblObj.getCounterSaveUpdate());
                    transRepCode.add(objTO);
                }
                if (tRCCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(tRCCW.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (tRCCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(tRCCW.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for STRD form processing");
            if ((sTRD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || sTRD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && sTRD.getSchemeMaster().getPageType().equalsIgnoreCase("strd")) {
                schemeTodRefdetails = new ArrayList<SchemeTODRefdetailsTO>();
                for (int i = 0; i < sTRD.getTodRef().size(); i++) {
                    //It is a complex TO
                    SchemeTODRefdetailsTO objTO = new SchemeTODRefdetailsTO();
                    SchemeTodReferenceDetails tblObj = sTRD.getTodRef().get(i);
                    objTO.setSchemeCode(sTRD.getSchemeMaster().getSchemeCode());
                    objTO.setSchemeType(sTRD.getSchemeMaster().getSchemeType());
                    objTO.setReferenceType(tblObj.getReferenceType());
                    objTO.setDiscretNumberOfDays(tblObj.getDiscretNumberOfDays());
                    objTO.setPenalDays(tblObj.getPenalDays());
                    objTO.setDiscretAdvnType(tblObj.getDiscretAdvnType());
                    objTO.setDiscretAdvnCategory(tblObj.getDiscretAdvnCategory());
                    objTO.setIntFlag(tblObj.getIntFlag());
                    objTO.setInterestTableCode(tblObj.getInterestTableCode());
                    objTO.setToLevelIntTblCode(tblObj.getToLevelIntTblCode());
                    objTO.setFreeTxtCode(tblObj.getFreeTxtCode());
                    objTO.setDelFlagTodRef(tblObj.getDelFlagTodRef());
                    objTO.setCounterSaveUpdate(tblObj.getCounterSaveUpdate());
                    schemeTodRefdetails.add(objTO);
                }
                if (sTRD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(sTRD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (sTRD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(sTRD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for TEDCW form processing");
            if ((tEDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || tEDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && tEDCW.getSchemeMaster().getPageType().equalsIgnoreCase("tedcw")) {
                todExceptionDetails = new ArrayList<TodExceptionDetailsTO>();
                for (int i = 0; i < tEDCW.getSchemeTod().size(); i++) {
                    //It is a complex TO
                    TodExceptionDetailsTO objTO = new TodExceptionDetailsTO();
                    SchemeTodCurrencyWiseTable tblObj = tEDCW.getSchemeTod().get(i);
                    objTO.setSchemeCode(tEDCW.getSchemeMaster().getSchemeCode());
                    objTO.setTodSrlNo("");
                    objTO.setCurrencyCode(tblObj.getCurrencyCode());
                    objTO.setSchemeType(tEDCW.getSchemeMaster().getSchemeType());
                    objTO.setBeginAmount(new BigDecimal(tblObj.getBgAmt()));
                    objTO.setEndAmount(new BigDecimal(tblObj.getEndAmt()));
                    objTO.setTodException(tblObj.getTodException());
                    objTO.setDelFlag(tblObj.getDeleteTod());
                    objTO.setExceptionCode(tblObj.getTodException());
                    objTO.setExceptionDesc(tblObj.getTodExceptionDesc());
                    objTO.setExceptionType("");
                    objTO.setCounterSaveUpdate(tblObj.getCounterSaveUpdate());
                    todExceptionDetails.add(objTO);
                }
                if (tEDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(tEDCW.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (tEDCW.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(tEDCW.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            /************************Added by Ankit************************/
            System.out.println("In AddModifyData method for FFDD form processing ");
            if ((fFDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || fFDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && fFDD.getSchemeMaster().getPageType().equalsIgnoreCase("ffdd")) {
                cbsSchemeFlexiFixedDepositsDetailsTOs = new ArrayList<CbsSchemeFlexiFixedDepositsDetailsTO>();
                CbsSchemeFlexiFixedDepositsDetailsTO cbsSchemeFlexiFixedDepositsDtlTO = new CbsSchemeFlexiFixedDepositsDetailsTO();
                cbsSchemeFlexiFixedDepositsDtlTO.setAddPreferentialToPenaltyRate(fFDD.getAddPreferentialtoPenaltyRate());
                cbsSchemeFlexiFixedDepositsDtlTO.setAutoCrFreqNp(fFDD.getAutoCrFreqNP());
                cbsSchemeFlexiFixedDepositsDtlTO.setAutoCrFreqStartDate(fFDD.getAutoCrFreqStartDate());
                cbsSchemeFlexiFixedDepositsDtlTO.setAutoCrFreqType(fFDD.getAutoCrFreqType());
                cbsSchemeFlexiFixedDepositsDtlTO.setAutoCrFreqWeekDay(fFDD.getAutoCrFreqWeekDay());
                cbsSchemeFlexiFixedDepositsDtlTO.setAutoCrFreqWeekNo(fFDD.getAutoCrFreqWeekNo());
                cbsSchemeFlexiFixedDepositsDtlTO.setAutoCrPerdDays(fFDD.getAutoCrPerdDays());
                cbsSchemeFlexiFixedDepositsDtlTO.setAutoCrPerdMonths(fFDD.getAutoCrPerdMonths());
                cbsSchemeFlexiFixedDepositsDtlTO.setAutomaticallyCreateDeposits(fFDD.getAutomaticallyCreateDeposits());
                cbsSchemeFlexiFixedDepositsDtlTO.setBreakDepositInStepsOf(new BigDecimal(fFDD.getBreakDepositInStepsOf().toString()));
                cbsSchemeFlexiFixedDepositsDtlTO.setCreateDepositIfOperativeAccountMoreThan(new BigDecimal(fFDD.getCreateDepositIfOperativeAccountMoreThan().toString()));
                cbsSchemeFlexiFixedDepositsDtlTO.setCreateDepositsInStepsOf(new BigDecimal(fFDD.getCreateDepositsInStepsOf()));
                cbsSchemeFlexiFixedDepositsDtlTO.setForeClosureInterestMethod(fFDD.getForeClosureInterestMethod());
                cbsSchemeFlexiFixedDepositsDtlTO.setLinkToOperativeAccount(fFDD.getLinkToOperativeAccount());
                cbsSchemeFlexiFixedDepositsDtlTO.setSchemeCode(fFDD.getSchemeMaster().getSchemeCode());
                cbsSchemeFlexiFixedDepositsDtlTO.setSchemeType(fFDD.getSchemeMaster().getSchemeType());
                cbsSchemeFlexiFixedDepositsDtlTO.setCurrencyCodeType(fFDD.getSchemeMaster().getCurrencyType());
                cbsSchemeFlexiFixedDepositsDetailsTOs.add(cbsSchemeFlexiFixedDepositsDtlTO);
                if (fFDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(fFDD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (fFDD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(fFDD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for DD form processing");
            //ddD.getDeliqDetails(); It will going to save
            if ((ddD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || ddD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && ddD.getSchemeMaster().getPageType().equalsIgnoreCase("dd")) {
                deliqDetailsTOs = new ArrayList<DeliquencyTableTO>();
                for (int i = 0; i < ddD.getDeliqDetails().size(); i++) {
                    //Table TO
                    DeliquencyTableTO objTO = new DeliquencyTableTO();
                    DeliquencyTable tblObj = ddD.getDeliqDetails().get(i);
                    objTO.setSchemeCode(ddD.getSchemeMaster().getSchemeCode());
                    objTO.setDeliqCycle(tblObj.getDeliqCycle());
                    objTO.setDaysPastDue(tblObj.getDaysPastDue());
                    objTO.setDelFlagDeliq(tblObj.getDelFlagDeliq());
                    objTO.setPlaceHolder(tblObj.getPlaceInHolder());
                    objTO.setProvisionPercent(tblObj.getProvisionInPercent());
                    objTO.setCounterSaveUpdate(tblObj.getCounterSaveUpdate());
                    deliqDetailsTOs.add(objTO);
                }
                if (ddD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(ddD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (ddD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(ddD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for LPD form processing");
            if ((lPD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || lPD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && lPD.getSchemeMaster().getPageType().equalsIgnoreCase("lpd")) {
                cbsSchemeLoanPrepaymentDetailsTOs = new ArrayList<CbsSchemeLoanPrepaymentDetailsTO>();
                CbsSchemeLoanPrepaymentDetailsTO cbsSchemeLoanPrepaymentDetailsTO = new CbsSchemeLoanPrepaymentDetailsTO();
                cbsSchemeLoanPrepaymentDetailsTO.setSchemeCode(lPD.getSchemeMaster().getSchemeCode());
                cbsSchemeLoanPrepaymentDetailsTO.setCurrencyCode(lPD.getSchemeMaster().getCurrencyType());
                cbsSchemeLoanPrepaymentDetailsTO.setSchemeType(lPD.getSchemeMaster().getSchemeType());

                cbsSchemeLoanPrepaymentDetailsTO.setPrepaymentIntReductionMethod(lPD.getPrepaymentIntReductionMethod());
                cbsSchemeLoanPrepaymentDetailsTO.setApplyPrepaymentCharges(lPD.getApplyPrepaymentCharges());
                cbsSchemeLoanPrepaymentDetailsTO.setMinAmountForPrepayment(new BigDecimal(lPD.getMinAmountforPrepayment()));
                cbsSchemeLoanPrepaymentDetailsTO.setNoPrepaymentChargesAfterMonths(lPD.getNoPrepaymentChargesAfterMonths());
                cbsSchemeLoanPrepaymentDetailsTO.setNoPrepaymentChargesAfterDays(lPD.getNoPrepaymentChargesAfterDays());
                cbsSchemeLoanPrepaymentDetailsTO.setLimitForPrepaymentInAYear(new BigDecimal(lPD.getLimitForPrepaymentInAYear()));
                cbsSchemeLoanPrepaymentDetailsTO.setLimitIndicatorForPrepayment(lPD.getLimitIndicatorForPrepayment());
                cbsSchemeLoanPrepaymentDetailsTO.setYearIndicatorForPrepaymentLimit(lPD.getYearIndicatorForPrepaymentLimit());
                cbsSchemeLoanPrepaymentDetailsTO.setPrepaymentNotAcceptedBeforeMonths(lPD.getPrepaymentNotAcceptedBeforeMonths());
                cbsSchemeLoanPrepaymentDetailsTO.setPrepaymentNotAcceptedBeforeDays(lPD.getPrepaymentNotAcceptedBeforeDays());
                cbsSchemeLoanPrepaymentDetailsTO.setNoticeReqdForPrepayment(lPD.getNoticeReqdForPrepayment());
                cbsSchemeLoanPrepaymentDetailsTO.setMinNoticePeriodMonths(lPD.getMinNoticePeriodMonths());
                cbsSchemeLoanPrepaymentDetailsTO.setMinNoticePeriodDay(lPD.getMinNoticePeriodDay());
                cbsSchemeLoanPrepaymentDetailsTO.setValidityOfTheNoticePeriodMonths(lPD.getValidityOfTheNoticePeriodMonths());
                cbsSchemeLoanPrepaymentDetailsTO.setValidityofthenoticeperioddays(lPD.getValidityOfTheNoticePeriodDays());
                cbsSchemeLoanPrepaymentDetailsTO.setEiFlowId(lPD.geteIflowId());
                cbsSchemeLoanPrepaymentDetailsTO.setPrincipalFlowId(lPD.getPrincipalFlowId());
                cbsSchemeLoanPrepaymentDetailsTO.setDisbursementFlowId(lPD.getDisbursementFlowId());
                cbsSchemeLoanPrepaymentDetailsTO.setCollectionFlowId(lPD.getCollectionFlowId());
                cbsSchemeLoanPrepaymentDetailsTO.setIntDemandFlowId(lPD.getIntDemandFlowId());
                cbsSchemeLoanPrepaymentDetailsTO.setPenalIntDemandFlowId(lPD.getPenalIntDemandFlowId());
                cbsSchemeLoanPrepaymentDetailsTO.setOverdueIntDemandFlowId(lPD.getPenalIntDemandFlowId());
                cbsSchemeLoanPrepaymentDetailsTO.setPastDueCollectionFlowId(lPD.getPastDueCollectionFlowId());
                cbsSchemeLoanPrepaymentDetailsTO.setChargeDemandFlowId(lPD.getChargeDemandFlowId());
                cbsSchemeLoanPrepaymentDetailsTO.setPayOffIntToBeCollectedTill(lPD.getApplyPrepaymentChargs1());
                cbsSchemeLoanPrepaymentDetailsTOs.add(cbsSchemeLoanPrepaymentDetailsTO);
                if (lPD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(lPD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (lPD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(lPD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for LSD form processing");
            if ((lSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || lSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && lSD.getSchemeMaster().getPageType().equalsIgnoreCase("lsd")) {
                cbsSchemeLoanSchemeDetailsTOs = new ArrayList<CbsSchemeLoanSchemeDetailsTO>();
                CbsSchemeLoanSchemeDetailsTO cbsSchemeLoanSchemeDetailsTO = new CbsSchemeLoanSchemeDetailsTO();
                cbsSchemeLoanSchemeDetailsTO.setSchemeCode(lSD.getSchemeMaster().getSchemeCode());
                cbsSchemeLoanSchemeDetailsTO.setCurrencyCode(lSD.getSchemeMaster().getCurrencyType());
                cbsSchemeLoanSchemeDetailsTO.setSchemeType(lSD.getSchemeMaster().getSchemeType());
                cbsSchemeLoanSchemeDetailsTO.setLoanPeriodMiniMonths(lSD.getPeriodMinMonths());
                cbsSchemeLoanSchemeDetailsTO.setLoanPeriodMiniDays(lSD.getPeriodMiniDays());
                cbsSchemeLoanSchemeDetailsTO.setLoanPeriodMaxMonths(lSD.getPeriodMaxMonths());
                cbsSchemeLoanSchemeDetailsTO.setLoanPeriodMaxDays(lSD.getPeriodMaxDays());
                cbsSchemeLoanSchemeDetailsTO.setLoanAmountMin(new BigDecimal(lSD.getAmountMin()));
                cbsSchemeLoanSchemeDetailsTO.setLoanAmountMax(new BigDecimal(lSD.getAmountMax()));
                cbsSchemeLoanSchemeDetailsTO.setLoanRepaymentMethod(lSD.getRepaymentMethod());
                cbsSchemeLoanSchemeDetailsTO.setHoldInOpenAccountForAmountDue(lSD.getHoldInOpenAccountForAmount());
                cbsSchemeLoanSchemeDetailsTO.setUpfrontInstallmentCollection(lSD.getUpfrontInstallmentCollection());
                cbsSchemeLoanSchemeDetailsTO.setIntBaseMethod(lSD.getIntBaseMethod());
                cbsSchemeLoanSchemeDetailsTO.setIntProductMethod(lSD.getIntProduct());
                cbsSchemeLoanSchemeDetailsTO.setIntRouteFlag(lSD.getRoute());
                cbsSchemeLoanSchemeDetailsTO.setChrargeRouteFlag(lSD.getChargeRoute());
                cbsSchemeLoanSchemeDetailsTO.setLoanIntOrChrgAccountPlaceholder(lSD.getIntOrChrgAccount());
                cbsSchemeLoanSchemeDetailsTO.setEquatedInstallments(lSD.getEquatedInstallments());
                cbsSchemeLoanSchemeDetailsTO.setEiInAdvance(lSD.getEiINAdvance());
                cbsSchemeLoanSchemeDetailsTO.setEiFormulaFlag(lSD.getEiFormulaFlag());
                cbsSchemeLoanSchemeDetailsTO.setEiRoundingOffAmount(lSD.getEiRoundingOffAmount());
                cbsSchemeLoanSchemeDetailsTO.setEiRoundingOffInd(lSD.getRoundingOffInd());
                cbsSchemeLoanSchemeDetailsTO.setCompoundingFreq(lSD.getCompoundingFreq());
                cbsSchemeLoanSchemeDetailsTO.setEiPaymentFreq(lSD.getEiPaymentFreq());
                cbsSchemeLoanSchemeDetailsTO.setInterestRestFreq(lSD.getInterestRestFreq());
                cbsSchemeLoanSchemeDetailsTO.setInterestRestBasis(lSD.getInterestRestBasis());
                cbsSchemeLoanSchemeDetailsTO.setUpfrontInterestCollection(lSD.getUpfrontInterestCollection());
                cbsSchemeLoanSchemeDetailsTO.setDiscountedInterest(lSD.getDiscountedInterest());
                cbsSchemeLoanSchemeDetailsTO.setIntAmortizationByRule78(lSD.getIntAmortization());
                cbsSchemeLoanSchemeDetailsTOs.add(cbsSchemeLoanSchemeDetailsTO);
                if (lSD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(lSD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }
            System.out.println("In AddModifyData method for SPM form processing");
            if ((sPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || sPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && sPM.getSchemeMaster().getPageType().equalsIgnoreCase("spm")) {
                cbsSchemeCaSbSchDetailsTOs = new ArrayList<CbsSchemeCaSbSchDetailsTO>();
                CbsSchemeCaSbSchDetailsTO cbsSchemeCaSbSchDetailsTO = new CbsSchemeCaSbSchDetailsTO();
                cbsSchemeCaSbSchDetailsTO.setAlwdNumberOfWthdrawals(sPM.getAllowedNoWithdrawls());
                cbsSchemeCaSbSchDetailsTO.setNoOfWithdrawalsExceeded(sPM.getNoWithdrawlsExceeded());
                cbsSchemeCaSbSchDetailsTO.setMinBalanceForCheque(new BigDecimal(sPM.getMinBalChq()));
                cbsSchemeCaSbSchDetailsTO.setDrBalLimit(sPM.getDrBalLimitExc());
                cbsSchemeCaSbSchDetailsTO.setLedgerFolioChargeFolio(new BigDecimal(sPM.getLedgerFolio1()));
                cbsSchemeCaSbSchDetailsTO.setServiceChargeExtraWithdrawal(new BigDecimal(sPM.getServChrgWithdrawl()));
                cbsSchemeCaSbSchDetailsTO.setInactiveAccountAbmmlTranLimit(new BigDecimal(sPM.getInactiveAccAbnrmlTranLimit()));
                cbsSchemeCaSbSchDetailsTO.setDormantAccAbmmlTranLimit(new BigDecimal(sPM.getDormantAccAbnrmlTranLimit().toString()));
                cbsSchemeCaSbSchDetailsTO.setBalanceAnd(new BigDecimal(sPM.getAndBalBet()));
                cbsSchemeCaSbSchDetailsTO.setDurationToMarkAccasInactive(sPM.getDurationMarkAccAct());
                cbsSchemeCaSbSchDetailsTO.setDurationToFromInactiveToDormat(sPM.getDurationFromactDorm());
                cbsSchemeCaSbSchDetailsTO.setDormantChargeEvent(sPM.getDormEvent());
                cbsSchemeCaSbSchDetailsTO.setInactiveChargeEvent(sPM.getInacEvent().toString());
                cbsSchemeCaSbSchDetailsTO.setAllowSweeps(sPM.getAllowSweep());
                cbsSchemeCaSbSchDetailsTO.setAllowDrAgainstUnclearBalance(sPM.getAllowDrUnclearBalance());
                cbsSchemeCaSbSchDetailsTO.setIntCalculationOnLocalCalender(sPM.getIntCalcLocalCalender());
                cbsSchemeCaSbSchDetailsTO.setIntOnAvgOrMinBal(sPM.getIntAvgBalance());
                cbsSchemeCaSbSchDetailsTO.setBalanceBetween(new BigDecimal(sPM.getBalBet()));
                cbsSchemeCaSbSchDetailsTO.setDrBalanceLimit(new BigDecimal(sPM.getDrBalLim()));
                cbsSchemeCaSbSchDetailsTO.setChequeIssuedOnDormantAccount(sPM.getChqIssuedOnDormantAcct());
                cbsSchemeCaSbSchDetailsTO.setChequeBookWithoutMinBalance(sPM.getChqBkWithoutMinBal());
                cbsSchemeCaSbSchDetailsTO.setAccMinBalanceBelowSchemeMinBal(sPM.getAccMinBalBlowSchMinBal());
                cbsSchemeCaSbSchDetailsTO.setcITranToInactiveAccount(sPM.getcITranInacAcc());
                cbsSchemeCaSbSchDetailsTO.setcIDrTranToDormantAccount(sPM.getcIDrTranDormantAcct());
                cbsSchemeCaSbSchDetailsTO.setbIDrTranToDormantAccount(sPM.getbIDrTranDormantAcct());
                cbsSchemeCaSbSchDetailsTO.setChequeIssuedOnInactiveAccount(sPM.getChqIssuedOnInactiveAcct());
                cbsSchemeCaSbSchDetailsTO.setAccClosedWithinAnYear(sPM.getAccClosedWithinYr());
                cbsSchemeCaSbSchDetailsTO.setNoIntIfWithdrawalsExceed(sPM.getNoWithdrawlsExceed());
                cbsSchemeCaSbSchDetailsTO.settODOnMinorAccount(sPM.gettODMinorAcc());
                cbsSchemeCaSbSchDetailsTO.setIntroducerNotACAHolder(sPM.getIntroducerNotCAHold());
                cbsSchemeCaSbSchDetailsTO.setSchemeCode(sPM.getSchemeMaster().getSchemeCode());
                cbsSchemeCaSbSchDetailsTO.setSchemeType(sPM.getSchemeMaster().getSchemeType());
                cbsSchemeCaSbSchDetailsTO.setCurrencyCodeType(sPM.getSchemeMaster().getCurrencyType());

                cbsSchemeCaSbSchDetailsTOs.add(cbsSchemeCaSbSchDetailsTO);
                if (sPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(sPM.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (sPM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(sPM.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for Lid form processing");
            if ((lID.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || lID.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && lID.getSchemeMaster().getPageType().equalsIgnoreCase("lid")) {
                cbsSchemeLoanInterestDetails = new ArrayList<CbsSchemeLoanInterestDetailsTO>();
                CbsSchemeLoanInterestDetailsTO singleTO = new CbsSchemeLoanInterestDetailsTO();
                singleTO.setSchemeCode(lID.getSchemeMaster().getSchemeCode());
                singleTO.setSchemeType(lID.getSchemeMaster().getSchemeType());
                singleTO.setAgriLoan(lID.getAgriLoan());
                singleTO.setAodOrAosType(lID.getAodOrAosType());
                singleTO.setApplyLateFeeForDelayedPayment(lID.getApplyLateFeeForDelayedPayment());
                singleTO.setApplyPreferentialForOverdueInt(lID.getApplyPreferentialForOverdueInt());
                singleTO.setConsiderToleranceForLateFee(lID.getConsiderToleranceForLateFee());
                singleTO.setCoveredByDicge(lID.getCoveredByDicge());
                singleTO.setCreateIntDemandFromRepSchedule(lID.getCreateIntDemandFromRepSchedule());
                singleTO.setCurrencyCode(lID.getSchemeMaster().currencyType);
                singleTO.setDicgcFeeAccountPlaceholer(lID.getdICGCFeeAccountPlaceholder());
                singleTO.setDicgcFeeFlowId(lID.getdICGCFeeFlowId());
                singleTO.setGracePeriodForLateFeeDays(lID.getGracePeriodForLateFeeDays());
                singleTO.setGracePeriodForLateFeeMonths(lID.getGracePeriodForLateFeeMonths());
                singleTO.setHirerDetails(lID.getHirerDetails());
                singleTO.setIntDemandOverdueAtEndOfMonth(lID.getIntDemandOverdueAtEndOfMonth());
                singleTO.setIntLimit(new BigDecimal(lID.getIntLimit().toString()));
                singleTO.setIntOnIntDemand(lID.getIntOnIntDemand());
                singleTO.setIntOnPrincipal(lID.getIntOnPrincipal());
                singleTO.setIntOverduePeriodDays(lID.getIntOverduePeriodDays());
                singleTO.setIntOverduePeriodMonths(lID.getIntOverduePeriodMonths());
                singleTO.setIntRateBasedOnLoanAmount(lID.getIntRateBasedOnLoanAmount());
                singleTO.setOverdueIntOnPrincipal(lID.getOverdueIntOnPrincipal());
                singleTO.setPenalIntOnIntDemandOverdue(lID.getPenalIntOnIntDemandOverdue());
                singleTO.setPenalIntOnPrincipalDemandOverdue(lID.getPenalIntOnPrincipalDemandOverdue());
                singleTO.setPrincipalDemandOverdueAtEndOfMonths(lID.getPrincipalDemandOverdueAtEndOfMonths());
                singleTO.setPrincipalOverduePeriodDays(lID.getPrincipalOverduePeriodDays());
                singleTO.setPrincipalOverduePeriodMonths(lID.getPrincipalOverduePeriodMonths());
                singleTO.setPriorityLoan(lID.getPriorityLoan());
                singleTO.setRefinanceScheme(lID.getRefinanceScheme());
                singleTO.setRephasementCarryOverdueDemands(lID.getRephasementCarryOverdueDemands());
                singleTO.setSubsidyAvailable(lID.getSubsidyAvailable());
                singleTO.setToleranceLimitForDpdCycleAmount(new BigDecimal(lID.getToleranceLimitForDpdCycleAmount().toString()));
                singleTO.setToleranceLimitForDpdCycleType(lID.getToleranceLimitForDpdCycleType());
                cbsSchemeLoanInterestDetails.add(singleTO);
                if (lID.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(lID.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (lID.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(lID.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }
            /************************Addition end here********************/
            /*************************Added by Rohit*********************/
            System.out.println("In AddModifyData method for lpesd(Loan Pre Ei Setup Details) form processing");
            if ((lPESD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || lPESD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && lPESD.getSchemeMaster().getPageType().equalsIgnoreCase("lpesd")) {
                cbsSchLoanPreSetupDet = new ArrayList<CbsSchemeLoanPreEiSetupDetailsTO>();
                CbsSchemeLoanPreEiSetupDetailsTO singleTO = new CbsSchemeLoanPreEiSetupDetailsTO();
                singleTO.setSchemeCode(lPESD.getSchemeMaster().getSchemeCode());
                singleTO.setCurrencyCode(lPESD.getSchemeMaster().getCurrencyType());
                singleTO.setSchemeType(lPESD.getSchemeMaster().getSchemeType());
                singleTO.setNormalHolidayPeriodMonths(lPESD.getNormalHolidayPeriodMonths());
                singleTO.setMaxHolidayPeriodAllowedMonths(lPESD.getMaxHolidayPeriodAllowedMonths());
                singleTO.setIntDuringHolidayPeriod(lPESD.getIntDuringHolidayPeriod());
                singleTO.setIntFreqDuringHolidayPeriodType(lPESD.getIntFreqDuringHolidayPeriodType());
                singleTO.setIntFreqDuringHolidayPeriodWeekNo(lPESD.getIntFreqDuringHolidayPeriodWeekNo());
                singleTO.setIntFreqDuringHolidayPeriodWeekDay(lPESD.getIntFreqDuringHolidayPeriodWeekDay());
                singleTO.setIntFreqDuringHolidayPeriodStartDate(lPESD.getIntFreqDuringHolidayPeriodStartDate());
                singleTO.setIntFreqDuringHolidayPeriodNp(lPESD.getIntFreqDuringHolidayPeriodNp());
                singleTO.setMultipleDisbursementsAllowed(lPESD.getMultipleDisbursementsAllowed());
                singleTO.setAutoProcessAfterHolidayPeriod(lPESD.getAutoProcessAfterHolidayPeriod());
                singleTO.setOddDaysInt(new BigDecimal(lPESD.getOddDaysInt()));
                singleTO.setResidualBalanceWaiverLimit(new BigDecimal(lPESD.getResidualBalanceWaiverLimit()));
                singleTO.setResidualBalanceAbsorbLimit(new BigDecimal(lPESD.getResidualBalanceAbsorbLimit()));
                singleTO.setPlaceholderForResidualBalanceWaiver(lPESD.getPlaceholderForResidualBalanceWaiver());
                singleTO.setPlaceholderForResidualBalanceAbsorb(lPESD.getPlaceholderForResidualBalanceAbsorb());
                singleTO.setMaxCycleForPromptPaymentDiscount(new BigDecimal(lPESD.getMaxCycleForPromptPaymentDiscount()));
                singleTO.setEventIdForPromptPaymentDiscount(lPESD.getEventIDForPromptPaymentDiscount());
                cbsSchLoanPreSetupDet.add(singleTO);
                if (lPESD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(lPESD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
                if (lPESD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterPm(lPESD.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfPm();
                    }
                }
            }

            System.out.println("In AddModifyData method for pM(Parameter master) form processing");
            if ((pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A") || pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) && pM.getSchemeMaster().getPageType().equalsIgnoreCase("pm")) {
                cbsGenSchemeParamMasterDet = new ArrayList<CbsSchemeGeneralSchemeParameterMasterTO>();
                CbsSchemeGeneralSchemeParameterMasterTO singleTO = new CbsSchemeGeneralSchemeParameterMasterTO();
                singleTO.setSchemeCode(pM.getSchemeMaster().getSchemeCode());
                singleTO.setSchemeType(pM.getSchemeMaster().getSchemeType());
                singleTO.setSchemeDescription(pM.getSchemeDesc());
                singleTO.setNativeSchemeDescription(pM.getNativeSchemeDesc());
                singleTO.setRptTranFlag(pM.getRptTransFlag());
                singleTO.setSchemeSupervisorId(pM.getSchemeSupId());
                singleTO.setStmtFreqType(pM.getStmtFrqType());
                singleTO.setStmtFreqWeekNumber(pM.getStmtFrqNum());
                singleTO.setStmtFreqWeekDay(pM.getStmtFrqDay());
                singleTO.setStmtFreqStartDate(pM.getStmtFrqStartDt());
                singleTO.setStmtFreqNp(pM.getStmtFrqNP());
                singleTO.setSchemeCrDrInd(pM.getSchemeCrDrInd());
                singleTO.setTurnOverDetailFlag(pM.getTurnoverDetailFlag());
                singleTO.setSysGenAccNoFlag(pM.getSysgenAcctNoFlag());
                singleTO.setTurnOverFreqType(pM.getTurnoverFreqType());
                singleTO.setTurnOverFreqWeekNumber(pM.getTurnoverFreqNo());
                singleTO.setTurnOverFreqWeekDay(pM.getTurnoverFreqDay());
                singleTO.setTurnOverFreqStartDate(pM.getTurnoverFreqDate());
                singleTO.setTurnOverFreqNp(pM.getTurnoverFreqNp());
                singleTO.setAcctPrefix(pM.getAcctPrefix());
                singleTO.setNxtNbrTblCode(pM.getNxtNoTblCode());
                singleTO.setFcnrSchemeFlag(pM.getFcnrSchemeFlg());
                singleTO.setAcctClosuerAcrossSolsAlwdFlag(pM.getAcctCloserAcrossSols());
                singleTO.setEefcSchemeFlag(pM.getSchemeFlag());
                singleTO.setStatusOption(pM.getOpt());
                singleTO.setFdCrAcctPlaceHolder(pM.getFdCrAccountPlaceHolder());
                singleTO.setCommitCalculationMethod(pM.getCommitCalMethd());
                singleTO.setFdDrAcctPlaceHolder(pM.getFdDrAccountPlaceHolder());
                singleTO.setMinCommitUtilisation(pM.getMinCommitUtln());
                singleTO.setStaffSchemeFlag(pM.getStaffSchmeflg());
                singleTO.setDormantChargePeriodMonths(pM.getDormantChargeMonth());
                singleTO.setDormantChargePeriodDays(pM.getDormantChargeDay());
                singleTO.setNreSchemeFlag(pM.getNrSchemeFlg());
                singleTO.setInactiveChargePeriodMonths(pM.getInactiveChrgmnth());
                singleTO.setInactiveChargePeriodDays(pM.getInactiveChrgDay());
                singleTO.setNewAccountDuration(pM.getNewAcctDur());
                singleTO.setProductGroup(pM.getProductGroup());
                singleTO.setMinimumPostWorkClass(pM.getMinPostWorkClass());
                singleTO.setLinkTranToPurchaseSale(pM.getLinkTranSale());
                singleTO.setMinimumAccountClosurePeriodMonths(pM.getMinAcctClosurePeriodMonth());
                singleTO.setMinimumAccountClosurePeriodDays(pM.getMinAcctClosurePeriodDay());
                singleTO.setAccountMaintenancePeriod(pM.getAcctMaintPeriod());
                singleTO.setDfltClgTranCode(pM.getDfltClgTransCode());
                singleTO.setDfltInstrumentType(pM.getDfltInsttype());
                singleTO.setTransactionReferenceNumberFlag(pM.getTrnRefNo());
                singleTO.setPegInterestForAccountFlag(pM.getPegIntForAcctFlg());
                singleTO.setModificationofPegAllowedFlag(pM.getModificationPegFlg());
                singleTO.setPegRevCustPrefFromCustMastFlag(pM.getPegRevCust());
                singleTO.setAcctPartitionAllowed(pM.getAcctPartionAllowed());
                singleTO.setAutoRenewalPeriodFlag(pM.getAutoRenewal());
                singleTO.setPdGlSubheadCode(pM.getPdGlSubHead());
                singleTO.setStmtMessage(pM.getStmtMsg());
                singleTO.setNativeStmtMessage(pM.getNativeStmtMsg());
                if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    singleTO.setCreatedByUserId(pM.getSchemeMaster().getUserName());
                } else {
                    singleTO.setCreatedByUserId(pM.getCretedBy());
                }
                SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
                String tempCreationDt = "";
                if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    tempCreationDt = ymd.format(new Date());
                    singleTO.setCreationDate(ymd.parse(tempCreationDt));
                } else {
                    singleTO.setCreationDate(dmyFormat.parse(pM.getCreateDt()));
                }
                if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) {
                    singleTO.setLastUpdatedByUserId(pM.getSchemeMaster().getUserName());
                } else {
                    singleTO.setLastUpdatedByUserId(pM.getLastUpdateBy());
                }
                String tempLastUpdateDt = "";
                if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) {
                    tempLastUpdateDt = ymd.format(new Date());
                    singleTO.setLastUpdatedDate(ymd.parse(tempLastUpdateDt));

                } else if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    tempLastUpdateDt = ymd.format(new Date());
                    singleTO.setLastUpdatedDate(ymd.parse(tempLastUpdateDt));
                }
                if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) {
                    singleTO.setTotalModifications(Integer.parseInt(pM.getTotalModification()) + 1);
                } else if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    singleTO.setTotalModifications(Integer.parseInt(pM.getTotalModification()));
                }
                singleTO.setLiabilityExceedsGroupLimitExcep(pM.getLiabilityExceed());
                singleTO.setInsufficientAvailableBalanceExcep(pM.getInsufficientBal());
                singleTO.setAccountNameChangeExcep(pM.getAcctNameChng());
                singleTO.setStaleInstrumentExcep(pM.getStaleInstrment());
                singleTO.setAccountFrozenExcep(pM.getAcctFrozenExcep());
                singleTO.setCustomerIdMismatchDr(pM.getCustIdMismatchDr());
                singleTO.setCustomerIdMismatchCr(pM.getCustIdMismatchcr());
                singleTO.setBackDatedTransactionExcep(pM.getBackDatedTrn());
                singleTO.setValueDateTransactionExcep(pM.getValueDateTrn());
                singleTO.setCashTransactionExcep(pM.getCashTrn());
                singleTO.setAccountOpenMatrixExcep(pM.getAcctOpenMatrix());
                singleTO.setClearingTransactionExcep(pM.getClrTrnExcp());
                singleTO.setTransferTransactionExcep(pM.getTransferTrn());
                singleTO.setSanctionedLimitExpiredExcep(pM.getSanctionedLmt());
                singleTO.setSanctionedExceedsLimitExcep(pM.getSanctionedExceed());
                singleTO.setCustDrWithoutCheque(pM.getCusrDrWithoutChq());
                singleTO.setRefferedAccountClosure(pM.getRefferedAcctClosure());
                singleTO.setUserTodGrantException(pM.getUserTodGrntExcp());
                singleTO.setAutoTodGrantException(pM.getAutoTodGrntExcp());
                singleTO.setAccountInDrBalanceExp(pM.getAcctInDrBal());
                singleTO.setAccountInCrBalanceExp(pM.getAcctInCrBal());
                singleTO.setOverrideDfLtCheque(pM.getOverrideDfltChq());
                singleTO.setInvalidReportCodeExcep(pM.getInvalidReportCode());
                singleTO.setMemoPadExists(pM.getMamoPadExist());
                singleTO.setDfltIntParmChngException(pM.getDfltIntParmExcp());
                singleTO.setAcctBalBelowTheReqMin(pM.getAcctBalBelowmin());
                singleTO.setMinBalPenalChrgNotCalc(pM.getMinBalPenalChrg());
                singleTO.setIntForPastDueNotUpToDate(pM.getIntForPastDue());
                singleTO.setDrTranToPastDueAsset(pM.getDrTranToPast());
                cbsGenSchemeParamMasterDet.add(singleTO);
                if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                    SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
                    msg = delegateObj.validSchemeMasterLsd(pM.getSchemeMaster().getSchemeCode());
                    if (!msg.equalsIgnoreCase("true")) {
                        listOfLsd();
                    }
                }
            }

            /************************Addition end here********************/
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void saveUpdateSchemeMaster() {
        System.out.println("Save & Update Processing");
        try {
            String msg = "";
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            if (cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                msg = schemeMasterManagementDelegate.saveSchemeMaster(cbsSchemeCustAccountDetailsTOs, cbsSchemeAccountOpenMatrixTOs, cbsSchemeCurrencyDetailsTOs, cbsSchemeCashCrBillsAndOverdraftDetailsTOs, depositOverDueIntParameter, GlSubHeadSchemeTOs, cbsSchDepSchParaMainTOs, cbsSchIntSerChgDtls, cbsSchLoanExpDtls, schValidComplexTO, ledgerFolioDtls, loanRepaymentCyclDefs, feeOrChargesDtls, transRepCode, schemeTodRefdetails, todExceptionDetails, schemeDocumentdetails, depositFlowTO, loanAssetTO, depositIntTO, cbsSchemeFlexiFixedDepositsDetailsTOs, cbsSchemeLoanPrepaymentDetailsTOs, cbsSchemeLoanSchemeDetailsTOs, cbsSchemeCaSbSchDetailsTOs, deliqDetailsTOs, cbsSchemeLoanInterestDetails, cbsSchLoanPreSetupDet, cbsGenSchemeParamMasterDet);
                if (msg.equalsIgnoreCase("true")) {
                    cAD.getSchemeMaster().setMessage("Data has been saved successfully");
                } else {
                    cAD.getSchemeMaster().setMessage("There is problem to save the data");
                }
                refreshList();
                //new SchemeMaster().refresh();
            }
            if (cAD.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) {
                msg = schemeMasterManagementDelegate.updateSchemeMaster(cbsSchemeCustAccountDetailsTOs, cbsSchemeAccountOpenMatrixTOs, cbsSchemeCurrencyDetailsTOs, cbsSchemeCashCrBillsAndOverdraftDetailsTOs, depositOverDueIntParameter, GlSubHeadSchemeTOs, cbsSchDepSchParaMainTOs, cbsSchIntSerChgDtls, cbsSchLoanExpDtls, schValidComplexTO, ledgerFolioDtls, loanRepaymentCyclDefs, feeOrChargesDtls, transRepCode, schemeTodRefdetails, todExceptionDetails, schemeDocumentdetails, depositFlowTO, loanAssetTO, depositIntTO, cbsSchemeFlexiFixedDepositsDetailsTOs, cbsSchemeLoanPrepaymentDetailsTOs, cbsSchemeLoanSchemeDetailsTOs, cbsSchemeCaSbSchDetailsTOs, deliqDetailsTOs, cbsSchemeLoanInterestDetails, cbsSchLoanPreSetupDet, cbsGenSchemeParamMasterDet);
                if (msg.equalsIgnoreCase("true")) {
                    cAD.getSchemeMaster().setMessage("Data has been updated successfully");
                } else {
                    cAD.getSchemeMaster().setMessage("There is problem to update the data");
                }
                refreshList();
                //new SchemeMaster().refresh();
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void listOfPm() {
        try {
            cbsGenSchemeParamMasterDet = new ArrayList<CbsSchemeGeneralSchemeParameterMasterTO>();
            CbsSchemeGeneralSchemeParameterMasterTO singleTO = new CbsSchemeGeneralSchemeParameterMasterTO();
            singleTO.setSchemeCode(cAD.getSchemeMaster().getSchemeCode());
            singleTO.setSchemeType(cAD.getSchemeMaster().getSchemeCode().substring(0, 2));
            singleTO.setSchemeDescription("");
            singleTO.setNativeSchemeDescription("");
            singleTO.setRptTranFlag("");
            singleTO.setSchemeSupervisorId("");
            singleTO.setStmtFreqType("");
            singleTO.setStmtFreqWeekNumber("");
            singleTO.setStmtFreqWeekDay("");
            singleTO.setStmtFreqStartDate("");
            singleTO.setStmtFreqNp("");
            singleTO.setSchemeCrDrInd("");
            singleTO.setTurnOverDetailFlag("");
            singleTO.setSysGenAccNoFlag("");
            singleTO.setTurnOverFreqType("");
            singleTO.setTurnOverFreqWeekNumber("");
            singleTO.setTurnOverFreqWeekDay("");
            singleTO.setTurnOverFreqStartDate("");
            singleTO.setTurnOverFreqNp("");
            singleTO.setAcctPrefix("");
            singleTO.setNxtNbrTblCode("");
            singleTO.setFcnrSchemeFlag("");
            singleTO.setAcctClosuerAcrossSolsAlwdFlag("");
            singleTO.setEefcSchemeFlag("");
            singleTO.setStatusOption("");
            singleTO.setFdCrAcctPlaceHolder("");
            singleTO.setCommitCalculationMethod("");
            singleTO.setFdDrAcctPlaceHolder("");
            singleTO.setMinCommitUtilisation(new BigDecimal("0"));
            singleTO.setStaffSchemeFlag("");
            singleTO.setDormantChargePeriodMonths("");
            singleTO.setDormantChargePeriodDays("");
            singleTO.setNreSchemeFlag("");
            singleTO.setInactiveChargePeriodMonths("");
            singleTO.setInactiveChargePeriodDays("");
            singleTO.setNewAccountDuration("");
            singleTO.setProductGroup("");
            singleTO.setMinimumPostWorkClass("");
            singleTO.setLinkTranToPurchaseSale("");
            singleTO.setMinimumAccountClosurePeriodMonths("");
            singleTO.setMinimumAccountClosurePeriodDays("");
            singleTO.setAccountMaintenancePeriod("");
            singleTO.setDfltClgTranCode("");
            singleTO.setDfltInstrumentType("");
            singleTO.setTransactionReferenceNumberFlag("");
            singleTO.setPegInterestForAccountFlag("");
            singleTO.setModificationofPegAllowedFlag("");
            singleTO.setPegRevCustPrefFromCustMastFlag("");
            singleTO.setAcctPartitionAllowed("");
            singleTO.setAutoRenewalPeriodFlag("");
            singleTO.setPdGlSubheadCode("");
            singleTO.setStmtMessage("");
            singleTO.setNativeStmtMessage("");
            if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                singleTO.setCreatedByUserId("");
            } else {
                singleTO.setCreatedByUserId("");
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            String tempCreationDt = "";
            if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                tempCreationDt = ymd.format(new Date());
                singleTO.setCreationDate(ymd.parse(tempCreationDt));
            } else {
                singleTO.setCreationDate(dmyFormat.parse(pM.getCreateDt()));
            }
            if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) {
                singleTO.setLastUpdatedByUserId("");
            } else {
                singleTO.setLastUpdatedByUserId("");
            }
            String tempLastUpdateDt = "";
            if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) {
                tempLastUpdateDt = ymd.format(new Date());
                singleTO.setLastUpdatedDate(ymd.parse(tempLastUpdateDt));

            } else if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                tempLastUpdateDt = ymd.format(new Date());
                singleTO.setLastUpdatedDate(ymd.parse(tempLastUpdateDt));
            }
            if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("M")) {
                singleTO.setTotalModifications(Integer.parseInt("0"));
            } else if (pM.getSchemeMaster().getFunctionFlag().equalsIgnoreCase("A")) {
                singleTO.setTotalModifications(Integer.parseInt("0"));
            }
            singleTO.setLiabilityExceedsGroupLimitExcep("");
            singleTO.setInsufficientAvailableBalanceExcep("");
            singleTO.setAccountNameChangeExcep("");
            singleTO.setStaleInstrumentExcep("");
            singleTO.setAccountFrozenExcep("");
            singleTO.setCustomerIdMismatchDr("");
            singleTO.setCustomerIdMismatchCr("");
            singleTO.setBackDatedTransactionExcep("");
            singleTO.setValueDateTransactionExcep("");
            singleTO.setCashTransactionExcep("");
            singleTO.setAccountOpenMatrixExcep("");
            singleTO.setClearingTransactionExcep("");
            singleTO.setTransferTransactionExcep("");
            singleTO.setSanctionedLimitExpiredExcep("");
            singleTO.setSanctionedExceedsLimitExcep("");
            singleTO.setCustDrWithoutCheque("");
            singleTO.setRefferedAccountClosure("");
            singleTO.setUserTodGrantException("");
            singleTO.setAutoTodGrantException("");
            singleTO.setAccountInDrBalanceExp("");
            singleTO.setAccountInCrBalanceExp("");
            singleTO.setOverrideDfLtCheque("");
            singleTO.setInvalidReportCodeExcep("");
            singleTO.setMemoPadExists("");
            singleTO.setDfltIntParmChngException("");
            singleTO.setAcctBalBelowTheReqMin("");
            singleTO.setMinBalPenalChrgNotCalc("");
            singleTO.setIntForPastDueNotUpToDate("");
            singleTO.setDrTranToPastDueAsset("");
            cbsGenSchemeParamMasterDet.add(singleTO);
        } catch (Exception e) {
        }

    }

    public void listOfLsd() {
        cbsSchemeLoanSchemeDetailsTOs = new ArrayList<CbsSchemeLoanSchemeDetailsTO>();
        CbsSchemeLoanSchemeDetailsTO cbsSchemeLoanSchemeDetailsTO = new CbsSchemeLoanSchemeDetailsTO();
        cbsSchemeLoanSchemeDetailsTO.setSchemeCode(cAD.getSchemeMaster().getSchemeCode());
        cbsSchemeLoanSchemeDetailsTO.setCurrencyCode(cAD.getSchemeMaster().getCurrencyType());
        cbsSchemeLoanSchemeDetailsTO.setSchemeType(cAD.getSchemeMaster().getSchemeType());
        cbsSchemeLoanSchemeDetailsTO.setLoanPeriodMiniMonths("");
        cbsSchemeLoanSchemeDetailsTO.setLoanPeriodMiniDays("");
        cbsSchemeLoanSchemeDetailsTO.setLoanPeriodMaxMonths("");
        cbsSchemeLoanSchemeDetailsTO.setLoanPeriodMaxDays("");
        cbsSchemeLoanSchemeDetailsTO.setLoanAmountMin(new BigDecimal("0"));
        cbsSchemeLoanSchemeDetailsTO.setLoanAmountMax(new BigDecimal("0"));
        cbsSchemeLoanSchemeDetailsTO.setLoanRepaymentMethod("");
        cbsSchemeLoanSchemeDetailsTO.setHoldInOpenAccountForAmountDue("");
        cbsSchemeLoanSchemeDetailsTO.setUpfrontInstallmentCollection("");
        cbsSchemeLoanSchemeDetailsTO.setIntBaseMethod("");
        cbsSchemeLoanSchemeDetailsTO.setIntProductMethod("");
        cbsSchemeLoanSchemeDetailsTO.setIntRouteFlag("");
        cbsSchemeLoanSchemeDetailsTO.setChrargeRouteFlag("");
        cbsSchemeLoanSchemeDetailsTO.setLoanIntOrChrgAccountPlaceholder("");
        cbsSchemeLoanSchemeDetailsTO.setEquatedInstallments("");
        cbsSchemeLoanSchemeDetailsTO.setEiInAdvance("");
        cbsSchemeLoanSchemeDetailsTO.setEiFormulaFlag("");
        cbsSchemeLoanSchemeDetailsTO.setEiRoundingOffAmount("");
        cbsSchemeLoanSchemeDetailsTO.setEiRoundingOffInd("");
        cbsSchemeLoanSchemeDetailsTO.setCompoundingFreq("");
        cbsSchemeLoanSchemeDetailsTO.setEiPaymentFreq("");
        cbsSchemeLoanSchemeDetailsTO.setInterestRestFreq("");
        cbsSchemeLoanSchemeDetailsTO.setInterestRestBasis("");
        cbsSchemeLoanSchemeDetailsTO.setUpfrontInterestCollection("");
        cbsSchemeLoanSchemeDetailsTO.setDiscountedInterest("");
        cbsSchemeLoanSchemeDetailsTO.setIntAmortizationByRule78("");
        cbsSchemeLoanSchemeDetailsTOs.add(cbsSchemeLoanSchemeDetailsTO);

    }

    public void refreshList() {
        cbsSchemeCustAccountDetailsTOs = null;
        cbsSchemeAccountOpenMatrixTOs = null;
        cbsSchemeCurrencyDetailsTOs = null;
        cbsSchemeCashCrBillsAndOverdraftDetailsTOs = null;
        depositOverDueIntParameter = null;
        GlSubHeadSchemeTOs = null;
        cbsSchDepSchParaMainTOs = null;
        cbsSchIntSerChgDtls = null;
        cbsSchLoanExpDtls = null;
        schValidComplexTO = null;
        ledgerFolioDtls = null;
        loanRepaymentCyclDefs = null;
        feeOrChargesDtls = null;
        transRepCode = null;
        schemeTodRefdetails = null;
        todExceptionDetails = null;
        schemeDocumentdetails = null;
        depositFlowTO = null;
        loanAssetTO = null;
        depositIntTO = null;
        cbsSchemeFlexiFixedDepositsDetailsTOs = null;
        cbsSchemeLoanPrepaymentDetailsTOs = null;
        cbsSchemeLoanSchemeDetailsTOs = null;
        cbsSchemeCaSbSchDetailsTOs = null;
        deliqDetailsTOs = null;
        cbsSchemeLoanInterestDetails = null;
        cbsSchLoanPreSetupDet = null;
        cbsGenSchemeParamMasterDet = null;
    }
}
