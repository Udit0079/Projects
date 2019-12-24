/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto;

import java.util.Comparator;

/**
 *
 * @author root
 */
public class DateByComparator implements Comparator<RdIntDetail> {
    public int compare(RdIntDetail rdIntDetailObj1, RdIntDetail rdIntDetailObj2){
        Long l1 = rdIntDetailObj1.getDate().getTime();
        Long l2 = rdIntDetailObj2.getDate().getTime();
        return l1.compareTo(l2);
    }
}
