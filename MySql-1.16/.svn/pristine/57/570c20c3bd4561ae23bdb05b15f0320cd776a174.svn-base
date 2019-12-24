/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.sms;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.sms.MbSubscriberServicesPKTO;
import com.cbs.dto.sms.MbSubscriberServicesTO;
import com.cbs.dto.sms.MbSubscriberTabTO;
import com.cbs.entity.sms.MbSubscriberTab;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.SmsManagementDelegate;
import com.cbs.web.utils.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name = "addSubscriber")
public class AddSubscriber extends BaseBean {

    private String msg;
    private String acno;
    private String acnoType;
    private String name;
    private String mobileNo;
    private String billingAcno;
    private String acNoLen;
    private String newAcno;
    private String newBillingAcno;
    private String function;
    private String gridOperation;
    private String btnActionValue;
    private String combinedConfirmation;
    private boolean balanceInAccountChkBx;
    private boolean termDepositeMaturityChkBx;
    private boolean miniAccountStatementChkBx;
    private boolean dailyAccountStatementChkBx;
    private boolean missedCallAlert;
    private boolean cashCrChkBx;
    private boolean cashDrChkBx;
    private boolean trfCrChkBx;
    private boolean trfDrChkBx;
    private boolean clgCrChkBx;
    private boolean clgDrChkBx;
    private Float cashCrLimit = 0.0f;
    private Float cashDrLimit = 0.0f;
    private Float trfDrLimit = 0.0f;
    private Float clgCrLimit = 0.0f;
    private Float clgDrLimit = 0.0f;
    private Float trfCrLimit = 0.0f;
    private String billingAcCustName;
    private boolean cashCrLimitFlag;
    private boolean cashDrLimitFlag;
    private boolean trfCrLimitFlag;
    private boolean trfDrLimitFlag;
    private boolean clgCrLimitFlag;
    private boolean clgDrLimitFlag;
    private boolean interestLimitFlag;
    private boolean chargeLimitFlag;
    private boolean disableBtnSave;
    private Validator validator;
    private boolean delFlag;
    private boolean authFlag;
    private boolean interestChkBx;
    private boolean chargeChkBx;
    private String missedCallActive;
    private Float interestLimit = 0.0f;
    private Float chargeLimit = 0.0f;
    private MbSubscriberTabTO currentSubscriber;
    private List<MbSubscriberTabTO> gridDetail;
    Date date = new Date();
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private MessageDetailBeanRemote msgDetailRemote = null;
    private String[]billingNatures;
    private String natureStr = "";

