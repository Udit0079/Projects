/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author root
 */
public class SortedByAcnoStatus implements Comparator<LoanPeriodPojo>{

    @Override
    public int compare(LoanPeriodPojo o1, LoanPeriodPojo o2) {
        return o1.getStatus().compareTo(o2.getStatus());
    }
    
}
