/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class AccountCloseRegisterGrid implements Serializable{

    public String acctNo;
    public String custName;
    public String closeBy;
    public String authorize;
    public String chqNo;
    public String issueDt;

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }

    public String getChqNo() {
        return chqNo;
    }

    public void setChqNo(String chqNo) {
        this.chqNo = chqNo;
    }

    public String getCloseBy() {
        return closeBy;
    }

    public void setCloseBy(String closeBy) {
        this.closeBy = closeBy;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    
    
}
