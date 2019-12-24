/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByTy implements Comparator<CashTranRepPojo>{
     public int compare(CashTranRepPojo object1, CashTranRepPojo object2) {
        return object1.getFLAG().compareTo(object2.getFLAG());
    }  
    
}
