/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsLoanInterestCodeMasterTO;
import com.cbs.dto.loan.CbsSchemeTodReferenceDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeTodReferenceDetailsTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.SchemeTodReferenceDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class STRD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for strd.jsp file
    private String referenceType;
    private String discretNumberOfDays;
    private String penalDays;
    private String discretAdvnType;
    private String discretAdvnCategory;
    private String intFlag;
    private String interestTableCode;
    private String toLevelIntTblCode;
    private String freeTxtCode;
    private String delFlagTodRef;
    int count1TodRef = 0;
    int count2FTodRef = 0;
    int selectCountTodRef = 0;
    private int currentRowTodRef;
    private boolean disableFlagTodRef;
    boolean disableFlagReferenceType;
    private SchemeTodReferenceDetails currentItemtodRef = new SchemeTodReferenceDetails();
    private List<SchemeTodReferenceDetails> todRef;
    private List<SchemeTodReferenceDetails> todRefTmp;
    private List<SelectItem> deleteStrdList;
    private List<SelectItem> interestTableCodeList;
    private List<SelectItem> referenceTypeList;
    private List<SelectItem> discretAdvnTypeList;
    private List<SelectItem> discretAdvnCategoryList;
    private List<SelectItem> freeTxtCodeList;

    //Getter-Setter for strd.jsp file
    public List<SelectItem> getInterestTableCodeList() {
        return interestTableCodeList;
    }

    public void setInterestTableCodeList(List<SelectItem> interestTableCodeList) {
        this.interestTableCodeList = interestTableCodeList;
    }

    public List<SelectItem> getDeleteStrdList() {
        return deleteStrdList;
    }

    public void setDeleteStrdList(List<SelectItem> deleteStrdList) {
        this.deleteStrdList = deleteStrdList;
    }

    public int getCount1TodRef() {
        return count1TodRef;
    }

    public void setCount1TodRef(int count1TodRef) {
        this.count1TodRef = count1TodRef;
    }

    public int getCount2FTodRef() {
        return count2FTodRef;
    }

    public void setCount2FTodRef(int count2FTodRef) {
        this.count2FTodRef = count2FTodRef;
    }

    public SchemeTodReferenceDetails getCurrentItemtodRef() {
        return currentItemtodRef;
    }

    public void setCurrentItemtodRef(SchemeTodReferenceDetails currentItemtodRef) {
        this.currentItemtodRef = currentItemtodRef;
    }

    public int getCurrentRowTodRef() {
        return currentRowTodRef;
    }

    public void setCurrentRowTodRef(int currentRowTodRef) {
        this.currentRowTodRef = currentRowTodRef;
    }

    public String getDelFlagTodRef() {
        return delFlagTodRef;
    }

    public void setDelFlagTodRef(String delFlagTodRef) {
        this.delFlagTodRef = delFlagTodRef;
    }

    public boolean isDisableFlagReferenceType() {
        return disableFlagReferenceType;
    }

    public void setDisableFlagReferenceType(boolean disableFlagReferenceType) {
        this.disableFlagReferenceType = disableFlagReferenceType;
    }

    public boolean isDisableFlagTodRef() {
        return disableFlagTodRef;
    }

    public void setDisableFlagTodRef(boolean disableFlagTodRef) {
        this.disableFlagTodRef = disableFlagTodRef;
    }

    public String getDiscretAdvnCategory() {
        return discretAdvnCategory;
    }

    public void setDiscretAdvnCategory(String discretAdvnCategory) {
        this.discretAdvnCategory = discretAdvnCategory;
    }

    public List<SelectItem> getDiscretAdvnCategoryList() {
        return discretAdvnCategoryList;
    }

    public void setDiscretAdvnCategoryList(List<SelectItem> discretAdvnCategoryList) {
        this.discretAdvnCategoryList = discretAdvnCategoryList;
    }

    public String getDiscretAdvnType() {
        return discretAdvnType;
    }

    public void setDiscretAdvnType(String discretAdvnType) {
        this.discretAdvnType = discretAdvnType;
    }

    public List<SelectItem> getDiscretAdvnTypeList() {
        return discretAdvnTypeList;
    }

    public void setDiscretAdvnTypeList(List<SelectItem> discretAdvnTypeList) {
        this.discretAdvnTypeList = discretAdvnTypeList;
    }

    public String getDiscretNumberOfDays() {
        return discretNumberOfDays;
    }

    public void setDiscretNumberOfDays(String discretNumberOfDays) {
        this.discretNumberOfDays = discretNumberOfDays;
    }

    public String getFreeTxtCode() {
        return freeTxtCode;
    }

    public void setFreeTxtCode(String freeTxtCode) {
        this.freeTxtCode = freeTxtCode;
    }

    public List<SelectItem> getFreeTxtCodeList() {
        return freeTxtCodeList;
    }

    public void setFreeTxtCodeList(List<SelectItem> freeTxtCodeList) {
        this.freeTxtCodeList = freeTxtCodeList;
    }

    public String getIntFlag() {
        return intFlag;
    }

    public void setIntFlag(String intFlag) {
        this.intFlag = intFlag;
    }

    public String getInterestTableCode() {
        return interestTableCode;
    }

    public void setInterestTableCode(String interestTableCode) {
        this.interestTableCode = interestTableCode;
    }

    public String getPenalDays() {
        return penalDays;
    }

    public void setPenalDays(String penalDays) {
        this.penalDays = penalDays;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public List<SelectItem> getReferenceTypeList() {
        return referenceTypeList;
    }

    public void setReferenceTypeList(List<SelectItem> referenceTypeList) {
        this.referenceTypeList = referenceTypeList;
    }

    public int getSelectCountTodRef() {
        return selectCountTodRef;
    }

    public void setSelectCountTodRef(int selectCountTodRef) {
        this.selectCountTodRef = selectCountTodRef;
    }

    public String getToLevelIntTblCode() {
        return toLevelIntTblCode;
    }

    public void setToLevelIntTblCode(String toLevelIntTblCode) {
        this.toLevelIntTblCode = toLevelIntTblCode;
    }

    public List<SchemeTodReferenceDetails> getTodRef() {
        return todRef;
    }

    public void setTodRef(List<SchemeTodReferenceDetails> todRef) {
        this.todRef = todRef;
    }

    public List<SchemeTodReferenceDetails> getTodRefTmp() {
        return todRefTmp;
    }

    public void setTodRefTmp(List<SchemeTodReferenceDetails> todRefTmp) {
        this.todRefTmp = todRefTmp;
    }

    /**
     * Creates a new instance of STRD
     */
    public STRD() {

        try {
            todRef = new ArrayList<SchemeTodReferenceDetails>();
            todRefTmp = new ArrayList<SchemeTodReferenceDetails>();

            deleteStrdList = new ArrayList<SelectItem>();
            deleteStrdList.add(new SelectItem("0", ""));
            deleteStrdList.add(new SelectItem("Y", "Yes"));
            deleteStrdList.add(new SelectItem("N", "No"));

            discretAdvnTypeList = new ArrayList<SelectItem>();
            discretAdvnTypeList.add(new SelectItem("0", " "));
            discretAdvnTypeList.add(new SelectItem("A", "Ad hoc Limit"));
            discretAdvnTypeList.add(new SelectItem("R", "Running Limit"));
            discretAdvnTypeList.add(new SelectItem("S", "Single Tran Limit"));

            discretAdvnCategoryList = new ArrayList<SelectItem>();
            discretAdvnCategoryList.add(new SelectItem("0", " "));
            discretAdvnCategoryList.add(new SelectItem("N", "Secured"));
            discretAdvnCategoryList.add(new SelectItem("R", "Clean"));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs1 = schemeMasterManagementDelegate.getCurrencyCode("207");
            if (CbsRefRecTypeTOs1.size() > 0) {
                referenceTypeList = new ArrayList<SelectItem>();
                referenceTypeList.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs1) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    referenceTypeList.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            } else {
                referenceTypeList = new ArrayList<SelectItem>();
                referenceTypeList.add(new SelectItem("0", ""));
            }

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs2 = schemeMasterManagementDelegate.getCurrencyCode("096");
            if (CbsRefRecTypeTOs2.size() > 0) {
                freeTxtCodeList = new ArrayList<SelectItem>();
                freeTxtCodeList.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs2) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    freeTxtCodeList.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            } else {
                freeTxtCodeList = new ArrayList<SelectItem>();
                freeTxtCodeList.add(new SelectItem("0", ""));
            }

            List<CbsLoanInterestCodeMasterTO> listTOs = schemeMasterManagementDelegate.getDataTodRef();
            if (listTOs.size() > 0) {
                interestTableCodeList = new ArrayList<SelectItem>();
                interestTableCodeList.add(new SelectItem("0", ""));
                for (CbsLoanInterestCodeMasterTO tosList : listTOs) {
                    interestTableCodeList.add(new SelectItem(tosList.getInterestCode(), tosList.getInterestCodeDescription()));
                }
            } else {
                interestTableCodeList = new ArrayList<SelectItem>();
                interestTableCodeList.add(new SelectItem("0", ""));
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    //Functionality for strd.jsp file
    public void selectStrdDetails() {
        schemeMaster.setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeTodReferenceDetailsTO> listTOs = schemeMasterManagementDelegate.getStrdDetails(schemeMaster.getSchemeCode());
            if (listTOs.size() > 0) {
                for (CbsSchemeTodReferenceDetailsTO singleTO : listTOs) {
                    CbsSchemeTodReferenceDetailsPKTO pKTo = singleTO.getCbsSchemeTodReferenceDetailsPKTO();
                    SchemeTodReferenceDetails tblObj = new SchemeTodReferenceDetails();
                    if (pKTo.getReferenceType() == null || pKTo.getReferenceType().equalsIgnoreCase("")) {
                        tblObj.setReferenceType("");
                    } else {
                        tblObj.setReferenceType(pKTo.getReferenceType());
                    }
                    if (singleTO.getDiscretNumberOfDays() == null || singleTO.getDiscretNumberOfDays().equalsIgnoreCase("")) {
                        tblObj.setDiscretNumberOfDays("");
                    } else {
                        tblObj.setDiscretNumberOfDays(singleTO.getDiscretNumberOfDays());
                    }
                    if (singleTO.getPenalDays() == null || singleTO.getPenalDays().equalsIgnoreCase("")) {
                        tblObj.setPenalDays("");
                    } else {
                        tblObj.setPenalDays(singleTO.getPenalDays());
                    }
                    if (singleTO.getDiscretAdvnType() == null || singleTO.getDiscretAdvnType().equalsIgnoreCase("")) {
                        tblObj.setDiscretAdvnType("");
                    } else {
                        tblObj.setDiscretAdvnType(singleTO.getDiscretAdvnType());
                    }
                    if (singleTO.getDiscretAdvnCategory() == null || singleTO.getDiscretAdvnCategory().equalsIgnoreCase("")) {
                        tblObj.setDiscretAdvnCategory("");
                    } else {
                        tblObj.setDiscretAdvnCategory(singleTO.getDiscretAdvnCategory());
                    }
                    if (singleTO.getIntFlag() == null || singleTO.getIntFlag().equalsIgnoreCase("")) {
                        tblObj.setIntFlag("");
                    } else {
                        tblObj.setIntFlag(singleTO.getIntFlag());
                    }
                    if (singleTO.getInterestTableCode() == null || singleTO.getInterestTableCode().equalsIgnoreCase("")) {
                        tblObj.setInterestTableCode("");
                    } else {
                        tblObj.setInterestTableCode(singleTO.getInterestTableCode());
                    }
                    if (singleTO.getToLevelIntTblCode() == null || singleTO.getToLevelIntTblCode().equalsIgnoreCase("")) {
                        tblObj.setToLevelIntTblCode("");
                    } else {
                        tblObj.setToLevelIntTblCode(singleTO.getToLevelIntTblCode());
                    }
                    if (singleTO.getFreeTextCode() == null || singleTO.getFreeTextCode().equalsIgnoreCase("")) {
                        tblObj.setFreeTxtCode("");
                    } else {
                        tblObj.setFreeTxtCode(singleTO.getFreeTextCode());
                    }
                    if (singleTO.getDelFlag() == null || singleTO.getDelFlag().equalsIgnoreCase("")) {
                        tblObj.setDelFlagTodRef("");
                    } else {
                        tblObj.setDelFlagTodRef(singleTO.getDelFlag());
                    }

                    tblObj.setCounterSaveUpdate("G");
                    todRef.add(tblObj);
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

    public void setValueInTodRef() {
        List<SchemeTodReferenceDetails> todRefTemp = todRef;
        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (selectCountTodRef == 0) {
                for (int i = 0; i < todRefTemp.size(); i++) {
                    String referenceTypetmp = todRefTemp.get(i).getReferenceType();
                    if (referenceTypetmp.equalsIgnoreCase(referenceType)) {
                        schemeMaster.setMessage("Reference Type Already Exists In The Table. Please Select other Reference Type.");
                        return;
                    }
                }
            }
        }

        if (validateTodRef().equalsIgnoreCase("false")) {
            return;
        }
        schemeMaster.setMessage("");
        SchemeTodReferenceDetails todRefDtl = new SchemeTodReferenceDetails();
        todRefDtl.setReferenceType(getReferenceType());
        todRefDtl.setDiscretNumberOfDays(getDiscretNumberOfDays());
        todRefDtl.setPenalDays(getPenalDays());
        todRefDtl.setDiscretAdvnType(getDiscretAdvnType());
        todRefDtl.setDiscretAdvnCategory(getDiscretAdvnCategory());
        todRefDtl.setIntFlag(getIntFlag());
        todRefDtl.setInterestTableCode(getInterestTableCode());
        todRefDtl.setToLevelIntTblCode(getToLevelIntTblCode());
        todRefDtl.setFreeTxtCode(getFreeTxtCode());
        todRefDtl.setDelFlagTodRef(getDelFlagTodRef());
        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            todRefDtl.setCounterSaveUpdate("S");
            refreshStrdForm();
        }
        todRef.add(todRefDtl);
        setDisableFlagReferenceType(false);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (selectCountTodRef != 1) {
                for (int i = 0; i < todRefTemp.size(); i++) {
                    String referenceTypetmp = todRefTemp.get(i).getReferenceType();

                    if (!referenceTypetmp.equalsIgnoreCase(referenceType)) {
                        todRefDtl.setCounterSaveUpdate("S");
                        todRefTmp.add(todRefDtl);
                        refreshStrdForm();
                        return;
                    }
                }
            }
            if (currentItemtodRef.getReferenceType() == null || currentItemtodRef.getReferenceType().equalsIgnoreCase("")) {
                todRefDtl.setCounterSaveUpdate("S");
                todRefTmp.add(todRefDtl);
                refreshStrdForm();
                return;
            } else if (currentItemtodRef.getReferenceType().equalsIgnoreCase(referenceType)) {
                if (!currentItemtodRef.getDiscretNumberOfDays().equalsIgnoreCase(discretNumberOfDays)
                        || !currentItemtodRef.getPenalDays().equalsIgnoreCase(penalDays)
                        || !currentItemtodRef.getDiscretAdvnType().equalsIgnoreCase(discretAdvnType)
                        || !currentItemtodRef.getDiscretAdvnCategory().equalsIgnoreCase(discretAdvnCategory)
                        || !currentItemtodRef.getIntFlag().equalsIgnoreCase(intFlag)
                        || !currentItemtodRef.getInterestTableCode().equalsIgnoreCase(interestTableCode)
                        || !currentItemtodRef.getToLevelIntTblCode().equalsIgnoreCase(toLevelIntTblCode)
                        || !currentItemtodRef.getFreeTxtCode().equalsIgnoreCase(freeTxtCode)
                        || !currentItemtodRef.getDelFlagTodRef().equalsIgnoreCase(delFlagTodRef)) {

                    todRefDtl.setCounterSaveUpdate("U");
                    refreshStrdForm();
                    todRefTmp.add(todRefDtl);
                }
                selectCountTodRef = 0;
            }
        }
    }

    public void selectTodRef() {
        schemeMaster.setMessage("");
        selectCountTodRef = 1;
        if (!(referenceType.equalsIgnoreCase("0") && currentItemtodRef.getReferenceType() != null)) {
            if (!referenceType.equalsIgnoreCase("")) {
                if (!referenceType.equalsIgnoreCase(currentItemtodRef.getReferenceType())) {
                    count2FTodRef = count1TodRef;
                    count1TodRef = count1TodRef + 1;
                    if (count2FTodRef != count1TodRef) {
                        setValueInTodRef();
                    }
                } else {
                    count1TodRef = 0;
                }
            }
        }

        setReferenceType(currentItemtodRef.getReferenceType());
        setDiscretNumberOfDays(currentItemtodRef.getDiscretNumberOfDays());
        setPenalDays(currentItemtodRef.getPenalDays());
        setDiscretAdvnType(currentItemtodRef.getDiscretAdvnType());
        setDiscretAdvnCategory(currentItemtodRef.getDiscretAdvnCategory());
        setInterestTableCode(currentItemtodRef.getInterestTableCode());
        setIntFlag(currentItemtodRef.getIntFlag());
        setFreeTxtCode(currentItemtodRef.getFreeTxtCode());
        setToLevelIntTblCode(currentItemtodRef.getToLevelIntTblCode());
        setDelFlagTodRef(currentItemtodRef.getDelFlagTodRef());
        todRef.remove(currentRowTodRef);
        setDisableFlagReferenceType(true);
    }

    public String validateTodRef() {
        schemeMaster.setMessage("");
        String msg = "";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (referenceType.equalsIgnoreCase("0")) {
            msg = msg + "Please Select The Reference Type.";
        }
        if (discretAdvnType.equalsIgnoreCase("0")) {
            msg = msg + "Please Select The Discret Advn Type.";
        }
        if (discretAdvnCategory.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Discret Advn Category.";
        }
        if (intFlag.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Int Flag.";
        }
        if (interestTableCode.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Interest Table Code.";
        }
        if (toLevelIntTblCode.equalsIgnoreCase("0")) {
            msg = msg + "Please Select To Level Int Table Code.";
        }
        if (freeTxtCode.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Free Txt Code.";
        }
        if (delFlagTodRef.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Delete Flag.";
        }
        if (discretNumberOfDays.equalsIgnoreCase("")) {
        } else {
            Matcher discretNoDays = p.matcher(discretNumberOfDays);
            if (!discretNoDays.matches()) {
                msg = msg + " Discret Number Of Days Should be numeric";
            }
        }
        if (penalDays.equalsIgnoreCase("")) {
        } else {
            Matcher penalDay = p.matcher(penalDays);
            if (!penalDay.matches()) {
                msg = msg + "Penal Days Of Days Should be numeric";
            }
        }

        if (!msg.equalsIgnoreCase("")) {
            schemeMaster.setMessage(msg);
            return "False";
        }
        return "true";
    }

    public void refreshStrdForm() {
        this.setReferenceType("0");
        this.setDiscretNumberOfDays("");
        this.setPenalDays("0");
        this.setDiscretAdvnType("0");
        this.setDiscretAdvnCategory("0");
        this.setInterestTableCode("0");
        this.setFreeTxtCode("0");
        this.setDelFlagTodRef("0");
        this.setIntFlag("0");
        this.setToLevelIntTblCode("0");
    }

    public void enableStrdForm() {
        this.disableFlagTodRef = false;
    }

    public void disableStrdForm() {
        this.disableFlagTodRef = true;
    }
}
