/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByRoi implements Comparator<ReportProfilePojo> {
    public int compare(ReportProfilePojo o1, ReportProfilePojo o2) {
        return new BigDecimal(o1.getRoi()).compareTo(new BigDecimal(o2.getRoi()));
    }
}
