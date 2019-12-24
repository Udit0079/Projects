/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.util.Comparator;

/**
 *
 * @author root
 */
public class GlHeadPojoComparatorForBal implements Comparator<GlHeadPojo> {

    public int compare(GlHeadPojo one, GlHeadPojo two) {
        return one.getBalance().compareTo(two.getBalance());
    }
}
