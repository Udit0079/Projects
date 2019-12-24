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
public class AccountCloseauth implements Serializable{
    String accountNo;
    String name;
    String clossedby;
    String authorize;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }

    public String getClossedby() {
        return clossedby;
    }

    public void setClossedby(String clossedby) {
        this.clossedby = clossedby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
