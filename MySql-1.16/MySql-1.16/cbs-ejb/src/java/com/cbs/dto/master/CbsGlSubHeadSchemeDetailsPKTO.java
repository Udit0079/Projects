/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.master;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsGlSubHeadSchemeDetailsPKTO implements Serializable {
    private String schemeCode;    
    private String glSubHeadCode;

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
    
    
}
