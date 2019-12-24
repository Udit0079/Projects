/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import com.cbs.dto.report.CashDepositAggregateAnyOneDayPojo;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByDepAmt implements Comparator<CashDepositAggregateAnyOneDayPojo> {

    public int compare(CashDepositAggregateAnyOneDayPojo obj1, CashDepositAggregateAnyOneDayPojo obj2) {
        return new Double(obj1.getAmount()).compareTo(new Double(obj2.getAmount()));
    }
}
