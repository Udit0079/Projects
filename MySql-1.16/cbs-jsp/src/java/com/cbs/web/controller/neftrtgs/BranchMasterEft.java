/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.EPSBranchMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class BranchMasterEft extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String bankCode;
    private String branchCode;
    private String branchName;
    private String shortName;
    private String address;
    private String tradeFinanceFlag;
    private String regionCode;
    private String zoneCode;
    private String ifsCode;
    private String message;
    private boolean add;
    private boolean update;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfsCode() {
        return ifsCode;
    }

    public void setIfsCode(String ifsCode) {
        this.ifsCode = ifsCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTradeFinanceFlag() {
        return tradeFinanceFlag;
    }

    public void setTradeFinanceFlag(String tradeFinanceFlag) {
        this.tradeFinanceFlag = tradeFinanceFlag;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
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

    /** Creates a new instance of BranchMasterEft */
    public BranchMasterEft() {
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

    public void saveButton() {
        try {
            String msg = valdations();
            if (!msg.equalsIgnoreCase("true")) {
                this.setMessage(msg);
                return;
            }
            EPSBranchMaster tableObj = new EPSBranchMaster(this.getBranchCode(), this.getBankCode(), this.getBranchName(), this.getShortName(), this.getAddress(), this.getTradeFinanceFlag(), this.getRegionCode(), this.getZoneCode(), this.getIfsCode());
            String rsmsg = remoteInterface.saveEpsBranchMaster(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been saved successfuly");
            }
            refreshOther();
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
            EPSBranchMaster tableObj = new EPSBranchMaster(this.getBranchCode(), this.getBankCode(), this.getBranchName(), this.getShortName(), this.getAddress(), this.getTradeFinanceFlag(), this.getRegionCode(), this.getZoneCode(), this.getIfsCode());
            String rsmsg = remoteInterface.updateEpsBranchMaster(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been updated successfuly");
            }
            refreshOther();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onBlurBranchCode() {
        this.setMessage("");
        try {
            if (branchCode.equalsIgnoreCase("")) {
                this.setMessage("Please enter branch code");
                return;
            }
            EPSBranchMaster entity = remoteInterface.getEpsBranchMaster(this.getBranchCode());
            if (entity != null) {
                this.setBankCode(entity.getBankCode());
                this.setBranchCode(entity.getBranchCode());
                this.setBranchName(entity.getBranchName());
                this.setShortName(entity.getShortName());
                this.setAddress(entity.getAddress());
                this.setTradeFinanceFlag(entity.getTradeFinanceFlag());
                this.setRegionCode(entity.getRegionCode());
                this.setZoneCode(entity.getZoneCode());
                this.setIfsCode(entity.getIFSCode());
                update = false;
                add = true;
            }
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                update = true;
                add = false;
                this.setMessage("");
                this.setBankCode("");
                this.setBranchName("");
                this.setShortName("");
                this.setAddress("");
                this.setTradeFinanceFlag("");
                this.setRegionCode("");
                this.setZoneCode("");
                this.setIfsCode("");
            }
        }
    }

    public String valdations() {
        if (branchCode.equalsIgnoreCase("")) {
            return "Please enter the branch code";
        }
        Pattern p3 = Pattern.compile("[A-Za-z0-9]+[ \t\r\n]*");
        if (!getBranchCode().equals("")) {
            Matcher ifscodemapper = p3.matcher(this.getBranchCode());
            if (!ifscodemapper.matches()) {
                this.setBranchCode("");
                return "branch code not valid,use (A-Z,a-z,0-9)..";
            }
        }
        if (bankCode.equalsIgnoreCase("")) {
            return "Please enter the bank code";
        }
        if (!getBankCode().equals("")) {
            Matcher ifscodemapper = p3.matcher(this.getBankCode());
            if (!ifscodemapper.matches()) {
                this.setBankCode("");
                return "Bank code not valid,use (A-Z,a-z,0-9)..";
            }
        }
        if (branchName.equalsIgnoreCase("")) {
            return "Please enter the branch name";
        }
        if (!getBranchName().equals("")) {
            Matcher ifscodemapper = p3.matcher(this.getBranchName());
            if (!ifscodemapper.matches()) {
                this.setBranchName("");
                return "Branch name not valid, use (A-Z,a-z,0-9)..";
            }
        }
        if (shortName.equalsIgnoreCase("")) {
            return "Please enter the short name";
        }
        if (!getShortName().equals("")) {
            Matcher ifscodemapper = p3.matcher(this.getShortName());
            if (!ifscodemapper.matches()) {
                this.setIfsCode("");
                return "Short name not valid,use (A-Z,a-z,0-9)..";
            }
        }
        if (address.equalsIgnoreCase("")) {
            return "Please enter the address";
        }
        if (!getAddress().equals("")) {
            Matcher ifscodemapper = p3.matcher(this.getAddress());
            if (!ifscodemapper.matches()) {
                this.setAddress("");
                return "Address not valid,use (A-Z,a-z,0-9)..";
            }
        }
        if (!getTradeFinanceFlag().equals("")) {
            Matcher ifscodemapper = p3.matcher(this.getTradeFinanceFlag());
            if (!ifscodemapper.matches()) {
                this.setTradeFinanceFlag("");
                return "Trade finance flag not valid,use (A-Z,a-z,0-9)..";
            }
        }
        if (!getRegionCode().equals("")) {
            Matcher ifscodemapper = p3.matcher(this.getRegionCode());
            if (!ifscodemapper.matches()) {
                this.setRegionCode("");
                return "Region code not valid,use (A-Z,a-z,0-9)..";
            }
        }
        if (!getZoneCode().equals("")) {
            Matcher ifscodemapper = p3.matcher(getZoneCode());
            if (!ifscodemapper.matches()) {
                this.setZoneCode("");
                return "ifs Code not valid,use (A-Z,a-z,0-9)..";
            }
        }
        if (ifsCode.equalsIgnoreCase("")) {
            return "Please enter the ifs code";
        }
        if (ifsCode.length() != 11) {
            return "Please enter 11 digit ifs code";
        }
        if (!getIfsCode().equals("")) {
            Matcher ifscodemapper = p3.matcher(this.getIfsCode());
            if (!ifscodemapper.matches()) {
                this.setIfsCode("");
                return "Ifs Code not valid,use (A-Z,a-z,0-9)..";
            }
        }
        return "true";
    }

    public void refresh() {
        setMessage("");
        setAddress("");
        setBankCode("");
        setBranchCode("");
        setBranchName("");
        setIfsCode("");
        setRegionCode("");
        setShortName("");
        setZoneCode("");
        setTradeFinanceFlag("");
        update = true;
        add = true;
    }

    public void refreshOther() {
        setAddress("");
        setBankCode("");
        setBranchCode("");
        setBranchName("");
        setIfsCode("");
        setRegionCode("");
        setShortName("");
        setZoneCode("");
        setTradeFinanceFlag("");
        update = true;
        add = true;
    }

    public String exitButton() {
        refresh();
        return "case1";
    }
}
