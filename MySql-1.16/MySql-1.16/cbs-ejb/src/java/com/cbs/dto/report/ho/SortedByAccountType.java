/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import com.cbs.dto.report.LoanMisCellaniousPojo;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByAccountType implements Comparator<LoanMisCellaniousPojo>{
    public int compare(LoanMisCellaniousPojo object1, LoanMisCellaniousPojo object2) {
        return object1.getAcType().compareTo(object2.getAcType());
    }
}
