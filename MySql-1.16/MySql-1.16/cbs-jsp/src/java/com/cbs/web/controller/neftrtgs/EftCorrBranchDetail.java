/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.EPSCorrBranchDetail;
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
public class EftCorrBranchDetail extends BaseBean {

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
    private String corrAccNo;
    private String message;
    private boolean add;
    private boolean update;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public String getCorrAccNo() {
        return corrAccNo;
    }

    public void setCorrAccNo(String corrAccNo) {
        this.corrAccNo = corrAccNo;
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

    /** Creates a new instance of BranchMaster */
    public EftCorrBranchDetail() {
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
            String mes = valdations();
            if (!mes.equalsIgnoreCase("true")) {
                this.setMessage(mes);
                return;
            }
            EPSCorrBranchDetail tableObj = new EPSCorrBranchDetail(this.getBankCode(), this.getBranchCode(), this.getBranchName(), this.getShortName(), this.getAddress(), this.getTradeFinanceFlag(), this.getRegionCode(), this.getZoneCode(), this.getIfsCode(), this.getCorrAccNo());
            String rsmsg = remoteInterface.saveEpsCorrbranchDetails(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been successfuly saved");
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
            EPSCorrBranchDetail tableObj = new EPSCorrBranchDetail(this.getBankCode(), this.getBranchCode(), this.getBranchName(), this.getShortName(), this.getAddress(), this.getTradeFinanceFlag(), this.getRegionCode(), this.getZoneCode(), this.getIfsCode(), this.getCorrAccNo());
            String rsmsg = remoteInterface.updateEpsCorrbranchDetails(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been updated successfuly");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    public void onBlurBranchCode() {
        try {
            if (branchCode.equalsIgnoreCase("")) {
                this.setMessage("Please enter branch code");
                return;
            }
            EPSCorrBranchDetail entity = remoteInterface.getEpsCorrbranchDetails(this.getBranchCode());
            if (entity != null) {
                setMessage("");
                update = false;
                add = true;
                setBankCode(entity.getBankCode());
                setBranchCode(entity.getBranchCode());
                setBranchName(entity.getBranchName());
                setShortName(entity.getShortName());
                setAddress(entity.getAddress());
                setTradeFinanceFlag(entity.getTradeFinanceFlag());
                setRegionCode(entity.getRegionCode());
                setZoneCode(entity.getZoneCode());
                setIfsCode(entity.getIFSCode());
                setCorrAccNo(entity.getCorrAccNo());
            }
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                update = true;
                add = false;
                setMessage("");
                setBankCode("");
                setBranchName("");
                setShortName("");
                setAddress("");
                setTradeFinanceFlag("");
                setRegionCode("");
                setZoneCode("");
                setIfsCode("");
                setCorrAccNo("");
            }
        }
    }

    public String valdations() {
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
        if (branchName.equalsIgnoreCase("")) {
            return "Please enter the branch name";
        }
        if (address.equalsIgnoreCase("")) {
            return "Please enter the address";
        }
        if (ifsCode.equalsIgnoreCase("")) {
            return "Please enter the ifs code";
        }
        if (getIfsCode().length() != 11) {
            return "Ifs code could not be less than 11";
        }
        if (!getIfsCode().equals("")) {
            Matcher billNoCheck3 = p3.matcher(this.getIfsCode());
            if (!billNoCheck3.matches()) {
                this.setIfsCode("");
                return "IFS code not valid, use (A-Z,a-z,0-9)..";
            }
        }
        if (corrAccNo.equalsIgnoreCase("")) {
            return "Please enter the correspondent account no..";
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
        setCorrAccNo("");
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
        setCorrAccNo("");
        update = true;
        add = true;
    }

    public String exitButton() {
        refresh();
        return "case1";
    }
}
