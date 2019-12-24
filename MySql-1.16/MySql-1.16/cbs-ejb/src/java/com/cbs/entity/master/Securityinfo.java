/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

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
 * @author mayank
 */
@Entity
@Table(name = "securityinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Securityinfo.findAll", query = "SELECT s FROM Securityinfo s"),
    @NamedQuery(name = "Securityinfo.findByLevelId", query = "SELECT s FROM Securityinfo s WHERE s.levelId = :levelId"),
    @NamedQuery(name = "Securityinfo.findByUserId", query = "SELECT s FROM Securityinfo s WHERE s.userId = :userId"),
    @NamedQuery(name = "Securityinfo.findByPassword", query = "SELECT s FROM Securityinfo s WHERE s.password = :password"),
    @NamedQuery(name = "Securityinfo.findByLastUpdateDt", query = "SELECT s FROM Securityinfo s WHERE s.lastUpdateDt = :lastUpdateDt"),
    @NamedQuery(name = "Securityinfo.findByUserName", query = "SELECT s FROM Securityinfo s WHERE s.userName = :userName"),
    @NamedQuery(name = "Securityinfo.findByTocashlimit", query = "SELECT s FROM Securityinfo s WHERE s.tocashlimit = :tocashlimit"),
    @NamedQuery(name = "Securityinfo.findByLastLoginDate", query = "SELECT s FROM Securityinfo s WHERE s.lastLoginDate = :lastLoginDate"),
    @NamedQuery(name = "Securityinfo.findByAddress", query = "SELECT s FROM Securityinfo s WHERE s.address = :address"),
    @NamedQuery(name = "Securityinfo.findByStatus", query = "SELECT s FROM Securityinfo s WHERE s.status = :status"),
    @NamedQuery(name = "Securityinfo.findByEnterby", query = "SELECT s FROM Securityinfo s WHERE s.enterby = :enterby"),
    @NamedQuery(name = "Securityinfo.findByPwDate", query = "SELECT s FROM Securityinfo s WHERE s.pwDate = :pwDate"),
    @NamedQuery(name = "Securityinfo.findByLogin", query = "SELECT s FROM Securityinfo s WHERE s.login = :login"),
    @NamedQuery(name = "Securityinfo.findByCashierst", query = "SELECT s FROM Securityinfo s WHERE s.cashierst = :cashierst"),
    @NamedQuery(name = "Securityinfo.findBySid", query = "SELECT s FROM Securityinfo s WHERE s.sid = :sid"),
    @NamedQuery(name = "Securityinfo.findByClgDebit", query = "SELECT s FROM Securityinfo s WHERE s.clgDebit = :clgDebit"),
    @NamedQuery(name = "Securityinfo.findByTranDebit", query = "SELECT s FROM Securityinfo s WHERE s.tranDebit = :tranDebit"),
    @NamedQuery(name = "Securityinfo.findByBrncode", query = "SELECT s FROM Securityinfo s WHERE s.brncode = :brncode"),
    @NamedQuery(name = "Securityinfo.findByHeadcashier", query = "SELECT s FROM Securityinfo s WHERE s.headcashier = :headcashier"),
    @NamedQuery(name = "Securityinfo.findByOrgbrncode", query = "SELECT s FROM Securityinfo s WHERE s.orgbrncode = :orgbrncode"),
    @NamedQuery(name = "Securityinfo.findByTodate", query = "SELECT s FROM Securityinfo s WHERE s.todate = :todate"),
    @NamedQuery(name = "Securityinfo.findByFromdate", query = "SELECT s FROM Securityinfo s WHERE s.fromdate = :fromdate"),
    @NamedQuery(name = "Securityinfo.findByDeputeorxfer", query = "SELECT s FROM Securityinfo s WHERE s.deputeorxfer = :deputeorxfer"),
    @NamedQuery(name = "Securityinfo.findByNpciUserName", query = "SELECT s FROM Securityinfo s WHERE s.npciUserName = :npciUserName")})
public class Securityinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "LevelId")
    private Short levelId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "UserId")
    private String userId;
    @Size(max = 1000)
    @Column(name = "Password")
    private String password;
    @Size(max = 8)
    @Column(name = "LastUpdateDt")
    private String lastUpdateDt;
    @Size(max = 40)
    @Column(name = "UserName")
    private String userName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tocashlimit")
    private Double tocashlimit;
    @Column(name = "lastLoginDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;
    @Size(max = 100)
    @Column(name = "Address")
    private String address;
    @Size(max = 1)
    @Column(name = "Status")
    private String status;
    @Size(max = 20)
    @Column(name = "enterby")
    private String enterby;
    @Column(name = "pwDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pwDate;
    @Size(max = 1)
    @Column(name = "login")
    private String login;
    @Size(max = 1)
    @Column(name = "cashierst")
    private String cashierst;
    @Column(name = "sid")
    private Integer sid;
    @Column(name = "ClgDebit")
    private Double clgDebit;
    @Column(name = "TranDebit")
    private Double tranDebit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "brncode")
    private String brncode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "HEADCASHIER")
    private String headcashier;
    @Size(max = 2)
    @Column(name = "orgbrncode")
    private String orgbrncode;
    @Column(name = "todate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date todate;
    @Column(name = "fromdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromdate;
    @Size(max = 10)
    @Column(name = "deputeorxfer")
    private String deputeorxfer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NpciUserName")
    private String npciUserName;

    public Securityinfo() {
    }

    public Securityinfo(String userId) {
        this.userId = userId;
    }

    public Securityinfo(String userId, String brncode, String headcashier, String npciUserName) {
        this.userId = userId;
        this.brncode = brncode;
        this.headcashier = headcashier;
        this.npciUserName = npciUserName;
    }

    public Short getLevelId() {
        return levelId;
    }

    public void setLevelId(Short levelId) {
        this.levelId = levelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(String lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getTocashlimit() {
        return tocashlimit;
    }

    public void setTocashlimit(Double tocashlimit) {
        this.tocashlimit = tocashlimit;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public Date getPwDate() {
        return pwDate;
    }

    public void setPwDate(Date pwDate) {
        this.pwDate = pwDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCashierst() {
        return cashierst;
    }

    public void setCashierst(String cashierst) {
        this.cashierst = cashierst;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Double getClgDebit() {
        return clgDebit;
    }

    public void setClgDebit(Double clgDebit) {
        this.clgDebit = clgDebit;
    }

    public Double getTranDebit() {
        return tranDebit;
    }

    public void setTranDebit(Double tranDebit) {
        this.tranDebit = tranDebit;
    }

    public String getBrncode() {
        return brncode;
    }

    public void setBrncode(String brncode) {
        this.brncode = brncode;
    }

    public String getHeadcashier() {
        return headcashier;
    }

    public void setHeadcashier(String headcashier) {
        this.headcashier = headcashier;
    }

    public String getOrgbrncode() {
        return orgbrncode;
    }

    public void setOrgbrncode(String orgbrncode) {
        this.orgbrncode = orgbrncode;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public String getDeputeorxfer() {
        return deputeorxfer;
    }

    public void setDeputeorxfer(String deputeorxfer) {
        this.deputeorxfer = deputeorxfer;
    }

    public String getNpciUserName() {
        return npciUserName;
    }

    public void setNpciUserName(String npciUserName) {
        this.npciUserName = npciUserName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Securityinfo)) {
            return false;
        }
        Securityinfo other = (Securityinfo) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.Securityinfo[ userId=" + userId + " ]";
    }
    
}
