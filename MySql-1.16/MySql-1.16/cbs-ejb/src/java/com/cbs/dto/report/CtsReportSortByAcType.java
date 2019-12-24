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
public class CtsReportSortByAcType implements Comparator<CtsChequeStatusReportPojo> {

    public int compare(CtsChequeStatusReportPojo o1, CtsChequeStatusReportPojo o2) {
        return o1.getAcType().compareTo(o2.getAcType());
    }
}
