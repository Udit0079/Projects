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
public class GlSubHeadSchemeTO implements Serializable {

    private String schemeCode;
    private String schemeType;
    private String glSubHeadCode;
    private String defaultFlag;
    private String delFlag;
    private String acName;
    private String saveUpdateCounter;

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getGlSubHeadCode() {
        return glSubHeadCode;
    }

    public void setGlSubHeadCode(String glSubHeadCode) {
        this.glSubHeadCode = glSubHeadCode;
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

    public String getSaveUpdateCounter() {
        return saveUpdateCounter;
    }

    public void setSaveUpdateCounter(String saveUpdateCounter) {
        this.saveUpdateCounter = saveUpdateCounter;
    }
}
