/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import com.cbs.dto.LoanIntCalcList;
import java.util.Comparator;

/**
 *
 * @author root
 */
public class LoanIntCalcListSort  implements Comparator<LoanIntCalcList> {
    
    public int compare(LoanIntCalcList obj1, LoanIntCalcList obj2) {
        return obj1.getAcNo().compareTo(obj2.getAcNo());
    }
    
}
