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
public class SortedByTokenNo implements Comparator<StatemenrtOfRecoveriesPojo>{

    public int compare(StatemenrtOfRecoveriesPojo o1, StatemenrtOfRecoveriesPojo o2) {
        return o1.getPersonalTokenNo().compareTo(o2.getPersonalTokenNo());
    }
    
}
