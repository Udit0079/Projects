/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.misc.StrAlertFacadeRemote;
import com.cbs.pojo.BankLevelSTRPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.List;

/**
 *
 * @author root
 */
public class BankLevelSTRParameterController extends BaseBean{

    private String msg;
    private String msgStyle;
    private List alertIndicatorList;
    private boolean  saveDisable;
    private boolean gridCheckBoxDisable;
    private boolean fromDisable;
    private boolean toDisable;
    private boolean noOfTxnDisable;
    private boolean dayMonthDisable;
    private StrAlertFacadeRemote strAlertRemote;
    List<BankLevelSTRPojo> strAlertList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgStyle() {
        return msgStyle;
    }

    public void setMsgStyle(String msgStyle) {
        this.msgStyle = msgStyle;
    }
    

    public List getAlertIndicatorList() {
        return alertIndicatorList;
    }

    public void setAlertIndicatorList(List alertIndicatorList) {
        this.alertIndicatorList = alertIndicatorList;
    }

    public List<BankLevelSTRPojo> getStrAlertList() {
        return strAlertList;
    }

    public void setStrAlertList(List<BankLevelSTRPojo> strAlertList) {
        this.strAlertList = strAlertList;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isGridCheckBoxDisable() {
        return gridCheckBoxDisable;
    }

    public void setGridCheckBoxDisable(boolean gridCheckBoxDisable) {
        this.gridCheckBoxDisable = gridCheckBoxDisable;
    }

    public boolean isFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public boolean isToDisable() {
        return toDisable;
    }

    public void setToDisable(boolean toDisable) {
        this.toDisable = toDisable;
    }

    public boolean isNoOfTxnDisable() {
        return noOfTxnDisable;
    }

    public void setNoOfTxnDisable(boolean noOfTxnDisable) {
        this.noOfTxnDisable = noOfTxnDisable;
    }

    public boolean isDayMonthDisable() {
        return dayMonthDisable;
    }

    public void setDayMonthDisable(boolean dayMonthDisable) {
        this.dayMonthDisable = dayMonthDisable;
    }
    
    public BankLevelSTRParameterController() throws ServiceLocatorException {
           strAlertRemote =null; 
           strAlertRemote = (StrAlertFacadeRemote)ServiceLocator.getInstance().lookup("StrAlertFacade");
        getStrList();
    }
    
    public void getStrList(){
        try{
            strAlertList = strAlertRemote.getBankLevelStr();
            this.setMsg("");
        }catch(Exception ex){
            this.setMsgStyle("error");
            this.setMsg(ex.getMessage());
        }
    }
    public void checkUnCheck() {
        // This method is required for grid check box. 
        // It is using by CKYCRBranchRequest.jsp, in grid.
    }
    public void saveStr(){
        try{
            int result = strAlertRemote.insertCBSAlertIndicator(strAlertList, this.getUserName(), this.getOrgnBrCode(), true);
            if(result > 0){
                getStrList();
                this.setMsgStyle("msg");
                this.setMsg("STR Alert Indicater updated successfully.");
            }
        }catch(Exception ex){
            this.setMsgStyle("error");
            this.setMsg(ex.getMessage());
        }
    }
    public void refresh(){
        this.strAlertList = null;
        this.setMsg("");
        getStrList();
    }
    public String exitForm(){
        return "case1";
    }
}
