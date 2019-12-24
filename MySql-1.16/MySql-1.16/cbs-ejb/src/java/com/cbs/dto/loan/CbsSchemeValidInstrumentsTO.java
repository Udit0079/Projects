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
public class CbsSchemeValidInstrumentsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeValidInstrumentsPKTO cbsSchemeValidInstrumentsPKTO;
    private String schemeType;
    private String crDrIndFlag;
    private String delFlag;

    public CbsSchemeValidInstrumentsPKTO getCbsSchemeValidInstrumentsPKTO() {
        return cbsSchemeValidInstrumentsPKTO;
    }

    public void setCbsSchemeValidInstrumentsPKTO(CbsSchemeValidInstrumentsPKTO cbsSchemeValidInstrumentsPKTO) {
        this.cbsSchemeValidInstrumentsPKTO = cbsSchemeValidInstrumentsPKTO;
    }

    public String getCrDrIndFlag() {
        return crDrIndFlag;
    }

    public void setCrDrIndFlag(String crDrIndFlag) {
        this.crDrIndFlag = crDrIndFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
}
