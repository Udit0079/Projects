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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "mb_sms_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbSmsLog.findAll", query = "SELECT m FROM MbSmsLog m"),
    @NamedQuery(name = "MbSmsLog.findByTxnId", query = "SELECT m FROM MbSmsLog m WHERE m.txnId = :txnId"),
    @NamedQuery(name = "MbSmsLog.findByMobile", query = "SELECT m FROM MbSmsLog m WHERE m.mobile = :mobile"),
    @NamedQuery(name = "MbSmsLog.findByAcno", query = "SELECT m FROM MbSmsLog m WHERE m.acno = :acno"),
    @NamedQuery(name = "MbSmsLog.findByActualMessage", query = "SELECT m FROM MbSmsLog m WHERE m.actualMessage = :actualMessage"),
    @NamedQuery(name = "MbSmsLog.findByErrorCode", query = "SELECT m FROM MbSmsLog m WHERE m.errorCode = :errorCode"),
    @NamedQuery(name = "MbSmsLog.findByErrorMessage", query = "SELECT m FROM MbSmsLog m WHERE m.errorMessage = :errorMessage"),
    @NamedQuery(name = "MbSmsLog.findByTranTime", query = "SELECT m FROM MbSmsLog m WHERE m.tranTime = :tranTime"),
    @NamedQuery(name = "MbSmsLog.findBySmsVendor", query = "SELECT m FROM MbSmsLog m WHERE m.smsVendor = :smsVendor"),
    @NamedQuery(name = "MbSmsLog.findByModuleName", query = "SELECT m FROM MbSmsLog m WHERE m.moduleName = :moduleName")})
public class MbSmsLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXN_ID")
    private Long txnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "MOBILE")
    private String mobile;
    @Size(max = 16)
    @Column(name = "ACNO")
    private String acno;
    @Size(max = 1000)
    @Column(name = "ACTUAL_MESSAGE")
    private String actualMessage;
    @Size(max = 10)
    @Column(name = "ERROR_CODE")
    private String errorCode;
    @Size(max = 1000)
    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;
    @Size(max = 50)
    @Column(name = "SMS_VENDOR")
    private String smsVendor;
    @Size(max = 10)
    @Column(name = "MODULE_NAME")
    private String moduleName;

    public MbSmsLog() {
    }

    public MbSmsLog(Long txnId) {
        this.txnId = txnId;
    }

    public MbSmsLog(Long txnId, String mobile, Date tranTime) {
        this.txnId = txnId;
        this.mobile = mobile;
        this.tranTime = tranTime;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
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

    public String getActualMessage() {
        return actualMessage;
    }

    public void setActualMessage(String actualMessage) {
        this.actualMessage = actualMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public String getSmsVendor() {
        return smsVendor;
    }

    public void setSmsVendor(String smsVendor) {
        this.smsVendor = smsVendor;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
        if (!(object instanceof MbSmsLog)) {
            return false;
        }
        MbSmsLog other = (MbSmsLog) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbSmsLog[ txnId=" + txnId + " ]";
    }
}
