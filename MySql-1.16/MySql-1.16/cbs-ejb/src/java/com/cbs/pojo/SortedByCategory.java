/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import com.cbs.dto.report.ProvDetailOfLoanAccounts;
import java.util.Comparator;

/**
 *
 * @author saurabhsipl
 */
public class SortedByCategory implements Comparator<ProvDetailOfLoanAccounts> {

    public int compare(ProvDetailOfLoanAccounts o1, ProvDetailOfLoanAccounts o2) {
        return o1.getCategory().compareTo(o2.getCategory());
    }
    
}
