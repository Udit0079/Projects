/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.master.ExpenditureLimitFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Deepshikha
 */
public class ExpenditureLimit {

    String message;
    String bankName;
    String branchName;
    String amount;
    List<SelectItem> branchNamesList;
    ExpenditureLimitFacadeRemote expLimitRemote;
    private HttpServletRequest request;
    private String todayDate;
    private String userName;
    private String brncode;
    String flag = "false";
    String flag1 = "false";

    public List<SelectItem> getBranchNamesList() {
        return branchNamesList;
    }

    public void setBranchNamesList(List<SelectItem> branchNamesList) {
        this.branchNamesList = branchNamesList;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getTodayDate() {
        return todayDate;
    }

    private void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBrncode() {
        return brncode;
    }

    public void setBrncode(String brncode) {
        this.brncode = brncode;
    }

    public String exitBtn() {
        return "case1";

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public ExpenditureLimit() {

        try {
            expLimitRemote = (ExpenditureLimitFacadeRemote) ServiceLocator.getInstance().lookup("ExpenditureLimitFacade");
            branchNamesList = new ArrayList<SelectItem>();
            request = getRequest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            brncode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            getBranch();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }


    }

    private HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    private void getBranch() {
        branchNamesList = new ArrayList<SelectItem>();
        branchNamesList.add(new SelectItem("0", "--Select--"));
        List list = expLimitRemote.loadBranchNames();
        Vector vtr;
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                vtr = (Vector) list.get(i);
                branchNamesList.add(new SelectItem(vtr.get(0).toString().length()==2?vtr.get(0).toString():"0"+vtr.get(0).toString(), vtr.get(1).toString()));
            }
        }
    }

    public void refreshBtn() {
        message = "";
        this.setBranchName("0");
        this.setAmount("0");
        flag1 = "true";
        flag = "false";
    }

    public void saveDetails() {
          if(validate()){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            String msg = expLimitRemote.saveData(sdf.format(new Date()), branchName, amount);
            if (!msg.equalsIgnoreCase("Data has been saved successfully")) {
                this.setMessage(msg);
                return;
            } else {
                this.setMessage(msg);
                this.setBranchName("0");
                this.setAmount("0");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
          }
    }

    public void updateDetails() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String msg = expLimitRemote.updateData(userName, branchName, amount);
            if (!msg.equalsIgnoreCase("Data has been saved!")) {
                this.setMessage(msg);
                return;
            } else {
                this.setMessage(msg);
                this.setBranchName("0");
                this.setAmount("0");
            }
        } catch (Exception e) {
        }

    }

    public void getLimitDetails() {
        try {
            amount = expLimitRemote.getLimitDetails(branchName) + "";
            if (amount.equals("0") || amount.equals("0.0")) {
                flag1 = "true";
                flag = "false";
            } else {
                flag = "true";
                flag1 = "false";
            }
        } catch (Exception e) {
        }
    }
    
    
     private boolean validate() {
        if ( branchName== null || branchName.trim().equals("")) {
     message ="Please Select the Branch";
            return false;
        } else if (amount.equals("0") || amount.equals("0.0")) {
     message ="Enter Limit";
            return false;
        }
        return true;
    }
}
