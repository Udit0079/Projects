/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author saurabhsipl
 */
public class AdvncAgnstShareDebtPojo implements Serializable {
    private String name;
    private String pan;
    private String status;
    private String dateOfAdvnc;
    private String typeOfSecurity;
    private String natureOfCreditFacility;
    private BigDecimal sanctAmt;
    private BigDecimal outstndngAtEndOfQtr;
    private String mktValueOfShare;
    private String shareType;
    private String dueDtofRepymntOfAdvnc;

    public String getDateOfAdvnc() {
        return dateOfAdvnc;
    }

    public void setDateOfAdvnc(String dateOfAdvnc) {
        this.dateOfAdvnc = dateOfAdvnc;
    }

    public String getDueDtofRepymntOfAdvnc() {
        return dueDtofRepymntOfAdvnc;
    }

    public void setDueDtofRepymntOfAdvnc(String dueDtofRepymntOfAdvnc) {
        this.dueDtofRepymntOfAdvnc = dueDtofRepymntOfAdvnc;
    }

    public String getMktValueOfShare() {
        return mktValueOfShare;
    }

    public void setMktValueOfShare(String mktValueOfShare) {
        this.mktValueOfShare = mktValueOfShare;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNatureOfCreditFacility() {
        return natureOfCreditFacility;
    }

    public void setNatureOfCreditFacility(String natureOfCreditFacility) {
        this.natureOfCreditFacility = natureOfCreditFacility;
    }

    public BigDecimal getOutstndngAtEndOfQtr() {
        return outstndngAtEndOfQtr;
    }

    public void setOutstndngAtEndOfQtr(BigDecimal outstndngAtEndOfQtr) {
        this.outstndngAtEndOfQtr = outstndngAtEndOfQtr;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public BigDecimal getSanctAmt() {
        return sanctAmt;
    }

    public void setSanctAmt(BigDecimal sanctAmt) {
        this.sanctAmt = sanctAmt;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeOfSecurity() {
        return typeOfSecurity;
    }

    public void setTypeOfSecurity(String typeOfSecurity) {
        this.typeOfSecurity = typeOfSecurity;
    }

}
