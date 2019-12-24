/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sipl
 */
@Entity
@Table(name = "investment_fdr_int_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentFdrIntHis.findAll", query = "SELECT i FROM InvestmentFdrIntHis i"),
    @NamedQuery(name = "InvestmentFdrIntHis.findByCtrlNo", query = "SELECT i FROM InvestmentFdrIntHis i WHERE i.investmentFdrIntHisPK.ctrlNo = :ctrlNo"),
    @NamedQuery(name = "InvestmentFdrIntHis.findByGlhead", query = "SELECT i FROM InvestmentFdrIntHis i WHERE i.glhead = :glhead"),
    @NamedQuery(name = "InvestmentFdrIntHis.findByCramt", query = "SELECT i FROM InvestmentFdrIntHis i WHERE i.cramt = :cramt"),
    @NamedQuery(name = "InvestmentFdrIntHis.findByDramt", query = "SELECT i FROM InvestmentFdrIntHis i WHERE i.dramt = :dramt"),
    @NamedQuery(name = "InvestmentFdrIntHis.findByDt", query = "SELECT i FROM InvestmentFdrIntHis i WHERE i.investmentFdrIntHisPK.dt = :dt"),
    @NamedQuery(name = "InvestmentFdrIntHis.findByEnterby", query = "SELECT i FROM InvestmentFdrIntHis i WHERE i.enterby = :enterby"),
    @NamedQuery(name = "InvestmentFdrIntHis.findByTrantime", query = "SELECT i FROM InvestmentFdrIntHis i WHERE i.trantime = :trantime")})
public class InvestmentFdrIntHis extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvestmentFdrIntHisPK investmentFdrIntHisPK;
    @Size(max = 12)
    @Column(name = "GLHEAD")
    private String glhead;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CRAMT")
    private Double cramt;
    @Column(name = "DRAMT")
    private Double dramt;
    @Size(max = 20)
    @Column(name = "ENTERBY")
    private String enterby;
    @Column(name = "TRANTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trantime;

    public InvestmentFdrIntHis() {
    }

    public InvestmentFdrIntHis(InvestmentFdrIntHisPK investmentFdrIntHisPK) {
        this.investmentFdrIntHisPK = investmentFdrIntHisPK;
    }

    public InvestmentFdrIntHis(long ctrlNo, Date dt) {
        this.investmentFdrIntHisPK = new InvestmentFdrIntHisPK(ctrlNo, dt);
    }

    public InvestmentFdrIntHisPK getInvestmentFdrIntHisPK() {
        return investmentFdrIntHisPK;
    }

    public void setInvestmentFdrIntHisPK(InvestmentFdrIntHisPK investmentFdrIntHisPK) {
        this.investmentFdrIntHisPK = investmentFdrIntHisPK;
    }

    public String getGlhead() {
        return glhead;
    }

    public void setGlhead(String glhead) {
        this.glhead = glhead;
    }

    public Double getCramt() {
        return cramt;
    }

    public void setCramt(Double cramt) {
        this.cramt = cramt;
    }

    public Double getDramt() {
        return dramt;
    }

    public void setDramt(Double dramt) {
        this.dramt = dramt;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public Date getTrantime() {
        return trantime;
    }

    public void setTrantime(Date trantime) {
        this.trantime = trantime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (investmentFdrIntHisPK != null ? investmentFdrIntHisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentFdrIntHis)) {
            return false;
        }
        InvestmentFdrIntHis other = (InvestmentFdrIntHis) object;
        if ((this.investmentFdrIntHisPK == null && other.investmentFdrIntHisPK != null) || (this.investmentFdrIntHisPK != null && !this.investmentFdrIntHisPK.equals(other.investmentFdrIntHisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentFdrIntHis[ investmentFdrIntHisPK=" + investmentFdrIntHisPK + " ]";
    }
    
}
