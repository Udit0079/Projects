/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import com.cbs.dto.report.RbiSossPojo;
import java.util.Comparator;

/**
 *
 * @author root
 */
public class ComparatorByReportName implements Comparator<RbiSossPojo> {

    public int compare(RbiSossPojo o1, RbiSossPojo o2) {
        return o1.getReportName().compareTo(o2.getReportName());
    }
}
