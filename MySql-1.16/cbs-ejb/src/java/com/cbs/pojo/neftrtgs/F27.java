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
public class F27 {

    @XmlElement(name = "FTransaction_Reference_Number")
    private String FTransaction_Reference_Number;
    @XmlElement(name = "FICReference_Number")
    private String FICReference_Number;
    @XmlElement(name = "FSucess_Flag")
    private String FSucess_Flag;
    @XmlElement(name = "FReason_Description")
    private String FReason_Description;

    public String getFTransaction_Reference_Number() {
        return FTransaction_Reference_Number;
    }

    public void setFTransaction_Reference_Number(String FTransaction_Reference_Number) {
        this.FTransaction_Reference_Number = FTransaction_Reference_Number;
    }

    public String getFICReference_Number() {
        return FICReference_Number;
    }

    public void setFICReference_Number(String FICReference_Number) {
        this.FICReference_Number = FICReference_Number;
    }

    public String getFSucess_Flag() {
        return FSucess_Flag;
    }

    public void setFSucess_Flag(String FSucess_Flag) {
        this.FSucess_Flag = FSucess_Flag;
    }

    public String getFReason_Description() {
        return FReason_Description;
    }

    public void setFReason_Description(String FReason_Description) {
        this.FReason_Description = FReason_Description;
    }
}
