/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.ho;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareTransferAuth {
    String trfDt;
    String certNo;
    String trfrNo;
    String trfeNo;
    String trfrName;
    String trfeName;
    String minshare;
    String maxShare;
    String noOfShare;
    String auth;
    String enterby;

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

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getMaxShare() {
        return maxShare;
    }

    public void setMaxShare(String maxShare) {
        this.maxShare = maxShare;
    }

    public String getMinshare() {
        return minshare;
    }

    public void setMinshare(String minshare) {
        this.minshare = minshare;
    }

    public String getNoOfShare() {
        return noOfShare;
    }

    public void setNoOfShare(String noOfShare) {
        this.noOfShare = noOfShare;
    }

    public String getTrfDt() {
        return trfDt;
    }

    public void setTrfDt(String trfDt) {
        this.trfDt = trfDt;
    }

    public String getTrfeName() {
        return trfeName;
    }

    public void setTrfeName(String trfeName) {
        this.trfeName = trfeName;
    }

    public String getTrfeNo() {
        return trfeNo;
    }

    public void setTrfeNo(String trfeNo) {
        this.trfeNo = trfeNo;
    }

    public String getTrfrName() {
        return trfrName;
    }

    public void setTrfrName(String trfrName) {
        this.trfrName = trfrName;
    }

    public String getTrfrNo() {
        return trfrNo;
    }

    public void setTrfrNo(String trfrNo) {
        this.trfrNo = trfrNo;
    }
    
}
