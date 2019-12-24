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
public class SortByCompSGno implements Comparator<CbsCompGeneralLedgerBookPojo>{
    public int compare(CbsCompGeneralLedgerBookPojo obj1, CbsCompGeneralLedgerBookPojo obj2){
        return new Integer(obj1.getSGNO()).compareTo(new Integer(obj2.getSGNO()));
    } 
}
