/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.EPSNodalBranchDetail;
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
public class EftNodalBranchDetail extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String bankCode;
    private String branchCode;
    private String PaySysId;
    private String CrAccId;
    private String InwardAccPlaceHolder;
    private String OutwardAccPlaceHolder;
    private String InwardSundryPlaceHolder;
    private String CommAccPlaceHolder;
    private String message;
    private boolean add;
    private boolean update;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public String getOutwardAccPlaceHolder() {
        return OutwardAccPlaceHolder;
    }

    public void setOutwardAccPlaceHolder(String OutwardAccPlaceHolder) {
        this.OutwardAccPlaceHolder = OutwardAccPlaceHolder;
    }

    public String getCommAccPlaceHolder() {
        return CommAccPlaceHolder;
    }

    public void setCommAccPlaceHolder(String CommAccPlaceHolder) {
        this.CommAccPlaceHolder = CommAccPlaceHolder;
    }

    public String getCrAccId() {
        return CrAccId;
    }

    public void setCrAccId(String CrAccId) {
        this.CrAccId = CrAccId;
    }

    public String getInwardAccPlaceHolder() {
        return InwardAccPlaceHolder;
    }

    public void setInwardAccPlaceHolder(String InwardAccPlaceHolder) {
        this.InwardAccPlaceHolder = InwardAccPlaceHolder;
    }

    public String getInwardSundryPlaceHolder() {
        return InwardSundryPlaceHolder;
    }

    public void setInwardSundryPlaceHolder(String InwardSundryPlaceHolder) {
        this.InwardSundryPlaceHolder = InwardSundryPlaceHolder;
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

    public EftNodalBranchDetail() {
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
            EPSNodalBranchDetail tableObj = new EPSNodalBranchDetail(CrAccId, bankCode, branchCode, PaySysId, InwardAccPlaceHolder, OutwardAccPlaceHolder, "", InwardSundryPlaceHolder, CommAccPlaceHolder);
            String rsmsg = remoteInterface.updateEpsNodalBranchDetails(tableObj);
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
            EPSNodalBranchDetail tableObj = new EPSNodalBranchDetail(CrAccId, bankCode, branchCode, PaySysId, InwardAccPlaceHolder, OutwardAccPlaceHolder, "", InwardSundryPlaceHolder, CommAccPlaceHolder);
            String rsmsg = remoteInterface.saveEpsNodalBranchDetails(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been successfuly saved");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurBranchCode() {
        try {
            if (CrAccId.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Cr Account Id");
                return;
            }
            EPSNodalBranchDetail brnData = remoteInterface.getEpsNodalBranchDetails(CrAccId);
            if (brnData != null) {
                setMessage("");
                update = false;
                add = true;
                setBankCode(brnData.getBankCode());
                setBranchCode(brnData.getBranchCode());
                setPaySysId(brnData.getPaySysId());
                setCrAccId(brnData.getOutwardPoolAccId());
                setInwardAccPlaceHolder(brnData.getInwardAccPlaceHolder());
                setOutwardAccPlaceHolder(brnData.getOutwardAccPlaceHolder());
                setInwardSundryPlaceHolder(brnData.getInwardSundryPlaceHolder());
                setCommAccPlaceHolder(brnData.getCommRecvAccPlaceHolder());

            }
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                update = true;
                add = false;
                setMessage("");
                setBankCode("");
                setBranchCode("");
                setPaySysId("");
                setInwardAccPlaceHolder("");
                setOutwardAccPlaceHolder("");
                setInwardSundryPlaceHolder("");
                setCommAccPlaceHolder("");
            }
        }
    }

    public String valdations() {
        if (CrAccId.equalsIgnoreCase("")) {
            return "Please enter the Cr A/c Id";
        }
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
        return "true";
    }

    public void refresh() {
        setMessage("");
        setBankCode("");
        setBranchCode("");
        setCommAccPlaceHolder("");
        setCrAccId("");
        setInwardAccPlaceHolder("");
        setOutwardAccPlaceHolder("");
        setPaySysId("");
        setInwardSundryPlaceHolder("");
        update = true;
        add = true;
    }

    public void refreshOther() {
        setBankCode("");
        setBranchCode("");
        setCommAccPlaceHolder("");
        setCrAccId("");
        setInwardAccPlaceHolder("");
        setOutwardAccPlaceHolder("");
        setPaySysId("");
        setInwardSundryPlaceHolder("");
        update = true;
        add = true;
    }

    public String exitButton() {
        refresh();
        return "case1";
    }
}
