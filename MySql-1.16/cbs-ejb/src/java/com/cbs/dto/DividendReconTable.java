/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author Navneet
 */
public class DividendReconTable implements Comparable {

    private String acno, certno, enterby, authby, details, bcode, dest_brnid, org_brnid, auth, disdate, fyear;
    private Double dramt, cramt;
    private Integer ty, trantype, iy;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
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

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public Double getCramt() {
        return cramt;
    }

    public void setCramt(Double cramt) {
        this.cramt = cramt;
    }

    public String getDest_brnid() {
        return dest_brnid;
    }

    public void setDest_brnid(String dest_brnid) {
        this.dest_brnid = dest_brnid;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDisdate() {
        return disdate;
    }

    public void setDisdate(String disdate) {
        this.disdate = disdate;
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

    public String getFyear() {
        return fyear;
    }

    public void setFyear(String fyear) {
        this.fyear = fyear;
    }

    public Integer getIy() {
        return iy;
    }

    public void setIy(Integer iy) {
        this.iy = iy;
    }

    public String getOrg_brnid() {
        return org_brnid;
    }

    public void setOrg_brnid(String org_brnid) {
        this.org_brnid = org_brnid;
    }

    public Integer getTrantype() {
        return trantype;
    }

    public void setTrantype(Integer trantype) {
        this.trantype = trantype;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public int compareTo(Object object) {
        return acno.compareTo(((DividendReconTable) object).acno);
    }
}
