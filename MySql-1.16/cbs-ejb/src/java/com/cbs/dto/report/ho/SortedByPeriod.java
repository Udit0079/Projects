/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByPeriod implements Comparator<ReportProfilePojo> {
    public int compare(ReportProfilePojo o1, ReportProfilePojo o2) {
        return o1.getPeriod().compareTo(o2.getPeriod());
    }
}
