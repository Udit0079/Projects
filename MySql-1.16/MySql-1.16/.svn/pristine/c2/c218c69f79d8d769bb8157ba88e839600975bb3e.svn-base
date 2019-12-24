/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.EPSBankMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;

/**
 *
 * @author root
 */
public class EFTBankMaster extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String message;
    private String bankCode;
    private String bankName;
    private String shortName;
    private String localClgFlag;
    private String primeBankFlag;
    private String corrBankFlag;
    private String bicCode;
    private String weekOff;
    private boolean add;
    private boolean update;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBicCode() {
        return bicCode;
    }

    public void setBicCode(String bicCode) {
        this.bicCode = bicCode;
    }

    public String getCorrBankFlag() {
        return corrBankFlag;
    }

    public void setCorrBankFlag(String corrBankFlag) {
        this.corrBankFlag = corrBankFlag;
    }

    public String getLocalClgFlag() {
        return localClgFlag;
    }

    public void setLocalClgFlag(String localClgFlag) {
        this.localClgFlag = localClgFlag;
    }

    public String getPrimeBankFlag() {
        return primeBankFlag;
    }

    public void setPrimeBankFlag(String primeBankFlag) {
        this.primeBankFlag = primeBankFlag;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getWeekOff() {
        return weekOff;
    }

    public void setWeekOff(String weekOff) {
        this.weekOff = weekOff;
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

    /** Creates a new instance of EPSBankMaster */
    public EFTBankMaster() {
        try {
            add = true;
            update = true;
            this.setCurrentDate(getTodayDate());
            this.setLoggedInUser(getUserName());
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onBlurBankCode() {
        this.setMessage("");
        try {
            if (this.bankCode.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Bank Code");
                return;
            }
            EPSBankMaster bankDetail = remoteInterface.getEpsBankMaster(this.getBankCode());
            if (bankDetail != null) {
                this.setBankCode(bankDetail.getBankCode());
                this.setBankName(bankDetail.getBankName());
                this.setShortName(bankDetail.getShortName());
                this.setLocalClgFlag(bankDetail.getLocalClearingFlag());
                this.setPrimeBankFlag(bankDetail.getPrimeBankFlag());
                this.setCorrBankFlag(bankDetail.getCorrBankFlag());
                this.setBicCode(bankDetail.getBICode());
                this.setWeekOff(bankDetail.getWeeklyOff());
                update = false;
                add = true;
            }
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                update = true;
                add = false;
                this.setBankName("");
                this.setShortName("");
                this.setLocalClgFlag("");
                this.setPrimeBankFlag("");
                this.setCorrBankFlag("");
                this.setBicCode("");
                this.setWeekOff("");
            }
        }
    }

    public void saveButton() {
        try {
            String msg = valdations();
            if (!msg.equalsIgnoreCase("true")) {
                this.setMessage(msg);
                return;
            }
            EPSBankMaster tableObj = new EPSBankMaster(bankCode, this.getBankName(), this.getShortName(), this.getLocalClgFlag(), this.getPrimeBankFlag(), this.getCorrBankFlag(), this.getBicCode(), this.getWeekOff());
            String rsmsg = remoteInterface.saveEpsBankMaster(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been saved successfuly");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void updateButton() {
        try {
            String msg = valdations();
            if (!msg.equalsIgnoreCase("true")) {
                this.setMessage(msg);
                return;
            }
            EPSBankMaster tableObj = new EPSBankMaster(this.getBankCode(), this.getBankName(), this.getShortName(), this.getLocalClgFlag(), this.getPrimeBankFlag(), this.getCorrBankFlag(), this.getBicCode(), this.getWeekOff());
            String rsmsg = remoteInterface.updateEpsBankMaster(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been updated successfuly");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String valdations() {
        if (this.getBankCode().equalsIgnoreCase("")) {
            return "Please Enter Bank Code";
        }
        if (this.getBankName().equalsIgnoreCase("")) {
            return "Please Enter Bank Name";
        }
        if (this.getCorrBankFlag().equalsIgnoreCase("")) {
            return "Please Enter Corr.Bank Flag";
        }
        return "true";
    }

    public void refresh() {
        this.setMessage("");
        this.setBankCode("");
        this.setBankName("");
        this.setShortName("");
        this.setLocalClgFlag("");
        this.setPrimeBankFlag("");
        this.setCorrBankFlag("");
        this.setBicCode("");
        this.setWeekOff("");
        update = true;
        add = true;
    }

    public void refreshOther() {
        this.setBankCode("");
        this.setBankName("");
        this.setShortName("");
        this.setLocalClgFlag("");
        this.setPrimeBankFlag("");
        this.setCorrBankFlag("");
        this.setBicCode("");
        this.setWeekOff("");
        update = true;
        add = true;
    }

    public String exitButton() {
        refresh();
        return "case1";
    }
}
