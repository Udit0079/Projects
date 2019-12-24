/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.complexTO;

import com.hrms.common.to.HrTaxInvestmentCategoryTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class TaxInvestmentCategoryTO implements Serializable {

    private HrTaxInvestmentCategoryTO toObj;
    private String empName;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public HrTaxInvestmentCategoryTO getToObj() {
        return toObj;
    }

    public void setToObj(HrTaxInvestmentCategoryTO toObj) {
        this.toObj = toObj;
    }
}
