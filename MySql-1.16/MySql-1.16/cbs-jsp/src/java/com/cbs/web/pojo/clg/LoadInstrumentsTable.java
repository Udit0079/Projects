/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.clg;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class LoadInstrumentsTable implements Serializable{
    public String acctno;
    public String custName;
    public String jtName1;
    public String jtName2;
    public String jtName3;
    public String jtName4;
    public String amount;
    public String date;
    public String instNo;
    public String bankName;
    public String bankAdd;
    public String micrCode;
    public String drwAcctNo;
    public String drwName;
    public String enterBy;
    public String vtot;
    public String status;

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankAdd() {
        return bankAdd;
    }

    public void setBankAdd(String bankAdd) {
        this.bankAdd = bankAdd;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDrwAcctNo() {
        return drwAcctNo;
    }

    public void setDrwAcctNo(String drwAcctNo) {
        this.drwAcctNo = drwAcctNo;
    }

    public String getDrwName() {
        return drwName;
    }

    public void setDrwName(String drwName) {
        this.drwName = drwName;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVtot() {
        return vtot;
    }

    public void setVtot(String vtot) {
        this.vtot = vtot;
    }

    public LoadInstrumentsTable(String acctno, String custName, String jtName1, String jtName2, String jtName3, String jtName4, String amount, String date, String instNo, String bankName, String bankAdd, String micrCode, String drwAcctNo, String drwName, String enterBy, String vtot, String status) {
        this.acctno = acctno;
        this.custName = custName;
        this.jtName1 = jtName1;
        this.jtName2 = jtName2;
        this.jtName3 = jtName3;
        this.jtName4 = jtName4;
        this.amount = amount;
        this.date = date;
        this.instNo = instNo;
        this.bankName = bankName;
        this.bankAdd = bankAdd;
        this.micrCode = micrCode;
        this.drwAcctNo = drwAcctNo;
        this.drwName = drwName;
        this.enterBy = enterBy;
        this.vtot = vtot;
        this.status = status;
    }

   

  


}
