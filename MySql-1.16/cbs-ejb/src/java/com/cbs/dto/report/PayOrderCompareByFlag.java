/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author Zeeshan Waris
 */
public class PayOrderCompareByFlag implements Comparator<PayOrderPojo> {

    public int compare(PayOrderPojo obj1, PayOrderPojo obj2) {
        return  obj1.getFlag().compareTo(obj2.getFlag());
    }
}
