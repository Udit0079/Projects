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
public class SortedByProductCode implements Comparator<NpaSummaryPojo>{

    @Override
    public int compare(NpaSummaryPojo o1, NpaSummaryPojo o2) {
        return o1.getProductCode().compareTo(o2.getProductCode());
    }
    
}
