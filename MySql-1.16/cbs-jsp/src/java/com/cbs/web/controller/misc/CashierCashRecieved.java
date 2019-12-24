/*
 * CREATED BY    :   ROHIT KRISHNA GUPTA
 * CREATION DATE :   09 AUGUST 2011
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.CashierCashRecieveGridFile;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.misc.CashierCashRecievedFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author ROHIT KRISHNA
 */
public class CashierCashRecieved {

    Context ctx;
    CashierCashRecievedFacadeRemote remoteObject;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private List<CashierCashRecieveGridFile> gridDetail;
    int currentRow;
    private CashierCashRecieveGridFile currentItem = new CashierCashRecieveGridFile();
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
    private boolean denominationRender;
    private String recNo;
    private String acNo;
    private final String jndiHomeName = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private String selectOldNewFlg;
    //private List<SelectItem> oldNewFlgList;
    private String tyFlg;
    private List<SelectItem> tyList;
    private String AcNature;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private List oldLst;
    private List newLst;
    AdvancesInformationTrackingRemote aitr;

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

    public boolean isDenominationRender() {
        return denominationRender;
    }

    public void setDenominationRender(boolean denominationRender) {
        this.denominationRender = denominationRender;
    }

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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

    public boolean isDisableReveiveButton() {
        return disableReveiveButton;
    }

