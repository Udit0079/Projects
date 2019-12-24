/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo;

import com.cbs.dto.report.OutwardClearingEnteredReportPojo;
import java.util.Comparator;

/**
 *
 * @author root
 */
public class CompareOwByAcType implements Comparator<OutwardClearingEnteredReportPojo> {

    public int compare(OutwardClearingEnteredReportPojo obj1, OutwardClearingEnteredReportPojo obj2) {
        return obj1.getActype().compareTo(obj2.getActype());
    }
}
