/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

/**
 *
 * @author root
 */
public class CBSCustIdentityDetailsHisTo {

    private String customerId;
    private String identificationType;
    private String identityNo;
    private String identityExpiryDate;
    private String enterDate;
    private String enterBy;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdentityExpiryDate() {
        return identityExpiryDate;
    }

    public void setIdentityExpiryDate(String identityExpiryDate) {
        this.identityExpiryDate = identityExpiryDate;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }
}
