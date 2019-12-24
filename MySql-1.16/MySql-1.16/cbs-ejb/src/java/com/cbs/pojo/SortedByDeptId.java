/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortedByDeptId implements Comparator<VillageWiseEMIDetailPojo>{
    public int compare(VillageWiseEMIDetailPojo o1, VillageWiseEMIDetailPojo o2) {
        return o1.getGroupId().compareTo(o2.getGroupId());
    }
    
}
