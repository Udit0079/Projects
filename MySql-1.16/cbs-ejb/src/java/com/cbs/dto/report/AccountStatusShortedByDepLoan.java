/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class AccountStatusShortedByDepLoan implements Comparator<AccountStatusReportPojo>{
     public int compare(AccountStatusReportPojo object1, AccountStatusReportPojo object2) {
        return object1.getDepositLoan().compareTo(object2.getDepositLoan());
    }
    
}
