/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class OrganisationalPojo implements Serializable {

    private String name;
    private String address;
    private String pin;
    private String state;
    private String district;
    private BigDecimal branchPL;
    private String locationType;
    private String computerizedStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public BigDecimal getBranchPL() {
        return branchPL;
    }

    public void setBranchPL(BigDecimal branchPL) {
        this.branchPL = branchPL;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getComputerizedStatus() {
        return computerizedStatus;
    }

    public void setComputerizedStatus(String computerizedStatus) {
        this.computerizedStatus = computerizedStatus;
    }
}
