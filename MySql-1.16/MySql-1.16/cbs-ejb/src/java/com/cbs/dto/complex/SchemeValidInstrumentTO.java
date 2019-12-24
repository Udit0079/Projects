/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.complex;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class SchemeValidInstrumentTO implements Serializable {

    private String schemeCode;
    private String instrumentCode;
    private String schemeType;
    private String crDrIndFlag;
    private String delFlag;
    private String saveUpdateFlag;

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

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public void setInstrumentCode(String instrumentCode) {
        this.instrumentCode = instrumentCode;
    }

    public String getSaveUpdateFlag() {
        return saveUpdateFlag;
    }

    public void setSaveUpdateFlag(String saveUpdateFlag) {
        this.saveUpdateFlag = saveUpdateFlag;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
}
