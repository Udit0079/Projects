/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

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
public class DOIP {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for doip.jsp file
    private String overdueGLSubheadCode;
    private String overdueIntCode;
    private String overdueIntTblCode;
    private List<SelectItem> overdueIntTblCodeList;
    private String overdueIntCalcMethod;
    private List<SelectItem> overdueIntCalcMethodList;
    private String overdueIntCalcMethodValue;
    private String renewalPerdExcd;
    private String maxPerd;
    private String maxAmt;
    private String minorDepPreclosure;
    private String extension;
    private String splCatgClosure;
    private String matAmtTolerance;
    private String nilPenalty;
    private String disContinuedInst;
    private String transferIn;
    private String acctVerBalCheck;
    private String systemDrTransAllowed;
    private String dupReprntRcpt;
    private String preMatureClosure;
    private String noticePerdMinNoticePerd;
    private String defaultValueForPreIntChgd;
    private String backValueDateAccOpen;
    private String futureValueDateAccOpen;
    private String renewalPerdExcdValue;
    private String maxPerdValue;
    private String maxAmtValue;
    private String minorDepPreclosureValue;
    private String extensionValue;
    private String splCatgClosureValue;
    private String matAmtToleranceValue;
    private String nilPenaltyValue;
    private String disContinuedInstValue;
    private String transferInValue;
    private String acctVerBalCheckValue;
    private String systemDrTransAllowedValue;
    private String dupReprntRcptValue;
    private String preMatureClosureValue;
    private String noticePerdMinNoticePerdValue;
    private String defaultValueForPreIntChgdValue;
    private String backValueDateAccOpenValue;
    private String futureValueDateAccOpenValue;
    private boolean disableFlag;

    //Getter-Setter for doip.jsp file
    public String getAcctVerBalCheck() {
        return acctVerBalCheck;
    }

    public void setAcctVerBalCheck(String acctVerBalCheck) {
        this.acctVerBalCheck = acctVerBalCheck;
    }

    public String getAcctVerBalCheckValue() {
        return acctVerBalCheckValue;
    }

    public void setAcctVerBalCheckValue(String acctVerBalCheckValue) {
        this.acctVerBalCheckValue = acctVerBalCheckValue;
    }

    public String getBackValueDateAccOpen() {
        return backValueDateAccOpen;
    }

    public void setBackValueDateAccOpen(String backValueDateAccOpen) {
        this.backValueDateAccOpen = backValueDateAccOpen;
    }

    public String getBackValueDateAccOpenValue() {
        return backValueDateAccOpenValue;
    }

    public void setBackValueDateAccOpenValue(String backValueDateAccOpenValue) {
        this.backValueDateAccOpenValue = backValueDateAccOpenValue;
    }

    public String getDefaultValueForPreIntChgd() {
        return defaultValueForPreIntChgd;
    }

    public void setDefaultValueForPreIntChgd(String defaultValueForPreIntChgd) {
        this.defaultValueForPreIntChgd = defaultValueForPreIntChgd;
    }

    public String getDefaultValueForPreIntChgdValue() {
        return defaultValueForPreIntChgdValue;
    }

    public void setDefaultValueForPreIntChgdValue(String defaultValueForPreIntChgdValue) {
        this.defaultValueForPreIntChgdValue = defaultValueForPreIntChgdValue;
    }

    public String getDisContinuedInst() {
        return disContinuedInst;
    }

    public void setDisContinuedInst(String disContinuedInst) {
        this.disContinuedInst = disContinuedInst;
    }

    public String getDisContinuedInstValue() {
        return disContinuedInstValue;
    }

    public void setDisContinuedInstValue(String disContinuedInstValue) {
        this.disContinuedInstValue = disContinuedInstValue;
    }

    public String getDupReprntRcpt() {
        return dupReprntRcpt;
    }

    public void setDupReprntRcpt(String dupReprntRcpt) {
        this.dupReprntRcpt = dupReprntRcpt;
    }

    public String getDupReprntRcptValue() {
        return dupReprntRcptValue;
    }

