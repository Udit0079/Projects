/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "investment_call_head")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentCallHead.findAll", query = "SELECT i FROM InvestmentCallHead i"),
    @NamedQuery(name = "InvestmentCallHead.findByCode", query = "SELECT i FROM InvestmentCallHead i WHERE i.code = :code"),
    @NamedQuery(name = "InvestmentCallHead.findByDetails", query = "SELECT i FROM InvestmentCallHead i WHERE i.details = :details"),
    @NamedQuery(name = "InvestmentCallHead.findByCrGlhead", query = "SELECT i FROM InvestmentCallHead i WHERE i.crGlhead = :crGlhead"),
    @NamedQuery(name = "InvestmentCallHead.findByDrGlhead", query = "SELECT i FROM InvestmentCallHead i WHERE i.drGlhead = :drGlhead"),
    @NamedQuery(name = "InvestmentCallHead.findByIntGlhead", query = "SELECT i FROM InvestmentCallHead i WHERE i.intGlhead = :intGlhead"),
    @NamedQuery(name = "InvestmentCallHead.findByEnterBy", query = "SELECT i FROM InvestmentCallHead i WHERE i.enterBy = :enterBy"),
    @NamedQuery(name = "InvestmentCallHead.findByTranTime", query = "SELECT i FROM InvestmentCallHead i WHERE i.tranTime = :tranTime")})
public class InvestmentCallHead extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODE")
    private String code;
    @Size(max = 50)
    @Column(name = "DETAILS")
    private String details;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "CR_GLHEAD")
    private String crGlhead;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "DR_GLHEAD")
    private String drGlhead;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "INT_GLHEAD")
    private String intGlhead;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TRAN_TIME")
    private String tranTime;

    public InvestmentCallHead() {
    }

    public InvestmentCallHead(String code) {
        this.code = code;
    }

    public InvestmentCallHead(String code, String crGlhead, String drGlhead, String intGlhead, String enterBy, String tranTime) {
        this.code = code;
        this.crGlhead = crGlhead;
        this.drGlhead = drGlhead;
        this.intGlhead = intGlhead;
        this.enterBy = enterBy;
        this.tranTime = tranTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCrGlhead() {
        return crGlhead;
    }

    public void setCrGlhead(String crGlhead) {
        this.crGlhead = crGlhead;
    }

    public String getDrGlhead() {
        return drGlhead;
    }

    public void setDrGlhead(String drGlhead) {
        this.drGlhead = drGlhead;
    }

    public String getIntGlhead() {
        return intGlhead;
    }

    public void setIntGlhead(String intGlhead) {
        this.intGlhead = intGlhead;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentCallHead)) {
            return false;
        }
        InvestmentCallHead other = (InvestmentCallHead) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentCallHead[ code=" + code + " ]";
    }
}
