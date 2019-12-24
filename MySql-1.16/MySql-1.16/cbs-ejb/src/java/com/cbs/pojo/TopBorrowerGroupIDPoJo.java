/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import com.cbs.dto.report.TopDepositorBorrowerPojo;
import java.util.Comparator;

/**
 *
 * @author SAMY
 */
public class TopBorrowerGroupIDPoJo implements Comparator<TopDepositorBorrowerPojo> {
    public int compare(TopDepositorBorrowerPojo o1, TopDepositorBorrowerPojo o2) {
        return o1.getGroupId().compareTo(o2.getGroupId());
    }
}
