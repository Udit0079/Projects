/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class DataBankAdvertisement implements Serializable {

    String advetisementcode;
    String jobCode;

    public String getAdvetisementcode() {
        return advetisementcode;
    }

    public void setAdvetisementcode(String advetisementcode) {
        this.advetisementcode = advetisementcode;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }
}
