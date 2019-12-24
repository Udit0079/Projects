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
public class TdSortedByAcno implements Comparator<TdReceiptIntDetailPojo> {
    public int compare(TdReceiptIntDetailPojo object1, TdReceiptIntDetailPojo object2) {
        return object1.getAcNo().compareTo(object2.getAcNo());
    }    
}