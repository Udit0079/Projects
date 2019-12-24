/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.ho;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareCertificateAuth {
    String regfolioNo;
    String name;
    String certNo;
    String issueDt;
    String enterBy;
    String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegfolioNo() {
        return regfolioNo;
    }

    public void setRegfolioNo(String regfolioNo) {
        this.regfolioNo = regfolioNo;
    }

}
