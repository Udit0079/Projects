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
public class OverdueEmiReportSortedByMoratorium implements Comparator<OverdueEmiReportPojo>{

    public int compare(OverdueEmiReportPojo o1, OverdueEmiReportPojo o2) {
       return o1.getMoratoriumFlag().compareTo(o2.getMoratoriumFlag());
       
    }
    
}
