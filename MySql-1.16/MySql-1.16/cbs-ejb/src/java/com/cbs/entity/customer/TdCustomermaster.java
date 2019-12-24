/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
@Table(name = "td_customermaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TdCustomermaster.findAll", query = "SELECT t FROM TdCustomermaster t"),
    @NamedQuery(name = "TdCustomermaster.findByCustno", query = "SELECT t FROM TdCustomermaster t WHERE t.tdCustomermasterPK.custno = :custno"),
    @NamedQuery(name = "TdCustomermaster.findByTitle", query = "SELECT t FROM TdCustomermaster t WHERE t.title = :title"),
    @NamedQuery(name = "TdCustomermaster.findByCustname", query = "SELECT t FROM TdCustomermaster t WHERE t.custname = :custname"),
    @NamedQuery(name = "TdCustomermaster.findByCraddress", query = "SELECT t FROM TdCustomermaster t WHERE t.craddress = :craddress"),
    @NamedQuery(name = "TdCustomermaster.findByPraddress", query = "SELECT t FROM TdCustomermaster t WHERE t.praddress = :praddress"),
    @NamedQuery(name = "TdCustomermaster.findByPhoneno", query = "SELECT t FROM TdCustomermaster t WHERE t.phoneno = :phoneno"),
    @NamedQuery(name = "TdCustomermaster.findByPanno", query = "SELECT t FROM TdCustomermaster t WHERE t.panno = :panno"),
    @NamedQuery(name = "TdCustomermaster.findByStatus", query = "SELECT t FROM TdCustomermaster t WHERE t.status = :status"),
    @NamedQuery(name = "TdCustomermaster.findByGrdname", query = "SELECT t FROM TdCustomermaster t WHERE t.grdname = :grdname"),
    @NamedQuery(name = "TdCustomermaster.findByRelation", query = "SELECT t FROM TdCustomermaster t WHERE t.relation = :relation"),
    @NamedQuery(name = "TdCustomermaster.findByDob", query = "SELECT t FROM TdCustomermaster t WHERE t.dob = :dob"),
    @NamedQuery(name = "TdCustomermaster.findByOccupation", query = "SELECT t FROM TdCustomermaster t WHERE t.occupation = :occupation"),
    @NamedQuery(name = "TdCustomermaster.findByEnteredby", query = "SELECT t FROM TdCustomermaster t WHERE t.enteredby = :enteredby"),
    @NamedQuery(name = "TdCustomermaster.findByLastupdatedt", query = "SELECT t FROM TdCustomermaster t WHERE t.lastupdatedt = :lastupdatedt"),
    @NamedQuery(name = "TdCustomermaster.findByTdLines", query = "SELECT t FROM TdCustomermaster t WHERE t.tdLines = :tdLines"),
    @NamedQuery(name = "TdCustomermaster.findByActype", query = "SELECT t FROM TdCustomermaster t WHERE t.tdCustomermasterPK.actype = :actype"),
    @NamedQuery(name = "TdCustomermaster.findByAgcode", query = "SELECT t FROM TdCustomermaster t WHERE t.agcode = :agcode"),
    @NamedQuery(name = "TdCustomermaster.findByFathername", query = "SELECT t FROM TdCustomermaster t WHERE t.fathername = :fathername"),
    @NamedQuery(name = "TdCustomermaster.findByBrncode", query = "SELECT t FROM TdCustomermaster t WHERE t.tdCustomermasterPK.brncode = :brncode")})
public class TdCustomermaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TdCustomermasterPK tdCustomermasterPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "custname")
    private String custname;
    @Size(max = 100)
    @Column(name = "craddress")
    private String craddress;
    @Size(max = 100)
    @Column(name = "praddress")
    private String praddress;
    @Size(max = 255)
    @Column(name = "phoneno")
    private String phoneno;
    @Size(max = 20)
    @Column(name = "panno")
    private String panno;
    @Size(max = 2)
    @Column(name = "status")
    private String status;
    @Size(max = 40)
    @Column(name = "grdname")
    private String grdname;
    @Size(max = 20)
    @Column(name = "relation")
    private String relation;
    @Size(max = 8)
    @Column(name = "dob")
    private String dob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "occupation")
    private short occupation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "enteredby")
    private String enteredby;
    @Size(max = 8)
    @Column(name = "lastupdatedt")
    private String lastupdatedt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "td_lines")
    private Integer tdLines;
    @Size(max = 4)
    @Column(name = "agcode")
    private String agcode;
    @Size(max = 40)
    @Column(name = "fathername")
    private String fathername;

    public TdCustomermaster() {
    }

    public TdCustomermaster(TdCustomermasterPK tdCustomermasterPK) {
        this.tdCustomermasterPK = tdCustomermasterPK;
    }

    public TdCustomermaster(TdCustomermasterPK tdCustomermasterPK, String title, String custname, short occupation, String enteredby) {
        this.tdCustomermasterPK = tdCustomermasterPK;
        this.title = title;
        this.custname = custname;
        this.occupation = occupation;
        this.enteredby = enteredby;
    }

    public TdCustomermaster(String custno, String actype, String brncode) {
        this.tdCustomermasterPK = new TdCustomermasterPK(custno, actype, brncode);
    }

    public TdCustomermasterPK getTdCustomermasterPK() {
        return tdCustomermasterPK;
    }

    public void setTdCustomermasterPK(TdCustomermasterPK tdCustomermasterPK) {
        this.tdCustomermasterPK = tdCustomermasterPK;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCraddress() {
        return craddress;
    }

    public void setCraddress(String craddress) {
        this.craddress = craddress;
    }

    public String getPraddress() {
        return praddress;
    }

    public void setPraddress(String praddress) {
        this.praddress = praddress;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrdname() {
        return grdname;
    }

    public void setGrdname(String grdname) {
        this.grdname = grdname;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public short getOccupation() {
        return occupation;
    }

    public void setOccupation(short occupation) {
        this.occupation = occupation;
    }

    public String getEnteredby() {
        return enteredby;
    }

    public void setEnteredby(String enteredby) {
        this.enteredby = enteredby;
    }

    public String getLastupdatedt() {
        return lastupdatedt;
    }

    public void setLastupdatedt(String lastupdatedt) {
        this.lastupdatedt = lastupdatedt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getTdLines() {
        return tdLines;
    }

    public void setTdLines(Integer tdLines) {
        this.tdLines = tdLines;
    }

    public String getAgcode() {
        return agcode;
    }

    public void setAgcode(String agcode) {
        this.agcode = agcode;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdCustomermasterPK != null ? tdCustomermasterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TdCustomermaster)) {
            return false;
        }
        TdCustomermaster other = (TdCustomermaster) object;
        if ((this.tdCustomermasterPK == null && other.tdCustomermasterPK != null) || (this.tdCustomermasterPK != null && !this.tdCustomermasterPK.equals(other.tdCustomermasterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.TdCustomermaster[ tdCustomermasterPK=" + tdCustomermasterPK + " ]";
    }
}
