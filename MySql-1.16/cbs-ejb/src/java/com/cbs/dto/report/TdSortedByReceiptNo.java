/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author sipl
 */
public class TdSortedByReceiptNo implements Comparator<TdReceiptIntDetailPojo> {
    public int compare(TdReceiptIntDetailPojo object1, TdReceiptIntDetailPojo object2) {
        return object1.getReceiptNo().compareTo(object2.getReceiptNo());
    } 
}