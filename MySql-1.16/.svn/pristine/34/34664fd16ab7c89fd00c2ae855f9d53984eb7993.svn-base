/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class HoReconcileEntry {

    private HttpServletRequest request;
    private String newDate;
    private String userName;
    private String accDescType;
    private String balType;
    private String orgBrnCode;
    private List<SelectItem> acctDescOption;
    private List<SelectItem> balTypeOption;
    private String acdesc;
    private DailyMasterFacadeRemote rfr;
    private String msg;
    private boolean disableResponding;
    private boolean disableOriginating;
    private boolean disableReconcilation;

    public boolean isDisableReconcilation() {
        return disableReconcilation;
    }

    public void setDisableReconcilation(boolean disableReconcilation) {
        this.disableReconcilation = disableReconcilation;
    }

    public boolean isDisableResponding() {
        return disableResponding;
    }

    public void setDisableResponding(boolean disableResponding) {
        this.disableResponding = disableResponding;
    }

    public boolean isDisableOriginating() {
        return disableOriginating;
    }

    public void setDisableOriginating(boolean disableOriginating) {
        this.disableOriginating = disableOriginating;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAcdesc() {
        return acdesc;
    }

    public void setAcdesc(String acdesc) {
        this.acdesc = acdesc;
    }

    public String getAccDescType() {
        return accDescType;
    }

    public void setAccDescType(String accDescType) {
        this.accDescType = accDescType;
    }

    public List<SelectItem> getAcctDescOption() {
        return acctDescOption;
    }

    public void setAcctDescOption(List<SelectItem> acctDescOption) {
        this.acctDescOption = acctDescOption;
    }

    public String getBalType() {
        return balType;
    }

    public void setBalType(String balType) {
        this.balType = balType;
    }

    public List<SelectItem> getBalTypeOption() {
        return balTypeOption;
    }

    public void setBalTypeOption(List<SelectItem> balTypeOption) {
        this.balTypeOption = balTypeOption;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public HoReconcileEntry() {
        try {
            request = getRequest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setNewDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            rfr = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            disableOriginating = false;
            disableResponding = true;
            disableReconcilation = true;
            this.setMsg("");
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void saveOriginatingData() {
        try {
            String result = rfr.getTableDetailsHoreCouncile();
            if (result.equals("Data has been inserted.")) {
                this.setMsg("Data successfully saved for Originating button");
                disableOriginating = true;
                disableResponding = false;
                disableReconcilation = true;
            } else {
                this.setMsg("Error!! Data could not save, Or there is no data to save..");
                disableOriginating= false;
                disableResponding= true;
                disableReconcilation = true;
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public void saveRespondingData() {
        try {
            String result = rfr.getTableDetails1();
            if (result.equals("Data has been saved For Responding Entry Type")) {
                this.setMsg("Data has been save for Responding button");
                disableOriginating = true;
                disableResponding = true;
                disableReconcilation = false;
            } else {
                this.setMsg("Error!! Not successful,Perhaps there is no data to save");
                disableOriginating= true;
                disableResponding= false;
                disableReconcilation = true;
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public void saveReconsilationData() {
        try {
            String result = rfr.getTableDetails2();
            if (result.equals("Successful Transaction")) {
                this.setMsg("Successful Transaction");
            } else {
                this.setMsg("Error!! Transaction not successful");
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        try {
            disableOriginating = false;
            disableResponding = true;
            disableReconcilation = true;
            this.setMsg("");
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
        return "case1";
    }
}
