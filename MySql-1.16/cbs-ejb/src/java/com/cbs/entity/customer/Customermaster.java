/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "customermaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customermaster.findAll", query = "SELECT c FROM Customermaster c"),
    @NamedQuery(name = "Customermaster.findByCustno", query = "SELECT c FROM Customermaster c WHERE c.customermasterPK.custno = :custno"),
    @NamedQuery(name = "Customermaster.findByTitle", query = "SELECT c FROM Customermaster c WHERE c.title = :title"),
    @NamedQuery(name = "Customermaster.findByCustname", query = "SELECT c FROM Customermaster c WHERE c.custname = :custname"),
    @NamedQuery(name = "Customermaster.findByCraddress", query = "SELECT c FROM Customermaster c WHERE c.craddress = :craddress"),
    @NamedQuery(name = "Customermaster.findByPraddress", query = "SELECT c FROM Customermaster c WHERE c.praddress = :praddress"),
    @NamedQuery(name = "Customermaster.findByPhoneno", query = "SELECT c FROM Customermaster c WHERE c.phoneno = :phoneno"),
    @NamedQuery(name = "Customermaster.findByPanno", query = "SELECT c FROM Customermaster c WHERE c.panno = :panno"),
    @NamedQuery(name = "Customermaster.findByStatus", query = "SELECT c FROM Customermaster c WHERE c.status = :status"),
    @NamedQuery(name = "Customermaster.findByGrdname", query = "SELECT c FROM Customermaster c WHERE c.grdname = :grdname"),
    @NamedQuery(name = "Customermaster.findByRelation", query = "SELECT c FROM Customermaster c WHERE c.relation = :relation"),
    @NamedQuery(name = "Customermaster.findByDob", query = "SELECT c FROM Customermaster c WHERE c.dob = :dob"),
    @NamedQuery(name = "Customermaster.findByOccupation", query = "SELECT c FROM Customermaster c WHERE c.occupation = :occupation"),
    @NamedQuery(name = "Customermaster.findByEnteredby", query = "SELECT c FROM Customermaster c WHERE c.enteredby = :enteredby"),
    @NamedQuery(name = "Customermaster.findByLastupdatedt", query = "SELECT c FROM Customermaster c WHERE c.lastupdatedt = :lastupdatedt"),
    @NamedQuery(name = "Customermaster.findByCustLines", query = "SELECT c FROM Customermaster c WHERE c.custLines = :custLines"),
    @NamedQuery(name = "Customermaster.findByActype", query = "SELECT c FROM Customermaster c WHERE c.customermasterPK.actype = :actype"),
    @NamedQuery(name = "Customermaster.findByAgcode1", query = "SELECT c FROM Customermaster c WHERE c.agcode1 = :agcode1"),
    @NamedQuery(name = "Customermaster.findByAgcode", query = "SELECT c FROM Customermaster c WHERE c.customermasterPK.agcode = :agcode"),
    @NamedQuery(name = "Customermaster.findByFathername", query = "SELECT c FROM Customermaster c WHERE c.fathername = :fathername"),
    @NamedQuery(name = "Customermaster.findByBrncode", query = "SELECT c FROM Customermaster c WHERE c.customermasterPK.brncode = :brncode")})
public class Customermaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CustomermasterPK customermasterPK;
    @Size(max = 10)
    @Column(name = "title")
    private String title;
    @Size(max = 70)
    @Column(name = "custname")
    private String custname;
    @Size(max = 100)
    @Column(name = "craddress")
    private String craddress;
    @Size(max = 100)
    @Column(name = "praddress")
    private String praddress;
    @Size(max = 100)
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
    @Size(max = 30)
    @Column(name = "relation")
    private String relation;
    @Size(max = 8)
    @Column(name = "dob")
    private String dob;
    @Column(name = "occupation")
    private Short occupation;
    @Size(max = 20)
    @Column(name = "enteredby")
    private String enteredby;
    @Size(max = 8)
    @Column(name = "lastupdatedt")
    private String lastupdatedt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "cust_lines")
    private Integer custLines;
    @Column(name = "agcode1")
    private Integer agcode1;
    @Size(max = 50)
    @Column(name = "fathername")
    private String fathername;

    public Customermaster() {
    }

    public Customermaster(CustomermasterPK customermasterPK) {
        this.customermasterPK = customermasterPK;
    }

    public Customermaster(String custno, String actype, String agcode, String brncode) {
        this.customermasterPK = new CustomermasterPK(custno, actype, agcode, brncode);
    }

    public CustomermasterPK getCustomermasterPK() {
        return customermasterPK;
    }

    public void setCustomermasterPK(CustomermasterPK customermasterPK) {
        this.customermasterPK = customermasterPK;
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

    public Short getOccupation() {
        return occupation;
    }

    public void setOccupation(Short occupation) {
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

    public Integer getCustLines() {
        return custLines;
    }

    public void setCustLines(Integer custLines) {
        this.custLines = custLines;
    }

    public Integer getAgcode1() {
        return agcode1;
    }

    public void setAgcode1(Integer agcode1) {
        this.agcode1 = agcode1;
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
        hash += (customermasterPK != null ? customermasterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customermaster)) {
            return false;
        }
        Customermaster other = (Customermaster) object;
        if ((this.customermasterPK == null && other.customermasterPK != null) || (this.customermasterPK != null && !this.customermasterPK.equals(other.customermasterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.Customermaster[ customermasterPK=" + customermasterPK + " ]";
    }
}
