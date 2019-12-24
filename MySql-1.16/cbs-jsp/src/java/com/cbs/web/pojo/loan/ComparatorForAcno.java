/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.loan;

import com.cbs.dto.report.NpaAccountDetailPojo;
import java.util.Comparator;

/**
 *
 * @author root
 */
public class ComparatorForAcno implements Comparator<Object> {

    public int compare(Object one, Object two) {
        NpaAccountDetailPojo o1 = (NpaAccountDetailPojo) one;
        NpaAccountDetailPojo o2 = (NpaAccountDetailPojo) two;
        return o1.getAcNo().compareTo(o2.getAcNo());
    }
}
