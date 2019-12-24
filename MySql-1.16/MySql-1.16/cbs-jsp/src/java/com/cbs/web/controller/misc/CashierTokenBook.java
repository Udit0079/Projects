/*
 * CREATED BY    :   ROHIT KRISHNA GUPTA
 * CREATION DATE :   11 AUGUST 2011
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.CashTokenBookGridFile;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.misc.CashTokenBookFacadeRemote;
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
public class CashierTokenBook {

    Context ctx;
    CashTokenBookFacadeRemote remoteObject;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private List<CashTokenBookGridFile> gridDetail;
    int currentRow;
    private CashTokenBookGridFile currentItem = new CashTokenBookGridFile();
    /**
     * For Denomination*
     */
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

    public boolean isDenominationRender() {
        return denominationRender;
    }

    public void setDenominationRender(boolean denominationRender) {
        this.denominationRender = denominationRender;
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

    /**
     * *
     */
    public CashTokenBookGridFile getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CashTokenBookGridFile currentItem) {
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

    public List<CashTokenBookGridFile> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<CashTokenBookGridFile> gridDetail) {
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
     * Creates a new instance of CashierTokenBook
     */
    public CashierTokenBook() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            remoteObject = (CashTokenBookFacadeRemote) ServiceLocator.getInstance().lookup("CashTokenBookFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            setUserName(req.getRemoteUser());
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
            gridDetail = new ArrayList<CashTokenBookGridFile>();
            List resultList = new ArrayList();
            this.setDenominationRender(remoteObject.cashAndDenominitionModChk());
            resultList = remoteObject.gridLoad(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    CashTokenBookGridFile cash = new CashTokenBookGridFile();
                    cash.setSrNo(i + 1);
                    cash.setTokenNo(ele.get(0).toString());
                    cash.setSubTokenNo(ele.get(1).toString());
                    cash.setAcno(ele.get(2).toString());
                    cash.setCustName(ele.get(3).toString());
                    cash.setAmount(formatter.format(ele.get(4)));
                    cash.setEnterBy(ele.get(5).toString());
                    cash.setAuthBy(ele.get(6).toString());
                    cash.setRecNo(ele.get(7).toString());
                    cash.setOrgBrnId(ele.get(8).toString());
                    cash.setDestBrnId(ele.get(9).toString());
                    String jName = "";

                    if (!ele.get(10).toString().trim().equalsIgnoreCase("")) {
                        String val[] = ele.get(10).toString().split("/");

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
                    cash.setJointName(jName);
                    cash.setTokenPaid("N");
                    gridDetail.add(cash);
                }
            }

//            oldNewFlgList = new ArrayList<SelectItem>();
//            oldNewFlgList.add(new SelectItem("O", "OLD"));
//            oldNewFlgList.add(new SelectItem("N", "NEW"));

            tyList = new ArrayList<SelectItem>();
            tyList.add(new SelectItem("1", "Paid"));
            tyList.add(new SelectItem("4", "Return"));

            oldLst = aitr.getReferenceCode("500");
            newLst = aitr.getReferenceCode("501");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void changeTokenPaid() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setDisableReveiveButton(true);
            CashTokenBookGridFile item = gridDetail.get(currentRow);
            if (item.getTokenPaid().equalsIgnoreCase("N")) {
                boolean found1 = false;
                for (CashTokenBookGridFile item1 : gridDetail) {
                    if (item1.getTokenPaid().equalsIgnoreCase("Y")) {
                        found1 = true;
                    }
                }
                if (found1) {
                    this.setErrorMessage("Only one token can be paid at one time.");
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

    public void paidBtnAction() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setErrorMessage("Please select the record from grid by double clicking on Token Paid column to change from N to Y and then press Paid button.");
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
//            String result = remoteObject.tokenPaidDebitWithCashier(gridDetail, this.userName, enterDt, denominitionList, this.denominationRender, this.orgnBrCode);
            String result = remoteObject.tokenPaidDebitWithCashier(gridDetail, this.userName, enterDt, denominationTable, this.denominationRender, this.orgnBrCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
            } else {
                this.setMessage(result);
            }
            gridOnLoad();
            denominationTable = new LinkedList<DDSDenominationGrid>();
            this.setDisableDenoValue(false);
            this.setDisableDenoNo(false);
            this.setDisableReveiveButton(false);
            this.setTotalAmount("0.00");
            this.setCurrencyAmount("0.00");
            this.setDenoNo("");
            this.setDenoValue("");
            this.setSelectOldNewFlg("N");
            this.setTyFlg("1");
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
            gridOnLoad();
            denominationTable = new LinkedList<DDSDenominationGrid>();
            this.setDisableDenoValue(false);
            this.setDisableDenoNo(false);
            this.setDisableReveiveButton(false);
            this.setTotalAmount("0.00");
            this.setCurrencyAmount("0.00");
            this.setDenoNo("");
            this.setDenoValue("");
            this.setSelectOldNewFlg("N");
            this.setTyFlg("1");
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
                    //DenominationComparatorDesc denominationComparator = new DenominationComparatorDesc();                    
                    //DenominationComparatorDesc denominationComparator = new DenominationComparatorDesc();
                    DDSDenominationGrid currentDenominationGrid = new DDSDenominationGrid();
                    if (denominationTable != null) {
                        currentDenominationGrid.setSno(denominationTable.size() + 1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                        currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                        currentDenominationGrid.setFlag("N");
                        currentDenominationGrid.setTy(this.getTyFlg());
                        if (this.getTyFlg().equalsIgnoreCase("4")) {
                            currentDenominationGrid.setTySh("Return");
                        } else {
                            currentDenominationGrid.setTySh("Paid");
                        }

                        ComparatorChain chain = new ComparatorChain();
                        chain.addComparator(new DenoValueCompare());
                        chain.addComparator(new DenoValueFlag());

                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, chain) >= 0) {
//                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, chain) != 0) {
                            this.setErrorMessage("You have already entered denomination for " + denoValue + " with " + this.getSelectOldNewFlg() + ", So you can only update it by selecting from grid !!!");

                        } else {
                            denominationTable.add(currentDenominationGrid);
                        }
//                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, denominationComparator) != 0) {                            
//                            denominationTable.add(currentDenominationGrid);
//                        } else {
//                            this.setErrorMessage("You have already entered denomination for " + denoValue + " with " + this.getSelectOldNewFlg() + ", So you can only update it by selecting from grid !!!");
//                        }
                    } else {
                        denominationTable = new LinkedList<DDSDenominationGrid>();
                        currentDenominationGrid.setSno(1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                        currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                        currentDenominationGrid.setFlag("N");
                        currentDenominationGrid.setTy(this.getTyFlg());
                        if (this.getTyFlg().equalsIgnoreCase("4")) {
                            currentDenominationGrid.setTySh("Return");
                        } else {
                            currentDenominationGrid.setTySh("Paid");
                        }
                        denominationTable.add(currentDenominationGrid);
                    }

                    //Collections.sort(denominationTable, denominationComparator);
                    double i = 0.0d;
                    if (denominationTable != null) {
                        for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                            if (dDSDenominationGrid.getTy().equalsIgnoreCase("4")) {
                                i -= dDSDenominationGrid.getDenoAmount();
                            } else {
                                i += dDSDenominationGrid.getDenoAmount();
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
//        public int compare(DDSDenominationGrid one, DDSDenominationGrid two) {
//            if(two.getDenoValue().equals(one.getDenoValue()) && two.getFlag().equalsIgnoreCase(one.getFlag())){
//                return 0;
//            }else{
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
                    this.setErrorMessage("This denomination in new can't be accepted");
                    return false;
//                }
            }

//            if (tyFlg.equalsIgnoreCase("1")) {
//                if ((denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0"))
//                        && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                    this.setErrorMessage("2000 notes can't be old !!!");
//                    return false;
//                }
//                
//                if (!AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                    if ((denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.0") || denoValue.equalsIgnoreCase("500.00")
//                            || denoValue.equalsIgnoreCase("1000") || denoValue.equalsIgnoreCase("1000.00") || denoValue.equalsIgnoreCase("1000.0"))
//                            && this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                        this.setErrorMessage("Old 500/1000 notes can't withdraw !!!");
//                        return false;
//                    }
//                }
//            } else if (tyFlg.equalsIgnoreCase("4")) {
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
                    if (dDSDenominationGrid.getTy().equalsIgnoreCase("4")) {
                        i -= dDSDenominationGrid.getDenoAmount();
                    } else {
                        i += dDSDenominationGrid.getDenoAmount();
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
//            denominition.setTy(1);
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
                    if (dDSDenominationGrid.getTy().equalsIgnoreCase("4")) {
                        i -= dDSDenominationGrid.getDenoAmount();
                    } else {
                        i += dDSDenominationGrid.getDenoAmount();
                    }
                }
                setCurrencyAmount(formatter.format(i));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