    public void setDupReprntRcptValue(String dupReprntRcptValue) {
        this.dupReprntRcptValue = dupReprntRcptValue;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtensionValue() {
        return extensionValue;
    }

    public void setExtensionValue(String extensionValue) {
        this.extensionValue = extensionValue;
    }

    public String getFutureValueDateAccOpen() {
        return futureValueDateAccOpen;
    }

    public void setFutureValueDateAccOpen(String futureValueDateAccOpen) {
        this.futureValueDateAccOpen = futureValueDateAccOpen;
    }

    public String getFutureValueDateAccOpenValue() {
        return futureValueDateAccOpenValue;
    }

    public void setFutureValueDateAccOpenValue(String futureValueDateAccOpenValue) {
        this.futureValueDateAccOpenValue = futureValueDateAccOpenValue;
    }

    public String getMatAmtTolerance() {
        return matAmtTolerance;
    }

    public void setMatAmtTolerance(String matAmtTolerance) {
        this.matAmtTolerance = matAmtTolerance;
    }

    public String getMatAmtToleranceValue() {
        return matAmtToleranceValue;
    }

    public void setMatAmtToleranceValue(String matAmtToleranceValue) {
        this.matAmtToleranceValue = matAmtToleranceValue;
    }

    public String getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(String maxAmt) {
        this.maxAmt = maxAmt;
    }

    public String getMaxAmtValue() {
        return maxAmtValue;
    }

    public void setMaxAmtValue(String maxAmtValue) {
        this.maxAmtValue = maxAmtValue;
    }

    public String getMaxPerd() {
        return maxPerd;
    }

    public void setMaxPerd(String maxPerd) {
        this.maxPerd = maxPerd;
    }

    public String getMaxPerdValue() {
        return maxPerdValue;
    }

    public void setMaxPerdValue(String maxPerdValue) {
        this.maxPerdValue = maxPerdValue;
    }

    public String getMinorDepPreclosure() {
        return minorDepPreclosure;
    }

    public void setMinorDepPreclosure(String minorDepPreclosure) {
        this.minorDepPreclosure = minorDepPreclosure;
    }

    public String getMinorDepPreclosureValue() {
        return minorDepPreclosureValue;
    }

    public void setMinorDepPreclosureValue(String minorDepPreclosureValue) {
        this.minorDepPreclosureValue = minorDepPreclosureValue;
    }

    public String getNilPenalty() {
        return nilPenalty;
    }

    public void setNilPenalty(String nilPenalty) {
        this.nilPenalty = nilPenalty;
    }

    public String getNilPenaltyValue() {
        return nilPenaltyValue;
    }

    public void setNilPenaltyValue(String nilPenaltyValue) {
        this.nilPenaltyValue = nilPenaltyValue;
    }

    public String getNoticePerdMinNoticePerd() {
        return noticePerdMinNoticePerd;
    }

    public void setNoticePerdMinNoticePerd(String noticePerdMinNoticePerd) {
        this.noticePerdMinNoticePerd = noticePerdMinNoticePerd;
    }

    public String getNoticePerdMinNoticePerdValue() {
        return noticePerdMinNoticePerdValue;
    }

    public void setNoticePerdMinNoticePerdValue(String noticePerdMinNoticePerdValue) {
        this.noticePerdMinNoticePerdValue = noticePerdMinNoticePerdValue;
    }

    public String getOverdueGLSubheadCode() {
        return overdueGLSubheadCode;
    }

    public void setOverdueGLSubheadCode(String overdueGLSubheadCode) {
        this.overdueGLSubheadCode = overdueGLSubheadCode;
    }

    public String getOverdueIntCalcMethod() {
        return overdueIntCalcMethod;
    }

    public void setOverdueIntCalcMethod(String overdueIntCalcMethod) {
        this.overdueIntCalcMethod = overdueIntCalcMethod;
    }

    public String getOverdueIntCalcMethodValue() {
        return overdueIntCalcMethodValue;
    }

    public void setOverdueIntCalcMethodValue(String overdueIntCalcMethodValue) {
        this.overdueIntCalcMethodValue = overdueIntCalcMethodValue;
    }

    public String getOverdueIntCode() {
        return overdueIntCode;
    }

    public void setOverdueIntCode(String overdueIntCode) {
        this.overdueIntCode = overdueIntCode;
    }

    public String getOverdueIntTblCode() {
        return overdueIntTblCode;
    }

    public void setOverdueIntTblCode(String overdueIntTblCode) {
        this.overdueIntTblCode = overdueIntTblCode;
    }

    public String getPreMatureClosure() {
        return preMatureClosure;
    }

    public void setPreMatureClosure(String preMatureClosure) {
        this.preMatureClosure = preMatureClosure;
    }

    public String getPreMatureClosureValue() {
        return preMatureClosureValue;
    }

    public void setPreMatureClosureValue(String preMatureClosureValue) {
        this.preMatureClosureValue = preMatureClosureValue;
    }

    public String getRenewalPerdExcd() {
        return renewalPerdExcd;
    }

    public void setRenewalPerdExcd(String renewalPerdExcd) {
        this.renewalPerdExcd = renewalPerdExcd;
    }

    public String getRenewalPerdExcdValue() {
        return renewalPerdExcdValue;
    }

    public void setRenewalPerdExcdValue(String renewalPerdExcdValue) {
        this.renewalPerdExcdValue = renewalPerdExcdValue;
    }

    public String getSplCatgClosure() {
        return splCatgClosure;
    }

    public void setSplCatgClosure(String splCatgClosure) {
        this.splCatgClosure = splCatgClosure;
    }

    public String getSplCatgClosureValue() {
        return splCatgClosureValue;
    }

    public void setSplCatgClosureValue(String splCatgClosureValue) {
        this.splCatgClosureValue = splCatgClosureValue;
    }

    public String getSystemDrTransAllowed() {
        return systemDrTransAllowed;
    }

    public void setSystemDrTransAllowed(String systemDrTransAllowed) {
        this.systemDrTransAllowed = systemDrTransAllowed;
    }

    public String getSystemDrTransAllowedValue() {
        return systemDrTransAllowedValue;
    }

    public void setSystemDrTransAllowedValue(String systemDrTransAllowedValue) {
        this.systemDrTransAllowedValue = systemDrTransAllowedValue;
    }

    public String getTransferIn() {
        return transferIn;
    }

    public void setTransferIn(String transferIn) {
        this.transferIn = transferIn;
    }

    public String getTransferInValue() {
        return transferInValue;
    }

    public void setTransferInValue(String transferInValue) {
        this.transferInValue = transferInValue;
    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public List<SelectItem> getOverdueIntCalcMethodList() {
        return overdueIntCalcMethodList;
    }

    public void setOverdueIntCalcMethodList(List<SelectItem> overdueIntCalcMethodList) {
        this.overdueIntCalcMethodList = overdueIntCalcMethodList;
    }

    public List<SelectItem> getOverdueIntTblCodeList() {
        return overdueIntTblCodeList;
    }

    public void setOverdueIntTblCodeList(List<SelectItem> overdueIntTblCodeList) {
        this.overdueIntTblCodeList = overdueIntTblCodeList;
    }

    /** Creates a new instance of DOIP */
    public DOIP() {
       
        try {
            overdueIntTblCodeList = new ArrayList<SelectItem>();
            overdueIntTblCodeList.add(new SelectItem("0", ""));
            overdueIntTblCodeList.add(new SelectItem("T", "Term Deposit"));
            overdueIntTblCodeList.add(new SelectItem("G", "General Deposit(Saving)"));

            overdueIntCalcMethodList = new ArrayList<SelectItem>();
            overdueIntCalcMethodList.add(new SelectItem("0", ""));
            overdueIntCalcMethodList.add(new SelectItem("C", "ContractedRateForDepAmt"));
            overdueIntCalcMethodList.add(new SelectItem("R", "ContractedRateForOverdueRunPeriod"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Functionality for doip.jsp file
    public void selectDOIPDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<String> depositOverDueIntParameter = schemeMasterManagementDelegate.getDepositOverDueIntParameter(schemeMaster.getSchemeCode());
            if (depositOverDueIntParameter.size() > 0) {
                if (depositOverDueIntParameter.get(2) == null || depositOverDueIntParameter.get(2).equalsIgnoreCase("")) {
                    this.setOverdueGLSubheadCode("");
                } else {
                    this.setOverdueGLSubheadCode(depositOverDueIntParameter.get(2));
                }
                if (depositOverDueIntParameter.get(3) == null || depositOverDueIntParameter.get(3).equalsIgnoreCase("")) {
                    this.setOverdueIntCode("");
                } else {
                    this.setOverdueIntCode(depositOverDueIntParameter.get(3));
                }
                if (depositOverDueIntParameter.get(4) == null || depositOverDueIntParameter.get(4).equalsIgnoreCase("")) {
                    this.setOverdueIntTblCode("");
                } else {
                    this.setOverdueIntTblCode(depositOverDueIntParameter.get(4));
                }
                if (depositOverDueIntParameter.get(5) == null || depositOverDueIntParameter.get(5).equalsIgnoreCase("")) {
                    this.setOverdueIntCalcMethod("");
                } else {
                    this.setOverdueIntCalcMethod(depositOverDueIntParameter.get(5));
                }
                if (depositOverDueIntParameter.get(6) == null || depositOverDueIntParameter.get(6).equalsIgnoreCase("")) {
                    this.setRenewalPerdExcd("");
                } else {
                    this.setRenewalPerdExcd(depositOverDueIntParameter.get(6));
                    getExcepDescRenPrd();
                }
                if (depositOverDueIntParameter.get(7) == null || depositOverDueIntParameter.get(7).equalsIgnoreCase("")) {
                    this.setMaxPerd("");
                } else {
                    this.setMaxPerd(depositOverDueIntParameter.get(7));
                    getExcepDescMaxPrd();
                }
                if (depositOverDueIntParameter.get(8) == null || depositOverDueIntParameter.get(8).equalsIgnoreCase("")) {
                    this.setMaxAmt("");
                } else {
                    this.setMaxAmt(depositOverDueIntParameter.get(8));
                    getExcepDescMaxAmt();
                }
                if (depositOverDueIntParameter.get(9) == null || depositOverDueIntParameter.get(9).equalsIgnoreCase("")) {
                    this.setMinorDepPreclosure("");
                } else {
                    this.setMinorDepPreclosure(depositOverDueIntParameter.get(9));
                    getExcepDescMinorDep();
                }
                if (depositOverDueIntParameter.get(10) == null || depositOverDueIntParameter.get(10).equalsIgnoreCase("")) {
                    this.setExtension("");
                } else {
                    this.setExtension(depositOverDueIntParameter.get(10));
                    getExcepDescExtension();
                }
                if (depositOverDueIntParameter.get(11) == null || depositOverDueIntParameter.get(11).equalsIgnoreCase("")) {
                    this.setSplCatgClosure("");
                } else {
                    this.setSplCatgClosure(depositOverDueIntParameter.get(11));
                    getExcepDescSplCatgClosure();
                }
                if (depositOverDueIntParameter.get(12) == null || depositOverDueIntParameter.get(12).equalsIgnoreCase("")) {
                    this.setMatAmtTolerance("");
                } else {
                    this.setMatAmtTolerance(depositOverDueIntParameter.get(12));
                    getExcepDescMatAmtTolerance();
                }
                if (depositOverDueIntParameter.get(13) == null || depositOverDueIntParameter.get(13).equalsIgnoreCase("")) {
                    this.setNilPenalty("");
                } else {
                    this.setNilPenalty(depositOverDueIntParameter.get(13));
                    getExcepDescNilPenalty();
                }
                if (depositOverDueIntParameter.get(14) == null || depositOverDueIntParameter.get(14).equalsIgnoreCase("")) {
                    this.setDisContinuedInst("");
                } else {
                    this.setDisContinuedInst(depositOverDueIntParameter.get(14));
                    getExcepDescDiscontInst();
                }
                if (depositOverDueIntParameter.get(15) == null || depositOverDueIntParameter.get(15).equalsIgnoreCase("")) {
                    this.setTransferIn("");
                } else {
                    this.setTransferIn(depositOverDueIntParameter.get(15));
                    getExcepDescTransferIn();
                }
                if (depositOverDueIntParameter.get(16) == null || depositOverDueIntParameter.get(16).equalsIgnoreCase("")) {
                    this.setAcctVerBalCheck("");
                } else {
                    this.setAcctVerBalCheck(depositOverDueIntParameter.get(16));
                    getExcepDescAccVerBal();
                }
                if (depositOverDueIntParameter.get(17) == null || depositOverDueIntParameter.get(17).equalsIgnoreCase("")) {
                    this.setSystemDrTransAllowed("");
                } else {
                    this.setSystemDrTransAllowed(depositOverDueIntParameter.get(17));
                    getExcepDescSysDrTransAllowed();
                }
                if (depositOverDueIntParameter.get(18) == null || depositOverDueIntParameter.get(18).equalsIgnoreCase("")) {
                    this.setDupReprntRcpt("");
                } else {
                    this.setDupReprntRcpt(depositOverDueIntParameter.get(18));
                    getExcepDescDupReprntRcpt();
                }
                if (depositOverDueIntParameter.get(19) == null || depositOverDueIntParameter.get(19).equalsIgnoreCase("")) {
                    this.setPreMatureClosure("");
                } else {
                    this.setPreMatureClosure(depositOverDueIntParameter.get(19));
                    getExcepDescPrematureClosure();
                }
                if (depositOverDueIntParameter.get(20) == null || depositOverDueIntParameter.get(20).equalsIgnoreCase("")) {
                    this.setNoticePerdMinNoticePerd("");
                } else {
                    this.setNoticePerdMinNoticePerd(depositOverDueIntParameter.get(20));
                    getExcepDescNoticePeriod();
                }
                if (depositOverDueIntParameter.get(21) == null || depositOverDueIntParameter.get(21).equalsIgnoreCase("")) {
                    this.setDefaultValueForPreIntChgd("");
                } else {
                    this.setDefaultValueForPreIntChgd(depositOverDueIntParameter.get(21));
                    getExcepDescDefaultValue();
                }
                if (depositOverDueIntParameter.get(22) == null || depositOverDueIntParameter.get(22).equalsIgnoreCase("")) {
                    this.setBackValueDateAccOpen("");
                } else {
                    this.setBackValueDateAccOpen(depositOverDueIntParameter.get(22));
                    getExcepDescBackValueDate();
                }
                if (depositOverDueIntParameter.get(23) == null || depositOverDueIntParameter.get(23).equalsIgnoreCase("")) {
                    this.setFutureValueDateAccOpen("");
                } else {
                    this.setFutureValueDateAccOpen(depositOverDueIntParameter.get(23));
                    getExcepDescFutureValueDate();
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

    public void getExcepDescRenPrd() {
        this.schemeMaster.setMessage("");
        try {
            if (this.renewalPerdExcd.equals("")) {
                this.setRenewalPerdExcdValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.renewalPerdExcd);
                this.setRenewalPerdExcdValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setRenewalPerdExcdValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescMaxPrd() {
        this.schemeMaster.setMessage("");
        try {
            if (this.maxPerd.equals("")) {
                this.setMaxPerdValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.maxPerd);
                this.setMaxPerdValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setMaxPerdValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescMaxAmt() {
        this.schemeMaster.setMessage("");
        try {
            if (this.maxAmt.equals("")) {
                this.setMaxAmtValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.maxAmt);
                this.setMaxAmtValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setMaxAmtValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescMinorDep() {
        this.schemeMaster.setMessage("");
        try {
            if (this.minorDepPreclosure.equals("")) {
                this.setMinorDepPreclosureValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.minorDepPreclosure);
                this.setMinorDepPreclosureValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setMinorDepPreclosureValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescExtension() {
        this.schemeMaster.setMessage("");
        try {
            if (this.extension.equals("")) {
                this.setExtensionValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.extension);
                this.setExtensionValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setExtensionValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescSplCatgClosure() {
        this.schemeMaster.setMessage("");
        try {
            if (this.splCatgClosure.equals("")) {
                this.setSplCatgClosureValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.splCatgClosure);
                this.setSplCatgClosureValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setSplCatgClosureValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescMatAmtTolerance() {
        this.schemeMaster.setMessage("");
        try {
            if (this.matAmtTolerance.equals("")) {
                this.setMatAmtToleranceValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.matAmtTolerance);
                this.setMatAmtToleranceValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setMatAmtToleranceValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescDiscontInst() {
        this.schemeMaster.setMessage("");
        try {
            if (this.disContinuedInst.equals("")) {
                this.setDisContinuedInstValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.disContinuedInst);
                this.setDisContinuedInstValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setDisContinuedInstValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescTransferIn() {
        this.schemeMaster.setMessage("");
        try {
            if (this.transferIn.equals("")) {
                this.setTransferInValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.transferIn);
                this.setTransferInValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setTransferInValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescSysDrTransAllowed() {
        this.schemeMaster.setMessage("");
        try {
            if (this.systemDrTransAllowed.equals("")) {
                this.setSystemDrTransAllowedValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.systemDrTransAllowed);
                this.setSystemDrTransAllowedValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setSystemDrTransAllowedValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescDupReprntRcpt() {
        this.schemeMaster.setMessage("");
        try {
            if (this.dupReprntRcpt.equals("")) {
                this.setDupReprntRcptValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.dupReprntRcpt);
                this.setDupReprntRcptValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setDupReprntRcptValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescPrematureClosure() {
        this.schemeMaster.setMessage("");
        try {
            if (this.preMatureClosure.equals("")) {
                this.setPreMatureClosureValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.preMatureClosure);
                this.setPreMatureClosureValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setPreMatureClosureValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescNoticePeriod() {
        this.schemeMaster.setMessage("");
        try {
            if (this.noticePerdMinNoticePerd.equals("")) {
                this.setNoticePerdMinNoticePerdValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.noticePerdMinNoticePerd);
                this.setNoticePerdMinNoticePerdValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setNoticePerdMinNoticePerdValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescDefaultValue() {
        this.schemeMaster.setMessage("");
        try {
            if (this.defaultValueForPreIntChgd.equals("")) {
                this.setDefaultValueForPreIntChgdValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.defaultValueForPreIntChgd);
                this.setDefaultValueForPreIntChgdValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setDefaultValueForPreIntChgdValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescBackValueDate() {
        this.schemeMaster.setMessage("");
        try {
            if (this.backValueDateAccOpen.equals("")) {
                this.setBackValueDateAccOpenValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.backValueDateAccOpen);
                this.setBackValueDateAccOpenValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setBackValueDateAccOpenValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescFutureValueDate() {
        this.schemeMaster.setMessage("");
        try {
            if (this.futureValueDateAccOpen.equals("")) {
                this.setFutureValueDateAccOpenValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.futureValueDateAccOpen);
                this.setFutureValueDateAccOpenValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setFutureValueDateAccOpenValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescNilPenalty() {
        this.schemeMaster.setMessage("");
        try {
            if (this.nilPenalty.equals("")) {
                this.setNilPenaltyValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.nilPenalty);
                this.setNilPenaltyValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setNilPenaltyValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescAccVerBal() {
        this.schemeMaster.setMessage("");
        try {
            if (this.acctVerBalCheck.equals("")) {
                this.setAcctVerBalCheckValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.acctVerBalCheck);
                this.setAcctVerBalCheckValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setAcctVerBalCheckValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refreshDOIPForm() {
        this.setOverdueGLSubheadCode("");
        this.setOverdueIntCode("");
        this.setOverdueIntTblCode("0");
        this.setOverdueIntCalcMethod("0");
        this.setOverdueIntCalcMethodValue("");
        this.setRenewalPerdExcd("");
        this.setMaxPerd("");
        this.setMaxAmt("");
        this.setMinorDepPreclosure("");
        this.setExtension("");
        this.setSplCatgClosure("");
        this.setMatAmtTolerance("");
        this.setNilPenalty("");
        this.setDisContinuedInst("");
        this.setTransferIn("");
        this.setAcctVerBalCheck("");
        this.setSystemDrTransAllowed("");
        this.setDupReprntRcpt("");
        this.setPreMatureClosure("");
        this.setNoticePerdMinNoticePerd("");
        this.setDefaultValueForPreIntChgd("");
        this.setBackValueDateAccOpen("");
        this.setFutureValueDateAccOpen("");
        this.setRenewalPerdExcd("");
        this.setMaxPerd("");
        this.setMaxAmtValue("");
        this.setMinorDepPreclosure("");
        this.setExtensionValue("");
        this.setSplCatgClosureValue("");
        this.setMatAmtToleranceValue("");
        this.setNilPenaltyValue("");
        this.setDisContinuedInstValue("");
        this.setTransferInValue("");
        this.setAcctVerBalCheckValue("");
        this.setSystemDrTransAllowedValue("");
        this.setDupReprntRcptValue("");
        this.setPreMatureClosureValue("");
        this.setNoticePerdMinNoticePerdValue("");
        this.setDefaultValueForPreIntChgdValue("");
        this.setBackValueDateAccOpenValue("");
        this.setFutureValueDateAccOpenValue("");
    }

    public void enableDOIPForm() {
        this.disableFlag = false;
    }

    public void disableDOIPForm() {
        this.disableFlag = true;
    }
}
