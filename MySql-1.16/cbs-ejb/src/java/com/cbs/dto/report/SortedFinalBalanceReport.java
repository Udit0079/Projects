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
public class SortedFinalBalanceReport implements Comparator<FinalBalanceReportPojo> {

    public int compare(FinalBalanceReportPojo object1, FinalBalanceReportPojo object2) {
        return object1.getAccNo().compareTo(object2.getAccNo());
    }
}
