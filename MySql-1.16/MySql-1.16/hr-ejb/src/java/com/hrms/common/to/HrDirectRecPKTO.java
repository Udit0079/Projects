/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class HrDirectRecPKTO implements Serializable {

    private int compCode;

    private String arno;

    public String getArno() {
        return arno;
    }

    public void setArno(String arno) {
        this.arno = arno;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }



}
