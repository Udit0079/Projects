/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "eps_chargedetails")
@NamedQueries({
    @NamedQuery(name = "EPSChargeDetails.findAll", query = "SELECT e FROM EPSChargeDetails e"),
    @NamedQuery(name = "EPSChargeDetails.findByPaySysId", query = "SELECT e FROM EPSChargeDetails e WHERE e.paySysId = :paySysId"),
    @NamedQuery(name = "EPSChargeDetails.findByChargeEventID", query = "SELECT e FROM EPSChargeDetails e WHERE e.chargeEventID = :chargeEventID"),
    @NamedQuery(name = "EPSChargeDetails.findByMinLimit", query = "SELECT e FROM EPSChargeDetails e WHERE e.minLimit = :minLimit"),
    @NamedQuery(name = "EPSChargeDetails.findByMaxLimit", query = "SELECT e FROM EPSChargeDetails e WHERE e.maxLimit = :maxLimit"),
    @NamedQuery(name = "EPSChargeDetails.findByChargeAmt", query = "SELECT e FROM EPSChargeDetails e WHERE e.chargeAmt = :chargeAmt"),
    @NamedQuery(name = "EPSChargeDetails.findByChargeROT", query = "SELECT e FROM EPSChargeDetails e WHERE e.chargeROT = :chargeROT"),
    @NamedQuery(name = "EPSChargeDetails.findByStax", query = "SELECT e FROM EPSChargeDetails e WHERE e.stax = :stax"),
    @NamedQuery(name = "EPSChargeDetails.findByStaxPlaceHolder", query = "SELECT e FROM EPSChargeDetails e WHERE e.staxPlaceHolder = :staxPlaceHolder"),
    @NamedQuery(name = "EPSChargeDetails.findByPLPlaceHolder", query = "SELECT e FROM EPSChargeDetails e WHERE e.pLPlaceHolder = :pLPlaceHolder")})
public class EPSChargeDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "PaySysId")
    private String paySysId;
    @Id
    @Basic(optional = false)
    @Column(name = "ChargeEventID")
    private String chargeEventID;
    @Column(name = "MinLimit")
    private Double minLimit;
    @Column(name = "MaxLimit")
    private Double maxLimit;
    @Column(name = "ChargeAmt")
    private Double chargeAmt;
    @Column(name = "ChargeROT")
    private Double chargeROT;
    @Column(name = "Stax")
    private Double stax;
    @Column(name = "StaxPlaceHolder")
    private String staxPlaceHolder;
    @Column(name = "PLPlaceHolder")
    private String pLPlaceHolder;

    public EPSChargeDetails() {
    }

    public EPSChargeDetails(String chargeEventID) {
        this.chargeEventID = chargeEventID;
    }

    public EPSChargeDetails(String chargeEventID, String paySysId) {
        this.chargeEventID = chargeEventID;
        this.paySysId = paySysId;
    }

    /**************This Constructor is added by Dinesh***********************/
    public EPSChargeDetails(String chargeEventID, String paySysId, Double minLimit, Double maxLimit, Double chargeAmt, Double chargeROT, Double stax, String staxPlaceHolder, String pLPlaceHolder) {
        this.chargeEventID = chargeEventID;
        this.paySysId = paySysId;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
        this.chargeAmt = chargeAmt;
        this.chargeROT = chargeROT;
        this.stax = stax;
        this.staxPlaceHolder = staxPlaceHolder;
        this.pLPlaceHolder = pLPlaceHolder;
    }

    public String getPaySysId() {
        return paySysId;
    }

    public void setPaySysId(String paySysId) {
        this.paySysId = paySysId;
    }

    public String getChargeEventID() {
        return chargeEventID;
    }

    public void setChargeEventID(String chargeEventID) {
        this.chargeEventID = chargeEventID;
    }

    public Double getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(Double minLimit) {
        this.minLimit = minLimit;
    }

    public Double getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Double maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Double getChargeAmt() {
        return chargeAmt;
    }

    public void setChargeAmt(Double chargeAmt) {
        this.chargeAmt = chargeAmt;
    }

    public Double getChargeROT() {
        return chargeROT;
    }

    public void setChargeROT(Double chargeROT) {
        this.chargeROT = chargeROT;
    }

    public Double getStax() {
        return stax;
    }

    public void setStax(Double stax) {
        this.stax = stax;
    }

    public String getStaxPlaceHolder() {
        return staxPlaceHolder;
    }

    public void setStaxPlaceHolder(String staxPlaceHolder) {
        this.staxPlaceHolder = staxPlaceHolder;
    }

    public String getPLPlaceHolder() {
        return pLPlaceHolder;
    }

    public void setPLPlaceHolder(String pLPlaceHolder) {
        this.pLPlaceHolder = pLPlaceHolder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chargeEventID != null ? chargeEventID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSChargeDetails)) {
            return false;
        }
        EPSChargeDetails other = (EPSChargeDetails) object;
        if ((this.chargeEventID == null && other.chargeEventID != null) || (this.chargeEventID != null && !this.chargeEventID.equals(other.chargeEventID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSChargeDetails[chargeEventID=" + chargeEventID + "]";
    }
}
