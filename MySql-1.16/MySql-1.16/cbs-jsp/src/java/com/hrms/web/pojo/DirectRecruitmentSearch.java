/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.pojo;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class DirectRecruitmentSearch implements Serializable {
    String locationcode;
    String description;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }
}
