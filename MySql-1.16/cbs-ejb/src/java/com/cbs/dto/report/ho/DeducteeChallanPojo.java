/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class DeducteeChallanPojo {

    List<DeducteePojo> deducteeList = new ArrayList<DeducteePojo>();
    List<ChallanPojo> challanList = new ArrayList<ChallanPojo>();

    public List<DeducteePojo> getDeducteeList() {
        return deducteeList;
    }

    public void setDeducteeList(List<DeducteePojo> deducteeList) {
        this.deducteeList = deducteeList;
    }

    public List<ChallanPojo> getChallanList() {
        return challanList;
    }

    public void setChallanList(List<ChallanPojo> challanList) {
        this.challanList = challanList;
    }
    
    
}
