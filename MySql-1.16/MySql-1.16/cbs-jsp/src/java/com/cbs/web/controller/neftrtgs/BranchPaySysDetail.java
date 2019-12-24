/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.EPSBranchPaySysDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author stellar
 */
public class BranchPaySysDetail extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String bankCode;
    private String branchCode;
    private String PaySysId;
    private String chargesFlag;
    private String enableFlag;
    private String delStatusFlag;
    private String message;
    private boolean add;
    private boolean update;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public String getDelStatusFlag() {
        return delStatusFlag;
    }

    public void setDelStatusFlag(String delStatusFlag) {
        this.delStatusFlag = delStatusFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getChargesFlag() {
        return chargesFlag;
    }

    public void setChargesFlag(String chargesFlag) {
        this.chargesFlag = chargesFlag;
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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    public BranchPaySysDetail() {
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

    public void updateButton() {
        try {
            String msg = valdations();
            if (!msg.equalsIgnoreCase("true")) {
                this.setMessage(msg);
                return;
            }
            EPSBranchPaySysDetail tableObj = new EPSBranchPaySysDetail(PaySysId, bankCode, branchCode, enableFlag, chargesFlag, delStatusFlag);
            String rsmsg = remoteInterface.updateEpsBranchPaySysDetail(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been updated successfuly");
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
            EPSBranchPaySysDetail tableObj = new EPSBranchPaySysDetail(PaySysId, bankCode, branchCode, enableFlag, chargesFlag, delStatusFlag);
            String rsmsg = remoteInterface.saveEpsBranchPaySysDetail(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been successfuly saved");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurPaySysId() {
        try {
            if (PaySysId.equalsIgnoreCase("")) {
                this.setMessage("Please Enter PaySysId");
                return;
            }
            EPSBranchPaySysDetail paySysData = remoteInterface.getEpsBranchPaySysDetail(PaySysId);
            if (paySysData != null) {
                this.setMessage("");
                update = false;
                add = true;
                this.setBankCode(paySysData.getBankCode());
                this.setBranchCode(paySysData.getBranchCode());
                this.setPaySysId(paySysData.getPaySysId());
                this.setEnableFlag(paySysData.getEnableFlag());
                this.setChargesFlag(paySysData.getChargesFlag());
                this.setDelStatusFlag(paySysData.getDelStatusFlag());
            }
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                update = true;
                add = false;
                this.setMessage("");
                this.setBankCode("");
                this.setBranchCode("");
                this.setChargesFlag("");
                this.setEnableFlag("");
                this.setDelStatusFlag("");
            }
        }
    }

    public String valdations() {
        if (PaySysId.equalsIgnoreCase("")) {
            return "Please enter the Pay SysId";
        }
        if (branchCode.equalsIgnoreCase("")) {
            return "Please enter the branch code";
        }
        Pattern p3 = Pattern.compile("[A-Za-z0-9]+[ \t\r\n]*");
        if (!getBranchCode().equals("")) {
            Matcher billNoCheck3 = p3.matcher(this.getBranchCode());
            if (!billNoCheck3.matches()) {
                this.setBranchCode("");
                return "Branch code not valid, use (A-Z,a-z,0-9)..";
            }
        }
        if (bankCode.equalsIgnoreCase("")) {
            return "Please enter the bank code";
        }
        if (enableFlag.equalsIgnoreCase("")) {
            return "Please enter the enable flag";
        }
        return "true";
    }

    public void refresh() {
        this.setMessage("");
        this.setBankCode("");
        this.setBranchCode("");
        this.setChargesFlag("");
        this.setEnableFlag("");
        this.setDelStatusFlag("");
        this.setPaySysId("");
        update = true;
        add = true;
    }

    public void refreshOther() {
        this.setBankCode("");
        this.setBranchCode("");
        this.setChargesFlag("");
        this.setEnableFlag("");
        this.setDelStatusFlag("");
        this.setPaySysId("");
        update = true;
        add = true;
    }

    public String exitButton() {
        refresh();
        return "case1";
    }
}
