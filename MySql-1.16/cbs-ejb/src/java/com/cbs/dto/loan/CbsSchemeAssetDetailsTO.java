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
public class CbsSchemeAssetDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeAssetDetailsPKTO cbsSchemeAssetDetailsPKTO;
    private String mainClass;
    private String subClass;
    private String intAccre;
    private String intFlagBk;
    private String intFlagColl;
    private String pdFlag;
    private String intSuspPlaceholder;
    private String provisionDr;
    private String placeholdersCr;
    private String delFlag;

    public CbsSchemeAssetDetailsPKTO getCbsSchemeAssetDetailsPKTO() {
        return cbsSchemeAssetDetailsPKTO;
    }

    public void setCbsSchemeAssetDetailsPKTO(CbsSchemeAssetDetailsPKTO cbsSchemeAssetDetailsPKTO) {
        this.cbsSchemeAssetDetailsPKTO = cbsSchemeAssetDetailsPKTO;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getIntAccre() {
        return intAccre;
    }

    public void setIntAccre(String intAccre) {
        this.intAccre = intAccre;
    }

    public String getIntFlagBk() {
        return intFlagBk;
    }

    public void setIntFlagBk(String intFlagBk) {
        this.intFlagBk = intFlagBk;
    }

    public String getIntFlagColl() {
        return intFlagColl;
    }

    public void setIntFlagColl(String intFlagColl) {
        this.intFlagColl = intFlagColl;
    }

    public String getIntSuspPlaceholder() {
        return intSuspPlaceholder;
    }

    public void setIntSuspPlaceholder(String intSuspPlaceholder) {
        this.intSuspPlaceholder = intSuspPlaceholder;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getPdFlag() {
        return pdFlag;
    }

    public void setPdFlag(String pdFlag) {
        this.pdFlag = pdFlag;
    }

    public String getPlaceholdersCr() {
        return placeholdersCr;
    }

    public void setPlaceholdersCr(String placeholdersCr) {
        this.placeholdersCr = placeholdersCr;
    }

    public String getProvisionDr() {
        return provisionDr;
    }

    public void setProvisionDr(String provisionDr) {
        this.provisionDr = provisionDr;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }
}
