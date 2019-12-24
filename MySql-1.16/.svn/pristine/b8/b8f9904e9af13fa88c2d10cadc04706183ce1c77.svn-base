/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.DenominitionTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.CashTokenBookFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class CashAllotmentRegister {

    Context ctx;
    CashTokenBookFacadeRemote remoteObject;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private Validator validator;
    private String denoValue;
    private String denoNo;
    private boolean disableDenoNo;
    private boolean disableDenoValue;
    private boolean disableReveiveButton;
    private List<DDSDenominationGrid> denominationTable;
    private String totalAmount;
    private String currencyAmount;
    private DDSDenominationGrid currentDenominationItem;
    private int currentIDenominationRow;
    private String activeCashier;
    private List<SelectItem> activeCashierList;
    private String openingBal;
    private String recNo;
    private String acNo;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getRecNo() {
        return recNo;
    }

    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    public String getActiveCashier() {
        return activeCashier;
    }

    public void setActiveCashier(String activeCashier) {
        this.activeCashier = activeCashier;
    }

    public List<SelectItem> getActiveCashierList() {
        return activeCashierList;
    }

    public void setActiveCashierList(List<SelectItem> activeCashierList) {
        this.activeCashierList = activeCashierList;
    }

    public String getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(String openingBal) {
        this.openingBal = openingBal;
    }

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
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

    public String getDenoNo() {
        return denoNo;
    }

    public void setDenoNo(String denoNo) {
        this.denoNo = denoNo;
    }

    public String getDenoValue() {
        return denoValue;
    }

    public void setDenoValue(String denoValue) {
        this.denoValue = denoValue;
    }

    public List<DDSDenominationGrid> getDenominationTable() {
        return denominationTable;
    }

    public void setDenominationTable(List<DDSDenominationGrid> denominationTable) {
        this.denominationTable = denominationTable;
    }

    public boolean isDisableDenoNo() {
        return disableDenoNo;
    }

    public void setDisableDenoNo(boolean disableDenoNo) {
        this.disableDenoNo = disableDenoNo;
    }

    public boolean isDisableDenoValue() {
        return disableDenoValue;
    }

    public void setDisableDenoValue(boolean disableDenoValue) {
        this.disableDenoValue = disableDenoValue;
    }

    public boolean isDisableReveiveButton() {
        return disableReveiveButton;
    }

    public void setDisableReveiveButton(boolean disableReveiveButton) {
        this.disableReveiveButton = disableReveiveButton;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of CashAllotmentRegister */
    public CashAllotmentRegister() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            remoteObject = (CashTokenBookFacadeRemote) ServiceLocator.getInstance().lookup("CashTokenBookFacade");
            Date date = new Date();
            setTodayDate(sdf.format(date));
            validator = new Validator();
            this.setErrorMessage("");
            this.setMessage("");
            this.setTotalAmount("0.00");
            this.setCurrencyAmount("0.00");
            this.setOpeningBal("0.00");
            onloadActiveCashier();
            this.setDisableReveiveButton(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void onloadActiveCashier() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.activeCashierCombo(this.orgnBrCode);
            activeCashierList = new ArrayList<SelectItem>();
            activeCashierList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele1 = (Vector) resultList.get(i);
                    activeCashierList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void openingBalOnBlur() {
        try {
            if (!validator.validateDoublePositive(this.openingBal)) {
                this.setTotalAmount("0.00");
                this.setOpeningBal("0");
                this.setErrorMessage("Please enter only numeric values in denomination !!!");
                return;
            } else {
                this.setTotalAmount(this.openingBal);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void cashRecievedBtn() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.activeCashier.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select active cashier.");
                return;
            }
            if (this.openingBal == null || this.openingBal.equalsIgnoreCase("") || this.openingBal.length() == 0) {
                this.setErrorMessage("Please enter opening balance.");
                return;
            }
            if (!validator.validateDoublePositive(openingBal)) {
                this.setErrorMessage("Please enter only numeric values in opening balance !!!");
                return;
            }
            if (Double.parseDouble(this.openingBal) == 0) {
                this.setErrorMessage("Zero amount cannot be recieved by cashier.");
                return;
            }
            if (Double.parseDouble(this.totalAmount) != Double.parseDouble(this.totalAmount)) {
                this.setErrorMessage("Denomination amount and opening balances are not equal.");
            }
            if (this.userName == null || this.userName.equalsIgnoreCase("") || this.userName.length() == 0) {
                this.setErrorMessage("User name cannot be blank.");
                return;
            }
            if (this.orgnBrCode == null || this.orgnBrCode.equalsIgnoreCase("") || this.orgnBrCode.length() == 0) {
                this.setErrorMessage("Branch Id cannot be blank.");
                return;
            }
            if (denominationTable == null || denominationTable.isEmpty()) {
                this.setErrorMessage("Please enter denomination detail.");
                return;
            }
            DenominitionTable denominitionList = getDenominitionTable();
            String enterDt = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
            if (denominitionList == null) {
                this.setErrorMessage("Denomination grid is empty , Please enter denomination !!!");
                return;
            }
            String result = remoteObject.cashRecievedByCashier(denominitionList, this.activeCashier, Double.parseDouble(this.openingBal), this.userName, enterDt, this.orgnBrCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
            } else {
                this.setMessage(result);
            }
            this.setDenoNo("");
            this.setDenoValue("");
            this.setDisableDenoNo(false);
            this.setDisableDenoValue(false);
            this.setTotalAmount("0.00");
            this.setCurrencyAmount("0.00");
            denominationTable = new LinkedList<DDSDenominationGrid>();
            this.setDisableReveiveButton(true);
            this.setOpeningBal("0.00");
            this.setActiveCashier("--Select--");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setDenoNo("");
            this.setDenoValue("");
            this.setDisableDenoNo(false);
            this.setDisableDenoValue(false);
            this.setTotalAmount("0.00");
            this.setCurrencyAmount("0.00");
            denominationTable = new LinkedList<DDSDenominationGrid>();
            this.setDisableReveiveButton(true);
            this.setOpeningBal("0.00");
            this.setActiveCashier("--Select--");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    /**Below methods are for cash denominition**/
    public void onBlurDenominationNo() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setDisableReveiveButton(true);
        try {
            if (validate()) {
                if (checkCurrency()) {
                    setErrorMessage("");
                    DenominationComparatorDesc denominationComparator = new DenominationComparatorDesc();
                    DDSDenominationGrid currentDenominationGrid = new DDSDenominationGrid();
                    if (denominationTable != null) {
                        currentDenominationGrid.setSno(denominationTable.size() + 1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, denominationComparator) < 0) {
                            denominationTable.add(currentDenominationGrid);
                        } else {
                            this.setErrorMessage("You have already entered denomination for " + denoValue + " , So you can only update it by selecting from grid !!!");
                        }
                    } else {
                        denominationTable = new LinkedList<DDSDenominationGrid>();
                        currentDenominationGrid.setSno(1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
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
                        this.setDisableReveiveButton(false);
                        this.setDisableDenoNo(true);
                        this.setDisableDenoValue(true);
                    } else if (Double.parseDouble(currencyAmount) > Double.parseDouble(totalAmount)) {
                        denominationTable.remove(currentDenominationGrid);
                        this.setDisableReveiveButton(true);
                        calculateCurrencyAmount();
                        setErrorMessage("Currency amount is exceeding total receivable amount !!");
                    }
                    setDenoValue("");
                    setDenoNo("");
                } else {
                    setErrorMessage("Currency " + denoValue + " is not allowed !!");
                }
            } else {
                return;
                //setErrorMessage("Please fill proper denomination and number of Notes / Coins !!!");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    class DenominationComparatorDesc implements Comparator<DDSDenominationGrid> {

        public int compare(DDSDenominationGrid one, DDSDenominationGrid two) {
            return two.getDenoValue().compareTo(one.getDenoValue());
        }
    }

    private boolean validate() {
        try {
            if (!validator.validateDoublePositive(denoValue)) {
                this.setErrorMessage("Please enter only numeric values in denomination !!!");
                return false;
            }
            if (!validator.validateInteger(denoNo)) {
                this.setErrorMessage("Please enter only numeric values in number of notes / coins !!!");
                return false;
            }
            if (Integer.parseInt(this.denoNo) == 0) {
                this.setErrorMessage("Zero is not allowed in number of notes / coins !!!");
                return false;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return true;
    }

    public void setDenominationRowValues() {
        try {
            this.setDisableReveiveButton(true);
            denoValue = String.valueOf(currentDenominationItem.getDenoValue());
            denoNo = String.valueOf(currentDenominationItem.getDenoNo());
            denominationTable.remove(currentDenominationItem);
            double i = 0.0d;
            if (denominationTable != null) {
                for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                    i += dDSDenominationGrid.getDenoAmount();
                }
                setCurrencyAmount(formatter.format(i));
            }
            if (Double.parseDouble(currencyAmount) == Double.parseDouble(totalAmount)) {
                setDisableReveiveButton(false);
                setDisableDenoNo(true);
                setDisableDenoValue(true);
            } else {
                setDisableReveiveButton(true);
                setDisableDenoNo(false);
                setDisableDenoValue(false);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
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
            switch (value.intValue()) {
                case 1:
                    return true;
                case 2:
                    return true;
                case 5:
                    return true;
                case 10:
                    return true;
                case 20:
                    return true;
                case 50:
                    return true;
                case 100:
                    return true;
                case 500:
                    return true;
                case 1000:
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return false;
    }

    public DenominitionTable getDenominitionTable() throws ParseException {
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        Date d = new Date();
        DenominitionTable denominition = new DenominitionTable();
        try {
            denominition.setCashierId(this.activeCashier);
            denominition.setAmount(Double.parseDouble(this.totalAmount));
            denominition.setDt(ymdFormat.format(d));
            denominition.setEnterBy(userName);
            //denominition.setRecno(Double.parseDouble(this.recNo));
            denominition.setRs1(0);
            denominition.setRs2(0);
            denominition.setRs5(0);
            denominition.setRs10(0);
            denominition.setRs20(0);
            denominition.setRs50(0);
            denominition.setRs100(0);
            denominition.setRs500(0);
            denominition.setRs1000(0);
            denominition.setRs00001(0);
            denominition.setRs00002(0);
            denominition.setRs001(0);
            denominition.setRs002(0);
            denominition.setRs005(0);
            denominition.setRs010(0);
            denominition.setRs020(0);
            denominition.setRs025(0);
            denominition.setRs05(0);
            denominition.setRsc001(0);
            denominition.setTrantime(ymdFormat.format(d));
            denominition.setTy(0);

            for (DDSDenominationGrid dSDenominationGrid : denominationTable) {
                if (dSDenominationGrid.getDenoValue() == 0.5) {
                    denominition.setRs05(dSDenominationGrid.getDenoNo().intValue());
                    break;
                }
                switch (dSDenominationGrid.getDenoValue().intValue()) {
                    case 1:
                        denominition.setRs1(dSDenominationGrid.getDenoNo().intValue());
                        break;
                    case 2:
                        denominition.setRs2(dSDenominationGrid.getDenoNo().intValue());
                        break;
                    case 5:
                        denominition.setRs5(dSDenominationGrid.getDenoNo().intValue());
                        break;
                    case 10:
                        denominition.setRs10(dSDenominationGrid.getDenoNo().intValue());
                        break;
                    case 20:
                        denominition.setRs20(dSDenominationGrid.getDenoNo().intValue());
                        break;
                    case 50:
                        denominition.setRs50(dSDenominationGrid.getDenoNo().intValue());
                        break;
                    case 100:
                        denominition.setRs100(dSDenominationGrid.getDenoNo().intValue());
                        break;
                    case 500:
                        denominition.setRs500(dSDenominationGrid.getDenoNo().intValue());
                        break;
                    case 1000:
                        denominition.setRs1000(dSDenominationGrid.getDenoNo().intValue());
                        break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return denominition;
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
            setMessage(e.getLocalizedMessage());
        }
    }
    /****/
}
