/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import com.cbs.dto.report.LoanOutStandingBalancePojo;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByBalanceForBalWiseReport implements Comparator <LoanOutStandingBalancePojo>{
     public int compare(LoanOutStandingBalancePojo o1, LoanOutStandingBalancePojo o2) {
       return new BigDecimal(o1.getBal()).compareTo(new BigDecimal(o2.getBal()));
    }
    
}
