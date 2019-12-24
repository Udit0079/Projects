/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByAgCode  implements Comparator<DDSAgentNaturePojo>{

    public int compare(DDSAgentNaturePojo obj1, DDSAgentNaturePojo obj2) {
        return obj1.getAgcode().compareTo(obj2.getAgcode());
    }
}
