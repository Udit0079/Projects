/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.sms;

import com.cbs.dto.sms.MbChargePostingIndividualHistoryTO;
import com.cbs.dto.sms.SmsPostingTo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
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

public class ChargePosting extends BaseBean {
    
    private String type;
    private String accountNo, acNo, acNoLen;
    private String messageType;
    private Date fromDate;
    private Date toDate;
    private String msg;
    private String flag = "";
    private String focusId;
    private boolean disableAccountNo;
    private SmsManagementDelegate delegate;
    private List<ChargePostingPojo> chargePostingList;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    
    public ChargePosting() {
        try {
            delegate = new SmsManagementDelegate();
            chargePostingList = new ArrayList<ChargePostingPojo>();
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            this.setAcNoLen(getAcNoLength());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }
    
    public void onBlurGetNewAccount() {
        try{
            if (acNo == null || acNo.equalsIgnoreCase("")) {
                this.setMsg("Please fill Account Number.");
                return;
            }
            
            if (!this.acNo.equalsIgnoreCase("") && ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMsg("Please fill Account Number.");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(acNo);
        }catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }
    
    public void onBlurOfType() {
        this.msg = "";
        if (this.type.equalsIgnoreCase("0")) {
            this.setMsg("Please select type.");
            return;
        }
        if (this.type.equalsIgnoreCase("A")) {
            this.setDisableAccountNo(true);
            this.setFocusId("ddType");
        } else {
            this.setDisableAccountNo(false);
            this.setFocusId("txtAccountNo");
        }
    }
    
    public void onBlurOfRequestType() {
        msg = "";
        if (messageType.equalsIgnoreCase("0")) {
            this.setMsg("Please select Request Type.");
            return;
        }
        try {
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                flag = "ALL";
            } else {
                flag = accountNo;
                if (accountNo.length() < 12 || (!new Validator().validateString(accountNo))) {
                    this.setMsg("Please fill valid account no of 12 digits !");
                    return;
                }
                
                if (!(flag.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {
                    this.setMsg("Please fill your branch account number !");
                    return;
                }
            }
            
            List<SmsPostingTo> dataList  = new ArrayList<SmsPostingTo>();
//            List<SmsPostingTo> dataList = delegate.allAccountMessage(messageType, flag, getOrgnBrCode());
            int i = 0;
            chargePostingList = new ArrayList<ChargePostingPojo>();
            if (!dataList.isEmpty()) {
                String chargePerMessage = "";
//                String chargePerMessage = delegate.getChargePerMessage();
                Double totalCharge = 0.0;
                for (int j = 0; j < dataList.size(); j++) {
                    ChargePostingPojo pojo = new ChargePostingPojo();
                    SmsPostingTo to = dataList.get(j);
                    pojo.setAcno(to.getAcno());
                    pojo.setBillingAcno(to.getBillingAcno());
                    pojo.setBillingAcnoNature(to.getBillingAcnoNature());
                    pojo.setMessageType(this.messageType);
                    pojo.setNoOfMsg(to.getNoOfMessage().intValue());
//                    pojo.setFromDate(sdf.format(delegate.getMaxToDateForChargePostingByAcno(pojo.getAcno(), this.messageType)));
                    pojo.setToDate(sdf.format(new Date()));
                    pojo.setCharges(new BigDecimal(formatter.format(pojo.getNoOfMsg() * Double.parseDouble(chargePerMessage))));
                    totalCharge = totalCharge + pojo.getCharges().doubleValue();
                    
                    pojo.setSno(++i);
                    chargePostingList.add(pojo);
                }
            } else {
                this.setMsg("No detail exists.");
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }
    
    public String validation() {
        if (type.equalsIgnoreCase("0")) {
            return "Please select type.";
        }
        if (type.equalsIgnoreCase("I") && accountNo.equalsIgnoreCase("")) {
            return "Please insert account no.";
        }
        if (messageType.equalsIgnoreCase("0")) {
            return "Please select Request Type.";
        }
        return "true";
    }
    
    public void postAction() {
        msg = validation();
        try {
            if (msg.equalsIgnoreCase("true")) {
                msg = "";
                List<MbChargePostingIndividualHistoryTO> entityList = new ArrayList<MbChargePostingIndividualHistoryTO>();
                for (ChargePostingPojo pojo : chargePostingList) {
                    MbChargePostingIndividualHistoryTO entity = new MbChargePostingIndividualHistoryTO();
                    entity.setAcno(pojo.getAcno());
                    entity.setBillingAcno(pojo.getBillingAcno());
                    entity.setBillingAcnoNature(pojo.getBillingAcnoNature());
                    entity.setDrAmount(pojo.getCharges().doubleValue());
                    entity.setEnterBy(getUserName());
                    entity.setFromDate(sdf.parse(pojo.getFromDate()));
                    entity.setMessageType(pojo.getMessageType());
                    entity.setNoOfMessage(pojo.getNoOfMsg());
                    entity.setToDate(sdf.parse(pojo.getToDate()));
                    entity.setTranDt(sdf.parse(sdf.format(new Date())));
                    
                    entityList.add(entity);
                }
//                String msg = delegate.postChargeHistory(entityList, flag, this.messageType, getOrgnBrCode());
                refreshForm();
                this.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setMsg(e.getMessage());
        }
    }
    
    public void refreshForm() {
        chargePostingList = new ArrayList<ChargePostingPojo>();
        this.setAccountNo("");
        this.setMessageType("0");
        this.setType("0");
        this.setMsg("");
        this.setAcNo("");
        this.setDisableAccountNo(true);
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
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
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

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }    
}