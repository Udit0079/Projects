/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.neftrtgs;

import java.io.Serializable;
import java.math.BigDecimal;

public class NeftOwDownloadDTO implements Serializable {

    private String fileGenDt;
    private String fileGenBrName;
    private String fileName;
    private String remitterAccount;
    private String beneficiaryName;
    private String beneficiaryAccount;
    private String beneficiaryIfsc;
    private BigDecimal amount;

    public String getFileGenDt() {
        return fileGenDt;
    }

    public void setFileGenDt(String fileGenDt) {
        this.fileGenDt = fileGenDt;
    }

    public String getFileGenBrName() {
        return fileGenBrName;
    }

    public void setFileGenBrName(String fileGenBrName) {
        this.fileGenBrName = fileGenBrName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemitterAccount() {
        return remitterAccount;
    }

    public void setRemitterAccount(String remitterAccount) {
        this.remitterAccount = remitterAccount;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getBeneficiaryIfsc() {
        return beneficiaryIfsc;
    }

    public void setBeneficiaryIfsc(String beneficiaryIfsc) {
        this.beneficiaryIfsc = beneficiaryIfsc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
