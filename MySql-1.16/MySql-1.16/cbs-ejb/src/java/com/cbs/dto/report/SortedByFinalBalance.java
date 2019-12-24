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
public class SortedByFinalBalance implements Comparator<FinalBalanceReportPojo>{

    public int compare(FinalBalanceReportPojo o1, FinalBalanceReportPojo o2) {
        return o1.getCrDrFlag().compareTo(o2.getCrDrFlag());
    }
    
}
