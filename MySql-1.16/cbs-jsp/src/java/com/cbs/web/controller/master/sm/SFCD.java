/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeFeeOrChargesDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeFeeOrChargesDetailsTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.SchemeFeeOrChargesDetails;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class SFCD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for sfcd.jsp file
    private String chargesType;
    private String amortize;
    private String chargesDesc;
    private String amortMethod;
    private String chargesEventId;
    private String drPlaceHolder;
    private String crPlaceHolder;
    private String assertOrDmd;
    private String deductible;
    private String considerForIrr;
    private String multipleFlag;
    private String delFlagFeeCharges;
    int count1FeeCharges = 0;
    int count2FeeCharges = 0;
    private int currentRowFeeCharges;
    int selectCountFeeCharges = 0;
    private boolean disableFlagFeeCharges;
    boolean chargesTypedisable;
    private SchemeFeeOrChargesDetails currentItemFeeCharges = new SchemeFeeOrChargesDetails();
    private List<SchemeFeeOrChargesDetails> feeCharges;
    private List<SchemeFeeOrChargesDetails> feeChargesTmp;
    private List<SelectItem> assertOrDmdList;
    private List<SelectItem> chargesTypeList;
    private List<SelectItem> chargesEventIdList;
    private List<SelectItem> deleteSfcdList;

    //Getter-Setter for sfcd.jsp file
    public List<SelectItem> getDeleteSfcdList() {
        return deleteSfcdList;
    }

    public void setDeleteSfcdList(List<SelectItem> deleteSfcdList) {
        this.deleteSfcdList = deleteSfcdList;
    }

    public String getAmortMethod() {
        return amortMethod;
    }

    public void setAmortMethod(String amortMethod) {
        this.amortMethod = amortMethod;
    }

    public String getAmortize() {
        return amortize;
    }

    public void setAmortize(String amortize) {
        this.amortize = amortize;
    }

    public String getAssertOrDmd() {
        return assertOrDmd;
    }

    public void setAssertOrDmd(String assertOrDmd) {
        this.assertOrDmd = assertOrDmd;
    }

    public List<SelectItem> getAssertOrDmdList() {
        return assertOrDmdList;
    }

    public void setAssertOrDmdList(List<SelectItem> assertOrDmdList) {
        this.assertOrDmdList = assertOrDmdList;
    }

    public String getChargesDesc() {
        return chargesDesc;
    }

    public void setChargesDesc(String chargesDesc) {
        this.chargesDesc = chargesDesc;
    }

    public String getChargesEventId() {
        return chargesEventId;
    }

    public void setChargesEventId(String chargesEventId) {
        this.chargesEventId = chargesEventId;
    }

    public List<SelectItem> getChargesEventIdList() {
        return chargesEventIdList;
    }

    public void setChargesEventIdList(List<SelectItem> chargesEventIdList) {
        this.chargesEventIdList = chargesEventIdList;
    }

    public String getChargesType() {
        return chargesType;
    }

    public void setChargesType(String chargesType) {
        this.chargesType = chargesType;
    }

    public List<SelectItem> getChargesTypeList() {
        return chargesTypeList;
    }

    public void setChargesTypeList(List<SelectItem> chargesTypeList) {
        this.chargesTypeList = chargesTypeList;
    }

    public boolean isChargesTypedisable() {
        return chargesTypedisable;
    }

    public void setChargesTypedisable(boolean chargesTypedisable) {
        this.chargesTypedisable = chargesTypedisable;
    }

    public String getConsiderForIrr() {
        return considerForIrr;
    }

    public void setConsiderForIrr(String considerForIrr) {
        this.considerForIrr = considerForIrr;
    }

    public int getCount1FeeCharges() {
        return count1FeeCharges;
    }

    public void setCount1FeeCharges(int count1FeeCharges) {
        this.count1FeeCharges = count1FeeCharges;
    }

    public int getCount2FeeCharges() {
        return count2FeeCharges;
    }

    public void setCount2FeeCharges(int count2FeeCharges) {
        this.count2FeeCharges = count2FeeCharges;
    }

    public String getCrPlaceHolder() {
        return crPlaceHolder;
    }

    public void setCrPlaceHolder(String crPlaceHolder) {
        this.crPlaceHolder = crPlaceHolder;
    }

    public SchemeFeeOrChargesDetails getCurrentItemFeeCharges() {
        return currentItemFeeCharges;
    }

    public void setCurrentItemFeeCharges(SchemeFeeOrChargesDetails currentItemFeeCharges) {
        this.currentItemFeeCharges = currentItemFeeCharges;
    }

    public int getCurrentRowFeeCharges() {
        return currentRowFeeCharges;
    }

    public void setCurrentRowFeeCharges(int currentRowFeeCharges) {
        this.currentRowFeeCharges = currentRowFeeCharges;
    }

    public String getDeductible() {
        return deductible;
    }

    public void setDeductible(String deductible) {
        this.deductible = deductible;
    }

    public String getDelFlagFeeCharges() {
        return delFlagFeeCharges;
    }

    public void setDelFlagFeeCharges(String delFlagFeeCharges) {
        this.delFlagFeeCharges = delFlagFeeCharges;
    }

    public boolean isDisableFlagFeeCharges() {
        return disableFlagFeeCharges;
    }

    public void setDisableFlagFeeCharges(boolean disableFlagFeeCharges) {
        this.disableFlagFeeCharges = disableFlagFeeCharges;
    }

    public String getDrPlaceHolder() {
        return drPlaceHolder;
    }

    public void setDrPlaceHolder(String drPlaceHolder) {
        this.drPlaceHolder = drPlaceHolder;
    }

    public List<SchemeFeeOrChargesDetails> getFeeCharges() {
        return feeCharges;
    }

    public void setFeeCharges(List<SchemeFeeOrChargesDetails> feeCharges) {
        this.feeCharges = feeCharges;
    }

    public List<SchemeFeeOrChargesDetails> getFeeChargesTmp() {
        return feeChargesTmp;
    }

    public void setFeeChargesTmp(List<SchemeFeeOrChargesDetails> feeChargesTmp) {
        this.feeChargesTmp = feeChargesTmp;
    }

    public String getMultipleFlag() {
        return multipleFlag;
    }

    public void setMultipleFlag(String multipleFlag) {
        this.multipleFlag = multipleFlag;
    }

    public int getSelectCountFeeCharges() {
        return selectCountFeeCharges;
    }

    public void setSelectCountFeeCharges(int selectCountFeeCharges) {
        this.selectCountFeeCharges = selectCountFeeCharges;
    }

    /** Creates a new instance of SFCD */
    public SFCD() {
       
        try {
            feeCharges = new ArrayList<SchemeFeeOrChargesDetails>();
            feeChargesTmp = new ArrayList<SchemeFeeOrChargesDetails>();
            
            deleteSfcdList = new ArrayList<SelectItem>();
            deleteSfcdList.add(new SelectItem("0", ""));
            deleteSfcdList.add(new SelectItem("Y", "Yes"));
            deleteSfcdList.add(new SelectItem("N", "No"));

            assertOrDmdList = new ArrayList<SelectItem>();
            assertOrDmdList.add(new SelectItem("0", " "));
            assertOrDmdList.add(new SelectItem("A", "Asset"));
            assertOrDmdList.add(new SelectItem("D", "Demand"));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs1 = schemeMasterManagementDelegate.getCurrencyCode("181");
            if (CbsRefRecTypeTOs1.size() > 0) {
                chargesEventIdList = new ArrayList<SelectItem>();
                chargesEventIdList.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs1) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    chargesEventIdList.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				chargesEventIdList = new ArrayList<SelectItem>();
                chargesEventIdList.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs2 = schemeMasterManagementDelegate.getCurrencyCode("108");
            if (CbsRefRecTypeTOs2.size() > 0) {
                chargesTypeList = new ArrayList<SelectItem>();
                chargesTypeList.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs2) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    chargesTypeList.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                    chargesDesc = refRecTo.getRefDesc();
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

    //Functionality for sfcd.jsp file
    public void selectSfcdDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeFeeOrChargesDetailsTO> listTOs = schemeMasterManagementDelegate.getSfcdDetails(schemeMaster.getSchemeCode());
            if (listTOs.size() > 0) {
                for (CbsSchemeFeeOrChargesDetailsTO singleTO : listTOs) {
                    CbsSchemeFeeOrChargesDetailsPKTO pKTo = singleTO.getCbsSchemeFeeOrChargesDetailsPKTO();
                    SchemeFeeOrChargesDetails tblObj = new SchemeFeeOrChargesDetails();
                    if (pKTo.getChargeType() == null || pKTo.getChargeType().equalsIgnoreCase("")) {
                        tblObj.setChargesType("");
                    } else {
                        tblObj.setChargesType(pKTo.getChargeType());
                    }
                    if (singleTO.getAmortize() == null || singleTO.getAmortize().equalsIgnoreCase("")) {
                        tblObj.setAmortize("");
                    } else {
                        tblObj.setAmortize(singleTO.getAmortize());
                    }
                    if (singleTO.getChargeDescription() == null || singleTO.getChargeDescription().equalsIgnoreCase("")) {
                        tblObj.setChargesDesc("");
                    } else {
                        tblObj.setChargesDesc(singleTO.getChargeDescription());
                    }
                    if (singleTO.getAmortMethod() == null || singleTO.getAmortMethod().equalsIgnoreCase("")) {
                        tblObj.setAmortMethod("");
                    } else {
                        tblObj.setAmortMethod(singleTO.getAmortMethod());
                    }
                    if (singleTO.getChargeEventId() == null || singleTO.getChargeEventId().equalsIgnoreCase("")) {
                        tblObj.setChargesEventId("");
                    } else {
                        tblObj.setChargesEventId(singleTO.getChargeEventId());
                    }
                    if (singleTO.getDrPlaceholder() == null || singleTO.getDrPlaceholder().equalsIgnoreCase("")) {
                        tblObj.setDrPlaceHolder("");
                    } else {
                        tblObj.setDrPlaceHolder(singleTO.getDrPlaceholder());
                    }
                    if (singleTO.getCrPlaceholder() == null || singleTO.getCrPlaceholder().equalsIgnoreCase("")) {
                        tblObj.setCrPlaceHolder("");
                    } else {
                        tblObj.setCrPlaceHolder(singleTO.getCrPlaceholder());
                    }
                    if (singleTO.getAssessOrDmd() == null || singleTO.getAssessOrDmd().equalsIgnoreCase("")) {
                        tblObj.setAssertOrDmd("");
                    } else {
                        tblObj.setAssertOrDmd(singleTO.getAssessOrDmd());
                    }
                    if (singleTO.getDeductible() == null || singleTO.getDeductible().equalsIgnoreCase("")) {
                        tblObj.setDeductible("");
                    } else {
                        tblObj.setDeductible(singleTO.getDeductible());
                    }
                    if (singleTO.getConsiderForIrr() == null || singleTO.getConsiderForIrr().equalsIgnoreCase("")) {
                        tblObj.setConsiderForIrr("");
                    } else {
                        tblObj.setConsiderForIrr(singleTO.getConsiderForIrr());
                    }
                    if (singleTO.getMultipleFlag() == null || singleTO.getMultipleFlag().equalsIgnoreCase("")) {
                        tblObj.setMultipleFlag("");
                    } else {
                        tblObj.setMultipleFlag(singleTO.getMultipleFlag());
                    }
                    if (singleTO.getDelFlag() == null || singleTO.getDelFlag().equalsIgnoreCase("")) {
                        tblObj.setDelFlagFeeCharges("");
                    } else {
                        tblObj.setDelFlagFeeCharges(singleTO.getDelFlag());
                    }
                    tblObj.setCounterSaveUpdate("G");
                    feeCharges.add(tblObj);
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

    public void setValueCharDesc() {
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsRefRecTypeTO> CbsRefRecTypeTOs2 = schemeMasterManagementDelegate.getCurrencyCode("108");
            if (CbsRefRecTypeTOs2.size() > 0) {
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs2) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    if (chargesType.equals(cbsRefRecTypePKTO.getRefCode())) {
                        chargesDesc = refRecTo.getRefDesc();
                    }
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

    public void setValueInFeeChargesTable() {
        List<SchemeFeeOrChargesDetails> feeChargesTemp = feeCharges;
        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (selectCountFeeCharges == 0) {
                for (int i = 0; i < feeChargesTemp.size(); i++) {
                    String chargesTypetmp = feeChargesTemp.get(i).getChargesType();

                    if (chargesTypetmp.equalsIgnoreCase(chargesType)) {
                        schemeMaster.setMessage("Charges Type Already Exists In The Table. Please Select other Charges Type.");
                        return;
                    }
                }
            }
        }
        if (validateFeeCharges().equalsIgnoreCase("false")) {
            return;
        }
        schemeMaster.setMessage("");
        SchemeFeeOrChargesDetails feeChg = new SchemeFeeOrChargesDetails();
        feeChg.setChargesType(getChargesType());
        feeChg.setAmortize(getAmortize());
        feeChg.setChargesDesc(getChargesDesc());
        feeChg.setAmortMethod(getAmortMethod());
        feeChg.setChargesEventId(getChargesEventId());
        feeChg.setDrPlaceHolder(getDrPlaceHolder());
        feeChg.setCrPlaceHolder(getCrPlaceHolder());
        feeChg.setAssertOrDmd(getAssertOrDmd());
        feeChg.setDeductible(getDeductible());
        feeChg.setConsiderForIrr(getConsiderForIrr());
        feeChg.setMultipleFlag(getMultipleFlag());
        feeChg.setDelFlagFeeCharges(getDelFlagFeeCharges());
        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            feeChg.setCounterSaveUpdate("S");
            refreshSfcdForm();
        }
        feeCharges.add(feeChg);
        setChargesTypedisable(false);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (selectCountFeeCharges != 1) {
                for (int i = 0; i < feeChargesTemp.size(); i++) {
                    String chargesTypetmp = feeChargesTemp.get(i).getChargesType();
                    if (!chargesTypetmp.equalsIgnoreCase(chargesType)) {
                        feeChg.setCounterSaveUpdate("S");
                        feeChargesTmp.add(feeChg);
                        refreshSfcdForm();
                        return;
                    }
                }
            }
            if (currentItemFeeCharges.getChargesType() == null || currentItemFeeCharges.getChargesType().equalsIgnoreCase("")) {
                feeChg.setCounterSaveUpdate("S");
                feeChargesTmp.add(feeChg);
                refreshSfcdForm();
                return;
            } else if (currentItemFeeCharges.getChargesType().equalsIgnoreCase(chargesType)) {
                if (!currentItemFeeCharges.getAmortize().equalsIgnoreCase(amortize) || !currentItemFeeCharges.getAmortMethod().equalsIgnoreCase(amortMethod) || !currentItemFeeCharges.getChargesEventId().equalsIgnoreCase(chargesEventId) || !currentItemFeeCharges.getDrPlaceHolder().equalsIgnoreCase(drPlaceHolder) || !currentItemFeeCharges.getCrPlaceHolder().equalsIgnoreCase(crPlaceHolder) || !currentItemFeeCharges.getAssertOrDmd().equalsIgnoreCase(assertOrDmd) || !currentItemFeeCharges.getDeductible().equalsIgnoreCase(deductible) || !currentItemFeeCharges.getConsiderForIrr().equalsIgnoreCase(considerForIrr) || !currentItemFeeCharges.getDelFlagFeeCharges().equalsIgnoreCase(delFlagFeeCharges)) {
                    feeChg.setCounterSaveUpdate("U");
                    refreshSfcdForm();
                    feeChargesTmp.add(feeChg);
                }
                selectCountFeeCharges = 0;
            }
        }
    }

    public void selectFeeCharges() {
        schemeMaster.setMessage("");
        selectCountFeeCharges = 1;
        if (!(chargesType.equalsIgnoreCase("0") && currentItemFeeCharges.getChargesType() != null)) {
            if (!chargesType.equalsIgnoreCase("")) {
                if (!chargesType.equalsIgnoreCase(currentItemFeeCharges.getChargesType())) {
                    count2FeeCharges = count1FeeCharges;
                    count1FeeCharges = count1FeeCharges + 1;
                    if (count2FeeCharges != count1FeeCharges) {
                        setValueInFeeChargesTable();
                    }
                } else {
                    count1FeeCharges = 0;
                }
            }
        }

        setChargesType(currentItemFeeCharges.getChargesType());
        setAmortize(currentItemFeeCharges.getAmortize());
        setChargesDesc(currentItemFeeCharges.getChargesDesc());
        setAmortMethod(currentItemFeeCharges.getAmortMethod());
        setChargesEventId(currentItemFeeCharges.getChargesEventId());
        setDrPlaceHolder(currentItemFeeCharges.getDrPlaceHolder());
        setCrPlaceHolder(currentItemFeeCharges.getCrPlaceHolder());
        setAssertOrDmd(currentItemFeeCharges.getAssertOrDmd());
        setDeductible(currentItemFeeCharges.getDeductible());
        setConsiderForIrr(currentItemFeeCharges.getConsiderForIrr());
        setMultipleFlag(currentItemFeeCharges.getMultipleFlag());
        setDelFlagFeeCharges(currentItemFeeCharges.getDelFlagFeeCharges());
        feeCharges.remove(currentRowFeeCharges);
        setChargesTypedisable(true);
    }

    public String validateFeeCharges() {
        schemeMaster.setMessage("");
        String msg = "";
        if (amortize.equalsIgnoreCase("0")) {
            msg = msg + "Please Select The Amortize.";
        }
        if (amortMethod.equalsIgnoreCase("0")) {
            msg = msg + "Amort Method";
        }
        if (chargesEventId.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Charges Event Id.";
        }
        if (assertOrDmd.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Assert Or Dmd.";
        }
        if (deductible.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Deductible.";
        }
        if (considerForIrr.equalsIgnoreCase("0")) {
            msg = msg + "Please Select consider For Irr.";
        }
        if (multipleFlag.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Multiple Flag.";
        }
        if (delFlagFeeCharges.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Delete Flag.";
        }
        if (chargesType.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Delete Flag.";
        }
        if (!msg.equalsIgnoreCase("")) {
            schemeMaster.setMessage(msg);
            return "False";
        }
        return "true";
    }

    public void refreshSfcdForm() {
        this.setAmortMethod("");
        this.setCrPlaceHolder("");
        this.setDrPlaceHolder("");
        this.setChargesDesc("");

        this.setChargesType("0");
        this.setAmortize("0");
        this.setChargesEventId("0");
        this.setAssertOrDmd("0");
        this.setDeductible("0");
        this.setConsiderForIrr("0");
        this.setMultipleFlag("0");
        this.setDelFlagFeeCharges("0");
    }

    public void enableSfcdForm() {
        this.disableFlagFeeCharges = false;
    }

    public void disableSfcdForm() {
        this.disableFlagFeeCharges = true;
    }
}
