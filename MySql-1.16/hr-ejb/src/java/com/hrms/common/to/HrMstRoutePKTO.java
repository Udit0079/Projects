/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class HrMstRoutePKTO implements Serializable {

    private int compCode;
    private String routeCode;

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
}
