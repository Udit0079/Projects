/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.ho;

/**
 *
 * @author root
 */
public class GridReconsilation {

    private String originBranch;
    private String respondingBranch;
    private String dt;
    private String originDt;
    private String respondDt;
    private Float amount;
    private String enterBY;
    private String details;
    private String authBy;
    private String lastUpdateBy;
    private String lastUpdateDt;
    private String instNo;
    private String image;
    private String entrytype;
    private Integer ty;

    public String getEntrytype() {
        return entrytype;
    }

    public void setEntrytype(String entrytype) {
        this.entrytype = entrytype;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getOriginDt() {
        return originDt;
    }

    public void setOriginDt(String originDt) {
        this.originDt = originDt;
    }

    public String getRespondDt() {
        return respondDt;
    }

    public void setRespondDt(String respondDt) {
        this.respondDt = respondDt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(String lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getRespondingBranch() {
        return respondingBranch;
    }

    public void setRespondingBranch(String respondingBranch) {
        this.respondingBranch = respondingBranch;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEnterBY() {
        return enterBY;
    }

    public void setEnterBY(String enterBY) {
        this.enterBY = enterBY;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getOriginBranch() {
        return originBranch;
    }

    public void setOriginBranch(String originBranch) {
        this.originBranch = originBranch;
    }
}
