package com.cbs.pojo.cpsms.accountvalidationresponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dhirendra singh
 */
@XmlRootElement(namespace = "", name = "Accounts")
public class MsgRes {
    private String messageId;
    private String source;
    private String destination;
    private String bankCode;
    private String bankName;
    private String error;
    private int recordsCount;
    private String xmlns;

    List<AcctResDetail> accountList;

    public String getMessageId() {
        return messageId;
    }

    @XmlAttribute(name = "MessageId", required = true)
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSource() {
        return source;
    }

    @XmlAttribute(name = "Source", required = true)
    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    @XmlAttribute(name = "Destination", required = true)
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBankCode() {
        return bankCode;
    }

    @XmlAttribute(name = "BankCode", required = true)
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    @XmlAttribute(name = "BankName", required = true)
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getError() {
        return error;
    }
    @XmlAttribute(name = "Error", required = true)
    public void setError(String error) {
        this.error = error;
    }

    public int getRecordsCount() {
        return recordsCount;
    }

    @XmlAttribute(name = "RecordsCount", required = true)
    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    public String getXmlns() {
        return xmlns;
    }

    @XmlAttribute(name = "xmlns", required = true)
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }
    
    public List<AcctResDetail> getAccountList() {
        return accountList;
    }
    
    @XmlElement(namespace = "", name = "Account", required = true)
    public void setAccountList(List<AcctResDetail> accountList) {
        this.accountList = accountList;
    }

    
    
    
}
