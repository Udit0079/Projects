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
@Table(name = "mb_charge_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbChargeMaster.findAll", query = "SELECT m FROM MbChargeMaster m"),
    @NamedQuery(name = "MbChargeMaster.findByTxnId", query = "SELECT m FROM MbChargeMaster m WHERE m.txnId = :txnId"),
    @NamedQuery(name = "MbChargeMaster.findByMessageType", query = "SELECT m FROM MbChargeMaster m WHERE m.messageType = :messageType"),
    @NamedQuery(name = "MbChargeMaster.findByChargePerMessage", query = "SELECT m FROM MbChargeMaster m WHERE m.chargePerMessage = :chargePerMessage"),
    @NamedQuery(name = "MbChargeMaster.findByStatus", query = "SELECT m FROM MbChargeMaster m WHERE m.status = :status"),
    @NamedQuery(name = "MbChargeMaster.findByChargeGlHead", query = "SELECT m FROM MbChargeMaster m WHERE m.chargeGlHead = :chargeGlHead"),
    @NamedQuery(name = "MbChargeMaster.findByEnterBy", query = "SELECT m FROM MbChargeMaster m WHERE m.enterBy = :enterBy"),
    @NamedQuery(name = "MbChargeMaster.findByCreationDate", query = "SELECT m FROM MbChargeMaster m WHERE m.creationDate = :creationDate")})
public class MbChargeMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXN_ID")
    private Long txnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "MESSAGE_TYPE")
    private String messageType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHARGE_PER_MESSAGE")
    private double chargePerMessage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "CHARGE_GL_HEAD")
    private String chargeGlHead;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public MbChargeMaster() {
    }

    public MbChargeMaster(Long txnId) {
        this.txnId = txnId;
    }

    public MbChargeMaster(Long txnId, String messageType, double chargePerMessage, String status, String chargeGlHead, String enterBy, Date creationDate) {
        this.txnId = txnId;
        this.messageType = messageType;
        this.chargePerMessage = chargePerMessage;
        this.status = status;
        this.chargeGlHead = chargeGlHead;
        this.enterBy = enterBy;
        this.creationDate = creationDate;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public double getChargePerMessage() {
        return chargePerMessage;
    }

    public void setChargePerMessage(double chargePerMessage) {
        this.chargePerMessage = chargePerMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChargeGlHead() {
        return chargeGlHead;
    }

    public void setChargeGlHead(String chargeGlHead) {
        this.chargeGlHead = chargeGlHead;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnId != null ? txnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbChargeMaster)) {
            return false;
        }
        MbChargeMaster other = (MbChargeMaster) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbChargeMaster[ txnId=" + txnId + " ]";
    }
}
