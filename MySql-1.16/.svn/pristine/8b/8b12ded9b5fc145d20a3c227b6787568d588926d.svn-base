/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

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

/**
 *
 * @author root
 */
@Entity
@Table(name = "eps_ackmessage")
@NamedQueries({
    @NamedQuery(name = "EPSAckMessage.findAll", query = "SELECT e FROM EPSAckMessage e"),
    @NamedQuery(name = "EPSAckMessage.findByBlockBeginIdentifier", query = "SELECT e FROM EPSAckMessage e WHERE e.blockBeginIdentifier = :blockBeginIdentifier"),
    @NamedQuery(name = "EPSAckMessage.findByBankAppIdentifier", query = "SELECT e FROM EPSAckMessage e WHERE e.bankAppIdentifier = :bankAppIdentifier"),
    @NamedQuery(name = "EPSAckMessage.findByMessageIdentifier", query = "SELECT e FROM EPSAckMessage e WHERE e.messageIdentifier = :messageIdentifier"),
    @NamedQuery(name = "EPSAckMessage.findByInputOutputIdentifier", query = "SELECT e FROM EPSAckMessage e WHERE e.inputOutputIdentifier = :inputOutputIdentifier"),
    @NamedQuery(name = "EPSAckMessage.findBySequenceNo", query = "SELECT e FROM EPSAckMessage e WHERE e.ePSAckMessagePK.sequenceNo = :sequenceNo"),
    @NamedQuery(name = "EPSAckMessage.findBySenderIFSC", query = "SELECT e FROM EPSAckMessage e WHERE e.senderIFSC = :senderIFSC"),
    @NamedQuery(name = "EPSAckMessage.findByDateTime", query = "SELECT e FROM EPSAckMessage e WHERE e.dateTime = :dateTime"),
    @NamedQuery(name = "EPSAckMessage.findByErrorCode", query = "SELECT e FROM EPSAckMessage e WHERE e.errorCode = :errorCode"),
    @NamedQuery(name = "EPSAckMessage.findByFiller", query = "SELECT e FROM EPSAckMessage e WHERE e.filler = :filler"),
    @NamedQuery(name = "EPSAckMessage.findByBlockEndIdentifier", query = "SELECT e FROM EPSAckMessage e WHERE e.blockEndIdentifier = :blockEndIdentifier"),
    @NamedQuery(name = "EPSAckMessage.findByInsertionTime", query = "SELECT e FROM EPSAckMessage e WHERE e.ePSAckMessagePK.insertionTime = :insertionTime")})
public class EPSAckMessage extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EPSAckMessagePK ePSAckMessagePK;
    @Basic(optional = false)
    @Column(name = "BlockBeginIdentifier")
    private String blockBeginIdentifier;
    @Basic(optional = false)
    @Column(name = "BankAppIdentifier")
    private String bankAppIdentifier;
    @Basic(optional = false)
    @Column(name = "MessageIdentifier")
    private String messageIdentifier;
    @Basic(optional = false)
    @Column(name = "InputOutputIdentifier")
    private String inputOutputIdentifier;
    @Basic(optional = false)
    @Column(name = "SenderIFSC")
    private String senderIFSC;
    @Basic(optional = false)
    @Column(name = "DateTime")
    private String dateTime;
    @Column(name = "ErrorCode")
    private String errorCode;
    @Column(name = "Filler")
    private String filler;
    @Basic(optional = false)
    @Column(name = "BlockEndIdentifier")
    private String blockEndIdentifier;

    public EPSAckMessage() {
    }

    public EPSAckMessage(EPSAckMessagePK ePSAckMessagePK) {
        this.ePSAckMessagePK = ePSAckMessagePK;
    }

    public EPSAckMessage(EPSAckMessagePK ePSAckMessagePK, String blockBeginIdentifier, String bankAppIdentifier, String messageIdentifier, String inputOutputIdentifier, String senderIFSC, String dateTime, String blockEndIdentifier) {
        this.ePSAckMessagePK = ePSAckMessagePK;
        this.blockBeginIdentifier = blockBeginIdentifier;
        this.bankAppIdentifier = bankAppIdentifier;
        this.messageIdentifier = messageIdentifier;
        this.inputOutputIdentifier = inputOutputIdentifier;
        this.senderIFSC = senderIFSC;
        this.dateTime = dateTime;
        this.blockEndIdentifier = blockEndIdentifier;
    }

    /********************added by dinesh************************/
    public EPSAckMessage(EPSAckMessagePK ePSAckMessagePK, String blockBeginIdentifier, String bankAppIdentifier, String messageIdentifier, String inputOutputIdentifier, String senderIFSC, String dateTime, String errorCode, String filler, String blockEndIdentifier) {
        this.ePSAckMessagePK = ePSAckMessagePK;
        this.blockBeginIdentifier = blockBeginIdentifier;
        this.bankAppIdentifier = bankAppIdentifier;
        this.messageIdentifier = messageIdentifier;
        this.inputOutputIdentifier = inputOutputIdentifier;
        this.senderIFSC = senderIFSC;
        this.dateTime = dateTime;
        this.errorCode = errorCode;
        this.filler = filler;
        this.blockEndIdentifier = blockEndIdentifier;
    }

    public EPSAckMessage(String sequenceNo, Date insertionTime) {
        this.ePSAckMessagePK = new EPSAckMessagePK(sequenceNo, insertionTime);
    }

    public EPSAckMessagePK getEPSAckMessagePK() {
        return ePSAckMessagePK;
    }

    public void setEPSAckMessagePK(EPSAckMessagePK ePSAckMessagePK) {
        this.ePSAckMessagePK = ePSAckMessagePK;
    }

    public String getBlockBeginIdentifier() {
        return blockBeginIdentifier;
    }

    public void setBlockBeginIdentifier(String blockBeginIdentifier) {
        this.blockBeginIdentifier = blockBeginIdentifier;
    }

    public String getBankAppIdentifier() {
        return bankAppIdentifier;
    }

    public void setBankAppIdentifier(String bankAppIdentifier) {
        this.bankAppIdentifier = bankAppIdentifier;
    }

    public String getMessageIdentifier() {
        return messageIdentifier;
    }

    public void setMessageIdentifier(String messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }

    public String getInputOutputIdentifier() {
        return inputOutputIdentifier;
    }

    public void setInputOutputIdentifier(String inputOutputIdentifier) {
        this.inputOutputIdentifier = inputOutputIdentifier;
    }

    public String getSenderIFSC() {
        return senderIFSC;
    }

    public void setSenderIFSC(String senderIFSC) {
        this.senderIFSC = senderIFSC;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getBlockEndIdentifier() {
        return blockEndIdentifier;
    }

    public void setBlockEndIdentifier(String blockEndIdentifier) {
        this.blockEndIdentifier = blockEndIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ePSAckMessagePK != null ? ePSAckMessagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSAckMessage)) {
            return false;
        }
        EPSAckMessage other = (EPSAckMessage) object;
        if ((this.ePSAckMessagePK == null && other.ePSAckMessagePK != null) || (this.ePSAckMessagePK != null && !this.ePSAckMessagePK.equals(other.ePSAckMessagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSAckMessage[ePSAckMessagePK=" + ePSAckMessagePK + "]";
    }
}
