/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author Athar Reza
 */
public class AlmFdshortedByBktNo  implements Comparator<AlmFddetailPojo>{
     public int compare(AlmFddetailPojo object1, AlmFddetailPojo object2) {
        return object1.getBktNo().compareTo(object2.getBktNo());
    }
}
