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
 * @author Administrator
 */
@Entity
@Table(name = "td_accountmaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TDAccountMaster.findAll", query = "SELECT t FROM TDAccountMaster t"),
    @NamedQuery(name = "TDAccountMaster.findByAcno", query = "SELECT t FROM TDAccountMaster t WHERE t.acno = :acno"),
    @NamedQuery(name = "TDAccountMaster.findByCustname", query = "SELECT t FROM TDAccountMaster t WHERE t.custname = :custname"),
    @NamedQuery(name = "TDAccountMaster.findByOpeningDt", query = "SELECT t FROM TDAccountMaster t WHERE t.openingDt = :openingDt"),
    @NamedQuery(name = "TDAccountMaster.findByOperMode", query = "SELECT t FROM TDAccountMaster t WHERE t.operMode = :operMode"),
    @NamedQuery(name = "TDAccountMaster.findByAccttype", query = "SELECT t FROM TDAccountMaster t WHERE t.accttype = :accttype"),
    @NamedQuery(name = "TDAccountMaster.findByTdsflag", query = "SELECT t FROM TDAccountMaster t WHERE t.tdsflag = :tdsflag"),
    @NamedQuery(name = "TDAccountMaster.findByTdsdetails", query = "SELECT t FROM TDAccountMaster t WHERE t.tdsdetails = :tdsdetails"),
    @NamedQuery(name = "TDAccountMaster.findByCustType", query = "SELECT t FROM TDAccountMaster t WHERE t.custType = :custType"),
    @NamedQuery(name = "TDAccountMaster.findByOrgnCode", query = "SELECT t FROM TDAccountMaster t WHERE t.orgnCode = :orgnCode"),
    @NamedQuery(name = "TDAccountMaster.findByAccStatus", query = "SELECT t FROM TDAccountMaster t WHERE t.accStatus = :accStatus"),
    @NamedQuery(name = "TDAccountMaster.findByJtName1", query = "SELECT t FROM TDAccountMaster t WHERE t.jtName1 = :jtName1"),
    @NamedQuery(name = "TDAccountMaster.findByJtName2", query = "SELECT t FROM TDAccountMaster t WHERE t.jtName2 = :jtName2"),
    @NamedQuery(name = "TDAccountMaster.findByJtName3", query = "SELECT t FROM TDAccountMaster t WHERE t.jtName3 = :jtName3"),
    @NamedQuery(name = "TDAccountMaster.findByJtName4", query = "SELECT t FROM TDAccountMaster t WHERE t.jtName4 = :jtName4"),
    @NamedQuery(name = "TDAccountMaster.findByNomination", query = "SELECT t FROM TDAccountMaster t WHERE t.nomination = :nomination"),
    @NamedQuery(name = "TDAccountMaster.findByRelationship", query = "SELECT t FROM TDAccountMaster t WHERE t.relationship = :relationship"),
    @NamedQuery(name = "TDAccountMaster.findByRemarks", query = "SELECT t FROM TDAccountMaster t WHERE t.remarks = :remarks"),
    @NamedQuery(name = "TDAccountMaster.findByIntroAccno", query = "SELECT t FROM TDAccountMaster t WHERE t.introAccno = :introAccno"),
    @NamedQuery(name = "TDAccountMaster.findByClosingDate", query = "SELECT t FROM TDAccountMaster t WHERE t.closingDate = :closingDate"),
    @NamedQuery(name = "TDAccountMaster.findByEnteredBy", query = "SELECT t FROM TDAccountMaster t WHERE t.enteredBy = :enteredBy"),
    @NamedQuery(name = "TDAccountMaster.findByAuthBy", query = "SELECT t FROM TDAccountMaster t WHERE t.authBy = :authBy"),
    @NamedQuery(name = "TDAccountMaster.findByLastUpdateDt", query = "SELECT t FROM TDAccountMaster t WHERE t.lastUpdateDt = :lastUpdateDt"),
    @NamedQuery(name = "TDAccountMaster.findByInstruction", query = "SELECT t FROM TDAccountMaster t WHERE t.instruction = :instruction"),
    @NamedQuery(name = "TDAccountMaster.findByCustid1", query = "SELECT t FROM TDAccountMaster t WHERE t.custid1 = :custid1"),
    @NamedQuery(name = "TDAccountMaster.findByCustid2", query = "SELECT t FROM TDAccountMaster t WHERE t.custid2 = :custid2"),
    @NamedQuery(name = "TDAccountMaster.findByCustid3", query = "SELECT t FROM TDAccountMaster t WHERE t.custid3 = :custid3"),
    @NamedQuery(name = "TDAccountMaster.findByCustid4", query = "SELECT t FROM TDAccountMaster t WHERE t.custid4 = :custid4"),
    @NamedQuery(name = "TDAccountMaster.findByCurBrCode", query = "SELECT t FROM TDAccountMaster t WHERE t.curBrCode = :curBrCode")})
