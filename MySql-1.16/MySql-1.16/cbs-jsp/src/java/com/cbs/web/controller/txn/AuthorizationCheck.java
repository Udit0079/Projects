/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.txn.MessageShow;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AuthorizationCheck extends BaseBean {

    private String tempBd;
    private String errorMsg;
    private String message;
    // private List msgList;
    private MessageShow msgShow;
    private List<MessageShow> msgShowList;
    private String flag1 = "none";
    private final String jndiHomeName = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherAuthRemote = null;

    public String getTempBd() {
        return tempBd;
    }

    public void setTempBd(String tempBd) {
        this.tempBd = tempBd;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageShow getMsgShow() {
        return msgShow;
    }

    public void setMsgShow(MessageShow msgShow) {
        this.msgShow = msgShow;
    }

    public List<MessageShow> getMsgShowList() {
        return msgShowList;
    }

    public void setMsgShowList(List<MessageShow> msgShowList) {
        this.msgShowList = msgShowList;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public AuthorizationCheck() {
        try {
            otherAuthRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void clickOnAuthChk() {
        try {
            msgShowList = new ArrayList<MessageShow>();
            List listBankDays = otherAuthRemote.selectFromBankDays(getOrgnBrCode());
            if (!listBankDays.isEmpty()) {
                Vector vecBankDays = (Vector) listBankDays.get(0);
                tempBd = vecBankDays.get(0).toString();
                List<String> list = otherAuthRemote.returnAuthStatus(tempBd, getOrgnBrCode());
                if (list.isEmpty()) {
                    flag1 = "none";
                    this.setErrorMsg("No unauthorized records were found.");
                    return;
                } else {
                    flag1 = "";
                    this.setErrorMsg("");
                    for (String msg : list) {
                        msgShow = new MessageShow();
                        msgShow.setMsg1(msg);
                        msgShowList.add(msgShow);
                    }
                }
            } else {
                this.setErrorMsg("Date denied.");
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String exitForm() {
        msgShowList = new ArrayList<MessageShow>();
        this.setErrorMsg("");
        flag1 = "none";
        return "case1";
    }
}
