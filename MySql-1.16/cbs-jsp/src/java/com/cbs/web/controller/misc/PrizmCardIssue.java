/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dao.master.PrizmMasterPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.TransferBatchDeletionFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class PrizmCardIssue extends BaseBean {

    private String message;
    private String function;
    private String acno;
    private String acnoName;
    private String cardNo;
    private String cardExpiry;
//    private String pin;
    private String minLimit;
    private String processButtonValue;
    private boolean processVisible;
    private String confirmationMsg;
    private String enableAuthGrid = "none";
    private boolean disableAcno;
    private boolean disableCardNo;
    private boolean disableCardExpiry;
//    private boolean disablePin = true;
    private boolean disableLimit;
    private PrizmMasterPojo currentItem;
    private List<PrizmMasterPojo> unAuthList;
//    private List<SelectItem> pinList;
    private List<SelectItem> functionList;
    private TransferBatchDeletionFacadeRemote remote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public PrizmCardIssue() {
//        pinList = new ArrayList<SelectItem>();
        functionList = new ArrayList<SelectItem>();
        try {
            functionList.add(new SelectItem("A", "ADD"));
            functionList.add(new SelectItem("V", "VERIFY"));
            functionList.add(new SelectItem("M", "MODIFY"));
            functionList.add(new SelectItem("R", "RE-PIN"));
            functionList.add(new SelectItem("E", "EXPIRY"));
            functionList.add(new SelectItem("L", "LOST"));
            functionList.add(new SelectItem("S", "STOLEN"));
            functionList.add(new SelectItem("C", "A/C CLOSE"));
            functionList.add(new SelectItem("N", "RE-SEND"));

//            pinList.add(new SelectItem("Y", "FRESH PIN"));
//            pinList.add(new SelectItem("R", "RE-ISSUE-PIN"));

            remote = (TransferBatchDeletionFacadeRemote) ServiceLocator.getInstance().lookup("TransferBatchDeletionFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            resetForm();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setMessage("");
        try {
            if (this.function.equals("A")) {
                this.disableAcno = false;
                this.disableCardNo = true;
                this.disableCardExpiry = true;
//                this.disablePin = true;
                this.disableLimit = false;
                this.setAcno("");
                this.setAcnoName("");
                this.setCardNo("");
                this.setCardExpiry(getTodayDate());
//                this.setPin("Y");
                this.setMinLimit("");
                this.enableAuthGrid = "none";
                this.processButtonValue = "ADD";
                this.processVisible = false;
            } else if (this.function.equals("V")) {
                this.disableAcno = true;
                this.disableCardNo = true;
                this.disableCardExpiry = true;
//                this.disablePin = true;
                this.disableLimit = true;
                this.setAcno("");
                this.setAcnoName("");
                this.setCardNo("");
                this.setCardExpiry(getTodayDate());
//                this.setPin("Y");
                this.setMinLimit("");
                this.enableAuthGrid = "";
                this.processButtonValue = "VERIFY";
                this.processVisible = true;
                retrieveUnAuthData();
            } else if (this.function.equals("M")) {
                this.disableAcno = false;
                this.disableCardNo = false;
                this.disableCardExpiry = false;
//                this.disablePin = false;
                this.disableLimit = false;
                this.setAcno("");
                this.setAcnoName("");
                this.setCardNo("");
                this.setCardExpiry(getTodayDate());
//                this.setPin("Y");
                this.setMinLimit("");
                this.processButtonValue = "MODIFY";
                this.processVisible = false;
                this.enableAuthGrid = "none";
            } else if (this.function.equals("R") || this.function.equals("E") || this.function.equals("L")
                    || this.function.equals("S") || this.function.equals("C")) {
                this.disableAcno = false;
                this.disableCardNo = false;
                this.disableCardExpiry = false;
//                this.disablePin = true;
                this.disableLimit = true;
                this.setAcno("");
                this.setAcnoName("");
                this.setCardNo("");
                this.setCardExpiry(getTodayDate());
//                this.setPin("Y");
                this.setMinLimit("");
                if (this.function.equals("E")) {
                    this.processButtonValue = "EXPIRY";
                } else if (this.function.equals("L")) {
                    this.processButtonValue = "LOST";
                } else if (this.function.equals("S")) {
                    this.processButtonValue = "STOLEN";
                } else if (this.function.equals("C")) {
                    this.processButtonValue = "A/C CLOSE";
                } else if (this.function.equals("R")) {
                    this.processButtonValue = "RE-PIN";
                }
                this.processVisible = false;
                this.enableAuthGrid = "none";
            } else if (this.function.equals("N")) { //RE-SEND case
                this.disableAcno = false;
                this.disableCardNo = true;
                this.disableCardExpiry = true;
                this.disableLimit = false;
                this.setAcno("");
                this.setAcnoName("");
                this.setCardNo("");
                this.setCardExpiry(getTodayDate());
                this.setMinLimit("");
                this.enableAuthGrid = "none";
                this.processButtonValue = "RE-SEND";
                this.processVisible = false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurAccount() {
        this.setMessage("");
        try {
            if (this.acno == null || this.acno.equals("")) {
                this.setMessage("Please fill account no.");
                return;
            }
            if (this.acno.length() < 12) {
                this.setMessage("Please fill 12 digit account no.");
                return;
            }
            if (!getOrgnBrCode().equalsIgnoreCase(ftsRemote.getCurrentBrnCode(this.acno))) {
                this.setMessage("Please fill self branch a/c number.");
                return;
            }
            this.acnoName = ftsRemote.getCustName(this.acno);
            if (!(this.function.equals("A") || this.function.equals("V") || this.function.equals("N"))) {
                List list = remote.getPrizmAtmRegisterdAcToModify(this.acno);
                if (list.isEmpty()) {
                    this.processVisible = true;
                    this.setMessage("There is no detail for a/c no:" + this.acno);
                    return;
                }
                Vector ele = (Vector) list.get(0);
                String cbsStatus = ele.get(0).toString().trim();
                if (cbsStatus.equals("C")) {
                    this.processVisible = true;
                    this.setMessage("This is already closed A/c");
                    return;
                }
                this.setCardNo(ele.get(1).toString().trim());
                String expiryCard = ele.get(2).toString().trim();
                if (expiryCard.equals("")) {
                    expiryCard = getTodayDate();
                }
                this.setCardExpiry(expiryCard);
//                this.setPin(ele.get(2).toString().trim());
                this.setMinLimit(ele.get(3).toString().trim());
                this.setMessage("Now you can process !");
                this.disableAcno = true;
                this.processVisible = false;
            }
            if (this.function.equals("N")) { //Re-Send Case
                List list = remote.getPrizmAtmRegisterdAcToModify(this.acno);
                if (list.isEmpty()) {
                    this.processVisible = true;
                    this.setMessage("There is no detail for a/c no:" + this.acno);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.function.equals("A")) {
            this.setConfirmationMsg("Are you sure to add this a/c !");
        } else if (this.function.equals("M")) {
            this.setConfirmationMsg("Are you sure to modify this a/c !");
        } else if (this.function.equals("E")) {
            this.setConfirmationMsg("Are you sure to expire this a/c atm !");
        } else if (this.function.equals("L")) {
            this.setConfirmationMsg("Are you sure to mark this a/c atm to lost !");
        } else if (this.function.equals("S")) {
            this.setConfirmationMsg("Are you sure to mark this a/c atm to stolen !");
        } else if (this.function.equals("C")) {
            this.setConfirmationMsg("Are you sure to mark this a/c to close !");
        } else if (this.function.equals("R")) {
            this.setConfirmationMsg("Are you sure for Re-Pin !");
        } else if (this.function.equals("N")) {
            this.setConfirmationMsg("Are you sure to Re-Send !");
        }
    }

    public void processAction() {
        this.setMessage("");
        try {
            String result = "";
            if (this.function.equals("A") || this.function.equals("N")) {
                if (this.acno == null || this.acno.equals("")) {
                    this.setMessage("Please fill account no.");
                    return;
                }
                if (this.acno.length() < 12) {
                    this.setMessage("Please fill 12 digit account no.");
                    return;
                }
                if (!getOrgnBrCode().equalsIgnoreCase(ftsRemote.getCurrentBrnCode(this.acno))) {
                    this.setMessage("Please fill self branch a/c number.");
                    return;
                }
                String acNature = ftsRemote.getAccountNature(acno);
                if (!(acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                    this.setMessage("Only saving and current a/c are allowed.");
                    return;
                }
                result = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
                if (!result.equalsIgnoreCase("true")) {
                    this.setMessage(result);
                    return;
                }
                result = validateAmount();
                if (!result.equalsIgnoreCase("true")) {
                    this.setMessage(result);
                    return;
                }
                remote.addAcToPrizmAtm(this.acno, new BigDecimal(this.minLimit),
                        this.function, getUserName(), this.function);
                resetForm();
                this.setMessage("A/c has been registered successfully.");
            } else if (this.function.equals("M")) {
                if (!getOrgnBrCode().equalsIgnoreCase(ftsRemote.getCurrentBrnCode(this.acno))) {
                    this.setMessage("Please fill self branch a/c number.");
                    return;
                }
                remote.checkPrizmRegisteredAc(this.acno);
                if (!(this.cardNo == null || this.cardNo.equals(""))) {
                    Pattern p = Pattern.compile("[0-9]*");
                    Matcher m = p.matcher(this.cardNo);
                    if (m.matches() != true) {
                        this.setMessage("Please fill only numeric digits for card no.");
                        return;
                    }
                    if (this.cardNo.length() < 16) {
                        this.setMessage("Please fill 16 digit card no.");
                        return;
                    }
                }
                if (this.cardExpiry == null || this.cardExpiry.equals("")) {
                    this.setMessage("Please fill proper card expiry date at least current date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(this.cardExpiry)) {
                    this.setMessage("Please fill proper card expiry date.");
                    return;
                }
                result = validateAmount();
                if (!result.equalsIgnoreCase("true")) {
                    this.setMessage(result);
                }
                remote.modifyProcess(this.function, this.acno, this.cardNo, this.cardExpiry,
                        new BigDecimal(this.minLimit), getUserName());
                resetForm();
                this.setMessage("Data has been modified successfully.");
            } else {
                if (!getOrgnBrCode().equalsIgnoreCase(ftsRemote.getCurrentBrnCode(this.acno))) {
                    this.setMessage("Please fill self branch a/c number.");
                    return;
                }
                remote.checkPrizmRegisteredAc(this.acno);
                if (this.cardNo == null || this.cardNo.equals("")) {
                    this.setMessage("Please fill numeric 16 digit card no.");
                    return;
                }

                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(this.cardNo);
                if (m.matches() != true) {
                    this.setMessage("Please fill only numeric digits for card no.");
                    return;
                }
                if (this.cardNo.length() < 16) {
                    this.setMessage("Please fill 16 digit card no.");
                    return;
                }
                if (this.cardExpiry == null || this.cardExpiry.equals("")) {
                    this.setMessage("Please fill proper card expiry date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(this.cardExpiry)) {
                    this.setMessage("Please fill proper card expiry date.");
                    return;
                }

                if (this.function.equals("E")) {
                    if (dmy.parse(this.cardExpiry).compareTo(dmy.parse(getTodayDate())) > 0) {
                        this.setMessage("In case of Expiry, date should be less than or equal to curent date.");
                        return;
                    }
                }
                if (this.function.equals("R")) {
                    if (dmy.parse(this.cardExpiry).compareTo(dmy.parse(getTodayDate())) <= 0) {
                        this.setMessage("In case of Re-Pin, date should be greater than curent date.");
                        return;
                    }
                }
                remote.expLostStolenAndCloseProcess(this.acno, this.function, getUserName(),
                        this.cardNo, this.cardExpiry);
                resetForm();
                this.setMessage("Data has been processed successfully.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void retrieveUnAuthData() {
        unAuthList = new ArrayList<PrizmMasterPojo>();
        try {
            unAuthList = remote.retrieveUnAuthData(getOrgnBrCode(), getTodayDate());
            if (unAuthList.isEmpty()) {
                this.setMessage("There is no data to verify !");
                return;
            }
            this.setMessage("Please select a row from table to verify !");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void txnVerification() {
        this.setMessage("");
        try {
            if (currentItem == null) {
                this.setMessage("Please select a row from table to verify !");
                return;
            }
            if (currentItem.getEnterBy().equalsIgnoreCase(getUserName())) {
                this.setMessage("You can not authorize this entry !");
                return;
            }
            remote.verifyPrizmAtmRegistration(this.function, currentItem.getAcno(), getUserName());
            retrieveUnAuthData();
            this.setMessage("Data has been verified successfully.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateAmount() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            if (this.minLimit == null || this.minLimit.equals("")
                    || new BigDecimal(this.minLimit).compareTo(new BigDecimal("0")) == 0) {
                this.minLimit = "0";
            } else {
                Matcher m = p.matcher(this.minLimit);
                if (m.matches() != true) {
                    return "Please fill proper amount.";
                } else if (new BigDecimal(this.minLimit).compareTo(new BigDecimal("0")) == -1) {
                    return "Amount can not be negative.";
                } else if (this.minLimit.contains(".")) {
                    return "Please fill exact limit amount.";
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "case1";
    }

    public void resetForm() {
        try {
            this.setMessage("");
            this.setFunction("A");
            this.setAcno("");
            this.setAcnoName("");
            this.setCardNo("");
            this.setCardExpiry(getTodayDate());
//            this.setPin("Y");
            this.setMinLimit("");
            this.disableAcno = true;
            this.disableCardNo = true;
            this.disableCardExpiry = true;
//            this.disablePin = true;
            this.disableLimit = true;
            this.setProcessButtonValue("ADD");
            this.setProcessVisible(true);
            this.setConfirmationMsg("");
            this.enableAuthGrid = "none";
            unAuthList = new ArrayList<PrizmMasterPojo>();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    //Getter And Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

//    public String getPin() {
//        return pin;
//    }
//
//    public void setPin(String pin) {
//        this.pin = pin;
//    }
//
//    public List<SelectItem> getPinList() {
//        return pinList;
//    }
//
//    public void setPinList(List<SelectItem> pinList) {
//        this.pinList = pinList;
//    }
    public String getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(String minLimit) {
        this.minLimit = minLimit;
    }

    public String getProcessButtonValue() {
        return processButtonValue;
    }

    public void setProcessButtonValue(String processButtonValue) {
        this.processButtonValue = processButtonValue;
    }

    public boolean isProcessVisible() {
        return processVisible;
    }

    public void setProcessVisible(boolean processVisible) {
        this.processVisible = processVisible;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public PrizmMasterPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(PrizmMasterPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<PrizmMasterPojo> getUnAuthList() {
        return unAuthList;
    }

    public void setUnAuthList(List<PrizmMasterPojo> unAuthList) {
        this.unAuthList = unAuthList;
    }

    public String getEnableAuthGrid() {
        return enableAuthGrid;
    }

    public void setEnableAuthGrid(String enableAuthGrid) {
        this.enableAuthGrid = enableAuthGrid;
    }

//    public boolean isDisablePin() {
//        return disablePin;
//    }
//
//    public void setDisablePin(boolean disablePin) {
//        this.disablePin = disablePin;
//    }
    public boolean isDisableAcno() {
        return disableAcno;
    }

    public void setDisableAcno(boolean disableAcno) {
        this.disableAcno = disableAcno;
    }

    public boolean isDisableCardNo() {
        return disableCardNo;
    }

    public void setDisableCardNo(boolean disableCardNo) {
        this.disableCardNo = disableCardNo;
    }

    public boolean isDisableCardExpiry() {
        return disableCardExpiry;
    }

    public void setDisableCardExpiry(boolean disableCardExpiry) {
        this.disableCardExpiry = disableCardExpiry;
    }

    public boolean isDisableLimit() {
        return disableLimit;
    }

    public void setDisableLimit(boolean disableLimit) {
        this.disableLimit = disableLimit;
    }

    public String getAcnoName() {
        return acnoName;
    }

    public void setAcnoName(String acnoName) {
        this.acnoName = acnoName;
    }
}
