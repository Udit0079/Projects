/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeTransactionReportCodeCurrencyWisePKTO;
import com.cbs.dto.loan.CbsSchemeTransactionReportCodeCurrencyWiseTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.SchemeTranReportCodeCurrWise;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class TRCCW {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for trccw.jsp file
    private String transactionReportCode;
    private String crAmt;
    private String drAmt;
    private String deleteTranCode;
    int count1TranCurr = 0;
    int count2TranCurr = 0;
    private int selectCountTod = 0;
    private int currentRowTranCurr;
    private int selectCountTranCurr = 0;
    private boolean disableFlagTranCode;
    boolean tranReportCodeDisable;
    private List<SchemeTranReportCodeCurrWise> tranRepCodeWise;
    private List<SchemeTranReportCodeCurrWise> tranCodeTmp;
    private SchemeTranReportCodeCurrWise currentItemTran = new SchemeTranReportCodeCurrWise();
    private List<SelectItem> transactionRepCodeList;
    private List<SelectItem> deleteTrccwList;

    //Getter-Setter for trccw.jsp file
    public int getSelectCountTod() {
        return selectCountTod;
    }

    public void setSelectCountTod(int selectCountTod) {
        this.selectCountTod = selectCountTod;
    }

    public List<SchemeTranReportCodeCurrWise> getTranCodeTmp() {
        return tranCodeTmp;
    }

    public void setTranCodeTmp(List<SchemeTranReportCodeCurrWise> tranCodeTmp) {
        this.tranCodeTmp = tranCodeTmp;
    }

    public List<SelectItem> getDeleteTrccwList() {
        return deleteTrccwList;
    }

    public void setDeleteTrccwList(List<SelectItem> deleteTrccwList) {
        this.deleteTrccwList = deleteTrccwList;
    }

    public int getCount1TranCurr() {
        return count1TranCurr;
    }

    public void setCount1TranCurr(int count1TranCurr) {
        this.count1TranCurr = count1TranCurr;
    }

    public int getCount2TranCurr() {
        return count2TranCurr;
    }

    public void setCount2TranCurr(int count2TranCurr) {
        this.count2TranCurr = count2TranCurr;
    }

    public String getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(String crAmt) {
        this.crAmt = crAmt;
    }

    public SchemeTranReportCodeCurrWise getCurrentItemTran() {
        return currentItemTran;
    }

    public void setCurrentItemTran(SchemeTranReportCodeCurrWise currentItemTran) {
        this.currentItemTran = currentItemTran;
    }

    public int getCurrentRowTranCurr() {
        return currentRowTranCurr;
    }

    public void setCurrentRowTranCurr(int currentRowTranCurr) {
        this.currentRowTranCurr = currentRowTranCurr;
    }

    public String getDeleteTranCode() {
        return deleteTranCode;
    }

    public void setDeleteTranCode(String deleteTranCode) {
        this.deleteTranCode = deleteTranCode;
    }

    public boolean isDisableFlagTranCode() {
        return disableFlagTranCode;
    }

    public void setDisableFlagTranCode(boolean disableFlagTranCode) {
        this.disableFlagTranCode = disableFlagTranCode;
    }

    public String getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(String drAmt) {
        this.drAmt = drAmt;
    }

    public int getSelectCountTranCurr() {
        return selectCountTranCurr;
    }

    public void setSelectCountTranCurr(int selectCountTranCurr) {
        this.selectCountTranCurr = selectCountTranCurr;
    }

    public List<SchemeTranReportCodeCurrWise> getTranRepCodeWise() {
        return tranRepCodeWise;
    }

    public void setTranRepCodeWise(List<SchemeTranReportCodeCurrWise> tranRepCodeWise) {
        this.tranRepCodeWise = tranRepCodeWise;
    }

    public boolean isTranReportCodeDisable() {
        return tranReportCodeDisable;
    }

    public void setTranReportCodeDisable(boolean tranReportCodeDisable) {
        this.tranReportCodeDisable = tranReportCodeDisable;
    }

    public List<SelectItem> getTransactionRepCodeList() {
        return transactionRepCodeList;
    }

    public void setTransactionRepCodeList(List<SelectItem> transactionRepCodeList) {
        this.transactionRepCodeList = transactionRepCodeList;
    }

    public String getTransactionReportCode() {
        return transactionReportCode;
    }

    public void setTransactionReportCode(String transactionReportCode) {
        this.transactionReportCode = transactionReportCode;
    }

    /** Creates a new instance of TRCCW */
    public TRCCW() {
       
        try {
            tranRepCodeWise = new ArrayList<SchemeTranReportCodeCurrWise>();
            tranCodeTmp = new ArrayList<SchemeTranReportCodeCurrWise>();

            deleteTrccwList = new ArrayList<SelectItem>();
            deleteTrccwList.add(new SelectItem("0", ""));
            deleteTrccwList.add(new SelectItem("Y", "Yes"));
            deleteTrccwList.add(new SelectItem("N", "No"));
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsRefRecTypeTO> CbsRefRecTypeTOs1 = schemeMasterManagementDelegate.getCurrencyCode("010");
            if (CbsRefRecTypeTOs1.size() > 0) {
                transactionRepCodeList = new ArrayList<SelectItem>();
                transactionRepCodeList.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs1) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    transactionRepCodeList.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				transactionRepCodeList = new ArrayList<SelectItem>();
                transactionRepCodeList.add(new SelectItem("0", ""));
			}
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    //Functionality for trccw.jsp file
    public void selectTrccwDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeTransactionReportCodeCurrencyWiseTO> listTOs = schemeMasterManagementDelegate.getTrccwdetails(schemeMaster.getSchemeCode());
            if (listTOs.size() > 0) {
                for (CbsSchemeTransactionReportCodeCurrencyWiseTO singleTO : listTOs) {
                    CbsSchemeTransactionReportCodeCurrencyWisePKTO pKTo = singleTO.getCbsSchemeTransactionReportCodeCurrencyWisePKTO();
                    SchemeTranReportCodeCurrWise tblObj = new SchemeTranReportCodeCurrWise();
                    if (pKTo.getTransactionReportCode() == null || pKTo.getTransactionReportCode().equalsIgnoreCase("")) {
                        tblObj.setTransactionReportCode("");
                    } else {
                        tblObj.setTransactionReportCode(pKTo.getTransactionReportCode());
                    }
                    if (singleTO.getCurrencyCode() == null || singleTO.getCurrencyCode().equalsIgnoreCase("")) {
                        tblObj.setCurrencyCodeTranRep("");
                    } else {
                        tblObj.setCurrencyCodeTranRep(singleTO.getCurrencyCode());
                    }
                    if (singleTO.getCrAmtLimit().toString() == null || singleTO.getCrAmtLimit().toString().equalsIgnoreCase("")) {
                        tblObj.setCrAmt("");
                    } else {
                        tblObj.setCrAmt(singleTO.getCrAmtLimit().toString());
                    }
                    if (singleTO.getDrAmtLimit().toString() == null || singleTO.getDrAmtLimit().toString().equalsIgnoreCase("")) {
                        tblObj.setDrAmt("");
                    } else {
                        tblObj.setDrAmt(singleTO.getDrAmtLimit().toString());
                    }
                    if (singleTO.getDelFlag() == null || singleTO.getDelFlag().equalsIgnoreCase("")) {
                        tblObj.setDeleteTranCode("");
                    } else {
                        tblObj.setDeleteTranCode(singleTO.getDelFlag());
                    }

                    tblObj.setCounterSaveUpdate("G");
                    tranRepCodeWise.add(tblObj);
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

    public void setValueInTranRepCurrWiseTable() {
        if (transactionReportCode == null || transactionReportCode.equalsIgnoreCase("")) {
            schemeMaster.setMessage("Please Select Transaction Report Code ");
            return;
        }

        List<SchemeTranReportCodeCurrWise> tmpTranTable = tranRepCodeWise;
        setTranReportCodeDisable(false);

        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (selectCountTod == 0) {
                for (int i = 0; i < tmpTranTable.size(); i++) {
                    String transactionReportCodetmp = tmpTranTable.get(i).getTransactionReportCode();
                    if (transactionReportCodetmp.equalsIgnoreCase(transactionReportCode)) {
                        schemeMaster.setMessage("Transaction Report Code Already Exists In The Table. Please Select other Transaction Report Code.");
                        return;
                    }
                }
            }
        }

        if (validateTranCurCode().equalsIgnoreCase("false")) {
            return;
        }
        SchemeTranReportCodeCurrWise tranReportCodeCurrWise = new SchemeTranReportCodeCurrWise();
        tranReportCodeCurrWise.setCrAmt(getCrAmt());
        tranReportCodeCurrWise.setDrAmt(getDrAmt());
        tranReportCodeCurrWise.setCurrencyCodeTranRep(schemeMaster.getCurrencyType());
        tranReportCodeCurrWise.setDeleteTranCode(getDeleteTranCode());
        tranReportCodeCurrWise.setTransactionReportCode(getTransactionReportCode());
        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            tranReportCodeCurrWise.setCounterSaveUpdate("S");
            refreshTrccwForm();
        }
        tranRepCodeWise.add(tranReportCodeCurrWise);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {

            if (selectCountTranCurr != 1) {
                for (int i = 0; i < tmpTranTable.size(); i++) {
                    String transactionReportCd = tmpTranTable.get(i).getTransactionReportCode();
                    if (!transactionReportCd.equalsIgnoreCase(transactionReportCode)) {
                        tranReportCodeCurrWise.setCounterSaveUpdate("S");
                        tranCodeTmp.add(tranReportCodeCurrWise);
                        refreshTrccwForm();
                        return;
                    }
                }

            }
            if (currentItemTran.getTransactionReportCode() == null || currentItemTran.getTransactionReportCode().equalsIgnoreCase("")) {
                tranReportCodeCurrWise.setCounterSaveUpdate("S");
                tranCodeTmp.add(tranReportCodeCurrWise);
                refreshTrccwForm();
                return;
            } else if (currentItemTran.getTransactionReportCode().equalsIgnoreCase(transactionReportCode)) {
                if (!currentItemTran.getCrAmt().equalsIgnoreCase(crAmt) || !currentItemTran.getDrAmt().equalsIgnoreCase(drAmt) || !currentItemTran.getDeleteTranCode().equalsIgnoreCase(deleteTranCode)) {

                    tranReportCodeCurrWise.setCounterSaveUpdate("U");
                    refreshTrccwForm();
                    tranCodeTmp.add(tranReportCodeCurrWise);
                }
                selectCountTranCurr = 0;
            }
        }
    }

    public void selectTranRepCurrWise() {
        selectCountTranCurr = 1;
        if (transactionReportCode != null && currentItemTran.getTransactionReportCode() != null) {
            if (!transactionReportCode.equalsIgnoreCase("")) {
                if (!transactionReportCode.equalsIgnoreCase(currentItemTran.getTransactionReportCode())
                        || !crAmt.equalsIgnoreCase(currentItemTran.getCrAmt()) || !drAmt.equalsIgnoreCase(currentItemTran.getDrAmt())
                        || !deleteTranCode.equalsIgnoreCase(currentItemTran.getDeleteTranCode())) {
                    count2TranCurr = count1TranCurr;
                    count1TranCurr = count1TranCurr + 1;
                    if (count2TranCurr != count1TranCurr) {
                        setValueInTranRepCurrWiseTable();
                        setTranReportCodeDisable(true);
                    }
                } else {
                    count1TranCurr = 0;
                }
            }
        }

        setCrAmt(currentItemTran.getCrAmt());
        setDrAmt(currentItemTran.getDrAmt());
        setTransactionReportCode(currentItemTran.getTransactionReportCode());
        setDeleteTranCode(currentItemTran.getDeleteTranCode());
        tranRepCodeWise.remove(currentRowTranCurr);
        setTranReportCodeDisable(true);
    }

    public String validateTranCurCode() {
        String msg = "";
        if (crAmt.equalsIgnoreCase("") || crAmt == null) {
            msg = msg + "Please Enter Credit Amt";
        }
        if (drAmt.equalsIgnoreCase("") || drAmt == null) {
            msg = msg + "Please  Enter Debit Amt";
        }

        if (transactionReportCode.equalsIgnoreCase("")) {
            msg = msg + "Please Enter Transaction Report Code";
        }
        if (deleteTranCode.equalsIgnoreCase("0")) {
            msg = msg + "Please Select The Delete Flag";
        }
        if (!msg.equalsIgnoreCase("")) {
            schemeMaster.setMessage(msg);
            return "False";
        }
        return "true";
    }

    public void refreshTrccwForm() {
        this.setDeleteTranCode("0");
        this.setTransactionReportCode("0");
        this.setCrAmt("0.0");
        this.setDrAmt("0.0");
    }

    public void enableTrccwForm() {
        this.disableFlagTranCode = false;
    }

    public void disableTrccwForm() {
        this.disableFlagTranCode = true;
    }
}
