/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.loan;

import java.util.Comparator;

/**
 *
 * @author root
 */
public class SortedByBranch implements Comparator<InterestCalculationPojo>{

    @Override
    public int compare(InterestCalculationPojo o1, InterestCalculationPojo o2) {
        return o1.getBranch().compareTo(o2.getBranch());
                
    }
    
}
