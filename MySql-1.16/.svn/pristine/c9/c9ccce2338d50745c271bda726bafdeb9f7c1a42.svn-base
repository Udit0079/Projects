/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.EPSMessageCategory;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author stellar
 */
public class MessageCategory extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String msgType;
    private String screenFlag;
    private String PaySysId;
    private String msgSubType;
    private String eftMessage;
    private String message;
    private boolean add;
    private boolean update;
    private boolean delete;
    private String scheduleId;
    private List<SelectItem> scheduleIdList;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public String getEftMessage() {
        return eftMessage;
    }

    public void setEftMessage(String eftMessage) {
        this.eftMessage = eftMessage;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgSubType() {
        return msgSubType;
    }

    public void setMsgSubType(String msgSubType) {
        this.msgSubType = msgSubType;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getPaySysId() {
        return PaySysId;
    }

    public void setPaySysId(String PaySysId) {
        this.PaySysId = PaySysId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getScreenFlag() {
        return screenFlag;
    }

    public void setScreenFlag(String screenFlag) {
        this.screenFlag = screenFlag;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<SelectItem> getScheduleIdList() {
        return scheduleIdList;
    }

    public void setScheduleIdList(List<SelectItem> scheduleIdList) {
        this.scheduleIdList = scheduleIdList;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public MessageCategory() {
        try {
            add = true;
            update = true;
            delete = true;
            this.setCurrentDate(getTodayDate());
            this.setLoggedInUser(getUserName());
            scheduleIdList = new ArrayList<SelectItem>();
            scheduleIdList.add(new SelectItem("N", "NO"));
            scheduleIdList.add(new SelectItem("Y", "YES"));
            this.setScheduleId("NO");
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void updateButton() {
        try {
            String msg = valdations();
            if (!msg.equalsIgnoreCase("true")) {
                this.setMessage(msg);
                return;
            }
            EPSMessageCategory tableObj = new EPSMessageCategory(PaySysId, msgType, msgSubType, eftMessage, screenFlag, scheduleId);
            String updateMsg = remoteInterface.updateEpsMessageCategory(tableObj);
            if (updateMsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been updated successfuly");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void deleteButton() {
        try {
            if (msgSubType.equalsIgnoreCase("")) {
                this.setMessage("Please enter message sub type");
                return;
            }
            EPSMessageCategory tableObj = new EPSMessageCategory(PaySysId, msgType, msgSubType, eftMessage, screenFlag, scheduleId);
            String deleteMsg = remoteInterface.deleteEpsMessageCategory(tableObj);
            if (deleteMsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been deleted successfuly");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    public void saveButton() {
        try {
            String mes = valdations();
            if (!mes.equalsIgnoreCase("true")) {
                this.setMessage(mes);
                return;
            }
            EPSMessageCategory tableObj = new EPSMessageCategory(PaySysId, msgType, msgSubType, eftMessage, screenFlag, scheduleId);
            String saveMsg = remoteInterface.saveEpsMessageCategory(tableObj);
            if (saveMsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been successfuly saved");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurMessageSubType() {
        try {
            if (msgSubType.equalsIgnoreCase("")) {
                this.setMessage("Please enter message sub type");
                return;
            }
            EPSMessageCategory brnData = remoteInterface.getEpsMessageCategory(msgSubType);
            if (brnData != null) {
                setMessage("");
                update = false;
                delete = false;
                add = true;
                this.setMsgSubType(brnData.getMsgSubType());
                this.setPaySysId(brnData.getPaySysId());
                this.setMsgType(brnData.getMsgType());
                this.setScreenFlag(brnData.getScreenFlag());
                this.setEftMessage(brnData.getMsgPurpose());
            }
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                delete = true;
                update = true;
                add = false;
                setMessage("");
                this.setMsgType("");
                this.setScreenFlag("");
                this.setPaySysId("");
                this.setEftMessage("");
            }
        }
    }

    public String valdations() {
        if (msgSubType.equalsIgnoreCase("")) {
            return "Please enter the message sub type";
        }
        if (screenFlag.equalsIgnoreCase("")) {
            return "Please enter the screen flag";
        }
        if (eftMessage.equalsIgnoreCase("")) {
            return "Please enter the message";
        }
        return "true";
    }

    public void refresh() {
        setMessage("");
        this.setMsgType("");
        this.setScreenFlag("");
        this.setEftMessage("");
        this.setMsgSubType("");
        this.setScheduleId("NO");
        setPaySysId("");
        update = true;
        add = true;
        delete = true;
    }

    public void refreshOther() {
        this.setMsgType("");
        this.setScreenFlag("");
        this.setEftMessage("");
        this.setMsgSubType("");
        this.setScheduleId("NO");
        this.setPaySysId("");
        update = true;
        delete = true;
        add = true;
    }

    public String exitButton() {
        refresh();
        return "case1";
    }
}
