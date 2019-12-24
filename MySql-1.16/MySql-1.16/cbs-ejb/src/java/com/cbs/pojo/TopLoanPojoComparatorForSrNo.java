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
public class TopLoanPojoComparatorForSrNo  implements Comparator<TopLoanPojo> {
    
    public int compare(TopLoanPojo o1, TopLoanPojo o2) {
        return o1.getSno().compareTo(o2.getSno());
    }
    
}
