/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author sipl
 */
public class SortBySGno implements Comparator<CbsGeneralLedgerBookPojo>{
    public int compare(CbsGeneralLedgerBookPojo obj1, CbsGeneralLedgerBookPojo obj2){
        return new Integer(obj1.getSGNO()).compareTo(new Integer(obj2.getSGNO()));
    }  
    
}
