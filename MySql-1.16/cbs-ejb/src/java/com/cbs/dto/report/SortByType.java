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
public class SortByType implements Comparator<ConsolidateProfitLossPojo> {

    public int compare(ConsolidateProfitLossPojo obj1, ConsolidateProfitLossPojo obj2) {
        return obj1.getType().compareTo(obj2.getType());
    }
}
