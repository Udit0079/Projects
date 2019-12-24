/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.EPSChargeDetails;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;

/**
 *
 * @author root
 */
public class EFTChargeDetails extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String message;
    private String chargeEventId;
    private String paySysId;
    private Double minLimit;
    private Double maxLimit;
    private Double chargeAmt;
    private Double chargeRoi;
    private Double sTax;
    private String sTaxHolder;
    private String plHolder;
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

    public String getChargeEventId() {
        return chargeEventId;
    }

    public void setChargeEventId(String chargeEventId) {
        this.chargeEventId = chargeEventId;
    }

    public String getPaySysId() {
        return paySysId;
    }

    public void setPaySysId(String paySysId) {
        this.paySysId = paySysId;
    }

    public String getPlHolder() {
        return plHolder;
    }

    public void setPlHolder(String plHolder) {
        this.plHolder = plHolder;
    }

    public String getsTaxHolder() {
        return sTaxHolder;
    }

    public void setsTaxHolder(String sTaxHolder) {
        this.sTaxHolder = sTaxHolder;
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

    public Double getChargeAmt() {
        return chargeAmt;
    }

    public void setChargeAmt(Double chargeAmt) {
        this.chargeAmt = chargeAmt;
    }

    public Double getChargeRoi() {
        return chargeRoi;
    }

    public void setChargeRoi(Double chargeRoi) {
        this.chargeRoi = chargeRoi;
    }

    public Double getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Double maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Double getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(Double minLimit) {
        this.minLimit = minLimit;
    }

    public Double getsTax() {
        return sTax;
    }

    public void setsTax(Double sTax) {
        this.sTax = sTax;
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

    /** Creates a new instance of EFTChargeDetails */
    public EFTChargeDetails() {
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

    public void onBlurChargeEventId() {
        this.setMessage("");
        try {
            if (this.getChargeEventId().equalsIgnoreCase("")) {
                this.setMessage("Please Enter Charge Event ID");
                return;
            }
            EPSChargeDetails chargeDetail = remoteInterface.getEpsChargeDetails(this.getChargeEventId());
            if (chargeDetail != null) {
                this.setChargeEventId(chargeDetail.getChargeEventID());
                this.setPaySysId(chargeDetail.getPaySysId());
                this.setMinLimit(chargeDetail.getMinLimit());
                this.setMaxLimit(chargeDetail.getMaxLimit());
                this.setChargeAmt(chargeDetail.getChargeAmt());
                this.setChargeRoi(chargeDetail.getChargeROT());
                this.setsTax(chargeDetail.getStax());
                this.setsTaxHolder(chargeDetail.getStaxPlaceHolder());
                this.setPlHolder(chargeDetail.getPLPlaceHolder());
                update = false;
                add = true;
            }
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                update = true;
                add = false;
                this.setPaySysId("");
                this.setMinLimit(0d);
                this.setMaxLimit(0d);
                this.setChargeAmt(0d);
                this.setChargeRoi(0d);
                this.setsTax(0d);
                this.setsTaxHolder("");
                this.setPlHolder("");
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
            EPSChargeDetails tableObj = new EPSChargeDetails(this.getChargeEventId(), this.getPaySysId(), this.getMinLimit(), this.getMaxLimit(), this.getChargeAmt(), this.getChargeRoi(), this.getsTax(), this.getsTaxHolder(), this.getPlHolder());
            String rsmsg = remoteInterface.saveEpsChargeDetails(tableObj);
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
            EPSChargeDetails tableObj = new EPSChargeDetails(this.getChargeEventId(), this.getPaySysId(), this.getMinLimit(), this.getMaxLimit(), this.getChargeAmt(), this.getChargeRoi(), this.getsTax(), this.getsTaxHolder(), this.getPlHolder());
            String rsmsg = remoteInterface.updateEpsChargeDetails(tableObj);
            if (rsmsg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been updated successfuly");
            }
            refreshOther();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String valdations() {
        if (this.getChargeEventId().equalsIgnoreCase("")) {
            return "Please Enter Charge Event ID";
        }
        if (this.getPaySysId().equalsIgnoreCase("")) {
            return "Please Enter Pay. Sys ID";
        }
        return "true";
    }

    public void refresh() {
        this.setMessage("");
        this.setChargeEventId("");
        this.setPaySysId("");
        this.setMinLimit(0d);
        this.setMaxLimit(0d);
        this.setChargeAmt(0d);
        this.setChargeRoi(0d);
        this.setsTax(0d);
        this.setsTaxHolder("");
        this.setPlHolder("");
        update = true;
        add = true;
    }

    public void refreshOther() {
        this.setChargeEventId("");
        this.setPaySysId("");
        this.setMinLimit(0d);
        this.setMaxLimit(0d);
        this.setChargeAmt(0d);
        this.setChargeRoi(0d);
        this.setsTax(0d);
        this.setsTaxHolder("");
        this.setPlHolder("");
        update = true;
        add = true;
    }

    public String exitButton() {
        refresh();
        return "case1";
    }
}
