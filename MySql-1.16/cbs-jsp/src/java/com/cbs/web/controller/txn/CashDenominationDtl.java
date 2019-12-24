/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Admin
 */
public class CashDenominationDtl extends BaseBean {

    private boolean denominationRender;
    private String denoValue;
    private boolean disableDenoNo;
    private String denoNo;
    private List<DDSDenominationGrid> denominationTable;
    private DDSDenominationGrid currentDenominationItem;
    private int currentIDenominationRow;
    private String totalAmount;
    private String currencyAmount;
    private String errorMessage1;
    NumberFormat formatter = new DecimalFormat("#0.00");
    private Validator validator;
    private boolean closeDeno;
    private String selectOldNewFlg;
    //private List<SelectItem> oldNewFlgList;
    private String tyDenoValue;
    private String acDenoNat;
    private String tyFlg;
    private List<SelectItem> tyList;
    private String tyCaption;
    private List oldLst;
    private List newLst;
    AdvancesInformationTrackingRemote aitr;

    public boolean isDenominationRender() {
        return denominationRender;
    }

    public void setDenominationRender(boolean denominationRender) {
        this.denominationRender = denominationRender;
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

    public int getCurrentIDenominationRow() {
        return currentIDenominationRow;
    }

    public void setCurrentIDenominationRow(int currentIDenominationRow) {
        this.currentIDenominationRow = currentIDenominationRow;
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

    public String getErrorMessage1() {
        return errorMessage1;
    }

    public void setErrorMessage1(String errorMessage1) {
        this.errorMessage1 = errorMessage1;
    }

    public boolean isCloseDeno() {
        return closeDeno;
    }

    public void setCloseDeno(boolean closeDeno) {
        this.closeDeno = closeDeno;
    }

    public String getSelectOldNewFlg() {
        return selectOldNewFlg;
    }

    public void setSelectOldNewFlg(String selectOldNewFlg) {
        this.selectOldNewFlg = selectOldNewFlg;
    }
//
//    public List<SelectItem> getOldNewFlgList() {
//        return oldNewFlgList;
//    }
//
//    public void setOldNewFlgList(List<SelectItem> oldNewFlgList) {
//        this.oldNewFlgList = oldNewFlgList;
//    }

    public String getTyDenoValue() {
        return tyDenoValue;
    }

    public void setTyDenoValue(String tyDenoValue) {
        this.tyDenoValue = tyDenoValue;
    }

    public String getAcDenoNat() {
        return acDenoNat;
    }

    public void setAcDenoNat(String acDenoNat) {
        this.acDenoNat = acDenoNat;
    }

    public String getTyFlg() {
        return tyFlg;
    }

    public void setTyFlg(String tyFlg) {
        this.tyFlg = tyFlg;
    }

    public List<SelectItem> getTyList() {
        return tyList;
    }

    public void setTyList(List<SelectItem> tyList) {
        this.tyList = tyList;
    }

    public String getTyCaption() {
        return tyCaption;
    }

    public void setTyCaption(String tyCaption) {
        this.tyCaption = tyCaption;
    }

    public List getOldLst() {
        return oldLst;
    }

    public void setOldLst(List oldLst) {
        this.oldLst = oldLst;
    }

    public List getNewLst() {
        return newLst;
    }

    public void setNewLst(List newLst) {
        this.newLst = newLst;
    }   
    
    public CashDenominationDtl() {
        try {
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            this.setErrorMessage1("");
            this.setDisableDenoNo(false);
            validator = new Validator();
            //this.setTotalAmount(getHttpRequest().getParameter("cashAmt").toString());
            this.setCurrencyAmount("0.00");
            denominationTable = new LinkedList<DDSDenominationGrid>();
            this.setDenominationRender(true);

//            oldNewFlgList = new ArrayList<SelectItem>();
//            oldNewFlgList.add(new SelectItem("O", "OLD"));
//            oldNewFlgList.add(new SelectItem("N", "NEW"));
            
            oldLst = aitr.getReferenceCode("500");
            newLst = aitr.getReferenceCode("501");
        } catch (Exception e) {
            setErrorMessage1(e.getLocalizedMessage());
        }

    }

    public void setDenominationRowValues() {
        try {
            denoValue = String.valueOf(currentDenominationItem.getDenoValue());
            denoNo = String.valueOf(currentDenominationItem.getDenoNo());
            //setSelectOldNewFlg(currentDenominationItem.getFlag());
            setSelectOldNewFlg("N");
            setTyFlg(currentDenominationItem.getTy());
            denominationTable.remove(currentDenominationItem);
            double i = 0.0d;
            if (denominationTable != null) {
                for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                    if (tyDenoValue.equalsIgnoreCase("0")) {
                        if (dDSDenominationGrid.getTy().equalsIgnoreCase("0")) {
                            i += dDSDenominationGrid.getDenoAmount();
                        } else if (dDSDenominationGrid.getTy().equalsIgnoreCase("3")) {
                            i -= dDSDenominationGrid.getDenoAmount();
                        }
                    } else {
                        if (dDSDenominationGrid.getTy().equalsIgnoreCase("4")) {
                            i -= dDSDenominationGrid.getDenoAmount();
                        } else if (dDSDenominationGrid.getTy().equalsIgnoreCase("1")) {
                            i += dDSDenominationGrid.getDenoAmount();
                        }
                    }
                }
                setCurrencyAmount(formatter.format(i));
            }
            if (Double.parseDouble(currencyAmount) == Double.parseDouble(totalAmount)) {
                setDisableDenoNo(true);
            } else {
                setDisableDenoNo(false);
            }
        } catch (Exception e) {
            setErrorMessage1(e.getLocalizedMessage());
        }
    }

    private boolean validate() {
        try {
            if (!validator.validateDoublePositive(denoValue)) {
                this.setErrorMessage1("Please enter only numeric values in denomination !!!");
                return false;
            }
            if (!validator.validateInteger(denoNo)) {
                this.setErrorMessage1("Please enter only numeric values in number of notes / coins !!!");
                return false;
            }
            if (Integer.parseInt(this.denoNo) == 0) {
                this.setErrorMessage1("Zero is not allowed in number of notes / coins !!!");
                return false;
            }

            if (!acDenoNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                if(this.getSelectOldNewFlg().equalsIgnoreCase("O")){
//                    for (int i = 0; i < oldLst.size(); i++) {
//                        Vector element = (Vector) oldLst.get(i);
//                        if(Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)){
//                            return true;
//                        }
//                    }
//                    this.setErrorMessage1("This denomination in old can't be accepted");
//                    return false;
//                }
            
//                if(this.getSelectOldNewFlg().equalsIgnoreCase("N")){
                    for (int i = 0; i < newLst.size(); i++) {
                        Vector element = (Vector) newLst.get(i);
                        if(Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)){
                            return true;
                        }
                    }
                    this.setErrorMessage1("This denomination can't be accepted");
                    return false;
//                }
            } 
            
//            if (tyDenoValue.equalsIgnoreCase("0")) {
//                if (tyFlg.equalsIgnoreCase("0")) {
//                    if ((denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0"))
//                            && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                        this.setErrorMessage1("2000 notes can't be old !!!");
//                        return false;
//                    }
//                    if (!(denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0")
//                            || denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.00") || denoValue.equalsIgnoreCase("500.0"))
//                            && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                            this.setErrorMessage1(denoValue + " notes can't be New !!!");
//                        return false;
//                    }
//                } else if (tyFlg.equalsIgnoreCase("3")) {
//                    if (!acDenoNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                        if ((denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.0") || denoValue.equalsIgnoreCase("500.00")
//                                || denoValue.equalsIgnoreCase("1000") || denoValue.equalsIgnoreCase("1000.00") || denoValue.equalsIgnoreCase("1000.0"))
//                                && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                            this.setErrorMessage1("Old 500/1000 notes can't withdraw !!!");
//                            return false;
//                        }
//                        if (!(denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.00") || denoValue.equalsIgnoreCase("500.0")
//                                || denoValue.equalsIgnoreCase("1000") || denoValue.equalsIgnoreCase("1000.00") || denoValue.equalsIgnoreCase("1000.0")
//                                || denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0"))
//                                && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                            this.setErrorMessage1(denoValue + "notes can't be new !!!");
//                            return false;
//                        }
//                    }
//                } else {
//                    this.setErrorMessage1("Please Check Transaction Type !!!");
//                    return false;
//                }
//            }else{
//                if (tyFlg.equalsIgnoreCase("1")) {
//                    if ((denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0"))
//                            && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                        this.setErrorMessage1("2000 notes can't be old !!!");
//                        return false;
//                    }
//                    if (!(denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0")
//                            || denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.00") || denoValue.equalsIgnoreCase("500.0"))
//                            && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                            this.setErrorMessage1(denoValue + " notes can't be New !!!");
//                        return false;
//                    }
//                } else if (tyFlg.equalsIgnoreCase("4")) {
//                    if (!acDenoNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                        if ((denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.0") || denoValue.equalsIgnoreCase("500.00")
//                                || denoValue.equalsIgnoreCase("1000") || denoValue.equalsIgnoreCase("1000.00") || denoValue.equalsIgnoreCase("1000.0"))
//                                && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                            this.setErrorMessage1("Old 500/1000 notes can't withdraw !!!");
//                            return false;
//                        }
//                        if (!(denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.00") || denoValue.equalsIgnoreCase("500.0")
//                                || denoValue.equalsIgnoreCase("1000") || denoValue.equalsIgnoreCase("1000.00") || denoValue.equalsIgnoreCase("1000.0")
//                                || denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0"))
//                                && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                            this.setErrorMessage1(denoValue + "notes can't be new !!!");
//                            return false;
//                        }
//                    }
//                } else {
//                    this.setErrorMessage1("Please Check Transaction Type !!!");
//                    return false;
//                }
//            }            
            
        } catch (Exception e) {
            setErrorMessage1(e.getLocalizedMessage());
        }
        return true;
    }

    public boolean checkCurrency() {
        try {
            Double value = Double.parseDouble(denoValue);
            if (value == 0.5) {
                return true;
            }
            if (value.intValue() != 0 && CbsUtil.round(value - value.intValue(), 2) > 0.0) {
                return false;
            }
            
//            if(this.getSelectOldNewFlg().equalsIgnoreCase("O")){
//                for (int i = 0; i < oldLst.size(); i++) {
//                    Vector element = (Vector) oldLst.get(i);
//                    if(Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)){
//                        return true;
//                    }
//                }
//                return false;
//            }
            
//            if(this.getSelectOldNewFlg().equalsIgnoreCase("N")){
                for (int i = 0; i < newLst.size(); i++) {
                    Vector element = (Vector) newLst.get(i);
                    if(Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)){
                        return true;
                    }
                }
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
//                case 200:
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
        } catch (Exception e) {
            setErrorMessage1(e.getLocalizedMessage());
        }
        return false;
    }

