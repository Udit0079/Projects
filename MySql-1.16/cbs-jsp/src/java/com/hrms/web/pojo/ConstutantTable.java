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
public class ConstutantTable implements Serializable {
    int comCode;
    String consCode;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    public int getComCode() {
        return comCode;
    }

    public void setComCode(int comCode) {
        this.comCode = comCode;
    }

    public String getConsCode() {
        return consCode;
    }

    public void setConsCode(String consCode) {
        this.consCode = consCode;
    }

}
