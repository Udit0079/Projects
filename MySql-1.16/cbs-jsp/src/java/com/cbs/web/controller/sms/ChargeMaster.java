/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.sms;

import com.cbs.entity.sms.MbChargeMaster;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.SmsManagementDelegate;
import java.util.Date;

public class ChargeMaster extends BaseBean {

    /** Creates a new instance of ChargeMaster */
    private String message;
    private String messageType;
    private String crHead;
    private String chargePerMessage;

    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }

    public String getChargePerMessage() {
        return chargePerMessage;
    }

    public void setChargePerMessage(String chargePerMessage) {
        this.chargePerMessage = chargePerMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public ChargeMaster() {
        this.setChargePerMessage("0.0");
    }

    public void saveAction() {
        if (messageType.equalsIgnoreCase("--SELECT--")) {
            setMessage("Please select message type.");
            return;
        }
        if (chargePerMessage == null || (!new Validator().validateDoublePositive(chargePerMessage))) {
            setMessage("Please insert valid charge per message.");
            return;
        }
        if (this.crHead == null || this.crHead.equalsIgnoreCase("")) {
            this.setMessage("Please fill Credited GL Head !");
            return;
        }
        if (this.crHead.length() < 6) {
            this.setMessage("Please fill 6 digit account number !");
            return;
        }
        try {
            SmsManagementDelegate delegate = new SmsManagementDelegate();

            MbChargeMaster masterEntity = new MbChargeMaster();
            masterEntity.setMessageType(messageType);
            masterEntity.setChargePerMessage(Double.parseDouble(chargePerMessage));
            masterEntity.setStatus("A");
            masterEntity.setChargeGlHead(this.crHead);
            masterEntity.setCreationDate(new Date());
            masterEntity.setEnterBy(getUserName());
            refreshForm();
            message = delegate.saveChargeMasterDetails(masterEntity);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("This account number is not present !");
            }
        }
    }

    public void refreshForm() {
        this.setMessageType("--SELECT--");
        this.setChargePerMessage("0.0");
        this.setCrHead("");
        this.setMessage("");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
