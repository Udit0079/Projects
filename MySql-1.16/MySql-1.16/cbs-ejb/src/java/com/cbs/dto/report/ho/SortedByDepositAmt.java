/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import com.cbs.pojo.LoanPrincipalInterestPojo;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByDepositAmt implements Comparator<LoanPrincipalInterestPojo> {

    public int compare(LoanPrincipalInterestPojo o1, LoanPrincipalInterestPojo o2) {
        return new BigDecimal(o1.getDepositAmt()).compareTo(new BigDecimal(o2.getDepositAmt()));
    }
}
