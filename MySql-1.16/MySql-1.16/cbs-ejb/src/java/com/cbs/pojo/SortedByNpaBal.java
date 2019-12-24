/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByNpaBal implements Comparator<NpaStatusReportPojo> {

    public int compare(NpaStatusReportPojo o1, NpaStatusReportPojo o2) {
        return o1.getBalance().compareTo(o2.getBalance());
    }
}
