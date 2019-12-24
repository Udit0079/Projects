/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import com.cbs.dto.report.LoanMisCellaniousPojo;
import java.util.Comparator;

/**
 *
 * @author Athar Raza
 */
public class SortedBySector implements Comparator<LoanMisCellaniousPojo>{
     public int compare(LoanMisCellaniousPojo object1, LoanMisCellaniousPojo object2) {
        return object1.getSector().compareTo(object2.getSector());
    }
    
}
