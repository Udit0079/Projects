/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.sms;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.master.CbsChargeDetailTO;
import com.cbs.dto.sms.MbChargePostingIndividualHistoryTO;
import com.cbs.dto.sms.SmsPostingTo;
import com.cbs.entity.sms.MbChargeMaster;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.SmsManagementDelegate;
import com.cbs.web.pojo.sms.ChargePostingPojo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class FixedChargePosting extends BaseBean {

    private String type;
    private String accountNo;
    private Date fromDate;
    private Date toDate;
    private String msg;
    private String focusId;
    private String frDt;
    private String toDt;
    private String totalChg;
    private String crHead;
    private String nature;
    private String acType;
    private String showFixedPanel = "none";
    private String showPerSMSPanel = "none";
    private boolean disableAccountNo;
    private boolean btnCalFlag;
    private boolean btnPostFlag;
    private boolean dtEnable;
    private boolean toDtEnable;
    private boolean disableNature;
    private boolean disableAcType;
    private List<SelectItem> natureList;
    private List<SelectItem> acTypeList;
    private SmsManagementDelegate delegate;
    private List<ChargePostingPojo> chargePostingList;
    private final String jndiHomeName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiSmsName = "SmsManagementFacade";
    private SmsManagementFacadeRemote beanRemote = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.##");
    private int fixedOrPerSms;
    private int chargeBasedOnParam;

    public FixedChargePosting() {
        try {
            delegate = new SmsManagementDelegate();
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (SmsManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiSmsName);
            chargePostingList = new ArrayList<ChargePostingPojo>();
            initData();
            refreshForm();
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void initData() {
        try {
            natureList = new ArrayList<SelectItem>();
            acTypeList = new ArrayList<SelectItem>();

            chargeBasedOnParam = ftsRemote.getCodeForReportName("SMS-CHG-NATURE-WISE");

            fixedOrPerSms = ftsRemote.getCodeForReportName("SMS-FIXED-CHARGE");
            if (fixedOrPerSms == 2) { //Per SMS Charge
                this.showPerSMSPanel = "";
                this.showFixedPanel = "none";
            } else {
                this.showPerSMSPanel = "none";
                this.showFixedPanel = "";
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void onBlurOfType() {
        this.msg = "";
        try {
            if (this.type.equalsIgnoreCase("0")) {
                this.setMsg("Please select type.");
                return;
            }
            if (this.type.equalsIgnoreCase("A")) {
                natureList = new ArrayList<SelectItem>();
                acTypeList = new ArrayList<SelectItem>();
                this.setDisableAccountNo(true);
                this.setDtEnable(true);
                if (chargeBasedOnParam == 2) { //All nature in one go
                    natureList.add(new SelectItem("A", "ALL"));
                    this.setDisableNature(true);
                    this.setDisableAcType(true);
                    this.setFocusId("update");
                    String[] arr = setSmsFrequency(2);
                    if (!arr[0].equalsIgnoreCase("true")) {
                        this.setMsg(arr[0]);
                        return;
                    }
                    this.setFrDt(arr[1]);
                    this.setToDt(arr[2]);

                    this.setBtnCalFlag(false);
                    this.setMsg("Please check from and to date.");
                    this.toDtEnable = (fixedOrPerSms == 2) ? false : true;
                } else if (chargeBasedOnParam == 1) { //Nature wise
                    natureList.add(new SelectItem("0", "--SELECT--"));
                    List list = beanRemote.getAllAccountNatureWithoutPO();
                    for (int i = 0; i < list.size(); i++) {
                        Vector ele = (Vector) list.get(i);
                        natureList.add(new SelectItem(ele.get(0).toString()));
                    }
                    this.setDisableNature(false);
                    acTypeList.add(new SelectItem("A", "ALL"));
                    this.setDisableAcType(true);
                    this.setFocusId("ddNature");
                    this.toDtEnable = (fixedOrPerSms == 2) ? false : true;
                } else if (chargeBasedOnParam == 0) { //Account code wise
                    natureList.add(new SelectItem("0", "--SELECT--"));
                    List list = beanRemote.getAllAccountNatureWithoutPO();
                    for (int i = 0; i < list.size(); i++) {
                        Vector ele = (Vector) list.get(i);
                        natureList.add(new SelectItem(ele.get(0).toString()));
                    }
                    this.setDisableNature(false);
                    this.setDisableAcType(true);
                    this.setFocusId("ddNature");
                    this.toDtEnable = (fixedOrPerSms == 2) ? false : true;
                }
            } else {
                this.setDisableAccountNo(false);
                this.setDisableNature(true);
                this.setDisableAcType(true);
                this.setDtEnable(true);
                this.setToDtEnable(true);
                this.setFocusId("txtAccountNo");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void onBlurNature() {
        try {
            if (this.nature == null || this.nature.equals("") || this.nature.equals("0")) {
                this.setMsg("Please select Nature.");
                return;
            }
            if (chargeBasedOnParam == 1) {
                this.setDisableAcType(true);
                this.setDtEnable(true);
                String[] arr = setSmsFrequency(1);
                if (!arr[0].equalsIgnoreCase("true")) {
                    this.setMsg(arr[0]);
                    return;
                }
                this.setFrDt(arr[1]);
                this.setToDt(arr[2]);

                this.setBtnCalFlag(false);
                this.setFocusId("update");
                this.setMsg("Please check from and to date.");
                this.toDtEnable = (fixedOrPerSms == 2) ? false : true;
            } else if (chargeBasedOnParam == 0) {
                acTypeList = new ArrayList<SelectItem>();
                acTypeList.add(new SelectItem("0", "--SELECT--"));
                List list = beanRemote.getAllAccountCodeByNature(this.nature);
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    acTypeList.add(new SelectItem(ele.get(0).toString()));
                }
                this.setDisableAcType(false);
                this.setDtEnable(true);
                this.setFocusId("ddType");
                this.toDtEnable = (fixedOrPerSms == 2) ? false : true;
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void onBlurAcType() {
        try {
            if (this.nature == null || this.nature.equals("") || this.nature.equals("0")) {
                this.setMsg("Please select Nature.");
                return;
            }
            if (this.acType == null || this.acType.equals("") || this.acType.equals("0")) {
                this.setMsg("Please select A/c Type.");
                return;
            }
            this.setDtEnable(true);
            this.setBtnCalFlag(true);
            this.setFrDt(getTodayDate());
            this.setToDt(getTodayDate());
            String[] arr = setSmsFrequency(0);
            if (!arr[0].equalsIgnoreCase("true")) {
                this.setMsg(arr[0]);
                return;
            }
            this.setFrDt(arr[1]);
            this.setToDt(arr[2]);
            this.setBtnCalFlag(false);
            this.setMsg("Please check from and to date.");
            this.toDtEnable = (fixedOrPerSms == 2) ? false : true;
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public String[] setSmsFrequency(int chgParam) {
        String[] arr = new String[3];
        try {
            List list = beanRemote.getSmsChgFrequency(chgParam, this.nature, this.acType, getOrgnBrCode());
            if (list.isEmpty()) {
                arr[0] = "Please Fill SMS Charge Frequency In Loan Parameter Table.";
                arr[1] = "";
                arr[2] = "";
                return arr;
            }
            Vector element = (Vector) list.get(0);
            if (element.get(0) == null || element.get(1) == null) {
                arr[0] = "Please Fill SMS Charge Frequency In Loan Parameter Table.";
                arr[1] = "";
                arr[2] = "";
                return arr;
            }

            arr[0] = "true";
            arr[1] = sdf.format(ymd.parse(element.get(0).toString()));
            arr[2] = sdf.format(ymd.parse(element.get(1).toString()));
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
        return arr;
    }

    public void accountFieldAction() {
        this.setMsg("");
        this.setDtEnable(true);
        this.setBtnCalFlag(true);
        this.setDisableNature(true);
        this.setDisableAcType(true);
        try {
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                this.setMsg("Please fill account number.");
                return;
            }
            if (accountNo.length() < 12 || (!new Validator().validateString(accountNo))) {
                this.setMsg("Please fill valid account no of 12 digits !");
                return;
            }
            if (!(accountNo.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {
                this.setMsg("Please fill your branch account number !");
                return;
            }
            String result = ftsRemote.ftsAcnoValidate(accountNo, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setMsg(result);
                return;
            }
            beanRemote.isActiveAccountForSms(accountNo);

            this.setFrDt(sdf.format(delegate.getMaxToDateOfSmsPosting(accountNo)));
            this.setToDt(sdf.format(new Date()));
            this.setBtnCalFlag(false);
            this.dtEnable = false;
            this.toDtEnable = false;
            this.setMsg("Please check from and to date.");
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public String validation() {
        try {
            if (type.equalsIgnoreCase("0")) {
                return "Please select type.";
            }
            if (type.equalsIgnoreCase("I") && accountNo.equalsIgnoreCase("")) {
                return "Please insert account no.";
            }
            if (this.frDt == null || this.toDt == null || this.frDt.equals("") || this.toDt.equals("")) {
                return "Please fill from and/or to date properly.";
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.frDt) == false) {
                return "Please fill proper from date.";
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.toDt) == false) {
                return "Please fill proper to date.";
            }
            if (sdf.parse(this.frDt).compareTo(sdf.parse(this.toDt)) == 0) {
                return "From and to date can not be same.";
            }
            if (sdf.parse(this.frDt).compareTo(sdf.parse(this.toDt)) == 1) {
                return "From date can not be greater than to date.";
            }
            if (sdf.parse(this.toDt).compareTo(sdf.parse(sdf.format(new Date()))) == 1) {
                return "To date can not be greater than current date.";
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
        return "true";
    }

    public void calculateAction() {
        this.setMsg("");
        try {
            System.out.println("Type Is-->" + type);
            System.out.println("Acno Is-->" + accountNo);
            System.out.println("Nature Is-->" + nature);
            System.out.println("Actype Is-->" + acType);
            System.out.println("From Dt Is-->" + frDt);
            System.out.println("To DT Is-->" + toDt);

            if (!validation().equalsIgnoreCase("true")) {
                this.setMsg(validation());
                return;
            }
            if (fixedOrPerSms == 2) { //Charge per sms
                List<SmsPostingTo> dataList = delegate.allAccountMessage(chargeBasedOnParam, this.type, this.accountNo, this.nature,
                        this.acType, this.frDt, this.toDt, getOrgnBrCode());
                if (dataList.isEmpty()) {
                    this.setMsg("There is no data to post.");
                    return;
                }
                MbChargeMaster chgMaster = delegate.getChargePerMessage();
                int i = 0;
                Double totalChgAmount = 0.0;
                chargePostingList = new ArrayList<ChargePostingPojo>();
                for (int j = 0; j < dataList.size(); j++) {
                    ChargePostingPojo pojo = new ChargePostingPojo();
                    SmsPostingTo to = dataList.get(j);
                    pojo.setSno(++i);
                    pojo.setAcno(to.getAcno());
                    pojo.setBillingAcno(to.getBillingAcno());
                    pojo.setBillingAcnoNature(to.getBillingAcnoNature());
                    pojo.setNoOfMsg(to.getNoOfMessage().intValue());
                    pojo.setFromDate(this.frDt);
                    pojo.setToDate(this.toDt);
                    pojo.setCharges(new BigDecimal(formatter.format(pojo.getNoOfMsg() * chgMaster.getChargePerMessage())));
                    pojo.setCustState(to.getCustState());
                    pojo.setBrState(to.getBrState());
                    totalChgAmount = totalChgAmount + pojo.getCharges().doubleValue();
                    chargePostingList.add(pojo);
                }
                this.setTotalChg(formatter.format(totalChgAmount));
                this.setCrHead(getOrgnBrCode() + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + chgMaster.getChargeGlHead() + "01");
                this.setBtnCalFlag(true);
                this.setBtnPostFlag(false);
            } else {    //Fixed sms charge
                String chargeBasedOn = "";
                if (chargeBasedOnParam == 2) {  //All nature in one go
                    chargeBasedOn = "A";
                } else {
                    if (chargeBasedOnParam == 1) { //Nature wise
                        if (this.type.equals("I")) {
                            chargeBasedOn = ftsRemote.getAccountNature(this.accountNo);
                        } else if (this.type.equals("A")) {
                            chargeBasedOn = this.nature;
                        }
                    } else if (chargeBasedOnParam == 0) { //A/c type wise
                        if (this.type.equals("I")) {
                            chargeBasedOn = ftsRemote.getAccountCode(this.accountNo);
                        } else if (this.type.equals("A")) {
                            chargeBasedOn = this.acType;
                        }
                    }
                }
                //Charge amount extraction
                List<CbsChargeDetailTO> list = delegate.getCbsChargeDetailByTypeNameAndActype("SMS", "PROMO-TRAN", chargeBasedOn);
                if (list.isEmpty()) {
                    this.setMsg("Please fill data into Cbs Charge Detail for SMS.");
                    return;
                }
                String creditedGlHead = "";
                Double chgAmount = 0.0, totalChgAmount = 0.0;
                for (CbsChargeDetailTO to : list) {
                    creditedGlHead = getOrgnBrCode() + to.getCreditHead();
                    chgAmount = to.getAmount();
                }

                int i = 0;
                chargePostingList = new ArrayList<ChargePostingPojo>();
                if (this.type.equals("I")) {
                    List<SmsPostingTo> dataList = delegate.getAccountSmsDetail(accountNo);
                    if (dataList.isEmpty()) {
                        this.setMsg("There is no data to post.");
                        return;
                    }

                    ChargePostingPojo pojo = new ChargePostingPojo();
                    pojo.setSno(1);
                    pojo.setAcno(this.accountNo);
                    pojo.setBillingAcno(dataList.get(0).getBillingAcno());
                    if (fixedOrPerSms == 1) {
                        pojo.setCharges(new BigDecimal(chgAmount));
                    } else {
                        int monthDiff = CbsUtil.monthDiff(sdf.parse(this.frDt), sdf.parse(this.toDt)) + 1;
                        chgAmount = (chgAmount * monthDiff);
                        pojo.setCharges(new BigDecimal(chgAmount));
                    }
                    pojo.setFromDate(this.frDt);
                    pojo.setToDate(this.toDt);
                    pojo.setCustState(dataList.get(0).getCustState());
                    pojo.setBrState(dataList.get(0).getBrState());
                    pojo.setBillingAcnoNature(dataList.get(0).getBillingAcnoNature());
                    chargePostingList.add(pojo);
                    totalChgAmount += chgAmount;
                } else if (this.type.equals("A")) {
                    List<SmsPostingTo> dataList = delegate.getAccountDetail(chargeBasedOnParam, this.frDt, this.toDt, getOrgnBrCode(),
                            this.nature, this.acType);
                    if (dataList.isEmpty()) {
                        this.setMsg("There is no data to post.");
                        return;
                    }
                    for (int j = 0; j < dataList.size(); j++) {
                        ChargePostingPojo pojo = new ChargePostingPojo();
                        SmsPostingTo to = dataList.get(j);
                        pojo.setSno(++i);
                        pojo.setAcno(to.getAcno());
                        pojo.setBillingAcno(to.getBillingAcno());
                        if (fixedOrPerSms == 1) {
                            pojo.setCharges(new BigDecimal(chgAmount));
                            totalChgAmount += chgAmount;
                        } else {
                            Double tmpChgAmt = chgAmount;
                            int monthDiff = 0;
                            if ((sdf.parse(to.getFromDt()).compareTo(sdf.parse(this.frDt)) == 1)
                                    || (sdf.parse(to.getFromDt()).compareTo(sdf.parse(this.frDt)) == -1)) {
                                monthDiff = CbsUtil.monthDiff(sdf.parse(to.getFromDt()), sdf.parse(this.toDt)) + 1;
                            } else {
                                monthDiff = CbsUtil.monthDiff(sdf.parse(this.frDt), sdf.parse(this.toDt)) + 1;
                            }
                            tmpChgAmt = (tmpChgAmt * monthDiff);
                            pojo.setCharges(new BigDecimal(tmpChgAmt));
                            totalChgAmount += tmpChgAmt;
                        }
                        pojo.setFromDate(to.getFromDt());
                        pojo.setToDate(this.toDt);
                        pojo.setBillingAcnoNature(to.getBillingAcnoNature());
                        pojo.setCustState(to.getCustState());
                        pojo.setBrState(to.getBrState());
                        chargePostingList.add(pojo);
                    }
                }
                this.setTotalChg(totalChgAmount.toString());
                this.setCrHead(creditedGlHead);
                this.setBtnCalFlag(true);
                this.setBtnPostFlag(false);
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void postAction() {
        try {
            msg = validation();
            if (!msg.equalsIgnoreCase("true")) {
                this.setMsg(msg);
                return;
            }
            List<MbChargePostingIndividualHistoryTO> entityList = new ArrayList<MbChargePostingIndividualHistoryTO>();
            for (ChargePostingPojo pojo : chargePostingList) {
                MbChargePostingIndividualHistoryTO entity = new MbChargePostingIndividualHistoryTO();
                entity.setAcno(pojo.getAcno());
                entity.setBillingAcno(pojo.getBillingAcno());
                entity.setBillingAcnoNature(pojo.getBillingAcnoNature());
                entity.setDrAmount(pojo.getCharges().doubleValue());
                entity.setEnterBy(getUserName());
                entity.setFromDate(sdf.parse(pojo.getFromDate()));
                entity.setToDate(sdf.parse(pojo.getToDate()));
                entity.setTranDt(sdf.parse(sdf.format(new Date())));
                entity.setBrState(pojo.getBrState());
                entity.setCustState(pojo.getCustState());
                entity.setNoOfMessage(pojo.getNoOfMsg()); //Only in case of charge per message.

                entityList.add(entity);
            }
            String msg = delegate.postChargeHistory(fixedOrPerSms, chargeBasedOnParam, entityList,
                    this.type, getOrgnBrCode(), this.crHead, this.frDt, this.toDt, this.nature, this.acType);
            refreshForm();
            this.setMsg(msg);
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void refreshForm() {
        chargePostingList = new ArrayList<ChargePostingPojo>();
        this.setMsg("");
        this.setType("0");
        this.setAccountNo("");
        this.setFrDt(getTodayDate());
        this.setToDt(getTodayDate());
        this.setTotalChg("");
        this.setCrHead("");
        this.setDisableAccountNo(true);
        this.setBtnCalFlag(true);
        this.setBtnPostFlag(true);
        this.setDtEnable(false);
        acTypeList = new ArrayList<SelectItem>();
        natureList = new ArrayList<SelectItem>();
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDisableAccountNo() {
        return disableAccountNo;
    }

    public void setDisableAccountNo(boolean disableAccountNo) {
        this.disableAccountNo = disableAccountNo;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public List<ChargePostingPojo> getChargePostingList() {
        return chargePostingList;
    }

    public void setChargePostingList(List<ChargePostingPojo> chargePostingList) {
        this.chargePostingList = chargePostingList;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public boolean isBtnCalFlag() {
        return btnCalFlag;
    }

    public void setBtnCalFlag(boolean btnCalFlag) {
        this.btnCalFlag = btnCalFlag;
    }

    public boolean isBtnPostFlag() {
        return btnPostFlag;
    }

    public void setBtnPostFlag(boolean btnPostFlag) {
        this.btnPostFlag = btnPostFlag;
    }

    public String getTotalChg() {
        return totalChg;
    }

    public void setTotalChg(String totalChg) {
        this.totalChg = totalChg;
    }

    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }

    public boolean isDtEnable() {
        return dtEnable;
    }

    public void setDtEnable(boolean dtEnable) {
        this.dtEnable = dtEnable;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getNatureList() {
        return natureList;
    }

    public void setNatureList(List<SelectItem> natureList) {
        this.natureList = natureList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public boolean isDisableNature() {
        return disableNature;
    }

    public void setDisableNature(boolean disableNature) {
        this.disableNature = disableNature;
    }

    public boolean isDisableAcType() {
        return disableAcType;
    }

    public void setDisableAcType(boolean disableAcType) {
        this.disableAcType = disableAcType;
    }

    public String getShowFixedPanel() {
        return showFixedPanel;
    }

    public void setShowFixedPanel(String showFixedPanel) {
        this.showFixedPanel = showFixedPanel;
    }

    public String getShowPerSMSPanel() {
        return showPerSMSPanel;
    }

    public void setShowPerSMSPanel(String showPerSMSPanel) {
        this.showPerSMSPanel = showPerSMSPanel;
    }

    public int getChargeBasedOnParam() {
        return chargeBasedOnParam;
    }

    public void setChargeBasedOnParam(int chargeBasedOnParam) {
        this.chargeBasedOnParam = chargeBasedOnParam;
    }

    public boolean isToDtEnable() {
        return toDtEnable;
    }

    public void setToDtEnable(boolean toDtEnable) {
        this.toDtEnable = toDtEnable;
    }
}