/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import com.cbs.dto.report.AchEcsResponseStatusReportPojo;
import java.util.Comparator;

/**
 *
 * @author root
 */
public class SortedByAchEcsStatus implements Comparator<AchEcsResponseStatusReportPojo>{

    public int compare(AchEcsResponseStatusReportPojo o1, AchEcsResponseStatusReportPojo o2) {
        return o1.getStatus().compareTo(o2.getStatus());
    }
    
}
