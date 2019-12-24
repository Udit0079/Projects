/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author saurabhsipl
 */
public class CashInHandEntry extends BaseBean {
    String message;
    String calDate;
    String fromDate;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "BankProcessManagementFacade";
    private BankProcessManagementFacadeRemote bankProcessManagementFacade = null;

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public CashInHandEntry() {
        try {
            bankProcessManagementFacade = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setCalDate(dmy.format(new Date()));
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }
    
    
    public void savedata() {
        try {
            String report = "Cash In Hand Entry";
            this.setMessage("");
            if (fromDate.equalsIgnoreCase("") || this.fromDate == null) {
                this.setMessage("Please Enter From Date.");
                return;
            }
            if (calDate.equalsIgnoreCase("") || this.calDate == null) {
                this.setMessage("Please Enter  To Date.");
                return;
            }
            /**END**/
            String cashInHandEntry = bankProcessManagementFacade.cashInHandEntryBetweenDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90")?"0A":getOrgnBrCode(), ymd.format(dmy.parse(fromDate)), ymd.format(dmy.parse(calDate)));
            if(cashInHandEntry.equalsIgnoreCase("true")){
                setMessage("Data entered successfully");
            }else{
                setMessage(cashInHandEntry);
            }
            

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            this.setMessage(e.getMessage());
        }
        //return "report";
    }
    
    public void refresh(){
        this.setCalDate("");
        this.setFromDate("");
        this.setMessage("");
    }

    public String exit() {
        this.setCalDate(dmy.format(new Date()));
        return "case1";
    }
    
}
