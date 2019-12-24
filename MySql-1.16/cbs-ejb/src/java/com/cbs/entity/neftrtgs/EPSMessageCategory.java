/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "eps_messagecategory")
@NamedQueries({
    @NamedQuery(name = "EPSMessageCategory.findAll", query = "SELECT e FROM EPSMessageCategory e"),
    @NamedQuery(name = "EPSMessageCategory.findByPaySysId", query = "SELECT e FROM EPSMessageCategory e WHERE e.paySysId = :paySysId"),
    @NamedQuery(name = "EPSMessageCategory.findByMsgType", query = "SELECT e FROM EPSMessageCategory e WHERE e.msgType = :msgType"),
    @NamedQuery(name = "EPSMessageCategory.findByMsgSubType", query = "SELECT e FROM EPSMessageCategory e WHERE e.msgSubType = :msgSubType"),
    @NamedQuery(name = "EPSMessageCategory.findByMsgPurpose", query = "SELECT e FROM EPSMessageCategory e WHERE e.msgPurpose = :msgPurpose"),
    @NamedQuery(name = "EPSMessageCategory.findByScreenFlag", query = "SELECT e FROM EPSMessageCategory e WHERE e.screenFlag = :screenFlag"),
    @NamedQuery(name = "EPSMessageCategory.findByReschedule", query = "SELECT e FROM EPSMessageCategory e WHERE e.reschedule = :reschedule")})
public class EPSMessageCategory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "PaySysId")
    private String paySysId;
    @Column(name = "MsgType")
    private String msgType;
    @Id
    @Basic(optional = false)
    @Column(name = "MsgSubType")
    private String msgSubType;
    @Basic(optional = false)
    @Column(name = "MsgPurpose")
    private String msgPurpose;
    @Basic(optional = false)
    @Column(name = "ScreenFlag")
    private String screenFlag;
    @Basic(optional = false)
    @Column(name = "Reschedule")
    private String reschedule;

    public EPSMessageCategory() {
    }

    public EPSMessageCategory(String msgSubType) {
        this.msgSubType = msgSubType;
    }

    public EPSMessageCategory(String msgSubType, String msgPurpose, String screenFlag, String reschedule) {
        this.msgSubType = msgSubType;
        this.msgPurpose = msgPurpose;
        this.screenFlag = screenFlag;
        this.reschedule = reschedule;
    }

    /***************Added by dinesh******************/
    public EPSMessageCategory(String paySysId, String msgType, String msgSubType, String msgPurpose, String screenFlag, String reschedule) {
        this.paySysId = paySysId;
        this.msgType = msgType;
        this.msgSubType = msgSubType;
        this.msgPurpose = msgPurpose;
        this.screenFlag = screenFlag;
        this.reschedule = reschedule;
    }

    public String getPaySysId() {
        return paySysId;
    }

    public void setPaySysId(String paySysId) {
        this.paySysId = paySysId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgSubType() {
        return msgSubType;
    }

    public void setMsgSubType(String msgSubType) {
        this.msgSubType = msgSubType;
    }

    public String getMsgPurpose() {
        return msgPurpose;
    }

    public void setMsgPurpose(String msgPurpose) {
        this.msgPurpose = msgPurpose;
    }

    public String getScreenFlag() {
        return screenFlag;
    }

    public void setScreenFlag(String screenFlag) {
        this.screenFlag = screenFlag;
    }

    public String getReschedule() {
        return reschedule;
    }

    public void setReschedule(String reschedule) {
        this.reschedule = reschedule;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgSubType != null ? msgSubType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSMessageCategory)) {
            return false;
        }
        EPSMessageCategory other = (EPSMessageCategory) object;
        if ((this.msgSubType == null && other.msgSubType != null) || (this.msgSubType != null && !this.msgSubType.equals(other.msgSubType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSMessageCategory[msgSubType=" + msgSubType + "]";
    }
}
