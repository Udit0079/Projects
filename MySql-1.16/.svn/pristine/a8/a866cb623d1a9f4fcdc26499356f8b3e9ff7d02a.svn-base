/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import com.cbs.dto.report.TopDepositorBorrowerPojo;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByBalance implements Comparator<TopDepositorBorrowerPojo>{
     public int compare(TopDepositorBorrowerPojo o1, TopDepositorBorrowerPojo o2) {
       return new BigDecimal(o1.getBal()).compareTo(new BigDecimal(o2.getBal()));
    }
    
}
