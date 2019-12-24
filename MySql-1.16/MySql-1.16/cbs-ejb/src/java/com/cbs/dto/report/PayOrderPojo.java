package com.cbs.dto.report;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Zeeshan Waris
 */
public class PayOrderPojo implements java.io.Serializable {

    private String instno;
    private double seqno;
    private Date origindt;
    private String infavourof;
    private Integer trantype;
    private String trantypechar;
    private BigDecimal amount;
    private String comm;
    private String status;
    private String enterby;
    private Integer ty;
    private String purchaser;
    private Date dt;
    private String flag;
    private BigDecimal openbal;
    private BigDecimal sumbal;
    private String alphaCode;
    private String bankname;
    private String bankaddress;

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankaddress() {
        return bankaddress;
    }

    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getInfavourof() {
        return infavourof;
    }

    public void setInfavourof(String infavourof) {
        this.infavourof = infavourof;
    }

    public String getInstno() {
        return instno;
    }

    public void setInstno(String instno) {
        this.instno = instno;
    }

    public BigDecimal getOpenbal() {
        return openbal;
    }

    public void setOpenbal(BigDecimal openbal) {
        this.openbal = openbal;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public double getSeqno() {
        return seqno;
    }

    public void setSeqno(double seqno) {
        this.seqno = seqno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getSumbal() {
        return sumbal;
    }

    public void setSumbal(BigDecimal sumbal) {
        this.sumbal = sumbal;
    }

    public Integer getTrantype() {
        return trantype;
    }

    public void setTrantype(Integer trantype) {
        this.trantype = trantype;
    }

    public String getTrantypechar() {
        return trantypechar;
    }

    public void setTrantypechar(String trantypechar) {
        this.trantypechar = trantypechar;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Date getOrigindt() {
        return origindt;
    }

    public void setOrigindt(Date origindt) {
        this.origindt = origindt;
    }
}
