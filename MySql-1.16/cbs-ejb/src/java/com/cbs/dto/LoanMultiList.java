/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto;

import java.util.List;

/**
 *
 * @author admin
 */
public class LoanMultiList {
    
    private List loanIntDetails;
    private List loanProductIntDetails;

    public List getLoanIntDetails() {
        return loanIntDetails;
    }

    public void setLoanIntDetails(List loanIntDetails) {
        this.loanIntDetails = loanIntDetails;
    }

    public List getLoanProductIntDetails() {
        return loanProductIntDetails;
    }

    public void setLoanProductIntDetails(List loanProductIntDetails) {
        this.loanProductIntDetails = loanProductIntDetails;
    }
    
    

}
