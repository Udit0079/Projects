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
public class SortSummaryByNPA implements Comparator<NpaSummaryPojo> {

    public int compare(NpaSummaryPojo obj1, NpaSummaryPojo obj2) {
        return obj1.getNpa().compareTo(obj2.getNpa());
    }
}
