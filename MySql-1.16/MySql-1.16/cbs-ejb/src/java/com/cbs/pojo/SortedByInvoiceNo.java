/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.util.Comparator;

/**
 *
 * @author root
 */
public class SortedByInvoiceNo implements Comparator<GstReportPojo>{

    @Override
    public int compare(GstReportPojo o1, GstReportPojo o2) {
        return o1.getNo().compareTo(o2.getNo());
    }
    
}