public class TDAccountMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "ACNO")
    private String acno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "CUSTNAME")
    private String custname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OpeningDt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openingDt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OperMode")
    private short operMode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Accttype")
    private String accttype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TDSFLAG")
    private String tdsflag;
    @Size(max = 25)
    @Column(name = "TDSDETAILS")
    private String tdsdetails;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CUST_TYPE")
    private String custType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrgnCode")
    private short orgnCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccStatus")
    private short accStatus;
     @Size(max = 40)
    @Column(name = "JtName1")
    private String jtName1;
    @Size(max = 40)
    @Column(name = "JtName2")
    private String jtName2;
    @Size(max = 40)
    @Column(name = "JtName3")
    private String jtName3;
    @Size(max = 40)
    @Column(name = "JtName4")
    private String jtName4;
    @Size(max = 40)
    @Column(name = "Nomination")
    private String nomination;
    @Size(max = 30)
    @Column(name = "Relationship")
    private String relationship;
    @Size(max = 50)
    @Column(name = "Remarks")
    private String remarks;
    @Size(max = 12)
    @Column(name = "IntroAccno")
    private String introAccno;
    @Size(max = 8)
    @Column(name = "ClosingDate")
    private String closingDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EnteredBy")
    private String enteredBy;
    @Size(max = 20)
    @Column(name = "AuthBy")
    private String authBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LastUpdateDt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDt;
    @Size(max = 255)
    @Column(name = "instruction")
    private String instruction;
    @Size(max = 10)
    @Column(name = "custid1")
    private String custid1;
    @Size(max = 10)
    @Column(name = "custid2")
    private String custid2;
    @Size(max = 10)
    @Column(name = "custid3")
    private String custid3;
    @Size(max = 10)
    @Column(name = "custid4")
    private String custid4;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CurBrCode")
    private String curBrCode;

    public TDAccountMaster() {
    }

    public TDAccountMaster(String acno) {
        this.acno = acno;
    }

    public TDAccountMaster(String acno, String custname, Date openingDt, short operMode, String accttype, String tdsflag, String custType, short orgnCode, short accStatus, String enteredBy, Date lastUpdateDt, String curBrCode) {
        this.acno = acno;
        this.custname = custname;
        this.openingDt = openingDt;
        this.operMode = operMode;
        this.accttype = accttype;
        this.tdsflag = tdsflag;
        this.custType = custType;
        this.orgnCode = orgnCode;
        this.accStatus = accStatus;
        this.enteredBy = enteredBy;
        this.lastUpdateDt = lastUpdateDt;
        this.curBrCode = curBrCode;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public Date getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(Date openingDt) {
        this.openingDt = openingDt;
    }

    public short getOperMode() {
        return operMode;
    }

    public void setOperMode(short operMode) {
        this.operMode = operMode;
    }

    public String getAccttype() {
        return accttype;
    }

    public void setAccttype(String accttype) {
        this.accttype = accttype;
    }

    public String getTdsflag() {
        return tdsflag;
    }

    public void setTdsflag(String tdsflag) {
        this.tdsflag = tdsflag;
    }

    public String getTdsdetails() {
        return tdsdetails;
    }

    public void setTdsdetails(String tdsdetails) {
        this.tdsdetails = tdsdetails;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public short getOrgnCode() {
        return orgnCode;
    }

    public void setOrgnCode(short orgnCode) {
        this.orgnCode = orgnCode;
    }

    public short getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(short accStatus) {
        this.accStatus = accStatus;
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

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIntroAccno() {
        return introAccno;
    }

    public void setIntroAccno(String introAccno) {
        this.introAccno = introAccno;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Date getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(Date lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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
    
     public String getCurBrCode() {
        return curBrCode;
    }

    public void setCurBrCode(String curBrCode) {
        this.curBrCode = curBrCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acno != null ? acno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TDAccountMaster)) {
            return false;
        }
        TDAccountMaster other = (TDAccountMaster) object;
        if ((this.acno == null && other.acno != null) || (this.acno != null && !this.acno.equals(other.acno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.TDAccountMaster[ acno=" + acno + " ]";
    }
}
