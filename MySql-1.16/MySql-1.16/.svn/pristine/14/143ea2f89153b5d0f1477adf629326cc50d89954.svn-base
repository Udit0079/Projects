/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "mb_push_msg_tab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbPushMsgTab.findAll", query = "SELECT m FROM MbPushMsgTab m"),
    @NamedQuery(name = "MbPushMsgTab.findByMessageId", query = "SELECT m FROM MbPushMsgTab m WHERE m.messageId = :messageId"),
    @NamedQuery(name = "MbPushMsgTab.findByMobile", query = "SELECT m FROM MbPushMsgTab m WHERE m.mobile = :mobile"),
    @NamedQuery(name = "MbPushMsgTab.findByAcno", query = "SELECT m FROM MbPushMsgTab m WHERE m.acno = :acno"),
    @NamedQuery(name = "MbPushMsgTab.findByMessage", query = "SELECT m FROM MbPushMsgTab m WHERE m.message = :message"),
    @NamedQuery(name = "MbPushMsgTab.findByMessageStatus", query = "SELECT m FROM MbPushMsgTab m WHERE m.messageStatus = :messageStatus"),
    @NamedQuery(name = "MbPushMsgTab.findByMessageType", query = "SELECT m FROM MbPushMsgTab m WHERE m.messageType = :messageType"),
    @NamedQuery(name = "MbPushMsgTab.findByDt", query = "SELECT m FROM MbPushMsgTab m WHERE m.dt = :dt"),
    @NamedQuery(name = "MbPushMsgTab.findByEnterBy", query = "SELECT m FROM MbPushMsgTab m WHERE m.enterBy = :enterBy"),
    @NamedQuery(name = "MbPushMsgTab.findByModuleName", query = "SELECT m FROM MbPushMsgTab m WHERE m.moduleName = :moduleName"),
    @NamedQuery(name = "MbPushMsgTab.findByVendorTranid", query = "SELECT m FROM MbPushMsgTab m WHERE m.vendorTranid = :vendorTranid"),
    @NamedQuery(name = "MbPushMsgTab.findByVendorStatus", query = "SELECT m FROM MbPushMsgTab m WHERE m.vendorStatus = :vendorStatus")})
public class MbPushMsgTab extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "MESSAGE_ID")
    private String messageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "MOBILE")
    private String mobile;
    @Size(max = 16)
    @Column(name = "ACNO")
    private String acno;
    @Size(max = 2000)
    @Column(name = "MESSAGE")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MESSAGE_STATUS")
    private int messageStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "MESSAGE_TYPE")
    private String messageType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Size(max = 10)
    @Column(name = "MODULE_NAME")
    private String moduleName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "VENDOR_TRANID")
    private String vendorTranid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VENDOR_STATUS")
    private int vendorStatus;

    public MbPushMsgTab() {
    }

    public MbPushMsgTab(String messageId) {
        this.messageId = messageId;
    }

    public MbPushMsgTab(String messageId, String mobile, int messageStatus, String messageType, Date dt, String enterBy, String vendorTranid, int vendorStatus) {
        this.messageId = messageId;
        this.mobile = mobile;
        this.messageStatus = messageStatus;
        this.messageType = messageType;
        this.dt = dt;
        this.enterBy = enterBy;
        this.vendorTranid = vendorTranid;
        this.vendorStatus = vendorStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getVendorTranid() {
        return vendorTranid;
    }

    public void setVendorTranid(String vendorTranid) {
        this.vendorTranid = vendorTranid;
    }

    public int getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(int vendorStatus) {
        this.vendorStatus = vendorStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbPushMsgTab)) {
            return false;
        }
        MbPushMsgTab other = (MbPushMsgTab) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbPushMsgTab[ messageId=" + messageId + " ]";
    }
}
