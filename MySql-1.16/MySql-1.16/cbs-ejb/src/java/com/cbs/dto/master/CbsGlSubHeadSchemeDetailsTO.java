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
public class CbsGlSubHeadSchemeDetailsTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected CbsGlSubHeadSchemeDetailsPKTO cbsGlSubHeadSchemeDetailsPKTO;
    
    private String schemeType;
 
    private String dfltFlag;
    
    private String delFlag;

    public CbsGlSubHeadSchemeDetailsPKTO getCbsGlSubHeadSchemeDetailsPKTO() {
        return cbsGlSubHeadSchemeDetailsPKTO;
    }

    public void setCbsGlSubHeadSchemeDetailsPKTO(CbsGlSubHeadSchemeDetailsPKTO cbsGlSubHeadSchemeDetailsPKTO) {
        this.cbsGlSubHeadSchemeDetailsPKTO = cbsGlSubHeadSchemeDetailsPKTO;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDfltFlag() {
        return dfltFlag;
    }

    public void setDfltFlag(String dfltFlag) {
        this.dfltFlag = dfltFlag;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
    
    
    
}
