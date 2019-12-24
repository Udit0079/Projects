/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "cbs_scheme_valid_instruments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeValidInstruments.findAll", query = "SELECT c FROM CbsSchemeValidInstruments c"),
    @NamedQuery(name = "CbsSchemeValidInstruments.findBySchemeCode", query = "SELECT c FROM CbsSchemeValidInstruments c WHERE c.cbsSchemeValidInstrumentsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeValidInstruments.findByInstrumentCode", query = "SELECT c FROM CbsSchemeValidInstruments c WHERE c.cbsSchemeValidInstrumentsPK.instrumentCode = :instrumentCode"),
    @NamedQuery(name = "CbsSchemeValidInstruments.findBySchemeType", query = "SELECT c FROM CbsSchemeValidInstruments c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeValidInstruments.findByCrDrIndFlag", query = "SELECT c FROM CbsSchemeValidInstruments c WHERE c.crDrIndFlag = :crDrIndFlag"),
    @NamedQuery(name = "CbsSchemeValidInstruments.findByDelFlag", query = "SELECT c FROM CbsSchemeValidInstruments c WHERE c.delFlag = :delFlag")})
public class CbsSchemeValidInstruments extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeValidInstrumentsPK cbsSchemeValidInstrumentsPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CR_DR_IND_FLAG")
    private String crDrIndFlag;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeValidInstruments() {
    }

    public CbsSchemeValidInstruments(CbsSchemeValidInstrumentsPK cbsSchemeValidInstrumentsPK) {
        this.cbsSchemeValidInstrumentsPK = cbsSchemeValidInstrumentsPK;
    }

    public CbsSchemeValidInstruments(CbsSchemeValidInstrumentsPK cbsSchemeValidInstrumentsPK, String schemeType, String crDrIndFlag) {
        this.cbsSchemeValidInstrumentsPK = cbsSchemeValidInstrumentsPK;
        this.schemeType = schemeType;
        this.crDrIndFlag = crDrIndFlag;
    }

    public CbsSchemeValidInstruments(String schemeCode, String instrumentCode) {
        this.cbsSchemeValidInstrumentsPK = new CbsSchemeValidInstrumentsPK(schemeCode, instrumentCode);
    }

    public CbsSchemeValidInstrumentsPK getCbsSchemeValidInstrumentsPK() {
        return cbsSchemeValidInstrumentsPK;
    }

    public void setCbsSchemeValidInstrumentsPK(CbsSchemeValidInstrumentsPK cbsSchemeValidInstrumentsPK) {
        this.cbsSchemeValidInstrumentsPK = cbsSchemeValidInstrumentsPK;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getCrDrIndFlag() {
        return crDrIndFlag;
    }

    public void setCrDrIndFlag(String crDrIndFlag) {
        this.crDrIndFlag = crDrIndFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsSchemeValidInstrumentsPK != null ? cbsSchemeValidInstrumentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeValidInstruments)) {
            return false;
        }
        CbsSchemeValidInstruments other = (CbsSchemeValidInstruments) object;
        if ((this.cbsSchemeValidInstrumentsPK == null && other.cbsSchemeValidInstrumentsPK != null) || (this.cbsSchemeValidInstrumentsPK != null && !this.cbsSchemeValidInstrumentsPK.equals(other.cbsSchemeValidInstrumentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeValidInstruments[ cbsSchemeValidInstrumentsPK=" + cbsSchemeValidInstrumentsPK + " ]";
    }
    
}
