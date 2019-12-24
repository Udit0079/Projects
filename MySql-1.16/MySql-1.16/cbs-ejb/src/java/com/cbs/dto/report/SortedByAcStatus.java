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
public class SortedByAcStatus implements Comparator<NpaAccountDetailPojo>{

    public int compare(NpaAccountDetailPojo o1, NpaAccountDetailPojo o2) {
       return o1.getAcStatus().compareTo(o2.getAcStatus());
    }
    
}
