/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@XmlRootElement(name = "MSG")
@XmlAccessorType(XmlAccessType.FIELD)
public class R09 {

    @XmlElement(name = "F2020_1")
    private String F2020_1;
    @XmlElement(name = "F6450_1")
    private String F6450_1;
    @XmlElement(name = "F6346_1")
    private String F6346_1;
    @XmlElement(name = "F3525_1")
    private String F3525_1;

    public String getF2020_1() {
        return F2020_1;
    }

    public void setF2020_1(String F2020_1) {
        this.F2020_1 = F2020_1;
    }

    public String getF6450_1() {
        return F6450_1;
    }

    public void setF6450_1(String F6450_1) {
        this.F6450_1 = F6450_1;
    }

    public String getF6346_1() {
        return F6346_1;
    }

    public void setF6346_1(String F6346_1) {
        this.F6346_1 = F6346_1;
    }

    public String getF3525_1() {
        return F3525_1;
    }

    public void setF3525_1(String F3525_1) {
        this.F3525_1 = F3525_1;
    }
}
