/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class AccountLoanLimitAuthorizationGrid implements Serializable{
    
    String accountNo;
    String txnId;


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }



}
