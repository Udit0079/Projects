/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;
import java.io.Serializable;
/**
 *
 * @author Administrator
 */
public class LoanMisAuthD implements Serializable{

    private String restructing;
    private String senctionLevel;
    private String senctionAuthority;
    private String interestTbl;
    private String interestType;
    private String schemeCode;
    private String npaClassification;
    private String court;
    private String modeOfSet;
    private String debtWaiverRs;
    private String assetClassRe;

    public String getAssetClassRe() {
        return assetClassRe;
    }

    public void setAssetClassRe(String assetClassRe) {
        this.assetClassRe = assetClassRe;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getDebtWaiverRs() {
        return debtWaiverRs;
    }

    public void setDebtWaiverRs(String debtWaiverRs) {
        this.debtWaiverRs = debtWaiverRs;
    }

    public String getInterestTbl() {
        return interestTbl;
    }

    public void setInterestTbl(String interestTbl) {
        this.interestTbl = interestTbl;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getModeOfSet() {
        return modeOfSet;
    }

    public void setModeOfSet(String modeOfSet) {
        this.modeOfSet = modeOfSet;
    }

    public String getNpaClassification() {
        return npaClassification;
    }

    public void setNpaClassification(String npaClassification) {
        this.npaClassification = npaClassification;
    }

    public String getRestructing() {
        return restructing;
    }

    public void setRestructing(String restructing) {
        this.restructing = restructing;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSenctionAuthority() {
        return senctionAuthority;
    }

    public void setSenctionAuthority(String senctionAuthority) {
        this.senctionAuthority = senctionAuthority;
    }

    public String getSenctionLevel() {
        return senctionLevel;
    }

    public void setSenctionLevel(String senctionLevel) {
        this.senctionLevel = senctionLevel;
    }

}
