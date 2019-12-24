/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeAssetDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeAssetDetailsTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.LoanAsset;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class AD {

    private static final Logger logger = Logger.getLogger(AD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for ad.jap file
    private String dPDCounter;
    private String mainClass;
    private String subClass;
    private String intAccre;
    private String intFlagBk;
    private String intFlagColl;
    private String pDFlag;
    private String intSuspPlaceHolder;
    private String provisionDR;
    private String placeHoldersCr;
    private String delFlag;
    private int currentRow1;
    private int schemeAsset = 0;
    private int count11;
    private int count22;
    boolean disablePDP;
    private boolean adFlag;
    private List<SelectItem> ddAdTrnRefNo;
    private LoanAsset currentItem1 = new LoanAsset();
    private List<LoanAsset> asset;
    private List<LoanAsset> assettmp;

    //Getter-Setter for ad.jsp file
    public List<SelectItem> getDdAdTrnRefNo() {
        return ddAdTrnRefNo;
    }

    public void setDdAdTrnRefNo(List<SelectItem> ddAdTrnRefNo) {
        this.ddAdTrnRefNo = ddAdTrnRefNo;
    }

    public boolean isAdFlag() {
        return adFlag;
    }

    public void setAdFlag(boolean adFlag) {
        this.adFlag = adFlag;
    }

    public List<LoanAsset> getAsset() {
        return asset;
    }

    public void setAsset(List<LoanAsset> asset) {
        this.asset = asset;
    }

    public LoanAsset getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(LoanAsset currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public String getdPDCounter() {
        return dPDCounter;
    }

    public void setdPDCounter(String dPDCounter) {
        this.dPDCounter = dPDCounter;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isDisablePDP() {
        return disablePDP;
    }

    public void setDisablePDP(boolean disablePDP) {
        this.disablePDP = disablePDP;
    }

    public String getIntAccre() {
        return intAccre;
    }

    public void setIntAccre(String intAccre) {
        this.intAccre = intAccre;
    }

    public String getIntFlagBk() {
        return intFlagBk;
    }

    public void setIntFlagBk(String intFlagBk) {
        this.intFlagBk = intFlagBk;
    }

    public String getIntFlagColl() {
        return intFlagColl;
    }

    public void setIntFlagColl(String intFlagColl) {
        this.intFlagColl = intFlagColl;
    }

    public String getIntSuspPlaceHolder() {
        return intSuspPlaceHolder;
    }

    public void setIntSuspPlaceHolder(String intSuspPlaceHolder) {
        this.intSuspPlaceHolder = intSuspPlaceHolder;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getpDFlag() {
        return pDFlag;
    }

    public void setpDFlag(String pDFlag) {
        this.pDFlag = pDFlag;
    }

    public String getPlaceHoldersCr() {
        return placeHoldersCr;
    }

    public void setPlaceHoldersCr(String placeHoldersCr) {
        this.placeHoldersCr = placeHoldersCr;
    }

    public String getProvisionDR() {
        return provisionDR;
    }

    public void setProvisionDR(String provisionDR) {
        this.provisionDR = provisionDR;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public List<LoanAsset> getAssettmp() {
        return assettmp;
    }

    public void setAssettmp(List<LoanAsset> assettmp) {
        this.assettmp = assettmp;
    }

    public int getCount11() {
        return count11;
    }

    public void setCount11(int count11) {
        this.count11 = count11;
    }

    public int getCount22() {
        return count22;
    }

    public void setCount22(int count22) {
        this.count22 = count22;
    }

    public int getSchemeAsset() {
        return schemeAsset;
    }

    public void setSchemeAsset(int schemeAsset) {
        this.schemeAsset = schemeAsset;
    }

    /** Creates a new instance of AD */
    public AD() {
        
        try {
            asset = new ArrayList<LoanAsset>();
            assettmp = new ArrayList<LoanAsset>();

            ddAdTrnRefNo = new ArrayList<SelectItem>();
            ddAdTrnRefNo.add(new SelectItem("0", ""));
            ddAdTrnRefNo.add(new SelectItem("Y", "Yes"));
            ddAdTrnRefNo.add(new SelectItem("N", "No"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Functionality for ad.jsp file
    public void setTable() {
        this.disablePDP = false;
        List<LoanAsset> assetTemp = asset;

        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (schemeAsset == 0) {
                for (int i = 0; i < assetTemp.size(); i++) {
                    String tmpCompare = assetTemp.get(i).getTbdPDCounter();
                    if (tmpCompare.equalsIgnoreCase(dPDCounter)) {
                        schemeMaster.setMessage("dPDCounter Already Exists In The Table. Please Select other dPDCounter.");
                        return;
                    }
                }
            }
        }

        LoanAsset stk = new LoanAsset();
        stk.setTbdPDCounter(dPDCounter);
        stk.setTbMainClass(mainClass);
        stk.setTbSubClass(subClass);
        stk.setTbIntAccre(intAccre);
        stk.setTbIntFlagBk(intFlagBk);
        stk.setTbIntFlagColl(intFlagColl);
        stk.setTbPDFlag(pDFlag);
        stk.setTbIntSuspPlaceHolder(intSuspPlaceHolder);
        stk.setTbprovisionDR(provisionDR);
        stk.setTbPlaceHoldersCr(placeHoldersCr);
        stk.setTbdelFlag(delFlag);
        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            stk.setCounterSaveUpdate("S");
            refreshADForm();
        }

        asset.add(stk);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (schemeAsset != 1) {
                for (int i = 0; i < assetTemp.size(); i++) {
                    String referenceTypetmp = assetTemp.get(i).getTbdPDCounter();
                    if (!referenceTypetmp.equalsIgnoreCase(dPDCounter)) {
                        stk.setCounterSaveUpdate("S");
                        assettmp.add(stk);
                        refreshADForm();
                        return;
                    }
                }
            }
            if (currentItem1.getTbdPDCounter() == null || currentItem1.getTbdPDCounter().equalsIgnoreCase("")) {
                stk.setCounterSaveUpdate("S");
                assettmp.add(stk);
                refreshADForm();
                return;

            } else if (currentItem1.getTbdPDCounter().equalsIgnoreCase(dPDCounter)) {

                if (!currentItem1.getTbdPDCounter().equalsIgnoreCase(dPDCounter)
                        || !currentItem1.getTbMainClass().equalsIgnoreCase(mainClass)
                        || !currentItem1.getTbSubClass().equalsIgnoreCase(subClass)
                        || !currentItem1.getTbIntAccre().equalsIgnoreCase(intAccre)
                        || !currentItem1.getTbIntFlagBk().equalsIgnoreCase(intFlagBk)
                        || !currentItem1.getTbIntFlagColl().equalsIgnoreCase(intFlagColl)
                        || !currentItem1.getTbPDFlag().equalsIgnoreCase(pDFlag)
                        || !currentItem1.getTbIntSuspPlaceHolder().equalsIgnoreCase(intSuspPlaceHolder)
                        || !currentItem1.getTbprovisionDR().equalsIgnoreCase(provisionDR)
                        || !currentItem1.getTbPlaceHoldersCr().equalsIgnoreCase(placeHoldersCr)
                        || !currentItem1.getTbdelFlag().equalsIgnoreCase(delFlag)) {
                    stk.setCounterSaveUpdate("U");
                    refreshADForm();
                    assettmp.add(stk);
                }
                schemeAsset = 0;
            }
        }
    }

    public void selectAsset() {
        this.disablePDP = true;
        schemeMaster.setMessage("");
        schemeAsset = 1;

        if (!(dPDCounter.equalsIgnoreCase("0") && currentItem1.getTbdPDCounter() != null)) {
            if (!dPDCounter.equalsIgnoreCase("")) {
                if (!dPDCounter.equalsIgnoreCase(currentItem1.getTbdPDCounter())) {
                    count22 = count11;
                    count11 = count11 + 1;
                    if (count22 != count11) {
                        setTable();
                    }
                } else {
                    count11 = 0;
                }
            }
        }

        setdPDCounter(currentItem1.getTbdPDCounter());
        setMainClass(currentItem1.getTbMainClass());
        setSubClass(currentItem1.getTbSubClass());
        setIntAccre(currentItem1.getTbIntAccre());
        setIntFlagBk(currentItem1.getTbIntFlagBk());
        setIntFlagColl(currentItem1.getTbIntFlagColl());
        setpDFlag(currentItem1.getTbPDFlag());
        setIntSuspPlaceHolder(currentItem1.getTbIntSuspPlaceHolder());
        setProvisionDR(currentItem1.getTbprovisionDR());
        setPlaceHoldersCr(currentItem1.getTbPlaceHoldersCr());
        setDelFlag(currentItem1.getTbdelFlag());
        asset.remove(currentRow1);
    }

    public void selectADDetails() {
        this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeAssetDetailsTO> cbsSchemeAssetDetailsTOs = schemeMasterManagementDelegate.getSelectAssetDetails(schemeMaster.schemeCode);
            if (cbsSchemeAssetDetailsTOs.size() > 0) {
                for (CbsSchemeAssetDetailsTO assetDetails : cbsSchemeAssetDetailsTOs) {
                    LoanAsset loanAsset = new LoanAsset();
                    CbsSchemeAssetDetailsPKTO cbsSchemeAssetDetailsPKTO = assetDetails.getCbsSchemeAssetDetailsPKTO();

                    if (cbsSchemeAssetDetailsPKTO.getDpdCounter() == null || cbsSchemeAssetDetailsPKTO.getDpdCounter().equalsIgnoreCase("")) {
                        loanAsset.setTbdPDCounter("");
                    } else {
                        loanAsset.setTbdPDCounter(cbsSchemeAssetDetailsPKTO.getDpdCounter());
                    }
                    if (assetDetails.getMainClass() == null || assetDetails.getMainClass().equalsIgnoreCase("")) {
                        loanAsset.setTbMainClass("");
                    } else {
                        loanAsset.setTbMainClass(assetDetails.getMainClass());
                    }
                    if (assetDetails.getSubClass() == null || assetDetails.getSubClass().equalsIgnoreCase("")) {
                        loanAsset.setTbSubClass("");
                    } else {
                        loanAsset.setTbSubClass(assetDetails.getSubClass());
                    }
                    if (assetDetails.getIntAccre() == null || assetDetails.getIntAccre().equalsIgnoreCase("")) {
                        loanAsset.setTbIntAccre("");
                    } else {
                        loanAsset.setTbIntAccre(assetDetails.getIntAccre());
                    }
                    if (assetDetails.getIntFlagBk() == null || assetDetails.getIntFlagBk().equalsIgnoreCase("")) {
                        loanAsset.setTbIntFlagBk("");
                    } else {
                        loanAsset.setTbIntFlagBk(assetDetails.getIntFlagBk());
                    }
                    if (assetDetails.getIntFlagColl() == null || assetDetails.getIntFlagColl().equalsIgnoreCase("")) {
                        loanAsset.setTbIntFlagColl("");
                    } else {
                        loanAsset.setTbIntFlagColl(assetDetails.getIntFlagColl());
                    }
                    if (assetDetails.getPdFlag() == null || assetDetails.getPdFlag().equalsIgnoreCase("")) {
                        loanAsset.setTbPDFlag("");
                    } else {
                        loanAsset.setTbPDFlag(assetDetails.getPdFlag());
                    }
                    if (assetDetails.getIntSuspPlaceholder() == null || assetDetails.getIntSuspPlaceholder().equalsIgnoreCase("")) {
                        loanAsset.setTbIntSuspPlaceHolder("");
                    } else {
                        loanAsset.setTbIntSuspPlaceHolder(assetDetails.getIntSuspPlaceholder());
                    }
                    if (assetDetails.getProvisionDr() == null || assetDetails.getProvisionDr().equalsIgnoreCase(intAccre)) {
                        loanAsset.setTbprovisionDR("");
                    } else {
                        loanAsset.setTbprovisionDR(assetDetails.getProvisionDr());
                    }
                    if (assetDetails.getPlaceholdersCr() == null || assetDetails.getPlaceholdersCr().equalsIgnoreCase("")) {
                        loanAsset.setTbPlaceHoldersCr("");
                    } else {
                        loanAsset.setTbPlaceHoldersCr(assetDetails.getPlaceholdersCr());
                    }
                    if (assetDetails.getDelFlag() == null || assetDetails.getDelFlag().equalsIgnoreCase("")) {
                        loanAsset.setTbdelFlag("");
                    } else {
                        loanAsset.setTbdelFlag(assetDetails.getDelFlag());
                    }
                    loanAsset.setCounterSaveUpdate("G");
                    asset.add(loanAsset);
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

    public void refreshADForm() {
        this.setdPDCounter("");
        this.setMainClass("");
        this.setSubClass("");
        this.setIntAccre("0");
        this.setIntFlagBk("0");
        this.setIntFlagColl("0");
        this.setpDFlag("0");
        this.setIntSuspPlaceHolder("");
        this.setProvisionDR("");
        this.setPlaceHoldersCr("");
        this.setDelFlag("0");
    }

    public void enableADForm() {
        this.adFlag = false;
    }

    public void disableADForm() {
        this.adFlag = true;
    }
}
