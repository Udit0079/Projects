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
public class OverdueEmiReportSortedByAcNo  implements Comparator<OverdueEmiReportPojo> {
    
    public int compare(OverdueEmiReportPojo obj1, OverdueEmiReportPojo obj2) {
        return obj1.getAccountNumber().compareTo(obj2.getAccountNumber());
    }
}
