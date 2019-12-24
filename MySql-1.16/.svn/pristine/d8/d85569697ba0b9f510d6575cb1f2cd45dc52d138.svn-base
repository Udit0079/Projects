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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "mb_pull_msg_tab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbPullMsgTab.findAll", query = "SELECT m FROM MbPullMsgTab m"),
    @NamedQuery(name = "MbPullMsgTab.findByTxnId", query = "SELECT m FROM MbPullMsgTab m WHERE m.mbPullMsgTabPK.txnId = :txnId"),
    @NamedQuery(name = "MbPullMsgTab.findByMobile", query = "SELECT m FROM MbPullMsgTab m WHERE m.mobile = :mobile"),
    @NamedQuery(name = "MbPullMsgTab.findByMessage", query = "SELECT m FROM MbPullMsgTab m WHERE m.message = :message"),
    @NamedQuery(name = "MbPullMsgTab.findByMessageStatus", query = "SELECT m FROM MbPullMsgTab m WHERE m.messageStatus = :messageStatus"),
    @NamedQuery(name = "MbPullMsgTab.findByReceivedDt", query = "SELECT m FROM MbPullMsgTab m WHERE m.mbPullMsgTabPK.receivedDt = :receivedDt")})
public class MbPullMsgTab extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MbPullMsgTabPK mbPullMsgTabPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MOBILE")
    private String mobile;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "MESSAGE")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MESSAGE_STATUS")
    private int messageStatus;

    public MbPullMsgTab() {
    }

    public MbPullMsgTab(MbPullMsgTabPK mbPullMsgTabPK) {
        this.mbPullMsgTabPK = mbPullMsgTabPK;
    }

    public MbPullMsgTab(MbPullMsgTabPK mbPullMsgTabPK, String mobile, String message, int messageStatus) {
        this.mbPullMsgTabPK = mbPullMsgTabPK;
        this.mobile = mobile;
        this.message = message;
        this.messageStatus = messageStatus;
    }

    public MbPullMsgTab(long txnId, Date receivedDt) {
        this.mbPullMsgTabPK = new MbPullMsgTabPK(txnId, receivedDt);
    }

    public MbPullMsgTabPK getMbPullMsgTabPK() {
        return mbPullMsgTabPK;
    }

    public void setMbPullMsgTabPK(MbPullMsgTabPK mbPullMsgTabPK) {
        this.mbPullMsgTabPK = mbPullMsgTabPK;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mbPullMsgTabPK != null ? mbPullMsgTabPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbPullMsgTab)) {
            return false;
        }
        MbPullMsgTab other = (MbPullMsgTab) object;
        if ((this.mbPullMsgTabPK == null && other.mbPullMsgTabPK != null) || (this.mbPullMsgTabPK != null && !this.mbPullMsgTabPK.equals(other.mbPullMsgTabPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbPullMsgTab[ mbPullMsgTabPK=" + mbPullMsgTabPK + " ]";
    }
    
}
