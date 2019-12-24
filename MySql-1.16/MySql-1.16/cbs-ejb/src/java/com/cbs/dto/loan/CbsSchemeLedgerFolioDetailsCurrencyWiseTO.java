/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsSchemeLedgerFolioDetailsCurrencyWiseTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeLedgerFolioDetailsCurrencyWisePKTO cbsSchemeLedgerFolioDetailsCurrencyWisePKTO;
    private String schemeType;
    private String currencyCode;
    private Integer freeFolios;
    private String delFlag;

    public CbsSchemeLedgerFolioDetailsCurrencyWisePKTO getCbsSchemeLedgerFolioDetailsCurrencyWisePKTO() {
        return cbsSchemeLedgerFolioDetailsCurrencyWisePKTO;
    }

    public void setCbsSchemeLedgerFolioDetailsCurrencyWisePKTO(CbsSchemeLedgerFolioDetailsCurrencyWisePKTO cbsSchemeLedgerFolioDetailsCurrencyWisePKTO) {
        this.cbsSchemeLedgerFolioDetailsCurrencyWisePKTO = cbsSchemeLedgerFolioDetailsCurrencyWisePKTO;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getFreeFolios() {
        return freeFolios;
    }

    public void setFreeFolios(Integer freeFolios) {
        this.freeFolios = freeFolios;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
}
