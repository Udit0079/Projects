/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeLedgerFolioDetailsCurrencyWisePKTO;
import com.cbs.dto.loan.CbsSchemeLedgerFolioDetailsCurrencyWiseTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.TblLedgerFolio;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class LFDCW {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for lfdcw.jsp file
    private String startAmount;
    private String endAmount;
    private int freeFolios;
    private String delFlagledger;
    private int currentRow2;
    private int countLedger1;
    private int countLedger2;
    private int schemeLedger = 0;
    boolean disableStartAmount;
    boolean disableendAmount;
    private boolean lfdcwFlag;
    private TblLedgerFolio currentItem2 = new TblLedgerFolio();
    private List<TblLedgerFolio> ledger;
    private List<TblLedgerFolio> ledgertmp;
    private List<SelectItem> ddLfdcwTrnRefNo;

    //Getter-Setter for lfdcw.jsp file
    public List<SelectItem> getDdLfdcwTrnRefNo() {
        return ddLfdcwTrnRefNo;
    }

    public void setDdLfdcwTrnRefNo(List<SelectItem> ddLfdcwTrnRefNo) {
        this.ddLfdcwTrnRefNo = ddLfdcwTrnRefNo;
    }

    public boolean isLfdcwFlag() {
        return lfdcwFlag;
    }

    public void setLfdcwFlag(boolean lfdcwFlag) {
        this.lfdcwFlag = lfdcwFlag;
    }

    public int getCountLedger1() {
        return countLedger1;
    }

    public void setCountLedger1(int countLedger1) {
        this.countLedger1 = countLedger1;
    }

    public int getCountLedger2() {
        return countLedger2;
    }

    public void setCountLedger2(int countLedger2) {
        this.countLedger2 = countLedger2;
    }

    public TblLedgerFolio getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(TblLedgerFolio currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public String getDelFlagledger() {
        return delFlagledger;
    }

    public void setDelFlagledger(String delFlagledger) {
        this.delFlagledger = delFlagledger;
    }

    public boolean isDisableStartAmount() {
        return disableStartAmount;
    }

    public void setDisableStartAmount(boolean disableStartAmount) {
        this.disableStartAmount = disableStartAmount;
    }

    public boolean isDisableendAmount() {
        return disableendAmount;
    }

    public void setDisableendAmount(boolean disableendAmount) {
        this.disableendAmount = disableendAmount;
    }

    public String getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(String endAmount) {
        this.endAmount = endAmount;
    }

    public int getFreeFolios() {
        return freeFolios;
    }

    public void setFreeFolios(int freeFolios) {
        this.freeFolios = freeFolios;
    }

    public List<TblLedgerFolio> getLedger() {
        return ledger;
    }

    public void setLedger(List<TblLedgerFolio> ledger) {
        this.ledger = ledger;
    }

    public List<TblLedgerFolio> getLedgertmp() {
        return ledgertmp;
    }

    public void setLedgertmp(List<TblLedgerFolio> ledgertmp) {
        this.ledgertmp = ledgertmp;
    }

    public int getSchemeLedger() {
        return schemeLedger;
    }

    public void setSchemeLedger(int schemeLedger) {
        this.schemeLedger = schemeLedger;
    }

    public String getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(String startAmount) {
        this.startAmount = startAmount;
    }

    /** Creates a new instance of LFDCW */
    public LFDCW() {
       
        try {
            ledger = new ArrayList<TblLedgerFolio>();
            ledgertmp = new ArrayList<TblLedgerFolio>();

            ddLfdcwTrnRefNo = new ArrayList<SelectItem>();
            ddLfdcwTrnRefNo.add(new SelectItem("0", ""));
            ddLfdcwTrnRefNo.add(new SelectItem("Y", "Yes"));
            ddLfdcwTrnRefNo.add(new SelectItem("N", "No"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Functionality for lfdcw.jsp file
    public void selectLfdcwDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeLedgerFolioDetailsCurrencyWiseTO> listTOs = schemeMasterManagementDelegate.getLfdcwDetails(schemeMaster.getSchemeCode());
            if (listTOs.size() > 0) {
                for (CbsSchemeLedgerFolioDetailsCurrencyWiseTO singleTO : listTOs) {
                    CbsSchemeLedgerFolioDetailsCurrencyWisePKTO pkto = singleTO.getCbsSchemeLedgerFolioDetailsCurrencyWisePKTO();
                    TblLedgerFolio tblObj = new TblLedgerFolio();
                    tblObj.setTblStartAmount(pkto.getStartAmount().toString());
                    tblObj.setTblEndAmount(pkto.getEndAmount().toString());
                    tblObj.setTblFreeFolios(singleTO.getFreeFolios());
                    tblObj.setTblDelFlagledger(singleTO.getDelFlag());
                    tblObj.setCounterSaveUpdateLedger("G");
                    ledger.add(tblObj);
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

    public void setTableLedgerFolio() {
        schemeMaster.setMessage("");
        this.setDisableStartAmount(false);
        this.setDisableendAmount(false);
        List<TblLedgerFolio> ledgerTmp = ledger;
        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (Float.parseFloat(startAmount) > Float.parseFloat(endAmount)) {
                schemeMaster.setMessage("End Amount not Less Than Start Amount.");
                return;
            }
            if (schemeLedger == 0) {
                for (int i = 0; i < ledgerTmp.size(); i++) {
                    String tmpCompare = ledgerTmp.get(i).getTblStartAmount();
                    String tmpCompare1 = ledgerTmp.get(i).getTblEndAmount();
                    if (Float.parseFloat(tmpCompare) == Float.parseFloat(startAmount) || Float.parseFloat(tmpCompare1) == Float.parseFloat(endAmount)) {
                        schemeMaster.setMessage("StartAmount,EndAmount Already Exists In The Table. Please Select other amount.");
                        return;
                    }
                }
            }
        }
        TblLedgerFolio stk1 = new TblLedgerFolio();
        stk1.setTblStartAmount(startAmount);
        stk1.setTblEndAmount(endAmount);
        stk1.setTblFreeFolios(freeFolios);
        stk1.setTblDelFlagledger(delFlagledger);

        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            stk1.setCounterSaveUpdateLedger("S");
            refreshLfdcwForm();
        }

        ledger.add(stk1);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (schemeLedger != 1) {
                for (int i = 0; i < ledgerTmp.size(); i++) {
                    String tmpCompareleg = ledgerTmp.get(i).getTblStartAmount();
                    String tmpCompare1leg = ledgerTmp.get(i).getTblEndAmount();
                    if (Float.parseFloat(tmpCompareleg) != Float.parseFloat(startAmount) && Float.parseFloat(tmpCompare1leg) != Float.parseFloat(endAmount)) {
                        stk1.setCounterSaveUpdateLedger("S");
                        ledgertmp.add(stk1);

                        refreshLfdcwForm();
                        return;
                    }
                }
            }

            if (currentItem2.getTblStartAmount() == null || currentItem2.getTblStartAmount().equalsIgnoreCase("")) {
                stk1.setCounterSaveUpdateLedger("S");
                ledgertmp.add(stk1);
                refreshLfdcwForm();
                return;

            } else if (Float.parseFloat(currentItem2.getTblStartAmount()) == Float.parseFloat(startAmount) && Float.parseFloat(currentItem2.getTblEndAmount()) == Float.parseFloat(endAmount)) {
                if (!currentItem2.getTblDelFlagledger().equalsIgnoreCase(delFlagledger)) {
                    stk1.setCounterSaveUpdateLedger("U");
                    refreshLfdcwForm();
                    ledgertmp.add(stk1);
                }
                schemeLedger = 0;
            }
        }
    }

    public void selectLedgerFolio() {
        this.setDisableStartAmount(true);
        this.setDisableendAmount(true);
        schemeMaster.setMessage("");
        schemeLedger = 1;
        if (Float.parseFloat(startAmount) != 0) {
            if (Float.parseFloat(startAmount) != Float.parseFloat(currentItem2.getTblStartAmount()) || Float.parseFloat(endAmount) != Float.parseFloat(currentItem2.getTblEndAmount()) || freeFolios != currentItem2.getTblFreeFolios() || !delFlagledger.equalsIgnoreCase(currentItem2.getTblDelFlagledger())) {
                countLedger2 = countLedger1;
                countLedger1 = countLedger1 + 1;
                if (countLedger2 != countLedger1) {

                    setTableLedgerFolio();
                }
            } else {
                countLedger1 = 0;
            }
        }

        setStartAmount(currentItem2.getTblStartAmount());
        setEndAmount(currentItem2.getTblEndAmount());
        setFreeFolios(currentItem2.getTblFreeFolios());
        setDelFlagledger(currentItem2.getTblDelFlagledger());
        ledger.remove(currentRow2);
    }

    public void refreshLfdcwForm() {
        this.setStartAmount("0.0");
        this.setEndAmount("0.0");
        this.setFreeFolios(0);
        this.setDelFlagledger("0");
    }

    public void enableLfdcwForm() {
        this.lfdcwFlag = false;
    }

    public void disableLfdcwForm() {
        this.lfdcwFlag = true;
    }
}
