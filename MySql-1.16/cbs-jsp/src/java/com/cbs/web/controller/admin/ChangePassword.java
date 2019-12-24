/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;

/**
 *
 * @author root
 */
public class ChangePassword extends BaseBean {

    
    private String oldPassword;
    private String newPassword;
    private String reNewPassword;
    private final String jndiHomeName = "AdminManagementFacade";
    private AdminManagementFacadeRemote beanLocal = null;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getReNewPassword() {
        return reNewPassword;
    }

    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }

    public ChangePassword() {
        this.setMessage("");
    }

    public void btnChangeaction() {
        try {
            beanLocal = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            String result = beanLocal.btnChangeAction(this.getOldPassword(), this.getNewPassword(), this.getReNewPassword(),getUserName());
            this.setMessage(result);
            
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } 
    }

    public void cancelButton() {
        this.setMessage("");
        this.setNewPassword("");
        this.setOldPassword("");
        this.setReNewPassword("");
    }

    public String exitBtnAction(){
        cancelButton();
        return "case1";
    }

    public String exitLogAction(){
        cancelButton();
        return "case2";
    }
}
