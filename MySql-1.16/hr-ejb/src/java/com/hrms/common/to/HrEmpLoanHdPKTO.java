/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class HrEmpLoanHdPKTO implements Serializable {

    private long empLoanNo;

    private int compCode;

    private long empCode;

    private String loanType;

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public long getEmpLoanNo() {
        return empLoanNo;
    }

    public void setEmpLoanNo(long empLoanNo) {
        this.empLoanNo = empLoanNo;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    
}
