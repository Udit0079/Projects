/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_cust_account_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findAll", query = "SELECT c FROM CbsSchemeCustAccountDetails c"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByChequeNotAllowed", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.chequeNotAllowed = :chequeNotAllowed"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByChequeNotIssued", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.chequeNotIssued = :chequeNotIssued"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByChequeIssuedToMinor", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.chequeIssuedToMinor = :chequeIssuedToMinor"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByChequeStopped", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.chequeStopped = :chequeStopped"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByChequeCautioned", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.chequeCautioned = :chequeCautioned"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByIntroducerNotCust", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.introducerNotCust = :introducerNotCust"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByIntroducerNewCust", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.introducerNewCust = :introducerNewCust"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByEmployeeOwnAccount", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.employeeOwnAccount = :employeeOwnAccount"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findBySiPerfixCharges", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.siPerfixCharges = :siPerfixCharges"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByDrAgainstCc", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.drAgainstCc = :drAgainstCc"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByClsPendingJobs", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.clsPendingJobs = :clsPendingJobs"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByClsPendingSi", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.clsPendingSi = :clsPendingSi"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByClsPendingCc", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.clsPendingCc = :clsPendingCc"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByClsPendingChq", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.clsPendingChq = :clsPendingChq"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByClsPendingProxy", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.clsPendingProxy = :clsPendingProxy"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByChequeIssuedButNotAck", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.chequeIssuedButNotAck = :chequeIssuedButNotAck"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByChequeUnusable", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.chequeUnusable = :chequeUnusable"),
    @NamedQuery(name = "CbsSchemeCustAccountDetails.findByChequeStoppedButNotVerified", query = "SELECT c FROM CbsSchemeCustAccountDetails c WHERE c.chequeStoppedButNotVerified = :chequeStoppedButNotVerified")})
public class CbsSchemeCustAccountDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 3)
    @Column(name = "CHEQUE_NOT_ALLOWED")
    private String chequeNotAllowed;
    @Size(max = 3)
    @Column(name = "CHEQUE_NOT_ISSUED")
    private String chequeNotIssued;
    @Size(max = 3)
    @Column(name = "CHEQUE_ISSUED_TO_MINOR")
    private String chequeIssuedToMinor;
    @Size(max = 3)
    @Column(name = "CHEQUE_STOPPED")
    private String chequeStopped;
    @Size(max = 3)
    @Column(name = "CHEQUE_CAUTIONED")
    private String chequeCautioned;
    @Size(max = 3)
    @Column(name = "INTRODUCER_NOT_CUST")
    private String introducerNotCust;
    @Size(max = 3)
    @Column(name = "INTRODUCER_NEW_CUST")
    private String introducerNewCust;
    @Size(max = 3)
    @Column(name = "EMPLOYEE_OWN_ACCOUNT")
    private String employeeOwnAccount;
    @Size(max = 3)
    @Column(name = "SI_PERFIX_CHARGES")
    private String siPerfixCharges;
    @Size(max = 3)
    @Column(name = "DR_AGAINST_CC")
    private String drAgainstCc;
    @Size(max = 3)
    @Column(name = "CLS_PENDING_JOBS")
    private String clsPendingJobs;
    @Size(max = 3)
    @Column(name = "CLS_PENDING_SI")
    private String clsPendingSi;
    @Size(max = 3)
    @Column(name = "CLS_PENDING_CC")
    private String clsPendingCc;
    @Size(max = 3)
    @Column(name = "CLS_PENDING_CHQ")
    private String clsPendingChq;
    @Size(max = 3)
    @Column(name = "CLS_PENDING_PROXY")
    private String clsPendingProxy;
    @Size(max = 3)
    @Column(name = "CHEQUE_ISSUED_BUT_NOT_ACK")
    private String chequeIssuedButNotAck;
    @Size(max = 3)
    @Column(name = "CHEQUE_UNUSABLE")
    private String chequeUnusable;
    @Size(max = 3)
    @Column(name = "CHEQUE_STOPPED_BUT_NOT_VERIFIED")
    private String chequeStoppedButNotVerified;

    public CbsSchemeCustAccountDetails() {
    }

    public CbsSchemeCustAccountDetails(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getChequeNotAllowed() {
        return chequeNotAllowed;
    }

    public void setChequeNotAllowed(String chequeNotAllowed) {
        this.chequeNotAllowed = chequeNotAllowed;
    }

    public String getChequeNotIssued() {
        return chequeNotIssued;
    }

    public void setChequeNotIssued(String chequeNotIssued) {
        this.chequeNotIssued = chequeNotIssued;
    }

    public String getChequeIssuedToMinor() {
        return chequeIssuedToMinor;
    }

    public void setChequeIssuedToMinor(String chequeIssuedToMinor) {
        this.chequeIssuedToMinor = chequeIssuedToMinor;
    }

    public String getChequeStopped() {
        return chequeStopped;
    }

    public void setChequeStopped(String chequeStopped) {
        this.chequeStopped = chequeStopped;
    }

    public String getChequeCautioned() {
        return chequeCautioned;
    }

    public void setChequeCautioned(String chequeCautioned) {
        this.chequeCautioned = chequeCautioned;
    }

    public String getIntroducerNotCust() {
        return introducerNotCust;
    }

    public void setIntroducerNotCust(String introducerNotCust) {
        this.introducerNotCust = introducerNotCust;
    }

    public String getIntroducerNewCust() {
        return introducerNewCust;
    }

    public void setIntroducerNewCust(String introducerNewCust) {
        this.introducerNewCust = introducerNewCust;
    }

    public String getEmployeeOwnAccount() {
        return employeeOwnAccount;
    }

    public void setEmployeeOwnAccount(String employeeOwnAccount) {
        this.employeeOwnAccount = employeeOwnAccount;
    }

    public String getSiPerfixCharges() {
        return siPerfixCharges;
    }

    public void setSiPerfixCharges(String siPerfixCharges) {
        this.siPerfixCharges = siPerfixCharges;
    }

    public String getDrAgainstCc() {
        return drAgainstCc;
    }

    public void setDrAgainstCc(String drAgainstCc) {
        this.drAgainstCc = drAgainstCc;
    }

    public String getClsPendingJobs() {
        return clsPendingJobs;
    }

    public void setClsPendingJobs(String clsPendingJobs) {
        this.clsPendingJobs = clsPendingJobs;
    }

    public String getClsPendingSi() {
        return clsPendingSi;
    }

    public void setClsPendingSi(String clsPendingSi) {
        this.clsPendingSi = clsPendingSi;
    }

    public String getClsPendingCc() {
        return clsPendingCc;
    }

    public void setClsPendingCc(String clsPendingCc) {
        this.clsPendingCc = clsPendingCc;
    }

    public String getClsPendingChq() {
        return clsPendingChq;
    }

    public void setClsPendingChq(String clsPendingChq) {
        this.clsPendingChq = clsPendingChq;
    }

    public String getClsPendingProxy() {
        return clsPendingProxy;
    }

    public void setClsPendingProxy(String clsPendingProxy) {
        this.clsPendingProxy = clsPendingProxy;
    }

    public String getChequeIssuedButNotAck() {
        return chequeIssuedButNotAck;
    }

    public void setChequeIssuedButNotAck(String chequeIssuedButNotAck) {
        this.chequeIssuedButNotAck = chequeIssuedButNotAck;
    }

    public String getChequeUnusable() {
        return chequeUnusable;
    }

    public void setChequeUnusable(String chequeUnusable) {
        this.chequeUnusable = chequeUnusable;
    }

    public String getChequeStoppedButNotVerified() {
        return chequeStoppedButNotVerified;
    }

    public void setChequeStoppedButNotVerified(String chequeStoppedButNotVerified) {
        this.chequeStoppedButNotVerified = chequeStoppedButNotVerified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeCustAccountDetails)) {
            return false;
        }
        CbsSchemeCustAccountDetails other = (CbsSchemeCustAccountDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeCustAccountDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