    class DenoValueCompare implements Comparator<DDSDenominationGrid> {

        @Override
        public int compare(DDSDenominationGrid o1, DDSDenominationGrid o2) {
            if (o1.denoValue.compareTo(o2.denoValue) == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    class DenoValueFlag implements Comparator<DDSDenominationGrid> {

        @Override
        public int compare(DDSDenominationGrid o1, DDSDenominationGrid o2) {
            if (o1.getFlag().equals(o2.getFlag())) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public void onBlurDenominationNo() {
        this.setErrorMessage1("");
        try {
            if (validate()) {
                if (checkCurrency()) {
                    setErrorMessage1("");                    
                    DDSDenominationGrid currentDenominationGrid = new DDSDenominationGrid();
                    if (denominationTable != null) {
                        currentDenominationGrid.setSno(denominationTable.size() + 1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                        currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                        currentDenominationGrid.setFlag("N");
                        currentDenominationGrid.setTy(this.getTyFlg());
                        if(tyDenoValue.equalsIgnoreCase("0")){
                            if (this.getTyFlg().equalsIgnoreCase("0")) {
                                currentDenominationGrid.setTySh("Receive");
                            } else {
                                currentDenominationGrid.setTySh("Return");
                            }
                        } else{
                            if (this.getTyFlg().equalsIgnoreCase("1")) {
                                currentDenominationGrid.setTySh("Receive");
                            } else {
                                currentDenominationGrid.setTySh("Return");
                            }
                        }
                        ComparatorChain chain = new ComparatorChain();
                        chain.addComparator(new DenoValueCompare());
                        chain.addComparator(new DenoValueFlag());
                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, chain) >= 0) {
                            this.setErrorMessage1("You have already entered denomination for " + denoValue + ", So you can only update it by selecting from grid !!!");

                        } else {
                            denominationTable.add(currentDenominationGrid);
                        }
                    } else {
                        denominationTable = new LinkedList<DDSDenominationGrid>();
                        currentDenominationGrid.setSno(1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                        currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                        currentDenominationGrid.setFlag("N");
                        currentDenominationGrid.setTy(this.getTyFlg());
                        if(tyDenoValue.equalsIgnoreCase("0")){
                            if (this.getTyFlg().equalsIgnoreCase("0")) {
                                currentDenominationGrid.setTySh("Receive");
                            } else {
                                currentDenominationGrid.setTySh("Return");
                            }
                        } else{
                            if (this.getTyFlg().equalsIgnoreCase("1")) {
                                currentDenominationGrid.setTySh("Receive");
                            } else {
                                currentDenominationGrid.setTySh("Return");
                            }
                        }
                        denominationTable.add(currentDenominationGrid);
                    }                    
                    double i = 0.0d;
                    if (denominationTable != null) {
                        for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                            if (tyDenoValue.equalsIgnoreCase("0")) {
                                if (dDSDenominationGrid.getTy().equalsIgnoreCase("0")) {
                                    i += dDSDenominationGrid.getDenoAmount();
                                } else {
                                    i -= dDSDenominationGrid.getDenoAmount();
                                }
                            } else {
                                if (dDSDenominationGrid.getTy().equalsIgnoreCase("4")) {
                                    i -= dDSDenominationGrid.getDenoAmount();
                                } else {
                                    i += dDSDenominationGrid.getDenoAmount();
                                }
                            }                            
                        }
                        setCurrencyAmount(formatter.format(i));
                    }
                    if (Double.parseDouble(currencyAmount) == Double.parseDouble(totalAmount)) {
                        this.setDisableDenoNo(true);
                    }
                    setDenoValue("");
                    setDenoNo("");
                } else {
                    setErrorMessage1("Currency " + denoValue + " is not allowed !!");
                }
            } else {
                return;

            }
        } catch (Exception e) {
            setErrorMessage1(e.getLocalizedMessage());
        }
    }

    private void calculateCurrencyAmount() {
        try {
            double i = 0;
            if (denominationTable != null) {
                for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                    if (tyDenoValue.equalsIgnoreCase("0")) {
                        if (dDSDenominationGrid.getTy().equalsIgnoreCase("0")) {
                            i += dDSDenominationGrid.getDenoAmount();
                        } else {
                            i -= dDSDenominationGrid.getDenoAmount();
                        }
                    } else {
                        if (dDSDenominationGrid.getTy().equalsIgnoreCase("4")) {
                            i -= dDSDenominationGrid.getDenoAmount();
                        } else {
                            i += dDSDenominationGrid.getDenoAmount();
                        }
                    }
                }
                setCurrencyAmount(formatter.format(i));
            }
        } catch (Exception e) {
            setErrorMessage1(e.getLocalizedMessage());
        }
    }

    public void valdateClose() {
        if (!(denominationTable.isEmpty() || denominationTable != null)) {
            if (Double.parseDouble(totalAmount) != Double.parseDouble(this.currencyAmount)) {
                this.setErrorMessage1("Total amount is not equal with currency amount");
                this.setCloseDeno(false);
            } else {
                this.setCloseDeno(true);
            }
        } else {
            this.setCloseDeno(true);
        }        
    }
}