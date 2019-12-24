/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class HolidayMarkProcess extends BaseBean {

    private String message;
    private String option;
    private List<SelectItem> optionList;
    private String date;
    private String frDate ;
    private String displayDate = "";
    BankProcessManagementFacadeRemote remoteProcess;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public HolidayMarkProcess() {
        try {
            remoteProcess = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup("BankProcessManagementFacade");

            optionList = new ArrayList<>();
            optionList.add(new SelectItem("0", "--Select--"));
            optionList.add(new SelectItem("M", "Holiday Marking"));
            optionList.add(new SelectItem("U", "Holiday UnMarking"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setMessage("");
        try {
            if (option.equalsIgnoreCase("0")) {
                this.setMessage("Please Select the option .");
            }
            if (option.equalsIgnoreCase("M")) {
                this.displayDate = "";
            } else if (option.equalsIgnoreCase("U")) {
                this.displayDate = "none";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void ondateFunction() {
        try {
            if (this.frDate.equalsIgnoreCase("") || this.frDate.equalsIgnoreCase(null) || this.frDate.equalsIgnoreCase("__/__/____")) {
                     this.setMessage("Please fill the from date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(frDate)) {
                    this.setMessage("Please fill proper from date.");
                    return;
                }
                if (dmy.parse(frDate).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setMessage("From date can not be greater than current date.");
                    return;
                }
                if (this.date.equalsIgnoreCase("") || this.date.equalsIgnoreCase(null) || this.date.equalsIgnoreCase("__/__/____")) {
                this.setMessage("Please fill the date .");
                return;
                }
                 if (!new Validator().validateDate_dd_mm_yyyy(date)) {
                    this.setMessage("Please fill proper to date.");
                    return;
                }
                if (dmy.parse(date).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setMessage("To date can not be greater than current date.");
                    return;
                }
                if (dmy.parse(frDate).compareTo(dmy.parse(date)) > 0) {
                   this.setMessage("From date can not be greater than to date.");
                    return;
                }
                if(frDate.equals(getTodayDate())){
                    this.setMessage("From date can not be today date");
                    return;
                }
                if(date.equals(getTodayDate())){
                    this.setMessage("To date can not be today date.");
                }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void proceedAction() {
        try {
            ondateFunction();
            if (option.equalsIgnoreCase("M")) {
                List resultList = remoteProcess.getAtmTransactionDataForMarking(ymd.format(dmy.parse(this.frDate)));
                
                 String result = remoteProcess.holidayMarkingProcess(resultList, getUserName(),ymd.format(dmy.parse(this.date)), 
                            ymd.format(dmy.parse(this.frDate)));
                    if (result.equalsIgnoreCase("true")) {
                        this.setMessage("Holiday marked successfully.");
                    }
            } else if (option.equalsIgnoreCase("U")) {
           String result  = remoteProcess.holidayUnmarkingProcess(getUserName(),ymd.format(dmy.parse(getTodayDate())));
           if(result.equalsIgnoreCase("true")){
               this.setMessage("Holiday Unmarked Successfully.");
           }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setDate("");
        this.setFrDate("");
        this.setOption("0");
        this.setDisplayDate("");
    }

    public String ExitForm() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public String getFrDate() {
        return frDate;
    }

    public void setFrDate(String frDate) {
        this.frDate = frDate;
    }
     
}
