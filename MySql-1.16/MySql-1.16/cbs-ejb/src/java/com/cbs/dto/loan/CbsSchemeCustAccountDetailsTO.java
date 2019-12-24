/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsSchemeCustAccountDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String schemeType;
    private String chequeNotAllowed;
    private String chequeNotIssued;
    private String chequeIssuedToMinor;
    private String chequeStopped;
    private String chequeCautioned;
    private String introducerNotCust;
    private String introducerNewCust;
    private String employeeOwnAccount;
    private String siPerfixCharges;
    private String drAgainstCc;
    private String clsPendingJobs;
    private String clsPendingSi;
    private String clsPendingCc;
    private String clsPendingChq;
    private String clsPendingProxy;
    private String chequeIssuedButNotAck;
    private String chequeUnusable;
    private String chequeStoppedButNotVerified;

    public String getChequeCautioned() {
        return chequeCautioned;
    }

    public void setChequeCautioned(String chequeCautioned) {
        this.chequeCautioned = chequeCautioned;
    }

    public String getChequeIssuedButNotAck() {
        return chequeIssuedButNotAck;
    }

    public void setChequeIssuedButNotAck(String chequeIssuedButNotAck) {
        this.chequeIssuedButNotAck = chequeIssuedButNotAck;
    }

    public String getChequeIssuedToMinor() {
        return chequeIssuedToMinor;
    }

    public void setChequeIssuedToMinor(String chequeIssuedToMinor) {
        this.chequeIssuedToMinor = chequeIssuedToMinor;
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

    public String getChequeStopped() {
        return chequeStopped;
    }

    public void setChequeStopped(String chequeStopped) {
        this.chequeStopped = chequeStopped;
    }

    public String getChequeStoppedButNotVerified() {
        return chequeStoppedButNotVerified;
    }

    public void setChequeStoppedButNotVerified(String chequeStoppedButNotVerified) {
        this.chequeStoppedButNotVerified = chequeStoppedButNotVerified;
    }

    public String getChequeUnusable() {
        return chequeUnusable;
    }

    public void setChequeUnusable(String chequeUnusable) {
        this.chequeUnusable = chequeUnusable;
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

    public String getClsPendingJobs() {
        return clsPendingJobs;
    }

    public void setClsPendingJobs(String clsPendingJobs) {
        this.clsPendingJobs = clsPendingJobs;
    }

    public String getClsPendingProxy() {
        return clsPendingProxy;
    }

    public void setClsPendingProxy(String clsPendingProxy) {
        this.clsPendingProxy = clsPendingProxy;
    }

    public String getClsPendingSi() {
        return clsPendingSi;
    }

    public void setClsPendingSi(String clsPendingSi) {
        this.clsPendingSi = clsPendingSi;
    }

    public String getDrAgainstCc() {
        return drAgainstCc;
    }

    public void setDrAgainstCc(String drAgainstCc) {
        this.drAgainstCc = drAgainstCc;
    }

    public String getEmployeeOwnAccount() {
        return employeeOwnAccount;
    }

    public void setEmployeeOwnAccount(String employeeOwnAccount) {
        this.employeeOwnAccount = employeeOwnAccount;
    }

    public String getIntroducerNewCust() {
        return introducerNewCust;
    }

    public void setIntroducerNewCust(String introducerNewCust) {
        this.introducerNewCust = introducerNewCust;
    }

    public String getIntroducerNotCust() {
        return introducerNotCust;
    }

    public void setIntroducerNotCust(String introducerNotCust) {
        this.introducerNotCust = introducerNotCust;
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

    public String getSiPerfixCharges() {
        return siPerfixCharges;
    }

    public void setSiPerfixCharges(String siPerfixCharges) {
        this.siPerfixCharges = siPerfixCharges;
    }
}
