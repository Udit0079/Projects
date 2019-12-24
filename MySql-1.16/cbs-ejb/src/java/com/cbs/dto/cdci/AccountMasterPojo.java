/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.cdci;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountMasterPojo", propOrder = {
    "acNo", "custname", "openingDt",
    "introAccno", "intDeposit", "closingDate", "closingBal",
    "operMode", "jtName1", "jtName2", "jtName3",
    "jtName4", "lastOpDate", "accStatus", "optstatus",
    "orgnCode", "nomination", "oDLimit", "enteredBy",
    "authBy", "lastUpdateDt", "accttype", "relatioship",
    "ledgerFolioNo", "instruction", "penalty", "minbal",
    "rdmatdate", "rdInstal", "chequebook", "adhoclimit",
    "adhoctilldt", "adhocInterest", "creationDt",
    "name", "custType", "penalty1",
    "tDSFLAG", "custid1", "custid2",
    "custid3", "custid4"})
public class AccountMasterPojo {

    private String acNo;
    private String custname;
    private String openingDt;
    private String introAccno;
    private double intDeposit;
    private String closingDate;
    private double closingBal;
    private int operMode;
    private String jtName1;
    private String jtName2;
    private String jtName3;
    private String jtName4;
    private String lastOpDate;
    private int accStatus;
    private int optstatus;
    private int orgnCode;
    private String nomination;
    private double oDLimit;
    private String enteredBy;
    private String authBy;
    private String lastUpdateDt;
    private String accttype;
    private String relatioship;
    private int ledgerFolioNo;
    private String instruction;
    private double penalty;
    private int minbal;
    private Date rdmatdate;
    private double rdInstal;
    private int chequebook;
    double adhoclimit;
    private Date adhoctilldt;
    private double adhocInterest;
    private String creationDt;
    private String name;
    private String custType;
    private int penalty1;
    private String tDSFLAG;
    private String custid1;
    private String custid2;
    private String custid3;
    private String custid4;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public int getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(int accStatus) {
        this.accStatus = accStatus;
    }

    public String getAccttype() {
        return accttype;
    }

    public void setAccttype(String accttype) {
        this.accttype = accttype;
    }

    public double getAdhocInterest() {
        return adhocInterest;
    }

    public void setAdhocInterest(double adhocInterest) {
        this.adhocInterest = adhocInterest;
    }

    public double getAdhoclimit() {
        return adhoclimit;
    }

    public void setAdhoclimit(double adhoclimit) {
        this.adhoclimit = adhoclimit;
    }

    public Date getAdhoctilldt() {
        return adhoctilldt;
    }

    public void setAdhoctilldt(Date adhoctilldt) {
        this.adhoctilldt = adhoctilldt;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public int getChequebook() {
        return chequebook;
    }

    public void setChequebook(int chequebook) {
        this.chequebook = chequebook;
    }

    public double getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(double closingBal) {
        this.closingBal = closingBal;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getCreationDt() {
        return creationDt;
    }

    public void setCreationDt(String creationDt) {
        this.creationDt = creationDt;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustid1() {
        return custid1;
    }

    public void setCustid1(String custid1) {
        this.custid1 = custid1;
    }

    public String getCustid2() {
        return custid2;
    }

    public void setCustid2(String custid2) {
        this.custid2 = custid2;
    }

    public String getCustid3() {
        return custid3;
    }

    public void setCustid3(String custid3) {
        this.custid3 = custid3;
    }

    public String getCustid4() {
        return custid4;
    }

    public void setCustid4(String custid4) {
        this.custid4 = custid4;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public double getIntDeposit() {
        return intDeposit;
    }

    public void setIntDeposit(double intDeposit) {
        this.intDeposit = intDeposit;
    }

    public String getIntroAccno() {
        return introAccno;
    }

    public void setIntroAccno(String introAccno) {
        this.introAccno = introAccno;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getLastOpDate() {
        return lastOpDate;
    }

    public void setLastOpDate(String lastOpDate) {
        this.lastOpDate = lastOpDate;
    }

    public String getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(String lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public int getLedgerFolioNo() {
        return ledgerFolioNo;
    }

    public void setLedgerFolioNo(int ledgerFolioNo) {
        this.ledgerFolioNo = ledgerFolioNo;
    }

    public int getMinbal() {
        return minbal;
    }

    public void setMinbal(int minbal) {
        this.minbal = minbal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public double getoDLimit() {
        return oDLimit;
    }

    public void setoDLimit(double oDLimit) {
        this.oDLimit = oDLimit;
    }

    public String getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(String openingDt) {
        this.openingDt = openingDt;
    }

    public int getOperMode() {
        return operMode;
    }

    public void setOperMode(int operMode) {
        this.operMode = operMode;
    }

    public int getOptstatus() {
        return optstatus;
    }

    public void setOptstatus(int optstatus) {
        this.optstatus = optstatus;
    }

    public int getOrgnCode() {
        return orgnCode;
    }

    public void setOrgnCode(int orgnCode) {
        this.orgnCode = orgnCode;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public int getPenalty1() {
        return penalty1;
    }

    public void setPenalty1(int penalty1) {
        this.penalty1 = penalty1;
    }

    public double getRdInstal() {
        return rdInstal;
    }

    public void setRdInstal(double rdInstal) {
        this.rdInstal = rdInstal;
    }

    public Date getRdmatdate() {
        return rdmatdate;
    }

    public void setRdmatdate(Date rdmatdate) {
        this.rdmatdate = rdmatdate;
    }

    public String getRelatioship() {
        return relatioship;
    }

    public void setRelatioship(String relatioship) {
        this.relatioship = relatioship;
    }

    public String gettDSFLAG() {
        return tDSFLAG;
    }

    public void settDSFLAG(String tDSFLAG) {
        this.tDSFLAG = tDSFLAG;
    }
}
