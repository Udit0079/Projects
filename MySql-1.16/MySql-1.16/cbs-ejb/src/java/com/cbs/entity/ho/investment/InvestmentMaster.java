/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author mayank
 */
@Entity
@Table(name = "investment_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentMaster.findAll", query = "SELECT i FROM InvestmentMaster i"),
    @NamedQuery(name = "InvestmentMaster.findByControlno", query = "SELECT i FROM InvestmentMaster i WHERE i.investmentMasterPK.controlno = :controlno"),
    @NamedQuery(name = "InvestmentMaster.findBySectype", query = "SELECT i FROM InvestmentMaster i WHERE i.investmentMasterPK.sectype = :sectype"),
    @NamedQuery(name = "InvestmentMaster.findBySecdesc", query = "SELECT i FROM InvestmentMaster i WHERE i.secdesc = :secdesc"),
    @NamedQuery(name = "InvestmentMaster.findByInvstcode", query = "SELECT i FROM InvestmentMaster i WHERE i.invstcode = :invstcode"),
    @NamedQuery(name = "InvestmentMaster.findByTransno", query = "SELECT i FROM InvestmentMaster i WHERE i.transno = :transno"),
    @NamedQuery(name = "InvestmentMaster.findByRoi", query = "SELECT i FROM InvestmentMaster i WHERE i.roi = :roi"),
    @NamedQuery(name = "InvestmentMaster.findByTransdt", query = "SELECT i FROM InvestmentMaster i WHERE i.transdt = :transdt"),
    @NamedQuery(name = "InvestmentMaster.findBySettledt", query = "SELECT i FROM InvestmentMaster i WHERE i.settledt = :settledt"),
    @NamedQuery(name = "InvestmentMaster.findByPdDay", query = "SELECT i FROM InvestmentMaster i WHERE i.pdDay = :pdDay"),
    @NamedQuery(name = "InvestmentMaster.findByPdMon", query = "SELECT i FROM InvestmentMaster i WHERE i.pdMon = :pdMon"),
    @NamedQuery(name = "InvestmentMaster.findByPdYear", query = "SELECT i FROM InvestmentMaster i WHERE i.pdYear = :pdYear"),
    @NamedQuery(name = "InvestmentMaster.findByMatdt", query = "SELECT i FROM InvestmentMaster i WHERE i.matdt = :matdt"),
    @NamedQuery(name = "InvestmentMaster.findBySellername", query = "SELECT i FROM InvestmentMaster i WHERE i.sellername = :sellername"),
    @NamedQuery(name = "InvestmentMaster.findByBuyeracno", query = "SELECT i FROM InvestmentMaster i WHERE i.buyeracno = :buyeracno"),
    @NamedQuery(name = "InvestmentMaster.findByBrokername", query = "SELECT i FROM InvestmentMaster i WHERE i.brokername = :brokername"),
    @NamedQuery(name = "InvestmentMaster.findByBrokeracno", query = "SELECT i FROM InvestmentMaster i WHERE i.brokeracno = :brokeracno"),
    @NamedQuery(name = "InvestmentMaster.findByFacevalue", query = "SELECT i FROM InvestmentMaster i WHERE i.facevalue = :facevalue"),
    @NamedQuery(name = "InvestmentMaster.findByBookvalue", query = "SELECT i FROM InvestmentMaster i WHERE i.bookvalue = :bookvalue"),
    @NamedQuery(name = "InvestmentMaster.findByIntopt", query = "SELECT i FROM InvestmentMaster i WHERE i.intopt = :intopt"),
    @NamedQuery(name = "InvestmentMaster.findByBrokerage", query = "SELECT i FROM InvestmentMaster i WHERE i.brokerage = :brokerage"),
    @NamedQuery(name = "InvestmentMaster.findByAccdint", query = "SELECT i FROM InvestmentMaster i WHERE i.accdint = :accdint"),
    @NamedQuery(name = "InvestmentMaster.findByEnterby", query = "SELECT i FROM InvestmentMaster i WHERE i.enterby = :enterby"),
    @NamedQuery(name = "InvestmentMaster.findByAuth", query = "SELECT i FROM InvestmentMaster i WHERE i.auth = :auth"),
    @NamedQuery(name = "InvestmentMaster.findByAuthby", query = "SELECT i FROM InvestmentMaster i WHERE i.authby = :authby"),
    @NamedQuery(name = "InvestmentMaster.findByDt", query = "SELECT i FROM InvestmentMaster i WHERE i.dt = :dt"),
    @NamedQuery(name = "InvestmentMaster.findByLastupdateby", query = "SELECT i FROM InvestmentMaster i WHERE i.lastupdateby = :lastupdateby"),
    @NamedQuery(name = "InvestmentMaster.findByLastupdatedt", query = "SELECT i FROM InvestmentMaster i WHERE i.lastupdatedt = :lastupdatedt"),
    @NamedQuery(name = "InvestmentMaster.findByClosedt", query = "SELECT i FROM InvestmentMaster i WHERE i.closedt = :closedt"),
    @NamedQuery(name = "InvestmentMaster.findByNoofshares", query = "SELECT i FROM InvestmentMaster i WHERE i.noofshares = :noofshares"),
    @NamedQuery(name = "InvestmentMaster.findByPricegsec", query = "SELECT i FROM InvestmentMaster i WHERE i.pricegsec = :pricegsec"),
    @NamedQuery(name = "InvestmentMaster.findByClosepricegsec", query = "SELECT i FROM InvestmentMaster i WHERE i.closepricegsec = :closepricegsec"),
    @NamedQuery(name = "InvestmentMaster.findByContpersonnm", query = "SELECT i FROM InvestmentMaster i WHERE i.contpersonnm = :contpersonnm"),
    @NamedQuery(name = "InvestmentMaster.findByContpersphone", query = "SELECT i FROM InvestmentMaster i WHERE i.contpersphone = :contpersphone"),
    @NamedQuery(name = "InvestmentMaster.findByVouchno", query = "SELECT i FROM InvestmentMaster i WHERE i.vouchno = :vouchno"),
    @NamedQuery(name = "InvestmentMaster.findByYtm", query = "SELECT i FROM InvestmentMaster i WHERE i.ytm = :ytm"),
    @NamedQuery(name = "InvestmentMaster.findByMarking", query = "SELECT i FROM InvestmentMaster i WHERE i.marking = :marking")})
