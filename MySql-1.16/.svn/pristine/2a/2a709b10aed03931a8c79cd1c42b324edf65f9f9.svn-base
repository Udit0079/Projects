/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeCashCrBillsAndOverdraftDetailsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class CRBOSD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for crbosd.jsp file
    private String maxSanction;
    private String debitBalance;
    private String ledgerFolio;
    private String inactiveAccount;
    private String dormantAccount;
    private String maximunPenal;
    private String durationToMark;
    private String durationFrom;
    private String dormantAccountTranlimit;
    private String inactiveAccountabrml;
    private String allowSweeps;
    private String debitAgainst;
    private String debitBalanceabrnml;
    private String stdebitBalanceabrnml;
    private String bPPtranOutside;
    private String stbPPtranOutside;
    private String sancOrDisbExceed;
    private String stsancOrDisbExceed;
    private String sancOrDisbWithout;
    private String stsancOrDisbWithout;
    private String ciTranToInact;
    private String stciTranToInact;
    private String cIDRTranTo;
    private String stcIDRTranTo;
    private String bIDRTranToDorman;
    private String stbIDRTranToDorman;
    private String chequeIssued;
    private String stchequeIssued;
    private String chequeIssuedToDormant;
    private String stchequeIssuedToDormant;
    private String sanctionLimitCompletely;
    private String stsanctionLimitCompletely;
    private String acctMiniBalBelowScheme;
    private String stacctMiniBalBelowScheme;
    private boolean crbosdFlag;
    private List<SelectItem> ddCrbosdTrnRefNo;

    //Getter-Setter for crbosd.jsp file
    public String getAcctMiniBalBelowScheme() {
        return acctMiniBalBelowScheme;
    }

    public void setAcctMiniBalBelowScheme(String acctMiniBalBelowScheme) {
        this.acctMiniBalBelowScheme = acctMiniBalBelowScheme;
    }

    public String getAllowSweeps() {
        return allowSweeps;
    }

    public void setAllowSweeps(String allowSweeps) {
        this.allowSweeps = allowSweeps;
    }

    public String getbIDRTranToDorman() {
        return bIDRTranToDorman;
    }

    public void setbIDRTranToDorman(String bIDRTranToDorman) {
        this.bIDRTranToDorman = bIDRTranToDorman;
    }

    public String getbPPtranOutside() {
        return bPPtranOutside;
    }

    public void setbPPtranOutside(String bPPtranOutside) {
        this.bPPtranOutside = bPPtranOutside;
    }

    public String getcIDRTranTo() {
        return cIDRTranTo;
    }

    public void setcIDRTranTo(String cIDRTranTo) {
        this.cIDRTranTo = cIDRTranTo;
    }

    public String getChequeIssued() {
        return chequeIssued;
    }

    public void setChequeIssued(String chequeIssued) {
        this.chequeIssued = chequeIssued;
    }

    public String getChequeIssuedToDormant() {
        return chequeIssuedToDormant;
    }

    public void setChequeIssuedToDormant(String chequeIssuedToDormant) {
        this.chequeIssuedToDormant = chequeIssuedToDormant;
    }

    public String getCiTranToInact() {
        return ciTranToInact;
    }

    public void setCiTranToInact(String ciTranToInact) {
        this.ciTranToInact = ciTranToInact;
    }

    public String getDebitAgainst() {
        return debitAgainst;
    }

    public void setDebitAgainst(String debitAgainst) {
        this.debitAgainst = debitAgainst;
    }

    public String getDebitBalance() {
        return debitBalance;
    }

    public void setDebitBalance(String debitBalance) {
        this.debitBalance = debitBalance;
    }

    public String getDebitBalanceabrnml() {
        return debitBalanceabrnml;
    }

    public void setDebitBalanceabrnml(String debitBalanceabrnml) {
        this.debitBalanceabrnml = debitBalanceabrnml;
    }

    public String getDormantAccount() {
        return dormantAccount;
    }

    public void setDormantAccount(String dormantAccount) {
        this.dormantAccount = dormantAccount;
    }

    public String getDormantAccountTranlimit() {
        return dormantAccountTranlimit;
    }

    public void setDormantAccountTranlimit(String dormantAccountTranlimit) {
        this.dormantAccountTranlimit = dormantAccountTranlimit;
    }

    public String getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(String durationFrom) {
        this.durationFrom = durationFrom;
    }

    public String getDurationToMark() {
        return durationToMark;
    }

    public void setDurationToMark(String durationToMark) {
        this.durationToMark = durationToMark;
    }

    public String getInactiveAccount() {
        return inactiveAccount;
    }

    public void setInactiveAccount(String inactiveAccount) {
        this.inactiveAccount = inactiveAccount;
    }

    public String getInactiveAccountabrml() {
        return inactiveAccountabrml;
    }

    public void setInactiveAccountabrml(String inactiveAccountabrml) {
        this.inactiveAccountabrml = inactiveAccountabrml;
    }

    public String getLedgerFolio() {
        return ledgerFolio;
    }

    public void setLedgerFolio(String ledgerFolio) {
        this.ledgerFolio = ledgerFolio;
    }

    public String getMaxSanction() {
        return maxSanction;
    }

    public void setMaxSanction(String maxSanction) {
        this.maxSanction = maxSanction;
    }

    public String getMaximunPenal() {
        return maximunPenal;
    }

    public void setMaximunPenal(String maximunPenal) {
        this.maximunPenal = maximunPenal;
    }

    public String getSancOrDisbExceed() {
        return sancOrDisbExceed;
    }

    public void setSancOrDisbExceed(String sancOrDisbExceed) {
        this.sancOrDisbExceed = sancOrDisbExceed;
    }

    public String getSancOrDisbWithout() {
        return sancOrDisbWithout;
    }

    public void setSancOrDisbWithout(String sancOrDisbWithout) {
        this.sancOrDisbWithout = sancOrDisbWithout;
    }

    public String getSanctionLimitCompletely() {
        return sanctionLimitCompletely;
    }

    public void setSanctionLimitCompletely(String sanctionLimitCompletely) {
        this.sanctionLimitCompletely = sanctionLimitCompletely;
    }

    public String getStacctMiniBalBelowScheme() {
        return stacctMiniBalBelowScheme;
    }

    public void setStacctMiniBalBelowScheme(String stacctMiniBalBelowScheme) {
        this.stacctMiniBalBelowScheme = stacctMiniBalBelowScheme;
    }

    public String getStbIDRTranToDorman() {
        return stbIDRTranToDorman;
    }

    public void setStbIDRTranToDorman(String stbIDRTranToDorman) {
        this.stbIDRTranToDorman = stbIDRTranToDorman;
    }

    public String getStbPPtranOutside() {
        return stbPPtranOutside;
    }

    public void setStbPPtranOutside(String stbPPtranOutside) {
        this.stbPPtranOutside = stbPPtranOutside;
    }

    public String getStcIDRTranTo() {
        return stcIDRTranTo;
    }

    public void setStcIDRTranTo(String stcIDRTranTo) {
        this.stcIDRTranTo = stcIDRTranTo;
    }

    public String getStchequeIssued() {
        return stchequeIssued;
    }

    public void setStchequeIssued(String stchequeIssued) {
        this.stchequeIssued = stchequeIssued;
    }

    public String getStchequeIssuedToDormant() {
        return stchequeIssuedToDormant;
    }

    public void setStchequeIssuedToDormant(String stchequeIssuedToDormant) {
        this.stchequeIssuedToDormant = stchequeIssuedToDormant;
    }

    public String getStciTranToInact() {
        return stciTranToInact;
    }

    public void setStciTranToInact(String stciTranToInact) {
        this.stciTranToInact = stciTranToInact;
    }

    public String getStdebitBalanceabrnml() {
        return stdebitBalanceabrnml;
    }

    public void setStdebitBalanceabrnml(String stdebitBalanceabrnml) {
        this.stdebitBalanceabrnml = stdebitBalanceabrnml;
    }

    public String getStsancOrDisbExceed() {
        return stsancOrDisbExceed;
    }

    public void setStsancOrDisbExceed(String stsancOrDisbExceed) {
        this.stsancOrDisbExceed = stsancOrDisbExceed;
    }

    public String getStsancOrDisbWithout() {
        return stsancOrDisbWithout;
    }

    public void setStsancOrDisbWithout(String stsancOrDisbWithout) {
        this.stsancOrDisbWithout = stsancOrDisbWithout;
    }

    public String getStsanctionLimitCompletely() {
        return stsanctionLimitCompletely;
    }

    public void setStsanctionLimitCompletely(String stsanctionLimitCompletely) {
        this.stsanctionLimitCompletely = stsanctionLimitCompletely;
    }

    public boolean isCrbosdFlag() {
        return crbosdFlag;
    }

    public void setCrbosdFlag(boolean crbosdFlag) {
        this.crbosdFlag = crbosdFlag;
    }

    public List<SelectItem> getDdCrbosdTrnRefNo() {
        return ddCrbosdTrnRefNo;
    }

    public void setDdCrbosdTrnRefNo(List<SelectItem> ddCrbosdTrnRefNo) {
        this.ddCrbosdTrnRefNo = ddCrbosdTrnRefNo;
    }

    /** Creates a new instance of CRBOSD */
    public CRBOSD() {
        try {
            ddCrbosdTrnRefNo = new ArrayList<SelectItem>();
            ddCrbosdTrnRefNo.add(new SelectItem("0", ""));
            ddCrbosdTrnRefNo.add(new SelectItem("Y", "Yes"));
            ddCrbosdTrnRefNo.add(new SelectItem("N", "No"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Functionality for crbosd.jsp file
    public void descdebitBalanceabrnml() {
        schemeMaster.setMessage("");
        try {
            if (this.debitBalanceabrnml.equals("")) {
                this.setStdebitBalanceabrnml("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.debitBalanceabrnml);
                this.setStdebitBalanceabrnml(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStdebitBalanceabrnml("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descbPPtranOutside() {
        schemeMaster.setMessage("");
        try {
            if (this.bPPtranOutside.equals("")) {
                this.setStbPPtranOutside("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.bPPtranOutside);
                this.setStbPPtranOutside(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStbPPtranOutside("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descsancOrDisbExceed() {
        schemeMaster.setMessage("");
        try {
            if (this.sancOrDisbExceed.equals("")) {
                this.setStsancOrDisbExceed("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.sancOrDisbExceed);
                this.setStsancOrDisbExceed(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStsancOrDisbExceed("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descSancOrDisbWithout() {
        schemeMaster.setMessage("");
        try {
            if (this.sancOrDisbWithout.equals("")) {
                this.setStsancOrDisbWithout("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.sancOrDisbWithout);
                this.setStsancOrDisbWithout(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStsancOrDisbWithout("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCiTranToInact() {
        schemeMaster.setMessage("");
        try {
            if (this.ciTranToInact.equals("")) {
                this.setStciTranToInact("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.ciTranToInact);
                this.setStciTranToInact(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStciTranToInact("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCIDRTranTo() {
        schemeMaster.setMessage("");
        try {
            if (this.cIDRTranTo.equals("")) {
                this.setStcIDRTranTo("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.cIDRTranTo);
                this.setStcIDRTranTo(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStcIDRTranTo("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descBIDRTranToDorman() {
        schemeMaster.setMessage("");
        try {
            if (this.bIDRTranToDorman.equals("")) {
                this.setStbIDRTranToDorman("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.bIDRTranToDorman);
                this.setStbIDRTranToDorman(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStbIDRTranToDorman("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descChequeIssued() {
        schemeMaster.setMessage("");
        try {
            if (this.chequeIssued.equals("")) {
                this.setStchequeIssued("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeIssued);
                this.setStchequeIssued(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStchequeIssued("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descChequeIssuedToDormant() {
        schemeMaster.setMessage("");
        try {
            if (this.chequeIssuedToDormant.equals("")) {
                this.setStchequeIssuedToDormant("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeIssuedToDormant);
                this.setStchequeIssuedToDormant(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStchequeIssuedToDormant("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descSanctionLimitCompletely() {
        schemeMaster.setMessage("");
        try {
            if (this.sanctionLimitCompletely.equals("")) {
                this.setStsanctionLimitCompletely("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.sanctionLimitCompletely);
                this.setStsanctionLimitCompletely(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStsanctionLimitCompletely("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descacctMiniBalBelowScheme() {
        schemeMaster.setMessage("");
        try {
            if (this.acctMiniBalBelowScheme.equals("")) {
                this.setStacctMiniBalBelowScheme("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.acctMiniBalBelowScheme);
                this.setStacctMiniBalBelowScheme(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStacctMiniBalBelowScheme("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void selectCrBosdDetails() {
        
        schemeMaster.setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeCashCrBillsAndOverdraftDetailsTO cbsSchemeCashCrBillsAndOverdraftDetailsTO = schemeMasterManagementDelegate.getCrBosddetails(schemeMaster.getSchemeCode());
            if (cbsSchemeCashCrBillsAndOverdraftDetailsTO != null) {

                this.setMaxSanction(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getMaxSanctionLimit().toString());
                this.setDebitBalance(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDebitBalanceLimit().toString());
                this.setLedgerFolio(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getLedgerFolioChargeOrFolio().toString());
                this.setInactiveAccount(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getInactiveAccountAbrnmlTranLimit().toString());
                this.setDormantAccount(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDormantAccountAbrnmlTranLimit().toString());
                this.setMaximunPenal(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getMaximumPenalInterest().toString());

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDurationtoMarkAccountAsInactive() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDurationtoMarkAccountAsInactive().equalsIgnoreCase("")) {
                    this.setDurationToMark("");
                } else {
                    this.setDurationToMark(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDurationtoMarkAccountAsInactive());
                }

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDurationFromInactiveToDormant() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDurationFromInactiveToDormant().equalsIgnoreCase("")) {
                    this.setDurationFrom("");
                } else {
                    this.setDurationFrom(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDurationFromInactiveToDormant());
                }

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDormantAccountChargeEvent() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDormantAccountChargeEvent().equalsIgnoreCase("")) {
                    this.setDormantAccountTranlimit("");
                } else {
                    this.setDormantAccountTranlimit(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDormantAccountChargeEvent());
                }

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getInactiveAccountChargeEvent() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getInactiveAccountChargeEvent().equalsIgnoreCase("")) {
                    this.setInactiveAccountabrml("");
                } else {
                    this.setInactiveAccountabrml(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getInactiveAccountChargeEvent());
                }

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getAllowSweeps() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getAllowSweeps().equalsIgnoreCase("")) {
                    this.setAllowSweeps("0");
                } else {
                    this.setAllowSweeps(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getAllowSweeps());
                }

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDebitAgainstUnclearBalance() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDebitAgainstUnclearBalance().equalsIgnoreCase("")) {
                    this.setDebitAgainst("0");
                } else {
                    this.setDebitAgainst(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDebitAgainstUnclearBalance());
                }

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDebitBalanceLimitExec() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDebitBalanceLimitExec().equalsIgnoreCase("")) {
                    this.setDebitBalanceabrnml("");
                } else {
                    this.setDebitBalanceabrnml(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getDebitBalanceLimitExec());
                    descdebitBalanceabrnml();
                }

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getBpPtranOutsideBills() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getBpPtranOutsideBills().equalsIgnoreCase("")) {
                    this.setbPPtranOutside("");
                } else {
                    this.setbPPtranOutside(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getBpPtranOutsideBills());
                    descbPPtranOutside();
                }

                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSancOrDisbExceedExpOrdOrDc() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSancOrDisbExceedExpOrdOrDc().equalsIgnoreCase("")) {
                    this.setSancOrDisbExceed("");
                } else {
                    this.setSancOrDisbExceed(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSancOrDisbExceedExpOrdOrDc());
                    descsancOrDisbExceed();
                }
                
                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSancOrDisbWithoutExpOrdOrDc() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSancOrDisbWithoutExpOrdOrDc().equalsIgnoreCase("")) {
                    this.setSancOrDisbWithout("");
                } else {
                    this.setSancOrDisbWithout(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSancOrDisbWithoutExpOrdOrDc());
                    descSancOrDisbWithout();
                }
                
                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getCiTranToInactiveAcct() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getCiTranToInactiveAcct().equalsIgnoreCase("")) {
                    this.setCiTranToInact("");
                } else {
                    this.setCiTranToInact(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getCiTranToInactiveAcct());
                    descCiTranToInact();
                }
                
                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getCiDrTranToDormantAcct() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getCiDrTranToDormantAcct().equalsIgnoreCase("")) {
                    this.setcIDRTranTo("");
                } else {
                    this.setcIDRTranTo(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getCiDrTranToDormantAcct());
                    descCIDRTranTo();
                }
                
                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getBiDrTranToDormantAcct() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getBiDrTranToDormantAcct().equalsIgnoreCase("")) {
                    this.setbIDRTranToDorman("");
                } else {
                    this.setbIDRTranToDorman(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getBiDrTranToDormantAcct());
                    descBIDRTranToDorman();
                }
                
                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getChequeIssuedToInactiveAcct() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getChequeIssuedToInactiveAcct().equalsIgnoreCase("")) {
                    this.setChequeIssued("");
                } else {
                    this.setChequeIssued(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getChequeIssuedToInactiveAcct());
                    descChequeIssued();
                }
                
                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getChequeIssuedToDormantAcct() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getChequeIssuedToDormantAcct().equalsIgnoreCase("")) {
                    this.setChequeIssuedToDormant("");
                } else {
                    this.setChequeIssuedToDormant(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getChequeIssuedToDormantAcct());
                    descChequeIssuedToDormant();
                }
                
                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSanctionLimitCompletelyUtilised() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSanctionLimitCompletelyUtilised().equalsIgnoreCase("")) {
                    this.setSanctionLimitCompletely("");
                } else {
                    this.setSanctionLimitCompletely(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getSanctionLimitCompletelyUtilised());
                    descSanctionLimitCompletely();
                }
                
                if (cbsSchemeCashCrBillsAndOverdraftDetailsTO.getAcctMiniBalBelowSchemeMinBal() == null || cbsSchemeCashCrBillsAndOverdraftDetailsTO.getAcctMiniBalBelowSchemeMinBal().equalsIgnoreCase("")) {
                    this.setAcctMiniBalBelowScheme("");
                } else {
                    this.setAcctMiniBalBelowScheme(cbsSchemeCashCrBillsAndOverdraftDetailsTO.getAcctMiniBalBelowSchemeMinBal());
                    descacctMiniBalBelowScheme();
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refreshCrBosdForm() {
        this.setMaxSanction("0.00");
        this.setDebitBalance("0.00");
        this.setLedgerFolio("0.00");
        this.setInactiveAccount("0.00");
        this.setDormantAccount("0.00");
        this.setMaximunPenal("0.00");
        this.setDurationToMark("");
        this.setDurationFrom("");
        this.setDormantAccountTranlimit("");
        this.setInactiveAccountabrml("");
        this.setAllowSweeps("0");
        this.setDebitAgainst("0");
        this.setDebitBalanceabrnml("");
        this.setStdebitBalanceabrnml("");
        this.setbPPtranOutside("");
        this.setStbPPtranOutside("");
        this.setSancOrDisbExceed("");
        this.setStsancOrDisbExceed("");
        this.setSancOrDisbWithout("");
        this.setStsancOrDisbWithout("");
        this.setCiTranToInact("");
        this.setStciTranToInact("");
        this.setcIDRTranTo("");
        this.setStcIDRTranTo("");
        this.setbIDRTranToDorman("");
        this.setStbIDRTranToDorman("");
        this.setChequeIssued("");
        this.setStchequeIssued("");
        this.setChequeIssuedToDormant("");
        this.setStchequeIssuedToDormant("");
        this.setSanctionLimitCompletely("");
        this.setStsanctionLimitCompletely("");
        this.setAcctMiniBalBelowScheme("");
        this.setStacctMiniBalBelowScheme("");
    }

    public void resetAllLimit() {
        this.setMaxSanction("0.00");
        this.setDebitBalance("0.00");
        this.setLedgerFolio("0.00");
        this.setInactiveAccount("0.00");
        this.setDormantAccount("0.00");
        this.setMaximunPenal("0.00");
    }

    public void enableCrBosdForm() {
        this.crbosdFlag = false;
    }

    public void disableCrBosdForm() {
        this.crbosdFlag = true;
    }
}
