/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Comparator;

/**
 *
 * @author saurabhsipl
 */
public class SortedAgentCollectionReport implements Comparator<AgentCollectionPojo> {
    public int compare(AgentCollectionPojo object1, AgentCollectionPojo object2) {
        return object1.getAccNo().compareTo(object2.getAccNo());
    }
    
}
