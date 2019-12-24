/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto.other;

/**
 *
 * @author Admin
 */
public class UserInfo {

    int serialNo;

    String userId;

    String userName;

    String status;

    String allowLogin;

    String cashierAccess;

    String headCashierAccess;

    public String getHeadCashierAccess() {
        return headCashierAccess;
    }

    public void setHeadCashierAccess(String headCashierAccess) {
        this.headCashierAccess = headCashierAccess;
    }

    public String getAllowLogin() {
        return allowLogin;
    }

    public void setAllowLogin(String allowLogin) {
        this.allowLogin = allowLogin;
    }

    public String getCashierAccess() {
        return cashierAccess;
    }

    public void setCashierAccess(String cashierAccess) {
        this.cashierAccess = cashierAccess;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    

}
