/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class ExistingLoan implements Serializable{
    private String existingLoan;
    private String loanAmount;

    public String getExistingLoan() {
        return existingLoan;
    }

    public void setExistingLoan(String existingLoan) {
        this.existingLoan = existingLoan;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }


}
