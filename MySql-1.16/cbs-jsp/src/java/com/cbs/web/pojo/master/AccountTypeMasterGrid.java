/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.master;

/**
 *
 * @author root
 */
public class AccountTypeMasterGrid implements Comparable<Object> {

    String acctcode;
    String acctDesc;
    String acctnature;
    String glHead;
    String glheadint;
    String minbal;
    String minInt;
    String minbalcharge;
    String chqsrno;
    String GLheadProv;
    String productcode;
    String ofAcctnature;
    String staffInt;
    String minbal_chq;

    public String getGLheadProv() {
        return GLheadProv;
    }

    public void setGLheadProv(String GLheadProv) {
        this.GLheadProv = GLheadProv;
    }

    public String getAcctDesc() {
        return acctDesc;
    }

    public void setAcctDesc(String acctDesc) {
        this.acctDesc = acctDesc;
    }

    public String getAcctcode() {
        return acctcode;
    }

    public void setAcctcode(String acctcode) {
        this.acctcode = acctcode;
    }

    public String getAcctnature() {
        return acctnature;
    }

    public void setAcctnature(String acctnature) {
        this.acctnature = acctnature;
    }

    public String getChqsrno() {
        return chqsrno;
    }

    public void setChqsrno(String chqsrno) {
        this.chqsrno = chqsrno;
    }

    public String getGlHead() {
        return glHead;
    }

    public void setGlHead(String glHead) {
        this.glHead = glHead;
    }

    public String getGlheadint() {
        return glheadint;
    }

    public void setGlheadint(String glheadint) {
        this.glheadint = glheadint;
    }

    public String getMinInt() {
        return minInt;
    }

    public void setMinInt(String minInt) {
        this.minInt = minInt;
    }

    public String getMinbal() {
        return minbal;
    }

    public void setMinbal(String minbal) {
        this.minbal = minbal;
    }

    public String getMinbal_chq() {
        return minbal_chq;
    }

    public void setMinbal_chq(String minbal_chq) {
        this.minbal_chq = minbal_chq;
    }

    public String getMinbalcharge() {
        return minbalcharge;
    }

    public void setMinbalcharge(String minbalcharge) {
        this.minbalcharge = minbalcharge;
    }

    public String getOfAcctnature() {
        return ofAcctnature;
    }

    public void setOfAcctnature(String ofAcctnature) {
        this.ofAcctnature = ofAcctnature;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getStaffInt() {
        return staffInt;
    }

    public void setStaffInt(String staffInt) {
        this.staffInt = staffInt;
    }

    @Override
    public int compareTo(Object o1) {
        AccountTypeMasterGrid pojo = (AccountTypeMasterGrid) o1;
        return this.acctnature.compareTo(pojo.getAcctnature());
    }
}
