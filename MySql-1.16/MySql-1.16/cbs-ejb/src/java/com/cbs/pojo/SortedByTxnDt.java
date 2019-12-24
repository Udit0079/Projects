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
public class SortedByTxnDt implements Comparator<CashDepositAggregateAnyOneDayPojo> {

    public int compare(CashDepositAggregateAnyOneDayPojo obj1, CashDepositAggregateAnyOneDayPojo obj2) {
        return obj1.getTxnDate().compareTo(obj2.getTxnDate());
    }
}
