/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 *
 * @author sipl
 */
public class TdSortedByDate implements Comparator<TdReceiptIntDetailPojo>{
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    int i;
    public int compare(TdReceiptIntDetailPojo object1, TdReceiptIntDetailPojo object2) {
        try{
            i = dmy.parse(object1.getDate()).compareTo(dmy.parse(object2.getDate()));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}