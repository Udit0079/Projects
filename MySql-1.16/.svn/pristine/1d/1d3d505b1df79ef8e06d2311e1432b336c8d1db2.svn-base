/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.complex.TodExceptionDetailsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.SchemeTodCurrencyWiseTable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class TEDCW {
    
    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;
    private String bgAmt;
    private String endAmt;
    private String todException;
    private String deleteTod;
    private String todExceptionDesc;
    private String currencyCodeTod;
    int count1Tod = 0;
    int count2Tod = 0;
    private int currentRowTod;
    private String serialNo;
    private int selectCountTod = 0;
    private boolean bgAmtDisable;
    private boolean endAmtDisable;
    private boolean deleteTodDisable;
    private boolean exceptionDisable;
    private SchemeTodCurrencyWiseTable currentItemTod = new SchemeTodCurrencyWiseTable();
    private List<SchemeTodCurrencyWiseTable> schemeTod;
    private List<SchemeTodCurrencyWiseTable> schTodTmp;
    private List<SelectItem> currencyCodeList;
    private List<SelectItem> deleteTedcwList;
    
    public List<SelectItem> getDeleteTedcwList() {
        return deleteTedcwList;
    }
    
    public void setDeleteTedcwList(List<SelectItem> deleteTedcwList) {
        this.deleteTedcwList = deleteTedcwList;
    }

    public boolean isDeleteTodDisable() {
        return deleteTodDisable;
    }

    public void setDeleteTodDisable(boolean deleteTodDisable) {
        this.deleteTodDisable = deleteTodDisable;
    }
    
    public String getBgAmt() {
        return bgAmt;
    }
    
    public void setBgAmt(String bgAmt) {
        this.bgAmt = bgAmt;
    }
    
    public boolean isBgAmtDisable() {
        return bgAmtDisable;
    }
    
    public void setBgAmtDisable(boolean bgAmtDisable) {
        this.bgAmtDisable = bgAmtDisable;
    }
    
    public int getCount1Tod() {
        return count1Tod;
    }
    
    public void setCount1Tod(int count1Tod) {
        this.count1Tod = count1Tod;
    }
    
    public int getCount2Tod() {
        return count2Tod;
    }
    
    public void setCount2Tod(int count2Tod) {
        this.count2Tod = count2Tod;
    }
    
    public List<SelectItem> getCurrencyCodeList() {
        return currencyCodeList;
    }
    
    public void setCurrencyCodeList(List<SelectItem> currencyCodeList) {
        this.currencyCodeList = currencyCodeList;
    }
    
    public String getCurrencyCodeTod() {
        return currencyCodeTod;
    }
    
    public void setCurrencyCodeTod(String currencyCodeTod) {
        this.currencyCodeTod = currencyCodeTod;
    }
    
    public SchemeTodCurrencyWiseTable getCurrentItemTod() {
        return currentItemTod;
    }
    
    public void setCurrentItemTod(SchemeTodCurrencyWiseTable currentItemTod) {
        this.currentItemTod = currentItemTod;
    }
    
    public int getCurrentRowTod() {
        return currentRowTod;
    }
    
    public void setCurrentRowTod(int currentRowTod) {
        this.currentRowTod = currentRowTod;
    }
    
    public String getDeleteTod() {
        return deleteTod;
    }
    
    public void setDeleteTod(String deleteTod) {
        this.deleteTod = deleteTod;
    }
    
    public String getEndAmt() {
        return endAmt;
    }
    
    public void setEndAmt(String endAmt) {
        this.endAmt = endAmt;
    }
    
    public boolean isEndAmtDisable() {
        return endAmtDisable;
    }
    
    public void setEndAmtDisable(boolean endAmtDisable) {
        this.endAmtDisable = endAmtDisable;
    }
    
    public List<SchemeTodCurrencyWiseTable> getSchTodTmp() {
        return schTodTmp;
    }
    
    public void setSchTodTmp(List<SchemeTodCurrencyWiseTable> schTodTmp) {
        this.schTodTmp = schTodTmp;
    }
    
    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }
    
    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    
    public List<SchemeTodCurrencyWiseTable> getSchemeTod() {
        return schemeTod;
    }
    
    public void setSchemeTod(List<SchemeTodCurrencyWiseTable> schemeTod) {
        this.schemeTod = schemeTod;
    }
    
    public int getSelectCountTod() {
        return selectCountTod;
    }
    
    public void setSelectCountTod(int selectCountTod) {
        this.selectCountTod = selectCountTod;
    }
    
    public String getSerialNo() {
        return serialNo;
    }
    
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    
    public String getTodException() {
        return todException;
    }
    
    public void setTodException(String todException) {
        this.todException = todException;
    }
    
    public String getTodExceptionDesc() {
        return todExceptionDesc;
    }
    
    public void setTodExceptionDesc(String todExceptionDesc) {
        this.todExceptionDesc = todExceptionDesc;
    }

    public boolean isExceptionDisable() {
        return exceptionDisable;
    }

    public void setExceptionDisable(boolean exceptionDisable) {
        this.exceptionDisable = exceptionDisable;
    }
    

    /** Creates a new instance of TEDCW */
    public TEDCW() {
       
        schemeTod = new ArrayList<SchemeTodCurrencyWiseTable>();
        schTodTmp = new ArrayList<SchemeTodCurrencyWiseTable>();
        
        deleteTedcwList = new ArrayList<SelectItem>();
        deleteTedcwList.add(new SelectItem("0", " "));
        deleteTedcwList.add(new SelectItem("Y", "Yes"));
        deleteTedcwList.add(new SelectItem("N", "No"));
    }
    
    public void selectSchemeTod() {
        selectCountTod = 1;
        if ((bgAmt != null && currentItemTod.getBgAmt() != null)) {
            if (!bgAmt.equalsIgnoreCase("")) {
                if (!bgAmt.equalsIgnoreCase(currentItemTod.getBgAmt()) || !endAmt.equalsIgnoreCase(currentItemTod.getEndAmt()) || !schemeMaster.currencyType.equalsIgnoreCase(currentItemTod.getCurrencyCode())) {
                    count2Tod = count1Tod;
                    count1Tod = count1Tod + 1;
                    if (count2Tod != count1Tod) {
                        setValueInTodTable();
                        setBgAmtDisable(true);
                        setEndAmtDisable(true);
                    }
                } else {
                    count1Tod = 0;
                }
            }
        }
        setBgAmt(currentItemTod.getBgAmt());
        setEndAmt(currentItemTod.getEndAmt());
        setTodException(currentItemTod.getTodException());
        setDeleteTod(currentItemTod.getDeleteTod());
        schemeMaster.setCurrencyType(currentItemTod.getCurrencyCode());
        setTodExceptionDesc(currentItemTod.getTodExceptionDesc());
        serialNo = currentItemTod.getSerialNo();
        schemeTod.remove(currentRowTod);
        setBgAmtDisable(true);
        setEndAmtDisable(true);
    }
    
    public void setValueInTodTable() {
        schemeMaster.setMessage("");
        if (bgAmt == null || bgAmt.equalsIgnoreCase("")) {
            schemeMaster.setMessage("Please Fill begin Amount");
            return;
        }
        if (endAmt == null || endAmt.equalsIgnoreCase("")) {
            schemeMaster.setMessage("Please Fill End Amount");
            return;
        }
        List<SchemeTodCurrencyWiseTable> tmpschTodTable = schemeTod;
        setBgAmtDisable(false);
        setEndAmtDisable(false);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (selectCountTod == 0) {
                for (int i = 0; i < tmpschTodTable.size(); i++) {
                    String tmpCurrencyCode = tmpschTodTable.get(i).getCurrencyCode();
                    String tmpBgAmt = tmpschTodTable.get(i).getBgAmt();
                    String tmpEndAmt = tmpschTodTable.get(i).getEndAmt();
                    
                    if (tmpCurrencyCode.equalsIgnoreCase(schemeMaster.currencyType)) {
                        
                        if (Float.parseFloat(tmpBgAmt) == Float.parseFloat(bgAmt)) {
                            if (Float.parseFloat(tmpEndAmt) == Float.parseFloat(endAmt)) {
                                
                                schemeMaster.setMessage("Entry Already Exists In The Table");
                                return;
                            }
                        }
                    }
                }
            }
        }
        
        if (validateTod().equalsIgnoreCase("false")) {
            return;
        }
        
        SchemeTodCurrencyWiseTable schTod = new SchemeTodCurrencyWiseTable();
        schTod.setBgAmt(getBgAmt());
        schTod.setEndAmt(getEndAmt());
        schTod.setTodException(getTodException());
        schTod.setDeleteTod(getDeleteTod());
        schTod.setCurrencyCode(schemeMaster.getCurrencyType());
        schTod.setTodExceptionDesc(getTodExceptionDesc());
        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            refreshTEDCWForm();
            schTod.setCounterSaveUpdate("S");
        }
        schemeTod.add(schTod);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (selectCountTod == 0) {
                for (int i = 0; i < tmpschTodTable.size(); i++) {
                    String tmpbgAmt = tmpschTodTable.get(i).getBgAmt();
                    if (!tmpbgAmt.equalsIgnoreCase(bgAmt)) {
                        schTod.setCounterSaveUpdate("S");
                        schTodTmp.add(schTod);
                        refreshTEDCWForm();
                        return;
                    }
                }
                selectCountTod = 0;
            }
            if (currentItemTod.getCurrencyCode() == null || currentItemTod.getCurrencyCode().equalsIgnoreCase("")) {
                schTod.setCounterSaveUpdate("S");
                schTodTmp.add(schTod);
                refreshTEDCWForm();
                return;
            } else if (currentItemTod.getCurrencyCode().equalsIgnoreCase(schemeMaster.currencyType) || currentItemTod.getBgAmt().equalsIgnoreCase(bgAmt) || currentItemTod.getEndAmt().equalsIgnoreCase(endAmt)) {
                if (!currentItemTod.getTodException().equalsIgnoreCase(todException) || !currentItemTod.getDeleteTod().equalsIgnoreCase(deleteTod)) {
                    schTod.setCounterSaveUpdate("U");
                    
                    schTod.setSerialNo(serialNo);
                    refreshTEDCWForm();
                    schTodTmp.add(schTod);
                }
                
            }
        }
    }
    
    public void setTodExceptionFun() {
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsExceptionMasterTO exceptionTO = schemeMasterManagementDelegate.getExceptionDesc(this.todException);
            if (exceptionTO != null) {
                if (exceptionTO.getExceptionDesc() == null || exceptionTO.getExceptionDesc().equalsIgnoreCase("")) {
                    schemeMaster.setMessage("Exception Does Not Exist");
                    return;
                } else {
                    this.setTodExceptionDesc(exceptionTO.getExceptionDesc());
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
    
    public String validateTod() {
        String msg = "";
        if (bgAmt.equalsIgnoreCase(" ") || bgAmt == null) {
            msg = msg + "Please Select  Enter  begin Amt";
        }
        if (bgAmt.equalsIgnoreCase(" ") || endAmt == null) {
            msg = msg + "Please Select  Enter  End Amt";
        }
        if ((bgAmt != null) && (endAmt != null)) {
            if (Float.parseFloat(bgAmt) > Float.parseFloat(endAmt)) {
                msg = msg + "Begin Amt can not be greater than End Amt";
            }
        }
        if (todException.equalsIgnoreCase(" ") || todException == null) {
            msg = msg + "Please Select  Enter  End Amt";
        }
        if (deleteTod.equalsIgnoreCase("0")) {
            msg = msg + "Please Select The Delete Flag";
        }
        if (!msg.equalsIgnoreCase("")) {
            schemeMaster.setMessage(msg);
            return "False";
        }
        return "true";
    }
    
    public void selectTEDCWDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<TodExceptionDetailsTO> listTOs = schemeMasterManagementDelegate.getTODExceptionDetailsAccToSchemeTod(schemeMaster.schemeCode);
            if (listTOs.size() > 0) {
                for (TodExceptionDetailsTO singleTO : listTOs) {
                    SchemeTodCurrencyWiseTable dt = new SchemeTodCurrencyWiseTable();
                    if (singleTO.getBeginAmount().toString() == null || singleTO.getBeginAmount().toString().equalsIgnoreCase("")) {
                        dt.setBgAmt("");
                    } else {
                        dt.setBgAmt(singleTO.getBeginAmount().toString());
                    }
                    if (singleTO.getEndAmount().toString() == null || singleTO.getEndAmount().toString().toString().equalsIgnoreCase("")) {
                        dt.setEndAmt("");
                    } else {
                        dt.setEndAmt(singleTO.getEndAmount().toString());
                    }
                    if (singleTO.getTodException() == null || singleTO.getTodException().equalsIgnoreCase("")) {
                        dt.setTodException("");
                    } else {
                        dt.setTodException(singleTO.getTodException());
                    }
                    if (singleTO.getDelFlag() == null || singleTO.getDelFlag().equalsIgnoreCase("")) {
                        dt.setDeleteTod("");
                    } else {
                        dt.setDeleteTod(singleTO.getDelFlag());
                    }
                    if (singleTO.getExceptionDesc() == null || singleTO.getExceptionDesc().equalsIgnoreCase("")) {
                        dt.setTodExceptionDesc("");
                    } else {
                        dt.setTodExceptionDesc(singleTO.getExceptionDesc());
                    }
                    if (singleTO.getCurrencyCode() == null || singleTO.getCurrencyCode().equalsIgnoreCase("")) {
                        dt.setCurrencyCode("");
                    } else {
                        dt.setCurrencyCode(singleTO.getCurrencyCode());
                    }
                    if (singleTO.getTodSrlNo() == null || singleTO.getTodSrlNo().equalsIgnoreCase("")) {
                        dt.setSerialNo("");
                    } else {
                        dt.setSerialNo(singleTO.getTodSrlNo());
                    }
                    dt.setCounterSaveUpdate(singleTO.getCounterSaveUpdate());
                    schemeTod.add(dt);
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
    
    public void refreshTEDCWForm() {
        this.setBgAmt("0.0");
        this.setEndAmt("0.0");
        this.setTodException("");
        this.setTodExceptionDesc("");
        this.setDeleteTod("0");
    }
    
    public void enableTEDCWForm() {
        bgAmtDisable = false;
        endAmtDisable = false;
        deleteTodDisable=false;
        exceptionDisable=false;
    }
    
    public void disableTEDCWForm() {
        bgAmtDisable = true;
        endAmtDisable = true;
        deleteTodDisable=true;
        exceptionDisable=true;
    }
}