    public void setDisableReveiveButton(boolean disableReveiveButton) {
        this.disableReveiveButton = disableReveiveButton;
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

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public CashierCashRecieveGridFile getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CashierCashRecieveGridFile currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<CashierCashRecieveGridFile> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<CashierCashRecieveGridFile> gridDetail) {
        this.gridDetail = gridDetail;
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

    public String getAcNature() {
        return AcNature;
    }

    public void setAcNature(String AcNature) {
        this.AcNature = AcNature;
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Creates a new instance of CashierCashRecieved
     */
    public CashierCashRecieved() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            remoteObject = (CashierCashRecievedFacadeRemote) ServiceLocator.getInstance().lookup("CashierCashRecievedFacade");
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setDenominationRender(false);
            gridOnLoad();
            this.setErrorMessage("");
            this.setMessage("");
            this.setDisableDenoValue(false);
            this.setDisableDenoNo(false);
            this.setDisableReveiveButton(false);
            validator = new Validator();
            this.setTotalAmount("0.00");
            this.setCurrencyAmount("0.00");
            denominationTable = new LinkedList<DDSDenominationGrid>();
            oldLst = aitr.getReferenceCode("500");
            newLst = aitr.getReferenceCode("501");
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

    public void gridOnLoad() {
        try {
            gridDetail = new ArrayList<CashierCashRecieveGridFile>();
            List resultList = new ArrayList();
            this.setDenominationRender(remoteObject.cashAndDenominitionModChk());
            resultList = remoteObject.gridLoad(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    CashierCashRecieveGridFile cash = new CashierCashRecieveGridFile();
                    cash.setsNo(i + 1);
                    cash.setTokenNo(ele.get(0).toString());
                    cash.setAcno(ele.get(1).toString());
                    cash.setCustName(ele.get(2).toString());
                    cash.setTransaction("CASH");
                    cash.setAmount(formatter.format(ele.get(3)));
                    cash.setBalBeforeInst(formatter.format(ele.get(4)));
                    cash.setEnterBy(ele.get(5).toString());
                    cash.setTokenPaid(ele.get(6).toString());
                    cash.setRecNo(ele.get(7).toString());
                    cash.setDetails(ele.get(8).toString());

                    int index = ele.get(8).toString().indexOf("~`");
                    if (index > -1) {
                        cash.setViewDetails(ele.get(8).toString().substring(0, index));
                    } else {
                        cash.setViewDetails(ele.get(8).toString().toString());
                    }
                    cash.setOrgnCode(ele.get(9).toString());
                    cash.setDestCode(ele.get(10).toString());
                    String jName = "";
                    if (!ele.get(11).toString().trim().equalsIgnoreCase("")) {
                        String val[] = ele.get(11).toString().split("/");
                        if (!ele.get(1).toString().substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                            if (!val[0].trim().equalsIgnoreCase("")) {
                                jName = val[0];
                            }
                            if (!val[1].trim().equalsIgnoreCase("")) {
                                jName = jName + "/" + val[1];
                            }
                            if (!val[2].trim().equalsIgnoreCase("")) {
                                jName = jName + "/" + val[2];
                            }
                            if (!val[3].trim().equalsIgnoreCase("")) {
                                jName = jName + "/" + val[3];
                            }
                        }
                    }

                    cash.setJointName(jName);
                    gridDetail.add(cash);
                }
            }

//            oldNewFlgList = new ArrayList<SelectItem>();
//            oldNewFlgList.add(new SelectItem("O", "OLD"));
//            oldNewFlgList.add(new SelectItem("N", "NEW"));

            tyList = new ArrayList<SelectItem>();
            tyList.add(new SelectItem("0", "Recieved"));
            tyList.add(new SelectItem("3", "Return"));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void changeCashRecieved() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setDisableReveiveButton(true);
            CashierCashRecieveGridFile item = gridDetail.get(currentRow);
            if (item.getTokenPaid().equalsIgnoreCase("N")) {
                boolean found1 = false;
                for (CashierCashRecieveGridFile item1 : gridDetail) {
                    if (item1.getTokenPaid().equalsIgnoreCase("Y")) {
                        found1 = true;
                    }
                }
                if (found1) {
                    this.setErrorMessage("Only one transaction cash can be recieved at one time.");
                    if (this.denominationRender == true) {
                        if (denominationTable.isEmpty()) {
                            this.setDisableReveiveButton(true);
                        } else {
                            this.setDisableReveiveButton(false);
                        }
                    } else {
                        this.setDisableReveiveButton(false);
                    }
                } else {
                    if (this.denominationRender == true) {
                        this.setDisableReveiveButton(true);
                    } else {
                        this.setDisableReveiveButton(false);
                    }
                    this.setRecNo(item.getRecNo());
                    this.setAcNo(item.getAcno());
                    this.setTotalAmount(item.getAmount());
                    item.setTokenPaid("Y");
                    gridDetail.remove(currentRow);
                    gridDetail.add(currentRow, item);
                    AcNature = ftsPostRemote.getAccountNature(item.getAcno());
                }
            } else if (item.getTokenPaid().equalsIgnoreCase("Y")) {
                this.setTotalAmount("0.00");
                this.setCurrencyAmount("0.00");
                denominationTable = new LinkedList<DDSDenominationGrid>();
                this.setDisableDenoNo(false);
                this.setDisableDenoValue(false);
                item.setTokenPaid("N");
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
                AcNature = "";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void cashRecievedBtn() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setErrorMessage("Grid is empty !!!");
                return;
            }
            if (this.denominationRender == true) {
                if (denominationTable == null || denominationTable.isEmpty()) {
                    this.setErrorMessage("Please enter denomination detail !!!");
                    return;
                }

                if (Double.parseDouble(currencyAmount) > Double.parseDouble(totalAmount)) {
                    setErrorMessage("Currency amount is exceeding total receivable amount !!");
                    return;
                } else if (Double.parseDouble(currencyAmount) < Double.parseDouble(totalAmount)) {
                    setErrorMessage("Currency amount is less total receivable amount !!");
                    return;
                }
            }


//            DenominitionTable denominitionList = getDenominitionTable();
//            if (this.denominationRender == true) {
//                if (denominitionList == null) {
//                    this.setErrorMessage("Please enter denomination detail !!!");
//                    return;
//                }
//            }
            String enterDt = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
//            String result = remoteObject.cashRecievedProcess(gridDetail, this.userName, enterDt, denominitionList, this.denominationRender, this.orgnBrCode);
            String result = remoteObject.cashRecievedProcess(gridDetail, this.userName, enterDt, denominationTable, this.denominationRender, this.orgnBrCode);
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
            this.setSelectOldNewFlg("N");
            this.setTyFlg("0");
            gridOnLoad();
            denominationTable = new LinkedList<DDSDenominationGrid>();
            this.setDisableReveiveButton(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void deleteForm() {
        Date date = new Date();
        String curDate = sdf.format(date);
        try {
            double amount = Double.parseDouble(currentItem.getAmount());
            String delMsg = txnAuthRemote.cbsAuthCashCrDeletion(Float.parseFloat(currentItem.getRecNo()), "", currentItem.getAcno(),
                    amount, curDate, currentItem.getCustName(), "A", Float.parseFloat(currentItem.getTokenNo()), curDate, currentItem.getEnterBy(), userName,
                    currentItem.getOrgnCode());

            if (delMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                gridOnLoad();
                this.setMessage("Entry deleted successfuly");
            } else {
                this.setMessage(delMsg);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setTotalAmount("0.00");
            this.setCurrencyAmount("0.00");
            this.setDenoNo("");
            this.setDenoValue("");
            this.setDisableDenoNo(false);
            this.setDisableDenoValue(false);
            this.setSelectOldNewFlg("N");
            this.setTyFlg("0");
            gridOnLoad();
            denominationTable = new LinkedList<DDSDenominationGrid>();
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

    /**
     * Below code is for denomination*
     */
    public void onBlurDenominationNo() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setDisableReveiveButton(true);
        try {
            if (validate()) {
                if (checkCurrency()) {
                    setErrorMessage("");
//                    DenominationComparatorDesc denominationComparator = new DenominationComparatorDesc();
                    DDSDenominationGrid currentDenominationGrid = new DDSDenominationGrid();
                    if (denominationTable != null) {
                        currentDenominationGrid.setSno(denominationTable.size() + 1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                        currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                        currentDenominationGrid.setFlag("N");
                        currentDenominationGrid.setTy(this.getTyFlg());
                        if (this.getTyFlg().equalsIgnoreCase("0")) {
                            currentDenominationGrid.setTySh("Receive");
                        } else {
                            currentDenominationGrid.setTySh("Return");
                        }
                        ComparatorChain chain = new ComparatorChain();
                        chain.addComparator(new DenoValueCompare());
                        chain.addComparator(new DenoValueFlag());

                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, chain) >= 0) {
//                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, chain) != 0) {
                            this.setErrorMessage("You have already entered denomination for " + denoValue + ", So you can only update it by selecting from grid !!!");

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
                        if (this.getTyFlg().equalsIgnoreCase("0")) {
                            currentDenominationGrid.setTySh("Receive");
                        } else {
                            currentDenominationGrid.setTySh("Return");
                        }
                        denominationTable.add(currentDenominationGrid);
                    }

//                    Collections.sort(denominationTable, denominationComparator);
                    double i = 0.0d;
                    if (denominationTable != null) {
                        for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                            if (dDSDenominationGrid.getTy().equalsIgnoreCase("0")) {
                                i += dDSDenominationGrid.getDenoAmount();
                            } else {
                                i -= dDSDenominationGrid.getDenoAmount();
                            }
                            //i += dDSDenominationGrid.getDenoAmount();
                        }
                        setCurrencyAmount(formatter.format(i));
                    }
                    if (Double.parseDouble(currencyAmount) == Double.parseDouble(totalAmount)) {
                        this.setDisableReveiveButton(false);
                        this.setDisableDenoNo(true);
                        this.setDisableDenoValue(true);
                    }
//                    else if (Double.parseDouble(currencyAmount) > Double.parseDouble(totalAmount)) {
//                        denominationTable.remove(currentDenominationGrid);
//                        this.setDisableReveiveButton(true);
//                        calculateCurrencyAmount();
//                        setErrorMessage("Currency amount is exceeding total receivable amount !!");
//                    }
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

//    class DenominationComparatorDesc implements Comparator<DDSDenominationGrid> {
//
//        public int compare(DDSDenominationGrid one, DDSDenominationGrid two) {
//            if (two.getDenoValue().equals(one.getDenoValue()) && two.getFlag().equalsIgnoreCase(one.getFlag())) {
//                return 0;
//            } else {
//                return 1;
//            }
//        }
//    }
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

            if (!AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                if (this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                    for (int i = 0; i < oldLst.size(); i++) {
//                        Vector element = (Vector) oldLst.get(i);
//                        if (Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)) {
//                            return true;
//                        }
//                    }
//                    this.setErrorMessage("This denomination in old can't be accepted");
//                    return false;
//                }

//                if (this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
                    for (int i = 0; i < newLst.size(); i++) {
                        Vector element = (Vector) newLst.get(i);
                        if (Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)) {
                            return true;
                        }
                    }
                    this.setErrorMessage("This denomination can't be accepted");
                    return false;
//                }
            }

//            if (tyFlg.equalsIgnoreCase("0")) {
//                if ((denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0"))
//                        && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                    this.setErrorMessage("2000 notes can't be old !!!");
//                    return false;
//                }
//                if (!(denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0")
//                        || denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.00") || denoValue.equalsIgnoreCase("500.0"))
//                        && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                    this.setErrorMessage(denoValue + " notes can't be New !!!");
//                    return false;
//                }
//            } else if (tyFlg.equalsIgnoreCase("3")) {
//                
//                if (!AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                    if ((denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.0") || denoValue.equalsIgnoreCase("500.00")
//                            || denoValue.equalsIgnoreCase("1000") || denoValue.equalsIgnoreCase("1000.00") || denoValue.equalsIgnoreCase("1000.0"))
//                            && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                        this.setErrorMessage("Old 500/1000 notes can't withdraw !!!");
//                        return false;
//                    }
//                }
//
//                if (!(denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.00") || denoValue.equalsIgnoreCase("500.0")
//                        || denoValue.equalsIgnoreCase("1000") || denoValue.equalsIgnoreCase("1000.00") || denoValue.equalsIgnoreCase("1000.0")
//                        || denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0"))
//                        && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                    this.setErrorMessage(denoValue + "notes can't be new !!!");
//                    return false;
//                }
//            } else {
//                this.setErrorMessage("Please Check Transaction Type !!!");
//                return false;
//            }

//            if ((denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0"))
//                    && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                this.setErrorMessage("2000 notes can't be old !!!");
//                return false;
//            }
//
//            if (!(denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0")
//                    || denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.00") || denoValue.equalsIgnoreCase("500.0"))
//                    && this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
//                this.setErrorMessage(denoValue + " notes can't be New !!!");
//                return false;
//            }
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
//            setSelectOldNewFlg(currentDenominationItem.getFlag());
            setTyFlg(currentDenominationItem.getTy());
            denominationTable.remove(currentDenominationItem);
            double i = 0.0d;
            if (denominationTable != null) {
                for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                    if (dDSDenominationGrid.getTy().equalsIgnoreCase("0")) {
                        i += dDSDenominationGrid.getDenoAmount();
                    } else {
                        i -= dDSDenominationGrid.getDenoAmount();
                    }
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
            setMessage(e.getLocalizedMessage());
        }
        return false;
    }

//    public DenominitionTable getDenominitionTable() throws ParseException {
//        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
//        Date d = new Date();
//        DenominitionTable denominition = new DenominitionTable();
//        try {
//            denominition.setAcno(this.acNo);
//            denominition.setAmount(Double.parseDouble(this.totalAmount));
//            denominition.setDt(ymdFormat.format(d));
//            denominition.setEnterBy(userName);
//            denominition.setRecno(Double.parseDouble(this.recNo));
//            denominition.setRs1(0);
//            denominition.setRs2(0);
//            denominition.setRs5(0);
//            denominition.setRs10(0);
//            denominition.setRs20(0);
//            denominition.setRs50(0);
//            denominition.setRs100(0);
//            denominition.setRs500(0);
//            denominition.setRs1000(0);
//            denominition.setRs00001(0);
//            denominition.setRs00002(0);
//            denominition.setRs001(0);
//            denominition.setRs002(0);
//            denominition.setRs005(0);
//            denominition.setRs010(0);
//            denominition.setRs020(0);
//            denominition.setRs025(0);
//            denominition.setRs05(0);
//            denominition.setRsc001(0);
//            denominition.setTrantime(ymdFormat.format(d));
//            denominition.setTy(0);
//
//            for (DDSDenominationGrid dSDenominationGrid : denominationTable) {
//                if (dSDenominationGrid.getDenoValue() == 0.5) {
//                    denominition.setRs05(dSDenominationGrid.getDenoNo().intValue());
//                    break;
//                }
//                switch (dSDenominationGrid.getDenoValue().intValue()) {
//                    case 1:
//                        denominition.setRs1(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 2:
//                        denominition.setRs2(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 5:
//                        denominition.setRs5(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 10:
//                        denominition.setRs10(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 20:
//                        denominition.setRs20(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 50:
//                        denominition.setRs50(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 100:
//                        denominition.setRs100(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 500:
//                        denominition.setRs500(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 1000:
//                        denominition.setRs1000(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                }
//            }
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//        return denominition;
//    }
    private void calculateCurrencyAmount() {
        try {
            double i = 0;
            if (denominationTable != null) {
                for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                    if (dDSDenominationGrid.getTy().equalsIgnoreCase("0")) {
                        i += dDSDenominationGrid.getDenoAmount();
                    } else {
                        i -= dDSDenominationGrid.getDenoAmount();
                    }
                }
                setCurrencyAmount(formatter.format(i));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
