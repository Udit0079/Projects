/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.misc.CashierCashRecievedFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Spellar;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.MoneyExchgPojo;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class MoneyExchRecord extends BaseBean {

    private String tendName;
    private String idProof = "";
    private String idNo;
    private String place = "";
    private String option;
    private double denCnt;
    private String denWord;
    private double paidAmt;
    private String paidAmtWord;
    private String authFlag;
    private String msg;
    public double exchgAmt;
    public String exchgLimit;
    private String returnPaid;
    private List<SelectItem> idProofList;
    private List<SelectItem> placeList;
    private List<SelectItem> optList;
    private List<SelectItem> returnPaidList;
    private String btnCaption;
    private List<MoneyExchgPojo> monExchTable;
    private MoneyExchgPojo currentItem = new MoneyExchgPojo();
    public String entBy;
    public int spNo;
    private boolean disabled;
    private String denomination;
    private String noOfNoteCoins;
    private String focusId;
    private List newLst;
    private List<DDSDenominationGrid> denominationTab;
    private DDSDenominationGrid currentDenominationItm;
    DecimalFormat format = new DecimalFormat("0.0");
    AdvancesInformationTrackingRemote aitr;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    CashierCashRecievedFacadeRemote remoteObject;
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public double getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(double paidAmt) {
        this.paidAmt = paidAmt;
    }

    public String getPaidAmtWord() {
        return paidAmtWord;
    }

    public void setPaidAmtWord(String paidAmtWord) {
        this.paidAmtWord = paidAmtWord;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getTendName() {
        return tendName;
    }

    public void setTendName(String tendName) {
        this.tendName = tendName;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public List<SelectItem> getIdProofList() {
        return idProofList;
    }

    public void setIdProofList(List<SelectItem> idProofList) {
        this.idProofList = idProofList;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<SelectItem> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<SelectItem> placeList) {
        this.placeList = placeList;
    }

    public double getDenCnt() {
        return denCnt;
    }

    public void setDenCnt(double denCnt) {
        this.denCnt = denCnt;
    }

    public String getDenWord() {
        return denWord;
    }

    public void setDenWord(String denWord) {
        this.denWord = denWord;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getExchgAmt() {
        return exchgAmt;
    }

    public void setExchgAmt(double exchgAmt) {
        this.exchgAmt = exchgAmt;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptList() {
        return optList;
    }

    public void setOptList(List<SelectItem> optList) {
        this.optList = optList;
    }

    public String getBtnCaption() {
        return btnCaption;
    }

    public void setBtnCaption(String btnCaption) {
        this.btnCaption = btnCaption;
    }

    public List<MoneyExchgPojo> getMonExchTable() {
        return monExchTable;
    }

    public void setMonExchTable(List<MoneyExchgPojo> monExchTable) {
        this.monExchTable = monExchTable;
    }

    public MoneyExchgPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(MoneyExchgPojo currentItem) {
        this.currentItem = currentItem;
    }

    public String getEntBy() {
        return entBy;
    }

    public void setEntBy(String entBy) {
        this.entBy = entBy;
    }

    public int getSpNo() {
        return spNo;
    }

    public void setSpNo(int spNo) {
        this.spNo = spNo;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getNoOfNoteCoins() {
        return noOfNoteCoins;
    }

    public void setNoOfNoteCoins(String noOfNoteCoins) {
        this.noOfNoteCoins = noOfNoteCoins;
    }

    public String getReturnPaid() {
        return returnPaid;
    }

    public void setReturnPaid(String returnPaid) {
        this.returnPaid = returnPaid;
    }

    public List<SelectItem> getReturnPaidList() {
        return returnPaidList;
    }

    public void setReturnPaidList(List<SelectItem> returnPaidList) {
        this.returnPaidList = returnPaidList;
    }

    public DDSDenominationGrid getCurrentDenominationItm() {
        return currentDenominationItm;
    }

    public void setCurrentDenominationItm(DDSDenominationGrid currentDenominationItm) {
        this.currentDenominationItm = currentDenominationItm;
    }

    public List<DDSDenominationGrid> getDenominationTab() {
        return denominationTab;
    }

    public void setDenominationTab(List<DDSDenominationGrid> denominationTab) {
        this.denominationTab = denominationTab;
    }
    NumberFormat formatter = new DecimalFormat("#0.00");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public MoneyExchRecord() {
        try {
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            remoteObject = (CashierCashRecievedFacadeRemote) ServiceLocator.getInstance().lookup("CashierCashRecievedFacade");
            authFlag = "Y";
            optList = new ArrayList<>();
            List funList = aitr.getReferenceCode("415");
            for (int i = 0; i < funList.size(); i++) {
                Vector element = (Vector) funList.get(i);
                optList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            for (SelectItem item : optList) {
                if (item.getValue().toString().equalsIgnoreCase("V")) {
                    authFlag = "N";
                    break;
                }
            }

            idProofList = new ArrayList<>();
            idProofList.add(new SelectItem("0", "--Select--"));

            List idList = aitr.getReferenceCode("311");
            for (int i = 0; i < idList.size(); i++) {
                Vector element = (Vector) idList.get(i);
                if (!(element.get(0).toString().equals("S01") || element.get(0).toString().equals("S02"))) {
                    idProofList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }
            }
            //setIdProof("0");
            placeList = new ArrayList<>();
            placeList.add(new SelectItem("0", "-- Select -- "));
            List listCity = aitr.getReferenceCode("001");
            for (int i = 0; i < listCity.size(); i++) {
                Vector element = (Vector) listCity.get(i);
                placeList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //setPlace("");

            returnPaidList = new ArrayList<>();
            List txnList = aitr.getReferenceCode("414");
            for (int i = 0; i < txnList.size(); i++) {
                Vector element = (Vector) txnList.get(i);
                returnPaidList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            
            exchgLimit = ftsPostRemote.getCodeFromCbsParameterInfo("EXCHANGE_LIMIT_AMT");

            btnCaption = "Save";
            setDisabled(false);
            denominationTab = new ArrayList<>();
            newLst = aitr.getReferenceCode("501");
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void checkXchangeLimit() {
        try {
            setMsg("");
            if (Double.parseDouble(exchgLimit) < exchgAmt) {
                setFocusId("txtXcAmt");
                throw new ApplicationException("Exchange Amount must be less than Exchange Limit " + exchgLimit);
            }
            setFocusId("ddReturnPaid");
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void changeFunction() {
        try {
            setMsg("");
            setTendName("");
            setIdProof("0");
            setIdNo("");
            setDenCnt(0);
            setDenWord("");
            setPlace("0");
            setPaidAmt(0);
            setPaidAmtWord("");
            setExchgAmt(0);

            monExchTable = new ArrayList<>();
            if (option.equals("A")) {
                btnCaption = "Save";
                setDisabled(false);
            } else if (option.equals("V")) {
                setDisabled(true);
                getDataToUpdate();
                btnCaption = "Authorize";

            } else if (option.equals("M")) {
                getDataToUpdate();
                setDisabled(false);
                btnCaption = "Update";
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        try {
            setMsg("");
            setTendName("");
            setIdProof("0");
            setIdNo("");
            setDenCnt(0);
            setDenWord("");
            setPaidAmt(0);
            setPaidAmtWord("");
            setExchgAmt(0);
            setPlace("0");
            btnCaption = "Save";
            monExchTable = new ArrayList<>();
            setDisabled(false);
            denominationTab = new LinkedList<>();
            this.denomination = "";
            this.noOfNoteCoins = "";
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void validateDeno() throws ApplicationException {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.denomination == null || this.denomination.toString().equalsIgnoreCase("")) {
            throw new ApplicationException("Please Fill Denomination ");
        }
//        if (this.denomination.toString().contains(".")) {
//            throw new ApplicationException("Denomination  Can't Contain Decimal");
//        }

        Matcher amountTxnCheck = p.matcher(this.denomination.toString());
        if (!amountTxnCheck.matches()) {
            throw new ApplicationException("Invalid Entry In Denomination.");
        }

        if (this.noOfNoteCoins == null || this.noOfNoteCoins.toString().equalsIgnoreCase("")) {
            throw new ApplicationException("Please Fill Number of Notes/Coins");
        }
        if (this.noOfNoteCoins.toString().contains(".")) {
            throw new ApplicationException("Number of Notes/Coins Can't Contain Decimal");
        }

        amountTxnCheck = p.matcher(this.noOfNoteCoins.toString());
        if (!amountTxnCheck.matches()) {
            throw new ApplicationException("Invalid Entry In Number of Notes/Coins .");
        }
        if (!checkCurrency()) {
            throw new ApplicationException("Currency " + denomination + " is not allowed !!");
        }
        if (Double.parseDouble(denomination) * Integer.parseInt(noOfNoteCoins) > exchgAmt) {
            throw new ApplicationException("Received / Paid amount can not be greater than Exchange Amount");
        }
    }

    public void addToGrid() {
        this.setMsg("");
        try {
            validateDeno();
            DenominationComparatorDesc denominationComparator = new DenominationComparatorDesc();
            DDSDenominationGrid curDenoGrid = new DDSDenominationGrid();
            if (denominationTab != null) {
                curDenoGrid.setSno(denominationTab.size() + 1);
                curDenoGrid.setDenoValue(Double.parseDouble(denomination));

                curDenoGrid.setDenoNo(Integer.parseInt(noOfNoteCoins));
                curDenoGrid.setDenoAmount(Double.parseDouble(denomination) * Integer.parseInt(noOfNoteCoins));

                if (returnPaid.equalsIgnoreCase("R")) {
                    curDenoGrid.setTy("0");
                    curDenoGrid.setTySh("Received");
                    denCnt = denCnt + Double.parseDouble(this.denomination) * Integer.parseInt(noOfNoteCoins);
                } else {
                    curDenoGrid.setTy("1");
                    curDenoGrid.setTySh("Paid");
                    paidAmt = paidAmt + Double.parseDouble(this.denomination) * Integer.parseInt(noOfNoteCoins);
                }

                if (Collections.binarySearch(denominationTab, curDenoGrid, denominationComparator) < 0) {
                    denominationTab.add(curDenoGrid);
                } else {
                    this.setMsg("You have already entered denomination for " + denomination + " , So you can only update it by selecting from grid !!!");
                }
            } else {
                denominationTab = new LinkedList<>();
                curDenoGrid.setSno(1);
                curDenoGrid.setDenoValue(Double.parseDouble(denomination));
                curDenoGrid.setDenoNo(Integer.parseInt(noOfNoteCoins));
                curDenoGrid.setDenoAmount(Double.parseDouble(denomination) * Integer.parseInt(noOfNoteCoins));
                if (returnPaid.equalsIgnoreCase("R")) {
                    curDenoGrid.setTy("0");
                    curDenoGrid.setTySh("Received");
                    denCnt = denCnt + Integer.parseInt(this.denomination) * Integer.parseInt(noOfNoteCoins);
                } else {
                    curDenoGrid.setTy("1");
                    curDenoGrid.setTySh("Paid");
                    paidAmt = paidAmt + Integer.parseInt(this.denomination) * Integer.parseInt(noOfNoteCoins);
                }
                denominationTab.add(curDenoGrid);
            }

            Collections.sort(denominationTab, denominationComparator);
            Spellar obj = new Spellar();
            setDenWord(obj.spellAmount(this.getDenCnt()));
            setPaidAmtWord(obj.spellAmount(this.getPaidAmt()));

            setDenomination("");
            setNoOfNoteCoins("");
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void save() {
        try {
            setMsg("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Pattern onlyAlphabetWitSpace = Pattern.compile("[A-Za-z ]*");

            if (this.tendName.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Fill Tenderer Name");
            }

            Matcher matcher = onlyAlphabetWitSpace.matcher(this.tendName);
            if (!matcher.matches()) {
                throw new ApplicationException("Tenderer Name Should Be Alphabets");
            }

            if (null == this.place || this.place.equalsIgnoreCase("0")) {
                throw new ApplicationException("Please Select Place");
            }

            if (this.getExchgAmt() == 0) {
                throw new ApplicationException("Exchange Amount must not be zero");
            }

            if (exchgAmt != this.getDenCnt()) {
                throw new ApplicationException("Exchange Amount must be equal to received amount");
            }

            if (exchgAmt != this.getPaidAmt()) {
                throw new ApplicationException("Exchange Amount must be equal to paid amount");
            }

            if (getDenCnt() != this.getPaidAmt()) {
                throw new ApplicationException("Received Amount must be equal to paid amount");
            }

            double gridRecdAmt = 0, gridPaidAmt = 0;
            for (DDSDenominationGrid denoObj : denominationTab) {
                if (denoObj.getTy().equals("0")) {
                    gridRecdAmt = gridRecdAmt + denoObj.getDenoAmount();
                } else {
                    gridPaidAmt = gridPaidAmt + denoObj.getDenoAmount();
                }
            }

            if (gridRecdAmt != gridPaidAmt) {
                throw new ApplicationException("Received Amount must be equal to paid amount in the table");
            }
            String rsMsg = "";
            if (option.equalsIgnoreCase("A")) {
                rsMsg = aitr.SaveMoneyExchg(this.getTendName(), this.getIdProof(), this.getIdNo(), this.getExchgAmt(), this.getPlace(), this.getOrgnBrCode(),
                        this.getUserName(), denominationTab, authFlag);
            } else if (option.equalsIgnoreCase("M")) {
                if (!this.getUserName().equals(entBy)) {
                    this.setMsg("You can not Modify Other's entry.");
                    return;
                } else {
                    rsMsg = aitr.ModifyMoneyExchg(this.getTendName(), this.getIdProof(), this.getIdNo(), this.getExchgAmt(), this.getPlace(), this.getOrgnBrCode(),
                            this.getUserName(), spNo, currentItem.getRecNo(), denominationTab);
                }

            } else if (option.equalsIgnoreCase("V")) {
                if (ftsPostRemote.isUserAuthorized(getUserName(), getOrgnBrCode())) {
                    if (this.getUserName().equals(entBy)) {
                        this.setMsg("You can not authorize your own entry.");
                        return;
                    } else {
                        rsMsg = aitr.verifyMoneyExchg(this.getUserName(), spNo);
                    }
                } else {
                    this.setMsg("You are not authorized person for verifing this detail.");
                    return;
                }
            }
            this.setMsg(rsMsg);
            setTendName("");
            setIdProof("0");
            setIdNo("");
            setDenCnt(0);
            setDenWord("");
            setPaidAmt(0);
            setPaidAmtWord("");
            setExchgAmt(0);
            btnCaption = "Save";
            this.setOption("A");
            this.setPlace("0");
            setDisabled(false);
            this.setDenomination("");
            this.setNoOfNoteCoins("");
            denominationTab = new LinkedList<>();
            monExchTable = new ArrayList<>();
        } catch (ApplicationException e) {
            setMsg(e.getMessage());
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void setDataToForm() {
        this.setMsg("");
        try {
            if (currentItem != null) {
                denCnt = 0;
                paidAmt = 0;
                this.setTendName(currentItem.getTendName());
                this.setIdProof(getDropDownCode(currentItem.getIdenProof(),idProofList));
                this.setIdNo(currentItem.getIdenNo());
                this.setPlace(getDropDownCode(currentItem.getPlace(),placeList));

                this.setExchgAmt(Double.parseDouble(currentItem.getTotVal()));
                entBy = currentItem.getEnterBy();
                spNo = currentItem.getSpNo();
                denominationTab = aitr.getDenominationDetails(currentItem.getRecNo(), ymd.format(new Date()));
                for (DDSDenominationGrid obj : denominationTab) {
                    if (obj.getTy().equalsIgnoreCase("0")) {
                        denCnt = denCnt + obj.getDenoAmount();
                    } else {
                        paidAmt = paidAmt + obj.getDenoAmount();
                    }
                }
                Spellar obj = new Spellar();
                setDenWord(obj.spellAmount(this.getDenCnt()));
                setPaidAmtWord(obj.spellAmount(getPaidAmt()));
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    private String getDropDownName(String cityCode, List<SelectItem> itemList) throws ApplicationException {
        for (SelectItem item : itemList) {
            if (cityCode.equalsIgnoreCase(item.getValue().toString())) {
                return item.getLabel();
            }
        }
        return "";
    }

    private String getDropDownCode(String name, List<SelectItem> itemList) throws ApplicationException {
        for (SelectItem item : itemList) {
            if (name.equalsIgnoreCase(item.getLabel().toString())) {
                return item.getValue().toString();
            }
        }
        return "";
    }
    
    public void getDataToUpdate() {
        this.setMsg("");
        try {
            monExchTable = new ArrayList<>();
            List resultLt = aitr.gridLoadForMoneyExchg(getOrgnBrCode(), authFlag);

            if (resultLt.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            for (int i = 0; i < resultLt.size(); i++) {
                Vector ele = (Vector) resultLt.get(i);
                MoneyExchgPojo rd = new MoneyExchgPojo();
                rd.setTendName(ele.get(0).toString());
                if (!ele.get(1).toString().equals("0")) {
                    rd.setIdenProof(getDropDownName(ele.get(1).toString(), idProofList));
                } else {
                    rd.setIdenProof("");
                }
                rd.setIdenNo(ele.get(2).toString());

                rd.setDenomination(ele.get(3).toString());
                rd.setNoOfNotes(ele.get(4).toString());
                rd.setTotVal(ele.get(5).toString());
                rd.setPlace(getDropDownName(ele.get(6).toString(), placeList));
                rd.setEnterBy(ele.get(7).toString());

                rd.setSpNo(Integer.parseInt(ele.get(8).toString()));
                rd.setRecNo(Float.parseFloat(ele.get(9).toString()));
                rd.setDt(dmy.format(ymd.parse(ele.get(10).toString())));
                monExchTable.add(rd);
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    class DenominationComparatorDesc implements Comparator<DDSDenominationGrid> {

        public int compare(DDSDenominationGrid one, DDSDenominationGrid two) {
            return two.getDenoValue().compareTo(one.getDenoValue());
        }
    }

    public void setDenoRowValues() {
        try {
            denomination = String.valueOf(currentDenominationItm.getDenoValue().longValue());
            noOfNoteCoins = String.valueOf(currentDenominationItm.getDenoNo());
            denominationTab.remove(currentDenominationItm);
            if(currentDenominationItm.getTy().equals("0"))setReturnPaid("R");
            else setReturnPaid("P");
            if (currentDenominationItm.getTy().equalsIgnoreCase("0")) {
                denCnt = denCnt - currentDenominationItm.getDenoAmount();
            } else {
                paidAmt = paidAmt - currentDenominationItm.getDenoAmount();
            }
            Spellar obj = new Spellar();
            setDenWord(obj.spellAmount(this.getDenCnt()));
            setPaidAmtWord(obj.spellAmount(getPaidAmt()));

        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public boolean checkCurrency() {
        try {
            Double value = Double.parseDouble(denomination);
            if (value == 0.5) {
                return true;
            }
            if (value.intValue() != 0 && CbsUtil.round(value - value.intValue(), 2) > 0.0) {
                return false;
            }
            for (int i = 0; i < newLst.size(); i++) {
                Vector element = (Vector) newLst.get(i);
                if (Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denomination)) {
                    return true;
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
        return false;
    }
}
