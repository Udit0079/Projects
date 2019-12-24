/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author root
 */
public class ComparatorForAcStatusInString implements Comparator<NpaAccountDetailPojo>{
    public int compare(NpaAccountDetailPojo object1, NpaAccountDetailPojo object2) {
        return object1.getSortAcStatus().compareTo(object2.getSortAcStatus());
    }    
}
