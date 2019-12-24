/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareCertPojo implements Serializable {

    private String folioNo;
    private String shareHolderName, relatedAcno;
    private String branch;
    private int certNo;
    private Date issueDt;
    private int noOfShare;
    private double shareAmt;
    private int fromNo;
    private int toNO;
    private String customerId;

    public String getRelatedAcno() {
        return relatedAcno;
    }

    public void setRelatedAcno(String relatedAcno) {
        this.relatedAcno = relatedAcno;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getCertNo() {
        return certNo;
    }

    public void setCertNo(int certNo) {
        this.certNo = certNo;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public int getFromNo() {
        return fromNo;
    }

    public void setFromNo(int fromNo) {
        this.fromNo = fromNo;
    }

    public Date getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(Date issueDt) {
        this.issueDt = issueDt;
    }

    public int getNoOfShare() {
        return noOfShare;
    }

    public void setNoOfShare(int noOfShare) {
        this.noOfShare = noOfShare;
    }

    public double getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(double shareAmt) {
        this.shareAmt = shareAmt;
    }

    public String getShareHolderName() {
        return shareHolderName;
    }

    public void setShareHolderName(String shareHolderName) {
        this.shareHolderName = shareHolderName;
    }

    public int getToNO() {
        return toNO;
    }

    public void setToNO(int toNO) {
        this.toNO = toNO;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
       
}
