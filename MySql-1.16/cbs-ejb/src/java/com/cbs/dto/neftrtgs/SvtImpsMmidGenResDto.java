/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.neftrtgs;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "XML")
@XmlAccessorType(XmlAccessType.NONE)
public class SvtImpsMmidGenResDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement(name = "MessageType")
    private String messageType;
    @XmlElement(name = "ProcCode")
    private String procCode;
    @XmlElement(name = "OriginatingChannel")
    private String originatingChannel;
    @XmlElement(name = "Stan")
    private String stan;
    @XmlElement(name = "LocalTxnDtTime")
    private String localTxnDtTime;
    @XmlElement(name = "ActCode")
    private String actCode;
    @XmlElement(name = "ActCodeDesc")
    private String actCodeDesc;
    @XmlElement(name = "RemitterMobNo")
    private String remitterMobNo;
    @XmlElement(name = "RemitterAccNo")
    private String remitterAccNo;
    @XmlElement(name = "Mmid")
    private String mmid;
    @XmlElement(name = "TransRefNo")
    private String transRefNo;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    public String getOriginatingChannel() {
        return originatingChannel;
    }

    public void setOriginatingChannel(String originatingChannel) {
        this.originatingChannel = originatingChannel;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getLocalTxnDtTime() {
        return localTxnDtTime;
    }

    public void setLocalTxnDtTime(String localTxnDtTime) {
        this.localTxnDtTime = localTxnDtTime;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getActCodeDesc() {
        return actCodeDesc;
    }

    public void setActCodeDesc(String actCodeDesc) {
        this.actCodeDesc = actCodeDesc;
    }

    public String getRemitterMobNo() {
        return remitterMobNo;
    }

    public void setRemitterMobNo(String remitterMobNo) {
        this.remitterMobNo = remitterMobNo;
    }

    public String getRemitterAccNo() {
        return remitterAccNo;
    }

    public void setRemitterAccNo(String remitterAccNo) {
        this.remitterAccNo = remitterAccNo;
    }

    public String getMmid() {
        return mmid;
    }

    public void setMmid(String mmid) {
        this.mmid = mmid;
    }

    public String getTransRefNo() {
        return transRefNo;
    }

    public void setTransRefNo(String transRefNo) {
        this.transRefNo = transRefNo;
    }
}
