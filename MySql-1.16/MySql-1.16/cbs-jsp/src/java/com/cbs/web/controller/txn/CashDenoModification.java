/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.TxnDetailBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class CashDenoModification extends BaseBean {

    public CashDenoModification() {
        try {
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            this.setMsg("");
            this.setErrorMessage("");
            this.setDisableDenoNo(false);
            validator = new Validator();
            this.setCurrencyAmount("0.00");
            denominationTable = new LinkedList<DDSDenominationGrid>();

//            oldNewFlgList = new ArrayList<SelectItem>();
//            oldNewFlgList.add(new SelectItem("O", "OLD"));
//            oldNewFlgList.add(new SelectItem("N", "NEW"));
            newLst = aitr.getReferenceCode("501");
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }

    }

    public void getCashTxn() {
        try {
            if (this.acno.equalsIgnoreCase("") || this.acno == null || this.acno.equalsIgnoreCase("null")) {
                throw new ApplicationException("Please Enter 12 Digit Account Number.");
            }
            if (!this.acno.equalsIgnoreCase("") && this.acno.length() < 12) {
                throw new ApplicationException("Please Enter 12 Digit Account Number.");
            }
            List rsList = txnRemote.getCashTxnDetals(acno, getOrgnBrCode());
            if (rsList.isEmpty()) {
                throw new ApplicationException("Transaction does not exist for " + acno);
            }
            TxnDetailBean txnPojo;
            cashTxnList = new ArrayList<TxnDetailBean>();

            for (int i = 0; i < rsList.size(); i++) {
                Vector vect = (Vector) rsList.get(i);
                txnPojo = new TxnDetailBean();
                txnPojo.setTranId(i+1); // Use for Serial Number

                txnPojo.setAcNo(vect.get(0).toString());
                txnPojo.setScreenFlag(vect.get(1).toString());//Use for customer Name
                txnPojo.setDt(vect.get(2).toString());

                txnPojo.setCrAmt(Double.parseDouble(vect.get(4).toString()));
                txnPojo.setDrAmt(Double.parseDouble(vect.get(3).toString()));
                txnPojo.setTy(vect.get(5).toString());
                txnPojo.setTyDesc(vect.get(5).toString().equals("1") ? "Payment" : "Received");

                txnPojo.setRecNo(Float.parseFloat(vect.get(6).toString()));
                txnPojo.setEnterBy(vect.get(7).toString());
                txnPojo.setAuthBy(vect.get(8).toString());
                int index = vect.get(9).toString().indexOf("~`");
                if (index > -1) {
                    txnPojo.setDetails(vect.get(9).toString().substring(0, index));
                } else {
                    txnPojo.setDetails(vect.get(9).toString().toString());
                }
                txnPojo.setDetails(denoNo);
                cashTxnList.add(txnPojo);
            }
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void getDenominationDetails() {
        try {
            if (currentItem.getTy().equals("1")) {
                setTotalAmount(formatter.format(currentItem.getDrAmt()));
            } else {
                setTotalAmount(formatter.format(currentItem.getCrAmt()));
            }
            List denoList = txnRemote.getDenominationDetails(acno, currentItem.getRecNo(), currentItem.getDt(), getOrgnBrCode(), Integer.parseInt(currentItem.getTy()));
            if (denoList.isEmpty()) {
                throw new ApplicationException("Denomination does not exist for this transaction");
            }
            denominationTable = new LinkedList<DDSDenominationGrid>();
            DDSDenominationGrid ddg;
            double tmpCurAmt = 0;
            for (int i = 0; i < denoList.size(); i++) {
                Vector vect = (Vector) denoList.get(i);
                ddg = new DDSDenominationGrid();
                ddg.setSno(i+1);

                ddg.setDenoValue(Double.parseDouble(vect.get(0).toString()));
                ddg.setDenoNo(Integer.parseInt(vect.get(1).toString()));
                ddg.setDenoAmount(Double.parseDouble(vect.get(0).toString()) * Integer.parseInt(vect.get(1).toString()));
//                ddg.setFlag(vect.get(2).toString());
                ddg.setFlag("N");
                tmpCurAmt = tmpCurAmt + ddg.getDenoAmount();
                denominationTable.add(ddg);
            }
            currencyAmount = formatter.format(tmpCurAmt);
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void updateDenominationDetails() {
        try {
            if (this.acno.equalsIgnoreCase("") || this.acno == null || this.acno.equalsIgnoreCase("null")) {
                throw new ApplicationException("Please Enter 12 Digit Account Number.");
            }
            if (!this.acno.equalsIgnoreCase("") && this.acno.length() < 12) {
                throw new ApplicationException("Please Enter 12 Digit Account Number.");
            }
            if (denominationTable.isEmpty()) {
                throw new ApplicationException("Please fill the Return Cash Denomination Detail");
            }
            if (Double.parseDouble(currencyAmount) != Double.parseDouble(totalAmount)) {
                throw new ApplicationException("Currency amount must be equal to total received amount !!");
            }
            String rs = txnRemote.updateDenominationDetails(denominationTable, acno, currentItem.getRecNo(), currentItem.getDt(), getOrgnBrCode(),
                    Integer.parseInt(currentItem.getTy()), getUserName());
            
            if(rs.equalsIgnoreCase("True")) setMsg("Denomination Detail successfully updated");
            setErrorMessage("");
            setAcno("");
            setTotalAmount("0.00");
            setCurrencyAmount("0.00");
            setDenoNo("");
            setDenoValue("");
            denominationTable = new LinkedList<DDSDenominationGrid>();
            cashTxnList = new ArrayList<TxnDetailBean>();
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void refresh(){
        setMsg("");
        setErrorMessage("");
        setAcno("");
        setTotalAmount("0.00");
        setCurrencyAmount("0.00");
        setDenoNo("");
        setDenoValue("");
        denominationTable = new LinkedList<DDSDenominationGrid>();
        cashTxnList = new ArrayList<TxnDetailBean>();
    }
     public String exit() {
        refresh();
        return "case1";
    }
    
    public void setDenominationRowValues() {
        try {
            denoValue = String.valueOf(currentDenominationItem.getDenoValue());
            denoNo = String.valueOf(currentDenominationItem.getDenoNo());
//            setSelectOldNewFlg(currentDenominationItem.getFlag());
            denominationTable.remove(currentDenominationItem);
            double i = 0.0d;
            if (denominationTable != null) {
                for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                    i += dDSDenominationGrid.getDenoAmount();
                }
                setCurrencyAmount(formatter.format(i));
            }
            if (Double.parseDouble(currencyAmount) == Double.parseDouble(totalAmount)) {
                setDisableDenoNo(true);
            } else {
                setDisableDenoNo(false);
            }
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    private boolean validate() throws ApplicationException {
        if (!validator.validateDoublePositive(denoValue)) {
            throw new ApplicationException("Please enter only numeric values in denomination !!!");
        }
        if (!validator.validateInteger(denoNo)) {
            throw new ApplicationException("Please enter only numeric values in number of notes / coins !!!");
        }
        if (Integer.parseInt(this.denoNo) == 0) {
            throw new ApplicationException("Zero is not allowed in number of notes / coins !!!");
        }

//        if (currentItem.getTy().equalsIgnoreCase("0")) {
//            if ((Double.parseDouble(denoValue) == 2000) && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                throw new ApplicationException("2000 notes can't be old !!!");
//            }
//            if (!(Double.parseDouble(denoValue) == 2000 || Double.parseDouble(denoValue) == 500) && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                throw new ApplicationException(denoValue + " notes can't be New !!!");
//            }
//        } else if (currentItem.getTy().equalsIgnoreCase("1")) {
//            if ((Double.parseDouble(denoValue) == 1000 || Double.parseDouble(denoValue) == 500) && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                throw new ApplicationException("Old 500/1000 notes can't withdraw !!!");
//            }
//            if (!(Double.parseDouble(denoValue) == 2000 || Double.parseDouble(denoValue) == 1000 || Double.parseDouble(denoValue) == 500) && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                throw new ApplicationException(denoValue + "notes can't be new !!!");
//            }
//        } else {
//            throw new ApplicationException("Please Check Transaction Type !!!");
//        }
        
        int k = 0;
        for (int i = 0; i < newLst.size(); i++) {
            Vector element = (Vector) newLst.get(i);
            if (Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)) {
                 k = 1;
            }
        }
        if(k != 1){
            return false;
        }
        
        return true;
    }

//    public boolean checkCurrency() {
//        try {
//            Double value = Double.parseDouble(denoValue);
//            if (value == 0.5) {
//                return true;
//            }
//            if (value.intValue() != 0 && CbsUtil.round(value - value.intValue(), 2) > 0.0) {
//                return false;
//            }
//            switch (value.intValue()) {
//                case 1:
//                    return true;
//                case 2:
//                    return true;
//                case 5:
//                    return true;
//                case 10:
//                    return true;
//                case 20:
//                    return true;
//                case 50:
//                    return true;
//                case 100:
//                    return true;
//                case 500:
//                    return true;
//                case 1000:
//                    return true;
//                case 2000:
//                    return true;
//                default:
//                    return false;
//            }
//        } catch (Exception e) {
//            setErrorMessage(e.getLocalizedMessage());
//        }
//        return false;
//    }
    public boolean checkCurrency() {
        try {
            Double value = Double.parseDouble(denoValue);
            if (value == 0.5) {
                return true;
            }
            if (value.intValue() != 0 && CbsUtil.round(value - value.intValue(), 2) > 0.0) {
                return false;
            }
            for (int i = 0; i < newLst.size(); i++) {
                Vector element = (Vector) newLst.get(i);
                if(Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)){
                    return true;
                }
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
        return false;
    }

    class DenominationComparatorDesc implements Comparator<DDSDenominationGrid> {

        public int compare(DDSDenominationGrid one, DDSDenominationGrid two) {
            return two.getDenoValue().compareTo(one.getDenoValue());
        }
    }

    public void onBlurDenominationNo() {
        this.setErrorMessage("");
        try {
            validate();
            if (!checkCurrency()) {
                throw new ApplicationException("Currency " + denoValue + " is not allowed !!");
            }
            setErrorMessage("");
            DenominationComparatorDesc denominationComparator = new DenominationComparatorDesc();
            DDSDenominationGrid currentDenominationGrid = new DDSDenominationGrid();
            if (denominationTable != null) {
                currentDenominationGrid.setSno(denominationTable.size() + 1);
                currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                
                currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                currentDenominationGrid.setFlag("N");
                
                if (Collections.binarySearch(denominationTable, currentDenominationGrid, denominationComparator) >= 0) {
                    throw new ApplicationException("You have already entered denomination for " + denoValue + " , So you can only update it by selecting from grid !!!");
                }
                denominationTable.add(currentDenominationGrid);

            } else {
                denominationTable = new LinkedList<DDSDenominationGrid>();
                currentDenominationGrid.setSno(1);
                currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                currentDenominationGrid.setFlag("N");
                denominationTable.add(currentDenominationGrid);
            }

            Collections.sort(denominationTable, denominationComparator);
            double i = 0.0d;
            if (denominationTable != null) {
                for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                    i += dDSDenominationGrid.getDenoAmount();
                }
                setCurrencyAmount(formatter.format(i));
            }
            if (Double.parseDouble(currencyAmount) == Double.parseDouble(totalAmount)) {
                this.setDisableDenoNo(true);
            } else if (Double.parseDouble(currencyAmount) > Double.parseDouble(totalAmount)) {
                denominationTable.remove(currentDenominationGrid);
                calculateCurrencyAmount();
                setErrorMessage("Currency amount is exceeding total receivable amount !!");
            }
            setDenoValue("");
            setDenoNo("");
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    private void calculateCurrencyAmount() {
        try {
            double i = 0;
            if (denominationTable != null) {
                for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                    i += dDSDenominationGrid.getDenoAmount();
                }
                setCurrencyAmount(formatter.format(i));
            }
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    private String acno;
    private String msg;
    private TxnDetailBean currentItem;
    private String denoValue;
    private boolean disableDenoNo;
    private String denoNo;
    private List<DDSDenominationGrid> denominationTable;
    private DDSDenominationGrid currentDenominationItem;
    private String totalAmount;
    private String currencyAmount;
    private String errorMessage;
    NumberFormat formatter = new DecimalFormat("#0.00");
    private Validator validator;
    //private boolean closeDeno;
    private String selectOldNewFlg;
   // private List<SelectItem> oldNewFlgList;
   // private String tyDenoValue;
    private List<TxnDetailBean> cashTxnList;
    private final String jndiHomeNameTxn = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private List newLst;
    AdvancesInformationTrackingRemote aitr;

    public TxnDetailBean getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TxnDetailBean currentItem) {
        this.currentItem = currentItem;
    }

    public List<TxnDetailBean> getCashTxnList() {
        return cashTxnList;
    }

    public void setCashTxnList(List<TxnDetailBean> cashTxnList) {
        this.cashTxnList = cashTxnList;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDenoValue() {
        return denoValue;
    }

    public void setDenoValue(String denoValue) {
        this.denoValue = denoValue;
    }

    public boolean isDisableDenoNo() {
        return disableDenoNo;
    }

    public void setDisableDenoNo(boolean disableDenoNo) {
        this.disableDenoNo = disableDenoNo;
    }

    public String getDenoNo() {
        return denoNo;
    }

    public void setDenoNo(String denoNo) {
        this.denoNo = denoNo;
    }

    public List<DDSDenominationGrid> getDenominationTable() {
        return denominationTable;
    }

    public void setDenominationTable(List<DDSDenominationGrid> denominationTable) {
        this.denominationTable = denominationTable;
    }

    public DDSDenominationGrid getCurrentDenominationItem() {
        return currentDenominationItem;
    }

    public void setCurrentDenominationItem(DDSDenominationGrid currentDenominationItem) {
        this.currentDenominationItem = currentDenominationItem;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSelectOldNewFlg() {
        return selectOldNewFlg;
    }

    public void setSelectOldNewFlg(String selectOldNewFlg) {
        this.selectOldNewFlg = selectOldNewFlg;
    }

    //    public List<SelectItem> getOldNewFlgList() {
    //        return oldNewFlgList;
    //    }
    //
    //    public void setOldNewFlgList(List<SelectItem> oldNewFlgList) {
    //        this.oldNewFlgList = oldNewFlgList;
    //    }
    public List getNewLst() {
        return newLst;
    }

    public void setNewLst(List newLst) {
        this.newLst = newLst;
    }
    
}
