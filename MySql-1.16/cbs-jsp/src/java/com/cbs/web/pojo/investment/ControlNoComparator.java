/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.investment;

import java.util.Comparator;

/**
 *
 * @author root
 */
public class ControlNoComparator implements Comparator<CallMoneyPojo> {
    
    public int compare(CallMoneyPojo o1, CallMoneyPojo o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Object getting is null");
        }
        return new Integer(o2.getCtrlNo()).compareTo(new Integer(o1.getCtrlNo()));
    }
}