public class InvestmentMaster extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvestmentMasterPK investmentMasterPK;
    @Size(max = 200)
    @Column(name = "SECDESC")
    private String secdesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "INVSTCODE")
    private String invstcode;
    @Size(max = 15)
    @Column(name = "TRANSNO")
    private String transno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROI")
    private double roi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANSDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transdt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SETTLEDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date settledt;
    @Column(name = "PD_DAY")
    private Integer pdDay;
    @Column(name = "PD_MON")
    private Integer pdMon;
    @Column(name = "PD_YEAR")
    private Integer pdYear;
    @Column(name = "MATDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date matdt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "SELLERNAME")
    private String sellername;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "BUYERACNO")
    private String buyeracno;
    @Size(max = 30)
    @Column(name = "BROKERNAME")
    private String brokername;
    @Size(max = 12)
    @Column(name = "BROKERACNO")
    private String brokeracno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FACEVALUE")
    private double facevalue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BOOKVALUE")
    private double bookvalue;
    @Size(max = 1)
    @Column(name = "INTOPT")
    private String intopt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BROKERAGE")
    private Double brokerage;
    @Column(name = "ACCDINT")
    private Double accdint;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ENTERBY")
    private String enterby;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "AUTH")
    private String auth;
    @Size(max = 20)
    @Column(name = "AUTHBY")
    private String authby;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Size(max = 20)
    @Column(name = "LASTUPDATEBY")
    private String lastupdateby;
    @Column(name = "LASTUPDATEDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatedt;
    @Column(name = "CLOSEDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOOFSHARES")
    private int noofshares;
    @Column(name = "PRICEGSEC")
    private Double pricegsec;
    @Column(name = "CLOSEPRICEGSEC")
    private Double closepricegsec;
    @Size(max = 50)
    @Column(name = "CONTPERSONNM")
    private String contpersonnm;
    @Size(max = 12)
    @Column(name = "CONTPERSPHONE")
    private String contpersphone;
    @Size(max = 8)
    @Column(name = "VOUCHNO")
    private String vouchno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ytm")
    private double ytm;
    @Column(name = "marking")
    private String marking;

    public InvestmentMaster() {
    }

    public InvestmentMaster(InvestmentMasterPK investmentMasterPK) {
        this.investmentMasterPK = investmentMasterPK;
    }

    public InvestmentMaster(InvestmentMasterPK investmentMasterPK, String invstcode, double roi, Date transdt, Date settledt, String sellername, String buyeracno, double facevalue, double bookvalue, String enterby, String auth, Date dt, int noofshares, double ytm) {
        this.investmentMasterPK = investmentMasterPK;
        this.invstcode = invstcode;
        this.roi = roi;
        this.transdt = transdt;
        this.settledt = settledt;
        this.sellername = sellername;
        this.buyeracno = buyeracno;
        this.facevalue = facevalue;
        this.bookvalue = bookvalue;
        this.enterby = enterby;
        this.auth = auth;
        this.dt = dt;
        this.noofshares = noofshares;
        this.ytm = ytm;
    }

    public InvestmentMaster(InvestmentMasterPK investmentMasterPK, String secdesc, String invstcode, double roi, Date transdt, Date settledt, Date dt, String sellername, String buyeracno, String brokeracno, double facevalue, Double brokerage, Double accdint, String enterby, Date matdt, double bookvalue, String brokername, String auth, String authby, Double pricegsec, double ytm, String marking) {
        this.investmentMasterPK = investmentMasterPK;
        this.secdesc = secdesc;
        this.invstcode = invstcode;
        this.roi = roi;
        this.transdt = transdt;
        this.settledt = settledt;
        this.dt = dt;
        this.sellername = sellername;
        this.buyeracno = buyeracno;
        this.brokeracno = brokeracno;
        this.facevalue = facevalue;
        this.brokerage = brokerage;
        this.accdint = accdint;
        this.enterby = enterby;
        this.matdt = matdt;
        this.bookvalue = bookvalue;
        this.brokername = brokername;
        this.auth = auth;
        this.authby = authby;
        this.pricegsec = pricegsec;
        this.ytm = ytm;
        this.marking = marking;
    }
    
    public InvestmentMaster(int controlno, String sectype) {
        this.investmentMasterPK = new InvestmentMasterPK(controlno, sectype);
    }

    public InvestmentMasterPK getInvestmentMasterPK() {
        return investmentMasterPK;
    }

    public void setInvestmentMasterPK(InvestmentMasterPK investmentMasterPK) {
        this.investmentMasterPK = investmentMasterPK;
    }

    public String getSecdesc() {
        return secdesc;
    }

    public void setSecdesc(String secdesc) {
        this.secdesc = secdesc;
    }

    public String getInvstcode() {
        return invstcode;
    }

    public void setInvstcode(String invstcode) {
        this.invstcode = invstcode;
    }

    public String getTransno() {
        return transno;
    }

    public void setTransno(String transno) {
        this.transno = transno;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public Date getTransdt() {
        return transdt;
    }

    public void setTransdt(Date transdt) {
        this.transdt = transdt;
    }

    public Date getSettledt() {
        return settledt;
    }

    public void setSettledt(Date settledt) {
        this.settledt = settledt;
    }

    public Integer getPdDay() {
        return pdDay;
    }

    public void setPdDay(Integer pdDay) {
        this.pdDay = pdDay;
    }

    public Integer getPdMon() {
        return pdMon;
    }

    public void setPdMon(Integer pdMon) {
        this.pdMon = pdMon;
    }

    public Integer getPdYear() {
        return pdYear;
    }

    public void setPdYear(Integer pdYear) {
        this.pdYear = pdYear;
    }

    public Date getMatdt() {
        return matdt;
    }

    public void setMatdt(Date matdt) {
        this.matdt = matdt;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    public String getBuyeracno() {
        return buyeracno;
    }

    public void setBuyeracno(String buyeracno) {
        this.buyeracno = buyeracno;
    }

    public String getBrokername() {
        return brokername;
    }

    public void setBrokername(String brokername) {
        this.brokername = brokername;
    }

    public String getBrokeracno() {
        return brokeracno;
    }

    public void setBrokeracno(String brokeracno) {
        this.brokeracno = brokeracno;
    }

    public double getFacevalue() {
        return facevalue;
    }

    public void setFacevalue(double facevalue) {
        this.facevalue = facevalue;
    }

    public double getBookvalue() {
        return bookvalue;
    }

    public void setBookvalue(double bookvalue) {
        this.bookvalue = bookvalue;
    }

    public String getIntopt() {
        return intopt;
    }

    public void setIntopt(String intopt) {
        this.intopt = intopt;
    }

    public Double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Double brokerage) {
        this.brokerage = brokerage;
    }

    public Double getAccdint() {
        return accdint;
    }

    public void setAccdint(Double accdint) {
        this.accdint = accdint;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthby() {
        return authby;
    }

    public void setAuthby(String authby) {
        this.authby = authby;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getLastupdateby() {
        return lastupdateby;
    }

    public void setLastupdateby(String lastupdateby) {
        this.lastupdateby = lastupdateby;
    }

    public Date getLastupdatedt() {
        return lastupdatedt;
    }

    public void setLastupdatedt(Date lastupdatedt) {
        this.lastupdatedt = lastupdatedt;
    }

    public Date getClosedt() {
        return closedt;
    }

    public void setClosedt(Date closedt) {
        this.closedt = closedt;
    }

    public int getNoofshares() {
        return noofshares;
    }

    public void setNoofshares(int noofshares) {
        this.noofshares = noofshares;
    }

    public Double getPricegsec() {
        return pricegsec;
    }

    public void setPricegsec(Double pricegsec) {
        this.pricegsec = pricegsec;
    }

    public Double getClosepricegsec() {
        return closepricegsec;
    }

    public void setClosepricegsec(Double closepricegsec) {
        this.closepricegsec = closepricegsec;
    }

    public String getContpersonnm() {
        return contpersonnm;
    }

    public void setContpersonnm(String contpersonnm) {
        this.contpersonnm = contpersonnm;
    }

    public String getContpersphone() {
        return contpersphone;
    }

    public void setContpersphone(String contpersphone) {
        this.contpersphone = contpersphone;
    }

    public String getVouchno() {
        return vouchno;
    }

    public void setVouchno(String vouchno) {
        this.vouchno = vouchno;
    }

    public double getYtm() {
        return ytm;
    }

    public void setYtm(double ytm) {
        this.ytm = ytm;
    }

    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        this.marking = marking;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (investmentMasterPK != null ? investmentMasterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentMaster)) {
            return false;
        }
        InvestmentMaster other = (InvestmentMaster) object;
        if ((this.investmentMasterPK == null && other.investmentMasterPK != null) || (this.investmentMasterPK != null && !this.investmentMasterPK.equals(other.investmentMasterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentMaster[ investmentMasterPK=" + investmentMasterPK + " ]";
    }
    
}
