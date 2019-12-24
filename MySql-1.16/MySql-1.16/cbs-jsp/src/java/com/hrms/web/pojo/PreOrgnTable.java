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
public class PreOrgnTable implements Serializable {
    String structCode;
    String desscription;

    public String getDesscription() {
        return desscription;
    }

    public void setDesscription(String desscription) {
        this.desscription = desscription;
    }

    public String getStructCode() {
        return structCode;
    }

    public void setStructCode(String structCode) {
        this.structCode = structCode;
    }
}