    public AddSubscriber() {
        try {
            validator = new Validator();
            disableBtnSave = true;
            msg = "";
            name = "";
            billingAcCustName = "";
            this.gridOperation = "";
            this.delFlag = false;
            this.authFlag = false;
            this.function = "ADD";
            this.btnActionValue = "ADD";
            this.combinedConfirmation = "";
            gridDetail = new ArrayList<MbSubscriberTabTO>();
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            msgDetailRemote = (MessageDetailBeanRemote) ServiceLocator.getInstance().lookup("MessageDetailBean");
            this.setAcNoLen(getAcNoLength());

            int code = ftsRemote.getCodeForReportName("MISSED-CALL-BALANCE-ALERT");
            if (code == 1) {
                missedCallActive = "";
            } else {
                missedCallActive = "none";
            }
            natureStr = ftsRemote.getValueFromCbsparameterInfo("SMS-BILLING-NATURE");
            billingNatures = natureStr.split(",");
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void processFunctionAction() {
        this.setMsg("");
        try {
            refresh();
            SmsManagementDelegate smsManagementDelegate = new SmsManagementDelegate();
            if (function.equals("DELETE") || function.equals("AUTHORIZE")) {
                //Get data according to the del and auth function.
                gridDetail = new ArrayList<MbSubscriberTabTO>();
                List list = new ArrayList();
                if (function.equals("DELETE")) {
                    gridOperation = "Delete";
                    delFlag = true;
                    authFlag = false;
                    list = smsManagementDelegate.getAllAccountByAuthAndAuthStatus("N", "A");
                } else if (function.equals("AUTHORIZE")) {
                    gridOperation = "Authorize";
                    delFlag = false;
                    authFlag = true;
                    list = smsManagementDelegate.getAllAccountToVerify(getOrgnBrCode());
                }
                if (list.isEmpty()) {
                    this.setMsg("There is no data.");
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    MbSubscriberTabTO to = new MbSubscriberTabTO();
                    MbSubscriberTab listObj = (MbSubscriberTab) list.get(i);
                    to.setAcno(listObj.getAcno());
                    to.setAcnoType(listObj.getAcnoType());
                    to.setMobileNo(listObj.getMobileNo());
                    to.setStatus(listObj.getStatus());
                    to.setBillDeductionAcno(listObj.getBillDeductionAcno());
                    to.setCashCrLimit(listObj.getCashCrLimit());
                    to.setCashDrLimit(listObj.getCashDrLimit());
                    to.setTrfCrLimit(listObj.getTrfCrLimit());
                    to.setTrfDrLimit(listObj.getTrfDrLimit());
                    to.setClgCrLimit(listObj.getClgCrLimit());
                    to.setClgDrLimit(listObj.getClgDrLimit());
                    to.setPinNo(listObj.getPinNo());
                    to.setCreatedDate(listObj.getCreatedDate());
                    to.setEnterBy(listObj.getEnterBy());
                    to.setAuth(listObj.getAuth());
                    to.setAuthBy(listObj.getAuthBy());
                    to.setAuthStatus(listObj.getAuthStatus());
                    to.setInterestLimit(listObj.getInterestLimit());
                    to.setChargeLimit(listObj.getChargeLimit());
                    if(listObj.getEnterBy().equalsIgnoreCase(listObj.getUpdateBy())){
                        to.setUpdateBy("");
                    }else{
                        to.setUpdateBy(listObj.getUpdateBy());
                    }
                    to.setUpdateDt(listObj.getUpdateDt());

                    gridDetail.add(to);
                }
                this.setMsg("Please select a row from table for desired action.");
            } else {
                this.setMsg("Please fill your branch account no.");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public String checkSubscriberDetails(String acno) {
        String msg = "";
        try {
            SmsManagementDelegate smsManagementDelegate = new SmsManagementDelegate();
            msg = smsManagementDelegate.checkSubscriberDetails(acno);
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
        return msg;
    }

    public void saveSubscriberDetails(String function) {
        msg = validation();
        MbSubscriberTabTO mbSubscriberTabTO = new MbSubscriberTabTO();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                List<MbSubscriberServicesTO> referenceList = new ArrayList<MbSubscriberServicesTO>();
                List<MbSubscriberServicesTO> onRequestList = new ArrayList<MbSubscriberServicesTO>();
                SmsManagementDelegate smsManagementDelegate = new SmsManagementDelegate();

                mbSubscriberTabTO.setAcno(newAcno);
                mbSubscriberTabTO.setAcnoType(acnoType);
                mbSubscriberTabTO.setBillDeductionAcno(newBillingAcno);
                mbSubscriberTabTO.setAuth("N");
                mbSubscriberTabTO.setAuthBy("");
                mbSubscriberTabTO.setAuthStatus(function);

                mbSubscriberTabTO.setStatus("1");
                mbSubscriberTabTO.setCashCrLimit(cashCrLimit.doubleValue());
                mbSubscriberTabTO.setCashDrLimit(cashDrLimit.doubleValue());
                mbSubscriberTabTO.setTrfCrLimit(trfCrLimit.doubleValue());
                mbSubscriberTabTO.setTrfDrLimit(trfDrLimit.doubleValue());
                mbSubscriberTabTO.setClgCrLimit(clgCrLimit.doubleValue());
                mbSubscriberTabTO.setClgDrLimit(clgDrLimit.doubleValue());
                mbSubscriberTabTO.setInterestLimit(interestLimit.doubleValue());
                mbSubscriberTabTO.setChargeLimit(chargeLimit.doubleValue());

                String modeValue = "";
                if (function.equals("A")) {
                    mbSubscriberTabTO.setEnterBy(getUserName());
                    mbSubscriberTabTO.setCreatedDate(date);
                    mbSubscriberTabTO.setUpdateBy(getUserName());
                    mbSubscriberTabTO.setUpdateDt(date);

                    addPinChangeService(referenceList);
                    modeValue = "SAVE";
                } else {
                    mbSubscriberTabTO.setUpdateBy(getUserName());
                    mbSubscriberTabTO.setUpdateDt(date);
                    modeValue = "UPDATE";
                }

                if (balanceInAccountChkBx == true) {
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("1");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                    onRequestList.add(mbSubscriberServicesTO);
                }
//                    if (termDepositeMaturityChkBx == true) {
//                        MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
//                        MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();
//
//                        mbSubscriberServicesPKTO.setVar("2");
//                        mbSubscriberServicesPKTO.setAcno(acno);
//                        mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
//                        referenceList.add(mbSubscriberServicesTO);
//                    }
                if (miniAccountStatementChkBx == true) {
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("3");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                    onRequestList.add(mbSubscriberServicesTO);
                }
//                    if (dailyAccountStatementChkBx == true) {
//                        MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
//                        MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();
//
//                        mbSubscriberServicesPKTO.setVar("4");
//                        mbSubscriberServicesPKTO.setAcno(acno);
//                        mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
//                        referenceList.add(mbSubscriberServicesTO);
//                    }
                if (cashCrChkBx == true) {
                    cashCrLimitFlag = false;
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("5");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }
                if (cashDrChkBx == true) {
                    cashDrLimitFlag = false;
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("6");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }
                if (trfCrChkBx == true) {
                    trfCrLimitFlag = false;
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("7");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }
                if (trfDrChkBx == true) {
                    trfDrLimitFlag = false;
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("8");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }
                if (clgCrChkBx == true) {
                    clgCrLimitFlag = false;
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("9");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }
                if (clgDrChkBx == true) {
                    clgDrLimitFlag = false;
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();
                    mbSubscriberServicesPKTO.setVar("10");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }
                if (interestChkBx == true) {
                    interestLimitFlag = false;
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("16");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);

                    mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("17");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }
                if (chargeChkBx == true) {
                    chargeLimitFlag = false;
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("18");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);

                    mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("19");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }

                if (missedCallAlert == true) {
                    MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
                    MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

                    mbSubscriberServicesPKTO.setVar("20");
                    mbSubscriberServicesPKTO.setAcno(newAcno);
                    mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
                    referenceList.add(mbSubscriberServicesTO);
                }

                mobileNo = "+91" + mobileNo.trim();
                mbSubscriberTabTO.setMobileNo(mobileNo);
                String pin = Integer.toString(Util.fourDigitRandomNumber());
                String pinChkMsg = smsManagementDelegate.isExistMobileAcCodeAndPin(mobileNo.substring(1),
                        acno.substring(2, 4), pin);
                if (pinChkMsg.equalsIgnoreCase("true")) {
                    pin = Integer.toString(Util.fourDigitRandomNumber());
                }
                mbSubscriberTabTO.setPinNo(pin);

                String message = smsManagementDelegate.saveUpdateSubscriberAndServicesDetails(newAcno, mbSubscriberTabTO,
                        referenceList, onRequestList, pin, mobileNo, modeValue);

                if (message.equalsIgnoreCase("true") && function.equals("A")) {
                    this.setMsg("Account has been registered successfully !");
                } else if (message.equalsIgnoreCase("true") && function.equals("E")) {
                    this.setMsg("Account has been updated successfully !");
                } else {
                    this.setMsg(msg);
                }
                refresh();
            } catch (Exception e) {
                this.setMsg(e.getMessage());
            }
        }
    }

    public void activateOrDeactivate(String function) {
        this.setMsg("");
        try {
            SmsManagementDelegate delegate = new SmsManagementDelegate();
            String msg = delegate.activateOrDeactivate(newAcno, function, getUserName());
            if (msg.equalsIgnoreCase("true") && function.equals("T")) {
                this.setMsg("Account has been activated successfully.");
                refresh();
            } else if (msg.equalsIgnoreCase("true") && function.equals("D")) {
                this.setMsg("Account has been de-activated successfully.");
                refresh();
            } else {
                this.setMsg(msg);
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMsg("");
        try {
            SmsManagementDelegate delegate = new SmsManagementDelegate();
            if (function.equalsIgnoreCase("AUTHORIZE")) {
                if (currentSubscriber.getUpdateBy().equalsIgnoreCase(getUserName())) {
                    this.setMsg("You can not authorize your own entry.");
                    return;
                }
            }
            String msg = delegate.deleteOrAuthorize(currentSubscriber.getAcno(), function, getUserName());
            if (!msg.equalsIgnoreCase("true")) {
                this.setMsg(msg);
                return;
            }
            gridDetail.remove(currentSubscriber);
            if (msg.equalsIgnoreCase("true") && function.equalsIgnoreCase("AUTHORIZE")) {
                this.setMsg("Account has been authorized successfully.");
            } else if (msg.equalsIgnoreCase("true") && function.equalsIgnoreCase("DELETE")) {
                this.setMsg("Account has been deleted successfully.");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void addPinChangeService(List<MbSubscriberServicesTO> referenceList) {
        try {
            MbSubscriberServicesTO mbSubscriberServicesTO = new MbSubscriberServicesTO();
            MbSubscriberServicesPKTO mbSubscriberServicesPKTO = new MbSubscriberServicesPKTO();

            mbSubscriberServicesPKTO.setVar("0");
            mbSubscriberServicesPKTO.setAcno(newAcno);
            mbSubscriberServicesTO.setMbSubscriberServicesPKTO(mbSubscriberServicesPKTO);
            referenceList.add(mbSubscriberServicesTO);
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void findNameByAccountNumber() {
        this.setMsg("");
        try {
            this.newAcno = "";
            if (acno == null || acno.equalsIgnoreCase("")) {
                msg = "Account number should not be blank !";
                return;
            }
            if (!(this.acno.length() == 12 || (this.acno.length() == Integer.parseInt(getAcNoLen())))) {
                this.setMsg("Please fill proper Account Number.");
                return;
            }
            this.newAcno = ftsRemote.getNewAccountNumber(this.acno);
            if (!(newAcno.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {
                msg = "Please fill your branch account number !";
                return;
            }
            String result = ftsRemote.ftsAcnoValidate(newAcno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                msg = result;
                return;
            }

            this.combinedConfirmation = "";
            SmsManagementDelegate delegate = new SmsManagementDelegate();
            String checkMessage = "";
            if (function.equals("ADD")) {
                checkMessage = delegate.checkSubscriberDetails(newAcno);
                if (checkMessage.equalsIgnoreCase("true")) {
                    this.setMsg("This account number is already registered !");
                    disableBtnSave = true;
                    return;
                }
                name = delegate.getNameByAcno(newAcno);
                if (name.equalsIgnoreCase("Account number does not exist !")) {
                    disableBtnSave = true;
                    return;
                }
                disableBtnSave = false;
                btnActionValue = "ADD";
            } else if (function.equals("EDIT")) {
                MbSubscriberTab entity = delegate.checkAccountByAuthAndAuthStatus("Y", "V", newAcno);
                if (entity == null) {
                    this.setMsg("This account is not editable.");
                    disableBtnSave = true;
                    return;
                }
                disableBtnSave = false;
                btnActionValue = "EDIT";
                setSubscriberDetailsOnPage(this.newAcno);
            } else if (function.equals("ACTIVATE")) {
                MbSubscriberTab entity = delegate.checkAccountByAuthAndAuthStatus("Y", "D", newAcno);
                if (entity == null) {
                    this.setMsg("This account can not be activate.");
                    disableBtnSave = true;
                    return;
                }
                disableBtnSave = false;
                btnActionValue = "ACTIVATE";
                setSubscriberDetailsOnPage(this.newAcno);
            } else if (function.equals("DE-ACTIVATE")) {
                MbSubscriberTab entity = delegate.checkAccountByAuthAndAuthStatus("Y", "V", newAcno);
                if (entity == null) {
                    this.setMsg("This account can not be de-activate.");
                    disableBtnSave = true;
                    return;
                }
                disableBtnSave = false;
                btnActionValue = "DE-ACTIVATE";
                setSubscriberDetailsOnPage(this.newAcno);
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void setSubscriberDetailsOnPage(String accountNo) {
        try {
            SmsManagementDelegate delegate = new SmsManagementDelegate();
            MbSubscriberTabTO mbSubscriberTabTO = delegate.getSubscriberDetails(accountNo);
            name = delegate.getNameByAcno(accountNo);
            this.setAcnoType(mbSubscriberTabTO.getAcnoType());
            if (mbSubscriberTabTO.getBillDeductionAcno() != null || (!mbSubscriberTabTO.getBillDeductionAcno().equalsIgnoreCase(""))) {
                setBillingAcno(mbSubscriberTabTO.getBillDeductionAcno());
                setNewBillingAcno(mbSubscriberTabTO.getBillDeductionAcno());
            } else {
                this.setBillingAcno("");
                this.setNewBillingAcno("");
            }
            billingAcCustName = delegate.getNameByAcno(newBillingAcno);
            if (mbSubscriberTabTO.getMobileNo() != null || (!mbSubscriberTabTO.getMobileNo().equalsIgnoreCase(""))) {
                setMobileNo(mbSubscriberTabTO.getMobileNo().substring(3));
            } else {
                this.setMobileNo("");
            }
            setCashCrLimit(new Float(mbSubscriberTabTO.getCashCrLimit()));
            setCashDrLimit(new Float(mbSubscriberTabTO.getCashDrLimit()));
            setClgCrLimit(new Float(mbSubscriberTabTO.getClgCrLimit()));
            setClgDrLimit(new Float(mbSubscriberTabTO.getClgDrLimit()));
            setTrfCrLimit(new Float(mbSubscriberTabTO.getTrfCrLimit()));
            setTrfDrLimit(new Float(mbSubscriberTabTO.getTrfDrLimit()));
            setInterestLimit(new Float(mbSubscriberTabTO.getInterestLimit()));
            setChargeLimit(new Float(mbSubscriberTabTO.getChargeLimit()));

            List<MbSubscriberServicesTO> resultList = delegate.getSubsCriberServices(newAcno);
            for (MbSubscriberServicesTO entityList : resultList) {
                if (entityList.getMbSubscriberServicesPKTO().getServices() != null || (!entityList.getMbSubscriberServicesPKTO().getServices().toString().equalsIgnoreCase(""))) {
                    Short services = entityList.getMbSubscriberServicesPKTO().getServices();
                    if (services == 1) {
                        setBalanceInAccountChkBx(true);
                    }
                    if (services == 2) {
                        setTermDepositeMaturityChkBx(true);
                    }
                    if (services == 3) {
                        setMiniAccountStatementChkBx(true);
                    }
                    if (services == 4) {
                        setDailyAccountStatementChkBx(true);
                    }
                    if (services == 5) {
                        setCashCrChkBx(true);
                    }
                    if (services == 6) {
                        setCashDrChkBx(true);
                    }
                    if (services == 7) {
                        setTrfCrChkBx(true);
                    }
                    if (services == 8) {
                        setTrfDrChkBx(true);
                    }
                    if (services == 9) {
                        setClgCrChkBx(true);
                    }
                    if (services == 10) {
                        setClgDrChkBx(true);
                    }
                    if (services == 16) {
                        setInterestChkBx(true);
                    }
                    if (services == 18) {
                        setChargeChkBx(true);
                    }
                    if (services == 20) {
                        setMissedCallAlert(true);
                    }
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void processCombinedMessage() {
        this.setMsg("");
        this.setCombinedConfirmation("");
        try {
            if (function.equalsIgnoreCase("ADD")) {
                this.setCombinedConfirmation("Are you sure to register this A/c.");
            } else if (function.equalsIgnoreCase("EDIT")) {
                this.setCombinedConfirmation("Are you sure to edit this A/c.");
            } else if (function.equalsIgnoreCase("ACTIVATE")) {
                this.setCombinedConfirmation("Are you sure to activate this A/c.");
            } else if (function.equalsIgnoreCase("DE-ACTIVATE")) {
                this.setCombinedConfirmation("Are you sure to de-activate this A/c.");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void processCombinedFunction() {
        this.setMsg("");
        try {
            if (function.equalsIgnoreCase("ADD")) {
                saveSubscriberDetails("A");
            } else if (function.equalsIgnoreCase("EDIT")) {
                saveSubscriberDetails("E");
            } else if (function.equalsIgnoreCase("ACTIVATE")) {
                activateOrDeactivate("T");
            } else if (function.equalsIgnoreCase("DE-ACTIVATE")) {
                activateOrDeactivate("D");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    private boolean isAccountAllowedForBilling(String nature){
        for (int i = 0; i < billingNatures.length; i++) {
            if(nature.equalsIgnoreCase(billingNatures[i])) return true;
        }
        return false;
    }
    
    public void findBillingAcNameByAccountNumber() {
        this.setMsg("");
        try {
            this.newBillingAcno = "";
            if (billingAcno == null || billingAcno.equalsIgnoreCase("")) {
                msg = " Billing A/C number can not be blank !";
                return;
            }
            if (!(this.billingAcno.length() == 12 || (this.billingAcno.length() == Integer.parseInt(getAcNoLen())))) {
                this.setMsg("Please fill proper Account Number.");
                return;
            }
            this.newBillingAcno = ftsRemote.getNewAccountNumber(this.billingAcno);

//            if (!(newBillingAcno.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {
//                msg = "Please fill your branch account number !";
//                return;
//            }
            String result = ftsRemote.ftsAcnoValidate(newBillingAcno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                msg = result;
                return;
            }
            String actNature = ftsRemote.getAccountNature(this.newBillingAcno);
            if (!isAccountAllowedForBilling(actNature)) {
                
                msg = "Only " + natureStr + " nature is allowed for sms charges in Billing A/c Number.";
                return;
            }
            String acnoCustomerId = msgDetailRemote.getCustomerIdByAccountNo(newAcno);
            String billingAcnoCustomerId = msgDetailRemote.getCustomerIdByAccountNo(newBillingAcno);
            if (!acnoCustomerId.equalsIgnoreCase(billingAcnoCustomerId)) {
                msg = "Billing A/c should be of same customer id as of a/c number.";
                return;
            }

            SmsManagementDelegate smsManagementDelegate = new SmsManagementDelegate();
            billingAcCustName = smsManagementDelegate.getNameByAcno(newBillingAcno);
            if (billingAcCustName.equalsIgnoreCase("Account Holder name does not exist")) {
                disableBtnSave = true;
            } else {
                disableBtnSave = false;
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }

    }

    public void refreshOnAccFunction() {
        mobileNo = "";
        billingAcno = "";
        newBillingAcno = "";
        balanceInAccountChkBx = false;
        termDepositeMaturityChkBx = false;
        miniAccountStatementChkBx = false;
        dailyAccountStatementChkBx = false;
        missedCallAlert = false;
        interestChkBx = false;
        chargeChkBx = false;
        cashCrChkBx = false;
        cashDrChkBx = false;
        trfCrChkBx = false;
        trfDrChkBx = false;
        clgCrChkBx = false;
        clgDrChkBx = false;
        cashCrLimit = 0.0f;
        cashDrLimit = 0.0f;
        trfDrLimit = 0.0f;
        clgCrLimit = 0.0f;
        clgDrLimit = 0.0f;
        trfCrLimit = 0.0f;
        interestLimit = 0.0f;
        chargeLimit = 0.0f;
        name = "";
        billingAcCustName = "";
        this.gridOperation = "";
        this.delFlag = false;
        this.authFlag = false;
        gridDetail = new ArrayList<MbSubscriberTabTO>();
    }

    public void refresh() {
        acno = "";
        newAcno = "";
        mobileNo = "";
        billingAcno = "";
        newBillingAcno = "";
        balanceInAccountChkBx = false;
        termDepositeMaturityChkBx = false;
        miniAccountStatementChkBx = false;
        dailyAccountStatementChkBx = false;
        missedCallAlert = false;
        interestChkBx = false;
        chargeChkBx = false;
        cashCrChkBx = false;
        cashDrChkBx = false;
        trfCrChkBx = false;
        trfDrChkBx = false;
        clgCrChkBx = false;
        clgDrChkBx = false;
        cashCrLimit = 0.0f;
        cashDrLimit = 0.0f;
        trfDrLimit = 0.0f;
        clgCrLimit = 0.0f;
        clgDrLimit = 0.0f;
        trfCrLimit = 0.0f;
        interestLimit = 0.0f;
        chargeLimit = 0.0f;
        name = "";
        billingAcCustName = "";
        disableBtnSave = true;
        this.gridOperation = "";
        this.delFlag = false;
        this.authFlag = false;
        gridDetail = new ArrayList<MbSubscriberTabTO>();
    }

    public void checkFloatValues() {
        if (cashCrChkBx && cashCrLimit == null) {
            cashCrLimit = 0.0f;
        }
        if (cashDrChkBx && cashDrLimit == null) {
            cashDrLimit = 0.0f;
        }
        if (clgCrChkBx && clgCrLimit == null) {
            clgCrLimit = 0.0f;
        }
        if (clgDrChkBx && clgDrLimit == null) {
            clgDrLimit = 0.0f;
        }
        if (trfCrChkBx && trfCrLimit == null) {
            trfCrLimit = 0.0f;
        }
        if (trfDrChkBx && trfDrLimit == null) {
            trfDrLimit = 0.0f;
        }
        if (interestChkBx && interestLimit == null) {
            interestLimit = 0.0f;
        }
        if (chargeChkBx && chargeLimit == null) {
            chargeLimit = 0.0f;
        }
    }

    public void uncheckedOperation() {
        if (!cashCrChkBx) {
            cashCrLimit = 0.0f;
        }
        if (!cashDrChkBx) {
            cashDrLimit = 0.0f;
        }
        if (!clgCrChkBx) {
            clgCrLimit = 0.0f;
        }
        if (!clgDrChkBx) {
            clgDrLimit = 0.0f;
        }
        if (!trfCrChkBx) {
            trfCrLimit = 0.0f;
        }
        if (!trfDrChkBx) {
            trfDrLimit = 0.0f;
        }
        if (!interestChkBx) {
            interestLimit = 0.0f;
        }
        if (!chargeChkBx) {
            chargeLimit = 0.0f;
        }
    }

    public void refreshPage() {
        msg = "";
        function = "ADD";
        btnActionValue = "ADD";
        this.combinedConfirmation = "";
        refresh();
    }

    public String validation() {
        try {
            if (acno == null || acno.equalsIgnoreCase("")) {
                return "Account number should not be blank !";
            }
            if (!(this.acno.length() == 12 || (this.acno.length() == Integer.parseInt(getAcNoLen())))) {
                return "Please fill proper Account Number.";
            }
            if (!validator.validateStringAll(acno)) {
                return "Invalid Account No.";
            }
            if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                return "CBS account number not found!";
            }
            if (!(newAcno.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {
                return "Please fill your branch account number !";
            }
            String result = ftsRemote.ftsAcnoValidate(newAcno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                return result;
            }
            if (billingAcno == null || billingAcno.equalsIgnoreCase("")) {
                return "Billing account number should not be blank !";
            }
            if (!validator.validateStringAll(billingAcno)) {
                return "Invalid Billing Account no.";
            }
            if (!(this.billingAcno.length() == 12 || (this.billingAcno.length() == Integer.parseInt(getAcNoLen())))) {
                return "Please fill proper Account Number.";
            }
            if (newBillingAcno == null || newBillingAcno.equalsIgnoreCase("")) {
                return "CBS billing account number not found!";
            }
//            if (!(newBillingAcno.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {
//                return "Please fill your branch account number !";
//            }
            result = ftsRemote.ftsAcnoValidate(newBillingAcno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                return result;
            }
            String actNature = ftsRemote.getAccountNature(this.newBillingAcno);
            if (!isAccountAllowedForBilling(actNature)){
                return "Only " + natureStr +" nature is allowed for sms charges in Billing A/c Number.";
            }
            String acnoCustomerId = msgDetailRemote.getCustomerIdByAccountNo(newAcno);
            String billingAcnoCustomerId = msgDetailRemote.getCustomerIdByAccountNo(newBillingAcno);
            if (!acnoCustomerId.equalsIgnoreCase(billingAcnoCustomerId)) {
                return "Billing A/c should be of same customer id as of a/c number.";
            }
            if (mobileNo.equalsIgnoreCase("")) {
                return "Please specify Mobile no.";
            }
            if (!validator.validateMobile(mobileNo)) {
                return "Mobile no. is not valid !";
            }
            if (mobileNo.length() < 10) {
                return "Please fill proper Mobile Number !";
            }
            if (!validator.validateDoubleAll(String.valueOf(cashCrLimit))) {
                return "Please specify valid Cash Cr. limit !";
            }
            if (!validator.validateDoubleAll(String.valueOf(cashDrLimit))) {
                return "Please specify valid Cash Dr. limit !";
            }
            if (!validator.validateDoubleAll(String.valueOf(trfDrLimit))) {
                return "Please specify valid Trf. Dr. limit !";
            }
            if (!validator.validateDoubleAll(String.valueOf(clgCrLimit))) {
                return "Please specify valid Clg. cr. limit !";
            }
            if (!validator.validateDoubleAll(String.valueOf(clgDrLimit))) {
                return "Please specify valid Clg. Dr. limit !";
            }
            if (!validator.validateDoubleAll(String.valueOf(trfCrLimit))) {
                return "Please specify valid Trf. cr. limit !";
            }
            if (!validator.validateDoubleAll(String.valueOf(interestLimit))) {
                return "Please specify valid Interest limit !";
            }
            if (!validator.validateDoubleAll(String.valueOf(chargeLimit))) {
                return "Please specify valid Charge limit !";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "ok";
    }

    public String onExitFromAddSubscriber() {
        refreshPage();
        return "/index.jsp?faces-redirect=true";
    }

    public String exitForm() {
        refreshPage();
        return "case1";
    }

    public String getBtnActionValue() {
        return btnActionValue;
    }

    public void setBtnActionValue(String btnActionValue) {
        this.btnActionValue = btnActionValue;
    }

    public String getGridOperation() {
        return gridOperation;
    }

    public void setGridOperation(String gridOperation) {
        this.gridOperation = gridOperation;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(boolean authFlag) {
        this.authFlag = authFlag;
    }

    public MbSubscriberTabTO getCurrentSubscriber() {
        return currentSubscriber;
    }

    public void setCurrentSubscriber(MbSubscriberTabTO currentSubscriber) {
        this.currentSubscriber = currentSubscriber;
    }

    public List<MbSubscriberTabTO> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<MbSubscriberTabTO> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getAcnoType() {
        return acnoType;
    }

    public void setAcnoType(String acnoType) {
        this.acnoType = acnoType;
    }

    public boolean isDisableBtnSave() {
        return disableBtnSave;
    }

    public void setDisableBtnSave(boolean disableBtnSave) {
        this.disableBtnSave = disableBtnSave;
    }

    public boolean isCashCrLimitFlag() {
        return cashCrLimitFlag;
    }

    public void setCashCrLimitFlag(boolean cashCrLimitFlag) {
        this.cashCrLimitFlag = cashCrLimitFlag;
    }

    public boolean isCashDrLimitFlag() {
        return cashDrLimitFlag;
    }

    public void setCashDrLimitFlag(boolean cashDrLimitFlag) {
        this.cashDrLimitFlag = cashDrLimitFlag;
    }

    public boolean isClgCrLimitFlag() {
        return clgCrLimitFlag;
    }

    public void setClgCrLimitFlag(boolean clgCrLimitFlag) {
        this.clgCrLimitFlag = clgCrLimitFlag;
    }

    public boolean isClgDrLimitFlag() {
        return clgDrLimitFlag;
    }

    public void setClgDrLimitFlag(boolean clgDrLimitFlag) {
        this.clgDrLimitFlag = clgDrLimitFlag;
    }

    public boolean isTrfCrLimitFlag() {
        return trfCrLimitFlag;
    }

    public void setTrfCrLimitFlag(boolean trfCrLimitFlag) {
        this.trfCrLimitFlag = trfCrLimitFlag;
    }

    public boolean isTrfDrLimitFlag() {
        return trfDrLimitFlag;
    }

    public void setTrfDrLimitFlag(boolean trfDrLimitFlag) {
        this.trfDrLimitFlag = trfDrLimitFlag;
    }

    public String getBillingAcCustName() {
        return billingAcCustName;
    }

    public void setBillingAcCustName(String billingAcCustName) {
        this.billingAcCustName = billingAcCustName;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBillingAcno() {
        return billingAcno;
    }

    public void setBillingAcno(String billingAcno) {
        this.billingAcno = billingAcno;
    }

    public Float getCashCrLimit() {
        return cashCrLimit;
    }

    public void setCashCrLimit(Float cashCrLimit) {
        this.cashCrLimit = cashCrLimit;
    }

    public Float getCashDrLimit() {
        return cashDrLimit;
    }

    public void setCashDrLimit(Float cashDrLimit) {
        this.cashDrLimit = cashDrLimit;
    }

    public Float getClgCrLimit() {
        return clgCrLimit;
    }

    public void setClgCrLimit(Float clgCrLimit) {
        this.clgCrLimit = clgCrLimit;
    }

    public Float getTrfCrLimit() {
        return trfCrLimit;
    }

    public void setTrfCrLimit(Float trfCrLimit) {
        this.trfCrLimit = trfCrLimit;
    }

    public Float getClgDrLimit() {
        return clgDrLimit;
    }

    public void setClgDrLimit(Float clgDrLimit) {
        this.clgDrLimit = clgDrLimit;
    }

    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }

    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBalanceInAccountChkBx() {
        return balanceInAccountChkBx;
    }

    public void setBalanceInAccountChkBx(boolean balanceInAccountChkBx) {
        this.balanceInAccountChkBx = balanceInAccountChkBx;
    }

    public boolean isCashCrChkBx() {
        return cashCrChkBx;
    }

    public void setCashCrChkBx(boolean cashCrChkBx) {
        this.cashCrChkBx = cashCrChkBx;
    }

    public boolean isCashDrChkBx() {
        return cashDrChkBx;
    }

    public void setCashDrChkBx(boolean cashDrChkBx) {
        this.cashDrChkBx = cashDrChkBx;
    }

    public boolean isClgCrChkBx() {
        return clgCrChkBx;
    }

    public void setClgCrChkBx(boolean clgCrChkBx) {
        this.clgCrChkBx = clgCrChkBx;
    }

    public boolean isClgDrChkBx() {
        return clgDrChkBx;
    }

    public void setClgDrChkBx(boolean clgDrChkBx) {
        this.clgDrChkBx = clgDrChkBx;
    }

    public boolean isDailyAccountStatementChkBx() {
        return dailyAccountStatementChkBx;
    }

    public void setDailyAccountStatementChkBx(boolean dailyAccountStatementChkBx) {
        this.dailyAccountStatementChkBx = dailyAccountStatementChkBx;
    }

    public boolean isMiniAccountStatementChkBx() {
        return miniAccountStatementChkBx;
    }

    public void setMiniAccountStatementChkBx(boolean miniAccountStatementChkBx) {
        this.miniAccountStatementChkBx = miniAccountStatementChkBx;
    }

    public boolean isTermDepositeMaturityChkBx() {
        return termDepositeMaturityChkBx;
    }

    public void setTermDepositeMaturityChkBx(boolean termDepositeMaturityChkBx) {
        this.termDepositeMaturityChkBx = termDepositeMaturityChkBx;
    }

    public boolean isTrfCrChkBx() {
        return trfCrChkBx;
    }

    public void setTrfCrChkBx(boolean trfCrChkBx) {
        this.trfCrChkBx = trfCrChkBx;
    }

    public boolean isTrfDrChkBx() {
        return trfDrChkBx;
    }

    public void setTrfDrChkBx(boolean trfDrChkBx) {
        this.trfDrChkBx = trfDrChkBx;
    }

    public Float getTrfDrLimit() {
        return trfDrLimit;
    }

    public void setTrfDrLimit(Float trfDrLimit) {
        this.trfDrLimit = trfDrLimit;
    }

    public String getCombinedConfirmation() {
        return combinedConfirmation;
    }

    public void setCombinedConfirmation(String combinedConfirmation) {
        this.combinedConfirmation = combinedConfirmation;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isInterestChkBx() {
        return interestChkBx;
    }

    public void setInterestChkBx(boolean interestChkBx) {
        this.interestChkBx = interestChkBx;
    }

    public boolean isChargeChkBx() {
        return chargeChkBx;
    }

    public void setChargeChkBx(boolean chargeChkBx) {
        this.chargeChkBx = chargeChkBx;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public FtsPostingMgmtFacadeRemote getFtsRemote() {
        return ftsRemote;
    }

    public void setFtsRemote(FtsPostingMgmtFacadeRemote ftsRemote) {
        this.ftsRemote = ftsRemote;
    }

    public Float getInterestLimit() {
        return interestLimit;
    }

    public void setInterestLimit(Float interestLimit) {
        this.interestLimit = interestLimit;
    }

    public Float getChargeLimit() {
        return chargeLimit;
    }

    public void setChargeLimit(Float chargeLimit) {
        this.chargeLimit = chargeLimit;
    }

    public boolean isInterestLimitFlag() {
        return interestLimitFlag;
    }

    public void setInterestLimitFlag(boolean interestLimitFlag) {
        this.interestLimitFlag = interestLimitFlag;
    }

    public boolean isChargeLimitFlag() {
        return chargeLimitFlag;
    }

    public void setChargeLimitFlag(boolean chargeLimitFlag) {
        this.chargeLimitFlag = chargeLimitFlag;
    }

    public boolean isMissedCallAlert() {
        return missedCallAlert;
    }

    public void setMissedCallAlert(boolean missedCallAlert) {
        this.missedCallAlert = missedCallAlert;
    }

    public String getMissedCallActive() {
        return missedCallActive;
    }

    public void setMissedCallActive(String missedCallActive) {
        this.missedCallActive = missedCallActive;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getNewBillingAcno() {
        return newBillingAcno;
    }

    public void setNewBillingAcno(String newBillingAcno) {
        this.newBillingAcno = newBillingAcno;
    }
}
