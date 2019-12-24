/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author SAMY
 */
public class CibilTSPoJo {
    public String segId;
    public String noOfBor;
    public String noOfCrFacility;
    public String filler;

    public String getFiller() {
        return filler;
    }
    public void setFiller(String filler) {
        this.filler = filler;
    }
    public String getNoOfBor() {
        return noOfBor;
    }
    public void setNoOfBor(String noOfBor) {
        this.noOfBor = noOfBor;
    }
    public String getNoOfCrFacility() {
        return noOfCrFacility;
    }
    public void setNoOfCrFacility(String noOfCrFacility) {
        this.noOfCrFacility = noOfCrFacility;
    }
    public String getSegId() {
        return segId;
    }
    public void setSegId(String segId) {
        this.segId = segId;
    }
}